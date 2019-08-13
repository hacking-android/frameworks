/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.internal.Util;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

public final class Handshake {
    private final String cipherSuite;
    private final List<Certificate> localCertificates;
    private final List<Certificate> peerCertificates;

    private Handshake(String string, List<Certificate> list, List<Certificate> list2) {
        this.cipherSuite = string;
        this.peerCertificates = list;
        this.localCertificates = list2;
    }

    public static Handshake get(String string, List<Certificate> list, List<Certificate> list2) {
        if (string != null) {
            return new Handshake(string, Util.immutableList(list), Util.immutableList(list2));
        }
        throw new IllegalArgumentException("cipherSuite == null");
    }

    public static Handshake get(SSLSession object) {
        String string = object.getCipherSuite();
        if (string != null) {
            Object object2;
            try {
                object2 = object.getPeerCertificates();
            }
            catch (SSLPeerUnverifiedException sSLPeerUnverifiedException) {
                object2 = null;
            }
            object2 = object2 != null ? Util.immutableList(object2) : Collections.emptyList();
            object = object.getLocalCertificates();
            object = object != null ? Util.immutableList(object) : Collections.emptyList();
            return new Handshake(string, (List<Certificate>)object2, (List<Certificate>)object);
        }
        throw new IllegalStateException("cipherSuite == null");
    }

    public String cipherSuite() {
        return this.cipherSuite;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Handshake;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (Handshake)object;
            if (!this.cipherSuite.equals(((Handshake)object).cipherSuite) || !this.peerCertificates.equals(((Handshake)object).peerCertificates) || !this.localCertificates.equals(((Handshake)object).localCertificates)) break block1;
            bl = true;
        }
        return bl;
    }

    public int hashCode() {
        return ((17 * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates.hashCode()) * 31 + this.localCertificates.hashCode();
    }

    public List<Certificate> localCertificates() {
        return this.localCertificates;
    }

    public Principal localPrincipal() {
        X500Principal x500Principal = !this.localCertificates.isEmpty() ? ((X509Certificate)this.localCertificates.get(0)).getSubjectX500Principal() : null;
        return x500Principal;
    }

    public List<Certificate> peerCertificates() {
        return this.peerCertificates;
    }

    public Principal peerPrincipal() {
        X500Principal x500Principal = !this.peerCertificates.isEmpty() ? ((X509Certificate)this.peerCertificates.get(0)).getSubjectX500Principal() : null;
        return x500Principal;
    }
}

