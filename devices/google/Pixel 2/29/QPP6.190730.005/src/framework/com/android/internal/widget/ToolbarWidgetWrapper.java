/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ActionMenuPresenter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toolbar;
import com.android.internal.R;
import com.android.internal.view.menu.ActionMenuItem;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ScrollingTabContainerView;

public class ToolbarWidgetWrapper
implements DecorToolbar {
    private static final int AFFECTS_LOGO_MASK = 3;
    private static final long DEFAULT_FADE_DURATION_MS = 200L;
    private static final String TAG = "ToolbarWidgetWrapper";
    private ActionMenuPresenter mActionMenuPresenter;
    private View mCustomView;
    private int mDefaultNavigationContentDescription = 0;
    private Drawable mDefaultNavigationIcon;
    private int mDisplayOpts;
    private CharSequence mHomeDescription;
    private Drawable mIcon;
    private Drawable mLogo;
    private boolean mMenuPrepared;
    private Drawable mNavIcon;
    private int mNavigationMode = 0;
    private Spinner mSpinner;
    private CharSequence mSubtitle;
    private View mTabView;
    private CharSequence mTitle;
    private boolean mTitleSet;
    private Toolbar mToolbar;
    private Window.Callback mWindowCallback;

    public ToolbarWidgetWrapper(Toolbar toolbar, boolean bl) {
        this(toolbar, bl, 17039447);
    }

    public ToolbarWidgetWrapper(Toolbar object, boolean bl, int n) {
        this.mToolbar = object;
        this.mTitle = ((Toolbar)object).getTitle();
        this.mSubtitle = ((Toolbar)object).getSubtitle();
        boolean bl2 = this.mTitle != null;
        this.mTitleSet = bl2;
        this.mNavIcon = this.mToolbar.getNavigationIcon();
        object = ((View)object).getContext().obtainStyledAttributes(null, R.styleable.ActionBar, 16843470, 0);
        this.mDefaultNavigationIcon = ((TypedArray)object).getDrawable(13);
        if (bl) {
            Object object2 = ((TypedArray)object).getText(5);
            if (!TextUtils.isEmpty((CharSequence)object2)) {
                this.setTitle((CharSequence)object2);
            }
            if (!TextUtils.isEmpty((CharSequence)(object2 = ((TypedArray)object).getText(9)))) {
                this.setSubtitle((CharSequence)object2);
            }
            if ((object2 = ((TypedArray)object).getDrawable(6)) != null) {
                this.setLogo((Drawable)object2);
            }
            if ((object2 = ((TypedArray)object).getDrawable(0)) != null) {
                this.setIcon((Drawable)object2);
            }
            if (this.mNavIcon == null && (object2 = this.mDefaultNavigationIcon) != null) {
                this.setNavigationIcon((Drawable)object2);
            }
            this.setDisplayOptions(((TypedArray)object).getInt(8, 0));
            int n2 = ((TypedArray)object).getResourceId(10, 0);
            if (n2 != 0) {
                this.setCustomView(LayoutInflater.from(this.mToolbar.getContext()).inflate(n2, (ViewGroup)this.mToolbar, false));
                this.setDisplayOptions(this.mDisplayOpts | 16);
            }
            if ((n2 = ((TypedArray)object).getLayoutDimension(4, 0)) > 0) {
                object2 = this.mToolbar.getLayoutParams();
                ((ViewGroup.LayoutParams)object2).height = n2;
                this.mToolbar.setLayoutParams((ViewGroup.LayoutParams)object2);
            }
            int n3 = ((TypedArray)object).getDimensionPixelOffset(22, -1);
            n2 = ((TypedArray)object).getDimensionPixelOffset(23, -1);
            if (n3 >= 0 || n2 >= 0) {
                this.mToolbar.setContentInsetsRelative(Math.max(n3, 0), Math.max(n2, 0));
            }
            if ((n2 = ((TypedArray)object).getResourceId(11, 0)) != 0) {
                object2 = this.mToolbar;
                ((Toolbar)object2).setTitleTextAppearance(((View)object2).getContext(), n2);
            }
            if ((n2 = ((TypedArray)object).getResourceId(12, 0)) != 0) {
                object2 = this.mToolbar;
                ((Toolbar)object2).setSubtitleTextAppearance(((View)object2).getContext(), n2);
            }
            if ((n2 = ((TypedArray)object).getResourceId(26, 0)) != 0) {
                this.mToolbar.setPopupTheme(n2);
            }
        } else {
            this.mDisplayOpts = this.detectDisplayOptions();
        }
        ((TypedArray)object).recycle();
        this.setDefaultNavigationContentDescription(n);
        this.mHomeDescription = this.mToolbar.getNavigationContentDescription();
        this.mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            final ActionMenuItem mNavItem;
            {
                this.mNavItem = new ActionMenuItem(ToolbarWidgetWrapper.this.mToolbar.getContext(), 0, 16908332, 0, 0, ToolbarWidgetWrapper.this.mTitle);
            }

            @Override
            public void onClick(View view) {
                if (ToolbarWidgetWrapper.this.mWindowCallback != null && ToolbarWidgetWrapper.this.mMenuPrepared) {
                    ToolbarWidgetWrapper.this.mWindowCallback.onMenuItemSelected(0, this.mNavItem);
                }
            }
        });
    }

    private int detectDisplayOptions() {
        int n = 11;
        if (this.mToolbar.getNavigationIcon() != null) {
            n = 11 | 4;
            this.mDefaultNavigationIcon = this.mToolbar.getNavigationIcon();
        }
        return n;
    }

    private void ensureSpinner() {
        if (this.mSpinner == null) {
            this.mSpinner = new Spinner(this.getContext(), null, 16843479);
            Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -2, 8388627);
            this.mSpinner.setLayoutParams(layoutParams);
        }
    }

    private void setTitleInt(CharSequence charSequence) {
        this.mTitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setTitle(charSequence);
        }
    }

    private void updateHomeAccessibility() {
        if ((this.mDisplayOpts & 4) != 0) {
            if (TextUtils.isEmpty(this.mHomeDescription)) {
                this.mToolbar.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
            } else {
                this.mToolbar.setNavigationContentDescription(this.mHomeDescription);
            }
        }
    }

    private void updateNavigationIcon() {
        if ((this.mDisplayOpts & 4) != 0) {
            Toolbar toolbar = this.mToolbar;
            Drawable drawable2 = this.mNavIcon;
            if (drawable2 == null) {
                drawable2 = this.mDefaultNavigationIcon;
            }
            toolbar.setNavigationIcon(drawable2);
        } else {
            this.mToolbar.setNavigationIcon(null);
        }
    }

    private void updateToolbarLogo() {
        Drawable drawable2 = null;
        int n = this.mDisplayOpts;
        if ((n & 2) != 0) {
            if ((n & 1) != 0) {
                drawable2 = this.mLogo;
                if (drawable2 == null) {
                    drawable2 = this.mIcon;
                }
            } else {
                drawable2 = this.mIcon;
            }
        }
        this.mToolbar.setLogo(drawable2);
    }

    @Override
    public void animateToVisibility(int n) {
        Animator animator2 = this.setupAnimatorToVisibility(n, 200L);
        if (animator2 != null) {
            animator2.start();
        }
    }

    @Override
    public boolean canShowOverflowMenu() {
        return this.mToolbar.canShowOverflowMenu();
    }

    @Override
    public boolean canSplit() {
        return false;
    }

    @Override
    public void collapseActionView() {
        this.mToolbar.collapseActionView();
    }

    @Override
    public void dismissPopupMenus() {
        this.mToolbar.dismissPopupMenus();
    }

    @Override
    public Context getContext() {
        return this.mToolbar.getContext();
    }

    @Override
    public View getCustomView() {
        return this.mCustomView;
    }

    @Override
    public int getDisplayOptions() {
        return this.mDisplayOpts;
    }

    @Override
    public int getDropdownItemCount() {
        Spinner spinner = this.mSpinner;
        int n = spinner != null ? spinner.getCount() : 0;
        return n;
    }

    @Override
    public int getDropdownSelectedPosition() {
        Spinner spinner = this.mSpinner;
        int n = spinner != null ? spinner.getSelectedItemPosition() : 0;
        return n;
    }

    @Override
    public int getHeight() {
        return this.mToolbar.getHeight();
    }

    @Override
    public Menu getMenu() {
        return this.mToolbar.getMenu();
    }

    @Override
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mToolbar.getSubtitle();
    }

    @Override
    public CharSequence getTitle() {
        return this.mToolbar.getTitle();
    }

    @Override
    public ViewGroup getViewGroup() {
        return this.mToolbar;
    }

    @Override
    public int getVisibility() {
        return this.mToolbar.getVisibility();
    }

    @Override
    public boolean hasEmbeddedTabs() {
        boolean bl = this.mTabView != null;
        return bl;
    }

    @Override
    public boolean hasExpandedActionView() {
        return this.mToolbar.hasExpandedActionView();
    }

    @Override
    public boolean hasIcon() {
        boolean bl = this.mIcon != null;
        return bl;
    }

    @Override
    public boolean hasLogo() {
        boolean bl = this.mLogo != null;
        return bl;
    }

    @Override
    public boolean hideOverflowMenu() {
        return this.mToolbar.hideOverflowMenu();
    }

    @Override
    public void initIndeterminateProgress() {
        Log.i(TAG, "Progress display unsupported");
    }

    @Override
    public void initProgress() {
        Log.i(TAG, "Progress display unsupported");
    }

    @Override
    public boolean isOverflowMenuShowPending() {
        return this.mToolbar.isOverflowMenuShowPending();
    }

    @Override
    public boolean isOverflowMenuShowing() {
        return this.mToolbar.isOverflowMenuShowing();
    }

    @Override
    public boolean isSplit() {
        return false;
    }

    @Override
    public boolean isTitleTruncated() {
        return this.mToolbar.isTitleTruncated();
    }

    @Override
    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.restoreHierarchyState(sparseArray);
    }

    @Override
    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.mToolbar.saveHierarchyState(sparseArray);
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        this.mToolbar.setBackgroundDrawable(drawable2);
    }

    @Override
    public void setCollapsible(boolean bl) {
        this.mToolbar.setCollapsible(bl);
    }

    @Override
    public void setCustomView(View view) {
        View view2 = this.mCustomView;
        if (view2 != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.removeView(view2);
        }
        this.mCustomView = view;
        if (view != null && (this.mDisplayOpts & 16) != 0) {
            this.mToolbar.addView(this.mCustomView);
        }
    }

    @Override
    public void setDefaultNavigationContentDescription(int n) {
        if (n == this.mDefaultNavigationContentDescription) {
            return;
        }
        this.mDefaultNavigationContentDescription = n;
        if (TextUtils.isEmpty(this.mToolbar.getNavigationContentDescription())) {
            this.setNavigationContentDescription(this.mDefaultNavigationContentDescription);
        }
    }

    @Override
    public void setDefaultNavigationIcon(Drawable drawable2) {
        if (this.mDefaultNavigationIcon != drawable2) {
            this.mDefaultNavigationIcon = drawable2;
            this.updateNavigationIcon();
        }
    }

    @Override
    public void setDisplayOptions(int n) {
        int n2 = this.mDisplayOpts ^ n;
        this.mDisplayOpts = n;
        if (n2 != 0) {
            View view;
            if ((n2 & 4) != 0) {
                if ((n & 4) != 0) {
                    this.updateHomeAccessibility();
                }
                this.updateNavigationIcon();
            }
            if ((n2 & 3) != 0) {
                this.updateToolbarLogo();
            }
            if ((n2 & 8) != 0) {
                if ((n & 8) != 0) {
                    this.mToolbar.setTitle(this.mTitle);
                    this.mToolbar.setSubtitle(this.mSubtitle);
                } else {
                    this.mToolbar.setTitle(null);
                    this.mToolbar.setSubtitle(null);
                }
            }
            if ((n2 & 16) != 0 && (view = this.mCustomView) != null) {
                if ((n & 16) != 0) {
                    this.mToolbar.addView(view);
                } else {
                    this.mToolbar.removeView(view);
                }
            }
        }
    }

    @Override
    public void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.ensureSpinner();
        this.mSpinner.setAdapter(spinnerAdapter);
        this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override
    public void setDropdownSelectedPosition(int n) {
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setSelection(n);
            return;
        }
        throw new IllegalStateException("Can't set dropdown selected position without an adapter");
    }

    @Override
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        Object object = this.mTabView;
        if (object != null) {
            ViewParent viewParent = ((View)object).getParent();
            object = this.mToolbar;
            if (viewParent == object) {
                ((ViewGroup)object).removeView(this.mTabView);
            }
        }
        this.mTabView = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.mNavigationMode == 2) {
            this.mToolbar.addView(this.mTabView, 0);
            object = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
            ((Toolbar.LayoutParams)object).width = -2;
            ((Toolbar.LayoutParams)object).height = -2;
            ((Toolbar.LayoutParams)object).gravity = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
    }

    @Override
    public void setIcon(int n) {
        Drawable drawable2 = n != 0 ? this.getContext().getDrawable(n) : null;
        this.setIcon(drawable2);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        this.updateToolbarLogo();
    }

    @Override
    public void setLogo(int n) {
        Drawable drawable2 = n != 0 ? this.getContext().getDrawable(n) : null;
        this.setLogo(drawable2);
    }

    @Override
    public void setLogo(Drawable drawable2) {
        this.mLogo = drawable2;
        this.updateToolbarLogo();
    }

    @Override
    public void setMenu(Menu menu2, MenuPresenter.Callback callback) {
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mToolbar.getContext());
            this.mActionMenuPresenter.setId(16908690);
        }
        this.mActionMenuPresenter.setCallback(callback);
        this.mToolbar.setMenu((MenuBuilder)menu2, this.mActionMenuPresenter);
    }

    @Override
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mToolbar.setMenuCallbacks(callback, callback2);
    }

    @Override
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override
    public void setNavigationContentDescription(int n) {
        String string2 = n == 0 ? null : this.getContext().getString(n);
        this.setNavigationContentDescription(string2);
    }

    @Override
    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        this.updateHomeAccessibility();
    }

    @Override
    public void setNavigationIcon(int n) {
        Drawable drawable2 = n != 0 ? this.mToolbar.getContext().getDrawable(n) : null;
        this.setNavigationIcon(drawable2);
    }

    @Override
    public void setNavigationIcon(Drawable drawable2) {
        this.mNavIcon = drawable2;
        this.updateNavigationIcon();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setNavigationMode(int n) {
        Object object;
        int n2 = this.mNavigationMode;
        if (n == n2) return;
        if (n2 != 1) {
            if (n2 == 2 && (object = this.mTabView) != null) {
                ViewParent viewParent = ((View)object).getParent();
                object = this.mToolbar;
                if (viewParent == object) {
                    ((ViewGroup)object).removeView(this.mTabView);
                }
            }
        } else {
            object = this.mSpinner;
            if (object != null) {
                ViewParent viewParent = ((View)object).getParent();
                object = this.mToolbar;
                if (viewParent == object) {
                    ((ViewGroup)object).removeView(this.mSpinner);
                }
            }
        }
        this.mNavigationMode = n;
        if (n == 0) return;
        if (n == 1) {
            this.ensureSpinner();
            this.mToolbar.addView((View)this.mSpinner, 0);
            return;
        }
        if (n == 2) {
            object = this.mTabView;
            if (object == null) return;
            this.mToolbar.addView((View)object, 0);
            object = (Toolbar.LayoutParams)this.mTabView.getLayoutParams();
            ((Toolbar.LayoutParams)object).width = -2;
            ((Toolbar.LayoutParams)object).height = -2;
            ((Toolbar.LayoutParams)object).gravity = 8388691;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid navigation mode ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public void setSplitToolbar(boolean bl) {
        if (!bl) {
            return;
        }
        throw new UnsupportedOperationException("Cannot split an android.widget.Toolbar");
    }

    @Override
    public void setSplitView(ViewGroup viewGroup) {
    }

    @Override
    public void setSplitWhenNarrow(boolean bl) {
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        if ((this.mDisplayOpts & 8) != 0) {
            this.mToolbar.setSubtitle(charSequence);
        }
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTitleSet = true;
        this.setTitleInt(charSequence);
    }

    @Override
    public void setVisibility(int n) {
        this.mToolbar.setVisibility(n);
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        if (!this.mTitleSet) {
            this.setTitleInt(charSequence);
        }
    }

    @Override
    public Animator setupAnimatorToVisibility(int n, long l) {
        if (n == 8) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this.mToolbar, View.ALPHA, 1.0f, 0.0f);
            objectAnimator.setDuration(l);
            objectAnimator.addListener(new AnimatorListenerAdapter(){
                private boolean mCanceled = false;

                @Override
                public void onAnimationCancel(Animator animator2) {
                    this.mCanceled = true;
                }

                @Override
                public void onAnimationEnd(Animator animator2) {
                    if (!this.mCanceled) {
                        ToolbarWidgetWrapper.this.mToolbar.setVisibility(8);
                    }
                }
            });
            return objectAnimator;
        }
        if (n == 0) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this.mToolbar, View.ALPHA, 0.0f, 1.0f);
            objectAnimator.setDuration(l);
            objectAnimator.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationStart(Animator animator2) {
                    ToolbarWidgetWrapper.this.mToolbar.setVisibility(0);
                }
            });
            return objectAnimator;
        }
        return null;
    }

    @Override
    public boolean showOverflowMenu() {
        return this.mToolbar.showOverflowMenu();
    }

}

