/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.IDNA
 *  android.icu.text.StringPrepParseException
 */
package java.net;

import android.icu.text.IDNA;
import android.icu.text.StringPrepParseException;

public final class IDN {
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int USE_STD3_ASCII_RULES = 2;

    private IDN() {
    }

    private static StringBuffer convertFullStop(StringBuffer stringBuffer) {
        for (int i = 0; i < stringBuffer.length(); ++i) {
            if (!IDN.isLabelSeperator(stringBuffer.charAt(i))) continue;
            stringBuffer.setCharAt(i, '.');
        }
        return stringBuffer;
    }

    private static boolean isLabelSeperator(char c) {
        boolean bl = c == '\u3002' || c == '\uff0e' || c == '\uff61';
        return bl;
    }

    public static String toASCII(String string) {
        return IDN.toASCII(string, 0);
    }

    public static String toASCII(String string, int n) {
        try {
            String string2 = IDNA.convertIDNToASCII((String)string, (int)n).toString();
            return string2;
        }
        catch (StringPrepParseException stringPrepParseException) {
            if (".".equals(string)) {
                return string;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid input to toASCII: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString(), stringPrepParseException);
        }
    }

    public static String toUnicode(String string) {
        return IDN.toUnicode(string, 0);
    }

    public static String toUnicode(String string, int n) {
        try {
            String string2 = IDN.convertFullStop(IDNA.convertIDNToUnicode((String)string, (int)n)).toString();
            return string2;
        }
        catch (StringPrepParseException stringPrepParseException) {
            return string;
        }
    }
}

