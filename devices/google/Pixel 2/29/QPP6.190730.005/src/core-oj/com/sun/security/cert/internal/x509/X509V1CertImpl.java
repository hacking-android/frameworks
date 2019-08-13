/*
 * Decompiled with CFR 0.145.
 */
package com.sun.security.cert.internal.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Date;
import javax.security.cert.CertificateEncodingException;
import javax.security.cert.CertificateException;
import javax.security.cert.CertificateExpiredException;
import javax.security.cert.CertificateNotYetValidException;
import javax.security.cert.X509Certificate;

public class X509V1CertImpl
extends X509Certificate
implements Serializable {
    static final long serialVersionUID = -2048442350420423405L;
    private java.security.cert.X509Certificate wrappedCert;

    public X509V1CertImpl() {
    }

    public X509V1CertImpl(InputStream inputStream) throws CertificateException {
        try {
            this.wrappedCert = (java.security.cert.X509Certificate)X509V1CertImpl.getFactory().generateCertificate(inputStream);
            return;
        }
        catch (java.security.cert.CertificateException certificateException) {
            throw new CertificateException(certificateException.getMessage());
        }
    }

    public X509V1CertImpl(byte[] arrby) throws CertificateException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
            this.wrappedCert = (java.security.cert.X509Certificate)X509V1CertImpl.getFactory().generateCertificate(byteArrayInputStream);
            return;
        }
        catch (java.security.cert.CertificateException certificateException) {
            throw new CertificateException(certificateException.getMessage());
        }
    }

    private static CertificateFactory getFactory() throws java.security.cert.CertificateException {
        synchronized (X509V1CertImpl.class) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return certificateFactory;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readObject(ObjectInputStream object) throws IOException {
        synchronized (this) {
            try {
                try {
                    this.wrappedCert = (java.security.cert.X509Certificate)X509V1CertImpl.getFactory().generateCertificate((InputStream)object);
                    return;
                }
                catch (java.security.cert.CertificateException certificateException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("generateCertificate failed: ");
                    ((StringBuilder)object).append(certificateException.getMessage());
                    IOException iOException = new IOException(((StringBuilder)object).toString());
                    throw iOException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            try {
                try {
                    objectOutputStream.write(this.getEncoded());
                    return;
                }
                catch (CertificateEncodingException certificateEncodingException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getEncoded failed: ");
                    stringBuilder.append(certificateEncodingException.getMessage());
                    IOException iOException = new IOException(stringBuilder.toString());
                    throw iOException;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        try {
            this.wrappedCert.checkValidity(date);
            return;
        }
        catch (java.security.cert.CertificateExpiredException certificateExpiredException) {
            throw new CertificateExpiredException(certificateExpiredException.getMessage());
        }
        catch (java.security.cert.CertificateNotYetValidException certificateNotYetValidException) {
            throw new CertificateNotYetValidException(certificateNotYetValidException.getMessage());
        }
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        try {
            byte[] arrby = this.wrappedCert.getEncoded();
            return arrby;
        }
        catch (java.security.cert.CertificateEncodingException certificateEncodingException) {
            throw new CertificateEncodingException(certificateEncodingException.getMessage());
        }
    }

    @Override
    public Principal getIssuerDN() {
        return this.wrappedCert.getIssuerDN();
    }

    @Override
    public Date getNotAfter() {
        return this.wrappedCert.getNotAfter();
    }

    @Override
    public Date getNotBefore() {
        return this.wrappedCert.getNotBefore();
    }

    @Override
    public PublicKey getPublicKey() {
        return this.wrappedCert.getPublicKey();
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.wrappedCert.getSerialNumber();
    }

    @Override
    public String getSigAlgName() {
        return this.wrappedCert.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
        return this.wrappedCert.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
        return this.wrappedCert.getSigAlgParams();
    }

    @Override
    public Principal getSubjectDN() {
        return this.wrappedCert.getSubjectDN();
    }

    @Override
    public int getVersion() {
        return this.wrappedCert.getVersion() - 1;
    }

    public java.security.cert.X509Certificate getX509Certificate() {
        return this.wrappedCert;
    }

    @Override
    public String toString() {
        return this.wrappedCert.toString();
    }

    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        try {
            this.wrappedCert.verify(publicKey);
            return;
        }
        catch (java.security.cert.CertificateException certificateException) {
            throw new CertificateException(certificateException.getMessage());
        }
    }

    @Override
    public void verify(PublicKey publicKey, String string) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        try {
            this.wrappedCert.verify(publicKey, string);
            return;
        }
        catch (java.security.cert.CertificateException certificateException) {
            throw new CertificateException(certificateException.getMessage());
        }
    }
}

