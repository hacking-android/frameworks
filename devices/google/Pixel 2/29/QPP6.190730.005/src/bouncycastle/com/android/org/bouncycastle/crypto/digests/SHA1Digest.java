/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.digests.EncodableDigest;
import com.android.org.bouncycastle.crypto.digests.GeneralDigest;
import com.android.org.bouncycastle.util.Memoable;
import com.android.org.bouncycastle.util.Pack;

public class SHA1Digest
extends GeneralDigest
implements EncodableDigest {
    private static final int DIGEST_LENGTH = 20;
    private static final int Y1 = 1518500249;
    private static final int Y2 = 1859775393;
    private static final int Y3 = -1894007588;
    private static final int Y4 = -899497514;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int[] X = new int[80];
    private int xOff;

    public SHA1Digest() {
        this.reset();
    }

    public SHA1Digest(SHA1Digest sHA1Digest) {
        super(sHA1Digest);
        this.copyIn(sHA1Digest);
    }

    public SHA1Digest(byte[] arrby) {
        super(arrby);
        this.H1 = Pack.bigEndianToInt(arrby, 16);
        this.H2 = Pack.bigEndianToInt(arrby, 20);
        this.H3 = Pack.bigEndianToInt(arrby, 24);
        this.H4 = Pack.bigEndianToInt(arrby, 28);
        this.H5 = Pack.bigEndianToInt(arrby, 32);
        this.xOff = Pack.bigEndianToInt(arrby, 36);
        for (int i = 0; i != this.xOff; ++i) {
            this.X[i] = Pack.bigEndianToInt(arrby, i * 4 + 40);
        }
    }

    private void copyIn(SHA1Digest sHA1Digest) {
        this.H1 = sHA1Digest.H1;
        this.H2 = sHA1Digest.H2;
        this.H3 = sHA1Digest.H3;
        this.H4 = sHA1Digest.H4;
        this.H5 = sHA1Digest.H5;
        int[] arrn = sHA1Digest.X;
        System.arraycopy(arrn, 0, this.X, 0, arrn.length);
        this.xOff = sHA1Digest.xOff;
    }

    private int f(int n, int n2, int n3) {
        return n & n2 | n & n3;
    }

    private int g(int n, int n2, int n3) {
        return n & n2 | n & n3 | n2 & n3;
    }

    private int h(int n, int n2, int n3) {
        return n ^ n2 ^ n3;
    }

    @Override
    public Memoable copy() {
        return new SHA1Digest(this);
    }

    @Override
    public int doFinal(byte[] arrby, int n) {
        this.finish();
        Pack.intToBigEndian(this.H1, arrby, n);
        Pack.intToBigEndian(this.H2, arrby, n + 4);
        Pack.intToBigEndian(this.H3, arrby, n + 8);
        Pack.intToBigEndian(this.H4, arrby, n + 12);
        Pack.intToBigEndian(this.H5, arrby, n + 16);
        this.reset();
        return 20;
    }

    @Override
    public String getAlgorithmName() {
        return "SHA-1";
    }

    @Override
    public int getDigestSize() {
        return 20;
    }

    @Override
    public byte[] getEncodedState() {
        byte[] arrby = new byte[this.xOff * 4 + 40];
        super.populateState(arrby);
        Pack.intToBigEndian(this.H1, arrby, 16);
        Pack.intToBigEndian(this.H2, arrby, 20);
        Pack.intToBigEndian(this.H3, arrby, 24);
        Pack.intToBigEndian(this.H4, arrby, 28);
        Pack.intToBigEndian(this.H5, arrby, 32);
        Pack.intToBigEndian(this.xOff, arrby, 36);
        for (int i = 0; i != this.xOff; ++i) {
            Pack.intToBigEndian(this.X[i], arrby, i * 4 + 40);
        }
        return arrby;
    }

    @Override
    protected void processBlock() {
        int n;
        int n2;
        int n3;
        int n4;
        int[] arrn;
        for (n3 = 16; n3 < 80; ++n3) {
            arrn = this.X;
            n4 = arrn[n3 - 3] ^ arrn[n3 - 8] ^ arrn[n3 - 14] ^ arrn[n3 - 16];
            arrn[n3] = n4 << 1 | n4 >>> 31;
        }
        n4 = this.H1;
        int n5 = this.H2;
        n3 = this.H3;
        int n6 = this.H4;
        int n7 = this.H5;
        int n8 = 0;
        int n9 = 0;
        while (n9 < 4) {
            n = this.f(n5, n3, n6);
            arrn = this.X;
            n2 = n8 + 1;
            n7 += (n4 << 5 | n4 >>> 27) + n + arrn[n8] + 1518500249;
            n5 = n5 << 30 | n5 >>> 2;
            n = this.f(n4, n5, n3);
            arrn = this.X;
            n8 = n2 + 1;
            n6 += (n7 << 5 | n7 >>> 27) + n + arrn[n2] + 1518500249;
            n4 = n4 << 30 | n4 >>> 2;
            n = this.f(n7, n4, n5);
            arrn = this.X;
            n2 = n8 + 1;
            n3 += (n6 << 5 | n6 >>> 27) + n + arrn[n8] + 1518500249;
            n7 = n7 << 30 | n7 >>> 2;
            n = this.f(n6, n7, n4);
            arrn = this.X;
            n8 = n2 + 1;
            n6 = n6 << 30 | n6 >>> 2;
            n4 += ((n5 += (n3 << 5 | n3 >>> 27) + n + arrn[n2] + 1518500249) << 5 | n5 >>> 27) + this.f(n3, n6, n7) + this.X[n8] + 1518500249;
            n3 = n3 << 30 | n3 >>> 2;
            ++n9;
            ++n8;
        }
        n9 = 0;
        while (n9 < 4) {
            n = this.h(n5, n3, n6);
            arrn = this.X;
            n2 = n8 + 1;
            n7 += (n4 << 5 | n4 >>> 27) + n + arrn[n8] + 1859775393;
            n5 = n5 << 30 | n5 >>> 2;
            n = this.h(n4, n5, n3);
            arrn = this.X;
            n8 = n2 + 1;
            n6 += (n7 << 5 | n7 >>> 27) + n + arrn[n2] + 1859775393;
            n4 = n4 << 30 | n4 >>> 2;
            n = this.h(n7, n4, n5);
            arrn = this.X;
            n2 = n8 + 1;
            n3 += (n6 << 5 | n6 >>> 27) + n + arrn[n8] + 1859775393;
            n7 = n7 << 30 | n7 >>> 2;
            n = this.h(n6, n7, n4);
            arrn = this.X;
            n8 = n2 + 1;
            n6 = n6 << 30 | n6 >>> 2;
            n4 += ((n5 += (n3 << 5 | n3 >>> 27) + n + arrn[n2] + 1859775393) << 5 | n5 >>> 27) + this.h(n3, n6, n7) + this.X[n8] + 1859775393;
            n3 = n3 << 30 | n3 >>> 2;
            ++n9;
            ++n8;
        }
        n9 = 0;
        while (n9 < 4) {
            n = this.g(n5, n3, n6);
            arrn = this.X;
            n2 = n8 + 1;
            n7 += (n4 << 5 | n4 >>> 27) + n + arrn[n8] - 1894007588;
            n5 = n5 << 30 | n5 >>> 2;
            n = this.g(n4, n5, n3);
            arrn = this.X;
            n8 = n2 + 1;
            n6 += (n7 << 5 | n7 >>> 27) + n + arrn[n2] - 1894007588;
            n4 = n4 << 30 | n4 >>> 2;
            n = this.g(n7, n4, n5);
            arrn = this.X;
            n2 = n8 + 1;
            n3 += (n6 << 5 | n6 >>> 27) + n + arrn[n8] - 1894007588;
            n7 = n7 << 30 | n7 >>> 2;
            n = this.g(n6, n7, n4);
            arrn = this.X;
            n8 = n2 + 1;
            n6 = n6 << 30 | n6 >>> 2;
            n4 += ((n5 += (n3 << 5 | n3 >>> 27) + n + arrn[n2] - 1894007588) << 5 | n5 >>> 27) + this.g(n3, n6, n7) + this.X[n8] - 1894007588;
            n3 = n3 << 30 | n3 >>> 2;
            ++n9;
            ++n8;
        }
        n9 = 0;
        while (n9 <= 3) {
            n = this.h(n5, n3, n6);
            arrn = this.X;
            n2 = n8 + 1;
            n7 += (n4 << 5 | n4 >>> 27) + n + arrn[n8] - 899497514;
            n5 = n5 << 30 | n5 >>> 2;
            n = this.h(n4, n5, n3);
            arrn = this.X;
            n8 = n2 + 1;
            n6 += (n7 << 5 | n7 >>> 27) + n + arrn[n2] - 899497514;
            n4 = n4 << 30 | n4 >>> 2;
            n = this.h(n7, n4, n5);
            arrn = this.X;
            n2 = n8 + 1;
            n3 += (n6 << 5 | n6 >>> 27) + n + arrn[n8] - 899497514;
            n7 = n7 << 30 | n7 >>> 2;
            n = this.h(n6, n7, n4);
            arrn = this.X;
            n8 = n2 + 1;
            n6 = n6 << 30 | n6 >>> 2;
            n4 += ((n5 += (n3 << 5 | n3 >>> 27) + n + arrn[n2] - 899497514) << 5 | n5 >>> 27) + this.h(n3, n6, n7) + this.X[n8] - 899497514;
            n3 = n3 << 30 | n3 >>> 2;
            ++n9;
            ++n8;
        }
        this.H1 += n4;
        this.H2 += n5;
        this.H3 += n3;
        this.H4 += n6;
        this.H5 += n7;
        this.xOff = 0;
        for (n3 = 0; n3 < 16; ++n3) {
            this.X[n3] = 0;
        }
    }

    @Override
    protected void processLength(long l) {
        if (this.xOff > 14) {
            this.processBlock();
        }
        int[] arrn = this.X;
        arrn[14] = (int)(l >>> 32);
        arrn[15] = (int)l;
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
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.H5 = -1009589776;
        this.xOff = 0;
        for (int i = 0; i != (arrn = this.X).length; ++i) {
            arrn[i] = 0;
        }
    }

    @Override
    public void reset(Memoable memoable) {
        memoable = (SHA1Digest)memoable;
        super.copyIn((GeneralDigest)memoable);
        this.copyIn((SHA1Digest)memoable);
    }
}

