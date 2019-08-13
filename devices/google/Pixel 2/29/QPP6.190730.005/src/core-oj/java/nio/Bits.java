/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import sun.misc.Unsafe;
import sun.security.action.GetPropertyAction;

class Bits {
    private static final ByteOrder byteOrder;
    private static int pageSize;
    private static boolean unaligned;
    private static boolean unalignedKnown;
    private static final Unsafe unsafe;

    static {
        unsafe = Unsafe.getUnsafe();
        byteOrder = ByteOrder.LITTLE_ENDIAN;
        pageSize = -1;
        unalignedKnown = false;
    }

    private Bits() {
    }

    private static byte _get(long l) {
        return unsafe.getByte(l);
    }

    private static void _put(long l, byte by) {
        unsafe.putByte(l, by);
    }

    static ByteOrder byteOrder() {
        return byteOrder;
    }

    private static byte char0(char c) {
        return (byte)c;
    }

    private static byte char1(char c) {
        return (byte)(c >> 8);
    }

    static char getChar(long l, boolean bl) {
        char c;
        char c2;
        char c3 = bl ? (c = Bits.getCharB(l)) : (c2 = Bits.getCharL(l));
        return c3;
    }

    static char getChar(ByteBuffer byteBuffer, int n, boolean bl) {
        char c;
        if (bl) {
            n = Bits.getCharB(byteBuffer, n);
            c = n;
        } else {
            n = Bits.getCharL(byteBuffer, n);
            c = n;
        }
        return c;
    }

    static char getCharB(long l) {
        return Bits.makeChar(Bits._get(l), Bits._get(1L + l));
    }

    static char getCharB(ByteBuffer byteBuffer, int n) {
        return Bits.makeChar(byteBuffer._get(n), byteBuffer._get(n + 1));
    }

    static char getCharL(long l) {
        return Bits.makeChar(Bits._get(1L + l), Bits._get(l));
    }

    static char getCharL(ByteBuffer byteBuffer, int n) {
        return Bits.makeChar(byteBuffer._get(n + 1), byteBuffer._get(n));
    }

    static double getDouble(long l, boolean bl) {
        double d = bl ? Bits.getDoubleB(l) : Bits.getDoubleL(l);
        return d;
    }

    static double getDouble(ByteBuffer byteBuffer, int n, boolean bl) {
        double d = bl ? Bits.getDoubleB(byteBuffer, n) : Bits.getDoubleL(byteBuffer, n);
        return d;
    }

    static double getDoubleB(long l) {
        return Double.longBitsToDouble(Bits.getLongB(l));
    }

    static double getDoubleB(ByteBuffer byteBuffer, int n) {
        return Double.longBitsToDouble(Bits.getLongB(byteBuffer, n));
    }

    static double getDoubleL(long l) {
        return Double.longBitsToDouble(Bits.getLongL(l));
    }

    static double getDoubleL(ByteBuffer byteBuffer, int n) {
        return Double.longBitsToDouble(Bits.getLongL(byteBuffer, n));
    }

    static float getFloat(long l, boolean bl) {
        float f = bl ? Bits.getFloatB(l) : Bits.getFloatL(l);
        return f;
    }

    static float getFloat(ByteBuffer byteBuffer, int n, boolean bl) {
        float f = bl ? Bits.getFloatB(byteBuffer, n) : Bits.getFloatL(byteBuffer, n);
        return f;
    }

    static float getFloatB(long l) {
        return Float.intBitsToFloat(Bits.getIntB(l));
    }

    static float getFloatB(ByteBuffer byteBuffer, int n) {
        return Float.intBitsToFloat(Bits.getIntB(byteBuffer, n));
    }

    static float getFloatL(long l) {
        return Float.intBitsToFloat(Bits.getIntL(l));
    }

    static float getFloatL(ByteBuffer byteBuffer, int n) {
        return Float.intBitsToFloat(Bits.getIntL(byteBuffer, n));
    }

    static int getInt(long l, boolean bl) {
        int n = bl ? Bits.getIntB(l) : Bits.getIntL(l);
        return n;
    }

    static int getInt(ByteBuffer byteBuffer, int n, boolean bl) {
        n = bl ? Bits.getIntB(byteBuffer, n) : Bits.getIntL(byteBuffer, n);
        return n;
    }

    static int getIntB(long l) {
        return Bits.makeInt(Bits._get(l), Bits._get(1L + l), Bits._get(2L + l), Bits._get(3L + l));
    }

    static int getIntB(ByteBuffer byteBuffer, int n) {
        return Bits.makeInt(byteBuffer._get(n), byteBuffer._get(n + 1), byteBuffer._get(n + 2), byteBuffer._get(n + 3));
    }

    static int getIntL(long l) {
        return Bits.makeInt(Bits._get(3L + l), Bits._get(2L + l), Bits._get(1L + l), Bits._get(l));
    }

    static int getIntL(ByteBuffer byteBuffer, int n) {
        return Bits.makeInt(byteBuffer._get(n + 3), byteBuffer._get(n + 2), byteBuffer._get(n + 1), byteBuffer._get(n));
    }

    static long getLong(long l, boolean bl) {
        l = bl ? Bits.getLongB(l) : Bits.getLongL(l);
        return l;
    }

    static long getLong(ByteBuffer byteBuffer, int n, boolean bl) {
        long l = bl ? Bits.getLongB(byteBuffer, n) : Bits.getLongL(byteBuffer, n);
        return l;
    }

    static long getLongB(long l) {
        return Bits.makeLong(Bits._get(l), Bits._get(1L + l), Bits._get(2L + l), Bits._get(3L + l), Bits._get(4L + l), Bits._get(5L + l), Bits._get(6L + l), Bits._get(7L + l));
    }

    static long getLongB(ByteBuffer byteBuffer, int n) {
        return Bits.makeLong(byteBuffer._get(n), byteBuffer._get(n + 1), byteBuffer._get(n + 2), byteBuffer._get(n + 3), byteBuffer._get(n + 4), byteBuffer._get(n + 5), byteBuffer._get(n + 6), byteBuffer._get(n + 7));
    }

    static long getLongL(long l) {
        return Bits.makeLong(Bits._get(7L + l), Bits._get(6L + l), Bits._get(5L + l), Bits._get(4L + l), Bits._get(3L + l), Bits._get(2L + l), Bits._get(1L + l), Bits._get(l));
    }

    static long getLongL(ByteBuffer byteBuffer, int n) {
        return Bits.makeLong(byteBuffer._get(n + 7), byteBuffer._get(n + 6), byteBuffer._get(n + 5), byteBuffer._get(n + 4), byteBuffer._get(n + 3), byteBuffer._get(n + 2), byteBuffer._get(n + 1), byteBuffer._get(n));
    }

    static short getShort(long l, boolean bl) {
        short s;
        short s2;
        short s3 = bl ? (s = Bits.getShortB(l)) : (s2 = Bits.getShortL(l));
        return s3;
    }

    static short getShort(ByteBuffer byteBuffer, int n, boolean bl) {
        short s;
        if (bl) {
            n = Bits.getShortB(byteBuffer, n);
            s = n;
        } else {
            n = Bits.getShortL(byteBuffer, n);
            s = n;
        }
        return s;
    }

    static short getShortB(long l) {
        return Bits.makeShort(Bits._get(l), Bits._get(1L + l));
    }

    static short getShortB(ByteBuffer byteBuffer, int n) {
        return Bits.makeShort(byteBuffer._get(n), byteBuffer._get(n + 1));
    }

    static short getShortL(long l) {
        return Bits.makeShort(Bits._get(1L + l), Bits._get(l));
    }

    static short getShortL(ByteBuffer byteBuffer, int n) {
        return Bits.makeShort(byteBuffer._get(n + 1), byteBuffer._get(n));
    }

    private static byte int0(int n) {
        return (byte)n;
    }

    private static byte int1(int n) {
        return (byte)(n >> 8);
    }

    private static byte int2(int n) {
        return (byte)(n >> 16);
    }

    private static byte int3(int n) {
        return (byte)(n >> 24);
    }

    private static byte long0(long l) {
        return (byte)l;
    }

    private static byte long1(long l) {
        return (byte)(l >> 8);
    }

    private static byte long2(long l) {
        return (byte)(l >> 16);
    }

    private static byte long3(long l) {
        return (byte)(l >> 24);
    }

    private static byte long4(long l) {
        return (byte)(l >> 32);
    }

    private static byte long5(long l) {
        return (byte)(l >> 40);
    }

    private static byte long6(long l) {
        return (byte)(l >> 48);
    }

    private static byte long7(long l) {
        return (byte)(l >> 56);
    }

    private static char makeChar(byte by, byte by2) {
        return (char)(by << 8 | by2 & 255);
    }

    private static int makeInt(byte by, byte by2, byte by3, byte by4) {
        return by << 24 | (by2 & 255) << 16 | (by3 & 255) << 8 | by4 & 255;
    }

    private static long makeLong(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8) {
        return (long)by << 56 | ((long)by2 & 255L) << 48 | ((long)by3 & 255L) << 40 | ((long)by4 & 255L) << 32 | ((long)by5 & 255L) << 24 | ((long)by6 & 255L) << 16 | ((long)by7 & 255L) << 8 | (long)by8 & 255L;
    }

    private static short makeShort(byte by, byte by2) {
        return (short)(by << 8 | by2 & 255);
    }

    static int pageCount(long l) {
        return (int)((long)Bits.pageSize() + l - 1L) / Bits.pageSize();
    }

    static int pageSize() {
        if (pageSize == -1) {
            pageSize = Bits.unsafe().pageSize();
        }
        return pageSize;
    }

    static void putChar(long l, char c, boolean bl) {
        if (bl) {
            Bits.putCharB(l, c);
        } else {
            Bits.putCharL(l, c);
        }
    }

    static void putChar(ByteBuffer byteBuffer, int n, char c, boolean bl) {
        if (bl) {
            Bits.putCharB(byteBuffer, n, c);
        } else {
            Bits.putCharL(byteBuffer, n, c);
        }
    }

    static void putCharB(long l, char c) {
        Bits._put(l, Bits.char1(c));
        Bits._put(1L + l, Bits.char0(c));
    }

    static void putCharB(ByteBuffer byteBuffer, int n, char c) {
        byteBuffer._put(n, Bits.char1(c));
        byteBuffer._put(n + 1, Bits.char0(c));
    }

    static void putCharL(long l, char c) {
        Bits._put(l, Bits.char0(c));
        Bits._put(1L + l, Bits.char1(c));
    }

    static void putCharL(ByteBuffer byteBuffer, int n, char c) {
        byteBuffer._put(n, Bits.char0(c));
        byteBuffer._put(n + 1, Bits.char1(c));
    }

    static void putDouble(long l, double d, boolean bl) {
        if (bl) {
            Bits.putDoubleB(l, d);
        } else {
            Bits.putDoubleL(l, d);
        }
    }

    static void putDouble(ByteBuffer byteBuffer, int n, double d, boolean bl) {
        if (bl) {
            Bits.putDoubleB(byteBuffer, n, d);
        } else {
            Bits.putDoubleL(byteBuffer, n, d);
        }
    }

    static void putDoubleB(long l, double d) {
        Bits.putLongB(l, Double.doubleToRawLongBits(d));
    }

    static void putDoubleB(ByteBuffer byteBuffer, int n, double d) {
        Bits.putLongB(byteBuffer, n, Double.doubleToRawLongBits(d));
    }

    static void putDoubleL(long l, double d) {
        Bits.putLongL(l, Double.doubleToRawLongBits(d));
    }

    static void putDoubleL(ByteBuffer byteBuffer, int n, double d) {
        Bits.putLongL(byteBuffer, n, Double.doubleToRawLongBits(d));
    }

    static void putFloat(long l, float f, boolean bl) {
        if (bl) {
            Bits.putFloatB(l, f);
        } else {
            Bits.putFloatL(l, f);
        }
    }

    static void putFloat(ByteBuffer byteBuffer, int n, float f, boolean bl) {
        if (bl) {
            Bits.putFloatB(byteBuffer, n, f);
        } else {
            Bits.putFloatL(byteBuffer, n, f);
        }
    }

    static void putFloatB(long l, float f) {
        Bits.putIntB(l, Float.floatToRawIntBits(f));
    }

    static void putFloatB(ByteBuffer byteBuffer, int n, float f) {
        Bits.putIntB(byteBuffer, n, Float.floatToRawIntBits(f));
    }

    static void putFloatL(long l, float f) {
        Bits.putIntL(l, Float.floatToRawIntBits(f));
    }

    static void putFloatL(ByteBuffer byteBuffer, int n, float f) {
        Bits.putIntL(byteBuffer, n, Float.floatToRawIntBits(f));
    }

    static void putInt(long l, int n, boolean bl) {
        if (bl) {
            Bits.putIntB(l, n);
        } else {
            Bits.putIntL(l, n);
        }
    }

    static void putInt(ByteBuffer byteBuffer, int n, int n2, boolean bl) {
        if (bl) {
            Bits.putIntB(byteBuffer, n, n2);
        } else {
            Bits.putIntL(byteBuffer, n, n2);
        }
    }

    static void putIntB(long l, int n) {
        Bits._put(l, Bits.int3(n));
        Bits._put(1L + l, Bits.int2(n));
        Bits._put(2L + l, Bits.int1(n));
        Bits._put(3L + l, Bits.int0(n));
    }

    static void putIntB(ByteBuffer byteBuffer, int n, int n2) {
        byteBuffer._put(n, Bits.int3(n2));
        byteBuffer._put(n + 1, Bits.int2(n2));
        byteBuffer._put(n + 2, Bits.int1(n2));
        byteBuffer._put(n + 3, Bits.int0(n2));
    }

    static void putIntL(long l, int n) {
        Bits._put(3L + l, Bits.int3(n));
        Bits._put(2L + l, Bits.int2(n));
        Bits._put(1L + l, Bits.int1(n));
        Bits._put(l, Bits.int0(n));
    }

    static void putIntL(ByteBuffer byteBuffer, int n, int n2) {
        byteBuffer._put(n + 3, Bits.int3(n2));
        byteBuffer._put(n + 2, Bits.int2(n2));
        byteBuffer._put(n + 1, Bits.int1(n2));
        byteBuffer._put(n, Bits.int0(n2));
    }

    static void putLong(long l, long l2, boolean bl) {
        if (bl) {
            Bits.putLongB(l, l2);
        } else {
            Bits.putLongL(l, l2);
        }
    }

    static void putLong(ByteBuffer byteBuffer, int n, long l, boolean bl) {
        if (bl) {
            Bits.putLongB(byteBuffer, n, l);
        } else {
            Bits.putLongL(byteBuffer, n, l);
        }
    }

    static void putLongB(long l, long l2) {
        Bits._put(l, Bits.long7(l2));
        Bits._put(1L + l, Bits.long6(l2));
        Bits._put(2L + l, Bits.long5(l2));
        Bits._put(3L + l, Bits.long4(l2));
        Bits._put(4L + l, Bits.long3(l2));
        Bits._put(5L + l, Bits.long2(l2));
        Bits._put(6L + l, Bits.long1(l2));
        Bits._put(7L + l, Bits.long0(l2));
    }

    static void putLongB(ByteBuffer byteBuffer, int n, long l) {
        byteBuffer._put(n, Bits.long7(l));
        byteBuffer._put(n + 1, Bits.long6(l));
        byteBuffer._put(n + 2, Bits.long5(l));
        byteBuffer._put(n + 3, Bits.long4(l));
        byteBuffer._put(n + 4, Bits.long3(l));
        byteBuffer._put(n + 5, Bits.long2(l));
        byteBuffer._put(n + 6, Bits.long1(l));
        byteBuffer._put(n + 7, Bits.long0(l));
    }

    static void putLongL(long l, long l2) {
        Bits._put(7L + l, Bits.long7(l2));
        Bits._put(6L + l, Bits.long6(l2));
        Bits._put(5L + l, Bits.long5(l2));
        Bits._put(4L + l, Bits.long4(l2));
        Bits._put(3L + l, Bits.long3(l2));
        Bits._put(2L + l, Bits.long2(l2));
        Bits._put(1L + l, Bits.long1(l2));
        Bits._put(l, Bits.long0(l2));
    }

    static void putLongL(ByteBuffer byteBuffer, int n, long l) {
        byteBuffer._put(n + 7, Bits.long7(l));
        byteBuffer._put(n + 6, Bits.long6(l));
        byteBuffer._put(n + 5, Bits.long5(l));
        byteBuffer._put(n + 4, Bits.long4(l));
        byteBuffer._put(n + 3, Bits.long3(l));
        byteBuffer._put(n + 2, Bits.long2(l));
        byteBuffer._put(n + 1, Bits.long1(l));
        byteBuffer._put(n, Bits.long0(l));
    }

    static void putShort(long l, short s, boolean bl) {
        if (bl) {
            Bits.putShortB(l, s);
        } else {
            Bits.putShortL(l, s);
        }
    }

    static void putShort(ByteBuffer byteBuffer, int n, short s, boolean bl) {
        if (bl) {
            Bits.putShortB(byteBuffer, n, s);
        } else {
            Bits.putShortL(byteBuffer, n, s);
        }
    }

    static void putShortB(long l, short s) {
        Bits._put(l, Bits.short1(s));
        Bits._put(1L + l, Bits.short0(s));
    }

    static void putShortB(ByteBuffer byteBuffer, int n, short s) {
        byteBuffer._put(n, Bits.short1(s));
        byteBuffer._put(n + 1, Bits.short0(s));
    }

    static void putShortL(long l, short s) {
        Bits._put(l, Bits.short0(s));
        Bits._put(1L + l, Bits.short1(s));
    }

    static void putShortL(ByteBuffer byteBuffer, int n, short s) {
        byteBuffer._put(n, Bits.short0(s));
        byteBuffer._put(n + 1, Bits.short1(s));
    }

    private static byte short0(short s) {
        return (byte)s;
    }

    private static byte short1(short s) {
        return (byte)(s >> 8);
    }

    static char swap(char c) {
        return Character.reverseBytes(c);
    }

    static int swap(int n) {
        return Integer.reverseBytes(n);
    }

    static long swap(long l) {
        return Long.reverseBytes(l);
    }

    static short swap(short s) {
        return Short.reverseBytes(s);
    }

    static boolean unaligned() {
        if (unalignedKnown) {
            return unaligned;
        }
        String string = AccessController.doPrivileged(new GetPropertyAction("os.arch"));
        boolean bl = string.equals("i386") || string.equals("x86") || string.equals("amd64") || string.equals("x86_64");
        unaligned = bl;
        unalignedKnown = true;
        return unaligned;
    }

    static Unsafe unsafe() {
        return unsafe;
    }
}

