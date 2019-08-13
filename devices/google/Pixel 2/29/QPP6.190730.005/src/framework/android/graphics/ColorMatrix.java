/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import java.util.Arrays;

public class ColorMatrix {
    private final float[] mArray = new float[20];

    public ColorMatrix() {
        this.reset();
    }

    public ColorMatrix(ColorMatrix colorMatrix) {
        System.arraycopy(colorMatrix.mArray, 0, this.mArray, 0, 20);
    }

    public ColorMatrix(float[] arrf) {
        System.arraycopy(arrf, 0, this.mArray, 0, 20);
    }

    public boolean equals(Object arrf) {
        if (!(arrf instanceof ColorMatrix)) {
            return false;
        }
        arrf = ((ColorMatrix)arrf).mArray;
        for (int i = 0; i < 20; ++i) {
            if (arrf[i] == this.mArray[i]) continue;
            return false;
        }
        return true;
    }

    public final float[] getArray() {
        return this.mArray;
    }

    public void postConcat(ColorMatrix colorMatrix) {
        this.setConcat(colorMatrix, this);
    }

    public void preConcat(ColorMatrix colorMatrix) {
        this.setConcat(this, colorMatrix);
    }

    public void reset() {
        float[] arrf = this.mArray;
        Arrays.fill(arrf, 0.0f);
        arrf[18] = 1.0f;
        arrf[12] = 1.0f;
        arrf[6] = 1.0f;
        arrf[0] = 1.0f;
    }

    public void set(ColorMatrix colorMatrix) {
        System.arraycopy(colorMatrix.mArray, 0, this.mArray, 0, 20);
    }

    public void set(float[] arrf) {
        System.arraycopy(arrf, 0, this.mArray, 0, 20);
    }

    public void setConcat(ColorMatrix arrf, ColorMatrix arrf2) {
        float[] arrf3 = arrf != this && arrf2 != this ? this.mArray : new float[20];
        arrf = arrf.mArray;
        arrf2 = arrf2.mArray;
        int n = 0;
        int n2 = 0;
        while (n2 < 20) {
            int n3 = 0;
            while (n3 < 4) {
                arrf3[n] = arrf[n2 + 0] * arrf2[n3 + 0] + arrf[n2 + 1] * arrf2[n3 + 5] + arrf[n2 + 2] * arrf2[n3 + 10] + arrf[n2 + 3] * arrf2[n3 + 15];
                ++n3;
                ++n;
            }
            arrf3[n] = arrf[n2 + 0] * arrf2[4] + arrf[n2 + 1] * arrf2[9] + arrf[n2 + 2] * arrf2[14] + arrf[n2 + 3] * arrf2[19] + arrf[n2 + 4];
            n2 += 5;
            ++n;
        }
        arrf = this.mArray;
        if (arrf3 != arrf) {
            System.arraycopy(arrf3, 0, arrf, 0, 20);
        }
    }

    public void setRGB2YUV() {
        this.reset();
        float[] arrf = this.mArray;
        arrf[0] = 0.299f;
        arrf[1] = 0.587f;
        arrf[2] = 0.114f;
        arrf[5] = -0.16874f;
        arrf[6] = -0.33126f;
        arrf[7] = 0.5f;
        arrf[10] = 0.5f;
        arrf[11] = -0.41869f;
        arrf[12] = -0.08131f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setRotate(int n, float f) {
        this.reset();
        double d = (double)f * 3.141592653589793 / 180.0;
        float f2 = (float)Math.cos(d);
        f = (float)Math.sin(d);
        if (n != 0) {
            if (n != 1) {
                if (n != 2) throw new RuntimeException();
                float[] arrf = this.mArray;
                arrf[6] = f2;
                arrf[0] = f2;
                arrf[1] = f;
                arrf[5] = -f;
                return;
            } else {
                float[] arrf = this.mArray;
                arrf[12] = f2;
                arrf[0] = f2;
                arrf[2] = -f;
                arrf[10] = f;
            }
            return;
        } else {
            float[] arrf = this.mArray;
            arrf[12] = f2;
            arrf[6] = f2;
            arrf[7] = f;
            arrf[11] = -f;
        }
    }

    public void setSaturation(float f) {
        this.reset();
        float[] arrf = this.mArray;
        float f2 = 1.0f - f;
        float f3 = 0.213f * f2;
        float f4 = 0.715f * f2;
        f2 = 0.072f * f2;
        arrf[0] = f3 + f;
        arrf[1] = f4;
        arrf[2] = f2;
        arrf[5] = f3;
        arrf[6] = f4 + f;
        arrf[7] = f2;
        arrf[10] = f3;
        arrf[11] = f4;
        arrf[12] = f2 + f;
    }

    public void setScale(float f, float f2, float f3, float f4) {
        float[] arrf = this.mArray;
        for (int i = 19; i > 0; --i) {
            arrf[i] = 0.0f;
        }
        arrf[0] = f;
        arrf[6] = f2;
        arrf[12] = f3;
        arrf[18] = f4;
    }

    public void setYUV2RGB() {
        this.reset();
        float[] arrf = this.mArray;
        arrf[2] = 1.402f;
        arrf[5] = 1.0f;
        arrf[6] = -0.34414f;
        arrf[7] = -0.71414f;
        arrf[10] = 1.0f;
        arrf[11] = 1.772f;
        arrf[12] = 0.0f;
    }
}

