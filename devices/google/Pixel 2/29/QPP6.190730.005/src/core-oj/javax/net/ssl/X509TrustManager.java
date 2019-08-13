/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;

public interface X509TrustManager
extends TrustManager {
    public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException;

    public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException;

    public X509Certificate[] getAcceptedIssuers();
}

