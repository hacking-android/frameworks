/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;

public class ColorMatrixColorFilter
extends ColorFilter {
    @UnsupportedAppUsage
    private final ColorMatrix mMatrix = new ColorMatrix();

    public ColorMatrixColorFilter(ColorMatrix colorMatrix) {
        this.mMatrix.set(colorMatrix);
    }

    public ColorMatrixColorFilter(float[] arrf) {
        if (arrf.length >= 20) {
            this.mMatrix.set(arrf);
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    private static native long nativeColorMatrixFilter(float[] var0);

    @Override
    long createNativeInstance() {
        return ColorMatrixColorFilter.nativeColorMatrixFilter(this.mMatrix.getArray());
    }

    public void getColorMatrix(ColorMatrix colorMatrix) {
        colorMatrix.set(this.mMatrix);
    }

    @UnsupportedAppUsage
    public void setColorMatrix(ColorMatrix colorMatrix) {
        this.discardNativeInstance();
        if (colorMatrix == null) {
            this.mMatrix.reset();
        } else {
            this.mMatrix.set(colorMatrix);
        }
    }

    @UnsupportedAppUsage
    public void setColorMatrixArray(float[] arrf) {
        block4 : {
            block3 : {
                block2 : {
                    this.discardNativeInstance();
                    if (arrf != null) break block2;
                    this.mMatrix.reset();
                    break block3;
                }
                if (arrf.length < 20) break block4;
                this.mMatrix.set(arrf);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}

