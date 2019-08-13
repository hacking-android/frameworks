/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.view.accessibility.AccessibilityManager;

public final class _$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI
implements Runnable {
    private final /* synthetic */ AccessibilityManager.TouchExplorationStateChangeListener f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$AccessibilityManager$a0OtrjOl35tiW2vwyvAmY6_LiLI(AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener, boolean bl) {
        this.f$0 = touchExplorationStateChangeListener;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        AccessibilityManager.lambda$notifyTouchExplorationStateChanged$1(this.f$0, this.f$1);
    }
}

