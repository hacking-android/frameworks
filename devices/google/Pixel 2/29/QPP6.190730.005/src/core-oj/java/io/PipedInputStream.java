/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package java.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedOutputStream;
import libcore.io.IoUtils;

public class PipedInputStream
extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DEFAULT_PIPE_SIZE = 1024;
    protected static final int PIPE_SIZE = 1024;
    protected byte[] buffer;
    volatile boolean closedByReader = false;
    boolean closedByWriter = false;
    boolean connected = false;
    protected int in = -1;
    protected int out = 0;
    Thread readSide;
    Thread writeSide;

    public PipedInputStream() {
        this.initPipe(1024);
    }

    public PipedInputStream(int n) {
        this.initPipe(n);
    }

    public PipedInputStream(PipedOutputStream pipedOutputStream) throws IOException {
        this(pipedOutputStream, 1024);
    }

    public PipedInputStream(PipedOutputStream pipedOutputStream, int n) throws IOException {
        this.initPipe(n);
        this.connect(pipedOutputStream);
    }

    private void awaitSpace() throws IOException {
        while (this.in == this.out) {
            this.checkStateForReceive();
            this.notifyAll();
            try {
                this.wait(1000L);
            }
            catch (InterruptedException interruptedException) {
                IoUtils.throwInterruptedIoException();
            }
        }
    }

    private void checkStateForReceive() throws IOException {
        if (this.connected) {
            if (!this.closedByWriter && !this.closedByReader) {
                Thread thread = this.readSide;
                if (thread != null && !thread.isAlive()) {
                    throw new IOException("Read end dead");
                }
                return;
            }
            throw new IOException("Pipe closed");
        }
        throw new IOException("Pipe not connected");
    }

    private void initPipe(int n) {
        if (n > 0) {
            this.buffer = new byte[n];
            return;
        }
        throw new IllegalArgumentException("Pipe Size <= 0");
    }

    @Override
    public int available() throws IOException {
        synchronized (this) {
            int n;
            block8 : {
                block7 : {
                    block6 : {
                        n = this.in;
                        if (n >= 0) break block6;
                        return 0;
                    }
                    if (this.in != this.out) break block7;
                    n = this.buffer.length;
                    return n;
                }
                if (this.in <= this.out) break block8;
                n = this.in;
                int n2 = this.out;
                return n - n2;
            }
            n = this.in;
            int n3 = this.buffer.length;
            int n4 = this.out;
            return n + n3 - n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        this.closedByReader = true;
        synchronized (this) {
            this.in = -1;
            return;
        }
    }

    public void connect(PipedOutputStream pipedOutputStream) throws IOException {
        pipedOutputStream.connect(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        synchronized (this) {
            if (!this.connected) {
                IOException iOException = new IOException("Pipe not connected");
                throw iOException;
            }
            if (this.closedByReader) {
                IOException iOException = new IOException("Pipe closed");
                throw iOException;
            }
            if (this.writeSide != null && !this.writeSide.isAlive() && !this.closedByWriter && this.in < 0) {
                IOException iOException = new IOException("Write end dead");
                throw iOException;
            }
            this.readSide = Thread.currentThread();
            int n = 2;
            while (this.in < 0) {
                boolean bl = this.closedByWriter;
                if (bl) {
                    return -1;
                }
                int n2 = n;
                if (this.writeSide != null) {
                    n2 = n;
                    if (!this.writeSide.isAlive() && (n2 = n - 1) < 0) {
                        IOException iOException = new IOException("Pipe broken");
                        throw iOException;
                    }
                }
                this.notifyAll();
                try {
                    this.wait(1000L);
                }
                catch (InterruptedException interruptedException) {
                    IoUtils.throwInterruptedIoException();
                }
                n = n2;
            }
            byte[] arrby = this.buffer;
            n = this.out;
            this.out = n + 1;
            n = arrby[n];
            if (this.out >= this.buffer.length) {
                this.out = 0;
            }
            if (this.in == this.out) {
                this.in = -1;
            }
            return n & 255;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(byte[] object, int n, int n2) throws IOException {
        synchronized (this) {
            int n3;
            int n4;
            int n5;
            void var2_2;
            if (object == null) {
                object = new NullPointerException();
                throw object;
            }
            if (var2_2 >= 0 && n5 >= 0 && n5 <= (n4 = ((byte[])object).length) - var2_2) {
                if (n5 == 0) {
                    return 0;
                }
                n4 = this.read();
                if (n4 < 0) {
                    return -1;
                }
                n4 = (byte)n4;
                object[var2_2] = (byte)n4;
                n3 = 1;
                n4 = n5;
            } else {
                object = new IndexOutOfBoundsException();
                throw object;
            }
            while (this.in >= 0 && n4 > 1) {
                n5 = this.in > this.out ? Math.min(this.buffer.length - this.out, this.in - this.out) : this.buffer.length - this.out;
                int n6 = n5;
                if (n5 > n4 - 1) {
                    n6 = n4 - 1;
                }
                System.arraycopy(this.buffer, this.out, object, (int)(var2_2 + n3), n6);
                this.out += n6;
                n3 += n6;
                n4 -= n6;
                if (this.out >= this.buffer.length) {
                    this.out = 0;
                }
                if (this.in != this.out) continue;
                this.in = -1;
            }
            return n3;
        }
    }

    protected void receive(int n) throws IOException {
        synchronized (this) {
            this.checkStateForReceive();
            this.writeSide = Thread.currentThread();
            if (this.in == this.out) {
                this.awaitSpace();
            }
            if (this.in < 0) {
                this.in = 0;
                this.out = 0;
            }
            byte[] arrby = this.buffer;
            int n2 = this.in;
            this.in = n2 + 1;
            arrby[n2] = (byte)(n & 255);
            if (this.in >= this.buffer.length) {
                this.in = 0;
            }
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void receive(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            int n3;
            this.checkStateForReceive();
            this.writeSide = Thread.currentThread();
            void var4_4 = n3;
            void var3_3;
            while (var3_3 > 0) {
                if (this.in == this.out) {
                    this.awaitSpace();
                }
                n3 = 0;
                if (this.out < this.in) {
                    n3 = this.buffer.length - this.in;
                } else if (this.in < this.out) {
                    if (this.in == -1) {
                        this.out = 0;
                        this.in = 0;
                        n3 = this.buffer.length - this.in;
                    } else {
                        n3 = this.out - this.in;
                    }
                }
                int n4 = n3;
                if (n3 > var3_3) {
                    n4 = var3_3;
                }
                System.arraycopy(arrby, (int)var4_4, this.buffer, this.in, n4);
                var3_3 -= n4;
                var4_4 += n4;
                this.in += n4;
                if (this.in < this.buffer.length) continue;
                this.in = 0;
            }
            return;
        }
    }

    void receivedLast() {
        synchronized (this) {
            this.closedByWriter = true;
            this.notifyAll();
            return;
        }
    }
}

