/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.graphics.drawable.Drawable;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;

public interface MenuView {
    @UnsupportedAppUsage
    public int getWindowAnimations();

    public void initialize(MenuBuilder var1);

    public static interface ItemView {
        @UnsupportedAppUsage
        public MenuItemImpl getItemData();

        public void initialize(MenuItemImpl var1, int var2);

        public boolean prefersCondensedTitle();

        public void setCheckable(boolean var1);

        public void setChecked(boolean var1);

        public void setEnabled(boolean var1);

        public void setIcon(Drawable var1);

        public void setShortcut(boolean var1, char var2);

        public void setTitle(CharSequence var1);

        public boolean showsIcon();
    }

}

