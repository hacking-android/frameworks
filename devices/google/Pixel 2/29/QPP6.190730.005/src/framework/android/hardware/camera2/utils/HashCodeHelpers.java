/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.annotation.UnsupportedAppUsage;

public final class HashCodeHelpers {
    public static int hashCode(float ... arrf) {
        if (arrf == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrf.length;
        for (int i = 0; i < n2; ++i) {
            n = (n << 5) - n ^ Float.floatToIntBits(arrf[i]);
        }
        return n;
    }

    @UnsupportedAppUsage
    public static int hashCode(int ... arrn) {
        if (arrn == null) {
            return 0;
        }
        int n = 1;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            n = (n << 5) - n ^ arrn[i];
        }
        return n;
    }

    public static <T> int hashCodeGeneric(T ... arrT) {
        if (arrT == null) {
            return 0;
        }
        int n = arrT.length;
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            T t = arrT[i];
            int n3 = t == null ? 0 : t.hashCode();
            n2 = (n2 << 5) - n2 ^ n3;
        }
        return n2;
    }
}

