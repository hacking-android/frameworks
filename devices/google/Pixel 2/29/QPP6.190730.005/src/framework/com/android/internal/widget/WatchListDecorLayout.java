/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.ArrayList;

public class WatchListDecorLayout
extends FrameLayout
implements ViewTreeObserver.OnScrollChangedListener {
    private View mBottomPanel;
    private int mForegroundPaddingBottom = 0;
    private int mForegroundPaddingLeft = 0;
    private int mForegroundPaddingRight = 0;
    private int mForegroundPaddingTop = 0;
    private ListView mListView;
    private final ArrayList<View> mMatchParentChildren = new ArrayList(1);
    private ViewTreeObserver mObserver;
    private int mPendingScroll;
    private View mTopPanel;

    public WatchListDecorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WatchListDecorLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public WatchListDecorLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private void applyMeasureToChild(View view, int n, int n2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        n = marginLayoutParams.width == -1 ? View.MeasureSpec.makeMeasureSpec(Math.max(0, this.getMeasuredWidth() - this.getPaddingLeftWithForeground() - this.getPaddingRightWithForeground() - marginLayoutParams.leftMargin - marginLayoutParams.rightMargin), 1073741824) : WatchListDecorLayout.getChildMeasureSpec(n, this.getPaddingLeftWithForeground() + this.getPaddingRightWithForeground() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
        n2 = marginLayoutParams.height == -1 ? View.MeasureSpec.makeMeasureSpec(Math.max(0, this.getMeasuredHeight() - this.getPaddingTopWithForeground() - this.getPaddingBottomWithForeground() - marginLayoutParams.topMargin - marginLayoutParams.bottomMargin), 1073741824) : WatchListDecorLayout.getChildMeasureSpec(n2, this.getPaddingTopWithForeground() + this.getPaddingBottomWithForeground() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        view.measure(n, n2);
    }

    private int getPaddingBottomWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingBottom, this.mForegroundPaddingBottom) : this.mPaddingBottom + this.mForegroundPaddingBottom;
        return n;
    }

    private int getPaddingLeftWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingLeft, this.mForegroundPaddingLeft) : this.mPaddingLeft + this.mForegroundPaddingLeft;
        return n;
    }

    private int getPaddingRightWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingRight, this.mForegroundPaddingRight) : this.mPaddingRight + this.mForegroundPaddingRight;
        return n;
    }

    private int getPaddingTopWithForeground() {
        int n = this.isForegroundInsidePadding() ? Math.max(this.mPaddingTop, this.mForegroundPaddingTop) : this.mPaddingTop + this.mForegroundPaddingTop;
        return n;
    }

    private int measureAndGetHeight(View view, int n, int n2) {
        if (view != null) {
            if (view.getVisibility() != 8) {
                this.applyMeasureToChild(this.mBottomPanel, n, n2);
                return view.getMeasuredHeight();
            }
            if (this.getMeasureAllChildren()) {
                this.applyMeasureToChild(this.mBottomPanel, n, n2);
            }
        }
        return 0;
    }

    private void setScrolling(View view, float f) {
        if (view.getTranslationY() != f) {
            view.setTranslationY(f);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPendingScroll = 0;
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            if (view instanceof ListView) {
                if (this.mListView == null) {
                    this.mListView = (ListView)view;
                    this.mListView.setNestedScrollingEnabled(true);
                    this.mObserver = this.mListView.getViewTreeObserver();
                    this.mObserver.addOnScrollChangedListener(this);
                    continue;
                }
                throw new IllegalArgumentException("only one ListView child allowed");
            }
            int n = ((FrameLayout.LayoutParams)view.getLayoutParams()).gravity & 112;
            if (n == 48 && this.mTopPanel == null) {
                this.mTopPanel = view;
                continue;
            }
            if (n != 80 || this.mBottomPanel != null) continue;
            this.mBottomPanel = view;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        this.mListView = null;
        this.mBottomPanel = null;
        this.mTopPanel = null;
        ViewTreeObserver viewTreeObserver = this.mObserver;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.mObserver.removeOnScrollChangedListener(this);
            }
            this.mObserver = null;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        Object object;
        int n4 = this.getChildCount();
        int n5 = View.MeasureSpec.getMode(n) == 1073741824 && View.MeasureSpec.getMode(n2) == 1073741824 ? 0 : 1;
        this.mMatchParentChildren.clear();
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        for (n3 = 0; n3 < n4; ++n3) {
            int n9;
            int n10;
            int n11;
            block12 : {
                block11 : {
                    object = this.getChildAt(n3);
                    if (this.getMeasureAllChildren()) break block11;
                    n11 = n8;
                    n9 = n7;
                    n10 = n6;
                    if (((View)object).getVisibility() == 8) break block12;
                }
                this.measureChildWithMargins((View)object, n, 0, n2, 0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)((View)object).getLayoutParams();
                n9 = Math.max(n7, ((View)object).getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                n10 = Math.max(n6, ((View)object).getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                n11 = WatchListDecorLayout.combineMeasuredStates(n8, ((View)object).getMeasuredState());
                if (n5 != 0 && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add((View)object);
                }
            }
            n8 = n11;
            n7 = n9;
            n6 = n10;
        }
        n5 = this.getPaddingLeftWithForeground();
        n3 = this.getPaddingRightWithForeground();
        n6 = Math.max(n6 + (this.getPaddingTopWithForeground() + this.getPaddingBottomWithForeground()), this.getSuggestedMinimumHeight());
        n7 = Math.max(n7 + (n5 + n3), this.getSuggestedMinimumWidth());
        object = this.getForeground();
        n3 = n6;
        n5 = n7;
        if (object != null) {
            n3 = Math.max(n6, ((Drawable)object).getMinimumHeight());
            n5 = Math.max(n7, ((Drawable)object).getMinimumWidth());
        }
        this.setMeasuredDimension(WatchListDecorLayout.resolveSizeAndState(n5, n, n8), WatchListDecorLayout.resolveSizeAndState(n3, n2, n8 << 16));
        object = this.mListView;
        if (object != null) {
            n8 = this.mPendingScroll;
            if (n8 != 0) {
                ((AbsListView)object).scrollListBy(n8);
                this.mPendingScroll = 0;
            }
            n5 = Math.max(this.mListView.getPaddingTop(), this.measureAndGetHeight(this.mTopPanel, n, n2));
            n8 = Math.max(this.mListView.getPaddingBottom(), this.measureAndGetHeight(this.mBottomPanel, n, n2));
            if (n5 != this.mListView.getPaddingTop() || n8 != this.mListView.getPaddingBottom()) {
                this.mPendingScroll += this.mListView.getPaddingTop() - n5;
                object = this.mListView;
                ((View)object).setPadding(((View)object).getPaddingLeft(), n5, this.mListView.getPaddingRight(), n8);
            }
        }
        if ((n5 = this.mMatchParentChildren.size()) > 1) {
            for (n8 = 0; n8 < n5; ++n8) {
                object = this.mMatchParentChildren.get(n8);
                if (this.mListView != null && (object == this.mTopPanel || object == this.mBottomPanel)) continue;
                this.applyMeasureToChild((View)object, n, n2);
            }
        }
    }

    @Override
    public void onScrollChanged() {
        View view = this.mListView;
        if (view == null) {
            return;
        }
        if (this.mTopPanel != null) {
            if (((ViewGroup)view).getChildCount() > 0) {
                if (this.mListView.getFirstVisiblePosition() == 0) {
                    view = this.mListView.getChildAt(0);
                    this.setScrolling(this.mTopPanel, view.getY() - (float)this.mTopPanel.getHeight() - (float)this.mTopPanel.getTop());
                } else {
                    view = this.mTopPanel;
                    this.setScrolling(view, -view.getHeight());
                }
            } else {
                this.setScrolling(this.mTopPanel, 0.0f);
            }
        }
        if (this.mBottomPanel != null) {
            if (this.mListView.getChildCount() > 0) {
                if (this.mListView.getLastVisiblePosition() >= this.mListView.getCount() - 1) {
                    view = this.mListView;
                    view = ((ViewGroup)view).getChildAt(((ViewGroup)view).getChildCount() - 1);
                    this.setScrolling(this.mBottomPanel, Math.max(0.0f, view.getY() + (float)view.getHeight() - (float)this.mBottomPanel.getTop()));
                } else {
                    view = this.mBottomPanel;
                    this.setScrolling(view, view.getHeight());
                }
            } else {
                this.setScrolling(this.mBottomPanel, 0.0f);
            }
        }
    }

    @Override
    public void setForegroundGravity(int n) {
        if (this.getForegroundGravity() != n) {
            super.setForegroundGravity(n);
            Drawable drawable2 = this.getForeground();
            if (this.getForegroundGravity() == 119 && drawable2 != null) {
                Rect rect = new Rect();
                if (drawable2.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                }
            } else {
                this.mForegroundPaddingLeft = 0;
                this.mForegroundPaddingTop = 0;
                this.mForegroundPaddingRight = 0;
                this.mForegroundPaddingBottom = 0;
            }
        }
    }
}

