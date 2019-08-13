/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class HandlerThread
extends Thread {
    private Handler mHandler;
    Looper mLooper;
    int mPriority;
    int mTid = -1;

    public HandlerThread(String string2) {
        super(string2);
        this.mPriority = 0;
    }

    public HandlerThread(String string2, int n) {
        super(string2);
        this.mPriority = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Looper getLooper() {
        if (!this.isAlive()) {
            return null;
        }
        synchronized (this) {
            Looper looper;
            while (this.isAlive() && (looper = this.mLooper) == null) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {}
            }
            return this.mLooper;
        }
    }

    public Handler getThreadHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.getLooper());
        }
        return this.mHandler;
    }

    public int getThreadId() {
        return this.mTid;
    }

    protected void onLooperPrepared() {
    }

    public boolean quit() {
        Looper looper = this.getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    public boolean quitSafely() {
        Looper looper = this.getLooper();
        if (looper != null) {
            looper.quitSafely();
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        this.mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            this.mLooper = Looper.myLooper();
            this.notifyAll();
        }
        Process.setThreadPriority(this.mPriority);
        this.onLooperPrepared();
        Looper.loop();
        this.mTid = -1;
    }
}

