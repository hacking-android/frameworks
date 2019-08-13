/*
 * Decompiled with CFR 0.145.
 */
package android.net.metrics;

import android.net.MacAddress;
import java.util.StringJoiner;

public class WakeupEvent {
    public MacAddress dstHwAddr;
    public String dstIp;
    public int dstPort;
    public int ethertype;
    public String iface;
    public int ipNextHeader;
    public String srcIp;
    public int srcPort;
    public long timestampMs;
    public int uid;

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "WakeupEvent(", ")");
        stringJoiner.add(String.format("%tT.%tL", this.timestampMs, this.timestampMs));
        stringJoiner.add(this.iface);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("uid: ");
        stringBuilder.append(Integer.toString(this.uid));
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("eth=0x");
        stringBuilder.append(Integer.toHexString(this.ethertype));
        stringJoiner.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("dstHw=");
        stringBuilder.append(this.dstHwAddr);
        stringJoiner.add(stringBuilder.toString());
        if (this.ipNextHeader > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("ipNxtHdr=");
            stringBuilder.append(this.ipNextHeader);
            stringJoiner.add(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("srcIp=");
            stringBuilder.append(this.srcIp);
            stringJoiner.add(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("dstIp=");
            stringBuilder.append(this.dstIp);
            stringJoiner.add(stringBuilder.toString());
            if (this.srcPort > -1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("srcPort=");
                stringBuilder.append(this.srcPort);
                stringJoiner.add(stringBuilder.toString());
            }
            if (this.dstPort > -1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("dstPort=");
                stringBuilder.append(this.dstPort);
                stringJoiner.add(stringBuilder.toString());
            }
        }
        return stringJoiner.toString();
    }
}

