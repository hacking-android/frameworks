/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.internal.R;

public class EdgeEffect {
    private static final double ANGLE = 0.5235987755982988;
    private static final float COS;
    public static final BlendMode DEFAULT_BLEND_MODE;
    private static final float EPSILON = 0.001f;
    private static final float GLOW_ALPHA_START = 0.09f;
    private static final float MAX_ALPHA = 0.15f;
    private static final float MAX_GLOW_SCALE = 2.0f;
    private static final int MAX_VELOCITY = 10000;
    private static final int MIN_VELOCITY = 100;
    private static final int PULL_DECAY_TIME = 2000;
    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 0.8f;
    private static final float PULL_GLOW_BEGIN = 0.0f;
    private static final int PULL_TIME = 167;
    private static final float RADIUS_FACTOR = 0.6f;
    private static final int RECEDE_TIME = 600;
    private static final float SIN;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final String TAG = "EdgeEffect";
    private static final int VELOCITY_GLOW_FACTOR = 6;
    private float mBaseGlowScale;
    private final Rect mBounds = new Rect();
    private float mDisplacement = 0.5f;
    private float mDuration;
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    @UnsupportedAppUsage
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private final Interpolator mInterpolator;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769450L)
    private final Paint mPaint = new Paint();
    private float mPullDistance;
    private float mRadius;
    private long mStartTime;
    private int mState = 0;
    private float mTargetDisplacement = 0.5f;

    static {
        DEFAULT_BLEND_MODE = BlendMode.SRC_ATOP;
        SIN = (float)Math.sin(0.5235987755982988);
        COS = (float)Math.cos(0.5235987755982988);
    }

    public EdgeEffect(Context object) {
        this.mPaint.setAntiAlias(true);
        object = ((Context)object).obtainStyledAttributes(R.styleable.EdgeEffect);
        int n = ((TypedArray)object).getColor(0, -10066330);
        ((TypedArray)object).recycle();
        this.mPaint.setColor(16777215 & n | 855638016);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setBlendMode(DEFAULT_BLEND_MODE);
        this.mInterpolator = new DecelerateInterpolator();
    }

    private void update() {
        float f = Math.min((float)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / this.mDuration, 1.0f);
        float f2 = this.mInterpolator.getInterpolation(f);
        float f3 = this.mGlowAlphaStart;
        this.mGlowAlpha = f3 + (this.mGlowAlphaFinish - f3) * f2;
        f3 = this.mGlowScaleYStart;
        this.mGlowScaleY = f3 + (this.mGlowScaleYFinish - f3) * f2;
        this.mDisplacement = (this.mDisplacement + this.mTargetDisplacement) / 2.0f;
        if (f >= 0.999f) {
            int n = this.mState;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mState = 3;
                        }
                    } else {
                        this.mState = 0;
                    }
                } else {
                    this.mState = 3;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 600.0f;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                }
            } else {
                this.mState = 4;
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = 2000.0f;
                this.mGlowAlphaStart = this.mGlowAlpha;
                this.mGlowScaleYStart = this.mGlowScaleY;
                this.mGlowAlphaFinish = 0.0f;
                this.mGlowScaleYFinish = 0.0f;
            }
        }
    }

    public boolean draw(Canvas canvas) {
        this.update();
        int n = canvas.save();
        float f = this.mBounds.centerX();
        float f2 = this.mBounds.height();
        float f3 = this.mRadius;
        canvas.scale(1.0f, Math.min(this.mGlowScaleY, 1.0f) * this.mBaseGlowScale, f, 0.0f);
        float f4 = Math.max(0.0f, Math.min(this.mDisplacement, 1.0f));
        f4 = (float)this.mBounds.width() * (f4 - 0.5f) / 2.0f;
        canvas.clipRect(this.mBounds);
        canvas.translate(f4, 0.0f);
        this.mPaint.setAlpha((int)(this.mGlowAlpha * 255.0f));
        canvas.drawCircle(f, f2 - f3, this.mRadius, this.mPaint);
        canvas.restoreToCount(n);
        int n2 = 0;
        int n3 = this.mState;
        boolean bl = false;
        n = n2;
        if (n3 == 3) {
            n = n2;
            if (this.mGlowScaleY == 0.0f) {
                this.mState = 0;
                n = 1;
            }
        }
        if (this.mState != 0 || n != 0) {
            bl = true;
        }
        return bl;
    }

    public void finish() {
        this.mState = 0;
    }

    public BlendMode getBlendMode() {
        return this.mPaint.getBlendMode();
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public int getMaxHeight() {
        return (int)((float)this.mBounds.height() * 2.0f + 0.5f);
    }

    public boolean isFinished() {
        boolean bl = this.mState == 0;
        return bl;
    }

    public void onAbsorb(int n) {
        this.mState = 2;
        n = Math.min(Math.max(100, Math.abs(n)), 10000);
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = (float)n * 0.02f + 0.15f;
        this.mGlowAlphaStart = 0.09f;
        this.mGlowScaleYStart = Math.max(this.mGlowScaleY, 0.0f);
        this.mGlowScaleYFinish = Math.min((float)(n / 100 * n) * 1.5E-4f / 2.0f + 0.025f, 1.0f);
        this.mGlowAlphaFinish = Math.max(this.mGlowAlphaStart, Math.min((float)(n * 6) * 1.0E-5f, 0.15f));
        this.mTargetDisplacement = 0.5f;
    }

    public void onPull(float f) {
        this.onPull(f, 0.5f);
    }

    public void onPull(float f, float f2) {
        long l = AnimationUtils.currentAnimationTimeMillis();
        this.mTargetDisplacement = f2;
        if (this.mState == 4 && (float)(l - this.mStartTime) < this.mDuration) {
            return;
        }
        if (this.mState != 1) {
            this.mGlowScaleY = Math.max(0.0f, this.mGlowScaleY);
        }
        this.mState = 1;
        this.mStartTime = l;
        this.mDuration = 167.0f;
        this.mPullDistance += f;
        f = Math.abs(f);
        this.mGlowAlphaStart = f = Math.min(0.15f, this.mGlowAlpha + 0.8f * f);
        this.mGlowAlpha = f;
        f = this.mPullDistance;
        if (f == 0.0f) {
            this.mGlowScaleYStart = 0.0f;
            this.mGlowScaleY = 0.0f;
        } else {
            this.mGlowScaleYStart = f = (float)(Math.max(0.0, 1.0 - 1.0 / Math.sqrt(Math.abs(f) * (float)this.mBounds.height()) - 0.3) / 0.7);
            this.mGlowScaleY = f;
        }
        this.mGlowAlphaFinish = this.mGlowAlpha;
        this.mGlowScaleYFinish = this.mGlowScaleY;
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        int n = this.mState;
        if (n != 1 && n != 4) {
            return;
        }
        this.mState = 3;
        this.mGlowAlphaStart = this.mGlowAlpha;
        this.mGlowScaleYStart = this.mGlowScaleY;
        this.mGlowAlphaFinish = 0.0f;
        this.mGlowScaleYFinish = 0.0f;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 600.0f;
    }

    public void setBlendMode(BlendMode blendMode) {
        this.mPaint.setBlendMode(blendMode);
    }

    public void setColor(int n) {
        this.mPaint.setColor(n);
    }

    public void setSize(int n, int n2) {
        float f = n;
        float f2 = SIN;
        float f3 = f * 0.6f / f2;
        float f4 = COS;
        f = f3 - f4 * f3;
        float f5 = (float)n2 * 0.6f / f2;
        this.mRadius = f3;
        f2 = 1.0f;
        if (f > 0.0f) {
            f2 = Math.min((f5 - f4 * f5) / f, 1.0f);
        }
        this.mBaseGlowScale = f2;
        Rect rect = this.mBounds;
        rect.set(rect.left, this.mBounds.top, n, (int)Math.min((float)n2, f));
    }
}

