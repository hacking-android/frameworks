/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes.gcm;

import com.android.org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMUtil;
import com.android.org.bouncycastle.util.Arrays;
import java.util.Vector;

public class Tables1kGCMExponentiator
implements GCMExponentiator {
    private Vector lookupPowX2;

    private void ensureAvailable(int n) {
        int n2 = this.lookupPowX2.size();
        if (n2 <= n) {
            int n3;
            long[] arrl = (long[])this.lookupPowX2.elementAt(n2 - 1);
            do {
                arrl = Arrays.clone(arrl);
                GCMUtil.square(arrl, arrl);
                this.lookupPowX2.addElement(arrl);
                n2 = n3 = n2 + 1;
            } while (n3 <= n);
        }
    }

    @Override
    public void exponentiateX(long l, byte[] arrby) {
        long[] arrl = GCMUtil.oneAsLongs();
        int n = 0;
        while (l > 0L) {
            if ((1L & l) != 0L) {
                this.ensureAvailable(n);
                GCMUtil.multiply(arrl, (long[])this.lookupPowX2.elementAt(n));
            }
            ++n;
            l >>>= 1;
        }
        GCMUtil.asBytes(arrl, arrby);
    }

    @Override
    public void init(byte[] arrby) {
        arrby = GCMUtil.asLongs(arrby);
        Vector vector = this.lookupPowX2;
        if (vector != null && Arrays.areEqual((long[])arrby, (long[])vector.elementAt(0))) {
            return;
        }
        this.lookupPowX2 = new Vector(8);
        this.lookupPowX2.addElement(arrby);
    }
}

