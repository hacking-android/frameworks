/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Extension;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.X509CertImpl;

public abstract class X509Certificate
extends Certificate
implements X509Extension {
    private static final long serialVersionUID = -2491127588187038216L;
    private transient X500Principal issuerX500Principal;
    private transient X500Principal subjectX500Principal;

    protected X509Certificate() {
        super("X.509");
    }

    public abstract void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;

    public abstract void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException;

    public abstract int getBasicConstraints();

    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return X509CertImpl.getExtendedKeyUsage(this);
    }

    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return X509CertImpl.getIssuerAlternativeNames(this);
    }

    public abstract Principal getIssuerDN();

    public abstract boolean[] getIssuerUniqueID();

    public X500Principal getIssuerX500Principal() {
        if (this.issuerX500Principal == null) {
            this.issuerX500Principal = X509CertImpl.getIssuerX500Principal(this);
        }
        return this.issuerX500Principal;
    }

    public abstract boolean[] getKeyUsage();

    public abstract Date getNotAfter();

    public abstract Date getNotBefore();

    public abstract BigInteger getSerialNumber();

    public abstract String getSigAlgName();

    public abstract String getSigAlgOID();

    public abstract byte[] getSigAlgParams();

    public abstract byte[] getSignature();

    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return X509CertImpl.getSubjectAlternativeNames(this);
    }

    public abstract Principal getSubjectDN();

    public abstract boolean[] getSubjectUniqueID();

    public X500Principal getSubjectX500Principal() {
        if (this.subjectX500Principal == null) {
            this.subjectX500Principal = X509CertImpl.getSubjectX500Principal(this);
        }
        return this.subjectX500Principal;
    }

    public abstract byte[] getTBSCertificate() throws CertificateEncodingException;

    public abstract int getVersion();

    @Override
    public void verify(PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        super.verify(publicKey, provider);
    }
}

