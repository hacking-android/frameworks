/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import java.nio.IntBuffer;

public class GLES10Ext {
    static {
        GLES10Ext._nativeClassInit();
    }

    private static native void _nativeClassInit();

    public static native int glQueryMatrixxOES(IntBuffer var0, IntBuffer var1);

    public static native int glQueryMatrixxOES(int[] var0, int var1, int[] var2, int var3);
}

