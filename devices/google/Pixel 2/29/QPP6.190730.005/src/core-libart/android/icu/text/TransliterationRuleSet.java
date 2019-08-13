/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Replaceable;
import android.icu.text.TransliterationRule;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import java.util.ArrayList;
import java.util.List;

class TransliterationRuleSet {
    private int[] index;
    private int maxContextLength = 0;
    private List<TransliterationRule> ruleVector = new ArrayList<TransliterationRule>();
    private TransliterationRule[] rules;

    public void addRule(TransliterationRule transliterationRule) {
        this.ruleVector.add(transliterationRule);
        int n = transliterationRule.getAnteContextLength();
        if (n > this.maxContextLength) {
            this.maxContextLength = n;
        }
        this.rules = null;
    }

    void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = new UnicodeSet(unicodeSet);
        unicodeSet = new UnicodeSet();
        int n = this.ruleVector.size();
        for (int i = 0; i < n; ++i) {
            this.ruleVector.get(i).addSourceTargetSet(unicodeSet4, unicodeSet2, unicodeSet3, unicodeSet.clear());
            unicodeSet4.addAll(unicodeSet);
        }
    }

    public void freeze() {
        TransliterationRule transliterationRule;
        int n;
        int n2;
        int n3 = this.ruleVector.size();
        this.index = new int[257];
        Object object = new ArrayList(n3 * 2);
        Object object2 = new int[n3];
        for (n = 0; n < n3; ++n) {
            object2[n] = this.ruleVector.get(n).getIndexValue();
        }
        for (n = 0; n < 256; ++n) {
            this.index[n] = object.size();
            for (n2 = 0; n2 < n3; ++n2) {
                if (object2[n2] >= 0) {
                    if (object2[n2] != n) continue;
                    object.add(this.ruleVector.get(n2));
                    continue;
                }
                transliterationRule = this.ruleVector.get(n2);
                if (!transliterationRule.matchesIndexValue(n)) continue;
                object.add(transliterationRule);
            }
        }
        this.index[256] = object.size();
        this.rules = new TransliterationRule[object.size()];
        object.toArray(this.rules);
        object2 = null;
        for (n = 0; n < 256; ++n) {
            for (n2 = this.index[n]; n2 < this.index[n + 1] - 1; ++n2) {
                transliterationRule = this.rules[n2];
                for (n3 = n2 + 1; n3 < this.index[n + 1]; ++n3) {
                    TransliterationRule transliterationRule2 = this.rules[n3];
                    object = object2;
                    if (transliterationRule.masks(transliterationRule2)) {
                        if (object2 == null) {
                            object2 = new StringBuilder();
                        } else {
                            ((StringBuilder)object2).append("\n");
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Rule ");
                        ((StringBuilder)object).append(transliterationRule);
                        ((StringBuilder)object).append(" masks ");
                        ((StringBuilder)object).append(transliterationRule2);
                        ((StringBuilder)object2).append(((StringBuilder)object).toString());
                        object = object2;
                    }
                    object2 = object;
                }
            }
        }
        if (object2 == null) {
            return;
        }
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public int getMaximumContextLength() {
        return this.maxContextLength;
    }

    String toRules(boolean bl) {
        int n = this.ruleVector.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append('\n');
            }
            stringBuilder.append(this.ruleVector.get(i).toRule(bl));
        }
        return stringBuilder.toString();
    }

    public boolean transliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n = replaceable.char32At(position.start) & 255;
        for (int i = this.index[n]; i < this.index[n + 1]; ++i) {
            int n2 = this.rules[i].matchAndReplace(replaceable, position, bl);
            if (n2 != 1) {
                if (n2 != 2) {
                    continue;
                }
                return true;
            }
            return false;
        }
        position.start += UTF16.getCharCount(replaceable.char32At(position.start));
        return true;
    }
}

