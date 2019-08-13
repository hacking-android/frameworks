/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.view.accessibility.AccessibilityManager;

public final class _$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA
implements Runnable {
    private final /* synthetic */ AccessibilityManager.1 f$0;
    private final /* synthetic */ AccessibilityManager.AccessibilityServicesStateChangeListener f$1;

    public /* synthetic */ _$$Lambda$AccessibilityManager$1$o7fCplskH9NlBwJvkl6NoZ0L_BA(AccessibilityManager.1 var1_1, AccessibilityManager.AccessibilityServicesStateChangeListener accessibilityServicesStateChangeListener) {
        this.f$0 = var1_1;
        this.f$1 = accessibilityServicesStateChangeListener;
    }

    @Override
    public final void run() {
        this.f$0.lambda$notifyServicesStateChanged$0$AccessibilityManager$1(this.f$1);
    }
}

