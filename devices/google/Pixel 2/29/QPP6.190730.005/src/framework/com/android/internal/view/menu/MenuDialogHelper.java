/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import com.android.internal.view.menu.ListMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;

public class MenuDialogHelper
implements MenuHelper,
DialogInterface.OnKeyListener,
DialogInterface.OnClickListener,
DialogInterface.OnDismissListener,
MenuPresenter.Callback {
    private AlertDialog mDialog;
    private MenuBuilder mMenu;
    ListMenuPresenter mPresenter;
    private MenuPresenter.Callback mPresenterCallback;

    @UnsupportedAppUsage
    public MenuDialogHelper(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @UnsupportedAppUsage
    @Override
    public void dismiss() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int n) {
        this.mMenu.performItemAction((MenuItemImpl)this.mPresenter.getAdapter().getItem(n), 0);
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        MenuPresenter.Callback callback;
        if (bl || menuBuilder == this.mMenu) {
            this.dismiss();
        }
        if ((callback = this.mPresenterCallback) != null) {
            callback.onCloseMenu(menuBuilder, bl);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        this.mPresenter.onCloseMenu(this.mMenu, true);
    }

    @Override
    public boolean onKey(DialogInterface object, int n, KeyEvent keyEvent) {
        if (n == 82 || n == 4) {
            Object object2;
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                object = this.mDialog.getWindow();
                if (object != null && (object = ((Window)object).getDecorView()) != null && (object = ((View)object).getKeyDispatcherState()) != null) {
                    ((KeyEvent.DispatcherState)object).startTracking(keyEvent, this);
                    return true;
                }
            } else if (keyEvent.getAction() == 1 && !keyEvent.isCanceled() && (object2 = this.mDialog.getWindow()) != null && (object2 = ((Window)object2).getDecorView()) != null && (object2 = ((View)object2).getKeyDispatcherState()) != null && ((KeyEvent.DispatcherState)object2).isTracking(keyEvent)) {
                this.mMenu.close(true);
                object.dismiss();
                return true;
            }
        }
        return this.mMenu.performShortcut(n, keyEvent, 0);
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            return callback.onOpenSubMenu(menuBuilder);
        }
        return false;
    }

    @Override
    public void setPresenterCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @UnsupportedAppUsage
    public void show(IBinder iBinder) {
        MenuBuilder menuBuilder = this.mMenu;
        AlertDialog.Builder builder = new AlertDialog.Builder(menuBuilder.getContext());
        this.mPresenter = new ListMenuPresenter(builder.getContext(), 17367181);
        this.mPresenter.setCallback(this);
        this.mMenu.addMenuPresenter(this.mPresenter);
        builder.setAdapter(this.mPresenter.getAdapter(), this);
        Object object = menuBuilder.getHeaderView();
        if (object != null) {
            builder.setCustomTitle((View)object);
        } else {
            builder.setIcon(menuBuilder.getHeaderIcon()).setTitle(menuBuilder.getHeaderTitle());
        }
        builder.setOnKeyListener(this);
        this.mDialog = builder.create();
        this.mDialog.setOnDismissListener(this);
        object = this.mDialog.getWindow().getAttributes();
        ((WindowManager.LayoutParams)object).type = 1003;
        if (iBinder != null) {
            ((WindowManager.LayoutParams)object).token = iBinder;
        }
        ((WindowManager.LayoutParams)object).flags |= 131072;
        this.mDialog.show();
    }
}

