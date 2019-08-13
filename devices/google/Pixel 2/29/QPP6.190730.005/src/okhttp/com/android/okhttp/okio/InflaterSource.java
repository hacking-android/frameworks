/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Segment;
import com.android.okhttp.okio.SegmentPool;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class InflaterSource
implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource != null) {
            if (inflater != null) {
                this.source = bufferedSource;
                this.inflater = inflater;
                return;
            }
            throw new IllegalArgumentException("inflater == null");
        }
        throw new IllegalArgumentException("source == null");
    }

    public InflaterSource(Source source, Inflater inflater) {
        this(Okio.buffer(source), inflater);
    }

    private void releaseInflatedBytes() throws IOException {
        int n = this.bufferBytesHeldByInflater;
        if (n == 0) {
            return;
        }
        this.bufferBytesHeldByInflater -= (n -= this.inflater.getRemaining());
        this.source.skip(n);
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public long read(Buffer var1_1, long var2_3) throws IOException {
        block8 : {
            if (var2_3 < 0L) break block8;
            if (this.closed) throw new IllegalStateException("closed");
            if (var2_3 == 0L) {
                return 0L;
            }
            do {
                block7 : {
                    var4_4 = this.refill();
                    var5_5 = var1_1.writableSegment(1);
                    var6_6 = this.inflater.inflate(var5_5.data, var5_5.limit, 8192 - var5_5.limit);
                    if (var6_6 <= 0) break block7;
                    var5_5.limit += var6_6;
                    var1_1.size += (long)var6_6;
                    return var6_6;
                }
                if (this.inflater.finished() || this.inflater.needsDictionary()) ** GOTO lbl22
                if (!var4_4) continue;
                break;
            } while (true);
            try {
                var1_1 = new EOFException("source exhausted prematurely");
                throw var1_1;
lbl22: // 1 sources:
                this.releaseInflatedBytes();
                if (var5_5.pos != var5_5.limit) return -1L;
                var1_1.head = var5_5.pop();
                SegmentPool.recycle(var5_5);
                return -1L;
            }
            catch (DataFormatException var1_2) {
                throw new IOException(var1_2);
            }
        }
        var1_1 = new StringBuilder();
        var1_1.append("byteCount < 0: ");
        var1_1.append(var2_3);
        throw new IllegalArgumentException(var1_1.toString());
    }

    public boolean refill() throws IOException {
        if (!this.inflater.needsInput()) {
            return false;
        }
        this.releaseInflatedBytes();
        if (this.inflater.getRemaining() == 0) {
            if (this.source.exhausted()) {
                return true;
            }
            Segment segment = this.source.buffer().head;
            this.bufferBytesHeldByInflater = segment.limit - segment.pos;
            this.inflater.setInput(segment.data, segment.pos, this.bufferBytesHeldByInflater);
            return false;
        }
        throw new IllegalStateException("?");
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

