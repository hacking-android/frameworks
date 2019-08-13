/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CertStoreCollectionSpi
extends CertStoreSpi {
    private CollectionCertStoreParameters params;

    public CertStoreCollectionSpi(CertStoreParameters certStoreParameters) throws InvalidAlgorithmParameterException {
        super(certStoreParameters);
        if (certStoreParameters instanceof CollectionCertStoreParameters) {
            this.params = (CollectionCertStoreParameters)certStoreParameters;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("com.android.org.bouncycastle.jce.provider.CertStoreCollectionSpi: parameter must be a CollectionCertStoreParameters object\n");
        stringBuilder.append(certStoreParameters.toString());
        throw new InvalidAlgorithmParameterException(stringBuilder.toString());
    }

    public Collection engineGetCRLs(CRLSelector cRLSelector) throws CertStoreException {
        ArrayList<CRLSelector> arrayList = new ArrayList<CRLSelector>();
        Iterator<?> iterator = this.params.getCollection().iterator();
        if (cRLSelector == null) {
            while (iterator.hasNext()) {
                cRLSelector = iterator.next();
                if (!(cRLSelector instanceof CRL)) continue;
                arrayList.add(cRLSelector);
            }
        } else {
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                if (!(obj instanceof CRL) || !cRLSelector.match((CRL)obj)) continue;
                arrayList.add((CRLSelector)obj);
            }
        }
        return arrayList;
    }

    public Collection engineGetCertificates(CertSelector certSelector) throws CertStoreException {
        ArrayList<CertSelector> arrayList = new ArrayList<CertSelector>();
        Iterator<?> iterator = this.params.getCollection().iterator();
        if (certSelector == null) {
            while (iterator.hasNext()) {
                certSelector = iterator.next();
                if (!(certSelector instanceof Certificate)) continue;
                arrayList.add(certSelector);
            }
        } else {
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                if (!(obj instanceof Certificate) || !certSelector.match((Certificate)obj)) continue;
                arrayList.add((CertSelector)obj);
            }
        }
        return arrayList;
    }
}

