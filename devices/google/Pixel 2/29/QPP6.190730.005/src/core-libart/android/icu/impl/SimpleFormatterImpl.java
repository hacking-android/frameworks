/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public final class SimpleFormatterImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ARG_NUM_LIMIT = 256;
    private static final String[][] COMMON_PATTERNS = new String[][]{{"{0} {1}", "\u0002\u0000\u0101 \u0001"}, {"{0} ({1})", "\u0002\u0000\u0102 (\u0001\u0101)"}, {"{0}, {1}", "\u0002\u0000\u0102, \u0001"}, {"{0} \u2013 {1}", "\u0002\u0000\u0103 \u2013 \u0001"}};
    private static final char LEN1_CHAR = '\u0101';
    private static final char LEN2_CHAR = '\u0102';
    private static final char LEN3_CHAR = '\u0103';
    private static final int MAX_SEGMENT_LENGTH = 65279;
    private static final char SEGMENT_LENGTH_ARGUMENT_CHAR = '\uffff';

    private SimpleFormatterImpl() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String compileToStringMinMaxArguments(CharSequence var0, StringBuilder var1_1, int var2_2, int var3_3) {
        if (var2_2 <= 2 && 2 <= var3_3) {
            for (String[] var7_7 : SimpleFormatterImpl.COMMON_PATTERNS) {
                if (!var7_7[0].contentEquals(var0)) continue;
                return var7_7[1];
            }
        }
        var8_8 = var0.length();
        var1_1.ensureCapacity(var8_8);
        var1_1.setLength(1);
        var5_5 = 0;
        var9_9 = -1;
        var10_10 = 0;
        var6_6 = 0;
        do {
            block22 : {
                block17 : {
                    block23 : {
                        block24 : {
                            block18 : {
                                block20 : {
                                    block21 : {
                                        block19 : {
                                            if (var6_6 >= var8_8) break block18;
                                            var11_11 = var6_6 + 1;
                                            var12_12 = var0.charAt(var6_6);
                                            if (var12_12 != 39) break block19;
                                            if (var11_11 >= var8_8) ** GOTO lbl-1000
                                            var13_13 = var0.charAt(var11_11);
                                            var12_12 = var6_6 = (int)var13_13;
                                            if (var13_13 == 39) {
                                                var13_13 = var10_10;
                                                var14_14 = var6_6;
                                                var6_6 = ++var11_11;
                                            } else lbl-1000: // 2 sources:
                                            {
                                                if (var10_10 != 0) {
                                                    var10_10 = 0;
                                                    var6_6 = var11_11;
                                                    continue;
                                                }
                                                if (var12_12 != 123 && var12_12 != 125) {
                                                    var6_6 = 39;
                                                    var13_13 = var10_10;
                                                    var14_14 = var6_6;
                                                    var6_6 = var11_11;
                                                } else {
                                                    var6_6 = var11_11 + 1;
                                                    var13_13 = 1;
                                                    var14_14 = var12_12;
                                                }
                                            }
                                            break block20;
                                        }
                                        var13_13 = var10_10;
                                        var14_14 = var12_12;
                                        var6_6 = var11_11;
                                        if (var10_10 != 0) break block20;
                                        var13_13 = var10_10;
                                        var14_14 = var12_12;
                                        var6_6 = var11_11;
                                        if (var12_12 != 123) break block20;
                                        var15_15 = var5_5;
                                        if (var5_5 > 0) {
                                            var1_1.setCharAt(var1_1.length() - var5_5 - 1, (char)(var5_5 + 256));
                                            var15_15 = 0;
                                        }
                                        if (var11_11 + 1 >= var8_8) break block21;
                                        var5_5 = var6_6 = var0.charAt(var11_11) - 48;
                                        if (var6_6 < 0 || var5_5 > 9 || var0.charAt(var11_11 + 1) != '}') break block21;
                                        var6_6 = var11_11 + 2;
                                        break block22;
                                    }
                                    var16_16 = var11_11 - 1;
                                    var17_17 = -1;
                                    var13_13 = var12_12;
                                    var5_5 = var11_11;
                                    var6_6 = var17_17;
                                    if (var11_11 >= var8_8) break block17;
                                    var18_18 = var11_11 + 1;
                                    var13_13 = var6_6 = (var11_11 = (int)var0.charAt(var11_11));
                                    var5_5 = var17_17;
                                    var12_12 = var18_18;
                                    if (49 > var11_11) break block23;
                                    var13_13 = var6_6;
                                    var5_5 = var17_17;
                                    var12_12 = var18_18;
                                    if (var6_6 > 57) break block23;
                                    break block24;
                                }
                                if (var5_5 == 0) {
                                    var1_1.append('\uffff');
                                }
                                var1_1.append((char)var14_14);
                                var5_5 = var11_11 = var5_5 + 1;
                                if (var11_11 == 65279) {
                                    var5_5 = 0;
                                }
                                var10_10 = var13_13;
                                continue;
                            }
                            if (var5_5 > 0) {
                                var1_1.setCharAt(var1_1.length() - var5_5 - 1, (char)(var5_5 + 256));
                            }
                            if ((var6_6 = var9_9 + 1) < var2_2) {
                                var1_1 = new StringBuilder();
                                var1_1.append("Fewer than minimum ");
                                var1_1.append(var2_2);
                                var1_1.append(" arguments in pattern \"");
                                var1_1.append((Object)var0);
                                var1_1.append("\"");
                                throw new IllegalArgumentException(var1_1.toString());
                            }
                            if (var6_6 <= var3_3) {
                                var1_1.setCharAt(0, (char)var6_6);
                                return var1_1.toString();
                            }
                            var1_1 = new StringBuilder();
                            var1_1.append("More than maximum ");
                            var1_1.append(var3_3);
                            var1_1.append(" arguments in pattern \"");
                            var1_1.append((Object)var0);
                            var1_1.append("\"");
                            throw new IllegalArgumentException(var1_1.toString());
                        }
                        var11_11 = var6_6 - 48;
                        var12_12 = var18_18;
                        do {
                            var13_13 = var6_6;
                            var5_5 = var12_12;
                            var6_6 = var11_11;
                            if (var12_12 >= var8_8) break block17;
                            var17_17 = var12_12 + 1;
                            var13_13 = var6_6 = (var18_18 = (int)var0.charAt(var12_12));
                            var5_5 = var11_11;
                            var12_12 = var17_17;
                            if (48 > var18_18) break;
                            var13_13 = var6_6;
                            var5_5 = var11_11;
                            var12_12 = var17_17;
                            if (var6_6 > 57) break;
                            if ((var11_11 = var11_11 * 10 + (var6_6 - 48)) >= 256) {
                                var5_5 = var17_17;
                                var13_13 = var6_6;
                                var6_6 = var11_11;
                                break block17;
                            }
                            var12_12 = var17_17;
                        } while (true);
                    }
                    var6_6 = var5_5;
                    var5_5 = var12_12;
                }
                if (var6_6 < 0 || var13_13 != 125) break;
                var11_11 = var5_5;
                var5_5 = var6_6;
                var6_6 = var11_11;
            }
            var11_11 = var9_9;
            if (var5_5 > var9_9) {
                var11_11 = var5_5;
            }
            var1_1.append((char)var5_5);
            var5_5 = var15_15;
            var9_9 = var11_11;
        } while (true);
        var1_1 = new StringBuilder();
        var1_1.append("Argument syntax error in pattern \"");
        var1_1.append((Object)var0);
        var1_1.append("\" at index ");
        var1_1.append(var16_16);
        var1_1.append(": ");
        var1_1.append((Object)var0.subSequence(var16_16, var5_5));
        throw new IllegalArgumentException(var1_1.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static StringBuilder format(String string, CharSequence[] arrcharSequence, StringBuilder stringBuilder, String string2, boolean bl, int[] arrn) {
        int n;
        int n2;
        int n3;
        if (arrn == null) {
            n3 = 0;
        } else {
            n2 = arrn.length;
            n = 0;
            do {
                n3 = n2;
                if (n >= n2) break;
                arrn[n] = -1;
                ++n;
            } while (true);
        }
        n = 1;
        while (n < string.length()) {
            n2 = n + 1;
            if ((n = (int)string.charAt(n)) < 256) {
                CharSequence charSequence = arrcharSequence[n];
                if (charSequence == stringBuilder) {
                    if (bl) throw new IllegalArgumentException("Value must not be same object as result");
                    if (n2 == 2) {
                        if (n < n3) {
                            arrn[n] = 0;
                        }
                    } else {
                        if (n < n3) {
                            arrn[n] = stringBuilder.length();
                        }
                        stringBuilder.append(string2);
                    }
                } else {
                    if (n < n3) {
                        arrn[n] = stringBuilder.length();
                    }
                    stringBuilder.append(charSequence);
                }
                n = n2;
                continue;
            }
            n = n - 256 + n2;
            stringBuilder.append(string, n2, n);
        }
        return stringBuilder;
    }

    public static StringBuilder formatAndAppend(String string, StringBuilder stringBuilder, int[] arrn, CharSequence ... arrcharSequence) {
        int n = arrcharSequence != null ? arrcharSequence.length : 0;
        if (n >= SimpleFormatterImpl.getArgumentLimit(string)) {
            return SimpleFormatterImpl.format(string, arrcharSequence, stringBuilder, null, true, arrn);
        }
        throw new IllegalArgumentException("Too few values.");
    }

    public static StringBuilder formatAndReplace(String string, StringBuilder stringBuilder, int[] arrn, CharSequence ... arrcharSequence) {
        int n = arrcharSequence != null ? arrcharSequence.length : 0;
        if (n >= SimpleFormatterImpl.getArgumentLimit(string)) {
            int n2 = -1;
            String string2 = null;
            if (SimpleFormatterImpl.getArgumentLimit(string) > 0) {
                int n3 = 1;
                while (n3 < string.length()) {
                    n = n3 + 1;
                    if ((n3 = (int)string.charAt(n3)) < 256) {
                        if (arrcharSequence[n3] == stringBuilder) {
                            if (n == 2) {
                                n2 = n3;
                            } else if (string2 == null) {
                                string2 = stringBuilder.toString();
                            }
                        }
                    } else {
                        n += n3 - 256;
                    }
                    n3 = n;
                }
            } else {
                string2 = null;
            }
            if (n2 < 0) {
                stringBuilder.setLength(0);
            }
            return SimpleFormatterImpl.format(string, arrcharSequence, stringBuilder, string2, false, arrn);
        }
        throw new IllegalArgumentException("Too few values.");
    }

    public static String formatCompiledPattern(String string, CharSequence ... arrcharSequence) {
        return SimpleFormatterImpl.formatAndAppend(string, new StringBuilder(), null, arrcharSequence).toString();
    }

    public static String formatRawPattern(String string, int n, int n2, CharSequence ... arrcharSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        string = SimpleFormatterImpl.compileToStringMinMaxArguments(string, stringBuilder, n, n2);
        stringBuilder.setLength(0);
        return SimpleFormatterImpl.formatAndAppend(string, stringBuilder, null, arrcharSequence).toString();
    }

    public static int getArgumentLimit(String string) {
        return string.charAt(0);
    }

    public static String getTextWithNoArguments(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length() - 1 - SimpleFormatterImpl.getArgumentLimit(string));
        int n = 1;
        while (n < string.length()) {
            int n2 = n + 1;
            if ((n = string.charAt(n) - 256) > 0) {
                n = n2 + n;
                stringBuilder.append(string, n2, n);
                continue;
            }
            n = n2;
        }
        return stringBuilder.toString();
    }
}

