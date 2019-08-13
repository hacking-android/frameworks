/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.CurrencyPluralInfoAffixProvider;
import android.icu.impl.number.CustomSymbolCurrency;
import android.icu.impl.number.DecimalFormatProperties;
import android.icu.impl.number.Grouper;
import android.icu.impl.number.MacroProps;
import android.icu.impl.number.Padder;
import android.icu.impl.number.PatternStringParser;
import android.icu.impl.number.PropertiesAffixPatternProvider;
import android.icu.impl.number.RoundingUtils;
import android.icu.number.CompactNotation;
import android.icu.number.CurrencyPrecision;
import android.icu.number.FractionPrecision;
import android.icu.number.IntegerWidth;
import android.icu.number.Notation;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.number.Scale;
import android.icu.number.ScientificNotation;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.text.CompactDecimalFormat;
import android.icu.text.CurrencyPluralInfo;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.PluralRules;
import android.icu.util.Currency;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;

final class NumberPropertyMapper {
    NumberPropertyMapper() {
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties cloneable, DecimalFormatSymbols decimalFormatSymbols) {
        cloneable = NumberPropertyMapper.oldToNew(cloneable, decimalFormatSymbols, null);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros((MacroProps)cloneable);
    }

    public static UnlocalizedNumberFormatter create(DecimalFormatProperties cloneable, DecimalFormatSymbols decimalFormatSymbols, DecimalFormatProperties decimalFormatProperties) {
        cloneable = NumberPropertyMapper.oldToNew(cloneable, decimalFormatSymbols, decimalFormatProperties);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros((MacroProps)cloneable);
    }

    public static UnlocalizedNumberFormatter create(String string, DecimalFormatSymbols decimalFormatSymbols) {
        return NumberPropertyMapper.create(PatternStringParser.parseToProperties(string), decimalFormatSymbols);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static MacroProps oldToNew(DecimalFormatProperties object, DecimalFormatSymbols object2, DecimalFormatProperties decimalFormatProperties) {
        int n;
        void var1_11;
        void var1_14;
        void var1_17;
        void var2_21;
        int n2;
        void var1_4;
        MacroProps macroProps = new MacroProps();
        Serializable serializable = ((DecimalFormatSymbols)object2).getULocale();
        macroProps.symbols = object2;
        Serializable serializable2 = ((DecimalFormatProperties)object).getPluralRules();
        Object object3 = serializable2;
        if (serializable2 == null) {
            object3 = serializable2;
            if (((DecimalFormatProperties)object).getCurrencyPluralInfo() != null) {
                object3 = ((DecimalFormatProperties)object).getCurrencyPluralInfo().getPluralRules();
            }
        }
        macroProps.rules = object3;
        object3 = ((DecimalFormatProperties)object).getCurrencyPluralInfo() == null ? new PropertiesAffixPatternProvider((DecimalFormatProperties)object) : new CurrencyPluralInfoAffixProvider(((DecimalFormatProperties)object).getCurrencyPluralInfo(), (DecimalFormatProperties)object);
        macroProps.affixProvider = object3;
        int n3 = ((DecimalFormatProperties)object).getCurrency() == null && ((DecimalFormatProperties)object).getCurrencyPluralInfo() == null && ((DecimalFormatProperties)object).getCurrencyUsage() == null && !object3.hasCurrencySign() ? 0 : 1;
        serializable2 = CustomSymbolCurrency.resolve(((DecimalFormatProperties)object).getCurrency(), (ULocale)serializable, (DecimalFormatSymbols)object2);
        Currency.CurrencyUsage currencyUsage = ((DecimalFormatProperties)object).getCurrencyUsage();
        int n4 = currencyUsage != null ? 1 : 0;
        if (n4 == 0) {
            Currency.CurrencyUsage currencyUsage2 = Currency.CurrencyUsage.STANDARD;
        }
        if (n3 != 0) {
            macroProps.unit = serializable2;
        }
        int n5 = ((DecimalFormatProperties)object).getMaximumIntegerDigits();
        int n6 = ((DecimalFormatProperties)object).getMinimumIntegerDigits();
        int n7 = ((DecimalFormatProperties)object).getMaximumFractionDigits();
        int n8 = ((DecimalFormatProperties)object).getMinimumFractionDigits();
        int n9 = ((DecimalFormatProperties)object).getMinimumSignificantDigits();
        int n10 = ((DecimalFormatProperties)object).getMaximumSignificantDigits();
        object3 = ((DecimalFormatProperties)object).getRoundingIncrement();
        serializable = RoundingUtils.getMathContextOrUnlimited((DecimalFormatProperties)object);
        int n11 = n8 == -1 && n7 == -1 ? 0 : 1;
        int n12 = n9 == -1 && n10 == -1 ? 0 : 1;
        if (n3 != 0) {
            if (n8 == -1 && n7 == -1) {
                n8 = ((Currency)serializable2).getDefaultFractionDigits((Currency.CurrencyUsage)var1_4);
                n7 = ((Currency)serializable2).getDefaultFractionDigits((Currency.CurrencyUsage)var1_4);
            } else if (n8 == -1) {
                n8 = Math.min(n7, ((Currency)serializable2).getDefaultFractionDigits((Currency.CurrencyUsage)var1_4));
            } else if (n7 == -1) {
                n7 = Math.max(n8, ((Currency)serializable2).getDefaultFractionDigits((Currency.CurrencyUsage)var1_4));
            }
        }
        if (n6 == 0 && n7 != 0) {
            n2 = n8 <= 0 ? 1 : n8;
            if (n7 < 0) {
                n7 = -1;
            } else if (n7 < n2) {
                n7 = n2;
            }
            n8 = n7;
            n = 0;
            n7 = n5 < 0 || n5 > 999 ? -1 : n5;
            n5 = n8;
            n8 = n7;
            n7 = n;
        } else {
            n2 = n8 < 0 ? 0 : n8;
            if (n7 < 0) {
                n7 = -1;
            } else if (n7 < n2) {
                n7 = n2;
            }
            n = n7;
            n8 = n6 <= 0 || n6 > 999 ? 1 : n6;
            n7 = n5 < 0 ? -1 : (n5 < n8 ? n8 : (n5 > 999 ? -1 : n5));
            n5 = n7;
            n7 = n8;
            n8 = n5;
            n5 = n;
        }
        if (n4 != 0) {
            Precision precision = Precision.constructCurrency((Currency.CurrencyUsage)var1_4).withCurrency((Currency)serializable2);
        } else if (object3 != null) {
            Precision precision = Precision.constructIncrement((BigDecimal)object3);
        } else if (n12 != 0) {
            n3 = n9 < 1 ? 1 : (n9 > 999 ? 999 : n9);
            if (n10 < 0) {
                n10 = 999;
            } else if (n10 < n3) {
                n10 = n3;
            } else {
                n9 = 999;
                if (n10 > 999) {
                    n10 = n9;
                }
            }
            Precision precision = Precision.constructSignificant(n3, n10);
            n9 = n3;
        } else if (n11 != 0) {
            FractionPrecision fractionPrecision = Precision.constructFraction(n2, n5);
        } else if (n3 != 0) {
            CurrencyPrecision currencyPrecision = Precision.constructCurrency((Currency.CurrencyUsage)var1_4);
        } else {
            Object var1_10 = null;
        }
        object3 = var1_11;
        if (var1_11 != null) {
            macroProps.precision = object3 = var1_11.withMode((MathContext)serializable);
        }
        macroProps.integerWidth = IntegerWidth.zeroFillTo(n7).truncateAt(n8);
        macroProps.grouping = Grouper.forProperties((DecimalFormatProperties)object);
        if (((DecimalFormatProperties)object).getFormatWidth() != -1) {
            macroProps.padder = Padder.forProperties((DecimalFormatProperties)object);
        }
        if (((DecimalFormatProperties)object).getDecimalSeparatorAlwaysShown()) {
            NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay = NumberFormatter.DecimalSeparatorDisplay.ALWAYS;
        } else {
            NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay = NumberFormatter.DecimalSeparatorDisplay.AUTO;
        }
        macroProps.decimal = var1_14;
        if (((DecimalFormatProperties)object).getSignAlwaysShown()) {
            NumberFormatter.SignDisplay signDisplay = NumberFormatter.SignDisplay.ALWAYS;
        } else {
            NumberFormatter.SignDisplay signDisplay = NumberFormatter.SignDisplay.AUTO;
        }
        macroProps.sign = var1_17;
        if (((DecimalFormatProperties)object).getMinimumExponentDigits() != -1) {
            void var1_20;
            if (n8 > 8) {
                macroProps.integerWidth = IntegerWidth.zeroFillTo(n7).truncateAt(n7);
                n3 = n7;
                n4 = n7;
            } else {
                n3 = n8;
                n4 = n7;
                if (n8 > n7) {
                    n3 = n8;
                    n4 = n7;
                    if (n7 > 1) {
                        n4 = 1;
                        macroProps.integerWidth = IntegerWidth.zeroFillTo(1).truncateAt(n8);
                        n3 = n8;
                    }
                }
            }
            n7 = n3 < 0 ? -1 : n3;
            boolean bl = n7 == n4;
            n8 = ((DecimalFormatProperties)object).getMinimumExponentDigits();
            if (((DecimalFormatProperties)object).getExponentSignAlwaysShown()) {
                NumberFormatter.SignDisplay signDisplay = NumberFormatter.SignDisplay.ALWAYS;
            } else {
                NumberFormatter.SignDisplay signDisplay = NumberFormatter.SignDisplay.AUTO;
            }
            macroProps.notation = new ScientificNotation(n7, bl, n8, (NumberFormatter.SignDisplay)var1_20);
            if (macroProps.precision instanceof FractionPrecision) {
                n = ((DecimalFormatProperties)object).getMaximumIntegerDigits();
                n8 = ((DecimalFormatProperties)object).getMinimumIntegerDigits();
                n12 = ((DecimalFormatProperties)object).getMinimumFractionDigits();
                n11 = ((DecimalFormatProperties)object).getMaximumFractionDigits();
                if (n8 == 0 && n11 == 0) {
                    macroProps.precision = Precision.constructInfinite().withMode((MathContext)serializable);
                } else if (n8 == 0 && n12 == 0) {
                    macroProps.precision = Precision.constructSignificant(1, n11 + 1).withMode((MathContext)serializable);
                } else {
                    n7 = n8;
                    if (n > n8) {
                        n7 = n8;
                        if (n8 > 1) {
                            n7 = 1;
                        }
                    }
                    macroProps.precision = Precision.constructSignificant(n7 + n12, n8 + n11).withMode((MathContext)serializable);
                }
            }
            n8 = n3;
            n7 = n4;
        }
        if (((DecimalFormatProperties)object).getCompactStyle() != null) {
            macroProps.notation = ((DecimalFormatProperties)object).getCompactCustomData() != null ? new CompactNotation(((DecimalFormatProperties)object).getCompactCustomData()) : (((DecimalFormatProperties)object).getCompactStyle() == CompactDecimalFormat.CompactStyle.LONG ? Notation.compactLong() : Notation.compactShort());
            macroProps.affixProvider = null;
        }
        macroProps.scale = RoundingUtils.scaleFromProperties((DecimalFormatProperties)object);
        if (var2_21 == null) return macroProps;
        var2_21.setCurrency((Currency)serializable2);
        var2_21.setMathContext((MathContext)serializable);
        var2_21.setRoundingMode(((MathContext)serializable).getRoundingMode());
        var2_21.setMinimumIntegerDigits(n7);
        if (n8 == -1) {
            n8 = Integer.MAX_VALUE;
        }
        var2_21.setMaximumIntegerDigits(n8);
        object = object3 instanceof CurrencyPrecision ? ((CurrencyPrecision)object3).withCurrency((Currency)serializable2) : object3;
        n7 = n5;
        n8 = n10;
        if (object instanceof Precision.FractionRounderImpl) {
            n10 = ((Precision.FractionRounderImpl)object).minFrac;
            n7 = ((Precision.FractionRounderImpl)object).maxFrac;
            object = null;
        } else if (object instanceof Precision.IncrementRounderImpl) {
            object = ((Precision.IncrementRounderImpl)object).increment;
            n10 = ((BigDecimal)object).scale();
            n7 = ((BigDecimal)object).scale();
        } else if (object instanceof Precision.SignificantRounderImpl) {
            n9 = ((Precision.SignificantRounderImpl)object).minSig;
            n8 = ((Precision.SignificantRounderImpl)object).maxSig;
            object = null;
            n10 = n2;
        } else {
            object = null;
            n10 = n2;
        }
        var2_21.setMinimumFractionDigits(n10);
        var2_21.setMaximumFractionDigits(n7);
        var2_21.setMinimumSignificantDigits(n9);
        var2_21.setMaximumSignificantDigits(n8);
        var2_21.setRoundingIncrement((BigDecimal)object);
        return macroProps;
    }
}

