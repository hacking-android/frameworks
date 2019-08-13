/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.RippleComponent;
import android.graphics.drawable.RippleDrawable;
import android.util.FloatProperty;
import android.view.animation.LinearInterpolator;

class RippleBackground
extends RippleComponent {
    private static final TimeInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final BackgroundProperty OPACITY = new BackgroundProperty("opacity"){

        @Override
        public Float get(RippleBackground rippleBackground) {
            return Float.valueOf(rippleBackground.mOpacity);
        }

        @Override
        public void setValue(RippleBackground rippleBackground, float f) {
            rippleBackground.mOpacity = f;
            rippleBackground.invalidateSelf();
        }
    };
    private static final int OPACITY_DURATION = 80;
    private ObjectAnimator mAnimator;
    private boolean mFocused = false;
    private boolean mHovered = false;
    private boolean mIsBounded;
    private float mOpacity = 0.0f;

    public RippleBackground(RippleDrawable rippleDrawable, Rect rect, boolean bl) {
        super(rippleDrawable, rect);
        this.mIsBounded = bl;
    }

    private void onStateChanged() {
        float f = this.mFocused ? 0.6f : (this.mHovered ? 0.2f : 0.0f);
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mAnimator = null;
        }
        this.mAnimator = ObjectAnimator.ofFloat(this, OPACITY, f);
        this.mAnimator.setDuration(80L);
        this.mAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        this.mAnimator.start();
    }

    public void draw(Canvas canvas, Paint paint) {
        int n = paint.getAlpha();
        int n2 = Math.min((int)((float)n * this.mOpacity + 0.5f), 255);
        if (n2 > 0) {
            paint.setAlpha(n2);
            canvas.drawCircle(0.0f, 0.0f, this.mTargetRadius, paint);
            paint.setAlpha(n);
        }
    }

    public boolean isVisible() {
        boolean bl = this.mOpacity > 0.0f;
        return bl;
    }

    public void jumpToFinal() {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.end();
            this.mAnimator = null;
        }
    }

    public void setState(boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = this.mFocused;
        boolean bl5 = true;
        boolean bl6 = bl;
        if (!bl4) {
            bl = bl && !bl3;
            bl6 = bl;
        }
        bl = bl2;
        if (!this.mHovered) {
            bl = bl2 && !bl3 ? bl5 : false;
        }
        if (this.mHovered != bl || this.mFocused != bl6) {
            this.mHovered = bl;
            this.mFocused = bl6;
            this.onStateChanged();
        }
    }

    private static abstract class BackgroundProperty
    extends FloatProperty<RippleBackground> {
        public BackgroundProperty(String string2) {
            super(string2);
        }
    }

}

