/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import com.android.internal.R;

public class ToggleButton
extends CompoundButton {
    private static final int NO_ALPHA = 255;
    private float mDisabledAlpha;
    private Drawable mIndicatorDrawable;
    private CharSequence mTextOff;
    private CharSequence mTextOn;

    public ToggleButton(Context context) {
        this(context, null);
    }

    public ToggleButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842827);
    }

    public ToggleButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ToggleButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ToggleButton, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.ToggleButton, attributeSet, typedArray, n, n2);
        this.mTextOn = typedArray.getText(1);
        this.mTextOff = typedArray.getText(2);
        this.mDisabledAlpha = typedArray.getFloat(0, 0.5f);
        this.syncTextState();
        typedArray.recycle();
    }

    private void syncTextState() {
        CharSequence charSequence;
        boolean bl = this.isChecked();
        if (bl && (charSequence = this.mTextOn) != null) {
            this.setText(charSequence);
        } else if (!bl && (charSequence = this.mTextOff) != null) {
            this.setText(charSequence);
        }
    }

    private void updateReferenceToIndicatorDrawable(Drawable drawable2) {
        this.mIndicatorDrawable = drawable2 instanceof LayerDrawable ? ((LayerDrawable)drawable2).findDrawableByLayerId(16908311) : null;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mIndicatorDrawable;
        if (drawable2 != null) {
            int n = this.isEnabled() ? 255 : (int)(this.mDisabledAlpha * 255.0f);
            drawable2.setAlpha(n);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ToggleButton.class.getName();
    }

    public float getDisabledAlpha() {
        return this.mDisabledAlpha;
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.updateReferenceToIndicatorDrawable(this.getBackground());
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        this.updateReferenceToIndicatorDrawable(drawable2);
    }

    @Override
    public void setChecked(boolean bl) {
        super.setChecked(bl);
        this.syncTextState();
    }

    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
    }

    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
    }
}

