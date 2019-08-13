/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class ECDomainParameters
implements ECConstants {
    private ECPoint G;
    private ECCurve curve;
    private BigInteger h;
    private BigInteger hInv = null;
    private BigInteger n;
    private byte[] seed;

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, ONE, null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        if (eCCurve != null) {
            if (bigInteger != null) {
                this.curve = eCCurve;
                this.G = ECDomainParameters.validate(eCCurve, eCPoint);
                this.n = bigInteger;
                this.h = bigInteger2;
                this.seed = Arrays.clone(arrby);
                return;
            }
            throw new NullPointerException("n");
        }
        throw new NullPointerException("curve");
    }

    static ECPoint validate(ECCurve object, ECPoint eCPoint) {
        if (eCPoint != null) {
            if (!((ECPoint)(object = ECAlgorithms.importPoint((ECCurve)object, eCPoint).normalize())).isInfinity()) {
                if (((ECPoint)object).isValid()) {
                    return object;
                }
                throw new IllegalArgumentException("Point not on curve");
            }
            throw new IllegalArgumentException("Point at infinity");
        }
        throw new IllegalArgumentException("Point has null value");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ECDomainParameters) {
            object = (ECDomainParameters)object;
            if (!(this.curve.equals(((ECDomainParameters)object).curve) && this.G.equals(((ECDomainParameters)object).G) && this.n.equals(((ECDomainParameters)object).n) && this.h.equals(((ECDomainParameters)object).h))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public ECPoint getG() {
        return this.G;
    }

    public BigInteger getH() {
        return this.h;
    }

    public BigInteger getHInv() {
        synchronized (this) {
            if (this.hInv == null) {
                this.hInv = this.h.modInverse(this.n);
            }
            BigInteger bigInteger = this.hInv;
            return bigInteger;
        }
    }

    public BigInteger getN() {
        return this.n;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    public int hashCode() {
        return ((this.curve.hashCode() * 37 ^ this.G.hashCode()) * 37 ^ this.n.hashCode()) * 37 ^ this.h.hashCode();
    }
}

