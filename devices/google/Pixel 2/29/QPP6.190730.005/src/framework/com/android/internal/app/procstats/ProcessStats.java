/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package com.android.internal.app.procstats;

import android.content.ComponentName;
import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.LongSparseArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.ProcessMap;
import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ServiceState;
import com.android.internal.app.procstats.SparseMappingTable;
import com.android.internal.app.procstats.SysMemUsageTable;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;

public final class ProcessStats
implements Parcelable {
    public static final int ADD_PSS_EXTERNAL = 3;
    public static final int ADD_PSS_EXTERNAL_SLOW = 4;
    public static final int ADD_PSS_INTERNAL_ALL_MEM = 1;
    public static final int ADD_PSS_INTERNAL_ALL_POLL = 2;
    public static final int ADD_PSS_INTERNAL_SINGLE = 0;
    public static final int ADJ_COUNT = 8;
    public static final int ADJ_MEM_FACTOR_COUNT = 4;
    public static final int ADJ_MEM_FACTOR_CRITICAL = 3;
    public static final int ADJ_MEM_FACTOR_LOW = 2;
    public static final int ADJ_MEM_FACTOR_MODERATE = 1;
    public static final int ADJ_MEM_FACTOR_NORMAL = 0;
    public static final int ADJ_NOTHING = -1;
    public static final int ADJ_SCREEN_MOD = 4;
    public static final int ADJ_SCREEN_OFF = 0;
    public static final int ADJ_SCREEN_ON = 4;
    public static final int[] ALL_MEM_ADJ;
    public static final int[] ALL_PROC_STATES;
    public static final int[] ALL_SCREEN_ADJ;
    public static final int[] BACKGROUND_PROC_STATES;
    static final int[] BAD_TABLE;
    public static long COMMIT_PERIOD = 0L;
    public static long COMMIT_UPTIME_PERIOD = 0L;
    public static final Parcelable.Creator<ProcessStats> CREATOR;
    static final boolean DEBUG = false;
    static final boolean DEBUG_PARCEL = false;
    public static final int FLAG_COMPLETE = 1;
    public static final int FLAG_SHUTDOWN = 2;
    public static final int FLAG_SYSPROPS = 4;
    private static final long INVERSE_PROC_STATE_WARNING_MIN_INTERVAL_MS = 10000L;
    private static final int MAGIC = 1347638356;
    public static final int[] NON_CACHED_PROC_STATES;
    public static final int[] OPTIONS;
    public static final String[] OPTIONS_STR;
    private static final int PARCEL_VERSION = 36;
    public static final int PSS_AVERAGE = 2;
    public static final int PSS_COUNT = 10;
    public static final int PSS_MAXIMUM = 3;
    public static final int PSS_MINIMUM = 1;
    public static final int PSS_RSS_AVERAGE = 8;
    public static final int PSS_RSS_MAXIMUM = 9;
    public static final int PSS_RSS_MINIMUM = 7;
    public static final int PSS_SAMPLE_COUNT = 0;
    public static final int PSS_USS_AVERAGE = 5;
    public static final int PSS_USS_MAXIMUM = 6;
    public static final int PSS_USS_MINIMUM = 4;
    public static final int REPORT_ALL = 15;
    public static final int REPORT_PKG_ASC_STATS = 8;
    public static final int REPORT_PKG_PROC_STATS = 2;
    public static final int REPORT_PKG_STATS = 14;
    public static final int REPORT_PKG_SVC_STATS = 4;
    public static final int REPORT_PROC_STATS = 1;
    public static final String SERVICE_NAME = "procstats";
    public static final int STATE_BACKUP = 4;
    public static final int STATE_CACHED_ACTIVITY = 11;
    public static final int STATE_CACHED_ACTIVITY_CLIENT = 12;
    public static final int STATE_CACHED_EMPTY = 13;
    public static final int STATE_COUNT = 14;
    public static final int STATE_HEAVY_WEIGHT = 8;
    public static final int STATE_HOME = 9;
    public static final int STATE_IMPORTANT_BACKGROUND = 3;
    public static final int STATE_IMPORTANT_FOREGROUND = 2;
    public static final int STATE_LAST_ACTIVITY = 10;
    public static final int STATE_NOTHING = -1;
    public static final int STATE_PERSISTENT = 0;
    public static final int STATE_RECEIVER = 7;
    public static final int STATE_SERVICE = 5;
    public static final int STATE_SERVICE_RESTARTING = 6;
    public static final int STATE_TOP = 1;
    public static final int SYS_MEM_USAGE_CACHED_AVERAGE = 2;
    public static final int SYS_MEM_USAGE_CACHED_MAXIMUM = 3;
    public static final int SYS_MEM_USAGE_CACHED_MINIMUM = 1;
    public static final int SYS_MEM_USAGE_COUNT = 16;
    public static final int SYS_MEM_USAGE_FREE_AVERAGE = 5;
    public static final int SYS_MEM_USAGE_FREE_MAXIMUM = 6;
    public static final int SYS_MEM_USAGE_FREE_MINIMUM = 4;
    public static final int SYS_MEM_USAGE_KERNEL_AVERAGE = 11;
    public static final int SYS_MEM_USAGE_KERNEL_MAXIMUM = 12;
    public static final int SYS_MEM_USAGE_KERNEL_MINIMUM = 10;
    public static final int SYS_MEM_USAGE_NATIVE_AVERAGE = 14;
    public static final int SYS_MEM_USAGE_NATIVE_MAXIMUM = 15;
    public static final int SYS_MEM_USAGE_NATIVE_MINIMUM = 13;
    public static final int SYS_MEM_USAGE_SAMPLE_COUNT = 0;
    public static final int SYS_MEM_USAGE_ZRAM_AVERAGE = 8;
    public static final int SYS_MEM_USAGE_ZRAM_MAXIMUM = 9;
    public static final int SYS_MEM_USAGE_ZRAM_MINIMUM = 7;
    public static final String TAG = "ProcessStats";
    private static final Pattern sPageTypeRegex;
    ArrayMap<String, Integer> mCommonStringToIndex;
    public long mExternalPssCount;
    public long mExternalPssTime;
    public long mExternalSlowPssCount;
    public long mExternalSlowPssTime;
    public int mFlags;
    boolean mHasSwappedOutPss;
    ArrayList<String> mIndexToCommonString;
    public long mInternalAllMemPssCount;
    public long mInternalAllMemPssTime;
    public long mInternalAllPollPssCount;
    public long mInternalAllPollPssTime;
    public long mInternalSinglePssCount;
    public long mInternalSinglePssTime;
    public int mMemFactor = -1;
    public final long[] mMemFactorDurations = new long[8];
    private long mNextInverseProcStateWarningUptime;
    public final ProcessMap<LongSparseArray<PackageState>> mPackages = new ProcessMap();
    private final ArrayList<String> mPageTypeLabels = new ArrayList();
    private final ArrayList<Integer> mPageTypeNodes = new ArrayList();
    private final ArrayList<int[]> mPageTypeSizes = new ArrayList();
    private final ArrayList<String> mPageTypeZones = new ArrayList();
    public final ProcessMap<ProcessState> mProcesses = new ProcessMap();
    public String mReadError;
    boolean mRunning;
    String mRuntime;
    private int mSkippedInverseProcStateWarningCount;
    public long mStartTime;
    public final SysMemUsageTable mSysMemUsage = new SysMemUsageTable(this.mTableData);
    public final long[] mSysMemUsageArgs = new long[16];
    public final SparseMappingTable mTableData = new SparseMappingTable();
    public long mTimePeriodEndRealtime;
    public long mTimePeriodEndUptime;
    public long mTimePeriodStartClock;
    public String mTimePeriodStartClockStr;
    public long mTimePeriodStartRealtime;
    public long mTimePeriodStartUptime;
    public final ArrayList<AssociationState.SourceState> mTrackingAssociations = new ArrayList();

    static {
        COMMIT_PERIOD = 10800000L;
        COMMIT_UPTIME_PERIOD = 3600000L;
        ALL_MEM_ADJ = new int[]{0, 1, 2, 3};
        ALL_SCREEN_ADJ = new int[]{0, 4};
        NON_CACHED_PROC_STATES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        BACKGROUND_PROC_STATES = new int[]{2, 3, 4, 8, 5, 6, 7};
        ALL_PROC_STATES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        OPTIONS = new int[]{1, 2, 4, 8, 14, 15};
        OPTIONS_STR = new String[]{"proc", "pkg-proc", "pkg-svc", "pkg-asc", "pkg-all", "all"};
        sPageTypeRegex = Pattern.compile("^Node\\s+(\\d+),.* zone\\s+(\\w+),.* type\\s+(\\w+)\\s+([\\s\\d]+?)\\s*$");
        CREATOR = new Parcelable.Creator<ProcessStats>(){

            @Override
            public ProcessStats createFromParcel(Parcel parcel) {
                return new ProcessStats(parcel);
            }

            public ProcessStats[] newArray(int n) {
                return new ProcessStats[n];
            }
        };
        BAD_TABLE = new int[0];
    }

    public ProcessStats(Parcel parcel) {
        this.reset();
        this.readFromParcel(parcel);
    }

    public ProcessStats(boolean bl) {
        this.mRunning = bl;
        this.reset();
        if (bl) {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(Process.myPid(), memoryInfo);
            this.mHasSwappedOutPss = memoryInfo.hasSwappedOutPss();
        }
    }

    private void buildTimePeriodStartClockStr() {
        this.mTimePeriodStartClockStr = DateFormat.format((CharSequence)"yyyy-MM-dd-HH-mm-ss", this.mTimePeriodStartClock).toString();
    }

    private void dumpFragmentationLocked(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("Available pages by page size:");
        int n = this.mPageTypeLabels.size();
        for (int i = 0; i < n; ++i) {
            printWriter.format("Node %3d Zone %7s  %14s ", this.mPageTypeNodes.get(i), this.mPageTypeZones.get(i), this.mPageTypeLabels.get(i));
            int[] arrn = this.mPageTypeSizes.get(i);
            int n2 = arrn == null ? 0 : arrn.length;
            for (int j = 0; j < n2; ++j) {
                printWriter.format("%6d", arrn[j]);
            }
            printWriter.println();
        }
    }

    private boolean readCheckedInt(Parcel object, int n, String string2) {
        int n2 = ((Parcel)object).readInt();
        if (n2 != n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("bad ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(n2);
            this.mReadError = ((StringBuilder)object).toString();
            return false;
        }
        return true;
    }

    private void readCompactedLongArray(Parcel object, int n, long[] arrl, int n2) {
        if (n <= 10) {
            ((Parcel)object).readLongArray(arrl);
            return;
        }
        int n3 = arrl.length;
        if (n2 <= n3) {
            int n4;
            n = 0;
            do {
                if (n >= n2) break;
                int n5 = ((Parcel)object).readInt();
                if (n5 >= 0) {
                    arrl[n] = n5;
                    continue;
                }
                n4 = ((Parcel)object).readInt();
                arrl[n] = (long)n5 << 32 | (long)n4;
            } while (true);
            for (n4 = ++n; n4 < n3; ++n4) {
                arrl[n4] = 0L;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bad array lengths: got ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" array is ");
        ((StringBuilder)object).append(n3);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    static byte[] readFully(InputStream inputStream, int[] arrn) throws IOException {
        int n = 0;
        int n2 = inputStream.available();
        n2 = n2 > 0 ? ++n2 : 16384;
        byte[] arrby = new byte[n2];
        n2 = n;
        do {
            if ((n = inputStream.read(arrby, n2, arrby.length - n2)) < 0) {
                arrn[0] = n2;
                return arrby;
            }
            byte[] arrby2 = arrby;
            if ((n2 += n) >= arrby.length) {
                arrby2 = new byte[n2 + 16384];
                System.arraycopy(arrby, 0, arrby2, 0, n2);
            }
            arrby = arrby2;
        } while (true);
    }

    private void resetCommon() {
        long l;
        this.mTimePeriodStartClock = System.currentTimeMillis();
        this.buildTimePeriodStartClockStr();
        this.mTimePeriodEndRealtime = l = SystemClock.elapsedRealtime();
        this.mTimePeriodStartRealtime = l;
        this.mTimePeriodEndUptime = l = SystemClock.uptimeMillis();
        this.mTimePeriodStartUptime = l;
        this.mInternalSinglePssCount = 0L;
        this.mInternalSinglePssTime = 0L;
        this.mInternalAllMemPssCount = 0L;
        this.mInternalAllMemPssTime = 0L;
        this.mInternalAllPollPssCount = 0L;
        this.mInternalAllPollPssTime = 0L;
        this.mExternalPssCount = 0L;
        this.mExternalPssTime = 0L;
        this.mExternalSlowPssCount = 0L;
        this.mExternalSlowPssTime = 0L;
        this.mTableData.reset();
        Arrays.fill(this.mMemFactorDurations, 0L);
        this.mSysMemUsage.resetTable();
        this.mStartTime = 0L;
        this.mReadError = null;
        this.mFlags = 0;
        this.evaluateSystemProperties(true);
        this.updateFragmentation();
    }

    private static int[] splitAndParseNumbers(String string2) {
        int n;
        int n2;
        int n3;
        int n4 = 0;
        int n5 = 0;
        int n6 = string2.length();
        for (n3 = 0; n3 < n6; ++n3) {
            n = string2.charAt(n3);
            if (n >= 48 && n <= 57) {
                n = n4;
                n2 = n5;
                if (n4 == 0) {
                    n = 1;
                    n2 = n5 + 1;
                }
            } else {
                n = 0;
                n2 = n5;
            }
            n4 = n;
            n5 = n2;
        }
        int[] arrn = new int[n5];
        n2 = 0;
        int n7 = 0;
        int n8 = n4;
        for (n3 = 0; n3 < n6; ++n3) {
            int n9;
            n = string2.charAt(n3);
            if (n >= 48 && n <= 57) {
                if (n8 == 0) {
                    n4 = 1;
                    n -= 48;
                    n9 = n2;
                } else {
                    n = n7 * 10 + (n - 48);
                    n4 = n8;
                    n9 = n2;
                }
            } else {
                n4 = n8;
                n9 = n2;
                n = n7;
                if (n8 != 0) {
                    n4 = 0;
                    arrn[n2] = n7;
                    n9 = n2 + 1;
                    n = n7;
                }
            }
            n8 = n4;
            n2 = n9;
            n7 = n;
        }
        if (n5 > 0) {
            arrn[n5 - 1] = n7;
        }
        return arrn;
    }

    private void writeCompactedLongArray(Parcel parcel, long[] arrl, int n) {
        for (int i = 0; i < n; ++i) {
            long l;
            long l2 = l = arrl[i];
            if (l < 0L) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Time val negative: ");
                stringBuilder.append(l);
                Slog.w(TAG, stringBuilder.toString());
                l2 = 0L;
            }
            if (l2 <= Integer.MAX_VALUE) {
                parcel.writeInt((int)l2);
                continue;
            }
            int n2 = (int)(Integer.MAX_VALUE & l2 >> 32);
            int n3 = (int)(0xFFFFFFFFL & l2);
            parcel.writeInt(n2);
            parcel.writeInt(n3);
        }
    }

    public void add(ProcessStats processStats) {
        Object object;
        long[] arrl;
        String string2;
        Object object2;
        int n;
        int n2;
        Object object3;
        int n3;
        long l;
        Object object4;
        Object object5 = processStats.mPackages.getMap();
        for (n = 0; n < ((ArrayMap)object5).size(); ++n) {
            string2 = ((ArrayMap)object5).keyAt(n);
            arrl = ((ArrayMap)object5).valueAt(n);
            for (n2 = 0; n2 < arrl.size(); ++n2) {
                int n4 = arrl.keyAt(n2);
                object4 = (LongSparseArray)arrl.valueAt(n2);
                for (int i = 0; i < ((LongSparseArray)object4).size(); ++i) {
                    l = ((LongSparseArray)object4).keyAt(i);
                    object = (PackageState)((LongSparseArray)object4).valueAt(i);
                    int n5 = ((PackageState)object).mProcesses.size();
                    n3 = ((PackageState)object).mServices.size();
                    int n6 = ((PackageState)object).mAssociations.size();
                    for (int j = 0; j < n5; ++j) {
                        object2 = ((PackageState)object).mProcesses.valueAt(j);
                        if (((ProcessState)object2).getCommonProcess() == object2) continue;
                        object3 = this.getProcessStateLocked(string2, n4, l, ((ProcessState)object2).getName());
                        if (((ProcessState)object3).getCommonProcess() == object3) {
                            ((ProcessState)object3).setMultiPackage(true);
                            long l2 = SystemClock.uptimeMillis();
                            PackageState packageState = this.getPackageStateLocked(string2, n4, l);
                            object3 = ((ProcessState)object3).clone(l2);
                            packageState.mProcesses.put(((ProcessState)object3).getName(), (ProcessState)object3);
                        }
                        ((ProcessState)object3).add((ProcessState)object2);
                    }
                    for (n5 = 0; n5 < n3; ++n5) {
                        object3 = ((PackageState)object).mServices.valueAt(n5);
                        this.getServiceStateLocked(string2, n4, l, ((ServiceState)object3).getProcessName(), ((ServiceState)object3).getName()).add((ServiceState)object3);
                    }
                    for (n3 = 0; n3 < n6; ++n3) {
                        object3 = ((PackageState)object).mAssociations.valueAt(n3);
                        this.getAssociationStateLocked(string2, n4, l, ((AssociationState)object3).getProcessName(), ((AssociationState)object3).getName()).add((AssociationState)object3);
                    }
                }
            }
        }
        arrl = processStats.mProcesses.getMap();
        for (n3 = 0; n3 < arrl.size(); ++n3) {
            object = (SparseArray)arrl.valueAt(n3);
            for (n = 0; n < ((SparseArray)object).size(); ++n) {
                n2 = ((SparseArray)object).keyAt(n);
                object3 = (ProcessState)((SparseArray)object).valueAt(n);
                string2 = ((ProcessState)object3).getName();
                object2 = ((ProcessState)object3).getPackage();
                l = ((ProcessState)object3).getVersion();
                object4 = this.mProcesses.get(string2, n2);
                if (object4 == null) {
                    object5 = new ProcessState(this, (String)object2, n2, l, string2);
                    this.mProcesses.put(string2, n2, (ProcessState)object5);
                    object2 = this.getPackageStateLocked((String)object2, n2, l);
                    object4 = object5;
                    if (!((PackageState)object2).mProcesses.containsKey(string2)) {
                        ((PackageState)object2).mProcesses.put(string2, (ProcessState)object5);
                        object4 = object5;
                    }
                }
                ((ProcessState)object4).add((ProcessState)object3);
            }
        }
        for (n3 = 0; n3 < 8; ++n3) {
            arrl = this.mMemFactorDurations;
            arrl[n3] = arrl[n3] + processStats.mMemFactorDurations[n3];
        }
        this.mSysMemUsage.mergeStats(processStats.mSysMemUsage);
        l = processStats.mTimePeriodStartClock;
        if (l < this.mTimePeriodStartClock) {
            this.mTimePeriodStartClock = l;
            this.mTimePeriodStartClockStr = processStats.mTimePeriodStartClockStr;
        }
        this.mTimePeriodEndRealtime += processStats.mTimePeriodEndRealtime - processStats.mTimePeriodStartRealtime;
        this.mTimePeriodEndUptime += processStats.mTimePeriodEndUptime - processStats.mTimePeriodStartUptime;
        this.mInternalSinglePssCount += processStats.mInternalSinglePssCount;
        this.mInternalSinglePssTime += processStats.mInternalSinglePssTime;
        this.mInternalAllMemPssCount += processStats.mInternalAllMemPssCount;
        this.mInternalAllMemPssTime += processStats.mInternalAllMemPssTime;
        this.mInternalAllPollPssCount += processStats.mInternalAllPollPssCount;
        this.mInternalAllPollPssTime += processStats.mInternalAllPollPssTime;
        this.mExternalPssCount += processStats.mExternalPssCount;
        this.mExternalPssTime += processStats.mExternalPssTime;
        this.mExternalSlowPssCount += processStats.mExternalSlowPssCount;
        this.mExternalSlowPssTime += processStats.mExternalSlowPssTime;
        this.mHasSwappedOutPss |= processStats.mHasSwappedOutPss;
    }

    public void addSysMemUsage(long l, long l2, long l3, long l4, long l5) {
        int n = this.mMemFactor;
        if (n != -1) {
            this.mSysMemUsageArgs[0] = 1L;
            for (int i = 0; i < 3; ++i) {
                long[] arrl = this.mSysMemUsageArgs;
                arrl[i + 1] = l;
                arrl[i + 4] = l2;
                arrl[i + 7] = l3;
                arrl[i + 10] = l4;
                arrl[i + 13] = l5;
            }
            this.mSysMemUsage.mergeStats(n * 14, this.mSysMemUsageArgs, 0);
        }
    }

    public ArrayList<ProcessState> collectProcessesLocked(int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4, long l, String object, boolean bl) {
        Object object2;
        int n;
        ArraySet<ProcessState> arraySet = new ArraySet<ProcessState>();
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap = this.mPackages.getMap();
        for (n = 0; n < arrayMap.size(); ++n) {
            String string2 = arrayMap.keyAt(n);
            object2 = arrayMap.valueAt(n);
            for (int i = 0; i < ((SparseArray)object2).size(); ++i) {
                LongSparseArray<PackageState> longSparseArray = ((SparseArray)object2).valueAt(i);
                int n2 = longSparseArray.size();
                for (int j = 0; j < n2; ++j) {
                    PackageState packageState = longSparseArray.valueAt(j);
                    int n3 = packageState.mProcesses.size();
                    boolean bl2 = object == null || ((String)object).equals(string2);
                    for (int k = 0; k < n3; ++k) {
                        ProcessState processState = packageState.mProcesses.valueAt(k);
                        if (!bl2 && !((String)object).equals(processState.getName()) || bl && !processState.isInUse()) continue;
                        arraySet.add(processState.getCommonProcess());
                    }
                }
            }
        }
        object = new ArrayList(arraySet.size());
        for (n = 0; n < arraySet.size(); ++n) {
            object2 = (ProcessState)arraySet.valueAt(n);
            if (((ProcessState)object2).computeProcessTimeLocked(arrn, arrn2, arrn3, l) <= 0L) continue;
            ((ArrayList)object).add(object2);
            if (arrn3 == arrn4) continue;
            ((ProcessState)object2).computeProcessTimeLocked(arrn, arrn2, arrn4, l);
        }
        Collections.sort(object, ProcessState.COMPARATOR);
        return object;
    }

    public void computeTotalMemoryUse(TotalMemoryUseCollection totalMemoryUseCollection, long l) {
        long[] arrl;
        int n;
        int n2;
        totalMemoryUseCollection.totalTime = 0L;
        for (n2 = 0; n2 < 14; ++n2) {
            totalMemoryUseCollection.processStateWeight[n2] = 0.0;
            totalMemoryUseCollection.processStatePss[n2] = 0L;
            totalMemoryUseCollection.processStateTime[n2] = 0L;
            totalMemoryUseCollection.processStateSamples[n2] = 0;
        }
        for (n2 = 0; n2 < 16; ++n2) {
            totalMemoryUseCollection.sysMemUsage[n2] = 0L;
        }
        totalMemoryUseCollection.sysMemCachedWeight = 0.0;
        totalMemoryUseCollection.sysMemFreeWeight = 0.0;
        totalMemoryUseCollection.sysMemZRamWeight = 0.0;
        totalMemoryUseCollection.sysMemKernelWeight = 0.0;
        totalMemoryUseCollection.sysMemNativeWeight = 0.0;
        totalMemoryUseCollection.sysMemSamples = 0;
        Object object = this.mSysMemUsage.getTotalMemUsage();
        for (n2 = 0; n2 < totalMemoryUseCollection.screenStates.length; ++n2) {
            for (n = 0; n < totalMemoryUseCollection.memStates.length; ++n) {
                long l2;
                int n3 = totalMemoryUseCollection.screenStates[n2] + totalMemoryUseCollection.memStates[n];
                long l3 = l2 = this.mMemFactorDurations[n3];
                if (this.mMemFactor == n3) {
                    l3 = l2 + (l - this.mStartTime);
                }
                totalMemoryUseCollection.totalTime += l3;
                int n4 = this.mSysMemUsage.getKey((byte)(n3 * 14));
                arrl = object;
                n3 = 0;
                if (n4 != -1) {
                    long[] arrl2 = this.mSysMemUsage.getArrayForKey(n4);
                    if (arrl2[(n4 = SparseMappingTable.getIndexFromKey(n4)) + 0] >= 3L) {
                        SysMemUsageTable.mergeSysMemUsage(totalMemoryUseCollection.sysMemUsage, 0, arrl, 0);
                        arrl = arrl2;
                        n3 = n4;
                    }
                }
                totalMemoryUseCollection.sysMemCachedWeight += (double)arrl[n3 + 2] * (double)l3;
                totalMemoryUseCollection.sysMemFreeWeight += (double)arrl[n3 + 5] * (double)l3;
                totalMemoryUseCollection.sysMemZRamWeight += (double)arrl[n3 + 8] * (double)l3;
                totalMemoryUseCollection.sysMemKernelWeight += (double)arrl[n3 + 11] * (double)l3;
                totalMemoryUseCollection.sysMemNativeWeight += (double)arrl[n3 + 14] * (double)l3;
                totalMemoryUseCollection.sysMemSamples = (int)((long)totalMemoryUseCollection.sysMemSamples + arrl[n3 + 0]);
            }
        }
        totalMemoryUseCollection.hasSwappedOutPss = this.mHasSwappedOutPss;
        object = this.mProcesses.getMap();
        for (n2 = 0; n2 < ((ArrayMap)object).size(); ++n2) {
            arrl = (SparseArray)((ArrayMap)object).valueAt(n2);
            for (n = 0; n < arrl.size(); ++n) {
                ((ProcessState)arrl.valueAt(n)).aggregatePss(totalMemoryUseCollection, l);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dumpCheckinLocked(PrintWriter printWriter, String object, int n) {
        int n2;
        Object object2;
        int n3;
        long l = SystemClock.uptimeMillis();
        int[] arrn = this.mPackages.getMap();
        printWriter.println("vers,5");
        printWriter.print("period,");
        printWriter.print(this.mTimePeriodStartClockStr);
        int[] arrn2 = ",";
        printWriter.print(",");
        printWriter.print(this.mTimePeriodStartRealtime);
        printWriter.print(",");
        long l2 = this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime;
        printWriter.print(l2);
        int n4 = 1;
        if ((this.mFlags & 2) != 0) {
            printWriter.print(",shutdown");
            n4 = 0;
        }
        if ((this.mFlags & 4) != 0) {
            printWriter.print(",sysprops");
            n4 = 0;
        }
        if ((this.mFlags & 1) != 0) {
            printWriter.print(",complete");
            n4 = 0;
        }
        if (n4 != 0) {
            printWriter.print(",partial");
        }
        if (this.mHasSwappedOutPss) {
            printWriter.print(",swapped-out-pss");
        }
        printWriter.println();
        printWriter.print("config,");
        printWriter.println(this.mRuntime);
        if ((n & 14) != 0) {
            n4 = 0;
            do {
                Object object3 = object;
                if (n4 >= arrn.size()) break;
                object2 = arrn.keyAt(n4);
                if (object3 == null || ((String)object3).equals(object2)) {
                    Object object4 = arrn.valueAt(n4);
                    for (n3 = 0; n3 < ((SparseArray)object4).size(); ++n3) {
                        int n5 = ((SparseArray)object4).keyAt(n3);
                        Object object5 = ((SparseArray)object4).valueAt(n3);
                        object3 = arrn2;
                        arrn2 = object5;
                        for (int i = 0; i < arrn2.size(); ++i) {
                            Object object6;
                            l2 = arrn2.keyAt(i);
                            Object object7 = (PackageState)arrn2.valueAt(i);
                            int n6 = ((PackageState)object7).mProcesses.size();
                            int n7 = ((PackageState)object7).mServices.size();
                            int n8 = ((PackageState)object7).mAssociations.size();
                            if ((n & 2) != 0) {
                                n2 = n3;
                                object5 = arrn2;
                                n3 = n8;
                                arrn2 = object7;
                                n8 = n6;
                                for (int j = 0; j < n8; ++j) {
                                    arrn2.mProcesses.valueAt(j).dumpPackageProcCheckin(printWriter, (String)object2, n5, l2, arrn2.mProcesses.keyAt(j), l);
                                }
                                object7 = object4;
                                n8 = n2;
                                object4 = object2;
                                object2 = object3;
                                object6 = arrn2;
                                n2 = n3;
                                n3 = n4;
                                object3 = arrn;
                                arrn = object5;
                                arrn2 = object7;
                                n4 = n8;
                            } else {
                                object5 = arrn2;
                                arrn2 = object4;
                                object4 = object2;
                                n2 = n4;
                                object6 = arrn;
                                object2 = object3;
                                n4 = n3;
                                arrn = object5;
                                object3 = object6;
                                n3 = n2;
                                n2 = n8;
                                object6 = object7;
                            }
                            if ((n & 4) != 0) {
                                for (n8 = 0; n8 < n7; ++n8) {
                                    object5 = DumpUtils.collapseString((String)object4, ((PackageState)object6).mServices.keyAt(n8));
                                    ((PackageState)object6).mServices.valueAt(n8).dumpTimesCheckin(printWriter, (String)object4, n5, l2, (String)object5, l);
                                }
                            }
                            if ((n & 8) != 0) {
                                for (n7 = 0; n7 < n2; ++n7) {
                                    object5 = DumpUtils.collapseString((String)object4, ((PackageState)object6).mAssociations.keyAt(n7));
                                    ((PackageState)object6).mAssociations.valueAt(n7).dumpTimesCheckin(printWriter, (String)object4, n5, l2, (String)object5, l);
                                }
                            }
                            object7 = object4;
                            n2 = n3;
                            object5 = object2;
                            object4 = arrn2;
                            arrn2 = arrn;
                            n3 = n4;
                            object2 = object7;
                            n4 = n2;
                            arrn = object3;
                            object3 = object5;
                        }
                        arrn2 = object3;
                    }
                }
                ++n4;
            } while (true);
            object = arrn2;
        } else {
            object = ",";
        }
        if ((n & 1) != 0) {
            arrn2 = this.mProcesses.getMap();
            for (n = 0; n < arrn2.size(); ++n) {
                arrn = arrn2.keyAt(n);
                object2 = arrn2.valueAt(n);
                for (n4 = 0; n4 < ((SparseArray)object2).size(); ++n4) {
                    n3 = ((SparseArray)object2).keyAt(n4);
                    ((ProcessState)((SparseArray)object2).valueAt(n4)).dumpProcCheckin(printWriter, (String)arrn, n3, l);
                }
            }
        }
        arrn2 = this;
        printWriter.print("total");
        DumpUtils.dumpAdjTimesCheckin(printWriter, ",", arrn2.mMemFactorDurations, arrn2.mMemFactor, arrn2.mStartTime, l);
        printWriter.println();
        n3 = arrn2.mSysMemUsage.getKeyCount();
        if (n3 > 0) {
            printWriter.print("sysmemusage");
            for (n = 0; n < n3; ++n) {
                n2 = arrn2.mSysMemUsage.getKeyAt(n);
                n4 = SparseMappingTable.getIdFromKey(n2);
                printWriter.print((String)object);
                DumpUtils.printProcStateTag(printWriter, n4);
                for (n4 = 0; n4 < 16; ++n4) {
                    if (n4 > 1) {
                        printWriter.print(":");
                    }
                    printWriter.print(arrn2.mSysMemUsage.getValue(n2, n4));
                }
            }
        }
        printWriter.println();
        arrn = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        arrn2.computeTotalMemoryUse((TotalMemoryUseCollection)arrn, l);
        printWriter.print("weights,");
        printWriter.print(arrn.totalTime);
        printWriter.print((String)object);
        printWriter.print(arrn.sysMemCachedWeight);
        printWriter.print(":");
        printWriter.print(arrn.sysMemSamples);
        printWriter.print((String)object);
        printWriter.print(arrn.sysMemFreeWeight);
        printWriter.print(":");
        printWriter.print(arrn.sysMemSamples);
        printWriter.print((String)object);
        printWriter.print(arrn.sysMemZRamWeight);
        printWriter.print(":");
        printWriter.print(arrn.sysMemSamples);
        printWriter.print((String)object);
        printWriter.print(arrn.sysMemKernelWeight);
        printWriter.print(":");
        printWriter.print(arrn.sysMemSamples);
        printWriter.print((String)object);
        printWriter.print(arrn.sysMemNativeWeight);
        printWriter.print(":");
        printWriter.print(arrn.sysMemSamples);
        for (n = 0; n < 14; ++n) {
            printWriter.print((String)object);
            printWriter.print(arrn.processStateWeight[n]);
            printWriter.print(":");
            printWriter.print(arrn.processStateSamples[n]);
        }
        printWriter.println();
        n2 = arrn2.mPageTypeLabels.size();
        for (n = 0; n < n2; ++n) {
            printWriter.print("availablepages,");
            printWriter.print(arrn2.mPageTypeLabels.get(n));
            printWriter.print((String)object);
            printWriter.print(arrn2.mPageTypeZones.get(n));
            printWriter.print((String)object);
            arrn = arrn2.mPageTypeSizes.get(n);
            n4 = arrn == null ? 0 : arrn.length;
            for (n3 = 0; n3 < n4; ++n3) {
                if (n3 != 0) {
                    printWriter.print((String)object);
                }
                printWriter.print(arrn[n3]);
            }
            printWriter.println();
        }
    }

    void dumpFilteredSummaryLocked(PrintWriter printWriter, String string2, String string3, String string4, int[] arrn, int[] arrn2, int[] object, int[] arrn3, long l, long l2, String string5, boolean bl) {
        if (((ArrayList)(object = this.collectProcessesLocked(arrn, arrn2, (int[])object, arrn3, l, string5, bl))).size() > 0) {
            if (string2 != null) {
                printWriter.println();
                printWriter.println(string2);
            }
            DumpUtils.dumpProcessSummaryLocked(printWriter, string3, string4, (ArrayList<ProcessState>)object, arrn, arrn2, arrn3, l, l2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void dumpLocked(PrintWriter var1_1, String var2_2, long var3_3, boolean var5_4, boolean var6_5, boolean var7_6, boolean var8_7, int var9_8) {
        block52 : {
            block51 : {
                var10_9 = var7_6;
                var11_10 = DumpUtils.dumpSingleTime(null, null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, var3_3);
                var13_11 = 0;
                if (this.mSysMemUsage.getKeyCount() > 0) {
                    var1_1.println("System memory usage:");
                    this.mSysMemUsage.dump(var1_1, "  ", ProcessStats.ALL_SCREEN_ADJ, ProcessStats.ALL_MEM_ADJ);
                    var13_11 = 1;
                }
                var14_12 = 0;
                var15_13 = 0;
                var16_14 = "      (Not active: ";
                var17_15 = "        ";
                var18_16 = " entries)";
                var19_17 = " / ";
                var20_18 = "  * ";
                var21_19 = ")";
                var22_20 = ":";
                if ((var9_8 & 14) == 0) break block51;
                var23_21 = this.mPackages.getMap();
                var14_12 = var13_11;
                var13_11 = var15_13;
                for (var24_22 = 0; var24_22 < var23_21.size(); ++var24_22) {
                    var25_23 = (String)var23_21.keyAt(var24_22);
                    var26_24 = (SparseArray)var23_21.valueAt(var24_22);
                    var27_25 = 0;
                    var28_26 = var17_15;
                    var29_27 = var16_14;
                    var30_28 = var18_16;
                    var31_29 = var19_17;
                    var32_30 = var20_18;
                    var19_17 = var21_19;
                    var17_15 = var22_20;
                    var18_16 = var23_21;
                    var23_21 = var25_23;
                    while (var27_25 < var26_24.size()) {
                        var33_31 = var26_24.keyAt(var27_25);
                        var25_23 = (LongSparseArray)var26_24.valueAt(var27_25);
                        var22_20 = var18_16;
                        var16_14 = var28_26;
                        var21_19 = var29_27;
                        var20_18 = var30_28;
                        var28_26 = var31_29;
                        var30_28 = var32_30;
                        var18_16 = var23_21;
                        var34_32 = var27_25;
                        var32_30 = var25_23;
                        for (var15_13 = 0; var15_13 < var32_30.size(); ++var15_13) {
                            var35_33 = var32_30.keyAt(var15_13);
                            var23_21 = (PackageState)var32_30.valueAt(var15_13);
                            var37_34 = var23_21.mProcesses.size();
                            var38_35 = var23_21.mServices.size();
                            var39_36 = var23_21.mAssociations.size();
                            var40_37 = var2_2 == null || var2_2.equals(var18_16);
                            var41_38 = 0;
                            var42_39 = 0;
                            if (var40_37) ** GOTO lbl-1000
                            var43_40 = 0;
                            for (var27_25 = 0; var27_25 < var37_34; ++var27_25) {
                                if (!var2_2.equals(var23_21.mProcesses.valueAt(var27_25).getName())) continue;
                                var43_40 = 1;
                                break;
                            }
                            var15_13 = var27_25 = var15_13;
                            if (var43_40 != 0) ** GOTO lbl-1000
                            var15_13 = 0;
                            do {
                                var43_40 = var42_39;
                                if (var15_13 >= var39_36) break;
                                if (var23_21.mAssociations.valueAt(var15_13).hasProcessOrPackage(var2_2)) {
                                    var43_40 = 1;
                                    break;
                                }
                                ++var15_13;
                            } while (true);
                            var41_38 = var43_40;
                            var15_13 = var27_25;
                            if (var43_40 == 0) {
                                var23_21 = var18_16;
                                var18_16 = var17_15;
                                var17_15 = var21_19;
                                var21_19 = var16_14;
                                var16_14 = var20_18;
                                var15_13 = var27_25;
                                var20_18 = var21_19;
                            } else lbl-1000: // 3 sources:
                            {
                                if (var37_34 > 0 || var38_35 > 0 || var39_36 > 0) {
                                    var43_40 = var14_12;
                                    var27_25 = var13_11;
                                    if (var13_11 == 0) {
                                        if (var14_12 != 0) {
                                            var1_1.println();
                                        }
                                        var1_1.println("Per-Package Stats:");
                                        var27_25 = 1;
                                        var43_40 = 1;
                                    }
                                    var1_1.print((String)var30_28);
                                    var1_1.print((String)var18_16);
                                    var1_1.print((String)var28_26);
                                    UserHandle.formatUid(var1_1, var33_31);
                                    var1_1.print(" / v");
                                    var1_1.print(var35_33);
                                    var1_1.println((String)var17_15);
                                    var14_12 = var43_40;
                                    var13_11 = var27_25;
                                }
                                if ((var9_8 & 2) != 0 && var41_38 == 0) {
                                    if (var5_4 && !var7_6) {
                                        var31_29 = new ArrayList<ProcessState>();
                                        for (var27_25 = 0; var27_25 < var37_34; ++var27_25) {
                                            var29_27 = var23_21.mProcesses.valueAt(var27_25);
                                            if (!var40_37 && !var2_2.equals(var29_27.getName()) || var8_7 && !var29_27.isInUse()) continue;
                                            var31_29.add((ProcessState)var29_27);
                                        }
                                        var29_27 = var18_16;
                                        DumpUtils.dumpProcessSummaryLocked(var1_1, "      ", "Prc ", var31_29, ProcessStats.ALL_SCREEN_ADJ, ProcessStats.ALL_MEM_ADJ, ProcessStats.NON_CACHED_PROC_STATES, var3_3, var11_10);
                                        var18_16 = var17_15;
                                        var17_15 = var16_14;
                                        var16_14 = var23_21;
                                        var23_21 = var29_27;
                                    } else {
                                        var29_27 = var18_16;
                                        var18_16 = var23_21;
                                        var27_25 = var37_34;
                                        for (var43_40 = 0; var43_40 < var27_25; ++var43_40) {
                                            var23_21 = var18_16.mProcesses.valueAt(var43_40);
                                            if (!var40_37 && !var2_2.equals(var23_21.getName())) continue;
                                            if (var8_7 && !var23_21.isInUse()) {
                                                var1_1.print((String)var21_19);
                                                var1_1.print(var18_16.mProcesses.keyAt(var43_40));
                                                var1_1.println((String)var19_17);
                                                continue;
                                            }
                                            var1_1.print("      Process ");
                                            var1_1.print(var18_16.mProcesses.keyAt(var43_40));
                                            if (var23_21.getCommonProcess().isMultiPackage()) {
                                                var1_1.print(" (multi, ");
                                            } else {
                                                var1_1.print(" (unique, ");
                                            }
                                            var1_1.print(var23_21.getDurationsBucketCount());
                                            var1_1.print((String)var20_18);
                                            var1_1.println((String)var17_15);
                                            var23_21.dumpProcessState(var1_1, "        ", ProcessStats.ALL_SCREEN_ADJ, ProcessStats.ALL_MEM_ADJ, ProcessStats.ALL_PROC_STATES, var3_3);
                                            var23_21.dumpPss(var1_1, "        ", ProcessStats.ALL_SCREEN_ADJ, ProcessStats.ALL_MEM_ADJ, ProcessStats.ALL_PROC_STATES, var3_3);
                                            var23_21.dumpInternalLocked(var1_1, (String)var16_14, var7_6);
                                        }
                                        var23_21 = var18_16;
                                        var18_16 = var17_15;
                                        var17_15 = var16_14;
                                        var16_14 = var23_21;
                                        var23_21 = var29_27;
                                    }
                                } else {
                                    var29_27 = var18_16;
                                    var18_16 = var17_15;
                                    var17_15 = var16_14;
                                    var16_14 = var23_21;
                                    var23_21 = var29_27;
                                }
                                var27_25 = var39_36;
                                var10_9 = var7_6;
                                var29_27 = var20_18;
                                var44_41 = var19_17;
                                var20_18 = "        Process: ";
                                if ((var9_8 & 4) != 0 && var41_38 == 0) {
                                    for (var43_40 = 0; var43_40 < var38_35; ++var43_40) {
                                        var31_29 = var16_14.mServices.valueAt(var43_40);
                                        if (!var40_37 && !var2_2.equals(var31_29.getProcessName())) continue;
                                        if (var8_7 && !var31_29.isInUse()) {
                                            var1_1.print("      (Not active service: ");
                                            var1_1.print(var16_14.mServices.keyAt(var43_40));
                                            var1_1.println(var44_41);
                                            continue;
                                        }
                                        if (var10_9) {
                                            var1_1.print("      Service ");
                                        } else {
                                            var1_1.print("      * Svc ");
                                        }
                                        var1_1.print(var16_14.mServices.keyAt(var43_40));
                                        var1_1.println((String)var18_16);
                                        var1_1.print((String)var20_18);
                                        var1_1.println(var31_29.getProcessName());
                                        var31_29.dumpStats(var1_1, "        ", "          ", "    ", var3_3, var11_10, var5_4, var7_6);
                                    }
                                    var25_23 = var29_27;
                                    var31_29 = var21_19;
                                    var21_19 = var16_14;
                                    var29_27 = var20_18;
                                    var16_14 = var25_23;
                                    var20_18 = var17_15;
                                    var17_15 = var31_29;
                                } else {
                                    var20_18 = var21_19;
                                    var31_29 = var17_15;
                                    var21_19 = var16_14;
                                    var25_23 = "        Process: ";
                                    var17_15 = var20_18;
                                    var20_18 = var31_29;
                                    var16_14 = var29_27;
                                    var29_27 = var25_23;
                                }
                                if ((var9_8 & 8) != 0) {
                                    for (var43_40 = 0; var43_40 < var27_25; ++var43_40) {
                                        var25_23 = var21_19.mAssociations.valueAt(var43_40);
                                        if (!var40_37 && !var2_2.equals(var25_23.getProcessName()) && (var41_38 == 0 || !var25_23.hasProcessOrPackage(var2_2))) continue;
                                        if (var8_7 && !var25_23.isInUse()) {
                                            var1_1.print("      (Not active association: ");
                                            var1_1.print(var21_19.mAssociations.keyAt(var43_40));
                                            var1_1.println(var44_41);
                                            continue;
                                        }
                                        if (var10_9) {
                                            var1_1.print("      Association ");
                                        } else {
                                            var1_1.print("      * Asc ");
                                        }
                                        var1_1.print(var21_19.mAssociations.keyAt(var43_40));
                                        var1_1.println((String)var18_16);
                                        var1_1.print((String)var29_27);
                                        var1_1.println(var25_23.getProcessName());
                                        var31_29 = var41_38 != 0 ? var2_2 : null;
                                        var25_23.dumpStats(var1_1, "        ", "          ", "    ", var3_3, var11_10, (String)var31_29, var6_5, var7_6);
                                    }
                                }
                            }
                            var29_27 = var19_17;
                            var19_17 = var18_16;
                            var10_9 = var7_6;
                            var18_16 = var23_21;
                            var21_19 = var16_14;
                            var16_14 = var20_18;
                            var23_21 = var17_15;
                            var17_15 = var19_17;
                            var19_17 = var29_27;
                            var20_18 = var21_19;
                            var21_19 = var23_21;
                        }
                        var27_25 = var34_32 + 1;
                        var23_21 = var18_16;
                        var18_16 = var22_20;
                        var32_30 = var30_28;
                        var31_29 = var28_26;
                        var30_28 = var20_18;
                        var29_27 = var21_19;
                        var28_26 = var16_14;
                    }
                    var23_21 = var18_16;
                    var22_20 = var17_15;
                    var21_19 = var19_17;
                    var20_18 = var32_30;
                    var19_17 = var31_29;
                    var18_16 = var30_28;
                    var16_14 = var29_27;
                    var17_15 = var28_26;
                }
                var23_21 = var22_20;
                var22_20 = var21_19;
                var29_27 = var19_17;
                var19_17 = var18_16;
                var21_19 = var16_14;
                var26_24 = var17_15;
                var17_15 = var22_20;
                var15_13 = var13_11;
                var13_11 = var14_12;
                var16_14 = var29_27;
                var18_16 = var20_18;
                var20_18 = var26_24;
                break block52;
            }
            var23_21 = ":";
            var17_15 = ")";
            var18_16 = "  * ";
            var16_14 = " / ";
            var19_17 = " entries)";
            var21_19 = "      (Not active: ";
            var20_18 = "        ";
            var15_13 = var14_12;
        }
        if ((var9_8 & 1) != 0) {
            var30_28 = this.mProcesses.getMap();
            var24_22 = 0;
            var15_13 = 0;
            var9_8 = 0;
            var29_27 = var19_17;
            var19_17 = var16_14;
            for (var14_12 = 0; var14_12 < var30_28.size(); ++var14_12) {
                var22_20 = (String)var30_28.keyAt(var14_12);
                var26_24 = (SparseArray)var30_28.valueAt(var14_12);
                var43_40 = 0;
                var16_14 = var21_19;
                var21_19 = var20_18;
                var27_25 = var13_11;
                var20_18 = var26_24;
                var13_11 = var43_40;
                while (var13_11 < var20_18.size()) {
                    var43_40 = var20_18.keyAt(var13_11);
                    var26_24 = (ProcessState)var20_18.valueAt(var13_11);
                    if (var26_24.hasAnyData() && var26_24.isMultiPackage() && (var2_2 == null || var2_2.equals(var22_20) || var2_2.equals(var26_24.getPackage()))) {
                        if (var27_25 != 0) {
                            var1_1.println();
                        }
                        var27_25 = 1;
                        if (var9_8 == 0) {
                            var1_1.println("Multi-Package Common Processes:");
                            var9_8 = 1;
                        }
                        if (var8_7 && !var26_24.isInUse()) {
                            var1_1.print((String)var16_14);
                            var1_1.print((String)var22_20);
                            var1_1.println((String)var17_15);
                        } else {
                            var1_1.print((String)var18_16);
                            var1_1.print((String)var22_20);
                            var1_1.print((String)var19_17);
                            UserHandle.formatUid(var1_1, var43_40);
                            var1_1.print(" (");
                            var1_1.print(var26_24.getDurationsBucketCount());
                            var1_1.print((String)var29_27);
                            var1_1.println((String)var23_21);
                            var26_24.dumpProcessState(var1_1, "        ", ProcessStats.ALL_SCREEN_ADJ, ProcessStats.ALL_MEM_ADJ, ProcessStats.ALL_PROC_STATES, var3_3);
                            var26_24.dumpPss(var1_1, "        ", ProcessStats.ALL_SCREEN_ADJ, ProcessStats.ALL_MEM_ADJ, ProcessStats.ALL_PROC_STATES, var3_3);
                            var26_24.dumpInternalLocked(var1_1, (String)var21_19, var10_9);
                        }
                        ++var24_22;
                    }
                    ++var13_11;
                    ++var15_13;
                }
                var13_11 = var27_25;
                var20_18 = var21_19;
                var21_19 = var16_14;
            }
            var1_1.print("  Total procs: ");
            var1_1.print(var24_22);
            var1_1.print(" shown of ");
            var1_1.print(var15_13);
            var1_1.println(" total");
        }
        var17_15 = this;
        var9_8 = var13_11;
        if (var10_9) {
            if (var13_11 != 0) {
                var1_1.println();
            }
            var9_8 = var14_12 = 1;
            if (var17_15.mTrackingAssociations.size() > 0) {
                var1_1.println();
                var1_1.println("Tracking associations:");
                var13_11 = 0;
                do {
                    var9_8 = var14_12;
                    if (var13_11 >= var17_15.mTrackingAssociations.size()) break;
                    var18_16 = var17_15.mTrackingAssociations.get(var13_11);
                    var16_14 = var18_16.getAssociationState();
                    var1_1.print("  #");
                    var1_1.print(var13_11);
                    var1_1.print(": ");
                    var1_1.print(var16_14.getProcessName());
                    var1_1.print("/");
                    UserHandle.formatUid(var1_1, var16_14.getUid());
                    var1_1.print(" <- ");
                    var1_1.print(var18_16.getProcessName());
                    var1_1.print("/");
                    UserHandle.formatUid(var1_1, var18_16.getUid());
                    var1_1.println((String)var23_21);
                    var1_1.print("    Tracking for: ");
                    TimeUtils.formatDuration(var3_3 - var18_16.mTrackingUptime, var1_1);
                    var1_1.println();
                    var1_1.print("    Component: ");
                    var1_1.print(new ComponentName(var16_14.getPackage(), var16_14.getName()).flattenToShortString());
                    var1_1.println();
                    var1_1.print("    Proc state: ");
                    if (var18_16.mProcState != -1) {
                        var1_1.print(DumpUtils.STATE_NAMES[var18_16.mProcState]);
                    } else {
                        var1_1.print("--");
                    }
                    var1_1.print(" #");
                    var1_1.println(var18_16.mProcStateSeq);
                    var1_1.print("    Process: ");
                    var1_1.println(var16_14.getProcess());
                    if (var18_16.mActiveCount > 0) {
                        var1_1.print("    Active count ");
                        var1_1.print(var18_16.mActiveCount);
                        var1_1.print(": ");
                        var16_14.dumpActiveDurationSummary(var1_1, (AssociationState.SourceState)var18_16, var11_10, var3_3, var7_6);
                        var1_1.println();
                    }
                    ++var13_11;
                } while (true);
            }
        }
        if (var9_8 != 0) {
            var1_1.println();
        }
        if (var5_4) {
            var1_1.println("Process summary:");
            this.dumpSummaryLocked(var1_1, var2_2, var3_3, var8_7);
        } else {
            var17_15.dumpTotalsLocked(var1_1, var3_3);
        }
        var16_14 = var1_1;
        if (var7_6) {
            var1_1.println();
            var16_14.println("Internal state:");
            var16_14.print("  mRunning=");
            var16_14.println(var17_15.mRunning);
        }
        if (var2_2 != null) return;
        this.dumpFragmentationLocked(var1_1);
    }

    public void dumpSummaryLocked(PrintWriter printWriter, String string2, long l, boolean bl) {
        long l2 = DumpUtils.dumpSingleTime(null, null, this.mMemFactorDurations, this.mMemFactor, this.mStartTime, l);
        this.dumpFilteredSummaryLocked(printWriter, null, "  ", null, ALL_SCREEN_ADJ, ALL_MEM_ADJ, ALL_PROC_STATES, NON_CACHED_PROC_STATES, l, l2, string2, bl);
        printWriter.println();
        this.dumpTotalsLocked(printWriter, l);
    }

    void dumpTotalsLocked(PrintWriter printWriter, long l) {
        int n;
        printWriter.println("Run time Stats:");
        DumpUtils.dumpSingleTime(printWriter, "  ", this.mMemFactorDurations, this.mMemFactor, this.mStartTime, l);
        printWriter.println();
        printWriter.println("Memory usage:");
        TotalMemoryUseCollection totalMemoryUseCollection = new TotalMemoryUseCollection(ALL_SCREEN_ADJ, ALL_MEM_ADJ);
        this.computeTotalMemoryUse(totalMemoryUseCollection, l);
        l = this.printMemoryCategory(printWriter, "  ", "Kernel ", totalMemoryUseCollection.sysMemKernelWeight, totalMemoryUseCollection.totalTime, 0L, totalMemoryUseCollection.sysMemSamples);
        l = this.printMemoryCategory(printWriter, "  ", "Native ", totalMemoryUseCollection.sysMemNativeWeight, totalMemoryUseCollection.totalTime, l, totalMemoryUseCollection.sysMemSamples);
        for (n = 0; n < 14; ++n) {
            if (n == 6) continue;
            l = this.printMemoryCategory(printWriter, "  ", DumpUtils.STATE_NAMES[n], totalMemoryUseCollection.processStateWeight[n], totalMemoryUseCollection.totalTime, l, totalMemoryUseCollection.processStateSamples[n]);
        }
        l = this.printMemoryCategory(printWriter, "  ", "Cached ", totalMemoryUseCollection.sysMemCachedWeight, totalMemoryUseCollection.totalTime, l, totalMemoryUseCollection.sysMemSamples);
        l = this.printMemoryCategory(printWriter, "  ", "Free   ", totalMemoryUseCollection.sysMemFreeWeight, totalMemoryUseCollection.totalTime, l, totalMemoryUseCollection.sysMemSamples);
        l = this.printMemoryCategory(printWriter, "  ", "Z-Ram  ", totalMemoryUseCollection.sysMemZRamWeight, totalMemoryUseCollection.totalTime, l, totalMemoryUseCollection.sysMemSamples);
        printWriter.print("  TOTAL  : ");
        DebugUtils.printSizeValue(printWriter, l);
        printWriter.println();
        this.printMemoryCategory(printWriter, "  ", DumpUtils.STATE_NAMES[6], totalMemoryUseCollection.processStateWeight[6], totalMemoryUseCollection.totalTime, l, totalMemoryUseCollection.processStateSamples[6]);
        printWriter.println();
        printWriter.println("PSS collection stats:");
        printWriter.print("  Internal Single: ");
        printWriter.print(this.mInternalSinglePssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalSinglePssTime, printWriter);
        printWriter.println();
        printWriter.print("  Internal All Procs (Memory Change): ");
        printWriter.print(this.mInternalAllMemPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllMemPssTime, printWriter);
        printWriter.println();
        printWriter.print("  Internal All Procs (Polling): ");
        printWriter.print(this.mInternalAllPollPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mInternalAllPollPssTime, printWriter);
        printWriter.println();
        printWriter.print("  External: ");
        printWriter.print(this.mExternalPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mExternalPssTime, printWriter);
        printWriter.println();
        printWriter.print("  External Slow: ");
        printWriter.print(this.mExternalSlowPssCount);
        printWriter.print("x over ");
        TimeUtils.formatDuration(this.mExternalSlowPssTime, printWriter);
        printWriter.println();
        printWriter.println();
        printWriter.print("          Start time: ");
        printWriter.print(DateFormat.format((CharSequence)"yyyy-MM-dd HH:mm:ss", this.mTimePeriodStartClock));
        printWriter.println();
        printWriter.print("        Total uptime: ");
        l = this.mRunning ? SystemClock.uptimeMillis() : this.mTimePeriodEndUptime;
        TimeUtils.formatDuration(l - this.mTimePeriodStartUptime, printWriter);
        printWriter.println();
        printWriter.print("  Total elapsed time: ");
        l = this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime;
        TimeUtils.formatDuration(l - this.mTimePeriodStartRealtime, printWriter);
        n = 1;
        if ((this.mFlags & 2) != 0) {
            printWriter.print(" (shutdown)");
            n = 0;
        }
        if ((this.mFlags & 4) != 0) {
            printWriter.print(" (sysprops)");
            n = 0;
        }
        if ((this.mFlags & 1) != 0) {
            printWriter.print(" (complete)");
            n = 0;
        }
        if (n != 0) {
            printWriter.print(" (partial)");
        }
        if (this.mHasSwappedOutPss) {
            printWriter.print(" (swapped-out-pss)");
        }
        printWriter.print(' ');
        printWriter.print(this.mRuntime);
        printWriter.println();
    }

    public boolean evaluateSystemProperties(boolean bl) {
        boolean bl2 = false;
        String string2 = SystemProperties.get("persist.sys.dalvik.vm.lib.2", VMRuntime.getRuntime().vmLibrary());
        if (!Objects.equals(string2, this.mRuntime)) {
            boolean bl3;
            bl2 = bl3 = true;
            if (bl) {
                this.mRuntime = string2;
                bl2 = bl3;
            }
        }
        return bl2;
    }

    public AssociationState getAssociationStateLocked(String object, int n, long l, String string2, String string3) {
        PackageState packageState = this.getPackageStateLocked((String)object, n, l);
        AssociationState associationState = packageState.mAssociations.get(string3);
        if (associationState != null) {
            return associationState;
        }
        object = string2 != null ? this.getProcessStateLocked((String)object, n, l, string2) : null;
        object = new AssociationState(this, packageState, string3, string2, (ProcessState)object);
        packageState.mAssociations.put(string3, (AssociationState)object);
        return object;
    }

    public PackageState getPackageStateLocked(String object, int n, long l) {
        Object object2 = this.mPackages.get((String)object, n);
        LongSparseArray<PackageState> longSparseArray = object2;
        if (object2 == null) {
            longSparseArray = new LongSparseArray();
            this.mPackages.put((String)object, n, longSparseArray);
        }
        if ((object2 = longSparseArray.get(l)) != null) {
            return object2;
        }
        object = new PackageState(this, (String)object, n, l);
        longSparseArray.put(l, (PackageState)object);
        return object;
    }

    public ProcessState getProcessStateLocked(PackageState packageState, String string2) {
        ProcessState processState = packageState.mProcesses.get(string2);
        if (processState != null) {
            return processState;
        }
        processState = this.mProcesses.get(string2, packageState.mUid);
        if (processState == null) {
            processState = new ProcessState(this, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, string2);
            this.mProcesses.put(string2, packageState.mUid, processState);
        }
        if (!processState.isMultiPackage()) {
            if (!packageState.mPackageName.equals(processState.getPackage()) || packageState.mVersionCode != processState.getVersion()) {
                processState.setMultiPackage(true);
                long l = SystemClock.uptimeMillis();
                PackageState packageState2 = this.getPackageStateLocked(processState.getPackage(), packageState.mUid, processState.getVersion());
                if (packageState2 != null) {
                    int n;
                    Object object;
                    ProcessState processState2 = processState.clone(l);
                    packageState2.mProcesses.put(processState.getName(), processState2);
                    for (n = packageState2.mServices.size() - 1; n >= 0; --n) {
                        object = packageState2.mServices.valueAt(n);
                        if (((ServiceState)object).getProcess() != processState) continue;
                        ((ServiceState)object).setProcess(processState2);
                    }
                    for (n = packageState2.mAssociations.size() - 1; n >= 0; --n) {
                        object = packageState2.mAssociations.valueAt(n);
                        if (((AssociationState)object).getProcess() != processState) continue;
                        ((AssociationState)object).setProcess(processState2);
                    }
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cloning proc state: no package state ");
                    stringBuilder.append(processState.getPackage());
                    stringBuilder.append("/");
                    stringBuilder.append(packageState.mUid);
                    stringBuilder.append(" for proc ");
                    stringBuilder.append(processState.getName());
                    Slog.w(TAG, stringBuilder.toString());
                }
                processState = new ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, string2, l);
            }
        } else {
            processState = new ProcessState(processState, packageState.mPackageName, packageState.mUid, packageState.mVersionCode, string2, SystemClock.uptimeMillis());
        }
        packageState.mProcesses.put(string2, processState);
        return processState;
    }

    public ProcessState getProcessStateLocked(String string2, int n, long l, String string3) {
        return this.getProcessStateLocked(this.getPackageStateLocked(string2, n, l), string3);
    }

    public ServiceState getServiceStateLocked(String object, int n, long l, String string2, String string3) {
        PackageState packageState = this.getPackageStateLocked((String)object, n, l);
        Object object2 = packageState.mServices.get(string3);
        if (object2 != null) {
            return object2;
        }
        object2 = string2 != null ? this.getProcessStateLocked((String)object, n, l, string2) : null;
        object = new ServiceState(this, (String)object, string3, string2, (ProcessState)object2);
        packageState.mServices.put(string3, (ServiceState)object);
        return object;
    }

    long printMemoryCategory(PrintWriter printWriter, String string2, String string3, double d, long l, long l2, int n) {
        if (d != 0.0) {
            l = (long)(1024.0 * d / (double)l);
            printWriter.print(string2);
            printWriter.print(string3);
            printWriter.print(": ");
            DebugUtils.printSizeValue(printWriter, l);
            printWriter.print(" (");
            printWriter.print(n);
            printWriter.print(" samples)");
            printWriter.println();
            return l2 + l;
        }
        return l2;
    }

    public void read(InputStream inputStream) {
        try {
            int[] arrn = new int[1];
            byte[] arrby = ProcessStats.readFully(inputStream, arrn);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(arrby, 0, arrn[0]);
            parcel.setDataPosition(0);
            inputStream.close();
            this.readFromParcel(parcel);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("caught exception: ");
            stringBuilder.append(iOException);
            this.mReadError = stringBuilder.toString();
        }
    }

    String readCommonString(Parcel object, int n) {
        if (n <= 9) {
            return ((Parcel)object).readString();
        }
        n = ((Parcel)object).readInt();
        if (n >= 0) {
            return this.mIndexToCommonString.get(n);
        }
        object = ((Parcel)object).readString();
        while (this.mIndexToCommonString.size() <= n) {
            this.mIndexToCommonString.add(null);
        }
        this.mIndexToCommonString.set(n, (String)object);
        return object;
    }

    public void readFromParcel(Parcel object) {
        Object object2;
        int n;
        Object object3;
        int n2;
        long l;
        boolean bl = this.mPackages.getMap().size() > 0 || this.mProcesses.getMap().size() > 0;
        if (bl) {
            this.resetSafely();
        }
        if (!this.readCheckedInt((Parcel)object, 1347638356, "magic number")) {
            return;
        }
        int n3 = ((Parcel)object).readInt();
        if (n3 != 36) {
            object = new StringBuilder();
            ((StringBuilder)object).append("bad version: ");
            ((StringBuilder)object).append(n3);
            this.mReadError = ((StringBuilder)object).toString();
            return;
        }
        if (!this.readCheckedInt((Parcel)object, 14, "state count")) {
            return;
        }
        if (!this.readCheckedInt((Parcel)object, 8, "adj count")) {
            return;
        }
        if (!this.readCheckedInt((Parcel)object, 10, "pss count")) {
            return;
        }
        if (!this.readCheckedInt((Parcel)object, 16, "sys mem usage count")) {
            return;
        }
        if (!this.readCheckedInt((Parcel)object, 4096, "longs size")) {
            return;
        }
        this.mIndexToCommonString = new ArrayList();
        this.mTimePeriodStartClock = ((Parcel)object).readLong();
        this.buildTimePeriodStartClockStr();
        this.mTimePeriodStartRealtime = ((Parcel)object).readLong();
        this.mTimePeriodEndRealtime = ((Parcel)object).readLong();
        this.mTimePeriodStartUptime = ((Parcel)object).readLong();
        this.mTimePeriodEndUptime = ((Parcel)object).readLong();
        this.mInternalSinglePssCount = ((Parcel)object).readLong();
        this.mInternalSinglePssTime = ((Parcel)object).readLong();
        this.mInternalAllMemPssCount = ((Parcel)object).readLong();
        this.mInternalAllMemPssTime = ((Parcel)object).readLong();
        this.mInternalAllPollPssCount = ((Parcel)object).readLong();
        this.mInternalAllPollPssTime = ((Parcel)object).readLong();
        this.mExternalPssCount = ((Parcel)object).readLong();
        this.mExternalPssTime = ((Parcel)object).readLong();
        this.mExternalSlowPssCount = ((Parcel)object).readLong();
        this.mExternalSlowPssTime = ((Parcel)object).readLong();
        this.mRuntime = ((Parcel)object).readString();
        boolean bl2 = ((Parcel)object).readInt() != 0;
        this.mHasSwappedOutPss = bl2;
        this.mFlags = ((Parcel)object).readInt();
        this.mTableData.readFromParcel((Parcel)object);
        Object object4 = this.mMemFactorDurations;
        this.readCompactedLongArray((Parcel)object, n3, (long[])object4, ((long[])object4).length);
        if (!this.mSysMemUsage.readFromParcel((Parcel)object)) {
            return;
        }
        int n4 = ((Parcel)object).readInt();
        if (n4 < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("bad process count: ");
            ((StringBuilder)object).append(n4);
            this.mReadError = ((StringBuilder)object).toString();
            return;
        }
        while (n4 > 0) {
            object4 = this.readCommonString((Parcel)object, n3);
            if (object4 == null) {
                this.mReadError = "bad process name";
                return;
            }
            if (n2 < 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bad uid count: ");
                ((StringBuilder)object).append(n2);
                this.mReadError = ((StringBuilder)object).toString();
                return;
            }
            for (n = n2 = object.readInt(); n > 0; --n) {
                n2 = ((Parcel)object).readInt();
                if (n2 < 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("bad uid: ");
                    ((StringBuilder)object).append(n2);
                    this.mReadError = ((StringBuilder)object).toString();
                    return;
                }
                object3 = this.readCommonString((Parcel)object, n3);
                if (object3 == null) {
                    this.mReadError = "bad process package name";
                    return;
                }
                l = ((Parcel)object).readLong();
                object2 = bl ? this.mProcesses.get((String)object4, n2) : null;
                if (object2 != null) {
                    if (!object2.readFromParcel((Parcel)object, false)) {
                        return;
                    }
                } else {
                    object3 = new ProcessState(this, (String)object3, n2, l, (String)object4);
                    object2 = object3;
                    if (!((ProcessState)object3).readFromParcel((Parcel)object, true)) {
                        return;
                    }
                }
                this.mProcesses.put((String)object4, n2, (ProcessState)object2);
            }
            --n4;
        }
        if (n < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("bad package count: ");
            ((StringBuilder)object).append(n);
            this.mReadError = ((StringBuilder)object).toString();
            return;
        }
        for (n2 = n = object.readInt(); n2 > 0; --n2) {
            object3 = this.readCommonString((Parcel)object, n3);
            if (object3 == null) {
                this.mReadError = "bad package name";
                return;
            }
            if (n < 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("bad uid count: ");
                ((StringBuilder)object).append(n);
                this.mReadError = ((StringBuilder)object).toString();
                return;
            }
            for (int i = n = object.readInt(); i > 0; --i) {
                int n5;
                n = ((Parcel)object).readInt();
                if (n < 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("bad uid: ");
                    ((StringBuilder)object).append(n);
                    this.mReadError = ((StringBuilder)object).toString();
                    return;
                }
                if (n5 < 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("bad versions count: ");
                    ((StringBuilder)object).append(n5);
                    this.mReadError = ((StringBuilder)object).toString();
                    return;
                }
                for (int j = n5 = object.readInt(); j > 0; --j) {
                    int n6;
                    int n7;
                    Object object5;
                    l = ((Parcel)object).readLong();
                    n5 = n;
                    Object object6 = new PackageState(this, (String)object3, n, l);
                    object4 = this.mPackages.get((String)object3, n5);
                    if (object4 == null) {
                        object4 = new LongSparseArray();
                        this.mPackages.put((String)object3, n5, (LongSparseArray<PackageState>)object4);
                    }
                    object4.put(l, object6);
                    n = ((Parcel)object).readInt();
                    if (n < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("bad package process count: ");
                        ((StringBuilder)object).append(n);
                        this.mReadError = ((StringBuilder)object).toString();
                        return;
                    }
                    object2 = object4;
                    while (n > 0) {
                        --n;
                        String string2 = this.readCommonString((Parcel)object, n3);
                        if (string2 == null) {
                            this.mReadError = "bad package process name";
                            return;
                        }
                        n6 = ((Parcel)object).readInt();
                        object5 = this.mProcesses.get(string2, n5);
                        if (object5 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("no common proc: ");
                            ((StringBuilder)object).append(string2);
                            this.mReadError = ((StringBuilder)object).toString();
                            return;
                        }
                        if (n6 != 0) {
                            object4 = bl ? ((PackageState)object6).mProcesses.get(string2) : null;
                            if (object4 != null) {
                                if (!object4.readFromParcel((Parcel)object, false)) {
                                    return;
                                }
                            } else {
                                object5 = new ProcessState((ProcessState)object5, (String)object3, n5, l, string2, 0L);
                                object4 = object5;
                                if (!((ProcessState)object5).readFromParcel((Parcel)object, true)) {
                                    return;
                                }
                            }
                            ((PackageState)object6).mProcesses.put(string2, (ProcessState)object4);
                            continue;
                        }
                        ((PackageState)object6).mProcesses.put(string2, (ProcessState)object5);
                    }
                    n = ((Parcel)object).readInt();
                    if (n < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("bad package service count: ");
                        ((StringBuilder)object).append(n);
                        this.mReadError = ((StringBuilder)object).toString();
                        return;
                    }
                    object4 = object6;
                    while (n > 0) {
                        --n;
                        object5 = ((Parcel)object).readString();
                        if (object5 == null) {
                            this.mReadError = "bad package service name";
                            return;
                        }
                        object6 = n3 > 9 ? this.readCommonString((Parcel)object, n3) : null;
                        object2 = bl ? object4.mServices.get(object5) : null;
                        if (object2 == null) {
                            object2 = new ServiceState(this, (String)object3, (String)object5, (String)object6, null);
                        }
                        if (!object2.readFromParcel((Parcel)object)) {
                            return;
                        }
                        object4.mServices.put((String)object5, (ServiceState)object2);
                    }
                    n = n5;
                    if (n7 < 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("bad package association count: ");
                        ((StringBuilder)object).append(n7);
                        this.mReadError = ((StringBuilder)object).toString();
                        return;
                    }
                    for (n6 = n7 = object.readInt(); n6 > 0; --n6) {
                        object6 = this.readCommonString((Parcel)object, n3);
                        if (object6 == null) {
                            this.mReadError = "bad package association name";
                            return;
                        }
                        object5 = this.readCommonString((Parcel)object, n3);
                        object2 = bl ? object4.mAssociations.get(object6) : null;
                        if (object2 == null) {
                            object2 = new AssociationState(this, (PackageState)object4, (String)object6, (String)object5, null);
                        }
                        if ((object5 = object2.readFromParcel(this, (Parcel)object, n3)) != null) {
                            this.mReadError = object5;
                            return;
                        }
                        object4.mAssociations.put((String)object6, (AssociationState)object2);
                    }
                }
            }
        }
        n = ((Parcel)object).readInt();
        this.mPageTypeNodes.clear();
        this.mPageTypeNodes.ensureCapacity(n);
        this.mPageTypeZones.clear();
        this.mPageTypeZones.ensureCapacity(n);
        this.mPageTypeLabels.clear();
        this.mPageTypeLabels.ensureCapacity(n);
        this.mPageTypeSizes.clear();
        this.mPageTypeSizes.ensureCapacity(n);
        for (n4 = 0; n4 < n; ++n4) {
            this.mPageTypeNodes.add(((Parcel)object).readInt());
            this.mPageTypeZones.add(((Parcel)object).readString());
            this.mPageTypeLabels.add(((Parcel)object).readString());
            this.mPageTypeSizes.add(((Parcel)object).createIntArray());
        }
        this.mIndexToCommonString = null;
    }

    public void reset() {
        this.resetCommon();
        this.mPackages.getMap().clear();
        this.mProcesses.getMap().clear();
        this.mMemFactor = -1;
        this.mStartTime = 0L;
    }

    public void resetSafely() {
        int n;
        Object object;
        Cloneable cloneable;
        int n2;
        this.resetCommon();
        long l = SystemClock.uptimeMillis();
        ArrayMap<String, SparseArray<ProcessState>> arrayMap = this.mProcesses.getMap();
        for (n2 = arrayMap.size() - 1; n2 >= 0; --n2) {
            cloneable = arrayMap.valueAt(n2);
            for (n = cloneable.size() - 1; n >= 0; --n) {
                cloneable.valueAt((int)n).tmpNumInUse = 0;
            }
        }
        ArrayMap<String, SparseArray<LongSparseArray<PackageState>>> arrayMap2 = this.mPackages.getMap();
        for (n2 = arrayMap2.size() - 1; n2 >= 0; --n2) {
            object = arrayMap2.valueAt(n2);
            for (n = object.size() - 1; n >= 0; --n) {
                cloneable = ((SparseArray)object).valueAt(n);
                for (int i = cloneable.size() - 1; i >= 0; --i) {
                    Object object2;
                    int n3;
                    PackageState packageState = (PackageState)((LongSparseArray)cloneable).valueAt(i);
                    for (n3 = packageState.mProcesses.size() - 1; n3 >= 0; --n3) {
                        object2 = packageState.mProcesses.valueAt(n3);
                        if (((ProcessState)object2).isInUse()) {
                            ((ProcessState)object2).resetSafely(l);
                            ProcessState processState = ((ProcessState)object2).getCommonProcess();
                            ++processState.tmpNumInUse;
                            object2.getCommonProcess().tmpFoundSubProc = object2;
                            continue;
                        }
                        packageState.mProcesses.valueAt(n3).makeDead();
                        packageState.mProcesses.removeAt(n3);
                    }
                    for (n3 = packageState.mServices.size() - 1; n3 >= 0; --n3) {
                        object2 = packageState.mServices.valueAt(n3);
                        if (((ServiceState)object2).isInUse()) {
                            ((ServiceState)object2).resetSafely(l);
                            continue;
                        }
                        packageState.mServices.removeAt(n3);
                    }
                    for (n3 = packageState.mAssociations.size() - 1; n3 >= 0; --n3) {
                        object2 = packageState.mAssociations.valueAt(n3);
                        if (((AssociationState)object2).isInUse()) {
                            ((AssociationState)object2).resetSafely(l);
                            continue;
                        }
                        packageState.mAssociations.removeAt(n3);
                    }
                    if (packageState.mProcesses.size() > 0 || packageState.mServices.size() > 0 || packageState.mAssociations.size() > 0) continue;
                    ((LongSparseArray)cloneable).removeAt(i);
                }
                if (((LongSparseArray)cloneable).size() > 0) continue;
                ((SparseArray)object).removeAt(n);
            }
            if (((SparseArray)object).size() > 0) continue;
            arrayMap2.removeAt(n2);
        }
        for (n2 = arrayMap.size() - 1; n2 >= 0; --n2) {
            cloneable = arrayMap.valueAt(n2);
            for (n = cloneable.size() - 1; n >= 0; --n) {
                object = (ProcessState)((SparseArray)cloneable).valueAt(n);
                if (!((ProcessState)object).isInUse() && ((ProcessState)object).tmpNumInUse <= 0) {
                    ((ProcessState)object).makeDead();
                    ((SparseArray)cloneable).removeAt(n);
                    continue;
                }
                if (!((ProcessState)object).isActive() && ((ProcessState)object).isMultiPackage() && ((ProcessState)object).tmpNumInUse == 1) {
                    object = ((ProcessState)object).tmpFoundSubProc;
                    ((ProcessState)object).makeStandalone();
                    ((SparseArray)cloneable).setValueAt(n, object);
                    continue;
                }
                ((ProcessState)object).resetSafely(l);
            }
            if (((SparseArray)cloneable).size() > 0) continue;
            arrayMap.removeAt(n2);
        }
        this.mStartTime = l;
    }

    /*
     * Exception decompiling
     */
    public void updateFragmentation() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 11[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void updateTrackingAssociationsLocked(int n, long l) {
        for (int i = this.mTrackingAssociations.size() - 1; i >= 0; --i) {
            AssociationState.SourceState sourceState = this.mTrackingAssociations.get(i);
            if (sourceState.mProcStateSeq == n && sourceState.mProcState < 9) {
                Object object = sourceState.getAssociationState().getProcess();
                if (object != null) {
                    int n2 = ((ProcessState)object).getCombinedState() % 14;
                    if (sourceState.mProcState == n2) {
                        sourceState.startActive(l);
                        continue;
                    }
                    sourceState.stopActive(l);
                    if (sourceState.mProcState >= n2) continue;
                    long l2 = SystemClock.uptimeMillis();
                    if (this.mNextInverseProcStateWarningUptime > l2) {
                        ++this.mSkippedInverseProcStateWarningCount;
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Tracking association ");
                    stringBuilder.append(sourceState);
                    stringBuilder.append(" whose proc state ");
                    stringBuilder.append(sourceState.mProcState);
                    stringBuilder.append(" is better than process ");
                    stringBuilder.append(object);
                    stringBuilder.append(" proc state ");
                    stringBuilder.append(n2);
                    stringBuilder.append(" (");
                    stringBuilder.append(this.mSkippedInverseProcStateWarningCount);
                    stringBuilder.append(" skipped)");
                    Slog.w(TAG, stringBuilder.toString());
                    this.mSkippedInverseProcStateWarningCount = 0;
                    this.mNextInverseProcStateWarningUptime = 10000L + l2;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Tracking association without process: ");
                ((StringBuilder)object).append(sourceState);
                ((StringBuilder)object).append(" in ");
                ((StringBuilder)object).append(sourceState.getAssociationState());
                Slog.wtf(TAG, ((StringBuilder)object).toString());
                continue;
            }
            sourceState.stopActive(l);
            sourceState.mInTrackingList = false;
            sourceState.mProcState = -1;
            this.mTrackingAssociations.remove(i);
        }
    }

    void writeCommonString(Parcel parcel, String string2) {
        Integer n = this.mCommonStringToIndex.get(string2);
        if (n != null) {
            parcel.writeInt(n);
            return;
        }
        n = this.mCommonStringToIndex.size();
        this.mCommonStringToIndex.put(string2, n);
        parcel.writeInt(n);
        parcel.writeString(string2);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcel(parcel, SystemClock.uptimeMillis(), n);
    }

    public void writeToParcel(Parcel parcel, long l, int n) {
        Object object;
        int n2;
        int n3;
        int n4;
        int n5;
        Object object2;
        int n6;
        SparseArray<ProcessState> sparseArray;
        int n7;
        Object object3;
        parcel.writeInt(1347638356);
        parcel.writeInt(36);
        parcel.writeInt(14);
        parcel.writeInt(8);
        parcel.writeInt(10);
        parcel.writeInt(16);
        parcel.writeInt(4096);
        this.mCommonStringToIndex = new ArrayMap(this.mProcesses.size());
        Object object4 = this.mProcesses.getMap();
        int n8 = ((ArrayMap)object4).size();
        for (n = 0; n < n8; ++n) {
            sparseArray = ((ArrayMap)object4).valueAt(n);
            n3 = sparseArray.size();
            for (n4 = 0; n4 < n3; ++n4) {
                sparseArray.valueAt(n4).commitStateTime(l);
            }
        }
        Object object5 = this.mPackages.getMap();
        int n9 = ((ArrayMap)object5).size();
        for (n = 0; n < n9; ++n) {
            object = ((ArrayMap)object5).valueAt(n);
            n3 = ((SparseArray)object).size();
            for (n4 = 0; n4 < n3; ++n4) {
                sparseArray = ((SparseArray)object).valueAt(n4);
                int n10 = ((LongSparseArray)((Object)sparseArray)).size();
                for (n6 = 0; n6 < n10; ++n6) {
                    object3 = (PackageState)((LongSparseArray)((Object)sparseArray)).valueAt(n6);
                    n7 = ((PackageState)object3).mProcesses.size();
                    for (n2 = 0; n2 < n7; ++n2) {
                        object2 = ((PackageState)object3).mProcesses.valueAt(n2);
                        if (((ProcessState)object2).getCommonProcess() == object2) continue;
                        ((ProcessState)object2).commitStateTime(l);
                    }
                    n5 = ((PackageState)object3).mServices.size();
                    for (n7 = 0; n7 < n5; ++n7) {
                        ((PackageState)object3).mServices.valueAt(n7).commitStateTime(l);
                    }
                    int n11 = ((PackageState)object3).mAssociations.size();
                    n7 = n5;
                    for (n2 = 0; n2 < n11; ++n2) {
                        ((PackageState)object3).mAssociations.valueAt(n2).commitStateTime(l);
                    }
                }
            }
        }
        parcel.writeLong(this.mTimePeriodStartClock);
        parcel.writeLong(this.mTimePeriodStartRealtime);
        parcel.writeLong(this.mTimePeriodEndRealtime);
        parcel.writeLong(this.mTimePeriodStartUptime);
        parcel.writeLong(this.mTimePeriodEndUptime);
        parcel.writeLong(this.mInternalSinglePssCount);
        parcel.writeLong(this.mInternalSinglePssTime);
        parcel.writeLong(this.mInternalAllMemPssCount);
        parcel.writeLong(this.mInternalAllMemPssTime);
        parcel.writeLong(this.mInternalAllPollPssCount);
        parcel.writeLong(this.mInternalAllPollPssTime);
        parcel.writeLong(this.mExternalPssCount);
        parcel.writeLong(this.mExternalPssTime);
        parcel.writeLong(this.mExternalSlowPssCount);
        parcel.writeLong(this.mExternalSlowPssTime);
        parcel.writeString(this.mRuntime);
        parcel.writeInt((int)this.mHasSwappedOutPss);
        parcel.writeInt(this.mFlags);
        this.mTableData.writeToParcel(parcel);
        n = this.mMemFactor;
        if (n != -1) {
            sparseArray = this.mMemFactorDurations;
            sparseArray[n] = sparseArray[n] + (l - this.mStartTime);
            this.mStartTime = l;
        }
        sparseArray = this.mMemFactorDurations;
        this.writeCompactedLongArray(parcel, (long[])sparseArray, ((Cloneable)sparseArray).length);
        this.mSysMemUsage.writeToParcel(parcel);
        parcel.writeInt(n8);
        for (n = 0; n < n8; ++n) {
            this.writeCommonString(parcel, (String)((ArrayMap)object4).keyAt(n));
            sparseArray = ((ArrayMap)object4).valueAt(n);
            n3 = sparseArray.size();
            parcel.writeInt(n3);
            for (n4 = 0; n4 < n3; ++n4) {
                parcel.writeInt(sparseArray.keyAt(n4));
                object = sparseArray.valueAt(n4);
                this.writeCommonString(parcel, ((ProcessState)object).getPackage());
                parcel.writeLong(((ProcessState)object).getVersion());
                ((ProcessState)object).writeToParcel(parcel, l);
            }
        }
        parcel.writeInt(n9);
        n = n9;
        sparseArray = object5;
        n6 = n8;
        object = object4;
        for (n4 = 0; n4 < n; ++n4) {
            this.writeCommonString(parcel, (String)((ArrayMap)((Object)sparseArray)).keyAt(n4));
            object5 = (SparseArray)((ArrayMap)((Object)sparseArray)).valueAt(n4);
            n9 = ((SparseArray)object5).size();
            parcel.writeInt(n9);
            for (n3 = 0; n3 < n9; ++n3) {
                parcel.writeInt(((SparseArray)object5).keyAt(n3));
                object3 = (LongSparseArray)((SparseArray)object5).valueAt(n3);
                n5 = ((LongSparseArray)object3).size();
                parcel.writeInt(n5);
                for (n7 = 0; n7 < n5; ++n7) {
                    parcel.writeLong(((LongSparseArray)object3).keyAt(n7));
                    object4 = (PackageState)((LongSparseArray)object3).valueAt(n7);
                    n2 = ((PackageState)object4).mProcesses.size();
                    parcel.writeInt(n2);
                    for (n8 = 0; n8 < n2; ++n8) {
                        this.writeCommonString(parcel, ((PackageState)object4).mProcesses.keyAt(n8));
                        object2 = ((PackageState)object4).mProcesses.valueAt(n8);
                        if (((ProcessState)object2).getCommonProcess() == object2) {
                            parcel.writeInt(0);
                            continue;
                        }
                        parcel.writeInt(1);
                        ((ProcessState)object2).writeToParcel(parcel, l);
                    }
                    n2 = ((PackageState)object4).mServices.size();
                    parcel.writeInt(n2);
                    for (n8 = 0; n8 < n2; ++n8) {
                        parcel.writeString(((PackageState)object4).mServices.keyAt(n8));
                        object2 = ((PackageState)object4).mServices.valueAt(n8);
                        this.writeCommonString(parcel, ((ServiceState)object2).getProcessName());
                        ((ServiceState)object2).writeToParcel(parcel, l);
                    }
                    n8 = ((PackageState)object4).mAssociations.size();
                    parcel.writeInt(n8);
                    for (n2 = 0; n2 < n8; ++n2) {
                        this.writeCommonString(parcel, ((PackageState)object4).mAssociations.keyAt(n2));
                        object2 = ((PackageState)object4).mAssociations.valueAt(n2);
                        this.writeCommonString(parcel, ((AssociationState)object2).getProcessName());
                        ((AssociationState)object2).writeToParcel(this, parcel, l);
                    }
                }
            }
        }
        n4 = this.mPageTypeLabels.size();
        parcel.writeInt(n4);
        for (n = 0; n < n4; ++n) {
            parcel.writeInt(this.mPageTypeNodes.get(n));
            parcel.writeString(this.mPageTypeZones.get(n));
            parcel.writeString(this.mPageTypeLabels.get(n));
            parcel.writeIntArray(this.mPageTypeSizes.get(n));
        }
        this.mCommonStringToIndex = null;
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, int n) {
        int n2;
        int n3;
        Object object;
        Object object2;
        protoOutputStream.write(1112396529665L, this.mTimePeriodStartRealtime);
        long l2 = this.mRunning ? SystemClock.elapsedRealtime() : this.mTimePeriodEndRealtime;
        protoOutputStream.write(1112396529666L, l2);
        protoOutputStream.write(1112396529667L, this.mTimePeriodStartUptime);
        protoOutputStream.write(1112396529668L, this.mTimePeriodEndUptime);
        protoOutputStream.write(1138166333445L, this.mRuntime);
        protoOutputStream.write(1133871366150L, this.mHasSwappedOutPss);
        int n4 = 1;
        if ((this.mFlags & 2) != 0) {
            protoOutputStream.write(2259152797703L, 3);
            n4 = 0;
        }
        if ((this.mFlags & 4) != 0) {
            protoOutputStream.write(2259152797703L, 4);
            n4 = 0;
        }
        if ((this.mFlags & 1) != 0) {
            protoOutputStream.write(2259152797703L, 1);
            n4 = 0;
        }
        if (n4 != 0) {
            protoOutputStream.write(2259152797703L, 2);
        }
        int n5 = this.mPageTypeLabels.size();
        for (n4 = 0; n4 < n5; ++n4) {
            l2 = protoOutputStream.start(2246267895818L);
            protoOutputStream.write(1120986464257L, this.mPageTypeNodes.get(n4));
            protoOutputStream.write(1138166333442L, this.mPageTypeZones.get(n4));
            protoOutputStream.write(1138166333443L, this.mPageTypeLabels.get(n4));
            object = this.mPageTypeSizes.get(n4);
            n2 = object == null ? 0 : ((int[])object).length;
            for (n3 = 0; n3 < n2; ++n3) {
                protoOutputStream.write(2220498092036L, (int)object[n3]);
            }
            protoOutputStream.end(l2);
        }
        Object object3 = this.mProcesses.getMap();
        if ((n & 1) != 0) {
            for (n4 = 0; n4 < ((ArrayMap)object3).size(); ++n4) {
                object2 = ((ArrayMap)object3).keyAt(n4);
                object = ((ArrayMap)object3).valueAt(n4);
                for (n2 = 0; n2 < ((SparseArray)object).size(); ++n2) {
                    n3 = ((SparseArray)object).keyAt(n2);
                    ((ProcessState)((SparseArray)object).valueAt(n2)).writeToProto(protoOutputStream, 2246267895816L, (String)object2, n3, l);
                }
            }
        }
        if ((n & 14) != 0) {
            object2 = this.mPackages.getMap();
            for (n4 = 0; n4 < ((ArrayMap)object2).size(); ++n4) {
                object3 = (SparseArray)((ArrayMap)object2).valueAt(n4);
                for (n2 = 0; n2 < ((SparseArray)object3).size(); ++n2) {
                    object = (LongSparseArray)((SparseArray)object3).valueAt(n2);
                    for (n3 = 0; n3 < ((LongSparseArray)object).size(); ++n3) {
                        ((PackageState)((LongSparseArray)object).valueAt(n3)).writeToProto(protoOutputStream, 2246267895817L, l, n);
                    }
                }
            }
        }
    }

    public static final class PackageState {
        public final ArrayMap<String, AssociationState> mAssociations = new ArrayMap();
        public final String mPackageName;
        public final ProcessStats mProcessStats;
        public final ArrayMap<String, ProcessState> mProcesses = new ArrayMap();
        public final ArrayMap<String, ServiceState> mServices = new ArrayMap();
        public final int mUid;
        public final long mVersionCode;

        public PackageState(ProcessStats processStats, String string2, int n, long l) {
            this.mProcessStats = processStats;
            this.mUid = n;
            this.mPackageName = string2;
            this.mVersionCode = l;
        }

        public AssociationState getAssociationStateLocked(ProcessState object, String string2) {
            AssociationState associationState = this.mAssociations.get(string2);
            if (associationState != null) {
                if (object != null) {
                    associationState.setProcess((ProcessState)object);
                }
                return associationState;
            }
            object = new AssociationState(this.mProcessStats, this, string2, ((ProcessState)object).getName(), (ProcessState)object);
            this.mAssociations.put(string2, (AssociationState)object);
            return object;
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, long l, long l2, int n) {
            int n2;
            l = protoOutputStream.start(l);
            protoOutputStream.write(1138166333441L, this.mPackageName);
            protoOutputStream.write(1120986464258L, this.mUid);
            protoOutputStream.write(1112396529667L, this.mVersionCode);
            if ((n & 2) != 0) {
                for (n2 = 0; n2 < this.mProcesses.size(); ++n2) {
                    String string2 = this.mProcesses.keyAt(n2);
                    this.mProcesses.valueAt(n2).writeToProto(protoOutputStream, 2246267895812L, string2, this.mUid, l2);
                }
            }
            if ((n & 4) != 0) {
                for (n2 = 0; n2 < this.mServices.size(); ++n2) {
                    this.mServices.valueAt(n2).writeToProto(protoOutputStream, 2246267895813L, l2);
                }
            }
            if ((n & 8) != 0) {
                for (n = 0; n < this.mAssociations.size(); ++n) {
                    this.mAssociations.valueAt(n).writeToProto(protoOutputStream, 2246267895814L, l2);
                }
            }
            protoOutputStream.end(l);
        }
    }

    public static final class ProcessDataCollection {
        public long avgPss;
        public long avgRss;
        public long avgUss;
        public long maxPss;
        public long maxRss;
        public long maxUss;
        final int[] memStates;
        public long minPss;
        public long minRss;
        public long minUss;
        public long numPss;
        final int[] procStates;
        final int[] screenStates;
        public long totalTime;

        public ProcessDataCollection(int[] arrn, int[] arrn2, int[] arrn3) {
            this.screenStates = arrn;
            this.memStates = arrn2;
            this.procStates = arrn3;
        }

        void print(PrintWriter printWriter, long l, boolean bl) {
            if (this.totalTime > l) {
                printWriter.print("*");
            }
            DumpUtils.printPercent(printWriter, (double)this.totalTime / (double)l);
            if (this.numPss > 0L) {
                printWriter.print(" (");
                DebugUtils.printSizeValue(printWriter, this.minPss * 1024L);
                printWriter.print("-");
                DebugUtils.printSizeValue(printWriter, this.avgPss * 1024L);
                printWriter.print("-");
                DebugUtils.printSizeValue(printWriter, this.maxPss * 1024L);
                printWriter.print("/");
                DebugUtils.printSizeValue(printWriter, this.minUss * 1024L);
                printWriter.print("-");
                DebugUtils.printSizeValue(printWriter, this.avgUss * 1024L);
                printWriter.print("-");
                DebugUtils.printSizeValue(printWriter, this.maxUss * 1024L);
                printWriter.print("/");
                DebugUtils.printSizeValue(printWriter, this.minRss * 1024L);
                printWriter.print("-");
                DebugUtils.printSizeValue(printWriter, this.avgRss * 1024L);
                printWriter.print("-");
                DebugUtils.printSizeValue(printWriter, this.maxRss * 1024L);
                if (bl) {
                    printWriter.print(" over ");
                    printWriter.print(this.numPss);
                }
                printWriter.print(")");
            }
        }
    }

    public static final class ProcessStateHolder {
        public final long appVersion;
        public PackageState pkg;
        public ProcessState state;

        public ProcessStateHolder(long l) {
            this.appVersion = l;
        }
    }

    public static class TotalMemoryUseCollection {
        public boolean hasSwappedOutPss;
        final int[] memStates;
        public long[] processStatePss = new long[14];
        public int[] processStateSamples = new int[14];
        public long[] processStateTime = new long[14];
        public double[] processStateWeight = new double[14];
        final int[] screenStates;
        public double sysMemCachedWeight;
        public double sysMemFreeWeight;
        public double sysMemKernelWeight;
        public double sysMemNativeWeight;
        public int sysMemSamples;
        public long[] sysMemUsage = new long[16];
        public double sysMemZRamWeight;
        public long totalTime;

        public TotalMemoryUseCollection(int[] arrn, int[] arrn2) {
            this.screenStates = arrn;
            this.memStates = arrn2;
        }
    }

}

