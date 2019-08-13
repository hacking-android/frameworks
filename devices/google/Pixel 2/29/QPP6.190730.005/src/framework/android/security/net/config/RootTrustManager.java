/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.annotation.UnsupportedAppUsage;
import android.security.net.config.ApplicationConfig;
import android.security.net.config.NetworkSecurityConfig;
import android.security.net.config.NetworkSecurityTrustManager;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;

public class RootTrustManager
extends X509ExtendedTrustManager {
    private final ApplicationConfig mConfig;

    public RootTrustManager(ApplicationConfig applicationConfig) {
        if (applicationConfig != null) {
            this.mConfig = applicationConfig;
            return;
        }
        throw new NullPointerException("config must not be null");
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        this.mConfig.getConfigForHostname("").getTrustManager().checkClientTrusted(arrx509Certificate, string2);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2, Socket socket) throws CertificateException {
        this.mConfig.getConfigForHostname("").getTrustManager().checkClientTrusted(arrx509Certificate, string2, socket);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arrx509Certificate, String string2, SSLEngine sSLEngine) throws CertificateException {
        this.mConfig.getConfigForHostname("").getTrustManager().checkClientTrusted(arrx509Certificate, string2, sSLEngine);
    }

    @UnsupportedAppUsage
    public List<X509Certificate> checkServerTrusted(X509Certificate[] arrx509Certificate, String string2, String string3) throws CertificateException {
        if (string3 == null && this.mConfig.hasPerDomainConfigs()) {
            throw new CertificateException("Domain specific configurations require that the hostname be provided");
        }
        return this.mConfig.getConfigForHostname(string3).getTrustManager().checkServerTrusted(arrx509Certificate, string2, string3);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2) throws CertificateException {
        if (!this.mConfig.hasPerDomainConfigs()) {
            this.mConfig.getConfigForHostname("").getTrustManager().checkServerTrusted(arrx509Certificate, string2);
            return;
        }
        throw new CertificateException("Domain specific configurations require that hostname aware checkServerTrusted(X509Certificate[], String, String) is used");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2, Socket socket) throws CertificateException {
        if (socket instanceof SSLSocket) {
            Object object = ((SSLSocket)socket).getHandshakeSession();
            if (object == null) throw new CertificateException("Not in handshake; no session available");
            object = object.getPeerHost();
            this.mConfig.getConfigForHostname((String)object).getTrustManager().checkServerTrusted(arrx509Certificate, string2, socket);
            return;
        } else {
            this.checkServerTrusted(arrx509Certificate, string2);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arrx509Certificate, String string2, SSLEngine sSLEngine) throws CertificateException {
        Object object = sSLEngine.getHandshakeSession();
        if (object != null) {
            object = object.getPeerHost();
            this.mConfig.getConfigForHostname((String)object).getTrustManager().checkServerTrusted(arrx509Certificate, string2, sSLEngine);
            return;
        }
        throw new CertificateException("Not in handshake; no session available");
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.mConfig.getConfigForHostname("").getTrustManager().getAcceptedIssuers();
    }

    public boolean isSameTrustConfiguration(String string2, String string3) {
        return this.mConfig.getConfigForHostname(string2).equals(this.mConfig.getConfigForHostname(string3));
    }
}

