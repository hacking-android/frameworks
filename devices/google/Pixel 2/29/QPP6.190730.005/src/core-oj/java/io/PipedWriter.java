/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.PipedReader;
import java.io.Writer;

public class PipedWriter
extends Writer {
    private boolean closed = false;
    private PipedReader sink;

    public PipedWriter() {
    }

    public PipedWriter(PipedReader pipedReader) throws IOException {
        this.connect(pipedReader);
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        PipedReader pipedReader = this.sink;
        if (pipedReader != null) {
            pipedReader.receivedLast();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void connect(PipedReader object) throws IOException {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    if (this.sink == null && !((PipedReader)object).connected) {
                        if (!((PipedReader)object).closedByReader && !this.closed) {
                            this.sink = object;
                            ((PipedReader)object).in = -1;
                            ((PipedReader)object).out = 0;
                            ((PipedReader)object).connected = true;
                            return;
                        }
                        object = new IOException("Pipe closed");
                        throw object;
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
                if (!this.sink.closedByReader && !this.closed) {
                    PipedReader pipedReader = this.sink;
                    synchronized (pipedReader) {
                        this.sink.notifyAll();
                    }
                } else {
                    IOException iOException = new IOException("Pipe closed");
                    throw iOException;
                }
            }
            return;
        }
    }

    @Override
    public void write(int n) throws IOException {
        PipedReader pipedReader = this.sink;
        if (pipedReader != null) {
            pipedReader.receive(n);
            return;
        }
        throw new IOException("Pipe not connected");
    }

    @Override
    public void write(char[] arrc, int n, int n2) throws IOException {
        PipedReader pipedReader = this.sink;
        if (pipedReader != null) {
            if ((n | n2 | n + n2 | arrc.length - (n + n2)) >= 0) {
                pipedReader.receive(arrc, n, n2);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IOException("Pipe not connected");
    }
}

