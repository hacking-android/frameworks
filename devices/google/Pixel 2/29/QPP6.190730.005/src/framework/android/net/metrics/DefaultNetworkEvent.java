/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.net.NetworkCapabilities;
import com.android.internal.util.BitUtils;
import java.util.StringJoiner;

public class DefaultNetworkEvent {
    public final long creationTimeMs;
    public long durationMs;
    public int finalScore;
    public int initialScore;
    public boolean ipv4;
    public boolean ipv6;
    public int netId = 0;
    public int previousTransports;
    public int transports;
    public long validatedMs;

    public DefaultNetworkEvent(long l) {
        this.creationTimeMs = l;
    }

    private String ipSupport() {
        if (this.ipv4 && this.ipv6) {
            return "IPv4v6";
        }
        if (this.ipv6) {
            return "IPv6";
        }
        if (this.ipv4) {
            return "IPv4";
        }
        return "NONE";
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "DefaultNetworkEvent(", ")");
        Object object = new StringBuilder();
        ((StringBuilder)object).append("netId=");
        ((StringBuilder)object).append(this.netId);
        stringJoiner.add(((StringBuilder)object).toString());
        object = BitUtils.unpackBits(this.transports);
        int n = ((int[])object).length;
        for (int i = 0; i < n; ++i) {
            stringJoiner.add(NetworkCapabilities.transportNameOf(object[i]));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ip=");
        ((StringBuilder)object).append(this.ipSupport());
        stringJoiner.add(((StringBuilder)object).toString());
        if (this.initialScore > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("initial_score=");
            ((StringBuilder)object).append(this.initialScore);
            stringJoiner.add(((StringBuilder)object).toString());
        }
        if (this.finalScore > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("final_score=");
            ((StringBuilder)object).append(this.finalScore);
            stringJoiner.add(((StringBuilder)object).toString());
        }
        stringJoiner.add(String.format("duration=%.0fs", (double)this.durationMs / 1000.0));
        stringJoiner.add(String.format("validation=%04.1f%%", (double)this.validatedMs * 100.0 / (double)this.durationMs));
        return stringJoiner.toString();
    }

    public void updateDuration(long l) {
        this.durationMs = l - this.creationTimeMs;
    }
}

