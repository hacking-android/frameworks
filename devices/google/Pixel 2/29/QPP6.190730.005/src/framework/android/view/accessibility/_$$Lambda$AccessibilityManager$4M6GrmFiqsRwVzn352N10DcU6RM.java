/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.view.accessibility.AccessibilityManager;

public final class _$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM
implements Runnable {
    private final /* synthetic */ AccessibilityManager.HighTextContrastChangeListener f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$AccessibilityManager$4M6GrmFiqsRwVzn352N10DcU6RM(AccessibilityManager.HighTextContrastChangeListener highTextContrastChangeListener, boolean bl) {
        this.f$0 = highTextContrastChangeListener;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        AccessibilityManager.lambda$notifyHighTextContrastStateChanged$2(this.f$0, this.f$1);
    }
}

