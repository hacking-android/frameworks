/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.hardware.display.NightDisplayListener;

public final class _$$Lambda$NightDisplayListener$sOK1HmSbMnFLzc4SdDD1WpVWJiI
implements Runnable {
    private final /* synthetic */ NightDisplayListener f$0;
    private final /* synthetic */ NightDisplayListener.Callback f$1;

    public /* synthetic */ _$$Lambda$NightDisplayListener$sOK1HmSbMnFLzc4SdDD1WpVWJiI(NightDisplayListener nightDisplayListener, NightDisplayListener.Callback callback) {
        this.f$0 = nightDisplayListener;
        this.f$1 = callback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setCallback$0$NightDisplayListener(this.f$1);
    }
}

