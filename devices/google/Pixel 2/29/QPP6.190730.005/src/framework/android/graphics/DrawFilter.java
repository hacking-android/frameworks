/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

public class DrawFilter {
    public long mNativeInt;

    private static native void nativeDestructor(long var0);

    protected void finalize() throws Throwable {
        try {
            DrawFilter.nativeDestructor(this.mNativeInt);
            this.mNativeInt = 0L;
            return;
        }
        finally {
            super.finalize();
        }
    }
}

