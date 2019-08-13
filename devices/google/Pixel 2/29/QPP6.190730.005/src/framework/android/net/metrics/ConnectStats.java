/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.net.metrics;

import android.net.NetworkCapabilities;
import android.system.OsConstants;
import android.util.IntArray;
import android.util.SparseIntArray;
import com.android.internal.util.BitUtils;
import com.android.internal.util.TokenBucket;

public class ConnectStats {
    private static final int EALREADY = OsConstants.EALREADY;
    private static final int EINPROGRESS = OsConstants.EINPROGRESS;
    public int connectBlockingCount = 0;
    public int connectCount = 0;
    public final SparseIntArray errnos = new SparseIntArray();
    public int eventCount = 0;
    public int ipv6ConnectCount = 0;
    public final IntArray latencies = new IntArray();
    public final TokenBucket mLatencyTb;
    public final int mMaxLatencyRecords;
    public final int netId;
    public final long transports;

    public ConnectStats(int n, long l, TokenBucket tokenBucket, int n2) {
        this.netId = n;
        this.transports = l;
        this.mLatencyTb = tokenBucket;
        this.mMaxLatencyRecords = n2;
    }

    private void countConnect(int n, String string2) {
        ++this.connectCount;
        if (!ConnectStats.isNonBlocking(n)) {
            ++this.connectBlockingCount;
        }
        if (ConnectStats.isIPv6(string2)) {
            ++this.ipv6ConnectCount;
        }
    }

    private void countError(int n) {
        int n2 = this.errnos.get(n, 0);
        this.errnos.put(n, n2 + 1);
    }

    private void countLatency(int n, int n2) {
        if (ConnectStats.isNonBlocking(n)) {
            return;
        }
        if (!this.mLatencyTb.get()) {
            return;
        }
        if (this.latencies.size() >= this.mMaxLatencyRecords) {
            return;
        }
        this.latencies.add(n2);
    }

    private static boolean isIPv6(String string2) {
        return string2.contains(":");
    }

    static boolean isNonBlocking(int n) {
        boolean bl = n == EINPROGRESS || n == EALREADY;
        return bl;
    }

    private static boolean isSuccess(int n) {
        boolean bl = n == 0 || ConnectStats.isNonBlocking(n);
        return bl;
    }

    boolean addEvent(int n, int n2, String string2) {
        ++this.eventCount;
        if (ConnectStats.isSuccess(n)) {
            this.countConnect(n, string2);
            this.countLatency(n, n2);
            return true;
        }
        this.countError(n);
        return false;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder("ConnectStats(");
        stringBuilder.append("netId=");
        stringBuilder.append(this.netId);
        stringBuilder = stringBuilder.append(", ");
        int[] arrn = BitUtils.unpackBits(this.transports);
        int n2 = arrn.length;
        for (n = 0; n < n2; ++n) {
            stringBuilder.append(NetworkCapabilities.transportNameOf(arrn[n]));
            stringBuilder.append(", ");
        }
        stringBuilder.append(String.format("%d events, ", this.eventCount));
        stringBuilder.append(String.format("%d success, ", this.connectCount));
        stringBuilder.append(String.format("%d blocking, ", this.connectBlockingCount));
        stringBuilder.append(String.format("%d IPv6 dst", this.ipv6ConnectCount));
        for (n = 0; n < this.errnos.size(); ++n) {
            stringBuilder.append(String.format(", %s: %d", OsConstants.errnoName((int)this.errnos.keyAt(n)), this.errnos.valueAt(n)));
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

