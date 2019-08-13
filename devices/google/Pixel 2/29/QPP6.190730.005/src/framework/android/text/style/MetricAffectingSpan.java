/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateLayout;

public abstract class MetricAffectingSpan
extends CharacterStyle
implements UpdateLayout {
    @Override
    public MetricAffectingSpan getUnderlying() {
        return this;
    }

    public abstract void updateMeasureState(TextPaint var1);

    static class Passthrough
    extends MetricAffectingSpan {
        private MetricAffectingSpan mStyle;

        Passthrough(MetricAffectingSpan metricAffectingSpan) {
            this.mStyle = metricAffectingSpan;
        }

        @Override
        public MetricAffectingSpan getUnderlying() {
            return this.mStyle.getUnderlying();
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            this.mStyle.updateDrawState(textPaint);
        }

        @Override
        public void updateMeasureState(TextPaint textPaint) {
            this.mStyle.updateMeasureState(textPaint);
        }
    }

}

