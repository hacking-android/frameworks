/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class LineNumberInputStream
extends FilterInputStream {
    int lineNumber;
    int markLineNumber;
    int markPushBack = -1;
    int pushBack = -1;

    public LineNumberInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int available() throws IOException {
        int n = this.pushBack == -1 ? super.available() / 2 : super.available() / 2 + 1;
        return n;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public void mark(int n) {
        this.markLineNumber = this.lineNumber;
        this.markPushBack = this.pushBack;
        this.in.mark(n);
    }

    @Override
    public int read() throws IOException {
        int n = this.pushBack;
        if (n != -1) {
            this.pushBack = -1;
        } else {
            n = this.in.read();
        }
        if (n != 10) {
            if (n != 13) {
                return n;
            }
            this.pushBack = this.in.read();
            if (this.pushBack == 10) {
                this.pushBack = -1;
            }
        }
        ++this.lineNumber;
        return 10;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            if (n >= 0 && n <= arrby.length && n2 >= 0 && n + n2 <= arrby.length && n + n2 >= 0) {
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

    @Override
    public void reset() throws IOException {
        this.lineNumber = this.markLineNumber;
        this.pushBack = this.markPushBack;
        this.in.reset();
    }

    public void setLineNumber(int n) {
        this.lineNumber = n;
    }

    @Override
    public long skip(long l) throws IOException {
        long l2;
        int n;
        if (l <= 0L) {
            return 0L;
        }
        byte[] arrby = new byte[2048];
        for (l2 = l; l2 > 0L && (n = this.read(arrby, 0, (int)Math.min((long)2048, l2))) >= 0; l2 -= (long)n) {
        }
        return l - l2;
    }
}

