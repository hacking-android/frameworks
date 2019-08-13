/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.IconMenuView;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;

public final class IconMenuItemView
extends TextView
implements MenuView.ItemView {
    private static final int NO_ALPHA = 255;
    private static String sPrependShortcutLabel;
    private float mDisabledAlpha;
    private Drawable mIcon;
    private IconMenuView mIconMenuView;
    private MenuItemImpl mItemData;
    private MenuBuilder.ItemInvoker mItemInvoker;
    private Rect mPositionIconAvailable = new Rect();
    private Rect mPositionIconOutput = new Rect();
    private String mShortcutCaption;
    private boolean mShortcutCaptionMode;
    private int mTextAppearance;
    private Context mTextAppearanceContext;

    public IconMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IconMenuItemView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public IconMenuItemView(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        if (sPrependShortcutLabel == null) {
            sPrependShortcutLabel = this.getResources().getString(17040878);
        }
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.MenuView, n, n2);
        this.mDisabledAlpha = ((TypedArray)object).getFloat(6, 0.8f);
        this.mTextAppearance = ((TypedArray)object).getResourceId(1, -1);
        this.mTextAppearanceContext = context;
        ((TypedArray)object).recycle();
    }

    private void positionIcon() {
        if (this.mIcon == null) {
            return;
        }
        Rect rect = this.mPositionIconOutput;
        this.getLineBounds(0, rect);
        this.mPositionIconAvailable.set(0, 0, this.getWidth(), rect.top);
        int n = this.getLayoutDirection();
        Gravity.apply(8388627, this.mIcon.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight(), this.mPositionIconAvailable, this.mPositionIconOutput, n);
        this.mIcon.setBounds(this.mPositionIconOutput);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Object object = this.mItemData;
        if (object != null && this.mIcon != null) {
            int n = !((MenuItemImpl)object).isEnabled() && (this.isPressed() || !this.isFocused()) ? 1 : 0;
            object = this.mIcon;
            n = n != 0 ? (int)(this.mDisabledAlpha * 255.0f) : 255;
            ((Drawable)object).setAlpha(n);
        }
    }

    @ViewDebug.CapturedViewProperty(retrieveReturn=true)
    @Override
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @UnsupportedAppUsage
    IconMenuView.LayoutParams getTextAppropriateLayoutParams() {
        IconMenuView.LayoutParams layoutParams;
        IconMenuView.LayoutParams layoutParams2 = layoutParams = (IconMenuView.LayoutParams)this.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = new IconMenuView.LayoutParams(-1, -1);
        }
        layoutParams2.desiredWidth = (int)Layout.getDesiredWidth(this.getText(), 0, this.getText().length(), this.getPaint(), this.getTextDirectionHeuristic());
        return layoutParams2;
    }

    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n) {
        this.mItemData = menuItemImpl;
        this.initialize(menuItemImpl.getTitleForItemView(this), menuItemImpl.getIcon());
        n = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n);
        this.setEnabled(menuItemImpl.isEnabled());
    }

    void initialize(CharSequence charSequence, Drawable object) {
        this.setClickable(true);
        this.setFocusable(true);
        int n = this.mTextAppearance;
        if (n != -1) {
            this.setTextAppearance(this.mTextAppearanceContext, n);
        }
        this.setTitle(charSequence);
        this.setIcon((Drawable)object);
        object = this.mItemData;
        if (object != null) {
            if (TextUtils.isEmpty((CharSequence)(object = ((MenuItemImpl)object).getContentDescription()))) {
                this.setContentDescription(charSequence);
            } else {
                this.setContentDescription((CharSequence)object);
            }
            this.setTooltipText(this.mItemData.getTooltipText());
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.positionIcon();
    }

    @Override
    protected void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        super.onTextChanged(charSequence, n, n2, n3);
        this.setLayoutParams(this.getTextAppropriateLayoutParams());
    }

    @Override
    public boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
        if (itemInvoker != null && itemInvoker.invokeItem(this.mItemData)) {
            this.playSoundEffect(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean prefersCondensedTitle() {
        return true;
    }

    void setCaptionMode(boolean bl) {
        Object object = this.mItemData;
        if (object == null) {
            return;
        }
        bl = bl && ((MenuItemImpl)object).shouldShowShortcut();
        this.mShortcutCaptionMode = bl;
        object = this.mItemData.getTitleForItemView(this);
        if (this.mShortcutCaptionMode) {
            if (this.mShortcutCaption == null) {
                this.mShortcutCaption = this.mItemData.getShortcutLabel();
            }
            object = this.mShortcutCaption;
        }
        this.setText((CharSequence)object);
    }

    @Override
    public void setCheckable(boolean bl) {
    }

    @Override
    public void setChecked(boolean bl) {
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            this.setCompoundDrawables(null, drawable2, null, null);
            this.setGravity(81);
            this.requestLayout();
        } else {
            this.setCompoundDrawables(null, null, null, null);
            this.setGravity(17);
        }
    }

    @UnsupportedAppUsage
    void setIconMenuView(IconMenuView iconMenuView) {
        this.mIconMenuView = iconMenuView;
    }

    public void setItemData(MenuItemImpl menuItemImpl) {
        this.mItemData = menuItemImpl;
    }

    @UnsupportedAppUsage
    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    @Override
    public void setShortcut(boolean bl, char c) {
        if (this.mShortcutCaptionMode) {
            this.mShortcutCaption = null;
            this.setCaptionMode(true);
        }
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        if (this.mShortcutCaptionMode) {
            this.setCaptionMode(true);
        } else if (charSequence != null) {
            this.setText(charSequence);
        }
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        IconMenuView iconMenuView = this.mIconMenuView;
        if (iconMenuView != null) {
            iconMenuView.markStaleChildren();
        }
    }

    @Override
    public boolean showsIcon() {
        return true;
    }
}

