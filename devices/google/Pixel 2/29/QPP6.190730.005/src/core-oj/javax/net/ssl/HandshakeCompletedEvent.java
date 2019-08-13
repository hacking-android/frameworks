/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.EventObject;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.security.auth.x500.X500Principal;

public class HandshakeCompletedEvent
extends EventObject {
    private static final long serialVersionUID = 7914963744257769778L;
    private transient SSLSession session;

    public HandshakeCompletedEvent(SSLSocket sSLSocket, SSLSession sSLSession) {
        super(sSLSocket);
        this.session = sSLSession;
    }

    public String getCipherSuite() {
        return this.session.getCipherSuite();
    }

    public Certificate[] getLocalCertificates() {
        return this.session.getLocalCertificates();
    }

    public Principal getLocalPrincipal() {
        Principal principal;
        block2 : {
            try {
                principal = this.session.getLocalPrincipal();
            }
            catch (AbstractMethodError abstractMethodError) {
                principal = null;
                Certificate[] arrcertificate = this.getLocalCertificates();
                if (arrcertificate == null) break block2;
                principal = ((X509Certificate)arrcertificate[0]).getSubjectX500Principal();
            }
        }
        return principal;
    }

    public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return this.session.getPeerCertificateChain();
    }

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return this.session.getPeerCertificates();
    }

    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        Principal principal;
        try {
            principal = this.session.getPeerPrincipal();
        }
        catch (AbstractMethodError abstractMethodError) {
            principal = ((X509Certificate)this.getPeerCertificates()[0]).getSubjectX500Principal();
        }
        return principal;
    }

    public SSLSession getSession() {
        return this.session;
    }

    public SSLSocket getSocket() {
        return (SSLSocket)this.getSource();
    }
}

