/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.AbsSavedState;
import android.view.CollapsibleActionView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.ActionMenuItem;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import com.android.internal.widget.AbsActionBarView;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ScrollingTabContainerView;
import java.util.List;

public class ActionBarView
extends AbsActionBarView
implements DecorToolbar {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 63;
    private static final String TAG = "ActionBarView";
    private ActionBarContextView mContextView;
    private View mCustomNavView;
    private int mDefaultUpDescription = 17039447;
    private int mDisplayOptions = -1;
    View mExpandedActionView;
    private final View.OnClickListener mExpandedActionViewUpListener = new View.OnClickListener(){

        @Override
        public void onClick(View object) {
            object = ActionBarView.access$000((ActionBarView)ActionBarView.this).mCurrentExpandedItem;
            if (object != null) {
                ((MenuItemImpl)object).collapseActionView();
            }
        }
    };
    private HomeView mExpandedHomeLayout;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private CharSequence mHomeDescription;
    private int mHomeDescriptionRes;
    private HomeView mHomeLayout;
    private Drawable mIcon;
    private boolean mIncludeTabs;
    private final int mIndeterminateProgressStyle;
    private ProgressBar mIndeterminateProgressView;
    private boolean mIsCollapsible;
    private int mItemPadding;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    private ActionMenuItem mLogoNavItem;
    private boolean mMenuPrepared;
    private AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    private int mNavigationMode;
    private MenuBuilder mOptionsMenu;
    private int mProgressBarPadding;
    private final int mProgressStyle;
    private ProgressBar mProgressView;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private CharSequence mSubtitle;
    private final int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private ScrollingTabContainerView mTabScrollView;
    private Runnable mTabSelector;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private final int mTitleStyleRes;
    private TextView mTitleView;
    private final View.OnClickListener mUpClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (ActionBarView.this.mMenuPrepared) {
                ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.this.mLogoNavItem);
            }
        }
    };
    private ViewGroup mUpGoerFive;
    private boolean mUserTitle;
    private boolean mWasHomeEnabled;
    Window.Callback mWindowCallback;

    public ActionBarView(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        this.setBackgroundResource(0);
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.ActionBar, 16843470, 0);
        this.mNavigationMode = ((TypedArray)object).getInt(7, 0);
        this.mTitle = ((TypedArray)object).getText(5);
        this.mSubtitle = ((TypedArray)object).getText(9);
        this.mLogo = ((TypedArray)object).getDrawable(6);
        this.mIcon = ((TypedArray)object).getDrawable(0);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int n = ((TypedArray)object).getResourceId(16, 17367066);
        this.mUpGoerFive = (ViewGroup)layoutInflater.inflate(17367069, (ViewGroup)this, false);
        this.mHomeLayout = (HomeView)layoutInflater.inflate(n, this.mUpGoerFive, false);
        this.mExpandedHomeLayout = (HomeView)layoutInflater.inflate(n, this.mUpGoerFive, false);
        this.mExpandedHomeLayout.setShowUp(true);
        this.mExpandedHomeLayout.setOnClickListener(this.mExpandedActionViewUpListener);
        this.mExpandedHomeLayout.setContentDescription(this.getResources().getText(this.mDefaultUpDescription));
        Drawable drawable2 = this.mUpGoerFive.getBackground();
        if (drawable2 != null) {
            this.mExpandedHomeLayout.setBackground(drawable2.getConstantState().newDrawable());
        }
        this.mExpandedHomeLayout.setEnabled(true);
        this.mExpandedHomeLayout.setFocusable(true);
        this.mTitleStyleRes = ((TypedArray)object).getResourceId(11, 0);
        this.mSubtitleStyleRes = ((TypedArray)object).getResourceId(12, 0);
        this.mProgressStyle = ((TypedArray)object).getResourceId(1, 0);
        this.mIndeterminateProgressStyle = ((TypedArray)object).getResourceId(14, 0);
        this.mProgressBarPadding = ((TypedArray)object).getDimensionPixelOffset(15, 0);
        this.mItemPadding = ((TypedArray)object).getDimensionPixelOffset(17, 0);
        this.setDisplayOptions(((TypedArray)object).getInt(8, 0));
        n = ((TypedArray)object).getResourceId(10, 0);
        if (n != 0) {
            this.mCustomNavView = layoutInflater.inflate(n, (ViewGroup)this, false);
            this.mNavigationMode = 0;
            this.setDisplayOptions(16 | this.mDisplayOptions);
        }
        this.mContentHeight = ((TypedArray)object).getLayoutDimension(4, 0);
        ((TypedArray)object).recycle();
        this.mLogoNavItem = new ActionMenuItem(context, 0, 16908332, 0, 0, this.mTitle);
        this.mUpGoerFive.setOnClickListener(this.mUpClickListener);
        this.mUpGoerFive.setClickable(true);
        this.mUpGoerFive.setFocusable(true);
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
    }

    static /* synthetic */ ExpandedActionViewMenuPresenter access$000(ActionBarView actionBarView) {
        return actionBarView.mExpandedMenuPresenter;
    }

    private CharSequence buildHomeContentDescription() {
        CharSequence charSequence = this.mHomeDescription != null ? this.mHomeDescription : ((this.mDisplayOptions & 4) != 0 ? this.mContext.getResources().getText(this.mDefaultUpDescription) : this.mContext.getResources().getText(17039444));
        CharSequence charSequence2 = this.getTitle();
        CharSequence charSequence3 = this.getSubtitle();
        if (!TextUtils.isEmpty(charSequence2)) {
            charSequence = !TextUtils.isEmpty(charSequence3) ? this.getResources().getString(17039446, charSequence2, charSequence3, charSequence) : this.getResources().getString(17039445, charSequence2, charSequence);
            return charSequence;
        }
        return charSequence;
    }

    private void configPresenters(MenuBuilder menuBuilder) {
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            this.mActionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mActionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
    }

    private void initTitle() {
        if (this.mTitleLayout == null) {
            CharSequence charSequence;
            this.mTitleLayout = (LinearLayout)LayoutInflater.from(this.getContext()).inflate(17367068, (ViewGroup)this, false);
            this.mTitleView = (TextView)this.mTitleLayout.findViewById(16908686);
            this.mSubtitleView = (TextView)this.mTitleLayout.findViewById(16908685);
            int n = this.mTitleStyleRes;
            if (n != 0) {
                this.mTitleView.setTextAppearance(n);
            }
            if ((charSequence = this.mTitle) != null) {
                this.mTitleView.setText(charSequence);
            }
            if ((n = this.mSubtitleStyleRes) != 0) {
                this.mSubtitleView.setTextAppearance(n);
            }
            if ((charSequence = this.mSubtitle) != null) {
                this.mSubtitleView.setText(charSequence);
                this.mSubtitleView.setVisibility(0);
            }
        }
        this.mUpGoerFive.addView(this.mTitleLayout);
        if (!(this.mExpandedActionView != null || TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) {
            this.mTitleLayout.setVisibility(0);
        } else {
            this.mTitleLayout.setVisibility(8);
        }
    }

    private void setHomeButtonEnabled(boolean bl, boolean bl2) {
        if (bl2) {
            this.mWasHomeEnabled = bl;
        }
        if (this.mExpandedActionView != null) {
            return;
        }
        this.mUpGoerFive.setEnabled(bl);
        this.mUpGoerFive.setFocusable(bl);
        this.updateHomeAccessibility(bl);
    }

    private void setTitleImpl(CharSequence charSequence) {
        this.mTitle = charSequence;
        Object object = this.mTitleView;
        if (object != null) {
            ((TextView)object).setText(charSequence);
            object = this.mExpandedActionView;
            int n = 8;
            boolean bl = object == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle));
            object = this.mTitleLayout;
            if (bl) {
                n = 0;
            }
            ((View)object).setVisibility(n);
        }
        if ((object = this.mLogoNavItem) != null) {
            ((ActionMenuItem)object).setTitle(charSequence);
        }
        this.updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    private void updateHomeAccessibility(boolean bl) {
        if (!bl) {
            this.mUpGoerFive.setContentDescription(null);
            this.mUpGoerFive.setImportantForAccessibility(2);
        } else {
            this.mUpGoerFive.setImportantForAccessibility(0);
            this.mUpGoerFive.setContentDescription(this.buildHomeContentDescription());
        }
    }

    @Override
    public boolean canSplit() {
        return true;
    }

    @Override
    public void collapseActionView() {
        Object object = this.mExpandedMenuPresenter;
        object = object == null ? null : ((ExpandedActionViewMenuPresenter)object).mCurrentExpandedItem;
        if (object != null) {
            ((MenuItemImpl)object).collapseActionView();
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams(8388627);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ActionBar.LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (layoutParams == null) {
            layoutParams2 = this.generateDefaultLayoutParams();
        }
        return layoutParams2;
    }

    @Override
    public View getCustomView() {
        return this.mCustomNavView;
    }

    @Override
    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    @Override
    public int getDropdownItemCount() {
        SpinnerAdapter spinnerAdapter = this.mSpinnerAdapter;
        int n = spinnerAdapter != null ? spinnerAdapter.getCount() : 0;
        return n;
    }

    @Override
    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    @Override
    public Menu getMenu() {
        return this.mOptionsMenu;
    }

    @Override
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Override
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override
    public ViewGroup getViewGroup() {
        return this;
    }

    @Override
    public boolean hasEmbeddedTabs() {
        return this.mIncludeTabs;
    }

    @Override
    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        boolean bl = expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null;
        return bl;
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
    public void initIndeterminateProgress() {
        this.mIndeterminateProgressView = new ProgressBar(this.mContext, null, 0, this.mIndeterminateProgressStyle);
        this.mIndeterminateProgressView.setId(16909260);
        this.mIndeterminateProgressView.setVisibility(8);
        this.addView(this.mIndeterminateProgressView);
    }

    @Override
    public void initProgress() {
        this.mProgressView = new ProgressBar(this.mContext, null, 0, this.mProgressStyle);
        this.mProgressView.setId(16909261);
        this.mProgressView.setMax(10000);
        this.mProgressView.setVisibility(8);
        this.addView(this.mProgressView);
    }

    @Override
    public boolean isSplit() {
        return this.mSplitActionBar;
    }

    @Override
    public boolean isTitleTruncated() {
        Object object = this.mTitleView;
        if (object == null) {
            return false;
        }
        if ((object = ((TextView)object).getLayout()) == null) {
            return false;
        }
        int n = ((Layout)object).getLineCount();
        for (int i = 0; i < n; ++i) {
            if (((Layout)object).getEllipsisCount(i) <= 0) continue;
            return true;
        }
        return false;
    }

    @Override
    protected void onConfigurationChanged(Configuration object) {
        ViewGroup viewGroup;
        int n;
        super.onConfigurationChanged((Configuration)object);
        this.mTitleView = null;
        this.mSubtitleView = null;
        object = this.mTitleLayout;
        if (object != null && (object = ((View)object).getParent()) == (viewGroup = this.mUpGoerFive)) {
            viewGroup.removeView(this.mTitleLayout);
        }
        this.mTitleLayout = null;
        if ((this.mDisplayOptions & 8) != 0) {
            this.initTitle();
        }
        if ((n = this.mHomeDescriptionRes) != 0) {
            this.setNavigationContentDescription(n);
        }
        if ((object = this.mTabScrollView) != null && this.mIncludeTabs) {
            if ((object = ((View)object).getLayoutParams()) != null) {
                ((ViewGroup.LayoutParams)object).width = -2;
                ((ViewGroup.LayoutParams)object).height = -1;
            }
            this.mTabScrollView.setAllowCollapse(true);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mTabSelector);
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mUpGoerFive.addView((View)this.mHomeLayout, 0);
        this.addView(this.mUpGoerFive);
        Object object = this.mCustomNavView;
        if (object != null && (this.mDisplayOptions & 16) != 0 && (object = ((View)object).getParent()) != this) {
            if (object instanceof ViewGroup) {
                ((ViewGroup)object).removeView(this.mCustomNavView);
            }
            this.addView(this.mCustomNavView);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        var6_6 = var5_5 - var3_3 - this.getPaddingTop() - this.getPaddingBottom();
        if (var6_6 <= 0) {
            return;
        }
        var1_1 = this.isLayoutRtl();
        var5_5 = var1_1 != false ? 1 : -1;
        var3_3 = var1_1 != false ? this.getPaddingLeft() : var4_4 - var2_2 - this.getPaddingRight();
        var7_7 = var1_1 != false ? var4_4 - var2_2 - this.getPaddingRight() : this.getPaddingLeft();
        var8_8 = this.getPaddingTop();
        var9_9 = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
        var10_10 = this.mTitleLayout;
        var4_4 = var10_10 != null && var10_10.getVisibility() != 8 && (this.mDisplayOptions & 8) != 0 ? 1 : 0;
        if (var9_9.getParent() != this.mUpGoerFive) ** GOTO lbl-1000
        if (var9_9.getVisibility() != 8) {
            var2_2 = var9_9.getStartOffset();
        } else if (var4_4 != 0) {
            var2_2 = var9_9.getUpWidth();
        } else lbl-1000: // 2 sources:
        {
            var2_2 = 0;
        }
        var2_2 = ActionBarView.next(var7_7 + this.positionChild(this.mUpGoerFive, ActionBarView.next(var7_7, var2_2, var1_1), var8_8, var6_6, var1_1), var2_2, var1_1);
        if (this.mExpandedActionView == null && (var7_7 = this.mNavigationMode) != 0) {
            if (var7_7 != 1) {
                if (var7_7 == 2 && this.mTabScrollView != null) {
                    var7_7 = var2_2;
                    if (var4_4 != 0) {
                        var7_7 = ActionBarView.next(var2_2, this.mItemPadding, var1_1);
                    }
                    var2_2 = ActionBarView.next(var7_7 + this.positionChild(this.mTabScrollView, var7_7, var8_8, var6_6, var1_1), this.mItemPadding, var1_1);
                }
            } else if (this.mListNavLayout != null) {
                if (var4_4 != 0) {
                    var2_2 = ActionBarView.next(var2_2, this.mItemPadding, var1_1);
                }
                var2_2 = ActionBarView.next(var2_2 + this.positionChild(this.mListNavLayout, var2_2, var8_8, var6_6, var1_1), this.mItemPadding, var1_1);
            }
        }
        if (this.mMenuView != null && this.mMenuView.getParent() == this) {
            this.positionChild(this.mMenuView, var3_3, var8_8, var6_6, var1_1 ^ true);
            var3_3 += this.mMenuView.getMeasuredWidth() * var5_5;
        }
        var11_11 = var2_2;
        var9_9 = this.mIndeterminateProgressView;
        var2_2 = var3_3;
        if (var9_9 != null) {
            var2_2 = var3_3;
            if (var9_9.getVisibility() != 8) {
                this.positionChild(this.mIndeterminateProgressView, var3_3, var8_8, var6_6, var1_1 ^ true);
                var2_2 = var3_3 + this.mIndeterminateProgressView.getMeasuredWidth() * var5_5;
            }
        }
        var10_10 = null;
        if (this.mExpandedActionView != null) {
            var9_9 = this.mExpandedActionView;
        } else {
            var9_9 = var10_10;
            if ((this.mDisplayOptions & 16) != 0) {
                var9_9 = var10_10;
                if (this.mCustomNavView != null) {
                    var9_9 = this.mCustomNavView;
                }
            }
        }
        if (var9_9 != null) {
            var12_12 = this.getLayoutDirection();
            var10_10 = var9_9.getLayoutParams();
            var10_10 = var10_10 instanceof ActionBar.LayoutParams != false ? (ActionBar.LayoutParams)var10_10 : null;
            var7_7 = var10_10 != null ? var10_10.gravity : 8388627;
            var13_13 = var9_9.getMeasuredWidth();
            var6_6 = 0;
            var8_8 = 0;
            var3_3 = var2_2;
            var4_4 = var11_11;
            if (var10_10 != null) {
                var4_4 = ActionBarView.next(var11_11, var10_10.getMarginStart(), var1_1);
                var3_3 = var2_2 + var10_10.getMarginEnd() * var5_5;
                var6_6 = var10_10.topMargin;
                var8_8 = var10_10.bottomMargin;
            }
            if ((var2_2 = 8388615 & var7_7) == 1) {
                var5_5 = this.mRight;
                var5_5 = (var5_5 - this.mLeft - var13_13) / 2;
                if (var1_1) {
                    if (var5_5 + var13_13 > var4_4) {
                        var2_2 = 5;
                    } else if (var5_5 < var3_3) {
                        var2_2 = 3;
                    }
                } else if (var5_5 < var4_4) {
                    var2_2 = 3;
                } else if (var5_5 + var13_13 > var3_3) {
                    var2_2 = 5;
                }
            } else if (var7_7 == 0) {
                var2_2 = 8388611;
            }
            var5_5 = 0;
            var2_2 = Gravity.getAbsoluteGravity(var2_2, var12_12);
            var2_2 = var2_2 != 1 ? (var2_2 != 3 ? (var2_2 != 5 ? var5_5 : (var1_1 ? var4_4 - var13_13 : var3_3 - var13_13)) : (var1_1 ? var3_3 : var4_4)) : (this.mRight - this.mLeft - var13_13) / 2;
            var3_3 = var7_7 & 112;
            if (var7_7 == 0) {
                var3_3 = 16;
            }
            var5_5 = 0;
            if (var3_3 != 16) {
                var3_3 = var3_3 != 48 ? (var3_3 != 80 ? var5_5 : this.getHeight() - this.getPaddingBottom() - var9_9.getMeasuredHeight() - var8_8) : this.getPaddingTop() + var6_6;
            } else {
                var3_3 = this.getPaddingTop();
                var3_3 = (this.mBottom - this.mTop - this.getPaddingBottom() - var3_3 - var9_9.getMeasuredHeight()) / 2;
            }
            var5_5 = var9_9.getMeasuredWidth();
            var9_9.layout(var2_2, var3_3, var2_2 + var5_5, var9_9.getMeasuredHeight() + var3_3);
            ActionBarView.next(var4_4, var5_5, var1_1);
        }
        if ((var9_9 = this.mProgressView) == null) return;
        var9_9.bringToFront();
        var2_2 = this.mProgressView.getMeasuredHeight() / 2;
        var9_9 = this.mProgressView;
        var3_3 = this.mProgressBarPadding;
        var9_9.layout(var3_3, -var2_2, var9_9.getMeasuredWidth() + var3_3, var2_2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected void onMeasure(int var1_1, int var2_2) {
        block42 : {
            block41 : {
                block38 : {
                    var3_3 = this.getChildCount();
                    if (!this.mIsCollapsible) break block38;
                    var4_4 = 0;
                    for (var5_5 = 0; var5_5 < var3_3; ++var5_5) {
                        block39 : {
                            block40 : {
                                var6_6 = this.getChildAt(var5_5);
                                var7_7 = var4_4;
                                if (var6_6.getVisibility() == 8) break block39;
                                if (var6_6 != this.mMenuView) break block40;
                                var7_7 = var4_4;
                                if (this.mMenuView.getChildCount() == 0) break block39;
                            }
                            var7_7 = var4_4;
                            if (var6_6 != this.mUpGoerFive) {
                                var7_7 = var4_4 + 1;
                            }
                        }
                        var4_4 = var7_7;
                    }
                    var8_8 = this.mUpGoerFive.getChildCount();
                    for (var5_5 = 0; var5_5 < var8_8; ++var5_5) {
                        var7_7 = var4_4;
                        if (this.mUpGoerFive.getChildAt(var5_5).getVisibility() != 8) {
                            var7_7 = var4_4 + 1;
                        }
                        var4_4 = var7_7;
                    }
                    if (var4_4 == 0) {
                        this.setMeasuredDimension(0, 0);
                        return;
                    }
                }
                if (View.MeasureSpec.getMode(var1_1) != 1073741824) {
                    var6_6 = new StringBuilder();
                    var6_6.append(this.getClass().getSimpleName());
                    var6_6.append(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
                    throw new IllegalStateException(var6_6.toString());
                }
                if (View.MeasureSpec.getMode(var2_2) != Integer.MIN_VALUE) {
                    var6_6 = new StringBuilder();
                    var6_6.append(this.getClass().getSimpleName());
                    var6_6.append(" can only be used with android:layout_height=\"wrap_content\"");
                    throw new IllegalStateException(var6_6.toString());
                }
                var9_9 = View.MeasureSpec.getSize(var1_1);
                var7_7 = this.mContentHeight >= 0 ? this.mContentHeight : View.MeasureSpec.getSize(var2_2);
                var10_10 = this.getPaddingTop() + this.getPaddingBottom();
                var2_2 = this.getPaddingLeft();
                var1_1 = this.getPaddingRight();
                var11_11 = var7_7 - var10_10;
                var12_12 = View.MeasureSpec.makeMeasureSpec(var11_11, Integer.MIN_VALUE);
                var13_13 = View.MeasureSpec.makeMeasureSpec(var11_11, 1073741824);
                var4_4 = var9_9 - var2_2 - var1_1;
                var14_14 = var1_1 = var4_4 / 2;
                var6_6 = this.mTitleLayout;
                var15_15 = var6_6 != null && var6_6.getVisibility() != 8 && (this.mDisplayOptions & 8) != 0 ? 1 : 0;
                var6_6 = this.mExpandedActionView != null ? this.mExpandedHomeLayout : this.mHomeLayout;
                var16_16 = var6_6.getLayoutParams();
                var2_2 = var16_16.width < 0 ? View.MeasureSpec.makeMeasureSpec(var4_4, Integer.MIN_VALUE) : View.MeasureSpec.makeMeasureSpec(var16_16.width, 1073741824);
                var6_6.measure(var2_2, var13_13);
                var2_2 = var6_6.getVisibility();
                var8_8 = 0;
                if (var2_2 != 8 && var6_6.getParent() == this.mUpGoerFive || var15_15 != 0) {
                    var8_8 = var6_6.getMeasuredWidth();
                    var1_1 = var6_6.getStartOffset() + var8_8;
                    var4_4 = Math.max(0, var4_4 - var1_1);
                    var1_1 = Math.max(0, var4_4 - var1_1);
                }
                var5_5 = var4_4;
                var2_2 = var14_14;
                if (this.mMenuView != null) {
                    var5_5 = var4_4;
                    var2_2 = var14_14;
                    if (this.mMenuView.getParent() == this) {
                        var5_5 = this.measureChildView(this.mMenuView, var4_4, var13_13, 0);
                        var2_2 = Math.max(0, var14_14 - this.mMenuView.getMeasuredWidth());
                    }
                }
                if ((var6_6 = this.mIndeterminateProgressView) != null && var6_6.getVisibility() != 8) {
                    var4_4 = this.measureChildView(this.mIndeterminateProgressView, var5_5, var12_12, 0);
                    var5_5 = Math.max(0, var2_2 - this.mIndeterminateProgressView.getMeasuredWidth());
                } else {
                    var4_4 = var5_5;
                    var5_5 = var2_2;
                }
                if (this.mExpandedActionView != null) ** GOTO lbl-1000
                var2_2 = this.mNavigationMode;
                if (var2_2 == 1) break block41;
                if (var2_2 != 2 || this.mTabScrollView == null) ** GOTO lbl-1000
                var2_2 = var14_14 = this.mItemPadding;
                if (var15_15 != 0) {
                    var2_2 = var14_14 * 2;
                }
                var4_4 = Math.max(0, var4_4 - var2_2);
                var2_2 = Math.max(0, var1_1 - var2_2);
                this.mTabScrollView.measure(View.MeasureSpec.makeMeasureSpec(var4_4, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(var11_11, 1073741824));
                var15_15 = this.mTabScrollView.getMeasuredWidth();
                var1_1 = Math.max(0, var4_4 - var15_15);
                var2_2 = Math.max(0, var2_2 - var15_15);
                break block42;
            }
            if (this.mListNavLayout != null) {
                var2_2 = var14_14 = this.mItemPadding;
                if (var15_15 != 0) {
                    var2_2 = var14_14 * 2;
                }
                var4_4 = Math.max(0, var4_4 - var2_2);
                var15_15 = Math.max(0, var1_1 - var2_2);
                this.mListNavLayout.measure(View.MeasureSpec.makeMeasureSpec(var4_4, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(var11_11, 1073741824));
                var2_2 = this.mListNavLayout.getMeasuredWidth();
                var1_1 = Math.max(0, var4_4 - var2_2);
                var2_2 = Math.max(0, var15_15 - var2_2);
            } else lbl-1000: // 3 sources:
            {
                var2_2 = var1_1;
                var1_1 = var4_4;
            }
        }
        var16_16 = null;
        if (this.mExpandedActionView != null) {
            var6_6 = this.mExpandedActionView;
        } else {
            var6_6 = var16_16;
            if ((this.mDisplayOptions & 16) != 0) {
                var6_6 = var16_16;
                if (this.mCustomNavView != null) {
                    var6_6 = this.mCustomNavView;
                }
            }
        }
        if (var6_6 != null) {
            var17_17 = this.generateLayoutParams(var6_6.getLayoutParams());
            var16_16 = var17_17 instanceof ActionBar.LayoutParams != false ? (ActionBar.LayoutParams)var17_17 : null;
            var14_14 = 0;
            if (var16_16 != null) {
                var15_15 = var16_16.leftMargin;
                var14_14 = var16_16.rightMargin;
                var4_4 = var16_16.topMargin;
                var15_15 = var14_14 + var15_15;
                var14_14 = var4_4 + var16_16.bottomMargin;
            } else {
                var15_15 = 0;
            }
            var4_4 = this.mContentHeight <= 0 ? Integer.MIN_VALUE : (var17_17.height != -2 ? 1073741824 : Integer.MIN_VALUE);
            if (var17_17.height >= 0) {
                var11_11 = Math.min(var17_17.height, var11_11);
            }
            var13_13 = Math.max(0, var11_11 - var14_14);
            var14_14 = var17_17.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            var11_11 = var17_17.width >= 0 ? Math.min(var17_17.width, var1_1) : var1_1;
            var12_12 = Math.max(0, var11_11 - var15_15);
            var11_11 = var16_16 != null ? var16_16.gravity : 8388627;
            if ((var11_11 & 7) == 1) {
                var11_11 = var12_12;
                if (var17_17.width == -1) {
                    var11_11 = Math.min(var2_2, var5_5) * 2;
                }
            } else {
                var11_11 = var12_12;
            }
            var6_6.measure(View.MeasureSpec.makeMeasureSpec(var11_11, var14_14), View.MeasureSpec.makeMeasureSpec(var13_13, var4_4));
            var1_1 -= var15_15 + var6_6.getMeasuredWidth();
        }
        this.measureChildView(this.mUpGoerFive, var1_1 + var8_8, View.MeasureSpec.makeMeasureSpec(this.mContentHeight, 1073741824), 0);
        var6_6 = this.mTitleLayout;
        if (var6_6 != null) {
            Math.max(0, var2_2 - var6_6.getMeasuredWidth());
        }
        if (this.mContentHeight <= 0) {
            var4_4 = 0;
            for (var1_1 = 0; var1_1 < var3_3; ++var1_1) {
                var5_5 = this.getChildAt(var1_1).getMeasuredHeight() + var10_10;
                var2_2 = var4_4;
                if (var5_5 > var4_4) {
                    var2_2 = var5_5;
                }
                var4_4 = var2_2;
            }
            this.setMeasuredDimension(var9_9, var4_4);
        } else {
            this.setMeasuredDimension(var9_9, var7_7);
        }
        var6_6 = this.mContextView;
        if (var6_6 != null) {
            var6_6.setContentHeight(this.getMeasuredHeight());
        }
        if ((var6_6 = this.mProgressView) == null) return;
        if (var6_6.getVisibility() == 8) return;
        this.mProgressView.measure(View.MeasureSpec.makeMeasureSpec(var9_9 - this.mProgressBarPadding * 2, 1073741824), View.MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), Integer.MIN_VALUE));
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        Object object;
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        if (((SavedState)parcelable).expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && (object = this.mOptionsMenu) != null && (object = ((MenuBuilder)object).findItem(((SavedState)parcelable).expandedMenuItemId)) != null) {
            object.expandActionView();
        }
        if (((SavedState)parcelable).isOverflowOpen) {
            this.postShowOverflowMenu();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = this.isOverflowMenuShowing();
        return savedState;
    }

    @Override
    public void setCollapsible(boolean bl) {
        this.mIsCollapsible = bl;
    }

    public void setContextView(ActionBarContextView actionBarContextView) {
        this.mContextView = actionBarContextView;
    }

    @Override
    public void setCustomView(View view) {
        boolean bl = (this.mDisplayOptions & 16) != 0;
        View view2 = this.mCustomNavView;
        if (view2 != null && bl) {
            this.removeView(view2);
        }
        if ((view = (this.mCustomNavView = view)) != null && bl) {
            this.addView(view);
        }
    }

    @Override
    public void setDefaultNavigationContentDescription(int n) {
        if (this.mDefaultUpDescription == n) {
            return;
        }
        this.mDefaultUpDescription = n;
        this.updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override
    public void setDefaultNavigationIcon(Drawable drawable2) {
        this.mHomeLayout.setDefaultUpIndicator(drawable2);
    }

    @Override
    public void setDisplayOptions(int n) {
        int n2 = this.mDisplayOptions;
        int n3 = -1;
        if (n2 != -1) {
            n3 = n ^ n2;
        }
        this.mDisplayOptions = n;
        if ((n3 & 63) != 0) {
            boolean bl;
            Object object;
            if ((n3 & 4) != 0) {
                bl = (n & 4) != 0;
                this.mHomeLayout.setShowUp(bl);
                if (bl) {
                    this.setHomeButtonEnabled(true);
                }
            }
            if ((n3 & 1) != 0) {
                n2 = this.mLogo != null && (n & 1) != 0 ? 1 : 0;
                HomeView homeView = this.mHomeLayout;
                object = n2 != 0 ? this.mLogo : this.mIcon;
                homeView.setIcon((Drawable)object);
            }
            if ((n3 & 8) != 0) {
                if ((n & 8) != 0) {
                    this.initTitle();
                } else {
                    this.mUpGoerFive.removeView(this.mTitleLayout);
                }
            }
            bl = (n & 2) != 0;
            n2 = (this.mDisplayOptions & 4) != 0 ? 1 : 0;
            n2 = !bl && n2 != 0 ? 1 : 0;
            this.mHomeLayout.setShowIcon(bl);
            n2 = (bl || n2 != 0) && this.mExpandedActionView == null ? 0 : 8;
            this.mHomeLayout.setVisibility(n2);
            if ((n3 & 16) != 0 && (object = this.mCustomNavView) != null) {
                if ((n & 16) != 0) {
                    this.addView((View)object);
                } else {
                    this.removeView((View)object);
                }
            }
            if (this.mTitleLayout != null && (n3 & 32) != 0) {
                if ((n & 32) != 0) {
                    this.mTitleView.setSingleLine(false);
                    this.mTitleView.setMaxLines(2);
                } else {
                    this.mTitleView.setMaxLines(1);
                    this.mTitleView.setSingleLine(true);
                }
            }
            this.requestLayout();
        } else {
            this.invalidate();
        }
        this.updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override
    public void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mSpinnerAdapter = spinnerAdapter;
        this.mNavItemSelectedListener = onItemSelectedListener;
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setAdapter(spinnerAdapter);
            this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    @Override
    public void setDropdownSelectedPosition(int n) {
        this.mSpinner.setSelection(n);
    }

    @Override
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        Object object = this.mTabScrollView;
        if (object != null) {
            this.removeView((View)object);
        }
        this.mTabScrollView = scrollingTabContainerView;
        boolean bl = scrollingTabContainerView != null;
        this.mIncludeTabs = bl;
        if (this.mIncludeTabs && this.mNavigationMode == 2) {
            this.addView(this.mTabScrollView);
            object = this.mTabScrollView.getLayoutParams();
            ((ViewGroup.LayoutParams)object).width = -2;
            ((ViewGroup.LayoutParams)object).height = -1;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override
    public void setHomeButtonEnabled(boolean bl) {
        this.setHomeButtonEnabled(bl, true);
    }

    @Override
    public void setIcon(int n) {
        Drawable drawable2 = n != 0 ? this.mContext.getDrawable(n) : null;
        this.setIcon(drawable2);
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        if (drawable2 != null && ((this.mDisplayOptions & 1) == 0 || this.mLogo == null)) {
            this.mHomeLayout.setIcon(drawable2);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(this.getResources()));
        }
    }

    @Override
    public void setLogo(int n) {
        Drawable drawable2 = n != 0 ? this.mContext.getDrawable(n) : null;
        this.setLogo(drawable2);
    }

    @Override
    public void setLogo(Drawable drawable2) {
        this.mLogo = drawable2;
        if (drawable2 != null && (this.mDisplayOptions & 1) != 0) {
            this.mHomeLayout.setIcon(drawable2);
        }
    }

    @Override
    public void setMenu(Menu object, MenuPresenter.Callback object2) {
        Object object3 = this.mOptionsMenu;
        if (object == object3) {
            return;
        }
        if (object3 != null) {
            ((MenuBuilder)object3).removeMenuPresenter(this.mActionMenuPresenter);
            this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        object = (MenuBuilder)object;
        this.mOptionsMenu = object;
        if (this.mMenuView != null && (object3 = (ViewGroup)this.mMenuView.getParent()) != null) {
            ((ViewGroup)object3).removeView(this.mMenuView);
        }
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
            this.mActionMenuPresenter.setCallback((MenuPresenter.Callback)object2);
            this.mActionMenuPresenter.setId(16908690);
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        object2 = new ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(this.getResources().getBoolean(17891335));
            this.configPresenters((MenuBuilder)object);
            object = (ActionMenuView)this.mActionMenuPresenter.getMenuView(this);
            object3 = (ViewGroup)((View)object).getParent();
            if (object3 != null && object3 != this) {
                ((ViewGroup)object3).removeView((View)object);
            }
            this.addView((View)object, (ViewGroup.LayoutParams)object2);
        } else {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
            this.mActionMenuPresenter.setWidthLimit(this.getContext().getResources().getDisplayMetrics().widthPixels, true);
            this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
            ((ViewGroup.LayoutParams)object2).width = -1;
            ((ViewGroup.LayoutParams)object2).height = -2;
            this.configPresenters((MenuBuilder)object);
            object = (ActionMenuView)this.mActionMenuPresenter.getMenuView(this);
            if (this.mSplitView != null) {
                object3 = (ViewGroup)((View)object).getParent();
                if (object3 != null && object3 != this.mSplitView) {
                    ((ViewGroup)object3).removeView((View)object);
                }
                ((View)object).setVisibility(this.getAnimatedVisibility());
                this.mSplitView.addView((View)object, (ViewGroup.LayoutParams)object2);
            } else {
                ((View)object).setLayoutParams((ViewGroup.LayoutParams)object2);
            }
        }
        this.mMenuView = object;
    }

    @Override
    public void setMenuCallbacks(MenuPresenter.Callback object, MenuBuilder.Callback callback) {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.setCallback((MenuPresenter.Callback)object);
        }
        if ((object = this.mOptionsMenu) != null) {
            ((MenuBuilder)object).setCallback(callback);
        }
    }

    @Override
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override
    public void setNavigationContentDescription(int n) {
        this.mHomeDescriptionRes = n;
        CharSequence charSequence = n != 0 ? this.getResources().getText(n) : null;
        this.mHomeDescription = charSequence;
        this.updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override
    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        this.updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override
    public void setNavigationIcon(int n) {
        this.mHomeLayout.setUpIndicator(n);
    }

    @Override
    public void setNavigationIcon(Drawable drawable2) {
        this.mHomeLayout.setUpIndicator(drawable2);
    }

    @Override
    public void setNavigationMode(int n) {
        int n2 = this.mNavigationMode;
        if (n != n2) {
            Object object;
            if (n2 != 1) {
                if (n2 == 2 && (object = this.mTabScrollView) != null && this.mIncludeTabs) {
                    this.removeView((View)object);
                }
            } else {
                object = this.mListNavLayout;
                if (object != null) {
                    this.removeView((View)object);
                }
            }
            if (n != 1) {
                if (n == 2 && (object = this.mTabScrollView) != null && this.mIncludeTabs) {
                    this.addView((View)object);
                }
            } else {
                Adapter adapter;
                if (this.mSpinner == null) {
                    this.mSpinner = new Spinner(this.mContext, null, 16843479);
                    this.mSpinner.setId(16908684);
                    this.mListNavLayout = new LinearLayout(this.mContext, null, 16843508);
                    object = new LinearLayout.LayoutParams(-2, -1);
                    ((LinearLayout.LayoutParams)object).gravity = 17;
                    this.mListNavLayout.addView((View)this.mSpinner, (ViewGroup.LayoutParams)object);
                }
                if ((adapter = this.mSpinner.getAdapter()) != (object = this.mSpinnerAdapter)) {
                    this.mSpinner.setAdapter((SpinnerAdapter)object);
                }
                this.mSpinner.setOnItemSelectedListener(this.mNavItemSelectedListener);
                this.addView(this.mListNavLayout);
            }
            this.mNavigationMode = n;
            this.requestLayout();
        }
    }

    @Override
    public void setSplitToolbar(boolean bl) {
        if (this.mSplitActionBar != bl) {
            ViewGroup viewGroup;
            if (this.mMenuView != null) {
                viewGroup = (ViewGroup)this.mMenuView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.mMenuView);
                }
                if (bl) {
                    if (this.mSplitView != null) {
                        this.mSplitView.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    this.addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            if (this.mSplitView != null) {
                viewGroup = this.mSplitView;
                int n = bl ? 0 : 8;
                viewGroup.setVisibility(n);
            }
            if (this.mActionMenuPresenter != null) {
                if (!bl) {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(this.getResources().getBoolean(17891335));
                } else {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(this.getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                }
            }
            super.setSplitToolbar(bl);
        }
    }

    @Override
    public void setSubtitle(CharSequence object) {
        this.mSubtitle = object;
        TextView textView = this.mSubtitleView;
        if (textView != null) {
            textView.setText((CharSequence)object);
            textView = this.mSubtitleView;
            int n = 0;
            int n2 = object != null ? 0 : 8;
            textView.setVisibility(n2);
            n2 = this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle)) ? 1 : 0;
            object = this.mTitleLayout;
            n2 = n2 != 0 ? n : 8;
            ((View)object).setVisibility(n2);
        }
        this.updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mUserTitle = true;
        this.setTitleImpl(charSequence);
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        if (!this.mUserTitle) {
            this.setTitleImpl(charSequence);
        }
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private class ExpandedActionViewMenuPresenter
    implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)((Object)ActionBarView.this.mExpandedActionView)).onActionViewCollapsed();
            }
            object = ActionBarView.this;
            ((ViewGroup)object).removeView(((ActionBarView)object).mExpandedActionView);
            ActionBarView.this.mUpGoerFive.removeView(ActionBarView.this.mExpandedHomeLayout);
            object = ActionBarView.this;
            ((ActionBarView)object).mExpandedActionView = null;
            if ((((ActionBarView)object).mDisplayOptions & 2) != 0) {
                ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (ActionBarView.this.mTitleLayout == null) {
                    ActionBarView.this.initTitle();
                } else {
                    ActionBarView.this.mTitleLayout.setVisibility(0);
                }
            }
            if (ActionBarView.this.mTabScrollView != null) {
                ActionBarView.this.mTabScrollView.setVisibility(0);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            object = ActionBarView.this;
            ((ActionBarView)object).setHomeButtonEnabled(((ActionBarView)object).mWasHomeEnabled);
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            ActionBarView.this.mExpandedActionView = menuItemImpl.getActionView();
            ActionBarView.this.mExpandedHomeLayout.setIcon(ActionBarView.this.mIcon.getConstantState().newDrawable(ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = menuItemImpl;
            object = ActionBarView.this.mExpandedActionView.getParent();
            ActionBarView actionBarView = ActionBarView.this;
            if (object != actionBarView) {
                actionBarView.addView(actionBarView.mExpandedActionView);
            }
            if (ActionBarView.this.mExpandedHomeLayout.getParent() != ActionBarView.this.mUpGoerFive) {
                ActionBarView.this.mUpGoerFive.addView(ActionBarView.this.mExpandedHomeLayout);
            }
            ActionBarView.this.mHomeLayout.setVisibility(8);
            if (ActionBarView.this.mTitleLayout != null) {
                ActionBarView.this.mTitleLayout.setVisibility(8);
            }
            if (ActionBarView.this.mTabScrollView != null) {
                ActionBarView.this.mTabScrollView.setVisibility(8);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            ActionBarView.this.setHomeButtonEnabled(false, false);
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)((Object)ActionBarView.this.mExpandedActionView)).onActionViewExpanded();
            }
            return true;
        }

        @Override
        public boolean flagActionItems() {
            return false;
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void initForMenu(Context object, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            object = this.mMenu;
            if (object != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                ((MenuBuilder)object).collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override
        public void setCallback(MenuPresenter.Callback callback) {
        }

        @Override
        public void updateMenuView(boolean bl) {
            if (this.mCurrentExpandedItem != null) {
                boolean bl2 = false;
                MenuBuilder menuBuilder = this.mMenu;
                boolean bl3 = bl2;
                if (menuBuilder != null) {
                    int n = menuBuilder.size();
                    int n2 = 0;
                    do {
                        bl3 = bl2;
                        if (n2 >= n) break;
                        if (this.mMenu.getItem(n2) == this.mCurrentExpandedItem) {
                            bl3 = true;
                            break;
                        }
                        ++n2;
                    } while (true);
                }
                if (!bl3) {
                    this.collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }
    }

    private static class HomeView
    extends FrameLayout {
        private static final long DEFAULT_TRANSITION_DURATION = 150L;
        private Drawable mDefaultUpIndicator;
        private ImageView mIconView;
        private int mStartOffset;
        private Drawable mUpIndicator;
        private int mUpIndicatorRes;
        private ImageView mUpView;
        private int mUpWidth;

        public HomeView(Context context) {
            this(context, null);
        }

        public HomeView(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = this.getLayoutTransition();
            if (object != null) {
                ((LayoutTransition)object).setDuration(150L);
            }
        }

        private void updateUpIndicator() {
            Drawable drawable2 = this.mUpIndicator;
            if (drawable2 != null) {
                this.mUpView.setImageDrawable(drawable2);
            } else if (this.mUpIndicatorRes != 0) {
                this.mUpView.setImageDrawable(this.getContext().getDrawable(this.mUpIndicatorRes));
            } else {
                this.mUpView.setImageDrawable(this.mDefaultUpIndicator);
            }
        }

        @Override
        public boolean dispatchHoverEvent(MotionEvent motionEvent) {
            return this.onHoverEvent(motionEvent);
        }

        @Override
        public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
            this.onPopulateAccessibilityEvent(accessibilityEvent);
            return true;
        }

        public int getStartOffset() {
            int n = this.mUpView.getVisibility() == 8 ? this.mStartOffset : 0;
            return n;
        }

        public int getUpWidth() {
            return this.mUpWidth;
        }

        @Override
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            if (this.mUpIndicatorRes != 0) {
                this.updateUpIndicator();
            }
        }

        @Override
        protected void onFinishInflate() {
            this.mUpView = (ImageView)this.findViewById(16909529);
            this.mIconView = (ImageView)this.findViewById(16908332);
            this.mDefaultUpIndicator = this.mUpView.getDrawable();
        }

        @Override
        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
            int n5;
            FrameLayout.LayoutParams layoutParams;
            int n6;
            int n7 = (n4 - n2) / 2;
            bl = this.isLayoutRtl();
            int n8 = this.getWidth();
            n4 = 0;
            if (this.mUpView.getVisibility() != 8) {
                layoutParams = (FrameLayout.LayoutParams)this.mUpView.getLayoutParams();
                int n9 = this.mUpView.getMeasuredHeight();
                n4 = this.mUpView.getMeasuredWidth();
                n6 = layoutParams.leftMargin + n4 + layoutParams.rightMargin;
                int n10 = n7 - n9 / 2;
                if (bl) {
                    n2 = n8;
                    int n11 = n2 - n4;
                    n5 = n3 - n6;
                    n4 = n2;
                    n3 = n11;
                    n2 = n;
                    n = n5;
                } else {
                    n5 = 0;
                    n2 = n + n6;
                    n = n3;
                    n3 = n5;
                }
                this.mUpView.layout(n3, n10, n4, n10 + n9);
                n3 = n6;
            } else {
                n2 = n;
                n = n3;
                n3 = n4;
            }
            layoutParams = (FrameLayout.LayoutParams)this.mIconView.getLayoutParams();
            n4 = this.mIconView.getMeasuredHeight();
            n5 = this.mIconView.getMeasuredWidth();
            n = (n - n2) / 2;
            n6 = Math.max(layoutParams.topMargin, n7 - n4 / 2);
            n = Math.max(layoutParams.getMarginStart(), n - n5 / 2);
            if (bl) {
                n = n2 = n8 - n3 - n;
                n2 -= n5;
            } else {
                n2 = n3 + n;
                n = n2 + n5;
            }
            this.mIconView.layout(n2, n6, n, n6 + n4);
        }

        @Override
        protected void onMeasure(int n, int n2) {
            int n3;
            int n4;
            this.measureChildWithMargins(this.mUpView, n, 0, n2, 0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mUpView.getLayoutParams();
            int n5 = layoutParams.leftMargin + layoutParams.rightMargin;
            this.mUpWidth = this.mUpView.getMeasuredWidth();
            this.mStartOffset = this.mUpWidth + n5;
            int n6 = this.mUpView.getVisibility() == 8 ? 0 : this.mStartOffset;
            int n7 = layoutParams.topMargin + this.mUpView.getMeasuredHeight() + layoutParams.bottomMargin;
            if (this.mIconView.getVisibility() != 8) {
                this.measureChildWithMargins(this.mIconView, n, n6, n2, 0);
                layoutParams = (FrameLayout.LayoutParams)this.mIconView.getLayoutParams();
                n4 = n6 + (layoutParams.leftMargin + this.mIconView.getMeasuredWidth() + layoutParams.rightMargin);
                n3 = Math.max(n7, layoutParams.topMargin + this.mIconView.getMeasuredHeight() + layoutParams.bottomMargin);
            } else {
                n4 = n6;
                n3 = n7;
                if (n5 < 0) {
                    n4 = n6 - n5;
                    n3 = n7;
                }
            }
            n5 = View.MeasureSpec.getMode(n);
            n7 = View.MeasureSpec.getMode(n2);
            n6 = View.MeasureSpec.getSize(n);
            n = View.MeasureSpec.getSize(n2);
            if (n5 != Integer.MIN_VALUE) {
                if (n5 == 1073741824) {
                    n4 = n6;
                }
            } else {
                n4 = Math.min(n4, n6);
            }
            if (n7 != Integer.MIN_VALUE) {
                if (n7 == 1073741824) {
                    n3 = n;
                }
            } else {
                n3 = Math.min(n3, n);
            }
            this.setMeasuredDimension(n4, n3);
        }

        @Override
        public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEventInternal(accessibilityEvent);
            CharSequence charSequence = this.getContentDescription();
            if (!TextUtils.isEmpty(charSequence)) {
                accessibilityEvent.getText().add(charSequence);
            }
        }

        public void setDefaultUpIndicator(Drawable drawable2) {
            this.mDefaultUpIndicator = drawable2;
            this.updateUpIndicator();
        }

        public void setIcon(Drawable drawable2) {
            this.mIconView.setImageDrawable(drawable2);
        }

        public void setShowIcon(boolean bl) {
            ImageView imageView = this.mIconView;
            int n = bl ? 0 : 8;
            imageView.setVisibility(n);
        }

        public void setShowUp(boolean bl) {
            ImageView imageView = this.mUpView;
            int n = bl ? 0 : 8;
            imageView.setVisibility(n);
        }

        public void setUpIndicator(int n) {
            this.mUpIndicatorRes = n;
            this.mUpIndicator = null;
            this.updateUpIndicator();
        }

        public void setUpIndicator(Drawable drawable2) {
            this.mUpIndicator = drawable2;
            this.mUpIndicatorRes = 0;
            this.updateUpIndicator();
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
        int expandedMenuItemId;
        boolean isOverflowOpen;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.isOverflowOpen = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt((int)this.isOverflowOpen);
        }

    }

}

