/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.AsyncTimeout;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.RealBufferedSink;
import com.android.okhttp.okio.RealBufferedSource;
import com.android.okhttp.okio.Segment;
import com.android.okhttp.okio.SegmentPool;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import com.android.okhttp.okio.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Okio {
    private static final Logger logger = Logger.getLogger(Okio.class.getName());

    private Okio() {
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file != null) {
            return Okio.sink(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static BufferedSink buffer(Sink sink) {
        if (sink != null) {
            return new RealBufferedSink(sink);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static BufferedSource buffer(Source source) {
        if (source != null) {
            return new RealBufferedSource(source);
        }
        throw new IllegalArgumentException("source == null");
    }

    private static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        boolean bl = ((Throwable)((Object)assertionError)).getCause() != null && ((Throwable)((Object)assertionError)).getMessage() != null && ((Throwable)((Object)assertionError)).getMessage().contains("getsockname failed");
        return bl;
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file != null) {
            return Okio.sink(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink sink(OutputStream outputStream) {
        return Okio.sink(outputStream, new Timeout());
    }

    private static Sink sink(final OutputStream outputStream, final Timeout timeout) {
        if (outputStream != null) {
            if (timeout != null) {
                return new Sink(){

                    @Override
                    public void close() throws IOException {
                        outputStream.close();
                    }

                    @Override
                    public void flush() throws IOException {
                        outputStream.flush();
                    }

                    @Override
                    public Timeout timeout() {
                        return timeout;
                    }

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("sink(");
                        stringBuilder.append(outputStream);
                        stringBuilder.append(")");
                        return stringBuilder.toString();
                    }

                    @Override
                    public void write(Buffer buffer, long l) throws IOException {
                        Util.checkOffsetAndCount(buffer.size, 0L, l);
                        while (l > 0L) {
                            timeout.throwIfReached();
                            Segment segment = buffer.head;
                            int n = (int)Math.min(l, (long)(segment.limit - segment.pos));
                            outputStream.write(segment.data, segment.pos, n);
                            segment.pos += n;
                            l -= (long)n;
                            buffer.size -= (long)n;
                            if (segment.pos != segment.limit) continue;
                            buffer.head = segment.pop();
                            SegmentPool.recycle(segment);
                        }
                    }
                };
            }
            throw new IllegalArgumentException("timeout == null");
        }
        throw new IllegalArgumentException("out == null");
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket != null) {
            AsyncTimeout asyncTimeout = Okio.timeout(socket);
            return asyncTimeout.sink(Okio.sink(socket.getOutputStream(), asyncTimeout));
        }
        throw new IllegalArgumentException("socket == null");
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file != null) {
            return Okio.source(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Source source(InputStream inputStream) {
        return Okio.source(inputStream, new Timeout());
    }

    private static Source source(final InputStream inputStream, final Timeout timeout) {
        if (inputStream != null) {
            if (timeout != null) {
                return new Source(){

                    @Override
                    public void close() throws IOException {
                        inputStream.close();
                    }

                    @Override
                    public long read(Buffer object, long l) throws IOException {
                        if (l >= 0L) {
                            int n;
                            block6 : {
                                if (l == 0L) {
                                    return 0L;
                                }
                                try {
                                    timeout.throwIfReached();
                                    Segment segment = ((Buffer)object).writableSegment(1);
                                    n = (int)Math.min(l, (long)(8192 - segment.limit));
                                    n = inputStream.read(segment.data, segment.limit, n);
                                    if (n != -1) break block6;
                                    return -1L;
                                }
                                catch (AssertionError assertionError) {
                                    if (Okio.isAndroidGetsocknameError(assertionError)) {
                                        throw new IOException((Throwable)((Object)assertionError));
                                    }
                                    throw assertionError;
                                }
                            }
                            segment.limit += n;
                            ((Buffer)object).size += (long)n;
                            return n;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("byteCount < 0: ");
                        ((StringBuilder)object).append(l);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }

                    @Override
                    public Timeout timeout() {
                        return timeout;
                    }

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("source(");
                        stringBuilder.append(inputStream);
                        stringBuilder.append(")");
                        return stringBuilder.toString();
                    }
                };
            }
            throw new IllegalArgumentException("timeout == null");
        }
        throw new IllegalArgumentException("in == null");
    }

    public static Source source(Socket socket) throws IOException {
        if (socket != null) {
            AsyncTimeout asyncTimeout = Okio.timeout(socket);
            return asyncTimeout.source(Okio.source(socket.getInputStream(), asyncTimeout));
        }
        throw new IllegalArgumentException("socket == null");
    }

    private static AsyncTimeout timeout(final Socket socket) {
        return new AsyncTimeout(){

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
                try {
                    socket.close();
                }
                catch (AssertionError assertionError) {
                    if (Okio.isAndroidGetsocknameError(assertionError)) {
                        Logger logger = logger;
                        Level level = Level.WARNING;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to close timed out socket ");
                        stringBuilder.append(socket);
                        logger.log(level, stringBuilder.toString(), (Throwable)((Object)assertionError));
                    }
                    throw assertionError;
                }
                catch (Exception exception) {
                    Logger logger = logger;
                    Level level = Level.WARNING;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to close timed out socket ");
                    stringBuilder.append(socket);
                    logger.log(level, stringBuilder.toString(), exception);
                }
            }
        };
    }

}

