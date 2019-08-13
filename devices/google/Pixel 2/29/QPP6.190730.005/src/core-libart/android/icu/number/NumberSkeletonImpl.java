/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.CacheBase;
import android.icu.impl.PatternProps;
import android.icu.impl.SoftCache;
import android.icu.impl.StringSegment;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.MacroProps;
import android.icu.impl.number.Padder;
import android.icu.impl.number.RoundingUtils;
import android.icu.number.CompactNotation;
import android.icu.number.FractionPrecision;
import android.icu.number.IntegerWidth;
import android.icu.number.Notation;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.number.Scale;
import android.icu.number.ScientificNotation;
import android.icu.number.SkeletonSyntaxException;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.text.NumberingSystem;
import android.icu.text.PluralRules;
import android.icu.util.BytesTrie;
import android.icu.util.CharsTrie;
import android.icu.util.CharsTrieBuilder;
import android.icu.util.Currency;
import android.icu.util.MeasureUnit;
import android.icu.util.NoUnit;
import android.icu.util.StringTrieBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;

class NumberSkeletonImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final String SERIALIZED_STEM_TRIE;
    static final StemEnum[] STEM_ENUM_VALUES;
    private static final CacheBase<String, UnlocalizedNumberFormatter, Void> cache;

    static {
        STEM_ENUM_VALUES = StemEnum.values();
        SERIALIZED_STEM_TRIE = NumberSkeletonImpl.buildStemTrie();
        cache = new SoftCache<String, UnlocalizedNumberFormatter, Void>(){

            @Override
            protected UnlocalizedNumberFormatter createInstance(String string, Void void_) {
                return NumberSkeletonImpl.create(string);
            }
        };
    }

    NumberSkeletonImpl() {
    }

    private static void appendMultiple(StringBuilder stringBuilder, int n, int n2) {
        for (int i = 0; i < n2; ++i) {
            stringBuilder.appendCodePoint(n);
        }
    }

    static String buildStemTrie() {
        CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
        charsTrieBuilder.add("compact-short", StemEnum.STEM_COMPACT_SHORT.ordinal());
        charsTrieBuilder.add("compact-long", StemEnum.STEM_COMPACT_LONG.ordinal());
        charsTrieBuilder.add("scientific", StemEnum.STEM_SCIENTIFIC.ordinal());
        charsTrieBuilder.add("engineering", StemEnum.STEM_ENGINEERING.ordinal());
        charsTrieBuilder.add("notation-simple", StemEnum.STEM_NOTATION_SIMPLE.ordinal());
        charsTrieBuilder.add("base-unit", StemEnum.STEM_BASE_UNIT.ordinal());
        charsTrieBuilder.add("percent", StemEnum.STEM_PERCENT.ordinal());
        charsTrieBuilder.add("permille", StemEnum.STEM_PERMILLE.ordinal());
        charsTrieBuilder.add("precision-integer", StemEnum.STEM_PRECISION_INTEGER.ordinal());
        charsTrieBuilder.add("precision-unlimited", StemEnum.STEM_PRECISION_UNLIMITED.ordinal());
        charsTrieBuilder.add("precision-currency-standard", StemEnum.STEM_PRECISION_CURRENCY_STANDARD.ordinal());
        charsTrieBuilder.add("precision-currency-cash", StemEnum.STEM_PRECISION_CURRENCY_CASH.ordinal());
        charsTrieBuilder.add("rounding-mode-ceiling", StemEnum.STEM_ROUNDING_MODE_CEILING.ordinal());
        charsTrieBuilder.add("rounding-mode-floor", StemEnum.STEM_ROUNDING_MODE_FLOOR.ordinal());
        charsTrieBuilder.add("rounding-mode-down", StemEnum.STEM_ROUNDING_MODE_DOWN.ordinal());
        charsTrieBuilder.add("rounding-mode-up", StemEnum.STEM_ROUNDING_MODE_UP.ordinal());
        charsTrieBuilder.add("rounding-mode-half-even", StemEnum.STEM_ROUNDING_MODE_HALF_EVEN.ordinal());
        charsTrieBuilder.add("rounding-mode-half-down", StemEnum.STEM_ROUNDING_MODE_HALF_DOWN.ordinal());
        charsTrieBuilder.add("rounding-mode-half-up", StemEnum.STEM_ROUNDING_MODE_HALF_UP.ordinal());
        charsTrieBuilder.add("rounding-mode-unnecessary", StemEnum.STEM_ROUNDING_MODE_UNNECESSARY.ordinal());
        charsTrieBuilder.add("group-off", StemEnum.STEM_GROUP_OFF.ordinal());
        charsTrieBuilder.add("group-min2", StemEnum.STEM_GROUP_MIN2.ordinal());
        charsTrieBuilder.add("group-auto", StemEnum.STEM_GROUP_AUTO.ordinal());
        charsTrieBuilder.add("group-on-aligned", StemEnum.STEM_GROUP_ON_ALIGNED.ordinal());
        charsTrieBuilder.add("group-thousands", StemEnum.STEM_GROUP_THOUSANDS.ordinal());
        charsTrieBuilder.add("latin", StemEnum.STEM_LATIN.ordinal());
        charsTrieBuilder.add("unit-width-narrow", StemEnum.STEM_UNIT_WIDTH_NARROW.ordinal());
        charsTrieBuilder.add("unit-width-short", StemEnum.STEM_UNIT_WIDTH_SHORT.ordinal());
        charsTrieBuilder.add("unit-width-full-name", StemEnum.STEM_UNIT_WIDTH_FULL_NAME.ordinal());
        charsTrieBuilder.add("unit-width-iso-code", StemEnum.STEM_UNIT_WIDTH_ISO_CODE.ordinal());
        charsTrieBuilder.add("unit-width-hidden", StemEnum.STEM_UNIT_WIDTH_HIDDEN.ordinal());
        charsTrieBuilder.add("sign-auto", StemEnum.STEM_SIGN_AUTO.ordinal());
        charsTrieBuilder.add("sign-always", StemEnum.STEM_SIGN_ALWAYS.ordinal());
        charsTrieBuilder.add("sign-never", StemEnum.STEM_SIGN_NEVER.ordinal());
        charsTrieBuilder.add("sign-accounting", StemEnum.STEM_SIGN_ACCOUNTING.ordinal());
        charsTrieBuilder.add("sign-accounting-always", StemEnum.STEM_SIGN_ACCOUNTING_ALWAYS.ordinal());
        charsTrieBuilder.add("sign-except-zero", StemEnum.STEM_SIGN_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("sign-accounting-except-zero", StemEnum.STEM_SIGN_ACCOUNTING_EXCEPT_ZERO.ordinal());
        charsTrieBuilder.add("decimal-auto", StemEnum.STEM_DECIMAL_AUTO.ordinal());
        charsTrieBuilder.add("decimal-always", StemEnum.STEM_DECIMAL_ALWAYS.ordinal());
        charsTrieBuilder.add("precision-increment", StemEnum.STEM_PRECISION_INCREMENT.ordinal());
        charsTrieBuilder.add("measure-unit", StemEnum.STEM_MEASURE_UNIT.ordinal());
        charsTrieBuilder.add("per-measure-unit", StemEnum.STEM_PER_MEASURE_UNIT.ordinal());
        charsTrieBuilder.add("currency", StemEnum.STEM_CURRENCY.ordinal());
        charsTrieBuilder.add("integer-width", StemEnum.STEM_INTEGER_WIDTH.ordinal());
        charsTrieBuilder.add("numbering-system", StemEnum.STEM_NUMBERING_SYSTEM.ordinal());
        charsTrieBuilder.add("scale", StemEnum.STEM_SCALE.ordinal());
        return charsTrieBuilder.buildCharSequence(StringTrieBuilder.Option.FAST).toString();
    }

    private static void checkNull(Object object, CharSequence charSequence) {
        if (object == null) {
            return;
        }
        throw new SkeletonSyntaxException("Duplicated setting", charSequence);
    }

    public static UnlocalizedNumberFormatter create(String object) {
        object = NumberSkeletonImpl.parseSkeleton((String)object);
        return (UnlocalizedNumberFormatter)NumberFormatter.with().macros((MacroProps)object);
    }

    public static String generate(MacroProps macroProps) {
        StringBuilder stringBuilder = new StringBuilder();
        NumberSkeletonImpl.generateSkeleton(macroProps, stringBuilder);
        return stringBuilder.toString();
    }

    private static void generateSkeleton(MacroProps macroProps, StringBuilder stringBuilder) {
        if (macroProps.notation != null && GeneratorHelpers.notation(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.unit != null && GeneratorHelpers.unit(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.perUnit != null && GeneratorHelpers.perUnit(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.precision != null && GeneratorHelpers.precision(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.roundingMode != null && GeneratorHelpers.roundingMode(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.grouping != null && GeneratorHelpers.grouping(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.integerWidth != null && GeneratorHelpers.integerWidth(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.symbols != null && GeneratorHelpers.symbols(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.unitWidth != null && GeneratorHelpers.unitWidth(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.sign != null && GeneratorHelpers.sign(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.decimal != null && GeneratorHelpers.decimal(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.scale != null && GeneratorHelpers.scale(macroProps, stringBuilder)) {
            stringBuilder.append(' ');
        }
        if (macroProps.padder == null) {
            if (macroProps.affixProvider == null) {
                if (macroProps.rules == null) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.setLength(stringBuilder.length() - 1);
                    }
                    return;
                }
                throw new UnsupportedOperationException("Cannot generate number skeleton with custom plural rules");
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom affix provider");
        }
        throw new UnsupportedOperationException("Cannot generate number skeleton with custom padder");
    }

    public static UnlocalizedNumberFormatter getOrCreate(String string) {
        return cache.getInstance(string, null);
    }

    private static ParseState parseOption(ParseState parseState, StringSegment stringSegment, MacroProps macroProps) {
        block12 : {
            switch (parseState) {
                default: {
                    if (2.$SwitchMap$android$icu$number$NumberSkeletonImpl$ParseState[parseState.ordinal()] == 8) break;
                    break block12;
                }
                case STATE_SCALE: {
                    BlueprintHelpers.parseScaleOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
                case STATE_NUMBERING_SYSTEM: {
                    BlueprintHelpers.parseNumberingSystemOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
                case STATE_INTEGER_WIDTH: {
                    BlueprintHelpers.parseIntegerWidthOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
                case STATE_CURRENCY_UNIT: {
                    BlueprintHelpers.parseCurrencyOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
                case STATE_PER_MEASURE_UNIT: {
                    BlueprintHelpers.parseMeasurePerUnitOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
                case STATE_MEASURE_UNIT: {
                    BlueprintHelpers.parseMeasureUnitOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
                case STATE_INCREMENT_PRECISION: {
                    BlueprintHelpers.parseIncrementOption(stringSegment, macroProps);
                    return ParseState.STATE_NULL;
                }
            }
            if (BlueprintHelpers.parseExponentWidthOption(stringSegment, macroProps)) {
                return ParseState.STATE_SCIENTIFIC;
            }
            if (BlueprintHelpers.parseExponentSignOption(stringSegment, macroProps)) {
                return ParseState.STATE_SCIENTIFIC;
            }
        }
        if (2.$SwitchMap$android$icu$number$NumberSkeletonImpl$ParseState[parseState.ordinal()] == 9 && BlueprintHelpers.parseFracSigOption(stringSegment, macroProps)) {
            return ParseState.STATE_NULL;
        }
        throw new SkeletonSyntaxException("Invalid option", stringSegment);
    }

    private static MacroProps parseSkeleton(String object) {
        Object object2 = new StringBuilder();
        object2.append((String)object);
        object2.append(" ");
        object = object2.toString();
        MacroProps macroProps = new MacroProps();
        StringSegment stringSegment = new StringSegment((String)object, false);
        CharsTrie charsTrie = new CharsTrie(SERIALIZED_STEM_TRIE, 0);
        object = ParseState.STATE_NULL;
        int n = 0;
        while (n < stringSegment.length()) {
            int n2;
            block15 : {
                int n3;
                boolean bl;
                block14 : {
                    block13 : {
                        n2 = stringSegment.codePointAt(n);
                        bl = PatternProps.isWhiteSpace(n2);
                        n3 = n2 == 47 ? 1 : 0;
                        if (!bl && n3 == 0) {
                            n = n3 = n + Character.charCount(n2);
                            if (object != ParseState.STATE_NULL) continue;
                            charsTrie.nextForCodePoint(n2);
                            n = n3;
                            continue;
                        }
                        if (n == 0) break block13;
                        stringSegment.setLength(n);
                        if (object == ParseState.STATE_NULL) {
                            object = NumberSkeletonImpl.parseStem(stringSegment, charsTrie, macroProps);
                            charsTrie.reset();
                        } else {
                            object = NumberSkeletonImpl.parseOption((ParseState)((Object)object), stringSegment, macroProps);
                        }
                        stringSegment.resetLength();
                        stringSegment.adjustOffset(n);
                        n = 0;
                        break block14;
                    }
                    if (object != ParseState.STATE_NULL) break block15;
                }
                if (n3 != 0 && object == ParseState.STATE_NULL) {
                    stringSegment.setLength(Character.charCount(n2));
                    throw new SkeletonSyntaxException("Unexpected option separator", stringSegment);
                }
                object2 = object;
                if (bl) {
                    object2 = object;
                    if (object != ParseState.STATE_NULL) {
                        switch (2.$SwitchMap$android$icu$number$NumberSkeletonImpl$ParseState[((Enum)object).ordinal()]) {
                            default: {
                                object2 = ParseState.STATE_NULL;
                                break;
                            }
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: {
                                stringSegment.setLength(Character.charCount(n2));
                                throw new SkeletonSyntaxException("Stem requires an option", stringSegment);
                            }
                        }
                    }
                }
                stringSegment.adjustOffset(Character.charCount(n2));
                object = object2;
                continue;
            }
            stringSegment.setLength(Character.charCount(n2));
            throw new SkeletonSyntaxException("Unexpected separator character", stringSegment);
        }
        return macroProps;
    }

    private static ParseState parseStem(StringSegment stringSegment, CharsTrie object, MacroProps macroProps) {
        int n = stringSegment.charAt(0);
        if (n != 46) {
            if (n != 64) {
                BytesTrie.Result result = ((CharsTrie)object).current();
                if (result != BytesTrie.Result.INTERMEDIATE_VALUE && result != BytesTrie.Result.FINAL_VALUE) {
                    throw new SkeletonSyntaxException("Unknown stem", stringSegment);
                }
                object = STEM_ENUM_VALUES[((CharsTrie)object).getValue()];
                switch (2.$SwitchMap$android$icu$number$NumberSkeletonImpl$StemEnum[((Enum)object).ordinal()]) {
                    default: {
                        throw new AssertionError();
                    }
                    case 47: {
                        NumberSkeletonImpl.checkNull(macroProps.scale, stringSegment);
                        return ParseState.STATE_SCALE;
                    }
                    case 46: {
                        NumberSkeletonImpl.checkNull(macroProps.symbols, stringSegment);
                        return ParseState.STATE_NUMBERING_SYSTEM;
                    }
                    case 45: {
                        NumberSkeletonImpl.checkNull(macroProps.integerWidth, stringSegment);
                        return ParseState.STATE_INTEGER_WIDTH;
                    }
                    case 44: {
                        NumberSkeletonImpl.checkNull(macroProps.unit, stringSegment);
                        return ParseState.STATE_CURRENCY_UNIT;
                    }
                    case 43: {
                        NumberSkeletonImpl.checkNull(macroProps.perUnit, stringSegment);
                        return ParseState.STATE_PER_MEASURE_UNIT;
                    }
                    case 42: {
                        NumberSkeletonImpl.checkNull(macroProps.unit, stringSegment);
                        return ParseState.STATE_MEASURE_UNIT;
                    }
                    case 41: {
                        NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
                        return ParseState.STATE_INCREMENT_PRECISION;
                    }
                    case 40: {
                        NumberSkeletonImpl.checkNull(macroProps.symbols, stringSegment);
                        macroProps.symbols = NumberingSystem.LATIN;
                        return ParseState.STATE_NULL;
                    }
                    case 38: 
                    case 39: {
                        NumberSkeletonImpl.checkNull((Object)macroProps.decimal, stringSegment);
                        macroProps.decimal = StemToObject.decimalSeparatorDisplay((StemEnum)((Object)object));
                        return ParseState.STATE_NULL;
                    }
                    case 31: 
                    case 32: 
                    case 33: 
                    case 34: 
                    case 35: 
                    case 36: 
                    case 37: {
                        NumberSkeletonImpl.checkNull((Object)macroProps.sign, stringSegment);
                        macroProps.sign = StemToObject.signDisplay((StemEnum)((Object)object));
                        return ParseState.STATE_NULL;
                    }
                    case 26: 
                    case 27: 
                    case 28: 
                    case 29: 
                    case 30: {
                        NumberSkeletonImpl.checkNull((Object)macroProps.unitWidth, stringSegment);
                        macroProps.unitWidth = StemToObject.unitWidth((StemEnum)((Object)object));
                        return ParseState.STATE_NULL;
                    }
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: {
                        NumberSkeletonImpl.checkNull(macroProps.grouping, stringSegment);
                        macroProps.grouping = StemToObject.groupingStrategy((StemEnum)((Object)object));
                        return ParseState.STATE_NULL;
                    }
                    case 13: 
                    case 14: 
                    case 15: 
                    case 16: 
                    case 17: 
                    case 18: 
                    case 19: 
                    case 20: {
                        NumberSkeletonImpl.checkNull((Object)macroProps.roundingMode, stringSegment);
                        macroProps.roundingMode = StemToObject.roundingMode((StemEnum)((Object)object));
                        return ParseState.STATE_NULL;
                    }
                    case 9: 
                    case 10: 
                    case 11: 
                    case 12: {
                        NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
                        macroProps.precision = StemToObject.precision((StemEnum)((Object)object));
                        if (2.$SwitchMap$android$icu$number$NumberSkeletonImpl$StemEnum[((Enum)object).ordinal()] != 9) {
                            return ParseState.STATE_NULL;
                        }
                        return ParseState.STATE_FRACTION_PRECISION;
                    }
                    case 6: 
                    case 7: 
                    case 8: {
                        NumberSkeletonImpl.checkNull(macroProps.unit, stringSegment);
                        macroProps.unit = StemToObject.unit((StemEnum)((Object)object));
                        return ParseState.STATE_NULL;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                }
                NumberSkeletonImpl.checkNull(macroProps.notation, stringSegment);
                macroProps.notation = StemToObject.notation((StemEnum)((Object)object));
                n = 2.$SwitchMap$android$icu$number$NumberSkeletonImpl$StemEnum[((Enum)object).ordinal()];
                if (n != 3 && n != 4) {
                    return ParseState.STATE_NULL;
                }
                return ParseState.STATE_SCIENTIFIC;
            }
            NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
            BlueprintHelpers.parseDigitsStem(stringSegment, macroProps);
            return ParseState.STATE_NULL;
        }
        NumberSkeletonImpl.checkNull(macroProps.precision, stringSegment);
        BlueprintHelpers.parseFractionStem(stringSegment, macroProps);
        return ParseState.STATE_FRACTION_PRECISION;
    }

    static final class BlueprintHelpers {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        BlueprintHelpers() {
        }

        private static void generateCurrencyOption(Currency currency, StringBuilder stringBuilder) {
            stringBuilder.append(currency.getCurrencyCode());
        }

        private static void generateDigitsStem(int n, int n2, StringBuilder stringBuilder) {
            NumberSkeletonImpl.appendMultiple(stringBuilder, 64, n);
            if (n2 == -1) {
                stringBuilder.append('+');
            } else {
                NumberSkeletonImpl.appendMultiple(stringBuilder, 35, n2 - n);
            }
        }

        private static void generateExponentWidthOption(int n, StringBuilder stringBuilder) {
            stringBuilder.append('+');
            NumberSkeletonImpl.appendMultiple(stringBuilder, 101, n);
        }

        private static void generateFractionStem(int n, int n2, StringBuilder stringBuilder) {
            if (n == 0 && n2 == 0) {
                stringBuilder.append("precision-integer");
                return;
            }
            stringBuilder.append('.');
            NumberSkeletonImpl.appendMultiple(stringBuilder, 48, n);
            if (n2 == -1) {
                stringBuilder.append('+');
            } else {
                NumberSkeletonImpl.appendMultiple(stringBuilder, 35, n2 - n);
            }
        }

        private static void generateIncrementOption(BigDecimal bigDecimal, StringBuilder stringBuilder) {
            stringBuilder.append(bigDecimal.toPlainString());
        }

        private static void generateIntegerWidthOption(int n, int n2, StringBuilder stringBuilder) {
            if (n2 == -1) {
                stringBuilder.append('+');
            } else {
                NumberSkeletonImpl.appendMultiple(stringBuilder, 35, n2 - n);
            }
            NumberSkeletonImpl.appendMultiple(stringBuilder, 48, n);
        }

        private static void generateMeasureUnitOption(MeasureUnit measureUnit, StringBuilder stringBuilder) {
            stringBuilder.append(measureUnit.getType());
            stringBuilder.append("-");
            stringBuilder.append(measureUnit.getSubtype());
        }

        private static void generateNumberingSystemOption(NumberingSystem numberingSystem, StringBuilder stringBuilder) {
            stringBuilder.append(numberingSystem.getName());
        }

        private static void generateScaleOption(Scale scale, StringBuilder stringBuilder) {
            BigDecimal bigDecimal;
            BigDecimal bigDecimal2 = bigDecimal = scale.arbitrary;
            if (bigDecimal == null) {
                bigDecimal2 = BigDecimal.ONE;
            }
            stringBuilder.append(bigDecimal2.scaleByPowerOfTen(scale.magnitude).toPlainString());
        }

        private static void parseCurrencyOption(StringSegment stringSegment, MacroProps macroProps) {
            Object object = stringSegment.subSequence(0, stringSegment.length()).toString();
            try {}
            catch (IllegalArgumentException illegalArgumentException) {
                throw new SkeletonSyntaxException("Invalid currency", stringSegment, illegalArgumentException);
            }
            macroProps.unit = object = Currency.getInstance((String)object);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private static void parseDigitsStem(StringSegment var0, MacroProps var1_1) {
            block7 : {
                var3_3 = 0;
                for (var2_2 = 0; var2_2 < var0.length() && var0.charAt(var2_2) == '@'; ++var3_3, ++var2_2) {
                }
                if (var2_2 >= var0.length()) ** GOTO lbl20
                if (var0.charAt(var2_2) == '+') {
                    var4_4 = -1;
                    var5_5 = var2_2 + 1;
                } else {
                    var6_6 = var3_3;
                    do {
                        var5_5 = var2_2;
                        var4_4 = var6_6;
                        if (var2_2 < var0.length()) {
                            var5_5 = var2_2;
                            var4_4 = var6_6++;
                            if (var0.charAt(var2_2) == '#') {
                                ++var2_2;
                                continue;
                            }
                        }
                        break block7;
                        break;
                    } while (true);
lbl20: // 1 sources:
                    var4_4 = var3_3;
                    var5_5 = var2_2;
                }
            }
            if (var5_5 < var0.length()) throw new SkeletonSyntaxException("Invalid significant digits stem", var0);
            if (var4_4 == -1) {
                var1_1.precision = Precision.minSignificantDigits(var3_3);
                return;
            }
            var1_1.precision = Precision.minMaxSignificantDigits(var3_3, var4_4);
        }

        private static boolean parseExponentSignOption(StringSegment object, MacroProps macroProps) {
            CharsTrie charsTrie = new CharsTrie(SERIALIZED_STEM_TRIE, 0);
            if ((object = charsTrie.next((CharSequence)object, 0, object.length())) != BytesTrie.Result.INTERMEDIATE_VALUE && object != BytesTrie.Result.FINAL_VALUE) {
                return false;
            }
            object = StemToObject.signDisplay(STEM_ENUM_VALUES[charsTrie.getValue()]);
            if (object == null) {
                return false;
            }
            macroProps.notation = ((ScientificNotation)macroProps.notation).withExponentSignDisplay((NumberFormatter.SignDisplay)((Object)object));
            return true;
        }

        private static boolean parseExponentWidthOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            if (stringSegment.charAt(0) != '+') {
                return false;
            }
            int n2 = 0;
            for (n = 1; n < stringSegment.length() && stringSegment.charAt(n) == 'e'; ++n) {
                ++n2;
            }
            if (n < stringSegment.length()) {
                return false;
            }
            macroProps.notation = ((ScientificNotation)macroProps.notation).withMinExponentDigits(n2);
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static boolean parseFracSigOption(StringSegment object, MacroProps macroProps) {
            int n;
            int n2;
            int n3;
            if (((StringSegment)object).charAt(0) != '@') {
                return false;
            }
            int n4 = 0;
            for (n2 = 0; n2 < ((StringSegment)object).length() && ((StringSegment)object).charAt(n2) == '@'; ++n2) {
                ++n4;
            }
            if (n2 >= ((StringSegment)object).length()) throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", (CharSequence)object);
            if (((StringSegment)object).charAt(n2) == '+') {
                n = -1;
                n3 = n2 + 1;
            } else {
                if (n4 > 1) throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", (CharSequence)object);
                int n5 = n4;
                do {
                    n3 = n2;
                    n = n5;
                    if (n2 >= ((StringSegment)object).length()) break;
                    n3 = n2;
                    n = n5++;
                    if (((StringSegment)object).charAt(n2) != '#') break;
                    ++n2;
                } while (true);
            }
            if (n3 < ((StringSegment)object).length()) throw new SkeletonSyntaxException("Invalid digits option for fraction rounder", (CharSequence)object);
            object = (FractionPrecision)macroProps.precision;
            macroProps.precision = n == -1 ? ((FractionPrecision)object).withMinDigits(n4) : ((FractionPrecision)object).withMaxDigits(n);
            return true;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private static void parseFractionStem(StringSegment var0, MacroProps var1_1) {
            block7 : {
                var3_3 = 0;
                for (var2_2 = 1; var2_2 < var0.length() && var0.charAt(var2_2) == '0'; ++var3_3, ++var2_2) {
                }
                if (var2_2 >= var0.length()) ** GOTO lbl21
                if (var0.charAt(var2_2) == '+') {
                    var4_4 = -1;
                    ++var2_2;
                } else {
                    var5_5 = var3_3;
                    var6_6 = var2_2;
                    do {
                        var2_2 = var6_6;
                        var4_4 = var5_5;
                        if (var6_6 < var0.length()) {
                            var2_2 = var6_6;
                            var4_4 = var5_5++;
                            if (var0.charAt(var6_6) == '#') {
                                ++var6_6;
                                continue;
                            }
                        }
                        break block7;
                        break;
                    } while (true);
lbl21: // 1 sources:
                    var4_4 = var3_3;
                }
            }
            if (var2_2 < var0.length()) throw new SkeletonSyntaxException("Invalid fraction stem", var0);
            if (var4_4 == -1) {
                var1_1.precision = Precision.minFraction(var3_3);
                return;
            }
            var1_1.precision = Precision.minMaxFraction(var3_3, var4_4);
        }

        private static void parseIncrementOption(StringSegment stringSegment, MacroProps macroProps) {
            Object object = stringSegment.subSequence(0, stringSegment.length()).toString();
            try {
                object = new BigDecimal((String)object);
            }
            catch (NumberFormatException numberFormatException) {
                throw new SkeletonSyntaxException("Invalid rounding increment", stringSegment, numberFormatException);
            }
            macroProps.precision = Precision.increment((BigDecimal)object);
        }

        private static void parseIntegerWidthOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            if (stringSegment.charAt(0) == '+') {
                n = -1;
                n2 = 0 + 1;
            } else {
                n = 0;
            }
            while (n2 < stringSegment.length() && stringSegment.charAt(n2) == '#') {
                ++n;
                ++n2;
            }
            int n5 = n2;
            if (n2 < stringSegment.length()) {
                do {
                    n5 = n2;
                    n3 = n4;
                    if (n2 >= stringSegment.length()) break;
                    n5 = n2;
                    n3 = n4++;
                    if (stringSegment.charAt(n2) != '0') break;
                    ++n2;
                } while (true);
            }
            n2 = n;
            if (n != -1) {
                n2 = n + n3;
            }
            if (n5 >= stringSegment.length()) {
                macroProps.integerWidth = n2 == -1 ? IntegerWidth.zeroFillTo(n3) : IntegerWidth.zeroFillTo(n3).truncateAt(n2);
                return;
            }
            throw new SkeletonSyntaxException("Invalid integer width stem", stringSegment);
        }

        private static void parseMeasurePerUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            MeasureUnit measureUnit = macroProps.unit;
            BlueprintHelpers.parseMeasureUnitOption(stringSegment, macroProps);
            macroProps.perUnit = macroProps.unit;
            macroProps.unit = measureUnit;
        }

        private static void parseMeasureUnitOption(StringSegment stringSegment, MacroProps macroProps) {
            int n;
            for (n = 0; n < stringSegment.length() && stringSegment.charAt(n) != '-'; ++n) {
            }
            if (n != stringSegment.length()) {
                String string = stringSegment.subSequence(0, n).toString();
                String string2 = stringSegment.subSequence(n + 1, stringSegment.length()).toString();
                for (MeasureUnit measureUnit : MeasureUnit.getAvailable(string)) {
                    if (!string2.equals(measureUnit.getSubtype())) continue;
                    macroProps.unit = measureUnit;
                    return;
                }
                throw new SkeletonSyntaxException("Unknown measure unit", stringSegment);
            }
            throw new SkeletonSyntaxException("Invalid measure unit option", stringSegment);
        }

        private static void parseNumberingSystemOption(StringSegment stringSegment, MacroProps macroProps) {
            NumberingSystem numberingSystem = NumberingSystem.getInstanceByName(stringSegment.subSequence(0, stringSegment.length()).toString());
            if (numberingSystem != null) {
                macroProps.symbols = numberingSystem;
                return;
            }
            throw new SkeletonSyntaxException("Unknown numbering system", stringSegment);
        }

        private static void parseScaleOption(StringSegment stringSegment, MacroProps macroProps) {
            Object object = stringSegment.subSequence(0, stringSegment.length()).toString();
            try {
                object = new BigDecimal((String)object);
            }
            catch (NumberFormatException numberFormatException) {
                throw new SkeletonSyntaxException("Invalid scale", stringSegment, numberFormatException);
            }
            macroProps.scale = Scale.byBigDecimal((BigDecimal)object);
        }
    }

    static final class EnumToStemString {
        EnumToStemString() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static void decimalSeparatorDisplay(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay, StringBuilder stringBuilder) {
            int n = 2.$SwitchMap$android$icu$number$NumberFormatter$DecimalSeparatorDisplay[decimalSeparatorDisplay.ordinal()];
            if (n != 1) {
                if (n != 2) throw new AssertionError();
                stringBuilder.append("decimal-always");
                return;
            } else {
                stringBuilder.append("decimal-auto");
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static void groupingStrategy(NumberFormatter.GroupingStrategy groupingStrategy, StringBuilder stringBuilder) {
            int n = 2.$SwitchMap$android$icu$number$NumberFormatter$GroupingStrategy[groupingStrategy.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) throw new AssertionError();
                            stringBuilder.append("group-thousands");
                            return;
                        } else {
                            stringBuilder.append("group-on-aligned");
                        }
                        return;
                    } else {
                        stringBuilder.append("group-auto");
                    }
                    return;
                } else {
                    stringBuilder.append("group-min2");
                }
                return;
            } else {
                stringBuilder.append("group-off");
            }
        }

        private static void roundingMode(RoundingMode roundingMode, StringBuilder stringBuilder) {
            switch (roundingMode) {
                default: {
                    throw new AssertionError();
                }
                case UNNECESSARY: {
                    stringBuilder.append("rounding-mode-unnecessary");
                    break;
                }
                case HALF_UP: {
                    stringBuilder.append("rounding-mode-half-up");
                    break;
                }
                case HALF_DOWN: {
                    stringBuilder.append("rounding-mode-half-down");
                    break;
                }
                case HALF_EVEN: {
                    stringBuilder.append("rounding-mode-half-even");
                    break;
                }
                case UP: {
                    stringBuilder.append("rounding-mode-up");
                    break;
                }
                case DOWN: {
                    stringBuilder.append("rounding-mode-down");
                    break;
                }
                case FLOOR: {
                    stringBuilder.append("rounding-mode-floor");
                    break;
                }
                case CEILING: {
                    stringBuilder.append("rounding-mode-ceiling");
                }
            }
        }

        private static void signDisplay(NumberFormatter.SignDisplay signDisplay, StringBuilder stringBuilder) {
            switch (signDisplay) {
                default: {
                    throw new AssertionError();
                }
                case ACCOUNTING_EXCEPT_ZERO: {
                    stringBuilder.append("sign-accounting-except-zero");
                    break;
                }
                case EXCEPT_ZERO: {
                    stringBuilder.append("sign-except-zero");
                    break;
                }
                case ACCOUNTING_ALWAYS: {
                    stringBuilder.append("sign-accounting-always");
                    break;
                }
                case ACCOUNTING: {
                    stringBuilder.append("sign-accounting");
                    break;
                }
                case NEVER: {
                    stringBuilder.append("sign-never");
                    break;
                }
                case ALWAYS: {
                    stringBuilder.append("sign-always");
                    break;
                }
                case AUTO: {
                    stringBuilder.append("sign-auto");
                }
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private static void unitWidth(NumberFormatter.UnitWidth unitWidth, StringBuilder stringBuilder) {
            int n = 2.$SwitchMap$android$icu$number$NumberFormatter$UnitWidth[unitWidth.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) throw new AssertionError();
                            stringBuilder.append("unit-width-hidden");
                            return;
                        } else {
                            stringBuilder.append("unit-width-iso-code");
                        }
                        return;
                    } else {
                        stringBuilder.append("unit-width-full-name");
                    }
                    return;
                } else {
                    stringBuilder.append("unit-width-short");
                }
                return;
            } else {
                stringBuilder.append("unit-width-narrow");
            }
        }
    }

    static final class GeneratorHelpers {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        GeneratorHelpers() {
        }

        private static boolean decimal(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.decimal == NumberFormatter.DecimalSeparatorDisplay.AUTO) {
                return false;
            }
            EnumToStemString.decimalSeparatorDisplay(macroProps.decimal, stringBuilder);
            return true;
        }

        private static boolean grouping(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.grouping instanceof NumberFormatter.GroupingStrategy) {
                if (macroProps.grouping == NumberFormatter.GroupingStrategy.AUTO) {
                    return false;
                }
                EnumToStemString.groupingStrategy((NumberFormatter.GroupingStrategy)((Object)macroProps.grouping), stringBuilder);
                return true;
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom Grouper");
        }

        private static boolean integerWidth(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.integerWidth.equals(IntegerWidth.DEFAULT)) {
                return false;
            }
            stringBuilder.append("integer-width/");
            BlueprintHelpers.generateIntegerWidthOption(macroProps.integerWidth.minInt, macroProps.integerWidth.maxInt, stringBuilder);
            return true;
        }

        private static boolean notation(MacroProps cloneable, StringBuilder stringBuilder) {
            if (cloneable.notation instanceof CompactNotation) {
                if (cloneable.notation == Notation.compactLong()) {
                    stringBuilder.append("compact-long");
                    return true;
                }
                if (cloneable.notation == Notation.compactShort()) {
                    stringBuilder.append("compact-short");
                    return true;
                }
                throw new UnsupportedOperationException("Cannot generate number skeleton with custom compact data");
            }
            if (cloneable.notation instanceof ScientificNotation) {
                cloneable = (ScientificNotation)cloneable.notation;
                if (((ScientificNotation)cloneable).engineeringInterval == 3) {
                    stringBuilder.append("engineering");
                } else {
                    stringBuilder.append("scientific");
                }
                if (((ScientificNotation)cloneable).minExponentDigits > 1) {
                    stringBuilder.append('/');
                    BlueprintHelpers.generateExponentWidthOption(((ScientificNotation)cloneable).minExponentDigits, stringBuilder);
                }
                if (((ScientificNotation)cloneable).exponentSignDisplay != NumberFormatter.SignDisplay.AUTO) {
                    stringBuilder.append('/');
                    EnumToStemString.signDisplay(((ScientificNotation)cloneable).exponentSignDisplay, stringBuilder);
                }
                return true;
            }
            return false;
        }

        private static boolean perUnit(MacroProps macroProps, StringBuilder stringBuilder) {
            if (!(macroProps.perUnit instanceof Currency) && !(macroProps.perUnit instanceof NoUnit)) {
                stringBuilder.append("per-measure-unit/");
                BlueprintHelpers.generateMeasureUnitOption(macroProps.perUnit, stringBuilder);
                return true;
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with per-unit that is not a standard measure unit");
        }

        private static boolean precision(MacroProps cloneable, StringBuilder stringBuilder) {
            if (cloneable.precision instanceof Precision.InfiniteRounderImpl) {
                stringBuilder.append("precision-unlimited");
            } else if (cloneable.precision instanceof Precision.FractionRounderImpl) {
                cloneable = (Precision.FractionRounderImpl)cloneable.precision;
                BlueprintHelpers.generateFractionStem(((Precision.FractionRounderImpl)cloneable).minFrac, ((Precision.FractionRounderImpl)cloneable).maxFrac, stringBuilder);
            } else if (cloneable.precision instanceof Precision.SignificantRounderImpl) {
                cloneable = (Precision.SignificantRounderImpl)cloneable.precision;
                BlueprintHelpers.generateDigitsStem(((Precision.SignificantRounderImpl)cloneable).minSig, ((Precision.SignificantRounderImpl)cloneable).maxSig, stringBuilder);
            } else if (cloneable.precision instanceof Precision.FracSigRounderImpl) {
                cloneable = (Precision.FracSigRounderImpl)cloneable.precision;
                BlueprintHelpers.generateFractionStem(((Precision.FracSigRounderImpl)cloneable).minFrac, ((Precision.FracSigRounderImpl)cloneable).maxFrac, stringBuilder);
                stringBuilder.append('/');
                if (((Precision.FracSigRounderImpl)cloneable).minSig == -1) {
                    BlueprintHelpers.generateDigitsStem(1, ((Precision.FracSigRounderImpl)cloneable).maxSig, stringBuilder);
                } else {
                    BlueprintHelpers.generateDigitsStem(((Precision.FracSigRounderImpl)cloneable).minSig, -1, stringBuilder);
                }
            } else if (cloneable.precision instanceof Precision.IncrementRounderImpl) {
                cloneable = (Precision.IncrementRounderImpl)cloneable.precision;
                stringBuilder.append("precision-increment/");
                BlueprintHelpers.generateIncrementOption(((Precision.IncrementRounderImpl)cloneable).increment, stringBuilder);
            } else if (((Precision.CurrencyRounderImpl)cloneable.precision).usage == Currency.CurrencyUsage.STANDARD) {
                stringBuilder.append("precision-currency-standard");
            } else {
                stringBuilder.append("precision-currency-cash");
            }
            return true;
        }

        private static boolean roundingMode(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.roundingMode == RoundingUtils.DEFAULT_ROUNDING_MODE) {
                return false;
            }
            EnumToStemString.roundingMode(macroProps.roundingMode, stringBuilder);
            return true;
        }

        private static boolean scale(MacroProps macroProps, StringBuilder stringBuilder) {
            if (!macroProps.scale.isValid()) {
                return false;
            }
            stringBuilder.append("scale/");
            BlueprintHelpers.generateScaleOption(macroProps.scale, stringBuilder);
            return true;
        }

        private static boolean sign(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.sign == NumberFormatter.SignDisplay.AUTO) {
                return false;
            }
            EnumToStemString.signDisplay(macroProps.sign, stringBuilder);
            return true;
        }

        private static boolean symbols(MacroProps object, StringBuilder stringBuilder) {
            if (((MacroProps)object).symbols instanceof NumberingSystem) {
                object = (NumberingSystem)((MacroProps)object).symbols;
                if (((NumberingSystem)object).getName().equals("latn")) {
                    stringBuilder.append("latin");
                } else {
                    stringBuilder.append("numbering-system/");
                    BlueprintHelpers.generateNumberingSystemOption((NumberingSystem)object, stringBuilder);
                }
                return true;
            }
            throw new UnsupportedOperationException("Cannot generate number skeleton with custom DecimalFormatSymbols");
        }

        private static boolean unit(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.unit instanceof Currency) {
                stringBuilder.append("currency/");
                BlueprintHelpers.generateCurrencyOption((Currency)macroProps.unit, stringBuilder);
                return true;
            }
            if (macroProps.unit instanceof NoUnit) {
                if (macroProps.unit == NoUnit.PERCENT) {
                    stringBuilder.append("percent");
                    return true;
                }
                if (macroProps.unit == NoUnit.PERMILLE) {
                    stringBuilder.append("permille");
                    return true;
                }
                return false;
            }
            stringBuilder.append("measure-unit/");
            BlueprintHelpers.generateMeasureUnitOption(macroProps.unit, stringBuilder);
            return true;
        }

        private static boolean unitWidth(MacroProps macroProps, StringBuilder stringBuilder) {
            if (macroProps.unitWidth == NumberFormatter.UnitWidth.SHORT) {
                return false;
            }
            EnumToStemString.unitWidth(macroProps.unitWidth, stringBuilder);
            return true;
        }
    }

    static enum ParseState {
        STATE_NULL,
        STATE_SCIENTIFIC,
        STATE_FRACTION_PRECISION,
        STATE_INCREMENT_PRECISION,
        STATE_MEASURE_UNIT,
        STATE_PER_MEASURE_UNIT,
        STATE_CURRENCY_UNIT,
        STATE_INTEGER_WIDTH,
        STATE_NUMBERING_SYSTEM,
        STATE_SCALE;
        
    }

    static enum StemEnum {
        STEM_COMPACT_SHORT,
        STEM_COMPACT_LONG,
        STEM_SCIENTIFIC,
        STEM_ENGINEERING,
        STEM_NOTATION_SIMPLE,
        STEM_BASE_UNIT,
        STEM_PERCENT,
        STEM_PERMILLE,
        STEM_PRECISION_INTEGER,
        STEM_PRECISION_UNLIMITED,
        STEM_PRECISION_CURRENCY_STANDARD,
        STEM_PRECISION_CURRENCY_CASH,
        STEM_ROUNDING_MODE_CEILING,
        STEM_ROUNDING_MODE_FLOOR,
        STEM_ROUNDING_MODE_DOWN,
        STEM_ROUNDING_MODE_UP,
        STEM_ROUNDING_MODE_HALF_EVEN,
        STEM_ROUNDING_MODE_HALF_DOWN,
        STEM_ROUNDING_MODE_HALF_UP,
        STEM_ROUNDING_MODE_UNNECESSARY,
        STEM_GROUP_OFF,
        STEM_GROUP_MIN2,
        STEM_GROUP_AUTO,
        STEM_GROUP_ON_ALIGNED,
        STEM_GROUP_THOUSANDS,
        STEM_LATIN,
        STEM_UNIT_WIDTH_NARROW,
        STEM_UNIT_WIDTH_SHORT,
        STEM_UNIT_WIDTH_FULL_NAME,
        STEM_UNIT_WIDTH_ISO_CODE,
        STEM_UNIT_WIDTH_HIDDEN,
        STEM_SIGN_AUTO,
        STEM_SIGN_ALWAYS,
        STEM_SIGN_NEVER,
        STEM_SIGN_ACCOUNTING,
        STEM_SIGN_ACCOUNTING_ALWAYS,
        STEM_SIGN_EXCEPT_ZERO,
        STEM_SIGN_ACCOUNTING_EXCEPT_ZERO,
        STEM_DECIMAL_AUTO,
        STEM_DECIMAL_ALWAYS,
        STEM_PRECISION_INCREMENT,
        STEM_MEASURE_UNIT,
        STEM_PER_MEASURE_UNIT,
        STEM_CURRENCY,
        STEM_INTEGER_WIDTH,
        STEM_NUMBERING_SYSTEM,
        STEM_SCALE;
        
    }

    static final class StemToObject {
        StemToObject() {
        }

        private static NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay(StemEnum stemEnum) {
            int n = 2.$SwitchMap$android$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
            if (n != 38) {
                if (n != 39) {
                    return null;
                }
                return NumberFormatter.DecimalSeparatorDisplay.ALWAYS;
            }
            return NumberFormatter.DecimalSeparatorDisplay.AUTO;
        }

        private static NumberFormatter.GroupingStrategy groupingStrategy(StemEnum stemEnum) {
            switch (stemEnum) {
                default: {
                    return null;
                }
                case STEM_GROUP_THOUSANDS: {
                    return NumberFormatter.GroupingStrategy.THOUSANDS;
                }
                case STEM_GROUP_ON_ALIGNED: {
                    return NumberFormatter.GroupingStrategy.ON_ALIGNED;
                }
                case STEM_GROUP_AUTO: {
                    return NumberFormatter.GroupingStrategy.AUTO;
                }
                case STEM_GROUP_MIN2: {
                    return NumberFormatter.GroupingStrategy.MIN2;
                }
                case STEM_GROUP_OFF: 
            }
            return NumberFormatter.GroupingStrategy.OFF;
        }

        private static Notation notation(StemEnum stemEnum) {
            int n = 2.$SwitchMap$android$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                return Notation.simple();
                            }
                            throw new AssertionError();
                        }
                        return Notation.engineering();
                    }
                    return Notation.scientific();
                }
                return Notation.compactLong();
            }
            return Notation.compactShort();
        }

        private static Precision precision(StemEnum stemEnum) {
            switch (stemEnum) {
                default: {
                    throw new AssertionError();
                }
                case STEM_PRECISION_CURRENCY_CASH: {
                    return Precision.currency(Currency.CurrencyUsage.CASH);
                }
                case STEM_PRECISION_CURRENCY_STANDARD: {
                    return Precision.currency(Currency.CurrencyUsage.STANDARD);
                }
                case STEM_PRECISION_UNLIMITED: {
                    return Precision.unlimited();
                }
                case STEM_PRECISION_INTEGER: 
            }
            return Precision.integer();
        }

        private static RoundingMode roundingMode(StemEnum stemEnum) {
            switch (stemEnum) {
                default: {
                    throw new AssertionError();
                }
                case STEM_ROUNDING_MODE_UNNECESSARY: {
                    return RoundingMode.UNNECESSARY;
                }
                case STEM_ROUNDING_MODE_HALF_UP: {
                    return RoundingMode.HALF_UP;
                }
                case STEM_ROUNDING_MODE_HALF_DOWN: {
                    return RoundingMode.HALF_DOWN;
                }
                case STEM_ROUNDING_MODE_HALF_EVEN: {
                    return RoundingMode.HALF_EVEN;
                }
                case STEM_ROUNDING_MODE_UP: {
                    return RoundingMode.UP;
                }
                case STEM_ROUNDING_MODE_DOWN: {
                    return RoundingMode.DOWN;
                }
                case STEM_ROUNDING_MODE_FLOOR: {
                    return RoundingMode.FLOOR;
                }
                case STEM_ROUNDING_MODE_CEILING: 
            }
            return RoundingMode.CEILING;
        }

        private static NumberFormatter.SignDisplay signDisplay(StemEnum stemEnum) {
            switch (stemEnum) {
                default: {
                    return null;
                }
                case STEM_SIGN_ACCOUNTING_EXCEPT_ZERO: {
                    return NumberFormatter.SignDisplay.ACCOUNTING_EXCEPT_ZERO;
                }
                case STEM_SIGN_EXCEPT_ZERO: {
                    return NumberFormatter.SignDisplay.EXCEPT_ZERO;
                }
                case STEM_SIGN_ACCOUNTING_ALWAYS: {
                    return NumberFormatter.SignDisplay.ACCOUNTING_ALWAYS;
                }
                case STEM_SIGN_ACCOUNTING: {
                    return NumberFormatter.SignDisplay.ACCOUNTING;
                }
                case STEM_SIGN_NEVER: {
                    return NumberFormatter.SignDisplay.NEVER;
                }
                case STEM_SIGN_ALWAYS: {
                    return NumberFormatter.SignDisplay.ALWAYS;
                }
                case STEM_SIGN_AUTO: 
            }
            return NumberFormatter.SignDisplay.AUTO;
        }

        private static MeasureUnit unit(StemEnum stemEnum) {
            int n = 2.$SwitchMap$android$icu$number$NumberSkeletonImpl$StemEnum[stemEnum.ordinal()];
            if (n != 6) {
                if (n != 7) {
                    if (n == 8) {
                        return NoUnit.PERMILLE;
                    }
                    throw new AssertionError();
                }
                return NoUnit.PERCENT;
            }
            return NoUnit.BASE;
        }

        private static NumberFormatter.UnitWidth unitWidth(StemEnum stemEnum) {
            switch (stemEnum) {
                default: {
                    return null;
                }
                case STEM_UNIT_WIDTH_HIDDEN: {
                    return NumberFormatter.UnitWidth.HIDDEN;
                }
                case STEM_UNIT_WIDTH_ISO_CODE: {
                    return NumberFormatter.UnitWidth.ISO_CODE;
                }
                case STEM_UNIT_WIDTH_FULL_NAME: {
                    return NumberFormatter.UnitWidth.FULL_NAME;
                }
                case STEM_UNIT_WIDTH_SHORT: {
                    return NumberFormatter.UnitWidth.SHORT;
                }
                case STEM_UNIT_WIDTH_NARROW: 
            }
            return NumberFormatter.UnitWidth.NARROW;
        }
    }

}

