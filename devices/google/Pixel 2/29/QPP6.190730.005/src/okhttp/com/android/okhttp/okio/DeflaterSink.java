/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Segment;
import com.android.okhttp.okio.SegmentPool;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Timeout;
import com.android.okhttp.okio.Util;
import java.io.IOException;
import java.util.zip.Deflater;

public final class DeflaterSink
implements Sink {
    private boolean closed;
    private final Deflater deflater;
    private final BufferedSink sink;

    DeflaterSink(BufferedSink bufferedSink, Deflater deflater) {
        if (bufferedSink != null) {
            if (deflater != null) {
                this.sink = bufferedSink;
                this.deflater = deflater;
                return;
            }
            throw new IllegalArgumentException("inflater == null");
        }
        throw new IllegalArgumentException("source == null");
    }

    public DeflaterSink(Sink sink, Deflater deflater) {
        this(Okio.buffer(sink), deflater);
    }

    private void deflate(boolean bl) throws IOException {
        Segment segment;
        Buffer buffer = this.sink.buffer();
        do {
            segment = buffer.writableSegment(1);
            int n = bl ? this.deflater.deflate(segment.data, segment.limit, 8192 - segment.limit, 2) : this.deflater.deflate(segment.data, segment.limit, 8192 - segment.limit);
            if (n > 0) {
                segment.limit += n;
                buffer.size += (long)n;
                this.sink.emitCompleteSegments();
                continue;
            }
            if (this.deflater.needsInput()) break;
        } while (true);
        if (segment.pos == segment.limit) {
            buffer.head = segment.pop();
            SegmentPool.recycle(segment);
        }
    }

    @Override
    public void close() throws IOException {
        Throwable throwable;
        block9 : {
            Throwable throwable2;
            block8 : {
                if (this.closed) {
                    return;
                }
                throwable = null;
                try {
                    this.finishDeflate();
                }
                catch (Throwable throwable3) {
                    // empty catch block
                }
                try {
                    this.deflater.end();
                    throwable2 = throwable;
                }
                catch (Throwable throwable4) {
                    throwable2 = throwable;
                    if (throwable != null) break block8;
                    throwable2 = throwable4;
                }
            }
            try {
                this.sink.close();
                throwable = throwable2;
            }
            catch (Throwable throwable5) {
                throwable = throwable2;
                if (throwable2 != null) break block9;
                throwable = throwable5;
            }
        }
        this.closed = true;
        if (throwable != null) {
            Util.sneakyRethrow(throwable);
        }
    }

    void finishDeflate() throws IOException {
        this.deflater.finish();
        this.deflate(false);
    }

    @Override
    public void flush() throws IOException {
        this.deflate(true);
        this.sink.flush();
    }

    @Override
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DeflaterSink(");
        stringBuilder.append(this.sink);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void write(Buffer buffer, long l) throws IOException {
        Util.checkOffsetAndCount(buffer.size, 0L, l);
        while (l > 0L) {
            Segment segment = buffer.head;
            int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
            this.deflater.setInput(segment.data, segment.pos, n);
            this.deflate(false);
            buffer.size -= (long)n;
            segment.pos += n;
            if (segment.pos == segment.limit) {
                buffer.head = segment.pop();
                SegmentPool.recycle(segment);
            }
            l -= (long)n;
        }
    }
}

