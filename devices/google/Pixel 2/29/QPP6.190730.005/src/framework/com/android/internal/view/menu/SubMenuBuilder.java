/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;

public class SubMenuBuilder
extends MenuBuilder
implements SubMenu {
    private MenuItemImpl mItem;
    private MenuBuilder mParentMenu;

    public SubMenuBuilder(Context context, MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        super(context);
        this.mParentMenu = menuBuilder;
        this.mItem = menuItemImpl;
    }

    @Override
    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.collapseItemActionView(menuItemImpl);
    }

    @Override
    boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        boolean bl = super.dispatchMenuItemSelected(menuBuilder, menuItem) || this.mParentMenu.dispatchMenuItemSelected(menuBuilder, menuItem);
        return bl;
    }

    @Override
    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.expandItemActionView(menuItemImpl);
    }

    @Override
    public String getActionViewStatesKey() {
        Object object = this.mItem;
        int n = object != null ? ((MenuItemImpl)object).getItemId() : 0;
        if (n == 0) {
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(super.getActionViewStatesKey());
        ((StringBuilder)object).append(":");
        ((StringBuilder)object).append(n);
        return ((StringBuilder)object).toString();
    }

    @Override
    public MenuItem getItem() {
        return this.mItem;
    }

    public Menu getParentMenu() {
        return this.mParentMenu;
    }

    @UnsupportedAppUsage
    @Override
    public MenuBuilder getRootMenu() {
        return this.mParentMenu.getRootMenu();
    }

    @Override
    public boolean isGroupDividerEnabled() {
        return this.mParentMenu.isGroupDividerEnabled();
    }

    @Override
    public boolean isQwertyMode() {
        return this.mParentMenu.isQwertyMode();
    }

    @Override
    public boolean isShortcutsVisible() {
        return this.mParentMenu.isShortcutsVisible();
    }

    @UnsupportedAppUsage
    @Override
    public void setCallback(MenuBuilder.Callback callback) {
        this.mParentMenu.setCallback(callback);
    }

    @Override
    public void setGroupDividerEnabled(boolean bl) {
        this.mParentMenu.setGroupDividerEnabled(bl);
    }

    @Override
    public SubMenu setHeaderIcon(int n) {
        return (SubMenu)((Object)super.setHeaderIconInt(n));
    }

    @Override
    public SubMenu setHeaderIcon(Drawable drawable2) {
        return (SubMenu)((Object)super.setHeaderIconInt(drawable2));
    }

    @Override
    public SubMenu setHeaderTitle(int n) {
        return (SubMenu)((Object)super.setHeaderTitleInt(n));
    }

    @Override
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        return (SubMenu)((Object)super.setHeaderTitleInt(charSequence));
    }

    @Override
    public SubMenu setHeaderView(View view) {
        return (SubMenu)((Object)super.setHeaderViewInt(view));
    }

    @Override
    public SubMenu setIcon(int n) {
        this.mItem.setIcon(n);
        return this;
    }

    @Override
    public SubMenu setIcon(Drawable drawable2) {
        this.mItem.setIcon(drawable2);
        return this;
    }

    @Override
    public void setQwertyMode(boolean bl) {
        this.mParentMenu.setQwertyMode(bl);
    }

    @Override
    public void setShortcutsVisible(boolean bl) {
        this.mParentMenu.setShortcutsVisible(bl);
    }
}

