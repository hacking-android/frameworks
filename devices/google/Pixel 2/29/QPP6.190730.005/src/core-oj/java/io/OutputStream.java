/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

public abstract class OutputStream
implements Closeable,
Flushable {
    @Override
    public void close() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    public abstract void write(int var1) throws IOException;

    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            if (n >= 0 && n <= arrby.length && n2 >= 0 && n + n2 <= arrby.length && n + n2 >= 0) {
                if (n2 == 0) {
                    return;
                }
                for (int i = 0; i < n2; ++i) {
                    this.write(arrby[n + i]);
                }
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }
}

