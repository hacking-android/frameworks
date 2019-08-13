/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TypeEvaluator;

public class IntArrayEvaluator
implements TypeEvaluator<int[]> {
    private int[] mArray;

    public IntArrayEvaluator() {
    }

    public IntArrayEvaluator(int[] arrn) {
        this.mArray = arrn;
    }

    @Override
    public int[] evaluate(float f, int[] arrn, int[] arrn2) {
        int[] arrn3;
        int[] arrn4 = arrn3 = this.mArray;
        if (arrn3 == null) {
            arrn4 = new int[arrn.length];
        }
        for (int i = 0; i < arrn4.length; ++i) {
            int n = arrn[i];
            int n2 = arrn2[i];
            arrn4[i] = (int)((float)n + (float)(n2 - n) * f);
        }
        return arrn4;
    }
}

