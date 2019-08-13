/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public interface ViewParent {
    public void bringChildToFront(View var1);

    public boolean canResolveLayoutDirection();

    public boolean canResolveTextAlignment();

    public boolean canResolveTextDirection();

    public void childDrawableStateChanged(View var1);

    public void childHasTransientStateChanged(View var1, boolean var2);

    public void clearChildFocus(View var1);

    public void createContextMenu(ContextMenu var1);

    public View focusSearch(View var1, int var2);

    public void focusableViewAvailable(View var1);

    public boolean getChildVisibleRect(View var1, Rect var2, Point var3);

    public int getLayoutDirection();

    public ViewParent getParent();

    public ViewParent getParentForAccessibility();

    public int getTextAlignment();

    public int getTextDirection();

    @Deprecated
    public void invalidateChild(View var1, Rect var2);

    @Deprecated
    public ViewParent invalidateChildInParent(int[] var1, Rect var2);

    public boolean isLayoutDirectionResolved();

    public boolean isLayoutRequested();

    public boolean isTextAlignmentResolved();

    public boolean isTextDirectionResolved();

    public View keyboardNavigationClusterSearch(View var1, int var2);

    public void notifySubtreeAccessibilityStateChanged(View var1, View var2, int var3);

    default public void onDescendantInvalidated(View view, View view2) {
        if (this.getParent() != null) {
            this.getParent().onDescendantInvalidated(view, view2);
        }
    }

    public boolean onNestedFling(View var1, float var2, float var3, boolean var4);

    public boolean onNestedPreFling(View var1, float var2, float var3);

    public boolean onNestedPrePerformAccessibilityAction(View var1, int var2, Bundle var3);

    public void onNestedPreScroll(View var1, int var2, int var3, int[] var4);

    public void onNestedScroll(View var1, int var2, int var3, int var4, int var5);

    public void onNestedScrollAccepted(View var1, View var2, int var3);

    public boolean onStartNestedScroll(View var1, View var2, int var3);

    public void onStopNestedScroll(View var1);

    public void recomputeViewAttributes(View var1);

    public void requestChildFocus(View var1, View var2);

    public boolean requestChildRectangleOnScreen(View var1, Rect var2, boolean var3);

    public void requestDisallowInterceptTouchEvent(boolean var1);

    public void requestFitSystemWindows();

    public void requestLayout();

    public boolean requestSendAccessibilityEvent(View var1, AccessibilityEvent var2);

    public void requestTransparentRegion(View var1);

    public boolean showContextMenuForChild(View var1);

    public boolean showContextMenuForChild(View var1, float var2, float var3);

    public ActionMode startActionModeForChild(View var1, ActionMode.Callback var2);

    public ActionMode startActionModeForChild(View var1, ActionMode.Callback var2, int var3);

    default public void subtractObscuredTouchableRegion(Region region, View view) {
    }
}

