/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.cs;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import sun.nio.ch.ChannelInputStream;
import sun.nio.cs.HistoricallyNamedCharset;

public class StreamDecoder
extends Reader {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
    private static final int MIN_BYTE_BUFFER_SIZE = 32;
    private static volatile boolean channelsAvailable = true;
    private ByteBuffer bb;
    private ReadableByteChannel ch;
    private Charset cs;
    private CharsetDecoder decoder;
    private boolean haveLeftoverChar = false;
    private InputStream in;
    private volatile boolean isOpen = true;
    private char leftoverChar;
    private boolean needsFlush = false;

    StreamDecoder(InputStream inputStream, Object object, Charset charset) {
        this(inputStream, object, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
    }

    StreamDecoder(InputStream inputStream, Object object, CharsetDecoder charsetDecoder) {
        super(object);
        this.cs = charsetDecoder.charset();
        this.decoder = charsetDecoder;
        if (this.ch == null) {
            this.in = inputStream;
            this.ch = null;
            this.bb = ByteBuffer.allocate(8192);
        }
        this.bb.flip();
    }

    StreamDecoder(ReadableByteChannel readableByteChannel, CharsetDecoder charsetDecoder, int n) {
        this.in = null;
        this.ch = readableByteChannel;
        this.decoder = charsetDecoder;
        this.cs = charsetDecoder.charset();
        int n2 = 32;
        if (n < 0) {
            n = 8192;
        } else if (n < 32) {
            n = n2;
        }
        this.bb = ByteBuffer.allocate(n);
        this.bb.flip();
    }

    private void ensureOpen() throws IOException {
        if (this.isOpen) {
            return;
        }
        throw new IOException("Stream closed");
    }

    public static StreamDecoder forDecoder(ReadableByteChannel readableByteChannel, CharsetDecoder charsetDecoder, int n) {
        return new StreamDecoder(readableByteChannel, charsetDecoder, n);
    }

    public static StreamDecoder forInputStreamReader(InputStream closeable, Object object, String string) throws UnsupportedEncodingException {
        String string2;
        string = string2 = string;
        if (string2 == null) {
            string = Charset.defaultCharset().name();
        }
        try {
            if (Charset.isSupported(string)) {
                closeable = new StreamDecoder((InputStream)closeable, object, Charset.forName(string));
                return closeable;
            }
        }
        catch (IllegalCharsetNameException illegalCharsetNameException) {
            // empty catch block
        }
        throw new UnsupportedEncodingException(string);
    }

    public static StreamDecoder forInputStreamReader(InputStream inputStream, Object object, Charset charset) {
        return new StreamDecoder(inputStream, object, charset);
    }

    public static StreamDecoder forInputStreamReader(InputStream inputStream, Object object, CharsetDecoder charsetDecoder) {
        return new StreamDecoder(inputStream, object, charsetDecoder);
    }

    private static FileChannel getChannel(FileInputStream closeable) {
        if (!channelsAvailable) {
            return null;
        }
        try {
            closeable = closeable.getChannel();
            return closeable;
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            channelsAvailable = false;
            return null;
        }
    }

    private boolean inReady() {
        boolean bl = false;
        try {
            boolean bl2;
            if (this.in != null && this.in.available() > 0 || (bl2 = this.ch instanceof FileChannel)) {
                bl = true;
            }
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    private boolean isOpen() {
        return this.isOpen;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int read0() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (this.haveLeftoverChar) {
                this.haveLeftoverChar = false;
                return this.leftoverChar;
            }
            char[] arrc = new char[2];
            int n = this.read(arrc, 0, 2);
            if (n == -1) {
                return -1;
            }
            if (n == 1) return arrc[0];
            if (n != 2) {
                return -1;
            }
            this.leftoverChar = arrc[1];
            this.haveLeftoverChar = true;
            return arrc[0];
        }
    }

    private int readBytes() throws IOException {
        block14 : {
            int n;
            block10 : {
                int n2;
                block12 : {
                    block13 : {
                        block11 : {
                            block9 : {
                                this.bb.compact();
                                if (this.ch == null) break block9;
                                n = ChannelInputStream.read(this.ch, this.bb, true);
                                if (n >= 0) break block10;
                                this.bb.flip();
                                return n;
                            }
                            n = this.bb.limit();
                            n2 = this.bb.position();
                            if (n2 > n) break block11;
                            n -= n2;
                            break block13;
                        }
                        n = 0;
                    }
                    n = this.in.read(this.bb.array(), this.bb.arrayOffset() + n2, n);
                    if (n >= 0) break block12;
                    this.bb.flip();
                    return n;
                }
                if (n == 0) break block14;
                this.bb.position(n2 + n);
            }
            n = this.bb.remaining();
            return n;
        }
        IOException iOException = new IOException("Underlying input stream returned zero bytes");
        throw iOException;
        finally {
            this.bb.flip();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (!this.isOpen) {
                return;
            }
            this.implClose();
            this.isOpen = false;
            return;
        }
    }

    String encodingName() {
        Object object = this.cs;
        object = object instanceof HistoricallyNamedCharset ? ((HistoricallyNamedCharset)object).historicalName() : ((Charset)object).name();
        return object;
    }

    public String getEncoding() {
        if (this.isOpen()) {
            return this.encodingName();
        }
        return null;
    }

    void implClose() throws IOException {
        ReadableByteChannel readableByteChannel = this.ch;
        if (readableByteChannel != null) {
            readableByteChannel.close();
        } else {
            this.in.close();
        }
    }

    int implRead(char[] object, int n, int n2) throws IOException {
        Object object2;
        object = object2 = CharBuffer.wrap((char[])object, n, n2 - n);
        if (((Buffer)object2).position() != 0) {
            object = ((CharBuffer)object2).slice();
        }
        if (this.needsFlush) {
            object2 = this.decoder.flush((CharBuffer)object);
            if (((CoderResult)object2).isOverflow()) {
                return ((Buffer)object).position();
            }
            if (((CoderResult)object2).isUnderflow()) {
                if (((Buffer)object).position() == 0) {
                    return -1;
                }
                return ((Buffer)object).position();
            }
            ((CoderResult)object2).throwException();
        }
        boolean bl = false;
        do {
            block17 : {
                block16 : {
                    block15 : {
                        if (!((CoderResult)(object2 = this.decoder.decode(this.bb, (CharBuffer)object, bl))).isUnderflow()) break block15;
                        if (!bl && ((Buffer)object).hasRemaining() && (((Buffer)object).position() <= 0 || this.inReady())) {
                            if (this.readBytes() >= 0) continue;
                            bl = true;
                            continue;
                        }
                        break block16;
                    }
                    if (!((CoderResult)object2).isOverflow()) break block17;
                }
                if (bl) {
                    object2 = this.decoder.flush((CharBuffer)object);
                    if (((CoderResult)object2).isOverflow()) {
                        this.needsFlush = true;
                        return ((Buffer)object).position();
                    }
                    this.decoder.reset();
                    if (!((CoderResult)object2).isUnderflow()) {
                        ((CoderResult)object2).throwException();
                    }
                }
                if (((Buffer)object).position() == 0 && bl) {
                    return -1;
                }
                return ((Buffer)object).position();
            }
            ((CoderResult)object2).throwException();
        } while (true);
    }

    boolean implReady() {
        boolean bl = this.bb.hasRemaining() || this.inReady();
        return bl;
    }

    @Override
    public int read() throws IOException {
        return this.read0();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(char[] object, int n, int n2) throws IOException {
        int n3 = n;
        int n4 = n2;
        Object object2 = this.lock;
        synchronized (object2) {
            this.ensureOpen();
            if (n3 >= 0 && n3 <= ((char[])object).length && n4 >= 0 && n3 + n4 <= ((char[])object).length && n3 + n4 >= 0) {
                if (n4 == 0) {
                    return 0;
                }
                n = 0;
                n2 = n3;
                int n5 = n4;
                if (this.haveLeftoverChar) {
                    object[n3] = this.leftoverChar;
                    n2 = n3 + 1;
                    n5 = n4 - 1;
                    this.haveLeftoverChar = false;
                    n = 1;
                    if (n5 == 0) return 1;
                    if (!this.implReady()) {
                        return 1;
                    }
                }
                if (n5 != 1) {
                    n2 = this.implRead((char[])object, n2, n2 + n5);
                    return n2 + n;
                }
                n4 = this.read0();
                n5 = -1;
                if (n4 != -1) {
                    object[n2] = (char)n4;
                    return n + 1;
                }
                if (n != 0) return n;
                return n5;
            }
            object = new IndexOutOfBoundsException();
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean ready() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            if (this.haveLeftoverChar) return true;
            if (!this.implReady()) return false;
            return true;
        }
    }
}

