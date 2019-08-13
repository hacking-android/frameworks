/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public abstract class SSLContextSpi {
    private SSLSocket getDefaultSocket() {
        try {
            SSLSocket sSLSocket = (SSLSocket)this.engineGetSocketFactory().createSocket();
            return sSLSocket;
        }
        catch (IOException iOException) {
            throw new UnsupportedOperationException("Could not obtain parameters", iOException);
        }
    }

    protected abstract SSLEngine engineCreateSSLEngine();

    protected abstract SSLEngine engineCreateSSLEngine(String var1, int var2);

    protected abstract SSLSessionContext engineGetClientSessionContext();

    protected SSLParameters engineGetDefaultSSLParameters() {
        return this.getDefaultSocket().getSSLParameters();
    }

    protected abstract SSLSessionContext engineGetServerSessionContext();

    protected abstract SSLServerSocketFactory engineGetServerSocketFactory();

    protected abstract SSLSocketFactory engineGetSocketFactory();

    protected SSLParameters engineGetSupportedSSLParameters() {
        SSLSocket sSLSocket = this.getDefaultSocket();
        SSLParameters sSLParameters = new SSLParameters();
        sSLParameters.setCipherSuites(sSLSocket.getSupportedCipherSuites());
        sSLParameters.setProtocols(sSLSocket.getSupportedProtocols());
        return sSLParameters;
    }

    protected abstract void engineInit(KeyManager[] var1, TrustManager[] var2, SecureRandom var3) throws KeyManagementException;
}

