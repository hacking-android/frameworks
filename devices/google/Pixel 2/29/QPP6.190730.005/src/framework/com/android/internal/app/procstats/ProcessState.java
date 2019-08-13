/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.app.ProcessMap;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.DurationsTable;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.PssTable;
import com.android.internal.app.procstats.SparseMappingTable;
import java.io.PrintWriter;
import java.util.Comparator;

public final class ProcessState {
    public static final Comparator<ProcessState> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_PARCEL = false;
    static final int[] PROCESS_STATE_TO_STATE;
    private static final String TAG = "ProcessStats";
    private boolean mActive;
    private long mAvgCachedKillPss;
    private ProcessState mCommonProcess;
    private int mCurCombinedState = -1;
    private boolean mDead;
    private final DurationsTable mDurations;
    private int mLastPssState = -1;
    private long mLastPssTime;
    private long mMaxCachedKillPss;
    private long mMinCachedKillPss;
    private boolean mMultiPackage;
    private final String mName;
    private int mNumActiveServices;
    private int mNumCachedKill;
    private int mNumExcessiveCpu;
    private int mNumStartedServices;
    private final String mPackage;
    private final PssTable mPssTable;
    private long mStartTime;
    private final ProcessStats mStats;
    private long mTmpTotalTime;
    private long mTotalRunningDuration;
    private final long[] mTotalRunningPss = new long[10];
    private long mTotalRunningStartTime;
    private final int mUid;
    private final long mVersion;
    public ProcessState tmpFoundSubProc;
    public int tmpNumInUse;

    static {
        PROCESS_STATE_TO_STATE = new int[]{0, 0, 1, 2, 2, 2, 2, 2, 3, 3, 4, 5, 7, 1, 8, 9, 10, 11, 12, 11, 13};
        COMPARATOR = new Comparator<ProcessState>(){

            @Override
            public int compare(ProcessState processState, ProcessState processState2) {
                if (processState.mTmpTotalTime < processState2.mTmpTotalTime) {
                    return -1;
                }
                return processState.mTmpTotalTime > processState2.mTmpTotalTime;
            }
        };
    }

    public ProcessState(ProcessState processState, String string2, int n, long l, String string3, long l2) {
        this.mStats = processState.mStats;
        this.mName = string3;
        this.mCommonProcess = processState;
        this.mPackage = string2;
        this.mUid = n;
        this.mVersion = l;
        this.mCurCombinedState = processState.mCurCombinedState;
        this.mStartTime = l2;
        if (this.mCurCombinedState != -1) {
            this.mTotalRunningStartTime = l2;
        }
        this.mDurations = new DurationsTable(processState.mStats.mTableData);
        this.mPssTable = new PssTable(processState.mStats.mTableData);
    }

    public ProcessState(ProcessStats processStats, String string2, int n, long l, String string3) {
        this.mStats = processStats;
        this.mName = string3;
        this.mCommonProcess = this;
        this.mPackage = string2;
        this.mUid = n;
        this.mVersion = l;
        this.mDurations = new DurationsTable(processStats.mTableData);
        this.mPssTable = new PssTable(processStats.mTableData);
    }

    private void addCachedKill(int n, long l, long l2, long l3) {
        if (this.mNumCachedKill <= 0) {
            this.mNumCachedKill = n;
            this.mMinCachedKillPss = l;
            this.mAvgCachedKillPss = l2;
            this.mMaxCachedKillPss = l3;
        } else {
            if (l < this.mMinCachedKillPss) {
                this.mMinCachedKillPss = l;
            }
            if (l3 > this.mMaxCachedKillPss) {
                this.mMaxCachedKillPss = l3;
            }
            double d = this.mAvgCachedKillPss;
            int n2 = this.mNumCachedKill;
            this.mAvgCachedKillPss = (long)((d * (double)n2 + (double)l2) / (double)(n2 + n));
            this.mNumCachedKill = n2 + n;
        }
    }

    private void dumpProcessSummaryDetails(PrintWriter printWriter, String string2, String string3, int[] object, int[] arrn, int[] arrn2, long l, long l2, boolean bl) {
        object = new ProcessStats.ProcessDataCollection((int[])object, arrn, arrn2);
        this.computeProcessData((ProcessStats.ProcessDataCollection)object, l);
        if ((double)((ProcessStats.ProcessDataCollection)object).totalTime / (double)l2 * 100.0 >= 0.005 || ((ProcessStats.ProcessDataCollection)object).numPss != 0L) {
            if (string2 != null) {
                printWriter.print(string2);
            }
            if (string3 != null) {
                printWriter.print("  ");
                printWriter.print(string3);
                printWriter.print(": ");
            }
            ((ProcessStats.ProcessDataCollection)object).print(printWriter, l2, bl);
            if (string2 != null) {
                printWriter.println();
            }
        }
    }

    public static void dumpPssSamples(PrintWriter printWriter, long[] arrl, int n) {
        DebugUtils.printSizeValue(printWriter, arrl[n + 1] * 1024L);
        printWriter.print("-");
        DebugUtils.printSizeValue(printWriter, arrl[n + 2] * 1024L);
        printWriter.print("-");
        DebugUtils.printSizeValue(printWriter, arrl[n + 3] * 1024L);
        printWriter.print("/");
        DebugUtils.printSizeValue(printWriter, arrl[n + 4] * 1024L);
        printWriter.print("-");
        DebugUtils.printSizeValue(printWriter, arrl[n + 5] * 1024L);
        printWriter.print("-");
        DebugUtils.printSizeValue(printWriter, arrl[n + 6] * 1024L);
        printWriter.print("/");
        DebugUtils.printSizeValue(printWriter, arrl[n + 7] * 1024L);
        printWriter.print("-");
        DebugUtils.printSizeValue(printWriter, arrl[n + 8] * 1024L);
        printWriter.print("-");
        DebugUtils.printSizeValue(printWriter, arrl[n + 9] * 1024L);
        printWriter.print(" over ");
        printWriter.print(arrl[n + 0]);
    }

    public static void dumpPssSamplesCheckin(PrintWriter printWriter, long[] arrl, int n) {
        printWriter.print(arrl[n + 0]);
        printWriter.print(':');
        printWriter.print(arrl[n + 1]);
        printWriter.print(':');
        printWriter.print(arrl[n + 2]);
        printWriter.print(':');
        printWriter.print(arrl[n + 3]);
        printWriter.print(':');
        printWriter.print(arrl[n + 4]);
        printWriter.print(':');
        printWriter.print(arrl[n + 5]);
        printWriter.print(':');
        printWriter.print(arrl[n + 6]);
        printWriter.print(':');
        printWriter.print(arrl[n + 7]);
        printWriter.print(':');
        printWriter.print(arrl[n + 8]);
        printWriter.print(':');
        printWriter.print(arrl[n + 9]);
    }

    private void ensureNotDead() {
        if (!this.mDead) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ProcessState dead: name=");
        stringBuilder.append(this.mName);
        stringBuilder.append(" pkg=");
        stringBuilder.append(this.mPackage);
        stringBuilder.append(" uid=");
        stringBuilder.append(this.mUid);
        stringBuilder.append(" common.name=");
        stringBuilder.append(this.mCommonProcess.mName);
        Slog.w(TAG, stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    private ProcessState pullFixedProc(ArrayMap<String, ProcessStats.ProcessStateHolder> object, int n) {
        Object object2;
        ProcessStats.ProcessStateHolder processStateHolder = (ProcessStats.ProcessStateHolder)((ArrayMap)object).valueAt(n);
        Object object3 = object2 = processStateHolder.state;
        if (this.mDead) {
            object3 = object2;
            if (((ProcessState)object2).mCommonProcess != object2) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Pulling dead proc: name=");
                ((StringBuilder)object3).append(this.mName);
                ((StringBuilder)object3).append(" pkg=");
                ((StringBuilder)object3).append(this.mPackage);
                ((StringBuilder)object3).append(" uid=");
                ((StringBuilder)object3).append(this.mUid);
                ((StringBuilder)object3).append(" common.name=");
                ((StringBuilder)object3).append(this.mCommonProcess.mName);
                Log.wtf(TAG, ((StringBuilder)object3).toString());
                object3 = this.mStats.getProcessStateLocked(((ProcessState)object2).mPackage, ((ProcessState)object2).mUid, ((ProcessState)object2).mVersion, ((ProcessState)object2).mName);
            }
        }
        object2 = object3;
        if (!((ProcessState)object3).mMultiPackage) return object2;
        object2 = this.mStats.mPackages.get((String)((ArrayMap)object).keyAt(n), ((ProcessState)object3).mUid);
        if (object2 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No existing package ");
            ((StringBuilder)object2).append((String)((ArrayMap)object).keyAt(n));
            ((StringBuilder)object2).append("/");
            ((StringBuilder)object2).append(((ProcessState)object3).mUid);
            ((StringBuilder)object2).append(" for multi-proc ");
            ((StringBuilder)object2).append(((ProcessState)object3).mName);
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }
        ProcessStats.PackageState packageState = (ProcessStats.PackageState)((LongSparseArray)object2).get(((ProcessState)object3).mVersion);
        if (packageState == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No existing package ");
            ((StringBuilder)object2).append((String)((ArrayMap)object).keyAt(n));
            ((StringBuilder)object2).append("/");
            ((StringBuilder)object2).append(((ProcessState)object3).mUid);
            ((StringBuilder)object2).append(" for multi-proc ");
            ((StringBuilder)object2).append(((ProcessState)object3).mName);
            ((StringBuilder)object2).append(" version ");
            ((StringBuilder)object2).append(((ProcessState)object3).mVersion);
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }
        object = ((ProcessState)object3).mName;
        object2 = packageState.mProcesses.get(((ProcessState)object3).mName);
        if (object2 != null) {
            processStateHolder.state = object2;
            return object2;
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("Didn't create per-package process ");
        ((StringBuilder)object3).append((String)object);
        ((StringBuilder)object3).append(" in pkg ");
        ((StringBuilder)object3).append(packageState.mPackageName);
        ((StringBuilder)object3).append("/");
        ((StringBuilder)object3).append(packageState.mUid);
        throw new IllegalStateException(((StringBuilder)object3).toString());
    }

    public void add(ProcessState processState) {
        this.mDurations.addDurations(processState.mDurations);
        this.mPssTable.mergeStats(processState.mPssTable);
        this.mNumExcessiveCpu += processState.mNumExcessiveCpu;
        int n = processState.mNumCachedKill;
        if (n > 0) {
            this.addCachedKill(n, processState.mMinCachedKillPss, processState.mAvgCachedKillPss, processState.mMaxCachedKillPss);
        }
    }

    public void addPss(long l, long l2, long l3, boolean bl, int n, long l4, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        Object object;
        this.ensureNotDead();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            object = this.mStats;
                            ++((ProcessStats)object).mExternalSlowPssCount;
                            object = this.mStats;
                            ((ProcessStats)object).mExternalSlowPssTime += l4;
                        }
                    } else {
                        object = this.mStats;
                        ++((ProcessStats)object).mExternalPssCount;
                        object = this.mStats;
                        ((ProcessStats)object).mExternalPssTime += l4;
                    }
                } else {
                    object = this.mStats;
                    ++((ProcessStats)object).mInternalAllPollPssCount;
                    object = this.mStats;
                    ((ProcessStats)object).mInternalAllPollPssTime += l4;
                }
            } else {
                object = this.mStats;
                ++((ProcessStats)object).mInternalAllMemPssCount;
                object = this.mStats;
                ((ProcessStats)object).mInternalAllMemPssTime += l4;
            }
        } else {
            object = this.mStats;
            ++((ProcessStats)object).mInternalSinglePssCount;
            object = this.mStats;
            ((ProcessStats)object).mInternalSinglePssTime += l4;
        }
        if (!bl && this.mLastPssState == this.mCurCombinedState && SystemClock.uptimeMillis() < this.mLastPssTime + 30000L) {
            return;
        }
        this.mLastPssState = this.mCurCombinedState;
        this.mLastPssTime = SystemClock.uptimeMillis();
        n = this.mCurCombinedState;
        if (n != -1) {
            this.mCommonProcess.mPssTable.mergeStats(n, 1, l, l, l, l2, l2, l2, l3, l3, l3);
            PssTable.mergeStats(this.mCommonProcess.mTotalRunningPss, 0, 1, l, l, l, l2, l2, l2, l3, l3, l3);
            if (!this.mCommonProcess.mMultiPackage) {
                return;
            }
            if (arrayMap != null) {
                for (n = arrayMap.size() - 1; n >= 0; --n) {
                    object = this.pullFixedProc(arrayMap, n);
                    ((ProcessState)object).mPssTable.mergeStats(this.mCurCombinedState, 1, l, l, l, l2, l2, l2, l3, l3, l3);
                    PssTable.mergeStats(((ProcessState)object).mTotalRunningPss, 0, 1, l, l, l, l2, l2, l2, l3, l3, l3);
                }
            }
        }
    }

    public void aggregatePss(ProcessStats.TotalMemoryUseCollection totalMemoryUseCollection, long l) {
        int n;
        long l2;
        long l3;
        int n2;
        int n3;
        Object object = this;
        PssAggr pssAggr = new PssAggr();
        PssAggr pssAggr2 = new PssAggr();
        double[] arrd = new PssAggr();
        boolean bl = false;
        for (n2 = 0; n2 < ((ProcessState)object).mDurations.getKeyCount(); ++n2) {
            n = SparseMappingTable.getIdFromKey(((ProcessState)object).mDurations.getKeyAt(n2));
            n3 = n % 14;
            l2 = ((ProcessState)object).getPssSampleCount(n);
            if (l2 <= 0L) continue;
            l3 = ((ProcessState)object).getPssAverage(n);
            bl = true;
            if (n3 <= 2) {
                pssAggr.add(l3, l2);
                continue;
            }
            if (n3 <= 7) {
                pssAggr2.add(l3, l2);
                continue;
            }
            arrd.add(l3, l2);
        }
        if (!bl) {
            return;
        }
        n3 = 0;
        n = 0;
        int n4 = 0;
        n2 = n3;
        if (pssAggr.samples < 3L) {
            n2 = n3;
            if (pssAggr2.samples > 0L) {
                n2 = 1;
                pssAggr.add(pssAggr2.pss, pssAggr2.samples);
            }
        }
        n3 = n;
        if (pssAggr.samples < 3L) {
            n3 = n;
            if (arrd.samples > 0L) {
                n3 = 1;
                pssAggr.add(arrd.pss, arrd.samples);
            }
        }
        n = n4;
        if (pssAggr2.samples < 3L) {
            n = n4;
            if (arrd.samples > 0L) {
                n = 1;
                pssAggr2.add(arrd.pss, arrd.samples);
            }
        }
        if (pssAggr2.samples < 3L && n2 == 0 && pssAggr.samples > 0L) {
            pssAggr2.add(pssAggr.pss, pssAggr.samples);
        }
        if (arrd.samples < 3L && n == 0 && pssAggr2.samples > 0L) {
            arrd.add(pssAggr2.pss, pssAggr2.samples);
        }
        if (arrd.samples < 3L && n3 == 0 && pssAggr.samples > 0L) {
            arrd.add(pssAggr.pss, pssAggr.samples);
        }
        n4 = 0;
        object = arrd;
        do {
            arrd = totalMemoryUseCollection;
            int[] arrn = this;
            if (n4 >= arrn.mDurations.getKeyCount()) break;
            int n5 = arrn.mDurations.getKeyAt(n4);
            byte by = SparseMappingTable.getIdFromKey(n5);
            long l4 = l2 = arrn.mDurations.getValue(n5);
            if (arrn.mCurCombinedState == by) {
                l4 = l2 + (l - arrn.mStartTime);
            }
            n5 = by % 14;
            long[] arrl = arrd.processStateTime;
            arrl[n5] = arrl[n5] + l4;
            l3 = arrn.getPssSampleCount(by);
            if (l3 > 0L) {
                l2 = arrn.getPssAverage(by);
            } else if (n5 <= 2) {
                l3 = pssAggr.samples;
                l2 = pssAggr.pss;
            } else if (n5 <= 7) {
                l3 = pssAggr2.samples;
                l2 = pssAggr2.pss;
            } else {
                l3 = ((PssAggr)object).samples;
                l2 = ((PssAggr)object).pss;
            }
            double d = ((double)arrd.processStatePss[n5] * (double)arrd.processStateSamples[n5] + (double)l2 * (double)l3) / (double)((long)arrd.processStateSamples[n5] + l3);
            arrd.processStatePss[n5] = (long)d;
            arrn = arrd.processStateSamples;
            arrn[n5] = (int)((long)arrn[n5] + l3);
            arrd = arrd.processStateWeight;
            arrd[n5] = arrd[n5] + (double)l2 * (double)l4;
            ++n4;
        } while (true);
    }

    public ProcessState clone(long l) {
        ProcessState processState = new ProcessState(this, this.mPackage, this.mUid, this.mVersion, this.mName, l);
        processState.mDurations.addDurations(this.mDurations);
        processState.mPssTable.copyFrom(this.mPssTable, 10);
        System.arraycopy(this.mTotalRunningPss, 0, processState.mTotalRunningPss, 0, 10);
        processState.mTotalRunningDuration = this.getTotalRunningDuration(l);
        processState.mNumExcessiveCpu = this.mNumExcessiveCpu;
        processState.mNumCachedKill = this.mNumCachedKill;
        processState.mMinCachedKillPss = this.mMinCachedKillPss;
        processState.mAvgCachedKillPss = this.mAvgCachedKillPss;
        processState.mMaxCachedKillPss = this.mMaxCachedKillPss;
        processState.mActive = this.mActive;
        processState.mNumActiveServices = this.mNumActiveServices;
        processState.mNumStartedServices = this.mNumStartedServices;
        return processState;
    }

    public void commitStateTime(long l) {
        int n = this.mCurCombinedState;
        if (n != -1) {
            long l2 = l - this.mStartTime;
            if (l2 > 0L) {
                this.mDurations.addDuration(n, l2);
            }
            this.mTotalRunningDuration += l - this.mTotalRunningStartTime;
            this.mTotalRunningStartTime = l;
        }
        this.mStartTime = l;
    }

    public void computeProcessData(ProcessStats.ProcessDataCollection processDataCollection, long l) {
        long l2 = 0L;
        processDataCollection.totalTime = 0L;
        processDataCollection.maxRss = 0L;
        processDataCollection.avgRss = 0L;
        processDataCollection.minRss = 0L;
        processDataCollection.maxUss = 0L;
        processDataCollection.avgUss = 0L;
        processDataCollection.minUss = 0L;
        processDataCollection.maxPss = 0L;
        processDataCollection.avgPss = 0L;
        processDataCollection.minPss = 0L;
        processDataCollection.numPss = 0L;
        for (int i = 0; i < processDataCollection.screenStates.length; ++i) {
            for (int j = 0; j < processDataCollection.memStates.length; ++j) {
                for (int k = 0; k < processDataCollection.procStates.length; ++k) {
                    int n = (processDataCollection.screenStates[i] + processDataCollection.memStates[j]) * 14 + processDataCollection.procStates[k];
                    processDataCollection.totalTime += this.getDuration(n, l);
                    long l3 = this.getPssSampleCount(n);
                    if (l3 <= l2) continue;
                    long l4 = this.getPssMinimum(n);
                    long l5 = this.getPssAverage(n);
                    long l6 = this.getPssMaximum(n);
                    long l7 = this.getPssUssMinimum(n);
                    long l8 = this.getPssUssAverage(n);
                    long l9 = this.getPssUssMaximum(n);
                    long l10 = this.getPssRssMinimum(n);
                    long l11 = this.getPssRssAverage(n);
                    long l12 = this.getPssRssMaximum(n);
                    long l13 = processDataCollection.numPss;
                    l2 = 0L;
                    if (l13 == 0L) {
                        processDataCollection.minPss = l4;
                        processDataCollection.avgPss = l5;
                        processDataCollection.maxPss = l6;
                        processDataCollection.minUss = l7;
                        processDataCollection.avgUss = l8;
                        processDataCollection.maxUss = l9;
                        processDataCollection.minRss = l10;
                        processDataCollection.avgRss = l11;
                        processDataCollection.maxRss = l12;
                    } else {
                        if (l4 < processDataCollection.minPss) {
                            processDataCollection.minPss = l4;
                        }
                        double d = processDataCollection.avgPss;
                        double d2 = processDataCollection.numPss;
                        double d3 = l5;
                        l5 = l3;
                        processDataCollection.avgPss = (long)((d * d2 + d3 * (double)l5) / (double)(processDataCollection.numPss + l5));
                        if (l6 > processDataCollection.maxPss) {
                            processDataCollection.maxPss = l6;
                        }
                        if (l7 < processDataCollection.minUss) {
                            processDataCollection.minUss = l7;
                        }
                        processDataCollection.avgUss = (long)(((double)processDataCollection.avgUss * (double)processDataCollection.numPss + (double)l8 * (double)l5) / (double)(processDataCollection.numPss + l5));
                        if (l9 > processDataCollection.maxUss) {
                            processDataCollection.maxUss = l9;
                        }
                        if (l10 < processDataCollection.minRss) {
                            processDataCollection.minRss = l10;
                        }
                        processDataCollection.avgRss = (long)(((double)processDataCollection.avgRss * (double)processDataCollection.numPss + (double)l11 * (double)l5) / (double)(processDataCollection.numPss + l5));
                        if (l12 > processDataCollection.maxRss) {
                            processDataCollection.maxRss = l12;
                        }
                    }
                    processDataCollection.numPss += l3;
                }
            }
        }
    }

    public long computeProcessTimeLocked(int[] arrn, int[] arrn2, int[] arrn3, long l) {
        long l2 = 0L;
        for (int i = 0; i < arrn.length; ++i) {
            for (int j = 0; j < arrn2.length; ++j) {
                for (int k = 0; k < arrn3.length; ++k) {
                    l2 += this.getDuration((arrn[i] + arrn2[j]) * 14 + arrn3[k], l);
                }
            }
        }
        this.mTmpTotalTime = l2;
        return l2;
    }

    public void decActiveServices(String string2) {
        Object object = this.mCommonProcess;
        if (object != this) {
            ((ProcessState)object).decActiveServices(string2);
        }
        --this.mNumActiveServices;
        if (this.mNumActiveServices < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Proc active services underrun: pkg=");
            ((StringBuilder)object).append(this.mPackage);
            ((StringBuilder)object).append(" uid=");
            ((StringBuilder)object).append(this.mUid);
            ((StringBuilder)object).append(" proc=");
            ((StringBuilder)object).append(this.mName);
            ((StringBuilder)object).append(" service=");
            ((StringBuilder)object).append(string2);
            Slog.wtfStack(TAG, ((StringBuilder)object).toString());
            this.mNumActiveServices = 0;
        }
    }

    public void decStartedServices(int n, long l, String charSequence) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.decStartedServices(n, l, (String)charSequence);
        }
        --this.mNumStartedServices;
        if (this.mNumStartedServices == 0 && this.mCurCombinedState % 14 == 6) {
            this.setCombinedState(-1, l);
        } else if (this.mNumStartedServices < 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Proc started services underrun: pkg=");
            ((StringBuilder)charSequence).append(this.mPackage);
            ((StringBuilder)charSequence).append(" uid=");
            ((StringBuilder)charSequence).append(this.mUid);
            ((StringBuilder)charSequence).append(" name=");
            ((StringBuilder)charSequence).append(this.mName);
            Slog.wtfStack(TAG, ((StringBuilder)charSequence).toString());
            this.mNumStartedServices = 0;
        }
    }

    public void dumpAllPssCheckin(PrintWriter printWriter) {
        int n = this.mPssTable.getKeyCount();
        for (int i = 0; i < n; ++i) {
            int n2 = this.mPssTable.getKeyAt(i);
            byte by = SparseMappingTable.getIdFromKey(n2);
            printWriter.print(',');
            DumpUtils.printProcStateTag(printWriter, by);
            printWriter.print(':');
            ProcessState.dumpPssSamplesCheckin(printWriter, this.mPssTable.getArrayForKey(n2), SparseMappingTable.getIndexFromKey(n2));
        }
    }

    public void dumpAllStateCheckin(PrintWriter printWriter, long l) {
        int n;
        boolean bl = false;
        for (n = 0; n < this.mDurations.getKeyCount(); ++n) {
            long l2;
            int n2 = this.mDurations.getKeyAt(n);
            byte by = SparseMappingTable.getIdFromKey(n2);
            long l3 = l2 = this.mDurations.getValue(n2);
            if (this.mCurCombinedState == by) {
                bl = true;
                l3 = l2 + (l - this.mStartTime);
            }
            DumpUtils.printProcStateTagAndValue(printWriter, by, l3);
        }
        if (!bl && (n = this.mCurCombinedState) != -1) {
            DumpUtils.printProcStateTagAndValue(printWriter, n, l - this.mStartTime);
        }
    }

    public void dumpCsv(PrintWriter printWriter, boolean bl, int[] arrn, boolean bl2, int[] arrn2, boolean bl3, int[] arrn3, long l) {
        int n = bl ? arrn.length : 1;
        int n2 = bl2 ? arrn2.length : 1;
        int n3 = bl3 ? arrn3.length : 1;
        int n4 = n2;
        for (int i = 0; i < n; ++i) {
            int n5 = n;
            block1 : for (n2 = 0; n2 < n4; ++n2) {
                int n6 = 0;
                do {
                    int[] arrn4 = arrn3;
                    int[] arrn5 = arrn2;
                    int[] arrn6 = arrn;
                    if (n6 >= n3) continue block1;
                    int n7 = bl ? arrn6[i] : 0;
                    int n8 = bl2 ? arrn5[n2] : 0;
                    int n9 = bl3 ? arrn4[n6] : 0;
                    int n10 = bl ? 1 : arrn6.length;
                    n = bl2 ? 1 : arrn5.length;
                    int n11 = bl3 ? 1 : arrn4.length;
                    long l2 = 0L;
                    for (int j = 0; j < n10; ++j) {
                        for (int k = 0; k < n; ++k) {
                            for (int i2 = 0; i2 < n11; ++i2) {
                                int n12 = bl ? 0 : arrn[j];
                                int n13 = bl2 ? 0 : arrn2[k];
                                int n14 = bl3 ? 0 : arrn3[i2];
                                l2 += this.getDuration((n7 + n12 + n8 + n13) * 14 + n9 + n14, l);
                            }
                        }
                    }
                    printWriter.print("\t");
                    printWriter.print(l2);
                    ++n6;
                } while (true);
            }
            n = n5;
        }
    }

    public void dumpInternalLocked(PrintWriter printWriter, String string2, boolean bl) {
        if (bl) {
            printWriter.print(string2);
            printWriter.print("myID=");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.print(" mCommonProcess=");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.mCommonProcess)));
            printWriter.print(" mPackage=");
            printWriter.println(this.mPackage);
            if (this.mMultiPackage) {
                printWriter.print(string2);
                printWriter.print("mMultiPackage=");
                printWriter.println(this.mMultiPackage);
            }
            if (this != this.mCommonProcess) {
                printWriter.print(string2);
                printWriter.print("Common Proc: ");
                printWriter.print(this.mCommonProcess.mName);
                printWriter.print("/");
                printWriter.print(this.mCommonProcess.mUid);
                printWriter.print(" pkg=");
                printWriter.println(this.mCommonProcess.mPackage);
            }
        }
        if (this.mActive) {
            printWriter.print(string2);
            printWriter.print("mActive=");
            printWriter.println(this.mActive);
        }
        if (this.mDead) {
            printWriter.print(string2);
            printWriter.print("mDead=");
            printWriter.println(this.mDead);
        }
        if (this.mNumActiveServices != 0 || this.mNumStartedServices != 0) {
            printWriter.print(string2);
            printWriter.print("mNumActiveServices=");
            printWriter.print(this.mNumActiveServices);
            printWriter.print(" mNumStartedServices=");
            printWriter.println(this.mNumStartedServices);
        }
    }

    public void dumpPackageProcCheckin(PrintWriter printWriter, String string2, int n, long l, String string3, long l2) {
        printWriter.print("pkgproc,");
        printWriter.print(string2);
        printWriter.print(",");
        printWriter.print(n);
        printWriter.print(",");
        printWriter.print(l);
        printWriter.print(",");
        printWriter.print(DumpUtils.collapseString(string2, string3));
        this.dumpAllStateCheckin(printWriter, l2);
        printWriter.println();
        if (this.mPssTable.getKeyCount() > 0) {
            printWriter.print("pkgpss,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            printWriter.print(",");
            printWriter.print(l);
            printWriter.print(",");
            printWriter.print(DumpUtils.collapseString(string2, string3));
            this.dumpAllPssCheckin(printWriter);
            printWriter.println();
        }
        if (this.mTotalRunningPss[0] != 0L) {
            printWriter.print("pkgrun,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            printWriter.print(",");
            printWriter.print(l);
            printWriter.print(",");
            printWriter.print(DumpUtils.collapseString(string2, string3));
            printWriter.print(",");
            printWriter.print(this.getTotalRunningDuration(l2));
            printWriter.print(",");
            ProcessState.dumpPssSamplesCheckin(printWriter, this.mTotalRunningPss, 0);
            printWriter.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            printWriter.print("pkgkills,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            printWriter.print(",");
            printWriter.print(l);
            printWriter.print(",");
            printWriter.print(DumpUtils.collapseString(string2, string3));
            printWriter.print(",");
            printWriter.print("0");
            printWriter.print(",");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.print(",");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(",");
            printWriter.print(this.mMinCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mAvgCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mMaxCachedKillPss);
            printWriter.println();
        }
    }

    public void dumpProcCheckin(PrintWriter printWriter, String string2, int n, long l) {
        if (this.mDurations.getKeyCount() > 0) {
            printWriter.print("proc,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            this.dumpAllStateCheckin(printWriter, l);
            printWriter.println();
        }
        if (this.mPssTable.getKeyCount() > 0) {
            printWriter.print("pss,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            this.dumpAllPssCheckin(printWriter);
            printWriter.println();
        }
        if (this.mTotalRunningPss[0] != 0L) {
            printWriter.print("procrun,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            printWriter.print(",");
            printWriter.print(this.getTotalRunningDuration(l));
            printWriter.print(",");
            ProcessState.dumpPssSamplesCheckin(printWriter, this.mTotalRunningPss, 0);
            printWriter.println();
        }
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            printWriter.print("kills,");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            printWriter.print(",");
            printWriter.print("0");
            printWriter.print(",");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.print(",");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(",");
            printWriter.print(this.mMinCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mAvgCachedKillPss);
            printWriter.print(":");
            printWriter.print(this.mMaxCachedKillPss);
            printWriter.println();
        }
    }

    public void dumpProcessState(PrintWriter printWriter, String string2, int[] arrn, int[] arrn2, int[] arrn3, long l) {
        long l2 = 0L;
        int n = -1;
        int n2 = 0;
        while (n2 < arrn.length) {
            int n3 = -1;
            int n4 = 0;
            int n5 = n2;
            while (n4 < arrn2.length) {
                int n6 = 0;
                n2 = n3;
                n3 = n4;
                do {
                    String string3;
                    ProcessState processState = this;
                    if (n6 >= arrn3.length) break;
                    int n7 = arrn[n5];
                    int n8 = arrn2[n3];
                    n4 = (n7 + n8) * 14 + arrn3[n6];
                    long l3 = processState.mDurations.getValueForId((byte)n4);
                    if (processState.mCurCombinedState == n4) {
                        string3 = " (running)";
                        l3 += l - processState.mStartTime;
                    } else {
                        string3 = "";
                    }
                    if (l3 != 0L) {
                        printWriter.print(string2);
                        n4 = n;
                        if (arrn.length > 1) {
                            n = n != n7 ? n7 : -1;
                            DumpUtils.printScreenLabel(printWriter, n);
                            n4 = n7;
                        }
                        n = n2;
                        if (arrn2.length > 1) {
                            n = n2 != n8 ? n8 : -1;
                            DumpUtils.printMemLabel(printWriter, n, '/');
                            n = n8;
                        }
                        printWriter.print(DumpUtils.STATE_LABELS[arrn3[n6]]);
                        printWriter.print(": ");
                        TimeUtils.formatDuration(l3, printWriter);
                        printWriter.println(string3);
                        l2 += l3;
                        n2 = n;
                        n = n4;
                    }
                    ++n6;
                } while (true);
                n4 = n3 + 1;
                n3 = n2;
            }
            n2 = n5 + 1;
        }
        if (l2 != 0L) {
            printWriter.print(string2);
            if (arrn.length > 1) {
                DumpUtils.printScreenLabel(printWriter, -1);
            }
            if (arrn2.length > 1) {
                DumpUtils.printMemLabel(printWriter, -1, '/');
            }
            printWriter.print(DumpUtils.STATE_LABEL_TOTAL);
            printWriter.print(": ");
            TimeUtils.formatDuration(l2, printWriter);
            printWriter.println();
        }
    }

    public void dumpPss(PrintWriter printWriter, String string2, int[] arrn, int[] arrn2, int[] arrn3, long l) {
        boolean bl = false;
        int n = -1;
        int n2 = 0;
        while (n2 < arrn.length) {
            int n3 = -1;
            int n4 = n2;
            for (int i = 0; i < arrn2.length; ++i) {
                int n5 = 0;
                n2 = n3;
                do {
                    int[] arrn4 = arrn;
                    if (n5 >= arrn3.length) break;
                    int n6 = arrn4[n4];
                    int n7 = arrn2[i];
                    n3 = arrn3[n5];
                    if ((n3 = this.mPssTable.getKey((byte)((n6 + n7) * 14 + n3))) != -1) {
                        long[] arrl = this.mPssTable.getArrayForKey(n3);
                        int n8 = SparseMappingTable.getIndexFromKey(n3);
                        if (!bl) {
                            printWriter.print(string2);
                            printWriter.print("PSS/USS (");
                            printWriter.print(this.mPssTable.getKeyCount());
                            printWriter.println(" entries):");
                            bl = true;
                        }
                        printWriter.print(string2);
                        printWriter.print("  ");
                        n3 = n;
                        if (arrn4.length > 1) {
                            n = n != n6 ? n6 : -1;
                            DumpUtils.printScreenLabel(printWriter, n);
                            n3 = n6;
                        }
                        n = n2;
                        if (arrn2.length > 1) {
                            n2 = n2 != n7 ? n7 : -1;
                            DumpUtils.printMemLabel(printWriter, n2, '/');
                            n = n7;
                        }
                        printWriter.print(DumpUtils.STATE_LABELS[arrn3[n5]]);
                        printWriter.print(": ");
                        ProcessState.dumpPssSamples(printWriter, arrl, n8);
                        printWriter.println();
                        n2 = n;
                        n = n3;
                    }
                    ++n5;
                } while (true);
                n3 = n2;
            }
            n2 = n4 + 1;
        }
        if ((l = this.getTotalRunningDuration(l)) != 0L) {
            printWriter.print(string2);
            printWriter.print("Cur time ");
            TimeUtils.formatDuration(l, printWriter);
            if (this.mTotalRunningStartTime != 0L) {
                printWriter.print(" (running)");
            }
            if (this.mTotalRunningPss[0] != 0L) {
                printWriter.print(": ");
                ProcessState.dumpPssSamples(printWriter, this.mTotalRunningPss, 0);
            }
            printWriter.println();
        }
        if (this.mNumExcessiveCpu != 0) {
            printWriter.print(string2);
            printWriter.print("Killed for excessive CPU use: ");
            printWriter.print(this.mNumExcessiveCpu);
            printWriter.println(" times");
        }
        if (this.mNumCachedKill != 0) {
            printWriter.print(string2);
            printWriter.print("Killed from cached state: ");
            printWriter.print(this.mNumCachedKill);
            printWriter.print(" times from pss ");
            DebugUtils.printSizeValue(printWriter, this.mMinCachedKillPss * 1024L);
            printWriter.print("-");
            DebugUtils.printSizeValue(printWriter, this.mAvgCachedKillPss * 1024L);
            printWriter.print("-");
            DebugUtils.printSizeValue(printWriter, this.mMaxCachedKillPss * 1024L);
            printWriter.println();
        }
    }

    public void dumpSummary(PrintWriter printWriter, String string2, String string3, int[] arrn, int[] arrn2, int[] arrn3, long l, long l2) {
        printWriter.print(string2);
        printWriter.print("* ");
        if (string3 != null) {
            printWriter.print(string3);
        }
        printWriter.print(this.mName);
        printWriter.print(" / ");
        UserHandle.formatUid(printWriter, this.mUid);
        printWriter.print(" / v");
        printWriter.print(this.mVersion);
        printWriter.println(":");
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABEL_TOTAL, arrn, arrn2, arrn3, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[0], arrn, arrn2, new int[]{0}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[1], arrn, arrn2, new int[]{1}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[2], arrn, arrn2, new int[]{2}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[3], arrn, arrn2, new int[]{3}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[4], arrn, arrn2, new int[]{4}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[5], arrn, arrn2, new int[]{5}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[6], arrn, arrn2, new int[]{6}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[7], arrn, arrn2, new int[]{7}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[8], arrn, arrn2, new int[]{8}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[9], arrn, arrn2, new int[]{9}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABELS[10], arrn, arrn2, new int[]{10}, l, l2, true);
        this.dumpProcessSummaryDetails(printWriter, string2, DumpUtils.STATE_LABEL_CACHED, arrn, arrn2, new int[]{11, 12, 13}, l, l2, true);
    }

    public int getCombinedState() {
        return this.mCurCombinedState;
    }

    public ProcessState getCommonProcess() {
        return this.mCommonProcess;
    }

    public long getDuration(int n, long l) {
        long l2;
        long l3 = l2 = this.mDurations.getValueForId((byte)n);
        if (this.mCurCombinedState == n) {
            l3 = l2 + (l - this.mStartTime);
        }
        return l3;
    }

    public int getDurationsBucketCount() {
        return this.mDurations.getKeyCount();
    }

    public String getName() {
        return this.mName;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public long getPssAverage(int n) {
        return this.mPssTable.getValueForId((byte)n, 2);
    }

    public long getPssMaximum(int n) {
        return this.mPssTable.getValueForId((byte)n, 3);
    }

    public long getPssMinimum(int n) {
        return this.mPssTable.getValueForId((byte)n, 1);
    }

    public long getPssRssAverage(int n) {
        return this.mPssTable.getValueForId((byte)n, 8);
    }

    public long getPssRssMaximum(int n) {
        return this.mPssTable.getValueForId((byte)n, 9);
    }

    public long getPssRssMinimum(int n) {
        return this.mPssTable.getValueForId((byte)n, 7);
    }

    public long getPssSampleCount(int n) {
        return this.mPssTable.getValueForId((byte)n, 0);
    }

    public long getPssUssAverage(int n) {
        return this.mPssTable.getValueForId((byte)n, 5);
    }

    public long getPssUssMaximum(int n) {
        return this.mPssTable.getValueForId((byte)n, 6);
    }

    public long getPssUssMinimum(int n) {
        return this.mPssTable.getValueForId((byte)n, 4);
    }

    public long getTotalRunningDuration(long l) {
        long l2 = this.mTotalRunningDuration;
        long l3 = this.mTotalRunningStartTime;
        long l4 = 0L;
        if (l3 != 0L) {
            l4 = l - l3;
        }
        return l2 + l4;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getVersion() {
        return this.mVersion;
    }

    public boolean hasAnyData() {
        int n = this.mDurations.getKeyCount();
        boolean bl = false;
        if (n != 0 || this.mCurCombinedState != -1 || this.mPssTable.getKeyCount() != 0 || this.mTotalRunningPss[0] != 0L) {
            bl = true;
        }
        return bl;
    }

    public void incActiveServices(String string2) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.incActiveServices(string2);
        }
        ++this.mNumActiveServices;
    }

    public void incStartedServices(int n, long l, String string2) {
        ProcessState processState = this.mCommonProcess;
        if (processState != this) {
            processState.incStartedServices(n, l, string2);
        }
        ++this.mNumStartedServices;
        if (this.mNumStartedServices == 1 && this.mCurCombinedState == -1) {
            this.setCombinedState(n * 14 + 6, l);
        }
    }

    public boolean isActive() {
        return this.mActive;
    }

    public boolean isInUse() {
        boolean bl = this.mActive || this.mNumActiveServices > 0 || this.mNumStartedServices > 0 || this.mCurCombinedState != -1;
        return bl;
    }

    public boolean isMultiPackage() {
        return this.mMultiPackage;
    }

    public void makeActive() {
        this.ensureNotDead();
        this.mActive = true;
    }

    public void makeDead() {
        this.mDead = true;
    }

    public void makeInactive() {
        this.mActive = false;
    }

    public void makeStandalone() {
        this.mCommonProcess = this;
    }

    public ProcessState pullFixedProc(String string2) {
        if (this.mMultiPackage) {
            Object object = this.mStats.mPackages.get(string2, this.mUid);
            if (object != null) {
                if ((object = ((LongSparseArray)object).get(this.mVersion)) != null) {
                    object = ((ProcessStats.PackageState)object).mProcesses.get(this.mName);
                    if (object != null) {
                        return object;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Didn't create per-package process ");
                    ((StringBuilder)object).append(this.mName);
                    ((StringBuilder)object).append(" in pkg ");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append(" / ");
                    ((StringBuilder)object).append(this.mUid);
                    ((StringBuilder)object).append(" vers ");
                    ((StringBuilder)object).append(this.mVersion);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Didn't find package ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" / ");
                ((StringBuilder)object).append(this.mUid);
                ((StringBuilder)object).append(" vers ");
                ((StringBuilder)object).append(this.mVersion);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Didn't find package ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" / ");
            ((StringBuilder)object).append(this.mUid);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        return this;
    }

    public boolean readFromParcel(Parcel parcel, boolean bl) {
        boolean bl2 = parcel.readInt() != 0;
        if (bl) {
            this.mMultiPackage = bl2;
        }
        if (!this.mDurations.readFromParcel(parcel)) {
            return false;
        }
        if (!this.mPssTable.readFromParcel(parcel)) {
            return false;
        }
        for (int i = 0; i < 10; ++i) {
            this.mTotalRunningPss[i] = parcel.readLong();
        }
        this.mTotalRunningDuration = parcel.readLong();
        parcel.readInt();
        this.mNumExcessiveCpu = parcel.readInt();
        this.mNumCachedKill = parcel.readInt();
        if (this.mNumCachedKill > 0) {
            this.mMinCachedKillPss = parcel.readLong();
            this.mAvgCachedKillPss = parcel.readLong();
            this.mMaxCachedKillPss = parcel.readLong();
        } else {
            this.mMaxCachedKillPss = 0L;
            this.mAvgCachedKillPss = 0L;
            this.mMinCachedKillPss = 0L;
        }
        return true;
    }

    public void reportCachedKill(ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap, long l) {
        this.ensureNotDead();
        this.mCommonProcess.addCachedKill(1, l, l, l);
        if (!this.mCommonProcess.mMultiPackage) {
            return;
        }
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            this.pullFixedProc(arrayMap, i).addCachedKill(1, l, l, l);
        }
    }

    public void reportExcessiveCpu(ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        this.ensureNotDead();
        ProcessState processState = this.mCommonProcess;
        ++processState.mNumExcessiveCpu;
        if (!processState.mMultiPackage) {
            return;
        }
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            processState = this.pullFixedProc(arrayMap, i);
            ++processState.mNumExcessiveCpu;
        }
    }

    public void resetSafely(long l) {
        this.mDurations.resetTable();
        this.mPssTable.resetTable();
        this.mStartTime = l;
        this.mLastPssState = -1;
        this.mLastPssTime = 0L;
        this.mNumExcessiveCpu = 0;
        this.mNumCachedKill = 0;
        this.mMaxCachedKillPss = 0L;
        this.mAvgCachedKillPss = 0L;
        this.mMinCachedKillPss = 0L;
    }

    public void setCombinedState(int n, long l) {
        this.ensureNotDead();
        if (!this.mDead && this.mCurCombinedState != n) {
            this.commitStateTime(l);
            if (n == -1) {
                this.mTotalRunningDuration += l - this.mTotalRunningStartTime;
                this.mTotalRunningStartTime = 0L;
            } else if (this.mCurCombinedState == -1) {
                this.mTotalRunningDuration = 0L;
                this.mTotalRunningStartTime = l;
                for (int i = 9; i >= 0; --i) {
                    this.mTotalRunningPss[i] = 0L;
                }
            }
            this.mCurCombinedState = n;
        }
    }

    public void setMultiPackage(boolean bl) {
        this.mMultiPackage = bl;
    }

    public void setState(int n, int n2, long l, ArrayMap<String, ProcessStats.ProcessStateHolder> arrayMap) {
        n = n < 0 ? (this.mNumStartedServices > 0 ? n2 * 14 + 6 : -1) : PROCESS_STATE_TO_STATE[n] + n2 * 14;
        this.mCommonProcess.setCombinedState(n, l);
        if (!this.mCommonProcess.mMultiPackage) {
            return;
        }
        if (arrayMap != null) {
            for (n2 = arrayMap.size() - 1; n2 >= 0; --n2) {
                this.pullFixedProc(arrayMap, n2).setCombinedState(n, l);
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("ProcessState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.mName);
        stringBuilder.append("/");
        stringBuilder.append(this.mUid);
        stringBuilder.append(" pkg=");
        stringBuilder.append(this.mPackage);
        if (this.mMultiPackage) {
            stringBuilder.append(" (multi)");
        }
        if (this.mCommonProcess != this) {
            stringBuilder.append(" (sub)");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, long l) {
        parcel.writeInt((int)this.mMultiPackage);
        this.mDurations.writeToParcel(parcel);
        this.mPssTable.writeToParcel(parcel);
        for (int i = 0; i < 10; ++i) {
            parcel.writeLong(this.mTotalRunningPss[i]);
        }
        parcel.writeLong(this.getTotalRunningDuration(l));
        parcel.writeInt(0);
        parcel.writeInt(this.mNumExcessiveCpu);
        parcel.writeInt(this.mNumCachedKill);
        if (this.mNumCachedKill > 0) {
            parcel.writeLong(this.mMinCachedKillPss);
            parcel.writeLong(this.mAvgCachedKillPss);
            parcel.writeLong(this.mMaxCachedKillPss);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, String arrl, int n, long l2) {
        long l3;
        byte by;
        long l4 = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, (String)arrl);
        protoOutputStream.write(1120986464258L, n);
        if (this.mNumExcessiveCpu > 0 || this.mNumCachedKill > 0) {
            l = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, this.mNumExcessiveCpu);
            protoOutputStream.write(1120986464258L, this.mNumCachedKill);
            ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268035L, this.mMinCachedKillPss, this.mAvgCachedKillPss, this.mMaxCachedKillPss);
            protoOutputStream.end(l);
        }
        arrl = new SparseLongArray();
        int n2 = 0;
        for (n = 0; n < this.mDurations.getKeyCount(); ++n) {
            int n3 = this.mDurations.getKeyAt(n);
            by = SparseMappingTable.getIdFromKey(n3);
            l = l3 = this.mDurations.getValue(n3);
            if (this.mCurCombinedState == by) {
                l = l3 + (l2 - this.mStartTime);
                n2 = 1;
            }
            arrl.put(by, l);
        }
        if (n2 == 0 && (n = this.mCurCombinedState) != -1) {
            arrl.put(n, l2 - this.mStartTime);
        }
        n = 0;
        do {
            n2 = this.mPssTable.getKeyCount();
            l = 2246267895813L;
            if (n >= n2) break;
            n2 = this.mPssTable.getKeyAt(n);
            by = SparseMappingTable.getIdFromKey(n2);
            if (arrl.indexOfKey(by) >= 0) {
                l3 = protoOutputStream.start(2246267895813L);
                DumpUtils.printProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169922L, 1159641169923L, by);
                l = arrl.get(by);
                arrl.delete(by);
                protoOutputStream.write(1112396529668L, l);
                this.mPssTable.writeStatsToProtoForKey(protoOutputStream, n2);
                protoOutputStream.end(l3);
            }
            ++n;
        } while (true);
        for (n = 0; n < arrl.size(); ++n) {
            l3 = protoOutputStream.start(l);
            DumpUtils.printProcStateTagProto(protoOutputStream, 1159641169921L, 1159641169922L, 1159641169923L, arrl.keyAt(n));
            protoOutputStream.write(1112396529668L, arrl.valueAt(n));
            protoOutputStream.end(l3);
        }
        if ((l2 = this.getTotalRunningDuration(l2)) > 0L) {
            l = protoOutputStream.start(1146756268038L);
            protoOutputStream.write(1112396529668L, l2);
            arrl = this.mTotalRunningPss;
            if (arrl[0] != 0L) {
                PssTable.writeStatsToProto(protoOutputStream, arrl, 0);
            }
            protoOutputStream.end(l);
        }
        protoOutputStream.end(l4);
    }

    static class PssAggr {
        long pss = 0L;
        long samples = 0L;

        PssAggr() {
        }

        void add(long l, long l2) {
            double d = this.pss;
            long l3 = this.samples;
            this.pss = (long)(d * (double)l3 + (double)l * (double)l2) / (l3 + l2);
            this.samples = l3 + l2;
        }
    }

}

