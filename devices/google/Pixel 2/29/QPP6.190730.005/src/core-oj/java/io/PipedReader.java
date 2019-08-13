/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package java.io;

import java.io.IOException;
import java.io.PipedWriter;
import java.io.Reader;
import libcore.io.IoUtils;

public class PipedReader
extends Reader {
    private static final int DEFAULT_PIPE_SIZE = 1024;
    char[] buffer;
    boolean closedByReader = false;
    boolean closedByWriter = false;
    boolean connected = false;
    int in = -1;
    int out = 0;
    Thread readSide;
    Thread writeSide;

    public PipedReader() {
        this.initPipe(1024);
    }

    public PipedReader(int n) {
        this.initPipe(n);
    }

    public PipedReader(PipedWriter pipedWriter) throws IOException {
        this(pipedWriter, 1024);
    }

    public PipedReader(PipedWriter pipedWriter, int n) throws IOException {
        this.initPipe(n);
        this.connect(pipedWriter);
    }

    private void initPipe(int n) {
        if (n > 0) {
            this.buffer = new char[n];
            return;
        }
        throw new IllegalArgumentException("Pipe size <= 0");
    }

    @Override
    public void close() throws IOException {
        this.in = -1;
        this.closedByReader = true;
    }

    public void connect(PipedWriter pipedWriter) throws IOException {
        pipedWriter.connect(this);
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
            char[] arrc = this.buffer;
            n = this.out;
            this.out = n + 1;
            n = arrc[n];
            if (this.out >= this.buffer.length) {
                this.out = 0;
            }
            if (this.in == this.out) {
                this.in = -1;
            }
            return n;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(char[] object, int n, int n2) throws IOException {
        synchronized (this) {
            int n3;
            int n4;
            void var2_6;
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
            if (var2_6 >= 0 && var2_6 <= ((char[])object).length && n4 >= 0 && var2_6 + n4 <= (n3 = ((char[])object).length) && var2_6 + n4 >= 0) {
                void var5_9;
                if (n4 == 0) {
                    return 0;
                }
                n3 = this.read();
                if (n3 < 0) {
                    return -1;
                }
                n3 = (char)n3;
                object[var2_6] = (char)n3;
                n3 = 1;
                while (this.in >= 0 && (var5_9 = n4 - true) > 0) {
                    char[] arrc = this.buffer;
                    n4 = this.out;
                    this.out = n4 + 1;
                    object[var2_6 + n3] = arrc[n4];
                    int n5 = n3 + 1;
                    if (this.out >= this.buffer.length) {
                        this.out = 0;
                    }
                    n3 = n5;
                    n4 = var5_9;
                    if (this.in != this.out) continue;
                    this.in = -1;
                    n3 = n5;
                    n4 = var5_9;
                }
                return n3;
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
            throw indexOutOfBoundsException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean ready() throws IOException {
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
            int n = this.in;
            return n >= 0;
            {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void receive(int n) throws IOException {
        synchronized (this) {
            if (!this.connected) {
                IOException iOException = new IOException("Pipe not connected");
                throw iOException;
            }
            if (!this.closedByWriter && !this.closedByReader) {
                if (this.readSide != null && !this.readSide.isAlive()) {
                    IOException iOException = new IOException("Read end dead");
                    throw iOException;
                }
                this.writeSide = Thread.currentThread();
            } else {
                IOException iOException = new IOException("Pipe closed");
                throw iOException;
            }
            while (this.in == this.out) {
                if (this.readSide != null && !this.readSide.isAlive()) {
                    IOException iOException = new IOException("Pipe broken");
                    throw iOException;
                }
                this.notifyAll();
                try {
                    this.wait(1000L);
                }
                catch (InterruptedException interruptedException) {
                    IoUtils.throwInterruptedIoException();
                }
            }
            if (this.in < 0) {
                this.in = 0;
                this.out = 0;
            }
            char[] arrc = this.buffer;
            int n2 = this.in;
            this.in = n2 + 1;
            arrc[n2] = (char)n;
            if (this.in >= this.buffer.length) {
                this.in = 0;
            }
            return;
        }
    }

    void receive(char[] arrc, int n, int n2) throws IOException {
        synchronized (this) {
            while (--n2 >= 0) {
                this.receive(arrc[n]);
                ++n;
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

