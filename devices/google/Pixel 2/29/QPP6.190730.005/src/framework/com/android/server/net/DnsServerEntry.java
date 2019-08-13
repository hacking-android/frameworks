/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.net;

import java.net.InetAddress;

class DnsServerEntry
implements Comparable<DnsServerEntry> {
    public final InetAddress address;
    public long expiry;

    public DnsServerEntry(InetAddress inetAddress, long l) throws IllegalArgumentException {
        this.address = inetAddress;
        this.expiry = l;
    }

    @Override
    public int compareTo(DnsServerEntry dnsServerEntry) {
        return Long.compare(dnsServerEntry.expiry, this.expiry);
    }
}

