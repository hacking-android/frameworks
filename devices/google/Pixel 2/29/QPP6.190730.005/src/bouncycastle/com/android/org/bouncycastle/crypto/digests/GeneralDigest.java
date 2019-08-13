/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.ExtendedDigest;
import com.android.org.bouncycastle.util.Memoable;
import com.android.org.bouncycastle.util.Pack;

public abstract class GeneralDigest
implements ExtendedDigest,
Memoable {
    private static final int BYTE_LENGTH = 64;
    private long byteCount;
    private final byte[] xBuf;
    private int xBufOff;

    protected GeneralDigest() {
        this.xBuf = new byte[4];
        this.xBufOff = 0;
    }

    protected GeneralDigest(GeneralDigest generalDigest) {
        this.xBuf = new byte[4];
        this.copyIn(generalDigest);
    }

    protected GeneralDigest(byte[] arrby) {
        byte[] arrby2 = this.xBuf = new byte[4];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby2.length);
        this.xBufOff = Pack.bigEndianToInt(arrby, 4);
        this.byteCount = Pack.bigEndianToLong(arrby, 8);
    }

    protected void copyIn(GeneralDigest generalDigest) {
        byte[] arrby = generalDigest.xBuf;
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.xBuf, (int)0, (int)arrby.length);
        this.xBufOff = generalDigest.xBufOff;
        this.byteCount = generalDigest.byteCount;
    }

    public void finish() {
        long l = this.byteCount;
        this.update((byte)-128);
        while (this.xBufOff != 0) {
            this.update((byte)0);
        }
        this.processLength(l << 3);
        this.processBlock();
    }

    @Override
    public int getByteLength() {
        return 64;
    }

    protected void populateState(byte[] arrby) {
        System.arraycopy((byte[])this.xBuf, (int)0, (byte[])arrby, (int)0, (int)this.xBufOff);
        Pack.intToBigEndian(this.xBufOff, arrby, 4);
        Pack.longToBigEndian(this.byteCount, arrby, 8);
    }

    protected abstract void processBlock();

    protected abstract void processLength(long var1);

    protected abstract void processWord(byte[] var1, int var2);

    @Override
    public void reset() {
        byte[] arrby;
        this.byteCount = 0L;
        this.xBufOff = 0;
        for (int i = 0; i < (arrby = this.xBuf).length; ++i) {
            arrby[i] = (byte)(false ? 1 : 0);
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
        ++this.byteCount;
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        int n3;
        byte[] arrby2;
        int n4 = Math.max(0, n2);
        n2 = 0;
        int n5 = 0;
        if (this.xBufOff != 0) {
            do {
                n2 = n5;
                if (n5 >= n4) break;
                arrby2 = this.xBuf;
                n3 = this.xBufOff;
                this.xBufOff = n3 + 1;
                n2 = n5 + 1;
                arrby2[n3] = arrby[n5 + n];
                if (this.xBufOff == 4) {
                    this.processWord(arrby2, 0);
                    this.xBufOff = 0;
                    break;
                }
                n5 = n2;
            } while (true);
        }
        n5 = n2;
        do {
            if (n3 >= (n4 - n2 & -4) + n2) break;
            this.processWord(arrby, n + n3);
            n5 = n3 + 4;
        } while (true);
        for (n5 = n3 = n5; n5 < n4; ++n5) {
            arrby2 = this.xBuf;
            n2 = this.xBufOff;
            this.xBufOff = n2 + 1;
            arrby2[n2] = arrby[n5 + n];
        }
        this.byteCount += (long)n4;
    }
}

