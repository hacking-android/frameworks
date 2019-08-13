/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;

public final class MenuItemImpl
implements MenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int IS_ACTION = 32;
    static final int NO_ICON = 0;
    private static final int SHOW_AS_ACTION_MASK = 3;
    private static final String TAG = "MenuItemImpl";
    private ActionProvider mActionProvider;
    private View mActionView;
    private final int mCategoryOrder;
    private MenuItem.OnMenuItemClickListener mClickListener;
    private CharSequence mContentDescription;
    private int mFlags = 16;
    private final int mGroup;
    private boolean mHasIconTint = false;
    private boolean mHasIconTintMode = false;
    private Drawable mIconDrawable;
    @UnsupportedAppUsage
    private int mIconResId = 0;
    private ColorStateList mIconTintList = null;
    private PorterDuff.Mode mIconTintMode = null;
    private final int mId;
    private Intent mIntent;
    private boolean mIsActionViewExpanded = false;
    private Runnable mItemCallback;
    private MenuBuilder mMenu;
    private ContextMenu.ContextMenuInfo mMenuInfo;
    private boolean mNeedToApplyIconTint = false;
    private MenuItem.OnActionExpandListener mOnActionExpandListener;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private int mShortcutAlphabeticModifiers = 4096;
    private char mShortcutNumericChar;
    private int mShortcutNumericModifiers = 4096;
    private int mShowAsAction = 0;
    private SubMenuBuilder mSubMenu;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;
    private CharSequence mTooltipText;

    MenuItemImpl(MenuBuilder menuBuilder, int n, int n2, int n3, int n4, CharSequence charSequence, int n5) {
        this.mMenu = menuBuilder;
        this.mId = n2;
        this.mGroup = n;
        this.mCategoryOrder = n3;
        this.mOrdering = n4;
        this.mTitle = charSequence;
        this.mShowAsAction = n5;
    }

    private static void appendModifier(StringBuilder stringBuilder, int n, int n2, String string2) {
        if ((n & n2) == n2) {
            stringBuilder.append(string2);
        }
    }

    private Drawable applyIconTintIfNecessary(Drawable drawable2) {
        Drawable drawable3;
        block5 : {
            block6 : {
                drawable3 = drawable2;
                if (drawable2 == null) break block5;
                drawable3 = drawable2;
                if (!this.mNeedToApplyIconTint) break block5;
                if (this.mHasIconTint) break block6;
                drawable3 = drawable2;
                if (!this.mHasIconTintMode) break block5;
            }
            drawable3 = drawable2.mutate();
            if (this.mHasIconTint) {
                drawable3.setTintList(this.mIconTintList);
            }
            if (this.mHasIconTintMode) {
                drawable3.setTintMode(this.mIconTintMode);
            }
            this.mNeedToApplyIconTint = false;
        }
        return drawable3;
    }

    public void actionFormatChanged() {
        this.mMenu.onItemActionRequestChanged(this);
    }

    @Override
    public boolean collapseActionView() {
        if ((this.mShowAsAction & 8) == 0) {
            return false;
        }
        if (this.mActionView == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
        if (onActionExpandListener != null && !onActionExpandListener.onMenuItemActionCollapse(this)) {
            return false;
        }
        return this.mMenu.collapseItemActionView(this);
    }

    @Override
    public boolean expandActionView() {
        if (!this.hasCollapsibleActionView()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
        if (onActionExpandListener != null && !onActionExpandListener.onMenuItemActionExpand(this)) {
            return false;
        }
        return this.mMenu.expandItemActionView(this);
    }

    @Override
    public ActionProvider getActionProvider() {
        return this.mActionProvider;
    }

    @Override
    public View getActionView() {
        Object object = this.mActionView;
        if (object != null) {
            return object;
        }
        object = this.mActionProvider;
        if (object != null) {
            this.mActionView = ((ActionProvider)object).onCreateActionView(this);
            return this.mActionView;
        }
        return null;
    }

    @Override
    public int getAlphabeticModifiers() {
        return this.mShortcutAlphabeticModifiers;
    }

    @Override
    public char getAlphabeticShortcut() {
        return this.mShortcutAlphabeticChar;
    }

    Runnable getCallback() {
        return this.mItemCallback;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override
    public int getGroupId() {
        return this.mGroup;
    }

    @Override
    public Drawable getIcon() {
        Drawable drawable2 = this.mIconDrawable;
        if (drawable2 != null) {
            return this.applyIconTintIfNecessary(drawable2);
        }
        if (this.mIconResId != 0) {
            drawable2 = this.mMenu.getContext().getDrawable(this.mIconResId);
            this.mIconResId = 0;
            this.mIconDrawable = drawable2;
            return this.applyIconTintIfNecessary(drawable2);
        }
        return null;
    }

    @Override
    public ColorStateList getIconTintList() {
        return this.mIconTintList;
    }

    @Override
    public PorterDuff.Mode getIconTintMode() {
        return this.mIconTintMode;
    }

    @Override
    public Intent getIntent() {
        return this.mIntent;
    }

    @ViewDebug.CapturedViewProperty
    @Override
    public int getItemId() {
        return this.mId;
    }

    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.mMenuInfo;
    }

    @Override
    public int getNumericModifiers() {
        return this.mShortcutNumericModifiers;
    }

    @Override
    public char getNumericShortcut() {
        return this.mShortcutNumericChar;
    }

    @Override
    public int getOrder() {
        return this.mCategoryOrder;
    }

    public int getOrdering() {
        return this.mOrdering;
    }

    char getShortcut() {
        char c;
        char c2;
        char c3 = this.mMenu.isQwertyMode() ? (c = this.mShortcutAlphabeticChar) : (c2 = this.mShortcutNumericChar);
        return c3;
    }

    String getShortcutLabel() {
        char c = this.getShortcut();
        if (c == '\u0000') {
            return "";
        }
        Resources resources = this.mMenu.getContext().getResources();
        StringBuilder stringBuilder = new StringBuilder();
        if (ViewConfiguration.get(this.mMenu.getContext()).hasPermanentMenuKey()) {
            stringBuilder.append(resources.getString(17040878));
        }
        int n = this.mMenu.isQwertyMode() ? this.mShortcutAlphabeticModifiers : this.mShortcutNumericModifiers;
        MenuItemImpl.appendModifier(stringBuilder, n, 65536, resources.getString(17040408));
        MenuItemImpl.appendModifier(stringBuilder, n, 4096, resources.getString(17040404));
        MenuItemImpl.appendModifier(stringBuilder, n, 2, resources.getString(17040403));
        MenuItemImpl.appendModifier(stringBuilder, n, 1, resources.getString(17040409));
        MenuItemImpl.appendModifier(stringBuilder, n, 4, resources.getString(17040411));
        MenuItemImpl.appendModifier(stringBuilder, n, 8, resources.getString(17040407));
        if (c != '\b') {
            if (c != '\n') {
                if (c != ' ') {
                    stringBuilder.append(c);
                } else {
                    stringBuilder.append(resources.getString(17040410));
                }
            } else {
                stringBuilder.append(resources.getString(17040406));
            }
        } else {
            stringBuilder.append(resources.getString(17040405));
        }
        return stringBuilder.toString();
    }

    @Override
    public SubMenu getSubMenu() {
        return this.mSubMenu;
    }

    @ViewDebug.CapturedViewProperty
    @Override
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override
    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.mTitleCondensed;
        if (charSequence == null) {
            charSequence = this.mTitle;
        }
        return charSequence;
    }

    CharSequence getTitleForItemView(MenuView.ItemView object) {
        object = object != null && object.prefersCondensedTitle() ? this.getTitleCondensed() : this.getTitle();
        return object;
    }

    @Override
    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public boolean hasCollapsibleActionView() {
        int n = this.mShowAsAction;
        boolean bl = false;
        if ((n & 8) != 0) {
            ActionProvider actionProvider;
            if (this.mActionView == null && (actionProvider = this.mActionProvider) != null) {
                this.mActionView = actionProvider.onCreateActionView(this);
            }
            if (this.mActionView != null) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public boolean hasSubMenu() {
        boolean bl = this.mSubMenu != null;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean invoke() {
        Object object = this.mClickListener;
        if (object != null && object.onMenuItemClick(this)) {
            return true;
        }
        object = this.mMenu;
        if (((MenuBuilder)object).dispatchMenuItemSelected((MenuBuilder)object, this)) {
            return true;
        }
        object = this.mItemCallback;
        if (object != null) {
            object.run();
            return true;
        }
        if (this.mIntent != null) {
            try {
                this.mMenu.getContext().startActivity(this.mIntent);
                return true;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                Log.e(TAG, "Can't find activity to handle intent; ignoring", activityNotFoundException);
            }
        }
        return (object = this.mActionProvider) != null && ((ActionProvider)object).onPerformDefaultAction();
    }

    @UnsupportedAppUsage
    public boolean isActionButton() {
        boolean bl = (this.mFlags & 32) == 32;
        return bl;
    }

    @Override
    public boolean isActionViewExpanded() {
        return this.mIsActionViewExpanded;
    }

    @Override
    public boolean isCheckable() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isChecked() {
        boolean bl = (this.mFlags & 2) == 2;
        return bl;
    }

    @Override
    public boolean isEnabled() {
        boolean bl = (this.mFlags & 16) != 0;
        return bl;
    }

    public boolean isExclusiveCheckable() {
        boolean bl = (this.mFlags & 4) != 0;
        return bl;
    }

    @Override
    public boolean isVisible() {
        ActionProvider actionProvider = this.mActionProvider;
        boolean bl = true;
        boolean bl2 = true;
        if (actionProvider != null && actionProvider.overridesItemVisibility()) {
            if ((this.mFlags & 8) != 0 || !this.mActionProvider.isVisible()) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = (this.mFlags & 8) == 0 ? bl : false;
        return bl2;
    }

    @UnsupportedAppUsage
    public boolean requestsActionButton() {
        int n = this.mShowAsAction;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    @Override
    public boolean requiresActionButton() {
        boolean bl = (this.mShowAsAction & 2) == 2;
        return bl;
    }

    @Override
    public boolean requiresOverflow() {
        boolean bl = !this.requiresActionButton() && !this.requestsActionButton();
        return bl;
    }

    @Override
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        ActionProvider actionProvider2 = this.mActionProvider;
        if (actionProvider2 != null) {
            actionProvider2.reset();
        }
        this.mActionView = null;
        this.mActionProvider = actionProvider;
        this.mMenu.onItemsChanged(true);
        actionProvider = this.mActionProvider;
        if (actionProvider != null) {
            actionProvider.setVisibilityListener(new ActionProvider.VisibilityListener(){

                @Override
                public void onActionProviderVisibilityChanged(boolean bl) {
                    MenuItemImpl.this.mMenu.onItemVisibleChanged(MenuItemImpl.this);
                }
            });
        }
        return this;
    }

    @Override
    public MenuItem setActionView(int n) {
        Context context = this.mMenu.getContext();
        this.setActionView(LayoutInflater.from(context).inflate(n, (ViewGroup)new LinearLayout(context), false));
        return this;
    }

    @Override
    public MenuItem setActionView(View view) {
        int n;
        this.mActionView = view;
        this.mActionProvider = null;
        if (view != null && view.getId() == -1 && (n = this.mId) > 0) {
            view.setId(n);
        }
        this.mMenu.onItemActionRequestChanged(this);
        return this;
    }

    @UnsupportedAppUsage
    public void setActionViewExpanded(boolean bl) {
        this.mIsActionViewExpanded = bl;
        this.mMenu.onItemsChanged(false);
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c) {
        if (this.mShortcutAlphabeticChar == c) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c, int n) {
        if (this.mShortcutAlphabeticChar == c && this.mShortcutAlphabeticModifiers == n) {
            return this;
        }
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(n);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    public MenuItem setCallback(Runnable runnable) {
        this.mItemCallback = runnable;
        return this;
    }

    @Override
    public MenuItem setCheckable(boolean bl) {
        int n = this.mFlags;
        this.mFlags = this.mFlags & -2 | bl;
        if (n != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
        return this;
    }

    @Override
    public MenuItem setChecked(boolean bl) {
        if ((this.mFlags & 4) != 0) {
            this.mMenu.setExclusiveItemChecked(this);
        } else {
            this.setCheckedInt(bl);
        }
        return this;
    }

    void setCheckedInt(boolean bl) {
        int n = this.mFlags;
        int n2 = this.mFlags;
        int n3 = bl ? 2 : 0;
        this.mFlags = n2 & -3 | n3;
        if (n != this.mFlags) {
            this.mMenu.onItemsChanged(false);
        }
    }

    @Override
    public MenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setEnabled(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 16) : (this.mFlags &= -17);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @UnsupportedAppUsage
    public void setExclusiveCheckable(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 4 : 0;
        this.mFlags = n & -5 | n2;
    }

    @Override
    public MenuItem setIcon(int n) {
        this.mIconDrawable = null;
        this.mIconResId = n;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIcon(Drawable drawable2) {
        this.mIconResId = 0;
        this.mIconDrawable = drawable2;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.mHasIconTint = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode;
        this.mHasIconTintMode = true;
        this.mNeedToApplyIconTint = true;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    public void setIsActionButton(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 32) : (this.mFlags &= -33);
    }

    @UnsupportedAppUsage
    void setMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.mMenuInfo = contextMenuInfo;
    }

    @Override
    public MenuItem setNumericShortcut(char c) {
        if (this.mShortcutNumericChar == c) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c, int n) {
        if (this.mShortcutNumericChar == c && this.mShortcutNumericModifiers == n) {
            return this;
        }
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(n);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
        return this;
    }

    @Override
    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mClickListener = onMenuItemClickListener;
        return this;
    }

    @Override
    public MenuItem setShortcut(char c, char c2) {
        this.mShortcutNumericChar = c;
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setShortcut(char c, char c2, int n, int n2) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(n);
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(n2);
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public void setShowAsAction(int n) {
        int n2 = n & 3;
        if (n2 != 0 && n2 != 1 && n2 != 2) {
            throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
        this.mShowAsAction = n;
        this.mMenu.onItemActionRequestChanged(this);
    }

    @Override
    public MenuItem setShowAsActionFlags(int n) {
        this.setShowAsAction(n);
        return this;
    }

    void setSubMenu(SubMenuBuilder subMenuBuilder) {
        this.mSubMenu = subMenuBuilder;
        subMenuBuilder.setHeaderTitle(this.getTitle());
    }

    @Override
    public MenuItem setTitle(int n) {
        return this.setTitle(this.mMenu.getContext().getString(n));
    }

    @Override
    public MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.mMenu.onItemsChanged(false);
        SubMenuBuilder subMenuBuilder = this.mSubMenu;
        if (subMenuBuilder != null) {
            subMenuBuilder.setHeaderTitle(charSequence);
        }
        return this;
    }

    @Override
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence;
        if (charSequence == null) {
            charSequence = this.mTitle;
        }
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence;
        this.mMenu.onItemsChanged(false);
        return this;
    }

    @Override
    public MenuItem setVisible(boolean bl) {
        if (this.setVisibleInt(bl)) {
            this.mMenu.onItemVisibleChanged(this);
        }
        return this;
    }

    boolean setVisibleInt(boolean bl) {
        int n = this.mFlags;
        int n2 = this.mFlags;
        boolean bl2 = false;
        int n3 = bl ? 0 : 8;
        this.mFlags = n2 & -9 | n3;
        bl = bl2;
        if (n != this.mFlags) {
            bl = true;
        }
        return bl;
    }

    public boolean shouldShowIcon() {
        return this.mMenu.getOptionalIconsVisible();
    }

    boolean shouldShowShortcut() {
        boolean bl = this.mMenu.isShortcutsVisible() && this.getShortcut() != '\u0000';
        return bl;
    }

    public boolean showsTextAsAction() {
        boolean bl = (this.mShowAsAction & 4) == 4;
        return bl;
    }

    public String toString() {
        CharSequence charSequence = this.mTitle;
        charSequence = charSequence != null ? charSequence.toString() : null;
        return charSequence;
    }

}

