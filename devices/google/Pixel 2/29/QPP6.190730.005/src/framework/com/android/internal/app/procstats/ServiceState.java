/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.os.Parcel;
import android.os.SystemClock;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.DurationsTable;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.SparseMappingTable;
import java.io.PrintWriter;

public final class ServiceState {
    private static final boolean DEBUG = false;
    public static final int SERVICE_BOUND = 2;
    public static final int SERVICE_COUNT = 5;
    public static final int SERVICE_EXEC = 3;
    public static final int SERVICE_FOREGROUND = 4;
    public static final int SERVICE_RUN = 0;
    public static final int SERVICE_STARTED = 1;
    private static final String TAG = "ProcessStats";
    private int mBoundCount;
    private long mBoundStartTime;
    private int mBoundState = -1;
    private final DurationsTable mDurations;
    private int mExecCount;
    private long mExecStartTime;
    private int mExecState = -1;
    private int mForegroundCount;
    private long mForegroundStartTime;
    private int mForegroundState = -1;
    private final String mName;
    private Object mOwner;
    private final String mPackage;
    private ProcessState mProc;
    private final String mProcessName;
    private boolean mRestarting;
    private int mRunCount;
    private long mRunStartTime;
    private int mRunState = -1;
    private boolean mStarted;
    private int mStartedCount;
    private long mStartedStartTime;
    private int mStartedState = -1;

    public ServiceState(ProcessStats processStats, String string2, String string3, String string4, ProcessState processState) {
        this.mPackage = string2;
        this.mName = string3;
        this.mProcessName = string4;
        this.mProc = processState;
        this.mDurations = new DurationsTable(processStats.mTableData);
    }

    private void dumpStats(PrintWriter printWriter, String string2, String string3, String string4, String string5, int n, int n2, int n3, long l, long l2, long l3, boolean bl) {
        block4 : {
            if (n == 0) break block4;
            if (bl) {
                printWriter.print(string2);
                printWriter.print(string5);
                printWriter.print(" op count ");
                printWriter.print(n);
                printWriter.println(":");
                this.dumpTime(printWriter, string3, n2, n3, l, l2);
            } else {
                l2 = this.dumpTimeInternal(null, null, n2, n3, l, l2, true);
                printWriter.print(string2);
                printWriter.print(string4);
                printWriter.print(string5);
                printWriter.print(" count ");
                printWriter.print(n);
                printWriter.print(" / time ");
                n = l2 < 0L ? 1 : 0;
                l = l2;
                if (n != 0) {
                    l = -l2;
                }
                DumpUtils.printPercent(printWriter, (double)l / (double)l3);
                if (n != 0) {
                    printWriter.print(" (running)");
                }
                printWriter.println();
            }
        }
    }

    private void dumpTimeCheckin(PrintWriter printWriter, String object, String string2, int n, long l, String string3, int n2, int n3, int n4, long l2, long l3) {
        if (n3 <= 0) {
            return;
        }
        printWriter.print((String)object);
        printWriter.print(",");
        printWriter.print(string2);
        printWriter.print(",");
        printWriter.print(n);
        printWriter.print(",");
        printWriter.print(l);
        printWriter.print(",");
        printWriter.print(string3);
        printWriter.print(",");
        printWriter.print(n3);
        n = 0;
        int n5 = this.mDurations.getKeyCount();
        n3 = 0;
        do {
            object = this;
            if (n3 >= n5) break;
            int n6 = ((ServiceState)object).mDurations.getKeyAt(n3);
            long l4 = ((ServiceState)object).mDurations.getValue(n6);
            byte by = SparseMappingTable.getIdFromKey(n6);
            n6 = by / 5;
            if (by % 5 == n2) {
                l = l4;
                if (n4 == n6) {
                    n = 1;
                    l = l4 + (l3 - l2);
                }
                DumpUtils.printAdjTagAndValue(printWriter, n6, l);
            }
            ++n3;
        } while (true);
        if (n == 0 && n4 != -1) {
            DumpUtils.printAdjTagAndValue(printWriter, n4, l3 - l2);
        }
        printWriter.println();
    }

    private void updateRunning(int n, long l) {
        int n2;
        if (this.mStartedState == -1 && this.mBoundState == -1 && this.mExecState == -1 && this.mForegroundState == -1) {
            n = -1;
        }
        if ((n2 = this.mRunState) != n) {
            if (n2 != -1) {
                this.mDurations.addDuration(n2 * 5 + 0, l - this.mRunStartTime);
            } else if (n != -1) {
                ++this.mRunCount;
            }
            this.mRunState = n;
            this.mRunStartTime = l;
        }
    }

    public void add(ServiceState serviceState) {
        this.mDurations.addDurations(serviceState.mDurations);
        this.mRunCount += serviceState.mRunCount;
        this.mStartedCount += serviceState.mStartedCount;
        this.mBoundCount += serviceState.mBoundCount;
        this.mExecCount += serviceState.mExecCount;
        this.mForegroundCount += serviceState.mForegroundCount;
    }

    public void applyNewOwner(Object object) {
        Object object2 = this.mOwner;
        if (object2 != object) {
            if (object2 == null) {
                this.mOwner = object;
                this.mProc.incActiveServices(this.mName);
            } else {
                this.mOwner = object;
                if (this.mStarted || this.mBoundState != -1 || this.mExecState != -1 || this.mForegroundState != -1) {
                    long l = SystemClock.uptimeMillis();
                    if (this.mStarted) {
                        this.setStarted(false, 0, l);
                    }
                    if (this.mBoundState != -1) {
                        this.setBound(false, 0, l);
                    }
                    if (this.mExecState != -1) {
                        this.setExecuting(false, 0, l);
                    }
                    if (this.mForegroundState != -1) {
                        this.setForeground(false, 0, l);
                    }
                }
            }
        }
    }

    public void clearCurrentOwner(Object object, boolean bl) {
        if (this.mOwner == object) {
            this.mProc.decActiveServices(this.mName);
            if (this.mStarted || this.mBoundState != -1 || this.mExecState != -1 || this.mForegroundState != -1) {
                StringBuilder stringBuilder;
                long l = SystemClock.uptimeMillis();
                if (this.mStarted) {
                    if (!bl) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Service owner ");
                        stringBuilder.append(object);
                        stringBuilder.append(" cleared while started: pkg=");
                        stringBuilder.append(this.mPackage);
                        stringBuilder.append(" service=");
                        stringBuilder.append(this.mName);
                        stringBuilder.append(" proc=");
                        stringBuilder.append(this.mProc);
                        Slog.wtfStack(TAG, stringBuilder.toString());
                    }
                    this.setStarted(false, 0, l);
                }
                if (this.mBoundState != -1) {
                    if (!bl) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Service owner ");
                        stringBuilder.append(object);
                        stringBuilder.append(" cleared while bound: pkg=");
                        stringBuilder.append(this.mPackage);
                        stringBuilder.append(" service=");
                        stringBuilder.append(this.mName);
                        stringBuilder.append(" proc=");
                        stringBuilder.append(this.mProc);
                        Slog.wtfStack(TAG, stringBuilder.toString());
                    }
                    this.setBound(false, 0, l);
                }
                if (this.mExecState != -1) {
                    if (!bl) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Service owner ");
                        stringBuilder.append(object);
                        stringBuilder.append(" cleared while exec: pkg=");
                        stringBuilder.append(this.mPackage);
                        stringBuilder.append(" service=");
                        stringBuilder.append(this.mName);
                        stringBuilder.append(" proc=");
                        stringBuilder.append(this.mProc);
                        Slog.wtfStack(TAG, stringBuilder.toString());
                    }
                    this.setExecuting(false, 0, l);
                }
                if (this.mForegroundState != -1) {
                    if (!bl) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Service owner ");
                        stringBuilder.append(object);
                        stringBuilder.append(" cleared while foreground: pkg=");
                        stringBuilder.append(this.mPackage);
                        stringBuilder.append(" service=");
                        stringBuilder.append(this.mName);
                        stringBuilder.append(" proc=");
                        stringBuilder.append(this.mProc);
                        Slog.wtfStack(TAG, stringBuilder.toString());
                    }
                    this.setForeground(false, 0, l);
                }
            }
            this.mOwner = null;
        }
    }

    public void commitStateTime(long l) {
        int n = this.mRunState;
        if (n != -1) {
            this.mDurations.addDuration(n * 5 + 0, l - this.mRunStartTime);
            this.mRunStartTime = l;
        }
        if ((n = this.mStartedState) != -1) {
            this.mDurations.addDuration(n * 5 + 1, l - this.mStartedStartTime);
            this.mStartedStartTime = l;
        }
        if ((n = this.mBoundState) != -1) {
            this.mDurations.addDuration(n * 5 + 2, l - this.mBoundStartTime);
            this.mBoundStartTime = l;
        }
        if ((n = this.mExecState) != -1) {
            this.mDurations.addDuration(n * 5 + 3, l - this.mExecStartTime);
            this.mExecStartTime = l;
        }
        if ((n = this.mForegroundState) != -1) {
            this.mDurations.addDuration(n * 5 + 4, l - this.mForegroundStartTime);
            this.mForegroundStartTime = l;
        }
    }

    public void dumpStats(PrintWriter printWriter, String object, String string2, String string3, long l, long l2, boolean bl, boolean bl2) {
        block8 : {
            int n;
            int n2;
            long l3;
            block7 : {
                block6 : {
                    n2 = this.mRunCount;
                    n = this.mRunState;
                    l3 = this.mRunStartTime;
                    boolean bl3 = false;
                    boolean bl4 = !bl || bl2;
                    this.dumpStats(printWriter, (String)object, string2, string3, "Running", n2, 0, n, l3, l, l2, bl4);
                    n2 = this.mStartedCount;
                    n = this.mStartedState;
                    l3 = this.mStartedStartTime;
                    bl4 = !bl || bl2;
                    this.dumpStats(printWriter, (String)object, string2, string3, "Started", n2, 1, n, l3, l, l2, bl4);
                    n2 = this.mForegroundCount;
                    n = this.mForegroundState;
                    l3 = this.mForegroundStartTime;
                    bl4 = !bl || bl2;
                    this.dumpStats(printWriter, (String)object, string2, string3, "Foreground", n2, 4, n, l3, l, l2, bl4);
                    n = this.mBoundCount;
                    n2 = this.mBoundState;
                    l3 = this.mBoundStartTime;
                    bl4 = !bl || bl2;
                    this.dumpStats(printWriter, (String)object, string2, string3, "Bound", n, 2, n2, l3, l, l2, bl4);
                    n2 = this.mExecCount;
                    n = this.mExecState;
                    l3 = this.mExecStartTime;
                    if (!bl) break block6;
                    bl = bl3;
                    if (!bl2) break block7;
                }
                bl = true;
            }
            this.dumpStats(printWriter, (String)object, string2, string3, "Executing", n2, 3, n, l3, l, l2, bl);
            if (!bl2) break block8;
            if (this.mOwner != null) {
                object = printWriter;
                ((PrintWriter)object).print("        mOwner=");
                ((PrintWriter)object).println(this.mOwner);
            }
            if (this.mStarted || this.mRestarting) {
                printWriter.print("        mStarted=");
                printWriter.print(this.mStarted);
                printWriter.print(" mRestarting=");
                printWriter.println(this.mRestarting);
            }
        }
    }

    public long dumpTime(PrintWriter printWriter, String string2, int n, int n2, long l, long l2) {
        return this.dumpTimeInternal(printWriter, string2, n, n2, l, l2, false);
    }

    long dumpTimeInternal(PrintWriter printWriter, String string2, int n, int n2, long l, long l2, boolean bl) {
        long l3;
        block7 : {
            l3 = 0L;
            int n3 = -1;
            int n4 = 0;
            for (int i = 0; i < 8; i += 4) {
                int n5 = -1;
                long l4 = l3;
                for (int j = 0; j < 4; ++j) {
                    int n6 = j + i;
                    long l5 = this.getDuration(n, n2, l, n6, l2);
                    String string3 = "";
                    int n7 = n4;
                    String string4 = string3;
                    if (n2 == n6) {
                        n7 = n4;
                        string4 = string3;
                        if (printWriter != null) {
                            string4 = " (running)";
                            n7 = 1;
                        }
                    }
                    l3 = l4;
                    n4 = n3;
                    n6 = n5;
                    if (l5 != 0L) {
                        n4 = n3;
                        n6 = n5;
                        if (printWriter != null) {
                            printWriter.print(string2);
                            n3 = n3 != i ? i : -1;
                            DumpUtils.printScreenLabel(printWriter, n3);
                            n4 = i;
                            n5 = n5 != j ? j : -1;
                            DumpUtils.printMemLabel(printWriter, n5, '\u0000');
                            n6 = j;
                            printWriter.print(": ");
                            TimeUtils.formatDuration(l5, printWriter);
                            printWriter.println(string4);
                        }
                        l3 = l4 + l5;
                    }
                    l4 = l3;
                    n3 = n4;
                    n4 = n7;
                    n5 = n6;
                }
                l3 = l4;
            }
            if (l3 != 0L && printWriter != null) {
                printWriter.print(string2);
                printWriter.print("    TOTAL: ");
                TimeUtils.formatDuration(l3, printWriter);
                printWriter.println();
            }
            if (n4 == 0 || !bl) break block7;
            l3 = -l3;
        }
        return l3;
    }

    public void dumpTimesCheckin(PrintWriter printWriter, String string2, int n, long l, String string3, long l2) {
        this.dumpTimeCheckin(printWriter, "pkgsvc-run", string2, n, l, string3, 0, this.mRunCount, this.mRunState, this.mRunStartTime, l2);
        this.dumpTimeCheckin(printWriter, "pkgsvc-start", string2, n, l, string3, 1, this.mStartedCount, this.mStartedState, this.mStartedStartTime, l2);
        this.dumpTimeCheckin(printWriter, "pkgsvc-fg", string2, n, l, string3, 4, this.mForegroundCount, this.mForegroundState, this.mForegroundStartTime, l2);
        this.dumpTimeCheckin(printWriter, "pkgsvc-bound", string2, n, l, string3, 2, this.mBoundCount, this.mBoundState, this.mBoundStartTime, l2);
        this.dumpTimeCheckin(printWriter, "pkgsvc-exec", string2, n, l, string3, 3, this.mExecCount, this.mExecState, this.mExecStartTime, l2);
    }

    public long getDuration(int n, int n2, long l, int n3, long l2) {
        long l3;
        long l4 = l3 = this.mDurations.getValueForId((byte)(n3 * 5 + n));
        if (n2 == n3) {
            l4 = l3 + (l2 - l);
        }
        return l4;
    }

    public String getName() {
        return this.mName;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public ProcessState getProcess() {
        return this.mProc;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public boolean isInUse() {
        boolean bl = this.mOwner != null || this.mRestarting;
        return bl;
    }

    public boolean isRestarting() {
        return this.mRestarting;
    }

    public boolean readFromParcel(Parcel parcel) {
        if (!this.mDurations.readFromParcel(parcel)) {
            return false;
        }
        this.mRunCount = parcel.readInt();
        this.mStartedCount = parcel.readInt();
        this.mBoundCount = parcel.readInt();
        this.mExecCount = parcel.readInt();
        this.mForegroundCount = parcel.readInt();
        return true;
    }

    public void resetSafely(long l) {
        this.mDurations.resetTable();
        int n = this.mRunState;
        int n2 = 1;
        n = n != -1 ? 1 : 0;
        this.mRunCount = n;
        n = this.mStartedState != -1 ? 1 : 0;
        this.mStartedCount = n;
        n = this.mBoundState != -1 ? 1 : 0;
        this.mBoundCount = n;
        n = this.mExecState != -1 ? 1 : 0;
        this.mExecCount = n;
        n = this.mForegroundState != -1 ? n2 : 0;
        this.mForegroundCount = n;
        this.mForegroundStartTime = l;
        this.mExecStartTime = l;
        this.mBoundStartTime = l;
        this.mStartedStartTime = l;
        this.mRunStartTime = l;
    }

    public void setBound(boolean bl, int n, long l) {
        int n2;
        int n3;
        if (this.mOwner == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Binding service ");
            stringBuilder.append(this);
            stringBuilder.append(" without owner");
            Slog.wtf(TAG, stringBuilder.toString());
        }
        if ((n2 = this.mBoundState) != (n3 = bl ? n : -1)) {
            if (n2 != -1) {
                this.mDurations.addDuration(n2 * 5 + 2, l - this.mBoundStartTime);
            } else if (bl) {
                ++this.mBoundCount;
            }
            this.mBoundState = n3;
            this.mBoundStartTime = l;
            this.updateRunning(n, l);
        }
    }

    public void setExecuting(boolean bl, int n, long l) {
        int n2;
        int n3;
        if (this.mOwner == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Executing service ");
            stringBuilder.append(this);
            stringBuilder.append(" without owner");
            Slog.wtf(TAG, stringBuilder.toString());
        }
        if ((n2 = this.mExecState) != (n3 = bl ? n : -1)) {
            if (n2 != -1) {
                this.mDurations.addDuration(n2 * 5 + 3, l - this.mExecStartTime);
            } else if (bl) {
                ++this.mExecCount;
            }
            this.mExecState = n3;
            this.mExecStartTime = l;
            this.updateRunning(n, l);
        }
    }

    public void setForeground(boolean bl, int n, long l) {
        int n2;
        int n3;
        if (this.mOwner == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Foregrounding service ");
            stringBuilder.append(this);
            stringBuilder.append(" without owner");
            Slog.wtf(TAG, stringBuilder.toString());
        }
        if ((n2 = this.mForegroundState) != (n3 = bl ? n : -1)) {
            if (n2 != -1) {
                this.mDurations.addDuration(n2 * 5 + 4, l - this.mForegroundStartTime);
            } else if (bl) {
                ++this.mForegroundCount;
            }
            this.mForegroundState = n3;
            this.mForegroundStartTime = l;
            this.updateRunning(n, l);
        }
    }

    public void setMemFactor(int n, long l) {
        if (this.isRestarting()) {
            this.setRestarting(true, n, l);
        } else if (this.isInUse()) {
            if (this.mStartedState != -1) {
                this.setStarted(true, n, l);
            }
            if (this.mBoundState != -1) {
                this.setBound(true, n, l);
            }
            if (this.mExecState != -1) {
                this.setExecuting(true, n, l);
            }
            if (this.mForegroundState != -1) {
                this.setForeground(true, n, l);
            }
        }
    }

    public void setProcess(ProcessState processState) {
        this.mProc = processState;
    }

    public void setRestarting(boolean bl, int n, long l) {
        this.mRestarting = bl;
        this.updateStartedState(n, l);
    }

    public void setStarted(boolean bl, int n, long l) {
        if (this.mOwner == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Starting service ");
            stringBuilder.append(this);
            stringBuilder.append(" without owner");
            Slog.wtf(TAG, stringBuilder.toString());
        }
        this.mStarted = bl;
        this.updateStartedState(n, l);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServiceState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.mName);
        stringBuilder.append(" pkg=");
        stringBuilder.append(this.mPackage);
        stringBuilder.append(" proc=");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this.mProc)));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void updateStartedState(int n, long l) {
        int n2;
        int n3;
        int n4 = this.mStartedState;
        int n5 = 0;
        n4 = n4 != -1 ? 1 : 0;
        if (this.mStarted || this.mRestarting) {
            n5 = 1;
        }
        if ((n2 = this.mStartedState) != (n3 = n5 != 0 ? n : -1)) {
            if (n2 != -1) {
                this.mDurations.addDuration(n2 * 5 + 1, l - this.mStartedStartTime);
            } else if (n5 != 0) {
                ++this.mStartedCount;
            }
            this.mStartedState = n3;
            this.mStartedStartTime = l;
            this.mProc = this.mProc.pullFixedProc(this.mPackage);
            if (n4 != n5) {
                if (n5 != 0) {
                    this.mProc.incStartedServices(n, l, this.mName);
                } else {
                    this.mProc.decStartedServices(n, l, this.mName);
                }
            }
            this.updateRunning(n, l);
        }
    }

    public void writeToParcel(Parcel parcel, long l) {
        this.mDurations.writeToParcel(parcel);
        parcel.writeInt(this.mRunCount);
        parcel.writeInt(this.mStartedCount);
        parcel.writeInt(this.mBoundCount);
        parcel.writeInt(this.mExecCount);
        parcel.writeInt(this.mForegroundCount);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, long l2) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.mName);
        this.writeTypeToProto(protoOutputStream, 2246267895810L, 1, 0, this.mRunCount, this.mRunState, this.mRunStartTime, l2);
        this.writeTypeToProto(protoOutputStream, 2246267895810L, 2, 1, this.mStartedCount, this.mStartedState, this.mStartedStartTime, l2);
        this.writeTypeToProto(protoOutputStream, 2246267895810L, 3, 4, this.mForegroundCount, this.mForegroundState, this.mForegroundStartTime, l2);
        this.writeTypeToProto(protoOutputStream, 2246267895810L, 4, 2, this.mBoundCount, this.mBoundState, this.mBoundStartTime, l2);
        this.writeTypeToProto(protoOutputStream, 2246267895810L, 5, 3, this.mExecCount, this.mExecState, this.mExecStartTime, l2);
        protoOutputStream.end(l);
    }

    public void writeTypeToProto(ProtoOutputStream protoOutputStream, long l, int n, int n2, int n3, int n4, long l2, long l3) {
        if (n3 <= 0) {
            return;
        }
        long l4 = protoOutputStream.start(l);
        protoOutputStream.write(1159641169921L, n);
        protoOutputStream.write(1120986464258L, n3);
        int n5 = this.mDurations.getKeyCount();
        n = 0;
        for (n3 = 0; n3 < n5; ++n3) {
            int n6 = this.mDurations.getKeyAt(n3);
            l = this.mDurations.getValue(n6);
            n6 = SparseMappingTable.getIdFromKey(n6);
            int n7 = n6 / 5;
            if ((n6 %= 5) != n2) continue;
            if (n4 == n7) {
                n = 1;
                l += l3 - l2;
            }
            long l5 = protoOutputStream.start(2246267895811L);
            DumpUtils.printProcStateAdjTagProto(protoOutputStream, 1159641169921L, 1159641169922L, n6);
            protoOutputStream.write(1112396529667L, l);
            protoOutputStream.end(l5);
        }
        if (n == 0 && n4 != -1) {
            l = protoOutputStream.start(2246267895811L);
            DumpUtils.printProcStateAdjTagProto(protoOutputStream, 1159641169921L, 1159641169922L, n4);
            protoOutputStream.write(1112396529667L, l3 - l2);
            protoOutputStream.end(l);
        }
        protoOutputStream.end(l4);
    }
}

