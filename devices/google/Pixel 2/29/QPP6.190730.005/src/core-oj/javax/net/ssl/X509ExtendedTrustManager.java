/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509TrustManager;

public abstract class X509ExtendedTrustManager
implements X509TrustManager {
    public abstract void checkClientTrusted(X509Certificate[] var1, String var2, Socket var3) throws CertificateException;

    public abstract void checkClientTrusted(X509Certificate[] var1, String var2, SSLEngine var3) throws CertificateException;

    public abstract void checkServerTrusted(X509Certificate[] var1, String var2, Socket var3) throws CertificateException;

    public abstract void checkServerTrusted(X509Certificate[] var1, String var2, SSLEngine var3) throws CertificateException;
}

