/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.EventLog;
import android.view.ContextMenu;
import android.view.View;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPopupHelper;
import java.util.ArrayList;

public class ContextMenuBuilder
extends MenuBuilder
implements ContextMenu {
    @UnsupportedAppUsage
    public ContextMenuBuilder(Context context) {
        super(context);
    }

    @Override
    public ContextMenu setHeaderIcon(int n) {
        return (ContextMenu)((Object)super.setHeaderIconInt(n));
    }

    @Override
    public ContextMenu setHeaderIcon(Drawable drawable2) {
        return (ContextMenu)((Object)super.setHeaderIconInt(drawable2));
    }

    @Override
    public ContextMenu setHeaderTitle(int n) {
        return (ContextMenu)((Object)super.setHeaderTitleInt(n));
    }

    @Override
    public ContextMenu setHeaderTitle(CharSequence charSequence) {
        return (ContextMenu)((Object)super.setHeaderTitleInt(charSequence));
    }

    @Override
    public ContextMenu setHeaderView(View view) {
        return (ContextMenu)((Object)super.setHeaderViewInt(view));
    }

    public MenuDialogHelper showDialog(View object, IBinder iBinder) {
        if (object != null) {
            ((View)object).createContextMenu(this);
        }
        if (this.getVisibleItems().size() > 0) {
            EventLog.writeEvent(50001, 1);
            object = new MenuDialogHelper(this);
            ((MenuDialogHelper)object).show(iBinder);
            return object;
        }
        return null;
    }

    public MenuPopupHelper showPopup(Context object, View view, float f, float f2) {
        if (view != null) {
            view.createContextMenu(this);
        }
        if (this.getVisibleItems().size() > 0) {
            EventLog.writeEvent(50001, 1);
            view.getLocationOnScreen(new int[2]);
            object = new MenuPopupHelper((Context)object, this, view, false, 16844033);
            ((MenuPopupHelper)object).show(Math.round(f), Math.round(f2));
            return object;
        }
        return null;
    }
}

