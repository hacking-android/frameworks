/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.CollapsibleActionView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RtlSpacingHelper;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import com.android.internal.widget.DecorToolbar;
import com.android.internal.widget.ToolbarWidgetWrapper;
import java.util.ArrayList;
import java.util.List;

public class Toolbar
extends ViewGroup {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private int mButtonGravity;
    private ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity = 8388627;
    private final ArrayList<View> mHiddenViews = new ArrayList();
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (Toolbar.this.mOnMenuItemClickListener != null) {
                return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
            }
            return false;
        }
    };
    private int mNavButtonStyle;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private ImageButton mNavButtonView;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable = new Runnable(){

        @Override
        public void run() {
            Toolbar.this.showOverflowMenu();
        }
    };
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins = new int[2];
    private final ArrayList<View> mTempViews = new ArrayList();
    @UnsupportedAppUsage
    private int mTitleMarginBottom;
    @UnsupportedAppUsage
    private int mTitleMarginEnd;
    @UnsupportedAppUsage
    private int mTitleMarginStart;
    @UnsupportedAppUsage
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    @UnsupportedAppUsage
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843946);
    }

    public Toolbar(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Toolbar(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Toolbar, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.Toolbar, attributeSet, typedArray, n, n2);
        this.mTitleTextAppearance = typedArray.getResourceId(4, 0);
        this.mSubtitleTextAppearance = typedArray.getResourceId(5, 0);
        this.mNavButtonStyle = typedArray.getResourceId(27, 0);
        this.mGravity = typedArray.getInteger(0, this.mGravity);
        this.mButtonGravity = typedArray.getInteger(23, 48);
        this.mTitleMarginBottom = n = typedArray.getDimensionPixelOffset(17, 0);
        this.mTitleMarginTop = n;
        this.mTitleMarginEnd = n;
        this.mTitleMarginStart = n;
        n = typedArray.getDimensionPixelOffset(18, -1);
        if (n >= 0) {
            this.mTitleMarginStart = n;
        }
        if ((n = typedArray.getDimensionPixelOffset(19, -1)) >= 0) {
            this.mTitleMarginEnd = n;
        }
        if ((n = typedArray.getDimensionPixelOffset(20, -1)) >= 0) {
            this.mTitleMarginTop = n;
        }
        if ((n = typedArray.getDimensionPixelOffset(21, -1)) >= 0) {
            this.mTitleMarginBottom = n;
        }
        this.mMaxButtonHeight = typedArray.getDimensionPixelSize(22, -1);
        n2 = typedArray.getDimensionPixelOffset(6, Integer.MIN_VALUE);
        int n3 = typedArray.getDimensionPixelOffset(7, Integer.MIN_VALUE);
        n = typedArray.getDimensionPixelSize(8, 0);
        int n4 = typedArray.getDimensionPixelSize(9, 0);
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n, n4);
        if (n2 != Integer.MIN_VALUE || n3 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(n2, n3);
        }
        this.mContentInsetStartWithNavigation = typedArray.getDimensionPixelOffset(25, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = typedArray.getDimensionPixelOffset(26, Integer.MIN_VALUE);
        this.mCollapseIcon = typedArray.getDrawable(24);
        this.mCollapseDescription = typedArray.getText(13);
        object = typedArray.getText(1);
        if (!TextUtils.isEmpty((CharSequence)object)) {
            this.setTitle((CharSequence)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = typedArray.getText(3)))) {
            this.setSubtitle((CharSequence)object);
        }
        this.mPopupContext = this.mContext;
        this.setPopupTheme(typedArray.getResourceId(10, 0));
        object = typedArray.getDrawable(11);
        if (object != null) {
            this.setNavigationIcon((Drawable)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = typedArray.getText(12)))) {
            this.setNavigationContentDescription((CharSequence)object);
        }
        if ((object = typedArray.getDrawable(2)) != null) {
            this.setLogo((Drawable)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = typedArray.getText(16)))) {
            this.setLogoDescription((CharSequence)object);
        }
        if (typedArray.hasValue(14)) {
            this.setTitleTextColor(typedArray.getColor(14, -1));
        }
        if (typedArray.hasValue(15)) {
            this.setSubtitleTextColor(typedArray.getColor(15, -1));
        }
        typedArray.recycle();
    }

    private void addCustomViewsWithGravity(List<View> list, int n) {
        int n2 = this.getLayoutDirection();
        boolean bl = true;
        if (n2 != 1) {
            bl = false;
        }
        int n3 = this.getChildCount();
        n2 = Gravity.getAbsoluteGravity(n, this.getLayoutDirection());
        list.clear();
        if (bl) {
            for (n = n3 - 1; n >= 0; --n) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n2) continue;
                list.add(view);
            }
        } else {
            for (n = 0; n < n3; ++n) {
                View view = this.getChildAt(n);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.mViewType != 0 || !this.shouldLayout(view) || this.getChildHorizontalGravity(layoutParams.gravity) != n2) continue;
                list.add(view);
            }
        }
    }

    private void addSystemView(View view, boolean bl) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams = layoutParams == null ? this.generateDefaultLayoutParams() : (!this.checkLayoutParams(layoutParams) ? this.generateLayoutParams(layoutParams) : (LayoutParams)layoutParams);
        ((LayoutParams)layoutParams).mViewType = 1;
        if (bl && this.mExpandedActionView != null) {
            view.setLayoutParams(layoutParams);
            this.mHiddenViews.add(view);
        } else {
            this.addView(view, layoutParams);
        }
    }

    private void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new ImageButton(this.getContext(), null, 0, this.mNavButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            ViewGroup.LayoutParams layoutParams = this.generateDefaultLayoutParams();
            ((LayoutParams)layoutParams).gravity = 8388611 | this.mButtonGravity & 112;
            ((LayoutParams)layoutParams).mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(layoutParams);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new ImageView(this.getContext());
        }
    }

    private void ensureMenu() {
        this.ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(this.getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            ViewGroup.LayoutParams layoutParams = this.generateDefaultLayoutParams();
            ((LayoutParams)layoutParams).gravity = 8388613 | this.mButtonGravity & 112;
            this.mMenuView.setLayoutParams(layoutParams);
            this.addSystemView(this.mMenuView, false);
        }
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new ImageButton(this.getContext(), null, 0, this.mNavButtonStyle);
            ViewGroup.LayoutParams layoutParams = this.generateDefaultLayoutParams();
            ((LayoutParams)layoutParams).gravity = 8388611 | this.mButtonGravity & 112;
            this.mNavButtonView.setLayoutParams(layoutParams);
        }
    }

    private int getChildHorizontalGravity(int n) {
        int n2 = this.getLayoutDirection();
        int n3 = Gravity.getAbsoluteGravity(n, n2) & 7;
        if (n3 != 1) {
            n = 3;
            if (n3 != 3 && n3 != 5) {
                if (n2 == 1) {
                    n = 5;
                }
                return n;
            }
        }
        return n3;
    }

    private int getChildTop(View view, int n) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n2 = view.getMeasuredHeight();
        n = n > 0 ? (n2 - n) / 2 : 0;
        int n3 = this.getChildVerticalGravity(layoutParams.gravity);
        if (n3 != 48) {
            if (n3 != 80) {
                int n4 = this.getPaddingTop();
                int n5 = this.getPaddingBottom();
                n = this.getHeight();
                n3 = (n - n4 - n5 - n2) / 2;
                if (n3 < layoutParams.topMargin) {
                    n = layoutParams.topMargin;
                } else {
                    n2 = n - n5 - n2 - n3 - n4;
                    n = n3;
                    if (n2 < layoutParams.bottomMargin) {
                        n = Math.max(0, n3 - (layoutParams.bottomMargin - n2));
                    }
                }
                return n4 + n;
            }
            return this.getHeight() - this.getPaddingBottom() - n2 - layoutParams.bottomMargin - n;
        }
        return this.getPaddingTop() - n;
    }

    private int getChildVerticalGravity(int n) {
        if ((n &= 112) != 16 && n != 48 && n != 80) {
            return this.mGravity & 112;
        }
        return n;
    }

    private int getHorizontalMargins(View object) {
        object = (ViewGroup.MarginLayoutParams)((View)object).getLayoutParams();
        return ((ViewGroup.MarginLayoutParams)object).getMarginStart() + ((ViewGroup.MarginLayoutParams)object).getMarginEnd();
    }

    private MenuInflater getMenuInflater() {
        return new MenuInflater(this.getContext());
    }

    private int getVerticalMargins(View object) {
        object = (ViewGroup.MarginLayoutParams)((View)object).getLayoutParams();
        return ((ViewGroup.MarginLayoutParams)object).topMargin + ((ViewGroup.MarginLayoutParams)object).bottomMargin;
    }

    private int getViewListMeasuredWidth(List<View> list, int[] object) {
        int n = object[0];
        int n2 = object[1];
        int n3 = 0;
        int n4 = list.size();
        for (int i = 0; i < n4; ++i) {
            object = list.get(i);
            LayoutParams layoutParams = (LayoutParams)((View)object).getLayoutParams();
            n = layoutParams.leftMargin - n;
            n2 = layoutParams.rightMargin - n2;
            int n5 = Math.max(0, n);
            int n6 = Math.max(0, n2);
            n = Math.max(0, -n);
            n2 = Math.max(0, -n2);
            n3 += ((View)object).getMeasuredWidth() + n5 + n6;
        }
        return n3;
    }

    private boolean isChildOrHidden(View view) {
        boolean bl = view.getParent() == this || this.mHiddenViews.contains(view);
        return bl;
    }

    private static boolean isCustomView(View view) {
        boolean bl = ((LayoutParams)view.getLayoutParams()).mViewType == 0;
        return bl;
    }

    private int layoutChildLeft(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.leftMargin - arrn[0];
        n += Math.max(0, n3);
        arrn[0] = Math.max(0, -n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n, n2, n + n3, view.getMeasuredHeight() + n2);
        return n + (layoutParams.rightMargin + n3);
    }

    private int layoutChildRight(View view, int n, int[] arrn, int n2) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n3 = layoutParams.rightMargin - arrn[1];
        n -= Math.max(0, n3);
        arrn[1] = Math.max(0, -n3);
        n2 = this.getChildTop(view, n2);
        n3 = view.getMeasuredWidth();
        view.layout(n - n3, n2, n, view.getMeasuredHeight() + n2);
        return n - (layoutParams.leftMargin + n3);
    }

    private int measureChildCollapseMargins(View view, int n, int n2, int n3, int n4, int[] arrn) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n5 = marginLayoutParams.leftMargin - arrn[0];
        int n6 = marginLayoutParams.rightMargin - arrn[1];
        int n7 = Math.max(0, n5) + Math.max(0, n6);
        arrn[0] = Math.max(0, -n5);
        arrn[1] = Math.max(0, -n6);
        view.measure(Toolbar.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight + n7 + n2, marginLayoutParams.width), Toolbar.getChildMeasureSpec(n3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4, marginLayoutParams.height));
        return view.getMeasuredWidth() + n7;
    }

    private void measureChildConstrained(View view, int n, int n2, int n3, int n4, int n5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n6 = Toolbar.getChildMeasureSpec(n, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2, marginLayoutParams.width);
        n2 = Toolbar.getChildMeasureSpec(n3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n4, marginLayoutParams.height);
        n3 = View.MeasureSpec.getMode(n2);
        n = n2;
        if (n3 != 1073741824) {
            n = n2;
            if (n5 >= 0) {
                if (n3 != 0) {
                    n5 = Math.min(View.MeasureSpec.getSize(n2), n5);
                }
                n = View.MeasureSpec.makeMeasureSpec(n5, 1073741824);
            }
        }
        view.measure(n6, n);
    }

    private void postShowOverflowMenu() {
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
        this.post(this.mShowOverflowMenuRunnable);
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (!this.shouldLayout(view) || view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0) continue;
            return false;
        }
        return true;
    }

    private boolean shouldLayout(View view) {
        boolean bl = view != null && view.getParent() == this && view.getVisibility() != 8;
        return bl;
    }

    void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; --i) {
            this.addView(this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }

    public boolean canShowOverflowMenu() {
        ActionMenuView actionMenuView;
        boolean bl = this.getVisibility() == 0 && (actionMenuView = this.mMenuView) != null && actionMenuView.isOverflowReserved();
        return bl;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        boolean bl = super.checkLayoutParams(layoutParams) && layoutParams instanceof LayoutParams;
        return bl;
    }

    public void collapseActionView() {
        Object object = this.mExpandedMenuPresenter;
        object = object == null ? null : ((ExpandedActionViewMenuPresenter)object).mCurrentExpandedItem;
        if (object != null) {
            ((MenuItemImpl)object).collapseActionView();
        }
    }

    public void dismissPopupMenus() {
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            actionMenuView.dismissPopupMenus();
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public CharSequence getCollapseContentDescription() {
        Object object = this.mCollapseButtonView;
        object = object != null ? ((View)object).getContentDescription() : null;
        return object;
    }

    public Drawable getCollapseIcon() {
        Object object = this.mCollapseButtonView;
        object = object != null ? ((ImageView)object).getDrawable() : null;
        return object;
    }

    public int getContentInsetEnd() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        int n = rtlSpacingHelper != null ? rtlSpacingHelper.getEnd() : 0;
        return n;
    }

    public int getContentInsetEndWithActions() {
        int n = this.mContentInsetEndWithActions;
        if (n == Integer.MIN_VALUE) {
            n = this.getContentInsetEnd();
        }
        return n;
    }

    public int getContentInsetLeft() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        int n = rtlSpacingHelper != null ? rtlSpacingHelper.getLeft() : 0;
        return n;
    }

    public int getContentInsetRight() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        int n = rtlSpacingHelper != null ? rtlSpacingHelper.getRight() : 0;
        return n;
    }

    public int getContentInsetStart() {
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        int n = rtlSpacingHelper != null ? rtlSpacingHelper.getStart() : 0;
        return n;
    }

    public int getContentInsetStartWithNavigation() {
        int n = this.mContentInsetStartWithNavigation;
        if (n == Integer.MIN_VALUE) {
            n = this.getContentInsetStart();
        }
        return n;
    }

    public int getCurrentContentInsetEnd() {
        int n = 0;
        Object object = this.mMenuView;
        if (object != null) {
            n = (object = ((ActionMenuView)object).peekMenu()) != null && ((MenuBuilder)object).hasVisibleItems() ? 1 : 0;
        }
        n = n != 0 ? Math.max(this.getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0)) : this.getContentInsetEnd();
        return n;
    }

    public int getCurrentContentInsetLeft() {
        int n = this.isLayoutRtl() ? this.getCurrentContentInsetEnd() : this.getCurrentContentInsetStart();
        return n;
    }

    public int getCurrentContentInsetRight() {
        int n = this.isLayoutRtl() ? this.getCurrentContentInsetStart() : this.getCurrentContentInsetEnd();
        return n;
    }

    public int getCurrentContentInsetStart() {
        int n = this.getNavigationIcon() != null ? Math.max(this.getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0)) : this.getContentInsetStart();
        return n;
    }

    public Drawable getLogo() {
        Object object = this.mLogoView;
        object = object != null ? ((ImageView)object).getDrawable() : null;
        return object;
    }

    public CharSequence getLogoDescription() {
        Object object = this.mLogoView;
        object = object != null ? ((View)object).getContentDescription() : null;
        return object;
    }

    public Menu getMenu() {
        this.ensureMenu();
        return this.mMenuView.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        Object object = this.mNavButtonView;
        object = object != null ? ((View)object).getContentDescription() : null;
        return object;
    }

    public Drawable getNavigationIcon() {
        Object object = this.mNavButtonView;
        object = object != null ? ((ImageView)object).getDrawable() : null;
        return object;
    }

    public View getNavigationView() {
        return this.mNavButtonView;
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    public Drawable getOverflowIcon() {
        this.ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        boolean bl = expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null;
        return bl;
    }

    public boolean hideOverflowMenu() {
        ActionMenuView actionMenuView = this.mMenuView;
        boolean bl = actionMenuView != null && actionMenuView.hideOverflowMenu();
        return bl;
    }

    public void inflateMenu(int n) {
        this.getMenuInflater().inflate(n, this.getMenu());
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuView actionMenuView = this.mMenuView;
        boolean bl = actionMenuView != null && actionMenuView.isOverflowMenuShowPending();
        return bl;
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuView actionMenuView = this.mMenuView;
        boolean bl = actionMenuView != null && actionMenuView.isOverflowMenuShowing();
        return bl;
    }

    public boolean isTitleTruncated() {
        Object object = this.mTitleTextView;
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
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (ViewParent viewParent = this.getParent(); viewParent != null && viewParent instanceof ViewGroup; viewParent = viewParent.getParent()) {
            if (!((View)((Object)(viewParent = (ViewGroup)viewParent))).isKeyboardNavigationCluster()) continue;
            this.setKeyboardNavigationCluster(false);
            if (!((ViewGroup)viewParent).getTouchscreenBlocksFocus()) break;
            this.setTouchscreenBlocksFocus(false);
            break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Object object;
        int n5 = this.getLayoutDirection() == 1 ? 1 : 0;
        int n6 = this.getWidth();
        int n7 = this.getHeight();
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingRight();
        int n10 = this.getPaddingTop();
        int n11 = this.getPaddingBottom();
        int n12 = n8;
        int n13 = n6 - n9;
        int[] arrn = this.mTempMargins;
        arrn[1] = 0;
        arrn[0] = 0;
        n = this.getMinimumHeight();
        n3 = n >= 0 ? Math.min(n, n4 - n2) : 0;
        n2 = n12;
        n = n13;
        if (this.shouldLayout(this.mNavButtonView)) {
            if (n5 != 0) {
                n = this.layoutChildRight(this.mNavButtonView, n13, arrn, n3);
                n2 = n12;
            } else {
                n2 = this.layoutChildLeft(this.mNavButtonView, n12, arrn, n3);
                n = n13;
            }
        }
        n12 = n2;
        n4 = n;
        if (this.shouldLayout(this.mCollapseButtonView)) {
            if (n5 != 0) {
                n4 = this.layoutChildRight(this.mCollapseButtonView, n, arrn, n3);
                n12 = n2;
            } else {
                n12 = this.layoutChildLeft(this.mCollapseButtonView, n2, arrn, n3);
                n4 = n;
            }
        }
        n2 = n12;
        n = n4;
        if (this.shouldLayout(this.mMenuView)) {
            if (n5 != 0) {
                n2 = this.layoutChildLeft(this.mMenuView, n12, arrn, n3);
                n = n4;
            } else {
                n = this.layoutChildRight(this.mMenuView, n4, arrn, n3);
                n2 = n12;
            }
        }
        n12 = this.getCurrentContentInsetLeft();
        n4 = this.getCurrentContentInsetRight();
        arrn[0] = Math.max(0, n12 - n2);
        arrn[1] = Math.max(0, n4 - (n6 - n9 - n));
        n2 = Math.max(n2, n12);
        n4 = Math.min(n, n6 - n9 - n4);
        n = n2;
        n12 = n4;
        if (this.shouldLayout(this.mExpandedActionView)) {
            if (n5 != 0) {
                n12 = this.layoutChildRight(this.mExpandedActionView, n4, arrn, n3);
                n = n2;
            } else {
                n = this.layoutChildLeft(this.mExpandedActionView, n2, arrn, n3);
                n12 = n4;
            }
        }
        n2 = n;
        n4 = n12;
        if (this.shouldLayout(this.mLogoView)) {
            if (n5 != 0) {
                n4 = this.layoutChildRight(this.mLogoView, n12, arrn, n3);
                n2 = n;
            } else {
                n2 = this.layoutChildLeft(this.mLogoView, n, arrn, n3);
                n4 = n12;
            }
        }
        bl = this.shouldLayout(this.mTitleTextView);
        boolean bl2 = this.shouldLayout(this.mSubtitleTextView);
        n = 0;
        if (bl) {
            object = (LayoutParams)this.mTitleTextView.getLayoutParams();
            n = 0 + (((LayoutParams)object).topMargin + this.mTitleTextView.getMeasuredHeight() + ((LayoutParams)object).bottomMargin);
        }
        n13 = n;
        if (bl2) {
            object = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
            n13 = n + (((LayoutParams)object).topMargin + this.mSubtitleTextView.getMeasuredHeight() + ((LayoutParams)object).bottomMargin);
        }
        if (!bl && !bl2) {
            n = n2;
            n2 = n4;
        } else {
            int n14;
            object = bl ? this.mTitleTextView : this.mSubtitleTextView;
            Object object2 = bl2 ? this.mSubtitleTextView : this.mTitleTextView;
            object = (LayoutParams)((View)object).getLayoutParams();
            object2 = (LayoutParams)((View)object2).getLayoutParams();
            n12 = bl && this.mTitleTextView.getMeasuredWidth() > 0 || bl2 && this.mSubtitleTextView.getMeasuredWidth() > 0 ? 1 : 0;
            n = this.mGravity & 112;
            if (n != 48) {
                if (n != 80) {
                    n14 = (n7 - n10 - n11 - n13) / 2;
                    if (n14 < ((LayoutParams)object).topMargin + this.mTitleMarginTop) {
                        n = ((LayoutParams)object).topMargin + this.mTitleMarginTop;
                    } else {
                        n13 = n7 - n11 - n13 - n14 - n10;
                        n = n14;
                        if (n13 < ((LayoutParams)object).bottomMargin + this.mTitleMarginBottom) {
                            n = Math.max(0, n14 - (((LayoutParams)object2).bottomMargin + this.mTitleMarginBottom - n13));
                        }
                    }
                    n = n10 + n;
                } else {
                    n = n7 - n11 - ((LayoutParams)object2).bottomMargin - this.mTitleMarginBottom - n13;
                }
            } else {
                n = this.getPaddingTop() + ((LayoutParams)object).topMargin + this.mTitleMarginTop;
            }
            n13 = n2;
            if (n5 != 0) {
                n2 = n12 != 0 ? this.mTitleMarginStart : 0;
                n5 = n2 - arrn[1];
                n2 = n4 - Math.max(0, n5);
                arrn[1] = Math.max(0, -n5);
                n5 = n2;
                n4 = n2;
                if (bl) {
                    object = (LayoutParams)this.mTitleTextView.getLayoutParams();
                    n10 = n5 - this.mTitleTextView.getMeasuredWidth();
                    n14 = this.mTitleTextView.getMeasuredHeight() + n;
                    this.mTitleTextView.layout(n10, n, n5, n14);
                    n = n10 - this.mTitleMarginEnd;
                    n14 += ((LayoutParams)object).bottomMargin;
                } else {
                    n14 = n;
                    n = n5;
                }
                n5 = n4;
                if (bl2) {
                    object = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    n5 = n14 + ((LayoutParams)object).topMargin;
                    n14 = this.mSubtitleTextView.getMeasuredWidth();
                    n10 = this.mSubtitleTextView.getMeasuredHeight() + n5;
                    this.mSubtitleTextView.layout(n4 - n14, n5, n4, n10);
                    n5 = n4 - this.mTitleMarginEnd;
                    n4 = ((LayoutParams)object).bottomMargin;
                }
                if (n12 != 0) {
                    n2 = Math.min(n, n5);
                }
                n = n13;
            } else {
                n2 = n12 != 0 ? this.mTitleMarginStart : 0;
                n5 = n2 - arrn[0];
                n2 = n13 + Math.max(0, n5);
                arrn[0] = Math.max(0, -n5);
                n13 = n2;
                n5 = n2;
                if (bl) {
                    object = (LayoutParams)this.mTitleTextView.getLayoutParams();
                    n10 = this.mTitleTextView.getMeasuredWidth() + n13;
                    n14 = this.mTitleTextView.getMeasuredHeight() + n;
                    this.mTitleTextView.layout(n13, n, n10, n14);
                    n13 = n10 + this.mTitleMarginEnd;
                    n14 += ((LayoutParams)object).bottomMargin;
                } else {
                    n14 = n;
                }
                n = n2;
                n10 = n5;
                if (bl2) {
                    object = (LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    n2 = n14 + ((LayoutParams)object).topMargin;
                    n14 = this.mSubtitleTextView.getMeasuredWidth() + n5;
                    n10 = this.mSubtitleTextView.getMeasuredHeight() + n2;
                    this.mSubtitleTextView.layout(n5, n2, n14, n10);
                    n10 = n14 + this.mTitleMarginEnd;
                    n2 = ((LayoutParams)object).bottomMargin;
                }
                n2 = n4;
                if (n12 != 0) {
                    n = Math.max(n13, n10);
                    n2 = n4;
                }
            }
        }
        n4 = n3;
        this.addCustomViewsWithGravity(this.mTempViews, 3);
        n12 = this.mTempViews.size();
        for (n3 = 0; n3 < n12; ++n3) {
            n = this.layoutChildLeft(this.mTempViews.get(n3), n, arrn, n4);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 5);
        n12 = this.mTempViews.size();
        for (n3 = 0; n3 < n12; ++n3) {
            n2 = this.layoutChildRight(this.mTempViews.get(n3), n2, arrn, n4);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 1);
        n12 = this.getViewListMeasuredWidth(this.mTempViews, arrn);
        n3 = n8 + (n6 - n8 - n9) / 2 - n12 / 2;
        n12 = n3 + n12;
        if (n3 >= n) {
            n = n3;
            if (n12 > n2) {
                n = n3 - (n12 - n2);
            }
        }
        n3 = this.mTempViews.size();
        n2 = n;
        for (n = 0; n < n3; ++n) {
            n2 = this.layoutChildLeft(this.mTempViews.get(n), n2, arrn, n4);
        }
        this.mTempViews.clear();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5 = 0;
        int n6 = 0;
        int[] arrn = this.mTempMargins;
        if (this.isLayoutRtl()) {
            n4 = 1;
            n3 = 0;
        } else {
            n4 = 0;
            n3 = 1;
        }
        int n7 = 0;
        if (this.shouldLayout(this.mNavButtonView)) {
            this.measureChildConstrained(this.mNavButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n7 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins(this.mNavButtonView);
            n5 = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins(this.mNavButtonView));
            n6 = Toolbar.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
        }
        int n8 = n5;
        int n9 = n6;
        if (this.shouldLayout(this.mCollapseButtonView)) {
            this.measureChildConstrained(this.mCollapseButtonView, n, 0, n2, 0, this.mMaxButtonHeight);
            n7 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins(this.mCollapseButtonView);
            n8 = Math.max(n5, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins(this.mCollapseButtonView));
            n9 = Toolbar.combineMeasuredStates(n6, this.mCollapseButtonView.getMeasuredState());
        }
        n6 = this.getCurrentContentInsetStart();
        n5 = 0 + Math.max(n6, n7);
        arrn[n4] = Math.max(0, n6 - n7);
        if (this.shouldLayout(this.mMenuView)) {
            this.measureChildConstrained(this.mMenuView, n, n5, n2, 0, this.mMaxButtonHeight);
            n7 = this.mMenuView.getMeasuredWidth();
            n4 = this.getHorizontalMargins(this.mMenuView);
            n8 = Math.max(n8, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins(this.mMenuView));
            n6 = Toolbar.combineMeasuredStates(n9, this.mMenuView.getMeasuredState());
            n9 = n7 + n4;
        } else {
            n6 = n9;
            n9 = 0;
        }
        n4 = this.getCurrentContentInsetEnd();
        n7 = n5 + Math.max(n4, n9);
        arrn[n3] = Math.max(0, n4 - n9);
        if (this.shouldLayout(this.mExpandedActionView)) {
            n7 += this.measureChildCollapseMargins(this.mExpandedActionView, n, n7, n2, 0, arrn);
            n5 = Math.max(n8, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
            n6 = Toolbar.combineMeasuredStates(n6, this.mExpandedActionView.getMeasuredState());
        } else {
            n5 = n8;
        }
        n3 = n7;
        n4 = n5;
        n8 = n6;
        if (this.shouldLayout(this.mLogoView)) {
            n3 = n7 + this.measureChildCollapseMargins(this.mLogoView, n, n7, n2, 0, arrn);
            n4 = Math.max(n5, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins(this.mLogoView));
            n8 = Toolbar.combineMeasuredStates(n6, this.mLogoView.getMeasuredState());
        }
        n5 = this.getChildCount();
        n6 = n9;
        n9 = n4;
        for (n7 = 0; n7 < n5; ++n7) {
            View view = this.getChildAt(n7);
            if (((LayoutParams)view.getLayoutParams()).mViewType != 0 || !this.shouldLayout(view)) continue;
            n3 += this.measureChildCollapseMargins(view, n, n3, n2, 0, arrn);
            n9 = Math.max(n9, view.getMeasuredHeight() + this.getVerticalMargins(view));
            n8 = Toolbar.combineMeasuredStates(n8, view.getMeasuredState());
        }
        n7 = 0;
        n6 = 0;
        n4 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int n10 = this.mTitleMarginStart + this.mTitleMarginEnd;
        n5 = n8;
        if (this.shouldLayout(this.mTitleTextView)) {
            this.measureChildCollapseMargins(this.mTitleTextView, n, n3 + n10, n2, n4, arrn);
            n7 = this.mTitleTextView.getMeasuredWidth() + this.getHorizontalMargins(this.mTitleTextView);
            n6 = this.mTitleTextView.getMeasuredHeight() + this.getVerticalMargins(this.mTitleTextView);
            n5 = Toolbar.combineMeasuredStates(n8, this.mTitleTextView.getMeasuredState());
        }
        if (this.shouldLayout(this.mSubtitleTextView)) {
            n7 = Math.max(n7, this.measureChildCollapseMargins(this.mSubtitleTextView, n, n3 + n10, n2, n6 + n4, arrn));
            n4 = this.mSubtitleTextView.getMeasuredHeight();
            n8 = this.getVerticalMargins(this.mSubtitleTextView);
            n5 = Toolbar.combineMeasuredStates(n5, this.mSubtitleTextView.getMeasuredState());
            n6 += n4 + n8;
        }
        n6 = Math.max(n9, n6);
        n4 = this.getPaddingLeft();
        n10 = this.getPaddingRight();
        n9 = this.getPaddingTop();
        n8 = this.getPaddingBottom();
        n7 = Toolbar.resolveSizeAndState(Math.max(n3 + n7 + (n4 + n10), this.getSuggestedMinimumWidth()), n, -16777216 & n5);
        n = Toolbar.resolveSizeAndState(Math.max(n6 + (n9 + n8), this.getSuggestedMinimumHeight()), n2, n5 << 16);
        if (this.shouldCollapse()) {
            n = 0;
        }
        this.setMeasuredDimension(n7, n);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        SavedState savedState = (SavedState)object;
        super.onRestoreInstanceState(savedState.getSuperState());
        object = this.mMenuView;
        object = object != null ? ((ActionMenuView)object).peekMenu() : null;
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && object != null && (object = object.findItem(savedState.expandedMenuItemId)) != null) {
            object.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            this.postShowOverflowMenu();
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        this.ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        rtlSpacingHelper.setDirection(bl);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = this.isOverflowMenuShowing();
        return savedState;
    }

    @Override
    protected void onSetLayoutParams(View view, ViewGroup.LayoutParams layoutParams) {
        if (!this.checkLayoutParams(layoutParams)) {
            view.setLayoutParams(this.generateLayoutParams(layoutParams));
        }
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

    void removeChildrenForExpandedActionView() {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            View view = this.getChildAt(i);
            if (((LayoutParams)view.getLayoutParams()).mViewType == 2 || view == this.mMenuView) continue;
            this.removeViewAt(i);
            this.mHiddenViews.add(view);
        }
    }

    public void setCollapseContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getContext().getText(n) : null;
        this.setCollapseContentDescription(charSequence);
    }

    public void setCollapseContentDescription(CharSequence charSequence) {
        ImageButton imageButton;
        if (!TextUtils.isEmpty(charSequence)) {
            this.ensureCollapseButtonView();
        }
        if ((imageButton = this.mCollapseButtonView) != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setCollapseIcon(int n) {
        this.setCollapseIcon(this.getContext().getDrawable(n));
    }

    public void setCollapseIcon(Drawable object) {
        if (object != null) {
            this.ensureCollapseButtonView();
            this.mCollapseButtonView.setImageDrawable((Drawable)object);
        } else {
            object = this.mCollapseButtonView;
            if (object != null) {
                ((ImageView)object).setImageDrawable(this.mCollapseIcon);
            }
        }
    }

    public void setCollapsible(boolean bl) {
        this.mCollapsible = bl;
        this.requestLayout();
    }

    public void setContentInsetEndWithActions(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = n2;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int n) {
        int n2 = n;
        if (n < 0) {
            n2 = Integer.MIN_VALUE;
        }
        if (n2 != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = n2;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetsAbsolute(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(n, n2);
    }

    public void setContentInsetsRelative(int n, int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setRelative(n, n2);
    }

    public void setLogo(int n) {
        this.setLogo(this.getContext().getDrawable(n));
    }

    public void setLogo(Drawable drawable2) {
        ImageView imageView;
        if (drawable2 != null) {
            this.ensureLogoView();
            if (!this.isChildOrHidden(this.mLogoView)) {
                this.addSystemView(this.mLogoView, true);
            }
        } else {
            imageView = this.mLogoView;
            if (imageView != null && this.isChildOrHidden(imageView)) {
                this.removeView(this.mLogoView);
                this.mHiddenViews.remove(this.mLogoView);
            }
        }
        if ((imageView = this.mLogoView) != null) {
            imageView.setImageDrawable(drawable2);
        }
    }

    public void setLogoDescription(int n) {
        this.setLogoDescription(this.getContext().getText(n));
    }

    public void setLogoDescription(CharSequence charSequence) {
        ImageView imageView;
        if (!TextUtils.isEmpty(charSequence)) {
            this.ensureLogoView();
        }
        if ((imageView = this.mLogoView) != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder == null && this.mMenuView == null) {
            return;
        }
        this.ensureMenuView();
        MenuBuilder menuBuilder2 = this.mMenuView.peekMenu();
        if (menuBuilder2 == menuBuilder) {
            return;
        }
        if (menuBuilder2 != null) {
            menuBuilder2.removeMenuPresenter(this.mOuterActionMenuPresenter);
            menuBuilder2.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        ActionMenuView actionMenuView = this.mMenuView;
        if (actionMenuView != null) {
            actionMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    public void setNavigationContentDescription(int n) {
        CharSequence charSequence = n != 0 ? this.getContext().getText(n) : null;
        this.setNavigationContentDescription(charSequence);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        ImageButton imageButton;
        if (!TextUtils.isEmpty(charSequence)) {
            this.ensureNavButtonView();
        }
        if ((imageButton = this.mNavButtonView) != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(int n) {
        this.setNavigationIcon(this.getContext().getDrawable(n));
    }

    public void setNavigationIcon(Drawable drawable2) {
        ImageButton imageButton;
        if (drawable2 != null) {
            this.ensureNavButtonView();
            if (!this.isChildOrHidden(this.mNavButtonView)) {
                this.addSystemView(this.mNavButtonView, true);
            }
        } else {
            imageButton = this.mNavButtonView;
            if (imageButton != null && this.isChildOrHidden(imageButton)) {
                this.removeView(this.mNavButtonView);
                this.mHiddenViews.remove(this.mNavButtonView);
            }
        }
        if ((imageButton = this.mNavButtonView) != null) {
            imageButton.setImageDrawable(drawable2);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(Drawable drawable2) {
        this.ensureMenu();
        this.mMenuView.setOverflowIcon(drawable2);
    }

    public void setPopupTheme(int n) {
        if (this.mPopupTheme != n) {
            this.mPopupTheme = n;
            this.mPopupContext = n == 0 ? this.mContext : new ContextThemeWrapper(this.mContext, n);
        }
    }

    public void setSubtitle(int n) {
        this.setSubtitle(this.getContext().getText(n));
    }

    public void setSubtitle(CharSequence charSequence) {
        TextView textView;
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mSubtitleTextView == null) {
                this.mSubtitleTextView = new TextView(this.getContext());
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int n = this.mSubtitleTextAppearance;
                if (n != 0) {
                    this.mSubtitleTextView.setTextAppearance(n);
                }
                if ((n = this.mSubtitleTextColor) != 0) {
                    this.mSubtitleTextView.setTextColor(n);
                }
            }
            if (!this.isChildOrHidden(this.mSubtitleTextView)) {
                this.addSystemView(this.mSubtitleTextView, true);
            }
        } else {
            textView = this.mSubtitleTextView;
            if (textView != null && this.isChildOrHidden(textView)) {
                this.removeView(this.mSubtitleTextView);
                this.mHiddenViews.remove(this.mSubtitleTextView);
            }
        }
        if ((textView = this.mSubtitleTextView) != null) {
            textView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setSubtitleTextAppearance(Context object, int n) {
        this.mSubtitleTextAppearance = n;
        object = this.mSubtitleTextView;
        if (object != null) {
            ((TextView)object).setTextAppearance(n);
        }
    }

    public void setSubtitleTextColor(int n) {
        this.mSubtitleTextColor = n;
        TextView textView = this.mSubtitleTextView;
        if (textView != null) {
            textView.setTextColor(n);
        }
    }

    public void setTitle(int n) {
        this.setTitle(this.getContext().getText(n));
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView;
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mTitleTextView == null) {
                this.mTitleTextView = new TextView(this.getContext());
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int n = this.mTitleTextAppearance;
                if (n != 0) {
                    this.mTitleTextView.setTextAppearance(n);
                }
                if ((n = this.mTitleTextColor) != 0) {
                    this.mTitleTextView.setTextColor(n);
                }
            }
            if (!this.isChildOrHidden(this.mTitleTextView)) {
                this.addSystemView(this.mTitleTextView, true);
            }
        } else {
            textView = this.mTitleTextView;
            if (textView != null && this.isChildOrHidden(textView)) {
                this.removeView(this.mTitleTextView);
                this.mHiddenViews.remove(this.mTitleTextView);
            }
        }
        if ((textView = this.mTitleTextView) != null) {
            textView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public void setTitleMargin(int n, int n2, int n3, int n4) {
        this.mTitleMarginStart = n;
        this.mTitleMarginTop = n2;
        this.mTitleMarginEnd = n3;
        this.mTitleMarginBottom = n4;
        this.requestLayout();
    }

    public void setTitleMarginBottom(int n) {
        this.mTitleMarginBottom = n;
        this.requestLayout();
    }

    public void setTitleMarginEnd(int n) {
        this.mTitleMarginEnd = n;
        this.requestLayout();
    }

    public void setTitleMarginStart(int n) {
        this.mTitleMarginStart = n;
        this.requestLayout();
    }

    public void setTitleMarginTop(int n) {
        this.mTitleMarginTop = n;
        this.requestLayout();
    }

    public void setTitleTextAppearance(Context object, int n) {
        this.mTitleTextAppearance = n;
        object = this.mTitleTextView;
        if (object != null) {
            ((TextView)object).setTextAppearance(n);
        }
    }

    public void setTitleTextColor(int n) {
        this.mTitleTextColor = n;
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextColor(n);
        }
    }

    public boolean showOverflowMenu() {
        ActionMenuView actionMenuView = this.mMenuView;
        boolean bl = actionMenuView != null && actionMenuView.showOverflowMenu();
        return bl;
    }

    private class ExpandedActionViewMenuPresenter
    implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        private ExpandedActionViewMenuPresenter() {
        }

        @Override
        public boolean collapseItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)((Object)Toolbar.this.mExpandedActionView)).onActionViewCollapsed();
            }
            object = Toolbar.this;
            ((ViewGroup)object).removeView(((Toolbar)object).mExpandedActionView);
            object = Toolbar.this;
            ((ViewGroup)object).removeView(((Toolbar)object).mCollapseButtonView);
            object = Toolbar.this;
            ((Toolbar)object).mExpandedActionView = null;
            ((Toolbar)object).addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override
        public boolean expandItemActionView(MenuBuilder object, MenuItemImpl menuItemImpl) {
            Toolbar.this.ensureCollapseButtonView();
            object = Toolbar.this.mCollapseButtonView.getParent();
            ViewParent viewParent = Toolbar.this;
            if (object != viewParent) {
                viewParent.addView(viewParent.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = menuItemImpl.getActionView();
            this.mCurrentExpandedItem = menuItemImpl;
            viewParent = Toolbar.this.mExpandedActionView.getParent();
            if (viewParent != (object = Toolbar.this)) {
                object = ((Toolbar)object).generateDefaultLayoutParams();
                ((LayoutParams)object).gravity = 8388611 | Toolbar.this.mButtonGravity & 112;
                ((LayoutParams)object).mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams((ViewGroup.LayoutParams)object);
                object = Toolbar.this;
                ((ViewGroup)object).addView(((Toolbar)object).mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView)((Object)Toolbar.this.mExpandedActionView)).onActionViewExpanded();
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

    public static class LayoutParams
    extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType = 0;

        public LayoutParams(int n) {
            this(-2, -1, n);
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.gravity = 8388627;
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.gravity = n3;
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.copyMarginsFrom(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.mViewType = layoutParams.mViewType;
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
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
        public int expandedMenuItemId;
        public boolean isOverflowOpen;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.isOverflowOpen = bl;
        }

        public SavedState(Parcelable parcelable) {
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

