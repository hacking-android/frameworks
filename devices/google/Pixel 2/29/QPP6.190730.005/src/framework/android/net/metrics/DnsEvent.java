/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.net.NetworkCapabilities;
import com.android.internal.util.BitUtils;
import java.util.Arrays;

public final class DnsEvent {
    private static final int SIZE_LIMIT = 20000;
    public int eventCount;
    public byte[] eventTypes;
    public int[] latenciesMs;
    public final int netId;
    public byte[] returnCodes;
    public int successCount;
    public final long transports;

    public DnsEvent(int n, long l, int n2) {
        this.netId = n;
        this.transports = l;
        this.eventTypes = new byte[n2];
        this.returnCodes = new byte[n2];
        this.latenciesMs = new int[n2];
    }

    boolean addResult(byte by, byte by2, int n) {
        boolean bl = by2 == 0;
        int n2 = this.eventCount;
        if (n2 >= 20000) {
            return bl;
        }
        if (n2 == this.eventTypes.length) {
            this.resize((int)((double)n2 * 1.4));
        }
        byte[] arrby = this.eventTypes;
        n2 = this.eventCount;
        arrby[n2] = by;
        this.returnCodes[n2] = by2;
        this.latenciesMs[n2] = n;
        this.eventCount = n2 + 1;
        if (bl) {
            ++this.successCount;
        }
        return bl;
    }

    public void resize(int n) {
        this.eventTypes = Arrays.copyOf(this.eventTypes, n);
        this.returnCodes = Arrays.copyOf(this.returnCodes, n);
        this.latenciesMs = Arrays.copyOf(this.latenciesMs, n);
    }

    public String toString() {
        int[] arrn = new StringBuilder("DnsEvent(");
        arrn.append("netId=");
        arrn.append(this.netId);
        StringBuilder stringBuilder = arrn.append(", ");
        arrn = BitUtils.unpackBits(this.transports);
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(NetworkCapabilities.transportNameOf(arrn[i]));
            stringBuilder.append(", ");
        }
        stringBuilder.append(String.format("%d events, ", this.eventCount));
        stringBuilder.append(String.format("%d success)", this.successCount));
        return stringBuilder.toString();
    }
}

