/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.media.AudioTrack;
import android.speech.tts.AbstractEventLogger;
import android.speech.tts.BlockingAudioTrack;
import android.speech.tts.PlaybackQueueItem;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class SynthesisPlaybackQueueItem
extends PlaybackQueueItem
implements AudioTrack.OnPlaybackPositionUpdateListener {
    private static final boolean DBG = false;
    private static final long MAX_UNCONSUMED_AUDIO_MS = 500L;
    private static final int NOT_RUN = 0;
    private static final int RUN_CALLED = 1;
    private static final int STOP_CALLED = 2;
    private static final String TAG = "TTS.SynthQueueItem";
    private final BlockingAudioTrack mAudioTrack;
    private final LinkedList<ListEntry> mDataBufferList = new LinkedList();
    private volatile boolean mDone = false;
    private final Lock mListLock = new ReentrantLock();
    private final AbstractEventLogger mLogger;
    private final Condition mNotFull = this.mListLock.newCondition();
    private final Condition mReadReady = this.mListLock.newCondition();
    private final AtomicInteger mRunState = new AtomicInteger(0);
    private volatile int mStatusCode = 0;
    private volatile boolean mStopped = false;
    private int mUnconsumedBytes = 0;
    private ConcurrentLinkedQueue<ProgressMarker> markerList = new ConcurrentLinkedQueue();

    SynthesisPlaybackQueueItem(TextToSpeechService.AudioOutputParams audioOutputParams, int n, int n2, int n3, TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object object, AbstractEventLogger abstractEventLogger) {
        super(utteranceProgressDispatcher, object);
        this.mAudioTrack = new BlockingAudioTrack(audioOutputParams, n, n2, n3);
        this.mLogger = abstractEventLogger;
    }

    private void dispatchEndStatus() {
        TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher = this.getDispatcher();
        if (this.mStatusCode == 0) {
            utteranceProgressDispatcher.dispatchOnSuccess();
        } else if (this.mStatusCode == -2) {
            utteranceProgressDispatcher.dispatchOnStop();
        } else {
            utteranceProgressDispatcher.dispatchOnError(this.mStatusCode);
        }
        this.mLogger.onCompleted(this.mStatusCode);
    }

    private byte[] take() throws InterruptedException {
        byte[] arrby;
        block7 : {
            block6 : {
                this.mListLock.lock();
                while (this.mDataBufferList.size() == 0 && !this.mStopped && !this.mDone) {
                    this.mReadReady.await();
                }
                boolean bl = this.mStopped;
                if (!bl) break block6;
                this.mListLock.unlock();
                return null;
            }
            arrby = this.mDataBufferList.poll();
            if (arrby != null) break block7;
            this.mListLock.unlock();
            return null;
        }
        try {
            this.mUnconsumedBytes -= arrby.mBytes.length;
            this.mNotFull.signal();
            arrby = arrby.mBytes;
            return arrby;
        }
        finally {
            this.mListLock.unlock();
        }
    }

    void done() {
        try {
            this.mListLock.lock();
            this.mDone = true;
            this.mReadReady.signal();
            this.mNotFull.signal();
            return;
        }
        finally {
            this.mListLock.unlock();
        }
    }

    @Override
    public void onMarkerReached(AudioTrack object) {
        object = this.markerList.poll();
        if (object == null) {
            Log.e(TAG, "onMarkerReached reached called but no marker in queue");
            return;
        }
        this.getDispatcher().dispatchOnRangeStart(((ProgressMarker)object).start, ((ProgressMarker)object).end, ((ProgressMarker)object).frames);
        this.updateMarker();
    }

    @Override
    public void onPeriodicNotification(AudioTrack audioTrack) {
    }

    void put(byte[] arrby) throws InterruptedException {
        block5 : {
            this.mListLock.lock();
            while (this.mAudioTrack.getAudioLengthMs(this.mUnconsumedBytes) > 500L && !this.mStopped) {
                this.mNotFull.await();
            }
            boolean bl = this.mStopped;
            if (!bl) break block5;
            this.mListLock.unlock();
            return;
        }
        try {
            LinkedList<ListEntry> linkedList = this.mDataBufferList;
            ListEntry listEntry = new ListEntry(arrby);
            linkedList.add(listEntry);
            this.mUnconsumedBytes += arrby.length;
            this.mReadReady.signal();
            return;
        }
        finally {
            this.mListLock.unlock();
        }
    }

    void rangeStart(int n, int n2, int n3) {
        this.markerList.add(new ProgressMarker(n, n2, n3));
        this.updateMarker();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        if (!this.mRunState.compareAndSet(0, 1)) {
            return;
        }
        byte[] arrby = this.getDispatcher();
        arrby.dispatchOnStart();
        if (!this.mAudioTrack.init()) {
            arrby.dispatchOnError(-5);
            return;
        }
        this.mAudioTrack.setPlaybackPositionUpdateListener(this);
        this.updateMarker();
        try {
            while ((arrby = this.take()) != null) {
                this.mAudioTrack.write(arrby);
                this.mLogger.onAudioDataWritten();
            }
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        this.mAudioTrack.waitAndRelease();
        this.dispatchEndStatus();
    }

    @Override
    void stop(int n) {
        try {
            this.mListLock.lock();
            this.mStopped = true;
            this.mStatusCode = n;
            this.mNotFull.signal();
            if (this.mRunState.getAndSet(2) == 0) {
                this.dispatchEndStatus();
                return;
            }
            this.mReadReady.signal();
            this.mAudioTrack.stop();
            return;
        }
        finally {
            this.mListLock.unlock();
        }
    }

    void updateMarker() {
        ProgressMarker progressMarker = this.markerList.peek();
        if (progressMarker != null) {
            int n = progressMarker.frames == 0 ? 1 : progressMarker.frames;
            this.mAudioTrack.setNotificationMarkerPosition(n);
        }
    }

    static final class ListEntry {
        final byte[] mBytes;

        ListEntry(byte[] arrby) {
            this.mBytes = arrby;
        }
    }

    private class ProgressMarker {
        public final int end;
        public final int frames;
        public final int start;

        public ProgressMarker(int n, int n2, int n3) {
            this.frames = n;
            this.start = n2;
            this.end = n3;
        }
    }

}

