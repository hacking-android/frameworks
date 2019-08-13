/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class OverScroller {
    private static final int DEFAULT_DURATION = 250;
    private static final int FLING_MODE = 1;
    private static final int SCROLL_MODE = 0;
    private final boolean mFlywheel;
    @UnsupportedAppUsage
    private Interpolator mInterpolator;
    private int mMode;
    private final SplineOverScroller mScrollerX;
    @UnsupportedAppUsage
    private final SplineOverScroller mScrollerY;

    public OverScroller(Context context) {
        this(context, null);
    }

    public OverScroller(Context context, Interpolator interpolator2) {
        this(context, interpolator2, true);
    }

    @Deprecated
    public OverScroller(Context context, Interpolator interpolator2, float f, float f2) {
        this(context, interpolator2, true);
    }

    @Deprecated
    public OverScroller(Context context, Interpolator interpolator2, float f, float f2, boolean bl) {
        this(context, interpolator2, bl);
    }

    @UnsupportedAppUsage
    public OverScroller(Context context, Interpolator interpolator2, boolean bl) {
        this.mInterpolator = interpolator2 == null ? new Scroller.ViscousFluidInterpolator() : interpolator2;
        this.mFlywheel = bl;
        this.mScrollerX = new SplineOverScroller(context);
        this.mScrollerY = new SplineOverScroller(context);
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public boolean computeScrollOffset() {
        if (this.isFinished()) {
            return false;
        }
        int n = this.mMode;
        if (n != 0) {
            if (n == 1) {
                if (!(this.mScrollerX.mFinished || this.mScrollerX.update() || this.mScrollerX.continueWhenFinished())) {
                    this.mScrollerX.finish();
                }
                if (!(this.mScrollerY.mFinished || this.mScrollerY.update() || this.mScrollerY.continueWhenFinished())) {
                    this.mScrollerY.finish();
                }
            }
        } else {
            long l = AnimationUtils.currentAnimationTimeMillis() - this.mScrollerX.mStartTime;
            if (l < (long)(n = this.mScrollerX.mDuration)) {
                float f = this.mInterpolator.getInterpolation((float)l / (float)n);
                this.mScrollerX.updateScroll(f);
                this.mScrollerY.updateScroll(f);
            } else {
                this.abortAnimation();
            }
        }
        return true;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void extendDuration(int n) {
        this.mScrollerX.extendDuration(n);
        this.mScrollerY.extendDuration(n);
    }

    public void fling(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.fling(n, n2, n3, n4, n5, n6, n7, n8, 0, 0);
    }

    public void fling(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        if (this.mFlywheel && !this.isFinished()) {
            float f = this.mScrollerX.mCurrVelocity;
            float f2 = this.mScrollerY.mCurrVelocity;
            if (Math.signum(n3) == Math.signum(f) && Math.signum(n4) == Math.signum(f2)) {
                n3 = (int)((float)n3 + f);
                n4 = (int)((float)n4 + f2);
            }
        }
        this.mMode = 1;
        this.mScrollerX.fling(n, n3, n5, n6, n9);
        this.mScrollerY.fling(n2, n4, n7, n8, n10);
    }

    public final void forceFinished(boolean bl) {
        this.mScrollerX.mFinished = (this.mScrollerY.mFinished = bl);
    }

    public float getCurrVelocity() {
        return (float)Math.hypot(this.mScrollerX.mCurrVelocity, this.mScrollerY.mCurrVelocity);
    }

    public final int getCurrX() {
        return this.mScrollerX.mCurrentPosition;
    }

    public final int getCurrY() {
        return this.mScrollerY.mCurrentPosition;
    }

    @Deprecated
    public final int getDuration() {
        return Math.max(this.mScrollerX.mDuration, this.mScrollerY.mDuration);
    }

    public final int getFinalX() {
        return this.mScrollerX.mFinal;
    }

    public final int getFinalY() {
        return this.mScrollerY.mFinal;
    }

    public final int getStartX() {
        return this.mScrollerX.mStart;
    }

    public final int getStartY() {
        return this.mScrollerY.mStart;
    }

    public final boolean isFinished() {
        boolean bl = this.mScrollerX.mFinished && this.mScrollerY.mFinished;
        return bl;
    }

    public boolean isOverScrolled() {
        boolean bl = !this.mScrollerX.mFinished && this.mScrollerX.mState != 0 || !this.mScrollerY.mFinished && this.mScrollerY.mState != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isScrollingInDirection(float f, float f2) {
        int n = this.mScrollerX.mFinal;
        int n2 = this.mScrollerX.mStart;
        int n3 = this.mScrollerY.mFinal;
        int n4 = this.mScrollerY.mStart;
        boolean bl = !this.isFinished() && Math.signum(f) == Math.signum(n - n2) && Math.signum(f2) == Math.signum(n3 - n4);
        return bl;
    }

    public void notifyHorizontalEdgeReached(int n, int n2, int n3) {
        this.mScrollerX.notifyEdgeReached(n, n2, n3);
    }

    public void notifyVerticalEdgeReached(int n, int n2, int n3) {
        this.mScrollerY.notifyEdgeReached(n, n2, n3);
    }

    @Deprecated
    public void setFinalX(int n) {
        this.mScrollerX.setFinalPosition(n);
    }

    @Deprecated
    public void setFinalY(int n) {
        this.mScrollerY.setFinalPosition(n);
    }

    public final void setFriction(float f) {
        this.mScrollerX.setFriction(f);
        this.mScrollerY.setFriction(f);
    }

    @UnsupportedAppUsage
    void setInterpolator(Interpolator interpolator2) {
        this.mInterpolator = interpolator2 == null ? new Scroller.ViscousFluidInterpolator() : interpolator2;
    }

    public boolean springBack(int n, int n2, int n3, int n4, int n5, int n6) {
        boolean bl = true;
        this.mMode = 1;
        boolean bl2 = this.mScrollerX.springback(n, n3, n4);
        boolean bl3 = this.mScrollerY.springback(n2, n5, n6);
        boolean bl4 = bl;
        if (!bl2) {
            bl4 = bl3 ? bl : false;
        }
        return bl4;
    }

    public void startScroll(int n, int n2, int n3, int n4) {
        this.startScroll(n, n2, n3, n4, 250);
    }

    public void startScroll(int n, int n2, int n3, int n4, int n5) {
        this.mMode = 0;
        this.mScrollerX.startScroll(n, n3, n5);
        this.mScrollerY.startScroll(n2, n4, n5);
    }

    public int timePassed() {
        return (int)(AnimationUtils.currentAnimationTimeMillis() - Math.min(this.mScrollerX.mStartTime, this.mScrollerY.mStartTime));
    }

    static class SplineOverScroller {
        private static final int BALLISTIC = 2;
        private static final int CUBIC = 1;
        private static float DECELERATION_RATE = 0.0f;
        private static final float END_TENSION = 1.0f;
        private static final float GRAVITY = 2000.0f;
        private static final float INFLEXION = 0.35f;
        private static final int NB_SAMPLES = 100;
        private static final float P1 = 0.175f;
        private static final float P2 = 0.35000002f;
        private static final int SPLINE = 0;
        private static final float[] SPLINE_POSITION;
        private static final float[] SPLINE_TIME;
        private static final float START_TENSION = 0.5f;
        @UnsupportedAppUsage
        private float mCurrVelocity;
        private int mCurrentPosition;
        private float mDeceleration;
        private int mDuration;
        private int mFinal;
        private boolean mFinished = true;
        private float mFlingFriction = ViewConfiguration.getScrollFriction();
        private int mOver;
        private float mPhysicalCoeff;
        private int mSplineDistance;
        private int mSplineDuration;
        private int mStart;
        private long mStartTime;
        private int mState = 0;
        private int mVelocity;

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
                        SplineOverScroller.SPLINE_POSITION[i] = ((1.0f - f5) * 0.5f + f5) * f7 + f5 * f5 * f5;
                        f4 = 1.0f;
                        do {
                            if ((double)Math.abs((f6 = ((1.0f - (f5 = (f4 - f2) / 2.0f + f2)) * 0.5f + f5) * (f7 = f5 * 3.0f * (1.0f - f5)) + f5 * f5 * f5) - f3) < 1.0E-5) {
                                SplineOverScroller.SPLINE_TIME[i] = f7 * ((1.0f - f5) * 0.175f + 0.35000002f * f5) + f5 * f5 * f5;
                                continue block0;
                            }
                            if (f6 > f3) {
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
            SplineOverScroller.SPLINE_TIME[100] = 1.0f;
            arrf[100] = 1.0f;
        }

        SplineOverScroller(Context context) {
            this.mPhysicalCoeff = 386.0878f * (context.getResources().getDisplayMetrics().density * 160.0f) * 0.84f;
        }

        private void adjustDuration(int n, int n2, int n3) {
            float f = Math.abs((float)(n3 - n) / (float)(n2 - n));
            n = (int)(f * 100.0f);
            if (n < 100) {
                float f2 = (float)n / 100.0f;
                float f3 = (float)(n + 1) / 100.0f;
                float[] arrf = SPLINE_TIME;
                float f4 = arrf[n];
                float f5 = arrf[n + 1];
                f2 = (f - f2) / (f3 - f2);
                this.mDuration = (int)((float)this.mDuration * (f2 * (f5 - f4) + f4));
            }
        }

        private void fitOnBounceCurve(int n, int n2, int n3) {
            float f = -n3;
            float f2 = this.mDeceleration;
            f /= f2;
            f2 = (float)Math.sqrt((double)((float)n3 * (float)n3 / 2.0f / Math.abs(f2) + (float)Math.abs(n2 - n)) * 2.0 / (double)Math.abs(this.mDeceleration));
            this.mStartTime -= (long)((int)((f2 - f) * 1000.0f));
            this.mStart = n2;
            this.mCurrentPosition = n2;
            this.mVelocity = (int)(-this.mDeceleration * f2);
        }

        private static float getDeceleration(int n) {
            float f = n > 0 ? -2000.0f : 2000.0f;
            return f;
        }

        private double getSplineDeceleration(int n) {
            return Math.log((float)Math.abs(n) * 0.35f / (this.mFlingFriction * this.mPhysicalCoeff));
        }

        private double getSplineFlingDistance(int n) {
            double d = this.getSplineDeceleration(n);
            float f = DECELERATION_RATE;
            double d2 = f;
            return (double)(this.mFlingFriction * this.mPhysicalCoeff) * Math.exp((double)f / (d2 - 1.0) * d);
        }

        private int getSplineFlingDuration(int n) {
            return (int)(Math.exp(this.getSplineDeceleration(n) / ((double)DECELERATION_RATE - 1.0)) * 1000.0);
        }

        private void onEdgeReached() {
            int n = this.mVelocity;
            float f = (float)n * (float)n;
            float f2 = f / (Math.abs(this.mDeceleration) * 2.0f);
            float f3 = Math.signum(this.mVelocity);
            n = this.mOver;
            float f4 = f2;
            if (f2 > (float)n) {
                this.mDeceleration = -f3 * f / ((float)n * 2.0f);
                f4 = n;
            }
            this.mOver = (int)f4;
            this.mState = 2;
            n = this.mStart;
            if (this.mVelocity <= 0) {
                f4 = -f4;
            }
            this.mFinal = n + (int)f4;
            this.mDuration = -((int)((float)this.mVelocity * 1000.0f / this.mDeceleration));
        }

        private void startAfterEdge(int n, int n2, int n3, int n4) {
            boolean bl = true;
            if (n > n2 && n < n3) {
                Log.e("OverScroller", "startAfterEdge called from a valid position");
                this.mFinished = true;
                return;
            }
            boolean bl2 = n > n3;
            int n5 = bl2 ? n3 : n2;
            int n6 = n - n5;
            if (n6 * n4 < 0) {
                bl = false;
            }
            if (bl) {
                this.startBounceAfterEdge(n, n5, n4);
            } else if (this.getSplineFlingDistance(n4) > (double)Math.abs(n6)) {
                if (!bl2) {
                    n2 = n;
                }
                if (bl2) {
                    n3 = n;
                }
                this.fling(n, n4, n2, n3, this.mOver);
            } else {
                this.startSpringback(n, n5, n4);
            }
        }

        private void startBounceAfterEdge(int n, int n2, int n3) {
            int n4 = n3 == 0 ? n - n2 : n3;
            this.mDeceleration = SplineOverScroller.getDeceleration(n4);
            this.fitOnBounceCurve(n, n2, n3);
            this.onEdgeReached();
        }

        private void startSpringback(int n, int n2, int n3) {
            this.mFinished = false;
            this.mState = 1;
            this.mStart = n;
            this.mCurrentPosition = n;
            this.mFinal = n2;
            this.mDeceleration = SplineOverScroller.getDeceleration(n -= n2);
            this.mVelocity = -n;
            this.mOver = Math.abs(n);
            this.mDuration = (int)(Math.sqrt((double)n * -2.0 / (double)this.mDeceleration) * 1000.0);
        }

        boolean continueWhenFinished() {
            block8 : {
                block7 : {
                    int n;
                    block5 : {
                        block6 : {
                            n = this.mState;
                            if (n == 0) break block5;
                            if (n == 1) break block6;
                            if (n == 2) {
                                this.mStartTime += (long)this.mDuration;
                                this.startSpringback(this.mFinal, this.mStart, 0);
                            }
                            break block7;
                        }
                        return false;
                    }
                    if (this.mDuration >= this.mSplineDuration) break block8;
                    this.mStart = n = this.mFinal;
                    this.mCurrentPosition = n;
                    this.mVelocity = (int)this.mCurrVelocity;
                    this.mDeceleration = SplineOverScroller.getDeceleration(this.mVelocity);
                    this.mStartTime += (long)this.mDuration;
                    this.onEdgeReached();
                }
                this.update();
                return true;
            }
            return false;
        }

        void extendDuration(int n) {
            this.mDuration = (int)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) + n;
            this.mFinished = false;
        }

        void finish() {
            this.mCurrentPosition = this.mFinal;
            this.mFinished = true;
        }

        void fling(int n, int n2, int n3, int n4, int n5) {
            this.mOver = n5;
            this.mFinished = false;
            this.mVelocity = n2;
            this.mCurrVelocity = n2;
            this.mSplineDuration = 0;
            this.mDuration = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStart = n;
            this.mCurrentPosition = n;
            if (n <= n4 && n >= n3) {
                this.mState = 0;
                double d = 0.0;
                if (n2 != 0) {
                    this.mSplineDuration = n5 = this.getSplineFlingDuration(n2);
                    this.mDuration = n5;
                    d = this.getSplineFlingDistance(n2);
                }
                this.mSplineDistance = (int)((double)Math.signum(n2) * d);
                this.mFinal = this.mSplineDistance + n;
                if ((n = this.mFinal) < n3) {
                    this.adjustDuration(this.mStart, n, n3);
                    this.mFinal = n3;
                }
                if ((n = this.mFinal) > n4) {
                    this.adjustDuration(this.mStart, n, n4);
                    this.mFinal = n4;
                }
                return;
            }
            this.startAfterEdge(n, n3, n4, n2);
        }

        void notifyEdgeReached(int n, int n2, int n3) {
            if (this.mState == 0) {
                this.mOver = n3;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.startAfterEdge(n, n2, n2, (int)this.mCurrVelocity);
            }
        }

        void setFinalPosition(int n) {
            this.mFinal = n;
            this.mFinished = false;
        }

        void setFriction(float f) {
            this.mFlingFriction = f;
        }

        boolean springback(int n, int n2, int n3) {
            this.mFinished = true;
            this.mFinal = n;
            this.mStart = n;
            this.mCurrentPosition = n;
            this.mVelocity = 0;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = 0;
            if (n < n2) {
                this.startSpringback(n, n2, 0);
            } else if (n > n3) {
                this.startSpringback(n, n3, 0);
            }
            return true ^ this.mFinished;
        }

        void startScroll(int n, int n2, int n3) {
            this.mFinished = false;
            this.mStart = n;
            this.mCurrentPosition = n;
            this.mFinal = n + n2;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mDuration = n3;
            this.mDeceleration = 0.0f;
            this.mVelocity = 0;
        }

        boolean update() {
            long l = AnimationUtils.currentAnimationTimeMillis() - this.mStartTime;
            boolean bl = false;
            if (l == 0L) {
                if (this.mDuration > 0) {
                    bl = true;
                }
                return bl;
            }
            int n = this.mDuration;
            if (l > (long)n) {
                return false;
            }
            double d = 0.0;
            int n2 = this.mState;
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) {
                        float f = (float)l / 1000.0f;
                        n2 = this.mVelocity;
                        float f2 = n2;
                        float f3 = this.mDeceleration;
                        this.mCurrVelocity = f2 + f3 * f;
                        d = (float)n2 * f + f3 * f * f / 2.0f;
                    }
                } else {
                    float f = (float)l / (float)n;
                    float f4 = f * f;
                    float f5 = Math.signum(this.mVelocity);
                    n2 = this.mOver;
                    d = (float)n2 * f5 * (3.0f * f4 - 2.0f * f * f4);
                    this.mCurrVelocity = (float)n2 * f5 * 6.0f * (-f + f4);
                }
            } else {
                float f = (float)l / (float)this.mSplineDuration;
                n2 = (int)(f * 100.0f);
                float f6 = 1.0f;
                float f7 = 0.0f;
                if (n2 < 100) {
                    f6 = (float)n2 / 100.0f;
                    f7 = (float)(n2 + 1) / 100.0f;
                    float[] arrf = SPLINE_POSITION;
                    float f8 = arrf[n2];
                    f7 = (arrf[n2 + 1] - f8) / (f7 - f6);
                    f6 = f8 + (f - f6) * f7;
                }
                n2 = this.mSplineDistance;
                d = (float)n2 * f6;
                this.mCurrVelocity = (float)n2 * f7 / (float)this.mSplineDuration * 1000.0f;
            }
            this.mCurrentPosition = this.mStart + (int)Math.round(d);
            return true;
        }

        void updateScroll(float f) {
            int n = this.mStart;
            this.mCurrentPosition = n + Math.round((float)(this.mFinal - n) * f);
        }
    }

}

