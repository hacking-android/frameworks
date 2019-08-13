/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.CompositionSamplingListener;

public final class _$$Lambda$CompositionSamplingListener$hrbPutjnKRv7VkkiY9eg32N6QA8
implements Runnable {
    private final /* synthetic */ CompositionSamplingListener f$0;
    private final /* synthetic */ float f$1;

    public /* synthetic */ _$$Lambda$CompositionSamplingListener$hrbPutjnKRv7VkkiY9eg32N6QA8(CompositionSamplingListener compositionSamplingListener, float f) {
        this.f$0 = compositionSamplingListener;
        this.f$1 = f;
    }

    @Override
    public final void run() {
        CompositionSamplingListener.lambda$dispatchOnSampleCollected$0(this.f$0, this.f$1);
    }
}

