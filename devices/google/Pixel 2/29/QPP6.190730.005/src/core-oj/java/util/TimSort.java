/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.lang.reflect.Array;
import java.util.Comparator;

class TimSort<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
    private static final int MIN_GALLOP = 7;
    private static final int MIN_MERGE = 32;
    private final T[] a;
    private final Comparator<? super T> c;
    private int minGallop = 7;
    private final int[] runBase;
    private final int[] runLen;
    private int stackSize = 0;
    private T[] tmp;
    private int tmpBase;
    private int tmpLen;

    private TimSort(T[] arrT, Comparator<? super T> comparator, T[] arrT2, int n, int n2) {
        this.a = arrT;
        this.c = comparator;
        int n3 = arrT.length;
        int n4 = n3 < 512 ? n3 >>> 1 : 256;
        if (arrT2 != null && n2 >= n4 && n + n4 <= arrT2.length) {
            this.tmp = arrT2;
            this.tmpBase = n;
            this.tmpLen = n2;
        } else {
            this.tmp = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n4);
            this.tmpBase = 0;
            this.tmpLen = n4;
        }
        n = n3 < 120 ? 5 : (n3 < 1542 ? 10 : (n3 < 119151 ? 24 : 49));
        this.runBase = new int[n];
        this.runLen = new int[n];
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static <T> void binarySort(T[] var0, int var1_1, int var2_2, int var3_3, Comparator<? super T> var4_4) {
        var5_5 = var3_3;
        if (var3_3 == var1_1) {
            var5_5 = var3_3 + 1;
        }
        while (var5_5 < var2_2) {
            var6_6 = var0[var5_5];
            var3_3 = var1_1;
            var7_7 = var5_5;
            while (var3_3 < var7_7) {
                var8_8 = var3_3 + var7_7 >>> 1;
                if (var4_4.compare(var6_6, var0[var8_8]) < 0) {
                    var7_7 = var8_8;
                    continue;
                }
                var3_3 = var8_8 + 1;
            }
            var7_7 = var5_5 - var3_3;
            if (var7_7 == 1) ** GOTO lbl21
            if (var7_7 != 2) {
                System.arraycopy(var0, var3_3, var0, var3_3 + 1, var7_7);
            } else {
                var0[var3_3 + 2] = var0[var3_3 + 1];
lbl21: // 2 sources:
                var0[var3_3 + 1] = var0[var3_3];
            }
            var0[var3_3] = var6_6;
            ++var5_5;
        }
    }

    private static <T> int countRunAndMakeAscending(T[] arrT, int n, int n2, Comparator<? super T> comparator) {
        int n3;
        int n4 = n + 1;
        if (n4 == n2) {
            return 1;
        }
        int n5 = n3 = n4 + 1;
        if (comparator.compare(arrT[n4], arrT[n]) < 0) {
            for (n5 = n3; n5 < n2 && comparator.compare(arrT[n5], arrT[n5 - 1]) < 0; ++n5) {
            }
            TimSort.reverseRange(arrT, n, n5);
            n3 = n5;
        } else {
            do {
                n3 = n5;
                if (n5 >= n2) break;
                n3 = n5;
                if (comparator.compare(arrT[n5], arrT[n5 - 1]) < 0) break;
                ++n5;
            } while (true);
        }
        return n3 - n;
    }

    private T[] ensureCapacity(int n) {
        if (this.tmpLen < n) {
            int n2 = n | n >> 1;
            n2 |= n2 >> 2;
            n2 |= n2 >> 4;
            n2 |= n2 >> 8;
            if ((n2 = (n2 | n2 >> 16) + 1) >= 0) {
                n = Math.min(n2, this.a.length >>> 1);
            }
            this.tmp = (Object[])Array.newInstance(this.a.getClass().getComponentType(), n);
            this.tmpLen = n;
            this.tmpBase = 0;
        }
        return this.tmp;
    }

    private static <T> int gallopLeft(T t, T[] arrT, int n, int n2, int n3, Comparator<? super T> comparator) {
        int n4 = 0;
        int n5 = 0;
        int n6 = 1;
        int n7 = 1;
        if (comparator.compare(t, arrT[n + n3]) > 0) {
            n4 = n2 - n3;
            n2 = n7;
            while (n2 < n4 && comparator.compare(t, arrT[n + n3 + n2]) > 0) {
                n7 = n2;
                n6 = (n2 << 1) + 1;
                n5 = n7;
                n2 = n6;
                if (n6 > 0) continue;
                n2 = n4;
                n5 = n7;
            }
            n7 = n2;
            if (n2 > n4) {
                n7 = n4;
            }
            n2 = n5 + n3;
            n4 = n7 + n3;
            n3 = n2;
            n2 = n4;
        } else {
            n5 = n3 + 1;
            n2 = n6;
            while (n2 < n5 && comparator.compare(t, arrT[n + n3 - n2]) <= 0) {
                n7 = n2;
                n6 = (n2 << 1) + 1;
                n4 = n7;
                n2 = n6;
                if (n6 > 0) continue;
                n2 = n5;
                n4 = n7;
            }
            n7 = n2;
            if (n2 > n5) {
                n7 = n5;
            }
            n5 = n3 - n7;
            n2 = n3 - n4;
            n3 = n5;
        }
        ++n3;
        while (n3 < n2) {
            n4 = (n2 - n3 >>> 1) + n3;
            if (comparator.compare(t, arrT[n + n4]) > 0) {
                n3 = n4 + 1;
                continue;
            }
            n2 = n4;
        }
        return n2;
    }

    private static <T> int gallopRight(T t, T[] arrT, int n, int n2, int n3, Comparator<? super T> comparator) {
        int n4 = 1;
        int n5 = 1;
        int n6 = 0;
        int n7 = 0;
        if (comparator.compare(t, arrT[n + n3]) < 0) {
            n6 = n3 + 1;
            n2 = n5;
            while (n2 < n6 && comparator.compare(t, arrT[n + n3 - n2]) < 0) {
                n5 = n2;
                n2 = n4 = (n2 << 1) + 1;
                n7 = n5;
                if (n4 > 0) continue;
                n2 = n6;
                n7 = n5;
            }
            n5 = n2;
            if (n2 > n6) {
                n5 = n6;
            }
            n6 = n3 - n5;
            n2 = n3 - n7;
            n3 = n6;
        } else {
            n7 = n2 - n3;
            n2 = n4;
            while (n2 < n7 && comparator.compare(t, arrT[n + n3 + n2]) >= 0) {
                n5 = n2;
                n2 = n4 = (n2 << 1) + 1;
                n6 = n5;
                if (n4 > 0) continue;
                n2 = n7;
                n6 = n5;
            }
            n5 = n2;
            if (n2 > n7) {
                n5 = n7;
            }
            n2 = n6 + n3;
            n7 = n5 + n3;
            n3 = n2;
            n2 = n7;
        }
        ++n3;
        while (n3 < n2) {
            n7 = (n2 - n3 >>> 1) + n3;
            if (comparator.compare(t, arrT[n + n7]) < 0) {
                n2 = n7;
                continue;
            }
            n3 = n7 + 1;
        }
        return n2;
    }

    private void mergeAt(int n) {
        Object[] arrobject = this.runBase;
        int n2 = arrobject[n];
        int[] arrn = this.runLen;
        int n3 = arrn[n];
        int n4 = arrobject[n + 1];
        int n5 = arrn[n + 1];
        arrn[n] = n3 + n5;
        if (n == this.stackSize - 3) {
            arrobject[n + 1] = arrobject[n + 2];
            arrn[n + 1] = arrn[n + 2];
        }
        --this.stackSize;
        arrobject = this.a;
        int n6 = TimSort.gallopRight(arrobject[n4], arrobject, n2, n3, 0, this.c);
        n = n2 + n6;
        if ((n3 -= n6) == 0) {
            return;
        }
        arrobject = this.a;
        if ((n5 = TimSort.gallopLeft(arrobject[n + n3 - 1], arrobject, n4, n5, n5 - 1, this.c)) == 0) {
            return;
        }
        if (n3 <= n5) {
            this.mergeLo(n, n3, n4, n5);
        } else {
            this.mergeHi(n, n3, n4, n5);
        }
    }

    private void mergeCollapse() {
        int n;
        while ((n = this.stackSize) > 1) {
            int[] arrn;
            int n2 = n - 2;
            if (n2 > 0 && (arrn = this.runLen)[n2 - 1] <= arrn[n2] + arrn[n2 + 1]) {
                n = n2;
                if (arrn[n2 - 1] < arrn[n2 + 1]) {
                    n = n2 - 1;
                }
                this.mergeAt(n);
                continue;
            }
            arrn = this.runLen;
            if (arrn[n2] > arrn[n2 + 1]) break;
            this.mergeAt(n2);
        }
    }

    private void mergeForceCollapse() {
        int n;
        while ((n = this.stackSize) > 1) {
            int n2;
            n = n2 = n - 2;
            if (n2 > 0) {
                int[] arrn = this.runLen;
                n = n2;
                if (arrn[n2 - 1] < arrn[n2 + 1]) {
                    n = n2 - 1;
                }
            }
            this.mergeAt(n);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void mergeHi(int var1_1, int var2_2, int var3_3, int var4_4) {
        var5_5 = var4_4;
        var6_6 = this.a;
        var7_7 = this.ensureCapacity(var5_5);
        var8_8 = this.tmpBase;
        System.arraycopy(var6_6, var3_3, var7_7, var8_8, var5_5);
        var9_9 = var1_1 + var2_2 - 1;
        var4_4 = var8_8 + var5_5 - 1;
        var10_10 = var3_3 + var5_5 - 1;
        var11_11 = var10_10 - 1;
        var3_3 = var9_9 - 1;
        var6_6[var10_10] = var6_6[var9_9];
        var9_9 = var2_2 - 1;
        if (var9_9 == 0) {
            System.arraycopy(var7_7, var8_8, var6_6, var11_11 - (var5_5 - 1), var5_5);
            return;
        }
        if (var5_5 == 1) {
            var1_1 = var11_11 - var9_9;
            System.arraycopy(var6_6, var3_3 - var9_9 + 1, var6_6, var1_1 + 1, var9_9);
            var6_6[var1_1] = var7_7[var4_4];
            return;
        }
        var12_12 = this.c;
        var2_2 = this.minGallop;
        block0 : do {
            var13_13 = 0;
            var10_10 = 0;
            var14_14 = var11_11;
            do {
                block15 : {
                    block16 : {
                        block13 : {
                            block14 : {
                                if (var12_12.compare(var7_7[var4_4], var6_6[var3_3]) >= 0) break block13;
                                var10_10 = var14_14 - 1;
                                var11_11 = var3_3 - 1;
                                var6_6[var14_14] = var6_6[var3_3];
                                ++var13_13;
                                if (--var9_9 != 0) break block14;
                                var1_1 = var2_2;
                                var2_2 = var9_9;
                                var3_3 = var4_4;
                                var4_4 = var5_5;
                                var5_5 = var11_11;
                                var9_9 = var10_10;
                                ** GOTO lbl131
                            }
                            var14_14 = 0;
                            var3_3 = var10_10;
                            var10_10 = var14_14;
                            break block15;
                        }
                        var13_13 = var14_14 - 1;
                        var11_11 = var4_4 - 1;
                        var6_6[var14_14] = var7_7[var4_4];
                        ++var10_10;
                        if (--var5_5 != 1) break block16;
                        var4_4 = var5_5;
                        var5_5 = var3_3;
                        var3_3 = var11_11;
                        var1_1 = var2_2;
                        var2_2 = var9_9;
                        var9_9 = var13_13;
                        ** GOTO lbl131
                    }
                    var15_15 = 0;
                    var4_4 = var13_13;
                    var14_14 = var11_11;
                    var13_13 = var15_15;
                    var11_11 = var3_3;
                    var3_3 = var4_4;
                    var4_4 = var14_14;
                }
                if ((var13_13 | var10_10) >= var2_2) break;
                var14_14 = var3_3;
                var3_3 = var11_11;
            } while (true);
            var13_13 = var11_11;
            var11_11 = var3_3;
            var3_3 = var4_4;
            var4_4 = var5_5;
            var10_10 = var9_9;
            do {
                block23 : {
                    block19 : {
                        block22 : {
                            block21 : {
                                block20 : {
                                    block17 : {
                                        block18 : {
                                            var15_15 = var10_10 - TimSort.gallopRight(var7_7[var3_3], var6_6, var1_1, var10_10, var10_10 - 1, var12_12);
                                            var9_9 = var10_10;
                                            var5_5 = var13_13;
                                            var14_14 = var11_11;
                                            if (var15_15 == 0) break block17;
                                            var9_9 = var11_11 - var15_15;
                                            var5_5 = var13_13 - var15_15;
                                            var11_11 = var10_10 - var15_15;
                                            System.arraycopy(var6_6, var5_5 + 1, var6_6, var9_9 + 1, var15_15);
                                            if (var11_11 != 0) break block18;
                                            var1_1 = var2_2;
                                            var2_2 = var11_11;
                                            break block19;
                                        }
                                        var14_14 = var9_9;
                                        var9_9 = var11_11;
                                    }
                                    var10_10 = var14_14 - 1;
                                    var11_11 = var3_3 - 1;
                                    var6_6[var14_14] = var7_7[var3_3];
                                    if (--var4_4 != 1) break block20;
                                    var1_1 = var9_9;
                                    var9_9 = var10_10;
                                    var10_10 = var2_2;
                                    var3_3 = var11_11;
                                    var2_2 = var1_1;
                                    var1_1 = var10_10;
                                    break block19;
                                }
                                var14_14 = var4_4 - TimSort.gallopLeft(var6_6[var5_5], var7_7, var8_8, var4_4, var4_4 - 1, var12_12);
                                if (var14_14 == 0) break block21;
                                var13_13 = var10_10 - var14_14;
                                var3_3 = var11_11 - var14_14;
                                System.arraycopy(var7_7, var3_3 + 1, var6_6, var13_13 + 1, var14_14);
                                if ((var4_4 -= var14_14) > 1) break block22;
                                var11_11 = var13_13;
                                var1_1 = var2_2;
                                var2_2 = var9_9;
                                var9_9 = var11_11;
                                break block19;
                            }
                            var3_3 = var11_11;
                            var13_13 = var10_10;
                        }
                        var11_11 = var13_13 - 1;
                        var10_10 = var5_5 - 1;
                        var6_6[var13_13] = var6_6[var5_5];
                        if (--var9_9 != 0) break block23;
                        var5_5 = var10_10;
                        var10_10 = var9_9;
                        var1_1 = var2_2;
                        var9_9 = var11_11;
                        var2_2 = var10_10;
                    }
                    if (var1_1 < 1) {
                        var1_1 = 1;
                    }
                    this.minGallop = var1_1;
                    if (var4_4 == 1) {
                        var1_1 = var9_9 - var2_2;
                        System.arraycopy(var6_6, var5_5 - var2_2 + 1, var6_6, var1_1 + 1, var2_2);
                        var6_6[var1_1] = var7_7[var3_3];
                        return;
                    }
                    if (var4_4 == 0) throw new IllegalArgumentException("Comparison method violates its general contract!");
                    System.arraycopy(var7_7, var8_8, var6_6, var9_9 - (var4_4 - 1), var4_4);
                    return;
                }
                --var2_2;
                var13_13 = 0;
                var5_5 = var15_15 >= 7 ? 1 : 0;
                if (var14_14 >= 7) {
                    var13_13 = 1;
                }
                if ((var13_13 | var5_5) == 0) {
                    var5_5 = var2_2;
                    if (var2_2 < 0) {
                        var5_5 = 0;
                    }
                    var2_2 = var5_5 + 2;
                    var5_5 = var4_4;
                    var4_4 = var3_3;
                    var3_3 = var10_10;
                    continue block0;
                }
                var13_13 = var10_10;
                var10_10 = var9_9;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void mergeLo(int var1_1, int var2_2, int var3_3, int var4_4) {
        var5_5 = var2_2;
        var6_6 = this.a;
        var7_7 = this.ensureCapacity(var5_5);
        var8_8 = this.tmpBase;
        System.arraycopy(var6_6, var1_1, var7_7, var8_8, var5_5);
        var2_2 = var1_1 + 1;
        var9_9 = var3_3 + 1;
        var6_6[var1_1] = var6_6[var3_3];
        var3_3 = var4_4 - 1;
        if (var3_3 == 0) {
            System.arraycopy(var7_7, var8_8, var6_6, var2_2, var5_5);
            return;
        }
        if (var5_5 == 1) {
            System.arraycopy(var6_6, var9_9, var6_6, var2_2, var3_3);
            var6_6[var2_2 + var3_3] = var7_7[var8_8];
            return;
        }
        var10_10 = this.c;
        var1_1 = this.minGallop;
        var4_4 = var9_9;
        var9_9 = var3_3;
        var3_3 = var8_8;
        block0 : do {
            var11_11 = 0;
            var12_12 = 0;
            do {
                block15 : {
                    block16 : {
                        block13 : {
                            block14 : {
                                if (var10_10.compare(var6_6[var4_4], var7_7[var3_3]) >= 0) break block13;
                                var11_11 = var2_2 + 1;
                                var8_8 = var4_4 + 1;
                                var6_6[var2_2] = var6_6[var4_4];
                                ++var12_12;
                                if (--var9_9 != 0) break block14;
                                var12_12 = 1;
                                var4_4 = var8_8;
                                var8_8 = var3_3;
                                var2_2 = var1_1;
                                var3_3 = var11_11;
                                var1_1 = var12_12;
                                ** GOTO lbl118
                            }
                            var13_13 = 0;
                            var2_2 = var11_11;
                            var4_4 = var8_8;
                            var11_11 = var13_13;
                            var8_8 = var12_12;
                            break block15;
                        }
                        var12_12 = var2_2 + 1;
                        var8_8 = var3_3 + 1;
                        var6_6[var2_2] = var7_7[var3_3];
                        ++var11_11;
                        if (--var5_5 != 1) break block16;
                        var11_11 = 1;
                        var3_3 = var12_12;
                        var2_2 = var1_1;
                        var1_1 = var11_11;
                        ** GOTO lbl118
                    }
                    var13_13 = 0;
                    var2_2 = var12_12;
                    var3_3 = var8_8;
                    var8_8 = var13_13;
                }
                if ((var11_11 | var8_8) >= var1_1) break;
                var12_12 = var8_8;
            } while (true);
            var8_8 = var4_4;
            var4_4 = var2_2;
            var2_2 = var8_8;
            var8_8 = var3_3;
            var3_3 = var4_4;
            do {
                block24 : {
                    block19 : {
                        block23 : {
                            block22 : {
                                block21 : {
                                    block20 : {
                                        block17 : {
                                            block18 : {
                                                if ((var13_13 = TimSort.gallopRight(var6_6[var2_2], var7_7, var8_8, var5_5, 0, var10_10)) == 0) break block17;
                                                System.arraycopy(var7_7, var8_8, var6_6, var3_3, var13_13);
                                                var3_3 += var13_13;
                                                var8_8 += var13_13;
                                                if ((var5_5 -= var13_13) > 1) break block18;
                                                var4_4 = var2_2;
                                                var2_2 = var1_1;
                                                var1_1 = 1;
                                                break block19;
                                            }
                                            var11_11 = var3_3;
                                            break block20;
                                        }
                                        var11_11 = var3_3;
                                    }
                                    var3_3 = var11_11 + 1;
                                    var4_4 = var2_2 + 1;
                                    var6_6[var11_11] = var6_6[var2_2];
                                    if (--var9_9 != 0) break block21;
                                    var2_2 = var1_1;
                                    var1_1 = 1;
                                    break block19;
                                }
                                var14_14 = TimSort.gallopLeft(var7_7[var8_8], var6_6, var4_4, var9_9, 0, var10_10);
                                if (var14_14 == 0) break block22;
                                System.arraycopy(var6_6, var4_4, var6_6, var3_3, var14_14);
                                var11_11 = var3_3 + var14_14;
                                var4_4 += var14_14;
                                if ((var9_9 -= var14_14) != 0) break block23;
                                var3_3 = var11_11;
                                var2_2 = var1_1;
                                var1_1 = 1;
                                break block19;
                            }
                            var11_11 = var3_3;
                        }
                        var2_2 = var11_11 + 1;
                        var3_3 = var8_8 + 1;
                        var6_6[var11_11] = var7_7[var8_8];
                        var12_12 = 1;
                        if (--var5_5 != 1) break block24;
                        var11_11 = var2_2;
                        var8_8 = var3_3;
                        var2_2 = var1_1;
                        var1_1 = var12_12;
                        var3_3 = var11_11;
                    }
                    if (var2_2 < var1_1) {
                        var2_2 = var1_1;
                    }
                    this.minGallop = var2_2;
                    if (var5_5 == var1_1) {
                        System.arraycopy(var6_6, var4_4, var6_6, var3_3, var9_9);
                        var6_6[var3_3 + var9_9] = var7_7[var8_8];
                        return;
                    }
                    if (var5_5 == 0) throw new IllegalArgumentException("Comparison method violates its general contract!");
                    System.arraycopy(var7_7, var8_8, var6_6, var3_3, var5_5);
                    return;
                }
                --var1_1;
                var11_11 = 0;
                var8_8 = var13_13 >= 7 ? 1 : 0;
                if (var14_14 >= 7) {
                    var11_11 = 1;
                }
                if ((var8_8 | var11_11) == 0) {
                    var8_8 = var1_1;
                    if (var1_1 < 0) {
                        var8_8 = 0;
                    }
                    var1_1 = var8_8 + 2;
                    continue block0;
                }
                var8_8 = var3_3;
                var3_3 = var2_2;
                var2_2 = var4_4;
            } while (true);
            break;
        } while (true);
    }

    private static int minRunLength(int n) {
        int n2 = 0;
        while (n >= 32) {
            n2 |= n & 1;
            n >>= 1;
        }
        return n + n2;
    }

    private void pushRun(int n, int n2) {
        int[] arrn = this.runBase;
        int n3 = this.stackSize;
        arrn[n3] = n;
        this.runLen[n3] = n2;
        this.stackSize = n3 + 1;
    }

    private static void reverseRange(Object[] arrobject, int n, int n2) {
        --n2;
        while (n < n2) {
            Object object = arrobject[n];
            arrobject[n] = arrobject[n2];
            arrobject[n2] = object;
            --n2;
            ++n;
        }
    }

    static <T> void sort(T[] arrT, int n, int n2, Comparator<? super T> comparator, T[] object, int n3, int n4) {
        int n5 = n2 - n;
        if (n5 < 2) {
            return;
        }
        if (n5 < 32) {
            TimSort.binarySort(arrT, n, n2, n + TimSort.countRunAndMakeAscending(arrT, n, n2, comparator), comparator);
            return;
        }
        object = new TimSort<T>(arrT, comparator, (T[])object, n3, n4);
        int n6 = TimSort.minRunLength(n5);
        n4 = n;
        n = n5;
        do {
            n3 = n5 = TimSort.countRunAndMakeAscending(arrT, n4, n2, comparator);
            if (n5 < n6) {
                n3 = n <= n6 ? n : n6;
                TimSort.binarySort(arrT, n4, n4 + n3, n4 + n5, comparator);
            }
            TimSort.super.pushRun(n4, n3);
            TimSort.super.mergeCollapse();
            n4 += n3;
        } while ((n -= n3) != 0);
        TimSort.super.mergeForceCollapse();
    }
}

