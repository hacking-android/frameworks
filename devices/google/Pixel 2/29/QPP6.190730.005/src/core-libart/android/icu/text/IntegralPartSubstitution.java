/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;

class IntegralPartSubstitution
extends NFSubstitution {
    IntegralPartSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
    }

    @Override
    public double calcUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d + d2;
    }

    @Override
    char tokenChar() {
        return '<';
    }

    @Override
    public double transformNumber(double d) {
        return Math.floor(d);
    }

    @Override
    public long transformNumber(long l) {
        return l;
    }
}

