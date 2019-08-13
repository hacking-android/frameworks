/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.fingerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.PowerManager;

public final class _$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY
implements Runnable {
    private final /* synthetic */ FingerprintManager.LockoutResetCallback f$0;
    private final /* synthetic */ PowerManager.WakeLock f$1;

    public /* synthetic */ _$$Lambda$FingerprintManager$1$4i3tUU8mafgvA9HaB2UPD31L6UY(FingerprintManager.LockoutResetCallback lockoutResetCallback, PowerManager.WakeLock wakeLock) {
        this.f$0 = lockoutResetCallback;
        this.f$1 = wakeLock;
    }

    @Override
    public final void run() {
        FingerprintManager.1.lambda$onLockoutReset$0(this.f$0, this.f$1);
    }
}

