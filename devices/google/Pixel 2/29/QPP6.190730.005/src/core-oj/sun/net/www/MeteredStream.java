/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.net.ProgressSource;

public class MeteredStream
extends FilterInputStream {
    protected boolean closed = false;
    protected long count = 0L;
    protected long expected;
    protected int markLimit = -1;
    protected long markedCount = 0L;
    protected ProgressSource pi;

    public MeteredStream(InputStream inputStream, ProgressSource progressSource, long l) {
        super(inputStream);
        this.pi = progressSource;
        this.expected = l;
        if (progressSource != null) {
            progressSource.updateProgress(0L, l);
        }
    }

    private boolean isMarked() {
        int n = this.markLimit;
        if (n < 0) {
            return false;
        }
        return this.count - this.markedCount <= (long)n;
    }

    private final void justRead(long l) throws IOException {
        ProgressSource progressSource;
        if (l == -1L) {
            if (!this.isMarked()) {
                this.close();
            }
            return;
        }
        this.count += l;
        if (this.count - this.markedCount > (long)this.markLimit) {
            this.markLimit = -1;
        }
        if ((progressSource = this.pi) != null) {
            progressSource.updateProgress(this.count, this.expected);
        }
        if (this.isMarked()) {
            return;
        }
        l = this.expected;
        if (l > 0L && this.count >= l) {
            this.close();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int available() throws IOException {
        synchronized (this) {
            block4 : {
                if (!this.closed) break block4;
                return 0;
            }
            return this.in.available();
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            block5 : {
                boolean bl = this.closed;
                if (!bl) break block5;
                return;
            }
            if (this.pi != null) {
                this.pi.finishTracking();
            }
            this.closed = true;
            this.in.close();
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            if (this.pi != null) {
                this.pi.close();
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    @Override
    public void mark(int n) {
        synchronized (this) {
            block4 : {
                boolean bl = this.closed;
                if (!bl) break block4;
                return;
            }
            super.mark(n);
            this.markedCount = this.count;
            this.markLimit = n;
            return;
        }
    }

    @Override
    public boolean markSupported() {
        if (this.closed) {
            return false;
        }
        return super.markSupported();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        synchronized (this) {
            boolean bl = this.closed;
            if (bl) {
                return -1;
            }
            int n = this.in.read();
            if (n != -1) {
                this.justRead(1L);
            } else {
                this.justRead(n);
            }
            return n;
        }
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            block4 : {
                boolean bl = this.closed;
                if (!bl) break block4;
                return -1;
            }
            n = this.in.read(arrby, n, n2);
            this.justRead(n);
            return n;
        }
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            block5 : {
                boolean bl = this.closed;
                if (!bl) break block5;
                return;
            }
            if (this.isMarked()) {
                this.count = this.markedCount;
                super.reset();
                return;
            }
            IOException iOException = new IOException("Resetting to an invalid mark");
            throw iOException;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        synchronized (this) {
            block5 : {
                boolean bl = this.closed;
                if (!bl) break block5;
                return 0L;
            }
            if (l > this.expected - this.count) {
                l = this.expected - this.count;
            }
            l = this.in.skip(l);
            this.justRead(l);
            return l;
        }
    }
}

