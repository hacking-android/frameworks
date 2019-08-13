/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Utility;
import android.icu.text.Replaceable;
import android.icu.text.ReplaceableString;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeMatcher;

public class UtilityExtensions {
    public static void appendToRule(StringBuffer stringBuffer, UnicodeMatcher unicodeMatcher, boolean bl, StringBuffer stringBuffer2) {
        if (unicodeMatcher != null) {
            UtilityExtensions.appendToRule(stringBuffer, unicodeMatcher.toPattern(bl), true, bl, stringBuffer2);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, String string, boolean bl, boolean bl2, StringBuffer stringBuffer2) {
        for (int i = 0; i < string.length(); ++i) {
            Utility.appendToRule(stringBuffer, string.charAt(i), bl, bl2, stringBuffer2);
        }
    }

    public static String formatInput(Replaceable replaceable, Transliterator.Position position) {
        return UtilityExtensions.formatInput((ReplaceableString)replaceable, position);
    }

    public static String formatInput(ReplaceableString replaceableString, Transliterator.Position position) {
        StringBuffer stringBuffer = new StringBuffer();
        UtilityExtensions.formatInput(stringBuffer, replaceableString, position);
        return Utility.escape(stringBuffer.toString());
    }

    public static StringBuffer formatInput(StringBuffer stringBuffer, Replaceable replaceable, Transliterator.Position position) {
        return UtilityExtensions.formatInput(stringBuffer, (ReplaceableString)replaceable, position);
    }

    public static StringBuffer formatInput(StringBuffer stringBuffer, ReplaceableString object, Transliterator.Position position) {
        if (position.contextStart >= 0 && position.contextStart <= position.start && position.start <= position.limit && position.limit <= position.contextLimit && position.contextLimit <= ((ReplaceableString)object).length()) {
            String string = ((ReplaceableString)object).substring(position.contextStart, position.start);
            String string2 = ((ReplaceableString)object).substring(position.start, position.limit);
            object = ((ReplaceableString)object).substring(position.limit, position.contextLimit);
            stringBuffer.append('{');
            stringBuffer.append(string);
            stringBuffer.append('|');
            stringBuffer.append(string2);
            stringBuffer.append('|');
            stringBuffer.append((String)object);
            stringBuffer.append('}');
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INVALID Position {cs=");
            stringBuilder.append(position.contextStart);
            stringBuilder.append(", s=");
            stringBuilder.append(position.start);
            stringBuilder.append(", l=");
            stringBuilder.append(position.limit);
            stringBuilder.append(", cl=");
            stringBuilder.append(position.contextLimit);
            stringBuilder.append("} on ");
            stringBuilder.append(object);
            stringBuffer.append(stringBuilder.toString());
        }
        return stringBuffer;
    }
}

