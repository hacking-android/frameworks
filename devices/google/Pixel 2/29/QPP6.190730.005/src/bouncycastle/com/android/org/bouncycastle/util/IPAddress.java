/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

public class IPAddress {
    private static boolean isMaskValue(String string, int n) {
        boolean bl = false;
        try {
            int n2 = Integer.parseInt(string);
            boolean bl2 = bl;
            if (n2 >= 0) {
                bl2 = bl;
                if (n2 <= n) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static boolean isValid(String string) {
        boolean bl = IPAddress.isValidIPv4(string) || IPAddress.isValidIPv6(string);
        return bl;
    }

    public static boolean isValidIPv4(String string) {
        int n;
        int n2 = string.length();
        boolean bl = false;
        if (n2 == 0) {
            return false;
        }
        n2 = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".");
        string = stringBuilder.toString();
        int n3 = 0;
        while (n3 < string.length() && (n = string.indexOf(46, n3)) > n3) {
            if (n2 == 4) {
                return false;
            }
            try {
                n3 = Integer.parseInt(string.substring(n3, n));
                if (n3 >= 0 && n3 <= 255) {
                    n3 = n + 1;
                    ++n2;
                    continue;
                }
                return false;
            }
            catch (NumberFormatException numberFormatException) {
                return false;
            }
        }
        if (n2 == 4) {
            bl = true;
        }
        return bl;
    }

    public static boolean isValidIPv4WithNetmask(String string) {
        boolean bl;
        block0 : {
            int n = string.indexOf("/");
            String string2 = string.substring(n + 1);
            bl = false;
            if (n <= 0 || !IPAddress.isValidIPv4(string.substring(0, n)) || !IPAddress.isValidIPv4(string2) && !IPAddress.isMaskValue(string2, 32)) break block0;
            bl = true;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean isValidIPv6(String var0) {
        var1_2 = var0.length();
        var2_3 = false;
        if (var1_2 == 0) {
            return false;
        }
        var1_2 = 0;
        var3_4 = new StringBuilder();
        var3_4.append(var0);
        var3_4.append(":");
        var3_4 = var3_4.toString();
        var4_5 = false;
        var5_6 = 0;
        while (var5_6 < var3_4.length() && (var6_7 = var3_4.indexOf(58, var5_6)) >= var5_6) {
            block10 : {
                if (var1_2 == 8) {
                    return false;
                }
                if (var5_6 == var6_7) ** GOTO lbl32
                var0 = var3_4.substring(var5_6, var6_7);
                if (var6_7 == var3_4.length() - 1 && var0.indexOf(46) > 0) {
                    if (!IPAddress.isValidIPv4(var0)) {
                        return false;
                    }
                    ++var1_2;
                } else {
                    try {
                        var5_6 = Integer.parseInt(var3_4.substring(var5_6, var6_7), 16);
                        if (var5_6 < 0) return false;
                        if (var5_6 > 65535) {
                            return false;
                        }
                        break block10;
                    }
                    catch (NumberFormatException var0_1) {
                        return false;
                    }
lbl32: // 1 sources:
                    if (var6_7 != 1 && var6_7 != var3_4.length() - 1 && var4_5) {
                        return false;
                    }
                    var4_5 = true;
                }
            }
            var5_6 = var6_7 + 1;
            ++var1_2;
        }
        if (var1_2 == 8) return true;
        if (var4_5 == false) return var2_3;
        return true;
    }

    public static boolean isValidIPv6WithNetmask(String string) {
        boolean bl;
        block0 : {
            int n = string.indexOf("/");
            String string2 = string.substring(n + 1);
            bl = false;
            if (n <= 0 || !IPAddress.isValidIPv6(string.substring(0, n)) || !IPAddress.isValidIPv6(string2) && !IPAddress.isMaskValue(string2, 128)) break block0;
            bl = true;
        }
        return bl;
    }

    public static boolean isValidWithNetMask(String string) {
        boolean bl = IPAddress.isValidIPv4WithNetmask(string) || IPAddress.isValidIPv6WithNetmask(string);
        return bl;
    }
}

