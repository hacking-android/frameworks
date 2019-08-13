/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

final class ArrayUtils {
    private ArrayUtils() {
    }

    static void checkOffsetAndCount(int n, int n2, int n3) {
        if ((n2 | n3) >= 0 && n2 <= n && n - n2 >= n3) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("length=");
        stringBuilder.append(n);
        stringBuilder.append("; regionStart=");
        stringBuilder.append(n2);
        stringBuilder.append("; regionLength=");
        stringBuilder.append(n3);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }
}

