/*
 * Decompiled with CFR 0.145.
 */
package javax.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;

class DefaultServerSocketFactory
extends ServerSocketFactory {
    DefaultServerSocketFactory() {
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        return new ServerSocket();
    }

    @Override
    public ServerSocket createServerSocket(int n) throws IOException {
        return new ServerSocket(n);
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2) throws IOException {
        return new ServerSocket(n, n2);
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return new ServerSocket(n, n2, inetAddress);
    }
}

