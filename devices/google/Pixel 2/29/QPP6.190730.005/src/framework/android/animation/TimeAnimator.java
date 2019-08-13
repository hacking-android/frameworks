/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.ValueAnimator;
import android.view.animation.AnimationUtils;

public class TimeAnimator
extends ValueAnimator {
    private TimeListener mListener;
    private long mPreviousTime = -1L;

    @Override
    boolean animateBasedOnTime(long l) {
        if (this.mListener != null) {
            long l2 = this.mStartTime;
            long l3 = this.mPreviousTime;
            l3 = l3 < 0L ? 0L : l - l3;
            this.mPreviousTime = l;
            this.mListener.onTimeUpdate(this, l - l2, l3);
        }
        return false;
    }

    @Override
    void animateValue(float f) {
    }

    @Override
    void initAnimation() {
    }

    @Override
    public void setCurrentPlayTime(long l) {
        long l2 = AnimationUtils.currentAnimationTimeMillis();
        this.mStartTime = Math.max(this.mStartTime, l2 - l);
        this.mStartTimeCommitted = true;
        this.animateBasedOnTime(l2);
    }

    public void setTimeListener(TimeListener timeListener) {
        this.mListener = timeListener;
    }

    @Override
    public void start() {
        this.mPreviousTime = -1L;
        super.start();
    }

    public static interface TimeListener {
        public void onTimeUpdate(TimeAnimator var1, long var2, long var4);
    }

}

