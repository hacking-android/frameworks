/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.util.Comparator;

public class ASCIICaseInsensitiveComparator
implements Comparator<String> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Comparator<String> CASE_INSENSITIVE_ORDER = new ASCIICaseInsensitiveComparator();

    static boolean isLower(int n) {
        boolean bl = (n - 97 | 122 - n) >= 0;
        return bl;
    }

    static boolean isUpper(int n) {
        boolean bl = (n - 65 | 90 - n) >= 0;
        return bl;
    }

    public static int lowerCaseHashCode(String string) {
        int n = 0;
        int n2 = string.length();
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + ASCIICaseInsensitiveComparator.toLower(string.charAt(i));
        }
        return n;
    }

    static int toLower(int n) {
        block0 : {
            if (!ASCIICaseInsensitiveComparator.isUpper(n)) break block0;
            n += 32;
        }
        return n;
    }

    static int toUpper(int n) {
        block0 : {
            if (!ASCIICaseInsensitiveComparator.isLower(n)) break block0;
            n -= 32;
        }
        return n;
    }

    @Override
    public int compare(String string, String string2) {
        int n;
        int n2 = string.length();
        int n3 = n2 < (n = string2.length()) ? n2 : n;
        for (int i = 0; i < n3; ++i) {
            char c;
            char c2 = string.charAt(i);
            if (c2 == (c = string2.charAt(i)) || (c2 = (char)ASCIICaseInsensitiveComparator.toLower(c2)) == (c = (char)ASCIICaseInsensitiveComparator.toLower(c))) continue;
            return c2 - c;
        }
        return n2 - n;
    }
}

