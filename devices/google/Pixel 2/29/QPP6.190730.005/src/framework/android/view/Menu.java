/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;

public interface Menu {
    public static final int CATEGORY_ALTERNATIVE = 262144;
    public static final int CATEGORY_CONTAINER = 65536;
    public static final int CATEGORY_MASK = -65536;
    public static final int CATEGORY_SECONDARY = 196608;
    public static final int CATEGORY_SHIFT = 16;
    public static final int CATEGORY_SYSTEM = 131072;
    public static final int FIRST = 1;
    public static final int FLAG_ALWAYS_PERFORM_CLOSE = 2;
    public static final int FLAG_APPEND_TO_GROUP = 1;
    public static final int FLAG_PERFORM_NO_CLOSE = 1;
    public static final int NONE = 0;
    public static final int SUPPORTED_MODIFIERS_MASK = 69647;
    public static final int USER_MASK = 65535;
    public static final int USER_SHIFT = 0;

    public MenuItem add(int var1);

    public MenuItem add(int var1, int var2, int var3, int var4);

    public MenuItem add(int var1, int var2, int var3, CharSequence var4);

    public MenuItem add(CharSequence var1);

    public int addIntentOptions(int var1, int var2, int var3, ComponentName var4, Intent[] var5, Intent var6, int var7, MenuItem[] var8);

    public SubMenu addSubMenu(int var1);

    public SubMenu addSubMenu(int var1, int var2, int var3, int var4);

    public SubMenu addSubMenu(int var1, int var2, int var3, CharSequence var4);

    public SubMenu addSubMenu(CharSequence var1);

    public void clear();

    public void close();

    public MenuItem findItem(int var1);

    public MenuItem getItem(int var1);

    public boolean hasVisibleItems();

    public boolean isShortcutKey(int var1, KeyEvent var2);

    public boolean performIdentifierAction(int var1, int var2);

    public boolean performShortcut(int var1, KeyEvent var2, int var3);

    public void removeGroup(int var1);

    public void removeItem(int var1);

    public void setGroupCheckable(int var1, boolean var2, boolean var3);

    default public void setGroupDividerEnabled(boolean bl) {
    }

    public void setGroupEnabled(int var1, boolean var2);

    public void setGroupVisible(int var1, boolean var2);

    public void setQwertyMode(boolean var1);

    public int size();
}

