/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayPrefixHelpers;
import java.util.ArraysParallelSortHelpers;
import java.util.ComparableTimSort;
import java.util.Comparator;
import java.util.DualPivotQuicksort;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TimSort;
import java.util._$$Lambda$Arrays$H0YqaggIxZUqId4_BJ1BLcUa93k;
import java.util._$$Lambda$Arrays$KFf05FUz26CqVc_cf2bKY9C927o;
import java.util._$$Lambda$Arrays$aBSX_SvA5f2Q1t8_MODHDGhokzk;
import java.util._$$Lambda$Arrays$x0HcRDlColwoPupFWmOW7TREPtM;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Arrays {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INSERTIONSORT_THRESHOLD = 7;
    public static final int MIN_ARRAY_SORT_GRAN = 8192;

    private Arrays() {
    }

    @SafeVarargs
    public static <T> List<T> asList(T ... arrT) {
        return new ArrayList<T>(arrT);
    }

    public static int binarySearch(byte[] arrby, byte by) {
        return Arrays.binarySearch0(arrby, 0, arrby.length, by);
    }

    public static int binarySearch(byte[] arrby, int n, int n2, byte by) {
        Arrays.rangeCheck(arrby.length, n, n2);
        return Arrays.binarySearch0(arrby, n, n2, by);
    }

    public static int binarySearch(char[] arrc, char c) {
        return Arrays.binarySearch0(arrc, 0, arrc.length, c);
    }

    public static int binarySearch(char[] arrc, int n, int n2, char c) {
        Arrays.rangeCheck(arrc.length, n, n2);
        return Arrays.binarySearch0(arrc, n, n2, c);
    }

    public static int binarySearch(double[] arrd, double d) {
        return Arrays.binarySearch0(arrd, 0, arrd.length, d);
    }

    public static int binarySearch(double[] arrd, int n, int n2, double d) {
        Arrays.rangeCheck(arrd.length, n, n2);
        return Arrays.binarySearch0(arrd, n, n2, d);
    }

    public static int binarySearch(float[] arrf, float f) {
        return Arrays.binarySearch0(arrf, 0, arrf.length, f);
    }

    public static int binarySearch(float[] arrf, int n, int n2, float f) {
        Arrays.rangeCheck(arrf.length, n, n2);
        return Arrays.binarySearch0(arrf, n, n2, f);
    }

    public static int binarySearch(int[] arrn, int n) {
        return Arrays.binarySearch0(arrn, 0, arrn.length, n);
    }

    public static int binarySearch(int[] arrn, int n, int n2, int n3) {
        Arrays.rangeCheck(arrn.length, n, n2);
        return Arrays.binarySearch0(arrn, n, n2, n3);
    }

    public static int binarySearch(long[] arrl, int n, int n2, long l) {
        Arrays.rangeCheck(arrl.length, n, n2);
        return Arrays.binarySearch0(arrl, n, n2, l);
    }

    public static int binarySearch(long[] arrl, long l) {
        return Arrays.binarySearch0(arrl, 0, arrl.length, l);
    }

    public static int binarySearch(Object[] arrobject, int n, int n2, Object object) {
        Arrays.rangeCheck(arrobject.length, n, n2);
        return Arrays.binarySearch0(arrobject, n, n2, object);
    }

    public static <T> int binarySearch(T[] arrT, int n, int n2, T t, Comparator<? super T> comparator) {
        Arrays.rangeCheck(arrT.length, n, n2);
        return Arrays.binarySearch0(arrT, n, n2, t, comparator);
    }

    public static int binarySearch(Object[] arrobject, Object object) {
        return Arrays.binarySearch0(arrobject, 0, arrobject.length, object);
    }

    public static <T> int binarySearch(T[] arrT, T t, Comparator<? super T> comparator) {
        return Arrays.binarySearch0(arrT, 0, arrT.length, t, comparator);
    }

    public static int binarySearch(short[] arrs, int n, int n2, short s) {
        Arrays.rangeCheck(arrs.length, n, n2);
        return Arrays.binarySearch0(arrs, n, n2, s);
    }

    public static int binarySearch(short[] arrs, short s) {
        return Arrays.binarySearch0(arrs, 0, arrs.length, s);
    }

    private static int binarySearch0(byte[] arrby, int n, int n2, byte by) {
        int n3 = n;
        n = n2 - 1;
        n2 = n3;
        while (n2 <= n) {
            int n4 = n2 + n >>> 1;
            n3 = arrby[n4];
            if (n3 < by) {
                n2 = n4 + 1;
                continue;
            }
            if (n3 > by) {
                n = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n2 + 1);
    }

    private static int binarySearch0(char[] arrc, int n, int n2, char c) {
        int n3 = n;
        n = n2 - 1;
        n2 = n3;
        while (n2 <= n) {
            int n4 = n2 + n >>> 1;
            n3 = arrc[n4];
            if (n3 < c) {
                n2 = n4 + 1;
                continue;
            }
            if (n3 > c) {
                n = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n2 + 1);
    }

    private static int binarySearch0(double[] arrd, int n, int n2, double d) {
        --n2;
        while (n <= n2) {
            long l;
            int n3 = n + n2 >>> 1;
            double d2 = arrd[n3];
            if (d2 < d) {
                n = n3 + 1;
                continue;
            }
            if (d2 > d) {
                n2 = n3 - 1;
                continue;
            }
            long l2 = Double.doubleToLongBits(d2);
            if (l2 == (l = Double.doubleToLongBits(d))) {
                return n3;
            }
            if (l2 < l) {
                n = n3 + 1;
                continue;
            }
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    private static int binarySearch0(float[] arrf, int n, int n2, float f) {
        --n2;
        while (n <= n2) {
            int n3;
            int n4 = n + n2 >>> 1;
            float f2 = arrf[n4];
            if (f2 < f) {
                n = n4 + 1;
                continue;
            }
            if (f2 > f) {
                n2 = n4 - 1;
                continue;
            }
            int n5 = Float.floatToIntBits(f2);
            if (n5 == (n3 = Float.floatToIntBits(f))) {
                return n4;
            }
            if (n5 < n3) {
                n = n4 + 1;
                continue;
            }
            n2 = n4 - 1;
        }
        return -(n + 1);
    }

    private static int binarySearch0(int[] arrn, int n, int n2, int n3) {
        int n4 = n;
        n = n2 - 1;
        n2 = n4;
        while (n2 <= n) {
            int n5 = n2 + n >>> 1;
            n4 = arrn[n5];
            if (n4 < n3) {
                n2 = n5 + 1;
                continue;
            }
            if (n4 > n3) {
                n = n5 - 1;
                continue;
            }
            return n5;
        }
        return -(n2 + 1);
    }

    private static int binarySearch0(long[] arrl, int n, int n2, long l) {
        int n3 = n;
        n = n2 - 1;
        n2 = n3;
        while (n2 <= n) {
            n3 = n2 + n >>> 1;
            long l2 = arrl[n3];
            if (l2 < l) {
                n2 = n3 + 1;
                continue;
            }
            if (l2 > l) {
                n = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n2 + 1);
    }

    private static int binarySearch0(Object[] arrobject, int n, int n2, Object object) {
        int n3 = n;
        n = n2 - 1;
        n2 = n3;
        while (n2 <= n) {
            int n4 = n2 + n >>> 1;
            n3 = ((Comparable)arrobject[n4]).compareTo(object);
            if (n3 < 0) {
                n2 = n4 + 1;
                continue;
            }
            if (n3 > 0) {
                n = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n2 + 1);
    }

    private static <T> int binarySearch0(T[] arrT, int n, int n2, T t, Comparator<? super T> comparator) {
        if (comparator == null) {
            return Arrays.binarySearch0(arrT, n, n2, t);
        }
        int n3 = n;
        n = n2 - 1;
        n2 = n3;
        while (n2 <= n) {
            int n4 = n2 + n >>> 1;
            n3 = comparator.compare(arrT[n4], t);
            if (n3 < 0) {
                n2 = n4 + 1;
                continue;
            }
            if (n3 > 0) {
                n = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n2 + 1);
    }

    private static int binarySearch0(short[] arrs, int n, int n2, short s) {
        int n3 = n;
        n = n2 - 1;
        n2 = n3;
        while (n2 <= n) {
            n3 = n2 + n >>> 1;
            short s2 = arrs[n3];
            if (s2 < s) {
                n2 = n3 + 1;
                continue;
            }
            if (s2 > s) {
                n = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n2 + 1);
    }

    public static byte[] copyOf(byte[] arrby, int n) {
        byte[] arrby2 = new byte[n];
        System.arraycopy(arrby, 0, arrby2, 0, Math.min(arrby.length, n));
        return arrby2;
    }

    public static char[] copyOf(char[] arrc, int n) {
        char[] arrc2 = new char[n];
        System.arraycopy((Object)arrc, 0, (Object)arrc2, 0, Math.min(arrc.length, n));
        return arrc2;
    }

    public static double[] copyOf(double[] arrd, int n) {
        double[] arrd2 = new double[n];
        System.arraycopy((Object)arrd, 0, (Object)arrd2, 0, Math.min(arrd.length, n));
        return arrd2;
    }

    public static float[] copyOf(float[] arrf, int n) {
        float[] arrf2 = new float[n];
        System.arraycopy((Object)arrf, 0, (Object)arrf2, 0, Math.min(arrf.length, n));
        return arrf2;
    }

    public static int[] copyOf(int[] arrn, int n) {
        int[] arrn2 = new int[n];
        System.arraycopy((Object)arrn, 0, (Object)arrn2, 0, Math.min(arrn.length, n));
        return arrn2;
    }

    public static long[] copyOf(long[] arrl, int n) {
        long[] arrl2 = new long[n];
        System.arraycopy((Object)arrl, 0, (Object)arrl2, 0, Math.min(arrl.length, n));
        return arrl2;
    }

    public static <T> T[] copyOf(T[] arrT, int n) {
        return Arrays.copyOf(arrT, n, arrT.getClass());
    }

    public static <T, U> T[] copyOf(U[] arrU, int n, Class<? extends T[]> arrobject) {
        arrobject = arrobject == Object[].class ? new Object[n] : (Object[])Array.newInstance(arrobject.getComponentType(), n);
        System.arraycopy(arrU, 0, arrobject, 0, Math.min(arrU.length, n));
        return arrobject;
    }

    public static short[] copyOf(short[] arrs, int n) {
        short[] arrs2 = new short[n];
        System.arraycopy((Object)arrs, 0, (Object)arrs2, 0, Math.min(arrs.length, n));
        return arrs2;
    }

    public static boolean[] copyOf(boolean[] arrbl, int n) {
        boolean[] arrbl2 = new boolean[n];
        System.arraycopy((Object)arrbl, 0, (Object)arrbl2, 0, Math.min(arrbl.length, n));
        return arrbl2;
    }

    public static byte[] copyOfRange(byte[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            byte[] arrby = new byte[n3];
            System.arraycopy((byte[])object, n, arrby, 0, Math.min(((Object)object).length - n, n3));
            return arrby;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static char[] copyOfRange(char[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            char[] arrc = new char[n3];
            System.arraycopy(object, n, (Object)arrc, 0, Math.min(((Object)object).length - n, n3));
            return arrc;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static double[] copyOfRange(double[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            double[] arrd = new double[n3];
            System.arraycopy(object, n, (Object)arrd, 0, Math.min(((Object)object).length - n, n3));
            return arrd;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static float[] copyOfRange(float[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            float[] arrf = new float[n3];
            System.arraycopy(object, n, (Object)arrf, 0, Math.min(((Object)object).length - n, n3));
            return arrf;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static int[] copyOfRange(int[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            int[] arrn = new int[n3];
            System.arraycopy(object, n, (Object)arrn, 0, Math.min(((Object)object).length - n, n3));
            return arrn;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static long[] copyOfRange(long[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            long[] arrl = new long[n3];
            System.arraycopy(object, n, (Object)arrl, 0, Math.min(((Object)object).length - n, n3));
            return arrl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static <T> T[] copyOfRange(T[] arrT, int n, int n2) {
        return Arrays.copyOfRange(arrT, n, n2, arrT.getClass());
    }

    public static <T, U> T[] copyOfRange(U[] object, int n, int n2, Class<? extends T[]> arrobject) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            arrobject = arrobject == Object[].class ? new Object[n3] : (Object[])Array.newInstance(arrobject.getComponentType(), n3);
            System.arraycopy(object, n, arrobject, 0, Math.min(((Object)object).length - n, n3));
            return arrobject;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static short[] copyOfRange(short[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            short[] arrs = new short[n3];
            System.arraycopy(object, n, (Object)arrs, 0, Math.min(((Object)object).length - n, n3));
            return arrs;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean[] copyOfRange(boolean[] object, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            boolean[] arrbl = new boolean[n3];
            System.arraycopy(object, n, (Object)arrbl, 0, Math.min(((Object)object).length - n, n3));
            return arrbl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" > ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean deepEquals(Object[] arrobject, Object[] arrobject2) {
        if (arrobject == arrobject2) {
            return true;
        }
        if (arrobject != null && arrobject2 != null) {
            int n = arrobject.length;
            if (arrobject2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                Object object = arrobject[i];
                Object object2 = arrobject2[i];
                if (object == object2) continue;
                if (object == null) {
                    return false;
                }
                if (Arrays.deepEquals0(object, object2)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    static boolean deepEquals0(Object object, Object object2) {
        boolean bl = object instanceof Object[] && object2 instanceof Object[] ? Arrays.deepEquals((Object[])object, (Object[])object2) : (object instanceof byte[] && object2 instanceof byte[] ? Arrays.equals((byte[])object, (byte[])object2) : (object instanceof short[] && object2 instanceof short[] ? Arrays.equals((short[])object, (short[])object2) : (object instanceof int[] && object2 instanceof int[] ? Arrays.equals((int[])object, (int[])object2) : (object instanceof long[] && object2 instanceof long[] ? Arrays.equals((long[])object, (long[])object2) : (object instanceof char[] && object2 instanceof char[] ? Arrays.equals((char[])object, (char[])object2) : (object instanceof float[] && object2 instanceof float[] ? Arrays.equals((float[])object, (float[])object2) : (object instanceof double[] && object2 instanceof double[] ? Arrays.equals((double[])object, (double[])object2) : (object instanceof boolean[] && object2 instanceof boolean[] ? Arrays.equals((boolean[])object, (boolean[])object2) : object.equals(object2)))))))));
        return bl;
    }

    public static int deepHashCode(Object[] arrobject) {
        if (arrobject == null) {
            return 0;
        }
        int n = 1;
        for (Object object : arrobject) {
            int n2 = 0;
            if (object != null) {
                Class<?> class_ = object.getClass().getComponentType();
                n2 = class_ == null ? object.hashCode() : (object instanceof Object[] ? Arrays.deepHashCode((Object[])object) : (class_ == Byte.TYPE ? Arrays.hashCode((byte[])object) : (class_ == Short.TYPE ? Arrays.hashCode((short[])object) : (class_ == Integer.TYPE ? Arrays.hashCode((int[])object) : (class_ == Long.TYPE ? Arrays.hashCode((long[])object) : (class_ == Character.TYPE ? Arrays.hashCode((char[])object) : (class_ == Float.TYPE ? Arrays.hashCode((float[])object) : (class_ == Double.TYPE ? Arrays.hashCode((double[])object) : (class_ == Boolean.TYPE ? Arrays.hashCode((boolean[])object) : object.hashCode())))))))));
            }
            n = n * 31 + n2;
        }
        return n;
    }

    public static String deepToString(Object[] arrobject) {
        int n;
        if (arrobject == null) {
            return "null";
        }
        int n2 = n = arrobject.length * 20;
        if (arrobject.length != 0) {
            n2 = n;
            if (n <= 0) {
                n2 = Integer.MAX_VALUE;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(n2);
        Arrays.deepToString(arrobject, stringBuilder, new HashSet<Object[]>());
        return stringBuilder.toString();
    }

    private static void deepToString(Object[] arrobject, StringBuilder stringBuilder, Set<Object[]> set) {
        if (arrobject == null) {
            stringBuilder.append("null");
            return;
        }
        int n = arrobject.length - 1;
        if (n == -1) {
            stringBuilder.append("[]");
            return;
        }
        set.add(arrobject);
        stringBuilder.append('[');
        int n2 = 0;
        do {
            Object object;
            if ((object = arrobject[n2]) == null) {
                stringBuilder.append("null");
            } else {
                Class<?> class_ = object.getClass();
                if (class_.isArray()) {
                    if (class_ == byte[].class) {
                        stringBuilder.append(Arrays.toString((byte[])object));
                    } else if (class_ == short[].class) {
                        stringBuilder.append(Arrays.toString((short[])object));
                    } else if (class_ == int[].class) {
                        stringBuilder.append(Arrays.toString((int[])object));
                    } else if (class_ == long[].class) {
                        stringBuilder.append(Arrays.toString((long[])object));
                    } else if (class_ == char[].class) {
                        stringBuilder.append(Arrays.toString((char[])object));
                    } else if (class_ == float[].class) {
                        stringBuilder.append(Arrays.toString((float[])object));
                    } else if (class_ == double[].class) {
                        stringBuilder.append(Arrays.toString((double[])object));
                    } else if (class_ == boolean[].class) {
                        stringBuilder.append(Arrays.toString((boolean[])object));
                    } else if (set.contains(object)) {
                        stringBuilder.append("[...]");
                    } else {
                        Arrays.deepToString((Object[])object, stringBuilder, set);
                    }
                } else {
                    stringBuilder.append(object.toString());
                }
            }
            if (n2 == n) {
                stringBuilder.append(']');
                set.remove(arrobject);
                return;
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static boolean equals(byte[] arrby, byte[] arrby2) {
        if (arrby == arrby2) {
            return true;
        }
        if (arrby != null && arrby2 != null) {
            int n = arrby.length;
            if (arrby2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (arrby[i] == arrby2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(char[] arrc, char[] arrc2) {
        if (arrc == arrc2) {
            return true;
        }
        if (arrc != null && arrc2 != null) {
            int n = arrc.length;
            if (arrc2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (arrc[i] == arrc2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(double[] arrd, double[] arrd2) {
        if (arrd == arrd2) {
            return true;
        }
        if (arrd != null && arrd2 != null) {
            int n = arrd.length;
            if (arrd2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (Double.doubleToLongBits(arrd[i]) == Double.doubleToLongBits(arrd2[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(float[] arrf, float[] arrf2) {
        if (arrf == arrf2) {
            return true;
        }
        if (arrf != null && arrf2 != null) {
            int n = arrf.length;
            if (arrf2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (Float.floatToIntBits(arrf[i]) == Float.floatToIntBits(arrf2[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(int[] arrn, int[] arrn2) {
        if (arrn == arrn2) {
            return true;
        }
        if (arrn != null && arrn2 != null) {
            int n = arrn.length;
            if (arrn2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (arrn[i] == arrn2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(long[] arrl, long[] arrl2) {
        if (arrl == arrl2) {
            return true;
        }
        if (arrl != null && arrl2 != null) {
            int n = arrl.length;
            if (arrl2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (arrl[i] == arrl2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(Object[] arrobject, Object[] arrobject2) {
        if (arrobject == arrobject2) {
            return true;
        }
        if (arrobject != null && arrobject2 != null) {
            int n = arrobject.length;
            if (arrobject2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                Object object = arrobject[i];
                Object object2 = arrobject2[i];
                if (object != null ? object.equals(object2) : object2 == null) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(short[] arrs, short[] arrs2) {
        if (arrs == arrs2) {
            return true;
        }
        if (arrs != null && arrs2 != null) {
            int n = arrs.length;
            if (arrs2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (arrs[i] == arrs2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equals(boolean[] arrbl, boolean[] arrbl2) {
        if (arrbl == arrbl2) {
            return true;
        }
        if (arrbl != null && arrbl2 != null) {
            int n = arrbl.length;
            if (arrbl2.length != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (arrbl[i] == arrbl2[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static void fill(byte[] arrby, byte by) {
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            arrby[i] = by;
        }
    }

    public static void fill(byte[] arrby, int n, int n2, byte by) {
        Arrays.rangeCheck(arrby.length, n, n2);
        while (n < n2) {
            arrby[n] = by;
            ++n;
        }
    }

    public static void fill(char[] arrc, char c) {
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            arrc[i] = c;
        }
    }

    public static void fill(char[] arrc, int n, int n2, char c) {
        Arrays.rangeCheck(arrc.length, n, n2);
        while (n < n2) {
            arrc[n] = c;
            ++n;
        }
    }

    public static void fill(double[] arrd, double d) {
        int n = arrd.length;
        for (int i = 0; i < n; ++i) {
            arrd[i] = d;
        }
    }

    public static void fill(double[] arrd, int n, int n2, double d) {
        Arrays.rangeCheck(arrd.length, n, n2);
        while (n < n2) {
            arrd[n] = d;
            ++n;
        }
    }

    public static void fill(float[] arrf, float f) {
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            arrf[i] = f;
        }
    }

    public static void fill(float[] arrf, int n, int n2, float f) {
        Arrays.rangeCheck(arrf.length, n, n2);
        while (n < n2) {
            arrf[n] = f;
            ++n;
        }
    }

    public static void fill(int[] arrn, int n) {
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            arrn[i] = n;
        }
    }

    public static void fill(int[] arrn, int n, int n2, int n3) {
        Arrays.rangeCheck(arrn.length, n, n2);
        while (n < n2) {
            arrn[n] = n3;
            ++n;
        }
    }

    public static void fill(long[] arrl, int n, int n2, long l) {
        Arrays.rangeCheck(arrl.length, n, n2);
        while (n < n2) {
            arrl[n] = l;
            ++n;
        }
    }

    public static void fill(long[] arrl, long l) {
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            arrl[i] = l;
        }
    }

    public static void fill(Object[] arrobject, int n, int n2, Object object) {
        Arrays.rangeCheck(arrobject.length, n, n2);
        while (n < n2) {
            arrobject[n] = object;
            ++n;
        }
    }

    public static void fill(Object[] arrobject, Object object) {
        int n = arrobject.length;
        for (int i = 0; i < n; ++i) {
            arrobject[i] = object;
        }
    }

    public static void fill(short[] arrs, int n, int n2, short s) {
        Arrays.rangeCheck(arrs.length, n, n2);
        while (n < n2) {
            arrs[n] = s;
            ++n;
        }
    }

    public static void fill(short[] arrs, short s) {
        int n = arrs.length;
        for (int i = 0; i < n; ++i) {
            arrs[i] = s;
        }
    }

    public static void fill(boolean[] arrbl, int n, int n2, boolean bl) {
        Arrays.rangeCheck(arrbl.length, n, n2);
        while (n < n2) {
            arrbl[n] = bl;
            ++n;
        }
    }

    public static void fill(boolean[] arrbl, boolean bl) {
        int n = arrbl.length;
        for (int i = 0; i < n; ++i) {
            arrbl[i] = bl;
        }
    }

    public static int hashCode(byte[] arrby) {
        if (arrby == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrby.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + arrby[i];
        }
        return n;
    }

    public static int hashCode(char[] arrc) {
        if (arrc == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrc.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + arrc[i];
        }
        return n;
    }

    public static int hashCode(double[] arrd) {
        if (arrd == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrd.length;
        for (int i = 0; i < n2; ++i) {
            long l = Double.doubleToLongBits(arrd[i]);
            n = n * 31 + (int)(l >>> 32 ^ l);
        }
        return n;
    }

    public static int hashCode(float[] arrf) {
        if (arrf == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrf.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + Float.floatToIntBits(arrf[i]);
        }
        return n;
    }

    public static int hashCode(int[] arrn) {
        if (arrn == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + arrn[i];
        }
        return n;
    }

    public static int hashCode(long[] arrl) {
        if (arrl == null) {
            return 0;
        }
        int n = 1;
        for (long l : arrl) {
            n = n * 31 + (int)(l >>> 32 ^ l);
        }
        return n;
    }

    public static int hashCode(Object[] arrobject) {
        if (arrobject == null) {
            return 0;
        }
        int n = arrobject.length;
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            int n3 = object == null ? 0 : object.hashCode();
            n2 = n2 * 31 + n3;
        }
        return n2;
    }

    public static int hashCode(short[] arrs) {
        if (arrs == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrs.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + arrs[i];
        }
        return n;
    }

    public static int hashCode(boolean[] arrbl) {
        if (arrbl == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrbl.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = arrbl[i] ? 1231 : 1237;
            n = n * 31 + n3;
        }
        return n;
    }

    static /* synthetic */ void lambda$parallelSetAll$0(Object[] arrobject, IntFunction intFunction, int n) {
        arrobject[n] = intFunction.apply(n);
    }

    static /* synthetic */ void lambda$parallelSetAll$1(int[] arrn, IntUnaryOperator intUnaryOperator, int n) {
        arrn[n] = intUnaryOperator.applyAsInt(n);
    }

    static /* synthetic */ void lambda$parallelSetAll$2(long[] arrl, IntToLongFunction intToLongFunction, int n) {
        arrl[n] = intToLongFunction.applyAsLong(n);
    }

    static /* synthetic */ void lambda$parallelSetAll$3(double[] arrd, IntToDoubleFunction intToDoubleFunction, int n) {
        arrd[n] = intToDoubleFunction.applyAsDouble(n);
    }

    private static void mergeSort(Object[] arrobject, Object[] arrobject2, int n, int n2, int n3) {
        int n4 = n2 - n;
        if (n4 < 7) {
            for (n3 = n; n3 < n2; ++n3) {
                for (int i = n3; i > n && ((Comparable)arrobject2[i - 1]).compareTo(arrobject2[i]) > 0; --i) {
                    Arrays.swap(arrobject2, i, i - 1);
                }
            }
            return;
        }
        int n5 = n + n3;
        int n6 = n2 + n3;
        int n7 = n5 + n6 >>> 1;
        Arrays.mergeSort(arrobject2, arrobject, n5, n7, -n3);
        Arrays.mergeSort(arrobject2, arrobject, n7, n6, -n3);
        if (((Comparable)arrobject[n7 - 1]).compareTo(arrobject[n7]) <= 0) {
            System.arraycopy(arrobject, n5, arrobject2, n, n4);
            return;
        }
        n3 = n5;
        n5 = n7;
        while (n < n2) {
            if (n5 < n6 && (n3 >= n7 || ((Comparable)arrobject[n3]).compareTo(arrobject[n5]) > 0)) {
                arrobject2[n] = arrobject[n5];
                ++n5;
            } else {
                arrobject2[n] = arrobject[n3];
                ++n3;
            }
            ++n;
        }
    }

    public static void parallelPrefix(double[] arrd, int n, int n2, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        Arrays.rangeCheck(arrd.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.DoubleCumulateTask(null, doubleBinaryOperator, arrd, n, n2).invoke();
        }
    }

    public static void parallelPrefix(double[] arrd, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        if (arrd.length > 0) {
            new ArrayPrefixHelpers.DoubleCumulateTask(null, doubleBinaryOperator, arrd, 0, arrd.length).invoke();
        }
    }

    public static void parallelPrefix(int[] arrn, int n, int n2, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        Arrays.rangeCheck(arrn.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.IntCumulateTask(null, intBinaryOperator, arrn, n, n2).invoke();
        }
    }

    public static void parallelPrefix(int[] arrn, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        if (arrn.length > 0) {
            new ArrayPrefixHelpers.IntCumulateTask(null, intBinaryOperator, arrn, 0, arrn.length).invoke();
        }
    }

    public static void parallelPrefix(long[] arrl, int n, int n2, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        Arrays.rangeCheck(arrl.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.LongCumulateTask(null, longBinaryOperator, arrl, n, n2).invoke();
        }
    }

    public static void parallelPrefix(long[] arrl, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        if (arrl.length > 0) {
            new ArrayPrefixHelpers.LongCumulateTask(null, longBinaryOperator, arrl, 0, arrl.length).invoke();
        }
    }

    public static <T> void parallelPrefix(T[] arrT, int n, int n2, BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        Arrays.rangeCheck(arrT.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.CumulateTask<T>(null, binaryOperator, arrT, n, n2).invoke();
        }
    }

    public static <T> void parallelPrefix(T[] arrT, BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        if (arrT.length > 0) {
            new ArrayPrefixHelpers.CumulateTask<T>(null, binaryOperator, arrT, 0, arrT.length).invoke();
        }
    }

    public static void parallelSetAll(double[] arrd, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        IntStream.range(0, arrd.length).parallel().forEach(new _$$Lambda$Arrays$x0HcRDlColwoPupFWmOW7TREPtM(arrd, intToDoubleFunction));
    }

    public static void parallelSetAll(int[] arrn, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        IntStream.range(0, arrn.length).parallel().forEach(new _$$Lambda$Arrays$KFf05FUz26CqVc_cf2bKY9C927o(arrn, intUnaryOperator));
    }

    public static void parallelSetAll(long[] arrl, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        IntStream.range(0, arrl.length).parallel().forEach(new _$$Lambda$Arrays$aBSX_SvA5f2Q1t8_MODHDGhokzk(arrl, intToLongFunction));
    }

    public static <T> void parallelSetAll(T[] arrT, IntFunction<? extends T> intFunction) {
        Objects.requireNonNull(intFunction);
        IntStream.range(0, arrT.length).parallel().forEach(new _$$Lambda$Arrays$H0YqaggIxZUqId4_BJ1BLcUa93k(arrT, intFunction));
    }

    public static void parallelSort(byte[] arrby) {
        int n;
        int n2 = arrby.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            byte[] arrby2 = new byte[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJByte.Sorter(null, arrby, arrby2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrby, 0, n2 - 1);
        }
    }

    public static void parallelSort(byte[] arrby, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrby.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            byte[] arrby2 = new byte[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJByte.Sorter(null, arrby, arrby2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrby, n, n2 - 1);
        }
    }

    public static void parallelSort(char[] arrc) {
        int n;
        int n2 = arrc.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            char[] arrc2 = new char[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJChar.Sorter(null, arrc, arrc2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrc, 0, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(char[] arrc, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrc.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            char[] arrc2 = new char[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJChar.Sorter(null, arrc, arrc2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrc, n, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(double[] arrd) {
        int n;
        int n2 = arrd.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            double[] arrd2 = new double[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJDouble.Sorter(null, arrd, arrd2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrd, 0, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(double[] arrd, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrd.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            double[] arrd2 = new double[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJDouble.Sorter(null, arrd, arrd2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrd, n, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(float[] arrf) {
        int n;
        int n2 = arrf.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            float[] arrf2 = new float[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJFloat.Sorter(null, arrf, arrf2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrf, 0, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(float[] arrf, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrf.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            float[] arrf2 = new float[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJFloat.Sorter(null, arrf, arrf2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrf, n, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(int[] arrn) {
        int n;
        int n2 = arrn.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            int[] arrn2 = new int[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJInt.Sorter(null, arrn, arrn2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrn, 0, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(int[] arrn, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrn.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            int[] arrn2 = new int[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJInt.Sorter(null, arrn, arrn2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrn, n, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(long[] arrl) {
        int n;
        int n2 = arrl.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            long[] arrl2 = new long[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJLong.Sorter(null, arrl, arrl2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrl, 0, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(long[] arrl, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrl.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            long[] arrl2 = new long[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJLong.Sorter(null, arrl, arrl2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrl, n, n2 - 1, null, 0, 0);
        }
    }

    public static <T extends Comparable<? super T>> void parallelSort(T[] arrT) {
        int n;
        int n2 = arrT.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            Comparable[] arrcomparable = (Comparable[])Array.newInstance(arrT.getClass().getComponentType(), n2);
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJObject.Sorter<Object>(null, arrT, arrcomparable, 0, n2, 0, n, NaturalOrder.INSTANCE).invoke();
        } else {
            TimSort.sort(arrT, 0, n2, NaturalOrder.INSTANCE, null, 0, 0);
        }
    }

    public static <T extends Comparable<? super T>> void parallelSort(T[] arrT, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrT.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            Comparable[] arrcomparable = (Comparable[])Array.newInstance(arrT.getClass().getComponentType(), n4);
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJObject.Sorter<Object>(null, arrT, arrcomparable, n, n4, 0, n2, NaturalOrder.INSTANCE).invoke();
        } else {
            TimSort.sort(arrT, n, n2, NaturalOrder.INSTANCE, null, 0, 0);
        }
    }

    public static <T> void parallelSort(T[] arrT, int n, int n2, Comparator<? super T> naturalOrder) {
        int n3;
        int n4;
        Arrays.rangeCheck(arrT.length, n, n2);
        if (naturalOrder == null) {
            naturalOrder = NaturalOrder.INSTANCE;
        }
        if ((n4 = n2 - n) > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            Object[] arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n4);
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJObject.Sorter<Object>(null, arrT, arrobject, n, n4, 0, n2, naturalOrder).invoke();
        } else {
            TimSort.sort(arrT, n, n2, naturalOrder, null, 0, 0);
        }
    }

    public static <T> void parallelSort(T[] arrT, Comparator<? super T> arrobject) {
        int n;
        int n2;
        Object[] arrobject2 = arrobject;
        if (arrobject == null) {
            arrobject2 = NaturalOrder.INSTANCE;
        }
        if ((n2 = arrT.length) > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n2);
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJObject.Sorter<Object>(null, arrT, (T[])arrobject, 0, n2, 0, n, (Comparator<Object>)arrobject2).invoke();
        } else {
            TimSort.sort(arrT, 0, n2, arrobject2, null, 0, 0);
        }
    }

    public static void parallelSort(short[] arrs) {
        int n;
        int n2 = arrs.length;
        if (n2 > 8192 && (n = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            short[] arrs2 = new short[n2];
            if ((n = n2 / (n << 2)) <= 8192) {
                n = 8192;
            }
            new ArraysParallelSortHelpers.FJShort.Sorter(null, arrs, arrs2, 0, n2, 0, n).invoke();
        } else {
            DualPivotQuicksort.sort(arrs, 0, n2 - 1, null, 0, 0);
        }
    }

    public static void parallelSort(short[] arrs, int n, int n2) {
        int n3;
        Arrays.rangeCheck(arrs.length, n, n2);
        int n4 = n2 - n;
        if (n4 > 8192 && (n3 = ForkJoinPool.getCommonPoolParallelism()) != 1) {
            short[] arrs2 = new short[n4];
            n2 = n4 / (n3 << 2);
            if (n2 <= 8192) {
                n2 = 8192;
            }
            new ArraysParallelSortHelpers.FJShort.Sorter(null, arrs, arrs2, n, n4, 0, n2).invoke();
        } else {
            DualPivotQuicksort.sort(arrs, n, n2 - 1, null, 0, 0);
        }
    }

    private static void rangeCheck(int n, int n2, int n3) {
        if (n2 <= n3) {
            if (n2 >= 0) {
                if (n3 <= n) {
                    return;
                }
                throw new ArrayIndexOutOfBoundsException(n3);
            }
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromIndex(");
        stringBuilder.append(n2);
        stringBuilder.append(") > toIndex(");
        stringBuilder.append(n3);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static void setAll(double[] arrd, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        for (int i = 0; i < arrd.length; ++i) {
            arrd[i] = intToDoubleFunction.applyAsDouble(i);
        }
    }

    public static void setAll(int[] arrn, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = intUnaryOperator.applyAsInt(i);
        }
    }

    public static void setAll(long[] arrl, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        for (int i = 0; i < arrl.length; ++i) {
            arrl[i] = intToLongFunction.applyAsLong(i);
        }
    }

    public static <T> void setAll(T[] arrT, IntFunction<? extends T> intFunction) {
        Objects.requireNonNull(intFunction);
        for (int i = 0; i < arrT.length; ++i) {
            arrT[i] = intFunction.apply(i);
        }
    }

    public static void sort(byte[] arrby) {
        DualPivotQuicksort.sort(arrby, 0, arrby.length - 1);
    }

    public static void sort(byte[] arrby, int n, int n2) {
        Arrays.rangeCheck(arrby.length, n, n2);
        DualPivotQuicksort.sort(arrby, n, n2 - 1);
    }

    public static void sort(char[] arrc) {
        DualPivotQuicksort.sort(arrc, 0, arrc.length - 1, null, 0, 0);
    }

    public static void sort(char[] arrc, int n, int n2) {
        Arrays.rangeCheck(arrc.length, n, n2);
        DualPivotQuicksort.sort(arrc, n, n2 - 1, null, 0, 0);
    }

    public static void sort(double[] arrd) {
        DualPivotQuicksort.sort(arrd, 0, arrd.length - 1, null, 0, 0);
    }

    public static void sort(double[] arrd, int n, int n2) {
        Arrays.rangeCheck(arrd.length, n, n2);
        DualPivotQuicksort.sort(arrd, n, n2 - 1, null, 0, 0);
    }

    public static void sort(float[] arrf) {
        DualPivotQuicksort.sort(arrf, 0, arrf.length - 1, null, 0, 0);
    }

    public static void sort(float[] arrf, int n, int n2) {
        Arrays.rangeCheck(arrf.length, n, n2);
        DualPivotQuicksort.sort(arrf, n, n2 - 1, null, 0, 0);
    }

    public static void sort(int[] arrn) {
        DualPivotQuicksort.sort(arrn, 0, arrn.length - 1, null, 0, 0);
    }

    public static void sort(int[] arrn, int n, int n2) {
        Arrays.rangeCheck(arrn.length, n, n2);
        DualPivotQuicksort.sort(arrn, n, n2 - 1, null, 0, 0);
    }

    public static void sort(long[] arrl) {
        DualPivotQuicksort.sort(arrl, 0, arrl.length - 1, null, 0, 0);
    }

    public static void sort(long[] arrl, int n, int n2) {
        Arrays.rangeCheck(arrl.length, n, n2);
        DualPivotQuicksort.sort(arrl, n, n2 - 1, null, 0, 0);
    }

    public static void sort(Object[] arrobject) {
        ComparableTimSort.sort(arrobject, 0, arrobject.length, null, 0, 0);
    }

    public static void sort(Object[] arrobject, int n, int n2) {
        Arrays.rangeCheck(arrobject.length, n, n2);
        ComparableTimSort.sort(arrobject, n, n2, null, 0, 0);
    }

    public static <T> void sort(T[] arrT, int n, int n2, Comparator<? super T> comparator) {
        if (comparator == null) {
            Arrays.sort(arrT, n, n2);
        } else {
            Arrays.rangeCheck(arrT.length, n, n2);
            TimSort.sort(arrT, n, n2, comparator, null, 0, 0);
        }
    }

    public static <T> void sort(T[] arrT, Comparator<? super T> comparator) {
        if (comparator == null) {
            Arrays.sort(arrT);
        } else {
            TimSort.sort(arrT, 0, arrT.length, comparator, null, 0, 0);
        }
    }

    public static void sort(short[] arrs) {
        DualPivotQuicksort.sort(arrs, 0, arrs.length - 1, null, 0, 0);
    }

    public static void sort(short[] arrs, int n, int n2) {
        Arrays.rangeCheck(arrs.length, n, n2);
        DualPivotQuicksort.sort(arrs, n, n2 - 1, null, 0, 0);
    }

    public static Spliterator.OfDouble spliterator(double[] arrd) {
        return Spliterators.spliterator(arrd, 1040);
    }

    public static Spliterator.OfDouble spliterator(double[] arrd, int n, int n2) {
        return Spliterators.spliterator(arrd, n, n2, 1040);
    }

    public static Spliterator.OfInt spliterator(int[] arrn) {
        return Spliterators.spliterator(arrn, 1040);
    }

    public static Spliterator.OfInt spliterator(int[] arrn, int n, int n2) {
        return Spliterators.spliterator(arrn, n, n2, 1040);
    }

    public static Spliterator.OfLong spliterator(long[] arrl) {
        return Spliterators.spliterator(arrl, 1040);
    }

    public static Spliterator.OfLong spliterator(long[] arrl, int n, int n2) {
        return Spliterators.spliterator(arrl, n, n2, 1040);
    }

    public static <T> Spliterator<T> spliterator(T[] arrT) {
        return Spliterators.spliterator(arrT, 1040);
    }

    public static <T> Spliterator<T> spliterator(T[] arrT, int n, int n2) {
        return Spliterators.spliterator(arrT, n, n2, 1040);
    }

    public static DoubleStream stream(double[] arrd) {
        return Arrays.stream(arrd, 0, arrd.length);
    }

    public static DoubleStream stream(double[] arrd, int n, int n2) {
        return StreamSupport.doubleStream(Arrays.spliterator(arrd, n, n2), false);
    }

    public static IntStream stream(int[] arrn) {
        return Arrays.stream(arrn, 0, arrn.length);
    }

    public static IntStream stream(int[] arrn, int n, int n2) {
        return StreamSupport.intStream(Arrays.spliterator(arrn, n, n2), false);
    }

    public static LongStream stream(long[] arrl) {
        return Arrays.stream(arrl, 0, arrl.length);
    }

    public static LongStream stream(long[] arrl, int n, int n2) {
        return StreamSupport.longStream(Arrays.spliterator(arrl, n, n2), false);
    }

    public static <T> Stream<T> stream(T[] arrT) {
        return Arrays.stream(arrT, 0, arrT.length);
    }

    public static <T> Stream<T> stream(T[] arrT, int n, int n2) {
        return StreamSupport.stream(Arrays.spliterator(arrT, n, n2), false);
    }

    private static void swap(Object[] arrobject, int n, int n2) {
        Object object = arrobject[n];
        arrobject[n] = arrobject[n2];
        arrobject[n2] = object;
    }

    public static String toString(byte[] arrby) {
        if (arrby == null) {
            return "null";
        }
        int n = arrby.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrby[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(char[] arrc) {
        if (arrc == null) {
            return "null";
        }
        int n = arrc.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrc[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(double[] arrd) {
        if (arrd == null) {
            return "null";
        }
        int n = arrd.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrd[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(float[] arrf) {
        if (arrf == null) {
            return "null";
        }
        int n = arrf.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrf[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(int[] arrn) {
        if (arrn == null) {
            return "null";
        }
        int n = arrn.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrn[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(long[] arrl) {
        if (arrl == null) {
            return "null";
        }
        int n = arrl.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrl[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(Object[] arrobject) {
        if (arrobject == null) {
            return "null";
        }
        int n = arrobject.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(String.valueOf(arrobject[n2]));
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(short[] arrs) {
        if (arrs == null) {
            return "null";
        }
        int n = arrs.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrs[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    public static String toString(boolean[] arrbl) {
        if (arrbl == null) {
            return "null";
        }
        int n = arrbl.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(arrbl[n2]);
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            ++n2;
        } while (true);
    }

    private static class ArrayList<E>
    extends AbstractList<E>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;

        ArrayList(E[] arrE) {
            this.a = Objects.requireNonNull(arrE);
        }

        @Override
        public boolean contains(Object object) {
            boolean bl = this.indexOf(object) != -1;
            return bl;
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            E[] arrE = this.a;
            int n = arrE.length;
            for (int i = 0; i < n; ++i) {
                consumer.accept(arrE[i]);
            }
        }

        @Override
        public E get(int n) {
            return this.a[n];
        }

        @Override
        public int indexOf(Object object) {
            E[] arrE = this.a;
            if (object == null) {
                for (int i = 0; i < arrE.length; ++i) {
                    if (arrE[i] != null) continue;
                    return i;
                }
            } else {
                for (int i = 0; i < arrE.length; ++i) {
                    if (!object.equals(arrE[i])) continue;
                    return i;
                }
            }
            return -1;
        }

        @Override
        public void replaceAll(UnaryOperator<E> unaryOperator) {
            Objects.requireNonNull(unaryOperator);
            E[] arrE = this.a;
            for (int i = 0; i < arrE.length; ++i) {
                arrE[i] = unaryOperator.apply(arrE[i]);
            }
        }

        @Override
        public E set(int n, E e) {
            E[] arrE = this.a;
            E e2 = arrE[n];
            arrE[n] = e;
            return e2;
        }

        @Override
        public int size() {
            return this.a.length;
        }

        @Override
        public void sort(Comparator<? super E> comparator) {
            Arrays.sort(this.a, comparator);
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.spliterator(this.a, 16);
        }

        @Override
        public Object[] toArray() {
            return (Object[])this.a.clone();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            int n = this.size();
            if (arrT.length < n) {
                return Arrays.copyOf(this.a, n, arrT.getClass());
            }
            System.arraycopy(this.a, 0, arrT, 0, n);
            if (arrT.length > n) {
                arrT[n] = null;
            }
            return arrT;
        }
    }

    static final class NaturalOrder
    implements Comparator<Object> {
        static final NaturalOrder INSTANCE = new NaturalOrder();

        NaturalOrder() {
        }

        @Override
        public int compare(Object object, Object object2) {
            return ((Comparable)object).compareTo(object2);
        }
    }

}

