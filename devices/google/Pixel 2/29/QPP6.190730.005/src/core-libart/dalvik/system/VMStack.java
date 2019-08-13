/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.AnnotatedStackTraceElement;

public final class VMStack {
    private VMStack() {
    }

    @UnsupportedAppUsage
    @FastNative
    public static native int fillStackTraceElements(Thread var0, StackTraceElement[] var1);

    @FastNative
    public static native AnnotatedStackTraceElement[] getAnnotatedThreadStackTrace(Thread var0);

    @Deprecated
    @UnsupportedAppUsage
    @FastNative
    public static native ClassLoader getCallingClassLoader();

    @FastNative
    public static native ClassLoader getClosestUserClassLoader();

    @Deprecated
    public static Class<?> getStackClass1() {
        return VMStack.getStackClass2();
    }

    @UnsupportedAppUsage
    @FastNative
    public static native Class<?> getStackClass2();

    @UnsupportedAppUsage
    @FastNative
    public static native StackTraceElement[] getThreadStackTrace(Thread var0);
}

