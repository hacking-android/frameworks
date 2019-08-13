/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.nist;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.sec.SECNamedCurves;
import com.android.org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.util.Strings;
import java.util.Enumeration;
import java.util.Hashtable;

public class NISTNamedCurves {
    static final Hashtable names;
    static final Hashtable objIds;

    static {
        objIds = new Hashtable();
        names = new Hashtable();
        NISTNamedCurves.defineCurve("B-571", SECObjectIdentifiers.sect571r1);
        NISTNamedCurves.defineCurve("B-409", SECObjectIdentifiers.sect409r1);
        NISTNamedCurves.defineCurve("B-283", SECObjectIdentifiers.sect283r1);
        NISTNamedCurves.defineCurve("B-233", SECObjectIdentifiers.sect233r1);
        NISTNamedCurves.defineCurve("B-163", SECObjectIdentifiers.sect163r2);
        NISTNamedCurves.defineCurve("K-571", SECObjectIdentifiers.sect571k1);
        NISTNamedCurves.defineCurve("K-409", SECObjectIdentifiers.sect409k1);
        NISTNamedCurves.defineCurve("K-283", SECObjectIdentifiers.sect283k1);
        NISTNamedCurves.defineCurve("K-233", SECObjectIdentifiers.sect233k1);
        NISTNamedCurves.defineCurve("K-163", SECObjectIdentifiers.sect163k1);
        NISTNamedCurves.defineCurve("P-521", SECObjectIdentifiers.secp521r1);
        NISTNamedCurves.defineCurve("P-384", SECObjectIdentifiers.secp384r1);
        NISTNamedCurves.defineCurve("P-256", SECObjectIdentifiers.secp256r1);
        NISTNamedCurves.defineCurve("P-224", SECObjectIdentifiers.secp224r1);
        NISTNamedCurves.defineCurve("P-192", SECObjectIdentifiers.secp192r1);
    }

    static void defineCurve(String string, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        objIds.put(string, aSN1ObjectIdentifier);
        names.put(aSN1ObjectIdentifier, string);
    }

    public static X9ECParameters getByName(String object) {
        if ((object = (ASN1ObjectIdentifier)objIds.get(Strings.toUpperCase((String)object))) != null) {
            return NISTNamedCurves.getByOID((ASN1ObjectIdentifier)object);
        }
        return null;
    }

    public static X9ECParameters getByOID(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return SECNamedCurves.getByOID(aSN1ObjectIdentifier);
    }

    public static String getName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String)names.get(aSN1ObjectIdentifier);
    }

    public static Enumeration getNames() {
        return objIds.keys();
    }

    public static ASN1ObjectIdentifier getOID(String string) {
        return (ASN1ObjectIdentifier)objIds.get(Strings.toUpperCase(string));
    }
}

