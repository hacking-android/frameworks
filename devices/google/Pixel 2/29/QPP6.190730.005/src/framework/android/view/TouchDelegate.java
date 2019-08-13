/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.ArrayMap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Map;

public class TouchDelegate {
    public static final int ABOVE = 1;
    public static final int BELOW = 2;
    public static final int TO_LEFT = 4;
    public static final int TO_RIGHT = 8;
    private Rect mBounds;
    @UnsupportedAppUsage
    private boolean mDelegateTargeted;
    private View mDelegateView;
    private int mSlop;
    private Rect mSlopBounds;
    private AccessibilityNodeInfo.TouchDelegateInfo mTouchDelegateInfo;

    public TouchDelegate(Rect rect, View view) {
        this.mBounds = rect;
        this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        rect = this.mSlopBounds = new Rect(rect);
        int n = this.mSlop;
        rect.inset(-n, -n);
        this.mDelegateView = view;
    }

    public AccessibilityNodeInfo.TouchDelegateInfo getTouchDelegateInfo() {
        if (this.mTouchDelegateInfo == null) {
            Rect rect;
            ArrayMap<Region, View> arrayMap = new ArrayMap<Region, View>(1);
            Rect rect2 = rect = this.mBounds;
            if (rect == null) {
                rect2 = new Rect();
            }
            arrayMap.put(new Region(rect2), this.mDelegateView);
            this.mTouchDelegateInfo = new AccessibilityNodeInfo.TouchDelegateInfo(arrayMap);
        }
        return this.mTouchDelegateInfo;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        int n;
        block8 : {
            int n2;
            int n3;
            block5 : {
                boolean bl3;
                int n4;
                block6 : {
                    block7 : {
                        n2 = (int)motionEvent.getX();
                        n3 = (int)motionEvent.getY();
                        bl = false;
                        n = 1;
                        n4 = 1;
                        bl2 = false;
                        int n5 = motionEvent.getActionMasked();
                        if (n5 == 0) break block5;
                        if (n5 == 1 || n5 == 2) break block6;
                        if (n5 == 3) break block7;
                        if (n5 == 5 || n5 == 6) break block6;
                        break block8;
                    }
                    bl = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                    break block8;
                }
                bl = bl3 = this.mDelegateTargeted;
                if (bl3) {
                    n = n4;
                    if (!this.mSlopBounds.contains(n2, n3)) {
                        n = 0;
                    }
                    bl = bl3;
                }
                break block8;
            }
            bl = this.mDelegateTargeted = this.mBounds.contains(n2, n3);
        }
        if (bl) {
            if (n != 0) {
                motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
            } else {
                n = this.mSlop;
                motionEvent.setLocation(-(n * 2), -(n * 2));
            }
            bl2 = this.mDelegateView.dispatchTouchEvent(motionEvent);
        }
        return bl2;
    }

    public boolean onTouchExplorationHoverEvent(MotionEvent motionEvent) {
        if (this.mBounds == null) {
            return false;
        }
        int n = (int)motionEvent.getX();
        int n2 = (int)motionEvent.getY();
        int n3 = 1;
        boolean bl = false;
        boolean bl2 = this.mBounds.contains(n, n2);
        int n4 = motionEvent.getActionMasked();
        if (n4 != 7) {
            if (n4 != 9) {
                if (n4 != 10) {
                    n4 = n3;
                } else {
                    this.mDelegateTargeted = true;
                    n4 = n3;
                }
            } else {
                this.mDelegateTargeted = bl2;
                n4 = n3;
            }
        } else if (bl2) {
            this.mDelegateTargeted = true;
            n4 = n3;
        } else {
            n4 = n3;
            if (this.mDelegateTargeted) {
                n4 = n3;
                if (!this.mSlopBounds.contains(n, n2)) {
                    n4 = 0;
                }
            }
        }
        if (this.mDelegateTargeted) {
            if (n4 != 0) {
                motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
            } else {
                this.mDelegateTargeted = false;
            }
            bl = this.mDelegateView.dispatchHoverEvent(motionEvent);
        }
        return bl;
    }
}

