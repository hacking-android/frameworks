/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.view.View;
import android.view.accessibility.WeakSparseArray;

public final class AccessibilityNodeIdManager {
    private static AccessibilityNodeIdManager sIdManager;
    private WeakSparseArray<View> mIdsToViews = new WeakSparseArray();

    private AccessibilityNodeIdManager() {
    }

    public static AccessibilityNodeIdManager getInstance() {
        synchronized (AccessibilityNodeIdManager.class) {
            AccessibilityNodeIdManager accessibilityNodeIdManager;
            if (sIdManager == null) {
                sIdManager = accessibilityNodeIdManager = new AccessibilityNodeIdManager();
            }
            accessibilityNodeIdManager = sIdManager;
            return accessibilityNodeIdManager;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public View findView(int n) {
        View view;
        WeakSparseArray<View> weakSparseArray = this.mIdsToViews;
        synchronized (weakSparseArray) {
            view = this.mIdsToViews.get(n);
        }
        if (view == null) return null;
        if (!view.includeForAccessibility()) return null;
        return view;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerViewWithId(View view, int n) {
        WeakSparseArray<View> weakSparseArray = this.mIdsToViews;
        synchronized (weakSparseArray) {
            this.mIdsToViews.append(n, view);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterViewWithId(int n) {
        WeakSparseArray<View> weakSparseArray = this.mIdsToViews;
        synchronized (weakSparseArray) {
            this.mIdsToViews.remove(n);
            return;
        }
    }
}

