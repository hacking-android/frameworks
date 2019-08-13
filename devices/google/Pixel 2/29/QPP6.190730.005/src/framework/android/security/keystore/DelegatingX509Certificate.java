/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

class DelegatingX509Certificate
extends X509Certificate {
    private final X509Certificate mDelegate;

    DelegatingX509Certificate(X509Certificate x509Certificate) {
        this.mDelegate = x509Certificate;
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.mDelegate.checkValidity();
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        this.mDelegate.checkValidity(date);
    }

    @Override
    public int getBasicConstraints() {
        return this.mDelegate.getBasicConstraints();
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return this.mDelegate.getCriticalExtensionOIDs();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.mDelegate.getEncoded();
    }

    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return this.mDelegate.getExtendedKeyUsage();
    }

    @Override
    public byte[] getExtensionValue(String string2) {
        return this.mDelegate.getExtensionValue(string2);
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return this.mDelegate.getIssuerAlternativeNames();
    }

    @Override
    public Principal getIssuerDN() {
        return this.mDelegate.getIssuerDN();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return this.mDelegate.getIssuerUniqueID();
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        return this.mDelegate.getIssuerX500Principal();
    }

    @Override
    public boolean[] getKeyUsage() {
        return this.mDelegate.getKeyUsage();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return this.mDelegate.getNonCriticalExtensionOIDs();
    }

    @Override
    public Date getNotAfter() {
        return this.mDelegate.getNotAfter();
    }

    @Override
    public Date getNotBefore() {
        return this.mDelegate.getNotBefore();
    }

    @Override
    public PublicKey getPublicKey() {
        return this.mDelegate.getPublicKey();
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.mDelegate.getSerialNumber();
    }

    @Override
    public String getSigAlgName() {
        return this.mDelegate.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
        return this.mDelegate.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
        return this.mDelegate.getSigAlgParams();
    }

    @Override
    public byte[] getSignature() {
        return this.mDelegate.getSignature();
    }

    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return this.mDelegate.getSubjectAlternativeNames();
    }

    @Override
    public Principal getSubjectDN() {
        return this.mDelegate.getSubjectDN();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return this.mDelegate.getSubjectUniqueID();
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        return this.mDelegate.getSubjectX500Principal();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return this.mDelegate.getTBSCertificate();
    }

    @Override
    public int getVersion() {
        return this.mDelegate.getVersion();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return this.mDelegate.hasUnsupportedCriticalExtension();
    }

    @Override
    public String toString() {
        return this.mDelegate.toString();
    }

    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.mDelegate.verify(publicKey);
    }

    @Override
    public void verify(PublicKey publicKey, String string2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.mDelegate.verify(publicKey, string2);
    }
}

