/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRule;
import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;
import java.text.ParsePosition;

class ModulusSubstitution
extends NFSubstitution {
    long divisor;
    private final NFRule ruleToUse;

    ModulusSubstitution(int n, NFRule object, NFRule nFRule, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        this.divisor = ((NFRule)object).getDivisor();
        if (this.divisor != 0L) {
            this.ruleToUse = string.equals(">>>") ? nFRule : null;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Substitution with bad divisor (");
        ((StringBuilder)object).append(this.divisor);
        ((StringBuilder)object).append(") ");
        ((StringBuilder)object).append(string.substring(0, n));
        ((StringBuilder)object).append(" | ");
        ((StringBuilder)object).append(string.substring(n));
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    public double calcUpperBound(double d) {
        return this.divisor;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d2 - d2 % (double)this.divisor + d;
    }

    @Override
    public Number doParse(String object, ParsePosition parsePosition, double d, double d2, boolean bl, int n) {
        NFRule nFRule = this.ruleToUse;
        if (nFRule == null) {
            return super.doParse((String)object, parsePosition, d, d2, bl, n);
        }
        object = nFRule.doParse((String)object, parsePosition, false, d2, n);
        if (parsePosition.getIndex() != 0) {
            d = this.composeRuleValue(((Number)object).doubleValue(), d);
            if (d == (double)((long)d)) {
                return (long)d;
            }
            return new Double(d);
        }
        return object;
    }

    @Override
    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(d, stringBuilder, n, n2);
        } else {
            d = this.transformNumber(d);
            this.ruleToUse.doFormat(d, stringBuilder, n + this.pos, n2);
        }
    }

    @Override
    public void doSubstitution(long l, StringBuilder stringBuilder, int n, int n2) {
        if (this.ruleToUse == null) {
            super.doSubstitution(l, stringBuilder, n, n2);
        } else {
            l = this.transformNumber(l);
            this.ruleToUse.doFormat(l, stringBuilder, n + this.pos, n2);
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object);
        boolean bl2 = false;
        if (bl) {
            object = (ModulusSubstitution)object;
            if (this.divisor == ((ModulusSubstitution)object).divisor) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public boolean isModulusSubstitution() {
        return true;
    }

    @Override
    public void setDivisor(int n, short s) {
        this.divisor = NFRule.power(n, s);
        if (this.divisor != 0L) {
            return;
        }
        throw new IllegalStateException("Substitution with bad divisor");
    }

    @Override
    char tokenChar() {
        return '>';
    }

    @Override
    public double transformNumber(double d) {
        return Math.floor(d % (double)this.divisor);
    }

    @Override
    public long transformNumber(long l) {
        return l % this.divisor;
    }
}

