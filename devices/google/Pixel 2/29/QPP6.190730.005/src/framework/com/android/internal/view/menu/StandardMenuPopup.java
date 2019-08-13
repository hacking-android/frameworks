/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MenuPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.util.Preconditions;
import com.android.internal.view.menu.MenuAdapter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPopup;
import com.android.internal.view.menu.MenuPopupHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.SubMenuBuilder;

final class StandardMenuPopup
extends MenuPopup
implements PopupWindow.OnDismissListener,
AdapterView.OnItemClickListener,
MenuPresenter,
View.OnKeyListener {
    private static final int ITEM_LAYOUT = 17367223;
    private final MenuAdapter mAdapter;
    private View mAnchorView;
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            if (StandardMenuPopup.this.mTreeObserver != null) {
                if (!StandardMenuPopup.this.mTreeObserver.isAlive()) {
                    StandardMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                StandardMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(StandardMenuPopup.this.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private int mContentWidth;
    private final Context mContext;
    private int mDropDownGravity = 0;
    private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        @Override
        public void onGlobalLayout() {
            if (StandardMenuPopup.this.isShowing() && !StandardMenuPopup.this.mPopup.isModal()) {
                View view = StandardMenuPopup.this.mShownAnchorView;
                if (view != null && view.isShown()) {
                    StandardMenuPopup.this.mPopup.show();
                } else {
                    StandardMenuPopup.this.dismiss();
                }
            }
        }
    };
    private boolean mHasContentWidth;
    private final MenuBuilder mMenu;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final MenuPopupWindow mPopup;
    private final int mPopupMaxWidth;
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    private boolean mShowTitle;
    private View mShownAnchorView;
    private ViewTreeObserver mTreeObserver;
    private boolean mWasDismissed;

    public StandardMenuPopup(Context context, MenuBuilder menuBuilder, View view, int n, int n2, boolean bl) {
        this.mContext = Preconditions.checkNotNull(context);
        this.mMenu = menuBuilder;
        this.mOverflowOnly = bl;
        this.mAdapter = new MenuAdapter(menuBuilder, LayoutInflater.from(context), this.mOverflowOnly, 17367223);
        this.mPopupStyleAttr = n;
        this.mPopupStyleRes = n2;
        Resources resources = context.getResources();
        this.mPopupMaxWidth = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(17105070));
        this.mAnchorView = view;
        this.mPopup = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuBuilder.addMenuPresenter(this, context);
    }

    private boolean tryShow() {
        View view;
        if (this.isShowing()) {
            return true;
        }
        if (!this.mWasDismissed && (view = this.mAnchorView) != null) {
            this.mShownAnchorView = view;
            this.mPopup.setOnDismissListener(this);
            this.mPopup.setOnItemClickListener(this);
            this.mPopup.setAdapter(this.mAdapter);
            this.mPopup.setModal(true);
            view = this.mShownAnchorView;
            boolean bl = this.mTreeObserver == null;
            this.mTreeObserver = view.getViewTreeObserver();
            if (bl) {
                this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
            }
            view.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
            this.mPopup.setAnchorView(view);
            this.mPopup.setDropDownGravity(this.mDropDownGravity);
            if (!this.mHasContentWidth) {
                this.mContentWidth = StandardMenuPopup.measureIndividualMenuWidth(this.mAdapter, null, this.mContext, this.mPopupMaxWidth);
                this.mHasContentWidth = true;
            }
            this.mPopup.setContentWidth(this.mContentWidth);
            this.mPopup.setInputMethodMode(2);
            this.mPopup.setEpicenterBounds(this.getEpicenterBounds());
            this.mPopup.show();
            view = this.mPopup.getListView();
            view.setOnKeyListener(this);
            if (this.mShowTitle && this.mMenu.getHeaderTitle() != null) {
                FrameLayout frameLayout = (FrameLayout)LayoutInflater.from(this.mContext).inflate(17367222, (ViewGroup)view, false);
                TextView textView = (TextView)frameLayout.findViewById(16908310);
                if (textView != null) {
                    textView.setText(this.mMenu.getHeaderTitle());
                }
                frameLayout.setEnabled(false);
                ((ListView)view).addHeaderView(frameLayout, null, false);
                this.mPopup.show();
            }
            return true;
        }
        return false;
    }

    @Override
    public void addMenu(MenuBuilder menuBuilder) {
    }

    @Override
    public void dismiss() {
        if (this.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    @Override
    public ListView getListView() {
        return this.mPopup.getListView();
    }

    @Override
    public boolean isShowing() {
        boolean bl = !this.mWasDismissed && this.mPopup.isShowing();
        return bl;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        if (menuBuilder != this.mMenu) {
            return;
        }
        this.dismiss();
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, bl);
        }
    }

    @Override
    public void onDismiss() {
        this.mWasDismissed = true;
        this.mMenu.close();
        Object object = this.mTreeObserver;
        if (object != null) {
            if (!((ViewTreeObserver)object).isAlive()) {
                this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            }
            this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            this.mTreeObserver = null;
        }
        this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        object = this.mOnDismissListener;
        if (object != null) {
            object.onDismiss();
        }
    }

    @Override
    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && n == 82) {
            this.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return null;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (subMenuBuilder.hasVisibleItems()) {
            Object object = new MenuPopupHelper(this.mContext, subMenuBuilder, this.mShownAnchorView, this.mOverflowOnly, this.mPopupStyleAttr, this.mPopupStyleRes);
            ((MenuPopupHelper)object).setPresenterCallback(this.mPresenterCallback);
            ((MenuPopupHelper)object).setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(subMenuBuilder));
            ((MenuPopupHelper)object).setOnDismissListener(this.mOnDismissListener);
            this.mOnDismissListener = null;
            this.mMenu.close(false);
            int n = this.mPopup.getHorizontalOffset();
            int n2 = this.mPopup.getVerticalOffset();
            int n3 = n;
            if ((Gravity.getAbsoluteGravity(this.mDropDownGravity, this.mAnchorView.getLayoutDirection()) & 7) == 5) {
                n3 = n + this.mAnchorView.getWidth();
            }
            if (((MenuPopupHelper)object).tryShow(n3, n2)) {
                object = this.mPresenterCallback;
                if (object != null) {
                    object.onOpenSubMenu(subMenuBuilder);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void setAnchorView(View view) {
        this.mAnchorView = view;
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override
    public void setForceShowIcon(boolean bl) {
        this.mAdapter.setForceShowIcon(bl);
    }

    @Override
    public void setGravity(int n) {
        this.mDropDownGravity = n;
    }

    @Override
    public void setHorizontalOffset(int n) {
        this.mPopup.setHorizontalOffset(n);
    }

    @Override
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    @Override
    public void setShowTitle(boolean bl) {
        this.mShowTitle = bl;
    }

    @Override
    public void setVerticalOffset(int n) {
        this.mPopup.setVerticalOffset(n);
    }

    @Override
    public void show() {
        if (this.tryShow()) {
            return;
        }
        throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
    }

    @Override
    public void updateMenuView(boolean bl) {
        this.mHasContentWidth = false;
        MenuAdapter menuAdapter = this.mAdapter;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

}

