/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuView;
import android.widget.ForwardingListener;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.ShowableListMenu;
import java.util.List;

public class ActionMenuItemView
extends TextView
implements MenuView.ItemView,
View.OnClickListener,
ActionMenuView.ActionMenuChildView {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    private MenuItemImpl mItemData;
    private MenuBuilder.ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    private PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ActionMenuItemView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        Resources resources = ((Context)object).getResources();
        this.mAllowTextWithIcon = this.shouldAllowTextWithIcon();
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, n, n2);
        this.mMinWidth = ((TypedArray)object).getDimensionPixelSize(0, 0);
        ((TypedArray)object).recycle();
        this.mMaxIconSize = (int)(32.0f * resources.getDisplayMetrics().density + 0.5f);
        this.setOnClickListener(this);
        this.mSavedPaddingLeft = -1;
        this.setSaveEnabled(false);
    }

    private boolean shouldAllowTextWithIcon() {
        Configuration configuration = this.getContext().getResources().getConfiguration();
        int n = configuration.screenWidthDp;
        int n2 = configuration.screenHeightDp;
        boolean bl = n >= 480 || n >= 640 && n2 >= 480 || configuration.orientation == 2;
        return bl;
    }

    private void updateTextButtonVisibility() {
        boolean bl = TextUtils.isEmpty(this.mTitle);
        boolean bl2 = true;
        if (!(this.mIcon == null || this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat))) {
            bl2 = false;
        }
        bl2 = (bl ^ true) & bl2;
        Object var3_3 = null;
        CharSequence charSequence = bl2 ? this.mTitle : null;
        this.setText(charSequence);
        charSequence = this.mItemData.getContentDescription();
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = bl2 ? null : this.mItemData.getTitle();
            this.setContentDescription(charSequence);
        } else {
            this.setContentDescription(charSequence);
        }
        charSequence = this.mItemData.getTooltipText();
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = bl2 ? var3_3 : this.mItemData.getTitle();
            this.setTooltipText(charSequence);
        } else {
            this.setTooltipText(charSequence);
        }
    }

    @Override
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.onHoverEvent(motionEvent);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @UnsupportedAppUsage
    public boolean hasText() {
        return TextUtils.isEmpty(this.getText()) ^ true;
    }

    @Override
    public void initialize(MenuItemImpl menuItemImpl, int n) {
        this.mItemData = menuItemImpl;
        this.setIcon(menuItemImpl.getIcon());
        this.setTitle(menuItemImpl.getTitleForItemView(this));
        this.setId(menuItemImpl.getItemId());
        n = menuItemImpl.isVisible() ? 0 : 8;
        this.setVisibility(n);
        this.setEnabled(menuItemImpl.isEnabled());
        if (menuItemImpl.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    @Override
    public boolean needsDividerAfter() {
        return this.hasText();
    }

    @Override
    public boolean needsDividerBefore() {
        boolean bl = this.hasText() && this.mItemData.getIcon() == null;
        return bl;
    }

    @Override
    public void onClick(View object) {
        object = this.mItemInvoker;
        if (object != null) {
            object.invokeItem(this.mItemData);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mAllowTextWithIcon = this.shouldAllowTextWithIcon();
        this.updateTextButtonVisibility();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        boolean bl = this.hasText();
        if (bl && (n3 = this.mSavedPaddingLeft) >= 0) {
            super.setPadding(n3, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
        super.onMeasure(n, n2);
        n3 = View.MeasureSpec.getMode(n);
        n = View.MeasureSpec.getSize(n);
        int n4 = this.getMeasuredWidth();
        n = n3 == Integer.MIN_VALUE ? Math.min(n, this.mMinWidth) : this.mMinWidth;
        if (n3 != 1073741824 && this.mMinWidth > 0 && n4 < n) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), n2);
        }
        if (!bl && this.mIcon != null) {
            super.setPadding((this.getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        }
    }

    @Override
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        CharSequence charSequence = this.getContentDescription();
        if (!TextUtils.isEmpty(charSequence)) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ForwardingListener forwardingListener;
        if (this.mItemData.hasSubMenu() && (forwardingListener = this.mForwardingListener) != null && forwardingListener.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean prefersCondensedTitle() {
        return true;
    }

    @Override
    public void setCheckable(boolean bl) {
    }

    @Override
    public void setChecked(boolean bl) {
    }

    public void setExpandedFormat(boolean bl) {
        if (this.mExpandedFormat != bl) {
            this.mExpandedFormat = bl;
            MenuItemImpl menuItemImpl = this.mItemData;
            if (menuItemImpl != null) {
                menuItemImpl.actionFormatChanged();
            }
        }
    }

    @Override
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        if (drawable2 != null) {
            float f;
            int n = drawable2.getIntrinsicWidth();
            int n2 = drawable2.getIntrinsicHeight();
            int n3 = this.mMaxIconSize;
            int n4 = n;
            int n5 = n2;
            if (n > n3) {
                f = (float)n3 / (float)n;
                n4 = this.mMaxIconSize;
                n5 = (int)((float)n2 * f);
            }
            n3 = this.mMaxIconSize;
            n = n4;
            n2 = n5;
            if (n5 > n3) {
                f = (float)n3 / (float)n5;
                n2 = this.mMaxIconSize;
                n = (int)((float)n4 * f);
            }
            drawable2.setBounds(0, 0, n, n2);
        }
        this.setCompoundDrawables(drawable2, null, null, null);
        this.updateTextButtonVisibility();
    }

    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    @Override
    public void setPadding(int n, int n2, int n3, int n4) {
        this.mSavedPaddingLeft = n;
        super.setPadding(n, n2, n3, n4);
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    @Override
    public void setShortcut(boolean bl, char c) {
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        this.updateTextButtonVisibility();
    }

    @Override
    public boolean showsIcon() {
        return true;
    }

    private class ActionMenuItemForwardingListener
    extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super(ActionMenuItemView.this);
        }

        @Override
        public ShowableListMenu getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        @Override
        protected boolean onForwardingStarted() {
            Object object = ActionMenuItemView.this.mItemInvoker;
            boolean bl = false;
            if (object != null && ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
                object = this.getPopup();
                boolean bl2 = bl;
                if (object != null) {
                    bl2 = bl;
                    if (object.isShowing()) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            return false;
        }
    }

    public static abstract class PopupCallback {
        public abstract ShowableListMenu getPopup();
    }

}

