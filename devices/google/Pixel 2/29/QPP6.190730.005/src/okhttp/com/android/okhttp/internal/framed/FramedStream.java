/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.internal.framed.ErrorCode;
import com.android.okhttp.internal.framed.FramedConnection;
import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.framed.HeadersMode;
import com.android.okhttp.internal.framed.Settings;
import com.android.okhttp.okio.AsyncTimeout;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class FramedStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    private final FramedConnection connection;
    private ErrorCode errorCode = null;
    private final int id;
    private final StreamTimeout readTimeout = new StreamTimeout();
    private final List<Header> requestHeaders;
    private List<Header> responseHeaders;
    final FramedDataSink sink;
    private final FramedDataSource source;
    long unacknowledgedBytesRead = 0L;
    private final StreamTimeout writeTimeout = new StreamTimeout();

    FramedStream(int n, FramedConnection framedConnection, boolean bl, boolean bl2, List<Header> list) {
        if (framedConnection != null) {
            if (list != null) {
                this.id = n;
                this.connection = framedConnection;
                this.bytesLeftInWriteWindow = framedConnection.peerSettings.getInitialWindowSize(65536);
                this.source = new FramedDataSource(framedConnection.okHttpSettings.getInitialWindowSize(65536));
                this.sink = new FramedDataSink();
                this.source.finished = bl2;
                this.sink.finished = bl;
                this.requestHeaders = list;
                return;
            }
            throw new NullPointerException("requestHeaders == null");
        }
        throw new NullPointerException("connection == null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void cancelStreamIfNecessary() throws IOException {
        // MONITORENTER : this
        boolean bl = !this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed);
        boolean bl2 = this.isOpen();
        // MONITOREXIT : this
        if (bl) {
            this.close(ErrorCode.CANCEL);
            return;
        }
        if (bl2) return;
        this.connection.removeStream(this.id);
    }

    private void checkOutNotClosed() throws IOException {
        if (!this.sink.closed) {
            if (!this.sink.finished) {
                if (this.errorCode == null) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stream was reset: ");
                stringBuilder.append((Object)this.errorCode);
                throw new IOException(stringBuilder.toString());
            }
            throw new IOException("stream finished");
        }
        throw new IOException("stream closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean closeInternal(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished && this.sink.finished) {
                return false;
            }
            this.errorCode = errorCode;
            this.notifyAll();
        }
        this.connection.removeStream(this.id);
        return true;
    }

    private void waitForIo() throws InterruptedIOException {
        try {
            this.wait();
            return;
        }
        catch (InterruptedException interruptedException) {
            throw new InterruptedIOException();
        }
    }

    void addBytesToWriteWindow(long l) {
        this.bytesLeftInWriteWindow += l;
        if (l > 0L) {
            this.notifyAll();
        }
    }

    public void close(ErrorCode errorCode) throws IOException {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynReset(this.id, errorCode);
    }

    public void closeLater(ErrorCode errorCode) {
        if (!this.closeInternal(errorCode)) {
            return;
        }
        this.connection.writeSynResetLater(this.id, errorCode);
    }

    public FramedConnection getConnection() {
        return this.connection;
    }

    public ErrorCode getErrorCode() {
        synchronized (this) {
            ErrorCode errorCode = this.errorCode;
            return errorCode;
        }
    }

    public int getId() {
        return this.id;
    }

    public List<Header> getRequestHeaders() {
        return this.requestHeaders;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public List<Header> getResponseHeaders() throws IOException {
        void var1_4;
        block9 : {
            List<Header> object;
            // MONITORENTER : this
            this.readTimeout.enter();
            do {
                object = this.responseHeaders;
                if (object != null) break;
                try {
                    if (this.errorCode != null) break;
                    this.waitForIo();
                }
                catch (Throwable throwable) {
                    break block9;
                }
            } while (true);
            this.readTimeout.exitAndThrowIfTimedOut();
            if (this.responseHeaders != null) {
                object = this.responseHeaders;
                // MONITOREXIT : this
                return object;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stream was reset: ");
            stringBuilder.append((Object)this.errorCode);
            object = new List<Header>(stringBuilder.toString());
            throw object;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        this.readTimeout.exitAndThrowIfTimedOut();
        throw var1_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Sink getSink() {
        synchronized (this) {
            if (this.responseHeaders == null && !this.isLocallyInitiated()) {
                IllegalStateException illegalStateException = new IllegalStateException("reply before requesting the sink");
                throw illegalStateException;
            }
            return this.sink;
        }
    }

    public Source getSource() {
        return this.source;
    }

    public boolean isLocallyInitiated() {
        int n = this.id;
        boolean bl = true;
        boolean bl2 = (n & 1) == 1;
        bl2 = this.connection.client == bl2 ? bl : false;
        return bl2;
    }

    public boolean isOpen() {
        synchronized (this) {
            block5 : {
                Object object;
                block4 : {
                    object = this.errorCode;
                    if (object == null) break block4;
                    return false;
                }
                if (!this.source.finished && !this.source.closed || !this.sink.finished && !this.sink.closed || (object = this.responseHeaders) == null) break block5;
                return false;
            }
            return true;
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    void receiveData(BufferedSource bufferedSource, int n) throws IOException {
        this.source.receive(bufferedSource, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void receiveFin() {
        // MONITORENTER : this
        this.source.finished = true;
        boolean bl = this.isOpen();
        this.notifyAll();
        // MONITOREXIT : this
        if (bl) return;
        this.connection.removeStream(this.id);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void receiveHeaders(List<Header> object, HeadersMode object2) {
        Object var3_3 = null;
        boolean bl = true;
        // MONITORENTER : this
        if (this.responseHeaders == null) {
            if (((HeadersMode)((Object)object2)).failIfHeadersAbsent()) {
                object = ErrorCode.PROTOCOL_ERROR;
            } else {
                this.responseHeaders = object;
                bl = this.isOpen();
                this.notifyAll();
                object = var3_3;
            }
        } else if (((HeadersMode)((Object)object2)).failIfHeadersPresent()) {
            object = ErrorCode.STREAM_IN_USE;
        } else {
            object2 = new ArrayList();
            object2.addAll(this.responseHeaders);
            object2.addAll(object);
            this.responseHeaders = object2;
            object = var3_3;
        }
        // MONITOREXIT : this
        if (object != null) {
            this.closeLater((ErrorCode)((Object)object));
            return;
        }
        if (bl) return;
        this.connection.removeStream(this.id);
    }

    void receiveRstStream(ErrorCode errorCode) {
        synchronized (this) {
            if (this.errorCode == null) {
                this.errorCode = errorCode;
                this.notifyAll();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reply(List<Header> object, boolean bl) throws IOException {
        boolean bl2 = false;
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                block8 : {
                    try {
                        if (this.responseHeaders != null) break block8;
                        this.responseHeaders = object;
                        if (!bl) {
                            this.sink.finished = true;
                            bl2 = true;
                        }
                        // MONITOREXIT [0, 2, 7] lbl11 : MonitorExitStatement: MONITOREXIT : this
                    }
                    catch (Throwable throwable2) {}
                    this.connection.writeSynReply(this.id, bl2, (List<Header>)object);
                    if (bl2) {
                        this.connection.flush();
                    }
                    return;
                }
                object = new IllegalStateException("reply already sent");
                throw object;
            } else {
                object = new NullPointerException("responseHeaders == null");
                throw object;
            }
            throw throwable2;
        }
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    final class FramedDataSink
    implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long EMIT_BUFFER_SIZE = 16384L;
        private boolean closed;
        private boolean finished;
        private final Buffer sendBuffer = new Buffer();

        FramedDataSink() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void emitDataFrame(boolean bl) throws IOException {
            long l;
            Object object = FramedStream.this;
            synchronized (object) {
                FramedStream.this.writeTimeout.enter();
                while (FramedStream.this.bytesLeftInWriteWindow <= 0L && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                    FramedStream.this.waitForIo();
                }
                FramedStream.this.checkOutNotClosed();
                l = Math.min(FramedStream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                FramedStream framedStream = FramedStream.this;
                framedStream.bytesLeftInWriteWindow -= l;
            }
            FramedStream.this.writeTimeout.enter();
            try {
                object = FramedStream.this.connection;
                int n = FramedStream.this.id;
                bl = bl && l == this.sendBuffer.size();
                ((FramedConnection)object).writeData(n, bl, this.sendBuffer, l);
                return;
            }
            finally {
                FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
            }
            {
                finally {
                    FramedStream.this.writeTimeout.exitAndThrowIfTimedOut();
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void close() throws IOException {
            FramedStream framedStream = FramedStream.this;
            // MONITORENTER : framedStream
            if (this.closed) {
                // MONITOREXIT : framedStream
                return;
            }
            // MONITOREXIT : framedStream
            if (!FramedStream.this.sink.finished) {
                if (this.sendBuffer.size() > 0L) {
                    while (this.sendBuffer.size() > 0L) {
                        this.emitDataFrame(true);
                    }
                } else {
                    FramedStream.this.connection.writeData(FramedStream.this.id, true, null, 0L);
                }
            }
            FramedStream framedStream2 = FramedStream.this;
            // MONITORENTER : framedStream2
            this.closed = true;
            // MONITOREXIT : framedStream2
            FramedStream.this.connection.flush();
            FramedStream.this.cancelStreamIfNecessary();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void flush() throws IOException {
            FramedStream framedStream = FramedStream.this;
            synchronized (framedStream) {
                FramedStream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0L) {
                this.emitDataFrame(false);
                FramedStream.this.connection.flush();
            }
            return;
        }

        @Override
        public Timeout timeout() {
            return FramedStream.this.writeTimeout;
        }

        @Override
        public void write(Buffer buffer, long l) throws IOException {
            this.sendBuffer.write(buffer, l);
            while (this.sendBuffer.size() >= 16384L) {
                this.emitDataFrame(false);
            }
        }
    }

    private final class FramedDataSource
    implements Source {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean closed;
        private boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer = new Buffer();
        private final Buffer receiveBuffer = new Buffer();

        private FramedDataSource(long l) {
            this.maxByteCount = l;
        }

        private void checkNotClosed() throws IOException {
            if (!this.closed) {
                if (FramedStream.this.errorCode == null) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("stream was reset: ");
                stringBuilder.append((Object)FramedStream.this.errorCode);
                throw new IOException(stringBuilder.toString());
            }
            throw new IOException("stream closed");
        }

        private void waitUntilReadable() throws IOException {
            FramedStream.this.readTimeout.enter();
            try {
                while (this.readBuffer.size() == 0L && !this.finished && !this.closed && FramedStream.this.errorCode == null) {
                    FramedStream.this.waitForIo();
                }
                return;
            }
            finally {
                FramedStream.this.readTimeout.exitAndThrowIfTimedOut();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void close() throws IOException {
            FramedStream framedStream = FramedStream.this;
            synchronized (framedStream) {
                this.closed = true;
                this.readBuffer.clear();
                FramedStream.this.notifyAll();
            }
            FramedStream.this.cancelStreamIfNecessary();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long read(Buffer object, long l) throws IOException {
            if (l < 0L) {
                object = new StringBuilder();
                ((StringBuilder)object).append("byteCount < 0: ");
                ((StringBuilder)object).append(l);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            Object object2 = FramedStream.this;
            synchronized (object2) {
                this.waitUntilReadable();
                this.checkNotClosed();
                if (this.readBuffer.size() == 0L) {
                    return -1L;
                }
                l = this.readBuffer.read((Buffer)object, Math.min(l, this.readBuffer.size()));
                object = FramedStream.this;
                ((FramedStream)object).unacknowledgedBytesRead += l;
                if (FramedStream.this.unacknowledgedBytesRead >= (long)(FramedStream.access$500((FramedStream)FramedStream.this).okHttpSettings.getInitialWindowSize(65536) / 2)) {
                    FramedStream.this.connection.writeWindowUpdateLater(FramedStream.this.id, FramedStream.this.unacknowledgedBytesRead);
                    FramedStream.this.unacknowledgedBytesRead = 0L;
                }
            }
            object = FramedStream.this.connection;
            synchronized (object) {
                object2 = FramedStream.this.connection;
                ((FramedConnection)object2).unacknowledgedBytesRead += l;
                if (FramedStream.access$500((FramedStream)FramedStream.this).unacknowledgedBytesRead >= (long)(FramedStream.access$500((FramedStream)FramedStream.this).okHttpSettings.getInitialWindowSize(65536) / 2)) {
                    FramedStream.this.connection.writeWindowUpdateLater(0, FramedStream.access$500((FramedStream)FramedStream.this).unacknowledgedBytesRead);
                    FramedStream.access$500((FramedStream)FramedStream.this).unacknowledgedBytesRead = 0L;
                }
                return l;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void receive(BufferedSource bufferedSource, long l) throws IOException {
            while (l > 0L) {
                FramedStream framedStream = FramedStream.this;
                // MONITORENTER : framedStream
                boolean bl = this.finished;
                long l2 = this.readBuffer.size();
                long l3 = this.maxByteCount;
                boolean bl2 = true;
                boolean bl3 = l2 + l > l3;
                // MONITOREXIT : framedStream
                if (bl3) {
                    bufferedSource.skip(l);
                    FramedStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                }
                if (bl) {
                    bufferedSource.skip(l);
                    return;
                }
                l3 = bufferedSource.read(this.receiveBuffer, l);
                if (l3 == -1L) throw new EOFException();
                framedStream = FramedStream.this;
                // MONITORENTER : framedStream
                bl3 = this.readBuffer.size() == 0L ? bl2 : false;
                this.readBuffer.writeAll(this.receiveBuffer);
                if (bl3) {
                    FramedStream.this.notifyAll();
                }
                // MONITOREXIT : framedStream
                l -= l3;
            }
        }

        @Override
        public Timeout timeout() {
            return FramedStream.this.readTimeout;
        }
    }

    class StreamTimeout
    extends AsyncTimeout {
        StreamTimeout() {
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (!this.exit()) {
                return;
            }
            throw this.newTimeoutException(null);
        }

        @Override
        protected IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        @Override
        protected void timedOut() {
            FramedStream.this.closeLater(ErrorCode.CANCEL);
        }
    }

}

