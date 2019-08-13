/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import com.android.okhttp.okio.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

final class RealBufferedSource
implements BufferedSource {
    public final Buffer buffer;
    private boolean closed;
    public final Source source;

    public RealBufferedSource(Source source) {
        this(source, new Buffer());
    }

    public RealBufferedSource(Source source, Buffer buffer) {
        if (source != null) {
            this.buffer = buffer;
            this.source = source;
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    private boolean rangeEquals(long l, ByteString byteString) throws IOException {
        boolean bl = this.request((long)byteString.size() + l) && this.buffer.rangeEquals(l, byteString);
        return bl;
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.source.close();
        this.buffer.clear();
    }

    @Override
    public boolean exhausted() throws IOException {
        if (!this.closed) {
            boolean bl = this.buffer.exhausted() && this.source.read(this.buffer, 8192L) == -1L;
            return bl;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public long indexOf(byte by) throws IOException {
        return this.indexOf(by, 0L);
    }

    @Override
    public long indexOf(byte by, long l) throws IOException {
        if (!this.closed) {
            long l2;
            block3 : {
                do {
                    l2 = l;
                    if (l < this.buffer.size) break block3;
                } while (this.source.read(this.buffer, 8192L) != -1L);
                return -1L;
            }
            while ((l = this.buffer.indexOf(by, l2)) == -1L) {
                l2 = this.buffer.size;
                if (this.source.read(this.buffer, 8192L) != -1L) continue;
                return -1L;
            }
            return l;
        }
        throw new IllegalStateException("closed");
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
    public long indexOfElement(ByteString byteString) throws IOException {
        return this.indexOfElement(byteString, 0L);
    }

    @Override
    public long indexOfElement(ByteString byteString, long l) throws IOException {
        if (!this.closed) {
            long l2;
            block3 : {
                do {
                    l2 = l;
                    if (l < this.buffer.size) break block3;
                } while (this.source.read(this.buffer, 8192L) != -1L);
                return -1L;
            }
            while ((l = this.buffer.indexOfElement(byteString, l2)) == -1L) {
                l2 = this.buffer.size;
                if (this.source.read(this.buffer, 8192L) != -1L) continue;
                return -1L;
            }
            return l;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public InputStream inputStream() {
        return new InputStream(){

            @Override
            public int available() throws IOException {
                if (!RealBufferedSource.this.closed) {
                    return (int)Math.min(RealBufferedSource.this.buffer.size, Integer.MAX_VALUE);
                }
                throw new IOException("closed");
            }

            @Override
            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            @Override
            public int read() throws IOException {
                if (!RealBufferedSource.this.closed) {
                    if (RealBufferedSource.this.buffer.size == 0L && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1L) {
                        return -1;
                    }
                    return RealBufferedSource.this.buffer.readByte() & 255;
                }
                throw new IOException("closed");
            }

            @Override
            public int read(byte[] arrby, int n, int n2) throws IOException {
                if (!RealBufferedSource.this.closed) {
                    Util.checkOffsetAndCount(arrby.length, n, n2);
                    if (RealBufferedSource.this.buffer.size == 0L && RealBufferedSource.this.source.read(RealBufferedSource.this.buffer, 8192L) == -1L) {
                        return -1;
                    }
                    return RealBufferedSource.this.buffer.read(arrby, n, n2);
                }
                throw new IOException("closed");
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(RealBufferedSource.this);
                stringBuilder.append(".inputStream()");
                return stringBuilder.toString();
            }
        };
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        Util.checkOffsetAndCount(arrby.length, n, n2);
        if (this.buffer.size == 0L && this.source.read(this.buffer, 8192L) == -1L) {
            return -1;
        }
        n2 = (int)Math.min((long)n2, this.buffer.size);
        return this.buffer.read(arrby, n, n2);
    }

    @Override
    public long read(Buffer object, long l) throws IOException {
        if (object != null) {
            if (l >= 0L) {
                if (!this.closed) {
                    if (this.buffer.size == 0L && this.source.read(this.buffer, 8192L) == -1L) {
                        return -1L;
                    }
                    l = Math.min(l, this.buffer.size);
                    return this.buffer.read((Buffer)object, l);
                }
                throw new IllegalStateException("closed");
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
        if (sink != null) {
            long l;
            long l2 = 0L;
            while (this.source.read(this.buffer, 8192L) != -1L) {
                long l3 = this.buffer.completeSegmentByteCount();
                l = l2;
                if (l3 > 0L) {
                    l = l2 + l3;
                    sink.write(this.buffer, l3);
                }
                l2 = l;
            }
            l = l2;
            if (this.buffer.size() > 0L) {
                l = l2 + this.buffer.size();
                Buffer buffer = this.buffer;
                sink.write(buffer, buffer.size());
            }
            return l;
        }
        throw new IllegalArgumentException("sink == null");
    }

    @Override
    public byte readByte() throws IOException {
        this.require(1L);
        return this.buffer.readByte();
    }

    @Override
    public byte[] readByteArray() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteArray();
    }

    @Override
    public byte[] readByteArray(long l) throws IOException {
        this.require(l);
        return this.buffer.readByteArray(l);
    }

    @Override
    public ByteString readByteString() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteString();
    }

    @Override
    public ByteString readByteString(long l) throws IOException {
        this.require(l);
        return this.buffer.readByteString(l);
    }

    @Override
    public long readDecimalLong() throws IOException {
        this.require(1L);
        int n = 0;
        while (this.request(n + 1)) {
            byte by = this.buffer.getByte(n);
            if (by >= 48 && by <= 57 || n == 0 && by == 45) {
                ++n;
                continue;
            }
            if (n != 0) break;
            throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", by));
        }
        return this.buffer.readDecimalLong();
    }

    @Override
    public void readFully(Buffer buffer, long l) throws IOException {
        try {
            this.require(l);
        }
        catch (EOFException eOFException) {
            buffer.writeAll(this.buffer);
            throw eOFException;
        }
        this.buffer.readFully(buffer, l);
    }

    @Override
    public void readFully(byte[] arrby) throws IOException {
        try {
            this.require(arrby.length);
        }
        catch (EOFException eOFException) {
            int n = 0;
            while (this.buffer.size > 0L) {
                Buffer buffer = this.buffer;
                int n2 = buffer.read(arrby, n, (int)buffer.size);
                if (n2 != -1) {
                    n += n2;
                    continue;
                }
                throw new AssertionError();
            }
            throw eOFException;
        }
        this.buffer.readFully(arrby);
    }

    @Override
    public long readHexadecimalUnsignedLong() throws IOException {
        this.require(1L);
        int n = 0;
        while (this.request(n + 1)) {
            byte by = this.buffer.getByte(n);
            if (by >= 48 && by <= 57 || by >= 97 && by <= 102 || by >= 65 && by <= 70) {
                ++n;
                continue;
            }
            if (n != 0) break;
            throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", by));
        }
        return this.buffer.readHexadecimalUnsignedLong();
    }

    @Override
    public int readInt() throws IOException {
        this.require(4L);
        return this.buffer.readInt();
    }

    @Override
    public int readIntLe() throws IOException {
        this.require(4L);
        return this.buffer.readIntLe();
    }

    @Override
    public long readLong() throws IOException {
        this.require(8L);
        return this.buffer.readLong();
    }

    @Override
    public long readLongLe() throws IOException {
        this.require(8L);
        return this.buffer.readLongLe();
    }

    @Override
    public short readShort() throws IOException {
        this.require(2L);
        return this.buffer.readShort();
    }

    @Override
    public short readShortLe() throws IOException {
        this.require(2L);
        return this.buffer.readShortLe();
    }

    @Override
    public String readString(long l, Charset charset) throws IOException {
        this.require(l);
        if (charset != null) {
            return this.buffer.readString(l, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override
    public String readString(Charset charset) throws IOException {
        if (charset != null) {
            this.buffer.writeAll(this.source);
            return this.buffer.readString(charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override
    public String readUtf8() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readUtf8();
    }

    @Override
    public String readUtf8(long l) throws IOException {
        this.require(l);
        return this.buffer.readUtf8(l);
    }

    @Override
    public int readUtf8CodePoint() throws IOException {
        this.require(1L);
        byte by = this.buffer.getByte(0L);
        if ((by & 224) == 192) {
            this.require(2L);
        } else if ((by & 240) == 224) {
            this.require(3L);
        } else if ((by & 248) == 240) {
            this.require(4L);
        }
        return this.buffer.readUtf8CodePoint();
    }

    @Override
    public String readUtf8Line() throws IOException {
        long l = this.indexOf((byte)10);
        if (l == -1L) {
            String string = this.buffer.size != 0L ? this.readUtf8(this.buffer.size) : null;
            return string;
        }
        return this.buffer.readUtf8Line(l);
    }

    @Override
    public String readUtf8LineStrict() throws IOException {
        long l = this.indexOf((byte)10);
        if (l != -1L) {
            return this.buffer.readUtf8Line(l);
        }
        Buffer buffer = new Buffer();
        Object object = this.buffer;
        ((Buffer)object).copyTo(buffer, 0L, Math.min(32L, ((Buffer)object).size()));
        object = new StringBuilder();
        ((StringBuilder)object).append("\\n not found: size=");
        ((StringBuilder)object).append(this.buffer.size());
        ((StringBuilder)object).append(" content=");
        ((StringBuilder)object).append(buffer.readByteString().hex());
        ((StringBuilder)object).append("...");
        throw new EOFException(((StringBuilder)object).toString());
    }

    @Override
    public boolean request(long l) throws IOException {
        if (l >= 0L) {
            if (!this.closed) {
                while (this.buffer.size < l) {
                    if (this.source.read(this.buffer, 8192L) != -1L) continue;
                    return false;
                }
                return true;
            }
            throw new IllegalStateException("closed");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("byteCount < 0: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void require(long l) throws IOException {
        if (this.request(l)) {
            return;
        }
        throw new EOFException();
    }

    @Override
    public void skip(long l) throws IOException {
        if (!this.closed) {
            while (l > 0L) {
                if (this.buffer.size == 0L && this.source.read(this.buffer, 8192L) == -1L) {
                    throw new EOFException();
                }
                long l2 = Math.min(l, this.buffer.size());
                this.buffer.skip(l2);
                l -= l2;
            }
            return;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer(");
        stringBuilder.append(this.source);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

}

