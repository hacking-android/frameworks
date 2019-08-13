/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Shader;

public class SweepGradient
extends Shader {
    @UnsupportedAppUsage
    private int mColor0;
    @UnsupportedAppUsage
    private int mColor1;
    private final long[] mColorLongs;
    @UnsupportedAppUsage
    private int[] mColors;
    @UnsupportedAppUsage
    private float mCx;
    @UnsupportedAppUsage
    private float mCy;
    @UnsupportedAppUsage
    private float[] mPositions;

    public SweepGradient(float f, float f2, int n, int n2) {
        this(f, f2, Color.pack(n), Color.pack(n2));
    }

    public SweepGradient(float f, float f2, long l, long l2) {
        this(f, f2, new long[]{l, l2}, null);
    }

    public SweepGradient(float f, float f2, int[] arrn, float[] arrf) {
        this(f, f2, SweepGradient.convertColors(arrn), arrf, ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public SweepGradient(float f, float f2, long[] arrl, float[] arrf) {
        this(f, f2, (long[])arrl.clone(), arrf, SweepGradient.detectColorSpace(arrl));
    }

    private SweepGradient(float f, float f2, long[] object, float[] arrf, ColorSpace colorSpace) {
        super(colorSpace);
        if (arrf != null && ((long[])object).length != arrf.length) {
            throw new IllegalArgumentException("color and position arrays must be of equal length");
        }
        this.mCx = f;
        this.mCy = f2;
        this.mColorLongs = object;
        object = arrf != null ? (float[])arrf.clone() : null;
        this.mPositions = object;
    }

    private static native long nativeCreate(long var0, float var2, float var3, long[] var4, float[] var5, long var6);

    @Override
    long createNativeInstance(long l) {
        return SweepGradient.nativeCreate(l, this.mCx, this.mCy, this.mColorLongs, this.mPositions, this.colorSpace().getNativeInstance());
    }
}

