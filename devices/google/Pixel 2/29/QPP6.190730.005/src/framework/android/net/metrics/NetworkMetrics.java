/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.net.NetworkCapabilities;
import android.net.metrics.ConnectStats;
import android.net.metrics.DnsEvent;
import com.android.internal.util.BitUtils;
import com.android.internal.util.TokenBucket;
import java.util.StringJoiner;

public class NetworkMetrics {
    private static final int CONNECT_LATENCY_MAXIMUM_RECORDS = 20000;
    private static final int INITIAL_DNS_BATCH_SIZE = 100;
    public final ConnectStats connectMetrics;
    public final DnsEvent dnsMetrics;
    public final int netId;
    public Summary pendingSummary;
    public final Summary summary;
    public final long transports;

    public NetworkMetrics(int n, long l, TokenBucket tokenBucket) {
        this.netId = n;
        this.transports = l;
        this.connectMetrics = new ConnectStats(n, l, tokenBucket, 20000);
        this.dnsMetrics = new DnsEvent(n, l, 100);
        this.summary = new Summary(n, l);
    }

    public void addConnectResult(int n, int n2, String object) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new Summary(this.netId, this.transports);
        }
        boolean bl = this.connectMetrics.addEvent(n, n2, (String)object);
        object = this.pendingSummary.connectErrorRate;
        double d = bl ? 0.0 : 1.0;
        ((Metrics)object).count(d);
        if (ConnectStats.isNonBlocking(n)) {
            this.pendingSummary.connectLatencies.count(n2);
        }
    }

    public void addDnsResult(int n, int n2, int n3) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new Summary(this.netId, this.transports);
        }
        boolean bl = this.dnsMetrics.addResult((byte)n, (byte)n2, n3);
        this.pendingSummary.dnsLatencies.count(n3);
        Metrics metrics = this.pendingSummary.dnsErrorRate;
        double d = bl ? 0.0 : 1.0;
        metrics.count(d);
    }

    public void addTcpStatsResult(int n, int n2, int n3, int n4) {
        if (this.pendingSummary == null) {
            this.pendingSummary = new Summary(this.netId, this.transports);
        }
        this.pendingSummary.tcpLossRate.count(n2, n);
        this.pendingSummary.roundTripTimeUs.count(n3);
        this.pendingSummary.sentAckTimeDiffenceMs.count(n4);
    }

    public Summary getPendingStats() {
        Summary summary = this.pendingSummary;
        this.pendingSummary = null;
        if (summary != null) {
            this.summary.merge(summary);
        }
        return summary;
    }

    static class Metrics {
        public int count;
        public double max = Double.MIN_VALUE;
        public double sum;

        Metrics() {
        }

        double average() {
            double d;
            double d2 = d = this.sum / (double)this.count;
            if (Double.isNaN(d)) {
                d2 = 0.0;
            }
            return d2;
        }

        void count(double d) {
            this.count(d, 1);
        }

        void count(double d, int n) {
            this.count += n;
            this.sum += d;
            this.max = Math.max(this.max, d);
        }

        void merge(Metrics metrics) {
            this.count += metrics.count;
            this.sum += metrics.sum;
            this.max = Math.max(this.max, metrics.max);
        }
    }

    public static class Summary {
        public final Metrics connectErrorRate = new Metrics();
        public final Metrics connectLatencies = new Metrics();
        public final Metrics dnsErrorRate = new Metrics();
        public final Metrics dnsLatencies = new Metrics();
        public final int netId;
        public final Metrics roundTripTimeUs = new Metrics();
        public final Metrics sentAckTimeDiffenceMs = new Metrics();
        public final Metrics tcpLossRate = new Metrics();
        public final long transports;

        public Summary(int n, long l) {
            this.netId = n;
            this.transports = l;
        }

        void merge(Summary summary) {
            this.dnsLatencies.merge(summary.dnsLatencies);
            this.dnsErrorRate.merge(summary.dnsErrorRate);
            this.connectLatencies.merge(summary.connectLatencies);
            this.connectErrorRate.merge(summary.connectErrorRate);
            this.tcpLossRate.merge(summary.tcpLossRate);
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
            int[] arrn = new StringBuilder();
            arrn.append("netId=");
            arrn.append(this.netId);
            stringJoiner.add(arrn.toString());
            arrn = BitUtils.unpackBits(this.transports);
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                stringJoiner.add(NetworkCapabilities.transportNameOf(arrn[i]));
            }
            stringJoiner.add(String.format("dns avg=%dms max=%dms err=%.1f%% tot=%d", (int)this.dnsLatencies.average(), (int)this.dnsLatencies.max, this.dnsErrorRate.average() * 100.0, this.dnsErrorRate.count));
            stringJoiner.add(String.format("connect avg=%dms max=%dms err=%.1f%% tot=%d", (int)this.connectLatencies.average(), (int)this.connectLatencies.max, this.connectErrorRate.average() * 100.0, this.connectErrorRate.count));
            stringJoiner.add(String.format("tcp avg_loss=%.1f%% total_sent=%d total_lost=%d", this.tcpLossRate.average() * 100.0, this.tcpLossRate.count, (int)this.tcpLossRate.sum));
            stringJoiner.add(String.format("tcp rtt=%dms", (int)(this.roundTripTimeUs.average() / 1000.0)));
            stringJoiner.add(String.format("tcp sent-ack_diff=%dms", (int)this.sentAckTimeDiffenceMs.average()));
            return stringJoiner.toString();
        }
    }

}

