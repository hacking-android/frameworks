/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509.extension;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.X509Extension;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jce.PrincipalUtil;
import com.android.org.bouncycastle.x509.extension.X509ExtensionUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

public class AuthorityKeyIdentifierStructure
extends AuthorityKeyIdentifier {
    public AuthorityKeyIdentifierStructure(Extension extension) {
        super((ASN1Sequence)extension.getParsedValue());
    }

    public AuthorityKeyIdentifierStructure(X509Extension x509Extension) {
        super((ASN1Sequence)x509Extension.getParsedValue());
    }

    public AuthorityKeyIdentifierStructure(PublicKey publicKey) throws InvalidKeyException {
        super(AuthorityKeyIdentifierStructure.fromKey(publicKey));
    }

    public AuthorityKeyIdentifierStructure(X509Certificate x509Certificate) throws CertificateParsingException {
        super(AuthorityKeyIdentifierStructure.fromCertificate(x509Certificate));
    }

    public AuthorityKeyIdentifierStructure(byte[] arrby) throws IOException {
        super((ASN1Sequence)X509ExtensionUtil.fromExtensionValue(arrby));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ASN1Sequence fromCertificate(X509Certificate object) throws CertificateParsingException {
        try {
            if (((X509Certificate)object).getVersion() != 3) {
                GeneralName generalName = new GeneralName(PrincipalUtil.getIssuerX509Principal((X509Certificate)object));
                SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(((Certificate)object).getPublicKey().getEncoded());
                GeneralNames generalNames = new GeneralNames(generalName);
                AuthorityKeyIdentifier authorityKeyIdentifier = new AuthorityKeyIdentifier(subjectPublicKeyInfo, generalNames, ((X509Certificate)object).getSerialNumber());
                return (ASN1Sequence)authorityKeyIdentifier.toASN1Primitive();
            }
            GeneralName generalName = new GeneralName(PrincipalUtil.getIssuerX509Principal((X509Certificate)object));
            Object object2 = object.getExtensionValue(Extension.subjectKeyIdentifier.getId());
            if (object2 != null) {
                byte[] arrby = (byte[])X509ExtensionUtil.fromExtensionValue((byte[])object2);
                arrby = arrby.getOctets();
                GeneralNames generalNames = new GeneralNames(generalName);
                object2 = new AuthorityKeyIdentifier(arrby, generalNames, ((X509Certificate)object).getSerialNumber());
                return (ASN1Sequence)((AuthorityKeyIdentifier)object2).toASN1Primitive();
            }
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(((Certificate)object).getPublicKey().getEncoded());
            GeneralNames generalNames = new GeneralNames(generalName);
            object2 = new AuthorityKeyIdentifier(subjectPublicKeyInfo, generalNames, ((X509Certificate)object).getSerialNumber());
            return (ASN1Sequence)((AuthorityKeyIdentifier)object2).toASN1Primitive();
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception extracting certificate details: ");
            ((StringBuilder)object).append(exception.toString());
            throw new CertificateParsingException(((StringBuilder)object).toString());
        }
    }

    private static ASN1Sequence fromKey(PublicKey object) throws InvalidKeyException {
        try {
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(object.getEncoded());
            object = new AuthorityKeyIdentifier(subjectPublicKeyInfo);
            object = (ASN1Sequence)((AuthorityKeyIdentifier)object).toASN1Primitive();
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("can't process key: ");
            stringBuilder.append(exception);
            throw new InvalidKeyException(stringBuilder.toString());
        }
    }
}

