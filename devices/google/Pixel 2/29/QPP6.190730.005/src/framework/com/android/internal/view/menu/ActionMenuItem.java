/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class ActionMenuItem
implements MenuItem {
    private static final int CHECKABLE = 1;
    private static final int CHECKED = 2;
    private static final int ENABLED = 16;
    private static final int EXCLUSIVE = 4;
    private static final int HIDDEN = 8;
    private static final int NO_ICON = 0;
    private final int mCategoryOrder;
    private MenuItem.OnMenuItemClickListener mClickListener;
    private CharSequence mContentDescription;
    private Context mContext;
    private int mFlags = 16;
    private final int mGroup;
    private boolean mHasIconTint = false;
    private boolean mHasIconTintMode = false;
    private Drawable mIconDrawable;
    private int mIconResId = 0;
    private ColorStateList mIconTintList = null;
    private PorterDuff.Mode mIconTintMode = null;
    private final int mId;
    private Intent mIntent;
    private final int mOrdering;
    private char mShortcutAlphabeticChar;
    private int mShortcutAlphabeticModifiers = 4096;
    private char mShortcutNumericChar;
    private int mShortcutNumericModifiers = 4096;
    private CharSequence mTitle;
    private CharSequence mTitleCondensed;
    private CharSequence mTooltipText;

    @UnsupportedAppUsage
    public ActionMenuItem(Context context, int n, int n2, int n3, int n4, CharSequence charSequence) {
        this.mContext = context;
        this.mId = n2;
        this.mGroup = n;
        this.mCategoryOrder = n3;
        this.mOrdering = n4;
        this.mTitle = charSequence;
    }

    private void applyIconTint() {
        if (this.mIconDrawable != null && (this.mHasIconTint || this.mHasIconTintMode)) {
            this.mIconDrawable = this.mIconDrawable.mutate();
            if (this.mHasIconTint) {
                this.mIconDrawable.setTintList(this.mIconTintList);
            }
            if (this.mHasIconTintMode) {
                this.mIconDrawable.setTintMode(this.mIconTintMode);
            }
        }
    }

    @Override
    public boolean collapseActionView() {
        return false;
    }

    @Override
    public boolean expandActionView() {
        return false;
    }

    @Override
    public ActionProvider getActionProvider() {
        return null;
    }

    @Override
    public View getActionView() {
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
        return this.mIconDrawable;
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

    @Override
    public int getItemId() {
        return this.mId;
    }

    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
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
        return this.mOrdering;
    }

    @Override
    public SubMenu getSubMenu() {
        return null;
    }

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

    @Override
    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    @Override
    public boolean hasSubMenu() {
        return false;
    }

    public boolean invoke() {
        Object object = this.mClickListener;
        if (object != null && object.onMenuItemClick(this)) {
            return true;
        }
        object = this.mIntent;
        if (object != null) {
            this.mContext.startActivity((Intent)object);
            return true;
        }
        return false;
    }

    @Override
    public boolean isActionViewExpanded() {
        return false;
    }

    @Override
    public boolean isCheckable() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean isChecked() {
        boolean bl = (this.mFlags & 2) != 0;
        return bl;
    }

    @Override
    public boolean isEnabled() {
        boolean bl = (this.mFlags & 16) != 0;
        return bl;
    }

    @Override
    public boolean isVisible() {
        boolean bl = (this.mFlags & 8) == 0;
        return bl;
    }

    @Override
    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItem setActionView(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItem setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c) {
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        return this;
    }

    @Override
    public MenuItem setAlphabeticShortcut(char c, int n) {
        this.mShortcutAlphabeticChar = Character.toLowerCase(c);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(n);
        return this;
    }

    @Override
    public MenuItem setCheckable(boolean bl) {
        this.mFlags = this.mFlags & -2 | bl;
        return this;
    }

    @Override
    public MenuItem setChecked(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 2 : 0;
        this.mFlags = n & -3 | n2;
        return this;
    }

    @Override
    public MenuItem setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
        return this;
    }

    @Override
    public MenuItem setEnabled(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 16 : 0;
        this.mFlags = n & -17 | n2;
        return this;
    }

    public ActionMenuItem setExclusiveCheckable(boolean bl) {
        int n = this.mFlags;
        int n2 = bl ? 4 : 0;
        this.mFlags = n & -5 | n2;
        return this;
    }

    @Override
    public MenuItem setIcon(int n) {
        this.mIconResId = n;
        this.mIconDrawable = this.mContext.getDrawable(n);
        this.applyIconTint();
        return this;
    }

    @Override
    public MenuItem setIcon(Drawable drawable2) {
        this.mIconDrawable = drawable2;
        this.mIconResId = 0;
        this.applyIconTint();
        return this;
    }

    @Override
    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
        this.mHasIconTint = true;
        this.applyIconTint();
        return this;
    }

    @Override
    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.mIconTintMode = mode;
        this.mHasIconTintMode = true;
        this.applyIconTint();
        return this;
    }

    @Override
    public MenuItem setIntent(Intent intent) {
        this.mIntent = intent;
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c) {
        this.mShortcutNumericChar = c;
        return this;
    }

    @Override
    public MenuItem setNumericShortcut(char c, int n) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(n);
        return this;
    }

    @Override
    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
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
        return this;
    }

    @Override
    public MenuItem setShortcut(char c, char c2, int n, int n2) {
        this.mShortcutNumericChar = c;
        this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(n);
        this.mShortcutAlphabeticChar = Character.toLowerCase(c2);
        this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(n2);
        return this;
    }

    @Override
    public void setShowAsAction(int n) {
    }

    @Override
    public MenuItem setShowAsActionFlags(int n) {
        this.setShowAsAction(n);
        return this;
    }

    @Override
    public MenuItem setTitle(int n) {
        this.mTitle = this.mContext.getResources().getString(n);
        return this;
    }

    @Override
    public MenuItem setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        return this;
    }

    @Override
    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.mTitleCondensed = charSequence;
        return this;
    }

    @Override
    public MenuItem setTooltipText(CharSequence charSequence) {
        this.mTooltipText = charSequence;
        return this;
    }

    @Override
    public MenuItem setVisible(boolean bl) {
        int n = this.mFlags;
        int n2 = 8;
        if (bl) {
            n2 = 0;
        }
        this.mFlags = n & 8 | n2;
        return this;
    }
}

