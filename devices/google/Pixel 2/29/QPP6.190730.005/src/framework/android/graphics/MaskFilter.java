/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

public class MaskFilter {
    long native_instance;

    private static native void nativeDestructor(long var0);

    protected void finalize() throws Throwable {
        MaskFilter.nativeDestructor(this.native_instance);
        this.native_instance = 0L;
    }
}

