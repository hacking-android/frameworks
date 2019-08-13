/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class Rotate
extends Transition {
    private static final String PROPNAME_ROTATION = "android:rotate:rotation";

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ROTATION, Float.valueOf(transitionValues.view.getRotation()));
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_ROTATION, Float.valueOf(transitionValues.view.getRotation()));
    }

    @Override
    public Animator createAnimator(ViewGroup view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null && transitionValues2 != null) {
            float f;
            view = transitionValues2.view;
            float f2 = ((Float)transitionValues.values.get(PROPNAME_ROTATION)).floatValue();
            if (f2 != (f = ((Float)transitionValues2.values.get(PROPNAME_ROTATION)).floatValue())) {
                view.setRotation(f2);
                return ObjectAnimator.ofFloat(view, View.ROTATION, f2, f);
            }
            return null;
        }
        return null;
    }
}

