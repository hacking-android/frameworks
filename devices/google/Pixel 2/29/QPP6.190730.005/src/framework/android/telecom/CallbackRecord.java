/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.os.Handler;

class CallbackRecord<T> {
    private final T mCallback;
    private final Handler mHandler;

    public CallbackRecord(T t, Handler handler) {
        this.mCallback = t;
        this.mHandler = handler;
    }

    public T getCallback() {
        return this.mCallback;
    }

    public Handler getHandler() {
        return this.mHandler;
    }
}

