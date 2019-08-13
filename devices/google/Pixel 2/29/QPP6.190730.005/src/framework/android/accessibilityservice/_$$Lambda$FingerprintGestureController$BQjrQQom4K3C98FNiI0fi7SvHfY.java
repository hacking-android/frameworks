/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.FingerprintGestureController;

public final class _$$Lambda$FingerprintGestureController$BQjrQQom4K3C98FNiI0fi7SvHfY
implements Runnable {
    private final /* synthetic */ FingerprintGestureController.FingerprintGestureCallback f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$FingerprintGestureController$BQjrQQom4K3C98FNiI0fi7SvHfY(FingerprintGestureController.FingerprintGestureCallback fingerprintGestureCallback, int n) {
        this.f$0 = fingerprintGestureCallback;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        FingerprintGestureController.lambda$onGesture$1(this.f$0, this.f$1);
    }
}

