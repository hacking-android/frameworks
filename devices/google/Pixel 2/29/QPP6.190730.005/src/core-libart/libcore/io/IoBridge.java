/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import android.system.ErrnoException;
import android.system.Int32Ref;
import android.system.OsConstants;
import android.system.StructGroupReq;
import android.system.StructLinger;
import android.system.StructPollfd;
import android.system.StructStat;
import android.system.StructTimeval;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.BindException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import libcore.io.AsynchronousCloseMonitor;
import libcore.io.IoUtils;
import libcore.io.Libcore;
import libcore.io.Os;
import libcore.util.ArrayUtils;

public final class IoBridge {
    public static final int JAVA_IP_MULTICAST_TTL = 17;
    public static final int JAVA_IP_TTL = 25;
    public static final int JAVA_MCAST_JOIN_GROUP = 19;
    public static final int JAVA_MCAST_LEAVE_GROUP = 20;

    private IoBridge() {
    }

    public static int available(FileDescriptor fileDescriptor) throws IOException {
        try {
            Int32Ref int32Ref = new Int32Ref(0);
            Libcore.os.ioctlInt(fileDescriptor, OsConstants.FIONREAD, int32Ref);
            if (int32Ref.value < 0) {
                int32Ref.value = 0;
            }
            int n = int32Ref.value;
            return n;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno == OsConstants.ENOTTY) {
                return 0;
            }
            throw errnoException.rethrowAsIOException();
        }
    }

    public static void bind(FileDescriptor object, InetAddress inetAddress, int n) throws SocketException {
        Object object2 = inetAddress;
        if (inetAddress instanceof Inet6Address) {
            Inet6Address inet6Address = (Inet6Address)inetAddress;
            object2 = inetAddress;
            if (inet6Address.getScopeId() == 0) {
                object2 = inetAddress;
                if (inet6Address.isLinkLocalAddress()) {
                    object2 = NetworkInterface.getByInetAddress(inetAddress);
                    if (object2 != null) {
                        try {
                            object2 = Inet6Address.getByAddress(inetAddress.getHostName(), inetAddress.getAddress(), ((NetworkInterface)object2).getIndex());
                        }
                        catch (UnknownHostException unknownHostException) {
                            throw new AssertionError(unknownHostException);
                        }
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Can't bind to a link-local address without a scope id: ");
                        ((StringBuilder)object).append(inetAddress);
                        throw new SocketException(((StringBuilder)object).toString());
                    }
                }
            }
        }
        try {
            Libcore.os.bind((FileDescriptor)object, (InetAddress)object2, n);
            return;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno != OsConstants.EADDRINUSE && errnoException.errno != OsConstants.EADDRNOTAVAIL && errnoException.errno != OsConstants.EPERM && errnoException.errno != OsConstants.EACCES) {
                throw new SocketException(errnoException.getMessage(), (Throwable)errnoException);
            }
            throw new BindException(errnoException.getMessage(), (Throwable)errnoException);
        }
    }

    private static boolean booleanFromInt(int n) {
        boolean bl = n != 0;
        return bl;
    }

    private static int booleanToInt(boolean bl) {
        return (int)bl;
    }

    public static void closeAndSignalBlockedThreads(FileDescriptor fileDescriptor) throws IOException {
        if (fileDescriptor != null && fileDescriptor.valid()) {
            fileDescriptor = fileDescriptor.release$();
            AsynchronousCloseMonitor.signalBlockedThreads(fileDescriptor);
            try {
                Libcore.os.close(fileDescriptor);
                return;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
    }

    public static void connect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws SocketException {
        try {
            IoBridge.connect(fileDescriptor, inetAddress, n, 0);
            return;
        }
        catch (SocketTimeoutException socketTimeoutException) {
            throw new AssertionError(socketTimeoutException);
        }
    }

    public static void connect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n, int n2) throws SocketException, SocketTimeoutException {
        try {
            IoBridge.connectErrno(fileDescriptor, inetAddress, n, n2);
            return;
        }
        catch (IOException iOException) {
            throw new SocketException((Throwable)iOException);
        }
        catch (SocketTimeoutException socketTimeoutException) {
            throw socketTimeoutException;
        }
        catch (SocketException socketException) {
            throw socketException;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno != OsConstants.EHOSTUNREACH) {
                if (errnoException.errno == OsConstants.EADDRNOTAVAIL) {
                    throw new NoRouteToHostException("Address not available");
                }
                throw new ConnectException(IoBridge.createMessageForException(fileDescriptor, inetAddress, n, n2, errnoException), (Throwable)errnoException);
            }
            throw new NoRouteToHostException("Host unreachable");
        }
    }

    private static void connectErrno(FileDescriptor fileDescriptor, InetAddress inetAddress, int n, int n2) throws ErrnoException, IOException {
        if (n2 <= 0) {
            Libcore.os.connect(fileDescriptor, inetAddress, n);
            return;
        }
        IoUtils.setBlocking(fileDescriptor, false);
        long l = System.nanoTime();
        long l2 = TimeUnit.MILLISECONDS.toNanos(n2);
        try {
            Libcore.os.connect(fileDescriptor, inetAddress, n);
            IoUtils.setBlocking(fileDescriptor, true);
            return;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno == OsConstants.EINPROGRESS) {
                int n3;
                while ((n3 = (int)TimeUnit.NANOSECONDS.toMillis(l + l2 - System.nanoTime())) > 0) {
                    if (!IoBridge.isConnected(fileDescriptor, inetAddress, n, n2, n3)) continue;
                    IoUtils.setBlocking(fileDescriptor, true);
                    return;
                }
                throw new SocketTimeoutException(IoBridge.createMessageForException(fileDescriptor, inetAddress, n, n2, null));
            }
            throw errnoException;
        }
    }

    private static String createMessageForException(FileDescriptor object, InetAddress serializable, int n, int n2, Exception exception) {
        StringBuilder stringBuilder = null;
        try {
            object = IoBridge.getLocalInetSocketAddress((FileDescriptor)object);
        }
        catch (SocketException socketException) {
            object = stringBuilder;
        }
        stringBuilder = new StringBuilder("failed to connect");
        stringBuilder.append(" to ");
        stringBuilder.append(serializable);
        stringBuilder.append(" (port ");
        stringBuilder.append(n);
        serializable = stringBuilder.append(")");
        if (object != null) {
            ((StringBuilder)serializable).append(" from ");
            ((StringBuilder)serializable).append(((InetSocketAddress)object).getAddress());
            ((StringBuilder)serializable).append(" (port ");
            ((StringBuilder)serializable).append(((InetSocketAddress)object).getPort());
            ((StringBuilder)serializable).append(")");
        }
        if (n2 > 0) {
            ((StringBuilder)serializable).append(" after ");
            ((StringBuilder)serializable).append(n2);
            ((StringBuilder)serializable).append("ms");
        }
        if (exception != null) {
            ((StringBuilder)serializable).append(": ");
            ((StringBuilder)serializable).append(exception.getMessage());
        }
        return ((StringBuilder)serializable).toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static InetSocketAddress getLocalInetSocketAddress(FileDescriptor object) throws SocketException {
        try {
            object = Libcore.os.getsockname((FileDescriptor)object);
            if (object == null) return (InetSocketAddress)object;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsSocketException();
        }
        if (object instanceof InetSocketAddress) return (InetSocketAddress)object;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Socket assumed to be pending closure: Expected sockname to be an InetSocketAddress, got ");
        stringBuilder.append(object.getClass());
        SocketException socketException = new SocketException(stringBuilder.toString());
        throw socketException;
    }

    public static Object getSocketOption(FileDescriptor object, int n) throws SocketException {
        try {
            object = IoBridge.getSocketOptionErrno((FileDescriptor)object, n);
            return object;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsSocketException();
        }
    }

    /*
     * Exception decompiling
     */
    private static Object getSocketOptionErrno(FileDescriptor var0, int var1_1) throws ErrnoException, SocketException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    @UnsupportedAppUsage
    public static boolean isConnected(FileDescriptor object, InetAddress inetAddress, int n, int n2, int n3) throws IOException {
        Object object2;
        block8 : {
            block7 : {
                try {
                    StructPollfd structPollfd = new StructPollfd();
                    object2 = new StructPollfd[]{structPollfd};
                }
                catch (ErrnoException errnoException) {
                    if (((FileDescriptor)object).valid()) {
                        object = IoBridge.createMessageForException((FileDescriptor)object, inetAddress, n, n2, errnoException);
                        if (errnoException.errno == OsConstants.ETIMEDOUT) {
                            object = new SocketTimeoutException((String)object);
                            ((Throwable)object).initCause(errnoException);
                            throw object;
                        }
                        throw new ConnectException((String)object, (Throwable)errnoException);
                    }
                    throw new SocketException("Socket closed");
                }
                object2[0].fd = object;
                object2[0].events = (short)OsConstants.POLLOUT;
                if (Libcore.os.poll((StructPollfd[])object2, n3) != 0) break block7;
                return false;
            }
            n3 = Libcore.os.getsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_ERROR);
            if (n3 != 0) break block8;
            return true;
        }
        object2 = new ErrnoException("isConnected", n3);
        throw object2;
    }

    private static int maybeThrowAfterRecvfrom(boolean bl, boolean bl2, ErrnoException errnoException) throws SocketException, SocketTimeoutException {
        if (bl) {
            if (errnoException.errno == OsConstants.EAGAIN) {
                return 0;
            }
            throw errnoException.rethrowAsSocketException();
        }
        if (bl2 && errnoException.errno == OsConstants.ECONNREFUSED) {
            throw new PortUnreachableException("ICMP Port Unreachable", (Throwable)errnoException);
        }
        if (errnoException.errno == OsConstants.EAGAIN) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException();
            socketTimeoutException.initCause(errnoException);
            throw socketTimeoutException;
        }
        throw errnoException.rethrowAsSocketException();
    }

    private static int maybeThrowAfterSendto(boolean bl, ErrnoException errnoException) throws IOException {
        if (bl) {
            if (errnoException.errno == OsConstants.ECONNREFUSED) {
                throw new PortUnreachableException("ICMP Port Unreachable");
            }
        } else if (errnoException.errno == OsConstants.EAGAIN) {
            return 0;
        }
        throw errnoException.rethrowAsIOException();
    }

    public static FileDescriptor open(String object, int n) throws FileNotFoundException {
        Object object2;
        int n2;
        Object object3 = object2 = null;
        try {
            n2 = (OsConstants.O_ACCMODE & n) == OsConstants.O_RDONLY ? 0 : 384;
            object3 = object2;
        }
        catch (ErrnoException errnoException) {
            if (object3 != null) {
                try {
                    IoBridge.closeAndSignalBlockedThreads(object3);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object);
            ((StringBuilder)object3).append(": ");
            ((StringBuilder)object3).append(errnoException.getMessage());
            object = new FileNotFoundException(((StringBuilder)object3).toString());
            ((Throwable)object).initCause(errnoException);
            throw object;
        }
        object3 = object2 = Libcore.os.open((String)object, n, n2);
        if (!OsConstants.S_ISDIR(Libcore.os.fstat((FileDescriptor)object2).st_mode)) {
            return object2;
        }
        object3 = object2;
        object3 = object2;
        ErrnoException errnoException = new ErrnoException("open", OsConstants.EISDIR);
        object3 = object2;
        throw errnoException;
    }

    public static void poll(FileDescriptor object, int n, int n2) throws SocketException, SocketTimeoutException {
        StructPollfd[] arrstructPollfd = new StructPollfd[]{new StructPollfd()};
        arrstructPollfd[0].fd = object;
        arrstructPollfd[0].events = (short)n;
        try {
            if (android.system.Os.poll(arrstructPollfd, n2) == 0) {
                object = new SocketTimeoutException("Poll timed out");
                throw object;
            }
        }
        catch (ErrnoException errnoException) {
            errnoException.rethrowAsSocketException();
        }
    }

    private static int postRecvfrom(boolean bl, DatagramPacket datagramPacket, InetSocketAddress inetSocketAddress, int n) {
        if (bl && n == 0) {
            return -1;
        }
        if (datagramPacket != null) {
            datagramPacket.setReceivedLength(n);
            datagramPacket.setPort(inetSocketAddress.getPort());
            if (!inetSocketAddress.getAddress().equals(datagramPacket.getAddress())) {
                datagramPacket.setAddress(inetSocketAddress.getAddress());
            }
        }
        return n;
    }

    public static int read(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws IOException {
        ArrayUtils.throwsIfOutOfBounds(arrby.length, n, n2);
        if (n2 == 0) {
            return 0;
        }
        try {
            n = Libcore.os.read(fileDescriptor, arrby, n, n2);
            if (n == 0) {
                return -1;
            }
            return n;
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno == OsConstants.EAGAIN) {
                return 0;
            }
            throw errnoException.rethrowAsIOException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int recvfrom(boolean bl, FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, DatagramPacket datagramPacket, boolean bl2) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (datagramPacket != null) {
            try {
                inetSocketAddress = new InetSocketAddress();
                return IoBridge.postRecvfrom(bl, datagramPacket, inetSocketAddress, Libcore.os.recvfrom(fileDescriptor, byteBuffer, n, inetSocketAddress));
            }
            catch (ErrnoException errnoException) {
                return IoBridge.maybeThrowAfterRecvfrom(bl, bl2, errnoException);
            }
        }
        inetSocketAddress = null;
        return IoBridge.postRecvfrom(bl, datagramPacket, inetSocketAddress, Libcore.os.recvfrom(fileDescriptor, byteBuffer, n, inetSocketAddress));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int recvfrom(boolean bl, FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, DatagramPacket datagramPacket, boolean bl2) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (datagramPacket != null) {
            try {
                inetSocketAddress = new InetSocketAddress();
                return IoBridge.postRecvfrom(bl, datagramPacket, inetSocketAddress, Libcore.os.recvfrom(fileDescriptor, arrby, n, n2, n3, inetSocketAddress));
            }
            catch (ErrnoException errnoException) {
                return IoBridge.maybeThrowAfterRecvfrom(bl, bl2, errnoException);
            }
        }
        inetSocketAddress = null;
        return IoBridge.postRecvfrom(bl, datagramPacket, inetSocketAddress, Libcore.os.recvfrom(fileDescriptor, arrby, n, n2, n3, inetSocketAddress));
    }

    public static int sendto(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetAddress inetAddress, int n2) throws IOException {
        boolean bl = inetAddress != null;
        if (!bl && byteBuffer.remaining() == 0) {
            return 0;
        }
        try {
            n = Libcore.os.sendto(fileDescriptor, byteBuffer, n, inetAddress, n2);
        }
        catch (ErrnoException errnoException) {
            n = IoBridge.maybeThrowAfterSendto(bl, errnoException);
        }
        return n;
    }

    public static int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetAddress inetAddress, int n4) throws IOException {
        boolean bl = inetAddress != null;
        if (!bl && n2 <= 0) {
            return 0;
        }
        try {
            n = Libcore.os.sendto(fileDescriptor, arrby, n, n2, n3, inetAddress, n4);
        }
        catch (ErrnoException errnoException) {
            n = IoBridge.maybeThrowAfterSendto(bl, errnoException);
        }
        return n;
    }

    public static void setSocketOption(FileDescriptor fileDescriptor, int n, Object object) throws SocketException {
        try {
            IoBridge.setSocketOptionErrno(fileDescriptor, n, object);
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsSocketException();
        }
    }

    private static void setSocketOptionErrno(FileDescriptor object, int n, Object object2) throws ErrnoException, SocketException {
        if (n != 1) {
            if (n != 8) {
                if (n != 25) {
                    if (n != 128) {
                        if (n != 4102) {
                            if (n != 3) {
                                if (n != 4) {
                                    if (n != 31) {
                                        if (n != 32) {
                                            switch (n) {
                                                default: {
                                                    switch (n) {
                                                        default: {
                                                            object = new StringBuilder();
                                                            ((StringBuilder)object).append("Unknown socket option: ");
                                                            ((StringBuilder)object).append(n);
                                                            throw new SocketException(((StringBuilder)object).toString());
                                                        }
                                                        case 4099: {
                                                            Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_OOBINLINE, IoBridge.booleanToInt((Boolean)object2));
                                                            return;
                                                        }
                                                        case 4098: {
                                                            Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_RCVBUF, (Integer)object2);
                                                            return;
                                                        }
                                                        case 4097: 
                                                    }
                                                    Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_SNDBUF, (Integer)object2);
                                                    return;
                                                }
                                                case 19: 
                                                case 20: {
                                                    object2 = (StructGroupReq)object2;
                                                    int n2 = ((StructGroupReq)object2).gr_group instanceof Inet4Address ? OsConstants.IPPROTO_IP : OsConstants.IPPROTO_IPV6;
                                                    n = n == 19 ? OsConstants.MCAST_JOIN_GROUP : OsConstants.MCAST_LEAVE_GROUP;
                                                    Libcore.os.setsockoptGroupReq((FileDescriptor)object, n2, n, (StructGroupReq)object2);
                                                    return;
                                                }
                                                case 18: {
                                                    n = IoBridge.booleanToInt(true ^ (Boolean)object2);
                                                    Libcore.os.setsockoptByte((FileDescriptor)object, OsConstants.IPPROTO_IP, OsConstants.IP_MULTICAST_LOOP, n);
                                                    Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IPV6, OsConstants.IPV6_MULTICAST_LOOP, n);
                                                    return;
                                                }
                                                case 17: {
                                                    Libcore.os.setsockoptByte((FileDescriptor)object, OsConstants.IPPROTO_IP, OsConstants.IP_MULTICAST_TTL, (Integer)object2);
                                                    Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IPV6, OsConstants.IPV6_MULTICAST_HOPS, (Integer)object2);
                                                    return;
                                                }
                                                case 16: 
                                            }
                                            object2 = NetworkInterface.getByInetAddress((InetAddress)object2);
                                            if (object2 != null) {
                                                Libcore.os.setsockoptIpMreqn((FileDescriptor)object, OsConstants.IPPROTO_IP, OsConstants.IP_MULTICAST_IF, ((NetworkInterface)object2).getIndex());
                                                Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IPV6, OsConstants.IPV6_MULTICAST_IF, ((NetworkInterface)object2).getIndex());
                                                return;
                                            }
                                            throw new SocketException("bad argument for IP_MULTICAST_IF : address not bound to any interface");
                                        }
                                        Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_BROADCAST, IoBridge.booleanToInt((Boolean)object2));
                                        return;
                                    }
                                    Libcore.os.setsockoptIpMreqn((FileDescriptor)object, OsConstants.IPPROTO_IP, OsConstants.IP_MULTICAST_IF, (Integer)object2);
                                    Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IPV6, OsConstants.IPV6_MULTICAST_IF, (Integer)object2);
                                    return;
                                }
                                Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_REUSEADDR, IoBridge.booleanToInt((Boolean)object2));
                                return;
                            }
                            Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IP, OsConstants.IP_TOS, (Integer)object2);
                            Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IPV6, OsConstants.IPV6_TCLASS, (Integer)object2);
                            return;
                        }
                        object2 = StructTimeval.fromMillis(((Integer)object2).intValue());
                        Libcore.os.setsockoptTimeval((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_RCVTIMEO, (StructTimeval)object2);
                        return;
                    }
                    boolean bl = false;
                    n = 0;
                    if (object2 instanceof Integer) {
                        bl = true;
                        n = Math.min((Integer)object2, 65535);
                    }
                    object2 = new StructLinger(IoBridge.booleanToInt(bl), n);
                    Libcore.os.setsockoptLinger((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_LINGER, (StructLinger)object2);
                    return;
                }
                Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IP, OsConstants.IP_TTL, (Integer)object2);
                Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_IPV6, OsConstants.IPV6_UNICAST_HOPS, (Integer)object2);
                return;
            }
            Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_KEEPALIVE, IoBridge.booleanToInt((Boolean)object2));
            return;
        }
        Libcore.os.setsockoptInt((FileDescriptor)object, OsConstants.IPPROTO_TCP, OsConstants.TCP_NODELAY, IoBridge.booleanToInt((Boolean)object2));
    }

    public static FileDescriptor socket(int n, int n2, int n3) throws SocketException {
        try {
            FileDescriptor fileDescriptor = Libcore.os.socket(n, n2, n3);
            return fileDescriptor;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsSocketException();
        }
    }

    public static void write(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws IOException {
        ArrayUtils.throwsIfOutOfBounds(arrby.length, n, n2);
        int n3 = n2;
        if (n2 == 0) {
            return;
        }
        while (n3 > 0) {
            try {
                n2 = Libcore.os.write(fileDescriptor, arrby, n, n3);
                n3 -= n2;
                n += n2;
            }
            catch (ErrnoException errnoException) {
                throw errnoException.rethrowAsIOException();
            }
        }
    }
}

