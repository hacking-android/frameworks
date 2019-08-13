/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import sun.nio.cs.StreamDecoder;

public class InputStreamReader
extends Reader {
    private final StreamDecoder sd;

    public InputStreamReader(InputStream inputStream) {
        super(inputStream);
        try {
            this.sd = StreamDecoder.forInputStreamReader(inputStream, (Object)this, (String)null);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new Error(unsupportedEncodingException);
        }
    }

    public InputStreamReader(InputStream inputStream, String string) throws UnsupportedEncodingException {
        super(inputStream);
        if (string != null) {
            this.sd = StreamDecoder.forInputStreamReader(inputStream, (Object)this, string);
            return;
        }
        throw new NullPointerException("charsetName");
    }

    public InputStreamReader(InputStream inputStream, Charset charset) {
        super(inputStream);
        if (charset != null) {
            this.sd = StreamDecoder.forInputStreamReader(inputStream, (Object)this, charset);
            return;
        }
        throw new NullPointerException("charset");
    }

    public InputStreamReader(InputStream inputStream, CharsetDecoder charsetDecoder) {
        super(inputStream);
        if (charsetDecoder != null) {
            this.sd = StreamDecoder.forInputStreamReader(inputStream, (Object)this, charsetDecoder);
            return;
        }
        throw new NullPointerException("charset decoder");
    }

    @Override
    public void close() throws IOException {
        this.sd.close();
    }

    public String getEncoding() {
        return this.sd.getEncoding();
    }

    @Override
    public int read() throws IOException {
        return this.sd.read();
    }

    @Override
    public int read(char[] arrc, int n, int n2) throws IOException {
        return this.sd.read(arrc, n, n2);
    }

    @Override
    public boolean ready() throws IOException {
        return this.sd.ready();
    }
}

