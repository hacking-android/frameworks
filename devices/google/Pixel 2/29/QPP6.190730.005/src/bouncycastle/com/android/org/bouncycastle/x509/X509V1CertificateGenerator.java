/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.asn1.x509.V1TBSCertificateGenerator;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import com.android.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.x509.ExtCertificateEncodingException;
import com.android.org.bouncycastle.x509.X509Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;

public class X509V1CertificateGenerator {
    private final JcaJceHelper bcHelper = new BCJcaJceHelper();
    private final CertificateFactory certificateFactory = new CertificateFactory();
    private AlgorithmIdentifier sigAlgId;
    private ASN1ObjectIdentifier sigOID;
    private String signatureAlgorithm;
    private V1TBSCertificateGenerator tbsGen = new V1TBSCertificateGenerator();

    private X509Certificate generateJcaObject(TBSCertificate object, byte[] object2) throws CertificateEncodingException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add((ASN1Encodable)object);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(new DERBitString((byte[])object2));
        try {
            object = this.certificateFactory;
            DERSequence dERSequence = new DERSequence(aSN1EncodableVector);
            object2 = new ByteArrayInputStream(dERSequence.getEncoded("DER"));
            object = (X509Certificate)((CertificateFactory)object).engineGenerateCertificate((InputStream)object2);
            return object;
        }
        catch (Exception exception) {
            throw new ExtCertificateEncodingException("exception producing certificate object", exception);
        }
    }

    public X509Certificate generate(PrivateKey privateKey) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        return this.generate(privateKey, (SecureRandom)null);
    }

    public X509Certificate generate(PrivateKey privateKey, String string) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        return this.generate(privateKey, string, null);
    }

    public X509Certificate generate(PrivateKey arrby, String string, SecureRandom secureRandom) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        TBSCertificate tBSCertificate = this.tbsGen.generateTBSCertificate();
        try {
            arrby = X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, string, (PrivateKey)arrby, secureRandom, tBSCertificate);
            return this.generateJcaObject(tBSCertificate, arrby);
        }
        catch (IOException iOException) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", iOException);
        }
    }

    public X509Certificate generate(PrivateKey arrby, SecureRandom secureRandom) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        TBSCertificate tBSCertificate = this.tbsGen.generateTBSCertificate();
        try {
            arrby = X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, (PrivateKey)arrby, secureRandom, tBSCertificate);
            return this.generateJcaObject(tBSCertificate, arrby);
        }
        catch (IOException iOException) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", iOException);
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey serializable) throws SecurityException, SignatureException, InvalidKeyException {
        try {
            serializable = this.generateX509Certificate((PrivateKey)serializable, "BC", null);
            return serializable;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey privateKey, String string) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
        return this.generateX509Certificate(privateKey, string, null);
    }

    public X509Certificate generateX509Certificate(PrivateKey serializable, String charSequence, SecureRandom secureRandom) throws NoSuchProviderException, SecurityException, SignatureException, InvalidKeyException {
        try {
            serializable = this.generate((PrivateKey)serializable, (String)charSequence, secureRandom);
            return serializable;
        }
        catch (GeneralSecurityException generalSecurityException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("exception: ");
            ((StringBuilder)charSequence).append(generalSecurityException);
            throw new SecurityException(((StringBuilder)charSequence).toString());
        }
        catch (InvalidKeyException invalidKeyException) {
            throw invalidKeyException;
        }
        catch (SignatureException signatureException) {
            throw signatureException;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw noSuchProviderException;
        }
    }

    public X509Certificate generateX509Certificate(PrivateKey serializable, SecureRandom secureRandom) throws SecurityException, SignatureException, InvalidKeyException {
        try {
            serializable = this.generateX509Certificate((PrivateKey)serializable, "BC", secureRandom);
            return serializable;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            throw new SecurityException("BC provider not installed!");
        }
    }

    public Iterator getSignatureAlgNames() {
        return X509Util.getAlgNames();
    }

    public void reset() {
        this.tbsGen = new V1TBSCertificateGenerator();
    }

    public void setIssuerDN(X509Name x509Name) {
        this.tbsGen.setIssuer(x509Name);
    }

    public void setIssuerDN(X500Principal x500Principal) {
        try {
            V1TBSCertificateGenerator v1TBSCertificateGenerator = this.tbsGen;
            X509Principal x509Principal = new X509Principal(x500Principal.getEncoded());
            v1TBSCertificateGenerator.setIssuer(x509Principal);
            return;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("can't process principal: ");
            stringBuilder.append(iOException);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public void setNotAfter(Date date) {
        this.tbsGen.setEndDate(new Time(date));
    }

    public void setNotBefore(Date date) {
        this.tbsGen.setStartDate(new Time(date));
    }

    public void setPublicKey(PublicKey publicKey) {
        try {
            this.tbsGen.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()));
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unable to process key - ");
            stringBuilder.append(exception.toString());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public void setSerialNumber(BigInteger bigInteger) {
        if (bigInteger.compareTo(BigInteger.ZERO) > 0) {
            this.tbsGen.setSerialNumber(new ASN1Integer(bigInteger));
            return;
        }
        throw new IllegalArgumentException("serial number must be a positive integer");
    }

    public void setSignatureAlgorithm(String string) {
        this.signatureAlgorithm = string;
        try {
            this.sigOID = X509Util.getAlgorithmOID(string);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Unknown signature type requested");
        }
        this.sigAlgId = X509Util.getSigAlgID(this.sigOID, string);
        this.tbsGen.setSignature(this.sigAlgId);
    }

    public void setSubjectDN(X509Name x509Name) {
        this.tbsGen.setSubject(x509Name);
    }

    public void setSubjectDN(X500Principal serializable) {
        try {
            V1TBSCertificateGenerator v1TBSCertificateGenerator = this.tbsGen;
            X509Principal x509Principal = new X509Principal(((X500Principal)serializable).getEncoded());
            v1TBSCertificateGenerator.setSubject(x509Principal);
            return;
        }
        catch (IOException iOException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("can't process principal: ");
            ((StringBuilder)serializable).append(iOException);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
    }
}

