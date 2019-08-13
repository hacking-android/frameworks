/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;

public class ListMenuItemView
extends LinearLayout
implements MenuView.ItemView,
AbsListView.SelectionBoundsAdjuster {
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private CheckBox mCheckBox;
    private LinearLayout mContent;
    private boolean mForceShowIcon;
    private ImageView mGroupDivider;
    private boolean mHasListDivider;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private MenuItemImpl mItemData;
    private int mMenuType;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private Drawable mSubMenuArrow;
    private ImageView mSubMenuArrowView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    public ListMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16844018);
    }

    public ListMenuItemView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ListMenuItemView(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2, n, n2);
        object2 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.MenuView, n, n2);
        this.mBackground = ((TypedArray)object2).getDrawable(5);
        this.mTextAppearance = ((TypedArray)object2).getResourceId(1, -1);
        this.mPreserveIconSpacing = ((TypedArray)object2).getBoolean(8, false);
        this.mTextAppearanceContext = object;
        this.mSubMenuArrow = ((TypedArray)object2).getDrawable(7);
        object = ((Context)object).getTheme().obtainStyledAttributes(null, new int[]{16843049}, 16842861, 0);
        this.mHasListDivider = ((TypedArray)object).hasValue(0);
        ((TypedArray)object2).recycle();
        ((TypedArray)object).recycle();
    }

    private void addContentView(View view) {
        this.addContentView(view, -1);
    }

    private void addContentView(View view, int n) {
        LinearLayout linearLayout = this.mContent;
        if (linearLayout != null) {
            linearLayout.addView(view, n);
        } else {
            this.addView(view, n);
        }
    }

    private LayoutInflater getInflater() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
        }
        return this.mInflater;
    }

    private void insertCheckBox() {
        LayoutInflater layoutInflater = this.getInflater();
        this.mCheckBox = (CheckBox)layoutInflater.inflate(17367179, (ViewGroup)this, false);
        this.addContentView(this.mCheckBox);
    }

    private void insertIconView() {
        this.mIconView = (ImageView)this.getInflater().inflate(17367180, (ViewGroup)this, false);
        this.addContentView(this.mIconView, 0);
    }

    private void insertRadioButton() {
        LayoutInflater layoutInflater = this.getInflater();
        this.mRadioButton = (RadioButton)layoutInflater.inflate(17367182, (ViewGroup)this, false);
        this.addContentView(this.mRadioButton);
    }

    private void setSubMenuArrowVisible(boolean bl) {
        ImageView imageView = this.mSubMenuArrowView;
        if (imageView != null) {
            int n = bl ? 0 : 8;
            imageView.setVisibility(n);
        }
    }

    @Override
    public void adjustListItemSelectionBounds(Rect rect) {
        Object object = this.mGroupDivider;
        if (object != null && ((View)object).getVisibility() == 0) {
            object = (LinearLayout.LayoutParams)this.mGroupDivider.getLayoutParams();
            rect.top += this.mGroupDivider.getHeight() + ((LinearLayout.LayoutParams)object).topMargin + ((LinearLayout.LayoutParams)object).bottomMargin;
        }
    }

    @Override
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n) {
        this.mItemData = menuItemImpl;
        this.mMenuType = n;
        n = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n);
        this.setTitle(menuItemImpl.getTitleForItemView(this));
        this.setCheckable(menuItemImpl.isCheckable());
        this.setShortcut(menuItemImpl.shouldShowShortcut(), menuItemImpl.getShortcut());
        this.setIcon(menuItemImpl.getIcon());
        this.setEnabled(menuItemImpl.isEnabled());
        this.setSubMenuArrowVisible(menuItemImpl.hasSubMenu());
        this.setContentDescription(menuItemImpl.getContentDescription());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setBackgroundDrawable(this.mBackground);
        this.mTitleView = (TextView)this.findViewById(16908310);
        int n = this.mTextAppearance;
        if (n != -1) {
            this.mTitleView.setTextAppearance(this.mTextAppearanceContext, n);
        }
        this.mShortcutView = (TextView)this.findViewById(16909351);
        this.mSubMenuArrowView = (ImageView)this.findViewById(16909413);
        ImageView imageView = this.mSubMenuArrowView;
        if (imageView != null) {
            imageView.setImageDrawable(this.mSubMenuArrow);
        }
        this.mGroupDivider = (ImageView)this.findViewById(16908968);
        this.mContent = (LinearLayout)this.findViewById(16908290);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        MenuItemImpl menuItemImpl = this.mItemData;
        if (menuItemImpl != null && menuItemImpl.hasSubMenu()) {
            accessibilityNodeInfo.setCanOpenPopup(true);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams)this.mIconView.getLayoutParams();
            if (layoutParams.height > 0 && layoutParams2.width <= 0) {
                layoutParams2.width = layoutParams.height;
            }
        }
        super.onMeasure(n, n2);
    }

    @Override
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override
    public void setCheckable(boolean bl) {
        CompoundButton compoundButton;
        CompoundButton compoundButton2;
        if (!bl && this.mRadioButton == null && this.mCheckBox == null) {
            return;
        }
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                this.insertRadioButton();
            }
            compoundButton = this.mRadioButton;
            compoundButton2 = this.mCheckBox;
        } else {
            if (this.mCheckBox == null) {
                this.insertCheckBox();
            }
            compoundButton = this.mCheckBox;
            compoundButton2 = this.mRadioButton;
        }
        if (bl) {
            compoundButton.setChecked(this.mItemData.isChecked());
            int n = bl ? 0 : 8;
            if (compoundButton.getVisibility() != n) {
                compoundButton.setVisibility(n);
            }
            if (compoundButton2 != null && compoundButton2.getVisibility() != 8) {
                compoundButton2.setVisibility(8);
            }
        } else {
            compoundButton = this.mCheckBox;
            if (compoundButton != null) {
                compoundButton.setVisibility(8);
            }
            if ((compoundButton = this.mRadioButton) != null) {
                compoundButton.setVisibility(8);
            }
        }
    }

    @Override
    public void setChecked(boolean bl) {
        CompoundButton compoundButton;
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                this.insertRadioButton();
            }
            compoundButton = this.mRadioButton;
        } else {
            if (this.mCheckBox == null) {
                this.insertCheckBox();
            }
            compoundButton = this.mCheckBox;
        }
        compoundButton.setChecked(bl);
    }

    public void setForceShowIcon(boolean bl) {
        this.mForceShowIcon = bl;
        this.mPreserveIconSpacing = bl;
    }

    public void setGroupDividerEnabled(boolean bl) {
        ImageView imageView = this.mGroupDivider;
        if (imageView != null) {
            int n = !this.mHasListDivider && bl ? 0 : 8;
            imageView.setVisibility(n);
        }
    }

    @Override
    public void setIcon(Drawable drawable2) {
        boolean bl = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (!bl && !this.mPreserveIconSpacing) {
            return;
        }
        if (this.mIconView == null && drawable2 == null && !this.mPreserveIconSpacing) {
            return;
        }
        if (this.mIconView == null) {
            this.insertIconView();
        }
        if (drawable2 == null && !this.mPreserveIconSpacing) {
            this.mIconView.setVisibility(8);
        } else {
            ImageView imageView = this.mIconView;
            if (!bl) {
                drawable2 = null;
            }
            imageView.setImageDrawable(drawable2);
            if (this.mIconView.getVisibility() != 0) {
                this.mIconView.setVisibility(0);
            }
        }
    }

    @Override
    public void setShortcut(boolean bl, char c) {
        c = bl && this.mItemData.shouldShowShortcut() ? (char)'\u0000' : (char)8;
        if (c == '\u0000') {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != c) {
            this.mShortcutView.setVisibility(c);
        }
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        if (charSequence != null) {
            this.mTitleView.setText(charSequence);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    @Override
    public boolean showsIcon() {
        return this.mForceShowIcon;
    }
}

