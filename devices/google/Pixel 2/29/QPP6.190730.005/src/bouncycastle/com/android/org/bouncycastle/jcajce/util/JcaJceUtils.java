/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.util;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.io.IOException;
import java.security.AlgorithmParameters;

public class JcaJceUtils {
    private JcaJceUtils() {
    }

    public static ASN1Encodable extractParameters(AlgorithmParameters object) throws IOException {
        try {
            ASN1Primitive aSN1Primitive = ASN1Primitive.fromByteArray(((AlgorithmParameters)object).getEncoded("ASN.1"));
            object = aSN1Primitive;
        }
        catch (Exception exception) {
            object = ASN1Primitive.fromByteArray(((AlgorithmParameters)object).getEncoded());
        }
        return object;
    }

    public static String getDigestAlgName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
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

    public static void loadParameters(AlgorithmParameters algorithmParameters, ASN1Encodable aSN1Encodable) throws IOException {
        try {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
        }
        catch (Exception exception) {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded());
        }
    }
}

