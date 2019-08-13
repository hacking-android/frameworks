/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.PowerManager;

public final class _$$Lambda$PowerManager$WakeLock$VvFzmRZ4ZGlXx7u3lSAJ_T_YUjw
implements Runnable {
    private final /* synthetic */ PowerManager.WakeLock f$0;
    private final /* synthetic */ Runnable f$1;

    public /* synthetic */ _$$Lambda$PowerManager$WakeLock$VvFzmRZ4ZGlXx7u3lSAJ_T_YUjw(PowerManager.WakeLock wakeLock, Runnable runnable) {
        this.f$0 = wakeLock;
        this.f$1 = runnable;
    }

    @Override
    public final void run() {
        this.f$0.lambda$wrap$0$PowerManager$WakeLock(this.f$1);
    }
}

