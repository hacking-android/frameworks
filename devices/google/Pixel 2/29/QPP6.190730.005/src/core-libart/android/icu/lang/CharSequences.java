/*
 * Decompiled with CFR 0.145.
 */
package android.icu.lang;

@Deprecated
public class CharSequences {
    private CharSequences() {
    }

    @Deprecated
    public static int[] codePoints(CharSequence arrn) {
        int[] arrn2 = new int[arrn.length()];
        int n = 0;
        for (int i = 0; i < arrn.length(); ++i) {
            char c;
            char c2 = arrn.charAt(i);
            if (c2 >= '\udc00' && c2 <= '\udfff' && i != 0 && (c = (char)arrn2[n - 1]) >= '\ud800' && c <= '\udbff') {
                arrn2[n - 1] = Character.toCodePoint(c, c2);
                continue;
            }
            arrn2[n] = c2;
            ++n;
        }
        if (n == arrn2.length) {
            return arrn2;
        }
        arrn = new int[n];
        System.arraycopy(arrn2, 0, arrn, 0, n);
        return arrn;
    }

    @Deprecated
    public static int compare(int n, CharSequence charSequence) {
        n = (n = CharSequences.compare(charSequence, n)) > 0 ? -1 : (n < 0 ? 1 : 0);
        return n;
    }

    @Deprecated
    public static int compare(CharSequence charSequence, int n) {
        if (n >= 0 && n <= 1114111) {
            int n2 = charSequence.length();
            if (n2 == 0) {
                return -1;
            }
            char c = charSequence.charAt(0);
            int n3 = n - 65536;
            if (n3 < 0) {
                if ((n = c - n) != 0) {
                    return n;
                }
                return n2 - 1;
            }
            n = c - (char)((n3 >>> 10) + 55296);
            if (n != 0) {
                return n;
            }
            if (n2 > 1) {
                n = (char)((n3 & 1023) + 56320);
                n = charSequence.charAt(1) - n;
                if (n != 0) {
                    return n;
                }
            }
            return n2 - 2;
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static int compare(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        int n2 = charSequence.length();
        int n3 = n2 <= (n = charSequence2.length()) ? n2 : n;
        for (int i = 0; i < n3; ++i) {
            int n4 = charSequence.charAt(i) - charSequence2.charAt(i);
            if (n4 == 0) continue;
            return n4;
        }
        return n2 - n;
    }

    @Deprecated
    public static final boolean equals(int n, CharSequence charSequence) {
        boolean bl = false;
        boolean bl2 = false;
        if (charSequence == null) {
            return false;
        }
        int n2 = charSequence.length();
        if (n2 != 1) {
            if (n2 != 2) {
                return false;
            }
            bl = bl2;
            if (n > 65535) {
                bl = bl2;
                if (n == Character.codePointAt(charSequence, 0)) {
                    bl = true;
                }
            }
            return bl;
        }
        if (n == charSequence.charAt(0)) {
            bl = true;
        }
        return bl;
    }

    @Deprecated
    public static final boolean equals(CharSequence charSequence, int n) {
        return CharSequences.equals(n, charSequence);
    }

    @Deprecated
    public static final <T> boolean equals(T t, T t2) {
        boolean bl = false;
        if (t == null) {
            if (t2 == null) {
                bl = true;
            }
        } else if (t2 != null) {
            bl = t.equals(t2);
        }
        return bl;
    }

    @Deprecated
    public static boolean equalsChars(CharSequence charSequence, CharSequence charSequence2) {
        boolean bl = charSequence.length() == charSequence2.length() && CharSequences.compare(charSequence, charSequence2) == 0;
        return bl;
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence charSequence) {
        int n = charSequence.length();
        int n2 = Integer.MAX_VALUE;
        boolean bl = true;
        if (n >= 1 && n <= 2) {
            int n3 = Character.codePointAt(charSequence, 0);
            boolean bl2 = n3 < 65536;
            if (n != 1) {
                bl = false;
            }
            if (bl2 == bl) {
                n2 = n3;
            }
            return n2;
        }
        return Integer.MAX_VALUE;
    }

    @Deprecated
    public static int indexOf(CharSequence charSequence, int n) {
        int n2;
        for (int i = 0; i < charSequence.length(); i += Character.charCount((int)n2)) {
            n2 = Character.codePointAt(charSequence, i);
            if (n2 != n) continue;
            return i;
        }
        return -1;
    }

    @Deprecated
    public static int matchAfter(CharSequence charSequence, CharSequence charSequence2, int n, int n2) {
        int n3;
        int n4 = charSequence.length();
        int n5 = charSequence2.length();
        for (n3 = n; n3 < n4 && n2 < n5 && charSequence.charAt(n3) == charSequence2.charAt(n2); ++n3, ++n2) {
        }
        n = n4 = n3 - n;
        if (n4 != 0) {
            n = n4;
            if (!CharSequences.onCharacterBoundary(charSequence, n3)) {
                n = n4;
                if (!CharSequences.onCharacterBoundary(charSequence2, n2)) {
                    n = n4 - 1;
                }
            }
        }
        return n;
    }

    @Deprecated
    public static boolean onCharacterBoundary(CharSequence charSequence, int n) {
        boolean bl = n <= 0 || n >= charSequence.length() || !Character.isHighSurrogate(charSequence.charAt(n - 1)) || !Character.isLowSurrogate(charSequence.charAt(n));
        return bl;
    }

    @Deprecated
    public int codePointLength(CharSequence charSequence) {
        return Character.codePointCount(charSequence, 0, charSequence.length());
    }
}

