/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.number.AffixUtils;
import android.icu.impl.number.parse.AffixTokenMatcherFactory;
import android.icu.impl.number.parse.CodePointMatcher;
import android.icu.impl.number.parse.CombinedCurrencyMatcher;
import android.icu.impl.number.parse.IgnorablesMatcher;
import android.icu.impl.number.parse.MinusSignMatcher;
import android.icu.impl.number.parse.NumberParseMatcher;
import android.icu.impl.number.parse.PercentMatcher;
import android.icu.impl.number.parse.PermilleMatcher;
import android.icu.impl.number.parse.PlusSignMatcher;
import android.icu.impl.number.parse.SeriesMatcher;
import android.icu.text.UnicodeSet;

public class AffixPatternMatcher
extends SeriesMatcher
implements AffixUtils.TokenConsumer {
    private final String affixPattern;
    private AffixTokenMatcherFactory factory;
    private IgnorablesMatcher ignorables;
    private int lastTypeOrCp;

    private AffixPatternMatcher(String string) {
        this.affixPattern = string;
    }

    public static AffixPatternMatcher fromAffixPattern(String string, AffixTokenMatcherFactory object, int n) {
        if (string.isEmpty()) {
            return null;
        }
        AffixPatternMatcher affixPatternMatcher = new AffixPatternMatcher(string);
        affixPatternMatcher.factory = object;
        object = (n & 512) != 0 ? null : ((AffixTokenMatcherFactory)object).ignorables();
        affixPatternMatcher.ignorables = object;
        affixPatternMatcher.lastTypeOrCp = 0;
        AffixUtils.iterateWithConsumer(string, affixPatternMatcher);
        affixPatternMatcher.factory = null;
        affixPatternMatcher.ignorables = null;
        affixPatternMatcher.lastTypeOrCp = 0;
        affixPatternMatcher.freeze();
        return affixPatternMatcher;
    }

    @Override
    public void consumeToken(int n) {
        if (!(this.ignorables == null || this.length() <= 0 || this.lastTypeOrCp >= 0 && this.ignorables.getSet().contains(this.lastTypeOrCp))) {
            this.addMatcher(this.ignorables);
        }
        if (n < 0) {
            switch (n) {
                default: {
                    throw new AssertionError();
                }
                case -1: {
                    this.addMatcher(this.factory.minusSign());
                    break;
                }
                case -2: {
                    this.addMatcher(this.factory.plusSign());
                    break;
                }
                case -3: {
                    this.addMatcher(this.factory.percent());
                    break;
                }
                case -4: {
                    this.addMatcher(this.factory.permille());
                    break;
                }
                case -9: 
                case -8: 
                case -7: 
                case -6: 
                case -5: {
                    this.addMatcher(this.factory.currency());
                    break;
                }
            }
        } else {
            IgnorablesMatcher ignorablesMatcher = this.ignorables;
            if (ignorablesMatcher == null || !ignorablesMatcher.getSet().contains(n)) {
                this.addMatcher(CodePointMatcher.getInstance(n));
            }
        }
        this.lastTypeOrCp = n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AffixPatternMatcher)) {
            return false;
        }
        return this.affixPattern.equals(((AffixPatternMatcher)object).affixPattern);
    }

    public String getPattern() {
        return this.affixPattern;
    }

    public int hashCode() {
        return this.affixPattern.hashCode();
    }

    @Override
    public String toString() {
        return this.affixPattern;
    }
}

