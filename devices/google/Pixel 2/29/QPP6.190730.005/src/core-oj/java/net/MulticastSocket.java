/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.DatagramSocketImpl;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

public class MulticastSocket
extends DatagramSocket {
    private InetAddress infAddress = null;
    private Object infLock = new Object();
    private boolean interfaceSet;
    private Object ttlLock = new Object();

    public MulticastSocket() throws IOException {
        this(new InetSocketAddress(0));
    }

    public MulticastSocket(int n) throws IOException {
        this(new InetSocketAddress(n));
    }

    public MulticastSocket(SocketAddress socketAddress) throws IOException {
        super((SocketAddress)null);
        this.setReuseAddress(true);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InetAddress getInterface() throws SocketException {
        if (this.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        Object object = this.infLock;
        synchronized (object) {
            InetAddress inetAddress = (InetAddress)this.getImpl().getOption(16);
            if (this.infAddress == null) {
                return inetAddress;
            }
            if (inetAddress.equals(this.infAddress)) {
                return inetAddress;
            }
            try {
                Enumeration<InetAddress> enumeration = NetworkInterface.getByInetAddress(inetAddress).getInetAddresses();
                do {
                    if (enumeration.hasMoreElements()) continue;
                    this.infAddress = null;
                    return inetAddress;
                } while (!enumeration.nextElement().equals(this.infAddress));
                return this.infAddress;
            }
            catch (Exception exception) {
                return inetAddress;
            }
        }
    }

    public boolean getLoopbackMode() throws SocketException {
        return (Boolean)this.getImpl().getOption(18);
    }

    public NetworkInterface getNetworkInterface() throws SocketException {
        InetAddress[] arrinetAddress = (InetAddress[])this.getImpl().getOption(31);
        if (arrinetAddress.intValue() == 0) {
            arrinetAddress = new InetAddress[]{InetAddress.anyLocalAddress()};
            return new NetworkInterface(arrinetAddress[0].getHostName(), 0, arrinetAddress);
        }
        return NetworkInterface.getByIndex(arrinetAddress.intValue());
    }

    @Deprecated
    public byte getTTL() throws IOException {
        if (!this.isClosed()) {
            return this.getImpl().getTTL();
        }
        throw new SocketException("Socket is closed");
    }

    public int getTimeToLive() throws IOException {
        if (!this.isClosed()) {
            return this.getImpl().getTimeToLive();
        }
        throw new SocketException("Socket is closed");
    }

    public void joinGroup(InetAddress inetAddress) throws IOException {
        if (!this.isClosed()) {
            this.checkAddress(inetAddress, "joinGroup");
            Object object = System.getSecurityManager();
            if (object != null) {
                ((SecurityManager)object).checkMulticast(inetAddress);
            }
            if (inetAddress.isMulticastAddress()) {
                object = NetworkInterface.getDefault();
                if (!this.interfaceSet && object != null) {
                    this.setNetworkInterface((NetworkInterface)object);
                }
                this.getImpl().join(inetAddress);
                return;
            }
            throw new SocketException("Not a multicast address");
        }
        throw new SocketException("Socket is closed");
    }

    public void joinGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        if (!this.isClosed()) {
            if (socketAddress != null && socketAddress instanceof InetSocketAddress) {
                if (!this.oldImpl) {
                    this.checkAddress(((InetSocketAddress)socketAddress).getAddress(), "joinGroup");
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkMulticast(((InetSocketAddress)socketAddress).getAddress());
                    }
                    if (((InetSocketAddress)socketAddress).getAddress().isMulticastAddress()) {
                        this.getImpl().joinGroup(socketAddress, networkInterface);
                        return;
                    }
                    throw new SocketException("Not a multicast address");
                }
                throw new UnsupportedOperationException();
            }
            throw new IllegalArgumentException("Unsupported address type");
        }
        throw new SocketException("Socket is closed");
    }

    public void leaveGroup(InetAddress inetAddress) throws IOException {
        if (!this.isClosed()) {
            this.checkAddress(inetAddress, "leaveGroup");
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkMulticast(inetAddress);
            }
            if (inetAddress.isMulticastAddress()) {
                this.getImpl().leave(inetAddress);
                return;
            }
            throw new SocketException("Not a multicast address");
        }
        throw new SocketException("Socket is closed");
    }

    public void leaveGroup(SocketAddress socketAddress, NetworkInterface networkInterface) throws IOException {
        if (!this.isClosed()) {
            if (socketAddress != null && socketAddress instanceof InetSocketAddress) {
                if (!this.oldImpl) {
                    this.checkAddress(((InetSocketAddress)socketAddress).getAddress(), "leaveGroup");
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkMulticast(((InetSocketAddress)socketAddress).getAddress());
                    }
                    if (((InetSocketAddress)socketAddress).getAddress().isMulticastAddress()) {
                        this.getImpl().leaveGroup(socketAddress, networkInterface);
                        return;
                    }
                    throw new SocketException("Not a multicast address");
                }
                throw new UnsupportedOperationException();
            }
            throw new IllegalArgumentException("Unsupported address type");
        }
        throw new SocketException("Socket is closed");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public void send(DatagramPacket var1_1, byte var2_2) throws IOException {
        block16 : {
            block14 : {
                block15 : {
                    block13 : {
                        if (this.isClosed() != false) throw new SocketException("Socket is closed");
                        this.checkAddress(var1_1.getAddress(), "send");
                        var3_3 = this.ttlLock;
                        // MONITORENTER : var3_3
                        // MONITORENTER : var1_1
                        if (this.connectState != 0) break block13;
                        var4_4 = System.getSecurityManager();
                        if (var4_4 != null) {
                            if (var1_1.getAddress().isMulticastAddress()) {
                                var4_4.checkMulticast(var1_1.getAddress(), var2_2);
                            } else {
                                var4_4.checkConnect(var1_1.getAddress().getHostAddress(), var1_1.getPort());
                            }
                        }
                        break block14;
                    }
                    var4_4 = var1_1.getAddress();
                    if (var4_4 != null) break block15;
                    var1_1.setAddress(this.connectedAddress);
                    var1_1.setPort(this.connectedPort);
                    break block14;
                }
                if (!var4_4.equals(this.connectedAddress) || var1_1.getPort() != this.connectedPort) break block16;
            }
            var5_6 = this.getTTL();
            if (var2_2 == var5_6) ** GOTO lbl27
            try {
                this.getImpl().setTTL(var2_2);
lbl27: // 2 sources:
                this.getImpl().send(var1_1);
                return;
            }
            finally {
                if (var2_2 != var5_6) {
                    this.getImpl().setTTL(var5_6);
                }
            }
        }
        var4_4 = new SecurityException("connected address and packet address differ");
        throw var4_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setInterface(InetAddress inetAddress) throws SocketException {
        if (!this.isClosed()) {
            this.checkAddress(inetAddress, "setInterface");
            Object object = this.infLock;
            synchronized (object) {
                this.getImpl().setOption(16, inetAddress);
                this.infAddress = inetAddress;
                this.interfaceSet = true;
                return;
            }
        }
        throw new SocketException("Socket is closed");
    }

    public void setLoopbackMode(boolean bl) throws SocketException {
        this.getImpl().setOption(18, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setNetworkInterface(NetworkInterface networkInterface) throws SocketException {
        Object object = this.infLock;
        synchronized (object) {
            this.getImpl().setOption(31, networkInterface);
            this.infAddress = null;
            this.interfaceSet = true;
            return;
        }
    }

    @Deprecated
    public void setTTL(byte by) throws IOException {
        if (!this.isClosed()) {
            this.getImpl().setTTL(by);
            return;
        }
        throw new SocketException("Socket is closed");
    }

    public void setTimeToLive(int n) throws IOException {
        if (n >= 0 && n <= 255) {
            if (!this.isClosed()) {
                this.getImpl().setTimeToLive(n);
                return;
            }
            throw new SocketException("Socket is closed");
        }
        throw new IllegalArgumentException("ttl out of range");
    }
}

