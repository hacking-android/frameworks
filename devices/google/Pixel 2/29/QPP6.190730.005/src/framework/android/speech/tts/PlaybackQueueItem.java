/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.speech.tts.TextToSpeechService;

abstract class PlaybackQueueItem
implements Runnable {
    private final Object mCallerIdentity;
    private final TextToSpeechService.UtteranceProgressDispatcher mDispatcher;

    PlaybackQueueItem(TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object object) {
        this.mDispatcher = utteranceProgressDispatcher;
        this.mCallerIdentity = object;
    }

    Object getCallerIdentity() {
        return this.mCallerIdentity;
    }

    protected TextToSpeechService.UtteranceProgressDispatcher getDispatcher() {
        return this.mDispatcher;
    }

    @Override
    public abstract void run();

    abstract void stop(int var1);
}

