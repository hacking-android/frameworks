/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TypeEvaluator;

public class IntEvaluator
implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float f, Integer n, Integer n2) {
        int n3 = n;
        return (int)((float)n3 + (float)(n2 - n3) * f);
    }
}

