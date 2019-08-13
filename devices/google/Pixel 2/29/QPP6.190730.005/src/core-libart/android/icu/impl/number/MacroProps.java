/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.AffixPatternProvider;
import android.icu.impl.number.Padder;
import android.icu.number.IntegerWidth;
import android.icu.number.Notation;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.number.Scale;
import android.icu.text.PluralRules;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import java.math.RoundingMode;
import java.util.Objects;

public class MacroProps
implements Cloneable {
    public AffixPatternProvider affixProvider;
    public NumberFormatter.DecimalSeparatorDisplay decimal;
    public Object grouping;
    public IntegerWidth integerWidth;
    public ULocale loc;
    public Notation notation;
    public Padder padder;
    public MeasureUnit perUnit;
    public Precision precision;
    public RoundingMode roundingMode;
    public PluralRules rules;
    public Scale scale;
    public NumberFormatter.SignDisplay sign;
    public Object symbols;
    public Long threshold;
    public MeasureUnit unit;
    public NumberFormatter.UnitWidth unitWidth;

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (!(object instanceof MacroProps)) {
                return false;
            }
            object = (MacroProps)object;
            if (!Objects.equals(this.notation, ((MacroProps)object).notation) || !Objects.equals(this.unit, ((MacroProps)object).unit) || !Objects.equals(this.perUnit, ((MacroProps)object).perUnit) || !Objects.equals(this.precision, ((MacroProps)object).precision) || !Objects.equals((Object)this.roundingMode, (Object)((MacroProps)object).roundingMode) || !Objects.equals(this.grouping, ((MacroProps)object).grouping) || !Objects.equals(this.padder, ((MacroProps)object).padder) || !Objects.equals(this.integerWidth, ((MacroProps)object).integerWidth) || !Objects.equals(this.symbols, ((MacroProps)object).symbols) || !Objects.equals((Object)this.unitWidth, (Object)((MacroProps)object).unitWidth) || !Objects.equals((Object)this.sign, (Object)((MacroProps)object).sign) || !Objects.equals((Object)this.decimal, (Object)((MacroProps)object).decimal) || !Objects.equals(this.affixProvider, ((MacroProps)object).affixProvider) || !Objects.equals(this.scale, ((MacroProps)object).scale) || !Objects.equals(this.rules, ((MacroProps)object).rules) || !Objects.equals(this.loc, ((MacroProps)object).loc)) break block3;
            bl = true;
        }
        return bl;
    }

    public void fallback(MacroProps macroProps) {
        if (this.notation == null) {
            this.notation = macroProps.notation;
        }
        if (this.unit == null) {
            this.unit = macroProps.unit;
        }
        if (this.perUnit == null) {
            this.perUnit = macroProps.perUnit;
        }
        if (this.precision == null) {
            this.precision = macroProps.precision;
        }
        if (this.roundingMode == null) {
            this.roundingMode = macroProps.roundingMode;
        }
        if (this.grouping == null) {
            this.grouping = macroProps.grouping;
        }
        if (this.padder == null) {
            this.padder = macroProps.padder;
        }
        if (this.integerWidth == null) {
            this.integerWidth = macroProps.integerWidth;
        }
        if (this.symbols == null) {
            this.symbols = macroProps.symbols;
        }
        if (this.unitWidth == null) {
            this.unitWidth = macroProps.unitWidth;
        }
        if (this.sign == null) {
            this.sign = macroProps.sign;
        }
        if (this.decimal == null) {
            this.decimal = macroProps.decimal;
        }
        if (this.affixProvider == null) {
            this.affixProvider = macroProps.affixProvider;
        }
        if (this.scale == null) {
            this.scale = macroProps.scale;
        }
        if (this.rules == null) {
            this.rules = macroProps.rules;
        }
        if (this.loc == null) {
            this.loc = macroProps.loc;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.notation, this.unit, this.perUnit, this.precision, this.roundingMode, this.grouping, this.padder, this.integerWidth, this.symbols, this.unitWidth, this.sign, this.decimal, this.affixProvider, this.scale, this.rules, this.loc});
    }
}

