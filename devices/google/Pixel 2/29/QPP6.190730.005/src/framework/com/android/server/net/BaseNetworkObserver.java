/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.net;

import android.net.INetworkManagementEventObserver;
import android.net.LinkAddress;
import android.net.RouteInfo;

public class BaseNetworkObserver
extends INetworkManagementEventObserver.Stub {
    @Override
    public void addressRemoved(String string2, LinkAddress linkAddress) {
    }

    @Override
    public void addressUpdated(String string2, LinkAddress linkAddress) {
    }

    @Override
    public void interfaceAdded(String string2) {
    }

    @Override
    public void interfaceClassDataActivityChanged(String string2, boolean bl, long l) {
    }

    @Override
    public void interfaceDnsServerInfo(String string2, long l, String[] arrstring) {
    }

    @Override
    public void interfaceLinkStateChanged(String string2, boolean bl) {
    }

    @Override
    public void interfaceRemoved(String string2) {
    }

    @Override
    public void interfaceStatusChanged(String string2, boolean bl) {
    }

    @Override
    public void limitReached(String string2, String string3) {
    }

    @Override
    public void routeRemoved(RouteInfo routeInfo) {
    }

    @Override
    public void routeUpdated(RouteInfo routeInfo) {
    }
}

