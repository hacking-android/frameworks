/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.dalvik;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

public final class NativeTestTarget {
    public static void emptyInlineMethod() {
    }

    public static native void emptyInternalStaticMethod();

    public static native void emptyJniStaticMethod0();

    @CriticalNative
    public static native void emptyJniStaticMethod0_Critical();

    @FastNative
    public static native void emptyJniStaticMethod0_Fast();

    public static native void emptyJniStaticMethod6(int var0, int var1, int var2, int var3, int var4, int var5);

    public static native void emptyJniStaticMethod6L(String var0, String[] var1, int[][] var2, Object var3, Object[] var4, Object[][][][] var5);

    @FastNative
    public static native void emptyJniStaticMethod6L_Fast(String var0, String[] var1, int[][] var2, Object var3, Object[] var4, Object[][][][] var5);

    @CriticalNative
    public static native void emptyJniStaticMethod6_Critical(int var0, int var1, int var2, int var3, int var4, int var5);

    @FastNative
    public static native void emptyJniStaticMethod6_Fast(int var0, int var1, int var2, int var3, int var4, int var5);

    public static synchronized native void emptyJniStaticSynchronizedMethod0();

    public native void emptyJniMethod0();

    @FastNative
    public native void emptyJniMethod0_Fast();

    public native void emptyJniMethod6(int var1, int var2, int var3, int var4, int var5, int var6);

    public native void emptyJniMethod6L(String var1, String[] var2, int[][] var3, Object var4, Object[] var5, Object[][][][] var6);

    @FastNative
    public native void emptyJniMethod6L_Fast(String var1, String[] var2, int[][] var3, Object var4, Object[] var5, Object[][][][] var6);

    @FastNative
    public native void emptyJniMethod6_Fast(int var1, int var2, int var3, int var4, int var5, int var6);

    public synchronized native void emptyJniSynchronizedMethod0();
}

