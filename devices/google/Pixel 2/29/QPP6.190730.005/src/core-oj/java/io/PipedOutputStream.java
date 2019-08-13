/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;

public class PipedOutputStream
extends OutputStream {
    private PipedInputStream sink;

    public PipedOutputStream() {
    }

    public PipedOutputStream(PipedInputStream pipedInputStream) throws IOException {
        this.connect(pipedInputStream);
    }

    @Override
    public void close() throws IOException {
        PipedInputStream pipedInputStream = this.sink;
        if (pipedInputStream != null) {
            pipedInputStream.receivedLast();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void connect(PipedInputStream object) throws IOException {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    if (this.sink == null && !((PipedInputStream)object).connected) {
                        this.sink = object;
                        ((PipedInputStream)object).in = -1;
                        ((PipedInputStream)object).out = 0;
                        ((PipedInputStream)object).connected = true;
                        return;
                    }
                    object = new IOException("Already connected");
                    throw object;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException();
                throw object;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void flush() throws IOException {
        synchronized (this) {
            if (this.sink != null) {
                PipedInputStream pipedInputStream = this.sink;
                synchronized (pipedInputStream) {
                    this.sink.notifyAll();
                }
            }
            return;
        }
    }

    @Override
    public void write(int n) throws IOException {
        PipedInputStream pipedInputStream = this.sink;
        if (pipedInputStream != null) {
            pipedInputStream.receive(n);
            return;
        }
        throw new IOException("Pipe not connected");
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        PipedInputStream pipedInputStream = this.sink;
        if (pipedInputStream != null) {
            if (arrby != null) {
                if (n >= 0 && n <= arrby.length && n2 >= 0 && n + n2 <= arrby.length && n + n2 >= 0) {
                    if (n2 == 0) {
                        return;
                    }
                    pipedInputStream.receive(arrby, n, n2);
                    return;
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException();
        }
        throw new IOException("Pipe not connected");
    }
}

