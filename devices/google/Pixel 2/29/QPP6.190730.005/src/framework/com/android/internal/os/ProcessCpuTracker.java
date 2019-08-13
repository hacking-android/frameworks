/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Process;
import android.os.StrictMode;
import android.os.SystemClock;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Slog;
import com.android.internal.os.BatteryStatsImpl;
import com.android.internal.os.ProcStatsUtil;
import com.android.internal.util.FastPrintWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ProcessCpuTracker {
    private static final boolean DEBUG = false;
    private static final int[] LOAD_AVERAGE_FORMAT;
    private static final int[] PROCESS_FULL_STATS_FORMAT;
    static final int PROCESS_FULL_STAT_MAJOR_FAULTS = 2;
    static final int PROCESS_FULL_STAT_MINOR_FAULTS = 1;
    static final int PROCESS_FULL_STAT_STIME = 4;
    static final int PROCESS_FULL_STAT_UTIME = 3;
    static final int PROCESS_FULL_STAT_VSIZE = 5;
    private static final int[] PROCESS_STATS_FORMAT;
    static final int PROCESS_STAT_MAJOR_FAULTS = 1;
    static final int PROCESS_STAT_MINOR_FAULTS = 0;
    static final int PROCESS_STAT_STIME = 3;
    static final int PROCESS_STAT_UTIME = 2;
    private static final int[] SYSTEM_CPU_FORMAT;
    private static final String TAG = "ProcessCpuTracker";
    private static final boolean localLOGV = false;
    private static final Comparator<Stats> sLoadComparator;
    private long mBaseIdleTime;
    private long mBaseIoWaitTime;
    private long mBaseIrqTime;
    private long mBaseSoftIrqTime;
    private long mBaseSystemTime;
    private long mBaseUserTime;
    private int[] mCurPids;
    private int[] mCurThreadPids;
    private long mCurrentSampleRealTime;
    private long mCurrentSampleTime;
    private long mCurrentSampleWallTime;
    private boolean mFirst = true;
    private final boolean mIncludeThreads;
    private final long mJiffyMillis;
    private long mLastSampleRealTime;
    private long mLastSampleTime;
    private long mLastSampleWallTime;
    private float mLoad1 = 0.0f;
    private float mLoad15 = 0.0f;
    private float mLoad5 = 0.0f;
    private final float[] mLoadAverageData = new float[3];
    private final ArrayList<Stats> mProcStats = new ArrayList();
    private final long[] mProcessFullStatsData = new long[6];
    private final String[] mProcessFullStatsStringData = new String[6];
    private final long[] mProcessStatsData = new long[4];
    private int mRelIdleTime;
    private int mRelIoWaitTime;
    private int mRelIrqTime;
    private int mRelSoftIrqTime;
    private boolean mRelStatsAreGood;
    private int mRelSystemTime;
    private int mRelUserTime;
    private final long[] mSinglePidStatsData = new long[4];
    private final long[] mSystemCpuData = new long[7];
    private final ArrayList<Stats> mWorkingProcs = new ArrayList();
    private boolean mWorkingProcsSorted;

    static {
        PROCESS_STATS_FORMAT = new int[]{32, 544, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224};
        PROCESS_FULL_STATS_FORMAT = new int[]{32, 4640, 32, 32, 32, 32, 32, 32, 32, 8224, 32, 8224, 32, 8224, 8224, 32, 32, 32, 32, 32, 32, 32, 8224};
        SYSTEM_CPU_FORMAT = new int[]{288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
        LOAD_AVERAGE_FORMAT = new int[]{16416, 16416, 16416};
        sLoadComparator = new Comparator<Stats>(){

            @Override
            public final int compare(Stats stats, Stats stats2) {
                int n = stats.rel_utime + stats.rel_stime;
                int n2 = stats2.rel_utime + stats2.rel_stime;
                int n3 = -1;
                if (n != n2) {
                    if (n <= n2) {
                        n3 = 1;
                    }
                    return n3;
                }
                if (stats.added != stats2.added) {
                    if (!stats.added) {
                        n3 = 1;
                    }
                    return n3;
                }
                if (stats.removed != stats2.removed) {
                    if (!stats.added) {
                        n3 = 1;
                    }
                    return n3;
                }
                return 0;
            }
        };
    }

    @UnsupportedAppUsage
    public ProcessCpuTracker(boolean bl) {
        this.mIncludeThreads = bl;
        this.mJiffyMillis = 1000L / Os.sysconf((int)OsConstants._SC_CLK_TCK);
    }

    private int[] collectStats(String object, int n, boolean bl, int[] arrn, ArrayList<Stats> object2) {
        int n2 = n;
        int n3 = (arrn = Process.getPids((String)object, arrn)) == null ? 0 : arrn.length;
        int n4 = n3;
        n3 = ((ArrayList)object2).size();
        int n5 = 0;
        int n6 = 0;
        int n7 = n2;
        n2 = n5;
        do {
            Object object3;
            Object object4;
            Object[] arrobject;
            boolean bl2;
            long l;
            object = object2;
            if (n6 >= n4 || (n5 = arrn[n6]) < 0) break;
            object = n2 < n3 ? (Stats)((ArrayList)object).get(n2) : null;
            if (object != null && ((Stats)object).pid == n5) {
                ((Stats)object).added = false;
                ((Stats)object).working = false;
                if (((Stats)object).interesting) {
                    object3 = SystemClock.uptimeMillis();
                    object4 = this.mProcessStatsData;
                    if (Process.readProcFile(((Stats)object).statFile.toString(), PROCESS_STATS_FORMAT, null, (long[])object4, null)) {
                        long l2 = object4[0];
                        l = object4[1];
                        long l3 = object4[2];
                        Object object5 = this.mJiffyMillis;
                        l3 *= object5;
                        object5 = object4[3] * object5;
                        if (l3 == ((Stats)object).base_utime && object5 == ((Stats)object).base_stime) {
                            ((Stats)object).rel_utime = 0;
                            ((Stats)object).rel_stime = 0;
                            ((Stats)object).rel_minfaults = 0;
                            ((Stats)object).rel_majfaults = 0;
                            if (((Stats)object).active) {
                                ((Stats)object).active = false;
                            }
                        } else {
                            if (!((Stats)object).active) {
                                ((Stats)object).active = true;
                            }
                            if (n7 < 0) {
                                this.getName((Stats)object, ((Stats)object).cmdlineFile);
                                if (((Stats)object).threadStats != null) {
                                    object4 = ((Stats)object).threadsDir;
                                    arrobject = this.mCurThreadPids;
                                    ArrayList<Stats> arrayList = ((Stats)object).threadStats;
                                    bl2 = true;
                                    this.mCurThreadPids = this.collectStats((String)object4, n5, false, (int[])arrobject, arrayList);
                                } else {
                                    bl2 = true;
                                }
                            } else {
                                bl2 = true;
                            }
                            ((Stats)object).rel_uptime = object3 - ((Stats)object).base_uptime;
                            ((Stats)object).base_uptime = object3;
                            ((Stats)object).rel_utime = (int)(l3 - ((Stats)object).base_utime);
                            ((Stats)object).rel_stime = (int)(object5 - ((Stats)object).base_stime);
                            ((Stats)object).base_utime = l3;
                            ((Stats)object).base_stime = object5;
                            ((Stats)object).rel_minfaults = (int)(l2 - ((Stats)object).base_minfaults);
                            ((Stats)object).rel_majfaults = (int)(l - ((Stats)object).base_majfaults);
                            ((Stats)object).base_minfaults = l2;
                            ((Stats)object).base_majfaults = l;
                            ((Stats)object).working = bl2;
                        }
                    }
                }
                n7 = n6;
                n6 = n2 + 1;
                n2 = n7;
            } else if (object != null && ((Stats)object).pid <= n5) {
                ((Stats)object).rel_utime = 0;
                ((Stats)object).rel_stime = 0;
                ((Stats)object).rel_minfaults = 0;
                ((Stats)object).rel_majfaults = 0;
                ((Stats)object).removed = true;
                ((Stats)object).working = true;
                ((ArrayList)object2).remove(n2);
                --n3;
                n7 = n6 - 1;
                n6 = n2;
                n2 = n7;
            } else {
                bl2 = this.mIncludeThreads;
                n7 = n;
                object = new Stats(n5, n7, bl2);
                ((ArrayList)object2).add(n2, object);
                ++n3;
                arrobject = this.mProcessFullStatsStringData;
                object4 = this.mProcessFullStatsData;
                ((Stats)object).base_uptime = SystemClock.uptimeMillis();
                if (Process.readProcFile(((Stats)object).statFile.toString(), PROCESS_FULL_STATS_FORMAT, arrobject, (long[])object4, null)) {
                    ((Stats)object).vsize = object4[5];
                    ((Stats)object).interesting = true;
                    ((Stats)object).baseName = arrobject[0];
                    ((Stats)object).base_minfaults = (long)object4[1];
                    ((Stats)object).base_majfaults = (long)object4[2];
                    object3 = object4[3];
                    l = this.mJiffyMillis;
                    ((Stats)object).base_utime = object3 * l;
                    ((Stats)object).base_stime = (long)(object4[4] * l);
                } else {
                    object4 = new StringBuilder();
                    ((StringBuilder)object4).append("Skipping unknown process pid ");
                    ((StringBuilder)object4).append(n5);
                    Slog.w(TAG, ((StringBuilder)object4).toString());
                    ((Stats)object).baseName = "<unknown>";
                    ((Stats)object).base_stime = 0L;
                    ((Stats)object).base_utime = 0L;
                    ((Stats)object).base_majfaults = 0L;
                    ((Stats)object).base_minfaults = 0L;
                }
                if (n7 < 0) {
                    this.getName((Stats)object, ((Stats)object).cmdlineFile);
                    if (((Stats)object).threadStats != null) {
                        this.mCurThreadPids = this.collectStats(((Stats)object).threadsDir, n5, true, this.mCurThreadPids, ((Stats)object).threadStats);
                    }
                } else if (((Stats)object).interesting) {
                    ((Stats)object).name = ((Stats)object).baseName;
                    ((Stats)object).nameWidth = this.onMeasureProcessName(((Stats)object).name);
                }
                ((Stats)object).rel_utime = 0;
                ((Stats)object).rel_stime = 0;
                ((Stats)object).rel_minfaults = 0;
                ((Stats)object).rel_majfaults = 0;
                ((Stats)object).added = true;
                if (!bl && ((Stats)object).interesting) {
                    ((Stats)object).working = true;
                }
                n7 = n2 + 1;
                n2 = n6;
                n6 = n7;
            }
            n5 = n2 + 1;
            n7 = n;
            n2 = n6;
            n6 = n5;
        } while (true);
        while (n2 < n3) {
            object2 = (Stats)((ArrayList)object).get(n2);
            ((Stats)object2).rel_utime = 0;
            ((Stats)object2).rel_stime = 0;
            ((Stats)object2).rel_minfaults = 0;
            ((Stats)object2).rel_majfaults = 0;
            ((Stats)object2).removed = true;
            ((Stats)object2).working = true;
            ((ArrayList)object).remove(n2);
            --n3;
        }
        return arrn;
    }

    private void getName(Stats stats, String string2) {
        String string3;
        block10 : {
            String string4;
            block9 : {
                string4 = stats.name;
                if (stats.name == null || stats.name.equals("app_process")) break block9;
                string3 = string4;
                if (!stats.name.equals("<pre-initialized>")) break block10;
            }
            string3 = ProcStatsUtil.readTerminatedProcFile(string2, (byte)0);
            string2 = string4;
            if (string3 != null) {
                string2 = string4;
                if (string3.length() > 1) {
                    int n = string3.lastIndexOf("/");
                    string2 = string3;
                    if (n > 0) {
                        string2 = string3;
                        if (n < string3.length() - 1) {
                            string2 = string3.substring(n + 1);
                        }
                    }
                }
            }
            string3 = string2;
            if (string2 == null) {
                string3 = stats.baseName;
            }
        }
        if (stats.name == null || !string3.equals(stats.name)) {
            stats.name = string3;
            stats.nameWidth = this.onMeasureProcessName(stats.name);
        }
    }

    private void printProcessCPU(PrintWriter printWriter, String string2, int n, String string3, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        printWriter.print(string2);
        if (n2 == 0) {
            n2 = 1;
        }
        this.printRatio(printWriter, n3 + n4 + n5 + n6 + n7, n2);
        printWriter.print("% ");
        if (n >= 0) {
            printWriter.print(n);
            printWriter.print("/");
        }
        printWriter.print(string3);
        printWriter.print(": ");
        this.printRatio(printWriter, n3, n2);
        printWriter.print("% user + ");
        this.printRatio(printWriter, n4, n2);
        printWriter.print("% kernel");
        if (n5 > 0) {
            printWriter.print(" + ");
            this.printRatio(printWriter, n5, n2);
            printWriter.print("% iowait");
        }
        if (n6 > 0) {
            printWriter.print(" + ");
            this.printRatio(printWriter, n6, n2);
            printWriter.print("% irq");
        }
        if (n7 > 0) {
            printWriter.print(" + ");
            this.printRatio(printWriter, n7, n2);
            printWriter.print("% softirq");
        }
        if (n8 > 0 || n9 > 0) {
            printWriter.print(" / faults:");
            if (n8 > 0) {
                printWriter.print(" ");
                printWriter.print(n8);
                printWriter.print(" minor");
            }
            if (n9 > 0) {
                printWriter.print(" ");
                printWriter.print(n9);
                printWriter.print(" major");
            }
        }
        printWriter.println();
    }

    private void printRatio(PrintWriter printWriter, long l, long l2) {
        l = 1000L * l / l2;
        l2 = l / 10L;
        printWriter.print(l2);
        if (l2 < 10L && (l -= 10L * l2) != 0L) {
            printWriter.print('.');
            printWriter.print(l);
        }
    }

    final void buildWorkingProcs() {
        if (!this.mWorkingProcsSorted) {
            this.mWorkingProcs.clear();
            int n = this.mProcStats.size();
            for (int i = 0; i < n; ++i) {
                Stats stats = this.mProcStats.get(i);
                if (!stats.working) continue;
                this.mWorkingProcs.add(stats);
                if (stats.threadStats == null || stats.threadStats.size() <= 1) continue;
                stats.workingThreads.clear();
                int n2 = stats.threadStats.size();
                for (int j = 0; j < n2; ++j) {
                    Stats stats2 = stats.threadStats.get(j);
                    if (!stats2.working) continue;
                    stats.workingThreads.add(stats2);
                }
                Collections.sort(stats.workingThreads, sLoadComparator);
            }
            Collections.sort(this.mWorkingProcs, sLoadComparator);
            this.mWorkingProcsSorted = true;
        }
    }

    public final int countStats() {
        return this.mProcStats.size();
    }

    @UnsupportedAppUsage
    public final int countWorkingStats() {
        this.buildWorkingProcs();
        return this.mWorkingProcs.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getCpuTimeForPid(int n) {
        long[] arrl = this.mSinglePidStatsData;
        synchronized (arrl) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("/proc/");
            charSequence.append(n);
            charSequence.append("/stat");
            charSequence = charSequence.toString();
            long[] arrl2 = this.mSinglePidStatsData;
            if (Process.readProcFile((String)charSequence, PROCESS_STATS_FORMAT, null, arrl2, null)) {
                long l = arrl2[2];
                long l2 = arrl2[3];
                long l3 = this.mJiffyMillis;
                return l3 * (l + l2);
            }
            return 0L;
        }
    }

    public final int getLastIdleTime() {
        return this.mRelIdleTime;
    }

    public final int getLastIoWaitTime() {
        return this.mRelIoWaitTime;
    }

    public final int getLastIrqTime() {
        return this.mRelIrqTime;
    }

    public final int getLastSoftIrqTime() {
        return this.mRelSoftIrqTime;
    }

    public final int getLastSystemTime() {
        return this.mRelSystemTime;
    }

    public final int getLastUserTime() {
        return this.mRelUserTime;
    }

    public final Stats getStats(int n) {
        return this.mProcStats.get(n);
    }

    public final List<Stats> getStats(FilterStats filterStats) {
        ArrayList<Stats> arrayList = new ArrayList<Stats>(this.mProcStats.size());
        int n = this.mProcStats.size();
        for (int i = 0; i < n; ++i) {
            Stats stats = this.mProcStats.get(i);
            if (!filterStats.needed(stats)) continue;
            arrayList.add(stats);
        }
        return arrayList;
    }

    public final float getTotalCpuPercent() {
        int n = this.mRelUserTime;
        int n2 = this.mRelSystemTime;
        int n3 = this.mRelIrqTime;
        int n4 = n + n2 + n3 + this.mRelIdleTime;
        if (n4 <= 0) {
            return 0.0f;
        }
        return (float)(n + n2 + n3) * 100.0f / (float)n4;
    }

    @UnsupportedAppUsage
    public final Stats getWorkingStats(int n) {
        return this.mWorkingProcs.get(n);
    }

    public final boolean hasGoodLastStats() {
        return this.mRelStatsAreGood;
    }

    public void init() {
        this.mFirst = true;
        this.update();
    }

    public void onLoadChanged(float f, float f2, float f3) {
    }

    public int onMeasureProcessName(String string2) {
        return 0;
    }

    public final String printCurrentLoad() {
        StringWriter stringWriter = new StringWriter();
        FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 128);
        ((PrintWriter)fastPrintWriter).print("Load: ");
        fastPrintWriter.print(this.mLoad1);
        ((PrintWriter)fastPrintWriter).print(" / ");
        fastPrintWriter.print(this.mLoad5);
        ((PrintWriter)fastPrintWriter).print(" / ");
        fastPrintWriter.println(this.mLoad15);
        ((PrintWriter)fastPrintWriter).flush();
        return stringWriter.toString();
    }

    public final String printCurrentState(long l) {
        Object object = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.buildWorkingProcs();
        StringWriter stringWriter = new StringWriter();
        FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 1024);
        ((PrintWriter)fastPrintWriter).print("CPU usage from ");
        long l2 = this.mLastSampleTime;
        if (l > l2) {
            ((PrintWriter)fastPrintWriter).print(l - l2);
            ((PrintWriter)fastPrintWriter).print("ms to ");
            ((PrintWriter)fastPrintWriter).print(l - this.mCurrentSampleTime);
            ((PrintWriter)fastPrintWriter).print("ms ago");
        } else {
            ((PrintWriter)fastPrintWriter).print(l2 - l);
            ((PrintWriter)fastPrintWriter).print("ms to ");
            ((PrintWriter)fastPrintWriter).print(this.mCurrentSampleTime - l);
            ((PrintWriter)fastPrintWriter).print("ms later");
        }
        ((PrintWriter)fastPrintWriter).print(" (");
        ((PrintWriter)fastPrintWriter).print(((DateFormat)object).format(new Date(this.mLastSampleWallTime)));
        ((PrintWriter)fastPrintWriter).print(" to ");
        ((PrintWriter)fastPrintWriter).print(((DateFormat)object).format(new Date(this.mCurrentSampleWallTime)));
        ((PrintWriter)fastPrintWriter).print(")");
        l2 = this.mCurrentSampleTime;
        long l3 = this.mLastSampleTime;
        long l4 = this.mCurrentSampleRealTime - this.mLastSampleRealTime;
        l = 0L;
        if (l4 > 0L) {
            l = (l2 - l3) * 100L / l4;
        }
        if (l != 100L) {
            ((PrintWriter)fastPrintWriter).print(" with ");
            ((PrintWriter)fastPrintWriter).print(l);
            ((PrintWriter)fastPrintWriter).print("% awake");
        }
        fastPrintWriter.println(":");
        int n = this.mRelUserTime;
        int n2 = this.mRelSystemTime;
        int n3 = this.mRelIoWaitTime;
        int n4 = this.mRelIrqTime;
        int n5 = this.mRelSoftIrqTime;
        int n6 = this.mRelIdleTime;
        int n7 = this.mWorkingProcs.size();
        for (int i = 0; i < n7; ++i) {
            Stats stats = this.mWorkingProcs.get(i);
            object = stats.added ? " +" : (stats.removed ? " -" : "  ");
            this.printProcessCPU(fastPrintWriter, (String)object, stats.pid, stats.name, (int)stats.rel_uptime, stats.rel_utime, stats.rel_stime, 0, 0, 0, stats.rel_minfaults, stats.rel_majfaults);
            if (stats.removed || stats.workingThreads == null) continue;
            int n8 = stats.workingThreads.size();
            for (int j = 0; j < n8; ++j) {
                Stats stats2 = stats.workingThreads.get(j);
                object = stats2.added ? "   +" : (stats2.removed ? "   -" : "    ");
                this.printProcessCPU(fastPrintWriter, (String)object, stats2.pid, stats2.name, (int)stats.rel_uptime, stats2.rel_utime, stats2.rel_stime, 0, 0, 0, 0, 0);
            }
        }
        this.printProcessCPU(fastPrintWriter, "", -1, "TOTAL", n + n2 + n3 + n4 + n5 + n6, this.mRelUserTime, this.mRelSystemTime, this.mRelIoWaitTime, this.mRelIrqTime, this.mRelSoftIrqTime, 0, 0);
        ((PrintWriter)fastPrintWriter).flush();
        return stringWriter.toString();
    }

    @UnsupportedAppUsage
    public void update() {
        long l = SystemClock.uptimeMillis();
        long l2 = SystemClock.elapsedRealtime();
        long l3 = System.currentTimeMillis();
        Object object = this.mSystemCpuData;
        if (Process.readProcFile("/proc/stat", SYSTEM_CPU_FORMAT, null, object, null)) {
            long l4 = object[0];
            long l5 = object[1];
            long l6 = this.mJiffyMillis;
            l4 = (l4 + l5) * l6;
            long l7 = object[2] * l6;
            l5 = object[3] * l6;
            long l8 = object[4] * l6;
            long l9 = object[5] * l6;
            this.mRelUserTime = (int)(l4 - this.mBaseUserTime);
            this.mRelSystemTime = (int)(l7 - this.mBaseSystemTime);
            this.mRelIoWaitTime = (int)(l8 - this.mBaseIoWaitTime);
            this.mRelIrqTime = (int)(l9 - this.mBaseIrqTime);
            this.mRelSoftIrqTime = (int)((l6 *= object[6]) - this.mBaseSoftIrqTime);
            this.mRelIdleTime = (int)(l5 - this.mBaseIdleTime);
            this.mRelStatsAreGood = true;
            this.mBaseUserTime = l4;
            this.mBaseSystemTime = l7;
            this.mBaseIoWaitTime = l8;
            this.mBaseIrqTime = l9;
            this.mBaseSoftIrqTime = l6;
            this.mBaseIdleTime = l5;
        }
        this.mLastSampleTime = this.mCurrentSampleTime;
        this.mCurrentSampleTime = l;
        this.mLastSampleRealTime = this.mCurrentSampleRealTime;
        this.mCurrentSampleRealTime = l2;
        this.mLastSampleWallTime = this.mCurrentSampleWallTime;
        this.mCurrentSampleWallTime = l3;
        object = StrictMode.allowThreadDiskReads();
        this.mCurPids = this.collectStats("/proc", -1, this.mFirst, this.mCurPids, this.mProcStats);
        object = this.mLoadAverageData;
        if (Process.readProcFile("/proc/loadavg", LOAD_AVERAGE_FORMAT, null, null, object)) {
            long l10 = object[0];
            long l11 = object[1];
            long l12 = object[2];
            if (l10 != this.mLoad1 || l11 != this.mLoad5 || l12 != this.mLoad15) {
                this.mLoad1 = l10;
                this.mLoad5 = l11;
                this.mLoad15 = l12;
                this.onLoadChanged(l10, l11, l12);
            }
        }
        this.mWorkingProcsSorted = false;
        this.mFirst = false;
        return;
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)object);
        }
    }

    public static interface FilterStats {
        public boolean needed(Stats var1);
    }

    public static class Stats {
        public boolean active;
        public boolean added;
        public String baseName;
        public long base_majfaults;
        public long base_minfaults;
        public long base_stime;
        public long base_uptime;
        public long base_utime;
        public BatteryStatsImpl.Uid.Proc batteryStats;
        final String cmdlineFile;
        public boolean interesting;
        @UnsupportedAppUsage
        public String name;
        public int nameWidth;
        public final int pid;
        public int rel_majfaults;
        public int rel_minfaults;
        @UnsupportedAppUsage
        public int rel_stime;
        @UnsupportedAppUsage
        public long rel_uptime;
        @UnsupportedAppUsage
        public int rel_utime;
        public boolean removed;
        final String statFile;
        final ArrayList<Stats> threadStats;
        final String threadsDir;
        public final int uid;
        public long vsize;
        public boolean working;
        final ArrayList<Stats> workingThreads;

        Stats(int n, int n2, boolean bl) {
            this.pid = n;
            if (n2 < 0) {
                File file = new File("/proc", Integer.toString(this.pid));
                this.uid = Stats.getUid(file.toString());
                this.statFile = new File(file, "stat").toString();
                this.cmdlineFile = new File(file, "cmdline").toString();
                this.threadsDir = new File(file, "task").toString();
                if (bl) {
                    this.threadStats = new ArrayList();
                    this.workingThreads = new ArrayList();
                } else {
                    this.threadStats = null;
                    this.workingThreads = null;
                }
            } else {
                File file = new File(new File(new File("/proc", Integer.toString(n2)), "task"), Integer.toString(this.pid));
                this.uid = Stats.getUid(file.toString());
                this.statFile = new File(file, "stat").toString();
                this.cmdlineFile = null;
                this.threadsDir = null;
                this.threadStats = null;
                this.workingThreads = null;
            }
        }

        private static int getUid(String string2) {
            try {
                int n = Os.stat((String)string2).st_uid;
                return n;
            }
            catch (ErrnoException errnoException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to stat(");
                stringBuilder.append(string2);
                stringBuilder.append("): ");
                stringBuilder.append((Object)errnoException);
                Slog.w(ProcessCpuTracker.TAG, stringBuilder.toString());
                return -1;
            }
        }
    }

}

