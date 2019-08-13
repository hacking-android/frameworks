/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import com.android.internal.view.menu.ActionMenuItem;
import java.util.ArrayList;
import java.util.List;

public class ActionMenu
implements Menu {
    private Context mContext;
    private boolean mIsQwerty;
    private ArrayList<ActionMenuItem> mItems;

    @UnsupportedAppUsage
    public ActionMenu(Context context) {
        this.mContext = context;
        this.mItems = new ArrayList();
    }

    private int findItemIndex(int n) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            if (arrayList.get(i).getItemId() != n) continue;
            return i;
        }
        return -1;
    }

    private ActionMenuItem findItemWithShortcut(int n, KeyEvent object) {
        boolean bl = this.mIsQwerty;
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n2 = arrayList.size();
        int n3 = ((KeyEvent)object).getModifiers();
        for (int i = 0; i < n2; ++i) {
            object = arrayList.get(i);
            char c = bl ? ((ActionMenuItem)object).getAlphabeticShortcut() : ((ActionMenuItem)object).getNumericShortcut();
            int n4 = bl ? ((ActionMenuItem)object).getAlphabeticModifiers() : ((ActionMenuItem)object).getNumericModifiers();
            n4 = (n3 & 69647) == (69647 & n4) ? 1 : 0;
            if (n != c || n4 == 0) continue;
            return object;
        }
        return null;
    }

    @Override
    public MenuItem add(int n) {
        return this.add(0, 0, 0, n);
    }

    @Override
    public MenuItem add(int n, int n2, int n3, int n4) {
        return this.add(n, n2, n3, this.mContext.getResources().getString(n4));
    }

    @Override
    public MenuItem add(int n, int n2, int n3, CharSequence object) {
        object = new ActionMenuItem(this.getContext(), n, n2, 0, n3, (CharSequence)object);
        this.mItems.add(n3, (ActionMenuItem)object);
        return object;
    }

    @Override
    public MenuItem add(CharSequence charSequence) {
        return this.add(0, 0, 0, charSequence);
    }

    @Override
    public int addIntentOptions(int n, int n2, int n3, ComponentName object, Intent[] arrintent, Intent intent, int n4, MenuItem[] arrmenuItem) {
        PackageManager packageManager = this.mContext.getPackageManager();
        int n5 = 0;
        List<ResolveInfo> list = packageManager.queryIntentActivityOptions((ComponentName)object, arrintent, intent, 0);
        if (list != null) {
            n5 = list.size();
        }
        if ((n4 & 1) == 0) {
            this.removeGroup(n);
        }
        for (n4 = 0; n4 < n5; ++n4) {
            ResolveInfo resolveInfo = list.get(n4);
            object = resolveInfo.specificIndex < 0 ? intent : arrintent[resolveInfo.specificIndex];
            object = new Intent((Intent)object);
            ((Intent)object).setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            object = this.add(n, n2, n3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent((Intent)object);
            if (arrmenuItem == null || resolveInfo.specificIndex < 0) continue;
            arrmenuItem[resolveInfo.specificIndex] = object;
        }
        return n5;
    }

    @Override
    public SubMenu addSubMenu(int n) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int n, int n2, int n3, int n4) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(int n, int n2, int n3, CharSequence charSequence) {
        return null;
    }

    @Override
    public SubMenu addSubMenu(CharSequence charSequence) {
        return null;
    }

    @Override
    public void clear() {
        this.mItems.clear();
    }

    @Override
    public void close() {
    }

    @Override
    public MenuItem findItem(int n) {
        return this.mItems.get(this.findItemIndex(n));
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public MenuItem getItem(int n) {
        return this.mItems.get(n);
    }

    @Override
    public boolean hasVisibleItems() {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (!arrayList.get(i).isVisible()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isShortcutKey(int n, KeyEvent keyEvent) {
        boolean bl = this.findItemWithShortcut(n, keyEvent) != null;
        return bl;
    }

    @Override
    public boolean performIdentifierAction(int n, int n2) {
        if ((n = this.findItemIndex(n)) < 0) {
            return false;
        }
        return this.mItems.get(n).invoke();
    }

    @Override
    public boolean performShortcut(int n, KeyEvent object, int n2) {
        if ((object = this.findItemWithShortcut(n, (KeyEvent)object)) == null) {
            return false;
        }
        return ((ActionMenuItem)object).invoke();
    }

    @Override
    public void removeGroup(int n) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n2 = arrayList.size();
        int n3 = 0;
        while (n3 < n2) {
            if (arrayList.get(n3).getGroupId() == n) {
                arrayList.remove(n3);
                --n2;
                continue;
            }
            ++n3;
        }
    }

    @Override
    public void removeItem(int n) {
        this.mItems.remove(this.findItemIndex(n));
    }

    @Override
    public void setGroupCheckable(int n, boolean bl, boolean bl2) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            ActionMenuItem actionMenuItem = arrayList.get(i);
            if (actionMenuItem.getGroupId() != n) continue;
            actionMenuItem.setCheckable(bl);
            actionMenuItem.setExclusiveCheckable(bl2);
        }
    }

    @Override
    public void setGroupEnabled(int n, boolean bl) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            ActionMenuItem actionMenuItem = arrayList.get(i);
            if (actionMenuItem.getGroupId() != n) continue;
            actionMenuItem.setEnabled(bl);
        }
    }

    @Override
    public void setGroupVisible(int n, boolean bl) {
        ArrayList<ActionMenuItem> arrayList = this.mItems;
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            ActionMenuItem actionMenuItem = arrayList.get(i);
            if (actionMenuItem.getGroupId() != n) continue;
            actionMenuItem.setVisible(bl);
        }
    }

    @Override
    public void setQwertyMode(boolean bl) {
        this.mIsQwerty = bl;
    }

    @Override
    public int size() {
        return this.mItems.size();
    }
}

