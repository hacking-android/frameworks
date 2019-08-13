/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.util;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.IOException;
import java.security.AlgorithmParameters;

public class AlgorithmParametersUtils {
    private AlgorithmParametersUtils() {
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

    public static void loadParameters(AlgorithmParameters algorithmParameters, ASN1Encodable aSN1Encodable) throws IOException {
        try {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded(), "ASN.1");
        }
        catch (Exception exception) {
            algorithmParameters.init(aSN1Encodable.toASN1Primitive().getEncoded());
        }
    }
}

