/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ssl.SSLParameters;

public abstract class SSLServerSocket
extends ServerSocket {
    protected SSLServerSocket() throws IOException {
    }

    protected SSLServerSocket(int n) throws IOException {
        super(n);
    }

    protected SSLServerSocket(int n, int n2) throws IOException {
        super(n, n2);
    }

    protected SSLServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        super(n, n2, inetAddress);
    }

    public abstract boolean getEnableSessionCreation();

    public abstract String[] getEnabledCipherSuites();

    public abstract String[] getEnabledProtocols();

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

    public abstract String[] getSupportedCipherSuites();

    public abstract String[] getSupportedProtocols();

    public abstract boolean getUseClientMode();

    public abstract boolean getWantClientAuth();

    public abstract void setEnableSessionCreation(boolean var1);

    public abstract void setEnabledCipherSuites(String[] var1);

    public abstract void setEnabledProtocols(String[] var1);

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SSL");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

