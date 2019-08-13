/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.widget.AbsActionBarView;

public class ActionBarContextView
extends AbsActionBarView {
    private static final String TAG = "ActionBarContextView";
    private View mClose;
    private int mCloseItemLayout;
    private View mCustomView;
    private Drawable mSplitBackground;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    public ActionBarContextView(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843668);
    }

    public ActionBarContextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ActionBarContextView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ActionMode, n, n2);
        this.setBackground(((TypedArray)object).getDrawable(0));
        this.mTitleStyleRes = ((TypedArray)object).getResourceId(2, 0);
        this.mSubtitleStyleRes = ((TypedArray)object).getResourceId(3, 0);
        this.mContentHeight = ((TypedArray)object).getLayoutDimension(1, 0);
        this.mSplitBackground = ((TypedArray)object).getDrawable(4);
        this.mCloseItemLayout = ((TypedArray)object).getResourceId(5, 17367073);
        ((TypedArray)object).recycle();
    }

    private void initTitle() {
        int n;
        if (this.mTitleLayout == null) {
            LayoutInflater.from(this.getContext()).inflate(17367068, (ViewGroup)this);
            this.mTitleLayout = (LinearLayout)this.getChildAt(this.getChildCount() - 1);
            this.mTitleView = (TextView)this.mTitleLayout.findViewById(16908686);
            this.mSubtitleView = (TextView)this.mTitleLayout.findViewById(16908685);
            n = this.mTitleStyleRes;
            if (n != 0) {
                this.mTitleView.setTextAppearance(n);
            }
            if ((n = this.mSubtitleStyleRes) != 0) {
                this.mSubtitleView.setTextAppearance(n);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean bl = TextUtils.isEmpty(this.mTitle);
        boolean bl2 = TextUtils.isEmpty(this.mSubtitle) ^ true;
        View view = this.mSubtitleView;
        int n2 = 0;
        n = bl2 ? 0 : 8;
        view.setVisibility(n);
        view = this.mTitleLayout;
        n = n2;
        if (!(bl ^ true)) {
            n = bl2 ? n2 : 8;
        }
        view.setVisibility(n);
        if (this.mTitleLayout.getParent() == null) {
            this.addView(this.mTitleLayout);
        }
    }

    public void closeMode() {
        if (this.mClose == null) {
            this.killMode();
            return;
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(this.getContext(), attributeSet);
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override
    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public void initForMode(ActionMode object) {
        Object object2 = this.mClose;
        if (object2 == null) {
            this.mClose = LayoutInflater.from(this.mContext).inflate(this.mCloseItemLayout, (ViewGroup)this, false);
            this.addView(this.mClose);
        } else if (((View)object2).getParent() == null) {
            this.addView(this.mClose);
        }
        ((View)this.mClose.findViewById(16908693)).setOnClickListener(new View.OnClickListener((ActionMode)object){
            final /* synthetic */ ActionMode val$mode;
            {
                this.val$mode = actionMode;
            }

            @Override
            public void onClick(View view) {
                this.val$mode.finish();
            }
        });
        object = (MenuBuilder)((ActionMode)object).getMenu();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
        this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
        this.mActionMenuPresenter.setReserveOverflow(true);
        object2 = new ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            ((MenuBuilder)object).addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            this.mMenuView = (ActionMenuView)this.mActionMenuPresenter.getMenuView(this);
            this.mMenuView.setBackground(null);
            this.addView((View)this.mMenuView, (ViewGroup.LayoutParams)object2);
        } else {
            this.mActionMenuPresenter.setWidthLimit(this.getContext().getResources().getDisplayMetrics().widthPixels, true);
            this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
            ((ViewGroup.LayoutParams)object2).width = -1;
            ((ViewGroup.LayoutParams)object2).height = this.mContentHeight;
            ((MenuBuilder)object).addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            this.mMenuView = (ActionMenuView)this.mActionMenuPresenter.getMenuView(this);
            this.mMenuView.setBackgroundDrawable(this.mSplitBackground);
            this.mSplitView.addView((View)this.mMenuView, (ViewGroup.LayoutParams)object2);
        }
    }

    @Override
    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isTitleOptional() {
        return this.mTitleOptional;
    }

    public void killMode() {
        this.removeAllViews();
        if (this.mSplitView != null) {
            this.mSplitView.removeView(this.mMenuView);
        }
        this.mCustomView = null;
        this.mMenuView = null;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            accessibilityEvent.setSource(this);
            accessibilityEvent.setClassName(this.getClass().getName());
            accessibilityEvent.setPackageName(this.getContext().getPackageName());
            accessibilityEvent.setContentDescription(this.mTitle);
        } else {
            super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        bl = this.isLayoutRtl();
        int n5 = bl ? n3 - n - this.getPaddingRight() : this.getPaddingLeft();
        int n6 = this.getPaddingTop();
        int n7 = n4 - n2 - this.getPaddingTop() - this.getPaddingBottom();
        Object object = this.mClose;
        if (object != null && ((View)object).getVisibility() != 8) {
            object = (ViewGroup.MarginLayoutParams)this.mClose.getLayoutParams();
            n2 = bl ? ((ViewGroup.MarginLayoutParams)object).rightMargin : ((ViewGroup.MarginLayoutParams)object).leftMargin;
            n4 = bl ? ((ViewGroup.MarginLayoutParams)object).leftMargin : ((ViewGroup.MarginLayoutParams)object).rightMargin;
            n2 = ActionBarContextView.next(n5, n2, bl);
            n2 = ActionBarContextView.next(n2 + this.positionChild(this.mClose, n2, n6, n7, bl), n4, bl);
        } else {
            n2 = n5;
        }
        object = this.mTitleLayout;
        n4 = n2;
        if (object != null) {
            n4 = n2;
            if (this.mCustomView == null) {
                n4 = n2;
                if (((View)object).getVisibility() != 8) {
                    n4 = n2 + this.positionChild(this.mTitleLayout, n2, n6, n7, bl);
                }
            }
        }
        if ((object = this.mCustomView) != null) {
            this.positionChild((View)object, n4, n6, n7, bl);
        }
        n = bl ? this.getPaddingLeft() : n3 - n - this.getPaddingRight();
        if (this.mMenuView != null) {
            this.positionChild(this.mMenuView, n, n6, n7, bl ^ true);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode(n);
        int n4 = 1073741824;
        if (n3 == 1073741824) {
            if (View.MeasureSpec.getMode(n2) != 0) {
                int n5 = View.MeasureSpec.getSize(n);
                n3 = this.mContentHeight > 0 ? this.mContentHeight : View.MeasureSpec.getSize(n2);
                int n6 = this.getPaddingTop() + this.getPaddingBottom();
                n = n5 - this.getPaddingLeft() - this.getPaddingRight();
                int n7 = n3 - n6;
                int n8 = View.MeasureSpec.makeMeasureSpec(n7, Integer.MIN_VALUE);
                Object object = this.mClose;
                int n9 = 0;
                n2 = n;
                if (object != null) {
                    n = this.measureChildView((View)object, n, n8, 0);
                    object = (ViewGroup.MarginLayoutParams)this.mClose.getLayoutParams();
                    n2 = n - (((ViewGroup.MarginLayoutParams)object).leftMargin + ((ViewGroup.MarginLayoutParams)object).rightMargin);
                }
                n = n2;
                if (this.mMenuView != null) {
                    n = n2;
                    if (this.mMenuView.getParent() == this) {
                        n = this.measureChildView(this.mMenuView, n2, n8, 0);
                    }
                }
                object = this.mTitleLayout;
                n2 = n;
                if (object != null) {
                    n2 = n;
                    if (this.mCustomView == null) {
                        if (this.mTitleOptional) {
                            n2 = View.MeasureSpec.makeSafeMeasureSpec(n5, 0);
                            this.mTitleLayout.measure(n2, n8);
                            int n10 = this.mTitleLayout.getMeasuredWidth();
                            n8 = n10 <= n ? 1 : 0;
                            n2 = n;
                            if (n8 != 0) {
                                n2 = n - n10;
                            }
                            object = this.mTitleLayout;
                            n = n8 != 0 ? n9 : 8;
                            ((View)object).setVisibility(n);
                        } else {
                            n2 = this.measureChildView((View)object, n, n8, 0);
                        }
                    }
                }
                if ((object = this.mCustomView) != null) {
                    object = ((View)object).getLayoutParams();
                    n = ((ViewGroup.LayoutParams)object).width != -2 ? 1073741824 : Integer.MIN_VALUE;
                    if (((ViewGroup.LayoutParams)object).width >= 0) {
                        n2 = Math.min(((ViewGroup.LayoutParams)object).width, n2);
                    }
                    n8 = ((ViewGroup.LayoutParams)object).height != -2 ? n4 : Integer.MIN_VALUE;
                    n4 = ((ViewGroup.LayoutParams)object).height >= 0 ? Math.min(((ViewGroup.LayoutParams)object).height, n7) : n7;
                    this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(n2, n), View.MeasureSpec.makeMeasureSpec(n4, n8));
                }
                if (this.mContentHeight <= 0) {
                    n3 = 0;
                    n4 = this.getChildCount();
                    for (n = 0; n < n4; ++n) {
                        n8 = this.getChildAt(n).getMeasuredHeight() + n6;
                        n2 = n3;
                        if (n8 > n3) {
                            n2 = n8;
                        }
                        n3 = n2;
                    }
                    this.setMeasuredDimension(n5, n3);
                } else {
                    this.setMeasuredDimension(n5, n3);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getSimpleName());
            stringBuilder.append(" can only be used with android:layout_height=\"wrap_content\"");
            throw new IllegalStateException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public void setContentHeight(int n) {
        this.mContentHeight = n;
    }

    public void setCustomView(View view) {
        View view2 = this.mCustomView;
        if (view2 != null) {
            this.removeView(view2);
        }
        this.mCustomView = view;
        if (view != null && (view2 = this.mTitleLayout) != null) {
            this.removeView(view2);
            this.mTitleLayout = null;
        }
        if (view != null) {
            this.addView(view);
        }
        this.requestLayout();
    }

    @Override
    public void setSplitToolbar(boolean bl) {
        if (this.mSplitActionBar != bl) {
            if (this.mActionMenuPresenter != null) {
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
                if (!bl) {
                    this.mMenuView = (ActionMenuView)this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView.setBackground(null);
                    ViewGroup viewGroup = (ViewGroup)this.mMenuView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.mMenuView);
                    }
                    this.addView((View)this.mMenuView, layoutParams);
                } else {
                    this.mActionMenuPresenter.setWidthLimit(this.getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                    layoutParams.width = -1;
                    layoutParams.height = this.mContentHeight;
                    this.mMenuView = (ActionMenuView)this.mActionMenuPresenter.getMenuView(this);
                    this.mMenuView.setBackground(this.mSplitBackground);
                    ViewGroup viewGroup = (ViewGroup)this.mMenuView.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.mMenuView);
                    }
                    this.mSplitView.addView((View)this.mMenuView, layoutParams);
                }
            }
            super.setSplitToolbar(bl);
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        this.initTitle();
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.initTitle();
    }

    public void setTitleOptional(boolean bl) {
        if (bl != this.mTitleOptional) {
            this.requestLayout();
        }
        this.mTitleOptional = bl;
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

}

