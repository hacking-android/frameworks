/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

public abstract class SSLSocket
extends Socket {
    protected SSLSocket() {
    }

    protected SSLSocket(String string, int n) throws IOException, UnknownHostException {
        super(string, n);
    }

    protected SSLSocket(String string, int n, InetAddress inetAddress, int n2) throws IOException, UnknownHostException {
        super(string, n, inetAddress, n2);
    }

    protected SSLSocket(InetAddress inetAddress, int n) throws IOException {
        super(inetAddress, n);
    }

    protected SSLSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        super(inetAddress, n, inetAddress2, n2);
    }

    public abstract void addHandshakeCompletedListener(HandshakeCompletedListener var1);

    public String getApplicationProtocol() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean getEnableSessionCreation();

    public abstract String[] getEnabledCipherSuites();

    public abstract String[] getEnabledProtocols();

    public String getHandshakeApplicationProtocol() {
        throw new UnsupportedOperationException();
    }

    public BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
        throw new UnsupportedOperationException();
    }

    public SSLSession getHandshakeSession() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean getNeedClientAuth();

    public SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = new SSLParameters();
        sSLParameters.setCipherSuites(this.getEnabledCipherSuites());
        sSLParameters.setProtocols(this.getEnabledProtocols());
        if (this.getNeedClientAuth()) {
            sSLParameters.setNeedClientAuth(true);
        } else if (this.getWantClientAuth()) {
            sSLParameters.setWantClientAuth(true);
        }
        return sSLParameters;
    }

    public abstract SSLSession getSession();

    public abstract String[] getSupportedCipherSuites();

    public abstract String[] getSupportedProtocols();

    public abstract boolean getUseClientMode();

    public abstract boolean getWantClientAuth();

    public abstract void removeHandshakeCompletedListener(HandshakeCompletedListener var1);

    public abstract void setEnableSessionCreation(boolean var1);

    public abstract void setEnabledCipherSuites(String[] var1);

    public abstract void setEnabledProtocols(String[] var1);

    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> biFunction) {
        throw new UnsupportedOperationException();
    }

    public abstract void setNeedClientAuth(boolean var1);

    public void setSSLParameters(SSLParameters sSLParameters) {
        String[] arrstring = sSLParameters.getCipherSuites();
        if (arrstring != null) {
            this.setEnabledCipherSuites(arrstring);
        }
        if ((arrstring = sSLParameters.getProtocols()) != null) {
            this.setEnabledProtocols(arrstring);
        }
        if (sSLParameters.getNeedClientAuth()) {
            this.setNeedClientAuth(true);
        } else if (sSLParameters.getWantClientAuth()) {
            this.setWantClientAuth(true);
        } else {
            this.setWantClientAuth(false);
        }
    }

    public abstract void setUseClientMode(boolean var1);

    public abstract void setWantClientAuth(boolean var1);

    public abstract void startHandshake() throws IOException;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SSL");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

