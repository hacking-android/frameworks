/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.os.SystemClock;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class PerfMeasurement {
    public static final int DEFAULT_MAX_QUERIES = 3;
    private static final long FAILED_TIMING = -2L;
    private static final long NO_DURATION_YET = -1L;
    private static final String TAG = "PerfMeasurement";
    private ArrayList<Long> mCollectedCpuDurations = new ArrayList();
    private ArrayList<Long> mCollectedGpuDurations = new ArrayList();
    private ArrayList<Long> mCollectedTimestamps = new ArrayList();
    private int mCompletedQueryCount = 0;
    private Queue<Long> mCpuDurationsQueue = new LinkedList<Long>();
    private final long mNativeContext;
    private long mStartTimeNs;
    private Queue<Long> mTimestampQueue = new LinkedList<Long>();

    public PerfMeasurement() {
        this.mNativeContext = PerfMeasurement.nativeCreateContext(3);
    }

    public PerfMeasurement(int n) {
        if (n >= 1) {
            this.mNativeContext = PerfMeasurement.nativeCreateContext(n);
            return;
        }
        throw new IllegalArgumentException("maxQueries is less than 1");
    }

    private long getNextGlDuration() {
        long l = PerfMeasurement.nativeGetNextGlDuration(this.mNativeContext);
        if (l > 0L) {
            ++this.mCompletedQueryCount;
        }
        return l;
    }

    public static boolean isGlTimingSupported() {
        return PerfMeasurement.nativeQuerySupport();
    }

    private static native long nativeCreateContext(int var0);

    private static native void nativeDeleteContext(long var0);

    protected static native long nativeGetNextGlDuration(long var0);

    private static native boolean nativeQuerySupport();

    protected static native void nativeStartGlTimer(long var0);

    protected static native void nativeStopGlTimer(long var0);

    public void addTimestamp(long l) {
        this.mTimestampQueue.add(l);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void dumpPerformanceData(String string2) {
        Appendable appendable = new FileWriter(string2);
        BufferedWriter bufferedWriter = new BufferedWriter((Writer)appendable);
        bufferedWriter.write("timestamp gpu_duration cpu_duration\n");
        for (int n = 0; n < this.mCollectedGpuDurations.size(); ++n) {
            bufferedWriter.write(String.format("%d %d %d\n", this.mCollectedTimestamps.get(n), this.mCollectedGpuDurations.get(n), this.mCollectedCpuDurations.get(n)));
        }
        this.mCollectedTimestamps.clear();
        this.mCollectedGpuDurations.clear();
        this.mCollectedCpuDurations.clear();
        bufferedWriter.close();
        return;
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    bufferedWriter.close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (IOException iOException) {
                        appendable = new StringBuilder();
                        ((StringBuilder)appendable).append("Error writing data dump to ");
                        ((StringBuilder)appendable).append(string2);
                        ((StringBuilder)appendable).append(":");
                        ((StringBuilder)appendable).append(iOException);
                        Log.e("PerfMeasurement", ((StringBuilder)appendable).toString());
                    }
                }
            }
        }
    }

    protected void finalize() {
        PerfMeasurement.nativeDeleteContext(this.mNativeContext);
    }

    public int getCompletedQueryCount() {
        return this.mCompletedQueryCount;
    }

    public void startTimer() {
        PerfMeasurement.nativeStartGlTimer(this.mNativeContext);
        this.mStartTimeNs = SystemClock.elapsedRealtimeNanos();
    }

    public void stopTimer() {
        long l = SystemClock.elapsedRealtimeNanos();
        this.mCpuDurationsQueue.add(l - this.mStartTimeNs);
        PerfMeasurement.nativeStopGlTimer(this.mNativeContext);
        long l2 = this.getNextGlDuration();
        if (l2 > 0L) {
            this.mCollectedGpuDurations.add(l2);
            ArrayList<Long> arrayList = this.mCollectedTimestamps;
            boolean bl = this.mTimestampQueue.isEmpty();
            long l3 = -1L;
            l = bl ? -1L : this.mTimestampQueue.poll();
            arrayList.add(l);
            arrayList = this.mCollectedCpuDurations;
            l = this.mCpuDurationsQueue.isEmpty() ? l3 : this.mCpuDurationsQueue.poll();
            arrayList.add(l);
        }
        if (l2 == -2L) {
            if (!this.mTimestampQueue.isEmpty()) {
                this.mTimestampQueue.poll();
            }
            if (!this.mCpuDurationsQueue.isEmpty()) {
                this.mCpuDurationsQueue.poll();
            }
        }
    }
}

