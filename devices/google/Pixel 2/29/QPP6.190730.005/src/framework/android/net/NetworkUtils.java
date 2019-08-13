/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.DnsResolver;
import android.net.IpPrefix;
import android.net.Network;
import android.net.TcpRepairWindow;
import android.net.shared.Inet4AddressUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import java.io.FileDescriptor;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;

public class NetworkUtils {
    private static final int[] ADDRESS_FAMILIES = new int[]{OsConstants.AF_INET, OsConstants.AF_INET6};
    private static final String TAG = "NetworkUtils";

    public static boolean addressTypeMatches(InetAddress inetAddress, InetAddress inetAddress2) {
        boolean bl = inetAddress instanceof Inet4Address && inetAddress2 instanceof Inet4Address || inetAddress instanceof Inet6Address && inetAddress2 instanceof Inet6Address;
        return bl;
    }

    public static native void attachDropAllBPFFilter(FileDescriptor var0) throws SocketException;

    public static native boolean bindProcessToNetwork(int var0);

    @Deprecated
    public static native boolean bindProcessToNetworkForHostResolution(int var0);

    public static native int bindSocketToNetwork(int var0, int var1);

    private static TreeSet<IpPrefix> deduplicatePrefixSet(TreeSet<IpPrefix> object) {
        TreeSet<IpPrefix> treeSet = new TreeSet<IpPrefix>(((TreeSet)object).comparator());
        object = ((TreeSet)object).iterator();
        block0 : while (object.hasNext()) {
            IpPrefix ipPrefix = (IpPrefix)object.next();
            Iterator<IpPrefix> iterator = treeSet.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().containsPrefix(ipPrefix)) continue;
                continue block0;
            }
            treeSet.add(ipPrefix);
        }
        return treeSet;
    }

    public static native void detachBPFFilter(FileDescriptor var0) throws SocketException;

    public static native int getBoundNetworkForProcess();

    public static native Network getDnsNetwork() throws ErrnoException;

    @UnsupportedAppUsage
    public static int getImplicitNetmask(Inet4Address inet4Address) {
        return Inet4AddressUtils.getImplicitNetmask(inet4Address);
    }

    public static InetAddress getNetworkPart(InetAddress object, int n) {
        object = object.getAddress();
        NetworkUtils.maskRawAddress(object, n);
        try {
            object = InetAddress.getByAddress(object);
            return object;
        }
        catch (UnknownHostException unknownHostException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getNetworkPart error - ");
            stringBuilder.append(unknownHostException.toString());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public static native TcpRepairWindow getTcpRepairWindow(FileDescriptor var0) throws ErrnoException;

    public static InetAddress hexToInet6Address(String string2) throws IllegalArgumentException {
        try {
            InetAddress inetAddress = NetworkUtils.numericToInetAddress(String.format(Locale.US, "%s:%s:%s:%s:%s:%s:%s:%s", string2.substring(0, 4), string2.substring(4, 8), string2.substring(8, 12), string2.substring(12, 16), string2.substring(16, 20), string2.substring(20, 24), string2.substring(24, 28), string2.substring(28, 32)));
            return inetAddress;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("error in hexToInet6Address(");
            stringBuilder.append(string2);
            stringBuilder.append("): ");
            stringBuilder.append(exception);
            Log.e("NetworkUtils", stringBuilder.toString());
            throw new IllegalArgumentException(exception);
        }
    }

    @Deprecated
    public static int inetAddressToInt(Inet4Address inet4Address) throws IllegalArgumentException {
        return Inet4AddressUtils.inet4AddressToIntHTL(inet4Address);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static InetAddress intToInetAddress(int n) {
        return Inet4AddressUtils.intToInet4AddressHTL(n);
    }

    public static boolean isWeaklyValidatedHostname(String string2) {
        if (!string2.matches("^[a-zA-Z0-9_.-]+$")) {
            return false;
        }
        int[] arrn = ADDRESS_FAMILIES;
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            if (Os.inet_pton((int)arrn[i], (String)string2) == null) continue;
            return false;
        }
        return true;
    }

    public static String[] makeStrings(Collection<InetAddress> object) {
        String[] arrstring = new String[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrstring[n] = ((InetAddress)object.next()).getHostAddress();
            ++n;
        }
        return arrstring;
    }

    public static void maskRawAddress(byte[] arrby, int n) {
        if (n >= 0 && n <= arrby.length * 8) {
            int n2 = n / 8;
            n = (byte)(255 << 8 - n % 8);
            if (n2 < arrby.length) {
                arrby[n2] = (byte)(arrby[n2] & n);
            }
            for (n = n2 + 1; n < arrby.length; ++n) {
                arrby[n] = (byte)(false ? 1 : 0);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IP address with ");
        stringBuilder.append(arrby.length);
        stringBuilder.append(" bytes has invalid prefix length ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    public static int netmaskIntToPrefixLength(int n) {
        return Integer.bitCount(n);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static int netmaskToPrefixLength(Inet4Address inet4Address) {
        return Inet4AddressUtils.netmaskToPrefixLength(inet4Address);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static InetAddress numericToInetAddress(String string2) throws IllegalArgumentException {
        return InetAddress.parseNumericAddress((String)string2);
    }

    public static Pair<InetAddress, Integer> parseIpAndMask(String string2) {
        int n;
        Object object = null;
        Object object2 = null;
        int n2 = n = -1;
        int n3 = n;
        int n4 = n;
        int n5 = n;
        Object object3 = string2.split("/", 2);
        n2 = n;
        n3 = n;
        n4 = n;
        n5 = n;
        n2 = n = Integer.parseInt(object3[1]);
        n3 = n;
        n4 = n;
        n5 = n;
        try {
            object2 = object3 = InetAddress.parseNumericAddress((String)object3[0]);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object2 = object;
            n = n2;
        }
        catch (NumberFormatException numberFormatException) {
            n = n3;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            n = n4;
        }
        catch (NullPointerException nullPointerException) {
            n = n5;
        }
        if (object2 != null && n != -1) {
            return new Pair<StringBuilder, Integer>((StringBuilder)object2, n);
        }
        object2 = new StringBuilder();
        object2.append("Invalid IP address and mask ");
        object2.append(string2);
        throw new IllegalArgumentException(object2.toString());
    }

    @Deprecated
    @UnsupportedAppUsage
    public static int prefixLengthToNetmaskInt(int n) throws IllegalArgumentException {
        return Inet4AddressUtils.prefixLengthToV4NetmaskIntHTL(n);
    }

    public static native boolean protectFromVpn(int var0);

    @UnsupportedAppUsage
    public static boolean protectFromVpn(FileDescriptor fileDescriptor) {
        return NetworkUtils.protectFromVpn(fileDescriptor.getInt$());
    }

    public static native boolean queryUserAccess(int var0, int var1);

    public static native void resNetworkCancel(FileDescriptor var0);

    public static native FileDescriptor resNetworkQuery(int var0, String var1, int var2, int var3, int var4) throws ErrnoException;

    public static native DnsResolver.DnsResponse resNetworkResult(FileDescriptor var0) throws ErrnoException;

    public static native FileDescriptor resNetworkSend(int var0, byte[] var1, int var2, int var3) throws ErrnoException;

    public static long routedIPv4AddressCount(TreeSet<IpPrefix> object2) {
        long l = 0L;
        for (IpPrefix ipPrefix : NetworkUtils.deduplicatePrefixSet((TreeSet<IpPrefix>)object2)) {
            if (!ipPrefix.isIPv4()) {
                Log.wtf("NetworkUtils", "Non-IPv4 prefix in routedIPv4AddressCount");
            }
            l += 1L << 32 - ipPrefix.getPrefixLength();
        }
        return l;
    }

    public static BigInteger routedIPv6AddressCount(TreeSet<IpPrefix> serializable) {
        Object object = BigInteger.ZERO;
        Iterator<IpPrefix> iterator = NetworkUtils.deduplicatePrefixSet(serializable).iterator();
        serializable = object;
        while (iterator.hasNext()) {
            object = iterator.next();
            if (!((IpPrefix)object).isIPv6()) {
                Log.wtf("NetworkUtils", "Non-IPv6 prefix in routedIPv6AddressCount");
            }
            int n = ((IpPrefix)object).getPrefixLength();
            serializable = ((BigInteger)serializable).add(BigInteger.ONE.shiftLeft(128 - n));
        }
        return serializable;
    }

    public static native void setupRaSocket(FileDescriptor var0, int var1) throws SocketException;

    @UnsupportedAppUsage
    public static String trimV4AddrZeros(String string2) {
        if (string2 == null) {
            return null;
        }
        String[] arrstring = string2.split("\\.");
        if (arrstring.length != 4) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(16);
        for (int i = 0; i < 4; ++i) {
            block6 : {
                try {
                    if (arrstring[i].length() <= 3) break block6;
                    return string2;
                }
                catch (NumberFormatException numberFormatException) {
                    return string2;
                }
            }
            stringBuilder.append(Integer.parseInt(arrstring[i]));
            if (i >= 3) continue;
            stringBuilder.append('.');
        }
        return stringBuilder.toString();
    }
}

