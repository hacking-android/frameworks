/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.BiometricPrompt;

public final class _$$Lambda$BiometricPrompt$1$AAMJr_dQQ3dkiYxppvTx2AjuvRQ
implements Runnable {
    private final /* synthetic */ BiometricPrompt.1 f$0;

    public /* synthetic */ _$$Lambda$BiometricPrompt$1$AAMJr_dQQ3dkiYxppvTx2AjuvRQ(BiometricPrompt.1 var1_1) {
        this.f$0 = var1_1;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onAuthenticationFailed$1$BiometricPrompt$1();
    }
}

