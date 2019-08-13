/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.nist.NISTNamedCurves;
import com.android.org.bouncycastle.asn1.sec.SECNamedCurves;
import com.android.org.bouncycastle.asn1.x9.X962NamedCurves;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Vector;

public class ECNamedCurveTable {
    private static void addEnumeration(Vector vector, Enumeration enumeration) {
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
    }

    private static X9ECParameters fromDomainParameters(ECDomainParameters object) {
        object = object == null ? null : new X9ECParameters(((ECDomainParameters)object).getCurve(), ((ECDomainParameters)object).getG(), ((ECDomainParameters)object).getN(), ((ECDomainParameters)object).getH(), ((ECDomainParameters)object).getSeed());
        return object;
    }

    public static X9ECParameters getByName(String string) {
        X9ECParameters x9ECParameters;
        X9ECParameters x9ECParameters2 = x9ECParameters = X962NamedCurves.getByName(string);
        if (x9ECParameters == null) {
            x9ECParameters2 = SECNamedCurves.getByName(string);
        }
        x9ECParameters = x9ECParameters2;
        if (x9ECParameters2 == null) {
            x9ECParameters = NISTNamedCurves.getByName(string);
        }
        return x9ECParameters;
    }

    public static X9ECParameters getByOID(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParameters x9ECParameters;
        X9ECParameters x9ECParameters2 = x9ECParameters = X962NamedCurves.getByOID(aSN1ObjectIdentifier);
        if (x9ECParameters == null) {
            x9ECParameters2 = SECNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        return x9ECParameters2;
    }

    public static String getName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String string;
        String string2 = string = X962NamedCurves.getName(aSN1ObjectIdentifier);
        if (string == null) {
            string2 = SECNamedCurves.getName(aSN1ObjectIdentifier);
        }
        string = string2;
        if (string2 == null) {
            string = NISTNamedCurves.getName(aSN1ObjectIdentifier);
        }
        return string;
    }

    public static Enumeration getNames() {
        Vector vector = new Vector();
        ECNamedCurveTable.addEnumeration(vector, X962NamedCurves.getNames());
        ECNamedCurveTable.addEnumeration(vector, SECNamedCurves.getNames());
        ECNamedCurveTable.addEnumeration(vector, NISTNamedCurves.getNames());
        return vector.elements();
    }

    public static ASN1ObjectIdentifier getOID(String string) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = aSN1ObjectIdentifier = X962NamedCurves.getOID(string);
        if (aSN1ObjectIdentifier == null) {
            aSN1ObjectIdentifier2 = SECNamedCurves.getOID(string);
        }
        aSN1ObjectIdentifier = aSN1ObjectIdentifier2;
        if (aSN1ObjectIdentifier2 == null) {
            aSN1ObjectIdentifier = NISTNamedCurves.getOID(string);
        }
        return aSN1ObjectIdentifier;
    }
}

