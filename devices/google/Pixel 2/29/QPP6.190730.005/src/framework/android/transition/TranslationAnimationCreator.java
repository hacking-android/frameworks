/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.view.View;

class TranslationAnimationCreator {
    TranslationAnimationCreator() {
    }

    static Animator createAnimation(View object, TransitionValues transitionValues, int n, int n2, float f, float f2, float f3, float f4, TimeInterpolator timeInterpolator, Transition transition2) {
        float f5 = ((View)object).getTranslationX();
        float f6 = ((View)object).getTranslationY();
        Object object2 = (int[])transitionValues.view.getTag(16909489);
        if (object2 != null) {
            f = object2[0] - n;
            f2 = object2[1] - n2;
            f += f5;
            f2 += f6;
        }
        int n3 = Math.round(f - f5);
        int n4 = Math.round(f2 - f6);
        ((View)object).setTranslationX(f);
        ((View)object).setTranslationY(f2);
        if (f == f3 && f2 == f4) {
            return null;
        }
        object2 = new Path();
        ((Path)object2).moveTo(f, f2);
        ((Path)object2).lineTo(f3, f4);
        object2 = ObjectAnimator.ofFloat(object, View.TRANSLATION_X, View.TRANSLATION_Y, (Path)object2);
        object = new TransitionPositionListener((View)object, transitionValues.view, n + n3, n2 + n4, f5, f6);
        transition2.addListener((Transition.TransitionListener)object);
        ((Animator)object2).addListener((Animator.AnimatorListener)object);
        ((Animator)object2).addPauseListener((Animator.AnimatorPauseListener)object);
        ((ValueAnimator)object2).setInterpolator(timeInterpolator);
        return object2;
    }

    private static class TransitionPositionListener
    extends AnimatorListenerAdapter
    implements Transition.TransitionListener {
        private final View mMovingView;
        private float mPausedX;
        private float mPausedY;
        private final int mStartX;
        private final int mStartY;
        private final float mTerminalX;
        private final float mTerminalY;
        private int[] mTransitionPosition;
        private final View mViewInHierarchy;

        private TransitionPositionListener(View view, View view2, int n, int n2, float f, float f2) {
            this.mMovingView = view;
            this.mViewInHierarchy = view2;
            this.mStartX = n - Math.round(this.mMovingView.getTranslationX());
            this.mStartY = n2 - Math.round(this.mMovingView.getTranslationY());
            this.mTerminalX = f;
            this.mTerminalY = f2;
            this.mTransitionPosition = (int[])this.mViewInHierarchy.getTag(16909489);
            if (this.mTransitionPosition != null) {
                this.mViewInHierarchy.setTagInternal(16909489, null);
            }
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
            if (this.mTransitionPosition == null) {
                this.mTransitionPosition = new int[2];
            }
            this.mTransitionPosition[0] = Math.round((float)this.mStartX + this.mMovingView.getTranslationX());
            this.mTransitionPosition[1] = Math.round((float)this.mStartY + this.mMovingView.getTranslationY());
            this.mViewInHierarchy.setTagInternal(16909489, this.mTransitionPosition);
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
        }

        @Override
        public void onAnimationPause(Animator animator2) {
            this.mPausedX = this.mMovingView.getTranslationX();
            this.mPausedY = this.mMovingView.getTranslationY();
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
        }

        @Override
        public void onAnimationResume(Animator animator2) {
            this.mMovingView.setTranslationX(this.mPausedX);
            this.mMovingView.setTranslationY(this.mPausedY);
        }

        @Override
        public void onTransitionCancel(Transition transition2) {
        }

        @Override
        public void onTransitionEnd(Transition transition2) {
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
            transition2.removeListener(this);
        }

        @Override
        public void onTransitionPause(Transition transition2) {
        }

        @Override
        public void onTransitionResume(Transition transition2) {
        }

        @Override
        public void onTransitionStart(Transition transition2) {
        }
    }

}

