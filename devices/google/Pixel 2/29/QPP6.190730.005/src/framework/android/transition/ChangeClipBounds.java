/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

public class ChangeClipBounds
extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String TAG = "ChangeTransform";
    private static final String[] sTransitionProperties = new String[]{"android:clipBounds:clip"};

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        Rect rect = view.getClipBounds();
        transitionValues.values.put(PROPNAME_CLIP, rect);
        if (rect == null) {
            rect = new Rect(0, 0, view.getWidth(), view.getHeight());
            transitionValues.values.put(PROPNAME_BOUNDS, rect);
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
    public Animator createAnimator(ViewGroup object, TransitionValues object2, TransitionValues transitionValues) {
        if (object2 != null && transitionValues != null && ((TransitionValues)object2).values.containsKey(PROPNAME_CLIP) && transitionValues.values.containsKey(PROPNAME_CLIP)) {
            Rect rect = (Rect)((TransitionValues)object2).values.get(PROPNAME_CLIP);
            Object object3 = (Rect)transitionValues.values.get(PROPNAME_CLIP);
            boolean bl = object3 == null;
            if (rect == null && object3 == null) {
                return null;
            }
            if (rect == null) {
                object2 = (Rect)((TransitionValues)object2).values.get(PROPNAME_BOUNDS);
                object = object3;
            } else {
                object2 = rect;
                object = object3;
                if (object3 == null) {
                    object = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
                    object2 = rect;
                }
            }
            if (((Rect)object2).equals(object)) {
                return null;
            }
            transitionValues.view.setClipBounds((Rect)object2);
            object3 = new RectEvaluator(new Rect());
            object = ObjectAnimator.ofObject((Object)transitionValues.view, "clipBounds", (TypeEvaluator)object3, object2, object);
            if (bl) {
                ((Animator)object).addListener(new AnimatorListenerAdapter(transitionValues.view){
                    final /* synthetic */ View val$endView;
                    {
                        this.val$endView = view;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        this.val$endView.setClipBounds(null);
                    }
                });
            }
            return object;
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}

