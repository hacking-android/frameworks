/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;

class AbsoluteValueSubstitution
extends NFSubstitution {
    AbsoluteValueSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
    }

    @Override
    public double calcUpperBound(double d) {
        return Double.MAX_VALUE;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return -d;
    }

    @Override
    char tokenChar() {
        return '>';
    }

    @Override
    public double transformNumber(double d) {
        return Math.abs(d);
    }

    @Override
    public long transformNumber(long l) {
        return Math.abs(l);
    }
}

