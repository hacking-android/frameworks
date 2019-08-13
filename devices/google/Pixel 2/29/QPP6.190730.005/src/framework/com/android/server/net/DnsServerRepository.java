/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.net;

import android.net.LinkProperties;
import com.android.server.net.DnsServerEntry;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class DnsServerRepository {
    public static final int NUM_CURRENT_SERVERS = 3;
    public static final int NUM_SERVERS = 12;
    public static final String TAG = "DnsServerRepository";
    private ArrayList<DnsServerEntry> mAllServers = new ArrayList(12);
    private Set<InetAddress> mCurrentServers = new HashSet<InetAddress>();
    private HashMap<InetAddress, DnsServerEntry> mIndex = new HashMap(12);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean updateCurrentServers() {
        synchronized (this) {
            DnsServerEntry dnsServerEntry;
            long l = System.currentTimeMillis();
            boolean bl = false;
            for (int i = this.mAllServers.size() - 1; i >= 0 && (i >= 12 || this.mAllServers.get((int)i).expiry < l); bl |= this.mCurrentServers.remove((Object)dnsServerEntry.address), --i) {
                dnsServerEntry = this.mAllServers.remove(i);
                this.mIndex.remove(dnsServerEntry.address);
            }
            for (DnsServerEntry dnsServerEntry2 : this.mAllServers) {
                if (this.mCurrentServers.size() >= 3) break;
                boolean bl2 = this.mCurrentServers.add(dnsServerEntry2.address);
                bl |= bl2;
            }
            return bl;
        }
    }

    private boolean updateExistingEntry(InetAddress object, long l) {
        synchronized (this) {
            block4 : {
                object = this.mIndex.get(object);
                if (object == null) break block4;
                ((DnsServerEntry)object).expiry = l;
                return true;
            }
            return false;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean addServers(long l, String[] arrstring) {
        synchronized (this) {
            long l2 = System.currentTimeMillis();
            l = 1000L * l + l2;
            int n = arrstring.length;
            int n2 = 0;
            do {
                InetAddress inetAddress;
                if (n2 >= n) {
                    Collections.sort(this.mAllServers);
                    return this.updateCurrentServers();
                }
                String string2 = arrstring[n2];
                try {
                    inetAddress = InetAddress.parseNumericAddress((String)string2);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                if (!this.updateExistingEntry(inetAddress, l) && l > l2) {
                    DnsServerEntry dnsServerEntry = new DnsServerEntry(inetAddress, l);
                    this.mAllServers.add(dnsServerEntry);
                    this.mIndex.put(inetAddress, dnsServerEntry);
                }
                ++n2;
            } while (true);
        }
    }

    public void setDnsServersOn(LinkProperties linkProperties) {
        synchronized (this) {
            linkProperties.setDnsServers(this.mCurrentServers);
            return;
        }
    }
}

