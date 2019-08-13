/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.Preconditions;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.SSLUtils;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import javax.net.ssl.SSLSocketFactory;

final class OpenSSLSocketFactoryImpl
extends SSLSocketFactory {
    private static boolean useEngineSocketByDefault = SSLUtils.USE_ENGINE_SOCKET_BY_DEFAULT;
    private final IOException instantiationException;
    @UnsupportedAppUsage
    private final SSLParametersImpl sslParameters;
    private boolean useEngineSocket = useEngineSocketByDefault;

    @UnsupportedAppUsage
    OpenSSLSocketFactoryImpl() {
        SSLParametersImpl sSLParametersImpl = null;
        IOException iOException = null;
        try {
            SSLParametersImpl sSLParametersImpl2;
            sSLParametersImpl = sSLParametersImpl2 = SSLParametersImpl.getDefault();
        }
        catch (KeyManagementException keyManagementException) {
            iOException = new IOException("Delayed instantiation exception:", keyManagementException);
        }
        this.sslParameters = sSLParametersImpl;
        this.instantiationException = iOException;
    }

    OpenSSLSocketFactoryImpl(SSLParametersImpl sSLParametersImpl) {
        this.sslParameters = sSLParametersImpl;
        this.instantiationException = null;
    }

    private boolean hasFileDescriptor(Socket socket) {
        try {
            Platform.getFileDescriptor(socket);
            return true;
        }
        catch (RuntimeException runtimeException) {
            return false;
        }
    }

    static void setUseEngineSocketByDefault(boolean bl) {
        useEngineSocketByDefault = bl;
    }

    @Override
    public Socket createSocket() throws IOException {
        IOException iOException = this.instantiationException;
        if (iOException == null) {
            if (this.useEngineSocket) {
                return Platform.createEngineSocket((SSLParametersImpl)this.sslParameters.clone());
            }
            return Platform.createFileDescriptorSocket((SSLParametersImpl)this.sslParameters.clone());
        }
        throw iOException;
    }

    @Override
    public Socket createSocket(String string, int n) throws IOException, UnknownHostException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(string, n, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(string, n, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(String string, int n, InetAddress inetAddress, int n2) throws IOException, UnknownHostException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(string, n, inetAddress, n2, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(string, n, inetAddress, n2, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n) throws IOException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(inetAddress, n, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(inetAddress, n, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        if (this.useEngineSocket) {
            return Platform.createEngineSocket(inetAddress, n, inetAddress2, n2, (SSLParametersImpl)this.sslParameters.clone());
        }
        return Platform.createFileDescriptorSocket(inetAddress, n, inetAddress2, n2, (SSLParametersImpl)this.sslParameters.clone());
    }

    @Override
    public Socket createSocket(Socket socket, String string, int n, boolean bl) throws IOException {
        Preconditions.checkNotNull(socket, "socket");
        if (socket.isConnected()) {
            if (!this.useEngineSocket && this.hasFileDescriptor(socket)) {
                return Platform.createFileDescriptorSocket(socket, string, n, bl, (SSLParametersImpl)this.sslParameters.clone());
            }
            return Platform.createEngineSocket(socket, string, n, bl, (SSLParametersImpl)this.sslParameters.clone());
        }
        throw new SocketException("Socket is not connected.");
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

