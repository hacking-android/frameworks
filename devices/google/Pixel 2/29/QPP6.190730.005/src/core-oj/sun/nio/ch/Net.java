/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BlockGuard
 */
package sun.nio.ch;

import dalvik.system.BlockGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ProtocolFamily;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketOption;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.net.UnknownHostException;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetBoundException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import jdk.net.NetworkPermission;
import jdk.net.SocketFlow;
import sun.net.ExtendedOptionsImpl;
import sun.nio.ch.IOUtil;
import sun.nio.ch.OptionKey;
import sun.nio.ch.SocketOptionRegistry;

public class Net {
    public static final short POLLCONN;
    public static final short POLLERR;
    public static final short POLLHUP;
    public static final short POLLIN;
    public static final short POLLNVAL;
    public static final short POLLOUT;
    public static final int SHUT_RD = 0;
    public static final int SHUT_RDWR = 2;
    public static final int SHUT_WR = 1;
    static final ProtocolFamily UNSPEC;
    private static volatile boolean checkedIPv6;
    private static final boolean exclusiveBind;
    private static final boolean fastLoopback;
    private static volatile boolean isIPv6Available;

    static {
        UNSPEC = new ProtocolFamily(){

            @Override
            public String name() {
                return "UNSPEC";
            }
        };
        checkedIPv6 = false;
        POLLIN = Net.pollinValue();
        POLLOUT = Net.polloutValue();
        POLLERR = Net.pollerrValue();
        POLLHUP = Net.pollhupValue();
        POLLNVAL = Net.pollnvalValue();
        POLLCONN = Net.pollconnValue();
        int n = Net.isExclusiveBindAvailable();
        if (n >= 0) {
            String string = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty("sun.net.useExclusiveBind");
                }
            });
            boolean bl = true;
            if (string != null) {
                if (string.length() != 0) {
                    bl = Boolean.parseBoolean(string);
                }
                exclusiveBind = bl;
            } else {
                exclusiveBind = n == 1;
            }
        } else {
            exclusiveBind = false;
        }
        fastLoopback = Net.isFastTcpLoopbackRequested();
    }

    private Net() {
    }

    static Inet4Address anyInet4Address(final NetworkInterface networkInterface) {
        return AccessController.doPrivileged(new PrivilegedAction<Inet4Address>(){

            @Override
            public Inet4Address run() {
                Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
                while (enumeration.hasMoreElements()) {
                    InetAddress inetAddress = enumeration.nextElement();
                    if (!(inetAddress instanceof Inet4Address)) continue;
                    return (Inet4Address)inetAddress;
                }
                return null;
            }
        });
    }

    static InetSocketAddress asInetSocketAddress(SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            return (InetSocketAddress)socketAddress;
        }
        throw new UnsupportedAddressTypeException();
    }

    public static void bind(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws IOException {
        Net.bind(UNSPEC, fileDescriptor, inetAddress, n);
    }

    static void bind(ProtocolFamily protocolFamily, FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws IOException {
        boolean bl = Net.isIPv6Available() && protocolFamily != StandardProtocolFamily.INET;
        Net.bind0(fileDescriptor, bl, exclusiveBind, inetAddress, n);
    }

    private static native void bind0(FileDescriptor var0, boolean var1, boolean var2, InetAddress var3, int var4) throws IOException;

    static int block4(FileDescriptor fileDescriptor, int n, int n2, int n3) throws IOException {
        return Net.blockOrUnblock4(true, fileDescriptor, n, n2, n3);
    }

    static int block6(FileDescriptor fileDescriptor, byte[] arrby, int n, byte[] arrby2) throws IOException {
        return Net.blockOrUnblock6(true, fileDescriptor, arrby, n, arrby2);
    }

    private static native int blockOrUnblock4(boolean var0, FileDescriptor var1, int var2, int var3, int var4) throws IOException;

    static native int blockOrUnblock6(boolean var0, FileDescriptor var1, byte[] var2, int var3, byte[] var4) throws IOException;

    static boolean canIPv6SocketJoinIPv4Group() {
        return Net.canIPv6SocketJoinIPv4Group0();
    }

    private static native boolean canIPv6SocketJoinIPv4Group0();

    static boolean canJoin6WithIPv4Group() {
        return Net.canJoin6WithIPv4Group0();
    }

    private static native boolean canJoin6WithIPv4Group0();

    public static InetSocketAddress checkAddress(SocketAddress serializable) {
        if (serializable != null) {
            if (serializable instanceof InetSocketAddress) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress)serializable;
                if (!inetSocketAddress.isUnresolved()) {
                    serializable = inetSocketAddress.getAddress();
                    if (!(serializable instanceof Inet4Address) && !(serializable instanceof Inet6Address)) {
                        throw new IllegalArgumentException("Invalid address type");
                    }
                    return inetSocketAddress;
                }
                throw new UnresolvedAddressException();
            }
            throw new UnsupportedAddressTypeException();
        }
        throw new IllegalArgumentException("sa == null");
    }

    static int connect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws IOException {
        return Net.connect(UNSPEC, fileDescriptor, inetAddress, n);
    }

    static int connect(ProtocolFamily protocolFamily, FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws IOException {
        BlockGuard.getThreadPolicy().onNetwork();
        boolean bl = Net.isIPv6Available() && protocolFamily != StandardProtocolFamily.INET;
        return Net.connect0(bl, fileDescriptor, inetAddress, n);
    }

    private static native int connect0(boolean var0, FileDescriptor var1, InetAddress var2, int var3) throws IOException;

    static void drop4(FileDescriptor fileDescriptor, int n, int n2, int n3) throws IOException {
        Net.joinOrDrop4(false, fileDescriptor, n, n2, n3);
    }

    static void drop6(FileDescriptor fileDescriptor, byte[] arrby, int n, byte[] arrby2) throws IOException {
        Net.joinOrDrop6(false, fileDescriptor, arrby, n, arrby2);
    }

    private static native int getIntOption0(FileDescriptor var0, boolean var1, int var2, int var3) throws IOException;

    static native int getInterface4(FileDescriptor var0) throws IOException;

    static native int getInterface6(FileDescriptor var0) throws IOException;

    private static InetSocketAddress getLoopbackAddress(int n) {
        return new InetSocketAddress(InetAddress.getLoopbackAddress(), n);
    }

    static InetSocketAddress getRevealedLocalAddress(InetSocketAddress inetSocketAddress) {
        SecurityManager securityManager = System.getSecurityManager();
        if (inetSocketAddress != null && securityManager != null) {
            try {
                securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), -1);
            }
            catch (SecurityException securityException) {
                inetSocketAddress = Net.getLoopbackAddress(inetSocketAddress.getPort());
            }
            return inetSocketAddress;
        }
        return inetSocketAddress;
    }

    static String getRevealedLocalAddressAsString(InetSocketAddress object) {
        object = System.getSecurityManager() == null ? ((InetSocketAddress)object).toString() : Net.getLoopbackAddress(((InetSocketAddress)object).getPort()).toString();
        return object;
    }

    static Object getSocketOption(FileDescriptor object, ProtocolFamily object2, SocketOption<?> object3) throws IOException {
        Class<?> class_ = object3.type();
        if (class_ == SocketFlow.class) {
            object2 = System.getSecurityManager();
            if (object2 != null) {
                ((SecurityManager)object2).checkPermission(new NetworkPermission("getOption.SO_FLOW_SLA"));
            }
            object2 = SocketFlow.create();
            ExtendedOptionsImpl.getFlowOption((FileDescriptor)object, (SocketFlow)object2);
            return object2;
        }
        if (class_ != Integer.class && class_ != Boolean.class) {
            throw new AssertionError((Object)"Should not reach here");
        }
        if ((object3 = SocketOptionRegistry.findOption(object3, (ProtocolFamily)object2)) != null) {
            boolean bl = object2 == UNSPEC;
            int n = Net.getIntOption0((FileDescriptor)object, bl, ((OptionKey)object3).level(), ((OptionKey)object3).name());
            if (class_ == Integer.class) {
                return n;
            }
            object = n == 0 ? Boolean.FALSE : Boolean.TRUE;
            return object;
        }
        throw new AssertionError((Object)"Option not found");
    }

    static int inet4AsInt(InetAddress arrby) {
        if (arrby instanceof Inet4Address) {
            arrby = arrby.getAddress();
            return arrby[3] & 255 | arrby[2] << 8 & 65280 | arrby[1] << 16 & 16711680 | arrby[0] << 24 & -16777216;
        }
        throw new AssertionError((Object)"Should not reach here");
    }

    static InetAddress inet4FromInt(int n) {
        byte by = (byte)(n >>> 24 & 255);
        byte by2 = (byte)(n >>> 16 & 255);
        byte by3 = (byte)(n >>> 8 & 255);
        byte by4 = (byte)(n & 255);
        try {
            InetAddress inetAddress = InetAddress.getByAddress(new byte[]{by, by2, by3, by4});
            return inetAddress;
        }
        catch (UnknownHostException unknownHostException) {
            throw new AssertionError((Object)"Should not reach here");
        }
    }

    static byte[] inet6AsByteArray(InetAddress arrby) {
        if (arrby instanceof Inet6Address) {
            return arrby.getAddress();
        }
        if (arrby instanceof Inet4Address) {
            arrby = arrby.getAddress();
            byte[] arrby2 = new byte[16];
            arrby2[10] = (byte)-1;
            arrby2[11] = (byte)-1;
            arrby2[12] = arrby[0];
            arrby2[13] = arrby[1];
            arrby2[14] = arrby[2];
            arrby2[15] = arrby[3];
            return arrby2;
        }
        throw new AssertionError((Object)"Should not reach here");
    }

    private static native int isExclusiveBindAvailable();

    public static boolean isFastTcpLoopbackRequested() {
        String string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty("jdk.net.useFastTcpLoopback");
            }
        });
        boolean bl = "".equals(string) ? true : Boolean.parseBoolean(string);
        return bl;
    }

    static boolean isIPv6Available() {
        if (!checkedIPv6) {
            isIPv6Available = Net.isIPv6Available0();
            checkedIPv6 = true;
        }
        return isIPv6Available;
    }

    private static native boolean isIPv6Available0();

    static int join4(FileDescriptor fileDescriptor, int n, int n2, int n3) throws IOException {
        return Net.joinOrDrop4(true, fileDescriptor, n, n2, n3);
    }

    static int join6(FileDescriptor fileDescriptor, byte[] arrby, int n, byte[] arrby2) throws IOException {
        return Net.joinOrDrop6(true, fileDescriptor, arrby, n, arrby2);
    }

    private static native int joinOrDrop4(boolean var0, FileDescriptor var1, int var2, int var3, int var4) throws IOException;

    private static native int joinOrDrop6(boolean var0, FileDescriptor var1, byte[] var2, int var3, byte[] var4) throws IOException;

    static native void listen(FileDescriptor var0, int var1) throws IOException;

    public static InetSocketAddress localAddress(FileDescriptor fileDescriptor) throws IOException {
        return new InetSocketAddress(Net.localInetAddress(fileDescriptor), Net.localPort(fileDescriptor));
    }

    private static native InetAddress localInetAddress(FileDescriptor var0) throws IOException;

    private static native int localPort(FileDescriptor var0) throws IOException;

    static native int poll(FileDescriptor var0, int var1, long var2) throws IOException;

    static native short pollconnValue();

    static native short pollerrValue();

    static native short pollhupValue();

    static native short pollinValue();

    static native short pollnvalValue();

    static native short polloutValue();

    static InetSocketAddress remoteAddress(FileDescriptor fileDescriptor) throws IOException {
        return new InetSocketAddress(Net.remoteInetAddress(fileDescriptor), Net.remotePort(fileDescriptor));
    }

    private static native InetAddress remoteInetAddress(FileDescriptor var0) throws IOException;

    private static native int remotePort(FileDescriptor var0) throws IOException;

    static FileDescriptor serverSocket(boolean bl) {
        return IOUtil.newFD(Net.socket0(Net.isIPv6Available(), bl, true, fastLoopback));
    }

    private static native void setIntOption0(FileDescriptor var0, boolean var1, int var2, int var3, int var4, boolean var5) throws IOException;

    static native void setInterface4(FileDescriptor var0, int var1) throws IOException;

    static native void setInterface6(FileDescriptor var0, int var1) throws IOException;

    static void setSocketOption(FileDescriptor fileDescriptor, ProtocolFamily object, SocketOption<?> object2, Object object3) throws IOException {
        if (object3 != null) {
            Class<?> class_ = object2.type();
            if (class_ == SocketFlow.class) {
                object = System.getSecurityManager();
                if (object != null) {
                    ((SecurityManager)object).checkPermission(new NetworkPermission("setOption.SO_FLOW_SLA"));
                }
                ExtendedOptionsImpl.setFlowOption(fileDescriptor, (SocketFlow)object3);
                return;
            }
            if (class_ != Integer.class && class_ != Boolean.class) {
                throw new AssertionError((Object)"Should not reach here");
            }
            if (object2 != StandardSocketOptions.SO_RCVBUF && object2 != StandardSocketOptions.SO_SNDBUF || (Integer)object3 >= 0) {
                int n;
                Object object4 = object3;
                if (object2 == StandardSocketOptions.SO_LINGER) {
                    n = (Integer)object3;
                    if (n < 0) {
                        object3 = -1;
                    }
                    object4 = object3;
                    if (n > 65535) {
                        object4 = 65535;
                    }
                }
                if (object2 == StandardSocketOptions.IP_TOS && ((n = ((Integer)object4).intValue()) < 0 || n > 255)) {
                    throw new IllegalArgumentException("Invalid IP_TOS value");
                }
                if (object2 == StandardSocketOptions.IP_MULTICAST_TTL && ((n = ((Integer)object4).intValue()) < 0 || n > 255)) {
                    throw new IllegalArgumentException("Invalid TTL/hop value");
                }
                if ((object2 = SocketOptionRegistry.findOption(object2, (ProtocolFamily)object)) != null) {
                    n = class_ == Integer.class ? (Integer)object4 : (int)(((Boolean)object4).booleanValue() ? 1 : 0);
                    boolean bl = object == UNSPEC;
                    boolean bl2 = object == StandardProtocolFamily.INET6;
                    Net.setIntOption0(fileDescriptor, bl, ((OptionKey)object2).level(), ((OptionKey)object2).name(), n, bl2);
                    return;
                }
                throw new AssertionError((Object)"Option not found");
            }
            throw new IllegalArgumentException("Invalid send/receive buffer size");
        }
        throw new IllegalArgumentException("Invalid option value");
    }

    static native void shutdown(FileDescriptor var0, int var1) throws IOException;

    static FileDescriptor socket(ProtocolFamily protocolFamily, boolean bl) throws IOException {
        boolean bl2 = Net.isIPv6Available() && protocolFamily != StandardProtocolFamily.INET;
        return IOUtil.newFD(Net.socket0(bl2, bl, false, fastLoopback));
    }

    static FileDescriptor socket(boolean bl) throws IOException {
        return Net.socket(UNSPEC, bl);
    }

    private static native int socket0(boolean var0, boolean var1, boolean var2, boolean var3);

    static void translateException(Exception exception) throws IOException {
        Net.translateException(exception, false);
    }

    static void translateException(Exception exception, boolean bl) throws IOException {
        if (!(exception instanceof IOException)) {
            if (bl && exception instanceof UnresolvedAddressException) {
                throw new UnknownHostException();
            }
            Net.translateToSocketException(exception);
            return;
        }
        throw (IOException)exception;
    }

    static void translateToSocketException(Exception exception) throws SocketException {
        if (!(exception instanceof SocketException)) {
            Exception exception2 = exception;
            if (exception instanceof ClosedChannelException) {
                exception2 = new SocketException("Socket is closed");
            } else if (exception instanceof NotYetConnectedException) {
                exception2 = new SocketException("Socket is not connected");
            } else if (exception instanceof AlreadyBoundException) {
                exception2 = new SocketException("Already bound");
            } else if (exception instanceof NotYetBoundException) {
                exception2 = new SocketException("Socket is not bound yet");
            } else if (exception instanceof UnsupportedAddressTypeException) {
                exception2 = new SocketException("Unsupported address type");
            } else if (exception instanceof UnresolvedAddressException) {
                exception2 = new SocketException("Unresolved address");
            } else if (exception instanceof AlreadyConnectedException) {
                exception2 = new SocketException("Already connected");
            }
            if (exception2 != exception) {
                exception2.initCause(exception);
            }
            if (!(exception2 instanceof SocketException)) {
                if (exception2 instanceof RuntimeException) {
                    throw (RuntimeException)exception2;
                }
                throw new Error("Untranslated exception", exception2);
            }
            throw (SocketException)exception2;
        }
        throw (SocketException)exception;
    }

    static void unblock4(FileDescriptor fileDescriptor, int n, int n2, int n3) throws IOException {
        Net.blockOrUnblock4(false, fileDescriptor, n, n2, n3);
    }

    static void unblock6(FileDescriptor fileDescriptor, byte[] arrby, int n, byte[] arrby2) throws IOException {
        Net.blockOrUnblock6(false, fileDescriptor, arrby, n, arrby2);
    }

    static boolean useExclusiveBind() {
        return exclusiveBind;
    }

}

