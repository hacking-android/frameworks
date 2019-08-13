/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500.style;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.DERUTF8String;
import com.android.org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.IETFUtils;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class AbstractX500NameStyle
implements X500NameStyle {
    private int calcHashCode(ASN1Encodable aSN1Encodable) {
        return IETFUtils.canonicalize(IETFUtils.valueToString(aSN1Encodable)).hashCode();
    }

    public static Hashtable copyHashTable(Hashtable hashtable) {
        Hashtable hashtable2 = new Hashtable();
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            Object k = enumeration.nextElement();
            hashtable2.put(k, hashtable.get(k));
        }
        return hashtable2;
    }

    private boolean foundMatch(boolean bl, RDN rDN, RDN[] arrrDN) {
        if (bl) {
            for (int i = arrrDN.length - 1; i >= 0; --i) {
                if (arrrDN[i] == null || !this.rdnAreEqual(rDN, arrrDN[i])) continue;
                arrrDN[i] = null;
                return true;
            }
        } else {
            for (int i = 0; i != arrrDN.length; ++i) {
                if (arrrDN[i] == null || !this.rdnAreEqual(rDN, arrrDN[i])) continue;
                arrrDN[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areEqual(X500Name arrrDN, X500Name arrrDN2) {
        boolean bl;
        if ((arrrDN = arrrDN.getRDNs()).length != (arrrDN2 = arrrDN2.getRDNs()).length) {
            return false;
        }
        boolean bl2 = bl = false;
        if (arrrDN[0].getFirst() != null) {
            bl2 = bl;
            if (arrrDN2[0].getFirst() != null) {
                bl2 = arrrDN[0].getFirst().getType().equals(arrrDN2[0].getFirst().getType()) ^ true;
            }
        }
        for (int i = 0; i != arrrDN.length; ++i) {
            if (this.foundMatch(bl2, arrrDN[i], arrrDN2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int calculateHashCode(X500Name arrrDN) {
        int n = 0;
        arrrDN = arrrDN.getRDNs();
        for (int i = 0; i != arrrDN.length; ++i) {
            if (arrrDN[i].isMultiValued()) {
                AttributeTypeAndValue[] arrattributeTypeAndValue = arrrDN[i].getTypesAndValues();
                for (int j = 0; j != arrattributeTypeAndValue.length; ++j) {
                    n = n ^ arrattributeTypeAndValue[j].getType().hashCode() ^ this.calcHashCode(arrattributeTypeAndValue[j].getValue());
                }
                continue;
            }
            n = n ^ arrrDN[i].getFirst().getType().hashCode() ^ this.calcHashCode(arrrDN[i].getFirst().getValue());
        }
        return n;
    }

    protected ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        return new DERUTF8String(string);
    }

    protected boolean rdnAreEqual(RDN rDN, RDN rDN2) {
        return IETFUtils.rDNAreEqual(rDN, rDN2);
    }

    @Override
    public ASN1Encodable stringToValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String object) {
        if (((String)object).length() != 0 && ((String)object).charAt(0) == '#') {
            try {
                object = IETFUtils.valueFromHexString((String)object, 1);
                return object;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("can't recode value for oid ");
                stringBuilder.append(aSN1ObjectIdentifier.getId());
                throw new ASN1ParsingException(stringBuilder.toString());
            }
        }
        String string = object;
        if (((String)object).length() != 0) {
            string = object;
            if (((String)object).charAt(0) == '\\') {
                string = ((String)object).substring(1);
            }
        }
        return this.encodeStringValue(aSN1ObjectIdentifier, string);
    }
}

