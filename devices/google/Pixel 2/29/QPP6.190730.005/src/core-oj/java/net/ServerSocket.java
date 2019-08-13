/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.net.SocksSocketImpl;
import java.nio.channels.ServerSocketChannel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class ServerSocket
implements Closeable {
    private static SocketImplFactory factory = null;
    private boolean bound = false;
    private Object closeLock = new Object();
    private boolean closed = false;
    private boolean created = false;
    private SocketImpl impl;
    private boolean oldImpl = false;

    public ServerSocket() throws IOException {
        this.setImpl();
    }

    public ServerSocket(int n) throws IOException {
        this(n, 50, null);
    }

    public ServerSocket(int n, int n2) throws IOException {
        this(n, n2, null);
    }

    public ServerSocket(int n, int n2, InetAddress serializable) throws IOException {
        this.setImpl();
        if (n >= 0 && n <= 65535) {
            int n3 = n2;
            if (n2 < 1) {
                n3 = 50;
            }
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress((InetAddress)serializable, n);
                this.bind(inetSocketAddress, n3);
                return;
            }
            catch (IOException iOException) {
                this.close();
                throw iOException;
            }
            catch (SecurityException securityException) {
                this.close();
                throw securityException;
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Port value out of range: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    ServerSocket(SocketImpl socketImpl) {
        this.impl = socketImpl;
        socketImpl.setServerSocket(this);
    }

    private void checkOldImpl() {
        if (this.impl == null) {
            return;
        }
        try {
            PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                @Override
                public Void run() throws NoSuchMethodException {
                    ServerSocket.this.impl.getClass().getDeclaredMethod("connect", SocketAddress.class, Integer.TYPE);
                    return null;
                }
            };
            AccessController.doPrivileged(privilegedExceptionAction);
        }
        catch (PrivilegedActionException privilegedActionException) {
            this.oldImpl = true;
        }
    }

    private void setImpl() {
        Object object = factory;
        if (object != null) {
            this.impl = object.createSocketImpl();
            this.checkOldImpl();
        } else {
            this.impl = new SocksSocketImpl();
        }
        object = this.impl;
        if (object != null) {
            ((SocketImpl)object).setServerSocket(this);
        }
    }

    public static void setSocketFactory(SocketImplFactory object) throws IOException {
        synchronized (ServerSocket.class) {
            if (factory == null) {
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    securityManager.checkSetFactory();
                }
                factory = object;
                return;
            }
            object = new SocketException("factory already defined");
            throw object;
        }
    }

    public Socket accept() throws IOException {
        if (!this.isClosed()) {
            if (this.isBound()) {
                Socket socket = new Socket((SocketImpl)null);
                this.implAccept(socket);
                return socket;
            }
            throw new SocketException("Socket is not bound yet");
        }
        throw new SocketException("Socket is closed");
    }

    public void bind(SocketAddress socketAddress) throws IOException {
        this.bind(socketAddress, 50);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bind(SocketAddress object, int n) throws IOException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!this.oldImpl && this.isBound()) {
            throw new SocketException("Already bound");
        }
        Object object2 = object;
        if (object == null) {
            object2 = new InetSocketAddress(0);
        }
        if (!(object2 instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Unsupported address type");
        }
        if (((InetSocketAddress)(object2 = (InetSocketAddress)object2)).isUnresolved()) {
            throw new SocketException("Unresolved address");
        }
        int n2 = n;
        if (n < 1) {
            n2 = 50;
        }
        try {
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkListen(((InetSocketAddress)object2).getPort());
            }
            this.getImpl().bind(((InetSocketAddress)object2).getAddress(), ((InetSocketAddress)object2).getPort());
            this.getImpl().listen(n2);
            this.bound = true;
            return;
        }
        catch (IOException iOException) {
            this.bound = false;
            throw iOException;
        }
        catch (SecurityException securityException) {
            this.bound = false;
            throw securityException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        Object object = this.closeLock;
        synchronized (object) {
            if (this.isClosed()) {
                return;
            }
            if (this.created) {
                this.impl.close();
            }
            this.closed = true;
            return;
        }
    }

    void createImpl() throws SocketException {
        if (this.impl == null) {
            this.setImpl();
        }
        try {
            this.impl.create(true);
            this.created = true;
            return;
        }
        catch (IOException iOException) {
            throw new SocketException(iOException.getMessage());
        }
    }

    public ServerSocketChannel getChannel() {
        return null;
    }

    public FileDescriptor getFileDescriptor$() {
        return this.impl.getFileDescriptor();
    }

    public SocketImpl getImpl() throws SocketException {
        if (!this.created) {
            this.createImpl();
        }
        return this.impl;
    }

    public InetAddress getInetAddress() {
        InetAddress inetAddress;
        block5 : {
            if (!this.isBound()) {
                return null;
            }
            inetAddress = this.getImpl().getInetAddress();
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager == null) break block5;
            try {
                securityManager.checkConnect(inetAddress.getHostAddress(), -1);
            }
            catch (SocketException socketException) {
                return null;
            }
            catch (SecurityException securityException) {
                return InetAddress.getLoopbackAddress();
            }
        }
        return inetAddress;
    }

    public int getLocalPort() {
        if (!this.isBound()) {
            return -1;
        }
        try {
            int n = this.getImpl().getLocalPort();
            return n;
        }
        catch (SocketException socketException) {
            return -1;
        }
    }

    public SocketAddress getLocalSocketAddress() {
        if (!this.isBound()) {
            return null;
        }
        return new InetSocketAddress(this.getInetAddress(), this.getLocalPort());
    }

    public int getReceiveBufferSize() throws SocketException {
        synchronized (this) {
            block6 : {
                if (this.isClosed()) break block6;
                int n = 0;
                Object object = this.getImpl().getOption(4098);
                if (object instanceof Integer) {
                    n = (Integer)object;
                }
                return n;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    public boolean getReuseAddress() throws SocketException {
        if (!this.isClosed()) {
            return (Boolean)this.getImpl().getOption(4);
        }
        throw new SocketException("Socket is closed");
    }

    public int getSoTimeout() throws IOException {
        synchronized (this) {
            if (!this.isClosed()) {
                Object object = this.getImpl().getOption(4102);
                if (object instanceof Integer) {
                    int n = (Integer)object;
                    return n;
                }
                return 0;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    protected final void implAccept(Socket socket) throws IOException {
        SocketImpl socketImpl;
        block20 : {
            SocketImpl socketImpl2;
            Object object;
            SocketImpl socketImpl3;
            block19 : {
                block18 : {
                    object = null;
                    socketImpl3 = socketImpl = null;
                    socketImpl2 = object;
                    if (socket.impl != null) break block18;
                    socketImpl3 = socketImpl;
                    socketImpl2 = object;
                    socket.setImpl();
                    break block19;
                }
                socketImpl3 = socketImpl;
                socketImpl2 = object;
                socket.impl.reset();
            }
            socketImpl3 = socketImpl;
            socketImpl2 = object;
            socketImpl3 = socketImpl = socket.impl;
            socketImpl2 = socketImpl;
            socket.impl = null;
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            object = new InetAddress();
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            socketImpl.address = object;
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            object = new FileDescriptor();
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            socketImpl.fd = object;
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            this.getImpl().accept(socketImpl);
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            object = System.getSecurityManager();
            if (object == null) break block20;
            socketImpl3 = socketImpl;
            socketImpl2 = socketImpl;
            try {
                ((SecurityManager)object).checkAccept(socketImpl.getInetAddress().getHostAddress(), socketImpl.getPort());
            }
            catch (SecurityException securityException) {
                if (socketImpl3 != null) {
                    socketImpl3.reset();
                }
                socket.impl = socketImpl3;
                throw securityException;
            }
            catch (IOException iOException) {
                if (socketImpl2 != null) {
                    socketImpl2.reset();
                }
                socket.impl = socketImpl2;
                throw iOException;
            }
        }
        socket.impl = socketImpl;
        socket.postAccept();
        return;
    }

    public boolean isBound() {
        boolean bl = this.bound || this.oldImpl;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isClosed() {
        Object object = this.closeLock;
        synchronized (object) {
            return this.closed;
        }
    }

    void setBound() {
        this.bound = true;
    }

    void setCreated() {
        this.created = true;
    }

    public void setPerformancePreferences(int n, int n2, int n3) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setReceiveBufferSize(int n) throws SocketException {
        synchronized (this) {
            Throwable throwable2;
            if (n > 0) {
                try {
                    if (!this.isClosed()) {
                        SocketImpl socketImpl = this.getImpl();
                        Integer n2 = new Integer(n);
                        socketImpl.setOption(4098, n2);
                        return;
                    }
                    SocketException socketException = new SocketException("Socket is closed");
                    throw socketException;
                }
                catch (Throwable throwable2) {}
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("negative receive size");
                throw illegalArgumentException;
            }
            throw throwable2;
        }
    }

    public void setReuseAddress(boolean bl) throws SocketException {
        if (!this.isClosed()) {
            this.getImpl().setOption(4, bl);
            return;
        }
        throw new SocketException("Socket is closed");
    }

    public void setSoTimeout(int n) throws SocketException {
        synchronized (this) {
            if (!this.isClosed()) {
                SocketImpl socketImpl = this.getImpl();
                Integer n2 = new Integer(n);
                socketImpl.setOption(4102, n2);
                return;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    public String toString() {
        if (!this.isBound()) {
            return "ServerSocket[unbound]";
        }
        InetAddress inetAddress = System.getSecurityManager() != null ? InetAddress.getLoopbackAddress() : this.impl.getInetAddress();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServerSocket[addr=");
        stringBuilder.append(inetAddress);
        stringBuilder.append(",localport=");
        stringBuilder.append(this.impl.getLocalPort());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}

