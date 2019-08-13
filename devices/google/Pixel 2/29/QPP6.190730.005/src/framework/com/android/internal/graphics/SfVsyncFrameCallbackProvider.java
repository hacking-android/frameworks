/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.graphics;

import android.animation.AnimationHandler;
import android.view.Choreographer;

public final class SfVsyncFrameCallbackProvider
implements AnimationHandler.AnimationFrameCallbackProvider {
    private final Choreographer mChoreographer;

    public SfVsyncFrameCallbackProvider() {
        this.mChoreographer = Choreographer.getSfInstance();
    }

    public SfVsyncFrameCallbackProvider(Choreographer choreographer) {
        this.mChoreographer = choreographer;
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

