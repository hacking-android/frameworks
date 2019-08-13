/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.speech.tts.AbstractEventLogger;
import android.speech.tts.AbstractSynthesisCallback;
import android.speech.tts.AudioPlaybackHandler;
import android.speech.tts.BlockingAudioTrack;
import android.speech.tts.PlaybackQueueItem;
import android.speech.tts.SynthesisPlaybackQueueItem;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

class PlaybackSynthesisCallback
extends AbstractSynthesisCallback {
    private static final boolean DBG = false;
    private static final int MIN_AUDIO_BUFFER_SIZE = 8192;
    private static final String TAG = "PlaybackSynthesisRequest";
    private final TextToSpeechService.AudioOutputParams mAudioParams;
    private final AudioPlaybackHandler mAudioTrackHandler;
    private final Object mCallerIdentity;
    private final TextToSpeechService.UtteranceProgressDispatcher mDispatcher;
    private volatile boolean mDone = false;
    private SynthesisPlaybackQueueItem mItem = null;
    private final AbstractEventLogger mLogger;
    private final Object mStateLock = new Object();
    protected int mStatusCode;

    PlaybackSynthesisCallback(TextToSpeechService.AudioOutputParams audioOutputParams, AudioPlaybackHandler audioPlaybackHandler, TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object object, AbstractEventLogger abstractEventLogger, boolean bl) {
        super(bl);
        this.mAudioParams = audioOutputParams;
        this.mAudioTrackHandler = audioPlaybackHandler;
        this.mDispatcher = utteranceProgressDispatcher;
        this.mCallerIdentity = object;
        this.mLogger = abstractEventLogger;
        this.mStatusCode = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int audioAvailable(byte[] object, int n, int n2) {
        if (n2 <= this.getMaxBufferSize() && n2 > 0) {
            SynthesisPlaybackQueueItem synthesisPlaybackQueueItem;
            byte[] arrby = this.mStateLock;
            synchronized (arrby) {
                if (this.mItem == null) {
                    this.mStatusCode = -5;
                    return -1;
                }
                if (this.mStatusCode != 0) {
                    return -1;
                }
                if (this.mStatusCode == -2) {
                    return this.errorCodeOnStop();
                }
                synthesisPlaybackQueueItem = this.mItem;
            }
            arrby = new byte[n2];
            System.arraycopy(object, n, arrby, 0, n2);
            this.mDispatcher.dispatchOnAudioAvailable(arrby);
            try {
                synthesisPlaybackQueueItem.put(arrby);
                this.mLogger.onEngineDataReceived();
                return 0;
            }
            catch (InterruptedException interruptedException) {
                Object object2 = this.mStateLock;
                synchronized (object2) {
                    this.mStatusCode = -5;
                    return -1;
                }
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("buffer is too large or of zero length (");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" bytes)");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public int done() {
        Object object = this.mStateLock;
        // MONITORENTER : object
        if (this.mDone) {
            Log.w(TAG, "Duplicate call to done()");
            // MONITOREXIT : object
            return -1;
        }
        if (this.mStatusCode == -2) {
            int n = this.errorCodeOnStop();
            // MONITOREXIT : object
            return n;
        }
        this.mDone = true;
        if (this.mItem == null) {
            Log.w(TAG, "done() was called before start() call");
            if (this.mStatusCode == 0) {
                this.mDispatcher.dispatchOnSuccess();
            } else {
                this.mDispatcher.dispatchOnError(this.mStatusCode);
            }
            this.mLogger.onEngineComplete();
            // MONITOREXIT : object
            return -1;
        }
        SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
        int n = this.mStatusCode;
        // MONITOREXIT : object
        if (n == 0) {
            synthesisPlaybackQueueItem.done();
        } else {
            synthesisPlaybackQueueItem.stop(n);
        }
        this.mLogger.onEngineComplete();
        return 0;
    }

    @Override
    public void error() {
        this.error(-3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void error(int n) {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mDone) {
                return;
            }
            this.mStatusCode = n;
            return;
        }
    }

    @Override
    public int getMaxBufferSize() {
        return 8192;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasFinished() {
        Object object = this.mStateLock;
        synchronized (object) {
            return this.mDone;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasStarted() {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mItem == null) return false;
            return true;
        }
    }

    @Override
    public void rangeStart(int n, int n2, int n3) {
        SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
        if (synthesisPlaybackQueueItem == null) {
            Log.e(TAG, "mItem is null");
            return;
        }
        synthesisPlaybackQueueItem.rangeStart(n, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int start(int n, int n2, int n3) {
        Object object;
        if (n2 != 3 && n2 != 2 && n2 != 4) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Audio format encoding ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" not supported. Please use one of AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT or AudioFormat.ENCODING_PCM_FLOAT");
            Log.w(TAG, ((StringBuilder)object).toString());
        }
        this.mDispatcher.dispatchOnBeginSynthesis(n, n2, n3);
        int n4 = BlockingAudioTrack.getChannelConfig(n3);
        object = this.mStateLock;
        synchronized (object) {
            if (n4 == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported number of channels :");
                stringBuilder.append(n3);
                Log.e(TAG, stringBuilder.toString());
                this.mStatusCode = -5;
                return -1;
            }
            if (this.mStatusCode == -2) {
                return this.errorCodeOnStop();
            }
            if (this.mStatusCode != 0) {
                return -1;
            }
            if (this.mItem != null) {
                Log.e(TAG, "Start called twice");
                return -1;
            }
            SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = new SynthesisPlaybackQueueItem(this.mAudioParams, n, n2, n3, this.mDispatcher, this.mCallerIdentity, this.mLogger);
            this.mAudioTrackHandler.enqueue(synthesisPlaybackQueueItem);
            this.mItem = synthesisPlaybackQueueItem;
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    void stop() {
        Object object = this.mStateLock;
        // MONITORENTER : object
        if (this.mDone) {
            // MONITOREXIT : object
            return;
        }
        if (this.mStatusCode == -2) {
            Log.w(TAG, "stop() called twice");
            // MONITOREXIT : object
            return;
        }
        SynthesisPlaybackQueueItem synthesisPlaybackQueueItem = this.mItem;
        this.mStatusCode = -2;
        // MONITOREXIT : object
        if (synthesisPlaybackQueueItem != null) {
            synthesisPlaybackQueueItem.stop(-2);
            return;
        }
        this.mLogger.onCompleted(-2);
        this.mDispatcher.dispatchOnStop();
    }
}

