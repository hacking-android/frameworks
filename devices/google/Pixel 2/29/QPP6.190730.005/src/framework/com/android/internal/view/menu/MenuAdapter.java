/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.internal.view.menu.ListMenuItemView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;
import java.util.ArrayList;

public class MenuAdapter
extends BaseAdapter {
    MenuBuilder mAdapterMenu;
    private int mExpandedIndex = -1;
    private boolean mForceShowIcon;
    private final LayoutInflater mInflater;
    private final int mItemLayoutRes;
    private final boolean mOverflowOnly;

    public MenuAdapter(MenuBuilder menuBuilder, LayoutInflater layoutInflater, boolean bl, int n) {
        this.mOverflowOnly = bl;
        this.mInflater = layoutInflater;
        this.mAdapterMenu = menuBuilder;
        this.mItemLayoutRes = n;
        this.findExpandedIndex();
    }

    void findExpandedIndex() {
        MenuItemImpl menuItemImpl = this.mAdapterMenu.getExpandedItem();
        if (menuItemImpl != null) {
            ArrayList<MenuItemImpl> arrayList = this.mAdapterMenu.getNonActionItems();
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                if (arrayList.get(i) != menuItemImpl) continue;
                this.mExpandedIndex = i;
                return;
            }
        }
        this.mExpandedIndex = -1;
    }

    public MenuBuilder getAdapterMenu() {
        return this.mAdapterMenu;
    }

    @Override
    public int getCount() {
        ArrayList<MenuItemImpl> arrayList = this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
        if (this.mExpandedIndex < 0) {
            return arrayList.size();
        }
        return arrayList.size() - 1;
    }

    public boolean getForceShowIcon() {
        return this.mForceShowIcon;
    }

    @Override
    public MenuItemImpl getItem(int n) {
        ArrayList<MenuItemImpl> arrayList = this.mOverflowOnly ? this.mAdapterMenu.getNonActionItems() : this.mAdapterMenu.getVisibleItems();
        int n2 = this.mExpandedIndex;
        int n3 = n;
        if (n2 >= 0) {
            n3 = n;
            if (n >= n2) {
                n3 = n + 1;
            }
        }
        return arrayList.get(n3);
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public View getView(int n, View object, ViewGroup viewGroup) {
        View view = object;
        if (object == null) {
            view = this.mInflater.inflate(this.mItemLayoutRes, viewGroup, false);
        }
        int n2 = this.getItem(n).getGroupId();
        int n3 = n - 1 >= 0 ? this.getItem(n - 1).getGroupId() : n2;
        object = (ListMenuItemView)view;
        boolean bl = this.mAdapterMenu.isGroupDividerEnabled() && n2 != n3;
        ((ListMenuItemView)object).setGroupDividerEnabled(bl);
        object = (MenuView.ItemView)((Object)view);
        if (this.mForceShowIcon) {
            ((ListMenuItemView)view).setForceShowIcon(true);
        }
        object.initialize(this.getItem(n), 0);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        this.findExpandedIndex();
        super.notifyDataSetChanged();
    }

    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
    }
}

