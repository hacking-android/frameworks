/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.text.TextUtils;
import java.util.Collection;
import java.util.Iterator;

public class Preconditions {
    @UnsupportedAppUsage
    public static void checkArgument(boolean bl) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException();
    }

    @UnsupportedAppUsage
    public static void checkArgument(boolean bl, Object object) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException(String.valueOf(object));
    }

    public static void checkArgument(boolean bl, String string2, Object ... arrobject) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException(String.format(string2, arrobject));
    }

    public static float checkArgumentFinite(float f, String string2) {
        if (!Float.isNaN(f)) {
            if (!Float.isInfinite(f)) {
                return f;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" must not be infinite");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must not be NaN");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static float checkArgumentInRange(float f, float f2, float f3, String string2) {
        if (!Float.isNaN(f)) {
            if (!(f < f2)) {
                if (!(f > f3)) {
                    return f;
                }
                throw new IllegalArgumentException(String.format("%s is out of range of [%f, %f] (too high)", string2, Float.valueOf(f2), Float.valueOf(f3)));
            }
            throw new IllegalArgumentException(String.format("%s is out of range of [%f, %f] (too low)", string2, Float.valueOf(f2), Float.valueOf(f3)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must not be NaN");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int checkArgumentInRange(int n, int n2, int n3, String string2) {
        if (n >= n2) {
            if (n <= n3) {
                return n;
            }
            throw new IllegalArgumentException(String.format("%s is out of range of [%d, %d] (too high)", string2, n2, n3));
        }
        throw new IllegalArgumentException(String.format("%s is out of range of [%d, %d] (too low)", string2, n2, n3));
    }

    public static long checkArgumentInRange(long l, long l2, long l3, String string2) {
        if (l >= l2) {
            if (l <= l3) {
                return l;
            }
            throw new IllegalArgumentException(String.format("%s is out of range of [%d, %d] (too high)", string2, l2, l3));
        }
        throw new IllegalArgumentException(String.format("%s is out of range of [%d, %d] (too low)", string2, l2, l3));
    }

    public static float checkArgumentNonNegative(float f, String string2) {
        if (!(f < 0.0f)) {
            return f;
        }
        throw new IllegalArgumentException(string2);
    }

    public static int checkArgumentNonnegative(int n) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException();
    }

    public static int checkArgumentNonnegative(int n, String string2) {
        if (n >= 0) {
            return n;
        }
        throw new IllegalArgumentException(string2);
    }

    public static long checkArgumentNonnegative(long l) {
        if (l >= 0L) {
            return l;
        }
        throw new IllegalArgumentException();
    }

    public static long checkArgumentNonnegative(long l, String string2) {
        if (l >= 0L) {
            return l;
        }
        throw new IllegalArgumentException(string2);
    }

    public static float checkArgumentPositive(float f, String string2) {
        if (!(f <= 0.0f)) {
            return f;
        }
        throw new IllegalArgumentException(string2);
    }

    public static int checkArgumentPositive(int n, String string2) {
        if (n > 0) {
            return n;
        }
        throw new IllegalArgumentException(string2);
    }

    public static float[] checkArrayElementsInRange(float[] object, float f, float f2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must not be null");
        Preconditions.checkNotNull(object, stringBuilder.toString());
        for (int i = 0; i < ((float[])object).length; ++i) {
            float f3 = object[i];
            if (!Float.isNaN(f3)) {
                if (!(f3 < f)) {
                    if (!(f3 > f2)) {
                        continue;
                    }
                    throw new IllegalArgumentException(String.format("%s[%d] is out of range of [%f, %f] (too high)", string2, i, Float.valueOf(f), Float.valueOf(f2)));
                }
                throw new IllegalArgumentException(String.format("%s[%d] is out of range of [%f, %f] (too low)", string2, i, Float.valueOf(f), Float.valueOf(f2)));
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("[");
            ((StringBuilder)object).append(i);
            ((StringBuilder)object).append("] must not be NaN");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return object;
    }

    public static int[] checkArrayElementsInRange(int[] arrn, int n, int n2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" must not be null");
        Preconditions.checkNotNull(arrn, stringBuilder.toString());
        for (int i = 0; i < arrn.length; ++i) {
            int n3 = arrn[i];
            if (n3 >= n) {
                if (n3 <= n2) {
                    continue;
                }
                throw new IllegalArgumentException(String.format("%s[%d] is out of range of [%d, %d] (too high)", string2, i, n, n2));
            }
            throw new IllegalArgumentException(String.format("%s[%d] is out of range of [%d, %d] (too low)", string2, i, n, n2));
        }
        return arrn;
    }

    public static <T> T[] checkArrayElementsNotNull(T[] object, String string2) {
        if (object != null) {
            for (int i = 0; i < ((T[])object).length; ++i) {
                if (object[i] != null) {
                    continue;
                }
                throw new NullPointerException(String.format("%s[%d] must not be null", string2, i));
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" must not be null");
        throw new NullPointerException(((StringBuilder)object).toString());
    }

    public static <C extends Collection<T>, T> C checkCollectionElementsNotNull(C object, String string2) {
        if (object != null) {
            long l = 0L;
            Iterator<T> iterator = object.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() != null) {
                    ++l;
                    continue;
                }
                throw new NullPointerException(String.format("%s[%d] must not be null", string2, l));
            }
            return (C)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" must not be null");
        throw new NullPointerException(((StringBuilder)object).toString());
    }

    public static <T> Collection<T> checkCollectionNotEmpty(Collection<T> object, String string2) {
        if (object != null) {
            if (!object.isEmpty()) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" is empty");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" must not be null");
        throw new NullPointerException(((StringBuilder)object).toString());
    }

    public static int checkFlagsArgument(int n, int n2) {
        if ((n & n2) == n) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested flags 0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(", but only 0x");
        stringBuilder.append(Integer.toHexString(n2));
        stringBuilder.append(" are allowed");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    @UnsupportedAppUsage
    public static <T> T checkNotNull(T t, Object object) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(object));
    }

    public static <T> T checkNotNull(T t, String string2, Object ... arrobject) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.format(string2, arrobject));
    }

    @UnsupportedAppUsage
    public static void checkState(boolean bl) {
        Preconditions.checkState(bl, null);
    }

    @UnsupportedAppUsage
    public static void checkState(boolean bl, String string2) {
        if (bl) {
            return;
        }
        throw new IllegalStateException(string2);
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T t) {
        if (!TextUtils.isEmpty(t)) {
            return t;
        }
        throw new IllegalArgumentException();
    }

    public static <T extends CharSequence> T checkStringNotEmpty(T t, Object object) {
        if (!TextUtils.isEmpty(t)) {
            return t;
        }
        throw new IllegalArgumentException(String.valueOf(object));
    }
}

