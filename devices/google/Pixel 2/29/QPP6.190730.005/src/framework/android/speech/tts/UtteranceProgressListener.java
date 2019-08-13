/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.speech.tts.TextToSpeech;

public abstract class UtteranceProgressListener {
    static UtteranceProgressListener from(TextToSpeech.OnUtteranceCompletedListener onUtteranceCompletedListener) {
        return new UtteranceProgressListener(){

            @Override
            public void onDone(String string2) {
                synchronized (this) {
                    OnUtteranceCompletedListener.this.onUtteranceCompleted(string2);
                    return;
                }
            }

            @Override
            public void onError(String string2) {
                OnUtteranceCompletedListener.this.onUtteranceCompleted(string2);
            }

            @Override
            public void onStart(String string2) {
            }

            @Override
            public void onStop(String string2, boolean bl) {
                OnUtteranceCompletedListener.this.onUtteranceCompleted(string2);
            }
        };
    }

    public void onAudioAvailable(String string2, byte[] arrby) {
    }

    public void onBeginSynthesis(String string2, int n, int n2, int n3) {
    }

    public abstract void onDone(String var1);

    @Deprecated
    public abstract void onError(String var1);

    public void onError(String string2, int n) {
        this.onError(string2);
    }

    public void onRangeStart(String string2, int n, int n2, int n3) {
        this.onUtteranceRangeStart(string2, n, n2);
    }

    public abstract void onStart(String var1);

    public void onStop(String string2, boolean bl) {
    }

    @Deprecated
    public void onUtteranceRangeStart(String string2, int n, int n2) {
    }

}

