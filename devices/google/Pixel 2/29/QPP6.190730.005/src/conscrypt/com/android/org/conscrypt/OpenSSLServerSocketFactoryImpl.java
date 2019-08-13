/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ConscryptServerSocket;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import javax.net.ssl.SSLServerSocketFactory;

final class OpenSSLServerSocketFactoryImpl
extends SSLServerSocketFactory {
    private static boolean useEngineSocketByDefault = SSLUtils.USE_ENGINE_SOCKET_BY_DEFAULT;
    private IOException instantiationException;
    private SSLParametersImpl sslParameters;
    private boolean useEngineSocket = useEngineSocketByDefault;

    OpenSSLServerSocketFactoryImpl() {
        try {
            this.sslParameters = SSLParametersImpl.getDefault();
            this.sslParameters.setUseClientMode(false);
        }
        catch (KeyManagementException keyManagementException) {
            this.instantiationException = new IOException("Delayed instantiation exception:");
            this.instantiationException.initCause(keyManagementException);
        }
    }

    OpenSSLServerSocketFactoryImpl(SSLParametersImpl sSLParametersImpl) {
        this.sslParameters = (SSLParametersImpl)sSLParametersImpl.clone();
        this.sslParameters.setUseClientMode(false);
    }

    static void setUseEngineSocketByDefault(boolean bl) {
        useEngineSocketByDefault = bl;
    }

    @Override
    public ServerSocket createServerSocket() throws IOException {
        return new ConscryptServerSocket((SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public ServerSocket createServerSocket(int n) throws IOException {
        return new ConscryptServerSocket(n, (SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2) throws IOException {
        return new ConscryptServerSocket(n, n2, (SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public ServerSocket createServerSocket(int n, int n2, InetAddress inetAddress) throws IOException {
        return new ConscryptServerSocket(n, n2, inetAddress, (SSLParametersImpl)this.sslParameters.clone()).setUseEngineSocket(this.useEngineSocket);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return this.sslParameters.getEnabledCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return NativeCrypto.getSupportedCipherSuites();
    }

    void setUseEngineSocket(boolean bl) {
        this.useEngineSocket = bl;
    }
}

