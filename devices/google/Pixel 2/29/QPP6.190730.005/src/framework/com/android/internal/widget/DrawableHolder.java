/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.DecelerateInterpolator;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawableHolder
implements Animator.AnimatorListener {
    private static final boolean DBG = false;
    public static final DecelerateInterpolator EASE_OUT_INTERPOLATOR = new DecelerateInterpolator();
    private static final String TAG = "DrawableHolder";
    private float mAlpha = 1.0f;
    private ArrayList<ObjectAnimator> mAnimators = new ArrayList();
    private BitmapDrawable mDrawable;
    private ArrayList<ObjectAnimator> mNeedToStart = new ArrayList();
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mX = 0.0f;
    private float mY = 0.0f;

    public DrawableHolder(BitmapDrawable bitmapDrawable) {
        this(bitmapDrawable, 0.0f, 0.0f);
    }

    public DrawableHolder(BitmapDrawable bitmapDrawable, float f, float f2) {
        this.mDrawable = bitmapDrawable;
        this.mX = f;
        this.mY = f2;
        this.mDrawable.getPaint().setAntiAlias(true);
        bitmapDrawable = this.mDrawable;
        bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(), this.mDrawable.getIntrinsicHeight());
    }

    private DrawableHolder addAnimation(ObjectAnimator objectAnimator, boolean bl) {
        if (objectAnimator != null) {
            this.mAnimators.add(objectAnimator);
        }
        this.mNeedToStart.add(objectAnimator);
        return this;
    }

    public ObjectAnimator addAnimTo(long l, long l2, String object, float f, boolean bl) {
        if (bl) {
            this.removeAnimationFor((String)object);
        }
        object = ObjectAnimator.ofFloat((Object)this, (String)object, f);
        ((ObjectAnimator)object).setDuration(l);
        ((ValueAnimator)object).setStartDelay(l2);
        ((ValueAnimator)object).setInterpolator(EASE_OUT_INTERPOLATOR);
        this.addAnimation((ObjectAnimator)object, bl);
        return object;
    }

    public void clearAnimations() {
        Iterator<ObjectAnimator> iterator = this.mAnimators.iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel();
        }
        this.mAnimators.clear();
    }

    public void draw(Canvas canvas) {
        if (this.mAlpha <= 0.00390625f) {
            return;
        }
        canvas.save(1);
        canvas.translate(this.mX, this.mY);
        canvas.scale(this.mScaleX, this.mScaleY);
        canvas.translate((float)this.getWidth() * -0.5f, (float)this.getHeight() * -0.5f);
        this.mDrawable.setAlpha(Math.round(this.mAlpha * 255.0f));
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public BitmapDrawable getDrawable() {
        return this.mDrawable;
    }

    public int getHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public int getWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    @Override
    public void onAnimationCancel(Animator animator2) {
    }

    @Override
    public void onAnimationEnd(Animator animator2) {
        this.mAnimators.remove(animator2);
    }

    @Override
    public void onAnimationRepeat(Animator animator2) {
    }

    @Override
    public void onAnimationStart(Animator animator2) {
    }

    public void removeAnimationFor(String string2) {
        for (ObjectAnimator objectAnimator : (ArrayList)this.mAnimators.clone()) {
            if (!string2.equals(objectAnimator.getPropertyName())) continue;
            objectAnimator.cancel();
        }
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public void setScaleX(float f) {
        this.mScaleX = f;
    }

    public void setScaleY(float f) {
        this.mScaleY = f;
    }

    public void setX(float f) {
        this.mX = f;
    }

    public void setY(float f) {
        this.mY = f;
    }

    public void startAnimations(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        for (int i = 0; i < this.mNeedToStart.size(); ++i) {
            ObjectAnimator objectAnimator = this.mNeedToStart.get(i);
            objectAnimator.addUpdateListener(animatorUpdateListener);
            objectAnimator.addListener(this);
            objectAnimator.start();
        }
        this.mNeedToStart.clear();
    }
}

