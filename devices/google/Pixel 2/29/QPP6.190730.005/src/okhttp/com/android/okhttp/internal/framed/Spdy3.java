/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.framed;

import com.android.okhttp.Protocol;
import com.android.okhttp.internal.Util;
import com.android.okhttp.internal.framed.ErrorCode;
import com.android.okhttp.internal.framed.FrameReader;
import com.android.okhttp.internal.framed.FrameWriter;
import com.android.okhttp.internal.framed.Header;
import com.android.okhttp.internal.framed.HeadersMode;
import com.android.okhttp.internal.framed.NameValueBlockReader;
import com.android.okhttp.internal.framed.Settings;
import com.android.okhttp.internal.framed.Variant;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSink;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.DeflaterSink;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Sink;
import com.android.okhttp.okio.Source;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.Deflater;

public final class Spdy3
implements Variant {
    static final byte[] DICTIONARY;
    static final int FLAG_FIN = 1;
    static final int FLAG_UNIDIRECTIONAL = 2;
    static final int TYPE_DATA = 0;
    static final int TYPE_GOAWAY = 7;
    static final int TYPE_HEADERS = 8;
    static final int TYPE_PING = 6;
    static final int TYPE_RST_STREAM = 3;
    static final int TYPE_SETTINGS = 4;
    static final int TYPE_SYN_REPLY = 2;
    static final int TYPE_SYN_STREAM = 1;
    static final int TYPE_WINDOW_UPDATE = 9;
    static final int VERSION = 3;

    static {
        try {
            DICTIONARY = "\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004head\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006delete\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000\u000eaccept-charset\u0000\u0000\u0000\u000faccept-encoding\u0000\u0000\u0000\u000faccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-control\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000\u0000\u000econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000\u000bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expect\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocation\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000bretry-after\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trailer\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-cookie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authoritative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service UnavailableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Thu, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml,application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.".getBytes(Util.UTF_8.name());
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError();
        }
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.SPDY_3;
    }

    @Override
    public FrameReader newReader(BufferedSource bufferedSource, boolean bl) {
        return new Reader(bufferedSource, bl);
    }

    @Override
    public FrameWriter newWriter(BufferedSink bufferedSink, boolean bl) {
        return new Writer(bufferedSink, bl);
    }

    static final class Reader
    implements FrameReader {
        private final boolean client;
        private final NameValueBlockReader headerBlockReader;
        private final BufferedSource source;

        Reader(BufferedSource bufferedSource, boolean bl) {
            this.source = bufferedSource;
            this.headerBlockReader = new NameValueBlockReader(this.source);
            this.client = bl;
        }

        private static IOException ioException(String string, Object ... arrobject) throws IOException {
            throw new IOException(String.format(string, arrobject));
        }

        private void readGoAway(FrameReader.Handler handler, int n, int n2) throws IOException {
            if (n2 == 8) {
                n = this.source.readInt();
                n2 = this.source.readInt();
                ErrorCode errorCode = ErrorCode.fromSpdyGoAway(n2);
                if (errorCode != null) {
                    handler.goAway(n & Integer.MAX_VALUE, errorCode, ByteString.EMPTY);
                    return;
                }
                throw Reader.ioException("TYPE_GOAWAY unexpected error code: %d", n2);
            }
            throw Reader.ioException("TYPE_GOAWAY length: %d != 8", n2);
        }

        private void readHeaders(FrameReader.Handler handler, int n, int n2) throws IOException {
            handler.headers(false, false, Integer.MAX_VALUE & this.source.readInt(), -1, this.headerBlockReader.readNameValueBlock(n2 - 4), HeadersMode.SPDY_HEADERS);
        }

        private void readPing(FrameReader.Handler handler, int n, int n2) throws IOException {
            boolean bl = true;
            if (n2 == 4) {
                n = this.source.readInt();
                boolean bl2 = this.client;
                boolean bl3 = (n & 1) == 1;
                bl3 = bl2 == bl3 ? bl : false;
                handler.ping(bl3, n, 0);
                return;
            }
            throw Reader.ioException("TYPE_PING length: %d != 4", n2);
        }

        private void readRstStream(FrameReader.Handler handler, int n, int n2) throws IOException {
            if (n2 == 8) {
                n = this.source.readInt();
                n2 = this.source.readInt();
                ErrorCode errorCode = ErrorCode.fromSpdy3Rst(n2);
                if (errorCode != null) {
                    handler.rstStream(n & Integer.MAX_VALUE, errorCode);
                    return;
                }
                throw Reader.ioException("TYPE_RST_STREAM unexpected error code: %d", n2);
            }
            throw Reader.ioException("TYPE_RST_STREAM length: %d != 8", n2);
        }

        private void readSettings(FrameReader.Handler handler, int n, int n2) throws IOException {
            int n3 = this.source.readInt();
            boolean bl = false;
            if (n2 == n3 * 8 + 4) {
                Settings settings = new Settings();
                for (n2 = 0; n2 < n3; ++n2) {
                    int n4 = this.source.readInt();
                    settings.set(16777215 & n4, (-16777216 & n4) >>> 24, this.source.readInt());
                }
                if ((n & 1) != 0) {
                    bl = true;
                }
                handler.settings(bl, settings);
                return;
            }
            throw Reader.ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", n2, n3);
        }

        private void readSynReply(FrameReader.Handler handler, int n, int n2) throws IOException {
            int n3 = this.source.readInt();
            List<Header> list = this.headerBlockReader.readNameValueBlock(n2 - 4);
            boolean bl = (n & 1) != 0;
            handler.headers(false, bl, Integer.MAX_VALUE & n3, -1, list, HeadersMode.SPDY_REPLY);
        }

        private void readSynStream(FrameReader.Handler handler, int n, int n2) throws IOException {
            int n3 = this.source.readInt();
            int n4 = this.source.readInt();
            this.source.readShort();
            List<Header> list = this.headerBlockReader.readNameValueBlock(n2 - 10);
            boolean bl = false;
            boolean bl2 = (n & 1) != 0;
            if ((n & 2) != 0) {
                bl = true;
            }
            handler.headers(bl, bl2, n3 & Integer.MAX_VALUE, Integer.MAX_VALUE & n4, list, HeadersMode.SPDY_SYN_STREAM);
        }

        private void readWindowUpdate(FrameReader.Handler handler, int n, int n2) throws IOException {
            if (n2 == 8) {
                n = this.source.readInt();
                long l = Integer.MAX_VALUE & this.source.readInt();
                if (l != 0L) {
                    handler.windowUpdate(n & Integer.MAX_VALUE, l);
                    return;
                }
                throw Reader.ioException("windowSizeIncrement was 0", l);
            }
            throw Reader.ioException("TYPE_WINDOW_UPDATE length: %d != 8", n2);
        }

        @Override
        public void close() throws IOException {
            this.headerBlockReader.close();
        }

        @Override
        public boolean nextFrame(FrameReader.Handler object) throws IOException {
            int n;
            boolean bl;
            int n2;
            int n3;
            block13 : {
                int n4;
                block14 : {
                    bl = false;
                    try {
                        n2 = this.source.readInt();
                        n3 = this.source.readInt();
                        n4 = (Integer.MIN_VALUE & n2) != 0 ? 1 : 0;
                        n = (-16777216 & n3) >>> 24;
                        n3 = 16777215 & n3;
                        if (n4 == 0) break block13;
                        n4 = (2147418112 & n2) >>> 16;
                        if (n4 != 3) break block14;
                    }
                    catch (IOException iOException) {
                        return false;
                    }
                    switch (65535 & n2) {
                        default: {
                            this.source.skip(n3);
                            return true;
                        }
                        case 9: {
                            this.readWindowUpdate((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 8: {
                            this.readHeaders((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 7: {
                            this.readGoAway((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 6: {
                            this.readPing((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 4: {
                            this.readSettings((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 3: {
                            this.readRstStream((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 2: {
                            this.readSynReply((FrameReader.Handler)object, n, n3);
                            return true;
                        }
                        case 1: 
                    }
                    this.readSynStream((FrameReader.Handler)object, n, n3);
                    return true;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("version != 3: ");
                ((StringBuilder)object).append(n4);
                throw new ProtocolException(((StringBuilder)object).toString());
            }
            if ((n & 1) != 0) {
                bl = true;
            }
            object.data(bl, Integer.MAX_VALUE & n2, this.source, n3);
            return true;
        }

        @Override
        public void readConnectionPreface() {
        }
    }

    static final class Writer
    implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer headerBlockBuffer;
        private final BufferedSink headerBlockOut;
        private final BufferedSink sink;

        Writer(BufferedSink object, boolean bl) {
            this.sink = object;
            this.client = bl;
            object = new Deflater();
            ((Deflater)object).setDictionary(DICTIONARY);
            this.headerBlockBuffer = new Buffer();
            this.headerBlockOut = Okio.buffer(new DeflaterSink((Sink)this.headerBlockBuffer, (Deflater)object));
        }

        private void writeNameValueBlockToBuffer(List<Header> list) throws IOException {
            this.headerBlockOut.writeInt(list.size());
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                ByteString byteString = list.get((int)i).name;
                this.headerBlockOut.writeInt(byteString.size());
                this.headerBlockOut.write(byteString);
                byteString = list.get((int)i).value;
                this.headerBlockOut.writeInt(byteString.size());
                this.headerBlockOut.write(byteString);
            }
            this.headerBlockOut.flush();
        }

        @Override
        public void ackSettings(Settings settings) {
        }

        @Override
        public void close() throws IOException {
            synchronized (this) {
                this.closed = true;
                Util.closeAll(this.sink, this.headerBlockOut);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void connectionPreface() {
            // MONITORENTER : this
            // MONITOREXIT : this
        }

        @Override
        public void data(boolean bl, int n, Buffer buffer, int n2) throws IOException {
            synchronized (this) {
                int n3 = bl ? 1 : 0;
                this.sendDataFrame(n, n3, buffer, n2);
                return;
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

        @Override
        public void goAway(int n, ErrorCode object, byte[] arrby) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    if (object.spdyGoAwayCode != -1) {
                        this.sink.writeInt(-2147287040 | 65535 & 7);
                        this.sink.writeInt((0 & 255) << 24 | 16777215 & 8);
                        this.sink.writeInt(n);
                        this.sink.writeInt(object.spdyGoAwayCode);
                        this.sink.flush();
                        return;
                    }
                    object = new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
                    throw object;
                }
                object = new IOException("closed");
                throw object;
            }
        }

        @Override
        public void headers(int n, List<Header> object) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    this.writeNameValueBlockToBuffer((List<Header>)object);
                    int n2 = (int)(this.headerBlockBuffer.size() + 4L);
                    this.sink.writeInt(-2147287040 | 65535 & 8);
                    this.sink.writeInt((0 & 255) << 24 | 16777215 & n2);
                    this.sink.writeInt(Integer.MAX_VALUE & n);
                    this.sink.writeAll(this.headerBlockBuffer);
                    return;
                }
                object = new IOException("closed");
                throw object;
            }
        }

        @Override
        public int maxDataLength() {
            return 16383;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void ping(boolean bl, int n, int n2) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    IOException iOException = new IOException("closed");
                    throw iOException;
                }
                boolean bl2 = this.client;
                boolean bl3 = false;
                boolean bl4 = (n & 1) == 1;
                if (bl2 != bl4) {
                    bl3 = true;
                }
                if (bl == bl3) {
                    this.sink.writeInt(-2147287040 | 65535 & 6);
                    this.sink.writeInt((0 & 255) << 24 | 16777215 & 4);
                    this.sink.writeInt(n);
                    this.sink.flush();
                    return;
                }
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("payload != reply");
                throw illegalArgumentException;
            }
        }

        @Override
        public void pushPromise(int n, int n2, List<Header> list) throws IOException {
        }

        @Override
        public void rstStream(int n, ErrorCode object) throws IOException {
            synchronized (this) {
                if (!this.closed) {
                    if (object.spdyRstCode != -1) {
                        this.sink.writeInt(-2147287040 | 65535 & 3);
                        this.sink.writeInt((0 & 255) << 24 | 16777215 & 8);
                        this.sink.writeInt(Integer.MAX_VALUE & n);
                        this.sink.writeInt(object.spdyRstCode);
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

        void sendDataFrame(int n, int n2, Buffer object, int n3) throws IOException {
            if (!this.closed) {
                if ((long)n3 <= 0xFFFFFFL) {
                    this.sink.writeInt(Integer.MAX_VALUE & n);
                    this.sink.writeInt((n2 & 255) << 24 | 16777215 & n3);
                    if (n3 > 0) {
                        this.sink.write((Buffer)object, n3);
                    }
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("FRAME_TOO_LARGE max size is 16Mib: ");
                ((StringBuilder)object).append(n3);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IOException("closed");
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
                int n = ((Settings)object).size();
                this.sink.writeInt(-2147287040 | 65535 & 4);
                this.sink.writeInt((0 & 255) << 24 | n * 8 + 4 & 16777215);
                this.sink.writeInt(n);
                n = 0;
                do {
                    if (n > 10) {
                        this.sink.flush();
                        return;
                    }
                    if (((Settings)object).isSet(n)) {
                        int n2 = ((Settings)object).flags(n);
                        this.sink.writeInt((n2 & 255) << 24 | n & 16777215);
                        this.sink.writeInt(((Settings)object).get(n));
                    }
                    ++n;
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void synReply(boolean bl, int n, List<Header> object) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    object = new IOException("closed");
                    throw object;
                }
                this.writeNameValueBlockToBuffer((List<Header>)object);
                int n2 = bl ? 1 : 0;
                int n3 = (int)(this.headerBlockBuffer.size() + 4L);
                this.sink.writeInt(-2147287040 | 65535 & 2);
                this.sink.writeInt((n2 & 255) << 24 | 16777215 & n3);
                this.sink.writeInt(Integer.MAX_VALUE & n);
                this.sink.writeAll(this.headerBlockBuffer);
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
        public void synStream(boolean bl, boolean bl2, int n, int n2, List<Header> object) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    object = new IOException("closed");
                    throw object;
                }
                this.writeNameValueBlockToBuffer((List<Header>)object);
                int n3 = (int)(this.headerBlockBuffer.size() + 10L);
                int n4 = bl2 ? 2 : 0;
                this.sink.writeInt(-2147287040 | 65535 & 1);
                this.sink.writeInt(((n4 | bl) & 255) << 24 | 16777215 & n3);
                this.sink.writeInt(n & Integer.MAX_VALUE);
                this.sink.writeInt(Integer.MAX_VALUE & n2);
                this.sink.writeShort((0 & 7) << 13 | (0 & 31) << 8 | 0 & 255);
                this.sink.writeAll(this.headerBlockBuffer);
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
        public void windowUpdate(int n, long l) throws IOException {
            synchronized (this) {
                if (this.closed) {
                    IOException iOException = new IOException("closed");
                    throw iOException;
                }
                if (l != 0L && l <= Integer.MAX_VALUE) {
                    this.sink.writeInt(-2147287040 | 65535 & 9);
                    this.sink.writeInt((0 & 255) << 24 | 16777215 & 8);
                    this.sink.writeInt(n);
                    this.sink.writeInt((int)l);
                    this.sink.flush();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("windowSizeIncrement must be between 1 and 0x7fffffff: ");
                stringBuilder.append(l);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
        }
    }

}

