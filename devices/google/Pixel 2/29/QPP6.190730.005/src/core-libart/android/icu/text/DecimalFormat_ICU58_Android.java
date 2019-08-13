/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUConfig;
import android.icu.impl.PatternProps;
import android.icu.lang.UCharacter;
import android.icu.math.BigDecimal;
import android.icu.text.CurrencyPluralInfo;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.DigitList_Android;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.text.UFieldPosition;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.Currency;
import android.icu.util.CurrencyAmount;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.ChoiceFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Deprecated
public class DecimalFormat_ICU58_Android
extends NumberFormat {
    private static final char CURRENCY_SIGN = '\u00a4';
    private static final int CURRENCY_SIGN_COUNT_IN_ISO_FORMAT = 2;
    private static final int CURRENCY_SIGN_COUNT_IN_PLURAL_FORMAT = 3;
    private static final int CURRENCY_SIGN_COUNT_IN_SYMBOL_FORMAT = 1;
    private static final int CURRENCY_SIGN_COUNT_ZERO = 0;
    static final int DOUBLE_FRACTION_DIGITS = 340;
    static final int DOUBLE_INTEGER_DIGITS = 309;
    static final int MAX_INTEGER_DIGITS = 2000000000;
    static final int MAX_SCIENTIFIC_INTEGER_DIGITS = 8;
    static final Unit NULL_UNIT;
    public static final int PAD_AFTER_PREFIX = 1;
    public static final int PAD_AFTER_SUFFIX = 3;
    public static final int PAD_BEFORE_PREFIX = 0;
    public static final int PAD_BEFORE_SUFFIX = 2;
    static final char PATTERN_DECIMAL_SEPARATOR = '.';
    static final char PATTERN_DIGIT = '#';
    static final char PATTERN_EIGHT_DIGIT = '8';
    static final char PATTERN_EXPONENT = 'E';
    static final char PATTERN_FIVE_DIGIT = '5';
    static final char PATTERN_FOUR_DIGIT = '4';
    static final char PATTERN_GROUPING_SEPARATOR = ',';
    static final char PATTERN_MINUS_SIGN = '-';
    static final char PATTERN_NINE_DIGIT = '9';
    static final char PATTERN_ONE_DIGIT = '1';
    static final char PATTERN_PAD_ESCAPE = '*';
    private static final char PATTERN_PERCENT = '%';
    private static final char PATTERN_PER_MILLE = '\u2030';
    static final char PATTERN_PLUS_SIGN = '+';
    private static final char PATTERN_SEPARATOR = ';';
    static final char PATTERN_SEVEN_DIGIT = '7';
    static final char PATTERN_SIGNIFICANT_DIGIT = '@';
    static final char PATTERN_SIX_DIGIT = '6';
    static final char PATTERN_THREE_DIGIT = '3';
    static final char PATTERN_TWO_DIGIT = '2';
    static final char PATTERN_ZERO_DIGIT = '0';
    private static final char QUOTE = '\'';
    private static final int STATUS_INFINITE = 0;
    private static final int STATUS_LENGTH = 3;
    private static final int STATUS_POSITIVE = 1;
    private static final int STATUS_UNDERFLOW = 2;
    private static final UnicodeSet commaEquivalents;
    static final int currentSerialVersion = 4;
    private static final UnicodeSet defaultGroupingSeparators;
    private static final UnicodeSet dotEquivalents;
    private static double epsilon = 0.0;
    static final UnicodeSet minusSigns;
    static final UnicodeSet plusSigns;
    static final double roundingIncrementEpsilon = 1.0E-9;
    private static final long serialVersionUID = 864413376551465018L;
    static final boolean skipExtendedSeparatorParsing;
    private static final UnicodeSet strictCommaEquivalents;
    private static final UnicodeSet strictDefaultGroupingSeparators;
    private static final UnicodeSet strictDotEquivalents;
    private int PARSE_MAX_EXPONENT = 1000;
    private transient java.math.BigDecimal actualRoundingIncrement = null;
    private transient BigDecimal actualRoundingIncrementICU = null;
    private transient Set<AffixForCurrency> affixPatternsForCurrency = null;
    private ArrayList<FieldPosition> attributes = new ArrayList();
    private ChoiceFormat currencyChoice;
    private CurrencyPluralInfo currencyPluralInfo = null;
    private int currencySignCount = 0;
    private Currency.CurrencyUsage currencyUsage = Currency.CurrencyUsage.STANDARD;
    private boolean decimalSeparatorAlwaysShown = false;
    private transient DigitList_Android digitList = new DigitList_Android();
    private boolean exponentSignAlwaysShown = false;
    private String formatPattern = "";
    private int formatWidth = 0;
    private byte groupingSize = (byte)3;
    private byte groupingSize2 = (byte)(false ? 1 : 0);
    private transient boolean isReadyForParsing = false;
    private android.icu.math.MathContext mathContext = new android.icu.math.MathContext(0, 0);
    private int maxSignificantDigits = 6;
    private byte minExponentDigits;
    private int minSignificantDigits = 1;
    private int multiplier = 1;
    private String negPrefixPattern;
    private String negSuffixPattern;
    private String negativePrefix = "-";
    private String negativeSuffix = "";
    private char pad = (char)32;
    private int padPosition = 0;
    private boolean parseBigDecimal = false;
    boolean parseRequireDecimalPoint = false;
    private String posPrefixPattern;
    private String posSuffixPattern;
    private String positivePrefix = "";
    private String positiveSuffix = "";
    private transient double roundingDouble = 0.0;
    private transient double roundingDoubleReciprocal = 0.0;
    private java.math.BigDecimal roundingIncrement = null;
    private transient BigDecimal roundingIncrementICU = null;
    private int roundingMode = 6;
    private int serialVersionOnStream = 4;
    private int style = 0;
    private DecimalFormatSymbols symbols = null;
    private boolean useExponentialNotation;
    private boolean useSignificantDigits = false;

    static {
        epsilon = 1.0E-11;
        dotEquivalents = new UnicodeSet(46, 46, 8228, 8228, 12290, 12290, 65042, 65042, 65106, 65106, 65294, 65294, 65377, 65377).freeze();
        commaEquivalents = new UnicodeSet(44, 44, 1548, 1548, 1643, 1643, 12289, 12289, 65040, 65041, 65104, 65105, 65292, 65292, 65380, 65380).freeze();
        strictDotEquivalents = new UnicodeSet(46, 46, 8228, 8228, 65106, 65106, 65294, 65294, 65377, 65377).freeze();
        strictCommaEquivalents = new UnicodeSet(44, 44, 1643, 1643, 65040, 65040, 65104, 65104, 65292, 65292).freeze();
        defaultGroupingSeparators = new UnicodeSet(32, 32, 39, 39, 44, 44, 46, 46, 160, 160, 1548, 1548, 1643, 1644, 8192, 8202, 8216, 8217, 8228, 8228, 8239, 8239, 8287, 8287, 12288, 12290, 65040, 65042, 65104, 65106, 65287, 65287, 65292, 65292, 65294, 65294, 65377, 65377, 65380, 65380).freeze();
        strictDefaultGroupingSeparators = new UnicodeSet(32, 32, 39, 39, 44, 44, 46, 46, 160, 160, 1643, 1644, 8192, 8202, 8216, 8217, 8228, 8228, 8239, 8239, 8287, 8287, 12288, 12288, 65040, 65040, 65104, 65104, 65106, 65106, 65287, 65287, 65292, 65292, 65294, 65294, 65377, 65377).freeze();
        minusSigns = new UnicodeSet(45, 45, 8315, 8315, 8331, 8331, 8722, 8722, 10134, 10134, 65123, 65123, 65293, 65293).freeze();
        plusSigns = new UnicodeSet(43, 43, 8314, 8314, 8330, 8330, 10133, 10133, 64297, 64297, 65122, 65122, 65291, 65291).freeze();
        skipExtendedSeparatorParsing = ICUConfig.get("android.icu.text.DecimalFormat.SkipExtendedSeparatorParsing", "false").equals("true");
        NULL_UNIT = new Unit("", "");
    }

    public DecimalFormat_ICU58_Android() {
        ULocale uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        String string = DecimalFormat_ICU58_Android.getPattern(uLocale, 0);
        this.symbols = new DecimalFormatSymbols(uLocale);
        this.setCurrency(Currency.getInstance(uLocale));
        this.applyPatternWithoutExpandAffix(string, false);
        if (this.currencySignCount == 3) {
            this.currencyPluralInfo = new CurrencyPluralInfo(uLocale);
        } else {
            this.expandAffixAdjustWidth(null);
        }
    }

    public DecimalFormat_ICU58_Android(String string) {
        ULocale uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        this.symbols = new DecimalFormatSymbols(uLocale);
        this.setCurrency(Currency.getInstance(uLocale));
        this.applyPatternWithoutExpandAffix(string, false);
        if (this.currencySignCount == 3) {
            this.currencyPluralInfo = new CurrencyPluralInfo(uLocale);
        } else {
            this.expandAffixAdjustWidth(null);
        }
    }

    public DecimalFormat_ICU58_Android(String string, DecimalFormatSymbols decimalFormatSymbols) {
        this.createFromPatternAndSymbols(string, decimalFormatSymbols);
    }

    @Deprecated
    public DecimalFormat_ICU58_Android(String string, DecimalFormatSymbols decimalFormatSymbols, int n) {
        CurrencyPluralInfo currencyPluralInfo = null;
        if (n == 6) {
            currencyPluralInfo = new CurrencyPluralInfo(decimalFormatSymbols.getULocale());
        }
        this.create(string, decimalFormatSymbols, currencyPluralInfo, n);
    }

    public DecimalFormat_ICU58_Android(String string, DecimalFormatSymbols decimalFormatSymbols, CurrencyPluralInfo currencyPluralInfo, int n) {
        CurrencyPluralInfo currencyPluralInfo2 = currencyPluralInfo;
        if (n == 6) {
            currencyPluralInfo2 = (CurrencyPluralInfo)currencyPluralInfo.clone();
        }
        this.create(string, decimalFormatSymbols, currencyPluralInfo2, n);
    }

    private void _setMaximumFractionDigits(int n) {
        super.setMaximumFractionDigits(Math.min(n, 340));
    }

    private void addAttribute(NumberFormat.Field object, int n, int n2) {
        object = new FieldPosition((Format.Field)object);
        ((FieldPosition)object).setBeginIndex(n);
        ((FieldPosition)object).setEndIndex(n2);
        this.attributes.add((FieldPosition)object);
    }

    private final void addPadding(StringBuffer stringBuffer, FieldPosition fieldPosition, int n, int n2) {
        int n3;
        int n4 = this.formatWidth;
        if (n4 > 0 && (n3 = n4 - stringBuffer.length()) > 0) {
            char[] arrc = new char[n3];
            for (n4 = 0; n4 < n3; ++n4) {
                arrc[n4] = this.pad;
            }
            n4 = this.padPosition;
            if (n4 != 0) {
                if (n4 != 1) {
                    if (n4 != 2) {
                        if (n4 == 3) {
                            stringBuffer.append(arrc);
                        }
                    } else {
                        stringBuffer.insert(stringBuffer.length() - n2, arrc);
                    }
                } else {
                    stringBuffer.insert(n, arrc);
                }
            } else {
                stringBuffer.insert(0, arrc);
            }
            n = this.padPosition;
            if (n == 0 || n == 1) {
                fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + n3);
                fieldPosition.setEndIndex(fieldPosition.getEndIndex() + n3);
            }
        }
    }

    private int appendAffix(StringBuffer stringBuffer, boolean bl, boolean bl2, FieldPosition object, boolean bl3) {
        int n;
        String string;
        Object object2;
        if (this.currencyChoice != null) {
            object = bl2 ? (bl ? this.negPrefixPattern : this.posPrefixPattern) : (bl ? this.negSuffixPattern : this.posSuffixPattern);
            StringBuffer stringBuffer2 = new StringBuffer();
            this.expandAffix((String)object, null, stringBuffer2);
            stringBuffer.append(stringBuffer2);
            return stringBuffer2.length();
        }
        if (bl2) {
            string = bl ? this.negativePrefix : this.positivePrefix;
            object2 = bl ? this.negPrefixPattern : this.posPrefixPattern;
        } else {
            string = bl ? this.negativeSuffix : this.positiveSuffix;
            object2 = bl ? this.negSuffixPattern : this.posSuffixPattern;
        }
        if (bl3) {
            n = string.indexOf(this.symbols.getCurrencySymbol());
            if (n > -1) {
                this.formatAffix2Attribute(bl2, NumberFormat.Field.CURRENCY, stringBuffer, n, this.symbols.getCurrencySymbol().length());
            }
            if ((n = string.indexOf(this.symbols.getMinusSignString())) > -1) {
                this.formatAffix2Attribute(bl2, NumberFormat.Field.SIGN, stringBuffer, n, this.symbols.getMinusSignString().length());
            }
            if ((n = string.indexOf(this.symbols.getPercentString())) > -1) {
                this.formatAffix2Attribute(bl2, NumberFormat.Field.PERCENT, stringBuffer, n, this.symbols.getPercentString().length());
            }
            if ((n = string.indexOf(this.symbols.getPerMillString())) > -1) {
                this.formatAffix2Attribute(bl2, NumberFormat.Field.PERMILLE, stringBuffer, n, this.symbols.getPerMillString().length());
            }
            if ((n = ((String)object2).indexOf("\u00a4\u00a4\u00a4")) > -1) {
                this.formatAffix2Attribute(bl2, NumberFormat.Field.CURRENCY, stringBuffer, n, string.length() - n);
            }
        }
        if (((FieldPosition)object).getFieldAttribute() == NumberFormat.Field.SIGN) {
            object2 = this.symbols;
            object2 = bl ? ((DecimalFormatSymbols)object2).getMinusSignString() : ((DecimalFormatSymbols)object2).getPlusSignString();
            n = string.indexOf((String)object2);
            if (n > -1) {
                n = stringBuffer.length() + n;
                ((FieldPosition)object).setBeginIndex(n);
                ((FieldPosition)object).setEndIndex(((String)object2).length() + n);
            }
        } else if (((FieldPosition)object).getFieldAttribute() == NumberFormat.Field.PERCENT) {
            n = string.indexOf(this.symbols.getPercentString());
            if (n > -1) {
                n = stringBuffer.length() + n;
                ((FieldPosition)object).setBeginIndex(n);
                ((FieldPosition)object).setEndIndex(this.symbols.getPercentString().length() + n);
            }
        } else if (((FieldPosition)object).getFieldAttribute() == NumberFormat.Field.PERMILLE) {
            n = string.indexOf(this.symbols.getPerMillString());
            if (n > -1) {
                n = stringBuffer.length() + n;
                ((FieldPosition)object).setBeginIndex(n);
                ((FieldPosition)object).setEndIndex(this.symbols.getPerMillString().length() + n);
            }
        } else if (((FieldPosition)object).getFieldAttribute() == NumberFormat.Field.CURRENCY) {
            if (string.indexOf(this.symbols.getCurrencySymbol()) > -1) {
                object2 = this.symbols.getCurrencySymbol();
                n = string.indexOf((String)object2);
                int n2 = stringBuffer.length() + n;
                n = ((String)object2).length();
                ((FieldPosition)object).setBeginIndex(n2);
                ((FieldPosition)object).setEndIndex(n + n2);
            } else if (string.indexOf(this.symbols.getInternationalCurrencySymbol()) > -1) {
                object2 = this.symbols.getInternationalCurrencySymbol();
                n = string.indexOf((String)object2);
                n = stringBuffer.length() + n;
                int n3 = ((String)object2).length();
                ((FieldPosition)object).setBeginIndex(n);
                ((FieldPosition)object).setEndIndex(n3 + n);
            } else if (((String)object2).indexOf("\u00a4\u00a4\u00a4") > -1) {
                int n4 = ((String)object2).indexOf("\u00a4\u00a4\u00a4");
                n = stringBuffer.length();
                int n5 = stringBuffer.length();
                int n6 = string.length();
                ((FieldPosition)object).setBeginIndex(n + n4);
                ((FieldPosition)object).setEndIndex(n5 + n6);
            }
        }
        stringBuffer.append(string);
        return string.length();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void appendAffixPattern(StringBuffer abstractStringBuilder, boolean bl, boolean bl2, boolean bl3) {
        String string = bl2 ? (bl ? this.negPrefixPattern : this.posPrefixPattern) : (bl ? this.negSuffixPattern : this.posSuffixPattern);
        if (string == null) {
            string = bl2 ? (bl ? this.negativePrefix : this.positivePrefix) : (bl ? this.negativeSuffix : this.positiveSuffix);
            ((StringBuffer)abstractStringBuilder).append('\'');
            int n = 0;
            do {
                if (n >= string.length()) {
                    ((StringBuffer)abstractStringBuilder).append('\'');
                    return;
                }
                char c = string.charAt(n);
                if (c == '\'') {
                    ((StringBuffer)abstractStringBuilder).append(c);
                }
                ((StringBuffer)abstractStringBuilder).append(c);
                ++n;
            } while (true);
        }
        if (!bl3) {
            ((StringBuffer)abstractStringBuilder).append(string);
            return;
        }
        int n = 0;
        while (n < string.length()) {
            block16 : {
                int n2;
                block15 : {
                    int n3;
                    block13 : {
                        block14 : {
                            n3 = string.charAt(n);
                            if (n3 == 37) break block13;
                            if (n3 == 39) break block14;
                            if (n3 != 45) {
                                if (n3 != 8240) {
                                    n2 = n3;
                                } else {
                                    n3 = this.symbols.getPerMill();
                                    n2 = n3;
                                }
                            } else {
                                n3 = this.symbols.getMinusSign();
                                n2 = n3;
                            }
                            break block15;
                        }
                        n3 = string.indexOf(39, n + 1);
                        if (n3 < 0) {
                            abstractStringBuilder = new StringBuilder();
                            ((StringBuilder)abstractStringBuilder).append("Malformed affix pattern: ");
                            ((StringBuilder)abstractStringBuilder).append(string);
                            throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
                        }
                        ((StringBuffer)abstractStringBuilder).append(string.substring(n, n3 + 1));
                        n = n3;
                        break block16;
                    }
                    n3 = this.symbols.getPercent();
                    n2 = n3;
                }
                if (n2 != this.symbols.getDecimalSeparator() && n2 != this.symbols.getGroupingSeparator()) {
                    ((StringBuffer)abstractStringBuilder).append((char)n2);
                } else {
                    ((StringBuffer)abstractStringBuilder).append('\'');
                    ((StringBuffer)abstractStringBuilder).append((char)n2);
                    ((StringBuffer)abstractStringBuilder).append('\'');
                }
            }
            ++n;
        }
    }

    private void applyPattern(String string, boolean bl) {
        this.applyPatternWithoutExpandAffix(string, bl);
        this.expandAffixAdjustWidth(null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void applyPatternWithoutExpandAffix(String var1_1, boolean var2_2) {
        var3_3 = 48;
        var4_4 = 64;
        var5_5 = 44;
        var6_6 = 46;
        var7_7 = 37;
        var8_8 = 8240;
        var9_9 = 35;
        var10_10 = 59;
        var11_11 = String.valueOf('E');
        var12_12 = 43;
        var13_13 = 42;
        var14_14 = 45;
        if (var2_2) {
            var3_3 = this.symbols.getZeroDigit();
            var4_4 = this.symbols.getSignificantDigit();
            var5_5 = this.symbols.getGroupingSeparator();
            var6_6 = this.symbols.getDecimalSeparator();
            var7_7 = this.symbols.getPercent();
            var8_8 = this.symbols.getPerMill();
            var9_9 = this.symbols.getDigit();
            var10_10 = this.symbols.getPatternSeparator();
            var11_11 = this.symbols.getExponentSeparator();
            var12_12 = this.symbols.getPlusSign();
            var13_13 = this.symbols.getPadEscape();
            var14_14 = this.symbols.getMinusSign();
        }
        var15_15 = var3_3 + 9;
        var16_16 = 0;
        var17_17 = false;
        var18_18 = 0;
        var19_19 = var12_12;
        var12_12 = var16_16;
        var20_20 = var9_9;
        var21_21 = var5_5;
        do {
            block84 : {
                var22_22 = var12_12;
                var23_23 = var11_11;
                if (var18_18 < 2 && var22_22 < var1_1.length()) {
                    var24_24 = 1;
                    var25_25 = 0;
                    var26_26 = 0;
                    var27_27 = new StringBuilder();
                    var28_28 = new StringBuilder();
                    var29_29 = 0;
                    var30_30 = 0;
                    var31_31 = 0;
                    var32_32 = -1;
                    var33_33 = 0;
                    var16_16 = -1;
                    var9_9 = 1;
                    var12_12 = 0;
                    var34_34 = -1;
                    var35_35 = -1;
                    var5_5 = -1;
                    var36_36 = '\u0000';
                    var37_37 = 0L;
                    var39_38 = -1;
                    var2_2 = false;
                    var40_39 = 0;
                    var11_11 = var27_27;
                    var41_40 = var13_13;
                } else {
                    if (var1_1.length() == 0) {
                        this.posSuffixPattern = "";
                        this.posPrefixPattern = "";
                        this.setMinimumIntegerDigits(0);
                        this.setMaximumIntegerDigits(309);
                        this.setMinimumFractionDigits(0);
                        this._setMaximumFractionDigits(340);
                    }
                    if (!var17_17 || this.negPrefixPattern.equals(this.posPrefixPattern) && this.negSuffixPattern.equals(this.posSuffixPattern)) {
                        this.negSuffixPattern = this.posSuffixPattern;
                        var11_11 = new StringBuilder();
                        var11_11.append('-');
                        var11_11.append(this.posPrefixPattern);
                        this.negPrefixPattern = var11_11.toString();
                    }
                    this.setLocale(null, null);
                    this.formatPattern = var1_1;
                    if (this.currencySignCount != 0) {
                        var1_1 = this.getCurrency();
                        if (var1_1 != null) {
                            this.setRoundingIncrement(var1_1.getRoundingIncrement(this.currencyUsage));
                            var5_5 = var1_1.getDefaultFractionDigits(this.currencyUsage);
                            this.setMinimumFractionDigits(var5_5);
                            this._setMaximumFractionDigits(var5_5);
                        }
                        if (this.currencySignCount == 3 && this.currencyPluralInfo == null) {
                            this.currencyPluralInfo = new CurrencyPluralInfo(this.symbols.getULocale());
                        }
                    }
                    this.resetActualRounding();
                    return;
                }
                for (var13_13 = var22_22; var13_13 < var1_1.length(); ++var13_13) {
                    block87 : {
                        block100 : {
                            block98 : {
                                block99 : {
                                    block97 : {
                                        block95 : {
                                            block96 : {
                                                block94 : {
                                                    block93 : {
                                                        block85 : {
                                                            block88 : {
                                                                block91 : {
                                                                    block92 : {
                                                                        block90 : {
                                                                            block89 : {
                                                                                block86 : {
                                                                                    var42_41 = var1_1.charAt(var13_13);
                                                                                    var43_42 = var8_8;
                                                                                    if (var24_24 == 0) break block85;
                                                                                    if (var24_24 == 1 || var24_24 == 2) break block86;
                                                                                    if (var24_24 != 3 && var24_24 != 4) {
                                                                                        var44_43 = var14_14;
                                                                                        var14_14 = var24_24;
                                                                                    } else {
                                                                                        if (var42_41 == 39) {
                                                                                            if (var13_13 + 1 < var1_1.length() && var1_1.charAt(var13_13 + 1) == '\'') {
                                                                                                ++var13_13;
                                                                                                var11_11.append((char)var42_41);
                                                                                            } else {
                                                                                                var24_24 -= 2;
                                                                                            }
                                                                                        }
                                                                                        var11_11.append((char)var42_41);
                                                                                        var44_43 = var14_14;
                                                                                        var14_14 = var24_24;
                                                                                    }
                                                                                    break block87;
                                                                                }
                                                                                if (var42_41 == var20_20 || var42_41 == var21_21 || var42_41 == var6_6 || var42_41 >= var3_3 && var42_41 <= var15_15 || var42_41 == var4_4) break block88;
                                                                                var44_43 = var6_6;
                                                                                if (var42_41 != 164) break block89;
                                                                                var44_43 = var13_13 + 1 < var1_1.length() && var1_1.charAt(var13_13 + '\u0001') == '\u00a4' ? 1 : 0;
                                                                                if (var44_43 != 0) {
                                                                                    var11_11.append((char)var42_41);
                                                                                    if (++var13_13 + 1 < var1_1.length() && var1_1.charAt(var13_13 + 1) == '\u00a4') {
                                                                                        ++var13_13;
                                                                                        var11_11.append((char)var42_41);
                                                                                        var43_42 = 3;
                                                                                    } else {
                                                                                        var43_42 = 2;
                                                                                    }
                                                                                } else {
                                                                                    var43_42 = 1;
                                                                                }
                                                                                var44_43 = var14_14;
                                                                                var14_14 = var24_24;
                                                                                ** GOTO lbl236
                                                                            }
                                                                            if (var42_41 != 39) break block90;
                                                                            if (var13_13 + '\u0001' < var1_1.length() && var1_1.charAt(var13_13 + 1) == '\'') {
                                                                                ++var13_13;
                                                                                var11_11.append((char)var42_41);
                                                                                var44_43 = var14_14;
                                                                                var14_14 = var24_24;
                                                                                var43_42 = var40_39;
                                                                            } else {
                                                                                var44_43 = var14_14;
                                                                                var14_14 = var24_24 += 2;
                                                                                var43_42 = var40_39;
                                                                            }
                                                                            ** GOTO lbl236
                                                                        }
                                                                        if (var42_41 == var10_10) {
                                                                            if (var24_24 == 1 || var18_18 == 1) {
                                                                                var11_11 = new StringBuilder();
                                                                                var11_11.append("Unquoted special character '");
                                                                                var11_11.append((char)var42_41);
                                                                                var11_11.append('\'');
                                                                                this.patternError(var11_11.toString(), (String)var1_1);
                                                                            }
                                                                            var43_42 = var13_13++;
                                                                            var6_6 = var44_43;
                                                                            var44_43 = var43_42;
                                                                            break block84;
                                                                        }
                                                                        if (var42_41 == var7_7 || var42_41 == var43_42) break block91;
                                                                        if (var42_41 != var14_14) break block92;
                                                                        var43_42 = 45;
                                                                        var44_43 = var14_14;
                                                                        var42_41 = var43_42;
                                                                        var14_14 = var24_24;
                                                                        var43_42 = var40_39;
                                                                        ** GOTO lbl236
                                                                    }
                                                                    if (var42_41 != var41_40) ** GOTO lbl233
                                                                    if (var5_5 >= 0) {
                                                                        this.patternError("Multiple pad specifiers", (String)var1_1);
                                                                    }
                                                                    if (var13_13 + 1 == var1_1.length()) {
                                                                        this.patternError("Invalid pad specifier", (String)var1_1);
                                                                    }
                                                                    var45_44 = var13_13 + 1;
                                                                    var36_36 = var1_1.charAt(var45_44);
                                                                    var5_5 = var13_13;
                                                                    var43_42 = var14_14;
                                                                    var6_6 = var44_43;
                                                                    var13_13 = var45_44;
                                                                    var14_14 = var24_24;
                                                                    var44_43 = var43_42;
                                                                    break block87;
                                                                }
                                                                if (var9_9 != 1) {
                                                                    this.patternError("Too many percent/permille characters", (String)var1_1);
                                                                }
                                                                var9_9 = var42_41 == var7_7 ? 100 : 1000;
                                                                var45_44 = var9_9;
                                                                var9_9 = var42_41 == var7_7 ? 37 : 8240;
                                                                var44_43 = var14_14;
                                                                var42_41 = var9_9;
                                                                var14_14 = var24_24;
                                                                var43_42 = var40_39;
                                                                var9_9 = var45_44;
                                                                ** GOTO lbl236
                                                            }
                                                            var46_45 = var14_14;
                                                            var44_43 = var6_6;
                                                            var43_42 = var9_9;
                                                            var45_44 = var5_5;
                                                            if (var24_24 == 1) {
                                                                var5_5 = var45_44;
                                                                var9_9 = var43_42;
                                                                var33_33 = var13_13--;
                                                                var6_6 = var44_43;
                                                                var14_14 = 0;
                                                                var44_43 = var46_45;
                                                            } else if (var42_41 == 39) {
                                                                if (var13_13 + 1 < var1_1.length() && var1_1.charAt(var13_13 + '\u0001') == '\'') {
                                                                    ++var13_13;
                                                                    var11_11.append((char)var42_41);
                                                                    var5_5 = var45_44;
                                                                    var9_9 = var43_42;
                                                                    var6_6 = var44_43;
                                                                    var14_14 = var24_24;
                                                                    var44_43 = var46_45;
                                                                } else {
                                                                    var14_14 = var24_24 + 2;
                                                                    var5_5 = var45_44;
                                                                    var9_9 = var43_42;
                                                                    var6_6 = var44_43;
                                                                    var44_43 = var46_45;
                                                                }
                                                            } else {
                                                                var47_46 = new StringBuilder();
                                                                var47_46.append("Unquoted special character '");
                                                                var47_46.append((char)var42_41);
                                                                var47_46.append('\'');
                                                                this.patternError(var47_46.toString(), (String)var1_1);
lbl233: // 2 sources:
                                                                var43_42 = var40_39;
                                                                var44_43 = var14_14;
                                                                var14_14 = var24_24;
lbl236: // 6 sources:
                                                                var11_11.append((char)var42_41);
                                                                var40_39 = var43_42;
                                                            }
                                                            break block87;
                                                        }
                                                        var43_42 = var14_14;
                                                        if (var42_41 != var20_20) break block93;
                                                        if (var30_30 <= 0 && var12_12 <= 0) {
                                                            ++var29_29;
                                                        } else {
                                                            ++var31_31;
                                                        }
                                                        if (var34_34 >= 0 && (var14_14 = var16_16) < 0) {
                                                            var34_34 = (byte)(var34_34 + 1);
                                                            var16_16 = var14_14;
                                                            var14_14 = var24_24;
                                                            var44_43 = var43_42;
                                                        } else {
                                                            var14_14 = var24_24;
                                                            var44_43 = var43_42;
                                                        }
                                                        break block87;
                                                    }
                                                    if ((var42_41 < var3_3 || var42_41 > var15_15) && var42_41 != var4_4) break block94;
                                                    if (var31_31 > 0) {
                                                        var47_46 = new StringBuilder();
                                                        var47_46.append("Unexpected '");
                                                        var47_46.append((char)var42_41);
                                                        var47_46.append('\'');
                                                        this.patternError(var47_46.toString(), (String)var1_1);
                                                    }
                                                    if (var42_41 == var4_4) {
                                                        var14_14 = var12_12 + 1;
                                                    } else {
                                                        ++var30_30;
                                                        if (var42_41 != var3_3) {
                                                            var44_43 = var29_29 + var30_30 + var31_31;
                                                            if (var32_32 >= 0) {
                                                                for (var14_14 = var32_32; var14_14 < var44_43; var37_37 *= 10L, ++var14_14) {
                                                                }
                                                                var32_32 = var14_14;
                                                            } else {
                                                                var32_32 = var44_43;
                                                            }
                                                            var14_14 = var12_12;
                                                            var37_37 += (long)(var42_41 - var3_3);
                                                        } else {
                                                            var14_14 = var12_12;
                                                        }
                                                    }
                                                    if (var34_34 >= 0 && var16_16 < 0) {
                                                        var34_34 = (byte)(var34_34 + 1);
                                                        var12_12 = var14_14;
                                                        var14_14 = var24_24;
                                                        var44_43 = var43_42;
                                                    } else {
                                                        var12_12 = var14_14;
                                                        var14_14 = var24_24;
                                                        var44_43 = var43_42;
                                                    }
                                                    break block87;
                                                }
                                                if (var42_41 != var21_21) break block95;
                                                var14_14 = var13_13;
                                                if (var42_41 != 39) ** GOTO lbl317
                                                var14_14 = var13_13;
                                                if (var13_13 + '\u0001' >= var1_1.length()) ** GOTO lbl317
                                                var44_43 = var1_1.charAt(var13_13 + '\u0001');
                                                var14_14 = var13_13;
                                                if (var44_43 == var20_20) ** GOTO lbl317
                                                if (var44_43 < var3_3) break block96;
                                                var14_14 = var13_13;
                                                if (var44_43 <= var15_15) ** GOTO lbl317
                                            }
                                            if (var44_43 != 39) {
                                                if (var34_34 < 0) {
                                                    var14_14 = 3;
                                                    var44_43 = var43_42;
                                                } else {
                                                    var11_11 = var28_28;
                                                    var25_25 = var13_13--;
                                                    var14_14 = 2;
                                                    var44_43 = var43_42;
                                                }
                                            } else {
                                                var14_14 = var13_13 + 1;
lbl317: // 5 sources:
                                                if (var16_16 >= 0) {
                                                    this.patternError("Grouping separator after decimal", (String)var1_1);
                                                }
                                                var35_35 = var34_34;
                                                var34_34 = 0;
                                                var13_13 = var14_14;
                                                var14_14 = var24_24;
                                                var44_43 = var43_42;
                                            }
                                            break block87;
                                        }
                                        var45_44 = var6_6;
                                        if (var42_41 != var45_44) break block97;
                                        if (var16_16 >= 0) {
                                            this.patternError("Multiple decimal separators", (String)var1_1);
                                        }
                                        var16_16 = var29_29 + var30_30 + var31_31;
                                        var6_6 = var45_44;
                                        var14_14 = var24_24;
                                        var44_43 = var43_42;
                                        break block87;
                                    }
                                    var11_11 = var23_23;
                                    var14_14 = var23_23.length();
                                    if (!var1_1.regionMatches(var13_13, (String)var11_11, 0, var14_14)) break block98;
                                    if (var39_38 >= 0) {
                                        this.patternError("Multiple exponential symbols", (String)var1_1);
                                    }
                                    if (var34_34 >= 0) {
                                        this.patternError("Grouping separator in exponential", (String)var1_1);
                                    }
                                    if ((var6_6 = var13_13 + var11_11.length()) < var1_1.length()) {
                                        var14_14 = var6_6;
                                        var48_47 = var2_2;
                                        if (var1_1.charAt(var6_6) == var19_19) {
                                            var48_47 = true;
                                            var14_14 = var6_6 + 1;
                                        }
                                    } else {
                                        var48_47 = var2_2;
                                        var14_14 = var6_6;
                                    }
                                    var6_6 = 0;
                                    while (var14_14 < var1_1.length() && var1_1.charAt(var14_14) == var3_3) {
                                        var6_6 = (byte)(var6_6 + '\u0001');
                                        ++var14_14;
                                    }
                                    if (var29_29 + var30_30 < 1 && var12_12 + var31_31 < 1 || var12_12 > 0 && var29_29 > 0) break block99;
                                    var44_43 = var6_6;
                                    var13_13 = var14_14;
                                    var2_2 = var48_47;
                                    if (var6_6 >= 1) break block100;
                                }
                                this.patternError("Malformed exponential", (String)var1_1);
                                var44_43 = var6_6;
                                var13_13 = var14_14;
                                var2_2 = var48_47;
                                break block100;
                            }
                            var44_43 = var39_38;
                        }
                        var25_25 = var13_13--;
                        var11_11 = var28_28;
                        var14_14 = 2;
                        var39_38 = var44_43;
                        var44_43 = var43_42;
                        var6_6 = var45_44;
                    }
                    var24_24 = var14_14;
                    var14_14 = var44_43;
                }
                var44_43 = var26_26;
            }
            var43_42 = var12_12;
            var45_44 = var39_38;
            if (var24_24 == 3 || var24_24 == 4) {
                this.patternError("Unterminated quote", (String)var1_1);
            }
            var12_12 = var25_25 == 0 ? var1_1.length() : var25_25;
            var39_38 = var44_43 == 0 ? var1_1.length() : (int)var44_43;
            if (var30_30 == 0 && var43_42 == 0 && var29_29 > 0 && var16_16 >= 0) {
                var44_43 = var31_31 = var16_16;
                if (var31_31 == 0) {
                    var44_43 = var31_31 + '\u0001';
                }
                var29_29 -= var44_43;
                var30_30 = 1;
                --var44_43;
            } else {
                var44_43 = var29_29;
                var29_29 = var31_31;
            }
            if (var16_16 < 0 && var29_29 > 0 && var43_42 == 0 || var16_16 >= 0 && (var43_42 > 0 || var16_16 < var44_43 || var16_16 > var44_43 + var30_30) || var34_34 == 0 || var35_35 == 0 || var43_42 > 0 && var30_30 > 0 || var24_24 > 2) {
                this.patternError("Malformed pattern", (String)var1_1);
            }
            if (var5_5 >= 0) {
                if (var5_5 == var22_22) {
                    var5_5 = 0;
                } else if (var5_5 + 2 == var33_33) {
                    var5_5 = 1;
                } else if (var5_5 == var12_12) {
                    var5_5 = 2;
                } else if (var5_5 + 2 == var39_38) {
                    var5_5 = 3;
                } else {
                    this.patternError("Illegal pad position", (String)var1_1);
                }
            }
            if (var18_18 == 0) {
                var11_11 = var27_27.toString();
                this.negPrefixPattern = var11_11;
                this.posPrefixPattern = var11_11;
                var11_11 = var28_28.toString();
                this.negSuffixPattern = var11_11;
                this.posSuffixPattern = var11_11;
                var48_47 = var45_44 >= 0;
                this.useExponentialNotation = var48_47;
                if (this.useExponentialNotation) {
                    this.minExponentDigits = (byte)var45_44;
                    this.exponentSignAlwaysShown = var2_2;
                }
                var39_38 = var44_43 + var30_30 + var29_29;
                var31_31 = var16_16 >= 0 ? var16_16 : var39_38;
                var2_2 = var43_42 > 0;
                this.setSignificantDigitsUsed(var2_2);
                if (var2_2) {
                    this.setMinimumSignificantDigits(var43_42);
                    this.setMaximumSignificantDigits(var43_42 + var29_29);
                } else {
                    var29_29 = var31_31 - var44_43;
                    this.setMinimumIntegerDigits(var29_29);
                    var29_29 = this.useExponentialNotation != false ? var44_43 + var29_29 : 309;
                    this.setMaximumIntegerDigits(var29_29);
                    var29_29 = var16_16 >= 0 ? var39_38 - var16_16 : 0;
                    this._setMaximumFractionDigits(var29_29);
                    var44_43 = var16_16 >= 0 ? var44_43 + var30_30 - var16_16 : 0;
                    this.setMinimumFractionDigits(var44_43);
                }
                var2_2 = var34_34 > 0;
                this.setGroupingUsed(var2_2);
                var44_43 = var34_34 > 0 ? var34_34 : 0;
                this.groupingSize = (byte)var44_43;
                var34_34 = var35_35 > 0 && var35_35 != var34_34 ? var35_35 : 0;
                this.groupingSize2 = (byte)var34_34;
                this.multiplier = var9_9;
                var2_2 = var16_16 == 0 || var16_16 == var39_38;
                this.setDecimalSeparatorAlwaysShown(var2_2);
                if (var5_5 >= 0) {
                    this.padPosition = var5_5;
                    this.formatWidth = var12_12 - var33_33;
                    this.pad = var36_36;
                } else {
                    this.formatWidth = 0;
                }
                if (var37_37 != 0L) {
                    var9_9 = var32_32 - var31_31;
                    var5_5 = var9_9 > 0 ? var9_9 : 0;
                    this.roundingIncrementICU = BigDecimal.valueOf(var37_37, var5_5);
                    if (var9_9 < 0) {
                        this.roundingIncrementICU = this.roundingIncrementICU.movePointRight(-var9_9);
                    }
                    this.roundingMode = 6;
                } else {
                    this.setRoundingIncrement((BigDecimal)null);
                }
                this.currencySignCount = var40_39;
            } else {
                this.negPrefixPattern = var27_27.toString();
                this.negSuffixPattern = var28_28.toString();
                var17_17 = true;
            }
            ++var18_18;
            var11_11 = var23_23;
            var12_12 = var13_13;
            var13_13 = var41_40;
        } while (true);
    }

    private int compareAffix(String string, int n, boolean bl, boolean bl2, String string2, boolean bl3, int n2, Currency[] arrcurrency) {
        if (!(arrcurrency != null || this.currencyChoice != null || this.currencySignCount != 0 && bl3)) {
            if (bl2) {
                string2 = bl ? this.negativePrefix : this.positivePrefix;
                return DecimalFormat_ICU58_Android.compareSimpleAffix(string2, string, n);
            }
            string2 = bl ? this.negativeSuffix : this.positiveSuffix;
            return DecimalFormat_ICU58_Android.compareSimpleAffix(string2, string, n);
        }
        return this.compareComplexAffix(string2, string, n, n2, arrcurrency);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int compareComplexAffix(String var1_1, String var2_2, int var3_3, int var4_4, Currency[] var5_5) {
        var6_6 = 0;
        var7_7 = var3_3;
        block0 : do {
            block15 : {
                block18 : {
                    block16 : {
                        block17 : {
                            if (var6_6 >= var1_1.length()) return var7_7 - var3_3;
                            if (var7_7 < 0) return var7_7 - var3_3;
                            var8_8 = var6_6 + 1;
                            if ((var6_6 = (int)var1_1.charAt(var6_6)) == 39) break block15;
                            var9_9 = null;
                            if (var6_6 == 37) break block16;
                            if (var6_6 == 43) break block17;
                            if (var6_6 == 45) ** GOTO lbl40
                            if (var6_6 != 164) {
                                if (var6_6 == 8240) {
                                    var9_9 = this.symbols.getPerMillString();
                                }
                            } else {
                                var6_6 = var1_1.length();
                                var10_10 = 1;
                                var6_6 = var8_8 < var6_6 && var1_1.charAt(var8_8) == '\u00a4' ? 1 : 0;
                                var11_11 = var8_8;
                                if (var6_6 != 0) {
                                    var11_11 = var8_8 + 1;
                                }
                                var8_8 = var11_11 < var1_1.length() && var1_1.charAt(var11_11) == '\u00a4' ? var10_10 : 0;
                                var6_6 = var11_11;
                                if (var8_8 != 0) {
                                    var6_6 = var11_11 + 1;
                                }
                                var9_9 = var12_12 = this.getLocale(ULocale.VALID_LOCALE);
                                if (var12_12 == null) {
                                    var9_9 = this.symbols.getLocale(ULocale.VALID_LOCALE);
                                }
                                if ((var9_9 = Currency.parse((ULocale)var9_9, var2_2, var4_4, (ParsePosition)(var12_12 = new ParsePosition(var7_7)))) != null) {
                                    if (var5_5 != null) {
                                        var5_5[0] = Currency.getInstance((String)var9_9);
                                    } else if (var9_9.compareTo(this.getEffectiveCurrency().getCurrencyCode()) != 0) {
                                        var7_7 = -1;
                                        continue;
                                    }
                                    var7_7 = var12_12.getIndex();
                                    continue;
                                }
                                var7_7 = -1;
                                continue;
lbl40: // 1 sources:
                                var9_9 = this.symbols.getMinusSignString();
                            }
                            break block18;
                        }
                        var9_9 = this.symbols.getPlusSignString();
                        break block18;
                    }
                    var9_9 = this.symbols.getPercentString();
                }
                if (var9_9 != null) {
                    var7_7 = DecimalFormat_ICU58_Android.match(var2_2, var7_7, (String)var9_9);
                    var6_6 = var8_8;
                    continue;
                }
                var7_7 = DecimalFormat_ICU58_Android.match(var2_2, var7_7, var6_6);
                if (PatternProps.isWhiteSpace(var6_6)) {
                    var6_6 = DecimalFormat_ICU58_Android.skipPatternWhiteSpace(var1_1, var8_8);
                    continue;
                }
                var6_6 = var8_8;
                continue;
            }
            do {
                if ((var6_6 = var1_1.indexOf(39, var8_8)) == var8_8) {
                    var7_7 = DecimalFormat_ICU58_Android.match(var2_2, var7_7, 39);
                    ++var6_6;
                    continue block0;
                }
                if (var6_6 <= var8_8) throw new RuntimeException();
                var7_7 = DecimalFormat_ICU58_Android.match(var2_2, var7_7, var1_1.substring(var8_8, var6_6));
                if (++var6_6 < var1_1.length() && var1_1.charAt(var6_6) == '\'') ** break;
                continue block0;
                var7_7 = DecimalFormat_ICU58_Android.match(var2_2, var7_7, 39);
                var8_8 = var6_6 + 1;
            } while (true);
            break;
        } while (true);
    }

    private static int compareSimpleAffix(String string, String string2, int n) {
        if (string.length() > 1) {
            string = DecimalFormat_ICU58_Android.trimMarksFromAffix(string);
        }
        int n2 = 0;
        int n3 = n;
        while (n2 < string.length()) {
            int n4;
            int n5;
            int n6 = UTF16.charAt(string, n2);
            int n7 = UTF16.getCharCount(n6);
            if (PatternProps.isWhiteSpace(n6)) {
                int n8;
                int n9;
                n4 = 0;
                do {
                    n8 = n2;
                    n5 = n4;
                    n9 = n3;
                    if (n3 >= string2.length()) break;
                    int n10 = UTF16.charAt(string2, n3);
                    if (n10 == n6) {
                        n9 = 1;
                        n5 = 1;
                        n4 = n2 + n7;
                        n2 = n3 + n7;
                        if (n4 == string.length()) {
                            n8 = n4;
                            n5 = n9;
                            n9 = n2;
                            break;
                        }
                        n10 = UTF16.charAt(string, n4);
                        n7 = UTF16.getCharCount(n10);
                        n8 = n4;
                        n6 = n10;
                        n3 = n2;
                        if (!PatternProps.isWhiteSpace(n10)) {
                            n8 = n4;
                            n5 = n9;
                            n9 = n2;
                            break;
                        }
                    } else {
                        n8 = n2;
                        n5 = n4;
                        n9 = n3++;
                        if (!DecimalFormat_ICU58_Android.isBidiMark(n10)) break;
                        n5 = n4;
                        n8 = n2;
                    }
                    n2 = n8;
                    n4 = n5;
                } while (true);
                n2 = DecimalFormat_ICU58_Android.skipPatternWhiteSpace(string, n8);
                n3 = DecimalFormat_ICU58_Android.skipUWhiteSpace(string2, n9);
                if (n3 == n9 && n5 == 0) {
                    return -1;
                }
                n2 = DecimalFormat_ICU58_Android.skipUWhiteSpace(string, n2);
                continue;
            }
            n4 = 0;
            while (n3 < string2.length()) {
                n5 = UTF16.charAt(string2, n3);
                if (n4 == 0 && DecimalFormat_ICU58_Android.equalWithSignCompatibility(n5, n6)) {
                    n2 += n7;
                    n3 += n7;
                    n4 = 1;
                    continue;
                }
                if (!DecimalFormat_ICU58_Android.isBidiMark(n5)) break;
                ++n3;
            }
            if (n4 != 0) continue;
            return -1;
        }
        return n3 - n;
    }

    private void create(String string, DecimalFormatSymbols decimalFormatSymbols, CurrencyPluralInfo currencyPluralInfo, int n) {
        if (n != 6) {
            this.createFromPatternAndSymbols(string, decimalFormatSymbols);
        } else {
            this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
            this.currencyPluralInfo = currencyPluralInfo;
            this.applyPatternWithoutExpandAffix(this.currencyPluralInfo.getCurrencyPluralPattern("other"), false);
            this.setCurrencyForSymbols();
        }
        this.style = n;
    }

    private void createFromPatternAndSymbols(String string, DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        if (string.indexOf(164) >= 0) {
            this.setCurrencyForSymbols();
        }
        this.applyPatternWithoutExpandAffix(string, false);
        if (this.currencySignCount == 3) {
            this.currencyPluralInfo = new CurrencyPluralInfo(this.symbols.getULocale());
        } else {
            this.expandAffixAdjustWidth(null);
        }
    }

    private static boolean equalWithSignCompatibility(int n, int n2) {
        boolean bl = n == n2 || minusSigns.contains(n) && minusSigns.contains(n2) || plusSigns.contains(n) && plusSigns.contains(n2);
        return bl;
    }

    private boolean equals(String string, String string2) {
        boolean bl = true;
        if (string != null && string2 != null) {
            if (string.equals(string2)) {
                return true;
            }
            return this.unquote(string).equals(this.unquote(string2));
        }
        if (string != null || string2 != null) {
            bl = false;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void expandAffix(String var1_1, String var2_2, StringBuffer var3_3) {
        var3_3.setLength(0);
        var4_4 = 0;
        block0 : do {
            block10 : {
                block12 : {
                    block11 : {
                        if (var4_4 >= var1_1.length()) return;
                        var5_5 = var4_4 + 1;
                        var6_6 = var1_1.charAt(var4_4);
                        if (var6_6 == '\'') break block10;
                        if (var6_6 == '%') break block11;
                        if (var6_6 == '-') ** GOTO lbl39
                        if (var6_6 != '\u00a4') {
                            if (var6_6 != '\u2030') {
                                var3_3.append(var6_6);
                            } else {
                                var3_3.append(this.symbols.getPerMillString());
                            }
                        } else {
                            var7_7 = var5_5 < var1_1.length() && var1_1.charAt(var5_5) == '\u00a4';
                            var8_8 = false;
                            var4_4 = var5_5++;
                            var9_9 = var7_7;
                            var10_10 = var8_8;
                            if (var7_7) {
                                var4_4 = var5_5;
                                var9_9 = var7_7;
                                var10_10 = var8_8;
                                if (var5_5 < var1_1.length()) {
                                    var4_4 = var5_5;
                                    var9_9 = var7_7;
                                    var10_10 = var8_8;
                                    if (var1_1.charAt(var5_5) == '\u00a4') {
                                        var10_10 = true;
                                        var9_9 = false;
                                        var4_4 = var5_5 + 1;
                                    }
                                }
                            }
                            var11_11 = (var11_11 = this.getCurrency()) != null ? (var10_10 && var2_2 != null ? var11_11.getName(this.symbols.getULocale(), 2, var2_2, null) : (!var9_9 ? var11_11.getName(this.symbols.getULocale(), 0, null) : var11_11.getCurrencyCode())) : (var9_9 != false ? this.symbols.getInternationalCurrencySymbol() : this.symbols.getCurrencySymbol());
                            var3_3.append((String)var11_11);
                            continue;
lbl39: // 1 sources:
                            var3_3.append(this.symbols.getMinusSignString());
                        }
                        break block12;
                    }
                    var3_3.append(this.symbols.getPercentString());
                }
                var4_4 = var5_5;
                continue;
            }
            do {
                if ((var4_4 = var1_1.indexOf(39, var5_5)) == var5_5) {
                    var3_3.append('\'');
                    ++var4_4;
                    continue block0;
                }
                if (var4_4 <= var5_5) throw new RuntimeException();
                var3_3.append(var1_1.substring(var5_5, var4_4));
                if (++var4_4 < var1_1.length() && var1_1.charAt(var4_4) == '\'') ** break;
                continue block0;
                var3_3.append('\'');
                var5_5 = var4_4 + 1;
            } while (true);
            break;
        } while (true);
    }

    private void expandAffixAdjustWidth(String string) {
        this.expandAffixes(string);
        int n = this.formatWidth;
        if (n > 0) {
            this.formatWidth = n + (this.positivePrefix.length() + this.positiveSuffix.length());
        }
    }

    private void expandAffixes(String string) {
        this.currencyChoice = null;
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = this.posPrefixPattern;
        if (string2 != null) {
            this.expandAffix(string2, string, stringBuffer);
            this.positivePrefix = stringBuffer.toString();
        }
        if ((string2 = this.posSuffixPattern) != null) {
            this.expandAffix(string2, string, stringBuffer);
            this.positiveSuffix = stringBuffer.toString();
        }
        if ((string2 = this.negPrefixPattern) != null) {
            this.expandAffix(string2, string, stringBuffer);
            this.negativePrefix = stringBuffer.toString();
        }
        if ((string2 = this.negSuffixPattern) != null) {
            this.expandAffix(string2, string, stringBuffer);
            this.negativeSuffix = stringBuffer.toString();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl) {
        boolean bl2 = false;
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        if (Double.isNaN(d)) {
            if (fieldPosition.getField() == 0) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            }
            stringBuffer.append(this.symbols.getNaN());
            if (bl) {
                this.addAttribute(NumberFormat.Field.INTEGER, stringBuffer.length() - this.symbols.getNaN().length(), stringBuffer.length());
            }
            if (fieldPosition.getField() == 0) {
                fieldPosition.setEndIndex(stringBuffer.length());
            } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                fieldPosition.setEndIndex(stringBuffer.length());
            }
            this.addPadding(stringBuffer, fieldPosition, 0, 0);
            return stringBuffer;
        }
        d = this.multiply(d);
        boolean bl3 = this.isNegative(d);
        double d2 = this.round(d);
        if (Double.isInfinite(d2)) {
            int n = this.appendAffix(stringBuffer, bl3, true, fieldPosition, bl);
            if (fieldPosition.getField() == 0) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            }
            stringBuffer.append(this.symbols.getInfinity());
            if (bl) {
                this.addAttribute(NumberFormat.Field.INTEGER, stringBuffer.length() - this.symbols.getInfinity().length(), stringBuffer.length());
            }
            if (fieldPosition.getField() == 0) {
                fieldPosition.setEndIndex(stringBuffer.length());
            } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                fieldPosition.setEndIndex(stringBuffer.length());
            }
            this.addPadding(stringBuffer, fieldPosition, n, this.appendAffix(stringBuffer, bl3, false, fieldPosition, bl));
            return stringBuffer;
        }
        int n = this.precision(false);
        if (this.useExponentialNotation && n > 0 && d2 != 0.0 && this.roundingMode != 6) {
            double d3;
            int n2 = 1 - n + (int)Math.floor(Math.log10(Math.abs(d2)));
            if (n2 < 0) {
                d3 = BigDecimal.ONE.movePointRight(-n2).doubleValue();
                d = 0.0;
            } else {
                d = BigDecimal.ONE.movePointRight(n2).doubleValue();
                d3 = 0.0;
            }
            d = DecimalFormat_ICU58_Android.round(d2, d, d3, this.roundingMode, bl3);
        } else {
            d = d2;
        }
        DigitList_Android digitList_Android = this.digitList;
        synchronized (digitList_Android) {
            void var3_4;
            block29 : {
                DigitList_Android digitList_Android2;
                block28 : {
                    digitList_Android2 = this.digitList;
                    boolean bl4 = this.useExponentialNotation;
                    if (!bl4) {
                        try {
                            bl4 = this.areSignificantDigitsUsed();
                            if (bl4) break block28;
                            bl2 = true;
                        }
                        catch (Throwable throwable) {
                            break block29;
                        }
                    }
                }
                digitList_Android2.set(d, n, bl2);
                try {
                    return this.subformat(d, stringBuffer, fieldPosition, bl3, false, bl);
                }
                catch (Throwable throwable) {}
            }
            throw var3_4;
        }
    }

    /*
     * Exception decompiling
     */
    private StringBuffer format(long var1_1, StringBuffer var3_2, FieldPosition var4_3, boolean var5_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 3[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private StringBuffer format(java.math.BigDecimal serializable, StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl) {
        int n = this.multiplier;
        Object object = serializable;
        if (n != 1) {
            object = serializable.multiply(java.math.BigDecimal.valueOf(n));
        }
        Object object2 = this.actualRoundingIncrement;
        serializable = object;
        if (object2 != null) {
            serializable = ((java.math.BigDecimal)object).divide((java.math.BigDecimal)object2, 0, this.roundingMode).multiply(this.actualRoundingIncrement);
        }
        object = this.digitList;
        synchronized (object) {
            object2 = this.digitList;
            n = this.precision(false);
            boolean bl2 = !this.useExponentialNotation && !this.areSignificantDigitsUsed();
            ((DigitList_Android)object2).set((java.math.BigDecimal)serializable, n, bl2);
            if (this.digitList.wasRounded() && this.roundingMode == 7) {
                serializable = new ArithmeticException("Rounding necessary");
                throw serializable;
            }
            double d = serializable.doubleValue();
            bl2 = serializable.signum() < 0;
            return this.subformat(d, stringBuffer, fieldPosition, bl2, false, bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private StringBuffer format(BigInteger object, StringBuffer serializable, FieldPosition fieldPosition, boolean bl) {
        if (this.actualRoundingIncrementICU != null) {
            return this.format(new BigDecimal((BigInteger)object), (StringBuffer)serializable, fieldPosition);
        }
        int n = this.multiplier;
        boolean bl2 = true;
        BigInteger bigInteger = object;
        if (n != 1) {
            bigInteger = ((BigInteger)object).multiply(BigInteger.valueOf(n));
        }
        object = this.digitList;
        synchronized (object) {
            this.digitList.set(bigInteger, this.precision(true));
            if (this.digitList.wasRounded() && this.roundingMode == 7) {
                serializable = new ArithmeticException("Rounding necessary");
                throw serializable;
            }
            n = bigInteger.intValue();
            if (bigInteger.signum() >= 0) {
                bl2 = false;
            }
            return this.subformat(n, (StringBuffer)serializable, fieldPosition, bl2, true, bl);
        }
    }

    private void formatAffix2Attribute(boolean bl, NumberFormat.Field field, StringBuffer stringBuffer, int n, int n2) {
        int n3;
        n = n3 = n;
        if (!bl) {
            n = n3 + stringBuffer.length();
        }
        this.addAttribute(field, n, n + n2);
    }

    private UnicodeSet getEquivalentDecimals(String string, boolean bl) {
        UnicodeSet unicodeSet = UnicodeSet.EMPTY;
        if (bl) {
            if (strictDotEquivalents.contains(string)) {
                unicodeSet = strictDotEquivalents;
            } else if (strictCommaEquivalents.contains(string)) {
                unicodeSet = strictCommaEquivalents;
            }
        } else if (dotEquivalents.contains(string)) {
            unicodeSet = dotEquivalents;
        } else if (commaEquivalents.contains(string)) {
            unicodeSet = commaEquivalents;
        }
        return unicodeSet;
    }

    private static boolean isBidiMark(int n) {
        boolean bl = n == 8206 || n == 8207 || n == 1564;
        return bl;
    }

    private boolean isGroupingPosition(int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.isGroupingUsed()) {
            bl2 = bl;
            if (n > 0) {
                byte by = this.groupingSize;
                bl2 = bl;
                if (by > 0) {
                    byte by2 = this.groupingSize2;
                    bl = true;
                    bl2 = true;
                    if (by2 > 0 && n > by) {
                        if ((n - by) % by2 != 0) {
                            bl2 = false;
                        }
                    } else {
                        bl2 = n % this.groupingSize == 0 ? bl : false;
                    }
                }
            }
        }
        return bl2;
    }

    private boolean isNegative(double d) {
        boolean bl = d < 0.0 || d == 0.0 && 1.0 / d < 0.0;
        return bl;
    }

    static final int match(String string, int n, int n2) {
        if (n >= 0 && n < string.length()) {
            n = DecimalFormat_ICU58_Android.skipBidiMarks(string, n);
            if (PatternProps.isWhiteSpace(n2)) {
                n2 = DecimalFormat_ICU58_Android.skipPatternWhiteSpace(string, n);
                if (n2 == n) {
                    return -1;
                }
                return n2;
            }
            if (n < string.length() && UTF16.charAt(string, n) == n2) {
                return DecimalFormat_ICU58_Android.skipBidiMarks(string, UTF16.getCharCount(n2) + n);
            }
            return -1;
        }
        return -1;
    }

    static final int match(String string, int n, String string2) {
        int n2 = 0;
        int n3 = n;
        n = n2;
        while (n < string2.length() && n3 >= 0) {
            int n4 = UTF16.charAt(string2, n);
            n2 = n + UTF16.getCharCount(n4);
            if (DecimalFormat_ICU58_Android.isBidiMark(n4)) {
                n = n2;
                continue;
            }
            n3 = DecimalFormat_ICU58_Android.match(string, n3, n4);
            n = n2;
            if (!PatternProps.isWhiteSpace(n4)) continue;
            n = DecimalFormat_ICU58_Android.skipPatternWhiteSpace(string2, n2);
        }
        return n3;
    }

    private int matchesDigit(String string, int n, int[] arrn) {
        String[] arrstring = this.symbols.getDigitStringsLocal();
        for (int i = 0; i < 10; ++i) {
            int n2 = arrstring[i].length();
            if (!string.regionMatches(n, arrstring[i], 0, n2)) continue;
            arrn[0] = i;
            return n2;
        }
        n = string.codePointAt(n);
        arrn[0] = UCharacter.digit(n, 10);
        if (arrn[0] >= 0) {
            return Character.charCount(n);
        }
        return 0;
    }

    private double multiply(double d) {
        int n = this.multiplier;
        if (n != 1) {
            return (double)n * d;
        }
        return d;
    }

    private Object parse(String object, ParsePosition object2, Currency[] arrcurrency) {
        block35 : {
            int n;
            int n2;
            block32 : {
                block33 : {
                    block34 : {
                        int n3;
                        block30 : {
                            block31 : {
                                n2 = n = ((ParsePosition)object2).getIndex();
                                if (this.formatWidth <= 0) break block30;
                                n3 = this.padPosition;
                                if (n3 == 0) break block31;
                                n2 = n;
                                if (n3 != 1) break block30;
                            }
                            n2 = this.skipPadding((String)object, n);
                        }
                        if (!((String)object).regionMatches(n2, this.symbols.getNaN(), 0, this.symbols.getNaN().length())) break block32;
                        n2 = n = n2 + this.symbols.getNaN().length();
                        if (this.formatWidth <= 0) break block33;
                        n3 = this.padPosition;
                        if (n3 == 2) break block34;
                        n2 = n;
                        if (n3 != 3) break block33;
                    }
                    n2 = this.skipPadding((String)object, n);
                }
                ((ParsePosition)object2).setIndex(n2);
                return new Double(Double.NaN);
            }
            boolean[] arrbl = new boolean[3];
            if (this.currencySignCount != 0) {
                if (!this.parseForCurrency((String)object, (ParsePosition)object2, arrcurrency, arrbl)) {
                    return null;
                }
            } else {
                if (arrcurrency != null) {
                    return null;
                }
                if (!this.subparse((String)object, (ParsePosition)object2, this.digitList, arrbl, arrcurrency, this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, false, 0)) {
                    ((ParsePosition)object2).setIndex(n);
                    return null;
                }
            }
            if (arrbl[0]) {
                double d = arrbl[1] ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
                object = new Double(d);
            } else if (arrbl[2]) {
                object = arrbl[1] ? new Double("0.0") : new Double("-0.0");
            } else if (!arrbl[1] && this.digitList.isZero()) {
                object = new Double("-0.0");
            } else {
                n2 = this.multiplier;
                while (n2 % 10 == 0) {
                    object = this.digitList;
                    --((DigitList_Android)object).decimalAt;
                    n2 /= 10;
                }
                if (!this.parseBigDecimal && n2 == 1 && this.digitList.isIntegral()) {
                    if (this.digitList.decimalAt < 12) {
                        long l;
                        long l2 = l = 0L;
                        if (this.digitList.count > 0) {
                            n2 = 0;
                            l2 = l;
                            do {
                                l = l2;
                                if (n2 >= this.digitList.count) break;
                                l2 = 10L * l2 + (long)((char)this.digitList.digits[n2]) - 48L;
                            } while (true);
                            for (n = ++n2; n < this.digitList.decimalAt; ++n) {
                                l *= 10L;
                            }
                            l2 = l;
                            if (!arrbl[1]) {
                                l2 = -l;
                            }
                        }
                        object = l2;
                    } else {
                        object = this.digitList.getBigInteger(arrbl[1]);
                        if (((BigInteger)object).bitLength() < 64) {
                            object = ((BigInteger)object).longValue();
                        }
                    }
                } else {
                    object = object2 = this.digitList.getBigDecimalICU(arrbl[1]);
                    if (n2 != 1) {
                        object = ((BigDecimal)object2).divide(BigDecimal.valueOf(n2), this.mathContext);
                    }
                }
            }
            if (arrcurrency == null) break block35;
            object = new CurrencyAmount((Number)object, arrcurrency[0]);
        }
        return object;
    }

    private boolean parseForCurrency(String string, ParsePosition parsePosition, Currency[] arrcurrency, boolean[] arrbl) {
        int n;
        int n2;
        int n3 = parsePosition.getIndex();
        if (!this.isReadyForParsing) {
            n2 = this.currencySignCount;
            this.setupCurrencyAffixForAllPatterns();
            if (n2 == 3) {
                this.applyPatternWithoutExpandAffix(this.formatPattern, false);
            } else {
                this.applyPattern(this.formatPattern, false);
            }
            this.isReadyForParsing = true;
        }
        int n4 = -1;
        Object object = null;
        Object object2 = new boolean[3];
        Object object3 = new ParsePosition(n3);
        Object object4 = new DigitList_Android();
        boolean bl = this.style == 6 ? this.subparse(string, (ParsePosition)object3, (DigitList_Android)object4, (boolean[])object2, arrcurrency, this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, true, 1) : this.subparse(string, (ParsePosition)object3, (DigitList_Android)object4, (boolean[])object2, arrcurrency, this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, true, 0);
        n2 = n3;
        if (bl) {
            n = ((ParsePosition)object3).getIndex();
            if (n > n2) {
                n2 = ((ParsePosition)object3).getIndex();
                object = object2;
                this.digitList = object4;
            }
        } else {
            n4 = ((ParsePosition)object3).getErrorIndex();
        }
        object2 = this.affixPatternsForCurrency.iterator();
        n = n2;
        n2 = n4;
        n4 = n;
        while (object2.hasNext()) {
            ParsePosition parsePosition2 = new ParsePosition(n3);
            object3 = new DigitList_Android();
            object4 = new boolean[3];
            AffixForCurrency affixForCurrency = (AffixForCurrency)object2.next();
            if (this.subparse(string, parsePosition2, (DigitList_Android)object3, (boolean[])object4, arrcurrency, affixForCurrency.getNegPrefix(), affixForCurrency.getNegSuffix(), affixForCurrency.getPosPrefix(), affixForCurrency.getPosSuffix(), true, affixForCurrency.getPatternType())) {
                if (parsePosition2.getIndex() > n4) {
                    n4 = parsePosition2.getIndex();
                    this.digitList = object3;
                    bl = true;
                    object = object4;
                    continue;
                }
                bl = true;
                continue;
            }
            if (parsePosition2.getErrorIndex() <= n2) continue;
            n2 = parsePosition2.getErrorIndex();
        }
        object3 = new ParsePosition(n3);
        object4 = new DigitList_Android();
        object2 = new boolean[3];
        if (this.subparse(string, (ParsePosition)object3, (DigitList_Android)object4, (boolean[])object2, arrcurrency, this.negativePrefix, this.negativeSuffix, this.positivePrefix, this.positiveSuffix, false, 0)) {
            n3 = n4;
            if (((ParsePosition)object3).getIndex() > n4) {
                n3 = ((ParsePosition)object3).getIndex();
                object = object2;
                this.digitList = object4;
            }
            bl = true;
            n4 = n3;
        } else if (((ParsePosition)object3).getErrorIndex() > n2) {
            n2 = ((ParsePosition)object3).getErrorIndex();
        }
        if (!bl) {
            parsePosition.setErrorIndex(n2);
        } else {
            parsePosition.setIndex(n4);
            parsePosition.setErrorIndex(-1);
            for (n2 = 0; n2 < 3; ++n2) {
                arrbl[n2] = object[n2];
            }
        }
        return bl;
    }

    private void patternError(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" in pattern \"");
        stringBuilder.append(string2);
        stringBuilder.append('\"');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int precision(boolean bl) {
        if (this.areSignificantDigitsUsed()) {
            return this.getMaximumSignificantDigits();
        }
        if (this.useExponentialNotation) {
            return this.getMinimumIntegerDigits() + this.getMaximumFractionDigits();
        }
        int n = bl ? 0 : this.getMaximumFractionDigits();
        return n;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        if (this.getMaximumIntegerDigits() > 2000000000) {
            this.setMaximumIntegerDigits(2000000000);
        }
        if (this.getMaximumFractionDigits() > 340) {
            this._setMaximumFractionDigits(340);
        }
        if (this.serialVersionOnStream < 2) {
            this.exponentSignAlwaysShown = false;
            this.setInternalRoundingIncrement(null);
            this.roundingMode = 6;
            this.formatWidth = 0;
            this.pad = (char)32;
            this.padPosition = 0;
            if (this.serialVersionOnStream < 1) {
                this.useExponentialNotation = false;
            }
        }
        if (this.serialVersionOnStream < 3) {
            this.setCurrencyForSymbols();
        }
        if (this.serialVersionOnStream < 4) {
            this.currencyUsage = Currency.CurrencyUsage.STANDARD;
        }
        this.serialVersionOnStream = 4;
        this.digitList = new DigitList_Android();
        object = this.roundingIncrement;
        if (object != null) {
            this.setInternalRoundingIncrement(new BigDecimal((java.math.BigDecimal)object));
        }
        this.resetActualRounding();
    }

    private void resetActualRounding() {
        BigDecimal bigDecimal;
        if (this.roundingIncrementICU != null) {
            bigDecimal = this.getMaximumFractionDigits() > 0 ? BigDecimal.ONE.movePointLeft(this.getMaximumFractionDigits()) : BigDecimal.ONE;
            if (this.roundingIncrementICU.compareTo(bigDecimal) >= 0) {
                this.actualRoundingIncrementICU = this.roundingIncrementICU;
            } else {
                if (bigDecimal.equals(BigDecimal.ONE)) {
                    bigDecimal = null;
                }
                this.actualRoundingIncrementICU = bigDecimal;
            }
        } else {
            this.actualRoundingIncrementICU = this.roundingMode != 6 && !this.isScientificNotation() ? (this.getMaximumFractionDigits() > 0 ? BigDecimal.ONE.movePointLeft(this.getMaximumFractionDigits()) : BigDecimal.ONE) : null;
        }
        bigDecimal = this.actualRoundingIncrementICU;
        if (bigDecimal == null) {
            this.setRoundingDouble(0.0);
            this.actualRoundingIncrement = null;
        } else {
            this.setRoundingDouble(bigDecimal.doubleValue());
            this.actualRoundingIncrement = this.actualRoundingIncrementICU.toBigDecimal();
        }
    }

    private double round(double d) {
        boolean bl = this.isNegative(d);
        double d2 = d;
        if (bl) {
            d2 = -d;
        }
        if ((d = this.roundingDouble) > 0.0) {
            return DecimalFormat_ICU58_Android.round(d2, d, this.roundingDoubleReciprocal, this.roundingMode, bl);
        }
        return d2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static double round(double d, double d2, double d3, int n, boolean bl) {
        double d4 = d3 == 0.0 ? d / d2 : d * d3;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 7) {
                            if (d4 != Math.floor(d4)) throw new ArithmeticException("Rounding necessary");
                            return d;
                        }
                        double d5 = Math.ceil(d4);
                        double d6 = d5 - d4;
                        d = Math.floor(d4);
                        double d7 = d4 - d;
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 6) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Invalid rounding mode: ");
                                    stringBuilder.append(n);
                                    throw new IllegalArgumentException(stringBuilder.toString());
                                }
                                d4 = epsilon;
                                if (!(d7 + d4 < d6)) {
                                    if (d4 + d6 < d7) {
                                        d = d5;
                                    } else {
                                        d4 = d / 2.0;
                                        if (d4 != Math.floor(d4)) {
                                            d = d5;
                                        }
                                    }
                                }
                            } else if (!(d7 <= epsilon + d6)) {
                                d = d5;
                            }
                        } else if (d6 <= epsilon + d7) {
                            d = d5;
                        }
                    } else {
                        d = epsilon;
                        d = bl ? Math.ceil(d4 - d) : Math.floor(d + d4);
                    }
                } else {
                    d = epsilon;
                    d = bl ? Math.floor(d + d4) : Math.ceil(d4 - d);
                }
            } else {
                d = Math.floor(epsilon + d4);
            }
        } else {
            d = Math.ceil(d4 - epsilon);
        }
        if (d3 == 0.0) {
            return d *= d2;
        }
        d /= d3;
        return d;
    }

    private void setCurrencyForSymbols() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(this.symbols.getULocale());
        if (this.symbols.getCurrencySymbol().equals(decimalFormatSymbols.getCurrencySymbol()) && this.symbols.getInternationalCurrencySymbol().equals(decimalFormatSymbols.getInternationalCurrencySymbol())) {
            this.setCurrency(Currency.getInstance(this.symbols.getULocale()));
        } else {
            this.setCurrency(null);
        }
    }

    private void setInternalRoundingIncrement(BigDecimal number) {
        this.roundingIncrementICU = number;
        number = number == null ? null : number.toBigDecimal();
        this.roundingIncrement = number;
    }

    private void setRoundingDouble(double d) {
        this.roundingDouble = d;
        if ((d = this.roundingDouble) > 0.0) {
            d = 1.0 / d;
            this.roundingDoubleReciprocal = Math.rint(d);
            if (Math.abs(d - this.roundingDoubleReciprocal) > 1.0E-9) {
                this.roundingDoubleReciprocal = 0.0;
            }
        } else {
            this.roundingDoubleReciprocal = 0.0;
        }
    }

    private void setupCurrencyAffixForAllPatterns() {
        if (this.currencyPluralInfo == null) {
            this.currencyPluralInfo = new CurrencyPluralInfo(this.symbols.getULocale());
        }
        this.affixPatternsForCurrency = new HashSet<AffixForCurrency>();
        String string = this.formatPattern;
        this.applyPatternWithoutExpandAffix(DecimalFormat_ICU58_Android.getPattern(this.symbols.getULocale(), 1), false);
        Object object = new AffixForCurrency(this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, 0);
        this.affixPatternsForCurrency.add((AffixForCurrency)object);
        Iterator<String> iterator = this.currencyPluralInfo.pluralPatternIterator();
        object = new HashSet();
        while (iterator.hasNext()) {
            Object object2 = iterator.next();
            if ((object2 = this.currencyPluralInfo.getCurrencyPluralPattern((String)object2)) == null || object.contains(object2)) continue;
            object.add(object2);
            this.applyPatternWithoutExpandAffix((String)object2, false);
            object2 = new AffixForCurrency(this.negPrefixPattern, this.negSuffixPattern, this.posPrefixPattern, this.posSuffixPattern, 1);
            this.affixPatternsForCurrency.add((AffixForCurrency)object2);
        }
        this.formatPattern = string;
    }

    private static int skipBidiMarks(String string, int n) {
        int n2;
        while (n < string.length() && DecimalFormat_ICU58_Android.isBidiMark(n2 = UTF16.charAt(string, n))) {
            n += UTF16.getCharCount(n2);
        }
        return n;
    }

    private final int skipPadding(String string, int n) {
        while (n < string.length() && string.charAt(n) == this.pad) {
            ++n;
        }
        return n;
    }

    private static int skipPatternWhiteSpace(String string, int n) {
        int n2;
        while (n < string.length() && PatternProps.isWhiteSpace(n2 = UTF16.charAt(string, n))) {
            n += UTF16.getCharCount(n2);
        }
        return n;
    }

    private static int skipUWhiteSpace(String string, int n) {
        int n2;
        while (n < string.length() && UCharacter.isUWhiteSpace(n2 = UTF16.charAt(string, n))) {
            n += UTF16.getCharCount(n2);
        }
        return n;
    }

    private StringBuffer subformat(double d, StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl, boolean bl2, boolean bl3) {
        if (this.currencySignCount == 3) {
            return this.subformat(this.currencyPluralInfo.select(this.getFixedDecimal(d)), stringBuffer, fieldPosition, bl, bl2, bl3);
        }
        return this.subformat(stringBuffer, fieldPosition, bl, bl2, bl3);
    }

    private StringBuffer subformat(int n, StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl, boolean bl2, boolean bl3) {
        if (this.currencySignCount == 3) {
            return this.subformat(this.currencyPluralInfo.select(this.getFixedDecimal(n)), stringBuffer, fieldPosition, bl, bl2, bl3);
        }
        return this.subformat(stringBuffer, fieldPosition, bl, bl2, bl3);
    }

    private StringBuffer subformat(String string, StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl, boolean bl2, boolean bl3) {
        String string2;
        if (this.style == 6 && !this.formatPattern.equals(string2 = this.currencyPluralInfo.getCurrencyPluralPattern(string))) {
            this.applyPatternWithoutExpandAffix(string2, false);
        }
        this.expandAffixAdjustWidth(string);
        return this.subformat(stringBuffer, fieldPosition, bl, bl2, bl3);
    }

    private StringBuffer subformat(StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl, boolean bl2, boolean bl3) {
        if (this.digitList.isZero()) {
            this.digitList.decimalAt = 0;
        }
        int n = this.appendAffix(stringBuffer, bl, true, fieldPosition, bl3);
        if (this.useExponentialNotation) {
            this.subformatExponential(stringBuffer, fieldPosition, bl3);
        } else {
            this.subformatFixed(stringBuffer, fieldPosition, bl2, bl3);
        }
        this.addPadding(stringBuffer, fieldPosition, n, this.appendAffix(stringBuffer, bl, false, fieldPosition, bl3));
        return stringBuffer;
    }

    private void subformatExponential(StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        String[] arrstring = this.symbols.getDigitStringsLocal();
        String string = this.currencySignCount == 0 ? this.symbols.getDecimalSeparatorString() : this.symbols.getMonetaryDecimalSeparatorString();
        boolean bl2 = this.areSignificantDigitsUsed();
        int n5 = this.getMaximumIntegerDigits();
        int n6 = this.getMinimumIntegerDigits();
        if (fieldPosition.getField() == 0) {
            fieldPosition.setBeginIndex(stringBuffer.length());
            fieldPosition.setEndIndex(-1);
        } else if (fieldPosition.getField() == 1) {
            fieldPosition.setBeginIndex(-1);
        } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
            fieldPosition.setBeginIndex(stringBuffer.length());
            fieldPosition.setEndIndex(-1);
        } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.FRACTION) {
            fieldPosition.setBeginIndex(-1);
        }
        int n7 = stringBuffer.length();
        int n8 = -1;
        if (bl2) {
            n5 = 1;
            n4 = 1;
            n = this.getMinimumSignificantDigits() - 1;
        } else {
            n2 = this.getMinimumFractionDigits();
            n3 = n5;
            if (n5 > 8) {
                n3 = 1;
                if (1 < n6) {
                    n3 = n6;
                }
            }
            n4 = n3;
            n5 = n6;
            n = n2;
            if (n3 > n6) {
                n5 = 1;
                n = n2;
                n4 = n3;
            }
        }
        n6 = this.digitList.decimalAt;
        if (n4 > 1 && n4 != n5) {
            n3 = n6 > 0 ? (n6 - 1) / n4 : n6 / n4 - 1;
            n3 *= n4;
        } else {
            n3 = n5 <= 0 && n <= 0 ? 1 : n5;
            n3 = n6 - n3;
        }
        n6 = n5 + n;
        if (!this.digitList.isZero()) {
            n5 = this.digitList.decimalAt - n3;
        }
        n4 = n2 = this.digitList.count;
        if (n6 > n2) {
            n4 = n6;
        }
        n6 = n4;
        if (n5 > n4) {
            n6 = n5;
        }
        int n9 = 0;
        int n10 = -1;
        int n11 = 0;
        bl2 = false;
        n4 = n3;
        long l = 0L;
        n2 = n;
        n3 = n10;
        n = n8;
        n8 = n11;
        do {
            n11 = 0;
            if (n8 >= n6) break;
            if (n8 == n5) {
                if (fieldPosition.getField() == 0) {
                    fieldPosition.setEndIndex(stringBuffer.length());
                } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                    fieldPosition.setEndIndex(stringBuffer.length());
                }
                if (bl) {
                    n3 = stringBuffer.length();
                    this.addAttribute(NumberFormat.Field.INTEGER, n7, stringBuffer.length());
                }
                if (fieldPosition.getFieldAttribute() == NumberFormat.Field.DECIMAL_SEPARATOR) {
                    fieldPosition.setBeginIndex(stringBuffer.length());
                }
                stringBuffer.append(string);
                if (fieldPosition.getFieldAttribute() == NumberFormat.Field.DECIMAL_SEPARATOR) {
                    fieldPosition.setEndIndex(stringBuffer.length());
                }
                n = stringBuffer.length();
                if (bl) {
                    n10 = stringBuffer.length();
                    this.addAttribute(NumberFormat.Field.DECIMAL_SEPARATOR, n10 - 1, stringBuffer.length());
                }
                if (fieldPosition.getField() == 1) {
                    fieldPosition.setBeginIndex(stringBuffer.length());
                } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.FRACTION) {
                    fieldPosition.setBeginIndex(stringBuffer.length());
                }
                bl2 = fieldPosition instanceof UFieldPosition;
            }
            if (n8 < this.digitList.count) {
                n11 = this.digitList.getDigitValue(n8);
            }
            stringBuffer.append(arrstring[n11]);
            if (bl2) {
                ++n9;
                l = l * 10L + (long)n11;
            }
            ++n8;
        } while (true);
        if (this.digitList.isZero() && n6 == 0) {
            stringBuffer.append(arrstring[0]);
        }
        if (n == -1 && this.decimalSeparatorAlwaysShown) {
            if (fieldPosition.getFieldAttribute() == NumberFormat.Field.DECIMAL_SEPARATOR) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            }
            stringBuffer.append(string);
            if (fieldPosition.getFieldAttribute() == NumberFormat.Field.DECIMAL_SEPARATOR) {
                fieldPosition.setEndIndex(stringBuffer.length());
            }
            if (bl) {
                n5 = stringBuffer.length();
                this.addAttribute(NumberFormat.Field.DECIMAL_SEPARATOR, n5 - 1, stringBuffer.length());
            }
        }
        if (fieldPosition.getField() == 0) {
            if (fieldPosition.getEndIndex() < 0) {
                fieldPosition.setEndIndex(stringBuffer.length());
            }
        } else if (fieldPosition.getField() == 1) {
            if (fieldPosition.getBeginIndex() < 0) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            }
            fieldPosition.setEndIndex(stringBuffer.length());
        } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
            if (fieldPosition.getEndIndex() < 0) {
                fieldPosition.setEndIndex(stringBuffer.length());
            }
        } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.FRACTION) {
            if (fieldPosition.getBeginIndex() < 0) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            }
            fieldPosition.setEndIndex(stringBuffer.length());
        }
        if (bl2) {
            ((UFieldPosition)fieldPosition).setFractionDigits(n9, l);
        }
        if (bl) {
            if (n3 < 0) {
                this.addAttribute(NumberFormat.Field.INTEGER, n7, stringBuffer.length());
            }
            if (n > 0) {
                this.addAttribute(NumberFormat.Field.FRACTION, n, stringBuffer.length());
            }
        }
        if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT_SYMBOL) {
            fieldPosition.setBeginIndex(stringBuffer.length());
        }
        stringBuffer.append(this.symbols.getExponentSeparator());
        if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT_SYMBOL) {
            fieldPosition.setEndIndex(stringBuffer.length());
        }
        if (bl) {
            this.addAttribute(NumberFormat.Field.EXPONENT_SYMBOL, stringBuffer.length() - this.symbols.getExponentSeparator().length(), stringBuffer.length());
        }
        n5 = n4;
        if (this.digitList.isZero()) {
            n5 = 0;
        }
        if ((n3 = n5 < 0 ? 1 : 0) != 0) {
            n3 = -n5;
            if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT_SIGN) {
                fieldPosition.setBeginIndex(stringBuffer.length());
            }
            stringBuffer.append(this.symbols.getMinusSignString());
            if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT_SIGN) {
                fieldPosition.setEndIndex(stringBuffer.length());
            }
            if (bl) {
                this.addAttribute(NumberFormat.Field.EXPONENT_SIGN, stringBuffer.length() - 1, stringBuffer.length());
            }
        } else {
            n3 = n5;
            if (this.exponentSignAlwaysShown) {
                if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT_SIGN) {
                    fieldPosition.setBeginIndex(stringBuffer.length());
                }
                stringBuffer.append(this.symbols.getPlusSignString());
                if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT_SIGN) {
                    fieldPosition.setEndIndex(stringBuffer.length());
                }
                n3 = n5;
                if (bl) {
                    n3 = stringBuffer.length();
                    this.addAttribute(NumberFormat.Field.EXPONENT_SIGN, n3 - 1, stringBuffer.length());
                    n3 = n5;
                }
            }
        }
        n = stringBuffer.length();
        this.digitList.set(n3);
        n5 = n3 = (int)this.minExponentDigits;
        if (this.useExponentialNotation) {
            n5 = n3;
            if (n3 < 1) {
                n5 = 1;
            }
        }
        for (n3 = this.digitList.decimalAt; n3 < n5; ++n3) {
            stringBuffer.append(arrstring[0]);
        }
        for (n5 = 0; n5 < this.digitList.decimalAt; ++n5) {
            string = n5 < this.digitList.count ? arrstring[this.digitList.getDigitValue(n5)] : arrstring[0];
            stringBuffer.append(string);
        }
        if (fieldPosition.getFieldAttribute() == NumberFormat.Field.EXPONENT) {
            fieldPosition.setBeginIndex(n);
            fieldPosition.setEndIndex(stringBuffer.length());
        }
        if (bl) {
            this.addAttribute(NumberFormat.Field.EXPONENT, n, stringBuffer.length());
        }
    }

    private void subformatFixed(StringBuffer stringBuffer, FieldPosition fieldPosition, boolean bl, boolean bl2) {
        boolean bl3;
        int n;
        int n2;
        boolean bl4;
        Object object;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        long l;
        String[] arrstring;
        int n10;
        block41 : {
            block42 : {
                arrstring = this.symbols.getDigitStrings();
                object = this.currencySignCount == 0 ? this.symbols.getGroupingSeparatorString() : this.symbols.getMonetaryGroupingSeparatorString();
                String string = this.currencySignCount == 0 ? this.symbols.getDecimalSeparatorString() : this.symbols.getMonetaryDecimalSeparatorString();
                bl3 = this.areSignificantDigitsUsed();
                n6 = this.getMaximumIntegerDigits();
                n4 = this.getMinimumIntegerDigits();
                int n11 = stringBuffer.length();
                if (fieldPosition.getField() == 0 || fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                    fieldPosition.setBeginIndex(n11);
                }
                n = 0;
                n2 = this.getMinimumSignificantDigits();
                n3 = this.getMaximumSignificantDigits();
                if (!bl3) {
                    n2 = 0;
                    n3 = Integer.MAX_VALUE;
                }
                l = 0L;
                if (bl3) {
                    n4 = Math.max(1, this.digitList.decimalAt);
                }
                n7 = n4;
                if (this.digitList.decimalAt > 0) {
                    n7 = n4;
                    if (n4 < this.digitList.decimalAt) {
                        n7 = this.digitList.decimalAt;
                    }
                }
                n9 = 0;
                if (n7 > n6 && n6 >= 0) {
                    n4 = n6;
                    n7 = this.digitList.decimalAt - n4;
                } else {
                    n4 = n7;
                    n7 = n9;
                }
                n5 = stringBuffer.length();
                n9 = n4 - 1;
                n4 = 0;
                n6 = n7;
                n7 = n4;
                while (n9 >= 0) {
                    if (n9 < this.digitList.decimalAt && n6 < this.digitList.count && n7 < n3) {
                        stringBuffer.append(arrstring[this.digitList.getDigitValue(n6)]);
                        n4 = n7 + 1;
                        n8 = n6 + 1;
                    } else {
                        stringBuffer.append(arrstring[0]);
                        n4 = n7;
                        n8 = n6;
                        if (n7 > 0) {
                            n4 = n7 + 1;
                            n8 = n6;
                        }
                    }
                    if (this.isGroupingPosition(n9)) {
                        stringBuffer.append((String)object);
                        if (fieldPosition.getFieldAttribute() == NumberFormat.Field.GROUPING_SEPARATOR && fieldPosition.getBeginIndex() == 0 && fieldPosition.getEndIndex() == 0) {
                            fieldPosition.setBeginIndex(stringBuffer.length() - 1);
                            fieldPosition.setEndIndex(stringBuffer.length());
                        }
                        if (bl2) {
                            this.addAttribute(NumberFormat.Field.GROUPING_SEPARATOR, stringBuffer.length() - 1, stringBuffer.length());
                        }
                    }
                    --n9;
                    n7 = n4;
                    n6 = n8;
                }
                if (fieldPosition.getField() == 0 || fieldPosition.getFieldAttribute() == NumberFormat.Field.INTEGER) {
                    fieldPosition.setEndIndex(stringBuffer.length());
                }
                n4 = n7;
                if (n7 == 0) {
                    n4 = n7;
                    if (this.digitList.count == 0) {
                        n4 = 1;
                    }
                }
                if ((n8 = !bl && n6 < this.digitList.count || (bl3 ? n4 < n2 : this.getMinimumFractionDigits() > 0) ? 1 : 0) == 0 && stringBuffer.length() == n5) {
                    stringBuffer.append(arrstring[0]);
                }
                if (bl2) {
                    this.addAttribute(NumberFormat.Field.INTEGER, n11, stringBuffer.length());
                }
                if (this.decimalSeparatorAlwaysShown || n8 != 0) {
                    if (fieldPosition.getFieldAttribute() == NumberFormat.Field.DECIMAL_SEPARATOR) {
                        fieldPosition.setBeginIndex(stringBuffer.length());
                    }
                    stringBuffer.append(string);
                    if (fieldPosition.getFieldAttribute() == NumberFormat.Field.DECIMAL_SEPARATOR) {
                        fieldPosition.setEndIndex(stringBuffer.length());
                    }
                    if (bl2) {
                        this.addAttribute(NumberFormat.Field.DECIMAL_SEPARATOR, stringBuffer.length() - 1, stringBuffer.length());
                    }
                }
                if (fieldPosition.getField() == 1) {
                    fieldPosition.setBeginIndex(stringBuffer.length());
                } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.FRACTION) {
                    fieldPosition.setBeginIndex(stringBuffer.length());
                }
                n10 = stringBuffer.length();
                bl4 = fieldPosition instanceof UFieldPosition;
                n7 = bl3 ? Integer.MAX_VALUE : this.getMaximumFractionDigits();
                n9 = n7;
                if (!bl3) break block41;
                if (n4 == n3) break block42;
                n9 = n7;
                if (n4 < n2) break block41;
                n9 = n7;
                if (n6 != this.digitList.count) break block41;
            }
            n9 = 0;
        }
        n7 = n;
        n = n6;
        n5 = n9;
        n6 = n4;
        for (int i = 0; i < n5 && (bl3 || i < this.getMinimumFractionDigits() || !bl && n < this.digitList.count); ++i) {
            int n12;
            long l2;
            if (-1 - i > this.digitList.decimalAt - 1) {
                stringBuffer.append(arrstring[0]);
                if (!bl4) continue;
                ++n7;
                l *= 10L;
                continue;
            }
            if (!bl && n < this.digitList.count) {
                object = this.digitList;
                n9 = n + 1;
                n4 = ((DigitList_Android)object).getDigitValue(n);
                stringBuffer.append(arrstring[n4]);
                if (bl4) {
                    ++n7;
                    l2 = l * 10L + (long)n4;
                } else {
                    l2 = l;
                }
                n4 = n7;
            } else {
                stringBuffer.append(arrstring[0]);
                n9 = n;
                l2 = l;
                n4 = n7;
                if (bl4) {
                    n4 = n7 + 1;
                    l2 = l * 10L;
                    n9 = n;
                }
            }
            n6 = n12 = n6 + 1;
            n = n9;
            l = l2;
            n7 = n4;
            if (!bl3) continue;
            n6 = n12;
            l = l2;
            n7 = n4;
            if (n12 == n3) break;
            n6 = n12;
            n = n9;
            l = l2;
            n7 = n4;
            if (n9 != this.digitList.count) continue;
            n6 = n12;
            n = n9;
            l = l2;
            n7 = n4;
            if (n12 < n2) continue;
            n6 = n12;
            l = l2;
            n7 = n4;
            break;
        }
        if (fieldPosition.getField() == 1) {
            fieldPosition.setEndIndex(stringBuffer.length());
        } else if (fieldPosition.getFieldAttribute() == NumberFormat.Field.FRACTION) {
            fieldPosition.setEndIndex(stringBuffer.length());
        }
        if (bl4) {
            ((UFieldPosition)fieldPosition).setFractionDigits(n7, l);
        }
        if (bl2 && (this.decimalSeparatorAlwaysShown || n8 != 0)) {
            this.addAttribute(NumberFormat.Field.FRACTION, n10, stringBuffer.length());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final boolean subparse(String var1_1, ParsePosition var2_2, DigitList_Android var3_3, boolean[] var4_4, Currency[] var5_5, String var6_6, String var7_7, String var8_8, String var9_9, boolean var10_10, int var11_11) {
        block78 : {
            block76 : {
                block77 : {
                    var12_17 = var2_2.getIndex();
                    var13_18 = var2_2.getIndex();
                    if (this.formatWidth > 0 && this.padPosition == 0) {
                        var12_17 = this.skipPadding(var1_1, var12_17);
                    }
                    var14_19 = this.compareAffix(var1_1, var12_17, false, true, (String)var8_8 /* !! */ , (boolean)var10_15, var11_16, var5_5);
                    var15_20 = this.compareAffix(var1_1, var12_17, true, true, (String)var6_6, (boolean)var10_15, var11_16, var5_5);
                    if (var14_19 >= 0 && var15_20 >= 0) {
                        if (var14_19 > var15_20) {
                            var15_20 = -1;
                        } else if (var15_20 > var14_19) {
                            var14_19 = -1;
                        }
                    }
                    if (var14_19 >= 0) {
                        var16_21 = var12_17 + var14_19;
                    } else {
                        if (var15_20 < 0) {
                            var2_2.setErrorIndex(var12_17);
                            return false;
                        }
                        var16_21 = var12_17 + var15_20;
                    }
                    var12_17 = var16_21;
                    if (this.formatWidth > 0) {
                        var12_17 = var16_21;
                        if (this.padPosition == 1) {
                            var12_17 = this.skipPadding(var1_1, var16_21);
                        }
                    }
                    var4_4[0] = false;
                    if (!var1_1.regionMatches(var12_17, this.symbols.getInfinity(), 0, this.symbols.getInfinity().length())) break block77;
                    var17_22 = var12_17 + this.symbols.getInfinity().length();
                    var4_4[0] = true;
                    break block78;
                }
                var3_3.count = 0;
                var3_3.decimalAt = 0;
                if (this.currencySignCount == 0) {
                    var18_23 = this.symbols.getDecimalSeparatorString();
                } else {
                    var18_24 = this.symbols.getMonetaryDecimalSeparatorString();
                }
                if (this.currencySignCount == 0) {
                    var8_9 = this.symbols.getGroupingSeparatorString();
                } else {
                    var8_10 = this.symbols.getMonetaryGroupingSeparatorString();
                }
                var19_30 = this.symbols.getExponentSeparator();
                var20_31 = 0L;
                var22_32 = this.isParseStrict();
                var23_33 = 0;
                var16_21 = var24_34 = (int)this.groupingSize2;
                if (var24_34 == 0) {
                    var16_21 = this.groupingSize;
                }
                var6_6 = DecimalFormat_ICU58_Android.skipExtendedSeparatorParsing != false ? UnicodeSet.EMPTY : this.getEquivalentDecimals((String)var18_25, var22_32);
                var25_35 = var6_6;
                var6_6 = DecimalFormat_ICU58_Android.skipExtendedSeparatorParsing != false ? UnicodeSet.EMPTY : (var22_32 != false ? DecimalFormat_ICU58_Android.strictDefaultGroupingSeparators : DecimalFormat_ICU58_Android.defaultGroupingSeparators);
                var26_36 = var6_6;
                var27_37 = new int[1];
                var28_38 = false;
                var27_37[0] = -1;
                var29_39 = -1;
                var30_40 = 0;
                var24_34 = 0;
                var31_41 = false;
                var32_42 = -1;
                var33_43 = false;
                var6_6 = var18_25;
                var18_26 = var25_35;
                var34_44 = var8_11;
                var8_12 = var27_37;
                while (var12_17 < var1_1.length()) {
                    block79 : {
                        var35_47 = this.matchesDigit(var1_1, var12_17, var8_12);
                        if (var35_47 > 0) {
                            var17_22 = var29_39;
                            var36_48 = var30_40;
                            if (var32_42 != -1) {
                                if (var22_32 && (var29_39 != -1 && var30_40 != var16_21 || var29_39 == -1 && var30_40 > var16_21)) {
                                    var16_21 = 1;
                                    var17_22 = var12_17;
                                    var37_49 = var20_31;
                                    break block76;
                                }
                                var36_48 = 0;
                                var17_22 = var32_42;
                            }
                            var30_40 = var36_48 + 1;
                            var12_17 += var35_47;
                            var32_42 = -1;
                            var28_38 = true;
                            if (var8_12[0] == 0 && var3_3.count == 0) {
                                if (var31_41) {
                                    --var3_3.decimalAt;
                                }
                                var29_39 = var17_22;
                                continue;
                            }
                            ++var24_34;
                            var3_3.append((char)(var8_12[0] + 48));
                            var29_39 = var17_22;
                            continue;
                        }
                        var17_22 = var6_6.length();
                        if (!var1_1.regionMatches(var12_17, (String)var6_6, 0, var17_22)) break block79;
                        if (var22_32 && (var32_42 != -1 || var29_39 != -1 && var30_40 != this.groupingSize)) {
                            var16_21 = 1;
                            var17_22 = var12_17;
                            var37_49 = var20_31;
                            break block76;
                        }
                        if (this.isParseIntegerOnly()) ** GOTO lbl108
                        if (var31_41) {
                            var17_22 = var12_17;
                            var37_49 = var20_31;
                            var16_21 = var23_33;
                        } else {
                            var3_3.decimalAt = var24_34;
                            var31_41 = true;
                            var12_17 += var17_22;
                            continue;
lbl108: // 1 sources:
                            var17_22 = var12_17;
                            var37_49 = var20_31;
                            var16_21 = var23_33;
                        }
                        break block76;
                    }
                    if (this.isGroupingUsed()) {
                        var17_22 = var34_45.length();
                        var25_35 = var6_6;
                        if (var1_1.regionMatches(var12_17, (String)var34_45, 0, var17_22)) {
                            if (var31_41) {
                                var6_6 = var25_35;
                                var17_22 = var12_17;
                                var37_49 = var20_31;
                                var16_21 = var23_33;
                                break block76;
                            }
                            if (var22_32 && (!var28_38 || var32_42 != -1)) {
                                var16_21 = 1;
                                var6_6 = var25_35;
                                var17_22 = var12_17;
                                var37_49 = var20_31;
                                break block76;
                            }
                            var32_42 = var12_17;
                            var12_17 += var17_22;
                            var33_43 = true;
                            var6_6 = var25_35;
                            continue;
                        }
                    }
                    var17_22 = var1_1.codePointAt(var12_17);
                    var25_35 = var18_27;
                    if (!var31_41) {
                        if (var18_27.contains(var17_22)) {
                            if (var22_32 && (var32_42 != -1 || var29_39 != -1 && var30_40 != this.groupingSize)) {
                                var16_21 = 1;
                                var17_22 = var12_17;
                                var37_49 = var20_31;
                                break block76;
                            }
                            if (this.isParseIntegerOnly()) {
                                var17_22 = var12_17;
                                var37_49 = var20_31;
                                var16_21 = var23_33;
                                break block76;
                            }
                            var3_3.decimalAt = var24_34;
                            var6_6 = String.valueOf(Character.toChars(var17_22));
                            var31_41 = true;
                            var12_17 += Character.charCount(var17_22);
                            continue;
                        }
                        var25_35 = var18_27;
                    }
                    if (!this.isGroupingUsed() || var33_43 || !var26_36.contains(var17_22)) ** GOTO lbl171
                    if (var31_41) {
                        var17_22 = var12_17;
                        var37_49 = var20_31;
                        var16_21 = var23_33;
                    } else if (var22_32 && (!var28_38 || var32_42 != -1)) {
                        var16_21 = 1;
                        var17_22 = var12_17;
                        var37_49 = var20_31;
                    } else {
                        var34_46 = String.valueOf(Character.toChars(var17_22));
                        var32_42 = var12_17;
                        var12_17 += Character.charCount(var17_22);
                        var33_43 = true;
                        var18_28 = var25_35;
                        continue;
lbl171: // 1 sources:
                        var36_48 = var19_30.length();
                        var18_29 = var8_12;
                        var8_13 = var6_6;
                        var17_22 = var12_17;
                        var37_49 = var20_31;
                        var16_21 = var23_33;
                        var6_6 = var8_13;
                        if (var1_1.regionMatches(true, var12_17, var19_30, 0, var36_48)) {
                            var36_48 = 0;
                            var35_47 = var19_30.length() + var12_17;
                            var17_22 = var36_48;
                            var16_21 = var35_47;
                            if (var35_47 < var1_1.length()) {
                                var25_35 = this.symbols.getPlusSignString();
                                var6_6 = this.symbols.getMinusSignString();
                                if (var1_1.regionMatches(var35_47, (String)var25_35, 0, var25_35.length())) {
                                    var16_21 = var35_47 + var25_35.length();
                                    var17_22 = var36_48;
                                } else {
                                    var17_22 = var36_48;
                                    var16_21 = var35_47;
                                    if (var1_1.regionMatches(var35_47, (String)var6_6, 0, var6_6.length())) {
                                        var16_21 = var35_47 + var6_6.length();
                                        var17_22 = 1;
                                    }
                                }
                            }
                            var25_35 = new DigitList_Android();
                            var25_35.count = 0;
                            var6_6 = var18_29;
                            while (var16_21 < var1_1.length() && (var36_48 = this.matchesDigit(var1_1, var16_21, (int[])var6_6)) > 0) {
                                var25_35.append((char)(var6_6[0] + 48));
                                var16_21 += var36_48;
                            }
                            if (var25_35.count > 0) {
                                if (var22_32 && var33_43) {
                                    var16_21 = 1;
                                    var17_22 = var12_17;
                                    var37_49 = var20_31;
                                    var6_6 = var8_13;
                                } else {
                                    if (var25_35.count > 10) {
                                        if (var17_22 != 0) {
                                            var4_4[2] = true;
                                        } else {
                                            var4_4[0] = true;
                                        }
                                        var37_49 = var20_31;
                                    } else {
                                        var25_35.decimalAt = var25_35.count;
                                        var37_49 = var25_35.getLong();
                                        if (var17_22 != 0) {
                                            var37_49 = -var37_49;
                                        }
                                    }
                                    var17_22 = var16_21;
                                    var16_21 = var23_33;
                                    var6_6 = var8_13;
                                }
                            } else {
                                var17_22 = var12_17;
                                var37_49 = var20_31;
                                var16_21 = var23_33;
                                var6_6 = var8_13;
                            }
                        }
                    }
                    break block76;
                }
                var16_21 = var23_33;
                var37_49 = var20_31;
                var17_22 = var12_17;
            }
            if (var3_3.decimalAt == 0 && this.isDecimalPatternMatchRequired() && this.formatPattern.indexOf((String)var6_6) != -1) {
                var2_2.setIndex(var13_18);
                var2_2.setErrorIndex(var17_22);
                return false;
            }
            if (var32_42 != -1) {
                var17_22 = var32_42;
            }
            if (!var31_41) {
                var3_3.decimalAt = var24_34;
            }
            var12_17 = var16_21;
            if (var22_32) {
                var12_17 = var16_21;
                if (!var31_41) {
                    var12_17 = var16_21;
                    if (var29_39 != -1) {
                        var12_17 = var16_21;
                        if (var30_40 != this.groupingSize) {
                            var12_17 = 1;
                        }
                    }
                }
            }
            if (var12_17 != 0) {
                var2_2.setIndex(var13_18);
                var2_2.setErrorIndex(var17_22);
                return false;
            }
            if ((var37_49 += (long)var3_3.decimalAt) < (long)(-this.getParseMaxDigits())) {
                var4_4[2] = true;
            } else if (var37_49 > (long)this.getParseMaxDigits()) {
                var4_4[0] = true;
            } else {
                var3_3.decimalAt = (int)var37_49;
            }
            if (!var28_38 && var24_34 == 0) {
                var2_2.setIndex(var13_18);
                var2_2.setErrorIndex(var13_18);
                return false;
            }
        }
        var30_40 = var17_22;
        if (this.formatWidth > 0) {
            var30_40 = var17_22;
            if (this.padPosition == 2) {
                var30_40 = this.skipPadding(var1_1, var17_22);
            }
        }
        if (var14_19 >= 0) {
            var14_19 = this.compareAffix(var1_1, var30_40, false, false, (String)var9_14, (boolean)var10_15, var11_16, var5_5);
        }
        var12_17 = 0;
        var16_21 = 1;
        var32_42 = var15_20 >= 0 ? this.compareAffix(var1_1, var30_40, true, false, var7_7, (boolean)var10_15, var11_16, var5_5) : var15_20;
        var24_34 = var32_42;
        var11_16 = var14_19;
        if (var14_19 >= 0) {
            var24_34 = var32_42;
            var11_16 = var14_19;
            if (var32_42 >= 0) {
                if (var14_19 > var32_42) {
                    var24_34 = -1;
                    var11_16 = var14_19;
                } else {
                    var24_34 = var32_42;
                    var11_16 = var14_19;
                    if (var32_42 > var14_19) {
                        var11_16 = -1;
                        var24_34 = var32_42;
                    }
                }
            }
        }
        if ((var32_42 = var11_16 >= 0 ? var16_21 : var12_17) == (var15_20 = var24_34 >= 0 ? var16_21 : var12_17)) {
            var2_2.setErrorIndex(var30_40);
            return (boolean)var12_17;
        }
        if (var11_16 >= 0) {
            var24_34 = var11_16;
        }
        var24_34 = var32_42 = var30_40 + var24_34;
        if (this.formatWidth > 0) {
            var24_34 = var32_42;
            if (this.padPosition == 3) {
                var24_34 = this.skipPadding(var1_1, var32_42);
            }
        }
        var2_2.setIndex(var24_34);
        var11_16 = var11_16 >= 0 ? var16_21 : var12_17;
        var4_4[var16_21] = var11_16;
        if (var2_2.getIndex() != var13_18) return (boolean)var16_21;
        var2_2.setErrorIndex(var24_34);
        return (boolean)var12_17;
    }

    private String toPattern(boolean bl) {
        int n;
        int n2;
        int n3;
        CharSequence charSequence;
        StringBuffer stringBuffer = new StringBuffer();
        int n4 = bl ? (int)this.symbols.getZeroDigit() : 48;
        int n5 = bl ? (int)this.symbols.getDigit() : 35;
        int n6 = 0;
        boolean bl2 = this.areSignificantDigitsUsed();
        if (bl2) {
            n3 = bl ? (int)this.symbols.getSignificantDigit() : 64;
            n6 = n3;
        }
        int n7 = bl ? (n3 = (int)this.symbols.getGroupingSeparator()) : (n3 = 44);
        int n8 = 0;
        String string = null;
        n3 = this.formatWidth > 0 ? this.padPosition : -1;
        if (this.formatWidth > 0) {
            charSequence = new StringBuffer(2);
            n2 = bl ? (n = (int)this.symbols.getPadEscape()) : (n = 42);
            charSequence.append((char)n2);
            charSequence.append(this.pad);
            charSequence = charSequence.toString();
        } else {
            charSequence = null;
        }
        Serializable serializable = this.roundingIncrementICU;
        if (serializable != null) {
            n8 = ((BigDecimal)serializable).scale();
            string = this.roundingIncrementICU.movePointRight(n8).toString();
            n8 = string.length() - n8;
        }
        n2 = n5;
        int n9 = n4;
        for (n = 0; n < 2; ++n) {
            int n10;
            int n11;
            int n12;
            int n13;
            if (n3 == 0) {
                stringBuffer.append((String)charSequence);
            }
            boolean bl3 = n != 0;
            this.appendAffixPattern(stringBuffer, bl3, true, bl);
            if (n3 == 1) {
                stringBuffer.append((String)charSequence);
            }
            int n14 = stringBuffer.length();
            n5 = this.isGroupingUsed() ? Math.max(0, this.groupingSize) : 0;
            if (n5 > 0 && (n11 = this.groupingSize2) > 0) {
                n4 = n5;
                if (n11 != this.groupingSize) {
                    n4 = n5 + n11;
                }
            } else {
                n4 = n5;
            }
            if (bl2) {
                n10 = this.getMinimumSignificantDigits();
                n5 = n13 = this.getMaximumSignificantDigits();
            } else {
                n10 = this.getMinimumIntegerDigits();
                n5 = this.getMaximumIntegerDigits();
                n13 = 0;
            }
            n11 = n3;
            if (this.useExponentialNotation) {
                n3 = n5;
                if (n5 > 8) {
                    n3 = 1;
                }
            } else {
                n3 = bl2 ? Math.max(n5, n4 + 1) : Math.max(Math.max(n4, this.getMinimumIntegerDigits()), n8) + 1;
            }
            for (n5 = n3; n5 > 0; --n5) {
                int n15;
                if (!this.useExponentialNotation && n5 < n3 && this.isGroupingPosition(n5)) {
                    stringBuffer.append((char)n7);
                }
                if (bl2) {
                    n12 = n13 >= n5 && n5 > n13 - n10 ? (n15 = n6) : (n15 = n2);
                    stringBuffer.append((char)n12);
                    continue;
                }
                if (string != null && (n15 = n8 - n5) >= 0 && n15 < string.length()) {
                    stringBuffer.append((char)(string.charAt(n15) - 48 + n9));
                    continue;
                }
                n12 = n5 <= n10 ? (n15 = n9) : (n15 = n2);
                stringBuffer.append((char)n12);
            }
            if (!bl2) {
                if (this.getMaximumFractionDigits() > 0 || this.decimalSeparatorAlwaysShown) {
                    n12 = bl ? (n4 = (int)this.symbols.getDecimalSeparator()) : (n4 = 46);
                    stringBuffer.append((char)n12);
                }
                n5 = n8;
                for (n4 = 0; n4 < this.getMaximumFractionDigits(); ++n4) {
                    if (string != null && n5 < string.length()) {
                        n12 = n5 < 0 ? (n10 = n9) : (n10 = (char)(string.charAt(n5) - 48 + n9));
                        stringBuffer.append((char)n12);
                        ++n5;
                        continue;
                    }
                    n12 = n4 < this.getMinimumFractionDigits() ? (n10 = n9) : (n10 = n2);
                    stringBuffer.append((char)n12);
                }
            }
            if (this.useExponentialNotation) {
                if (bl) {
                    stringBuffer.append(this.symbols.getExponentSeparator());
                } else {
                    stringBuffer.append('E');
                }
                if (this.exponentSignAlwaysShown) {
                    n12 = bl ? (n4 = (int)this.symbols.getPlusSign()) : (n4 = 43);
                    stringBuffer.append((char)n12);
                }
                for (n4 = 0; n4 < this.minExponentDigits; ++n4) {
                    stringBuffer.append((char)n9);
                }
            }
            if (charSequence != null && !this.useExponentialNotation) {
                n5 = this.formatWidth;
                n10 = stringBuffer.length();
                if (n == 0) {
                    n4 = this.positivePrefix.length() + this.positiveSuffix.length();
                } else {
                    n4 = this.negativePrefix.length();
                    n4 = this.negativeSuffix.length() + n4;
                }
                n5 = n5 - n10 + n14 - n4;
                n4 = n3;
                n3 = n5;
                while (n3 > 0) {
                    stringBuffer.insert(n14, (char)n2);
                    if (--n3 <= 1 || !this.isGroupingPosition(++n4)) continue;
                    stringBuffer.insert(n14, (char)n7);
                    --n3;
                }
            }
            bl3 = true;
            if (n11 == 2) {
                stringBuffer.append((String)charSequence);
            }
            if (n == 0) {
                bl3 = false;
            }
            this.appendAffixPattern(stringBuffer, bl3, false, bl);
            if (n11 == 3) {
                stringBuffer.append((String)charSequence);
            }
            if (n == 0) {
                if (this.negativeSuffix.equals(this.positiveSuffix)) {
                    String string2 = this.negativePrefix;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append('-');
                    ((StringBuilder)serializable).append(this.positivePrefix);
                    if (string2.equals(((StringBuilder)serializable).toString())) break;
                }
                n12 = bl ? (n3 = (int)this.symbols.getPatternSeparator()) : (n3 = 59);
                stringBuffer.append((char)n12);
            }
            n3 = n11;
            n4 = n2;
            n9 = n5 = n9;
            n2 = n4;
        }
        return stringBuffer.toString();
    }

    private static String trimMarksFromAffix(String string) {
        boolean bl;
        boolean bl2 = false;
        int n = 0;
        do {
            bl = bl2;
            if (n >= string.length()) break;
            if (DecimalFormat_ICU58_Android.isBidiMark(string.charAt(n))) {
                bl = true;
                break;
            }
            ++n;
        } while (true);
        if (!bl) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string, 0, n);
        ++n;
        while (n < string.length()) {
            char c = string.charAt(n);
            if (!DecimalFormat_ICU58_Android.isBidiMark(c)) {
                stringBuilder.append(c);
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    private String unquote(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\'') continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.attributes.clear();
        objectOutputStream.defaultWriteObject();
    }

    @Deprecated
    double adjustNumberAsInFormatting(double d) {
        if (Double.isNaN(d)) {
            return d;
        }
        if (Double.isInfinite(d = this.round(this.multiply(d)))) {
            return d;
        }
        return this.toDigitList(d).getDouble();
    }

    public void applyLocalizedPattern(String string) {
        this.applyPattern(string, true);
    }

    public void applyPattern(String string) {
        this.applyPattern(string, false);
    }

    public boolean areSignificantDigitsUsed() {
        return this.useSignificantDigits;
    }

    @Override
    public Object clone() {
        try {
            DecimalFormat_ICU58_Android decimalFormat_ICU58_Android = (DecimalFormat_ICU58_Android)super.clone();
            decimalFormat_ICU58_Android.symbols = (DecimalFormatSymbols)this.symbols.clone();
            ArrayList arrayList = new ArrayList();
            decimalFormat_ICU58_Android.digitList = arrayList;
            if (this.currencyPluralInfo != null) {
                decimalFormat_ICU58_Android.currencyPluralInfo = (CurrencyPluralInfo)this.currencyPluralInfo.clone();
            }
            arrayList = new ArrayList();
            decimalFormat_ICU58_Android.attributes = arrayList;
            decimalFormat_ICU58_Android.currencyUsage = this.currencyUsage;
            return decimalFormat_ICU58_Android;
        }
        catch (Exception exception) {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block2 : {
            boolean bl2;
            bl = false;
            if (object == null) {
                return false;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (DecimalFormat_ICU58_Android)object;
            if (this.currencySignCount != ((DecimalFormat_ICU58_Android)object).currencySignCount || this.style == 6 && (!this.equals(this.posPrefixPattern, ((DecimalFormat_ICU58_Android)object).posPrefixPattern) || !this.equals(this.posSuffixPattern, ((DecimalFormat_ICU58_Android)object).posSuffixPattern) || !this.equals(this.negPrefixPattern, ((DecimalFormat_ICU58_Android)object).negPrefixPattern) || !this.equals(this.negSuffixPattern, ((DecimalFormat_ICU58_Android)object).negSuffixPattern)) || this.multiplier != ((DecimalFormat_ICU58_Android)object).multiplier || this.groupingSize != ((DecimalFormat_ICU58_Android)object).groupingSize || this.groupingSize2 != ((DecimalFormat_ICU58_Android)object).groupingSize2 || this.decimalSeparatorAlwaysShown != ((DecimalFormat_ICU58_Android)object).decimalSeparatorAlwaysShown || (bl2 = this.useExponentialNotation) != ((DecimalFormat_ICU58_Android)object).useExponentialNotation || bl2 && this.minExponentDigits != ((DecimalFormat_ICU58_Android)object).minExponentDigits || (bl2 = this.useSignificantDigits) != ((DecimalFormat_ICU58_Android)object).useSignificantDigits || bl2 && (this.minSignificantDigits != ((DecimalFormat_ICU58_Android)object).minSignificantDigits || this.maxSignificantDigits != ((DecimalFormat_ICU58_Android)object).maxSignificantDigits) || !this.symbols.equals(((DecimalFormat_ICU58_Android)object).symbols) || !Objects.equals(this.currencyPluralInfo, ((DecimalFormat_ICU58_Android)object).currencyPluralInfo) || !this.currencyUsage.equals((Object)((DecimalFormat_ICU58_Android)object).currencyUsage)) break block2;
            bl = true;
        }
        return bl;
    }

    @Override
    public StringBuffer format(double d, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(d, stringBuffer, fieldPosition, false);
    }

    @Override
    public StringBuffer format(long l, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(l, stringBuffer, fieldPosition, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public StringBuffer format(BigDecimal serializable, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        int n = this.multiplier;
        Object object = serializable;
        if (n != 1) {
            object = serializable.multiply(BigDecimal.valueOf(n), this.mathContext);
        }
        Object object2 = this.actualRoundingIncrementICU;
        serializable = object;
        if (object2 != null) {
            serializable = ((BigDecimal)object).divide((BigDecimal)object2, 0, this.roundingMode).multiply(this.actualRoundingIncrementICU, this.mathContext);
        }
        object = this.digitList;
        synchronized (object) {
            object2 = this.digitList;
            n = this.precision(false);
            boolean bl = !this.useExponentialNotation && !this.areSignificantDigitsUsed();
            ((DigitList_Android)object2).set((BigDecimal)serializable, n, bl);
            if (this.digitList.wasRounded() && this.roundingMode == 7) {
                serializable = new ArithmeticException("Rounding necessary");
                throw serializable;
            }
            double d = serializable.doubleValue();
            bl = serializable.signum() < 0;
            return this.subformat(d, stringBuffer, fieldPosition, bl, false, false);
        }
    }

    @Override
    public StringBuffer format(java.math.BigDecimal bigDecimal, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(bigDecimal, stringBuffer, fieldPosition, false);
    }

    @Override
    public StringBuffer format(BigInteger bigInteger, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.format(bigInteger, stringBuffer, fieldPosition, false);
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        return this.formatToCharacterIterator(object, NULL_UNIT);
    }

    AttributedCharacterIterator formatToCharacterIterator(Object object, Unit object2) {
        if (object instanceof Number) {
            Number number = (Number)object;
            Serializable serializable = new StringBuffer();
            ((Unit)object2).writePrefix((StringBuffer)serializable);
            this.attributes.clear();
            if (object instanceof BigInteger) {
                this.format((BigInteger)number, (StringBuffer)serializable, new FieldPosition(0), true);
            } else if (object instanceof java.math.BigDecimal) {
                this.format((java.math.BigDecimal)number, (StringBuffer)serializable, new FieldPosition(0), true);
            } else if (object instanceof Double) {
                this.format(number.doubleValue(), (StringBuffer)serializable, new FieldPosition(0), true);
            } else {
                if (!(object instanceof Integer) && !(object instanceof Long)) {
                    throw new IllegalArgumentException();
                }
                this.format(number.longValue(), (StringBuffer)serializable, new FieldPosition(0), true);
            }
            ((Unit)object2).writeSuffix((StringBuffer)serializable);
            object = new AttributedString(serializable.toString());
            for (int i = 0; i < this.attributes.size(); ++i) {
                object2 = this.attributes.get(i);
                serializable = ((FieldPosition)object2).getFieldAttribute();
                ((AttributedString)object).addAttribute((AttributedCharacterIterator.Attribute)serializable, serializable, ((FieldPosition)object2).getBeginIndex(), ((FieldPosition)object2).getEndIndex());
            }
            return ((AttributedString)object).getIterator();
        }
        throw new IllegalArgumentException();
    }

    public CurrencyPluralInfo getCurrencyPluralInfo() {
        CurrencyPluralInfo currencyPluralInfo = null;
        try {
            if (this.currencyPluralInfo != null) {
                currencyPluralInfo = (CurrencyPluralInfo)this.currencyPluralInfo.clone();
            }
            return currencyPluralInfo;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public Currency.CurrencyUsage getCurrencyUsage() {
        return this.currencyUsage;
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        try {
            DecimalFormatSymbols decimalFormatSymbols = (DecimalFormatSymbols)this.symbols.clone();
            return decimalFormatSymbols;
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Deprecated
    @Override
    protected Currency getEffectiveCurrency() {
        Currency currency;
        Currency currency2 = currency = this.getCurrency();
        if (currency == null) {
            currency2 = Currency.getInstance(this.symbols.getInternationalCurrencySymbol());
        }
        return currency2;
    }

    PluralRules.FixedDecimal getFixedDecimal(double d) {
        return this.getFixedDecimal(d, this.digitList);
    }

    PluralRules.FixedDecimal getFixedDecimal(double d, DigitList_Android digitList_Android) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5 = digitList_Android.count - digitList_Android.decimalAt;
        if (this.useSignificantDigits) {
            n3 = this.maxSignificantDigits - digitList_Android.decimalAt;
            n = n2 = this.minSignificantDigits - digitList_Android.decimalAt;
            if (n2 < 0) {
                n = 0;
            }
            n2 = n3;
            n4 = n;
            if (n3 < 0) {
                n2 = 0;
                n4 = n;
            }
        } else {
            n2 = this.getMaximumFractionDigits();
            n4 = this.getMinimumFractionDigits();
        }
        if ((n3 = n5) < n4) {
            n = n4;
        } else {
            n = n3;
            if (n3 > n2) {
                n = n2;
            }
        }
        long l = 0L;
        if (n > 0) {
            for (n2 = Math.max((int)0, (int)digitList_Android.decimalAt); n2 < digitList_Android.count; ++n2) {
                l = l * 10L + (long)(digitList_Android.digits[n2] - 48);
            }
            for (n2 = n; n2 < n5; ++n2) {
                l *= 10L;
            }
        } else {
            l = 0L;
        }
        return new PluralRules.FixedDecimal(d, n, l);
    }

    public int getFormatWidth() {
        return this.formatWidth;
    }

    public int getGroupingSize() {
        return this.groupingSize;
    }

    public MathContext getMathContext() {
        MathContext mathContext = null;
        try {
            if (this.mathContext != null) {
                mathContext = new MathContext(this.mathContext.getDigits(), RoundingMode.valueOf(this.mathContext.getRoundingMode()));
            }
            return mathContext;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public android.icu.math.MathContext getMathContextICU() {
        return this.mathContext;
    }

    public int getMaximumSignificantDigits() {
        return this.maxSignificantDigits;
    }

    public byte getMinimumExponentDigits() {
        return this.minExponentDigits;
    }

    public int getMinimumSignificantDigits() {
        return this.minSignificantDigits;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public String getNegativePrefix() {
        return this.negativePrefix;
    }

    public String getNegativeSuffix() {
        return this.negativeSuffix;
    }

    public char getPadCharacter() {
        return this.pad;
    }

    public int getPadPosition() {
        return this.padPosition;
    }

    public int getParseMaxDigits() {
        return this.PARSE_MAX_EXPONENT;
    }

    public String getPositivePrefix() {
        return this.positivePrefix;
    }

    public String getPositiveSuffix() {
        return this.positiveSuffix;
    }

    public java.math.BigDecimal getRoundingIncrement() {
        BigDecimal bigDecimal = this.roundingIncrementICU;
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.toBigDecimal();
    }

    @Override
    public int getRoundingMode() {
        return this.roundingMode;
    }

    public int getSecondaryGroupingSize() {
        return this.groupingSize2;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 37 + this.positivePrefix.hashCode();
    }

    public boolean isDecimalPatternMatchRequired() {
        return this.parseRequireDecimalPoint;
    }

    public boolean isDecimalSeparatorAlwaysShown() {
        return this.decimalSeparatorAlwaysShown;
    }

    public boolean isExponentSignAlwaysShown() {
        return this.exponentSignAlwaysShown;
    }

    @Deprecated
    boolean isNumberNegative(double d) {
        if (Double.isNaN(d)) {
            return false;
        }
        return this.isNegative(this.multiply(d));
    }

    public boolean isParseBigDecimal() {
        return this.parseBigDecimal;
    }

    public boolean isScientificNotation() {
        return this.useExponentialNotation;
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        return (Number)this.parse(string, parsePosition, null);
    }

    @Override
    public CurrencyAmount parseCurrency(CharSequence charSequence, ParsePosition parsePosition) {
        Currency[] arrcurrency = new Currency[1];
        return (CurrencyAmount)this.parse(charSequence.toString(), parsePosition, arrcurrency);
    }

    @Override
    public void setCurrency(Currency currency) {
        super.setCurrency(currency);
        if (currency != null) {
            String string = currency.getName(this.symbols.getULocale(), 0, null);
            this.symbols.setCurrency(currency);
            this.symbols.setCurrencySymbol(string);
        }
        if (this.currencySignCount != 0) {
            if (currency != null) {
                this.setRoundingIncrement(currency.getRoundingIncrement(this.currencyUsage));
                int n = currency.getDefaultFractionDigits(this.currencyUsage);
                this.setMinimumFractionDigits(n);
                this.setMaximumFractionDigits(n);
            }
            if (this.currencySignCount != 3) {
                this.expandAffixes(null);
            }
        }
    }

    public void setCurrencyPluralInfo(CurrencyPluralInfo currencyPluralInfo) {
        this.currencyPluralInfo = (CurrencyPluralInfo)currencyPluralInfo.clone();
        this.isReadyForParsing = false;
    }

    public void setCurrencyUsage(Currency.CurrencyUsage object) {
        if (object != null) {
            this.currencyUsage = object;
            object = this.getCurrency();
            if (object != null) {
                this.setRoundingIncrement(((Currency)object).getRoundingIncrement(this.currencyUsage));
                int n = ((Currency)object).getDefaultFractionDigits(this.currencyUsage);
                this.setMinimumFractionDigits(n);
                this._setMaximumFractionDigits(n);
            }
            return;
        }
        throw new NullPointerException("return value is null at method AAA");
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        this.symbols = (DecimalFormatSymbols)decimalFormatSymbols.clone();
        this.setCurrencyForSymbols();
        this.expandAffixes(null);
    }

    public void setDecimalPatternMatchRequired(boolean bl) {
        this.parseRequireDecimalPoint = bl;
    }

    public void setDecimalSeparatorAlwaysShown(boolean bl) {
        this.decimalSeparatorAlwaysShown = bl;
    }

    public void setExponentSignAlwaysShown(boolean bl) {
        this.exponentSignAlwaysShown = bl;
    }

    public void setFormatWidth(int n) {
        if (n >= 0) {
            this.formatWidth = n;
            return;
        }
        throw new IllegalArgumentException("Illegal format width");
    }

    public void setGroupingSize(int n) {
        this.groupingSize = (byte)n;
    }

    public void setMathContext(MathContext mathContext) {
        this.mathContext = new android.icu.math.MathContext(mathContext.getPrecision(), 1, false, mathContext.getRoundingMode().ordinal());
    }

    public void setMathContextICU(android.icu.math.MathContext mathContext) {
        this.mathContext = mathContext;
    }

    @Override
    public void setMaximumFractionDigits(int n) {
        this._setMaximumFractionDigits(n);
        this.resetActualRounding();
    }

    @Override
    public void setMaximumIntegerDigits(int n) {
        super.setMaximumIntegerDigits(Math.min(n, 2000000000));
    }

    public void setMaximumSignificantDigits(int n) {
        int n2 = n;
        if (n < 1) {
            n2 = 1;
        }
        this.minSignificantDigits = Math.min(this.minSignificantDigits, n2);
        this.maxSignificantDigits = n2;
        this.setSignificantDigitsUsed(true);
    }

    public void setMinimumExponentDigits(byte by) {
        if (by >= 1) {
            this.minExponentDigits = by;
            return;
        }
        throw new IllegalArgumentException("Exponent digits must be >= 1");
    }

    @Override
    public void setMinimumFractionDigits(int n) {
        super.setMinimumFractionDigits(Math.min(n, 340));
    }

    @Override
    public void setMinimumIntegerDigits(int n) {
        super.setMinimumIntegerDigits(Math.min(n, 309));
    }

    public void setMinimumSignificantDigits(int n) {
        int n2 = n;
        if (n < 1) {
            n2 = 1;
        }
        n = Math.max(this.maxSignificantDigits, n2);
        this.minSignificantDigits = n2;
        this.maxSignificantDigits = n;
        this.setSignificantDigitsUsed(true);
    }

    public void setMultiplier(int n) {
        if (n != 0) {
            this.multiplier = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad multiplier: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setNegativePrefix(String string) {
        this.negativePrefix = string;
        this.negPrefixPattern = null;
    }

    public void setNegativeSuffix(String string) {
        this.negativeSuffix = string;
        this.negSuffixPattern = null;
    }

    public void setPadCharacter(char c) {
        this.pad = c;
    }

    public void setPadPosition(int n) {
        if (n >= 0 && n <= 3) {
            this.padPosition = n;
            return;
        }
        throw new IllegalArgumentException("Illegal pad position");
    }

    public void setParseBigDecimal(boolean bl) {
        this.parseBigDecimal = bl;
    }

    public void setParseMaxDigits(int n) {
        if (n > 0) {
            this.PARSE_MAX_EXPONENT = n;
        }
    }

    public void setPositivePrefix(String string) {
        this.positivePrefix = string;
        this.posPrefixPattern = null;
    }

    public void setPositiveSuffix(String string) {
        this.positiveSuffix = string;
        this.posSuffixPattern = null;
    }

    public void setRoundingIncrement(double d) {
        if (!(d < 0.0)) {
            if (d == 0.0) {
                this.setInternalRoundingIncrement(null);
            } else {
                this.setInternalRoundingIncrement(BigDecimal.valueOf(d));
            }
            this.resetActualRounding();
            return;
        }
        throw new IllegalArgumentException("Illegal rounding increment");
    }

    public void setRoundingIncrement(BigDecimal bigDecimal) {
        int n = bigDecimal == null ? 0 : bigDecimal.compareTo(BigDecimal.ZERO);
        if (n >= 0) {
            if (n == 0) {
                this.setInternalRoundingIncrement(null);
            } else {
                this.setInternalRoundingIncrement(bigDecimal);
            }
            this.resetActualRounding();
            return;
        }
        throw new IllegalArgumentException("Illegal rounding increment");
    }

    public void setRoundingIncrement(java.math.BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            this.setRoundingIncrement((BigDecimal)null);
        } else {
            this.setRoundingIncrement(new BigDecimal(bigDecimal));
        }
    }

    @Override
    public void setRoundingMode(int n) {
        if (n >= 0 && n <= 7) {
            this.roundingMode = n;
            this.resetActualRounding();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid rounding mode: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setScientificNotation(boolean bl) {
        this.useExponentialNotation = bl;
    }

    public void setSecondaryGroupingSize(int n) {
        this.groupingSize2 = (byte)n;
    }

    public void setSignificantDigitsUsed(boolean bl) {
        this.useSignificantDigits = bl;
    }

    @Deprecated
    DigitList_Android toDigitList(double d) {
        DigitList_Android digitList_Android = new DigitList_Android();
        digitList_Android.set(d, this.precision(false), false);
        return digitList_Android;
    }

    public String toLocalizedPattern() {
        if (this.style == 6) {
            return this.formatPattern;
        }
        return this.toPattern(true);
    }

    public String toPattern() {
        if (this.style == 6) {
            return this.formatPattern;
        }
        return this.toPattern(false);
    }

    private static final class AffixForCurrency {
        private String negPrefixPatternForCurrency = null;
        private String negSuffixPatternForCurrency = null;
        private final int patternType;
        private String posPrefixPatternForCurrency = null;
        private String posSuffixPatternForCurrency = null;

        public AffixForCurrency(String string, String string2, String string3, String string4, int n) {
            this.negPrefixPatternForCurrency = string;
            this.negSuffixPatternForCurrency = string2;
            this.posPrefixPatternForCurrency = string3;
            this.posSuffixPatternForCurrency = string4;
            this.patternType = n;
        }

        public String getNegPrefix() {
            return this.negPrefixPatternForCurrency;
        }

        public String getNegSuffix() {
            return this.negSuffixPatternForCurrency;
        }

        public int getPatternType() {
            return this.patternType;
        }

        public String getPosPrefix() {
            return this.posPrefixPatternForCurrency;
        }

        public String getPosSuffix() {
            return this.posSuffixPatternForCurrency;
        }
    }

    static class Unit {
        private final String prefix;
        private final String suffix;

        public Unit(String string, String string2) {
            this.prefix = string;
            this.suffix = string2;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Unit)) {
                return false;
            }
            object = (Unit)object;
            if (!this.prefix.equals(((Unit)object).prefix) || !this.suffix.equals(((Unit)object).suffix)) {
                bl = false;
            }
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.prefix);
            stringBuilder.append("/");
            stringBuilder.append(this.suffix);
            return stringBuilder.toString();
        }

        public void writePrefix(StringBuffer stringBuffer) {
            stringBuffer.append(this.prefix);
        }

        public void writeSuffix(StringBuffer stringBuffer) {
            stringBuffer.append(this.suffix);
        }
    }

}

