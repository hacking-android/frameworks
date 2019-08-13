/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.AbstractPollSelectorImpl;
import sun.nio.ch.FileDispatcherImpl;
import sun.nio.ch.IOUtil;
import sun.nio.ch.PollArrayWrapper;
import sun.nio.ch.SelectionKeyImpl;

class PollSelectorImpl
extends AbstractPollSelectorImpl {
    private int fd0;
    private int fd1;
    private Object interruptLock = new Object();
    private boolean interruptTriggered = false;

    PollSelectorImpl(SelectorProvider object) {
        super((SelectorProvider)object, 1, 1);
        long l = IOUtil.makePipe(false);
        this.fd0 = (int)(l >>> 32);
        this.fd1 = (int)l;
        try {
            this.pollWrapper = object = new PollArrayWrapper(10);
            this.pollWrapper.initInterrupt(this.fd0, this.fd1);
            this.channelArray = new SelectionKeyImpl[10];
            return;
        }
        catch (Throwable throwable) {
            try {
                FileDispatcherImpl.closeIntFD(this.fd0);
            }
            catch (IOException iOException) {
                throwable.addSuppressed(iOException);
            }
            try {
                FileDispatcherImpl.closeIntFD(this.fd1);
            }
            catch (IOException iOException) {
                throwable.addSuppressed(iOException);
            }
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected int doSelect(long l) throws IOException {
        if (this.channelArray == null) {
            throw new ClosedSelectorException();
        }
        this.processDeregisterQueue();
        this.begin();
        this.pollWrapper.poll(this.totalChannels, 0, l);
        this.processDeregisterQueue();
        int n = this.updateSelectedKeys();
        if (this.pollWrapper.getReventOps(0) != 0) {
            this.pollWrapper.putReventOps(0, 0);
            Object object = this.interruptLock;
            synchronized (object) {
                IOUtil.drain(this.fd0);
                this.interruptTriggered = false;
            }
        }
        return n;
        finally {
            this.end();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void implCloseInterrupt() throws IOException {
        Object object = this.interruptLock;
        synchronized (object) {
            this.interruptTriggered = true;
        }
        FileDispatcherImpl.closeIntFD(this.fd0);
        FileDispatcherImpl.closeIntFD(this.fd1);
        this.fd0 = -1;
        this.fd1 = -1;
        this.pollWrapper.release(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Selector wakeup() {
        Object object = this.interruptLock;
        synchronized (object) {
            if (!this.interruptTriggered) {
                this.pollWrapper.interrupt();
                this.interruptTriggered = true;
            }
            return this;
        }
    }
}

