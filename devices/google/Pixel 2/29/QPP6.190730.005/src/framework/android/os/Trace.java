/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$Trace
 *  android.os.-$$Lambda$Trace$2zLZ-Lc2kAXsVjw_nLYeNhqmGq0
 *  dalvik.annotation.optimization.FastNative
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.-$;
import android.os.SystemProperties;
import android.os._$$Lambda$Trace$2zLZ_Lc2kAXsVjw_nLYeNhqmGq0;
import dalvik.annotation.optimization.FastNative;

public final class Trace {
    private static final int MAX_SECTION_NAME_LEN = 127;
    private static final String TAG = "Trace";
    public static final long TRACE_TAG_ACTIVITY_MANAGER = 64L;
    public static final long TRACE_TAG_ADB = 0x400000L;
    public static final long TRACE_TAG_AIDL = 0x1000000L;
    public static final long TRACE_TAG_ALWAYS = 1L;
    @UnsupportedAppUsage
    public static final long TRACE_TAG_APP = 4096L;
    public static final long TRACE_TAG_AUDIO = 256L;
    public static final long TRACE_TAG_BIONIC = 65536L;
    public static final long TRACE_TAG_CAMERA = 1024L;
    public static final long TRACE_TAG_DALVIK = 16384L;
    public static final long TRACE_TAG_DATABASE = 0x100000L;
    public static final long TRACE_TAG_GRAPHICS = 2L;
    public static final long TRACE_TAG_HAL = 2048L;
    public static final long TRACE_TAG_INPUT = 4L;
    public static final long TRACE_TAG_NETWORK = 0x200000L;
    public static final long TRACE_TAG_NEVER = 0L;
    public static final long TRACE_TAG_NNAPI = 0x2000000L;
    private static final long TRACE_TAG_NOT_READY = Long.MIN_VALUE;
    public static final long TRACE_TAG_PACKAGE_MANAGER = 262144L;
    public static final long TRACE_TAG_POWER = 131072L;
    public static final long TRACE_TAG_RESOURCES = 8192L;
    public static final long TRACE_TAG_RRO = 0x4000000L;
    public static final long TRACE_TAG_RS = 32768L;
    public static final long TRACE_TAG_SYNC_MANAGER = 128L;
    public static final long TRACE_TAG_SYSTEM_SERVER = 524288L;
    public static final long TRACE_TAG_VIBRATOR = 0x800000L;
    public static final long TRACE_TAG_VIDEO = 512L;
    @UnsupportedAppUsage
    public static final long TRACE_TAG_VIEW = 8L;
    public static final long TRACE_TAG_WEBVIEW = 16L;
    public static final long TRACE_TAG_WINDOW_MANAGER = 32L;
    @UnsupportedAppUsage
    private static volatile long sEnabledTags = Long.MIN_VALUE;
    private static int sZygoteDebugFlags = 0;

    static {
        SystemProperties.addChangeCallback((Runnable)_$$Lambda$Trace$2zLZ_Lc2kAXsVjw_nLYeNhqmGq0.INSTANCE);
    }

    private Trace() {
    }

    @UnsupportedAppUsage
    public static void asyncTraceBegin(long l, String string2, int n) {
        if (Trace.isTagEnabled(l)) {
            Trace.nativeAsyncTraceBegin(l, string2, n);
        }
    }

    @UnsupportedAppUsage
    public static void asyncTraceEnd(long l, String string2, int n) {
        if (Trace.isTagEnabled(l)) {
            Trace.nativeAsyncTraceEnd(l, string2, n);
        }
    }

    public static void beginAsyncSection(String string2, int n) {
        Trace.asyncTraceBegin(4096L, string2, n);
    }

    public static void beginSection(String string2) {
        if (Trace.isTagEnabled(4096L)) {
            if (string2.length() <= 127) {
                Trace.nativeTraceBegin(4096L, string2);
            } else {
                throw new IllegalArgumentException("sectionName is too long");
            }
        }
    }

    private static long cacheEnabledTags() {
        long l;
        sEnabledTags = l = Trace.nativeGetEnabledTags();
        return l;
    }

    public static void endAsyncSection(String string2, int n) {
        Trace.asyncTraceEnd(4096L, string2, n);
    }

    public static void endSection() {
        if (Trace.isTagEnabled(4096L)) {
            Trace.nativeTraceEnd(4096L);
        }
    }

    public static boolean isEnabled() {
        return Trace.isTagEnabled(4096L);
    }

    @UnsupportedAppUsage
    public static boolean isTagEnabled(long l) {
        long l2;
        long l3 = l2 = sEnabledTags;
        if (l2 == Long.MIN_VALUE) {
            l3 = Trace.cacheEnabledTags();
        }
        boolean bl = (l3 & l) != 0L;
        return bl;
    }

    static /* synthetic */ void lambda$static$0() {
        Trace.cacheEnabledTags();
        if ((sZygoteDebugFlags & 256) != 0) {
            Trace.traceCounter(1L, "java_debuggable", 1);
        }
    }

    @FastNative
    private static native void nativeAsyncTraceBegin(long var0, String var2, int var3);

    @FastNative
    private static native void nativeAsyncTraceEnd(long var0, String var2, int var3);

    @UnsupportedAppUsage
    private static native long nativeGetEnabledTags();

    private static native void nativeSetAppTracingAllowed(boolean var0);

    private static native void nativeSetTracingEnabled(boolean var0);

    @FastNative
    private static native void nativeTraceBegin(long var0, String var2);

    @FastNative
    private static native void nativeTraceCounter(long var0, String var2, long var3);

    @FastNative
    private static native void nativeTraceEnd(long var0);

    @UnsupportedAppUsage
    public static void setAppTracingAllowed(boolean bl) {
        Trace.nativeSetAppTracingAllowed(bl);
        Trace.cacheEnabledTags();
    }

    public static void setCounter(String string2, long l) {
        if (Trace.isTagEnabled(4096L)) {
            Trace.nativeTraceCounter(4096L, string2, l);
        }
    }

    public static void setTracingEnabled(boolean bl, int n) {
        Trace.nativeSetTracingEnabled(bl);
        sZygoteDebugFlags = n;
        Trace.cacheEnabledTags();
    }

    @UnsupportedAppUsage
    public static void traceBegin(long l, String string2) {
        if (Trace.isTagEnabled(l)) {
            Trace.nativeTraceBegin(l, string2);
        }
    }

    @UnsupportedAppUsage
    public static void traceCounter(long l, String string2, int n) {
        if (Trace.isTagEnabled(l)) {
            Trace.nativeTraceCounter(l, string2, n);
        }
    }

    @UnsupportedAppUsage
    public static void traceEnd(long l) {
        if (Trace.isTagEnabled(l)) {
            Trace.nativeTraceEnd(l);
        }
    }
}

