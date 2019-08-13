/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Address;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.net.ssl.SSLSocketFactory;

public final class Route {
    final Address address;
    final InetSocketAddress inetSocketAddress;
    final Proxy proxy;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (address != null) {
            if (proxy != null) {
                if (inetSocketAddress != null) {
                    this.address = address;
                    this.proxy = proxy;
                    this.inetSocketAddress = inetSocketAddress;
                    return;
                }
                throw new NullPointerException("inetSocketAddress == null");
            }
            throw new NullPointerException("proxy == null");
        }
        throw new NullPointerException("address == null");
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Route;
        boolean bl2 = false;
        if (bl) {
            object = (Route)object;
            if (this.address.equals(((Route)object).address) && this.proxy.equals(((Route)object).proxy) && this.inetSocketAddress.equals(((Route)object).inetSocketAddress)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public Address getAddress() {
        return this.address;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public InetSocketAddress getSocketAddress() {
        return this.inetSocketAddress;
    }

    public int hashCode() {
        return ((17 * 31 + this.address.hashCode()) * 31 + this.proxy.hashCode()) * 31 + this.inetSocketAddress.hashCode();
    }

    public boolean requiresTunnel() {
        boolean bl = this.address.sslSocketFactory != null && this.proxy.type() == Proxy.Type.HTTP;
        return bl;
    }
}

