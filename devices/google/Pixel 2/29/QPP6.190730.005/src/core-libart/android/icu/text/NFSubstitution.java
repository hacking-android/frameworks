/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.AbsoluteValueSubstitution;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.FractionalPartSubstitution;
import android.icu.text.IntegralPartSubstitution;
import android.icu.text.ModulusSubstitution;
import android.icu.text.MultiplierSubstitution;
import android.icu.text.NFRule;
import android.icu.text.NFRuleSet;
import android.icu.text.NumeratorSubstitution;
import android.icu.text.RuleBasedNumberFormat;
import android.icu.text.SameValueSubstitution;
import java.text.ParsePosition;

abstract class NFSubstitution {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MAX_INT64_IN_DOUBLE = 0x1FFFFFFFFFFFFFL;
    final DecimalFormat numberFormat;
    final int pos;
    final NFRuleSet ruleSet;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    NFSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        this.pos = n;
        n = string.length();
        if (n >= 2 && string.charAt(0) == string.charAt(n - 1)) {
            string = string.substring(1, n - 1);
        } else if (n != 0) throw new IllegalArgumentException("Illegal substitution syntax");
        if (string.length() == 0) {
            this.ruleSet = nFRuleSet;
            this.numberFormat = null;
            return;
        } else if (string.charAt(0) == '%') {
            this.ruleSet = nFRuleSet.owner.findRuleSet(string);
            this.numberFormat = null;
            return;
        } else if (string.charAt(0) != '#' && string.charAt(0) != '0') {
            if (string.charAt(0) != '>') throw new IllegalArgumentException("Illegal substitution syntax");
            this.ruleSet = nFRuleSet;
            this.numberFormat = null;
            return;
        } else {
            this.ruleSet = null;
            this.numberFormat = (DecimalFormat)nFRuleSet.owner.getDecimalFormat().clone();
            this.numberFormat.applyPattern(string);
        }
    }

    public static NFSubstitution makeSubstitution(int n, NFRule nFRule, NFRule nFRule2, NFRuleSet nFRuleSet, RuleBasedNumberFormat ruleBasedNumberFormat, String string) {
        if (string.length() == 0) {
            return null;
        }
        switch (string.charAt(0)) {
            default: {
                throw new IllegalArgumentException("Illegal substitution character");
            }
            case '>': {
                if (nFRule.getBaseValue() == -1L) {
                    return new AbsoluteValueSubstitution(n, nFRuleSet, string);
                }
                if (nFRule.getBaseValue() != -2L && nFRule.getBaseValue() != -3L && nFRule.getBaseValue() != -4L) {
                    if (!nFRuleSet.isFractionSet()) {
                        return new ModulusSubstitution(n, nFRule, nFRule2, nFRuleSet, string);
                    }
                    throw new IllegalArgumentException(">> not allowed in fraction rule set");
                }
                return new FractionalPartSubstitution(n, nFRuleSet, string);
            }
            case '=': {
                return new SameValueSubstitution(n, nFRuleSet, string);
            }
            case '<': 
        }
        if (nFRule.getBaseValue() != -1L) {
            if (nFRule.getBaseValue() != -2L && nFRule.getBaseValue() != -3L && nFRule.getBaseValue() != -4L) {
                if (nFRuleSet.isFractionSet()) {
                    return new NumeratorSubstitution(n, nFRule.getBaseValue(), ruleBasedNumberFormat.getDefaultRuleSet(), string);
                }
                return new MultiplierSubstitution(n, nFRule, nFRuleSet, string);
            }
            return new IntegralPartSubstitution(n, nFRuleSet, string);
        }
        throw new IllegalArgumentException("<< not allowed in negative-number rule");
    }

    public abstract double calcUpperBound(double var1);

    public abstract double composeRuleValue(double var1, double var3);

    public Number doParse(String string, ParsePosition parsePosition, double d, double d2, boolean bl, int n) {
        d2 = this.calcUpperBound(d2);
        Object object = this.ruleSet;
        if (object != null) {
            Number number = ((NFRuleSet)object).parse(string, parsePosition, d2, n);
            object = number;
            if (bl) {
                object = number;
                if (!this.ruleSet.isFractionSet()) {
                    object = number;
                    if (parsePosition.getIndex() == 0) {
                        object = this.ruleSet.owner.getDecimalFormat().parse(string, parsePosition);
                    }
                }
            }
        } else {
            object = this.numberFormat.parse(string, parsePosition);
        }
        if (parsePosition.getIndex() != 0) {
            d = this.composeRuleValue(((Number)object).doubleValue(), d);
            if (d == (double)((long)d)) {
                return (long)d;
            }
            return new Double(d);
        }
        return object;
    }

    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        NFRuleSet nFRuleSet;
        if (Double.isInfinite(d = this.transformNumber(d))) {
            this.ruleSet.findRule(Double.POSITIVE_INFINITY).doFormat(d, stringBuilder, n + this.pos, n2);
            return;
        }
        if (d == Math.floor(d) && (nFRuleSet = this.ruleSet) != null) {
            nFRuleSet.format((long)d, stringBuilder, n + this.pos, n2);
        } else {
            nFRuleSet = this.ruleSet;
            if (nFRuleSet != null) {
                nFRuleSet.format(d, stringBuilder, n + this.pos, n2);
            } else {
                stringBuilder.insert(n + this.pos, this.numberFormat.format(d));
            }
        }
    }

    public void doSubstitution(long l, StringBuilder stringBuilder, int n, int n2) {
        if (this.ruleSet != null) {
            l = this.transformNumber(l);
            this.ruleSet.format(l, stringBuilder, n + this.pos, n2);
        } else if (l <= 0x1FFFFFFFFFFFFFL) {
            double d;
            double d2 = d = this.transformNumber((double)l);
            if (this.numberFormat.getMaximumFractionDigits() == 0) {
                d2 = Math.floor(d);
            }
            stringBuilder.insert(this.pos + n, this.numberFormat.format(d2));
        } else {
            l = this.transformNumber(l);
            stringBuilder.insert(this.pos + n, this.numberFormat.format(l));
        }
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (this.getClass() == object.getClass()) {
            NFSubstitution nFSubstitution = (NFSubstitution)object;
            if (this.pos == nFSubstitution.pos && (this.ruleSet != null || nFSubstitution.ruleSet == null) && ((object = this.numberFormat) == null ? nFSubstitution.numberFormat == null : ((DecimalFormat)object).equals(nFSubstitution.numberFormat))) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public final int getPos() {
        return this.pos;
    }

    public int hashCode() {
        return 42;
    }

    public boolean isModulusSubstitution() {
        return false;
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        DecimalFormat decimalFormat = this.numberFormat;
        if (decimalFormat != null) {
            decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }

    public void setDivisor(int n, short s) {
    }

    public String toString() {
        if (this.ruleSet != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.tokenChar());
            stringBuilder.append(this.ruleSet.getName());
            stringBuilder.append(this.tokenChar());
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.tokenChar());
        stringBuilder.append(this.numberFormat.toPattern());
        stringBuilder.append(this.tokenChar());
        return stringBuilder.toString();
    }

    abstract char tokenChar();

    public abstract double transformNumber(double var1);

    public abstract long transformNumber(long var1);
}

