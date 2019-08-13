/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.TunerCallbackAdapter;
import java.util.Map;

public final class _$$Lambda$TunerCallbackAdapter$Yz_4KCDu1MOynGdkDf_oMxqhjeY
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ Map f$1;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$Yz_4KCDu1MOynGdkDf_oMxqhjeY(TunerCallbackAdapter tunerCallbackAdapter, Map map) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = map;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onParametersUpdated$13$TunerCallbackAdapter(this.f$1);
    }
}

