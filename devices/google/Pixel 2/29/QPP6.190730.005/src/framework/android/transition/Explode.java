/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.transition.CircularPropagation;
import android.transition.TransitionPropagation;
import android.transition.TransitionValues;
import android.transition.TranslationAnimationCreator;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Map;

public class Explode
extends Visibility {
    private static final String PROPNAME_SCREEN_BOUNDS = "android:explode:screenBounds";
    private static final String TAG = "Explode";
    private static final TimeInterpolator sAccelerate;
    private static final TimeInterpolator sDecelerate;
    private int[] mTempLoc = new int[2];

    static {
        sDecelerate = new DecelerateInterpolator();
        sAccelerate = new AccelerateInterpolator();
    }

    public Explode() {
        this.setPropagation(new CircularPropagation());
    }

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setPropagation(new CircularPropagation());
    }

    private static double calculateMaxDistance(View view, int n, int n2) {
        n = Math.max(n, view.getWidth() - n);
        n2 = Math.max(n2, view.getHeight() - n2);
        return Math.hypot(n, n2);
    }

    private void calculateOut(View view, Rect rect, int[] arrn) {
        int n;
        int n2;
        view.getLocationOnScreen(this.mTempLoc);
        Object object = this.mTempLoc;
        int n3 = object[0];
        int n4 = object[1];
        object = this.getEpicenter();
        if (object == null) {
            n2 = view.getWidth() / 2 + n3 + Math.round(view.getTranslationX());
            n = view.getHeight() / 2 + n4 + Math.round(view.getTranslationY());
        } else {
            n2 = ((Rect)object).centerX();
            n = ((Rect)object).centerY();
        }
        int n5 = rect.centerX();
        int n6 = rect.centerY();
        double d = n5 - n2;
        double d2 = n6 - n;
        double d3 = d;
        double d4 = d2;
        if (d == 0.0) {
            d3 = d;
            d4 = d2;
            if (d2 == 0.0) {
                d3 = Math.random() * 2.0 - 1.0;
                d4 = Math.random() * 2.0 - 1.0;
            }
        }
        d = Math.hypot(d3, d4);
        d3 /= d;
        d = d4 / d;
        d4 = Explode.calculateMaxDistance(view, n2 - n3, n - n4);
        arrn[0] = (int)Math.round(d4 * d3);
        arrn[1] = (int)Math.round(d4 * d);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        view.getLocationOnScreen(this.mTempLoc);
        int[] arrn = this.mTempLoc;
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = view.getWidth();
        int n4 = view.getHeight();
        transitionValues.values.put(PROPNAME_SCREEN_BOUNDS, new Rect(n, n2, n3 + n, n4 + n2));
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public Animator onAppear(ViewGroup arrn, View view, TransitionValues object, TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        object = (Rect)transitionValues.values.get(PROPNAME_SCREEN_BOUNDS);
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        this.calculateOut((View)arrn, (Rect)object, this.mTempLoc);
        arrn = this.mTempLoc;
        float f3 = arrn[0];
        float f4 = arrn[1];
        return TranslationAnimationCreator.createAnimation(view, transitionValues, ((Rect)object).left, ((Rect)object).top, f + f3, f2 + f4, f, f2, sDecelerate, this);
    }

    @Override
    public Animator onDisappear(ViewGroup arrn, View view, TransitionValues transitionValues, TransitionValues arrn2) {
        if (transitionValues == null) {
            return null;
        }
        Rect rect = (Rect)transitionValues.values.get(PROPNAME_SCREEN_BOUNDS);
        int n = rect.left;
        int n2 = rect.top;
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        float f3 = f;
        float f4 = f2;
        arrn2 = (int[])transitionValues.view.getTag(16909489);
        float f5 = f3;
        float f6 = f4;
        if (arrn2 != null) {
            f5 = f3 + (float)(arrn2[0] - rect.left);
            f6 = f4 + (float)(arrn2[1] - rect.top);
            rect.offsetTo(arrn2[0], arrn2[1]);
        }
        this.calculateOut((View)arrn, rect, this.mTempLoc);
        arrn = this.mTempLoc;
        return TranslationAnimationCreator.createAnimation(view, transitionValues, n, n2, f, f2, f5 + (float)arrn[0], f6 + (float)arrn[1], sAccelerate, this);
    }
}

