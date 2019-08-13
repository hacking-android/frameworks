/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

class ExtractButton
extends Button {
    public ExtractButton(Context context) {
        super(context, null);
    }

    public ExtractButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    public ExtractButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ExtractButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public boolean hasWindowFocus() {
        boolean bl = this.isEnabled() && this.getVisibility() == 0;
        return bl;
    }
}

