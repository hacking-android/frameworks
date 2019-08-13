/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MessageUtils {
    private static final boolean DBG = false;
    public static final String[] DEFAULT_PREFIXES;
    private static final String TAG;

    static {
        TAG = MessageUtils.class.getSimpleName();
        DEFAULT_PREFIXES = new String[]{"CMD_", "EVENT_"};
    }

    public static SparseArray<String> findMessageNames(Class[] arrclass) {
        return MessageUtils.findMessageNames(arrclass, DEFAULT_PREFIXES);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static SparseArray<String> findMessageNames(Class[] var0, String[] var1_1) {
        var2_2 = new SparseArray<String>();
        var3_3 = var0.length;
        var4_4 = 0;
        block6 : do {
            if (var4_4 >= var3_3) return var2_2;
            var5_6 = var0[var4_4];
            var6_10 = var5_6.getName();
            try {
                var5_7 = var5_6.getDeclaredFields();
            }
            catch (SecurityException var5_8) {
                var5_9 = MessageUtils.TAG;
                var10_15 = new StringBuilder();
                var10_15.append("Can't list fields of class ");
                var10_15.append((String)var6_10);
                Log.e(var5_9, var10_15.toString());
                ** GOTO lbl31
            }
            var7_12 = var5_7.length;
            var8_13 = 0;
            do {
                block11 : {
                    block12 : {
                        block10 : {
                            if (var8_13 >= var7_12) break block10;
                            var6_10 = var5_7[var8_13];
                            var9_14 = var6_10.getModifiers();
                            if (Modifier.isStatic(var9_14) ^ true | Modifier.isFinal(var9_14) ^ true) break block11;
                            var10_15 = var6_10.getName();
                            var11_16 = var1_1.length;
                            break block12;
                        }
                        ++var4_4;
                        continue block6;
                    }
                    for (var9_14 = 0; var9_14 < var11_16; ++var9_14) {
                        if (!var10_15.startsWith(var1_1[var9_14])) continue;
                        try {
                            var6_10.setAccessible(true);
                            try {
                                var12_17 = var6_10.getInt(null);
                            }
                            catch (ExceptionInInitializerError | IllegalArgumentException var6_11) {
                                break;
                            }
                            var13_18 = var2_2.get(var12_17);
                            if (var13_18 != null && !var13_18.equals(var10_15)) {
                                var14_20 = new DuplicateConstantError((String)var10_15, var13_18, var12_17);
                                throw var14_20;
                            }
                            var2_2.put(var12_17, (String)var10_15);
                            continue;
                        }
                        catch (IllegalAccessException | SecurityException var13_19) {
                            // empty catch block
                        }
                    }
                }
                ++var8_13;
            } while (true);
            break;
        } while (true);
    }

    public static class DuplicateConstantError
    extends Error {
        private DuplicateConstantError() {
        }

        public DuplicateConstantError(String string2, String string3, int n) {
            super(String.format("Duplicate constant value: both %s and %s = %d", string2, string3, n));
        }
    }

}

