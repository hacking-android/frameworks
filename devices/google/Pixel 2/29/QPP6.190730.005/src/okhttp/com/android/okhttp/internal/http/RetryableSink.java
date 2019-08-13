/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.internal.Util;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Timeout;
import java.io.IOException;
import java.net.ProtocolException;

public final class RetryableSink
implements Sink {
    private boolean closed;
    private final Buffer content = new Buffer();
    private final int limit;

    public RetryableSink() {
        this(-1);
    }

    public RetryableSink(int n) {
        this.limit = n;
    }

    @Override
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.content.size() >= (long)this.limit) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("content-length promised ");
        stringBuilder.append(this.limit);
        stringBuilder.append(" bytes, but received ");
        stringBuilder.append(this.content.size());
        throw new ProtocolException(stringBuilder.toString());
    }

    public long contentLength() throws IOException {
        return this.content.size();
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public Timeout timeout() {
        return Timeout.NONE;
    }

    @Override
    public void write(Buffer object, long l) throws IOException {
        if (!this.closed) {
            Util.checkOffsetAndCount(((Buffer)object).size(), 0L, l);
            if (this.limit != -1 && this.content.size() > (long)this.limit - l) {
                object = new StringBuilder();
                ((StringBuilder)object).append("exceeded content-length limit of ");
                ((StringBuilder)object).append(this.limit);
                ((StringBuilder)object).append(" bytes");
                throw new ProtocolException(((StringBuilder)object).toString());
            }
            this.content.write((Buffer)object, l);
            return;
        }
        throw new IllegalStateException("closed");
    }

    public void writeToSocket(Sink sink) throws IOException {
        Buffer buffer = new Buffer();
        Buffer buffer2 = this.content;
        buffer2.copyTo(buffer, 0L, buffer2.size());
        sink.write(buffer, buffer.size());
    }
}

