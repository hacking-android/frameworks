/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import java.nio.charset.Charset;

final class Util {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Util() {
    }

    public static boolean arrayRangeEquals(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            if (arrby[i + n] == arrby2[i + n2]) continue;
            return false;
        }
        return true;
    }

    public static void checkOffsetAndCount(long l, long l2, long l3) {
        if ((l2 | l3) >= 0L && l2 <= l && l - l2 >= l3) {
            return;
        }
        throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", l, l2, l3));
    }

    public static int reverseBytesInt(int n) {
        return (-16777216 & n) >>> 24 | (16711680 & n) >>> 8 | (65280 & n) << 8 | (n & 255) << 24;
    }

    public static long reverseBytesLong(long l) {
        return (-72057594037927936L & l) >>> 56 | (0xFF000000000000L & l) >>> 40 | (0xFF0000000000L & l) >>> 24 | (0xFF00000000L & l) >>> 8 | (0xFF000000L & l) << 8 | (0xFF0000L & l) << 24 | (65280L & l) << 40 | (255L & l) << 56;
    }

    public static short reverseBytesShort(short s) {
        s = (short)(65535 & s);
        return (short)((65280 & s) >>> 8 | (s & 255) << 8);
    }

    public static void sneakyRethrow(Throwable throwable) {
        Util.sneakyThrow2(throwable);
    }

    private static <T extends Throwable> void sneakyThrow2(Throwable throwable) throws Throwable {
        throw throwable;
    }
}

