/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.FingerprintGestureController;

public final class _$$Lambda$FingerprintGestureController$M_ZApqp96G6ZF2WdWrGDJ8Qsfck
implements Runnable {
    private final /* synthetic */ FingerprintGestureController.FingerprintGestureCallback f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$FingerprintGestureController$M_ZApqp96G6ZF2WdWrGDJ8Qsfck(FingerprintGestureController.FingerprintGestureCallback fingerprintGestureCallback, boolean bl) {
        this.f$0 = fingerprintGestureCallback;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        FingerprintGestureController.lambda$onGestureDetectionActiveChanged$0(this.f$0, this.f$1);
    }
}

