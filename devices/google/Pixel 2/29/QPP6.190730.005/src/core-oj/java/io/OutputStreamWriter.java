/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import sun.nio.cs.StreamEncoder;

public class OutputStreamWriter
extends Writer {
    private final StreamEncoder se;

    public OutputStreamWriter(OutputStream outputStream) {
        super(outputStream);
        try {
            this.se = StreamEncoder.forOutputStreamWriter(outputStream, (Object)this, (String)null);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new Error(unsupportedEncodingException);
        }
    }

    public OutputStreamWriter(OutputStream outputStream, String string) throws UnsupportedEncodingException {
        super(outputStream);
        if (string != null) {
            this.se = StreamEncoder.forOutputStreamWriter(outputStream, (Object)this, string);
            return;
        }
        throw new NullPointerException("charsetName");
    }

    public OutputStreamWriter(OutputStream outputStream, Charset charset) {
        super(outputStream);
        if (charset != null) {
            this.se = StreamEncoder.forOutputStreamWriter(outputStream, (Object)this, charset);
            return;
        }
        throw new NullPointerException("charset");
    }

    public OutputStreamWriter(OutputStream outputStream, CharsetEncoder charsetEncoder) {
        super(outputStream);
        if (charsetEncoder != null) {
            this.se = StreamEncoder.forOutputStreamWriter(outputStream, (Object)this, charsetEncoder);
            return;
        }
        throw new NullPointerException("charset encoder");
    }

    @Override
    public void close() throws IOException {
        this.se.close();
    }

    @Override
    public void flush() throws IOException {
        this.se.flush();
    }

    void flushBuffer() throws IOException {
        this.se.flushBuffer();
    }

    public String getEncoding() {
        return this.se.getEncoding();
    }

    @Override
    public void write(int n) throws IOException {
        this.se.write(n);
    }

    @Override
    public void write(String string, int n, int n2) throws IOException {
        this.se.write(string, n, n2);
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        this.se.write(arrc, n, n2);
    }
}

