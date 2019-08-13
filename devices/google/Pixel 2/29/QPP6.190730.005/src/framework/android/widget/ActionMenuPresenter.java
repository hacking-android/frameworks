/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Property;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ActionMenuView;
import android.widget.ForwardingListener;
import android.widget.ImageButton;
import com.android.internal.view.ActionBarPolicy;
import com.android.internal.view.menu.ActionMenuItemView;
import com.android.internal.view.menu.BaseMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPopup;
import com.android.internal.view.menu.MenuPopupHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.ShowableListMenu;
import com.android.internal.view.menu.SubMenuBuilder;
import java.util.ArrayList;
import java.util.List;

public class ActionMenuPresenter
extends BaseMenuPresenter
implements ActionProvider.SubUiVisibilityListener {
    private static final boolean ACTIONBAR_ANIMATIONS_ENABLED = false;
    private static final int ITEM_ANIMATION_DURATION = 150;
    private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
    private ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener(){

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            ((View)((Object)ActionMenuPresenter.this.mMenuView)).getViewTreeObserver().removeOnPreDrawListener(ActionMenuPresenter.this.mItemAnimationPreDrawListener);
            ActionMenuPresenter.this.mPreLayoutItems.clear();
            ActionMenuPresenter.this.mPostLayoutItems.clear();
        }
    };
    private boolean mExpandedActionViewsExclusive;
    private ViewTreeObserver.OnPreDrawListener mItemAnimationPreDrawListener = new ViewTreeObserver.OnPreDrawListener(){

        @Override
        public boolean onPreDraw() {
            ActionMenuPresenter.this.computeMenuItemAnimationInfo(false);
            ((View)((Object)ActionMenuPresenter.this.mMenuView)).getViewTreeObserver().removeOnPreDrawListener(this);
            ActionMenuPresenter.this.runItemAnimations();
            return true;
        }
    };
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    int mOpenSubMenuId;
    private OverflowMenuButton mOverflowButton;
    private OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
    private SparseArray<MenuItemLayoutInfo> mPostLayoutItems = new SparseArray();
    private OpenOverflowRunnable mPostedOpenRunnable;
    private SparseArray<MenuItemLayoutInfo> mPreLayoutItems = new SparseArray();
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private List<ItemAnimationInfo> mRunningItemAnimations = new ArrayList<ItemAnimationInfo>();
    private boolean mStrictWidthLimit;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(Context context) {
        super(context, 17367071, 17367070);
    }

    private void computeMenuItemAnimationInfo(boolean bl) {
        ViewGroup viewGroup = (ViewGroup)((Object)this.mMenuView);
        int n = viewGroup.getChildCount();
        SparseArray<MenuItemLayoutInfo> sparseArray = bl ? this.mPreLayoutItems : this.mPostLayoutItems;
        for (int i = 0; i < n; ++i) {
            View view = viewGroup.getChildAt(i);
            int n2 = view.getId();
            if (n2 <= 0 || view.getWidth() == 0 || view.getHeight() == 0) continue;
            sparseArray.put(n2, new MenuItemLayoutInfo(view, bl));
        }
    }

    private View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup)((Object)this.mMenuView);
        if (viewGroup == null) {
            return null;
        }
        int n = viewGroup.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = viewGroup.getChildAt(i);
            if (!(view instanceof MenuView.ItemView) || ((MenuView.ItemView)((Object)view)).getItemData() != menuItem) continue;
            return view;
        }
        return null;
    }

    private void runItemAnimations() {
        int n;
        float f;
        Object object;
        Object object2;
        float f2;
        int n2;
        int n3;
        for (n3 = 0; n3 < this.mPreLayoutItems.size(); ++n3) {
            n = this.mPreLayoutItems.keyAt(n3);
            Object object3 = this.mPreLayoutItems.get(n);
            n2 = this.mPostLayoutItems.indexOfKey(n);
            if (n2 >= 0) {
                MenuItemLayoutInfo menuItemLayoutInfo = this.mPostLayoutItems.valueAt(n2);
                object2 = null;
                object = null;
                if (((MenuItemLayoutInfo)object3).left != menuItemLayoutInfo.left) {
                    object2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, ((MenuItemLayoutInfo)object3).left - menuItemLayoutInfo.left, 0.0f);
                }
                if (((MenuItemLayoutInfo)object3).top != menuItemLayoutInfo.top) {
                    object = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, ((MenuItemLayoutInfo)object3).top - menuItemLayoutInfo.top, 0.0f);
                }
                if (object2 != null || object != null) {
                    for (n2 = 0; n2 < this.mRunningItemAnimations.size(); ++n2) {
                        object3 = this.mRunningItemAnimations.get(n2);
                        if (((ItemAnimationInfo)object3).id != n || ((ItemAnimationInfo)object3).animType != 0) continue;
                        ((ItemAnimationInfo)object3).animator.cancel();
                    }
                    object2 = object2 != null ? (object != null ? ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfo.view, new PropertyValuesHolder[]{object2, object}) : ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfo.view, new PropertyValuesHolder[]{object2})) : ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfo.view, new PropertyValuesHolder[]{object});
                    ((ObjectAnimator)object2).setDuration(150L);
                    ((ObjectAnimator)object2).start();
                    object = new ItemAnimationInfo(n, menuItemLayoutInfo, (Animator)object2, 0);
                    this.mRunningItemAnimations.add((ItemAnimationInfo)object);
                    ((Animator)object2).addListener(new AnimatorListenerAdapter(){

                        @Override
                        public void onAnimationEnd(Animator animator2) {
                            for (int i = 0; i < ActionMenuPresenter.this.mRunningItemAnimations.size(); ++i) {
                                if (((ItemAnimationInfo)ActionMenuPresenter.access$900((ActionMenuPresenter)ActionMenuPresenter.this).get((int)i)).animator != animator2) continue;
                                ActionMenuPresenter.this.mRunningItemAnimations.remove(i);
                                break;
                            }
                        }
                    });
                }
                this.mPostLayoutItems.remove(n);
                continue;
            }
            f2 = 1.0f;
            for (n2 = 0; n2 < this.mRunningItemAnimations.size(); ++n2) {
                object2 = this.mRunningItemAnimations.get(n2);
                f = f2;
                if (((ItemAnimationInfo)object2).id == n) {
                    f = f2;
                    if (((ItemAnimationInfo)object2).animType == 1) {
                        f = object2.menuItemLayoutInfo.view.getAlpha();
                        ((ItemAnimationInfo)object2).animator.cancel();
                    }
                }
                f2 = f;
            }
            object = ObjectAnimator.ofFloat(((MenuItemLayoutInfo)object3).view, View.ALPHA, f2, 0.0f);
            ((ViewGroup)((Object)this.mMenuView)).getOverlay().add(((MenuItemLayoutInfo)object3).view);
            ((ObjectAnimator)object).setDuration(150L);
            ((ObjectAnimator)object).start();
            object2 = new ItemAnimationInfo(n, (MenuItemLayoutInfo)object3, (Animator)object, 2);
            this.mRunningItemAnimations.add((ItemAnimationInfo)object2);
            ((Animator)object).addListener(new AnimatorListenerAdapter((MenuItemLayoutInfo)object3){
                final /* synthetic */ MenuItemLayoutInfo val$menuItemLayoutInfoPre;
                {
                    this.val$menuItemLayoutInfoPre = menuItemLayoutInfo;
                }

                @Override
                public void onAnimationEnd(Animator animator2) {
                    for (int i = 0; i < ActionMenuPresenter.this.mRunningItemAnimations.size(); ++i) {
                        if (((ItemAnimationInfo)ActionMenuPresenter.access$900((ActionMenuPresenter)ActionMenuPresenter.this).get((int)i)).animator != animator2) continue;
                        ActionMenuPresenter.this.mRunningItemAnimations.remove(i);
                        break;
                    }
                    ((ViewGroup)((Object)ActionMenuPresenter.this.mMenuView)).getOverlay().remove(this.val$menuItemLayoutInfoPre.view);
                }
            });
        }
        for (n3 = 0; n3 < this.mPostLayoutItems.size(); ++n3) {
            n = this.mPostLayoutItems.keyAt(n3);
            n2 = this.mPostLayoutItems.indexOfKey(n);
            if (n2 < 0) continue;
            object2 = this.mPostLayoutItems.valueAt(n2);
            f2 = 0.0f;
            for (n2 = 0; n2 < this.mRunningItemAnimations.size(); ++n2) {
                object = this.mRunningItemAnimations.get(n2);
                f = f2;
                if (((ItemAnimationInfo)object).id == n) {
                    f = f2;
                    if (((ItemAnimationInfo)object).animType == 2) {
                        f = object.menuItemLayoutInfo.view.getAlpha();
                        ((ItemAnimationInfo)object).animator.cancel();
                    }
                }
                f2 = f;
            }
            object = ObjectAnimator.ofFloat(((MenuItemLayoutInfo)object2).view, View.ALPHA, f2, 1.0f);
            ((ObjectAnimator)object).start();
            ((ObjectAnimator)object).setDuration(150L);
            object2 = new ItemAnimationInfo(n, (MenuItemLayoutInfo)object2, (Animator)object, 1);
            this.mRunningItemAnimations.add((ItemAnimationInfo)object2);
            ((Animator)object).addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    for (int i = 0; i < ActionMenuPresenter.this.mRunningItemAnimations.size(); ++i) {
                        if (((ItemAnimationInfo)ActionMenuPresenter.access$900((ActionMenuPresenter)ActionMenuPresenter.this).get((int)i)).animator != animator2) continue;
                        ActionMenuPresenter.this.mRunningItemAnimations.remove(i);
                        break;
                    }
                }
            });
        }
        this.mPreLayoutItems.clear();
        this.mPostLayoutItems.clear();
    }

    private void setupItemAnimations() {
        this.computeMenuItemAnimationInfo(true);
        ((View)((Object)this.mMenuView)).getViewTreeObserver().addOnPreDrawListener(this.mItemAnimationPreDrawListener);
    }

    @Override
    public void bindItemView(MenuItemImpl object, MenuView.ItemView itemView) {
        itemView.initialize((MenuItemImpl)object, 0);
        object = (ActionMenuView)this.mMenuView;
        itemView = (ActionMenuItemView)itemView;
        ((ActionMenuItemView)itemView).setItemInvoker((MenuBuilder.ItemInvoker)object);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        ((ActionMenuItemView)itemView).setPopupCallback(this.mPopupCallback);
    }

    @UnsupportedAppUsage
    public boolean dismissPopupMenus() {
        return this.hideOverflowMenu() | this.hideSubMenus();
    }

    @Override
    public boolean filterLeftoverView(ViewGroup viewGroup, int n) {
        if (viewGroup.getChildAt(n) == this.mOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, n);
    }

    @Override
    public boolean flagActionItems() {
        ViewGroup viewGroup;
        int n;
        Object object;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        Object object2;
        int n7;
        int n8;
        ArrayList<MenuItemImpl> arrayList;
        int n9;
        int n10;
        block39 : {
            block40 : {
                object2 = this;
                if (((ActionMenuPresenter)object2).mMenu != null) {
                    arrayList = ((ActionMenuPresenter)object2).mMenu.getVisibleItems();
                    n4 = arrayList.size();
                } else {
                    arrayList = null;
                    n4 = 0;
                }
                n9 = ((ActionMenuPresenter)object2).mMaxItems;
                n2 = ((ActionMenuPresenter)object2).mActionItemWidthLimit;
                n5 = View.MeasureSpec.makeMeasureSpec(0, 0);
                viewGroup = (ViewGroup)((Object)((ActionMenuPresenter)object2).mMenuView);
                n = 0;
                n6 = 0;
                n10 = 0;
                n7 = 0;
                for (n3 = 0; n3 < n4; ++n3) {
                    object = arrayList.get(n3);
                    if (((MenuItemImpl)object).requiresActionButton()) {
                        ++n;
                    } else if (((MenuItemImpl)object).requestsActionButton()) {
                        ++n6;
                    } else {
                        n7 = 1;
                    }
                    n8 = n9;
                    if (((ActionMenuPresenter)object2).mExpandedActionViewsExclusive) {
                        n8 = n9;
                        if (((MenuItemImpl)object).isActionViewExpanded()) {
                            n8 = 0;
                        }
                    }
                    n9 = n8;
                }
                n3 = n9;
                if (!((ActionMenuPresenter)object2).mReserveOverflow) break block39;
                if (n7 != 0) break block40;
                n3 = n9;
                if (n + n6 <= n9) break block39;
            }
            n3 = n9 - 1;
        }
        int n11 = n3 - n;
        object = ((ActionMenuPresenter)object2).mActionButtonGroups;
        ((SparseBooleanArray)object).clear();
        n6 = 0;
        n3 = 0;
        if (((ActionMenuPresenter)object2).mStrictWidthLimit) {
            n9 = ((ActionMenuPresenter)object2).mMinCellSize;
            n3 = n2 / n9;
            n6 = n9 + n2 % n9 / n3;
        }
        n8 = 0;
        n9 = n10;
        n10 = n;
        n7 = n2;
        n = n11;
        n2 = n4;
        do {
            View view;
            object2 = this;
            if (n8 >= n2) break;
            MenuItemImpl menuItemImpl = arrayList.get(n8);
            if (menuItemImpl.requiresActionButton()) {
                view = ((ActionMenuPresenter)object2).getItemView(menuItemImpl, null, viewGroup);
                if (((ActionMenuPresenter)object2).mStrictWidthLimit) {
                    n3 -= ActionMenuView.measureChildForCells(view, n6, n3, n5, 0);
                } else {
                    view.measure(n5, n5);
                }
                n11 = view.getMeasuredWidth();
                n7 -= n11;
                n4 = n9;
                if (n9 == 0) {
                    n4 = n11;
                }
                if ((n9 = menuItemImpl.getGroupId()) != 0) {
                    ((SparseBooleanArray)object).put(n9, true);
                }
                menuItemImpl.setIsActionButton(true);
                n9 = n4;
            } else if (menuItemImpl.requestsActionButton()) {
                int n12 = menuItemImpl.getGroupId();
                boolean bl = ((SparseBooleanArray)object).get(n12);
                int n13 = !(n <= 0 && !bl || n7 <= 0 || ((ActionMenuPresenter)object2).mStrictWidthLimit && n3 <= 0) ? 1 : 0;
                if (n13 != 0) {
                    view = ((ActionMenuPresenter)object2).getItemView(menuItemImpl, null, viewGroup);
                    if (((ActionMenuPresenter)object2).mStrictWidthLimit) {
                        n4 = ActionMenuView.measureChildForCells(view, n6, n3, n5, 0);
                        n3 -= n4;
                        if (n4 == 0) {
                            n13 = 0;
                        }
                    } else {
                        view.measure(n5, n5);
                    }
                    n11 = view.getMeasuredWidth();
                    n7 -= n11;
                    n4 = n9;
                    if (n9 == 0) {
                        n4 = n11;
                    }
                    if (((ActionMenuPresenter)object2).mStrictWidthLimit) {
                        n9 = n7 >= 0 ? 1 : 0;
                        n13 = n9 & n13;
                    } else {
                        n9 = n7 + n4 > 0 ? 1 : 0;
                        n13 = n9 & n13;
                    }
                } else {
                    n4 = n9;
                }
                n9 = n;
                if (n13 != 0 && n12 != 0) {
                    ((SparseBooleanArray)object).put(n12, true);
                    n = n9;
                } else if (bl) {
                    ((SparseBooleanArray)object).put(n12, false);
                    for (n11 = 0; n11 < n8; ++n11) {
                        object2 = arrayList.get(n11);
                        n = n9;
                        if (((MenuItemImpl)object2).getGroupId() == n12) {
                            n = n9;
                            if (((MenuItemImpl)object2).isActionButton()) {
                                n = n9 + 1;
                            }
                            ((MenuItemImpl)object2).setIsActionButton(false);
                        }
                        n9 = n;
                    }
                    n = n9;
                } else {
                    n = n9;
                }
                n9 = n;
                if (n13 != 0) {
                    n9 = n - 1;
                }
                menuItemImpl.setIsActionButton(n13 != 0);
                n = n9;
                n9 = n4;
            } else {
                menuItemImpl.setIsActionButton(false);
            }
            ++n8;
        } while (true);
        return true;
    }

    @Override
    public View getItemView(MenuItemImpl object, View object2, ViewGroup viewGroup) {
        View view = ((MenuItemImpl)object).getActionView();
        if (view == null || ((MenuItemImpl)object).hasCollapsibleActionView()) {
            view = super.getItemView((MenuItemImpl)object, (View)object2, viewGroup);
        }
        int n = ((MenuItemImpl)object).isActionViewExpanded() ? 8 : 0;
        view.setVisibility(n);
        object = (ActionMenuView)viewGroup;
        object2 = view.getLayoutParams();
        if (!((ActionMenuView)object).checkLayoutParams((ViewGroup.LayoutParams)object2)) {
            view.setLayoutParams(((ActionMenuView)object).generateLayoutParams((ViewGroup.LayoutParams)object2));
        }
        return view;
    }

    @Override
    public MenuView getMenuView(ViewGroup object) {
        MenuView menuView = this.mMenuView;
        if (menuView != (object = super.getMenuView((ViewGroup)object))) {
            ((ActionMenuView)object).setPresenter(this);
            if (menuView != null) {
                ((View)((Object)menuView)).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            }
            ((View)object).addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
        return object;
    }

    public Drawable getOverflowIcon() {
        OverflowMenuButton overflowMenuButton = this.mOverflowButton;
        if (overflowMenuButton != null) {
            return overflowMenuButton.getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((View)((Object)this.mMenuView)).removeCallbacks(this.mPostedOpenRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup != null) {
            overflowPopup.dismiss();
            return true;
        }
        return false;
    }

    public boolean hideSubMenus() {
        ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
        if (actionButtonSubmenu != null) {
            actionButtonSubmenu.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void initForMenu(Context object, MenuBuilder object2) {
        super.initForMenu((Context)object, (MenuBuilder)object2);
        object2 = ((Context)object).getResources();
        object = ActionBarPolicy.get((Context)object);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = ((ActionBarPolicy)object).showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            this.mWidthLimit = ((ActionBarPolicy)object).getEmbeddedMenuWidthLimit();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ((ActionBarPolicy)object).getMaxActionButtons();
        }
        int n = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
                if (this.mPendingOverflowIconSet) {
                    this.mOverflowButton.setImageDrawable(this.mPendingOverflowIcon);
                    this.mPendingOverflowIcon = null;
                    this.mPendingOverflowIconSet = false;
                }
                int n2 = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.mOverflowButton.measure(n2, n2);
            }
            n -= this.mOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
        }
        this.mActionItemWidthLimit = n;
        this.mMinCellSize = (int)(object2.getDisplayMetrics().density * 56.0f);
    }

    public boolean isOverflowMenuShowPending() {
        boolean bl = this.mPostedOpenRunnable != null || this.isOverflowMenuShowing();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isOverflowMenuShowing() {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        boolean bl = overflowPopup != null && overflowPopup.isShowing();
        return bl;
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override
    public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
        this.dismissPopupMenus();
        super.onCloseMenu(menuBuilder, bl);
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    @UnsupportedAppUsage
    @Override
    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)object;
        if (((SavedState)object).openSubMenuId > 0 && (object = this.mMenu.findItem(((SavedState)object).openSubMenuId)) != null) {
            this.onSubMenuSelected((SubMenuBuilder)object.getSubMenu());
        }
    }

    @UnsupportedAppUsage
    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    @Override
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        boolean bl;
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        Object object = subMenuBuilder;
        while (((SubMenuBuilder)object).getParentMenu() != this.mMenu) {
            object = (SubMenuBuilder)((SubMenuBuilder)object).getParentMenu();
        }
        View view = this.findViewForItem(((SubMenuBuilder)object).getItem());
        if (view == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        boolean bl2 = false;
        int n = subMenuBuilder.size();
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            object = subMenuBuilder.getItem(n2);
            if (object.isVisible() && object.getIcon() != null) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        this.mActionButtonPopup = new ActionButtonSubmenu(this.mContext, subMenuBuilder, view);
        this.mActionButtonPopup.setForceShowIcon(bl);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    @Override
    public void onSubUiVisibilityChanged(boolean bl) {
        if (bl) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void setExpandedActionViewsExclusive(boolean bl) {
        this.mExpandedActionViewsExclusive = bl;
    }

    public void setItemLimit(int n) {
        this.mMaxItems = n;
        this.mMaxItemsSet = true;
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        if (actionMenuView != this.mMenuView) {
            if (this.mMenuView != null) {
                ((View)((Object)this.mMenuView)).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            }
            this.mMenuView = actionMenuView;
            actionMenuView.initialize(this.mMenu);
            actionMenuView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
    }

    public void setOverflowIcon(Drawable drawable2) {
        OverflowMenuButton overflowMenuButton = this.mOverflowButton;
        if (overflowMenuButton != null) {
            overflowMenuButton.setImageDrawable(drawable2);
        } else {
            this.mPendingOverflowIconSet = true;
            this.mPendingOverflowIcon = drawable2;
        }
    }

    public void setReserveOverflow(boolean bl) {
        this.mReserveOverflow = bl;
        this.mReserveOverflowSet = true;
    }

    public void setWidthLimit(int n, boolean bl) {
        this.mWidthLimit = n;
        this.mStrictWidthLimit = bl;
        this.mWidthLimitSet = true;
    }

    @Override
    public boolean shouldIncludeItem(int n, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    public boolean showOverflowMenu() {
        if (this.mReserveOverflow && !this.isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
            this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mOverflowButton, true));
            ((View)((Object)this.mMenuView)).post(this.mPostedOpenRunnable);
            super.onSubMenuSelected(null);
            return true;
        }
        return false;
    }

    @Override
    public void updateMenuView(boolean bl) {
        int n;
        int n2;
        Object object = (ViewGroup)((View)((Object)this.mMenuView)).getParent();
        super.updateMenuView(bl);
        ((View)((Object)this.mMenuView)).requestLayout();
        if (this.mMenu != null) {
            object = this.mMenu.getActionItems();
            n = ((ArrayList)object).size();
            for (n2 = 0; n2 < n; ++n2) {
                ActionProvider actionProvider = ((ArrayList)object).get(n2).getActionProvider();
                if (actionProvider == null) continue;
                actionProvider.setSubUiVisibilityListener(this);
            }
        }
        object = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        n2 = n = 0;
        if (this.mReserveOverflow) {
            n2 = n;
            if (object != null) {
                n = ((ArrayList)object).size();
                n2 = 0;
                if (n == 1) {
                    n2 = ((MenuItemImpl)((ArrayList)object).get(0)).isActionViewExpanded() ^ true;
                } else if (n > 0) {
                    n2 = 1;
                }
            }
        }
        if (n2 != 0) {
            if (this.mOverflowButton == null) {
                this.mOverflowButton = new OverflowMenuButton(this.mSystemContext);
            }
            if ((object = (ViewGroup)this.mOverflowButton.getParent()) != this.mMenuView) {
                if (object != null) {
                    ((ViewGroup)object).removeView(this.mOverflowButton);
                }
                object = (ActionMenuView)this.mMenuView;
                ((ViewGroup)object).addView((View)this.mOverflowButton, ((ActionMenuView)object).generateOverflowButtonLayoutParams());
            }
        } else {
            object = this.mOverflowButton;
            if (object != null && ((View)object).getParent() == this.mMenuView) {
                ((ViewGroup)((Object)this.mMenuView)).removeView(this.mOverflowButton);
            }
        }
        ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    private class ActionButtonSubmenu
    extends MenuPopupHelper {
        public ActionButtonSubmenu(Context object, SubMenuBuilder subMenuBuilder, View view) {
            super((Context)object, subMenuBuilder, view, false, 16843844);
            if (!((MenuItemImpl)subMenuBuilder.getItem()).isActionButton()) {
                object = ActionMenuPresenter.this.mOverflowButton == null ? (View)((Object)ActionMenuPresenter.this.mMenuView) : ActionMenuPresenter.this.mOverflowButton;
                this.setAnchorView((View)object);
            }
            this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        protected void onDismiss() {
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class ActionMenuPopupCallback
    extends ActionMenuItemView.PopupCallback {
        private ActionMenuPopupCallback() {
        }

        @Override
        public ShowableListMenu getPopup() {
            MenuPopup menuPopup = ActionMenuPresenter.this.mActionButtonPopup != null ? ActionMenuPresenter.this.mActionButtonPopup.getPopup() : null;
            return menuPopup;
        }
    }

    private static class ItemAnimationInfo {
        static final int FADE_IN = 1;
        static final int FADE_OUT = 2;
        static final int MOVE = 0;
        int animType;
        Animator animator;
        int id;
        MenuItemLayoutInfo menuItemLayoutInfo;

        ItemAnimationInfo(int n, MenuItemLayoutInfo menuItemLayoutInfo, Animator animator2, int n2) {
            this.id = n;
            this.menuItemLayoutInfo = menuItemLayoutInfo;
            this.animator = animator2;
            this.animType = n2;
        }
    }

    private static class MenuItemLayoutInfo {
        int left;
        int top;
        View view;

        MenuItemLayoutInfo(View view, boolean bl) {
            this.left = view.getLeft();
            this.top = view.getTop();
            if (bl) {
                this.left = (int)((float)this.left + view.getTranslationX());
                this.top = (int)((float)this.top + view.getTranslationY());
            }
            this.view = view;
        }
    }

    private class OpenOverflowRunnable
    implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override
        public void run() {
            View view;
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            if ((view = (View)((Object)ActionMenuPresenter.this.mMenuView)) != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class OverflowMenuButton
    extends ImageButton
    implements ActionMenuView.ActionMenuChildView {
        public OverflowMenuButton(Context context) {
            super(context, null, 16843510);
            this.setClickable(true);
            this.setFocusable(true);
            this.setVisibility(0);
            this.setEnabled(true);
            this.setOnTouchListener(new ForwardingListener(this){

                @Override
                public ShowableListMenu getPopup() {
                    if (ActionMenuPresenter.this.mOverflowPopup == null) {
                        return null;
                    }
                    return ActionMenuPresenter.this.mOverflowPopup.getPopup();
                }

                @Override
                public boolean onForwardingStarted() {
                    ActionMenuPresenter.this.showOverflowMenu();
                    return true;
                }

                @Override
                public boolean onForwardingStopped() {
                    if (ActionMenuPresenter.this.mPostedOpenRunnable != null) {
                        return false;
                    }
                    ActionMenuPresenter.this.hideOverflowMenu();
                    return true;
                }
            });
        }

        @Override
        public boolean needsDividerAfter() {
            return false;
        }

        @Override
        public boolean needsDividerBefore() {
            return false;
        }

        @Override
        public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
            accessibilityNodeInfo.setCanOpenPopup(true);
        }

        @Override
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            this.playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        @Override
        protected boolean setFrame(int n, int n2, int n3, int n4) {
            boolean bl = super.setFrame(n, n2, n3, n4);
            Drawable drawable2 = this.getDrawable();
            Drawable drawable3 = this.getBackground();
            if (drawable2 != null && drawable3 != null) {
                int n5 = this.getWidth();
                n4 = this.getHeight();
                n = Math.max(n5, n4) / 2;
                int n6 = this.getPaddingLeft();
                int n7 = this.getPaddingRight();
                n3 = this.getPaddingTop();
                n2 = this.getPaddingBottom();
                n6 = (n5 + (n6 - n7)) / 2;
                n2 = (n4 + (n3 - n2)) / 2;
                drawable3.setHotspotBounds(n6 - n, n2 - n, n6 + n, n2 + n);
            }
            return bl;
        }

    }

    private class OverflowPopup
    extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean bl) {
            super(context, menuBuilder, view, bl, 16843844);
            this.setGravity(8388613);
            this.setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override
        protected void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    private class PopupPresenterCallback
    implements MenuPresenter.Callback {
        private PopupPresenterCallback() {
        }

        @Override
        public void onCloseMenu(MenuBuilder menuBuilder, boolean bl) {
            MenuPresenter.Callback callback;
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            if ((callback = ActionMenuPresenter.this.getCallback()) != null) {
                callback.onCloseMenu(menuBuilder, bl);
            }
        }

        @Override
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            boolean bl = false;
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                bl = callback.onOpenSubMenu(menuBuilder);
            }
            return bl;
        }
    }

    private static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int openSubMenuId;

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.openSubMenuId);
        }

    }

}

