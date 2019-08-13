/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.android.internal.R;
import java.util.Random;

public class LayoutAnimationController {
    public static final int ORDER_NORMAL = 0;
    public static final int ORDER_RANDOM = 2;
    public static final int ORDER_REVERSE = 1;
    protected Animation mAnimation;
    private float mDelay;
    private long mDuration;
    protected Interpolator mInterpolator;
    private long mMaxDelay;
    private int mOrder;
    protected Random mRandomizer;

    public LayoutAnimationController(Context context, AttributeSet object) {
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.LayoutAnimation);
        this.mDelay = Animation.Description.parseValue((TypedValue)object.peekValue((int)1)).value;
        this.mOrder = ((TypedArray)object).getInt(3, 0);
        int n = ((TypedArray)object).getResourceId(2, 0);
        if (n > 0) {
            this.setAnimation(context, n);
        }
        if ((n = ((TypedArray)object).getResourceId(0, 0)) > 0) {
            this.setInterpolator(context, n);
        }
        ((TypedArray)object).recycle();
    }

    public LayoutAnimationController(Animation animation) {
        this(animation, 0.5f);
    }

    public LayoutAnimationController(Animation animation, float f) {
        this.mDelay = f;
        this.setAnimation(animation);
    }

    public Animation getAnimation() {
        return this.mAnimation;
    }

    public final Animation getAnimationForView(View object) {
        long l = this.getDelayForView((View)object) + this.mAnimation.getStartOffset();
        this.mMaxDelay = Math.max(this.mMaxDelay, l);
        try {
            object = this.mAnimation.clone();
            ((Animation)object).setStartOffset(l);
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    public float getDelay() {
        return this.mDelay;
    }

    protected long getDelayForView(View object) {
        object = object.getLayoutParams().layoutAnimationParameters;
        if (object == null) {
            return 0L;
        }
        float f = this.mDelay * (float)this.mAnimation.getDuration();
        long l = (long)((float)this.getTransformedIndex((AnimationParameters)object) * f);
        f = (float)((AnimationParameters)object).count * f;
        if (this.mInterpolator == null) {
            this.mInterpolator = new LinearInterpolator();
        }
        float f2 = (float)l / f;
        return (long)(this.mInterpolator.getInterpolation(f2) * f);
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public int getOrder() {
        return this.mOrder;
    }

    protected int getTransformedIndex(AnimationParameters animationParameters) {
        int n = this.getOrder();
        if (n != 1) {
            if (n != 2) {
                return animationParameters.index;
            }
            if (this.mRandomizer == null) {
                this.mRandomizer = new Random();
            }
            return (int)((float)animationParameters.count * this.mRandomizer.nextFloat());
        }
        return animationParameters.count - 1 - animationParameters.index;
    }

    public boolean isDone() {
        boolean bl = AnimationUtils.currentAnimationTimeMillis() > this.mAnimation.getStartTime() + this.mMaxDelay + this.mDuration;
        return bl;
    }

    public void setAnimation(Context context, int n) {
        this.setAnimation(AnimationUtils.loadAnimation(context, n));
    }

    public void setAnimation(Animation animation) {
        this.mAnimation = animation;
        this.mAnimation.setFillBefore(true);
    }

    public void setDelay(float f) {
        this.mDelay = f;
    }

    public void setInterpolator(Context context, int n) {
        this.setInterpolator(AnimationUtils.loadInterpolator(context, n));
    }

    public void setInterpolator(Interpolator interpolator2) {
        this.mInterpolator = interpolator2;
    }

    public void setOrder(int n) {
        this.mOrder = n;
    }

    public void start() {
        this.mDuration = this.mAnimation.getDuration();
        this.mMaxDelay = Long.MIN_VALUE;
        this.mAnimation.setStartTime(-1L);
    }

    public boolean willOverlap() {
        boolean bl = this.mDelay < 1.0f;
        return bl;
    }

    public static class AnimationParameters {
        public int count;
        public int index;
    }

}

