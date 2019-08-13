/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.x509.AttributeCertificateHolder;
import com.android.org.bouncycastle.x509.AttributeCertificateIssuer;
import com.android.org.bouncycastle.x509.X509Attribute;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Extension;
import java.util.Date;

public interface X509AttributeCertificate
extends X509Extension {
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException;

    public void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException;

    public X509Attribute[] getAttributes();

    public X509Attribute[] getAttributes(String var1);

    public byte[] getEncoded() throws IOException;

    public AttributeCertificateHolder getHolder();

    public AttributeCertificateIssuer getIssuer();

    public boolean[] getIssuerUniqueID();

    public Date getNotAfter();

    public Date getNotBefore();

    public BigInteger getSerialNumber();

    public byte[] getSignature();

    public int getVersion();

    public void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
}

