/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;

public final class ExpandedMenuView
extends ListView
implements MenuBuilder.ItemInvoker,
MenuView,
AdapterView.OnItemClickListener {
    private int mAnimations;
    private MenuBuilder mMenu;

    public ExpandedMenuView(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.MenuView, 0, 0);
        this.mAnimations = ((TypedArray)object).getResourceId(0, 0);
        ((TypedArray)object).recycle();
        this.setOnItemClickListener(this);
    }

    @Override
    public int getWindowAnimations() {
        return this.mAnimations;
    }

    @Override
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.setChildrenDrawingCacheEnabled(false);
    }

    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.invokeItem((MenuItemImpl)this.getAdapter().getItem(n));
    }
}

