/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.os.-$
 *  com.android.internal.os.-$$Lambda
 *  com.android.internal.os.-$$Lambda$BinderCallsStats
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$-YP-7pwoNn8TN0iTmo5Q1r2lQz0
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$233x_Qux4c_AiqShYaWwvFplEXs
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$sqXweH5BoxhmZvI188ctqYiACRk
 *  com.android.internal.os.-$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc
 */
package com.android.internal.os;

import android.os.Binder;
import android.os.Process;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.-$;
import com.android.internal.os.AppIdToPackageMap;
import com.android.internal.os.BinderInternal;
import com.android.internal.os.CachedDeviceState;
import com.android.internal.os._$$Lambda$BinderCallsStats$233x_Qux4c_AiqShYaWwvFplEXs;
import com.android.internal.os._$$Lambda$BinderCallsStats$Vota0PqfoPWckjXH35wE48myGdk;
import com.android.internal.os._$$Lambda$BinderCallsStats$_YP_7pwoNn8TN0iTmo5Q1r2lQz0;
import com.android.internal.os._$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls;
import com.android.internal.os._$$Lambda$BinderCallsStats$sqXweH5BoxhmZvI188ctqYiACRk;
import com.android.internal.os._$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

public class BinderCallsStats
implements BinderInternal.Observer {
    private static final int CALL_SESSIONS_POOL_SIZE = 100;
    private static final String DEBUG_ENTRY_PREFIX = "__DEBUG_";
    public static final boolean DEFAULT_TRACK_DIRECT_CALLING_UID = true;
    public static final boolean DEFAULT_TRACK_SCREEN_INTERACTIVE = false;
    public static final boolean DETAILED_TRACKING_DEFAULT = true;
    public static final boolean ENABLED_DEFAULT = true;
    private static final String EXCEPTION_COUNT_OVERFLOW_NAME = "overflow";
    public static final int MAX_BINDER_CALL_STATS_COUNT_DEFAULT = 1500;
    private static final int MAX_EXCEPTION_COUNT_SIZE = 50;
    private static final Class<? extends Binder> OVERFLOW_BINDER = OverflowBinder.class;
    private static final int OVERFLOW_DIRECT_CALLING_UID = -1;
    private static final boolean OVERFLOW_SCREEN_INTERACTIVE = false;
    private static final int OVERFLOW_TRANSACTION_CODE = -1;
    public static final int PERIODIC_SAMPLING_INTERVAL_DEFAULT = 1000;
    private static final String TAG = "BinderCallsStats";
    private boolean mAddDebugEntries = false;
    private CachedDeviceState.TimeInStateStopwatch mBatteryStopwatch;
    private final Queue<BinderInternal.CallSession> mCallSessionsPool = new ConcurrentLinkedQueue<BinderInternal.CallSession>();
    private long mCallStatsCount = 0L;
    private boolean mDetailedTracking = true;
    private CachedDeviceState.Readonly mDeviceState;
    @GuardedBy(value={"mLock"})
    private final ArrayMap<String, Integer> mExceptionCounts = new ArrayMap();
    private final Object mLock = new Object();
    private int mMaxBinderCallStatsCount = 1500;
    private int mPeriodicSamplingInterval = 1000;
    private final Random mRandom;
    private long mStartCurrentTime = System.currentTimeMillis();
    private long mStartElapsedTime = SystemClock.elapsedRealtime();
    private boolean mTrackDirectCallingUid = true;
    private boolean mTrackScreenInteractive = false;
    @GuardedBy(value={"mLock"})
    private final SparseArray<UidEntry> mUidEntries = new SparseArray();

    public BinderCallsStats(Injector injector) {
        this.mRandom = injector.getRandomGenerator();
    }

    private static int compareByBinderClassAndCode(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        int n = exportedCallStat.className.compareTo(exportedCallStat2.className);
        if (n == 0) {
            n = Integer.compare(exportedCallStat.transactionCode, exportedCallStat2.transactionCode);
        }
        return n;
    }

    private static int compareByCpuDesc(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        return Long.compare(exportedCallStat2.cpuTimeMicros, exportedCallStat.cpuTimeMicros);
    }

    private ExportedCallStat createDebugEntry(String string2, long l) {
        int n = Process.myUid();
        ExportedCallStat exportedCallStat = new ExportedCallStat();
        exportedCallStat.className = "";
        exportedCallStat.workSourceUid = n;
        exportedCallStat.callingUid = n;
        exportedCallStat.recordedCallCount = 1L;
        exportedCallStat.callCount = 1L;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DEBUG_ENTRY_PREFIX);
        stringBuilder.append(string2);
        exportedCallStat.methodName = stringBuilder.toString();
        exportedCallStat.latencyMicros = l;
        return exportedCallStat;
    }

    private void dumpLocked(PrintWriter printWriter, AppIdToPackageMap iterator, boolean bl) {
        int n;
        Object object;
        Object object2 = iterator;
        long l = 0L;
        long l2 = 0L;
        long l3 = 0L;
        printWriter.print("Start time: ");
        printWriter.println(DateFormat.format((CharSequence)"yyyy-MM-dd HH:mm:ss", this.mStartCurrentTime));
        printWriter.print("On battery time (ms): ");
        Object object3 = this.mBatteryStopwatch;
        long l4 = object3 != null ? ((CachedDeviceState.TimeInStateStopwatch)object3).getMillis() : 0L;
        printWriter.println(l4);
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("Sampling interval period: ");
        ((StringBuilder)object3).append(this.mPeriodicSamplingInterval);
        printWriter.println(((StringBuilder)object3).toString());
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int n2 = this.mUidEntries.size();
        l4 = l3;
        for (n = 0; n < n2; ++n) {
            object3 = this.mUidEntries.valueAt(n);
            arrayList.add(object3);
            l4 += ((UidEntry)object3).cpuTimeMicros;
            l2 += ((UidEntry)object3).recordedCallCount;
            l += ((UidEntry)object3).callCount;
        }
        arrayList.sort(Comparator.comparingDouble(_$$Lambda$BinderCallsStats$iPOmTqbqUiHzgsAugINuZgf9tls.INSTANCE).reversed());
        String string2 = "";
        object3 = bl ? "" : "(top 90% by cpu time) ";
        Object object4 = new StringBuilder();
        List<Object> list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Per-UID raw data ");
        ((StringBuilder)((Object)list)).append((String)object3);
        ((StringBuilder)((Object)list)).append("(package/uid, worksource, call_desc, screen_interactive, cpu_time_micros, max_cpu_time_micros, latency_time_micros, max_latency_time_micros, exception_count, max_request_size_bytes, max_reply_size_bytes, recorded_call_count, call_count):");
        printWriter.println(((StringBuilder)((Object)list)).toString());
        Serializable serializable = this.getExportedCallStats();
        serializable.sort((Comparator<ExportedCallStat>)_$$Lambda$BinderCallsStats$233x_Qux4c_AiqShYaWwvFplEXs.INSTANCE);
        list = serializable.iterator();
        n = n2;
        while (list.hasNext()) {
            object = (ExportedCallStat)list.next();
            if (((ExportedCallStat)object).methodName.startsWith(DEBUG_ENTRY_PREFIX)) continue;
            ((StringBuilder)object4).setLength(0);
            ((StringBuilder)object4).append("    ");
            ((StringBuilder)object4).append(((AppIdToPackageMap)object2).mapUid(((ExportedCallStat)object).callingUid));
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((AppIdToPackageMap)object2).mapUid(((ExportedCallStat)object).workSourceUid));
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).className);
            ((StringBuilder)object4).append('#');
            ((StringBuilder)object4).append(((ExportedCallStat)object).methodName);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).screenInteractive);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).cpuTimeMicros);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).maxCpuTimeMicros);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).latencyMicros);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).maxLatencyMicros);
            ((StringBuilder)object4).append(',');
            l3 = this.mDetailedTracking ? ((ExportedCallStat)object).exceptionCount : 95L;
            ((StringBuilder)object4).append(l3);
            ((StringBuilder)object4).append(',');
            l3 = this.mDetailedTracking ? ((ExportedCallStat)object).maxRequestSizeBytes : 95L;
            ((StringBuilder)object4).append(l3);
            ((StringBuilder)object4).append(',');
            l3 = this.mDetailedTracking ? ((ExportedCallStat)object).maxReplySizeBytes : 95L;
            ((StringBuilder)object4).append(l3);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).recordedCallCount);
            ((StringBuilder)object4).append(',');
            ((StringBuilder)object4).append(((ExportedCallStat)object).callCount);
            printWriter.println(object4);
        }
        printWriter.println();
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("Per-UID Summary ");
        ((StringBuilder)((Object)list)).append((String)object3);
        ((StringBuilder)((Object)list)).append("(cpu_time, % of total cpu_time, recorded_call_count, call_count, package/uid):");
        printWriter.println(((StringBuilder)((Object)list)).toString());
        list = bl ? arrayList : BinderCallsStats.getHighestValues(arrayList, _$$Lambda$BinderCallsStats$xI0E0RpviGYsokEB7ojNx8LEbUc.INSTANCE, 0.9);
        object2 = list.iterator();
        serializable = object4;
        object4 = object3;
        object3 = string2;
        while (object2.hasNext()) {
            object = (UidEntry)object2.next();
            string2 = ((AppIdToPackageMap)((Object)iterator)).mapUid(((UidEntry)object).workSourceUid);
            printWriter.println(String.format("  %10d %3.0f%% %8d %8d %s", ((UidEntry)object).cpuTimeMicros, (double)((UidEntry)object).cpuTimeMicros * 100.0 / (double)l4, ((UidEntry)object).recordedCallCount, ((UidEntry)object).callCount, string2));
        }
        printWriter.println();
        printWriter.println(String.format("  Summary: total_cpu_time=%d, calls_count=%d, avg_call_cpu_time=%.0f", l4, l, (double)l4 / (double)l2));
        printWriter.println();
        printWriter.println("Exceptions thrown (exception_count, class_name):");
        iterator = new ArrayList();
        this.mExceptionCounts.entrySet().iterator().forEachRemaining(new _$$Lambda$BinderCallsStats$Vota0PqfoPWckjXH35wE48myGdk((List)((Object)iterator)));
        iterator.sort(_$$Lambda$BinderCallsStats$_YP_7pwoNn8TN0iTmo5Q1r2lQz0.INSTANCE);
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            list = (Pair)iterator.next();
            printWriter.println(String.format("  %6d %s", ((Pair)list).second, ((Pair)list).first));
        }
        if (this.mPeriodicSamplingInterval != 1) {
            printWriter.println((String)object3);
            printWriter.println("/!\\ Displayed data is sampled. See sampling interval at the top.");
        }
    }

    private Method getDefaultTransactionNameMethod(Class<? extends Binder> genericDeclaration) {
        try {
            genericDeclaration = genericDeclaration.getMethod("getDefaultTransactionName", Integer.TYPE);
            return genericDeclaration;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    @VisibleForTesting
    public static <T> List<T> getHighestValues(List<T> arrayList, ToDoubleFunction<T> toDoubleFunction, double d) {
        Object object = new ArrayList<T>(arrayList);
        object.sort(Comparator.comparingDouble(toDoubleFunction).reversed());
        double d2 = 0.0;
        arrayList = arrayList.iterator();
        while (arrayList.hasNext()) {
            d2 += toDoubleFunction.applyAsDouble(arrayList.next());
        }
        arrayList = new ArrayList();
        double d3 = 0.0;
        object = object.iterator();
        while (object.hasNext()) {
            Object e = object.next();
            if (d3 > d * d2) break;
            arrayList.add(e);
            d3 += toDoubleFunction.applyAsDouble(e);
        }
        return arrayList;
    }

    private UidEntry getUidEntry(int n) {
        UidEntry uidEntry;
        UidEntry uidEntry2 = uidEntry = this.mUidEntries.get(n);
        if (uidEntry == null) {
            uidEntry2 = new UidEntry(n);
            this.mUidEntries.put(n, uidEntry2);
        }
        return uidEntry2;
    }

    public static /* synthetic */ int lambda$233x_Qux4c_AiqShYaWwvFplEXs(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        return BinderCallsStats.compareByCpuDesc(exportedCallStat, exportedCallStat2);
    }

    static /* synthetic */ double lambda$dumpLocked$0(UidEntry uidEntry) {
        return uidEntry.cpuTimeMicros;
    }

    static /* synthetic */ double lambda$dumpLocked$1(UidEntry uidEntry) {
        return uidEntry.cpuTimeMicros;
    }

    static /* synthetic */ void lambda$dumpLocked$2(List list, Map.Entry entry) {
        list.add(Pair.create((String)entry.getKey(), (Integer)entry.getValue()));
    }

    static /* synthetic */ int lambda$dumpLocked$3(Pair pair, Pair pair2) {
        return Integer.compare((Integer)pair2.second, (Integer)pair.second);
    }

    public static /* synthetic */ int lambda$sqXweH5BoxhmZvI188ctqYiACRk(ExportedCallStat exportedCallStat, ExportedCallStat exportedCallStat2) {
        return BinderCallsStats.compareByBinderClassAndCode(exportedCallStat, exportedCallStat2);
    }

    private BinderInternal.CallSession obtainCallSession() {
        BinderInternal.CallSession callSession;
        block0 : {
            callSession = this.mCallSessionsPool.poll();
            if (callSession != null) break block0;
            callSession = new BinderInternal.CallSession();
        }
        return callSession;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void processCallEnded(BinderInternal.CallSession var1_1, int var2_8, int var3_9, int var4_10) {
        block18 : {
            block17 : {
                var5_12 = var1_1.cpuTimeStarted >= 0L ? 1 : 0;
                if (var5_12 != 0) {
                    var6_13 = this.getThreadTimeMicro() - var1_1.cpuTimeStarted;
                    var8_14 = this.getElapsedRealtimeMicro() - var1_1.timeStarted;
                } else {
                    var6_13 = 0L;
                    var8_14 = 0L;
                }
                var10_15 = this.mTrackScreenInteractive != false ? this.mDeviceState.isScreenInteractive() : false;
                var11_16 = this.mTrackDirectCallingUid != false ? this.getCallingUid() : -1;
                var12_17 = this.mLock;
                // MONITORENTER : var12_17
                if (this.mDeviceState == null || this.mDeviceState.isCharging()) ** GOTO lbl77
                var13_18 = this.getUidEntry(var4_11);
                var14_19 = var13_18.callCount;
                var16_20 = 1L;
                var13_18.callCount = var14_19 + 1L;
                if (var5_12 == 0) ** GOTO lbl68
                var13_18.cpuTimeMicros += var6_13;
                ++var13_18.recordedCallCount;
                var18_21 = var1_1.binderClass;
                var5_12 = var1_1.transactionCode;
                var14_19 = this.mCallStatsCount;
                var4_11 = this.mMaxBinderCallStatsCount;
                {
                    catch (Throwable var1_5) {
                        break block18;
                    }
                }
                var19_23 = var14_19 >= (long)var4_11;
                var20_24 = var12_17;
                var18_21 = var13_18.getOrCreate(var11_16, (Class<? extends Binder>)var18_21, var5_12, var10_15, var19_23);
                var14_19 = var18_21.callCount;
                var4_11 = var14_19 == 0L ? 1 : 0;
                if (var4_11 == 0) break block17;
                try {
                    ++this.mCallStatsCount;
                }
                catch (Throwable var1_2) {
                    var12_17 = var20_24;
                    break block18;
                }
            }
            ++var18_21.callCount;
            ++var18_21.recordedCallCount;
            var18_21.cpuTimeMicros += var6_13;
            var18_21.maxCpuTimeMicros = Math.max(var18_21.maxCpuTimeMicros, var6_13);
            var18_21.latencyMicros += var8_14;
            var18_21.maxLatencyMicros = Math.max(var18_21.maxLatencyMicros, var8_14);
            if (!this.mDetailedTracking) ** GOTO lbl73
            var6_13 = var18_21.exceptionCount;
            var8_14 = var1_1.exceptionThrown != false ? var16_20 : 0L;
            var18_21.exceptionCount = var6_13 + var8_14;
            var6_13 = var18_21.maxRequestSizeBytes;
            var8_14 = (long)var2_9;
            var18_21.maxRequestSizeBytes = Math.max(var6_13, var8_14);
            var8_14 = var18_21.maxReplySizeBytes;
            var6_13 = (long)var3_10;
            try {
                block19 : {
                    var18_21.maxReplySizeBytes = Math.max(var8_14, var6_13);
                    break block19;
                    catch (Throwable var1_3) {
                        var12_17 = var20_24;
                    }
                    break block18;
                    catch (Throwable var1_4) {
                        var12_17 = var20_24;
                    }
                    break block18;
lbl68: // 1 sources:
                    var20_24 = var18_22 = var12_17;
                    var1_1 = var13_18.get(var11_16, var1_1.binderClass, var1_1.transactionCode, var10_15);
                    if (var1_1 != null) {
                        var20_24 = var18_22;
                        ++var1_1.callCount;
                    }
                }
                var20_24 = var12_17;
                // MONITOREXIT : var12_17
                return;
lbl77: // 1 sources:
                var20_25 = var12_17;
                // MONITOREXIT : var12_17
                return;
                catch (Throwable var1_6) {
                    // empty catch block
                }
            }
            catch (Throwable var1_8) {
                var12_17 = var20_24;
            }
        }
        var20_24 = var12_17;
        // MONITOREXIT : var12_17
        throw var1_7;
    }

    private String resolveTransactionCode(Method object, int n) {
        if (object == null) {
            return null;
        }
        try {
            object = (String)((Method)object).invoke(null, n);
            return object;
        }
        catch (ClassCastException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void callEnded(BinderInternal.CallSession callSession, int n, int n2, int n3) {
        if (callSession == null) {
            return;
        }
        this.processCallEnded(callSession, n, n2, n3);
        if (this.mCallSessionsPool.size() < 100) {
            this.mCallSessionsPool.add(callSession);
        }
    }

    @Override
    public BinderInternal.CallSession callStarted(Binder binder, int n, int n2) {
        Object object = this.mDeviceState;
        if (object != null && !((CachedDeviceState.Readonly)object).isCharging()) {
            object = this.obtainCallSession();
            ((BinderInternal.CallSession)object).binderClass = binder.getClass();
            ((BinderInternal.CallSession)object).transactionCode = n;
            ((BinderInternal.CallSession)object).exceptionThrown = false;
            ((BinderInternal.CallSession)object).cpuTimeStarted = -1L;
            ((BinderInternal.CallSession)object).timeStarted = -1L;
            if (this.shouldRecordDetailedData()) {
                ((BinderInternal.CallSession)object).cpuTimeStarted = this.getThreadTimeMicro();
                ((BinderInternal.CallSession)object).timeStarted = this.getElapsedRealtimeMicro();
            }
            return object;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void callThrewException(BinderInternal.CallSession object, Exception object2) {
        if (object == null) {
            return;
        }
        int n = 1;
        ((BinderInternal.CallSession)object).exceptionThrown = true;
        try {
            object = object2.getClass().getName();
            object2 = this.mLock;
            // MONITORENTER : object2
        }
        catch (RuntimeException runtimeException) {
            Slog.wtf(TAG, "Unexpected exception while updating mExceptionCounts");
        }
        if (this.mExceptionCounts.size() >= 50) {
            object = EXCEPTION_COUNT_OVERFLOW_NAME;
        }
        Integer n2 = this.mExceptionCounts.get(object);
        ArrayMap<String, Integer> arrayMap = this.mExceptionCounts;
        if (n2 != null) {
            n = 1 + n2;
        }
        arrayMap.put((String)object, n);
        // MONITOREXIT : object2
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(PrintWriter printWriter, AppIdToPackageMap appIdToPackageMap, boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.dumpLocked(printWriter, appIdToPackageMap, bl);
            return;
        }
    }

    protected int getCallingUid() {
        return Binder.getCallingUid();
    }

    protected long getElapsedRealtimeMicro() {
        return SystemClock.elapsedRealtimeNanos() / 1000L;
    }

    @VisibleForTesting
    public ArrayMap<String, Integer> getExceptionCounts() {
        return this.mExceptionCounts;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList<ExportedCallStat> getExportedCallStats() {
        Object object3;
        int n;
        if (!this.mDetailedTracking) {
            return new ArrayList<ExportedCallStat>();
        }
        ArrayList<ExportedCallStat> arrayList = new ArrayList<ExportedCallStat>();
        Object object2 = this.mLock;
        synchronized (object2) {
            int n2 = this.mUidEntries.size();
            for (n = 0; n < n2; ++n) {
                UidEntry uidEntry = this.mUidEntries.valueAt(n);
                for (Object object3 : uidEntry.getCallStatsList()) {
                    ExportedCallStat exportedCallStat = new ExportedCallStat();
                    exportedCallStat.workSourceUid = uidEntry.workSourceUid;
                    exportedCallStat.callingUid = ((CallStat)object3).callingUid;
                    exportedCallStat.className = ((CallStat)object3).binderClass.getName();
                    exportedCallStat.binderClass = ((CallStat)object3).binderClass;
                    exportedCallStat.transactionCode = ((CallStat)object3).transactionCode;
                    exportedCallStat.screenInteractive = ((CallStat)object3).screenInteractive;
                    exportedCallStat.cpuTimeMicros = ((CallStat)object3).cpuTimeMicros;
                    exportedCallStat.maxCpuTimeMicros = ((CallStat)object3).maxCpuTimeMicros;
                    exportedCallStat.latencyMicros = ((CallStat)object3).latencyMicros;
                    exportedCallStat.maxLatencyMicros = ((CallStat)object3).maxLatencyMicros;
                    exportedCallStat.recordedCallCount = ((CallStat)object3).recordedCallCount;
                    exportedCallStat.callCount = ((CallStat)object3).callCount;
                    exportedCallStat.maxRequestSizeBytes = ((CallStat)object3).maxRequestSizeBytes;
                    exportedCallStat.maxReplySizeBytes = ((CallStat)object3).maxReplySizeBytes;
                    exportedCallStat.exceptionCount = ((CallStat)object3).exceptionCount;
                    arrayList.add(exportedCallStat);
                }
            }
        }
        object3 = null;
        object2 = null;
        arrayList.sort((Comparator<ExportedCallStat>)_$$Lambda$BinderCallsStats$sqXweH5BoxhmZvI188ctqYiACRk.INSTANCE);
        for (ExportedCallStat exportedCallStat : arrayList) {
            n = 0;
            if (false) {
                throw new NullPointerException();
            }
            if (true) {
                object3 = this.getDefaultTransactionNameMethod(exportedCallStat.binderClass);
            }
            if (false) {
                throw new NullPointerException();
            }
            n = 1;
            if ((true || n != 0) && (object2 = this.resolveTransactionCode((Method)object3, exportedCallStat.transactionCode)) == null) {
                object2 = String.valueOf(exportedCallStat.transactionCode);
            }
            Object object4 = object2;
            exportedCallStat.methodName = object2;
            object2 = object4;
        }
        if (this.mAddDebugEntries && this.mBatteryStopwatch != null) {
            arrayList.add(this.createDebugEntry("start_time_millis", this.mStartElapsedTime));
            arrayList.add(this.createDebugEntry("end_time_millis", SystemClock.elapsedRealtime()));
            arrayList.add(this.createDebugEntry("battery_time_millis", this.mBatteryStopwatch.getMillis()));
            arrayList.add(this.createDebugEntry("sampling_interval", this.mPeriodicSamplingInterval));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayMap<String, Integer> getExportedExceptionStats() {
        Object object = this.mLock;
        synchronized (object) {
            return new ArrayMap<String, Integer>(this.mExceptionCounts);
        }
    }

    protected long getThreadTimeMicro() {
        return SystemClock.currentThreadTimeMicro();
    }

    @VisibleForTesting
    public SparseArray<UidEntry> getUidEntries() {
        return this.mUidEntries;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() {
        Object object = this.mLock;
        synchronized (object) {
            this.mCallStatsCount = 0L;
            this.mUidEntries.clear();
            this.mExceptionCounts.clear();
            this.mStartCurrentTime = System.currentTimeMillis();
            this.mStartElapsedTime = SystemClock.elapsedRealtime();
            if (this.mBatteryStopwatch != null) {
                this.mBatteryStopwatch.reset();
            }
            return;
        }
    }

    public void setAddDebugEntries(boolean bl) {
        this.mAddDebugEntries = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDetailedTracking(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            if (bl != this.mDetailedTracking) {
                this.mDetailedTracking = bl;
                this.reset();
            }
            return;
        }
    }

    public void setDeviceState(CachedDeviceState.Readonly readonly) {
        CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch = this.mBatteryStopwatch;
        if (timeInStateStopwatch != null) {
            timeInStateStopwatch.close();
        }
        this.mDeviceState = readonly;
        this.mBatteryStopwatch = readonly.createTimeOnBatteryStopwatch();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaxBinderCallStats(int n) {
        if (n <= 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignored invalid max value (value must be positive): ");
            stringBuilder.append(n);
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (n != this.mMaxBinderCallStatsCount) {
                this.mMaxBinderCallStatsCount = n;
                this.reset();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSamplingInterval(int n) {
        if (n <= 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ignored invalid sampling interval (value must be positive): ");
            stringBuilder.append(n);
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (n != this.mPeriodicSamplingInterval) {
                this.mPeriodicSamplingInterval = n;
                this.reset();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTrackDirectCallerUid(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            if (bl != this.mTrackDirectCallingUid) {
                this.mTrackDirectCallingUid = bl;
                this.reset();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTrackScreenInteractive(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            if (bl != this.mTrackScreenInteractive) {
                this.mTrackScreenInteractive = bl;
                this.reset();
            }
            return;
        }
    }

    protected boolean shouldRecordDetailedData() {
        boolean bl = this.mRandom.nextInt() % this.mPeriodicSamplingInterval == 0;
        return bl;
    }

    @VisibleForTesting
    public static class CallStat {
        public final Class<? extends Binder> binderClass;
        public long callCount;
        public final int callingUid;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public long recordedCallCount;
        public final boolean screenInteractive;
        public final int transactionCode;

        CallStat(int n, Class<? extends Binder> class_, int n2, boolean bl) {
            this.callingUid = n;
            this.binderClass = class_;
            this.transactionCode = n2;
            this.screenInteractive = bl;
        }
    }

    public static class CallStatKey {
        public Class<? extends Binder> binderClass;
        public int callingUid;
        private boolean screenInteractive;
        public int transactionCode;

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            object = (CallStatKey)object;
            if (this.callingUid != ((CallStatKey)object).callingUid || this.transactionCode != ((CallStatKey)object).transactionCode || this.screenInteractive != ((CallStatKey)object).screenInteractive || !this.binderClass.equals(((CallStatKey)object).binderClass)) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            int n = this.binderClass.hashCode();
            int n2 = this.transactionCode;
            int n3 = this.callingUid;
            int n4 = this.screenInteractive ? 1231 : 1237;
            return ((n * 31 + n2) * 31 + n3) * 31 + n4;
        }
    }

    public static class ExportedCallStat {
        Class<? extends Binder> binderClass;
        public long callCount;
        public int callingUid;
        public String className;
        public long cpuTimeMicros;
        public long exceptionCount;
        public long latencyMicros;
        public long maxCpuTimeMicros;
        public long maxLatencyMicros;
        public long maxReplySizeBytes;
        public long maxRequestSizeBytes;
        public String methodName;
        public long recordedCallCount;
        public boolean screenInteractive;
        int transactionCode;
        public int workSourceUid;
    }

    public static class Injector {
        public Random getRandomGenerator() {
            return new Random();
        }
    }

    private static class OverflowBinder
    extends Binder {
        private OverflowBinder() {
        }
    }

    @VisibleForTesting
    public static class UidEntry {
        public long callCount;
        public long cpuTimeMicros;
        private Map<CallStatKey, CallStat> mCallStats = new ArrayMap<CallStatKey, CallStat>();
        private CallStatKey mTempKey = new CallStatKey();
        public long recordedCallCount;
        public int workSourceUid;

        UidEntry(int n) {
            this.workSourceUid = n;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            object = (UidEntry)object;
            if (this.workSourceUid != ((UidEntry)object).workSourceUid) {
                bl = false;
            }
            return bl;
        }

        CallStat get(int n, Class<? extends Binder> class_, int n2, boolean bl) {
            CallStatKey callStatKey = this.mTempKey;
            callStatKey.callingUid = n;
            callStatKey.binderClass = class_;
            callStatKey.transactionCode = n2;
            callStatKey.screenInteractive = bl;
            return this.mCallStats.get(this.mTempKey);
        }

        public Collection<CallStat> getCallStatsList() {
            return this.mCallStats.values();
        }

        CallStat getOrCreate(int n, Class<? extends Binder> object, int n2, boolean bl, boolean bl2) {
            Object object2 = this.get(n, (Class<? extends Binder>)object, n2, bl);
            CallStat callStat = object2;
            if (object2 == null) {
                if (bl2) {
                    object = this.get(-1, OVERFLOW_BINDER, -1, false);
                    if (object != null) {
                        return object;
                    }
                    n = -1;
                    object = OVERFLOW_BINDER;
                    n2 = -1;
                    bl = false;
                }
                callStat = new CallStat(n, (Class<? extends Binder>)object, n2, bl);
                object2 = new CallStatKey();
                ((CallStatKey)object2).callingUid = n;
                ((CallStatKey)object2).binderClass = object;
                ((CallStatKey)object2).transactionCode = n2;
                ((CallStatKey)object2).screenInteractive = bl;
                this.mCallStats.put((CallStatKey)object2, callStat);
            }
            return callStat;
        }

        public int hashCode() {
            return this.workSourceUid;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UidEntry{cpuTimeMicros=");
            stringBuilder.append(this.cpuTimeMicros);
            stringBuilder.append(", callCount=");
            stringBuilder.append(this.callCount);
            stringBuilder.append(", mCallStats=");
            stringBuilder.append(this.mCallStats);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

