/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TypeEvaluator;

public class FloatArrayEvaluator
implements TypeEvaluator<float[]> {
    private float[] mArray;

    public FloatArrayEvaluator() {
    }

    public FloatArrayEvaluator(float[] arrf) {
        this.mArray = arrf;
    }

    @Override
    public float[] evaluate(float f, float[] arrf, float[] arrf2) {
        float[] arrf3;
        float[] arrf4 = arrf3 = this.mArray;
        if (arrf3 == null) {
            arrf4 = new float[arrf.length];
        }
        for (int i = 0; i < arrf4.length; ++i) {
            float f2 = arrf[i];
            arrf4[i] = (arrf2[i] - f2) * f + f2;
        }
        return arrf4;
    }
}

