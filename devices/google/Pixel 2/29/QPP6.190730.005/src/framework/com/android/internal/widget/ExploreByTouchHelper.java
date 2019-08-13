/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.IntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

public abstract class ExploreByTouchHelper
extends View.AccessibilityDelegate {
    private static final String DEFAULT_CLASS_NAME = View.class.getName();
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final Context mContext;
    private int mFocusedVirtualViewId = Integer.MIN_VALUE;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private ExploreByTouchNodeProvider mNodeProvider;
    private IntArray mTempArray;
    private int[] mTempGlobalRect;
    private Rect mTempParentRect;
    private Rect mTempScreenRect;
    private Rect mTempVisibleRect;
    private final View mView;

    public ExploreByTouchHelper(View view) {
        if (view != null) {
            this.mView = view;
            this.mContext = view.getContext();
            this.mManager = (AccessibilityManager)this.mContext.getSystemService("accessibility");
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    private boolean clearAccessibilityFocus(int n) {
        if (this.isAccessibilityFocused(n)) {
            this.mFocusedVirtualViewId = Integer.MIN_VALUE;
            this.mView.invalidate();
            this.sendEventForVirtualView(n, 65536);
            return true;
        }
        return false;
    }

    private AccessibilityEvent createEvent(int n, int n2) {
        if (n != -1) {
            return this.createEventForChild(n, n2);
        }
        return this.createEventForHost(n2);
    }

    private AccessibilityEvent createEventForChild(int n, int n2) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(n2);
        accessibilityEvent.setEnabled(true);
        accessibilityEvent.setClassName(DEFAULT_CLASS_NAME);
        this.onPopulateEventForVirtualView(n, accessibilityEvent);
        if (accessibilityEvent.getText().isEmpty() && accessibilityEvent.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEvent.setPackageName(this.mView.getContext().getPackageName());
        accessibilityEvent.setSource(this.mView, n);
        return accessibilityEvent;
    }

    private AccessibilityEvent createEventForHost(int n) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(n);
        this.mView.onInitializeAccessibilityEvent(accessibilityEvent);
        this.onPopulateEventForHost(accessibilityEvent);
        return accessibilityEvent;
    }

    private AccessibilityNodeInfo createNode(int n) {
        if (n != -1) {
            return this.createNodeForChild(n);
        }
        return this.createNodeForHost();
    }

    private AccessibilityNodeInfo createNodeForChild(int n) {
        this.ensureTempRects();
        Rect rect = this.mTempParentRect;
        int[] arrn = this.mTempGlobalRect;
        Rect rect2 = this.mTempScreenRect;
        AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
        accessibilityNodeInfo.setEnabled(true);
        accessibilityNodeInfo.setClassName(DEFAULT_CLASS_NAME);
        accessibilityNodeInfo.setBoundsInParent(INVALID_PARENT_BOUNDS);
        this.onPopulateNodeForVirtualView(n, accessibilityNodeInfo);
        if (accessibilityNodeInfo.getText() == null && accessibilityNodeInfo.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfo.getBoundsInParent(rect);
        if (!rect.equals(INVALID_PARENT_BOUNDS)) {
            int n2 = accessibilityNodeInfo.getActions();
            if ((n2 & 64) == 0) {
                if ((n2 & 128) == 0) {
                    accessibilityNodeInfo.setPackageName(this.mView.getContext().getPackageName());
                    accessibilityNodeInfo.setSource(this.mView, n);
                    accessibilityNodeInfo.setParent(this.mView);
                    if (this.mFocusedVirtualViewId == n) {
                        accessibilityNodeInfo.setAccessibilityFocused(true);
                        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
                    } else {
                        accessibilityNodeInfo.setAccessibilityFocused(false);
                        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
                    }
                    if (this.intersectVisibleToUser(rect)) {
                        accessibilityNodeInfo.setVisibleToUser(true);
                        accessibilityNodeInfo.setBoundsInParent(rect);
                    }
                    this.mView.getLocationOnScreen(arrn);
                    n2 = arrn[0];
                    n = arrn[1];
                    rect2.set(rect);
                    rect2.offset(n2, n);
                    accessibilityNodeInfo.setBoundsInScreen(rect2);
                    return accessibilityNodeInfo;
                }
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
    }

    private AccessibilityNodeInfo createNodeForHost() {
        AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain(this.mView);
        this.mView.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        int n = accessibilityNodeInfo.getChildCount();
        this.onPopulateNodeForHost(accessibilityNodeInfo);
        IntArray intArray = this.mTempArray;
        if (intArray == null) {
            this.mTempArray = new IntArray();
        } else {
            intArray.clear();
        }
        intArray = this.mTempArray;
        this.getVisibleVirtualViews(intArray);
        if (n > 0 && intArray.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int n2 = intArray.size();
        for (n = 0; n < n2; ++n) {
            accessibilityNodeInfo.addChild(this.mView, intArray.get(n));
        }
        return accessibilityNodeInfo;
    }

    private void ensureTempRects() {
        this.mTempGlobalRect = new int[2];
        this.mTempParentRect = new Rect();
        this.mTempScreenRect = new Rect();
    }

    private boolean intersectVisibleToUser(Rect rect) {
        if (rect != null && !rect.isEmpty()) {
            if (this.mView.getWindowVisibility() != 0) {
                return false;
            }
            Object object = this.mView.getParent();
            while (object instanceof View) {
                if (!(((View)(object = (View)object)).getAlpha() <= 0.0f) && ((View)object).getVisibility() == 0) {
                    object = ((View)object).getParent();
                    continue;
                }
                return false;
            }
            if (object == null) {
                return false;
            }
            if (this.mTempVisibleRect == null) {
                this.mTempVisibleRect = new Rect();
            }
            if (!this.mView.getLocalVisibleRect((Rect)(object = this.mTempVisibleRect))) {
                return false;
            }
            return rect.intersect((Rect)object);
        }
        return false;
    }

    private boolean isAccessibilityFocused(int n) {
        boolean bl = this.mFocusedVirtualViewId == n;
        return bl;
    }

    private boolean manageFocusForChild(int n, int n2) {
        if (n2 != 64) {
            if (n2 != 128) {
                return false;
            }
            return this.clearAccessibilityFocus(n);
        }
        return this.requestAccessibilityFocus(n);
    }

    private boolean performAction(int n, int n2, Bundle bundle) {
        if (n != -1) {
            return this.performActionForChild(n, n2, bundle);
        }
        return this.performActionForHost(n2, bundle);
    }

    private boolean performActionForChild(int n, int n2, Bundle bundle) {
        if (n2 != 64 && n2 != 128) {
            return this.onPerformActionForVirtualView(n, n2, bundle);
        }
        return this.manageFocusForChild(n, n2);
    }

    private boolean performActionForHost(int n, Bundle bundle) {
        return this.mView.performAccessibilityAction(n, bundle);
    }

    private boolean requestAccessibilityFocus(int n) {
        AccessibilityManager accessibilityManager = (AccessibilityManager)this.mContext.getSystemService("accessibility");
        if (this.mManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            if (!this.isAccessibilityFocused(n)) {
                int n2 = this.mFocusedVirtualViewId;
                if (n2 != Integer.MIN_VALUE) {
                    this.sendEventForVirtualView(n2, 65536);
                }
                this.mFocusedVirtualViewId = n;
                this.mView.invalidate();
                this.sendEventForVirtualView(n, 32768);
                return true;
            }
            return false;
        }
        return false;
    }

    private void updateHoveredVirtualView(int n) {
        if (this.mHoveredVirtualViewId == n) {
            return;
        }
        int n2 = this.mHoveredVirtualViewId;
        this.mHoveredVirtualViewId = n;
        this.sendEventForVirtualView(n, 128);
        this.sendEventForVirtualView(n2, 256);
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        boolean bl = this.mManager.isEnabled();
        boolean bl2 = false;
        if (bl && this.mManager.isTouchExplorationEnabled()) {
            int n = motionEvent.getAction();
            if (n != 7 && n != 9) {
                if (n != 10) {
                    return false;
                }
                if (this.mHoveredVirtualViewId != Integer.MIN_VALUE) {
                    this.updateHoveredVirtualView(Integer.MIN_VALUE);
                    return true;
                }
                return false;
            }
            n = this.getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
            this.updateHoveredVirtualView(n);
            if (n != Integer.MIN_VALUE) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new ExploreByTouchNodeProvider();
        }
        return this.mNodeProvider;
    }

    public int getFocusedVirtualView() {
        return this.mFocusedVirtualViewId;
    }

    protected abstract int getVirtualViewAt(float var1, float var2);

    protected abstract void getVisibleVirtualViews(IntArray var1);

    public void invalidateRoot() {
        this.invalidateVirtualView(-1, 1);
    }

    public void invalidateVirtualView(int n) {
        this.invalidateVirtualView(n, 0);
    }

    public void invalidateVirtualView(int n, int n2) {
        ViewParent viewParent;
        if (n != Integer.MIN_VALUE && this.mManager.isEnabled() && (viewParent = this.mView.getParent()) != null) {
            AccessibilityEvent accessibilityEvent = this.createEvent(n, 2048);
            accessibilityEvent.setContentChangeTypes(n2);
            viewParent.requestSendAccessibilityEvent(this.mView, accessibilityEvent);
        }
    }

    protected abstract boolean onPerformActionForVirtualView(int var1, int var2, Bundle var3);

    protected void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
    }

    protected abstract void onPopulateEventForVirtualView(int var1, AccessibilityEvent var2);

    protected void onPopulateNodeForHost(AccessibilityNodeInfo accessibilityNodeInfo) {
    }

    protected abstract void onPopulateNodeForVirtualView(int var1, AccessibilityNodeInfo var2);

    public boolean sendEventForVirtualView(int n, int n2) {
        if (n != Integer.MIN_VALUE && this.mManager.isEnabled()) {
            ViewParent viewParent = this.mView.getParent();
            if (viewParent == null) {
                return false;
            }
            AccessibilityEvent accessibilityEvent = this.createEvent(n, n2);
            return viewParent.requestSendAccessibilityEvent(this.mView, accessibilityEvent);
        }
        return false;
    }

    private class ExploreByTouchNodeProvider
    extends AccessibilityNodeProvider {
        private ExploreByTouchNodeProvider() {
        }

        @Override
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int n) {
            return ExploreByTouchHelper.this.createNode(n);
        }

        @Override
        public boolean performAction(int n, int n2, Bundle bundle) {
            return ExploreByTouchHelper.this.performAction(n, n2, bundle);
        }
    }

}

