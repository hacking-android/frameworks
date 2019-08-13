/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public final class UCharacterUtility {
    private static final int NON_CHARACTER_MAX_3_1_ = 65007;
    private static final int NON_CHARACTER_MIN_3_1_ = 64976;
    private static final int NON_CHARACTER_SUFFIX_MIN_3_0_ = 65534;

    private UCharacterUtility() {
    }

    static int compareNullTermByteSubString(String string, byte[] arrby, int n, int n2) {
        byte by = 1;
        int n3 = string.length();
        while (by != 0) {
            by = arrby[n2];
            ++n2;
            if (by == 0) break;
            if (n != n3 && string.charAt(n) == (char)(by & 255)) {
                ++n;
                continue;
            }
            return -1;
        }
        return n;
    }

    static int getNullTermByteSubString(StringBuffer stringBuffer, byte[] arrby, int n) {
        byte by = 1;
        while (by != 0) {
            by = arrby[n];
            if (by != 0) {
                stringBuffer.append((char)(by & 255));
            }
            ++n;
        }
        return n;
    }

    public static boolean isNonCharacter(int n) {
        boolean bl = true;
        if ((n & 65534) == 65534) {
            return true;
        }
        if (n < 64976 || n > 65007) {
            bl = false;
        }
        return bl;
    }

    static int skipByteSubString(byte[] arrby, int n, int n2, byte by) {
        int n3;
        int n4 = 0;
        do {
            n3 = ++n4;
            if (n4 >= n2) break;
            if (arrby[n + n4] != by) continue;
            n3 = n4 + 1;
            break;
        } while (true);
        return n3;
    }

    static int skipNullTermByteSubString(byte[] arrby, int n, int n2) {
        int n3 = 0;
        int n4 = n;
        for (n = n3; n < n2; ++n) {
            n3 = 1;
            while (n3 != 0) {
                n3 = arrby[n4];
                ++n4;
            }
        }
        return n4;
    }

    static int toInt(char c, char c2) {
        return c << 16 | c2;
    }
}

