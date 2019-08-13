/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.DropDownListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.MenuItemHoverListener;
import android.widget.PopupWindow;
import com.android.internal.view.menu.ListMenuItemView;
import com.android.internal.view.menu.MenuAdapter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;

public class MenuPopupWindow
extends ListPopupWindow
implements MenuItemHoverListener {
    private MenuItemHoverListener mHoverListener;

    public MenuPopupWindow(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    DropDownListView createDropDownListView(Context object, boolean bl) {
        object = new MenuDropDownListView((Context)object, bl);
        ((MenuDropDownListView)object).setHoverListener(this);
        return object;
    }

    @Override
    public void onItemHoverEnter(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
        if (menuItemHoverListener != null) {
            menuItemHoverListener.onItemHoverEnter(menuBuilder, menuItem);
        }
    }

    @Override
    public void onItemHoverExit(MenuBuilder menuBuilder, MenuItem menuItem) {
        MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
        if (menuItemHoverListener != null) {
            menuItemHoverListener.onItemHoverExit(menuBuilder, menuItem);
        }
    }

    public void setEnterTransition(Transition transition2) {
        this.mPopup.setEnterTransition(transition2);
    }

    public void setExitTransition(Transition transition2) {
        this.mPopup.setExitTransition(transition2);
    }

    public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
        this.mHoverListener = menuItemHoverListener;
    }

    public void setTouchModal(boolean bl) {
        this.mPopup.setTouchModal(bl);
    }

    public static class MenuDropDownListView
    extends DropDownListView {
        final int mAdvanceKey;
        private MenuItemHoverListener mHoverListener;
        private MenuItem mHoveredMenuItem;
        final int mRetreatKey;

        public MenuDropDownListView(Context context, boolean bl) {
            super(context, bl);
            if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
            } else {
                this.mAdvanceKey = 22;
                this.mRetreatKey = 21;
            }
        }

        public void clearSelection() {
            this.setSelectedPositionInt(-1);
            this.setNextSelectedPositionInt(-1);
        }

        @Override
        public boolean onHoverEvent(MotionEvent motionEvent) {
            if (this.mHoverListener != null) {
                MenuItem menuItem;
                int n;
                Object object = this.getAdapter();
                if (object instanceof HeaderViewListAdapter) {
                    object = (HeaderViewListAdapter)object;
                    n = ((HeaderViewListAdapter)object).getHeadersCount();
                    object = (MenuAdapter)((HeaderViewListAdapter)object).getWrappedAdapter();
                } else {
                    n = 0;
                    object = (MenuAdapter)object;
                }
                MenuItem menuItem2 = menuItem = null;
                if (motionEvent.getAction() != 10) {
                    int n2 = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
                    menuItem2 = menuItem;
                    if (n2 != -1) {
                        n = n2 - n;
                        menuItem2 = menuItem;
                        if (n >= 0) {
                            menuItem2 = menuItem;
                            if (n < ((MenuAdapter)object).getCount()) {
                                menuItem2 = ((MenuAdapter)object).getItem(n);
                            }
                        }
                    }
                }
                if ((menuItem = this.mHoveredMenuItem) != menuItem2) {
                    object = ((MenuAdapter)object).getAdapterMenu();
                    if (menuItem != null) {
                        this.mHoverListener.onItemHoverExit((MenuBuilder)object, menuItem);
                    }
                    this.mHoveredMenuItem = menuItem2;
                    if (menuItem2 != null) {
                        this.mHoverListener.onItemHoverEnter((MenuBuilder)object, menuItem2);
                    }
                }
            }
            return super.onHoverEvent(motionEvent);
        }

        @Override
        public boolean onKeyDown(int n, KeyEvent keyEvent) {
            ListMenuItemView listMenuItemView = (ListMenuItemView)this.getSelectedView();
            if (listMenuItemView != null && n == this.mAdvanceKey) {
                if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                    this.performItemClick(listMenuItemView, this.getSelectedItemPosition(), this.getSelectedItemId());
                }
                return true;
            }
            if (listMenuItemView != null && n == this.mRetreatKey) {
                this.setSelectedPositionInt(-1);
                this.setNextSelectedPositionInt(-1);
                ((MenuAdapter)this.getAdapter()).getAdapterMenu().close(false);
                return true;
            }
            return super.onKeyDown(n, keyEvent);
        }

        public void setHoverListener(MenuItemHoverListener menuItemHoverListener) {
            this.mHoverListener = menuItemHoverListener;
        }
    }

}

