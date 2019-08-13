/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.math.BigInteger;

public final class Long
extends Number
implements Comparable<Long> {
    public static final int BYTES = 8;
    public static final long MAX_VALUE = Long.MAX_VALUE;
    public static final long MIN_VALUE = Long.MIN_VALUE;
    public static final int SIZE = 64;
    public static final Class<Long> TYPE = Class.getPrimitiveClass("long");
    private static final long serialVersionUID = 4290774380558885855L;
    private final long value;

    public Long(long l) {
        this.value = l;
    }

    public Long(String string) throws NumberFormatException {
        this.value = Long.parseLong(string, 10);
    }

    public static int bitCount(long l) {
        l -= l >>> 1 & 0x5555555555555555L;
        l = (l & 0x3333333333333333L) + (0x3333333333333333L & l >>> 2);
        l = (l >>> 4) + l & 0xF0F0F0F0F0F0F0FL;
        l += l >>> 8;
        l += l >>> 16;
        return (int)(l + (l >>> 32)) & 127;
    }

    public static int compare(long l, long l2) {
        int n = l < l2 ? -1 : (l == l2 ? 0 : 1);
        return n;
    }

    public static int compareUnsigned(long l, long l2) {
        return Long.compare(l - Long.MIN_VALUE, Long.MIN_VALUE + l2);
    }

    public static Long decode(String object) throws NumberFormatException {
        block15 : {
            block16 : {
                Long l;
                block14 : {
                    int n;
                    int n2 = 10;
                    int n3 = 0;
                    int n4 = 0;
                    if (((String)object).length() == 0) break block15;
                    int n5 = ((String)object).charAt(0);
                    if (n5 == 45) {
                        n = 1;
                        n3 = 0 + 1;
                    } else {
                        n = n4;
                        if (n5 == 43) {
                            n3 = 0 + 1;
                            n = n4;
                        }
                    }
                    if (!((String)object).startsWith("0x", n3) && !((String)object).startsWith("0X", n3)) {
                        if (((String)object).startsWith("#", n3)) {
                            n5 = n3 + 1;
                            n4 = 16;
                        } else {
                            n4 = n2;
                            n5 = n3;
                            if (((String)object).startsWith("0", n3)) {
                                n4 = n2;
                                n5 = n3;
                                if (((String)object).length() > n3 + 1) {
                                    n5 = n3 + 1;
                                    n4 = 8;
                                }
                            }
                        }
                    } else {
                        n5 = n3 + 2;
                        n4 = 16;
                    }
                    if (((String)object).startsWith("-", n5) || ((String)object).startsWith("+", n5)) break block16;
                    l = Long.valueOf(((String)object).substring(n5), n4);
                    if (n == 0) break block14;
                    try {
                        l = -l.longValue();
                        object = l;
                    }
                    catch (NumberFormatException numberFormatException) {
                        if (n != 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("-");
                            stringBuilder.append(((String)object).substring(n5));
                            object = stringBuilder.toString();
                        } else {
                            object = ((String)object).substring(n5);
                        }
                        object = Long.valueOf((String)object, n4);
                    }
                }
                object = l;
                return object;
            }
            throw new NumberFormatException("Sign character in wrong position");
        }
        throw new NumberFormatException("Zero length string");
    }

    public static long divideUnsigned(long l, long l2) {
        long l3 = 0L;
        if (l2 < 0L) {
            l = Long.compareUnsigned(l, l2) < 0 ? l3 : 1L;
            return l;
        }
        if (l > 0L) {
            return l / l2;
        }
        return Long.toUnsignedBigInteger(l).divide(Long.toUnsignedBigInteger(l2)).longValue();
    }

    static int formatUnsignedLong(long l, int n, char[] arrc, int n2, int n3) {
        int n4;
        do {
            n4 = n3 - 1;
            arrc[n2 + n4] = Integer.digits[(int)l & (1 << n) - 1];
            if ((l >>>= n) == 0L) break;
            n3 = n4;
        } while (n4 > 0);
        return n4;
    }

    static void getChars(long l, int n, char[] arrc) {
        int n2;
        int n3;
        int n4 = n;
        int n5 = 0;
        n = n4;
        long l2 = l;
        if (l < 0L) {
            n5 = 45;
            l2 = -l;
            n = n4;
        }
        while (l2 > Integer.MAX_VALUE) {
            l = l2 / 100L;
            n4 = (int)(l2 - ((l << 6) + (l << 5) + (l << 2)));
            l2 = l;
            arrc[--n] = Integer.DigitOnes[n4];
            arrc[--n] = Integer.DigitTens[n4];
        }
        n4 = (int)l2;
        do {
            n3 = n;
            n2 = n4;
            if (n4 < 65536) break;
            n3 = n4 / 100;
            n2 = n4 - ((n3 << 6) + (n3 << 5) + (n3 << 2));
            n4 = n3;
            arrc[--n] = Integer.DigitOnes[n2];
            arrc[--n] = Integer.DigitTens[n2];
        } while (true);
        do {
            n = 52429 * n2 >>> 19;
            n4 = n3 - 1;
            arrc[n4] = Integer.digits[n2 - ((n << 3) + (n << 1))];
            n3 = n4;
            n2 = n;
        } while (n != 0);
        if (n5 != 0) {
            arrc[n4 - 1] = (char)n5;
        }
    }

    public static Long getLong(String string) {
        return Long.getLong(string, null);
    }

    public static Long getLong(String object, long l) {
        block0 : {
            if ((object = Long.getLong((String)object, null)) != null) break block0;
            object = l;
        }
        return object;
    }

    public static Long getLong(String object, Long l) {
        Object var2_4 = null;
        try {
            object = System.getProperty((String)object);
        }
        catch (IllegalArgumentException | NullPointerException runtimeException) {
            object = var2_4;
        }
        if (object != null) {
            try {
                object = Long.decode((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return l;
    }

    public static int hashCode(long l) {
        return (int)(l >>> 32 ^ l);
    }

    public static long highestOneBit(long l) {
        l |= l >> 1;
        l |= l >> 2;
        l |= l >> 4;
        l |= l >> 8;
        l |= l >> 16;
        l |= l >> 32;
        return l - (l >>> 1);
    }

    public static long lowestOneBit(long l) {
        return -l & l;
    }

    public static long max(long l, long l2) {
        return Math.max(l, l2);
    }

    public static long min(long l, long l2) {
        return Math.min(l, l2);
    }

    public static int numberOfLeadingZeros(long l) {
        int n;
        if (l == 0L) {
            return 64;
        }
        int n2 = 1;
        int n3 = n = (int)(l >>> 32);
        if (n == 0) {
            n2 = 1 + 32;
            n3 = (int)l;
        }
        int n4 = n2;
        n = n3;
        if (n3 >>> 16 == 0) {
            n4 = n2 + 16;
            n = n3 << 16;
        }
        n2 = n4;
        n3 = n;
        if (n >>> 24 == 0) {
            n2 = n4 + 8;
            n3 = n << 8;
        }
        n4 = n2;
        n = n3;
        if (n3 >>> 28 == 0) {
            n4 = n2 + 4;
            n = n3 << 4;
        }
        n2 = n4;
        n3 = n;
        if (n >>> 30 == 0) {
            n2 = n4 + 2;
            n3 = n << 2;
        }
        return n2 - (n3 >>> 31);
    }

    public static int numberOfTrailingZeros(long l) {
        if (l == 0L) {
            return 64;
        }
        int n = 63;
        int n2 = (int)l;
        if (n2 != 0) {
            n = 63 - 32;
        } else {
            n2 = (int)(l >>> 32);
        }
        int n3 = n2 << 16;
        int n4 = n;
        if (n3 != 0) {
            n4 = n - 16;
            n2 = n3;
        }
        n3 = n2 << 8;
        n = n4;
        if (n3 != 0) {
            n = n4 - 8;
            n2 = n3;
        }
        n3 = n2 << 4;
        n4 = n;
        if (n3 != 0) {
            n4 = n - 4;
            n2 = n3;
        }
        n3 = n2 << 2;
        n = n4;
        if (n3 != 0) {
            n = n4 - 2;
            n2 = n3;
        }
        return n - (n2 << 1 >>> 31);
    }

    public static long parseLong(String string) throws NumberFormatException {
        return Long.parseLong(string, 10);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static long parseLong(String charSequence, int n) throws NumberFormatException {
        if (charSequence == null) throw new NumberFormatException("null");
        if (n >= 2) {
            if (n <= 36) {
                long l = 0L;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = ((String)charSequence).length();
                long l2 = -9223372036854775807L;
                if (n5 <= 0) throw NumberFormatException.forInputString((String)charSequence);
                char c = ((String)charSequence).charAt(0);
                long l3 = l2;
                if (c < '0') {
                    if (c == '-') {
                        n4 = 1;
                        l2 = Long.MIN_VALUE;
                    } else {
                        if (c != '+') throw NumberFormatException.forInputString((String)charSequence);
                        n4 = n3;
                    }
                    if (n5 == 1) throw NumberFormatException.forInputString((String)charSequence);
                    n3 = 0 + 1;
                    n2 = n4;
                    n4 = n3;
                    l3 = l2;
                }
                long l4 = l3 / (long)n;
                l2 = l;
                while (n4 < n5) {
                    n3 = Character.digit(((String)charSequence).charAt(n4), n);
                    if (n3 < 0) throw NumberFormatException.forInputString((String)charSequence);
                    if (l2 < l4) throw NumberFormatException.forInputString((String)charSequence);
                    if ((l2 *= (long)n) < (long)n3 + l3) throw NumberFormatException.forInputString((String)charSequence);
                    l2 -= (long)n3;
                    ++n4;
                }
                if (n2 == 0) return -l2;
                return l2;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("radix ");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" greater than Character.MAX_RADIX");
            throw new NumberFormatException(((StringBuilder)charSequence).toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("radix ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" less than Character.MIN_RADIX");
        throw new NumberFormatException(((StringBuilder)charSequence).toString());
    }

    public static long parseUnsignedLong(String string) throws NumberFormatException {
        return Long.parseUnsignedLong(string, 10);
    }

    public static long parseUnsignedLong(String string, int n) throws NumberFormatException {
        if (string != null) {
            int n2 = string.length();
            if (n2 > 0) {
                if (string.charAt(0) != '-') {
                    if (n2 > 12 && (n != 10 || n2 > 18)) {
                        long l = Long.parseLong(string.substring(0, n2 - 1), n);
                        if ((n2 = Character.digit(string.charAt(n2 - 1), n)) >= 0) {
                            long l2 = (long)n * l + (long)n2;
                            if (Long.compareUnsigned(l2, l) >= 0) {
                                return l2;
                            }
                            throw new NumberFormatException(String.format("String value %s exceeds range of unsigned long.", string));
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Bad digit at end of ");
                        stringBuilder.append(string);
                        throw new NumberFormatException(stringBuilder.toString());
                    }
                    return Long.parseLong(string, n);
                }
                throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s.", string));
            }
            throw NumberFormatException.forInputString(string);
        }
        throw new NumberFormatException("null");
    }

    public static long remainderUnsigned(long l, long l2) {
        if (l > 0L && l2 > 0L) {
            return l % l2;
        }
        if (Long.compareUnsigned(l, l2) < 0) {
            return l;
        }
        return Long.toUnsignedBigInteger(l).remainder(Long.toUnsignedBigInteger(l2)).longValue();
    }

    public static long reverse(long l) {
        l = (l & 0x5555555555555555L) << 1 | 0x5555555555555555L & l >>> 1;
        l = (l & 0x3333333333333333L) << 2 | 0x3333333333333333L & l >>> 2;
        l = (l & 0xF0F0F0F0F0F0F0FL) << 4 | 0xF0F0F0F0F0F0F0FL & l >>> 4;
        l = (l & 0xFF00FF00FF00FFL) << 8 | 0xFF00FF00FF00FFL & l >>> 8;
        return l << 48 | (l & 0xFFFF0000L) << 16 | 0xFFFF0000L & l >>> 16 | l >>> 48;
    }

    public static long reverseBytes(long l) {
        l = (l & 0xFF00FF00FF00FFL) << 8 | 0xFF00FF00FF00FFL & l >>> 8;
        return l << 48 | (l & 0xFFFF0000L) << 16 | 0xFFFF0000L & l >>> 16 | l >>> 48;
    }

    public static long rotateLeft(long l, int n) {
        return l << n | l >>> -n;
    }

    public static long rotateRight(long l, int n) {
        return l >>> n | l << -n;
    }

    public static int signum(long l) {
        return (int)(l >> 63 | -l >>> 63);
    }

    static int stringSize(long l) {
        long l2 = 10L;
        for (int i = 1; i < 19; ++i) {
            if (l < l2) {
                return i;
            }
            l2 *= 10L;
        }
        return 19;
    }

    public static long sum(long l, long l2) {
        return l + l2;
    }

    public static String toBinaryString(long l) {
        return Long.toUnsignedString0(l, 1);
    }

    public static String toHexString(long l) {
        return Long.toUnsignedString0(l, 4);
    }

    public static String toOctalString(long l) {
        return Long.toUnsignedString0(l, 3);
    }

    public static String toString(long l) {
        if (l == Long.MIN_VALUE) {
            return "-9223372036854775808";
        }
        int n = l < 0L ? Long.stringSize(-l) + 1 : Long.stringSize(l);
        char[] arrc = new char[n];
        Long.getChars(l, n, arrc);
        return new String(arrc);
    }

    public static String toString(long l, int n) {
        int n2;
        block8 : {
            block7 : {
                if (n < 2) break block7;
                n2 = n;
                if (n <= 36) break block8;
            }
            n2 = 10;
        }
        if (n2 == 10) {
            return Long.toString(l);
        }
        char[] arrc = new char[65];
        int n3 = 64;
        boolean bl = l < 0L;
        n = n3;
        long l2 = l;
        if (!bl) {
            l2 = -l;
            n = n3;
        }
        while (l2 <= (long)(-n2)) {
            arrc[n] = Integer.digits[(int)(-(l2 % (long)n2))];
            l2 /= (long)n2;
            --n;
        }
        arrc[n] = Integer.digits[(int)(-l2)];
        n2 = n;
        if (bl) {
            n2 = n - 1;
            arrc[n2] = (char)45;
        }
        return new String(arrc, n2, 65 - n2);
    }

    private static BigInteger toUnsignedBigInteger(long l) {
        if (l >= 0L) {
            return BigInteger.valueOf(l);
        }
        int n = (int)(l >>> 32);
        int n2 = (int)l;
        return BigInteger.valueOf(Integer.toUnsignedLong(n)).shiftLeft(32).add(BigInteger.valueOf(Integer.toUnsignedLong(n2)));
    }

    public static String toUnsignedString(long l) {
        return Long.toUnsignedString(l, 10);
    }

    public static String toUnsignedString(long l, int n) {
        if (l >= 0L) {
            return Long.toString(l, n);
        }
        if (n != 2) {
            if (n != 4) {
                if (n != 8) {
                    if (n != 10) {
                        if (n != 16) {
                            if (n != 32) {
                                return Long.toUnsignedBigInteger(l).toString(n);
                            }
                            return Long.toUnsignedString0(l, 5);
                        }
                        return Long.toHexString(l);
                    }
                    long l2 = (l >>> 1) / 5L;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Long.toString(l2));
                    stringBuilder.append(l - 10L * l2);
                    return stringBuilder.toString();
                }
                return Long.toOctalString(l);
            }
            return Long.toUnsignedString0(l, 2);
        }
        return Long.toBinaryString(l);
    }

    static String toUnsignedString0(long l, int n) {
        int n2 = Math.max((n - 1 + (64 - Long.numberOfLeadingZeros(l))) / n, 1);
        char[] arrc = new char[n2];
        Long.formatUnsignedLong(l, n, arrc, 0, n2);
        return new String(arrc);
    }

    public static Long valueOf(long l) {
        if (l >= -128L && l <= 127L) {
            return LongCache.cache[(int)l + 128];
        }
        return new Long(l);
    }

    public static Long valueOf(String string) throws NumberFormatException {
        return Long.parseLong(string, 10);
    }

    public static Long valueOf(String string, int n) throws NumberFormatException {
        return Long.parseLong(string, n);
    }

    @Override
    public byte byteValue() {
        return (byte)this.value;
    }

    @Override
    public int compareTo(Long l) {
        return Long.compare(this.value, l.value);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Long;
        boolean bl2 = false;
        if (bl) {
            if (this.value == (Long)object) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    public int hashCode() {
        return Long.hashCode(this.value);
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public short shortValue() {
        return (short)this.value;
    }

    public String toString() {
        return Long.toString(this.value);
    }

    private static class LongCache {
        static final Long[] cache;

        static {
            Long[] arrlong;
            cache = new Long[256];
            for (int i = 0; i < (arrlong = cache).length; ++i) {
                arrlong[i] = new Long(i - 128);
            }
        }

        private LongCache() {
        }
    }

}

