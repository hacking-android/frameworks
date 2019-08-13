/*
 * Decompiled with CFR 0.145.
 */
package javax.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

class DefaultSocketFactory
extends SocketFactory {
    DefaultSocketFactory() {
    }

    @Override
    public Socket createSocket() {
        return new Socket();
    }

    @Override
    public Socket createSocket(String string, int n) throws IOException, UnknownHostException {
        return new Socket(string, n);
    }

    @Override
    public Socket createSocket(String string, int n, InetAddress inetAddress, int n2) throws IOException, UnknownHostException {
        return new Socket(string, n, inetAddress, n2);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        return new Socket(inetAddress, n);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        return new Socket(inetAddress, n, inetAddress2, n2);
    }
}

