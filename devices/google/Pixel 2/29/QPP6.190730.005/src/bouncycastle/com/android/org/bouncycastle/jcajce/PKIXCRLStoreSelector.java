/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce;

import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Selector;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

public class PKIXCRLStoreSelector<T extends CRL>
implements Selector<T> {
    private final CRLSelector baseSelector;
    private final boolean completeCRLEnabled;
    private final boolean deltaCRLIndicator;
    private final byte[] issuingDistributionPoint;
    private final boolean issuingDistributionPointEnabled;
    private final BigInteger maxBaseCRLNumber;

    private PKIXCRLStoreSelector(Builder builder) {
        this.baseSelector = builder.baseSelector;
        this.deltaCRLIndicator = builder.deltaCRLIndicator;
        this.completeCRLEnabled = builder.completeCRLEnabled;
        this.maxBaseCRLNumber = builder.maxBaseCRLNumber;
        this.issuingDistributionPoint = builder.issuingDistributionPoint;
        this.issuingDistributionPointEnabled = builder.issuingDistributionPointEnabled;
    }

    public static Collection<? extends CRL> getCRLs(PKIXCRLStoreSelector pKIXCRLStoreSelector, CertStore certStore) throws CertStoreException {
        return certStore.getCRLs(new SelectorClone(pKIXCRLStoreSelector));
    }

    @Override
    public Object clone() {
        return this;
    }

    public X509Certificate getCertificateChecking() {
        CRLSelector cRLSelector = this.baseSelector;
        if (cRLSelector instanceof X509CRLSelector) {
            return ((X509CRLSelector)cRLSelector).getCertificateChecking();
        }
        return null;
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

    @Override
    public boolean match(CRL cRL) {
        byte[] arrby;
        byte[] arrby2;
        block9 : {
            byte[] arrby3;
            if (!(cRL instanceof X509CRL)) {
                return this.baseSelector.match(cRL);
            }
            arrby = (byte[])cRL;
            arrby2 = null;
            try {
                arrby3 = arrby.getExtensionValue(Extension.deltaCRLIndicator.getId());
                if (arrby3 == null) break block9;
            }
            catch (Exception exception) {
                return false;
            }
            arrby2 = ASN1Integer.getInstance(ASN1OctetString.getInstance(arrby3).getOctets());
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
        return this.baseSelector.match(cRL);
    }

    public static class Builder {
        private final CRLSelector baseSelector;
        private boolean completeCRLEnabled = false;
        private boolean deltaCRLIndicator = false;
        private byte[] issuingDistributionPoint = null;
        private boolean issuingDistributionPointEnabled = false;
        private BigInteger maxBaseCRLNumber = null;

        public Builder(CRLSelector cRLSelector) {
            this.baseSelector = (CRLSelector)cRLSelector.clone();
        }

        public PKIXCRLStoreSelector<? extends CRL> build() {
            return new PKIXCRLStoreSelector(this);
        }

        public Builder setCompleteCRLEnabled(boolean bl) {
            this.completeCRLEnabled = bl;
            return this;
        }

        public Builder setDeltaCRLIndicatorEnabled(boolean bl) {
            this.deltaCRLIndicator = bl;
            return this;
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

    private static class SelectorClone
    extends X509CRLSelector {
        private final PKIXCRLStoreSelector selector;

        SelectorClone(PKIXCRLStoreSelector cloneable) {
            this.selector = cloneable;
            if (((PKIXCRLStoreSelector)cloneable).baseSelector instanceof X509CRLSelector) {
                cloneable = (X509CRLSelector)((PKIXCRLStoreSelector)cloneable).baseSelector;
                this.setCertificateChecking(((X509CRLSelector)cloneable).getCertificateChecking());
                this.setDateAndTime(((X509CRLSelector)cloneable).getDateAndTime());
                this.setIssuers(((X509CRLSelector)cloneable).getIssuers());
                this.setMinCRLNumber(((X509CRLSelector)cloneable).getMinCRL());
                this.setMaxCRLNumber(((X509CRLSelector)cloneable).getMaxCRL());
            }
        }

        @Override
        public boolean match(CRL cRL) {
            PKIXCRLStoreSelector pKIXCRLStoreSelector = this.selector;
            boolean bl = pKIXCRLStoreSelector == null ? cRL != null : pKIXCRLStoreSelector.match(cRL);
            return bl;
        }
    }

}

