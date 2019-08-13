/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Address;
import com.android.okhttp.Dns;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Route;
import com.android.okhttp.internal.RouteDatabase;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public final class RouteSelector {
    private final Address address;
    private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private int nextInetSocketAddressIndex;
    private int nextProxyIndex;
    private final List<Route> postponedRoutes = new ArrayList<Route>();
    private List<Proxy> proxies = Collections.emptyList();
    private final RouteDatabase routeDatabase;

    public RouteSelector(Address address, RouteDatabase routeDatabase) {
        this.address = address;
        this.routeDatabase = routeDatabase;
        this.resetNextProxy(address.url(), address.getProxy());
    }

    static String getHostString(InetSocketAddress inetSocketAddress) {
        InetAddress inetAddress = inetSocketAddress.getAddress();
        if (inetAddress == null) {
            return inetSocketAddress.getHostName();
        }
        return inetAddress.getHostAddress();
    }

    private boolean hasNextInetSocketAddress() {
        boolean bl = this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
        return bl;
    }

    private boolean hasNextPostponed() {
        return this.postponedRoutes.isEmpty() ^ true;
    }

    private boolean hasNextProxy() {
        boolean bl = this.nextProxyIndex < this.proxies.size();
        return bl;
    }

    private InetSocketAddress nextInetSocketAddress() throws IOException {
        if (this.hasNextInetSocketAddress()) {
            List<InetSocketAddress> list = this.inetSocketAddresses;
            int n = this.nextInetSocketAddressIndex;
            this.nextInetSocketAddressIndex = n + 1;
            return list.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No route to ");
        stringBuilder.append(this.address.getUriHost());
        stringBuilder.append("; exhausted inet socket addresses: ");
        stringBuilder.append(this.inetSocketAddresses);
        throw new SocketException(stringBuilder.toString());
    }

    private Route nextPostponed() {
        return this.postponedRoutes.remove(0);
    }

    private Proxy nextProxy() throws IOException {
        if (this.hasNextProxy()) {
            Object object = this.proxies;
            int n = this.nextProxyIndex;
            this.nextProxyIndex = n + 1;
            object = object.get(n);
            this.resetNextInetSocketAddress((Proxy)object);
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No route to ");
        stringBuilder.append(this.address.getUriHost());
        stringBuilder.append("; exhausted proxy configurations: ");
        stringBuilder.append(this.proxies);
        throw new SocketException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resetNextInetSocketAddress(Proxy list) throws IOException {
        Object object;
        int n;
        this.inetSocketAddresses = new ArrayList<InetSocketAddress>();
        if (((Proxy)((Object)list)).type() != Proxy.Type.DIRECT && ((Proxy)((Object)list)).type() != Proxy.Type.SOCKS) {
            object = ((Proxy)((Object)list)).address();
            if (!(object instanceof InetSocketAddress)) {
                list = new StringBuilder();
                ((StringBuilder)((Object)list)).append("Proxy.address() is not an InetSocketAddress: ");
                ((StringBuilder)((Object)list)).append(object.getClass());
                throw new IllegalArgumentException(((StringBuilder)((Object)list)).toString());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)object;
            object = RouteSelector.getHostString(inetSocketAddress);
            n = inetSocketAddress.getPort();
        } else {
            object = this.address.getUriHost();
            n = this.address.getUriPort();
        }
        if (n >= 1 && n <= 65535) {
            if (((Proxy)((Object)list)).type() == Proxy.Type.SOCKS) {
                this.inetSocketAddresses.add(InetSocketAddress.createUnresolved((String)object, n));
            } else {
                list = this.address.getDns().lookup((String)object);
                int n2 = list.size();
                for (int i = 0; i < n2; ++i) {
                    object = list.get(i);
                    this.inetSocketAddresses.add(new InetSocketAddress((InetAddress)object, n));
                }
            }
            this.nextInetSocketAddressIndex = 0;
            return;
        }
        list = new StringBuilder();
        ((StringBuilder)((Object)list)).append("No route to ");
        ((StringBuilder)((Object)list)).append((String)object);
        ((StringBuilder)((Object)list)).append(":");
        ((StringBuilder)((Object)list)).append(n);
        ((StringBuilder)((Object)list)).append("; port is out of range");
        throw new SocketException(((StringBuilder)((Object)list)).toString());
    }

    private void resetNextProxy(HttpUrl list, Proxy proxy) {
        if (proxy != null) {
            this.proxies = Collections.singletonList(proxy);
        } else {
            this.proxies = new ArrayList<Proxy>();
            list = this.address.getProxySelector().select(((HttpUrl)((Object)list)).uri());
            if (list != null) {
                this.proxies.addAll((Collection<Proxy>)list);
            }
            this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.proxies.add(Proxy.NO_PROXY);
        }
        this.nextProxyIndex = 0;
    }

    public void connectFailed(Route route, IOException iOException) {
        if (route.getProxy().type() != Proxy.Type.DIRECT && this.address.getProxySelector() != null) {
            this.address.getProxySelector().connectFailed(this.address.url().uri(), route.getProxy().address(), iOException);
        }
        this.routeDatabase.failed(route);
    }

    @UnsupportedAppUsage
    public boolean hasNext() {
        boolean bl = this.hasNextInetSocketAddress() || this.hasNextProxy() || this.hasNextPostponed();
        return bl;
    }

    public Route next() throws IOException {
        if (!this.hasNextInetSocketAddress()) {
            if (!this.hasNextProxy()) {
                if (this.hasNextPostponed()) {
                    return this.nextPostponed();
                }
                throw new NoSuchElementException();
            }
            this.lastProxy = this.nextProxy();
        }
        this.lastInetSocketAddress = this.nextInetSocketAddress();
        Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress);
        if (this.routeDatabase.shouldPostpone(route)) {
            this.postponedRoutes.add(route);
            return this.next();
        }
        return route;
    }
}

