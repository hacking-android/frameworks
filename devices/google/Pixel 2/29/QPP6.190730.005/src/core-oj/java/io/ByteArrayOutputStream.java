/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ByteArrayOutputStream
extends OutputStream {
    private static final int MAX_ARRAY_SIZE = 2147483639;
    protected byte[] buf;
    protected int count;

    public ByteArrayOutputStream() {
        this(32);
    }

    public ByteArrayOutputStream(int n) {
        if (n >= 0) {
            this.buf = new byte[n];
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative initial size: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void ensureCapacity(int n) {
        if (n - this.buf.length > 0) {
            this.grow(n);
        }
    }

    private void grow(int n) {
        int n2;
        int n3 = n2 = this.buf.length << 1;
        if (n2 - n < 0) {
            n3 = n;
        }
        n2 = n3;
        if (n3 - 2147483639 > 0) {
            n2 = ByteArrayOutputStream.hugeCapacity(n);
        }
        this.buf = Arrays.copyOf(this.buf, n2);
    }

    private static int hugeCapacity(int n) {
        if (n >= 0) {
            int n2 = 2147483639;
            n = n > 2147483639 ? Integer.MAX_VALUE : n2;
            return n;
        }
        throw new OutOfMemoryError();
    }

    @Override
    public void close() throws IOException {
    }

    public void reset() {
        synchronized (this) {
            this.count = 0;
            return;
        }
    }

    public int size() {
        synchronized (this) {
            int n = this.count;
            return n;
        }
    }

    public byte[] toByteArray() {
        synchronized (this) {
            byte[] arrby = Arrays.copyOf(this.buf, this.count);
            return arrby;
        }
    }

    public String toString() {
        synchronized (this) {
            String string = new String(this.buf, 0, this.count);
            return string;
        }
    }

    @Deprecated
    public String toString(int n) {
        synchronized (this) {
            String string = new String(this.buf, n, 0, this.count);
            return string;
        }
    }

    public String toString(String string) throws UnsupportedEncodingException {
        synchronized (this) {
            string = new String(this.buf, 0, this.count, string);
            return string;
        }
    }

    @Override
    public void write(int n) {
        synchronized (this) {
            this.ensureCapacity(this.count + 1);
            this.buf[this.count] = (byte)n;
            ++this.count;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(byte[] object, int n, int n2) {
        synchronized (this) {
            if (n >= 0 && n <= ((byte[])object).length && n2 >= 0 && n + n2 - ((byte[])object).length <= 0) {
                this.ensureCapacity(this.count + n2);
                System.arraycopy(object, n, this.buf, this.count, n2);
                this.count += n2;
                return;
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
            throw indexOutOfBoundsException;
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        synchronized (this) {
            outputStream.write(this.buf, 0, this.count);
            return;
        }
    }
}

