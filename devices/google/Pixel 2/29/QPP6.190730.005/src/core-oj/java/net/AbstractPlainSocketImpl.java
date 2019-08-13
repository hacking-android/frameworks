/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 */
package java.net;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketInputStream;
import java.net.SocketOutputStream;
import java.net.UnknownHostException;
import sun.net.ConnectionResetException;
import sun.net.NetHooks;
import sun.net.ResourceManager;

abstract class AbstractPlainSocketImpl
extends SocketImpl {
    public static final int SHUT_RD = 0;
    public static final int SHUT_WR = 1;
    private int CONNECTION_NOT_RESET = 0;
    private int CONNECTION_RESET = 2;
    private int CONNECTION_RESET_PENDING = 1;
    protected boolean closePending = false;
    @ReachabilitySensitive
    protected final Object fdLock = new Object();
    protected int fdUseCount = 0;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private final Object resetLock = new Object();
    private int resetState;
    private boolean shut_rd = false;
    private boolean shut_wr = false;
    private SocketInputStream socketInputStream = null;
    private SocketOutputStream socketOutputStream = null;
    protected boolean stream;
    int timeout;

    AbstractPlainSocketImpl() {
    }

    private void connectToAddress(InetAddress inetAddress, int n, int n2) throws IOException {
        if (inetAddress.isAnyLocalAddress()) {
            this.doConnect(InetAddress.getLocalHost(), n, n2);
        } else {
            this.doConnect(inetAddress, n, n2);
        }
    }

    private void socketPreClose() throws IOException {
        this.socketClose0(true);
    }

    @Override
    protected void accept(SocketImpl socketImpl) throws IOException {
        this.acquireFD();
        try {
            BlockGuard.getThreadPolicy().onNetwork();
            this.socketAccept(socketImpl);
            return;
        }
        finally {
            this.releaseFD();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FileDescriptor acquireFD() {
        Object object = this.fdLock;
        synchronized (object) {
            ++this.fdUseCount;
            return this.fd;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected int available() throws IOException {
        synchronized (this) {
            if (this.isClosedOrPending()) {
                IOException throwable = new IOException("Stream closed.");
                throw throwable;
            }
            if (this.isConnectionReset()) return 0;
            boolean bl = this.shut_rd;
            if (!bl) {
                int n = 0;
                try {
                    int n2 = this.socketAvailable();
                    if (n2 != 0) return n2;
                    n = n2;
                    if (!this.isConnectionResetPending()) return n2;
                    n = n2;
                    this.setConnectionReset();
                    return n2;
                }
                catch (ConnectionResetException connectionResetException2) {
                    this.setConnectionResetPending();
                    try {
                        int n2 = this.socketAvailable();
                        if (n2 != 0) return n2;
                        n = n2;
                        this.setConnectionReset();
                        return n2;
                    }
                    catch (ConnectionResetException iOException) {
                        // empty catch block
                    }
                }
                return n;
            }
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    protected void bind(InetAddress inetAddress, int n) throws IOException {
        // MONITORENTER : this
        Object object = this.fdLock;
        // MONITORENTER : object
        boolean bl = this.closePending;
        if (!bl) {
            if (this.socket == null || !this.socket.isBound()) {
                NetHooks.beforeTcpBind(this.fd, inetAddress, n);
            }
        }
        // MONITOREXIT : object
        this.socketBind(inetAddress, n);
        if (this.socket != null) {
            this.socket.setBound();
        }
        if (this.serverSocket != null) {
            this.serverSocket.setBound();
        }
        // MONITOREXIT : this
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void close() throws IOException {
        Object object = this.fdLock;
        synchronized (object) {
            if (this.fd != null && this.fd.valid()) {
                if (!this.stream) {
                    ResourceManager.afterUdpClose();
                }
                if (!this.closePending) {
                    this.closePending = true;
                    this.guard.close();
                    int n = this.fdUseCount--;
                    if (n == 0) {
                        try {
                            this.socketPreClose();
                            return;
                        }
                        finally {
                            this.socketClose();
                        }
                    }
                    this.socketPreClose();
                }
            }
            return;
        }
    }

    @Override
    protected void connect(String object, int n) throws UnknownHostException, IOException {
        block7 : {
            try {
                object = InetAddress.getByName((String)object);
                this.port = n;
                this.address = object;
                this.connectToAddress((InetAddress)object, n, this.timeout);
                if (true) break block7;
            }
            catch (Throwable throwable) {
                if (!false) {
                    try {
                        this.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                throw throwable;
            }
            try {
                this.close();
            }
            catch (IOException iOException) {}
        }
    }

    @Override
    protected void connect(InetAddress inetAddress, int n) throws IOException {
        this.port = n;
        this.address = inetAddress;
        try {
            this.connectToAddress(inetAddress, n, this.timeout);
            return;
        }
        catch (IOException iOException) {
            this.close();
            throw iOException;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void connect(SocketAddress var1_1, int var2_4) throws IOException {
        block7 : {
            if (var1_1 /* !! */  == null) ** GOTO lbl27
            try {
                if (!(var1_1 /* !! */  instanceof InetSocketAddress)) ** GOTO lbl27
                var3_7 = (InetSocketAddress)var1_1 /* !! */ ;
                if (var3_7.isUnresolved()) break block7;
                this.port = var3_7.getPort();
                this.address = var3_7.getAddress();
                this.connectToAddress(this.address, this.port, (int)var2_6);
                if (true != false) return;
            }
            catch (Throwable var1_5) {
                if (false != false) throw var1_5;
                try {
                    this.close();
                    throw var1_5;
                }
                catch (IOException var3_8) {
                    // empty catch block
                }
                throw var1_5;
            }
            try {
                this.close();
                return;
            }
            catch (IOException var1_2) {
                return;
            }
        }
        var1_3 = new UnknownHostException(var3_7.getHostName());
        throw var1_3;
lbl27: // 2 sources:
        var1_4 = new IllegalArgumentException("unsupported address type");
        throw var1_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void create(boolean bl) throws IOException {
        synchronized (this) {
            this.stream = bl;
            if (!bl) {
                ResourceManager.beforeUdpCreate();
                try {
                    this.socketCreate(false);
                }
                catch (IOException iOException) {
                    ResourceManager.afterUdpClose();
                    throw iOException;
                }
            } else {
                this.socketCreate(true);
            }
            if (this.socket != null) {
                this.socket.setCreated();
            }
            if (this.serverSocket != null) {
                this.serverSocket.setCreated();
            }
            if (this.fd != null && this.fd.valid()) {
                this.guard.open("close");
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void doConnect(InetAddress object, int n, int n2) throws IOException {
        block29 : {
            block27 : {
                block28 : {
                    // MONITORENTER : this
                    Object object2 = this.fdLock;
                    // MONITORENTER : object2
                    boolean bl = this.closePending;
                    if (!bl) {
                        if (this.socket == null || !this.socket.isBound()) {
                            NetHooks.beforeTcpConnect(this.fd, (InetAddress)object, n);
                        }
                    }
                    // MONITOREXIT : object2
                    try {
                        this.acquireFD();
                    }
                    catch (IOException iOException) {
                        this.close();
                        throw iOException;
                    }
                    BlockGuard.getThreadPolicy().onNetwork();
                    this.socketConnect((InetAddress)object, n, n2);
                    object2 = this.fdLock;
                    // MONITORENTER : object2
                    bl = this.closePending;
                    if (bl) break block27;
                    object = this.socket;
                    if (object == null) break block28;
                    this.socket.setBound();
                    this.socket.setConnected();
                }
                this.releaseFD();
                // MONITOREXIT : this
                return;
                catch (Throwable throwable) {}
                break block29;
            }
            object = new SocketException("Socket closed");
            throw object;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        this.releaseFD();
        throw object;
    }

    protected void finalize() throws IOException {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    protected InputStream getInputStream() throws IOException {
        SocketInputStream socketInputStream;
        block13 : {
            // MONITORENTER : this
            Object object = this.fdLock;
            // MONITORENTER : object
            if (this.isClosedOrPending()) {
                IOException iOException = new IOException("Socket Closed");
                throw iOException;
            }
            boolean bl = this.shut_rd;
            if (bl) {
                IOException iOException = new IOException("Socket input is shutdown");
                throw iOException;
            }
            socketInputStream = this.socketInputStream;
            if (socketInputStream != null) break block13;
            this.socketInputStream = socketInputStream = new SocketInputStream(this);
        }
        // MONITOREXIT : object
        socketInputStream = this.socketInputStream;
        // MONITOREXIT : this
        return socketInputStream;
    }

    @Override
    public Object getOption(int n) throws SocketException {
        if (!this.isClosedOrPending()) {
            if (n == 4102) {
                return new Integer(this.timeout);
            }
            return this.socketGetOption(n);
        }
        throw new SocketException("Socket Closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    protected OutputStream getOutputStream() throws IOException {
        SocketOutputStream socketOutputStream;
        block13 : {
            // MONITORENTER : this
            Object object = this.fdLock;
            // MONITORENTER : object
            if (this.isClosedOrPending()) {
                IOException iOException = new IOException("Socket Closed");
                throw iOException;
            }
            boolean bl = this.shut_wr;
            if (bl) {
                IOException iOException = new IOException("Socket output is shutdown");
                throw iOException;
            }
            socketOutputStream = this.socketOutputStream;
            if (socketOutputStream != null) break block13;
            this.socketOutputStream = socketOutputStream = new SocketOutputStream(this);
        }
        // MONITOREXIT : object
        socketOutputStream = this.socketOutputStream;
        // MONITOREXIT : this
        return socketOutputStream;
    }

    public int getTimeout() {
        return this.timeout;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isClosedOrPending() {
        Object object = this.fdLock;
        synchronized (object) {
            return this.closePending || this.fd == null || !this.fd.valid();
            {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionReset() {
        Object object = this.resetLock;
        synchronized (object) {
            if (this.resetState != this.CONNECTION_RESET) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionResetPending() {
        Object object = this.resetLock;
        synchronized (object) {
            if (this.resetState != this.CONNECTION_RESET_PENDING) return false;
            return true;
        }
    }

    @Override
    protected void listen(int n) throws IOException {
        synchronized (this) {
            this.socketListen(n);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void releaseFD() {
        Object object = this.fdLock;
        synchronized (object) {
            FileDescriptor fileDescriptor;
            --this.fdUseCount;
            if (this.fdUseCount == -1 && (fileDescriptor = this.fd) != null) {
                try {
                    this.socketClose();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            return;
        }
    }

    @Override
    void reset() throws IOException {
        if (this.fd != null && this.fd.valid()) {
            this.socketClose();
            this.guard.close();
        }
        super.reset();
    }

    @Override
    protected void sendUrgentData(int n) throws IOException {
        if (this.fd != null && this.fd.valid()) {
            this.socketSendUrgentData(n);
            return;
        }
        throw new IOException("Socket Closed");
    }

    void setAddress(InetAddress inetAddress) {
        this.address = inetAddress;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setConnectionReset() {
        Object object = this.resetLock;
        synchronized (object) {
            this.resetState = this.CONNECTION_RESET;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setConnectionResetPending() {
        Object object = this.resetLock;
        synchronized (object) {
            if (this.resetState == this.CONNECTION_NOT_RESET) {
                this.resetState = this.CONNECTION_RESET_PENDING;
            }
            return;
        }
    }

    void setInputStream(SocketInputStream socketInputStream) {
        this.socketInputStream = socketInputStream;
    }

    void setLocalPort(int n) {
        this.localport = n;
    }

    @Override
    public void setOption(int n, Object object) throws SocketException {
        if (!this.isClosedOrPending()) {
            if (n == 4102) {
                this.timeout = (Integer)object;
            }
            this.socketSetOption(n, object);
            return;
        }
        throw new SocketException("Socket Closed");
    }

    void setPort(int n) {
        this.port = n;
    }

    @Override
    protected void shutdownInput() throws IOException {
        if (this.fd != null && this.fd.valid()) {
            this.socketShutdown(0);
            SocketInputStream socketInputStream = this.socketInputStream;
            if (socketInputStream != null) {
                socketInputStream.setEOF(true);
            }
            this.shut_rd = true;
        }
    }

    @Override
    protected void shutdownOutput() throws IOException {
        if (this.fd != null && this.fd.valid()) {
            this.socketShutdown(1);
            this.shut_wr = true;
        }
    }

    abstract void socketAccept(SocketImpl var1) throws IOException;

    abstract int socketAvailable() throws IOException;

    abstract void socketBind(InetAddress var1, int var2) throws IOException;

    protected void socketClose() throws IOException {
        this.socketClose0(false);
    }

    abstract void socketClose0(boolean var1) throws IOException;

    abstract void socketConnect(InetAddress var1, int var2, int var3) throws IOException;

    abstract void socketCreate(boolean var1) throws IOException;

    abstract Object socketGetOption(int var1) throws SocketException;

    abstract void socketListen(int var1) throws IOException;

    abstract void socketSendUrgentData(int var1) throws IOException;

    abstract void socketSetOption(int var1, Object var2) throws SocketException;

    abstract void socketShutdown(int var1) throws IOException;

    @Override
    protected boolean supportsUrgentData() {
        return true;
    }
}

