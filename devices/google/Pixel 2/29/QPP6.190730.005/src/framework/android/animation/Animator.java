/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TimeInterpolator;
import android.annotation.UnsupportedAppUsage;
import android.content.res.ConstantState;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Animator
implements Cloneable {
    public static final long DURATION_INFINITE = -1L;
    int mChangingConfigurations = 0;
    private AnimatorConstantState mConstantState;
    ArrayList<AnimatorListener> mListeners = null;
    ArrayList<AnimatorPauseListener> mPauseListeners = null;
    boolean mPaused = false;

    public void addListener(AnimatorListener animatorListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(animatorListener);
    }

    public void addPauseListener(AnimatorPauseListener animatorPauseListener) {
        if (this.mPauseListeners == null) {
            this.mPauseListeners = new ArrayList();
        }
        this.mPauseListeners.add(animatorPauseListener);
    }

    void animateBasedOnPlayTime(long l, long l2, boolean bl) {
    }

    public void appendChangingConfigurations(int n) {
        this.mChangingConfigurations |= n;
    }

    public boolean canReverse() {
        return false;
    }

    public void cancel() {
    }

    public Animator clone() {
        try {
            ArrayList<AnimatorListener> arrayList;
            Animator animator2 = (Animator)super.clone();
            if (this.mListeners != null) {
                arrayList = new ArrayList<AnimatorListener>(this.mListeners);
                animator2.mListeners = arrayList;
            }
            if (this.mPauseListeners != null) {
                arrayList = new ArrayList<AnimatorListener>(this.mPauseListeners);
                animator2.mPauseListeners = arrayList;
            }
            return animator2;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError();
        }
    }

    public ConstantState<Animator> createConstantState() {
        return new AnimatorConstantState(this);
    }

    public void end() {
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public abstract long getDuration();

    public TimeInterpolator getInterpolator() {
        return null;
    }

    public ArrayList<AnimatorListener> getListeners() {
        return this.mListeners;
    }

    public abstract long getStartDelay();

    public long getTotalDuration() {
        long l = this.getDuration();
        if (l == -1L) {
            return -1L;
        }
        return this.getStartDelay() + l;
    }

    boolean isInitialized() {
        return true;
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    public abstract boolean isRunning();

    public boolean isStarted() {
        return this.isRunning();
    }

    public void pause() {
        if (this.isStarted() && !this.mPaused) {
            this.mPaused = true;
            ArrayList arrayList = this.mPauseListeners;
            if (arrayList != null) {
                arrayList = (ArrayList)arrayList.clone();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((AnimatorPauseListener)arrayList.get(i)).onAnimationPause(this);
                }
            }
        }
    }

    boolean pulseAnimationFrame(long l) {
        return false;
    }

    public void removeAllListeners() {
        ArrayList<Object> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.clear();
            this.mListeners = null;
        }
        if ((arrayList = this.mPauseListeners) != null) {
            arrayList.clear();
            this.mPauseListeners = null;
        }
    }

    public void removeListener(AnimatorListener animatorListener) {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    public void removePauseListener(AnimatorPauseListener animatorPauseListener) {
        ArrayList<AnimatorPauseListener> arrayList = this.mPauseListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorPauseListener);
        if (this.mPauseListeners.size() == 0) {
            this.mPauseListeners = null;
        }
    }

    public void resume() {
        if (this.mPaused) {
            this.mPaused = false;
            ArrayList arrayList = this.mPauseListeners;
            if (arrayList != null) {
                arrayList = (ArrayList)arrayList.clone();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((AnimatorPauseListener)arrayList.get(i)).onAnimationResume(this);
                }
            }
        }
    }

    @UnsupportedAppUsage
    public void reverse() {
        throw new IllegalStateException("Reverse is not supported");
    }

    public void setAllowRunningAsynchronously(boolean bl) {
    }

    public void setChangingConfigurations(int n) {
        this.mChangingConfigurations = n;
    }

    public abstract Animator setDuration(long var1);

    public abstract void setInterpolator(TimeInterpolator var1);

    public abstract void setStartDelay(long var1);

    public void setTarget(Object object) {
    }

    public void setupEndValues() {
    }

    public void setupStartValues() {
    }

    void skipToEndValue(boolean bl) {
    }

    public void start() {
    }

    void startWithoutPulsing(boolean bl) {
        if (bl) {
            this.reverse();
        } else {
            this.start();
        }
    }

    private static class AnimatorConstantState
    extends ConstantState<Animator> {
        final Animator mAnimator;
        int mChangingConf;

        public AnimatorConstantState(Animator animator2) {
            this.mAnimator = animator2;
            this.mAnimator.mConstantState = this;
            this.mChangingConf = this.mAnimator.getChangingConfigurations();
        }

        @Override
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        @Override
        public Animator newInstance() {
            Animator animator2 = this.mAnimator.clone();
            animator2.mConstantState = this;
            return animator2;
        }
    }

    public static interface AnimatorListener {
        public void onAnimationCancel(Animator var1);

        public void onAnimationEnd(Animator var1);

        default public void onAnimationEnd(Animator animator2, boolean bl) {
            this.onAnimationEnd(animator2);
        }

        public void onAnimationRepeat(Animator var1);

        public void onAnimationStart(Animator var1);

        default public void onAnimationStart(Animator animator2, boolean bl) {
            this.onAnimationStart(animator2);
        }
    }

    public static interface AnimatorPauseListener {
        public void onAnimationPause(Animator var1);

        public void onAnimationResume(Animator var1);
    }

}

