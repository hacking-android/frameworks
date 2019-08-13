/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public final class Byte
extends Number
implements Comparable<Byte> {
    public static final int BYTES = 1;
    private static final char[] DIGITS;
    public static final byte MAX_VALUE = 127;
    public static final byte MIN_VALUE = -128;
    public static final int SIZE = 8;
    public static final Class<Byte> TYPE;
    private static final char[] UPPER_CASE_DIGITS;
    private static final long serialVersionUID = -7183698231559129828L;
    private final byte value;

    static {
        TYPE = Class.getPrimitiveClass("byte");
        DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        UPPER_CASE_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }

    public Byte(byte by) {
        this.value = by;
    }

    public Byte(String string) throws NumberFormatException {
        this.value = Byte.parseByte(string, 10);
    }

    public static int compare(byte by, byte by2) {
        return by - by2;
    }

    public static Byte decode(String string) throws NumberFormatException {
        int n = Integer.decode(string);
        if (n >= -128 && n <= 127) {
            return (byte)n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value ");
        stringBuilder.append(n);
        stringBuilder.append(" out of range from input ");
        stringBuilder.append(string);
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static int hashCode(byte by) {
        return by;
    }

    public static byte parseByte(String string) throws NumberFormatException {
        return Byte.parseByte(string, 10);
    }

    public static byte parseByte(String string, int n) throws NumberFormatException {
        int n2 = Integer.parseInt(string, n);
        if (n2 >= -128 && n2 <= 127) {
            return (byte)n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value out of range. Value:\"");
        stringBuilder.append(string);
        stringBuilder.append("\" Radix:");
        stringBuilder.append(n);
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static String toHexString(byte by, boolean bl) {
        char[] arrc = bl ? UPPER_CASE_DIGITS : DIGITS;
        return new String(0, 2, new char[]{arrc[by >> 4 & 15], arrc[by & 15]});
    }

    public static String toString(byte by) {
        return Integer.toString(by, 10);
    }

    public static int toUnsignedInt(byte by) {
        return by & 255;
    }

    public static long toUnsignedLong(byte by) {
        return (long)by & 255L;
    }

    public static Byte valueOf(byte by) {
        return ByteCache.cache[by + 128];
    }

    public static Byte valueOf(String string) throws NumberFormatException {
        return Byte.valueOf(string, 10);
    }

    public static Byte valueOf(String string, int n) throws NumberFormatException {
        return Byte.parseByte(string, n);
    }

    @Override
    public byte byteValue() {
        return this.value;
    }

    @Override
    public int compareTo(Byte by) {
        return Byte.compare(this.value, by.value);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Byte;
        boolean bl2 = false;
        if (bl) {
            if (this.value == (Byte)object) {
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
        return Byte.hashCode(this.value);
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
        return this.value;
    }

    public String toString() {
        return Integer.toString(this.value);
    }

    private static class ByteCache {
        static final Byte[] cache;

        static {
            Byte[] arrbyte;
            cache = new Byte[256];
            for (int i = 0; i < (arrbyte = cache).length; ++i) {
                arrbyte[i] = new Byte((byte)(i - 128));
            }
        }

        private ByteCache() {
        }
    }

}

