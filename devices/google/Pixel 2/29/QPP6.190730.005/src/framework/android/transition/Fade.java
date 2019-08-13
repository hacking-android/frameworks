/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import java.util.Map;

public class Fade
extends Visibility {
    private static boolean DBG = false;
    public static final int IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    static final String PROPNAME_TRANSITION_ALPHA = "android:fade:transitionAlpha";

    static {
        DBG = false;
    }

    public Fade() {
    }

    public Fade(int n) {
        this.setMode(n);
    }

    public Fade(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Fade);
        this.setMode(((TypedArray)object).getInt(0, this.getMode()));
        ((TypedArray)object).recycle();
    }

    private Animator createAnimation(final View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        view.setTransitionAlpha(f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)view, "transitionAlpha", f2);
        if (DBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Created animator ");
            stringBuilder.append(objectAnimator);
            Log.d(LOG_TAG, stringBuilder.toString());
        }
        objectAnimator.addListener(new FadeAnimatorListener(view));
        this.addListener(new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition2) {
                view.setTransitionAlpha(1.0f);
                transition2.removeListener(this);
            }
        });
        return objectAnimator;
    }

    private static float getStartAlpha(TransitionValues object, float f) {
        float f2 = f;
        if (object != null) {
            object = (Float)((TransitionValues)object).values.get(PROPNAME_TRANSITION_ALPHA);
            f2 = f;
            if (object != null) {
                f2 = ((Float)object).floatValue();
            }
        }
        return f2;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        transitionValues.values.put(PROPNAME_TRANSITION_ALPHA, Float.valueOf(transitionValues.view.getTransitionAlpha()));
    }

    @Override
    public Animator onAppear(ViewGroup view, View view2, TransitionValues transitionValues, TransitionValues object) {
        float f;
        if (DBG) {
            view = transitionValues != null ? transitionValues.view : null;
            object = new StringBuilder();
            ((StringBuilder)object).append("Fade.onAppear: startView, startVis, endView, endVis = ");
            ((StringBuilder)object).append(view);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(view2);
            Log.d(LOG_TAG, ((StringBuilder)object).toString());
        }
        float f2 = f = Fade.getStartAlpha(transitionValues, 0.0f);
        if (f == 1.0f) {
            f2 = 0.0f;
        }
        return this.createAnimation(view2, f2, 1.0f);
    }

    @Override
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return this.createAnimation(view, Fade.getStartAlpha(transitionValues, 1.0f), 0.0f);
    }

    private static class FadeAnimatorListener
    extends AnimatorListenerAdapter {
        private boolean mLayerTypeChanged = false;
        private final View mView;

        public FadeAnimatorListener(View view) {
            this.mView = view;
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            this.mView.setTransitionAlpha(1.0f);
            if (this.mLayerTypeChanged) {
                this.mView.setLayerType(0, null);
            }
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            if (this.mView.hasOverlappingRendering() && this.mView.getLayerType() == 0) {
                this.mLayerTypeChanged = true;
                this.mView.setLayerType(2, null);
            }
        }
    }

}

