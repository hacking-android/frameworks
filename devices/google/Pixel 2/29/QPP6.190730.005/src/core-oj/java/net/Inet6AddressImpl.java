/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.GaiException
 *  android.system.OsConstants
 *  android.system.StructAddrinfo
 *  android.system.StructIcmpHdr
 *  dalvik.system.BlockGuard
 *  java.net.AddressCache
 *  libcore.io.IoBridge
 *  libcore.io.Libcore
 *  libcore.io.Os
 *  libcore.net.InetAddressUtils
 */
package java.net;

import android.system.ErrnoException;
import android.system.GaiException;
import android.system.OsConstants;
import android.system.StructAddrinfo;
import android.system.StructIcmpHdr;
import dalvik.system.BlockGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.net.AddressCache;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetAddressImpl;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import libcore.io.IoBridge;
import libcore.io.Libcore;
import libcore.io.Os;
import libcore.net.InetAddressUtils;

class Inet6AddressImpl
implements InetAddressImpl {
    private static final AddressCache addressCache = new AddressCache();
    private static InetAddress anyLocalAddress;
    private static InetAddress[] loopbackAddresses;

    Inet6AddressImpl() {
    }

    private String getHostByAddr0(byte[] object) throws UnknownHostException {
        Serializable serializable = InetAddress.getByAddress(object);
        try {
            object = Libcore.os.getnameinfo((InetAddress)serializable, OsConstants.NI_NAMEREQD);
            return object;
        }
        catch (GaiException gaiException) {
            serializable = new UnknownHostException(((InetAddress)serializable).toString());
            ((Throwable)serializable).initCause(gaiException);
            throw serializable;
        }
    }

    private static InetAddress[] lookupHostByName(String string, int n) throws UnknownHostException {
        Object object;
        int n2;
        BlockGuard.getThreadPolicy().onNetwork();
        InetAddress[] arrinetAddress = addressCache.get(string, n);
        if (arrinetAddress != null) {
            if (arrinetAddress instanceof InetAddress[]) {
                return arrinetAddress;
            }
            throw new UnknownHostException((String)arrinetAddress);
        }
        arrinetAddress = new StructAddrinfo();
        arrinetAddress.ai_flags = OsConstants.AI_ADDRCONFIG;
        arrinetAddress.ai_family = OsConstants.AF_UNSPEC;
        arrinetAddress.ai_socktype = OsConstants.SOCK_STREAM;
        arrinetAddress = Libcore.os.android_getaddrinfo(string, (StructAddrinfo)arrinetAddress, n);
        int n3 = arrinetAddress.length;
        for (n2 = 0; n2 < n3; ++n2) {
            object = arrinetAddress[n2];
            object.holder().hostName = string;
            object.holder().originalHostName = string;
            continue;
        }
        try {
            addressCache.put(string, n, arrinetAddress);
            return arrinetAddress;
        }
        catch (GaiException gaiException) {
            if (gaiException.getCause() instanceof ErrnoException && ((n2 = ((ErrnoException)gaiException.getCause()).errno) == OsConstants.EACCES || n2 == OsConstants.EPERM)) {
                throw new SecurityException("Permission denied (missing INTERNET permission?)", gaiException);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to resolve host \"");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("\": ");
            ((StringBuilder)object).append(Libcore.os.gai_strerror(gaiException.error));
            object = ((StringBuilder)object).toString();
            addressCache.putUnknownHost(string, n, (String)object);
            throw gaiException.rethrowAsUnknownHostException((String)object);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean tcpEcho(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2) throws IOException {
        Throwable throwable2222;
        FileDescriptor fileDescriptor = null;
        FileDescriptor fileDescriptor2 = null;
        boolean bl = true;
        FileDescriptor fileDescriptor3 = IoBridge.socket((int)OsConstants.AF_INET6, (int)OsConstants.SOCK_STREAM, (int)0);
        if (n2 > 0) {
            fileDescriptor2 = fileDescriptor3;
            fileDescriptor = fileDescriptor3;
            IoBridge.setSocketOption((FileDescriptor)fileDescriptor3, (int)25, (Object)n2);
        }
        if (inetAddress2 != null) {
            fileDescriptor2 = fileDescriptor3;
            fileDescriptor = fileDescriptor3;
            IoBridge.bind((FileDescriptor)fileDescriptor3, (InetAddress)inetAddress2, (int)0);
        }
        fileDescriptor2 = fileDescriptor3;
        fileDescriptor = fileDescriptor3;
        IoBridge.connect((FileDescriptor)fileDescriptor3, (InetAddress)inetAddress, (int)7, (int)n);
        IoBridge.closeAndSignalBlockedThreads((FileDescriptor)fileDescriptor3);
        return true;
        {
            block7 : {
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                fileDescriptor2 = fileDescriptor;
                {
                    Throwable throwable3 = iOException.getCause();
                    fileDescriptor2 = fileDescriptor;
                    if (throwable3 instanceof ErrnoException) {
                        fileDescriptor2 = fileDescriptor;
                        n = ((ErrnoException)throwable3).errno;
                        fileDescriptor2 = fileDescriptor;
                        n2 = OsConstants.ECONNREFUSED;
                        if (n == n2) break block7;
                    }
                    bl = false;
                }
            }
            IoBridge.closeAndSignalBlockedThreads((FileDescriptor)fileDescriptor);
            return bl;
        }
        IoBridge.closeAndSignalBlockedThreads(fileDescriptor2);
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public InetAddress anyLocalAddress() {
        synchronized (Inet6AddressImpl.class) {
            if (anyLocalAddress != null) return anyLocalAddress;
            InetAddress inetAddress = new Inet6Address();
            inetAddress.holder().hostName = "::";
            anyLocalAddress = inetAddress;
            return anyLocalAddress;
        }
    }

    @Override
    public void clearAddressCache() {
        addressCache.clear();
    }

    @Override
    public String getHostByAddr(byte[] arrby) throws UnknownHostException {
        BlockGuard.getThreadPolicy().onNetwork();
        return this.getHostByAddr0(arrby);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected boolean icmpEcho(InetAddress inetAddress, int n, InetAddress object, int n2) throws IOException {
        block26 : {
            void var1_11;
            Object object2;
            block25 : {
                int n4;
                int n3;
                boolean bl;
                block28 : {
                    block24 : {
                        bl = inetAddress instanceof Inet4Address;
                        n3 = bl ? OsConstants.AF_INET : OsConstants.AF_INET6;
                        n4 = bl ? OsConstants.IPPROTO_ICMP : OsConstants.IPPROTO_ICMPV6;
                        object2 = IoBridge.socket((int)n3, (int)OsConstants.SOCK_DGRAM, (int)n4);
                        if (n2 <= 0) break block24;
                        try {
                            IoBridge.setSocketOption((FileDescriptor)object2, (int)25, (Object)n2);
                        }
                        catch (Throwable throwable) {
                            break block25;
                        }
                        catch (IOException iOException) {
                            object = object2;
                            break block26;
                        }
                    }
                    if (object != null) {
                        IoBridge.bind((FileDescriptor)object2, (InetAddress)object, (int)0);
                    }
                    n2 = 0;
                    object = object2;
                    break block28;
                    catch (Throwable throwable) {
                        object2 = null;
                        break block25;
                    }
                    catch (IOException iOException) {
                        object = null;
                        break block26;
                    }
                }
                while (n > 0) {
                    block27 : {
                        n3 = 1000;
                        if (n < 1000) {
                            n3 = n;
                        }
                        IoBridge.setSocketOption((FileDescriptor)object, (int)4102, (Object)n3);
                        byte[] arrby = StructIcmpHdr.IcmpEchoHdr((boolean)bl, (int)n2).getBytes();
                        IoBridge.sendto((FileDescriptor)object, (byte[])arrby, (int)0, (int)arrby.length, (int)0, (InetAddress)inetAddress, (int)0);
                        int n5 = IoBridge.getLocalInetSocketAddress((FileDescriptor)object).getPort();
                        byte[] arrby2 = new byte[arrby.length];
                        DatagramPacket datagramPacket = new DatagramPacket(arrby2, arrby.length);
                        n4 = arrby2.length;
                        object2 = object;
                        try {
                            if (IoBridge.recvfrom((boolean)true, (FileDescriptor)object, (byte[])arrby2, (int)0, (int)n4, (int)0, (DatagramPacket)datagramPacket, (boolean)false) != arrby.length) break block27;
                            n4 = bl ? (int)((byte)OsConstants.ICMP_ECHOREPLY) : (int)((byte)OsConstants.ICMP6_ECHO_REPLY);
                        }
                        catch (Throwable throwable) {
                            break block25;
                        }
                        catch (IOException iOException) {
                            object = object2;
                            break block26;
                        }
                        if (datagramPacket.getAddress().equals(inetAddress) && arrby2[0] == n4 && arrby2[4] == (byte)(n5 >> 8) && arrby2[5] == (byte)n5 && (n5 = arrby2[6]) == (byte)((n4 = n2) >> 8) && (n5 = arrby2[7]) == (byte)n4) {
                            if (object2 == null) return true;
                            try {
                                Libcore.os.close((FileDescriptor)object2);
                                return true;
                            }
                            catch (ErrnoException errnoException) {
                                // empty catch block
                            }
                            return true;
                        }
                    }
                    n -= n3;
                    ++n2;
                    object = object2;
                    continue;
                    catch (Throwable throwable) {
                        object2 = object;
                        break block25;
                    }
                    catch (IOException iOException) {
                        break block26;
                    }
                }
                if (object == null) return false;
                try {
                    Libcore.os.close((FileDescriptor)object);
                    return false;
                }
                catch (ErrnoException errnoException) {
                    return false;
                }
            }
            if (object2 == null) throw var1_11;
            try {
                Libcore.os.close(object2);
                throw var1_11;
            }
            catch (ErrnoException errnoException) {
                // empty catch block
            }
            throw var1_11;
        }
        if (object == null) return false;
        try {
            Libcore.os.close((FileDescriptor)object);
            return false;
        }
        catch (ErrnoException errnoException) {
            // empty catch block
        }
        return false;
    }

    @Override
    public boolean isReachable(InetAddress inetAddress, int n, NetworkInterface object, int n2) throws IOException {
        Object object2 = null;
        Object var6_6 = null;
        if (object != null) {
            object2 = ((NetworkInterface)object).getInetAddresses();
            do {
                object = var6_6;
            } while (object2.hasMoreElements() && !(object = object2.nextElement()).getClass().isInstance(inetAddress));
            object2 = object;
            if (object == null) {
                return false;
            }
        }
        if (this.icmpEcho(inetAddress, n, (InetAddress)object2, n2)) {
            return true;
        }
        return this.tcpEcho(inetAddress, n, (InetAddress)object2, n2);
    }

    @Override
    public InetAddress[] lookupAllHostAddr(String string, int n) throws UnknownHostException {
        if (string != null && !string.isEmpty()) {
            InetAddress inetAddress = InetAddressUtils.parseNumericAddressNoThrowStripOptionalBrackets((String)string);
            if (inetAddress != null) {
                return new InetAddress[]{inetAddress};
            }
            return Inet6AddressImpl.lookupHostByName(string, n);
        }
        return this.loopbackAddresses();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public InetAddress[] loopbackAddresses() {
        synchronized (Inet6AddressImpl.class) {
            if (loopbackAddresses != null) return loopbackAddresses;
            loopbackAddresses = new InetAddress[]{Inet6Address.LOOPBACK, Inet4Address.LOOPBACK};
            return loopbackAddresses;
        }
    }
}

