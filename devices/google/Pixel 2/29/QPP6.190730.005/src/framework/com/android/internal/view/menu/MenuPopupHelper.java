/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.android.internal.view.menu.CascadingMenuPopup;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuPopup;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.StandardMenuPopup;

public class MenuPopupHelper
implements MenuHelper {
    private static final int TOUCH_EPICENTER_SIZE_DP = 48;
    private View mAnchorView;
    private final Context mContext;
    private int mDropDownGravity = 8388611;
    @UnsupportedAppUsage
    private boolean mForceShowIcon;
    private final PopupWindow.OnDismissListener mInternalOnDismissListener = new PopupWindow.OnDismissListener(){

        @Override
        public void onDismiss() {
            MenuPopupHelper.this.onDismiss();
        }
    };
    private final MenuBuilder mMenu;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private MenuPopup mPopup;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;

    @UnsupportedAppUsage
    public MenuPopupHelper(Context context, MenuBuilder menuBuilder) {
        this(context, menuBuilder, null, false, 16843520, 0);
    }

    @UnsupportedAppUsage
    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view) {
        this(context, menuBuilder, view, false, 16843520, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean bl, int n) {
        this(context, menuBuilder, view, bl, n, 0);
    }

    public MenuPopupHelper(Context context, MenuBuilder menuBuilder, View view, boolean bl, int n, int n2) {
        this.mContext = context;
        this.mMenu = menuBuilder;
        this.mAnchorView = view;
        this.mOverflowOnly = bl;
        this.mPopupStyleAttr = n;
        this.mPopupStyleRes = n2;
    }

    private MenuPopup createPopup() {
        Display display = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
        Object object = new Point();
        display.getRealSize((Point)object);
        boolean bl = Math.min(((Point)object).x, ((Point)object).y) >= this.mContext.getResources().getDimensionPixelSize(17105031);
        object = bl ? new CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly) : new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
        ((MenuPopup)object).addMenu(this.mMenu);
        ((MenuPopup)object).setOnDismissListener(this.mInternalOnDismissListener);
        ((MenuPopup)object).setAnchorView(this.mAnchorView);
        object.setCallback(this.mPresenterCallback);
        ((MenuPopup)object).setForceShowIcon(this.mForceShowIcon);
        ((MenuPopup)object).setGravity(this.mDropDownGravity);
        return object;
    }

    private void showPopup(int n, int n2, boolean bl, boolean bl2) {
        MenuPopup menuPopup = this.getPopup();
        menuPopup.setShowTitle(bl2);
        if (bl) {
            int n3 = n;
            if ((Gravity.getAbsoluteGravity(this.mDropDownGravity, this.mAnchorView.getLayoutDirection()) & 7) == 5) {
                n3 = n - this.mAnchorView.getWidth();
            }
            menuPopup.setHorizontalOffset(n3);
            menuPopup.setVerticalOffset(n2);
            n = (int)(48.0f * this.mContext.getResources().getDisplayMetrics().density / 2.0f);
            menuPopup.setEpicenterBounds(new Rect(n3 - n, n2 - n, n3 + n, n2 + n));
        }
        menuPopup.show();
    }

    @UnsupportedAppUsage
    @Override
    public void dismiss() {
        if (this.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public int getGravity() {
        return this.mDropDownGravity;
    }

    @UnsupportedAppUsage
    public MenuPopup getPopup() {
        if (this.mPopup == null) {
            this.mPopup = this.createPopup();
        }
        return this.mPopup;
    }

    public boolean isShowing() {
        MenuPopup menuPopup = this.mPopup;
        boolean bl = menuPopup != null && menuPopup.isShowing();
        return bl;
    }

    protected void onDismiss() {
        this.mPopup = null;
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @UnsupportedAppUsage
    public void setAnchorView(View view) {
        this.mAnchorView = view;
    }

    @UnsupportedAppUsage
    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup != null) {
            menuPopup.setForceShowIcon(bl);
        }
    }

    @UnsupportedAppUsage
    public void setGravity(int n) {
        this.mDropDownGravity = n;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override
    public void setPresenterCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
        MenuPopup menuPopup = this.mPopup;
        if (menuPopup != null) {
            menuPopup.setCallback(callback);
        }
    }

    @UnsupportedAppUsage
    public void show() {
        if (this.tryShow()) {
            return;
        }
        throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }

    public void show(int n, int n2) {
        if (this.tryShow(n, n2)) {
            return;
        }
        throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }

    @UnsupportedAppUsage
    public boolean tryShow() {
        if (this.isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        this.showPopup(0, 0, false, false);
        return true;
    }

    public boolean tryShow(int n, int n2) {
        if (this.isShowing()) {
            return true;
        }
        if (this.mAnchorView == null) {
            return false;
        }
        this.showPopup(n, n2, true, true);
        return true;
    }

}

