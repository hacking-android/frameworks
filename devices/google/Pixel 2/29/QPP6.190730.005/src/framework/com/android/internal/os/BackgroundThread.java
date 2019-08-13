/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.concurrent.Executor;

public final class BackgroundThread
extends HandlerThread {
    private static final long SLOW_DELIVERY_THRESHOLD_MS = 30000L;
    private static final long SLOW_DISPATCH_THRESHOLD_MS = 10000L;
    private static Handler sHandler;
    private static HandlerExecutor sHandlerExecutor;
    private static BackgroundThread sInstance;

    private BackgroundThread() {
        super("android.bg", 10);
    }

    private static void ensureThreadLocked() {
        if (sInstance == null) {
            sInstance = new BackgroundThread();
            sInstance.start();
            Looper looper = sInstance.getLooper();
            looper.setTraceTag(524288L);
            looper.setSlowLogThresholdMs(10000L, 30000L);
            sHandler = new Handler(sInstance.getLooper());
            sHandlerExecutor = new HandlerExecutor(sHandler);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static BackgroundThread get() {
        synchronized (BackgroundThread.class) {
            BackgroundThread.ensureThreadLocked();
            return sInstance;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Executor getExecutor() {
        synchronized (BackgroundThread.class) {
            BackgroundThread.ensureThreadLocked();
            return sHandlerExecutor;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Handler getHandler() {
        synchronized (BackgroundThread.class) {
            BackgroundThread.ensureThreadLocked();
            return sHandler;
        }
    }
}

