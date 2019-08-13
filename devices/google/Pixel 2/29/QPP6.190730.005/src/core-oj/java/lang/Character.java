/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Character
implements Serializable,
Comparable<Character> {
    public static final int BYTES = 2;
    public static final byte COMBINING_SPACING_MARK = 8;
    public static final byte CONNECTOR_PUNCTUATION = 23;
    public static final byte CONTROL = 15;
    public static final byte CURRENCY_SYMBOL = 26;
    public static final byte DASH_PUNCTUATION = 20;
    public static final byte DECIMAL_DIGIT_NUMBER = 9;
    private static final byte[] DIRECTIONALITY;
    public static final byte DIRECTIONALITY_ARABIC_NUMBER = 6;
    public static final byte DIRECTIONALITY_BOUNDARY_NEUTRAL = 9;
    public static final byte DIRECTIONALITY_COMMON_NUMBER_SEPARATOR = 7;
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER = 3;
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR = 4;
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR = 5;
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT = 0;
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING = 14;
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE = 15;
    public static final byte DIRECTIONALITY_NONSPACING_MARK = 8;
    public static final byte DIRECTIONALITY_OTHER_NEUTRALS = 13;
    public static final byte DIRECTIONALITY_PARAGRAPH_SEPARATOR = 10;
    public static final byte DIRECTIONALITY_POP_DIRECTIONAL_FORMAT = 18;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT = 1;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC = 2;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING = 16;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE = 17;
    public static final byte DIRECTIONALITY_SEGMENT_SEPARATOR = 11;
    public static final byte DIRECTIONALITY_UNDEFINED = -1;
    public static final byte DIRECTIONALITY_WHITESPACE = 12;
    public static final byte ENCLOSING_MARK = 7;
    public static final byte END_PUNCTUATION = 22;
    static final int ERROR = -1;
    public static final byte FINAL_QUOTE_PUNCTUATION = 30;
    public static final byte FORMAT = 16;
    public static final byte INITIAL_QUOTE_PUNCTUATION = 29;
    public static final byte LETTER_NUMBER = 10;
    public static final byte LINE_SEPARATOR = 13;
    public static final byte LOWERCASE_LETTER = 2;
    public static final byte MATH_SYMBOL = 25;
    public static final int MAX_CODE_POINT = 1114111;
    public static final char MAX_HIGH_SURROGATE = '\udbff';
    public static final char MAX_LOW_SURROGATE = '\udfff';
    public static final int MAX_RADIX = 36;
    public static final char MAX_SURROGATE = '\udfff';
    public static final char MAX_VALUE = '\uffff';
    public static final int MIN_CODE_POINT = 0;
    public static final char MIN_HIGH_SURROGATE = '\ud800';
    public static final char MIN_LOW_SURROGATE = '\udc00';
    public static final int MIN_RADIX = 2;
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 65536;
    public static final char MIN_SURROGATE = '\ud800';
    public static final char MIN_VALUE = '\u0000';
    public static final byte MODIFIER_LETTER = 4;
    public static final byte MODIFIER_SYMBOL = 27;
    public static final byte NON_SPACING_MARK = 6;
    public static final byte OTHER_LETTER = 5;
    public static final byte OTHER_NUMBER = 11;
    public static final byte OTHER_PUNCTUATION = 24;
    public static final byte OTHER_SYMBOL = 28;
    public static final byte PARAGRAPH_SEPARATOR = 14;
    public static final byte PRIVATE_USE = 18;
    public static final int SIZE = 16;
    public static final byte SPACE_SEPARATOR = 12;
    public static final byte START_PUNCTUATION = 21;
    public static final byte SURROGATE = 19;
    public static final byte TITLECASE_LETTER = 3;
    public static final Class<Character> TYPE;
    public static final byte UNASSIGNED = 0;
    public static final byte UPPERCASE_LETTER = 1;
    private static final long serialVersionUID = 3786198910865385080L;
    private final char value;

    static {
        TYPE = Class.getPrimitiveClass("char");
        DIRECTIONALITY = new byte[]{0, 1, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 2, 16, 17, 18, 8, 9};
    }

    public Character(char c) {
        this.value = c;
    }

    public static int charCount(int n) {
        n = n >= 65536 ? 2 : 1;
        return n;
    }

    public static int codePointAt(CharSequence charSequence, int n) {
        char c;
        char c2 = charSequence.charAt(n);
        if (Character.isHighSurrogate(c2) && ++n < charSequence.length() && Character.isLowSurrogate(c = charSequence.charAt(n))) {
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    public static int codePointAt(char[] arrc, int n) {
        return Character.codePointAtImpl(arrc, n, arrc.length);
    }

    public static int codePointAt(char[] arrc, int n, int n2) {
        if (n < n2 && n2 >= 0 && n2 <= arrc.length) {
            return Character.codePointAtImpl(arrc, n, n2);
        }
        throw new IndexOutOfBoundsException();
    }

    static int codePointAtImpl(char[] arrc, int n, int n2) {
        char c;
        char c2 = arrc[n];
        if (Character.isHighSurrogate(c2) && ++n < n2 && Character.isLowSurrogate(c = arrc[n])) {
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    public static int codePointBefore(CharSequence charSequence, int n) {
        char c;
        char c2;
        if (Character.isLowSurrogate(c2 = charSequence.charAt(--n)) && n > 0 && Character.isHighSurrogate(c = charSequence.charAt(n - 1))) {
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    public static int codePointBefore(char[] arrc, int n) {
        return Character.codePointBeforeImpl(arrc, n, 0);
    }

    public static int codePointBefore(char[] arrc, int n, int n2) {
        if (n > n2 && n2 >= 0 && n2 < arrc.length) {
            return Character.codePointBeforeImpl(arrc, n, n2);
        }
        throw new IndexOutOfBoundsException();
    }

    static int codePointBeforeImpl(char[] arrc, int n, int n2) {
        char c;
        char c2;
        if (Character.isLowSurrogate(c2 = arrc[--n]) && n > n2 && Character.isHighSurrogate(c = arrc[n - 1])) {
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    public static int codePointCount(CharSequence charSequence, int n, int n2) {
        int n3 = charSequence.length();
        if (n >= 0 && n2 <= n3 && n <= n2) {
            n3 = n2 - n;
            while (n < n2) {
                int n4 = n + 1;
                if (Character.isHighSurrogate(charSequence.charAt(n)) && n4 < n2 && Character.isLowSurrogate(charSequence.charAt(n4))) {
                    --n3;
                    n = n4 + 1;
                    continue;
                }
                n = n4;
            }
            return n3;
        }
        throw new IndexOutOfBoundsException();
    }

    public static int codePointCount(char[] arrc, int n, int n2) {
        if (n2 <= arrc.length - n && n >= 0 && n2 >= 0) {
            return Character.codePointCountImpl(arrc, n, n2);
        }
        throw new IndexOutOfBoundsException();
    }

    static int codePointCountImpl(char[] arrc, int n, int n2) {
        int n3 = n + n2;
        while (n < n3) {
            int n4 = n + 1;
            if (Character.isHighSurrogate(arrc[n]) && n4 < n3 && Character.isLowSurrogate(arrc[n4])) {
                --n2;
                n = n4 + 1;
                continue;
            }
            n = n4;
        }
        return n2;
    }

    public static int compare(char c, char c2) {
        return c - c2;
    }

    public static int digit(char c, int n) {
        return Character.digit((int)c, n);
    }

    public static int digit(int n, int n2) {
        int n3 = -1;
        if (n2 >= 2 && n2 <= 36) {
            if (n < 128) {
                int n4;
                int n5 = -1;
                if (48 <= n && n <= 57) {
                    n4 = n - 48;
                } else if (97 <= n && n <= 122) {
                    n4 = n - 97 + 10;
                } else {
                    n4 = n5;
                    if (65 <= n) {
                        n4 = n5;
                        if (n <= 90) {
                            n4 = n - 65 + 10;
                        }
                    }
                }
                n = n3;
                if (n4 < n2) {
                    n = n4;
                }
                return n;
            }
            return Character.digitImpl(n, n2);
        }
        return -1;
    }

    @FastNative
    static native int digitImpl(int var0, int var1);

    public static char forDigit(int n, int n2) {
        if (n < n2 && n >= 0) {
            if (n2 >= 2 && n2 <= 36) {
                if (n < 10) {
                    return (char)(n + 48);
                }
                return (char)(n + 87);
            }
            return '\u0000';
        }
        return '\u0000';
    }

    public static byte getDirectionality(char c) {
        return Character.getDirectionality((int)c);
    }

    public static byte getDirectionality(int n) {
        byte[] arrby;
        if (Character.getType(n) == 0) {
            return -1;
        }
        if ((n = (int)Character.getDirectionalityImpl(n)) >= 0 && n < (arrby = DIRECTIONALITY).length) {
            return arrby[n];
        }
        return -1;
    }

    @FastNative
    static native byte getDirectionalityImpl(int var0);

    public static String getName(int n) {
        if (Character.isValidCodePoint(n)) {
            Object object = Character.getNameImpl(n);
            if (object != null) {
                return object;
            }
            if (Character.getType(n) == 0) {
                return null;
            }
            object = UnicodeBlock.of(n);
            if (object != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((Subset)object).toString().replace('_', ' '));
                stringBuilder.append(" ");
                stringBuilder.append(Integer.toHexString(n).toUpperCase(Locale.ENGLISH));
                return stringBuilder.toString();
            }
            return Integer.toHexString(n).toUpperCase(Locale.ENGLISH);
        }
        throw new IllegalArgumentException();
    }

    private static native String getNameImpl(int var0);

    public static int getNumericValue(char c) {
        return Character.getNumericValue((int)c);
    }

    public static int getNumericValue(int n) {
        if (n < 128) {
            if (n >= 48 && n <= 57) {
                return n - 48;
            }
            if (n >= 97 && n <= 122) {
                return n - 87;
            }
            if (n >= 65 && n <= 90) {
                return n - 55;
            }
            return -1;
        }
        if (n >= 65313 && n <= 65338) {
            return n - 65303;
        }
        if (n >= 65345 && n <= 65370) {
            return n - 65335;
        }
        return Character.getNumericValueImpl(n);
    }

    @FastNative
    static native int getNumericValueImpl(int var0);

    public static int getType(char c) {
        return Character.getType((int)c);
    }

    public static int getType(int n) {
        if ((n = Character.getTypeImpl(n)) <= 16) {
            return n;
        }
        return n + 1;
    }

    @FastNative
    static native int getTypeImpl(int var0);

    public static int hashCode(char c) {
        return c;
    }

    public static char highSurrogate(int n) {
        return (char)((n >>> 10) + 55232);
    }

    public static boolean isAlphabetic(int n) {
        return Character.isAlphabeticImpl(n);
    }

    @FastNative
    static native boolean isAlphabeticImpl(int var0);

    public static boolean isBmpCodePoint(int n) {
        boolean bl = n >>> 16 == 0;
        return bl;
    }

    public static boolean isDefined(char c) {
        return Character.isDefined((int)c);
    }

    public static boolean isDefined(int n) {
        return Character.isDefinedImpl(n);
    }

    @FastNative
    static native boolean isDefinedImpl(int var0);

    public static boolean isDigit(char c) {
        return Character.isDigit((int)c);
    }

    public static boolean isDigit(int n) {
        return Character.isDigitImpl(n);
    }

    @FastNative
    static native boolean isDigitImpl(int var0);

    public static boolean isHighSurrogate(char c) {
        boolean bl = c >= '\ud800' && c < '\udc00';
        return bl;
    }

    public static boolean isISOControl(char c) {
        return Character.isISOControl((int)c);
    }

    public static boolean isISOControl(int n) {
        boolean bl = n <= 159 && (n >= 127 || n >>> 5 == 0);
        return bl;
    }

    public static boolean isIdentifierIgnorable(char c) {
        return Character.isIdentifierIgnorable((int)c);
    }

    public static boolean isIdentifierIgnorable(int n) {
        return Character.isIdentifierIgnorableImpl(n);
    }

    @FastNative
    static native boolean isIdentifierIgnorableImpl(int var0);

    public static boolean isIdeographic(int n) {
        return Character.isIdeographicImpl(n);
    }

    @FastNative
    static native boolean isIdeographicImpl(int var0);

    public static boolean isJavaIdentifierPart(char c) {
        return Character.isJavaIdentifierPart((int)c);
    }

    public static boolean isJavaIdentifierPart(int n) {
        boolean bl;
        block8 : {
            block7 : {
                boolean bl2 = false;
                boolean bl3 = false;
                bl = false;
                if (n < 64) {
                    if ((1L << n & 287948970162897407L) != 0L) {
                        bl = true;
                    }
                    return bl;
                }
                if (n < 128) {
                    bl = bl2;
                    if ((1L << n - 64 & -8646911290859585538L) != 0L) {
                        bl = true;
                    }
                    return bl;
                }
                if ((1 << Character.getType(n) & 75564926) != 0 || n >= 0 && n <= 8 || n >= 14 && n <= 27) break block7;
                bl = bl3;
                if (n < 127) break block8;
                bl = bl3;
                if (n > 159) break block8;
            }
            bl = true;
        }
        return bl;
    }

    public static boolean isJavaIdentifierStart(char c) {
        return Character.isJavaIdentifierStart((int)c);
    }

    public static boolean isJavaIdentifierStart(int n) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (n < 64) {
            if (n == 36) {
                bl3 = true;
            }
            return bl3;
        }
        if (n < 128) {
            bl3 = bl;
            if ((576460745995190270L & 1L << n - 64) != 0L) {
                bl3 = true;
            }
            return bl3;
        }
        bl3 = bl2;
        if ((1 << Character.getType(n) & 75498558) != 0) {
            bl3 = true;
        }
        return bl3;
    }

    @Deprecated
    public static boolean isJavaLetter(char c) {
        return Character.isJavaIdentifierStart(c);
    }

    @Deprecated
    public static boolean isJavaLetterOrDigit(char c) {
        return Character.isJavaIdentifierPart(c);
    }

    public static boolean isLetter(char c) {
        return Character.isLetter((int)c);
    }

    public static boolean isLetter(int n) {
        return Character.isLetterImpl(n);
    }

    @FastNative
    static native boolean isLetterImpl(int var0);

    public static boolean isLetterOrDigit(char c) {
        return Character.isLetterOrDigit((int)c);
    }

    public static boolean isLetterOrDigit(int n) {
        return Character.isLetterOrDigitImpl(n);
    }

    @FastNative
    static native boolean isLetterOrDigitImpl(int var0);

    public static boolean isLowSurrogate(char c) {
        boolean bl = c >= '\udc00' && c < '\ue000';
        return bl;
    }

    public static boolean isLowerCase(char c) {
        return Character.isLowerCase((int)c);
    }

    public static boolean isLowerCase(int n) {
        return Character.isLowerCaseImpl(n);
    }

    @FastNative
    static native boolean isLowerCaseImpl(int var0);

    public static boolean isMirrored(char c) {
        return Character.isMirrored((int)c);
    }

    public static boolean isMirrored(int n) {
        return Character.isMirroredImpl(n);
    }

    @FastNative
    static native boolean isMirroredImpl(int var0);

    @Deprecated
    public static boolean isSpace(char c) {
        boolean bl = c <= ' ' && (4294981120L >> c & 1L) != 0L;
        return bl;
    }

    public static boolean isSpaceChar(char c) {
        return Character.isSpaceChar((int)c);
    }

    public static boolean isSpaceChar(int n) {
        boolean bl = true;
        if (n != 32 && n != 160) {
            if (n < 4096) {
                return false;
            }
            if (n != 5760 && n != 6158) {
                if (n < 8192) {
                    return false;
                }
                if (n <= 65535) {
                    boolean bl2 = bl;
                    if (n > 8202) {
                        bl2 = bl;
                        if (n != 8232) {
                            bl2 = bl;
                            if (n != 8233) {
                                bl2 = bl;
                                if (n != 8239) {
                                    bl2 = bl;
                                    if (n != 8287) {
                                        bl2 = n == 12288 ? bl : false;
                                    }
                                }
                            }
                        }
                    }
                    return bl2;
                }
                return Character.isSpaceCharImpl(n);
            }
            return true;
        }
        return true;
    }

    @FastNative
    static native boolean isSpaceCharImpl(int var0);

    public static boolean isSupplementaryCodePoint(int n) {
        boolean bl = n >= 65536 && n < 1114112;
        return bl;
    }

    public static boolean isSurrogate(char c) {
        boolean bl = c >= '\ud800' && c < '\ue000';
        return bl;
    }

    public static boolean isSurrogatePair(char c, char c2) {
        boolean bl = Character.isHighSurrogate(c) && Character.isLowSurrogate(c2);
        return bl;
    }

    public static boolean isTitleCase(char c) {
        return Character.isTitleCase((int)c);
    }

    public static boolean isTitleCase(int n) {
        return Character.isTitleCaseImpl(n);
    }

    @FastNative
    static native boolean isTitleCaseImpl(int var0);

    public static boolean isUnicodeIdentifierPart(char c) {
        return Character.isUnicodeIdentifierPart((int)c);
    }

    public static boolean isUnicodeIdentifierPart(int n) {
        return Character.isUnicodeIdentifierPartImpl(n);
    }

    @FastNative
    static native boolean isUnicodeIdentifierPartImpl(int var0);

    public static boolean isUnicodeIdentifierStart(char c) {
        return Character.isUnicodeIdentifierStart((int)c);
    }

    public static boolean isUnicodeIdentifierStart(int n) {
        return Character.isUnicodeIdentifierStartImpl(n);
    }

    @FastNative
    static native boolean isUnicodeIdentifierStartImpl(int var0);

    public static boolean isUpperCase(char c) {
        return Character.isUpperCase((int)c);
    }

    public static boolean isUpperCase(int n) {
        return Character.isUpperCaseImpl(n);
    }

    @FastNative
    static native boolean isUpperCaseImpl(int var0);

    public static boolean isValidCodePoint(int n) {
        boolean bl = n >>> 16 < 17;
        return bl;
    }

    public static boolean isWhitespace(char c) {
        return Character.isWhitespace((int)c);
    }

    public static boolean isWhitespace(int n) {
        boolean bl = true;
        if (n >= 28 && n <= 32 || n >= 9 && n <= 13) {
            return true;
        }
        if (n < 4096) {
            return false;
        }
        if (n != 5760 && n != 6158) {
            if (n < 8192) {
                return false;
            }
            if (n != 8199 && n != 8239) {
                if (n <= 65535) {
                    boolean bl2 = bl;
                    if (n > 8202) {
                        bl2 = bl;
                        if (n != 8232) {
                            bl2 = bl;
                            if (n != 8233) {
                                bl2 = bl;
                                if (n != 8287) {
                                    bl2 = n == 12288 ? bl : false;
                                }
                            }
                        }
                    }
                    return bl2;
                }
                return Character.isWhitespaceImpl(n);
            }
            return false;
        }
        return true;
    }

    @FastNative
    static native boolean isWhitespaceImpl(int var0);

    public static char lowSurrogate(int n) {
        return (char)((n & 1023) + 56320);
    }

    public static int offsetByCodePoints(CharSequence charSequence, int n, int n2) {
        block10 : {
            block13 : {
                block12 : {
                    block11 : {
                        int n3;
                        int n4 = charSequence.length();
                        if (n < 0 || n > n4) break block10;
                        if (n2 < 0) break block11;
                        for (n3 = 0; n < n4 && n3 < n2; ++n3) {
                            int n5 = n + 1;
                            n = Character.isHighSurrogate(charSequence.charAt(n)) && n5 < n4 && Character.isLowSurrogate(charSequence.charAt(n5)) ? n5 + 1 : n5;
                        }
                        if (n3 < n2) {
                            throw new IndexOutOfBoundsException();
                        }
                        break block12;
                    }
                    while (n > 0 && n2 < 0) {
                        int n6;
                        n = n6 = n - 1;
                        if (Character.isLowSurrogate(charSequence.charAt(n6))) {
                            n = n6;
                            if (n6 > 0) {
                                n = n6;
                                if (Character.isHighSurrogate(charSequence.charAt(n6 - 1))) {
                                    n = n6 - 1;
                                }
                            }
                        }
                        ++n2;
                    }
                    if (n2 < 0) break block13;
                }
                return n;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IndexOutOfBoundsException();
    }

    public static int offsetByCodePoints(char[] arrc, int n, int n2, int n3, int n4) {
        if (n2 <= arrc.length - n && n >= 0 && n2 >= 0 && n3 >= n && n3 <= n + n2) {
            return Character.offsetByCodePointsImpl(arrc, n, n2, n3, n4);
        }
        throw new IndexOutOfBoundsException();
    }

    static int offsetByCodePointsImpl(char[] arrc, int n, int n2, int n3, int n4) {
        block11 : {
            block10 : {
                block8 : {
                    block9 : {
                        if (n4 < 0) break block8;
                        int n5 = n + n2;
                        for (n = 0; n3 < n5 && n < n4; ++n) {
                            n2 = n3 + 1;
                            n3 = Character.isHighSurrogate(arrc[n3]) && n2 < n5 && Character.isLowSurrogate(arrc[n2]) ? n2 + 1 : n2;
                        }
                        if (n < n4) break block9;
                        n2 = n3;
                        break block10;
                    }
                    throw new IndexOutOfBoundsException();
                }
                n2 = n3;
                while (n2 > n && n4 < 0) {
                    n2 = n3 = n2 - 1;
                    if (Character.isLowSurrogate(arrc[n3])) {
                        n2 = n3;
                        if (n3 > n) {
                            n2 = n3;
                            if (Character.isHighSurrogate(arrc[n3 - 1])) {
                                n2 = n3 - 1;
                            }
                        }
                    }
                    ++n4;
                }
                if (n4 < 0) break block11;
            }
            return n2;
        }
        throw new IndexOutOfBoundsException();
    }

    public static char reverseBytes(char c) {
        return (char)((65280 & c) >> 8 | c << 8);
    }

    public static int toChars(int n, char[] arrc, int n2) {
        if (Character.isBmpCodePoint(n)) {
            arrc[n2] = (char)n;
            return 1;
        }
        if (Character.isValidCodePoint(n)) {
            Character.toSurrogates(n, arrc, n2);
            return 2;
        }
        throw new IllegalArgumentException();
    }

    public static char[] toChars(int n) {
        if (Character.isBmpCodePoint(n)) {
            return new char[]{(char)n};
        }
        if (Character.isValidCodePoint(n)) {
            char[] arrc = new char[2];
            Character.toSurrogates(n, arrc, 0);
            return arrc;
        }
        throw new IllegalArgumentException();
    }

    public static int toCodePoint(char c, char c2) {
        return (c << 10) + c2 - 56613888;
    }

    public static char toLowerCase(char c) {
        return (char)Character.toLowerCase((int)c);
    }

    public static int toLowerCase(int n) {
        if (n >= 65 && n <= 90) {
            return n + 32;
        }
        if (n < 128) {
            return n;
        }
        return Character.toLowerCaseImpl(n);
    }

    @FastNative
    static native int toLowerCaseImpl(int var0);

    public static String toString(char c) {
        return String.valueOf(c);
    }

    static void toSurrogates(int n, char[] arrc, int n2) {
        arrc[n2 + 1] = Character.lowSurrogate(n);
        arrc[n2] = Character.highSurrogate(n);
    }

    public static char toTitleCase(char c) {
        return (char)Character.toTitleCase((int)c);
    }

    public static int toTitleCase(int n) {
        return Character.toTitleCaseImpl(n);
    }

    @FastNative
    static native int toTitleCaseImpl(int var0);

    public static char toUpperCase(char c) {
        return (char)Character.toUpperCase((int)c);
    }

    public static int toUpperCase(int n) {
        if (n >= 97 && n <= 122) {
            return n - 32;
        }
        if (n < 128) {
            return n;
        }
        return Character.toUpperCaseImpl(n);
    }

    @FastNative
    static native int toUpperCaseImpl(int var0);

    public static Character valueOf(char c) {
        if (c <= '') {
            return CharacterCache.cache[c];
        }
        return new Character(c);
    }

    public char charValue() {
        return this.value;
    }

    @Override
    public int compareTo(Character c) {
        return Character.compare(this.value, c.value);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Character;
        boolean bl2 = false;
        if (bl) {
            if (this.value == ((Character)object).charValue()) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        return Character.hashCode(this.value);
    }

    public String toString() {
        return String.valueOf(new char[]{this.value});
    }

    private static class CharacterCache {
        static final Character[] cache;

        static {
            Character[] arrcharacter;
            cache = new Character[128];
            for (int i = 0; i < (arrcharacter = cache).length; ++i) {
                arrcharacter[i] = new Character((char)i);
            }
        }

        private CharacterCache() {
        }
    }

    public static class Subset {
        private String name;

        protected Subset(String string) {
            if (string != null) {
                this.name = string;
                return;
            }
            throw new NullPointerException("name");
        }

        public final boolean equals(Object object) {
            boolean bl = this == object;
            return bl;
        }

        public final int hashCode() {
            return super.hashCode();
        }

        public final String toString() {
            return this.name;
        }
    }

    public static final class UnicodeBlock
    extends Subset {
        public static final UnicodeBlock AEGEAN_NUMBERS;
        public static final UnicodeBlock ALCHEMICAL_SYMBOLS;
        public static final UnicodeBlock ALPHABETIC_PRESENTATION_FORMS;
        public static final UnicodeBlock ANCIENT_GREEK_MUSICAL_NOTATION;
        public static final UnicodeBlock ANCIENT_GREEK_NUMBERS;
        public static final UnicodeBlock ANCIENT_SYMBOLS;
        public static final UnicodeBlock ARABIC;
        public static final UnicodeBlock ARABIC_EXTENDED_A;
        public static final UnicodeBlock ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS;
        public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_A;
        public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_B;
        public static final UnicodeBlock ARABIC_SUPPLEMENT;
        public static final UnicodeBlock ARMENIAN;
        public static final UnicodeBlock ARROWS;
        public static final UnicodeBlock AVESTAN;
        public static final UnicodeBlock BALINESE;
        public static final UnicodeBlock BAMUM;
        public static final UnicodeBlock BAMUM_SUPPLEMENT;
        public static final UnicodeBlock BASIC_LATIN;
        public static final UnicodeBlock BATAK;
        public static final UnicodeBlock BENGALI;
        public static final UnicodeBlock BLOCK_ELEMENTS;
        public static final UnicodeBlock BOPOMOFO;
        public static final UnicodeBlock BOPOMOFO_EXTENDED;
        public static final UnicodeBlock BOX_DRAWING;
        public static final UnicodeBlock BRAHMI;
        public static final UnicodeBlock BRAILLE_PATTERNS;
        public static final UnicodeBlock BUGINESE;
        public static final UnicodeBlock BUHID;
        public static final UnicodeBlock BYZANTINE_MUSICAL_SYMBOLS;
        public static final UnicodeBlock CARIAN;
        public static final UnicodeBlock CHAKMA;
        public static final UnicodeBlock CHAM;
        public static final UnicodeBlock CHEROKEE;
        public static final UnicodeBlock CJK_COMPATIBILITY;
        public static final UnicodeBlock CJK_COMPATIBILITY_FORMS;
        public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS;
        public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
        public static final UnicodeBlock CJK_RADICALS_SUPPLEMENT;
        public static final UnicodeBlock CJK_STROKES;
        public static final UnicodeBlock CJK_SYMBOLS_AND_PUNCTUATION;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D;
        public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS;
        public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS_SUPPLEMENT;
        public static final UnicodeBlock COMBINING_HALF_MARKS;
        public static final UnicodeBlock COMBINING_MARKS_FOR_SYMBOLS;
        public static final UnicodeBlock COMMON_INDIC_NUMBER_FORMS;
        public static final UnicodeBlock CONTROL_PICTURES;
        public static final UnicodeBlock COPTIC;
        public static final UnicodeBlock COUNTING_ROD_NUMERALS;
        public static final UnicodeBlock CUNEIFORM;
        public static final UnicodeBlock CUNEIFORM_NUMBERS_AND_PUNCTUATION;
        public static final UnicodeBlock CURRENCY_SYMBOLS;
        public static final UnicodeBlock CYPRIOT_SYLLABARY;
        public static final UnicodeBlock CYRILLIC;
        public static final UnicodeBlock CYRILLIC_EXTENDED_A;
        public static final UnicodeBlock CYRILLIC_EXTENDED_B;
        public static final UnicodeBlock CYRILLIC_SUPPLEMENTARY;
        public static final UnicodeBlock DESERET;
        public static final UnicodeBlock DEVANAGARI;
        public static final UnicodeBlock DEVANAGARI_EXTENDED;
        public static final UnicodeBlock DINGBATS;
        public static final UnicodeBlock DOMINO_TILES;
        public static final UnicodeBlock EGYPTIAN_HIEROGLYPHS;
        public static final UnicodeBlock EMOTICONS;
        public static final UnicodeBlock ENCLOSED_ALPHANUMERICS;
        public static final UnicodeBlock ENCLOSED_ALPHANUMERIC_SUPPLEMENT;
        public static final UnicodeBlock ENCLOSED_CJK_LETTERS_AND_MONTHS;
        public static final UnicodeBlock ENCLOSED_IDEOGRAPHIC_SUPPLEMENT;
        public static final UnicodeBlock ETHIOPIC;
        public static final UnicodeBlock ETHIOPIC_EXTENDED;
        public static final UnicodeBlock ETHIOPIC_EXTENDED_A;
        public static final UnicodeBlock ETHIOPIC_SUPPLEMENT;
        public static final UnicodeBlock GENERAL_PUNCTUATION;
        public static final UnicodeBlock GEOMETRIC_SHAPES;
        public static final UnicodeBlock GEORGIAN;
        public static final UnicodeBlock GEORGIAN_SUPPLEMENT;
        public static final UnicodeBlock GLAGOLITIC;
        public static final UnicodeBlock GOTHIC;
        public static final UnicodeBlock GREEK;
        public static final UnicodeBlock GREEK_EXTENDED;
        public static final UnicodeBlock GUJARATI;
        public static final UnicodeBlock GURMUKHI;
        public static final UnicodeBlock HALFWIDTH_AND_FULLWIDTH_FORMS;
        public static final UnicodeBlock HANGUL_COMPATIBILITY_JAMO;
        public static final UnicodeBlock HANGUL_JAMO;
        public static final UnicodeBlock HANGUL_JAMO_EXTENDED_A;
        public static final UnicodeBlock HANGUL_JAMO_EXTENDED_B;
        public static final UnicodeBlock HANGUL_SYLLABLES;
        public static final UnicodeBlock HANUNOO;
        public static final UnicodeBlock HEBREW;
        public static final UnicodeBlock HIGH_PRIVATE_USE_SURROGATES;
        public static final UnicodeBlock HIGH_SURROGATES;
        public static final UnicodeBlock HIRAGANA;
        public static final UnicodeBlock IDEOGRAPHIC_DESCRIPTION_CHARACTERS;
        public static final UnicodeBlock IMPERIAL_ARAMAIC;
        public static final UnicodeBlock INSCRIPTIONAL_PAHLAVI;
        public static final UnicodeBlock INSCRIPTIONAL_PARTHIAN;
        public static final UnicodeBlock IPA_EXTENSIONS;
        public static final UnicodeBlock JAVANESE;
        public static final UnicodeBlock KAITHI;
        public static final UnicodeBlock KANA_SUPPLEMENT;
        public static final UnicodeBlock KANBUN;
        public static final UnicodeBlock KANGXI_RADICALS;
        public static final UnicodeBlock KANNADA;
        public static final UnicodeBlock KATAKANA;
        public static final UnicodeBlock KATAKANA_PHONETIC_EXTENSIONS;
        public static final UnicodeBlock KAYAH_LI;
        public static final UnicodeBlock KHAROSHTHI;
        public static final UnicodeBlock KHMER;
        public static final UnicodeBlock KHMER_SYMBOLS;
        public static final UnicodeBlock LAO;
        public static final UnicodeBlock LATIN_1_SUPPLEMENT;
        public static final UnicodeBlock LATIN_EXTENDED_A;
        public static final UnicodeBlock LATIN_EXTENDED_ADDITIONAL;
        public static final UnicodeBlock LATIN_EXTENDED_B;
        public static final UnicodeBlock LATIN_EXTENDED_C;
        public static final UnicodeBlock LATIN_EXTENDED_D;
        public static final UnicodeBlock LEPCHA;
        public static final UnicodeBlock LETTERLIKE_SYMBOLS;
        public static final UnicodeBlock LIMBU;
        public static final UnicodeBlock LINEAR_B_IDEOGRAMS;
        public static final UnicodeBlock LINEAR_B_SYLLABARY;
        public static final UnicodeBlock LISU;
        public static final UnicodeBlock LOW_SURROGATES;
        public static final UnicodeBlock LYCIAN;
        public static final UnicodeBlock LYDIAN;
        public static final UnicodeBlock MAHJONG_TILES;
        public static final UnicodeBlock MALAYALAM;
        public static final UnicodeBlock MANDAIC;
        public static final UnicodeBlock MATHEMATICAL_ALPHANUMERIC_SYMBOLS;
        public static final UnicodeBlock MATHEMATICAL_OPERATORS;
        public static final UnicodeBlock MEETEI_MAYEK;
        public static final UnicodeBlock MEETEI_MAYEK_EXTENSIONS;
        public static final UnicodeBlock MEROITIC_CURSIVE;
        public static final UnicodeBlock MEROITIC_HIEROGLYPHS;
        public static final UnicodeBlock MIAO;
        public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A;
        public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_ARROWS;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS;
        public static final UnicodeBlock MISCELLANEOUS_TECHNICAL;
        public static final UnicodeBlock MODIFIER_TONE_LETTERS;
        public static final UnicodeBlock MONGOLIAN;
        public static final UnicodeBlock MUSICAL_SYMBOLS;
        public static final UnicodeBlock MYANMAR;
        public static final UnicodeBlock MYANMAR_EXTENDED_A;
        public static final UnicodeBlock NEW_TAI_LUE;
        public static final UnicodeBlock NKO;
        public static final UnicodeBlock NUMBER_FORMS;
        public static final UnicodeBlock OGHAM;
        public static final UnicodeBlock OLD_ITALIC;
        public static final UnicodeBlock OLD_PERSIAN;
        public static final UnicodeBlock OLD_SOUTH_ARABIAN;
        public static final UnicodeBlock OLD_TURKIC;
        public static final UnicodeBlock OL_CHIKI;
        public static final UnicodeBlock OPTICAL_CHARACTER_RECOGNITION;
        public static final UnicodeBlock ORIYA;
        public static final UnicodeBlock OSMANYA;
        public static final UnicodeBlock PHAGS_PA;
        public static final UnicodeBlock PHAISTOS_DISC;
        public static final UnicodeBlock PHOENICIAN;
        public static final UnicodeBlock PHONETIC_EXTENSIONS;
        public static final UnicodeBlock PHONETIC_EXTENSIONS_SUPPLEMENT;
        public static final UnicodeBlock PLAYING_CARDS;
        public static final UnicodeBlock PRIVATE_USE_AREA;
        public static final UnicodeBlock REJANG;
        public static final UnicodeBlock RUMI_NUMERAL_SYMBOLS;
        public static final UnicodeBlock RUNIC;
        public static final UnicodeBlock SAMARITAN;
        public static final UnicodeBlock SAURASHTRA;
        public static final UnicodeBlock SHARADA;
        public static final UnicodeBlock SHAVIAN;
        public static final UnicodeBlock SINHALA;
        public static final UnicodeBlock SMALL_FORM_VARIANTS;
        public static final UnicodeBlock SORA_SOMPENG;
        public static final UnicodeBlock SPACING_MODIFIER_LETTERS;
        public static final UnicodeBlock SPECIALS;
        public static final UnicodeBlock SUNDANESE;
        public static final UnicodeBlock SUNDANESE_SUPPLEMENT;
        public static final UnicodeBlock SUPERSCRIPTS_AND_SUBSCRIPTS;
        public static final UnicodeBlock SUPPLEMENTAL_ARROWS_A;
        public static final UnicodeBlock SUPPLEMENTAL_ARROWS_B;
        public static final UnicodeBlock SUPPLEMENTAL_MATHEMATICAL_OPERATORS;
        public static final UnicodeBlock SUPPLEMENTAL_PUNCTUATION;
        public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_A;
        public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_B;
        @Deprecated
        public static final UnicodeBlock SURROGATES_AREA;
        public static final UnicodeBlock SYLOTI_NAGRI;
        public static final UnicodeBlock SYRIAC;
        public static final UnicodeBlock TAGALOG;
        public static final UnicodeBlock TAGBANWA;
        public static final UnicodeBlock TAGS;
        public static final UnicodeBlock TAI_LE;
        public static final UnicodeBlock TAI_THAM;
        public static final UnicodeBlock TAI_VIET;
        public static final UnicodeBlock TAI_XUAN_JING_SYMBOLS;
        public static final UnicodeBlock TAKRI;
        public static final UnicodeBlock TAMIL;
        public static final UnicodeBlock TELUGU;
        public static final UnicodeBlock THAANA;
        public static final UnicodeBlock THAI;
        public static final UnicodeBlock TIBETAN;
        public static final UnicodeBlock TIFINAGH;
        public static final UnicodeBlock TRANSPORT_AND_MAP_SYMBOLS;
        public static final UnicodeBlock UGARITIC;
        public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS;
        public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED;
        public static final UnicodeBlock VAI;
        public static final UnicodeBlock VARIATION_SELECTORS;
        public static final UnicodeBlock VARIATION_SELECTORS_SUPPLEMENT;
        public static final UnicodeBlock VEDIC_EXTENSIONS;
        public static final UnicodeBlock VERTICAL_FORMS;
        public static final UnicodeBlock YIJING_HEXAGRAM_SYMBOLS;
        public static final UnicodeBlock YI_RADICALS;
        public static final UnicodeBlock YI_SYLLABLES;
        private static final int[] blockStarts;
        private static final UnicodeBlock[] blocks;
        private static Map<String, UnicodeBlock> map;

        static {
            map = new HashMap<String, UnicodeBlock>(256);
            BASIC_LATIN = new UnicodeBlock("BASIC_LATIN", "BASIC LATIN", "BASICLATIN");
            LATIN_1_SUPPLEMENT = new UnicodeBlock("LATIN_1_SUPPLEMENT", "LATIN-1 SUPPLEMENT", "LATIN-1SUPPLEMENT");
            LATIN_EXTENDED_A = new UnicodeBlock("LATIN_EXTENDED_A", "LATIN EXTENDED-A", "LATINEXTENDED-A");
            LATIN_EXTENDED_B = new UnicodeBlock("LATIN_EXTENDED_B", "LATIN EXTENDED-B", "LATINEXTENDED-B");
            IPA_EXTENSIONS = new UnicodeBlock("IPA_EXTENSIONS", "IPA EXTENSIONS", "IPAEXTENSIONS");
            SPACING_MODIFIER_LETTERS = new UnicodeBlock("SPACING_MODIFIER_LETTERS", "SPACING MODIFIER LETTERS", "SPACINGMODIFIERLETTERS");
            COMBINING_DIACRITICAL_MARKS = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS", "COMBINING DIACRITICAL MARKS", "COMBININGDIACRITICALMARKS");
            GREEK = new UnicodeBlock("GREEK", "GREEK AND COPTIC", "GREEKANDCOPTIC");
            CYRILLIC = new UnicodeBlock("CYRILLIC");
            ARMENIAN = new UnicodeBlock("ARMENIAN");
            HEBREW = new UnicodeBlock("HEBREW");
            ARABIC = new UnicodeBlock("ARABIC");
            DEVANAGARI = new UnicodeBlock("DEVANAGARI");
            BENGALI = new UnicodeBlock("BENGALI");
            GURMUKHI = new UnicodeBlock("GURMUKHI");
            GUJARATI = new UnicodeBlock("GUJARATI");
            ORIYA = new UnicodeBlock("ORIYA");
            TAMIL = new UnicodeBlock("TAMIL");
            TELUGU = new UnicodeBlock("TELUGU");
            KANNADA = new UnicodeBlock("KANNADA");
            MALAYALAM = new UnicodeBlock("MALAYALAM");
            THAI = new UnicodeBlock("THAI");
            LAO = new UnicodeBlock("LAO");
            TIBETAN = new UnicodeBlock("TIBETAN");
            GEORGIAN = new UnicodeBlock("GEORGIAN");
            HANGUL_JAMO = new UnicodeBlock("HANGUL_JAMO", "HANGUL JAMO", "HANGULJAMO");
            LATIN_EXTENDED_ADDITIONAL = new UnicodeBlock("LATIN_EXTENDED_ADDITIONAL", "LATIN EXTENDED ADDITIONAL", "LATINEXTENDEDADDITIONAL");
            GREEK_EXTENDED = new UnicodeBlock("GREEK_EXTENDED", "GREEK EXTENDED", "GREEKEXTENDED");
            GENERAL_PUNCTUATION = new UnicodeBlock("GENERAL_PUNCTUATION", "GENERAL PUNCTUATION", "GENERALPUNCTUATION");
            SUPERSCRIPTS_AND_SUBSCRIPTS = new UnicodeBlock("SUPERSCRIPTS_AND_SUBSCRIPTS", "SUPERSCRIPTS AND SUBSCRIPTS", "SUPERSCRIPTSANDSUBSCRIPTS");
            CURRENCY_SYMBOLS = new UnicodeBlock("CURRENCY_SYMBOLS", "CURRENCY SYMBOLS", "CURRENCYSYMBOLS");
            COMBINING_MARKS_FOR_SYMBOLS = new UnicodeBlock("COMBINING_MARKS_FOR_SYMBOLS", "COMBINING DIACRITICAL MARKS FOR SYMBOLS", "COMBININGDIACRITICALMARKSFORSYMBOLS", "COMBINING MARKS FOR SYMBOLS", "COMBININGMARKSFORSYMBOLS");
            LETTERLIKE_SYMBOLS = new UnicodeBlock("LETTERLIKE_SYMBOLS", "LETTERLIKE SYMBOLS", "LETTERLIKESYMBOLS");
            NUMBER_FORMS = new UnicodeBlock("NUMBER_FORMS", "NUMBER FORMS", "NUMBERFORMS");
            ARROWS = new UnicodeBlock("ARROWS");
            MATHEMATICAL_OPERATORS = new UnicodeBlock("MATHEMATICAL_OPERATORS", "MATHEMATICAL OPERATORS", "MATHEMATICALOPERATORS");
            MISCELLANEOUS_TECHNICAL = new UnicodeBlock("MISCELLANEOUS_TECHNICAL", "MISCELLANEOUS TECHNICAL", "MISCELLANEOUSTECHNICAL");
            CONTROL_PICTURES = new UnicodeBlock("CONTROL_PICTURES", "CONTROL PICTURES", "CONTROLPICTURES");
            OPTICAL_CHARACTER_RECOGNITION = new UnicodeBlock("OPTICAL_CHARACTER_RECOGNITION", "OPTICAL CHARACTER RECOGNITION", "OPTICALCHARACTERRECOGNITION");
            ENCLOSED_ALPHANUMERICS = new UnicodeBlock("ENCLOSED_ALPHANUMERICS", "ENCLOSED ALPHANUMERICS", "ENCLOSEDALPHANUMERICS");
            BOX_DRAWING = new UnicodeBlock("BOX_DRAWING", "BOX DRAWING", "BOXDRAWING");
            BLOCK_ELEMENTS = new UnicodeBlock("BLOCK_ELEMENTS", "BLOCK ELEMENTS", "BLOCKELEMENTS");
            GEOMETRIC_SHAPES = new UnicodeBlock("GEOMETRIC_SHAPES", "GEOMETRIC SHAPES", "GEOMETRICSHAPES");
            MISCELLANEOUS_SYMBOLS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS", "MISCELLANEOUS SYMBOLS", "MISCELLANEOUSSYMBOLS");
            DINGBATS = new UnicodeBlock("DINGBATS");
            CJK_SYMBOLS_AND_PUNCTUATION = new UnicodeBlock("CJK_SYMBOLS_AND_PUNCTUATION", "CJK SYMBOLS AND PUNCTUATION", "CJKSYMBOLSANDPUNCTUATION");
            HIRAGANA = new UnicodeBlock("HIRAGANA");
            KATAKANA = new UnicodeBlock("KATAKANA");
            BOPOMOFO = new UnicodeBlock("BOPOMOFO");
            HANGUL_COMPATIBILITY_JAMO = new UnicodeBlock("HANGUL_COMPATIBILITY_JAMO", "HANGUL COMPATIBILITY JAMO", "HANGULCOMPATIBILITYJAMO");
            KANBUN = new UnicodeBlock("KANBUN");
            ENCLOSED_CJK_LETTERS_AND_MONTHS = new UnicodeBlock("ENCLOSED_CJK_LETTERS_AND_MONTHS", "ENCLOSED CJK LETTERS AND MONTHS", "ENCLOSEDCJKLETTERSANDMONTHS");
            CJK_COMPATIBILITY = new UnicodeBlock("CJK_COMPATIBILITY", "CJK COMPATIBILITY", "CJKCOMPATIBILITY");
            CJK_UNIFIED_IDEOGRAPHS = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS", "CJK UNIFIED IDEOGRAPHS", "CJKUNIFIEDIDEOGRAPHS");
            HANGUL_SYLLABLES = new UnicodeBlock("HANGUL_SYLLABLES", "HANGUL SYLLABLES", "HANGULSYLLABLES");
            PRIVATE_USE_AREA = new UnicodeBlock("PRIVATE_USE_AREA", "PRIVATE USE AREA", "PRIVATEUSEAREA");
            CJK_COMPATIBILITY_IDEOGRAPHS = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS", "CJK COMPATIBILITY IDEOGRAPHS", "CJKCOMPATIBILITYIDEOGRAPHS");
            ALPHABETIC_PRESENTATION_FORMS = new UnicodeBlock("ALPHABETIC_PRESENTATION_FORMS", "ALPHABETIC PRESENTATION FORMS", "ALPHABETICPRESENTATIONFORMS");
            ARABIC_PRESENTATION_FORMS_A = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_A", "ARABIC PRESENTATION FORMS-A", "ARABICPRESENTATIONFORMS-A");
            COMBINING_HALF_MARKS = new UnicodeBlock("COMBINING_HALF_MARKS", "COMBINING HALF MARKS", "COMBININGHALFMARKS");
            CJK_COMPATIBILITY_FORMS = new UnicodeBlock("CJK_COMPATIBILITY_FORMS", "CJK COMPATIBILITY FORMS", "CJKCOMPATIBILITYFORMS");
            SMALL_FORM_VARIANTS = new UnicodeBlock("SMALL_FORM_VARIANTS", "SMALL FORM VARIANTS", "SMALLFORMVARIANTS");
            ARABIC_PRESENTATION_FORMS_B = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_B", "ARABIC PRESENTATION FORMS-B", "ARABICPRESENTATIONFORMS-B");
            HALFWIDTH_AND_FULLWIDTH_FORMS = new UnicodeBlock("HALFWIDTH_AND_FULLWIDTH_FORMS", "HALFWIDTH AND FULLWIDTH FORMS", "HALFWIDTHANDFULLWIDTHFORMS");
            SPECIALS = new UnicodeBlock("SPECIALS");
            SURROGATES_AREA = new UnicodeBlock("SURROGATES_AREA", false);
            SYRIAC = new UnicodeBlock("SYRIAC");
            THAANA = new UnicodeBlock("THAANA");
            SINHALA = new UnicodeBlock("SINHALA");
            MYANMAR = new UnicodeBlock("MYANMAR");
            ETHIOPIC = new UnicodeBlock("ETHIOPIC");
            CHEROKEE = new UnicodeBlock("CHEROKEE");
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS", "UNIFIED CANADIAN ABORIGINAL SYLLABICS", "UNIFIEDCANADIANABORIGINALSYLLABICS");
            OGHAM = new UnicodeBlock("OGHAM");
            RUNIC = new UnicodeBlock("RUNIC");
            KHMER = new UnicodeBlock("KHMER");
            MONGOLIAN = new UnicodeBlock("MONGOLIAN");
            BRAILLE_PATTERNS = new UnicodeBlock("BRAILLE_PATTERNS", "BRAILLE PATTERNS", "BRAILLEPATTERNS");
            CJK_RADICALS_SUPPLEMENT = new UnicodeBlock("CJK_RADICALS_SUPPLEMENT", "CJK RADICALS SUPPLEMENT", "CJKRADICALSSUPPLEMENT");
            KANGXI_RADICALS = new UnicodeBlock("KANGXI_RADICALS", "KANGXI RADICALS", "KANGXIRADICALS");
            IDEOGRAPHIC_DESCRIPTION_CHARACTERS = new UnicodeBlock("IDEOGRAPHIC_DESCRIPTION_CHARACTERS", "IDEOGRAPHIC DESCRIPTION CHARACTERS", "IDEOGRAPHICDESCRIPTIONCHARACTERS");
            BOPOMOFO_EXTENDED = new UnicodeBlock("BOPOMOFO_EXTENDED", "BOPOMOFO EXTENDED", "BOPOMOFOEXTENDED");
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A", "CJK UNIFIED IDEOGRAPHS EXTENSION A", "CJKUNIFIEDIDEOGRAPHSEXTENSIONA");
            YI_SYLLABLES = new UnicodeBlock("YI_SYLLABLES", "YI SYLLABLES", "YISYLLABLES");
            YI_RADICALS = new UnicodeBlock("YI_RADICALS", "YI RADICALS", "YIRADICALS");
            CYRILLIC_SUPPLEMENTARY = new UnicodeBlock("CYRILLIC_SUPPLEMENTARY", "CYRILLIC SUPPLEMENTARY", "CYRILLICSUPPLEMENTARY", "CYRILLIC SUPPLEMENT", "CYRILLICSUPPLEMENT");
            TAGALOG = new UnicodeBlock("TAGALOG");
            HANUNOO = new UnicodeBlock("HANUNOO");
            BUHID = new UnicodeBlock("BUHID");
            TAGBANWA = new UnicodeBlock("TAGBANWA");
            LIMBU = new UnicodeBlock("LIMBU");
            TAI_LE = new UnicodeBlock("TAI_LE", "TAI LE", "TAILE");
            KHMER_SYMBOLS = new UnicodeBlock("KHMER_SYMBOLS", "KHMER SYMBOLS", "KHMERSYMBOLS");
            PHONETIC_EXTENSIONS = new UnicodeBlock("PHONETIC_EXTENSIONS", "PHONETIC EXTENSIONS", "PHONETICEXTENSIONS");
            MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A", "MISCELLANEOUS MATHEMATICAL SYMBOLS-A", "MISCELLANEOUSMATHEMATICALSYMBOLS-A");
            SUPPLEMENTAL_ARROWS_A = new UnicodeBlock("SUPPLEMENTAL_ARROWS_A", "SUPPLEMENTAL ARROWS-A", "SUPPLEMENTALARROWS-A");
            SUPPLEMENTAL_ARROWS_B = new UnicodeBlock("SUPPLEMENTAL_ARROWS_B", "SUPPLEMENTAL ARROWS-B", "SUPPLEMENTALARROWS-B");
            MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B", "MISCELLANEOUS MATHEMATICAL SYMBOLS-B", "MISCELLANEOUSMATHEMATICALSYMBOLS-B");
            SUPPLEMENTAL_MATHEMATICAL_OPERATORS = new UnicodeBlock("SUPPLEMENTAL_MATHEMATICAL_OPERATORS", "SUPPLEMENTAL MATHEMATICAL OPERATORS", "SUPPLEMENTALMATHEMATICALOPERATORS");
            MISCELLANEOUS_SYMBOLS_AND_ARROWS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_ARROWS", "MISCELLANEOUS SYMBOLS AND ARROWS", "MISCELLANEOUSSYMBOLSANDARROWS");
            KATAKANA_PHONETIC_EXTENSIONS = new UnicodeBlock("KATAKANA_PHONETIC_EXTENSIONS", "KATAKANA PHONETIC EXTENSIONS", "KATAKANAPHONETICEXTENSIONS");
            YIJING_HEXAGRAM_SYMBOLS = new UnicodeBlock("YIJING_HEXAGRAM_SYMBOLS", "YIJING HEXAGRAM SYMBOLS", "YIJINGHEXAGRAMSYMBOLS");
            VARIATION_SELECTORS = new UnicodeBlock("VARIATION_SELECTORS", "VARIATION SELECTORS", "VARIATIONSELECTORS");
            LINEAR_B_SYLLABARY = new UnicodeBlock("LINEAR_B_SYLLABARY", "LINEAR B SYLLABARY", "LINEARBSYLLABARY");
            LINEAR_B_IDEOGRAMS = new UnicodeBlock("LINEAR_B_IDEOGRAMS", "LINEAR B IDEOGRAMS", "LINEARBIDEOGRAMS");
            AEGEAN_NUMBERS = new UnicodeBlock("AEGEAN_NUMBERS", "AEGEAN NUMBERS", "AEGEANNUMBERS");
            OLD_ITALIC = new UnicodeBlock("OLD_ITALIC", "OLD ITALIC", "OLDITALIC");
            GOTHIC = new UnicodeBlock("GOTHIC");
            UGARITIC = new UnicodeBlock("UGARITIC");
            DESERET = new UnicodeBlock("DESERET");
            SHAVIAN = new UnicodeBlock("SHAVIAN");
            OSMANYA = new UnicodeBlock("OSMANYA");
            CYPRIOT_SYLLABARY = new UnicodeBlock("CYPRIOT_SYLLABARY", "CYPRIOT SYLLABARY", "CYPRIOTSYLLABARY");
            BYZANTINE_MUSICAL_SYMBOLS = new UnicodeBlock("BYZANTINE_MUSICAL_SYMBOLS", "BYZANTINE MUSICAL SYMBOLS", "BYZANTINEMUSICALSYMBOLS");
            MUSICAL_SYMBOLS = new UnicodeBlock("MUSICAL_SYMBOLS", "MUSICAL SYMBOLS", "MUSICALSYMBOLS");
            TAI_XUAN_JING_SYMBOLS = new UnicodeBlock("TAI_XUAN_JING_SYMBOLS", "TAI XUAN JING SYMBOLS", "TAIXUANJINGSYMBOLS");
            MATHEMATICAL_ALPHANUMERIC_SYMBOLS = new UnicodeBlock("MATHEMATICAL_ALPHANUMERIC_SYMBOLS", "MATHEMATICAL ALPHANUMERIC SYMBOLS", "MATHEMATICALALPHANUMERICSYMBOLS");
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B", "CJK UNIFIED IDEOGRAPHS EXTENSION B", "CJKUNIFIEDIDEOGRAPHSEXTENSIONB");
            CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT", "CJK COMPATIBILITY IDEOGRAPHS SUPPLEMENT", "CJKCOMPATIBILITYIDEOGRAPHSSUPPLEMENT");
            TAGS = new UnicodeBlock("TAGS");
            VARIATION_SELECTORS_SUPPLEMENT = new UnicodeBlock("VARIATION_SELECTORS_SUPPLEMENT", "VARIATION SELECTORS SUPPLEMENT", "VARIATIONSELECTORSSUPPLEMENT");
            SUPPLEMENTARY_PRIVATE_USE_AREA_A = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_A", "SUPPLEMENTARY PRIVATE USE AREA-A", "SUPPLEMENTARYPRIVATEUSEAREA-A");
            SUPPLEMENTARY_PRIVATE_USE_AREA_B = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_B", "SUPPLEMENTARY PRIVATE USE AREA-B", "SUPPLEMENTARYPRIVATEUSEAREA-B");
            HIGH_SURROGATES = new UnicodeBlock("HIGH_SURROGATES", "HIGH SURROGATES", "HIGHSURROGATES");
            HIGH_PRIVATE_USE_SURROGATES = new UnicodeBlock("HIGH_PRIVATE_USE_SURROGATES", "HIGH PRIVATE USE SURROGATES", "HIGHPRIVATEUSESURROGATES");
            LOW_SURROGATES = new UnicodeBlock("LOW_SURROGATES", "LOW SURROGATES", "LOWSURROGATES");
            ARABIC_SUPPLEMENT = new UnicodeBlock("ARABIC_SUPPLEMENT", "ARABIC SUPPLEMENT", "ARABICSUPPLEMENT");
            NKO = new UnicodeBlock("NKO");
            SAMARITAN = new UnicodeBlock("SAMARITAN");
            MANDAIC = new UnicodeBlock("MANDAIC");
            ETHIOPIC_SUPPLEMENT = new UnicodeBlock("ETHIOPIC_SUPPLEMENT", "ETHIOPIC SUPPLEMENT", "ETHIOPICSUPPLEMENT");
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED", "UNIFIED CANADIAN ABORIGINAL SYLLABICS EXTENDED", "UNIFIEDCANADIANABORIGINALSYLLABICSEXTENDED");
            NEW_TAI_LUE = new UnicodeBlock("NEW_TAI_LUE", "NEW TAI LUE", "NEWTAILUE");
            BUGINESE = new UnicodeBlock("BUGINESE");
            TAI_THAM = new UnicodeBlock("TAI_THAM", "TAI THAM", "TAITHAM");
            BALINESE = new UnicodeBlock("BALINESE");
            SUNDANESE = new UnicodeBlock("SUNDANESE");
            BATAK = new UnicodeBlock("BATAK");
            LEPCHA = new UnicodeBlock("LEPCHA");
            OL_CHIKI = new UnicodeBlock("OL_CHIKI", "OL CHIKI", "OLCHIKI");
            VEDIC_EXTENSIONS = new UnicodeBlock("VEDIC_EXTENSIONS", "VEDIC EXTENSIONS", "VEDICEXTENSIONS");
            PHONETIC_EXTENSIONS_SUPPLEMENT = new UnicodeBlock("PHONETIC_EXTENSIONS_SUPPLEMENT", "PHONETIC EXTENSIONS SUPPLEMENT", "PHONETICEXTENSIONSSUPPLEMENT");
            COMBINING_DIACRITICAL_MARKS_SUPPLEMENT = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS_SUPPLEMENT", "COMBINING DIACRITICAL MARKS SUPPLEMENT", "COMBININGDIACRITICALMARKSSUPPLEMENT");
            GLAGOLITIC = new UnicodeBlock("GLAGOLITIC");
            LATIN_EXTENDED_C = new UnicodeBlock("LATIN_EXTENDED_C", "LATIN EXTENDED-C", "LATINEXTENDED-C");
            COPTIC = new UnicodeBlock("COPTIC");
            GEORGIAN_SUPPLEMENT = new UnicodeBlock("GEORGIAN_SUPPLEMENT", "GEORGIAN SUPPLEMENT", "GEORGIANSUPPLEMENT");
            TIFINAGH = new UnicodeBlock("TIFINAGH");
            ETHIOPIC_EXTENDED = new UnicodeBlock("ETHIOPIC_EXTENDED", "ETHIOPIC EXTENDED", "ETHIOPICEXTENDED");
            CYRILLIC_EXTENDED_A = new UnicodeBlock("CYRILLIC_EXTENDED_A", "CYRILLIC EXTENDED-A", "CYRILLICEXTENDED-A");
            SUPPLEMENTAL_PUNCTUATION = new UnicodeBlock("SUPPLEMENTAL_PUNCTUATION", "SUPPLEMENTAL PUNCTUATION", "SUPPLEMENTALPUNCTUATION");
            CJK_STROKES = new UnicodeBlock("CJK_STROKES", "CJK STROKES", "CJKSTROKES");
            LISU = new UnicodeBlock("LISU");
            VAI = new UnicodeBlock("VAI");
            CYRILLIC_EXTENDED_B = new UnicodeBlock("CYRILLIC_EXTENDED_B", "CYRILLIC EXTENDED-B", "CYRILLICEXTENDED-B");
            BAMUM = new UnicodeBlock("BAMUM");
            MODIFIER_TONE_LETTERS = new UnicodeBlock("MODIFIER_TONE_LETTERS", "MODIFIER TONE LETTERS", "MODIFIERTONELETTERS");
            LATIN_EXTENDED_D = new UnicodeBlock("LATIN_EXTENDED_D", "LATIN EXTENDED-D", "LATINEXTENDED-D");
            SYLOTI_NAGRI = new UnicodeBlock("SYLOTI_NAGRI", "SYLOTI NAGRI", "SYLOTINAGRI");
            COMMON_INDIC_NUMBER_FORMS = new UnicodeBlock("COMMON_INDIC_NUMBER_FORMS", "COMMON INDIC NUMBER FORMS", "COMMONINDICNUMBERFORMS");
            PHAGS_PA = new UnicodeBlock("PHAGS_PA", "PHAGS-PA");
            SAURASHTRA = new UnicodeBlock("SAURASHTRA");
            DEVANAGARI_EXTENDED = new UnicodeBlock("DEVANAGARI_EXTENDED", "DEVANAGARI EXTENDED", "DEVANAGARIEXTENDED");
            KAYAH_LI = new UnicodeBlock("KAYAH_LI", "KAYAH LI", "KAYAHLI");
            REJANG = new UnicodeBlock("REJANG");
            HANGUL_JAMO_EXTENDED_A = new UnicodeBlock("HANGUL_JAMO_EXTENDED_A", "HANGUL JAMO EXTENDED-A", "HANGULJAMOEXTENDED-A");
            JAVANESE = new UnicodeBlock("JAVANESE");
            CHAM = new UnicodeBlock("CHAM");
            MYANMAR_EXTENDED_A = new UnicodeBlock("MYANMAR_EXTENDED_A", "MYANMAR EXTENDED-A", "MYANMAREXTENDED-A");
            TAI_VIET = new UnicodeBlock("TAI_VIET", "TAI VIET", "TAIVIET");
            ETHIOPIC_EXTENDED_A = new UnicodeBlock("ETHIOPIC_EXTENDED_A", "ETHIOPIC EXTENDED-A", "ETHIOPICEXTENDED-A");
            MEETEI_MAYEK = new UnicodeBlock("MEETEI_MAYEK", "MEETEI MAYEK", "MEETEIMAYEK");
            HANGUL_JAMO_EXTENDED_B = new UnicodeBlock("HANGUL_JAMO_EXTENDED_B", "HANGUL JAMO EXTENDED-B", "HANGULJAMOEXTENDED-B");
            VERTICAL_FORMS = new UnicodeBlock("VERTICAL_FORMS", "VERTICAL FORMS", "VERTICALFORMS");
            ANCIENT_GREEK_NUMBERS = new UnicodeBlock("ANCIENT_GREEK_NUMBERS", "ANCIENT GREEK NUMBERS", "ANCIENTGREEKNUMBERS");
            ANCIENT_SYMBOLS = new UnicodeBlock("ANCIENT_SYMBOLS", "ANCIENT SYMBOLS", "ANCIENTSYMBOLS");
            PHAISTOS_DISC = new UnicodeBlock("PHAISTOS_DISC", "PHAISTOS DISC", "PHAISTOSDISC");
            LYCIAN = new UnicodeBlock("LYCIAN");
            CARIAN = new UnicodeBlock("CARIAN");
            OLD_PERSIAN = new UnicodeBlock("OLD_PERSIAN", "OLD PERSIAN", "OLDPERSIAN");
            IMPERIAL_ARAMAIC = new UnicodeBlock("IMPERIAL_ARAMAIC", "IMPERIAL ARAMAIC", "IMPERIALARAMAIC");
            PHOENICIAN = new UnicodeBlock("PHOENICIAN");
            LYDIAN = new UnicodeBlock("LYDIAN");
            KHAROSHTHI = new UnicodeBlock("KHAROSHTHI");
            OLD_SOUTH_ARABIAN = new UnicodeBlock("OLD_SOUTH_ARABIAN", "OLD SOUTH ARABIAN", "OLDSOUTHARABIAN");
            AVESTAN = new UnicodeBlock("AVESTAN");
            INSCRIPTIONAL_PARTHIAN = new UnicodeBlock("INSCRIPTIONAL_PARTHIAN", "INSCRIPTIONAL PARTHIAN", "INSCRIPTIONALPARTHIAN");
            INSCRIPTIONAL_PAHLAVI = new UnicodeBlock("INSCRIPTIONAL_PAHLAVI", "INSCRIPTIONAL PAHLAVI", "INSCRIPTIONALPAHLAVI");
            OLD_TURKIC = new UnicodeBlock("OLD_TURKIC", "OLD TURKIC", "OLDTURKIC");
            RUMI_NUMERAL_SYMBOLS = new UnicodeBlock("RUMI_NUMERAL_SYMBOLS", "RUMI NUMERAL SYMBOLS", "RUMINUMERALSYMBOLS");
            BRAHMI = new UnicodeBlock("BRAHMI");
            KAITHI = new UnicodeBlock("KAITHI");
            CUNEIFORM = new UnicodeBlock("CUNEIFORM");
            CUNEIFORM_NUMBERS_AND_PUNCTUATION = new UnicodeBlock("CUNEIFORM_NUMBERS_AND_PUNCTUATION", "CUNEIFORM NUMBERS AND PUNCTUATION", "CUNEIFORMNUMBERSANDPUNCTUATION");
            EGYPTIAN_HIEROGLYPHS = new UnicodeBlock("EGYPTIAN_HIEROGLYPHS", "EGYPTIAN HIEROGLYPHS", "EGYPTIANHIEROGLYPHS");
            BAMUM_SUPPLEMENT = new UnicodeBlock("BAMUM_SUPPLEMENT", "BAMUM SUPPLEMENT", "BAMUMSUPPLEMENT");
            KANA_SUPPLEMENT = new UnicodeBlock("KANA_SUPPLEMENT", "KANA SUPPLEMENT", "KANASUPPLEMENT");
            ANCIENT_GREEK_MUSICAL_NOTATION = new UnicodeBlock("ANCIENT_GREEK_MUSICAL_NOTATION", "ANCIENT GREEK MUSICAL NOTATION", "ANCIENTGREEKMUSICALNOTATION");
            COUNTING_ROD_NUMERALS = new UnicodeBlock("COUNTING_ROD_NUMERALS", "COUNTING ROD NUMERALS", "COUNTINGRODNUMERALS");
            MAHJONG_TILES = new UnicodeBlock("MAHJONG_TILES", "MAHJONG TILES", "MAHJONGTILES");
            DOMINO_TILES = new UnicodeBlock("DOMINO_TILES", "DOMINO TILES", "DOMINOTILES");
            PLAYING_CARDS = new UnicodeBlock("PLAYING_CARDS", "PLAYING CARDS", "PLAYINGCARDS");
            ENCLOSED_ALPHANUMERIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_ALPHANUMERIC_SUPPLEMENT", "ENCLOSED ALPHANUMERIC SUPPLEMENT", "ENCLOSEDALPHANUMERICSUPPLEMENT");
            ENCLOSED_IDEOGRAPHIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_IDEOGRAPHIC_SUPPLEMENT", "ENCLOSED IDEOGRAPHIC SUPPLEMENT", "ENCLOSEDIDEOGRAPHICSUPPLEMENT");
            MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS", "MISCELLANEOUS SYMBOLS AND PICTOGRAPHS", "MISCELLANEOUSSYMBOLSANDPICTOGRAPHS");
            EMOTICONS = new UnicodeBlock("EMOTICONS");
            TRANSPORT_AND_MAP_SYMBOLS = new UnicodeBlock("TRANSPORT_AND_MAP_SYMBOLS", "TRANSPORT AND MAP SYMBOLS", "TRANSPORTANDMAPSYMBOLS");
            ALCHEMICAL_SYMBOLS = new UnicodeBlock("ALCHEMICAL_SYMBOLS", "ALCHEMICAL SYMBOLS", "ALCHEMICALSYMBOLS");
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C", "CJK UNIFIED IDEOGRAPHS EXTENSION C", "CJKUNIFIEDIDEOGRAPHSEXTENSIONC");
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D", "CJK UNIFIED IDEOGRAPHS EXTENSION D", "CJKUNIFIEDIDEOGRAPHSEXTENSIOND");
            ARABIC_EXTENDED_A = new UnicodeBlock("ARABIC_EXTENDED_A", "ARABIC EXTENDED-A", "ARABICEXTENDED-A");
            SUNDANESE_SUPPLEMENT = new UnicodeBlock("SUNDANESE_SUPPLEMENT", "SUNDANESE SUPPLEMENT", "SUNDANESESUPPLEMENT");
            MEETEI_MAYEK_EXTENSIONS = new UnicodeBlock("MEETEI_MAYEK_EXTENSIONS", "MEETEI MAYEK EXTENSIONS", "MEETEIMAYEKEXTENSIONS");
            MEROITIC_HIEROGLYPHS = new UnicodeBlock("MEROITIC_HIEROGLYPHS", "MEROITIC HIEROGLYPHS", "MEROITICHIEROGLYPHS");
            MEROITIC_CURSIVE = new UnicodeBlock("MEROITIC_CURSIVE", "MEROITIC CURSIVE", "MEROITICCURSIVE");
            SORA_SOMPENG = new UnicodeBlock("SORA_SOMPENG", "SORA SOMPENG", "SORASOMPENG");
            CHAKMA = new UnicodeBlock("CHAKMA");
            SHARADA = new UnicodeBlock("SHARADA");
            TAKRI = new UnicodeBlock("TAKRI");
            MIAO = new UnicodeBlock("MIAO");
            ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS = new UnicodeBlock("ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS", "ARABIC MATHEMATICAL ALPHABETIC SYMBOLS", "ARABICMATHEMATICALALPHABETICSYMBOLS");
            blockStarts = new int[]{0, 128, 256, 384, 592, 688, 768, 880, 1024, 1280, 1328, 1424, 1536, 1792, 1872, 1920, 1984, 2048, 2112, 2144, 2208, 2304, 2432, 2560, 2688, 2816, 2944, 3072, 3200, 3328, 3456, 3584, 3712, 3840, 4096, 4256, 4352, 4608, 4992, 5024, 5120, 5760, 5792, 5888, 5920, 5952, 5984, 6016, 6144, 6320, 6400, 6480, 6528, 6624, 6656, 6688, 6832, 6912, 7040, 7104, 7168, 7248, 7296, 7360, 7376, 7424, 7552, 7616, 7680, 7936, 8192, 8304, 8352, 8400, 8448, 8528, 8592, 8704, 8960, 9216, 9280, 9312, 9472, 9600, 9632, 9728, 9984, 10176, 10224, 10240, 10496, 10624, 10752, 11008, 11264, 11360, 11392, 11520, 11568, 11648, 11744, 11776, 11904, 12032, 12256, 12272, 12288, 12352, 12448, 12544, 12592, 12688, 12704, 12736, 12784, 12800, 13056, 13312, 19904, 19968, 40960, 42128, 42192, 42240, 42560, 42656, 42752, 42784, 43008, 43056, 43072, 43136, 43232, 43264, 43312, 43360, 43392, 43488, 43520, 43616, 43648, 43744, 43776, 43824, 43968, 44032, 55216, 55296, 56192, 56320, 57344, 63744, 64256, 64336, 65024, 65040, 65056, 65072, 65104, 65136, 65280, 65520, 65536, 65664, 65792, 65856, 65936, 66000, 66048, 66176, 66208, 66272, 66304, 66352, 66384, 66432, 66464, 66528, 66560, 66640, 66688, 66736, 67584, 67648, 67680, 67840, 67872, 67904, 67968, 68000, 68096, 68192, 68224, 68352, 68416, 68448, 68480, 68608, 68688, 69216, 69248, 69632, 69760, 69840, 69888, 69968, 70016, 70112, 71296, 71376, 73728, 74752, 74880, 77824, 78896, 92160, 92736, 93952, 94112, 110592, 110848, 118784, 119040, 119296, 119376, 119552, 119648, 119680, 119808, 120832, 126464, 126720, 126976, 127024, 127136, 127232, 127488, 127744, 128512, 128592, 128640, 128768, 128896, 131072, 173792, 173824, 177984, 178208, 194560, 195104, 917504, 917632, 917760, 918000, 983040, 1048576};
            blocks = new UnicodeBlock[]{BASIC_LATIN, LATIN_1_SUPPLEMENT, LATIN_EXTENDED_A, LATIN_EXTENDED_B, IPA_EXTENSIONS, SPACING_MODIFIER_LETTERS, COMBINING_DIACRITICAL_MARKS, GREEK, CYRILLIC, CYRILLIC_SUPPLEMENTARY, ARMENIAN, HEBREW, ARABIC, SYRIAC, ARABIC_SUPPLEMENT, THAANA, NKO, SAMARITAN, MANDAIC, null, ARABIC_EXTENDED_A, DEVANAGARI, BENGALI, GURMUKHI, GUJARATI, ORIYA, TAMIL, TELUGU, KANNADA, MALAYALAM, SINHALA, THAI, LAO, TIBETAN, MYANMAR, GEORGIAN, HANGUL_JAMO, ETHIOPIC, ETHIOPIC_SUPPLEMENT, CHEROKEE, UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS, OGHAM, RUNIC, TAGALOG, HANUNOO, BUHID, TAGBANWA, KHMER, MONGOLIAN, UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED, LIMBU, TAI_LE, NEW_TAI_LUE, KHMER_SYMBOLS, BUGINESE, TAI_THAM, null, BALINESE, SUNDANESE, BATAK, LEPCHA, OL_CHIKI, null, SUNDANESE_SUPPLEMENT, VEDIC_EXTENSIONS, PHONETIC_EXTENSIONS, PHONETIC_EXTENSIONS_SUPPLEMENT, COMBINING_DIACRITICAL_MARKS_SUPPLEMENT, LATIN_EXTENDED_ADDITIONAL, GREEK_EXTENDED, GENERAL_PUNCTUATION, SUPERSCRIPTS_AND_SUBSCRIPTS, CURRENCY_SYMBOLS, COMBINING_MARKS_FOR_SYMBOLS, LETTERLIKE_SYMBOLS, NUMBER_FORMS, ARROWS, MATHEMATICAL_OPERATORS, MISCELLANEOUS_TECHNICAL, CONTROL_PICTURES, OPTICAL_CHARACTER_RECOGNITION, ENCLOSED_ALPHANUMERICS, BOX_DRAWING, BLOCK_ELEMENTS, GEOMETRIC_SHAPES, MISCELLANEOUS_SYMBOLS, DINGBATS, MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A, SUPPLEMENTAL_ARROWS_A, BRAILLE_PATTERNS, SUPPLEMENTAL_ARROWS_B, MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B, SUPPLEMENTAL_MATHEMATICAL_OPERATORS, MISCELLANEOUS_SYMBOLS_AND_ARROWS, GLAGOLITIC, LATIN_EXTENDED_C, COPTIC, GEORGIAN_SUPPLEMENT, TIFINAGH, ETHIOPIC_EXTENDED, CYRILLIC_EXTENDED_A, SUPPLEMENTAL_PUNCTUATION, CJK_RADICALS_SUPPLEMENT, KANGXI_RADICALS, null, IDEOGRAPHIC_DESCRIPTION_CHARACTERS, CJK_SYMBOLS_AND_PUNCTUATION, HIRAGANA, KATAKANA, BOPOMOFO, HANGUL_COMPATIBILITY_JAMO, KANBUN, BOPOMOFO_EXTENDED, CJK_STROKES, KATAKANA_PHONETIC_EXTENSIONS, ENCLOSED_CJK_LETTERS_AND_MONTHS, CJK_COMPATIBILITY, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A, YIJING_HEXAGRAM_SYMBOLS, CJK_UNIFIED_IDEOGRAPHS, YI_SYLLABLES, YI_RADICALS, LISU, VAI, CYRILLIC_EXTENDED_B, BAMUM, MODIFIER_TONE_LETTERS, LATIN_EXTENDED_D, SYLOTI_NAGRI, COMMON_INDIC_NUMBER_FORMS, PHAGS_PA, SAURASHTRA, DEVANAGARI_EXTENDED, KAYAH_LI, REJANG, HANGUL_JAMO_EXTENDED_A, JAVANESE, null, CHAM, MYANMAR_EXTENDED_A, TAI_VIET, MEETEI_MAYEK_EXTENSIONS, ETHIOPIC_EXTENDED_A, null, MEETEI_MAYEK, HANGUL_SYLLABLES, HANGUL_JAMO_EXTENDED_B, HIGH_SURROGATES, HIGH_PRIVATE_USE_SURROGATES, LOW_SURROGATES, PRIVATE_USE_AREA, CJK_COMPATIBILITY_IDEOGRAPHS, ALPHABETIC_PRESENTATION_FORMS, ARABIC_PRESENTATION_FORMS_A, VARIATION_SELECTORS, VERTICAL_FORMS, COMBINING_HALF_MARKS, CJK_COMPATIBILITY_FORMS, SMALL_FORM_VARIANTS, ARABIC_PRESENTATION_FORMS_B, HALFWIDTH_AND_FULLWIDTH_FORMS, SPECIALS, LINEAR_B_SYLLABARY, LINEAR_B_IDEOGRAMS, AEGEAN_NUMBERS, ANCIENT_GREEK_NUMBERS, ANCIENT_SYMBOLS, PHAISTOS_DISC, null, LYCIAN, CARIAN, null, OLD_ITALIC, GOTHIC, null, UGARITIC, OLD_PERSIAN, null, DESERET, SHAVIAN, OSMANYA, null, CYPRIOT_SYLLABARY, IMPERIAL_ARAMAIC, null, PHOENICIAN, LYDIAN, null, MEROITIC_HIEROGLYPHS, MEROITIC_CURSIVE, KHAROSHTHI, OLD_SOUTH_ARABIAN, null, AVESTAN, INSCRIPTIONAL_PARTHIAN, INSCRIPTIONAL_PAHLAVI, null, OLD_TURKIC, null, RUMI_NUMERAL_SYMBOLS, null, BRAHMI, KAITHI, SORA_SOMPENG, CHAKMA, null, SHARADA, null, TAKRI, null, CUNEIFORM, CUNEIFORM_NUMBERS_AND_PUNCTUATION, null, EGYPTIAN_HIEROGLYPHS, null, BAMUM_SUPPLEMENT, null, MIAO, null, KANA_SUPPLEMENT, null, BYZANTINE_MUSICAL_SYMBOLS, MUSICAL_SYMBOLS, ANCIENT_GREEK_MUSICAL_NOTATION, null, TAI_XUAN_JING_SYMBOLS, COUNTING_ROD_NUMERALS, null, MATHEMATICAL_ALPHANUMERIC_SYMBOLS, null, ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS, null, MAHJONG_TILES, DOMINO_TILES, PLAYING_CARDS, ENCLOSED_ALPHANUMERIC_SUPPLEMENT, ENCLOSED_IDEOGRAPHIC_SUPPLEMENT, MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS, EMOTICONS, null, TRANSPORT_AND_MAP_SYMBOLS, ALCHEMICAL_SYMBOLS, null, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B, null, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D, null, CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT, null, TAGS, null, VARIATION_SELECTORS_SUPPLEMENT, null, SUPPLEMENTARY_PRIVATE_USE_AREA_A, SUPPLEMENTARY_PRIVATE_USE_AREA_B};
        }

        private UnicodeBlock(String string) {
            super(string);
            map.put(string, this);
        }

        private UnicodeBlock(String string, String string2) {
            this(string);
            map.put(string2, this);
        }

        private UnicodeBlock(String string, boolean bl) {
            super(string);
            if (bl) {
                map.put(string, this);
            }
        }

        private UnicodeBlock(String string2, String ... arrstring) {
            this(string2);
            for (String string2 : arrstring) {
                map.put(string2, this);
            }
        }

        public static final UnicodeBlock forName(String object) {
            if ((object = map.get(((String)object).toUpperCase(Locale.US))) != null) {
                return object;
            }
            throw new IllegalArgumentException();
        }

        public static UnicodeBlock of(char c) {
            return UnicodeBlock.of((int)c);
        }

        public static UnicodeBlock of(int n) {
            if (Character.isValidCodePoint(n)) {
                int n2 = 0;
                int n3 = blockStarts.length;
                int n4 = n3 / 2;
                while (n3 - n2 > 1) {
                    if (n >= blockStarts[n4]) {
                        n2 = n4;
                    } else {
                        n3 = n4;
                    }
                    n4 = (n3 + n2) / 2;
                }
                return blocks[n4];
            }
            throw new IllegalArgumentException();
        }
    }

    public static final class UnicodeScript
    extends Enum<UnicodeScript> {
        private static final /* synthetic */ UnicodeScript[] $VALUES;
        public static final /* enum */ UnicodeScript ARABIC;
        public static final /* enum */ UnicodeScript ARMENIAN;
        public static final /* enum */ UnicodeScript AVESTAN;
        public static final /* enum */ UnicodeScript BALINESE;
        public static final /* enum */ UnicodeScript BAMUM;
        public static final /* enum */ UnicodeScript BATAK;
        public static final /* enum */ UnicodeScript BENGALI;
        public static final /* enum */ UnicodeScript BOPOMOFO;
        public static final /* enum */ UnicodeScript BRAHMI;
        public static final /* enum */ UnicodeScript BRAILLE;
        public static final /* enum */ UnicodeScript BUGINESE;
        public static final /* enum */ UnicodeScript BUHID;
        public static final /* enum */ UnicodeScript CANADIAN_ABORIGINAL;
        public static final /* enum */ UnicodeScript CARIAN;
        public static final /* enum */ UnicodeScript CHAKMA;
        public static final /* enum */ UnicodeScript CHAM;
        public static final /* enum */ UnicodeScript CHEROKEE;
        public static final /* enum */ UnicodeScript COMMON;
        public static final /* enum */ UnicodeScript COPTIC;
        public static final /* enum */ UnicodeScript CUNEIFORM;
        public static final /* enum */ UnicodeScript CYPRIOT;
        public static final /* enum */ UnicodeScript CYRILLIC;
        public static final /* enum */ UnicodeScript DESERET;
        public static final /* enum */ UnicodeScript DEVANAGARI;
        public static final /* enum */ UnicodeScript EGYPTIAN_HIEROGLYPHS;
        public static final /* enum */ UnicodeScript ETHIOPIC;
        public static final /* enum */ UnicodeScript GEORGIAN;
        public static final /* enum */ UnicodeScript GLAGOLITIC;
        public static final /* enum */ UnicodeScript GOTHIC;
        public static final /* enum */ UnicodeScript GREEK;
        public static final /* enum */ UnicodeScript GUJARATI;
        public static final /* enum */ UnicodeScript GURMUKHI;
        public static final /* enum */ UnicodeScript HAN;
        public static final /* enum */ UnicodeScript HANGUL;
        public static final /* enum */ UnicodeScript HANUNOO;
        public static final /* enum */ UnicodeScript HEBREW;
        public static final /* enum */ UnicodeScript HIRAGANA;
        public static final /* enum */ UnicodeScript IMPERIAL_ARAMAIC;
        public static final /* enum */ UnicodeScript INHERITED;
        public static final /* enum */ UnicodeScript INSCRIPTIONAL_PAHLAVI;
        public static final /* enum */ UnicodeScript INSCRIPTIONAL_PARTHIAN;
        public static final /* enum */ UnicodeScript JAVANESE;
        public static final /* enum */ UnicodeScript KAITHI;
        public static final /* enum */ UnicodeScript KANNADA;
        public static final /* enum */ UnicodeScript KATAKANA;
        public static final /* enum */ UnicodeScript KAYAH_LI;
        public static final /* enum */ UnicodeScript KHAROSHTHI;
        public static final /* enum */ UnicodeScript KHMER;
        public static final /* enum */ UnicodeScript LAO;
        public static final /* enum */ UnicodeScript LATIN;
        public static final /* enum */ UnicodeScript LEPCHA;
        public static final /* enum */ UnicodeScript LIMBU;
        public static final /* enum */ UnicodeScript LINEAR_B;
        public static final /* enum */ UnicodeScript LISU;
        public static final /* enum */ UnicodeScript LYCIAN;
        public static final /* enum */ UnicodeScript LYDIAN;
        public static final /* enum */ UnicodeScript MALAYALAM;
        public static final /* enum */ UnicodeScript MANDAIC;
        public static final /* enum */ UnicodeScript MEETEI_MAYEK;
        public static final /* enum */ UnicodeScript MEROITIC_CURSIVE;
        public static final /* enum */ UnicodeScript MEROITIC_HIEROGLYPHS;
        public static final /* enum */ UnicodeScript MIAO;
        public static final /* enum */ UnicodeScript MONGOLIAN;
        public static final /* enum */ UnicodeScript MYANMAR;
        public static final /* enum */ UnicodeScript NEW_TAI_LUE;
        public static final /* enum */ UnicodeScript NKO;
        public static final /* enum */ UnicodeScript OGHAM;
        public static final /* enum */ UnicodeScript OLD_ITALIC;
        public static final /* enum */ UnicodeScript OLD_PERSIAN;
        public static final /* enum */ UnicodeScript OLD_SOUTH_ARABIAN;
        public static final /* enum */ UnicodeScript OLD_TURKIC;
        public static final /* enum */ UnicodeScript OL_CHIKI;
        public static final /* enum */ UnicodeScript ORIYA;
        public static final /* enum */ UnicodeScript OSMANYA;
        public static final /* enum */ UnicodeScript PHAGS_PA;
        public static final /* enum */ UnicodeScript PHOENICIAN;
        public static final /* enum */ UnicodeScript REJANG;
        public static final /* enum */ UnicodeScript RUNIC;
        public static final /* enum */ UnicodeScript SAMARITAN;
        public static final /* enum */ UnicodeScript SAURASHTRA;
        public static final /* enum */ UnicodeScript SHARADA;
        public static final /* enum */ UnicodeScript SHAVIAN;
        public static final /* enum */ UnicodeScript SINHALA;
        public static final /* enum */ UnicodeScript SORA_SOMPENG;
        public static final /* enum */ UnicodeScript SUNDANESE;
        public static final /* enum */ UnicodeScript SYLOTI_NAGRI;
        public static final /* enum */ UnicodeScript SYRIAC;
        public static final /* enum */ UnicodeScript TAGALOG;
        public static final /* enum */ UnicodeScript TAGBANWA;
        public static final /* enum */ UnicodeScript TAI_LE;
        public static final /* enum */ UnicodeScript TAI_THAM;
        public static final /* enum */ UnicodeScript TAI_VIET;
        public static final /* enum */ UnicodeScript TAKRI;
        public static final /* enum */ UnicodeScript TAMIL;
        public static final /* enum */ UnicodeScript TELUGU;
        public static final /* enum */ UnicodeScript THAANA;
        public static final /* enum */ UnicodeScript THAI;
        public static final /* enum */ UnicodeScript TIBETAN;
        public static final /* enum */ UnicodeScript TIFINAGH;
        public static final /* enum */ UnicodeScript UGARITIC;
        public static final /* enum */ UnicodeScript UNKNOWN;
        public static final /* enum */ UnicodeScript VAI;
        public static final /* enum */ UnicodeScript YI;
        private static HashMap<String, UnicodeScript> aliases;
        private static final int[] scriptStarts;
        private static final UnicodeScript[] scripts;

        static {
            COMMON = new UnicodeScript();
            LATIN = new UnicodeScript();
            GREEK = new UnicodeScript();
            CYRILLIC = new UnicodeScript();
            ARMENIAN = new UnicodeScript();
            HEBREW = new UnicodeScript();
            ARABIC = new UnicodeScript();
            SYRIAC = new UnicodeScript();
            THAANA = new UnicodeScript();
            DEVANAGARI = new UnicodeScript();
            BENGALI = new UnicodeScript();
            GURMUKHI = new UnicodeScript();
            GUJARATI = new UnicodeScript();
            ORIYA = new UnicodeScript();
            TAMIL = new UnicodeScript();
            TELUGU = new UnicodeScript();
            KANNADA = new UnicodeScript();
            MALAYALAM = new UnicodeScript();
            SINHALA = new UnicodeScript();
            THAI = new UnicodeScript();
            LAO = new UnicodeScript();
            TIBETAN = new UnicodeScript();
            MYANMAR = new UnicodeScript();
            GEORGIAN = new UnicodeScript();
            HANGUL = new UnicodeScript();
            ETHIOPIC = new UnicodeScript();
            CHEROKEE = new UnicodeScript();
            CANADIAN_ABORIGINAL = new UnicodeScript();
            OGHAM = new UnicodeScript();
            RUNIC = new UnicodeScript();
            KHMER = new UnicodeScript();
            MONGOLIAN = new UnicodeScript();
            HIRAGANA = new UnicodeScript();
            KATAKANA = new UnicodeScript();
            BOPOMOFO = new UnicodeScript();
            HAN = new UnicodeScript();
            YI = new UnicodeScript();
            OLD_ITALIC = new UnicodeScript();
            GOTHIC = new UnicodeScript();
            DESERET = new UnicodeScript();
            INHERITED = new UnicodeScript();
            TAGALOG = new UnicodeScript();
            HANUNOO = new UnicodeScript();
            BUHID = new UnicodeScript();
            TAGBANWA = new UnicodeScript();
            LIMBU = new UnicodeScript();
            TAI_LE = new UnicodeScript();
            LINEAR_B = new UnicodeScript();
            UGARITIC = new UnicodeScript();
            SHAVIAN = new UnicodeScript();
            OSMANYA = new UnicodeScript();
            CYPRIOT = new UnicodeScript();
            BRAILLE = new UnicodeScript();
            BUGINESE = new UnicodeScript();
            COPTIC = new UnicodeScript();
            NEW_TAI_LUE = new UnicodeScript();
            GLAGOLITIC = new UnicodeScript();
            TIFINAGH = new UnicodeScript();
            SYLOTI_NAGRI = new UnicodeScript();
            OLD_PERSIAN = new UnicodeScript();
            KHAROSHTHI = new UnicodeScript();
            BALINESE = new UnicodeScript();
            CUNEIFORM = new UnicodeScript();
            PHOENICIAN = new UnicodeScript();
            PHAGS_PA = new UnicodeScript();
            NKO = new UnicodeScript();
            SUNDANESE = new UnicodeScript();
            BATAK = new UnicodeScript();
            LEPCHA = new UnicodeScript();
            OL_CHIKI = new UnicodeScript();
            VAI = new UnicodeScript();
            SAURASHTRA = new UnicodeScript();
            KAYAH_LI = new UnicodeScript();
            REJANG = new UnicodeScript();
            LYCIAN = new UnicodeScript();
            CARIAN = new UnicodeScript();
            LYDIAN = new UnicodeScript();
            CHAM = new UnicodeScript();
            TAI_THAM = new UnicodeScript();
            TAI_VIET = new UnicodeScript();
            AVESTAN = new UnicodeScript();
            EGYPTIAN_HIEROGLYPHS = new UnicodeScript();
            SAMARITAN = new UnicodeScript();
            MANDAIC = new UnicodeScript();
            LISU = new UnicodeScript();
            BAMUM = new UnicodeScript();
            JAVANESE = new UnicodeScript();
            MEETEI_MAYEK = new UnicodeScript();
            IMPERIAL_ARAMAIC = new UnicodeScript();
            OLD_SOUTH_ARABIAN = new UnicodeScript();
            INSCRIPTIONAL_PARTHIAN = new UnicodeScript();
            INSCRIPTIONAL_PAHLAVI = new UnicodeScript();
            OLD_TURKIC = new UnicodeScript();
            BRAHMI = new UnicodeScript();
            KAITHI = new UnicodeScript();
            MEROITIC_HIEROGLYPHS = new UnicodeScript();
            MEROITIC_CURSIVE = new UnicodeScript();
            SORA_SOMPENG = new UnicodeScript();
            CHAKMA = new UnicodeScript();
            SHARADA = new UnicodeScript();
            TAKRI = new UnicodeScript();
            MIAO = new UnicodeScript();
            UNKNOWN = new UnicodeScript();
            UnicodeScript unicodeScript = COMMON;
            UnicodeScript unicodeScript2 = LATIN;
            UnicodeScript unicodeScript3 = GREEK;
            UnicodeScript unicodeScript4 = CYRILLIC;
            UnicodeScript unicodeScript5 = ARMENIAN;
            UnicodeScript unicodeScript6 = HEBREW;
            UnicodeScript unicodeScript7 = ARABIC;
            UnicodeScript unicodeScript8 = SYRIAC;
            UnicodeScript unicodeScript9 = THAANA;
            UnicodeScript unicodeScript10 = DEVANAGARI;
            UnicodeScript unicodeScript11 = BENGALI;
            UnicodeScript unicodeScript12 = GURMUKHI;
            UnicodeScript unicodeScript13 = GUJARATI;
            UnicodeScript unicodeScript14 = ORIYA;
            UnicodeScript unicodeScript15 = TAMIL;
            UnicodeScript unicodeScript16 = TELUGU;
            UnicodeScript unicodeScript17 = KANNADA;
            UnicodeScript unicodeScript18 = MALAYALAM;
            UnicodeScript unicodeScript19 = SINHALA;
            UnicodeScript unicodeScript20 = THAI;
            UnicodeScript unicodeScript21 = LAO;
            UnicodeScript unicodeScript22 = TIBETAN;
            UnicodeScript unicodeScript23 = MYANMAR;
            UnicodeScript unicodeScript24 = GEORGIAN;
            UnicodeScript unicodeScript25 = HANGUL;
            UnicodeScript unicodeScript26 = ETHIOPIC;
            UnicodeScript unicodeScript27 = CHEROKEE;
            UnicodeScript unicodeScript28 = CANADIAN_ABORIGINAL;
            UnicodeScript unicodeScript29 = OGHAM;
            UnicodeScript unicodeScript30 = RUNIC;
            UnicodeScript unicodeScript31 = KHMER;
            UnicodeScript unicodeScript32 = MONGOLIAN;
            UnicodeScript unicodeScript33 = HIRAGANA;
            UnicodeScript unicodeScript34 = KATAKANA;
            UnicodeScript unicodeScript35 = BOPOMOFO;
            UnicodeScript unicodeScript36 = HAN;
            UnicodeScript unicodeScript37 = YI;
            UnicodeScript unicodeScript38 = OLD_ITALIC;
            UnicodeScript unicodeScript39 = GOTHIC;
            UnicodeScript unicodeScript40 = DESERET;
            UnicodeScript unicodeScript41 = INHERITED;
            UnicodeScript unicodeScript42 = TAGALOG;
            UnicodeScript unicodeScript43 = HANUNOO;
            UnicodeScript unicodeScript44 = BUHID;
            UnicodeScript unicodeScript45 = TAGBANWA;
            UnicodeScript unicodeScript46 = LIMBU;
            UnicodeScript unicodeScript47 = TAI_LE;
            UnicodeScript unicodeScript48 = LINEAR_B;
            UnicodeScript unicodeScript49 = UGARITIC;
            UnicodeScript unicodeScript50 = SHAVIAN;
            UnicodeScript unicodeScript51 = OSMANYA;
            UnicodeScript unicodeScript52 = CYPRIOT;
            UnicodeScript unicodeScript53 = BRAILLE;
            UnicodeScript unicodeScript54 = BUGINESE;
            UnicodeScript unicodeScript55 = COPTIC;
            UnicodeScript unicodeScript56 = NEW_TAI_LUE;
            UnicodeScript unicodeScript57 = GLAGOLITIC;
            UnicodeScript unicodeScript58 = TIFINAGH;
            UnicodeScript unicodeScript59 = SYLOTI_NAGRI;
            UnicodeScript unicodeScript60 = OLD_PERSIAN;
            UnicodeScript unicodeScript61 = KHAROSHTHI;
            UnicodeScript unicodeScript62 = BALINESE;
            UnicodeScript unicodeScript63 = CUNEIFORM;
            UnicodeScript unicodeScript64 = PHOENICIAN;
            UnicodeScript unicodeScript65 = PHAGS_PA;
            UnicodeScript unicodeScript66 = NKO;
            UnicodeScript unicodeScript67 = SUNDANESE;
            UnicodeScript unicodeScript68 = BATAK;
            UnicodeScript unicodeScript69 = LEPCHA;
            UnicodeScript unicodeScript70 = OL_CHIKI;
            UnicodeScript unicodeScript71 = VAI;
            UnicodeScript unicodeScript72 = SAURASHTRA;
            UnicodeScript unicodeScript73 = KAYAH_LI;
            UnicodeScript unicodeScript74 = REJANG;
            UnicodeScript unicodeScript75 = LYCIAN;
            UnicodeScript unicodeScript76 = CARIAN;
            UnicodeScript unicodeScript77 = LYDIAN;
            UnicodeScript unicodeScript78 = CHAM;
            UnicodeScript unicodeScript79 = TAI_THAM;
            UnicodeScript unicodeScript80 = TAI_VIET;
            UnicodeScript unicodeScript81 = AVESTAN;
            UnicodeScript unicodeScript82 = EGYPTIAN_HIEROGLYPHS;
            UnicodeScript unicodeScript83 = SAMARITAN;
            UnicodeScript unicodeScript84 = MANDAIC;
            UnicodeScript unicodeScript85 = LISU;
            UnicodeScript unicodeScript86 = BAMUM;
            UnicodeScript unicodeScript87 = JAVANESE;
            UnicodeScript unicodeScript88 = MEETEI_MAYEK;
            UnicodeScript unicodeScript89 = IMPERIAL_ARAMAIC;
            UnicodeScript unicodeScript90 = OLD_SOUTH_ARABIAN;
            UnicodeScript unicodeScript91 = INSCRIPTIONAL_PARTHIAN;
            UnicodeScript unicodeScript92 = INSCRIPTIONAL_PAHLAVI;
            UnicodeScript unicodeScript93 = OLD_TURKIC;
            UnicodeScript unicodeScript94 = BRAHMI;
            UnicodeScript unicodeScript95 = KAITHI;
            UnicodeScript unicodeScript96 = MEROITIC_HIEROGLYPHS;
            UnicodeScript unicodeScript97 = MEROITIC_CURSIVE;
            UnicodeScript unicodeScript98 = SORA_SOMPENG;
            UnicodeScript unicodeScript99 = CHAKMA;
            UnicodeScript unicodeScript100 = SHARADA;
            UnicodeScript unicodeScript101 = TAKRI;
            UnicodeScript unicodeScript102 = MIAO;
            UnicodeScript unicodeScript103 = UNKNOWN;
            $VALUES = new UnicodeScript[]{unicodeScript, unicodeScript2, unicodeScript3, unicodeScript4, unicodeScript5, unicodeScript6, unicodeScript7, unicodeScript8, unicodeScript9, unicodeScript10, unicodeScript11, unicodeScript12, unicodeScript13, unicodeScript14, unicodeScript15, unicodeScript16, unicodeScript17, unicodeScript18, unicodeScript19, unicodeScript20, unicodeScript21, unicodeScript22, unicodeScript23, unicodeScript24, unicodeScript25, unicodeScript26, unicodeScript27, unicodeScript28, unicodeScript29, unicodeScript30, unicodeScript31, unicodeScript32, unicodeScript33, unicodeScript34, unicodeScript35, unicodeScript36, unicodeScript37, unicodeScript38, unicodeScript39, unicodeScript40, unicodeScript41, unicodeScript42, unicodeScript43, unicodeScript44, unicodeScript45, unicodeScript46, unicodeScript47, unicodeScript48, unicodeScript49, unicodeScript50, unicodeScript51, unicodeScript52, unicodeScript53, unicodeScript54, unicodeScript55, unicodeScript56, unicodeScript57, unicodeScript58, unicodeScript59, unicodeScript60, unicodeScript61, unicodeScript62, unicodeScript63, unicodeScript64, unicodeScript65, unicodeScript66, unicodeScript67, unicodeScript68, unicodeScript69, unicodeScript70, unicodeScript71, unicodeScript72, unicodeScript73, unicodeScript74, unicodeScript75, unicodeScript76, unicodeScript77, unicodeScript78, unicodeScript79, unicodeScript80, unicodeScript81, unicodeScript82, unicodeScript83, unicodeScript84, unicodeScript85, unicodeScript86, unicodeScript87, unicodeScript88, unicodeScript89, unicodeScript90, unicodeScript91, unicodeScript92, unicodeScript93, unicodeScript94, unicodeScript95, unicodeScript96, unicodeScript97, unicodeScript98, unicodeScript99, unicodeScript100, unicodeScript101, unicodeScript102, unicodeScript103};
            scriptStarts = new int[]{0, 65, 91, 97, 123, 170, 171, 186, 187, 192, 215, 216, 247, 248, 697, 736, 741, 746, 748, 768, 880, 884, 885, 894, 900, 901, 902, 903, 904, 994, 1008, 1024, 1157, 1159, 1329, 1417, 1418, 1425, 1536, 1548, 1549, 1563, 1566, 1567, 1568, 1600, 1601, 1611, 1622, 1632, 1642, 1648, 1649, 1757, 1758, 1792, 1872, 1920, 1984, 2048, 2112, 2208, 2304, 2385, 2387, 2404, 2406, 2433, 2561, 2689, 2817, 2946, 3073, 3202, 3330, 3458, 3585, 3647, 3648, 3713, 3840, 4053, 4057, 4096, 4256, 4347, 4348, 4352, 4608, 5024, 5120, 5760, 5792, 5867, 5870, 5888, 5920, 5941, 5952, 5984, 6016, 6144, 6146, 6148, 6149, 6150, 6320, 6400, 6480, 6528, 6624, 6656, 6688, 6912, 7040, 7104, 7168, 7248, 7360, 7376, 7379, 7380, 7393, 7394, 7401, 7405, 7406, 7412, 7413, 7424, 7462, 7467, 7468, 7517, 7522, 7526, 7531, 7544, 7545, 7615, 7616, 7680, 7936, 8192, 8204, 8206, 8305, 8308, 8319, 8320, 8336, 8352, 8400, 8448, 8486, 8487, 8490, 8492, 8498, 8499, 8526, 8527, 8544, 8585, 10240, 10496, 11264, 11360, 11392, 11520, 11568, 11648, 11744, 11776, 11904, 12272, 12293, 12294, 12295, 12296, 12321, 12330, 12334, 12336, 12344, 12348, 12353, 12441, 12443, 12445, 12448, 12449, 12539, 12541, 12549, 12593, 12688, 12704, 12736, 12784, 12800, 12832, 12896, 12927, 13008, 13144, 13312, 19904, 19968, 40960, 42192, 42240, 42560, 42656, 42752, 42786, 42888, 42891, 43008, 43056, 43072, 43136, 43232, 43264, 43312, 43360, 43392, 43520, 43616, 43648, 43744, 43777, 43968, 44032, 55292, 63744, 64256, 64275, 64285, 64336, 64830, 64848, 65021, 65024, 65040, 65056, 65072, 65136, 65279, 65313, 65339, 65345, 65371, 65382, 65392, 65393, 65438, 65440, 65504, 65536, 65792, 65856, 65936, 66045, 66176, 66208, 66304, 66352, 66432, 66464, 66560, 66640, 66688, 67584, 67648, 67840, 67872, 67968, 68000, 68096, 68192, 68352, 68416, 68448, 68608, 69216, 69632, 69760, 69840, 69888, 70016, 71296, 73728, 77824, 92160, 93952, 110592, 110593, 118784, 119143, 119146, 119163, 119171, 119173, 119180, 119210, 119214, 119296, 119552, 126464, 126976, 127488, 127489, 131072, 917505, 917760, 918000};
            scripts = new UnicodeScript[]{unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript35, unicodeScript, unicodeScript41, unicodeScript3, unicodeScript, unicodeScript3, unicodeScript, unicodeScript3, unicodeScript, unicodeScript3, unicodeScript, unicodeScript3, unicodeScript55, unicodeScript3, unicodeScript4, unicodeScript41, unicodeScript4, unicodeScript5, unicodeScript, unicodeScript5, unicodeScript6, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript41, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript41, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript8, unicodeScript7, unicodeScript9, unicodeScript66, unicodeScript83, unicodeScript84, unicodeScript7, unicodeScript10, unicodeScript41, unicodeScript10, unicodeScript, unicodeScript10, unicodeScript11, unicodeScript12, unicodeScript13, unicodeScript14, unicodeScript15, unicodeScript16, unicodeScript17, unicodeScript18, unicodeScript19, unicodeScript20, unicodeScript, unicodeScript20, unicodeScript21, unicodeScript22, unicodeScript, unicodeScript22, unicodeScript23, unicodeScript24, unicodeScript, unicodeScript24, unicodeScript25, unicodeScript26, unicodeScript27, unicodeScript28, unicodeScript29, unicodeScript30, unicodeScript, unicodeScript30, unicodeScript42, unicodeScript43, unicodeScript, unicodeScript44, unicodeScript45, unicodeScript31, unicodeScript32, unicodeScript, unicodeScript32, unicodeScript, unicodeScript32, unicodeScript28, unicodeScript46, unicodeScript47, unicodeScript56, unicodeScript31, unicodeScript54, unicodeScript79, unicodeScript62, unicodeScript67, unicodeScript68, unicodeScript69, unicodeScript70, unicodeScript67, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript2, unicodeScript3, unicodeScript4, unicodeScript2, unicodeScript3, unicodeScript2, unicodeScript3, unicodeScript2, unicodeScript4, unicodeScript2, unicodeScript3, unicodeScript41, unicodeScript2, unicodeScript3, unicodeScript, unicodeScript41, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript41, unicodeScript, unicodeScript3, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript53, unicodeScript, unicodeScript57, unicodeScript2, unicodeScript55, unicodeScript24, unicodeScript58, unicodeScript26, unicodeScript4, unicodeScript, unicodeScript36, unicodeScript, unicodeScript36, unicodeScript, unicodeScript36, unicodeScript, unicodeScript36, unicodeScript41, unicodeScript25, unicodeScript, unicodeScript36, unicodeScript, unicodeScript33, unicodeScript41, unicodeScript, unicodeScript33, unicodeScript, unicodeScript34, unicodeScript, unicodeScript34, unicodeScript35, unicodeScript25, unicodeScript, unicodeScript35, unicodeScript, unicodeScript34, unicodeScript25, unicodeScript, unicodeScript25, unicodeScript, unicodeScript34, unicodeScript, unicodeScript36, unicodeScript, unicodeScript36, unicodeScript37, unicodeScript85, unicodeScript71, unicodeScript4, unicodeScript86, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript59, unicodeScript, unicodeScript65, unicodeScript72, unicodeScript10, unicodeScript73, unicodeScript74, unicodeScript25, unicodeScript87, unicodeScript78, unicodeScript23, unicodeScript80, unicodeScript88, unicodeScript26, unicodeScript88, unicodeScript25, unicodeScript103, unicodeScript36, unicodeScript2, unicodeScript5, unicodeScript6, unicodeScript7, unicodeScript, unicodeScript7, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript7, unicodeScript, unicodeScript2, unicodeScript, unicodeScript2, unicodeScript, unicodeScript34, unicodeScript, unicodeScript34, unicodeScript, unicodeScript25, unicodeScript, unicodeScript48, unicodeScript, unicodeScript3, unicodeScript, unicodeScript41, unicodeScript75, unicodeScript76, unicodeScript38, unicodeScript39, unicodeScript49, unicodeScript60, unicodeScript40, unicodeScript50, unicodeScript51, unicodeScript52, unicodeScript89, unicodeScript64, unicodeScript77, unicodeScript96, unicodeScript97, unicodeScript61, unicodeScript90, unicodeScript81, unicodeScript91, unicodeScript92, unicodeScript93, unicodeScript7, unicodeScript94, unicodeScript95, unicodeScript98, unicodeScript99, unicodeScript100, unicodeScript101, unicodeScript63, unicodeScript82, unicodeScript86, unicodeScript102, unicodeScript34, unicodeScript33, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript41, unicodeScript, unicodeScript3, unicodeScript, unicodeScript7, unicodeScript, unicodeScript33, unicodeScript, unicodeScript36, unicodeScript, unicodeScript41, unicodeScript103};
            aliases = new HashMap(128);
            aliases.put("ARAB", ARABIC);
            aliases.put("ARMI", IMPERIAL_ARAMAIC);
            aliases.put("ARMN", ARMENIAN);
            aliases.put("AVST", AVESTAN);
            aliases.put("BALI", BALINESE);
            aliases.put("BAMU", BAMUM);
            aliases.put("BATK", BATAK);
            aliases.put("BENG", BENGALI);
            aliases.put("BOPO", BOPOMOFO);
            aliases.put("BRAI", BRAILLE);
            aliases.put("BRAH", BRAHMI);
            aliases.put("BUGI", BUGINESE);
            aliases.put("BUHD", BUHID);
            aliases.put("CAKM", CHAKMA);
            aliases.put("CANS", CANADIAN_ABORIGINAL);
            aliases.put("CARI", CARIAN);
            aliases.put("CHAM", CHAM);
            aliases.put("CHER", CHEROKEE);
            aliases.put("COPT", COPTIC);
            aliases.put("CPRT", CYPRIOT);
            aliases.put("CYRL", CYRILLIC);
            aliases.put("DEVA", DEVANAGARI);
            aliases.put("DSRT", DESERET);
            aliases.put("EGYP", EGYPTIAN_HIEROGLYPHS);
            aliases.put("ETHI", ETHIOPIC);
            aliases.put("GEOR", GEORGIAN);
            aliases.put("GLAG", GLAGOLITIC);
            aliases.put("GOTH", GOTHIC);
            aliases.put("GREK", GREEK);
            aliases.put("GUJR", GUJARATI);
            aliases.put("GURU", GURMUKHI);
            aliases.put("HANG", HANGUL);
            aliases.put("HANI", HAN);
            aliases.put("HANO", HANUNOO);
            aliases.put("HEBR", HEBREW);
            aliases.put("HIRA", HIRAGANA);
            aliases.put("ITAL", OLD_ITALIC);
            aliases.put("JAVA", JAVANESE);
            aliases.put("KALI", KAYAH_LI);
            aliases.put("KANA", KATAKANA);
            aliases.put("KHAR", KHAROSHTHI);
            aliases.put("KHMR", KHMER);
            aliases.put("KNDA", KANNADA);
            aliases.put("KTHI", KAITHI);
            aliases.put("LANA", TAI_THAM);
            aliases.put("LAOO", LAO);
            aliases.put("LATN", LATIN);
            aliases.put("LEPC", LEPCHA);
            aliases.put("LIMB", LIMBU);
            aliases.put("LINB", LINEAR_B);
            aliases.put("LISU", LISU);
            aliases.put("LYCI", LYCIAN);
            aliases.put("LYDI", LYDIAN);
            aliases.put("MAND", MANDAIC);
            aliases.put("MERC", MEROITIC_CURSIVE);
            aliases.put("MERO", MEROITIC_HIEROGLYPHS);
            aliases.put("MLYM", MALAYALAM);
            aliases.put("MONG", MONGOLIAN);
            aliases.put("MTEI", MEETEI_MAYEK);
            aliases.put("MYMR", MYANMAR);
            aliases.put("NKOO", NKO);
            aliases.put("OGAM", OGHAM);
            aliases.put("OLCK", OL_CHIKI);
            aliases.put("ORKH", OLD_TURKIC);
            aliases.put("ORYA", ORIYA);
            aliases.put("OSMA", OSMANYA);
            aliases.put("PHAG", PHAGS_PA);
            aliases.put("PLRD", MIAO);
            aliases.put("PHLI", INSCRIPTIONAL_PAHLAVI);
            aliases.put("PHNX", PHOENICIAN);
            aliases.put("PRTI", INSCRIPTIONAL_PARTHIAN);
            aliases.put("RJNG", REJANG);
            aliases.put("RUNR", RUNIC);
            aliases.put("SAMR", SAMARITAN);
            aliases.put("SARB", OLD_SOUTH_ARABIAN);
            aliases.put("SAUR", SAURASHTRA);
            aliases.put("SHAW", SHAVIAN);
            aliases.put("SHRD", SHARADA);
            aliases.put("SINH", SINHALA);
            aliases.put("SORA", SORA_SOMPENG);
            aliases.put("SUND", SUNDANESE);
            aliases.put("SYLO", SYLOTI_NAGRI);
            aliases.put("SYRC", SYRIAC);
            aliases.put("TAGB", TAGBANWA);
            aliases.put("TALE", TAI_LE);
            aliases.put("TAKR", TAKRI);
            aliases.put("TALU", NEW_TAI_LUE);
            aliases.put("TAML", TAMIL);
            aliases.put("TAVT", TAI_VIET);
            aliases.put("TELU", TELUGU);
            aliases.put("TFNG", TIFINAGH);
            aliases.put("TGLG", TAGALOG);
            aliases.put("THAA", THAANA);
            aliases.put("THAI", THAI);
            aliases.put("TIBT", TIBETAN);
            aliases.put("UGAR", UGARITIC);
            aliases.put("VAII", VAI);
            aliases.put("XPEO", OLD_PERSIAN);
            aliases.put("XSUX", CUNEIFORM);
            aliases.put("YIII", YI);
            aliases.put("ZINH", INHERITED);
            aliases.put("ZYYY", COMMON);
            aliases.put("ZZZZ", UNKNOWN);
        }

        public static final UnicodeScript forName(String object) {
            String string = object.toUpperCase(Locale.ENGLISH);
            object = aliases.get(string);
            if (object != null) {
                return object;
            }
            return UnicodeScript.valueOf(string);
        }

        public static UnicodeScript of(int n) {
            if (Character.isValidCodePoint(n)) {
                int n2;
                if (Character.getType(n) == 0) {
                    return UNKNOWN;
                }
                n = n2 = Arrays.binarySearch(scriptStarts, n);
                if (n2 < 0) {
                    n = -n2 - 2;
                }
                return scripts[n];
            }
            throw new IllegalArgumentException();
        }

        public static UnicodeScript valueOf(String string) {
            return Enum.valueOf(UnicodeScript.class, string);
        }

        public static UnicodeScript[] values() {
            return (UnicodeScript[])$VALUES.clone();
        }
    }

}

