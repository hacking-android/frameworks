/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Permission;
import java.security.Provider;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPermission;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import sun.security.jca.GetInstance;

public class SSLContext {
    private static SSLContext defaultContext;
    private final SSLContextSpi contextSpi;
    private final String protocol;
    private final Provider provider;

    protected SSLContext(SSLContextSpi sSLContextSpi, Provider provider, String string) {
        this.contextSpi = sSLContextSpi;
        this.provider = provider;
        this.protocol = string;
    }

    public static SSLContext getDefault() throws NoSuchAlgorithmException {
        synchronized (SSLContext.class) {
            if (defaultContext == null) {
                defaultContext = SSLContext.getInstance("Default");
            }
            SSLContext sSLContext = defaultContext;
            return sSLContext;
        }
    }

    public static SSLContext getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = GetInstance.getInstance("SSLContext", SSLContextSpi.class, string);
        return new SSLContext((SSLContextSpi)instance.impl, instance.provider, string);
    }

    public static SSLContext getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = GetInstance.getInstance("SSLContext", SSLContextSpi.class, string, (String)object);
        return new SSLContext((SSLContextSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static SSLContext getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = GetInstance.getInstance("SSLContext", SSLContextSpi.class, string, (Provider)object);
        return new SSLContext((SSLContextSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefault(SSLContext object) {
        synchronized (SSLContext.class) {
            if (object != null) {
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    SSLPermission sSLPermission = new SSLPermission("setDefaultSSLContext");
                    securityManager.checkPermission(sSLPermission);
                }
                defaultContext = object;
                return;
            }
            try {
                object = new NullPointerException();
                throw object;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final SSLEngine createSSLEngine() {
        try {
            SSLEngine sSLEngine = this.contextSpi.engineCreateSSLEngine();
            return sSLEngine;
        }
        catch (AbstractMethodError abstractMethodError) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Provider: ");
            ((StringBuilder)serializable).append(this.getProvider());
            ((StringBuilder)serializable).append(" doesn't support this operation");
            serializable = new UnsupportedOperationException(((StringBuilder)serializable).toString());
            ((Throwable)serializable).initCause(abstractMethodError);
            throw serializable;
        }
    }

    public final SSLEngine createSSLEngine(String object, int n) {
        try {
            object = this.contextSpi.engineCreateSSLEngine((String)object, n);
            return object;
        }
        catch (AbstractMethodError abstractMethodError) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Provider: ");
            ((StringBuilder)serializable).append(this.getProvider());
            ((StringBuilder)serializable).append(" does not support this operation");
            serializable = new UnsupportedOperationException(((StringBuilder)serializable).toString());
            ((Throwable)serializable).initCause(abstractMethodError);
            throw serializable;
        }
    }

    public final SSLSessionContext getClientSessionContext() {
        return this.contextSpi.engineGetClientSessionContext();
    }

    public final SSLParameters getDefaultSSLParameters() {
        return this.contextSpi.engineGetDefaultSSLParameters();
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final SSLSessionContext getServerSessionContext() {
        return this.contextSpi.engineGetServerSessionContext();
    }

    public final SSLServerSocketFactory getServerSocketFactory() {
        return this.contextSpi.engineGetServerSocketFactory();
    }

    public final SSLSocketFactory getSocketFactory() {
        return this.contextSpi.engineGetSocketFactory();
    }

    public final SSLParameters getSupportedSSLParameters() {
        return this.contextSpi.engineGetSupportedSSLParameters();
    }

    public final void init(KeyManager[] arrkeyManager, TrustManager[] arrtrustManager, SecureRandom secureRandom) throws KeyManagementException {
        this.contextSpi.engineInit(arrkeyManager, arrtrustManager, secureRandom);
    }
}

