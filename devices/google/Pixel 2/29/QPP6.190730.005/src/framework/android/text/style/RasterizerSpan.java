/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Rasterizer;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class RasterizerSpan
extends CharacterStyle
implements UpdateAppearance {
    private Rasterizer mRasterizer;

    public RasterizerSpan(Rasterizer rasterizer) {
        this.mRasterizer = rasterizer;
    }

    public Rasterizer getRasterizer() {
        return this.mRasterizer;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setRasterizer(this.mRasterizer);
    }
}

