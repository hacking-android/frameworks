/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.util.regex.PatternSyntaxException;

public class Globs {
    private static char EOL = '\u0000';
    private static final String globMetaChars = "\\*?[{";
    private static final String regexMetaChars = ".^$+{[]|()";

    static {
        EOL = (char)(false ? 1 : 0);
    }

    private Globs() {
    }

    private static boolean isGlobMeta(char c) {
        boolean bl = globMetaChars.indexOf(c) != -1;
        return bl;
    }

    private static boolean isRegexMeta(char c) {
        boolean bl = regexMetaChars.indexOf(c) != -1;
        return bl;
    }

    private static char next(String string, int n) {
        if (n < string.length()) {
            return string.charAt(n);
        }
        return EOL;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static String toRegexPattern(String var0, boolean var1_1) {
        var2_2 = '\u0000';
        var3_3 = new StringBuilder("^");
        var4_4 = '\u0000';
        do {
            block31 : {
                block33 : {
                    block23 : {
                        block24 : {
                            block25 : {
                                block26 : {
                                    block27 : {
                                        block34 : {
                                            block28 : {
                                                block32 : {
                                                    block29 : {
                                                        block30 : {
                                                            if (var4_4 >= var0.length()) {
                                                                if (var2_2 != '\u0000') throw new PatternSyntaxException("Missing '}", var0, var4_4 - 1);
                                                                var3_3.append('$');
                                                                return var3_3.toString();
                                                            }
                                                            var5_5 = var4_4 + 1;
                                                            var6_6 = var0.charAt(var4_4);
                                                            if (var6_6 == '*') break block23;
                                                            if (var6_6 == ',') break block24;
                                                            if (var6_6 == '/') break block25;
                                                            if (var6_6 == '?') break block26;
                                                            if (var6_6 == '{') break block27;
                                                            if (var6_6 == '}') break block28;
                                                            if (var6_6 == '[') break block29;
                                                            if (var6_6 == '\\') break block30;
                                                            if (Globs.isRegexMeta(var6_6)) {
                                                                var3_3.append('\\');
                                                            }
                                                            var3_3.append(var6_6);
                                                            ** GOTO lbl113
                                                        }
                                                        if (var5_5 == var0.length()) throw new PatternSyntaxException("No character to escape", var0, var5_5 - 1);
                                                        var6_6 = var0.charAt(var5_5);
                                                        if (Globs.isGlobMeta(var6_6) || Globs.isRegexMeta(var6_6)) {
                                                            var3_3.append('\\');
                                                        }
                                                        var3_3.append(var6_6);
                                                        var4_4 = var2_2;
                                                        break block31;
                                                    }
                                                    if (var1_1) {
                                                        var3_3.append("[[^\\\\]&&[");
                                                    } else {
                                                        var3_3.append("[[^/]&&[");
                                                    }
                                                    if (Globs.next(var0, var5_5) != '^') break block32;
                                                    var3_3.append("\\^");
                                                    ++var5_5;
                                                    break block33;
                                                }
                                                var4_4 = var5_5;
                                                if (Globs.next(var0, var5_5) == '!') {
                                                    var3_3.append('^');
                                                    var4_4 = var5_5 + 1;
                                                }
                                                var5_5 = var4_4;
                                                if (Globs.next(var0, var4_4) != '-') break block33;
                                                var3_3.append('-');
                                                var5_5 = var4_4 + 1;
                                                break block33;
                                            }
                                            if (var2_2 == '\u0000') break block34;
                                            var3_3.append("))");
                                            var4_4 = '\u0000';
                                            break block31;
                                        }
                                        var3_3.append('}');
                                        ** GOTO lbl113
                                    }
                                    if (var2_2 != '\u0000') throw new PatternSyntaxException("Cannot nest groups", var0, var5_5 - 1);
                                    var3_3.append("(?:(?:");
                                    var4_4 = '\u0001';
                                    break block31;
                                }
                                if (var1_1) {
                                    var3_3.append("[^\\\\]");
                                } else {
                                    var3_3.append("[^/]");
                                }
                                ** GOTO lbl113
                            }
                            if (var1_1) {
                                var3_3.append("\\\\");
                            } else {
                                var3_3.append(var6_6);
                            }
                            ** GOTO lbl113
                        }
                        if (var2_2 != '\u0000') {
                            var3_3.append(")|(?:");
                        } else {
                            var3_3.append(',');
                        }
                        ** GOTO lbl113
                    }
                    if (Globs.next(var0, var5_5) == '*') {
                        var3_3.append(".*");
                        ++var5_5;
                        var4_4 = var2_2;
                    } else {
                        if (var1_1) {
                            var3_3.append("[^\\\\]*");
                        } else {
                            var3_3.append("[^/]*");
                        }
lbl113: // 10 sources:
                        var4_4 = var2_2;
                    }
                    break block31;
                }
                var7_7 = false;
                var8_8 = '\u0000';
                var4_4 = var6_6;
                do {
                    var9_9 = var4_4;
                    var4_4 = var5_5;
                    if (var5_5 >= var0.length()) break;
                    var4_4 = var5_5 + 1;
                    var6_6 = var0.charAt(var5_5);
                    if (var6_6 == ']') {
                        var9_9 = var6_6;
                        break;
                    }
                    if (var6_6 == '/') throw new PatternSyntaxException("Explicit 'name separator' in class", var0, var4_4 - 1);
                    if (var1_1) {
                        if (var6_6 == '\\') throw new PatternSyntaxException("Explicit 'name separator' in class", var0, var4_4 - 1);
                    }
                    if (var6_6 == '\\' || var6_6 == '[' || var6_6 == '&' && Globs.next(var0, var4_4) == '&') {
                        var3_3.append('\\');
                    }
                    var3_3.append(var6_6);
                    if (var6_6 == '-') {
                        if (var7_7 == false) throw new PatternSyntaxException("Invalid range", var0, var4_4 - 1);
                        var5_5 = var4_4 + 1;
                        var6_6 = var4_4 = (char)Globs.next(var0, var4_4);
                        if (var4_4 != Globs.EOL && var6_6 != ']') {
                            if (var6_6 < var8_8) throw new PatternSyntaxException("Invalid range", var0, var5_5 - 3);
                            var3_3.append(var6_6);
                            var7_7 = false;
                            var4_4 = var6_6;
                            continue;
                        }
                        var9_9 = var6_6;
                        var4_4 = var5_5;
                        break;
                    }
                    var7_7 = true;
                    var8_8 = var6_6;
                    var5_5 = var4_4;
                    var4_4 = var6_6;
                } while (true);
                if (var9_9 != ']') throw new PatternSyntaxException("Missing ']", var0, var4_4 - 1);
                var3_3.append("]]");
                var5_5 = var4_4;
                var4_4 = var2_2;
            }
            var2_2 = var4_4;
            var4_4 = ++var5_5;
        } while (true);
    }

    static String toUnixRegexPattern(String string) {
        return Globs.toRegexPattern(string, false);
    }

    static String toWindowsRegexPattern(String string) {
        return Globs.toRegexPattern(string, true);
    }
}

