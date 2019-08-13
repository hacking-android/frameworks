/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import android.view.View;

public abstract class ClickableSpan
extends CharacterStyle
implements UpdateAppearance {
    private static int sIdCounter = 0;
    private int mId;

    public ClickableSpan() {
        int n = sIdCounter;
        sIdCounter = n + 1;
        this.mId = n;
    }

    public int getId() {
        return this.mId;
    }

    public abstract void onClick(View var1);

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(textPaint.linkColor);
        textPaint.setUnderlineText(true);
    }
}

