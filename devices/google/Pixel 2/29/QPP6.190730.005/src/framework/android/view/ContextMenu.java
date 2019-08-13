/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

public interface ContextMenu
extends Menu {
    public void clearHeader();

    public ContextMenu setHeaderIcon(int var1);

    public ContextMenu setHeaderIcon(Drawable var1);

    public ContextMenu setHeaderTitle(int var1);

    public ContextMenu setHeaderTitle(CharSequence var1);

    public ContextMenu setHeaderView(View var1);

    public static interface ContextMenuInfo {
    }

}

