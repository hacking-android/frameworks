/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.OsConstants
 */
package android.net.util;

import android.system.OsConstants;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class IpUtils {
    public static String addressAndPortToString(InetAddress inetAddress, int n) {
        String string2 = inetAddress instanceof Inet6Address ? "[%s]:%d" : "%s:%d";
        return String.format(string2, inetAddress.getHostAddress(), n);
    }

    private static int checksum(ByteBuffer byteBuffer, int n, int n2, int n3) {
        int n4 = byteBuffer.position();
        byteBuffer.position(n2);
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        byteBuffer.position(n4);
        int n5 = (n3 - n2) / 2;
        for (n4 = 0; n4 < n5; ++n4) {
            n += IpUtils.intAbs(shortBuffer.get(n4));
        }
        n4 = n2 + n5 * 2;
        n2 = n;
        if (n3 != n4) {
            n2 = n3 = (int)byteBuffer.get(n4);
            if (n3 < 0) {
                n2 = (short)(n3 + 256);
            }
            n2 = n + n2 * 256;
        }
        n = (n2 >> 16 & 65535) + (n2 & 65535);
        return IpUtils.intAbs((short)((n >> 16 & 65535) + n & 65535));
    }

    private static int intAbs(short s) {
        return 65535 & s;
    }

    public static short ipChecksum(ByteBuffer byteBuffer, int n) {
        return (short)IpUtils.checksum(byteBuffer, 0, n, (byte)(byteBuffer.get(n) & 15) * 4 + n);
    }

    private static byte ipversion(ByteBuffer byteBuffer, int n) {
        return (byte)((byteBuffer.get(n) & -16) >> 4);
    }

    public static boolean isValidUdpOrTcpPort(int n) {
        boolean bl = n > 0 && n < 65536;
        return bl;
    }

    private static int pseudoChecksumIPv4(ByteBuffer byteBuffer, int n, int n2, int n3) {
        return n2 + n3 + IpUtils.intAbs(byteBuffer.getShort(n + 12)) + IpUtils.intAbs(byteBuffer.getShort(n + 14)) + IpUtils.intAbs(byteBuffer.getShort(n + 16)) + IpUtils.intAbs(byteBuffer.getShort(n + 18));
    }

    private static int pseudoChecksumIPv6(ByteBuffer byteBuffer, int n, int n2, int n3) {
        n3 = n2 + n3;
        for (n2 = 8; n2 < 40; n2 += 2) {
            n3 += IpUtils.intAbs(byteBuffer.getShort(n + n2));
        }
        return n3;
    }

    public static short tcpChecksum(ByteBuffer byteBuffer, int n, int n2, int n3) {
        return IpUtils.transportChecksum(byteBuffer, OsConstants.IPPROTO_TCP, n, n2, n3);
    }

    private static short transportChecksum(ByteBuffer object, int n, int n2, int n3, int n4) {
        block5 : {
            block8 : {
                block7 : {
                    byte by;
                    block6 : {
                        if (n4 < 0) break block5;
                        by = IpUtils.ipversion((ByteBuffer)object, n2);
                        if (by != 4) break block6;
                        n2 = IpUtils.pseudoChecksumIPv4((ByteBuffer)object, n2, n, n4);
                        break block7;
                    }
                    if (by != 6) break block8;
                    n2 = IpUtils.pseudoChecksumIPv6((ByteBuffer)object, n2, n, n4);
                }
                n2 = n3 = IpUtils.checksum((ByteBuffer)object, n2, n3, n3 + n4);
                if (n == OsConstants.IPPROTO_UDP) {
                    n2 = n3;
                    if (n3 == 0) {
                        n2 = -1;
                    }
                }
                return (short)n2;
            }
            throw new UnsupportedOperationException("Checksum must be IPv4 or IPv6");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Transport length < 0: ");
        ((StringBuilder)object).append(n4);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static short udpChecksum(ByteBuffer byteBuffer, int n, int n2) {
        int n3 = IpUtils.intAbs(byteBuffer.getShort(n2 + 4));
        return IpUtils.transportChecksum(byteBuffer, OsConstants.IPPROTO_UDP, n, n2, n3);
    }
}

