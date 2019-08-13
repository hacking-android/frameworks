/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.PatternProps;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NFRule;
import android.icu.text.RuleBasedNumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

final class NFRuleSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IMPROPER_FRACTION_RULE_INDEX = 1;
    static final int INFINITY_RULE_INDEX = 4;
    static final int MASTER_RULE_INDEX = 3;
    static final int NAN_RULE_INDEX = 5;
    static final int NEGATIVE_RULE_INDEX = 0;
    static final int PROPER_FRACTION_RULE_INDEX = 2;
    private static final int RECURSION_LIMIT = 64;
    LinkedList<NFRule> fractionRules;
    private boolean isFractionRuleSet = false;
    private final boolean isParseable;
    private final String name;
    final NFRule[] nonNumericalRules = new NFRule[6];
    final RuleBasedNumberFormat owner;
    private NFRule[] rules;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public NFRuleSet(RuleBasedNumberFormat object, String[] arrstring, int n) throws IllegalArgumentException {
        this.owner = object;
        String string = arrstring[n];
        if (string.length() == 0) throw new IllegalArgumentException("Empty rule set description");
        if (string.charAt(0) == '%') {
            int n2;
            int n3 = string.indexOf(58);
            if (n3 == -1) throw new IllegalArgumentException("Rule set name doesn't end in colon");
            String string2 = string.substring(0, n3);
            this.isParseable = true ^ string2.endsWith("@noparse");
            object = string2;
            if (!this.isParseable) {
                object = string2.substring(0, string2.length() - 8);
            }
            this.name = object;
            do {
                n2 = n3;
                if (n3 >= string.length()) break;
                n2 = ++n3;
            } while (PatternProps.isWhiteSpace(string.charAt(n3)));
            arrstring[n] = object = string.substring(n2);
        } else {
            this.name = "%default";
            this.isParseable = true;
            object = string;
        }
        if (((String)object).length() == 0) throw new IllegalArgumentException("Empty rule set description");
    }

    private NFRule findFractionRuleSetRule(double d) {
        int n;
        block7 : {
            int n2;
            block8 : {
                NFRule[] arrnFRule;
                long l = this.rules[0].getBaseValue();
                for (n = 1; n < (arrnFRule = this.rules).length; ++n) {
                    l = NFRuleSet.lcm(l, arrnFRule[n].getBaseValue());
                }
                long l2 = Math.round((double)l * d);
                long l3 = Long.MAX_VALUE;
                int n3 = 0;
                n = 0;
                do {
                    long l4;
                    arrnFRule = this.rules;
                    n2 = n3;
                    if (n >= arrnFRule.length) break;
                    long l5 = l4 = arrnFRule[n].getBaseValue() * l2 % l;
                    if (l - l4 < l4) {
                        l5 = l - l4;
                    }
                    l4 = l3;
                    if (l5 < l3) {
                        n2 = n;
                        l4 = l5;
                        n3 = n2;
                        if (l5 == 0L) break;
                    }
                    ++n;
                    l3 = l4;
                } while (true);
                arrnFRule = this.rules;
                n = n2;
                if (n2 + 1 >= arrnFRule.length) break block7;
                n = n2;
                if (arrnFRule[n2 + 1].getBaseValue() != this.rules[n2].getBaseValue()) break block7;
                if (Math.round((double)this.rules[n2].getBaseValue() * d) < 1L) break block8;
                n = n2;
                if (Math.round((double)this.rules[n2].getBaseValue() * d) < 2L) break block7;
            }
            n = n2 + 1;
        }
        return this.rules[n];
    }

    private NFRule findNormalRule(long l) {
        Object object;
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(l);
        }
        long l2 = l;
        if (l < 0L) {
            object = this.nonNumericalRules;
            if (object[0] != null) {
                return object[0];
            }
            l2 = -l;
        }
        int n = 0;
        int n2 = this.rules.length;
        if (n2 > 0) {
            while (n < n2) {
                int n3 = n + n2 >>> 1;
                l = this.rules[n3].getBaseValue();
                if (l == l2) {
                    return this.rules[n3];
                }
                if (l > l2) {
                    n2 = n3;
                    continue;
                }
                n = n3 + 1;
            }
            if (n2 != 0) {
                NFRule nFRule = this.rules[n2 - 1];
                object = nFRule;
                if (nFRule.shouldRollBack(l2)) {
                    if (n2 != 1) {
                        object = this.rules[n2 - 2];
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("The rule set ");
                        ((StringBuilder)object).append(this.name);
                        ((StringBuilder)object).append(" cannot roll back from the rule '");
                        ((StringBuilder)object).append(nFRule);
                        ((StringBuilder)object).append("'");
                        throw new IllegalStateException(((StringBuilder)object).toString());
                    }
                }
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("The rule set ");
            ((StringBuilder)object).append(this.name);
            ((StringBuilder)object).append(" cannot format the value ");
            ((StringBuilder)object).append(l2);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        return this.nonNumericalRules[3];
    }

    private static long lcm(long l, long l2) {
        long l3 = l;
        long l4 = l2;
        int n = 0;
        while ((l3 & 1L) == 0L && (l4 & 1L) == 0L) {
            ++n;
            l3 >>= 1;
            l4 >>= 1;
        }
        long l5 = (l3 & 1L) == 1L ? -l4 : l3;
        while (l5 != 0L) {
            while ((l5 & 1L) == 0L) {
                l5 >>= 1;
            }
            if (l5 > 0L) {
                l3 = l5;
            } else {
                l4 = -l5;
            }
            l5 = l3 - l4;
        }
        return l / (l3 << n) * l2;
    }

    private void setBestFractionRule(int n, NFRule nFRule, boolean bl) {
        NFRule[] arrnFRule;
        if (bl) {
            if (this.fractionRules == null) {
                this.fractionRules = new LinkedList();
            }
            this.fractionRules.add(nFRule);
        }
        if ((arrnFRule = this.nonNumericalRules)[n] == null) {
            arrnFRule[n] = nFRule;
        } else if (this.owner.getDecimalFormatSymbols().getDecimalSeparator() == nFRule.getDecimalPoint()) {
            this.nonNumericalRules[n] = nFRule;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof NFRuleSet)) {
            return false;
        }
        object = (NFRuleSet)object;
        if (this.name.equals(((NFRuleSet)object).name) && this.rules.length == ((NFRuleSet)object).rules.length && this.isFractionRuleSet == ((NFRuleSet)object).isFractionRuleSet) {
            NFRule[] arrnFRule;
            int n;
            for (n = 0; n < (arrnFRule = this.nonNumericalRules).length; ++n) {
                if (Objects.equals(arrnFRule[n], ((NFRuleSet)object).nonNumericalRules[n])) continue;
                return false;
            }
            for (n = 0; n < (arrnFRule = this.rules).length; ++n) {
                if (arrnFRule[n].equals(((NFRuleSet)object).rules[n])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    NFRule findRule(double d) {
        Object object;
        if (this.isFractionRuleSet) {
            return this.findFractionRuleSetRule(d);
        }
        if (Double.isNaN(d)) {
            NFRule nFRule;
            NFRule nFRule2 = nFRule = this.nonNumericalRules[5];
            if (nFRule == null) {
                nFRule2 = this.owner.getDefaultNaNRule();
            }
            return nFRule2;
        }
        double d2 = d;
        if (d < 0.0) {
            object = this.nonNumericalRules;
            if (object[0] != null) {
                return object[0];
            }
            d2 = -d;
        }
        if (Double.isInfinite(d2)) {
            NFRule nFRule = this.nonNumericalRules[4];
            object = nFRule;
            if (nFRule == null) {
                object = this.owner.getDefaultInfinityRule();
            }
            return object;
        }
        if (d2 != Math.floor(d2)) {
            if (d2 < 1.0 && (object = this.nonNumericalRules)[2] != null) {
                return object[2];
            }
            object = this.nonNumericalRules;
            if (object[1] != null) {
                return object[1];
            }
        }
        if ((object = this.nonNumericalRules)[3] != null) {
            return object[3];
        }
        return this.findNormalRule(Math.round(d2));
    }

    public void format(double d, StringBuilder stringBuilder, int n, int n2) {
        if (n2 < 64) {
            this.findRule(d).doFormat(d, stringBuilder, n, n2 + 1);
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Recursion limit exceeded when applying ruleSet ");
        stringBuilder.append(this.name);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void format(long l, StringBuilder stringBuilder, int n, int n2) {
        if (n2 < 64) {
            this.findNormalRule(l).doFormat(l, stringBuilder, n, n2 + 1);
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Recursion limit exceeded when applying ruleSet ");
        stringBuilder.append(this.name);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return 42;
    }

    public boolean isFractionSet() {
        return this.isFractionRuleSet;
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public boolean isPublic() {
        return this.name.startsWith("%%") ^ true;
    }

    public void makeIntoFractionRuleSet() {
        this.isFractionRuleSet = true;
    }

    public Number parse(String string, ParsePosition parsePosition, double d, int n) {
        Object object;
        int n2;
        ParsePosition parsePosition2 = new ParsePosition(0);
        Object object2 = NFRule.ZERO;
        if (string.length() == 0) {
            return object2;
        }
        for (n2 = 0; n2 < ((NFRule[])(object = this.nonNumericalRules)).length; ++n2) {
            NFRule nFRule = object[n2];
            int n3 = n;
            object = object2;
            if (nFRule != null) {
                n3 = n;
                object = object2;
                if ((n >> n2 & 1) == 0) {
                    n3 = n | 1 << n2;
                    object = nFRule.doParse(string, parsePosition, false, d, n3);
                    if (parsePosition.getIndex() > parsePosition2.getIndex()) {
                        object2 = object;
                        parsePosition2.setIndex(parsePosition.getIndex());
                    }
                    parsePosition.setIndex(0);
                    object = object2;
                }
            }
            n = n3;
            object2 = object;
        }
        for (n2 = this.rules.length - 1; n2 >= 0 && parsePosition2.getIndex() < string.length(); --n2) {
            if (!this.isFractionRuleSet && (double)this.rules[n2].getBaseValue() >= d) continue;
            object = this.rules[n2].doParse(string, parsePosition, this.isFractionRuleSet, d, n);
            if (parsePosition.getIndex() > parsePosition2.getIndex()) {
                object2 = object;
                parsePosition2.setIndex(parsePosition.getIndex());
            }
            parsePosition.setIndex(0);
        }
        parsePosition.setIndex(parsePosition2.getIndex());
        return object2;
    }

    public void parseRules(String object) {
        int n;
        ArrayList<NFRule> arrayList = new ArrayList<NFRule>();
        NFRule nFRule = null;
        int n2 = 0;
        int n3 = ((String)object).length();
        do {
            int n4;
            n = n4 = ((String)object).indexOf(59, n2);
            if (n4 < 0) {
                n = n3;
            }
            NFRule.makeRules(((String)object).substring(n2, n), this, nFRule, this.owner, arrayList);
            if (arrayList.isEmpty()) continue;
            nFRule = (NFRule)arrayList.get(arrayList.size() - 1);
        } while ((n2 = n + 1) < n3);
        long l = 0L;
        object = arrayList.iterator();
        while (object.hasNext()) {
            long l2;
            block9 : {
                block8 : {
                    block7 : {
                        nFRule = (NFRule)object.next();
                        l2 = nFRule.getBaseValue();
                        if (l2 != 0L) break block7;
                        nFRule.setBaseValue(l);
                        break block8;
                    }
                    if (l2 < l) break block9;
                    l = l2;
                }
                l2 = l;
                if (!this.isFractionRuleSet) {
                    l2 = l + 1L;
                }
                l = l2;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Rules are not in order, base: ");
            ((StringBuilder)object).append(l2);
            ((StringBuilder)object).append(" < ");
            ((StringBuilder)object).append(l);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        this.rules = new NFRule[arrayList.size()];
        arrayList.toArray(this.rules);
    }

    public void setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        int n;
        NFRule[] object2 = this.rules;
        int n2 = object2.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            object2[n].setDecimalFormatSymbols(decimalFormatSymbols);
        }
        if (this.fractionRules != null) {
            for (n = 1; n <= 3; ++n) {
                if (this.nonNumericalRules[n] == null) continue;
                for (NFRule nFRule : this.fractionRules) {
                    if (this.nonNumericalRules[n].getBaseValue() != nFRule.getBaseValue()) continue;
                    this.setBestFractionRule(n, nFRule, false);
                }
            }
        }
        NFRule[] arrnFRule = this.nonNumericalRules;
        n2 = arrnFRule.length;
        for (n = n3; n < n2; ++n) {
            NFRule nFRule = arrnFRule[n];
            if (nFRule == null) continue;
            nFRule.setDecimalFormatSymbols(decimalFormatSymbols);
        }
    }

    void setNonNumericalRule(NFRule nFRule) {
        long l = nFRule.getBaseValue();
        if (l == -1L) {
            this.nonNumericalRules[0] = nFRule;
        } else if (l == -2L) {
            this.setBestFractionRule(1, nFRule, true);
        } else if (l == -3L) {
            this.setBestFractionRule(2, nFRule, true);
        } else if (l == -4L) {
            this.setBestFractionRule(3, nFRule, true);
        } else if (l == -5L) {
            this.nonNumericalRules[4] = nFRule;
        } else if (l == -6L) {
            this.nonNumericalRules[5] = nFRule;
        }
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(":\n");
        NFRule[] object2 = this.rules;
        int n2 = object2.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            stringBuilder.append(object2[n].toString());
            stringBuilder.append("\n");
        }
        NFRule[] arrnFRule = this.nonNumericalRules;
        n2 = arrnFRule.length;
        for (n = n3; n < n2; ++n) {
            NFRule nFRule = arrnFRule[n];
            if (nFRule == null) continue;
            if (nFRule.getBaseValue() != -2L && nFRule.getBaseValue() != -3L && nFRule.getBaseValue() != -4L) {
                stringBuilder.append(nFRule.toString());
                stringBuilder.append("\n");
                continue;
            }
            for (NFRule nFRule2 : this.fractionRules) {
                if (nFRule2.getBaseValue() != nFRule.getBaseValue()) continue;
                stringBuilder.append(nFRule2.toString());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}

