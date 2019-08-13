/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Handler;
import android.os.Message;
import android.view.Choreographer;
import android.view.Display;

public class SurfaceFlingerVsyncChoreographer {
    private static final long ONE_MS_IN_NS = 1000000L;
    private static final long ONE_S_IN_NS = 1000000000L;
    private final Choreographer mChoreographer;
    private final Handler mHandler;
    private long mSurfaceFlingerOffsetMs;

    public SurfaceFlingerVsyncChoreographer(Handler handler, Display display, Choreographer choreographer) {
        this.mHandler = handler;
        this.mChoreographer = choreographer;
        this.mSurfaceFlingerOffsetMs = this.calculateAppSurfaceFlingerVsyncOffsetMs(display);
    }

    private long calculateAppSurfaceFlingerVsyncOffsetMs(Display display) {
        return Math.max(0L, ((long)(1.0E9f / display.getRefreshRate()) - (display.getPresentationDeadlineNanos() - 1000000L) - display.getAppVsyncOffsetNanos()) / 1000000L);
    }

    private long calculateDelay() {
        long l = System.nanoTime();
        long l2 = this.mChoreographer.getLastFrameTimeNanos();
        return this.mSurfaceFlingerOffsetMs - (l - l2) / 1000000L;
    }

    public long getSurfaceFlingerOffsetMs() {
        return this.mSurfaceFlingerOffsetMs;
    }

    public void scheduleAtSfVsync(Handler handler, Message message) {
        long l = this.calculateDelay();
        if (l <= 0L) {
            handler.handleMessage(message);
        } else {
            message.setAsynchronous(true);
            handler.sendMessageDelayed(message, l);
        }
    }

    public void scheduleAtSfVsync(Runnable runnable) {
        long l = this.calculateDelay();
        if (l <= 0L) {
            runnable.run();
        } else {
            this.mHandler.postDelayed(runnable, l);
        }
    }
}

