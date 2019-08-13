/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.speech.tts.PlaybackQueueItem;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

class AudioPlaybackHandler {
    private static final boolean DBG = false;
    private static final String TAG = "TTS.AudioPlaybackHandler";
    private volatile PlaybackQueueItem mCurrentWorkItem = null;
    private final Thread mHandlerThread = new Thread((Runnable)new MessageLoop(), "TTS.AudioPlaybackThread");
    private final LinkedBlockingQueue<PlaybackQueueItem> mQueue = new LinkedBlockingQueue();

    AudioPlaybackHandler() {
    }

    static /* synthetic */ LinkedBlockingQueue access$100(AudioPlaybackHandler audioPlaybackHandler) {
        return audioPlaybackHandler.mQueue;
    }

    static /* synthetic */ PlaybackQueueItem access$202(AudioPlaybackHandler audioPlaybackHandler, PlaybackQueueItem playbackQueueItem) {
        audioPlaybackHandler.mCurrentWorkItem = playbackQueueItem;
        return playbackQueueItem;
    }

    private void removeAllMessages() {
        this.mQueue.clear();
    }

    private void removeWorkItemsFor(Object object) {
        Iterator<PlaybackQueueItem> iterator = this.mQueue.iterator();
        while (iterator.hasNext()) {
            PlaybackQueueItem playbackQueueItem = iterator.next();
            if (playbackQueueItem.getCallerIdentity() != object) continue;
            iterator.remove();
            this.stop(playbackQueueItem);
        }
    }

    private void stop(PlaybackQueueItem playbackQueueItem) {
        if (playbackQueueItem == null) {
            return;
        }
        playbackQueueItem.stop(-2);
    }

    public void enqueue(PlaybackQueueItem playbackQueueItem) {
        try {
            this.mQueue.put(playbackQueueItem);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    public boolean isSpeaking() {
        boolean bl = this.mQueue.peek() != null || this.mCurrentWorkItem != null;
        return bl;
    }

    public void quit() {
        this.removeAllMessages();
        this.stop(this.mCurrentWorkItem);
        this.mHandlerThread.interrupt();
    }

    public void start() {
        this.mHandlerThread.start();
    }

    public void stop() {
        this.removeAllMessages();
        this.stop(this.mCurrentWorkItem);
    }

    public void stopForApp(Object object) {
        this.removeWorkItemsFor(object);
        PlaybackQueueItem playbackQueueItem = this.mCurrentWorkItem;
        if (playbackQueueItem != null && playbackQueueItem.getCallerIdentity() == object) {
            this.stop(playbackQueueItem);
        }
    }

    private final class MessageLoop
    implements Runnable {
        private MessageLoop() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            do lbl-1000: // 2 sources:
            {
                var1_1 = (PlaybackQueueItem)AudioPlaybackHandler.access$100(AudioPlaybackHandler.this).take();
                break;
            } while (true);
            catch (InterruptedException var1_2) {
                return;
            }
            {
                AudioPlaybackHandler.access$202(AudioPlaybackHandler.this, var1_1);
                var1_1.run();
                AudioPlaybackHandler.access$202(AudioPlaybackHandler.this, null);
                ** while (true)
            }
        }
    }

}

