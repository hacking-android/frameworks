/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.IllegalIcuArgumentException;
import android.icu.impl.PatternProps;
import android.icu.lang.UCharacter;
import android.icu.text.Replaceable;
import android.icu.text.UTF16;
import android.icu.text.UnicodeMatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public final class Utility {
    private static final char APOSTROPHE = '\'';
    private static final char BACKSLASH = '\\';
    static final char[] DIGITS;
    private static final char ESCAPE = '\ua5a5';
    static final byte ESCAPE_BYTE = -91;
    static final char[] HEX_DIGIT;
    public static String LINE_SEPARATOR;
    private static final int MAGIC_UNSIGNED = Integer.MIN_VALUE;
    private static final char[] UNESCAPE_MAP;

    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
        HEX_DIGIT = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        UNESCAPE_MAP = new char[]{'a', '\u0007', 'b', '\b', 'e', '\u001b', 'f', '\f', 'n', '\n', 'r', '\r', 't', '\t', 'v', '\u000b'};
        DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }

    public static final byte[] RLEStringToByteArray(String string) {
        int n = string.charAt(0) << 16 | string.charAt(1);
        byte[] arrby = new byte[n];
        byte by = 1;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 2;
        int n6 = 0;
        while (n6 < n) {
            int n7;
            if (by != 0) {
                n7 = n5 + 1;
                n2 = string.charAt(n5);
                n5 = (byte)(n2 >> 8);
                by = 0;
            } else {
                by = (byte)(n2 & 255);
                byte by2 = 1;
                n7 = n5;
                n5 = by;
                by = by2;
            }
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        n5 = n3;
                    } else {
                        n3 = 0;
                        while (n3 < n4) {
                            arrby[n6] = (byte)n5;
                            ++n3;
                            ++n6;
                        }
                        n5 = 0;
                    }
                } else if (n5 == -91) {
                    arrby[n6] = (byte)-91;
                    n5 = 0;
                    ++n6;
                } else {
                    n4 = n5;
                    if (n5 < 0) {
                        n4 = n5 + 256;
                    }
                    n5 = 2;
                }
            } else if (n5 == -91) {
                n5 = 1;
            } else {
                arrby[n6] = (byte)n5;
                ++n6;
                n5 = n3;
            }
            n3 = n5;
            n5 = n7;
        }
        if (n3 == 0) {
            if (n5 == string.length()) {
                return arrby;
            }
            throw new IllegalStateException("Excess data in RLE byte array string");
        }
        throw new IllegalStateException("Bad run-length encoded byte array");
    }

    public static final char[] RLEStringToCharArray(String string) {
        int n = string.charAt(0) << 16 | string.charAt(1);
        char[] arrc = new char[n];
        int n2 = 0;
        for (int i = 2; i < string.length(); ++i) {
            int n3 = string.charAt(i);
            if (n3 == 42405) {
                char c;
                if ((c = string.charAt(++i)) == '\ua5a5') {
                    arrc[n2] = c;
                    ++n2;
                    continue;
                }
                n3 = i + 1;
                char c2 = string.charAt(n3);
                i = 0;
                while (i < c) {
                    arrc[n2] = c2;
                    ++i;
                    ++n2;
                }
                i = n3;
                continue;
            }
            arrc[n2] = (char)n3;
            ++n2;
        }
        if (n2 == n) {
            return arrc;
        }
        throw new IllegalStateException("Bad run-length encoded short array");
    }

    public static final int[] RLEStringToIntArray(String string) {
        int n = Utility.getInt(string, 0);
        int[] arrn = new int[n];
        int n2 = 0;
        int n3 = 1;
        int n4 = string.length() / 2;
        while (n2 < n && n3 < n4) {
            int n5 = n3 + 1;
            if ((n3 = Utility.getInt(string, n3)) == 42405) {
                int n6 = n5 + 1;
                if ((n5 = Utility.getInt(string, n5)) == 42405) {
                    arrn[n2] = n5;
                    ++n2;
                    n3 = n6;
                    continue;
                }
                int n7 = Utility.getInt(string, n6);
                n3 = 0;
                while (n3 < n5) {
                    arrn[n2] = n7;
                    ++n3;
                    ++n2;
                }
                n3 = n6 + 1;
                continue;
            }
            arrn[n2] = n3;
            n3 = n5;
            ++n2;
        }
        if (n2 == n && n3 == n4) {
            return arrn;
        }
        throw new IllegalStateException("Bad run-length encoded int array");
    }

    public static final short[] RLEStringToShortArray(String string) {
        int n = string.charAt(0) << 16 | string.charAt(1);
        short[] arrs = new short[n];
        int n2 = 0;
        for (int i = 2; i < string.length(); ++i) {
            int n3 = string.charAt(i);
            if (n3 == 42405) {
                char c;
                if ((c = string.charAt(++i)) == '\ua5a5') {
                    arrs[n2] = (short)c;
                    ++n2;
                    continue;
                }
                n3 = i + 1;
                short s = (short)string.charAt(n3);
                i = 0;
                while (i < c) {
                    arrs[n2] = s;
                    ++i;
                    ++n2;
                }
                i = n3;
                continue;
            }
            arrs[n2] = (short)n3;
            ++n2;
        }
        if (n2 == n) {
            return arrs;
        }
        throw new IllegalStateException("Bad run-length encoded short array");
    }

    public static int addExact(int n, int n2) {
        int n3 = n + n2;
        if (((n ^ n3) & (n2 ^ n3)) >= 0) {
            return n3;
        }
        throw new ArithmeticException("integer overflow");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static final <T extends Appendable> void appendEncodedByte(T t, byte by, byte[] arrby) {
        if (arrby[0] == 0) {
            arrby[0] = (byte)(true ? 1 : 0);
            arrby[1] = by;
            return;
        }
        try {
            t.append((char)(arrby[1] << 8 | by & 255));
        }
        catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
        arrby[0] = (byte)(false ? 1 : 0);
    }

    private static final <T extends Appendable> void appendInt(T t, int n) {
        char c = (char)(n >>> 16);
        try {
            t.append(c);
            t.append((char)(65535 & n));
            return;
        }
        catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <T extends Appendable> T appendNumber(T var0, int var1_2, int var2_3, int var3_4) {
        if (var2_3 < 2 || var2_3 > 36) ** GOTO lbl10
        var4_5 = var1_2;
        if (var1_2 >= 0) ** GOTO lbl8
        var4_5 = -var1_2;
        try {
            var0.append("-");
lbl8: // 2 sources:
            Utility.recursiveAppendNumber(var0, var4_5, var2_3, var3_4);
            return (T)var0;
lbl10: // 1 sources:
            var0 = new StringBuilder();
            var0.append("Illegal radix ");
            var0.append(var2_3);
            var5_6 = new IllegalArgumentException(var0.toString());
            throw var5_6;
        }
        catch (IOException var0_1) {
            throw new IllegalIcuArgumentException(var0_1);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, int n, boolean bl, boolean bl2, StringBuffer stringBuffer2) {
        if (!(bl || bl2 && Utility.isUnprintable(n))) {
            if (stringBuffer2.length() == 0 && (n == 39 || n == 92)) {
                stringBuffer.append('\\');
                stringBuffer.append((char)n);
            } else if (stringBuffer2.length() <= 0 && (n < 33 || n > 126 || n >= 48 && n <= 57 || n >= 65 && n <= 90 || n >= 97 && n <= 122) && !PatternProps.isWhiteSpace(n)) {
                stringBuffer.appendCodePoint(n);
            } else {
                stringBuffer2.appendCodePoint(n);
                if (n == 39) {
                    stringBuffer2.append((char)n);
                }
            }
        } else {
            if (stringBuffer2.length() > 0) {
                while (stringBuffer2.length() >= 2 && stringBuffer2.charAt(0) == '\'' && stringBuffer2.charAt(1) == '\'') {
                    stringBuffer.append('\\');
                    stringBuffer.append('\'');
                    stringBuffer2.delete(0, 2);
                }
                int n2 = 0;
                while (stringBuffer2.length() >= 2 && stringBuffer2.charAt(stringBuffer2.length() - 2) == '\'' && stringBuffer2.charAt(stringBuffer2.length() - 1) == '\'') {
                    stringBuffer2.setLength(stringBuffer2.length() - 2);
                    ++n2;
                }
                int n3 = n2;
                if (stringBuffer2.length() > 0) {
                    stringBuffer.append('\'');
                    stringBuffer.append(stringBuffer2);
                    stringBuffer.append('\'');
                    stringBuffer2.setLength(0);
                    n3 = n2;
                }
                while (n3 > 0) {
                    stringBuffer.append('\\');
                    stringBuffer.append('\'');
                    --n3;
                }
            }
            if (n != -1) {
                if (n == 32) {
                    n = stringBuffer.length();
                    if (n > 0 && stringBuffer.charAt(n - 1) != ' ') {
                        stringBuffer.append(' ');
                    }
                } else if (!bl2 || !Utility.escapeUnprintable(stringBuffer, n)) {
                    stringBuffer.appendCodePoint(n);
                }
            }
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, UnicodeMatcher unicodeMatcher, boolean bl, StringBuffer stringBuffer2) {
        if (unicodeMatcher != null) {
            Utility.appendToRule(stringBuffer, unicodeMatcher.toPattern(bl), true, bl, stringBuffer2);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, String string, boolean bl, boolean bl2, StringBuffer stringBuffer2) {
        for (int i = 0; i < string.length(); ++i) {
            Utility.appendToRule(stringBuffer, string.charAt(i), bl, bl2, stringBuffer2);
        }
    }

    public static final boolean arrayEquals(Object object, Object object2) {
        if (object == null) {
            boolean bl = object2 == null;
            return bl;
        }
        if (object instanceof Object[]) {
            return Utility.arrayEquals((Object[])object, object2);
        }
        if (object instanceof int[]) {
            return Utility.arrayEquals((int[])object, object2);
        }
        if (object instanceof double[]) {
            return Utility.arrayEquals((double[])object, object2);
        }
        if (object instanceof byte[]) {
            return Utility.arrayEquals((byte[])object, object2);
        }
        return object.equals(object2);
    }

    public static final boolean arrayEquals(byte[] arrby, Object arrby2) {
        boolean bl = true;
        boolean bl2 = true;
        if (arrby == null) {
            if (arrby2 != null) {
                bl2 = false;
            }
            return bl2;
        }
        if (!(arrby2 instanceof byte[])) {
            return false;
        }
        bl2 = arrby.length == (arrby2 = (byte[])arrby2).length && Utility.arrayRegionMatches(arrby, 0, arrby2, 0, arrby.length) ? bl : false;
        return bl2;
    }

    public static final boolean arrayEquals(double[] arrd, Object arrd2) {
        boolean bl = true;
        boolean bl2 = true;
        if (arrd == null) {
            if (arrd2 != null) {
                bl2 = false;
            }
            return bl2;
        }
        if (!(arrd2 instanceof double[])) {
            return false;
        }
        bl2 = arrd.length == (arrd2 = (double[])arrd2).length && Utility.arrayRegionMatches(arrd, 0, arrd2, 0, arrd.length) ? bl : false;
        return bl2;
    }

    public static final boolean arrayEquals(int[] arrn, Object arrn2) {
        boolean bl = true;
        boolean bl2 = true;
        if (arrn == null) {
            if (arrn2 != null) {
                bl2 = false;
            }
            return bl2;
        }
        if (!(arrn2 instanceof int[])) {
            return false;
        }
        bl2 = arrn.length == (arrn2 = (int[])arrn2).length && Utility.arrayRegionMatches(arrn, 0, arrn2, 0, arrn.length) ? bl : false;
        return bl2;
    }

    public static final boolean arrayEquals(Object[] arrobject, Object arrobject2) {
        boolean bl = true;
        boolean bl2 = true;
        if (arrobject == null) {
            if (arrobject2 != null) {
                bl2 = false;
            }
            return bl2;
        }
        if (!(arrobject2 instanceof Object[])) {
            return false;
        }
        bl2 = arrobject.length == (arrobject2 = (Object[])arrobject2).length && Utility.arrayRegionMatches(arrobject, 0, arrobject2, 0, arrobject.length) ? bl : false;
        return bl2;
    }

    public static final boolean arrayRegionMatches(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (arrby[i] == arrby2[i + (n2 - n)]) continue;
            return false;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(char[] arrc, int n, char[] arrc2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (arrc[i] == arrc2[i + (n2 - n)]) continue;
            return false;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(double[] arrd, int n, double[] arrd2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (arrd[i] == arrd2[i + (n2 - n)]) continue;
            return false;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(int[] arrn, int n, int[] arrn2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (arrn[i] == arrn2[i + (n2 - n)]) continue;
            return false;
        }
        return true;
    }

    public static final boolean arrayRegionMatches(Object[] arrobject, int n, Object[] arrobject2, int n2, int n3) {
        for (int i = n; i < n + n3; ++i) {
            if (Utility.arrayEquals(arrobject[i], arrobject2[i + (n2 - n)])) continue;
            return false;
        }
        return true;
    }

    public static final String arrayToRLEString(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(arrby.length >> 16));
        stringBuilder.append((char)arrby.length);
        byte by = arrby[0];
        int n = 1;
        byte[] arrby2 = new byte[2];
        byte by2 = by;
        for (int i = 1; i < arrby.length; ++i) {
            by = arrby[i];
            if (by == by2 && n < 255) {
                ++n;
                by = by2;
            } else {
                Utility.encodeRun(stringBuilder, by2, n, arrby2);
                n = 1;
            }
            by2 = by;
        }
        Utility.encodeRun(stringBuilder, by2, n, arrby2);
        if (arrby2[0] != 0) {
            Utility.appendEncodedByte(stringBuilder, (byte)0, arrby2);
        }
        return stringBuilder.toString();
    }

    public static final String arrayToRLEString(char[] arrc) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(arrc.length >> 16));
        stringBuilder.append((char)arrc.length);
        char c = arrc[0];
        int n = 1;
        for (int i = 1; i < arrc.length; ++i) {
            char c2 = arrc[i];
            if (c2 == c && n < 65535) {
                ++n;
                continue;
            }
            Utility.encodeRun(stringBuilder, (short)c, n);
            c = c2;
            n = 1;
        }
        Utility.encodeRun(stringBuilder, (short)c, n);
        return stringBuilder.toString();
    }

    public static final String arrayToRLEString(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder();
        Utility.appendInt(stringBuilder, arrn.length);
        int n = arrn[0];
        int n2 = 1;
        for (int i = 1; i < arrn.length; ++i) {
            int n3 = arrn[i];
            if (n3 == n && n2 < 65535) {
                ++n2;
                continue;
            }
            Utility.encodeRun(stringBuilder, n, n2);
            n = n3;
            n2 = 1;
        }
        Utility.encodeRun(stringBuilder, n, n2);
        return stringBuilder.toString();
    }

    public static final String arrayToRLEString(short[] arrs) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(arrs.length >> 16));
        stringBuilder.append((char)arrs.length);
        short s = arrs[0];
        int n = 1;
        short s2 = s;
        for (int i = 1; i < arrs.length; ++i) {
            s = arrs[i];
            if (s == s2 && n < 65535) {
                ++n;
                s = s2;
            } else {
                Utility.encodeRun(stringBuilder, s2, n);
                n = 1;
            }
            s2 = s;
        }
        Utility.encodeRun(stringBuilder, s2, n);
        return stringBuilder.toString();
    }

    public static boolean charSequenceEquals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence != null && charSequence2 != null) {
            if (charSequence.length() != charSequence2.length()) {
                return false;
            }
            for (int i = 0; i < charSequence.length(); ++i) {
                if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static int charSequenceHashCode(CharSequence charSequence) {
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            n = n * 31 + charSequence.charAt(i);
        }
        return n;
    }

    public static <T extends Comparable<T>> int checkCompare(T t, T t2) {
        int n = t == null ? (t2 == null ? 0 : -1) : (t2 == null ? 1 : t.compareTo(t2));
        return n;
    }

    public static int checkHash(Object object) {
        int n = object == null ? 0 : object.hashCode();
        return n;
    }

    public static final int compareUnsigned(int n, int n2) {
        if ((n += Integer.MIN_VALUE) < (n2 += Integer.MIN_VALUE)) {
            return -1;
        }
        return n > n2;
    }

    private static final <T extends Appendable> void encodeRun(T t, byte by, int n, byte[] arrby) {
        if (n < 4) {
            for (int i = 0; i < n; ++i) {
                if (by == -91) {
                    Utility.appendEncodedByte(t, (byte)-91, arrby);
                }
                Utility.appendEncodedByte(t, by, arrby);
            }
        } else {
            int n2 = n;
            if ((byte)n == -91) {
                if (by == -91) {
                    Utility.appendEncodedByte(t, (byte)-91, arrby);
                }
                Utility.appendEncodedByte(t, by, arrby);
                n2 = n - 1;
            }
            Utility.appendEncodedByte(t, (byte)-91, arrby);
            Utility.appendEncodedByte(t, (byte)n2, arrby);
            Utility.appendEncodedByte(t, by, arrby);
        }
    }

    private static final <T extends Appendable> void encodeRun(T t, int n, int n2) {
        if (n2 < 4) {
            for (int i = 0; i < n2; ++i) {
                if (n == 42405) {
                    Utility.appendInt(t, n);
                }
                Utility.appendInt(t, n);
            }
        } else {
            int n3 = n2;
            if (n2 == 42405) {
                if (n == 42405) {
                    Utility.appendInt(t, 42405);
                }
                Utility.appendInt(t, n);
                n3 = n2 - 1;
            }
            Utility.appendInt(t, 42405);
            Utility.appendInt(t, n3);
            Utility.appendInt(t, n);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static final <T extends Appendable> void encodeRun(T var0, short var1_2, int var2_3) {
        block5 : {
            block6 : {
                var3_4 = (char)var1_2;
                if (var2_3 < 4) break block5;
                var1_2 = (short)var2_3;
                if (var2_3 != 42405) break block6;
                if (var3_4 != '\ua5a5') ** GOTO lbl9
                var0.append('\ua5a5');
lbl9: // 2 sources:
                var0.append(var3_4);
                var1_2 = (short)(var2_3 - 1);
            }
            try {
                var0.append('\ua5a5');
                var0.append((char)var1_2);
                var0.append(var3_4);
                return;
            }
            catch (IOException var0_1) {
                throw new IllegalIcuArgumentException(var0_1);
            }
        }
        var1_2 = 0;
        while (var1_2 < var2_3) {
            if (var3_4 != '\ua5a5') ** GOTO lbl28
            var0.append('\ua5a5');
lbl28: // 2 sources:
            var0.append(var3_4);
            var1_2 = (short)(var1_2 + 1);
        }
        return;
    }

    public static final String escape(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < string.length()) {
            int n2 = Character.codePointAt(string, n);
            int n3 = n + UTF16.getCharCount(n2);
            if (n2 >= 32 && n2 <= 127) {
                if (n2 == 92) {
                    stringBuilder.append("\\\\");
                } else {
                    stringBuilder.append((char)n2);
                }
            } else {
                n = n2 <= 65535 ? 1 : 0;
                String string2 = n != 0 ? "\\u" : "\\U";
                stringBuilder.append(string2);
                long l = n2;
                n = n != 0 ? 4 : 8;
                stringBuilder.append(Utility.hex(l, n));
            }
            n = n3;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T extends Appendable> boolean escapeUnprintable(T t, int n) {
        try {
            if (!Utility.isUnprintable(n)) {
                return false;
            }
            t.append('\\');
            if ((-65536 & n) != 0) {
                t.append('U');
                t.append(DIGITS[n >> 28 & 15]);
                t.append(DIGITS[n >> 24 & 15]);
                t.append(DIGITS[n >> 20 & 15]);
                t.append(DIGITS[n >> 16 & 15]);
            } else {
                t.append('u');
            }
            t.append(DIGITS[n >> 12 & 15]);
            t.append(DIGITS[n >> 8 & 15]);
            t.append(DIGITS[n >> 4 & 15]);
            t.append(DIGITS[n & 15]);
            return true;
        }
        catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    public static final String format1ForSource(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"");
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= ' ' && c != '\"' && c != '\\') {
                if (c <= '~') {
                    stringBuilder.append(c);
                    continue;
                }
                stringBuilder.append("\\u");
                stringBuilder.append(HEX_DIGIT[(61440 & c) >> 12]);
                stringBuilder.append(HEX_DIGIT[(c & 3840) >> 8]);
                stringBuilder.append(HEX_DIGIT[(c & 240) >> 4]);
                stringBuilder.append(HEX_DIGIT[c & 15]);
                continue;
            }
            if (c == '\n') {
                stringBuilder.append("\\n");
                continue;
            }
            if (c == '\t') {
                stringBuilder.append("\\t");
                continue;
            }
            if (c == '\r') {
                stringBuilder.append("\\r");
                continue;
            }
            stringBuilder.append('\\');
            stringBuilder.append(HEX_DIGIT[(c & 448) >> 6]);
            stringBuilder.append(HEX_DIGIT[(c & 56) >> 3]);
            stringBuilder.append(HEX_DIGIT[c & 7]);
        }
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    public static final String formatForSource(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < string.length()) {
            if (n > 0) {
                stringBuilder.append('+');
                stringBuilder.append(LINE_SEPARATOR);
            }
            stringBuilder.append("        \"");
            int n2 = 11;
            while (n < string.length() && n2 < 80) {
                char c = string.charAt(n);
                if (c >= ' ' && c != '\"' && c != '\\') {
                    if (c <= '~') {
                        stringBuilder.append(c);
                        ++n2;
                    } else {
                        stringBuilder.append("\\u");
                        stringBuilder.append(HEX_DIGIT[(61440 & c) >> 12]);
                        stringBuilder.append(HEX_DIGIT[(c & 3840) >> 8]);
                        stringBuilder.append(HEX_DIGIT[(c & 240) >> 4]);
                        stringBuilder.append(HEX_DIGIT[c & 15]);
                        n2 += 6;
                    }
                } else if (c == '\n') {
                    stringBuilder.append("\\n");
                    n2 += 2;
                } else if (c == '\t') {
                    stringBuilder.append("\\t");
                    n2 += 2;
                } else if (c == '\r') {
                    stringBuilder.append("\\r");
                    n2 += 2;
                } else {
                    stringBuilder.append('\\');
                    stringBuilder.append(HEX_DIGIT[(c & 448) >> 6]);
                    stringBuilder.append(HEX_DIGIT[(c & 56) >> 3]);
                    stringBuilder.append(HEX_DIGIT[c & 7]);
                    n2 += 4;
                }
                ++n;
            }
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    public static String fromHex(String string, int n, String string2) {
        if (string2 == null) {
            string2 = "\\s+";
        }
        return Utility.fromHex(string, n, Pattern.compile(string2));
    }

    public static String fromHex(String string, int n, Pattern object) {
        StringBuilder stringBuilder = new StringBuilder();
        object = ((Pattern)object).split(string);
        int n2 = ((String[])object).length;
        for (int i = 0; i < n2; ++i) {
            string = object[i];
            if (string.length() >= n) {
                stringBuilder.appendCodePoint(Integer.parseInt(string, 16));
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("code point too short: ");
            ((StringBuilder)object).append(string);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return stringBuilder.toString();
    }

    static final int getInt(String string, int n) {
        return string.charAt(n * 2) << 16 | string.charAt(n * 2 + 1);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static <S extends CharSequence, U extends CharSequence, T extends Appendable> T hex(S var0, int var1_2, U var2_3, boolean var3_4, T var4_5) {
        var5_6 = 0;
        var6_7 = 0;
        if (!var3_4) ** GOTO lbl15
        try {
            while (var6_7 < var0.length()) {
                var5_6 = Character.codePointAt(var0, var6_7);
                if (var6_7 != 0) {
                    var4_5.append(var2_3);
                }
                var4_5.append(Utility.hex(var5_6, var1_2));
                var6_7 += UTF16.getCharCount(var5_6);
            }
            return var4_5;
lbl15: // 1 sources:
            var6_7 = var5_6;
            while (var6_7 < var0.length()) {
                if (var6_7 != 0) {
                    var4_5.append(var2_3);
                }
                var4_5.append(Utility.hex(var0.charAt(var6_7), var1_2));
                ++var6_7;
            }
            return var4_5;
        }
        catch (IOException var0_1) {
            throw new IllegalIcuArgumentException(var0_1);
        }
    }

    public static String hex(long l) {
        return Utility.hex(l, 4);
    }

    public static String hex(long l, int n) {
        CharSequence charSequence;
        if (l == Long.MIN_VALUE) {
            return "-8000000000000000";
        }
        boolean bl = l < 0L;
        long l2 = l;
        if (bl) {
            l2 = -l;
        }
        CharSequence charSequence2 = charSequence = Long.toString(l2, 16).toUpperCase(Locale.ENGLISH);
        if (((String)charSequence).length() < n) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("0000000000000000".substring(((String)charSequence).length(), n));
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        if (bl) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append('-');
            ((StringBuilder)charSequence).append((String)charSequence2);
            return ((StringBuilder)charSequence).toString();
        }
        return charSequence2;
    }

    public static String hex(CharSequence charSequence) {
        return Utility.hex(charSequence, 4, ",", true, new StringBuilder()).toString();
    }

    public static <S extends CharSequence> String hex(S s, int n, S s2) {
        return Utility.hex(s, n, s2, true, new StringBuilder()).toString();
    }

    public static String hex(byte[] arrby, int n, int n2, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        while (n < n2) {
            if (n != 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(Utility.hex(arrby[n]));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static final byte highBit(int n) {
        if (n <= 0) {
            return -1;
        }
        int n2 = 0;
        int n3 = n;
        if (n >= 65536) {
            n3 = n >> 16;
            n2 = (byte)(0 + 16);
        }
        n = n2;
        int n4 = n3;
        if (n3 >= 256) {
            n4 = n3 >> 8;
            n = (byte)(n2 + 8);
        }
        n3 = n;
        n2 = n4;
        if (n4 >= 16) {
            n2 = n4 >> 4;
            n3 = (byte)(n + 4);
        }
        n4 = n3;
        n = n2;
        if (n2 >= 4) {
            n = n2 >> 2;
            n4 = (byte)(n3 + 2);
        }
        int n5 = n4;
        if (n >= 2) {
            n5 = n = (int)((byte)(n4 + 1));
        }
        return (byte)n5;
    }

    public static boolean isUnprintable(int n) {
        boolean bl = n < 32 || n > 126;
        return bl;
    }

    public static int lookup(String string, String[] arrstring) {
        for (int i = 0; i < arrstring.length; ++i) {
            if (!string.equals(arrstring[i])) continue;
            return i;
        }
        return -1;
    }

    public static boolean parseChar(String string, int[] arrn, char c) {
        int n = arrn[0];
        arrn[0] = PatternProps.skipWhiteSpace(string, arrn[0]);
        if (arrn[0] != string.length() && string.charAt(arrn[0]) == c) {
            arrn[0] = arrn[0] + 1;
            return true;
        }
        arrn[0] = n;
        return false;
    }

    public static int parseInteger(String string, int[] arrn, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = 0;
        int n7 = 0;
        int n8 = arrn[0];
        int n9 = 10;
        if (string.regionMatches(true, n8, "0x", 0, 2)) {
            n3 = n8 + 2;
            n2 = 16;
            n5 = n6;
            n4 = n7;
        } else {
            n5 = n6;
            n4 = n7;
            n3 = n8;
            n2 = n9;
            if (n8 < n) {
                n5 = n6;
                n4 = n7;
                n3 = n8;
                n2 = n9;
                if (string.charAt(n8) == '0') {
                    n3 = n8 + 1;
                    n5 = 1;
                    n2 = 8;
                    n4 = n7;
                }
            }
        }
        do {
            n7 = n3;
            if (n3 >= n) break;
            n7 = n3 + 1;
            if ((n3 = UCharacter.digit(string.charAt(n3), n2)) < 0) {
                --n7;
                break;
            }
            ++n5;
            if ((n3 = n4 * n2 + n3) <= n4) {
                return 0;
            }
            n4 = n3;
            n3 = n7;
        } while (true);
        if (n5 > 0) {
            arrn[0] = n7;
        }
        return n4;
    }

    public static int parseNumber(String string, int[] arrn, int n) {
        int n2;
        int n3;
        int n4 = 0;
        for (n2 = arrn[0]; n2 < string.length() && (n3 = UCharacter.digit(Character.codePointAt(string, n2), n)) >= 0; ++n2) {
            if ((n4 = n * n4 + n3) >= 0) continue;
            return -1;
        }
        if (n2 == arrn[0]) {
            return -1;
        }
        arrn[0] = n2;
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int parsePattern(String string, int n, int n2, String string2, int[] arrn) {
        int[] arrn2 = new int[1];
        int n3 = 0;
        int n4 = 0;
        do {
            block10 : {
                int n5;
                block9 : {
                    block7 : {
                        block8 : {
                            if (n4 >= string2.length()) {
                                return n;
                            }
                            char c = string2.charAt(n4);
                            if (c == ' ') break block7;
                            if (c == '#') break block8;
                            n5 = n;
                            if (c == '~') break block9;
                            if (n >= n2) {
                                return -1;
                            }
                            if ((char)UCharacter.toLowerCase(string.charAt(n)) != c) {
                                return -1;
                            }
                            ++n;
                            break block10;
                        }
                        arrn2[0] = n;
                        arrn[n3] = Utility.parseInteger(string, arrn2, n2);
                        if (arrn2[0] == n) {
                            return -1;
                        }
                        n = arrn2[0];
                        ++n3;
                        break block10;
                    }
                    if (n >= n2) {
                        return -1;
                    }
                    if (!PatternProps.isWhiteSpace(string.charAt(n))) {
                        return -1;
                    }
                    n5 = n + 1;
                }
                n = PatternProps.skipWhiteSpace(string, n5);
            }
            ++n4;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int parsePattern(String string, Replaceable replaceable, int n, int n2) {
        int n3 = 0;
        if (string.length() == 0) {
            return n;
        }
        int n4 = Character.codePointAt(string, 0);
        while (n < n2) {
            int n5 = replaceable.char32At(n);
            if (n4 == 126) {
                if (PatternProps.isWhiteSpace(n5)) {
                    n += UTF16.getCharCount(n5);
                    continue;
                }
                n3 = n4 = n3 + 1;
                n5 = n;
                if (n4 == string.length()) {
                    return n;
                }
            } else {
                if (n5 != n4) return -1;
                n4 = UTF16.getCharCount(n5);
                n5 = n + n4;
                if ((n3 += n4) == string.length()) {
                    return n5;
                }
            }
            n4 = UTF16.charAt(string, n3);
            n = n5;
        }
        return -1;
    }

    public static String parseUnicodeIdentifier(String string, int[] arrn) {
        int n;
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        for (n2 = arrn[0]; n2 < string.length(); n2 += UTF16.getCharCount((int)n)) {
            n = Character.codePointAt(string, n2);
            if (stringBuilder.length() == 0) {
                if (UCharacter.isUnicodeIdentifierStart(n)) {
                    stringBuilder.appendCodePoint(n);
                    continue;
                }
                return null;
            }
            if (!UCharacter.isUnicodeIdentifierPart(n)) break;
            stringBuilder.appendCodePoint(n);
        }
        arrn[0] = n2;
        return stringBuilder.toString();
    }

    public static int quotedIndexOf(String string, int n, int n2, String string2) {
        while (n < n2) {
            int n3;
            char c = string.charAt(n);
            if (c == '\\') {
                n3 = n + 1;
            } else if (c == '\'') {
                do {
                    n3 = ++n;
                    if (n < n2) {
                        n3 = n;
                        if (string.charAt(n) != '\'') {
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                n3 = n;
                if (string2.indexOf(c) >= 0) {
                    return n;
                }
            }
            n = n3 + 1;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static <T extends Appendable> void recursiveAppendNumber(T var0, int var1_2, int var2_3, int var3_4) {
        if (var1_2 < var2_3 && var3_4 <= 1) ** GOTO lbl4
        try {
            Utility.recursiveAppendNumber(var0, var1_2 / var2_3, var2_3, var3_4 - 1);
lbl4: // 2 sources:
            var0.append(Utility.DIGITS[var1_2 % var2_3]);
            return;
        }
        catch (IOException var0_1) {
            throw new IllegalIcuArgumentException(var0_1);
        }
    }

    public static String repeat(String string, int n) {
        if (n <= 0) {
            return "";
        }
        if (n == 1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static final boolean sameObjects(Object object, Object object2) {
        boolean bl = object == object2;
        return bl;
    }

    public static void split(String string, char c, String[] arrstring) {
        int n;
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < string.length(); ++n) {
            int n4 = n2;
            int n5 = n3;
            if (string.charAt(n) == c) {
                arrstring[n3] = string.substring(n2, n);
                n4 = n + 1;
                n5 = n3 + 1;
            }
            n2 = n4;
            n3 = n5;
        }
        arrstring[n3] = string.substring(n2, n);
        for (c = (char)(n3 + 1); c < arrstring.length; c = (char)(c + 1)) {
            arrstring[c] = "";
        }
    }

    public static String[] split(String string, char c) {
        int n;
        int n2 = 0;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n = 0; n < string.length(); ++n) {
            int n3 = n2;
            if (string.charAt(n) == c) {
                arrayList.add(string.substring(n2, n));
                n3 = n + 1;
            }
            n2 = n3;
        }
        arrayList.add(string.substring(n2, n));
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitString(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\\Q");
        stringBuilder.append(string2);
        stringBuilder.append("\\E");
        return string.split(stringBuilder.toString());
    }

    public static String[] splitWhitespace(String string) {
        return string.split("\\s+");
    }

    public static String unescape(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int[] arrn = new int[1];
        int n = 0;
        while (n < string.length()) {
            int n2 = n + 1;
            char c = string.charAt(n);
            if (c == '\\') {
                arrn[0] = n2;
                n = Utility.unescapeAt(string, arrn);
                if (n >= 0) {
                    stringBuilder.appendCodePoint(n);
                    n = arrn[0];
                    continue;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid escape sequence ");
                stringBuilder.append(string.substring(n2 - 1, Math.min(n2 + 8, string.length())));
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            stringBuilder.append(c);
            n = n2;
        }
        return stringBuilder.toString();
    }

    public static int unescapeAt(String string, int[] arrn) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 4;
        int n6 = 0;
        int n7 = arrn[0];
        int n8 = string.length();
        if (n7 >= 0 && n7 < n8) {
            int n9;
            char[] arrc;
            int n10 = Character.codePointAt(string, n7);
            int n11 = n7 + UTF16.getCharCount(n10);
            if (n10 != 85) {
                if (n10 != 117) {
                    if (n10 != 120) {
                        int n12 = UCharacter.digit(n10, 8);
                        n9 = n6;
                        n7 = n11;
                        if (n12 >= 0) {
                            n3 = 1;
                            n4 = 3;
                            n2 = 1;
                            n5 = 3;
                            n = n12;
                            n9 = n6;
                            n7 = n11;
                        }
                    } else {
                        n3 = 1;
                        if (n11 < n8 && UTF16.charAt(string, n11) == 123) {
                            n7 = n11 + 1;
                            n9 = 1;
                            n4 = 8;
                        } else {
                            n4 = 2;
                            n9 = n6;
                            n7 = n11;
                        }
                    }
                } else {
                    n4 = 4;
                    n3 = 4;
                    n9 = n6;
                    n7 = n11;
                }
            } else {
                n4 = 8;
                n3 = 8;
                n7 = n11;
                n9 = n6;
            }
            if (n3 != 0) {
                do {
                    n6 = n10;
                    if (n7 >= n8) break;
                    n6 = n10;
                    if (n2 >= n4) break;
                    n10 = UTF16.charAt(string, n7);
                    n6 = n5 == 3 ? 8 : 16;
                    if ((n6 = UCharacter.digit(n10, n6)) < 0) {
                        n6 = n10;
                        break;
                    }
                    n = n << n5 | n6;
                    n7 += UTF16.getCharCount(n10);
                    ++n2;
                } while (true);
                if (n2 < n3) {
                    return -1;
                }
                n4 = n7;
                if (n9 != 0) {
                    if (n6 != 125) {
                        return -1;
                    }
                    n4 = n7 + 1;
                }
                if (n >= 0 && n < 1114112) {
                    n10 = n;
                    n7 = n4;
                    if (n4 < n8) {
                        n10 = n;
                        n7 = n4;
                        if (UTF16.isLeadSurrogate((char)n)) {
                            n10 = n4 + 1;
                            n7 = string.charAt(n4);
                            n3 = n10;
                            n2 = n7;
                            if (n7 == 92) {
                                n3 = n10;
                                n2 = n7;
                                if (n10 < n8) {
                                    int[] arrn2 = new int[]{n10};
                                    n2 = Utility.unescapeAt(string, arrn2);
                                    n3 = arrn2[0];
                                }
                            }
                            n10 = n;
                            n7 = n4;
                            if (UTF16.isTrailSurrogate((char)n2)) {
                                n10 = Character.toCodePoint((char)n, (char)n2);
                                n7 = n3;
                            }
                        }
                    }
                    arrn[0] = n7;
                    return n10;
                }
                return -1;
            }
            for (n4 = 0; n4 < (arrc = UNESCAPE_MAP).length; n4 += 2) {
                if (n10 == arrc[n4]) {
                    arrn[0] = n7;
                    return arrc[n4 + 1];
                }
                if (n10 < arrc[n4]) break;
            }
            if (n10 == 99 && n7 < n8) {
                n4 = UTF16.charAt(string, n7);
                arrn[0] = UTF16.getCharCount(n4) + n7;
                return n4 & 31;
            }
            arrn[0] = n7;
            return n10;
        }
        return -1;
    }

    public static String unescapeLeniently(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int[] arrn = new int[1];
        int n = 0;
        while (n < string.length()) {
            int n2 = n + 1;
            char c = string.charAt(n);
            if (c == '\\') {
                arrn[0] = n2;
                n = Utility.unescapeAt(string, arrn);
                if (n < 0) {
                    stringBuilder.append(c);
                } else {
                    stringBuilder.appendCodePoint(n);
                    n2 = arrn[0];
                }
            } else {
                stringBuilder.append(c);
            }
            n = n2;
        }
        return stringBuilder.toString();
    }

    public static String valueOf(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder(arrn.length);
        for (int i = 0; i < arrn.length; ++i) {
            stringBuilder.appendCodePoint(arrn[i]);
        }
        return stringBuilder.toString();
    }
}

