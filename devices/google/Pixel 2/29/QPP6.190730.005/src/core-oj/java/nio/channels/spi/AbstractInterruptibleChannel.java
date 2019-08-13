/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels.spi;

import java.io.IOException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.Channel;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.InterruptibleChannel;
import sun.nio.ch.Interruptible;

public abstract class AbstractInterruptibleChannel
implements Channel,
InterruptibleChannel {
    private final Object closeLock = new Object();
    private volatile Thread interrupted;
    private Interruptible interruptor;
    private volatile boolean open = true;

    protected AbstractInterruptibleChannel() {
    }

    static void blockedOn(Interruptible interruptible) {
        Thread.currentThread().blockedOn(interruptible);
    }

    protected final void begin() {
        if (this.interruptor == null) {
            this.interruptor = new Interruptible(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void interrupt(Thread thread) {
                    Object object = AbstractInterruptibleChannel.this.closeLock;
                    synchronized (object) {
                        if (!AbstractInterruptibleChannel.this.open) {
                            return;
                        }
                        AbstractInterruptibleChannel.this.open = false;
                        AbstractInterruptibleChannel.this.interrupted = thread;
                        try {
                            AbstractInterruptibleChannel.this.implCloseChannel();
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                        return;
                    }
                }
            };
        }
        AbstractInterruptibleChannel.blockedOn(this.interruptor);
        Thread thread = Thread.currentThread();
        if (thread.isInterrupted()) {
            this.interruptor.interrupt(thread);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void close() throws IOException {
        Object object = this.closeLock;
        synchronized (object) {
            if (!this.open) {
                return;
            }
            this.open = false;
            this.implCloseChannel();
            return;
        }
    }

    protected final void end(boolean bl) throws AsynchronousCloseException {
        AbstractInterruptibleChannel.blockedOn(null);
        Thread thread = this.interrupted;
        if (thread != null && thread == Thread.currentThread()) {
            throw new ClosedByInterruptException();
        }
        if (!bl && !this.open) {
            throw new AsynchronousCloseException();
        }
    }

    protected abstract void implCloseChannel() throws IOException;

    @Override
    public final boolean isOpen() {
        return this.open;
    }

}

