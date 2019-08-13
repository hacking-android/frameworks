/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.Choreographer;
import java.util.ArrayList;

public class AnimationHandler {
    public static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal();
    private final ArrayList<AnimationFrameCallback> mAnimationCallbacks = new ArrayList();
    private final ArrayList<AnimationFrameCallback> mCommitCallbacks = new ArrayList();
    private final ArrayMap<AnimationFrameCallback, Long> mDelayedCallbackStartTime = new ArrayMap();
    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback(){

        @Override
        public void doFrame(long l) {
            AnimationHandler animationHandler = AnimationHandler.this;
            animationHandler.doAnimationFrame(animationHandler.getProvider().getFrameTime());
            if (AnimationHandler.this.mAnimationCallbacks.size() > 0) {
                AnimationHandler.this.getProvider().postFrameCallback(this);
            }
        }
    };
    private boolean mListDirty = false;
    private AnimationFrameCallbackProvider mProvider;

    private void cleanUpList() {
        if (this.mListDirty) {
            for (int i = this.mAnimationCallbacks.size() - 1; i >= 0; --i) {
                if (this.mAnimationCallbacks.get(i) != null) continue;
                this.mAnimationCallbacks.remove(i);
            }
            this.mListDirty = false;
        }
    }

    private void commitAnimationFrame(AnimationFrameCallback animationFrameCallback, long l) {
        if (!this.mDelayedCallbackStartTime.containsKey(animationFrameCallback) && this.mCommitCallbacks.contains(animationFrameCallback)) {
            animationFrameCallback.commitAnimationFrame(l);
            this.mCommitCallbacks.remove(animationFrameCallback);
        }
    }

    private void doAnimationFrame(long l) {
        long l2 = SystemClock.uptimeMillis();
        int n = this.mAnimationCallbacks.size();
        for (int i = 0; i < n; ++i) {
            final AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(i);
            if (animationFrameCallback == null || !this.isCallbackDue(animationFrameCallback, l2)) continue;
            animationFrameCallback.doAnimationFrame(l);
            if (!this.mCommitCallbacks.contains(animationFrameCallback)) continue;
            this.getProvider().postCommitCallback(new Runnable(){

                @Override
                public void run() {
                    AnimationHandler animationHandler = AnimationHandler.this;
                    animationHandler.commitAnimationFrame(animationFrameCallback, animationHandler.getProvider().getFrameTime());
                }
            });
        }
        this.cleanUpList();
    }

    public static int getAnimationCount() {
        AnimationHandler animationHandler = sAnimatorHandler.get();
        if (animationHandler == null) {
            return 0;
        }
        return animationHandler.getCallbackSize();
    }

    private int getCallbackSize() {
        int n = 0;
        for (int i = this.mAnimationCallbacks.size() - 1; i >= 0; --i) {
            int n2 = n;
            if (this.mAnimationCallbacks.get(i) != null) {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    public static long getFrameDelay() {
        return AnimationHandler.getInstance().getProvider().getFrameDelay();
    }

    public static AnimationHandler getInstance() {
        if (sAnimatorHandler.get() == null) {
            sAnimatorHandler.set(new AnimationHandler());
        }
        return sAnimatorHandler.get();
    }

    private AnimationFrameCallbackProvider getProvider() {
        if (this.mProvider == null) {
            this.mProvider = new MyFrameCallbackProvider();
        }
        return this.mProvider;
    }

    private boolean isCallbackDue(AnimationFrameCallback animationFrameCallback, long l) {
        Long l2 = this.mDelayedCallbackStartTime.get(animationFrameCallback);
        if (l2 == null) {
            return true;
        }
        if (l2 < l) {
            this.mDelayedCallbackStartTime.remove(animationFrameCallback);
            return true;
        }
        return false;
    }

    public static void setFrameDelay(long l) {
        AnimationHandler.getInstance().getProvider().setFrameDelay(l);
    }

    public void addAnimationFrameCallback(AnimationFrameCallback animationFrameCallback, long l) {
        if (this.mAnimationCallbacks.size() == 0) {
            this.getProvider().postFrameCallback(this.mFrameCallback);
        }
        if (!this.mAnimationCallbacks.contains(animationFrameCallback)) {
            this.mAnimationCallbacks.add(animationFrameCallback);
        }
        if (l > 0L) {
            this.mDelayedCallbackStartTime.put(animationFrameCallback, SystemClock.uptimeMillis() + l);
        }
    }

    public void addOneShotCommitCallback(AnimationFrameCallback animationFrameCallback) {
        if (!this.mCommitCallbacks.contains(animationFrameCallback)) {
            this.mCommitCallbacks.add(animationFrameCallback);
        }
    }

    void autoCancelBasedOn(ObjectAnimator objectAnimator) {
        for (int i = this.mAnimationCallbacks.size() - 1; i >= 0; --i) {
            AnimationFrameCallback animationFrameCallback = this.mAnimationCallbacks.get(i);
            if (animationFrameCallback == null || !objectAnimator.shouldAutoCancel(animationFrameCallback)) continue;
            ((Animator)((Object)this.mAnimationCallbacks.get(i))).cancel();
        }
    }

    public void removeCallback(AnimationFrameCallback animationFrameCallback) {
        this.mCommitCallbacks.remove(animationFrameCallback);
        this.mDelayedCallbackStartTime.remove(animationFrameCallback);
        int n = this.mAnimationCallbacks.indexOf(animationFrameCallback);
        if (n >= 0) {
            this.mAnimationCallbacks.set(n, null);
            this.mListDirty = true;
        }
    }

    public void setProvider(AnimationFrameCallbackProvider animationFrameCallbackProvider) {
        this.mProvider = animationFrameCallbackProvider == null ? new MyFrameCallbackProvider() : animationFrameCallbackProvider;
    }

    static interface AnimationFrameCallback {
        public void commitAnimationFrame(long var1);

        public boolean doAnimationFrame(long var1);
    }

    public static interface AnimationFrameCallbackProvider {
        public long getFrameDelay();

        public long getFrameTime();

        public void postCommitCallback(Runnable var1);

        public void postFrameCallback(Choreographer.FrameCallback var1);

        public void setFrameDelay(long var1);
    }

    private class MyFrameCallbackProvider
    implements AnimationFrameCallbackProvider {
        final Choreographer mChoreographer = Choreographer.getInstance();

        private MyFrameCallbackProvider() {
        }

        @Override
        public long getFrameDelay() {
            return Choreographer.getFrameDelay();
        }

        @Override
        public long getFrameTime() {
            return this.mChoreographer.getFrameTime();
        }

        @Override
        public void postCommitCallback(Runnable runnable) {
            this.mChoreographer.postCallback(4, runnable, null);
        }

        @Override
        public void postFrameCallback(Choreographer.FrameCallback frameCallback) {
            this.mChoreographer.postFrameCallback(frameCallback);
        }

        @Override
        public void setFrameDelay(long l) {
            Choreographer.setFrameDelay(l);
        }
    }

}

