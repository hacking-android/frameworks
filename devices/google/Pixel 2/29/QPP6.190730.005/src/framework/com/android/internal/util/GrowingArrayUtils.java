/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.ArrayUtils;

public final class GrowingArrayUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private GrowingArrayUtils() {
    }

    public static float[] append(float[] arrf, int n, float f) {
        float[] arrf2 = arrf;
        if (n + 1 > arrf.length) {
            arrf2 = ArrayUtils.newUnpaddedFloatArray(GrowingArrayUtils.growSize(n));
            System.arraycopy(arrf, 0, arrf2, 0, n);
        }
        arrf2[n] = f;
        return arrf2;
    }

    @UnsupportedAppUsage
    public static int[] append(int[] arrn, int n, int n2) {
        int[] arrn2 = arrn;
        if (n + 1 > arrn.length) {
            arrn2 = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(n));
            System.arraycopy(arrn, 0, arrn2, 0, n);
        }
        arrn2[n] = n2;
        return arrn2;
    }

    public static long[] append(long[] arrl, int n, long l) {
        long[] arrl2 = arrl;
        if (n + 1 > arrl.length) {
            arrl2 = ArrayUtils.newUnpaddedLongArray(GrowingArrayUtils.growSize(n));
            System.arraycopy(arrl, 0, arrl2, 0, n);
        }
        arrl2[n] = l;
        return arrl2;
    }

    @UnsupportedAppUsage
    public static <T> T[] append(T[] arrT, int n, T t) {
        Object[] arrobject = arrT;
        if (n + 1 > arrT.length) {
            arrobject = ArrayUtils.newUnpaddedArray(arrT.getClass().getComponentType(), GrowingArrayUtils.growSize(n));
            System.arraycopy(arrT, 0, arrobject, 0, n);
        }
        arrobject[n] = t;
        return arrobject;
    }

    public static boolean[] append(boolean[] arrbl, int n, boolean bl) {
        boolean[] arrbl2 = arrbl;
        if (n + 1 > arrbl.length) {
            arrbl2 = ArrayUtils.newUnpaddedBooleanArray(GrowingArrayUtils.growSize(n));
            System.arraycopy(arrbl, 0, arrbl2, 0, n);
        }
        arrbl2[n] = bl;
        return arrbl2;
    }

    public static int growSize(int n) {
        n = n <= 4 ? 8 : (n *= 2);
        return n;
    }

    public static int[] insert(int[] arrn, int n, int n2, int n3) {
        if (n + 1 <= arrn.length) {
            System.arraycopy(arrn, n2, arrn, n2 + 1, n - n2);
            arrn[n2] = n3;
            return arrn;
        }
        int[] arrn2 = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(n));
        System.arraycopy(arrn, 0, arrn2, 0, n2);
        arrn2[n2] = n3;
        System.arraycopy(arrn, n2, arrn2, n2 + 1, arrn.length - n2);
        return arrn2;
    }

    public static long[] insert(long[] arrl, int n, int n2, long l) {
        if (n + 1 <= arrl.length) {
            System.arraycopy(arrl, n2, arrl, n2 + 1, n - n2);
            arrl[n2] = l;
            return arrl;
        }
        long[] arrl2 = ArrayUtils.newUnpaddedLongArray(GrowingArrayUtils.growSize(n));
        System.arraycopy(arrl, 0, arrl2, 0, n2);
        arrl2[n2] = l;
        System.arraycopy(arrl, n2, arrl2, n2 + 1, arrl.length - n2);
        return arrl2;
    }

    public static <T> T[] insert(T[] arrT, int n, int n2, T t) {
        if (n + 1 <= arrT.length) {
            System.arraycopy(arrT, n2, arrT, n2 + 1, n - n2);
            arrT[n2] = t;
            return arrT;
        }
        ?[] arrobj = ArrayUtils.newUnpaddedArray(arrT.getClass().getComponentType(), GrowingArrayUtils.growSize(n));
        System.arraycopy(arrT, 0, arrobj, 0, n2);
        arrobj[n2] = t;
        System.arraycopy(arrT, n2, arrobj, n2 + 1, arrT.length - n2);
        return arrobj;
    }

    public static boolean[] insert(boolean[] arrbl, int n, int n2, boolean bl) {
        if (n + 1 <= arrbl.length) {
            System.arraycopy(arrbl, n2, arrbl, n2 + 1, n - n2);
            arrbl[n2] = bl;
            return arrbl;
        }
        boolean[] arrbl2 = ArrayUtils.newUnpaddedBooleanArray(GrowingArrayUtils.growSize(n));
        System.arraycopy(arrbl, 0, arrbl2, 0, n2);
        arrbl2[n2] = bl;
        System.arraycopy(arrbl, n2, arrbl2, n2 + 1, arrbl.length - n2);
        return arrbl2;
    }
}

