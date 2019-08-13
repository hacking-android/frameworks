/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.CollationSettings;

public final class CollationCompare {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int compareUpToQuaternary(CollationIterator var0, CollationIterator var1_1, CollationSettings var2_2) {
        block63 : {
            block61 : {
                block62 : {
                    var3_3 = var2_2.options;
                    var4_4 = (var3_3 & 12) == 0 ? 0L : var2_2.variableTop + 1L;
                    var6_5 = 0;
                    block0 : do {
                        block60 : {
                            var7_6 = var0.nextCE();
                            var9_7 = var7_6 >>> 32;
                            var11_8 = var6_5;
                            var12_9 = var9_7;
                            if (var9_7 < var4_4) {
                                var11_8 = var6_5;
                                var12_9 = var9_7;
                                if (var9_7 > 0x2000000L) {
                                    var14_10 = 1;
                                    var12_9 = var7_6;
                                    do {
                                        var0.setCurrentCE(var12_9 & -4294967296L);
                                        while ((var7_6 = (var9_7 = var0.nextCE()) >>> 32) == 0L) {
                                            var0.setCurrentCE(0L);
                                        }
                                        var11_8 = var14_10;
                                        var12_9 = var7_6;
                                        if (var7_6 >= var4_4) break block60;
                                        var12_9 = var9_7;
                                    } while (var7_6 > 0x2000000L);
                                    var12_9 = var7_6;
                                    var11_8 = var14_10;
                                }
                            }
                        }
                        if (var12_9 == 0L) {
                            var6_5 = var11_8;
                            continue;
                        }
                        do {
                            if ((var7_6 = (var9_7 = var1_1.nextCE()) >>> 32) >= var4_4 || var7_6 <= 0x2000000L) {
                                var6_5 = var11_8;
                            } else {
                                var11_8 = 1;
                                var7_6 = var9_7;
                                do {
                                    var9_7 = var12_9;
                                    var1_1.setCurrentCE(var7_6 & -4294967296L);
                                    while ((var17_12 = (var15_11 = var1_1.nextCE()) >>> 32) == 0L) {
                                        var1_1.setCurrentCE(0L);
                                    }
                                    var6_5 = var11_8;
                                    var7_6 = var17_12;
                                    var12_9 = var9_7;
                                    if (var17_12 >= var4_4) break;
                                    if (var17_12 <= 0x2000000L) {
                                        var6_5 = var11_8;
                                        var7_6 = var17_12;
                                        var12_9 = var9_7;
                                        break;
                                    }
                                    var12_9 = var9_7;
                                    var7_6 = var15_11;
                                } while (true);
                            }
                            if (var7_6 != 0L) {
                                if (var12_9 != var7_6) {
                                    if (var2_2.hasReordering()) {
                                        var12_9 = var2_2.reorder(var12_9);
                                        var7_6 = var2_2.reorder(var7_6);
                                    }
                                    if (var12_9 >= var7_6) return 1;
                                    return -1;
                                }
                                if (var12_9 != 1L) ** break;
                                if (CollationSettings.getStrength(var3_3) < 1) break block61;
                                if ((var3_3 & 2048) == 0) break block0;
                                break block62;
                            }
                            var11_8 = var6_5;
                        } while (true);
                        break;
                    } while (true);
                    var14_10 = 0;
                    var11_8 = 0;
                    var12_9 = var4_4;
                    block6 : do {
                        var19_13 = var14_10 + 1;
                        var20_14 = (int)var0.getCE(var14_10) >>> 16;
                        if (var20_14 == 0) {
                            var14_10 = var19_13;
                            continue;
                        }
                        var14_10 = var11_8;
                        do {
                            var11_8 = var14_10 + 1;
                            if ((var14_10 = (int)var1_1.getCE(var14_10) >>> 16) != 0) {
                                if (var20_14 != var14_10) {
                                    if (var20_14 >= var14_10) return 1;
                                    return -1;
                                }
                                if (var20_14 != 256) {
                                    var14_10 = var19_13;
                                    continue block6;
                                }
                                break block61;
                            }
                            var14_10 = var11_8;
                        } while (true);
                        break;
                    } while (true);
                }
                var11_8 = 0;
                var14_10 = 0;
                do {
                    var19_13 = var11_8;
                    do {
                        if ((var12_9 = var0.getCE(var19_13) >>> 32) <= 0x2000000L && var12_9 != 0L) break;
                        ++var19_13;
                    } while (true);
                    var20_14 = var14_10;
                    do {
                        var12_9 = var4_4 = var1_1.getCE(var20_14) >>> 32;
                        if (var4_4 <= 0x2000000L && var12_9 != 0L) break;
                        ++var20_14;
                    } while (true);
                    var21_15 = var19_13;
                    var22_16 = var20_14;
                    do {
                        var23_17 = 0;
                        while (var23_17 == 0 && var21_15 > var11_8) {
                            var23_17 = (int)var0.getCE(--var21_15) >>> 16;
                        }
                        var24_18 = 0;
                        while (var24_18 == 0 && var22_16 > var14_10) {
                            var24_18 = (int)var1_1.getCE(--var22_16) >>> 16;
                        }
                        if (var23_17 == var24_18) continue;
                        if (var23_17 >= var24_18) return 1;
                        return -1;
                    } while (var23_17 != 0);
                    if (var12_9 == 1L) break;
                    var11_8 = var19_13 + 1;
                    var14_10 = var20_14 + 1;
                } while (true);
            }
            var23_17 = 49152;
            if ((var3_3 & 1024) == 0) break block63;
            var24_18 = CollationSettings.getStrength(var3_3);
            var14_10 = 0;
            var11_8 = 0;
            block14 : do {
                block64 : {
                    var20_14 = var14_10;
                    if (var24_18 != 0) break block64;
                    var19_13 = var14_10;
                    do {
                        var14_10 = var19_13 + 1;
                        var12_9 = var0.getCE(var19_13);
                        var20_14 = (int)var12_9;
                        if (var12_9 >>> 32 != 0L && var20_14 != 0) break;
                        var19_13 = var14_10;
                    } while (true);
                    var19_13 = var20_14;
                    var21_15 = var20_14 & 49152;
                    var20_14 = var11_8;
                    do {
                        block65 : {
                            var11_8 = var20_14 + 1;
                            var12_9 = var1_1.getCE(var20_14);
                            var20_14 = (int)var12_9;
                            if (var12_9 >>> 32 == 0L || var20_14 == 0) break block65;
                            var20_14 &= 49152;
                            ** GOTO lbl170
                        }
                        var20_14 = var11_8;
                    } while (true);
                }
                do {
                    var19_13 = var20_14 + 1;
                    if (((var20_14 = (int)var0.getCE(var20_14)) & -65536) != 0) break;
                    var20_14 = var19_13;
                } while (true);
                var14_10 = var20_14;
                var21_15 = var20_14 & 49152;
                var20_14 = var11_8;
                do {
                    block66 : {
                        var11_8 = var20_14 + 1;
                        if (((var20_14 = (int)var1_1.getCE(var20_14)) & -65536) == 0) break block66;
                        var20_14 &= 49152;
                        var22_16 = var19_13;
                        var19_13 = var14_10;
                        var14_10 = var22_16;
lbl170: // 2 sources:
                        if (var21_15 != var20_14) {
                            if ((var3_3 & 256) == 0) {
                                if (var21_15 >= var20_14) return 1;
                                return -1;
                            }
                            if (var21_15 >= var20_14) return -1;
                            return 1;
                        }
                        if (var19_13 >>> 16 != 256) continue block14;
                        break block14;
                    }
                    var20_14 = var11_8;
                } while (true);
                break;
            } while (true);
        }
        if (CollationSettings.getStrength(var3_3) <= 1) {
            return 0;
        }
        var24_18 = CollationSettings.getTertiaryMask(var3_3);
        var20_14 = 0;
        var11_8 = 0;
        var19_13 = 0;
        var14_10 = var23_17;
        block19 : do {
            var21_15 = var20_14 + 1;
            var23_17 = (int)var0.getCE(var20_14);
            var22_16 = var19_13 | var23_17;
            var19_13 = var23_17 & var24_18;
            if (var19_13 == 0) {
                var20_14 = var21_15;
                var19_13 = var22_16;
                continue;
            }
            var14_10 = var11_8;
            do {
                var11_8 = var14_10 + 1;
                var25_19 = (int)var1_1.getCE(var14_10);
                var22_16 |= var25_19;
                var20_14 = var25_19 & var24_18;
                if (var20_14 != 0) {
                    if (var19_13 != var20_14) {
                        var14_10 = var20_14;
                        var6_5 = var19_13;
                        if (CollationSettings.sortsTertiaryUpperCaseFirst(var3_3)) {
                            var11_8 = var19_13;
                            if (var19_13 > 256) {
                                var11_8 = (var23_17 & -65536) != 0 ? var19_13 ^ 49152 : var19_13 + 16384;
                            }
                            var14_10 = var20_14;
                            var6_5 = var11_8;
                            if (var20_14 > 256) {
                                if ((-65536 & var25_19) != 0) {
                                    var14_10 = var20_14 ^ 49152;
                                    var6_5 = var11_8;
                                } else {
                                    var14_10 = var20_14 + 16384;
                                    var6_5 = var11_8;
                                }
                            }
                        }
                        if (var6_5 >= var14_10) return 1;
                        return -1;
                    }
                    if (var19_13 == 256) {
                        if (CollationSettings.getStrength(var3_3) <= 2) {
                            return 0;
                        }
                        if (var6_5 == 0 && (var22_16 & 192) == 0) {
                            return 0;
                        }
                        break block19;
                    }
                    var20_14 = var21_15;
                    var14_10 = 49152;
                    var19_13 = var22_16;
                    continue block19;
                }
                var14_10 = var11_8;
            } while (true);
            break;
        } while (true);
        var14_10 = 0;
        var11_8 = 0;
        block21 : do {
            var6_5 = var14_10 + 1;
            var12_9 = var0.getCE(var14_10);
            var4_4 = var12_9 & 65535L;
            var12_9 = var4_4 <= 256L ? (var12_9 >>>= 32) : var4_4 | 0xFFFFFF3FL;
            if (var12_9 == 0L) {
                var14_10 = var6_5;
                continue;
            }
            var14_10 = var11_8;
            do {
                var11_8 = var14_10 + 1;
                var4_4 = var1_1.getCE(var14_10);
                var7_6 = var4_4 & 65535L;
                var4_4 = var7_6 <= 256L ? (var4_4 >>>= 32) : var7_6 | 0xFFFFFF3FL;
                if (var4_4 != 0L) {
                    if (var12_9 != var4_4) {
                        var9_7 = var12_9;
                        var7_6 = var4_4;
                        if (var2_2.hasReordering()) {
                            var9_7 = var2_2.reorder(var12_9);
                            var7_6 = var2_2.reorder(var4_4);
                        }
                        if (var9_7 >= var7_6) return 1;
                        return -1;
                    }
                    if (var12_9 == 1L) {
                        return 0;
                    }
                    var14_10 = var6_5;
                    continue block21;
                }
                var14_10 = var11_8;
            } while (true);
            break;
        } while (true);
    }
}

