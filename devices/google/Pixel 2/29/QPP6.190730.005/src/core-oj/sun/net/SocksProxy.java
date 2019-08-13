/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.Proxy;
import java.net.SocketAddress;

public final class SocksProxy
extends Proxy {
    private final int version;

    private SocksProxy(SocketAddress socketAddress, int n) {
        super(Proxy.Type.SOCKS, socketAddress);
        this.version = n;
    }

    public static SocksProxy create(SocketAddress socketAddress, int n) {
        return new SocksProxy(socketAddress, n);
    }

    public int protocolVersion() {
        return this.version;
    }
}

