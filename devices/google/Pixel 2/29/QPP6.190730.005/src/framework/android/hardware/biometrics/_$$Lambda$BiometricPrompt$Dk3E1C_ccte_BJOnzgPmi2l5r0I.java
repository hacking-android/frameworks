/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.BiometricPrompt;

public final class _$$Lambda$BiometricPrompt$Dk3E1C_ccte_BJOnzgPmi2l5r0I
implements Runnable {
    private final /* synthetic */ BiometricPrompt f$0;
    private final /* synthetic */ BiometricPrompt.AuthenticationCallback f$1;

    public /* synthetic */ _$$Lambda$BiometricPrompt$Dk3E1C_ccte_BJOnzgPmi2l5r0I(BiometricPrompt biometricPrompt, BiometricPrompt.AuthenticationCallback authenticationCallback) {
        this.f$0 = biometricPrompt;
        this.f$1 = authenticationCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$authenticateInternal$0$BiometricPrompt(this.f$1);
    }
}

