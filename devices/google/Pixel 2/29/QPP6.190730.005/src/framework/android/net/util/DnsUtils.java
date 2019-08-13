/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  libcore.io.IoUtils
 */
package android.net.util;

import android.net.InetAddresses;
import android.net.Network;
import android.net.util._$$Lambda$DnsUtils$E7rjA1PKdcqMJSVvye8jaivYDec;
import android.net.util._$$Lambda$DnsUtils$GlRZOd_k4dipl4wcKx5eyR_B_sU;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.android.internal.util.BitUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import libcore.io.IoUtils;

public class DnsUtils {
    private static final int CHAR_BIT = 8;
    public static final int IPV6_ADDR_SCOPE_GLOBAL = 14;
    public static final int IPV6_ADDR_SCOPE_LINKLOCAL = 2;
    public static final int IPV6_ADDR_SCOPE_NODELOCAL = 1;
    public static final int IPV6_ADDR_SCOPE_SITELOCAL = 5;
    private static final String TAG = "DnsUtils";
    private static final Comparator<SortableAddress> sRfc6724Comparator = new Rfc6724Comparator();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static boolean checkConnectivity(Network var0, int var1_4, SocketAddress var2_5) {
        try {
            var3_6 = Os.socket((int)var1_4, (int)OsConstants.SOCK_DGRAM, (int)OsConstants.IPPROTO_UDP);
            if (var0 == null) ** GOTO lbl8
        }
        catch (ErrnoException var0_3) {
            return false;
        }
        try {
            var0.bindSocket(var3_6);
lbl8: // 2 sources:
            Os.connect((FileDescriptor)var3_6, (SocketAddress)var2_5);
            return true;
        }
        catch (ErrnoException | IOException var0_2) {
            return false;
        }
        finally {
            IoUtils.closeQuietly((FileDescriptor)var3_6);
        }
    }

    private static int compareIpv6PrefixMatchLen(InetAddress arrby, InetAddress arrby2) {
        if ((arrby = arrby.getAddress()).length != (arrby2 = arrby2.getAddress()).length) {
            return 0;
        }
        for (int i = 0; i < arrby2.length; ++i) {
            if (arrby[i] == arrby2[i]) {
                continue;
            }
            return i * 8 + (Integer.numberOfLeadingZeros(BitUtils.uint8(arrby[i]) ^ BitUtils.uint8(arrby2[i])) - 24);
        }
        return arrby2.length * 8;
    }

    private static int findLabel(InetAddress inetAddress) {
        if (DnsUtils.isIpv4Address(inetAddress)) {
            return 4;
        }
        if (DnsUtils.isIpv6Address(inetAddress)) {
            if (inetAddress.isLoopbackAddress()) {
                return 0;
            }
            if (DnsUtils.isIpv6Address6To4(inetAddress)) {
                return 2;
            }
            if (DnsUtils.isIpv6AddressTeredo(inetAddress)) {
                return 5;
            }
            if (DnsUtils.isIpv6AddressULA(inetAddress)) {
                return 13;
            }
            if (((Inet6Address)inetAddress).isIPv4CompatibleAddress()) {
                return 3;
            }
            if (inetAddress.isSiteLocalAddress()) {
                return 11;
            }
            if (DnsUtils.isIpv6Address6Bone(inetAddress)) {
                return 12;
            }
            return 1;
        }
        return 1;
    }

    private static int findPrecedence(InetAddress inetAddress) {
        if (DnsUtils.isIpv4Address(inetAddress)) {
            return 35;
        }
        if (DnsUtils.isIpv6Address(inetAddress)) {
            if (inetAddress.isLoopbackAddress()) {
                return 50;
            }
            if (DnsUtils.isIpv6Address6To4(inetAddress)) {
                return 30;
            }
            if (DnsUtils.isIpv6AddressTeredo(inetAddress)) {
                return 5;
            }
            if (DnsUtils.isIpv6AddressULA(inetAddress)) {
                return 3;
            }
            if (!(((Inet6Address)inetAddress).isIPv4CompatibleAddress() || inetAddress.isSiteLocalAddress() || DnsUtils.isIpv6Address6Bone(inetAddress))) {
                return 40;
            }
            return 1;
        }
        return 1;
    }

    private static int findScope(InetAddress inetAddress) {
        if (DnsUtils.isIpv6Address(inetAddress)) {
            if (inetAddress.isMulticastAddress()) {
                return DnsUtils.getIpv6MulticastScope(inetAddress);
            }
            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                if (inetAddress.isSiteLocalAddress()) {
                    return 5;
                }
                return 14;
            }
            return 2;
        }
        if (DnsUtils.isIpv4Address(inetAddress)) {
            if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                return 14;
            }
            return 2;
        }
        return 1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static InetAddress findSrcAddress(Network var0, InetAddress var1_3) {
        if (DnsUtils.isIpv4Address(var1_3)) {
            var2_5 = OsConstants.AF_INET;
        } else {
            if (DnsUtils.isIpv6Address(var1_3) == false) return null;
            var2_5 = OsConstants.AF_INET6;
        }
        try {
            var3_6 = Os.socket((int)var2_5, (int)OsConstants.SOCK_DGRAM, (int)OsConstants.IPPROTO_UDP);
            if (var0 == null) ** GOTO lbl20
        }
        catch (ErrnoException var1_4) {
            var0 = new StringBuilder();
            var0.append("findSrcAddress:");
            var0.append(var1_4.toString());
            Log.e("DnsUtils", var0.toString());
            return null;
        }
        try {
            var0.bindSocket(var3_6);
lbl20: // 2 sources:
            var0 = new InetSocketAddress(var1_3, 0);
            Os.connect((FileDescriptor)var3_6, (SocketAddress)var0);
            var0 = ((InetSocketAddress)Os.getsockname((FileDescriptor)var3_6)).getAddress();
            return var0;
        }
        catch (ErrnoException | IOException var0_2) {
            return null;
        }
        finally {
            IoUtils.closeQuietly((FileDescriptor)var3_6);
        }
    }

    private static int getIpv6MulticastScope(InetAddress inetAddress) {
        int n = !DnsUtils.isIpv6Address(inetAddress) ? 0 : inetAddress.getAddress()[1] & 15;
        return n;
    }

    public static boolean haveIpv4(Network network) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddresses.parseNumericAddress("8.8.8.8"), 0);
        return DnsUtils.checkConnectivity(network, OsConstants.AF_INET, inetSocketAddress);
    }

    public static boolean haveIpv6(Network network) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddresses.parseNumericAddress("2000::"), 0);
        return DnsUtils.checkConnectivity(network, OsConstants.AF_INET6, inetSocketAddress);
    }

    private static boolean isIpv4Address(InetAddress inetAddress) {
        return inetAddress instanceof Inet4Address;
    }

    private static boolean isIpv6Address(InetAddress inetAddress) {
        return inetAddress instanceof Inet6Address;
    }

    private static boolean isIpv6Address6Bone(InetAddress arrby) {
        boolean bl = DnsUtils.isIpv6Address((InetAddress)arrby);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        arrby = arrby.getAddress();
        bl = bl2;
        if (arrby[0] == 63) {
            bl = bl2;
            if (arrby[1] == -2) {
                bl = true;
            }
        }
        return bl;
    }

    private static boolean isIpv6Address6To4(InetAddress arrby) {
        boolean bl = DnsUtils.isIpv6Address((InetAddress)arrby);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        arrby = arrby.getAddress();
        bl = bl2;
        if (arrby[0] == 32) {
            bl = bl2;
            if (arrby[1] == 2) {
                bl = true;
            }
        }
        return bl;
    }

    private static boolean isIpv6AddressTeredo(InetAddress arrby) {
        boolean bl = DnsUtils.isIpv6Address((InetAddress)arrby);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        arrby = arrby.getAddress();
        bl = bl2;
        if (arrby[0] == 32) {
            bl = bl2;
            if (arrby[1] == 1) {
                bl = bl2;
                if (arrby[2] == 0) {
                    bl = bl2;
                    if (arrby[3] == 0) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    private static boolean isIpv6AddressULA(InetAddress inetAddress) {
        boolean bl;
        boolean bl2 = DnsUtils.isIpv6Address(inetAddress);
        boolean bl3 = bl = false;
        if (bl2) {
            bl3 = bl;
            if ((inetAddress.getAddress()[0] & 254) == 252) {
                bl3 = true;
            }
        }
        return bl3;
    }

    static /* synthetic */ void lambda$rfc6724Sort$0(List list, Network network, InetAddress inetAddress) {
        list.add(new SortableAddress(inetAddress, DnsUtils.findSrcAddress(network, inetAddress)));
    }

    static /* synthetic */ void lambda$rfc6724Sort$1(List list, SortableAddress sortableAddress) {
        list.add(sortableAddress.address);
    }

    public static List<InetAddress> rfc6724Sort(Network object, List<InetAddress> list) {
        ArrayList arrayList = new ArrayList();
        list.forEach(new _$$Lambda$DnsUtils$E7rjA1PKdcqMJSVvye8jaivYDec(arrayList, (Network)object));
        Collections.sort(arrayList, sRfc6724Comparator);
        object = new ArrayList();
        arrayList.forEach(new _$$Lambda$DnsUtils$GlRZOd_k4dipl4wcKx5eyR_B_sU((List)object));
        return object;
    }

    public static class Rfc6724Comparator
    implements Comparator<SortableAddress> {
        @Override
        public int compare(SortableAddress sortableAddress, SortableAddress sortableAddress2) {
            if (sortableAddress.hasSrcAddr != sortableAddress2.hasSrcAddr) {
                return sortableAddress2.hasSrcAddr - sortableAddress.hasSrcAddr;
            }
            if (sortableAddress.scopeMatch != sortableAddress2.scopeMatch) {
                return sortableAddress2.scopeMatch - sortableAddress.scopeMatch;
            }
            if (sortableAddress.labelMatch != sortableAddress2.labelMatch) {
                return sortableAddress2.labelMatch - sortableAddress.labelMatch;
            }
            if (sortableAddress.precedence != sortableAddress2.precedence) {
                return sortableAddress2.precedence - sortableAddress.precedence;
            }
            if (sortableAddress.scope != sortableAddress2.scope) {
                return sortableAddress.scope - sortableAddress2.scope;
            }
            if (sortableAddress.prefixMatchLen != sortableAddress2.prefixMatchLen) {
                return sortableAddress2.prefixMatchLen - sortableAddress.prefixMatchLen;
            }
            return 0;
        }
    }

    public static class SortableAddress {
        public final InetAddress address;
        public final int hasSrcAddr;
        public final int label;
        public final int labelMatch;
        public final int precedence;
        public final int prefixMatchLen;
        public final int scope;
        public final int scopeMatch;

        public SortableAddress(InetAddress inetAddress, InetAddress inetAddress2) {
            this.address = inetAddress;
            int n = 1;
            int n2 = inetAddress2 != null ? 1 : 0;
            this.hasSrcAddr = n2;
            this.label = DnsUtils.findLabel(inetAddress);
            this.scope = DnsUtils.findScope(inetAddress);
            this.precedence = DnsUtils.findPrecedence(inetAddress);
            n2 = inetAddress2 != null && this.label == DnsUtils.findLabel(inetAddress2) ? 1 : 0;
            this.labelMatch = n2;
            n2 = inetAddress2 != null && this.scope == DnsUtils.findScope(inetAddress2) ? n : 0;
            this.scopeMatch = n2;
            this.prefixMatchLen = DnsUtils.isIpv6Address(inetAddress) && DnsUtils.isIpv6Address(inetAddress2) ? DnsUtils.compareIpv6PrefixMatchLen(inetAddress2, inetAddress) : 0;
        }
    }

}

