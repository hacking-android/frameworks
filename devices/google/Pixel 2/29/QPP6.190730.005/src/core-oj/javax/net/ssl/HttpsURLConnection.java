/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLPermission;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;

public abstract class HttpsURLConnection
extends HttpURLConnection {
    private static SSLSocketFactory defaultSSLSocketFactory = null;
    protected HostnameVerifier hostnameVerifier;
    private SSLSocketFactory sslSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();

    protected HttpsURLConnection(URL uRL) {
        super(uRL);
    }

    public static HostnameVerifier getDefaultHostnameVerifier() {
        return NoPreloadHolder.defaultHostnameVerifier;
    }

    public static SSLSocketFactory getDefaultSSLSocketFactory() {
        if (defaultSSLSocketFactory == null) {
            defaultSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        }
        return defaultSSLSocketFactory;
    }

    public static void setDefaultHostnameVerifier(HostnameVerifier hostnameVerifier) {
        if (hostnameVerifier != null) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new SSLPermission("setHostnameVerifier"));
            }
            NoPreloadHolder.defaultHostnameVerifier = hostnameVerifier;
            return;
        }
        throw new IllegalArgumentException("no default HostnameVerifier specified");
    }

    public static void setDefaultSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        if (sSLSocketFactory != null) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkSetFactory();
            }
            defaultSSLSocketFactory = sSLSocketFactory;
            return;
        }
        throw new IllegalArgumentException("no default SSLSocketFactory specified");
    }

    public abstract String getCipherSuite();

    public HostnameVerifier getHostnameVerifier() {
        if (this.hostnameVerifier == null) {
            this.hostnameVerifier = NoPreloadHolder.defaultHostnameVerifier;
        }
        return this.hostnameVerifier;
    }

    public abstract Certificate[] getLocalCertificates();

    public Principal getLocalPrincipal() {
        Certificate[] arrcertificate = this.getLocalCertificates();
        if (arrcertificate != null) {
            return ((X509Certificate)arrcertificate[0]).getSubjectX500Principal();
        }
        return null;
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return ((X509Certificate)this.getServerCertificates()[0]).getSubjectX500Principal();
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return this.sslSocketFactory;
    }

    public abstract Certificate[] getServerCertificates() throws SSLPeerUnverifiedException;

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        if (hostnameVerifier != null) {
            this.hostnameVerifier = hostnameVerifier;
            return;
        }
        throw new IllegalArgumentException("no HostnameVerifier specified");
    }

    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        if (sSLSocketFactory != null) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkSetFactory();
            }
            this.sslSocketFactory = sSLSocketFactory;
            return;
        }
        throw new IllegalArgumentException("no SSLSocketFactory specified");
    }

    private static class NoPreloadHolder {
        public static HostnameVerifier defaultHostnameVerifier;
        public static final Class<? extends HostnameVerifier> originalDefaultHostnameVerifierClass;

        static {
            try {
                defaultHostnameVerifier = (HostnameVerifier)Class.forName("com.android.okhttp.internal.tls.OkHostnameVerifier").getField("INSTANCE").get(null);
                originalDefaultHostnameVerifierClass = defaultHostnameVerifier.getClass();
                return;
            }
            catch (Exception exception) {
                throw new AssertionError("Failed to obtain okhttp HostnameVerifier", exception);
            }
        }

        private NoPreloadHolder() {
        }
    }

}

