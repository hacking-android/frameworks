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
import android.util.Log;
import android.view.AbsSavedState;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.Checkable;
import com.android.internal.R;

public abstract class CompoundButton
extends Button
implements Checkable {
    private static final int[] CHECKED_STATE_SET;
    private static final String LOG_TAG;
    @UnsupportedAppUsage
    private boolean mBroadcasting;
    private BlendMode mButtonBlendMode = null;
    @UnsupportedAppUsage
    private Drawable mButtonDrawable;
    private ColorStateList mButtonTintList = null;
    private boolean mChecked;
    private boolean mCheckedFromResource = false;
    private boolean mHasButtonBlendMode = false;
    private boolean mHasButtonTint = false;
    @UnsupportedAppUsage
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;

    static {
        LOG_TAG = CompoundButton.class.getSimpleName();
        CHECKED_STATE_SET = new int[]{16842912};
    }

    public CompoundButton(Context context) {
        this(context, null);
    }

    public CompoundButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompoundButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public CompoundButton(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.CompoundButton, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.CompoundButton, attributeSet, typedArray, n, n2);
        object = typedArray.getDrawable(1);
        if (object != null) {
            this.setButtonDrawable((Drawable)object);
        }
        if (typedArray.hasValue(3)) {
            this.mButtonBlendMode = Drawable.parseBlendMode(typedArray.getInt(3, -1), this.mButtonBlendMode);
            this.mHasButtonBlendMode = true;
        }
        if (typedArray.hasValue(2)) {
            this.mButtonTintList = typedArray.getColorStateList(2);
            this.mHasButtonTint = true;
        }
        this.setChecked(typedArray.getBoolean(0, false));
        this.mCheckedFromResource = true;
        typedArray.recycle();
        this.applyButtonTint();
    }

    private void applyButtonTint() {
        if (this.mButtonDrawable != null && (this.mHasButtonTint || this.mHasButtonBlendMode)) {
            this.mButtonDrawable = this.mButtonDrawable.mutate();
            if (this.mHasButtonTint) {
                this.mButtonDrawable.setTintList(this.mButtonTintList);
            }
            if (this.mHasButtonBlendMode) {
                this.mButtonDrawable.setTintBlendMode(this.mButtonBlendMode);
            }
            if (this.mButtonDrawable.isStateful()) {
                this.mButtonDrawable.setState(this.getDrawableState());
            }
        }
    }

    @Override
    public void autofill(AutofillValue autofillValue) {
        if (!this.isEnabled()) {
            return;
        }
        if (!autofillValue.isToggle()) {
            String string2 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(autofillValue);
            stringBuilder.append(" could not be autofilled into ");
            stringBuilder.append(this);
            Log.w(string2, stringBuilder.toString());
            return;
        }
        this.setChecked(autofillValue.getToggleValue());
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mButtonDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mButtonDrawable;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("checked", this.isChecked());
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return CompoundButton.class.getName();
    }

    @Override
    public int getAutofillType() {
        int n = this.isEnabled() ? 2 : 0;
        return n;
    }

    @Override
    public AutofillValue getAutofillValue() {
        AutofillValue autofillValue = this.isEnabled() ? AutofillValue.forToggle(this.isChecked()) : null;
        return autofillValue;
    }

    public Drawable getButtonDrawable() {
        return this.mButtonDrawable;
    }

    public BlendMode getButtonTintBlendMode() {
        return this.mButtonBlendMode;
    }

    public ColorStateList getButtonTintList() {
        return this.mButtonTintList;
    }

    public PorterDuff.Mode getButtonTintMode() {
        Enum enum_ = this.mButtonBlendMode;
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    @Override
    public int getCompoundPaddingLeft() {
        int n;
        int n2 = n = super.getCompoundPaddingLeft();
        if (!this.isLayoutRtl()) {
            Drawable drawable2 = this.mButtonDrawable;
            n2 = n;
            if (drawable2 != null) {
                n2 = n + drawable2.getIntrinsicWidth();
            }
        }
        return n2;
    }

    @Override
    public int getCompoundPaddingRight() {
        int n;
        int n2 = n = super.getCompoundPaddingRight();
        if (this.isLayoutRtl()) {
            Drawable drawable2 = this.mButtonDrawable;
            n2 = n;
            if (drawable2 != null) {
                n2 = n + drawable2.getIntrinsicWidth();
            }
        }
        return n2;
    }

    @Override
    public int getHorizontalOffsetForDrawables() {
        Drawable drawable2 = this.mButtonDrawable;
        int n = drawable2 != null ? drawable2.getIntrinsicWidth() : 0;
        return n;
    }

    @ViewDebug.ExportedProperty
    @Override
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable2 = this.mButtonDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override
    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 1);
        if (this.isChecked()) {
            CompoundButton.mergeDrawableStates(arrn, CHECKED_STATE_SET);
        }
        return arrn;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int n;
        int n2;
        Drawable drawable2 = this.mButtonDrawable;
        if (drawable2 != null) {
            n = this.getGravity() & 112;
            n2 = drawable2.getIntrinsicHeight();
            int n3 = drawable2.getIntrinsicWidth();
            n = n != 16 ? (n != 80 ? 0 : this.getHeight() - n2) : (this.getHeight() - n2) / 2;
            int n4 = n + n2;
            n2 = this.isLayoutRtl() ? this.getWidth() - n3 : 0;
            if (this.isLayoutRtl()) {
                n3 = this.getWidth();
            }
            drawable2.setBounds(n2, n, n3, n4);
            Drawable drawable3 = this.getBackground();
            if (drawable3 != null) {
                drawable3.setHotspotBounds(n2, n, n3, n4);
            }
        }
        super.onDraw(canvas);
        if (drawable2 != null) {
            n = this.mScrollX;
            n2 = this.mScrollY;
            if (n == 0 && n2 == 0) {
                drawable2.draw(canvas);
            } else {
                canvas.translate(n, n2);
                drawable2.draw(canvas);
                canvas.translate(-n, -n2);
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
    protected void onProvideStructure(ViewStructure viewStructure, int n, int n2) {
        super.onProvideStructure(viewStructure, n, n2);
        if (n == 1) {
            viewStructure.setDataIsSensitive(true ^ this.mCheckedFromResource);
        }
    }

    @Override
    public void onResolveDrawables(int n) {
        super.onResolveDrawables(n);
        Drawable drawable2 = this.mButtonDrawable;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.setChecked(((SavedState)parcelable).checked);
        this.requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.isChecked();
        return savedState;
    }

    @Override
    public boolean performClick() {
        this.toggle();
        boolean bl = super.performClick();
        if (!bl) {
            this.playSoundEffect(0);
        }
        return bl;
    }

    public void setButtonDrawable(int n) {
        Drawable drawable2 = n != 0 ? this.getContext().getDrawable(n) : null;
        this.setButtonDrawable(drawable2);
    }

    public void setButtonDrawable(Drawable drawable2) {
        Drawable drawable3 = this.mButtonDrawable;
        if (drawable3 != drawable2) {
            if (drawable3 != null) {
                drawable3.setCallback(null);
                this.unscheduleDrawable(this.mButtonDrawable);
            }
            this.mButtonDrawable = drawable2;
            if (drawable2 != null) {
                drawable2.setCallback(this);
                drawable2.setLayoutDirection(this.getLayoutDirection());
                if (drawable2.isStateful()) {
                    drawable2.setState(this.getDrawableState());
                }
                boolean bl = this.getVisibility() == 0;
                drawable2.setVisible(bl, false);
                this.setMinHeight(drawable2.getIntrinsicHeight());
                this.applyButtonTint();
            }
        }
    }

    public void setButtonTintBlendMode(BlendMode blendMode) {
        this.mButtonBlendMode = blendMode;
        this.mHasButtonBlendMode = true;
        this.applyButtonTint();
    }

    public void setButtonTintList(ColorStateList colorStateList) {
        this.mButtonTintList = colorStateList;
        this.mHasButtonTint = true;
        this.applyButtonTint();
    }

    public void setButtonTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setButtonTintBlendMode((BlendMode)enum_);
    }

    @Override
    public void setChecked(boolean bl) {
        if (this.mChecked != bl) {
            this.mCheckedFromResource = false;
            this.mChecked = bl;
            this.refreshDrawableState();
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
            if (this.mBroadcasting) {
                return;
            }
            this.mBroadcasting = true;
            Object object = this.mOnCheckedChangeListener;
            if (object != null) {
                object.onCheckedChanged(this, this.mChecked);
            }
            if ((object = this.mOnCheckedChangeWidgetListener) != null) {
                object.onCheckedChanged(this, this.mChecked);
            }
            if ((object = this.mContext.getSystemService(AutofillManager.class)) != null) {
                ((AutofillManager)object).notifyValueChanged(this);
            }
            this.mBroadcasting = false;
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeWidgetListener = onCheckedChangeListener;
    }

    @Override
    public void toggle() {
        this.setChecked(this.mChecked ^ true);
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        boolean bl = super.verifyDrawable(drawable2) || drawable2 == this.mButtonDrawable;
        return bl;
    }

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton var1, boolean var2);
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
            stringBuilder.append("CompoundButton.SavedState{");
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

