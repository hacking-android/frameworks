/*
 * Decompiled with CFR 0.145.
 */
package android.net.shared;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Inet4AddressUtils {
    public static Inet4Address getBroadcastAddress(Inet4Address inet4Address, int n) throws IllegalArgumentException {
        return Inet4AddressUtils.intToInet4AddressHTH(Inet4AddressUtils.inet4AddressToIntHTH(inet4Address) | Inet4AddressUtils.prefixLengthToV4NetmaskIntHTH(n));
    }

    public static int getImplicitNetmask(Inet4Address inet4Address) {
        int n = inet4Address.getAddress()[0] & 255;
        if (n < 128) {
            return 8;
        }
        if (n < 192) {
            return 16;
        }
        if (n < 224) {
            return 24;
        }
        return 32;
    }

    public static Inet4Address getPrefixMaskAsInet4Address(int n) throws IllegalArgumentException {
        return Inet4AddressUtils.intToInet4AddressHTH(Inet4AddressUtils.prefixLengthToV4NetmaskIntHTH(n));
    }

    public static int inet4AddressToIntHTH(Inet4Address arrby) throws IllegalArgumentException {
        arrby = arrby.getAddress();
        return (arrby[0] & 255) << 24 | (arrby[1] & 255) << 16 | (arrby[2] & 255) << 8 | arrby[3] & 255;
    }

    public static int inet4AddressToIntHTL(Inet4Address inet4Address) {
        return Integer.reverseBytes(Inet4AddressUtils.inet4AddressToIntHTH(inet4Address));
    }

    public static Inet4Address intToInet4AddressHTH(int n) {
        byte by = (byte)(n >> 24 & 255);
        byte by2 = (byte)(n >> 16 & 255);
        byte by3 = (byte)(n >> 8 & 255);
        byte by4 = (byte)(n & 255);
        try {
            Inet4Address inet4Address = (Inet4Address)InetAddress.getByAddress(new byte[]{by, by2, by3, by4});
            return inet4Address;
        }
        catch (UnknownHostException unknownHostException) {
            throw new AssertionError();
        }
    }

    public static Inet4Address intToInet4AddressHTL(int n) {
        return Inet4AddressUtils.intToInet4AddressHTH(Integer.reverseBytes(n));
    }

    public static int netmaskToPrefixLength(Inet4Address serializable) {
        int n = Inet4AddressUtils.inet4AddressToIntHTH((Inet4Address)serializable);
        int n2 = Integer.bitCount(n);
        if (Integer.numberOfTrailingZeros(n) == 32 - n2) {
            return n2;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Non-contiguous netmask: ");
        ((StringBuilder)serializable).append(Integer.toHexString(n));
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public static int prefixLengthToV4NetmaskIntHTH(int n) throws IllegalArgumentException {
        if (n >= 0 && n <= 32) {
            n = n == 0 ? 0 : -1 << 32 - n;
            return n;
        }
        throw new IllegalArgumentException("Invalid prefix length (0 <= prefix <= 32)");
    }

    public static int prefixLengthToV4NetmaskIntHTL(int n) throws IllegalArgumentException {
        return Integer.reverseBytes(Inet4AddressUtils.prefixLengthToV4NetmaskIntHTH(n));
    }
}

