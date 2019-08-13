/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes.gcm;

import com.android.org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMUtil;
import com.android.org.bouncycastle.util.Arrays;

public class BasicGCMExponentiator
implements GCMExponentiator {
    private long[] x;

    @Override
    public void exponentiateX(long l, byte[] arrby) {
        long[] arrl = GCMUtil.oneAsLongs();
        if (l > 0L) {
            long l2;
            long[] arrl2 = Arrays.clone(this.x);
            do {
                if ((1L & l) != 0L) {
                    GCMUtil.multiply(arrl, arrl2);
                }
                GCMUtil.square(arrl2, arrl2);
                l = l2 = l >>> 1;
            } while (l2 > 0L);
        }
        GCMUtil.asBytes(arrl, arrby);
    }

    @Override
    public void init(byte[] arrby) {
        this.x = GCMUtil.asLongs(arrby);
    }
}

