/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.util.Arrays;

public class ECFieldF2m
implements ECField {
    private int[] ks;
    private int m;
    private BigInteger rp;

    public ECFieldF2m(int n) {
        if (n > 0) {
            this.m = n;
            this.ks = null;
            this.rp = null;
            return;
        }
        throw new IllegalArgumentException("m is not positive");
    }

    public ECFieldF2m(int n, BigInteger bigInteger) {
        this.m = n;
        this.rp = bigInteger;
        if (n > 0) {
            int n2 = this.rp.bitCount();
            if (this.rp.testBit(0) && this.rp.testBit(n) && (n2 == 3 || n2 == 5)) {
                bigInteger = this.rp.clearBit(0).clearBit(n);
                this.ks = new int[n2 - 2];
                for (n = this.ks.length - 1; n >= 0; --n) {
                    this.ks[n] = n2 = bigInteger.getLowestSetBit();
                    bigInteger = bigInteger.clearBit(n2);
                }
                return;
            }
            throw new IllegalArgumentException("rp does not represent a valid reduction polynomial");
        }
        throw new IllegalArgumentException("m is not positive");
    }

    public ECFieldF2m(int n, int[] object) {
        this.m = n;
        this.ks = (int[])object.clone();
        if (n > 0) {
            object = this.ks;
            if (((int[])object).length != 1 && ((int[])object).length != 3) {
                throw new IllegalArgumentException("length of ks is neither 1 nor 3");
            }
            for (int i = 0; i < ((int[])(object = this.ks)).length; ++i) {
                if (object[i] >= 1 && object[i] <= n - 1) {
                    if (i == 0 || object[i] < object[i - 1]) continue;
                    throw new IllegalArgumentException("values in ks are not in descending order");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("ks[");
                ((StringBuilder)object).append(i);
                ((StringBuilder)object).append("] is out of range");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.rp = BigInteger.ONE;
            this.rp = this.rp.setBit(n);
            for (n = 0; n < ((Object)(object = this.ks)).length; ++n) {
                this.rp = this.rp.setBit((int)object[n]);
            }
            return;
        }
        throw new IllegalArgumentException("m is not positive");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ECFieldF2m) {
            if (this.m != ((ECFieldF2m)object).m || !Arrays.equals(this.ks, ((ECFieldF2m)object).ks)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int getFieldSize() {
        return this.m;
    }

    public int getM() {
        return this.m;
    }

    public int[] getMidTermsOfReductionPolynomial() {
        int[] arrn = this.ks;
        if (arrn == null) {
            return null;
        }
        return (int[])arrn.clone();
    }

    public BigInteger getReductionPolynomial() {
        return this.rp;
    }

    public int hashCode() {
        int n = this.m;
        BigInteger bigInteger = this.rp;
        int n2 = bigInteger == null ? 0 : bigInteger.hashCode();
        return (n << 5) + n2;
    }
}

