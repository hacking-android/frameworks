/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.android.internal.R;
import com.android.internal.logging.MetricsLogger;

public class ResolverDrawerLayout
extends ViewGroup {
    private static final String TAG = "ResolverDrawerLayout";
    private int mActivePointerId = -1;
    private int mAlwaysShowHeight;
    private float mCollapseOffset;
    private int mCollapsibleHeight;
    private int mCollapsibleHeightReserved;
    private boolean mDismissLocked;
    private boolean mDismissOnScrollerFinished;
    private float mDragRemainder = 0.0f;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private boolean mIsDragging;
    private float mLastTouchY;
    private int mMaxCollapsedHeight;
    private int mMaxCollapsedHeightSmall;
    private int mMaxWidth;
    private MetricsLogger mMetricsLogger;
    private final float mMinFlingVelocity;
    private AbsListView mNestedScrollingChild;
    private OnCollapsedChangedListener mOnCollapsedChangedListener;
    private OnDismissedListener mOnDismissedListener;
    private boolean mOpenOnClick;
    private boolean mOpenOnLayout;
    private RunOnDismissedListener mRunOnDismissedListener;
    private Drawable mScrollIndicatorDrawable;
    private final OverScroller mScroller;
    private boolean mShowAtTop;
    private boolean mSmallCollapsed;
    private final Rect mTempRect = new Rect();
    private int mTopOffset;
    private final ViewTreeObserver.OnTouchModeChangeListener mTouchModeChangeListener = new ViewTreeObserver.OnTouchModeChangeListener(){

        @Override
        public void onTouchModeChanged(boolean bl) {
            ResolverDrawerLayout resolverDrawerLayout;
            if (!bl && ResolverDrawerLayout.this.hasFocus() && (resolverDrawerLayout = ResolverDrawerLayout.this).isDescendantClipped(resolverDrawerLayout.getFocusedChild())) {
                ResolverDrawerLayout.this.smoothScrollTo(0, 0.0f);
            }
        }
    };
    private final int mTouchSlop;
    private int mUncollapsibleHeight;
    private final VelocityTracker mVelocityTracker;

    public ResolverDrawerLayout(Context context) {
        this(context, null);
    }

    public ResolverDrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ResolverDrawerLayout(Context object, AttributeSet object2, int n) {
        super((Context)object, (AttributeSet)object2, n);
        object2 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.ResolverDrawerLayout, n, 0);
        this.mMaxWidth = ((TypedArray)object2).getDimensionPixelSize(0, -1);
        this.mMaxCollapsedHeight = ((TypedArray)object2).getDimensionPixelSize(1, 0);
        this.mMaxCollapsedHeightSmall = ((TypedArray)object2).getDimensionPixelSize(2, this.mMaxCollapsedHeight);
        this.mShowAtTop = ((TypedArray)object2).getBoolean(3, false);
        ((TypedArray)object2).recycle();
        this.mScrollIndicatorDrawable = this.mContext.getDrawable(17303389);
        this.mScroller = new OverScroller((Context)object, AnimationUtils.loadInterpolator((Context)object, 17563653));
        this.mVelocityTracker = VelocityTracker.obtain();
        object = ViewConfiguration.get((Context)object);
        this.mTouchSlop = ((ViewConfiguration)object).getScaledTouchSlop();
        this.mMinFlingVelocity = ((ViewConfiguration)object).getScaledMinimumFlingVelocity();
        this.setImportantForAccessibility(1);
    }

    private void abortAnimation() {
        this.mScroller.abortAnimation();
        this.mRunOnDismissedListener = null;
        this.mDismissOnScrollerFinished = false;
    }

    private void dismiss() {
        this.mRunOnDismissedListener = new RunOnDismissedListener();
        this.post(this.mRunOnDismissedListener);
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((float)((double)(f - 0.5f) * 0.4712389167638204));
    }

    private View findChildUnder(float f, float f2) {
        return ResolverDrawerLayout.findChildUnder(this, f, f2);
    }

    private static View findChildUnder(ViewGroup viewGroup, float f, float f2) {
        for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
            View view = viewGroup.getChildAt(i);
            if (!ResolverDrawerLayout.isChildUnder(view, f, f2)) continue;
            return view;
        }
        return null;
    }

    private View findListChildUnder(float f, float f2) {
        View view = this.findChildUnder(f, f2);
        while (view != null) {
            f -= view.getX();
            f2 -= view.getY();
            if (view instanceof AbsListView) {
                return ResolverDrawerLayout.findChildUnder((ViewGroup)view, f, f2);
            }
            if (view instanceof ViewGroup) {
                view = ResolverDrawerLayout.findChildUnder((ViewGroup)view, f, f2);
                continue;
            }
            view = null;
        }
        return view;
    }

    private int getMaxCollapsedHeight() {
        int n = this.isSmallCollapsed() ? this.mMaxCollapsedHeightSmall : this.mMaxCollapsedHeight;
        return n + this.mCollapsibleHeightReserved;
    }

    private MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    private static boolean isChildUnder(View view, float f, float f2) {
        float f3 = view.getX();
        float f4 = view.getY();
        float f5 = view.getWidth();
        float f6 = view.getHeight();
        boolean bl = f >= f3 && f2 >= f4 && f < f5 + f3 && f2 < f6 + f4;
        return bl;
    }

    private boolean isDescendantClipped(View object) {
        Object object2 = this.mTempRect;
        int n = ((View)object).getWidth();
        int n2 = ((View)object).getHeight();
        boolean bl = false;
        ((Rect)object2).set(0, 0, n, n2);
        this.offsetDescendantRectToMyCoords((View)object, this.mTempRect);
        if (((View)object).getParent() != this) {
            object2 = object;
            ViewParent viewParent = ((View)object).getParent();
            object = object2;
            while (viewParent != this) {
                object = (View)((Object)viewParent);
                viewParent = ((View)object).getParent();
            }
        }
        n2 = this.getHeight() - this.getPaddingBottom();
        int n3 = this.getChildCount();
        for (n = this.indexOfChild((View)object) + 1; n < n3; ++n) {
            object = this.getChildAt(n);
            if (((View)object).getVisibility() == 8) continue;
            n2 = Math.min(n2, ((View)object).getTop());
        }
        if (this.mTempRect.bottom > n2) {
            bl = true;
        }
        return bl;
    }

    private boolean isDismissable() {
        boolean bl = this.mOnDismissedListener != null && !this.mDismissLocked;
        return bl;
    }

    private boolean isDragging() {
        boolean bl = this.mIsDragging || this.getNestedScrollAxes() == 2;
        return bl;
    }

    private boolean isListChildUnderClipped(float f, float f2) {
        View view = this.findListChildUnder(f, f2);
        boolean bl = view != null && this.isDescendantClipped(view);
        return bl;
    }

    private boolean isMoving() {
        boolean bl = this.mIsDragging || !this.mScroller.isFinished();
        return bl;
    }

    private boolean isNestedChildScrolled() {
        boolean bl;
        block0 : {
            AbsListView absListView = this.mNestedScrollingChild;
            bl = false;
            if (absListView == null || absListView.getChildCount() <= 0 || this.mNestedScrollingChild.getFirstVisiblePosition() <= 0 && this.mNestedScrollingChild.getChildAt(0).getTop() >= 0) break block0;
            bl = true;
        }
        return bl;
    }

    private void onCollapsedChanged(boolean bl) {
        OnCollapsedChangedListener onCollapsedChangedListener;
        this.notifyViewAccessibilityStateChangedIfNeeded(0);
        if (this.mScrollIndicatorDrawable != null) {
            this.setWillNotDraw(bl ^ true);
        }
        if ((onCollapsedChangedListener = this.mOnCollapsedChangedListener) != null) {
            onCollapsedChangedListener.onCollapsedChanged(bl);
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            float f;
            n = n == 0 ? 1 : 0;
            this.mInitialTouchX = motionEvent.getX(n);
            this.mLastTouchY = f = motionEvent.getY(n);
            this.mInitialTouchY = f;
            this.mActivePointerId = motionEvent.getPointerId(n);
        }
    }

    private float performDrag(float f) {
        if (this.getShowAtTop()) {
            return 0.0f;
        }
        float f2 = Math.max(0.0f, Math.min(this.mCollapseOffset + f, (float)(this.mCollapsibleHeight + this.mUncollapsibleHeight)));
        f = this.mCollapseOffset;
        if (f2 != f) {
            Object object;
            int n;
            float f3 = f2 - f;
            this.mDragRemainder += f3 - (float)((int)f3);
            float f4 = this.mDragRemainder;
            if (f4 >= 1.0f) {
                this.mDragRemainder = f4 - 1.0f;
                f = f3 + 1.0f;
            } else {
                f = f3;
                if (f4 <= -1.0f) {
                    this.mDragRemainder = f4 + 1.0f;
                    f = f3 - 1.0f;
                }
            }
            int n2 = this.getChildCount();
            for (n = 0; n < n2; ++n) {
                object = this.getChildAt(n);
                if (((LayoutParams)object.getLayoutParams()).ignoreOffset) continue;
                ((View)object).offsetTopAndBottom((int)f);
            }
            f3 = this.mCollapseOffset;
            n = 1;
            boolean bl = f3 != 0.0f;
            this.mCollapseOffset = f2;
            this.mTopOffset = (int)((float)this.mTopOffset + f);
            boolean bl2 = f2 != 0.0f;
            if (bl != bl2) {
                this.onCollapsedChanged(bl2);
                object = this.getMetricsLogger();
                LogMaker logMaker = new LogMaker(1651);
                if (!bl2) {
                    n = 0;
                }
                ((MetricsLogger)object).write(logMaker.setSubtype(n));
            }
            this.onScrollChanged(0, (int)f2, 0, (int)(f2 - f));
            this.postInvalidateOnAnimation();
            return f;
        }
        return 0.0f;
    }

    private void resetTouch() {
        this.mActivePointerId = -1;
        this.mIsDragging = false;
        this.mOpenOnClick = false;
        this.mLastTouchY = 0.0f;
        this.mInitialTouchY = 0.0f;
        this.mInitialTouchX = 0.0f;
        this.mVelocityTracker.clear();
    }

    private void smoothScrollTo(int n, float f) {
        this.abortAnimation();
        int n2 = (int)this.mCollapseOffset;
        int n3 = n - n2;
        if (n3 == 0) {
            return;
        }
        n = this.getHeight();
        int n4 = n / 2;
        float f2 = Math.min(1.0f, (float)Math.abs(n3) * 1.0f / (float)n);
        float f3 = n4;
        float f4 = n4;
        f2 = this.distanceInfluenceForSnapDuration(f2);
        n = (f = Math.abs(f)) > 0.0f ? Math.round(Math.abs((f3 + f4 * f2) / f) * 1000.0f) * 4 : (int)((1.0f + (float)Math.abs(n3) / (float)n) * 100.0f);
        n = Math.min(n, 300);
        this.mScroller.startScroll(0, n2, 0, n3, n);
        this.postInvalidateOnAnimation();
    }

    private boolean updateCollapseOffset(int n, boolean bl) {
        int n2 = this.mCollapsibleHeight;
        boolean bl2 = false;
        if (n == n2) {
            return false;
        }
        boolean bl3 = this.getShowAtTop();
        float f = 0.0f;
        if (bl3) {
            this.mCollapseOffset = 0.0f;
            return false;
        }
        if (this.isLaidOut()) {
            bl3 = this.mCollapseOffset != 0.0f;
            this.mCollapseOffset = bl && n < (n2 = this.mCollapsibleHeight) && this.mCollapseOffset == (float)n ? (float)n2 : Math.min(this.mCollapseOffset, (float)this.mCollapsibleHeight);
            bl = bl2;
            if (this.mCollapseOffset != 0.0f) {
                bl = true;
            }
            if (bl3 != bl) {
                this.onCollapsedChanged(bl);
            }
        } else {
            if (!this.mOpenOnLayout) {
                f = this.mCollapsibleHeight;
            }
            this.mCollapseOffset = f;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            boolean bl = this.mScroller.isFinished();
            this.performDrag((float)this.mScroller.getCurrY() - this.mCollapseOffset);
            if (bl ^ true) {
                this.postInvalidateOnAnimation();
            } else if (this.mDismissOnScrollerFinished && this.mOnDismissedListener != null) {
                this.dismiss();
            }
        }
    }

    void dispatchOnDismissed() {
        Object object = this.mOnDismissedListener;
        if (object != null) {
            object.onDismissed();
        }
        if ((object = this.mRunOnDismissedListener) != null) {
            this.removeCallbacks((Runnable)object);
            this.mRunOnDismissedListener = null;
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ScrollView.class.getName();
    }

    public int getAlwaysShowHeight() {
        return this.mAlwaysShowHeight;
    }

    public boolean getShowAtTop() {
        return this.mShowAtTop;
    }

    public boolean isCollapsed() {
        boolean bl = this.mCollapseOffset > 0.0f;
        return bl;
    }

    public boolean isSmallCollapsed() {
        return this.mSmallCollapsed;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getViewTreeObserver().addOnTouchModeChangeListener(this.mTouchModeChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getViewTreeObserver().removeOnTouchModeChangeListener(this.mTouchModeChangeListener);
        this.abortAnimation();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Drawable drawable2 = this.mScrollIndicatorDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        super.onDrawForeground(canvas);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.isEnabled() && this.mCollapseOffset != 0.0f) {
            accessibilityNodeInfo.addAction(4096);
            accessibilityNodeInfo.setScrollable(true);
        }
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        block14 : {
            block13 : {
                boolean bl2;
                block12 : {
                    block9 : {
                        block10 : {
                            int n;
                            block11 : {
                                n = motionEvent.getActionMasked();
                                if (n == 0) {
                                    this.mVelocityTracker.clear();
                                }
                                this.mVelocityTracker.addMovement(motionEvent);
                                bl2 = false;
                                if (n == 0) break block9;
                                if (n == 1) break block10;
                                if (n == 2) break block11;
                                if (n == 3) break block10;
                                if (n == 6) {
                                    this.onSecondaryPointerUp(motionEvent);
                                }
                                break block12;
                            }
                            float f = motionEvent.getX();
                            float f2 = motionEvent.getY();
                            float f3 = f2 - this.mInitialTouchY;
                            if (Math.abs(f3) > (float)this.mTouchSlop && this.findChildUnder(f, f2) != null && (2 & this.getNestedScrollAxes()) == 0) {
                                this.mActivePointerId = motionEvent.getPointerId(0);
                                this.mIsDragging = true;
                                f = this.mLastTouchY;
                                n = this.mTouchSlop;
                                this.mLastTouchY = Math.max(f - (float)n, Math.min(f + f3, f + (float)n));
                            }
                            break block12;
                        }
                        this.resetTouch();
                        break block12;
                    }
                    float f = motionEvent.getX();
                    float f4 = motionEvent.getY();
                    this.mInitialTouchX = f;
                    this.mLastTouchY = f4;
                    this.mInitialTouchY = f4;
                    bl = this.isListChildUnderClipped(f, f4) && this.mCollapseOffset > 0.0f;
                    this.mOpenOnClick = bl;
                }
                if (this.mIsDragging) {
                    this.abortAnimation();
                }
                if (this.mIsDragging) break block13;
                bl = bl2;
                if (!this.mOpenOnClick) break block14;
            }
            bl = true;
        }
        return bl;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n4 = this.getWidth();
        View view = null;
        n3 = this.mTopOffset;
        int n5 = this.getPaddingLeft();
        int n6 = this.getPaddingRight();
        int n7 = this.getChildCount();
        n = n4;
        for (n2 = 0; n2 < n7; ++n2) {
            int n8;
            View view2 = this.getChildAt(n2);
            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
            if (layoutParams.hasNestedScrollIndicator) {
                view = view2;
            }
            if (view2.getVisibility() == 8) continue;
            n3 = n8 = layoutParams.topMargin + n3;
            if (layoutParams.ignoreOffset) {
                n3 = (int)((float)n8 - this.mCollapseOffset);
            }
            int n9 = view2.getMeasuredHeight() + n3;
            int n10 = view2.getMeasuredWidth();
            n8 = (n4 - n6 - n5 - n10) / 2 + n5;
            view2.layout(n8, n3, n8 + n10, n9);
            n3 = layoutParams.bottomMargin + n9;
        }
        if (this.mScrollIndicatorDrawable != null) {
            if (view != null) {
                n3 = view.getLeft();
                n = view.getRight();
                n4 = view.getTop();
                n2 = this.mScrollIndicatorDrawable.getIntrinsicHeight();
                this.mScrollIndicatorDrawable.setBounds(n3, n4 - n2, n, n4);
                this.setWillNotDraw(true ^ this.isCollapsed());
            } else {
                this.mScrollIndicatorDrawable = null;
                this.setWillNotDraw(true);
            }
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        Object object;
        int n3;
        int n4;
        int n5;
        Object object2;
        int n6;
        int n7 = View.MeasureSpec.getSize(n);
        int n8 = View.MeasureSpec.getSize(n2);
        n = this.mMaxWidth;
        n = n >= 0 ? Math.min(n7, n) : n7;
        int n9 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
        int n10 = View.MeasureSpec.makeMeasureSpec(n8, 1073741824);
        int n11 = this.getChildCount();
        n = 0;
        n2 = 0;
        do {
            n5 = -1;
            n3 = 8;
            n6 = 0;
            if (n2 >= n11) break;
            object2 = this.getChildAt(n2);
            object = (LayoutParams)((View)object2).getLayoutParams();
            if (((LayoutParams)object).alwaysShow && ((View)object2).getVisibility() != 8) {
                if (((LayoutParams)object).maxHeight != -1) {
                    n3 = n8 - n;
                    n4 = View.MeasureSpec.makeMeasureSpec(((LayoutParams)object).maxHeight, Integer.MIN_VALUE);
                    n3 = ((LayoutParams)object).maxHeight > n3 ? ((LayoutParams)object).maxHeight - n3 : 0;
                    this.measureChildWithMargins((View)object2, n9, 0, n4, n3);
                } else {
                    this.measureChildWithMargins((View)object2, n9, 0, n10, n);
                }
                n += ((View)object2).getMeasuredHeight();
            }
            ++n2;
        } while (true);
        this.mAlwaysShowHeight = n;
        int n12 = n;
        n2 = n5;
        n = n6;
        for (n4 = 0; n4 < n11; ++n4) {
            object = this.getChildAt(n4);
            object2 = (LayoutParams)((View)object).getLayoutParams();
            if (((LayoutParams)object2).alwaysShow || ((View)object).getVisibility() == n3) continue;
            if (((LayoutParams)object2).maxHeight != n2) {
                n6 = n8 - n12;
                n5 = View.MeasureSpec.makeMeasureSpec(((LayoutParams)object2).maxHeight, Integer.MIN_VALUE);
                n6 = ((LayoutParams)object2).maxHeight > n6 ? ((LayoutParams)object2).maxHeight - n6 : n;
                this.measureChildWithMargins((View)object, n9, 0, n5, n6);
            } else {
                this.measureChildWithMargins((View)object, n9, 0, n10, n12);
            }
            n12 += ((View)object).getMeasuredHeight();
        }
        n2 = this.mCollapsibleHeight;
        this.mCollapsibleHeight = Math.max(n, n12 - this.mAlwaysShowHeight - this.getMaxCollapsedHeight());
        this.mUncollapsibleHeight = n12 - this.mCollapsibleHeight;
        this.updateCollapseOffset(n2, this.isDragging() ^ true);
        this.mTopOffset = this.getShowAtTop() ? n : Math.max(n, n8 - n12) + (int)this.mCollapseOffset;
        this.setMeasuredDimension(n7, n8);
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        int n = 0;
        int n2 = 0;
        if (!bl && Math.abs(f2) > this.mMinFlingVelocity) {
            if (this.getShowAtTop()) {
                if (this.isDismissable() && f2 > 0.0f) {
                    this.abortAnimation();
                    this.dismiss();
                } else {
                    n = n2;
                    if (f2 < 0.0f) {
                        n = this.mCollapsibleHeight;
                    }
                    this.smoothScrollTo(n, f2);
                }
            } else if (this.isDismissable() && f2 < 0.0f && (f = this.mCollapseOffset) > (float)(n2 = this.mCollapsibleHeight)) {
                this.smoothScrollTo(n2 + this.mUncollapsibleHeight, f2);
                this.mDismissOnScrollerFinished = true;
            } else {
                if (!(f2 > 0.0f)) {
                    n = this.mCollapsibleHeight;
                }
                this.smoothScrollTo(n, f2);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        if (!this.getShowAtTop() && f2 > this.mMinFlingVelocity && this.mCollapseOffset != 0.0f) {
            this.smoothScrollTo(0, f2);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View view, int n, Bundle bundle) {
        if (super.onNestedPrePerformAccessibilityAction(view, n, bundle)) {
            return true;
        }
        if (n == 4096 && this.mCollapseOffset != 0.0f) {
            this.smoothScrollTo(0, 0.0f);
            return true;
        }
        return false;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        if (n2 > 0) {
            arrn[1] = (int)(-this.performDrag(-n2));
        }
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        if (n4 < 0) {
            this.performDrag(-n4);
        }
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        super.onNestedScrollAccepted(view, view2, n);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.mOpenOnLayout = ((SavedState)parcelable).open;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        boolean bl = this.mCollapsibleHeight > 0 && this.mCollapseOffset == 0.0f;
        savedState.open = bl;
        return savedState;
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        if ((n & 2) != 0) {
            if (view instanceof AbsListView) {
                this.mNestedScrollingChild = (AbsListView)view;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onStopNestedScroll(View view) {
        super.onStopNestedScroll(view);
        if (this.mScroller.isFinished()) {
            int n;
            float f = this.mCollapseOffset;
            int n2 = n = this.mCollapsibleHeight;
            if (f < (float)(n / 2)) {
                n2 = 0;
            }
            this.smoothScrollTo(n2, 0.0f);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        this.mVelocityTracker.addMovement(motionEvent);
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = true;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (n != 0) {
            if (n != 1) {
                float f;
                float f2;
                if (n != 2) {
                    if (n != 3) {
                        if (n == 5) {
                            float f3;
                            n5 = motionEvent.getActionIndex();
                            this.mActivePointerId = motionEvent.getPointerId(n5);
                            this.mInitialTouchX = motionEvent.getX(n5);
                            this.mLastTouchY = f3 = motionEvent.getY(n5);
                            this.mInitialTouchY = f3;
                            return bl;
                        }
                        if (n != 6) {
                            return bl;
                        }
                        this.onSecondaryPointerUp(motionEvent);
                        return bl;
                    }
                    if (this.mIsDragging) {
                        float f4 = this.mCollapseOffset;
                        n3 = this.mCollapsibleHeight;
                        if (!(f4 < (float)(n3 / 2))) {
                            n5 = n3;
                        }
                        this.smoothScrollTo(n5, 0.0f);
                    }
                    this.resetTouch();
                    return true;
                }
                n5 = n3 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (n3 < 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Bad pointer id ");
                    stringBuilder.append(this.mActivePointerId);
                    stringBuilder.append(", resetting");
                    Log.e(TAG, stringBuilder.toString());
                    n5 = 0;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    this.mInitialTouchX = motionEvent.getX();
                    this.mLastTouchY = f = motionEvent.getY();
                    this.mInitialTouchY = f;
                }
                float f5 = motionEvent.getX(n5);
                f = motionEvent.getY(n5);
                bl = bl2;
                if (!this.mIsDragging) {
                    f2 = f - this.mInitialTouchY;
                    bl = bl2;
                    if (Math.abs(f2) > (float)this.mTouchSlop) {
                        bl = bl2;
                        if (this.findChildUnder(f5, f) != null) {
                            this.mIsDragging = true;
                            bl = true;
                            f5 = this.mLastTouchY;
                            n5 = this.mTouchSlop;
                            this.mLastTouchY = Math.max(f5 - (float)n5, Math.min(f5 + f2, f5 + (float)n5));
                        }
                    }
                }
                if (this.mIsDragging) {
                    f2 = f - this.mLastTouchY;
                    if (f2 > 0.0f && this.isNestedChildScrolled()) {
                        this.mNestedScrollingChild.smoothScrollBy((int)(-f2), 0);
                    } else {
                        this.performDrag(f2);
                    }
                }
                this.mLastTouchY = f;
                return bl;
            }
            bl3 = this.mIsDragging;
            this.mIsDragging = false;
            if (!bl3 && this.findChildUnder(this.mInitialTouchX, this.mInitialTouchY) == null && this.findChildUnder(motionEvent.getX(), motionEvent.getY()) == null && this.isDismissable()) {
                this.dispatchOnDismissed();
                this.resetTouch();
                return true;
            }
            if (this.mOpenOnClick && Math.abs(motionEvent.getX() - this.mInitialTouchX) < (float)this.mTouchSlop && Math.abs(motionEvent.getY() - this.mInitialTouchY) < (float)this.mTouchSlop) {
                this.smoothScrollTo(0, 0.0f);
                return true;
            }
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
            if (Math.abs(f) > this.mMinFlingVelocity) {
                float f6;
                if (this.getShowAtTop()) {
                    if (this.isDismissable() && f < 0.0f) {
                        this.abortAnimation();
                        this.dismiss();
                    } else {
                        n5 = f < 0.0f ? n2 : this.mCollapsibleHeight;
                        this.smoothScrollTo(n5, f);
                    }
                } else if (this.isDismissable() && f > 0.0f && (f6 = this.mCollapseOffset) > (float)(n5 = this.mCollapsibleHeight)) {
                    this.smoothScrollTo(n5 + this.mUncollapsibleHeight, f);
                    this.mDismissOnScrollerFinished = true;
                } else {
                    if (this.isNestedChildScrolled()) {
                        this.mNestedScrollingChild.smoothScrollToPosition(0);
                    }
                    n5 = f < 0.0f ? n3 : this.mCollapsibleHeight;
                    this.smoothScrollTo(n5, f);
                }
            } else {
                float f7 = this.mCollapseOffset;
                n5 = this.mCollapsibleHeight;
                if (f7 < (float)(n5 / 2)) {
                    n5 = n4;
                }
                this.smoothScrollTo(n5, 0.0f);
            }
            this.resetTouch();
            return bl;
        }
        float f = motionEvent.getX();
        float f8 = motionEvent.getY();
        this.mInitialTouchX = f;
        this.mLastTouchY = f8;
        this.mInitialTouchY = f8;
        this.mActivePointerId = motionEvent.getPointerId(0);
        n5 = this.findChildUnder(this.mInitialTouchX, this.mInitialTouchY) != null ? 1 : 0;
        bl = this.isDismissable() || this.mCollapsibleHeight > 0;
        if (n5 == 0 || !bl) {
            bl3 = false;
        }
        this.mIsDragging = bl3;
        this.abortAnimation();
        return bl;
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (n == AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS.getId()) {
            return false;
        }
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (n == 4096 && this.mCollapseOffset != 0.0f) {
            this.smoothScrollTo(0, 0.0f);
            return true;
        }
        return false;
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!this.isInTouchMode() && this.isDescendantClipped(view2)) {
            this.smoothScrollTo(0, 0.0f);
        }
    }

    public void setCollapsed(boolean bl) {
        if (!this.isLaidOut()) {
            this.mOpenOnLayout = bl;
        } else {
            int n = bl ? this.mCollapsibleHeight : 0;
            this.smoothScrollTo(n, 0.0f);
        }
    }

    public void setCollapsibleHeightReserved(int n) {
        int n2 = this.mCollapsibleHeightReserved;
        this.mCollapsibleHeightReserved = n;
        n = this.mCollapsibleHeightReserved - n2;
        if (n != 0 && this.mIsDragging) {
            this.mLastTouchY -= (float)n;
        }
        n = this.mCollapsibleHeight;
        this.mCollapsibleHeight = Math.max(this.mCollapsibleHeight, this.getMaxCollapsedHeight());
        if (this.updateCollapseOffset(n, this.isDragging() ^ true)) {
            return;
        }
        this.invalidate();
    }

    public void setDismissLocked(boolean bl) {
        this.mDismissLocked = bl;
    }

    public void setOnCollapsedChangedListener(OnCollapsedChangedListener onCollapsedChangedListener) {
        this.mOnCollapsedChangedListener = onCollapsedChangedListener;
    }

    public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
        this.mOnDismissedListener = onDismissedListener;
    }

    public void setShowAtTop(boolean bl) {
        this.mShowAtTop = bl;
        this.invalidate();
        this.requestLayout();
    }

    public void setSmallCollapsed(boolean bl) {
        this.mSmallCollapsed = bl;
        this.requestLayout();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public boolean alwaysShow;
        public boolean hasNestedScrollIndicator;
        public boolean ignoreOffset;
        public int maxHeight;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ResolverDrawerLayout_LayoutParams);
            this.alwaysShow = ((TypedArray)object).getBoolean(1, false);
            this.ignoreOffset = ((TypedArray)object).getBoolean(3, false);
            this.hasNestedScrollIndicator = ((TypedArray)object).getBoolean(2, false);
            this.maxHeight = ((TypedArray)object).getDimensionPixelSize(4, -1);
            ((TypedArray)object).recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.alwaysShow = layoutParams.alwaysShow;
            this.ignoreOffset = layoutParams.ignoreOffset;
            this.hasNestedScrollIndicator = layoutParams.hasNestedScrollIndicator;
            this.maxHeight = layoutParams.maxHeight;
        }
    }

    public static interface OnCollapsedChangedListener {
        public void onCollapsedChanged(boolean var1);
    }

    public static interface OnDismissedListener {
        public void onDismissed();
    }

    private class RunOnDismissedListener
    implements Runnable {
        private RunOnDismissedListener() {
        }

        @Override
        public void run() {
            ResolverDrawerLayout.this.dispatchOnDismissed();
        }
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
        boolean open;

        private SavedState(Parcel parcel) {
            super(parcel);
            boolean bl = parcel.readInt() != 0;
            this.open = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt((int)this.open);
        }

    }

}

