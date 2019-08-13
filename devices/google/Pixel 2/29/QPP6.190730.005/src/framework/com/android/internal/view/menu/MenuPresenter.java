/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Parcelable;
import android.view.ViewGroup;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;

public interface MenuPresenter {
    public boolean collapseItemActionView(MenuBuilder var1, MenuItemImpl var2);

    public boolean expandItemActionView(MenuBuilder var1, MenuItemImpl var2);

    public boolean flagActionItems();

    public int getId();

    public MenuView getMenuView(ViewGroup var1);

    public void initForMenu(Context var1, MenuBuilder var2);

    public void onCloseMenu(MenuBuilder var1, boolean var2);

    public void onRestoreInstanceState(Parcelable var1);

    public Parcelable onSaveInstanceState();

    public boolean onSubMenuSelected(SubMenuBuilder var1);

    public void setCallback(Callback var1);

    public void updateMenuView(boolean var1);

    public static interface Callback {
        public void onCloseMenu(MenuBuilder var1, boolean var2);

        @UnsupportedAppUsage
        public boolean onOpenSubMenu(MenuBuilder var1);
    }

}

