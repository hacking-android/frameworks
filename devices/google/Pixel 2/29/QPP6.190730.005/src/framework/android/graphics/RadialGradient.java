/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Shader;

public class RadialGradient
extends Shader {
    @UnsupportedAppUsage
    private int mCenterColor;
    private final long[] mColorLongs;
    @UnsupportedAppUsage
    private int[] mColors;
    @UnsupportedAppUsage
    private int mEdgeColor;
    @UnsupportedAppUsage
    private float[] mPositions;
    @UnsupportedAppUsage
    private float mRadius;
    @UnsupportedAppUsage
    private Shader.TileMode mTileMode;
    @UnsupportedAppUsage
    private float mX;
    @UnsupportedAppUsage
    private float mY;

    public RadialGradient(float f, float f2, float f3, int n, int n2, Shader.TileMode tileMode) {
        this(f, f2, f3, Color.pack(n), Color.pack(n2), tileMode);
    }

    public RadialGradient(float f, float f2, float f3, long l, long l2, Shader.TileMode tileMode) {
        this(f, f2, f3, new long[]{l, l2}, null, tileMode);
    }

    public RadialGradient(float f, float f2, float f3, int[] arrn, float[] arrf, Shader.TileMode tileMode) {
        this(f, f2, f3, RadialGradient.convertColors(arrn), arrf, tileMode, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public RadialGradient(float f, float f2, float f3, long[] arrl, float[] arrf, Shader.TileMode tileMode) {
        this(f, f2, f3, (long[])arrl.clone(), arrf, tileMode, RadialGradient.detectColorSpace(arrl));
    }

    private RadialGradient(float f, float f2, float f3, long[] object, float[] arrf, Shader.TileMode tileMode, ColorSpace colorSpace) {
        super(colorSpace);
        if (!(f3 <= 0.0f)) {
            if (arrf != null && ((long[])object).length != arrf.length) {
                throw new IllegalArgumentException("color and position arrays must be of equal length");
            }
            this.mX = f;
            this.mY = f2;
            this.mRadius = f3;
            this.mColorLongs = object;
            object = arrf != null ? (float[])arrf.clone() : null;
            this.mPositions = object;
            this.mTileMode = tileMode;
            return;
        }
        throw new IllegalArgumentException("radius must be > 0");
    }

    private static native long nativeCreate(long var0, float var2, float var3, float var4, long[] var5, float[] var6, int var7, long var8);

    @Override
    long createNativeInstance(long l) {
        return RadialGradient.nativeCreate(l, this.mX, this.mY, this.mRadius, this.mColorLongs, this.mPositions, this.mTileMode.nativeInt, this.colorSpace().getNativeInstance());
    }
}

