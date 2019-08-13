/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.stack.SIPStackTimerTask;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

public class Pipeline
extends InputStream {
    private LinkedList buffList;
    private Buffer currentBuffer;
    private boolean isClosed;
    private TimerTask myTimerTask;
    private InputStream pipe;
    private int readTimeout;
    private Timer timer;

    public Pipeline(InputStream inputStream, int n, Timer timer) {
        this.timer = timer;
        this.pipe = inputStream;
        this.buffList = new LinkedList();
        this.readTimeout = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        this.isClosed = true;
        LinkedList linkedList = this.buffList;
        synchronized (linkedList) {
            this.buffList.notifyAll();
        }
        this.pipe.close();
    }

    /*
     * Loose catch block
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read() throws IOException {
        LinkedList linkedList = this.buffList;
        synchronized (linkedList) {
            block12 : {
                if (this.currentBuffer != null && this.currentBuffer.ptr < this.currentBuffer.length) {
                    int n = this.currentBuffer.getNextByte();
                    if (this.currentBuffer.ptr == this.currentBuffer.length) {
                        this.currentBuffer = null;
                    }
                    return n;
                }
                if (this.isClosed && this.buffList.isEmpty()) {
                    return -1;
                }
                do {
                    boolean bl;
                    if (!this.buffList.isEmpty()) break block12;
                    this.buffList.wait();
                    if (!(bl = this.isClosed)) continue;
                    break;
                } while (true);
                return -1;
            }
            try {
                this.currentBuffer = (Buffer)this.buffList.removeFirst();
                int n = this.currentBuffer.getNextByte();
                if (this.currentBuffer.ptr == this.currentBuffer.length) {
                    this.currentBuffer = null;
                }
                return n;
            }
            catch (NoSuchElementException noSuchElementException) {
                noSuchElementException.printStackTrace();
                IOException iOException = new IOException(noSuchElementException.getMessage());
                throw iOException;
            }
            catch (InterruptedException interruptedException) {
                IOException iOException = new IOException(interruptedException.getMessage());
                throw iOException;
            }
            catch (Throwable throwable) {}
            {
                throw throwable;
            }
        }
    }

    public void startTimer() {
        if (this.readTimeout == -1) {
            return;
        }
        this.myTimerTask = new MyTimer(this);
        this.timer.schedule(this.myTimerTask, this.readTimeout);
    }

    public void stopTimer() {
        if (this.readTimeout == -1) {
            return;
        }
        TimerTask timerTask = this.myTimerTask;
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(byte[] object) throws IOException {
        if (!this.isClosed) {
            Buffer buffer = new Buffer((byte[])object, ((byte[])object).length);
            object = this.buffList;
            synchronized (object) {
                this.buffList.add(buffer);
                this.buffList.notifyAll();
                return;
            }
        }
        throw new IOException("Closed!!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(byte[] object, int n, int n2) throws IOException {
        if (!this.isClosed) {
            Buffer buffer = new Buffer((byte[])object, n2);
            buffer.ptr = n;
            object = this.buffList;
            synchronized (object) {
                this.buffList.add(buffer);
                this.buffList.notifyAll();
                return;
            }
        }
        throw new IOException("Closed!!");
    }

    class Buffer {
        byte[] bytes;
        int length;
        int ptr = 0;

        public Buffer(byte[] arrby, int n) {
            this.length = n;
            this.bytes = arrby;
        }

        public int getNextByte() {
            byte[] arrby = this.bytes;
            int n = this.ptr;
            this.ptr = n + 1;
            return arrby[n] & 255;
        }
    }

    class MyTimer
    extends SIPStackTimerTask {
        private boolean isCancelled;
        Pipeline pipeline;

        protected MyTimer(Pipeline pipeline2) {
            this.pipeline = pipeline2;
        }

        @Override
        public boolean cancel() {
            boolean bl = super.cancel();
            this.isCancelled = true;
            return bl;
        }

        @Override
        protected void runTask() {
            if (this.isCancelled) {
                return;
            }
            try {
                this.pipeline.close();
            }
            catch (IOException iOException) {
                InternalErrorHandler.handleException(iOException);
            }
        }
    }

}

