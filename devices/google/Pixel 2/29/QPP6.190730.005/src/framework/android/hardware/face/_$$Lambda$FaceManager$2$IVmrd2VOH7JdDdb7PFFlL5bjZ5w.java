/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.face;

import android.hardware.face.FaceManager;
import android.os.PowerManager;

public final class _$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w
implements Runnable {
    private final /* synthetic */ FaceManager.LockoutResetCallback f$0;
    private final /* synthetic */ PowerManager.WakeLock f$1;

    public /* synthetic */ _$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w(FaceManager.LockoutResetCallback lockoutResetCallback, PowerManager.WakeLock wakeLock) {
        this.f$0 = lockoutResetCallback;
        this.f$1 = wakeLock;
    }

    @Override
    public final void run() {
        FaceManager.2.lambda$onLockoutReset$0(this.f$0, this.f$1);
    }
}

