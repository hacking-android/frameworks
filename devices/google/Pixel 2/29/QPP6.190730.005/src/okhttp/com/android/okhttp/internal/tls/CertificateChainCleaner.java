/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.tls;

import com.android.okhttp.internal.tls.TrustRootIndex;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

public final class CertificateChainCleaner {
    private static final int MAX_SIGNERS = 9;
    private final TrustRootIndex trustRootIndex;

    public CertificateChainCleaner(TrustRootIndex trustRootIndex) {
        this.trustRootIndex = trustRootIndex;
    }

    private boolean verifySignature(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        if (!x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
            return false;
        }
        try {
            x509Certificate.verify(x509Certificate2.getPublicKey());
            return true;
        }
        catch (GeneralSecurityException generalSecurityException) {
            return false;
        }
    }

    public List<Certificate> clean(List<Certificate> object) throws SSLPeerUnverifiedException {
        Serializable serializable;
        ArrayDeque<Certificate> arrayDeque = new ArrayDeque<Certificate>((Collection<Certificate>)object);
        object = new ArrayList<Certificate>();
        object.add(arrayDeque.removeFirst());
        boolean bl = false;
        block0 : for (int i = 0; i < 9; ++i) {
            serializable = (X509Certificate)object.get(object.size() - 1);
            Object object2 = this.trustRootIndex.findByIssuerAndSignature((X509Certificate)serializable);
            if (object2 != null) {
                if (object.size() > 1 || !((Certificate)serializable).equals(object2)) {
                    object.add((Certificate)object2);
                }
                if (this.verifySignature((X509Certificate)object2, (X509Certificate)object2)) {
                    return object;
                }
                bl = true;
                continue;
            }
            object2 = arrayDeque.iterator();
            while (object2.hasNext()) {
                X509Certificate x509Certificate = (X509Certificate)object2.next();
                if (!this.verifySignature((X509Certificate)serializable, x509Certificate)) continue;
                object2.remove();
                object.add(x509Certificate);
                continue block0;
            }
            if (bl) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to find a trusted cert that signed ");
            ((StringBuilder)object).append(serializable);
            throw new SSLPeerUnverifiedException(((StringBuilder)object).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Certificate chain too long: ");
        ((StringBuilder)serializable).append(object);
        throw new SSLPeerUnverifiedException(((StringBuilder)serializable).toString());
    }
}

