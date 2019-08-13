/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.Window;
import com.android.internal.view.menu.MenuPresenter;

public interface DecorContentParent {
    public boolean canShowOverflowMenu();

    public void dismissPopups();

    public CharSequence getTitle();

    public boolean hasIcon();

    public boolean hasLogo();

    public boolean hideOverflowMenu();

    public void initFeature(int var1);

    public boolean isOverflowMenuShowPending();

    public boolean isOverflowMenuShowing();

    public void restoreToolbarHierarchyState(SparseArray<Parcelable> var1);

    public void saveToolbarHierarchyState(SparseArray<Parcelable> var1);

    public void setIcon(int var1);

    public void setIcon(Drawable var1);

    public void setLogo(int var1);

    public void setMenu(Menu var1, MenuPresenter.Callback var2);

    public void setMenuPrepared();

    public void setUiOptions(int var1);

    public void setWindowCallback(Window.Callback var1);

    public void setWindowTitle(CharSequence var1);

    public boolean showOverflowMenu();
}

