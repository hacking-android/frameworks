/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.accessibilityservice.AccessibilityButtonController;

public final class _$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU
implements Runnable {
    private final /* synthetic */ AccessibilityButtonController f$0;
    private final /* synthetic */ AccessibilityButtonController.AccessibilityButtonCallback f$1;

    public /* synthetic */ _$$Lambda$AccessibilityButtonController$b_UAM9QJWcH4KQOC_odiN0t_boU(AccessibilityButtonController accessibilityButtonController, AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback) {
        this.f$0 = accessibilityButtonController;
        this.f$1 = accessibilityButtonCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$dispatchAccessibilityButtonClicked$0$AccessibilityButtonController(this.f$1);
    }
}

