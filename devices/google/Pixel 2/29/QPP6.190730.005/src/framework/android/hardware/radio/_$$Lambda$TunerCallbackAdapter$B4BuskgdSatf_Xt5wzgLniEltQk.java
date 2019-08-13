/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.RadioManager;
import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$B4BuskgdSatf_Xt5wzgLniEltQk
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ RadioManager.BandConfig f$1;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$B4BuskgdSatf_Xt5wzgLniEltQk(TunerCallbackAdapter tunerCallbackAdapter, RadioManager.BandConfig bandConfig) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = bandConfig;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onConfigurationChanged$5$TunerCallbackAdapter(this.f$1);
    }
}

