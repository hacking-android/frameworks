/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.IllegalSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.Util;

public abstract class SelectorImpl
extends AbstractSelector {
    protected HashSet<SelectionKey> keys = new HashSet();
    private Set<SelectionKey> publicKeys;
    private Set<SelectionKey> publicSelectedKeys;
    protected Set<SelectionKey> selectedKeys = new HashSet<SelectionKey>();

    protected SelectorImpl(SelectorProvider selectorProvider) {
        super(selectorProvider);
        if (Util.atBugLevel("1.4")) {
            this.publicKeys = this.keys;
            this.publicSelectedKeys = this.selectedKeys;
        } else {
            this.publicKeys = Collections.unmodifiableSet(this.keys);
            this.publicSelectedKeys = Util.ungrowableSet(this.selectedKeys);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int lockAndDoSelect(long l) throws IOException {
        synchronized (this) {
            if (this.isOpen()) {
                Set<SelectionKey> set = this.publicKeys;
                synchronized (set) {
                    Set<SelectionKey> set2 = this.publicSelectedKeys;
                    synchronized (set2) {
                        return this.doSelect(l);
                    }
                }
            }
            ClosedSelectorException closedSelectorException = new ClosedSelectorException();
            throw closedSelectorException;
        }
    }

    protected abstract int doSelect(long var1) throws IOException;

    protected abstract void implClose() throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void implCloseSelector() throws IOException {
        this.wakeup();
        synchronized (this) {
            Set<SelectionKey> set = this.publicKeys;
            synchronized (set) {
                Set<SelectionKey> set2 = this.publicSelectedKeys;
                synchronized (set2) {
                    this.implClose();
                    return;
                }
            }
        }
    }

    protected abstract void implDereg(SelectionKeyImpl var1) throws IOException;

    protected abstract void implRegister(SelectionKeyImpl var1);

    @Override
    public Set<SelectionKey> keys() {
        if (!this.isOpen() && !Util.atBugLevel("1.4")) {
            throw new ClosedSelectorException();
        }
        return this.publicKeys;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void processDeregisterQueue() throws IOException {
        Throwable throwable2222;
        Object object;
        Iterator<SelectionKey> iterator;
        Set<SelectionKey> set = this.cancelledKeys();
        // MONITORENTER : set
        if (!set.isEmpty()) {
            iterator = set.iterator();
            while (iterator.hasNext()) {
                object = (SelectionKeyImpl)iterator.next();
                this.implDereg((SelectionKeyImpl)object);
                iterator.remove();
            }
        }
        // MONITOREXIT : set
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (SocketException socketException) {}
            {
                object = new IOException("Error deregistering key", socketException);
                throw object;
            }
        }
        iterator.remove();
        throw throwable2222;
    }

    public void putEventOps(SelectionKeyImpl selectionKeyImpl, int n) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final SelectionKey register(AbstractSelectableChannel object, int n, Object object2) {
        if (object instanceof SelChImpl) {
            SelectionKeyImpl selectionKeyImpl = new SelectionKeyImpl((SelChImpl)object, this);
            selectionKeyImpl.attach(object2);
            object = this.publicKeys;
            synchronized (object) {
                this.implRegister(selectionKeyImpl);
            }
            selectionKeyImpl.interestOps(n);
            return selectionKeyImpl;
        }
        throw new IllegalSelectorException();
    }

    @Override
    public int select() throws IOException {
        return this.select(0L);
    }

    @Override
    public int select(long l) throws IOException {
        if (l >= 0L) {
            if (l == 0L) {
                l = -1L;
            }
            return this.lockAndDoSelect(l);
        }
        throw new IllegalArgumentException("Negative timeout");
    }

    @Override
    public int selectNow() throws IOException {
        return this.lockAndDoSelect(0L);
    }

    @Override
    public Set<SelectionKey> selectedKeys() {
        if (!this.isOpen() && !Util.atBugLevel("1.4")) {
            throw new ClosedSelectorException();
        }
        return this.publicSelectedKeys;
    }

    @Override
    public abstract Selector wakeup();
}

