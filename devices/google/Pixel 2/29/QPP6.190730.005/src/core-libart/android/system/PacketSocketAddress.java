/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.net.SocketAddress;

public final class PacketSocketAddress
extends SocketAddress {
    public byte[] sll_addr;
    public short sll_hatype;
    public int sll_ifindex;
    public byte sll_pkttype;
    public short sll_protocol;

    @UnsupportedAppUsage
    public PacketSocketAddress(int n, byte[] arrby) {
        this(0, n, 0, 0, arrby);
    }

    @UnsupportedAppUsage
    public PacketSocketAddress(short s, int n) {
        this(s, n, 0, 0, null);
    }

    public PacketSocketAddress(short s, int n, short s2, byte by, byte[] arrby) {
        this.sll_protocol = s;
        this.sll_ifindex = n;
        this.sll_hatype = s2;
        this.sll_pkttype = by;
        this.sll_addr = arrby;
    }
}

