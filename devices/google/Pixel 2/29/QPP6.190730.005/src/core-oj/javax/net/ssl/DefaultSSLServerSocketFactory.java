/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import javax.net.ssl.SSLServerSocketFactory;

class DefaultSSLServerSocketFactory
extends SSLServerSocketFactory {
    private final Exception reason;

    DefaultSSLServerSocketFactory(Exception exception) {
        this.reason = exception;
    }

    private ServerSocket throwException() throws SocketException {
        throw (SocketException)new SocketException(this.reason.toString()).initCause(this.reason);
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        return this.throwException();
    }

    @Override
    public ServerSocket createServerSocket(int n) throws IOException {
        return this.throwException();
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2) throws IOException {
        return this.throwException();
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return this.throwException();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }
}

