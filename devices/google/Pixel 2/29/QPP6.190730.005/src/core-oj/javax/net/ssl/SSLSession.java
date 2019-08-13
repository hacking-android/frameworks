/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

public interface SSLSession {
    public int getApplicationBufferSize();

    public String getCipherSuite();

    public long getCreationTime();

    public byte[] getId();

    public long getLastAccessedTime();

    public Certificate[] getLocalCertificates();

    public Principal getLocalPrincipal();

    public int getPacketBufferSize();

    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException;

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException;

    public String getPeerHost();

    public int getPeerPort();

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException;

    public String getProtocol();

    public SSLSessionContext getSessionContext();

    public Object getValue(String var1);

    public String[] getValueNames();

    public void invalidate();

    public boolean isValid();

    public void putValue(String var1, Object var2);

    public void removeValue(String var1);
}

