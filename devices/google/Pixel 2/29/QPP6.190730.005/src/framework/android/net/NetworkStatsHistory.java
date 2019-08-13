/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.NetworkStats;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.IndentingPrintWriter;
import java.io.CharArrayWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ProtocolException;
import java.util.Arrays;
import java.util.Random;
import libcore.util.EmptyArray;

public class NetworkStatsHistory
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkStatsHistory> CREATOR = new Parcelable.Creator<NetworkStatsHistory>(){

        @Override
        public NetworkStatsHistory createFromParcel(Parcel parcel) {
            return new NetworkStatsHistory(parcel);
        }

        public NetworkStatsHistory[] newArray(int n) {
            return new NetworkStatsHistory[n];
        }
    };
    public static final int FIELD_ACTIVE_TIME = 1;
    public static final int FIELD_ALL = -1;
    public static final int FIELD_OPERATIONS = 32;
    public static final int FIELD_RX_BYTES = 2;
    public static final int FIELD_RX_PACKETS = 4;
    public static final int FIELD_TX_BYTES = 8;
    public static final int FIELD_TX_PACKETS = 16;
    private static final int VERSION_ADD_ACTIVE = 3;
    private static final int VERSION_ADD_PACKETS = 2;
    private static final int VERSION_INIT = 1;
    private long[] activeTime;
    private int bucketCount;
    private long bucketDuration;
    private long[] bucketStart;
    private long[] operations;
    private long[] rxBytes;
    private long[] rxPackets;
    private long totalBytes;
    private long[] txBytes;
    private long[] txPackets;

    @UnsupportedAppUsage
    public NetworkStatsHistory(long l) {
        this(l, 10, -1);
    }

    public NetworkStatsHistory(long l, int n) {
        this(l, n, -1);
    }

    public NetworkStatsHistory(long l, int n, int n2) {
        this.bucketDuration = l;
        this.bucketStart = new long[n];
        if ((n2 & 1) != 0) {
            this.activeTime = new long[n];
        }
        if ((n2 & 2) != 0) {
            this.rxBytes = new long[n];
        }
        if ((n2 & 4) != 0) {
            this.rxPackets = new long[n];
        }
        if ((n2 & 8) != 0) {
            this.txBytes = new long[n];
        }
        if ((n2 & 16) != 0) {
            this.txPackets = new long[n];
        }
        if ((n2 & 32) != 0) {
            this.operations = new long[n];
        }
        this.bucketCount = 0;
        this.totalBytes = 0L;
    }

    public NetworkStatsHistory(NetworkStatsHistory networkStatsHistory, long l) {
        this(l, networkStatsHistory.estimateResizeBuckets(l));
        this.recordEntireHistory(networkStatsHistory);
    }

    @UnsupportedAppUsage
    public NetworkStatsHistory(Parcel parcel) {
        this.bucketDuration = parcel.readLong();
        this.bucketStart = ParcelUtils.readLongArray(parcel);
        this.activeTime = ParcelUtils.readLongArray(parcel);
        this.rxBytes = ParcelUtils.readLongArray(parcel);
        this.rxPackets = ParcelUtils.readLongArray(parcel);
        this.txBytes = ParcelUtils.readLongArray(parcel);
        this.txPackets = ParcelUtils.readLongArray(parcel);
        this.operations = ParcelUtils.readLongArray(parcel);
        this.bucketCount = this.bucketStart.length;
        this.totalBytes = parcel.readLong();
    }

    public NetworkStatsHistory(DataInputStream arrl) throws IOException {
        int n = arrl.readInt();
        if (n != 1) {
            if (n != 2 && n != 3) {
                arrl = new StringBuilder();
                arrl.append("unexpected version: ");
                arrl.append(n);
                throw new ProtocolException(arrl.toString());
            }
            this.bucketDuration = arrl.readLong();
            this.bucketStart = DataStreamUtils.readVarLongArray((DataInputStream)arrl);
            long[] arrl2 = n >= 3 ? DataStreamUtils.readVarLongArray((DataInputStream)arrl) : new long[this.bucketStart.length];
            this.activeTime = arrl2;
            this.rxBytes = DataStreamUtils.readVarLongArray((DataInputStream)arrl);
            this.rxPackets = DataStreamUtils.readVarLongArray((DataInputStream)arrl);
            this.txBytes = DataStreamUtils.readVarLongArray((DataInputStream)arrl);
            this.txPackets = DataStreamUtils.readVarLongArray((DataInputStream)arrl);
            this.operations = DataStreamUtils.readVarLongArray((DataInputStream)arrl);
            this.bucketCount = this.bucketStart.length;
            this.totalBytes = ArrayUtils.total(this.rxBytes) + ArrayUtils.total(this.txBytes);
        } else {
            this.bucketDuration = arrl.readLong();
            this.bucketStart = DataStreamUtils.readFullLongArray((DataInputStream)arrl);
            this.rxBytes = DataStreamUtils.readFullLongArray((DataInputStream)arrl);
            this.rxPackets = new long[this.bucketStart.length];
            this.txBytes = DataStreamUtils.readFullLongArray((DataInputStream)arrl);
            arrl = this.bucketStart;
            this.txPackets = new long[arrl.length];
            this.operations = new long[arrl.length];
            this.bucketCount = arrl.length;
            this.totalBytes = ArrayUtils.total(this.rxBytes) + ArrayUtils.total(this.txBytes);
        }
        int n2 = this.bucketStart.length;
        n = this.bucketCount;
        if (n2 == n && this.rxBytes.length == n && this.rxPackets.length == n && this.txBytes.length == n && this.txPackets.length == n && this.operations.length == n) {
            return;
        }
        throw new ProtocolException("Mismatched history lengths");
    }

    private static void addLong(long[] arrl, int n, long l) {
        if (arrl != null) {
            arrl[n] = arrl[n] + l;
        }
    }

    private void ensureBuckets(long l, long l2) {
        long l3 = this.bucketDuration;
        l -= l % l3;
        while (l < l2 + (l3 - l2 % l3) % l3) {
            int n = Arrays.binarySearch(this.bucketStart, 0, this.bucketCount, l);
            if (n < 0) {
                this.insertBucket(n, l);
            }
            l += this.bucketDuration;
        }
    }

    private static long getLong(long[] arrl, int n, long l) {
        block0 : {
            if (arrl == null) break block0;
            l = arrl[n];
        }
        return l;
    }

    private void insertBucket(int n, long l) {
        int n2;
        int n3 = this.bucketCount;
        long[] arrl = this.bucketStart;
        if (n3 >= arrl.length) {
            n3 = Math.max(arrl.length, 10) * 3 / 2;
            this.bucketStart = Arrays.copyOf(this.bucketStart, n3);
            arrl = this.activeTime;
            if (arrl != null) {
                this.activeTime = Arrays.copyOf(arrl, n3);
            }
            if ((arrl = this.rxBytes) != null) {
                this.rxBytes = Arrays.copyOf(arrl, n3);
            }
            if ((arrl = this.rxPackets) != null) {
                this.rxPackets = Arrays.copyOf(arrl, n3);
            }
            if ((arrl = this.txBytes) != null) {
                this.txBytes = Arrays.copyOf(arrl, n3);
            }
            if ((arrl = this.txPackets) != null) {
                this.txPackets = Arrays.copyOf(arrl, n3);
            }
            if ((arrl = this.operations) != null) {
                this.operations = Arrays.copyOf(arrl, n3);
            }
        }
        if (n < (n2 = this.bucketCount)) {
            n3 = n + 1;
            arrl = this.bucketStart;
            System.arraycopy(arrl, n, arrl, n3, n2 -= n);
            arrl = this.activeTime;
            if (arrl != null) {
                System.arraycopy(arrl, n, arrl, n3, n2);
            }
            if ((arrl = this.rxBytes) != null) {
                System.arraycopy(arrl, n, arrl, n3, n2);
            }
            if ((arrl = this.rxPackets) != null) {
                System.arraycopy(arrl, n, arrl, n3, n2);
            }
            if ((arrl = this.txBytes) != null) {
                System.arraycopy(arrl, n, arrl, n3, n2);
            }
            if ((arrl = this.txPackets) != null) {
                System.arraycopy(arrl, n, arrl, n3, n2);
            }
            if ((arrl = this.operations) != null) {
                System.arraycopy(arrl, n, arrl, n3, n2);
            }
        }
        this.bucketStart[n] = l;
        NetworkStatsHistory.setLong(this.activeTime, n, 0L);
        NetworkStatsHistory.setLong(this.rxBytes, n, 0L);
        NetworkStatsHistory.setLong(this.rxPackets, n, 0L);
        NetworkStatsHistory.setLong(this.txBytes, n, 0L);
        NetworkStatsHistory.setLong(this.txPackets, n, 0L);
        NetworkStatsHistory.setLong(this.operations, n, 0L);
        ++this.bucketCount;
    }

    public static long randomLong(Random random, long l, long l2) {
        return (long)((float)l + random.nextFloat() * (float)(l2 - l));
    }

    private static void setLong(long[] arrl, int n, long l) {
        if (arrl != null) {
            arrl[n] = l;
        }
    }

    private static void writeToProto(ProtoOutputStream protoOutputStream, long l, long[] arrl, int n) {
        if (arrl != null) {
            protoOutputStream.write(l, arrl[n]);
        }
    }

    public void clear() {
        this.bucketStart = EmptyArray.LONG;
        if (this.activeTime != null) {
            this.activeTime = EmptyArray.LONG;
        }
        if (this.rxBytes != null) {
            this.rxBytes = EmptyArray.LONG;
        }
        if (this.rxPackets != null) {
            this.rxPackets = EmptyArray.LONG;
        }
        if (this.txBytes != null) {
            this.txBytes = EmptyArray.LONG;
        }
        if (this.txPackets != null) {
            this.txPackets = EmptyArray.LONG;
        }
        if (this.operations != null) {
            this.operations = EmptyArray.LONG;
        }
        this.bucketCount = 0;
        this.totalBytes = 0L;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter, boolean bl) {
        indentingPrintWriter.print("NetworkStatsHistory: bucketDuration=");
        indentingPrintWriter.println(this.bucketDuration / 1000L);
        indentingPrintWriter.increaseIndent();
        int n = 0;
        if (!bl) {
            n = Math.max(0, this.bucketCount - 32);
        }
        if (n > 0) {
            indentingPrintWriter.print("(omitting ");
            indentingPrintWriter.print(n);
            indentingPrintWriter.println(" buckets)");
        }
        while (n < this.bucketCount) {
            indentingPrintWriter.print("st=");
            indentingPrintWriter.print(this.bucketStart[n] / 1000L);
            if (this.rxBytes != null) {
                indentingPrintWriter.print(" rb=");
                indentingPrintWriter.print(this.rxBytes[n]);
            }
            if (this.rxPackets != null) {
                indentingPrintWriter.print(" rp=");
                indentingPrintWriter.print(this.rxPackets[n]);
            }
            if (this.txBytes != null) {
                indentingPrintWriter.print(" tb=");
                indentingPrintWriter.print(this.txBytes[n]);
            }
            if (this.txPackets != null) {
                indentingPrintWriter.print(" tp=");
                indentingPrintWriter.print(this.txPackets[n]);
            }
            if (this.operations != null) {
                indentingPrintWriter.print(" op=");
                indentingPrintWriter.print(this.operations[n]);
            }
            indentingPrintWriter.println();
            ++n;
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void dumpCheckin(PrintWriter printWriter) {
        printWriter.print("d,");
        printWriter.print(this.bucketDuration / 1000L);
        printWriter.println();
        for (int i = 0; i < this.bucketCount; ++i) {
            printWriter.print("b,");
            printWriter.print(this.bucketStart[i] / 1000L);
            printWriter.print(',');
            long[] arrl = this.rxBytes;
            if (arrl != null) {
                printWriter.print(arrl[i]);
            } else {
                printWriter.print("*");
            }
            printWriter.print(',');
            arrl = this.rxPackets;
            if (arrl != null) {
                printWriter.print(arrl[i]);
            } else {
                printWriter.print("*");
            }
            printWriter.print(',');
            arrl = this.txBytes;
            if (arrl != null) {
                printWriter.print(arrl[i]);
            } else {
                printWriter.print("*");
            }
            printWriter.print(',');
            arrl = this.txPackets;
            if (arrl != null) {
                printWriter.print(arrl[i]);
            } else {
                printWriter.print("*");
            }
            printWriter.print(',');
            arrl = this.operations;
            if (arrl != null) {
                printWriter.print(arrl[i]);
            } else {
                printWriter.print("*");
            }
            printWriter.println();
        }
    }

    public int estimateResizeBuckets(long l) {
        return (int)((long)this.size() * this.getBucketDuration() / l);
    }

    @Deprecated
    public void generateRandom(long l, long l2, long l3) {
        Random random = new Random();
        float f = random.nextFloat();
        long l4 = (long)((float)l3 * f);
        l3 = (long)((float)l3 * (1.0f - f));
        this.generateRandom(l, l2, l4, l4 / 1024L, l3, l3 / 1024L, l4 / 2048L, random);
    }

    @Deprecated
    public void generateRandom(long l, long l2, long l3, long l4, long l5, long l6, long l7, Random random) {
        this.ensureBuckets(l, l2);
        NetworkStats.Entry entry = new NetworkStats.Entry(NetworkStats.IFACE_ALL, -1, 0, 0, 0L, 0L, 0L, 0L, 0L);
        long l8 = l3;
        long l9 = l4;
        l4 = l6;
        l3 = l7;
        l6 = l9;
        do {
            l7 = l2;
            if (l8 <= 1024L && l6 <= 128L && l5 <= 1024L && l4 <= 128L && l3 <= 32L) {
                return;
            }
            l9 = NetworkStatsHistory.randomLong(random, l, l7);
            l7 = NetworkStatsHistory.randomLong(random, 0L, (l7 - l9) / 2L);
            entry.rxBytes = NetworkStatsHistory.randomLong(random, 0L, l8);
            entry.rxPackets = NetworkStatsHistory.randomLong(random, 0L, l6);
            entry.txBytes = NetworkStatsHistory.randomLong(random, 0L, l5);
            entry.txPackets = NetworkStatsHistory.randomLong(random, 0L, l4);
            entry.operations = NetworkStatsHistory.randomLong(random, 0L, l3);
            l8 -= entry.rxBytes;
            l6 -= entry.rxPackets;
            l5 -= entry.txBytes;
            l4 -= entry.txPackets;
            l3 -= entry.operations;
            this.recordData(l9, l9 + l7, entry);
        } while (true);
    }

    public long getBucketDuration() {
        return this.bucketDuration;
    }

    @UnsupportedAppUsage
    public long getEnd() {
        int n = this.bucketCount;
        if (n > 0) {
            return this.bucketStart[n - 1] + this.bucketDuration;
        }
        return Long.MIN_VALUE;
    }

    public int getIndexAfter(long l) {
        int n = Arrays.binarySearch(this.bucketStart, 0, this.bucketCount, l);
        if (n >= 0) {
            ++n;
        }
        return MathUtils.constrain(n, 0, this.bucketCount - 1);
    }

    @UnsupportedAppUsage
    public int getIndexBefore(long l) {
        int n = Arrays.binarySearch(this.bucketStart, 0, this.bucketCount, l);
        n = n < 0 ? --n : --n;
        return MathUtils.constrain(n, 0, this.bucketCount - 1);
    }

    @UnsupportedAppUsage
    public long getStart() {
        if (this.bucketCount > 0) {
            return this.bucketStart[0];
        }
        return Long.MAX_VALUE;
    }

    public long getTotalBytes() {
        return this.totalBytes;
    }

    @UnsupportedAppUsage
    public Entry getValues(int n, Entry entry) {
        if (entry == null) {
            entry = new Entry();
        }
        entry.bucketStart = this.bucketStart[n];
        entry.bucketDuration = this.bucketDuration;
        entry.activeTime = NetworkStatsHistory.getLong(this.activeTime, n, -1L);
        entry.rxBytes = NetworkStatsHistory.getLong(this.rxBytes, n, -1L);
        entry.rxPackets = NetworkStatsHistory.getLong(this.rxPackets, n, -1L);
        entry.txBytes = NetworkStatsHistory.getLong(this.txBytes, n, -1L);
        entry.txPackets = NetworkStatsHistory.getLong(this.txPackets, n, -1L);
        entry.operations = NetworkStatsHistory.getLong(this.operations, n, -1L);
        return entry;
    }

    @UnsupportedAppUsage
    public Entry getValues(long l, long l2, long l3, Entry entry) {
        long l4 = l;
        long l5 = l2;
        if (entry == null) {
            entry = new Entry();
        }
        entry.bucketDuration = l5 - l4;
        entry.bucketStart = l4;
        long[] arrl = this.activeTime;
        long l6 = -1L;
        l4 = arrl != null ? 0L : -1L;
        entry.activeTime = l4;
        l4 = this.rxBytes != null ? 0L : -1L;
        entry.rxBytes = l4;
        l4 = this.rxPackets != null ? 0L : -1L;
        entry.rxPackets = l4;
        l4 = this.txBytes != null ? 0L : -1L;
        entry.txBytes = l4;
        l4 = this.txPackets != null ? 0L : -1L;
        entry.txPackets = l4;
        l4 = l6;
        if (this.operations != null) {
            l4 = 0L;
        }
        entry.operations = l4;
        int n = this.getIndexAfter(l5);
        do {
            long l7;
            l5 = l2;
            l4 = l;
            if (n < 0 || (l7 = this.bucketDuration + (l6 = this.bucketStart[n])) <= l4) break;
            if (l6 < l5) {
                boolean bl = l6 < l3 && l7 > l3;
                if (bl) {
                    l4 = this.bucketDuration;
                } else {
                    if (l7 < l5) {
                        l5 = l7;
                    }
                    if (l6 > l4) {
                        l4 = l6;
                    }
                    l4 = l5 - l4;
                }
                if (l4 > 0L) {
                    if (this.activeTime != null) {
                        entry.activeTime += this.activeTime[n] * l4 / this.bucketDuration;
                    }
                    if (this.rxBytes != null) {
                        entry.rxBytes += this.rxBytes[n] * l4 / this.bucketDuration;
                    }
                    if (this.rxPackets != null) {
                        entry.rxPackets += this.rxPackets[n] * l4 / this.bucketDuration;
                    }
                    if (this.txBytes != null) {
                        entry.txBytes += this.txBytes[n] * l4 / this.bucketDuration;
                    }
                    if (this.txPackets != null) {
                        entry.txPackets += this.txPackets[n] * l4 / this.bucketDuration;
                    }
                    if (this.operations != null) {
                        entry.operations += this.operations[n] * l4 / this.bucketDuration;
                    }
                }
            }
            --n;
        } while (true);
        return entry;
    }

    @UnsupportedAppUsage
    public Entry getValues(long l, long l2, Entry entry) {
        return this.getValues(l, l2, Long.MAX_VALUE, entry);
    }

    public boolean intersects(long l, long l2) {
        long l3 = this.getStart();
        long l4 = this.getEnd();
        if (l >= l3 && l <= l4) {
            return true;
        }
        if (l2 >= l3 && l2 <= l4) {
            return true;
        }
        if (l3 >= l && l3 <= l2) {
            return true;
        }
        return l4 >= l && l4 <= l2;
    }

    @Deprecated
    public void recordData(long l, long l2, long l3, long l4) {
        this.recordData(l, l2, new NetworkStats.Entry(NetworkStats.IFACE_ALL, -1, 0, 0, l3, 0L, l4, 0L, 0L));
    }

    public void recordData(long l, long l2, NetworkStats.Entry entry) {
        long l3 = l2;
        long l4 = entry.rxBytes;
        long l5 = entry.rxPackets;
        long l6 = entry.txBytes;
        long l7 = entry.txPackets;
        long l8 = entry.operations;
        if (!entry.isNegative()) {
            if (entry.isEmpty()) {
                return;
            }
            this.ensureBuckets(l, l2);
            long l9 = l3 - l;
            int n = this.getIndexAfter(l3);
            do {
                long l10;
                long l11;
                long l12 = l2;
                l3 = l;
                if (n < 0 || (l10 = this.bucketDuration + (l11 = this.bucketStart[n])) < l3) break;
                if (l11 <= l12 && (l12 = Math.min(l10, l12) - Math.max(l11, l3)) > 0L) {
                    long l13 = l4 * l12 / l9;
                    long l14 = l5 * l12 / l9;
                    l10 = l6 * l12 / l9;
                    l11 = l7 * l12 / l9;
                    l3 = l8 * l12 / l9;
                    NetworkStatsHistory.addLong(this.activeTime, n, l12);
                    NetworkStatsHistory.addLong(this.rxBytes, n, l13);
                    l4 -= l13;
                    NetworkStatsHistory.addLong(this.rxPackets, n, l14);
                    l5 -= l14;
                    NetworkStatsHistory.addLong(this.txBytes, n, l10);
                    l6 -= l10;
                    NetworkStatsHistory.addLong(this.txPackets, n, l11);
                    l7 -= l11;
                    NetworkStatsHistory.addLong(this.operations, n, l3);
                    l9 -= l12;
                    l8 -= l3;
                }
                --n;
            } while (true);
            this.totalBytes += entry.rxBytes + entry.txBytes;
            return;
        }
        throw new IllegalArgumentException("tried recording negative data");
    }

    @UnsupportedAppUsage
    public void recordEntireHistory(NetworkStatsHistory networkStatsHistory) {
        this.recordHistory(networkStatsHistory, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public void recordHistory(NetworkStatsHistory networkStatsHistory, long l, long l2) {
        NetworkStats.Entry entry = new NetworkStats.Entry(NetworkStats.IFACE_ALL, -1, 0, 0, 0L, 0L, 0L, 0L, 0L);
        for (int i = 0; i < networkStatsHistory.bucketCount; ++i) {
            long l3 = networkStatsHistory.bucketStart[i];
            long l4 = networkStatsHistory.bucketDuration + l3;
            if (l3 < l || l4 > l2) continue;
            entry.rxBytes = NetworkStatsHistory.getLong(networkStatsHistory.rxBytes, i, 0L);
            entry.rxPackets = NetworkStatsHistory.getLong(networkStatsHistory.rxPackets, i, 0L);
            entry.txBytes = NetworkStatsHistory.getLong(networkStatsHistory.txBytes, i, 0L);
            entry.txPackets = NetworkStatsHistory.getLong(networkStatsHistory.txPackets, i, 0L);
            entry.operations = NetworkStatsHistory.getLong(networkStatsHistory.operations, i, 0L);
            this.recordData(l3, l4, entry);
        }
    }

    @Deprecated
    public void removeBucketsBefore(long l) {
        int n;
        long l2;
        for (n = 0; n < this.bucketCount && this.bucketDuration + (l2 = this.bucketStart[n]) <= l; ++n) {
        }
        if (n > 0) {
            long[] arrl = this.bucketStart;
            int n2 = arrl.length;
            this.bucketStart = Arrays.copyOfRange(arrl, n, n2);
            arrl = this.activeTime;
            if (arrl != null) {
                this.activeTime = Arrays.copyOfRange(arrl, n, n2);
            }
            if ((arrl = this.rxBytes) != null) {
                this.rxBytes = Arrays.copyOfRange(arrl, n, n2);
            }
            if ((arrl = this.rxPackets) != null) {
                this.rxPackets = Arrays.copyOfRange(arrl, n, n2);
            }
            if ((arrl = this.txBytes) != null) {
                this.txBytes = Arrays.copyOfRange(arrl, n, n2);
            }
            if ((arrl = this.txPackets) != null) {
                this.txPackets = Arrays.copyOfRange(arrl, n, n2);
            }
            if ((arrl = this.operations) != null) {
                this.operations = Arrays.copyOfRange(arrl, n, n2);
            }
            this.bucketCount -= n;
        }
    }

    public void setValues(int n, Entry arrl) {
        long[] arrl2 = this.rxBytes;
        if (arrl2 != null) {
            this.totalBytes -= arrl2[n];
        }
        if ((arrl2 = this.txBytes) != null) {
            this.totalBytes -= arrl2[n];
        }
        this.bucketStart[n] = arrl.bucketStart;
        NetworkStatsHistory.setLong(this.activeTime, n, arrl.activeTime);
        NetworkStatsHistory.setLong(this.rxBytes, n, arrl.rxBytes);
        NetworkStatsHistory.setLong(this.rxPackets, n, arrl.rxPackets);
        NetworkStatsHistory.setLong(this.txBytes, n, arrl.txBytes);
        NetworkStatsHistory.setLong(this.txPackets, n, arrl.txPackets);
        NetworkStatsHistory.setLong(this.operations, n, arrl.operations);
        arrl = this.rxBytes;
        if (arrl != null) {
            this.totalBytes += arrl[n];
        }
        if ((arrl = this.txBytes) != null) {
            this.totalBytes += arrl[n];
        }
    }

    @UnsupportedAppUsage
    public int size() {
        return this.bucketCount;
    }

    public String toString() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        this.dump(new IndentingPrintWriter(charArrayWriter, "  "), false);
        return charArrayWriter.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.bucketDuration);
        ParcelUtils.writeLongArray(parcel, this.bucketStart, this.bucketCount);
        ParcelUtils.writeLongArray(parcel, this.activeTime, this.bucketCount);
        ParcelUtils.writeLongArray(parcel, this.rxBytes, this.bucketCount);
        ParcelUtils.writeLongArray(parcel, this.rxPackets, this.bucketCount);
        ParcelUtils.writeLongArray(parcel, this.txBytes, this.bucketCount);
        ParcelUtils.writeLongArray(parcel, this.txPackets, this.bucketCount);
        ParcelUtils.writeLongArray(parcel, this.operations, this.bucketCount);
        parcel.writeLong(this.totalBytes);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1112396529665L, this.bucketDuration);
        for (int i = 0; i < this.bucketCount; ++i) {
            long l2 = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1112396529665L, this.bucketStart[i]);
            NetworkStatsHistory.writeToProto(protoOutputStream, 1112396529666L, this.rxBytes, i);
            NetworkStatsHistory.writeToProto(protoOutputStream, 1112396529667L, this.rxPackets, i);
            NetworkStatsHistory.writeToProto(protoOutputStream, 1112396529668L, this.txBytes, i);
            NetworkStatsHistory.writeToProto(protoOutputStream, 1112396529669L, this.txPackets, i);
            NetworkStatsHistory.writeToProto(protoOutputStream, 1112396529670L, this.operations, i);
            protoOutputStream.end(l2);
        }
        protoOutputStream.end(l);
    }

    public void writeToStream(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeInt(3);
        dataOutputStream.writeLong(this.bucketDuration);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.bucketStart, this.bucketCount);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.activeTime, this.bucketCount);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.rxBytes, this.bucketCount);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.rxPackets, this.bucketCount);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.txBytes, this.bucketCount);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.txPackets, this.bucketCount);
        DataStreamUtils.writeVarLongArray(dataOutputStream, this.operations, this.bucketCount);
    }

    public static class DataStreamUtils {
        @Deprecated
        public static long[] readFullLongArray(DataInputStream dataInputStream) throws IOException {
            int n = dataInputStream.readInt();
            if (n >= 0) {
                long[] arrl = new long[n];
                for (n = 0; n < arrl.length; ++n) {
                    arrl[n] = dataInputStream.readLong();
                }
                return arrl;
            }
            throw new ProtocolException("negative array size");
        }

        public static long readVarLong(DataInputStream dataInputStream) throws IOException {
            long l = 0L;
            for (int i = 0; i < 64; i += 7) {
                byte by = dataInputStream.readByte();
                l |= (long)(by & 127) << i;
                if ((by & 128) != 0) continue;
                return l;
            }
            throw new ProtocolException("malformed long");
        }

        public static long[] readVarLongArray(DataInputStream dataInputStream) throws IOException {
            int n = dataInputStream.readInt();
            if (n == -1) {
                return null;
            }
            if (n >= 0) {
                long[] arrl = new long[n];
                for (n = 0; n < arrl.length; ++n) {
                    arrl[n] = DataStreamUtils.readVarLong(dataInputStream);
                }
                return arrl;
            }
            throw new ProtocolException("negative array size");
        }

        public static void writeVarLong(DataOutputStream dataOutputStream, long l) throws IOException {
            do {
                if ((-128L & l) == 0L) {
                    dataOutputStream.writeByte((int)l);
                    return;
                }
                dataOutputStream.writeByte((int)l & 127 | 128);
                l >>>= 7;
            } while (true);
        }

        public static void writeVarLongArray(DataOutputStream dataOutputStream, long[] arrl, int n) throws IOException {
            if (arrl == null) {
                dataOutputStream.writeInt(-1);
                return;
            }
            if (n <= arrl.length) {
                dataOutputStream.writeInt(n);
                for (int i = 0; i < n; ++i) {
                    DataStreamUtils.writeVarLong(dataOutputStream, arrl[i]);
                }
                return;
            }
            throw new IllegalArgumentException("size larger than length");
        }
    }

    public static class Entry {
        public static final long UNKNOWN = -1L;
        public long activeTime;
        @UnsupportedAppUsage
        public long bucketDuration;
        @UnsupportedAppUsage
        public long bucketStart;
        public long operations;
        @UnsupportedAppUsage
        public long rxBytes;
        @UnsupportedAppUsage
        public long rxPackets;
        @UnsupportedAppUsage
        public long txBytes;
        @UnsupportedAppUsage
        public long txPackets;
    }

    public static class ParcelUtils {
        public static long[] readLongArray(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            long[] arrl = new long[n];
            for (n = 0; n < arrl.length; ++n) {
                arrl[n] = parcel.readLong();
            }
            return arrl;
        }

        public static void writeLongArray(Parcel parcel, long[] arrl, int n) {
            if (arrl == null) {
                parcel.writeInt(-1);
                return;
            }
            if (n <= arrl.length) {
                parcel.writeInt(n);
                for (int i = 0; i < n; ++i) {
                    parcel.writeLong(arrl[i]);
                }
                return;
            }
            throw new IllegalArgumentException("size larger than length");
        }
    }

}

