/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public final class Short
extends Number
implements Comparable<Short> {
    public static final int BYTES = 2;
    public static final short MAX_VALUE = 32767;
    public static final short MIN_VALUE = -32768;
    public static final int SIZE = 16;
    public static final Class<Short> TYPE = Class.getPrimitiveClass("short");
    private static final long serialVersionUID = 7515723908773894738L;
    private final short value;

    public Short(String string) throws NumberFormatException {
        this.value = Short.parseShort(string, 10);
    }

    public Short(short s) {
        this.value = s;
    }

    public static int compare(short s, short s2) {
        return s - s2;
    }

    public static Short decode(String string) throws NumberFormatException {
        int n = Integer.decode(string);
        if (n >= -32768 && n <= 32767) {
            return (short)n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value ");
        stringBuilder.append(n);
        stringBuilder.append(" out of range from input ");
        stringBuilder.append(string);
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static int hashCode(short s) {
        return s;
    }

    public static short parseShort(String string) throws NumberFormatException {
        return Short.parseShort(string, 10);
    }

    public static short parseShort(String string, int n) throws NumberFormatException {
        int n2 = Integer.parseInt(string, n);
        if (n2 >= -32768 && n2 <= 32767) {
            return (short)n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value out of range. Value:\"");
        stringBuilder.append(string);
        stringBuilder.append("\" Radix:");
        stringBuilder.append(n);
        throw new NumberFormatException(stringBuilder.toString());
    }

    public static short reverseBytes(short s) {
        return (short)((65280 & s) >> 8 | s << 8);
    }

    public static String toString(short s) {
        return Integer.toString(s, 10);
    }

    public static int toUnsignedInt(short s) {
        return 65535 & s;
    }

    public static long toUnsignedLong(short s) {
        return (long)s & 65535L;
    }

    public static Short valueOf(String string) throws NumberFormatException {
        return Short.valueOf(string, 10);
    }

    public static Short valueOf(String string, int n) throws NumberFormatException {
        return Short.parseShort(string, n);
    }

    public static Short valueOf(short s) {
        if (s >= -128 && s <= 127) {
            return ShortCache.cache[s + 128];
        }
        return new Short(s);
    }

    @Override
    public byte byteValue() {
        return (byte)this.value;
    }

    @Override
    public int compareTo(Short s) {
        return Short.compare(this.value, s.value);
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Short;
        boolean bl2 = false;
        if (bl) {
            if (this.value == (Short)object) {
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
        return Short.hashCode(this.value);
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

    private static class ShortCache {
        static final Short[] cache;

        static {
            Short[] arrshort;
            cache = new Short[256];
            for (int i = 0; i < (arrshort = cache).length; ++i) {
                arrshort[i] = new Short((short)(i - 128));
            }
        }

        private ShortCache() {
        }
    }

}

