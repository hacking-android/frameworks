/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class RadioButton
extends CompoundButton {
    public RadioButton(Context context) {
        this(context, null);
    }

    public RadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842878);
    }

    public RadioButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public RadioButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return RadioButton.class.getName();
    }

    @Override
    public void toggle() {
        if (!this.isChecked()) {
            super.toggle();
        }
    }
}

