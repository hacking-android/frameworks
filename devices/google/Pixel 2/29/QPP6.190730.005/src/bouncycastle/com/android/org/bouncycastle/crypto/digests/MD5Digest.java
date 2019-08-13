/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.digests.EncodableDigest;
import com.android.org.bouncycastle.crypto.digests.GeneralDigest;
import com.android.org.bouncycastle.util.Memoable;
import com.android.org.bouncycastle.util.Pack;

public class MD5Digest
extends GeneralDigest
implements EncodableDigest {
    private static final int DIGEST_LENGTH = 16;
    private static final int S11 = 7;
    private static final int S12 = 12;
    private static final int S13 = 17;
    private static final int S14 = 22;
    private static final int S21 = 5;
    private static final int S22 = 9;
    private static final int S23 = 14;
    private static final int S24 = 20;
    private static final int S31 = 4;
    private static final int S32 = 11;
    private static final int S33 = 16;
    private static final int S34 = 23;
    private static final int S41 = 6;
    private static final int S42 = 10;
    private static final int S43 = 15;
    private static final int S44 = 21;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int[] X = new int[16];
    private int xOff;

    public MD5Digest() {
        this.reset();
    }

    public MD5Digest(MD5Digest mD5Digest) {
        super(mD5Digest);
        this.copyIn(mD5Digest);
    }

    public MD5Digest(byte[] arrby) {
        super(arrby);
        this.H1 = Pack.bigEndianToInt(arrby, 16);
        this.H2 = Pack.bigEndianToInt(arrby, 20);
        this.H3 = Pack.bigEndianToInt(arrby, 24);
        this.H4 = Pack.bigEndianToInt(arrby, 28);
        this.xOff = Pack.bigEndianToInt(arrby, 32);
        for (int i = 0; i != this.xOff; ++i) {
            this.X[i] = Pack.bigEndianToInt(arrby, i * 4 + 36);
        }
    }

    private int F(int n, int n2, int n3) {
        return n & n2 | n & n3;
    }

    private int G(int n, int n2, int n3) {
        return n & n3 | n3 & n2;
    }

    private int H(int n, int n2, int n3) {
        return n ^ n2 ^ n3;
    }

    private int K(int n, int n2, int n3) {
        return (n3 | n) ^ n2;
    }

    private void copyIn(MD5Digest mD5Digest) {
        super.copyIn(mD5Digest);
        this.H1 = mD5Digest.H1;
        this.H2 = mD5Digest.H2;
        this.H3 = mD5Digest.H3;
        this.H4 = mD5Digest.H4;
        int[] arrn = mD5Digest.X;
        System.arraycopy(arrn, 0, this.X, 0, arrn.length);
        this.xOff = mD5Digest.xOff;
    }

    private int rotateLeft(int n, int n2) {
        return n << n2 | n >>> 32 - n2;
    }

    private void unpackWord(int n, byte[] arrby, int n2) {
        arrby[n2] = (byte)n;
        arrby[n2 + 1] = (byte)(n >>> 8);
        arrby[n2 + 2] = (byte)(n >>> 16);
        arrby[n2 + 3] = (byte)(n >>> 24);
    }

    @Override
    public Memoable copy() {
        return new MD5Digest(this);
    }

    @Override
    public int doFinal(byte[] arrby, int n) {
        this.finish();
        this.unpackWord(this.H1, arrby, n);
        this.unpackWord(this.H2, arrby, n + 4);
        this.unpackWord(this.H3, arrby, n + 8);
        this.unpackWord(this.H4, arrby, n + 12);
        this.reset();
        return 16;
    }

    @Override
    public String getAlgorithmName() {
        return "MD5";
    }

    @Override
    public int getDigestSize() {
        return 16;
    }

    @Override
    public byte[] getEncodedState() {
        byte[] arrby = new byte[this.xOff * 4 + 36];
        super.populateState(arrby);
        Pack.intToBigEndian(this.H1, arrby, 16);
        Pack.intToBigEndian(this.H2, arrby, 20);
        Pack.intToBigEndian(this.H3, arrby, 24);
        Pack.intToBigEndian(this.H4, arrby, 28);
        Pack.intToBigEndian(this.xOff, arrby, 32);
        for (int i = 0; i != this.xOff; ++i) {
            Pack.intToBigEndian(this.X[i], arrby, i * 4 + 36);
        }
        return arrby;
    }

    @Override
    protected void processBlock() {
        int[] arrn;
        int n = this.H1;
        int n2 = this.H2;
        int n3 = this.H3;
        int n4 = this.H4;
        n = this.rotateLeft(this.F(n2, n3, n4) + n + this.X[0] - 680876936, 7) + n2;
        n4 = this.rotateLeft(this.F(n, n2, n3) + n4 + this.X[1] - 389564586, 12) + n;
        n3 = this.rotateLeft(this.F(n4, n, n2) + n3 + this.X[2] + 606105819, 17) + n4;
        int n5 = this.rotateLeft(this.F(n3, n4, n) + n2 + this.X[3] - 1044525330, 22) + n3;
        n2 = this.rotateLeft(this.F(n5, n3, n4) + n + this.X[4] - 176418897, 7) + n5;
        n4 = this.rotateLeft(this.F(n2, n5, n3) + n4 + this.X[5] + 1200080426, 12) + n2;
        n = this.rotateLeft(this.F(n4, n2, n5) + n3 + this.X[6] - 1473231341, 17) + n4;
        n3 = this.rotateLeft(this.F(n, n4, n2) + n5 + this.X[7] - 45705983, 22) + n;
        n5 = this.rotateLeft(this.F(n3, n, n4) + n2 + this.X[8] + 1770035416, 7) + n3;
        n2 = this.rotateLeft(this.F(n5, n3, n) + n4 + this.X[9] - 1958414417, 12) + n5;
        n4 = this.rotateLeft(this.F(n2, n5, n3) + n + this.X[10] - 42063, 17) + n2;
        n = this.rotateLeft(this.F(n4, n2, n5) + n3 + this.X[11] - 1990404162, 22) + n4;
        n3 = this.rotateLeft(this.F(n, n4, n2) + n5 + this.X[12] + 1804603682, 7) + n;
        n2 = this.rotateLeft(this.F(n3, n, n4) + n2 + this.X[13] - 40341101, 12) + n3;
        n4 = this.rotateLeft(this.F(n2, n3, n) + n4 + this.X[14] - 1502002290, 17) + n2;
        n = this.rotateLeft(this.F(n4, n2, n3) + n + this.X[15] + 1236535329, 22) + n4;
        n3 = this.rotateLeft(this.G(n, n4, n2) + n3 + this.X[1] - 165796510, 5) + n;
        n2 = this.rotateLeft(this.G(n3, n, n4) + n2 + this.X[6] - 1069501632, 9) + n3;
        int n6 = this.rotateLeft(this.G(n2, n3, n) + n4 + this.X[11] + 643717713, 14) + n2;
        n4 = this.rotateLeft(this.G(n6, n2, n3) + n + this.X[0] - 373897302, 20) + n6;
        n3 = this.rotateLeft(this.G(n4, n6, n2) + n3 + this.X[5] - 701558691, 5) + n4;
        n5 = this.rotateLeft(this.G(n3, n4, n6) + n2 + this.X[10] + 38016083, 9) + n3;
        n = this.rotateLeft(this.G(n5, n3, n4) + n6 + this.X[15] - 660478335, 14) + n5;
        n4 = this.rotateLeft(this.G(n, n5, n3) + n4 + this.X[4] - 405537848, 20) + n;
        n2 = this.rotateLeft(this.G(n4, n, n5) + n3 + this.X[9] + 568446438, 5) + n4;
        n3 = this.rotateLeft(this.G(n2, n4, n) + n5 + this.X[14] - 1019803690, 9) + n2;
        n = this.rotateLeft(this.G(n3, n2, n4) + n + this.X[3] - 187363961, 14) + n3;
        n5 = this.rotateLeft(this.G(n, n3, n2) + n4 + this.X[8] + 1163531501, 20) + n;
        n2 = this.rotateLeft(this.G(n5, n, n3) + n2 + this.X[13] - 1444681467, 5) + n5;
        n4 = this.rotateLeft(this.G(n2, n5, n) + n3 + this.X[2] - 51403784, 9) + n2;
        n = this.rotateLeft(this.G(n4, n2, n5) + n + this.X[7] + 1735328473, 14) + n4;
        n5 = this.rotateLeft(this.G(n, n4, n2) + n5 + this.X[12] - 1926607734, 20) + n;
        n3 = this.rotateLeft(this.H(n5, n, n4) + n2 + this.X[5] - 378558, 4) + n5;
        n2 = this.rotateLeft(this.H(n3, n5, n) + n4 + this.X[8] - 2022574463, 11) + n3;
        n = this.rotateLeft(this.H(n2, n3, n5) + n + this.X[11] + 1839030562, 16) + n2;
        n4 = this.rotateLeft(this.H(n, n2, n3) + n5 + this.X[14] - 35309556, 23) + n;
        n3 = this.rotateLeft(this.H(n4, n, n2) + n3 + this.X[1] - 1530992060, 4) + n4;
        n2 = this.rotateLeft(this.H(n3, n4, n) + n2 + this.X[4] + 1272893353, 11) + n3;
        n = this.rotateLeft(this.H(n2, n3, n4) + n + this.X[7] - 155497632, 16) + n2;
        n5 = this.rotateLeft(this.H(n, n2, n3) + n4 + this.X[10] - 1094730640, 23) + n;
        n3 = this.rotateLeft(this.H(n5, n, n2) + n3 + this.X[13] + 681279174, 4) + n5;
        n2 = this.rotateLeft(this.H(n3, n5, n) + n2 + this.X[0] - 358537222, 11) + n3;
        n4 = this.rotateLeft(this.H(n2, n3, n5) + n + this.X[3] - 722521979, 16) + n2;
        n = this.rotateLeft(this.H(n4, n2, n3) + n5 + this.X[6] + 76029189, 23) + n4;
        n5 = this.rotateLeft(this.H(n, n4, n2) + n3 + this.X[9] - 640364487, 4) + n;
        n2 = this.rotateLeft(this.H(n5, n, n4) + n2 + this.X[12] - 421815835, 11) + n5;
        n4 = this.rotateLeft(this.H(n2, n5, n) + n4 + this.X[15] + 530742520, 16) + n2;
        n3 = this.rotateLeft(this.H(n4, n2, n5) + n + this.X[2] - 995338651, 23) + n4;
        n5 = this.rotateLeft(this.K(n3, n4, n2) + n5 + this.X[0] - 198630844, 6) + n3;
        n2 = this.rotateLeft(this.K(n5, n3, n4) + n2 + this.X[7] + 1126891415, 10) + n5;
        n4 = this.rotateLeft(this.K(n2, n5, n3) + n4 + this.X[14] - 1416354905, 15) + n2;
        n = this.rotateLeft(this.K(n4, n2, n5) + n3 + this.X[5] - 57434055, 21) + n4;
        n3 = this.rotateLeft(this.K(n, n4, n2) + n5 + this.X[12] + 1700485571, 6) + n;
        n5 = this.rotateLeft(this.K(n3, n, n4) + n2 + this.X[3] - 1894986606, 10) + n3;
        n4 = this.rotateLeft(this.K(n5, n3, n) + n4 + this.X[10] - 1051523, 15) + n5;
        n2 = this.rotateLeft(this.K(n4, n5, n3) + n + this.X[1] - 2054922799, 21) + n4;
        n = this.rotateLeft(this.K(n2, n4, n5) + n3 + this.X[8] + 1873313359, 6) + n2;
        n3 = this.rotateLeft(this.K(n, n2, n4) + n5 + this.X[15] - 30611744, 10) + n;
        n5 = this.rotateLeft(this.K(n3, n, n2) + n4 + this.X[6] - 1560198380, 15) + n3;
        n4 = this.rotateLeft(this.K(n5, n3, n) + n2 + this.X[13] + 1309151649, 21) + n5;
        n2 = this.rotateLeft(this.K(n4, n5, n3) + n + this.X[4] - 145523070, 6) + n4;
        n3 = this.rotateLeft(this.K(n2, n4, n5) + n3 + this.X[11] - 1120210379, 10) + n2;
        n = this.rotateLeft(this.K(n3, n2, n4) + n5 + this.X[2] + 718787259, 15) + n3;
        n4 = this.rotateLeft(this.K(n, n3, n2) + n4 + this.X[9] - 343485551, 21);
        this.H1 += n2;
        this.H2 += n4 + n;
        this.H3 += n;
        this.H4 += n3;
        this.xOff = 0;
        for (n4 = 0; n4 != (arrn = this.X).length; ++n4) {
            arrn[n4] = 0;
        }
    }

    @Override
    protected void processLength(long l) {
        if (this.xOff > 14) {
            this.processBlock();
        }
        int[] arrn = this.X;
        arrn[14] = (int)(-1L & l);
        arrn[15] = (int)(l >>> 32);
    }

    @Override
    protected void processWord(byte[] arrby, int n) {
        int[] arrn = this.X;
        int n2 = this.xOff;
        this.xOff = n2 + 1;
        arrn[n2] = arrby[n] & 255 | (arrby[n + 1] & 255) << 8 | (arrby[n + 2] & 255) << 16 | (arrby[n + 3] & 255) << 24;
        if (this.xOff == 16) {
            this.processBlock();
        }
    }

    @Override
    public void reset() {
        int[] arrn;
        super.reset();
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.xOff = 0;
        for (int i = 0; i != (arrn = this.X).length; ++i) {
            arrn[i] = 0;
        }
    }

    @Override
    public void reset(Memoable memoable) {
        this.copyIn((MD5Digest)memoable);
    }
}

