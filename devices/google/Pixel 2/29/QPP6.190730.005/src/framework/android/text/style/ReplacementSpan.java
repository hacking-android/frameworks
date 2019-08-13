/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public abstract class ReplacementSpan
extends MetricAffectingSpan {
    public abstract void draw(Canvas var1, CharSequence var2, int var3, int var4, float var5, int var6, int var7, int var8, Paint var9);

    public abstract int getSize(Paint var1, CharSequence var2, int var3, int var4, Paint.FontMetricsInt var5);

    @Override
    public void updateDrawState(TextPaint textPaint) {
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
    }
}

