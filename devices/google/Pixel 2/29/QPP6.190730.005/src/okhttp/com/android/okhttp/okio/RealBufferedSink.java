/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import com.android.okhttp.okio.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

final class RealBufferedSink
implements BufferedSink {
    public final Buffer buffer;
    private boolean closed;
    public final Sink sink;

    public RealBufferedSink(Sink sink) {
        this(sink, new Buffer());
    }

    public RealBufferedSink(Sink sink, Buffer buffer) {
        if (sink != null) {
            this.buffer = buffer;
            this.sink = sink;
            return;
        }
        throw new IllegalArgumentException("sink == null");
    }

    @Override
    public Buffer buffer() {
        return this.buffer;
    }

    @Override
    public void close() throws IOException {
        Throwable throwable;
        block7 : {
            if (this.closed) {
                return;
            }
            Throwable throwable2 = null;
            try {
                if (this.buffer.size > 0L) {
                    this.sink.write(this.buffer, this.buffer.size);
                }
            }
            catch (Throwable throwable3) {
                // empty catch block
            }
            try {
                this.sink.close();
                throwable = throwable2;
            }
            catch (Throwable throwable4) {
                throwable = throwable2;
                if (throwable2 != null) break block7;
                throwable = throwable4;
            }
        }
        this.closed = true;
        if (throwable != null) {
            Util.sneakyRethrow(throwable);
        }
    }

    @Override
    public BufferedSink emit() throws IOException {
        if (!this.closed) {
            long l = this.buffer.size();
            if (l > 0L) {
                this.sink.write(this.buffer, l);
            }
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink emitCompleteSegments() throws IOException {
        if (!this.closed) {
            long l = this.buffer.completeSegmentByteCount();
            if (l > 0L) {
                this.sink.write(this.buffer, l);
            }
            return this;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public void flush() throws IOException {
        if (!this.closed) {
            if (this.buffer.size > 0L) {
                Sink sink = this.sink;
                Buffer buffer = this.buffer;
                sink.write(buffer, buffer.size);
            }
            this.sink.flush();
            return;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public OutputStream outputStream() {
        return new OutputStream(){

            @Override
            public void close() throws IOException {
                RealBufferedSink.this.close();
            }

            @Override
            public void flush() throws IOException {
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.flush();
                }
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(RealBufferedSink.this);
                stringBuilder.append(".outputStream()");
                return stringBuilder.toString();
            }

            @Override
            public void write(int n) throws IOException {
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.buffer.writeByte((byte)n);
                    RealBufferedSink.this.emitCompleteSegments();
                    return;
                }
                throw new IOException("closed");
            }

            @Override
            public void write(byte[] arrby, int n, int n2) throws IOException {
                if (!RealBufferedSink.this.closed) {
                    RealBufferedSink.this.buffer.write(arrby, n, n2);
                    RealBufferedSink.this.emitCompleteSegments();
                    return;
                }
                throw new IOException("closed");
            }
        };
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buffer(");
        stringBuilder.append(this.sink);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public BufferedSink write(ByteString byteString) throws IOException {
        if (!this.closed) {
            this.buffer.write(byteString);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink write(Source source, long l) throws IOException {
        while (l > 0L) {
            long l2 = source.read(this.buffer, l);
            if (l2 != -1L) {
                l -= l2;
                this.emitCompleteSegments();
                continue;
            }
            throw new EOFException();
        }
        return this;
    }

    @Override
    public BufferedSink write(byte[] arrby) throws IOException {
        if (!this.closed) {
            this.buffer.write(arrby);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink write(byte[] arrby, int n, int n2) throws IOException {
        if (!this.closed) {
            this.buffer.write(arrby, n, n2);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public void write(Buffer buffer, long l) throws IOException {
        if (!this.closed) {
            this.buffer.write(buffer, l);
            this.emitCompleteSegments();
            return;
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public long writeAll(Source source) throws IOException {
        if (source != null) {
            long l;
            long l2 = 0L;
            while ((l = source.read(this.buffer, 8192L)) != -1L) {
                l2 += l;
                this.emitCompleteSegments();
            }
            return l2;
        }
        throw new IllegalArgumentException("source == null");
    }

    @Override
    public BufferedSink writeByte(int n) throws IOException {
        if (!this.closed) {
            this.buffer.writeByte(n);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeDecimalLong(long l) throws IOException {
        if (!this.closed) {
            this.buffer.writeDecimalLong(l);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeHexadecimalUnsignedLong(long l) throws IOException {
        if (!this.closed) {
            this.buffer.writeHexadecimalUnsignedLong(l);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeInt(int n) throws IOException {
        if (!this.closed) {
            this.buffer.writeInt(n);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeIntLe(int n) throws IOException {
        if (!this.closed) {
            this.buffer.writeIntLe(n);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeLong(long l) throws IOException {
        if (!this.closed) {
            this.buffer.writeLong(l);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeLongLe(long l) throws IOException {
        if (!this.closed) {
            this.buffer.writeLongLe(l);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeShort(int n) throws IOException {
        if (!this.closed) {
            this.buffer.writeShort(n);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeShortLe(int n) throws IOException {
        if (!this.closed) {
            this.buffer.writeShortLe(n);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeString(String string, int n, int n2, Charset charset) throws IOException {
        if (!this.closed) {
            this.buffer.writeString(string, n, n2, charset);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeString(String string, Charset charset) throws IOException {
        if (!this.closed) {
            this.buffer.writeString(string, charset);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeUtf8(String string) throws IOException {
        if (!this.closed) {
            this.buffer.writeUtf8(string);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeUtf8(String string, int n, int n2) throws IOException {
        if (!this.closed) {
            this.buffer.writeUtf8(string, n, n2);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override
    public BufferedSink writeUtf8CodePoint(int n) throws IOException {
        if (!this.closed) {
            this.buffer.writeUtf8CodePoint(n);
            return this.emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

}

