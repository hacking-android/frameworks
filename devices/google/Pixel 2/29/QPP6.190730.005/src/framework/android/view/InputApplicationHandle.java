/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.IBinder;

public final class InputApplicationHandle {
    public long dispatchingTimeoutNanos;
    public String name;
    private long ptr;
    public IBinder token;

    public InputApplicationHandle(IBinder iBinder) {
        this.token = iBinder;
    }

    private native void nativeDispose();

    protected void finalize() throws Throwable {
        try {
            this.nativeDispose();
            return;
        }
        finally {
            super.finalize();
        }
    }
}

