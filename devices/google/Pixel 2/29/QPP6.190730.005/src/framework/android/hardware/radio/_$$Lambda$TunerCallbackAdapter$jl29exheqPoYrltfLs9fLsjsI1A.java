/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.TunerCallbackAdapter;

public final class _$$Lambda$TunerCallbackAdapter$jl29exheqPoYrltfLs9fLsjsI1A
implements Runnable {
    private final /* synthetic */ TunerCallbackAdapter f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$TunerCallbackAdapter$jl29exheqPoYrltfLs9fLsjsI1A(TunerCallbackAdapter tunerCallbackAdapter, int n) {
        this.f$0 = tunerCallbackAdapter;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onError$2$TunerCallbackAdapter(this.f$1);
    }
}

