/*
 * Decompiled with CFR 0.145.
 */
package android.net.util;

import android.net.util.MultinetworkPolicyTracker;

public final class _$$Lambda$MultinetworkPolicyTracker$0siHK6f4lHJz8hbdHbT6G4Kp_V4
implements Runnable {
    private final /* synthetic */ MultinetworkPolicyTracker f$0;
    private final /* synthetic */ Runnable f$1;

    public /* synthetic */ _$$Lambda$MultinetworkPolicyTracker$0siHK6f4lHJz8hbdHbT6G4Kp_V4(MultinetworkPolicyTracker multinetworkPolicyTracker, Runnable runnable) {
        this.f$0 = multinetworkPolicyTracker;
        this.f$1 = runnable;
    }

    @Override
    public final void run() {
        this.f$0.lambda$new$0$MultinetworkPolicyTracker(this.f$1);
    }
}

