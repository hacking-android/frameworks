/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.util.Map;
import java.util.Properties;

public class VM {
    private static final int JVMTI_THREAD_STATE_ALIVE = 1;
    private static final int JVMTI_THREAD_STATE_BLOCKED_ON_MONITOR_ENTER = 1024;
    private static final int JVMTI_THREAD_STATE_RUNNABLE = 4;
    private static final int JVMTI_THREAD_STATE_TERMINATED = 2;
    private static final int JVMTI_THREAD_STATE_WAITING_INDEFINITELY = 16;
    private static final int JVMTI_THREAD_STATE_WAITING_WITH_TIMEOUT = 32;
    @Deprecated
    public static final int STATE_GREEN = 1;
    @Deprecated
    public static final int STATE_RED = 3;
    @Deprecated
    public static final int STATE_YELLOW = 2;
    private static boolean allowArraySyntax;
    private static volatile boolean booted;
    private static boolean defaultAllowArraySyntax;
    private static long directMemory;
    private static volatile int finalRefCount;
    private static final Object lock;
    private static boolean pageAlignDirectMemory;
    private static volatile int peakFinalRefCount;
    private static final Properties savedProps;
    private static boolean suspended;

    static {
        suspended = false;
        booted = false;
        lock = new Object();
        directMemory = 0x4000000L;
        allowArraySyntax = defaultAllowArraySyntax = false;
        savedProps = new Properties();
        finalRefCount = 0;
        peakFinalRefCount = 0;
    }

    public static void addFinalRefCount(int n) {
        if ((finalRefCount += n) > peakFinalRefCount) {
            peakFinalRefCount = finalRefCount;
        }
    }

    public static boolean allowArraySyntax() {
        return allowArraySyntax;
    }

    public static boolean allowThreadSuspension(ThreadGroup threadGroup, boolean bl) {
        return threadGroup.allowThreadSuspension(bl);
    }

    @Deprecated
    public static void asChange(int n, int n2) {
    }

    @Deprecated
    public static void asChange_otherthread(int n, int n2) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void awaitBooted() throws InterruptedException {
        Object object = lock;
        synchronized (object) {
            while (!booted) {
                lock.wait();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void booted() {
        Object object = lock;
        synchronized (object) {
            booted = true;
            lock.notifyAll();
            return;
        }
    }

    public static int getFinalRefCount() {
        return finalRefCount;
    }

    public static int getPeakFinalRefCount() {
        return peakFinalRefCount;
    }

    public static String getSavedProperty(String string) {
        return savedProps.getProperty(string);
    }

    @Deprecated
    public static final int getState() {
        return 1;
    }

    public static void initializeOSEnvironment() {
    }

    public static boolean isBooted() {
        return booted;
    }

    public static boolean isDirectMemoryPageAligned() {
        return pageAlignDirectMemory;
    }

    public static long maxDirectMemory() {
        return directMemory;
    }

    public static void saveAndRemoveProperties(Properties properties) {
        if (!booted) {
            savedProps.putAll(properties);
            String string = (String)properties.remove("sun.nio.MaxDirectMemorySize");
            if (string != null) {
                if (string.equals("-1")) {
                    directMemory = Runtime.getRuntime().maxMemory();
                } else {
                    long l = Long.parseLong(string);
                    if (l > -1L) {
                        directMemory = l;
                    }
                }
            }
            if ("true".equals((String)properties.remove("sun.nio.PageAlignDirectMemory"))) {
                pageAlignDirectMemory = true;
            }
            boolean bl = (string = properties.getProperty("sun.lang.ClassLoader.allowArraySyntax")) == null ? defaultAllowArraySyntax : Boolean.parseBoolean(string);
            allowArraySyntax = bl;
            properties.remove("java.lang.Integer.IntegerCache.high");
            properties.remove("sun.zip.disableMemoryMapping");
            properties.remove("sun.java.launcher.diag");
            properties.remove("sun.cds.enableSharedLookupCache");
            return;
        }
        throw new IllegalStateException("System initialization has completed");
    }

    @Deprecated
    public static boolean suspendThreads() {
        suspended = true;
        return true;
    }

    @Deprecated
    public static boolean threadsSuspended() {
        return suspended;
    }

    public static Thread.State toThreadState(int n) {
        if ((n & 4) != 0) {
            return Thread.State.RUNNABLE;
        }
        if ((n & 1024) != 0) {
            return Thread.State.BLOCKED;
        }
        if ((n & 16) != 0) {
            return Thread.State.WAITING;
        }
        if ((n & 32) != 0) {
            return Thread.State.TIMED_WAITING;
        }
        if ((n & 2) != 0) {
            return Thread.State.TERMINATED;
        }
        if ((n & 1) == 0) {
            return Thread.State.NEW;
        }
        return Thread.State.RUNNABLE;
    }

    @Deprecated
    public static void unsuspendSomeThreads() {
    }

    @Deprecated
    public static void unsuspendThreads() {
        suspended = false;
    }
}

