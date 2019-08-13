/*
 * Decompiled with CFR 0.145.
 */
package sun.net;

import java.net.Proxy;
import java.net.SocketAddress;

public final class ApplicationProxy
extends Proxy {
    private ApplicationProxy(Proxy proxy) {
        super(proxy.type(), proxy.address());
    }

    public static ApplicationProxy create(Proxy proxy) {
        return new ApplicationProxy(proxy);
    }
}

