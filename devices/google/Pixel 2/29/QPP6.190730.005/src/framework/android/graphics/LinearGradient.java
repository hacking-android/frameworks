/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Shader;

public class LinearGradient
extends Shader {
    @UnsupportedAppUsage
    private int mColor0;
    @UnsupportedAppUsage
    private int mColor1;
    private final long[] mColorLongs;
    @UnsupportedAppUsage
    private int[] mColors;
    @UnsupportedAppUsage
    private float[] mPositions;
    @UnsupportedAppUsage
    private Shader.TileMode mTileMode;
    @UnsupportedAppUsage
    private float mX0;
    @UnsupportedAppUsage
    private float mX1;
    @UnsupportedAppUsage
    private float mY0;
    @UnsupportedAppUsage
    private float mY1;

    public LinearGradient(float f, float f2, float f3, float f4, int n, int n2, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, Color.pack(n), Color.pack(n2), tileMode);
    }

    public LinearGradient(float f, float f2, float f3, float f4, long l, long l2, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, new long[]{l, l2}, null, tileMode);
    }

    public LinearGradient(float f, float f2, float f3, float f4, int[] arrn, float[] arrf, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, LinearGradient.convertColors(arrn), arrf, tileMode, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public LinearGradient(float f, float f2, float f3, float f4, long[] arrl, float[] arrf, Shader.TileMode tileMode) {
        this(f, f2, f3, f4, (long[])arrl.clone(), arrf, tileMode, LinearGradient.detectColorSpace(arrl));
    }

    private LinearGradient(float f, float f2, float f3, float f4, long[] object, float[] arrf, Shader.TileMode tileMode, ColorSpace colorSpace) {
        super(colorSpace);
        if (arrf != null && ((long[])object).length != arrf.length) {
            throw new IllegalArgumentException("color and position arrays must be of equal length");
        }
        this.mX0 = f;
        this.mY0 = f2;
        this.mX1 = f3;
        this.mY1 = f4;
        this.mColorLongs = object;
        object = arrf != null ? (float[])arrf.clone() : null;
        this.mPositions = object;
        this.mTileMode = tileMode;
    }

    private native long nativeCreate(long var1, float var3, float var4, float var5, float var6, long[] var7, float[] var8, int var9, long var10);

    @Override
    long createNativeInstance(long l) {
        return this.nativeCreate(l, this.mX0, this.mY0, this.mX1, this.mY1, this.mColorLongs, this.mPositions, this.mTileMode.nativeInt, this.colorSpace().getNativeInstance());
    }
}

