/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.ExtendedDigest;
import com.android.org.bouncycastle.crypto.digests.EncodableDigest;
import com.android.org.bouncycastle.util.Memoable;
import com.android.org.bouncycastle.util.Pack;

public abstract class LongDigest
implements ExtendedDigest,
Memoable,
EncodableDigest {
    private static final int BYTE_LENGTH = 128;
    static final long[] K = new long[]{4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};
    protected long H1;
    protected long H2;
    protected long H3;
    protected long H4;
    protected long H5;
    protected long H6;
    protected long H7;
    protected long H8;
    private long[] W = new long[80];
    private long byteCount1;
    private long byteCount2;
    private int wOff;
    private byte[] xBuf = new byte[8];
    private int xBufOff;

    protected LongDigest() {
        this.xBufOff = 0;
        this.reset();
    }

    protected LongDigest(LongDigest longDigest) {
        this.copyIn(longDigest);
    }

    private long Ch(long l, long l2, long l3) {
        return l & l2 ^ l & l3;
    }

    private long Maj(long l, long l2, long l3) {
        return l & l2 ^ l & l3 ^ l2 & l3;
    }

    private long Sigma0(long l) {
        return (l << 63 | l >>> 1) ^ (l << 56 | l >>> 8) ^ l >>> 7;
    }

    private long Sigma1(long l) {
        return (l << 45 | l >>> 19) ^ (l << 3 | l >>> 61) ^ l >>> 6;
    }

    private long Sum0(long l) {
        return (l << 36 | l >>> 28) ^ (l << 30 | l >>> 34) ^ (l << 25 | l >>> 39);
    }

    private long Sum1(long l) {
        return (l << 50 | l >>> 14) ^ (l << 46 | l >>> 18) ^ (l << 23 | l >>> 41);
    }

    private void adjustByteCounts() {
        long l = this.byteCount1;
        if (l > 0x1FFFFFFFFFFFFFFFL) {
            this.byteCount2 += l >>> 61;
            this.byteCount1 = l & 0x1FFFFFFFFFFFFFFFL;
        }
    }

    protected void copyIn(LongDigest longDigest) {
        byte[] arrby = longDigest.xBuf;
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.xBuf, (int)0, (int)arrby.length);
        this.xBufOff = longDigest.xBufOff;
        this.byteCount1 = longDigest.byteCount1;
        this.byteCount2 = longDigest.byteCount2;
        this.H1 = longDigest.H1;
        this.H2 = longDigest.H2;
        this.H3 = longDigest.H3;
        this.H4 = longDigest.H4;
        this.H5 = longDigest.H5;
        this.H6 = longDigest.H6;
        this.H7 = longDigest.H7;
        this.H8 = longDigest.H8;
        arrby = longDigest.W;
        System.arraycopy(arrby, 0, this.W, 0, arrby.length);
        this.wOff = longDigest.wOff;
    }

    public void finish() {
        this.adjustByteCounts();
        long l = this.byteCount1;
        long l2 = this.byteCount2;
        this.update((byte)-128);
        while (this.xBufOff != 0) {
            this.update((byte)0);
        }
        this.processLength(l << 3, l2);
        this.processBlock();
    }

    @Override
    public int getByteLength() {
        return 128;
    }

    protected int getEncodedStateSize() {
        return this.wOff * 8 + 96;
    }

    protected void populateState(byte[] arrby) {
        System.arraycopy((byte[])this.xBuf, (int)0, (byte[])arrby, (int)0, (int)this.xBufOff);
        Pack.intToBigEndian(this.xBufOff, arrby, 8);
        Pack.longToBigEndian(this.byteCount1, arrby, 12);
        Pack.longToBigEndian(this.byteCount2, arrby, 20);
        Pack.longToBigEndian(this.H1, arrby, 28);
        Pack.longToBigEndian(this.H2, arrby, 36);
        Pack.longToBigEndian(this.H3, arrby, 44);
        Pack.longToBigEndian(this.H4, arrby, 52);
        Pack.longToBigEndian(this.H5, arrby, 60);
        Pack.longToBigEndian(this.H6, arrby, 68);
        Pack.longToBigEndian(this.H7, arrby, 76);
        Pack.longToBigEndian(this.H8, arrby, 84);
        Pack.intToBigEndian(this.wOff, arrby, 92);
        for (int i = 0; i < this.wOff; ++i) {
            Pack.longToBigEndian(this.W[i], arrby, i * 8 + 96);
        }
    }

    protected void processBlock() {
        int n;
        long l;
        long[] arrl;
        this.adjustByteCounts();
        for (n = 16; n <= 79; ++n) {
            long[] arrl2 = this.W;
            l = this.Sigma1(arrl2[n - 2]);
            arrl = this.W;
            arrl2[n] = l + arrl[n - 7] + this.Sigma0(arrl[n - 15]) + this.W[n - 16];
        }
        long l2 = this.H1;
        long l3 = this.H2;
        long l4 = this.H3;
        long l5 = this.H4;
        long l6 = this.H5;
        long l7 = this.H6;
        long l8 = this.H7;
        l = this.H8;
        int n2 = 0;
        for (n = 0; n < 10; ++n) {
            long l9 = this.Sum1(l6);
            long l10 = this.Ch(l6, l7, l8);
            long l11 = K[n2];
            arrl = this.W;
            int n3 = n2 + 1;
            l5 += (l += l9 + l10 + l11 + arrl[n2]);
            l += this.Sum0(l2) + this.Maj(l2, l3, l4);
            l11 = this.Sum1(l5);
            l9 = this.Ch(l5, l6, l7);
            l10 = K[n3];
            arrl = this.W;
            int n4 = n3 + 1;
            l4 += (l8 += l11 + l9 + l10 + arrl[n3]);
            l8 += this.Sum0(l) + this.Maj(l, l2, l3);
            l10 = this.Sum1(l4);
            l11 = this.Ch(l4, l5, l6);
            l9 = K[n4];
            arrl = this.W;
            n2 = n4 + 1;
            l11 = l7 + (l10 + l11 + l9 + arrl[n4]);
            l7 = l3 + l11;
            l3 = l11 + (this.Sum0(l8) + this.Maj(l8, l, l2));
            l11 = this.Sum1(l7);
            l9 = this.Ch(l7, l4, l5);
            l10 = K[n2];
            arrl = this.W;
            n3 = n2 + 1;
            l6 += l11 + l9 + l10 + arrl[n2];
            l11 = l2 + l6;
            l2 = this.Sum1(l11);
            l10 = this.Ch(l11, l7, l4);
            l9 = K[n3];
            arrl = this.W;
            n4 = n3 + 1;
            l5 += l2 + l10 + l9 + arrl[n3];
            l2 = l + l5;
            l5 += this.Sum0(l6 += this.Sum0(l3) + this.Maj(l3, l8, l)) + this.Maj(l6, l3, l8);
            l10 = this.Sum1(l2);
            l = l2;
            l2 = this.Ch(l2, l11, l7);
            l9 = K[n4];
            arrl = this.W;
            n2 = n4 + 1;
            l4 += l10 + l2 + l9 + arrl[n4];
            l2 = l8 + l4;
            l9 = this.Sum0(l5);
            long l12 = this.Maj(l5, l6, l3);
            l10 = this.Sum1(l2);
            l8 = l2;
            l4 += l9 + l12;
            l9 = this.Ch(l2, l, l11);
            l2 = K[n2];
            arrl = this.W;
            n3 = n2 + 1;
            l9 = l7 + (l10 + l9 + l2 + arrl[n2]);
            l2 = l3 + l9;
            l3 = this.Sum0(l4);
            l12 = this.Maj(l4, l5, l6);
            l10 = this.Sum1(l2);
            l7 = l2;
            l3 = l9 + (l3 + l12);
            l2 = this.Ch(l2, l8, l);
            l9 = K[n3];
            arrl = this.W;
            n2 = n3 + 1;
            l11 += l10 + l2 + l9 + arrl[n3];
            l10 = this.Sum0(l3);
            l2 = this.Maj(l3, l4, l5);
            l6 += l11;
            l2 = l11 + (l10 + l2);
        }
        this.H1 += l2;
        this.H2 += l3;
        this.H3 += l4;
        this.H4 += l5;
        this.H5 += l6;
        this.H6 += l7;
        this.H7 += l8;
        this.H8 += l;
        this.wOff = 0;
        for (n = 0; n < 16; ++n) {
            this.W[n] = 0L;
        }
    }

    protected void processLength(long l, long l2) {
        if (this.wOff > 14) {
            this.processBlock();
        }
        long[] arrl = this.W;
        arrl[14] = l2;
        arrl[15] = l;
    }

    protected void processWord(byte[] arrby, int n) {
        this.W[this.wOff] = Pack.bigEndianToLong(arrby, n);
        this.wOff = n = this.wOff + 1;
        if (n == 16) {
            this.processBlock();
        }
    }

    @Override
    public void reset() {
        int n;
        byte[] arrby;
        this.byteCount1 = 0L;
        this.byteCount2 = 0L;
        this.xBufOff = 0;
        for (n = 0; n < (arrby = this.xBuf).length; ++n) {
            arrby[n] = (byte)(false ? 1 : 0);
        }
        this.wOff = 0;
        for (n = 0; n != (arrby = this.W).length; ++n) {
            arrby[n] = (byte)0L;
        }
    }

    protected void restoreState(byte[] arrby) {
        this.xBufOff = Pack.bigEndianToInt(arrby, 8);
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.xBuf, (int)0, (int)this.xBufOff);
        this.byteCount1 = Pack.bigEndianToLong(arrby, 12);
        this.byteCount2 = Pack.bigEndianToLong(arrby, 20);
        this.H1 = Pack.bigEndianToLong(arrby, 28);
        this.H2 = Pack.bigEndianToLong(arrby, 36);
        this.H3 = Pack.bigEndianToLong(arrby, 44);
        this.H4 = Pack.bigEndianToLong(arrby, 52);
        this.H5 = Pack.bigEndianToLong(arrby, 60);
        this.H6 = Pack.bigEndianToLong(arrby, 68);
        this.H7 = Pack.bigEndianToLong(arrby, 76);
        this.H8 = Pack.bigEndianToLong(arrby, 84);
        this.wOff = Pack.bigEndianToInt(arrby, 92);
        for (int i = 0; i < this.wOff; ++i) {
            this.W[i] = Pack.bigEndianToLong(arrby, i * 8 + 96);
        }
    }

    @Override
    public void update(byte by) {
        byte[] arrby = this.xBuf;
        int n = this.xBufOff;
        this.xBufOff = n + 1;
        arrby[n] = by;
        if (this.xBufOff == arrby.length) {
            this.processWord(arrby, 0);
            this.xBufOff = 0;
        }
        ++this.byteCount1;
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        int n3 = n2;
        int n4 = n;
        do {
            n = n4;
            n2 = n3;
            if (this.xBufOff == 0) break;
            n = n4;
            n2 = n3;
            if (n3 <= 0) break;
            this.update(arrby[n4]);
            ++n4;
            --n3;
        } while (true);
        do {
            n3 = n;
            if (n2 <= this.xBuf.length) break;
            this.processWord(arrby, n);
            byte[] arrby2 = this.xBuf;
            n += arrby2.length;
            n2 -= arrby2.length;
            this.byteCount1 += (long)arrby2.length;
        } while (true);
        for (n4 = n2; n4 > 0; --n4) {
            this.update(arrby[n3]);
            ++n3;
        }
    }
}

