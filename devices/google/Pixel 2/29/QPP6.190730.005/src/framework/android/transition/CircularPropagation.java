/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.transition.VisibilityPropagation;
import android.view.ViewGroup;

public class CircularPropagation
extends VisibilityPropagation {
    private static final String TAG = "CircularPropagation";
    private float mPropagationSpeed = 3.0f;

    private static double distance(float f, float f2, float f3, float f4) {
        return Math.hypot(f3 - f, f4 - f2);
    }

    @Override
    public long getStartDelay(ViewGroup viewGroup, Transition transition2, TransitionValues arrn, TransitionValues transitionValues) {
        int n;
        int n2;
        long l;
        if (arrn == null && transitionValues == null) {
            return 0L;
        }
        int n3 = 1;
        if (transitionValues != null && this.getViewVisibility((TransitionValues)arrn) != 0) {
            arrn = transitionValues;
        } else {
            n3 = -1;
        }
        int n4 = this.getViewX((TransitionValues)arrn);
        int n5 = this.getViewY((TransitionValues)arrn);
        arrn = transition2.getEpicenter();
        if (arrn != null) {
            n2 = arrn.centerX();
            n = arrn.centerY();
        } else {
            arrn = new int[2];
            viewGroup.getLocationOnScreen(arrn);
            n2 = Math.round((float)(arrn[0] + viewGroup.getWidth() / 2) + viewGroup.getTranslationX());
            n = Math.round((float)(arrn[1] + viewGroup.getHeight() / 2) + viewGroup.getTranslationY());
        }
        double d = CircularPropagation.distance(n4, n5, n2, n) / CircularPropagation.distance(0.0f, 0.0f, viewGroup.getWidth(), viewGroup.getHeight());
        long l2 = l = transition2.getDuration();
        if (l < 0L) {
            l2 = 300L;
        }
        return Math.round((double)((float)((long)n3 * l2) / this.mPropagationSpeed) * d);
    }

    public void setPropagationSpeed(float f) {
        if (f != 0.0f) {
            this.mPropagationSpeed = f;
            return;
        }
        throw new IllegalArgumentException("propagationSpeed may not be 0");
    }
}

