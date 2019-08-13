/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public abstract class CharacterStyle {
    public static CharacterStyle wrap(CharacterStyle characterStyle) {
        if (characterStyle instanceof MetricAffectingSpan) {
            return new MetricAffectingSpan.Passthrough((MetricAffectingSpan)characterStyle);
        }
        return new Passthrough(characterStyle);
    }

    public CharacterStyle getUnderlying() {
        return this;
    }

    public abstract void updateDrawState(TextPaint var1);

    private static class Passthrough
    extends CharacterStyle {
        private CharacterStyle mStyle;

        public Passthrough(CharacterStyle characterStyle) {
            this.mStyle = characterStyle;
        }

        @Override
        public CharacterStyle getUnderlying() {
            return this.mStyle.getUnderlying();
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            this.mStyle.updateDrawState(textPaint);
        }
    }

}

