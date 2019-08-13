/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.StrictMode;
import android.os.strictmode.Violation;

public final class _$$Lambda$StrictMode$UFC_nI1x6u8ZwMQmA7bmj9NHZz4
implements Runnable {
    private final /* synthetic */ StrictMode.OnVmViolationListener f$0;
    private final /* synthetic */ Violation f$1;

    public /* synthetic */ _$$Lambda$StrictMode$UFC_nI1x6u8ZwMQmA7bmj9NHZz4(StrictMode.OnVmViolationListener onVmViolationListener, Violation violation) {
        this.f$0 = onVmViolationListener;
        this.f$1 = violation;
    }

    @Override
    public final void run() {
        StrictMode.lambda$onVmPolicyViolation$3(this.f$0, this.f$1);
    }
}

