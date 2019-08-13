/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public abstract class CharacterEncoder {
    protected PrintStream pStream;

    private byte[] getBytes(ByteBuffer byteBuffer) {
        byte[] arrby;
        byte[] arrby2 = arrby = null;
        if (byteBuffer.hasArray()) {
            byte[] arrby3 = byteBuffer.array();
            arrby2 = arrby;
            if (arrby3.length == byteBuffer.capacity()) {
                arrby2 = arrby;
                if (arrby3.length == byteBuffer.remaining()) {
                    arrby2 = arrby3;
                    byteBuffer.position(byteBuffer.limit());
                }
            }
        }
        arrby = arrby2;
        if (arrby2 == null) {
            arrby = new byte[byteBuffer.remaining()];
            byteBuffer.get(arrby);
        }
        return arrby;
    }

    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    public String encode(ByteBuffer byteBuffer) {
        return this.encode(this.getBytes(byteBuffer));
    }

    public String encode(byte[] object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        object = new ByteArrayInputStream((byte[])object);
        try {
            this.encode((InputStream)object, (OutputStream)byteArrayOutputStream);
            object = byteArrayOutputStream.toString("8859_1");
            return object;
        }
        catch (Exception exception) {
            throw new Error("CharacterEncoder.encode internal error");
        }
    }

    public void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] arrby = new byte[this.bytesPerLine()];
        this.encodeBufferPrefix(outputStream);
        do {
            block7 : {
                block6 : {
                    int n;
                    if ((n = this.readFully(inputStream, arrby)) == 0) break block6;
                    this.encodeLinePrefix(outputStream, n);
                    for (int i = 0; i < n; i += this.bytesPerAtom()) {
                        if (this.bytesPerAtom() + i <= n) {
                            this.encodeAtom(outputStream, arrby, i, this.bytesPerAtom());
                            continue;
                        }
                        this.encodeAtom(outputStream, arrby, i, n - i);
                    }
                    if (n >= this.bytesPerLine()) break block7;
                }
                this.encodeBufferSuffix(outputStream);
                return;
            }
            this.encodeLineSuffix(outputStream);
        } while (true);
    }

    public void encode(ByteBuffer byteBuffer, OutputStream outputStream) throws IOException {
        this.encode(this.getBytes(byteBuffer), outputStream);
    }

    public void encode(byte[] arrby, OutputStream outputStream) throws IOException {
        this.encode(new ByteArrayInputStream(arrby), outputStream);
    }

    protected abstract void encodeAtom(OutputStream var1, byte[] var2, int var3, int var4) throws IOException;

    public String encodeBuffer(ByteBuffer byteBuffer) {
        return this.encodeBuffer(this.getBytes(byteBuffer));
    }

    public String encodeBuffer(byte[] object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        object = new ByteArrayInputStream((byte[])object);
        try {
            this.encodeBuffer((InputStream)object, (OutputStream)byteArrayOutputStream);
            return byteArrayOutputStream.toString();
        }
        catch (Exception exception) {
            throw new Error("CharacterEncoder.encodeBuffer internal error");
        }
    }

    public void encodeBuffer(InputStream inputStream, OutputStream outputStream) throws IOException {
        int n;
        byte[] arrby = new byte[this.bytesPerLine()];
        this.encodeBufferPrefix(outputStream);
        while ((n = this.readFully(inputStream, arrby)) != 0) {
            this.encodeLinePrefix(outputStream, n);
            for (int i = 0; i < n; i += this.bytesPerAtom()) {
                if (this.bytesPerAtom() + i <= n) {
                    this.encodeAtom(outputStream, arrby, i, this.bytesPerAtom());
                    continue;
                }
                this.encodeAtom(outputStream, arrby, i, n - i);
            }
            this.encodeLineSuffix(outputStream);
            if (n >= this.bytesPerLine()) continue;
        }
        this.encodeBufferSuffix(outputStream);
    }

    public void encodeBuffer(ByteBuffer byteBuffer, OutputStream outputStream) throws IOException {
        this.encodeBuffer(this.getBytes(byteBuffer), outputStream);
    }

    public void encodeBuffer(byte[] arrby, OutputStream outputStream) throws IOException {
        this.encodeBuffer(new ByteArrayInputStream(arrby), outputStream);
    }

    protected void encodeBufferPrefix(OutputStream outputStream) throws IOException {
        this.pStream = new PrintStream(outputStream);
    }

    protected void encodeBufferSuffix(OutputStream outputStream) throws IOException {
    }

    protected void encodeLinePrefix(OutputStream outputStream, int n) throws IOException {
    }

    protected void encodeLineSuffix(OutputStream outputStream) throws IOException {
        this.pStream.println();
    }

    protected int readFully(InputStream inputStream, byte[] arrby) throws IOException {
        for (int i = 0; i < arrby.length; ++i) {
            int n = inputStream.read();
            if (n == -1) {
                return i;
            }
            arrby[i] = (byte)n;
        }
        return arrby.length;
    }
}

