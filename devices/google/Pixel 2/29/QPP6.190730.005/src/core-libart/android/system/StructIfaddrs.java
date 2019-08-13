/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import java.net.InetAddress;

public final class StructIfaddrs {
    public final byte[] hwaddr;
    public final InetAddress ifa_addr;
    public final InetAddress ifa_broadaddr;
    public final int ifa_flags;
    public final String ifa_name;
    public final InetAddress ifa_netmask;

    public StructIfaddrs(String string, int n, InetAddress inetAddress, InetAddress inetAddress2, InetAddress inetAddress3, byte[] arrby) {
        this.ifa_name = string;
        this.ifa_flags = n;
        this.ifa_addr = inetAddress;
        this.ifa_netmask = inetAddress2;
        this.ifa_broadaddr = inetAddress3;
        this.hwaddr = arrby;
    }
}

