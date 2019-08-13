/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AbsListView;

public abstract class AutoScrollHelper
implements View.OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = ViewConfiguration.getTapTimeout();
    private static final int DEFAULT_EDGE_TYPE = 1;
    private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
    private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
    private static final int DEFAULT_RAMP_UP_DURATION = 500;
    private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
    private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    private static final int HORIZONTAL = 0;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int VERTICAL = 1;
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    private boolean mAnimating;
    private final Interpolator mEdgeInterpolator = new AccelerateInterpolator();
    private int mEdgeType;
    private boolean mEnabled;
    private boolean mExclusive;
    private float[] mMaximumEdges = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mMaximumVelocity = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mMinimumVelocity = new float[]{0.0f, 0.0f};
    private boolean mNeedsCancel;
    private boolean mNeedsReset;
    private float[] mRelativeEdges = new float[]{0.0f, 0.0f};
    private float[] mRelativeVelocity = new float[]{0.0f, 0.0f};
    private Runnable mRunnable;
    private final ClampedScroller mScroller = new ClampedScroller();
    private final View mTarget;

    public AutoScrollHelper(View object) {
        this.mTarget = object;
        object = Resources.getSystem().getDisplayMetrics();
        int n = (int)(((DisplayMetrics)object).density * 1575.0f + 0.5f);
        int n2 = (int)(((DisplayMetrics)object).density * 315.0f + 0.5f);
        this.setMaximumVelocity(n, n);
        this.setMinimumVelocity(n2, n2);
        this.setEdgeType(1);
        this.setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        this.setRelativeEdges(0.2f, 0.2f);
        this.setRelativeVelocity(1.0f, 1.0f);
        this.setActivationDelay(DEFAULT_ACTIVATION_DELAY);
        this.setRampUpDuration(500);
        this.setRampDownDuration(500);
    }

    private void cancelTargetTouch() {
        long l = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0);
        this.mTarget.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    private float computeTargetVelocity(int n, float f, float f2, float f3) {
        float f4 = this.getEdgeValue(this.mRelativeEdges[n], f2, this.mMaximumEdges[n], f);
        if (f4 == 0.0f) {
            return 0.0f;
        }
        float f5 = this.mRelativeVelocity[n];
        f = this.mMinimumVelocity[n];
        f2 = this.mMaximumVelocity[n];
        f3 = f5 * f3;
        if (f4 > 0.0f) {
            return AutoScrollHelper.constrain(f4 * f3, f, f2);
        }
        return -AutoScrollHelper.constrain(-f4 * f3, f, f2);
    }

    private static float constrain(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        if (f < f2) {
            return f2;
        }
        return f;
    }

    private static int constrain(int n, int n2, int n3) {
        if (n > n3) {
            return n3;
        }
        if (n < n2) {
            return n2;
        }
        return n;
    }

    private float constrainEdgeValue(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        int n = this.mEdgeType;
        if (n != 0 && n != 1) {
            if (n == 2 && f < 0.0f) {
                return f / -f2;
            }
        } else if (f < f2) {
            if (f >= 0.0f) {
                return 1.0f - f / f2;
            }
            if (this.mAnimating && this.mEdgeType == 1) {
                return 1.0f;
            }
        }
        return 0.0f;
    }

    private float getEdgeValue(float f, float f2, float f3, float f4) {
        block4 : {
            block3 : {
                block2 : {
                    f = AutoScrollHelper.constrain(f * f2, 0.0f, f3);
                    f3 = this.constrainEdgeValue(f4, f);
                    if (!((f = this.constrainEdgeValue(f2 - f4, f) - f3) < 0.0f)) break block2;
                    f = -this.mEdgeInterpolator.getInterpolation(-f);
                    break block3;
                }
                if (!(f > 0.0f)) break block4;
                f = this.mEdgeInterpolator.getInterpolation(f);
            }
            return AutoScrollHelper.constrain(f, -1.0f, 1.0f);
        }
        return 0.0f;
    }

    private void requestStop() {
        if (this.mNeedsReset) {
            this.mAnimating = false;
        } else {
            this.mScroller.requestStop();
        }
    }

    private boolean shouldAnimate() {
        ClampedScroller clampedScroller = this.mScroller;
        int n = clampedScroller.getVerticalDirection();
        int n2 = clampedScroller.getHorizontalDirection();
        boolean bl = n != 0 && this.canTargetScrollVertically(n) || n2 != 0 && this.canTargetScrollHorizontally(n2);
        return bl;
    }

    private void startAnimating() {
        int n;
        if (this.mRunnable == null) {
            this.mRunnable = new ScrollAnimationRunnable();
        }
        this.mAnimating = true;
        this.mNeedsReset = true;
        if (!this.mAlreadyDelayed && (n = this.mActivationDelay) > 0) {
            this.mTarget.postOnAnimationDelayed(this.mRunnable, n);
        } else {
            this.mRunnable.run();
        }
        this.mAlreadyDelayed = true;
    }

    public abstract boolean canTargetScrollHorizontally(int var1);

    public abstract boolean canTargetScrollVertically(int var1);

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isExclusive() {
        return this.mExclusive;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        block9 : {
            block8 : {
                block6 : {
                    block7 : {
                        bl2 = this.mEnabled;
                        bl = false;
                        if (!bl2) {
                            return false;
                        }
                        int n = motionEvent.getActionMasked();
                        if (n == 0) break block6;
                        if (n == 1) break block7;
                        if (n == 2) break block8;
                        if (n != 3) break block9;
                    }
                    this.requestStop();
                    break block9;
                }
                this.mNeedsCancel = true;
                this.mAlreadyDelayed = false;
            }
            float f = this.computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth());
            float f2 = this.computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight());
            this.mScroller.setTargetVelocity(f, f2);
            if (!this.mAnimating && this.shouldAnimate()) {
                this.startAnimating();
            }
        }
        bl2 = bl;
        if (this.mExclusive) {
            bl2 = bl;
            if (this.mAnimating) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public abstract void scrollTargetBy(int var1, int var2);

    public AutoScrollHelper setActivationDelay(int n) {
        this.mActivationDelay = n;
        return this;
    }

    public AutoScrollHelper setEdgeType(int n) {
        this.mEdgeType = n;
        return this;
    }

    public AutoScrollHelper setEnabled(boolean bl) {
        if (this.mEnabled && !bl) {
            this.requestStop();
        }
        this.mEnabled = bl;
        return this;
    }

    public AutoScrollHelper setExclusive(boolean bl) {
        this.mExclusive = bl;
        return this;
    }

    public AutoScrollHelper setMaximumEdges(float f, float f2) {
        float[] arrf = this.mMaximumEdges;
        arrf[0] = f;
        arrf[1] = f2;
        return this;
    }

    public AutoScrollHelper setMaximumVelocity(float f, float f2) {
        float[] arrf = this.mMaximumVelocity;
        arrf[0] = f / 1000.0f;
        arrf[1] = f2 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setMinimumVelocity(float f, float f2) {
        float[] arrf = this.mMinimumVelocity;
        arrf[0] = f / 1000.0f;
        arrf[1] = f2 / 1000.0f;
        return this;
    }

    public AutoScrollHelper setRampDownDuration(int n) {
        this.mScroller.setRampDownDuration(n);
        return this;
    }

    public AutoScrollHelper setRampUpDuration(int n) {
        this.mScroller.setRampUpDuration(n);
        return this;
    }

    public AutoScrollHelper setRelativeEdges(float f, float f2) {
        float[] arrf = this.mRelativeEdges;
        arrf[0] = f;
        arrf[1] = f2;
        return this;
    }

    public AutoScrollHelper setRelativeVelocity(float f, float f2) {
        float[] arrf = this.mRelativeVelocity;
        arrf[0] = f / 1000.0f;
        arrf[1] = f2 / 1000.0f;
        return this;
    }

    public static class AbsListViewAutoScroller
    extends AutoScrollHelper {
        private final AbsListView mTarget;

        public AbsListViewAutoScroller(AbsListView absListView) {
            super(absListView);
            this.mTarget = absListView;
        }

        @Override
        public boolean canTargetScrollHorizontally(int n) {
            return false;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean canTargetScrollVertically(int n) {
            AbsListView absListView = this.mTarget;
            int n2 = absListView.getCount();
            if (n2 == 0) {
                return false;
            }
            int n3 = absListView.getChildCount();
            int n4 = absListView.getFirstVisiblePosition();
            if (n > 0) {
                if (n4 + n3 < n2 || absListView.getChildAt(n3 - 1).getBottom() > absListView.getHeight()) return true;
                return false;
            }
            if (n >= 0) return false;
            if (n4 > 0 || absListView.getChildAt(0).getTop() < 0) return true;
            return false;
        }

        @Override
        public void scrollTargetBy(int n, int n2) {
            this.mTarget.scrollListBy(n2);
        }
    }

    private static class ClampedScroller {
        private long mDeltaTime = 0L;
        private int mDeltaX = 0;
        private int mDeltaY = 0;
        private int mEffectiveRampDown;
        private int mRampDownDuration;
        private int mRampUpDuration;
        private long mStartTime = Long.MIN_VALUE;
        private long mStopTime = -1L;
        private float mStopValue;
        private float mTargetVelocityX;
        private float mTargetVelocityY;

        private float getValueAt(long l) {
            if (l < this.mStartTime) {
                return 0.0f;
            }
            long l2 = this.mStopTime;
            if (l2 >= 0L && l >= l2) {
                float f = this.mStopValue;
                return 1.0f - f + f * AutoScrollHelper.constrain((float)(l - l2) / (float)this.mEffectiveRampDown, 0.0f, 1.0f);
            }
            return AutoScrollHelper.constrain((float)(l - this.mStartTime) / (float)this.mRampUpDuration, 0.0f, 1.0f) * 0.5f;
        }

        private float interpolateValue(float f) {
            return -4.0f * f * f + 4.0f * f;
        }

        public void computeScrollDelta() {
            if (this.mDeltaTime != 0L) {
                long l = AnimationUtils.currentAnimationTimeMillis();
                float f = this.interpolateValue(this.getValueAt(l));
                long l2 = l - this.mDeltaTime;
                this.mDeltaTime = l;
                this.mDeltaX = (int)((float)l2 * f * this.mTargetVelocityX);
                this.mDeltaY = (int)((float)l2 * f * this.mTargetVelocityY);
                return;
            }
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }

        public int getDeltaX() {
            return this.mDeltaX;
        }

        public int getDeltaY() {
            return this.mDeltaY;
        }

        public int getHorizontalDirection() {
            float f = this.mTargetVelocityX;
            return (int)(f / Math.abs(f));
        }

        public int getVerticalDirection() {
            float f = this.mTargetVelocityY;
            return (int)(f / Math.abs(f));
        }

        public boolean isFinished() {
            boolean bl = this.mStopTime > 0L && AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + (long)this.mEffectiveRampDown;
            return bl;
        }

        public void requestStop() {
            long l = AnimationUtils.currentAnimationTimeMillis();
            this.mEffectiveRampDown = AutoScrollHelper.constrain((int)(l - this.mStartTime), 0, this.mRampDownDuration);
            this.mStopValue = this.getValueAt(l);
            this.mStopTime = l;
        }

        public void setRampDownDuration(int n) {
            this.mRampDownDuration = n;
        }

        public void setRampUpDuration(int n) {
            this.mRampUpDuration = n;
        }

        public void setTargetVelocity(float f, float f2) {
            this.mTargetVelocityX = f;
            this.mTargetVelocityY = f2;
        }

        public void start() {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStopTime = -1L;
            this.mDeltaTime = this.mStartTime;
            this.mStopValue = 0.5f;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    private class ScrollAnimationRunnable
    implements Runnable {
        private ScrollAnimationRunnable() {
        }

        @Override
        public void run() {
            ClampedScroller clampedScroller;
            if (!AutoScrollHelper.this.mAnimating) {
                return;
            }
            if (AutoScrollHelper.this.mNeedsReset) {
                AutoScrollHelper.this.mNeedsReset = false;
                AutoScrollHelper.this.mScroller.start();
            }
            if (!(clampedScroller = AutoScrollHelper.this.mScroller).isFinished() && AutoScrollHelper.this.shouldAnimate()) {
                if (AutoScrollHelper.this.mNeedsCancel) {
                    AutoScrollHelper.this.mNeedsCancel = false;
                    AutoScrollHelper.this.cancelTargetTouch();
                }
                clampedScroller.computeScrollDelta();
                int n = clampedScroller.getDeltaX();
                int n2 = clampedScroller.getDeltaY();
                AutoScrollHelper.this.scrollTargetBy(n, n2);
                AutoScrollHelper.this.mTarget.postOnAnimation(this);
                return;
            }
            AutoScrollHelper.this.mAnimating = false;
        }
    }

}

