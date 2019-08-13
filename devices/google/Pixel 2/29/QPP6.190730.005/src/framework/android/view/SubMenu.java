/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public interface SubMenu
extends Menu {
    public void clearHeader();

    public MenuItem getItem();

    public SubMenu setHeaderIcon(int var1);

    public SubMenu setHeaderIcon(Drawable var1);

    public SubMenu setHeaderTitle(int var1);

    public SubMenu setHeaderTitle(CharSequence var1);

    public SubMenu setHeaderView(View var1);

    public SubMenu setIcon(int var1);

    public SubMenu setIcon(Drawable var1);
}

