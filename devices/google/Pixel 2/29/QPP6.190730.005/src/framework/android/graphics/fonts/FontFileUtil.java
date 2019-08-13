/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.fonts;

import android.graphics.fonts.FontVariationAxis;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FontFileUtil {
    private static final int ANALYZE_ERROR = -1;
    private static final int OS2_TABLE_TAG = 1330851634;
    private static final int SFNT_VERSION_1 = 65536;
    private static final int SFNT_VERSION_OTTO = 1330926671;
    private static final int TTC_TAG = 1953784678;

    private FontFileUtil() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static final int analyzeStyle(ByteBuffer var0, int var1_1, FontVariationAxis[] var2_2) {
        block21 : {
            block20 : {
                var3_4 = false;
                if (var2_2 /* !! */  == null) {
                    var9_10 = -1;
                    var8_9 = -1;
                } else {
                    var4_5 = var2_2 /* !! */ .length;
                    var5_6 = -1;
                    var6_7 = -1;
                    var7_8 = 0;
                    do {
                        var8_9 = var6_7;
                        var9_10 = var5_6;
                        if (var7_8 >= var4_5) break;
                        var10_11 = var2_2 /* !! */ [var7_8];
                        if ("wght".equals(var10_11.getTag())) {
                            var8_9 = (int)var10_11.getStyleValue();
                        } else {
                            var8_9 = var6_7;
                            if ("ital".equals(var10_11.getTag())) {
                                var8_9 = var10_11.getStyleValue() == 1.0f ? 1 : 0;
                                var5_6 = var8_9;
                                var8_9 = var6_7;
                            }
                        }
                        ++var7_8;
                        var6_7 = var8_9;
                    } while (true);
                }
                if (var8_9 != -1 && var9_10 != -1) {
                    if (var9_10 != 1) return FontFileUtil.pack(var8_9, var3_4);
                    var3_4 = true;
                    return FontFileUtil.pack(var8_9, var3_4);
                }
                var2_3 = var0.order();
                var0.order(ByteOrder.BIG_ENDIAN);
                var6_7 = 0;
                if (var0.getInt(0) != 1953784678) ** GOTO lbl44
                var6_7 = var0.getInt(8);
                if (var1_1 < var6_7) break block20;
                var0.order(var2_3);
                return -1;
            }
            var6_7 = var0.getInt(var1_1 * 4 + 12);
lbl44: // 2 sources:
            if ((var1_1 = var0.getInt(var6_7)) == 65536 || var1_1 == 1330926671) break block21;
            var0.order(var2_3);
            return -1;
        }
        var4_5 = var0.getShort(var6_7 + 4);
        var7_8 = -1;
        var5_6 = 0;
        do {
            var1_1 = var7_8;
            if (var5_6 >= var4_5) break;
            var1_1 = var6_7 + 12 + var5_6 * 16;
            if (var0.getInt(var1_1) == 1330851634) {
                var1_1 = var0.getInt(var1_1 + 8);
                break;
            }
            ++var5_6;
            continue;
            break;
        } while (true);
        if (var1_1 == -1) {
            var1_1 = FontFileUtil.pack(400, false);
            return var1_1;
        }
        var6_7 = var0.getShort(var1_1 + 4);
        var3_4 = (var0.getShort(var1_1 + 62) & 1) != 0;
        var1_1 = var8_9 == -1 ? var6_7 : var8_9;
        if (var9_10 != -1) {
            var3_4 = true;
            if (var9_10 != 1) {
                var3_4 = false;
            }
        }
        var1_1 = FontFileUtil.pack(var1_1, var3_4);
        var0.order(var2_3);
        return var1_1;
        finally {
            var0.order(var2_3);
        }
    }

    public static boolean isSuccess(int n) {
        boolean bl = n != -1;
        return bl;
    }

    private static int pack(int n, boolean bl) {
        int n2 = bl ? 65536 : 0;
        return n2 | n;
    }

    public static boolean unpackItalic(int n) {
        boolean bl = (65536 & n) != 0;
        return bl;
    }

    public static int unpackWeight(int n) {
        return 65535 & n;
    }
}

