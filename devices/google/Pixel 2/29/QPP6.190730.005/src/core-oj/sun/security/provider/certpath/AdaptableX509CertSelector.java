/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.SerialNumber;

class AdaptableX509CertSelector
extends X509CertSelector {
    private static final Debug debug = Debug.getInstance("certpath");
    private Date endDate;
    private BigInteger serial;
    private byte[] ski;
    private Date startDate;

    AdaptableX509CertSelector() {
    }

    private boolean matchSubjectKeyID(X509Certificate arrby) {
        Object object;
        block11 : {
            block10 : {
                if (this.ski == null) {
                    return true;
                }
                try {
                    object = arrby.getExtensionValue("2.5.29.14");
                    if (object != null) break block10;
                }
                catch (IOException iOException) {
                    Debug debug = AdaptableX509CertSelector.debug;
                    if (debug != null) {
                        debug.println("AdaptableX509CertSelector.match: exception in subject key ID check");
                    }
                    return false;
                }
                if (debug != null) {
                    Debug debug = AdaptableX509CertSelector.debug;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("AdaptableX509CertSelector.match: no subject key ID extension. Subject: ");
                    ((StringBuilder)object).append(arrby.getSubjectX500Principal());
                    debug.println(((StringBuilder)object).toString());
                }
                return true;
            }
            arrby = new DerInputStream((byte[])object);
            arrby = arrby.getOctetString();
            if (arrby == null) break block11;
            if (!Arrays.equals(this.ski, arrby)) break block11;
            return true;
        }
        if (debug != null) {
            object = debug;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AdaptableX509CertSelector.match: subject key IDs don't match. Expected: ");
            stringBuilder.append(Arrays.toString(this.ski));
            stringBuilder.append(" Cert's: ");
            stringBuilder.append(Arrays.toString(arrby));
            ((Debug)object).println(stringBuilder.toString());
        }
        return false;
    }

    @Override
    public Object clone() {
        AdaptableX509CertSelector adaptableX509CertSelector = (AdaptableX509CertSelector)super.clone();
        byte[] arrby = this.startDate;
        if (arrby != null) {
            adaptableX509CertSelector.startDate = (Date)arrby.clone();
        }
        if ((arrby = this.endDate) != null) {
            adaptableX509CertSelector.endDate = (Date)arrby.clone();
        }
        if ((arrby = this.ski) != null) {
            adaptableX509CertSelector.ski = (byte[])arrby.clone();
        }
        return adaptableX509CertSelector;
    }

    @Override
    public boolean match(Certificate certificate) {
        X509Certificate x509Certificate = (X509Certificate)certificate;
        if (!this.matchSubjectKeyID(x509Certificate)) {
            return false;
        }
        int n = x509Certificate.getVersion();
        Comparable<BigInteger> comparable = this.serial;
        if (comparable != null && n > 2 && !comparable.equals(x509Certificate.getSerialNumber())) {
            return false;
        }
        if (n < 3) {
            comparable = this.startDate;
            if (comparable != null) {
                try {
                    x509Certificate.checkValidity((Date)comparable);
                }
                catch (CertificateException certificateException) {
                    return false;
                }
            }
            if ((comparable = this.endDate) != null) {
                try {
                    x509Certificate.checkValidity((Date)comparable);
                }
                catch (CertificateException certificateException) {
                    return false;
                }
            }
        }
        return super.match(certificate);
    }

    @Override
    public void setSerialNumber(BigInteger bigInteger) {
        throw new IllegalArgumentException();
    }

    void setSkiAndSerialNumber(AuthorityKeyIdentifierExtension object) throws IOException {
        this.ski = null;
        this.serial = null;
        if (object != null) {
            this.ski = ((AuthorityKeyIdentifierExtension)object).getEncodedKeyIdentifier();
            if ((object = (SerialNumber)((AuthorityKeyIdentifierExtension)object).get("serial_number")) != null) {
                this.serial = ((SerialNumber)object).getNumber();
            }
        }
    }

    @Override
    public void setSubjectKeyIdentifier(byte[] arrby) {
        throw new IllegalArgumentException();
    }

    void setValidityPeriod(Date date, Date date2) {
        this.startDate = date;
        this.endDate = date2;
    }
}

