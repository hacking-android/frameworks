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
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NetworkChannel;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jdk.net.ExtendedSocketOptions;
import jdk.net.SocketFlow;
import sun.net.ExtendedOptionsImpl;
import sun.net.NetHooks;
import sun.nio.ch.ExtendedSocketOption;
import sun.nio.ch.IOStatus;
import sun.nio.ch.IOUtil;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.NativeThread;
import sun.nio.ch.Net;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketDispatcher;

class SocketChannelImpl
extends SocketChannel
implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CONNECTED = 2;
    private static final int ST_KILLED = 4;
    private static final int ST_KILLPENDING = 3;
    private static final int ST_PENDING = 1;
    private static final int ST_UNCONNECTED = 0;
    private static final int ST_UNINITIALIZED = -1;
    private static NativeDispatcher nd = new SocketDispatcher();
    private final FileDescriptor fd;
    private final int fdVal;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private boolean isInputOpen = true;
    private boolean isOutputOpen = true;
    private boolean isReuseAddress;
    private InetSocketAddress localAddress;
    private final Object readLock = new Object();
    private volatile long readerThread = 0L;
    private boolean readyToConnect = false;
    private InetSocketAddress remoteAddress;
    private Socket socket;
    private int state = -1;
    private final Object stateLock = new Object();
    private final Object writeLock = new Object();
    private volatile long writerThread = 0L;

    SocketChannelImpl(SelectorProvider object) throws IOException {
        super((SelectorProvider)object);
        this.fd = Net.socket(true);
        this.fdVal = IOUtil.fdVal(this.fd);
        this.state = 0;
        object = this.fd;
        if (object != null && ((FileDescriptor)object).valid()) {
            this.guard.open("close");
        }
    }

    SocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws IOException {
        super(selectorProvider);
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        this.state = 2;
        this.localAddress = Net.localAddress(fileDescriptor);
        this.remoteAddress = inetSocketAddress;
        if (fileDescriptor != null && fileDescriptor.valid()) {
            this.guard.open("close");
        }
    }

    SocketChannelImpl(SelectorProvider selectorProvider, FileDescriptor fileDescriptor, boolean bl) throws IOException {
        super(selectorProvider);
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        this.state = 0;
        if (fileDescriptor != null && fileDescriptor.valid()) {
            this.guard.open("close");
        }
        if (bl) {
            this.localAddress = Net.localAddress(fileDescriptor);
        }
    }

    private static native int checkConnect(FileDescriptor var0, boolean var1, boolean var2) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean ensureReadOpen() throws ClosedChannelException {
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (!this.isConnected()) {
                NotYetConnectedException notYetConnectedException = new NotYetConnectedException();
                throw notYetConnectedException;
            }
            return this.isInputOpen;
            {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void ensureWriteOpen() throws ClosedChannelException {
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (!this.isOutputOpen) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (this.isConnected()) {
                return;
            }
            NotYetConnectedException notYetConnectedException = new NotYetConnectedException();
            throw notYetConnectedException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readerCleanup() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            this.readerThread = 0L;
            if (this.state == 3) {
                this.kill();
            }
            return;
        }
    }

    private static native int sendOutOfBandData(FileDescriptor var0, byte var1) throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writerCleanup() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            this.writerThread = 0L;
            if (this.state == 3) {
                this.kill();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SocketChannel bind(SocketAddress serializable) throws IOException {
        Object object = this.readLock;
        synchronized (object) {
            Object object2 = this.writeLock;
            synchronized (object2) {
                Object object3 = this.stateLock;
                synchronized (object3) {
                    if (!this.isOpen()) {
                        serializable = new ClosedChannelException();
                        throw serializable;
                    }
                    if (this.state == 1) {
                        serializable = new ConnectionPendingException();
                        throw serializable;
                    }
                    if (this.localAddress != null) {
                        serializable = new AlreadyBoundException();
                        throw serializable;
                    }
                    serializable = serializable == null ? new InetSocketAddress(0) : Net.checkAddress((SocketAddress)serializable);
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkListen(((InetSocketAddress)serializable).getPort());
                    }
                    NetHooks.beforeTcpBind(this.fd, ((InetSocketAddress)serializable).getAddress(), ((InetSocketAddress)serializable).getPort());
                    Net.bind(this.fd, ((InetSocketAddress)serializable).getAddress(), ((InetSocketAddress)serializable).getPort());
                    this.localAddress = Net.localAddress(this.fd);
                    return this;
                }
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
    @Override
    public boolean connect(SocketAddress var1_1) throws IOException {
        block35 : {
            var2_5 = this.readLock;
            // MONITORENTER : var2_5
            var3_6 = this.writeLock;
            // MONITORENTER : var3_6
            this.ensureOpenAndUnconnected();
            var4_7 = Net.checkAddress((SocketAddress)var1_1);
            var1_1 = System.getSecurityManager();
            if (var1_1 != null) {
                var1_1.checkConnect(var4_7.getAddress().getHostAddress(), var4_7.getPort());
            }
            var5_8 = this.blockingLock();
            // MONITORENTER : var5_8
            var6_9 = 0;
            var7_10 = 0;
            var8_11 = false;
            var9_12 = true;
            var10_13 = var6_9;
            this.begin();
            var10_13 = var6_9;
            var1_1 = this.stateLock;
            var10_13 = var6_9;
            // MONITORENTER : var1_1
            if (this.isOpen()) break block35;
            // MONITOREXIT : var1_1
            this.readerCleanup();
            var8_11 = var9_12;
            if (0 <= 0) {
                var8_11 = -2 == 0 ? var9_12 : false;
            }
            this.end(var8_11);
            // MONITOREXIT : var5_8
            // MONITOREXIT : var3_6
            // MONITOREXIT : var2_5
            return false;
        }
        if (this.localAddress == null) {
            NetHooks.beforeTcpConnect(this.fd, var4_7.getAddress(), var4_7.getPort());
        }
        this.readerThread = NativeThread.current();
        // MONITOREXIT : var1_1
        ** GOTO lbl47
        {
            block36 : {
                catch (Throwable var11_15) {}
                var10_13 = var6_9;
                throw var11_15;
lbl47: // 1 sources:
                do {
                    var10_13 = var7_10;
                    var11_14 = var4_7.getAddress();
                    var1_1 = var11_14;
                    var10_13 = var7_10;
                    if (var11_14.isAnyLocalAddress()) {
                        var10_13 = var7_10;
                        var1_1 = InetAddress.getLocalHost();
                    }
                    var10_13 = var7_10;
                    var7_10 = Net.connect(this.fd, (InetAddress)var1_1, var4_7.getPort());
                    if (var7_10 != -3) break;
                    var10_13 = var7_10;
                    if (var9_12 = this.isOpen()) continue;
                    break;
                } while (true);
                this.readerCleanup();
                var8_11 = var7_10 > 0 || var7_10 == -2;
                this.end(var8_11);
                var1_1 = this.stateLock;
                this.remoteAddress = var4_7;
                if (var7_10 <= 0) break block36;
                this.state = 2;
                {
                    catch (Throwable var1_3) {
                        throw var1_3;
                    }
                }
                if (this.isOpen()) {
                    this.localAddress = Net.localAddress(this.fd);
                }
                // MONITOREXIT : var1_1
                // MONITOREXIT : var5_8
                // MONITOREXIT : var3_6
                // MONITOREXIT : var2_5
                return true;
            }
            if (!this.isBlocking()) {
                this.state = 1;
                if (this.isOpen()) {
                    this.localAddress = Net.localAddress(this.fd);
                }
            }
            // MONITOREXIT : var1_1
            // MONITOREXIT : var5_8
            // MONITOREXIT : var3_6
            // MONITOREXIT : var2_5
            return false;
            catch (Throwable var1_2) {
                this.readerCleanup();
                if (var10_13 > 0 || var10_13 == -2) {
                    var8_11 = true;
                }
                this.end(var8_11);
                throw var1_2;
                catch (IOException var1_4) {
                    this.close();
                    throw var1_4;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void ensureOpenAndUnconnected() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (this.state == 2) {
                AlreadyConnectedException alreadyConnectedException = new AlreadyConnectedException();
                throw alreadyConnectedException;
            }
            if (this.state != 1) {
                return;
            }
            ConnectionPendingException connectionPendingException = new ConnectionPendingException();
            throw connectionPendingException;
        }
    }

    protected void finalize() throws IOException {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        if (this.fd != null) {
            this.close();
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean finishConnect() throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Last catch has completely empty body
        // org.benf.cfr.reader.bytecode.analysis.parse.utils.finalhelp.FinalAnalyzer.identifyFinally(FinalAnalyzer.java:285)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.FinallyRewriter.identifyFinally(FinallyRewriter.java:40)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
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
            if (this.isOpen()) {
                return Net.getRevealedLocalAddress(this.localAddress);
            }
            ClosedChannelException closedChannelException = new ClosedChannelException();
            throw closedChannelException;
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
            if (object != StandardSocketOptions.IP_TOS) {
                object = Net.getSocketOption(this.fd, Net.UNSPEC, object);
                return (T)object;
            }
            StandardProtocolFamily standardProtocolFamily = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
            object = Net.getSocketOption(this.fd, standardProtocolFamily, object);
            return (T)object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.isOpen()) {
                return this.remoteAddress;
            }
            ClosedChannelException closedChannelException = new ClosedChannelException();
            throw closedChannelException;
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
            this.isInputOpen = false;
            this.isOutputOpen = false;
            if (this.state != 4) {
                this.guard.close();
                nd.preClose(this.fd);
            }
            if (this.readerThread != 0L) {
                NativeThread.signal(this.readerThread);
            }
            if (this.writerThread != 0L) {
                NativeThread.signal(this.writerThread);
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
    public boolean isConnected() {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.state != 2) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnectionPending() {
        Object object = this.stateLock;
        synchronized (object) {
            int n = this.state;
            boolean bl = true;
            if (n != 1) return false;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isInputOpen() {
        Object object = this.stateLock;
        synchronized (object) {
            return this.isInputOpen;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isOutputOpen() {
        Object object = this.stateLock;
        synchronized (object) {
            return this.isOutputOpen;
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
            if (this.state == 4) {
                return;
            }
            if (this.state == -1) {
                this.state = 4;
                return;
            }
            if (this.readerThread == 0L && this.writerThread == 0L) {
                nd.close(this.fd);
                this.state = 4;
            } else {
                this.state = 3;
            }
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
        Object object = this.readLock;
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
            this.readerCleanup();
            bl3 = 0 > 0 ? bl2 : false;
            this.end(bl3);
            throw throwable;
        }
        if (!this.isOpen()) {
            // MONITOREXIT : object2
            this.readerCleanup();
            if (0 <= 0) {
                bl3 = false;
            }
            this.end(bl3);
            // MONITOREXIT : object
            return 0;
        }
        this.readerThread = NativeThread.current();
        // MONITOREXIT : object2
        n = Net.poll(this.fd, n, l);
        this.readerCleanup();
        bl3 = n > 0 ? bl : false;
        this.end(bl3);
        // MONITOREXIT : object
        return n;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public int read(ByteBuffer var1_1) throws IOException {
        block34 : {
            if (var1_1 == null) throw new NullPointerException();
            var2_8 = this.readLock;
            // MONITORENTER : var2_8
            if (!this.ensureReadOpen()) {
                // MONITOREXIT : var2_8
                return -1;
            }
            var3_9 = 0;
            var4_10 = 0;
            var5_11 = true;
            var6_12 = true;
            var7_13 = true;
            var8_14 = var3_9;
            this.begin();
            var8_14 = var3_9;
            var9_15 = this.stateLock;
            var8_14 = var3_9;
            // MONITORENTER : var9_15
            if (this.isOpen()) break block34;
            // MONITOREXIT : var9_15
            this.readerCleanup();
            var10_16 = var7_13;
            if (0 <= 0) {
                var10_16 = -2 == 0 ? var7_13 : false;
            }
            this.end(var10_16);
            var9_15 = this.stateLock;
            // MONITORENTER : var9_15
            if (0 <= 0 && !this.isInputOpen) {
                // MONITOREXIT : var9_15
                // MONITOREXIT : var2_8
                return -1;
            }
            // MONITOREXIT : var9_15
            // MONITOREXIT : var2_8
            return 0;
        }
        this.readerThread = NativeThread.current();
        // MONITOREXIT : var9_15
        var8_14 = var4_10;
        while ((var4_10 = IOUtil.read(this.fd, var1_1, -1L, SocketChannelImpl.nd)) == -3) {
            var8_14 = var4_10;
            if (!this.isOpen()) break;
            var8_14 = var4_10;
        }
        var8_14 = var4_10;
        var3_9 = IOStatus.normalize(var4_10);
        {
            catch (Throwable var1_5) {
                this.readerCleanup();
                var10_18 = var6_12;
                if (var8_14 <= 0) {
                    var10_18 = var8_14 == -2 ? var6_12 : false;
                }
                this.end(var10_18);
                var9_15 = this.stateLock;
                // MONITORENTER : var9_15
                if (var8_14 > 0) throw var1_5;
                if (!this.isInputOpen) {
                    // MONITOREXIT : var9_15
                    // MONITOREXIT : var2_8
                    return -1;
                }
                // MONITOREXIT : var9_15
                {
                    catch (Throwable var1_6) {}
                    {
                        // MONITOREXIT : var9_15
                        throw var1_2;
                    }
                }
                throw var1_5;
            }
        }
        this.readerCleanup();
        var10_17 = var5_11;
        if (var4_10 <= 0) {
            var10_17 = var4_10 == -2 ? var5_11 : false;
        }
        this.end(var10_17);
        var9_15 = this.stateLock;
        // MONITORENTER : var9_15
        if (var4_10 > 0) ** GOTO lbl81
        try {
            if (!this.isInputOpen) {
                // MONITOREXIT : var9_15
                // MONITOREXIT : var2_8
                return -1;
            }
lbl81: // 3 sources:
            // MONITOREXIT : var9_15
            // MONITOREXIT : var2_8
            return var3_9;
        }
        catch (Throwable var1_3) {
            // MONITOREXIT : var9_15
            throw var1_2;
        }
        {
            catch (Throwable var1_4) {}
            var8_14 = var3_9;
            throw var1_4;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public long read(ByteBuffer[] var1_1, int var2_8, int var3_9) throws IOException {
        block34 : {
            if (var2_8 < 0) throw new IndexOutOfBoundsException();
            if (var3_9 < 0) throw new IndexOutOfBoundsException();
            if (var2_8 > var1_1.length - var3_9) throw new IndexOutOfBoundsException();
            var4_10 = this.readLock;
            // MONITORENTER : var4_10
            if (!this.ensureReadOpen()) {
                // MONITOREXIT : var4_10
                return -1L;
            }
            var5_11 = 0L;
            var7_12 = true;
            var8_13 = true;
            var9_14 = true;
            var10_15 = var5_11;
            this.begin();
            var10_15 = var5_11;
            var12_16 = this.stateLock;
            var10_15 = var5_11;
            // MONITORENTER : var12_16
            if (this.isOpen()) break block34;
            // MONITOREXIT : var12_16
            this.readerCleanup();
            var13_17 = var9_14;
            if (0L <= 0L) {
                var13_17 = 0L == -2L ? var9_14 : false;
            }
            this.end(var13_17);
            var12_16 = this.stateLock;
            // MONITORENTER : var12_16
            if (0L <= 0L && !this.isInputOpen) {
                // MONITOREXIT : var12_16
                // MONITOREXIT : var4_10
                return -1L;
            }
            // MONITOREXIT : var12_16
            // MONITOREXIT : var4_10
            return 0L;
        }
        this.readerThread = NativeThread.current();
        // MONITOREXIT : var12_16
        var10_15 = var5_11;
        while ((var5_11 = IOUtil.read(this.fd, var1_1, var2_8, var3_9, SocketChannelImpl.nd)) == -3L) {
            var10_15 = var5_11;
            if (!this.isOpen()) break;
            var10_15 = var5_11;
        }
        var10_15 = var5_11;
        var14_20 = IOStatus.normalize(var5_11);
        {
            catch (Throwable var1_5) {
                this.readerCleanup();
                var13_19 = var8_13;
                if (var10_15 <= 0L) {
                    var13_19 = var10_15 == -2L ? var8_13 : false;
                }
                this.end(var13_19);
                var12_16 = this.stateLock;
                // MONITORENTER : var12_16
                if (var10_15 > 0L) throw var1_5;
                if (!this.isInputOpen) {
                    // MONITOREXIT : var12_16
                    return -1L;
                }
                // MONITOREXIT : var12_16
                {
                    catch (Throwable var1_6) {}
                    {
                        // MONITOREXIT : var12_16
                        throw var1_2;
                    }
                }
                throw var1_5;
            }
        }
        this.readerCleanup();
        var13_18 = var7_12;
        if (var5_11 <= 0L) {
            var13_18 = var5_11 == -2L ? var7_12 : false;
        }
        this.end(var13_18);
        var12_16 = this.stateLock;
        // MONITORENTER : var12_16
        if (var5_11 > 0L) ** GOTO lbl81
        try {
            if (!this.isInputOpen) {
                // MONITOREXIT : var12_16
                // MONITOREXIT : var4_10
                return -1L;
            }
lbl81: // 3 sources:
            // MONITOREXIT : var12_16
            // MONITOREXIT : var4_10
            return var14_20;
        }
        catch (Throwable var1_3) {
            // MONITOREXIT : var12_16
            throw var1_2;
        }
        {
            catch (Throwable var1_4) {}
            var10_15 = var5_11;
            throw var1_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SocketAddress remoteAddress() {
        Object object = this.stateLock;
        synchronized (object) {
            return this.remoteAddress;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    int sendOutOfBandData(byte var1_1) throws IOException {
        block34 : {
            var2_2 = this.writeLock;
            // MONITORENTER : var2_2
            this.ensureWriteOpen();
            var3_3 = 0;
            var4_4 = 0;
            var5_5 = true;
            var6_6 = true;
            var7_7 = true;
            var8_8 = var3_3;
            this.begin();
            var8_8 = var3_3;
            var9_9 = this.stateLock;
            var8_8 = var3_3;
            // MONITORENTER : var9_9
            if (this.isOpen()) break block34;
            // MONITOREXIT : var9_9
            this.writerCleanup();
            var10_16 = var7_7;
            if (0 <= 0) {
                var10_16 = -2 == 0 ? var7_7 : false;
            }
            this.end(var10_16);
            var11_19 = this.stateLock;
            // MONITORENTER : var11_19
            if (0 > 0) {
                // MONITOREXIT : var11_19
                // MONITOREXIT : var2_2
                return 0;
            }
            if (this.isOutputOpen) {
                return 0;
            }
            var9_9 = new AsynchronousCloseException();
            throw var9_9;
        }
        this.writerThread = NativeThread.current();
        // MONITOREXIT : var9_9
        var8_8 = var4_4;
        while ((var4_4 = SocketChannelImpl.sendOutOfBandData(this.fd, var1_1)) == -3) {
            var8_8 = var4_4;
            if (!this.isOpen()) break;
            var8_8 = var4_4;
        }
        var8_8 = var4_4;
        var3_3 = IOStatus.normalize(var4_4);
        {
            catch (Throwable var9_12) {
                this.writerCleanup();
                var10_18 = var6_6;
                if (var8_8 <= 0) {
                    var10_18 = var8_8 == -2 ? var6_6 : false;
                }
                this.end(var10_18);
                var11_22 = this.stateLock;
                // MONITORENTER : var11_22
                if (var8_8 > 0) throw var9_12;
                if (this.isOutputOpen) {
                    throw var9_12;
                }
                var9_13 = new AsynchronousCloseException();
                throw var9_13;
                {
                    catch (Throwable var9_14) {}
                    {
                        // MONITOREXIT : var11_22
                        throw var9_10;
                    }
                }
            }
        }
        this.writerCleanup();
        var10_17 = var5_5;
        if (var4_4 <= 0) {
            var10_17 = var4_4 == -2 ? var5_5 : false;
        }
        this.end(var10_17);
        var11_20 = this.stateLock;
        // MONITORENTER : var11_20
        if (var4_4 > 0) ** GOTO lbl77
        try {
            if (this.isOutputOpen) {
                return var3_3;
            }
            var9_9 = new AsynchronousCloseException();
            throw var9_9;
lbl77: // 1 sources:
            // MONITOREXIT : var11_20
            // MONITOREXIT : var2_2
            return var3_3;
        }
        catch (Throwable var9_11) {
            // MONITOREXIT : var11_20
            throw var9_10;
        }
        {
            catch (Throwable var11_21) {}
            var8_8 = var3_3;
            throw var11_21;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> SocketChannel setOption(SocketOption<T> object, T object2) throws IOException {
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
                return this;
            }
            Net.setSocketOption(this.fd, Net.UNSPEC, object, object2);
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SocketChannel shutdownInput() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (!this.isConnected()) {
                NotYetConnectedException notYetConnectedException = new NotYetConnectedException();
                throw notYetConnectedException;
            }
            if (this.isInputOpen) {
                Net.shutdown(this.fd, 0);
                if (this.readerThread != 0L) {
                    NativeThread.signal(this.readerThread);
                }
                this.isInputOpen = false;
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
    public SocketChannel shutdownOutput() throws IOException {
        Object object = this.stateLock;
        synchronized (object) {
            if (!this.isOpen()) {
                ClosedChannelException closedChannelException = new ClosedChannelException();
                throw closedChannelException;
            }
            if (!this.isConnected()) {
                NotYetConnectedException notYetConnectedException = new NotYetConnectedException();
                throw notYetConnectedException;
            }
            if (this.isOutputOpen) {
                Net.shutdown(this.fd, 1);
                if (this.writerThread != 0L) {
                    NativeThread.signal(this.writerThread);
                }
                this.isOutputOpen = false;
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
    public Socket socket() {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.socket != null) return this.socket;
            this.socket = SocketAdaptor.create(this);
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
        stringBuffer.append(this.getClass().getSuperclass().getName());
        stringBuffer.append('[');
        if (!this.isOpen()) {
            stringBuffer.append("closed");
        } else {
            Object object = this.stateLock;
            synchronized (object) {
                int n = this.state;
                if (n != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            stringBuffer.append("connected");
                            if (!this.isInputOpen) {
                                stringBuffer.append(" ishut");
                            }
                            if (!this.isOutputOpen) {
                                stringBuffer.append(" oshut");
                            }
                        }
                    } else {
                        stringBuffer.append("connection-pending");
                    }
                } else {
                    stringBuffer.append("unconnected");
                }
                InetSocketAddress inetSocketAddress = this.localAddress();
                if (inetSocketAddress != null) {
                    stringBuffer.append(" local=");
                    stringBuffer.append(Net.getRevealedLocalAddressAsString(inetSocketAddress));
                }
                if (this.remoteAddress() != null) {
                    stringBuffer.append(" remote=");
                    stringBuffer.append(this.remoteAddress().toString());
                }
            }
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    @Override
    public void translateAndSetInterestOps(int n, SelectionKeyImpl selectionKeyImpl) {
        int n2 = 0;
        if ((n & 1) != 0) {
            n2 = 0 | Net.POLLIN;
        }
        int n3 = n2;
        if ((n & 4) != 0) {
            n3 = n2 | Net.POLLOUT;
        }
        n2 = n3;
        if ((n & 8) != 0) {
            n2 = n3 | Net.POLLCONN;
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
        int n3;
        int n4;
        boolean bl;
        boolean bl2;
        int n5;
        block13 : {
            block14 : {
                n4 = selectionKeyImpl.nioInterestOps();
                n3 = selectionKeyImpl.nioReadyOps();
                n5 = n2;
                n2 = Net.POLLNVAL;
                bl2 = false;
                bl = false;
                if ((n2 & n) != 0) {
                    return false;
                }
                if (((Net.POLLERR | Net.POLLHUP) & n) != 0) {
                    selectionKeyImpl.nioReadyOps(n4);
                    this.readyToConnect = true;
                    if ((n3 & n4) != 0) {
                        bl = true;
                    }
                    return bl;
                }
                n2 = n5;
                if ((Net.POLLIN & n) != 0) {
                    n2 = n5;
                    if ((n4 & 1) != 0) {
                        n2 = n5;
                        if (this.state == 2) {
                            n2 = n5 | 1;
                        }
                    }
                }
                n5 = n2;
                if ((Net.POLLCONN & n) == 0) break block13;
                n5 = n2;
                if ((n4 & 8) == 0) break block13;
                int n6 = this.state;
                if (n6 == 0) break block14;
                n5 = n2;
                if (n6 != 1) break block13;
            }
            n5 = n2 | 8;
            this.readyToConnect = true;
        }
        n2 = n5;
        if ((Net.POLLOUT & n) != 0) {
            n2 = n5;
            if ((n4 & 4) != 0) {
                n2 = n5;
                if (this.state == 2) {
                    n2 = n5 | 4;
                }
            }
        }
        selectionKeyImpl.nioReadyOps(n2);
        bl = bl2;
        if ((n3 & n2) != 0) {
            bl = true;
        }
        return bl;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public int write(ByteBuffer var1_1) throws IOException {
        block34 : {
            if (var1_1 == null) throw new NullPointerException();
            var2_9 = this.writeLock;
            // MONITORENTER : var2_9
            this.ensureWriteOpen();
            var3_10 = 0;
            var4_11 = 0;
            var5_12 = true;
            var6_13 = true;
            var7_14 = true;
            var8_15 = var3_10;
            this.begin();
            var8_15 = var3_10;
            var9_16 = this.stateLock;
            var8_15 = var3_10;
            // MONITORENTER : var9_16
            if (this.isOpen()) break block34;
            // MONITOREXIT : var9_16
            this.writerCleanup();
            var10_17 = var7_14;
            if (0 <= 0) {
                var10_17 = -2 == 0 ? var7_14 : false;
            }
            this.end(var10_17);
            var9_16 = this.stateLock;
            // MONITORENTER : var9_16
            if (0 > 0) {
                // MONITOREXIT : var9_16
                // MONITOREXIT : var2_9
                return 0;
            }
            if (this.isOutputOpen) {
                return 0;
            }
            var1_1 = new AsynchronousCloseException();
            throw var1_1;
        }
        this.writerThread = NativeThread.current();
        // MONITOREXIT : var9_16
        var8_15 = var4_11;
        while ((var4_11 = IOUtil.write(this.fd, (ByteBuffer)var1_1, -1L, SocketChannelImpl.nd)) == -3) {
            var8_15 = var4_11;
            if (!this.isOpen()) break;
            var8_15 = var4_11;
        }
        var8_15 = var4_11;
        var3_10 = IOStatus.normalize(var4_11);
        {
            catch (Throwable var1_5) {
                this.writerCleanup();
                var10_19 = var6_13;
                if (var8_15 <= 0) {
                    var10_19 = var8_15 == -2 ? var6_13 : false;
                }
                this.end(var10_19);
                var9_16 = this.stateLock;
                // MONITORENTER : var9_16
                if (var8_15 > 0) throw var1_5;
                if (this.isOutputOpen) {
                    throw var1_5;
                }
                var1_6 = new AsynchronousCloseException();
                throw var1_6;
                {
                    catch (Throwable var1_7) {}
                    {
                        // MONITOREXIT : var9_16
                        throw var1_2;
                    }
                }
            }
        }
        this.writerCleanup();
        var10_18 = var5_12;
        if (var4_11 <= 0) {
            var10_18 = var4_11 == -2 ? var5_12 : false;
        }
        this.end(var10_18);
        var9_16 = this.stateLock;
        // MONITORENTER : var9_16
        if (var4_11 > 0) ** GOTO lbl78
        try {
            if (this.isOutputOpen) {
                return var3_10;
            }
            var1_1 = new AsynchronousCloseException();
            throw var1_1;
lbl78: // 1 sources:
            // MONITOREXIT : var9_16
            // MONITOREXIT : var2_9
            return var3_10;
        }
        catch (Throwable var1_3) {
            // MONITOREXIT : var9_16
            throw var1_2;
        }
        {
            catch (Throwable var1_4) {}
            var8_15 = var3_10;
            throw var1_4;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public long write(ByteBuffer[] var1_1, int var2_9, int var3_10) throws IOException {
        block42 : {
            block41 : {
                block38 : {
                    if (var2_9 < 0) throw new IndexOutOfBoundsException();
                    if (var3_10 < 0) throw new IndexOutOfBoundsException();
                    if (var2_9 > var1_1 /* !! */ .length - var3_10) throw new IndexOutOfBoundsException();
                    var4_11 = this.writeLock;
                    // MONITORENTER : var4_11
                    this.ensureWriteOpen();
                    var5_12 = 0L;
                    var7_13 = false;
                    var8_14 = false;
                    var9_15 = false;
                    var10_16 = var5_12;
                    this.begin();
                    var10_16 = var5_12;
                    var12_17 = this.stateLock;
                    var10_16 = var5_12;
                    // MONITORENTER : var12_17
                    if (this.isOpen()) break block38;
                    // MONITOREXIT : var12_17
                    this.writerCleanup();
                    if (0L > 0L || 0L == -2L) {
                        var9_15 = true;
                    }
                    this.end(var9_15);
                    var12_17 = this.stateLock;
                    // MONITORENTER : var12_17
                    if (0L > 0L) {
                        // MONITOREXIT : var12_17
                        // MONITOREXIT : var4_11
                        return 0L;
                    }
                    if (this.isOutputOpen) {
                        return 0L;
                    }
                    var1_1 /* !! */  = new AsynchronousCloseException();
                    throw var1_1 /* !! */ ;
                }
                this.writerThread = NativeThread.current();
                // MONITOREXIT : var12_17
                var10_16 = var5_12;
                while ((var5_12 = IOUtil.write(this.fd, var1_1 /* !! */ , var2_9, var3_10, SocketChannelImpl.nd)) == -3L) {
                    var10_16 = var5_12;
                    if (!this.isOpen()) break;
                    var10_16 = var5_12;
                }
                var10_16 = var5_12;
                var13_18 = IOStatus.normalize(var5_12);
                {
                    catch (Throwable var1_5) {
                        block40 : {
                            block39 : {
                                this.writerCleanup();
                                if (var10_16 > 0L) break block39;
                                var9_15 = var8_14;
                                if (var10_16 != -2L) break block40;
                            }
                            var9_15 = true;
                        }
                        this.end(var9_15);
                        var12_17 = this.stateLock;
                        // MONITORENTER : var12_17
                        if (var10_16 > 0L) throw var1_5;
                        if (this.isOutputOpen) {
                            throw var1_5;
                        }
                        var1_6 = new AsynchronousCloseException();
                        throw var1_6;
                        {
                            catch (Throwable var1_7) {}
                            {
                                // MONITOREXIT : var12_17
                                throw var1_2;
                            }
                        }
                    }
                }
                this.writerCleanup();
                if (var5_12 > 0L) break block41;
                var9_15 = var7_13;
                if (var5_12 != -2L) break block42;
            }
            var9_15 = true;
        }
        this.end(var9_15);
        var12_17 = this.stateLock;
        // MONITORENTER : var12_17
        if (var5_12 > 0L) ** GOTO lbl84
        try {
            if (this.isOutputOpen) {
                return var13_18;
            }
            var1_1 /* !! */  = new AsynchronousCloseException();
            throw var1_1 /* !! */ ;
lbl84: // 1 sources:
            // MONITOREXIT : var12_17
            // MONITOREXIT : var4_11
            return var13_18;
        }
        catch (Throwable var1_3) {
            // MONITOREXIT : var12_17
            throw var1_2;
        }
        {
            catch (Throwable var1_4) {}
            var10_16 = var5_12;
            throw var1_4;
        }
    }

    private static class DefaultOptionsHolder {
        static final Set<SocketOption<?>> defaultOptions = DefaultOptionsHolder.defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set<SocketOption<?>> defaultOptions() {
            HashSet<SocketOption<Object>> hashSet = new HashSet<SocketOption<Object>>(8);
            hashSet.add(StandardSocketOptions.SO_SNDBUF);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_KEEPALIVE);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            hashSet.add(StandardSocketOptions.SO_LINGER);
            hashSet.add(StandardSocketOptions.TCP_NODELAY);
            hashSet.add(StandardSocketOptions.IP_TOS);
            hashSet.add(ExtendedSocketOption.SO_OOBINLINE);
            if (ExtendedOptionsImpl.flowSupported()) {
                hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
            }
            return Collections.unmodifiableSet(hashSet);
        }
    }

}

