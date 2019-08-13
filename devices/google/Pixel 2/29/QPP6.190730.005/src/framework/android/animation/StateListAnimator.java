/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.ConstantState;
import android.util.StateSet;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class StateListAnimator
implements Cloneable {
    private AnimatorListenerAdapter mAnimatorListener;
    private int mChangingConfigurations;
    private StateListAnimatorConstantState mConstantState;
    private Tuple mLastMatch = null;
    private Animator mRunningAnimator = null;
    private ArrayList<Tuple> mTuples = new ArrayList();
    private WeakReference<View> mViewRef;

    public StateListAnimator() {
        this.initAnimatorListener();
    }

    private void cancel() {
        Animator animator2 = this.mRunningAnimator;
        if (animator2 != null) {
            animator2.cancel();
            this.mRunningAnimator = null;
        }
    }

    private void clearTarget() {
        int n = this.mTuples.size();
        for (int i = 0; i < n; ++i) {
            this.mTuples.get((int)i).mAnimator.setTarget(null);
        }
        this.mViewRef = null;
        this.mLastMatch = null;
        this.mRunningAnimator = null;
    }

    private void initAnimatorListener() {
        this.mAnimatorListener = new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                animator2.setTarget(null);
                if (StateListAnimator.this.mRunningAnimator == animator2) {
                    StateListAnimator.this.mRunningAnimator = null;
                }
            }
        };
    }

    private void start(Tuple tuple) {
        tuple.mAnimator.setTarget(this.getTarget());
        this.mRunningAnimator = tuple.mAnimator;
        this.mRunningAnimator.start();
    }

    public void addState(int[] object, Animator animator2) {
        object = new Tuple((int[])object, animator2);
        object.mAnimator.addListener(this.mAnimatorListener);
        this.mTuples.add((Tuple)object);
        this.mChangingConfigurations |= animator2.getChangingConfigurations();
    }

    public void appendChangingConfigurations(int n) {
        this.mChangingConfigurations |= n;
    }

    public StateListAnimator clone() {
        StateListAnimator stateListAnimator = (StateListAnimator)super.clone();
        Cloneable cloneable = new ArrayList(this.mTuples.size());
        stateListAnimator.mTuples = cloneable;
        stateListAnimator.mLastMatch = null;
        stateListAnimator.mRunningAnimator = null;
        stateListAnimator.mViewRef = null;
        stateListAnimator.mAnimatorListener = null;
        stateListAnimator.initAnimatorListener();
        int n = this.mTuples.size();
        for (int i = 0; i < n; ++i) {
            Tuple tuple = this.mTuples.get(i);
            cloneable = tuple.mAnimator.clone();
            ((Animator)cloneable).removeListener(this.mAnimatorListener);
            stateListAnimator.addState(tuple.mSpecs, (Animator)cloneable);
            continue;
        }
        try {
            stateListAnimator.setChangingConfigurations(this.getChangingConfigurations());
            return stateListAnimator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError("cannot clone state list animator", cloneNotSupportedException);
        }
    }

    public ConstantState<StateListAnimator> createConstantState() {
        return new StateListAnimatorConstantState(this);
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public Animator getRunningAnimator() {
        return this.mRunningAnimator;
    }

    public View getTarget() {
        WeakReference<View> weakReference = this.mViewRef;
        weakReference = weakReference == null ? null : (View)weakReference.get();
        return weakReference;
    }

    public ArrayList<Tuple> getTuples() {
        return this.mTuples;
    }

    public void jumpToCurrentState() {
        Animator animator2 = this.mRunningAnimator;
        if (animator2 != null) {
            animator2.end();
        }
    }

    public void setChangingConfigurations(int n) {
        this.mChangingConfigurations = n;
    }

    public void setState(int[] object) {
        Tuple tuple;
        Tuple tuple2 = null;
        int n = this.mTuples.size();
        int n2 = 0;
        do {
            tuple = tuple2;
            if (n2 >= n) break;
            tuple = this.mTuples.get(n2);
            if (StateSet.stateSetMatches(tuple.mSpecs, object)) break;
            ++n2;
        } while (true);
        if (tuple == (object = this.mLastMatch)) {
            return;
        }
        if (object != null) {
            this.cancel();
        }
        this.mLastMatch = tuple;
        if (tuple != null) {
            this.start(tuple);
        }
    }

    public void setTarget(View view) {
        View view2 = this.getTarget();
        if (view2 == view) {
            return;
        }
        if (view2 != null) {
            this.clearTarget();
        }
        if (view != null) {
            this.mViewRef = new WeakReference<View>(view);
        }
    }

    private static class StateListAnimatorConstantState
    extends ConstantState<StateListAnimator> {
        final StateListAnimator mAnimator;
        int mChangingConf;

        public StateListAnimatorConstantState(StateListAnimator stateListAnimator) {
            this.mAnimator = stateListAnimator;
            this.mAnimator.mConstantState = this;
            this.mChangingConf = this.mAnimator.getChangingConfigurations();
        }

        @Override
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        @Override
        public StateListAnimator newInstance() {
            StateListAnimator stateListAnimator = this.mAnimator.clone();
            stateListAnimator.mConstantState = this;
            return stateListAnimator;
        }
    }

    public static class Tuple {
        final Animator mAnimator;
        final int[] mSpecs;

        private Tuple(int[] arrn, Animator animator2) {
            this.mSpecs = arrn;
            this.mAnimator = animator2;
        }

        public Animator getAnimator() {
            return this.mAnimator;
        }

        public int[] getSpecs() {
            return this.mSpecs;
        }
    }

}

