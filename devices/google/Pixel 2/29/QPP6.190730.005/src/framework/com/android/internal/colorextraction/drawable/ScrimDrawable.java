/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.colorextraction.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.drawable._$$Lambda$ScrimDrawable$UWtyAZ9Ss5P5TukFNvAyvh0pNf0;
import com.android.internal.graphics.ColorUtils;

public class ScrimDrawable
extends Drawable {
    private static final long COLOR_ANIMATION_DURATION = 2000L;
    private static final String TAG = "ScrimDrawable";
    private int mAlpha = 255;
    private ValueAnimator mColorAnimation;
    private int mMainColor;
    private int mMainColorTo;
    private final Paint mPaint = new Paint();

    public ScrimDrawable() {
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas) {
        this.mPaint.setColor(this.mMainColor);
        this.mPaint.setAlpha(this.mAlpha);
        canvas.drawRect(this.getBounds(), this.mPaint);
    }

    @Override
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @VisibleForTesting
    public int getMainColor() {
        return this.mMainColor;
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    public /* synthetic */ void lambda$setColor$0$ScrimDrawable(int n, int n2, ValueAnimator valueAnimator) {
        this.mMainColor = ColorUtils.blendARGB(n, n2, ((Float)valueAnimator.getAnimatedValue()).floatValue());
        this.invalidateSelf();
    }

    @Override
    public void setAlpha(int n) {
        if (n != this.mAlpha) {
            this.mAlpha = n;
            this.invalidateSelf();
        }
    }

    public void setColor(int n, boolean bl) {
        if (n == this.mMainColorTo) {
            return;
        }
        ValueAnimator valueAnimator = this.mColorAnimation;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mColorAnimation.cancel();
        }
        this.mMainColorTo = n;
        if (bl) {
            int n2 = this.mMainColor;
            valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(2000L);
            valueAnimator.addUpdateListener(new _$$Lambda$ScrimDrawable$UWtyAZ9Ss5P5TukFNvAyvh0pNf0(this, n2, n));
            valueAnimator.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2, boolean bl) {
                    if (ScrimDrawable.this.mColorAnimation == animator2) {
                        ScrimDrawable.this.mColorAnimation = null;
                    }
                }
            });
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.start();
            this.mColorAnimation = valueAnimator;
        } else {
            this.mMainColor = n;
            this.invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override
    public void setXfermode(Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        this.invalidateSelf();
    }

}

