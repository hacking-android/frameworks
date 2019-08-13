/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.util.-$
 *  com.android.internal.util.-$$Lambda
 *  com.android.internal.util.-$$Lambda$ProviderAccessStats
 *  com.android.internal.util.-$$Lambda$ProviderAccessStats$9AhC6lKURctNKuYjVd-wu7jn6_c
 */
package com.android.internal.util;

import android.os.SystemClock;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import com.android.internal.util.-$;
import com.android.internal.util._$$Lambda$ProviderAccessStats$9AhC6lKURctNKuYjVd_wu7jn6_c;
import java.io.PrintWriter;

public class ProviderAccessStats {
    private final SparseBooleanArray mAllCallingUids = new SparseBooleanArray();
    private final SparseLongArray mBatchStats = new SparseLongArray(0);
    private final SparseLongArray mDeleteInBatchStats = new SparseLongArray(0);
    private final SparseLongArray mDeleteStats = new SparseLongArray(0);
    private final SparseLongArray mInsertInBatchStats = new SparseLongArray(0);
    private final SparseLongArray mInsertStats = new SparseLongArray(0);
    private final Object mLock = new Object();
    private final SparseLongArray mOperationDurationMillis = new SparseLongArray(16);
    private final SparseLongArray mQueryStats = new SparseLongArray(16);
    private final long mStartUptime = SystemClock.uptimeMillis();
    private final ThreadLocal<PerThreadData> mThreadLocal = ThreadLocal.withInitial(_$$Lambda$ProviderAccessStats$9AhC6lKURctNKuYjVd_wu7jn6_c.INSTANCE);
    private final SparseLongArray mUpdateInBatchStats = new SparseLongArray(0);
    private final SparseLongArray mUpdateStats = new SparseLongArray(0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void incrementStats(int n, SparseLongArray object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            ((SparseLongArray)object).put(n, ((SparseLongArray)object).get(n) + 1L);
            this.mAllCallingUids.put(n, true);
        }
        object = this.mThreadLocal.get();
        ++((PerThreadData)object).nestCount;
        if (((PerThreadData)object).nestCount == 1) {
            ((PerThreadData)object).startUptimeMillis = SystemClock.uptimeMillis();
        }
    }

    private void incrementStats(int n, boolean bl, SparseLongArray sparseLongArray, SparseLongArray sparseLongArray2) {
        if (bl) {
            sparseLongArray = sparseLongArray2;
        }
        this.incrementStats(n, sparseLongArray);
    }

    static /* synthetic */ PerThreadData lambda$new$0() {
        return new PerThreadData();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(PrintWriter printWriter, String string2) {
        Object object = this.mLock;
        synchronized (object) {
            printWriter.print("  Process uptime: ");
            printWriter.print((SystemClock.uptimeMillis() - this.mStartUptime) / 60000L);
            printWriter.println(" minutes");
            printWriter.println();
            printWriter.print(string2);
            printWriter.println("Client activities:");
            printWriter.print(string2);
            printWriter.println("  UID        Query  Insert Update Delete   Batch Insert Update Delete          Sec");
            int n = 0;
            do {
                if (n >= this.mAllCallingUids.size()) {
                    printWriter.println();
                    return;
                }
                int n2 = this.mAllCallingUids.keyAt(n);
                printWriter.print(string2);
                printWriter.println(String.format("  %-9d %6d  %6d %6d %6d  %6d %6d %6d %6d %12.3f", n2, this.mQueryStats.get(n2), this.mInsertStats.get(n2), this.mUpdateStats.get(n2), this.mDeleteStats.get(n2), this.mBatchStats.get(n2), this.mInsertInBatchStats.get(n2), this.mUpdateInBatchStats.get(n2), this.mDeleteInBatchStats.get(n2), (double)this.mOperationDurationMillis.get(n2) / 1000.0));
                ++n;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void finishOperation(int n) {
        PerThreadData perThreadData = this.mThreadLocal.get();
        --perThreadData.nestCount;
        if (perThreadData.nestCount != 0) return;
        long l = Math.max(1L, SystemClock.uptimeMillis() - perThreadData.startUptimeMillis);
        Object object = this.mLock;
        synchronized (object) {
            this.mOperationDurationMillis.put(n, this.mOperationDurationMillis.get(n) + l);
            return;
        }
    }

    public final void incrementBatchStats(int n) {
        this.incrementStats(n, this.mBatchStats);
    }

    public final void incrementDeleteStats(int n, boolean bl) {
        this.incrementStats(n, bl, this.mDeleteStats, this.mDeleteInBatchStats);
    }

    public final void incrementInsertStats(int n, boolean bl) {
        this.incrementStats(n, bl, this.mInsertStats, this.mInsertInBatchStats);
    }

    public final void incrementQueryStats(int n) {
        this.incrementStats(n, this.mQueryStats);
    }

    public final void incrementUpdateStats(int n, boolean bl) {
        this.incrementStats(n, bl, this.mUpdateStats, this.mUpdateInBatchStats);
    }

    private static class PerThreadData {
        public int nestCount;
        public long startUptimeMillis;

        private PerThreadData() {
        }
    }

}

