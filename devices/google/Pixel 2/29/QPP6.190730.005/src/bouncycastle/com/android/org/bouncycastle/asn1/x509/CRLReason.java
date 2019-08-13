/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Enumerated;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.util.Hashtable;

public class CRLReason
extends ASN1Object {
    public static final int AA_COMPROMISE = 10;
    public static final int AFFILIATION_CHANGED = 3;
    public static final int CA_COMPROMISE = 2;
    public static final int CERTIFICATE_HOLD = 6;
    public static final int CESSATION_OF_OPERATION = 5;
    public static final int KEY_COMPROMISE = 1;
    public static final int PRIVILEGE_WITHDRAWN = 9;
    public static final int REMOVE_FROM_CRL = 8;
    public static final int SUPERSEDED = 4;
    public static final int UNSPECIFIED = 0;
    public static final int aACompromise = 10;
    public static final int affiliationChanged = 3;
    public static final int cACompromise = 2;
    public static final int certificateHold = 6;
    public static final int cessationOfOperation = 5;
    public static final int keyCompromise = 1;
    public static final int privilegeWithdrawn = 9;
    private static final String[] reasonString = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};
    public static final int removeFromCRL = 8;
    public static final int superseded = 4;
    private static final Hashtable table = new Hashtable();
    public static final int unspecified = 0;
    private ASN1Enumerated value;

    private CRLReason(int n) {
        this.value = new ASN1Enumerated(n);
    }

    public static CRLReason getInstance(Object object) {
        if (object instanceof CRLReason) {
            return (CRLReason)object;
        }
        if (object != null) {
            return CRLReason.lookup(ASN1Enumerated.getInstance(object).getValue().intValue());
        }
        return null;
    }

    public static CRLReason lookup(int n) {
        Integer n2 = Integers.valueOf(n);
        if (!table.containsKey(n2)) {
            table.put(n2, new CRLReason(n));
        }
        return (CRLReason)table.get(n2);
    }

    public BigInteger getValue() {
        return this.value.getValue();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.value;
    }

    public String toString() {
        int n = this.getValue().intValue();
        String string = n >= 0 && n <= 10 ? reasonString[n] : "invalid";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CRLReason: ");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }
}

