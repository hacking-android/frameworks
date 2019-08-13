/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectionKey;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectorImpl;

public class SelectionKeyImpl
extends AbstractSelectionKey {
    final SelChImpl channel;
    private int index;
    private volatile int interestOps;
    private int readyOps;
    public final SelectorImpl selector;

    SelectionKeyImpl(SelChImpl selChImpl, SelectorImpl selectorImpl) {
        this.channel = selChImpl;
        this.selector = selectorImpl;
    }

    private void ensureValid() {
        if (this.isValid()) {
            return;
        }
        throw new CancelledKeyException();
    }

    @Override
    public SelectableChannel channel() {
        return (SelectableChannel)((Object)this.channel);
    }

    int getIndex() {
        return this.index;
    }

    @Override
    public int interestOps() {
        this.ensureValid();
        return this.interestOps;
    }

    @Override
    public SelectionKey interestOps(int n) {
        this.ensureValid();
        return this.nioInterestOps(n);
    }

    public int nioInterestOps() {
        return this.interestOps;
    }

    public SelectionKey nioInterestOps(int n) {
        if ((this.channel().validOps() & n) == 0) {
            this.channel.translateAndSetInterestOps(n, this);
            this.interestOps = n;
            return this;
        }
        throw new IllegalArgumentException();
    }

    public int nioReadyOps() {
        return this.readyOps;
    }

    public void nioReadyOps(int n) {
        this.readyOps = n;
    }

    @Override
    public int readyOps() {
        this.ensureValid();
        return this.readyOps;
    }

    @Override
    public Selector selector() {
        return this.selector;
    }

    void setIndex(int n) {
        this.index = n;
    }
}

