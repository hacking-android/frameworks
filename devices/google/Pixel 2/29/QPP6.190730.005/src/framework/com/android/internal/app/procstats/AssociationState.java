/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.DurationsTable;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.internal.app.procstats.SparseMappingTable;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public final class AssociationState {
    private static final boolean DEBUG = false;
    private static final String TAG = "ProcessStats";
    private final String mName;
    private int mNumActive;
    private final ProcessStats.PackageState mPackageState;
    private ProcessState mProc;
    private final String mProcessName;
    private final ProcessStats mProcessStats;
    private final ArrayMap<SourceKey, SourceState> mSources = new ArrayMap();
    private final SourceKey mTmpSourceKey = new SourceKey(0, null, null);

    public AssociationState(ProcessStats processStats, ProcessStats.PackageState packageState, String string2, String string3, ProcessState processState) {
        this.mProcessStats = processStats;
        this.mPackageState = packageState;
        this.mName = string2;
        this.mProcessName = string3;
        this.mProc = processState;
    }

    static /* synthetic */ ProcessStats access$000(AssociationState associationState) {
        return associationState.mProcessStats;
    }

    static /* synthetic */ int access$110(AssociationState associationState) {
        int n = associationState.mNumActive;
        associationState.mNumActive = n - 1;
        return n;
    }

    public void add(AssociationState associationState) {
        for (int i = associationState.mSources.size() - 1; i >= 0; --i) {
            SourceState sourceState;
            SourceKey sourceKey = associationState.mSources.keyAt(i);
            SourceState sourceState2 = associationState.mSources.valueAt(i);
            SourceState sourceState3 = sourceState = this.mSources.get(sourceKey);
            if (sourceState == null) {
                sourceState3 = new SourceState(sourceKey);
                this.mSources.put(sourceKey, sourceState3);
            }
            sourceState3.mCount += sourceState2.mCount;
            sourceState3.mDuration += sourceState2.mDuration;
            sourceState3.mActiveCount += sourceState2.mActiveCount;
            if (sourceState2.mActiveDuration == 0L && sourceState2.mDurations == null) continue;
            if (sourceState3.mDurations != null) {
                if (sourceState2.mDurations != null) {
                    sourceState3.mDurations.addDurations(sourceState2.mDurations);
                    continue;
                }
                sourceState3.mDurations.addDuration(sourceState2.mActiveProcState, sourceState2.mActiveDuration);
                continue;
            }
            if (sourceState2.mDurations != null) {
                sourceState3.makeDurations();
                sourceState3.mDurations.addDurations(sourceState2.mDurations);
                if (sourceState3.mActiveDuration == 0L) continue;
                sourceState3.mDurations.addDuration(sourceState3.mActiveProcState, sourceState3.mActiveDuration);
                sourceState3.mActiveDuration = 0L;
                sourceState3.mActiveProcState = -1;
                continue;
            }
            if (sourceState3.mActiveDuration != 0L) {
                if (sourceState3.mActiveProcState == sourceState2.mActiveProcState) {
                    sourceState3.mDuration += sourceState2.mDuration;
                    continue;
                }
                sourceState3.makeDurations();
                sourceState3.mDurations.addDuration(sourceState3.mActiveProcState, sourceState3.mActiveDuration);
                sourceState3.mDurations.addDuration(sourceState2.mActiveProcState, sourceState2.mActiveDuration);
                sourceState3.mActiveDuration = 0L;
                sourceState3.mActiveProcState = -1;
                continue;
            }
            sourceState3.mActiveProcState = sourceState2.mActiveProcState;
            sourceState3.mActiveDuration = sourceState2.mActiveDuration;
        }
    }

    public void commitStateTime(long l) {
        if (this.isInUse()) {
            for (int i = this.mSources.size() - 1; i >= 0; --i) {
                SourceState sourceState = this.mSources.valueAt(i);
                if (sourceState.mNesting > 0) {
                    sourceState.mDuration += l - sourceState.mStartUptime;
                    sourceState.mStartUptime = l;
                }
                if (sourceState.mActiveStartUptime <= 0L) continue;
                long l2 = sourceState.mActiveDuration + l - sourceState.mActiveStartUptime;
                if (sourceState.mDurations != null) {
                    sourceState.mDurations.addDuration(sourceState.mActiveProcState, l2);
                } else {
                    sourceState.mActiveDuration = l2;
                }
                sourceState.mActiveStartUptime = l;
            }
        }
    }

    void dumpActiveDurationSummary(PrintWriter printWriter, SourceState sourceState, long l, long l2, boolean bl) {
        long l3 = this.dumpTime(null, null, sourceState, l, l2, false, false);
        boolean bl2 = l3 < 0L;
        l2 = l3;
        if (bl2) {
            l2 = -l3;
        }
        if (bl) {
            printWriter.print("Duration ");
            TimeUtils.formatDuration(l2, printWriter);
            printWriter.print(" / ");
        } else {
            printWriter.print("time ");
        }
        DumpUtils.printPercent(printWriter, (double)l2 / (double)l);
        if (sourceState.mActiveStartUptime > 0L) {
            printWriter.print(" (running)");
        }
        printWriter.println();
    }

    public void dumpStats(PrintWriter printWriter, String string2, String string3, String object, long l, long l2, String string4, boolean bl, boolean bl2) {
        object = this;
        if (bl2) {
            printWriter.print(string2);
            printWriter.print("mNumActive=");
            printWriter.println(((AssociationState)object).mNumActive);
        }
        int n = ((AssociationState)object).mSources.size();
        int n2 = 0;
        do {
            string2 = string4;
            object = this;
            if (n2 >= n) break;
            SourceKey sourceKey = ((AssociationState)object).mSources.keyAt(n2);
            object = ((AssociationState)object).mSources.valueAt(n2);
            if (string2 == null || string2.equals(sourceKey.mProcess) || string2.equals(sourceKey.mPackage)) {
                printWriter.print(string3);
                printWriter.print("<- ");
                printWriter.print(sourceKey.mProcess);
                printWriter.print("/");
                UserHandle.formatUid(printWriter, sourceKey.mUid);
                if (sourceKey.mPackage != null) {
                    printWriter.print(" (");
                    printWriter.print(sourceKey.mPackage);
                    printWriter.print(")");
                }
                printWriter.println(":");
                printWriter.print(string3);
                printWriter.print("   Total count ");
                printWriter.print(((SourceState)object).mCount);
                long l3 = ((SourceState)object).mDuration;
                if (((SourceState)object).mNesting > 0) {
                    l3 += l - ((SourceState)object).mStartUptime;
                }
                if (bl2) {
                    printWriter.print(": Duration ");
                    TimeUtils.formatDuration(l3, printWriter);
                    printWriter.print(" / ");
                } else {
                    printWriter.print(": time ");
                }
                DumpUtils.printPercent(printWriter, (double)l3 / (double)l2);
                if (((SourceState)object).mNesting > 0) {
                    printWriter.print(" (running");
                    if (((SourceState)object).mProcState != -1) {
                        printWriter.print(" / ");
                        printWriter.print(DumpUtils.STATE_NAMES[((SourceState)object).mProcState]);
                        printWriter.print(" #");
                        printWriter.print(((SourceState)object).mProcStateSeq);
                    }
                    printWriter.print(")");
                }
                printWriter.println();
                if (((SourceState)object).mActiveCount > 0 || ((SourceState)object).mDurations != null || ((SourceState)object).mActiveDuration != 0L || ((SourceState)object).mActiveStartUptime != 0L) {
                    printWriter.print(string3);
                    printWriter.print("   Active count ");
                    printWriter.print(((SourceState)object).mActiveCount);
                    if (bl) {
                        if (bl2) {
                            string2 = ((SourceState)object).mDurations != null ? " (multi-field)" : " (inline)";
                            printWriter.print(string2);
                        }
                        printWriter.println(":");
                        this.dumpTime(printWriter, string3, (SourceState)object, l2, l, bl, bl2);
                    } else {
                        printWriter.print(": ");
                        this.dumpActiveDurationSummary(printWriter, (SourceState)object, l2, l, bl2);
                        printWriter.println();
                    }
                }
                if (bl2) {
                    if (((SourceState)object).mInTrackingList) {
                        printWriter.print(string3);
                        printWriter.print("   mInTrackingList=");
                        printWriter.println(((SourceState)object).mInTrackingList);
                    }
                    if (((SourceState)object).mProcState != -1) {
                        printWriter.print(string3);
                        printWriter.print("   mProcState=");
                        printWriter.print(DumpUtils.STATE_NAMES[((SourceState)object).mProcState]);
                        printWriter.print(" mProcStateSeq=");
                        printWriter.println(((SourceState)object).mProcStateSeq);
                    }
                }
            }
            ++n2;
        } while (true);
    }

    long dumpTime(PrintWriter printWriter, String string2, SourceState sourceState, long l, long l2, boolean bl, boolean bl2) {
        long l3;
        block10 : {
            l3 = 0L;
            boolean bl3 = false;
            for (int i = 0; i < 14; ++i) {
                String string3;
                long l4 = sourceState.mDurations != null ? sourceState.mDurations.getValueForId((byte)i) : (sourceState.mActiveProcState == i ? sourceState.mDuration : 0L);
                if (sourceState.mActiveStartUptime != 0L && sourceState.mActiveProcState == i) {
                    string3 = " (running)";
                    bl3 = true;
                    l4 += l2 - sourceState.mActiveStartUptime;
                } else {
                    string3 = null;
                }
                if (l4 == 0L) continue;
                if (printWriter != null) {
                    printWriter.print(string2);
                    printWriter.print("  ");
                    printWriter.print(DumpUtils.STATE_LABELS[i]);
                    printWriter.print(": ");
                    if (bl2) {
                        printWriter.print("Duration ");
                        TimeUtils.formatDuration(l4, printWriter);
                        printWriter.print(" / ");
                    } else {
                        printWriter.print("time ");
                    }
                    DumpUtils.printPercent(printWriter, (double)l4 / (double)l);
                    if (string3 != null) {
                        printWriter.print(string3);
                    }
                    printWriter.println();
                }
                l3 += l4;
            }
            if (l3 != 0L && printWriter != null) {
                printWriter.print(string2);
                printWriter.print("  ");
                printWriter.print(DumpUtils.STATE_LABEL_TOTAL);
                printWriter.print(": ");
                if (bl2) {
                    printWriter.print("Duration ");
                    TimeUtils.formatDuration(l3, printWriter);
                    printWriter.print(" / ");
                } else {
                    printWriter.print("time ");
                }
                DumpUtils.printPercent(printWriter, (double)l3 / (double)l);
                printWriter.println();
            }
            if (!bl3) break block10;
            l3 = -l3;
        }
        return l3;
    }

    public void dumpTimesCheckin(PrintWriter printWriter, String string2, int n, long l, String string3, long l2) {
        int n2 = this.mSources.size();
        int n3 = 0;
        do {
            int n4;
            long l3;
            Object object = this;
            if (n3 >= n2) break;
            SourceKey sourceKey = ((AssociationState)object).mSources.keyAt(n3);
            object = ((AssociationState)object).mSources.valueAt(n3);
            printWriter.print("pkgasc");
            printWriter.print(",");
            printWriter.print(string2);
            printWriter.print(",");
            printWriter.print(n);
            printWriter.print(",");
            printWriter.print(l);
            printWriter.print(",");
            printWriter.print(string3);
            printWriter.print(",");
            printWriter.print(sourceKey.mProcess);
            printWriter.print(",");
            printWriter.print(sourceKey.mUid);
            printWriter.print(",");
            printWriter.print(((SourceState)object).mCount);
            long l4 = l3 = ((SourceState)object).mDuration;
            if (((SourceState)object).mNesting > 0) {
                l4 = l3 + (l2 - ((SourceState)object).mStartUptime);
            }
            printWriter.print(",");
            printWriter.print(l4);
            printWriter.print(",");
            printWriter.print(((SourceState)object).mActiveCount);
            l3 = ((SourceState)object).mActiveStartUptime != 0L ? l2 - ((SourceState)object).mActiveStartUptime : 0L;
            if (((SourceState)object).mDurations != null) {
                int n5 = ((SourceState)object).mDurations.getKeyCount();
                for (n4 = 0; n4 < n5; ++n4) {
                    int n6 = ((SourceState)object).mDurations.getKeyAt(n4);
                    l4 = ((SourceState)object).mDurations.getValue(n6);
                    if (n6 == ((SourceState)object).mActiveProcState) {
                        l4 += l3;
                    }
                    n6 = SparseMappingTable.getIdFromKey(n6);
                    printWriter.print(",");
                    DumpUtils.printArrayEntry(printWriter, DumpUtils.STATE_TAGS, n6, 1);
                    printWriter.print(':');
                    printWriter.print(l4);
                }
            } else {
                n4 = n2;
                l4 = ((SourceState)object).mActiveDuration + l3;
                n2 = n4;
                if (l4 != 0L) {
                    printWriter.print(",");
                    DumpUtils.printArrayEntry(printWriter, DumpUtils.STATE_TAGS, ((SourceState)object).mActiveProcState, 1);
                    printWriter.print(':');
                    printWriter.print(l4);
                    n2 = n4;
                }
            }
            printWriter.println();
            ++n3;
        } while (true);
    }

    public String getName() {
        return this.mName;
    }

    public String getPackage() {
        return this.mPackageState.mPackageName;
    }

    public ProcessState getProcess() {
        return this.mProc;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public int getUid() {
        return this.mPackageState.mUid;
    }

    public boolean hasProcessOrPackage(String string2) {
        int n = this.mSources.size();
        for (int i = 0; i < n; ++i) {
            SourceKey sourceKey = this.mSources.keyAt(i);
            if (!string2.equals(sourceKey.mProcess) && !string2.equals(sourceKey.mPackage)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean isInUse() {
        boolean bl = this.mNumActive > 0;
        return bl;
    }

    public String readFromParcel(ProcessStats object, Parcel parcel, int n) {
        int n2 = parcel.readInt();
        if (n2 >= 0 && n2 <= 100000) {
            for (int i = 0; i < n2; ++i) {
                SourceKey sourceKey = new SourceKey(parcel.readInt(), ((ProcessStats)object).readCommonString(parcel, n), ((ProcessStats)object).readCommonString(parcel, n));
                SourceState sourceState = new SourceState(sourceKey);
                sourceState.mCount = parcel.readInt();
                sourceState.mDuration = parcel.readLong();
                sourceState.mActiveCount = parcel.readInt();
                if (parcel.readInt() != 0) {
                    sourceState.makeDurations();
                    if (!sourceState.mDurations.readFromParcel(parcel)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Duration table corrupt: ");
                        ((StringBuilder)object).append(sourceKey);
                        ((StringBuilder)object).append(" <- ");
                        ((StringBuilder)object).append(sourceState);
                        return ((StringBuilder)object).toString();
                    }
                } else {
                    sourceState.mActiveProcState = parcel.readInt();
                    sourceState.mActiveDuration = parcel.readLong();
                }
                this.mSources.put(sourceKey, sourceState);
            }
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Association with bad src count: ");
        ((StringBuilder)object).append(n2);
        return ((StringBuilder)object).toString();
    }

    public void resetSafely(long l) {
        if (!this.isInUse()) {
            this.mSources.clear();
        } else {
            for (int i = this.mSources.size() - 1; i >= 0; --i) {
                SourceState sourceState = this.mSources.valueAt(i);
                if (sourceState.mNesting > 0) {
                    sourceState.mCount = 1;
                    sourceState.mStartUptime = l;
                    sourceState.mDuration = 0L;
                    if (sourceState.mActiveStartUptime > 0L) {
                        sourceState.mActiveCount = 1;
                        sourceState.mActiveStartUptime = l;
                    } else {
                        sourceState.mActiveCount = 0;
                    }
                    sourceState.mActiveDuration = 0L;
                    sourceState.mDurations = null;
                    continue;
                }
                this.mSources.removeAt(i);
            }
        }
    }

    public void setProcess(ProcessState processState) {
        this.mProc = processState;
    }

    public SourceState startSource(int n, String object, String string2) {
        Object object2 = this.mTmpSourceKey;
        ((SourceKey)object2).mUid = n;
        ((SourceKey)object2).mProcess = object;
        ((SourceKey)object2).mPackage = string2;
        SourceState sourceState = this.mSources.get(object2);
        object2 = sourceState;
        if (sourceState == null) {
            object = new SourceKey(n, (String)object, string2);
            object2 = new SourceState((SourceKey)object);
            this.mSources.put((SourceKey)object, (SourceState)object2);
        }
        ++((SourceState)object2).mNesting;
        if (((SourceState)object2).mNesting == 1) {
            ++((SourceState)object2).mCount;
            ((SourceState)object2).mStartUptime = SystemClock.uptimeMillis();
            ++this.mNumActive;
        }
        return object2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AssociationState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.mName);
        stringBuilder.append(" pkg=");
        stringBuilder.append(this.mPackageState.mPackageName);
        stringBuilder.append(" proc=");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this.mProc)));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(ProcessStats processStats, Parcel parcel, long l) {
        int n = this.mSources.size();
        parcel.writeInt(n);
        for (int i = 0; i < n; ++i) {
            SourceKey sourceKey = this.mSources.keyAt(i);
            SourceState sourceState = this.mSources.valueAt(i);
            parcel.writeInt(sourceKey.mUid);
            processStats.writeCommonString(parcel, sourceKey.mProcess);
            processStats.writeCommonString(parcel, sourceKey.mPackage);
            parcel.writeInt(sourceState.mCount);
            parcel.writeLong(sourceState.mDuration);
            parcel.writeInt(sourceState.mActiveCount);
            if (sourceState.mDurations != null) {
                parcel.writeInt(1);
                sourceState.mDurations.writeToParcel(parcel);
                continue;
            }
            parcel.writeInt(0);
            parcel.writeInt(sourceState.mActiveProcState);
            parcel.writeLong(sourceState.mActiveDuration);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l, long l2) {
        Object object = this;
        ProtoOutputStream protoOutputStream2 = protoOutputStream;
        l = protoOutputStream.start(l);
        protoOutputStream2.write(1138166333441L, ((AssociationState)object).mName);
        int n = ((AssociationState)object).mSources.size();
        int n2 = 0;
        do {
            long l3;
            int n3;
            object = this;
            if (n2 >= n) break;
            int[] arrn = ((AssociationState)object).mSources.keyAt(n2);
            object = ((AssociationState)object).mSources.valueAt(n2);
            long l4 = protoOutputStream2.start(2246267895810L);
            protoOutputStream2.write(1138166333442L, arrn.mProcess);
            protoOutputStream2.write(1138166333447L, arrn.mPackage);
            protoOutputStream2.write(1120986464257L, arrn.mUid);
            protoOutputStream2.write(1120986464259L, ((SourceState)object).mCount);
            long l5 = l3 = ((SourceState)object).mDuration;
            if (((SourceState)object).mNesting > 0) {
                l5 = l3 + (l2 - ((SourceState)object).mStartUptime);
            }
            protoOutputStream2.write(1112396529668L, l5);
            if (((SourceState)object).mActiveCount != 0) {
                protoOutputStream2.write(1120986464261L, ((SourceState)object).mActiveCount);
            }
            l5 = ((SourceState)object).mActiveStartUptime != 0L ? l2 - ((SourceState)object).mActiveStartUptime : 0L;
            if (((SourceState)object).mDurations != null) {
                n3 = ((SourceState)object).mDurations.getKeyCount();
                for (int i = 0; i < n3; ++i) {
                    long l6;
                    int n4 = ((SourceState)object).mDurations.getKeyAt(i);
                    l3 = l6 = ((SourceState)object).mDurations.getValue(n4);
                    if (n4 == ((SourceState)object).mActiveProcState) {
                        l3 = l6 + l5;
                    }
                    n4 = SparseMappingTable.getIdFromKey(n4);
                    l6 = protoOutputStream.start(2246267895814L);
                    DumpUtils.printProto(protoOutputStream, 1159641169921L, DumpUtils.STATE_PROTO_ENUMS, n4, 1);
                    protoOutputStream.write(1112396529666L, l3);
                    protoOutputStream.end(l6);
                }
            } else if ((l5 = ((SourceState)object).mActiveDuration + l5) != 0L) {
                l3 = protoOutputStream.start(2246267895814L);
                arrn = DumpUtils.STATE_PROTO_ENUMS;
                n3 = ((SourceState)object).mActiveProcState;
                protoOutputStream2 = protoOutputStream;
                DumpUtils.printProto(protoOutputStream, 1159641169921L, arrn, n3, 1);
                protoOutputStream2.write(1112396529666L, l5);
                protoOutputStream2.end(l3);
            }
            protoOutputStream2 = protoOutputStream;
            protoOutputStream2.end(l4);
            ++n2;
        } while (true);
        protoOutputStream2.end(l);
    }

    private static final class SourceKey {
        String mPackage;
        String mProcess;
        int mUid;

        SourceKey(int n, String string2, String string3) {
            this.mUid = n;
            this.mProcess = string2;
            this.mPackage = string3;
        }

        public boolean equals(Object object) {
            boolean bl;
            block1 : {
                boolean bl2 = object instanceof SourceKey;
                bl = false;
                if (!bl2) {
                    return false;
                }
                object = (SourceKey)object;
                if (((SourceKey)object).mUid != this.mUid || !Objects.equals(((SourceKey)object).mProcess, this.mProcess) || !Objects.equals(((SourceKey)object).mPackage, this.mPackage)) break block1;
                bl = true;
            }
            return bl;
        }

        public int hashCode() {
            int n = Integer.hashCode(this.mUid);
            String string2 = this.mProcess;
            int n2 = 0;
            int n3 = string2 == null ? 0 : string2.hashCode();
            string2 = this.mPackage;
            if (string2 != null) {
                n2 = string2.hashCode() * 33;
            }
            return n ^ n3 ^ n2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("SourceKey{");
            UserHandle.formatUid(stringBuilder, this.mUid);
            stringBuilder.append(' ');
            stringBuilder.append(this.mProcess);
            stringBuilder.append(' ');
            stringBuilder.append(this.mPackage);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public final class SourceState {
        int mActiveCount;
        long mActiveDuration;
        int mActiveProcState = -1;
        long mActiveStartUptime;
        int mCount;
        long mDuration;
        DurationsTable mDurations;
        boolean mInTrackingList;
        final SourceKey mKey;
        int mNesting;
        int mProcState = -1;
        int mProcStateSeq = -1;
        long mStartUptime;
        long mTrackingUptime;

        SourceState(SourceKey sourceKey) {
            this.mKey = sourceKey;
        }

        public AssociationState getAssociationState() {
            return AssociationState.this;
        }

        public String getProcessName() {
            return this.mKey.mProcess;
        }

        public int getUid() {
            return this.mKey.mUid;
        }

        void makeDurations() {
            this.mDurations = new DurationsTable(AssociationState.access$000((AssociationState)AssociationState.this).mTableData);
        }

        void startActive(long l) {
            if (this.mInTrackingList) {
                int n;
                if (this.mActiveStartUptime == 0L) {
                    this.mActiveStartUptime = l;
                    ++this.mActiveCount;
                }
                if ((n = this.mActiveProcState) != this.mProcState) {
                    if (n != -1) {
                        long l2 = this.mActiveDuration + l - this.mActiveStartUptime;
                        if (l2 != 0L) {
                            if (this.mDurations == null) {
                                this.makeDurations();
                            }
                            this.mDurations.addDuration(this.mActiveProcState, l2);
                            this.mActiveDuration = 0L;
                        }
                        this.mActiveStartUptime = l;
                    }
                    this.mActiveProcState = this.mProcState;
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("startActive while not tracking: ");
                stringBuilder.append(this);
                Slog.wtf(AssociationState.TAG, stringBuilder.toString());
            }
        }

        public void stop() {
            --this.mNesting;
            if (this.mNesting == 0) {
                this.mDuration += SystemClock.uptimeMillis() - this.mStartUptime;
                AssociationState.access$110(AssociationState.this);
                this.stopTracking(SystemClock.uptimeMillis());
            }
        }

        void stopActive(long l) {
            if (this.mActiveStartUptime != 0L) {
                Object object;
                if (!this.mInTrackingList) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("stopActive while not tracking: ");
                    ((StringBuilder)object).append(this);
                    Slog.wtf(AssociationState.TAG, ((StringBuilder)object).toString());
                }
                l = this.mActiveDuration + l - this.mActiveStartUptime;
                object = this.mDurations;
                if (object != null) {
                    ((DurationsTable)object).addDuration(this.mActiveProcState, l);
                } else {
                    this.mActiveDuration = l;
                }
                this.mActiveStartUptime = 0L;
            }
        }

        void stopTracking(long l) {
            this.stopActive(l);
            if (this.mInTrackingList) {
                this.mInTrackingList = false;
                this.mProcState = -1;
                Serializable serializable = AssociationState.access$000((AssociationState)AssociationState.this).mTrackingAssociations;
                for (int i = serializable.size() - 1; i >= 0; --i) {
                    if (((ArrayList)serializable).get(i) != this) continue;
                    ((ArrayList)serializable).remove(i);
                    return;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Stop tracking didn't find in tracking list: ");
                ((StringBuilder)serializable).append(this);
                Slog.wtf(AssociationState.TAG, ((StringBuilder)serializable).toString());
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("SourceState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" ");
            stringBuilder.append(this.mKey.mProcess);
            stringBuilder.append("/");
            stringBuilder.append(this.mKey.mUid);
            if (this.mProcState != -1) {
                stringBuilder.append(" ");
                stringBuilder.append(DumpUtils.STATE_NAMES[this.mProcState]);
                stringBuilder.append(" #");
                stringBuilder.append(this.mProcStateSeq);
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void trackProcState(int n, int n2, long l) {
            n = ProcessState.PROCESS_STATE_TO_STATE[n];
            if (n2 != this.mProcStateSeq) {
                this.mProcStateSeq = n2;
                this.mProcState = n;
            } else if (n < this.mProcState) {
                this.mProcState = n;
            }
            if (n < 9 && !this.mInTrackingList) {
                this.mInTrackingList = true;
                this.mTrackingUptime = l;
                AssociationState.access$000((AssociationState)AssociationState.this).mTrackingAssociations.add(this);
            }
        }
    }

}

