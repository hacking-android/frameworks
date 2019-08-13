/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PlainSocketImpl;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.SocketImplFactory;
import java.net.SocksSocketImpl;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.net.ApplicationProxy;

public class Socket
implements Closeable {
    private static SocketImplFactory factory = null;
    private boolean bound;
    private Object closeLock;
    private boolean closed;
    private boolean connected;
    private boolean created;
    SocketImpl impl;
    private boolean oldImpl;
    private boolean shutIn;
    private boolean shutOut;

    public Socket() {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        this.setImpl();
    }

    public Socket(String string, int n) throws UnknownHostException, IOException {
        this(InetAddress.getAllByName(string), n, (SocketAddress)null, true);
    }

    public Socket(String string, int n, InetAddress inetAddress, int n2) throws IOException {
        this(InetAddress.getAllByName(string), n, new InetSocketAddress(inetAddress, n2), true);
    }

    @Deprecated
    public Socket(String string, int n, boolean bl) throws IOException {
        this(InetAddress.getAllByName(string), n, (SocketAddress)null, bl);
    }

    public Socket(InetAddress inetAddress, int n) throws IOException {
        this(Socket.nonNullAddress(inetAddress), n, (SocketAddress)null, true);
    }

    public Socket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        this(Socket.nonNullAddress(inetAddress), n, new InetSocketAddress(inetAddress2, n2), true);
    }

    @Deprecated
    public Socket(InetAddress inetAddress, int n, boolean bl) throws IOException {
        this(Socket.nonNullAddress(inetAddress), n, new InetSocketAddress(0), bl);
    }

    public Socket(Proxy proxy) {
        block10 : {
            block13 : {
                block12 : {
                    block11 : {
                        this.created = false;
                        this.bound = false;
                        this.connected = false;
                        this.closed = false;
                        this.closeLock = new Object();
                        this.shutIn = false;
                        this.shutOut = false;
                        this.oldImpl = false;
                        if (proxy == null) break block10;
                        if ((proxy = proxy == Proxy.NO_PROXY ? Proxy.NO_PROXY : ApplicationProxy.create(proxy)).type() != Proxy.Type.SOCKS) break block11;
                        SecurityManager securityManager = System.getSecurityManager();
                        InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
                        if (inetSocketAddress.getAddress() != null) {
                            this.checkAddress(inetSocketAddress.getAddress(), "Socket");
                        }
                        if (securityManager != null) {
                            InetSocketAddress inetSocketAddress2 = inetSocketAddress;
                            if (inetSocketAddress.isUnresolved()) {
                                inetSocketAddress2 = new InetSocketAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort());
                            }
                            if (inetSocketAddress2.isUnresolved()) {
                                securityManager.checkConnect(inetSocketAddress2.getHostName(), inetSocketAddress2.getPort());
                            } else {
                                securityManager.checkConnect(inetSocketAddress2.getAddress().getHostAddress(), inetSocketAddress2.getPort());
                            }
                        }
                        this.impl = new SocksSocketImpl(proxy);
                        this.impl.setSocket(this);
                        break block12;
                    }
                    if (proxy != Proxy.NO_PROXY) break block13;
                    if (factory == null) {
                        this.impl = new PlainSocketImpl();
                        this.impl.setSocket(this);
                    } else {
                        this.setImpl();
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Invalid Proxy");
        }
        throw new IllegalArgumentException("Invalid Proxy");
    }

    protected Socket(SocketImpl socketImpl) throws SocketException {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        this.impl = socketImpl;
        if (socketImpl != null) {
            this.checkOldImpl();
            this.impl.setSocket(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Socket(InetAddress[] arrinetAddress, int n, SocketAddress socketAddress, boolean bl) throws IOException {
        this.created = false;
        this.bound = false;
        this.connected = false;
        this.closed = false;
        this.closeLock = new Object();
        this.shutIn = false;
        this.shutOut = false;
        this.oldImpl = false;
        if (arrinetAddress == null) throw new SocketException("Impossible: empty address list");
        if (arrinetAddress.length == 0) throw new SocketException("Impossible: empty address list");
        int n2 = 0;
        while (n2 < arrinetAddress.length) {
            this.setImpl();
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(arrinetAddress[n2], n);
                this.createImpl(bl);
                if (socketAddress != null) {
                    this.bind(socketAddress);
                }
                this.connect(inetSocketAddress);
                return;
            }
            catch (IOException | IllegalArgumentException | SecurityException exception) {
                try {
                    this.impl.close();
                    this.closed = true;
                }
                catch (IOException iOException) {
                    exception.addSuppressed(iOException);
                }
                if (n2 == arrinetAddress.length - 1) throw exception;
                this.impl = null;
                this.created = false;
                this.bound = false;
                this.closed = false;
                ++n2;
            }
        }
    }

    private void checkAddress(InetAddress serializable, String string) {
        if (serializable == null) {
            return;
        }
        if (!(serializable instanceof Inet4Address) && !(serializable instanceof Inet6Address)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(": invalid address type");
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
    }

    private void checkOldImpl() {
        if (this.impl == null) {
            return;
        }
        this.oldImpl = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                Class<?> class_ = Socket.this.impl.getClass();
                do {
                    try {
                        class_.getDeclaredMethod("connect", SocketAddress.class, Integer.TYPE);
                        Boolean bl = Boolean.FALSE;
                        return bl;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        if (!(class_ = class_.getSuperclass()).equals(SocketImpl.class)) continue;
                        return Boolean.TRUE;
                    }
                    break;
                } while (true);
            }
        });
    }

    private static InetAddress[] nonNullAddress(InetAddress inetAddress) {
        if (inetAddress != null) {
            return new InetAddress[]{inetAddress};
        }
        throw new NullPointerException();
    }

    public static void setSocketImplFactory(SocketImplFactory object) throws IOException {
        synchronized (Socket.class) {
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

    public void bind(SocketAddress object) throws IOException {
        if (!this.isClosed()) {
            if (!this.oldImpl && this.isBound()) {
                throw new SocketException("Already bound");
            }
            if (object != null && !(object instanceof InetSocketAddress)) {
                throw new IllegalArgumentException("Unsupported address type");
            }
            Serializable serializable = (InetSocketAddress)object;
            if (serializable != null && serializable.isUnresolved()) {
                throw new SocketException("Unresolved address");
            }
            object = serializable;
            if (serializable == null) {
                object = new InetSocketAddress(0);
            }
            serializable = ((InetSocketAddress)object).getAddress();
            int n = ((InetSocketAddress)object).getPort();
            this.checkAddress((InetAddress)serializable, "bind");
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkListen(n);
            }
            this.getImpl().bind((InetAddress)serializable, n);
            this.bound = true;
            return;
        }
        throw new SocketException("Socket is closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        synchronized (this) {
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
    }

    public void connect(SocketAddress socketAddress) throws IOException {
        this.connect(socketAddress, 0);
    }

    public void connect(SocketAddress serializable, int n) throws IOException {
        block10 : {
            block11 : {
                block12 : {
                    block13 : {
                        block16 : {
                            block15 : {
                                int n2;
                                InetSocketAddress inetSocketAddress;
                                block14 : {
                                    if (serializable == null) break block10;
                                    if (n < 0) break block11;
                                    if (this.isClosed()) break block12;
                                    if (!this.oldImpl && this.isConnected()) {
                                        throw new SocketException("already connected");
                                    }
                                    if (!(serializable instanceof InetSocketAddress)) break block13;
                                    inetSocketAddress = (InetSocketAddress)serializable;
                                    serializable = inetSocketAddress.getAddress();
                                    n2 = inetSocketAddress.getPort();
                                    this.checkAddress((InetAddress)serializable, "connect");
                                    SecurityManager securityManager = System.getSecurityManager();
                                    if (securityManager != null) {
                                        if (inetSocketAddress.isUnresolved()) {
                                            securityManager.checkConnect(inetSocketAddress.getHostName(), n2);
                                        } else {
                                            securityManager.checkConnect(((InetAddress)serializable).getHostAddress(), n2);
                                        }
                                    }
                                    if (!this.created) {
                                        this.createImpl(true);
                                    }
                                    if (this.oldImpl) break block14;
                                    this.impl.connect(inetSocketAddress, n);
                                    break block15;
                                }
                                if (n != 0) break block16;
                                if (inetSocketAddress.isUnresolved()) {
                                    this.impl.connect(((InetAddress)serializable).getHostName(), n2);
                                } else {
                                    this.impl.connect((InetAddress)serializable, n2);
                                }
                            }
                            this.connected = true;
                            this.bound = true;
                            return;
                        }
                        throw new UnsupportedOperationException("SocketImpl.connect(addr, timeout)");
                    }
                    throw new IllegalArgumentException("Unsupported address type");
                }
                throw new SocketException("Socket is closed");
            }
            throw new IllegalArgumentException("connect: timeout can't be negative");
        }
        throw new IllegalArgumentException("connect: The address can't be null");
    }

    void createImpl(boolean bl) throws SocketException {
        if (this.impl == null) {
            this.setImpl();
        }
        try {
            this.impl.create(bl);
            this.created = true;
            return;
        }
        catch (IOException iOException) {
            throw new SocketException(iOException.getMessage());
        }
    }

    public SocketChannel getChannel() {
        return null;
    }

    public FileDescriptor getFileDescriptor$() {
        return this.impl.getFileDescriptor();
    }

    SocketImpl getImpl() throws SocketException {
        if (!this.created) {
            this.createImpl(true);
        }
        return this.impl;
    }

    public InetAddress getInetAddress() {
        if (!this.isConnected()) {
            return null;
        }
        try {
            InetAddress inetAddress = this.getImpl().getInetAddress();
            return inetAddress;
        }
        catch (SocketException socketException) {
            return null;
        }
    }

    public InputStream getInputStream() throws IOException {
        if (!this.isClosed()) {
            if (this.isConnected()) {
                if (!this.isInputShutdown()) {
                    try {
                        PrivilegedExceptionAction<InputStream> privilegedExceptionAction = new PrivilegedExceptionAction<InputStream>(){

                            @Override
                            public InputStream run() throws IOException {
                                return Socket.this.impl.getInputStream();
                            }
                        };
                        privilegedExceptionAction = AccessController.doPrivileged(privilegedExceptionAction);
                        return privilegedExceptionAction;
                    }
                    catch (PrivilegedActionException privilegedActionException) {
                        throw (IOException)privilegedActionException.getException();
                    }
                }
                throw new SocketException("Socket input is shutdown");
            }
            throw new SocketException("Socket is not connected");
        }
        throw new SocketException("Socket is closed");
    }

    public boolean getKeepAlive() throws SocketException {
        if (!this.isClosed()) {
            return (Boolean)this.getImpl().getOption(8);
        }
        throw new SocketException("Socket is closed");
    }

    public InetAddress getLocalAddress() {
        Object object;
        InetAddress inetAddress;
        block7 : {
            if (!this.isBound()) {
                return InetAddress.anyLocalAddress();
            }
            inetAddress = (InetAddress)this.getImpl().getOption(15);
            object = System.getSecurityManager();
            if (object == null) break block7;
            ((SecurityManager)object).checkConnect(inetAddress.getHostAddress(), -1);
        }
        object = inetAddress;
        try {
            if (inetAddress.isAnyLocalAddress()) {
                object = InetAddress.anyLocalAddress();
            }
        }
        catch (Exception exception) {
            object = InetAddress.anyLocalAddress();
        }
        catch (SecurityException securityException) {
            object = InetAddress.getLoopbackAddress();
        }
        return object;
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
        return new InetSocketAddress(this.getLocalAddress(), this.getLocalPort());
    }

    public boolean getOOBInline() throws SocketException {
        if (!this.isClosed()) {
            return (Boolean)this.getImpl().getOption(4099);
        }
        throw new SocketException("Socket is closed");
    }

    public OutputStream getOutputStream() throws IOException {
        if (!this.isClosed()) {
            if (this.isConnected()) {
                if (!this.isOutputShutdown()) {
                    try {
                        PrivilegedExceptionAction<OutputStream> privilegedExceptionAction = new PrivilegedExceptionAction<OutputStream>(){

                            @Override
                            public OutputStream run() throws IOException {
                                return Socket.this.impl.getOutputStream();
                            }
                        };
                        privilegedExceptionAction = AccessController.doPrivileged(privilegedExceptionAction);
                        return privilegedExceptionAction;
                    }
                    catch (PrivilegedActionException privilegedActionException) {
                        throw (IOException)privilegedActionException.getException();
                    }
                }
                throw new SocketException("Socket output is shutdown");
            }
            throw new SocketException("Socket is not connected");
        }
        throw new SocketException("Socket is closed");
    }

    public int getPort() {
        if (!this.isConnected()) {
            return 0;
        }
        try {
            int n = this.getImpl().getPort();
            return n;
        }
        catch (SocketException socketException) {
            return -1;
        }
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

    public SocketAddress getRemoteSocketAddress() {
        if (!this.isConnected()) {
            return null;
        }
        return new InetSocketAddress(this.getInetAddress(), this.getPort());
    }

    public boolean getReuseAddress() throws SocketException {
        if (!this.isClosed()) {
            return (Boolean)this.getImpl().getOption(4);
        }
        throw new SocketException("Socket is closed");
    }

    public int getSendBufferSize() throws SocketException {
        synchronized (this) {
            block6 : {
                if (this.isClosed()) break block6;
                int n = 0;
                Object object = this.getImpl().getOption(4097);
                if (object instanceof Integer) {
                    n = (Integer)object;
                }
                return n;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    public int getSoLinger() throws SocketException {
        if (!this.isClosed()) {
            Object object = this.getImpl().getOption(128);
            if (object instanceof Integer) {
                return (Integer)object;
            }
            return -1;
        }
        throw new SocketException("Socket is closed");
    }

    public int getSoTimeout() throws SocketException {
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

    public boolean getTcpNoDelay() throws SocketException {
        if (!this.isClosed()) {
            return (Boolean)this.getImpl().getOption(1);
        }
        throw new SocketException("Socket is closed");
    }

    public int getTrafficClass() throws SocketException {
        if (!this.isClosed()) {
            return (Integer)this.getImpl().getOption(3);
        }
        throw new SocketException("Socket is closed");
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

    public boolean isConnected() {
        boolean bl = this.connected || this.oldImpl;
        return bl;
    }

    public boolean isInputShutdown() {
        return this.shutIn;
    }

    public boolean isOutputShutdown() {
        return this.shutOut;
    }

    final void postAccept() {
        this.connected = true;
        this.created = true;
        this.bound = true;
    }

    public void sendUrgentData(int n) throws IOException {
        if (!this.isClosed()) {
            if (this.getImpl().supportsUrgentData()) {
                this.getImpl().sendUrgentData(n);
                return;
            }
            throw new SocketException("Urgent data not supported");
        }
        throw new SocketException("Socket is closed");
    }

    void setBound() {
        this.bound = true;
    }

    void setConnected() {
        this.connected = true;
    }

    void setCreated() {
        this.created = true;
    }

    void setImpl() {
        Object object = factory;
        if (object != null) {
            this.impl = object.createSocketImpl();
            this.checkOldImpl();
        } else {
            this.impl = new SocksSocketImpl();
        }
        object = this.impl;
        if (object != null) {
            ((SocketImpl)object).setSocket(this);
        }
    }

    public void setKeepAlive(boolean bl) throws SocketException {
        if (!this.isClosed()) {
            this.getImpl().setOption(8, bl);
            return;
        }
        throw new SocketException("Socket is closed");
    }

    public void setOOBInline(boolean bl) throws SocketException {
        if (!this.isClosed()) {
            this.getImpl().setOption(4099, bl);
            return;
        }
        throw new SocketException("Socket is closed");
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
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("invalid receive size");
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSendBufferSize(int n) throws SocketException {
        synchronized (this) {
            Throwable throwable2;
            if (n > 0) {
                try {
                    if (!this.isClosed()) {
                        SocketImpl socketImpl = this.getImpl();
                        Integer n2 = new Integer(n);
                        socketImpl.setOption(4097, n2);
                        return;
                    }
                    SocketException socketException = new SocketException("Socket is closed");
                    throw socketException;
                }
                catch (Throwable throwable2) {}
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("negative send size");
                throw illegalArgumentException;
            }
            throw throwable2;
        }
    }

    public void setSoLinger(boolean bl, int n) throws SocketException {
        block4 : {
            block7 : {
                block6 : {
                    block5 : {
                        if (this.isClosed()) break block4;
                        if (bl) break block5;
                        this.getImpl().setOption(128, new Boolean(bl));
                        break block6;
                    }
                    if (n < 0) break block7;
                    int n2 = n;
                    if (n > 65535) {
                        n2 = 65535;
                    }
                    this.getImpl().setOption(128, new Integer(n2));
                }
                return;
            }
            throw new IllegalArgumentException("invalid value for SO_LINGER");
        }
        throw new SocketException("Socket is closed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setSoTimeout(int n) throws SocketException {
        synchronized (this) {
            if (this.isClosed()) {
                SocketException socketException = new SocketException("Socket is closed");
                throw socketException;
            }
            if (n >= 0) {
                SocketImpl socketImpl = this.getImpl();
                Integer n2 = new Integer(n);
                socketImpl.setOption(4102, n2);
                return;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("timeout can't be negative");
            throw illegalArgumentException;
        }
    }

    public void setTcpNoDelay(boolean bl) throws SocketException {
        if (!this.isClosed()) {
            this.getImpl().setOption(1, bl);
            return;
        }
        throw new SocketException("Socket is closed");
    }

    public void setTrafficClass(int n) throws SocketException {
        if (n >= 0 && n <= 255) {
            if (!this.isClosed()) {
                SocketException socketException2;
                block4 : {
                    try {
                        this.getImpl().setOption(3, n);
                    }
                    catch (SocketException socketException2) {
                        if (!this.isConnected()) break block4;
                    }
                    return;
                }
                throw socketException2;
            }
            throw new SocketException("Socket is closed");
        }
        throw new IllegalArgumentException("tc is not in range 0 -- 255");
    }

    public void shutdownInput() throws IOException {
        if (!this.isClosed()) {
            if (this.isConnected()) {
                if (!this.isInputShutdown()) {
                    this.getImpl().shutdownInput();
                    this.shutIn = true;
                    return;
                }
                throw new SocketException("Socket input is already shutdown");
            }
            throw new SocketException("Socket is not connected");
        }
        throw new SocketException("Socket is closed");
    }

    public void shutdownOutput() throws IOException {
        if (!this.isClosed()) {
            if (this.isConnected()) {
                if (!this.isOutputShutdown()) {
                    this.getImpl().shutdownOutput();
                    this.shutOut = true;
                    return;
                }
                throw new SocketException("Socket output is already shutdown");
            }
            throw new SocketException("Socket is not connected");
        }
        throw new SocketException("Socket is closed");
    }

    public String toString() {
        try {
            if (this.isConnected()) {
                CharSequence charSequence = new StringBuilder();
                charSequence.append("Socket[address=");
                charSequence.append(this.getImpl().getInetAddress());
                charSequence.append(",port=");
                charSequence.append(this.getImpl().getPort());
                charSequence.append(",localPort=");
                charSequence.append(this.getImpl().getLocalPort());
                charSequence.append("]");
                charSequence = charSequence.toString();
                return charSequence;
            }
        }
        catch (SocketException socketException) {
            // empty catch block
        }
        return "Socket[unconnected]";
    }

}

