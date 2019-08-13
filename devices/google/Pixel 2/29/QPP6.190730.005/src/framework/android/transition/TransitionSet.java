/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionPropagation;
import android.transition.TransitionValues;
import android.transition.TransitionValuesMaps;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionSet
extends Transition {
    static final int FLAG_CHANGE_EPICENTER = 8;
    private static final int FLAG_CHANGE_INTERPOLATOR = 1;
    private static final int FLAG_CHANGE_PATH_MOTION = 4;
    private static final int FLAG_CHANGE_PROPAGATION = 2;
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    private int mChangeFlags = 0;
    int mCurrentListeners;
    private boolean mPlayTogether = true;
    boolean mStarted = false;
    ArrayList<Transition> mTransitions = new ArrayList();

    public TransitionSet() {
    }

    public TransitionSet(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.TransitionSet);
        this.setOrdering(((TypedArray)object).getInt(0, 0));
        ((TypedArray)object).recycle();
    }

    private void addTransitionInternal(Transition transition2) {
        this.mTransitions.add(transition2);
        transition2.mParent = this;
    }

    private void setupStartEndListeners() {
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator<Transition> iterator = this.mTransitions.iterator();
        while (iterator.hasNext()) {
            iterator.next().addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    @Override
    public TransitionSet addListener(Transition.TransitionListener transitionListener) {
        return (TransitionSet)super.addListener(transitionListener);
    }

    @Override
    public TransitionSet addTarget(int n) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).addTarget(n);
        }
        return (TransitionSet)super.addTarget(n);
    }

    @Override
    public TransitionSet addTarget(View view) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).addTarget(view);
        }
        return (TransitionSet)super.addTarget(view);
    }

    @Override
    public TransitionSet addTarget(Class class_) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).addTarget(class_);
        }
        return (TransitionSet)super.addTarget(class_);
    }

    @Override
    public TransitionSet addTarget(String string2) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).addTarget(string2);
        }
        return (TransitionSet)super.addTarget(string2);
    }

    public TransitionSet addTransition(Transition transition2) {
        if (transition2 != null) {
            this.addTransitionInternal(transition2);
            if (this.mDuration >= 0L) {
                transition2.setDuration(this.mDuration);
            }
            if ((this.mChangeFlags & 1) != 0) {
                transition2.setInterpolator(this.getInterpolator());
            }
            if ((this.mChangeFlags & 2) != 0) {
                transition2.setPropagation(this.getPropagation());
            }
            if ((this.mChangeFlags & 4) != 0) {
                transition2.setPathMotion(this.getPathMotion());
            }
            if ((this.mChangeFlags & 8) != 0) {
                transition2.setEpicenterCallback(this.getEpicenterCallback());
            }
        }
        return this;
    }

    @Override
    protected void cancel() {
        super.cancel();
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).cancel();
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        if (this.isValidTarget(transitionValues.view)) {
            for (Transition transition2 : this.mTransitions) {
                if (!transition2.isValidTarget(transitionValues.view)) continue;
                transition2.captureEndValues(transitionValues);
                transitionValues.targetedTransitions.add(transition2);
            }
        }
    }

    @Override
    void capturePropagationValues(TransitionValues transitionValues) {
        super.capturePropagationValues(transitionValues);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).capturePropagationValues(transitionValues);
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        if (this.isValidTarget(transitionValues.view)) {
            for (Transition transition2 : this.mTransitions) {
                if (!transition2.isValidTarget(transitionValues.view)) continue;
                transition2.captureStartValues(transitionValues);
                transitionValues.targetedTransitions.add(transition2);
            }
        }
    }

    @Override
    public TransitionSet clone() {
        TransitionSet transitionSet = (TransitionSet)super.clone();
        transitionSet.mTransitions = new ArrayList();
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            transitionSet.addTransitionInternal(this.mTransitions.get(i).clone());
        }
        return transitionSet;
    }

    @Override
    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        long l = this.getStartDelay();
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            Transition transition2 = this.mTransitions.get(i);
            if (l > 0L && (this.mPlayTogether || i == 0)) {
                long l2 = transition2.getStartDelay();
                if (l2 > 0L) {
                    transition2.setStartDelay(l + l2);
                } else {
                    transition2.setStartDelay(l);
                }
            }
            transition2.createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2, arrayList, arrayList2);
        }
    }

    @Override
    public Transition excludeTarget(int n, boolean bl) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).excludeTarget(n, bl);
        }
        return super.excludeTarget(n, bl);
    }

    @Override
    public Transition excludeTarget(View view, boolean bl) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).excludeTarget(view, bl);
        }
        return super.excludeTarget(view, bl);
    }

    @Override
    public Transition excludeTarget(Class class_, boolean bl) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).excludeTarget(class_, bl);
        }
        return super.excludeTarget(class_, bl);
    }

    @Override
    public Transition excludeTarget(String string2, boolean bl) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).excludeTarget(string2, bl);
        }
        return super.excludeTarget(string2, bl);
    }

    @Override
    void forceToEnd(ViewGroup viewGroup) {
        super.forceToEnd(viewGroup);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).forceToEnd(viewGroup);
        }
    }

    public int getOrdering() {
        return this.mPlayTogether ^ true;
    }

    public Transition getTransitionAt(int n) {
        if (n >= 0 && n < this.mTransitions.size()) {
            return this.mTransitions.get(n);
        }
        return null;
    }

    public int getTransitionCount() {
        return this.mTransitions.size();
    }

    @Override
    public void pause(View view) {
        super.pause(view);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).pause(view);
        }
    }

    @Override
    public TransitionSet removeListener(Transition.TransitionListener transitionListener) {
        return (TransitionSet)super.removeListener(transitionListener);
    }

    @Override
    public TransitionSet removeTarget(int n) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).removeTarget(n);
        }
        return (TransitionSet)super.removeTarget(n);
    }

    @Override
    public TransitionSet removeTarget(View view) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).removeTarget(view);
        }
        return (TransitionSet)super.removeTarget(view);
    }

    @Override
    public TransitionSet removeTarget(Class class_) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).removeTarget(class_);
        }
        return (TransitionSet)super.removeTarget(class_);
    }

    @Override
    public TransitionSet removeTarget(String string2) {
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            this.mTransitions.get(i).removeTarget(string2);
        }
        return (TransitionSet)super.removeTarget(string2);
    }

    public TransitionSet removeTransition(Transition transition2) {
        this.mTransitions.remove(transition2);
        transition2.mParent = null;
        return this;
    }

    @Override
    public void resume(View view) {
        super.resume(view);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).resume(view);
        }
    }

    @Override
    protected void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            this.start();
            this.end();
            return;
        }
        this.setupStartEndListeners();
        int n = this.mTransitions.size();
        if (!this.mPlayTogether) {
            for (int i = 1; i < n; ++i) {
                this.mTransitions.get(i - 1).addListener(new TransitionListenerAdapter(this.mTransitions.get(i)){
                    final /* synthetic */ Transition val$nextTransition;
                    {
                        this.val$nextTransition = transition2;
                    }

                    @Override
                    public void onTransitionEnd(Transition transition2) {
                        this.val$nextTransition.runAnimators();
                        transition2.removeListener(this);
                    }
                });
            }
            Transition transition2 = this.mTransitions.get(0);
            if (transition2 != null) {
                transition2.runAnimators();
            }
        } else {
            for (int i = 0; i < n; ++i) {
                this.mTransitions.get(i).runAnimators();
            }
        }
    }

    @Override
    void setCanRemoveViews(boolean bl) {
        super.setCanRemoveViews(bl);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).setCanRemoveViews(bl);
        }
    }

    @Override
    public TransitionSet setDuration(long l) {
        ArrayList<Transition> arrayList;
        super.setDuration(l);
        if (this.mDuration >= 0L && (arrayList = this.mTransitions) != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.mTransitions.get(i).setDuration(l);
            }
        }
        return this;
    }

    @Override
    public void setEpicenterCallback(Transition.EpicenterCallback epicenterCallback) {
        super.setEpicenterCallback(epicenterCallback);
        this.mChangeFlags |= 8;
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).setEpicenterCallback(epicenterCallback);
        }
    }

    @Override
    public TransitionSet setInterpolator(TimeInterpolator timeInterpolator) {
        this.mChangeFlags |= 1;
        ArrayList<Transition> arrayList = this.mTransitions;
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.mTransitions.get(i).setInterpolator(timeInterpolator);
            }
        }
        return (TransitionSet)super.setInterpolator(timeInterpolator);
    }

    /*
     * Enabled aggressive block sorting
     */
    public TransitionSet setOrdering(int n) {
        if (n == 0) {
            this.mPlayTogether = true;
            return this;
        }
        if (n == 1) {
            this.mPlayTogether = false;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid parameter for TransitionSet ordering: ");
        stringBuilder.append(n);
        throw new AndroidRuntimeException(stringBuilder.toString());
    }

    @Override
    public void setPathMotion(PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.mChangeFlags |= 4;
        if (this.mTransitions != null) {
            for (int i = 0; i < this.mTransitions.size(); ++i) {
                this.mTransitions.get(i).setPathMotion(pathMotion);
            }
        }
    }

    @Override
    public void setPropagation(TransitionPropagation transitionPropagation) {
        super.setPropagation(transitionPropagation);
        this.mChangeFlags |= 2;
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).setPropagation(transitionPropagation);
        }
    }

    @Override
    TransitionSet setSceneRoot(ViewGroup viewGroup) {
        super.setSceneRoot(viewGroup);
        int n = this.mTransitions.size();
        for (int i = 0; i < n; ++i) {
            this.mTransitions.get(i).setSceneRoot(viewGroup);
        }
        return this;
    }

    @Override
    public TransitionSet setStartDelay(long l) {
        return (TransitionSet)super.setStartDelay(l);
    }

    @Override
    String toString(String string2) {
        CharSequence charSequence = super.toString(string2);
        for (int i = 0; i < this.mTransitions.size(); ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("\n");
            Transition transition2 = this.mTransitions.get(i);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("  ");
            stringBuilder.append(transition2.toString(((StringBuilder)charSequence).toString()));
            charSequence = stringBuilder.toString();
        }
        return charSequence;
    }

    static class TransitionSetListener
    extends TransitionListenerAdapter {
        TransitionSet mTransitionSet;

        TransitionSetListener(TransitionSet transitionSet) {
            this.mTransitionSet = transitionSet;
        }

        @Override
        public void onTransitionEnd(Transition transition2) {
            TransitionSet transitionSet = this.mTransitionSet;
            --transitionSet.mCurrentListeners;
            if (this.mTransitionSet.mCurrentListeners == 0) {
                transitionSet = this.mTransitionSet;
                transitionSet.mStarted = false;
                transitionSet.end();
            }
            transition2.removeListener(this);
        }

        @Override
        public void onTransitionStart(Transition transition2) {
            if (!this.mTransitionSet.mStarted) {
                this.mTransitionSet.start();
                this.mTransitionSet.mStarted = true;
            }
        }
    }

}

