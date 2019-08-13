/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.PatternProps;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.StandardPlural;
import android.icu.impl.UResource;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MacroProps;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.ModifierStore;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.impl.number.SimpleModifier;
import android.icu.impl.number.range.PrefixInfixSuffixLengthHelper;
import android.icu.impl.number.range.RangeMacroProps;
import android.icu.impl.number.range.StandardPluralRanges;
import android.icu.number.FormattedNumberRange;
import android.icu.number.NumberFormatter;
import android.icu.number.NumberFormatterImpl;
import android.icu.number.NumberRangeFormatter;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.text.NumberFormat;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;

class NumberRangeFormatterImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    SimpleModifier fApproximatelyModifier;
    final NumberRangeFormatter.RangeCollapse fCollapse;
    final NumberRangeFormatter.RangeIdentityFallback fIdentityFallback;
    final StandardPluralRanges fPluralRanges;
    String fRangePattern;
    final boolean fSameFormatters;
    final NumberFormatterImpl formatterImpl1;
    final NumberFormatterImpl formatterImpl2;

    public NumberRangeFormatterImpl(RangeMacroProps rangeMacroProps) {
        Object object = rangeMacroProps.formatter1 != null ? rangeMacroProps.formatter1.resolve() : NumberFormatter.withLocale(rangeMacroProps.loc).resolve();
        this.formatterImpl1 = new NumberFormatterImpl((MacroProps)object);
        object = rangeMacroProps.formatter2 != null ? rangeMacroProps.formatter2.resolve() : NumberFormatter.withLocale(rangeMacroProps.loc).resolve();
        this.formatterImpl2 = new NumberFormatterImpl((MacroProps)object);
        boolean bl = rangeMacroProps.sameFormatters != 0;
        this.fSameFormatters = bl;
        object = rangeMacroProps.collapse != null ? rangeMacroProps.collapse : NumberRangeFormatter.RangeCollapse.AUTO;
        this.fCollapse = object;
        object = rangeMacroProps.identityFallback != null ? rangeMacroProps.identityFallback : NumberRangeFormatter.RangeIdentityFallback.APPROXIMATELY;
        this.fIdentityFallback = object;
        NumberRangeFormatterImpl.getNumberRangeData(rangeMacroProps.loc, "latn", this);
        this.fPluralRanges = new StandardPluralRanges(rangeMacroProps.loc);
    }

    private void formatApproximately(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, NumberStringBuilder numberStringBuilder, MicroProps microProps, MicroProps microProps2) {
        if (this.fSameFormatters) {
            int n = NumberFormatterImpl.writeNumber(microProps, decimalQuantity, numberStringBuilder, 0);
            n += microProps.modInner.apply(numberStringBuilder, 0, n);
            int n2 = n + microProps.modMiddle.apply(numberStringBuilder, 0, n);
            n = this.fApproximatelyModifier.apply(numberStringBuilder, 0, n2);
            microProps.modOuter.apply(numberStringBuilder, 0, n2 + n);
        } else {
            this.formatRange(decimalQuantity, decimalQuantity2, numberStringBuilder, microProps, microProps2);
        }
    }

    private void formatRange(DecimalQuantity object, DecimalQuantity decimalQuantity, NumberStringBuilder numberStringBuilder, MicroProps microProps, MicroProps microProps2) {
        boolean bl;
        boolean bl2;
        Object object2;
        boolean bl3;
        int n = 1.$SwitchMap$android$icu$number$NumberRangeFormatter$RangeCollapse[this.fCollapse.ordinal()];
        if (n != 1 && n != 2 && n != 3) {
            bl3 = false;
            bl = false;
            bl2 = false;
        } else {
            bl3 = microProps.modOuter.semanticallyEquivalent(microProps2.modOuter);
            if (!bl3) {
                bl = false;
                bl2 = false;
            } else {
                bl2 = microProps.modMiddle.semanticallyEquivalent(microProps2.modMiddle);
                if (!bl2) {
                    boolean bl4 = false;
                    bl = bl2;
                    bl2 = bl4;
                } else {
                    object2 = microProps.modMiddle;
                    if (this.fCollapse == NumberRangeFormatter.RangeCollapse.UNIT) {
                        bl = bl2;
                        if (!object2.containsField(NumberFormat.Field.CURRENCY)) {
                            bl = bl2;
                            if (!object2.containsField(NumberFormat.Field.PERCENT)) {
                                bl = false;
                            }
                        }
                    } else {
                        bl = bl2;
                        if (this.fCollapse == NumberRangeFormatter.RangeCollapse.AUTO) {
                            bl = bl2;
                            if (object2.getCodePointCount() <= 1) {
                                bl = false;
                            }
                        }
                    }
                    bl2 = bl && this.fCollapse == NumberRangeFormatter.RangeCollapse.ALL ? microProps.modInner.semanticallyEquivalent(microProps2.modInner) : false;
                }
            }
        }
        object2 = new PrefixInfixSuffixLengthHelper();
        String string = this.fRangePattern;
        boolean bl5 = false;
        SimpleModifier.formatTwoArgPattern(string, numberStringBuilder, 0, (PrefixInfixSuffixLengthHelper)object2, null);
        n = !bl2 && microProps.modInner.getCodePointCount() > 0 ? 1 : 0;
        boolean bl6 = !bl && microProps.modMiddle.getCodePointCount() > 0;
        boolean bl7 = bl5;
        if (!bl3) {
            bl7 = bl5;
            if (microProps.modOuter.getCodePointCount() > 0) {
                bl7 = true;
            }
        }
        if (n != 0 || bl6 || bl7) {
            if (!PatternProps.isWhiteSpace(numberStringBuilder.charAt(((PrefixInfixSuffixLengthHelper)object2).index1()))) {
                ((PrefixInfixSuffixLengthHelper)object2).lengthInfix += numberStringBuilder.insertCodePoint(((PrefixInfixSuffixLengthHelper)object2).index1(), 32, null);
            }
            if (!PatternProps.isWhiteSpace(numberStringBuilder.charAt(((PrefixInfixSuffixLengthHelper)object2).index2() - 1))) {
                ((PrefixInfixSuffixLengthHelper)object2).lengthInfix += numberStringBuilder.insertCodePoint(((PrefixInfixSuffixLengthHelper)object2).index2(), 32, null);
            }
        }
        ((PrefixInfixSuffixLengthHelper)object2).length1 += NumberFormatterImpl.writeNumber(microProps, (DecimalQuantity)object, numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0());
        ((PrefixInfixSuffixLengthHelper)object2).length2 += NumberFormatterImpl.writeNumber(microProps2, decimalQuantity, numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index2());
        if (bl2) {
            object = this.resolveModifierPlurals(microProps.modInner, microProps2.modInner);
            ((PrefixInfixSuffixLengthHelper)object2).lengthInfix += object.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0(), ((PrefixInfixSuffixLengthHelper)object2).index3());
        } else {
            ((PrefixInfixSuffixLengthHelper)object2).length1 += microProps.modInner.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0(), ((PrefixInfixSuffixLengthHelper)object2).index1());
            ((PrefixInfixSuffixLengthHelper)object2).length2 += microProps2.modInner.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index2(), ((PrefixInfixSuffixLengthHelper)object2).index3());
        }
        if (bl) {
            object = this.resolveModifierPlurals(microProps.modMiddle, microProps2.modMiddle);
            ((PrefixInfixSuffixLengthHelper)object2).lengthInfix += object.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0(), ((PrefixInfixSuffixLengthHelper)object2).index3());
        } else {
            ((PrefixInfixSuffixLengthHelper)object2).length1 += microProps.modMiddle.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0(), ((PrefixInfixSuffixLengthHelper)object2).index1());
            ((PrefixInfixSuffixLengthHelper)object2).length2 += microProps2.modMiddle.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index2(), ((PrefixInfixSuffixLengthHelper)object2).index3());
        }
        if (bl3) {
            object = this.resolveModifierPlurals(microProps.modOuter, microProps2.modOuter);
            ((PrefixInfixSuffixLengthHelper)object2).lengthInfix += object.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0(), ((PrefixInfixSuffixLengthHelper)object2).index3());
        } else {
            ((PrefixInfixSuffixLengthHelper)object2).length1 += microProps.modOuter.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index0(), ((PrefixInfixSuffixLengthHelper)object2).index1());
            ((PrefixInfixSuffixLengthHelper)object2).length2 += microProps2.modOuter.apply(numberStringBuilder, ((PrefixInfixSuffixLengthHelper)object2).index2(), ((PrefixInfixSuffixLengthHelper)object2).index3());
        }
    }

    private void formatSingleValue(DecimalQuantity decimalQuantity, DecimalQuantity decimalQuantity2, NumberStringBuilder numberStringBuilder, MicroProps microProps, MicroProps microProps2) {
        if (this.fSameFormatters) {
            NumberFormatterImpl.writeAffixes(microProps, numberStringBuilder, 0, NumberFormatterImpl.writeNumber(microProps, decimalQuantity, numberStringBuilder, 0));
        } else {
            this.formatRange(decimalQuantity, decimalQuantity2, numberStringBuilder, microProps, microProps2);
        }
    }

    private static void getNumberRangeData(ULocale object, String string, NumberRangeFormatterImpl numberRangeFormatterImpl) {
        StringBuilder stringBuilder = new StringBuilder();
        NumberRangeDataSink numberRangeDataSink = new NumberRangeDataSink(stringBuilder);
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object);
        stringBuilder.append("NumberElements/");
        stringBuilder.append(string);
        stringBuilder.append("/miscPatterns");
        ((ICUResourceBundle)object).getAllItemsWithFallback(stringBuilder.toString(), numberRangeDataSink);
        if (numberRangeDataSink.rangePattern == null) {
            numberRangeDataSink.rangePattern = SimpleFormatterImpl.compileToStringMinMaxArguments("{0}\u2013{1}", stringBuilder, 2, 2);
        }
        if (numberRangeDataSink.approximatelyPattern == null) {
            numberRangeDataSink.approximatelyPattern = SimpleFormatterImpl.compileToStringMinMaxArguments("~{0}", stringBuilder, 1, 1);
        }
        numberRangeFormatterImpl.fRangePattern = numberRangeDataSink.rangePattern;
        numberRangeFormatterImpl.fApproximatelyModifier = new SimpleModifier(numberRangeDataSink.approximatelyPattern, null, false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public FormattedNumberRange format(DecimalQuantity var1_1, DecimalQuantity var2_2, boolean var3_3) {
        block8 : {
            block9 : {
                var4_4 = new NumberStringBuilder();
                var5_5 = this.formatterImpl1.preProcess(var1_1);
                var6_6 = this.fSameFormatters != false ? this.formatterImpl1.preProcess(var2_2) : this.formatterImpl2.preProcess(var2_2);
                if (!var5_5.modInner.semanticallyEquivalent(var6_6.modInner) || !var5_5.modMiddle.semanticallyEquivalent(var6_6.modMiddle) || !var5_5.modOuter.semanticallyEquivalent(var6_6.modOuter)) break block8;
                var7_7 = var3_3 != false ? NumberRangeFormatter.RangeIdentityResult.EQUAL_BEFORE_ROUNDING : (var1_1.equals(var2_2) != false ? NumberRangeFormatter.RangeIdentityResult.EQUAL_AFTER_ROUNDING : NumberRangeFormatter.RangeIdentityResult.NOT_EQUAL);
                var8_8 = this.identity2d(this.fIdentityFallback, var7_7);
                if (var8_8 == 0 || var8_8 == 1) break block9;
                if (var8_8 == 2) ** GOTO lbl-1000
                if (var8_8 == 3) ** GOTO lbl-1000
                switch (var8_8) {
                    default: {
                        switch (var8_8) {
                            default: {
                                return new FormattedNumberRange(var4_4, var1_1, var2_2, var7_7);
                            }
                            case 32: 
                            case 33: 
                            case 34: 
                            case 35: 
                        }
                    }
                    case 19: lbl-1000: // 2 sources:
                    {
                        this.formatRange(var1_1, var2_2, var4_4, var5_5, var6_6);
                        return new FormattedNumberRange(var4_4, var1_1, var2_2, var7_7);
                    }
                    case 17: 
                    case 18: lbl-1000: // 2 sources:
                    {
                        this.formatApproximately(var1_1, var2_2, var4_4, var5_5, var6_6);
                        return new FormattedNumberRange(var4_4, var1_1, var2_2, var7_7);
                    }
                    case 16: 
                }
            }
            this.formatSingleValue(var1_1, var2_2, var4_4, var5_5, var6_6);
            return new FormattedNumberRange(var4_4, var1_1, var2_2, var7_7);
        }
        this.formatRange(var1_1, var2_2, var4_4, var5_5, var6_6);
        return new FormattedNumberRange(var4_4, var1_1, var2_2, NumberRangeFormatter.RangeIdentityResult.NOT_EQUAL);
    }

    int identity2d(NumberRangeFormatter.RangeIdentityFallback rangeIdentityFallback, NumberRangeFormatter.RangeIdentityResult rangeIdentityResult) {
        return rangeIdentityFallback.ordinal() | rangeIdentityResult.ordinal() << 4;
    }

    Modifier resolveModifierPlurals(Modifier object, Modifier object2) {
        Modifier.Parameters parameters = object.getParameters();
        if (parameters == null) {
            return object;
        }
        if ((object2 = object2.getParameters()) == null) {
            return object;
        }
        object = this.fPluralRanges.resolve(parameters.plural, ((Modifier.Parameters)object2).plural);
        object = parameters.obj.getModifier(parameters.signum, (StandardPlural)((Object)object));
        return object;
    }

    private static final class NumberRangeDataSink
    extends UResource.Sink {
        String approximatelyPattern;
        String rangePattern;
        StringBuilder sb;

        NumberRangeDataSink(StringBuilder stringBuilder) {
            this.sb = stringBuilder;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (key.contentEquals("range") && this.rangePattern == null) {
                    this.rangePattern = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 2, 2);
                }
                if (key.contentEquals("approximately") && this.approximatelyPattern == null) {
                    this.approximatelyPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 1, 1);
                }
                ++n;
            }
        }
    }

}

