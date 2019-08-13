/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ArrayUtils {
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayUtils";

    private ArrayUtils() {
        throw new AssertionError();
    }

    public static boolean contains(int[] arrn, int n) {
        boolean bl = ArrayUtils.getArrayIndex(arrn, n) != -1;
        return bl;
    }

    public static <T> boolean contains(T[] arrT, T t) {
        boolean bl = ArrayUtils.getArrayIndex(arrT, t) != -1;
        return bl;
    }

    public static int[] convertStringListToIntArray(List<String> arrn, String[] object, int[] arrn2) {
        if (arrn == null) {
            return null;
        }
        object = ArrayUtils.convertStringListToIntList(arrn, (String[])object, arrn2);
        arrn = new int[object.size()];
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = (Integer)object.get(i);
        }
        return arrn;
    }

    public static List<Integer> convertStringListToIntList(List<String> object, String[] arrstring, int[] arrn) {
        if (object == null) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            int n = ArrayUtils.getArrayIndex(arrstring, (String)object.next());
            if (n < 0 || n >= arrn.length) continue;
            arrayList.add(arrn[n]);
        }
        return arrayList;
    }

    public static int getArrayIndex(int[] arrn, int n) {
        if (arrn == null) {
            return -1;
        }
        for (int i = 0; i < arrn.length; ++i) {
            if (arrn[i] != n) continue;
            return i;
        }
        return -1;
    }

    public static <T> int getArrayIndex(T[] arrT, T t) {
        if (arrT == null) {
            return -1;
        }
        int n = 0;
        int n2 = arrT.length;
        for (int i = 0; i < n2; ++i) {
            if (Objects.equals(arrT[i], t)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static int[] toIntArray(List<Integer> object) {
        if (object == null) {
            return null;
        }
        int[] arrn = new int[object.size()];
        int n = 0;
        object = object.iterator();
        while (object.hasNext()) {
            arrn[n] = (Integer)object.next();
            ++n;
        }
        return arrn;
    }
}

