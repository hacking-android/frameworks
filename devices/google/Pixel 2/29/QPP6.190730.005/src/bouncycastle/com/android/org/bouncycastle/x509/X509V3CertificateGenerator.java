/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import com.android.org.bouncycastle.asn1.x509.X509Extensions;
import com.android.org.bouncycastle.asn1.x509.X509ExtensionsGenerator;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import com.android.org.bouncycastle.jcajce.util.BCJcaJceHelper;
import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import com.android.org.bouncycastle.jce.X509Principal;
import com.android.org.bouncycastle.x509.ExtCertificateEncodingException;
import com.android.org.bouncycastle.x509.X509Util;
import com.android.org.bouncycastle.x509.extension.X509ExtensionUtil;
import dalvik.annotation.compat.UnsupportedAppUsage;
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
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;

public class X509V3CertificateGenerator {
    private final JcaJceHelper bcHelper = new BCJcaJceHelper();
    private final CertificateFactory certificateFactory = new CertificateFactory();
    private X509ExtensionsGenerator extGenerator = new X509ExtensionsGenerator();
    private AlgorithmIdentifier sigAlgId;
    private ASN1ObjectIdentifier sigOID;
    private String signatureAlgorithm;
    private V3TBSCertificateGenerator tbsGen = new V3TBSCertificateGenerator();

    private DERBitString booleanToBitString(boolean[] arrbl) {
        int n;
        byte[] arrby = new byte[(arrbl.length + 7) / 8];
        for (n = 0; n != arrbl.length; ++n) {
            int n2 = n / 8;
            byte by = arrby[n2];
            int n3 = arrbl[n] ? 1 << 7 - n % 8 : 0;
            arrby[n2] = (byte)(by | n3);
        }
        n = arrbl.length % 8;
        if (n == 0) {
            return new DERBitString(arrby);
        }
        return new DERBitString(arrby, 8 - n);
    }

    private X509Certificate generateJcaObject(TBSCertificate tBSCertificate, byte[] arrby) throws Exception {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(new DERBitString(arrby));
        return (X509Certificate)this.certificateFactory.engineGenerateCertificate(new ByteArrayInputStream(new DERSequence(aSN1EncodableVector).getEncoded("DER")));
    }

    private TBSCertificate generateTbsCert() {
        if (!this.extGenerator.isEmpty()) {
            this.tbsGen.setExtensions(this.extGenerator.generate());
        }
        return this.tbsGen.generateTBSCertificate();
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, ASN1Encodable aSN1Encodable) {
        this.extGenerator.addExtension(new ASN1ObjectIdentifier(aSN1ObjectIdentifier.getId()), bl, aSN1Encodable);
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, byte[] arrby) {
        this.extGenerator.addExtension(new ASN1ObjectIdentifier(aSN1ObjectIdentifier.getId()), bl, arrby);
    }

    public void addExtension(String string, boolean bl, ASN1Encodable aSN1Encodable) {
        this.addExtension(new ASN1ObjectIdentifier(string), bl, aSN1Encodable);
    }

    public void addExtension(String string, boolean bl, byte[] arrby) {
        this.addExtension(new ASN1ObjectIdentifier(string), bl, arrby);
    }

    public void copyAndAddExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, X509Certificate x509Certificate) throws CertificateParsingException {
        this.copyAndAddExtension(aSN1ObjectIdentifier.getId(), bl, x509Certificate);
    }

    public void copyAndAddExtension(String string, boolean bl, X509Certificate object) throws CertificateParsingException {
        if ((object = object.getExtensionValue(string)) != null) {
            try {
                this.addExtension(string, bl, (ASN1Encodable)X509ExtensionUtil.fromExtensionValue((byte[])object));
                return;
            }
            catch (IOException iOException) {
                throw new CertificateParsingException(iOException.toString());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("extension ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" not present");
        throw new CertificateParsingException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public X509Certificate generate(PrivateKey privateKey) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        return this.generate(privateKey, (SecureRandom)null);
    }

    public X509Certificate generate(PrivateKey privateKey, String string) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        return this.generate(privateKey, string, null);
    }

    public X509Certificate generate(PrivateKey object, String string, SecureRandom secureRandom) throws CertificateEncodingException, IllegalStateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        TBSCertificate tBSCertificate = this.generateTbsCert();
        try {
            object = X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, string, (PrivateKey)object, secureRandom, tBSCertificate);
        }
        catch (IOException iOException) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", iOException);
        }
        try {
            object = this.generateJcaObject(tBSCertificate, (byte[])object);
            return object;
        }
        catch (Exception exception) {
            throw new ExtCertificateEncodingException("exception producing certificate object", exception);
        }
    }

    public X509Certificate generate(PrivateKey object, SecureRandom secureRandom) throws CertificateEncodingException, IllegalStateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        TBSCertificate tBSCertificate = this.generateTbsCert();
        try {
            object = X509Util.calculateSignature(this.sigOID, this.signatureAlgorithm, (PrivateKey)object, secureRandom, tBSCertificate);
        }
        catch (IOException iOException) {
            throw new ExtCertificateEncodingException("exception encoding TBS cert", iOException);
        }
        try {
            object = this.generateJcaObject(tBSCertificate, (byte[])object);
            return object;
        }
        catch (Exception exception) {
            throw new ExtCertificateEncodingException("exception producing certificate object", exception);
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
        this.tbsGen = new V3TBSCertificateGenerator();
        this.extGenerator.reset();
    }

    @UnsupportedAppUsage
    public void setIssuerDN(X509Name x509Name) {
        this.tbsGen.setIssuer(x509Name);
    }

    @UnsupportedAppUsage
    public void setIssuerDN(X500Principal serializable) {
        try {
            V3TBSCertificateGenerator v3TBSCertificateGenerator = this.tbsGen;
            X509Principal x509Principal = new X509Principal(((X500Principal)serializable).getEncoded());
            v3TBSCertificateGenerator.setIssuer(x509Principal);
            return;
        }
        catch (IOException iOException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("can't process principal: ");
            ((StringBuilder)serializable).append(iOException);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
    }

    public void setIssuerUniqueID(boolean[] arrbl) {
        this.tbsGen.setIssuerUniqueID(this.booleanToBitString(arrbl));
    }

    @UnsupportedAppUsage
    public void setNotAfter(Date date) {
        this.tbsGen.setEndDate(new Time(date));
    }

    @UnsupportedAppUsage
    public void setNotBefore(Date date) {
        this.tbsGen.setStartDate(new Time(date));
    }

    @UnsupportedAppUsage
    public void setPublicKey(PublicKey serializable) throws IllegalArgumentException {
        try {
            V3TBSCertificateGenerator v3TBSCertificateGenerator = this.tbsGen;
            ASN1InputStream aSN1InputStream = new ASN1InputStream(serializable.getEncoded());
            v3TBSCertificateGenerator.setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(aSN1InputStream.readObject()));
            return;
        }
        catch (Exception exception) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("unable to process key - ");
            ((StringBuilder)serializable).append(exception.toString());
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
    }

    @UnsupportedAppUsage
    public void setSerialNumber(BigInteger bigInteger) {
        if (bigInteger.compareTo(BigInteger.ZERO) > 0) {
            this.tbsGen.setSerialNumber(new ASN1Integer(bigInteger));
            return;
        }
        throw new IllegalArgumentException("serial number must be a positive integer");
    }

    @UnsupportedAppUsage
    public void setSignatureAlgorithm(String string) {
        this.signatureAlgorithm = string;
        try {
            this.sigOID = X509Util.getAlgorithmOID(string);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown signature type requested: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.sigAlgId = X509Util.getSigAlgID(this.sigOID, string);
        this.tbsGen.setSignature(this.sigAlgId);
    }

    @UnsupportedAppUsage
    public void setSubjectDN(X509Name x509Name) {
        this.tbsGen.setSubject(x509Name);
    }

    @UnsupportedAppUsage
    public void setSubjectDN(X500Principal serializable) {
        try {
            V3TBSCertificateGenerator v3TBSCertificateGenerator = this.tbsGen;
            X509Principal x509Principal = new X509Principal(((X500Principal)serializable).getEncoded());
            v3TBSCertificateGenerator.setSubject(x509Principal);
            return;
        }
        catch (IOException iOException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("can't process principal: ");
            ((StringBuilder)serializable).append(iOException);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
    }

    public void setSubjectUniqueID(boolean[] arrbl) {
        this.tbsGen.setSubjectUniqueID(this.booleanToBitString(arrbl));
    }
}

