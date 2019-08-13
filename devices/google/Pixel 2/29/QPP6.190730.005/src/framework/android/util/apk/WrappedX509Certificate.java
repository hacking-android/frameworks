/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

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
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

class WrappedX509Certificate
extends X509Certificate {
    private final X509Certificate mWrapped;

    WrappedX509Certificate(X509Certificate x509Certificate) {
        this.mWrapped = x509Certificate;
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.mWrapped.checkValidity();
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        this.mWrapped.checkValidity(date);
    }

    @Override
    public int getBasicConstraints() {
        return this.mWrapped.getBasicConstraints();
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return this.mWrapped.getCriticalExtensionOIDs();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.mWrapped.getEncoded();
    }

    @Override
    public byte[] getExtensionValue(String string2) {
        return this.mWrapped.getExtensionValue(string2);
    }

    @Override
    public Principal getIssuerDN() {
        return this.mWrapped.getIssuerDN();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return this.mWrapped.getIssuerUniqueID();
    }

    @Override
    public boolean[] getKeyUsage() {
        return this.mWrapped.getKeyUsage();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return this.mWrapped.getNonCriticalExtensionOIDs();
    }

    @Override
    public Date getNotAfter() {
        return this.mWrapped.getNotAfter();
    }

    @Override
    public Date getNotBefore() {
        return this.mWrapped.getNotBefore();
    }

    @Override
    public PublicKey getPublicKey() {
        return this.mWrapped.getPublicKey();
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.mWrapped.getSerialNumber();
    }

    @Override
    public String getSigAlgName() {
        return this.mWrapped.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
        return this.mWrapped.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
        return this.mWrapped.getSigAlgParams();
    }

    @Override
    public byte[] getSignature() {
        return this.mWrapped.getSignature();
    }

    @Override
    public Principal getSubjectDN() {
        return this.mWrapped.getSubjectDN();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return this.mWrapped.getSubjectUniqueID();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return this.mWrapped.getTBSCertificate();
    }

    @Override
    public int getVersion() {
        return this.mWrapped.getVersion();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return this.mWrapped.hasUnsupportedCriticalExtension();
    }

    @Override
    public String toString() {
        return this.mWrapped.toString();
    }

    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.mWrapped.verify(publicKey);
    }

    @Override
    public void verify(PublicKey publicKey, String string2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.mWrapped.verify(publicKey, string2);
    }
}

