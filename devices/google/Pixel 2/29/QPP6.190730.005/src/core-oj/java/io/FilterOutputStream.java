/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.OutputStream;

public class FilterOutputStream
extends OutputStream {
    protected OutputStream out;

    public FilterOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    @Override
    public void close() throws IOException {
        block7 : {
            OutputStream outputStream = this.out;
            try {
                this.flush();
                if (outputStream == null) break block7;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    }
                    throw throwable2;
                }
            }
            outputStream.close();
        }
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if ((n | n2 | arrby.length - (n2 + n) | n + n2) >= 0) {
            for (int i = 0; i < n2; ++i) {
                this.write(arrby[n + i]);
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}

