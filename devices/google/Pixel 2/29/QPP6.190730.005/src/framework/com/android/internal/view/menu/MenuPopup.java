/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import com.android.internal.view.menu.MenuAdapter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.ShowableListMenu;

public abstract class MenuPopup
implements ShowableListMenu,
MenuPresenter,
AdapterView.OnItemClickListener {
    private Rect mEpicenterBounds;

    protected static int measureIndividualMenuWidth(ListAdapter listAdapter, ViewGroup view, Context context, int n) {
        int n2 = 0;
        ViewGroup viewGroup = null;
        int n3 = 0;
        int n4 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int n5 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int n6 = listAdapter.getCount();
        ViewGroup viewGroup2 = view;
        view = viewGroup;
        for (int i = 0; i < n6; ++i) {
            int n7 = listAdapter.getItemViewType(i);
            int n8 = n3;
            if (n7 != n3) {
                n8 = n7;
                view = null;
            }
            viewGroup = viewGroup2;
            if (viewGroup2 == null) {
                viewGroup = new FrameLayout(context);
            }
            view = listAdapter.getView(i, view, viewGroup);
            view.measure(n4, n5);
            n7 = view.getMeasuredWidth();
            if (n7 >= n) {
                return n;
            }
            n3 = n2;
            if (n7 > n2) {
                n3 = n7;
            }
            n2 = n3;
            n3 = n8;
            viewGroup2 = viewGroup;
        }
        return n2;
    }

    protected static boolean shouldPreserveIconSpacing(MenuBuilder menuBuilder) {
        boolean bl;
        boolean bl2 = false;
        int n = menuBuilder.size();
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            MenuItem menuItem = menuBuilder.getItem(n2);
            if (menuItem.isVisible() && menuItem.getIcon() != null) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        return bl;
    }

    protected static MenuAdapter toMenuAdapter(ListAdapter listAdapter) {
        if (listAdapter instanceof HeaderViewListAdapter) {
            return (MenuAdapter)((HeaderViewListAdapter)listAdapter).getWrappedAdapter();
        }
        return (MenuAdapter)listAdapter;
    }

    public abstract void addMenu(MenuBuilder var1);

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public Rect getEpicenterBounds() {
        return this.mEpicenterBounds;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("MenuPopups manage their own views");
    }

    @Override
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
    }

    @Override
    public void onItemClick(AdapterView<?> object, View view, int n, long l) {
        object = (ListAdapter)((AdapterView)object).getAdapter();
        MenuPopup.toMenuAdapter((ListAdapter)object).mAdapterMenu.performItemAction((MenuItem)object.getItem(n), 0);
    }

    public abstract void setAnchorView(View var1);

    public void setEpicenterBounds(Rect rect) {
        this.mEpicenterBounds = rect;
    }

    public abstract void setForceShowIcon(boolean var1);

    public abstract void setGravity(int var1);

    public abstract void setHorizontalOffset(int var1);

    public abstract void setOnDismissListener(PopupWindow.OnDismissListener var1);

    public abstract void setShowTitle(boolean var1);

    public abstract void setVerticalOffset(int var1);
}

