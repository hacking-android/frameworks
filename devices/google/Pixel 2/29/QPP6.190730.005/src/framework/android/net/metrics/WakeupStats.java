/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.net.MacAddress;
import android.net.metrics.WakeupEvent;
import android.os.SystemClock;
import android.util.SparseIntArray;
import java.util.StringJoiner;

public class WakeupStats {
    private static final int NO_UID = -1;
    public long applicationWakeups = 0L;
    public final long creationTimeMs = SystemClock.elapsedRealtime();
    public long durationSec = 0L;
    public final SparseIntArray ethertypes = new SparseIntArray();
    public final String iface;
    public final SparseIntArray ipNextHeaders = new SparseIntArray();
    public long l2BroadcastCount = 0L;
    public long l2MulticastCount = 0L;
    public long l2UnicastCount = 0L;
    public long noUidWakeups = 0L;
    public long nonApplicationWakeups = 0L;
    public long rootWakeups = 0L;
    public long systemWakeups = 0L;
    public long totalWakeups = 0L;

    public WakeupStats(String string2) {
        this.iface = string2;
    }

    private static void increment(SparseIntArray sparseIntArray, int n) {
        sparseIntArray.put(n, sparseIntArray.get(n, 0) + 1);
    }

    public void countEvent(WakeupEvent wakeupEvent) {
        ++this.totalWakeups;
        int n = wakeupEvent.uid;
        if (n != -1) {
            if (n != 0) {
                if (n != 1000) {
                    if (wakeupEvent.uid >= 10000) {
                        ++this.applicationWakeups;
                    } else {
                        ++this.nonApplicationWakeups;
                    }
                } else {
                    ++this.systemWakeups;
                }
            } else {
                ++this.rootWakeups;
            }
        } else {
            ++this.noUidWakeups;
        }
        n = wakeupEvent.dstHwAddr.getAddressType();
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    ++this.l2BroadcastCount;
                }
            } else {
                ++this.l2MulticastCount;
            }
        } else {
            ++this.l2UnicastCount;
        }
        WakeupStats.increment(this.ethertypes, wakeupEvent.ethertype);
        if (wakeupEvent.ipNextHeader >= 0) {
            WakeupStats.increment(this.ipNextHeaders, wakeupEvent.ipNextHeader);
        }
    }

    public String toString() {
        int n;
        this.updateDuration();
        StringJoiner stringJoiner = new StringJoiner(", ", "WakeupStats(", ")");
        stringJoiner.add(this.iface);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.durationSec);
        stringBuilder.append("s");
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("total: ");
        stringBuilder.append(this.totalWakeups);
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("root: ");
        stringBuilder.append(this.rootWakeups);
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("system: ");
        stringBuilder.append(this.systemWakeups);
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("apps: ");
        stringBuilder.append(this.applicationWakeups);
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("non-apps: ");
        stringBuilder.append(this.nonApplicationWakeups);
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("no uid: ");
        stringBuilder.append(this.noUidWakeups);
        stringJoiner.add(stringBuilder.toString());
        stringJoiner.add(String.format("l2 unicast/multicast/broadcast: %d/%d/%d", this.l2UnicastCount, this.l2MulticastCount, this.l2BroadcastCount));
        for (n = 0; n < this.ethertypes.size(); ++n) {
            stringJoiner.add(String.format("ethertype 0x%x: %d", this.ethertypes.keyAt(n), this.ethertypes.valueAt(n)));
        }
        for (n = 0; n < this.ipNextHeaders.size(); ++n) {
            stringJoiner.add(String.format("ipNxtHdr %d: %d", this.ipNextHeaders.keyAt(n), this.ipNextHeaders.valueAt(n)));
        }
        return stringJoiner.toString();
    }

    public void updateDuration() {
        this.durationSec = (SystemClock.elapsedRealtime() - this.creationTimeMs) / 1000L;
    }
}

