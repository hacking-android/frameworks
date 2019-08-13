/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.hardware.biometrics.BiometricPrompt;

public final class _$$Lambda$BiometricPrompt$FhnggONVmg0fSM3ar79llL7ZRYM
implements Runnable {
    private final /* synthetic */ BiometricPrompt f$0;
    private final /* synthetic */ BiometricPrompt.AuthenticationCallback f$1;

    public /* synthetic */ _$$Lambda$BiometricPrompt$FhnggONVmg0fSM3ar79llL7ZRYM(BiometricPrompt biometricPrompt, BiometricPrompt.AuthenticationCallback authenticationCallback) {
        this.f$0 = biometricPrompt;
        this.f$1 = authenticationCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$authenticateInternal$1$BiometricPrompt(this.f$1);
    }
}

