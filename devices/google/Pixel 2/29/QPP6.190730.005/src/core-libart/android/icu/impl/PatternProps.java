/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public final class PatternProps {
    private static final byte[] index2000;
    private static final byte[] latin1;
    private static final int[] syntax2000;
    private static final int[] syntaxOrWhiteSpace2000;

    static {
        latin1 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0, 3, 3, 0, 3, 0, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0};
        index2000 = new byte[]{2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9};
        syntax2000 = new int[]{0, -1, -65536, 2147418367, 2146435070, -65536, 4194303, -1048576, -242, 65537};
        syntaxOrWhiteSpace2000 = new int[]{0, -1, -16384, 2147419135, 2146435070, -65536, 4194303, -1048576, -242, 65537};
    }

    public static boolean isIdentifier(CharSequence charSequence) {
        int n = charSequence.length();
        if (n == 0) {
            return false;
        }
        int n2 = 0;
        do {
            int n3 = n2 + 1;
            if (PatternProps.isSyntaxOrWhiteSpace(charSequence.charAt(n2))) {
                return false;
            }
            if (n3 >= n) {
                return true;
            }
            n2 = n3;
        } while (true);
    }

    public static boolean isIdentifier(CharSequence charSequence, int n, int n2) {
        int n3 = n;
        if (n >= n2) {
            return false;
        }
        do {
            n = n3 + 1;
            if (PatternProps.isSyntaxOrWhiteSpace(charSequence.charAt(n3))) {
                return false;
            }
            if (n >= n2) {
                return true;
            }
            n3 = n;
        } while (true);
    }

    public static boolean isSyntax(int n) {
        block9 : {
            boolean bl;
            block11 : {
                block10 : {
                    boolean bl2 = false;
                    boolean bl3 = false;
                    bl = false;
                    if (n < 0) {
                        return false;
                    }
                    if (n <= 255) {
                        if (latin1[n] == 3) {
                            bl = true;
                        }
                        return bl;
                    }
                    if (n < 8208) {
                        return false;
                    }
                    if (n <= 12336) {
                        bl = bl2;
                        if ((syntax2000[index2000[n - 8192 >> 5]] >> (n & 31) & 1) != 0) {
                            bl = true;
                        }
                        return bl;
                    }
                    if (64830 > n || n > 65094) break block9;
                    if (n <= 64831) break block10;
                    bl = bl3;
                    if (65093 > n) break block11;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public static boolean isSyntaxOrWhiteSpace(int n) {
        block9 : {
            boolean bl;
            block11 : {
                block10 : {
                    boolean bl2 = false;
                    boolean bl3 = false;
                    bl = false;
                    if (n < 0) {
                        return false;
                    }
                    if (n <= 255) {
                        if (latin1[n] != 0) {
                            bl = true;
                        }
                        return bl;
                    }
                    if (n < 8206) {
                        return false;
                    }
                    if (n <= 12336) {
                        bl = bl2;
                        if ((syntaxOrWhiteSpace2000[index2000[n - 8192 >> 5]] >> (n & 31) & 1) != 0) {
                            bl = true;
                        }
                        return bl;
                    }
                    if (64830 > n || n > 65094) break block9;
                    if (n <= 64831) break block10;
                    bl = bl3;
                    if (65093 > n) break block11;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public static boolean isWhiteSpace(int n) {
        block6 : {
            boolean bl;
            block8 : {
                block7 : {
                    boolean bl2 = false;
                    bl = false;
                    if (n < 0) {
                        return false;
                    }
                    if (n <= 255) {
                        if (latin1[n] == 5) {
                            bl = true;
                        }
                        return bl;
                    }
                    if (8206 > n || n > 8233) break block6;
                    if (n <= 8207) break block7;
                    bl = bl2;
                    if (8232 > n) break block8;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public static int skipIdentifier(CharSequence charSequence, int n) {
        while (n < charSequence.length() && !PatternProps.isSyntaxOrWhiteSpace(charSequence.charAt(n))) {
            ++n;
        }
        return n;
    }

    public static int skipWhiteSpace(CharSequence charSequence, int n) {
        while (n < charSequence.length() && PatternProps.isWhiteSpace(charSequence.charAt(n))) {
            ++n;
        }
        return n;
    }

    public static String trimWhiteSpace(String string) {
        if (string.length() != 0 && (PatternProps.isWhiteSpace(string.charAt(0)) || PatternProps.isWhiteSpace(string.charAt(string.length() - 1)))) {
            int n;
            int n2 = string.length();
            for (n = 0; n < n2 && PatternProps.isWhiteSpace(string.charAt(n)); ++n) {
            }
            int n3 = n2;
            if (n < n2) {
                do {
                    n3 = --n2;
                } while (PatternProps.isWhiteSpace(string.charAt(n2 - 1)));
            }
            return string.substring(n, n3);
        }
        return string;
    }
}

