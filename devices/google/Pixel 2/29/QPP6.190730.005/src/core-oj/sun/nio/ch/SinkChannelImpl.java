/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Pipe;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.FileDispatcherImpl;
import sun.nio.ch.IOStatus;
import sun.nio.ch.IOUtil;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.NativeThread;
import sun.nio.ch.Net;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;
import sun.nio.ch.Util;

class SinkChannelImpl
extends Pipe.SinkChannel
implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_INUSE = 0;
    private static final int ST_KILLED = 1;
    private static final int ST_UNINITIALIZED = -1;
    private static final NativeDispatcher nd = new FileDispatcherImpl();
    FileDescriptor fd;
    int fdVal;
    private final Object lock = new Object();
    private volatile int state = -1;
    private final Object stateLock = new Object();
    private volatile long thread = 0L;

    SinkChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor) {
        super(selectorProvider);
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        this.state = 0;
    }

    private void ensureOpen() throws IOException {
        if (this.isOpen()) {
            return;
        }
        throw new ClosedChannelException();
    }

    @Override
    public FileDescriptor getFD() {
        return this.fd;
    }

    @Override
    public int getFDVal() {
        return this.fdVal;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void implCloseSelectableChannel() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            long l;
            if (this.state != 1) {
                nd.preClose(this.fd);
            }
            if ((l = this.thread) != 0L) {
                NativeThread.signal(l);
            }
            if (!this.isRegistered()) {
                this.kill();
            }
            return;
        }
    }

    @Override
    protected void implConfigureBlocking(boolean bl) throws IOException {
        IOUtil.configureBlocking(this.fd, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void kill() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.state == 1) {
                return;
            }
            if (this.state == -1) {
                this.state = 1;
                return;
            }
            nd.close(this.fd);
            this.state = 1;
            return;
        }
    }

    @Override
    public void translateAndSetInterestOps(int n, SelectionKeyImpl selectionKeyImpl) {
        int n2 = n;
        if (n == 4) {
            n2 = Net.POLLOUT;
        }
        selectionKeyImpl.selector.putEventOps(selectionKeyImpl, n2);
    }

    @Override
    public boolean translateAndSetReadyOps(int n, SelectionKeyImpl selectionKeyImpl) {
        return this.translateReadyOps(n, 0, selectionKeyImpl);
    }

    @Override
    public boolean translateAndUpdateReadyOps(int n, SelectionKeyImpl selectionKeyImpl) {
        return this.translateReadyOps(n, selectionKeyImpl.nioReadyOps(), selectionKeyImpl);
    }

    public boolean translateReadyOps(int n, int n2, SelectionKeyImpl selectionKeyImpl) {
        int n3 = selectionKeyImpl.nioInterestOps();
        int n4 = selectionKeyImpl.nioReadyOps();
        if ((Net.POLLNVAL & n) == 0) {
            int n5 = Net.POLLERR;
            short s = Net.POLLHUP;
            boolean bl = true;
            boolean bl2 = true;
            if (((n5 | s) & n) != 0) {
                selectionKeyImpl.nioReadyOps(n3);
                if ((n4 & n3) == 0) {
                    bl2 = false;
                }
                return bl2;
            }
            n5 = n2;
            if ((Net.POLLOUT & n) != 0) {
                n5 = n2;
                if ((n3 & 4) != 0) {
                    n5 = n2 | 4;
                }
            }
            selectionKeyImpl.nioReadyOps(n5);
            bl2 = (n4 & n5) != 0 ? bl : false;
            return bl2;
        }
        throw new Error("POLLNVAL detected");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        this.ensureOpen();
        Object object = this.lock;
        synchronized (object) {
            boolean bl;
            int n;
            block10 : {
                boolean bl2;
                boolean bl3;
                int n2;
                int n3;
                block8 : {
                    block9 : {
                        n3 = 0;
                        n = 0;
                        bl3 = true;
                        bl2 = true;
                        boolean bl4 = true;
                        n2 = n3;
                        this.begin();
                        n2 = n3;
                        bl = this.isOpen();
                        if (bl) break block8;
                        this.thread = 0L;
                        bl = bl4;
                        if (0 > 0) break block9;
                        bl = -2 == 0 ? bl4 : false;
                    }
                    this.end(bl);
                    return 0;
                }
                n2 = n3;
                try {
                    this.thread = NativeThread.current();
                    do {
                        n2 = n;
                        n3 = IOUtil.write(this.fd, byteBuffer, -1L, nd);
                        if (n3 != -3) break;
                        n = n3;
                        n2 = n3;
                    } while (this.isOpen());
                    n2 = n3;
                    n = IOStatus.normalize(n3);
                    this.thread = 0L;
                    bl = bl3;
                    if (n3 > 0) break block10;
                    bl = n3 == -2 ? bl3 : false;
                }
                catch (Throwable throwable) {
                    this.thread = 0L;
                    bl = bl2;
                    if (n2 <= 0) {
                        bl = n2 == -2 ? bl2 : false;
                    }
                    this.end(bl);
                    throw throwable;
                }
            }
            this.end(bl);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long write(ByteBuffer[] arrbyteBuffer) throws IOException {
        if (arrbyteBuffer == null) {
            throw new NullPointerException();
        }
        this.ensureOpen();
        Object object = this.lock;
        synchronized (object) {
            boolean bl;
            long l;
            block15 : {
                boolean bl2;
                boolean bl3;
                long l2;
                block13 : {
                    block14 : {
                        l = 0L;
                        bl2 = false;
                        bl3 = false;
                        bl = false;
                        l2 = l;
                        this.begin();
                        l2 = l;
                        boolean bl4 = this.isOpen();
                        if (bl4) break block13;
                        this.thread = 0L;
                        if (0L <= 0L && 0L != -2L) break block14;
                        bl = true;
                    }
                    this.end(bl);
                    return 0L;
                }
                l2 = l;
                try {
                    long l3;
                    this.thread = NativeThread.current();
                    do {
                        l2 = l;
                        l3 = IOUtil.write(this.fd, arrbyteBuffer, nd);
                        if (l3 != -3L) break;
                        l = l3;
                        l2 = l3;
                    } while (this.isOpen());
                    l2 = l3;
                    l = IOStatus.normalize(l3);
                    this.thread = 0L;
                    if (l3 <= 0L) {
                        bl = bl2;
                        if (l3 != -2L) break block15;
                    }
                    bl = true;
                }
                catch (Throwable throwable) {
                    block17 : {
                        block16 : {
                            this.thread = 0L;
                            if (l2 > 0L) break block16;
                            bl = bl3;
                            if (l2 != -2L) break block17;
                        }
                        bl = true;
                    }
                    this.end(bl);
                    throw throwable;
                }
            }
            this.end(bl);
            return l;
        }
    }

    @Override
    public long write(ByteBuffer[] arrbyteBuffer, int n, int n2) throws IOException {
        if (n >= 0 && n2 >= 0 && n <= arrbyteBuffer.length - n2) {
            return this.write(Util.subsequence(arrbyteBuffer, n, n2));
        }
        throw new IndexOutOfBoundsException();
    }
}

