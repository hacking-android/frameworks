/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.CloseGuard
 */
package sun.nio.ch;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.InterruptedByTimeoutException;
import java.nio.channels.ShutdownChannelGroupException;
import java.security.AccessController;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import sun.net.NetHooks;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.nio.ch.AsynchronousSocketChannelImpl;
import sun.nio.ch.CompletedFuture;
import sun.nio.ch.IOUtil;
import sun.nio.ch.Invoker;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.Net;
import sun.nio.ch.PendingFuture;
import sun.nio.ch.Port;
import sun.nio.ch.SocketDispatcher;
import sun.security.action.GetPropertyAction;

class UnixAsynchronousSocketChannelImpl
extends AsynchronousSocketChannelImpl
implements Port.PollableChannel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean disableSynchronousRead;
    private static final NativeDispatcher nd;
    private Object connectAttachment;
    private PendingFuture<Void, Object> connectFuture;
    private CompletionHandler<Void, Object> connectHandler;
    private boolean connectPending;
    private final int fdVal;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private boolean isGatheringWrite;
    private boolean isScatteringRead;
    private SocketAddress pendingRemote;
    private final Port port;
    private Object readAttachment;
    private ByteBuffer readBuffer;
    private ByteBuffer[] readBuffers;
    private PendingFuture<Number, Object> readFuture;
    private CompletionHandler<Number, Object> readHandler;
    private boolean readPending;
    private Runnable readTimeoutTask = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            CompletionHandler completionHandler;
            PendingFuture pendingFuture;
            Object object;
            Object object2 = UnixAsynchronousSocketChannelImpl.this.updateLock;
            synchronized (object2) {
                if (!UnixAsynchronousSocketChannelImpl.this.readPending) {
                    return;
                }
                UnixAsynchronousSocketChannelImpl.this.readPending = false;
                completionHandler = UnixAsynchronousSocketChannelImpl.this.readHandler;
                object = UnixAsynchronousSocketChannelImpl.this.readAttachment;
                pendingFuture = UnixAsynchronousSocketChannelImpl.this.readFuture;
            }
            UnixAsynchronousSocketChannelImpl.this.enableReading(true);
            object2 = new InterruptedByTimeoutException();
            if (completionHandler == null) {
                pendingFuture.setFailure((Throwable)object2);
                return;
            }
            Invoker.invokeIndirectly(UnixAsynchronousSocketChannelImpl.this, completionHandler, object, null, (Throwable)object2);
        }
    };
    private Future<?> readTimer;
    private final Object updateLock = new Object();
    private Object writeAttachment;
    private ByteBuffer writeBuffer;
    private ByteBuffer[] writeBuffers;
    private PendingFuture<Number, Object> writeFuture;
    private CompletionHandler<Number, Object> writeHandler;
    private boolean writePending;
    private Runnable writeTimeoutTask = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            CompletionHandler completionHandler;
            PendingFuture pendingFuture;
            Object object;
            Object object2 = UnixAsynchronousSocketChannelImpl.this.updateLock;
            synchronized (object2) {
                if (!UnixAsynchronousSocketChannelImpl.this.writePending) {
                    return;
                }
                UnixAsynchronousSocketChannelImpl.this.writePending = false;
                completionHandler = UnixAsynchronousSocketChannelImpl.this.writeHandler;
                object = UnixAsynchronousSocketChannelImpl.this.writeAttachment;
                pendingFuture = UnixAsynchronousSocketChannelImpl.this.writeFuture;
            }
            UnixAsynchronousSocketChannelImpl.this.enableWriting(true);
            object2 = new InterruptedByTimeoutException();
            if (completionHandler != null) {
                Invoker.invokeIndirectly(UnixAsynchronousSocketChannelImpl.this, completionHandler, object, null, (Throwable)object2);
                return;
            }
            pendingFuture.setFailure((Throwable)object2);
        }
    };
    private Future<?> writeTimer;

    static {
        nd = new SocketDispatcher();
        String string = AccessController.doPrivileged(new GetPropertyAction("sun.nio.ch.disableSynchronousRead", "false"));
        boolean bl = string.length() == 0 ? true : Boolean.valueOf(string);
        disableSynchronousRead = bl;
    }

    UnixAsynchronousSocketChannelImpl(Port port) throws IOException {
        super(port);
        try {
            IOUtil.configureBlocking(this.fd, false);
            this.port = port;
        }
        catch (IOException iOException) {
            nd.close(this.fd);
            throw iOException;
        }
        this.fdVal = IOUtil.fdVal(this.fd);
        port.register(this.fdVal, this);
        this.guard.open("close");
    }

    UnixAsynchronousSocketChannelImpl(Port port, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
        super(port, fileDescriptor, inetSocketAddress);
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        IOUtil.configureBlocking(fileDescriptor, false);
        try {
            port.register(this.fdVal, this);
        }
        catch (ShutdownChannelGroupException shutdownChannelGroupException) {
            throw new IOException(shutdownChannelGroupException);
        }
        this.port = port;
        this.guard.open("close");
    }

    private static native void checkConnect(int var0) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void finish(boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        Object object = this.updateLock;
        // MONITORENTER : object
        boolean bl7 = bl4;
        if (bl2) {
            bl7 = bl4;
            if (this.readPending) {
                this.readPending = false;
                bl7 = true;
            }
        }
        bl4 = bl5;
        boolean bl8 = bl6;
        if (bl3) {
            if (this.writePending) {
                this.writePending = false;
                bl4 = true;
                bl8 = bl6;
            } else {
                bl4 = bl5;
                bl8 = bl6;
                if (this.connectPending) {
                    this.connectPending = false;
                    bl8 = true;
                    bl4 = bl5;
                }
            }
        }
        // MONITOREXIT : object
        if (bl7) {
            if (bl4) {
                this.finishWrite(false);
            }
            this.finishRead(bl);
            return;
        }
        if (bl4) {
            this.finishWrite(bl);
        }
        if (!bl8) return;
        this.finishConnect(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void finishConnect(boolean bl) {
        Throwable throwable = null;
        try {
            this.begin();
            UnixAsynchronousSocketChannelImpl.checkConnect(this.fdVal);
            this.setConnected();
        }
        catch (Throwable throwable2) {
            block10 : {
                throwable = throwable2;
                if (!(throwable2 instanceof ClosedChannelException)) break block10;
                throwable = new AsynchronousCloseException();
            }
            if (throwable != null) {
                try {
                    this.close();
                }
                catch (Throwable throwable3) {
                    throwable.addSuppressed(throwable3);
                }
            }
            CompletionHandler<Void, Object> completionHandler = this.connectHandler;
            Object object = this.connectAttachment;
            PendingFuture<Void, Object> pendingFuture = this.connectFuture;
            if (completionHandler == null) {
                pendingFuture.setResult(null, throwable);
                return;
            }
            if (bl) {
                Invoker.invokeUnchecked(completionHandler, object, null, throwable);
                return;
            }
            Invoker.invokeIndirectly(this, completionHandler, object, null, throwable);
            return;
            finally {
                this.end();
            }
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
    private void finishRead(boolean var1_1) {
        var2_2 = -1;
        var3_3 = null;
        var4_7 = null;
        var5_8 = this.isScatteringRead;
        var6_9 = this.readHandler;
        var7_10 = this.readAttachment;
        var8_11 = this.readFuture;
        var9_12 = this.readTimer;
        var10_13 = null;
        var11_14 = var2_2;
        this.begin();
        if (var5_8) {
            var11_14 = var2_2;
            var2_2 = (int)IOUtil.read(this.fd, this.readBuffers, UnixAsynchronousSocketChannelImpl.nd);
        } else {
            var11_14 = var2_2;
            var2_2 = IOUtil.read(this.fd, this.readBuffer, -1L, UnixAsynchronousSocketChannelImpl.nd);
        }
        if (var2_2 != -2) ** GOTO lbl67
        var11_14 = var2_2;
        var4_7 = this.updateLock;
        var11_14 = var2_2;
        // MONITORENTER : var4_7
        {
            catch (Throwable var3_5) {
                block20 : {
                    try {
                        this.enableReading();
                        var4_7 = var3_5;
                        if (var3_5 instanceof ClosedChannelException) {
                            var4_7 = new AsynchronousCloseException();
                        }
                        var12_15 = var11_14;
                        var3_3 = var4_7;
                    }
                    catch (Throwable var3_6) {
                        if (!(null instanceof AsynchronousCloseException)) {
                            this.lockAndUpdateEvents();
                        }
                        this.end();
                        throw var3_6;
                    }
                    if (var4_7 instanceof AsynchronousCloseException) break block20;
                    var3_3 = var4_7;
                    var2_2 = var11_14;
lbl40: // 2 sources:
                    this.lockAndUpdateEvents();
                    var12_15 = var2_2;
                }
lbl43: // 2 sources:
                this.end();
                if (var9_12 != null) {
                    var9_12.cancel(false);
                }
                var4_7 = var3_3 != null ? var10_13 : (var5_8 != false ? Long.valueOf(var12_15) : Integer.valueOf(var12_15));
                if (var6_9 == null) {
                    var8_11.setResult((Number)var4_7, (Throwable)var3_3);
                    return;
                }
                if (var1_1) {
                    Invoker.invokeUnchecked(var6_9, var7_10, var4_7, (Throwable)var3_3);
                    return;
                }
                Invoker.invokeIndirectly(this, var6_9, var7_10, var4_7, (Throwable)var3_3);
                return;
            }
        }
        this.readPending = true;
        // MONITOREXIT : var4_7
        if (!(null instanceof AsynchronousCloseException)) {
            this.lockAndUpdateEvents();
        }
        this.end();
        return;
        {
            catch (Throwable var3_4) {}
            var11_14 = var2_2;
            throw var3_4;
lbl67: // 1 sources:
            var11_14 = var2_2;
            this.readBuffer = null;
            var11_14 = var2_2;
            this.readBuffers = null;
            var11_14 = var2_2;
            this.readAttachment = null;
            var11_14 = var2_2;
            this.enableReading();
            var12_15 = var2_2;
            if (null instanceof AsynchronousCloseException) ** GOTO lbl43
            var3_3 = var4_7;
            ** GOTO lbl40
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
    private void finishWrite(boolean var1_1) {
        var2_2 = -1;
        var3_3 = null;
        var4_5 /* !! */  = null;
        var5_8 = this.isGatheringWrite;
        var6_9 = this.writeHandler;
        var7_10 = this.writeAttachment;
        var8_11 = this.writeFuture;
        var9_12 = this.writeTimer;
        var10_13 = null;
        var11_14 = var2_2;
        this.begin();
        if (var5_8) {
            var11_14 = var2_2;
            var2_2 = (int)IOUtil.write(this.fd, this.writeBuffers, UnixAsynchronousSocketChannelImpl.nd);
        } else {
            var11_14 = var2_2;
            var2_2 = IOUtil.write(this.fd, this.writeBuffer, -1L, UnixAsynchronousSocketChannelImpl.nd);
        }
        if (var2_2 != -2) ** GOTO lbl68
        var11_14 = var2_2;
        var3_3 = this.updateLock;
        var11_14 = var2_2;
        // MONITORENTER : var3_3
        {
            catch (Throwable var4_7) {
                block20 : {
                    try {
                        this.enableWriting();
                        var3_3 = var4_7;
                        if (var4_7 instanceof ClosedChannelException) {
                            var3_3 = new AsynchronousCloseException();
                        }
                        var4_5 /* !! */  = var3_3;
                        var12_15 = var11_14;
                        var3_3 = var4_5 /* !! */ ;
                    }
                    catch (Throwable var3_4) {
                        if (!(null instanceof AsynchronousCloseException)) {
                            this.lockAndUpdateEvents();
                        }
                        this.end();
                        throw var3_4;
                    }
                    if (var4_5 /* !! */  instanceof AsynchronousCloseException) break block20;
                    var3_3 = var4_5 /* !! */ ;
                    var2_2 = var11_14;
lbl41: // 2 sources:
                    this.lockAndUpdateEvents();
                    var12_15 = var2_2;
                }
lbl44: // 2 sources:
                this.end();
                if (var9_12 != null) {
                    var9_12.cancel(false);
                }
                var4_5 /* !! */  = var3_3 != null ? var10_13 : (var5_8 != false ? Long.valueOf(var12_15) : Integer.valueOf(var12_15));
                if (var6_9 == null) {
                    var8_11.setResult((Number)var4_5 /* !! */ , (Throwable)var3_3);
                    return;
                }
                if (var1_1) {
                    Invoker.invokeUnchecked(var6_9, var7_10, var4_5 /* !! */ , (Throwable)var3_3);
                    return;
                }
                Invoker.invokeIndirectly(this, var6_9, var7_10, var4_5 /* !! */ , (Throwable)var3_3);
                return;
            }
        }
        this.writePending = true;
        // MONITOREXIT : var3_3
        if (!(null instanceof AsynchronousCloseException)) {
            this.lockAndUpdateEvents();
        }
        this.end();
        return;
        {
            catch (Throwable var4_6) {}
            var11_14 = var2_2;
            throw var4_6;
lbl68: // 1 sources:
            var11_14 = var2_2;
            this.writeBuffer = null;
            var11_14 = var2_2;
            this.writeBuffers = null;
            var11_14 = var2_2;
            this.writeAttachment = null;
            var11_14 = var2_2;
            this.enableWriting();
            var12_15 = var2_2;
            if (null instanceof AsynchronousCloseException) ** GOTO lbl44
            var3_3 = var4_5 /* !! */ ;
            ** GOTO lbl41
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void lockAndUpdateEvents() {
        Object object = this.updateLock;
        synchronized (object) {
            this.updateEvents();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void setConnected() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            this.state = 2;
            this.localAddress = Net.localAddress(this.fd);
            this.remoteAddress = (InetSocketAddress)this.pendingRemote;
            return;
        }
    }

    private void updateEvents() {
        int n;
        block6 : {
            int n2;
            block5 : {
                n2 = 0;
                if (this.readPending) {
                    n2 = 0 | Net.POLLIN;
                }
                if (this.connectPending) break block5;
                n = n2;
                if (!this.writePending) break block6;
            }
            n = n2 | Net.POLLOUT;
        }
        if (n != 0) {
            this.port.startPoll(this.fdVal, n);
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    @Override
    public AsynchronousChannelGroupImpl group() {
        return this.port;
    }

    @Override
    void implClose() throws IOException {
        this.guard.close();
        this.port.unregister(this.fdVal);
        nd.close(this.fd);
        this.finish(false, true, true);
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
    <A> Future<Void> implConnect(SocketAddress var1_1, A var2_3, CompletionHandler<Void, ? super A> var3_4) {
        if (!this.isOpen()) {
            var1_1 = new ClosedChannelException();
            if (var3_4 == null) {
                return CompletedFuture.withFailure((Throwable)var1_1);
            }
            Invoker.invoke(this, var3_4, var2_3, null, (Throwable)var1_1);
            return null;
        }
        var4_5 = Net.checkAddress(var1_1);
        var5_6 = System.getSecurityManager();
        if (var5_6 != null) {
            var5_6.checkConnect(var4_5.getAddress().getHostAddress(), var4_5.getPort());
        }
        var5_6 = this.stateLock;
        // MONITORENTER : var5_6
        if (this.state == 2) {
            var1_1 = new PendingFuture<V, A>();
            throw var1_1;
        }
        if (this.state == 1) {
            var1_1 = new PendingFuture<V, A>();
            throw var1_1;
        }
        this.state = 1;
        this.pendingRemote = var1_1;
        var6_9 = this.localAddress == null;
        // MONITOREXIT : var5_6
        var1_1 = null;
        try {
            this.begin();
            if (var6_9) {
                NetHooks.beforeTcpConnect(this.fd, var4_5.getAddress(), var4_5.getPort());
            }
            if (Net.connect(this.fd, var4_5.getAddress(), var4_5.getPort()) != -2) ** GOTO lbl-1000
            var1_1 = null;
            var5_6 = this.updateLock;
            // MONITORENTER : var5_6
            if (var3_4 == null) {
                var1_1 = new PendingFuture<V, A>(this, (Object)OpType.CONNECT);
                this.connectFuture = var1_1;
            } else {
                this.connectHandler = var3_4;
                this.connectAttachment = var2_3;
            }
            this.connectPending = true;
            this.updateEvents();
        }
        catch (Throwable var5_7) {
            block24 : {
                var1_1 = var5_7;
                if (!(var5_7 instanceof ClosedChannelException)) break block24;
                var1_1 = new AsynchronousCloseException();
            }
            if (var1_1 != null) {
                try {
                    this.close();
                }
                catch (Throwable var5_8) {
                    var1_1.addSuppressed(var5_8);
                }
            }
            if (var3_4 == null) {
                return CompletedFuture.withResult(null, (Throwable)var1_1);
            }
            Invoker.invoke(this, var3_4, var2_3, null, (Throwable)var1_1);
            return null;
            finally {
                this.end();
            }
        }
        this.end();
        return var1_1;
lbl-1000: // 1 sources:
        {
            this.setConnected();
            ** GOTO lbl45
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
    <V extends Number, A> Future<V> implRead(boolean var1_1, ByteBuffer var2_2, ByteBuffer[] var3_11, long var4_14, TimeUnit var6_15, A var7_16, CompletionHandler<V, ? super A> var8_17) {
        block37 : {
            block34 : {
                block38 : {
                    block36 : {
                        block35 : {
                            block32 : {
                                block33 : {
                                    var9_29 = 0;
                                    if (!UnixAsynchronousSocketChannelImpl.disableSynchronousRead) {
                                        if (var8_28 == null) {
                                            var9_29 = 1;
                                            var10_30 = false;
                                            var11_31 = null;
                                        } else {
                                            var11_31 = Invoker.getGroupAndInvokeCount();
                                            var10_30 = Invoker.mayInvokeDirect((Invoker.GroupAndInvokeCount)var11_31, this.port);
                                            var9_29 = !var10_30 && this.port.isFixedThreadPool() ? 0 : 1;
                                        }
                                    } else {
                                        var10_30 = false;
                                        var11_31 = null;
                                    }
                                    var12_32 = -2;
                                    this.begin();
                                    if (var9_29 == 0) break block32;
                                    if (!var1_1) break block33;
                                    var13_33 = this.fd;
                                    var14_34 = UnixAsynchronousSocketChannelImpl.nd;
                                    try {
                                        var15_35 = IOUtil.read((FileDescriptor)var13_33, var3_11, (NativeDispatcher)var14_34);
                                    }
                                    catch (Throwable var2_3) {
                                        var9_29 = -2;
                                        break block34;
                                    }
                                    var9_29 = (int)var15_35;
                                    break block35;
                                    catch (Throwable var2_4) {
                                        var9_29 = var12_32;
                                    }
                                    break block34;
                                }
                                var13_33 = this.fd;
                                var14_34 = UnixAsynchronousSocketChannelImpl.nd;
                                try {
                                    var9_29 = IOUtil.read((FileDescriptor)var13_33, (ByteBuffer)var2_2, -1L, (NativeDispatcher)var14_34);
                                    break block35;
                                }
                                catch (Throwable var2_5) {
                                    var9_29 = -2;
                                    break block34;
                                }
                                catch (Throwable var2_6) {
                                    var9_29 = -2;
                                }
                                break block34;
                            }
                            var9_29 = var12_32;
                        }
                        if (var9_29 != -2) break block38;
                        var14_34 = null;
                        try {
                            var13_33 = this.updateLock;
                            // MONITORENTER : var13_33
                        }
                        catch (Throwable var2_8) {}
                        try {
                            this.isScatteringRead = var1_1;
                            this.readBuffer = var2_2;
                            this.readBuffers = var3_11;
                            if (var8_28 == null) {
                                this.readHandler = null;
                                var3_12 = new PendingFuture<V, A>(this, (Object)OpType.READ);
                                this.readFuture = var3_12;
                                this.readAttachment = null;
                            } else {
                                this.readHandler = var8_28;
                                this.readAttachment = var7_27;
                                this.readFuture = null;
                                var3_13 = var14_34;
                            }
                            if (var4_25 <= 0L) ** GOTO lbl77
                            var17_36 = this.port;
                            var14_34 = this.readTimeoutTask;
                            var2_2 = var11_31;
                            var18_37 = var10_30;
                            var12_32 = var9_29;
                        }
                        catch (Throwable var3_15) {}
                        this.readTimer = var17_36.schedule((Runnable)var14_34, (long)var4_25, (TimeUnit)var6_26);
lbl77: // 2 sources:
                        var2_2 = var11_31;
                        var18_37 = var10_30;
                        var12_32 = var9_29;
                        this.readPending = true;
                        var2_2 = var11_31;
                        var18_37 = var10_30;
                        var12_32 = var9_29;
                        this.updateEvents();
                        var2_2 = var11_31;
                        var18_37 = var10_30;
                        var12_32 = var9_29;
                        // MONITOREXIT : var13_33
                        if (true) break block36;
                        this.enableReading();
                    }
                    this.end();
                    return var3_14;
                    ** GOTO lbl99
                    {
                        catch (Throwable var3_17) {
                            var11_31 = var2_2;
                            var10_30 = var18_37;
                            var9_29 = var12_32;
                        }
lbl99: // 2 sources:
                        var2_2 = var11_31;
                        var18_37 = var10_30;
                        var12_32 = var9_29;
                        try {
                            throw var3_16;
                        }
                        catch (Throwable var2_7) {
                            break block34;
                        }
                    }
                    break block34;
                }
                if (!false) {
                    this.enableReading();
                }
                this.end();
                var3_18 = null;
                break block37;
                catch (Throwable var2_9) {
                    var9_29 = var12_32;
                }
            }
            var3_20 = var2_2;
            if (!(var2_2 instanceof ClosedChannelException)) break block37;
            var3_21 = new AsynchronousCloseException();
        }
        var2_2 = var3_23 != null ? null : (var1_1 != false ? Long.valueOf(var9_29) : Integer.valueOf(var9_29));
        if (var8_28 == null) return CompletedFuture.withResult(var2_2, (Throwable)var3_23);
        if (var10_30) {
            Invoker.invokeDirect((Invoker.GroupAndInvokeCount)var11_31, var8_28, var7_27, var2_2, (Throwable)var3_23);
            return null;
        }
        Invoker.invokeIndirectly(this, var8_28, var7_27, var2_2, (Throwable)var3_23);
        return null;
        finally {
            if (!false) {
                this.enableReading();
            }
            this.end();
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
    <V extends Number, A> Future<V> implWrite(boolean var1_1, ByteBuffer var2_2, ByteBuffer[] var3_11, long var4_12, TimeUnit var6_13, A var7_14, CompletionHandler<V, ? super A> var8_15) {
        block31 : {
            block33 : {
                block29 : {
                    block32 : {
                        block30 : {
                            block28 : {
                                block27 : {
                                    var9_38 = Invoker.getGroupAndInvokeCount();
                                    var10_39 = Invoker.mayInvokeDirect(var9_38, this.port);
                                    var11_40 = var8_37 != null && !var10_39 && this.port.isFixedThreadPool() ? 0 : 1;
                                    var12_41 = -2;
                                    var13_42 = null;
                                    var14_43 = null;
                                    this.begin();
                                    if (var11_40 == 0) break block27;
                                    if (!var1_1) ** GOTO lbl20
                                    var15_44 = this.fd;
                                    var16_45 = UnixAsynchronousSocketChannelImpl.nd;
                                    try {
                                        var11_40 = (int)IOUtil.write(var15_44, (ByteBuffer[])var3_23, var16_45);
                                        break block28;
                                        catch (Throwable var2_3) {
                                            var11_40 = var12_41;
                                        }
                                        break block29;
lbl20: // 1 sources:
                                        var11_40 = IOUtil.write(this.fd, (ByteBuffer)var2_2 /* !! */ , -1L, UnixAsynchronousSocketChannelImpl.nd);
                                        break block28;
                                    }
                                    catch (Throwable var2_4) {
                                        var11_40 = -2;
                                        break block29;
                                    }
                                }
                                var11_40 = var12_41;
                            }
                            if (var11_40 != -2) break block32;
                            var14_43 = null;
                            try {
                                var13_42 = this.updateLock;
                                // MONITORENTER : var13_42
                            }
                            catch (Throwable var2_12) {}
                            try {
                                this.isGatheringWrite = var1_1;
                                this.writeBuffer = var2_2 /* !! */ ;
                                this.writeBuffers = var3_23;
                                if (var8_37 == null) {
                                    this.writeHandler = null;
                                    var2_5 = new PendingFuture<V, A>(this, (Object)OpType.WRITE);
                                    this.writeFuture = var2_5;
                                    this.writeAttachment = null;
                                } else {
                                    this.writeHandler = var8_37;
                                    this.writeAttachment = var7_36;
                                    this.writeFuture = null;
                                    var2_6 = var14_43;
                                }
                                if (var4_34 <= 0L) ** GOTO lbl55
                                var14_43 = this.port;
                                var3_24 = this.writeTimeoutTask;
                                var12_41 = var11_40;
                            }
                            catch (Throwable var2_8) {}
                            this.writeTimer = var14_43.schedule(var3_24, (long)var4_34, (TimeUnit)var6_35);
lbl55: // 2 sources:
                            var12_41 = var11_40;
                            this.writePending = true;
                            var12_41 = var11_40;
                            this.updateEvents();
                            var12_41 = var11_40;
                            // MONITOREXIT : var13_42
                            if (true) break block30;
                            this.enableWriting();
                        }
                        this.end();
                        return var2_7;
                        ** GOTO lbl69
                        {
                            catch (Throwable var2_11) {
                                var11_40 = var12_41;
                            }
lbl69: // 2 sources:
                            var12_41 = var11_40;
                            try {
                                throw var2_9;
                            }
                            catch (Throwable var2_10) {
                                break block29;
                            }
                        }
                        break block29;
                    }
                    var12_41 = var11_40;
                    var2_13 = var13_42;
                    if (false) break block31;
                    var2_14 = var14_43;
                    break block33;
                    catch (Throwable var2_17) {
                        var11_40 = var12_41;
                    }
                }
                var3_27 = var2_18;
                try {
                    if (var2_18 instanceof ClosedChannelException) {
                        var3_28 = new AsynchronousCloseException();
                    }
                    var12_41 = var11_40;
                    var2_19 = var3_29;
                    if (false) break block31;
                    var2_20 = var3_29;
                }
                catch (Throwable var2_21) {
                    if (!false) {
                        this.enableWriting();
                    }
                    this.end();
                    throw var2_21;
                }
            }
            this.enableWriting();
            var12_41 = var11_40;
        }
        this.end();
        if (var2_16 != null) {
            var3_30 = null;
        } else if (var1_1) {
            var3_31 = var12_41;
        } else {
            var3_32 = var12_41;
        }
        if (var8_37 == null) return CompletedFuture.withResult(var3_33, (Throwable)var2_16);
        if (var10_39) {
            Invoker.invokeDirect(var9_38, var8_37, var7_36, var3_33, (Throwable)var2_16);
            return null;
        }
        Invoker.invokeIndirectly(this, var8_37, var7_36, var3_33, (Throwable)var2_16);
        return null;
    }

    @Override
    public void onCancel(PendingFuture<?, ?> pendingFuture) {
        if (pendingFuture.getContext() == OpType.CONNECT) {
            this.killConnect();
        }
        if (pendingFuture.getContext() == OpType.READ) {
            this.killReading();
        }
        if (pendingFuture.getContext() == OpType.WRITE) {
            this.killWriting();
        }
    }

    @Override
    public void onEvent(int n, boolean bl) {
        short s = Net.POLLIN;
        boolean bl2 = true;
        boolean bl3 = (s & n) > 0;
        if ((Net.POLLOUT & n) <= 0) {
            bl2 = false;
        }
        if (((Net.POLLERR | Net.POLLHUP) & n) > 0) {
            bl3 = true;
            bl2 = true;
        }
        this.finish(bl, bl3, bl2);
    }

    private static enum OpType {
        CONNECT,
        READ,
        WRITE;
        
    }

}

