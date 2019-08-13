/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.util.Arrays;

final class ByteArray {
    private final byte[] bytes;
    private final int hashCode;

    ByteArray(byte[] arrby) {
        this.bytes = arrby;
        this.hashCode = Arrays.hashCode(arrby);
    }

    public boolean equals(Object object) {
        if (!(object instanceof ByteArray)) {
            return false;
        }
        object = (ByteArray)object;
        return Arrays.equals(this.bytes, ((ByteArray)object).bytes);
    }

    public int hashCode() {
        return this.hashCode;
    }
}

