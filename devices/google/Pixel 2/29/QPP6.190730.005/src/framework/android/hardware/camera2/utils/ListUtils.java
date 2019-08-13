/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import java.util.Iterator;
import java.util.List;

public class ListUtils {
    private ListUtils() {
        throw new AssertionError();
    }

    public static <T> boolean listContains(List<T> list, T t) {
        if (list == null) {
            return false;
        }
        return list.contains(t);
    }

    public static <T> boolean listElementsEqualTo(List<T> list, T t) {
        boolean bl = false;
        if (list == null) {
            return false;
        }
        boolean bl2 = bl;
        if (list.size() == 1) {
            bl2 = bl;
            if (list.contains(t)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static <T> T listSelectFirstFrom(List<T> list, T[] arrT) {
        if (list == null) {
            return null;
        }
        for (T t : arrT) {
            if (!list.contains(t)) continue;
            return t;
        }
        return null;
    }

    public static <T> String listToString(List<T> object) {
        if (object == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n = object.size();
        int n2 = 0;
        object = object.iterator();
        while (object.hasNext()) {
            stringBuilder.append(object.next());
            if (n2 != n - 1) {
                stringBuilder.append(',');
            }
            ++n2;
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

