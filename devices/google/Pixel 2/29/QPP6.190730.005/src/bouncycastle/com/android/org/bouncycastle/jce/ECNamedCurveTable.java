/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce;

import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;
import java.util.Enumeration;

public class ECNamedCurveTable {
    public static Enumeration getNames() {
        return com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable.getNames();
    }

    public static ECNamedCurveParameterSpec getParameterSpec(String string) {
        ASN1Object aSN1Object = CustomNamedCurves.getByName(string);
        ASN1Object aSN1Object2 = aSN1Object;
        if (aSN1Object == null) {
            try {
                aSN1Object2 = new ASN1ObjectIdentifier(string);
                aSN1Object2 = CustomNamedCurves.getByOID((ASN1ObjectIdentifier)aSN1Object2);
                aSN1Object = aSN1Object2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            aSN1Object2 = aSN1Object;
            if (aSN1Object == null) {
                aSN1Object = com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByName(string);
                aSN1Object2 = aSN1Object;
                if (aSN1Object == null) {
                    try {
                        aSN1Object2 = new ASN1ObjectIdentifier(string);
                        aSN1Object2 = com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable.getByOID((ASN1ObjectIdentifier)aSN1Object2);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        aSN1Object2 = aSN1Object;
                    }
                }
            }
        }
        if (aSN1Object2 == null) {
            return null;
        }
        return new ECNamedCurveParameterSpec(string, ((X9ECParameters)aSN1Object2).getCurve(), ((X9ECParameters)aSN1Object2).getG(), ((X9ECParameters)aSN1Object2).getN(), ((X9ECParameters)aSN1Object2).getH(), ((X9ECParameters)aSN1Object2).getSeed());
    }
}

