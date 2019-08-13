/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramSelector;
import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ ProgramSelector f$2;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$Hj_P___HTEx_8p7qvYVPXmhwu7w(TunerCallbackAdapter tunerCallbackAdapter, int n, ProgramSelector programSelector) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = n;
        this.f$2 = programSelector;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onTuneFailed$3$TunerCallbackAdapter(this.f$1, this.f$2);
    }
}

