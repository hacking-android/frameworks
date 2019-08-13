/*
 * Decompiled with CFR 0.145.
 */
package java.math;

class Division {
    Division() {
    }

    static int divideArrayByInt(int[] arrn, int[] arrn2, int n, int n2) {
        long l = 0L;
        long l2 = (long)n2 & 0xFFFFFFFFL;
        --n;
        while (n >= 0) {
            long l3;
            if ((l = l << 32 | (long)arrn2[n] & 0xFFFFFFFFL) >= 0L) {
                l3 = l / l2;
                l %= l2;
            } else {
                long l4 = l >>> 1;
                long l5 = n2 >>> 1;
                l3 = l4 / l5;
                l = (l4 % l5 << 1) + (l & 1L);
                if ((n2 & 1) != 0) {
                    if (l3 <= l) {
                        l -= l3;
                    } else if (l3 - l <= l2) {
                        l5 = l3 - 1L;
                        l += l2 - l3;
                        l3 = l5;
                    } else {
                        l5 = l3 - 2L;
                        l += (l2 << 1) - l3;
                        l3 = l5;
                    }
                }
            }
            arrn[n] = (int)(l3 & 0xFFFFFFFFL);
            --n;
        }
        return (int)l;
    }
}

