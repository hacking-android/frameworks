/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.animation;

import android.animation.TimeInterpolator;
import android.view.Choreographer;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

@HasNativeInterpolator
public class FallbackLUTInterpolator
implements NativeInterpolatorFactory,
TimeInterpolator {
    private static final int MAX_SAMPLE_POINTS = 300;
    private final float[] mLut;
    private TimeInterpolator mSourceInterpolator;

    public FallbackLUTInterpolator(TimeInterpolator timeInterpolator, long l) {
        this.mSourceInterpolator = timeInterpolator;
        this.mLut = FallbackLUTInterpolator.createLUT(timeInterpolator, l);
    }

    private static float[] createLUT(TimeInterpolator timeInterpolator, long l) {
        int n = (int)(Choreographer.getInstance().getFrameIntervalNanos() / 1000000L);
        int n2 = Math.min(Math.max(2, (int)Math.ceil((double)l / (double)n)), 300);
        float[] arrf = new float[n2];
        float f = n2 - 1;
        for (n = 0; n < n2; ++n) {
            arrf[n] = timeInterpolator.getInterpolation((float)n / f);
        }
        return arrf;
    }

    public static long createNativeInterpolator(TimeInterpolator timeInterpolator, long l) {
        return NativeInterpolatorFactoryHelper.createLutInterpolator(FallbackLUTInterpolator.createLUT(timeInterpolator, l));
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createLutInterpolator(this.mLut);
    }

    @Override
    public float getInterpolation(float f) {
        return this.mSourceInterpolator.getInterpolation(f);
    }
}

