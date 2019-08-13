/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class VMDebug {
    private static final int KIND_ALLOCATED_BYTES = 2;
    private static final int KIND_ALLOCATED_OBJECTS = 1;
    public static final int KIND_ALL_COUNTS = -1;
    private static final int KIND_CLASS_INIT_COUNT = 32;
    private static final int KIND_CLASS_INIT_TIME = 64;
    private static final int KIND_EXT_ALLOCATED_BYTES = 8192;
    private static final int KIND_EXT_ALLOCATED_OBJECTS = 4096;
    private static final int KIND_EXT_FREED_BYTES = 32768;
    private static final int KIND_EXT_FREED_OBJECTS = 16384;
    private static final int KIND_FREED_BYTES = 8;
    private static final int KIND_FREED_OBJECTS = 4;
    private static final int KIND_GC_INVOCATIONS = 16;
    public static final int KIND_GLOBAL_ALLOCATED_BYTES = 2;
    public static final int KIND_GLOBAL_ALLOCATED_OBJECTS = 1;
    public static final int KIND_GLOBAL_CLASS_INIT_COUNT = 32;
    public static final int KIND_GLOBAL_CLASS_INIT_TIME = 64;
    public static final int KIND_GLOBAL_EXT_ALLOCATED_BYTES = 8192;
    public static final int KIND_GLOBAL_EXT_ALLOCATED_OBJECTS = 4096;
    public static final int KIND_GLOBAL_EXT_FREED_BYTES = 32768;
    public static final int KIND_GLOBAL_EXT_FREED_OBJECTS = 16384;
    public static final int KIND_GLOBAL_FREED_BYTES = 8;
    public static final int KIND_GLOBAL_FREED_OBJECTS = 4;
    public static final int KIND_GLOBAL_GC_INVOCATIONS = 16;
    public static final int KIND_THREAD_ALLOCATED_BYTES = 131072;
    public static final int KIND_THREAD_ALLOCATED_OBJECTS = 65536;
    public static final int KIND_THREAD_CLASS_INIT_COUNT = 2097152;
    public static final int KIND_THREAD_CLASS_INIT_TIME = 4194304;
    public static final int KIND_THREAD_EXT_ALLOCATED_BYTES = 536870912;
    public static final int KIND_THREAD_EXT_ALLOCATED_OBJECTS = 268435456;
    public static final int KIND_THREAD_EXT_FREED_BYTES = Integer.MIN_VALUE;
    public static final int KIND_THREAD_EXT_FREED_OBJECTS = 1073741824;
    public static final int KIND_THREAD_FREED_BYTES = 524288;
    public static final int KIND_THREAD_FREED_OBJECTS = 262144;
    public static final int KIND_THREAD_GC_INVOCATIONS = 1048576;
    public static final int TRACE_COUNT_ALLOCS = 1;
    private static final HashMap<String, Integer> runtimeStatsMap = new HashMap();

    static {
        runtimeStatsMap.put("art.gc.gc-count", 0);
        runtimeStatsMap.put("art.gc.gc-time", 1);
        runtimeStatsMap.put("art.gc.bytes-allocated", 2);
        runtimeStatsMap.put("art.gc.bytes-freed", 3);
        runtimeStatsMap.put("art.gc.blocking-gc-count", 4);
        runtimeStatsMap.put("art.gc.blocking-gc-time", 5);
        runtimeStatsMap.put("art.gc.gc-count-rate-histogram", 6);
        runtimeStatsMap.put("art.gc.blocking-gc-count-rate-histogram", 7);
    }

    private VMDebug() {
    }

    @UnsupportedAppUsage
    public static native void allowHiddenApiReflectionFrom(Class<?> var0);

    public static void attachAgent(String string) throws IOException {
        VMDebug.attachAgent(string, null);
    }

    public static void attachAgent(String string, ClassLoader classLoader) throws IOException {
        VMDebug.nativeAttachAgent(string, classLoader);
    }

    public static native boolean cacheRegisterMap(String var0);

    private static int checkBufferSize(int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 8388608;
        }
        if (n2 >= 1024) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer size < 1024: ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static native long countInstancesOfClass(Class var0, boolean var1);

    public static native long[] countInstancesOfClasses(Class[] var0, boolean var1);

    public static native void crash();

    public static void dumpHprofData(String string) throws IOException {
        if (string != null) {
            VMDebug.dumpHprofData(string, null);
            return;
        }
        throw new NullPointerException("filename == null");
    }

    private static native void dumpHprofData(String var0, int var1) throws IOException;

    public static void dumpHprofData(String string, FileDescriptor fileDescriptor) throws IOException {
        int n = fileDescriptor != null ? fileDescriptor.getInt$() : -1;
        VMDebug.dumpHprofData(string, n);
    }

    public static native void dumpHprofDataDdms();

    @UnsupportedAppUsage
    public static native void dumpReferenceTables();

    public static native int getAllocCount(int var0);

    public static native void getHeapSpaceStats(long[] var0);

    public static native Object[][] getInstancesOfClasses(Class[] var0, boolean var1);

    public static native void getInstructionCount(int[] var0);

    @FastNative
    public static native int getLoadedClassCount();

    public static native int getMethodTracingMode();

    public static String getRuntimeStat(String object) {
        if (object != null) {
            if ((object = runtimeStatsMap.get(object)) != null) {
                return VMDebug.getRuntimeStatInternal((Integer)object);
            }
            return null;
        }
        throw new NullPointerException("statName == null");
    }

    private static native String getRuntimeStatInternal(int var0);

    public static Map<String, String> getRuntimeStats() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] arrstring = VMDebug.getRuntimeStatsInternal();
        for (String string : runtimeStatsMap.keySet()) {
            hashMap.put(string, arrstring[runtimeStatsMap.get(string)]);
        }
        return hashMap;
    }

    private static native String[] getRuntimeStatsInternal();

    public static native String[] getVmFeatureList();

    public static native void infopoint(int var0);

    @UnsupportedAppUsage
    @FastNative
    public static native boolean isDebuggerConnected();

    @FastNative
    public static native boolean isDebuggingEnabled();

    @FastNative
    public static native long lastDebuggerActivity();

    private static native void nativeAttachAgent(String var0, ClassLoader var1) throws IOException;

    @FastNative
    public static native void printLoadedClasses(int var0);

    public static native void resetAllocCount(int var0);

    public static native void resetInstructionCount();

    public static native void setAllocTrackerStackDepth(int var0);

    @Deprecated
    public static int setAllocationLimit(int n) {
        return -1;
    }

    @Deprecated
    public static int setGlobalAllocationLimit(int n) {
        return -1;
    }

    public static native void startAllocCounting();

    private static void startClassPrep() {
    }

    public static native void startEmulatorTracing();

    private static void startGC() {
    }

    public static native void startInstructionCounting();

    @Deprecated
    public static void startMethodTracing() {
        throw new UnsupportedOperationException();
    }

    public static void startMethodTracing(String string, int n, int n2, boolean bl, int n3) {
        VMDebug.startMethodTracingFilename(string, VMDebug.checkBufferSize(n), n2, bl, n3);
    }

    public static void startMethodTracing(String string, FileDescriptor fileDescriptor, int n, int n2, boolean bl, int n3) {
        VMDebug.startMethodTracing(string, fileDescriptor, n, n2, bl, n3, false);
    }

    public static void startMethodTracing(String string, FileDescriptor fileDescriptor, int n, int n2, boolean bl, int n3, boolean bl2) {
        if (fileDescriptor != null) {
            VMDebug.startMethodTracingFd(string, fileDescriptor.getInt$(), VMDebug.checkBufferSize(n), n2, bl, n3, bl2);
            return;
        }
        throw new NullPointerException("fd == null");
    }

    public static void startMethodTracingDdms(int n, int n2, boolean bl, int n3) {
        VMDebug.startMethodTracingDdmsImpl(VMDebug.checkBufferSize(n), n2, bl, n3);
    }

    private static native void startMethodTracingDdmsImpl(int var0, int var1, boolean var2, int var3);

    private static native void startMethodTracingFd(String var0, int var1, int var2, int var3, boolean var4, int var5, boolean var6);

    private static native void startMethodTracingFilename(String var0, int var1, int var2, boolean var3, int var4);

    public static native void stopAllocCounting();

    public static native void stopEmulatorTracing();

    public static native void stopInstructionCounting();

    public static native void stopMethodTracing();

    @FastNative
    public static native long threadCpuTimeNanos();
}

