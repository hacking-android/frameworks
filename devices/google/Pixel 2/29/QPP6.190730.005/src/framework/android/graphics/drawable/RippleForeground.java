/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.RippleComponent;
import android.graphics.drawable.RippleDrawable;
import android.util.FloatProperty;
import android.util.MathUtils;
import android.view.RenderNodeAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import java.util.ArrayList;

class RippleForeground
extends RippleComponent {
    private static final TimeInterpolator DECELERATE_INTERPOLATOR;
    private static final TimeInterpolator LINEAR_INTERPOLATOR;
    private static final FloatProperty<RippleForeground> OPACITY;
    private static final int OPACITY_ENTER_DURATION = 75;
    private static final int OPACITY_EXIT_DURATION = 150;
    private static final int OPACITY_HOLD_DURATION = 225;
    private static final int RIPPLE_ENTER_DURATION = 225;
    private static final int RIPPLE_ORIGIN_DURATION = 225;
    private static final FloatProperty<RippleForeground> TWEEN_ORIGIN;
    private static final FloatProperty<RippleForeground> TWEEN_RADIUS;
    private final AnimatorListenerAdapter mAnimationListener = new AnimatorListenerAdapter(){

        @Override
        public void onAnimationEnd(Animator animator2) {
            RippleForeground.this.mHasFinishedExit = true;
            RippleForeground.this.pruneHwFinished();
            RippleForeground.this.pruneSwFinished();
            if (RippleForeground.this.mRunningHwAnimators.isEmpty()) {
                RippleForeground.this.clearHwProps();
            }
        }
    };
    private float mClampedStartingX;
    private float mClampedStartingY;
    private long mEnterStartedAtMillis;
    private final boolean mForceSoftware;
    private boolean mHasFinishedExit;
    private float mOpacity = 0.0f;
    private ArrayList<RenderNodeAnimator> mPendingHwAnimators = new ArrayList();
    private CanvasProperty<Paint> mPropPaint;
    private CanvasProperty<Float> mPropRadius;
    private CanvasProperty<Float> mPropX;
    private CanvasProperty<Float> mPropY;
    private ArrayList<RenderNodeAnimator> mRunningHwAnimators = new ArrayList();
    private ArrayList<Animator> mRunningSwAnimators = new ArrayList();
    private float mStartRadius = 0.0f;
    private float mStartingX;
    private float mStartingY;
    private float mTargetX = 0.0f;
    private float mTargetY = 0.0f;
    private float mTweenRadius = 0.0f;
    private float mTweenX = 0.0f;
    private float mTweenY = 0.0f;
    private boolean mUsingProperties;

    static {
        LINEAR_INTERPOLATOR = new LinearInterpolator();
        DECELERATE_INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        TWEEN_RADIUS = new FloatProperty<RippleForeground>("tweenRadius"){

            @Override
            public Float get(RippleForeground rippleForeground) {
                return Float.valueOf(rippleForeground.mTweenRadius);
            }

            @Override
            public void setValue(RippleForeground rippleForeground, float f) {
                rippleForeground.mTweenRadius = f;
                rippleForeground.onAnimationPropertyChanged();
            }
        };
        TWEEN_ORIGIN = new FloatProperty<RippleForeground>("tweenOrigin"){

            @Override
            public Float get(RippleForeground rippleForeground) {
                return Float.valueOf(rippleForeground.mTweenX);
            }

            @Override
            public void setValue(RippleForeground rippleForeground, float f) {
                rippleForeground.mTweenX = f;
                rippleForeground.mTweenY = f;
                rippleForeground.onAnimationPropertyChanged();
            }
        };
        OPACITY = new FloatProperty<RippleForeground>("opacity"){

            @Override
            public Float get(RippleForeground rippleForeground) {
                return Float.valueOf(rippleForeground.mOpacity);
            }

            @Override
            public void setValue(RippleForeground rippleForeground, float f) {
                rippleForeground.mOpacity = f;
                rippleForeground.onAnimationPropertyChanged();
            }
        };
    }

    public RippleForeground(RippleDrawable rippleDrawable, Rect rect, float f, float f2, boolean bl) {
        super(rippleDrawable, rect);
        this.mForceSoftware = bl;
        this.mStartingX = f;
        this.mStartingY = f2;
        this.mStartRadius = (float)Math.max(rect.width(), rect.height()) * 0.3f;
        this.clampStartingPosition();
    }

    private void clampStartingPosition() {
        float f;
        float f2;
        float f3;
        float f4 = this.mBounds.exactCenterX();
        float f5 = this.mStartingX - f4;
        if (f5 * f5 + (f3 = this.mStartingY - (f2 = this.mBounds.exactCenterY())) * f3 > (f = this.mTargetRadius - this.mStartRadius) * f) {
            double d = Math.atan2(f3, f5);
            this.mClampedStartingX = (float)(Math.cos(d) * (double)f) + f4;
            this.mClampedStartingY = (float)(Math.sin(d) * (double)f) + f2;
        } else {
            this.mClampedStartingX = this.mStartingX;
            this.mClampedStartingY = this.mStartingY;
        }
    }

    private void clearHwProps() {
        this.mPropPaint = null;
        this.mPropRadius = null;
        this.mPropX = null;
        this.mPropY = null;
        this.mUsingProperties = false;
    }

    private long computeFadeOutDelay() {
        long l = AnimationUtils.currentAnimationTimeMillis() - this.mEnterStartedAtMillis;
        if (l > 0L && l < 225L) {
            return 225L - l;
        }
        return 0L;
    }

    private void drawHardware(RecordingCanvas recordingCanvas, Paint paint) {
        this.startPending(recordingCanvas);
        this.pruneHwFinished();
        CanvasProperty<Paint> canvasProperty = this.mPropPaint;
        if (canvasProperty != null) {
            this.mUsingProperties = true;
            recordingCanvas.drawCircle(this.mPropX, this.mPropY, this.mPropRadius, canvasProperty);
        } else {
            this.mUsingProperties = false;
            this.drawSoftware(recordingCanvas, paint);
        }
    }

    private void drawSoftware(Canvas canvas, Paint paint) {
        int n = paint.getAlpha();
        int n2 = (int)((float)n * this.mOpacity + 0.5f);
        float f = this.getCurrentRadius();
        if (n2 > 0 && f > 0.0f) {
            float f2 = this.getCurrentX();
            float f3 = this.getCurrentY();
            paint.setAlpha(n2);
            canvas.drawCircle(f2, f3, f, paint);
            paint.setAlpha(n);
        }
    }

    private float getCurrentRadius() {
        return MathUtils.lerp(this.mStartRadius, this.mTargetRadius, this.mTweenRadius);
    }

    private float getCurrentX() {
        return MathUtils.lerp(this.mClampedStartingX - this.mBounds.exactCenterX(), this.mTargetX, this.mTweenX);
    }

    private float getCurrentY() {
        return MathUtils.lerp(this.mClampedStartingY - this.mBounds.exactCenterY(), this.mTargetY, this.mTweenY);
    }

    private void onAnimationPropertyChanged() {
        if (!this.mUsingProperties) {
            this.invalidateSelf();
        }
    }

    private void pruneHwFinished() {
        if (!this.mRunningHwAnimators.isEmpty()) {
            for (int i = this.mRunningHwAnimators.size() - 1; i >= 0; --i) {
                if (this.mRunningHwAnimators.get(i).isRunning()) continue;
                this.mRunningHwAnimators.remove(i);
            }
        }
    }

    private void pruneSwFinished() {
        if (!this.mRunningSwAnimators.isEmpty()) {
            for (int i = this.mRunningSwAnimators.size() - 1; i >= 0; --i) {
                if (this.mRunningSwAnimators.get(i).isRunning()) continue;
                this.mRunningSwAnimators.remove(i);
            }
        }
    }

    private void startHardwareEnter() {
        if (this.mForceSoftware) {
            return;
        }
        this.mPropX = CanvasProperty.createFloat(this.getCurrentX());
        this.mPropY = CanvasProperty.createFloat(this.getCurrentY());
        this.mPropRadius = CanvasProperty.createFloat(this.getCurrentRadius());
        Object object = this.mOwner.getRipplePaint();
        this.mPropPaint = CanvasProperty.createPaint((Paint)object);
        RenderNodeAnimator renderNodeAnimator = new RenderNodeAnimator(this.mPropRadius, this.mTargetRadius);
        renderNodeAnimator.setDuration(225L);
        renderNodeAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        this.mPendingHwAnimators.add(renderNodeAnimator);
        renderNodeAnimator = new RenderNodeAnimator(this.mPropX, this.mTargetX);
        renderNodeAnimator.setDuration(225L);
        renderNodeAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        this.mPendingHwAnimators.add(renderNodeAnimator);
        renderNodeAnimator = new RenderNodeAnimator(this.mPropY, this.mTargetY);
        renderNodeAnimator.setDuration(225L);
        renderNodeAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        this.mPendingHwAnimators.add(renderNodeAnimator);
        object = new RenderNodeAnimator(this.mPropPaint, 1, ((Paint)object).getAlpha());
        ((RenderNodeAnimator)object).setDuration(75L);
        ((RenderNodeAnimator)object).setInterpolator(LINEAR_INTERPOLATOR);
        ((RenderNodeAnimator)object).setStartValue(0.0f);
        this.mPendingHwAnimators.add((RenderNodeAnimator)object);
        this.invalidateSelf();
    }

    private void startHardwareExit() {
        Object object;
        if (!this.mForceSoftware && (object = this.mPropPaint) != null) {
            object = new RenderNodeAnimator((CanvasProperty<Paint>)object, 1, 0.0f);
            ((RenderNodeAnimator)object).setDuration(150L);
            ((RenderNodeAnimator)object).setInterpolator(LINEAR_INTERPOLATOR);
            ((Animator)object).addListener(this.mAnimationListener);
            ((RenderNodeAnimator)object).setStartDelay(this.computeFadeOutDelay());
            ((RenderNodeAnimator)object).setStartValue(this.mOwner.getRipplePaint().getAlpha());
            this.mPendingHwAnimators.add((RenderNodeAnimator)object);
            this.invalidateSelf();
            return;
        }
    }

    private void startPending(RecordingCanvas recordingCanvas) {
        if (!this.mPendingHwAnimators.isEmpty()) {
            for (int i = 0; i < this.mPendingHwAnimators.size(); ++i) {
                RenderNodeAnimator renderNodeAnimator = this.mPendingHwAnimators.get(i);
                renderNodeAnimator.setTarget(recordingCanvas);
                renderNodeAnimator.start();
                this.mRunningHwAnimators.add(renderNodeAnimator);
            }
            this.mPendingHwAnimators.clear();
        }
    }

    private void startSoftwareEnter() {
        for (int i = 0; i < this.mRunningSwAnimators.size(); ++i) {
            this.mRunningSwAnimators.get(i).cancel();
        }
        this.mRunningSwAnimators.clear();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, TWEEN_RADIUS, 1.0f);
        objectAnimator.setDuration(225L);
        objectAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        objectAnimator.start();
        this.mRunningSwAnimators.add(objectAnimator);
        objectAnimator = ObjectAnimator.ofFloat(this, TWEEN_ORIGIN, 1.0f);
        objectAnimator.setDuration(225L);
        objectAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        objectAnimator.start();
        this.mRunningSwAnimators.add(objectAnimator);
        objectAnimator = ObjectAnimator.ofFloat(this, OPACITY, 1.0f);
        objectAnimator.setDuration(75L);
        objectAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        objectAnimator.start();
        this.mRunningSwAnimators.add(objectAnimator);
    }

    private void startSoftwareExit() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, OPACITY, 0.0f);
        objectAnimator.setDuration(150L);
        objectAnimator.setInterpolator(LINEAR_INTERPOLATOR);
        objectAnimator.addListener(this.mAnimationListener);
        objectAnimator.setStartDelay(this.computeFadeOutDelay());
        objectAnimator.start();
        this.mRunningSwAnimators.add(objectAnimator);
    }

    private void switchToUiThreadAnimation() {
        for (int i = 0; i < this.mRunningHwAnimators.size(); ++i) {
            Animator animator2 = this.mRunningHwAnimators.get(i);
            animator2.removeListener(this.mAnimationListener);
            animator2.end();
        }
        this.mRunningHwAnimators.clear();
        this.clearHwProps();
        this.invalidateSelf();
    }

    public void draw(Canvas canvas, Paint paint) {
        boolean bl = !this.mForceSoftware && canvas instanceof RecordingCanvas;
        this.pruneSwFinished();
        if (bl) {
            this.drawHardware((RecordingCanvas)canvas, paint);
        } else {
            this.drawSoftware(canvas, paint);
        }
    }

    public void end() {
        int n;
        for (n = 0; n < this.mRunningSwAnimators.size(); ++n) {
            this.mRunningSwAnimators.get(n).end();
        }
        this.mRunningSwAnimators.clear();
        for (n = 0; n < this.mRunningHwAnimators.size(); ++n) {
            this.mRunningHwAnimators.get(n).end();
        }
        this.mRunningHwAnimators.clear();
    }

    public final void enter() {
        this.mEnterStartedAtMillis = AnimationUtils.currentAnimationTimeMillis();
        this.startSoftwareEnter();
        this.startHardwareEnter();
    }

    public final void exit() {
        this.startSoftwareExit();
        this.startHardwareExit();
    }

    @Override
    public void getBounds(Rect rect) {
        int n = (int)this.mTargetX;
        int n2 = (int)this.mTargetY;
        int n3 = (int)this.mTargetRadius + 1;
        rect.set(n - n3, n2 - n3, n + n3, n2 + n3);
    }

    public boolean hasFinishedExit() {
        return this.mHasFinishedExit;
    }

    public void move(float f, float f2) {
        this.mStartingX = f;
        this.mStartingY = f2;
        this.clampStartingPosition();
    }

    @Override
    protected void onTargetRadiusChanged(float f) {
        this.clampStartingPosition();
        this.switchToUiThreadAnimation();
    }

}

