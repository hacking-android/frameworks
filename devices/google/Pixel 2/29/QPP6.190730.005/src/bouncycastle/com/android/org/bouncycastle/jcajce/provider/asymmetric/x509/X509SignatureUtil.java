/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.jcajce.util.MessageDigestUtils;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PSSParameterSpec;

class X509SignatureUtil {
    private static final ASN1Null derNull = DERNull.INSTANCE;

    X509SignatureUtil() {
    }

    private static String getDigestAlgName(ASN1ObjectIdentifier object) {
        String string = MessageDigestUtils.getDigestName((ASN1ObjectIdentifier)object);
        int n = string.indexOf(45);
        if (n > 0 && !string.startsWith("SHA3")) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string.substring(0, n));
            ((StringBuilder)object).append(string.substring(n + 1));
            return ((StringBuilder)object).toString();
        }
        return MessageDigestUtils.getDigestName((ASN1ObjectIdentifier)object);
    }

    static String getSignatureName(AlgorithmIdentifier object) {
        Object object2;
        Object object3 = ((AlgorithmIdentifier)object).getParameters();
        if (object3 != null && !derNull.equals(object3)) {
            if (((AlgorithmIdentifier)object).getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
                object3 = RSASSAPSSparams.getInstance(object3);
                object = new StringBuilder();
                ((StringBuilder)object).append(X509SignatureUtil.getDigestAlgName(((RSASSAPSSparams)object3).getHashAlgorithm().getAlgorithm()));
                ((StringBuilder)object).append("withRSAandMGF1");
                return ((StringBuilder)object).toString();
            }
            if (((AlgorithmIdentifier)object).getAlgorithm().equals(X9ObjectIdentifiers.ecdsa_with_SHA2)) {
                object = ASN1Sequence.getInstance(object3);
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(X509SignatureUtil.getDigestAlgName((ASN1ObjectIdentifier)((ASN1Sequence)object).getObjectAt(0)));
                ((StringBuilder)object3).append("withECDSA");
                return ((StringBuilder)object3).toString();
            }
        }
        if ((object2 = Security.getProvider("BC")) != null) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Alg.Alias.Signature.");
            ((StringBuilder)object3).append(((AlgorithmIdentifier)object).getAlgorithm().getId());
            object3 = ((Provider)object2).getProperty(((StringBuilder)object3).toString());
            if (object3 != null) {
                return object3;
            }
        }
        object3 = Security.getProviders();
        for (int i = 0; i != ((Object)object3).length; ++i) {
            Provider provider = object3[i];
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Alg.Alias.Signature.");
            ((StringBuilder)object2).append(((AlgorithmIdentifier)object).getAlgorithm().getId());
            object2 = provider.getProperty(((StringBuilder)object2).toString());
            if (object2 == null) continue;
            return object2;
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

