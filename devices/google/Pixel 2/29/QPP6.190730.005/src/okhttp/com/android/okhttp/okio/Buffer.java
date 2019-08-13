/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Segment;
import com.android.okhttp.okio.SegmentPool;
import com.android.okhttp.okio.SegmentedByteString;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import com.android.okhttp.okio.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Buffer
implements BufferedSource,
BufferedSink,
Cloneable {
    private static final byte[] DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    static final int REPLACEMENT_CHARACTER = 65533;
    Segment head;
    long size;

    private void readFrom(InputStream inputStream, long l, boolean bl) throws IOException {
        if (inputStream != null) {
            do {
                if (l <= 0L && !bl) {
                    return;
                }
                Segment segment = this.writableSegment(1);
                int n = (int)Math.min(l, (long)(8192 - segment.limit));
                if ((n = inputStream.read(segment.data, segment.limit, n)) == -1) {
                    if (bl) {
                        return;
                    }
                    throw new EOFException();
                }
                segment.limit += n;
                this.size += (long)n;
                l -= (long)n;
            } while (true);
        }
        throw new IllegalArgumentException("in == null");
    }

    @Override
    public Buffer buffer() {
        return this;
    }

    public void clear() {
        try {
            this.skip(this.size);
            return;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    public Buffer clone() {
        Segment segment;
        Buffer buffer = new Buffer();
        if (this.size == 0L) {
            return buffer;
        }
        segment.prev = segment = (buffer.head = new Segment(this.head));
        segment.next = segment;
        segment = this.head.next;
        while (segment != this.head) {
            buffer.head.prev.push(new Segment(segment));
            segment = segment.next;
        }
        buffer.size = this.size;
        return buffer;
    }

    @Override
    public void close() {
    }

    public long completeSegmentByteCount() {
        long l = this.size;
        if (l == 0L) {
            return 0L;
        }
        Segment segment = this.head.prev;
        long l2 = l;
        if (segment.limit < 8192) {
            l2 = l;
            if (segment.owner) {
                l2 = l - (long)(segment.limit - segment.pos);
            }
        }
        return l2;
    }

    public Buffer copyTo(Buffer buffer, long l, long l2) {
        if (buffer != null) {
            long l3;
            Segment segment;
            Util.checkOffsetAndCount(this.size, l, l2);
            if (l2 == 0L) {
                return this;
            }
            buffer.size += l2;
            Segment segment2 = this.head;
            do {
                segment = segment2;
                l3 = l;
                if (l < (long)(segment2.limit - segment2.pos)) break;
                l -= (long)(segment2.limit - segment2.pos);
                segment2 = segment2.next;
            } while (true);
            for (long i = l2; i > 0L; i -= (long)(segment2.limit - segment2.pos)) {
                segment2 = new Segment(segment);
                segment2.pos = (int)((long)segment2.pos + l3);
                segment2.limit = Math.min(segment2.pos + (int)i, segment2.limit);
                Segment segment3 = buffer.head;
                if (segment3 == null) {
                    segment2.prev = segment2;
                    segment2.next = segment2;
                    buffer.head = segment2;
                } else {
                    segment3.prev.push(segment2);
                }
                l3 = 0L;
                segment = segment.next;
            }
            return this;
        }
        throw new IllegalArgumentException("out == null");
    }

    public Buffer copyTo(OutputStream outputStream) throws IOException {
        return this.copyTo(outputStream, 0L, this.size);
    }

    public Buffer copyTo(OutputStream outputStream, long l, long l2) throws IOException {
        if (outputStream != null) {
            long l3;
            int n;
            Segment segment;
            Util.checkOffsetAndCount(this.size, l, l2);
            if (l2 == 0L) {
                return this;
            }
            Segment segment2 = this.head;
            do {
                segment = segment2;
                l3 = l;
                if (l < (long)(segment2.limit - segment2.pos)) break;
                l -= (long)(segment2.limit - segment2.pos);
                segment2 = segment2.next;
            } while (true);
            for (long i = l2; i > 0L; i -= (long)n) {
                int n2 = (int)((long)segment.pos + l3);
                n = (int)Math.min((long)(segment.limit - n2), i);
                outputStream.write(segment.data, n2, n);
                l3 = 0L;
                segment = segment.next;
            }
            return this;
        }
        throw new IllegalArgumentException("out == null");
    }

    @Override
    public BufferedSink emit() {
        return this;
    }

    @Override
    public Buffer emitCompleteSegments() {
        return this;
    }

    public boolean equals(Object object) {
        long l;
        if (this == object) {
            return true;
        }
        if (!(object instanceof Buffer)) {
            return false;
        }
        object = (Buffer)object;
        long l2 = this.size;
        if (l2 != ((Buffer)object).size) {
            return false;
        }
        if (l2 == 0L) {
            return true;
        }
        Segment segment = this.head;
        object = ((Buffer)object).head;
        int n = segment.pos;
        int n2 = ((Segment)object).pos;
        for (l2 = 0L; l2 < this.size; l2 += l) {
            l = Math.min(segment.limit - n, ((Segment)object).limit - n2);
            int n3 = 0;
            while ((long)n3 < l) {
                if (segment.data[n] != ((Segment)object).data[n2]) {
                    return false;
                }
                ++n3;
                ++n;
                ++n2;
            }
            Segment segment2 = segment;
            n3 = n;
            if (n == segment.limit) {
                segment2 = segment.next;
                n3 = segment2.pos;
            }
            Object object2 = object;
            int n4 = n2;
            if (n2 == ((Segment)object).limit) {
                object2 = ((Segment)object).next;
                n4 = ((Segment)object2).pos;
            }
            segment = segment2;
            object = object2;
            n = n3;
            n2 = n4;
        }
        return true;
    }

    @Override
    public boolean exhausted() {
        boolean bl = this.size == 0L;
        return bl;
    }

    @Override
    public void flush() {
    }

    public byte getByte(long l) {
        Util.checkOffsetAndCount(this.size, l, 1L);
        Segment segment = this.head;
        int n;
        while (l >= (long)(n = segment.limit - segment.pos)) {
            l -= (long)n;
            segment = segment.next;
        }
        return segment.data[segment.pos + (int)l];
    }

    public int hashCode() {
        Segment segment = this.head;
        if (segment == null) {
            return 0;
        }
        int n = 1;
        do {
            int n2 = segment.limit;
            for (int i = segment.pos; i < n2; ++i) {
                n = n * 31 + segment.data[i];
            }
        } while ((segment = segment.next) != this.head);
        return n;
    }

    @Override
    public long indexOf(byte by) {
        return this.indexOf(by, 0L);
    }

    @Override
    public long indexOf(byte by, long l) {
        if (l >= 0L) {
            Object object;
            Object object2 = this.head;
            if (object2 == null) {
                return -1L;
            }
            long l2 = 0L;
            do {
                int n;
                if (l >= (long)(n = object2.limit - object2.pos)) {
                    l -= (long)n;
                } else {
                    object = object2.data;
                    int n2 = object2.limit;
                    for (int i = (int)((long)object2.pos + l); i < n2; ++i) {
                        if (object[i] != by) continue;
                        return (long)i + l2 - (long)object2.pos;
                    }
                    l = 0L;
                }
                l2 += (long)n;
                object2 = object = object2.next;
            } while (object != this.head);
            return -1L;
        }
        throw new IllegalArgumentException("fromIndex < 0");
    }

    @Override
    public long indexOf(ByteString byteString) throws IOException {
        return this.indexOf(byteString, 0L);
    }

    @Override
    public long indexOf(ByteString byteString, long l) throws IOException {
        if (byteString.size() != 0) {
            do {
                if ((l = this.indexOf(byteString.getByte(0), l)) == -1L) {
                    return -1L;
                }
                if (this.rangeEquals(l, byteString)) {
                    return l;
                }
                ++l;
            } while (true);
        }
        throw new IllegalArgumentException("bytes is empty");
    }

    @Override
    public long indexOfElement(ByteString byteString) {
        return this.indexOfElement(byteString, 0L);
    }

    @Override
    public long indexOfElement(ByteString object, long l) {
        if (l >= 0L) {
            byte[] arrby = this.head;
            if (arrby == null) {
                return -1L;
            }
            byte[] arrby2 = ((ByteString)object).toByteArray();
            long l2 = 0L;
            object = arrby;
            do {
                int n;
                if (l >= (long)(n = ((Segment)object).limit - ((Segment)object).pos)) {
                    l -= (long)n;
                } else {
                    arrby = ((Segment)object).data;
                    long l3 = ((Segment)object).limit;
                    for (l = (long)object.pos + l; l < l3; ++l) {
                        byte by = arrby[(int)l];
                        int n2 = arrby2.length;
                        for (int i = 0; i < n2; ++i) {
                            if (by != arrby2[i]) continue;
                            return l2 + l - (long)((Segment)object).pos;
                        }
                    }
                    l = 0L;
                }
                l2 += (long)n;
            } while ((object = ((Segment)object).next) != this.head);
            return -1L;
        }
        throw new IllegalArgumentException("fromIndex < 0");
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(){

            @Override
            public int available() {
                return (int)Math.min(Buffer.this.size, Integer.MAX_VALUE);
            }

            @Override
            public void close() {
            }

            @Override
            public int read() {
                if (Buffer.this.size > 0L) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            @Override
            public int read(byte[] arrby, int n, int n2) {
                return Buffer.this.read(arrby, n, n2);
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Buffer.this);
                stringBuilder.append(".inputStream()");
                return stringBuilder.toString();
            }
        };
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(){

            @Override
            public void close() {
            }

            @Override
            public void flush() {
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this);
                stringBuilder.append(".outputStream()");
                return stringBuilder.toString();
            }

            @Override
            public void write(int n) {
                Buffer.this.writeByte((byte)n);
            }

            @Override
            public void write(byte[] arrby, int n, int n2) {
                Buffer.this.write(arrby, n, n2);
            }
        };
    }

    boolean rangeEquals(long l, ByteString byteString) {
        int n = byteString.size();
        if (this.size - l < (long)n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.getByte((long)i + l) == byteString.getByte(i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int read(byte[] arrby) {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) {
        Util.checkOffsetAndCount(arrby.length, n, n2);
        Segment segment = this.head;
        if (segment == null) {
            return -1;
        }
        n2 = Math.min(n2, segment.limit - segment.pos);
        System.arraycopy((byte[])segment.data, (int)segment.pos, (byte[])arrby, (int)n, (int)n2);
        segment.pos += n2;
        this.size -= (long)n2;
        if (segment.pos == segment.limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return n2;
    }

    @Override
    public long read(Buffer object, long l) {
        if (object != null) {
            if (l >= 0L) {
                long l2 = this.size;
                if (l2 == 0L) {
                    return -1L;
                }
                long l3 = l;
                if (l > l2) {
                    l3 = this.size;
                }
                ((Buffer)object).write(this, l3);
                return l3;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("sink == null");
    }

    @Override
    public long readAll(Sink sink) throws IOException {
        long l = this.size;
        if (l > 0L) {
            sink.write(this, l);
        }
        return l;
    }

    @Override
    public byte readByte() {
        if (this.size != 0L) {
            Segment segment = this.head;
            int n = segment.pos;
            int n2 = segment.limit;
            byte[] arrby = segment.data;
            int n3 = n + 1;
            byte by = arrby[n];
            --this.size;
            if (n3 == n2) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = n3;
            }
            return by;
        }
        throw new IllegalStateException("size == 0");
    }

    @Override
    public byte[] readByteArray() {
        try {
            byte[] arrby = this.readByteArray(this.size);
            return arrby;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    @Override
    public byte[] readByteArray(long l) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, l);
        if (l <= Integer.MAX_VALUE) {
            byte[] arrby = new byte[(int)l];
            this.readFully(arrby);
            return arrby;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("byteCount > Integer.MAX_VALUE: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public ByteString readByteString() {
        return new ByteString(this.readByteArray());
    }

    @Override
    public ByteString readByteString(long l) throws EOFException {
        return new ByteString(this.readByteArray(l));
    }

    @Override
    public long readDecimalLong() {
        block8 : {
            if (this.size == 0L) break block8;
            long l = 0L;
            int n = 0;
            boolean bl = false;
            boolean bl2 = false;
            long l2 = -922337203685477580L;
            long l3 = -7L;
            do {
                Object object = this.head;
                Object object2 = ((Segment)object).data;
                int n2 = ((Segment)object).pos;
                int n3 = ((Segment)object).limit;
                while (n2 < n3) {
                    byte by;
                    block12 : {
                        block11 : {
                            block9 : {
                                block10 : {
                                    by = object2[n2];
                                    if (by < 48 || by > 57) break block9;
                                    int n4 = 48 - by;
                                    if (l < l2 || l == l2 && (long)n4 < l3) break block10;
                                    l = l * 10L + (long)n4;
                                    break block11;
                                }
                                object = new Buffer().writeDecimalLong(l).writeByte(by);
                                if (!bl) {
                                    ((Buffer)object).readByte();
                                }
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Number too large: ");
                                ((StringBuilder)object2).append(((Buffer)object).readUtf8());
                                throw new NumberFormatException(((StringBuilder)object2).toString());
                            }
                            if (by != 45 || n != 0) break block12;
                            bl = true;
                            --l3;
                        }
                        ++n2;
                        ++n;
                        continue;
                    }
                    if (n != 0) {
                        bl2 = true;
                        break;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Expected leading [0-9] or '-' character but was 0x");
                    ((StringBuilder)object2).append(Integer.toHexString(by));
                    throw new NumberFormatException(((StringBuilder)object2).toString());
                }
                if (n2 == n3) {
                    this.head = ((Segment)object).pop();
                    SegmentPool.recycle((Segment)object);
                    continue;
                }
                ((Segment)object).pos = n2;
            } while (!bl2 && this.head != null);
            this.size -= (long)n;
            l2 = bl ? l : -l;
            return l2;
        }
        throw new IllegalStateException("size == 0");
    }

    public Buffer readFrom(InputStream inputStream) throws IOException {
        this.readFrom(inputStream, Long.MAX_VALUE, true);
        return this;
    }

    public Buffer readFrom(InputStream object, long l) throws IOException {
        if (l >= 0L) {
            this.readFrom((InputStream)object, l, false);
            return this;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("byteCount < 0: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public void readFully(Buffer buffer, long l) throws EOFException {
        long l2 = this.size;
        if (l2 >= l) {
            buffer.write(this, l);
            return;
        }
        buffer.write(this, l2);
        throw new EOFException();
    }

    @Override
    public void readFully(byte[] arrby) throws EOFException {
        int n;
        for (int i = 0; i < arrby.length; i += n) {
            n = this.read(arrby, i, arrby.length - i);
            if (n != -1) {
                continue;
            }
            throw new EOFException();
        }
    }

    @Override
    public long readHexadecimalUnsignedLong() {
        if (this.size != 0L) {
            long l;
            int n;
            long l2 = 0L;
            int n2 = 0;
            byte by = 0;
            do {
                int n3;
                byte by2;
                int n4;
                Object object;
                block11 : {
                    Object object2;
                    block12 : {
                        object = this.head;
                        object2 = ((Segment)object).data;
                        n3 = ((Segment)object).pos;
                        n4 = ((Segment)object).limit;
                        n = n2;
                        l = l2;
                        do {
                            by2 = by;
                            if (n3 >= n4) break block11;
                            by2 = object2[n3];
                            if (by2 >= 48 && by2 <= 57) {
                                n2 = by2 - 48;
                            } else if (by2 >= 97 && by2 <= 102) {
                                n2 = by2 - 97 + 10;
                            } else {
                                if (by2 < 65 || by2 > 70) break block12;
                                n2 = by2 - 65 + 10;
                            }
                            if ((-1152921504606846976L & l) != 0L) break;
                            l = l << 4 | (long)n2;
                            ++n3;
                            ++n;
                        } while (true);
                        object2 = new Buffer().writeHexadecimalUnsignedLong(l).writeByte(by2);
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Number too large: ");
                        ((StringBuilder)object).append(((Buffer)object2).readUtf8());
                        throw new NumberFormatException(((StringBuilder)object).toString());
                    }
                    if (n != 0) {
                        by2 = 1;
                    } else {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected leading [0-9a-fA-F] character but was 0x");
                        ((StringBuilder)object2).append(Integer.toHexString(by2));
                        throw new NumberFormatException(((StringBuilder)object2).toString());
                    }
                }
                if (n3 == n4) {
                    this.head = ((Segment)object).pop();
                    SegmentPool.recycle((Segment)object);
                } else {
                    ((Segment)object).pos = n3;
                }
                if (by2 != 0) break;
                l2 = l;
                n2 = n;
                by = by2;
            } while (this.head != null);
            this.size -= (long)n;
            return l;
        }
        throw new IllegalStateException("size == 0");
    }

    @Override
    public int readInt() {
        if (this.size >= 4L) {
            Segment segment = this.head;
            int n = segment.limit;
            int n2 = segment.pos;
            if (n - n2 < 4) {
                return (this.readByte() & 255) << 24 | (this.readByte() & 255) << 16 | (this.readByte() & 255) << 8 | this.readByte() & 255;
            }
            byte[] arrby = segment.data;
            int n3 = n2 + 1;
            n2 = arrby[n2];
            int n4 = n3 + 1;
            n3 = arrby[n3];
            int n5 = n4 + 1;
            n4 = arrby[n4];
            int n6 = n5 + 1;
            n5 = arrby[n5];
            this.size -= 4L;
            if (n6 == n) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = n6;
            }
            return (n2 & 255) << 24 | (n3 & 255) << 16 | (n4 & 255) << 8 | n5 & 255;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size < 4: ");
        stringBuilder.append(this.size);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public int readIntLe() {
        return Util.reverseBytesInt(this.readInt());
    }

    @Override
    public long readLong() {
        if (this.size >= 8L) {
            Segment segment = this.head;
            int n = segment.limit;
            int n2 = segment.pos;
            if (n - n2 < 8) {
                return ((long)this.readInt() & 0xFFFFFFFFL) << 32 | (long)this.readInt() & 0xFFFFFFFFL;
            }
            byte[] arrby = segment.data;
            int n3 = n2 + 1;
            long l = arrby[n2];
            n2 = n3 + 1;
            long l2 = arrby[n3];
            n3 = n2 + 1;
            long l3 = arrby[n2];
            n2 = n3 + 1;
            long l4 = arrby[n3];
            n3 = n2 + 1;
            long l5 = arrby[n2];
            n2 = n3 + 1;
            long l6 = arrby[n3];
            n3 = n2 + 1;
            long l7 = arrby[n2];
            n2 = n3 + 1;
            long l8 = arrby[n3];
            this.size -= 8L;
            if (n2 == n) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = n2;
            }
            return (l & 255L) << 56 | (l2 & 255L) << 48 | (l3 & 255L) << 40 | (l4 & 255L) << 32 | (l5 & 255L) << 24 | (l6 & 255L) << 16 | (l7 & 255L) << 8 | l8 & 255L;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size < 8: ");
        stringBuilder.append(this.size);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public long readLongLe() {
        return Util.reverseBytesLong(this.readLong());
    }

    @Override
    public short readShort() {
        if (this.size >= 2L) {
            Segment segment = this.head;
            int n = segment.limit;
            int n2 = segment.pos;
            if (n - n2 < 2) {
                return (short)((this.readByte() & 255) << 8 | this.readByte() & 255);
            }
            byte[] arrby = segment.data;
            int n3 = n2 + 1;
            byte by = arrby[n2];
            n2 = n3 + 1;
            n3 = arrby[n3];
            this.size -= 2L;
            if (n2 == n) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = n2;
            }
            return (short)((by & 255) << 8 | n3 & 255);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size < 2: ");
        stringBuilder.append(this.size);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public short readShortLe() {
        return Util.reverseBytesShort(this.readShort());
    }

    @Override
    public String readString(long l, Charset object) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, l);
        if (object != null) {
            if (l <= Integer.MAX_VALUE) {
                if (l == 0L) {
                    return "";
                }
                Segment segment = this.head;
                if ((long)segment.pos + l > (long)segment.limit) {
                    return new String(this.readByteArray(l), (Charset)object);
                }
                object = new String(segment.data, segment.pos, (int)l, (Charset)object);
                segment.pos = (int)((long)segment.pos + l);
                this.size -= l;
                if (segment.pos == segment.limit) {
                    this.head = segment.pop();
                    SegmentPool.recycle(segment);
                }
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount > Integer.MAX_VALUE: ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override
    public String readString(Charset object) {
        try {
            object = this.readString(this.size, (Charset)object);
            return object;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    @Override
    public String readUtf8() {
        try {
            String string = this.readString(this.size, Util.UTF_8);
            return string;
        }
        catch (EOFException eOFException) {
            throw new AssertionError(eOFException);
        }
    }

    @Override
    public String readUtf8(long l) throws EOFException {
        return this.readString(l, Util.UTF_8);
    }

    @Override
    public int readUtf8CodePoint() throws EOFException {
        block9 : {
            block14 : {
                int n;
                int n2;
                int n3;
                int n4;
                block11 : {
                    block13 : {
                        block12 : {
                            block10 : {
                                if (this.size == 0L) break block9;
                                n2 = this.getByte(0L);
                                if ((n2 & 128) != 0) break block10;
                                n4 = n2 & 127;
                                n = 1;
                                n3 = 0;
                                break block11;
                            }
                            if ((n2 & 224) != 192) break block12;
                            n4 = n2 & 31;
                            n = 2;
                            n3 = 128;
                            break block11;
                        }
                        if ((n2 & 240) != 224) break block13;
                        n4 = n2 & 15;
                        n = 3;
                        n3 = 2048;
                        break block11;
                    }
                    if ((n2 & 248) != 240) break block14;
                    n4 = n2 & 7;
                    n = 4;
                    n3 = 65536;
                }
                if (this.size >= (long)n) {
                    for (n2 = 1; n2 < n; ++n2) {
                        byte by = this.getByte(n2);
                        if ((by & 192) == 128) {
                            n4 = n4 << 6 | by & 63;
                            continue;
                        }
                        this.skip(n2);
                        return 65533;
                    }
                    this.skip(n);
                    if (n4 > 1114111) {
                        return 65533;
                    }
                    if (n4 >= 55296 && n4 <= 57343) {
                        return 65533;
                    }
                    if (n4 < n3) {
                        return 65533;
                    }
                    return n4;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("size < ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(this.size);
                stringBuilder.append(" (to read code point prefixed 0x");
                stringBuilder.append(Integer.toHexString(n2));
                stringBuilder.append(")");
                throw new EOFException(stringBuilder.toString());
            }
            this.skip(1L);
            return 65533;
        }
        throw new EOFException();
    }

    @Override
    public String readUtf8Line() throws EOFException {
        long l = this.indexOf((byte)10);
        if (l == -1L) {
            l = this.size;
            String string = l != 0L ? this.readUtf8(l) : null;
            return string;
        }
        return this.readUtf8Line(l);
    }

    String readUtf8Line(long l) throws EOFException {
        if (l > 0L && this.getByte(l - 1L) == 13) {
            String string = this.readUtf8(l - 1L);
            this.skip(2L);
            return string;
        }
        String string = this.readUtf8(l);
        this.skip(1L);
        return string;
    }

    @Override
    public String readUtf8LineStrict() throws EOFException {
        long l = this.indexOf((byte)10);
        if (l != -1L) {
            return this.readUtf8Line(l);
        }
        Buffer buffer = new Buffer();
        this.copyTo(buffer, 0L, Math.min(32L, this.size));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\n not found: size=");
        stringBuilder.append(this.size());
        stringBuilder.append(" content=");
        stringBuilder.append(buffer.readByteString().hex());
        stringBuilder.append("...");
        throw new EOFException(stringBuilder.toString());
    }

    @Override
    public boolean request(long l) {
        boolean bl = this.size >= l;
        return bl;
    }

    @Override
    public void require(long l) throws EOFException {
        if (this.size >= l) {
            return;
        }
        throw new EOFException();
    }

    List<Integer> segmentSizes() {
        if (this.head == null) {
            return Collections.emptyList();
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(this.head.limit - this.head.pos);
        Segment segment = this.head.next;
        while (segment != this.head) {
            arrayList.add(segment.limit - segment.pos);
            segment = segment.next;
        }
        return arrayList;
    }

    public long size() {
        return this.size;
    }

    @Override
    public void skip(long l) throws EOFException {
        while (l > 0L) {
            Segment segment = this.head;
            if (segment != null) {
                int n = (int)Math.min(l, (long)(segment.limit - this.head.pos));
                this.size -= (long)n;
                l -= (long)n;
                segment = this.head;
                segment.pos += n;
                if (this.head.pos != this.head.limit) continue;
                segment = this.head;
                this.head = segment.pop();
                SegmentPool.recycle(segment);
                continue;
            }
            throw new EOFException();
        }
    }

    public ByteString snapshot() {
        long l = this.size;
        if (l <= Integer.MAX_VALUE) {
            return this.snapshot((int)l);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size > Integer.MAX_VALUE: ");
        stringBuilder.append(this.size);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ByteString snapshot(int n) {
        if (n == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, n);
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public String toString() {
        long l = this.size;
        if (l == 0L) {
            return "Buffer[size=0]";
        }
        if (l <= 16L) {
            ByteString byteString = this.clone().readByteString();
            return String.format("Buffer[size=%s data=%s]", this.size, byteString.hex());
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(this.head.data, this.head.pos, this.head.limit - this.head.pos);
            Object object = this.head.next;
            while (object != this.head) {
                messageDigest.update(((Segment)object).data, ((Segment)object).pos, ((Segment)object).limit - ((Segment)object).pos);
                object = ((Segment)object).next;
            }
            object = String.format("Buffer[size=%s md5=%s]", this.size, ByteString.of(messageDigest.digest()).hex());
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError();
        }
    }

    Segment writableSegment(int n) {
        block4 : {
            Segment segment;
            block6 : {
                Segment segment2;
                block5 : {
                    if (n < 1 || n > 8192) break block4;
                    segment = this.head;
                    if (segment == null) {
                        segment.prev = segment = (this.head = SegmentPool.take());
                        segment.next = segment;
                        return segment;
                    }
                    segment2 = segment.prev;
                    if (segment2.limit + n > 8192) break block5;
                    segment = segment2;
                    if (segment2.owner) break block6;
                }
                segment = segment2.push(SegmentPool.take());
            }
            return segment;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Buffer write(ByteString byteString) {
        if (byteString != null) {
            byteString.write(this);
            return this;
        }
        throw new IllegalArgumentException("byteString == null");
    }

    @Override
    public Buffer write(byte[] arrby) {
        if (arrby != null) {
            return this.write(arrby, 0, arrby.length);
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override
    public Buffer write(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            Util.checkOffsetAndCount(arrby.length, n, n2);
            int n3 = n + n2;
            while (n < n3) {
                Segment segment = this.writableSegment(1);
                int n4 = Math.min(n3 - n, 8192 - segment.limit);
                System.arraycopy((byte[])arrby, (int)n, (byte[])segment.data, (int)segment.limit, (int)n4);
                n += n4;
                segment.limit += n4;
            }
            this.size += (long)n2;
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override
    public BufferedSink write(Source source, long l) throws IOException {
        while (l > 0L) {
            long l2 = source.read(this, l);
            if (l2 != -1L) {
                l -= l2;
                continue;
            }
            throw new EOFException();
        }
        return this;
    }

    @Override
    public void write(Buffer buffer, long l) {
        if (buffer != null) {
            if (buffer != this) {
                Util.checkOffsetAndCount(buffer.size, 0L, l);
                while (l > 0L) {
                    long l2;
                    Segment segment;
                    if (l < (long)(buffer.head.limit - buffer.head.pos)) {
                        int n;
                        segment = this.head;
                        segment = segment != null ? segment.prev : null;
                        if (segment != null && segment.owner && (l2 = (long)segment.limit) + l - (long)(n = segment.shared ? 0 : segment.pos) <= 8192L) {
                            buffer.head.writeTo(segment, (int)l);
                            buffer.size -= l;
                            this.size += l;
                            return;
                        }
                        buffer.head = buffer.head.split((int)l);
                    }
                    Segment segment2 = buffer.head;
                    l2 = segment2.limit - segment2.pos;
                    buffer.head = segment2.pop();
                    segment = this.head;
                    if (segment == null) {
                        segment.prev = segment = (this.head = segment2);
                        segment.next = segment;
                    } else {
                        segment.prev.push(segment2).compact();
                    }
                    buffer.size -= l2;
                    this.size += l2;
                    l -= l2;
                }
                return;
            }
            throw new IllegalArgumentException("source == this");
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override
    public long writeAll(Source source) throws IOException {
        if (source != null) {
            long l;
            long l2 = 0L;
            while ((l = source.read(this, 8192L)) != -1L) {
                l2 += l;
            }
            return l2;
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override
    public Buffer writeByte(int n) {
        Segment segment = this.writableSegment(1);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        segment.limit = n2 + 1;
        arrby[n2] = (byte)n;
        ++this.size;
        return this;
    }

    @Override
    public Buffer writeDecimalLong(long l) {
        if (l == 0L) {
            return this.writeByte(48);
        }
        boolean bl = false;
        long l2 = l;
        if (l < 0L) {
            l2 = -l;
            if (l2 < 0L) {
                return this.writeUtf8("-9223372036854775808");
            }
            bl = true;
        }
        int n = l2 < 100000000L ? (l2 < 10000L ? (l2 < 100L ? (l2 < 10L ? 1 : 2) : (l2 < 1000L ? 3 : 4)) : (l2 < 1000000L ? (l2 < 100000L ? 5 : 6) : (l2 < 10000000L ? 7 : 8))) : (l2 < 1000000000000L ? (l2 < 10000000000L ? (l2 < 1000000000L ? 9 : 10) : (l2 < 100000000000L ? 11 : 12)) : (l2 < 1000000000000000L ? (l2 < 10000000000000L ? 13 : (l2 < 100000000000000L ? 14 : 15)) : (l2 < 100000000000000000L ? (l2 < 10000000000000000L ? 16 : 17) : (l2 < 1000000000000000000L ? 18 : 19))));
        int n2 = n;
        if (bl) {
            n2 = n + 1;
        }
        Segment segment = this.writableSegment(n2);
        byte[] arrby = segment.data;
        n = segment.limit + n2;
        while (l2 != 0L) {
            int n3 = (int)(l2 % 10L);
            arrby[--n] = DIGITS[n3];
            l2 /= 10L;
        }
        if (bl) {
            arrby[n - 1] = (byte)45;
        }
        segment.limit += n2;
        this.size += (long)n2;
        return this;
    }

    @Override
    public Buffer writeHexadecimalUnsignedLong(long l) {
        if (l == 0L) {
            return this.writeByte(48);
        }
        int n = Long.numberOfTrailingZeros(Long.highestOneBit(l)) / 4 + 1;
        Segment segment = this.writableSegment(n);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        for (int i = segment.limit + n - 1; i >= n2; --i) {
            arrby[i] = DIGITS[(int)(15L & l)];
            l >>>= 4;
        }
        segment.limit += n;
        this.size += (long)n;
        return this;
    }

    @Override
    public Buffer writeInt(int n) {
        Segment segment = this.writableSegment(4);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 24 & 255);
        n2 = n3 + 1;
        arrby[n3] = (byte)(n >>> 16 & 255);
        n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 255);
        arrby[n3] = (byte)(n & 255);
        segment.limit = n3 + 1;
        this.size += 4L;
        return this;
    }

    @Override
    public Buffer writeIntLe(int n) {
        return this.writeInt(Util.reverseBytesInt(n));
    }

    @Override
    public Buffer writeLong(long l) {
        Segment segment = this.writableSegment(8);
        byte[] arrby = segment.data;
        int n = segment.limit;
        int n2 = n + 1;
        arrby[n] = (byte)(l >>> 56 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 48 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 40 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 32 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 24 & 255L);
        n = n2 + 1;
        arrby[n2] = (byte)(l >>> 16 & 255L);
        n2 = n + 1;
        arrby[n] = (byte)(l >>> 8 & 255L);
        arrby[n2] = (byte)(l & 255L);
        segment.limit = n2 + 1;
        this.size += 8L;
        return this;
    }

    @Override
    public Buffer writeLongLe(long l) {
        return this.writeLong(Util.reverseBytesLong(l));
    }

    @Override
    public Buffer writeShort(int n) {
        Segment segment = this.writableSegment(2);
        byte[] arrby = segment.data;
        int n2 = segment.limit;
        int n3 = n2 + 1;
        arrby[n2] = (byte)(n >>> 8 & 255);
        arrby[n3] = (byte)(n & 255);
        segment.limit = n3 + 1;
        this.size += 2L;
        return this;
    }

    @Override
    public Buffer writeShortLe(int n) {
        return this.writeShort(Util.reverseBytesShort((short)n));
    }

    @Override
    public Buffer writeString(String charSequence, int n, int n2, Charset object) {
        if (charSequence != null) {
            if (n >= 0) {
                if (n2 >= n) {
                    if (n2 <= ((String)charSequence).length()) {
                        if (object != null) {
                            if (((Charset)object).equals(Util.UTF_8)) {
                                return this.writeUtf8((String)charSequence);
                            }
                            charSequence = ((String)charSequence).substring(n, n2).getBytes((Charset)object);
                            return this.write((byte[])charSequence, 0, ((CharSequence)charSequence).length);
                        }
                        throw new IllegalArgumentException("charset == null");
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("endIndex > string.length: ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" > ");
                    ((StringBuilder)object).append(((String)charSequence).length());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("endIndex < beginIndex: ");
                ((StringBuilder)charSequence).append(n2);
                ((StringBuilder)charSequence).append(" < ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("beginIndex < 0: ");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalAccessError(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException("string == null");
    }

    @Override
    public Buffer writeString(String string, Charset charset) {
        return this.writeString(string, 0, string.length(), charset);
    }

    public Buffer writeTo(OutputStream outputStream) throws IOException {
        return this.writeTo(outputStream, this.size);
    }

    public Buffer writeTo(OutputStream outputStream, long l) throws IOException {
        if (outputStream != null) {
            Util.checkOffsetAndCount(this.size, 0L, l);
            Segment segment = this.head;
            while (l > 0L) {
                int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
                outputStream.write(segment.data, segment.pos, n);
                segment.pos += n;
                this.size -= (long)n;
                l -= (long)n;
                Segment segment2 = segment;
                if (segment.pos == segment.limit) {
                    Segment segment3;
                    segment2 = segment3 = segment.pop();
                    this.head = segment3;
                    SegmentPool.recycle(segment);
                }
                segment = segment2;
            }
            return this;
        }
        throw new IllegalArgumentException("out == null");
    }

    @Override
    public Buffer writeUtf8(String string) {
        return this.writeUtf8(string, 0, string.length());
    }

    @Override
    public Buffer writeUtf8(String charSequence, int n, int n2) {
        if (charSequence != null) {
            if (n >= 0) {
                if (n2 >= n) {
                    if (n2 <= ((String)charSequence).length()) {
                        while (n < n2) {
                            int n3;
                            char c = ((String)charSequence).charAt(n);
                            if (c < '') {
                                Segment segment = this.writableSegment(1);
                                byte[] arrby = segment.data;
                                int n4 = segment.limit - n;
                                int n5 = Math.min(n2, 8192 - n4);
                                n3 = n + 1;
                                arrby[n + n4] = (byte)c;
                                n = n3;
                                while (n < n5 && (n3 = (int)((String)charSequence).charAt(n)) < 128) {
                                    arrby[n + n4] = (byte)n3;
                                    ++n;
                                }
                                n3 = n + n4 - segment.limit;
                                segment.limit += n3;
                                this.size += (long)n3;
                                continue;
                            }
                            if (c < '\u0800') {
                                this.writeByte(c >> 6 | 192);
                                this.writeByte(128 | c & 63);
                                ++n;
                                continue;
                            }
                            if (c >= '\ud800' && c <= '\udfff') {
                                n3 = n + 1 < n2 ? ((String)charSequence).charAt(n + 1) : 0;
                                if (c <= '\udbff' && n3 >= 56320 && n3 <= 57343) {
                                    n3 = ((-55297 & c) << 10 | -56321 & n3) + 65536;
                                    this.writeByte(n3 >> 18 | 240);
                                    this.writeByte(n3 >> 12 & 63 | 128);
                                    this.writeByte(n3 >> 6 & 63 | 128);
                                    this.writeByte(128 | n3 & 63);
                                    n += 2;
                                    continue;
                                }
                                this.writeByte(63);
                                ++n;
                                continue;
                            }
                            this.writeByte(c >> 12 | 224);
                            this.writeByte(c >> 6 & 63 | 128);
                            this.writeByte(128 | c & 63);
                            ++n;
                        }
                        return this;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("endIndex > string.length: ");
                    stringBuilder.append(n2);
                    stringBuilder.append(" > ");
                    stringBuilder.append(((String)charSequence).length());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("endIndex < beginIndex: ");
                ((StringBuilder)charSequence).append(n2);
                ((StringBuilder)charSequence).append(" < ");
                ((StringBuilder)charSequence).append(n);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("beginIndex < 0: ");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalAccessError(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException("string == null");
    }

    @Override
    public Buffer writeUtf8CodePoint(int n) {
        block8 : {
            block5 : {
                block7 : {
                    block6 : {
                        block4 : {
                            if (n >= 128) break block4;
                            this.writeByte(n);
                            break block5;
                        }
                        if (n >= 2048) break block6;
                        this.writeByte(n >> 6 | 192);
                        this.writeByte(128 | n & 63);
                        break block5;
                    }
                    if (n >= 65536) break block7;
                    if (n >= 55296 && n <= 57343) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected code point: ");
                        stringBuilder.append(Integer.toHexString(n));
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    this.writeByte(n >> 12 | 224);
                    this.writeByte(n >> 6 & 63 | 128);
                    this.writeByte(128 | n & 63);
                    break block5;
                }
                if (n > 1114111) break block8;
                this.writeByte(n >> 18 | 240);
                this.writeByte(n >> 12 & 63 | 128);
                this.writeByte(n >> 6 & 63 | 128);
                this.writeByte(128 | n & 63);
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected code point: ");
        stringBuilder.append(Integer.toHexString(n));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

}

