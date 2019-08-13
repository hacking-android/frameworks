/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Selector;
import com.android.org.bouncycastle.x509.X509AttributeCertificate;
import com.android.org.bouncycastle.x509.extension.X509ExtensionUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

public class X509CRLStoreSelector
extends X509CRLSelector
implements Selector {
    private X509AttributeCertificate attrCertChecking;
    private boolean completeCRLEnabled = false;
    private boolean deltaCRLIndicator = false;
    private byte[] issuingDistributionPoint = null;
    private boolean issuingDistributionPointEnabled = false;
    private BigInteger maxBaseCRLNumber = null;

    public static X509CRLStoreSelector getInstance(X509CRLSelector x509CRLSelector) {
        if (x509CRLSelector != null) {
            X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
            x509CRLStoreSelector.setCertificateChecking(x509CRLSelector.getCertificateChecking());
            x509CRLStoreSelector.setDateAndTime(x509CRLSelector.getDateAndTime());
            try {
                x509CRLStoreSelector.setIssuerNames(x509CRLSelector.getIssuerNames());
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException(iOException.getMessage());
            }
            x509CRLStoreSelector.setIssuers(x509CRLSelector.getIssuers());
            x509CRLStoreSelector.setMaxCRLNumber(x509CRLSelector.getMaxCRL());
            x509CRLStoreSelector.setMinCRLNumber(x509CRLSelector.getMinCRL());
            return x509CRLStoreSelector;
        }
        throw new IllegalArgumentException("cannot create from null selector");
    }

    @Override
    public Object clone() {
        X509CRLStoreSelector x509CRLStoreSelector = X509CRLStoreSelector.getInstance(this);
        x509CRLStoreSelector.deltaCRLIndicator = this.deltaCRLIndicator;
        x509CRLStoreSelector.completeCRLEnabled = this.completeCRLEnabled;
        x509CRLStoreSelector.maxBaseCRLNumber = this.maxBaseCRLNumber;
        x509CRLStoreSelector.attrCertChecking = this.attrCertChecking;
        x509CRLStoreSelector.issuingDistributionPointEnabled = this.issuingDistributionPointEnabled;
        x509CRLStoreSelector.issuingDistributionPoint = Arrays.clone(this.issuingDistributionPoint);
        return x509CRLStoreSelector;
    }

    public X509AttributeCertificate getAttrCertificateChecking() {
        return this.attrCertChecking;
    }

    public byte[] getIssuingDistributionPoint() {
        return Arrays.clone(this.issuingDistributionPoint);
    }

    public BigInteger getMaxBaseCRLNumber() {
        return this.maxBaseCRLNumber;
    }

    public boolean isCompleteCRLEnabled() {
        return this.completeCRLEnabled;
    }

    public boolean isDeltaCRLIndicatorEnabled() {
        return this.deltaCRLIndicator;
    }

    public boolean isIssuingDistributionPointEnabled() {
        return this.issuingDistributionPointEnabled;
    }

    public boolean match(Object object) {
        byte[] arrby;
        byte[] arrby2;
        block9 : {
            byte[] arrby3;
            if (!(object instanceof X509CRL)) {
                return false;
            }
            arrby = (byte[])object;
            arrby2 = null;
            try {
                arrby3 = arrby.getExtensionValue(Extension.deltaCRLIndicator.getId());
                if (arrby3 == null) break block9;
            }
            catch (Exception exception) {
                return false;
            }
            arrby2 = ASN1Integer.getInstance(X509ExtensionUtil.fromExtensionValue(arrby3));
        }
        if (this.isDeltaCRLIndicatorEnabled() && arrby2 == null) {
            return false;
        }
        if (this.isCompleteCRLEnabled() && arrby2 != null) {
            return false;
        }
        if (arrby2 != null && this.maxBaseCRLNumber != null && arrby2.getPositiveValue().compareTo(this.maxBaseCRLNumber) == 1) {
            return false;
        }
        if (this.issuingDistributionPointEnabled) {
            arrby2 = arrby.getExtensionValue(Extension.issuingDistributionPoint.getId());
            arrby = this.issuingDistributionPoint;
            if (arrby == null ? arrby2 != null : !Arrays.areEqual(arrby2, arrby)) {
                return false;
            }
        }
        return super.match((X509CRL)object);
    }

    @Override
    public boolean match(CRL cRL) {
        return this.match((Object)cRL);
    }

    public void setAttrCertificateChecking(X509AttributeCertificate x509AttributeCertificate) {
        this.attrCertChecking = x509AttributeCertificate;
    }

    public void setCompleteCRLEnabled(boolean bl) {
        this.completeCRLEnabled = bl;
    }

    public void setDeltaCRLIndicatorEnabled(boolean bl) {
        this.deltaCRLIndicator = bl;
    }

    public void setIssuingDistributionPoint(byte[] arrby) {
        this.issuingDistributionPoint = Arrays.clone(arrby);
    }

    public void setIssuingDistributionPointEnabled(boolean bl) {
        this.issuingDistributionPointEnabled = bl;
    }

    public void setMaxBaseCRLNumber(BigInteger bigInteger) {
        this.maxBaseCRLNumber = bigInteger;
    }
}

