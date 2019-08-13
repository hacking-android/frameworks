/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.os.ConditionVariable;
import android.speech.tts.PlaybackQueueItem;
import android.speech.tts.TextToSpeechService;

class SilencePlaybackQueueItem
extends PlaybackQueueItem {
    private final ConditionVariable mCondVar = new ConditionVariable();
    private final long mSilenceDurationMs;

    SilencePlaybackQueueItem(TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object object, long l) {
        super(utteranceProgressDispatcher, object);
        this.mSilenceDurationMs = l;
    }

    @Override
    public void run() {
        this.getDispatcher().dispatchOnStart();
        boolean bl = false;
        long l = this.mSilenceDurationMs;
        if (l > 0L) {
            bl = this.mCondVar.block(l);
        }
        if (bl) {
            this.getDispatcher().dispatchOnStop();
        } else {
            this.getDispatcher().dispatchOnSuccess();
        }
    }

    @Override
    void stop(int n) {
        this.mCondVar.open();
    }
}

