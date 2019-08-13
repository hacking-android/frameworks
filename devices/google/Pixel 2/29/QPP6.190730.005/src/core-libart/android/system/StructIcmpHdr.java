/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import android.system.OsConstants;

public final class StructIcmpHdr {
    private byte[] packet = new byte[8];

    private StructIcmpHdr() {
    }

    public static StructIcmpHdr IcmpEchoHdr(boolean bl, int n) {
        StructIcmpHdr structIcmpHdr = new StructIcmpHdr();
        byte[] arrby = structIcmpHdr.packet;
        int n2 = bl ? OsConstants.ICMP_ECHO : OsConstants.ICMP6_ECHO_REQUEST;
        arrby[0] = (byte)n2;
        arrby = structIcmpHdr.packet;
        arrby[6] = (byte)(n >> 8);
        arrby[7] = (byte)n;
        return structIcmpHdr;
    }

    public byte[] getBytes() {
        return (byte[])this.packet.clone();
    }
}

