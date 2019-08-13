/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Segment;
import com.android.okhttp.okio.Util;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

final class SegmentedByteString
extends ByteString {
    final transient int[] directory;
    final transient byte[][] segments;

    SegmentedByteString(Buffer object, int n) {
        super(null);
        Util.checkOffsetAndCount(((Buffer)object).size, 0L, n);
        int n2 = 0;
        int n3 = 0;
        int[] arrn = ((Buffer)object).head;
        while (n2 < n) {
            if (arrn.limit != arrn.pos) {
                n2 += arrn.limit - arrn.pos;
                ++n3;
                arrn = arrn.next;
                continue;
            }
            throw new AssertionError((Object)"s.limit == s.pos");
        }
        this.segments = new byte[n3][];
        this.directory = new int[n3 * 2];
        n2 = 0;
        n3 = 0;
        object = ((Buffer)object).head;
        while (n2 < n) {
            this.segments[n3] = ((Segment)object).data;
            arrn = this.directory;
            arrn[n3] = n2 += ((Segment)object).limit - ((Segment)object).pos;
            arrn[this.segments.length + n3] = ((Segment)object).pos;
            ((Segment)object).shared = true;
            ++n3;
            object = ((Segment)object).next;
        }
    }

    private int segment(int n) {
        if ((n = Arrays.binarySearch(this.directory, 0, this.segments.length, n + 1)) < 0) {
            // empty if block
        }
        return n;
    }

    private ByteString toByteString() {
        return new ByteString(this.toByteArray());
    }

    private Object writeReplace() {
        return this.toByteString();
    }

    @Override
    public String base64() {
        return this.toByteString().base64();
    }

    @Override
    public String base64Url() {
        return this.toByteString().base64Url();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByteString) || ((ByteString)object).size() != this.size() || !this.rangeEquals(0, (ByteString)object, 0, this.size())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public byte getByte(int n) {
        Util.checkOffsetAndCount(this.directory[this.segments.length - 1], n, 1L);
        int n2 = this.segment(n);
        int n3 = n2 == 0 ? 0 : this.directory[n2 - 1];
        int[] arrn = this.directory;
        byte[][] arrby = this.segments;
        int n4 = arrn[arrby.length + n2];
        return arrby[n2][n - n3 + n4];
    }

    @Override
    public int hashCode() {
        int n = this.hashCode;
        if (n != 0) {
            return n;
        }
        int n2 = 1;
        int n3 = 0;
        int n4 = this.segments.length;
        for (n = 0; n < n4; ++n) {
            byte[] arrby = this.segments[n];
            int[] arrn = this.directory;
            int n5 = arrn[n4 + n];
            int n6 = arrn[n];
            for (int i = n5; i < n5 + (n6 - n3); ++i) {
                n2 = n2 * 31 + arrby[i];
            }
            n3 = n6;
        }
        this.hashCode = n2;
        return n2;
    }

    @Override
    public String hex() {
        return this.toByteString().hex();
    }

    @Override
    public ByteString md5() {
        return this.toByteString().md5();
    }

    @Override
    public boolean rangeEquals(int n, ByteString byteString, int n2, int n3) {
        if (n > this.size() - n3) {
            return false;
        }
        int n4 = this.segment(n);
        while (n3 > 0) {
            int n5;
            int[] arrn;
            int n6;
            byte[][] arrby = this.segments;
            int n7 = n4 == 0 ? 0 : this.directory[n4 - 1];
            if (!byteString.rangeEquals(n2, arrby[n4], n - n7 + (n5 = (arrn = this.directory)[arrby.length + n4]), n6 = Math.min(n3, n7 + (this.directory[n4] - n7) - n))) {
                return false;
            }
            n += n6;
            n2 += n6;
            n3 -= n6;
            ++n4;
        }
        return true;
    }

    @Override
    public boolean rangeEquals(int n, byte[] arrby, int n2, int n3) {
        if (n <= this.size() - n3 && n2 <= arrby.length - n3) {
            int n4 = this.segment(n);
            while (n3 > 0) {
                int n5;
                int[] arrn;
                int n6;
                byte[][] arrby2 = this.segments;
                int n7 = n4 == 0 ? 0 : this.directory[n4 - 1];
                if (!Util.arrayRangeEquals(arrby2[n4], n - n7 + (n5 = (arrn = this.directory)[arrby2.length + n4]), arrby, n2, n6 = Math.min(n3, n7 + (this.directory[n4] - n7) - n))) {
                    return false;
                }
                n += n6;
                n2 += n6;
                n3 -= n6;
                ++n4;
            }
            return true;
        }
        return false;
    }

    @Override
    public ByteString sha256() {
        return this.toByteString().sha256();
    }

    @Override
    public int size() {
        return this.directory[this.segments.length - 1];
    }

    @Override
    public ByteString substring(int n) {
        return this.toByteString().substring(n);
    }

    @Override
    public ByteString substring(int n, int n2) {
        return this.toByteString().substring(n, n2);
    }

    @Override
    public ByteString toAsciiLowercase() {
        return this.toByteString().toAsciiLowercase();
    }

    @Override
    public ByteString toAsciiUppercase() {
        return this.toByteString().toAsciiUppercase();
    }

    @Override
    public byte[] toByteArray() {
        int[] arrn = this.directory;
        byte[][] arrby = this.segments;
        arrn = new byte[arrn[arrby.length - 1]];
        Object object = 0;
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            arrby = this.directory;
            byte[] arrby2 = arrby[n + i];
            byte[] arrby3 = arrby[i];
            System.arraycopy((byte[])this.segments[i], (int)arrby2, (byte[])arrn, (int)object, (int)(arrby3 - object));
            object = arrby3;
        }
        return arrn;
    }

    @Override
    public String toString() {
        return this.toByteString().toString();
    }

    @Override
    public String utf8() {
        return this.toByteString().utf8();
    }

    @Override
    void write(Buffer buffer) {
        int n = 0;
        int n2 = this.segments.length;
        for (int i = 0; i < n2; ++i) {
            Object object = this.directory;
            int n3 = object[n2 + i];
            int n4 = object[i];
            object = new Segment(this.segments[i], n3, n3 + n4 - n);
            if (buffer.head == null) {
                object.prev = object;
                object.next = object;
                buffer.head = object;
            } else {
                buffer.head.prev.push((Segment)object);
            }
            n = n4;
        }
        buffer.size += (long)n;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            int n = 0;
            int n2 = this.segments.length;
            for (int i = 0; i < n2; ++i) {
                int[] arrn = this.directory;
                int n3 = arrn[n2 + i];
                int n4 = arrn[i];
                outputStream.write(this.segments[i], n3, n4 - n);
                n = n4;
            }
            return;
        }
        throw new IllegalArgumentException("out == null");
    }
}

