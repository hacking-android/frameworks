/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

public final class Objects {
    private Objects() {
        throw new AssertionError((Object)"No java.util.Objects instances for you!");
    }

    public static <T> int compare(T t, T t2, Comparator<? super T> comparator) {
        int n = t == t2 ? 0 : comparator.compare(t, t2);
        return n;
    }

    public static boolean deepEquals(Object object, Object object2) {
        if (object == object2) {
            return true;
        }
        if (object != null && object2 != null) {
            return Arrays.deepEquals0(object, object2);
        }
        return false;
    }

    public static boolean equals(Object object, Object object2) {
        boolean bl = object == object2 || object != null && object.equals(object2);
        return bl;
    }

    public static int hash(Object ... arrobject) {
        return Arrays.hashCode(arrobject);
    }

    public static int hashCode(Object object) {
        int n = object != null ? object.hashCode() : 0;
        return n;
    }

    public static boolean isNull(Object object) {
        boolean bl = object == null;
        return bl;
    }

    public static boolean nonNull(Object object) {
        boolean bl = object != null;
        return bl;
    }

    public static <T> T requireNonNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static <T> T requireNonNull(T t, String string) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(string);
    }

    public static <T> T requireNonNull(T t, Supplier<String> supplier) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(supplier.get());
    }

    public static String toString(Object object) {
        return String.valueOf(object);
    }

    public static String toString(Object object, String string) {
        block0 : {
            if (object == null) break block0;
            string = object.toString();
        }
        return string;
    }
}

