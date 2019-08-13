/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 */
package sun.nio.ch;

import dalvik.annotation.optimization.ReachabilitySensitive;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.PortUnreachableException;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.nio.channels.MulticastChannel;
import java.nio.channels.NetworkChannel;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.UnsupportedAddressTypeException;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jdk.net.ExtendedSocketOptions;
import jdk.net.SocketFlow;
import sun.net.ExtendedOptionsImpl;
import sun.net.ResourceManager;
import sun.nio.ch.DatagramDispatcher;
import sun.nio.ch.DatagramSocketAdaptor;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.IOStatus;
import sun.nio.ch.IOUtil;
import sun.nio.ch.MembershipKeyImpl;
import sun.nio.ch.MembershipRegistry;
import sun.nio.ch.NativeDispatcher;
import sun.nio.ch.NativeThread;
import sun.nio.ch.Net;
import sun.nio.ch.SelChImpl;
import sun.nio.ch.SelectionKeyImpl;
import sun.nio.ch.SelectorImpl;
import sun.nio.ch.Util;

class DatagramChannelImpl
extends DatagramChannel
implements SelChImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ST_CONNECTED = 1;
    private static final int ST_KILLED = 2;
    private static final int ST_UNCONNECTED = 0;
    private static final int ST_UNINITIALIZED = -1;
    private static NativeDispatcher nd = new DatagramDispatcher();
    private InetAddress cachedSenderInetAddress;
    private int cachedSenderPort;
    private final ProtocolFamily family;
    @ReachabilitySensitive
    final FileDescriptor fd;
    private final int fdVal;
    @ReachabilitySensitive
    private final CloseGuard guard = CloseGuard.get();
    private boolean isReuseAddress;
    private InetSocketAddress localAddress;
    private final Object readLock = new Object();
    private volatile long readerThread = 0L;
    private MembershipRegistry registry;
    private InetSocketAddress remoteAddress;
    private boolean reuseAddressEmulated;
    private SocketAddress sender;
    private DatagramSocket socket;
    private int state = -1;
    private final Object stateLock = new Object();
    private final Object writeLock = new Object();
    private volatile long writerThread = 0L;

    static {
        DatagramChannelImpl.initIDs();
    }

    public DatagramChannelImpl(SelectorProvider object) throws IOException {
        super((SelectorProvider)object);
        ResourceManager.beforeUdpCreate();
        try {
            object = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
            this.family = object;
            this.fd = Net.socket(this.family, false);
            this.fdVal = IOUtil.fdVal(this.fd);
            this.state = 0;
            if (this.fd != null && this.fd.valid()) {
                this.guard.open("close");
            }
            return;
        }
        catch (IOException iOException) {
            ResourceManager.afterUdpClose();
            throw iOException;
        }
    }

    public DatagramChannelImpl(SelectorProvider object, FileDescriptor fileDescriptor) throws IOException {
        super((SelectorProvider)object);
        object = Net.isIPv6Available() ? StandardProtocolFamily.INET6 : StandardProtocolFamily.INET;
        this.family = object;
        this.fd = fileDescriptor;
        this.fdVal = IOUtil.fdVal(fileDescriptor);
        this.state = 0;
        this.localAddress = Net.localAddress(fileDescriptor);
        if (fileDescriptor != null && fileDescriptor.valid()) {
            this.guard.open("close");
        }
    }

    public DatagramChannelImpl(SelectorProvider object, ProtocolFamily protocolFamily) throws IOException {
        super((SelectorProvider)object);
        if (protocolFamily != StandardProtocolFamily.INET && protocolFamily != StandardProtocolFamily.INET6) {
            if (protocolFamily == null) {
                throw new NullPointerException("'family' is null");
            }
            throw new UnsupportedOperationException("Protocol family not supported");
        }
        if (protocolFamily == StandardProtocolFamily.INET6 && !Net.isIPv6Available()) {
            throw new UnsupportedOperationException("IPv6 not available");
        }
        this.family = protocolFamily;
        this.fd = Net.socket(protocolFamily, false);
        this.fdVal = IOUtil.fdVal(this.fd);
        this.state = 0;
        object = this.fd;
        if (object != null && ((FileDescriptor)object).valid()) {
            this.guard.open("close");
        }
    }

    private static native void disconnect0(FileDescriptor var0, boolean var1) throws IOException;

    private void ensureOpen() throws ClosedChannelException {
        if (this.isOpen()) {
            return;
        }
        throw new ClosedChannelException();
    }

    private static native void initIDs();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private MembershipKey innerJoin(InetAddress var1_1, NetworkInterface var2_4, InetAddress var3_5) throws IOException {
        block16 : {
            if (var1_1.isMulticastAddress() == false) throw new IllegalArgumentException("Group not a multicast address");
            if (var1_1 instanceof Inet4Address) {
                if (this.family == StandardProtocolFamily.INET6) {
                    if (Net.canIPv6SocketJoinIPv4Group() == false) throw new IllegalArgumentException("IPv6 socket cannot join IPv4 multicast group");
                }
            } else {
                if (var1_1 instanceof Inet6Address == false) throw new IllegalArgumentException("Address type not supported");
                if (this.family != StandardProtocolFamily.INET6) throw new IllegalArgumentException("Only IPv6 sockets can join IPv6 multicast group");
            }
            if (var3_4 != null) {
                if (var3_4.isAnyLocalAddress() != false) throw new IllegalArgumentException("Source address is a wildcard address");
                if (var3_4.isMulticastAddress() != false) throw new IllegalArgumentException("Source address is multicast address");
                if (var3_4.getClass() != var1_1.getClass()) throw new IllegalArgumentException("Source address is different type to group");
            }
            if ((var4_5 /* !! */  = System.getSecurityManager()) != null) {
                var4_5 /* !! */ .checkMulticast((InetAddress)var1_1);
            }
            var5_6 = this.stateLock;
            // MONITORENTER : var5_6
            if (!this.isOpen()) ** GOTO lbl52
            if (this.registry != null) break block16;
            var4_5 /* !! */  = new MembershipRegistry();
            this.registry = var4_5 /* !! */ ;
            ** GOTO lbl28
        }
        var4_5 /* !! */  = this.registry;
        var4_5 /* !! */  = var4_5 /* !! */ .checkMembership((InetAddress)var1_1, (NetworkInterface)var2_3, (InetAddress)var3_4);
        if (var4_5 /* !! */  != null) {
            // MONITOREXIT : var5_6
            return var4_5 /* !! */ ;
        }
lbl28: // 3 sources:
        if (this.family == StandardProtocolFamily.INET6 && (var1_1 instanceof Inet6Address || Net.canJoin6WithIPv4Group())) {
            var6_7 = var2_3.getIndex();
            if (var6_7 == -1) {
                var1_1 = new IOException("Network interface cannot be identified");
                throw var1_1;
            }
            var7_9 = Net.inet6AsByteArray((InetAddress)var1_1);
            if (Net.join6(this.fd, var7_9, var6_7, var4_5 /* !! */  = var3_4 == null ? null : Net.inet6AsByteArray((InetAddress)var3_4)) == -2) {
                var1_1 = new UnsupportedOperationException();
                throw var1_1;
            }
            var8_10 = new MembershipKeyImpl.Type6(this, (InetAddress)var1_1, (NetworkInterface)var2_3, (InetAddress)var3_4, var7_9, var6_7, var4_5 /* !! */ );
            var1_1 = var8_10;
        } else {
            var4_5 /* !! */  = Net.anyInet4Address((NetworkInterface)var2_3);
            if (var4_5 /* !! */  == null) {
                var1_1 = new IOException("Network interface not configured for IPv4");
                throw var1_1;
            }
            var9_11 = Net.inet4AsInt((InetAddress)var1_1);
            if (Net.join4(this.fd, var9_11, var10_12 = Net.inet4AsInt((InetAddress)var4_5 /* !! */ ), var6_8 = var3_4 == null ? 0 : Net.inet4AsInt((InetAddress)var3_4)) == -2) {
                var1_1 = new UnsupportedOperationException();
                throw var1_1;
            }
            var1_1 = new MembershipKeyImpl.Type4(this, (InetAddress)var1_1, (NetworkInterface)var2_3, (InetAddress)var3_4, var9_11, var10_12, var6_8);
        }
        this.registry.add((MembershipKeyImpl)var1_1);
        // MONITOREXIT : var5_6
        return var1_1;
lbl52: // 1 sources:
        var1_1 = new ClosedChannelException();
        throw var1_1;
    }

    private int receive(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws IOException {
        int n;
        ByteBuffer byteBuffer2;
        block5 : {
            int n2;
            n = byteBuffer.position();
            n2 = n <= (n2 = byteBuffer.limit()) ? (n2 -= n) : 0;
            if (byteBuffer instanceof DirectBuffer && n2 > 0) {
                return this.receiveIntoNativeBuffer(fileDescriptor, byteBuffer, n2, n);
            }
            n = Math.max(n2, 1);
            byteBuffer2 = Util.getTemporaryDirectBuffer(n);
            BlockGuard.getThreadPolicy().onNetwork();
            n = this.receiveIntoNativeBuffer(fileDescriptor, byteBuffer2, n, 0);
            byteBuffer2.flip();
            if (n <= 0 || n2 <= 0) break block5;
            byteBuffer.put(byteBuffer2);
        }
        return n;
        finally {
            Util.releaseTemporaryDirectBuffer(byteBuffer2);
        }
    }

    private native int receive0(FileDescriptor var1, long var2, int var4, boolean var5) throws IOException;

    private int receiveIntoNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, int n2) throws IOException {
        n = this.receive0(fileDescriptor, ((DirectBuffer)((Object)byteBuffer)).address() + (long)n2, n, this.isConnected());
        if (n > 0) {
            byteBuffer.position(n2 + n);
        }
        return n;
    }

    private int send(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, InetSocketAddress inetSocketAddress) throws IOException {
        int n;
        ByteBuffer byteBuffer2;
        block5 : {
            if (byteBuffer instanceof DirectBuffer) {
                return this.sendFromNativeBuffer(fileDescriptor, byteBuffer, inetSocketAddress);
            }
            int n2 = byteBuffer.position();
            n = n2 <= (n = byteBuffer.limit()) ? (n -= n2) : 0;
            byteBuffer2 = Util.getTemporaryDirectBuffer(n);
            byteBuffer2.put(byteBuffer);
            byteBuffer2.flip();
            byteBuffer.position(n2);
            n = this.sendFromNativeBuffer(fileDescriptor, byteBuffer2, inetSocketAddress);
            if (n <= 0) break block5;
            byteBuffer.position(n2 + n);
        }
        return n;
        finally {
            Util.releaseTemporaryDirectBuffer(byteBuffer2);
        }
    }

    private native int send0(boolean var1, FileDescriptor var2, long var3, int var5, InetAddress var6, int var7) throws IOException;

    private int sendFromNativeBuffer(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, InetSocketAddress inetSocketAddress) throws IOException {
        PortUnreachableException portUnreachableException2;
        block4 : {
            int n = byteBuffer.position();
            int n2 = byteBuffer.limit();
            boolean bl = false;
            n2 = n <= n2 ? (n2 -= n) : 0;
            if (this.family != StandardProtocolFamily.INET) {
                bl = true;
            }
            try {
                int n3;
                n2 = n3 = this.send0(bl, fileDescriptor, ((DirectBuffer)((Object)byteBuffer)).address() + (long)n, n2, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
            }
            catch (PortUnreachableException portUnreachableException2) {
                if (this.isConnected()) break block4;
            }
            if (n2 > 0) {
                byteBuffer.position(n + n2);
            }
            return n2;
        }
        throw portUnreachableException2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DatagramChannel bind(SocketAddress object) throws IOException {
        Object object2 = this.readLock;
        synchronized (object2) {
            Object object3 = this.writeLock;
            synchronized (object3) {
                Object object4 = this.stateLock;
                synchronized (object4) {
                    Object object5;
                    this.ensureOpen();
                    if (this.localAddress != null) {
                        object = new AlreadyBoundException();
                        throw object;
                    }
                    if (object == null) {
                        object = this.family == StandardProtocolFamily.INET ? new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0) : new InetSocketAddress(0);
                    } else {
                        object = object5 = Net.checkAddress((SocketAddress)object);
                        if (this.family == StandardProtocolFamily.INET) {
                            if (!(((InetSocketAddress)object5).getAddress() instanceof Inet4Address)) {
                                object = new UnsupportedAddressTypeException();
                                throw object;
                            }
                            object = object5;
                        }
                    }
                    object5 = System.getSecurityManager();
                    if (object5 != null) {
                        ((SecurityManager)object5).checkListen(((InetSocketAddress)object).getPort());
                    }
                    Net.bind(this.family, this.fd, ((InetSocketAddress)object).getAddress(), ((InetSocketAddress)object).getPort());
                    this.localAddress = Net.localAddress(this.fd);
                    return this;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void block(MembershipKeyImpl object, InetAddress inetAddress) throws IOException {
        Object object2 = this.stateLock;
        synchronized (object2) {
            int n;
            if (!((MembershipKeyImpl)object).isValid()) {
                object = new IllegalStateException("key is no longer valid");
                throw object;
            }
            if (inetAddress.isAnyLocalAddress()) {
                object = new IllegalArgumentException("Source address is a wildcard address");
                throw object;
            }
            if (inetAddress.isMulticastAddress()) {
                object = new IllegalArgumentException("Source address is multicast address");
                throw object;
            }
            if (inetAddress.getClass() != ((MembershipKeyImpl)object).group().getClass()) {
                object = new IllegalArgumentException("Source address is different type to group");
                throw object;
            }
            if (object instanceof MembershipKeyImpl.Type6) {
                object = (MembershipKeyImpl.Type6)object;
                n = Net.block6(this.fd, ((MembershipKeyImpl.Type6)object).groupAddress(), ((MembershipKeyImpl.Type6)object).index(), Net.inet6AsByteArray(inetAddress));
            } else {
                object = (MembershipKeyImpl.Type4)object;
                n = Net.block4(this.fd, ((MembershipKeyImpl.Type4)object).groupAddress(), ((MembershipKeyImpl.Type4)object).interfaceAddress(), Net.inet4AsInt(inetAddress));
            }
            if (n != -2) {
                return;
            }
            object = new UnsupportedOperationException();
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DatagramChannel connect(SocketAddress object) throws IOException {
        Object object2 = this.readLock;
        synchronized (object2) {
            Object object3 = this.writeLock;
            synchronized (object3) {
                Object object4 = this.stateLock;
                synchronized (object4) {
                    this.ensureOpenAndUnconnected();
                    SocketAddress socketAddress = Net.checkAddress((SocketAddress)object);
                    object = System.getSecurityManager();
                    if (object != null) {
                        ((SecurityManager)object).checkConnect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
                    }
                    if (Net.connect(this.family, this.fd, socketAddress.getAddress(), socketAddress.getPort()) <= 0) {
                        object = new Error();
                        throw object;
                    }
                    this.state = 1;
                    this.remoteAddress = socketAddress;
                    this.sender = socketAddress;
                    this.cachedSenderInetAddress = socketAddress.getAddress();
                    this.cachedSenderPort = socketAddress.getPort();
                    this.localAddress = Net.localAddress(this.fd);
                    boolean bl = false;
                    object = this.blockingLock();
                    synchronized (object) {
                        block18 : {
                            try {
                                boolean bl2;
                                bl = bl2 = this.isBlocking();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1);
                                if (bl2) {
                                    bl = bl2;
                                    this.configureBlocking(false);
                                }
                                do {
                                    bl = bl2;
                                    byteBuffer.clear();
                                    bl = bl2;
                                } while ((socketAddress = this.receive(byteBuffer)) != null);
                                if (!bl2) break block18;
                            }
                            catch (Throwable throwable) {
                                if (bl) {
                                    this.configureBlocking(true);
                                }
                                throw throwable;
                            }
                            this.configureBlocking(true);
                        }
                        return this;
                    }
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DatagramChannel disconnect() throws IOException {
        Object object = this.readLock;
        synchronized (object) {
            Object object2 = this.writeLock;
            synchronized (object2) {
                Object object3 = this.stateLock;
                synchronized (object3) {
                    if (this.isConnected() && this.isOpen()) {
                        InetSocketAddress inetSocketAddress = this.remoteAddress;
                        SecurityManager securityManager = System.getSecurityManager();
                        if (securityManager != null) {
                            securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress.getPort());
                        }
                        boolean bl = this.family == StandardProtocolFamily.INET6;
                        DatagramChannelImpl.disconnect0(this.fd, bl);
                        this.remoteAddress = null;
                        this.state = 0;
                        this.localAddress = Net.localAddress(this.fd);
                        return this;
                    }
                    return this;
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void drop(MembershipKeyImpl membershipKeyImpl) {
        Object object = this.stateLock;
        synchronized (object) {
            if (!membershipKeyImpl.isValid()) {
                return;
            }
            try {
                if (membershipKeyImpl instanceof MembershipKeyImpl.Type6) {
                    MembershipKeyImpl.Type6 type6 = (MembershipKeyImpl.Type6)membershipKeyImpl;
                    Net.drop6(this.fd, type6.groupAddress(), type6.index(), type6.source());
                } else {
                    MembershipKeyImpl.Type4 type4 = (MembershipKeyImpl.Type4)membershipKeyImpl;
                    Net.drop4(this.fd, type4.groupAddress(), type4.interfaceAddress(), type4.source());
                }
                membershipKeyImpl.invalidate();
                this.registry.remove(membershipKeyImpl);
            }
            catch (IOException iOException) {
                AssertionError assertionError = new AssertionError(iOException);
                throw assertionError;
            }
            return;
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
            if (this.state == 0) {
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Connect already invoked");
            throw illegalStateException;
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            if (this.fd != null) {
                this.close();
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
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
            this.ensureOpen();
            if (object != StandardSocketOptions.IP_TOS && object != StandardSocketOptions.IP_MULTICAST_TTL && object != StandardSocketOptions.IP_MULTICAST_LOOP) {
                if (object == StandardSocketOptions.IP_MULTICAST_IF) {
                    if (this.family == StandardProtocolFamily.INET) {
                        int n = Net.getInterface4(this.fd);
                        if (n == 0) {
                            return null;
                        }
                        object = NetworkInterface.getByInetAddress(Net.inet4FromInt(n));
                        if (object != null) {
                            return (T)object;
                        }
                        object = new IOException("Unable to map address to interface");
                        throw object;
                    }
                    int n = Net.getInterface6(this.fd);
                    if (n == 0) {
                        return null;
                    }
                    object = NetworkInterface.getByIndex(n);
                    if (object != null) {
                        return (T)object;
                    }
                    object = new IOException("Unable to map index to interface");
                    throw object;
                }
                if (object == StandardSocketOptions.SO_REUSEADDR && this.reuseAddressEmulated) {
                    boolean bl = this.isReuseAddress;
                    return bl;
                }
                object = Net.getSocketOption(this.fd, Net.UNSPEC, object);
                return (T)object;
            }
            object = Net.getSocketOption(this.fd, this.family, object);
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
            long l;
            this.guard.close();
            if (this.state != 2) {
                nd.preClose(this.fd);
            }
            ResourceManager.afterUdpClose();
            if (this.registry != null) {
                this.registry.invalidateAll();
            }
            if ((l = this.readerThread) != 0L) {
                NativeThread.signal(l);
            }
            if ((l = this.writerThread) != 0L) {
                NativeThread.signal(l);
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
            int n = this.state;
            boolean bl = true;
            if (n != 1) return false;
            return bl;
        }
    }

    @Override
    public MembershipKey join(InetAddress inetAddress, NetworkInterface networkInterface) throws IOException {
        return this.innerJoin(inetAddress, networkInterface, null);
    }

    @Override
    public MembershipKey join(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) throws IOException {
        if (inetAddress2 != null) {
            return this.innerJoin(inetAddress, networkInterface, inetAddress2);
        }
        throw new NullPointerException("source address is null");
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
            if (this.state == 2) {
                return;
            }
            if (this.state == -1) {
                this.state = 2;
                return;
            }
            nd.close(this.fd);
            this.state = 2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SocketAddress localAddress() {
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
            this.readerThread = 0L;
            bl3 = 0 > 0 ? bl2 : false;
            this.end(bl3);
            throw throwable;
        }
        if (!this.isOpen()) {
            // MONITOREXIT : object2
            this.readerThread = 0L;
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
        this.readerThread = 0L;
        bl3 = n > 0 ? bl : false;
        this.end(bl3);
        // MONITOREXIT : object
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int read(ByteBuffer object) throws IOException {
        if (object == null) {
            throw new NullPointerException();
        }
        Object object2 = this.readLock;
        synchronized (object2) {
            int n;
            boolean bl;
            block17 : {
                boolean bl2;
                int n2;
                boolean bl3;
                int n3;
                block15 : {
                    block16 : {
                        Object object3 = this.stateLock;
                        synchronized (object3) {
                            this.ensureOpen();
                            if (!this.isConnected()) {
                                object = new NotYetConnectedException();
                                throw object;
                            }
                        }
                        n3 = 0;
                        n = 0;
                        bl2 = true;
                        bl3 = true;
                        boolean bl4 = true;
                        n2 = n3;
                        this.begin();
                        n2 = n3;
                        bl = this.isOpen();
                        if (bl) break block15;
                        this.readerThread = 0L;
                        bl = bl4;
                        if (0 > 0) break block16;
                        bl = -2 == 0 ? bl4 : false;
                    }
                    this.end(bl);
                    return 0;
                }
                n2 = n3;
                try {
                    this.readerThread = NativeThread.current();
                    do {
                        n2 = n;
                        n3 = IOUtil.read(this.fd, (ByteBuffer)object, -1L, nd);
                        if (n3 != -3) break;
                        n = n3;
                        n2 = n3;
                    } while (this.isOpen());
                    n2 = n3;
                    n = IOStatus.normalize(n3);
                    this.readerThread = 0L;
                    bl = bl2;
                    if (n3 > 0) break block17;
                    bl = n3 == -2 ? bl2 : false;
                }
                catch (Throwable throwable) {
                    this.readerThread = 0L;
                    bl = bl3;
                    if (n2 <= 0) {
                        bl = n2 == -2 ? bl3 : false;
                    }
                    this.end(bl);
                    throw throwable;
                }
            }
            this.end(bl);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long read(ByteBuffer[] arrbyteBuffer, int n, int n2) throws IOException {
        block21 : {
            if (n < 0 || n2 < 0 || n > arrbyteBuffer.length - n2) break block21;
            Object object = this.readLock;
            synchronized (object) {
                boolean bl;
                long l;
                block20 : {
                    boolean bl2;
                    boolean bl3;
                    long l2;
                    block18 : {
                        block19 : {
                            Object object2 = this.stateLock;
                            synchronized (object2) {
                                this.ensureOpen();
                                if (!this.isConnected()) {
                                    arrbyteBuffer = new NotYetConnectedException();
                                    throw arrbyteBuffer;
                                }
                            }
                            l = 0L;
                            bl3 = false;
                            bl2 = false;
                            bl = false;
                            l2 = l;
                            this.begin();
                            l2 = l;
                            boolean bl4 = this.isOpen();
                            if (bl4) break block18;
                            this.readerThread = 0L;
                            if (0L <= 0L && 0L != -2L) break block19;
                            bl = true;
                        }
                        this.end(bl);
                        return 0L;
                    }
                    l2 = l;
                    try {
                        long l3;
                        this.readerThread = NativeThread.current();
                        do {
                            l2 = l;
                            l3 = IOUtil.read(this.fd, arrbyteBuffer, n, n2, nd);
                            if (l3 != -3L) break;
                            l = l3;
                            l2 = l3;
                        } while (this.isOpen());
                        l2 = l3;
                        l = IOStatus.normalize(l3);
                        this.readerThread = 0L;
                        if (l3 <= 0L) {
                            bl = bl3;
                            if (l3 != -2L) break block20;
                        }
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        block23 : {
                            block22 : {
                                this.readerThread = 0L;
                                if (l2 > 0L) break block22;
                                bl = bl2;
                                if (l2 != -2L) break block23;
                            }
                            bl = true;
                        }
                        this.end(bl);
                        throw throwable;
                    }
                }
                this.end(bl);
                return l;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    /*
     * Exception decompiling
     */
    @Override
    public SocketAddress receive(ByteBuffer var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public int send(ByteBuffer object, SocketAddress object2) throws IOException {
        if (object == null) throw new NullPointerException();
        Object object3 = this.writeLock;
        // MONITORENTER : object3
        this.ensureOpen();
        InetSocketAddress inetSocketAddress = Net.checkAddress((SocketAddress)object2);
        InetAddress inetAddress = inetSocketAddress.getAddress();
        if (inetAddress == null) {
            object = new IOException("Target address not resolved");
            throw object;
        }
        Object object4 = this.stateLock;
        // MONITORENTER : object4
        if (!this.isConnected()) {
            int n2;
            int n;
            boolean bl;
            int n3;
            block30 : {
                boolean bl3;
                boolean bl2;
                block28 : {
                    block29 : {
                        if (object2 == null) {
                            object = new NullPointerException();
                            throw object;
                        }
                        object2 = System.getSecurityManager();
                        if (object2 != null) {
                            if (inetAddress.isMulticastAddress()) {
                                ((SecurityManager)object2).checkMulticast(inetAddress);
                            } else {
                                ((SecurityManager)object2).checkConnect(inetAddress.getHostAddress(), inetSocketAddress.getPort());
                            }
                        }
                        // MONITOREXIT : object4
                        n2 = 0;
                        n = 0;
                        bl3 = true;
                        bl2 = true;
                        boolean bl4 = true;
                        n3 = n2;
                        this.begin();
                        n3 = n2;
                        bl = this.isOpen();
                        if (bl) break block28;
                        this.writerThread = 0L;
                        bl = bl4;
                        if (0 > 0) break block29;
                        bl = -2 == 0 ? bl4 : false;
                    }
                    this.end(bl);
                    // MONITOREXIT : object3
                    return 0;
                }
                n3 = n2;
                this.writerThread = NativeThread.current();
                n3 = n2;
                BlockGuard.getThreadPolicy().onNetwork();
                do {
                    n3 = n;
                    n2 = this.send(this.fd, (ByteBuffer)object, inetSocketAddress);
                    if (n2 != -3) break;
                    n = n2;
                    n3 = n2;
                } while (this.isOpen());
                n3 = n2;
                object2 = this.stateLock;
                n3 = n2;
                // MONITORENTER : object2
                {
                    catch (Throwable throwable) {
                        this.writerThread = 0L;
                        bl = bl2;
                        if (n3 <= 0) {
                            bl = n3 == -2 ? bl2 : false;
                        }
                        this.end(bl);
                        throw throwable;
                    }
                }
                if (this.isOpen() && this.localAddress == null) {
                    this.localAddress = Net.localAddress(this.fd);
                }
                // MONITOREXIT : object2
                n3 = n2;
                n = IOStatus.normalize(n2);
                this.writerThread = 0L;
                bl = bl3;
                if (n2 > 0) break block30;
                bl = n2 == -2 ? bl3 : false;
            }
            this.end(bl);
            // MONITOREXIT : object3
            return n;
            {
                catch (Throwable throwable) {}
                n3 = n2;
                throw throwable;
            }
        }
        if (object2.equals(this.remoteAddress)) {
            int n = this.write((ByteBuffer)object);
            // MONITOREXIT : object4
            // MONITOREXIT : object3
            return n;
        }
        object = new IllegalArgumentException("Connected address not equal to target address");
        throw object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> DatagramChannel setOption(SocketOption<T> object, T object2) throws IOException {
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
            this.ensureOpen();
            if (object != StandardSocketOptions.IP_TOS && object != StandardSocketOptions.IP_MULTICAST_TTL && object != StandardSocketOptions.IP_MULTICAST_LOOP) {
                if (object == StandardSocketOptions.IP_MULTICAST_IF) {
                    if (object2 == null) {
                        object = new IllegalArgumentException("Cannot set IP_MULTICAST_IF to 'null'");
                        throw object;
                    }
                    object = (NetworkInterface)object2;
                    if (this.family == StandardProtocolFamily.INET6) {
                        int n = ((NetworkInterface)object).getIndex();
                        if (n == -1) {
                            object = new IOException("Network interface cannot be identified");
                            throw object;
                        }
                        Net.setInterface6(this.fd, n);
                    } else {
                        if ((object = Net.anyInet4Address((NetworkInterface)object)) == null) {
                            object = new IOException("Network interface not configured for IPv4");
                            throw object;
                        }
                        int n = Net.inet4AsInt((InetAddress)object);
                        Net.setInterface4(this.fd, n);
                    }
                    return this;
                }
                if (object == StandardSocketOptions.SO_REUSEADDR && Net.useExclusiveBind() && this.localAddress != null) {
                    this.reuseAddressEmulated = true;
                    this.isReuseAddress = (Boolean)object2;
                }
                Net.setSocketOption(this.fd, Net.UNSPEC, object, object2);
                return this;
            }
            Net.setSocketOption(this.fd, this.family, object, object2);
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public DatagramSocket socket() {
        Object object = this.stateLock;
        synchronized (object) {
            if (this.socket != null) return this.socket;
            this.socket = DatagramSocketAdaptor.create(this);
            return this.socket;
        }
    }

    @Override
    public final Set<SocketOption<?>> supportedOptions() {
        return DefaultOptionsHolder.defaultOptions;
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
            n2 = n3 | Net.POLLIN;
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
        int n3 = selectionKeyImpl.nioInterestOps();
        int n4 = selectionKeyImpl.nioReadyOps();
        int n5 = n2;
        n2 = Net.POLLNVAL;
        boolean bl = false;
        boolean bl2 = false;
        if ((n2 & n) != 0) {
            return false;
        }
        if (((Net.POLLERR | Net.POLLHUP) & n) != 0) {
            selectionKeyImpl.nioReadyOps(n3);
            if ((n4 & n3) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        n2 = n5;
        if ((Net.POLLIN & n) != 0) {
            n2 = n5;
            if ((n3 & 1) != 0) {
                n2 = n5 | 1;
            }
        }
        n5 = n2;
        if ((Net.POLLOUT & n) != 0) {
            n5 = n2;
            if ((n3 & 4) != 0) {
                n5 = n2 | 4;
            }
        }
        selectionKeyImpl.nioReadyOps(n5);
        bl2 = bl;
        if ((n4 & n5) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void unblock(MembershipKeyImpl object, InetAddress object2) {
        Object object3 = this.stateLock;
        synchronized (object3) {
            boolean bl = ((MembershipKeyImpl)object).isValid();
            if (!bl) {
                object = new IllegalStateException("key is no longer valid");
                throw object;
            }
            try {
                if (object instanceof MembershipKeyImpl.Type6) {
                    object = (MembershipKeyImpl.Type6)object;
                    Net.unblock6(this.fd, ((MembershipKeyImpl.Type6)object).groupAddress(), ((MembershipKeyImpl.Type6)object).index(), Net.inet6AsByteArray((InetAddress)object2));
                } else {
                    object = (MembershipKeyImpl.Type4)object;
                    Net.unblock4(this.fd, ((MembershipKeyImpl.Type4)object).groupAddress(), ((MembershipKeyImpl.Type4)object).interfaceAddress(), Net.inet4AsInt((InetAddress)object2));
                }
                return;
            }
            catch (IOException iOException) {
                object2 = new AssertionError(iOException);
                throw object2;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int write(ByteBuffer object) throws IOException {
        if (object == null) {
            throw new NullPointerException();
        }
        Object object2 = this.writeLock;
        synchronized (object2) {
            int n;
            boolean bl;
            block17 : {
                boolean bl2;
                int n2;
                boolean bl3;
                int n3;
                block15 : {
                    block16 : {
                        Object object3 = this.stateLock;
                        synchronized (object3) {
                            this.ensureOpen();
                            if (!this.isConnected()) {
                                object = new NotYetConnectedException();
                                throw object;
                            }
                        }
                        n3 = 0;
                        n = 0;
                        bl2 = true;
                        bl3 = true;
                        boolean bl4 = true;
                        n2 = n3;
                        this.begin();
                        n2 = n3;
                        bl = this.isOpen();
                        if (bl) break block15;
                        this.writerThread = 0L;
                        bl = bl4;
                        if (0 > 0) break block16;
                        bl = -2 == 0 ? bl4 : false;
                    }
                    this.end(bl);
                    return 0;
                }
                n2 = n3;
                try {
                    this.writerThread = NativeThread.current();
                    do {
                        n2 = n;
                        n3 = IOUtil.write(this.fd, (ByteBuffer)object, -1L, nd);
                        if (n3 != -3) break;
                        n = n3;
                        n2 = n3;
                    } while (this.isOpen());
                    n2 = n3;
                    n = IOStatus.normalize(n3);
                    this.writerThread = 0L;
                    bl = bl2;
                    if (n3 > 0) break block17;
                    bl = n3 == -2 ? bl2 : false;
                }
                catch (Throwable throwable) {
                    this.writerThread = 0L;
                    bl = bl3;
                    if (n2 <= 0) {
                        bl = n2 == -2 ? bl3 : false;
                    }
                    this.end(bl);
                    throw throwable;
                }
            }
            this.end(bl);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long write(ByteBuffer[] arrbyteBuffer, int n, int n2) throws IOException {
        block21 : {
            if (n < 0 || n2 < 0 || n > arrbyteBuffer.length - n2) break block21;
            Object object = this.writeLock;
            synchronized (object) {
                boolean bl;
                long l;
                block20 : {
                    boolean bl2;
                    boolean bl3;
                    long l2;
                    block18 : {
                        block19 : {
                            Object object2 = this.stateLock;
                            synchronized (object2) {
                                this.ensureOpen();
                                if (!this.isConnected()) {
                                    arrbyteBuffer = new NotYetConnectedException();
                                    throw arrbyteBuffer;
                                }
                            }
                            l = 0L;
                            bl3 = false;
                            bl2 = false;
                            bl = false;
                            l2 = l;
                            this.begin();
                            l2 = l;
                            boolean bl4 = this.isOpen();
                            if (bl4) break block18;
                            this.writerThread = 0L;
                            if (0L <= 0L && 0L != -2L) break block19;
                            bl = true;
                        }
                        this.end(bl);
                        return 0L;
                    }
                    l2 = l;
                    try {
                        long l3;
                        this.writerThread = NativeThread.current();
                        do {
                            l2 = l;
                            l3 = IOUtil.write(this.fd, arrbyteBuffer, n, n2, nd);
                            if (l3 != -3L) break;
                            l = l3;
                            l2 = l3;
                        } while (this.isOpen());
                        l2 = l3;
                        l = IOStatus.normalize(l3);
                        this.writerThread = 0L;
                        if (l3 <= 0L) {
                            bl = bl3;
                            if (l3 != -2L) break block20;
                        }
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        block23 : {
                            block22 : {
                                this.writerThread = 0L;
                                if (l2 > 0L) break block22;
                                bl = bl2;
                                if (l2 != -2L) break block23;
                            }
                            bl = true;
                        }
                        this.end(bl);
                        throw throwable;
                    }
                }
                this.end(bl);
                return l;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private static class DefaultOptionsHolder {
        static final Set<SocketOption<?>> defaultOptions = DefaultOptionsHolder.defaultOptions();

        private DefaultOptionsHolder() {
        }

        private static Set<SocketOption<?>> defaultOptions() {
            HashSet<SocketOption<Object>> hashSet = new HashSet<SocketOption<Object>>(8);
            hashSet.add(StandardSocketOptions.SO_SNDBUF);
            hashSet.add(StandardSocketOptions.SO_RCVBUF);
            hashSet.add(StandardSocketOptions.SO_REUSEADDR);
            hashSet.add(StandardSocketOptions.SO_BROADCAST);
            hashSet.add(StandardSocketOptions.IP_TOS);
            hashSet.add(StandardSocketOptions.IP_MULTICAST_IF);
            hashSet.add(StandardSocketOptions.IP_MULTICAST_TTL);
            hashSet.add(StandardSocketOptions.IP_MULTICAST_LOOP);
            if (ExtendedOptionsImpl.flowSupported()) {
                hashSet.add(ExtendedSocketOptions.SO_FLOW_SLA);
            }
            return Collections.unmodifiableSet(hashSet);
        }
    }

}

