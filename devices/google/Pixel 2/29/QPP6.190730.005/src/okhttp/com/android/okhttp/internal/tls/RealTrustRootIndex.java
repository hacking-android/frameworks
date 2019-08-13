/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.tls;

import com.android.okhttp.internal.tls.TrustRootIndex;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

public final class RealTrustRootIndex
implements TrustRootIndex {
    private final Map<X500Principal, List<X509Certificate>> subjectToCaCerts = new LinkedHashMap<X500Principal, List<X509Certificate>>();

    public RealTrustRootIndex(X509Certificate ... arrx509Certificate) {
        for (X509Certificate x509Certificate : arrx509Certificate) {
            List<X509Certificate> list;
            X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
            List<X509Certificate> list2 = list = this.subjectToCaCerts.get(x500Principal);
            if (list == null) {
                list2 = new ArrayList<X509Certificate>(1);
                this.subjectToCaCerts.put(x500Principal, list2);
            }
            list2.add(x509Certificate);
        }
    }

    @Override
    public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
        Iterator iterator = x509Certificate.getIssuerX500Principal();
        if ((iterator = this.subjectToCaCerts.get(iterator)) == null) {
            return null;
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            X509Certificate x509Certificate2 = (X509Certificate)iterator.next();
            PublicKey publicKey = x509Certificate2.getPublicKey();
            try {
                x509Certificate.verify(publicKey);
                return x509Certificate2;
            }
            catch (Exception exception) {
            }
        }
        return null;
    }
}

