/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import com.android.internal.view.menu.BaseMenuPresenter;
import com.android.internal.view.menu.IconMenuItemView;
import com.android.internal.view.menu.IconMenuView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import java.util.ArrayList;

public class IconMenuPresenter
extends BaseMenuPresenter {
    private static final String OPEN_SUBMENU_KEY = "android:menu:icon:submenu";
    private static final String VIEWS_TAG = "android:menu:icon";
    private int mMaxItems = -1;
    private IconMenuItemView mMoreView;
    MenuDialogHelper mOpenSubMenu;
    int mOpenSubMenuId;
    SubMenuPresenterCallback mSubMenuPresenterCallback = new SubMenuPresenterCallback();

    public IconMenuPresenter(Context context) {
        super(new ContextThemeWrapper(context, 16974871), 17367164, 17367163);
    }

    @Override
    protected void addItemView(View view, int n) {
        IconMenuItemView iconMenuItemView = (IconMenuItemView)view;
        IconMenuView iconMenuView = (IconMenuView)this.mMenuView;
        iconMenuItemView.setIconMenuView(iconMenuView);
        iconMenuItemView.setItemInvoker(iconMenuView);
        iconMenuItemView.setBackgroundDrawable(iconMenuView.getItemBackgroundDrawable());
        super.addItemView(view, n);
    }

    @Override
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView = (IconMenuItemView)itemView;
        ((IconMenuItemView)itemView).setItemData(menuItemImpl);
        ((IconMenuItemView)itemView).initialize(menuItemImpl.getTitleForItemView(itemView), menuItemImpl.getIcon());
        int n = menuItemImpl.isVisible() ? 0 : 8;
        ((IconMenuItemView)itemView).setVisibility(n);
        ((TextView)((Object)itemView)).setEnabled(((View)((Object)itemView)).isEnabled());
        ((View)((Object)itemView)).setLayoutParams(((IconMenuItemView)itemView).getTextAppropriateLayoutParams());
    }

    @Override
    protected boolean filterLeftoverView(ViewGroup viewGroup, int n) {
        if (viewGroup.getChildAt(n) != this.mMoreView) {
            return super.filterLeftoverView(viewGroup, n);
        }
        return false;
    }

    public int getNumActualItemsShown() {
        return ((IconMenuView)this.mMenuView).getNumActualItemsShown();
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        this.mMaxItems = -1;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        this.restoreHierarchyState((Bundle)parcelable);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (this.mMenuView == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        this.saveHierarchyState(bundle);
        int n = this.mOpenSubMenuId;
        if (n > 0) {
            bundle.putInt(OPEN_SUBMENU_KEY, n);
        }
        return bundle;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        MenuDialogHelper menuDialogHelper = new MenuDialogHelper(subMenuBuilder);
        menuDialogHelper.setPresenterCallback(this.mSubMenuPresenterCallback);
        menuDialogHelper.show(null);
        this.mOpenSubMenu = menuDialogHelper;
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    public void restoreHierarchyState(Bundle object) {
        int n;
        SparseArray<Parcelable> sparseArray = ((Bundle)object).getSparseParcelableArray(VIEWS_TAG);
        if (sparseArray != null) {
            ((View)((Object)this.mMenuView)).restoreHierarchyState(sparseArray);
        }
        if ((n = ((BaseBundle)object).getInt(OPEN_SUBMENU_KEY, 0)) > 0 && this.mMenu != null && (object = this.mMenu.findItem(n)) != null) {
            this.onSubMenuSelected((SubMenuBuilder)object.getSubMenu());
        }
    }

    public void saveHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = new SparseArray<Parcelable>();
        if (this.mMenuView != null) {
            ((View)((Object)this.mMenuView)).saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    @Override
    public boolean shouldIncludeItem(int n, MenuItemImpl menuItemImpl) {
        int n2 = this.mMenu.getNonActionItems().size();
        int n3 = this.mMaxItems;
        boolean bl = false;
        n = n2 == n3 && n < n3 || n < this.mMaxItems - 1 ? 1 : 0;
        boolean bl2 = bl;
        if (n != 0) {
            bl2 = bl;
            if (!menuItemImpl.isActionButton()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @Override
    public void updateMenuView(boolean bl) {
        ArrayList<MenuItemImpl> arrayList;
        IconMenuItemView iconMenuItemView;
        IconMenuView iconMenuView = (IconMenuView)this.mMenuView;
        if (this.mMaxItems < 0) {
            this.mMaxItems = iconMenuView.getMaxItems();
        }
        int n = (arrayList = this.mMenu.getNonActionItems()).size() > this.mMaxItems ? 1 : 0;
        super.updateMenuView(bl);
        if (n != 0 && ((iconMenuItemView = this.mMoreView) == null || iconMenuItemView.getParent() != iconMenuView)) {
            if (this.mMoreView == null) {
                this.mMoreView = iconMenuView.createMoreItemView();
                this.mMoreView.setBackgroundDrawable(iconMenuView.getItemBackgroundDrawable());
            }
            iconMenuView.addView(this.mMoreView);
        } else if (n == 0 && (iconMenuItemView = this.mMoreView) != null) {
            iconMenuView.removeView(iconMenuItemView);
        }
        n = n != 0 ? this.mMaxItems - 1 : arrayList.size();
        iconMenuView.setNumActualItemsShown(n);
    }

    class SubMenuPresenterCallback
    implements MenuPresenter.Callback {
        SubMenuPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder object, boolean bl) {
            object = IconMenuPresenter.this;
            ((IconMenuPresenter)object).mOpenSubMenuId = 0;
            if (((IconMenuPresenter)object).mOpenSubMenu != null) {
                IconMenuPresenter.this.mOpenSubMenu.dismiss();
                IconMenuPresenter.this.mOpenSubMenu = null;
            }
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder != null) {
                IconMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)menuBuilder).getItem().getItemId();
            }
            return false;
        }
    }

}

