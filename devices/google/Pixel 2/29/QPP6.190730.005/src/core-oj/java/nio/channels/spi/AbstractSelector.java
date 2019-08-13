/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels.spi;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.AbstractSelectionKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import sun.nio.ch.Interruptible;

public abstract class AbstractSelector
extends Selector {
    private final Set<SelectionKey> cancelledKeys = new HashSet<SelectionKey>();
    private Interruptible interruptor = null;
    private final SelectorProvider provider;
    private AtomicBoolean selectorOpen = new AtomicBoolean(true);

    protected AbstractSelector(SelectorProvider selectorProvider) {
        this.provider = selectorProvider;
    }

    protected final void begin() {
        if (this.interruptor == null) {
            this.interruptor = new Interruptible(){

                @Override
                public void interrupt(Thread thread) {
                    AbstractSelector.this.wakeup();
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
    void cancel(SelectionKey selectionKey) {
        Set<SelectionKey> set = this.cancelledKeys;
        synchronized (set) {
            this.cancelledKeys.add(selectionKey);
            return;
        }
    }

    protected final Set<SelectionKey> cancelledKeys() {
        return this.cancelledKeys;
    }

    @Override
    public final void close() throws IOException {
        if (!this.selectorOpen.getAndSet(false)) {
            return;
        }
        this.implCloseSelector();
    }

    protected final void deregister(AbstractSelectionKey abstractSelectionKey) {
        ((AbstractSelectableChannel)abstractSelectionKey.channel()).removeKey(abstractSelectionKey);
    }

    protected final void end() {
        AbstractInterruptibleChannel.blockedOn(null);
    }

    protected abstract void implCloseSelector() throws IOException;

    @Override
    public final boolean isOpen() {
        return this.selectorOpen.get();
    }

    @Override
    public final SelectorProvider provider() {
        return this.provider;
    }

    protected abstract SelectionKey register(AbstractSelectableChannel var1, int var2, Object var3);

}

