/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.transition.VisibilityPropagation;
import android.view.View;
import android.view.ViewGroup;

public class SidePropagation
extends VisibilityPropagation {
    private static final String TAG = "SlidePropagation";
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    private int distance(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = this.mSide;
        int n10 = 0;
        int n11 = 0;
        if (n9 == 8388611) {
            if (view.getLayoutDirection() == 1) {
                n11 = 1;
            }
            n11 = n11 != 0 ? 5 : 3;
        } else if (n9 == 8388613) {
            n11 = n10;
            if (view.getLayoutDirection() == 1) {
                n11 = 1;
            }
            n11 = n11 != 0 ? 3 : 5;
        } else {
            n11 = this.mSide;
        }
        n10 = 0;
        n = n11 != 3 ? (n11 != 5 ? (n11 != 48 ? (n11 != 80 ? n10 : n2 - n6 + Math.abs(n3 - n)) : n8 - n2 + Math.abs(n3 - n)) : n - n5 + Math.abs(n4 - n2)) : n7 - n + Math.abs(n4 - n2);
        return n;
    }

    private int getMaxDistance(ViewGroup viewGroup) {
        int n = this.mSide;
        if (n != 3 && n != 5 && n != 8388611 && n != 8388613) {
            return viewGroup.getHeight();
        }
        return viewGroup.getWidth();
    }

    @Override
    public long getStartDelay(ViewGroup viewGroup, Transition transition2, TransitionValues arrn, TransitionValues transitionValues) {
        int n;
        long l;
        int n2;
        int n3;
        if (arrn == null && transitionValues == null) {
            return 0L;
        }
        Rect rect = transition2.getEpicenter();
        if (transitionValues != null && this.getViewVisibility((TransitionValues)arrn) != 0) {
            n2 = 1;
            arrn = transitionValues;
        } else {
            n2 = -1;
        }
        int n4 = this.getViewX((TransitionValues)arrn);
        int n5 = this.getViewY((TransitionValues)arrn);
        arrn = new int[2];
        viewGroup.getLocationOnScreen(arrn);
        int n6 = arrn[0] + Math.round(viewGroup.getTranslationX());
        int n7 = arrn[1] + Math.round(viewGroup.getTranslationY());
        int n8 = n6 + viewGroup.getWidth();
        int n9 = n7 + viewGroup.getHeight();
        if (rect != null) {
            n = rect.centerX();
            int n10 = rect.centerY();
            n3 = n;
            n = n10;
        } else {
            int n11 = (n6 + n8) / 2;
            n = (n7 + n9) / 2;
            n3 = n11;
        }
        float f = (float)this.distance(viewGroup, n4, n5, n3, n, n6, n7, n8, n9) / (float)this.getMaxDistance(viewGroup);
        long l2 = l = transition2.getDuration();
        if (l < 0L) {
            l2 = 300L;
        }
        return Math.round((float)((long)n2 * l2) / this.mPropagationSpeed * f);
    }

    public void setPropagationSpeed(float f) {
        if (f != 0.0f) {
            this.mPropagationSpeed = f;
            return;
        }
        throw new IllegalArgumentException("propagationSpeed may not be 0");
    }

    public void setSide(int n) {
        this.mSide = n;
    }
}

