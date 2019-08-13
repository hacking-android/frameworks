/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.digests.EncodableDigest;
import com.android.org.bouncycastle.crypto.digests.GeneralDigest;
import com.android.org.bouncycastle.util.Memoable;
import com.android.org.bouncycastle.util.Pack;

public class SHA224Digest
extends GeneralDigest
implements EncodableDigest {
    private static final int DIGEST_LENGTH = 28;
    static final int[] K = new int[]{1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int H6;
    private int H7;
    private int H8;
    private int[] X = new int[64];
    private int xOff;

    public SHA224Digest() {
        this.reset();
    }

    public SHA224Digest(SHA224Digest sHA224Digest) {
        super(sHA224Digest);
        this.doCopy(sHA224Digest);
    }

    public SHA224Digest(byte[] arrby) {
        super(arrby);
        this.H1 = Pack.bigEndianToInt(arrby, 16);
        this.H2 = Pack.bigEndianToInt(arrby, 20);
        this.H3 = Pack.bigEndianToInt(arrby, 24);
        this.H4 = Pack.bigEndianToInt(arrby, 28);
        this.H5 = Pack.bigEndianToInt(arrby, 32);
        this.H6 = Pack.bigEndianToInt(arrby, 36);
        this.H7 = Pack.bigEndianToInt(arrby, 40);
        this.H8 = Pack.bigEndianToInt(arrby, 44);
        this.xOff = Pack.bigEndianToInt(arrby, 48);
        for (int i = 0; i != this.xOff; ++i) {
            this.X[i] = Pack.bigEndianToInt(arrby, i * 4 + 52);
        }
    }

    private int Ch(int n, int n2, int n3) {
        return n & n2 ^ n & n3;
    }

    private int Maj(int n, int n2, int n3) {
        return n & n2 ^ n & n3 ^ n2 & n3;
    }

    private int Sum0(int n) {
        return (n >>> 2 | n << 30) ^ (n >>> 13 | n << 19) ^ (n >>> 22 | n << 10);
    }

    private int Sum1(int n) {
        return (n >>> 6 | n << 26) ^ (n >>> 11 | n << 21) ^ (n >>> 25 | n << 7);
    }

    private int Theta0(int n) {
        return (n >>> 7 | n << 25) ^ (n >>> 18 | n << 14) ^ n >>> 3;
    }

    private int Theta1(int n) {
        return (n >>> 17 | n << 15) ^ (n >>> 19 | n << 13) ^ n >>> 10;
    }

    private void doCopy(SHA224Digest sHA224Digest) {
        super.copyIn(sHA224Digest);
        this.H1 = sHA224Digest.H1;
        this.H2 = sHA224Digest.H2;
        this.H3 = sHA224Digest.H3;
        this.H4 = sHA224Digest.H4;
        this.H5 = sHA224Digest.H5;
        this.H6 = sHA224Digest.H6;
        this.H7 = sHA224Digest.H7;
        this.H8 = sHA224Digest.H8;
        int[] arrn = sHA224Digest.X;
        System.arraycopy(arrn, 0, this.X, 0, arrn.length);
        this.xOff = sHA224Digest.xOff;
    }

    @Override
    public Memoable copy() {
        return new SHA224Digest(this);
    }

    @Override
    public int doFinal(byte[] arrby, int n) {
        this.finish();
        Pack.intToBigEndian(this.H1, arrby, n);
        Pack.intToBigEndian(this.H2, arrby, n + 4);
        Pack.intToBigEndian(this.H3, arrby, n + 8);
        Pack.intToBigEndian(this.H4, arrby, n + 12);
        Pack.intToBigEndian(this.H5, arrby, n + 16);
        Pack.intToBigEndian(this.H6, arrby, n + 20);
        Pack.intToBigEndian(this.H7, arrby, n + 24);
        this.reset();
        return 28;
    }

    @Override
    public String getAlgorithmName() {
        return "SHA-224";
    }

    @Override
    public int getDigestSize() {
        return 28;
    }

    @Override
    public byte[] getEncodedState() {
        byte[] arrby = new byte[this.xOff * 4 + 52];
        super.populateState(arrby);
        Pack.intToBigEndian(this.H1, arrby, 16);
        Pack.intToBigEndian(this.H2, arrby, 20);
        Pack.intToBigEndian(this.H3, arrby, 24);
        Pack.intToBigEndian(this.H4, arrby, 28);
        Pack.intToBigEndian(this.H5, arrby, 32);
        Pack.intToBigEndian(this.H6, arrby, 36);
        Pack.intToBigEndian(this.H7, arrby, 40);
        Pack.intToBigEndian(this.H8, arrby, 44);
        Pack.intToBigEndian(this.xOff, arrby, 48);
        for (int i = 0; i != this.xOff; ++i) {
            Pack.intToBigEndian(this.X[i], arrby, i * 4 + 52);
        }
        return arrby;
    }

    @Override
    protected void processBlock() {
        int n;
        int n2;
        for (n = 16; n <= 63; ++n) {
            int[] arrn = this.X;
            n2 = this.Theta1(arrn[n - 2]);
            int[] arrn2 = this.X;
            arrn[n] = n2 + arrn2[n - 7] + this.Theta0(arrn2[n - 15]) + this.X[n - 16];
        }
        n2 = this.H1;
        int n3 = this.H2;
        int n4 = this.H3;
        int n5 = this.H4;
        int n6 = this.H5;
        int n7 = this.H6;
        int n8 = this.H7;
        int n9 = this.H8;
        int n10 = 0;
        for (n = 0; n < 8; ++n) {
            int n11 = n9 + (this.Sum1(n6) + this.Ch(n6, n7, n8) + K[n10] + this.X[n10]);
            n9 = n5 + n11;
            n5 = n11 + (this.Sum0(n2) + this.Maj(n2, n3, n4));
            n11 = n8 + (this.Sum1(n9) + this.Ch(n9, n6, n7) + K[++n10] + this.X[n10]);
            n8 = n4 + n11;
            n4 = n11 + (this.Sum0(n5) + this.Maj(n5, n2, n3));
            n3 += (n7 += this.Sum1(n8) + this.Ch(n8, n9, n6) + K[++n10] + this.X[n10]);
            n7 += this.Sum0(n4) + this.Maj(n4, n5, n2);
            n11 = n6 + (this.Sum1(n3) + this.Ch(n3, n8, n9) + K[++n10] + this.X[n10]);
            n6 = n2 + n11;
            n2 = n11 + (this.Sum0(n7) + this.Maj(n7, n4, n5));
            n11 = n9 + (this.Sum1(n6) + this.Ch(n6, n3, n8) + K[++n10] + this.X[n10]);
            n9 = n5 + n11;
            n5 = n11 + (this.Sum0(n2) + this.Maj(n2, n7, n4));
            n11 = n8 + (this.Sum1(n9) + this.Ch(n9, n6, n3) + K[++n10] + this.X[n10]);
            n8 = n4 + n11;
            n4 = n11 + (this.Sum0(n5) + this.Maj(n5, n2, n7));
            n7 += (n3 += this.Sum1(n8) + this.Ch(n8, n9, n6) + K[++n10] + this.X[n10]);
            n3 += this.Sum0(n4) + this.Maj(n4, n5, n2);
            n11 = n6 + (this.Sum1(n7) + this.Ch(n7, n8, n9) + K[++n10] + this.X[n10]);
            n6 = n2 + n11;
            n2 = n11 + (this.Sum0(n3) + this.Maj(n3, n4, n5));
            ++n10;
        }
        this.H1 += n2;
        this.H2 += n3;
        this.H3 += n4;
        this.H4 += n5;
        this.H5 += n6;
        this.H6 += n7;
        this.H7 += n8;
        this.H8 += n9;
        this.xOff = 0;
        for (n = 0; n < 16; ++n) {
            this.X[n] = 0;
        }
    }

    @Override
    protected void processLength(long l) {
        if (this.xOff > 14) {
            this.processBlock();
        }
        int[] arrn = this.X;
        arrn[14] = (int)(l >>> 32);
        arrn[15] = (int)(-1L & l);
    }

    @Override
    protected void processWord(byte[] arrby, int n) {
        byte by = arrby[n];
        int n2 = n + 1;
        n = arrby[n2];
        int n3 = n2 + 1;
        n2 = arrby[n3];
        n3 = arrby[n3 + 1];
        arrby = this.X;
        int n4 = this.xOff;
        arrby[n4] = by << 24 | (n & 255) << 16 | (n2 & 255) << 8 | n3 & 255;
        this.xOff = n = n4 + 1;
        if (n == 16) {
            this.processBlock();
        }
    }

    @Override
    public void reset() {
        int[] arrn;
        super.reset();
        this.H1 = -1056596264;
        this.H2 = 914150663;
        this.H3 = 812702999;
        this.H4 = -150054599;
        this.H5 = -4191439;
        this.H6 = 1750603025;
        this.H7 = 1694076839;
        this.H8 = -1090891868;
        this.xOff = 0;
        for (int i = 0; i != (arrn = this.X).length; ++i) {
            arrn[i] = 0;
        }
    }

    @Override
    public void reset(Memoable memoable) {
        this.doCopy((SHA224Digest)memoable);
    }
}

