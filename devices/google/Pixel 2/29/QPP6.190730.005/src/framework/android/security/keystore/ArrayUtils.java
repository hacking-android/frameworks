/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.security.keystore;

import libcore.util.EmptyArray;

public abstract class ArrayUtils {
    private ArrayUtils() {
    }

    public static byte[] cloneIfNotEmpty(byte[] arrby) {
        block0 : {
            if (arrby == null || arrby.length <= 0) break block0;
            arrby = (byte[])arrby.clone();
        }
        return arrby;
    }

    public static String[] cloneIfNotEmpty(String[] arrstring) {
        block0 : {
            if (arrstring == null || arrstring.length <= 0) break block0;
            arrstring = (String[])arrstring.clone();
        }
        return arrstring;
    }

    public static byte[] concat(byte[] arrby, int n, int n2, byte[] arrby2, int n3, int n4) {
        if (n2 == 0) {
            return ArrayUtils.subarray(arrby2, n3, n4);
        }
        if (n4 == 0) {
            return ArrayUtils.subarray(arrby, n, n2);
        }
        byte[] arrby3 = new byte[n2 + n4];
        System.arraycopy(arrby, n, arrby3, 0, n2);
        System.arraycopy(arrby2, n3, arrby3, n2, n4);
        return arrby3;
    }

    public static byte[] concat(byte[] arrby, byte[] arrby2) {
        int n = 0;
        int n2 = arrby != null ? arrby.length : 0;
        if (arrby2 != null) {
            n = arrby2.length;
        }
        return ArrayUtils.concat(arrby, 0, n2, arrby2, 0, n);
    }

    public static int[] concat(int[] arrn, int[] arrn2) {
        if (arrn != null && arrn.length != 0) {
            if (arrn2 != null && arrn2.length != 0) {
                int[] arrn3 = new int[arrn.length + arrn2.length];
                System.arraycopy(arrn, 0, arrn3, 0, arrn.length);
                System.arraycopy(arrn2, 0, arrn3, arrn.length, arrn2.length);
                return arrn3;
            }
            return arrn;
        }
        return arrn2;
    }

    public static String[] nullToEmpty(String[] arrstring) {
        if (arrstring == null) {
            arrstring = EmptyArray.STRING;
        }
        return arrstring;
    }

    public static byte[] subarray(byte[] arrby, int n, int n2) {
        if (n2 == 0) {
            return EmptyArray.BYTE;
        }
        if (n == 0 && n2 == arrby.length) {
            return arrby;
        }
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return arrby2;
    }
}

