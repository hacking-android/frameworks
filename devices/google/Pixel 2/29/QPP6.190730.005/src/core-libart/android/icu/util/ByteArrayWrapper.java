/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.Utility;
import java.nio.ByteBuffer;

public class ByteArrayWrapper
implements Comparable<ByteArrayWrapper> {
    public byte[] bytes;
    public int size;

    public ByteArrayWrapper() {
    }

    public ByteArrayWrapper(ByteBuffer byteBuffer) {
        int n = this.size = byteBuffer.limit();
        this.bytes = new byte[n];
        byteBuffer.get(this.bytes, 0, n);
    }

    public ByteArrayWrapper(byte[] object, int n) {
        if (!(object == null && n != 0 || n < 0 || object != null && n > ((byte[])object).length)) {
            this.bytes = object;
            this.size = n;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("illegal size: ");
        ((StringBuilder)object).append(n);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    private static final void copyBytes(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        if (n3 < 64) {
            int n4 = n;
            n = n2;
            while (--n3 >= 0) {
                arrby2[n] = arrby[n4];
                ++n4;
                ++n;
            }
        } else {
            System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)n2, (int)n3);
        }
    }

    public final ByteArrayWrapper append(byte[] arrby, int n, int n2) {
        this.ensureCapacity(this.size + (n2 -= n));
        ByteArrayWrapper.copyBytes(arrby, n, this.bytes, this.size, n2);
        this.size += n2;
        return this;
    }

    @Override
    public int compareTo(ByteArrayWrapper byteArrayWrapper) {
        if (this == byteArrayWrapper) {
            return 0;
        }
        int n = this.size;
        int n2 = byteArrayWrapper.size;
        if (n < n2) {
            n2 = n;
        }
        for (n = 0; n < n2; ++n) {
            byte[] arrby = this.bytes;
            byte by = arrby[n];
            byte[] arrby2 = byteArrayWrapper.bytes;
            if (by == arrby2[n]) continue;
            return (arrby[n] & 255) - (arrby2[n] & 255);
        }
        return this.size - byteArrayWrapper.size;
    }

    public ByteArrayWrapper ensureCapacity(int n) {
        byte[] arrby = this.bytes;
        if (arrby == null || arrby.length < n) {
            arrby = new byte[n];
            byte[] arrby2 = this.bytes;
            if (arrby2 != null) {
                ByteArrayWrapper.copyBytes(arrby2, 0, arrby, 0, this.size);
            }
            this.bytes = arrby;
        }
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        int n;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        try {
            object = (ByteArrayWrapper)object;
            if (this.size != ((ByteArrayWrapper)object).size) {
                return false;
            }
            n = 0;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        do {
            if (n >= this.size) return true;
            byte by = this.bytes[n];
            byte by2 = ((ByteArrayWrapper)object).bytes[n];
            if (by != by2) {
                return false;
            }
            ++n;
            continue;
            break;
        } while (true);
    }

    public int hashCode() {
        int n = this.bytes.length;
        for (int i = 0; i < this.size; ++i) {
            n = n * 37 + this.bytes[i];
        }
        return n;
    }

    public final byte[] releaseBytes() {
        byte[] arrby = this.bytes;
        this.bytes = null;
        this.size = 0;
        return arrby;
    }

    public final ByteArrayWrapper set(byte[] arrby, int n, int n2) {
        this.size = 0;
        this.append(arrby, n, n2);
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.size; ++i) {
            if (i != 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(Utility.hex(this.bytes[i] & 255, 2));
        }
        return stringBuilder.toString();
    }
}

