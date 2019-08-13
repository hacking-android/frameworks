/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;
import sun.misc.CEStreamExhausted;

public abstract class CharacterDecoder {
    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    protected void decodeAtom(PushbackInputStream pushbackInputStream, OutputStream outputStream, int n) throws IOException {
        throw new CEStreamExhausted();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void decodeBuffer(InputStream inputStream, OutputStream outputStream) throws IOException {
        int n = 0;
        inputStream = new PushbackInputStream(inputStream);
        this.decodeBufferPrefix((PushbackInputStream)inputStream, outputStream);
        try {
            do {
                int n2 = this.decodeLinePrefix((PushbackInputStream)inputStream, outputStream);
                int n3 = 0;
                while (this.bytesPerAtom() + n3 < n2) {
                    this.decodeAtom((PushbackInputStream)inputStream, outputStream, this.bytesPerAtom());
                    n += this.bytesPerAtom();
                    n3 += this.bytesPerAtom();
                }
                if (this.bytesPerAtom() + n3 == n2) {
                    this.decodeAtom((PushbackInputStream)inputStream, outputStream, this.bytesPerAtom());
                    n += this.bytesPerAtom();
                } else {
                    this.decodeAtom((PushbackInputStream)inputStream, outputStream, n2 - n3);
                    n += n2 - n3;
                }
                this.decodeLineSuffix((PushbackInputStream)inputStream, outputStream);
            } while (true);
        }
        catch (CEStreamExhausted cEStreamExhausted) {
            this.decodeBufferSuffix((PushbackInputStream)inputStream, outputStream);
            return;
        }
    }

    public byte[] decodeBuffer(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.decodeBuffer(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] decodeBuffer(String object) throws IOException {
        Object object2 = new byte[((String)object).length()];
        ((String)object).getBytes(0, ((String)object).length(), (byte[])object2, 0);
        object2 = new ByteArrayInputStream((byte[])object2);
        object = new ByteArrayOutputStream();
        this.decodeBuffer((InputStream)object2, (OutputStream)object);
        return ((ByteArrayOutputStream)object).toByteArray();
    }

    protected void decodeBufferPrefix(PushbackInputStream pushbackInputStream, OutputStream outputStream) throws IOException {
    }

    protected void decodeBufferSuffix(PushbackInputStream pushbackInputStream, OutputStream outputStream) throws IOException {
    }

    public ByteBuffer decodeBufferToByteBuffer(InputStream inputStream) throws IOException {
        return ByteBuffer.wrap(this.decodeBuffer(inputStream));
    }

    public ByteBuffer decodeBufferToByteBuffer(String string) throws IOException {
        return ByteBuffer.wrap(this.decodeBuffer(string));
    }

    protected int decodeLinePrefix(PushbackInputStream pushbackInputStream, OutputStream outputStream) throws IOException {
        return this.bytesPerLine();
    }

    protected void decodeLineSuffix(PushbackInputStream pushbackInputStream, OutputStream outputStream) throws IOException {
    }

    protected int readFully(InputStream inputStream, byte[] arrby, int n, int n2) throws IOException {
        for (int i = 0; i < n2; ++i) {
            int n3 = inputStream.read();
            int n4 = -1;
            if (n3 == -1) {
                if (i == 0) {
                    i = n4;
                }
                return i;
            }
            arrby[i + n] = (byte)n3;
        }
        return n2;
    }
}

