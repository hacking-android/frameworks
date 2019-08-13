/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.PowerManager;

public final class _$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA
implements Runnable {
    private final /* synthetic */ PowerManager.OnThermalStatusChangedListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$PowerManager$1$_RL9hKNKSaGL1mmR_EjQ_Cm9KuA(PowerManager.OnThermalStatusChangedListener onThermalStatusChangedListener, int n) {
        this.f$0 = onThermalStatusChangedListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        PowerManager.1.lambda$onStatusChange$0(this.f$0, this.f$1);
    }
}

