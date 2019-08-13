/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 *  libcore.util.EmptyArray
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.util.ArraySet;
import dalvik.system.VMRuntime;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

public class ArrayUtils {
    private static final int CACHE_SIZE = 73;
    public static final File[] EMPTY_FILE;
    private static Object[] sCache;

    static {
        sCache = new Object[73];
        EMPTY_FILE = new File[0];
    }

    private ArrayUtils() {
    }

    public static <T> ArraySet<T> add(ArraySet<T> arraySet, T t) {
        ArraySet<Object> arraySet2 = arraySet;
        if (arraySet == null) {
            arraySet2 = new ArraySet();
        }
        arraySet2.add(t);
        return arraySet2;
    }

    public static <T> ArrayList<T> add(ArrayList<T> arrayList, T t) {
        ArrayList<Object> arrayList2 = arrayList;
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        }
        arrayList2.add(t);
        return arrayList2;
    }

    @UnsupportedAppUsage
    public static <T> T[] appendElement(Class<T> class_, T[] arrT, T t) {
        return ArrayUtils.appendElement(class_, arrT, t, false);
    }

    public static <T> T[] appendElement(Class<T> arrobject, T[] arrT, T t, boolean bl) {
        int n;
        if (arrT != null) {
            if (!bl && ArrayUtils.contains(arrT, t)) {
                return arrT;
            }
            n = arrT.length;
            arrobject = (Object[])Array.newInstance(arrobject, n + 1);
            System.arraycopy(arrT, 0, arrobject, 0, n);
        } else {
            n = 0;
            arrobject = (Object[])Array.newInstance(arrobject, 1);
        }
        arrobject[n] = t;
        return arrobject;
    }

    @UnsupportedAppUsage
    public static int[] appendInt(int[] arrn, int n) {
        return ArrayUtils.appendInt(arrn, n, false);
    }

    public static int[] appendInt(int[] arrn, int n, boolean bl) {
        if (arrn == null) {
            return new int[]{n};
        }
        int n2 = arrn.length;
        if (!bl) {
            for (int i = 0; i < n2; ++i) {
                if (arrn[i] != n) continue;
                return arrn;
            }
        }
        int[] arrn2 = new int[n2 + 1];
        System.arraycopy(arrn, 0, arrn2, 0, n2);
        arrn2[n2] = n;
        return arrn2;
    }

    public static long[] appendLong(long[] arrl, long l) {
        return ArrayUtils.appendLong(arrl, l, false);
    }

    public static long[] appendLong(long[] arrl, long l, boolean bl) {
        if (arrl == null) {
            return new long[]{l};
        }
        int n = arrl.length;
        if (!bl) {
            for (int i = 0; i < n; ++i) {
                if (arrl[i] != l) continue;
                return arrl;
            }
        }
        long[] arrl2 = new long[n + 1];
        System.arraycopy(arrl, 0, arrl2, 0, n);
        arrl2[n] = l;
        return arrl2;
    }

    public static void checkBounds(int n, int n2) {
        if (n2 >= 0 && n > n2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("length=");
        stringBuilder.append(n);
        stringBuilder.append("; index=");
        stringBuilder.append(n2);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public static <T> ArraySet<T> cloneOrNull(ArraySet<T> arraySet) {
        arraySet = arraySet != null ? new ArraySet<T>((ArraySet<T>)arraySet) : null;
        return arraySet;
    }

    public static long[] cloneOrNull(long[] object) {
        object = object != null ? (long[])object.clone() : null;
        return object;
    }

    public static <T> T[] cloneOrNull(T[] object) {
        object = object != null ? (Object[])object.clone() : null;
        return object;
    }

    public static <T> T[] concatElements(Class<T> arrobject, T[] arrT, T[] arrT2) {
        int n = arrT != null ? arrT.length : 0;
        int n2 = arrT2 != null ? arrT2.length : 0;
        if (n == 0 && n2 == 0) {
            if (arrobject == String.class) {
                return EmptyArray.STRING;
            }
            if (arrobject == Object.class) {
                return EmptyArray.OBJECT;
            }
        }
        arrobject = (Object[])Array.newInstance(arrobject, n + n2);
        if (n > 0) {
            System.arraycopy(arrT, 0, arrobject, 0, n);
        }
        if (n2 > 0) {
            System.arraycopy(arrT2, 0, arrobject, n, n2);
        }
        return arrobject;
    }

    public static <T> boolean contains(Collection<T> collection, T t) {
        boolean bl = collection != null ? collection.contains(t) : false;
        return bl;
    }

    public static boolean contains(char[] arrc, char c) {
        if (arrc == null) {
            return false;
        }
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            if (arrc[i] != c) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean contains(int[] arrn, int n) {
        if (arrn == null) {
            return false;
        }
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(long[] arrl, long l) {
        if (arrl == null) {
            return false;
        }
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            if (arrl[i] != l) continue;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public static <T> boolean contains(T[] arrT, T t) {
        boolean bl = ArrayUtils.indexOf(arrT, t) != -1;
        return bl;
    }

    public static <T> boolean containsAll(char[] arrc, char[] arrc2) {
        if (arrc2 == null) {
            return true;
        }
        int n = arrc2.length;
        for (int i = 0; i < n; ++i) {
            if (ArrayUtils.contains(arrc, arrc2[i])) continue;
            return false;
        }
        return true;
    }

    public static <T> boolean containsAll(T[] arrT, T[] arrT2) {
        if (arrT2 == null) {
            return true;
        }
        int n = arrT2.length;
        for (int i = 0; i < n; ++i) {
            if (ArrayUtils.contains(arrT, arrT2[i])) continue;
            return false;
        }
        return true;
    }

    public static <T> boolean containsAny(T[] arrT, T[] arrT2) {
        if (arrT2 == null) {
            return false;
        }
        int n = arrT2.length;
        for (int i = 0; i < n; ++i) {
            if (!ArrayUtils.contains(arrT, arrT2[i])) continue;
            return true;
        }
        return false;
    }

    public static int[] convertToIntArray(List<Integer> list) {
        int[] arrn = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            arrn[i] = list.get(i);
        }
        return arrn;
    }

    public static long[] convertToLongArray(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        long[] arrl = new long[arrn.length];
        for (int i = 0; i < arrn.length; ++i) {
            arrl[i] = arrn[i];
        }
        return arrl;
    }

    public static String deepToString(Object object) {
        if (object != null && object.getClass().isArray()) {
            if (object.getClass() == boolean[].class) {
                return Arrays.toString((boolean[])object);
            }
            if (object.getClass() == byte[].class) {
                return Arrays.toString((byte[])object);
            }
            if (object.getClass() == char[].class) {
                return Arrays.toString((char[])object);
            }
            if (object.getClass() == double[].class) {
                return Arrays.toString((double[])object);
            }
            if (object.getClass() == float[].class) {
                return Arrays.toString((float[])object);
            }
            if (object.getClass() == int[].class) {
                return Arrays.toString((int[])object);
            }
            if (object.getClass() == long[].class) {
                return Arrays.toString((long[])object);
            }
            if (object.getClass() == short[].class) {
                return Arrays.toString((short[])object);
            }
            return Arrays.deepToString((Object[])object);
        }
        return String.valueOf(object);
    }

    public static int[] defeatNullable(int[] arrn) {
        if (arrn == null) {
            arrn = EmptyArray.INT;
        }
        return arrn;
    }

    public static File[] defeatNullable(File[] arrfile) {
        if (arrfile == null) {
            arrfile = EMPTY_FILE;
        }
        return arrfile;
    }

    public static String[] defeatNullable(String[] arrstring) {
        if (arrstring == null) {
            arrstring = EmptyArray.STRING;
        }
        return arrstring;
    }

    @UnsupportedAppUsage
    public static <T> T[] emptyArray(Class<T> class_) {
        Object object;
        block5 : {
            block4 : {
                if (class_ == Object.class) {
                    return EmptyArray.OBJECT;
                }
                int n = (class_.hashCode() & Integer.MAX_VALUE) % 73;
                Object object2 = sCache[n];
                if (object2 == null) break block4;
                object = object2;
                if (object2.getClass().getComponentType() == class_) break block5;
            }
            ArrayUtils.sCache[n] = object = Array.newInstance(class_, 0);
        }
        return (Object[])object;
    }

    public static boolean equals(byte[] arrby, byte[] arrby2, int n) {
        if (n >= 0) {
            if (arrby == arrby2) {
                return true;
            }
            if (arrby != null && arrby2 != null && arrby.length >= n && arrby2.length >= n) {
                for (int i = 0; i < n; ++i) {
                    if (arrby[i] == arrby2[i]) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException();
    }

    public static <T> T[] filterNotNull(T[] arrT, IntFunction<T[]> arrT2) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = ArrayUtils.size(arrT);
        for (n2 = 0; n2 < n4; ++n2) {
            n = n3;
            if (arrT[n2] == null) {
                n = n3 + 1;
            }
            n3 = n;
        }
        if (n3 == 0) {
            return arrT;
        }
        arrT2 = arrT2.apply(n4 - n3);
        n = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            n3 = n;
            if (arrT[n2] != null) {
                arrT2[n] = arrT[n2];
                n3 = n + 1;
            }
            n = n3;
        }
        return arrT2;
    }

    public static <T> T find(T[] arrT, Predicate<T> predicate) {
        if (ArrayUtils.isEmpty(arrT)) {
            return null;
        }
        for (T t : arrT) {
            if (!predicate.test(t)) continue;
            return t;
        }
        return null;
    }

    public static <T> T firstOrNull(T[] object) {
        object = ((T[])object).length > 0 ? object[0] : null;
        return (T)object;
    }

    @UnsupportedAppUsage
    public static <T> int indexOf(T[] arrT, T t) {
        if (arrT == null) {
            return -1;
        }
        for (int i = 0; i < arrT.length; ++i) {
            if (!Objects.equals(arrT[i], t)) continue;
            return i;
        }
        return -1;
    }

    public static boolean isEmpty(Collection<?> collection) {
        boolean bl = collection == null || collection.isEmpty();
        return bl;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        boolean bl = map == null || map.isEmpty();
        return bl;
    }

    public static boolean isEmpty(byte[] arrby) {
        boolean bl = arrby == null || arrby.length == 0;
        return bl;
    }

    public static boolean isEmpty(int[] arrn) {
        boolean bl = arrn == null || arrn.length == 0;
        return bl;
    }

    public static boolean isEmpty(long[] arrl) {
        boolean bl = arrl == null || arrl.length == 0;
        return bl;
    }

    @UnsupportedAppUsage
    public static <T> boolean isEmpty(T[] arrT) {
        boolean bl = arrT == null || arrT.length == 0;
        return bl;
    }

    public static boolean isEmpty(boolean[] arrbl) {
        boolean bl = arrbl == null || arrbl.length == 0;
        return bl;
    }

    @UnsupportedAppUsage
    public static <T> T[] newUnpaddedArray(Class<T> class_, int n) {
        return (Object[])VMRuntime.getRuntime().newUnpaddedArray(class_, n);
    }

    public static boolean[] newUnpaddedBooleanArray(int n) {
        return (boolean[])VMRuntime.getRuntime().newUnpaddedArray(Boolean.TYPE, n);
    }

    public static byte[] newUnpaddedByteArray(int n) {
        return (byte[])VMRuntime.getRuntime().newUnpaddedArray(Byte.TYPE, n);
    }

    public static char[] newUnpaddedCharArray(int n) {
        return (char[])VMRuntime.getRuntime().newUnpaddedArray(Character.TYPE, n);
    }

    public static float[] newUnpaddedFloatArray(int n) {
        return (float[])VMRuntime.getRuntime().newUnpaddedArray(Float.TYPE, n);
    }

    @UnsupportedAppUsage
    public static int[] newUnpaddedIntArray(int n) {
        return (int[])VMRuntime.getRuntime().newUnpaddedArray(Integer.TYPE, n);
    }

    public static long[] newUnpaddedLongArray(int n) {
        return (long[])VMRuntime.getRuntime().newUnpaddedArray(Long.TYPE, n);
    }

    public static Object[] newUnpaddedObjectArray(int n) {
        return (Object[])VMRuntime.getRuntime().newUnpaddedArray(Object.class, n);
    }

    public static <T> boolean referenceEquals(ArrayList<T> arrayList, ArrayList<T> arrayList2) {
        boolean bl;
        boolean bl2 = true;
        if (arrayList == arrayList2) {
            return true;
        }
        int n = arrayList.size();
        if (n != arrayList2.size()) {
            return false;
        }
        boolean bl3 = false;
        for (int i = 0; i < n && !bl3; bl3 |= bl, ++i) {
            bl = arrayList.get(i) != arrayList2.get(i);
        }
        if (bl3) {
            bl2 = false;
        }
        return bl2;
    }

    public static <T> ArraySet<T> remove(ArraySet<T> arraySet, T t) {
        if (arraySet == null) {
            return null;
        }
        arraySet.remove(t);
        if (arraySet.isEmpty()) {
            return null;
        }
        return arraySet;
    }

    public static <T> ArrayList<T> remove(ArrayList<T> arrayList, T t) {
        if (arrayList == null) {
            return null;
        }
        arrayList.remove(t);
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public static <T> T[] removeElement(Class<T> arrobject, T[] arrT, T t) {
        if (arrT != null) {
            if (!ArrayUtils.contains(arrT, t)) {
                return arrT;
            }
            int n = arrT.length;
            for (int i = 0; i < n; ++i) {
                if (!Objects.equals(arrT[i], t)) continue;
                if (n == 1) {
                    return null;
                }
                arrobject = (Object[])Array.newInstance(arrobject, n - 1);
                System.arraycopy(arrT, 0, arrobject, 0, i);
                System.arraycopy(arrT, i + 1, arrobject, i, n - i - 1);
                return arrobject;
            }
        }
        return arrT;
    }

    public static int[] removeInt(int[] arrn, int n) {
        if (arrn == null) {
            return null;
        }
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            int[] arrn2 = new int[n2 - 1];
            if (i > 0) {
                System.arraycopy(arrn, 0, arrn2, 0, i);
            }
            if (i < n2 - 1) {
                System.arraycopy(arrn, i + 1, arrn2, i, n2 - i - 1);
            }
            return arrn2;
        }
        return arrn;
    }

    public static long[] removeLong(long[] arrl, long l) {
        if (arrl == null) {
            return null;
        }
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            if (arrl[i] != l) continue;
            long[] arrl2 = new long[n - 1];
            if (i > 0) {
                System.arraycopy(arrl, 0, arrl2, 0, i);
            }
            if (i < n - 1) {
                System.arraycopy(arrl, i + 1, arrl2, i, n - i - 1);
            }
            return arrl2;
        }
        return arrl;
    }

    public static String[] removeString(String[] arrstring, String arrstring2) {
        if (arrstring == null) {
            return null;
        }
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!Objects.equals(arrstring[i], arrstring2)) continue;
            arrstring2 = new String[n - 1];
            if (i > 0) {
                System.arraycopy(arrstring, 0, arrstring2, 0, i);
            }
            if (i < n - 1) {
                System.arraycopy(arrstring, i + 1, arrstring2, i, n - i - 1);
            }
            return arrstring2;
        }
        return arrstring;
    }

    public static int size(Collection<?> collection) {
        int n = collection == null ? 0 : collection.size();
        return n;
    }

    public static int size(Object[] arrobject) {
        int n = arrobject == null ? 0 : arrobject.length;
        return n;
    }

    public static boolean startsWith(byte[] arrby, byte[] arrby2) {
        if (arrby != null && arrby2 != null) {
            if (arrby.length < arrby2.length) {
                return false;
            }
            for (int i = 0; i < arrby2.length; ++i) {
                if (arrby[i] == arrby2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static long total(long[] arrl) {
        long l;
        long l2 = l = 0L;
        if (arrl != null) {
            int n = arrl.length;
            int n2 = 0;
            do {
                l2 = l;
                if (n2 >= n) break;
                l += arrl[n2];
                ++n2;
            } while (true);
        }
        return l2;
    }

    public static <T> T[] trimToSize(T[] arrT, int n) {
        if (arrT != null && n != 0) {
            if (arrT.length == n) {
                return arrT;
            }
            return Arrays.copyOf(arrT, n);
        }
        return null;
    }

    public static <T> int unstableRemoveIf(ArrayList<T> arrayList, Predicate<T> predicate) {
        int n;
        if (arrayList == null) {
            return 0;
        }
        int n2 = arrayList.size();
        int n3 = 0;
        int n4 = n2 - 1;
        do {
            n = n3;
            if (n3 > n4) break;
            do {
                n = n4;
                if (n3 >= n2) break;
                n = n4;
                if (predicate.test(arrayList.get(n3))) break;
                ++n3;
            } while (true);
            while (n > n3 && predicate.test(arrayList.get(n))) {
                --n;
            }
            if (n3 >= n) {
                n = n3;
                break;
            }
            Collections.swap(arrayList, n3, n);
            ++n3;
            n4 = n - 1;
        } while (true);
        for (n3 = n2 - 1; n3 >= n; --n3) {
            arrayList.remove(n3);
        }
        return n2 - n;
    }
}

