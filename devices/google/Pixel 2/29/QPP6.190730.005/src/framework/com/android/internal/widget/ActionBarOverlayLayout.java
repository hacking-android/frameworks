/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.util.Log;
import android.util.Property;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.OverScroller;
import android.widget.Toolbar;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.widget.ActionBarContainer;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.DecorContentParent;
import com.android.internal.widget.DecorToolbar;

public class ActionBarOverlayLayout
extends ViewGroup
implements DecorContentParent {
    public static final Property<ActionBarOverlayLayout, Integer> ACTION_BAR_HIDE_OFFSET = new IntProperty<ActionBarOverlayLayout>("actionBarHideOffset"){

        @Override
        public Integer get(ActionBarOverlayLayout actionBarOverlayLayout) {
            return actionBarOverlayLayout.getActionBarHideOffset();
        }

        @Override
        public void setValue(ActionBarOverlayLayout actionBarOverlayLayout, int n) {
            actionBarOverlayLayout.setActionBarHideOffset(n);
        }
    };
    static final int[] ATTRS = new int[]{16843499, 16842841};
    private static final String TAG = "ActionBarOverlayLayout";
    private final int ACTION_BAR_ANIMATE_DELAY;
    private ActionBarContainer mActionBarBottom;
    private int mActionBarHeight;
    private ActionBarContainer mActionBarTop;
    private ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final Runnable mAddActionBarHideOffset = new Runnable(){

        @Override
        public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(-ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
            if (ActionBarOverlayLayout.this.mActionBarBottom != null && ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8) {
                actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarBottomAnimator = actionBarOverlayLayout.mActionBarBottom.animate().translationY(ActionBarOverlayLayout.this.mActionBarBottom.getHeight()).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener);
            }
        }
    };
    private boolean mAnimatingForFling;
    private final Rect mBaseContentInsets = new Rect();
    private WindowInsets mBaseInnerInsets = WindowInsets.CONSUMED;
    private final Animator.AnimatorListener mBottomAnimatorListener = new AnimatorListenerAdapter(){

        @Override
        public void onAnimationCancel(Animator animator2) {
            ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }
    };
    private View mContent;
    private final Rect mContentInsets = new Rect();
    private ViewPropertyAnimator mCurrentActionBarBottomAnimator;
    private ViewPropertyAnimator mCurrentActionBarTopAnimator;
    private DecorToolbar mDecorToolbar;
    private OverScroller mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private WindowInsets mInnerInsets = WindowInsets.CONSUMED;
    private final Rect mLastBaseContentInsets = new Rect();
    private WindowInsets mLastBaseInnerInsets = WindowInsets.CONSUMED;
    private WindowInsets mLastInnerInsets = WindowInsets.CONSUMED;
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final Runnable mRemoveActionBarHideOffset = new Runnable(){

        @Override
        public void run() {
            ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.mCurrentActionBarTopAnimator = actionBarOverlayLayout.mActionBarTop.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mTopAnimatorListener);
            if (ActionBarOverlayLayout.this.mActionBarBottom != null && ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8) {
                actionBarOverlayLayout = ActionBarOverlayLayout.this;
                actionBarOverlayLayout.mCurrentActionBarBottomAnimator = actionBarOverlayLayout.mActionBarBottom.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.mBottomAnimatorListener);
            }
        }
    };
    private final Animator.AnimatorListener mTopAnimatorListener = new AnimatorListenerAdapter(){

        @Override
        public void onAnimationCancel(Animator animator2) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
            ActionBarOverlayLayout.this.mAnimatingForFling = false;
        }
    };
    private Drawable mWindowContentOverlay;
    private int mWindowVisibility = 0;

    public ActionBarOverlayLayout(Context context) {
        super(context);
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.init(context);
    }

    @UnsupportedAppUsage
    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.init(context);
    }

    private void addActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean applyInsets(View object, Rect rect, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        boolean bl5 = false;
        object = (LayoutParams)((View)object).getLayoutParams();
        boolean bl6 = bl5;
        if (bl) {
            bl6 = bl5;
            if (((LayoutParams)object).leftMargin != rect.left) {
                bl6 = true;
                ((LayoutParams)object).leftMargin = rect.left;
            }
        }
        bl = bl6;
        if (bl2) {
            bl = bl6;
            if (((LayoutParams)object).topMargin != rect.top) {
                bl = true;
                ((LayoutParams)object).topMargin = rect.top;
            }
        }
        bl2 = bl;
        if (bl4) {
            bl2 = bl;
            if (((LayoutParams)object).rightMargin != rect.right) {
                bl2 = true;
                ((LayoutParams)object).rightMargin = rect.right;
            }
        }
        bl = bl2;
        if (bl3) {
            bl = bl2;
            if (((LayoutParams)object).bottomMargin != rect.bottom) {
                bl = true;
                ((LayoutParams)object).bottomMargin = rect.bottom;
            }
        }
        return bl;
    }

    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar)((Object)view);
        }
        if (view instanceof Toolbar) {
            return ((Toolbar)view).getWrapper();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't make a decor toolbar out of ");
        stringBuilder.append(view.getClass().getSimpleName());
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void haltActionBarHideOffsetAnimations() {
        this.removeCallbacks(this.mRemoveActionBarHideOffset);
        this.removeCallbacks(this.mAddActionBarHideOffset);
        ViewPropertyAnimator viewPropertyAnimator = this.mCurrentActionBarTopAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        if ((viewPropertyAnimator = this.mCurrentActionBarBottomAnimator) != null) {
            viewPropertyAnimator.cancel();
        }
    }

    private void init(Context context) {
        TypedArray typedArray = this.getContext().getTheme().obtainStyledAttributes(ATTRS);
        boolean bl = false;
        this.mActionBarHeight = typedArray.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = typedArray.getDrawable(1);
        boolean bl2 = this.mWindowContentOverlay == null;
        this.setWillNotDraw(bl2);
        typedArray.recycle();
        bl2 = bl;
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            bl2 = true;
        }
        this.mIgnoreWindowContentOverlay = bl2;
        this.mFlingEstimator = new OverScroller(context);
    }

    private void postAddActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.postDelayed(this.mAddActionBarHideOffset, 600L);
    }

    private void postRemoveActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.postDelayed(this.mRemoveActionBarHideOffset, 600L);
    }

    private void removeActionBarHideOffset() {
        this.haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float f, float f2) {
        this.mFlingEstimator.fling(0, 0, 0, (int)f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        boolean bl = this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight();
        return bl;
    }

    @Override
    public boolean canShowOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    public void dismissPopups() {
        this.pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
            int n = this.mActionBarTop.getVisibility() == 0 ? (int)((float)this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5f) : 0;
            this.mWindowContentOverlay.setBounds(0, n, this.getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + n);
            this.mWindowContentOverlay.draw(canvas);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.mActionBarTop;
        int n = actionBarContainer != null ? -((int)actionBarContainer.getTranslationY()) : 0;
        return n;
    }

    @Override
    public CharSequence getTitle() {
        this.pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    @Override
    public boolean hasIcon() {
        this.pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    @Override
    public boolean hasLogo() {
        this.pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    @Override
    public boolean hideOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override
    public void initFeature(int n) {
        this.pullChildren();
        if (n != 2) {
            if (n != 5) {
                if (n == 9) {
                    this.setOverlayMode(true);
                }
            } else {
                this.mDecorToolbar.initIndeterminateProgress();
            }
        } else {
            this.mDecorToolbar.initProgress();
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        this.pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        this.pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.pullChildren();
        this.getWindowSystemUiVisibility();
        Rect rect = windowInsets.getSystemWindowInsetsAsRect();
        boolean bl = this.applyInsets(this.mActionBarTop, rect, true, true, false, true);
        ActionBarContainer actionBarContainer = this.mActionBarBottom;
        boolean bl2 = bl;
        if (actionBarContainer != null) {
            bl2 = bl | this.applyInsets(actionBarContainer, rect, true, false, true, true);
        }
        this.computeSystemWindowInsets(windowInsets, this.mBaseContentInsets);
        this.mBaseInnerInsets = windowInsets.inset(this.mBaseContentInsets);
        if (!this.mLastBaseInnerInsets.equals(this.mBaseInnerInsets)) {
            bl2 = true;
            this.mLastBaseInnerInsets = this.mBaseInnerInsets;
        }
        if (!this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            bl2 = true;
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (bl2) {
            this.requestLayout();
        }
        return WindowInsets.CONSUMED;
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init(this.getContext());
        this.requestApplyInsets();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.haltActionBarHideOffsetAnimations();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeft();
        this.getPaddingRight();
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingBottom();
        for (n = 0; n < n5; ++n) {
            View view = this.getChildAt(n);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            int n9 = view.getMeasuredWidth();
            int n10 = view.getMeasuredHeight();
            int n11 = layoutParams.leftMargin + n6;
            n3 = view == this.mActionBarBottom ? n4 - n2 - n8 - n10 - layoutParams.bottomMargin : layoutParams.topMargin + n7;
            view.layout(n11, n3, n11 + n9, n3 + n10);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        boolean bl;
        int n3;
        this.pullChildren();
        int n4 = 0;
        int n5 = 0;
        this.measureChildWithMargins(this.mActionBarTop, n, 0, n2, 0);
        Object object = (LayoutParams)this.mActionBarTop.getLayoutParams();
        int n6 = Math.max(0, this.mActionBarTop.getMeasuredWidth() + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin);
        int n7 = Math.max(0, this.mActionBarTop.getMeasuredHeight() + ((LayoutParams)object).topMargin + ((LayoutParams)object).bottomMargin);
        int n8 = ActionBarOverlayLayout.combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
        object = this.mActionBarBottom;
        if (object != null) {
            this.measureChildWithMargins((View)object, n, 0, n2, 0);
            object = (LayoutParams)this.mActionBarBottom.getLayoutParams();
            n6 = Math.max(n6, this.mActionBarBottom.getMeasuredWidth() + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin);
            n7 = Math.max(n7, this.mActionBarBottom.getMeasuredHeight() + ((LayoutParams)object).topMargin + ((LayoutParams)object).bottomMargin);
            n8 = ActionBarOverlayLayout.combineMeasuredStates(n8, this.mActionBarBottom.getMeasuredState());
        }
        if (bl = (this.getWindowSystemUiVisibility() & 256) != 0) {
            n4 = n3 = this.mActionBarHeight;
            if (this.mHasNonEmbeddedTabs) {
                n4 = n3;
                if (this.mActionBarTop.getTabContainer() != null) {
                    n4 = n3 + this.mActionBarHeight;
                }
            }
        } else if (this.mActionBarTop.getVisibility() != 8) {
            n4 = this.mActionBarTop.getMeasuredHeight();
        }
        n3 = n5;
        if (this.mDecorToolbar.isSplit()) {
            object = this.mActionBarBottom;
            n3 = n5;
            if (object != null) {
                n3 = bl ? this.mActionBarHeight : ((View)object).getMeasuredHeight();
            }
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        this.mInnerInsets = this.mBaseInnerInsets;
        if (!this.mOverlayMode && !bl) {
            object = this.mContentInsets;
            ((Rect)object).top += n4;
            object = this.mContentInsets;
            ((Rect)object).bottom += n3;
            this.mInnerInsets = this.mInnerInsets.inset(0, n4, 0, n3);
        } else {
            object = this.mInnerInsets;
            this.mInnerInsets = ((WindowInsets)object).replaceSystemWindowInsets(((WindowInsets)object).getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + n4, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom() + n3);
        }
        this.applyInsets(this.mContent, this.mContentInsets, true, true, true, true);
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            this.mLastInnerInsets = object = this.mInnerInsets;
            this.mContent.dispatchApplyWindowInsets((WindowInsets)object);
        }
        this.measureChildWithMargins(this.mContent, n, 0, n2, 0);
        object = (LayoutParams)this.mContent.getLayoutParams();
        n4 = Math.max(n6, this.mContent.getMeasuredWidth() + ((LayoutParams)object).leftMargin + ((LayoutParams)object).rightMargin);
        n3 = Math.max(n7, this.mContent.getMeasuredHeight() + ((LayoutParams)object).topMargin + ((LayoutParams)object).bottomMargin);
        n7 = ActionBarOverlayLayout.combineMeasuredStates(n8, this.mContent.getMeasuredState());
        n6 = this.getPaddingLeft();
        n8 = this.getPaddingRight();
        n3 = Math.max(n3 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(ActionBarOverlayLayout.resolveSizeAndState(Math.max(n4 + (n6 + n8), this.getSuggestedMinimumWidth()), n, n7), ActionBarOverlayLayout.resolveSizeAndState(n3, n2, n7 << 16));
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (this.mHideOnContentScroll && bl) {
            if (this.shouldHideActionBarOnFling(f, f2)) {
                this.addActionBarHideOffset();
            } else {
                this.removeActionBarHideOffset();
            }
            this.mAnimatingForFling = true;
            return true;
        }
        return false;
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.mHideOnContentScrollReference += n2;
        this.setActionBarHideOffset(this.mHideOnContentScrollReference);
    }

    @Override
    public void onNestedScrollAccepted(View object, View view, int n) {
        super.onNestedScrollAccepted((View)object, view, n);
        this.mHideOnContentScrollReference = this.getActionBarHideOffset();
        this.haltActionBarHideOffsetAnimations();
        object = this.mActionBarVisibilityCallback;
        if (object != null) {
            object.onContentScrollStarted();
        }
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        if ((n & 2) != 0 && this.mActionBarTop.getVisibility() == 0) {
            return this.mHideOnContentScroll;
        }
        return false;
    }

    @Override
    public void onStopNestedScroll(View object) {
        super.onStopNestedScroll((View)object);
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                this.postRemoveActionBarHideOffset();
            } else {
                this.postAddActionBarHideOffset();
            }
        }
        if ((object = this.mActionBarVisibilityCallback) != null) {
            object.onContentScrollStopped();
        }
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int n) {
        super.onWindowSystemUiVisibilityChanged(n);
        this.pullChildren();
        int n2 = this.mLastSystemUiVisibility;
        this.mLastSystemUiVisibility = n;
        boolean bl = true;
        boolean bl2 = (n & 4) == 0;
        boolean bl3 = (n & 256) != 0;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            if (bl3) {
                bl = false;
            }
            actionBarVisibilityCallback.enableContentAnimations(bl);
            if (!bl2 && bl3) {
                this.mActionBarVisibilityCallback.hideForSystem();
            } else {
                this.mActionBarVisibilityCallback.showForSystem();
            }
        }
        if (((n2 ^ n) & 256) != 0 && this.mActionBarVisibilityCallback != null) {
            this.requestApplyInsets();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        this.mWindowVisibility = n;
        ActionBarVisibilityCallback actionBarVisibilityCallback = this.mActionBarVisibilityCallback;
        if (actionBarVisibilityCallback != null) {
            actionBarVisibilityCallback.onWindowVisibilityChanged(n);
        }
    }

    void pullChildren() {
        if (this.mContent == null) {
            this.mContent = this.findViewById(16908290);
            this.mActionBarTop = (ActionBarContainer)this.findViewById(16908683);
            this.mDecorToolbar = this.getDecorToolbar((View)this.findViewById(16908682));
            this.mActionBarBottom = (ActionBarContainer)this.findViewById(16909391);
        }
    }

    @Override
    public void restoreToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.pullChildren();
        this.mDecorToolbar.restoreHierarchyState(sparseArray);
    }

    @Override
    public void saveToolbarHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.pullChildren();
        this.mDecorToolbar.saveHierarchyState(sparseArray);
    }

    public void setActionBarHideOffset(int n) {
        this.haltActionBarHideOffsetAnimations();
        int n2 = this.mActionBarTop.getHeight();
        n = Math.max(0, Math.min(n, n2));
        this.mActionBarTop.setTranslationY(-n);
        ActionBarContainer actionBarContainer = this.mActionBarBottom;
        if (actionBarContainer != null && actionBarContainer.getVisibility() != 8) {
            float f = (float)n / (float)n2;
            n = (int)((float)this.mActionBarBottom.getHeight() * f);
            this.mActionBarBottom.setTranslationY(n);
        }
    }

    public void setActionBarVisibilityCallback(ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.mActionBarVisibilityCallback = actionBarVisibilityCallback;
        if (this.getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            if (this.mLastSystemUiVisibility != 0) {
                this.onWindowSystemUiVisibilityChanged(this.mLastSystemUiVisibility);
                this.requestApplyInsets();
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean bl) {
        this.mHasNonEmbeddedTabs = bl;
    }

    public void setHideOnContentScrollEnabled(boolean bl) {
        if (bl != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = bl;
            if (!bl) {
                this.stopNestedScroll();
                this.haltActionBarHideOffsetAnimations();
                this.setActionBarHideOffset(0);
            }
        }
    }

    @Override
    public void setIcon(int n) {
        this.pullChildren();
        this.mDecorToolbar.setIcon(n);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.pullChildren();
        this.mDecorToolbar.setIcon(drawable2);
    }

    @Override
    public void setLogo(int n) {
        this.pullChildren();
        this.mDecorToolbar.setLogo(n);
    }

    @Override
    public void setMenu(Menu menu2, MenuPresenter.Callback callback) {
        this.pullChildren();
        this.mDecorToolbar.setMenu(menu2, callback);
    }

    @Override
    public void setMenuPrepared() {
        this.pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    public void setOverlayMode(boolean bl) {
        this.mOverlayMode = bl;
        bl = bl && this.getContext().getApplicationInfo().targetSdkVersion < 19;
        this.mIgnoreWindowContentOverlay = bl;
    }

    public void setShowingForActionMode(boolean bl) {
        if (bl) {
            if ((this.getWindowSystemUiVisibility() & 1280) == 1280) {
                this.setDisabledSystemUiVisibility(4);
            }
        } else {
            this.setDisabledSystemUiVisibility(0);
        }
    }

    @Override
    public void setUiOptions(int n) {
        boolean bl = false;
        boolean bl2 = (n & 1) != 0;
        if (bl2) {
            bl = this.getContext().getResources().getBoolean(17891616);
        }
        if (bl) {
            this.pullChildren();
            if (this.mActionBarBottom != null && this.mDecorToolbar.canSplit()) {
                this.mDecorToolbar.setSplitView(this.mActionBarBottom);
                this.mDecorToolbar.setSplitToolbar(bl);
                this.mDecorToolbar.setSplitWhenNarrow(bl2);
                ActionBarContextView actionBarContextView = (ActionBarContextView)this.findViewById(16908687);
                actionBarContextView.setSplitView(this.mActionBarBottom);
                actionBarContextView.setSplitToolbar(bl);
                actionBarContextView.setSplitWhenNarrow(bl2);
            } else if (bl) {
                Log.e(TAG, "Requested split action bar with incompatible window decor! Ignoring request.");
            }
        }
    }

    @UnsupportedAppUsage
    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.pullChildren();
        this.mDecorToolbar.setWindowCallback(callback);
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        this.pullChildren();
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public boolean showOverflowMenu() {
        this.pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    public static interface ActionBarVisibilityCallback {
        public void enableContentAnimations(boolean var1);

        public void hideForSystem();

        public void onContentScrollStarted();

        public void onContentScrollStopped();

        public void onWindowVisibilityChanged(int var1);

        public void showForSystem();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

}

