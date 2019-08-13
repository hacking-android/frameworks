/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import java.lang.reflect.Field;
import java.util.Arrays;

public final class Objects {
    private Objects() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String toString(Object var0) {
        var1_4 = var0.getClass();
        var2_7 = new StringBuilder();
        var2_7.append(var1_4.getSimpleName());
        var2_7.append('[');
        var3_8 = 0;
        var4_9 = var1_4.getDeclaredFields();
        var5_10 = var4_9.length;
        var6_11 = 0;
        do lbl-1000: // 2 sources:
        {
            if (var6_11 >= var5_10) {
                var2_7.append("]");
                return var2_7.toString();
            }
            var1_6 = var4_9[var6_11];
            if ((var1_6.getModifiers() & 136) != 0) ** break block28
            var1_6.setAccessible(true);
            var7_12 = var1_6.get(var0);
            if (var3_8 <= 0) ** GOTO lbl25
            var2_7.append(',');
lbl25: // 2 sources:
            var2_7.append(var1_6.getName());
            var2_7.append('=');
            if (var7_12.getClass().isArray()) {
                if (var7_12.getClass() == boolean[].class) {
                    var2_7.append(Arrays.toString((boolean[])var7_12));
                } else if (var7_12.getClass() == byte[].class) {
                    var2_7.append(Arrays.toString((byte[])var7_12));
                } else if (var7_12.getClass() == char[].class) {
                    var2_7.append(Arrays.toString((char[])var7_12));
                } else if (var7_12.getClass() == double[].class) {
                    var2_7.append(Arrays.toString((double[])var7_12));
                } else if (var7_12.getClass() == float[].class) {
                    var2_7.append(Arrays.toString((float[])var7_12));
                } else if (var7_12.getClass() == int[].class) {
                    var2_7.append(Arrays.toString((int[])var7_12));
                } else if (var7_12.getClass() == long[].class) {
                    var2_7.append(Arrays.toString((long[])var7_12));
                } else if (var7_12.getClass() == short[].class) {
                    var2_7.append(Arrays.toString((short[])var7_12));
                } else {
                    var2_7.append(Arrays.toString((Object[])var7_12));
                }
            } else if (var7_12.getClass() == Character.class) {
                var2_7.append('\'');
                var2_7.append(var7_12);
                var2_7.append('\'');
            } else if (var7_12.getClass() == String.class) {
                var2_7.append('\"');
                var2_7.append(var7_12);
                var2_7.append('\"');
            } else {
                var2_7.append(var7_12);
            }
            ++var3_8;
            break;
        } while (true);
        catch (IllegalAccessException var0_1) {
            throw new AssertionError(var0_3);
        }
        {
            ++var6_11;
            ** while (true)
        }
        catch (IllegalAccessException var0_2) {
            // empty catch block
        }
        throw new AssertionError(var0_3);
    }
}

