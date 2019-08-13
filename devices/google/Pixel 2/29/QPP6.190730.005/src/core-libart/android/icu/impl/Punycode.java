/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.lang.UCharacter;
import android.icu.text.StringPrepParseException;
import android.icu.text.UTF16;

public final class Punycode {
    private static final int BASE = 36;
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    private static final int DAMP = 700;
    private static final char DELIMITER = '-';
    private static final char HYPHEN = '-';
    private static final int INITIAL_BIAS = 72;
    private static final int INITIAL_N = 128;
    private static final int SKEW = 38;
    private static final int SMALL_A = 97;
    private static final int SMALL_Z = 122;
    private static final int TMAX = 26;
    private static final int TMIN = 1;
    private static final int ZERO = 48;
    static final int[] basicToDigit = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    private static int adaptBias(int n, int n2, boolean bl) {
        n = bl ? (n /= 700) : (n /= 2);
        n2 = n + n / n2;
        n = 0;
        while (n2 > 455) {
            n2 /= 35;
            n += 36;
        }
        return n2 * 36 / (n2 + 38) + n;
    }

    private static char asciiCaseMap(char c, boolean bl) {
        char c2;
        if (bl) {
            c2 = c;
            if ('a' <= c) {
                c2 = c;
                if (c <= 'z') {
                    c2 = c = (char)(c - 32);
                }
            }
        } else {
            c2 = c;
            if ('A' <= c) {
                c2 = c;
                if (c <= 'Z') {
                    c2 = c = (char)(c + 32);
                }
            }
        }
        return c2;
    }

    public static StringBuilder decode(CharSequence charSequence, boolean[] arrbl) throws StringPrepParseException {
        int n;
        int n2 = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        int n3 = n2;
        do {
            n = n3;
            if (n3 <= 0) break;
            n3 = n = n3 - 1;
        } while (charSequence.charAt(n) != '-');
        int n4 = n;
        int n5 = n;
        for (int i = 0; i < n5; ++i) {
            char c = charSequence.charAt(i);
            if (Punycode.isBasic(c)) {
                stringBuilder.append(c);
                if (arrbl == null || i >= arrbl.length) continue;
                arrbl[i] = Punycode.isBasicUpperCase(c);
                continue;
            }
            throw new StringPrepParseException("Illegal char found", 0);
        }
        int n6 = 128;
        n3 = 0;
        int n7 = 72;
        int n8 = 1000000000;
        n = n5 > 0 ? n5 + 1 : 0;
        block2 : while (n < n2) {
            int n9 = 1;
            int n10 = 36;
            int n11 = n;
            int n12 = n3;
            n = n5;
            while (n11 < n2) {
                int[] arrn = basicToDigit;
                int n13 = n11 + 1;
                if ((n11 = arrn[charSequence.charAt(n11) & 255]) >= 0) {
                    if (n11 <= (Integer.MAX_VALUE - n12) / n9) {
                        n12 += n11 * n9;
                        n5 = n10 - n7;
                        if (n5 < 1) {
                            n5 = 1;
                        } else if (n10 >= n7 + 26) {
                            n5 = 26;
                        }
                        if (n11 < n5) {
                            boolean bl = n3 == 0;
                            n7 = Punycode.adaptBias(n12 - n3, ++n4, bl);
                            if (n12 / n4 <= Integer.MAX_VALUE - n6) {
                                n5 = n12 % n4;
                                if ((n6 += n12 / n4) <= 1114111 && !Punycode.isSurrogate(n6)) {
                                    n10 = Character.charCount(n6);
                                    if (n5 <= n8) {
                                        n3 = n5;
                                        n12 = n10 > 1 ? n3 : n8 + 1;
                                    } else {
                                        n3 = stringBuilder.offsetByCodePoints(n8, n5 - n8);
                                        n12 = n8;
                                    }
                                    if (arrbl != null && stringBuilder.length() + n10 <= arrbl.length) {
                                        if (n3 < stringBuilder.length()) {
                                            System.arraycopy(arrbl, n3, arrbl, n3 + n10, stringBuilder.length() - n3);
                                        }
                                        arrbl[n3] = Punycode.isBasicUpperCase(charSequence.charAt(n13 - 1));
                                        if (n10 == 2) {
                                            arrbl[n3 + 1] = false;
                                        }
                                    }
                                    if (n10 == 1) {
                                        stringBuilder.insert(n3, (char)n6);
                                    } else {
                                        stringBuilder.insert(n3, UTF16.getLeadSurrogate(n6));
                                        stringBuilder.insert(n3 + 1, UTF16.getTrailSurrogate(n6));
                                    }
                                    n3 = n5 + 1;
                                    n5 = n;
                                    n8 = n12;
                                    n = n13;
                                    continue block2;
                                }
                                throw new StringPrepParseException("Illegal char found", 1);
                            }
                            throw new StringPrepParseException("Illegal char found", 1);
                        }
                        if (n9 <= Integer.MAX_VALUE / (36 - n5)) {
                            n9 *= 36 - n5;
                            n10 += 36;
                            n11 = n13;
                            continue;
                        }
                        throw new StringPrepParseException("Illegal char found", 1);
                    }
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                throw new StringPrepParseException("Invalid char found", 0);
            }
            throw new StringPrepParseException("Illegal char found", 1);
        }
        return stringBuilder;
    }

    private static char digitToBasic(int n, boolean bl) {
        if (n < 26) {
            if (bl) {
                return (char)(n + 65);
            }
            return (char)(n + 97);
        }
        return (char)(n + 22);
    }

    public static StringBuilder encode(CharSequence charSequence, boolean[] arrbl) throws StringPrepParseException {
        int n;
        int n2;
        int n3;
        StringBuilder stringBuilder;
        int n4;
        int n5;
        int[] arrn;
        block20 : {
            n = charSequence.length();
            arrn = new int[n];
            stringBuilder = new StringBuilder(n);
            n2 = 0;
            n5 = 0;
            do {
                n3 = 0;
                if (n5 >= n) break block20;
                int n6 = charSequence.charAt(n5);
                if (Punycode.isBasic(n6)) {
                    arrn[n2] = 0;
                    n6 = arrbl != null ? (n4 = (int)Punycode.asciiCaseMap((char)n6, arrbl[n5])) : (n4 = (int)n6);
                    stringBuilder.append((char)n6);
                    n4 = n2 + 1;
                } else {
                    n4 = n3;
                    if (arrbl != null) {
                        n4 = n3;
                        if (arrbl[n5]) {
                            n4 = 1;
                        }
                    }
                    n4 <<= 31;
                    if (!UTF16.isSurrogate((char)n6)) {
                        n4 |= n6;
                    } else {
                        char c;
                        if (!UTF16.isLeadSurrogate((char)n6) || n5 + 1 >= n || !UTF16.isTrailSurrogate(c = charSequence.charAt(n5 + 1))) break;
                        ++n5;
                        n4 |= UCharacter.getCodePoint((char)n6, c);
                    }
                    arrn[n2] = n4;
                    n4 = n2 + 1;
                }
                ++n5;
                n2 = n4;
            } while (true);
            throw new StringPrepParseException("Illegal char found", 1);
        }
        int n7 = stringBuilder.length();
        if (n7 > 0) {
            stringBuilder.append('-');
        }
        n5 = 128;
        n4 = 0;
        int n8 = 72;
        n = n7;
        while (n < n2) {
            int n9;
            int n10;
            int n11;
            n3 = Integer.MAX_VALUE;
            for (n9 = 0; n9 < n2; ++n9) {
                n11 = Integer.MAX_VALUE & arrn[n9];
                n10 = n3;
                if (n5 <= n11) {
                    n10 = n3;
                    if (n11 < n3) {
                        n10 = n11;
                    }
                }
                n3 = n10;
            }
            if (n3 - n5 <= (Integer.MAX_VALUE - n4) / (n + 1)) {
                n5 = n4 + (n3 - n5) * (n + 1);
                block3 : for (n9 = 0; n9 < n2; ++n9) {
                    n4 = arrn[n9] & Integer.MAX_VALUE;
                    if (n4 < n3) {
                        ++n5;
                        continue;
                    }
                    if (n4 != n3) continue;
                    n11 = n5;
                    n10 = 36;
                    do {
                        if ((n4 = n10 - n8) < 1) {
                            n4 = 1;
                        } else if (n10 >= n8 + 26) {
                            n4 = 26;
                        }
                        if (n11 < n4) {
                            boolean bl = arrn[n9] < 0;
                            stringBuilder.append(Punycode.digitToBasic(n11, bl));
                            bl = n == n7;
                            n8 = Punycode.adaptBias(n5, n + 1, bl);
                            ++n;
                            n5 = 0;
                            continue block3;
                        }
                        stringBuilder.append(Punycode.digitToBasic((n11 - n4) % (36 - n4) + n4, false));
                        n11 = (n11 - n4) / (36 - n4);
                        n10 += 36;
                    } while (true);
                }
                n4 = n5 + 1;
                n5 = n3 + 1;
                continue;
            }
            throw new IllegalStateException("Internal program error");
        }
        return stringBuilder;
    }

    private static boolean isBasic(int n) {
        boolean bl = n < 128;
        return bl;
    }

    private static boolean isBasicUpperCase(int n) {
        boolean bl = 65 <= n && n >= 90;
        return bl;
    }

    private static boolean isSurrogate(int n) {
        boolean bl = (n & -2048) == 55296;
        return bl;
    }
}

