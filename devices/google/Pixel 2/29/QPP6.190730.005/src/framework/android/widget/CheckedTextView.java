/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.TextView;
import com.android.internal.R;

public class CheckedTextView
extends TextView
implements Checkable {
    private static final int[] CHECKED_STATE_SET = new int[]{16842912};
    private int mBasePadding;
    private BlendMode mCheckMarkBlendMode = null;
    @UnsupportedAppUsage
    private Drawable mCheckMarkDrawable;
    @UnsupportedAppUsage
    private int mCheckMarkGravity = 8388613;
    private int mCheckMarkResource;
    private ColorStateList mCheckMarkTintList = null;
    private int mCheckMarkWidth;
    private boolean mChecked;
    private boolean mHasCheckMarkTint = false;
    private boolean mHasCheckMarkTintMode = false;
    private boolean mNeedRequestlayout;

    public CheckedTextView(Context context) {
        this(context, null);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843720);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public CheckedTextView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.CheckedTextView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.CheckedTextView, attributeSet, typedArray, n, n2);
        object = typedArray.getDrawable(1);
        if (object != null) {
            this.setCheckMarkDrawable((Drawable)object);
        }
        if (typedArray.hasValue(3)) {
            this.mCheckMarkBlendMode = Drawable.parseBlendMode(typedArray.getInt(3, -1), this.mCheckMarkBlendMode);
            this.mHasCheckMarkTintMode = true;
        }
        if (typedArray.hasValue(2)) {
            this.mCheckMarkTintList = typedArray.getColorStateList(2);
            this.mHasCheckMarkTint = true;
        }
        this.mCheckMarkGravity = typedArray.getInt(4, 8388613);
        this.setChecked(typedArray.getBoolean(0, false));
        typedArray.recycle();
        this.applyCheckMarkTint();
    }

    private void applyCheckMarkTint() {
        if (this.mCheckMarkDrawable != null && (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode)) {
            this.mCheckMarkDrawable = this.mCheckMarkDrawable.mutate();
            if (this.mHasCheckMarkTint) {
                this.mCheckMarkDrawable.setTintList(this.mCheckMarkTintList);
            }
            if (this.mHasCheckMarkTintMode) {
                this.mCheckMarkDrawable.setTintBlendMode(this.mCheckMarkBlendMode);
            }
            if (this.mCheckMarkDrawable.isStateful()) {
                this.mCheckMarkDrawable.setState(this.getDrawableState());
            }
        }
    }

    private boolean isCheckMarkAtStart() {
        boolean bl = (Gravity.getAbsoluteGravity(this.mCheckMarkGravity, this.getLayoutDirection()) & 7) == 3;
        return bl;
    }

    private void setBasePadding(boolean bl) {
        this.mBasePadding = bl ? this.mPaddingLeft : this.mPaddingRight;
    }

    private void setCheckMarkDrawableInternal(Drawable drawable2, int n) {
        Drawable drawable3 = this.mCheckMarkDrawable;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            this.unscheduleDrawable(this.mCheckMarkDrawable);
        }
        drawable3 = this.mCheckMarkDrawable;
        boolean bl = true;
        boolean bl2 = drawable2 != drawable3;
        this.mNeedRequestlayout = bl2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            bl2 = this.getVisibility() == 0 ? bl : false;
            drawable2.setVisible(bl2, false);
            drawable2.setState(CHECKED_STATE_SET);
            this.setMinHeight(drawable2.getIntrinsicHeight());
            this.mCheckMarkWidth = drawable2.getIntrinsicWidth();
            drawable2.setState(this.getDrawableState());
        } else {
            this.mCheckMarkWidth = 0;
        }
        this.mCheckMarkDrawable = drawable2;
        this.mCheckMarkResource = n;
        this.applyCheckMarkTint();
        this.resolvePadding();
    }

    private void updatePadding() {
        this.resetPaddingToInitialValues();
        int n = this.mCheckMarkDrawable != null ? this.mCheckMarkWidth + this.mBasePadding : this.mBasePadding;
        boolean bl = this.isCheckMarkAtStart();
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl = this.mNeedRequestlayout;
            if (this.mPaddingLeft == n) {
                bl3 = false;
            }
            this.mNeedRequestlayout = bl | bl3;
            this.mPaddingLeft = n;
        } else {
            bl = this.mNeedRequestlayout;
            bl3 = this.mPaddingRight != n ? bl2 : false;
            this.mNeedRequestlayout = bl | bl3;
            this.mPaddingRight = n;
        }
        if (this.mNeedRequestlayout) {
            this.requestLayout();
            this.mNeedRequestlayout = false;
        }
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("text:checked", this.isChecked());
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return CheckedTextView.class.getName();
    }

    public Drawable getCheckMarkDrawable() {
        return this.mCheckMarkDrawable;
    }

    public BlendMode getCheckMarkTintBlendMode() {
        return this.mCheckMarkBlendMode;
    }

    public ColorStateList getCheckMarkTintList() {
        return this.mCheckMarkTintList;
    }

    public PorterDuff.Mode getCheckMarkTintMode() {
        Enum enum_ = this.mCheckMarkBlendMode;
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    @Override
    protected void internalSetPadding(int n, int n2, int n3, int n4) {
        super.internalSetPadding(n, n2, n3, n4);
        this.setBasePadding(this.isCheckMarkAtStart());
    }

    @ViewDebug.ExportedProperty
    @Override
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 1);
        if (this.isChecked()) {
            CheckedTextView.mergeDrawableStates(arrn, CHECKED_STATE_SET);
        }
        return arrn;
    }

    @Override
    protected void onDraw(Canvas object) {
        super.onDraw((Canvas)object);
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            int n = this.getGravity() & 112;
            int n2 = drawable2.getIntrinsicHeight();
            int n3 = 0;
            if (n != 16) {
                if (n == 80) {
                    n3 = this.getHeight() - n2;
                }
            } else {
                n3 = (this.getHeight() - n2) / 2;
            }
            boolean bl = this.isCheckMarkAtStart();
            n = this.getWidth();
            int n4 = n3 + n2;
            if (bl) {
                n2 = this.mBasePadding;
                n = this.mCheckMarkWidth + n2;
            } else {
                n2 = (n -= this.mBasePadding) - this.mCheckMarkWidth;
            }
            drawable2.setBounds(this.mScrollX + n2, n3, this.mScrollX + n, n4);
            drawable2.draw((Canvas)object);
            object = this.getBackground();
            if (object != null) {
                ((Drawable)object).setHotspotBounds(this.mScrollX + n2, n3, this.mScrollX + n, n4);
            }
        }
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.mChecked);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.setChecked(((SavedState)parcelable).checked);
        this.requestLayout();
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        this.updatePadding();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.isChecked();
        return savedState;
    }

    public void setCheckMarkDrawable(int n) {
        if (n != 0 && n == this.mCheckMarkResource) {
            return;
        }
        Drawable drawable2 = n != 0 ? this.getContext().getDrawable(n) : null;
        this.setCheckMarkDrawableInternal(drawable2, n);
    }

    public void setCheckMarkDrawable(Drawable drawable2) {
        this.setCheckMarkDrawableInternal(drawable2, 0);
    }

    public void setCheckMarkTintBlendMode(BlendMode blendMode) {
        this.mCheckMarkBlendMode = blendMode;
        this.mHasCheckMarkTintMode = true;
        this.applyCheckMarkTint();
    }

    public void setCheckMarkTintList(ColorStateList colorStateList) {
        this.mCheckMarkTintList = colorStateList;
        this.mHasCheckMarkTint = true;
        this.applyCheckMarkTint();
    }

    public void setCheckMarkTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setCheckMarkTintBlendMode((BlendMode)enum_);
    }

    @Override
    public void setChecked(boolean bl) {
        if (this.mChecked != bl) {
            this.mChecked = bl;
            this.refreshDrawableState();
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    @RemotableViewMethod
    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            boolean bl = n == 0;
            drawable2.setVisible(bl, false);
        }
    }

    @Override
    public void toggle() {
        this.setChecked(this.mChecked ^ true);
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = drawable2 == this.mCheckMarkDrawable || super.verifyDrawable(drawable2);
        return bl;
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean checked;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checked = (Boolean)parcel.readValue(null);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CheckedTextView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" checked=");
            stringBuilder.append(this.checked);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeValue(this.checked);
        }

    }

}

