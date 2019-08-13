/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1UTCTime;
import java.util.Date;

public class DERUTCTime
extends ASN1UTCTime {
    public DERUTCTime(String string) {
        super(string);
    }

    public DERUTCTime(Date date) {
        super(date);
    }

    DERUTCTime(byte[] arrby) {
        super(arrby);
    }
}

