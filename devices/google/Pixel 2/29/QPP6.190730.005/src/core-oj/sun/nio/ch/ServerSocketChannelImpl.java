/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NetworkChannel;
import java.nio.channels.NotYetBoundException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import sun.net.NetHooks;
import sun.nio.ch.IOUtil;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.NativeThread;
import sun.nio.ch.Net;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;
import sun.nio.ch.ServerSocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import sun.nio.ch.SocketDispatcher;

class ServerSocketChannelImpl
extends ServerSocketChannel
implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_INUSE = 0;
    private static final int ST_KILLED = 1;
    private static final int ST_UNINITIALIZED = -1;
    private static NativeDispatcher nd;
    private final FileDescriptor fd;
    private int fdVal;
    private boolean isReuseAddress;
    private InetSocketAddress localAddress;
    private final Object lock = new Object();
    ServerSocket socket;
    private int state = -1;
    private final Object stateLock = new Object();
    private volatile long thread = 0L;

    static {
        ServerSocketChannelImpl.initIDs();
        nd = new SocketDispatcher();
    }

    ServerSocketChannelImpl(SelectorProvider selectorProvider) throws IOException {
        super(selectorProvider);
        this.fd = Net.serverSocket(true);
        this.fdVal = IOUtil.fdVal(this.fd);
        this.state = 0;
    }

    ServerSocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, boolean bl) throws IOException {
        super(selectorProvider);
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        this.state = 0;
        if (bl) {
            this.localAddress = Net.localAddress(fileDescriptor);
        }
    }

    private int accept(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, InetSocketAddress[] arrinetSocketAddress) throws IOException {
        return this.accept0(fileDescriptor, fileDescriptor2, arrinetSocketAddress);
    }

    private native int accept0(FileDescriptor var1, FileDescriptor var2, InetSocketAddress[] var3) throws IOException;

    private static native void initIDs();

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SocketChannel accept() throws IOException {
        Object object = this.lock;
        synchronized (object) {
            int n;
            boolean bl;
            int n2;
            Object object2;
            Object object3;
            boolean bl2;
            int n3;
            block15 : {
                if (!this.isOpen()) {
                    ClosedChannelException closedChannelException = new ClosedChannelException();
                    throw closedChannelException;
                }
                if (!this.isBound()) {
                    NotYetBoundException notYetBoundException = new NotYetBoundException();
                    throw notYetBoundException;
                }
                n3 = 0;
                n2 = 0;
                object3 = new FileDescriptor();
                bl = true;
                bl2 = true;
                object2 = new InetSocketAddress[1];
                n = n3;
                this.begin();
                n = n3;
                boolean bl3 = this.isOpen();
                if (bl3) break block15;
                this.thread = 0L;
                bl = 0 > 0 ? bl2 : false;
                this.end(bl);
                return null;
            }
            n = n3;
            try {
                this.thread = NativeThread.current();
                n = n2;
                while ((n2 = this.accept(this.fd, (FileDescriptor)object3, (InetSocketAddress[])object2)) == -3) {
                    n = n2;
                    bl2 = this.isOpen();
                    if (!bl2) break;
                    n = n2;
                }
                this.thread = 0L;
                bl = n2 > 0;
            }
            catch (Throwable throwable) {
                this.thread = 0L;
                if (n <= 0) {
                    bl = false;
                }
                this.end(bl);
                throw throwable;
            }
            this.end(bl);
            if (n2 < 1) {
                return null;
            }
            IOUtil.configureBlocking((FileDescriptor)object3, true);
            InetSocketAddress inetSocketAddress = object2[0];
            object2 = new SocketChannelImpl(this.provider(), (FileDescriptor)object3, inetSocketAddress);
            object3 = System.getSecurityManager();
            if (object3 != null) {
                try {
                    ((SecurityManager)object3).checkAccept(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                }
                catch (SecurityException securityException) {
                    ((AbstractInterruptibleChannel)object2).close();
                    throw securityException;
                }
            }
            return object2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ServerSocketChannel bind(SocketAddress object, int n) throws IOException {
        Object object2 = this.lock;
        synchronized (object2) {
            if (!this.isOpen()) {
                object = new ClosedChannelException();
                throw object;
            }
            if (this.isBound()) {
                object = new AlreadyBoundException();
                throw object;
            }
            object = object == null ? new InetSocketAddress(0) : Net.checkAddress((SocketAddress)object);
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkListen(((InetSocketAddress)object).getPort());
            }
            NetHooks.beforeTcpBind(this.fd, ((InetSocketAddress)object).getAddress(), ((InetSocketAddress)object).getPort());
            Net.bind(this.fd, ((InetSocketAddress)object).getAddress(), ((InetSocketAddress)object).getPort());
            object = this.fd;
            if (n < 1) {
                n = 50;
            }
            Net.listen((FileDescriptor)object, n);
            object = this.stateLock;
            synchronized (object) {
                this.localAddress = Net.localAddress(this.fd);
                return this;
            }
        }
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
    public SocketAddress getLocalAddress() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (this.localAddress != null) return Net.getRevealedLocalAddress(Net.asInetSocketAddress(this.localAddress));
            return this.localAddress;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> T getOption(SocketOption<T> object) throws IOException {
        if (object == null) {
            throw new NullPointerException();
        }
        if (!this.supportedOptions().contains(object)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(object);
            stringBuilder.append("' not supported");
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
        Object object2 = this.stateLock;
        synchronized (object2) {
            if (!this.isOpen()) {
                object = new ClosedChannelException();
                throw object;
            }
            if (object == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                boolean bl = this.isReuseAddress;
                return bl;
            }
            object = Net.getSocketOption(this.fd, Net.UNSPEC, object);
            return (T)object;
        }
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
    public boolean isBound() {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.localAddress == null) return false;
            return true;
        }
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InetSocketAddress localAddress() {
        Object object = this.stateLock;
        synchronized (object) {
            return this.localAddress;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    int poll(int n, long l) throws IOException {
        Object object = this.lock;
        // MONITORENTER : object
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        try {
            this.begin();
            Object object2 = this.stateLock;
            // MONITORENTER : object2
        }
        catch (Throwable throwable) {
            this.thread = 0L;
            bl3 = 0 > 0 ? bl2 : false;
            this.end(bl3);
            throw throwable;
        }
        if (!this.isOpen()) {
            // MONITOREXIT : object2
            this.thread = 0L;
            if (0 <= 0) {
                bl3 = false;
            }
            this.end(bl3);
            // MONITOREXIT : object
            return 0;
        }
        this.thread = NativeThread.current();
        // MONITOREXIT : object2
        n = Net.poll(this.fd, n, l);
        this.thread = 0L;
        bl3 = n > 0 ? bl : false;
        this.end(bl3);
        // MONITOREXIT : object
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> ServerSocketChannel setOption(SocketOption<T> object, T object2) throws IOException {
        if (object == null) {
            throw new NullPointerException();
        }
        if (!this.supportedOptions().contains(object)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("'");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append("' not supported");
            throw new UnsupportedOperationException(((StringBuilder)object2).toString());
        }
        Object object3 = this.stateLock;
        synchronized (object3) {
            if (!this.isOpen()) {
                object = new ClosedChannelException();
                throw object;
            }
            if (object == StandardSocketOptions.IP_TOS) {
                StandardProtocolFamily standardProtocolFamily = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
                Net.setSocketOption(this.fd, standardProtocolFamily, object, object2);
                return this;
            }
            if (object == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                this.isReuseAddress = (Boolean)object2;
            } else {
                Net.setSocketOption(this.fd, Net.UNSPEC, object, object2);
            }
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ServerSocket socket() {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.socket != null) return this.socket;
            this.socket = ServerSocketAdaptor.create(this);
            return this.socket;
        }
    }

    @Override
    public final Set<SocketOption<?>> supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getName());
        stringBuffer.append('[');
        if (!this.isOpen()) {
            stringBuffer.append("closed");
        } else {
            Object object = this.stateLock;
            synchronized (object) {
                InetSocketAddress inetSocketAddress = this.localAddress();
                if (inetSocketAddress == null) {
                    stringBuffer.append("unbound");
                } else {
                    stringBuffer.append(Net.getRevealedLocalAddressAsString(inetSocketAddress));
                }
            }
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    @Override
    public void translateAndSetInterestOps(int n, SelectionKeyImpl selectionKeyImpl) {
        int n2 = 0;
        if ((n & 16) != 0) {
            n2 = 0 | Net.POLLIN;
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
        int n5 = Net.POLLNVAL;
        boolean bl = false;
        boolean bl2 = false;
        if ((n5 & n) != 0) {
            return false;
        }
        if (((Net.POLLERR | Net.POLLHUP) & n) != 0) {
            selectionKeyImpl.nioReadyOps(n3);
            if ((n4 & n3) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        n5 = n2;
        if ((Net.POLLIN & n) != 0) {
            n5 = n2;
            if ((n3 & 16) != 0) {
                n5 = n2 | 16;
            }
        }
        selectionKeyImpl.nioReadyOps(n5);
        bl2 = bl;
        if ((n4 & n5) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    private static class DefaultOptionsHolder {
        static final Set<SocketOption<?>> defaultOptions = DefaultOptionsHolder.defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set<SocketOption<?>> defaultOptions() {
            HashSet<SocketOption<Comparable<Integer>>> hashSet = new HashSet<SocketOption<Comparable<Integer>>>(2);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            hashSet.add(StandardSocketOptions.IP_TOS);
            return Collections.unmodifiableSet(hashSet);
        }
    }

}

