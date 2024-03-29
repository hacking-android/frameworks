/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.widget.SpinnerAdapter;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ActionBar {
    public static final int DISPLAY_HOME_AS_UP = 4;
    public static final int DISPLAY_SHOW_CUSTOM = 16;
    public static final int DISPLAY_SHOW_HOME = 2;
    public static final int DISPLAY_SHOW_TITLE = 8;
    @UnsupportedAppUsage
    public static final int DISPLAY_TITLE_MULTIPLE_LINES = 32;
    public static final int DISPLAY_USE_LOGO = 1;
    @Deprecated
    public static final int NAVIGATION_MODE_LIST = 1;
    @Deprecated
    public static final int NAVIGATION_MODE_STANDARD = 0;
    @Deprecated
    public static final int NAVIGATION_MODE_TABS = 2;

    public abstract void addOnMenuVisibilityListener(OnMenuVisibilityListener var1);

    @Deprecated
    public abstract void addTab(Tab var1);

    @Deprecated
    public abstract void addTab(Tab var1, int var2);

    @Deprecated
    public abstract void addTab(Tab var1, int var2, boolean var3);

    @Deprecated
    public abstract void addTab(Tab var1, boolean var2);

    public boolean closeOptionsMenu() {
        return false;
    }

    @UnsupportedAppUsage
    public boolean collapseActionView() {
        return false;
    }

    public void dispatchMenuVisibilityChanged(boolean bl) {
    }

    public abstract View getCustomView();

    public abstract int getDisplayOptions();

    public float getElevation() {
        return 0.0f;
    }

    public abstract int getHeight();

    public int getHideOffset() {
        return 0;
    }

    @Deprecated
    public abstract int getNavigationItemCount();

    @Deprecated
    public abstract int getNavigationMode();

    @Deprecated
    public abstract int getSelectedNavigationIndex();

    @Deprecated
    public abstract Tab getSelectedTab();

    public abstract CharSequence getSubtitle();

    @Deprecated
    public abstract Tab getTabAt(int var1);

    @Deprecated
    public abstract int getTabCount();

    public Context getThemedContext() {
        return null;
    }

    public abstract CharSequence getTitle();

    public abstract void hide();

    public boolean invalidateOptionsMenu() {
        return false;
    }

    public boolean isHideOnContentScrollEnabled() {
        return false;
    }

    public abstract boolean isShowing();

    public boolean isTitleTruncated() {
        return false;
    }

    @Deprecated
    public abstract Tab newTab();

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onDestroy() {
    }

    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        return false;
    }

    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public boolean openOptionsMenu() {
        return false;
    }

    @Deprecated
    public abstract void removeAllTabs();

    public abstract void removeOnMenuVisibilityListener(OnMenuVisibilityListener var1);

    @Deprecated
    public abstract void removeTab(Tab var1);

    @Deprecated
    public abstract void removeTabAt(int var1);

    @Deprecated
    public abstract void selectTab(Tab var1);

    public abstract void setBackgroundDrawable(Drawable var1);

    public abstract void setCustomView(int var1);

    public abstract void setCustomView(View var1);

    public abstract void setCustomView(View var1, LayoutParams var2);

    public void setDefaultDisplayHomeAsUpEnabled(boolean bl) {
    }

    public abstract void setDisplayHomeAsUpEnabled(boolean var1);

    public abstract void setDisplayOptions(int var1);

    public abstract void setDisplayOptions(int var1, int var2);

    public abstract void setDisplayShowCustomEnabled(boolean var1);

    public abstract void setDisplayShowHomeEnabled(boolean var1);

    public abstract void setDisplayShowTitleEnabled(boolean var1);

    public abstract void setDisplayUseLogoEnabled(boolean var1);

    public void setElevation(float f) {
        if (f == 0.0f) {
            return;
        }
        throw new UnsupportedOperationException("Setting a non-zero elevation is not supported in this action bar configuration.");
    }

    public void setHideOffset(int n) {
        if (n == 0) {
            return;
        }
        throw new UnsupportedOperationException("Setting an explicit action bar hide offset is not supported in this action bar configuration.");
    }

    public void setHideOnContentScrollEnabled(boolean bl) {
        if (!bl) {
            return;
        }
        throw new UnsupportedOperationException("Hide on content scroll is not supported in this action bar configuration.");
    }

    public void setHomeActionContentDescription(int n) {
    }

    public void setHomeActionContentDescription(CharSequence charSequence) {
    }

    public void setHomeAsUpIndicator(int n) {
    }

    public void setHomeAsUpIndicator(Drawable drawable2) {
    }

    public void setHomeButtonEnabled(boolean bl) {
    }

    public abstract void setIcon(int var1);

    public abstract void setIcon(Drawable var1);

    @Deprecated
    public abstract void setListNavigationCallbacks(SpinnerAdapter var1, OnNavigationListener var2);

    public abstract void setLogo(int var1);

    public abstract void setLogo(Drawable var1);

    @Deprecated
    public abstract void setNavigationMode(int var1);

    @Deprecated
    public abstract void setSelectedNavigationItem(int var1);

    @UnsupportedAppUsage
    public void setShowHideAnimationEnabled(boolean bl) {
    }

    public void setSplitBackgroundDrawable(Drawable drawable2) {
    }

    public void setStackedBackgroundDrawable(Drawable drawable2) {
    }

    public abstract void setSubtitle(int var1);

    public abstract void setSubtitle(CharSequence var1);

    public abstract void setTitle(int var1);

    public abstract void setTitle(CharSequence var1);

    public void setWindowTitle(CharSequence charSequence) {
    }

    public abstract void show();

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DisplayOptions {
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        @ViewDebug.ExportedProperty(category="layout", mapping={@ViewDebug.IntToString(from=-1, to="NONE"), @ViewDebug.IntToString(from=0, to="NONE"), @ViewDebug.IntToString(from=48, to="TOP"), @ViewDebug.IntToString(from=80, to="BOTTOM"), @ViewDebug.IntToString(from=3, to="LEFT"), @ViewDebug.IntToString(from=5, to="RIGHT"), @ViewDebug.IntToString(from=8388611, to="START"), @ViewDebug.IntToString(from=8388613, to="END"), @ViewDebug.IntToString(from=16, to="CENTER_VERTICAL"), @ViewDebug.IntToString(from=112, to="FILL_VERTICAL"), @ViewDebug.IntToString(from=1, to="CENTER_HORIZONTAL"), @ViewDebug.IntToString(from=7, to="FILL_HORIZONTAL"), @ViewDebug.IntToString(from=17, to="CENTER"), @ViewDebug.IntToString(from=119, to="FILL")})
        public int gravity = 0;

        public LayoutParams(int n) {
            this(-2, -1, n);
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.gravity = 8388627;
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.gravity = n3;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ActionBar_LayoutParams);
            this.gravity = ((TypedArray)object).getInt(0, 0);
            ((TypedArray)object).recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @Override
        protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("gravity", this.gravity);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NavigationMode {
    }

    public static interface OnMenuVisibilityListener {
        public void onMenuVisibilityChanged(boolean var1);
    }

    @Deprecated
    public static interface OnNavigationListener {
        public boolean onNavigationItemSelected(int var1, long var2);
    }

    @Deprecated
    public static abstract class Tab {
        public static final int INVALID_POSITION = -1;

        public abstract CharSequence getContentDescription();

        public abstract View getCustomView();

        public abstract Drawable getIcon();

        public abstract int getPosition();

        public abstract Object getTag();

        public abstract CharSequence getText();

        public abstract void select();

        public abstract Tab setContentDescription(int var1);

        public abstract Tab setContentDescription(CharSequence var1);

        public abstract Tab setCustomView(int var1);

        public abstract Tab setCustomView(View var1);

        public abstract Tab setIcon(int var1);

        public abstract Tab setIcon(Drawable var1);

        public abstract Tab setTabListener(TabListener var1);

        public abstract Tab setTag(Object var1);

        public abstract Tab setText(int var1);

        public abstract Tab setText(CharSequence var1);
    }

    @Deprecated
    public static interface TabListener {
        public void onTabReselected(Tab var1, FragmentTransaction var2);

        public void onTabSelected(Tab var1, FragmentTransaction var2);

        public void onTabUnselected(Tab var1, FragmentTransaction var2);
    }

}

