/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Map;

public class Recolor
extends Transition {
    private static final String PROPNAME_BACKGROUND = "android:recolor:background";
    private static final String PROPNAME_TEXT_COLOR = "android:recolor:textColor";

    public Recolor() {
    }

    public Recolor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_BACKGROUND, transitionValues.view.getBackground());
        if (transitionValues.view instanceof TextView) {
            transitionValues.values.put(PROPNAME_TEXT_COLOR, ((TextView)transitionValues.view).getCurrentTextColor());
        }
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
    public Animator createAnimator(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues != null && transitionValues2 != null) {
            View view = transitionValues2.view;
            Drawable drawable2 = (Drawable)transitionValues.values.get(PROPNAME_BACKGROUND);
            object = (Drawable)transitionValues2.values.get(PROPNAME_BACKGROUND);
            if (drawable2 instanceof ColorDrawable && object instanceof ColorDrawable) {
                drawable2 = (ColorDrawable)drawable2;
                ColorDrawable colorDrawable = (ColorDrawable)object;
                if (((ColorDrawable)drawable2).getColor() != colorDrawable.getColor()) {
                    colorDrawable.setColor(((ColorDrawable)drawable2).getColor());
                    return ObjectAnimator.ofArgb(object, "color", ((ColorDrawable)drawable2).getColor(), colorDrawable.getColor());
                }
            }
            if (view instanceof TextView) {
                int n;
                object = (TextView)view;
                int n2 = (Integer)transitionValues.values.get(PROPNAME_TEXT_COLOR);
                if (n2 != (n = ((Integer)transitionValues2.values.get(PROPNAME_TEXT_COLOR)).intValue())) {
                    ((TextView)object).setTextColor(n);
                    return ObjectAnimator.ofArgb(object, "textColor", n2, n);
                }
            }
            return null;
        }
        return null;
    }
}

