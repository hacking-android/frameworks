/*
 * Decompiled with CFR 0.145.
 */
package com.android.server;

import android.annotation.UnsupportedAppUsage;
import android.os.ConditionVariable;
import android.os.SystemClock;

abstract class ResettableTimeout {
    @UnsupportedAppUsage
    private ConditionVariable mLock = new ConditionVariable();
    @UnsupportedAppUsage
    private volatile long mOffAt;
    private volatile boolean mOffCalled;
    private Thread mThread;

    ResettableTimeout() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void cancel() {
        synchronized (this) {
            this.mOffAt = 0L;
            if (this.mThread != null) {
                this.mThread.interrupt();
                this.mThread = null;
            }
            if (!this.mOffCalled) {
                this.mOffCalled = true;
                this.off();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void go(long l) {
        synchronized (this) {
            boolean bl;
            this.mOffAt = SystemClock.uptimeMillis() + l;
            if (this.mThread == null) {
                bl = false;
                this.mLock.close();
                T t = new T();
                this.mThread = t;
                this.mThread.start();
                this.mLock.block();
                this.mOffCalled = false;
            } else {
                bl = true;
                this.mThread.interrupt();
            }
            this.on(bl);
            return;
        }
    }

    public abstract void off();

    public abstract void on(boolean var1);

    private class T
    extends Thread {
        private T() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            ResettableTimeout.this.mLock.open();
            do {
                long l;
                synchronized (this) {
                    l = ResettableTimeout.this.mOffAt - SystemClock.uptimeMillis();
                    if (l <= 0L) {
                        ResettableTimeout.this.mOffCalled = true;
                        ResettableTimeout.this.off();
                        ResettableTimeout.this.mThread = null;
                        return;
                    }
                }
                try {
                    T.sleep(l);
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }

}

