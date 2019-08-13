/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MenuItemHoverListener;
import android.widget.MenuPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.util.Preconditions;
import com.android.internal.view.menu.MenuAdapter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPopup;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.SubMenuBuilder;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

final class CascadingMenuPopup
extends MenuPopup
implements MenuPresenter,
View.OnKeyListener,
PopupWindow.OnDismissListener {
    private static final int HORIZ_POSITION_LEFT = 0;
    private static final int HORIZ_POSITION_RIGHT = 1;
    private static final int ITEM_LAYOUT = 17367113;
    private static final int SUBMENU_TIMEOUT_MS = 200;
    private View mAnchorView;
    private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            if (CascadingMenuPopup.this.mTreeObserver != null) {
                if (!CascadingMenuPopup.this.mTreeObserver.isAlive()) {
                    CascadingMenuPopup.this.mTreeObserver = view.getViewTreeObserver();
                }
                CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private final Context mContext;
    private int mDropDownGravity = 0;
    private boolean mForceShowIcon;
    private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        @Override
        public void onGlobalLayout() {
            if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.mShowingMenus.size() > 0 && !((CascadingMenuInfo)CascadingMenuPopup.access$000((CascadingMenuPopup)CascadingMenuPopup.this).get((int)0)).window.isModal()) {
                Object object = CascadingMenuPopup.this.mShownAnchorView;
                if (object != null && ((View)object).isShown()) {
                    object = CascadingMenuPopup.this.mShowingMenus.iterator();
                    while (object.hasNext()) {
                        ((CascadingMenuInfo)object.next()).window.show();
                    }
                } else {
                    CascadingMenuPopup.this.dismiss();
                }
            }
        }
    };
    private boolean mHasXOffset;
    private boolean mHasYOffset;
    private int mLastPosition;
    private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener(){

        @Override
        public void onItemHoverEnter(MenuBuilder menuBuilder, MenuItem object) {
            int n;
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(null);
            int n2 = -1;
            int n3 = 0;
            int n4 = CascadingMenuPopup.this.mShowingMenus.size();
            do {
                n = n2;
                if (n3 >= n4) break;
                if (menuBuilder == ((CascadingMenuInfo)CascadingMenuPopup.access$000((CascadingMenuPopup)CascadingMenuPopup.this).get((int)n3)).menu) {
                    n = n3;
                    break;
                }
                ++n3;
            } while (true);
            if (n == -1) {
                return;
            }
            n3 = n + 1;
            final CascadingMenuInfo cascadingMenuInfo = n3 < CascadingMenuPopup.this.mShowingMenus.size() ? (CascadingMenuInfo)CascadingMenuPopup.this.mShowingMenus.get(n3) : null;
            object = new Runnable((MenuItem)object, menuBuilder){
                final /* synthetic */ MenuItem val$item;
                final /* synthetic */ MenuBuilder val$menu;
                {
                    this.val$item = menuItem;
                    this.val$menu = menuBuilder;
                }

                @Override
                public void run() {
                    if (cascadingMenuInfo != null) {
                        CascadingMenuPopup.this.mShouldCloseImmediately = true;
                        cascadingMenuInfo.menu.close(false);
                        CascadingMenuPopup.this.mShouldCloseImmediately = false;
                    }
                    if (this.val$item.isEnabled() && this.val$item.hasSubMenu()) {
                        this.val$menu.performItemAction(this.val$item, 0);
                    }
                }
            };
            long l = SystemClock.uptimeMillis();
            CascadingMenuPopup.this.mSubMenuHoverHandler.postAtTime((Runnable)object, menuBuilder, l + 200L);
        }

        @Override
        public void onItemHoverExit(MenuBuilder menuBuilder, MenuItem menuItem) {
            CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(menuBuilder);
        }

    };
    private final int mMenuMaxWidth;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private final boolean mOverflowOnly;
    private final List<MenuBuilder> mPendingMenus = new LinkedList<MenuBuilder>();
    private final int mPopupStyleAttr;
    private final int mPopupStyleRes;
    private MenuPresenter.Callback mPresenterCallback;
    private int mRawDropDownGravity = 0;
    private boolean mShouldCloseImmediately;
    private boolean mShowTitle;
    private final List<CascadingMenuInfo> mShowingMenus = new ArrayList<CascadingMenuInfo>();
    private View mShownAnchorView;
    private final Handler mSubMenuHoverHandler;
    private ViewTreeObserver mTreeObserver;
    private int mXOffset;
    private int mYOffset;

    public CascadingMenuPopup(Context object, View view, int n, int n2, boolean bl) {
        this.mContext = Preconditions.checkNotNull(object);
        this.mAnchorView = Preconditions.checkNotNull(view);
        this.mPopupStyleAttr = n;
        this.mPopupStyleRes = n2;
        this.mOverflowOnly = bl;
        this.mForceShowIcon = false;
        this.mLastPosition = this.getInitialMenuPosition();
        object = ((Context)object).getResources();
        this.mMenuMaxWidth = Math.max(object.getDisplayMetrics().widthPixels / 2, ((Resources)object).getDimensionPixelSize(17105070));
        this.mSubMenuHoverHandler = new Handler();
    }

    private MenuPopupWindow createPopupWindow() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
        menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.mAnchorView);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    private int findIndexOfAddedMenu(MenuBuilder menuBuilder) {
        int n = this.mShowingMenus.size();
        for (int i = 0; i < n; ++i) {
            if (menuBuilder != this.mShowingMenus.get((int)i).menu) continue;
            return i;
        }
        return -1;
    }

    private MenuItem findMenuItemForSubmenu(MenuBuilder menuBuilder, MenuBuilder menuBuilder2) {
        int n = menuBuilder.size();
        for (int i = 0; i < n; ++i) {
            MenuItem menuItem = menuBuilder.getItem(i);
            if (!menuItem.hasSubMenu() || menuBuilder2 != menuItem.getSubMenu()) continue;
            return menuItem;
        }
        return null;
    }

    private View findParentViewForSubmenu(CascadingMenuInfo object, MenuBuilder object2) {
        int n;
        int n2;
        if ((object2 = this.findMenuItemForSubmenu(((CascadingMenuInfo)object).menu, (MenuBuilder)object2)) == null) {
            return null;
        }
        ListView listView = ((CascadingMenuInfo)object).getListView();
        object = listView.getAdapter();
        if (object instanceof HeaderViewListAdapter) {
            object = (HeaderViewListAdapter)object;
            n2 = ((HeaderViewListAdapter)object).getHeadersCount();
            object = (MenuAdapter)((HeaderViewListAdapter)object).getWrappedAdapter();
        } else {
            n2 = 0;
            object = (MenuAdapter)object;
        }
        int n3 = -1;
        int n4 = 0;
        int n5 = ((MenuAdapter)object).getCount();
        do {
            n = n3;
            if (n4 >= n5) break;
            if (object2 == ((MenuAdapter)object).getItem(n4)) {
                n = n4;
                break;
            }
            ++n4;
        } while (true);
        if (n == -1) {
            return null;
        }
        n4 = n + n2 - listView.getFirstVisiblePosition();
        if (n4 >= 0 && n4 < listView.getChildCount()) {
            return listView.getChildAt(n4);
        }
        return null;
    }

    private int getInitialMenuPosition() {
        int n;
        block0 : {
            int n2 = this.mAnchorView.getLayoutDirection();
            n = 1;
            if (n2 != 1) break block0;
            n = 0;
        }
        return n;
    }

    private int getNextMenuPosition(int n) {
        Object object = this.mShowingMenus;
        object = object.get(object.size() - 1).getListView();
        int[] arrn = new int[2];
        ((View)object).getLocationOnScreen(arrn);
        Rect rect = new Rect();
        this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
        if (this.mLastPosition == 1) {
            return arrn[0] + ((View)object).getWidth() + n <= rect.right;
        }
        return arrn[0] - n < 0;
    }

    private void showMenu(MenuBuilder menuBuilder) {
        Object object;
        Object object2 = LayoutInflater.from(this.mContext);
        Object object3 = new MenuAdapter(menuBuilder, (LayoutInflater)object2, this.mOverflowOnly, 17367113);
        if (!this.isShowing() && this.mForceShowIcon) {
            ((MenuAdapter)object3).setForceShowIcon(true);
        } else if (this.isShowing()) {
            ((MenuAdapter)object3).setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(menuBuilder));
        }
        int n = CascadingMenuPopup.measureIndividualMenuWidth((ListAdapter)object3, null, this.mContext, this.mMenuMaxWidth);
        MenuPopupWindow menuPopupWindow = this.createPopupWindow();
        menuPopupWindow.setAdapter((ListAdapter)object3);
        menuPopupWindow.setContentWidth(n);
        menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
        if (this.mShowingMenus.size() > 0) {
            object3 = this.mShowingMenus;
            object3 = (CascadingMenuInfo)object3.get(object3.size() - 1);
            object = this.findParentViewForSubmenu((CascadingMenuInfo)object3, menuBuilder);
        } else {
            object3 = null;
            object = null;
        }
        if (object != null) {
            menuPopupWindow.setAnchorView((View)object);
            menuPopupWindow.setTouchModal(false);
            menuPopupWindow.setEnterTransition(null);
            int n2 = this.getNextMenuPosition(n);
            int n3 = n2 == 1 ? 1 : 0;
            this.mLastPosition = n2;
            n3 = (this.mDropDownGravity & 5) == 5 ? (n3 != 0 ? n : -((View)object).getWidth()) : (n3 != 0 ? ((View)object).getWidth() : -n);
            menuPopupWindow.setHorizontalOffset(n3);
            menuPopupWindow.setOverlapAnchor(true);
            menuPopupWindow.setVerticalOffset(0);
        } else {
            if (this.mHasXOffset) {
                menuPopupWindow.setHorizontalOffset(this.mXOffset);
            }
            if (this.mHasYOffset) {
                menuPopupWindow.setVerticalOffset(this.mYOffset);
            }
            menuPopupWindow.setEpicenterBounds(this.getEpicenterBounds());
        }
        object = new CascadingMenuInfo(menuPopupWindow, menuBuilder, this.mLastPosition);
        this.mShowingMenus.add((CascadingMenuInfo)object);
        menuPopupWindow.show();
        object = menuPopupWindow.getListView();
        ((View)object).setOnKeyListener(this);
        if (object3 == null && this.mShowTitle && menuBuilder.getHeaderTitle() != null) {
            object2 = (FrameLayout)((LayoutInflater)object2).inflate(17367222, (ViewGroup)object, false);
            object3 = (TextView)((View)object2).findViewById(16908310);
            ((View)object2).setEnabled(false);
            ((TextView)object3).setText(menuBuilder.getHeaderTitle());
            ((ListView)object).addHeaderView((View)object2, null, false);
            menuPopupWindow.show();
        }
    }

    @Override
    public void addMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.mContext);
        if (this.isShowing()) {
            this.showMenu(menuBuilder);
        } else {
            this.mPendingMenus.add(menuBuilder);
        }
    }

    @Override
    public void dismiss() {
        int n = this.mShowingMenus.size();
        if (n > 0) {
            CascadingMenuInfo[] arrcascadingMenuInfo = this.mShowingMenus.toArray(new CascadingMenuInfo[n]);
            --n;
            while (n >= 0) {
                CascadingMenuInfo cascadingMenuInfo = arrcascadingMenuInfo[n];
                if (cascadingMenuInfo.window.isShowing()) {
                    cascadingMenuInfo.window.dismiss();
                }
                --n;
            }
        }
    }

    @Override
    public boolean flagActionItems() {
        return false;
    }

    @Override
    public ListView getListView() {
        Object object;
        if (this.mShowingMenus.isEmpty()) {
            object = null;
        } else {
            object = this.mShowingMenus;
            object = object.get(object.size() - 1).getListView();
        }
        return object;
    }

    @Override
    public boolean isShowing() {
        boolean bl;
        int n = this.mShowingMenus.size();
        boolean bl2 = bl = false;
        if (n > 0) {
            bl2 = bl;
            if (this.mShowingMenus.get((int)0).window.isShowing()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @Override
    public void onCloseMenu(MenuBuilder object, boolean bl) {
        int n = this.findIndexOfAddedMenu((MenuBuilder)object);
        if (n < 0) {
            return;
        }
        int n2 = n + 1;
        if (n2 < this.mShowingMenus.size()) {
            this.mShowingMenus.get((int)n2).menu.close(false);
        }
        Object object2 = this.mShowingMenus.remove(n);
        ((CascadingMenuInfo)object2).menu.removeMenuPresenter(this);
        if (this.mShouldCloseImmediately) {
            ((CascadingMenuInfo)object2).window.setExitTransition(null);
            ((CascadingMenuInfo)object2).window.setAnimationStyle(0);
        }
        ((CascadingMenuInfo)object2).window.dismiss();
        n = this.mShowingMenus.size();
        this.mLastPosition = n > 0 ? this.mShowingMenus.get((int)(n - 1)).position : this.getInitialMenuPosition();
        if (n == 0) {
            this.dismiss();
            object2 = this.mPresenterCallback;
            if (object2 != null) {
                object2.onCloseMenu((MenuBuilder)object, true);
            }
            if ((object = this.mTreeObserver) != null) {
                if (((ViewTreeObserver)object).isAlive()) {
                    this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
                }
                this.mTreeObserver = null;
            }
            this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            this.mOnDismissListener.onDismiss();
        } else if (bl) {
            this.mShowingMenus.get((int)0).menu.close(false);
        }
    }

    @Override
    public void onDismiss() {
        CascadingMenuInfo cascadingMenuInfo;
        CascadingMenuInfo cascadingMenuInfo2 = null;
        int n = 0;
        int n2 = this.mShowingMenus.size();
        do {
            cascadingMenuInfo = cascadingMenuInfo2;
            if (n >= n2) break;
            cascadingMenuInfo = this.mShowingMenus.get(n);
            if (!cascadingMenuInfo.window.isShowing()) break;
            ++n;
        } while (true);
        if (cascadingMenuInfo != null) {
            cascadingMenuInfo.menu.close(false);
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
        for (CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
            if (subMenuBuilder != cascadingMenuInfo.menu) continue;
            cascadingMenuInfo.getListView().requestFocus();
            return true;
        }
        if (subMenuBuilder.hasVisibleItems()) {
            this.addMenu(subMenuBuilder);
            MenuPresenter.Callback callback = this.mPresenterCallback;
            if (callback != null) {
                callback.onOpenSubMenu(subMenuBuilder);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setAnchorView(View view) {
        if (this.mAnchorView != view) {
            this.mAnchorView = view;
            this.mDropDownGravity = Gravity.getAbsoluteGravity(this.mRawDropDownGravity, this.mAnchorView.getLayoutDirection());
        }
    }

    @Override
    public void setCallback(MenuPresenter.Callback callback) {
        this.mPresenterCallback = callback;
    }

    @Override
    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
    }

    @Override
    public void setGravity(int n) {
        if (this.mRawDropDownGravity != n) {
            this.mRawDropDownGravity = n;
            this.mDropDownGravity = Gravity.getAbsoluteGravity(n, this.mAnchorView.getLayoutDirection());
        }
    }

    @Override
    public void setHorizontalOffset(int n) {
        this.mHasXOffset = true;
        this.mXOffset = n;
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
        this.mHasYOffset = true;
        this.mYOffset = n;
    }

    @Override
    public void show() {
        if (this.isShowing()) {
            return;
        }
        Iterator<MenuBuilder> iterator = this.mPendingMenus.iterator();
        while (iterator.hasNext()) {
            this.showMenu(iterator.next());
        }
        this.mPendingMenus.clear();
        this.mShownAnchorView = this.mAnchorView;
        if (this.mShownAnchorView != null) {
            boolean bl = this.mTreeObserver == null;
            this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver();
            if (bl) {
                this.mTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener);
            }
            this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    @Override
    public void updateMenuView(boolean bl) {
        Iterator<CascadingMenuInfo> iterator = this.mShowingMenus.iterator();
        while (iterator.hasNext()) {
            CascadingMenuPopup.toMenuAdapter((ListAdapter)iterator.next().getListView().getAdapter()).notifyDataSetChanged();
        }
    }

    private static class CascadingMenuInfo {
        public final MenuBuilder menu;
        public final int position;
        public final MenuPopupWindow window;

        public CascadingMenuInfo(MenuPopupWindow menuPopupWindow, MenuBuilder menuBuilder, int n) {
            this.window = menuPopupWindow;
            this.menu = menuBuilder;
            this.position = n;
        }

        public ListView getListView() {
            return this.window.getListView();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HorizPosition {
    }

}

