/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.AttCertIssuer;
import com.android.org.bouncycastle.asn1.x509.AttCertValidityPeriod;
import com.android.org.bouncycastle.asn1.x509.AttributeCertificate;
import com.android.org.bouncycastle.asn1.x509.AttributeCertificateInfo;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.Holder;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.x509.AttributeCertificateHolder;
import com.android.org.bouncycastle.x509.AttributeCertificateIssuer;
import com.android.org.bouncycastle.x509.X509Attribute;
import com.android.org.bouncycastle.x509.X509AttributeCertificate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class X509V2AttributeCertificate
implements X509AttributeCertificate {
    private AttributeCertificate cert;
    private Date notAfter;
    private Date notBefore;

    X509V2AttributeCertificate(AttributeCertificate attributeCertificate) throws IOException {
        this.cert = attributeCertificate;
        try {
            this.notAfter = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime().getDate();
            this.notBefore = attributeCertificate.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime().getDate();
            return;
        }
        catch (ParseException parseException) {
            throw new IOException("invalid data structure in certificate!");
        }
    }

    public X509V2AttributeCertificate(InputStream inputStream) throws IOException {
        this(X509V2AttributeCertificate.getObject(inputStream));
    }

    public X509V2AttributeCertificate(byte[] arrby) throws IOException {
        this(new ByteArrayInputStream(arrby));
    }

    private Set getExtensionOIDs(boolean bl) {
        Extensions extensions = this.cert.getAcinfo().getExtensions();
        if (extensions != null) {
            HashSet<String> hashSet = new HashSet<String>();
            Enumeration enumeration = extensions.oids();
            while (enumeration.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                if (extensions.getExtension(aSN1ObjectIdentifier).isCritical() != bl) continue;
                hashSet.add(aSN1ObjectIdentifier.getId());
            }
            return hashSet;
        }
        return null;
    }

    private static AttributeCertificate getObject(InputStream object) throws IOException {
        try {
            ASN1InputStream aSN1InputStream = new ASN1InputStream((InputStream)object);
            object = AttributeCertificate.getInstance(aSN1InputStream.readObject());
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("exception decoding certificate structure: ");
            stringBuilder.append(exception.toString());
            throw new IOException(stringBuilder.toString());
        }
        catch (IOException iOException) {
            throw iOException;
        }
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.checkValidity(new Date());
    }

    @Override
    public void checkValidity(Date serializable) throws CertificateExpiredException, CertificateNotYetValidException {
        if (!((Date)serializable).after(this.getNotAfter())) {
            if (!((Date)serializable).before(this.getNotBefore())) {
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("certificate not valid till ");
            ((StringBuilder)serializable).append(this.getNotBefore());
            throw new CertificateNotYetValidException(((StringBuilder)serializable).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("certificate expired on ");
        ((StringBuilder)serializable).append(this.getNotAfter());
        throw new CertificateExpiredException(((StringBuilder)serializable).toString());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof X509AttributeCertificate)) {
            return false;
        }
        object = (X509AttributeCertificate)object;
        try {
            boolean bl = Arrays.areEqual(this.getEncoded(), object.getEncoded());
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public X509Attribute[] getAttributes() {
        ASN1Sequence aSN1Sequence = this.cert.getAcinfo().getAttributes();
        X509Attribute[] arrx509Attribute = new X509Attribute[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            arrx509Attribute[i] = new X509Attribute(aSN1Sequence.getObjectAt(i));
        }
        return arrx509Attribute;
    }

    @Override
    public X509Attribute[] getAttributes(String string) {
        ASN1Sequence aSN1Sequence = this.cert.getAcinfo().getAttributes();
        ArrayList<X509Attribute> arrayList = new ArrayList<X509Attribute>();
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            X509Attribute x509Attribute = new X509Attribute(aSN1Sequence.getObjectAt(i));
            if (!x509Attribute.getOID().equals(string)) continue;
            arrayList.add(x509Attribute);
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList.toArray(new X509Attribute[arrayList.size()]);
    }

    public Set getCriticalExtensionOIDs() {
        return this.getExtensionOIDs(true);
    }

    @Override
    public byte[] getEncoded() throws IOException {
        return this.cert.getEncoded();
    }

    @Override
    public byte[] getExtensionValue(String object) {
        Extensions extensions = this.cert.getAcinfo().getExtensions();
        if (extensions != null && (object = extensions.getExtension(new ASN1ObjectIdentifier((String)object))) != null) {
            try {
                object = ((Extension)object).getExtnValue().getEncoded("DER");
                return object;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("error encoding ");
                ((StringBuilder)object).append(exception.toString());
                throw new RuntimeException(((StringBuilder)object).toString());
            }
        }
        return null;
    }

    @Override
    public AttributeCertificateHolder getHolder() {
        return new AttributeCertificateHolder((ASN1Sequence)this.cert.getAcinfo().getHolder().toASN1Primitive());
    }

    @Override
    public AttributeCertificateIssuer getIssuer() {
        return new AttributeCertificateIssuer(this.cert.getAcinfo().getIssuer());
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        boolean[] arrbl = this.cert.getAcinfo().getIssuerUniqueID();
        if (arrbl != null) {
            byte[] arrby = arrbl.getBytes();
            arrbl = new boolean[arrby.length * 8 - arrbl.getPadBits()];
            for (int i = 0; i != arrbl.length; ++i) {
                boolean bl = (arrby[i / 8] & 128 >>> i % 8) != 0;
                arrbl[i] = bl;
            }
            return arrbl;
        }
        return null;
    }

    public Set getNonCriticalExtensionOIDs() {
        return this.getExtensionOIDs(false);
    }

    @Override
    public Date getNotAfter() {
        return this.notAfter;
    }

    @Override
    public Date getNotBefore() {
        return this.notBefore;
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.cert.getAcinfo().getSerialNumber().getValue();
    }

    @Override
    public byte[] getSignature() {
        return this.cert.getSignatureValue().getOctets();
    }

    @Override
    public int getVersion() {
        return this.cert.getAcinfo().getVersion().getValue().intValue() + 1;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        Set set = this.getCriticalExtensionOIDs();
        boolean bl = set != null && !set.isEmpty();
        return bl;
    }

    public int hashCode() {
        try {
            int n = Arrays.hashCode(this.getEncoded());
            return n;
        }
        catch (IOException iOException) {
            return 0;
        }
    }

    @Override
    public final void verify(PublicKey publicKey, String object) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        if (this.cert.getSignatureAlgorithm().equals(this.cert.getAcinfo().getSignature())) {
            object = Signature.getInstance(this.cert.getSignatureAlgorithm().getAlgorithm().getId(), (String)object);
            ((Signature)object).initVerify(publicKey);
            try {
                ((Signature)object).update(this.cert.getAcinfo().getEncoded());
                if (((Signature)object).verify(this.getSignature())) {
                    return;
                }
                throw new InvalidKeyException("Public key presented not for certificate signature");
            }
            catch (IOException iOException) {
                throw new SignatureException("Exception encoding certificate info object");
            }
        }
        throw new CertificateException("Signature algorithm in certificate info not same as outer certificate");
    }
}

