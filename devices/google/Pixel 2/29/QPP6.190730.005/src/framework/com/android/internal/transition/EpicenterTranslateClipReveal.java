/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.android.internal.R;
import com.android.internal.transition.TransitionConstants;
import java.util.Map;

public class EpicenterTranslateClipReveal
extends Visibility {
    private static final String PROPNAME_BOUNDS = "android:epicenterReveal:bounds";
    private static final String PROPNAME_CLIP = "android:epicenterReveal:clip";
    private static final String PROPNAME_TRANSLATE_X = "android:epicenterReveal:translateX";
    private static final String PROPNAME_TRANSLATE_Y = "android:epicenterReveal:translateY";
    private static final String PROPNAME_TRANSLATE_Z = "android:epicenterReveal:translateZ";
    private static final String PROPNAME_Z = "android:epicenterReveal:z";
    private final TimeInterpolator mInterpolatorX;
    private final TimeInterpolator mInterpolatorY;
    private final TimeInterpolator mInterpolatorZ;

    public EpicenterTranslateClipReveal() {
        this.mInterpolatorX = null;
        this.mInterpolatorY = null;
        this.mInterpolatorZ = null;
    }

    public EpicenterTranslateClipReveal(Context context, AttributeSet object) {
        super(context, (AttributeSet)object);
        object = context.obtainStyledAttributes((AttributeSet)object, R.styleable.EpicenterTranslateClipReveal, 0, 0);
        int n = ((TypedArray)object).getResourceId(0, 0);
        this.mInterpolatorX = n != 0 ? AnimationUtils.loadInterpolator(context, n) : TransitionConstants.LINEAR_OUT_SLOW_IN;
        n = ((TypedArray)object).getResourceId(1, 0);
        this.mInterpolatorY = n != 0 ? AnimationUtils.loadInterpolator(context, n) : TransitionConstants.FAST_OUT_SLOW_IN;
        n = ((TypedArray)object).getResourceId(2, 0);
        this.mInterpolatorZ = n != 0 ? AnimationUtils.loadInterpolator(context, n) : TransitionConstants.FAST_OUT_SLOW_IN;
        ((TypedArray)object).recycle();
    }

    private void captureValues(TransitionValues transitionValues) {
        Object object = transitionValues.view;
        if (((View)object).getVisibility() == 8) {
            return;
        }
        Rect rect = new Rect(0, 0, ((View)object).getWidth(), ((View)object).getHeight());
        transitionValues.values.put(PROPNAME_BOUNDS, rect);
        transitionValues.values.put(PROPNAME_TRANSLATE_X, Float.valueOf(((View)object).getTranslationX()));
        transitionValues.values.put(PROPNAME_TRANSLATE_Y, Float.valueOf(((View)object).getTranslationY()));
        transitionValues.values.put(PROPNAME_TRANSLATE_Z, Float.valueOf(((View)object).getTranslationZ()));
        transitionValues.values.put(PROPNAME_Z, Float.valueOf(((View)object).getZ()));
        object = ((View)object).getClipBounds();
        transitionValues.values.put(PROPNAME_CLIP, object);
    }

    private static Animator createRectAnimator(View object, State object2, State object3, float f, State object4, State state, float f2, TransitionValues transitionValues, TimeInterpolator timeInterpolator, TimeInterpolator timeInterpolator2, TimeInterpolator timeInterpolator3) {
        StateEvaluator stateEvaluator = new StateEvaluator();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(object, View.TRANSLATION_Z, f, f2);
        if (timeInterpolator3 != null) {
            objectAnimator.setInterpolator(timeInterpolator3);
        }
        object2 = ObjectAnimator.ofObject(object, new StateProperty('x'), stateEvaluator, object2, object4);
        if (timeInterpolator != null) {
            ((ValueAnimator)object2).setInterpolator(timeInterpolator);
        }
        object3 = ObjectAnimator.ofObject(object, new StateProperty('y'), stateEvaluator, object3, state);
        if (timeInterpolator2 != null) {
            ((ValueAnimator)object3).setInterpolator(timeInterpolator2);
        }
        object = new AnimatorListenerAdapter((Rect)transitionValues.values.get(PROPNAME_CLIP)){
            final /* synthetic */ Rect val$terminalClip;
            {
                this.val$terminalClip = rect;
            }

            @Override
            public void onAnimationEnd(Animator animator2) {
                View.this.setClipBounds(this.val$terminalClip);
            }
        };
        object4 = new AnimatorSet();
        ((AnimatorSet)object4).playTogether(new Animator[]{object2, object3, objectAnimator});
        ((Animator)object4).addListener((Animator.AnimatorListener)object);
        return object4;
    }

    private Rect getBestRect(TransitionValues transitionValues) {
        Rect rect = (Rect)transitionValues.values.get(PROPNAME_CLIP);
        if (rect == null) {
            return (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
        }
        return rect;
    }

    private Rect getEpicenterOrCenter(Rect rect) {
        Rect rect2 = this.getEpicenter();
        if (rect2 != null) {
            return rect2;
        }
        int n = rect.centerX();
        int n2 = rect.centerY();
        return new Rect(n, n2, n, n2);
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
    public Animator onAppear(ViewGroup object, View view, TransitionValues object2, TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        object2 = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
        object = this.getEpicenterOrCenter((Rect)object2);
        float f = ((Rect)object).centerX() - ((Rect)object2).centerX();
        float f2 = ((Rect)object).centerY() - ((Rect)object2).centerY();
        float f3 = 0.0f - ((Float)transitionValues.values.get(PROPNAME_Z)).floatValue();
        view.setTranslationX(f);
        view.setTranslationY(f2);
        view.setTranslationZ(f3);
        float f4 = ((Float)transitionValues.values.get(PROPNAME_TRANSLATE_X)).floatValue();
        float f5 = ((Float)transitionValues.values.get(PROPNAME_TRANSLATE_Y)).floatValue();
        float f6 = ((Float)transitionValues.values.get(PROPNAME_TRANSLATE_Z)).floatValue();
        Rect rect = this.getBestRect(transitionValues);
        object = this.getEpicenterOrCenter(rect);
        view.setClipBounds((Rect)object);
        object2 = new State(((Rect)object).left, ((Rect)object).right, f);
        State state = new State(rect.left, rect.right, f4);
        return EpicenterTranslateClipReveal.createRectAnimator(view, (State)object2, new State(((Rect)object).top, ((Rect)object).bottom, f2), f3, state, new State(rect.top, rect.bottom, f5), f6, transitionValues, this.mInterpolatorX, this.mInterpolatorY, this.mInterpolatorZ);
    }

    @Override
    public Animator onDisappear(ViewGroup object, View view, TransitionValues object2, TransitionValues transitionValues) {
        if (object2 == null) {
            return null;
        }
        Object object3 = (Rect)transitionValues.values.get(PROPNAME_BOUNDS);
        object = this.getEpicenterOrCenter((Rect)object3);
        float f = ((Rect)object).centerX() - ((Rect)object3).centerX();
        float f2 = ((Rect)object).centerY() - ((Rect)object3).centerY();
        float f3 = ((Float)((TransitionValues)object2).values.get(PROPNAME_Z)).floatValue();
        float f4 = ((Float)transitionValues.values.get(PROPNAME_TRANSLATE_X)).floatValue();
        float f5 = ((Float)transitionValues.values.get(PROPNAME_TRANSLATE_Y)).floatValue();
        float f6 = ((Float)transitionValues.values.get(PROPNAME_TRANSLATE_Z)).floatValue();
        object = this.getBestRect((TransitionValues)object2);
        Rect rect = this.getEpicenterOrCenter((Rect)object);
        view.setClipBounds((Rect)object);
        object2 = new State(((Rect)object).left, ((Rect)object).right, f4);
        object3 = new State(rect.left, rect.right, f);
        return EpicenterTranslateClipReveal.createRectAnimator(view, (State)object2, new State(((Rect)object).top, ((Rect)object).bottom, f5), f6, (State)object3, new State(rect.top, rect.bottom, f2), 0.0f - f3, transitionValues, this.mInterpolatorX, this.mInterpolatorY, this.mInterpolatorZ);
    }

    private static class State {
        int lower;
        float trans;
        int upper;

        public State() {
        }

        public State(int n, int n2, float f) {
            this.lower = n;
            this.upper = n2;
            this.trans = f;
        }
    }

    private static class StateEvaluator
    implements TypeEvaluator<State> {
        private final State mTemp = new State();

        private StateEvaluator() {
        }

        @Override
        public State evaluate(float f, State state, State state2) {
            this.mTemp.upper = state.upper + (int)((float)(state2.upper - state.upper) * f);
            this.mTemp.lower = state.lower + (int)((float)(state2.lower - state.lower) * f);
            this.mTemp.trans = state.trans + (float)((int)((state2.trans - state.trans) * f));
            return this.mTemp;
        }
    }

    private static class StateProperty
    extends Property<View, State> {
        public static final char TARGET_X = 'x';
        public static final char TARGET_Y = 'y';
        private final int mTargetDimension;
        private final Rect mTempRect;
        private final State mTempState;

        public StateProperty(char c) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state_");
            stringBuilder.append(c);
            super(State.class, stringBuilder.toString());
            this.mTempRect = new Rect();
            this.mTempState = new State();
            this.mTargetDimension = c;
        }

        @Override
        public State get(View view) {
            Rect rect = this.mTempRect;
            if (!view.getClipBounds(rect)) {
                rect.setEmpty();
            }
            State state = this.mTempState;
            if (this.mTargetDimension == 120) {
                state.trans = view.getTranslationX();
                state.lower = rect.left + (int)state.trans;
                state.upper = rect.right + (int)state.trans;
            } else {
                state.trans = view.getTranslationY();
                state.lower = rect.top + (int)state.trans;
                state.upper = rect.bottom + (int)state.trans;
            }
            return state;
        }

        @Override
        public void set(View view, State state) {
            Rect rect = this.mTempRect;
            if (view.getClipBounds(rect)) {
                if (this.mTargetDimension == 120) {
                    rect.left = state.lower - (int)state.trans;
                    rect.right = state.upper - (int)state.trans;
                } else {
                    rect.top = state.lower - (int)state.trans;
                    rect.bottom = state.upper - (int)state.trans;
                }
                view.setClipBounds(rect);
            }
            if (this.mTargetDimension == 120) {
                view.setTranslationX(state.trans);
            } else {
                view.setTranslationY(state.trans);
            }
        }
    }

}

