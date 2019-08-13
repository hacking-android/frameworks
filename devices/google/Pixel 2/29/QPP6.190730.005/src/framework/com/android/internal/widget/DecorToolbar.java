/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.widget.ScrollingTabContainerView;

public interface DecorToolbar {
    public void animateToVisibility(int var1);

    public boolean canShowOverflowMenu();

    public boolean canSplit();

    public void collapseActionView();

    public void dismissPopupMenus();

    public Context getContext();

    public View getCustomView();

    public int getDisplayOptions();

    public int getDropdownItemCount();

    public int getDropdownSelectedPosition();

    public int getHeight();

    public Menu getMenu();

    public int getNavigationMode();

    public CharSequence getSubtitle();

    public CharSequence getTitle();

    public ViewGroup getViewGroup();

    public int getVisibility();

    public boolean hasEmbeddedTabs();

    public boolean hasExpandedActionView();

    public boolean hasIcon();

    public boolean hasLogo();

    public boolean hideOverflowMenu();

    public void initIndeterminateProgress();

    public void initProgress();

    public boolean isOverflowMenuShowPending();

    public boolean isOverflowMenuShowing();

    public boolean isSplit();

    public boolean isTitleTruncated();

    public void restoreHierarchyState(SparseArray<Parcelable> var1);

    public void saveHierarchyState(SparseArray<Parcelable> var1);

    public void setBackgroundDrawable(Drawable var1);

    public void setCollapsible(boolean var1);

    public void setCustomView(View var1);

    public void setDefaultNavigationContentDescription(int var1);

    public void setDefaultNavigationIcon(Drawable var1);

    public void setDisplayOptions(int var1);

    public void setDropdownParams(SpinnerAdapter var1, AdapterView.OnItemSelectedListener var2);

    public void setDropdownSelectedPosition(int var1);

    public void setEmbeddedTabView(ScrollingTabContainerView var1);

    public void setHomeButtonEnabled(boolean var1);

    public void setIcon(int var1);

    public void setIcon(Drawable var1);

    public void setLogo(int var1);

    public void setLogo(Drawable var1);

    public void setMenu(Menu var1, MenuPresenter.Callback var2);

    public void setMenuCallbacks(MenuPresenter.Callback var1, MenuBuilder.Callback var2);

    public void setMenuPrepared();

    public void setNavigationContentDescription(int var1);

    public void setNavigationContentDescription(CharSequence var1);

    public void setNavigationIcon(int var1);

    public void setNavigationIcon(Drawable var1);

    public void setNavigationMode(int var1);

    public void setSplitToolbar(boolean var1);

    public void setSplitView(ViewGroup var1);

    public void setSplitWhenNarrow(boolean var1);

    public void setSubtitle(CharSequence var1);

    public void setTitle(CharSequence var1);

    public void setVisibility(int var1);

    public void setWindowCallback(Window.Callback var1);

    public void setWindowTitle(CharSequence var1);

    public Animator setupAnimatorToVisibility(int var1, long var2);

    public boolean showOverflowMenu();
}

