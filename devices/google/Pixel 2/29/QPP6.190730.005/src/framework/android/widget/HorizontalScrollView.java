/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.R;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import java.util.ArrayList;

public class HorizontalScrollView
extends FrameLayout {
    private static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    private static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final String TAG = "HorizontalScrollView";
    private int mActivePointerId = -1;
    @UnsupportedAppUsage
    private View mChildToScrollTo = null;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124053130L)
    private EdgeEffect mEdgeGlowLeft = new EdgeEffect(this.getContext());
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124052619L)
    private EdgeEffect mEdgeGlowRight = new EdgeEffect(this.getContext());
    @ViewDebug.ExportedProperty(category="layout")
    private boolean mFillViewport;
    private float mHorizontalScrollFactor;
    @UnsupportedAppUsage
    private boolean mIsBeingDragged = false;
    private boolean mIsLayoutDirty = true;
    @UnsupportedAppUsage
    private int mLastMotionX;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    @UnsupportedAppUsage
    private int mOverflingDistance;
    @UnsupportedAppUsage
    private int mOverscrollDistance;
    private SavedState mSavedState;
    @UnsupportedAppUsage
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled = true;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    @UnsupportedAppUsage
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollView(Context context) {
        this(context, null);
    }

    public HorizontalScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843603);
    }

    public HorizontalScrollView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public HorizontalScrollView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.initScrollView();
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.HorizontalScrollView, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.HorizontalScrollView, attributeSet, typedArray, n, n2);
        this.setFillViewport(typedArray.getBoolean(0, false));
        typedArray.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            this.setRevealOnFocusHint(false);
        }
    }

    private boolean canScroll() {
        boolean bl = false;
        View view = this.getChildAt(0);
        if (view != null) {
            int n = view.getWidth();
            if (this.getWidth() < this.mPaddingLeft + n + this.mPaddingRight) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private static int clamp(int n, int n2, int n3) {
        if (n2 < n3 && n >= 0) {
            if (n2 + n > n3) {
                return n3 - n2;
            }
            return n;
        }
        return 0;
    }

    private void doScrollX(int n) {
        if (n != 0) {
            if (this.mSmoothScrollingEnabled) {
                this.smoothScrollBy(n, 0);
            } else {
                this.scrollBy(n, 0);
            }
        }
    }

    private View findFocusableViewInBounds(boolean bl, int n, int n2) {
        ArrayList<View> arrayList = this.getFocusables(2);
        View view = null;
        boolean bl2 = false;
        int n3 = arrayList.size();
        for (int i = 0; i < n3; ++i) {
            View view2 = (View)arrayList.get(i);
            int n4 = view2.getLeft();
            int n5 = view2.getRight();
            View view3 = view;
            boolean bl3 = bl2;
            if (n < n5) {
                view3 = view;
                bl3 = bl2;
                if (n4 < n2) {
                    boolean bl4 = false;
                    boolean bl5 = n < n4 && n5 < n2;
                    if (view == null) {
                        view3 = view2;
                        bl3 = bl5;
                    } else {
                        if (bl && n4 < view.getLeft() || !bl && n5 > view.getRight()) {
                            bl4 = true;
                        }
                        if (bl2) {
                            view3 = view;
                            bl3 = bl2;
                            if (bl5) {
                                view3 = view;
                                bl3 = bl2;
                                if (bl4) {
                                    view3 = view2;
                                    bl3 = bl2;
                                }
                            }
                        } else if (bl5) {
                            view3 = view2;
                            bl3 = true;
                        } else {
                            view3 = view;
                            bl3 = bl2;
                            if (bl4) {
                                view3 = view2;
                                bl3 = bl2;
                            }
                        }
                    }
                }
            }
            view = view3;
            bl2 = bl3;
        }
        return view;
    }

    private View findFocusableViewInMyBounds(boolean bl, int n, View view) {
        int n2 = this.getHorizontalFadingEdgeLength() / 2;
        int n3 = n + n2;
        n = this.getWidth() + n - n2;
        if (view != null && view.getLeft() < n && view.getRight() > n3) {
            return view;
        }
        return this.findFocusableViewInBounds(bl, n3, n);
    }

    private int getScrollRange() {
        int n = 0;
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n = Math.max(0, view.getWidth() - (this.getWidth() - this.mPaddingLeft - this.mPaddingRight));
        }
        return n;
    }

    private boolean inChild(int n, int n2) {
        int n3 = this.getChildCount();
        boolean bl = false;
        if (n3 > 0) {
            n3 = this.mScrollX;
            View view = this.getChildAt(0);
            if (n2 >= view.getTop() && n2 < view.getBottom() && n >= view.getLeft() - n3 && n < view.getRight() - n3) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void initScrollView() {
        this.mScroller = new OverScroller(this.getContext());
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private boolean isOffScreen(View view) {
        return this.isWithinDeltaOfScreen(view, 0) ^ true;
    }

    private static boolean isViewDescendantOf(View object, View view) {
        boolean bl = true;
        if (object == view) {
            return true;
        }
        if (!((object = ((View)object).getParent()) instanceof ViewGroup) || !HorizontalScrollView.isViewDescendantOf((View)object, view)) {
            bl = false;
        }
        return bl;
    }

    private boolean isWithinDeltaOfScreen(View view, int n) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        boolean bl = this.mTempRect.right + n >= this.getScrollX() && this.mTempRect.left - n <= this.getScrollX() + this.getWidth();
        return bl;
    }

    private void onSecondaryPointerUp(MotionEvent object) {
        int n = (((MotionEvent)object).getAction() & 65280) >> 8;
        if (((MotionEvent)object).getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionX = (int)((MotionEvent)object).getX(n);
            this.mActivePointerId = ((MotionEvent)object).getPointerId(n);
            object = this.mVelocityTracker;
            if (object != null) {
                ((VelocityTracker)object).clear();
            }
        }
    }

    @UnsupportedAppUsage
    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private boolean scrollAndFocus(int n, int n2, int n3) {
        View view;
        boolean bl = true;
        int n4 = this.getWidth();
        int n5 = this.getScrollX();
        n4 = n5 + n4;
        boolean bl2 = n == 17;
        View view2 = view = this.findFocusableViewInBounds(bl2, n2, n3);
        if (view == null) {
            view2 = this;
        }
        if (n2 >= n5 && n3 <= n4) {
            bl2 = false;
        } else {
            n2 = bl2 ? (n2 -= n5) : n3 - n4;
            this.doScrollX(n2);
            bl2 = bl;
        }
        if (view2 != this.findFocus()) {
            view2.requestFocus(n);
        }
        return bl2;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n = this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (n != 0) {
            this.scrollBy(n, 0);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean bl) {
        int n = this.computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean bl2 = n != 0;
        if (bl2) {
            if (bl) {
                this.scrollBy(n, 0);
            } else {
                this.smoothScrollBy(n, 0);
            }
        }
        return bl2;
    }

    private boolean shouldDisplayEdgeEffects() {
        boolean bl = this.getOverScrollMode() != 2;
        return bl;
    }

    @Override
    public void addView(View view) {
        if (this.getChildCount() <= 0) {
            super.addView(view);
            return;
        }
        throw new IllegalStateException("HorizontalScrollView can host only one direct child");
    }

    @Override
    public void addView(View view, int n) {
        if (this.getChildCount() <= 0) {
            super.addView(view, n);
            return;
        }
        throw new IllegalStateException("HorizontalScrollView can host only one direct child");
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() <= 0) {
            super.addView(view, n, layoutParams);
            return;
        }
        throw new IllegalStateException("HorizontalScrollView can host only one direct child");
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() <= 0) {
            super.addView(view, layoutParams);
            return;
        }
        throw new IllegalStateException("HorizontalScrollView can host only one direct child");
    }

    public boolean arrowScroll(int n) {
        View view;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus(this, view2, n);
        int n2 = this.getMaxScrollAmount();
        if (view != null && this.isWithinDeltaOfScreen(view, n2)) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScrollX(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            view.requestFocus(n);
        } else {
            int n3;
            int n4 = n2;
            if (n == 17 && this.getScrollX() < n4) {
                n3 = this.getScrollX();
            } else {
                n3 = n4;
                if (n == 66) {
                    n3 = n4;
                    if (this.getChildCount() > 0) {
                        int n5 = this.getChildAt(0).getRight();
                        int n6 = this.getScrollX() + this.getWidth();
                        n3 = n4;
                        if (n5 - n6 < n2) {
                            n3 = n5 - n6;
                        }
                    }
                }
            }
            if (n3 == 0) {
                return false;
            }
            n = n == 66 ? n3 : -n3;
            this.doScrollX(n);
        }
        if (view2 != null && view2.isFocused() && this.isOffScreen(view2)) {
            n = this.getDescendantFocusability();
            this.setDescendantFocusability(131072);
            this.requestFocus();
            this.setDescendantFocusability(n);
        }
        return true;
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return Math.max(0, super.computeHorizontalScrollOffset());
    }

    @Override
    protected int computeHorizontalScrollRange() {
        int n = this.getChildCount();
        int n2 = this.getWidth() - this.mPaddingLeft - this.mPaddingRight;
        if (n == 0) {
            return n2;
        }
        n = this.getChildAt(0).getRight();
        int n3 = this.mScrollX;
        int n4 = Math.max(0, n - n2);
        if (n3 < 0) {
            n2 = n - n3;
        } else {
            n2 = n;
            if (n3 > n4) {
                n2 = n + (n3 - n4);
            }
        }
        return n2;
    }

    @Override
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int n = this.mScrollX;
            int n2 = this.mScrollY;
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                boolean bl;
                int n5 = this.getScrollRange();
                int n6 = this.getOverScrollMode();
                boolean bl2 = bl = true;
                if (n6 != 0) {
                    bl2 = n6 == 1 && n5 > 0 ? bl : false;
                }
                this.overScrollBy(n3 - n, n4 - n2, n, n2, n5, 0, this.mOverflingDistance, 0, false);
                this.onScrollChanged(this.mScrollX, this.mScrollY, n, n2);
                if (bl2) {
                    if (n3 < 0 && n >= 0) {
                        this.mEdgeGlowLeft.onAbsorb((int)this.mScroller.getCurrVelocity());
                    } else if (n3 > n5 && n <= n5) {
                        this.mEdgeGlowRight.onAbsorb((int)this.mScroller.getCurrVelocity());
                    }
                }
            }
            if (!this.awakenScrollBars()) {
                this.postInvalidateOnAnimation();
            }
        }
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        int n = this.getWidth();
        int n2 = this.getScrollX();
        int n3 = n2 + n;
        int n4 = this.getHorizontalFadingEdgeLength();
        int n5 = n2;
        if (rect.left > 0) {
            n5 = n2 + n4;
        }
        n2 = n3;
        if (rect.right < this.getChildAt(0).getWidth()) {
            n2 = n3 - n4;
        }
        n4 = 0;
        if (rect.right > n2 && rect.left > n5) {
            n3 = rect.width() > n ? 0 + (rect.left - n5) : 0 + (rect.right - n2);
            n3 = Math.min(n3, this.getChildAt(0).getRight() - n2);
        } else {
            n3 = n4;
            if (rect.left < n5) {
                n3 = n4;
                if (rect.right < n2) {
                    n3 = rect.width() > n ? 0 - (n2 - rect.right) : 0 - (n5 - rect.left);
                    n3 = Math.max(n3, -this.getScrollX());
                }
            }
        }
        return n3;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean bl = super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
        return bl;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.shouldDisplayEdgeEffects()) {
            int n;
            int n2;
            int n3 = this.mScrollX;
            if (!this.mEdgeGlowLeft.isFinished()) {
                n = canvas.save();
                n2 = this.getHeight() - this.mPaddingTop - this.mPaddingBottom;
                canvas.rotate(270.0f);
                canvas.translate(-n2 + this.mPaddingTop, Math.min(0, n3));
                this.mEdgeGlowLeft.setSize(n2, this.getWidth());
                if (this.mEdgeGlowLeft.draw(canvas)) {
                    this.postInvalidateOnAnimation();
                }
                canvas.restoreToCount(n);
            }
            if (!this.mEdgeGlowRight.isFinished()) {
                n2 = canvas.save();
                n = this.getWidth();
                int n4 = this.getHeight();
                int n5 = this.mPaddingTop;
                int n6 = this.mPaddingBottom;
                canvas.rotate(90.0f);
                canvas.translate(-this.mPaddingTop, -(Math.max(this.getScrollRange(), n3) + n));
                this.mEdgeGlowRight.setSize(n4 - n5 - n6, n);
                if (this.mEdgeGlowRight.draw(canvas)) {
                    this.postInvalidateOnAnimation();
                }
                canvas.restoreToCount(n2);
            }
        }
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:fillViewPort", this.mFillViewport);
    }

    public boolean executeKeyEvent(KeyEvent object) {
        boolean bl;
        this.mTempRect.setEmpty();
        boolean bl2 = this.canScroll();
        int n = 66;
        if (!bl2) {
            boolean bl3 = this.isFocused();
            bl2 = false;
            if (bl3) {
                View view = this.findFocus();
                object = view;
                if (view == this) {
                    object = null;
                }
                if ((object = FocusFinder.getInstance().findNextFocus(this, (View)object, 66)) != null && object != this && ((View)object).requestFocus(66)) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }
        bl2 = bl = false;
        if (((KeyEvent)object).getAction() == 0) {
            int n2 = ((KeyEvent)object).getKeyCode();
            if (n2 != 21) {
                if (n2 != 22) {
                    if (n2 != 62) {
                        bl2 = bl;
                    } else {
                        if (((KeyEvent)object).isShiftPressed()) {
                            n = 17;
                        }
                        this.pageScroll(n);
                        bl2 = bl;
                    }
                } else {
                    bl2 = !((KeyEvent)object).isAltPressed() ? this.arrowScroll(66) : this.fullScroll(66);
                }
            } else {
                bl2 = !((KeyEvent)object).isAltPressed() ? this.arrowScroll(17) : this.fullScroll(17);
            }
        }
        return bl2;
    }

    public void fling(int n) {
        if (this.getChildCount() > 0) {
            View view;
            int n2 = this.getWidth() - this.mPaddingRight - this.mPaddingLeft;
            boolean bl = false;
            int n3 = this.getChildAt(0).getWidth();
            this.mScroller.fling(this.mScrollX, this.mScrollY, n, 0, 0, Math.max(0, n3 - n2), 0, 0, n2 / 2, 0);
            if (n > 0) {
                bl = true;
            }
            View view2 = this.findFocus();
            View view3 = view = this.findFocusableViewInMyBounds(bl, this.mScroller.getFinalX(), view2);
            if (view == null) {
                view3 = this;
            }
            if (view3 != view2) {
                n = bl ? 66 : 17;
                view3.requestFocus(n);
            }
            this.postInvalidateOnAnimation();
        }
    }

    public boolean fullScroll(int n) {
        boolean bl = n == 66;
        int n2 = this.getWidth();
        Object object = this.mTempRect;
        ((Rect)object).left = 0;
        ((Rect)object).right = n2;
        if (bl && this.getChildCount() > 0) {
            object = this.getChildAt(0);
            this.mTempRect.right = ((View)object).getRight();
            object = this.mTempRect;
            ((Rect)object).left = ((Rect)object).right - n2;
        }
        return this.scrollAndFocus(n, this.mTempRect.left, this.mTempRect.right);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return HorizontalScrollView.class.getName();
    }

    public int getLeftEdgeEffectColor() {
        return this.mEdgeGlowLeft.getColor();
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getHorizontalFadingEdgeLength();
        if (this.mScrollX < n) {
            return (float)this.mScrollX / (float)n;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int)((float)(this.mRight - this.mLeft) * 0.5f);
    }

    public int getRightEdgeEffectColor() {
        return this.mEdgeGlowRight.getColor();
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getHorizontalFadingEdgeLength();
        int n2 = this.getWidth();
        int n3 = this.mPaddingRight;
        n3 = this.getChildAt(0).getRight() - this.mScrollX - (n2 - n3);
        if (n3 < n) {
            return (float)n3 / (float)n;
        }
        return 1.0f;
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    @Override
    protected void measureChild(View view, int n, int n2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int n3 = this.mPaddingLeft;
        int n4 = this.mPaddingRight;
        view.measure(View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(n) - (n3 + n4)), 0), HorizontalScrollView.getChildMeasureSpec(n2, this.mPaddingTop + this.mPaddingBottom, layoutParams.height));
    }

    @Override
    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n5 = HorizontalScrollView.getChildMeasureSpec(n3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4, marginLayoutParams.height);
        n3 = this.mPaddingLeft;
        int n6 = this.mPaddingRight;
        int n7 = marginLayoutParams.leftMargin;
        n4 = marginLayoutParams.rightMargin;
        view.measure(View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(n) - (n3 + n6 + n7 + n4 + n2)), 0), n5);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
            float f = motionEvent.isFromSource(2) ? ((motionEvent.getMetaState() & 1) != 0 ? -motionEvent.getAxisValue(9) : motionEvent.getAxisValue(10)) : (motionEvent.isFromSource(4194304) ? motionEvent.getAxisValue(26) : 0.0f);
            int n = Math.round(this.mHorizontalScrollFactor * f);
            if (n != 0) {
                int n2 = this.getScrollRange();
                int n3 = this.mScrollX;
                int n4 = n3 + n;
                if (n4 < 0) {
                    n = 0;
                } else {
                    n = n4;
                    if (n4 > n2) {
                        n = n2;
                    }
                }
                if (n != n3) {
                    super.scrollTo(n, this.mScrollY);
                    return true;
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        boolean bl = this.getScrollRange() > 0;
        accessibilityEvent.setScrollable(bl);
        accessibilityEvent.setScrollX(this.mScrollX);
        accessibilityEvent.setScrollY(this.mScrollY);
        accessibilityEvent.setMaxScrollX(this.getScrollRange());
        accessibilityEvent.setMaxScrollY(this.mScrollY);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int n = this.getScrollRange();
        if (n > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (this.isEnabled() && this.mScrollX > 0) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
            }
            if (this.isEnabled() && this.mScrollX < n) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent object) {
        int n = ((MotionEvent)object).getAction();
        if (n == 2 && this.mIsBeingDragged) {
            return true;
        }
        if (super.onInterceptTouchEvent((MotionEvent)object)) {
            return true;
        }
        if ((n &= 255) != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 5) {
                            if (n != 6) return this.mIsBeingDragged;
                            this.onSecondaryPointerUp((MotionEvent)object);
                            this.mLastMotionX = (int)((MotionEvent)object).getX(((MotionEvent)object).findPointerIndex(this.mActivePointerId));
                            return this.mIsBeingDragged;
                        } else {
                            n = ((MotionEvent)object).getActionIndex();
                            this.mLastMotionX = (int)((MotionEvent)object).getX(n);
                            this.mActivePointerId = ((MotionEvent)object).getPointerId(n);
                        }
                        return this.mIsBeingDragged;
                    }
                } else {
                    n = this.mActivePointerId;
                    if (n == -1) return this.mIsBeingDragged;
                    int n2 = ((MotionEvent)object).findPointerIndex(n);
                    if (n2 == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid pointerId=");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" in onInterceptTouchEvent");
                        Log.e(TAG, ((StringBuilder)object).toString());
                        return this.mIsBeingDragged;
                    } else {
                        n = (int)((MotionEvent)object).getX(n2);
                        if (Math.abs(n - this.mLastMotionX) <= this.mTouchSlop) return this.mIsBeingDragged;
                        this.mIsBeingDragged = true;
                        this.mLastMotionX = n;
                        this.initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement((MotionEvent)object);
                        if (this.mParent == null) return this.mIsBeingDragged;
                        this.mParent.requestDisallowInterceptTouchEvent(true);
                    }
                    return this.mIsBeingDragged;
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            if (!this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, this.getScrollRange(), 0, 0)) return this.mIsBeingDragged;
            this.postInvalidateOnAnimation();
            return this.mIsBeingDragged;
        }
        n = (int)((MotionEvent)object).getX();
        if (!this.inChild(n, (int)((MotionEvent)object).getY())) {
            this.mIsBeingDragged = false;
            this.recycleVelocityTracker();
            return this.mIsBeingDragged;
        } else {
            this.mLastMotionX = n;
            this.mActivePointerId = ((MotionEvent)object).getPointerId(0);
            this.initOrResetVelocityTracker();
            this.mVelocityTracker.addMovement((MotionEvent)object);
            this.mIsBeingDragged = true ^ this.mScroller.isFinished();
        }
        return this.mIsBeingDragged;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        Object object;
        int n6;
        if (this.getChildCount() > 0) {
            n6 = this.getChildAt(0).getMeasuredWidth();
            object = (FrameLayout.LayoutParams)this.getChildAt(0).getLayoutParams();
            int n7 = ((FrameLayout.LayoutParams)object).leftMargin;
            n5 = ((FrameLayout.LayoutParams)object).rightMargin;
            n5 = n7 + n5;
        } else {
            n6 = 0;
            n5 = 0;
        }
        bl = n6 > n3 - n - this.getPaddingLeftWithForeground() - this.getPaddingRightWithForeground() - n5;
        this.layoutChildren(n, n2, n3, n4, bl);
        this.mIsLayoutDirty = false;
        object = this.mChildToScrollTo;
        if (object != null && HorizontalScrollView.isViewDescendantOf((View)object, this)) {
            this.scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.isLaidOut()) {
            n2 = Math.max(0, n6 - (n3 - n - this.mPaddingLeft - this.mPaddingRight));
            if (this.mSavedState != null) {
                n = this.isLayoutRtl() ? n2 - this.mSavedState.scrollOffsetFromStart : this.mSavedState.scrollOffsetFromStart;
                this.mScrollX = n;
                this.mSavedState = null;
            } else if (this.isLayoutRtl()) {
                this.mScrollX = n2 - this.mScrollX;
            }
            if (this.mScrollX > n2) {
                this.mScrollX = n2;
            } else if (this.mScrollX < 0) {
                this.mScrollX = 0;
            }
        }
        this.scrollTo(this.mScrollX, this.mScrollY);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (!this.mFillViewport) {
            return;
        }
        if (View.MeasureSpec.getMode(n) == 0) {
            return;
        }
        if (this.getChildCount() > 0) {
            int n3;
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            if (this.getContext().getApplicationInfo().targetSdkVersion >= 23) {
                n3 = this.mPaddingLeft + this.mPaddingRight + layoutParams.leftMargin + layoutParams.rightMargin;
                n = this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                n3 = this.mPaddingLeft + this.mPaddingRight;
                n = this.mPaddingTop + this.mPaddingBottom;
            }
            n3 = this.getMeasuredWidth() - n3;
            if (view.getMeasuredWidth() < n3) {
                view.measure(View.MeasureSpec.makeMeasureSpec(n3, 1073741824), HorizontalScrollView.getChildMeasureSpec(n2, n, layoutParams.height));
            }
        }
    }

    @Override
    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        if (!this.mScroller.isFinished()) {
            int n3 = this.mScrollX;
            int n4 = this.mScrollY;
            this.mScrollX = n;
            this.mScrollY = n2;
            this.invalidateParentIfNeeded();
            this.onScrollChanged(this.mScrollX, this.mScrollY, n3, n4);
            if (bl) {
                this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, this.getScrollRange(), 0, 0);
            }
        } else {
            super.scrollTo(n, n2);
        }
        this.awakenScrollBars();
    }

    @Override
    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        if (n == 2) {
            n2 = 66;
        } else {
            n2 = n;
            if (n == 1) {
                n2 = 17;
            }
        }
        View view = rect == null ? FocusFinder.getInstance().findNextFocus(this, null, n2) : FocusFinder.getInstance().findNextFocusFromRect(this, rect, n2);
        if (view == null) {
            return false;
        }
        if (this.isOffScreen(view)) {
            return false;
        }
        return view.requestFocus(n2, rect);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (this.mContext.getApplicationInfo().targetSdkVersion <= 18) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.mSavedState = parcelable;
        this.requestLayout();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        if (this.mContext.getApplicationInfo().targetSdkVersion <= 18) {
            return super.onSaveInstanceState();
        }
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int n = this.isLayoutRtl() ? -this.mScrollX : this.mScrollX;
        savedState.scrollOffsetFromStart = n;
        return savedState;
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        View view = this.findFocus();
        if (view != null && this != view) {
            if (this.isWithinDeltaOfScreen(view, this.mRight - this.mLeft)) {
                view.getDrawingRect(this.mTempRect);
                this.offsetDescendantRectToMyCoords(view, this.mTempRect);
                this.doScrollX(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            }
            return;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent object) {
        block33 : {
            ViewParent viewParent;
            boolean bl;
            block30 : {
                int n;
                block31 : {
                    int n2;
                    int n3;
                    int n4;
                    int n5;
                    block36 : {
                        block35 : {
                            int n6;
                            block34 : {
                                block32 : {
                                    this.initVelocityTrackerIfNotExists();
                                    this.mVelocityTracker.addMovement((MotionEvent)object);
                                    n = ((MotionEvent)object).getAction() & 255;
                                    n6 = 0;
                                    if (n == 0) break block30;
                                    if (n == 1) break block31;
                                    if (n == 2) break block32;
                                    if (n != 3) {
                                        if (n == 6) {
                                            this.onSecondaryPointerUp((MotionEvent)object);
                                        }
                                    } else if (this.mIsBeingDragged && this.getChildCount() > 0) {
                                        if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, this.getScrollRange(), 0, 0)) {
                                            this.postInvalidateOnAnimation();
                                        }
                                        this.mActivePointerId = -1;
                                        this.mIsBeingDragged = false;
                                        this.recycleVelocityTracker();
                                        if (this.shouldDisplayEdgeEffects()) {
                                            this.mEdgeGlowLeft.onRelease();
                                            this.mEdgeGlowRight.onRelease();
                                        }
                                    }
                                    break block33;
                                }
                                n5 = ((MotionEvent)object).findPointerIndex(this.mActivePointerId);
                                if (n5 != -1) break block34;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Invalid pointerId=");
                                ((StringBuilder)object).append(this.mActivePointerId);
                                ((StringBuilder)object).append(" in onTouchEvent");
                                Log.e(TAG, ((StringBuilder)object).toString());
                                break block33;
                            }
                            n3 = (int)((MotionEvent)object).getX(n5);
                            n = this.mLastMotionX - n3;
                            if (!this.mIsBeingDragged && Math.abs(n) > this.mTouchSlop) {
                                ViewParent viewParent2 = this.getParent();
                                if (viewParent2 != null) {
                                    viewParent2.requestDisallowInterceptTouchEvent(true);
                                }
                                this.mIsBeingDragged = true;
                                n = n > 0 ? (n -= this.mTouchSlop) : (n += this.mTouchSlop);
                            }
                            if (!this.mIsBeingDragged) break block33;
                            this.mLastMotionX = n3;
                            n2 = this.mScrollX;
                            n3 = this.mScrollY;
                            n4 = this.getScrollRange();
                            int n7 = this.getOverScrollMode();
                            if (n7 == 0) break block35;
                            n3 = n6;
                            if (n7 != 1) break block36;
                            n3 = n6;
                            if (n4 <= 0) break block36;
                        }
                        n3 = 1;
                    }
                    if (this.overScrollBy(n, 0, this.mScrollX, 0, n4, 0, this.mOverscrollDistance, 0, true)) {
                        this.mVelocityTracker.clear();
                    }
                    if (n3 != 0) {
                        n3 = n2 + n;
                        if (n3 < 0) {
                            this.mEdgeGlowLeft.onPull((float)n / (float)this.getWidth(), 1.0f - ((MotionEvent)object).getY(n5) / (float)this.getHeight());
                            if (!this.mEdgeGlowRight.isFinished()) {
                                this.mEdgeGlowRight.onRelease();
                            }
                        } else if (n3 > n4) {
                            this.mEdgeGlowRight.onPull((float)n / (float)this.getWidth(), ((MotionEvent)object).getY(n5) / (float)this.getHeight());
                            if (!this.mEdgeGlowLeft.isFinished()) {
                                this.mEdgeGlowLeft.onRelease();
                            }
                        }
                        if (!(!this.shouldDisplayEdgeEffects() || this.mEdgeGlowLeft.isFinished() && this.mEdgeGlowRight.isFinished())) {
                            this.postInvalidateOnAnimation();
                        }
                    }
                    break block33;
                }
                if (this.mIsBeingDragged) {
                    object = this.mVelocityTracker;
                    ((VelocityTracker)object).computeCurrentVelocity(1000, this.mMaximumVelocity);
                    n = (int)((VelocityTracker)object).getXVelocity(this.mActivePointerId);
                    if (this.getChildCount() > 0) {
                        if (Math.abs(n) > this.mMinimumVelocity) {
                            this.fling(-n);
                        } else if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, this.getScrollRange(), 0, 0)) {
                            this.postInvalidateOnAnimation();
                        }
                    }
                    this.mActivePointerId = -1;
                    this.mIsBeingDragged = false;
                    this.recycleVelocityTracker();
                    if (this.shouldDisplayEdgeEffects()) {
                        this.mEdgeGlowLeft.onRelease();
                        this.mEdgeGlowRight.onRelease();
                    }
                }
                break block33;
            }
            if (this.getChildCount() == 0) {
                return false;
            }
            this.mIsBeingDragged = bl = this.mScroller.isFinished() ^ true;
            if (bl && (viewParent = this.getParent()) != null) {
                viewParent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.mLastMotionX = (int)((MotionEvent)object).getX();
            this.mActivePointerId = ((MotionEvent)object).getPointerId(0);
        }
        return true;
    }

    public boolean pageScroll(int n) {
        Object object;
        boolean bl = n == 66;
        int n2 = this.getWidth();
        if (bl) {
            this.mTempRect.left = this.getScrollX() + n2;
            if (this.getChildCount() > 0 && this.mTempRect.left + n2 > ((View)(object = this.getChildAt(0))).getRight()) {
                this.mTempRect.left = ((View)object).getRight() - n2;
            }
        } else {
            this.mTempRect.left = this.getScrollX() - n2;
            if (this.mTempRect.left < 0) {
                this.mTempRect.left = 0;
            }
        }
        object = this.mTempRect;
        ((Rect)object).right = ((Rect)object).left + n2;
        return this.scrollAndFocus(n, this.mTempRect.left, this.mTempRect.right);
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (n != 4096) {
            if (n != 8192 && n != 16908345) {
                if (n != 16908347) {
                    return false;
                }
            } else {
                if (!this.isEnabled()) {
                    return false;
                }
                n = this.getWidth();
                int n2 = this.mPaddingLeft;
                int n3 = this.mPaddingRight;
                if ((n = Math.max(0, this.mScrollX - (n - n2 - n3))) != this.mScrollX) {
                    this.smoothScrollTo(n, 0);
                    return true;
                }
                return false;
            }
        }
        if (!this.isEnabled()) {
            return false;
        }
        int n4 = this.getWidth();
        int n5 = this.mPaddingLeft;
        n = this.mPaddingRight;
        if ((n = Math.min(this.mScrollX + (n4 - n5 - n), this.getScrollRange())) != this.mScrollX) {
            this.smoothScrollTo(n, 0);
            return true;
        }
        return false;
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        if (view2 != null && view2.getRevealOnFocusHint()) {
            if (!this.mIsLayoutDirty) {
                this.scrollToChild(view2);
            } else {
                this.mChildToScrollTo = view2;
            }
        }
        super.requestChildFocus(view, view2);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.scrollToChildRect(rect, bl);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (bl) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    @Override
    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override
    public void scrollTo(int n, int n2) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n = HorizontalScrollView.clamp(n, this.getWidth() - this.mPaddingRight - this.mPaddingLeft, view.getWidth());
            n2 = HorizontalScrollView.clamp(n2, this.getHeight() - this.mPaddingBottom - this.mPaddingTop, view.getHeight());
            if (n != this.mScrollX || n2 != this.mScrollY) {
                super.scrollTo(n, n2);
            }
        }
    }

    public void setEdgeEffectColor(int n) {
        this.setLeftEdgeEffectColor(n);
        this.setRightEdgeEffectColor(n);
    }

    public void setFillViewport(boolean bl) {
        if (bl != this.mFillViewport) {
            this.mFillViewport = bl;
            this.requestLayout();
        }
    }

    public void setLeftEdgeEffectColor(int n) {
        this.mEdgeGlowLeft.setColor(n);
    }

    public void setRightEdgeEffectColor(int n) {
        this.mEdgeGlowRight.setColor(n);
    }

    public void setSmoothScrollingEnabled(boolean bl) {
        this.mSmoothScrollingEnabled = bl;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void smoothScrollBy(int n, int n2) {
        if (this.getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
            int n3 = this.getWidth();
            int n4 = this.mPaddingRight;
            n2 = this.mPaddingLeft;
            n4 = Math.max(0, this.getChildAt(0).getWidth() - (n3 - n4 - n2));
            n2 = this.mScrollX;
            n = Math.max(0, Math.min(n2 + n, n4));
            this.mScroller.startScroll(n2, this.mScrollY, n - n2, 0);
            this.postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.scrollBy(n, n2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int n, int n2) {
        this.smoothScrollBy(n - this.mScrollX, n2 - this.mScrollY);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int scrollOffsetFromStart;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollOffsetFromStart = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HorizontalScrollView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" scrollPosition=");
            stringBuilder.append(this.scrollOffsetFromStart);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.scrollOffsetFromStart);
        }

    }

}

