/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;

public class TextPaint
extends Paint {
    public int baselineShift;
    public int bgColor;
    public float density = 1.0f;
    public int[] drawableState;
    public int linkColor;
    public int underlineColor = 0;
    public float underlineThickness;

    public TextPaint() {
    }

    public TextPaint(int n) {
        super(n);
    }

    public TextPaint(Paint paint) {
        super(paint);
    }

    @Override
    public float getUnderlineThickness() {
        if (this.underlineColor != 0) {
            return this.underlineThickness;
        }
        return super.getUnderlineThickness();
    }

    public void set(TextPaint textPaint) {
        super.set(textPaint);
        this.bgColor = textPaint.bgColor;
        this.baselineShift = textPaint.baselineShift;
        this.linkColor = textPaint.linkColor;
        this.drawableState = textPaint.drawableState;
        this.density = textPaint.density;
        this.underlineColor = textPaint.underlineColor;
        this.underlineThickness = textPaint.underlineThickness;
    }

    @UnsupportedAppUsage
    public void setUnderlineText(int n, float f) {
        this.underlineColor = n;
        this.underlineThickness = f;
    }
}

