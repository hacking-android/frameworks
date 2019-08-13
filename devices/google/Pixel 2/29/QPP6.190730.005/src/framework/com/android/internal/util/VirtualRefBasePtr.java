/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

public final class VirtualRefBasePtr {
    private long mNativePtr;

    public VirtualRefBasePtr(long l) {
        this.mNativePtr = l;
        VirtualRefBasePtr.nIncStrong(this.mNativePtr);
    }

    private static native void nDecStrong(long var0);

    private static native void nIncStrong(long var0);

    protected void finalize() throws Throwable {
        try {
            this.release();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public long get() {
        return this.mNativePtr;
    }

    public void release() {
        long l = this.mNativePtr;
        if (l != 0L) {
            VirtualRefBasePtr.nDecStrong(l);
            this.mNativePtr = 0L;
        }
    }
}

