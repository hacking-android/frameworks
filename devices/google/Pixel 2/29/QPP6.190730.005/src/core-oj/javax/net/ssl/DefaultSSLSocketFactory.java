/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSocketFactory;

class DefaultSSLSocketFactory
extends SSLSocketFactory {
    private Exception reason;

    DefaultSSLSocketFactory(Exception exception) {
        this.reason = exception;
    }

    private Socket throwException() throws SocketException {
        throw (SocketException)new SocketException(this.reason.toString()).initCause(this.reason);
    }

    @Override
    public Socket createSocket() throws IOException {
        return this.throwException();
    }

    @Override
    public Socket createSocket(String string, int n) throws IOException {
        return this.throwException();
    }

    @Override
    public Socket createSocket(String string, int n, InetAddress inetAddress, int n2) throws IOException {
        return this.throwException();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        return this.throwException();
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        return this.throwException();
    }

    @Override
    public Socket createSocket(Socket socket, String string, int n, boolean bl) throws IOException {
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

