/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Looper;

public class WindowManagerPolicyThread {
    static Looper mLooper;
    static Thread mThread;

    @UnsupportedAppUsage
    public static Looper getLooper() {
        return mLooper;
    }

    public static Thread getThread() {
        return mThread;
    }

    public static void set(Thread thread, Looper looper) {
        mThread = thread;
        mLooper = looper;
    }
}

