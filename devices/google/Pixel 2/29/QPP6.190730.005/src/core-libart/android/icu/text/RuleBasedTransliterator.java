/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.TransliterationRuleSet;
import android.icu.text.Transliterator;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeReplacer;
import android.icu.text.UnicodeSet;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class RuleBasedTransliterator
extends Transliterator {
    private final Data data;

    RuleBasedTransliterator(String string, Data data, UnicodeFilter unicodeFilter) {
        super(string, unicodeFilter);
        this.data = data;
        this.setMaximumContextLength(data.ruleSet.getMaximumContextLength());
    }

    @Deprecated
    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        this.data.ruleSet.addSourceTargetSet(unicodeSet, unicodeSet2, unicodeSet3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        Data data = this.data;
        synchronized (data) {
            int n = 0;
            int n2 = position.limit - position.start << 4;
            int n3 = n;
            int n4 = n2;
            if (n2 < 0) {
                n4 = Integer.MAX_VALUE;
                n3 = n;
            }
            while (position.start < position.limit && n3 <= n4 && this.data.ruleSet.transliterate(replaceable, position, bl)) {
                ++n3;
            }
            return;
        }
    }

    @Deprecated
    public Transliterator safeClone() {
        UnicodeFilter unicodeFilter;
        UnicodeFilter unicodeFilter2 = unicodeFilter = this.getFilter();
        if (unicodeFilter != null) {
            unicodeFilter2 = unicodeFilter;
            if (unicodeFilter instanceof UnicodeSet) {
                unicodeFilter2 = new UnicodeSet((UnicodeSet)unicodeFilter);
            }
        }
        return new RuleBasedTransliterator(this.getID(), this.data, unicodeFilter2);
    }

    @Deprecated
    @Override
    public String toRules(boolean bl) {
        return this.data.ruleSet.toRules(bl);
    }

    static class Data {
        public TransliterationRuleSet ruleSet = new TransliterationRuleSet();
        Map<String, char[]> variableNames = new HashMap<String, char[]>();
        Object[] variables;
        char variablesBase;

        public UnicodeMatcher lookupMatcher(int n) {
            Object object;
            object = (n -= this.variablesBase) >= 0 && n < ((Object[])(object = this.variables)).length ? (UnicodeMatcher)object[n] : null;
            return object;
        }

        public UnicodeReplacer lookupReplacer(int n) {
            Object object;
            object = (n -= this.variablesBase) >= 0 && n < ((Object[])(object = this.variables)).length ? (UnicodeReplacer)object[n] : null;
            return object;
        }
    }

}

