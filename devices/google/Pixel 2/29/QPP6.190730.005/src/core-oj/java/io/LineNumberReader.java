/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class LineNumberReader
extends BufferedReader {
    private static final int maxSkipBufferSize = 8192;
    private int lineNumber = 0;
    private int markedLineNumber;
    private boolean markedSkipLF;
    private char[] skipBuffer = null;
    private boolean skipLF;

    public LineNumberReader(Reader reader) {
        super(reader);
    }

    public LineNumberReader(Reader reader, int n) {
        super(reader, n);
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void mark(int n) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            super.mark(n);
            this.markedLineNumber = this.lineNumber;
            this.markedSkipLF = this.skipLF;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            int n;
            int n2 = n = super.read();
            if (this.skipLF) {
                n2 = n;
                if (n == 10) {
                    n2 = super.read();
                }
                this.skipLF = false;
            }
            if (n2 != 10) {
                if (n2 != 13) {
                    return n2;
                }
                this.skipLF = true;
            }
            ++this.lineNumber;
            return 10;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(char[] arrc, int n, int n2) throws IOException {
        Object object = this.lock;
        synchronized (object) {
            int n3 = super.read(arrc, n, n2);
            n2 = n;
            do {
                block10 : {
                    block11 : {
                        char c;
                        block9 : {
                            if (n2 >= n + n3) {
                                return n3;
                            }
                            c = arrc[n2];
                            if (!this.skipLF) break block9;
                            this.skipLF = false;
                            if (c == '\n') break block10;
                        }
                        if (c == '\n') break block11;
                        if (c != '\r') break block10;
                        this.skipLF = true;
                    }
                    ++this.lineNumber;
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String readLine() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            String string = super.readLine(this.skipLF);
            this.skipLF = false;
            if (string != null) {
                ++this.lineNumber;
            }
            return string;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void reset() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            super.reset();
            this.lineNumber = this.markedLineNumber;
            this.skipLF = this.markedSkipLF;
            return;
        }
    }

    public void setLineNumber(int n) {
        this.lineNumber = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long skip(long l) throws IOException {
        if (l < 0L) {
            throw new IllegalArgumentException("skip() value is negative");
        }
        int n = (int)Math.min(l, 8192L);
        Object object = this.lock;
        synchronized (object) {
            int n2;
            long l2;
            if (this.skipBuffer == null || this.skipBuffer.length < n) {
                this.skipBuffer = new char[n];
            }
            for (l2 = l; l2 > 0L && (n2 = this.read(this.skipBuffer, 0, (int)Math.min(l2, (long)n))) != -1; l2 -= (long)n2) {
            }
            return l - l2;
        }
    }
}

