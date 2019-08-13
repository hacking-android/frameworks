/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.IntFunction;

public final class BitUtils {
    private BitUtils() {
    }

    public static long bitAt(int n) {
        return 1L << n;
    }

    public static int bytesToBEInt(byte[] arrby) {
        return (BitUtils.uint8(arrby[0]) << 24) + (BitUtils.uint8(arrby[1]) << 16) + (BitUtils.uint8(arrby[2]) << 8) + BitUtils.uint8(arrby[3]);
    }

    public static int bytesToLEInt(byte[] arrby) {
        return Integer.reverseBytes(BitUtils.bytesToBEInt(arrby));
    }

    public static String flagsToString(int n, IntFunction<String> intFunction) {
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        while (n != 0) {
            int n3 = 1 << Integer.numberOfTrailingZeros(n);
            n &= n3;
            if (n2 > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(intFunction.apply(n3));
            ++n2;
        }
        TextUtils.wrap(stringBuilder, "[", "]");
        return stringBuilder.toString();
    }

    public static int getUint16(ByteBuffer byteBuffer, int n) {
        return BitUtils.uint16(byteBuffer.getShort(n));
    }

    public static long getUint32(ByteBuffer byteBuffer, int n) {
        return BitUtils.uint32(byteBuffer.getInt(n));
    }

    public static int getUint8(ByteBuffer byteBuffer, int n) {
        return BitUtils.uint8(byteBuffer.get(n));
    }

    public static boolean isBitSet(long l, int n) {
        boolean bl = (BitUtils.bitAt(n) & l) != 0L;
        return bl;
    }

    public static boolean maskedEquals(byte by, byte by2, byte by3) {
        boolean bl = (by & by3) == (by2 & by3);
        return bl;
    }

    public static boolean maskedEquals(long l, long l2, long l3) {
        boolean bl = (l & l3) == (l2 & l3);
        return bl;
    }

    public static boolean maskedEquals(UUID uUID, UUID uUID2, UUID uUID3) {
        if (uUID3 == null) {
            return Objects.equals(uUID, uUID2);
        }
        boolean bl = BitUtils.maskedEquals(uUID.getLeastSignificantBits(), uUID2.getLeastSignificantBits(), uUID3.getLeastSignificantBits()) && BitUtils.maskedEquals(uUID.getMostSignificantBits(), uUID2.getMostSignificantBits(), uUID3.getMostSignificantBits());
        return bl;
    }

    public static boolean maskedEquals(byte[] arrby, byte[] arrby2, byte[] arrby3) {
        boolean bl = false;
        if (arrby != null && arrby2 != null) {
            bl = arrby.length == arrby2.length;
            Preconditions.checkArgument(bl, "Inputs must be of same size");
            if (arrby3 == null) {
                return Arrays.equals(arrby, arrby2);
            }
            bl = arrby.length == arrby3.length;
            Preconditions.checkArgument(bl, "Mask must be of same size as inputs");
            for (int i = 0; i < arrby3.length; ++i) {
                if (BitUtils.maskedEquals(arrby[i], arrby2[i], arrby3[i])) continue;
                return false;
            }
            return true;
        }
        if (arrby == arrby2) {
            bl = true;
        }
        return bl;
    }

    public static long packBits(int[] arrn) {
        long l = 0L;
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            l |= (long)(1 << arrn[i]);
        }
        return l;
    }

    public static void put(ByteBuffer byteBuffer, int n, byte[] arrby) {
        int n2 = byteBuffer.position();
        byteBuffer.position(n);
        byteBuffer.put(arrby);
        byteBuffer.position(n2);
    }

    public static byte[] toBytes(long l) {
        return ByteBuffer.allocate(8).putLong(l).array();
    }

    public static int uint16(byte by, byte by2) {
        return (by & 255) << 8 | by2 & 255;
    }

    public static int uint16(short s) {
        return 65535 & s;
    }

    public static long uint32(int n) {
        return (long)n & 0xFFFFFFFFL;
    }

    public static int uint8(byte by) {
        return by & 255;
    }

    public static int[] unpackBits(long l) {
        int[] arrn = new int[Long.bitCount(l)];
        int n = 0;
        int n2 = 0;
        while (l > 0L) {
            int n3 = n;
            if ((l & 1L) == 1L) {
                arrn[n] = n2;
                n3 = n + 1;
            }
            l >>= 1;
            ++n2;
            n = n3;
        }
        return arrn;
    }
}

