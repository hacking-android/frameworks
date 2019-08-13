/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.jcajce.PKIXCRLStoreSelector;
import com.android.org.bouncycastle.jce.provider.AnnotatedException;
import java.security.cert.CRL;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class PKIXCRLUtil {
    PKIXCRLUtil() {
    }

    private final Collection findCRLs(PKIXCRLStoreSelector pKIXCRLStoreSelector, List object) throws AnnotatedException {
        HashSet<CRL> hashSet = new HashSet<CRL>();
        Iterator iterator = object.iterator();
        object = null;
        boolean bl = false;
        while (iterator.hasNext()) {
            CertStore certStore = (CertStore)iterator.next();
            try {
                hashSet.addAll(PKIXCRLStoreSelector.getCRLs(pKIXCRLStoreSelector, certStore));
                bl = true;
            }
            catch (CertStoreException certStoreException) {
                object = new AnnotatedException("Exception searching in X.509 CRL store.", certStoreException);
            }
        }
        if (!bl && object != null) {
            throw object;
        }
        return hashSet;
    }

    public Set findCRLs(PKIXCRLStoreSelector pKIXCRLStoreSelector, Date date, List collection, List object) throws AnnotatedException {
        Object object2 = new HashSet();
        try {
            object2.addAll(this.findCRLs(pKIXCRLStoreSelector, (List)object));
            object2.addAll(this.findCRLs(pKIXCRLStoreSelector, (List)collection));
            collection = new HashSet();
            object2 = object2.iterator();
        }
        catch (AnnotatedException annotatedException) {
            throw new AnnotatedException("Exception obtaining complete CRLs.", annotatedException);
        }
        while (object2.hasNext()) {
            X509CRL x509CRL = (X509CRL)object2.next();
            if (!x509CRL.getNextUpdate().after(date)) continue;
            object = pKIXCRLStoreSelector.getCertificateChecking();
            if (object != null) {
                if (!x509CRL.getThisUpdate().before(((X509Certificate)object).getNotAfter())) continue;
                collection.add(x509CRL);
                continue;
            }
            collection.add(x509CRL);
        }
        return collection;
    }
}

