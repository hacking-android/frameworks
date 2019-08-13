/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import sun.misc.VM;

public final class Integer
extends Number
implements Comparable<Integer> {
    public static final int BYTES = 4;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final int MAX_VALUE = Integer.MAX_VALUE;
    public static final int MIN_VALUE = Integer.MIN_VALUE;
    public static final int SIZE = 32;
    private static final String[] SMALL_NEG_VALUES;
    private static final String[] SMALL_NONNEG_VALUES;
    public static final Class<Integer> TYPE;
    static final char[] digits;
    private static final long serialVersionUID = 1360826667806852920L;
    static final int[] sizeTable;
    private final int value;

    static {
        TYPE = Class.getPrimitiveClass("int");
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        SMALL_NEG_VALUES = new String[100];
        SMALL_NONNEG_VALUES = new String[100];
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
    }

    public Integer(int n) {
        this.value = n;
    }

    public Integer(String string) throws NumberFormatException {
        this.value = Integer.parseInt(string, 10);
    }

    public static int bitCount(int n) {
        n -= n >>> 1 & 1431655765;
        n = (n & 858993459) + (858993459 & n >>> 2);
        n = (n >>> 4) + n & 252645135;
        n += n >>> 8;
        return n + (n >>> 16) & 63;
    }

    public static int compare(int n, int n2) {
        n = n < n2 ? -1 : (n == n2 ? 0 : 1);
        return n;
    }

    public static int compareUnsigned(int n, int n2) {
        return Integer.compare(n - Integer.MIN_VALUE, Integer.MIN_VALUE + n2);
    }

    public static Integer decode(String object) throws NumberFormatException {
        block15 : {
            block16 : {
                Integer n;
                block14 : {
                    int n2;
                    int n3 = 10;
                    int n4 = 0;
                    int n5 = 0;
                    if (((String)object).length() == 0) break block15;
                    int n6 = ((String)object).charAt(0);
                    if (n6 == 45) {
                        n2 = 1;
                        n4 = 0 + 1;
                    } else {
                        n2 = n5;
                        if (n6 == 43) {
                            n4 = 0 + 1;
                            n2 = n5;
                        }
                    }
                    if (!((String)object).startsWith("0x", n4) && !((String)object).startsWith("0X", n4)) {
                        if (((String)object).startsWith("#", n4)) {
                            n6 = n4 + 1;
                            n5 = 16;
                        } else {
                            n5 = n3;
                            n6 = n4;
                            if (((String)object).startsWith("0", n4)) {
                                n5 = n3;
                                n6 = n4;
                                if (((String)object).length() > n4 + 1) {
                                    n6 = n4 + 1;
                                    n5 = 8;
                                }
                            }
                        }
                    } else {
                        n6 = n4 + 2;
                        n5 = 16;
                    }
                    if (((String)object).startsWith("-", n6) || ((String)object).startsWith("+", n6)) break block16;
                    n = Integer.valueOf(((String)object).substring(n6), n5);
                    if (n2 == 0) break block14;
                    try {
                        n = -n.intValue();
                        object = n;
                    }
                    catch (NumberFormatException numberFormatException) {
                        if (n2 != 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("-");
                            stringBuilder.append(((String)object).substring(n6));
                            object = stringBuilder.toString();
                        } else {
                            object = ((String)object).substring(n6);
                        }
                        object = Integer.valueOf((String)object, n5);
                    }
                }
                object = n;
                return object;
            }
            throw new NumberFormatException("Sign character in wrong position");
        }
        throw new NumberFormatException("Zero length string");
    }

    public static int divideUnsigned(int n, int n2) {
        return (int)(Integer.toUnsignedLong(n) / Integer.toUnsignedLong(n2));
    }

    static int formatUnsignedInt(int n, int n2, char[] arrc, int n3, int n4) {
        int n5;
        do {
            n5 = n4 - 1;
            arrc[n3 + n5] = digits[n & (1 << n2) - 1];
            if ((n >>>= n2) == 0) break;
            n4 = n5;
        } while (n5 > 0);
        return n5;
    }

    static void getChars(int n, int n2, char[] arrc) {
        int n3 = n2;
        int n4 = 0;
        int n5 = n3;
        n2 = n;
        if (n < 0) {
            n4 = 45;
            n2 = -n;
            n5 = n3;
        }
        do {
            n3 = n5;
            n = n2;
            if (n2 < 65536) break;
            n = n2 / 100;
            n3 = n2 - ((n << 6) + (n << 5) + (n << 2));
            n2 = n;
            n = n5 - 1;
            arrc[n] = DigitOnes[n3];
            n5 = n - 1;
            arrc[n5] = DigitTens[n3];
        } while (true);
        do {
            n2 = 52429 * n >>> 19;
            n5 = n3 - 1;
            arrc[n5] = digits[n - ((n2 << 3) + (n2 << 1))];
            n3 = n5;
            n = n2;
        } while (n2 != 0);
        if (n4 != 0) {
            arrc[n5 - 1] = (char)n4;
        }
    }

    public static Integer getInteger(String string) {
        return Integer.getInteger(string, null);
    }

    public static Integer getInteger(String object, int n) {
        block0 : {
            if ((object = Integer.getInteger((String)object, null)) != null) break block0;
            object = n;
        }
        return object;
    }

    public static Integer getInteger(String object, Integer n) {
        Object var2_4 = null;
        try {
            object = System.getProperty((String)object);
        }
        catch (IllegalArgumentException | NullPointerException runtimeException) {
            object = var2_4;
        }
        if (object != null) {
            try {
                object = Integer.decode((String)object);
                return object;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return n;
    }

    public static int hashCode(int n) {
        return n;
    }

    public static int highestOneBit(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n - (n >>> 1);
    }

    public static int lowestOneBit(int n) {
        return -n & n;
    }

    public static int max(int n, int n2) {
        return Math.max(n, n2);
    }

    public static int min(int n, int n2) {
        return Math.min(n, n2);
    }

    public static int numberOfLeadingZeros(int n) {
        if (n == 0) {
            return 32;
        }
        int n2 = 1;
        int n3 = n;
        if (n >>> 16 == 0) {
            n2 = 1 + 16;
            n3 = n << 16;
        }
        int n4 = n2;
        n = n3;
        if (n3 >>> 24 == 0) {
            n4 = n2 + 8;
            n = n3 << 8;
        }
        n2 = n4;
        n3 = n;
        if (n >>> 28 == 0) {
            n2 = n4 + 4;
            n3 = n << 4;
        }
        n4 = n2;
        n = n3;
        if (n3 >>> 30 == 0) {
            n4 = n2 + 2;
            n = n3 << 2;
        }
        return n4 - (n >>> 31);
    }

    public static int numberOfTrailingZeros(int n) {
        if (n == 0) {
            return 32;
        }
        int n2 = 31;
        int n3 = n << 16;
        int n4 = n;
        if (n3 != 0) {
            n2 = 31 - 16;
            n4 = n3;
        }
        n3 = n4 << 8;
        n = n2;
        if (n3 != 0) {
            n = n2 - 8;
            n4 = n3;
        }
        n3 = n4 << 4;
        n2 = n;
        if (n3 != 0) {
            n2 = n - 4;
            n4 = n3;
        }
        n3 = n4 << 2;
        n = n2;
        if (n3 != 0) {
            n = n2 - 2;
            n4 = n3;
        }
        return n - (n4 << 1 >>> 31);
    }

    public static int parseInt(String string) throws NumberFormatException {
        return Integer.parseInt(string, 10);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int parseInt(String charSequence, int n) throws NumberFormatException {
        if (charSequence == null) throw new NumberFormatException("s == null");
        if (n >= 2) {
            if (n <= 36) {
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = ((String)charSequence).length();
                int n7 = -2147483647;
                if (n6 <= 0) throw NumberFormatException.forInputString((String)charSequence);
                char c = ((String)charSequence).charAt(0);
                int n8 = n7;
                if (c < '0') {
                    if (c == '-') {
                        n5 = 1;
                        n8 = Integer.MIN_VALUE;
                    } else {
                        if (c != '+') throw NumberFormatException.forInputString((String)charSequence);
                        n8 = n7;
                        n5 = n4;
                    }
                    if (n6 == 1) throw NumberFormatException.forInputString((String)charSequence);
                    n7 = 0 + 1;
                    n3 = n5;
                    n5 = n7;
                }
                n4 = n8 / n;
                n7 = n5;
                n5 = n2;
                while (n7 < n6) {
                    n2 = Character.digit(((String)charSequence).charAt(n7), n);
                    if (n2 < 0) throw NumberFormatException.forInputString((String)charSequence);
                    if (n5 < n4) throw NumberFormatException.forInputString((String)charSequence);
                    if ((n5 *= n) < n8 + n2) throw NumberFormatException.forInputString((String)charSequence);
                    n5 -= n2;
                    ++n7;
                }
                if (n3 == 0) return -n5;
                return n5;
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

    public static int parseUnsignedInt(String string) throws NumberFormatException {
        return Integer.parseUnsignedInt(string, 10);
    }

    public static int parseUnsignedInt(String string, int n) throws NumberFormatException {
        if (string != null) {
            int n2 = string.length();
            if (n2 > 0) {
                if (string.charAt(0) != '-') {
                    if (n2 > 5 && (n != 10 || n2 > 9)) {
                        long l = Long.parseLong(string, n);
                        if ((-4294967296L & l) == 0L) {
                            return (int)l;
                        }
                        throw new NumberFormatException(String.format("String value %s exceeds range of unsigned int.", string));
                    }
                    return Integer.parseInt(string, n);
                }
                throw new NumberFormatException(String.format("Illegal leading minus sign on unsigned string %s.", string));
            }
            throw NumberFormatException.forInputString(string);
        }
        throw new NumberFormatException("null");
    }

    public static int remainderUnsigned(int n, int n2) {
        return (int)(Integer.toUnsignedLong(n) % Integer.toUnsignedLong(n2));
    }

    public static int reverse(int n) {
        n = (n & 1431655765) << 1 | 1431655765 & n >>> 1;
        n = (n & 858993459) << 2 | 858993459 & n >>> 2;
        n = (n & 252645135) << 4 | 252645135 & n >>> 4;
        return n << 24 | (n & 65280) << 8 | 65280 & n >>> 8 | n >>> 24;
    }

    public static int reverseBytes(int n) {
        return n >>> 24 | n >> 8 & 65280 | n << 8 & 16711680 | n << 24;
    }

    public static int rotateLeft(int n, int n2) {
        return n << n2 | n >>> -n2;
    }

    public static int rotateRight(int n, int n2) {
        return n >>> n2 | n << -n2;
    }

    public static int signum(int n) {
        return n >> 31 | -n >>> 31;
    }

    static int stringSize(int n) {
        int n2 = 0;
        while (n > sizeTable[n2]) {
            ++n2;
        }
        return n2 + 1;
    }

    public static int sum(int n, int n2) {
        return n + n2;
    }

    public static String toBinaryString(int n) {
        return Integer.toUnsignedString0(n, 1);
    }

    public static String toHexString(int n) {
        return Integer.toUnsignedString0(n, 4);
    }

    public static String toOctalString(int n) {
        return Integer.toUnsignedString0(n, 3);
    }

    public static String toString(int n) {
        if (n == Integer.MIN_VALUE) {
            return "-2147483648";
        }
        int n2 = n < 0 ? 1 : 0;
        boolean bl = n2 != 0 ? n > -100 : n < 100;
        if (bl) {
            String[] arrstring = n2 != 0 ? SMALL_NEG_VALUES : SMALL_NONNEG_VALUES;
            if (n2 != 0) {
                n2 = n = -n;
                if (arrstring[n] == null) {
                    String string = n < 10 ? new String(new char[]{'-', DigitOnes[n]}) : new String(new char[]{'-', DigitTens[n], DigitOnes[n]});
                    arrstring[n] = string;
                    n2 = n;
                }
            } else {
                n2 = n;
                if (arrstring[n] == null) {
                    String string = n < 10 ? new String(new char[]{DigitOnes[n]}) : new String(new char[]{DigitTens[n], DigitOnes[n]});
                    arrstring[n] = string;
                    n2 = n;
                }
            }
            return arrstring[n2];
        }
        n2 = n2 != 0 ? Integer.stringSize(-n) + 1 : Integer.stringSize(n);
        char[] arrc = new char[n2];
        Integer.getChars(n, n2, arrc);
        return new String(arrc);
    }

    public static String toString(int n, int n2) {
        int n3;
        int n4;
        block8 : {
            block7 : {
                if (n2 < 2) break block7;
                n4 = n2;
                if (n2 <= 36) break block8;
            }
            n4 = 10;
        }
        if (n4 == 10) {
            return Integer.toString(n);
        }
        char[] arrc = new char[33];
        boolean bl = n < 0;
        n2 = n3 = 32;
        int n5 = n;
        if (!bl) {
            n5 = -n;
            n2 = n3;
        }
        while (n5 <= -n4) {
            arrc[n2] = digits[-(n5 % n4)];
            n5 /= n4;
            --n2;
        }
        arrc[n2] = digits[-n5];
        n = n2;
        if (bl) {
            n = n2 - 1;
            arrc[n] = (char)45;
        }
        return new String(arrc, n, 33 - n);
    }

    public static long toUnsignedLong(int n) {
        return (long)n & 0xFFFFFFFFL;
    }

    public static String toUnsignedString(int n) {
        return Long.toString(Integer.toUnsignedLong(n));
    }

    public static String toUnsignedString(int n, int n2) {
        return Long.toUnsignedString(Integer.toUnsignedLong(n), n2);
    }

    private static String toUnsignedString0(int n, int n2) {
        int n3 = Math.max((n2 - 1 + (32 - Integer.numberOfLeadingZeros(n))) / n2, 1);
        char[] arrc = new char[n3];
        Integer.formatUnsignedInt(n, n2, arrc, 0, n3);
        return new String(arrc);
    }

    public static Integer valueOf(int n) {
        if (n >= -128 && n <= IntegerCache.high) {
            return IntegerCache.cache[n + 128];
        }
        return new Integer(n);
    }

    public static Integer valueOf(String string) throws NumberFormatException {
        return Integer.parseInt(string, 10);
    }

    public static Integer valueOf(String string, int n) throws NumberFormatException {
        return Integer.parseInt(string, n);
    }

    @Override
    public byte byteValue() {
        return (byte)this.value;
    }

    @Override
    public int compareTo(Integer n) {
        return Integer.compare(this.value, n.value);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Integer;
        boolean bl2 = false;
        if (bl) {
            if (this.value == (Integer)object) {
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
        return Integer.hashCode(this.value);
    }

    @Override
    public int intValue() {
        return this.value;
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
        return Integer.toString(this.value);
    }

    private static class IntegerCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final Integer[] cache;
        static final int high;
        static final int low = -128;

        static {
            int n = 127;
            Integer[] arrinteger = VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            int n2 = n;
            if (arrinteger != null) {
                try {
                    n2 = Math.min(Math.max(Integer.parseInt((String)arrinteger), 127), 2147483518);
                }
                catch (NumberFormatException numberFormatException) {
                    n2 = n;
                }
            }
            high = n2;
            cache = new Integer[high + 128 + 1];
            n2 = -128;
            n = 0;
            while (n < (arrinteger = cache).length) {
                arrinteger[n] = new Integer(n2);
                ++n;
                ++n2;
            }
        }

        private IntegerCache() {
        }
    }

}

