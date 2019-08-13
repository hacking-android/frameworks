/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TypeEvaluator;

public class FloatEvaluator
implements TypeEvaluator<Number> {
    @Override
    public Float evaluate(float f, Number number, Number number2) {
        float f2 = number.floatValue();
        return Float.valueOf((number2.floatValue() - f2) * f + f2);
    }
}

