/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import com.android.internal.view.menu.ExpandedMenuView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;
import java.util.ArrayList;

public class ListMenuPresenter
implements MenuPresenter,
AdapterView.OnItemClickListener {
    private static final String TAG = "ListMenuPresenter";
    public static final String VIEWS_TAG = "android:menu:list";
    MenuAdapter mAdapter;
    private MenuPresenter.Callback mCallback;
    Context mContext;
    private int mId;
    LayoutInflater mInflater;
    private int mItemIndexOffset;
    int mItemLayoutRes;
    MenuBuilder mMenu;
    ExpandedMenuView mMenuView;
    int mThemeRes;

    public ListMenuPresenter(int n, int n2) {
        this.mItemLayoutRes = n;
        this.mThemeRes = n2;
    }

    public ListMenuPresenter(Context context, int n) {
        this(n, 0);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    public ListAdapter getAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new MenuAdapter();
        }
        return this.mAdapter;
    }

    @Override
    public int getId() {
        return this.mId;
    }

    int getItemIndexOffset() {
        return this.mItemIndexOffset;
    }

    @Override
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (ExpandedMenuView)this.mInflater.inflate(17367148, viewGroup, false);
            if (this.mAdapter == null) {
                this.mAdapter = new MenuAdapter();
            }
            this.mMenuView.setAdapter(this.mAdapter);
            this.mMenuView.setOnItemClickListener(this);
        }
        return this.mMenuView;
    }

    @Override
    public void initForMenu(Context object, MenuBuilder menuBuilder) {
        int n = this.mThemeRes;
        if (n != 0) {
            this.mContext = new ContextThemeWrapper((Context)object, n);
            this.mInflater = LayoutInflater.from(this.mContext);
        } else if (this.mContext != null) {
            this.mContext = object;
            if (this.mInflater == null) {
                this.mInflater = LayoutInflater.from(this.mContext);
            }
        }
        this.mMenu = menuBuilder;
        object = this.mAdapter;
        if (object != null) {
            ((MenuAdapter)object).notifyDataSetChanged();
        }
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        MenuPresenter.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, bl);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        this.mMenu.performItemAction(this.mAdapter.getItem(n), this, 0);
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
        return bundle;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        new MenuDialogHelper(subMenuBuilder).show(null);
        MenuPresenter.Callback callback = this.mCallback;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    public void restoreHierarchyState(Bundle cloneable) {
        if ((cloneable = cloneable.getSparseParcelableArray(VIEWS_TAG)) != null) {
            this.mMenuView.restoreHierarchyState((SparseArray<Parcelable>)cloneable);
        }
    }

    public void saveHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = new SparseArray<Parcelable>();
        ExpandedMenuView expandedMenuView = this.mMenuView;
        if (expandedMenuView != null) {
            expandedMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mCallback = callback;
    }

    public void setId(int n) {
        this.mId = n;
    }

    public void setItemIndexOffset(int n) {
        this.mItemIndexOffset = n;
        if (this.mMenuView != null) {
            this.updateMenuView(false);
        }
    }

    @Override
    public void updateMenuView(boolean bl) {
        MenuAdapter menuAdapter = this.mAdapter;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    private class MenuAdapter
    extends BaseAdapter {
        private int mExpandedIndex = -1;

        public MenuAdapter() {
            this.findExpandedIndex();
        }

        void findExpandedIndex() {
            MenuItemImpl menuItemImpl = ListMenuPresenter.this.mMenu.getExpandedItem();
            if (menuItemImpl != null) {
                ArrayList<MenuItemImpl> arrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    if (arrayList.get(i) != menuItemImpl) continue;
                    this.mExpandedIndex = i;
                    return;
                }
            }
            this.mExpandedIndex = -1;
        }

        @Override
        public int getCount() {
            int n = ListMenuPresenter.this.mMenu.getNonActionItems().size() - ListMenuPresenter.this.mItemIndexOffset;
            if (this.mExpandedIndex < 0) {
                return n;
            }
            return n - 1;
        }

        @Override
        public MenuItemImpl getItem(int n) {
            ArrayList<MenuItemImpl> arrayList = ListMenuPresenter.this.mMenu.getNonActionItems();
            int n2 = n + ListMenuPresenter.this.mItemIndexOffset;
            int n3 = this.mExpandedIndex;
            n = n2;
            if (n3 >= 0) {
                n = n2;
                if (n2 >= n3) {
                    n = n2 + 1;
                }
            }
            return arrayList.get(n);
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            View view2 = view;
            if (view == null) {
                view2 = ListMenuPresenter.this.mInflater.inflate(ListMenuPresenter.this.mItemLayoutRes, viewGroup, false);
            }
            ((MenuView.ItemView)((Object)view2)).initialize(this.getItem(n), 0);
            return view2;
        }

        @Override
        public void notifyDataSetChanged() {
            this.findExpandedIndex();
            super.notifyDataSetChanged();
        }
    }

}

