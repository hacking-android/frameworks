/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

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
import android.os.StrictMode;
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
import com.android.internal.R;
import java.util.ArrayList;

public class ScrollView
extends FrameLayout {
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final String TAG = "ScrollView";
    private int mActivePointerId = -1;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769715L)
    private View mChildToScrollTo = null;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769386L)
    private EdgeEffect mEdgeGlowBottom = new EdgeEffect(this.getContext());
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768600L)
    private EdgeEffect mEdgeGlowTop = new EdgeEffect(this.getContext());
    @ViewDebug.ExportedProperty(category="layout")
    private boolean mFillViewport;
    @UnsupportedAppUsage
    private StrictMode.Span mFlingStrictSpan = null;
    @UnsupportedAppUsage
    private boolean mIsBeingDragged = false;
    private boolean mIsLayoutDirty = true;
    @UnsupportedAppUsage
    private int mLastMotionY;
    @UnsupportedAppUsage
    private long mLastScroll;
    private int mMaximumVelocity;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124051125L)
    private int mMinimumVelocity;
    private int mNestedYOffset;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050903L)
    private int mOverflingDistance;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=124050903L)
    private int mOverscrollDistance;
    private SavedState mSavedState;
    private final int[] mScrollConsumed = new int[2];
    private final int[] mScrollOffset = new int[2];
    private StrictMode.Span mScrollStrictSpan = null;
    @UnsupportedAppUsage
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled = true;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    @UnsupportedAppUsage
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    public ScrollView(Context context) {
        this(context, null);
    }

    public ScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842880);
    }

    public ScrollView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ScrollView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.initScrollView();
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollView, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.ScrollView, attributeSet, typedArray, n, n2);
        this.setFillViewport(typedArray.getBoolean(0, false));
        typedArray.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            this.setRevealOnFocusHint(false);
        }
    }

    @UnsupportedAppUsage
    private boolean canScroll() {
        boolean bl = false;
        View view = this.getChildAt(0);
        if (view != null) {
            int n = view.getHeight();
            if (this.getHeight() < this.mPaddingTop + n + this.mPaddingBottom) {
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

    private void doScrollY(int n) {
        if (n != 0) {
            if (this.mSmoothScrollingEnabled) {
                this.smoothScrollBy(0, n);
            } else {
                this.scrollBy(0, n);
            }
        }
    }

    @UnsupportedAppUsage
    private void endDrag() {
        StrictMode.Span span;
        this.mIsBeingDragged = false;
        this.recycleVelocityTracker();
        if (this.shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        if ((span = this.mScrollStrictSpan) != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
    }

    private View findFocusableViewInBounds(boolean bl, int n, int n2) {
        ArrayList<View> arrayList = this.getFocusables(2);
        View view = null;
        boolean bl2 = false;
        int n3 = arrayList.size();
        for (int i = 0; i < n3; ++i) {
            View view2 = (View)arrayList.get(i);
            int n4 = view2.getTop();
            int n5 = view2.getBottom();
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
                        if (bl && n4 < view.getTop() || !bl && n5 > view.getBottom()) {
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

    private void flingWithNestedDispatch(int n) {
        boolean bl = !(this.mScrollY <= 0 && n <= 0 || this.mScrollY >= this.getScrollRange() && n >= 0);
        if (!this.dispatchNestedPreFling(0.0f, n)) {
            this.dispatchNestedFling(0.0f, n, bl);
            if (bl) {
                this.fling(n);
            }
        }
    }

    private int getScrollRange() {
        int n = 0;
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n = Math.max(0, view.getHeight() - (this.getHeight() - this.mPaddingBottom - this.mPaddingTop));
        }
        return n;
    }

    private boolean inChild(int n, int n2) {
        int n3 = this.getChildCount();
        boolean bl = false;
        if (n3 > 0) {
            n3 = this.mScrollY;
            View view = this.getChildAt(0);
            if (n2 >= view.getTop() - n3 && n2 < view.getBottom() - n3 && n >= view.getLeft() && n < view.getRight()) {
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
        this.mVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private boolean isOffScreen(View view) {
        return this.isWithinDeltaOfScreen(view, 0, this.getHeight()) ^ true;
    }

    private static boolean isViewDescendantOf(View object, View view) {
        boolean bl = true;
        if (object == view) {
            return true;
        }
        if (!((object = ((View)object).getParent()) instanceof ViewGroup) || !ScrollView.isViewDescendantOf((View)object, view)) {
            bl = false;
        }
        return bl;
    }

    private boolean isWithinDeltaOfScreen(View view, int n, int n2) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        boolean bl = this.mTempRect.bottom + n >= this.getScrollY() && this.mTempRect.top - n <= this.getScrollY() + n2;
        return bl;
    }

    private void onSecondaryPointerUp(MotionEvent object) {
        int n = (((MotionEvent)object).getAction() & 65280) >> 8;
        if (((MotionEvent)object).getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionY = (int)((MotionEvent)object).getY(n);
            this.mActivePointerId = ((MotionEvent)object).getPointerId(n);
            object = this.mVelocityTracker;
            if (object != null) {
                ((VelocityTracker)object).clear();
            }
        }
    }

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
        int n4 = this.getHeight();
        int n5 = this.getScrollY();
        n4 = n5 + n4;
        boolean bl2 = n == 33;
        View view2 = view = this.findFocusableViewInBounds(bl2, n2, n3);
        if (view == null) {
            view2 = this;
        }
        if (n2 >= n5 && n3 <= n4) {
            bl2 = false;
        } else {
            n2 = bl2 ? (n2 -= n5) : n3 - n4;
            this.doScrollY(n2);
            bl2 = bl;
        }
        if (view2 != this.findFocus()) {
            view2.requestFocus(n);
        }
        return bl2;
    }

    private boolean scrollToChildRect(Rect rect, boolean bl) {
        int n = this.computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean bl2 = n != 0;
        if (bl2) {
            if (bl) {
                this.scrollBy(0, n);
            } else {
                this.smoothScrollBy(0, n);
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
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    @Override
    public void addView(View view, int n) {
        if (this.getChildCount() <= 0) {
            super.addView(view, n);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() <= 0) {
            super.addView(view, n, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    @Override
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() <= 0) {
            super.addView(view, layoutParams);
            return;
        }
        throw new IllegalStateException("ScrollView can host only one direct child");
    }

    public boolean arrowScroll(int n) {
        View view;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus(this, view2, n);
        int n2 = this.getMaxScrollAmount();
        if (view != null && this.isWithinDeltaOfScreen(view, n2, this.getHeight())) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            view.requestFocus(n);
        } else {
            int n3;
            int n4 = n2;
            if (n == 33 && this.getScrollY() < n4) {
                n3 = this.getScrollY();
            } else {
                n3 = n4;
                if (n == 130) {
                    n3 = n4;
                    if (this.getChildCount() > 0) {
                        int n5 = this.getChildAt(0).getBottom();
                        int n6 = this.getScrollY() + this.getHeight() - this.mPaddingBottom;
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
            n = n == 130 ? n3 : -n3;
            this.doScrollY(n);
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
                this.overScrollBy(n3 - n, n4 - n2, n, n2, 0, n5, 0, this.mOverflingDistance, false);
                this.onScrollChanged(this.mScrollX, this.mScrollY, n, n2);
                if (bl2) {
                    if (n4 < 0 && n2 >= 0) {
                        this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
                    } else if (n4 > n5 && n2 <= n5) {
                        this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
                    }
                }
            }
            if (!this.awakenScrollBars()) {
                this.postInvalidateOnAnimation();
            }
        } else {
            StrictMode.Span span = this.mFlingStrictSpan;
            if (span != null) {
                span.finish();
                this.mFlingStrictSpan = null;
            }
        }
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        int n = this.getHeight();
        int n2 = this.getScrollY();
        int n3 = n2 + n;
        int n4 = this.getVerticalFadingEdgeLength();
        int n5 = n2;
        if (rect.top > 0) {
            n5 = n2 + n4;
        }
        n2 = n3;
        if (rect.bottom < this.getChildAt(0).getHeight()) {
            n2 = n3 - n4;
        }
        n4 = 0;
        if (rect.bottom > n2 && rect.top > n5) {
            n3 = rect.height() > n ? 0 + (rect.top - n5) : 0 + (rect.bottom - n2);
            n3 = Math.min(n3, this.getChildAt(0).getBottom() - n2);
        } else {
            n3 = n4;
            if (rect.top < n5) {
                n3 = n4;
                if (rect.bottom < n2) {
                    n3 = rect.height() > n ? 0 - (n2 - rect.bottom) : 0 - (n5 - rect.top);
                    n3 = Math.max(n3, -this.getScrollY());
                }
            }
        }
        return n3;
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override
    protected int computeVerticalScrollRange() {
        int n = this.getChildCount();
        int n2 = this.getHeight() - this.mPaddingBottom - this.mPaddingTop;
        if (n == 0) {
            return n2;
        }
        n = this.getChildAt(0).getBottom();
        int n3 = this.mScrollY;
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
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean bl = super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
        return bl;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.shouldDisplayEdgeEffects()) {
            float f;
            int n;
            int n2;
            float f2;
            int n3;
            int n4 = this.mScrollY;
            boolean bl = this.getClipToPadding();
            if (!this.mEdgeGlowTop.isFinished()) {
                n3 = canvas.save();
                if (bl) {
                    n = this.getWidth() - this.mPaddingLeft - this.mPaddingRight;
                    n2 = this.getHeight() - this.mPaddingTop - this.mPaddingBottom;
                    f = this.mPaddingLeft;
                    f2 = this.mPaddingTop;
                } else {
                    n = this.getWidth();
                    n2 = this.getHeight();
                    f = 0.0f;
                    f2 = 0.0f;
                }
                canvas.translate(f, (float)Math.min(0, n4) + f2);
                this.mEdgeGlowTop.setSize(n, n2);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    this.postInvalidateOnAnimation();
                }
                canvas.restoreToCount(n3);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                n3 = canvas.save();
                if (bl) {
                    n = this.getWidth() - this.mPaddingLeft - this.mPaddingRight;
                    n2 = this.getHeight() - this.mPaddingTop - this.mPaddingBottom;
                    f = this.mPaddingLeft;
                    f2 = this.mPaddingTop;
                } else {
                    n = this.getWidth();
                    n2 = this.getHeight();
                    f = 0.0f;
                    f2 = 0.0f;
                }
                canvas.translate((float)(-n) + f, (float)(Math.max(this.getScrollRange(), n4) + n2) + f2);
                canvas.rotate(180.0f, n, 0.0f);
                this.mEdgeGlowBottom.setSize(n, n2);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    this.postInvalidateOnAnimation();
                }
                canvas.restoreToCount(n3);
            }
        }
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("fillViewport", this.mFillViewport);
    }

    public boolean executeKeyEvent(KeyEvent object) {
        boolean bl;
        this.mTempRect.setEmpty();
        boolean bl2 = this.canScroll();
        int n = 130;
        if (!bl2) {
            boolean bl3 = this.isFocused();
            bl2 = false;
            if (bl3 && ((KeyEvent)object).getKeyCode() != 4) {
                View view = this.findFocus();
                object = view;
                if (view == this) {
                    object = null;
                }
                if ((object = FocusFinder.getInstance().findNextFocus(this, (View)object, 130)) != null && object != this && ((View)object).requestFocus(130)) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }
        bl2 = bl = false;
        if (((KeyEvent)object).getAction() == 0) {
            int n2 = ((KeyEvent)object).getKeyCode();
            if (n2 != 19) {
                if (n2 != 20) {
                    if (n2 != 62) {
                        bl2 = bl;
                    } else {
                        if (((KeyEvent)object).isShiftPressed()) {
                            n = 33;
                        }
                        this.pageScroll(n);
                        bl2 = bl;
                    }
                } else {
                    bl2 = !((KeyEvent)object).isAltPressed() ? this.arrowScroll(130) : this.fullScroll(130);
                }
            } else {
                bl2 = !((KeyEvent)object).isAltPressed() ? this.arrowScroll(33) : this.fullScroll(33);
            }
        }
        return bl2;
    }

    public void fling(int n) {
        if (this.getChildCount() > 0) {
            int n2 = this.getHeight() - this.mPaddingBottom - this.mPaddingTop;
            int n3 = this.getChildAt(0).getHeight();
            this.mScroller.fling(this.mScrollX, this.mScrollY, 0, n, 0, 0, 0, Math.max(0, n3 - n2), 0, n2 / 2);
            if (this.mFlingStrictSpan == null) {
                this.mFlingStrictSpan = StrictMode.enterCriticalSpan("ScrollView-fling");
            }
            this.postInvalidateOnAnimation();
        }
    }

    public boolean fullScroll(int n) {
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        Object object = this.mTempRect;
        ((Rect)object).top = 0;
        ((Rect)object).bottom = n3;
        if (n2 != 0 && (n2 = this.getChildCount()) > 0) {
            object = this.getChildAt(n2 - 1);
            this.mTempRect.bottom = ((View)object).getBottom() + this.mPaddingBottom;
            object = this.mTempRect;
            ((Rect)object).top = ((Rect)object).bottom - n3;
        }
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ScrollView.class.getName();
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getHeight();
        int n3 = this.mPaddingBottom;
        n3 = this.getChildAt(0).getBottom() - this.mScrollY - (n2 - n3);
        if (n3 < n) {
            return (float)n3 / (float)n;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int)((float)(this.mBottom - this.mTop) * 0.5f);
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        if (this.mScrollY < n) {
            return (float)this.mScrollY / (float)n;
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
        int n3 = ScrollView.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight, layoutParams.width);
        int n4 = this.mPaddingTop;
        n = this.mPaddingBottom;
        view.measure(n3, View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(n2) - (n4 + n)), 0));
    }

    @Override
    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n5 = ScrollView.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2, marginLayoutParams.width);
        n2 = this.mPaddingTop;
        n = this.mPaddingBottom;
        int n6 = marginLayoutParams.topMargin;
        int n7 = marginLayoutParams.bottomMargin;
        view.measure(n5, View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(n3) - (n2 + n + n6 + n7 + n4)), 0));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        StrictMode.Span span = this.mScrollStrictSpan;
        if (span != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
        if ((span = this.mFlingStrictSpan) != null) {
            span.finish();
            this.mFlingStrictSpan = null;
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f;
        int n;
        if (motionEvent.getAction() == 8 && (n = Math.round(this.mVerticalScrollFactor * (f = motionEvent.isFromSource(2) ? motionEvent.getAxisValue(9) : (motionEvent.isFromSource(4194304) ? motionEvent.getAxisValue(26) : 0.0f)))) != 0) {
            int n2 = this.getScrollRange();
            int n3 = this.mScrollY;
            int n4 = n3 - n;
            if (n4 < 0) {
                n = 0;
            } else {
                n = n4;
                if (n4 > n2) {
                    n = n2;
                }
            }
            if (n != n3) {
                super.scrollTo(this.mScrollX, n);
                return true;
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
        accessibilityEvent.setMaxScrollX(this.mScrollX);
        accessibilityEvent.setMaxScrollY(this.getScrollRange());
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        int n;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.isEnabled() && (n = this.getScrollRange()) > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (this.mScrollY > 0) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
            }
            if (this.mScrollY < n) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
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
        if (this.getScrollY() == 0 && !this.canScrollVertically(1)) {
            return false;
        }
        if ((n &= 255) != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 6) return this.mIsBeingDragged;
                        this.onSecondaryPointerUp((MotionEvent)object);
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
                        n = (int)((MotionEvent)object).getY(n2);
                        if (Math.abs(n - this.mLastMotionY) <= this.mTouchSlop || (2 & this.getNestedScrollAxes()) != 0) return this.mIsBeingDragged;
                        this.mIsBeingDragged = true;
                        this.mLastMotionY = n;
                        this.initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement((MotionEvent)object);
                        this.mNestedYOffset = 0;
                        if (this.mScrollStrictSpan == null) {
                            this.mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
                        }
                        if ((object = this.getParent()) == null) return this.mIsBeingDragged;
                        object.requestDisallowInterceptTouchEvent(true);
                    }
                    return this.mIsBeingDragged;
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            this.recycleVelocityTracker();
            if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, this.getScrollRange())) {
                this.postInvalidateOnAnimation();
            }
            this.stopNestedScroll();
            return this.mIsBeingDragged;
        }
        n = (int)((MotionEvent)object).getY();
        if (!this.inChild((int)((MotionEvent)object).getX(), n)) {
            this.mIsBeingDragged = false;
            this.recycleVelocityTracker();
            return this.mIsBeingDragged;
        } else {
            this.mLastMotionY = n;
            this.mActivePointerId = ((MotionEvent)object).getPointerId(0);
            this.initOrResetVelocityTracker();
            this.mVelocityTracker.addMovement((MotionEvent)object);
            this.mScroller.computeScrollOffset();
            this.mIsBeingDragged = true ^ this.mScroller.isFinished();
            if (this.mIsBeingDragged && this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
            }
            this.startNestedScroll(2);
        }
        return this.mIsBeingDragged;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mIsLayoutDirty = false;
        Object object = this.mChildToScrollTo;
        if (object != null && ScrollView.isViewDescendantOf((View)object, this)) {
            this.scrollToDescendant(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.isLaidOut()) {
            object = this.mSavedState;
            if (object != null) {
                this.mScrollY = ((SavedState)object).scrollPosition;
                this.mSavedState = null;
            }
            n = this.getChildCount() > 0 ? this.getChildAt(0).getMeasuredHeight() : 0;
            if (this.mScrollY > (n = Math.max(0, n - (n4 - n2 - this.mPaddingBottom - this.mPaddingTop)))) {
                this.mScrollY = n;
            } else if (this.mScrollY < 0) {
                this.mScrollY = 0;
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
        if (View.MeasureSpec.getMode(n2) == 0) {
            return;
        }
        if (this.getChildCount() > 0) {
            int n3;
            View view = this.getChildAt(0);
            n2 = this.getContext().getApplicationInfo().targetSdkVersion;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            if (n2 >= 23) {
                n2 = this.mPaddingLeft + this.mPaddingRight + layoutParams.leftMargin + layoutParams.rightMargin;
                n3 = this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                n2 = this.mPaddingLeft + this.mPaddingRight;
                n3 = this.mPaddingTop + this.mPaddingBottom;
            }
            n3 = this.getMeasuredHeight() - n3;
            if (view.getMeasuredHeight() < n3) {
                view.measure(ScrollView.getChildMeasureSpec(n, n2, layoutParams.width), View.MeasureSpec.makeMeasureSpec(n3, 1073741824));
            }
        }
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (!bl) {
            this.flingWithNestedDispatch((int)f2);
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        n = this.mScrollY;
        this.scrollBy(0, n4);
        n = this.mScrollY - n;
        this.dispatchNestedScroll(0, n, 0, n4 - n, null);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        super.onNestedScrollAccepted(view, view2, n);
        this.startNestedScroll(2);
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
            if (bl2) {
                this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, this.getScrollRange());
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
            n2 = 130;
        } else {
            n2 = n;
            if (n == 1) {
                n2 = 33;
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
        savedState.scrollPosition = this.mScrollY;
        return savedState;
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        View view = this.findFocus();
        if (view != null && this != view) {
            if (this.isWithinDeltaOfScreen(view, 0, n4)) {
                view.getDrawingRect(this.mTempRect);
                this.offsetDescendantRectToMyCoords(view, this.mTempRect);
                this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            }
            return;
        }
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        boolean bl = (n & 2) != 0;
        return bl;
    }

    @Override
    public void onStopNestedScroll(View view) {
        super.onStopNestedScroll(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arrn) {
        MotionEvent motionEvent;
        block38 : {
            Object object;
            boolean bl;
            block35 : {
                int n;
                block36 : {
                    int n2;
                    int n3;
                    int n4;
                    int n5;
                    int n6;
                    block41 : {
                        block40 : {
                            block39 : {
                                block37 : {
                                    this.initVelocityTrackerIfNotExists();
                                    motionEvent = MotionEvent.obtain((MotionEvent)arrn);
                                    n = arrn.getActionMasked();
                                    n6 = 0;
                                    if (n == 0) {
                                        this.mNestedYOffset = 0;
                                    }
                                    motionEvent.offsetLocation(0.0f, this.mNestedYOffset);
                                    if (n == 0) break block35;
                                    if (n == 1) break block36;
                                    if (n == 2) break block37;
                                    if (n != 3) {
                                        if (n != 5) {
                                            if (n == 6) {
                                                this.onSecondaryPointerUp((MotionEvent)arrn);
                                                this.mLastMotionY = (int)arrn.getY(arrn.findPointerIndex(this.mActivePointerId));
                                            }
                                        } else {
                                            n = arrn.getActionIndex();
                                            this.mLastMotionY = (int)arrn.getY(n);
                                            this.mActivePointerId = arrn.getPointerId(n);
                                        }
                                    } else if (this.mIsBeingDragged && this.getChildCount() > 0) {
                                        if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, this.getScrollRange())) {
                                            this.postInvalidateOnAnimation();
                                        }
                                        this.mActivePointerId = -1;
                                        this.endDrag();
                                    }
                                    break block38;
                                }
                                n2 = arrn.findPointerIndex(this.mActivePointerId);
                                if (n2 != -1) break block39;
                                arrn = new StringBuilder();
                                arrn.append("Invalid pointerId=");
                                arrn.append(this.mActivePointerId);
                                arrn.append(" in onTouchEvent");
                                Log.e(TAG, arrn.toString());
                                break block38;
                            }
                            n5 = (int)arrn.getY(n2);
                            n = n3 = this.mLastMotionY - n5;
                            if (this.dispatchNestedPreScroll(0, n3, this.mScrollConsumed, this.mScrollOffset)) {
                                n = n3 - this.mScrollConsumed[1];
                                motionEvent.offsetLocation(0.0f, this.mScrollOffset[1]);
                                this.mNestedYOffset += this.mScrollOffset[1];
                            }
                            if (!this.mIsBeingDragged && Math.abs(n) > this.mTouchSlop) {
                                ViewParent viewParent = this.getParent();
                                if (viewParent != null) {
                                    viewParent.requestDisallowInterceptTouchEvent(true);
                                }
                                this.mIsBeingDragged = true;
                                n = n > 0 ? (n -= this.mTouchSlop) : (n += this.mTouchSlop);
                            }
                            if (!this.mIsBeingDragged) break block38;
                            this.mLastMotionY = n5 - this.mScrollOffset[1];
                            n4 = this.mScrollY;
                            n5 = this.getScrollRange();
                            int n7 = this.getOverScrollMode();
                            if (n7 == 0) break block40;
                            n3 = n6;
                            if (n7 != 1) break block41;
                            n3 = n6;
                            if (n5 <= 0) break block41;
                        }
                        n3 = 1;
                    }
                    if (this.overScrollBy(0, n, 0, this.mScrollY, 0, n5, 0, this.mOverscrollDistance, true) && !this.hasNestedScrollingParent()) {
                        this.mVelocityTracker.clear();
                    }
                    if (this.dispatchNestedScroll(0, n6 = this.mScrollY - n4, 0, n - n6, this.mScrollOffset)) {
                        n = this.mLastMotionY;
                        arrn = this.mScrollOffset;
                        this.mLastMotionY = n - arrn[1];
                        motionEvent.offsetLocation(0.0f, arrn[1]);
                        this.mNestedYOffset += this.mScrollOffset[1];
                    } else if (n3 != 0) {
                        n3 = n4 + n;
                        if (n3 < 0) {
                            this.mEdgeGlowTop.onPull((float)n / (float)this.getHeight(), arrn.getX(n2) / (float)this.getWidth());
                            if (!this.mEdgeGlowBottom.isFinished()) {
                                this.mEdgeGlowBottom.onRelease();
                            }
                        } else if (n3 > n5) {
                            this.mEdgeGlowBottom.onPull((float)n / (float)this.getHeight(), 1.0f - arrn.getX(n2) / (float)this.getWidth());
                            if (!this.mEdgeGlowTop.isFinished()) {
                                this.mEdgeGlowTop.onRelease();
                            }
                        }
                        if (!(!this.shouldDisplayEdgeEffects() || this.mEdgeGlowTop.isFinished() && this.mEdgeGlowBottom.isFinished())) {
                            this.postInvalidateOnAnimation();
                        }
                    }
                    break block38;
                }
                if (this.mIsBeingDragged) {
                    arrn = this.mVelocityTracker;
                    arrn.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    n = (int)arrn.getYVelocity(this.mActivePointerId);
                    if (Math.abs(n) > this.mMinimumVelocity) {
                        this.flingWithNestedDispatch(-n);
                    } else if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, this.getScrollRange())) {
                        this.postInvalidateOnAnimation();
                    }
                    this.mActivePointerId = -1;
                    this.endDrag();
                }
                break block38;
            }
            if (this.getChildCount() == 0) {
                return false;
            }
            this.mIsBeingDragged = bl = this.mScroller.isFinished() ^ true;
            if (bl && (object = this.getParent()) != null) {
                object.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                object = this.mFlingStrictSpan;
                if (object != null) {
                    ((StrictMode.Span)object).finish();
                    this.mFlingStrictSpan = null;
                }
            }
            this.mLastMotionY = (int)arrn.getY();
            this.mActivePointerId = arrn.getPointerId(0);
            this.startNestedScroll(2);
        }
        arrn = this.mVelocityTracker;
        if (arrn != null) {
            arrn.addMovement(motionEvent);
        }
        motionEvent.recycle();
        return true;
    }

    public boolean pageScroll(int n) {
        Object object;
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        if (n2 != 0) {
            this.mTempRect.top = this.getScrollY() + n3;
            n2 = this.getChildCount();
            if (n2 > 0 && this.mTempRect.top + n3 > ((View)(object = this.getChildAt(n2 - 1))).getBottom()) {
                this.mTempRect.top = ((View)object).getBottom() - n3;
            }
        } else {
            this.mTempRect.top = this.getScrollY() - n3;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        object = this.mTempRect;
        ((Rect)object).bottom = ((Rect)object).top + n3;
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (!this.isEnabled()) {
            return false;
        }
        if (n != 4096) {
            if (n != 8192 && n != 16908344) {
                if (n != 16908346) {
                    return false;
                }
            } else {
                n = this.getHeight();
                int n2 = this.mPaddingBottom;
                int n3 = this.mPaddingTop;
                if ((n = Math.max(this.mScrollY - (n - n2 - n3), 0)) != this.mScrollY) {
                    this.smoothScrollTo(0, n);
                    return true;
                }
                return false;
            }
        }
        n = this.getHeight();
        int n4 = this.mPaddingBottom;
        int n5 = this.mPaddingTop;
        if ((n = Math.min(this.mScrollY + (n - n4 - n5), this.getScrollRange())) != this.mScrollY) {
            this.smoothScrollTo(0, n);
            return true;
        }
        return false;
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        if (view2 != null && view2.getRevealOnFocusHint()) {
            if (!this.mIsLayoutDirty) {
                this.scrollToDescendant(view2);
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
            n = ScrollView.clamp(n, this.getWidth() - this.mPaddingRight - this.mPaddingLeft, view.getWidth());
            n2 = ScrollView.clamp(n2, this.getHeight() - this.mPaddingBottom - this.mPaddingTop, view.getHeight());
            if (n != this.mScrollX || n2 != this.mScrollY) {
                super.scrollTo(n, n2);
            }
        }
    }

    public void scrollToDescendant(View view) {
        if (!this.mIsLayoutDirty) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            int n = this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (n != 0) {
                this.scrollBy(0, n);
            }
        } else {
            this.mChildToScrollTo = view;
        }
    }

    public void setBottomEdgeEffectColor(int n) {
        this.mEdgeGlowBottom.setColor(n);
    }

    public void setEdgeEffectColor(int n) {
        this.setTopEdgeEffectColor(n);
        this.setBottomEdgeEffectColor(n);
    }

    public void setFillViewport(boolean bl) {
        if (bl != this.mFillViewport) {
            this.mFillViewport = bl;
            this.requestLayout();
        }
    }

    public void setSmoothScrollingEnabled(boolean bl) {
        this.mSmoothScrollingEnabled = bl;
    }

    public void setTopEdgeEffectColor(int n) {
        this.mEdgeGlowTop.setColor(n);
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
            n = this.getHeight();
            int n3 = this.mPaddingBottom;
            int n4 = this.mPaddingTop;
            n4 = Math.max(0, this.getChildAt(0).getHeight() - (n - n3 - n4));
            n = this.mScrollY;
            n2 = Math.max(0, Math.min(n + n2, n4));
            this.mScroller.startScroll(this.mScrollX, n, 0, n2 - n);
            this.postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                StrictMode.Span span = this.mFlingStrictSpan;
                if (span != null) {
                    span.finish();
                    this.mFlingStrictSpan = null;
                }
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
        public int scrollPosition;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ScrollView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" scrollPosition=");
            stringBuilder.append(this.scrollPosition);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.scrollPosition);
        }

    }

}

