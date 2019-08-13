/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.Protocol;
import com.android.okhttp.internal.framed.ErrorCode;
import com.android.okhttp.internal.framed.FrameReader;
import com.android.okhttp.internal.framed.FrameWriter;
import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.framed.HeadersMode;
import com.android.okhttp.internal.framed.Hpack;
import com.android.okhttp.internal.framed.Settings;
import com.android.okhttp.internal.framed.Variant;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Http2
implements Variant {
    private static final ByteString CONNECTION_PREFACE;
    static final byte FLAG_ACK = 1;
    static final byte FLAG_COMPRESSED = 32;
    static final byte FLAG_END_HEADERS = 4;
    static final byte FLAG_END_PUSH_PROMISE = 4;
    static final byte FLAG_END_STREAM = 1;
    static final byte FLAG_NONE = 0;
    static final byte FLAG_PADDED = 8;
    static final byte FLAG_PRIORITY = 32;
    static final int INITIAL_MAX_FRAME_SIZE = 16384;
    static final byte TYPE_CONTINUATION = 9;
    static final byte TYPE_DATA = 0;
    static final byte TYPE_GOAWAY = 7;
    static final byte TYPE_HEADERS = 1;
    static final byte TYPE_PING = 6;
    static final byte TYPE_PRIORITY = 2;
    static final byte TYPE_PUSH_PROMISE = 5;
    static final byte TYPE_RST_STREAM = 3;
    static final byte TYPE_SETTINGS = 4;
    static final byte TYPE_WINDOW_UPDATE = 8;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(FrameLogger.class.getName());
        CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    }

    private static IllegalArgumentException illegalArgument(String string, Object ... arrobject) {
        throw new IllegalArgumentException(String.format(string, arrobject));
    }

    private static IOException ioException(String string, Object ... arrobject) throws IOException {
        throw new IOException(String.format(string, arrobject));
    }

    private static int lengthWithoutPadding(int n, byte by, short s) throws IOException {
        int n2 = n;
        if ((by & 8) != 0) {
            n2 = n - 1;
        }
        if (s <= n2) {
            return (short)(n2 - s);
        }
        throw Http2.ioException("PROTOCOL_ERROR padding %s > remaining length %s", s, n2);
    }

    private static int readMedium(BufferedSource bufferedSource) throws IOException {
        return (bufferedSource.readByte() & 255) << 16 | (bufferedSource.readByte() & 255) << 8 | bufferedSource.readByte() & 255;
    }

    private static void writeMedium(BufferedSink bufferedSink, int n) throws IOException {
        bufferedSink.writeByte(n >>> 16 & 255);
        bufferedSink.writeByte(n >>> 8 & 255);
        bufferedSink.writeByte(n & 255);
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    @Override
    public FrameReader newReader(BufferedSource bufferedSource, boolean bl) {
        return new Reader(bufferedSource, 4096, bl);
    }

    @Override
    public FrameWriter newWriter(BufferedSink bufferedSink, boolean bl) {
        return new Writer(bufferedSink, bl);
    }

    static final class ContinuationSource
    implements Source {
        byte flags;
        int left;
        int length;
        short padding;
        private final BufferedSource source;
        int streamId;

        public ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        private void readContinuationHeader() throws IOException {
            int n;
            int n2 = this.streamId;
            this.left = n = Http2.readMedium(this.source);
            this.length = n;
            byte by = (byte)(this.source.readByte() & 255);
            this.flags = (byte)(this.source.readByte() & 255);
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, by, this.flags));
            }
            this.streamId = this.source.readInt() & Integer.MAX_VALUE;
            if (by == 9) {
                if (this.streamId == n2) {
                    return;
                }
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
            throw Http2.ioException("%s != TYPE_CONTINUATION", new Object[]{by});
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public long read(Buffer buffer, long l) throws IOException {
            int n;
            while ((n = this.left) == 0) {
                this.source.skip(this.padding);
                this.padding = (short)(false ? 1 : 0);
                if ((this.flags & 4) != 0) {
                    return -1L;
                }
                this.readContinuationHeader();
            }
            if ((l = this.source.read(buffer, Math.min(l, (long)n))) == -1L) {
                return -1L;
            }
            this.left = (int)((long)this.left - l);
            return l;
        }

        @Override
        public Timeout timeout() {
            return this.source.timeout();
        }
    }

    static final class FrameLogger {
        private static final String[] BINARY;
        private static final String[] FLAGS;
        private static final String[] TYPES;

        static {
            Object object;
            Object[] arrobject;
            int n;
            String[] arrstring;
            TYPES = new String[]{"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
            FLAGS = new String[64];
            BINARY = new String[256];
            for (n = 0; n < (arrobject = BINARY).length; ++n) {
                arrobject[n] = String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0');
            }
            Object[] arrobject2 = FLAGS;
            arrobject2[0] = "";
            arrobject2[1] = "END_STREAM";
            arrobject = new int[]{(String)true};
            arrobject2[8] = "PADDED";
            int n2 = arrobject.length;
            for (n = 0; n < n2; ++n) {
                object = arrobject[n];
                arrobject2 = FLAGS;
                arrstring = new StringBuilder();
                arrstring.append(FLAGS[object]);
                arrstring.append("|PADDED");
                arrobject2[object | 8] = arrstring.toString();
            }
            arrobject2 = FLAGS;
            arrobject2[4] = "END_HEADERS";
            arrobject2[32] = "PRIORITY";
            arrobject2[36] = "END_HEADERS|PRIORITY";
            Object[] arrobject3 = arrobject2 = new int[3];
            arrobject3[0] = (String)4;
            arrobject3[1] = (String)32;
            arrobject3[2] = (String)36;
            object = arrobject2.length;
            for (n = 0; n < object; ++n) {
                String string = arrobject2[n];
                int n3 = arrobject.length;
                for (n2 = 0; n2 < n3; ++n2) {
                    String string2 = arrobject[n2];
                    Object object2 = FLAGS;
                    arrstring = new StringBuilder();
                    arrstring.append(FLAGS[string2]);
                    arrstring.append('|');
                    arrstring.append(FLAGS[string]);
                    object2[string2 | string] = arrstring.toString();
                    arrstring = FLAGS;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(FLAGS[string2]);
                    ((StringBuilder)object2).append('|');
                    ((StringBuilder)object2).append(FLAGS[string]);
                    ((StringBuilder)object2).append("|PADDED");
                    arrstring[string2 | string | 8] = ((StringBuilder)object2).toString();
                }
            }
            for (n = 0; n < (arrobject = FLAGS).length; ++n) {
                if (arrobject[n] != null) continue;
                arrobject[n] = BINARY[n];
            }
        }

        FrameLogger() {
        }

        static String formatFlags(byte by, byte by2) {
            if (by2 == 0) {
                return "";
            }
            if (by != 2 && by != 3) {
                if (by != 4 && by != 6) {
                    if (by != 7 && by != 8) {
                        Object object = FLAGS;
                        object = by2 < ((String[])object).length ? object[by2] : BINARY[by2];
                        if (by == 5 && (by2 & 4) != 0) {
                            return ((String)object).replace("HEADERS", "PUSH_PROMISE");
                        }
                        if (by == 0 && (by2 & 32) != 0) {
                            return ((String)object).replace("PRIORITY", "COMPRESSED");
                        }
                        return object;
                    }
                } else {
                    String string = by2 == 1 ? "ACK" : BINARY[by2];
                    return string;
                }
            }
            return BINARY[by2];
        }

        static String formatHeader(boolean bl, int n, int n2, byte by, byte by2) {
            Object object = TYPES;
            object = by < ((String[])object).length ? object[by] : String.format("0x%02x", by);
            String string = FrameLogger.formatFlags(by, by2);
            String string2 = bl ? "<<" : ">>";
            return String.format("%s 0x%08x %5d %-13s %s", string2, n, n2, object, string);
        }
    }

    static final class Reader
    implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation;
        final Hpack.Reader hpackReader;
        private final BufferedSource source;

        Reader(BufferedSource bufferedSource, int n, boolean bl) {
            this.source = bufferedSource;
            this.client = bl;
            this.continuation = new ContinuationSource(this.source);
            this.hpackReader = new Hpack.Reader(n, this.continuation);
        }

        private void readData(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            short s = 1;
            short s2 = 0;
            boolean bl = (by & 1) != 0;
            if ((by & 32) == 0) {
                s = 0;
            }
            if (s == 0) {
                short s3 = s2;
                if ((by & 8) != 0) {
                    s3 = s = (short)(this.source.readByte() & 255);
                }
                n = Http2.lengthWithoutPadding(n, by, s3);
                handler.data(bl, n2, this.source, n);
                this.source.skip(s3);
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }

        private void readGoAway(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            if (n >= 8) {
                if (n2 == 0) {
                    n2 = this.source.readInt();
                    by = (byte)this.source.readInt();
                    n -= 8;
                    ErrorCode errorCode = ErrorCode.fromHttp2(by);
                    if (errorCode != null) {
                        ByteString byteString = ByteString.EMPTY;
                        if (n > 0) {
                            byteString = this.source.readByteString(n);
                        }
                        handler.goAway(n2, errorCode, byteString);
                        return;
                    }
                    throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", new Object[]{(int)by});
                }
                throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            }
            throw Http2.ioException("TYPE_GOAWAY length < 8: %s", new Object[]{n});
        }

        private List<Header> readHeaderBlock(int n, short s, byte by, int n2) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            continuationSource.left = n;
            continuationSource.length = n;
            continuationSource.padding = s;
            continuationSource.flags = by;
            continuationSource.streamId = n2;
            this.hpackReader.readHeaders();
            return this.hpackReader.getAndResetHeaderList();
        }

        private void readHeaders(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            short s = 0;
            if (n2 != 0) {
                boolean bl = (by & 1) != 0;
                short s2 = s;
                if ((by & 8) != 0) {
                    s2 = s = (short)((short)(this.source.readByte() & 255));
                }
                s = n;
                if ((by & 32) != 0) {
                    this.readPriority(handler, n2);
                    s = n - 5;
                }
                handler.headers(false, bl, n2, -1, this.readHeaderBlock(Http2.lengthWithoutPadding(s, by, s2), s2, by, n2), HeadersMode.HTTP_20_HEADERS);
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }

        private void readPing(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            boolean bl = false;
            if (n == 8) {
                if (n2 == 0) {
                    n = this.source.readInt();
                    n2 = this.source.readInt();
                    if ((by & 1) != 0) {
                        bl = true;
                    }
                    handler.ping(bl, n, n2);
                    return;
                }
                throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
            }
            throw Http2.ioException("TYPE_PING length != 8: %s", new Object[]{n});
        }

        private void readPriority(FrameReader.Handler handler, int n) throws IOException {
            int n2 = this.source.readInt();
            boolean bl = (Integer.MIN_VALUE & n2) != 0;
            handler.priority(n, Integer.MAX_VALUE & n2, (this.source.readByte() & 255) + 1, bl);
        }

        private void readPriority(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            if (n == 5) {
                if (n2 != 0) {
                    this.readPriority(handler, n2);
                    return;
                }
                throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            }
            throw Http2.ioException("TYPE_PRIORITY length: %d != 5", new Object[]{n});
        }

        private void readPushPromise(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            short s = 0;
            if (n2 != 0) {
                short s2 = s;
                if ((by & 8) != 0) {
                    s2 = s = (short)(this.source.readByte() & 255);
                }
                handler.pushPromise(n2, this.source.readInt() & Integer.MAX_VALUE, this.readHeaderBlock(Http2.lengthWithoutPadding(n - 4, by, s2), s2, by, n2));
                return;
            }
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }

        private void readRstStream(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            if (n == 4) {
                if (n2 != 0) {
                    n = this.source.readInt();
                    ErrorCode errorCode = ErrorCode.fromHttp2(n);
                    if (errorCode != null) {
                        handler.rstStream(n2, errorCode);
                        return;
                    }
                    throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", new Object[]{n});
                }
                throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            }
            throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", new Object[]{n});
        }

        private void readSettings(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            if (n2 == 0) {
                if ((by & 1) != 0) {
                    if (n == 0) {
                        handler.ackSettings();
                        return;
                    }
                    throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                }
                if (n % 6 == 0) {
                    Settings settings = new Settings();
                    for (by = 0; by < n; by = (byte)(by + 6)) {
                        short s = this.source.readShort();
                        int n3 = this.source.readInt();
                        switch (s) {
                            default: {
                                throw Http2.ioException("PROTOCOL_ERROR invalid settings id: %s", new Object[]{s});
                            }
                            case 6: {
                                n2 = s;
                                break;
                            }
                            case 5: {
                                if (n3 >= 16384 && n3 <= 16777215) {
                                    n2 = s;
                                    break;
                                }
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[]{n3});
                            }
                            case 4: {
                                n2 = 7;
                                if (n3 >= 0) break;
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            }
                            case 3: {
                                n2 = 4;
                                break;
                            }
                            case 2: {
                                n2 = s;
                                if (n3 == 0) break;
                                if (n3 == 1) {
                                    n2 = s;
                                    break;
                                }
                                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            }
                            case 1: {
                                n2 = s;
                            }
                        }
                        settings.set(n2, 0, n3);
                    }
                    handler.settings(false, settings);
                    if (settings.getHeaderTableSize() >= 0) {
                        this.hpackReader.headerTableSizeSetting(settings.getHeaderTableSize());
                    }
                    return;
                }
                throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", new Object[]{n});
            }
            throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
        }

        private void readWindowUpdate(FrameReader.Handler handler, int n, byte by, int n2) throws IOException {
            if (n == 4) {
                long l = (long)this.source.readInt() & Integer.MAX_VALUE;
                if (l != 0L) {
                    handler.windowUpdate(n2, l);
                    return;
                }
                throw Http2.ioException("windowSizeIncrement was 0", new Object[]{l});
            }
            throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", new Object[]{n});
        }

        @Override
        public void close() throws IOException {
            this.source.close();
        }

        @Override
        public boolean nextFrame(FrameReader.Handler handler) throws IOException {
            try {
                this.source.require(9L);
            }
            catch (IOException iOException) {
                return false;
            }
            int n = Http2.readMedium(this.source);
            if (n >= 0 && n <= 16384) {
                byte by = (byte)(this.source.readByte() & 255);
                byte by2 = (byte)(this.source.readByte() & 255);
                int n2 = this.source.readInt() & Integer.MAX_VALUE;
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(FrameLogger.formatHeader(true, n2, n, by, by2));
                }
                switch (by) {
                    default: {
                        this.source.skip(n);
                        break;
                    }
                    case 8: {
                        this.readWindowUpdate(handler, n, by2, n2);
                        break;
                    }
                    case 7: {
                        this.readGoAway(handler, n, by2, n2);
                        break;
                    }
                    case 6: {
                        this.readPing(handler, n, by2, n2);
                        break;
                    }
                    case 5: {
                        this.readPushPromise(handler, n, by2, n2);
                        break;
                    }
                    case 4: {
                        this.readSettings(handler, n, by2, n2);
                        break;
                    }
                    case 3: {
                        this.readRstStream(handler, n, by2, n2);
                        break;
                    }
                    case 2: {
                        this.readPriority(handler, n, by2, n2);
                        break;
                    }
                    case 1: {
                        this.readHeaders(handler, n, by2, n2);
                        break;
                    }
                    case 0: {
                        this.readData(handler, n, by2, n2);
                    }
                }
                return true;
            }
            throw Http2.ioException("FRAME_SIZE_ERROR: %s", new Object[]{n});
        }

        @Override
        public void readConnectionPreface() throws IOException {
            if (this.client) {
                return;
            }
            ByteString byteString = this.source.readByteString(CONNECTION_PREFACE.size());
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("<< CONNECTION %s", byteString.hex()));
            }
            if (CONNECTION_PREFACE.equals(byteString)) {
                return;
            }
            throw Http2.ioException("Expected a connection header but was %s", new Object[]{byteString.utf8()});
        }
    }

    static final class Writer
    implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer hpackBuffer;
        private final Hpack.Writer hpackWriter;
        private int maxFrameSize;
        private final BufferedSink sink;

        Writer(BufferedSink bufferedSink, boolean bl) {
            this.sink = bufferedSink;
            this.client = bl;
            this.hpackBuffer = new Buffer();
            this.hpackWriter = new Hpack.Writer(this.hpackBuffer);
            this.maxFrameSize = 16384;
        }

        private void writeContinuationFrames(int n, long l) throws IOException {
            while (l > 0L) {
                byte by;
                int n2;
                byte by2 = (l -= (long)(n2 = (int)Math.min((long)this.maxFrameSize, l))) == 0L ? (by = 4) : (by = 0);
                this.frameHeader(n, n2, (byte)9, by2);
                this.sink.write(this.hpackBuffer, n2);
            }
        }

        @Override
        public void ackSettings(Settings object) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    this.maxFrameSize = ((Settings)object).getMaxFrameSize(this.maxFrameSize);
                    this.frameHeader(0, 0, (byte)4, (byte)1);
                    this.sink.flush();
                    return;
                }
                object = new IOException("closed");
                throw object;
            }
        }

        @Override
        public void close() throws IOException {
            synchronized (this) {
                this.closed = true;
                this.sink.close();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void connectionPreface() throws IOException {
            synchronized (this) {
                if (this.closed) {
                    IOException iOException = new IOException("closed");
                    throw iOException;
                }
                boolean bl = this.client;
                if (!bl) {
                    return;
                }
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine(String.format(">> CONNECTION %s", CONNECTION_PREFACE.hex()));
                }
                this.sink.write(CONNECTION_PREFACE.toByteArray());
                this.sink.flush();
                return;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void data(boolean bl, int n, Buffer object, int n2) throws IOException {
            synchronized (this) {
                void var4_4;
                byte by;
                if (this.closed) {
                    object = new IOException("closed");
                    throw object;
                }
                byte by2 = by = 0;
                if (bl) {
                    by2 = by = (byte)(false | true ? 1 : 0);
                }
                this.dataFrame(n, by2, (Buffer)object, (int)var4_4);
                return;
            }
        }

        void dataFrame(int n, byte by, Buffer buffer, int n2) throws IOException {
            this.frameHeader(n, n2, (byte)0, by);
            if (n2 > 0) {
                this.sink.write(buffer, n2);
            }
        }

        @Override
        public void flush() throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    this.sink.flush();
                    return;
                }
                IOException iOException = new IOException("closed");
                throw iOException;
            }
        }

        void frameHeader(int n, int n2, byte by, byte by2) throws IOException {
            int n3;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(FrameLogger.formatHeader(false, n, n2, by, by2));
            }
            if (n2 <= (n3 = this.maxFrameSize)) {
                if ((Integer.MIN_VALUE & n) == 0) {
                    Http2.writeMedium(this.sink, n2);
                    this.sink.writeByte(by & 255);
                    this.sink.writeByte(by2 & 255);
                    this.sink.writeInt(Integer.MAX_VALUE & n);
                    return;
                }
                throw Http2.illegalArgument("reserved bit set: %s", new Object[]{n});
            }
            throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", new Object[]{n3, n2});
        }

        @Override
        public void goAway(int n, ErrorCode object, byte[] arrby) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    if (object.httpCode != -1) {
                        this.frameHeader(0, arrby.length + 8, (byte)7, (byte)0);
                        this.sink.writeInt(n);
                        this.sink.writeInt(object.httpCode);
                        if (arrby.length > 0) {
                            this.sink.write(arrby);
                        }
                        this.sink.flush();
                        return;
                    }
                    throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
                }
                object = new IOException("closed");
                throw object;
            }
        }

        @Override
        public void headers(int n, List<Header> object) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    this.headers(false, n, (List<Header>)object);
                    return;
                }
                object = new IOException("closed");
                throw object;
            }
        }

        void headers(boolean bl, int n, List<Header> list) throws IOException {
            if (!this.closed) {
                this.hpackWriter.writeHeaders(list);
                long l = this.hpackBuffer.size();
                int n2 = (int)Math.min((long)this.maxFrameSize, l);
                byte by = l == (long)n2 ? (byte)4 : 0;
                byte by2 = by;
                if (bl) {
                    by2 = by = (byte)((byte)(by | 1));
                }
                this.frameHeader(n, n2, (byte)1, by2);
                this.sink.write(this.hpackBuffer, n2);
                if (l > (long)n2) {
                    this.writeContinuationFrames(n, l - (long)n2);
                }
                return;
            }
            throw new IOException("closed");
        }

        @Override
        public int maxDataLength() {
            return this.maxFrameSize;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void ping(boolean bl, int n, int n2) throws IOException {
            synchronized (this) {
                byte by;
                byte by2;
                if (this.closed) {
                    IOException iOException = new IOException("closed");
                    throw iOException;
                }
                byte by3 = bl ? (by = 1) : (by2 = 0);
                this.frameHeader(0, 8, (byte)6, by3);
                this.sink.writeInt(n);
                this.sink.writeInt(n2);
                this.sink.flush();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void pushPromise(int n, int n2, List<Header> object) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    object = new IOException("closed");
                    throw object;
                }
                this.hpackWriter.writeHeaders((List<Header>)object);
                long l = this.hpackBuffer.size();
                int n3 = this.maxFrameSize;
                byte by = 4;
                n3 = (int)Math.min((long)(n3 - 4), l);
                byte by2 = l == (long)n3 ? by : (by = 0);
                this.frameHeader(n, n3 + 4, (byte)5, by2);
                this.sink.writeInt(Integer.MAX_VALUE & n2);
                this.sink.write(this.hpackBuffer, n3);
                if (l > (long)n3) {
                    this.writeContinuationFrames(n, l - (long)n3);
                }
                return;
            }
        }

        @Override
        public void rstStream(int n, ErrorCode object) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    if (object.httpCode != -1) {
                        this.frameHeader(n, 4, (byte)3, (byte)0);
                        this.sink.writeInt(object.httpCode);
                        this.sink.flush();
                        return;
                    }
                    object = new IllegalArgumentException();
                    throw object;
                }
                object = new IOException("closed");
                throw object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void settings(Settings object) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    object = new IOException("closed");
                    throw object;
                }
                this.frameHeader(0, ((Settings)object).size() * 6, (byte)4, (byte)0);
                int n = 0;
                do {
                    if (n >= 10) {
                        this.sink.flush();
                        return;
                    }
                    if (((Settings)object).isSet(n)) {
                        int n2;
                        int n3 = n;
                        if (n3 == 4) {
                            n2 = 3;
                        } else {
                            n2 = n3;
                            if (n3 == 7) {
                                n2 = 4;
                            }
                        }
                        this.sink.writeShort(n2);
                        this.sink.writeInt(((Settings)object).get(n));
                    }
                    ++n;
                } while (true);
            }
        }

        @Override
        public void synReply(boolean bl, int n, List<Header> object) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    this.headers(bl, n, (List<Header>)object);
                    return;
                }
                object = new IOException("closed");
                throw object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void synStream(boolean bl, boolean bl2, int n, int n2, List<Header> object) throws IOException {
            synchronized (this) {
                Throwable throwable2;
                if (!bl2) {
                    try {
                        if (!this.closed) {
                            this.headers(bl, n, (List<Header>)object);
                            return;
                        }
                        object = new IOException("closed");
                        throw object;
                    }
                    catch (Throwable throwable2) {}
                } else {
                    object = new UnsupportedOperationException();
                    throw object;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void windowUpdate(int n, long l) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    IOException iOException = new IOException("closed");
                    throw iOException;
                }
                if (l != 0L && l <= Integer.MAX_VALUE) {
                    this.frameHeader(n, 4, (byte)8, (byte)0);
                    this.sink.writeInt((int)l);
                    this.sink.flush();
                    return;
                }
                throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", new Object[]{l});
            }
        }
    }

}

