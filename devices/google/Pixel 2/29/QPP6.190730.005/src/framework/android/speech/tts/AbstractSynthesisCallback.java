/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.speech.tts.SynthesisCallback;

abstract class AbstractSynthesisCallback
implements SynthesisCallback {
    protected final boolean mClientIsUsingV2;

    AbstractSynthesisCallback(boolean bl) {
        this.mClientIsUsingV2 = bl;
    }

    int errorCodeOnStop() {
        int n = this.mClientIsUsingV2 ? -2 : -1;
        return n;
    }

    abstract void stop();
}

