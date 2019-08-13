/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionUtils;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeScroll
extends Transition {
    private static final String[] PROPERTIES = new String[]{"android:changeScroll:x", "android:changeScroll:y"};
    private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
    private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";

    public ChangeScroll() {
    }

    public ChangeScroll(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_SCROLL_X, transitionValues.view.getScrollX());
        transitionValues.values.put(PROPNAME_SCROLL_Y, transitionValues.view.getScrollY());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues object2, TransitionValues transitionValues) {
        if (object2 != null && transitionValues != null) {
            View view = transitionValues.view;
            int n = (Integer)((TransitionValues)object2).values.get(PROPNAME_SCROLL_X);
            int n2 = (Integer)transitionValues.values.get(PROPNAME_SCROLL_X);
            int n3 = (Integer)((TransitionValues)object2).values.get(PROPNAME_SCROLL_Y);
            int n4 = (Integer)transitionValues.values.get(PROPNAME_SCROLL_Y);
            object = null;
            object2 = null;
            if (n != n2) {
                view.setScrollX(n);
                object = ObjectAnimator.ofInt((Object)view, "scrollX", n, n2);
            }
            if (n3 != n4) {
                view.setScrollY(n3);
                object2 = ObjectAnimator.ofInt((Object)view, "scrollY", n3, n4);
            }
            return TransitionUtils.mergeAnimators((Animator)object, (Animator)object2);
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return PROPERTIES;
    }
}

