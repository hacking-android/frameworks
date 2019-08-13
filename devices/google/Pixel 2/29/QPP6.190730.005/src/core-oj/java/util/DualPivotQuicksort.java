/*
 * Decompiled with CFR 0.145.
 */
package java.util;

final class DualPivotQuicksort {
    private static final int COUNTING_SORT_THRESHOLD_FOR_BYTE = 29;
    private static final int COUNTING_SORT_THRESHOLD_FOR_SHORT_OR_CHAR = 3200;
    private static final int INSERTION_SORT_THRESHOLD = 47;
    private static final int MAX_RUN_COUNT = 67;
    private static final int MAX_RUN_LENGTH = 33;
    private static final int NUM_BYTE_VALUES = 256;
    private static final int NUM_CHAR_VALUES = 65536;
    private static final int NUM_SHORT_VALUES = 65536;
    private static final int QUICKSORT_THRESHOLD = 286;

    private DualPivotQuicksort() {
    }

    private static void doSort(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        char[] arrc3 = arrc;
        if (n2 - n < 286) {
            DualPivotQuicksort.sort(arrc3, n, n2, true);
            return;
        }
        int[] arrn = new int[68];
        int n8 = 0;
        arrn[0] = n;
        int n9 = n;
        while (n9 < n2) {
            block26 : {
                if (arrc3[n9] < arrc3[n9 + 1]) {
                    do {
                        n9 = n5 = n9 + 1;
                        if (n5 <= n2) {
                            n9 = n5;
                            if (arrc3[n5 - 1] <= arrc3[n5]) {
                                n9 = n5;
                                continue;
                            }
                        }
                        break block26;
                        break;
                    } while (true);
                }
                if (arrc3[n9] > arrc3[n9 + 1]) {
                    while (++n9 <= n2 && arrc3[n9 - 1] >= arrc3[n9]) {
                    }
                    n7 = arrn[n8] - 1;
                    n5 = n9;
                    while (++n7 < --n5) {
                        n6 = arrc3[n7];
                        arrc3[n7] = arrc3[n5];
                        arrc3[n5] = (char)n6;
                    }
                } else {
                    n5 = 33;
                    do {
                        n9 = n7 = n9 + 1;
                        if (n7 > n2) break block26;
                        n9 = n7;
                        if (arrc3[n7 - 1] != arrc3[n7]) break block26;
                        n6 = n5 - 1;
                        n9 = n7;
                        n5 = n6;
                    } while (n6 != 0);
                    DualPivotQuicksort.sort(arrc3, n, n2, true);
                    return;
                }
            }
            if (++n8 == 67) {
                DualPivotQuicksort.sort(arrc3, n, n2, true);
                return;
            }
            arrn[n8] = n9;
        }
        n9 = arrn[n8];
        int n10 = n2 + 1;
        if (n9 == n2) {
            n9 = n8 + 1;
            arrn[n9] = n10;
        } else {
            n9 = n8;
            if (n8 == 1) {
                return;
            }
        }
        n8 = 0;
        n2 = 1;
        do {
            n2 = n5 = n2 << 1;
            if (n5 >= n9) break;
            n8 = (byte)(n8 ^ 1);
        } while (true);
        n7 = n10 - n;
        if (arrc2 != null && n4 >= n7 && n3 + n7 <= arrc2.length) {
            n2 = n3;
        } else {
            arrc2 = new char[n7];
            n2 = 0;
        }
        if (n8 == 0) {
            System.arraycopy((Object)arrc3, n, (Object)arrc2, n2, n7);
            n3 = 0;
            arrc3 = arrc2;
            n2 -= n;
            n5 = n9;
            n4 = n7;
            n = n3;
        } else {
            arrc = arrc2;
            n3 = 0;
            n = n2 - n;
            n2 = n3;
            n4 = n7;
            n5 = n9;
        }
        do {
            n7 = n2;
            if (n5 <= 1) break;
            n3 = 0;
            n2 = n8;
            for (n9 = 2; n9 <= n5; n9 += 2) {
                int n11;
                int n12 = arrn[n9];
                n8 = arrn[n9 - 1];
                int n13 = n8;
                for (n6 = n11 = arrn[n9 - 2]; n6 < n12; ++n6) {
                    if (n13 < n12 && (n11 >= n8 || arrc3[n11 + n7] > arrc3[n13 + n7])) {
                        arrc[n6 + n] = arrc3[n13 + n7];
                        ++n13;
                        continue;
                    }
                    arrc[n6 + n] = arrc3[n11 + n7];
                    ++n11;
                }
                arrn[++n3] = n12;
            }
            n9 = n3;
            if ((n5 & 1) != 0) {
                n9 = n10;
                n8 = arrn[n5 - 1];
                while (--n9 >= n8) {
                    arrc[n9 + n] = arrc3[n9 + n7];
                }
                n9 = n3 + 1;
                arrn[n9] = n10;
            }
            char[] arrc4 = arrc;
            arrc = arrc3;
            n3 = n;
            n = n7;
            arrc3 = arrc4;
            n8 = n2;
            n5 = n9;
            n2 = n3;
        } while (true);
    }

    private static void doSort(double[] arrd, int n, int n2, double[] arrd2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        double[] arrd3 = arrd;
        if (n2 - n < 286) {
            DualPivotQuicksort.sort(arrd3, n, n2, true);
            return;
        }
        int[] arrn = new int[68];
        int n8 = 0;
        arrn[0] = n;
        int n9 = n;
        while (n9 < n2) {
            block26 : {
                if (arrd3[n9] < arrd3[n9 + 1]) {
                    do {
                        n9 = n5 = n9 + 1;
                        if (n5 <= n2) {
                            n9 = n5;
                            if (arrd3[n5 - 1] <= arrd3[n5]) {
                                n9 = n5;
                                continue;
                            }
                        }
                        break block26;
                        break;
                    } while (true);
                }
                if (arrd3[n9] > arrd3[n9 + 1]) {
                    while (++n9 <= n2 && arrd3[n9 - 1] >= arrd3[n9]) {
                    }
                    n6 = arrn[n8] - 1;
                    n5 = n9;
                    while (++n6 < --n5) {
                        double d = arrd3[n6];
                        arrd3[n6] = arrd3[n5];
                        arrd3[n5] = d;
                    }
                } else {
                    n5 = 33;
                    do {
                        n9 = n6 = n9 + 1;
                        if (n6 > n2) break block26;
                        n9 = n6;
                        if (arrd3[n6 - 1] != arrd3[n6]) break block26;
                        n7 = n5 - 1;
                        n9 = n6;
                        n5 = n7;
                    } while (n7 != 0);
                    DualPivotQuicksort.sort(arrd3, n, n2, true);
                    return;
                }
            }
            if (++n8 == 67) {
                DualPivotQuicksort.sort(arrd3, n, n2, true);
                return;
            }
            arrn[n8] = n9;
        }
        n9 = arrn[n8];
        int n10 = n2 + 1;
        if (n9 == n2) {
            n9 = n8 + 1;
            arrn[n9] = n10;
        } else {
            n9 = n8;
            if (n8 == 1) {
                return;
            }
        }
        n8 = 0;
        n2 = 1;
        do {
            n2 = n5 = n2 << 1;
            if (n5 >= n9) break;
            n8 = (byte)(n8 ^ 1);
        } while (true);
        n5 = n10 - n;
        if (arrd2 != null && n4 >= n5 && n3 + n5 <= arrd2.length) {
            n2 = n3;
        } else {
            arrd2 = new double[n5];
            n2 = 0;
        }
        if (n8 == 0) {
            System.arraycopy((Object)arrd3, n, (Object)arrd2, n2, n5);
            n3 = 0;
            arrd3 = arrd2;
            n2 -= n;
            n = n3;
        } else {
            arrd = arrd2;
            n3 = 0;
            n = n2 - n;
            n2 = n3;
        }
        do {
            n5 = n2;
            if (n9 <= 1) break;
            n3 = 0;
            n2 = n8;
            for (n4 = 2; n4 <= n9; n4 += 2) {
                int n11 = arrn[n4];
                int n12 = arrn[n4 - 1];
                n7 = n12;
                for (n8 = n6 = arrn[n4 - 2]; n8 < n11; ++n8) {
                    if (!(n7 >= n11 || n6 < n12 && arrd3[n6 + n5] <= arrd3[n7 + n5])) {
                        arrd[n8 + n] = arrd3[n7 + n5];
                        ++n7;
                        continue;
                    }
                    arrd[n8 + n] = arrd3[n6 + n5];
                    ++n6;
                }
                arrn[++n3] = n11;
            }
            n4 = n3;
            if ((n9 & 1) != 0) {
                n4 = n10;
                n9 = arrn[n9 - 1];
                while (--n4 >= n9) {
                    arrd[n4 + n] = arrd3[n4 + n5];
                }
                n4 = n3 + 1;
                arrn[n4] = n10;
            }
            double[] arrd4 = arrd;
            arrd = arrd3;
            n3 = n;
            n = n5;
            arrd3 = arrd4;
            n8 = n2;
            n9 = n4;
            n2 = n3;
        } while (true);
    }

    private static void doSort(float[] arrf, int n, int n2, float[] arrf2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        float[] arrf3 = arrf;
        if (n2 - n < 286) {
            DualPivotQuicksort.sort(arrf3, n, n2, true);
            return;
        }
        int[] arrn = new int[68];
        int n8 = 0;
        arrn[0] = n;
        int n9 = n;
        while (n9 < n2) {
            block26 : {
                if (arrf3[n9] < arrf3[n9 + 1]) {
                    do {
                        n9 = n5 = n9 + 1;
                        if (n5 <= n2) {
                            n9 = n5;
                            if (arrf3[n5 - 1] <= arrf3[n5]) {
                                n9 = n5;
                                continue;
                            }
                        }
                        break block26;
                        break;
                    } while (true);
                }
                if (arrf3[n9] > arrf3[n9 + 1]) {
                    while (++n9 <= n2 && arrf3[n9 - 1] >= arrf3[n9]) {
                    }
                    n7 = arrn[n8] - 1;
                    n5 = n9;
                    while (++n7 < --n5) {
                        float f = arrf3[n7];
                        arrf3[n7] = arrf3[n5];
                        arrf3[n5] = f;
                    }
                } else {
                    n5 = 33;
                    do {
                        n9 = n7 = n9 + 1;
                        if (n7 > n2) break block26;
                        n9 = n7;
                        if (arrf3[n7 - 1] != arrf3[n7]) break block26;
                        n6 = n5 - 1;
                        n9 = n7;
                        n5 = n6;
                    } while (n6 != 0);
                    DualPivotQuicksort.sort(arrf3, n, n2, true);
                    return;
                }
            }
            if (++n8 == 67) {
                DualPivotQuicksort.sort(arrf3, n, n2, true);
                return;
            }
            arrn[n8] = n9;
        }
        n9 = arrn[n8];
        int n10 = n2 + 1;
        if (n9 == n2) {
            n9 = n8 + 1;
            arrn[n9] = n10;
        } else {
            n9 = n8;
            if (n8 == 1) {
                return;
            }
        }
        n8 = 0;
        n2 = 1;
        do {
            n2 = n5 = n2 << 1;
            if (n5 >= n9) break;
            n8 = (byte)(n8 ^ 1);
        } while (true);
        n5 = n10 - n;
        if (arrf2 != null && n4 >= n5 && n3 + n5 <= arrf2.length) {
            n2 = n3;
        } else {
            arrf2 = new float[n5];
            n2 = 0;
        }
        if (n8 == 0) {
            System.arraycopy((Object)arrf3, n, (Object)arrf2, n2, n5);
            n3 = 0;
            arrf3 = arrf2;
            n2 -= n;
            n = n3;
        } else {
            arrf = arrf2;
            n3 = 0;
            n = n2 - n;
            n2 = n3;
        }
        do {
            n5 = n2;
            if (n9 <= 1) break;
            n3 = 0;
            n2 = n8;
            for (n4 = 2; n4 <= n9; n4 += 2) {
                int n11 = arrn[n4];
                int n12 = arrn[n4 - 1];
                n7 = n12;
                for (n8 = n6 = arrn[n4 - 2]; n8 < n11; ++n8) {
                    if (!(n7 >= n11 || n6 < n12 && arrf3[n6 + n5] <= arrf3[n7 + n5])) {
                        arrf[n8 + n] = arrf3[n7 + n5];
                        ++n7;
                        continue;
                    }
                    arrf[n8 + n] = arrf3[n6 + n5];
                    ++n6;
                }
                arrn[++n3] = n11;
            }
            n4 = n3;
            if ((n9 & 1) != 0) {
                n4 = n10;
                n9 = arrn[n9 - 1];
                while (--n4 >= n9) {
                    arrf[n4 + n] = arrf3[n4 + n5];
                }
                n4 = n3 + 1;
                arrn[n4] = n10;
            }
            float[] arrf4 = arrf;
            arrf = arrf3;
            n3 = n;
            n = n5;
            arrf3 = arrf4;
            n8 = n2;
            n9 = n4;
            n2 = n3;
        } while (true);
    }

    private static void doSort(short[] arrs, int n, int n2, short[] arrs2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        short[] arrs3 = arrs;
        if (n2 - n < 286) {
            DualPivotQuicksort.sort(arrs3, n, n2, true);
            return;
        }
        int[] arrn = new int[68];
        int n8 = 0;
        arrn[0] = n;
        int n9 = n;
        while (n9 < n2) {
            block26 : {
                if (arrs3[n9] < arrs3[n9 + 1]) {
                    do {
                        n9 = n5 = n9 + 1;
                        if (n5 <= n2) {
                            n9 = n5;
                            if (arrs3[n5 - 1] <= arrs3[n5]) {
                                n9 = n5;
                                continue;
                            }
                        }
                        break block26;
                        break;
                    } while (true);
                }
                if (arrs3[n9] > arrs3[n9 + 1]) {
                    while (++n9 <= n2 && arrs3[n9 - 1] >= arrs3[n9]) {
                    }
                    n7 = arrn[n8] - 1;
                    n5 = n9;
                    while (++n7 < --n5) {
                        n6 = arrs3[n7];
                        arrs3[n7] = arrs3[n5];
                        arrs3[n5] = (short)n6;
                    }
                } else {
                    n5 = 33;
                    do {
                        n9 = n7 = n9 + 1;
                        if (n7 > n2) break block26;
                        n9 = n7;
                        if (arrs3[n7 - 1] != arrs3[n7]) break block26;
                        n6 = n5 - 1;
                        n9 = n7;
                        n5 = n6;
                    } while (n6 != 0);
                    DualPivotQuicksort.sort(arrs3, n, n2, true);
                    return;
                }
            }
            if (++n8 == 67) {
                DualPivotQuicksort.sort(arrs3, n, n2, true);
                return;
            }
            arrn[n8] = n9;
        }
        n9 = arrn[n8];
        int n10 = n2 + 1;
        if (n9 == n2) {
            n9 = n8 + 1;
            arrn[n9] = n10;
        } else {
            n9 = n8;
            if (n8 == 1) {
                return;
            }
        }
        n8 = 0;
        n2 = 1;
        do {
            n2 = n5 = n2 << 1;
            if (n5 >= n9) break;
            n8 = (byte)(n8 ^ 1);
        } while (true);
        n7 = n10 - n;
        if (arrs2 != null && n4 >= n7 && n3 + n7 <= arrs2.length) {
            n2 = n3;
        } else {
            arrs2 = new short[n7];
            n2 = 0;
        }
        if (n8 == 0) {
            System.arraycopy((Object)arrs3, n, (Object)arrs2, n2, n7);
            n3 = 0;
            arrs3 = arrs2;
            n2 -= n;
            n5 = n9;
            n4 = n7;
            n = n3;
        } else {
            arrs = arrs2;
            n3 = 0;
            n = n2 - n;
            n2 = n3;
            n4 = n7;
            n5 = n9;
        }
        do {
            n7 = n2;
            if (n5 <= 1) break;
            n3 = 0;
            n2 = n8;
            for (n9 = 2; n9 <= n5; n9 += 2) {
                int n11;
                int n12 = arrn[n9];
                n8 = arrn[n9 - 1];
                int n13 = n8;
                for (n6 = n11 = arrn[n9 - 2]; n6 < n12; ++n6) {
                    if (n13 < n12 && (n11 >= n8 || arrs3[n11 + n7] > arrs3[n13 + n7])) {
                        arrs[n6 + n] = arrs3[n13 + n7];
                        ++n13;
                        continue;
                    }
                    arrs[n6 + n] = arrs3[n11 + n7];
                    ++n11;
                }
                arrn[++n3] = n12;
            }
            n9 = n3;
            if ((n5 & 1) != 0) {
                n9 = n10;
                n8 = arrn[n5 - 1];
                while (--n9 >= n8) {
                    arrs[n9 + n] = arrs3[n9 + n7];
                }
                n9 = n3 + 1;
                arrn[n9] = n10;
            }
            short[] arrs4 = arrs;
            arrs = arrs3;
            n3 = n;
            n = n7;
            arrs3 = arrs4;
            n8 = n2;
            n5 = n9;
            n2 = n3;
        } while (true);
    }

    static void sort(byte[] arrby, int n, int n2) {
        if (n2 - n > 29) {
            int n3;
            int[] arrn = new int[256];
            int n4 = n - 1;
            while (++n4 <= n2) {
                n3 = arrby[n4] + 128;
                arrn[n3] = arrn[n3] + 1;
            }
            n4 = 256;
            ++n2;
            while (n2 > n) {
                int n5;
                while (arrn[--n4] == 0) {
                }
                byte by = (byte)(n4 - 128);
                int n6 = arrn[n4];
                n3 = n2;
                do {
                    n2 = n3 - 1;
                    arrby[n2] = by;
                    n6 = n5 = n6 - 1;
                    n3 = n2;
                } while (n5 > 0);
            }
        } else {
            int n7;
            int n8 = n7 = n;
            while (n7 < n2) {
                byte by = arrby[n7 + 1];
                do {
                    int n9 = n8;
                    if (by >= arrby[n8]) break;
                    arrby[n8 + 1] = arrby[n8];
                    n9 = n8 - 1;
                    if (n8 == n) break;
                    n8 = n9;
                } while (true);
                arrby[n9 + 1] = by;
                n8 = ++n7;
            }
        }
    }

    private static void sort(char[] arrc, int n, int n2, boolean bl) {
        char c;
        int n3 = n2;
        int n4 = n;
        int n5 = n3 - n4 + 1;
        if (n5 < 47) {
            int n6 = n4;
            if (bl) {
                n = n2 = n;
                while (n2 < n3) {
                    n5 = arrc[n2 + 1];
                    do {
                        n6 = n;
                        if (n5 >= arrc[n]) break;
                        arrc[n + 1] = arrc[n];
                        n6 = n - 1;
                        if (n == n4) break;
                        n = n6;
                    } while (true);
                    arrc[n6 + 1] = (char)n5;
                    n = ++n2;
                }
            } else {
                int n7;
                do {
                    if (n6 >= n3) {
                        return;
                    }
                    n6 = n2 = n6 + 1;
                } while (arrc[n2] >= arrc[n2 - 1]);
                n = n2;
                while ((n7 = n2 + 1) <= n3) {
                    n5 = arrc[n];
                    int n8 = arrc[n7];
                    n4 = n;
                    n6 = n5;
                    n2 = n8;
                    if (n5 < n8) {
                        n2 = n5;
                        n6 = arrc[n7];
                        n4 = n;
                    }
                    while (n6 < arrc[--n4]) {
                        arrc[n4 + 2] = arrc[n4];
                    }
                    n = n4 + 1;
                    arrc[n + 1] = (char)n6;
                    while (n2 < arrc[--n]) {
                        arrc[n + 1] = arrc[n];
                    }
                    arrc[n + 1] = (char)n2;
                    n = n2 = n7 + 1;
                }
                n = arrc[n3];
                while (n < arrc[--n3]) {
                    arrc[n3 + 1] = arrc[n3];
                }
                arrc[n3 + 1] = (char)n;
            }
            return;
        }
        int n9 = (n5 >> 3) + (n5 >> 6) + 1;
        int n10 = n4 + n3 >>> 1;
        int n11 = n10 - n9;
        int n12 = n11 - n9;
        int n13 = n10 + n9;
        int n14 = n13 + n9;
        if (arrc[n11] < arrc[n12]) {
            c = arrc[n11];
            arrc[n11] = arrc[n12];
            arrc[n12] = c;
        }
        if (arrc[n10] < arrc[n11]) {
            c = arrc[n10];
            arrc[n10] = arrc[n11];
            arrc[n11] = c;
            if (c < arrc[n12]) {
                arrc[n11] = arrc[n12];
                arrc[n12] = c;
            }
        }
        if (arrc[n13] < arrc[n10]) {
            c = arrc[n13];
            arrc[n13] = arrc[n10];
            arrc[n10] = c;
            if (c < arrc[n11]) {
                arrc[n10] = arrc[n11];
                arrc[n11] = c;
                if (c < arrc[n12]) {
                    arrc[n11] = arrc[n12];
                    arrc[n12] = c;
                }
            }
        }
        if (arrc[n14] < arrc[n13]) {
            c = arrc[n14];
            arrc[n14] = arrc[n13];
            arrc[n13] = c;
            if (c < arrc[n10]) {
                arrc[n13] = arrc[n10];
                arrc[n10] = c;
                if (c < arrc[n11]) {
                    arrc[n10] = arrc[n11];
                    arrc[n11] = c;
                    if (c < arrc[n12]) {
                        arrc[n11] = arrc[n12];
                        arrc[n12] = c;
                    }
                }
            }
        }
        if (arrc[n12] != arrc[n11] && arrc[n11] != arrc[n10] && arrc[n10] != arrc[n13] && arrc[n13] != arrc[n14]) {
            n10 = arrc[n11];
            c = arrc[n13];
            arrc[n11] = arrc[n4];
            arrc[n13] = arrc[n3];
            do {
                n13 = n2;
            } while (arrc[++n] < n10);
            while (arrc[--n13] > c) {
            }
            n11 = n - 1;
            n2 = n9;
            block9 : while ((n9 = n11 + 1) <= n13) {
                char c2 = arrc[n9];
                if (c2 < n10) {
                    arrc[n9] = arrc[n];
                    arrc[n] = c2;
                    ++n;
                } else if (c2 > c) {
                    while (arrc[n13] > c) {
                        n11 = n13 - 1;
                        if (n13 == n9) {
                            n13 = n11;
                            break block9;
                        }
                        n13 = n11;
                    }
                    if (arrc[n13] < n10) {
                        arrc[n9] = arrc[n];
                        arrc[n] = arrc[n13];
                        ++n;
                    } else {
                        arrc[n9] = arrc[n13];
                    }
                    arrc[n13] = c2;
                    --n13;
                }
                n11 = n9;
            }
            arrc[n4] = arrc[n - 1];
            arrc[n - 1] = (char)n10;
            arrc[n3] = arrc[n13 + 1];
            arrc[n13 + 1] = c;
            DualPivotQuicksort.sort(arrc, n4, n - 2, bl);
            DualPivotQuicksort.sort(arrc, n13 + 2, n3, false);
            n4 = n;
            n3 = n13;
            if (n < n12) {
                n4 = n;
                n3 = n13;
                if (n14 < n13) {
                    do {
                        n2 = n13;
                        if (arrc[n] != n10) break;
                        ++n;
                    } while (true);
                    while (arrc[n2] == c) {
                        --n2;
                    }
                    n4 = n - 1;
                    block13 : do {
                        n5 = n4 + 1;
                        n4 = n;
                        n3 = n2;
                        if (n5 > n2) break;
                        n4 = arrc[n5];
                        if (n4 == n10) {
                            arrc[n5] = arrc[n];
                            arrc[n] = (char)n4;
                            n13 = n + 1;
                            n3 = n2;
                        } else {
                            n13 = n;
                            n3 = n2;
                            if (n4 == c) {
                                while (arrc[n2] == c) {
                                    n13 = n2 - 1;
                                    if (n2 == n5) {
                                        n4 = n;
                                        n3 = n13;
                                        break block13;
                                    }
                                    n2 = n13;
                                }
                                if (arrc[n2] == n10) {
                                    arrc[n5] = arrc[n];
                                    arrc[n] = (char)n10;
                                    ++n;
                                } else {
                                    arrc[n5] = arrc[n2];
                                }
                                arrc[n2] = (char)n4;
                                n3 = n2 - 1;
                                n13 = n;
                            }
                        }
                        n4 = n5;
                        n = n13;
                        n2 = n3;
                    } while (true);
                }
            }
            DualPivotQuicksort.sort(arrc, n4, n3, false);
        } else {
            n11 = arrc[n10];
            for (n13 = n; n13 <= n2; ++n13) {
                if (arrc[n13] == n11) continue;
                n9 = arrc[n13];
                n5 = n2;
                if (n9 < n11) {
                    arrc[n13] = arrc[n];
                    arrc[n] = (char)n9;
                    ++n;
                    continue;
                }
                while (arrc[n5] > n11) {
                    --n5;
                }
                if (arrc[n5] < n11) {
                    arrc[n13] = arrc[n];
                    arrc[n] = arrc[n5];
                    ++n;
                } else {
                    arrc[n13] = (char)n11;
                }
                arrc[n5] = (char)n9;
                n2 = n5 - 1;
            }
            DualPivotQuicksort.sort(arrc, n4, n - 1, bl);
            DualPivotQuicksort.sort(arrc, n2 + 1, n3, false);
        }
    }

    static void sort(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4) {
        if (n2 - n > 3200) {
            arrc2 = new int[65536];
            n3 = n - 1;
            while (++n3 <= n2) {
                n4 = arrc[n3];
                arrc2[n4] = arrc2[n4] + '\u0001';
            }
            n3 = 65536;
            ++n2;
            while (n2 > n) {
                int n5;
                while (arrc2[--n3] == '\u0000') {
                }
                char c = (char)n3;
                int n6 = arrc2[n3];
                n4 = n2;
                do {
                    n2 = n4 - 1;
                    arrc[n2] = c;
                    n6 = n5 = n6 - '\u0001';
                    n4 = n2;
                } while (n5 > 0);
            }
        } else {
            DualPivotQuicksort.doSort(arrc, n, n2, arrc2, n3, n4);
        }
    }

    private static void sort(double[] arrd, int n, int n2, boolean bl) {
        double d;
        int n3 = n2;
        int n4 = n;
        int n5 = n3 - n4 + 1;
        if (n5 < 47) {
            n5 = n4;
            if (bl) {
                n2 = n;
                n5 = n;
                while (n5 < n3) {
                    double d2 = arrd[n5 + 1];
                    n = n2;
                    do {
                        n2 = n;
                        if (!(d2 < arrd[n])) break;
                        arrd[n + 1] = arrd[n];
                        n2 = n - 1;
                        if (n == n4) break;
                        n = n2;
                    } while (true);
                    arrd[n2 + 1] = d2;
                    n2 = ++n5;
                }
            } else {
                double d3;
                do {
                    if (n5 >= n3) {
                        return;
                    }
                    n5 = n2 = n5 + 1;
                } while (arrd[n2] >= arrd[n2 - 1]);
                n = n2;
                while ((n5 = n2 + 1) <= n3) {
                    double d4 = arrd[n];
                    double d5 = arrd[n5];
                    n2 = n;
                    double d6 = d4;
                    d3 = d5;
                    if (d4 < d5) {
                        d3 = d4;
                        d6 = arrd[n5];
                        n2 = n;
                    }
                    while (d6 < arrd[--n2]) {
                        arrd[n2 + 2] = arrd[n2];
                    }
                    n = n2 + 1;
                    arrd[n + 1] = d6;
                    while (d3 < arrd[--n]) {
                        arrd[n + 1] = arrd[n];
                    }
                    arrd[n + 1] = d3;
                    n = n2 = n5 + 1;
                }
                d3 = arrd[n3];
                while (d3 < arrd[--n3]) {
                    arrd[n3 + 1] = arrd[n3];
                }
                arrd[n3 + 1] = d3;
            }
            return;
        }
        int n6 = (n5 >> 3) + (n5 >> 6) + 1;
        int n7 = n4 + n3 >>> 1;
        int n8 = n7 - n6;
        int n9 = n8 - n6;
        n5 = n7 + n6;
        n6 = n5 + n6;
        if (arrd[n8] < arrd[n9]) {
            d = arrd[n8];
            arrd[n8] = arrd[n9];
            arrd[n9] = d;
        }
        if (arrd[n7] < arrd[n8]) {
            d = arrd[n7];
            arrd[n7] = arrd[n8];
            arrd[n8] = d;
            if (d < arrd[n9]) {
                arrd[n8] = arrd[n9];
                arrd[n9] = d;
            }
        }
        if (arrd[n5] < arrd[n7]) {
            d = arrd[n5];
            arrd[n5] = arrd[n7];
            arrd[n7] = d;
            if (d < arrd[n8]) {
                arrd[n7] = arrd[n8];
                arrd[n8] = d;
                if (d < arrd[n9]) {
                    arrd[n8] = arrd[n9];
                    arrd[n9] = d;
                }
            }
        }
        if (arrd[n6] < arrd[n5]) {
            d = arrd[n6];
            arrd[n6] = arrd[n5];
            arrd[n5] = d;
            if (d < arrd[n7]) {
                arrd[n5] = arrd[n7];
                arrd[n7] = d;
                if (d < arrd[n8]) {
                    arrd[n7] = arrd[n8];
                    arrd[n8] = d;
                    if (d < arrd[n9]) {
                        arrd[n8] = arrd[n9];
                        arrd[n9] = d;
                    }
                }
            }
        }
        if (arrd[n9] != arrd[n8] && arrd[n8] != arrd[n7] && arrd[n7] != arrd[n5] && arrd[n5] != arrd[n6]) {
            double d7;
            double d8 = arrd[n8];
            d = arrd[n5];
            arrd[n8] = arrd[n4];
            arrd[n5] = arrd[n3];
            do {
                n5 = n2;
            } while (arrd[++n] < d8);
            while (arrd[--n5] > d) {
            }
            n2 = n - 1;
            block9 : do {
                n7 = n2 + 1;
                n2 = n5;
                if (n7 > n5) break;
                d7 = arrd[n7];
                if (d7 < d8) {
                    arrd[n7] = arrd[n];
                    arrd[n] = d7;
                    n2 = n + 1;
                    n8 = n5;
                } else {
                    n2 = n;
                    n8 = n5;
                    if (d7 > d) {
                        while (arrd[n5] > d) {
                            n2 = n5 - 1;
                            if (n5 == n7) break block9;
                            n5 = n2;
                        }
                        if (arrd[n5] < d8) {
                            arrd[n7] = arrd[n];
                            arrd[n] = arrd[n5];
                            ++n;
                        } else {
                            arrd[n7] = arrd[n5];
                        }
                        arrd[n5] = d7;
                        n8 = n5 - 1;
                        n2 = n;
                    }
                }
                n = n2;
                n5 = n8;
                n2 = n7;
            } while (true);
            arrd[n4] = arrd[n - 1];
            arrd[n - 1] = d8;
            arrd[n3] = arrd[n2 + 1];
            arrd[n2 + 1] = d;
            DualPivotQuicksort.sort(arrd, n4, n - 2, bl);
            DualPivotQuicksort.sort(arrd, n2 + 2, n3, false);
            n3 = n;
            n5 = n2;
            if (n < n9) {
                n3 = n;
                n5 = n2;
                if (n6 < n2) {
                    do {
                        n5 = n2;
                        if (arrd[n] != d8) break;
                        ++n;
                    } while (true);
                    while (arrd[n5] == d) {
                        --n5;
                    }
                    n3 = n - 1;
                    n2 = n5;
                    block13 : do {
                        n4 = n3 + 1;
                        n3 = n;
                        n5 = n2;
                        if (n4 > n2) break;
                        d7 = arrd[n4];
                        if (d7 == d8) {
                            arrd[n4] = arrd[n];
                            arrd[n] = d7;
                            n5 = n + 1;
                            n3 = n2;
                        } else {
                            n5 = n;
                            n3 = n2;
                            if (d7 == d) {
                                while (arrd[n2] == d) {
                                    n5 = n2 - 1;
                                    if (n2 == n4) {
                                        n3 = n;
                                        break block13;
                                    }
                                    n2 = n5;
                                }
                                if (arrd[n2] == d8) {
                                    arrd[n4] = arrd[n];
                                    arrd[n] = arrd[n2];
                                    ++n;
                                } else {
                                    arrd[n4] = arrd[n2];
                                }
                                arrd[n2] = d7;
                                n3 = n2 - 1;
                                n5 = n;
                            }
                        }
                        n = n5;
                        n2 = n3;
                        n3 = n4;
                    } while (true);
                }
            }
            DualPivotQuicksort.sort(arrd, n3, n5, false);
        } else {
            double d9 = arrd[n7];
            for (n5 = n; n5 <= n2; ++n5) {
                if (arrd[n5] == d9) continue;
                d = arrd[n5];
                n8 = n2;
                if (d < d9) {
                    arrd[n5] = arrd[n];
                    arrd[n] = d;
                    ++n;
                    continue;
                }
                while (arrd[n8] > d9) {
                    --n8;
                }
                if (arrd[n8] < d9) {
                    arrd[n5] = arrd[n];
                    arrd[n] = arrd[n8];
                    ++n;
                } else {
                    arrd[n5] = arrd[n8];
                }
                arrd[n8] = d;
                n2 = n8 - 1;
            }
            DualPivotQuicksort.sort(arrd, n4, n - 1, bl);
            DualPivotQuicksort.sort(arrd, n2 + 1, n3, false);
        }
    }

    /*
     * Exception decompiling
     */
    static void sort(double[] var0, int var1_1, int var2_2, double[] var3_3, int var4_4, int var5_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: CONTINUE without a while class org.benf.cfr.reader.bytecode.analysis.parse.statement.IfStatement
        // org.benf.cfr.reader.bytecode.analysis.parse.statement.GotoStatement.getTargetStartBlock(GotoStatement.java:87)
        // org.benf.cfr.reader.bytecode.analysis.parse.statement.GotoStatement.getStructuredStatement(GotoStatement.java:101)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.getStructuredStatementPlaceHolder(Op03SimpleStatement.java:503)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:598)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void sort(float[] arrf, int n, int n2, boolean bl) {
        float f;
        int n3 = n2;
        int n4 = n;
        int n5 = n3 - n4 + 1;
        if (n5 < 47) {
            n5 = n4;
            if (bl) {
                n2 = n;
                n5 = n;
                while (n5 < n3) {
                    float f2 = arrf[n5 + 1];
                    n = n2;
                    do {
                        n2 = n;
                        if (!(f2 < arrf[n])) break;
                        arrf[n + 1] = arrf[n];
                        n2 = n - 1;
                        if (n == n4) break;
                        n = n2;
                    } while (true);
                    arrf[n2 + 1] = f2;
                    n2 = ++n5;
                }
            } else {
                float f3;
                do {
                    if (n5 >= n3) {
                        return;
                    }
                    n5 = n2 = n5 + 1;
                } while (arrf[n2] >= arrf[n2 - 1]);
                n = n2;
                while ((n5 = n2 + 1) <= n3) {
                    float f4 = arrf[n];
                    float f5 = arrf[n5];
                    n2 = n;
                    float f6 = f4;
                    f3 = f5;
                    if (f4 < f5) {
                        f3 = f4;
                        f6 = arrf[n5];
                        n2 = n;
                    }
                    while (f6 < arrf[--n2]) {
                        arrf[n2 + 2] = arrf[n2];
                    }
                    n = n2 + 1;
                    arrf[n + 1] = f6;
                    while (f3 < arrf[--n]) {
                        arrf[n + 1] = arrf[n];
                    }
                    arrf[n + 1] = f3;
                    n = n2 = n5 + 1;
                }
                f3 = arrf[n3];
                while (f3 < arrf[--n3]) {
                    arrf[n3 + 1] = arrf[n3];
                }
                arrf[n3 + 1] = f3;
            }
            return;
        }
        int n6 = (n5 >> 3) + (n5 >> 6) + 1;
        n5 = n4 + n3 >>> 1;
        int n7 = n5 - n6;
        int n8 = n7 - n6;
        int n9 = n5 + n6;
        n6 = n9 + n6;
        if (arrf[n7] < arrf[n8]) {
            f = arrf[n7];
            arrf[n7] = arrf[n8];
            arrf[n8] = f;
        }
        if (arrf[n5] < arrf[n7]) {
            f = arrf[n5];
            arrf[n5] = arrf[n7];
            arrf[n7] = f;
            if (f < arrf[n8]) {
                arrf[n7] = arrf[n8];
                arrf[n8] = f;
            }
        }
        if (arrf[n9] < arrf[n5]) {
            f = arrf[n9];
            arrf[n9] = arrf[n5];
            arrf[n5] = f;
            if (f < arrf[n7]) {
                arrf[n5] = arrf[n7];
                arrf[n7] = f;
                if (f < arrf[n8]) {
                    arrf[n7] = arrf[n8];
                    arrf[n8] = f;
                }
            }
        }
        if (arrf[n6] < arrf[n9]) {
            f = arrf[n6];
            arrf[n6] = arrf[n9];
            arrf[n9] = f;
            if (f < arrf[n5]) {
                arrf[n9] = arrf[n5];
                arrf[n5] = f;
                if (f < arrf[n7]) {
                    arrf[n5] = arrf[n7];
                    arrf[n7] = f;
                    if (f < arrf[n8]) {
                        arrf[n7] = arrf[n8];
                        arrf[n8] = f;
                    }
                }
            }
        }
        if (arrf[n8] != arrf[n7] && arrf[n7] != arrf[n5] && arrf[n5] != arrf[n9] && arrf[n9] != arrf[n6]) {
            float f7;
            float f8 = arrf[n7];
            f = arrf[n9];
            arrf[n7] = arrf[n4];
            arrf[n9] = arrf[n3];
            do {
                n5 = n2;
            } while (arrf[++n] < f8);
            while (arrf[n2 = n5 - 1] > f) {
                n5 = n2;
            }
            n7 = n - 1;
            block9 : do {
                n5 = n2;
                if (++n7 > n2) break;
                f7 = arrf[n7];
                if (f7 < f8) {
                    arrf[n7] = arrf[n];
                    arrf[n] = f7;
                    n5 = n + 1;
                    n9 = n2;
                } else {
                    n5 = n;
                    n9 = n2;
                    if (f7 > f) {
                        while (arrf[n2] > f) {
                            n5 = n2 - 1;
                            if (n2 == n7) break block9;
                            n2 = n5;
                        }
                        if (arrf[n2] < f8) {
                            arrf[n7] = arrf[n];
                            arrf[n] = arrf[n2];
                            ++n;
                        } else {
                            arrf[n7] = arrf[n2];
                        }
                        arrf[n2] = f7;
                        n9 = n2 - 1;
                        n5 = n;
                    }
                }
                n = n5;
                n2 = n9;
            } while (true);
            arrf[n4] = arrf[n - 1];
            arrf[n - 1] = f8;
            arrf[n3] = arrf[n5 + 1];
            arrf[n5 + 1] = f;
            DualPivotQuicksort.sort(arrf, n4, n - 2, bl);
            DualPivotQuicksort.sort(arrf, n5 + 2, n3, false);
            n4 = n;
            n3 = n5;
            if (n < n8) {
                n4 = n;
                n3 = n5;
                if (n6 < n5) {
                    do {
                        n2 = n5;
                        if (arrf[n] != f8) break;
                        ++n;
                    } while (true);
                    while (arrf[n2] == f) {
                        --n2;
                    }
                    n4 = n - 1;
                    block13 : do {
                        n9 = n4 + 1;
                        n4 = n;
                        n3 = n2;
                        if (n9 > n2) break;
                        f7 = arrf[n9];
                        if (f7 == f8) {
                            arrf[n9] = arrf[n];
                            arrf[n] = f7;
                            n5 = n + 1;
                            n3 = n2;
                        } else {
                            n5 = n;
                            n3 = n2;
                            if (f7 == f) {
                                while (arrf[n2] == f) {
                                    n5 = n2 - 1;
                                    if (n2 == n9) {
                                        n4 = n;
                                        n3 = n5;
                                        break block13;
                                    }
                                    n2 = n5;
                                }
                                if (arrf[n2] == f8) {
                                    arrf[n9] = arrf[n];
                                    arrf[n] = arrf[n2];
                                    ++n;
                                } else {
                                    arrf[n9] = arrf[n2];
                                }
                                arrf[n2] = f7;
                                n3 = n2 - 1;
                                n5 = n;
                            }
                        }
                        n4 = n9;
                        n = n5;
                        n2 = n3;
                    } while (true);
                }
            }
            DualPivotQuicksort.sort(arrf, n4, n3, false);
        } else {
            f = arrf[n5];
            for (n5 = n; n5 <= n2; ++n5) {
                if (arrf[n5] == f) continue;
                float f9 = arrf[n5];
                n9 = n2;
                if (f9 < f) {
                    arrf[n5] = arrf[n];
                    arrf[n] = f9;
                    ++n;
                    continue;
                }
                while (arrf[n9] > f) {
                    --n9;
                }
                if (arrf[n9] < f) {
                    arrf[n5] = arrf[n];
                    arrf[n] = arrf[n9];
                    ++n;
                } else {
                    arrf[n5] = arrf[n9];
                }
                arrf[n9] = f9;
                n2 = n9 - 1;
            }
            DualPivotQuicksort.sort(arrf, n4, n - 1, bl);
            DualPivotQuicksort.sort(arrf, n2 + 1, n3, false);
        }
    }

    static void sort(float[] arrf, int n, int n2, float[] arrf2, int n3, int n4) {
        float f;
        int n5;
        while (n <= n2 && Float.isNaN(arrf[n2])) {
            --n2;
        }
        int n6 = n5 = n2;
        while (--n6 >= n) {
            f = arrf[n6];
            n5 = n2;
            if (f != f) {
                arrf[n6] = arrf[n2];
                arrf[n2] = f;
                n5 = n2 - 1;
            }
            n2 = n5;
        }
        DualPivotQuicksort.doSort(arrf, n, n2, arrf2, n3, n4);
        n4 = n2;
        do {
            if (n >= n4) break;
            n3 = n + n4 >>> 1;
            if (arrf[n3] < 0.0f) {
                n = n3 + 1;
                n3 = n4;
            }
            n4 = n3;
        } while (true);
        for (n3 = n; n3 <= n2 && Float.floatToRawIntBits(arrf[n3]) < 0; ++n3) {
        }
        n4 = n3;
        n = n3 - 1;
        while (++n4 <= n2 && (f = arrf[n4]) == 0.0f) {
            n3 = n;
            if (Float.floatToRawIntBits(f) < 0) {
                arrf[n4] = 0.0f;
                n3 = n + 1;
                arrf[n3] = 0.0f;
            }
            n = n3;
        }
    }

    private static void sort(int[] arrn, int n, int n2, boolean bl) {
        int n3;
        int n4 = n2;
        int n5 = n;
        int n6 = n4 - n5 + 1;
        if (n6 < 47) {
            int n7 = n5;
            if (bl) {
                n = n2 = n;
                while (n2 < n4) {
                    n6 = arrn[n2 + 1];
                    do {
                        n7 = n;
                        if (n6 >= arrn[n]) break;
                        arrn[n + 1] = arrn[n];
                        n7 = n - 1;
                        if (n == n5) break;
                        n = n7;
                    } while (true);
                    arrn[n7 + 1] = n6;
                    n = ++n2;
                }
            } else {
                int n8;
                do {
                    if (n7 >= n4) {
                        return;
                    }
                    n7 = n2 = n7 + 1;
                } while (arrn[n2] >= arrn[n2 - 1]);
                n = n2;
                while ((n8 = n2 + 1) <= n4) {
                    n5 = arrn[n];
                    int n9 = arrn[n8];
                    n6 = n;
                    n7 = n5;
                    n2 = n9;
                    if (n5 < n9) {
                        n2 = n5;
                        n7 = arrn[n8];
                        n6 = n;
                    }
                    while (n7 < arrn[--n6]) {
                        arrn[n6 + 2] = arrn[n6];
                    }
                    n = n6 + 1;
                    arrn[n + 1] = n7;
                    while (n2 < arrn[--n]) {
                        arrn[n + 1] = arrn[n];
                    }
                    arrn[n + 1] = n2;
                    n = n2 = n8 + 1;
                }
                n = arrn[n4];
                while (n < arrn[--n4]) {
                    arrn[n4 + 1] = arrn[n4];
                }
                arrn[n4 + 1] = n;
            }
            return;
        }
        int n10 = (n6 >> 3) + (n6 >> 6) + 1;
        int n11 = n5 + n4 >>> 1;
        int n12 = n11 - n10;
        int n13 = n12 - n10;
        int n14 = n11 + n10;
        int n15 = n14 + n10;
        if (arrn[n12] < arrn[n13]) {
            n3 = arrn[n12];
            arrn[n12] = arrn[n13];
            arrn[n13] = n3;
        }
        if (arrn[n11] < arrn[n12]) {
            n3 = arrn[n11];
            arrn[n11] = arrn[n12];
            arrn[n12] = n3;
            if (n3 < arrn[n13]) {
                arrn[n12] = arrn[n13];
                arrn[n13] = n3;
            }
        }
        if (arrn[n14] < arrn[n11]) {
            n3 = arrn[n14];
            arrn[n14] = arrn[n11];
            arrn[n11] = n3;
            if (n3 < arrn[n12]) {
                arrn[n11] = arrn[n12];
                arrn[n12] = n3;
                if (n3 < arrn[n13]) {
                    arrn[n12] = arrn[n13];
                    arrn[n13] = n3;
                }
            }
        }
        if (arrn[n15] < arrn[n14]) {
            n3 = arrn[n15];
            arrn[n15] = arrn[n14];
            arrn[n14] = n3;
            if (n3 < arrn[n11]) {
                arrn[n14] = arrn[n11];
                arrn[n11] = n3;
                if (n3 < arrn[n12]) {
                    arrn[n11] = arrn[n12];
                    arrn[n12] = n3;
                    if (n3 < arrn[n13]) {
                        arrn[n12] = arrn[n13];
                        arrn[n13] = n3;
                    }
                }
            }
        }
        if (arrn[n13] != arrn[n12] && arrn[n12] != arrn[n11] && arrn[n11] != arrn[n14] && arrn[n14] != arrn[n15]) {
            n3 = arrn[n12];
            n11 = arrn[n14];
            arrn[n12] = arrn[n5];
            arrn[n14] = arrn[n4];
            do {
                n12 = n2;
            } while (arrn[++n] < n3);
            while (arrn[--n12] > n11) {
            }
            n14 = n - 1;
            n2 = n10;
            block9 : while ((n10 = n14 + 1) <= n12) {
                int n16 = arrn[n10];
                if (n16 < n3) {
                    arrn[n10] = arrn[n];
                    arrn[n] = n16;
                    ++n;
                } else if (n16 > n11) {
                    while (arrn[n12] > n11) {
                        n14 = n12 - 1;
                        if (n12 == n10) {
                            n12 = n14;
                            break block9;
                        }
                        n12 = n14;
                    }
                    if (arrn[n12] < n3) {
                        arrn[n10] = arrn[n];
                        arrn[n] = arrn[n12];
                        ++n;
                    } else {
                        arrn[n10] = arrn[n12];
                    }
                    arrn[n12] = n16;
                    --n12;
                }
                n14 = n10;
            }
            arrn[n5] = arrn[n - 1];
            arrn[n - 1] = n3;
            arrn[n4] = arrn[n12 + 1];
            arrn[n12 + 1] = n11;
            DualPivotQuicksort.sort(arrn, n5, n - 2, bl);
            DualPivotQuicksort.sort(arrn, n12 + 2, n4, false);
            n5 = n;
            n4 = n12;
            if (n < n13) {
                n5 = n;
                n4 = n12;
                if (n15 < n12) {
                    do {
                        n2 = n12;
                        if (arrn[n] != n3) break;
                        ++n;
                    } while (true);
                    while (arrn[n2] == n11) {
                        --n2;
                    }
                    n5 = n - 1;
                    block13 : do {
                        n6 = n5 + 1;
                        n5 = n;
                        n4 = n2;
                        if (n6 > n2) break;
                        n5 = arrn[n6];
                        if (n5 == n3) {
                            arrn[n6] = arrn[n];
                            arrn[n] = n5;
                            n12 = n + 1;
                            n4 = n2;
                        } else {
                            n12 = n;
                            n4 = n2;
                            if (n5 == n11) {
                                while (arrn[n2] == n11) {
                                    n12 = n2 - 1;
                                    if (n2 == n6) {
                                        n5 = n;
                                        n4 = n12;
                                        break block13;
                                    }
                                    n2 = n12;
                                }
                                if (arrn[n2] == n3) {
                                    arrn[n6] = arrn[n];
                                    arrn[n] = n3;
                                    ++n;
                                } else {
                                    arrn[n6] = arrn[n2];
                                }
                                arrn[n2] = n5;
                                n4 = n2 - 1;
                                n12 = n;
                            }
                        }
                        n5 = n6;
                        n = n12;
                        n2 = n4;
                    } while (true);
                }
            }
            DualPivotQuicksort.sort(arrn, n5, n4, false);
        } else {
            n14 = arrn[n11];
            for (n12 = n; n12 <= n2; ++n12) {
                if (arrn[n12] == n14) continue;
                n10 = arrn[n12];
                n6 = n2;
                if (n10 < n14) {
                    arrn[n12] = arrn[n];
                    arrn[n] = n10;
                    ++n;
                    continue;
                }
                while (arrn[n6] > n14) {
                    --n6;
                }
                if (arrn[n6] < n14) {
                    arrn[n12] = arrn[n];
                    arrn[n] = arrn[n6];
                    ++n;
                } else {
                    arrn[n12] = n14;
                }
                arrn[n6] = n10;
                n2 = n6 - 1;
            }
            DualPivotQuicksort.sort(arrn, n5, n - 1, bl);
            DualPivotQuicksort.sort(arrn, n2 + 1, n4, false);
        }
    }

    static void sort(int[] arrn, int n, int n2, int[] arrn2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int[] arrn3 = arrn;
        if (n2 - n < 286) {
            DualPivotQuicksort.sort(arrn3, n, n2, true);
            return;
        }
        int[] arrn4 = new int[68];
        int n8 = 0;
        arrn4[0] = n;
        int n9 = n;
        while (n9 < n2) {
            block26 : {
                if (arrn3[n9] < arrn3[n9 + 1]) {
                    do {
                        n9 = n5 = n9 + 1;
                        if (n5 <= n2) {
                            n9 = n5;
                            if (arrn3[n5 - 1] <= arrn3[n5]) {
                                n9 = n5;
                                continue;
                            }
                        }
                        break block26;
                        break;
                    } while (true);
                }
                if (arrn3[n9] > arrn3[n9 + 1]) {
                    while (++n9 <= n2 && arrn3[n9 - 1] >= arrn3[n9]) {
                    }
                    n7 = arrn4[n8] - 1;
                    n5 = n9;
                    while (++n7 < --n5) {
                        n6 = arrn3[n7];
                        arrn3[n7] = arrn3[n5];
                        arrn3[n5] = n6;
                    }
                } else {
                    n5 = 33;
                    do {
                        n9 = n7 = n9 + 1;
                        if (n7 > n2) break block26;
                        n9 = n7;
                        if (arrn3[n7 - 1] != arrn3[n7]) break block26;
                        n6 = n5 - 1;
                        n9 = n7;
                        n5 = n6;
                    } while (n6 != 0);
                    DualPivotQuicksort.sort(arrn3, n, n2, true);
                    return;
                }
            }
            if (++n8 == 67) {
                DualPivotQuicksort.sort(arrn3, n, n2, true);
                return;
            }
            arrn4[n8] = n9;
        }
        n9 = arrn4[n8];
        int n10 = n2 + 1;
        if (n9 == n2) {
            n9 = n8 + 1;
            arrn4[n9] = n10;
        } else {
            n9 = n8;
            if (n8 == 1) {
                return;
            }
        }
        n8 = 0;
        n2 = 1;
        do {
            n2 = n5 = n2 << 1;
            if (n5 >= n9) break;
            n8 = (byte)(n8 ^ 1);
        } while (true);
        n7 = n10 - n;
        if (arrn2 != null && n4 >= n7 && n3 + n7 <= arrn2.length) {
            n2 = n3;
        } else {
            arrn2 = new int[n7];
            n2 = 0;
        }
        if (n8 == 0) {
            System.arraycopy((Object)arrn3, n, (Object)arrn2, n2, n7);
            n3 = 0;
            arrn3 = arrn2;
            n2 -= n;
            n5 = n9;
            n4 = n7;
            n = n3;
        } else {
            arrn = arrn2;
            n3 = 0;
            n = n2 - n;
            n2 = n3;
            n4 = n7;
            n5 = n9;
        }
        do {
            n7 = n2;
            if (n5 <= 1) break;
            n3 = 0;
            n2 = n8;
            for (n9 = 2; n9 <= n5; n9 += 2) {
                int n11;
                int n12 = arrn4[n9];
                n8 = arrn4[n9 - 1];
                int n13 = n8;
                for (n6 = n11 = arrn4[n9 - 2]; n6 < n12; ++n6) {
                    if (n13 < n12 && (n11 >= n8 || arrn3[n11 + n7] > arrn3[n13 + n7])) {
                        arrn[n6 + n] = arrn3[n13 + n7];
                        ++n13;
                        continue;
                    }
                    arrn[n6 + n] = arrn3[n11 + n7];
                    ++n11;
                }
                arrn4[++n3] = n12;
            }
            n9 = n3;
            if ((n5 & 1) != 0) {
                n9 = n10;
                n8 = arrn4[n5 - 1];
                while (--n9 >= n8) {
                    arrn[n9 + n] = arrn3[n9 + n7];
                }
                n9 = n3 + 1;
                arrn4[n9] = n10;
            }
            int[] arrn5 = arrn;
            arrn = arrn3;
            n3 = n;
            n = n7;
            arrn3 = arrn5;
            n8 = n2;
            n5 = n9;
            n2 = n3;
        } while (true);
    }

    private static void sort(long[] arrl, int n, int n2, boolean bl) {
        long l;
        int n3 = n2;
        int n4 = n;
        int n5 = n3 - n4 + 1;
        if (n5 < 47) {
            n5 = n4;
            if (bl) {
                n2 = n;
                n5 = n;
                while (n5 < n3) {
                    long l2 = arrl[n5 + 1];
                    n = n2;
                    do {
                        n2 = n;
                        if (l2 >= arrl[n]) break;
                        arrl[n + 1] = arrl[n];
                        n2 = n - 1;
                        if (n == n4) break;
                        n = n2;
                    } while (true);
                    arrl[n2 + 1] = l2;
                    n2 = ++n5;
                }
            } else {
                long l3;
                do {
                    if (n5 >= n3) {
                        return;
                    }
                    n5 = n2 = n5 + 1;
                } while (arrl[n2] >= arrl[n2 - 1]);
                n = n2;
                while ((n5 = n2 + 1) <= n3) {
                    long l4 = arrl[n];
                    long l5 = arrl[n5];
                    n2 = n;
                    long l6 = l4;
                    l3 = l5;
                    if (l4 < l5) {
                        l3 = l4;
                        l6 = arrl[n5];
                        n2 = n;
                    }
                    while (l6 < arrl[--n2]) {
                        arrl[n2 + 2] = arrl[n2];
                    }
                    n = n2 + 1;
                    arrl[n + 1] = l6;
                    while (l3 < arrl[--n]) {
                        arrl[n + 1] = arrl[n];
                    }
                    arrl[n + 1] = l3;
                    n = n2 = n5 + 1;
                }
                l3 = arrl[n3];
                while (l3 < arrl[--n3]) {
                    arrl[n3 + 1] = arrl[n3];
                }
                arrl[n3 + 1] = l3;
            }
            return;
        }
        int n6 = (n5 >> 3) + (n5 >> 6) + 1;
        int n7 = n4 + n3 >>> 1;
        n5 = n7 - n6;
        int n8 = n5 - n6;
        int n9 = n7 + n6;
        n6 = n9 + n6;
        if (arrl[n5] < arrl[n8]) {
            l = arrl[n5];
            arrl[n5] = arrl[n8];
            arrl[n8] = l;
        }
        if (arrl[n7] < arrl[n5]) {
            l = arrl[n7];
            arrl[n7] = arrl[n5];
            arrl[n5] = l;
            if (l < arrl[n8]) {
                arrl[n5] = arrl[n8];
                arrl[n8] = l;
            }
        }
        if (arrl[n9] < arrl[n7]) {
            l = arrl[n9];
            arrl[n9] = arrl[n7];
            arrl[n7] = l;
            if (l < arrl[n5]) {
                arrl[n7] = arrl[n5];
                arrl[n5] = l;
                if (l < arrl[n8]) {
                    arrl[n5] = arrl[n8];
                    arrl[n8] = l;
                }
            }
        }
        if (arrl[n6] < arrl[n9]) {
            l = arrl[n6];
            arrl[n6] = arrl[n9];
            arrl[n9] = l;
            if (l < arrl[n7]) {
                arrl[n9] = arrl[n7];
                arrl[n7] = l;
                if (l < arrl[n5]) {
                    arrl[n7] = arrl[n5];
                    arrl[n5] = l;
                    if (l < arrl[n8]) {
                        arrl[n5] = arrl[n8];
                        arrl[n8] = l;
                    }
                }
            }
        }
        if (arrl[n8] != arrl[n5] && arrl[n5] != arrl[n7] && arrl[n7] != arrl[n9] && arrl[n9] != arrl[n6]) {
            long l7;
            long l8 = arrl[n5];
            l = arrl[n9];
            arrl[n5] = arrl[n4];
            arrl[n9] = arrl[n3];
            do {
                n5 = n2;
            } while (arrl[++n] < l8);
            while (arrl[n2 = n5 - 1] > l) {
                n5 = n2;
            }
            n5 = n - 1;
            block9 : do {
                n7 = n5 + 1;
                n5 = n2;
                if (n7 > n2) break;
                l7 = arrl[n7];
                if (l7 < l8) {
                    arrl[n7] = arrl[n];
                    arrl[n] = l7;
                    n5 = n + 1;
                    n9 = n2;
                } else {
                    n5 = n;
                    n9 = n2;
                    if (l7 > l) {
                        while (arrl[n2] > l) {
                            n5 = n2 - 1;
                            if (n2 == n7) break block9;
                            n2 = n5;
                        }
                        if (arrl[n2] < l8) {
                            arrl[n7] = arrl[n];
                            arrl[n] = arrl[n2];
                            ++n;
                        } else {
                            arrl[n7] = arrl[n2];
                        }
                        arrl[n2] = l7;
                        n9 = n2 - 1;
                        n5 = n;
                    }
                }
                n = n5;
                n2 = n9;
                n5 = n7;
            } while (true);
            arrl[n4] = arrl[n - 1];
            arrl[n - 1] = l8;
            arrl[n3] = arrl[n5 + 1];
            arrl[n5 + 1] = l;
            DualPivotQuicksort.sort(arrl, n4, n - 2, bl);
            DualPivotQuicksort.sort(arrl, n5 + 2, n3, false);
            n4 = n;
            n3 = n5;
            if (n < n8) {
                n4 = n;
                n3 = n5;
                if (n6 < n5) {
                    do {
                        n2 = n5;
                        if (arrl[n] != l8) break;
                        ++n;
                    } while (true);
                    while (arrl[n2] == l) {
                        --n2;
                    }
                    n5 = n - 1;
                    block13 : do {
                        n9 = n5 + 1;
                        n4 = n;
                        n3 = n2;
                        if (n9 > n2) break;
                        l7 = arrl[n9];
                        if (l7 == l8) {
                            arrl[n9] = arrl[n];
                            arrl[n] = l7;
                            n5 = n + 1;
                            n3 = n2;
                        } else {
                            n5 = n;
                            n3 = n2;
                            if (l7 == l) {
                                while (arrl[n2] == l) {
                                    n5 = n2 - 1;
                                    if (n2 == n9) {
                                        n4 = n;
                                        n3 = n5;
                                        break block13;
                                    }
                                    n2 = n5;
                                }
                                if (arrl[n2] == l8) {
                                    arrl[n9] = arrl[n];
                                    arrl[n] = l8;
                                    ++n;
                                } else {
                                    arrl[n9] = arrl[n2];
                                }
                                arrl[n2] = l7;
                                n3 = n2 - 1;
                                n5 = n;
                            }
                        }
                        n = n5;
                        n2 = n3;
                        n5 = n9;
                    } while (true);
                }
            }
            DualPivotQuicksort.sort(arrl, n4, n3, false);
        } else {
            l = arrl[n7];
            for (n5 = n; n5 <= n2; ++n5) {
                if (arrl[n5] == l) continue;
                long l9 = arrl[n5];
                n9 = n2;
                if (l9 < l) {
                    arrl[n5] = arrl[n];
                    arrl[n] = l9;
                    ++n;
                    continue;
                }
                while (arrl[n9] > l) {
                    --n9;
                }
                if (arrl[n9] < l) {
                    arrl[n5] = arrl[n];
                    arrl[n] = arrl[n9];
                    ++n;
                } else {
                    arrl[n5] = l;
                }
                arrl[n9] = l9;
                n2 = n9 - 1;
            }
            DualPivotQuicksort.sort(arrl, n4, n - 1, bl);
            DualPivotQuicksort.sort(arrl, n2 + 1, n3, false);
        }
    }

    static void sort(long[] arrl, int n, int n2, long[] arrl2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        long[] arrl3 = arrl;
        if (n2 - n < 286) {
            DualPivotQuicksort.sort(arrl3, n, n2, true);
            return;
        }
        int[] arrn = new int[68];
        int n8 = 0;
        arrn[0] = n;
        int n9 = n;
        while (n9 < n2) {
            block26 : {
                if (arrl3[n9] < arrl3[n9 + 1]) {
                    do {
                        n9 = n5 = n9 + 1;
                        if (n5 <= n2) {
                            n9 = n5;
                            if (arrl3[n5 - 1] <= arrl3[n5]) {
                                n9 = n5;
                                continue;
                            }
                        }
                        break block26;
                        break;
                    } while (true);
                }
                if (arrl3[n9] > arrl3[n9 + 1]) {
                    while (++n9 <= n2 && arrl3[n9 - 1] >= arrl3[n9]) {
                    }
                    n6 = arrn[n8] - 1;
                    n5 = n9;
                    while (++n6 < --n5) {
                        long l = arrl3[n6];
                        arrl3[n6] = arrl3[n5];
                        arrl3[n5] = l;
                    }
                } else {
                    n5 = 33;
                    do {
                        n9 = n6 = n9 + 1;
                        if (n6 > n2) break block26;
                        n9 = n6;
                        if (arrl3[n6 - 1] != arrl3[n6]) break block26;
                        n7 = n5 - 1;
                        n9 = n6;
                        n5 = n7;
                    } while (n7 != 0);
                    DualPivotQuicksort.sort(arrl3, n, n2, true);
                    return;
                }
            }
            if (++n8 == 67) {
                DualPivotQuicksort.sort(arrl3, n, n2, true);
                return;
            }
            arrn[n8] = n9;
        }
        n9 = arrn[n8];
        int n10 = n2 + 1;
        if (n9 == n2) {
            n9 = n8 + 1;
            arrn[n9] = n10;
        } else {
            n9 = n8;
            if (n8 == 1) {
                return;
            }
        }
        n8 = 0;
        n2 = 1;
        do {
            n2 = n5 = n2 << 1;
            if (n5 >= n9) break;
            n8 = (byte)(n8 ^ 1);
        } while (true);
        n5 = n10 - n;
        if (arrl2 != null && n4 >= n5 && n3 + n5 <= arrl2.length) {
            n2 = n3;
        } else {
            arrl2 = new long[n5];
            n2 = 0;
        }
        if (n8 == 0) {
            System.arraycopy((Object)arrl3, n, (Object)arrl2, n2, n5);
            n3 = 0;
            arrl3 = arrl2;
            n2 -= n;
            n = n3;
        } else {
            arrl = arrl2;
            n3 = 0;
            n = n2 - n;
            n2 = n3;
        }
        do {
            n5 = n2;
            if (n9 <= 1) break;
            n3 = 0;
            n2 = n8;
            for (n4 = 2; n4 <= n9; n4 += 2) {
                int n11 = arrn[n4];
                int n12 = arrn[n4 - 1];
                n6 = n12;
                for (n8 = n7 = arrn[n4 - 2]; n8 < n11; ++n8) {
                    if (n6 < n11 && (n7 >= n12 || arrl3[n7 + n5] > arrl3[n6 + n5])) {
                        arrl[n8 + n] = arrl3[n6 + n5];
                        ++n6;
                        continue;
                    }
                    arrl[n8 + n] = arrl3[n7 + n5];
                    ++n7;
                }
                arrn[++n3] = n11;
            }
            n4 = n3;
            if ((n9 & 1) != 0) {
                n4 = n10;
                n9 = arrn[n9 - 1];
                while (--n4 >= n9) {
                    arrl[n4 + n] = arrl3[n4 + n5];
                }
                n4 = n3 + 1;
                arrn[n4] = n10;
            }
            long[] arrl4 = arrl;
            arrl = arrl3;
            n3 = n;
            n = n5;
            arrl3 = arrl4;
            n8 = n2;
            n9 = n4;
            n2 = n3;
        } while (true);
    }

    private static void sort(short[] arrs, int n, int n2, boolean bl) {
        short s;
        int n3 = n2;
        int n4 = n;
        int n5 = n3 - n4 + 1;
        if (n5 < 47) {
            int n6 = n4;
            if (bl) {
                n = n2 = n;
                while (n2 < n3) {
                    n5 = arrs[n2 + 1];
                    do {
                        n6 = n;
                        if (n5 >= arrs[n]) break;
                        arrs[n + 1] = arrs[n];
                        n6 = n - 1;
                        if (n == n4) break;
                        n = n6;
                    } while (true);
                    arrs[n6 + 1] = (short)n5;
                    n = ++n2;
                }
            } else {
                int n7;
                do {
                    if (n6 >= n3) {
                        return;
                    }
                    n6 = n2 = n6 + 1;
                } while (arrs[n2] >= arrs[n2 - 1]);
                n = n2;
                while ((n7 = n2 + 1) <= n3) {
                    n5 = arrs[n];
                    int n8 = arrs[n7];
                    n4 = n;
                    n6 = n5;
                    n2 = n8;
                    if (n5 < n8) {
                        n2 = n5;
                        n6 = arrs[n7];
                        n4 = n;
                    }
                    while (n6 < arrs[--n4]) {
                        arrs[n4 + 2] = arrs[n4];
                    }
                    n = n4 + 1;
                    arrs[n + 1] = (short)n6;
                    while (n2 < arrs[--n]) {
                        arrs[n + 1] = arrs[n];
                    }
                    arrs[n + 1] = (short)n2;
                    n = n2 = n7 + 1;
                }
                n = arrs[n3];
                while (n < arrs[--n3]) {
                    arrs[n3 + 1] = arrs[n3];
                }
                arrs[n3 + 1] = (short)n;
            }
            return;
        }
        int n9 = (n5 >> 3) + (n5 >> 6) + 1;
        int n10 = n4 + n3 >>> 1;
        int n11 = n10 - n9;
        int n12 = n11 - n9;
        int n13 = n10 + n9;
        int n14 = n13 + n9;
        if (arrs[n11] < arrs[n12]) {
            s = arrs[n11];
            arrs[n11] = arrs[n12];
            arrs[n12] = s;
        }
        if (arrs[n10] < arrs[n11]) {
            s = arrs[n10];
            arrs[n10] = arrs[n11];
            arrs[n11] = s;
            if (s < arrs[n12]) {
                arrs[n11] = arrs[n12];
                arrs[n12] = s;
            }
        }
        if (arrs[n13] < arrs[n10]) {
            s = arrs[n13];
            arrs[n13] = arrs[n10];
            arrs[n10] = s;
            if (s < arrs[n11]) {
                arrs[n10] = arrs[n11];
                arrs[n11] = s;
                if (s < arrs[n12]) {
                    arrs[n11] = arrs[n12];
                    arrs[n12] = s;
                }
            }
        }
        if (arrs[n14] < arrs[n13]) {
            s = arrs[n14];
            arrs[n14] = arrs[n13];
            arrs[n13] = s;
            if (s < arrs[n10]) {
                arrs[n13] = arrs[n10];
                arrs[n10] = s;
                if (s < arrs[n11]) {
                    arrs[n10] = arrs[n11];
                    arrs[n11] = s;
                    if (s < arrs[n12]) {
                        arrs[n11] = arrs[n12];
                        arrs[n12] = s;
                    }
                }
            }
        }
        if (arrs[n12] != arrs[n11] && arrs[n11] != arrs[n10] && arrs[n10] != arrs[n13] && arrs[n13] != arrs[n14]) {
            n10 = arrs[n11];
            s = arrs[n13];
            arrs[n11] = arrs[n4];
            arrs[n13] = arrs[n3];
            do {
                n13 = n2;
            } while (arrs[++n] < n10);
            while (arrs[--n13] > s) {
            }
            n11 = n - 1;
            n2 = n9;
            block9 : while ((n9 = n11 + 1) <= n13) {
                short s2 = arrs[n9];
                if (s2 < n10) {
                    arrs[n9] = arrs[n];
                    arrs[n] = s2;
                    ++n;
                } else if (s2 > s) {
                    while (arrs[n13] > s) {
                        n11 = n13 - 1;
                        if (n13 == n9) {
                            n13 = n11;
                            break block9;
                        }
                        n13 = n11;
                    }
                    if (arrs[n13] < n10) {
                        arrs[n9] = arrs[n];
                        arrs[n] = arrs[n13];
                        ++n;
                    } else {
                        arrs[n9] = arrs[n13];
                    }
                    arrs[n13] = s2;
                    --n13;
                }
                n11 = n9;
            }
            arrs[n4] = arrs[n - 1];
            arrs[n - 1] = (short)n10;
            arrs[n3] = arrs[n13 + 1];
            arrs[n13 + 1] = s;
            DualPivotQuicksort.sort(arrs, n4, n - 2, bl);
            DualPivotQuicksort.sort(arrs, n13 + 2, n3, false);
            n4 = n;
            n3 = n13;
            if (n < n12) {
                n4 = n;
                n3 = n13;
                if (n14 < n13) {
                    do {
                        n2 = n13;
                        if (arrs[n] != n10) break;
                        ++n;
                    } while (true);
                    while (arrs[n2] == s) {
                        --n2;
                    }
                    n4 = n - 1;
                    block13 : do {
                        n5 = n4 + 1;
                        n4 = n;
                        n3 = n2;
                        if (n5 > n2) break;
                        n4 = arrs[n5];
                        if (n4 == n10) {
                            arrs[n5] = arrs[n];
                            arrs[n] = (short)n4;
                            n13 = n + 1;
                            n3 = n2;
                        } else {
                            n13 = n;
                            n3 = n2;
                            if (n4 == s) {
                                while (arrs[n2] == s) {
                                    n13 = n2 - 1;
                                    if (n2 == n5) {
                                        n4 = n;
                                        n3 = n13;
                                        break block13;
                                    }
                                    n2 = n13;
                                }
                                if (arrs[n2] == n10) {
                                    arrs[n5] = arrs[n];
                                    arrs[n] = (short)n10;
                                    ++n;
                                } else {
                                    arrs[n5] = arrs[n2];
                                }
                                arrs[n2] = (short)n4;
                                n3 = n2 - 1;
                                n13 = n;
                            }
                        }
                        n4 = n5;
                        n = n13;
                        n2 = n3;
                    } while (true);
                }
            }
            DualPivotQuicksort.sort(arrs, n4, n3, false);
        } else {
            n11 = arrs[n10];
            for (n13 = n; n13 <= n2; ++n13) {
                if (arrs[n13] == n11) continue;
                n9 = arrs[n13];
                n5 = n2;
                if (n9 < n11) {
                    arrs[n13] = arrs[n];
                    arrs[n] = (short)n9;
                    ++n;
                    continue;
                }
                while (arrs[n5] > n11) {
                    --n5;
                }
                if (arrs[n5] < n11) {
                    arrs[n13] = arrs[n];
                    arrs[n] = arrs[n5];
                    ++n;
                } else {
                    arrs[n13] = (short)n11;
                }
                arrs[n5] = (short)n9;
                n2 = n5 - 1;
            }
            DualPivotQuicksort.sort(arrs, n4, n - 1, bl);
            DualPivotQuicksort.sort(arrs, n2 + 1, n3, false);
        }
    }

    static void sort(short[] arrs, int n, int n2, short[] arrs2, int n3, int n4) {
        if (n2 - n > 3200) {
            arrs2 = new int[65536];
            n3 = n - 1;
            while (++n3 <= n2) {
                n4 = arrs[n3] + 32768;
                arrs2[n4] = arrs2[n4] + 1;
            }
            n3 = 65536;
            ++n2;
            while (n2 > n) {
                int n5;
                while (arrs2[--n3] == 0) {
                }
                short s = (short)(n3 - 32768);
                int n6 = arrs2[n3];
                n4 = n2;
                do {
                    n2 = n4 - 1;
                    arrs[n2] = s;
                    n6 = n5 = n6 - 1;
                    n4 = n2;
                } while (n5 > 0);
            }
        } else {
            DualPivotQuicksort.doSort(arrs, n, n2, arrs2, n3, n4);
        }
    }
}

