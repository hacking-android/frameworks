/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import java.math.BigInteger;

public class X9IntegerConverter {
    public int getByteLength(ECCurve eCCurve) {
        return (eCCurve.getFieldSize() + 7) / 8;
    }

    public int getByteLength(ECFieldElement eCFieldElement) {
        return (eCFieldElement.getFieldSize() + 7) / 8;
    }

    public byte[] integerToBytes(BigInteger arrby, int n) {
        if (n < (arrby = arrby.toByteArray()).length) {
            byte[] arrby2 = new byte[n];
            System.arraycopy((byte[])arrby, (int)(arrby.length - arrby2.length), (byte[])arrby2, (int)0, (int)arrby2.length);
            return arrby2;
        }
        if (n > arrby.length) {
            byte[] arrby3 = new byte[n];
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby3, (int)(arrby3.length - arrby.length), (int)arrby.length);
            return arrby3;
        }
        return arrby;
    }
}

