/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import com.android.internal.util.Preconditions;

public class ObjectUtils {
    private ObjectUtils() {
    }

    public static <T extends Comparable> int compare(T t, T t2) {
        if (t != null) {
            int n = t2 != null ? t.compareTo(t2) : 1;
            return n;
        }
        int n = t2 != null ? -1 : 0;
        return n;
    }

    public static <T> T firstNotNull(T t, T t2) {
        if (t == null) {
            t = Preconditions.checkNotNull(t2);
        }
        return t;
    }
}

