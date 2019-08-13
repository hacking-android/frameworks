/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.util.Arrays;

public class X9ECPoint
extends ASN1Object {
    private ECCurve c;
    private final ASN1OctetString encoding;
    private ECPoint p;

    public X9ECPoint(ECCurve eCCurve, ASN1OctetString aSN1OctetString) {
        this(eCCurve, aSN1OctetString.getOctets());
    }

    public X9ECPoint(ECCurve eCCurve, byte[] arrby) {
        this.c = eCCurve;
        this.encoding = new DEROctetString(Arrays.clone(arrby));
    }

    public X9ECPoint(ECPoint eCPoint) {
        this(eCPoint, false);
    }

    public X9ECPoint(ECPoint eCPoint, boolean bl) {
        this.p = eCPoint.normalize();
        this.encoding = new DEROctetString(eCPoint.getEncoded(bl));
    }

    public ECPoint getPoint() {
        synchronized (this) {
            if (this.p == null) {
                this.p = this.c.decodePoint(this.encoding.getOctets()).normalize();
            }
            ECPoint eCPoint = this.p;
            return eCPoint;
        }
    }

    public byte[] getPointEncoding() {
        return Arrays.clone(this.encoding.getOctets());
    }

    public boolean isPointCompressed() {
        boolean bl;
        block2 : {
            block3 : {
                boolean bl2;
                byte[] arrby = this.encoding.getOctets();
                bl = bl2 = false;
                if (arrby == null) break block2;
                bl = bl2;
                if (arrby.length <= 0) break block2;
                if (arrby[0] == 2) break block3;
                bl = bl2;
                if (arrby[0] != 3) break block2;
            }
            bl = true;
        }
        return bl;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.encoding;
    }
}

