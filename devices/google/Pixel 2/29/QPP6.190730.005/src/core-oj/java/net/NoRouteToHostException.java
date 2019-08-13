/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.SocketException;

public class NoRouteToHostException
extends SocketException {
    private static final long serialVersionUID = -1897550894873493790L;

    public NoRouteToHostException() {
    }

    public NoRouteToHostException(String string) {
        super(string);
    }
}

