/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class BufferedInputStream
extends FilterInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = 2147483639;
    private static final AtomicReferenceFieldUpdater<BufferedInputStream, byte[]> bufUpdater = AtomicReferenceFieldUpdater.newUpdater(BufferedInputStream.class, byte[].class, "buf");
    protected volatile byte[] buf;
    protected int count;
    protected int marklimit;
    protected int markpos = -1;
    protected int pos;

    public BufferedInputStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public BufferedInputStream(InputStream inputStream, int n) {
        super(inputStream);
        if (n > 0) {
            this.buf = new byte[n];
            return;
        }
        throw new IllegalArgumentException("Buffer size <= 0");
    }

    private void fill() throws IOException {
        Object object;
        Object object2 = this.getBufIfOpen();
        int n = this.markpos;
        if (n < 0) {
            this.pos = 0;
            object = object2;
        } else {
            int n2 = this.pos;
            object = object2;
            if (n2 >= ((byte[])object2).length) {
                if (n > 0) {
                    System.arraycopy((byte[])object2, n, (byte[])object2, 0, n2 -= n);
                    this.pos = n2;
                    this.markpos = 0;
                    object = object2;
                } else if (((byte[])object2).length >= this.marklimit) {
                    this.markpos = -1;
                    this.pos = 0;
                    object = object2;
                } else {
                    int n3 = ((byte[])object2).length;
                    n = 2147483639;
                    if (n3 < 2147483639) {
                        if (n2 <= 2147483639 - n2) {
                            n = n2 * 2;
                        }
                        n2 = n;
                        if (n > this.marklimit) {
                            n2 = this.marklimit;
                        }
                        object = new byte[n2];
                        System.arraycopy((byte[])object2, 0, object, 0, this.pos);
                        if (!bufUpdater.compareAndSet(this, (byte[])object2, (byte[])object)) {
                            throw new IOException("Stream closed");
                        }
                    } else {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                }
            }
        }
        this.count = this.pos;
        object2 = this.getInIfOpen();
        n = this.pos;
        n = ((InputStream)object2).read((byte[])object, n, ((byte[])object).length - n);
        if (n > 0) {
            this.count = this.pos + n;
        }
    }

    private byte[] getBufIfOpen() throws IOException {
        byte[] arrby = this.buf;
        if (arrby != null) {
            return arrby;
        }
        throw new IOException("Stream closed");
    }

    private InputStream getInIfOpen() throws IOException {
        InputStream inputStream = this.in;
        if (inputStream != null) {
            return inputStream;
        }
        throw new IOException("Stream closed");
    }

    private int read1(byte[] arrby, int n, int n2) throws IOException {
        int n3;
        int n4 = n3 = this.count - this.pos;
        if (n3 <= 0) {
            if (n2 >= this.getBufIfOpen().length && this.markpos < 0) {
                return this.getInIfOpen().read(arrby, n, n2);
            }
            this.fill();
            n4 = this.count - this.pos;
            if (n4 <= 0) {
                return -1;
            }
        }
        if (n4 < n2) {
            n2 = n4;
        }
        System.arraycopy(this.getBufIfOpen(), this.pos, arrby, n, n2);
        this.pos += n2;
        return n2;
    }

    @Override
    public int available() throws IOException {
        synchronized (this) {
            int n = this.count - this.pos;
            int n2 = this.getInIfOpen().available();
            int n3 = Integer.MAX_VALUE;
            if (n <= Integer.MAX_VALUE - n2) {
                n3 = n + n2;
            }
            return n3;
        }
    }

    @Override
    public void close() throws IOException {
        Object object;
        while ((object = this.buf) != null) {
            if (!bufUpdater.compareAndSet(this, (byte[])object, null)) continue;
            object = this.in;
            this.in = null;
            if (object != null) {
                ((InputStream)object).close();
            }
            return;
        }
    }

    @Override
    public void mark(int n) {
        synchronized (this) {
            this.marklimit = n;
            this.markpos = this.pos;
            return;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            int n;
            block4 : {
                if (this.pos < this.count) break block4;
                this.fill();
                int n2 = this.pos;
                n = this.count;
                if (n2 < n) break block4;
                return -1;
            }
            byte[] arrby = this.getBufIfOpen();
            n = this.pos;
            this.pos = n + 1;
            n = arrby[n];
            return n & 255;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        synchronized (this) {
            int n3;
            InputStream inputStream;
            this.getBufIfOpen();
            int n4 = ((byte[])object).length;
            if ((n | n2 | n + n2 | n4 - (n + n2)) < 0) {
                IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
                throw indexOutOfBoundsException;
            }
            n4 = 0;
            if (n2 == 0) {
                return 0;
            }
            do {
                if ((n3 = this.read1((byte[])object, n + n4, n2 - n4)) <= 0) {
                    if (n4 != 0) return n4;
                    return n3;
                }
                if ((n4 += n3) < n2) continue;
                return n4;
            } while ((inputStream = this.in) == null || (n3 = inputStream.available()) > 0);
            return n4;
        }
    }

    @Override
    public void reset() throws IOException {
        synchronized (this) {
            this.getBufIfOpen();
            if (this.markpos >= 0) {
                this.pos = this.markpos;
                return;
            }
            IOException iOException = new IOException("Resetting to invalid mark");
            throw iOException;
        }
    }

    @Override
    public long skip(long l) throws IOException {
        synchronized (this) {
            long l2;
            block10 : {
                long l3;
                block11 : {
                    block9 : {
                        this.getBufIfOpen();
                        if (l > 0L) break block9;
                        return 0L;
                    }
                    l2 = l3 = (long)(this.count - this.pos);
                    if (l3 > 0L) break block10;
                    if (this.markpos >= 0) break block11;
                    l = this.getInIfOpen().skip(l);
                    return l;
                }
                this.fill();
                int n = this.count;
                int n2 = this.pos;
                l2 = l3 = (long)(n - n2);
                if (l3 <= 0L) {
                    return 0L;
                }
            }
            if (l2 < l) {
                l = l2;
            }
            this.pos = (int)((long)this.pos + l);
            return l;
        }
    }
}

