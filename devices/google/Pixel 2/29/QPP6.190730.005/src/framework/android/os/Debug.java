/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMDebug
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.content.Context;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.Preconditions;
import com.android.internal.util.TypedProperties;
import dalvik.system.VMDebug;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public final class Debug {
    private static final String DEFAULT_TRACE_BODY = "dmtrace";
    private static final String DEFAULT_TRACE_EXTENSION = ".trace";
    public static final int MEMINFO_BUFFERS = 2;
    public static final int MEMINFO_CACHED = 3;
    public static final int MEMINFO_COUNT = 15;
    public static final int MEMINFO_FREE = 1;
    public static final int MEMINFO_KERNEL_STACK = 14;
    public static final int MEMINFO_MAPPED = 11;
    public static final int MEMINFO_PAGE_TABLES = 13;
    public static final int MEMINFO_SHMEM = 4;
    public static final int MEMINFO_SLAB = 5;
    public static final int MEMINFO_SLAB_RECLAIMABLE = 6;
    public static final int MEMINFO_SLAB_UNRECLAIMABLE = 7;
    public static final int MEMINFO_SWAP_FREE = 9;
    public static final int MEMINFO_SWAP_TOTAL = 8;
    public static final int MEMINFO_TOTAL = 0;
    public static final int MEMINFO_VM_ALLOC_USED = 12;
    public static final int MEMINFO_ZRAM_TOTAL = 10;
    private static final int MIN_DEBUGGER_IDLE = 1300;
    public static final int SHOW_CLASSLOADER = 2;
    public static final int SHOW_FULL_DETAIL = 1;
    public static final int SHOW_INITIALIZED = 4;
    private static final int SPIN_DELAY = 200;
    private static final String SYSFS_QEMU_TRACE_STATE = "/sys/qemu_trace/state";
    private static final String TAG = "Debug";
    @Deprecated
    public static final int TRACE_COUNT_ALLOCS = 1;
    private static final TypedProperties debugProperties;
    private static volatile boolean mWaiting;

    static {
        mWaiting = false;
        debugProperties = null;
    }

    @UnsupportedAppUsage
    private Debug() {
    }

    public static void attachJvmtiAgent(String string2, String string3, ClassLoader classLoader) throws IOException {
        Preconditions.checkNotNull(string2);
        Preconditions.checkArgument(string2.contains("=") ^ true);
        if (string3 == null) {
            VMDebug.attachAgent((String)string2, (ClassLoader)classLoader);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("=");
            stringBuilder.append(string3);
            VMDebug.attachAgent((String)stringBuilder.toString(), (ClassLoader)classLoader);
        }
    }

    public static final boolean cacheRegisterMap(String string2) {
        return VMDebug.cacheRegisterMap((String)string2);
    }

    @Deprecated
    public static void changeDebugPort(int n) {
    }

    @UnsupportedAppUsage
    public static long countInstancesOfClass(Class class_) {
        return VMDebug.countInstancesOfClass((Class)class_, (boolean)true);
    }

    public static void dumpHprofData(String string2) throws IOException {
        VMDebug.dumpHprofData((String)string2);
    }

    public static void dumpHprofData(String string2, FileDescriptor fileDescriptor) throws IOException {
        VMDebug.dumpHprofData((String)string2, (FileDescriptor)fileDescriptor);
    }

    public static void dumpHprofDataDdms() {
        VMDebug.dumpHprofDataDdms();
    }

    public static native boolean dumpJavaBacktraceToFileTimeout(int var0, String var1, int var2);

    public static native boolean dumpNativeBacktraceToFileTimeout(int var0, String var1, int var2);

    @UnsupportedAppUsage
    public static native void dumpNativeHeap(FileDescriptor var0);

    public static native void dumpNativeMallocInfo(FileDescriptor var0);

    @UnsupportedAppUsage
    public static final void dumpReferenceTables() {
        VMDebug.dumpReferenceTables();
    }

    public static boolean dumpService(String string2, FileDescriptor object, String[] object2) {
        IBinder iBinder = ServiceManager.getService(string2);
        if (iBinder == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't find service to dump: ");
            ((StringBuilder)object).append(string2);
            Log.e("Debug", ((StringBuilder)object).toString());
            return false;
        }
        try {
            iBinder.dump((FileDescriptor)object, (String[])object2);
            return true;
        }
        catch (RemoteException remoteException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Can't dump service: ");
            ((StringBuilder)object2).append(string2);
            Log.e("Debug", ((StringBuilder)object2).toString(), remoteException);
            return false;
        }
    }

    public static void enableEmulatorTraceOutput() {
        VMDebug.startEmulatorTracing();
    }

    private static boolean fieldTypeMatches(Field annotatedElement, Class<?> annotatedElement2) {
        annotatedElement = annotatedElement.getType();
        boolean bl = true;
        if (annotatedElement == annotatedElement2) {
            return true;
        }
        try {
            annotatedElement2 = ((Class)annotatedElement2).getField("TYPE");
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return false;
        }
        try {
            annotatedElement2 = (Class)((Field)annotatedElement2).get(null);
            if (annotatedElement != annotatedElement2) {
                bl = false;
            }
            return bl;
        }
        catch (IllegalAccessException illegalAccessException) {
            return false;
        }
    }

    private static String fixTracePath(String object) {
        Object object2;
        block5 : {
            block4 : {
                if (object == null) break block4;
                object2 = object;
                if (((String)object).charAt(0) == '/') break block5;
            }
            object2 = (object2 = AppGlobals.getInitialApplication()) != null ? ((Context)object2).getExternalFilesDir(null) : Environment.getExternalStorageDirectory();
            object2 = object == null ? new File((File)object2, "dmtrace").getAbsolutePath() : new File((File)object2, (String)object).getAbsolutePath();
        }
        object = object2;
        if (!((String)object2).endsWith(".trace")) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(".trace");
            object = ((StringBuilder)object).toString();
        }
        return object;
    }

    public static final native int getBinderDeathObjectCount();

    public static final native int getBinderLocalObjectCount();

    public static final native int getBinderProxyObjectCount();

    public static native int getBinderReceivedTransactions();

    public static native int getBinderSentTransactions();

    @UnsupportedAppUsage
    public static String getCaller() {
        return Debug.getCaller(Thread.currentThread().getStackTrace(), 0);
    }

    private static String getCaller(StackTraceElement[] object, int n) {
        if (n + 4 >= ((StackTraceElement[])object).length) {
            return "<bottom of call stack>";
        }
        StackTraceElement stackTraceElement = object[n + 4];
        object = new StringBuilder();
        ((StringBuilder)object).append(stackTraceElement.getClassName());
        ((StringBuilder)object).append(".");
        ((StringBuilder)object).append(stackTraceElement.getMethodName());
        ((StringBuilder)object).append(":");
        ((StringBuilder)object).append(stackTraceElement.getLineNumber());
        return ((StringBuilder)object).toString();
    }

    @UnsupportedAppUsage
    public static String getCallers(int n) {
        StackTraceElement[] arrstackTraceElement = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            stringBuffer.append(Debug.getCaller(arrstackTraceElement, i));
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    public static String getCallers(int n, int n2) {
        StackTraceElement[] arrstackTraceElement = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = n; i < n2 + n; ++i) {
            stringBuffer.append(Debug.getCaller(arrstackTraceElement, i));
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }

    public static String getCallers(int n, String string2) {
        StackTraceElement[] arrstackTraceElement = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            stringBuffer.append(string2);
            stringBuffer.append(Debug.getCaller(arrstackTraceElement, i));
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    @Deprecated
    public static int getGlobalAllocCount() {
        return VMDebug.getAllocCount((int)1);
    }

    @Deprecated
    public static int getGlobalAllocSize() {
        return VMDebug.getAllocCount((int)2);
    }

    @Deprecated
    public static int getGlobalClassInitCount() {
        return VMDebug.getAllocCount((int)32);
    }

    @Deprecated
    public static int getGlobalClassInitTime() {
        return VMDebug.getAllocCount((int)64);
    }

    @Deprecated
    public static int getGlobalExternalAllocCount() {
        return 0;
    }

    @Deprecated
    public static int getGlobalExternalAllocSize() {
        return 0;
    }

    @Deprecated
    public static int getGlobalExternalFreedCount() {
        return 0;
    }

    @Deprecated
    public static int getGlobalExternalFreedSize() {
        return 0;
    }

    @Deprecated
    public static int getGlobalFreedCount() {
        return VMDebug.getAllocCount((int)4);
    }

    @Deprecated
    public static int getGlobalFreedSize() {
        return VMDebug.getAllocCount((int)8);
    }

    @Deprecated
    public static int getGlobalGcInvocationCount() {
        return VMDebug.getAllocCount((int)16);
    }

    public static int getLoadedClassCount() {
        return VMDebug.getLoadedClassCount();
    }

    @UnsupportedAppUsage
    public static native void getMemInfo(long[] var0);

    @UnsupportedAppUsage
    public static native void getMemoryInfo(int var0, MemoryInfo var1);

    public static native void getMemoryInfo(MemoryInfo var0);

    public static int getMethodTracingMode() {
        return VMDebug.getMethodTracingMode();
    }

    public static native long getNativeHeapAllocatedSize();

    public static native long getNativeHeapFreeSize();

    public static native long getNativeHeapSize();

    public static native long getPss();

    public static native long getPss(int var0, long[] var1, long[] var2);

    public static String getRuntimeStat(String string2) {
        return VMDebug.getRuntimeStat((String)string2);
    }

    public static Map<String, String> getRuntimeStats() {
        return VMDebug.getRuntimeStats();
    }

    @Deprecated
    public static int getThreadAllocCount() {
        return VMDebug.getAllocCount((int)65536);
    }

    @Deprecated
    public static int getThreadAllocSize() {
        return VMDebug.getAllocCount((int)131072);
    }

    @Deprecated
    public static int getThreadExternalAllocCount() {
        return 0;
    }

    @Deprecated
    public static int getThreadExternalAllocSize() {
        return 0;
    }

    @Deprecated
    public static int getThreadGcInvocationCount() {
        return VMDebug.getAllocCount((int)1048576);
    }

    public static native String getUnreachableMemory(int var0, boolean var1);

    public static String[] getVmFeatureList() {
        return VMDebug.getVmFeatureList();
    }

    public static native long getZramFreeKb();

    public static boolean isDebuggerConnected() {
        return VMDebug.isDebuggerConnected();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void modifyFieldIfSet(Field object, TypedProperties object2, String string2) {
        if (((Field)object).getType() == String.class) {
            int n = ((TypedProperties)object2).getStringInfo(string2);
            if (n == -2) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Type of ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append("  does not match field type (");
                ((StringBuilder)object2).append(((Field)object).getType());
                ((StringBuilder)object2).append(")");
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            if (n == -1) return;
            if (n != 0) {
                if (n != 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected getStringInfo(");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append(") return value ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
            } else {
                try {
                    ((Field)object).set(null, null);
                    return;
                }
                catch (IllegalAccessException illegalAccessException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Cannot set field for ");
                    ((StringBuilder)object).append(string2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString(), illegalAccessException);
                }
            }
        }
        if ((object2 = ((TypedProperties)object2).get(string2)) == null) return;
        if (!Debug.fieldTypeMatches((Field)object, object2.getClass())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Type of ");
            stringBuilder.append(string2);
            stringBuilder.append(" (");
            stringBuilder.append(object2.getClass());
            stringBuilder.append(")  does not match field type (");
            stringBuilder.append(((Field)object).getType());
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        try {
            ((Field)object).set(null, object2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Cannot set field for ");
            ((StringBuilder)object2).append(string2);
            throw new IllegalArgumentException(((StringBuilder)object2).toString(), illegalAccessException);
        }
    }

    public static void printLoadedClasses(int n) {
        VMDebug.printLoadedClasses((int)n);
    }

    @Deprecated
    public static void resetAllCounts() {
        VMDebug.resetAllocCount((int)-1);
    }

    @Deprecated
    public static void resetGlobalAllocCount() {
        VMDebug.resetAllocCount((int)1);
    }

    @Deprecated
    public static void resetGlobalAllocSize() {
        VMDebug.resetAllocCount((int)2);
    }

    @Deprecated
    public static void resetGlobalClassInitCount() {
        VMDebug.resetAllocCount((int)32);
    }

    @Deprecated
    public static void resetGlobalClassInitTime() {
        VMDebug.resetAllocCount((int)64);
    }

    @Deprecated
    public static void resetGlobalExternalAllocCount() {
    }

    @Deprecated
    public static void resetGlobalExternalAllocSize() {
    }

    @Deprecated
    public static void resetGlobalExternalFreedCount() {
    }

    @Deprecated
    public static void resetGlobalExternalFreedSize() {
    }

    @Deprecated
    public static void resetGlobalFreedCount() {
        VMDebug.resetAllocCount((int)4);
    }

    @Deprecated
    public static void resetGlobalFreedSize() {
        VMDebug.resetAllocCount((int)8);
    }

    @Deprecated
    public static void resetGlobalGcInvocationCount() {
        VMDebug.resetAllocCount((int)16);
    }

    @Deprecated
    public static void resetThreadAllocCount() {
        VMDebug.resetAllocCount((int)65536);
    }

    @Deprecated
    public static void resetThreadAllocSize() {
        VMDebug.resetAllocCount((int)131072);
    }

    @Deprecated
    public static void resetThreadExternalAllocCount() {
    }

    @Deprecated
    public static void resetThreadExternalAllocSize() {
    }

    @Deprecated
    public static void resetThreadGcInvocationCount() {
        VMDebug.resetAllocCount((int)1048576);
    }

    @Deprecated
    public static int setAllocationLimit(int n) {
        return -1;
    }

    public static void setFieldsOn(Class<?> class_) {
        Debug.setFieldsOn(class_, false);
    }

    public static void setFieldsOn(Class<?> object, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setFieldsOn(");
        object = object == null ? "null" : ((Class)object).getName();
        stringBuilder.append((String)object);
        stringBuilder.append(") called in non-DEBUG build");
        Log.wtf("Debug", stringBuilder.toString());
    }

    @Deprecated
    public static int setGlobalAllocationLimit(int n) {
        return -1;
    }

    @Deprecated
    public static void startAllocCounting() {
        VMDebug.startAllocCounting();
    }

    public static void startMethodTracing() {
        VMDebug.startMethodTracing((String)Debug.fixTracePath(null), (int)0, (int)0, (boolean)false, (int)0);
    }

    public static void startMethodTracing(String string2) {
        Debug.startMethodTracing(string2, 0, 0);
    }

    public static void startMethodTracing(String string2, int n) {
        Debug.startMethodTracing(string2, n, 0);
    }

    public static void startMethodTracing(String string2, int n, int n2) {
        VMDebug.startMethodTracing((String)Debug.fixTracePath(string2), (int)n, (int)n2, (boolean)false, (int)0);
    }

    public static void startMethodTracing(String string2, FileDescriptor fileDescriptor, int n, int n2, boolean bl) {
        VMDebug.startMethodTracing((String)string2, (FileDescriptor)fileDescriptor, (int)n, (int)n2, (boolean)false, (int)0, (boolean)bl);
    }

    public static void startMethodTracingDdms(int n, int n2, boolean bl, int n3) {
        VMDebug.startMethodTracingDdms((int)n, (int)n2, (boolean)bl, (int)n3);
    }

    public static void startMethodTracingSampling(String string2, int n, int n2) {
        VMDebug.startMethodTracing((String)Debug.fixTracePath(string2), (int)n, (int)0, (boolean)true, (int)n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void startNativeTracing() {
        block4 : {
            PrintWriter printWriter;
            PrintWriter printWriter2;
            PrintWriter printWriter3 = null;
            PrintWriter printWriter4 = printWriter2 = null;
            PrintWriter printWriter5 = printWriter3;
            try {
                printWriter4 = printWriter2;
                printWriter5 = printWriter3;
                FileOutputStream fileOutputStream = new FileOutputStream("/sys/qemu_trace/state");
                printWriter4 = printWriter2;
                printWriter5 = printWriter3;
                printWriter4 = printWriter2;
                printWriter5 = printWriter3;
                printWriter4 = printWriter = new FastPrintWriter(fileOutputStream);
                printWriter5 = printWriter;
                printWriter.println("1");
            }
            catch (Throwable throwable) {
                if (printWriter4 != null) {
                    printWriter4.close();
                }
                throw throwable;
            }
            catch (Exception exception) {
                if (printWriter5 == null) break block4;
                printWriter = printWriter5;
            }
            printWriter.close();
        }
        VMDebug.startEmulatorTracing();
    }

    @Deprecated
    public static void stopAllocCounting() {
        VMDebug.stopAllocCounting();
    }

    public static void stopMethodTracing() {
        VMDebug.stopMethodTracing();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void stopNativeTracing() {
        PrintWriter printWriter;
        VMDebug.stopEmulatorTracing();
        PrintWriter printWriter2 = null;
        PrintWriter printWriter3 = printWriter = null;
        PrintWriter printWriter4 = printWriter2;
        try {
            printWriter3 = printWriter;
            printWriter4 = printWriter2;
            FileOutputStream fileOutputStream = new FileOutputStream("/sys/qemu_trace/state");
            printWriter3 = printWriter;
            printWriter4 = printWriter2;
            printWriter3 = printWriter;
            printWriter4 = printWriter2;
            FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
            printWriter3 = printWriter = fastPrintWriter;
            printWriter4 = printWriter;
            printWriter.println("0");
        }
        catch (Throwable throwable) {
            if (printWriter3 == null) throw throwable;
            printWriter3.close();
            throw throwable;
        }
        catch (Exception exception) {
            if (printWriter4 == null) return;
            printWriter = printWriter4;
        }
        printWriter.close();
    }

    public static long threadCpuTimeNanos() {
        return VMDebug.threadCpuTimeNanos();
    }

    public static void waitForDebugger() {
        block9 : {
            long l;
            if (!VMDebug.isDebuggingEnabled()) {
                return;
            }
            if (Debug.isDebuggerConnected()) {
                return;
            }
            System.out.println("Sending WAIT chunk");
            DdmServer.sendChunk((Chunk)new Chunk(ChunkHandler.type((String)"WAIT"), new byte[]{0}, 0, 1));
            mWaiting = true;
            while (!Debug.isDebuggerConnected()) {
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException interruptedException) {}
            }
            mWaiting = false;
            System.out.println("Debugger has connected");
            do {
                if ((l = VMDebug.lastDebuggerActivity()) < 0L) {
                    System.out.println("debugger detached?");
                    break block9;
                }
                if (l >= 1300L) break;
                System.out.println("waiting for debugger to settle...");
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException interruptedException) {}
            } while (true);
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("debugger has settled (");
            stringBuilder.append(l);
            stringBuilder.append(")");
            printStream.println(stringBuilder.toString());
        }
    }

    public static boolean waitingForDebugger() {
        return mWaiting;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface DebugProperty {
    }

    @Deprecated
    public static class InstructionCount {
        public boolean collect() {
            return false;
        }

        public int globalMethodInvocations() {
            return 0;
        }

        public int globalTotal() {
            return 0;
        }

        public boolean resetAndStart() {
            return false;
        }
    }

    public static class MemoryInfo
    implements Parcelable {
        public static final Parcelable.Creator<MemoryInfo> CREATOR = new Parcelable.Creator<MemoryInfo>(){

            @Override
            public MemoryInfo createFromParcel(Parcel parcel) {
                return new MemoryInfo(parcel);
            }

            public MemoryInfo[] newArray(int n) {
                return new MemoryInfo[n];
            }
        };
        public static final int HEAP_DALVIK = 1;
        public static final int HEAP_NATIVE = 2;
        public static final int HEAP_UNKNOWN = 0;
        public static final int NUM_CATEGORIES = 9;
        @UnsupportedAppUsage
        public static final int NUM_DVK_STATS = 14;
        @UnsupportedAppUsage
        public static final int NUM_OTHER_STATS = 17;
        public static final int OFFSET_PRIVATE_CLEAN = 5;
        public static final int OFFSET_PRIVATE_DIRTY = 3;
        public static final int OFFSET_PSS = 0;
        public static final int OFFSET_RSS = 2;
        public static final int OFFSET_SHARED_CLEAN = 6;
        public static final int OFFSET_SHARED_DIRTY = 4;
        public static final int OFFSET_SWAPPABLE_PSS = 1;
        public static final int OFFSET_SWAPPED_OUT = 7;
        public static final int OFFSET_SWAPPED_OUT_PSS = 8;
        public static final int OTHER_APK = 8;
        public static final int OTHER_ART = 12;
        public static final int OTHER_ART_APP = 29;
        public static final int OTHER_ART_BOOT = 30;
        public static final int OTHER_ASHMEM = 3;
        public static final int OTHER_CURSOR = 2;
        public static final int OTHER_DALVIK_LARGE = 18;
        public static final int OTHER_DALVIK_NON_MOVING = 20;
        public static final int OTHER_DALVIK_NORMAL = 17;
        public static final int OTHER_DALVIK_OTHER = 0;
        public static final int OTHER_DALVIK_OTHER_ACCOUNTING = 22;
        public static final int OTHER_DALVIK_OTHER_CODE_CACHE = 23;
        public static final int OTHER_DALVIK_OTHER_COMPILER_METADATA = 24;
        public static final int OTHER_DALVIK_OTHER_INDIRECT_REFERENCE_TABLE = 25;
        public static final int OTHER_DALVIK_OTHER_LINEARALLOC = 21;
        public static final int OTHER_DALVIK_ZYGOTE = 19;
        public static final int OTHER_DEX = 10;
        public static final int OTHER_DEX_APP_DEX = 27;
        public static final int OTHER_DEX_APP_VDEX = 28;
        public static final int OTHER_DEX_BOOT_VDEX = 26;
        public static final int OTHER_DVK_STAT_ART_END = 13;
        public static final int OTHER_DVK_STAT_ART_START = 12;
        public static final int OTHER_DVK_STAT_DALVIK_END = 3;
        public static final int OTHER_DVK_STAT_DALVIK_OTHER_END = 8;
        public static final int OTHER_DVK_STAT_DALVIK_OTHER_START = 4;
        public static final int OTHER_DVK_STAT_DALVIK_START = 0;
        public static final int OTHER_DVK_STAT_DEX_END = 11;
        public static final int OTHER_DVK_STAT_DEX_START = 9;
        public static final int OTHER_GL = 15;
        public static final int OTHER_GL_DEV = 4;
        public static final int OTHER_GRAPHICS = 14;
        public static final int OTHER_JAR = 7;
        public static final int OTHER_OAT = 11;
        public static final int OTHER_OTHER_MEMTRACK = 16;
        public static final int OTHER_SO = 6;
        public static final int OTHER_STACK = 1;
        public static final int OTHER_TTF = 9;
        public static final int OTHER_UNKNOWN_DEV = 5;
        public static final int OTHER_UNKNOWN_MAP = 13;
        @UnsupportedAppUsage
        public int dalvikPrivateClean;
        public int dalvikPrivateDirty;
        public int dalvikPss;
        @UnsupportedAppUsage
        public int dalvikRss;
        @UnsupportedAppUsage
        public int dalvikSharedClean;
        public int dalvikSharedDirty;
        @UnsupportedAppUsage
        public int dalvikSwappablePss;
        @UnsupportedAppUsage
        public int dalvikSwappedOut;
        @UnsupportedAppUsage
        public int dalvikSwappedOutPss;
        @UnsupportedAppUsage
        public boolean hasSwappedOutPss;
        @UnsupportedAppUsage
        public int nativePrivateClean;
        public int nativePrivateDirty;
        public int nativePss;
        @UnsupportedAppUsage
        public int nativeRss;
        @UnsupportedAppUsage
        public int nativeSharedClean;
        public int nativeSharedDirty;
        @UnsupportedAppUsage
        public int nativeSwappablePss;
        @UnsupportedAppUsage
        public int nativeSwappedOut;
        @UnsupportedAppUsage
        public int nativeSwappedOutPss;
        @UnsupportedAppUsage
        public int otherPrivateClean;
        public int otherPrivateDirty;
        public int otherPss;
        @UnsupportedAppUsage
        public int otherRss;
        @UnsupportedAppUsage
        public int otherSharedClean;
        public int otherSharedDirty;
        @UnsupportedAppUsage
        private int[] otherStats = new int[279];
        @UnsupportedAppUsage
        public int otherSwappablePss;
        @UnsupportedAppUsage
        public int otherSwappedOut;
        @UnsupportedAppUsage
        public int otherSwappedOutPss;

        public MemoryInfo() {
        }

        private MemoryInfo(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @UnsupportedAppUsage
        public static String getOtherLabel(int n) {
            switch (n) {
                default: {
                    return "????";
                }
                case 30: {
                    return ".Boot art";
                }
                case 29: {
                    return ".App art";
                }
                case 28: {
                    return ".App vdex";
                }
                case 27: {
                    return ".App dex";
                }
                case 26: {
                    return ".Boot vdex";
                }
                case 25: {
                    return ".IndirectRef";
                }
                case 24: {
                    return ".CompilerMetadata";
                }
                case 23: {
                    return ".JITCache";
                }
                case 22: {
                    return ".GC";
                }
                case 21: {
                    return ".LinearAlloc";
                }
                case 20: {
                    return ".NonMoving";
                }
                case 19: {
                    return ".Zygote";
                }
                case 18: {
                    return ".LOS";
                }
                case 17: {
                    return ".Heap";
                }
                case 16: {
                    return "Other mtrack";
                }
                case 15: {
                    return "GL mtrack";
                }
                case 14: {
                    return "EGL mtrack";
                }
                case 13: {
                    return "Other mmap";
                }
                case 12: {
                    return ".art mmap";
                }
                case 11: {
                    return ".oat mmap";
                }
                case 10: {
                    return ".dex mmap";
                }
                case 9: {
                    return ".ttf mmap";
                }
                case 8: {
                    return ".apk mmap";
                }
                case 7: {
                    return ".jar mmap";
                }
                case 6: {
                    return ".so mmap";
                }
                case 5: {
                    return "Other dev";
                }
                case 4: {
                    return "Gfx dev";
                }
                case 3: {
                    return "Ashmem";
                }
                case 2: {
                    return "Cursor";
                }
                case 1: {
                    return "Stack";
                }
                case 0: 
            }
            return "Dalvik Other";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public String getMemoryStat(String var1_1) {
            block22 : {
                switch (var1_1.hashCode()) {
                    case 2069370308: {
                        if (!var1_1.equals("summary.total-swap")) break;
                        var2_2 = 8;
                        break block22;
                    }
                    case 2016489427: {
                        if (!var1_1.equals("summary.graphics")) break;
                        var2_2 = 4;
                        break block22;
                    }
                    case 1640306485: {
                        if (!var1_1.equals("summary.code")) break;
                        var2_2 = 2;
                        break block22;
                    }
                    case 549300599: {
                        if (!var1_1.equals("summary.system")) break;
                        var2_2 = 6;
                        break block22;
                    }
                    case -675184064: {
                        if (!var1_1.equals("summary.stack")) break;
                        var2_2 = 3;
                        break block22;
                    }
                    case -1040176230: {
                        if (!var1_1.equals("summary.native-heap")) break;
                        var2_2 = 1;
                        break block22;
                    }
                    case -1086991874: {
                        if (!var1_1.equals("summary.private-other")) break;
                        var2_2 = 5;
                        break block22;
                    }
                    case -1318722433: {
                        if (!var1_1.equals("summary.total-pss")) break;
                        var2_2 = 7;
                        break block22;
                    }
                    case -1629983121: {
                        if (!var1_1.equals("summary.java-heap")) break;
                        var2_2 = 0;
                        break block22;
                    }
                }
                ** break;
lbl39: // 1 sources:
                var2_2 = -1;
            }
            switch (var2_2) {
                default: {
                    return null;
                }
                case 8: {
                    return Integer.toString(this.getSummaryTotalSwap());
                }
                case 7: {
                    return Integer.toString(this.getSummaryTotalPss());
                }
                case 6: {
                    return Integer.toString(this.getSummarySystem());
                }
                case 5: {
                    return Integer.toString(this.getSummaryPrivateOther());
                }
                case 4: {
                    return Integer.toString(this.getSummaryGraphics());
                }
                case 3: {
                    return Integer.toString(this.getSummaryStack());
                }
                case 2: {
                    return Integer.toString(this.getSummaryCode());
                }
                case 1: {
                    return Integer.toString(this.getSummaryNativeHeap());
                }
                case 0: 
            }
            return Integer.toString(this.getSummaryJavaHeap());
        }

        public Map<String, String> getMemoryStats() {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("summary.java-heap", Integer.toString(this.getSummaryJavaHeap()));
            hashMap.put("summary.native-heap", Integer.toString(this.getSummaryNativeHeap()));
            hashMap.put("summary.code", Integer.toString(this.getSummaryCode()));
            hashMap.put("summary.stack", Integer.toString(this.getSummaryStack()));
            hashMap.put("summary.graphics", Integer.toString(this.getSummaryGraphics()));
            hashMap.put("summary.private-other", Integer.toString(this.getSummaryPrivateOther()));
            hashMap.put("summary.system", Integer.toString(this.getSummarySystem()));
            hashMap.put("summary.total-pss", Integer.toString(this.getSummaryTotalPss()));
            hashMap.put("summary.total-swap", Integer.toString(this.getSummaryTotalSwap()));
            return hashMap;
        }

        @UnsupportedAppUsage
        public int getOtherPrivate(int n) {
            return this.getOtherPrivateClean(n) + this.getOtherPrivateDirty(n);
        }

        public int getOtherPrivateClean(int n) {
            return this.otherStats[n * 9 + 5];
        }

        @UnsupportedAppUsage
        public int getOtherPrivateDirty(int n) {
            return this.otherStats[n * 9 + 3];
        }

        @UnsupportedAppUsage
        public int getOtherPss(int n) {
            return this.otherStats[n * 9 + 0];
        }

        public int getOtherRss(int n) {
            return this.otherStats[n * 9 + 2];
        }

        public int getOtherSharedClean(int n) {
            return this.otherStats[n * 9 + 6];
        }

        @UnsupportedAppUsage
        public int getOtherSharedDirty(int n) {
            return this.otherStats[n * 9 + 4];
        }

        public int getOtherSwappablePss(int n) {
            return this.otherStats[n * 9 + 1];
        }

        public int getOtherSwappedOut(int n) {
            return this.otherStats[n * 9 + 7];
        }

        public int getOtherSwappedOutPss(int n) {
            return this.otherStats[n * 9 + 8];
        }

        @UnsupportedAppUsage
        public int getSummaryCode() {
            return this.getOtherPrivate(6) + this.getOtherPrivate(7) + this.getOtherPrivate(8) + this.getOtherPrivate(9) + this.getOtherPrivate(10) + this.getOtherPrivate(11);
        }

        @UnsupportedAppUsage
        public int getSummaryGraphics() {
            return this.getOtherPrivate(4) + this.getOtherPrivate(14) + this.getOtherPrivate(15);
        }

        @UnsupportedAppUsage
        public int getSummaryJavaHeap() {
            return this.dalvikPrivateDirty + this.getOtherPrivate(12);
        }

        @UnsupportedAppUsage
        public int getSummaryNativeHeap() {
            return this.nativePrivateDirty;
        }

        @UnsupportedAppUsage
        public int getSummaryPrivateOther() {
            return this.getTotalPrivateClean() + this.getTotalPrivateDirty() - this.getSummaryJavaHeap() - this.getSummaryNativeHeap() - this.getSummaryCode() - this.getSummaryStack() - this.getSummaryGraphics();
        }

        @UnsupportedAppUsage
        public int getSummaryStack() {
            return this.getOtherPrivateDirty(1);
        }

        @UnsupportedAppUsage
        public int getSummarySystem() {
            return this.getTotalPss() - this.getTotalPrivateClean() - this.getTotalPrivateDirty();
        }

        public int getSummaryTotalPss() {
            return this.getTotalPss();
        }

        public int getSummaryTotalSwap() {
            return this.getTotalSwappedOut();
        }

        public int getSummaryTotalSwapPss() {
            return this.getTotalSwappedOutPss();
        }

        public int getTotalPrivateClean() {
            return this.dalvikPrivateClean + this.nativePrivateClean + this.otherPrivateClean;
        }

        public int getTotalPrivateDirty() {
            return this.dalvikPrivateDirty + this.nativePrivateDirty + this.otherPrivateDirty;
        }

        public int getTotalPss() {
            return this.dalvikPss + this.nativePss + this.otherPss + this.getTotalSwappedOutPss();
        }

        public int getTotalRss() {
            return this.dalvikRss + this.nativeRss + this.otherRss;
        }

        public int getTotalSharedClean() {
            return this.dalvikSharedClean + this.nativeSharedClean + this.otherSharedClean;
        }

        public int getTotalSharedDirty() {
            return this.dalvikSharedDirty + this.nativeSharedDirty + this.otherSharedDirty;
        }

        public int getTotalSwappablePss() {
            return this.dalvikSwappablePss + this.nativeSwappablePss + this.otherSwappablePss;
        }

        public int getTotalSwappedOut() {
            return this.dalvikSwappedOut + this.nativeSwappedOut + this.otherSwappedOut;
        }

        public int getTotalSwappedOutPss() {
            return this.dalvikSwappedOutPss + this.nativeSwappedOutPss + this.otherSwappedOutPss;
        }

        @UnsupportedAppUsage
        public int getTotalUss() {
            return this.dalvikPrivateClean + this.dalvikPrivateDirty + this.nativePrivateClean + this.nativePrivateDirty + this.otherPrivateClean + this.otherPrivateDirty;
        }

        public boolean hasSwappedOutPss() {
            return this.hasSwappedOutPss;
        }

        public void readFromParcel(Parcel parcel) {
            this.dalvikPss = parcel.readInt();
            this.dalvikSwappablePss = parcel.readInt();
            this.dalvikRss = parcel.readInt();
            this.dalvikPrivateDirty = parcel.readInt();
            this.dalvikSharedDirty = parcel.readInt();
            this.dalvikPrivateClean = parcel.readInt();
            this.dalvikSharedClean = parcel.readInt();
            this.dalvikSwappedOut = parcel.readInt();
            this.dalvikSwappedOutPss = parcel.readInt();
            this.nativePss = parcel.readInt();
            this.nativeSwappablePss = parcel.readInt();
            this.nativeRss = parcel.readInt();
            this.nativePrivateDirty = parcel.readInt();
            this.nativeSharedDirty = parcel.readInt();
            this.nativePrivateClean = parcel.readInt();
            this.nativeSharedClean = parcel.readInt();
            this.nativeSwappedOut = parcel.readInt();
            this.nativeSwappedOutPss = parcel.readInt();
            this.otherPss = parcel.readInt();
            this.otherSwappablePss = parcel.readInt();
            this.otherRss = parcel.readInt();
            this.otherPrivateDirty = parcel.readInt();
            this.otherSharedDirty = parcel.readInt();
            this.otherPrivateClean = parcel.readInt();
            this.otherSharedClean = parcel.readInt();
            this.otherSwappedOut = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.hasSwappedOutPss = bl;
            this.otherSwappedOutPss = parcel.readInt();
            this.otherStats = parcel.createIntArray();
        }

        public void set(MemoryInfo arrn) {
            this.dalvikPss = arrn.dalvikPss;
            this.dalvikSwappablePss = arrn.dalvikSwappablePss;
            this.dalvikRss = arrn.dalvikRss;
            this.dalvikPrivateDirty = arrn.dalvikPrivateDirty;
            this.dalvikSharedDirty = arrn.dalvikSharedDirty;
            this.dalvikPrivateClean = arrn.dalvikPrivateClean;
            this.dalvikSharedClean = arrn.dalvikSharedClean;
            this.dalvikSwappedOut = arrn.dalvikSwappedOut;
            this.dalvikSwappedOutPss = arrn.dalvikSwappedOutPss;
            this.nativePss = arrn.nativePss;
            this.nativeSwappablePss = arrn.nativeSwappablePss;
            this.nativeRss = arrn.nativeRss;
            this.nativePrivateDirty = arrn.nativePrivateDirty;
            this.nativeSharedDirty = arrn.nativeSharedDirty;
            this.nativePrivateClean = arrn.nativePrivateClean;
            this.nativeSharedClean = arrn.nativeSharedClean;
            this.nativeSwappedOut = arrn.nativeSwappedOut;
            this.nativeSwappedOutPss = arrn.nativeSwappedOutPss;
            this.otherPss = arrn.otherPss;
            this.otherSwappablePss = arrn.otherSwappablePss;
            this.otherRss = arrn.otherRss;
            this.otherPrivateDirty = arrn.otherPrivateDirty;
            this.otherSharedDirty = arrn.otherSharedDirty;
            this.otherPrivateClean = arrn.otherPrivateClean;
            this.otherSharedClean = arrn.otherSharedClean;
            this.otherSwappedOut = arrn.otherSwappedOut;
            this.otherSwappedOutPss = arrn.otherSwappedOutPss;
            this.hasSwappedOutPss = arrn.hasSwappedOutPss;
            arrn = arrn.otherStats;
            int[] arrn2 = this.otherStats;
            System.arraycopy(arrn, 0, arrn2, 0, arrn2.length);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.dalvikPss);
            parcel.writeInt(this.dalvikSwappablePss);
            parcel.writeInt(this.dalvikRss);
            parcel.writeInt(this.dalvikPrivateDirty);
            parcel.writeInt(this.dalvikSharedDirty);
            parcel.writeInt(this.dalvikPrivateClean);
            parcel.writeInt(this.dalvikSharedClean);
            parcel.writeInt(this.dalvikSwappedOut);
            parcel.writeInt(this.dalvikSwappedOutPss);
            parcel.writeInt(this.nativePss);
            parcel.writeInt(this.nativeSwappablePss);
            parcel.writeInt(this.nativeRss);
            parcel.writeInt(this.nativePrivateDirty);
            parcel.writeInt(this.nativeSharedDirty);
            parcel.writeInt(this.nativePrivateClean);
            parcel.writeInt(this.nativeSharedClean);
            parcel.writeInt(this.nativeSwappedOut);
            parcel.writeInt(this.nativeSwappedOutPss);
            parcel.writeInt(this.otherPss);
            parcel.writeInt(this.otherSwappablePss);
            parcel.writeInt(this.otherRss);
            parcel.writeInt(this.otherPrivateDirty);
            parcel.writeInt(this.otherSharedDirty);
            parcel.writeInt(this.otherPrivateClean);
            parcel.writeInt(this.otherSharedClean);
            parcel.writeInt(this.otherSwappedOut);
            parcel.writeInt((int)this.hasSwappedOutPss);
            parcel.writeInt(this.otherSwappedOutPss);
            parcel.writeIntArray(this.otherStats);
        }

    }

}

