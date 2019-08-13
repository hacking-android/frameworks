/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseBooleanArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import libcore.util.EmptyArray;

public class NetworkStats
implements Parcelable {
    private static final String CLATD_INTERFACE_PREFIX = "v4-";
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkStats> CREATOR;
    public static final int DEFAULT_NETWORK_ALL = -1;
    public static final int DEFAULT_NETWORK_NO = 0;
    public static final int DEFAULT_NETWORK_YES = 1;
    public static final String IFACE_ALL;
    public static final String[] INTERFACES_ALL;
    private static final int IPV4V6_HEADER_DELTA = 20;
    public static final int METERED_ALL = -1;
    public static final int METERED_NO = 0;
    public static final int METERED_YES = 1;
    public static final int ROAMING_ALL = -1;
    public static final int ROAMING_NO = 0;
    public static final int ROAMING_YES = 1;
    public static final int SET_ALL = -1;
    public static final int SET_DBG_VPN_IN = 1001;
    public static final int SET_DBG_VPN_OUT = 1002;
    public static final int SET_DEBUG_START = 1000;
    public static final int SET_DEFAULT = 0;
    public static final int SET_FOREGROUND = 1;
    public static final int STATS_PER_IFACE = 0;
    public static final int STATS_PER_UID = 1;
    private static final String TAG = "NetworkStats";
    public static final int TAG_ALL = -1;
    public static final int TAG_NONE = 0;
    public static final int UID_ALL = -1;
    @UnsupportedAppUsage
    private int capacity;
    @UnsupportedAppUsage
    private int[] defaultNetwork;
    private long elapsedRealtime;
    @UnsupportedAppUsage
    private String[] iface;
    @UnsupportedAppUsage
    private int[] metered;
    @UnsupportedAppUsage
    private long[] operations;
    @UnsupportedAppUsage
    private int[] roaming;
    @UnsupportedAppUsage
    private long[] rxBytes;
    @UnsupportedAppUsage
    private long[] rxPackets;
    @UnsupportedAppUsage
    private int[] set;
    @UnsupportedAppUsage
    private int size;
    @UnsupportedAppUsage
    private int[] tag;
    @UnsupportedAppUsage
    private long[] txBytes;
    @UnsupportedAppUsage
    private long[] txPackets;
    @UnsupportedAppUsage
    private int[] uid;

    static {
        IFACE_ALL = null;
        INTERFACES_ALL = null;
        CREATOR = new Parcelable.Creator<NetworkStats>(){

            @Override
            public NetworkStats createFromParcel(Parcel parcel) {
                return new NetworkStats(parcel);
            }

            public NetworkStats[] newArray(int n) {
                return new NetworkStats[n];
            }
        };
    }

    @UnsupportedAppUsage
    public NetworkStats(long l, int n) {
        this.elapsedRealtime = l;
        this.size = 0;
        if (n > 0) {
            this.capacity = n;
            this.iface = new String[n];
            this.uid = new int[n];
            this.set = new int[n];
            this.tag = new int[n];
            this.metered = new int[n];
            this.roaming = new int[n];
            this.defaultNetwork = new int[n];
            this.rxBytes = new long[n];
            this.rxPackets = new long[n];
            this.txBytes = new long[n];
            this.txPackets = new long[n];
            this.operations = new long[n];
        } else {
            this.clear();
        }
    }

    @UnsupportedAppUsage
    public NetworkStats(Parcel parcel) {
        this.elapsedRealtime = parcel.readLong();
        this.size = parcel.readInt();
        this.capacity = parcel.readInt();
        this.iface = parcel.createStringArray();
        this.uid = parcel.createIntArray();
        this.set = parcel.createIntArray();
        this.tag = parcel.createIntArray();
        this.metered = parcel.createIntArray();
        this.roaming = parcel.createIntArray();
        this.defaultNetwork = parcel.createIntArray();
        this.rxBytes = parcel.createLongArray();
        this.rxPackets = parcel.createLongArray();
        this.txBytes = parcel.createLongArray();
        this.txPackets = parcel.createLongArray();
        this.operations = parcel.createLongArray();
    }

    private Entry addTrafficToApplications(int n, String string2, String string3, Entry entry, Entry entry2) {
        Entry entry3 = new Entry();
        Entry entry4 = new Entry();
        entry4.iface = string3;
        for (int i = 0; i < this.size; ++i) {
            if (!Objects.equals(this.iface[i], string2) || this.uid[i] == n) continue;
            entry4.rxBytes = entry.rxBytes > 0L ? entry2.rxBytes * this.rxBytes[i] / entry.rxBytes : 0L;
            entry4.rxPackets = entry.rxPackets > 0L ? entry2.rxPackets * this.rxPackets[i] / entry.rxPackets : 0L;
            entry4.txBytes = entry.txBytes > 0L ? entry2.txBytes * this.txBytes[i] / entry.txBytes : 0L;
            entry4.txPackets = entry.txPackets > 0L ? entry2.txPackets * this.txPackets[i] / entry.txPackets : 0L;
            entry4.operations = entry.operations > 0L ? entry2.operations * this.operations[i] / entry.operations : 0L;
            entry4.uid = this.uid[i];
            entry4.tag = this.tag[i];
            entry4.set = this.set[i];
            entry4.metered = this.metered[i];
            entry4.roaming = this.roaming[i];
            entry4.defaultNetwork = this.defaultNetwork[i];
            this.combineValues(entry4);
            if (this.tag[i] != 0) continue;
            entry3.add(entry4);
            entry4.set = 1001;
            this.combineValues(entry4);
        }
        return entry3;
    }

    public static void apply464xlatAdjustments(NetworkStats networkStats, NetworkStats networkStats2, Map<String, String> map, boolean bl) {
        NetworkStats networkStats3 = new NetworkStats(0L, map.size());
        Entry entry = null;
        Entry entry2 = new Entry(IFACE_ALL, 0, 0, 0, 0, 0, 0, 0L, 0L, 0L, 0L, 0L);
        for (int i = 0; i < networkStats2.size; ++i) {
            String string2;
            entry = networkStats2.getValues(i, entry);
            if (entry.iface == null || !entry.iface.startsWith(CLATD_INTERFACE_PREFIX) || (string2 = map.get(entry.iface)) == null) continue;
            entry2.iface = string2;
            if (!bl) {
                entry2.rxBytes = -(entry.rxBytes + entry.rxPackets * 20L);
                entry2.rxPackets = -entry.rxPackets;
            }
            networkStats3.combineValues(entry2);
            entry.rxBytes += entry.rxPackets * 20L;
            entry.txBytes += entry.txPackets * 20L;
            networkStats2.setValues(i, entry);
        }
        networkStats.removeUids(new int[]{1029});
        networkStats.combineAllValues(networkStats3);
    }

    private void deductTrafficFromVpnApp(int n, String string2, Entry entry) {
        entry.uid = n;
        entry.set = 1002;
        entry.tag = 0;
        entry.iface = string2;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        this.combineValues(entry);
        int n2 = this.findIndex(string2, n, 0, 0, 0, 0, 0);
        if (n2 != -1) {
            NetworkStats.tunSubtract(n2, this, entry);
        }
        if ((n = this.findIndex(string2, n, 1, 0, 0, 0, 0)) != -1) {
            NetworkStats.tunSubtract(n, this, entry);
        }
    }

    public static String defaultNetworkToString(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    return "UNKNOWN";
                }
                return "YES";
            }
            return "NO";
        }
        return "ALL";
    }

    private Entry getTotal(Entry entry, HashSet<String> hashSet, int n, boolean bl) {
        if (entry == null) {
            entry = new Entry();
        }
        entry.iface = IFACE_ALL;
        entry.uid = n;
        entry.set = -1;
        entry.tag = 0;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        entry.rxBytes = 0L;
        entry.rxPackets = 0L;
        entry.txBytes = 0L;
        entry.txPackets = 0L;
        entry.operations = 0L;
        for (int i = 0; i < this.size; ++i) {
            boolean bl2 = true;
            boolean bl3 = n == -1 || n == this.uid[i];
            boolean bl4 = bl2;
            if (hashSet != null) {
                bl4 = hashSet.contains(this.iface[i]) ? bl2 : false;
            }
            if (!bl3 || !bl4 || this.tag[i] != 0 && !bl) continue;
            entry.rxBytes += this.rxBytes[i];
            entry.rxPackets += this.rxPackets[i];
            entry.txBytes += this.txBytes[i];
            entry.txPackets += this.txPackets[i];
            entry.operations += this.operations[i];
        }
        return entry;
    }

    private void maybeCopyEntry(int n, int n2) {
        if (n == n2) {
            return;
        }
        Object[] arrobject = this.iface;
        arrobject[n] = arrobject[n2];
        arrobject = this.uid;
        arrobject[n] = arrobject[n2];
        arrobject = this.set;
        arrobject[n] = arrobject[n2];
        arrobject = this.tag;
        arrobject[n] = arrobject[n2];
        arrobject = this.metered;
        arrobject[n] = arrobject[n2];
        arrobject = this.roaming;
        arrobject[n] = arrobject[n2];
        arrobject = this.defaultNetwork;
        arrobject[n] = arrobject[n2];
        arrobject = this.rxBytes;
        arrobject[n] = arrobject[n2];
        arrobject = this.rxPackets;
        arrobject[n] = arrobject[n2];
        arrobject = this.txBytes;
        arrobject[n] = arrobject[n2];
        arrobject = this.txPackets;
        arrobject[n] = arrobject[n2];
        arrobject = this.operations;
        arrobject[n] = arrobject[n2];
    }

    public static String meteredToString(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    return "UNKNOWN";
                }
                return "YES";
            }
            return "NO";
        }
        return "ALL";
    }

    public static String roamingToString(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    return "UNKNOWN";
                }
                return "YES";
            }
            return "NO";
        }
        return "ALL";
    }

    public static boolean setMatches(int n, int n2) {
        boolean bl = true;
        if (n == n2) {
            return true;
        }
        if (n != -1 || n2 >= 1000) {
            bl = false;
        }
        return bl;
    }

    public static String setToCheckinString(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 1001) {
                        if (n != 1002) {
                            return "unk";
                        }
                        return "vpnout";
                    }
                    return "vpnin";
                }
                return "fg";
            }
            return "def";
        }
        return "all";
    }

    public static String setToString(int n) {
        if (n != -1) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 1001) {
                        if (n != 1002) {
                            return "UNKNOWN";
                        }
                        return "DBG_VPN_OUT";
                    }
                    return "DBG_VPN_IN";
                }
                return "FOREGROUND";
            }
            return "DEFAULT";
        }
        return "ALL";
    }

    private void setValues(int n, Entry entry) {
        this.iface[n] = entry.iface;
        this.uid[n] = entry.uid;
        this.set[n] = entry.set;
        this.tag[n] = entry.tag;
        this.metered[n] = entry.metered;
        this.roaming[n] = entry.roaming;
        this.defaultNetwork[n] = entry.defaultNetwork;
        this.rxBytes[n] = entry.rxBytes;
        this.rxPackets[n] = entry.rxPackets;
        this.txBytes[n] = entry.txBytes;
        this.txPackets[n] = entry.txPackets;
        this.operations[n] = entry.operations;
    }

    public static <C> NetworkStats subtract(NetworkStats networkStats, NetworkStats networkStats2, NonMonotonicObserver<C> nonMonotonicObserver, C c) {
        return NetworkStats.subtract(networkStats, networkStats2, nonMonotonicObserver, c, null);
    }

    public static <C> NetworkStats subtract(NetworkStats networkStats, NetworkStats networkStats2, NonMonotonicObserver<C> nonMonotonicObserver, C c, NetworkStats object) {
        Object object2;
        long l = networkStats.elapsedRealtime - networkStats2.elapsedRealtime;
        if (l < 0L) {
            if (nonMonotonicObserver != null) {
                nonMonotonicObserver.foundNonMonotonic(networkStats, -1, networkStats2, -1, c);
            }
            l = 0L;
        }
        Object object3 = new Entry();
        if (object != null && ((NetworkStats)object).capacity >= networkStats.size) {
            ((NetworkStats)object).size = 0;
            ((NetworkStats)object).elapsedRealtime = l;
            object2 = object;
        } else {
            object2 = new NetworkStats(l, networkStats.size);
        }
        int n = 0;
        object = object3;
        do {
            object3 = networkStats2;
            if (n >= networkStats.size) break;
            ((Entry)object).iface = networkStats.iface[n];
            ((Entry)object).uid = networkStats.uid[n];
            ((Entry)object).set = networkStats.set[n];
            ((Entry)object).tag = networkStats.tag[n];
            ((Entry)object).metered = networkStats.metered[n];
            ((Entry)object).roaming = networkStats.roaming[n];
            ((Entry)object).defaultNetwork = networkStats.defaultNetwork[n];
            ((Entry)object).rxBytes = networkStats.rxBytes[n];
            ((Entry)object).rxPackets = networkStats.rxPackets[n];
            ((Entry)object).txBytes = networkStats.txBytes[n];
            ((Entry)object).txPackets = networkStats.txPackets[n];
            ((Entry)object).operations = networkStats.operations[n];
            int n2 = networkStats2.findIndexHinted(((Entry)object).iface, ((Entry)object).uid, ((Entry)object).set, ((Entry)object).tag, ((Entry)object).metered, ((Entry)object).roaming, ((Entry)object).defaultNetwork, n);
            if (n2 != -1) {
                ((Entry)object).rxBytes -= ((NetworkStats)object3).rxBytes[n2];
                ((Entry)object).rxPackets -= ((NetworkStats)object3).rxPackets[n2];
                ((Entry)object).txBytes -= ((NetworkStats)object3).txBytes[n2];
                ((Entry)object).txPackets -= ((NetworkStats)object3).txPackets[n2];
                ((Entry)object).operations -= ((NetworkStats)object3).operations[n2];
            }
            if (((Entry)object).isNegative()) {
                if (nonMonotonicObserver != null) {
                    nonMonotonicObserver.foundNonMonotonic(networkStats, n, networkStats2, n2, c);
                }
                object3 = object;
                ((Entry)object3).rxBytes = Math.max(((Entry)object3).rxBytes, 0L);
                ((Entry)object3).rxPackets = Math.max(((Entry)object3).rxPackets, 0L);
                ((Entry)object3).txBytes = Math.max(((Entry)object3).txBytes, 0L);
                ((Entry)object3).txPackets = Math.max(((Entry)object3).txPackets, 0L);
                ((Entry)object3).operations = Math.max(((Entry)object3).operations, 0L);
            }
            ((NetworkStats)object2).addValues((Entry)object);
            ++n;
        } while (true);
        return object2;
    }

    public static String tagToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }

    private void tunAdjustmentInit(int n, String string2, String string3, Entry entry, Entry entry2) {
        Entry entry3 = new Entry();
        for (int i = 0; i < this.size; ++i) {
            this.getValues(i, entry3);
            if (entry3.uid != -1) {
                if (entry3.set != 1001 && entry3.set != 1002) {
                    if (entry3.uid == n && entry3.tag == 0 && Objects.equals(string3, entry3.iface)) {
                        entry2.add(entry3);
                    }
                    if (entry3.uid == n || entry3.tag != 0 || !Objects.equals(string2, entry3.iface)) continue;
                    entry.add(entry3);
                    continue;
                }
                throw new IllegalStateException("Cannot adjust VPN accounting on a NetworkStats containing SET_DBG_VPN_*");
            }
            throw new IllegalStateException("Cannot adjust VPN accounting on an iface aggregated NetworkStats.");
        }
    }

    private static Entry tunGetPool(Entry entry, Entry entry2) {
        Entry entry3 = new Entry();
        entry3.rxBytes = Math.min(entry.rxBytes, entry2.rxBytes);
        entry3.rxPackets = Math.min(entry.rxPackets, entry2.rxPackets);
        entry3.txBytes = Math.min(entry.txBytes, entry2.txBytes);
        entry3.txPackets = Math.min(entry.txPackets, entry2.txPackets);
        entry3.operations = Math.min(entry.operations, entry2.operations);
        return entry3;
    }

    private static void tunSubtract(int n, NetworkStats arrl, Entry entry) {
        long l = Math.min(arrl.rxBytes[n], entry.rxBytes);
        long[] arrl2 = arrl.rxBytes;
        arrl2[n] = arrl2[n] - l;
        entry.rxBytes -= l;
        l = Math.min(arrl.rxPackets[n], entry.rxPackets);
        arrl2 = arrl.rxPackets;
        arrl2[n] = arrl2[n] - l;
        entry.rxPackets -= l;
        l = Math.min(arrl.txBytes[n], entry.txBytes);
        arrl2 = arrl.txBytes;
        arrl2[n] = arrl2[n] - l;
        entry.txBytes -= l;
        l = Math.min(arrl.txPackets[n], entry.txPackets);
        arrl = arrl.txPackets;
        arrl[n] = arrl[n] - l;
        entry.txPackets -= l;
    }

    @VisibleForTesting
    public NetworkStats addIfaceValues(String string2, long l, long l2, long l3, long l4) {
        return this.addValues(string2, -1, 0, 0, l, l2, l3, l4, 0L);
    }

    public NetworkStats addValues(Entry entry) {
        int n = this.size;
        if (n >= this.capacity) {
            n = Math.max(n, 10) * 3 / 2;
            this.iface = Arrays.copyOf(this.iface, n);
            this.uid = Arrays.copyOf(this.uid, n);
            this.set = Arrays.copyOf(this.set, n);
            this.tag = Arrays.copyOf(this.tag, n);
            this.metered = Arrays.copyOf(this.metered, n);
            this.roaming = Arrays.copyOf(this.roaming, n);
            this.defaultNetwork = Arrays.copyOf(this.defaultNetwork, n);
            this.rxBytes = Arrays.copyOf(this.rxBytes, n);
            this.rxPackets = Arrays.copyOf(this.rxPackets, n);
            this.txBytes = Arrays.copyOf(this.txBytes, n);
            this.txPackets = Arrays.copyOf(this.txPackets, n);
            this.operations = Arrays.copyOf(this.operations, n);
            this.capacity = n;
        }
        this.setValues(this.size, entry);
        ++this.size;
        return this;
    }

    @VisibleForTesting
    public NetworkStats addValues(String string2, int n, int n2, int n3, int n4, int n5, int n6, long l, long l2, long l3, long l4, long l5) {
        return this.addValues(new Entry(string2, n, n2, n3, n4, n5, n6, l, l2, l3, l4, l5));
    }

    @VisibleForTesting
    public NetworkStats addValues(String string2, int n, int n2, int n3, long l, long l2, long l3, long l4, long l5) {
        return this.addValues(new Entry(string2, n, n2, n3, l, l2, l3, l4, l5));
    }

    public void apply464xlatAdjustments(Map<String, String> map, boolean bl) {
        NetworkStats.apply464xlatAdjustments(this, this, map, bl);
    }

    public void clear() {
        this.capacity = 0;
        this.iface = EmptyArray.STRING;
        this.uid = EmptyArray.INT;
        this.set = EmptyArray.INT;
        this.tag = EmptyArray.INT;
        this.metered = EmptyArray.INT;
        this.roaming = EmptyArray.INT;
        this.defaultNetwork = EmptyArray.INT;
        this.rxBytes = EmptyArray.LONG;
        this.rxPackets = EmptyArray.LONG;
        this.txBytes = EmptyArray.LONG;
        this.txPackets = EmptyArray.LONG;
        this.operations = EmptyArray.LONG;
    }

    public NetworkStats clone() {
        NetworkStats networkStats = new NetworkStats(this.elapsedRealtime, this.size);
        Entry entry = null;
        for (int i = 0; i < this.size; ++i) {
            entry = this.getValues(i, entry);
            networkStats.addValues(entry);
        }
        return networkStats;
    }

    @UnsupportedAppUsage
    public void combineAllValues(NetworkStats networkStats) {
        Entry entry = null;
        for (int i = 0; i < networkStats.size; ++i) {
            entry = networkStats.getValues(i, entry);
            this.combineValues(entry);
        }
    }

    @UnsupportedAppUsage
    public NetworkStats combineValues(Entry entry) {
        int n = this.findIndex(entry.iface, entry.uid, entry.set, entry.tag, entry.metered, entry.roaming, entry.defaultNetwork);
        if (n == -1) {
            this.addValues(entry);
        } else {
            long[] arrl = this.rxBytes;
            arrl[n] = arrl[n] + entry.rxBytes;
            arrl = this.rxPackets;
            arrl[n] = arrl[n] + entry.rxPackets;
            arrl = this.txBytes;
            arrl[n] = arrl[n] + entry.txBytes;
            arrl = this.txPackets;
            arrl[n] = arrl[n] + entry.txPackets;
            arrl = this.operations;
            arrl[n] = arrl[n] + entry.operations;
        }
        return this;
    }

    public NetworkStats combineValues(String string2, int n, int n2, int n3, long l, long l2, long l3, long l4, long l5) {
        return this.combineValues(new Entry(string2, n, n2, n3, l, l2, l3, l4, l5));
    }

    @Deprecated
    public NetworkStats combineValues(String string2, int n, int n2, long l, long l2, long l3, long l4, long l5) {
        return this.combineValues(string2, n, 0, n2, l, l2, l3, l4, l5);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.print("NetworkStats: elapsedRealtime=");
        printWriter.println(this.elapsedRealtime);
        for (int i = 0; i < this.size; ++i) {
            printWriter.print(string2);
            printWriter.print("  [");
            printWriter.print(i);
            printWriter.print("]");
            printWriter.print(" iface=");
            printWriter.print(this.iface[i]);
            printWriter.print(" uid=");
            printWriter.print(this.uid[i]);
            printWriter.print(" set=");
            printWriter.print(NetworkStats.setToString(this.set[i]));
            printWriter.print(" tag=");
            printWriter.print(NetworkStats.tagToString(this.tag[i]));
            printWriter.print(" metered=");
            printWriter.print(NetworkStats.meteredToString(this.metered[i]));
            printWriter.print(" roaming=");
            printWriter.print(NetworkStats.roamingToString(this.roaming[i]));
            printWriter.print(" defaultNetwork=");
            printWriter.print(NetworkStats.defaultNetworkToString(this.defaultNetwork[i]));
            printWriter.print(" rxBytes=");
            printWriter.print(this.rxBytes[i]);
            printWriter.print(" rxPackets=");
            printWriter.print(this.rxPackets[i]);
            printWriter.print(" txBytes=");
            printWriter.print(this.txBytes[i]);
            printWriter.print(" txPackets=");
            printWriter.print(this.txPackets[i]);
            printWriter.print(" operations=");
            printWriter.println(this.operations[i]);
        }
    }

    public void filter(int n, String[] arrstring, int n2) {
        if (n == -1 && n2 == -1 && arrstring == INTERFACES_ALL) {
            return;
        }
        Entry entry = new Entry();
        int n3 = 0;
        for (int i = 0; i < this.size; ++i) {
            entry = this.getValues(i, entry);
            boolean bl = !(n != -1 && n != entry.uid || n2 != -1 && n2 != entry.tag || arrstring != INTERFACES_ALL && !ArrayUtils.contains(arrstring, entry.iface));
            int n4 = n3;
            if (bl) {
                this.setValues(n3, entry);
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        this.size = n3;
    }

    public int findIndex(String string2, int n, int n2, int n3, int n4, int n5, int n6) {
        for (int i = 0; i < this.size; ++i) {
            if (n != this.uid[i] || n2 != this.set[i] || n3 != this.tag[i] || n4 != this.metered[i] || n5 != this.roaming[i] || n6 != this.defaultNetwork[i] || !Objects.equals(string2, this.iface[i])) continue;
            return i;
        }
        return -1;
    }

    @VisibleForTesting
    public int findIndexHinted(String string2, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        for (int i = 0; i < (n8 = this.size); ++i) {
            int n9 = i / 2;
            n8 = i % 2 == 0 ? (n7 + n9) % n8 : (n8 + n7 - n9 - 1) % n8;
            if (n != this.uid[n8] || n2 != this.set[n8] || n3 != this.tag[n8] || n4 != this.metered[n8] || n5 != this.roaming[n8] || n6 != this.defaultNetwork[n8] || !Objects.equals(string2, this.iface[n8])) continue;
            return n8;
        }
        return -1;
    }

    public long getElapsedRealtime() {
        return this.elapsedRealtime;
    }

    public long getElapsedRealtimeAge() {
        return SystemClock.elapsedRealtime() - this.elapsedRealtime;
    }

    @UnsupportedAppUsage
    public Entry getTotal(Entry entry) {
        return this.getTotal(entry, null, -1, false);
    }

    @UnsupportedAppUsage
    public Entry getTotal(Entry entry, int n) {
        return this.getTotal(entry, null, n, false);
    }

    public Entry getTotal(Entry entry, HashSet<String> hashSet) {
        return this.getTotal(entry, hashSet, -1, false);
    }

    @UnsupportedAppUsage
    public long getTotalBytes() {
        Entry entry = this.getTotal(null);
        return entry.rxBytes + entry.txBytes;
    }

    @UnsupportedAppUsage
    public Entry getTotalIncludingTags(Entry entry) {
        return this.getTotal(entry, null, -1, true);
    }

    public long getTotalPackets() {
        long l = 0L;
        for (int i = this.size - 1; i >= 0; --i) {
            l += this.rxPackets[i] + this.txPackets[i];
        }
        return l;
    }

    public String[] getUniqueIfaces() {
        HashSet<String> hashSet = new HashSet<String>();
        for (String string2 : this.iface) {
            if (string2 == IFACE_ALL) continue;
            hashSet.add(string2);
        }
        return hashSet.toArray(new String[hashSet.size()]);
    }

    @UnsupportedAppUsage
    public int[] getUniqueUids() {
        int n;
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        int[] arrn = this.uid;
        int n2 = arrn.length;
        for (n = 0; n < n2; ++n) {
            sparseBooleanArray.put(arrn[n], true);
        }
        n2 = sparseBooleanArray.size();
        arrn = new int[n2];
        for (n = 0; n < n2; ++n) {
            arrn[n] = sparseBooleanArray.keyAt(n);
        }
        return arrn;
    }

    @UnsupportedAppUsage
    public Entry getValues(int n, Entry entry) {
        if (entry == null) {
            entry = new Entry();
        }
        entry.iface = this.iface[n];
        entry.uid = this.uid[n];
        entry.set = this.set[n];
        entry.tag = this.tag[n];
        entry.metered = this.metered[n];
        entry.roaming = this.roaming[n];
        entry.defaultNetwork = this.defaultNetwork[n];
        entry.rxBytes = this.rxBytes[n];
        entry.rxPackets = this.rxPackets[n];
        entry.txBytes = this.txBytes[n];
        entry.txPackets = this.txPackets[n];
        entry.operations = this.operations[n];
        return entry;
    }

    public NetworkStats groupedByIface() {
        NetworkStats networkStats = new NetworkStats(this.elapsedRealtime, 10);
        Entry entry = new Entry();
        entry.uid = -1;
        entry.set = -1;
        entry.tag = 0;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        entry.operations = 0L;
        for (int i = 0; i < this.size; ++i) {
            if (this.tag[i] != 0) continue;
            entry.iface = this.iface[i];
            entry.rxBytes = this.rxBytes[i];
            entry.rxPackets = this.rxPackets[i];
            entry.txBytes = this.txBytes[i];
            entry.txPackets = this.txPackets[i];
            networkStats.combineValues(entry);
        }
        return networkStats;
    }

    public NetworkStats groupedByUid() {
        NetworkStats networkStats = new NetworkStats(this.elapsedRealtime, 10);
        Entry entry = new Entry();
        entry.iface = IFACE_ALL;
        entry.set = -1;
        entry.tag = 0;
        entry.metered = -1;
        entry.roaming = -1;
        entry.defaultNetwork = -1;
        for (int i = 0; i < this.size; ++i) {
            if (this.tag[i] != 0) continue;
            entry.uid = this.uid[i];
            entry.rxBytes = this.rxBytes[i];
            entry.rxPackets = this.rxPackets[i];
            entry.txBytes = this.txBytes[i];
            entry.txPackets = this.txPackets[i];
            entry.operations = this.operations[i];
            networkStats.combineValues(entry);
        }
        return networkStats;
    }

    @VisibleForTesting
    public int internalSize() {
        return this.capacity;
    }

    public boolean migrateTun(int n, String object, String charSequence) {
        Entry entry = new Entry();
        Entry entry2 = new Entry();
        this.tunAdjustmentInit(n, (String)object, (String)charSequence, entry, entry2);
        entry2 = NetworkStats.tunGetPool(entry, entry2);
        if (entry2.isEmpty()) {
            return true;
        }
        object = this.addTrafficToApplications(n, (String)object, (String)charSequence, entry, entry2);
        this.deductTrafficFromVpnApp(n, (String)charSequence, (Entry)object);
        if (!((Entry)object).isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed to deduct underlying network traffic from VPN package. Moved=");
            ((StringBuilder)charSequence).append(object);
            Slog.wtf(TAG, ((StringBuilder)charSequence).toString());
            return false;
        }
        return true;
    }

    public void removeUids(int[] arrn) {
        int n = 0;
        for (int i = 0; i < this.size; ++i) {
            int n2 = n;
            if (!ArrayUtils.contains(arrn, this.uid[i])) {
                this.maybeCopyEntry(n, i);
                n2 = n + 1;
            }
            n = n2;
        }
        this.size = n;
    }

    public void setElapsedRealtime(long l) {
        this.elapsedRealtime = l;
    }

    @UnsupportedAppUsage
    public int size() {
        return this.size;
    }

    public void spliceOperationsFrom(NetworkStats networkStats) {
        for (int i = 0; i < this.size; ++i) {
            int n = networkStats.findIndex(this.iface[i], this.uid[i], this.set[i], this.tag[i], this.metered[i], this.roaming[i], this.defaultNetwork[i]);
            this.operations[i] = n == -1 ? 0L : networkStats.operations[n];
        }
    }

    public NetworkStats subtract(NetworkStats networkStats) {
        return NetworkStats.subtract(this, networkStats, null, null);
    }

    public String toString() {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        this.dump("", new PrintWriter(charArrayWriter));
        return charArrayWriter.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.elapsedRealtime);
        parcel.writeInt(this.size);
        parcel.writeInt(this.capacity);
        parcel.writeStringArray(this.iface);
        parcel.writeIntArray(this.uid);
        parcel.writeIntArray(this.set);
        parcel.writeIntArray(this.tag);
        parcel.writeIntArray(this.metered);
        parcel.writeIntArray(this.roaming);
        parcel.writeIntArray(this.defaultNetwork);
        parcel.writeLongArray(this.rxBytes);
        parcel.writeLongArray(this.rxPackets);
        parcel.writeLongArray(this.txBytes);
        parcel.writeLongArray(this.txPackets);
        parcel.writeLongArray(this.operations);
    }

    public static class Entry {
        public int defaultNetwork;
        @UnsupportedAppUsage
        public String iface;
        public int metered;
        public long operations;
        public int roaming;
        @UnsupportedAppUsage
        public long rxBytes;
        @UnsupportedAppUsage
        public long rxPackets;
        @UnsupportedAppUsage
        public int set;
        @UnsupportedAppUsage
        public int tag;
        @UnsupportedAppUsage
        public long txBytes;
        @UnsupportedAppUsage
        public long txPackets;
        @UnsupportedAppUsage
        public int uid;

        @UnsupportedAppUsage
        public Entry() {
            this(IFACE_ALL, -1, 0, 0, 0L, 0L, 0L, 0L, 0L);
        }

        public Entry(long l, long l2, long l3, long l4, long l5) {
            this(IFACE_ALL, -1, 0, 0, l, l2, l3, l4, l5);
        }

        public Entry(String string2, int n, int n2, int n3, int n4, int n5, int n6, long l, long l2, long l3, long l4, long l5) {
            this.iface = string2;
            this.uid = n;
            this.set = n2;
            this.tag = n3;
            this.metered = n4;
            this.roaming = n5;
            this.defaultNetwork = n6;
            this.rxBytes = l;
            this.rxPackets = l2;
            this.txBytes = l3;
            this.txPackets = l4;
            this.operations = l5;
        }

        public Entry(String string2, int n, int n2, int n3, long l, long l2, long l3, long l4, long l5) {
            this(string2, n, n2, n3, 0, 0, 0, l, l2, l3, l4, l5);
        }

        public void add(Entry entry) {
            this.rxBytes += entry.rxBytes;
            this.rxPackets += entry.rxPackets;
            this.txBytes += entry.txBytes;
            this.txPackets += entry.txPackets;
            this.operations += entry.operations;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Entry;
            boolean bl2 = false;
            if (bl) {
                object = (Entry)object;
                if (this.uid == ((Entry)object).uid && this.set == ((Entry)object).set && this.tag == ((Entry)object).tag && this.metered == ((Entry)object).metered && this.roaming == ((Entry)object).roaming && this.defaultNetwork == ((Entry)object).defaultNetwork && this.rxBytes == ((Entry)object).rxBytes && this.rxPackets == ((Entry)object).rxPackets && this.txBytes == ((Entry)object).txBytes && this.txPackets == ((Entry)object).txPackets && this.operations == ((Entry)object).operations && this.iface.equals(((Entry)object).iface)) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.uid, this.set, this.tag, this.metered, this.roaming, this.defaultNetwork, this.iface);
        }

        public boolean isEmpty() {
            boolean bl = this.rxBytes == 0L && this.rxPackets == 0L && this.txBytes == 0L && this.txPackets == 0L && this.operations == 0L;
            return bl;
        }

        public boolean isNegative() {
            boolean bl = this.rxBytes < 0L || this.rxPackets < 0L || this.txBytes < 0L || this.txPackets < 0L || this.operations < 0L;
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("iface=");
            stringBuilder.append(this.iface);
            stringBuilder.append(" uid=");
            stringBuilder.append(this.uid);
            stringBuilder.append(" set=");
            stringBuilder.append(NetworkStats.setToString(this.set));
            stringBuilder.append(" tag=");
            stringBuilder.append(NetworkStats.tagToString(this.tag));
            stringBuilder.append(" metered=");
            stringBuilder.append(NetworkStats.meteredToString(this.metered));
            stringBuilder.append(" roaming=");
            stringBuilder.append(NetworkStats.roamingToString(this.roaming));
            stringBuilder.append(" defaultNetwork=");
            stringBuilder.append(NetworkStats.defaultNetworkToString(this.defaultNetwork));
            stringBuilder.append(" rxBytes=");
            stringBuilder.append(this.rxBytes);
            stringBuilder.append(" rxPackets=");
            stringBuilder.append(this.rxPackets);
            stringBuilder.append(" txBytes=");
            stringBuilder.append(this.txBytes);
            stringBuilder.append(" txPackets=");
            stringBuilder.append(this.txPackets);
            stringBuilder.append(" operations=");
            stringBuilder.append(this.operations);
            return stringBuilder.toString();
        }
    }

    public static interface NonMonotonicObserver<C> {
        public void foundNonMonotonic(NetworkStats var1, int var2, NetworkStats var3, int var4, C var5);

        public void foundNonMonotonic(NetworkStats var1, int var2, C var3);
    }

}

