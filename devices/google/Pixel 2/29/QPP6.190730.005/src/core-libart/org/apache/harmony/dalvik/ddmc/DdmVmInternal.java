/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.dalvik.ddmc;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;

public class DdmVmInternal {
    private DdmVmInternal() {
    }

    public static native void enableRecentAllocations(boolean var0);

    @FastNative
    public static native boolean getRecentAllocationStatus();

    @FastNative
    public static native byte[] getRecentAllocations();

    @UnsupportedAppUsage
    public static native StackTraceElement[] getStackTraceById(int var0);

    @UnsupportedAppUsage
    public static native byte[] getThreadStats();

    @FastNative
    public static native boolean heapInfoNotify(int var0);

    public static native boolean heapSegmentNotify(int var0, int var1, boolean var2);

    public static native void threadNotify(boolean var0);
}

