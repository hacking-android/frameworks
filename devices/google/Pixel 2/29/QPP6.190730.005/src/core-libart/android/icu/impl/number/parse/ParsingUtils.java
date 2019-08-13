/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.text.UnicodeSet;
import java.util.Collection;
import java.util.Iterator;

public class ParsingUtils {
    public static final int PARSE_FLAG_EXACT_AFFIX = 512;
    public static final int PARSE_FLAG_FORCE_BIG_DECIMAL = 4096;
    public static final int PARSE_FLAG_GROUPING_DISABLED = 32;
    public static final int PARSE_FLAG_IGNORE_CASE = 1;
    public static final int PARSE_FLAG_INCLUDE_UNPAIRED_AFFIXES = 128;
    public static final int PARSE_FLAG_INTEGER_ONLY = 16;
    public static final int PARSE_FLAG_MONETARY_SEPARATORS = 2;
    public static final int PARSE_FLAG_NO_FOREIGN_CURRENCIES = 8192;
    public static final int PARSE_FLAG_PLUS_SIGN_ALLOWED = 1024;
    public static final int PARSE_FLAG_STRICT_GROUPING_SIZE = 8;
    public static final int PARSE_FLAG_STRICT_SEPARATORS = 4;
    public static final int PARSE_FLAG_USE_FULL_AFFIXES = 256;

    public static void putLeadCodePoint(String string, UnicodeSet unicodeSet) {
        if (!string.isEmpty()) {
            unicodeSet.add(string.codePointAt(0));
        }
    }

    public static void putLeadCodePoints(UnicodeSet object, UnicodeSet unicodeSet) {
        for (UnicodeSet.EntryRange entryRange : ((UnicodeSet)object).ranges()) {
            unicodeSet.add(entryRange.codepoint, entryRange.codepointEnd);
        }
        object = ((UnicodeSet)object).strings().iterator();
        while (object.hasNext()) {
            unicodeSet.add(((String)object.next()).codePointAt(0));
        }
    }

    public static boolean safeContains(UnicodeSet unicodeSet, CharSequence charSequence) {
        boolean bl = charSequence.length() != 0 && unicodeSet.contains(charSequence);
        return bl;
    }
}

