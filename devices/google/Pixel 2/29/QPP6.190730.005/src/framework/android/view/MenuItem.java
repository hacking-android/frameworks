/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;

public interface MenuItem {
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;

    public boolean collapseActionView();

    public boolean expandActionView();

    public ActionProvider getActionProvider();

    public View getActionView();

    default public int getAlphabeticModifiers() {
        return 4096;
    }

    public char getAlphabeticShortcut();

    default public CharSequence getContentDescription() {
        return null;
    }

    public int getGroupId();

    public Drawable getIcon();

    default public BlendMode getIconTintBlendMode() {
        PorterDuff.Mode mode = this.getIconTintMode();
        if (mode != null) {
            return BlendMode.fromValue(mode.nativeInt);
        }
        return null;
    }

    default public ColorStateList getIconTintList() {
        return null;
    }

    default public PorterDuff.Mode getIconTintMode() {
        return null;
    }

    public Intent getIntent();

    public int getItemId();

    public ContextMenu.ContextMenuInfo getMenuInfo();

    default public int getNumericModifiers() {
        return 4096;
    }

    public char getNumericShortcut();

    public int getOrder();

    public SubMenu getSubMenu();

    public CharSequence getTitle();

    public CharSequence getTitleCondensed();

    default public CharSequence getTooltipText() {
        return null;
    }

    public boolean hasSubMenu();

    public boolean isActionViewExpanded();

    public boolean isCheckable();

    public boolean isChecked();

    public boolean isEnabled();

    public boolean isVisible();

    default public boolean requiresActionButton() {
        return false;
    }

    default public boolean requiresOverflow() {
        return true;
    }

    public MenuItem setActionProvider(ActionProvider var1);

    public MenuItem setActionView(int var1);

    public MenuItem setActionView(View var1);

    public MenuItem setAlphabeticShortcut(char var1);

    default public MenuItem setAlphabeticShortcut(char c, int n) {
        if ((69647 & n) == 4096) {
            return this.setAlphabeticShortcut(c);
        }
        return this;
    }

    public MenuItem setCheckable(boolean var1);

    public MenuItem setChecked(boolean var1);

    default public MenuItem setContentDescription(CharSequence charSequence) {
        return this;
    }

    public MenuItem setEnabled(boolean var1);

    public MenuItem setIcon(int var1);

    public MenuItem setIcon(Drawable var1);

    default public MenuItem setIconTintBlendMode(BlendMode enum_) {
        if ((enum_ = BlendMode.blendModeToPorterDuffMode((BlendMode)enum_)) != null) {
            return this.setIconTintMode((PorterDuff.Mode)enum_);
        }
        return this;
    }

    default public MenuItem setIconTintList(ColorStateList colorStateList) {
        return this;
    }

    default public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        return this;
    }

    public MenuItem setIntent(Intent var1);

    public MenuItem setNumericShortcut(char var1);

    default public MenuItem setNumericShortcut(char c, int n) {
        if ((69647 & n) == 4096) {
            return this.setNumericShortcut(c);
        }
        return this;
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener var1);

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener var1);

    public MenuItem setShortcut(char var1, char var2);

    default public MenuItem setShortcut(char c, char c2, int n, int n2) {
        if ((n2 & 69647) == 4096 && (69647 & n) == 4096) {
            return this.setShortcut(c, c2);
        }
        return this;
    }

    public void setShowAsAction(int var1);

    public MenuItem setShowAsActionFlags(int var1);

    public MenuItem setTitle(int var1);

    public MenuItem setTitle(CharSequence var1);

    public MenuItem setTitleCondensed(CharSequence var1);

    default public MenuItem setTooltipText(CharSequence charSequence) {
        return this;
    }

    public MenuItem setVisible(boolean var1);

    public static interface OnActionExpandListener {
        public boolean onMenuItemActionCollapse(MenuItem var1);

        public boolean onMenuItemActionExpand(MenuItem var1);
    }

    public static interface OnMenuItemClickListener {
        public boolean onMenuItemClick(MenuItem var1);
    }

}

