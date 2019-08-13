/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.annotation.UnsupportedAppUsage;
import android.os.Looper;
import android.os.Trace;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ValueAnimator
extends Animator
implements AnimationHandler.AnimationFrameCallback {
    private static final boolean DEBUG = false;
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = "ValueAnimator";
    private static final TimeInterpolator sDefaultInterpolator;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static float sDurationScale;
    private boolean mAnimationEndRequested = false;
    private float mCurrentFraction = 0.0f;
    @UnsupportedAppUsage
    private long mDuration = 300L;
    private float mDurationScale = -1.0f;
    private long mFirstFrameTime = -1L;
    boolean mInitialized = false;
    private TimeInterpolator mInterpolator = sDefaultInterpolator;
    private long mLastFrameTime = -1L;
    private float mOverallFraction = 0.0f;
    private long mPauseTime;
    private int mRepeatCount = 0;
    private int mRepeatMode = 1;
    private boolean mResumed = false;
    private boolean mReversing;
    private boolean mRunning = false;
    float mSeekFraction = -1.0f;
    private boolean mSelfPulse = true;
    private long mStartDelay = 0L;
    private boolean mStartListenersCalled = false;
    long mStartTime = -1L;
    boolean mStartTimeCommitted;
    private boolean mStarted = false;
    private boolean mSuppressSelfPulseRequested = false;
    ArrayList<AnimatorUpdateListener> mUpdateListeners = null;
    PropertyValuesHolder[] mValues;
    HashMap<String, PropertyValuesHolder> mValuesMap;

    static {
        sDurationScale = 1.0f;
        sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    }

    private void addAnimationCallback(long l) {
        if (!this.mSelfPulse) {
            return;
        }
        this.getAnimationHandler().addAnimationFrameCallback(this, l);
    }

    private void addOneShotCommitCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        this.getAnimationHandler().addOneShotCommitCallback(this);
    }

    public static boolean areAnimatorsEnabled() {
        boolean bl = sDurationScale != 0.0f;
        return bl;
    }

    private float clampFraction(float f) {
        float f2;
        if (f < 0.0f) {
            f2 = 0.0f;
        } else {
            int n = this.mRepeatCount;
            f2 = f;
            if (n != -1) {
                f2 = Math.min(f, (float)(n + 1));
            }
        }
        return f2;
    }

    private void endAnimation() {
        if (this.mAnimationEndRequested) {
            return;
        }
        this.removeAnimationCallback();
        int n = 1;
        this.mAnimationEndRequested = true;
        this.mPaused = false;
        if (!this.mStarted && !this.mRunning || this.mListeners == null) {
            n = 0;
        }
        if (n != 0 && !this.mRunning) {
            this.notifyStartListeners();
        }
        this.mRunning = false;
        this.mStarted = false;
        this.mStartListenersCalled = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        if (n != 0 && this.mListeners != null) {
            ArrayList arrayList = (ArrayList)this.mListeners.clone();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                ((Animator.AnimatorListener)arrayList.get(n)).onAnimationEnd(this, this.mReversing);
            }
        }
        this.mReversing = false;
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceEnd(8L, this.getNameForTrace(), System.identityHashCode(this));
        }
    }

    public static int getCurrentAnimationsCount() {
        return AnimationHandler.getAnimationCount();
    }

    private int getCurrentIteration(float f) {
        double d;
        f = this.clampFraction(f);
        double d2 = d = Math.floor(f);
        if ((double)f == d) {
            d2 = d;
            if (f > 0.0f) {
                d2 = d - 1.0;
            }
        }
        return (int)d2;
    }

    private float getCurrentIterationFraction(float f, boolean bl) {
        block0 : {
            f = this.clampFraction(f);
            int n = this.getCurrentIteration(f);
            f -= (float)n;
            if (!this.shouldPlayBackward(n, bl)) break block0;
            f = 1.0f - f;
        }
        return f;
    }

    public static float getDurationScale() {
        return sDurationScale;
    }

    public static long getFrameDelay() {
        AnimationHandler.getInstance();
        return AnimationHandler.getFrameDelay();
    }

    private long getScaledDuration() {
        return (long)((float)this.mDuration * this.resolveDurationScale());
    }

    private boolean isPulsingInternal() {
        boolean bl = this.mLastFrameTime >= 0L;
        return bl;
    }

    private void notifyStartListeners() {
        if (this.mListeners != null && !this.mStartListenersCalled) {
            ArrayList arrayList = (ArrayList)this.mListeners.clone();
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                ((Animator.AnimatorListener)arrayList.get(i)).onAnimationStart(this, this.mReversing);
            }
        }
        this.mStartListenersCalled = true;
    }

    public static ValueAnimator ofArgb(int ... arrn) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(arrn);
        valueAnimator.setEvaluator(ArgbEvaluator.getInstance());
        return valueAnimator;
    }

    public static ValueAnimator ofFloat(float ... arrf) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(arrf);
        return valueAnimator;
    }

    public static ValueAnimator ofInt(int ... arrn) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(arrn);
        return valueAnimator;
    }

    public static ValueAnimator ofObject(TypeEvaluator typeEvaluator, Object ... arrobject) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(arrobject);
        valueAnimator.setEvaluator(typeEvaluator);
        return valueAnimator;
    }

    public static ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder ... arrpropertyValuesHolder) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(arrpropertyValuesHolder);
        return valueAnimator;
    }

    private void removeAnimationCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        this.getAnimationHandler().removeCallback(this);
    }

    private float resolveDurationScale() {
        float f = this.mDurationScale;
        if (!(f >= 0.0f)) {
            f = sDurationScale;
        }
        return f;
    }

    public static void setDurationScale(float f) {
        sDurationScale = f;
    }

    public static void setFrameDelay(long l) {
        AnimationHandler.getInstance();
        AnimationHandler.setFrameDelay(l);
    }

    private boolean shouldPlayBackward(int n, boolean bl) {
        int n2;
        if (n > 0 && this.mRepeatMode == 2 && (n < (n2 = this.mRepeatCount) + 1 || n2 == -1)) {
            boolean bl2 = false;
            boolean bl3 = false;
            if (bl) {
                bl = bl3;
                if (n % 2 == 0) {
                    bl = true;
                }
                return bl;
            }
            bl = bl2;
            if (n % 2 != 0) {
                bl = true;
            }
            return bl;
        }
        return bl;
    }

    private void start(boolean bl) {
        if (Looper.myLooper() != null) {
            float f;
            this.mReversing = bl;
            this.mSelfPulse = this.mSuppressSelfPulseRequested ^ true;
            if (bl && (f = this.mSeekFraction) != -1.0f && f != 0.0f) {
                int n = this.mRepeatCount;
                this.mSeekFraction = n == -1 ? 1.0f - (float)((double)f - Math.floor(f)) : (float)(n + 1) - f;
            }
            this.mStarted = true;
            this.mPaused = false;
            this.mRunning = false;
            this.mAnimationEndRequested = false;
            this.mLastFrameTime = -1L;
            this.mFirstFrameTime = -1L;
            this.mStartTime = -1L;
            this.addAnimationCallback(0L);
            if (this.mStartDelay == 0L || this.mSeekFraction >= 0.0f || this.mReversing) {
                this.startAnimation();
                f = this.mSeekFraction;
                if (f == -1.0f) {
                    this.setCurrentPlayTime(0L);
                } else {
                    this.setCurrentFraction(f);
                }
            }
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    private void startAnimation() {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceBegin(8L, this.getNameForTrace(), System.identityHashCode(this));
        }
        this.mAnimationEndRequested = false;
        this.initAnimation();
        this.mRunning = true;
        float f = this.mSeekFraction;
        this.mOverallFraction = f >= 0.0f ? f : 0.0f;
        if (this.mListeners != null) {
            this.notifyStartListeners();
        }
    }

    public void addUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList();
        }
        this.mUpdateListeners.add(animatorUpdateListener);
    }

    @Override
    void animateBasedOnPlayTime(long l, long l2, boolean bl) {
        if (l >= 0L && l2 >= 0L) {
            int n;
            this.initAnimation();
            int n2 = this.mRepeatCount;
            if (n2 > 0) {
                long l3 = this.mDuration;
                int n3 = (int)(l / l3);
                n = (int)(l2 / l3);
                if (Math.min(n3, n2) != Math.min(n, this.mRepeatCount) && this.mListeners != null) {
                    n3 = this.mListeners.size();
                    for (n = 0; n < n3; ++n) {
                        ((Animator.AnimatorListener)this.mListeners.get(n)).onAnimationRepeat(this);
                    }
                }
            }
            if ((n = this.mRepeatCount) != -1 && l >= (long)(n + 1) * this.mDuration) {
                this.skipToEndValue(bl);
            } else {
                this.animateValue(this.getCurrentIterationFraction((float)l / (float)this.mDuration, bl));
            }
            return;
        }
        throw new UnsupportedOperationException("Error: Play time should never be negative.");
    }

    boolean animateBasedOnTime(long l) {
        boolean bl = false;
        boolean bl2 = false;
        if (this.mRunning) {
            long l2 = this.getScaledDuration();
            float f = l2 > 0L ? (float)(l - this.mStartTime) / (float)l2 : 1.0f;
            float f2 = this.mOverallFraction;
            int n = (int)f;
            int n2 = (int)f2;
            int n3 = 0;
            n2 = n > n2 ? 1 : 0;
            int n4 = this.mRepeatCount;
            n = n3;
            if (f >= (float)(n4 + 1)) {
                n = n3;
                if (n4 != -1) {
                    n = 1;
                }
            }
            if (l2 == 0L) {
                bl = true;
            } else if (n2 != 0 && n == 0) {
                bl = bl2;
                if (this.mListeners != null) {
                    n = this.mListeners.size();
                    for (n2 = 0; n2 < n; ++n2) {
                        ((Animator.AnimatorListener)this.mListeners.get(n2)).onAnimationRepeat(this);
                    }
                    bl = bl2;
                }
            } else {
                bl = bl2;
                if (n != 0) {
                    bl = true;
                }
            }
            this.mOverallFraction = this.clampFraction(f);
            this.animateValue(this.getCurrentIterationFraction(this.mOverallFraction, this.mReversing));
        }
        return bl;
    }

    @UnsupportedAppUsage
    void animateValue(float f) {
        int n;
        this.mCurrentFraction = f = this.mInterpolator.getInterpolation(f);
        int n2 = this.mValues.length;
        for (n = 0; n < n2; ++n) {
            this.mValues[n].calculateValue(f);
        }
        ArrayList<AnimatorUpdateListener> arrayList = this.mUpdateListeners;
        if (arrayList != null) {
            n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                this.mUpdateListeners.get(n).onAnimationUpdate(this);
            }
        }
    }

    @Override
    public boolean canReverse() {
        return true;
    }

    @Override
    public void cancel() {
        if (Looper.myLooper() != null) {
            if (this.mAnimationEndRequested) {
                return;
            }
            if ((this.mStarted || this.mRunning) && this.mListeners != null) {
                if (!this.mRunning) {
                    this.notifyStartListeners();
                }
                Iterator iterator = ((ArrayList)this.mListeners.clone()).iterator();
                while (iterator.hasNext()) {
                    ((Animator.AnimatorListener)iterator.next()).onAnimationCancel(this);
                }
            }
            this.endAnimation();
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    @Override
    public ValueAnimator clone() {
        ValueAnimator valueAnimator = (ValueAnimator)super.clone();
        PropertyValuesHolder[] arrpropertyValuesHolder = this.mUpdateListeners;
        if (arrpropertyValuesHolder != null) {
            valueAnimator.mUpdateListeners = new ArrayList<AnimatorUpdateListener>((Collection<AnimatorUpdateListener>)arrpropertyValuesHolder);
        }
        valueAnimator.mSeekFraction = -1.0f;
        valueAnimator.mReversing = false;
        valueAnimator.mInitialized = false;
        valueAnimator.mStarted = false;
        valueAnimator.mRunning = false;
        valueAnimator.mPaused = false;
        valueAnimator.mResumed = false;
        valueAnimator.mStartListenersCalled = false;
        valueAnimator.mStartTime = -1L;
        valueAnimator.mStartTimeCommitted = false;
        valueAnimator.mAnimationEndRequested = false;
        valueAnimator.mPauseTime = -1L;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mFirstFrameTime = -1L;
        valueAnimator.mOverallFraction = 0.0f;
        valueAnimator.mCurrentFraction = 0.0f;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        arrpropertyValuesHolder = this.mValues;
        if (arrpropertyValuesHolder != null) {
            int n = arrpropertyValuesHolder.length;
            valueAnimator.mValues = new PropertyValuesHolder[n];
            valueAnimator.mValuesMap = new HashMap(n);
            for (int i = 0; i < n; ++i) {
                PropertyValuesHolder propertyValuesHolder;
                valueAnimator.mValues[i] = propertyValuesHolder = arrpropertyValuesHolder[i].clone();
                valueAnimator.mValuesMap.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
            }
        }
        return valueAnimator;
    }

    @Override
    public void commitAnimationFrame(long l) {
        if (!this.mStartTimeCommitted) {
            this.mStartTimeCommitted = true;
            if ((l -= this.mLastFrameTime) > 0L) {
                this.mStartTime += l;
            }
        }
    }

    @Override
    public final boolean doAnimationFrame(long l) {
        long l2;
        if (this.mStartTime < 0L) {
            l2 = this.mReversing ? l : (long)((float)this.mStartDelay * this.resolveDurationScale()) + l;
            this.mStartTime = l2;
        }
        if (this.mPaused) {
            this.mPauseTime = l;
            this.removeAnimationCallback();
            return false;
        }
        if (this.mResumed) {
            this.mResumed = false;
            l2 = this.mPauseTime;
            if (l2 > 0L) {
                this.mStartTime += l - l2;
            }
        }
        if (!this.mRunning) {
            if (this.mStartTime > l && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            this.startAnimation();
        }
        if (this.mLastFrameTime < 0L) {
            if (this.mSeekFraction >= 0.0f) {
                this.mStartTime = l - (long)((float)this.getScaledDuration() * this.mSeekFraction);
                this.mSeekFraction = -1.0f;
            }
            this.mStartTimeCommitted = false;
        }
        this.mLastFrameTime = l;
        boolean bl = this.animateBasedOnTime(Math.max(l, this.mStartTime));
        if (bl) {
            this.endAnimation();
        }
        return bl;
    }

    @Override
    public void end() {
        if (Looper.myLooper() != null) {
            if (!this.mRunning) {
                this.startAnimation();
                this.mStarted = true;
            } else if (!this.mInitialized) {
                this.initAnimation();
            }
            float f = this.shouldPlayBackward(this.mRepeatCount, this.mReversing) ? 0.0f : 1.0f;
            this.animateValue(f);
            this.endAnimation();
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    public float getAnimatedFraction() {
        return this.mCurrentFraction;
    }

    public Object getAnimatedValue() {
        PropertyValuesHolder[] arrpropertyValuesHolder = this.mValues;
        if (arrpropertyValuesHolder != null && arrpropertyValuesHolder.length > 0) {
            return arrpropertyValuesHolder[0].getAnimatedValue();
        }
        return null;
    }

    public Object getAnimatedValue(String object) {
        if ((object = this.mValuesMap.get(object)) != null) {
            return ((PropertyValuesHolder)object).getAnimatedValue();
        }
        return null;
    }

    public AnimationHandler getAnimationHandler() {
        return AnimationHandler.getInstance();
    }

    public long getCurrentPlayTime() {
        if (this.mInitialized && (this.mStarted || !(this.mSeekFraction < 0.0f))) {
            float f;
            float f2 = this.mSeekFraction;
            if (f2 >= 0.0f) {
                return (long)((float)this.mDuration * f2);
            }
            f2 = f = this.resolveDurationScale();
            if (f == 0.0f) {
                f2 = 1.0f;
            }
            return (long)((float)(AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / f2);
        }
        return 0L;
    }

    @Override
    public long getDuration() {
        return this.mDuration;
    }

    @Override
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    String getNameForTrace() {
        return "animator";
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    @Override
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override
    public long getTotalDuration() {
        int n = this.mRepeatCount;
        if (n == -1) {
            return -1L;
        }
        return this.mStartDelay + this.mDuration * (long)(n + 1);
    }

    public PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    void initAnimation() {
        if (!this.mInitialized) {
            int n = this.mValues.length;
            for (int i = 0; i < n; ++i) {
                this.mValues[i].init();
            }
            this.mInitialized = true;
        }
    }

    @Override
    boolean isInitialized() {
        return this.mInitialized;
    }

    @Override
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override
    public boolean isStarted() {
        return this.mStarted;
    }

    public void overrideDurationScale(float f) {
        this.mDurationScale = f;
    }

    @Override
    public void pause() {
        boolean bl = this.mPaused;
        super.pause();
        if (!bl && this.mPaused) {
            this.mPauseTime = -1L;
            this.mResumed = false;
        }
    }

    @Override
    boolean pulseAnimationFrame(long l) {
        if (this.mSelfPulse) {
            return false;
        }
        return this.doAnimationFrame(l);
    }

    public void removeAllUpdateListeners() {
        ArrayList<AnimatorUpdateListener> arrayList = this.mUpdateListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.clear();
        this.mUpdateListeners = null;
    }

    public void removeUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        ArrayList<AnimatorUpdateListener> arrayList = this.mUpdateListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorUpdateListener);
        if (this.mUpdateListeners.size() == 0) {
            this.mUpdateListeners = null;
        }
    }

    @Override
    public void resume() {
        if (Looper.myLooper() != null) {
            if (this.mPaused && !this.mResumed) {
                this.mResumed = true;
                if (this.mPauseTime > 0L) {
                    this.addAnimationCallback(0L);
                }
            }
            super.resume();
            return;
        }
        throw new AndroidRuntimeException("Animators may only be resumed from the same thread that the animator was started on");
    }

    @Override
    public void reverse() {
        if (this.isPulsingInternal()) {
            long l = AnimationUtils.currentAnimationTimeMillis();
            long l2 = this.mStartTime;
            this.mStartTime = l - (this.getScaledDuration() - (l - l2));
            this.mStartTimeCommitted = true;
            this.mReversing ^= true;
        } else if (this.mStarted) {
            this.mReversing ^= true;
            this.end();
        } else {
            this.start(true);
        }
    }

    @Override
    public void setAllowRunningAsynchronously(boolean bl) {
    }

    public void setCurrentFraction(float f) {
        this.initAnimation();
        f = this.clampFraction(f);
        this.mStartTimeCommitted = true;
        if (this.isPulsingInternal()) {
            long l = (long)((float)this.getScaledDuration() * f);
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - l;
        } else {
            this.mSeekFraction = f;
        }
        this.mOverallFraction = f;
        this.animateValue(this.getCurrentIterationFraction(f, this.mReversing));
    }

    public void setCurrentPlayTime(long l) {
        long l2 = this.mDuration;
        float f = l2 > 0L ? (float)l / (float)l2 : 1.0f;
        this.setCurrentFraction(f);
    }

    @Override
    public ValueAnimator setDuration(long l) {
        if (l >= 0L) {
            this.mDuration = l;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Animators cannot have negative duration: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        PropertyValuesHolder[] arrpropertyValuesHolder;
        if (typeEvaluator != null && (arrpropertyValuesHolder = this.mValues) != null && arrpropertyValuesHolder.length > 0) {
            arrpropertyValuesHolder[0].setEvaluator(typeEvaluator);
        }
    }

    public void setFloatValues(float ... arrf) {
        if (arrf != null && arrf.length != 0) {
            PropertyValuesHolder[] arrpropertyValuesHolder = this.mValues;
            if (arrpropertyValuesHolder != null && arrpropertyValuesHolder.length != 0) {
                arrpropertyValuesHolder[0].setFloatValues(arrf);
            } else {
                this.setValues(PropertyValuesHolder.ofFloat("", arrf));
            }
            this.mInitialized = false;
            return;
        }
    }

    public void setIntValues(int ... arrn) {
        if (arrn != null && arrn.length != 0) {
            PropertyValuesHolder[] arrpropertyValuesHolder = this.mValues;
            if (arrpropertyValuesHolder != null && arrpropertyValuesHolder.length != 0) {
                arrpropertyValuesHolder[0].setIntValues(arrn);
            } else {
                this.setValues(PropertyValuesHolder.ofInt("", arrn));
            }
            this.mInitialized = false;
            return;
        }
    }

    @Override
    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator != null ? timeInterpolator : new LinearInterpolator();
    }

    public void setObjectValues(Object ... arrobject) {
        if (arrobject != null && arrobject.length != 0) {
            PropertyValuesHolder[] arrpropertyValuesHolder = this.mValues;
            if (arrpropertyValuesHolder != null && arrpropertyValuesHolder.length != 0) {
                arrpropertyValuesHolder[0].setObjectValues(arrobject);
            } else {
                this.setValues(PropertyValuesHolder.ofObject("", null, arrobject));
            }
            this.mInitialized = false;
            return;
        }
    }

    public void setRepeatCount(int n) {
        this.mRepeatCount = n;
    }

    public void setRepeatMode(int n) {
        this.mRepeatMode = n;
    }

    @Override
    public void setStartDelay(long l) {
        long l2 = l;
        if (l < 0L) {
            Log.w(TAG, "Start delay should always be non-negative");
            l2 = 0L;
        }
        this.mStartDelay = l2;
    }

    public void setValues(PropertyValuesHolder ... arrpropertyValuesHolder) {
        int n = arrpropertyValuesHolder.length;
        this.mValues = arrpropertyValuesHolder;
        this.mValuesMap = new HashMap(n);
        for (int i = 0; i < n; ++i) {
            PropertyValuesHolder propertyValuesHolder = arrpropertyValuesHolder[i];
            this.mValuesMap.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
        }
        this.mInitialized = false;
    }

    @Override
    void skipToEndValue(boolean bl) {
        this.initAnimation();
        float f = bl ? 0.0f : 1.0f;
        float f2 = f;
        if (this.mRepeatCount % 2 == 1) {
            f2 = f;
            if (this.mRepeatMode == 2) {
                f2 = 0.0f;
            }
        }
        this.animateValue(f2);
    }

    @Override
    public void start() {
        this.start(false);
    }

    @Override
    void startWithoutPulsing(boolean bl) {
        this.mSuppressSelfPulseRequested = true;
        if (bl) {
            this.reverse();
        } else {
            this.start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("ValueAnimator@");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.hashCode()));
        CharSequence charSequence2 = charSequence = ((StringBuilder)charSequence).toString();
        if (this.mValues != null) {
            int n = 0;
            do {
                charSequence2 = charSequence;
                if (n >= this.mValues.length) break;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("\n    ");
                ((StringBuilder)charSequence2).append(this.mValues[n].toString());
                charSequence = ((StringBuilder)charSequence2).toString();
                ++n;
            } while (true);
        }
        return charSequence2;
    }

    public static interface AnimatorUpdateListener {
        public void onAnimationUpdate(ValueAnimator var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RepeatMode {
    }

}

