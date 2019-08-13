/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;
import java.lang.ref.FinalizerReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public final class VMRuntime {
    private static final Map<String, String> ABI_TO_INSTRUCTION_SET_MAP;
    public static final int SDK_VERSION_CUR_DEVELOPMENT = 10000;
    private static final VMRuntime THE_ONE;
    static HiddenApiUsageLogger hiddenApiUsageLogger;
    private static Consumer<String> nonSdkApiUsageConsumer;
    private final AtomicInteger allocationCount = new AtomicInteger(0);
    private int notifyNativeInterval;
    private int targetSdkVersion = 10000;

    static {
        THE_ONE = new VMRuntime();
        ABI_TO_INSTRUCTION_SET_MAP = new HashMap<String, String>(16);
        ABI_TO_INSTRUCTION_SET_MAP.put("armeabi", "arm");
        ABI_TO_INSTRUCTION_SET_MAP.put("armeabi-v7a", "arm");
        ABI_TO_INSTRUCTION_SET_MAP.put("mips", "mips");
        ABI_TO_INSTRUCTION_SET_MAP.put("mips64", "mips64");
        ABI_TO_INSTRUCTION_SET_MAP.put("x86", "x86");
        ABI_TO_INSTRUCTION_SET_MAP.put("x86_64", "x86_64");
        ABI_TO_INSTRUCTION_SET_MAP.put("arm64-v8a", "arm64");
        nonSdkApiUsageConsumer = null;
    }

    private VMRuntime() {
    }

    public static native boolean didPruneDalvikCache();

    @UnsupportedAppUsage
    public static native String getCurrentInstructionSet();

    @UnsupportedAppUsage
    public static String getInstructionSet(String string) {
        CharSequence charSequence = ABI_TO_INSTRUCTION_SET_MAP.get(string);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported ABI: ");
        ((StringBuilder)charSequence).append(string);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private static native int getNotifyNativeInterval();

    @UnsupportedAppUsage
    public static VMRuntime getRuntime() {
        return THE_ONE;
    }

    @FastNative
    public static native boolean hasBootImageSpaces();

    private static void hiddenApiUsed(int n, String string, String string2, int n2, boolean bl) {
        HiddenApiUsageLogger hiddenApiUsageLogger = VMRuntime.hiddenApiUsageLogger;
        if (hiddenApiUsageLogger != null) {
            hiddenApiUsageLogger.hiddenApiUsed(n, string, string2, n2, bl);
        }
    }

    @UnsupportedAppUsage
    public static boolean is64BitAbi(String string) {
        return VMRuntime.is64BitInstructionSet(VMRuntime.getInstructionSet(string));
    }

    public static boolean is64BitInstructionSet(String string) {
        boolean bl = "arm64".equals(string) || "x86_64".equals(string) || "mips64".equals(string);
        return bl;
    }

    public static native boolean isBootClassPathOnDisk(String var0);

    private native void nativeSetTargetHeapUtilization(float var1);

    public static native void registerAppInfo(String var0, String[] var1);

    public static native void registerSensitiveThread();

    @UnsupportedAppUsage
    public static void runFinalization(long l) {
        try {
            FinalizerReference.finalizeAllEnqueued(l);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    public static native void setDedupeHiddenApiWarnings(boolean var0);

    public static void setHiddenApiUsageLogger(HiddenApiUsageLogger hiddenApiUsageLogger) {
        VMRuntime.hiddenApiUsageLogger = hiddenApiUsageLogger;
    }

    public static void setNonSdkApiUsageConsumer(Consumer<String> consumer) {
        nonSdkApiUsageConsumer = consumer;
    }

    public static native void setProcessDataDirectory(String var0);

    public static native void setProcessPackageName(String var0);

    public static native void setSystemDaemonThreadPriority();

    private native void setTargetSdkVersionNative(int var1);

    @UnsupportedAppUsage
    @FastNative
    public native long addressOf(Object var1);

    public native String bootClassPath();

    public native void clampGrowthLimit();

    public native String classPath();

    @UnsupportedAppUsage
    public native void clearGrowthLimit();

    public native void concurrentGC();

    public native void disableJitCompilation();

    @Deprecated
    @UnsupportedAppUsage
    public void gcSoftReferences() {
    }

    @Deprecated
    @UnsupportedAppUsage
    public long getExternalBytesAllocated() {
        return 0L;
    }

    public native long getFinalizerTimeoutMs();

    @Deprecated
    @UnsupportedAppUsage
    public long getMinimumHeapSize() {
        return 0L;
    }

    public native float getTargetHeapUtilization();

    public int getTargetSdkVersion() {
        synchronized (this) {
            int n = this.targetSdkVersion;
            return n;
        }
    }

    @UnsupportedAppUsage
    @FastNative
    public native boolean is64Bit();

    @FastNative
    public native boolean isCheckJniEnabled();

    @FastNative
    public native boolean isDebuggerActive();

    public native boolean isJavaDebuggable();

    @FastNative
    public native boolean isNativeDebuggable();

    @UnsupportedAppUsage
    @FastNative
    public native Object newNonMovableArray(Class<?> var1, int var2);

    @FastNative
    public native Object newUnpaddedArray(Class<?> var1, int var2);

    public void notifyNativeAllocation() {
        int n;
        int n2 = n = this.notifyNativeInterval;
        if (n == 0) {
            this.notifyNativeInterval = n2 = VMRuntime.getNotifyNativeInterval();
        }
        if (this.allocationCount.addAndGet(1) % n2 == 0) {
            this.notifyNativeAllocationsInternal();
        }
    }

    public native void notifyNativeAllocationsInternal();

    public native void notifyStartupCompleted();

    public native void preloadDexCaches();

    public native String[] properties();

    @Deprecated
    @UnsupportedAppUsage
    public void registerNativeAllocation(int n) {
        this.registerNativeAllocation((long)n);
    }

    @UnsupportedAppUsage
    public native void registerNativeAllocation(long var1);

    @Deprecated
    @UnsupportedAppUsage
    public void registerNativeFree(int n) {
        this.registerNativeFree((long)n);
    }

    @UnsupportedAppUsage
    public native void registerNativeFree(long var1);

    public native void requestConcurrentGC();

    public native void requestHeapTrim();

    @Deprecated
    @UnsupportedAppUsage
    public void runFinalizationSync() {
        System.runFinalization();
    }

    public native void runHeapTasks();

    public native void setHiddenApiAccessLogSamplingRate(int var1);

    public native void setHiddenApiExemptions(String[] var1);

    @Deprecated
    @UnsupportedAppUsage
    public long setMinimumHeapSize(long l) {
        return 0L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public float setTargetHeapUtilization(float f) {
        if (!(f <= 0.0f) && !(f >= 1.0f)) {
            if (f < 0.1f) {
                f = 0.1f;
            }
            synchronized (this) {
                float f2 = this.getTargetHeapUtilization();
                this.nativeSetTargetHeapUtilization(f);
                return f2;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f);
        stringBuilder.append(" out of range (0,1)");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setTargetSdkVersion(int n) {
        synchronized (this) {
            this.targetSdkVersion = n;
            this.setTargetSdkVersionNative(this.targetSdkVersion);
            return;
        }
    }

    public native void startHeapTaskProcessor();

    public native void startJitCompilation();

    public native void stopHeapTaskProcessor();

    @Deprecated
    @UnsupportedAppUsage
    public boolean trackExternalAllocation(long l) {
        return true;
    }

    @Deprecated
    @UnsupportedAppUsage
    public void trackExternalFree(long l) {
    }

    public native void trimHeap();

    public native void updateProcessState(int var1);

    @UnsupportedAppUsage
    public native String vmInstructionSet();

    @UnsupportedAppUsage
    public native String vmLibrary();

    public native String vmVersion();

    public static interface HiddenApiUsageLogger {
        public static final int ACCESS_METHOD_JNI = 2;
        public static final int ACCESS_METHOD_LINKING = 3;
        public static final int ACCESS_METHOD_NONE = 0;
        public static final int ACCESS_METHOD_REFLECTION = 1;

        public void hiddenApiUsed(int var1, String var2, String var3, int var4, boolean var5);
    }

}

