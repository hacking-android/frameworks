/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.ConscryptSession;
import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.NativeSsl;
import com.android.org.conscrypt.Preconditions;
import com.android.org.conscrypt.SSLUtils;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import javax.security.auth.x500.X500Principal;

final class ActiveSession
implements ConscryptSession {
    private long creationTime;
    private byte[] id;
    private long lastAccessedTime = 0L;
    private X509Certificate[] localCertificates;
    private volatile javax.security.cert.X509Certificate[] peerCertificateChain;
    private byte[] peerCertificateOcspData;
    private X509Certificate[] peerCertificates;
    private String peerHost;
    private int peerPort = -1;
    private byte[] peerTlsSctData;
    private String protocol;
    private AbstractSessionContext sessionContext;
    private final NativeSsl ssl;

    ActiveSession(NativeSsl nativeSsl, AbstractSessionContext abstractSessionContext) {
        this.ssl = Preconditions.checkNotNull(nativeSsl, "ssl");
        this.sessionContext = Preconditions.checkNotNull(abstractSessionContext, "sessionContext");
    }

    private void checkPeerCertificatesPresent() throws SSLPeerUnverifiedException {
        X509Certificate[] arrx509Certificate = this.peerCertificates;
        if (arrx509Certificate != null && arrx509Certificate.length != 0) {
            return;
        }
        throw new SSLPeerUnverifiedException("No peer certificates");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void configurePeer(String object, int n, X509Certificate[] arrx509Certificate) {
        this.peerHost = object;
        this.peerPort = n;
        this.peerCertificates = arrx509Certificate;
        object = this.ssl;
        synchronized (object) {
            this.peerCertificateOcspData = this.ssl.getPeerCertificateOcspData();
            this.peerTlsSctData = this.ssl.getPeerTlsSctData();
            return;
        }
    }

    @Override
    public int getApplicationBufferSize() {
        return 16384;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getCipherSuite() {
        String string;
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            string = this.ssl.getCipherSuite();
        }
        if (string != null) return string;
        return "SSL_NULL_WITH_NULL_NULL";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long getCreationTime() {
        if (this.creationTime != 0L) return this.creationTime;
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.creationTime = this.ssl.getTime();
            return this.creationTime;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getId() {
        byte[] arrby;
        if (this.id == null) {
            arrby = this.ssl;
            synchronized (arrby) {
                this.id = this.ssl.getSessionId();
            }
        }
        if ((arrby = this.id) == null) return EmptyArray.BYTE;
        return (byte[])arrby.clone();
    }

    @Override
    public long getLastAccessedTime() {
        long l;
        long l2 = l = this.lastAccessedTime;
        if (l == 0L) {
            l2 = this.getCreationTime();
        }
        return l2;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        Object object = this.localCertificates;
        object = object == null ? null : (Certificate[])object.clone();
        return object;
    }

    @Override
    public Principal getLocalPrincipal() {
        X509Certificate[] arrx509Certificate = this.localCertificates;
        if (arrx509Certificate != null && arrx509Certificate.length > 0) {
            return arrx509Certificate[0].getSubjectX500Principal();
        }
        return null;
    }

    @Override
    public int getPacketBufferSize() {
        return 16709;
    }

    @Override
    public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        javax.security.cert.X509Certificate[] arrx509Certificate;
        this.checkPeerCertificatesPresent();
        javax.security.cert.X509Certificate[] arrx509Certificate2 = arrx509Certificate = this.peerCertificateChain;
        if (arrx509Certificate == null) {
            arrx509Certificate2 = arrx509Certificate = SSLUtils.toCertificateChain(this.peerCertificates);
            this.peerCertificateChain = arrx509Certificate;
        }
        return arrx509Certificate2;
    }

    @Override
    public X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        this.checkPeerCertificatesPresent();
        return (X509Certificate[])this.peerCertificates.clone();
    }

    @Override
    public String getPeerHost() {
        return this.peerHost;
    }

    @Override
    public int getPeerPort() {
        return this.peerPort;
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        this.checkPeerCertificatesPresent();
        return this.peerCertificates[0].getSubjectX500Principal();
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        byte[] arrby = this.peerTlsSctData;
        if (arrby == null) {
            return null;
        }
        return (byte[])arrby.clone();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getProtocol() {
        Object object = this.protocol;
        String string = object;
        if (object != null) return string;
        object = this.ssl;
        synchronized (object) {
            string = this.ssl.getVersion();
        }
        this.protocol = string;
        return string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getRequestedServerName() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.ssl.getRequestedServerName();
        }
    }

    @Override
    public SSLSessionContext getSessionContext() {
        AbstractSessionContext abstractSessionContext = this.isValid() ? this.sessionContext : null;
        return abstractSessionContext;
    }

    @Override
    public List<byte[]> getStatusResponses() {
        byte[] arrby = this.peerCertificateOcspData;
        if (arrby == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList((byte[])arrby.clone());
    }

    @Override
    public Object getValue(String string) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ProvidedSessionDecorator.");
    }

    @Override
    public String[] getValueNames() {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ProvidedSessionDecorator.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void invalidate() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.ssl.setTimeout(0L);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isValid() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            long l = this.ssl.getTime();
            long l2 = this.ssl.getTimeout();
            if (System.currentTimeMillis() - l2 >= l) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onPeerCertificateAvailable(String string, int n) throws CertificateException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.id = null;
            this.localCertificates = this.ssl.getLocalCertificates();
            if (this.peerCertificates == null) {
                this.configurePeer(string, n, this.ssl.getPeerCertificates());
            }
            return;
        }
    }

    void onPeerCertificatesReceived(String string, int n, X509Certificate[] arrx509Certificate) {
        this.configurePeer(string, n, arrx509Certificate);
    }

    @Override
    public void putValue(String string, Object object) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ProvidedSessionDecorator.");
    }

    @Override
    public void removeValue(String string) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ProvidedSessionDecorator.");
    }

    void setLastAccessedTime(long l) {
        this.lastAccessedTime = l;
    }
}

