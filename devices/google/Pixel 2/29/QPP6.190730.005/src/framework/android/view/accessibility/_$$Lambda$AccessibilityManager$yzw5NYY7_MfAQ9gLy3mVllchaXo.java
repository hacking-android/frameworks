/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.view.accessibility.AccessibilityManager;

public final class _$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo
implements Runnable {
    private final /* synthetic */ AccessibilityManager.AccessibilityStateChangeListener f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$AccessibilityManager$yzw5NYY7_MfAQ9gLy3mVllchaXo(AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener, boolean bl) {
        this.f$0 = accessibilityStateChangeListener;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        AccessibilityManager.lambda$notifyAccessibilityStateChanged$0(this.f$0, this.f$1);
    }
}

