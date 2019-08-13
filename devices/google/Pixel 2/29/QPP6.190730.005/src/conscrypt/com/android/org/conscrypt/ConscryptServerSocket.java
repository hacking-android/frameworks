/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractConscryptSocket;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLSocketImpl;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.SSLParametersImpl;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;

final class ConscryptServerSocket
extends SSLServerSocket {
    private boolean channelIdEnabled;
    private final SSLParametersImpl sslParameters;
    private boolean useEngineSocket;

    ConscryptServerSocket(int n, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(n, n2);
        this.sslParameters = sSLParametersImpl;
    }

    ConscryptServerSocket(int n, int n2, InetAddress inetAddress, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(n, n2, inetAddress);
        this.sslParameters = sSLParametersImpl;
    }

    ConscryptServerSocket(int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(n);
        this.sslParameters = sSLParametersImpl;
    }

    ConscryptServerSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        this.sslParameters = sSLParametersImpl;
    }

    @Override
    public Socket accept() throws IOException {
        OpenSSLSocketImpl openSSLSocketImpl = this.useEngineSocket ? Platform.createEngineSocket(this.sslParameters) : Platform.createFileDescriptorSocket(this.sslParameters);
        ((AbstractConscryptSocket)openSSLSocketImpl).setChannelIdEnabled(this.channelIdEnabled);
        this.implAccept(openSSLSocketImpl);
        return openSSLSocketImpl;
    }

    @Override
    public boolean getEnableSessionCreation() {
        return this.sslParameters.getEnableSessionCreation();
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public String[] getEnabledProtocols() {
        return this.sslParameters.getEnabledProtocols();
    }

    @Override
    public boolean getNeedClientAuth() {
        return this.sslParameters.getNeedClientAuth();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    @Override
    public String[] getSupportedProtocols() {
        return NativeCrypto.getSupportedProtocols();
    }

    @Override
    public boolean getUseClientMode() {
        return this.sslParameters.getUseClientMode();
    }

    @Override
    public boolean getWantClientAuth() {
        return this.sslParameters.getWantClientAuth();
    }

    boolean isChannelIdEnabled() {
        return this.channelIdEnabled;
    }

    void setChannelIdEnabled(boolean bl) {
        this.channelIdEnabled = bl;
    }

    @Override
    public void setEnableSessionCreation(boolean bl) {
        this.sslParameters.setEnableSessionCreation(bl);
    }

    @Override
    public void setEnabledCipherSuites(String[] arrstring) {
        this.sslParameters.setEnabledCipherSuites(arrstring);
    }

    @Override
    public void setEnabledProtocols(String[] arrstring) {
        this.sslParameters.setEnabledProtocols(arrstring);
    }

    @Override
    public void setNeedClientAuth(boolean bl) {
        this.sslParameters.setNeedClientAuth(bl);
    }

    @Override
    public void setUseClientMode(boolean bl) {
        this.sslParameters.setUseClientMode(bl);
    }

    ConscryptServerSocket setUseEngineSocket(boolean bl) {
        this.useEngineSocket = bl;
        return this;
    }

    @Override
    public void setWantClientAuth(boolean bl) {
        this.sslParameters.setWantClientAuth(bl);
    }
}

