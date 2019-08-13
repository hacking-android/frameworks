/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PatternProps;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;
import android.icu.text.PluralFormat;
import android.icu.text.PluralRules;
import android.icu.text.RbnfLenientScanner;
import android.icu.text.RuleBasedNumberFormat;
import java.io.Serializable;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.List;
import java.util.Objects;

final class NFRule {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IMPROPER_FRACTION_RULE = -2;
    static final int INFINITY_RULE = -5;
    static final int MASTER_RULE = -4;
    static final int NAN_RULE = -6;
    static final int NEGATIVE_NUMBER_RULE = -1;
    static final int PROPER_FRACTION_RULE = -3;
    private static final String[] RULE_PREFIXES;
    static final Long ZERO;
    private long baseValue;
    private char decimalPoint = (char)(false ? 1 : 0);
    private short exponent = (short)(false ? 1 : 0);
    private final RuleBasedNumberFormat formatter;
    private int radix = 10;
    private PluralFormat rulePatternFormat;
    private String ruleText;
    private NFSubstitution sub1;
    private NFSubstitution sub2;

    static {
        ZERO = 0L;
        RULE_PREFIXES = new String[]{"<<", "<%", "<#", "<0", ">>", ">%", ">#", ">0", "=%", "=#", "=0"};
    }

    public NFRule(RuleBasedNumberFormat object, String string) {
        Object var3_3 = null;
        this.ruleText = null;
        this.rulePatternFormat = null;
        this.sub1 = null;
        this.sub2 = null;
        this.formatter = object;
        object = string == null ? var3_3 : this.parseRuleDescriptor(string);
        this.ruleText = object;
    }

    private boolean allIgnorable(String string) {
        boolean bl = true;
        if (string != null && string.length() != 0) {
            RbnfLenientScanner rbnfLenientScanner = this.formatter.getLenientScanner();
            if (rbnfLenientScanner == null || !rbnfLenientScanner.allIgnorable(string)) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    private short expectedExponent() {
        long l;
        if (this.radix != 0 && (l = this.baseValue) >= 1L) {
            short s = (short)(Math.log(l) / Math.log(this.radix));
            if (NFRule.power(this.radix, (short)(s + 1)) <= this.baseValue) {
                return (short)(s + 1);
            }
            return s;
        }
        return 0;
    }

    private NFSubstitution extractSubstitution(NFRuleSet object, NFRule object2) {
        int n;
        int n2 = NFRule.indexOfAnyRulePrefix(this.ruleText);
        if (n2 == -1) {
            return null;
        }
        if (this.ruleText.startsWith(">>>", n2)) {
            n = n2 + 2;
        } else {
            char c = this.ruleText.charAt(n2);
            n = this.ruleText.indexOf(c, n2 + 1);
            if (c == '<' && n != -1 && n < this.ruleText.length() - 1 && this.ruleText.charAt(n + 1) == c) {
                ++n;
            }
        }
        if (n == -1) {
            return null;
        }
        object = NFSubstitution.makeSubstitution(n2, this, (NFRule)object2, (NFRuleSet)object, this.formatter, this.ruleText.substring(n2, n + 1));
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(this.ruleText.substring(0, n2));
        ((StringBuilder)object2).append(this.ruleText.substring(n + 1));
        this.ruleText = ((StringBuilder)object2).toString();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void extractSubstitutions(NFRuleSet object, String charSequence, NFRule nFRule) {
        this.ruleText = charSequence;
        this.sub1 = this.extractSubstitution((NFRuleSet)object, nFRule);
        this.sub2 = this.sub1 == null ? null : this.extractSubstitution((NFRuleSet)object, nFRule);
        charSequence = this.ruleText;
        int n = ((String)charSequence).indexOf("$(");
        int n2 = n >= 0 ? ((String)charSequence).indexOf(")$", n) : -1;
        if (n2 < 0) return;
        int n3 = ((String)charSequence).indexOf(44, n);
        if (n3 < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Rule \"");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("\" does not have a defined type");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = this.ruleText.substring(n + 2, n3);
        if ("cardinal".equals(object)) {
            object = PluralRules.PluralType.CARDINAL;
        } else {
            if (!"ordinal".equals(object)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" is an unknown type");
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            object = PluralRules.PluralType.ORDINAL;
        }
        this.rulePatternFormat = this.formatter.createPluralFormat((PluralRules.PluralType)((Object)object), ((String)charSequence).substring(n3 + 1, n2));
    }

    private int[] findText(String string, String object, PluralFormat object2, int n) {
        RbnfLenientScanner rbnfLenientScanner = this.formatter.getLenientScanner();
        if (object2 != null) {
            object = new FieldPosition(0);
            ((FieldPosition)object).setBeginIndex(n);
            ((PluralFormat)object2).parseType(string, rbnfLenientScanner, (FieldPosition)object);
            n = ((FieldPosition)object).getBeginIndex();
            if (n >= 0) {
                int n2 = this.ruleText.indexOf("$(");
                int n3 = this.ruleText.indexOf(")$", n2);
                int n4 = ((FieldPosition)object).getEndIndex() - n;
                object2 = this.ruleText.substring(0, n2);
                object = this.ruleText.substring(n3 + 2);
                if (string.regionMatches(n - ((String)object2).length(), (String)object2, 0, ((String)object2).length()) && string.regionMatches(n + n4, (String)object, 0, ((String)object).length())) {
                    return new int[]{n - ((String)object2).length(), ((String)object2).length() + n4 + ((String)object).length()};
                }
            }
            return new int[]{-1, 0};
        }
        if (rbnfLenientScanner != null) {
            return rbnfLenientScanner.findText(string, (String)object, n);
        }
        return new int[]{string.indexOf((String)object, n), ((String)object).length()};
    }

    private static int indexOfAnyRulePrefix(String string) {
        int n;
        block3 : {
            int n2;
            n = n2 = -1;
            if (string.length() <= 0) break block3;
            String[] arrstring = RULE_PREFIXES;
            int n3 = arrstring.length;
            int n4 = 0;
            do {
                block4 : {
                    int n5;
                    block5 : {
                        n = n2;
                        if (n4 >= n3) break;
                        n5 = string.indexOf(arrstring[n4]);
                        n = n2;
                        if (n5 == -1) break block4;
                        if (n2 == -1) break block5;
                        n = n2;
                        if (n5 >= n2) break block4;
                    }
                    n = n5;
                }
                ++n4;
                n2 = n;
            } while (true);
        }
        return n;
    }

    public static void makeRules(String object, NFRuleSet nFRuleSet, NFRule nFRule, RuleBasedNumberFormat ruleBasedNumberFormat, List<NFRule> list) {
        long l;
        NFRule nFRule2 = new NFRule(ruleBasedNumberFormat, (String)object);
        String string = nFRule2.ruleText;
        int n = string.indexOf(91);
        int n2 = n < 0 ? -1 : string.indexOf(93);
        if (n2 >= 0 && n <= n2 && (l = nFRule2.baseValue) != -3L && l != -1L && l != -5L && l != -6L) {
            object = null;
            StringBuilder stringBuilder = new StringBuilder();
            l = nFRule2.baseValue;
            if (l > 0L && l % NFRule.power(nFRule2.radix, nFRule2.exponent) == 0L || (l = nFRule2.baseValue) == -2L || l == -4L) {
                object = new NFRule(ruleBasedNumberFormat, null);
                if ((l = nFRule2.baseValue++) >= 0L) {
                    ((NFRule)object).baseValue = l;
                    if (!nFRuleSet.isFractionSet()) {
                        // empty if block
                    }
                } else if (l == -2L) {
                    ((NFRule)object).baseValue = -3L;
                } else if (l == -4L) {
                    ((NFRule)object).baseValue = l;
                    nFRule2.baseValue = -2L;
                }
                ((NFRule)object).radix = nFRule2.radix;
                ((NFRule)object).exponent = nFRule2.exponent;
                stringBuilder.append(string.substring(0, n));
                if (n2 + 1 < string.length()) {
                    stringBuilder.append(string.substring(n2 + 1));
                }
                NFRule.super.extractSubstitutions(nFRuleSet, stringBuilder.toString(), nFRule);
            }
            stringBuilder.setLength(0);
            stringBuilder.append(string.substring(0, n));
            stringBuilder.append(string.substring(n + 1, n2));
            if (n2 + 1 < string.length()) {
                stringBuilder.append(string.substring(n2 + 1));
            }
            nFRule2.extractSubstitutions(nFRuleSet, stringBuilder.toString(), nFRule);
            if (object != null) {
                if (((NFRule)object).baseValue >= 0L) {
                    list.add((NFRule)object);
                } else {
                    nFRuleSet.setNonNumericalRule((NFRule)object);
                }
            }
        } else {
            nFRule2.extractSubstitutions(nFRuleSet, string, nFRule);
        }
        if (nFRule2.baseValue >= 0L) {
            list.add(nFRule2);
        } else {
            nFRuleSet.setNonNumericalRule(nFRule2);
        }
    }

    private Number matchToDelimiter(String object, int object2, double d, String object3, PluralFormat serializable, ParsePosition parsePosition, NFSubstitution nFSubstitution, double d2, int n) {
        if (!this.allIgnorable((String)object3)) {
            ParsePosition parsePosition2 = new ParsePosition(0);
            Object object4 = this.findText((String)object, (String)object3, (PluralFormat)serializable, (int)object2);
            Object object5 = object4[0];
            object2 = object4[1];
            while (object5 >= 0) {
                object4 = ((String)object).substring(0, (int)object5);
                if (((String)object4).length() > 0) {
                    object4 = nFSubstitution.doParse((String)object4, parsePosition2, d, d2, this.formatter.lenientParseEnabled(), n);
                    if (parsePosition2.getIndex() == object5) {
                        parsePosition.setIndex(object5 + object2);
                        return object4;
                    }
                }
                parsePosition2.setIndex(0);
                object4 = this.findText((String)object, (String)object3, (PluralFormat)serializable, object5 + object2);
                object5 = object4[0];
                object2 = object4[1];
            }
            parsePosition.setIndex(0);
            return ZERO;
        }
        if (nFSubstitution == null) {
            return d;
        }
        ParsePosition parsePosition3 = new ParsePosition(0);
        object3 = ZERO;
        serializable = nFSubstitution.doParse((String)object, parsePosition3, d, d2, this.formatter.lenientParseEnabled(), n);
        object = object3;
        if (parsePosition3.getIndex() != 0) {
            parsePosition.setIndex(parsePosition3.getIndex());
            object = object3;
            if (serializable != null) {
                object = serializable;
            }
        }
        return object;
    }

    private String parseRuleDescriptor(String charSequence) {
        String string = charSequence;
        int n = string.indexOf(":");
        String string2 = string;
        if (n != -1) {
            String string3 = string.substring(0, n);
            ++n;
            while (n < ((String)charSequence).length() && PatternProps.isWhiteSpace(string.charAt(n))) {
                ++n;
            }
            charSequence = string.substring(n);
            int n2 = string3.length();
            n = string3.charAt(0);
            short s = string3.charAt(n2 - 1);
            if (n >= 48 && n <= 57 && s != 120) {
                int n3;
                short s2;
                long l = 0L;
                s = 0;
                for (n3 = 0; n3 < n2; ++n3) {
                    long l2;
                    s2 = string3.charAt(n3);
                    if (s2 >= 48 && s2 <= 57) {
                        l2 = 10L * l + (long)(s2 - 48);
                    } else {
                        s = s2;
                        if (s2 == 47) break;
                        if (s2 == 62) {
                            s = s2;
                            break;
                        }
                        l2 = l;
                        if (!PatternProps.isWhiteSpace(s2)) {
                            l2 = l;
                            if (s2 != 44) {
                                if (s2 == 46) {
                                    l2 = l;
                                } else {
                                    charSequence = new StringBuilder();
                                    ((StringBuilder)charSequence).append("Illegal character ");
                                    ((StringBuilder)charSequence).append((char)s2);
                                    ((StringBuilder)charSequence).append(" in rule descriptor");
                                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                                }
                            }
                        }
                    }
                    s = s2;
                    l = l2;
                }
                this.setBaseValue(l);
                n = n3;
                short s3 = s;
                if (s == 47) {
                    l = 0L;
                    for (n = n3 + 1; n < n2; ++n) {
                        s2 = string3.charAt(n);
                        if (s2 >= 48 && s2 <= 57) {
                            l = l * 10L + (long)(s2 - 48);
                        } else {
                            if (s2 == 62) {
                                s = s2;
                                break;
                            }
                            if (!PatternProps.isWhiteSpace(s2) && s2 != 44 && s2 != 46) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("Illegal character ");
                                ((StringBuilder)charSequence).append((char)s2);
                                ((StringBuilder)charSequence).append(" in rule descriptor");
                                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                            }
                        }
                        s = s2;
                    }
                    this.radix = (int)l;
                    if (this.radix != 0) {
                        this.exponent = this.expectedExponent();
                        s3 = s;
                    } else {
                        throw new IllegalArgumentException("Rule can't have radix of 0");
                    }
                }
                if (s3 == 62) {
                    while (n < n2) {
                        if (string3.charAt(n) == '>' && (s = this.exponent) > 0) {
                            this.exponent = (short)(s - 1);
                            ++n;
                            continue;
                        }
                        throw new IllegalArgumentException("Illegal character in rule descriptor");
                    }
                }
                string2 = charSequence;
            } else if (string3.equals("-x")) {
                this.setBaseValue(-1L);
                string2 = charSequence;
            } else {
                string2 = charSequence;
                if (n2 == 3) {
                    if (n == 48 && s == 120) {
                        this.setBaseValue(-3L);
                        this.decimalPoint = string3.charAt(1);
                        string2 = charSequence;
                    } else if (n == 120 && s == 120) {
                        this.setBaseValue(-2L);
                        this.decimalPoint = string3.charAt(1);
                        string2 = charSequence;
                    } else if (n == 120 && s == 48) {
                        this.setBaseValue(-4L);
                        this.decimalPoint = string3.charAt(1);
                        string2 = charSequence;
                    } else if (string3.equals("NaN")) {
                        this.setBaseValue(-6L);
                        string2 = charSequence;
                    } else {
                        string2 = charSequence;
                        if (string3.equals("Inf")) {
                            this.setBaseValue(-5L);
                            string2 = charSequence;
                        }
                    }
                }
            }
        }
        charSequence = string2;
        if (string2.length() > 0) {
            charSequence = string2;
            if (string2.charAt(0) == '\'') {
                charSequence = string2.substring(1);
            }
        }
        return charSequence;
    }

    static long power(long l, short s) {
        if (s >= 0) {
            if (l >= 0L) {
                long l2 = 1L;
                while (s > 0) {
                    long l3 = l2;
                    if ((s & 1) == 1) {
                        l3 = l2 * l;
                    }
                    l *= l;
                    s = (short)(s >> 1);
                    l2 = l3;
                }
                return l2;
            }
            throw new IllegalArgumentException("Base can not be negative");
        }
        throw new IllegalArgumentException("Exponent can not be negative");
    }

    private int prefixLength(String string, String string2) {
        if (string2.length() == 0) {
            return 0;
        }
        RbnfLenientScanner rbnfLenientScanner = this.formatter.getLenientScanner();
        if (rbnfLenientScanner != null) {
            return rbnfLenientScanner.prefixLength(string, string2);
        }
        if (string.startsWith(string2)) {
            return string2.length();
        }
        return 0;
    }

    private String stripPrefix(String string, String string2, ParsePosition parsePosition) {
        if (string2.length() == 0) {
            return string;
        }
        int n = this.prefixLength(string, string2);
        if (n != 0) {
            parsePosition.setIndex(parsePosition.getIndex() + n);
            return string.substring(n);
        }
        return string;
    }

    public void doFormat(double d, StringBuilder stringBuilder, int n, int n2) {
        int n3;
        int n4;
        int n5 = this.ruleText.length();
        Object object = this.rulePatternFormat;
        int n6 = 0;
        if (object == null) {
            stringBuilder.insert(n, this.ruleText);
            n4 = 0;
        } else {
            n5 = this.ruleText.indexOf("$(");
            n3 = this.ruleText.indexOf(")$", n5);
            n4 = stringBuilder.length();
            if (n3 < this.ruleText.length() - 1) {
                stringBuilder.insert(n, this.ruleText.substring(n3 + 2));
            }
            double d2 = 0.0 <= d && d < 1.0 ? (double)Math.round((double)NFRule.power(this.radix, this.exponent) * d) : d / (double)NFRule.power(this.radix, this.exponent);
            stringBuilder.insert(n, this.rulePatternFormat.format((long)d2));
            if (n5 > 0) {
                stringBuilder.insert(n, this.ruleText.substring(0, n5));
            }
            n3 = this.ruleText.length();
            int n7 = stringBuilder.length();
            n4 = n3 - (n7 - n4);
        }
        object = this.sub2;
        if (object != null) {
            n3 = ((NFSubstitution)object).getPos() > n5 ? n4 : 0;
            ((NFSubstitution)object).doSubstitution(d, stringBuilder, n - n3, n2);
        }
        if ((object = this.sub1) != null) {
            n3 = n6;
            if (((NFSubstitution)object).getPos() > n5) {
                n3 = n4;
            }
            ((NFSubstitution)object).doSubstitution(d, stringBuilder, n - n3, n2);
        }
    }

    public void doFormat(long l, StringBuilder stringBuilder, int n, int n2) {
        int n3;
        int n4;
        int n5 = this.ruleText.length();
        Object object = this.rulePatternFormat;
        int n6 = 0;
        if (object == null) {
            stringBuilder.insert(n, this.ruleText);
            n4 = 0;
        } else {
            n5 = this.ruleText.indexOf("$(");
            n3 = this.ruleText.indexOf(")$", n5);
            n4 = stringBuilder.length();
            if (n3 < this.ruleText.length() - 1) {
                stringBuilder.insert(n, this.ruleText.substring(n3 + 2));
            }
            stringBuilder.insert(n, this.rulePatternFormat.format(l / NFRule.power(this.radix, this.exponent)));
            if (n5 > 0) {
                stringBuilder.insert(n, this.ruleText.substring(0, n5));
            }
            n3 = this.ruleText.length();
            int n7 = stringBuilder.length();
            n4 = n3 - (n7 - n4);
        }
        object = this.sub2;
        if (object != null) {
            n3 = ((NFSubstitution)object).getPos() > n5 ? n4 : 0;
            ((NFSubstitution)object).doSubstitution(l, stringBuilder, n - n3, n2);
        }
        if ((object = this.sub1) != null) {
            n3 = n6;
            if (((NFSubstitution)object).getPos() > n5) {
                n3 = n4;
            }
            ((NFSubstitution)object).doSubstitution(l, stringBuilder, n - n3, n2);
        }
    }

    public Number doParse(String object, ParsePosition parsePosition, boolean bl, double d, int n) {
        int n2 = 0;
        ParsePosition parsePosition2 = new ParsePosition(0);
        Object object2 = this.sub1;
        int n3 = object2 != null ? ((NFSubstitution)object2).getPos() : this.ruleText.length();
        int n4 = n3;
        object2 = this.sub2;
        n3 = object2 != null ? ((NFSubstitution)object2).getPos() : this.ruleText.length();
        object2 = this.stripPrefix((String)object, this.ruleText.substring(0, n4), parsePosition2);
        int n5 = ((String)object).length() - ((String)object2).length();
        if (parsePosition2.getIndex() == 0 && n4 != 0) {
            return ZERO;
        }
        long l = this.baseValue;
        if (l == -5L) {
            parsePosition.setIndex(parsePosition2.getIndex());
            return Double.POSITIVE_INFINITY;
        }
        if (l == -6L) {
            parsePosition.setIndex(parsePosition2.getIndex());
            return Double.NaN;
        }
        double d2 = Math.max(0L, l);
        double d3 = 0.0;
        int n6 = 0;
        int n7 = 0;
        int n8 = n3;
        object = object2;
        n3 = n7;
        do {
            parsePosition2.setIndex(n2);
            double d4 = this.matchToDelimiter((String)object, n6, d2, this.ruleText.substring(n4, n8), this.rulePatternFormat, parsePosition2, this.sub1, d, n).doubleValue();
            if (parsePosition2.getIndex() != 0 || this.sub1 == null) {
                n6 = parsePosition2.getIndex();
                object2 = ((String)object).substring(parsePosition2.getIndex());
                ParsePosition parsePosition3 = new ParsePosition(0);
                d4 = this.matchToDelimiter((String)object2, 0, d4, this.ruleText.substring(n8), this.rulePatternFormat, parsePosition3, this.sub2, d, n).doubleValue();
                if (parsePosition3.getIndex() != 0 || this.sub2 == null) {
                    if (n5 + parsePosition2.getIndex() + parsePosition3.getIndex() > n3) {
                        n3 = n5 + parsePosition2.getIndex() + parsePosition3.getIndex();
                        d3 = d4;
                    }
                }
            }
            if (n4 == n8 || parsePosition2.getIndex() <= 0 || parsePosition2.getIndex() >= ((String)object).length() || parsePosition2.getIndex() == n6) break;
            n2 = 0;
        } while (true);
        parsePosition.setIndex(n3);
        if (bl && n3 > 0 && this.sub1 == null) {
            d3 = 1.0 / d3;
        }
        if (d3 == (double)((long)d3)) {
            return (long)d3;
        }
        return new Double(d3);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof NFRule;
        boolean bl2 = false;
        if (bl) {
            object = (NFRule)object;
            if (this.baseValue == ((NFRule)object).baseValue && this.radix == ((NFRule)object).radix && this.exponent == ((NFRule)object).exponent && this.ruleText.equals(((NFRule)object).ruleText) && Objects.equals(this.sub1, ((NFRule)object).sub1) && Objects.equals(this.sub2, ((NFRule)object).sub2)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public final long getBaseValue() {
        return this.baseValue;
    }

    public final char getDecimalPoint() {
        return this.decimalPoint;
    }

    public long getDivisor() {
        return NFRule.power(this.radix, this.exponent);
    }

    public int hashCode() {
        return 42;
    }

    final void setBaseValue(long l) {
        this.baseValue = l;
        this.radix = 10;
        if (this.baseValue >= 1L) {
            this.exponent = this.expectedExponent();
            NFSubstitution nFSubstitution = this.sub1;
            if (nFSubstitution != null) {
                nFSubstitution.setDivisor(this.radix, this.exponent);
            }
            if ((nFSubstitution = this.sub2) != null) {
                nFSubstitution.setDivisor(this.radix, this.exponent);
            }
        } else {
            this.exponent = (short)(false ? 1 : 0);
        }
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        NFSubstitution nFSubstitution = this.sub1;
        if (nFSubstitution != null) {
            nFSubstitution.setDecimalFormatSymbols(decimalFormatSymbols);
        }
        if ((nFSubstitution = this.sub2) != null) {
            nFSubstitution.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }

    public boolean shouldRollBack(long l) {
        NFSubstitution nFSubstitution = this.sub1;
        boolean bl = false;
        if (nFSubstitution != null && nFSubstitution.isModulusSubstitution() || (nFSubstitution = this.sub2) != null && nFSubstitution.isModulusSubstitution()) {
            long l2 = NFRule.power(this.radix, this.exponent);
            boolean bl2 = bl;
            if (l % l2 == 0L) {
                bl2 = bl;
                if (this.baseValue % l2 != 0L) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder();
        long l = this.baseValue;
        if (l == -1L) {
            stringBuilder.append("-x: ");
        } else if (l == -2L) {
            int n;
            stringBuilder.append('x');
            int n2 = n = this.decimalPoint;
            if (n == 0) {
                n2 = n = 46;
            }
            stringBuilder.append((char)n2);
            stringBuilder.append("x: ");
        } else if (l == -3L) {
            int n;
            stringBuilder.append('0');
            int n3 = n = this.decimalPoint;
            if (n == 0) {
                n3 = n = 46;
            }
            stringBuilder.append((char)n3);
            stringBuilder.append("x: ");
        } else if (l == -4L) {
            int n;
            stringBuilder.append('x');
            int n4 = n = this.decimalPoint;
            if (n == 0) {
                n4 = n = 46;
            }
            stringBuilder.append((char)n4);
            stringBuilder.append("0: ");
        } else if (l == -5L) {
            stringBuilder.append("Inf: ");
        } else if (l == -6L) {
            stringBuilder.append("NaN: ");
        } else {
            stringBuilder.append(String.valueOf(l));
            if (this.radix != 10) {
                stringBuilder.append('/');
                stringBuilder.append(this.radix);
            }
            short s = this.expectedExponent();
            short s2 = this.exponent;
            for (int i = 0; i < s - s2; ++i) {
                stringBuilder.append('>');
            }
            stringBuilder.append(": ");
        }
        if (this.ruleText.startsWith(" ") && ((object = this.sub1) == null || ((NFSubstitution)object).getPos() != 0)) {
            stringBuilder.append('\'');
        }
        object = new StringBuilder(this.ruleText);
        NFSubstitution nFSubstitution = this.sub2;
        if (nFSubstitution != null) {
            ((StringBuilder)object).insert(nFSubstitution.getPos(), this.sub2.toString());
        }
        if ((nFSubstitution = this.sub1) != null) {
            ((StringBuilder)object).insert(nFSubstitution.getPos(), this.sub1.toString());
        }
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append(';');
        return stringBuilder.toString();
    }
}

