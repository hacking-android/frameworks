/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.net;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.RouteInfo;
import com.android.server.net.BaseNetworkObserver;
import com.android.server.net.DnsServerRepository;
import java.util.Arrays;

public class NetlinkTracker
extends BaseNetworkObserver {
    private static final boolean DBG = false;
    private final String TAG;
    private final Callback mCallback;
    private DnsServerRepository mDnsServerRepository;
    private final String mInterfaceName;
    private final LinkProperties mLinkProperties;

    @UnsupportedAppUsage
    public NetlinkTracker(String string2, Callback callback) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NetlinkTracker/");
        stringBuilder.append(string2);
        this.TAG = stringBuilder.toString();
        this.mInterfaceName = string2;
        this.mCallback = callback;
        this.mLinkProperties = new LinkProperties();
        this.mLinkProperties.setInterfaceName(this.mInterfaceName);
        this.mDnsServerRepository = new DnsServerRepository();
    }

    private void maybeLog(String string2, Object object) {
    }

    private void maybeLog(String string2, String string3, LinkAddress linkAddress) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addressRemoved(String string2, LinkAddress linkAddress) {
        boolean bl;
        if (!this.mInterfaceName.equals(string2)) return;
        this.maybeLog("addressRemoved", string2, linkAddress);
        synchronized (this) {
            bl = this.mLinkProperties.removeLinkAddress(linkAddress);
        }
        if (!bl) return;
        this.mCallback.update();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addressUpdated(String string2, LinkAddress linkAddress) {
        boolean bl;
        if (!this.mInterfaceName.equals(string2)) return;
        this.maybeLog("addressUpdated", string2, linkAddress);
        synchronized (this) {
            bl = this.mLinkProperties.addLinkAddress(linkAddress);
        }
        if (!bl) return;
        this.mCallback.update();
    }

    @UnsupportedAppUsage
    public void clearLinkProperties() {
        synchronized (this) {
            DnsServerRepository dnsServerRepository;
            this.mDnsServerRepository = dnsServerRepository = new DnsServerRepository();
            this.mLinkProperties.clear();
            this.mLinkProperties.setInterfaceName(this.mInterfaceName);
            return;
        }
    }

    @UnsupportedAppUsage
    public LinkProperties getLinkProperties() {
        synchronized (this) {
            LinkProperties linkProperties = new LinkProperties(this.mLinkProperties);
            return linkProperties;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void interfaceDnsServerInfo(String string2, long l, String[] arrstring) {
        if (!this.mInterfaceName.equals(string2)) return;
        this.maybeLog("interfaceDnsServerInfo", Arrays.toString(arrstring));
        if (!this.mDnsServerRepository.addServers(l, arrstring)) return;
        synchronized (this) {
            this.mDnsServerRepository.setDnsServersOn(this.mLinkProperties);
        }
        this.mCallback.update();
    }

    @Override
    public void interfaceRemoved(String string2) {
        this.maybeLog("interfaceRemoved", string2);
        if (this.mInterfaceName.equals(string2)) {
            this.clearLinkProperties();
            this.mCallback.update();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void routeRemoved(RouteInfo routeInfo) {
        boolean bl;
        if (!this.mInterfaceName.equals(routeInfo.getInterface())) return;
        this.maybeLog("routeRemoved", routeInfo);
        synchronized (this) {
            bl = this.mLinkProperties.removeRoute(routeInfo);
        }
        if (!bl) return;
        this.mCallback.update();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void routeUpdated(RouteInfo routeInfo) {
        boolean bl;
        if (!this.mInterfaceName.equals(routeInfo.getInterface())) return;
        this.maybeLog("routeUpdated", routeInfo);
        synchronized (this) {
            bl = this.mLinkProperties.addRoute(routeInfo);
        }
        if (!bl) return;
        this.mCallback.update();
    }

    public static interface Callback {
        public void update();
    }

}

