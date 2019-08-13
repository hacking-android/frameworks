/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels.spi;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectionKey;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;

public abstract class AbstractSelectableChannel
extends SelectableChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    boolean blocking = true;
    private int keyCount = 0;
    private final Object keyLock = new Object();
    private SelectionKey[] keys = null;
    private final SelectorProvider provider;
    private final Object regLock = new Object();

    protected AbstractSelectableChannel(SelectorProvider selectorProvider) {
        this.provider = selectorProvider;
    }

    private void addKey(SelectionKey selectionKey) {
        block5 : {
            int n = 0;
            SelectionKey[] arrselectionKey = this.keys;
            if (arrselectionKey != null && this.keyCount < arrselectionKey.length) {
                int n2 = 0;
                do {
                    arrselectionKey = this.keys;
                    n = ++n2;
                    if (n2 >= arrselectionKey.length) break block5;
                    if (arrselectionKey[n2] != null) continue;
                    n = n2;
                    break block5;
                } while (true);
            }
            arrselectionKey = this.keys;
            if (arrselectionKey == null) {
                this.keys = new SelectionKey[3];
            } else {
                SelectionKey[] arrselectionKey2;
                arrselectionKey = new SelectionKey[arrselectionKey.length * 2];
                for (n = 0; n < (arrselectionKey2 = this.keys).length; ++n) {
                    arrselectionKey[n] = arrselectionKey2[n];
                }
                this.keys = arrselectionKey;
                n = this.keyCount;
            }
        }
        this.keys[n] = selectionKey;
        ++this.keyCount;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SelectionKey findKey(Selector object) {
        Object object2 = this.keyLock;
        synchronized (object2) {
            if (this.keys == null) {
                return null;
            }
            int n = 0;
            while (n < this.keys.length) {
                if (this.keys[n] != null && this.keys[n].selector() == object) {
                    return this.keys[n];
                }
                ++n;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean haveValidKeys() {
        Object object = this.keyLock;
        synchronized (object) {
            if (this.keyCount == 0) {
                return false;
            }
            int n = 0;
            while (n < this.keys.length) {
                if (this.keys[n] != null && this.keys[n].isValid()) {
                    return true;
                }
                ++n;
            }
            return false;
        }
    }

    @Override
    public final Object blockingLock() {
        return this.regLock;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final SelectableChannel configureBlocking(boolean bl) throws IOException {
        Object object = this.regLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (this.blocking == bl) {
                return this;
            }
            if (bl && this.haveValidKeys()) {
                IllegalBlockingModeException illegalBlockingModeException = new IllegalBlockingModeException();
                throw illegalBlockingModeException;
            }
            this.implConfigureBlocking(bl);
            this.blocking = bl;
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void implCloseChannel() throws IOException {
        this.implCloseSelectableChannel();
        Object object = this.keyLock;
        synchronized (object) {
            int n = this.keys == null ? 0 : this.keys.length;
            int n2 = 0;
            while (n2 < n) {
                SelectionKey selectionKey = this.keys[n2];
                if (selectionKey != null) {
                    selectionKey.cancel();
                }
                ++n2;
            }
            return;
        }
    }

    protected abstract void implCloseSelectableChannel() throws IOException;

    protected abstract void implConfigureBlocking(boolean var1) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean isBlocking() {
        Object object = this.regLock;
        synchronized (object) {
            return this.blocking;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final boolean isRegistered() {
        Object object = this.keyLock;
        synchronized (object) {
            if (this.keyCount == 0) return false;
            return true;
        }
    }

    @Override
    public final SelectionKey keyFor(Selector selector) {
        return this.findKey(selector);
    }

    @Override
    public final SelectorProvider provider() {
        return this.provider;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final SelectionKey register(Selector object, int n, Object object2) throws ClosedChannelException {
        Object object3 = this.regLock;
        synchronized (object3) {
            if (!this.isOpen()) {
                object = new ClosedChannelException();
                throw object;
            }
            if ((this.validOps() & n) != 0) {
                object = new IllegalArgumentException();
                throw object;
            }
            if (this.blocking) {
                object = new IllegalBlockingModeException();
                throw object;
            }
            Object object4 = this.findKey((Selector)object);
            if (object4 != null) {
                ((SelectionKey)object4).interestOps(n);
                ((SelectionKey)object4).attach(object2);
            }
            SelectionKey selectionKey = object4;
            if (object4 == null) {
                object4 = this.keyLock;
                synchronized (object4) {
                    if (!this.isOpen()) {
                        object = new ClosedChannelException();
                        throw object;
                    }
                    selectionKey = ((AbstractSelector)object).register(this, n, object2);
                    this.addKey(selectionKey);
                }
            }
            return selectionKey;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeKey(SelectionKey selectionKey) {
        Object object = this.keyLock;
        synchronized (object) {
            int n = 0;
            do {
                if (n >= this.keys.length) {
                    ((AbstractSelectionKey)selectionKey).invalidate();
                    return;
                }
                if (this.keys[n] == selectionKey) {
                    this.keys[n] = null;
                    --this.keyCount;
                }
                ++n;
            } while (true);
        }
    }
}

