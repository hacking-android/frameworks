/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.StringSegment;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.Grouper;
import android.icu.impl.number.parse.DecimalMatcher;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.impl.number.parse.ParsingUtils;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.UnicodeSet;

public class ScientificMatcher
implements NumberParseMatcher {
    private final String customMinusSign;
    private final String customPlusSign;
    private final DecimalMatcher exponentMatcher;
    private final String exponentSeparatorString;

    private ScientificMatcher(DecimalFormatSymbols object, Grouper object2) {
        this.exponentSeparatorString = ((DecimalFormatSymbols)object).getExponentSeparator();
        this.exponentMatcher = DecimalMatcher.getInstance((DecimalFormatSymbols)object, (Grouper)object2, 48);
        object2 = ((DecimalFormatSymbols)object).getMinusSignString();
        boolean bl = ParsingUtils.safeContains(ScientificMatcher.minusSignSet(), (CharSequence)object2);
        Object var4_4 = null;
        if (bl) {
            object2 = null;
        }
        this.customMinusSign = object2;
        object = ((DecimalFormatSymbols)object).getPlusSignString();
        if (ParsingUtils.safeContains(ScientificMatcher.plusSignSet(), (CharSequence)object)) {
            object = var4_4;
        }
        this.customPlusSign = object;
    }

    public static ScientificMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper) {
        return new ScientificMatcher(decimalFormatSymbols, grouper);
    }

    private static UnicodeSet minusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.MINUS_SIGN);
    }

    private static UnicodeSet plusSignSet() {
        return StaticUnicodeSets.get(StaticUnicodeSets.Key.PLUS_SIGN);
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        boolean bl = parsedNumber.seenNumber();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if ((parsedNumber.flags & 8) != 0) {
            return false;
        }
        int n = stringSegment.getCommonPrefixLength(this.exponentSeparatorString);
        if (n == this.exponentSeparatorString.length()) {
            if (stringSegment.length() == n) {
                return true;
            }
            stringSegment.adjustOffset(n);
            int n2 = 1;
            int n3 = 1;
            if (stringSegment.startsWith(ScientificMatcher.minusSignSet())) {
                n3 = -1;
                stringSegment.adjustOffsetByCodePoint();
            } else if (stringSegment.startsWith(ScientificMatcher.plusSignSet())) {
                stringSegment.adjustOffsetByCodePoint();
                n3 = n2;
            } else if (stringSegment.startsWith(this.customMinusSign)) {
                n2 = stringSegment.getCommonPrefixLength(this.customMinusSign);
                if (n2 != this.customMinusSign.length()) {
                    stringSegment.adjustOffset(-n);
                    return true;
                }
                n3 = -1;
                stringSegment.adjustOffset(n2);
            } else if (stringSegment.startsWith(this.customPlusSign)) {
                n3 = stringSegment.getCommonPrefixLength(this.customPlusSign);
                if (n3 != this.customPlusSign.length()) {
                    stringSegment.adjustOffset(-n);
                    return true;
                }
                stringSegment.adjustOffset(n3);
                n3 = n2;
            }
            if (parsedNumber.quantity == null) {
                bl2 = true;
            }
            if (bl2) {
                parsedNumber.quantity = new DecimalQuantity_DualStorageBCD();
            }
            n2 = stringSegment.getOffset();
            bl = this.exponentMatcher.match(stringSegment, parsedNumber, n3);
            if (bl2) {
                parsedNumber.quantity = null;
            }
            if (stringSegment.getOffset() != n2) {
                parsedNumber.flags |= 8;
            } else {
                stringSegment.adjustOffset(-n);
            }
            return bl;
        }
        return n == stringSegment.length();
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.exponentSeparatorString);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ScientificMatcher ");
        stringBuilder.append(this.exponentSeparatorString);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }
}

