/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.AbstractPlainDatagramSocketImpl;
import java.net.DatagramPacket;
import java.net.DatagramSocketImpl;
import java.net.DatagramSocketImplFactory;
import java.net.DefaultDatagramSocketImplFactory;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class DatagramSocket
implements Closeable {
    static final int ST_CONNECTED = 1;
    static final int ST_CONNECTED_NO_IMPL = 2;
    static final int ST_NOT_CONNECTED = 0;
    static DatagramSocketImplFactory factory;
    static Class<?> implClass;
    private boolean bound = false;
    private int bytesLeftToFilter;
    private Object closeLock = new Object();
    private boolean closed = false;
    int connectState = 0;
    InetAddress connectedAddress = null;
    int connectedPort = -1;
    private boolean created = false;
    private boolean explicitFilter = false;
    DatagramSocketImpl impl;
    boolean oldImpl = false;
    private SocketException pendingConnectException;

    static {
        implClass = null;
    }

    public DatagramSocket() throws SocketException {
        this(new InetSocketAddress(0));
    }

    public DatagramSocket(int n) throws SocketException {
        this(n, null);
    }

    public DatagramSocket(int n, InetAddress inetAddress) throws SocketException {
        this(new InetSocketAddress(inetAddress, n));
    }

    protected DatagramSocket(DatagramSocketImpl datagramSocketImpl) {
        if (datagramSocketImpl != null) {
            this.impl = datagramSocketImpl;
            this.checkOldImpl();
            return;
        }
        throw new NullPointerException();
    }

    public DatagramSocket(SocketAddress socketAddress) throws SocketException {
        this.createImpl();
        if (socketAddress != null) {
            try {
                this.bind(socketAddress);
            }
            finally {
                if (!this.isBound()) {
                    this.close();
                }
            }
        }
    }

    private boolean checkFiltering(DatagramPacket datagramPacket) throws SocketException {
        this.bytesLeftToFilter -= datagramPacket.getLength();
        if (this.bytesLeftToFilter > 0 && this.getImpl().dataAvailable() > 0) {
            return false;
        }
        this.explicitFilter = false;
        return true;
    }

    private void checkOldImpl() {
        if (this.impl == null) {
            return;
        }
        try {
            PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                @Override
                public Void run() throws NoSuchMethodException {
                    DatagramSocket.this.impl.getClass().getDeclaredMethod("peekData", DatagramPacket.class);
                    return null;
                }
            };
            AccessController.doPrivileged(privilegedExceptionAction);
        }
        catch (PrivilegedActionException privilegedActionException) {
            this.oldImpl = true;
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
    private void connectInternal(InetAddress var1_1, int var2_3) throws SocketException {
        block28 : {
            block29 : {
                block27 : {
                    block23 : {
                        block25 : {
                            block26 : {
                                block24 : {
                                    // MONITORENTER : this
                                    if (var2_2 < 0 || var2_2 > 65535) break block28;
                                    if (var1_1 == null) {
                                        var1_1 = new IllegalArgumentException("connect: null address");
                                        throw var1_1;
                                    }
                                    this.checkAddress((InetAddress)var1_1, "connect");
                                    var3_3 = this.isClosed();
                                    if (var3_3) {
                                        // MONITOREXIT : this
                                        return;
                                    }
                                    var4_4 = System.getSecurityManager();
                                    if (var4_4 != null) {
                                        if (var1_1.isMulticastAddress()) {
                                            var4_4.checkMulticast((InetAddress)var1_1);
                                        } else {
                                            var4_4.checkConnect(var1_1.getHostAddress(), (int)var2_2);
                                            var4_4.checkAccept(var1_1.getHostAddress(), (int)var2_2);
                                        }
                                    }
                                    if (!this.isBound()) {
                                        var4_4 = new InetSocketAddress(0);
                                        this.bind((SocketAddress)var4_4);
                                    }
                                    var3_3 = this.oldImpl;
                                    if (var3_3) break block23;
                                    try {
                                        var3_3 = this.impl instanceof AbstractPlainDatagramSocketImpl;
                                        if (!var3_3) break block24;
                                    }
                                    catch (Throwable var4_8) {}
                                    var3_3 = ((AbstractPlainDatagramSocketImpl)this.impl).nativeConnectDisabled();
                                    if (var3_3) break block23;
                                }
                                this.getImpl().connect((InetAddress)var1_1, (int)var2_2);
                                var3_3 = true;
                                this.connectState = 1;
                                var5_12 = this.getImpl().dataAvailable();
                                if (var5_12 == -1) break block25;
                                if (var5_12 > 0) break block26;
                                var3_3 = false;
                            }
                            try {
                                this.explicitFilter = var3_3;
                                if (this.explicitFilter) {
                                    this.bytesLeftToFilter = this.getReceiveBufferSize();
                                }
                                break block27;
                            }
                            catch (SocketException var4_5) {
                                ** GOTO lbl55
                            }
                        }
                        try {
                            var4_4 = new SocketException();
                            throw var4_4;
                        }
                        catch (SocketException var4_6) {
                            // empty catch block
                        }
lbl55: // 2 sources:
                        this.connectState = 2;
                        throw var4_7;
                        break block29;
                    }
                    try {
                        this.connectState = 2;
                    }
                    catch (Throwable var4_9) {
                        // empty catch block
                    }
                }
                this.connectedAddress = var1_1;
                this.connectedPort = var2_2;
                // MONITOREXIT : this
                return;
            }
            this.connectedAddress = var1_1;
            this.connectedPort = var2_2;
            throw var4_10;
        }
        var4_11 = new StringBuilder();
        var4_11.append("connect: ");
        var4_11.append((int)var2_2);
        var1_1 = new IllegalArgumentException(var4_11.toString());
        throw var1_1;
    }

    public static void setDatagramSocketImplFactory(DatagramSocketImplFactory object) throws IOException {
        synchronized (DatagramSocket.class) {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void bind(SocketAddress serializable) throws SocketException {
        synchronized (this) {
            if (this.isClosed()) {
                serializable = new SocketException("Socket is closed");
                throw serializable;
            }
            if (this.isBound()) {
                serializable = new SocketException("already bound");
                throw serializable;
            }
            Object object = serializable;
            if (serializable == null) {
                object = new InetSocketAddress(0);
            }
            if (!(object instanceof InetSocketAddress)) {
                serializable = new IllegalArgumentException("Unsupported address type!");
                throw serializable;
            }
            if (((InetSocketAddress)(object = (InetSocketAddress)object)).isUnresolved()) {
                serializable = new SocketException("Unresolved address");
                throw serializable;
            }
            serializable = ((InetSocketAddress)object).getAddress();
            int n = ((InetSocketAddress)object).getPort();
            this.checkAddress((InetAddress)serializable, "bind");
            object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkListen(n);
            }
            try {
                this.getImpl().bind(n, (InetAddress)serializable);
                this.bound = true;
            }
            catch (SocketException socketException) {
                this.getImpl().close();
                throw socketException;
            }
            return;
        }
    }

    void checkAddress(InetAddress serializable, String string) {
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.closeLock;
        synchronized (object) {
            if (this.isClosed()) {
                return;
            }
            this.impl.close();
            this.closed = true;
            return;
        }
    }

    public void connect(InetAddress inetAddress, int n) {
        try {
            this.connectInternal(inetAddress, n);
        }
        catch (SocketException socketException) {
            this.pendingConnectException = socketException;
        }
    }

    public void connect(SocketAddress socketAddress) throws SocketException {
        if (socketAddress != null) {
            if (socketAddress instanceof InetSocketAddress) {
                if (!((InetSocketAddress)(socketAddress = (InetSocketAddress)socketAddress)).isUnresolved()) {
                    this.connectInternal(((InetSocketAddress)socketAddress).getAddress(), ((InetSocketAddress)socketAddress).getPort());
                    return;
                }
                throw new SocketException("Unresolved address");
            }
            throw new IllegalArgumentException("Unsupported address type");
        }
        throw new IllegalArgumentException("Address can't be null");
    }

    void createImpl() throws SocketException {
        if (this.impl == null) {
            DatagramSocketImplFactory datagramSocketImplFactory = factory;
            if (datagramSocketImplFactory != null) {
                this.impl = datagramSocketImplFactory.createDatagramSocketImpl();
                this.checkOldImpl();
            } else {
                boolean bl = this instanceof MulticastSocket;
                this.impl = DefaultDatagramSocketImplFactory.createDatagramSocketImpl(bl);
                this.checkOldImpl();
            }
        }
        this.impl.create();
        this.impl.setDatagramSocket(this);
        this.created = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disconnect() {
        synchronized (this) {
            if (this.isClosed()) {
                return;
            }
            if (this.connectState == 1) {
                this.impl.disconnect();
            }
            this.connectedAddress = null;
            this.connectedPort = -1;
            this.connectState = 0;
            this.explicitFilter = false;
            return;
        }
    }

    public boolean getBroadcast() throws SocketException {
        synchronized (this) {
            block4 : {
                if (this.isClosed()) break block4;
                boolean bl = (Boolean)this.getImpl().getOption(32);
                return bl;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    public DatagramChannel getChannel() {
        return null;
    }

    public FileDescriptor getFileDescriptor$() {
        return this.impl.fd;
    }

    DatagramSocketImpl getImpl() throws SocketException {
        if (!this.created) {
            this.createImpl();
        }
        return this.impl;
    }

    public InetAddress getInetAddress() {
        return this.connectedAddress;
    }

    public InetAddress getLocalAddress() {
        InetAddress inetAddress;
        block6 : {
            if (this.isClosed()) {
                return null;
            }
            Object object = (InetAddress)this.getImpl().getOption(15);
            inetAddress = object;
            if (((InetAddress)object).isAnyLocalAddress()) {
                inetAddress = InetAddress.anyLocalAddress();
            }
            if ((object = System.getSecurityManager()) == null) break block6;
            try {
                ((SecurityManager)object).checkConnect(inetAddress.getHostAddress(), -1);
            }
            catch (Exception exception) {
                inetAddress = InetAddress.anyLocalAddress();
            }
        }
        return inetAddress;
    }

    public int getLocalPort() {
        if (this.isClosed()) {
            return -1;
        }
        try {
            int n = this.getImpl().getLocalPort();
            return n;
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public SocketAddress getLocalSocketAddress() {
        if (this.isClosed()) {
            return null;
        }
        if (!this.isBound()) {
            return null;
        }
        return new InetSocketAddress(this.getLocalAddress(), this.getLocalPort());
    }

    public int getPort() {
        return this.connectedPort;
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
        synchronized (this) {
            block4 : {
                if (this.isClosed()) break block4;
                boolean bl = (Boolean)this.getImpl().getOption(4);
                return bl;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
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

    public int getSoTimeout() throws SocketException {
        synchronized (this) {
            block7 : {
                Object object;
                block8 : {
                    if (this.isClosed()) break block7;
                    object = this.getImpl();
                    if (object != null) break block8;
                    return 0;
                }
                object = this.getImpl().getOption(4102);
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

    public int getTrafficClass() throws SocketException {
        synchronized (this) {
            block4 : {
                if (this.isClosed()) break block4;
                int n = (Integer)this.getImpl().getOption(3);
                return n;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    public boolean isBound() {
        return this.bound;
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
        boolean bl = this.connectState != 0;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void receive(DatagramPacket datagramPacket) throws IOException {
        synchronized (this) {
            synchronized (datagramPacket) {
                Object object;
                Object object2;
                int n;
                if (!this.isBound()) {
                    object = new InetSocketAddress(0);
                    this.bind((SocketAddress)object);
                }
                if (this.pendingConnectException != null) {
                    object = new SocketException("Pending connect failure", this.pendingConnectException);
                    throw object;
                }
                if (this.connectState == 0 && (object2 = System.getSecurityManager()) != null) {
                    do {
                        if (!this.oldImpl) {
                            object = new DatagramPacket(new byte[1], 1);
                            n = this.getImpl().peekData((DatagramPacket)object);
                            object = ((DatagramPacket)object).getAddress().getHostAddress();
                        } else {
                            object = new InetAddress();
                            n = this.getImpl().peek((InetAddress)object);
                            object = ((InetAddress)object).getHostAddress();
                        }
                        try {
                            ((SecurityManager)object2).checkAccept((String)object, n);
                        }
                        catch (SecurityException securityException) {
                            object = new DatagramPacket(new byte[1], 1);
                            this.getImpl().receive((DatagramPacket)object);
                            continue;
                        }
                        break;
                    } while (true);
                }
                object2 = null;
                object = null;
                if (this.connectState == 2 || this.explicitFilter) {
                    int n2 = 0;
                    do {
                        object2 = object;
                        if (n2 != 0) break;
                        if (!this.oldImpl) {
                            object2 = new DatagramPacket(new byte[1], 1);
                            n = this.getImpl().peekData((DatagramPacket)object2);
                            object2 = ((DatagramPacket)object2).getAddress();
                        } else {
                            object2 = new InetAddress();
                            n = this.getImpl().peek((InetAddress)object2);
                        }
                        if (this.connectedAddress.equals(object2) && this.connectedPort == n) {
                            n = 1;
                        } else {
                            object2 = new DatagramPacket(new byte[1024], 1024);
                            this.getImpl().receive((DatagramPacket)object2);
                            object = object2;
                            n = n2;
                            if (this.explicitFilter) {
                                object = object2;
                                n = n2;
                                if (this.checkFiltering((DatagramPacket)object2)) {
                                    n = 1;
                                    object = object2;
                                }
                            }
                        }
                        n2 = n;
                    } while (true);
                }
                this.getImpl().receive(datagramPacket);
                if (this.explicitFilter && object2 == null) {
                    this.checkFiltering(datagramPacket);
                }
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void send(DatagramPacket datagramPacket) throws IOException {
        synchronized (datagramPacket) {
            Object object;
            block15 : {
                block13 : {
                    block14 : {
                        block12 : {
                            if (this.pendingConnectException != null) {
                                SocketException socketException = new SocketException("Pending connect failure", this.pendingConnectException);
                                throw socketException;
                            }
                            if (this.isClosed()) {
                                SocketException socketException = new SocketException("Socket is closed");
                                throw socketException;
                            }
                            this.checkAddress(datagramPacket.getAddress(), "send");
                            if (this.connectState != 0) break block12;
                            object = System.getSecurityManager();
                            if (object != null) {
                                if (datagramPacket.getAddress().isMulticastAddress()) {
                                    ((SecurityManager)object).checkMulticast(datagramPacket.getAddress());
                                } else {
                                    ((SecurityManager)object).checkConnect(datagramPacket.getAddress().getHostAddress(), datagramPacket.getPort());
                                }
                            }
                            break block13;
                        }
                        object = datagramPacket.getAddress();
                        if (object != null) break block14;
                        datagramPacket.setAddress(this.connectedAddress);
                        datagramPacket.setPort(this.connectedPort);
                        break block13;
                    }
                    if (!((InetAddress)object).equals(this.connectedAddress) || datagramPacket.getPort() != this.connectedPort) break block15;
                }
                if (!this.isBound()) {
                    object = new InetSocketAddress(0);
                    this.bind((SocketAddress)object);
                }
                this.getImpl().send(datagramPacket);
                return;
            }
            object = new IllegalArgumentException("connected address and packet address differ");
            throw object;
        }
    }

    public void setBroadcast(boolean bl) throws SocketException {
        synchronized (this) {
            if (!this.isClosed()) {
                this.getImpl().setOption(32, bl);
                return;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
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
                        DatagramSocketImpl datagramSocketImpl = this.getImpl();
                        Integer n2 = new Integer(n);
                        datagramSocketImpl.setOption(4098, n2);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setReuseAddress(boolean bl) throws SocketException {
        synchronized (this) {
            if (this.isClosed()) {
                SocketException socketException = new SocketException("Socket is closed");
                throw socketException;
            }
            if (this.oldImpl) {
                DatagramSocketImpl datagramSocketImpl = this.getImpl();
                int n = bl ? -1 : 0;
                Integer n2 = new Integer(n);
                datagramSocketImpl.setOption(4, n2);
            } else {
                this.getImpl().setOption(4, bl);
            }
            return;
        }
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
                        DatagramSocketImpl datagramSocketImpl = this.getImpl();
                        Integer n2 = new Integer(n);
                        datagramSocketImpl.setOption(4097, n2);
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

    public void setSoTimeout(int n) throws SocketException {
        synchronized (this) {
            if (!this.isClosed()) {
                DatagramSocketImpl datagramSocketImpl = this.getImpl();
                Integer n2 = new Integer(n);
                datagramSocketImpl.setOption(4102, n2);
                return;
            }
            SocketException socketException = new SocketException("Socket is closed");
            throw socketException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setTrafficClass(int n) throws SocketException {
        synchronized (this) {
            if (n >= 0 && n <= 255) {
                SocketException socketException3;
                block7 : {
                    boolean bl = this.isClosed();
                    if (bl) {
                        SocketException socketException2 = new SocketException("Socket is closed");
                        throw socketException2;
                    }
                    try {
                        this.getImpl().setOption(3, n);
                    }
                    catch (SocketException socketException3) {
                        bl = this.isConnected();
                        if (!bl) break block7;
                    }
                    return;
                }
                throw socketException3;
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("tc is not in range 0 -- 255");
            throw illegalArgumentException;
        }
    }

}

