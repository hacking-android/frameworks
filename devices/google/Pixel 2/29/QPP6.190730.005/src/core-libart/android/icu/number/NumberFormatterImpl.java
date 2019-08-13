/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.CurrencyData;
import android.icu.impl.StandardPlural;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.CompactData;
import android.icu.impl.number.ConstantAffixModifier;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.impl.number.Grouper;
import android.icu.impl.number.LongNameHandler;
import android.icu.impl.number.MacroProps;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.MultiplierFormatHandler;
import android.icu.impl.number.MutablePatternModifier;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.impl.number.Padder;
import android.icu.impl.number.PatternStringParser;
import android.icu.number.CompactNotation;
import android.icu.number.IntegerWidth;
import android.icu.number.Notation;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.number.Scale;
import android.icu.number.ScientificNotation;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;
import android.icu.text.NumberingSystem;
import android.icu.text.PluralRules;
import android.icu.util.Currency;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import java.math.RoundingMode;

class NumberFormatterImpl {
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("XXX");
    final MicroPropsGenerator microPropsGenerator;

    public NumberFormatterImpl(MacroProps macroProps) {
        this(NumberFormatterImpl.macrosToMicroGenerator(macroProps, true));
    }

    private NumberFormatterImpl(MicroPropsGenerator microPropsGenerator) {
        this.microPropsGenerator = microPropsGenerator;
    }

    public static int formatStatic(MacroProps cloneable, DecimalQuantity decimalQuantity, NumberStringBuilder numberStringBuilder) {
        cloneable = NumberFormatterImpl.preProcessUnsafe(cloneable, decimalQuantity);
        int n = NumberFormatterImpl.writeNumber((MicroProps)cloneable, decimalQuantity, numberStringBuilder, 0);
        return n + NumberFormatterImpl.writeAffixes((MicroProps)cloneable, numberStringBuilder, 0, n);
    }

    private static int getPrefixSuffixImpl(MicroPropsGenerator microPropsGenerator, byte by, NumberStringBuilder numberStringBuilder) {
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(0);
        if (by < 0) {
            decimalQuantity_DualStorageBCD.negate();
        }
        microPropsGenerator = microPropsGenerator.processQuantity(decimalQuantity_DualStorageBCD);
        ((MicroProps)microPropsGenerator).modMiddle.apply(numberStringBuilder, 0, 0);
        return ((MicroProps)microPropsGenerator).modMiddle.getPrefixLength();
    }

    public static int getPrefixSuffixStatic(MacroProps macroProps, byte by, StandardPlural standardPlural, NumberStringBuilder numberStringBuilder) {
        return NumberFormatterImpl.getPrefixSuffixImpl(NumberFormatterImpl.macrosToMicroGenerator(macroProps, false), by, numberStringBuilder);
    }

    private static MicroPropsGenerator macrosToMicroGenerator(MacroProps object, boolean bl) {
        Object object2;
        Object object3;
        MicroProps microProps = new MicroProps(bl);
        Object object4 = microProps;
        boolean bl2 = NumberFormatterImpl.unitIsCurrency(((MacroProps)object).unit);
        boolean bl3 = NumberFormatterImpl.unitIsNoUnit(((MacroProps)object).unit);
        int n = bl3 && NumberFormatterImpl.unitIsPercent(((MacroProps)object).unit) ? 1 : 0;
        boolean bl4 = bl3 && NumberFormatterImpl.unitIsPermille(((MacroProps)object).unit);
        boolean bl5 = !bl2 && !bl3;
        boolean bl6 = ((MacroProps)object).sign == NumberFormatter.SignDisplay.ACCOUNTING || ((MacroProps)object).sign == NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS || ((MacroProps)object).sign == NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
        Object object5 = bl2 ? (Currency)((MacroProps)object).unit : DEFAULT_CURRENCY;
        Object object6 = NumberFormatter.UnitWidth.SHORT;
        if (((MacroProps)object).unitWidth != null) {
            object6 = ((MacroProps)object).unitWidth;
        }
        Object object7 = ((MacroProps)object).rules;
        Object object8 = ((MacroProps)object).symbols instanceof NumberingSystem ? (NumberingSystem)((MacroProps)object).symbols : NumberingSystem.getInstance(((MacroProps)object).loc);
        String string = ((NumberingSystem)object8).getName();
        microProps.symbols = ((MacroProps)object).symbols instanceof DecimalFormatSymbols ? (DecimalFormatSymbols)((MacroProps)object).symbols : DecimalFormatSymbols.forNumberingSystem(((MacroProps)object).loc, (NumberingSystem)object8);
        if (bl2 && (object3 = CurrencyData.provider.getInstance(((MacroProps)object).loc, true).getFormatInfo(((Currency)object5).getCurrencyCode())) != null) {
            object8 = ((CurrencyData.CurrencyFormatInfo)object3).currencyPattern;
            microProps.symbols = (DecimalFormatSymbols)microProps.symbols.clone();
            object2 = microProps.symbols;
            ((DecimalFormatSymbols)object2).setMonetaryDecimalSeparatorString(((CurrencyData.CurrencyFormatInfo)object3).monetaryDecimalSeparator);
            microProps.symbols.setMonetaryGroupingSeparatorString(((CurrencyData.CurrencyFormatInfo)object3).monetaryGroupingSeparator);
        } else {
            object8 = null;
        }
        if (object8 == null) {
            n = n == 0 && !bl4 ? (bl2 && object6 != NumberFormatter.UnitWidth.FULL_NAME ? (bl6 ? 7 : 1) : 0) : 2;
            object8 = NumberFormat.getPatternForStyleAndNumberingSystem(((MacroProps)object).loc, string, n);
        }
        object2 = PatternStringParser.parseToPatternInfo((String)object8);
        object8 = ((MacroProps)object).scale != null ? new MultiplierFormatHandler(((MacroProps)object).scale, (MicroPropsGenerator)object4) : object4;
        microProps.rounder = ((MacroProps)object).precision != null ? ((MacroProps)object).precision : (((MacroProps)object).notation instanceof CompactNotation ? Precision.COMPACT_STRATEGY : (bl2 ? Precision.MONETARY_STANDARD : Precision.DEFAULT_MAX_FRAC_6));
        if (((MacroProps)object).roundingMode != null) {
            microProps.rounder = microProps.rounder.withMode(((MacroProps)object).roundingMode);
        }
        microProps.rounder = microProps.rounder.withLocaleData((Currency)object5);
        microProps.grouping = ((MacroProps)object).grouping instanceof Grouper ? (Grouper)((MacroProps)object).grouping : (((MacroProps)object).grouping instanceof NumberFormatter.GroupingStrategy ? Grouper.forStrategy((NumberFormatter.GroupingStrategy)((Object)((MacroProps)object).grouping)) : (((MacroProps)object).notation instanceof CompactNotation ? Grouper.forStrategy(NumberFormatter.GroupingStrategy.MIN2) : Grouper.forStrategy(NumberFormatter.GroupingStrategy.AUTO)));
        microProps.grouping = microProps.grouping.withLocaleData(((MacroProps)object).loc, (PatternStringParser.ParsedPatternInfo)object2);
        microProps.padding = ((MacroProps)object).padder != null ? ((MacroProps)object).padder : Padder.NONE;
        microProps.integerWidth = ((MacroProps)object).integerWidth != null ? ((MacroProps)object).integerWidth : IntegerWidth.DEFAULT;
        microProps.sign = ((MacroProps)object).sign != null ? ((MacroProps)object).sign : NumberFormatter.SignDisplay.AUTO;
        microProps.decimal = ((MacroProps)object).decimal != null ? ((MacroProps)object).decimal : NumberFormatter.DecimalSeparatorDisplay.AUTO;
        microProps.useCurrency = bl2;
        if (((MacroProps)object).notation instanceof ScientificNotation) {
            object4 = ((ScientificNotation)((MacroProps)object).notation).withLocaleData(microProps.symbols, bl, (MicroPropsGenerator)object8);
        } else {
            microProps.modInner = ConstantAffixModifier.EMPTY;
            object4 = object8;
        }
        object3 = new MutablePatternModifier(false);
        object8 = ((MacroProps)object).affixProvider != null ? ((MacroProps)object).affixProvider : object2;
        ((MutablePatternModifier)object3).setPatternInfo((AffixPatternProvider)object8);
        ((MutablePatternModifier)object3).setPatternAttributes(microProps.sign, bl4);
        if (((MutablePatternModifier)object3).needsPlurals()) {
            object8 = object7;
            if (object7 == null) {
                object8 = PluralRules.forLocale(((MacroProps)object).loc);
            }
            ((MutablePatternModifier)object3).setSymbols(microProps.symbols, (Currency)object5, (NumberFormatter.UnitWidth)((Object)object6), (PluralRules)object8);
        } else {
            ((MutablePatternModifier)object3).setSymbols(microProps.symbols, (Currency)object5, (NumberFormatter.UnitWidth)((Object)object6), null);
            object8 = object7;
        }
        object7 = bl ? ((MutablePatternModifier)object3).createImmutableAndChain((MicroPropsGenerator)object4) : ((MutablePatternModifier)object3).addToChain((MicroPropsGenerator)object4);
        if (bl5) {
            if (object8 == null) {
                object8 = PluralRules.forLocale(((MacroProps)object).loc);
            }
            object7 = LongNameHandler.forMeasureUnit(((MacroProps)object).loc, ((MacroProps)object).unit, ((MacroProps)object).perUnit, object6, (PluralRules)object8, (MicroPropsGenerator)object7);
            object6 = object8;
            object8 = object7;
        } else if (bl2 && object6 == NumberFormatter.UnitWidth.FULL_NAME) {
            object6 = object8;
            if (object8 == null) {
                object6 = PluralRules.forLocale(((MacroProps)object).loc);
            }
            object8 = LongNameHandler.forCurrencyLongNames(((MacroProps)object).loc, (Currency)object5, (PluralRules)object6, (MicroPropsGenerator)object7);
        } else {
            microProps.modOuter = ConstantAffixModifier.EMPTY;
            object6 = object8;
            object8 = object7;
        }
        object7 = object8;
        if (((MacroProps)object).notation instanceof CompactNotation) {
            object7 = object6;
            if (object6 == null) {
                object7 = PluralRules.forLocale(((MacroProps)object).loc);
            }
            object6 = ((MacroProps)object).unit instanceof Currency && ((MacroProps)object).unitWidth != NumberFormatter.UnitWidth.FULL_NAME ? CompactData.CompactType.CURRENCY : CompactData.CompactType.DECIMAL;
            object5 = (CompactNotation)((MacroProps)object).notation;
            object4 = ((MacroProps)object).loc;
            object = bl ? object3 : null;
            object7 = ((CompactNotation)object5).withLocaleData((ULocale)object4, string, (CompactData.CompactType)((Object)object6), (PluralRules)object7, (MutablePatternModifier)object, (MicroPropsGenerator)object8);
        }
        return object7;
    }

    private static MicroProps preProcessUnsafe(MacroProps cloneable, DecimalQuantity decimalQuantity) {
        cloneable = NumberFormatterImpl.macrosToMicroGenerator(cloneable, false).processQuantity(decimalQuantity);
        ((MicroProps)cloneable).rounder.apply(decimalQuantity);
        if (cloneable.integerWidth.maxInt == -1) {
            decimalQuantity.setIntegerLength(cloneable.integerWidth.minInt, Integer.MAX_VALUE);
        } else {
            decimalQuantity.setIntegerLength(cloneable.integerWidth.minInt, cloneable.integerWidth.maxInt);
        }
        return cloneable;
    }

    private static boolean unitIsCurrency(MeasureUnit measureUnit) {
        boolean bl = measureUnit != null && "currency".equals(measureUnit.getType());
        return bl;
    }

    private static boolean unitIsNoUnit(MeasureUnit measureUnit) {
        boolean bl = measureUnit == null || "none".equals(measureUnit.getType());
        return bl;
    }

    private static boolean unitIsPercent(MeasureUnit measureUnit) {
        boolean bl = measureUnit != null && "percent".equals(measureUnit.getSubtype());
        return bl;
    }

    private static boolean unitIsPermille(MeasureUnit measureUnit) {
        boolean bl = measureUnit != null && "permille".equals(measureUnit.getSubtype());
        return bl;
    }

    public static int writeAffixes(MicroProps microProps, NumberStringBuilder numberStringBuilder, int n, int n2) {
        int n3 = microProps.modInner.apply(numberStringBuilder, n, n2);
        if (microProps.padding.isValid()) {
            microProps.padding.padAndApply(microProps.modMiddle, microProps.modOuter, numberStringBuilder, n, n2 + n3);
            n = n3;
        } else {
            n3 += microProps.modMiddle.apply(numberStringBuilder, n, n2 + n3);
            n = n3 + microProps.modOuter.apply(numberStringBuilder, n, n2 + n3);
        }
        return n;
    }

    private static int writeFractionDigits(MicroProps microProps, DecimalQuantity decimalQuantity, NumberStringBuilder numberStringBuilder, int n) {
        int n2 = 0;
        int n3 = -decimalQuantity.getLowerDisplayMagnitude();
        for (int i = 0; i < n3; ++i) {
            byte by = decimalQuantity.getDigit(-i - 1);
            if (microProps.symbols.getCodePointZero() != -1) {
                n2 += numberStringBuilder.insertCodePoint(n2 + n, microProps.symbols.getCodePointZero() + by, NumberFormat.Field.FRACTION);
                continue;
            }
            n2 += numberStringBuilder.insert(n2 + n, microProps.symbols.getDigitStringsLocal()[by], NumberFormat.Field.FRACTION);
        }
        return n2;
    }

    private static int writeIntegerDigits(MicroProps microProps, DecimalQuantity decimalQuantity, NumberStringBuilder numberStringBuilder, int n) {
        int n2 = 0;
        int n3 = decimalQuantity.getUpperDisplayMagnitude();
        for (int i = 0; i < n3 + 1; ++i) {
            int n4 = n2;
            if (microProps.grouping.groupAtPosition(i, decimalQuantity)) {
                String string = microProps.useCurrency ? microProps.symbols.getMonetaryGroupingSeparatorString() : microProps.symbols.getGroupingSeparatorString();
                n4 = n2 + numberStringBuilder.insert(n, string, NumberFormat.Field.GROUPING_SEPARATOR);
            }
            n2 = decimalQuantity.getDigit(i);
            n2 = microProps.symbols.getCodePointZero() != -1 ? n4 + numberStringBuilder.insertCodePoint(n, microProps.symbols.getCodePointZero() + n2, NumberFormat.Field.INTEGER) : n4 + numberStringBuilder.insert(n, microProps.symbols.getDigitStringsLocal()[n2], NumberFormat.Field.INTEGER);
        }
        return n2;
    }

    public static int writeNumber(MicroProps microProps, DecimalQuantity decimalQuantity, NumberStringBuilder numberStringBuilder, int n) {
        block3 : {
            int n2;
            block6 : {
                int n3;
                block5 : {
                    block4 : {
                        block2 : {
                            if (!decimalQuantity.isInfinite()) break block2;
                            n = 0 + numberStringBuilder.insert(0 + n, microProps.symbols.getInfinity(), NumberFormat.Field.INTEGER);
                            break block3;
                        }
                        if (!decimalQuantity.isNaN()) break block4;
                        n = 0 + numberStringBuilder.insert(0 + n, microProps.symbols.getNaN(), NumberFormat.Field.INTEGER);
                        break block3;
                    }
                    n3 = 0 + NumberFormatterImpl.writeIntegerDigits(microProps, decimalQuantity, numberStringBuilder, 0 + n);
                    if (decimalQuantity.getLowerDisplayMagnitude() < 0) break block5;
                    n2 = n3;
                    if (microProps.decimal != NumberFormatter.DecimalSeparatorDisplay.ALWAYS) break block6;
                }
                String string = microProps.useCurrency ? microProps.symbols.getMonetaryDecimalSeparatorString() : microProps.symbols.getDecimalSeparatorString();
                n2 = n3 + numberStringBuilder.insert(n3 + n, string, NumberFormat.Field.DECIMAL_SEPARATOR);
            }
            n = n2 + NumberFormatterImpl.writeFractionDigits(microProps, decimalQuantity, numberStringBuilder, n2 + n);
        }
        return n;
    }

    public int format(DecimalQuantity decimalQuantity, NumberStringBuilder numberStringBuilder) {
        MicroProps microProps = this.preProcess(decimalQuantity);
        int n = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, numberStringBuilder, 0);
        return n + NumberFormatterImpl.writeAffixes(microProps, numberStringBuilder, 0, n);
    }

    public int getPrefixSuffix(byte by, StandardPlural standardPlural, NumberStringBuilder numberStringBuilder) {
        return NumberFormatterImpl.getPrefixSuffixImpl(this.microPropsGenerator, by, numberStringBuilder);
    }

    public MicroProps preProcess(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.microPropsGenerator.processQuantity(decimalQuantity);
        microProps.rounder.apply(decimalQuantity);
        if (microProps.integerWidth.maxInt == -1) {
            decimalQuantity.setIntegerLength(microProps.integerWidth.minInt, Integer.MAX_VALUE);
        } else {
            decimalQuantity.setIntegerLength(microProps.integerWidth.minInt, microProps.integerWidth.maxInt);
        }
        return microProps;
    }
}

