/*
 * Decompiled with CFR 0.145.
 */
package java.io;

class Bits {
    Bits() {
    }

    static boolean getBoolean(byte[] arrby, int n) {
        boolean bl = arrby[n] != 0;
        return bl;
    }

    static char getChar(byte[] arrby, int n) {
        return (char)((arrby[n + 1] & 255) + (arrby[n] << 8));
    }

    static double getDouble(byte[] arrby, int n) {
        return Double.longBitsToDouble(Bits.getLong(arrby, n));
    }

    static float getFloat(byte[] arrby, int n) {
        return Float.intBitsToFloat(Bits.getInt(arrby, n));
    }

    static int getInt(byte[] arrby, int n) {
        return (arrby[n + 3] & 255) + ((arrby[n + 2] & 255) << 8) + ((arrby[n + 1] & 255) << 16) + (arrby[n] << 24);
    }

    static long getLong(byte[] arrby, int n) {
        return ((long)arrby[n + 7] & 255L) + (((long)arrby[n + 6] & 255L) << 8) + (((long)arrby[n + 5] & 255L) << 16) + (((long)arrby[n + 4] & 255L) << 24) + (((long)arrby[n + 3] & 255L) << 32) + (((long)arrby[n + 2] & 255L) << 40) + ((255L & (long)arrby[n + 1]) << 48) + ((long)arrby[n] << 56);
    }

    static short getShort(byte[] arrby, int n) {
        return (short)((arrby[n + 1] & 255) + (arrby[n] << 8));
    }

    static void putBoolean(byte[] arrby, int n, boolean bl) {
        arrby[n] = (byte)(bl ? 1 : 0);
    }

    static void putChar(byte[] arrby, int n, char c) {
        arrby[n + 1] = (byte)c;
        arrby[n] = (byte)(c >>> 8);
    }

    static void putDouble(byte[] arrby, int n, double d) {
        Bits.putLong(arrby, n, Double.doubleToLongBits(d));
    }

    static void putFloat(byte[] arrby, int n, float f) {
        Bits.putInt(arrby, n, Float.floatToIntBits(f));
    }

    static void putInt(byte[] arrby, int n, int n2) {
        arrby[n + 3] = (byte)n2;
        arrby[n + 2] = (byte)(n2 >>> 8);
        arrby[n + 1] = (byte)(n2 >>> 16);
        arrby[n] = (byte)(n2 >>> 24);
    }

    static void putLong(byte[] arrby, int n, long l) {
        arrby[n + 7] = (byte)l;
        arrby[n + 6] = (byte)(l >>> 8);
        arrby[n + 5] = (byte)(l >>> 16);
        arrby[n + 4] = (byte)(l >>> 24);
        arrby[n + 3] = (byte)(l >>> 32);
        arrby[n + 2] = (byte)(l >>> 40);
        arrby[n + 1] = (byte)(l >>> 48);
        arrby[n] = (byte)(l >>> 56);
    }

    static void putShort(byte[] arrby, int n, short s) {
        arrby[n + 1] = (byte)s;
        arrby[n] = (byte)(s >>> 8);
    }
}

