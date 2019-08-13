/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.text.Normalizer;
import android.icu.text.Replaceable;
import java.util.Comparator;

public final class UTF16 {
    public static final int CODEPOINT_MAX_VALUE = 1114111;
    public static final int CODEPOINT_MIN_VALUE = 0;
    private static final int LEAD_SURROGATE_BITMASK = -1024;
    private static final int LEAD_SURROGATE_BITS = 55296;
    public static final int LEAD_SURROGATE_BOUNDARY = 2;
    public static final int LEAD_SURROGATE_MAX_VALUE = 56319;
    public static final int LEAD_SURROGATE_MIN_VALUE = 55296;
    private static final int LEAD_SURROGATE_OFFSET_ = 55232;
    private static final int LEAD_SURROGATE_SHIFT_ = 10;
    public static final int SINGLE_CHAR_BOUNDARY = 1;
    public static final int SUPPLEMENTARY_MIN_VALUE = 65536;
    private static final int SURROGATE_BITMASK = -2048;
    private static final int SURROGATE_BITS = 55296;
    public static final int SURROGATE_MAX_VALUE = 57343;
    public static final int SURROGATE_MIN_VALUE = 55296;
    private static final int TRAIL_SURROGATE_BITMASK = -1024;
    private static final int TRAIL_SURROGATE_BITS = 56320;
    public static final int TRAIL_SURROGATE_BOUNDARY = 5;
    private static final int TRAIL_SURROGATE_MASK_ = 1023;
    public static final int TRAIL_SURROGATE_MAX_VALUE = 57343;
    public static final int TRAIL_SURROGATE_MIN_VALUE = 56320;

    private UTF16() {
    }

    private static int _charAt(CharSequence charSequence, int n, char c) {
        char c2;
        if (c > '\udfff') {
            return c;
        }
        if (c <= '\udbff') {
            char c3;
            if (charSequence.length() != ++n && (c3 = charSequence.charAt(n)) >= '\udc00' && c3 <= '\udfff') {
                return Character.toCodePoint(c, c3);
            }
        } else if (--n >= 0 && (c2 = charSequence.charAt(n)) >= '\ud800' && c2 <= '\udbff') {
            return Character.toCodePoint(c2, c);
        }
        return c;
    }

    private static int _charAt(String string, int n, char c) {
        char c2;
        if (c > '\udfff') {
            return c;
        }
        if (c <= '\udbff') {
            char c3;
            if (string.length() != ++n && (c3 = string.charAt(n)) >= '\udc00' && c3 <= '\udfff') {
                return Character.toCodePoint(c, c3);
            }
        } else if (--n >= 0 && (c2 = string.charAt(n)) >= '\ud800' && c2 <= '\udbff') {
            return Character.toCodePoint(c2, c);
        }
        return c;
    }

    public static int append(char[] arrc, int n, int n2) {
        if (n2 >= 0 && n2 <= 1114111) {
            if (n2 >= 65536) {
                int n3 = n + 1;
                arrc[n] = UTF16.getLeadSurrogate(n2);
                n = n3 + 1;
                arrc[n3] = UTF16.getTrailSurrogate(n2);
            } else {
                arrc[n] = (char)n2;
                ++n;
            }
            return n;
        }
        throw new IllegalArgumentException("Illegal codepoint");
    }

    public static StringBuffer append(StringBuffer abstractStringBuilder, int n) {
        if (n >= 0 && n <= 1114111) {
            if (n >= 65536) {
                ((StringBuffer)abstractStringBuilder).append(UTF16.getLeadSurrogate(n));
                ((StringBuffer)abstractStringBuilder).append(UTF16.getTrailSurrogate(n));
            } else {
                ((StringBuffer)abstractStringBuilder).append((char)n);
            }
            return abstractStringBuilder;
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("Illegal codepoint: ");
        ((StringBuilder)abstractStringBuilder).append(Integer.toHexString(n));
        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    public static StringBuffer appendCodePoint(StringBuffer stringBuffer, int n) {
        return UTF16.append(stringBuffer, n);
    }

    public static int bounds(String string, int n) {
        char c = string.charAt(n);
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c)) {
                if (++n < string.length() && UTF16.isTrailSurrogate(string.charAt(n))) {
                    return 2;
                }
            } else if (--n >= 0 && UTF16.isLeadSurrogate(string.charAt(n))) {
                return 5;
            }
        }
        return 1;
    }

    public static int bounds(StringBuffer stringBuffer, int n) {
        char c = stringBuffer.charAt(n);
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c)) {
                if (++n < stringBuffer.length() && UTF16.isTrailSurrogate(stringBuffer.charAt(n))) {
                    return 2;
                }
            } else if (--n >= 0 && UTF16.isLeadSurrogate(stringBuffer.charAt(n))) {
                return 5;
            }
        }
        return 1;
    }

    public static int bounds(char[] arrc, int n, int n2, int n3) {
        if ((n3 += n) >= n && n3 < n2) {
            char c = arrc[n3];
            if (UTF16.isSurrogate(c)) {
                if (UTF16.isLeadSurrogate(c)) {
                    n = n3 + 1;
                    if (n < n2 && UTF16.isTrailSurrogate(arrc[n])) {
                        return 2;
                    }
                } else {
                    n2 = n3 - 1;
                    if (n2 >= n && UTF16.isLeadSurrogate(arrc[n2])) {
                        return 5;
                    }
                }
            }
            return 1;
        }
        throw new ArrayIndexOutOfBoundsException(n3);
    }

    public static int charAt(Replaceable replaceable, int n) {
        if (n >= 0 && n < replaceable.length()) {
            char c;
            char c2 = replaceable.charAt(n);
            if (!UTF16.isSurrogate(c2)) {
                return c2;
            }
            if (c2 <= '\udbff') {
                char c3;
                if (replaceable.length() != ++n && UTF16.isTrailSurrogate(c3 = replaceable.charAt(n))) {
                    return Character.toCodePoint(c2, c3);
                }
            } else if (--n >= 0 && UTF16.isLeadSurrogate(c = replaceable.charAt(n))) {
                return Character.toCodePoint(c, c2);
            }
            return c2;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public static int charAt(CharSequence charSequence, int n) {
        char c = charSequence.charAt(n);
        if (c < '\ud800') {
            return c;
        }
        return UTF16._charAt(charSequence, n, c);
    }

    public static int charAt(String string, int n) {
        char c = string.charAt(n);
        if (c < '\ud800') {
            return c;
        }
        return UTF16._charAt(string, n, c);
    }

    public static int charAt(StringBuffer stringBuffer, int n) {
        if (n >= 0 && n < stringBuffer.length()) {
            char c;
            char c2 = stringBuffer.charAt(n);
            if (!UTF16.isSurrogate(c2)) {
                return c2;
            }
            if (c2 <= '\udbff') {
                char c3;
                if (stringBuffer.length() != ++n && UTF16.isTrailSurrogate(c3 = stringBuffer.charAt(n))) {
                    return Character.toCodePoint(c2, c3);
                }
            } else if (--n >= 0 && UTF16.isLeadSurrogate(c = stringBuffer.charAt(n))) {
                return Character.toCodePoint(c, c2);
            }
            return c2;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public static int charAt(char[] arrc, int n, int n2, int n3) {
        if ((n3 += n) >= n && n3 < n2) {
            char c = arrc[n3];
            if (!UTF16.isSurrogate(c)) {
                return c;
            }
            if (c <= '\udbff') {
                n = n3 + 1;
                if (n >= n2) {
                    return c;
                }
                char c2 = arrc[n];
                if (UTF16.isTrailSurrogate(c2)) {
                    return Character.toCodePoint(c, c2);
                }
            } else {
                if (n3 == n) {
                    return c;
                }
                char c3 = arrc[n3 - 1];
                if (UTF16.isLeadSurrogate(c3)) {
                    return Character.toCodePoint(c3, c);
                }
            }
            return c;
        }
        throw new ArrayIndexOutOfBoundsException(n3);
    }

    public static int compareCodePoint(int n, CharSequence charSequence) {
        if (charSequence == null) {
            return 1;
        }
        int n2 = charSequence.length();
        if (n2 == 0) {
            return 1;
        }
        int n3 = 0;
        int n4 = n - Character.codePointAt(charSequence, 0);
        if (n4 != 0) {
            return n4;
        }
        n = n2 == Character.charCount(n) ? n3 : -1;
        return n;
    }

    public static int countCodePoint(String string) {
        if (string != null && string.length() != 0) {
            return UTF16.findCodePointOffset(string, string.length());
        }
        return 0;
    }

    public static int countCodePoint(StringBuffer stringBuffer) {
        if (stringBuffer != null && stringBuffer.length() != 0) {
            return UTF16.findCodePointOffset(stringBuffer, stringBuffer.length());
        }
        return 0;
    }

    public static int countCodePoint(char[] arrc, int n, int n2) {
        if (arrc != null && arrc.length != 0) {
            return UTF16.findCodePointOffset(arrc, n, n2, n2 - n);
        }
        return 0;
    }

    public static int delete(char[] arrc, int n, int n2) {
        int n3 = 1;
        int n4 = UTF16.bounds(arrc, 0, n, n2);
        if (n4 != 2) {
            if (n4 == 5) {
                n3 = 1 + 1;
                --n2;
            }
        } else {
            n3 = 1 + 1;
        }
        System.arraycopy(arrc, n2 + n3, arrc, n2, n - (n2 + n3));
        arrc[n - n3] = (char)(false ? 1 : 0);
        return n - n3;
    }

    public static StringBuffer delete(StringBuffer stringBuffer, int n) {
        int n2 = 1;
        int n3 = UTF16.bounds(stringBuffer, n);
        if (n3 != 2) {
            if (n3 == 5) {
                n2 = 1 + 1;
                --n;
            }
        } else {
            n2 = 1 + 1;
        }
        stringBuffer.delete(n, n + n2);
        return stringBuffer;
    }

    public static int findCodePointOffset(String string, int n) {
        if (n >= 0 && n <= string.length()) {
            int n2;
            int n3 = 0;
            boolean bl = false;
            for (n2 = 0; n2 < n; ++n2) {
                char c = string.charAt(n2);
                if (bl && UTF16.isTrailSurrogate(c)) {
                    bl = false;
                    continue;
                }
                bl = UTF16.isLeadSurrogate(c);
                ++n3;
            }
            if (n == string.length()) {
                return n3;
            }
            n2 = n3;
            if (bl) {
                n2 = n3;
                if (UTF16.isTrailSurrogate(string.charAt(n))) {
                    n2 = n3 - 1;
                }
            }
            return n2;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public static int findCodePointOffset(StringBuffer stringBuffer, int n) {
        if (n >= 0 && n <= stringBuffer.length()) {
            int n2;
            int n3 = 0;
            boolean bl = false;
            for (n2 = 0; n2 < n; ++n2) {
                char c = stringBuffer.charAt(n2);
                if (bl && UTF16.isTrailSurrogate(c)) {
                    bl = false;
                    continue;
                }
                bl = UTF16.isLeadSurrogate(c);
                ++n3;
            }
            if (n == stringBuffer.length()) {
                return n3;
            }
            n2 = n3;
            if (bl) {
                n2 = n3;
                if (UTF16.isTrailSurrogate(stringBuffer.charAt(n))) {
                    n2 = n3 - 1;
                }
            }
            return n2;
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public static int findCodePointOffset(char[] arrc, int n, int n2, int n3) {
        int n4 = n3 + n;
        if (n4 <= n2) {
            int n5 = 0;
            boolean bl = false;
            n3 = n;
            n = n5;
            while (n3 < n4) {
                char c = arrc[n3];
                if (bl && UTF16.isTrailSurrogate(c)) {
                    bl = false;
                } else {
                    bl = UTF16.isLeadSurrogate(c);
                    ++n;
                }
                ++n3;
            }
            if (n4 == n2) {
                return n;
            }
            n2 = n;
            if (bl) {
                n2 = n;
                if (UTF16.isTrailSurrogate(arrc[n4])) {
                    n2 = n - 1;
                }
            }
            return n2;
        }
        throw new StringIndexOutOfBoundsException(n4);
    }

    public static int findOffsetFromCodePoint(String string, int n) {
        int n2 = string.length();
        int n3 = 0;
        if (n >= 0 && n <= n2) {
            int n4;
            for (n4 = n; n3 < n2 && n4 > 0; --n4) {
                int n5 = n3;
                if (UTF16.isLeadSurrogate(string.charAt(n3))) {
                    n5 = n3;
                    if (n3 + 1 < n2) {
                        n5 = n3;
                        if (UTF16.isTrailSurrogate(string.charAt(n3 + 1))) {
                            n5 = n3 + 1;
                        }
                    }
                }
                n3 = n5 + 1;
            }
            if (n4 == 0) {
                return n3;
            }
            throw new StringIndexOutOfBoundsException(n);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public static int findOffsetFromCodePoint(StringBuffer stringBuffer, int n) {
        int n2 = stringBuffer.length();
        int n3 = 0;
        if (n >= 0 && n <= n2) {
            int n4;
            for (n4 = n; n3 < n2 && n4 > 0; --n4) {
                int n5 = n3;
                if (UTF16.isLeadSurrogate(stringBuffer.charAt(n3))) {
                    n5 = n3;
                    if (n3 + 1 < n2) {
                        n5 = n3;
                        if (UTF16.isTrailSurrogate(stringBuffer.charAt(n3 + 1))) {
                            n5 = n3 + 1;
                        }
                    }
                }
                n3 = n5 + 1;
            }
            if (n4 == 0) {
                return n3;
            }
            throw new StringIndexOutOfBoundsException(n);
        }
        throw new StringIndexOutOfBoundsException(n);
    }

    public static int findOffsetFromCodePoint(char[] arrc, int n, int n2, int n3) {
        int n4 = n;
        if (n3 <= n2 - n) {
            int n5;
            for (n5 = n3; n4 < n2 && n5 > 0; --n5) {
                int n6 = n4;
                if (UTF16.isLeadSurrogate(arrc[n4])) {
                    n6 = n4;
                    if (n4 + 1 < n2) {
                        n6 = n4;
                        if (UTF16.isTrailSurrogate(arrc[n4 + 1])) {
                            n6 = n4 + 1;
                        }
                    }
                }
                n4 = n6 + 1;
            }
            if (n5 == 0) {
                return n4 - n;
            }
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        throw new ArrayIndexOutOfBoundsException(n3);
    }

    public static int getCharCount(int n) {
        if (n < 65536) {
            return 1;
        }
        return 2;
    }

    public static char getLeadSurrogate(int n) {
        if (n >= 65536) {
            return (char)((n >> 10) + 55232);
        }
        return '\u0000';
    }

    public static int getSingleCodePoint(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() != 0) {
            if (charSequence.length() == 1) {
                return charSequence.charAt(0);
            }
            if (charSequence.length() > 2) {
                return -1;
            }
            int n = Character.codePointAt(charSequence, 0);
            if (n > 65535) {
                return n;
            }
            return -1;
        }
        return -1;
    }

    public static char getTrailSurrogate(int n) {
        if (n >= 65536) {
            return (char)((n & 1023) + 56320);
        }
        return (char)n;
    }

    public static boolean hasMoreCodePointsThan(String string, int n) {
        if (n < 0) {
            return true;
        }
        if (string == null) {
            return false;
        }
        int n2 = string.length();
        if (n2 + 1 >> 1 > n) {
            return true;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return false;
        }
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n2 != 0) {
            if (n5 == 0) {
                return true;
            }
            n4 = n + 1;
            if (UTF16.isLeadSurrogate(string.charAt(n)) && n4 != n2 && UTF16.isTrailSurrogate(string.charAt(n4))) {
                if (--n3 <= 0) {
                    return false;
                }
                n = n4 + 1;
            } else {
                n = n4;
            }
            --n5;
        }
        return false;
    }

    public static boolean hasMoreCodePointsThan(StringBuffer stringBuffer, int n) {
        if (n < 0) {
            return true;
        }
        if (stringBuffer == null) {
            return false;
        }
        int n2 = stringBuffer.length();
        if (n2 + 1 >> 1 > n) {
            return true;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return false;
        }
        int n4 = 0;
        int n5 = n;
        n = n4;
        while (n2 != 0) {
            if (n5 == 0) {
                return true;
            }
            n4 = n + 1;
            if (UTF16.isLeadSurrogate(stringBuffer.charAt(n)) && n4 != n2 && UTF16.isTrailSurrogate(stringBuffer.charAt(n4))) {
                if (--n3 <= 0) {
                    return false;
                }
                n = n4 + 1;
            } else {
                n = n4;
            }
            --n5;
        }
        return false;
    }

    public static boolean hasMoreCodePointsThan(char[] arrc, int n, int n2, int n3) {
        int n4 = n2 - n;
        if (n4 >= 0 && n >= 0 && n2 >= 0) {
            int n5;
            if (n3 < 0) {
                return true;
            }
            if (arrc == null) {
                return false;
            }
            if (n4 + 1 >> 1 > n3) {
                return true;
            }
            int n6 = n5 = n4 - n3;
            if (n5 <= 0) {
                return false;
            }
            do {
                if (n4 == 0) {
                    return false;
                }
                if (n3 == 0) {
                    return true;
                }
                n5 = n + 1;
                if (UTF16.isLeadSurrogate(arrc[n]) && n5 != n2 && UTF16.isTrailSurrogate(arrc[n5])) {
                    if (--n6 <= 0) {
                        return false;
                    }
                    n = n5 + 1;
                } else {
                    n = n5;
                }
                --n3;
            } while (true);
        }
        throw new IndexOutOfBoundsException("Start and limit indexes should be non-negative and start <= limit");
    }

    public static int indexOf(String string, int n) {
        if (n >= 0 && n <= 1114111) {
            if (n >= 55296 && (n <= 57343 || n >= 65536)) {
                if (n < 65536) {
                    int n2 = string.indexOf((char)n);
                    if (n2 >= 0) {
                        if (UTF16.isLeadSurrogate((char)n) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + 1))) {
                            return UTF16.indexOf(string, n, n2 + 1);
                        }
                        if (n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                            return UTF16.indexOf(string, n, n2 + 1);
                        }
                    }
                    return n2;
                }
                return string.indexOf(UTF16.toString(n));
            }
            return string.indexOf((char)n);
        }
        throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
    }

    public static int indexOf(String string, int n, int n2) {
        if (n >= 0 && n <= 1114111) {
            if (n >= 55296 && (n <= 57343 || n >= 65536)) {
                if (n < 65536) {
                    if ((n2 = string.indexOf((char)n, n2)) >= 0) {
                        if (UTF16.isLeadSurrogate((char)n) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + 1))) {
                            return UTF16.indexOf(string, n, n2 + 1);
                        }
                        if (n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                            return UTF16.indexOf(string, n, n2 + 1);
                        }
                    }
                    return n2;
                }
                return string.indexOf(UTF16.toString(n), n2);
            }
            return string.indexOf((char)n, n2);
        }
        throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
    }

    public static int indexOf(String string, String string2) {
        int n = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n - 1))) {
            return string.indexOf(string2);
        }
        int n2 = string.indexOf(string2);
        int n3 = n2 + n;
        if (n2 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n - 1)) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n3 + 1))) {
                return UTF16.indexOf(string, string2, n3 + 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                return UTF16.indexOf(string, string2, n3 + 1);
            }
        }
        return n2;
    }

    public static int indexOf(String string, String string2, int n) {
        int n2 = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n2 - 1))) {
            return string.indexOf(string2, n);
        }
        int n3 = string.indexOf(string2, n);
        n = n3 + n2;
        if (n3 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n2 - 1)) && n3 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n))) {
                return UTF16.indexOf(string, string2, n + 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n3 > 0 && UTF16.isLeadSurrogate(string.charAt(n3 - 1))) {
                return UTF16.indexOf(string, string2, n + 1);
            }
        }
        return n3;
    }

    public static int insert(char[] arrc, int n, int n2, int n3) {
        String string = UTF16.valueOf(n3);
        n3 = n2;
        if (n2 != n) {
            n3 = n2;
            if (UTF16.bounds(arrc, 0, n, n2) == 5) {
                n3 = n2 + 1;
            }
        }
        if (n + (n2 = string.length()) <= arrc.length) {
            System.arraycopy(arrc, n3, arrc, n3 + n2, n - n3);
            arrc[n3] = string.charAt(0);
            if (n2 == 2) {
                arrc[n3 + 1] = string.charAt(1);
            }
            return n + n2;
        }
        throw new ArrayIndexOutOfBoundsException(n3 + n2);
    }

    public static StringBuffer insert(StringBuffer stringBuffer, int n, int n2) {
        String string = UTF16.valueOf(n2);
        n2 = n;
        if (n != stringBuffer.length()) {
            n2 = n;
            if (UTF16.bounds(stringBuffer, n) == 5) {
                n2 = n + 1;
            }
        }
        stringBuffer.insert(n2, string);
        return stringBuffer;
    }

    public static boolean isLeadSurrogate(char c) {
        boolean bl = (c & -1024) == 55296;
        return bl;
    }

    public static boolean isSurrogate(char c) {
        boolean bl = (c & -2048) == 55296;
        return bl;
    }

    public static boolean isTrailSurrogate(char c) {
        boolean bl = (c & -1024) == 56320;
        return bl;
    }

    public static int lastIndexOf(String string, int n) {
        if (n >= 0 && n <= 1114111) {
            if (n >= 55296 && (n <= 57343 || n >= 65536)) {
                if (n < 65536) {
                    int n2 = string.lastIndexOf((char)n);
                    if (n2 >= 0) {
                        if (UTF16.isLeadSurrogate((char)n) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + 1))) {
                            return UTF16.lastIndexOf(string, n, n2 - 1);
                        }
                        if (n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                            return UTF16.lastIndexOf(string, n, n2 - 1);
                        }
                    }
                    return n2;
                }
                return string.lastIndexOf(UTF16.toString(n));
            }
            return string.lastIndexOf((char)n);
        }
        throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
    }

    public static int lastIndexOf(String string, int n, int n2) {
        if (n >= 0 && n <= 1114111) {
            if (n >= 55296 && (n <= 57343 || n >= 65536)) {
                if (n < 65536) {
                    if ((n2 = string.lastIndexOf((char)n, n2)) >= 0) {
                        if (UTF16.isLeadSurrogate((char)n) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + 1))) {
                            return UTF16.lastIndexOf(string, n, n2 - 1);
                        }
                        if (n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                            return UTF16.lastIndexOf(string, n, n2 - 1);
                        }
                    }
                    return n2;
                }
                return string.lastIndexOf(UTF16.toString(n), n2);
            }
            return string.lastIndexOf((char)n, n2);
        }
        throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
    }

    public static int lastIndexOf(String string, String string2) {
        int n = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n - 1))) {
            return string.lastIndexOf(string2);
        }
        int n2 = string.lastIndexOf(string2);
        if (n2 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n - 1)) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + n + 1))) {
                return UTF16.lastIndexOf(string, string2, n2 - 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                return UTF16.lastIndexOf(string, string2, n2 - 1);
            }
        }
        return n2;
    }

    public static int lastIndexOf(String string, String string2, int n) {
        int n2 = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n2 - 1))) {
            return string.lastIndexOf(string2, n);
        }
        if ((n = string.lastIndexOf(string2, n)) >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n2 - 1)) && n < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n + n2))) {
                return UTF16.lastIndexOf(string, string2, n - 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n > 0 && UTF16.isLeadSurrogate(string.charAt(n - 1))) {
                return UTF16.lastIndexOf(string, string2, n - 1);
            }
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int moveCodePointOffset(String string, int n, int n2) {
        int n3;
        int n4 = n;
        int n5 = string.length();
        if (n < 0 || n > n5) throw new StringIndexOutOfBoundsException(n);
        if (n2 > 0) {
            if (n2 + n > n5) throw new StringIndexOutOfBoundsException(n);
            int n6 = n2;
            n = n4;
            do {
                n4 = n;
                n3 = n6;
                if (n < n5) {
                    n4 = n;
                    n3 = n6;
                    if (n6 > 0) {
                        n4 = n;
                        if (UTF16.isLeadSurrogate(string.charAt(n))) {
                            n4 = n;
                            if (n + 1 < n5) {
                                n4 = n;
                                if (UTF16.isTrailSurrogate(string.charAt(n + 1))) {
                                    n4 = n + 1;
                                }
                            }
                        }
                        --n6;
                        n = n4 + 1;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            if (n + n2 < 0) throw new StringIndexOutOfBoundsException(n);
            n = -n2;
            int n7 = n4;
            do {
                n4 = n7;
                n3 = --n;
                if (n <= 0) break;
                n4 = n7 - 1;
                if (n4 < 0) {
                    n3 = n;
                    break;
                }
                n7 = n4;
                if (!UTF16.isTrailSurrogate(string.charAt(n4))) continue;
                n7 = n4;
                if (n4 <= 0) continue;
                n7 = n4;
                if (!UTF16.isLeadSurrogate(string.charAt(n4 - 1))) continue;
                n7 = n4 - 1;
            } while (true);
        }
        if (n3 != 0) throw new StringIndexOutOfBoundsException(n2);
        return n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int moveCodePointOffset(StringBuffer stringBuffer, int n, int n2) {
        int n3;
        int n4 = n;
        int n5 = stringBuffer.length();
        if (n < 0 || n > n5) throw new StringIndexOutOfBoundsException(n);
        if (n2 > 0) {
            if (n2 + n > n5) throw new StringIndexOutOfBoundsException(n);
            int n6 = n2;
            n = n4;
            do {
                n4 = n;
                n3 = n6;
                if (n < n5) {
                    n4 = n;
                    n3 = n6;
                    if (n6 > 0) {
                        n4 = n;
                        if (UTF16.isLeadSurrogate(stringBuffer.charAt(n))) {
                            n4 = n;
                            if (n + 1 < n5) {
                                n4 = n;
                                if (UTF16.isTrailSurrogate(stringBuffer.charAt(n + 1))) {
                                    n4 = n + 1;
                                }
                            }
                        }
                        --n6;
                        n = n4 + 1;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            if (n + n2 < 0) throw new StringIndexOutOfBoundsException(n);
            n = -n2;
            int n7 = n4;
            do {
                n4 = n7;
                n3 = --n;
                if (n <= 0) break;
                n4 = n7 - 1;
                if (n4 < 0) {
                    n3 = n;
                    break;
                }
                n7 = n4;
                if (!UTF16.isTrailSurrogate(stringBuffer.charAt(n4))) continue;
                n7 = n4;
                if (n4 <= 0) continue;
                n7 = n4;
                if (!UTF16.isLeadSurrogate(stringBuffer.charAt(n4 - 1))) continue;
                n7 = n4 - 1;
            } while (true);
        }
        if (n3 != 0) throw new StringIndexOutOfBoundsException(n2);
        return n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int moveCodePointOffset(char[] arrc, int n, int n2, int n3, int n4) {
        int n5;
        int n6 = arrc.length;
        int n7 = n3 + n;
        if (n < 0 || n2 < n) throw new StringIndexOutOfBoundsException(n);
        if (n2 > n6) throw new StringIndexOutOfBoundsException(n2);
        if (n3 < 0 || n7 > n2) throw new StringIndexOutOfBoundsException(n3);
        if (n4 > 0) {
            if (n4 + n7 > n6) throw new StringIndexOutOfBoundsException(n7);
            n6 = n4;
            n3 = n7;
            do {
                n7 = n3;
                n5 = n6;
                if (n3 < n2) {
                    n7 = n3;
                    n5 = n6;
                    if (n6 > 0) {
                        n7 = n3;
                        if (UTF16.isLeadSurrogate(arrc[n3])) {
                            n7 = n3;
                            if (n3 + 1 < n2) {
                                n7 = n3;
                                if (UTF16.isTrailSurrogate(arrc[n3 + 1])) {
                                    n7 = n3 + 1;
                                }
                            }
                        }
                        --n6;
                        n3 = n7 + 1;
                        continue;
                    }
                }
                break;
            } while (true);
        } else {
            if (n7 + n4 < n) throw new StringIndexOutOfBoundsException(n7);
            n2 = -n4;
            n3 = n7;
            do {
                n7 = n3;
                n5 = --n2;
                if (n2 <= 0) break;
                n7 = n3 - 1;
                if (n7 < n) {
                    n5 = n2;
                    break;
                }
                n3 = n7;
                if (!UTF16.isTrailSurrogate(arrc[n7])) continue;
                n3 = n7;
                if (n7 <= n) continue;
                n3 = n7;
                if (!UTF16.isLeadSurrogate(arrc[n7 - 1])) continue;
                n3 = n7 - 1;
            } while (true);
        }
        if (n5 != 0) throw new StringIndexOutOfBoundsException(n4);
        return n7 - n;
    }

    public static String newString(int[] arrn, int n, int n2) {
        if (n2 >= 0) {
            char[] arrc = new char[n2];
            int n3 = 0;
            for (int i = n; i < n + n2; ++i) {
                int n4 = arrn[i];
                if (n4 >= 0 && n4 <= 1114111) {
                    if (n4 < 65536) {
                        n4 = (char)n4;
                        arrc[n3] = (char)n4;
                        ++n3;
                        continue;
                    }
                    arrc[n3] = (char)((n4 >> 10) + 55232);
                    arrc[n3 + 1] = (char)((n4 & 1023) + 56320);
                    n3 += 2;
                    continue;
                }
                throw new IllegalArgumentException();
            }
            return new String(arrc, 0, n3);
        }
        throw new IllegalArgumentException();
    }

    public static String replace(String string, int n, int n2) {
        if (n > 0 && n <= 1114111) {
            if (n2 > 0 && n2 <= 1114111) {
                int n3 = UTF16.indexOf(string, n);
                if (n3 == -1) {
                    return string;
                }
                String string2 = UTF16.toString(n2);
                n2 = 1;
                int n4 = string2.length();
                StringBuffer stringBuffer = new StringBuffer(string);
                int n5 = n3;
                int n6 = n3;
                int n7 = n5;
                if (n >= 65536) {
                    n2 = 2;
                    n7 = n5;
                    n6 = n3;
                }
                while (n6 != -1) {
                    stringBuffer.replace(n7, n7 + n2, string2);
                    n3 = n6 + n2;
                    n6 = UTF16.indexOf(string, n, n3);
                    n7 += n4 + n6 - n3;
                }
                return stringBuffer.toString();
            }
            throw new IllegalArgumentException("Argument newChar32 is not a valid codepoint");
        }
        throw new IllegalArgumentException("Argument oldChar32 is not a valid codepoint");
    }

    public static String replace(String string, String string2, String string3) {
        int n = UTF16.indexOf(string, string2);
        if (n == -1) {
            return string;
        }
        int n2 = string2.length();
        int n3 = string3.length();
        StringBuffer stringBuffer = new StringBuffer(string);
        int n4 = n;
        while (n != -1) {
            stringBuffer.replace(n4, n4 + n2, string3);
            int n5 = n + n2;
            n = UTF16.indexOf(string, string2, n5);
            n4 += n3 + n - n5;
        }
        return stringBuffer.toString();
    }

    public static StringBuffer reverse(StringBuffer stringBuffer) {
        int n = stringBuffer.length();
        StringBuffer stringBuffer2 = new StringBuffer(n);
        do {
            char c;
            int n2 = n - 1;
            if (n <= 0) break;
            char c2 = stringBuffer.charAt(n2);
            if (UTF16.isTrailSurrogate(c2) && n2 > 0 && UTF16.isLeadSurrogate(c = stringBuffer.charAt(n2 - 1))) {
                stringBuffer2.append(c);
                stringBuffer2.append(c2);
                n = n2 - 1;
                continue;
            }
            stringBuffer2.append(c2);
            n = n2;
        } while (true);
        return stringBuffer2;
    }

    public static int setCharAt(char[] arrc, int n, int n2, int n3) {
        if (n2 < n) {
            int n4 = 1;
            char c = arrc[n2];
            int n5 = n4;
            int n6 = n2;
            if (UTF16.isSurrogate(c)) {
                if (UTF16.isLeadSurrogate(c) && arrc.length > n2 + 1 && UTF16.isTrailSurrogate(arrc[n2 + 1])) {
                    n5 = 1 + 1;
                    n6 = n2;
                } else {
                    n5 = n4;
                    n6 = n2;
                    if (UTF16.isTrailSurrogate(c)) {
                        n5 = n4;
                        n6 = n2;
                        if (n2 > 0) {
                            n5 = n4;
                            n6 = n2;
                            if (UTF16.isLeadSurrogate(arrc[n2 - 1])) {
                                n6 = n2 - 1;
                                n5 = 1 + 1;
                            }
                        }
                    }
                }
            }
            String string = UTF16.valueOf(n3);
            n2 = n;
            n3 = string.length();
            arrc[n6] = string.charAt(0);
            if (n5 == n3) {
                n = n2;
                if (n5 == 2) {
                    arrc[n6 + 1] = string.charAt(1);
                    n = n2;
                }
            } else {
                System.arraycopy(arrc, n6 + n5, arrc, n6 + n3, n - (n6 + n5));
                if (n5 < n3) {
                    arrc[n6 + 1] = string.charAt(1);
                    n = ++n2;
                    if (n2 < arrc.length) {
                        arrc[n2] = (char)(false ? 1 : 0);
                        n = n2;
                    }
                } else {
                    n = n2 - 1;
                    arrc[n] = (char)(false ? 1 : 0);
                }
            }
            return n;
        }
        throw new ArrayIndexOutOfBoundsException(n2);
    }

    public static void setCharAt(StringBuffer stringBuffer, int n, int n2) {
        int n3 = 1;
        char c = stringBuffer.charAt(n);
        int n4 = n3;
        int n5 = n;
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c) && stringBuffer.length() > n + 1 && UTF16.isTrailSurrogate(stringBuffer.charAt(n + 1))) {
                n4 = 1 + 1;
                n5 = n;
            } else {
                n4 = n3;
                n5 = n;
                if (UTF16.isTrailSurrogate(c)) {
                    n4 = n3;
                    n5 = n;
                    if (n > 0) {
                        n4 = n3;
                        n5 = n;
                        if (UTF16.isLeadSurrogate(stringBuffer.charAt(n - 1))) {
                            n5 = n - 1;
                            n4 = 1 + 1;
                        }
                    }
                }
            }
        }
        stringBuffer.replace(n5, n5 + n4, UTF16.valueOf(n2));
    }

    private static String toString(int n) {
        if (n < 65536) {
            return String.valueOf((char)n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UTF16.getLeadSurrogate(n));
        stringBuilder.append(UTF16.getTrailSurrogate(n));
        return stringBuilder.toString();
    }

    public static String valueOf(int n) {
        if (n >= 0 && n <= 1114111) {
            return UTF16.toString(n);
        }
        throw new IllegalArgumentException("Illegal codepoint");
    }

    public static String valueOf(String string, int n) {
        int n2 = UTF16.bounds(string, n);
        if (n2 != 2) {
            if (n2 != 5) {
                return string.substring(n, n + 1);
            }
            return string.substring(n - 1, n + 1);
        }
        return string.substring(n, n + 2);
    }

    public static String valueOf(StringBuffer stringBuffer, int n) {
        int n2 = UTF16.bounds(stringBuffer, n);
        if (n2 != 2) {
            if (n2 != 5) {
                return stringBuffer.substring(n, n + 1);
            }
            return stringBuffer.substring(n - 1, n + 1);
        }
        return stringBuffer.substring(n, n + 2);
    }

    public static String valueOf(char[] arrc, int n, int n2, int n3) {
        if ((n2 = UTF16.bounds(arrc, n, n2, n3)) != 2) {
            if (n2 != 5) {
                return new String(arrc, n + n3, 1);
            }
            return new String(arrc, n + n3 - 1, 2);
        }
        return new String(arrc, n + n3, 2);
    }

    public static final class StringComparator
    implements Comparator<String> {
        private static final int CODE_POINT_COMPARE_SURROGATE_OFFSET_ = 10240;
        public static final int FOLD_CASE_DEFAULT = 0;
        public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
        private int m_codePointCompare_;
        private int m_foldCase_;
        private boolean m_ignoreCase_;

        public StringComparator() {
            this(false, false, 0);
        }

        public StringComparator(boolean bl, boolean bl2, int n) {
            this.setCodePointCompare(bl);
            this.m_ignoreCase_ = bl2;
            if (n >= 0 && n <= 1) {
                this.m_foldCase_ = n;
                return;
            }
            throw new IllegalArgumentException("Invalid fold case option");
        }

        private int compareCaseInsensitive(String string, String string2) {
            return Normalizer.cmpEquivFold(string, string2, this.m_foldCase_ | this.m_codePointCompare_ | 65536);
        }

        private int compareCaseSensitive(String string, String string2) {
            char c;
            int n;
            block14 : {
                char c2;
                int n2;
                int n3;
                block17 : {
                    int n4;
                    block16 : {
                        char c3;
                        block15 : {
                            int n5 = string.length();
                            n4 = string2.length();
                            n = n5;
                            n3 = 0;
                            if (n5 < n4) {
                                n3 = -1;
                            } else if (n5 > n4) {
                                n3 = 1;
                                n = n4;
                            }
                            char c4 = '\u0000';
                            c = '\u0000';
                            n2 = 0;
                            do {
                                c3 = c4;
                                c2 = c;
                                if (n2 >= n) break;
                                c4 = string.charAt(n2);
                                if (c4 != (c = string2.charAt(n2))) {
                                    c3 = c4;
                                    c2 = c;
                                    break;
                                }
                                ++n2;
                            } while (true);
                            if (n2 == n) {
                                return n3;
                            }
                            n3 = this.m_codePointCompare_ == 32768 ? 1 : 0;
                            n = c3;
                            c = c2;
                            if (c3 < '\ud800') break block14;
                            n = c3;
                            c = c2;
                            if (c2 < '\ud800') break block14;
                            n = c3;
                            c = c2;
                            if (n3 == 0) break block14;
                            if (c3 > '\udbff' || n2 + 1 == n5) break block15;
                            n3 = c3;
                            if (UTF16.isTrailSurrogate(string.charAt(n2 + 1))) break block16;
                        }
                        n3 = UTF16.isTrailSurrogate(c3) && n2 != 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1)) ? (int)c3 : (int)((char)(c3 - 10240));
                    }
                    if (c2 > '\udbff' || n2 + 1 == n4) break block17;
                    n = n3;
                    c = c2;
                    if (UTF16.isTrailSurrogate(string2.charAt(n2 + 1))) break block14;
                }
                if (UTF16.isTrailSurrogate(c2) && n2 != 0 && UTF16.isLeadSurrogate(string2.charAt(n2 - 1))) {
                    n = n3;
                    c = c2;
                } else {
                    c = (char)(c2 - 10240);
                    n = n3;
                }
            }
            return n - c;
        }

        @Override
        public int compare(String string, String string2) {
            if (Utility.sameObjects(string, string2)) {
                return 0;
            }
            if (string == null) {
                return -1;
            }
            if (string2 == null) {
                return 1;
            }
            if (this.m_ignoreCase_) {
                return this.compareCaseInsensitive(string, string2);
            }
            return this.compareCaseSensitive(string, string2);
        }

        public boolean getCodePointCompare() {
            boolean bl = this.m_codePointCompare_ == 32768;
            return bl;
        }

        public boolean getIgnoreCase() {
            return this.m_ignoreCase_;
        }

        public int getIgnoreCaseOption() {
            return this.m_foldCase_;
        }

        public void setCodePointCompare(boolean bl) {
            this.m_codePointCompare_ = bl ? 32768 : 0;
        }

        public void setIgnoreCase(boolean bl, int n) {
            this.m_ignoreCase_ = bl;
            if (n >= 0 && n <= 1) {
                this.m_foldCase_ = n;
                return;
            }
            throw new IllegalArgumentException("Invalid fold case option");
        }
    }

}

