/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ConscryptSession;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;

final class ExternalSession
implements ConscryptSession {
    private final Provider provider;
    private final HashMap<String, Object> values = new HashMap(2);

    public ExternalSession(Provider provider) {
        this.provider = provider;
    }

    @Override
    public int getApplicationBufferSize() {
        return this.provider.provideSession().getApplicationBufferSize();
    }

    @Override
    public String getCipherSuite() {
        return this.provider.provideSession().getCipherSuite();
    }

    @Override
    public long getCreationTime() {
        return this.provider.provideSession().getCreationTime();
    }

    @Override
    public byte[] getId() {
        return this.provider.provideSession().getId();
    }

    @Override
    public long getLastAccessedTime() {
        return this.provider.provideSession().getLastAccessedTime();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return this.provider.provideSession().getLocalCertificates();
    }

    @Override
    public Principal getLocalPrincipal() {
        return this.provider.provideSession().getLocalPrincipal();
    }

    @Override
    public int getPacketBufferSize() {
        return this.provider.provideSession().getPacketBufferSize();
    }

    @Override
    public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return this.provider.provideSession().getPeerCertificateChain();
    }

    @Override
    public X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return this.provider.provideSession().getPeerCertificates();
    }

    @Override
    public String getPeerHost() {
        return this.provider.provideSession().getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.provider.provideSession().getPeerPort();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return this.provider.provideSession().getPeerPrincipal();
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        return this.provider.provideSession().getPeerSignedCertificateTimestamp();
    }

    @Override
    public String getProtocol() {
        return this.provider.provideSession().getProtocol();
    }

    @Override
    public String getRequestedServerName() {
        return this.provider.provideSession().getRequestedServerName();
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.provider.provideSession().getSessionContext();
    }

    @Override
    public List<byte[]> getStatusResponses() {
        return this.provider.provideSession().getStatusResponses();
    }

    @Override
    public Object getValue(String string) {
        if (string != null) {
            return this.values.get(string);
        }
        throw new IllegalArgumentException("name == null");
    }

    @Override
    public String[] getValueNames() {
        return this.values.keySet().toArray(new String[this.values.size()]);
    }

    @Override
    public void invalidate() {
        this.provider.provideSession().invalidate();
    }

    @Override
    public boolean isValid() {
        return this.provider.provideSession().isValid();
    }

    @Override
    public void putValue(String string, Object object) {
        this.putValue(this, string, object);
    }

    void putValue(SSLSession sSLSession, String string, Object object) {
        if (string != null && object != null) {
            Object object2 = this.values.put(string, object);
            if (object instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)object).valueBound(new SSLSessionBindingEvent(sSLSession, string));
            }
            if (object2 instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)object2).valueUnbound(new SSLSessionBindingEvent(sSLSession, string));
            }
            return;
        }
        throw new IllegalArgumentException("name == null || value == null");
    }

    @Override
    public void removeValue(String string) {
        this.removeValue(this, string);
    }

    void removeValue(SSLSession sSLSession, String string) {
        if (string != null) {
            Object object = this.values.remove(string);
            if (object instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)object).valueUnbound(new SSLSessionBindingEvent(sSLSession, string));
            }
            return;
        }
        throw new IllegalArgumentException("name == null");
    }

    static interface Provider {
        public ConscryptSession provideSession();
    }

}

