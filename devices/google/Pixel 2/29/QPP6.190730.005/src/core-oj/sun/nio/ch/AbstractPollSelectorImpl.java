/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.AbstractSelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashSet;
import java.util.Set;
import sun.nio.ch.PollArrayWrapper;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;

abstract class AbstractPollSelectorImpl
extends SelectorImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final int INIT_CAP;
    protected SelectionKeyImpl[] channelArray;
    protected int channelOffset = 0;
    private Object closeLock = new Object();
    private boolean closed = false;
    PollArrayWrapper pollWrapper;
    protected int totalChannels;

    AbstractPollSelectorImpl(SelectorProvider selectorProvider, int n, int n2) {
        super(selectorProvider);
        this.INIT_CAP = 10;
        this.totalChannels = n;
        this.channelOffset = n2;
    }

    @Override
    protected abstract int doSelect(long var1) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void implClose() throws IOException {
        Object object = this.closeLock;
        synchronized (object) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            int n = this.channelOffset;
            do {
                if (n >= this.totalChannels) {
                    this.implCloseInterrupt();
                    this.pollWrapper.free();
                    this.pollWrapper = null;
                    this.selectedKeys = null;
                    this.channelArray = null;
                    this.totalChannels = 0;
                    return;
                }
                Object object2 = this.channelArray[n];
                ((SelectionKeyImpl)object2).setIndex(-1);
                this.deregister((AbstractSelectionKey)object2);
                object2 = this.channelArray[n].channel();
                if (!((AbstractInterruptibleChannel)object2).isOpen() && !((SelectableChannel)object2).isRegistered()) {
                    ((SelChImpl)object2).kill();
                }
                ++n;
            } while (true);
        }
    }

    protected abstract void implCloseInterrupt() throws IOException;

    @Override
    protected void implDereg(SelectionKeyImpl object) throws IOException {
        Object object2;
        int n;
        int n2 = ((SelectionKeyImpl)object).getIndex();
        if (n2 != (n = this.totalChannels) - 1) {
            SelectionKeyImpl selectionKeyImpl;
            object2 = this.channelArray;
            object2[n2] = selectionKeyImpl = object2[n - 1];
            selectionKeyImpl.setIndex(n2);
            this.pollWrapper.release(n2);
            object2 = this.pollWrapper;
            PollArrayWrapper.replaceEntry((PollArrayWrapper)object2, this.totalChannels - 1, (PollArrayWrapper)object2, n2);
        } else {
            this.pollWrapper.release(n2);
        }
        object2 = this.channelArray;
        n = this.totalChannels;
        object2[n - 1] = null;
        this.totalChannels = n - 1;
        object2 = this.pollWrapper;
        --object2.totalChannels;
        ((SelectionKeyImpl)object).setIndex(-1);
        this.keys.remove(object);
        this.selectedKeys.remove(object);
        this.deregister((AbstractSelectionKey)object);
        object = ((SelectionKeyImpl)object).channel();
        if (!((AbstractInterruptibleChannel)object).isOpen() && !((SelectableChannel)object).isRegistered()) {
            ((SelChImpl)object).kill();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void implRegister(SelectionKeyImpl object) {
        Object object2 = this.closeLock;
        synchronized (object2) {
            if (this.closed) {
                object = new ClosedSelectorException();
                throw object;
            }
            if (this.channelArray.length == this.totalChannels) {
                int n = this.pollWrapper.totalChannels * 2;
                SelectionKeyImpl[] arrselectionKeyImpl = new SelectionKeyImpl[n];
                for (int i = this.channelOffset; i < this.totalChannels; ++i) {
                    arrselectionKeyImpl[i] = this.channelArray[i];
                }
                this.channelArray = arrselectionKeyImpl;
                this.pollWrapper.grow(n);
            }
            this.channelArray[this.totalChannels] = object;
            ((SelectionKeyImpl)object).setIndex(this.totalChannels);
            this.pollWrapper.addEntry(((SelectionKeyImpl)object).channel);
            ++this.totalChannels;
            this.keys.add(object);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void putEventOps(SelectionKeyImpl object, int n) {
        Object object2 = this.closeLock;
        synchronized (object2) {
            if (!this.closed) {
                this.pollWrapper.putEventOps(((SelectionKeyImpl)object).getIndex(), n);
                return;
            }
            object = new ClosedSelectorException();
            throw object;
        }
    }

    protected int updateSelectedKeys() {
        int n = 0;
        for (int i = this.channelOffset; i < this.totalChannels; ++i) {
            int n2 = this.pollWrapper.getReventOps(i);
            int n3 = n;
            if (n2 != 0) {
                SelectionKeyImpl selectionKeyImpl = this.channelArray[i];
                this.pollWrapper.putReventOps(i, 0);
                if (this.selectedKeys.contains(selectionKeyImpl)) {
                    n3 = n;
                    if (selectionKeyImpl.channel.translateAndSetReadyOps(n2, selectionKeyImpl)) {
                        n3 = n + 1;
                    }
                } else {
                    selectionKeyImpl.channel.translateAndSetReadyOps(n2, selectionKeyImpl);
                    n3 = n;
                    if ((selectionKeyImpl.nioReadyOps() & selectionKeyImpl.nioInterestOps()) != 0) {
                        this.selectedKeys.add(selectionKeyImpl);
                        n3 = n + 1;
                    }
                }
            }
            n = n3;
        }
        return n;
    }

    @Override
    public Selector wakeup() {
        this.pollWrapper.interrupt();
        return this;
    }
}

