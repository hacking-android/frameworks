/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.os.SystemClock;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.IndentingPrintWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class StatLogger {
    private static final String TAG = "StatLogger";
    private final int SIZE;
    @GuardedBy(value={"mLock"})
    private final int[] mCallsPerSecond;
    @GuardedBy(value={"mLock"})
    private final int[] mCountStats;
    @GuardedBy(value={"mLock"})
    private final long[] mDurationPerSecond;
    @GuardedBy(value={"mLock"})
    private final long[] mDurationStats;
    private final String[] mLabels;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private final int[] mMaxCallsPerSecond;
    @GuardedBy(value={"mLock"})
    private final long[] mMaxDurationPerSecond;
    @GuardedBy(value={"mLock"})
    private final long[] mMaxDurationStats;
    @GuardedBy(value={"mLock"})
    private long mNextTickTime = SystemClock.elapsedRealtime() + 1000L;

    public StatLogger(String[] arrstring) {
        int n = this.SIZE = arrstring.length;
        this.mCountStats = new int[n];
        this.mDurationStats = new long[n];
        this.mCallsPerSecond = new int[n];
        this.mMaxCallsPerSecond = new int[n];
        this.mDurationPerSecond = new long[n];
        this.mMaxDurationPerSecond = new long[n];
        this.mMaxDurationStats = new long[n];
        this.mLabels = arrstring;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(IndentingPrintWriter indentingPrintWriter) {
        Object object = this.mLock;
        synchronized (object) {
            indentingPrintWriter.println("Stats:");
            indentingPrintWriter.increaseIndent();
            int n = 0;
            do {
                if (n >= this.SIZE) {
                    indentingPrintWriter.decreaseIndent();
                    return;
                }
                int n2 = this.mCountStats[n];
                double d = (double)this.mDurationStats[n] / 1000.0;
                String string2 = this.mLabels[n];
                double d2 = n2 == 0 ? 0.0 : d / (double)n2;
                indentingPrintWriter.println(String.format("%s: count=%d, total=%.1fms, avg=%.3fms, max calls/s=%d max dur/s=%.1fms max time=%.1fms", string2, n2, d, d2, this.mMaxCallsPerSecond[n], (double)this.mMaxDurationPerSecond[n] / 1000.0, (double)this.mMaxDurationStats[n] / 1000.0));
                ++n;
            } while (true);
        }
    }

    public void dump(PrintWriter printWriter, String string2) {
        this.dump(new IndentingPrintWriter(printWriter, "  ").setIndent(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dumpProto(ProtoOutputStream protoOutputStream, long l) {
        Object object = this.mLock;
        synchronized (object) {
            long l2 = protoOutputStream.start(l);
            int n = 0;
            do {
                if (n >= this.mLabels.length) {
                    protoOutputStream.end(l2);
                    return;
                }
                l = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, n);
                protoOutputStream.write(1138166333442L, this.mLabels[n]);
                protoOutputStream.write(1120986464259L, this.mCountStats[n]);
                protoOutputStream.write(1112396529668L, this.mDurationStats[n]);
                protoOutputStream.end(l);
                ++n;
            } while (true);
        }
    }

    public long getTime() {
        return SystemClock.elapsedRealtimeNanos() / 1000L;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long logDurationStat(int n, long l) {
        Object object = this.mLock;
        synchronized (object) {
            long l2 = this.getTime() - l;
            if (n >= 0 && n < this.SIZE) {
                int[] arrn = this.mCountStats;
                arrn[n] = arrn[n] + 1;
                arrn = this.mDurationStats;
                arrn[n] = arrn[n] + l2;
                if (this.mMaxDurationStats[n] < l2) {
                    this.mMaxDurationStats[n] = l2;
                }
                if ((l = SystemClock.elapsedRealtime()) > this.mNextTickTime) {
                    if (this.mMaxCallsPerSecond[n] < this.mCallsPerSecond[n]) {
                        this.mMaxCallsPerSecond[n] = this.mCallsPerSecond[n];
                    }
                    if (this.mMaxDurationPerSecond[n] < this.mDurationPerSecond[n]) {
                        this.mMaxDurationPerSecond[n] = this.mDurationPerSecond[n];
                    }
                    this.mCallsPerSecond[n] = 0;
                    this.mDurationPerSecond[n] = 0L;
                    this.mNextTickTime = 1000L + l;
                }
                arrn = this.mCallsPerSecond;
                arrn[n] = arrn[n] + 1;
                arrn = this.mDurationPerSecond;
                arrn[n] = arrn[n] + l2;
                return l2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid event ID: ");
            stringBuilder.append(n);
            Slog.wtf(TAG, stringBuilder.toString());
            return l2;
        }
    }
}

