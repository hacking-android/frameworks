/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.StrictMode;
import android.os.strictmode.Violation;

public final class _$$Lambda$StrictMode$AndroidBlockGuardPolicy$FxZGA9KtfTewqdcxlUwvIe5Nx9I
implements Runnable {
    private final /* synthetic */ StrictMode.OnThreadViolationListener f$0;
    private final /* synthetic */ Violation f$1;

    public /* synthetic */ _$$Lambda$StrictMode$AndroidBlockGuardPolicy$FxZGA9KtfTewqdcxlUwvIe5Nx9I(StrictMode.OnThreadViolationListener onThreadViolationListener, Violation violation) {
        this.f$0 = onThreadViolationListener;
        this.f$1 = violation;
    }

    @Override
    public final void run() {
        StrictMode.AndroidBlockGuardPolicy.lambda$onThreadPolicyViolation$1(this.f$0, this.f$1);
    }
}

