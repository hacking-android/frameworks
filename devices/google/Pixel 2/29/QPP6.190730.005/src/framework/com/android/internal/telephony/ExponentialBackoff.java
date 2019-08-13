/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.annotations.VisibleForTesting;

public class ExponentialBackoff {
    private long mCurrentDelayMs;
    private final Handler mHandler;
    private HandlerAdapter mHandlerAdapter = new HandlerAdapter(){

        @Override
        public boolean postDelayed(Runnable runnable, long l) {
            return ExponentialBackoff.this.mHandler.postDelayed(runnable, l);
        }

        @Override
        public void removeCallbacks(Runnable runnable) {
            ExponentialBackoff.this.mHandler.removeCallbacks(runnable);
        }
    };
    private long mMaximumDelayMs;
    private int mMultiplier;
    private int mRetryCounter = 0;
    private final Runnable mRunnable;
    private long mStartDelayMs;

    public ExponentialBackoff(long l, long l2, int n, Handler handler, Runnable runnable) {
        this.mStartDelayMs = l;
        this.mMaximumDelayMs = l2;
        this.mMultiplier = n;
        this.mHandler = handler;
        this.mRunnable = runnable;
    }

    public ExponentialBackoff(long l, long l2, int n, Looper looper, Runnable runnable) {
        this(l, l2, n, new Handler(looper), runnable);
    }

    public long getCurrentDelay() {
        return this.mCurrentDelayMs;
    }

    public void notifyFailed() {
        ++this.mRetryCounter;
        long l = Math.min(this.mMaximumDelayMs, (long)((double)this.mStartDelayMs * Math.pow(this.mMultiplier, this.mRetryCounter)));
        this.mCurrentDelayMs = (long)((Math.random() + 1.0) / 2.0 * (double)l);
        this.mHandlerAdapter.removeCallbacks(this.mRunnable);
        this.mHandlerAdapter.postDelayed(this.mRunnable, this.mCurrentDelayMs);
    }

    @VisibleForTesting
    public void setHandlerAdapter(HandlerAdapter handlerAdapter) {
        this.mHandlerAdapter = handlerAdapter;
    }

    public void start() {
        this.mRetryCounter = 0;
        this.mCurrentDelayMs = this.mStartDelayMs;
        this.mHandlerAdapter.removeCallbacks(this.mRunnable);
        this.mHandlerAdapter.postDelayed(this.mRunnable, this.mCurrentDelayMs);
    }

    public void stop() {
        this.mRetryCounter = 0;
        this.mHandlerAdapter.removeCallbacks(this.mRunnable);
    }

    public static interface HandlerAdapter {
        public boolean postDelayed(Runnable var1, long var2);

        public void removeCallbacks(Runnable var1);
    }

}

