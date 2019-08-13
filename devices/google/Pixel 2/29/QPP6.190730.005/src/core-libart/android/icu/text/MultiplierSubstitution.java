/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRule;
import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;

class MultiplierSubstitution
extends NFSubstitution {
    long divisor;

    MultiplierSubstitution(int n, NFRule object, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        this.divisor = ((NFRule)object).getDivisor();
        if (this.divisor != 0L) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Substitution with divisor 0 ");
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
        return (double)this.divisor * d;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object) && this.divisor == ((MultiplierSubstitution)object).divisor;
        return bl;
    }

    @Override
    public void setDivisor(int n, short s) {
        this.divisor = NFRule.power(n, s);
        if (this.divisor != 0L) {
            return;
        }
        throw new IllegalStateException("Substitution with divisor 0");
    }

    @Override
    char tokenChar() {
        return '<';
    }

    @Override
    public double transformNumber(double d) {
        if (this.ruleSet == null) {
            return d / (double)this.divisor;
        }
        return Math.floor(d / (double)this.divisor);
    }

    @Override
    public long transformNumber(long l) {
        return (long)Math.floor(l / this.divisor);
    }
}

