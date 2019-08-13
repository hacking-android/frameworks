/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 *  libcore.io.IoBridge
 *  libcore.io.IoUtils
 */
package java.net;

import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocketImpl;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.AccessController;
import java.util.Enumeration;
import libcore.io.IoBridge;
import libcore.io.IoUtils;
import sun.net.ResourceManager;
import sun.security.action.GetPropertyAction;

abstract class AbstractPlainDatagramSocketImpl
extends DatagramSocketImpl {
    private static final boolean connectDisabled;
    private static final String os;
    boolean connected = false;
    protected InetAddress connectedAddress = null;
    private int connectedPort = -1;
    private final CloseGuard guard = CloseGuard.get();
    int timeout = 0;
    private int trafficClass = 0;

    static {
        os = AccessController.doPrivileged(new GetPropertyAction("os.name"));
        connectDisabled = os.contains("OS X");
    }

    AbstractPlainDatagramSocketImpl() {
    }

    static InetAddress getNIFirstAddress(int n) throws SocketException {
        Enumeration<InetAddress> enumeration;
        if (n > 0 && (enumeration = NetworkInterface.getByIndex(n).getInetAddresses()).hasMoreElements()) {
            return enumeration.nextElement();
        }
        return InetAddress.anyLocalAddress();
    }

    @Override
    protected void bind(int n, InetAddress inetAddress) throws SocketException {
        synchronized (this) {
            this.bind0(n, inetAddress);
            return;
        }
    }

    protected abstract void bind0(int var1, InetAddress var2) throws SocketException;

    @Override
    protected void close() {
        this.guard.close();
        if (this.fd != null) {
            this.datagramSocketClose();
            ResourceManager.afterUdpClose();
            this.fd = null;
        }
    }

    @Override
    protected void connect(InetAddress inetAddress, int n) throws SocketException {
        BlockGuard.getThreadPolicy().onNetwork();
        this.connect0(inetAddress, n);
        this.connectedAddress = inetAddress;
        this.connectedPort = n;
        this.connected = true;
    }

    protected abstract void connect0(InetAddress var1, int var2) throws SocketException;

    @Override
    protected void create() throws SocketException {
        synchronized (this) {
            block6 : {
                FileDescriptor fileDescriptor;
                ResourceManager.beforeUdpCreate();
                this.fd = fileDescriptor = new FileDescriptor();
                try {
                    this.datagramSocketCreate();
                    if (this.fd == null || !this.fd.valid()) break block6;
                }
                catch (SocketException socketException) {
                    ResourceManager.afterUdpClose();
                    this.fd = null;
                    throw socketException;
                }
                this.guard.open("close");
                IoUtils.setFdOwner((FileDescriptor)this.fd, (Object)this);
            }
            return;
        }
    }

    @Override
    int dataAvailable() {
        try {
            int n = IoBridge.available((FileDescriptor)this.fd);
            return n;
        }
        catch (IOException iOException) {
            return -1;
        }
    }

    protected abstract void datagramSocketClose();

    protected abstract void datagramSocketCreate() throws SocketException;

    @Override
    protected void disconnect() {
        this.disconnect0(this.connectedAddress.holder().getFamily());
        this.connected = false;
        this.connectedAddress = null;
        this.connectedPort = -1;
    }

    protected abstract void disconnect0(int var1);

    protected void finalize() {
        CloseGuard closeGuard = this.guard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        this.close();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object getOption(int n) throws SocketException {
        Object object;
        Object object2;
        if (this.isClosed()) throw new SocketException("Socket Closed");
        if (n != 3) {
            Object object3;
            if (n != 4 && n != 15 && n != 16 && n != 18) {
                if (n == 4102) return new Integer(this.timeout);
                if (n != 31 && n != 32 && n != 4097 && n != 4098) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid option: ");
                    stringBuilder.append(n);
                    throw new SocketException(stringBuilder.toString());
                }
            }
            object2 = object3 = this.socketGetOption(n);
            if (n != 16) return object2;
            return AbstractPlainDatagramSocketImpl.getNIFirstAddress((Integer)object3);
        }
        object2 = object = this.socketGetOption(n);
        if ((Integer)object != -1) return object2;
        return new Integer(this.trafficClass);
    }

    @Deprecated
    @Override
    protected abstract byte getTTL() throws IOException;

    @Override
    protected abstract int getTimeToLive() throws IOException;

    protected boolean isClosed() {
        boolean bl = this.fd == null;
        return bl;
    }

    @Override
    protected void join(InetAddress inetAddress) throws IOException {
        this.join(inetAddress, null);
    }

    protected abstract void join(InetAddress var1, NetworkInterface var2) throws IOException;

    @Override
    protected void joinGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        if (socketAddress != null && socketAddress instanceof InetSocketAddress) {
            this.join(((InetSocketAddress)socketAddress).getAddress(), networkInterface);
            return;
        }
        throw new IllegalArgumentException("Unsupported address type");
    }

    @Override
    protected void leave(InetAddress inetAddress) throws IOException {
        this.leave(inetAddress, null);
    }

    protected abstract void leave(InetAddress var1, NetworkInterface var2) throws IOException;

    @Override
    protected void leaveGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        if (socketAddress != null && socketAddress instanceof InetSocketAddress) {
            this.leave(((InetSocketAddress)socketAddress).getAddress(), networkInterface);
            return;
        }
        throw new IllegalArgumentException("Unsupported address type");
    }

    protected boolean nativeConnectDisabled() {
        return connectDisabled;
    }

    @Override
    protected abstract int peek(InetAddress var1) throws IOException;

    @Override
    protected abstract int peekData(DatagramPacket var1) throws IOException;

    @Override
    protected void receive(DatagramPacket datagramPacket) throws IOException {
        synchronized (this) {
            this.receive0(datagramPacket);
            return;
        }
    }

    protected abstract void receive0(DatagramPacket var1) throws IOException;

    @Override
    protected abstract void send(DatagramPacket var1) throws IOException;

    @Override
    public void setOption(int n, Object object) throws SocketException {
        block8 : {
            block24 : {
                Object object2;
                block18 : {
                    block9 : {
                        block23 : {
                            block10 : {
                                block11 : {
                                    block22 : {
                                        block12 : {
                                            block21 : {
                                                block13 : {
                                                    block14 : {
                                                        block20 : {
                                                            block15 : {
                                                                block19 : {
                                                                    block16 : {
                                                                        block17 : {
                                                                            if (this.isClosed()) break block8;
                                                                            if (n == 3) break block9;
                                                                            if (n == 4) break block10;
                                                                            if (n == 15) break block11;
                                                                            if (n == 16) break block12;
                                                                            if (n == 18) break block13;
                                                                            if (n == 4102) break block14;
                                                                            if (n == 31) break block15;
                                                                            if (n == 32) break block16;
                                                                            if (n != 4097 && n != 4098) {
                                                                                object = new StringBuilder();
                                                                                ((StringBuilder)object).append("invalid option: ");
                                                                                ((StringBuilder)object).append(n);
                                                                                throw new SocketException(((StringBuilder)object).toString());
                                                                            }
                                                                            if (object == null || !(object instanceof Integer) || (Integer)object < 0) break block17;
                                                                            object2 = object;
                                                                            break block18;
                                                                        }
                                                                        throw new SocketException("bad argument for SO_SNDBUF or SO_RCVBUF");
                                                                    }
                                                                    if (object == null || !(object instanceof Boolean)) break block19;
                                                                    object2 = object;
                                                                    break block18;
                                                                }
                                                                throw new SocketException("bad argument for SO_BROADCAST");
                                                            }
                                                            if (object == null || !(object instanceof Integer) && !(object instanceof NetworkInterface)) break block20;
                                                            object2 = object;
                                                            if (object instanceof NetworkInterface) {
                                                                object2 = new Integer(((NetworkInterface)object).getIndex());
                                                            }
                                                            break block18;
                                                        }
                                                        throw new SocketException("bad argument for IP_MULTICAST_IF2");
                                                    }
                                                    if (object != null && object instanceof Integer) {
                                                        n = (Integer)object;
                                                        if (n >= 0) {
                                                            this.timeout = n;
                                                            return;
                                                        }
                                                        throw new IllegalArgumentException("timeout < 0");
                                                    }
                                                    throw new SocketException("bad argument for SO_TIMEOUT");
                                                }
                                                if (object == null || !(object instanceof Boolean)) break block21;
                                                object2 = object;
                                                break block18;
                                            }
                                            throw new SocketException("bad argument for IP_MULTICAST_LOOP");
                                        }
                                        if (object == null || !(object instanceof InetAddress)) break block22;
                                        object2 = object;
                                        break block18;
                                    }
                                    throw new SocketException("bad argument for IP_MULTICAST_IF");
                                }
                                throw new SocketException("Cannot re-bind Socket");
                            }
                            if (object == null || !(object instanceof Boolean)) break block23;
                            object2 = object;
                            break block18;
                        }
                        throw new SocketException("bad argument for SO_REUSEADDR");
                    }
                    if (object == null || !(object instanceof Integer)) break block24;
                    this.trafficClass = (Integer)object;
                    object2 = object;
                }
                this.socketSetOption(n, object2);
                return;
            }
            throw new SocketException("bad argument for IP_TOS");
        }
        throw new SocketException("Socket Closed");
    }

    @Deprecated
    @Override
    protected abstract void setTTL(byte var1) throws IOException;

    @Override
    protected abstract void setTimeToLive(int var1) throws IOException;

    protected abstract Object socketGetOption(int var1) throws SocketException;

    protected abstract void socketSetOption(int var1, Object var2) throws SocketException;
}

