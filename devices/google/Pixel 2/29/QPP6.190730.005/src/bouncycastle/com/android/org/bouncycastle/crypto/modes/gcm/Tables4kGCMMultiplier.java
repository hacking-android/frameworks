/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes.gcm;

import com.android.org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Pack;

public class Tables4kGCMMultiplier
implements GCMMultiplier {
    private byte[] H;
    private long[][] T;

    @Override
    public void init(byte[] arrby) {
        if (this.T == null) {
            this.T = new long[256][2];
        } else if (Arrays.areEqual(this.H, arrby)) {
            return;
        }
        this.H = Arrays.clone(arrby);
        GCMUtil.asLongs(this.H, this.T[1]);
        arrby = this.T;
        GCMUtil.multiplyP7((long[])arrby[1], (long[])arrby[1]);
        for (int i = 2; i < 256; i += 2) {
            arrby = this.T;
            GCMUtil.divideP((long[])arrby[i >> 1], (long[])arrby[i]);
            arrby = this.T;
            GCMUtil.xor((long[])arrby[i], (long[])arrby[1], (long[])arrby[i + 1]);
        }
    }

    @Override
    public void multiplyH(byte[] arrby) {
        long[] arrl = this.T[arrby[15] & 255];
        long l = arrl[0];
        long l2 = arrl[1];
        for (int i = 14; i >= 0; --i) {
            arrl = this.T[arrby[i] & 255];
            long l3 = l2 << 56;
            l2 = arrl[1] ^ (l2 >>> 8 | l << 56);
            l = l >>> 8 ^ arrl[0] ^ l3 ^ l3 >>> 1 ^ l3 >>> 2 ^ l3 >>> 7;
        }
        Pack.longToBigEndian(l, arrby, 0);
        Pack.longToBigEndian(l2, arrby, 8);
    }
}

