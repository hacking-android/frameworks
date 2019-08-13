/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Bidi;
import android.icu.text.BidiRun;
import java.util.Arrays;

final class BidiLine {
    BidiLine() {
    }

    static byte getLevelAt(Bidi bidi, int n) {
        if (bidi.direction == 2 && n < bidi.trailingWSStart) {
            return bidi.levels[n];
        }
        return bidi.GetParaLevelAt(n);
    }

    static byte[] getLevels(Bidi bidi) {
        int n = bidi.trailingWSStart;
        int n2 = bidi.length;
        if (n != n2) {
            Arrays.fill(bidi.levels, n, n2, bidi.paraLevel);
            bidi.trailingWSStart = n2;
        }
        if (n2 < bidi.levels.length) {
            byte[] arrby = new byte[n2];
            System.arraycopy((byte[])bidi.levels, (int)0, (byte[])arrby, (int)0, (int)n2);
            return arrby;
        }
        return bidi.levels;
    }

    static int getLogicalIndex(Bidi bidi, int n) {
        int n2;
        int n3;
        BidiRun[] arrbidiRun;
        int n4;
        int n5;
        block21 : {
            int n6;
            int n7;
            int n8;
            Bidi bidi2 = bidi;
            n5 = n;
            arrbidiRun = bidi2.runs;
            n4 = bidi2.runCount;
            if (bidi2.insertPoints.size > 0) {
                n = 0;
                n3 = 0;
                n7 = 0;
                do {
                    n6 = arrbidiRun[n7].limit - n3;
                    n8 = arrbidiRun[n7].insertRemove;
                    n2 = n;
                    if ((n8 & 5) > 0) {
                        if (n5 <= n3 + n) {
                            return -1;
                        }
                        n2 = n + 1;
                    }
                    if (n5 < arrbidiRun[n7].limit + n2) {
                        n = n5 - n2;
                        break block21;
                    }
                    n = n2;
                    if ((n8 & 10) > 0) {
                        if (n5 == n3 + n6 + n2) {
                            return -1;
                        }
                        n = n2 + 1;
                    }
                    ++n7;
                    n3 += n6;
                } while (true);
            }
            n = n5;
            if (bidi2.controlCount > 0) {
                n = 0;
                n3 = 0;
                n2 = 0;
                do {
                    n8 = arrbidiRun[n2].limit - n3;
                    n7 = arrbidiRun[n2].insertRemove;
                    if (n5 < arrbidiRun[n2].limit - n + n7) break;
                    n -= n7;
                    ++n2;
                    n3 += n8;
                } while (true);
                if (n7 == 0) {
                    n = n5 + n;
                } else {
                    int n9 = arrbidiRun[n2].start;
                    boolean bl = arrbidiRun[n2].isEvenRun();
                    n7 = 0;
                    do {
                        n2 = n;
                        if (n7 >= n8) break;
                        n6 = bl ? n9 + n7 : n9 + n8 - 1 - n7;
                        n2 = n;
                        if (Bidi.IsBidiControlChar(bidi.text[n6])) {
                            n2 = n + 1;
                        }
                        if (n5 + n2 == n3 + n7) break;
                        ++n7;
                        n = n2;
                    } while (true);
                    n = n5 + n2;
                }
            }
        }
        if (n4 <= 10) {
            n2 = 0;
            do {
                n5 = n2++;
                if (n >= arrbidiRun[n2].limit) {
                    continue;
                }
                break;
            } while (true);
        } else {
            n5 = 0;
            n2 = n4;
            do {
                n3 = n5 + n2 >>> 1;
                if (n >= arrbidiRun[n3].limit) {
                    n5 = n3 + 1;
                    continue;
                }
                if (n3 == 0 || n >= arrbidiRun[n3 - 1].limit) break;
                n2 = n3;
            } while (true);
            n5 = n3;
        }
        n3 = arrbidiRun[n5].start;
        if (arrbidiRun[n5].isEvenRun()) {
            n2 = n;
            if (n5 > 0) {
                n2 = n - arrbidiRun[n5 - 1].limit;
            }
            return n3 + n2;
        }
        return arrbidiRun[n5].limit + n3 - n - 1;
    }

    static int[] getLogicalMap(Bidi arrbidiRun) {
        int[] arrn;
        block21 : {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            BidiRun[] arrbidiRun2;
            block20 : {
                arrbidiRun2 = arrbidiRun.runs;
                arrn = new int[arrbidiRun.length];
                if (arrbidiRun.length > arrbidiRun.resultLength) {
                    Arrays.fill(arrn, -1);
                }
                n6 = 0;
                for (n4 = 0; n4 < arrbidiRun.runCount; ++n4) {
                    n2 = arrbidiRun2[n4].start;
                    n5 = arrbidiRun2[n4].limit;
                    if (arrbidiRun2[n4].isEvenRun()) {
                        n3 = n6;
                        do {
                            n = n3 + 1;
                            arrn[n2] = n3;
                            ++n2;
                            n3 = n6 = n;
                        } while (n < n5);
                        continue;
                    }
                    n3 = n2 + (n5 - n6);
                    n2 = n6;
                    do {
                        n = n2 + 1;
                        arrn[--n3] = n2;
                        n2 = n6 = n;
                    } while (n < n5);
                }
                if (arrbidiRun.insertPoints.size <= 0) break block20;
                n4 = 0;
                n5 = arrbidiRun.runCount;
                arrbidiRun = arrbidiRun.runs;
                n2 = 0;
                n3 = 0;
                while (n3 < n5) {
                    int n7 = arrbidiRun[n3].limit - n2;
                    int n8 = arrbidiRun[n3].insertRemove;
                    n6 = n4;
                    if ((n8 & 5) > 0) {
                        n6 = n4 + 1;
                    }
                    if (n6 > 0) {
                        for (n4 = n = arrbidiRun[n3].start; n4 < n + n7; ++n4) {
                            arrn[n4] = arrn[n4] + n6;
                        }
                    }
                    n4 = n6;
                    if ((n8 & 10) > 0) {
                        n4 = n6 + 1;
                    }
                    ++n3;
                    n2 += n7;
                }
                break block21;
            }
            if (arrbidiRun.controlCount <= 0) break block21;
            n6 = 0;
            int n9 = arrbidiRun.runCount;
            arrbidiRun2 = arrbidiRun.runs;
            n3 = 0;
            n4 = 0;
            while (n4 < n9) {
                int n10 = arrbidiRun2[n4].limit - n3;
                n2 = arrbidiRun2[n4].insertRemove;
                if (n6 - n2 == 0) {
                    n = n6;
                } else {
                    n5 = arrbidiRun2[n4].start;
                    boolean bl = arrbidiRun2[n4].isEvenRun();
                    int n11 = n5 + n10;
                    if (n2 == 0) {
                        n2 = n5;
                        do {
                            n = n6;
                            if (n2 < n11) {
                                arrn[n2] = arrn[n2] - n6;
                                ++n2;
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        n2 = 0;
                        do {
                            n = n6;
                            if (n2 >= n10) break;
                            n = bl ? n5 + n2 : n11 - n2 - 1;
                            if (Bidi.IsBidiControlChar(arrbidiRun.text[n])) {
                                ++n6;
                                arrn[n] = -1;
                            } else {
                                arrn[n] = arrn[n] - n6;
                            }
                            ++n2;
                        } while (true);
                    }
                }
                ++n4;
                n3 += n10;
                n6 = n;
            }
        }
        return arrn;
    }

    static BidiRun getLogicalRun(Bidi bidi, int n) {
        BidiRun bidiRun = new BidiRun();
        BidiLine.getRuns(bidi);
        int n2 = bidi.runCount;
        int n3 = 0;
        int n4 = 0;
        BidiRun bidiRun2 = bidi.runs[0];
        for (int i = 0; i < n2; ++i) {
            bidiRun2 = bidi.runs[i];
            n4 = bidiRun2.start + bidiRun2.limit - n3;
            if (n >= bidiRun2.start && n < n4) break;
            n3 = bidiRun2.limit;
        }
        bidiRun.start = bidiRun2.start;
        bidiRun.limit = n4;
        bidiRun.level = bidiRun2.level;
        return bidiRun;
    }

    static int getRunFromLogicalIndex(Bidi bidi, int n) {
        BidiRun[] arrbidiRun = bidi.runs;
        int n2 = bidi.runCount;
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = arrbidiRun[i].limit - n3;
            int n5 = arrbidiRun[i].start;
            if (n >= n5 && n < n5 + n4) {
                return i;
            }
            n3 += n4;
        }
        throw new IllegalStateException("Internal ICU error in getRunFromLogicalIndex");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static void getRuns(Bidi var0) {
        block20 : {
            block19 : {
                if (var0.runCount >= 0) {
                    return;
                }
                if (var0.direction == 2) break block19;
                BidiLine.getSingleRun(var0, var0.paraLevel);
                ** GOTO lbl68
            }
            var1_1 = var0.length;
            var2_2 = var0.levels;
            var3_5 = -1;
            var4_6 = var0.trailingWSStart;
            var5_7 = 0;
            for (var6_8 = 0; var6_8 < var4_6; ++var6_8) {
                var7_9 = var3_5;
                var8_10 = var5_7;
                if (var2_2[var6_8] != var3_5) {
                    var8_10 = var5_7 + 1;
                    var7_9 = var2_2[var6_8];
                }
                var3_5 = var7_9;
                var5_7 = var8_10;
            }
            if (var5_7 != 1 || var4_6 != var1_1) break block20;
            BidiLine.getSingleRun(var0, var2_2[0]);
            ** GOTO lbl68
        }
        var9_11 = 126;
        var10_12 = 0;
        var6_8 = var5_7;
        if (var4_6 < var1_1) {
            var6_8 = var5_7 + 1;
        }
        var0.getRunsMemory(var6_8);
        var11_13 = var0.runsMemory;
        var8_10 = 0;
        var5_7 = 0;
        do {
            block21 : {
                var3_5 = var5_7;
                var12_14 = var2_2[var3_5];
                var7_9 = var9_11;
                if (var12_14 < var9_11) {
                    var7_9 = var12_14;
                }
                var5_7 = var3_5;
                var13_15 = var10_12;
                if (var12_14 > var10_12) {
                    var13_15 = var5_7 = var12_14;
                    var5_7 = var3_5;
                }
                while (++var5_7 < var4_6 && var2_2[var5_7] == var12_14) {
                }
                var11_13[var8_10] = new BidiRun(var3_5, var5_7 - var3_5, (byte)var12_14);
                ++var8_10;
                if (var5_7 < var4_6) break block21;
                var12_14 = var7_9;
                if (var4_6 < var1_1) {
                    var11_13[var8_10] = new BidiRun(var4_6, var1_1 - var4_6, var0.paraLevel);
                    var12_14 = var7_9;
                    if (var0.paraLevel < var7_9) {
                        var12_14 = var5_7 = var0.paraLevel;
                    }
                }
                var0.runs = var11_13;
                var0.runCount = var6_8;
                BidiLine.reorderLine(var0, (byte)var12_14, (byte)var13_15);
                var7_9 = 0;
                for (var5_7 = 0; var5_7 < var6_8; ++var5_7) {
                    var11_13[var5_7].level = var2_2[var11_13[var5_7].start];
                    var14_16 = var11_13[var5_7];
                    var14_16.limit = var7_9 = var14_16.limit + var7_9;
                }
                if (var8_10 < var6_8) {
                    var5_7 = (var0.paraLevel & 1) != 0 ? 0 : var8_10;
                    var11_13[var5_7].level = var0.paraLevel;
                }
lbl68: // 5 sources:
                if (var0.insertPoints.size > 0) {
                    for (var5_7 = 0; var5_7 < var0.insertPoints.size; var14_16.insertRemove |= var2_4.flag, ++var5_7) {
                        var2_4 = var0.insertPoints.points[var5_7];
                        var6_8 = BidiLine.getRunFromLogicalIndex(var0, var2_4.pos);
                        var14_16 = var0.runs[var6_8];
                    }
                }
                if (var0.controlCount <= 0) return;
                var5_7 = 0;
                while (var5_7 < var0.length) {
                    if (Bidi.IsBidiControlChar(var0.text[var5_7])) {
                        var6_8 = BidiLine.getRunFromLogicalIndex(var0, var5_7);
                        var14_16 = var0.runs[var6_8];
                        --var14_16.insertRemove;
                    }
                    ++var5_7;
                }
                return;
            }
            var9_11 = var7_9;
            var10_12 = var13_15;
        } while (true);
    }

    static void getSingleRun(Bidi bidi, byte by) {
        bidi.runs = bidi.simpleRuns;
        bidi.runCount = 1;
        bidi.runs[0] = new BidiRun(0, bidi.length, by);
    }

    static int getVisualIndex(Bidi arrbidiRun, int n) {
        int n2;
        int n3;
        BidiRun[] arrbidiRun2;
        int n4;
        int n5 = -1;
        int n6 = arrbidiRun.direction;
        if (n6 != 0) {
            if (n6 != 1) {
                BidiLine.getRuns((Bidi)arrbidiRun);
                arrbidiRun2 = arrbidiRun.runs;
                n4 = 0;
                n3 = 0;
                do {
                    n6 = n5;
                    if (n3 >= arrbidiRun.runCount) break;
                    n6 = arrbidiRun2[n3].limit - n4;
                    n2 = n - arrbidiRun2[n3].start;
                    if (n2 >= 0 && n2 < n6) {
                        if (arrbidiRun2[n3].isEvenRun()) {
                            n6 = n4 + n2;
                            break;
                        }
                        n6 = n4 + n6 - n2 - 1;
                        break;
                    }
                    n4 += n6;
                    ++n3;
                } while (true);
                if (n3 >= arrbidiRun.runCount) {
                    return -1;
                }
            } else {
                n6 = arrbidiRun.length - n - 1;
            }
        } else {
            n6 = n;
        }
        if (arrbidiRun.insertPoints.size > 0) {
            arrbidiRun = arrbidiRun.runs;
            n5 = 0;
            n = 0;
            n4 = 0;
            do {
                n2 = arrbidiRun[n4].limit;
                int n7 = arrbidiRun[n4].insertRemove;
                n3 = n;
                if ((n7 & 5) > 0) {
                    n3 = n + 1;
                }
                if (n6 < arrbidiRun[n4].limit) {
                    return n6 + n3;
                }
                n = n3;
                if ((n7 & 10) > 0) {
                    n = n3 + 1;
                }
                ++n4;
                n5 += n2 - n5;
            } while (true);
        }
        if (arrbidiRun.controlCount > 0) {
            int n8;
            arrbidiRun2 = arrbidiRun.runs;
            n5 = 0;
            n3 = 0;
            if (Bidi.IsBidiControlChar(arrbidiRun.text[n])) {
                return -1;
            }
            n4 = 0;
            do {
                n2 = arrbidiRun2[n4].limit - n5;
                n8 = arrbidiRun2[n4].insertRemove;
                if (n6 < arrbidiRun2[n4].limit) break;
                n3 -= n8;
                ++n4;
                n5 += n2;
            } while (true);
            if (n8 == 0) {
                return n6 - n3;
            }
            if (arrbidiRun2[n4].isEvenRun()) {
                n5 = arrbidiRun2[n4].start;
                n4 = n;
                n = n5;
            } else {
                ++n;
                n4 = arrbidiRun2[n4].start + n2;
            }
            while (n < n4) {
                n5 = n3;
                if (Bidi.IsBidiControlChar(arrbidiRun.text[n])) {
                    n5 = n3 + 1;
                }
                ++n;
                n3 = n5;
            }
            return n6 - n3;
        }
        return n6;
    }

    static int[] getVisualMap(Bidi bidi) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        Object[] arrobject = bidi.runs;
        int n6 = bidi.length > bidi.resultLength ? bidi.length : bidi.resultLength;
        int[] arrn = new int[n6];
        int n7 = 0;
        int n8 = 0;
        for (n5 = 0; n5 < bidi.runCount; ++n5) {
            n2 = arrobject[n5].start;
            n = arrobject[n5].limit;
            if (arrobject[n5].isEvenRun()) {
                n4 = n7;
                do {
                    arrn[n8] = n2++;
                    n7 = n4 + 1;
                    n3 = n8 + 1;
                    n4 = n7;
                    n8 = n3;
                } while (n7 < n);
                n8 = n3;
                continue;
            }
            n2 += n - n7;
            n3 = n7;
            do {
                arrn[n8] = --n2;
                n7 = n3 + 1;
                n4 = n8 + 1;
                n3 = n7;
                n8 = n4;
            } while (n7 < n);
            n8 = n4;
        }
        if (bidi.insertPoints.size > 0) {
            n7 = 0;
            n3 = bidi.runCount;
            arrobject = bidi.runs;
            for (n5 = 0; n5 < n3; ++n5) {
                n2 = arrobject[n5].insertRemove;
                n8 = n7;
                if ((n2 & 5) > 0) {
                    n8 = n7 + 1;
                }
                n7 = n8;
                if ((n2 & 10) <= 0) continue;
                n7 = n8 + 1;
            }
            n2 = bidi.resultLength;
            --n3;
            n5 = n7;
            while (n3 >= 0 && n5 > 0) {
                n4 = arrobject[n3].insertRemove;
                n7 = n5;
                n8 = n2;
                if ((n4 & 10) > 0) {
                    n8 = n2 - 1;
                    arrn[n8] = -1;
                    n7 = n5 - 1;
                }
                n5 = n3 > 0 ? arrobject[n3 - 1].limit : 0;
                for (n2 = arrobject[n3].limit - 1; n2 >= n5 && n7 > 0; --n2) {
                    arrn[--n8] = arrn[n2];
                }
                n5 = n7;
                n2 = n8;
                if ((n4 & 5) > 0) {
                    n2 = n8 - 1;
                    arrn[n2] = -1;
                    n5 = n7 - 1;
                }
                --n3;
            }
        } else if (bidi.controlCount > 0) {
            n = bidi.runCount;
            arrobject = bidi.runs;
            n8 = 0;
            n7 = 0;
            n2 = 0;
            while (n2 < n) {
                int n9 = arrobject[n2].limit - n8;
                n5 = arrobject[n2].insertRemove;
                if (n5 == 0 && n7 == n8) {
                    n7 += n9;
                } else if (n5 == 0) {
                    n4 = arrobject[n2].limit;
                    n3 = n8;
                    n5 = n7;
                    do {
                        n7 = n5++;
                        if (n3 < n4) {
                            arrn[n5] = arrn[n3];
                            ++n3;
                            continue;
                        }
                        break;
                    } while (true);
                } else {
                    int n10 = arrobject[n2].start;
                    boolean bl = arrobject[n2].isEvenRun();
                    n3 = 0;
                    n5 = n7;
                    do {
                        n7 = n5;
                        if (n3 >= n9) break;
                        n4 = bl ? n10 + n3 : n10 + n9 - 1 - n3;
                        n7 = n5;
                        if (!Bidi.IsBidiControlChar(bidi.text[n4])) {
                            arrn[n5] = n4;
                            n7 = n5 + 1;
                        }
                        ++n3;
                        n5 = n7;
                    } while (true);
                }
                ++n2;
                n8 += n9;
            }
        }
        if (n6 == bidi.resultLength) {
            return arrn;
        }
        arrobject = new int[bidi.resultLength];
        System.arraycopy(arrn, 0, arrobject, 0, bidi.resultLength);
        return arrobject;
    }

    static BidiRun getVisualRun(Bidi bidi, int n) {
        int n2 = bidi.runs[n].start;
        byte by = bidi.runs[n].level;
        n = n > 0 ? bidi.runs[n].limit + n2 - bidi.runs[n - 1].limit : bidi.runs[0].limit + n2;
        return new BidiRun(n2, n, by);
    }

    static int[] invertMap(int[] arrn) {
        int n;
        int n2 = arrn.length;
        int n3 = -1;
        int n4 = 0;
        for (n = 0; n < n2; ++n) {
            int n5 = arrn[n];
            int n6 = n3;
            if (n5 > n3) {
                n6 = n5;
            }
            int n7 = n4;
            if (n5 >= 0) {
                n7 = n4 + 1;
            }
            n3 = n6;
            n4 = n7;
        }
        n = n3 + 1;
        int[] arrn2 = new int[n];
        if (n4 < n) {
            Arrays.fill(arrn2, -1);
        }
        for (n4 = 0; n4 < n2; ++n4) {
            n = arrn[n4];
            if (n < 0) continue;
            arrn2[n] = n4;
        }
        return arrn2;
    }

    static int[] prepareReorder(byte[] arrby, byte[] arrby2, byte[] arrby3) {
        if (arrby != null && arrby.length > 0) {
            int n = 126;
            int n2 = 0;
            int n3 = arrby.length;
            while (n3 > 0) {
                int n4 = n3 - 1;
                int n5 = arrby[n4];
                if (n5 < 0) {
                    return null;
                }
                if (n5 > 126) {
                    return null;
                }
                int n6 = n;
                if (n5 < n) {
                    n6 = n5;
                }
                n = n6;
                n3 = n4;
                if (n5 <= n2) continue;
                n2 = n5;
                n = n6;
                n3 = n4;
            }
            arrby2[0] = (byte)n;
            arrby3[0] = (byte)n2;
            arrby2 = new int[arrby.length];
            n2 = arrby.length;
            while (n2 > 0) {
                arrby2[--n2] = n2;
            }
            return arrby2;
        }
        return null;
    }

    private static void reorderLine(Bidi object, byte by, byte by2) {
        int n;
        if (by2 <= (by | 1)) {
            return;
        }
        byte by3 = (byte)(by + 1);
        BidiRun[] arrbidiRun = ((Bidi)object).runs;
        byte[] arrby = ((Bidi)object).levels;
        int n2 = n = ((Bidi)object).runCount;
        by = by2;
        if (((Bidi)object).trailingWSStart < ((Bidi)object).length) {
            n2 = n - 1;
            by = by2;
        }
        block0 : while ((n = (int)((byte)(by - 1))) >= by3) {
            by = 0;
            do {
                int n3;
                if (by < n2 && arrby[arrbidiRun[by].start] < n) {
                    by = (byte)(by + 1);
                    continue;
                }
                if (by >= n2) {
                    by = (byte)n;
                    continue block0;
                }
                by2 = by;
                while ((n3 = by2 + 1) < n2 && arrby[arrbidiRun[n3].start] >= n) {
                    by2 = (byte)n3;
                }
                for (by2 = (byte)(n3 - 1); by < by2; by = (byte)(by + 1), by2 = (byte)(by2 - 1)) {
                    BidiRun bidiRun = arrbidiRun[by];
                    arrbidiRun[by] = arrbidiRun[by2];
                    arrbidiRun[by2] = bidiRun;
                }
                if (n3 == n2) {
                    by = (byte)n;
                    continue block0;
                }
                by = (byte)(n3 + 1);
            } while (true);
        }
        if ((by3 & 1) == 0) {
            n = 0;
            by = (byte)n2;
            by2 = (byte)n;
            if (((Bidi)object).trailingWSStart == ((Bidi)object).length) {
                by = (byte)(n2 - 1);
                by2 = (byte)n;
            }
            while (by2 < by) {
                object = arrbidiRun[by2];
                arrbidiRun[by2] = arrbidiRun[by];
                arrbidiRun[by] = object;
                by2 = (byte)(by2 + 1);
                by = (byte)(by - 1);
            }
        }
    }

    static int[] reorderLogical(byte[] arrby) {
        byte[] arrby2 = new byte[1];
        byte[] arrby3 = new byte[1];
        int[] arrn = BidiLine.prepareReorder(arrby, arrby2, arrby3);
        if (arrn == null) {
            return null;
        }
        int n = arrby2[0];
        int n2 = arrby3[0];
        if (n == n2 && (n & 1) == 0) {
            return arrn;
        }
        byte by = (byte)(n | 1);
        block0 : do {
            n = 0;
            do {
                int n3;
                block11 : {
                    block10 : {
                        if (n < arrby.length && arrby[n] < n2) {
                            ++n;
                            continue;
                        }
                        if (n >= arrby.length) break block10;
                        int n4 = n;
                        while ((n3 = n4 + 1) < arrby.length && arrby[n3] >= n2) {
                            n4 = n3;
                        }
                        n4 = n;
                        do {
                            arrn[n4] = n + n3 - 1 - arrn[n4];
                        } while (++n4 < n3);
                        if (n3 != arrby.length) break block11;
                    }
                    n2 = n = (int)((byte)(n2 - 1));
                    if (n >= by) continue block0;
                    return arrn;
                }
                n = n3 + 1;
            } while (true);
            break;
        } while (true);
    }

    static int[] reorderVisual(byte[] arrby) {
        byte[] arrby2 = new byte[1];
        byte[] arrby3 = new byte[1];
        int[] arrn = BidiLine.prepareReorder(arrby, arrby2, arrby3);
        if (arrn == null) {
            return null;
        }
        int n = arrby2[0];
        int n2 = arrby3[0];
        if (n == n2 && (n & 1) == 0) {
            return arrn;
        }
        byte by = (byte)(n | 1);
        block0 : do {
            n = 0;
            do {
                int n3;
                block11 : {
                    block10 : {
                        if (n < arrby.length && arrby[n] < n2) {
                            ++n;
                            continue;
                        }
                        if (n >= arrby.length) break block10;
                        int n4 = n;
                        while ((n3 = n4 + 1) < arrby.length && arrby[n3] >= n2) {
                            n4 = n3;
                        }
                        for (n4 = n3 - 1; n < n4; ++n, --n4) {
                            int n5 = arrn[n];
                            arrn[n] = arrn[n4];
                            arrn[n4] = n5;
                        }
                        if (n3 != arrby.length) break block11;
                    }
                    n2 = n = (int)((byte)(n2 - 1));
                    if (n >= by) continue block0;
                    return arrn;
                }
                n = n3 + 1;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static Bidi setLine(Bidi var0, int var1_1, int var2_2) {
        block11 : {
            block10 : {
                block9 : {
                    var3_3 = new Bidi();
                    var3_3.resultLength = var4_4 = var2_2 - var1_1;
                    var3_3.originalLength = var4_4;
                    var3_3.length = var4_4;
                    var3_3.text = new char[var4_4];
                    System.arraycopy(var0.text, var1_1, var3_3.text, 0, var4_4);
                    var3_3.paraLevel = var0.GetParaLevelAt(var1_1);
                    var3_3.paraCount = var0.paraCount;
                    var3_3.runs = new BidiRun[0];
                    var3_3.reorderingMode = var0.reorderingMode;
                    var3_3.reorderingOptions = var0.reorderingOptions;
                    if (var0.controlCount > 0) {
                        for (var5_5 = var1_1; var5_5 < var2_2; ++var5_5) {
                            if (!Bidi.IsBidiControlChar(var0.text[var5_5])) continue;
                            ++var3_3.controlCount;
                        }
                        var3_3.resultLength -= var3_3.controlCount;
                    }
                    var3_3.getDirPropsMemory(var4_4);
                    var3_3.dirProps = var3_3.dirPropsMemory;
                    System.arraycopy((byte[])var0.dirProps, (int)var1_1, (byte[])var3_3.dirProps, (int)0, (int)var4_4);
                    var3_3.getLevelsMemory(var4_4);
                    var3_3.levels = var3_3.levelsMemory;
                    System.arraycopy((byte[])var0.levels, (int)var1_1, (byte[])var3_3.levels, (int)0, (int)var4_4);
                    var3_3.runCount = -1;
                    if (var0.direction == 2) break block9;
                    var3_3.direction = var0.direction;
                    var3_3.trailingWSStart = var0.trailingWSStart <= var1_1 ? 0 : (var0.trailingWSStart < var2_2 ? var0.trailingWSStart - var1_1 : var4_4);
                    ** GOTO lbl59
                }
                var6_6 = var3_3.levels;
                BidiLine.setTrailingWSStart(var3_3);
                var5_5 = var3_3.trailingWSStart;
                if (var5_5 != 0) break block10;
                var3_3.direction = (byte)(var3_3.paraLevel & 1);
                ** GOTO lbl50
            }
            var2_2 = (byte)(var6_6[0] & 1);
            if (var5_5 >= var4_4 || (var3_3.paraLevel & 1) == var2_2) break block11;
            var3_3.direction = (byte)2;
            ** GOTO lbl50
        }
        var1_1 = 1;
        do {
            block14 : {
                block13 : {
                    block12 : {
                        if (var1_1 != var5_5) break block12;
                        var3_3.direction = (byte)var2_2;
                        break block13;
                    }
                    if ((var6_6[var1_1] & 1) == var2_2) break block14;
                    var3_3.direction = (byte)2;
                }
                var1_1 = var3_3.direction;
                if (var1_1 != 0) {
                    if (var1_1 == 1) {
                        var3_3.paraLevel = (byte)(1 | var3_3.paraLevel);
                        var3_3.trailingWSStart = 0;
                    }
                } else {
                    var3_3.paraLevel = (byte)(var3_3.paraLevel + 1 & -2);
                    var3_3.trailingWSStart = 0;
                }
lbl59: // 3 sources:
                var3_3.paraBidi = var0;
                return var3_3;
            }
            ++var1_1;
        } while (true);
    }

    static void setTrailingWSStart(Bidi bidi) {
        byte[] arrby = bidi.dirProps;
        byte[] arrby2 = bidi.levels;
        int n = bidi.length;
        byte by = bidi.paraLevel;
        int n2 = n;
        if (arrby[n - 1] == 7) {
            bidi.trailingWSStart = n;
            return;
        }
        do {
            n = n2;
            if (n2 <= 0) break;
            n = n2;
            if ((Bidi.DirPropFlag(arrby[n2 - 1]) & Bidi.MASK_WS) == 0) break;
            --n2;
        } while (true);
        while (n > 0 && arrby2[n - 1] == by) {
            --n;
        }
        bidi.trailingWSStart = n;
    }
}

