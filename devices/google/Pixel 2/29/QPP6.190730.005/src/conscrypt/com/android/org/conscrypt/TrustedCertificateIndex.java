/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.io.Serializable;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public final class TrustedCertificateIndex {
    private final Map<X500Principal, List<TrustAnchor>> subjectToTrustAnchors = new HashMap<X500Principal, List<TrustAnchor>>();

    public TrustedCertificateIndex() {
    }

    public TrustedCertificateIndex(Set<TrustAnchor> set) {
        this.index(set);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static TrustAnchor findBySubjectAndPublicKey(X509Certificate serializable, Collection<TrustAnchor> iterator) {
        PublicKey publicKey = ((Certificate)serializable).getPublicKey();
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            TrustAnchor trustAnchor = (TrustAnchor)iterator.next();
            try {
                boolean bl;
                serializable = trustAnchor.getTrustedCert();
                serializable = serializable != null ? ((Certificate)serializable).getPublicKey() : trustAnchor.getCAPublicKey();
                if (serializable.equals(publicKey)) {
                    return trustAnchor;
                }
                if (!"X.509".equals(serializable.getFormat()) || !"X.509".equals(publicKey.getFormat())) continue;
                byte[] arrby = serializable.getEncoded();
                serializable = publicKey.getEncoded();
                if (serializable == null || arrby == null || !(bl = Arrays.equals(arrby, (byte[])serializable))) continue;
                return trustAnchor;
            }
            catch (Exception exception) {
                continue;
            }
            break;
        }
        return null;
    }

    private void index(Set<TrustAnchor> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.index((TrustAnchor)object.next());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Set<TrustAnchor> findAllByIssuerAndSignature(X509Certificate object) {
        Object object2 = ((X509Certificate)object).getIssuerX500Principal();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            object2 = this.subjectToTrustAnchors.get(object2);
            if (object2 == null) {
                return Collections.emptySet();
            }
            HashSet<TrustAnchor> hashSet = new HashSet<TrustAnchor>();
            Iterator iterator = object2.iterator();
            while (iterator.hasNext()) {
                TrustAnchor trustAnchor = (TrustAnchor)iterator.next();
                try {
                    object2 = trustAnchor.getTrustedCert();
                    object2 = object2 != null ? ((Certificate)object2).getPublicKey() : trustAnchor.getCAPublicKey();
                    if (object2 == null) continue;
                    ((Certificate)object).verify((PublicKey)object2);
                    hashSet.add(trustAnchor);
                }
                catch (Exception exception) {
                }
            }
            return hashSet;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TrustAnchor findByIssuerAndSignature(X509Certificate x509Certificate) {
        Object object = x509Certificate.getIssuerX500Principal();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            object = this.subjectToTrustAnchors.get(object);
            if (object == null) {
                return null;
            }
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                TrustAnchor trustAnchor = (TrustAnchor)iterator.next();
                try {
                    object = trustAnchor.getTrustedCert();
                    object = object != null ? ((Certificate)object).getPublicKey() : trustAnchor.getCAPublicKey();
                    x509Certificate.verify((PublicKey)object);
                    return trustAnchor;
                }
                catch (Exception exception) {
                    continue;
                }
                break;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TrustAnchor findBySubjectAndPublicKey(X509Certificate object) {
        Object object2 = ((X509Certificate)object).getSubjectX500Principal();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            object2 = this.subjectToTrustAnchors.get(object2);
            if (object2 != null) return TrustedCertificateIndex.findBySubjectAndPublicKey((X509Certificate)object, (Collection<TrustAnchor>)object2);
            return null;
        }
    }

    public TrustAnchor index(X509Certificate object) {
        object = new TrustAnchor((X509Certificate)object, null);
        this.index((TrustAnchor)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void index(TrustAnchor trustAnchor) {
        X509Certificate x509Certificate = trustAnchor.getTrustedCert();
        Serializable serializable = x509Certificate != null ? x509Certificate.getSubjectX500Principal() : trustAnchor.getCA();
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            block6 : {
                ArrayList arrayList = this.subjectToTrustAnchors.get(serializable);
                if (arrayList == null) {
                    arrayList = new ArrayList(1);
                    this.subjectToTrustAnchors.put((X500Principal)serializable, arrayList);
                    serializable = arrayList;
                } else {
                    serializable = arrayList;
                    if (x509Certificate != null) {
                        Iterator iterator = arrayList.iterator();
                        do {
                            serializable = arrayList;
                            if (!iterator.hasNext()) break block6;
                        } while (!x509Certificate.equals(((TrustAnchor)iterator.next()).getTrustedCert()));
                        return;
                    }
                }
            }
            serializable.add(trustAnchor);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset() {
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            this.subjectToTrustAnchors.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reset(Set<TrustAnchor> set) {
        Map<X500Principal, List<TrustAnchor>> map = this.subjectToTrustAnchors;
        synchronized (map) {
            this.reset();
            this.index(set);
            return;
        }
    }
}

