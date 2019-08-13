/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

public class LowpanEnergyScanResult {
    public static final int UNKNOWN = Integer.MAX_VALUE;
    private int mChannel = Integer.MAX_VALUE;
    private int mMaxRssi = Integer.MAX_VALUE;

    LowpanEnergyScanResult() {
    }

    public int getChannel() {
        return this.mChannel;
    }

    public int getMaxRssi() {
        return this.mMaxRssi;
    }

    void setChannel(int n) {
        this.mChannel = n;
    }

    void setMaxRssi(int n) {
        this.mMaxRssi = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LowpanEnergyScanResult(channel: ");
        stringBuilder.append(this.mChannel);
        stringBuilder.append(", maxRssi:");
        stringBuilder.append(this.mMaxRssi);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

