/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class Scroller {
    @UnsupportedAppUsage
    private static float DECELERATION_RATE = 0.0f;
    private static final int DEFAULT_DURATION = 250;
    private static final float END_TENSION = 1.0f;
    private static final int FLING_MODE = 1;
    @UnsupportedAppUsage
    private static final float INFLEXION = 0.35f;
    private static final int NB_SAMPLES = 100;
    private static final float P1 = 0.175f;
    private static final float P2 = 0.35000002f;
    private static final int SCROLL_MODE = 0;
    private static final float[] SPLINE_POSITION;
    private static final float[] SPLINE_TIME;
    private static final float START_TENSION = 0.5f;
    private float mCurrVelocity;
    private int mCurrX;
    private int mCurrY;
    @UnsupportedAppUsage
    private float mDeceleration;
    private float mDeltaX;
    private float mDeltaY;
    private int mDistance;
    @UnsupportedAppUsage
    private int mDuration;
    private float mDurationReciprocal;
    private int mFinalX;
    private int mFinalY;
    private boolean mFinished;
    private float mFlingFriction;
    private boolean mFlywheel;
    @UnsupportedAppUsage
    private final Interpolator mInterpolator;
    private int mMaxX;
    private int mMaxY;
    private int mMinX;
    private int mMinY;
    private int mMode;
    @UnsupportedAppUsage
    private float mPhysicalCoeff;
    private final float mPpi;
    private long mStartTime;
    private int mStartX;
    private int mStartY;
    private float mVelocity;

    static {
        DECELERATION_RATE = (float)(Math.log(0.78) / Math.log(0.9));
        SPLINE_POSITION = new float[101];
        SPLINE_TIME = new float[101];
        float f = 0.0f;
        float f2 = 0.0f;
        block0 : for (int i = 0; i < 100; ++i) {
            float f3 = (float)i / 100.0f;
            float f4 = 1.0f;
            do {
                float f5;
                float f6;
                float f7;
                if ((double)Math.abs((f6 = ((1.0f - (f5 = (f4 - f) / 2.0f + f)) * 0.175f + f5 * 0.35000002f) * (f7 = f5 * 3.0f * (1.0f - f5)) + f5 * f5 * f5) - f3) < 1.0E-5) {
                    Scroller.SPLINE_POSITION[i] = ((1.0f - f5) * 0.5f + f5) * f7 + f5 * f5 * f5;
                    f4 = 1.0f;
                    do {
                        if ((double)Math.abs((f7 = ((1.0f - (f5 = (f4 - f2) / 2.0f + f2)) * 0.5f + f5) * (f6 = f5 * 3.0f * (1.0f - f5)) + f5 * f5 * f5) - f3) < 1.0E-5) {
                            Scroller.SPLINE_TIME[i] = f6 * ((1.0f - f5) * 0.175f + 0.35000002f * f5) + f5 * f5 * f5;
                            continue block0;
                        }
                        if (f7 > f3) {
                            f4 = f5;
                            continue;
                        }
                        f2 = f5;
                    } while (true);
                }
                if (f6 > f3) {
                    f4 = f5;
                    continue;
                }
                f = f5;
            } while (true);
        }
        float[] arrf = SPLINE_POSITION;
        Scroller.SPLINE_TIME[100] = 1.0f;
        arrf[100] = 1.0f;
    }

    public Scroller(Context context) {
        this(context, null);
    }

    public Scroller(Context context, Interpolator interpolator2) {
        boolean bl = context.getApplicationInfo().targetSdkVersion >= 11;
        this(context, interpolator2, bl);
    }

    public Scroller(Context context, Interpolator interpolator2, boolean bl) {
        this.mFlingFriction = ViewConfiguration.getScrollFriction();
        this.mFinished = true;
        this.mInterpolator = interpolator2 == null ? new ViscousFluidInterpolator() : interpolator2;
        this.mPpi = context.getResources().getDisplayMetrics().density * 160.0f;
        this.mDeceleration = this.computeDeceleration(ViewConfiguration.getScrollFriction());
        this.mFlywheel = bl;
        this.mPhysicalCoeff = this.computeDeceleration(0.84f);
    }

    private float computeDeceleration(float f) {
        return this.mPpi * 386.0878f * f;
    }

    private double getSplineDeceleration(float f) {
        return Math.log(Math.abs(f) * 0.35f / (this.mFlingFriction * this.mPhysicalCoeff));
    }

    private double getSplineFlingDistance(float f) {
        double d = this.getSplineDeceleration(f);
        f = DECELERATION_RATE;
        double d2 = f;
        return (double)(this.mFlingFriction * this.mPhysicalCoeff) * Math.exp((double)f / (d2 - 1.0) * d);
    }

    private int getSplineFlingDuration(float f) {
        return (int)(Math.exp(this.getSplineDeceleration(f) / ((double)DECELERATION_RATE - 1.0)) * 1000.0);
    }

    public void abortAnimation() {
        this.mCurrX = this.mFinalX;
        this.mCurrY = this.mFinalY;
        this.mFinished = true;
    }

    public boolean computeScrollOffset() {
        int n;
        if (this.mFinished) {
            return false;
        }
        int n2 = (int)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
        if (n2 < (n = this.mDuration)) {
            int n3 = this.mMode;
            if (n3 != 0) {
                if (n3 == 1) {
                    float f = (float)n2 / (float)n;
                    n = (int)(f * 100.0f);
                    float f2 = 1.0f;
                    float f3 = 0.0f;
                    if (n < 100) {
                        float f4 = (float)n / 100.0f;
                        f3 = (float)(n + 1) / 100.0f;
                        float[] arrf = SPLINE_POSITION;
                        f2 = arrf[n];
                        f3 = (arrf[n + 1] - f2) / (f3 - f4);
                        f2 += (f - f4) * f3;
                    }
                    this.mCurrVelocity = (float)this.mDistance * f3 / (float)this.mDuration * 1000.0f;
                    n = this.mStartX;
                    this.mCurrX = n + Math.round((float)(this.mFinalX - n) * f2);
                    this.mCurrX = Math.min(this.mCurrX, this.mMaxX);
                    this.mCurrX = Math.max(this.mCurrX, this.mMinX);
                    n = this.mStartY;
                    this.mCurrY = n + Math.round((float)(this.mFinalY - n) * f2);
                    this.mCurrY = Math.min(this.mCurrY, this.mMaxY);
                    this.mCurrY = Math.max(this.mCurrY, this.mMinY);
                    if (this.mCurrX == this.mFinalX && this.mCurrY == this.mFinalY) {
                        this.mFinished = true;
                    }
                }
            } else {
                float f = this.mInterpolator.getInterpolation((float)n2 * this.mDurationReciprocal);
                this.mCurrX = this.mStartX + Math.round(this.mDeltaX * f);
                this.mCurrY = this.mStartY + Math.round(this.mDeltaY * f);
            }
        } else {
            this.mCurrX = this.mFinalX;
            this.mCurrY = this.mFinalY;
            this.mFinished = true;
        }
        return true;
    }

    public void extendDuration(int n) {
        this.mDuration = this.timePassed() + n;
        this.mDurationReciprocal = 1.0f / (float)this.mDuration;
        this.mFinished = false;
    }

    public void fling(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        float f;
        float f2;
        float f3;
        int n9 = n3;
        int n10 = n4;
        if (this.mFlywheel) {
            n9 = n3;
            n10 = n4;
            if (!this.mFinished) {
                f2 = this.getCurrVelocity();
                float f4 = this.mFinalX - this.mStartX;
                f = this.mFinalY - this.mStartY;
                f3 = (float)Math.hypot(f4, f);
                f4 /= f3;
                f /= f3;
                f3 = f4 * f2;
                f2 = f * f2;
                n9 = n3;
                n10 = n4;
                if (Math.signum(n3) == Math.signum(f3)) {
                    n9 = n3;
                    n10 = n4;
                    if (Math.signum(n4) == Math.signum(f2)) {
                        n9 = (int)((float)n3 + f3);
                        n10 = (int)((float)n4 + f2);
                    }
                }
            }
        }
        this.mMode = 1;
        this.mFinished = false;
        this.mVelocity = f = (float)Math.hypot(n9, n10);
        this.mDuration = this.getSplineFlingDuration(f);
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = n;
        this.mStartY = n2;
        f3 = 1.0f;
        f2 = f == 0.0f ? 1.0f : (float)n9 / f;
        if (f != 0.0f) {
            f3 = (float)n10 / f;
        }
        double d = this.getSplineFlingDistance(f);
        this.mDistance = (int)((double)Math.signum(f) * d);
        this.mMinX = n5;
        this.mMaxX = n6;
        this.mMinY = n7;
        this.mMaxY = n8;
        this.mFinalX = (int)Math.round((double)f2 * d) + n;
        this.mFinalX = Math.min(this.mFinalX, this.mMaxX);
        this.mFinalX = Math.max(this.mFinalX, this.mMinX);
        this.mFinalY = (int)Math.round((double)f3 * d) + n2;
        this.mFinalY = Math.min(this.mFinalY, this.mMaxY);
        this.mFinalY = Math.max(this.mFinalY, this.mMinY);
    }

    public final void forceFinished(boolean bl) {
        this.mFinished = bl;
    }

    public float getCurrVelocity() {
        float f = this.mMode == 1 ? this.mCurrVelocity : this.mVelocity - this.mDeceleration * (float)this.timePassed() / 2000.0f;
        return f;
    }

    public final int getCurrX() {
        return this.mCurrX;
    }

    public final int getCurrY() {
        return this.mCurrY;
    }

    public final int getDuration() {
        return this.mDuration;
    }

    public final int getFinalX() {
        return this.mFinalX;
    }

    public final int getFinalY() {
        return this.mFinalY;
    }

    public final int getStartX() {
        return this.mStartX;
    }

    public final int getStartY() {
        return this.mStartY;
    }

    public final boolean isFinished() {
        return this.mFinished;
    }

    public boolean isScrollingInDirection(float f, float f2) {
        boolean bl = !this.mFinished && Math.signum(f) == Math.signum(this.mFinalX - this.mStartX) && Math.signum(f2) == Math.signum(this.mFinalY - this.mStartY);
        return bl;
    }

    public void setFinalX(int n) {
        this.mFinalX = n;
        this.mDeltaX = this.mFinalX - this.mStartX;
        this.mFinished = false;
    }

    public void setFinalY(int n) {
        this.mFinalY = n;
        this.mDeltaY = this.mFinalY - this.mStartY;
        this.mFinished = false;
    }

    public final void setFriction(float f) {
        this.mDeceleration = this.computeDeceleration(f);
        this.mFlingFriction = f;
    }

    public void startScroll(int n, int n2, int n3, int n4) {
        this.startScroll(n, n2, n3, n4, 250);
    }

    public void startScroll(int n, int n2, int n3, int n4, int n5) {
        this.mMode = 0;
        this.mFinished = false;
        this.mDuration = n5;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mStartX = n;
        this.mStartY = n2;
        this.mFinalX = n + n3;
        this.mFinalY = n2 + n4;
        this.mDeltaX = n3;
        this.mDeltaY = n4;
        this.mDurationReciprocal = 1.0f / (float)this.mDuration;
    }

    public int timePassed() {
        return (int)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime);
    }

    static class ViscousFluidInterpolator
    implements Interpolator {
        private static final float VISCOUS_FLUID_NORMALIZE = 1.0f / ViscousFluidInterpolator.viscousFluid(1.0f);
        private static final float VISCOUS_FLUID_OFFSET = 1.0f - VISCOUS_FLUID_NORMALIZE * ViscousFluidInterpolator.viscousFluid(1.0f);
        private static final float VISCOUS_FLUID_SCALE = 8.0f;

        ViscousFluidInterpolator() {
        }

        private static float viscousFluid(float f) {
            f = (f *= 8.0f) < 1.0f ? (f -= 1.0f - (float)Math.exp(-f)) : 0.36787945f + (1.0f - 0.36787945f) * (1.0f - (float)Math.exp(1.0f - f));
            return f;
        }

        @Override
        public float getInterpolation(float f) {
            if ((f = VISCOUS_FLUID_NORMALIZE * ViscousFluidInterpolator.viscousFluid(f)) > 0.0f) {
                return VISCOUS_FLUID_OFFSET + f;
            }
            return f;
        }
    }

}

