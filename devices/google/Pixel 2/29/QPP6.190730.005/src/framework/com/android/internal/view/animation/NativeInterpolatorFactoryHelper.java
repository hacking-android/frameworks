/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.animation;

public final class NativeInterpolatorFactoryHelper {
    private NativeInterpolatorFactoryHelper() {
    }

    public static native long createAccelerateDecelerateInterpolator();

    public static native long createAccelerateInterpolator(float var0);

    public static native long createAnticipateInterpolator(float var0);

    public static native long createAnticipateOvershootInterpolator(float var0);

    public static native long createBounceInterpolator();

    public static native long createCycleInterpolator(float var0);

    public static native long createDecelerateInterpolator(float var0);

    public static native long createLinearInterpolator();

    public static native long createLutInterpolator(float[] var0);

    public static native long createOvershootInterpolator(float var0);

    public static native long createPathInterpolator(float[] var0, float[] var1);
}

