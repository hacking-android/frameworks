/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class TextSwitcher
extends ViewSwitcher {
    public TextSwitcher(Context context) {
        super(context);
    }

    public TextSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof TextView) {
            super.addView(view, n, layoutParams);
            return;
        }
        throw new IllegalArgumentException("TextSwitcher children must be instances of TextView");
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TextSwitcher.class.getName();
    }

    public void setCurrentText(CharSequence charSequence) {
        ((TextView)this.getCurrentView()).setText(charSequence);
    }

    public void setText(CharSequence charSequence) {
        ((TextView)this.getNextView()).setText(charSequence);
        this.showNext();
    }
}

