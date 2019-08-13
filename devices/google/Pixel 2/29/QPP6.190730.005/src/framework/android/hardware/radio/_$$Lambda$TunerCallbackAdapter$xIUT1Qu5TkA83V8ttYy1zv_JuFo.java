/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$xIUT1Qu5TkA83V8ttYy1zv_JuFo
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$xIUT1Qu5TkA83V8ttYy1zv_JuFo(TunerCallbackAdapter tunerCallbackAdapter) {
        this.f$0 = tunerCallbackAdapter;
    }

    @Override
    public final void run() {
        this.f$0.lambda$sendBackgroundScanCompleteLocked$11$TunerCallbackAdapter();
    }
}

