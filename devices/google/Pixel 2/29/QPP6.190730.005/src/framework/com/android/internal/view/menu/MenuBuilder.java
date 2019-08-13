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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.SubMenuBuilder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MenuBuilder
implements Menu {
    private static final String ACTION_VIEW_STATES_KEY = "android:menu:actionviewstates";
    private static final String EXPANDED_ACTION_VIEW_ID = "android:menu:expandedactionview";
    private static final String PRESENTER_KEY = "android:menu:presenters";
    private static final String TAG = "MenuBuilder";
    private static final int[] sCategoryToOrder = new int[]{1, 4, 5, 3, 2, 0};
    private ArrayList<MenuItemImpl> mActionItems;
    private Callback mCallback;
    @UnsupportedAppUsage
    private final Context mContext;
    private ContextMenu.ContextMenuInfo mCurrentMenuInfo;
    private int mDefaultShowAsAction = 0;
    private MenuItemImpl mExpandedItem;
    private SparseArray<Parcelable> mFrozenViewStates;
    private boolean mGroupDividerEnabled = false;
    Drawable mHeaderIcon;
    CharSequence mHeaderTitle;
    View mHeaderView;
    private boolean mIsActionItemsStale;
    private boolean mIsClosing = false;
    private boolean mIsVisibleItemsStale;
    private ArrayList<MenuItemImpl> mItems;
    private boolean mItemsChangedWhileDispatchPrevented = false;
    private ArrayList<MenuItemImpl> mNonActionItems;
    private boolean mOptionalIconsVisible = false;
    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> mPresenters = new CopyOnWriteArrayList();
    private boolean mPreventDispatchingItemsChanged = false;
    private boolean mQwertyMode;
    private final Resources mResources;
    private boolean mShortcutsVisible;
    private ArrayList<MenuItemImpl> mTempShortcutItemList = new ArrayList();
    private ArrayList<MenuItemImpl> mVisibleItems;

    @UnsupportedAppUsage
    public MenuBuilder(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
        this.mItems = new ArrayList();
        this.mVisibleItems = new ArrayList();
        this.mIsVisibleItemsStale = true;
        this.mActionItems = new ArrayList();
        this.mNonActionItems = new ArrayList();
        this.mIsActionItemsStale = true;
        this.setShortcutsVisibleInner(true);
    }

    private MenuItem addInternal(int n, int n2, int n3, CharSequence object) {
        int n4 = MenuBuilder.getOrdering(n3);
        object = this.createNewMenuItem(n, n2, n3, n4, (CharSequence)object, this.mDefaultShowAsAction);
        Object object2 = this.mCurrentMenuInfo;
        if (object2 != null) {
            ((MenuItemImpl)object).setMenuInfo((ContextMenu.ContextMenuInfo)object2);
        }
        object2 = this.mItems;
        ((ArrayList)object2).add(MenuBuilder.findInsertIndex((ArrayList<MenuItemImpl>)object2, n4), object);
        this.onItemsChanged(true);
        return object;
    }

    private MenuItemImpl createNewMenuItem(int n, int n2, int n3, int n4, CharSequence charSequence, int n5) {
        return new MenuItemImpl(this, n, n2, n3, n4, charSequence, n5);
    }

    private void dispatchPresenterUpdate(boolean bl) {
        if (this.mPresenters.isEmpty()) {
            return;
        }
        this.stopDispatchingItemsChanged();
        for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            menuPresenter.updateMenuView(bl);
        }
        this.startDispatchingItemsChanged();
    }

    private void dispatchRestoreInstanceState(Bundle object) {
        SparseArray sparseArray = ((Bundle)((Object)object)).getSparseParcelableArray(PRESENTER_KEY);
        if (sparseArray != null && !this.mPresenters.isEmpty()) {
            for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
                Parcelable parcelable;
                MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(weakReference);
                    continue;
                }
                int n = menuPresenter.getId();
                if (n <= 0 || (parcelable = (Parcelable)sparseArray.get(n)) == null) continue;
                menuPresenter.onRestoreInstanceState(parcelable);
            }
            return;
        }
    }

    private void dispatchSaveInstanceState(Bundle bundle) {
        if (this.mPresenters.isEmpty()) {
            return;
        }
        SparseArray<Object> sparseArray = new SparseArray<Object>();
        for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            int n = menuPresenter.getId();
            if (n <= 0 || (weakReference = menuPresenter.onSaveInstanceState()) == null) continue;
            sparseArray.put(n, weakReference);
        }
        bundle.putSparseParcelableArray(PRESENTER_KEY, sparseArray);
    }

    private boolean dispatchSubMenuSelected(SubMenuBuilder subMenuBuilder, MenuPresenter weakReference2) {
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean bl = false;
        if (weakReference2 != null) {
            bl = weakReference2.onSubMenuSelected(subMenuBuilder);
        }
        for (WeakReference<MenuPresenter> weakReference2 : this.mPresenters) {
            boolean bl2;
            MenuPresenter menuPresenter = (MenuPresenter)weakReference2.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference2);
                bl2 = bl;
            } else {
                bl2 = bl;
                if (!bl) {
                    bl2 = menuPresenter.onSubMenuSelected(subMenuBuilder);
                }
            }
            bl = bl2;
        }
        return bl;
    }

    private static int findInsertIndex(ArrayList<MenuItemImpl> arrayList, int n) {
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            if (arrayList.get(i).getOrdering() > n) continue;
            return i + 1;
        }
        return 0;
    }

    private static int getOrdering(int n) {
        int[] arrn;
        int n2 = (-65536 & n) >> 16;
        if (n2 >= 0 && n2 < (arrn = sCategoryToOrder).length) {
            return arrn[n2] << 16 | 65535 & n;
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    private void removeItemAtInt(int n, boolean bl) {
        if (n >= 0 && n < this.mItems.size()) {
            this.mItems.remove(n);
            if (bl) {
                this.onItemsChanged(true);
            }
            return;
        }
    }

    private void setHeaderInternal(int n, CharSequence charSequence, int n2, Drawable drawable2, View view) {
        Resources resources = this.getResources();
        if (view != null) {
            this.mHeaderView = view;
            this.mHeaderTitle = null;
            this.mHeaderIcon = null;
        } else {
            if (n > 0) {
                this.mHeaderTitle = resources.getText(n);
            } else if (charSequence != null) {
                this.mHeaderTitle = charSequence;
            }
            if (n2 > 0) {
                this.mHeaderIcon = this.getContext().getDrawable(n2);
            } else if (drawable2 != null) {
                this.mHeaderIcon = drawable2;
            }
            this.mHeaderView = null;
        }
        this.onItemsChanged(false);
    }

    private void setShortcutsVisibleInner(boolean bl) {
        boolean bl2 = true;
        bl = bl && this.mResources.getConfiguration().keyboard != 1 && ViewConfiguration.get(this.mContext).shouldShowMenuShortcutsWhenKeyboardPresent() ? bl2 : false;
        this.mShortcutsVisible = bl;
    }

    @Override
    public MenuItem add(int n) {
        return this.addInternal(0, 0, 0, this.mResources.getString(n));
    }

    @Override
    public MenuItem add(int n, int n2, int n3, int n4) {
        return this.addInternal(n, n2, n3, this.mResources.getString(n4));
    }

    @Override
    public MenuItem add(int n, int n2, int n3, CharSequence charSequence) {
        return this.addInternal(n, n2, n3, charSequence);
    }

    @Override
    public MenuItem add(CharSequence charSequence) {
        return this.addInternal(0, 0, 0, charSequence);
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

    @UnsupportedAppUsage
    public void addMenuPresenter(MenuPresenter menuPresenter) {
        this.addMenuPresenter(menuPresenter, this.mContext);
    }

    @UnsupportedAppUsage
    public void addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        this.mPresenters.add(new WeakReference<MenuPresenter>(menuPresenter));
        menuPresenter.initForMenu(context, this);
        this.mIsActionItemsStale = true;
    }

    @Override
    public SubMenu addSubMenu(int n) {
        return this.addSubMenu(0, 0, 0, this.mResources.getString(n));
    }

    @Override
    public SubMenu addSubMenu(int n, int n2, int n3, int n4) {
        return this.addSubMenu(n, n2, n3, this.mResources.getString(n4));
    }

    @Override
    public SubMenu addSubMenu(int n, int n2, int n3, CharSequence object) {
        MenuItemImpl menuItemImpl = (MenuItemImpl)this.addInternal(n, n2, n3, (CharSequence)object);
        object = new SubMenuBuilder(this.mContext, this, menuItemImpl);
        menuItemImpl.setSubMenu((SubMenuBuilder)object);
        return object;
    }

    @Override
    public SubMenu addSubMenu(CharSequence charSequence) {
        return this.addSubMenu(0, 0, 0, charSequence);
    }

    public void changeMenuMode() {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onMenuModeChange(this);
        }
    }

    @Override
    public void clear() {
        MenuItemImpl menuItemImpl = this.mExpandedItem;
        if (menuItemImpl != null) {
            this.collapseItemActionView(menuItemImpl);
        }
        this.mItems.clear();
        this.onItemsChanged(true);
    }

    public void clearAll() {
        this.mPreventDispatchingItemsChanged = true;
        this.clear();
        this.clearHeader();
        this.mPresenters.clear();
        this.mPreventDispatchingItemsChanged = false;
        this.mItemsChangedWhileDispatchPrevented = false;
        this.onItemsChanged(true);
    }

    public void clearHeader() {
        this.mHeaderIcon = null;
        this.mHeaderTitle = null;
        this.mHeaderView = null;
        this.onItemsChanged(false);
    }

    @Override
    public void close() {
        this.close(true);
    }

    public final void close(boolean bl) {
        if (this.mIsClosing) {
            return;
        }
        this.mIsClosing = true;
        for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            menuPresenter.onCloseMenu(this, bl);
        }
        this.mIsClosing = false;
    }

    @UnsupportedAppUsage
    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        if (!this.mPresenters.isEmpty() && this.mExpandedItem == menuItemImpl) {
            boolean bl;
            boolean bl2 = false;
            this.stopDispatchingItemsChanged();
            Iterator<WeakReference<MenuPresenter>> iterator = this.mPresenters.iterator();
            do {
                bl = bl2;
                if (!iterator.hasNext()) break;
                WeakReference<MenuPresenter> weakReference = iterator.next();
                MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
                if (menuPresenter == null) {
                    this.mPresenters.remove(weakReference);
                    bl = bl2;
                } else {
                    boolean bl3;
                    bl = bl2 = (bl3 = menuPresenter.collapseItemActionView(this, menuItemImpl));
                    if (bl3) {
                        bl = bl2;
                        break;
                    }
                }
                bl2 = bl;
            } while (true);
            this.startDispatchingItemsChanged();
            if (bl) {
                this.mExpandedItem = null;
            }
            return bl;
        }
        return false;
    }

    boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        Callback callback = this.mCallback;
        boolean bl = callback != null && callback.onMenuItemSelected(menuBuilder, menuItem);
        return bl;
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        boolean bl;
        if (this.mPresenters.isEmpty()) {
            return false;
        }
        boolean bl2 = false;
        this.stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> iterator = this.mPresenters.iterator();
        do {
            bl = bl2;
            if (!iterator.hasNext()) break;
            WeakReference<MenuPresenter> weakReference = iterator.next();
            MenuPresenter menuPresenter = (MenuPresenter)weakReference.get();
            if (menuPresenter == null) {
                this.mPresenters.remove(weakReference);
                bl = bl2;
            } else {
                boolean bl3;
                bl = bl2 = (bl3 = menuPresenter.expandItemActionView(this, menuItemImpl));
                if (bl3) {
                    bl = bl2;
                    break;
                }
            }
            bl2 = bl;
        } while (true);
        this.startDispatchingItemsChanged();
        if (bl) {
            this.mExpandedItem = menuItemImpl;
        }
        return bl;
    }

    public int findGroupIndex(int n) {
        return this.findGroupIndex(n, 0);
    }

    public int findGroupIndex(int n, int n2) {
        int n3 = this.size();
        int n4 = n2;
        if (n2 < 0) {
            n4 = 0;
        }
        for (n2 = n4; n2 < n3; ++n2) {
            if (this.mItems.get(n2).getGroupId() != n) continue;
            return n2;
        }
        return -1;
    }

    @Override
    public MenuItem findItem(int n) {
        int n2 = this.size();
        for (int i = 0; i < n2; ++i) {
            MenuItem menuItem = this.mItems.get(i);
            if (menuItem.getItemId() == n) {
                return menuItem;
            }
            if (!menuItem.hasSubMenu() || (menuItem = menuItem.getSubMenu().findItem(n)) == null) continue;
            return menuItem;
        }
        return null;
    }

    public int findItemIndex(int n) {
        int n2 = this.size();
        for (int i = 0; i < n2; ++i) {
            if (this.mItems.get(i).getItemId() != n) continue;
            return i;
        }
        return -1;
    }

    MenuItemImpl findItemWithShortcutForKey(int n, KeyEvent object) {
        ArrayList<MenuItemImpl> arrayList = this.mTempShortcutItemList;
        arrayList.clear();
        this.findItemsWithShortcutForKey(arrayList, n, (KeyEvent)object);
        if (arrayList.isEmpty()) {
            return null;
        }
        int n2 = ((KeyEvent)object).getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        ((KeyEvent)object).getKeyData(keyData);
        int n3 = arrayList.size();
        if (n3 == 1) {
            return arrayList.get(0);
        }
        boolean bl = this.isQwertyMode();
        for (int i = 0; i < n3; ++i) {
            object = arrayList.get(i);
            char c = bl ? ((MenuItemImpl)object).getAlphabeticShortcut() : ((MenuItemImpl)object).getNumericShortcut();
            if (!(c == keyData.meta[0] && (n2 & 2) == 0 || c == keyData.meta[2] && (n2 & 2) != 0) && (!bl || c != '\b' || n != 67)) continue;
            return object;
        }
        return null;
    }

    void findItemsWithShortcutForKey(List<MenuItemImpl> list, int n, KeyEvent keyEvent) {
        boolean bl = this.isQwertyMode();
        int n2 = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (!keyEvent.getKeyData(keyData) && n != 67) {
            return;
        }
        int n3 = this.mItems.size();
        for (int i = 0; i < n3; ++i) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.hasSubMenu()) {
                ((MenuBuilder)((Object)menuItemImpl.getSubMenu())).findItemsWithShortcutForKey(list, n, keyEvent);
            }
            char c = bl ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut();
            int n4 = bl ? menuItemImpl.getAlphabeticModifiers() : menuItemImpl.getNumericModifiers();
            if ((n4 = (n2 & 69647) == (69647 & n4) ? 1 : 0) == 0 || c == '\u0000' || c != keyData.meta[0] && c != keyData.meta[2] && (!bl || c != '\b' || n != 67) || !menuItemImpl.isEnabled()) continue;
            list.add(menuItemImpl);
        }
    }

    public void flagActionItems() {
        Object object;
        ArrayList<MenuItemImpl> arrayList = this.getVisibleItems();
        if (!this.mIsActionItemsStale) {
            return;
        }
        int n = 0;
        for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
            object = (MenuPresenter)weakReference.get();
            if (object == null) {
                this.mPresenters.remove(weakReference);
                continue;
            }
            n |= object.flagActionItems();
        }
        if (n != 0) {
            this.mActionItems.clear();
            this.mNonActionItems.clear();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                object = arrayList.get(n);
                if (((MenuItemImpl)object).isActionButton()) {
                    this.mActionItems.add((MenuItemImpl)object);
                    continue;
                }
                this.mNonActionItems.add((MenuItemImpl)object);
            }
        } else {
            this.mActionItems.clear();
            this.mNonActionItems.clear();
            this.mNonActionItems.addAll(this.getVisibleItems());
        }
        this.mIsActionItemsStale = false;
    }

    public ArrayList<MenuItemImpl> getActionItems() {
        this.flagActionItems();
        return this.mActionItems;
    }

    protected String getActionViewStatesKey() {
        return ACTION_VIEW_STATES_KEY;
    }

    @UnsupportedAppUsage
    public Context getContext() {
        return this.mContext;
    }

    public MenuItemImpl getExpandedItem() {
        return this.mExpandedItem;
    }

    @UnsupportedAppUsage
    public Drawable getHeaderIcon() {
        return this.mHeaderIcon;
    }

    @UnsupportedAppUsage
    public CharSequence getHeaderTitle() {
        return this.mHeaderTitle;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    @Override
    public MenuItem getItem(int n) {
        return this.mItems.get(n);
    }

    @UnsupportedAppUsage
    public ArrayList<MenuItemImpl> getNonActionItems() {
        this.flagActionItems();
        return this.mNonActionItems;
    }

    boolean getOptionalIconsVisible() {
        return this.mOptionalIconsVisible;
    }

    Resources getResources() {
        return this.mResources;
    }

    @UnsupportedAppUsage
    public MenuBuilder getRootMenu() {
        return this;
    }

    @UnsupportedAppUsage
    public ArrayList<MenuItemImpl> getVisibleItems() {
        if (!this.mIsVisibleItemsStale) {
            return this.mVisibleItems;
        }
        this.mVisibleItems.clear();
        int n = this.mItems.size();
        for (int i = 0; i < n; ++i) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (!menuItemImpl.isVisible()) continue;
            this.mVisibleItems.add(menuItemImpl);
        }
        this.mIsVisibleItemsStale = false;
        this.mIsActionItemsStale = true;
        return this.mVisibleItems;
    }

    @Override
    public boolean hasVisibleItems() {
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            if (!this.mItems.get(i).isVisible()) continue;
            return true;
        }
        return false;
    }

    public boolean isGroupDividerEnabled() {
        return this.mGroupDividerEnabled;
    }

    boolean isQwertyMode() {
        return this.mQwertyMode;
    }

    @Override
    public boolean isShortcutKey(int n, KeyEvent keyEvent) {
        boolean bl = this.findItemWithShortcutForKey(n, keyEvent) != null;
        return bl;
    }

    public boolean isShortcutsVisible() {
        return this.mShortcutsVisible;
    }

    void onItemActionRequestChanged(MenuItemImpl menuItemImpl) {
        this.mIsActionItemsStale = true;
        this.onItemsChanged(true);
    }

    void onItemVisibleChanged(MenuItemImpl menuItemImpl) {
        this.mIsVisibleItemsStale = true;
        this.onItemsChanged(true);
    }

    public void onItemsChanged(boolean bl) {
        if (!this.mPreventDispatchingItemsChanged) {
            if (bl) {
                this.mIsVisibleItemsStale = true;
                this.mIsActionItemsStale = true;
            }
            this.dispatchPresenterUpdate(bl);
        } else {
            this.mItemsChangedWhileDispatchPrevented = true;
        }
    }

    @Override
    public boolean performIdentifierAction(int n, int n2) {
        return this.performItemAction(this.findItem(n), n2);
    }

    public boolean performItemAction(MenuItem menuItem, int n) {
        return this.performItemAction(menuItem, null, n);
    }

    public boolean performItemAction(MenuItem object, MenuPresenter menuPresenter, int n) {
        Object object2 = (MenuItemImpl)object;
        boolean bl = false;
        if (object2 != null && ((MenuItemImpl)object2).isEnabled()) {
            boolean bl2;
            boolean bl3 = ((MenuItemImpl)object2).invoke();
            object = object.getActionProvider();
            boolean bl4 = bl;
            if (object != null) {
                bl4 = bl;
                if (((ActionProvider)object).hasSubMenu()) {
                    bl4 = true;
                }
            }
            if (((MenuItemImpl)object2).hasCollapsibleActionView()) {
                bl2 = bl3 |= ((MenuItemImpl)object2).expandActionView();
                if (bl3) {
                    this.close(true);
                    bl2 = bl3;
                }
            } else if (!((MenuItemImpl)object2).hasSubMenu() && !bl4) {
                bl2 = bl3;
                if ((n & 1) == 0) {
                    this.close(true);
                    bl2 = bl3;
                }
            } else {
                if (!((MenuItemImpl)object2).hasSubMenu()) {
                    ((MenuItemImpl)object2).setSubMenu(new SubMenuBuilder(this.getContext(), this, (MenuItemImpl)object2));
                }
                object2 = (SubMenuBuilder)((MenuItemImpl)object2).getSubMenu();
                if (bl4) {
                    ((ActionProvider)object).onPrepareSubMenu((SubMenu)object2);
                }
                if (!(bl2 = bl3 | this.dispatchSubMenuSelected((SubMenuBuilder)object2, menuPresenter))) {
                    this.close(true);
                }
            }
            return bl2;
        }
        return false;
    }

    @Override
    public boolean performShortcut(int n, KeyEvent object, int n2) {
        object = this.findItemWithShortcutForKey(n, (KeyEvent)object);
        boolean bl = false;
        if (object != null) {
            bl = this.performItemAction((MenuItem)object, n2);
        }
        if ((n2 & 2) != 0) {
            this.close(true);
        }
        return bl;
    }

    @Override
    public void removeGroup(int n) {
        int n2 = this.findGroupIndex(n);
        if (n2 >= 0) {
            int n3 = this.mItems.size();
            for (int i = 0; i < n3 - n2 && this.mItems.get(n2).getGroupId() == n; ++i) {
                this.removeItemAtInt(n2, false);
            }
            this.onItemsChanged(true);
        }
    }

    @Override
    public void removeItem(int n) {
        this.removeItemAtInt(this.findItemIndex(n), true);
    }

    public void removeItemAt(int n) {
        this.removeItemAtInt(n, true);
    }

    @UnsupportedAppUsage
    public void removeMenuPresenter(MenuPresenter menuPresenter) {
        for (WeakReference<MenuPresenter> weakReference : this.mPresenters) {
            MenuPresenter menuPresenter2 = (MenuPresenter)weakReference.get();
            if (menuPresenter2 != null && menuPresenter2 != menuPresenter) continue;
            this.mPresenters.remove(weakReference);
        }
    }

    public void restoreActionViewStates(Bundle object) {
        int n;
        if (object == null) {
            return;
        }
        SparseArray<Parcelable> sparseArray = ((Bundle)object).getSparseParcelableArray(this.getActionViewStatesKey());
        int n2 = this.size();
        for (n = 0; n < n2; ++n) {
            MenuItem menuItem = this.getItem(n);
            View view = menuItem.getActionView();
            if (view != null && view.getId() != -1) {
                view.restoreHierarchyState(sparseArray);
            }
            if (!menuItem.hasSubMenu()) continue;
            ((SubMenuBuilder)menuItem.getSubMenu()).restoreActionViewStates((Bundle)object);
        }
        n = ((BaseBundle)object).getInt(EXPANDED_ACTION_VIEW_ID);
        if (n > 0 && (object = this.findItem(n)) != null) {
            object.expandActionView();
        }
    }

    public void restorePresenterStates(Bundle bundle) {
        this.dispatchRestoreInstanceState(bundle);
    }

    public void saveActionViewStates(Bundle bundle) {
        SparseArray sparseArray = null;
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            MenuItem menuItem = this.getItem(i);
            View view = menuItem.getActionView();
            SparseArray sparseArray2 = sparseArray;
            if (view != null) {
                sparseArray2 = sparseArray;
                if (view.getId() != -1) {
                    SparseArray sparseArray3 = sparseArray;
                    if (sparseArray == null) {
                        sparseArray3 = new SparseArray();
                    }
                    view.saveHierarchyState(sparseArray3);
                    sparseArray2 = sparseArray3;
                    if (menuItem.isActionViewExpanded()) {
                        bundle.putInt(EXPANDED_ACTION_VIEW_ID, menuItem.getItemId());
                        sparseArray2 = sparseArray3;
                    }
                }
            }
            if (menuItem.hasSubMenu()) {
                ((SubMenuBuilder)menuItem.getSubMenu()).saveActionViewStates(bundle);
            }
            sparseArray = sparseArray2;
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(this.getActionViewStatesKey(), sparseArray);
        }
    }

    public void savePresenterStates(Bundle bundle) {
        this.dispatchSaveInstanceState(bundle);
    }

    @UnsupportedAppUsage
    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @UnsupportedAppUsage
    public void setCurrentMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mCurrentMenuInfo = contextMenuInfo;
    }

    @UnsupportedAppUsage
    public MenuBuilder setDefaultShowAsAction(int n) {
        this.mDefaultShowAsAction = n;
        return this;
    }

    void setExclusiveItemChecked(MenuItem menuItem) {
        int n = menuItem.getGroupId();
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.getGroupId() != n || !menuItemImpl.isExclusiveCheckable() || !menuItemImpl.isCheckable()) continue;
            boolean bl = menuItemImpl == menuItem;
            menuItemImpl.setCheckedInt(bl);
        }
    }

    @Override
    public void setGroupCheckable(int n, boolean bl, boolean bl2) {
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.getGroupId() != n) continue;
            menuItemImpl.setExclusiveCheckable(bl2);
            menuItemImpl.setCheckable(bl);
        }
    }

    @Override
    public void setGroupDividerEnabled(boolean bl) {
        this.mGroupDividerEnabled = bl;
    }

    @Override
    public void setGroupEnabled(int n, boolean bl) {
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            if (menuItemImpl.getGroupId() != n) continue;
            menuItemImpl.setEnabled(bl);
        }
    }

    @Override
    public void setGroupVisible(int n, boolean bl) {
        int n2 = this.mItems.size();
        boolean bl2 = false;
        for (int i = 0; i < n2; ++i) {
            MenuItemImpl menuItemImpl = this.mItems.get(i);
            boolean bl3 = bl2;
            if (menuItemImpl.getGroupId() == n) {
                bl3 = bl2;
                if (menuItemImpl.setVisibleInt(bl)) {
                    bl3 = true;
                }
            }
            bl2 = bl3;
        }
        if (bl2) {
            this.onItemsChanged(true);
        }
    }

    protected MenuBuilder setHeaderIconInt(int n) {
        this.setHeaderInternal(0, null, n, null, null);
        return this;
    }

    protected MenuBuilder setHeaderIconInt(Drawable drawable2) {
        this.setHeaderInternal(0, null, 0, drawable2, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(int n) {
        this.setHeaderInternal(n, null, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderTitleInt(CharSequence charSequence) {
        this.setHeaderInternal(0, charSequence, 0, null, null);
        return this;
    }

    protected MenuBuilder setHeaderViewInt(View view) {
        this.setHeaderInternal(0, null, 0, null, view);
        return this;
    }

    @UnsupportedAppUsage
    void setOptionalIconsVisible(boolean bl) {
        this.mOptionalIconsVisible = bl;
    }

    @Override
    public void setQwertyMode(boolean bl) {
        this.mQwertyMode = bl;
        this.onItemsChanged(false);
    }

    public void setShortcutsVisible(boolean bl) {
        if (this.mShortcutsVisible == bl) {
            return;
        }
        this.setShortcutsVisibleInner(bl);
        this.onItemsChanged(false);
    }

    @Override
    public int size() {
        return this.mItems.size();
    }

    @UnsupportedAppUsage
    public void startDispatchingItemsChanged() {
        this.mPreventDispatchingItemsChanged = false;
        if (this.mItemsChangedWhileDispatchPrevented) {
            this.mItemsChangedWhileDispatchPrevented = false;
            this.onItemsChanged(true);
        }
    }

    @UnsupportedAppUsage
    public void stopDispatchingItemsChanged() {
        if (!this.mPreventDispatchingItemsChanged) {
            this.mPreventDispatchingItemsChanged = true;
            this.mItemsChangedWhileDispatchPrevented = false;
        }
    }

    public static interface Callback {
        @UnsupportedAppUsage
        public boolean onMenuItemSelected(MenuBuilder var1, MenuItem var2);

        @UnsupportedAppUsage
        public void onMenuModeChange(MenuBuilder var1);
    }

    public static interface ItemInvoker {
        public boolean invokeItem(MenuItemImpl var1);
    }

}

