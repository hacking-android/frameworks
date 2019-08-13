/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes.gcm;

import com.android.org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Pack;

public class Tables8kGCMMultiplier
implements GCMMultiplier {
    private byte[] H;
    private long[][][] T;

    @Override
    public void init(byte[] arrby) {
        if (this.T == null) {
            this.T = new long[32][16][2];
        } else if (Arrays.areEqual(this.H, arrby)) {
            return;
        }
        this.H = Arrays.clone(arrby);
        for (int i = 0; i < 32; ++i) {
            long[][][] arrl = this.T;
            arrby = arrl[i];
            if (i == 0) {
                GCMUtil.asLongs(this.H, (long[])arrby[1]);
                GCMUtil.multiplyP3((long[])arrby[1], (long[])arrby[1]);
            } else {
                GCMUtil.multiplyP4(arrl[i - 1][1], (long[])arrby[1]);
            }
            for (int j = 2; j < 16; j += 2) {
                GCMUtil.divideP((long[])arrby[j >> 1], (long[])arrby[j]);
                GCMUtil.xor((long[])arrby[j], (long[])arrby[1], (long[])arrby[j + 1]);
            }
        }
    }

    @Override
    public void multiplyH(byte[] arrby) {
        long l = 0L;
        long l2 = 0L;
        for (int i = 15; i >= 0; --i) {
            long[][][] arrl = this.T;
            long[] arrl2 = arrl[i + i + 1][arrby[i] & 15];
            arrl = arrl[i + i][(arrby[i] & 240) >>> 4];
            l ^= arrl2[0] ^ arrl[0];
            l2 ^= arrl2[1] ^ arrl[1];
        }
        Pack.longToBigEndian(l, arrby, 0);
        Pack.longToBigEndian(l2, arrby, 8);
    }
}

