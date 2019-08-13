/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import java.util.Locale;

public class Utils {
    public static String chineseNumber(long l, ChineseDigits chineseDigits) {
        int n;
        char[] arrc;
        int n2;
        int n3;
        int n4;
        block28 : {
            block29 : {
                long l2;
                l = l2 = l;
                if (l2 < 0L) {
                    l = -l2;
                }
                if (l <= 10L) {
                    if (l == 2L) {
                        return String.valueOf(chineseDigits.liang);
                    }
                    return String.valueOf(chineseDigits.digits[(int)l]);
                }
                arrc = new char[40];
                char[] arrc2 = String.valueOf(l).toCharArray();
                n2 = 1;
                n3 = 0;
                n4 = arrc.length;
                int n5 = arrc2.length;
                int n6 = -1;
                int n7 = -1;
                do {
                    int n8 = 1;
                    if (--n5 < 0) break;
                    if (n6 == -1) {
                        n = n4;
                        if (n7 != -1) {
                            n = n4 - 1;
                            arrc[n] = chineseDigits.levels[n7];
                            n2 = 1;
                            n3 = 0;
                        }
                        n4 = n;
                        n = ++n6;
                    } else {
                        char[] arrc3 = chineseDigits.units;
                        n = n6 + 1;
                        arrc[--n4] = arrc3[n6];
                        if (n == 3) {
                            n = -1;
                            ++n7;
                        }
                    }
                    n6 = arrc2[n5] - 48;
                    if (n6 == 0) {
                        if (n4 < arrc.length - 1 && n != 0) {
                            arrc[n4] = (char)42;
                        }
                        if (n2 == 0 && n3 == 0) {
                            n6 = n4 - 1;
                            arrc[n6] = chineseDigits.digits[0];
                            n2 = 1;
                            n4 = n == 1 ? n8 : 0;
                            n3 = n4;
                            n4 = n6;
                        } else {
                            arrc[--n4] = (char)42;
                        }
                    } else {
                        n2 = 0;
                        arrc[--n4] = chineseDigits.digits[n6];
                    }
                    n6 = n;
                } while (true);
                if (l > 1000000L) {
                    n = 1;
                    n2 = arrc.length - 3;
                    while (arrc[n2] != '0') {
                        n7 = n2 - 8;
                        n3 = n == 0 ? 1 : 0;
                        n = n3;
                        n2 = n7;
                        if (n7 > n4) continue;
                        n = n3;
                        break;
                    }
                    n3 = arrc.length - 7;
                    do {
                        if (arrc[n3] == chineseDigits.digits[0] && n == 0) {
                            arrc[n3] = (char)42;
                        }
                        n2 = n3 - 8;
                        n = n == 0 ? 1 : 0;
                        n3 = n2;
                    } while (n2 > n4);
                    if (l >= 100000000L) {
                        n = arrc.length - 8;
                        do {
                            n7 = 1;
                            n3 = n - 1;
                            n6 = Math.max(n4 - 1, n - 8);
                            do {
                                n2 = n7;
                                if (n3 <= n6) break;
                                if (arrc[n3] != '*') {
                                    n2 = 0;
                                    break;
                                }
                                --n3;
                            } while (true);
                            if (n2 != 0) {
                                arrc[n] = arrc[n + 1] != '*' && arrc[n + 1] != chineseDigits.digits[0] ? (char)chineseDigits.digits[0] : (char)42;
                            }
                            n = n3 = n - 8;
                        } while (n3 > n4);
                    }
                }
                for (n = n4; n < arrc.length; ++n) {
                    if (arrc[n] != chineseDigits.digits[2] || n < arrc.length - 1 && arrc[n + 1] == chineseDigits.units[0] || n > n4 && (arrc[n - 1] == chineseDigits.units[0] || arrc[n - 1] == chineseDigits.digits[0] || arrc[n - 1] == '*')) continue;
                    arrc[n] = chineseDigits.liang;
                }
                n = n4;
                if (arrc[n4] != chineseDigits.digits[1]) break block28;
                if (chineseDigits.ko) break block29;
                n = n4;
                if (arrc[n4 + 1] != chineseDigits.units[0]) break block28;
            }
            n = n4 + 1;
        }
        n3 = n;
        for (n4 = n; n4 < arrc.length; ++n4) {
            n2 = n3;
            if (arrc[n4] != '*') {
                arrc[n3] = arrc[n4];
                n2 = n3 + 1;
            }
            n3 = n2;
        }
        return new String(arrc, n, n3 - n);
    }

    public static final Locale localeFromString(String string) {
        String string2 = string;
        string = "";
        String string3 = "";
        int n = string2.indexOf("_");
        String string4 = string2;
        if (n != -1) {
            string = string2.substring(n + 1);
            string4 = string2.substring(0, n);
        }
        n = string.indexOf("_");
        String string5 = string;
        string2 = string3;
        if (n != -1) {
            string2 = string.substring(n + 1);
            string5 = string.substring(0, n);
        }
        return new Locale(string4, string5, string2);
    }

    public static class ChineseDigits {
        public static final ChineseDigits DEBUG = new ChineseDigits("0123456789s", "sbq", "WYZ", 'L', false);
        public static final ChineseDigits KOREAN;
        public static final ChineseDigits SIMPLIFIED;
        public static final ChineseDigits TRADITIONAL;
        final char[] digits;
        final boolean ko;
        final char[] levels;
        final char liang;
        final char[] units;

        static {
            TRADITIONAL = new ChineseDigits("\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341", "\u5341\u767e\u5343", "\u842c\u5104\u5146", '\u5169', false);
            SIMPLIFIED = new ChineseDigits("\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341", "\u5341\u767e\u5343", "\u4e07\u4ebf\u5146", '\u4e24', false);
            KOREAN = new ChineseDigits("\uc601\uc77c\uc774\uc0bc\uc0ac\uc624\uc721\uce60\ud314\uad6c\uc2ed", "\uc2ed\ubc31\ucc9c", "\ub9cc\uc5b5?", '\uc774', true);
        }

        ChineseDigits(String string, String string2, String string3, char c, boolean bl) {
            this.digits = string.toCharArray();
            this.units = string2.toCharArray();
            this.levels = string3.toCharArray();
            this.liang = c;
            this.ko = bl;
        }
    }

}

