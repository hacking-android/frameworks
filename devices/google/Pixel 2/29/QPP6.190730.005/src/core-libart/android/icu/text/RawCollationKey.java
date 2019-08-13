/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.util.ByteArrayWrapper;

public final class RawCollationKey
extends ByteArrayWrapper {
    public RawCollationKey() {
    }

    public RawCollationKey(int n) {
        this.bytes = new byte[n];
    }

    public RawCollationKey(byte[] arrby) {
        this.bytes = arrby;
    }

    public RawCollationKey(byte[] arrby, int n) {
        super(arrby, n);
    }

    @Override
    public int compareTo(RawCollationKey rawCollationKey) {
        int n = super.compareTo(rawCollationKey);
        n = n < 0 ? -1 : (n == 0 ? 0 : 1);
        return n;
    }
}

