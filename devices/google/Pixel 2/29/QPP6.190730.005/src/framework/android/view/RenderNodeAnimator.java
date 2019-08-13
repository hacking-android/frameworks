/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.graphics.CanvasProperty;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseIntArray;
import android.view.Choreographer;
import android.view.DisplayListCanvas;
import android.view.View;
import android.view._$$Lambda$1kvF4JuyM42_wmyDVPAIYdPz1jE;
import com.android.internal.util.VirtualRefBasePtr;
import com.android.internal.view.animation.FallbackLUTInterpolator;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Objects;

public class RenderNodeAnimator
extends Animator {
    public static final int ALPHA = 11;
    public static final int LAST_VALUE = 11;
    public static final int PAINT_ALPHA = 1;
    public static final int PAINT_STROKE_WIDTH = 0;
    public static final int ROTATION = 5;
    public static final int ROTATION_X = 6;
    public static final int ROTATION_Y = 7;
    public static final int SCALE_X = 3;
    public static final int SCALE_Y = 4;
    private static final int STATE_DELAYED = 1;
    private static final int STATE_FINISHED = 3;
    private static final int STATE_PREPARE = 0;
    private static final int STATE_RUNNING = 2;
    public static final int TRANSLATION_X = 0;
    public static final int TRANSLATION_Y = 1;
    public static final int TRANSLATION_Z = 2;
    public static final int X = 8;
    public static final int Y = 9;
    public static final int Z = 10;
    private static ThreadLocal<DelayedAnimationHelper> sAnimationHelper;
    private static final SparseIntArray sViewPropertyAnimatorMap;
    private float mFinalValue;
    private Handler mHandler;
    private TimeInterpolator mInterpolator;
    private VirtualRefBasePtr mNativePtr;
    private int mRenderProperty = -1;
    private long mStartDelay = 0L;
    private long mStartTime;
    private int mState = 0;
    private RenderNode mTarget;
    private final boolean mUiThreadHandlesDelay;
    private long mUnscaledDuration = 300L;
    private long mUnscaledStartDelay = 0L;
    private View mViewTarget;

    static {
        sViewPropertyAnimatorMap = new SparseIntArray(15){
            {
                this.put(1, 0);
                this.put(2, 1);
                this.put(4, 2);
                this.put(8, 3);
                this.put(16, 4);
                this.put(32, 5);
                this.put(64, 6);
                this.put(128, 7);
                this.put(256, 8);
                this.put(512, 9);
                this.put(1024, 10);
                this.put(2048, 11);
            }
        };
        sAnimationHelper = new ThreadLocal();
    }

    @UnsupportedAppUsage
    public RenderNodeAnimator(int n, float f) {
        this.mRenderProperty = n;
        this.mFinalValue = f;
        this.mUiThreadHandlesDelay = true;
        this.init(RenderNodeAnimator.nCreateAnimator(n, f));
    }

    public RenderNodeAnimator(int n, int n2, float f, float f2) {
        this.init(RenderNodeAnimator.nCreateRevealAnimator(n, n2, f, f2));
        this.mUiThreadHandlesDelay = true;
    }

    @UnsupportedAppUsage
    public RenderNodeAnimator(CanvasProperty<Float> canvasProperty, float f) {
        this.init(RenderNodeAnimator.nCreateCanvasPropertyFloatAnimator(canvasProperty.getNativeContainer(), f));
        this.mUiThreadHandlesDelay = false;
    }

    @UnsupportedAppUsage
    public RenderNodeAnimator(CanvasProperty<Paint> canvasProperty, int n, float f) {
        this.init(RenderNodeAnimator.nCreateCanvasPropertyPaintAnimator(canvasProperty.getNativeContainer(), n, f));
        this.mUiThreadHandlesDelay = false;
    }

    private void applyInterpolator() {
        TimeInterpolator timeInterpolator = this.mInterpolator;
        if (timeInterpolator != null && this.mNativePtr != null) {
            long l;
            if (RenderNodeAnimator.isNativeInterpolator(timeInterpolator)) {
                l = ((NativeInterpolatorFactory)((Object)this.mInterpolator)).createNativeInterpolator();
            } else {
                l = RenderNodeAnimator.nGetDuration(this.mNativePtr.get());
                l = FallbackLUTInterpolator.createNativeInterpolator(this.mInterpolator, l);
            }
            RenderNodeAnimator.nSetInterpolator(this.mNativePtr.get(), l);
            return;
        }
    }

    @UnsupportedAppUsage
    private static void callOnFinished(RenderNodeAnimator renderNodeAnimator) {
        Handler handler = renderNodeAnimator.mHandler;
        if (handler != null) {
            Objects.requireNonNull(renderNodeAnimator);
            handler.post(new _$$Lambda$1kvF4JuyM42_wmyDVPAIYdPz1jE(renderNodeAnimator));
        } else {
            handler = new Handler(Looper.getMainLooper(), null, true);
            Objects.requireNonNull(renderNodeAnimator);
            handler.post(new _$$Lambda$1kvF4JuyM42_wmyDVPAIYdPz1jE(renderNodeAnimator));
        }
    }

    private void checkMutable() {
        if (this.mState == 0) {
            if (this.mNativePtr != null) {
                return;
            }
            throw new IllegalStateException("Animator's target has been destroyed (trying to modify an animation after activity destroy?)");
        }
        throw new IllegalStateException("Animator has already started, cannot change it now!");
    }

    private ArrayList<Animator.AnimatorListener> cloneListeners() {
        ArrayList arrayList;
        ArrayList arrayList2 = arrayList = this.getListeners();
        if (arrayList != null) {
            arrayList2 = (ArrayList)arrayList.clone();
        }
        return arrayList2;
    }

    private void doStart() {
        if (this.mRenderProperty == 11) {
            this.mViewTarget.ensureTransformationInfo();
            this.mViewTarget.setAlphaInternal(this.mFinalValue);
        }
        this.moveToRunningState();
        View view = this.mViewTarget;
        if (view != null) {
            view.invalidateViewProperty(true, false);
        }
    }

    private static DelayedAnimationHelper getHelper() {
        DelayedAnimationHelper delayedAnimationHelper;
        DelayedAnimationHelper delayedAnimationHelper2 = delayedAnimationHelper = sAnimationHelper.get();
        if (delayedAnimationHelper == null) {
            delayedAnimationHelper2 = new DelayedAnimationHelper();
            sAnimationHelper.set(delayedAnimationHelper2);
        }
        return delayedAnimationHelper2;
    }

    private void init(long l) {
        this.mNativePtr = new VirtualRefBasePtr(l);
    }

    static boolean isNativeInterpolator(TimeInterpolator timeInterpolator) {
        return timeInterpolator.getClass().isAnnotationPresent(HasNativeInterpolator.class);
    }

    @UnsupportedAppUsage
    public static int mapViewPropertyToRenderProperty(int n) {
        return sViewPropertyAnimatorMap.get(n);
    }

    private void moveToRunningState() {
        this.mState = 2;
        VirtualRefBasePtr virtualRefBasePtr = this.mNativePtr;
        if (virtualRefBasePtr != null) {
            RenderNodeAnimator.nStart(virtualRefBasePtr.get());
        }
        this.notifyStartListeners();
    }

    private static native long nCreateAnimator(int var0, float var1);

    private static native long nCreateCanvasPropertyFloatAnimator(long var0, float var2);

    private static native long nCreateCanvasPropertyPaintAnimator(long var0, int var2, float var3);

    private static native long nCreateRevealAnimator(int var0, int var1, float var2, float var3);

    private static native void nEnd(long var0);

    private static native long nGetDuration(long var0);

    private static native void nSetAllowRunningAsync(long var0, boolean var2);

    private static native void nSetDuration(long var0, long var2);

    private static native void nSetInterpolator(long var0, long var2);

    private static native void nSetListener(long var0, RenderNodeAnimator var2);

    private static native void nSetStartDelay(long var0, long var2);

    private static native void nSetStartValue(long var0, float var2);

    private static native void nStart(long var0);

    private void notifyStartListeners() {
        ArrayList<Animator.AnimatorListener> arrayList = this.cloneListeners();
        int n = arrayList == null ? 0 : arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).onAnimationStart(this);
        }
    }

    private boolean processDelayed(long l) {
        long l2 = this.mStartTime;
        if (l2 == 0L) {
            this.mStartTime = l;
        } else if (l - l2 >= this.mStartDelay) {
            this.doStart();
            return true;
        }
        return false;
    }

    private void releaseNativePtr() {
        VirtualRefBasePtr virtualRefBasePtr = this.mNativePtr;
        if (virtualRefBasePtr != null) {
            virtualRefBasePtr.release();
            this.mNativePtr = null;
        }
    }

    private void setTarget(RenderNode renderNode) {
        this.checkMutable();
        if (this.mTarget == null) {
            RenderNodeAnimator.nSetListener(this.mNativePtr.get(), this);
            this.mTarget = renderNode;
            this.mTarget.addAnimator(this);
            return;
        }
        throw new IllegalStateException("Target already set!");
    }

    @Override
    public void cancel() {
        int n = this.mState;
        if (n != 0 && n != 3) {
            ArrayList<Animator.AnimatorListener> arrayList;
            if (n == 1) {
                RenderNodeAnimator.getHelper().removeDelayedAnimation(this);
                this.moveToRunningState();
            }
            n = (arrayList = this.cloneListeners()) == null ? 0 : arrayList.size();
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).onAnimationCancel(this);
            }
            this.end();
        }
    }

    @Override
    public Animator clone() {
        throw new IllegalStateException("Cannot clone this animator");
    }

    @Override
    public void end() {
        int n = this.mState;
        if (n != 3) {
            Object object;
            if (n < 2) {
                RenderNodeAnimator.getHelper().removeDelayedAnimation(this);
                this.doStart();
            }
            if ((object = this.mNativePtr) != null) {
                RenderNodeAnimator.nEnd(((VirtualRefBasePtr)object).get());
                object = this.mViewTarget;
                if (object != null) {
                    ((View)object).invalidateViewProperty(true, false);
                }
            } else {
                this.onFinished();
            }
        }
    }

    @Override
    public long getDuration() {
        return this.mUnscaledDuration;
    }

    @Override
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public long getNativeAnimator() {
        return this.mNativePtr.get();
    }

    @Override
    public long getStartDelay() {
        return this.mUnscaledStartDelay;
    }

    @Override
    public long getTotalDuration() {
        return this.mUnscaledDuration + this.mUnscaledStartDelay;
    }

    @Override
    public boolean isRunning() {
        boolean bl;
        int n = this.mState;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 2 ? bl : false;
        }
        return bl2;
    }

    @Override
    public boolean isStarted() {
        boolean bl = this.mState != 0;
        return bl;
    }

    protected void onFinished() {
        int n = this.mState;
        if (n == 0) {
            this.releaseNativePtr();
            return;
        }
        if (n == 1) {
            RenderNodeAnimator.getHelper().removeDelayedAnimation(this);
            this.notifyStartListeners();
        }
        this.mState = 3;
        ArrayList<Animator.AnimatorListener> arrayList = this.cloneListeners();
        n = arrayList == null ? 0 : arrayList.size();
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).onAnimationEnd(this);
        }
        this.releaseNativePtr();
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void resume() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAllowRunningAsynchronously(boolean bl) {
        this.checkMutable();
        RenderNodeAnimator.nSetAllowRunningAsync(this.mNativePtr.get(), bl);
    }

    @UnsupportedAppUsage
    @Override
    public RenderNodeAnimator setDuration(long l) {
        this.checkMutable();
        if (l >= 0L) {
            this.mUnscaledDuration = l;
            RenderNodeAnimator.nSetDuration(this.mNativePtr.get(), (long)((float)l * ValueAnimator.getDurationScale()));
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("duration must be positive; ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.checkMutable();
        this.mInterpolator = timeInterpolator;
    }

    @Override
    public void setStartDelay(long l) {
        this.checkMutable();
        if (l >= 0L) {
            this.mUnscaledStartDelay = l;
            this.mStartDelay = (long)(ValueAnimator.getDurationScale() * (float)l);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("startDelay must be positive; ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void setStartValue(float f) {
        this.checkMutable();
        RenderNodeAnimator.nSetStartValue(this.mNativePtr.get(), f);
    }

    public void setTarget(RecordingCanvas recordingCanvas) {
        this.setTarget(recordingCanvas.mNode);
    }

    @UnsupportedAppUsage
    public void setTarget(DisplayListCanvas displayListCanvas) {
        this.setTarget((RecordingCanvas)displayListCanvas);
    }

    @UnsupportedAppUsage
    public void setTarget(View view) {
        this.mViewTarget = view;
        this.setTarget(this.mViewTarget.mRenderNode);
    }

    @Override
    public void start() {
        if (this.mTarget != null) {
            if (this.mState == 0) {
                this.mState = 1;
                if (this.mHandler == null) {
                    this.mHandler = new Handler(true);
                }
                this.applyInterpolator();
                if (this.mNativePtr == null) {
                    this.cancel();
                } else if (this.mStartDelay > 0L && this.mUiThreadHandlesDelay) {
                    RenderNodeAnimator.getHelper().addDelayedAnimation(this);
                } else {
                    RenderNodeAnimator.nSetStartDelay(this.mNativePtr.get(), this.mStartDelay);
                    this.doStart();
                }
                return;
            }
            throw new IllegalStateException("Already started!");
        }
        throw new IllegalStateException("Missing target!");
    }

    private static class DelayedAnimationHelper
    implements Runnable {
        private boolean mCallbackScheduled;
        private final Choreographer mChoreographer = Choreographer.getInstance();
        private ArrayList<RenderNodeAnimator> mDelayedAnims = new ArrayList();

        private void scheduleCallback() {
            if (!this.mCallbackScheduled) {
                this.mCallbackScheduled = true;
                this.mChoreographer.postCallback(1, this, null);
            }
        }

        public void addDelayedAnimation(RenderNodeAnimator renderNodeAnimator) {
            this.mDelayedAnims.add(renderNodeAnimator);
            this.scheduleCallback();
        }

        public void removeDelayedAnimation(RenderNodeAnimator renderNodeAnimator) {
            this.mDelayedAnims.remove(renderNodeAnimator);
        }

        @Override
        public void run() {
            Cloneable cloneable;
            long l = this.mChoreographer.getFrameTime();
            this.mCallbackScheduled = false;
            int n = 0;
            for (int i = 0; i < this.mDelayedAnims.size(); ++i) {
                cloneable = this.mDelayedAnims.get(i);
                int n2 = n;
                if (!((RenderNodeAnimator)cloneable).processDelayed(l)) {
                    if (n != i) {
                        this.mDelayedAnims.set(n, (RenderNodeAnimator)cloneable);
                    }
                    n2 = n + 1;
                }
                n = n2;
            }
            while (this.mDelayedAnims.size() > n) {
                cloneable = this.mDelayedAnims;
                ((ArrayList)cloneable).remove(((ArrayList)cloneable).size() - 1);
            }
            if (this.mDelayedAnims.size() > 0) {
                this.scheduleCallback();
            }
        }
    }

}

