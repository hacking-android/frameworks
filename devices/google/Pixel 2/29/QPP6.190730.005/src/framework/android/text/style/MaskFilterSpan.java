/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.MaskFilter;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class MaskFilterSpan
extends CharacterStyle
implements UpdateAppearance {
    private MaskFilter mFilter;

    public MaskFilterSpan(MaskFilter maskFilter) {
        this.mFilter = maskFilter;
    }

    public MaskFilter getMaskFilter() {
        return this.mFilter;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setMaskFilter(this.mFilter);
    }
}

