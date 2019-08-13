/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Punycode;
import android.icu.text.StringPrep;
import android.icu.text.StringPrepParseException;
import android.icu.text.UCharacterIterator;

public final class IDNA2003 {
    private static char[] ACE_PREFIX = new char[]{'x', 'n', '-', '-'};
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    private static final int FULL_STOP = 46;
    private static final int HYPHEN = 45;
    private static final int LOWER_CASE_DELTA = 32;
    private static final int MAX_DOMAIN_NAME_LENGTH = 255;
    private static final int MAX_LABEL_LENGTH = 63;
    private static final StringPrep namePrep = StringPrep.getInstance(0);

    public static int compare(String string, String string2, int n) throws StringPrepParseException {
        return IDNA2003.compareCaseInsensitiveASCII(IDNA2003.convertIDNToASCII(string, n), IDNA2003.convertIDNToASCII(string2, n));
    }

    private static int compareCaseInsensitiveASCII(StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        int n = 0;
        while (n != stringBuffer.length()) {
            char c;
            int n2;
            char c2 = stringBuffer.charAt(n);
            if (c2 != (c = stringBuffer2.charAt(n)) && (n2 = IDNA2003.toASCIILower(c2) - IDNA2003.toASCIILower(c)) != 0) {
                return n2;
            }
            ++n;
        }
        return 0;
    }

    public static StringBuffer convertIDNToASCII(String string, int n) throws StringPrepParseException {
        char[] arrc = string.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        int n3 = 0;
        do {
            if ((string = new String(arrc, n3, (n2 = IDNA2003.getSeparatorIndex(arrc, n2, arrc.length)) - n3)).length() != 0 || n2 != arrc.length) {
                stringBuffer.append(IDNA2003.convertToASCII(UCharacterIterator.getInstance(string), n));
            }
            if (n2 == arrc.length) {
                if (stringBuffer.length() <= 255) {
                    return stringBuffer;
                }
                throw new StringPrepParseException("The output exceed the max allowed length.", 11);
            }
            n3 = ++n2;
            stringBuffer.append('.');
        } while (true);
    }

    public static StringBuffer convertIDNToUnicode(String charSequence, int n) throws StringPrepParseException {
        char[] arrc = ((String)charSequence).toCharArray();
        charSequence = new StringBuffer();
        int n2 = 0;
        int n3 = 0;
        do {
            String string;
            if ((string = new String(arrc, n3, (n2 = IDNA2003.getSeparatorIndex(arrc, n2, arrc.length)) - n3)).length() == 0 && n2 != arrc.length) {
                throw new StringPrepParseException("Found zero length lable after NamePrep.", 10);
            }
            ((StringBuffer)charSequence).append(IDNA2003.convertToUnicode(UCharacterIterator.getInstance(string), n));
            if (n2 == arrc.length) {
                if (((StringBuffer)charSequence).length() <= 255) {
                    return charSequence;
                }
                throw new StringPrepParseException("The output exceed the max allowed length.", 11);
            }
            ((StringBuffer)charSequence).append(arrc[n2]);
            n3 = ++n2;
        } while (true);
    }

    public static StringBuffer convertToASCII(UCharacterIterator object, int n) throws StringPrepParseException {
        block13 : {
            block15 : {
                block14 : {
                    int n2;
                    int n3 = 1;
                    boolean bl = true;
                    boolean bl2 = (n & 2) != 0;
                    while ((n2 = ((UCharacterIterator)object).next()) != -1) {
                        if (n2 <= 127) continue;
                        n3 = 0;
                    }
                    int n4 = -1;
                    ((UCharacterIterator)object).setToStart();
                    object = n3 == 0 ? namePrep.prepare((UCharacterIterator)object, n) : new StringBuffer(((UCharacterIterator)object).getText());
                    int n5 = ((StringBuffer)object).length();
                    if (n5 == 0) break block13;
                    StringBuffer stringBuffer = new StringBuffer();
                    n2 = 1;
                    n3 = n4;
                    for (n = 0; n < n5; ++n) {
                        char c = ((StringBuffer)object).charAt(n);
                        if (c > '') {
                            n4 = 0;
                        } else {
                            n4 = n2;
                            if (!IDNA2003.isLDHChar(c)) {
                                bl = false;
                                n3 = n;
                                n4 = n2;
                            }
                        }
                        n2 = n4;
                    }
                    if (bl2 && (!bl || ((StringBuffer)object).charAt(0) == '-' || ((StringBuffer)object).charAt(((StringBuffer)object).length() - 1) == '-')) {
                        if (!bl) {
                            object = ((StringBuffer)object).toString();
                            if (n3 > 0) {
                                --n3;
                            }
                            throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, (String)object, n3);
                        }
                        if (((StringBuffer)object).charAt(0) != '-') {
                            object = ((StringBuffer)object).toString();
                            n = n5 > 0 ? n5 - 1 : n5;
                            throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, (String)object, n);
                        }
                        throw new StringPrepParseException("The input does not conform to the STD 3 ASCII rules", 5, ((StringBuffer)object).toString(), 0);
                    }
                    if (n2 != 0) break block14;
                    if (IDNA2003.startsWithPrefix((StringBuffer)object)) break block15;
                    object = IDNA2003.toASCIILower(Punycode.encode((CharSequence)object, new boolean[n5]));
                    char[] arrc = ACE_PREFIX;
                    stringBuffer.append(arrc, 0, arrc.length);
                    stringBuffer.append((StringBuffer)object);
                    object = stringBuffer;
                }
                if (((StringBuffer)object).length() <= 63) {
                    return object;
                }
                throw new StringPrepParseException("The labels in the input are too long. Length > 63.", 8, ((StringBuffer)object).toString(), 0);
            }
            throw new StringPrepParseException("The input does not start with the ACE Prefix.", 6, ((StringBuffer)object).toString(), 0);
        }
        throw new StringPrepParseException("Found zero length lable after NamePrep.", 10);
    }

    public static StringBuffer convertToUnicode(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        StringBuffer stringBuffer;
        int n2;
        boolean bl = true;
        int n3 = uCharacterIterator.getIndex();
        while ((n2 = uCharacterIterator.next()) != -1) {
            if (n2 <= 127) continue;
            bl = false;
        }
        if (!bl) {
            try {
                uCharacterIterator.setIndex(n3);
                stringBuffer = namePrep.prepare(uCharacterIterator, n);
            }
            catch (StringPrepParseException stringPrepParseException) {
                return new StringBuffer(uCharacterIterator.getText());
            }
        } else {
            stringBuffer = new StringBuffer(uCharacterIterator.getText());
        }
        if (IDNA2003.startsWithPrefix(stringBuffer)) {
            StringBuffer stringBuffer2;
            CharSequence charSequence = stringBuffer.substring(ACE_PREFIX.length, stringBuffer.length());
            try {
                stringBuffer2 = new StringBuffer(Punycode.decode(charSequence, null));
            }
            catch (StringPrepParseException stringPrepParseException) {
                stringBuffer2 = null;
            }
            charSequence = stringBuffer2;
            if (stringBuffer2 != null) {
                charSequence = stringBuffer2;
                if (IDNA2003.compareCaseInsensitiveASCII(stringBuffer, IDNA2003.convertToASCII(UCharacterIterator.getInstance(stringBuffer2), n)) != 0) {
                    charSequence = null;
                }
            }
            if (charSequence != null) {
                return charSequence;
            }
        }
        return new StringBuffer(uCharacterIterator.getText());
    }

    private static int getSeparatorIndex(char[] arrc, int n, int n2) {
        while (n < n2) {
            if (IDNA2003.isLabelSeparator(arrc[n])) {
                return n;
            }
            ++n;
        }
        return n;
    }

    private static boolean isLDHChar(int n) {
        if (n > 122) {
            return false;
        }
        return n == 45 || 48 <= n && n <= 57 || 65 <= n && n <= 90 || 97 <= n && n <= 122;
        {
        }
    }

    private static boolean isLabelSeparator(int n) {
        return n == 46 || n == 12290 || n == 65294 || n == 65377;
    }

    private static boolean startsWithPrefix(StringBuffer stringBuffer) {
        boolean bl = true;
        if (stringBuffer.length() < ACE_PREFIX.length) {
            return false;
        }
        for (int i = 0; i < ACE_PREFIX.length; ++i) {
            if (IDNA2003.toASCIILower(stringBuffer.charAt(i)) == ACE_PREFIX[i]) continue;
            bl = false;
        }
        return bl;
    }

    private static char toASCIILower(char c) {
        if ('A' <= c && c <= 'Z') {
            return (char)(c + 32);
        }
        return c;
    }

    private static StringBuffer toASCIILower(CharSequence charSequence) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < charSequence.length(); ++i) {
            stringBuffer.append(IDNA2003.toASCIILower(charSequence.charAt(i)));
        }
        return stringBuffer;
    }
}

