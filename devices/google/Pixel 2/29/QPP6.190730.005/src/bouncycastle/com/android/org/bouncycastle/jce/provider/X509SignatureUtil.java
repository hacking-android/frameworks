/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PSSParameterSpec;

class X509SignatureUtil {
    private static final ASN1Null derNull = DERNull.INSTANCE;

    X509SignatureUtil() {
    }

    private static String getDigestAlgName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (PKCSObjectIdentifiers.md5.equals(aSN1ObjectIdentifier)) {
            return "MD5";
        }
        if (OIWObjectIdentifiers.idSHA1.equals(aSN1ObjectIdentifier)) {
            return "SHA1";
        }
        if (NISTObjectIdentifiers.id_sha224.equals(aSN1ObjectIdentifier)) {
            return "SHA224";
        }
        if (NISTObjectIdentifiers.id_sha256.equals(aSN1ObjectIdentifier)) {
            return "SHA256";
        }
        if (NISTObjectIdentifiers.id_sha384.equals(aSN1ObjectIdentifier)) {
            return "SHA384";
        }
        if (NISTObjectIdentifiers.id_sha512.equals(aSN1ObjectIdentifier)) {
            return "SHA512";
        }
        return aSN1ObjectIdentifier.getId();
    }

    static String getSignatureName(AlgorithmIdentifier object) {
        ASN1Encodable aSN1Encodable = ((AlgorithmIdentifier)object).getParameters();
        if (aSN1Encodable != null && !derNull.equals(aSN1Encodable) && ((AlgorithmIdentifier)object).getAlgorithm().equals(X9ObjectIdentifiers.ecdsa_with_SHA2)) {
            aSN1Encodable = ASN1Sequence.getInstance(aSN1Encodable);
            object = new StringBuilder();
            ((StringBuilder)object).append(X509SignatureUtil.getDigestAlgName(ASN1ObjectIdentifier.getInstance(((ASN1Sequence)aSN1Encodable).getObjectAt(0))));
            ((StringBuilder)object).append("withECDSA");
            return ((StringBuilder)object).toString();
        }
        return ((AlgorithmIdentifier)object).getAlgorithm().getId();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void setSignatureParameters(Signature object, ASN1Encodable object2) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (object2 == null || derNull.equals(object2)) return;
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(((Signature)object).getAlgorithm(), ((Signature)object).getProvider());
        try {
            algorithmParameters.init(object2.toASN1Primitive().getEncoded());
            if (!((Signature)object).getAlgorithm().endsWith("MGF1")) return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("IOException decoding parameters: ");
            ((StringBuilder)object).append(iOException.getMessage());
            throw new SignatureException(((StringBuilder)object).toString());
        }
        try {
            ((Signature)object).setParameter(algorithmParameters.getParameterSpec(PSSParameterSpec.class));
            return;
        }
        catch (GeneralSecurityException generalSecurityException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Exception extracting parameters: ");
            ((StringBuilder)object2).append(generalSecurityException.getMessage());
            throw new SignatureException(((StringBuilder)object2).toString());
        }
    }
}

