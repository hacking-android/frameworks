/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;

public final class SomeArgs {
    private static final int MAX_POOL_SIZE = 10;
    static final int WAIT_FINISHED = 2;
    static final int WAIT_NONE = 0;
    static final int WAIT_WAITING = 1;
    private static SomeArgs sPool;
    private static Object sPoolLock;
    private static int sPoolSize;
    @UnsupportedAppUsage
    public Object arg1;
    @UnsupportedAppUsage
    public Object arg2;
    @UnsupportedAppUsage
    public Object arg3;
    public Object arg4;
    public Object arg5;
    public Object arg6;
    public Object arg7;
    public Object arg8;
    public Object arg9;
    public int argi1;
    @UnsupportedAppUsage
    public int argi2;
    @UnsupportedAppUsage
    public int argi3;
    public int argi4;
    public int argi5;
    public int argi6;
    private boolean mInPool;
    private SomeArgs mNext;
    int mWaitState = 0;

    static {
        sPoolLock = new Object();
    }

    private SomeArgs() {
    }

    private void clear() {
        this.arg1 = null;
        this.arg2 = null;
        this.arg3 = null;
        this.arg4 = null;
        this.arg5 = null;
        this.arg6 = null;
        this.arg7 = null;
        this.arg8 = null;
        this.arg9 = null;
        this.argi1 = 0;
        this.argi2 = 0;
        this.argi3 = 0;
        this.argi4 = 0;
        this.argi5 = 0;
        this.argi6 = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static SomeArgs obtain() {
        Object object = sPoolLock;
        synchronized (object) {
            if (sPoolSize <= 0) return new SomeArgs();
            SomeArgs someArgs = sPool;
            sPool = SomeArgs.sPool.mNext;
            someArgs.mNext = null;
            someArgs.mInPool = false;
            --sPoolSize;
            return someArgs;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void complete() {
        synchronized (this) {
            if (this.mWaitState == 1) {
                this.mWaitState = 2;
                this.notifyAll();
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Not waiting");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void recycle() {
        if (this.mInPool) {
            throw new IllegalStateException("Already recycled.");
        }
        if (this.mWaitState != 0) {
            return;
        }
        Object object = sPoolLock;
        synchronized (object) {
            this.clear();
            if (sPoolSize < 10) {
                this.mNext = sPool;
                this.mInPool = true;
                sPool = this;
                ++sPoolSize;
            }
            return;
        }
    }
}

