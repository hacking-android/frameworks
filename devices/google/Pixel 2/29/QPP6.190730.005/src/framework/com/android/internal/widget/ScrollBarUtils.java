/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;

public class ScrollBarUtils {
    @UnsupportedAppUsage
    public static int getThumbLength(int n, int n2, int n3, int n4) {
        n = n3 = Math.round((float)n * (float)n3 / (float)n4);
        if (n3 < (n2 *= 2)) {
            n = n2;
        }
        return n;
    }

    public static int getThumbOffset(int n, int n2, int n3, int n4, int n5) {
        n3 = n4 = Math.round((float)(n - n2) * (float)n5 / (float)(n4 - n3));
        if (n4 > n - n2) {
            n3 = n - n2;
        }
        return n3;
    }
}

