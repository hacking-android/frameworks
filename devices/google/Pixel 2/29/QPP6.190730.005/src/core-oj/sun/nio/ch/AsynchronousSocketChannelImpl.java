/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NetworkChannel;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.ReadPendingException;
import java.nio.channels.WritePendingException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import jdk.net.ExtendedSocketOptions;
import jdk.net.SocketFlow;
import sun.net.ExtendedOptionsImpl;
import sun.net.NetHooks;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.nio.ch.Cancellable;
import sun.nio.ch.CompletedFuture;
import sun.nio.ch.Groupable;
import sun.nio.ch.Invoker;
import sun.nio.ch.Net;
import sun.nio.ch.Util;

abstract class AsynchronousSocketChannelImpl
extends AsynchronousSocketChannel
implements Cancellable,
Groupable {
    static final int ST_CONNECTED = 2;
    static final int ST_PENDING = 1;
    static final int ST_UNCONNECTED = 0;
    static final int ST_UNINITIALIZED = -1;
    private final ReadWriteLock closeLock = new ReentrantReadWriteLock();
    protected final FileDescriptor fd;
    private boolean isReuseAddress;
    protected volatile InetSocketAddress localAddress = null;
    private volatile boolean open = true;
    private boolean readKilled;
    private final Object readLock = new Object();
    private boolean readShutdown;
    private boolean reading;
    protected volatile InetSocketAddress remoteAddress = null;
    protected volatile int state = -1;
    protected final Object stateLock = new Object();
    private boolean writeKilled;
    private final Object writeLock = new Object();
    private boolean writeShutdown;
    private boolean writing;

    AsynchronousSocketChannelImpl(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) throws IOException {
        super(asynchronousChannelGroupImpl.provider());
        this.fd = Net.socket(true);
        this.state = 0;
    }

    AsynchronousSocketChannelImpl(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
        super(asynchronousChannelGroupImpl.provider());
        this.fd = fileDescriptor;
        this.state = 2;
        this.localAddress = Net.localAddress(fileDescriptor);
        this.remoteAddress = inetSocketAddress;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private <V extends Number, A> Future<V> read(boolean bl, ByteBuffer object, ByteBuffer[] arrbyteBuffer, long l, TimeUnit timeUnit, A a, CompletionHandler<V, ? super A> completionHandler) {
        boolean bl2;
        if (!this.isOpen()) {
            object = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure((Throwable)object);
            }
            Invoker.invoke(this, completionHandler, a, null, (Throwable)object);
            return null;
        }
        if (this.remoteAddress == null) throw new NotYetConnectedException();
        int n = 0;
        int n2 = !bl && !((Buffer)object).hasRemaining() ? 0 : 1;
        boolean bl3 = false;
        Object object2 = this.readLock;
        // MONITORENTER : object2
        if (this.readKilled) {
            object = new IllegalStateException("Reading not allowed due to timeout or cancellation");
            throw object;
        }
        if (this.reading) {
            object = new ReadPendingException();
            throw object;
        }
        if (this.readShutdown) {
            bl2 = true;
        } else {
            bl2 = bl3;
            if (n2 != 0) {
                this.reading = true;
                bl2 = bl3;
            }
        }
        // MONITOREXIT : object2
        if (!bl2) {
            if (n2 != 0) return this.implRead(bl, (ByteBuffer)object, arrbyteBuffer, l, timeUnit, a, completionHandler);
        }
        if (bl) {
            l = bl2 ? -1L : 0L;
            object = l;
        } else {
            n2 = n;
            if (bl2) {
                n2 = -1;
            }
            object = n2;
        }
        if (completionHandler == null) {
            return CompletedFuture.withResult(object);
        }
        Invoker.invoke(this, completionHandler, a, object, null);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private <V extends Number, A> Future<V> write(boolean bl, ByteBuffer object, ByteBuffer[] arrbyteBuffer, long l, TimeUnit timeUnit, A a, CompletionHandler<V, ? super A> completionHandler) {
        boolean bl2;
        boolean bl3 = bl || ((Buffer)object).hasRemaining();
        boolean bl4 = false;
        if (this.isOpen()) {
            if (this.remoteAddress == null) {
                throw new NotYetConnectedException();
            }
            Object object2 = this.writeLock;
            synchronized (object2) {
                if (this.writeKilled) {
                    object = new IllegalStateException("Writing not allowed due to timeout or cancellation");
                    throw object;
                }
                if (this.writing) {
                    object = new WritePendingException();
                    throw object;
                }
                if (this.writeShutdown) {
                    bl2 = true;
                } else {
                    bl2 = bl4;
                    if (bl3) {
                        this.writing = true;
                        bl2 = bl4;
                    }
                }
            }
        } else {
            bl2 = true;
        }
        if (bl2) {
            object = new ClosedChannelException();
            if (completionHandler == null) {
                return CompletedFuture.withFailure((Throwable)object);
            }
            Invoker.invoke(this, completionHandler, a, null, (Throwable)object);
            return null;
        }
        if (bl3) {
            return this.implWrite(bl, (ByteBuffer)object, arrbyteBuffer, l, timeUnit, a, completionHandler);
        }
        object = bl ? (Number)0L : (Number)0;
        if (completionHandler == null) {
            return CompletedFuture.withResult(object);
        }
        Invoker.invoke(this, completionHandler, a, object, null);
        return null;
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
    public final AsynchronousSocketChannel bind(SocketAddress serializable) throws IOException {
        block7 : {
            block8 : {
                try {
                    this.begin();
                    Object object = this.stateLock;
                    // MONITORENTER : object
                    if (this.state == 1) break block7;
                    if (this.localAddress != null) break block8;
                    serializable = serializable == null ? new InetSocketAddress(0) : Net.checkAddress((SocketAddress)serializable);
                }
                catch (Throwable throwable) {
                    this.end();
                    throw throwable;
                }
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    securityManager.checkListen(((InetSocketAddress)serializable).getPort());
                }
                NetHooks.beforeTcpBind(this.fd, ((InetSocketAddress)serializable).getAddress(), ((InetSocketAddress)serializable).getPort());
                Net.bind(this.fd, ((InetSocketAddress)serializable).getAddress(), ((InetSocketAddress)serializable).getPort());
                this.localAddress = Net.localAddress(this.fd);
                // MONITOREXIT : object
                this.end();
                return this;
            }
            serializable = new AlreadyBoundException();
            throw serializable;
        }
        serializable = new ConnectionPendingException();
        throw serializable;
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

    @Override
    public final Future<Void> connect(SocketAddress socketAddress) {
        return this.implConnect(socketAddress, null, null);
    }

    @Override
    public final <A> void connect(SocketAddress socketAddress, A a, CompletionHandler<Void, ? super A> completionHandler) {
        if (completionHandler != null) {
            this.implConnect(socketAddress, a, completionHandler);
            return;
        }
        throw new NullPointerException("'handler' is null");
    }

    final void enableReading() {
        this.enableReading(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void enableReading(boolean bl) {
        Object object = this.readLock;
        synchronized (object) {
            this.reading = false;
            if (bl) {
                this.readKilled = true;
            }
            return;
        }
    }

    final void enableWriting() {
        this.enableWriting(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void enableWriting(boolean bl) {
        Object object = this.writeLock;
        synchronized (object) {
            this.writing = false;
            if (bl) {
                this.writeKilled = true;
            }
            return;
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

    @Override
    public final SocketAddress getRemoteAddress() throws IOException {
        if (this.isOpen()) {
            return this.remoteAddress;
        }
        throw new ClosedChannelException();
    }

    abstract void implClose() throws IOException;

    abstract <A> Future<Void> implConnect(SocketAddress var1, A var2, CompletionHandler<Void, ? super A> var3);

    abstract <V extends Number, A> Future<V> implRead(boolean var1, ByteBuffer var2, ByteBuffer[] var3, long var4, TimeUnit var6, A var7, CompletionHandler<V, ? super A> var8);

    abstract <V extends Number, A> Future<V> implWrite(boolean var1, ByteBuffer var2, ByteBuffer[] var3, long var4, TimeUnit var6, A var7, CompletionHandler<V, ? super A> var8);

    @Override
    public final boolean isOpen() {
        return this.open;
    }

    final void killConnect() {
        this.killReading();
        this.killWriting();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void killReading() {
        Object object = this.readLock;
        synchronized (object) {
            this.readKilled = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void killWriting() {
        Object object = this.writeLock;
        synchronized (object) {
            this.writeKilled = true;
            return;
        }
    }

    @Override
    public final Future<Integer> read(ByteBuffer byteBuffer) {
        if (!byteBuffer.isReadOnly()) {
            return this.read(false, byteBuffer, null, 0L, TimeUnit.MILLISECONDS, null, null);
        }
        throw new IllegalArgumentException("Read-only buffer");
    }

    @Override
    public final <A> void read(ByteBuffer byteBuffer, long l, TimeUnit timeUnit, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        if (completionHandler != null) {
            if (!byteBuffer.isReadOnly()) {
                this.read(false, byteBuffer, null, l, timeUnit, a, completionHandler);
                return;
            }
            throw new IllegalArgumentException("Read-only buffer");
        }
        throw new NullPointerException("'handler' is null");
    }

    @Override
    public final <A> void read(ByteBuffer[] arrbyteBuffer, int n, int n2, long l, TimeUnit timeUnit, A a, CompletionHandler<Long, ? super A> completionHandler) {
        if (completionHandler != null) {
            if (n >= 0 && n2 >= 0 && n <= arrbyteBuffer.length - n2) {
                arrbyteBuffer = Util.subsequence(arrbyteBuffer, n, n2);
                for (n = 0; n < arrbyteBuffer.length; ++n) {
                    if (!arrbyteBuffer[n].isReadOnly()) {
                        continue;
                    }
                    throw new IllegalArgumentException("Read-only buffer");
                }
                this.read(true, null, arrbyteBuffer, l, timeUnit, a, completionHandler);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException("'handler' is null");
    }

    @Override
    public final <T> AsynchronousSocketChannel setOption(SocketOption<T> object, T object2) throws IOException {
        if (object != null) {
            if (this.supportedOptions().contains(object)) {
                try {
                    this.begin();
                    if (!this.writeShutdown) {
                        if (object == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind()) {
                            this.isReuseAddress = (Boolean)object2;
                        } else {
                            Net.setSocketOption(this.fd, Net.UNSPEC, object, object2);
                        }
                        return this;
                    }
                    object = new IOException("Connection has been shutdown for writing");
                    throw object;
                }
                finally {
                    this.end();
                }
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("'");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append("' not supported");
            throw new UnsupportedOperationException(((StringBuilder)object2).toString());
        }
        throw new NullPointerException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final AsynchronousSocketChannel shutdownInput() throws IOException {
        block6 : {
            try {
                this.begin();
                if (this.remoteAddress == null) ** GOTO lbl-1000
                var1_1 = this.readLock;
                // MONITORENTER : var1_1
                if (this.readShutdown) break block6;
            }
            catch (Throwable var1_3) {
                this.end();
                throw var1_3;
            }
            Net.shutdown(this.fd, 0);
            this.readShutdown = true;
        }
        // MONITOREXIT : var1_1
        this.end();
        return this;
lbl-1000: // 1 sources:
        {
            var1_2 = new NotYetConnectedException();
            throw var1_2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final AsynchronousSocketChannel shutdownOutput() throws IOException {
        block6 : {
            try {
                this.begin();
                if (this.remoteAddress == null) ** GOTO lbl-1000
                var1_1 = this.writeLock;
                // MONITORENTER : var1_1
                if (this.writeShutdown) break block6;
            }
            catch (Throwable var1_3) {
                this.end();
                throw var1_3;
            }
            Net.shutdown(this.fd, 1);
            this.writeShutdown = true;
        }
        // MONITOREXIT : var1_1
        this.end();
        return this;
lbl-1000: // 1 sources:
        {
            var1_2 = new NotYetConnectedException();
            throw var1_2;
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
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append('[');
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                stringBuilder.append("closed");
            } else {
                int n = this.state;
                if (n != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            stringBuilder.append("connected");
                            if (this.readShutdown) {
                                stringBuilder.append(" ishut");
                            }
                            if (this.writeShutdown) {
                                stringBuilder.append(" oshut");
                            }
                        }
                    } else {
                        stringBuilder.append("connection-pending");
                    }
                } else {
                    stringBuilder.append("unconnected");
                }
                if (this.localAddress != null) {
                    stringBuilder.append(" local=");
                    stringBuilder.append(Net.getRevealedLocalAddressAsString(this.localAddress));
                }
                if (this.remoteAddress != null) {
                    stringBuilder.append(" remote=");
                    stringBuilder.append(this.remoteAddress.toString());
                }
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public final Future<Integer> write(ByteBuffer byteBuffer) {
        return this.write(false, byteBuffer, null, 0L, TimeUnit.MILLISECONDS, null, null);
    }

    @Override
    public final <A> void write(ByteBuffer byteBuffer, long l, TimeUnit timeUnit, A a, CompletionHandler<Integer, ? super A> completionHandler) {
        if (completionHandler != null) {
            this.write(false, byteBuffer, null, l, timeUnit, a, completionHandler);
            return;
        }
        throw new NullPointerException("'handler' is null");
    }

    @Override
    public final <A> void write(ByteBuffer[] arrbyteBuffer, int n, int n2, long l, TimeUnit timeUnit, A a, CompletionHandler<Long, ? super A> completionHandler) {
        if (completionHandler != null) {
            if (n >= 0 && n2 >= 0 && n <= arrbyteBuffer.length - n2) {
                this.write(true, null, Util.subsequence(arrbyteBuffer, n, n2), l, timeUnit, a, completionHandler);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException("'handler' is null");
    }

    private static class DefaultOptionsHolder {
        static final Set<SocketOption<?>> defaultOptions = DefaultOptionsHolder.defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set<SocketOption<?>> defaultOptions() {
            HashSet<SocketOption<Object>> hashSet = new HashSet<SocketOption<Object>>(5);
            hashSet.add(StandardSocketOptions.SO_SNDBUF);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            hashSet.add(StandardSocketOptions.TCP_NODELAY);
            if (ExtendedOptionsImpl.flowSupported()) {
                hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
            }
            return Collections.unmodifiableSet(hashSet);
        }
    }

}

