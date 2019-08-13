/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;

class SameValueSubstitution
extends NFSubstitution {
    SameValueSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        if (!string.equals("==")) {
            return;
        }
        throw new IllegalArgumentException("== is not a legal token");
    }

    @Override
    public double calcUpperBound(double d) {
        return d;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d;
    }

    @Override
    char tokenChar() {
        return '=';
    }

    @Override
    public double transformNumber(double d) {
        return d;
    }

    @Override
    public long transformNumber(long l) {
        return l;
    }
}

