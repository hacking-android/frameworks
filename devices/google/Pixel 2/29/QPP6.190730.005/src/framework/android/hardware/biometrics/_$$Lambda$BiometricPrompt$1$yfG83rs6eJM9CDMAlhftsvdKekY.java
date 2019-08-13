/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.BiometricPrompt;

public final class _$$Lambda$BiometricPrompt$1$yfG83rs6eJM9CDMAlhftsvdKekY
implements Runnable {
    private final /* synthetic */ BiometricPrompt.1 f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$BiometricPrompt$1$yfG83rs6eJM9CDMAlhftsvdKekY(BiometricPrompt.1 var1_1, int n, String string2) {
        this.f$0 = var1_1;
        this.f$1 = n;
        this.f$2 = string2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onAcquired$3$BiometricPrompt$1(this.f$1, this.f$2);
    }
}

