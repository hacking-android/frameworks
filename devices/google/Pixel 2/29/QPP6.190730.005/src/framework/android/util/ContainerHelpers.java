/*
 * Decompiled with CFR 0.145.
 */
package android.util;

class ContainerHelpers {
    ContainerHelpers() {
    }

    static int binarySearch(int[] arrn, int n, int n2) {
        int n3 = 0;
        --n;
        while (n3 <= n) {
            int n4 = n3 + n >>> 1;
            int n5 = arrn[n4];
            if (n5 < n2) {
                n3 = n4 + 1;
                continue;
            }
            if (n5 > n2) {
                n = n4 - 1;
                continue;
            }
            return n4;
        }
        return n3;
    }

    static int binarySearch(long[] arrl, int n, long l) {
        int n2 = 0;
        --n;
        while (n2 <= n) {
            int n3 = n2 + n >>> 1;
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
        return n2;
    }
}

