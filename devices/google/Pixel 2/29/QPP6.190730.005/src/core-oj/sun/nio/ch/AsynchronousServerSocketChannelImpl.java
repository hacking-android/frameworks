/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NetworkChannel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import sun.net.NetHooks;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.nio.ch.Cancellable;
import sun.nio.ch.Groupable;
import sun.nio.ch.Net;
import sun.nio.ch.PendingFuture;

abstract class AsynchronousServerSocketChannelImpl
extends AsynchronousServerSocketChannel
implements Cancellable,
Groupable {
    private volatile boolean acceptKilled;
    private ReadWriteLock closeLock = new ReentrantReadWriteLock();
    protected final FileDescriptor fd = Net.serverSocket(true);
    private boolean isReuseAddress;
    protected volatile InetSocketAddress localAddress = null;
    private volatile boolean open = true;
    private final Object stateLock = new Object();

    AsynchronousServerSocketChannelImpl(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        super(asynchronousChannelGroupImpl.provider());
    }

    @Override
    public final Future<AsynchronousSocketChannel> accept() {
        return this.implAccept(null, null);
    }

    @Override
    public final <A> void accept(A a, CompletionHandler<AsynchronousSocketChannel, ? super A> completionHandler) {
        if (completionHandler != null) {
            this.implAccept(a, completionHandler);
            return;
        }
        throw new NullPointerException("'handler' is null");
    }

    final void begin() throws IOException {
        this.closeLock.readLock().lock();
        if (this.isOpen()) {
            return;
        }
        throw new ClosedChannelException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final AsynchronousServerSocketChannel bind(SocketAddress object, int n) throws IOException {
        block8 : {
            object = object == null ? new InetSocketAddress(0) : Net.checkAddress((SocketAddress)object);
            Object object2 = System.getSecurityManager();
            if (object2 != null) {
                ((SecurityManager)object2).checkListen(((InetSocketAddress)object).getPort());
            }
            try {
                this.begin();
                object2 = this.stateLock;
                // MONITORENTER : object2
                if (this.localAddress != null) break block8;
            }
            catch (Throwable throwable) {
                this.end();
                throw throwable;
            }
            NetHooks.beforeTcpBind(this.fd, ((InetSocketAddress)object).getAddress(), ((InetSocketAddress)object).getPort());
            Net.bind(this.fd, ((InetSocketAddress)object).getAddress(), ((InetSocketAddress)object).getPort());
            object = this.fd;
            if (n < 1) {
                n = 50;
            }
            Net.listen((FileDescriptor)object, n);
            this.localAddress = Net.localAddress(this.fd);
            // MONITOREXIT : object2
            this.end();
            return this;
        }
        object = new AlreadyBoundException();
        throw object;
    }

    @Override
    public final void close() throws IOException {
        block4 : {
            this.closeLock.writeLock().lock();
            boolean bl = this.open;
            if (bl) break block4;
            this.closeLock.writeLock().unlock();
            return;
        }
        try {
            this.open = false;
            this.implClose();
            return;
        }
        finally {
            this.closeLock.writeLock().unlock();
        }
    }

    final void end() {
        this.closeLock.readLock().unlock();
    }

    @Override
    public final SocketAddress getLocalAddress() throws IOException {
        if (this.isOpen()) {
            return Net.getRevealedLocalAddress(this.localAddress);
        }
        throw new ClosedChannelException();
    }

    @Override
    public final <T> T getOption(SocketOption<T> object) throws IOException {
        if (object != null) {
            if (this.supportedOptions().contains(object)) {
                try {
                    this.begin();
                    if (object == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                        boolean bl = this.isReuseAddress;
                        return bl;
                    }
                    object = Net.getSocketOption(this.fd, Net.UNSPEC, object);
                    return (T)object;
                }
                finally {
                    this.end();
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'");
            stringBuilder.append(object);
            stringBuilder.append("' not supported");
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
        throw new NullPointerException();
    }

    abstract Future<AsynchronousSocketChannel> implAccept(Object var1, CompletionHandler<AsynchronousSocketChannel, Object> var2);

    abstract void implClose() throws IOException;

    final boolean isAcceptKilled() {
        return this.acceptKilled;
    }

    @Override
    public final boolean isOpen() {
        return this.open;
    }

    @Override
    public final void onCancel(PendingFuture<?, ?> pendingFuture) {
        this.acceptKilled = true;
    }

    @Override
    public final <T> AsynchronousServerSocketChannel setOption(SocketOption<T> socketOption, T object) throws IOException {
        if (socketOption != null) {
            if (this.supportedOptions().contains(socketOption)) {
                try {
                    this.begin();
                    if (socketOption == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                        this.isReuseAddress = (Boolean)object;
                    } else {
                        Net.setSocketOption(this.fd, Net.UNSPEC, socketOption, object);
                    }
                    return this;
                }
                finally {
                    this.end();
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("'");
            ((StringBuilder)object).append(socketOption);
            ((StringBuilder)object).append("' not supported");
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new NullPointerException();
    }

    @Override
    public final Set<SocketOption<?>> supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append('[');
        if (!this.isOpen()) {
            stringBuilder.append("closed");
        } else if (this.localAddress == null) {
            stringBuilder.append("unbound");
        } else {
            stringBuilder.append(Net.getRevealedLocalAddressAsString(this.localAddress));
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static class DefaultOptionsHolder {
        static final Set<SocketOption<?>> defaultOptions = DefaultOptionsHolder.defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set<SocketOption<?>> defaultOptions() {
            HashSet<SocketOption<Comparable<Integer>>> hashSet = new HashSet<SocketOption<Comparable<Integer>>>(2);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            return Collections.unmodifiableSet(hashSet);
        }
    }

}

