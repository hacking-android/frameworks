/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.LinearLayout;
import com.android.internal.view.menu.ActionMenuItemView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;

public class ActionMenuView
extends LinearLayout
implements MenuBuilder.ItemInvoker,
MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int)(56.0f * f);
        this.mGeneratedItemPadding = (int)(4.0f * f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    static int measureChildForCells(View view, int n, int n2, int n3, int n4) {
        int n5;
        boolean bl;
        LayoutParams layoutParams;
        block8 : {
            int n6;
            block9 : {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n5 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(n3) - n4, View.MeasureSpec.getMode(n3));
                ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView)view : null;
                bl = false;
                n4 = actionMenuItemView != null && actionMenuItemView.hasText() ? 1 : 0;
                n3 = n6 = 0;
                if (n2 <= 0) break block8;
                if (n4 == 0) break block9;
                n3 = n6;
                if (n2 < 2) break block8;
            }
            view.measure(View.MeasureSpec.makeMeasureSpec(n * n2, Integer.MIN_VALUE), n5);
            n6 = view.getMeasuredWidth();
            n2 = n3 = n6 / n;
            if (n6 % n != 0) {
                n2 = n3 + 1;
            }
            n3 = n2;
            if (n4 != 0) {
                n3 = n2;
                if (n2 < 2) {
                    n3 = 2;
                }
            }
        }
        boolean bl2 = bl;
        if (!layoutParams.isOverflowButton) {
            bl2 = bl;
            if (n4 != 0) {
                bl2 = true;
            }
        }
        layoutParams.expandable = bl2;
        layoutParams.cellsUsed = n3;
        view.measure(View.MeasureSpec.makeMeasureSpec(n3 * n, 1073741824), n5);
        return n3;
    }

    private void onMeasureExactFormat(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        Object object;
        Object object2;
        int n8;
        int n9;
        int n10;
        block45 : {
            long l;
            int n11;
            int n12;
            block46 : {
                int n13;
                int n14;
                block44 : {
                    int n15;
                    n4 = View.MeasureSpec.getMode(n2);
                    n = View.MeasureSpec.getSize(n);
                    n7 = View.MeasureSpec.getSize(n2);
                    int n16 = this.getPaddingLeft() + this.getPaddingRight();
                    int n17 = this.getPaddingTop() + this.getPaddingBottom();
                    n6 = ActionMenuView.getChildMeasureSpec(n2, n17, -2);
                    int n18 = n - n16;
                    n = this.mMinCellSize;
                    n5 = n18 / n;
                    n11 = n18 % n;
                    if (n5 == 0) {
                        this.setMeasuredDimension(n18, 0);
                        return;
                    }
                    n8 = n + n11 / n5;
                    n3 = 0;
                    int n19 = this.getChildCount();
                    n9 = 0;
                    n14 = 0;
                    n10 = 0;
                    n13 = 0;
                    n = n5;
                    l = 0L;
                    for (n12 = 0; n12 < n19; ++n12) {
                        object2 = this.getChildAt(n12);
                        if (((View)object2).getVisibility() == 8) {
                            n2 = n10;
                        } else {
                            boolean bl = object2 instanceof ActionMenuItemView;
                            ++n14;
                            if (bl) {
                                n2 = this.mGeneratedItemPadding;
                                ((View)object2).setPadding(n2, 0, n2, 0);
                            }
                            object = (LayoutParams)((View)object2).getLayoutParams();
                            ((LayoutParams)object).expanded = false;
                            ((LayoutParams)object).extraPixels = 0;
                            ((LayoutParams)object).cellsUsed = 0;
                            ((LayoutParams)object).expandable = false;
                            ((LayoutParams)object).leftMargin = 0;
                            ((LayoutParams)object).rightMargin = 0;
                            bl = bl && ((ActionMenuItemView)object2).hasText();
                            ((LayoutParams)object).preventEdgeOffset = bl;
                            n2 = ((LayoutParams)object).isOverflowButton ? 1 : n;
                            n15 = ActionMenuView.measureChildForCells((View)object2, n8, n2, n6, n17);
                            n13 = Math.max(n13, n15);
                            n2 = n10;
                            if (((LayoutParams)object).expandable) {
                                n2 = n10 + 1;
                            }
                            if (((LayoutParams)object).isOverflowButton) {
                                n3 = 1;
                            }
                            n -= n15;
                            n9 = Math.max(n9, ((View)object2).getMeasuredHeight());
                            if (n15 == 1) {
                                l |= (long)(1 << n12);
                            }
                        }
                        n10 = n2;
                    }
                    n16 = n3 != 0 && n14 == 2 ? 1 : 0;
                    n11 = 0;
                    n12 = n;
                    n2 = n19;
                    n = n11;
                    n5 = n18;
                    while (n10 > 0 && n12 > 0) {
                        long l2;
                        long l3 = 0L;
                        n18 = Integer.MAX_VALUE;
                        n11 = 0;
                        for (n19 = 0; n19 < n2; ++n19) {
                            object = (LayoutParams)this.getChildAt(n19).getLayoutParams();
                            if (!((LayoutParams)object).expandable) {
                                n17 = n11;
                                n15 = n18;
                                l2 = l3;
                            } else if (((LayoutParams)object).cellsUsed < n18) {
                                n15 = ((LayoutParams)object).cellsUsed;
                                l2 = 1 << n19;
                                n17 = 1;
                            } else {
                                n17 = n11;
                                n15 = n18;
                                l2 = l3;
                                if (((LayoutParams)object).cellsUsed == n18) {
                                    l2 = 1 << n19;
                                    n17 = n11 + 1;
                                    l2 = l3 | l2;
                                    n15 = n18;
                                }
                            }
                            n11 = n17;
                            n18 = n15;
                            l3 = l2;
                        }
                        n17 = n;
                        n = n2;
                        l |= l3;
                        if (n11 > n12) {
                            n10 = n;
                            n = n17;
                            break block44;
                        }
                        for (n2 = 0; n2 < n; ++n2) {
                            object = this.getChildAt(n2);
                            object2 = (LayoutParams)((View)object).getLayoutParams();
                            if ((l3 & (long)(1 << n2)) == 0L) {
                                n17 = n12;
                                l2 = l;
                                if (((LayoutParams)object2).cellsUsed == n18 + 1) {
                                    l2 = l | (long)(1 << n2);
                                    n17 = n12;
                                }
                            } else {
                                if (n16 != 0 && ((LayoutParams)object2).preventEdgeOffset && n12 == 1) {
                                    n17 = this.mGeneratedItemPadding;
                                    ((View)object).setPadding(n17 + n8, 0, n17, 0);
                                }
                                ++((LayoutParams)object2).cellsUsed;
                                ((LayoutParams)object2).expanded = true;
                                n17 = n12 - 1;
                                l2 = l;
                            }
                            n12 = n17;
                            l = l2;
                        }
                        n11 = 1;
                        n2 = n;
                        n = n11;
                    }
                    n10 = n2;
                }
                n2 = n3 == 0 && n14 == 1 ? 1 : 0;
                n3 = n;
                if (n12 <= 0) break block45;
                n3 = n;
                if (l == 0L) break block45;
                if (n12 < n14 - 1 || n2 != 0) break block46;
                n3 = n;
                if (n13 <= true) break block45;
            }
            float f = Long.bitCount(l);
            if (n2 == 0) {
                float f2;
                if ((l & 1L) != 0L) {
                    f2 = f;
                    if (!((LayoutParams)this.getChildAt((int)0).getLayoutParams()).preventEdgeOffset) {
                        f2 = f - 0.5f;
                    }
                } else {
                    f2 = f;
                }
                f = f2;
                if ((l & (long)(1 << n10 - 1)) != 0L) {
                    f = f2;
                    if (!((LayoutParams)this.getChildAt((int)(n10 - 1)).getLayoutParams()).preventEdgeOffset) {
                        f = f2 - 0.5f;
                    }
                }
            }
            n3 = 0;
            if (f > 0.0f) {
                n3 = (int)((float)(n12 * n8) / f);
            }
            n2 = n;
            for (n11 = 0; n11 < n10; ++n11) {
                if ((l & (long)(1 << n11)) == 0L) {
                    n = n2;
                } else {
                    object = this.getChildAt(n11);
                    object2 = (LayoutParams)((View)object).getLayoutParams();
                    if (object instanceof ActionMenuItemView) {
                        ((LayoutParams)object2).extraPixels = n3;
                        ((LayoutParams)object2).expanded = true;
                        if (n11 == 0 && !((LayoutParams)object2).preventEdgeOffset) {
                            ((LayoutParams)object2).leftMargin = -n3 / 2;
                        }
                        n = 1;
                    } else if (((LayoutParams)object2).isOverflowButton) {
                        ((LayoutParams)object2).extraPixels = n3;
                        ((LayoutParams)object2).expanded = true;
                        ((LayoutParams)object2).rightMargin = -n3 / 2;
                        n = 1;
                    } else {
                        if (n11 != 0) {
                            ((LayoutParams)object2).leftMargin = n3 / 2;
                        }
                        n = n2;
                        if (n11 != n10 - 1) {
                            ((LayoutParams)object2).rightMargin = n3 / 2;
                            n = n2;
                        }
                    }
                }
                n2 = n;
            }
            n3 = n2;
        }
        if (n3 != 0) {
            for (n = 0; n < n10; ++n) {
                object = this.getChildAt(n);
                object2 = (LayoutParams)((View)object).getLayoutParams();
                if (!((LayoutParams)object2).expanded) continue;
                ((View)object).measure(View.MeasureSpec.makeMeasureSpec(((LayoutParams)object2).cellsUsed * n8 + ((LayoutParams)object2).extraPixels, 1073741824), n6);
            }
        }
        n = n4 != 1073741824 ? n9 : n7;
        this.setMeasuredDimension(n5, n);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        boolean bl = layoutParams != null && layoutParams instanceof LayoutParams;
        return bl;
    }

    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus();
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams != null) {
            layoutParams = layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams)layoutParams) : new LayoutParams(layoutParams);
            if (((LayoutParams)layoutParams).gravity <= 0) {
                ((LayoutParams)layoutParams).gravity = 16;
            }
            return layoutParams;
        }
        return this.generateDefaultLayoutParams();
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        ViewGroup.LayoutParams layoutParams = this.generateDefaultLayoutParams();
        ((LayoutParams)layoutParams).isOverflowButton = true;
        return layoutParams;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Object object = this.getContext();
            this.mMenu = new MenuBuilder((Context)object);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter((Context)object);
            this.mPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter = this.mPresenter;
            object = this.mActionMenuPresenterCallback;
            if (object == null) {
                object = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter.setCallback((MenuPresenter.Callback)object);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public Drawable getOverflowIcon() {
        this.getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @Override
    public int getWindowAnimations() {
        return 0;
    }

    @UnsupportedAppUsage
    @Override
    protected boolean hasDividerBeforeChildAt(int n) {
        boolean bl;
        if (n == 0) {
            return false;
        }
        View view = this.getChildAt(n - 1);
        View view2 = this.getChildAt(n);
        boolean bl2 = bl = false;
        if (n < this.getChildCount()) {
            bl2 = bl;
            if (view instanceof ActionMenuChildView) {
                bl2 = false | ((ActionMenuChildView)((Object)view)).needsDividerAfter();
            }
        }
        bl = bl2;
        if (n > 0) {
            bl = bl2;
            if (view2 instanceof ActionMenuChildView) {
                bl = bl2 | ((ActionMenuChildView)((Object)view2)).needsDividerBefore();
            }
        }
        return bl;
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        boolean bl = actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu();
        return bl;
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @UnsupportedAppUsage
    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        boolean bl = actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowPending();
        return bl;
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        boolean bl = actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override
    public void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = this.mPresenter;
        if (object != null) {
            ((ActionMenuPresenter)object).updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.dismissPopupMenus();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5;
        Object object;
        Object object2;
        Object object3 = this;
        if (!((ActionMenuView)object3).mFormatItems) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
        int n6 = this.getChildCount();
        int n7 = (n4 - n2) / 2;
        int n8 = this.getDividerWidth();
        n2 = 0;
        int n9 = 0;
        int n10 = 0;
        n4 = n3 - n - this.getPaddingRight() - this.getPaddingLeft();
        int n11 = 0;
        bl = this.isLayoutRtl();
        for (n5 = 0; n5 < n6; ++n5) {
            int n12;
            int n13;
            object = ((ViewGroup)object3).getChildAt(n5);
            if (((View)object).getVisibility() == 8) continue;
            object2 = (LayoutParams)((View)object).getLayoutParams();
            if (((LayoutParams)object2).isOverflowButton) {
                n2 = n11 = ((View)object).getMeasuredWidth();
                if (((ActionMenuView)object3).hasDividerBeforeChildAt(n5)) {
                    n2 = n11 + n8;
                }
                n13 = ((View)object).getMeasuredHeight();
                if (bl) {
                    n11 = this.getPaddingLeft() + ((LayoutParams)object2).leftMargin;
                    n12 = n11 + n2;
                } else {
                    n12 = this.getWidth() - this.getPaddingRight() - ((LayoutParams)object2).rightMargin;
                    n11 = n12 - n2;
                }
                int n14 = n7 - n13 / 2;
                ((View)object).layout(n11, n14, n12, n14 + n13);
                n4 -= n2;
                n11 = 1;
                continue;
            }
            n13 = ((View)object).getMeasuredWidth() + ((LayoutParams)object2).leftMargin + ((LayoutParams)object2).rightMargin;
            n12 = n9 + n13;
            n4 -= n13;
            n9 = n12;
            if (((ActionMenuView)object3).hasDividerBeforeChildAt(n5)) {
                n9 = n12 + n8;
            }
            ++n10;
        }
        n9 = 1;
        if (n6 == 1 && n11 == 0) {
            object3 = ((ViewGroup)object3).getChildAt(0);
            n4 = ((View)object3).getMeasuredWidth();
            n2 = ((View)object3).getMeasuredHeight();
            n = (n3 - n) / 2 - n4 / 2;
            n3 = n7 - n2 / 2;
            ((View)object3).layout(n, n3, n + n4, n3 + n2);
            return;
        }
        n = n9;
        if (n11 != 0) {
            n = 0;
        }
        n = (n = n10 - n) > 0 ? n4 / n : 0;
        n9 = Math.max(0, n);
        if (bl) {
            n4 = this.getWidth() - this.getPaddingRight();
            n3 = n8;
            for (n = 0; n < n6; ++n) {
                object2 = ((ViewGroup)object3).getChildAt(n);
                object = (LayoutParams)((View)object2).getLayoutParams();
                if (((View)object2).getVisibility() == 8 || ((LayoutParams)object).isOverflowButton) continue;
                n10 = n4 - ((LayoutParams)object).rightMargin;
                n11 = ((View)object2).getMeasuredWidth();
                n5 = ((View)object2).getMeasuredHeight();
                n4 = n7 - n5 / 2;
                ((View)object2).layout(n10 - n11, n4, n10, n4 + n5);
                n4 = n10 - (((LayoutParams)object).leftMargin + n11 + n9);
            }
        } else {
            n2 = this.getPaddingLeft();
            for (n = 0; n < n6; ++n) {
                object = this.getChildAt(n);
                object3 = (LayoutParams)((View)object).getLayoutParams();
                n3 = n2;
                if (((View)object).getVisibility() != 8) {
                    if (((LayoutParams)object3).isOverflowButton) {
                        n3 = n2;
                    } else {
                        n3 = n2 + ((LayoutParams)object3).leftMargin;
                        n4 = ((View)object).getMeasuredWidth();
                        n2 = ((View)object).getMeasuredHeight();
                        n5 = n7 - n2 / 2;
                        ((View)object).layout(n3, n5, n3 + n4, n5 + n2);
                        n3 += ((LayoutParams)object3).rightMargin + n4 + n9;
                    }
                }
                n2 = n3;
            }
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        Object object;
        boolean bl = this.mFormatItems;
        boolean bl2 = View.MeasureSpec.getMode(n) == 1073741824;
        this.mFormatItems = bl2;
        if (bl != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int n3 = View.MeasureSpec.getSize(n);
        if (this.mFormatItems && (object = this.mMenu) != null && n3 != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = n3;
            ((MenuBuilder)object).onItemsChanged(true);
        }
        int n4 = this.getChildCount();
        if (this.mFormatItems && n4 > 0) {
            this.onMeasureExactFormat(n, n2);
        } else {
            for (n3 = 0; n3 < n4; ++n3) {
                object = (LayoutParams)this.getChildAt(n3).getLayoutParams();
                ((LayoutParams)object).rightMargin = 0;
                ((LayoutParams)object).leftMargin = 0;
            }
            super.onMeasure(n, n2);
        }
    }

    @UnsupportedAppUsage
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    @UnsupportedAppUsage
    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mPresenter.setExpandedActionViewsExclusive(bl);
    }

    @UnsupportedAppUsage
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOverflowIcon(Drawable drawable2) {
        this.getMenu();
        this.mPresenter.setOverflowIcon(drawable2);
    }

    public void setOverflowReserved(boolean bl) {
        this.mReserveOverflow = bl;
    }

    public void setPopupTheme(int n) {
        if (this.mPopupTheme != n) {
            this.mPopupTheme = n;
            this.mPopupContext = n == 0 ? this.mContext : new ContextThemeWrapper(this.mContext, n);
        }
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        this.mPresenter.setMenuView(this);
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        boolean bl = actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu();
        return bl;
    }

    public static interface ActionMenuChildView {
        public boolean needsDividerAfter();

        @UnsupportedAppUsage
        public boolean needsDividerBefore();
    }

    private class ActionMenuPresenterCallback
    implements MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        public int cellsUsed;
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        public boolean expandable;
        @UnsupportedAppUsage
        public boolean expanded;
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        public int extraPixels;
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty(category="layout")
        @UnsupportedAppUsage
        public boolean preventEdgeOffset;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.isOverflowButton = false;
        }

        public LayoutParams(int n, int n2, boolean bl) {
            super(n, n2);
            this.isOverflowButton = bl;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:overFlowButton", this.isOverflowButton);
            viewHierarchyEncoder.addProperty("layout:cellsUsed", this.cellsUsed);
            viewHierarchyEncoder.addProperty("layout:extraPixels", this.extraPixels);
            viewHierarchyEncoder.addProperty("layout:expandable", this.expandable);
            viewHierarchyEncoder.addProperty("layout:preventEdgeOffset", this.preventEdgeOffset);
        }
    }

    private class MenuBuilderCallback
    implements MenuBuilder.Callback {
        private MenuBuilderCallback() {
        }

        @Override
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            boolean bl = ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
            return bl;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menuBuilder) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
            }
        }
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

}

