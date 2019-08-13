/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.view.SurfaceHolder;

public class SurfaceCallbackHelper {
    int mFinishDrawingCollected = 0;
    int mFinishDrawingExpected = 0;
    private Runnable mFinishDrawingRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            SurfaceCallbackHelper surfaceCallbackHelper = SurfaceCallbackHelper.this;
            synchronized (surfaceCallbackHelper) {
                SurfaceCallbackHelper surfaceCallbackHelper2 = SurfaceCallbackHelper.this;
                ++surfaceCallbackHelper2.mFinishDrawingCollected;
                if (SurfaceCallbackHelper.this.mFinishDrawingCollected < SurfaceCallbackHelper.this.mFinishDrawingExpected) {
                    return;
                }
                SurfaceCallbackHelper.this.mRunnable.run();
                return;
            }
        }
    };
    Runnable mRunnable;

    public SurfaceCallbackHelper(Runnable runnable) {
        this.mRunnable = runnable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispatchSurfaceRedrawNeededAsync(SurfaceHolder surfaceHolder, SurfaceHolder.Callback[] arrcallback) {
        int n;
        if (arrcallback != null && arrcallback.length != 0) {
            synchronized (this) {
                this.mFinishDrawingExpected = arrcallback.length;
                n = 0;
                this.mFinishDrawingCollected = 0;
            }
        } else {
            this.mRunnable.run();
            return;
        }
        int n2 = arrcallback.length;
        while (n < n2) {
            SurfaceHolder.Callback callback = arrcallback[n];
            if (callback instanceof SurfaceHolder.Callback2) {
                ((SurfaceHolder.Callback2)callback).surfaceRedrawNeededAsync(surfaceHolder, this.mFinishDrawingRunnable);
            } else {
                this.mFinishDrawingRunnable.run();
            }
            ++n;
        }
        return;
    }

}

