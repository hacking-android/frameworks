/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.StandardPlural;
import android.icu.impl.number.AdoptingModifierStore;
import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.ConstantMultiFieldModifier;
import android.icu.impl.number.CurrencySpacingEnabledModifier;
import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MicroProps;
import android.icu.impl.number.MicroPropsGenerator;
import android.icu.impl.number.Modifier;
import android.icu.impl.number.NumberStringBuilder;
import android.icu.impl.number.PatternStringUtils;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;
import android.icu.text.PluralRules;
import android.icu.util.Currency;
import android.icu.util.ULocale;
import java.util.List;

public class MutablePatternModifier
implements Modifier,
AffixUtils.SymbolProvider,
MicroPropsGenerator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    Currency currency;
    StringBuilder currentAffix;
    final boolean isStrong;
    MicroPropsGenerator parent;
    AffixPatternProvider patternInfo;
    boolean perMilleReplacesPercent;
    StandardPlural plural;
    PluralRules rules;
    NumberFormatter.SignDisplay signDisplay;
    int signum;
    DecimalFormatSymbols symbols;
    NumberFormatter.UnitWidth unitWidth;

    public MutablePatternModifier(boolean bl) {
        this.isStrong = bl;
    }

    private ConstantMultiFieldModifier createConstantModifier(NumberStringBuilder numberStringBuilder, NumberStringBuilder numberStringBuilder2) {
        this.insertPrefix(numberStringBuilder.clear(), 0);
        this.insertSuffix(numberStringBuilder2.clear(), 0);
        if (this.patternInfo.hasCurrencySign()) {
            return new CurrencySpacingEnabledModifier(numberStringBuilder, numberStringBuilder2, this.patternInfo.hasBody() ^ true, this.isStrong, this.symbols);
        }
        return new ConstantMultiFieldModifier(numberStringBuilder, numberStringBuilder2, this.patternInfo.hasBody() ^ true, this.isStrong);
    }

    private int insertPrefix(NumberStringBuilder numberStringBuilder, int n) {
        this.prepareAffix(true);
        return AffixUtils.unescape(this.currentAffix, numberStringBuilder, n, this);
    }

    private int insertSuffix(NumberStringBuilder numberStringBuilder, int n) {
        this.prepareAffix(false);
        return AffixUtils.unescape(this.currentAffix, numberStringBuilder, n, this);
    }

    private void prepareAffix(boolean bl) {
        if (this.currentAffix == null) {
            this.currentAffix = new StringBuilder();
        }
        PatternStringUtils.patternInfoToStringBuilder(this.patternInfo, bl, this.signum, this.signDisplay, this.plural, this.perMilleReplacesPercent, this.currentAffix);
    }

    public MicroPropsGenerator addToChain(MicroPropsGenerator microPropsGenerator) {
        this.parent = microPropsGenerator;
        return this;
    }

    @Override
    public int apply(NumberStringBuilder numberStringBuilder, int n, int n2) {
        int n3 = this.insertPrefix(numberStringBuilder, n);
        int n4 = this.insertSuffix(numberStringBuilder, n2 + n3);
        int n5 = !this.patternInfo.hasBody() ? numberStringBuilder.splice(n + n3, n2 + n3, "", 0, 0, null) : 0;
        CurrencySpacingEnabledModifier.applyCurrencySpacing(numberStringBuilder, n, n3, n2 + n3 + n5, n4, this.symbols);
        return n3 + n5 + n4;
    }

    @Override
    public boolean containsField(NumberFormat.Field field) {
        return false;
    }

    public ImmutablePatternModifier createImmutable() {
        return this.createImmutableAndChain(null);
    }

    public ImmutablePatternModifier createImmutableAndChain(MicroPropsGenerator microPropsGenerator) {
        NumberStringBuilder numberStringBuilder = new NumberStringBuilder();
        NumberStringBuilder numberStringBuilder2 = new NumberStringBuilder();
        if (this.needsPlurals()) {
            AdoptingModifierStore adoptingModifierStore = new AdoptingModifierStore();
            for (StandardPlural standardPlural : StandardPlural.VALUES) {
                this.setNumberProperties(1, standardPlural);
                adoptingModifierStore.setModifier(1, standardPlural, this.createConstantModifier(numberStringBuilder, numberStringBuilder2));
                this.setNumberProperties(0, standardPlural);
                adoptingModifierStore.setModifier(0, standardPlural, this.createConstantModifier(numberStringBuilder, numberStringBuilder2));
                this.setNumberProperties(-1, standardPlural);
                adoptingModifierStore.setModifier(-1, standardPlural, this.createConstantModifier(numberStringBuilder, numberStringBuilder2));
            }
            adoptingModifierStore.freeze();
            return new ImmutablePatternModifier(adoptingModifierStore, this.rules, microPropsGenerator);
        }
        this.setNumberProperties(1, null);
        ConstantMultiFieldModifier constantMultiFieldModifier = this.createConstantModifier(numberStringBuilder, numberStringBuilder2);
        this.setNumberProperties(0, null);
        ConstantMultiFieldModifier constantMultiFieldModifier2 = this.createConstantModifier(numberStringBuilder, numberStringBuilder2);
        this.setNumberProperties(-1, null);
        return new ImmutablePatternModifier(new AdoptingModifierStore(constantMultiFieldModifier, constantMultiFieldModifier2, this.createConstantModifier(numberStringBuilder, numberStringBuilder2)), null, microPropsGenerator);
    }

    @Override
    public int getCodePointCount() {
        this.prepareAffix(true);
        int n = AffixUtils.unescapedCount(this.currentAffix, false, this);
        this.prepareAffix(false);
        return n + AffixUtils.unescapedCount(this.currentAffix, false, this);
    }

    @Override
    public Modifier.Parameters getParameters() {
        return null;
    }

    @Override
    public int getPrefixLength() {
        this.prepareAffix(true);
        return AffixUtils.unescapedCount(this.currentAffix, true, this);
    }

    @Override
    public CharSequence getSymbol(int n) {
        int n2 = 3;
        switch (n) {
            default: {
                throw new AssertionError();
            }
            case -1: {
                return this.symbols.getMinusSignString();
            }
            case -2: {
                return this.symbols.getPlusSignString();
            }
            case -3: {
                return this.symbols.getPercentString();
            }
            case -4: {
                return this.symbols.getPerMillString();
            }
            case -5: {
                if (this.unitWidth == NumberFormatter.UnitWidth.ISO_CODE) {
                    return this.currency.getCurrencyCode();
                }
                if (this.unitWidth == NumberFormatter.UnitWidth.HIDDEN) {
                    return "";
                }
                n = this.unitWidth == NumberFormatter.UnitWidth.NARROW ? n2 : 0;
                return this.currency.getName(this.symbols.getULocale(), n, null);
            }
            case -6: {
                return this.currency.getCurrencyCode();
            }
            case -7: {
                return this.currency.getName(this.symbols.getULocale(), 2, this.plural.getKeyword(), null);
            }
            case -8: {
                return "\ufffd";
            }
            case -9: 
        }
        return this.currency.getName(this.symbols.getULocale(), 3, null);
    }

    @Override
    public boolean isStrong() {
        return this.isStrong;
    }

    public boolean needsPlurals() {
        return this.patternInfo.containsSymbolType(-7);
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.parent.processQuantity(decimalQuantity);
        if (this.needsPlurals()) {
            DecimalQuantity decimalQuantity2 = decimalQuantity.createCopy();
            microProps.rounder.apply(decimalQuantity2);
            this.setNumberProperties(decimalQuantity.signum(), decimalQuantity2.getStandardPlural(this.rules));
        } else {
            this.setNumberProperties(decimalQuantity.signum(), null);
        }
        microProps.modMiddle = this;
        return microProps;
    }

    @Override
    public boolean semanticallyEquivalent(Modifier modifier) {
        return false;
    }

    public void setNumberProperties(int n, StandardPlural standardPlural) {
        this.signum = n;
        this.plural = standardPlural;
    }

    public void setPatternAttributes(NumberFormatter.SignDisplay signDisplay, boolean bl) {
        this.signDisplay = signDisplay;
        this.perMilleReplacesPercent = bl;
    }

    public void setPatternInfo(AffixPatternProvider affixPatternProvider) {
        this.patternInfo = affixPatternProvider;
    }

    public void setSymbols(DecimalFormatSymbols decimalFormatSymbols, Currency currency, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules) {
        this.symbols = decimalFormatSymbols;
        this.currency = currency;
        this.unitWidth = unitWidth;
        this.rules = pluralRules;
    }

    public static class ImmutablePatternModifier
    implements MicroPropsGenerator {
        final MicroPropsGenerator parent;
        final AdoptingModifierStore pm;
        final PluralRules rules;

        ImmutablePatternModifier(AdoptingModifierStore adoptingModifierStore, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
            this.pm = adoptingModifierStore;
            this.rules = pluralRules;
            this.parent = microPropsGenerator;
        }

        public void applyToMicros(MicroProps microProps, DecimalQuantity decimalQuantity) {
            if (this.rules == null) {
                microProps.modMiddle = this.pm.getModifierWithoutPlural(decimalQuantity.signum());
            } else {
                Object object = decimalQuantity.createCopy();
                object.roundToInfinity();
                object = object.getStandardPlural(this.rules);
                microProps.modMiddle = this.pm.getModifier(decimalQuantity.signum(), (StandardPlural)((Object)object));
            }
        }

        @Override
        public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
            MicroProps microProps = this.parent.processQuantity(decimalQuantity);
            this.applyToMicros(microProps, decimalQuantity);
            return microProps;
        }
    }

}

