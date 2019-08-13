/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import com.android.internal.R;

public abstract class AbsActionBarView
extends ViewGroup {
    private static final int FADE_DURATION = 200;
    private static final TimeInterpolator sAlphaInterpolator = new DecelerateInterpolator();
    protected ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected ActionMenuView mMenuView;
    protected final Context mPopupContext;
    protected boolean mSplitActionBar;
    protected ViewGroup mSplitView;
    protected boolean mSplitWhenNarrow;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected Animator mVisibilityAnim;

    public AbsActionBarView(Context context) {
        this(context, null);
    }

    public AbsActionBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsActionBarView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AbsActionBarView(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        object = new TypedValue();
        this.mPopupContext = context.getTheme().resolveAttribute(16843917, (TypedValue)object, true) && ((TypedValue)object).resourceId != 0 ? new ContextThemeWrapper(context, ((TypedValue)object).resourceId) : context;
    }

    protected static int next(int n, int n2, boolean bl) {
        n = bl ? (n -= n2) : (n += n2);
        return n;
    }

    public void animateToVisibility(int n) {
        this.setupAnimatorToVisibility(n, 200L).start();
    }

    public boolean canShowOverflowMenu() {
        boolean bl = this.isOverflowReserved() && this.getVisibility() == 0;
        return bl;
    }

    @UnsupportedAppUsage
    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus();
        }
    }

    public int getAnimatedVisibility() {
        if (this.mVisibilityAnim != null) {
            return this.mVisAnimListener.mFinalVisibility;
        }
        return this.getVisibility();
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowReserved() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        boolean bl = actionMenuPresenter != null && actionMenuPresenter.isOverflowReserved();
        return bl;
    }

    protected int measureChildView(View view, int n, int n2, int n3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(n, Integer.MIN_VALUE), n2);
        return Math.max(0, n - view.getMeasuredWidth() - n3);
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Object object = this.getContext().obtainStyledAttributes(null, R.styleable.ActionBar, 16843470, 0);
        this.setContentHeight(((TypedArray)object).getLayoutDimension(4, 0));
        ((TypedArray)object).recycle();
        if (this.mSplitWhenNarrow) {
            this.setSplitToolbar(this.getContext().getResources().getBoolean(17891616));
        }
        if ((object = this.mActionMenuPresenter) != null) {
            ((ActionMenuPresenter)object).onConfigurationChanged(configuration);
        }
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n == 9 && !bl) {
                this.mEatingHover = true;
            }
        }
        if (n == 10 || n == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n == 0 && !bl) {
                this.mEatingTouch = true;
            }
        }
        if (n == 1 || n == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    protected int positionChild(View view, int n, int n2, int n3, boolean bl) {
        int n4 = view.getMeasuredWidth();
        int n5 = view.getMeasuredHeight();
        n2 = (n3 - n5) / 2 + n2;
        if (bl) {
            view.layout(n - n4, n2, n, n2 + n5);
        } else {
            view.layout(n, n2, n + n4, n2 + n5);
        }
        n = bl ? -n4 : n4;
        return n;
    }

    public void postShowOverflowMenu() {
        this.post(new Runnable(){

            @Override
            public void run() {
                AbsActionBarView.this.showOverflowMenu();
            }
        });
    }

    public void setContentHeight(int n) {
        this.mContentHeight = n;
        this.requestLayout();
    }

    public void setSplitToolbar(boolean bl) {
        this.mSplitActionBar = bl;
    }

    public void setSplitView(ViewGroup viewGroup) {
        this.mSplitView = viewGroup;
    }

    public void setSplitWhenNarrow(boolean bl) {
        this.mSplitWhenNarrow = bl;
    }

    @Override
    public void setVisibility(int n) {
        if (n != this.getVisibility()) {
            Animator animator2 = this.mVisibilityAnim;
            if (animator2 != null) {
                animator2.end();
            }
            super.setVisibility(n);
        }
    }

    public Animator setupAnimatorToVisibility(int n, long l) {
        Object object = this.mVisibilityAnim;
        if (object != null) {
            ((Animator)object).cancel();
        }
        if (n == 0) {
            if (this.getVisibility() != 0) {
                this.setAlpha(0.0f);
                if (this.mSplitView != null && (object = this.mMenuView) != null) {
                    ((View)object).setAlpha(0.0f);
                }
            }
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0f);
            objectAnimator.setDuration(l);
            objectAnimator.setInterpolator(sAlphaInterpolator);
            if (this.mSplitView != null && this.mMenuView != null) {
                object = new AnimatorSet();
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(this.mMenuView, View.ALPHA, 1.0f);
                objectAnimator2.setDuration(l);
                ((Animator)object).addListener(this.mVisAnimListener.withFinalVisibility(n));
                ((AnimatorSet)object).play(objectAnimator).with(objectAnimator2);
                return object;
            }
            objectAnimator.addListener(this.mVisAnimListener.withFinalVisibility(n));
            return objectAnimator;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0.0f);
        objectAnimator.setDuration(l);
        objectAnimator.setInterpolator(sAlphaInterpolator);
        if (this.mSplitView != null && this.mMenuView != null) {
            object = new AnimatorSet();
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(this.mMenuView, View.ALPHA, 0.0f);
            objectAnimator3.setDuration(l);
            ((Animator)object).addListener(this.mVisAnimListener.withFinalVisibility(n));
            ((AnimatorSet)object).play(objectAnimator).with(objectAnimator3);
            return object;
        }
        objectAnimator.addListener(this.mVisAnimListener.withFinalVisibility(n));
        return objectAnimator;
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mActionMenuPresenter;
        if (actionMenuPresenter != null) {
            return actionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    protected class VisibilityAnimListener
    implements Animator.AnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        @Override
        public void onAnimationEnd(Animator object) {
            if (this.mCanceled) {
                return;
            }
            object = AbsActionBarView.this;
            ((AbsActionBarView)object).mVisibilityAnim = null;
            ((AbsActionBarView)object).setVisibility(this.mFinalVisibility);
            if (AbsActionBarView.this.mSplitView != null && AbsActionBarView.this.mMenuView != null) {
                AbsActionBarView.this.mMenuView.setVisibility(this.mFinalVisibility);
            }
        }

        @Override
        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            AbsActionBarView.this.setVisibility(0);
            AbsActionBarView.this.mVisibilityAnim = animator2;
            this.mCanceled = false;
        }

        public VisibilityAnimListener withFinalVisibility(int n) {
            this.mFinalVisibility = n;
            return this;
        }
    }

}

