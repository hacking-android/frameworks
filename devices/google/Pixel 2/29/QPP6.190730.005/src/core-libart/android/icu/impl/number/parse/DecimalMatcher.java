/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.StaticUnicodeSets;
import android.icu.impl.StringSegment;
import android.icu.impl.number.DecimalQuantity_AbstractBCD;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.Grouper;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.ParsedNumber;
import android.icu.lang.UCharacter;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.UnicodeSet;

public class DecimalMatcher
implements NumberParseMatcher {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String decimalSeparator;
    private final UnicodeSet decimalUniSet;
    private final String[] digitStrings;
    private final int grouping1;
    private final int grouping2;
    private final boolean groupingDisabled;
    private final String groupingSeparator;
    private final UnicodeSet groupingUniSet;
    private final boolean integerOnly;
    private final UnicodeSet leadSet;
    private final boolean requireGroupingMatch;
    private final UnicodeSet separatorSet;

    private DecimalMatcher(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper, int n) {
        if ((n & 2) != 0) {
            this.groupingSeparator = decimalFormatSymbols.getMonetaryGroupingSeparatorString();
            this.decimalSeparator = decimalFormatSymbols.getMonetaryDecimalSeparatorString();
        } else {
            this.groupingSeparator = decimalFormatSymbols.getGroupingSeparatorString();
            this.decimalSeparator = decimalFormatSymbols.getDecimalSeparatorString();
        }
        boolean bl = true;
        int n2 = (n & 4) != 0 ? 1 : 0;
        StaticUnicodeSets.Key key = n2 != 0 ? StaticUnicodeSets.Key.STRICT_ALL_SEPARATORS : StaticUnicodeSets.Key.ALL_SEPARATORS;
        this.groupingUniSet = StaticUnicodeSets.get(key);
        String string = this.decimalSeparator;
        StaticUnicodeSets.Key key2 = n2 != 0 ? StaticUnicodeSets.Key.STRICT_COMMA : StaticUnicodeSets.Key.COMMA;
        StaticUnicodeSets.Key key3 = n2 != 0 ? StaticUnicodeSets.Key.STRICT_PERIOD : StaticUnicodeSets.Key.PERIOD;
        key2 = StaticUnicodeSets.chooseFrom(string, key2, key3);
        this.decimalUniSet = key2 != null ? StaticUnicodeSets.get(key2) : (!this.decimalSeparator.isEmpty() ? new UnicodeSet().add(this.decimalSeparator.codePointAt(0)).freeze() : UnicodeSet.EMPTY);
        if (key != null && key2 != null) {
            this.separatorSet = this.groupingUniSet;
            key = n2 != 0 ? StaticUnicodeSets.Key.DIGITS_OR_ALL_SEPARATORS : StaticUnicodeSets.Key.DIGITS_OR_STRICT_ALL_SEPARATORS;
            this.leadSet = StaticUnicodeSets.get(key);
        } else {
            this.separatorSet = new UnicodeSet().addAll(this.groupingUniSet).addAll(this.decimalUniSet).freeze();
            this.leadSet = null;
        }
        n2 = decimalFormatSymbols.getCodePointZero();
        this.digitStrings = n2 != -1 && UCharacter.isDigit(n2) && UCharacter.digit(n2) == 0 ? null : decimalFormatSymbols.getDigitStringsLocal();
        boolean bl2 = (n & 8) != 0;
        this.requireGroupingMatch = bl2;
        bl2 = (n & 32) != 0;
        this.groupingDisabled = bl2;
        bl2 = (n & 16) != 0 ? bl : false;
        this.integerOnly = bl2;
        this.grouping1 = grouper.getPrimary();
        this.grouping2 = grouper.getSecondary();
    }

    public static DecimalMatcher getInstance(DecimalFormatSymbols decimalFormatSymbols, Grouper grouper, int n) {
        return new DecimalMatcher(decimalFormatSymbols, grouper, n);
    }

    private boolean validateGroup(int n, int n2, boolean bl) {
        boolean bl2 = this.requireGroupingMatch;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        if (bl2) {
            if (n == -1) {
                return true;
            }
            if (n == 0) {
                if (bl) {
                    return true;
                }
                bl = bl6;
                if (n2 != 0) {
                    bl = bl6;
                    if (n2 <= this.grouping2) {
                        bl = true;
                    }
                }
                return bl;
            }
            if (n == 1) {
                if (bl) {
                    bl = bl3;
                    if (n2 == this.grouping1) {
                        bl = true;
                    }
                    return bl;
                }
                bl = bl4;
                if (n2 == this.grouping2) {
                    bl = true;
                }
                return bl;
            }
            return true;
        }
        if (n == 1) {
            bl = bl5;
            if (n2 != 1) {
                bl = true;
            }
            return bl;
        }
        return true;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        return this.match(stringSegment, parsedNumber, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber, int n) {
        boolean bl;
        int n2;
        int n3;
        int n4;
        Object object;
        int n5;
        int n6;
        Object object2;
        block74 : {
            block75 : {
                Object object3;
                int n7;
                boolean bl2;
                block73 : {
                    block69 : {
                        block71 : {
                            int n8;
                            int n9;
                            block72 : {
                                int n10;
                                int n11;
                                int n12;
                                block70 : {
                                    block64 : {
                                        if (parsedNumber.seenNumber() && n == 0) {
                                            return false;
                                        }
                                        if (n != 0) {
                                            // empty if block
                                        }
                                        n5 = stringSegment.getOffset();
                                        n2 = 0;
                                        object3 = null;
                                        n3 = 0;
                                        object2 = null;
                                        object = null;
                                        n7 = 0;
                                        n6 = 0;
                                        n9 = -1;
                                        n8 = 0;
                                        n10 = -1;
                                        n11 = -1;
                                        do {
                                            int n13;
                                            int n14;
                                            Object object4;
                                            int n15;
                                            int n16;
                                            block65 : {
                                                block68 : {
                                                    block66 : {
                                                        block67 : {
                                                            if (stringSegment.length() <= 0) break block66;
                                                            n15 = stringSegment.getCodePoint();
                                                            if (UCharacter.isDigit(n15)) {
                                                                stringSegment.adjustOffset(Character.charCount(n15));
                                                                n12 = (byte)UCharacter.digit(n15);
                                                            } else {
                                                                n12 = -1;
                                                            }
                                                            n2 = 0;
                                                            if (n12 != -1 || this.digitStrings == null) break block67;
                                                            break block68;
                                                        }
                                                        n2 = 0;
                                                        n13 = n12;
                                                        break block65;
                                                    }
                                                    n4 = n5;
                                                    object2 = object;
                                                    n12 = n2;
                                                    break block64;
                                                }
                                                for (n4 = 0; n4 < ((String[])(object4 = this.digitStrings)).length; ++n4) {
                                                    if (((String)(object4 = object4[n4])).isEmpty()) continue;
                                                    n16 = stringSegment.getCommonPrefixLength((CharSequence)object4);
                                                    n14 = n5;
                                                    if (n16 == ((String)object4).length()) {
                                                        stringSegment.adjustOffset(n16);
                                                        n13 = n5 = (int)((byte)n4);
                                                        n5 = n14;
                                                        break block65;
                                                    }
                                                    if (n2 == 0 && n16 != stringSegment.length()) {
                                                        n2 = 0;
                                                        continue;
                                                    }
                                                    n2 = 1;
                                                }
                                                n13 = n12;
                                            }
                                            if (n13 >= 0) {
                                                object4 = object3;
                                                if (object3 == null) {
                                                    object4 = new DecimalQuantity_DualStorageBCD();
                                                }
                                                ((DecimalQuantity_AbstractBCD)object4).appendDigit((byte)n13, 0, true);
                                                ++n6;
                                                if (object != null) {
                                                    ++n3;
                                                    object3 = object4;
                                                    continue;
                                                }
                                                object3 = object4;
                                                continue;
                                            }
                                            n16 = 0;
                                            if (object == null) {
                                                n12 = 0;
                                                if (!this.decimalSeparator.isEmpty()) {
                                                    n14 = stringSegment.getCommonPrefixLength(this.decimalSeparator);
                                                    n2 = n2 == 0 && n14 != stringSegment.length() ? 0 : 1;
                                                    n4 = n2;
                                                    object4 = object;
                                                    n2 = n4;
                                                    if (n14 == this.decimalSeparator.length()) {
                                                        object4 = this.decimalSeparator;
                                                        n12 = 1;
                                                        n2 = n4;
                                                    }
                                                } else {
                                                    object4 = object;
                                                }
                                            } else {
                                                n12 = 0;
                                                object4 = object;
                                            }
                                            n4 = n16;
                                            n14 = n2;
                                            if (object2 != null) {
                                                int n17 = stringSegment.getCommonPrefixLength((CharSequence)object2);
                                                n2 = n2 == 0 && n17 != stringSegment.length() ? 0 : 1;
                                                n4 = n16;
                                                n14 = n2;
                                                if (n17 == ((String)object2).length()) {
                                                    n4 = 1;
                                                    n14 = n2;
                                                }
                                            }
                                            if (!this.groupingDisabled && object2 == null && object4 == null && !this.groupingSeparator.isEmpty()) {
                                                n16 = stringSegment.getCommonPrefixLength(this.groupingSeparator);
                                                n2 = n14 == 0 && n16 != stringSegment.length() ? 0 : 1;
                                                if (n16 == this.groupingSeparator.length()) {
                                                    n4 = 1;
                                                    object2 = this.groupingSeparator;
                                                }
                                            } else {
                                                n2 = n14;
                                            }
                                            object = object4;
                                            n14 = n12;
                                            if (n4 == 0) {
                                                object = object4;
                                                n14 = n12;
                                                if (object4 == null) {
                                                    object = object4;
                                                    n14 = n12;
                                                    if (this.decimalUniSet.contains(n15)) {
                                                        n14 = 1;
                                                        object = UCharacter.toString(n15);
                                                    }
                                                }
                                            }
                                            n12 = n4;
                                            object4 = object2;
                                            if (!this.groupingDisabled) {
                                                n12 = n4;
                                                object4 = object2;
                                                if (object2 == null) {
                                                    n12 = n4;
                                                    object4 = object2;
                                                    if (object == null) {
                                                        n12 = n4;
                                                        object4 = object2;
                                                        if (this.groupingUniSet.contains(n15)) {
                                                            n12 = 1;
                                                            object4 = UCharacter.toString(n15);
                                                        }
                                                    }
                                                }
                                            }
                                            if (n14 == 0 && n12 == 0) {
                                                n12 = n2;
                                                object2 = object;
                                                n4 = n5;
                                                break block64;
                                            }
                                            if (n14 != 0 && this.integerOnly) {
                                                n12 = n2;
                                                object2 = object;
                                                n4 = n5;
                                                break block64;
                                            }
                                            if (n7 == 2 && n12 != 0) {
                                                n12 = n2;
                                                object2 = object;
                                                n4 = n5;
                                                break block64;
                                            }
                                            bl = this.validateGroup(n9, n10, false);
                                            bl2 = this.validateGroup(n7, n6, true);
                                            if (!bl || n14 != 0 && !bl2) break;
                                            if (this.requireGroupingMatch && n6 == 0 && n7 == 1) {
                                                n12 = n2;
                                                object2 = object;
                                                n4 = n5;
                                                break block64;
                                            }
                                            n11 = n8;
                                            n10 = n6;
                                            n9 = n14 != 0 ? -1 : n7;
                                            if (n6 != 0) {
                                                n8 = stringSegment.getOffset();
                                            }
                                            n6 = n12 != 0 ? 1 : 2;
                                            n4 = 0;
                                            if (n12 != 0) {
                                                stringSegment.adjustOffset(((String)object4).length());
                                            } else {
                                                stringSegment.adjustOffset(((String)object).length());
                                            }
                                            object2 = object4;
                                            n7 = n6;
                                            n6 = n4;
                                        } while (true);
                                        if (n12 != 0 && n6 == 0) {
                                            n12 = n2;
                                            object2 = object;
                                            n4 = n5;
                                        } else {
                                            n12 = n2;
                                            object2 = object;
                                            n4 = n5;
                                            if (this.requireGroupingMatch) {
                                                object3 = null;
                                                n12 = n2;
                                                object2 = object;
                                                n4 = n5;
                                            }
                                        }
                                    }
                                    if (n7 != 2 && n6 == 0) {
                                        n5 = 1;
                                        stringSegment.setOffset(n8);
                                        n7 = n9;
                                        n8 = n10;
                                        n6 = 0;
                                        n10 = 1;
                                        n2 = -1;
                                        n9 = n11;
                                        n11 = n6;
                                    } else {
                                        n2 = n11;
                                        n11 = n9;
                                        n9 = n8;
                                        n8 = n6;
                                        n5 = n12;
                                    }
                                    bl2 = this.validateGroup(n11, n10, false);
                                    bl = this.validateGroup(n7, n8, true);
                                    if (this.requireGroupingMatch) break block69;
                                    n12 = 0;
                                    if (bl2) break block70;
                                    stringSegment.setOffset(n2);
                                    n6 = 0 + n10 + n8;
                                    n2 = n5;
                                    break block71;
                                }
                                n2 = n5;
                                n6 = n12;
                                if (bl) break block71;
                                if (n11 != 0) break block72;
                                n2 = n5;
                                n6 = n12;
                                if (n10 == 0) break block71;
                            }
                            n2 = 1;
                            stringSegment.setOffset(n9);
                            n6 = 0 + n8;
                        }
                        if (n6 != 0) {
                            ((DecimalQuantity_AbstractBCD)object3).adjustMagnitude(-n6);
                            ((DecimalQuantity_AbstractBCD)object3).truncate();
                        }
                        bl2 = true;
                        bl = true;
                        break block73;
                    }
                    n2 = n5;
                }
                object = object3;
                if (n7 == 2) break block74;
                if (!bl2) break block75;
                object = object3;
                if (bl) break block74;
            }
            object = null;
        }
        if (object == null) {
            bl = n2 != 0 || stringSegment.length() == 0;
            stringSegment.setOffset(n4);
            return bl;
        }
        ((DecimalQuantity_AbstractBCD)object).adjustMagnitude(-n3);
        if (n != 0 && stringSegment.getOffset() != n4) {
            n5 = 0;
            if (((DecimalQuantity_AbstractBCD)object).fitsInLong()) {
                long l = ((DecimalQuantity_AbstractBCD)object).toLong(false);
                if (l <= Integer.MAX_VALUE) {
                    n6 = (int)l;
                    try {
                        parsedNumber.quantity.adjustMagnitude(n * n6);
                    }
                    catch (ArithmeticException arithmeticException) {
                        n5 = 1;
                    }
                } else {
                    n5 = 1;
                }
            } else {
                n5 = 1;
            }
            object = parsedNumber;
            if (n5 != 0) {
                if (n == -1) {
                    ((ParsedNumber)object).quantity.clear();
                } else {
                    ((ParsedNumber)object).quantity = null;
                    ((ParsedNumber)object).flags |= 128;
                }
            }
        } else {
            parsedNumber.quantity = object;
        }
        if (object2 != null) {
            parsedNumber.flags |= 32;
        }
        parsedNumber.setCharsConsumed(stringSegment);
        if (stringSegment.length() == 0) return true;
        if (n2 != 0) return true;
        return false;
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        String[] arrstring;
        if (this.digitStrings == null && (arrstring = this.leadSet) != null) {
            return stringSegment.startsWith((UnicodeSet)arrstring);
        }
        if (!stringSegment.startsWith(this.separatorSet) && !UCharacter.isDigit(stringSegment.getCodePoint())) {
            if (this.digitStrings == null) {
                return false;
            }
            for (int i = 0; i < (arrstring = this.digitStrings).length; ++i) {
                if (!stringSegment.startsWith(arrstring[i])) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public String toString() {
        return "<DecimalMatcher>";
    }
}

