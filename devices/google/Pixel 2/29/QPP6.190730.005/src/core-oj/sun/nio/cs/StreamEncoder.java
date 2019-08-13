/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.cs;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import sun.nio.cs.HistoricallyNamedCharset;

public class StreamEncoder
extends Writer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
    private ByteBuffer bb;
    private WritableByteChannel ch;
    private Charset cs;
    private CharsetEncoder encoder;
    private boolean haveLeftoverChar = false;
    private volatile boolean isOpen = true;
    private CharBuffer lcb = null;
    private char leftoverChar;
    private final OutputStream out;

    private StreamEncoder(OutputStream outputStream, Object object, Charset charset) {
        this(outputStream, object, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE));
    }

    private StreamEncoder(OutputStream outputStream, Object object, CharsetEncoder charsetEncoder) {
        super(object);
        this.out = outputStream;
        this.ch = null;
        this.cs = charsetEncoder.charset();
        this.encoder = charsetEncoder;
        if (this.ch == null) {
            this.bb = ByteBuffer.allocate(8192);
        }
    }

    private StreamEncoder(WritableByteChannel writableByteChannel, CharsetEncoder charsetEncoder, int n) {
        this.out = null;
        this.ch = writableByteChannel;
        this.cs = charsetEncoder.charset();
        this.encoder = charsetEncoder;
        if (n < 0) {
            n = 8192;
        }
        this.bb = ByteBuffer.allocate(n);
    }

    private void ensureOpen() throws IOException {
        if (this.isOpen) {
            return;
        }
        throw new IOException("Stream closed");
    }

    /*
     * Enabled aggressive block sorting
     */
    private void flushLeftoverChar(CharBuffer charBuffer, boolean bl) throws IOException {
        if (!this.haveLeftoverChar && !bl) {
            return;
        }
        Object object = this.lcb;
        if (object == null) {
            this.lcb = CharBuffer.allocate(2);
        } else {
            ((CharBuffer)object).clear();
        }
        if (this.haveLeftoverChar) {
            this.lcb.put(this.leftoverChar);
        }
        if (charBuffer != null && charBuffer.hasRemaining()) {
            this.lcb.put(charBuffer.get());
        }
        this.lcb.flip();
        while (this.lcb.hasRemaining() || bl) {
            object = this.encoder.encode(this.lcb, this.bb, bl);
            if (((CoderResult)object).isUnderflow()) {
                if (!this.lcb.hasRemaining()) break;
                this.leftoverChar = this.lcb.get();
                if (charBuffer != null && charBuffer.hasRemaining()) {
                    this.flushLeftoverChar(charBuffer, bl);
                }
                return;
            }
            if (((CoderResult)object).isOverflow()) {
                this.writeBytes();
                continue;
            }
            ((CoderResult)object).throwException();
        }
        this.haveLeftoverChar = false;
    }

    public static StreamEncoder forEncoder(WritableByteChannel writableByteChannel, CharsetEncoder charsetEncoder, int n) {
        return new StreamEncoder(writableByteChannel, charsetEncoder, n);
    }

    public static StreamEncoder forOutputStreamWriter(OutputStream closeable, Object object, String string) throws UnsupportedEncodingException {
        String string2;
        string = string2 = string;
        if (string2 == null) {
            string = Charset.defaultCharset().name();
        }
        try {
            if (Charset.isSupported(string)) {
                closeable = new StreamEncoder((OutputStream)closeable, object, Charset.forName(string));
                return closeable;
            }
        }
        catch (IllegalCharsetNameException illegalCharsetNameException) {
            // empty catch block
        }
        throw new UnsupportedEncodingException(string);
    }

    public static StreamEncoder forOutputStreamWriter(OutputStream outputStream, Object object, Charset charset) {
        return new StreamEncoder(outputStream, object, charset);
    }

    public static StreamEncoder forOutputStreamWriter(OutputStream outputStream, Object object, CharsetEncoder charsetEncoder) {
        return new StreamEncoder(outputStream, object, charsetEncoder);
    }

    private boolean isOpen() {
        return this.isOpen;
    }

    private void writeBytes() throws IOException {
        this.bb.flip();
        int n = this.bb.limit();
        int n2 = this.bb.position();
        n = n2 <= n ? (n -= n2) : 0;
        if (n > 0) {
            WritableByteChannel writableByteChannel = this.ch;
            if (writableByteChannel != null) {
                if (writableByteChannel.write(this.bb) != n) {
                    // empty if block
                }
            } else {
                this.out.write(this.bb.array(), this.bb.arrayOffset() + n2, n);
            }
        }
        this.bb.clear();
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void flush() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            this.ensureOpen();
            this.implFlush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void flushBuffer() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            if (this.isOpen()) {
                this.implFlushBuffer();
                return;
            }
            IOException iOException = new IOException("Stream closed");
            throw iOException;
        }
    }

    public String getEncoding() {
        if (this.isOpen()) {
            return this.encodingName();
        }
        return null;
    }

    void implClose() throws IOException {
        this.flushLeftoverChar(null, true);
        try {
            do {
                CoderResult coderResult;
                if ((coderResult = this.encoder.flush(this.bb)).isUnderflow()) {
                    if (this.bb.position() > 0) {
                        this.writeBytes();
                    }
                    if (this.ch != null) {
                        this.ch.close();
                    } else {
                        this.out.close();
                    }
                    return;
                }
                if (coderResult.isOverflow()) {
                    this.writeBytes();
                    continue;
                }
                coderResult.throwException();
            } while (true);
        }
        catch (IOException iOException) {
            this.encoder.reset();
            throw iOException;
        }
    }

    void implFlush() throws IOException {
        this.implFlushBuffer();
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    void implFlushBuffer() throws IOException {
        if (this.bb.position() > 0) {
            this.writeBytes();
        }
    }

    void implWrite(char[] object, int n, int n2) throws IOException {
        CharBuffer charBuffer = CharBuffer.wrap((char[])object, n, n2);
        if (this.haveLeftoverChar) {
            this.flushLeftoverChar(charBuffer, false);
        }
        while (charBuffer.hasRemaining()) {
            object = this.encoder.encode(charBuffer, this.bb, false);
            if (((CoderResult)object).isUnderflow()) {
                if (charBuffer.remaining() != 1) break;
                this.haveLeftoverChar = true;
                this.leftoverChar = charBuffer.get();
                break;
            }
            if (((CoderResult)object).isOverflow()) {
                this.writeBytes();
                continue;
            }
            ((CoderResult)object).throwException();
        }
    }

    @Override
    public void write(int n) throws IOException {
        this.write(new char[]{(char)n}, 0, 1);
    }

    @Override
    public void write(String string, int n, int n2) throws IOException {
        if (n2 >= 0) {
            char[] arrc = new char[n2];
            string.getChars(n, n + n2, arrc, 0);
            this.write(arrc, 0, n2);
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(char[] object, int n, int n2) throws IOException {
        Object object2 = this.lock;
        synchronized (object2) {
            this.ensureOpen();
            if (n >= 0 && n <= ((char[])object).length && n2 >= 0 && n + n2 <= ((char[])object).length && n + n2 >= 0) {
                if (n2 == 0) {
                    return;
                }
                this.implWrite((char[])object, n, n2);
                return;
            }
            object = new IndexOutOfBoundsException();
            throw object;
        }
    }
}

