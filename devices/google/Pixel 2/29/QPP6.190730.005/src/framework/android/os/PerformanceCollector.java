/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Debug;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class PerformanceCollector {
    public static final String METRIC_KEY_CPU_TIME = "cpu_time";
    public static final String METRIC_KEY_EXECUTION_TIME = "execution_time";
    public static final String METRIC_KEY_GC_INVOCATION_COUNT = "gc_invocation_count";
    public static final String METRIC_KEY_GLOBAL_ALLOC_COUNT = "global_alloc_count";
    public static final String METRIC_KEY_GLOBAL_ALLOC_SIZE = "global_alloc_size";
    public static final String METRIC_KEY_GLOBAL_FREED_COUNT = "global_freed_count";
    public static final String METRIC_KEY_GLOBAL_FREED_SIZE = "global_freed_size";
    public static final String METRIC_KEY_ITERATIONS = "iterations";
    public static final String METRIC_KEY_JAVA_ALLOCATED = "java_allocated";
    public static final String METRIC_KEY_JAVA_FREE = "java_free";
    public static final String METRIC_KEY_JAVA_PRIVATE_DIRTY = "java_private_dirty";
    public static final String METRIC_KEY_JAVA_PSS = "java_pss";
    public static final String METRIC_KEY_JAVA_SHARED_DIRTY = "java_shared_dirty";
    public static final String METRIC_KEY_JAVA_SIZE = "java_size";
    public static final String METRIC_KEY_LABEL = "label";
    public static final String METRIC_KEY_NATIVE_ALLOCATED = "native_allocated";
    public static final String METRIC_KEY_NATIVE_FREE = "native_free";
    public static final String METRIC_KEY_NATIVE_PRIVATE_DIRTY = "native_private_dirty";
    public static final String METRIC_KEY_NATIVE_PSS = "native_pss";
    public static final String METRIC_KEY_NATIVE_SHARED_DIRTY = "native_shared_dirty";
    public static final String METRIC_KEY_NATIVE_SIZE = "native_size";
    public static final String METRIC_KEY_OTHER_PRIVATE_DIRTY = "other_private_dirty";
    public static final String METRIC_KEY_OTHER_PSS = "other_pss";
    public static final String METRIC_KEY_OTHER_SHARED_DIRTY = "other_shared_dirty";
    public static final String METRIC_KEY_PRE_RECEIVED_TRANSACTIONS = "pre_received_transactions";
    public static final String METRIC_KEY_PRE_SENT_TRANSACTIONS = "pre_sent_transactions";
    public static final String METRIC_KEY_RECEIVED_TRANSACTIONS = "received_transactions";
    public static final String METRIC_KEY_SENT_TRANSACTIONS = "sent_transactions";
    private long mCpuTime;
    private long mExecTime;
    private Bundle mPerfMeasurement;
    private Bundle mPerfSnapshot;
    private PerformanceResultsWriter mPerfWriter;
    private long mSnapshotCpuTime;
    private long mSnapshotExecTime;

    @UnsupportedAppUsage
    public PerformanceCollector() {
    }

    public PerformanceCollector(PerformanceResultsWriter performanceResultsWriter) {
        this.setPerformanceResultsWriter(performanceResultsWriter);
    }

    private void endPerformanceSnapshot() {
        this.mSnapshotCpuTime = Process.getElapsedCpuTime() - this.mSnapshotCpuTime;
        this.mSnapshotExecTime = SystemClock.uptimeMillis() - this.mSnapshotExecTime;
        PerformanceCollector.stopAllocCounting();
        long l = Debug.getNativeHeapSize() / 1024L;
        long l2 = Debug.getNativeHeapAllocatedSize() / 1024L;
        long l3 = Debug.getNativeHeapFreeSize() / 1024L;
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        Object object2 = Runtime.getRuntime();
        long l4 = ((Runtime)object2).totalMemory() / 1024L;
        long l5 = ((Runtime)object2).freeMemory() / 1024L;
        long l6 = l4 - l5;
        Object object3 = PerformanceCollector.getBinderCounts();
        for (String object4 : ((BaseBundle)object3).keySet()) {
            this.mPerfSnapshot.putLong(object4, ((BaseBundle)object3).getLong(object4));
        }
        Bundle bundle = PerformanceCollector.getAllocCounts();
        Iterator<String> iterator = bundle.keySet().iterator();
        object2 = object3;
        while (iterator.hasNext()) {
            object3 = iterator.next();
            this.mPerfSnapshot.putLong((String)object3, bundle.getLong((String)object3));
        }
        this.mPerfSnapshot.putLong(METRIC_KEY_EXECUTION_TIME, this.mSnapshotExecTime);
        this.mPerfSnapshot.putLong(METRIC_KEY_CPU_TIME, this.mSnapshotCpuTime);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_SIZE, l);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_ALLOCATED, l2);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_FREE, l3);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_PSS, memoryInfo.nativePss);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_PRIVATE_DIRTY, memoryInfo.nativePrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_SHARED_DIRTY, memoryInfo.nativeSharedDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_SIZE, l4);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_ALLOCATED, l6);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_FREE, l5);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_PSS, memoryInfo.dalvikPss);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_PRIVATE_DIRTY, memoryInfo.dalvikPrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_SHARED_DIRTY, memoryInfo.dalvikSharedDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_PSS, memoryInfo.otherPss);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_PRIVATE_DIRTY, memoryInfo.otherPrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_SHARED_DIRTY, memoryInfo.otherSharedDirty);
    }

    private static Bundle getAllocCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong(METRIC_KEY_GLOBAL_ALLOC_COUNT, Debug.getGlobalAllocCount());
        bundle.putLong(METRIC_KEY_GLOBAL_ALLOC_SIZE, Debug.getGlobalAllocSize());
        bundle.putLong(METRIC_KEY_GLOBAL_FREED_COUNT, Debug.getGlobalFreedCount());
        bundle.putLong(METRIC_KEY_GLOBAL_FREED_SIZE, Debug.getGlobalFreedSize());
        bundle.putLong(METRIC_KEY_GC_INVOCATION_COUNT, Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    private static Bundle getBinderCounts() {
        Bundle bundle = new Bundle();
        bundle.putLong(METRIC_KEY_SENT_TRANSACTIONS, Debug.getBinderSentTransactions());
        bundle.putLong(METRIC_KEY_RECEIVED_TRANSACTIONS, Debug.getBinderReceivedTransactions());
        return bundle;
    }

    private static void startAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.resetAllCounts();
        Debug.startAllocCounting();
    }

    private void startPerformanceSnapshot() {
        this.mPerfSnapshot = new Bundle();
        Bundle bundle = PerformanceCollector.getBinderCounts();
        for (String string2 : bundle.keySet()) {
            Bundle bundle2 = this.mPerfSnapshot;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("pre_");
            stringBuilder.append(string2);
            bundle2.putLong(stringBuilder.toString(), bundle.getLong(string2));
        }
        PerformanceCollector.startAllocCounting();
        this.mSnapshotExecTime = SystemClock.uptimeMillis();
        this.mSnapshotCpuTime = Process.getElapsedCpuTime();
    }

    private static void stopAllocCounting() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Debug.stopAllocCounting();
    }

    public Bundle addIteration(String string2) {
        this.mCpuTime = Process.getElapsedCpuTime() - this.mCpuTime;
        this.mExecTime = SystemClock.uptimeMillis() - this.mExecTime;
        Bundle bundle = new Bundle();
        bundle.putString(METRIC_KEY_LABEL, string2);
        bundle.putLong(METRIC_KEY_EXECUTION_TIME, this.mExecTime);
        bundle.putLong(METRIC_KEY_CPU_TIME, this.mCpuTime);
        this.mPerfMeasurement.getParcelableArrayList(METRIC_KEY_ITERATIONS).add(bundle);
        this.mExecTime = SystemClock.uptimeMillis();
        this.mCpuTime = Process.getElapsedCpuTime();
        return bundle;
    }

    public void addMeasurement(String string2, float f) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeMeasurement(string2, f);
        }
    }

    public void addMeasurement(String string2, long l) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeMeasurement(string2, l);
        }
    }

    public void addMeasurement(String string2, String string3) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeMeasurement(string2, string3);
        }
    }

    @UnsupportedAppUsage
    public void beginSnapshot(String string2) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeBeginSnapshot(string2);
        }
        this.startPerformanceSnapshot();
    }

    @UnsupportedAppUsage
    public Bundle endSnapshot() {
        this.endPerformanceSnapshot();
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeEndSnapshot(this.mPerfSnapshot);
        }
        return this.mPerfSnapshot;
    }

    public void setPerformanceResultsWriter(PerformanceResultsWriter performanceResultsWriter) {
        this.mPerfWriter = performanceResultsWriter;
    }

    @UnsupportedAppUsage
    public void startTiming(String string2) {
        PerformanceResultsWriter performanceResultsWriter = this.mPerfWriter;
        if (performanceResultsWriter != null) {
            performanceResultsWriter.writeStartTiming(string2);
        }
        this.mPerfMeasurement = new Bundle();
        this.mPerfMeasurement.putParcelableArrayList(METRIC_KEY_ITERATIONS, new ArrayList());
        this.mExecTime = SystemClock.uptimeMillis();
        this.mCpuTime = Process.getElapsedCpuTime();
    }

    @UnsupportedAppUsage
    public Bundle stopTiming(String object) {
        this.addIteration((String)object);
        object = this.mPerfWriter;
        if (object != null) {
            object.writeStopTiming(this.mPerfMeasurement);
        }
        return this.mPerfMeasurement;
    }

    public static interface PerformanceResultsWriter {
        public void writeBeginSnapshot(String var1);

        public void writeEndSnapshot(Bundle var1);

        public void writeMeasurement(String var1, float var2);

        public void writeMeasurement(String var1, long var2);

        public void writeMeasurement(String var1, String var2);

        public void writeStartTiming(String var1);

        public void writeStopTiming(Bundle var1);
    }

}

