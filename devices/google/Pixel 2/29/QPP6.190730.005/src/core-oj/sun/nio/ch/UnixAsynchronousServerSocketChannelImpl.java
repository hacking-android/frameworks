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
import java.nio.channels.AcceptPendingException;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.NotYetBoundException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.nio.ch.AsynchronousServerSocketChannelImpl;
import sun.nio.ch.CompletedFuture;
import sun.nio.ch.IOUtil;
import sun.nio.ch.Invoker;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.Net;
import sun.nio.ch.PendingFuture;
import sun.nio.ch.Port;
import sun.nio.ch.SocketDispatcher;
import sun.nio.ch.UnixAsynchronousSocketChannelImpl;

class UnixAsynchronousServerSocketChannelImpl
extends AsynchronousServerSocketChannelImpl
implements Port.PollableChannel {
    private static final NativeDispatcher nd = new SocketDispatcher();
    private AccessControlContext acceptAcc;
    private Object acceptAttachment;
    private PendingFuture<AsynchronousSocketChannel, Object> acceptFuture;
    private CompletionHandler<AsynchronousSocketChannel, Object> acceptHandler;
    private boolean acceptPending;
    private final AtomicBoolean accepting = new AtomicBoolean();
    private final int fdVal;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private final Port port;
    private final Object updateLock = new Object();

    static {
        UnixAsynchronousServerSocketChannelImpl.initIDs();
    }

    UnixAsynchronousServerSocketChannelImpl(Port port) throws IOException {
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

    private int accept(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, InetSocketAddress[] arrinetSocketAddress) throws IOException {
        return this.accept0(fileDescriptor, fileDescriptor2, arrinetSocketAddress);
    }

    private native int accept0(FileDescriptor var1, FileDescriptor var2, InetSocketAddress[] var3) throws IOException;

    private void enableAccept() {
        this.accepting.set(false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private AsynchronousSocketChannel finishAccept(FileDescriptor var1_1, final InetSocketAddress var2_3, AccessControlContext var3_6) throws IOException, SecurityException {
        try {
            var4_7 = new UnixAsynchronousSocketChannelImpl(this.port, (FileDescriptor)var1_1, var2_3);
            if (var3_6 == null) ** GOTO lbl12
        }
        catch (IOException var2_5) {
            UnixAsynchronousServerSocketChannelImpl.nd.close((FileDescriptor)var1_1);
            throw var2_5;
        }
        try {
            var1_1 = new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkAccept(var2_3.getAddress().getHostAddress(), var2_3.getPort());
                    }
                    return null;
                }
            };
            AccessController.doPrivileged(var1_1, var3_6);
            return var4_7;
lbl12: // 1 sources:
            var1_1 = System.getSecurityManager();
            if (var1_1 == null) return var4_7;
            var1_1.checkAccept(var2_3.getAddress().getHostAddress(), var2_3.getPort());
            return var4_7;
        }
        catch (SecurityException var1_2) {
            try {
                var4_7.close();
                throw var1_2;
            }
            catch (Throwable var2_4) {
                var1_2.addSuppressed(var2_4);
            }
            throw var1_2;
        }
    }

    private static native void initIDs();

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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    Future<AsynchronousSocketChannel> implAccept(Object var1_1, CompletionHandler<AsynchronousSocketChannel, Object> var2_3) {
        block17 : {
            if (!this.isOpen()) {
                var3_4 = new ClosedChannelException();
                if (var2_3 == null) {
                    return CompletedFuture.withFailure(var3_4);
                }
                Invoker.invoke(this, var2_3, var1_1, null, var3_4);
                return null;
            }
            if (this.localAddress == null) throw new NotYetBoundException();
            if (this.isAcceptKilled() != false) throw new RuntimeException("Accept not allowed due cancellation");
            if (this.accepting.compareAndSet(false, true) == false) throw new AcceptPendingException();
            var4_6 = new FileDescriptor();
            var5_7 = new InetSocketAddress[1];
            var3_5 = null;
            try {
                this.begin();
                if (this.accept(this.fd, var4_6, var5_7) != -2) ** GOTO lbl31
                var3_5 = null;
                var6_8 = this.updateLock;
                // MONITORENTER : var6_8
                if (var2_3 == null) {
                    this.acceptHandler = null;
                    var3_5 = new PendingFuture<V, A>(this);
                    this.acceptFuture = var3_5;
                    break block17;
                }
                this.acceptHandler = var2_3;
                this.acceptAttachment = var1_1;
            }
            catch (Throwable var7_10) {
                block18 : {
                    var3_5 = var7_10;
                    if (!(var7_10 instanceof ClosedChannelException)) break block18;
                    var3_5 = new AsynchronousCloseException();
                }
                var6_8 = var8_13 = null;
                var7_11 = var3_5;
                if (var3_5 == null) {
                    try {
                        var6_8 = this.finishAccept(var4_6, var5_7[0], null);
                        var7_11 = var3_5;
                    }
                    catch (Throwable var7_12) {
                        var6_8 = var8_13;
                    }
                }
                this.enableAccept();
                if (var2_3 == null) {
                    return CompletedFuture.withResult(var6_8, (Throwable)var7_11);
                }
                Invoker.invokeIndirectly(this, var2_3, var1_1, var6_8, (Throwable)var7_11);
                return null;
                finally {
                    this.end();
                }
            }
        }
        var7_9 = System.getSecurityManager() == null ? null : AccessController.getContext();
        this.acceptAcc = var7_9;
        this.acceptPending = true;
        // MONITOREXIT : var6_8
        this.port.startPoll(this.fdVal, Net.POLLIN);
        this.end();
        return var3_5;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void implClose() throws IOException {
        CompletionHandler<AsynchronousSocketChannel, Object> completionHandler;
        PendingFuture<AsynchronousSocketChannel, Object> pendingFuture;
        Object object;
        this.guard.close();
        this.port.unregister(this.fdVal);
        nd.close(this.fd);
        Object object2 = this.updateLock;
        synchronized (object2) {
            if (!this.acceptPending) {
                return;
            }
            this.acceptPending = false;
            completionHandler = this.acceptHandler;
            object = this.acceptAttachment;
            pendingFuture = this.acceptFuture;
        }
        object2 = new AsynchronousCloseException();
        ((Throwable)object2).setStackTrace(new StackTraceElement[0]);
        if (completionHandler == null) {
            pendingFuture.setFailure((Throwable)object2);
            return;
        }
        Invoker.invokeIndirectly(this, completionHandler, object, null, (Throwable)object2);
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
    public void onEvent(int var1_1, boolean var2_2) {
        var3_3 = this.updateLock;
        // MONITORENTER : var3_3
        if (!this.acceptPending) {
            // MONITOREXIT : var3_3
            return;
        }
        this.acceptPending = false;
        // MONITOREXIT : var3_3
        var4_6 = new FileDescriptor();
        var5_7 = new InetSocketAddress[1];
        var3_3 = null;
        try {
            this.begin();
            if (this.accept(this.fd, var4_6, (InetSocketAddress[])var5_7) != -2) ** GOTO lbl22
            var3_3 = this.updateLock;
            // MONITORENTER : var3_3
            this.acceptPending = true;
        }
        catch (Throwable var6_8) {
            block22 : {
                var3_3 = var6_8;
                if (!(var6_8 instanceof ClosedChannelException)) break block22;
                var3_3 = new AsynchronousCloseException();
            }
            var8_12 = var7_11 = null;
            var6_9 = var3_3;
            if (var3_3 == null) {
                try {
                    var8_12 = this.finishAccept(var4_6, var5_7[0], this.acceptAcc);
                    var6_9 = var3_3;
                }
                catch (Throwable var6_10) {
                    var3_3 = var6_10;
                    if (!(var6_10 instanceof IOException)) {
                        var3_3 = var6_10;
                        if (!(var6_10 instanceof SecurityException)) {
                            var3_3 = new IOException(var6_10);
                        }
                    }
                    var6_9 = var3_3;
                    var8_12 = var7_11;
                }
            }
            var3_3 = this.acceptHandler;
            var7_11 = this.acceptAttachment;
            var5_7 = this.acceptFuture;
            this.enableAccept();
            if (var3_3 != null) {
                Invoker.invoke(this, var3_3, var7_11, var8_12, (Throwable)var6_9);
                return;
            }
            var5_7.setResult(var8_12, (Throwable)var6_9);
            if (var8_12 == null) return;
            if (var5_7.isCancelled() == false) return;
            try {
                var8_12.close();
                return;
            }
            catch (IOException var3_4) {
                return;
            }
            finally {
                this.end();
            }
        }
        this.port.startPoll(this.fdVal, Net.POLLIN);
        this.end();
    }

}

