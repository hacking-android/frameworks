/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.ConscryptEngine;
import com.android.org.conscrypt.DefaultSSLContextImpl;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLServerSocketFactoryImpl;
import com.android.org.conscrypt.OpenSSLSocketFactoryImpl;
import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.SSLParametersImpl;
import com.android.org.conscrypt.ServerSessionContext;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public abstract class OpenSSLContextImpl
extends SSLContextSpi {
    private static DefaultSSLContextImpl defaultSslContextImpl;
    private final String[] algorithms;
    private final ClientSessionContext clientSessionContext;
    private final ServerSessionContext serverSessionContext;
    SSLParametersImpl sslParameters;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    OpenSSLContextImpl() throws GeneralSecurityException, IOException {
        synchronized (DefaultSSLContextImpl.class) {
            Object object;
            this.algorithms = null;
            if (defaultSslContextImpl == null) {
                object = new ClientSessionContext();
                this.clientSessionContext = object;
                this.serverSessionContext = object;
                defaultSslContextImpl = (DefaultSSLContextImpl)this;
            } else {
                this.clientSessionContext = (ClientSessionContext)defaultSslContextImpl.engineGetClientSessionContext();
                this.serverSessionContext = (ServerSessionContext)defaultSslContextImpl.engineGetServerSessionContext();
            }
            this.sslParameters = object = new SSLParametersImpl(defaultSslContextImpl.getKeyManagers(), defaultSslContextImpl.getTrustManagers(), null, this.clientSessionContext, this.serverSessionContext, this.algorithms);
            return;
        }
    }

    OpenSSLContextImpl(String[] arrstring) {
        this.algorithms = arrstring;
        this.clientSessionContext = new ClientSessionContext();
        this.serverSessionContext = new ServerSessionContext();
    }

    @UnsupportedAppUsage
    static OpenSSLContextImpl getPreferred() {
        return new TLSv13();
    }

    @Override
    public SSLEngine engineCreateSSLEngine() {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            sSLParametersImpl = (SSLParametersImpl)sSLParametersImpl.clone();
            sSLParametersImpl.setUseClientMode(false);
            return Platform.wrapEngine(new ConscryptEngine(sSLParametersImpl));
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override
    public SSLEngine engineCreateSSLEngine(String string, int n) {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            sSLParametersImpl = (SSLParametersImpl)sSLParametersImpl.clone();
            sSLParametersImpl.setUseClientMode(false);
            return Platform.wrapEngine(new ConscryptEngine(string, n, sSLParametersImpl));
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override
    public SSLSessionContext engineGetClientSessionContext() {
        return this.clientSessionContext;
    }

    @Override
    public SSLSessionContext engineGetServerSessionContext() {
        return this.serverSessionContext;
    }

    @Override
    public SSLServerSocketFactory engineGetServerSocketFactory() {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            return new OpenSSLServerSocketFactoryImpl(sSLParametersImpl);
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override
    public SSLSocketFactory engineGetSocketFactory() {
        SSLParametersImpl sSLParametersImpl = this.sslParameters;
        if (sSLParametersImpl != null) {
            return Platform.wrapSocketFactoryIfNeeded(new OpenSSLSocketFactoryImpl(sSLParametersImpl));
        }
        throw new IllegalStateException("SSLContext is not initialized.");
    }

    @Override
    public void engineInit(KeyManager[] arrkeyManager, TrustManager[] arrtrustManager, SecureRandom secureRandom) throws KeyManagementException {
        this.sslParameters = new SSLParametersImpl(arrkeyManager, arrtrustManager, secureRandom, this.clientSessionContext, this.serverSessionContext, this.algorithms);
    }

    public static final class TLSv1
    extends OpenSSLContextImpl {
        public TLSv1() {
            super(NativeCrypto.TLSV1_PROTOCOLS);
        }
    }

    public static final class TLSv11
    extends OpenSSLContextImpl {
        public TLSv11() {
            super(NativeCrypto.TLSV11_PROTOCOLS);
        }
    }

    public static final class TLSv12
    extends OpenSSLContextImpl {
        @UnsupportedAppUsage
        public TLSv12() {
            super(NativeCrypto.TLSV12_PROTOCOLS);
        }
    }

    public static final class TLSv13
    extends OpenSSLContextImpl {
        public TLSv13() {
            super(NativeCrypto.TLSV13_PROTOCOLS);
        }
    }

}

