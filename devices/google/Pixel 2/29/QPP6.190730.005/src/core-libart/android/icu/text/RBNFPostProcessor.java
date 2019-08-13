/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.NFRuleSet;
import android.icu.text.RuleBasedNumberFormat;

interface RBNFPostProcessor {
    public void init(RuleBasedNumberFormat var1, String var2);

    public void process(StringBuilder var1, NFRuleSet var2);
}

