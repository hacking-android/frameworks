/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Connection;
import com.android.okhttp.Headers;
import com.android.okhttp.Protocol;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.ResponseBody;
import com.android.okhttp.Route;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.http.HttpEngine;
import com.android.okhttp.internal.http.HttpStream;
import com.android.okhttp.internal.http.OkHeaders;
import com.android.okhttp.internal.http.RealResponseBody;
import com.android.okhttp.internal.http.RequestLine;
import com.android.okhttp.internal.http.RetryableSink;
import com.android.okhttp.internal.http.StatusLine;
import com.android.okhttp.internal.http.StreamAllocation;
import com.android.okhttp.internal.io.RealConnection;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ForwardingTimeout;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public final class Http1xStream
implements HttpStream {
    private static final int STATE_CLOSED = 6;
    private static final int STATE_IDLE = 0;
    private static final int STATE_OPEN_REQUEST_BODY = 1;
    private static final int STATE_OPEN_RESPONSE_BODY = 4;
    private static final int STATE_READING_RESPONSE_BODY = 5;
    private static final int STATE_READ_RESPONSE_HEADERS = 3;
    private static final int STATE_WRITING_REQUEST_BODY = 2;
    private HttpEngine httpEngine;
    private final BufferedSink sink;
    private final BufferedSource source;
    private int state = 0;
    private final StreamAllocation streamAllocation;

    public Http1xStream(StreamAllocation streamAllocation, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        this.streamAllocation = streamAllocation;
        this.source = bufferedSource;
        this.sink = bufferedSink;
    }

    private void detachTimeout(ForwardingTimeout forwardingTimeout) {
        Timeout timeout = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        timeout.clearDeadline();
        timeout.clearTimeout();
    }

    private Source getTransferStream(Response response) throws IOException {
        if (!HttpEngine.hasBody(response)) {
            return this.newFixedLengthSource(0L);
        }
        if ("chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return this.newChunkedSource(this.httpEngine);
        }
        long l = OkHeaders.contentLength(response);
        if (l != -1L) {
            return this.newFixedLengthSource(l);
        }
        return this.newUnknownLengthSource();
    }

    @Override
    public void cancel() {
        RealConnection realConnection = this.streamAllocation.connection();
        if (realConnection != null) {
            realConnection.cancel();
        }
    }

    @Override
    public Sink createRequestBody(Request request, long l) throws IOException {
        if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            return this.newChunkedSink();
        }
        if (l != -1L) {
            return this.newFixedLengthSink(l);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    @Override
    public void finishRequest() throws IOException {
        this.sink.flush();
    }

    public boolean isClosed() {
        boolean bl = this.state == 6;
        return bl;
    }

    public Sink newChunkedSink() {
        if (this.state == 1) {
            this.state = 2;
            return new ChunkedSink();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Source newChunkedSource(HttpEngine object) throws IOException {
        if (this.state == 4) {
            this.state = 5;
            return new ChunkedSource((HttpEngine)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("state: ");
        ((StringBuilder)object).append(this.state);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public Sink newFixedLengthSink(long l) {
        if (this.state == 1) {
            this.state = 2;
            return new FixedLengthSink(l);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Source newFixedLengthSource(long l) throws IOException {
        if (this.state == 4) {
            this.state = 5;
            return new FixedLengthSource(l);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public Source newUnknownLengthSource() throws IOException {
        if (this.state == 4) {
            StreamAllocation streamAllocation = this.streamAllocation;
            if (streamAllocation != null) {
                this.state = 5;
                streamAllocation.noNewStreams();
                return new UnknownLengthSource();
            }
            throw new IllegalStateException("streamAllocation == null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state: ");
        stringBuilder.append(this.state);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public ResponseBody openResponseBody(Response response) throws IOException {
        Source source = this.getTransferStream(response);
        return new RealResponseBody(response.headers(), Okio.buffer(source));
    }

    public Headers readHeaders() throws IOException {
        String string;
        Headers.Builder builder = new Headers.Builder();
        while ((string = this.source.readUtf8LineStrict()).length() != 0) {
            Internal.instance.addLenient(builder, string);
        }
        return builder.build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Response.Builder readResponse() throws IOException {
        Object object;
        int n = this.state;
        if (n != 1 && n != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }
        try {
            StatusLine statusLine;
            do {
                statusLine = StatusLine.parse(this.source.readUtf8LineStrict());
                object = new Response.Builder();
                object = ((Response.Builder)object).protocol(statusLine.protocol).code(statusLine.code).message(statusLine.message).headers(this.readHeaders());
            } while (statusLine.code == 100);
            this.state = 4;
            return object;
        }
        catch (EOFException eOFException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected end of stream on ");
            ((StringBuilder)object).append(this.streamAllocation);
            object = new IOException(((StringBuilder)object).toString());
            ((Throwable)object).initCause(eOFException);
            throw object;
        }
    }

    @Override
    public Response.Builder readResponseHeaders() throws IOException {
        return this.readResponse();
    }

    @Override
    public void setHttpEngine(HttpEngine httpEngine) {
        this.httpEngine = httpEngine;
    }

    public void writeRequest(Headers object, String string) throws IOException {
        if (this.state == 0) {
            this.sink.writeUtf8(string).writeUtf8("\r\n");
            int n = ((Headers)object).size();
            for (int i = 0; i < n; ++i) {
                this.sink.writeUtf8(((Headers)object).name(i)).writeUtf8(": ").writeUtf8(((Headers)object).value(i)).writeUtf8("\r\n");
            }
            this.sink.writeUtf8("\r\n");
            this.state = 1;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("state: ");
        ((StringBuilder)object).append(this.state);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    public void writeRequestBody(RetryableSink object) throws IOException {
        if (this.state == 1) {
            this.state = 3;
            ((RetryableSink)object).writeToSocket(this.sink);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("state: ");
        ((StringBuilder)object).append(this.state);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    public void writeRequestHeaders(Request request) throws IOException {
        this.httpEngine.writingRequestHeaders();
        String string = RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type());
        this.writeRequest(request.headers(), string);
    }

    private abstract class AbstractSource
    implements Source {
        protected boolean closed;
        protected final ForwardingTimeout timeout;

        private AbstractSource() {
            this.timeout = new ForwardingTimeout(Http1xStream.this.source.timeout());
        }

        protected final void endOfInput() throws IOException {
            if (Http1xStream.this.state == 5) {
                Http1xStream.this.detachTimeout(this.timeout);
                Http1xStream.this.state = 6;
                if (Http1xStream.this.streamAllocation != null) {
                    Http1xStream.this.streamAllocation.streamFinished(Http1xStream.this);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("state: ");
            stringBuilder.append(Http1xStream.this.state);
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        protected final void unexpectedEndOfInput() {
            if (Http1xStream.this.state == 6) {
                return;
            }
            Http1xStream.this.state = 6;
            if (Http1xStream.this.streamAllocation != null) {
                Http1xStream.this.streamAllocation.noNewStreams();
                Http1xStream.this.streamAllocation.streamFinished(Http1xStream.this);
            }
        }
    }

    private final class ChunkedSink
    implements Sink {
        private boolean closed;
        private final ForwardingTimeout timeout;

        private ChunkedSink() {
            this.timeout = new ForwardingTimeout(Http1xStream.this.sink.timeout());
        }

        @Override
        public void close() throws IOException {
            synchronized (this) {
                block4 : {
                    boolean bl = this.closed;
                    if (!bl) break block4;
                    return;
                }
                this.closed = true;
                Http1xStream.this.sink.writeUtf8("0\r\n\r\n");
                Http1xStream.this.detachTimeout(this.timeout);
                Http1xStream.this.state = 3;
                return;
            }
        }

        @Override
        public void flush() throws IOException {
            synchronized (this) {
                block4 : {
                    boolean bl = this.closed;
                    if (!bl) break block4;
                    return;
                }
                Http1xStream.this.sink.flush();
                return;
            }
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            if (!this.closed) {
                if (l == 0L) {
                    return;
                }
                Http1xStream.this.sink.writeHexadecimalUnsignedLong(l);
                Http1xStream.this.sink.writeUtf8("\r\n");
                Http1xStream.this.sink.write(buffer, l);
                Http1xStream.this.sink.writeUtf8("\r\n");
                return;
            }
            throw new IllegalStateException("closed");
        }
    }

    private class ChunkedSource
    extends AbstractSource {
        private static final long NO_CHUNK_YET = -1L;
        private long bytesRemainingInChunk = -1L;
        private boolean hasMoreChunks = true;
        private final HttpEngine httpEngine;

        ChunkedSource(HttpEngine httpEngine) throws IOException {
            this.httpEngine = httpEngine;
        }

        private void readChunkSize() throws IOException {
            String string;
            block4 : {
                block5 : {
                    if (this.bytesRemainingInChunk != -1L) {
                        Http1xStream.this.source.readUtf8LineStrict();
                    }
                    try {
                        boolean bl;
                        this.bytesRemainingInChunk = Http1xStream.this.source.readHexadecimalUnsignedLong();
                        string = Http1xStream.this.source.readUtf8LineStrict().trim();
                        if (this.bytesRemainingInChunk < 0L || !string.isEmpty() && !(bl = string.startsWith(";"))) break block4;
                        if (this.bytesRemainingInChunk != 0L) break block5;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw new ProtocolException(numberFormatException.getMessage());
                    }
                    this.hasMoreChunks = false;
                    this.httpEngine.receiveHeaders(Http1xStream.this.readHeaders());
                    this.endOfInput();
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected chunk size and optional extensions but was \"");
            stringBuilder.append(this.bytesRemainingInChunk);
            stringBuilder.append(string);
            stringBuilder.append("\"");
            ProtocolException protocolException = new ProtocolException(stringBuilder.toString());
            throw protocolException;
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (this.hasMoreChunks && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.unexpectedEndOfInput();
            }
            this.closed = true;
        }

        @Override
        public long read(Buffer object, long l) throws IOException {
            if (l >= 0L) {
                if (!this.closed) {
                    if (!this.hasMoreChunks) {
                        return -1L;
                    }
                    long l2 = this.bytesRemainingInChunk;
                    if (l2 == 0L || l2 == -1L) {
                        this.readChunkSize();
                        if (!this.hasMoreChunks) {
                            return -1L;
                        }
                    }
                    if ((l = Http1xStream.this.source.read((Buffer)object, Math.min(l, this.bytesRemainingInChunk))) != -1L) {
                        this.bytesRemainingInChunk -= l;
                        return l;
                    }
                    this.unexpectedEndOfInput();
                    throw new ProtocolException("unexpected end of stream");
                }
                throw new IllegalStateException("closed");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    private final class FixedLengthSink
    implements Sink {
        private long bytesRemaining;
        private boolean closed;
        private final ForwardingTimeout timeout;

        private FixedLengthSink(long l) {
            this.timeout = new ForwardingTimeout(Http1xStream.this.sink.timeout());
            this.bytesRemaining = l;
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            this.closed = true;
            if (this.bytesRemaining <= 0L) {
                Http1xStream.this.detachTimeout(this.timeout);
                Http1xStream.this.state = 3;
                return;
            }
            throw new ProtocolException("unexpected end of stream");
        }

        @Override
        public void flush() throws IOException {
            if (this.closed) {
                return;
            }
            Http1xStream.this.sink.flush();
        }

        @Override
        public Timeout timeout() {
            return this.timeout;
        }

        @Override
        public void write(Buffer object, long l) throws IOException {
            if (!this.closed) {
                Util.checkOffsetAndCount(((Buffer)object).size(), 0L, l);
                if (l <= this.bytesRemaining) {
                    Http1xStream.this.sink.write((Buffer)object, l);
                    this.bytesRemaining -= l;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("expected ");
                ((StringBuilder)object).append(this.bytesRemaining);
                ((StringBuilder)object).append(" bytes but received ");
                ((StringBuilder)object).append(l);
                throw new ProtocolException(((StringBuilder)object).toString());
            }
            throw new IllegalStateException("closed");
        }
    }

    private class FixedLengthSource
    extends AbstractSource {
        private long bytesRemaining;

        public FixedLengthSource(long l) throws IOException {
            this.bytesRemaining = l;
            if (this.bytesRemaining == 0L) {
                this.endOfInput();
            }
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (this.bytesRemaining != 0L && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                this.unexpectedEndOfInput();
            }
            this.closed = true;
        }

        @Override
        public long read(Buffer object, long l) throws IOException {
            if (l >= 0L) {
                if (!this.closed) {
                    if (this.bytesRemaining == 0L) {
                        return -1L;
                    }
                    l = Http1xStream.this.source.read((Buffer)object, Math.min(this.bytesRemaining, l));
                    if (l != -1L) {
                        this.bytesRemaining -= l;
                        if (this.bytesRemaining == 0L) {
                            this.endOfInput();
                        }
                        return l;
                    }
                    this.unexpectedEndOfInput();
                    throw new ProtocolException("unexpected end of stream");
                }
                throw new IllegalStateException("closed");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    private class UnknownLengthSource
    extends AbstractSource {
        private boolean inputExhausted;

        private UnknownLengthSource() {
        }

        @Override
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (!this.inputExhausted) {
                this.unexpectedEndOfInput();
            }
            this.closed = true;
        }

        @Override
        public long read(Buffer object, long l) throws IOException {
            if (l >= 0L) {
                if (!this.closed) {
                    if (this.inputExhausted) {
                        return -1L;
                    }
                    l = Http1xStream.this.source.read((Buffer)object, l);
                    if (l == -1L) {
                        this.inputExhausted = true;
                        this.endOfInput();
                        return -1L;
                    }
                    return l;
                }
                throw new IllegalStateException("closed");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

}

