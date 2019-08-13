/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Closeable;
import java.io.IOException;

public abstract class InputStream
implements Closeable {
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;

    public int available() throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void mark(int n) {
        // MONITORENTER : this
        // MONITOREXIT : this
    }

    public boolean markSupported() {
        return false;
    }

    public abstract int read() throws IOException;

    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
                if (n2 == 0) {
                    return 0;
                }
                int n3 = this.read();
                if (n3 == -1) {
                    return -1;
                }
                arrby[n] = (byte)n3;
                for (n3 = 1; n3 < n2; ++n3) {
                    int n4;
                    try {
                        n4 = this.read();
                        if (n4 == -1) break;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                        break;
                    }
                    arrby[n + n3] = (byte)n4;
                    continue;
                }
                return n3;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }

    public void reset() throws IOException {
        synchronized (this) {
            IOException iOException = new IOException("mark/reset not supported");
            throw iOException;
        }
    }

    public long skip(long l) throws IOException {
        long l2;
        int n;
        if (l <= 0L) {
            return 0L;
        }
        int n2 = (int)Math.min(2048L, l2);
        byte[] arrby = new byte[n2];
        for (l2 = l; l2 > 0L && (n = this.read(arrby, 0, (int)Math.min((long)n2, l2))) >= 0; l2 -= (long)n) {
        }
        return l - l2;
    }
}

