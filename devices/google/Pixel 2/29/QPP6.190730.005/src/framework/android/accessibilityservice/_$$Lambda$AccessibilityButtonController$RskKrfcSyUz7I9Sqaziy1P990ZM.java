/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.AccessibilityButtonController;

public final class _$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM
implements Runnable {
    private final /* synthetic */ AccessibilityButtonController f$0;
    private final /* synthetic */ AccessibilityButtonController.AccessibilityButtonCallback f$1;
    private final /* synthetic */ boolean f$2;

    public /* synthetic */ _$$Lambda$AccessibilityButtonController$RskKrfcSyUz7I9Sqaziy1P990ZM(AccessibilityButtonController accessibilityButtonController, AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback, boolean bl) {
        this.f$0 = accessibilityButtonController;
        this.f$1 = accessibilityButtonCallback;
        this.f$2 = bl;
    }

    @Override
    public final void run() {
        this.f$0.lambda$dispatchAccessibilityButtonAvailabilityChanged$1$AccessibilityButtonController(this.f$1, this.f$2);
    }
}

