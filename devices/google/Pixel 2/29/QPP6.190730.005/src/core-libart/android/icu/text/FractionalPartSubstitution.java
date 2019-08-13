/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.number.DecimalQuantity_DualStorageBCD;
import android.icu.text.DecimalFormat;
import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;
import android.icu.text.RuleBasedNumberFormat;
import java.text.ParsePosition;

class FractionalPartSubstitution
extends NFSubstitution {
    private final boolean byDigits;
    private final boolean useSpaces;

    FractionalPartSubstitution(int n, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, string);
        if (!string.equals(">>") && !string.equals(">>>") && nFRuleSet != this.ruleSet) {
            this.byDigits = false;
            this.useSpaces = true;
            this.ruleSet.makeIntoFractionRuleSet();
        } else {
            this.byDigits = true;
            this.useSpaces = string.equals(">>>") ^ true;
        }
    }

    @Override
    public double calcUpperBound(double d) {
        return 0.0;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d + d2;
    }

    @Override
    public Number doParse(String object, ParsePosition parsePosition, double d, double d2, boolean bl, int n) {
        if (!this.byDigits) {
            return super.doParse((String)object, parsePosition, d, 0.0, bl, n);
        }
        ParsePosition parsePosition2 = new ParsePosition(1);
        DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD();
        int n2 = 0;
        Object object2 = object;
        block0 : while (((String)object2).length() > 0 && parsePosition2.getIndex() != 0) {
            int n3;
            parsePosition2.setIndex(0);
            int n4 = n3 = this.ruleSet.parse((String)object2, parsePosition2, 10.0, n).intValue();
            if (bl) {
                n4 = n3;
                if (parsePosition2.getIndex() == 0) {
                    object = this.ruleSet.owner.getDecimalFormat().parse((String)object2, parsePosition2);
                    n4 = n3;
                    if (object != null) {
                        n4 = ((Number)object).intValue();
                    }
                }
            }
            if (parsePosition2.getIndex() == 0) continue;
            decimalQuantity_DualStorageBCD.appendDigit((byte)n4, 0, true);
            n4 = n2 + 1;
            parsePosition.setIndex(parsePosition.getIndex() + parsePosition2.getIndex());
            object = ((String)object2).substring(parsePosition2.getIndex());
            do {
                object2 = object;
                n2 = n4;
                if (((String)object).length() <= 0) continue block0;
                object2 = object;
                n2 = n4;
                if (((String)object).charAt(0) != ' ') continue block0;
                object = ((String)object).substring(1);
                parsePosition.setIndex(parsePosition.getIndex() + 1);
            } while (true);
        }
        decimalQuantity_DualStorageBCD.adjustMagnitude(-n2);
        return new Double(this.composeRuleValue(decimalQuantity_DualStorageBCD.toDouble(), d));
    }

    @Override
    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        if (!this.byDigits) {
            super.doSubstitution(d, stringBuilder, n, n2);
        } else {
            DecimalQuantity_DualStorageBCD decimalQuantity_DualStorageBCD = new DecimalQuantity_DualStorageBCD(d);
            decimalQuantity_DualStorageBCD.roundToInfinity();
            boolean bl = false;
            for (int i = decimalQuantity_DualStorageBCD.getLowerDisplayMagnitude(); i < 0; ++i) {
                if (bl && this.useSpaces) {
                    stringBuilder.insert(this.pos + n, ' ');
                } else {
                    bl = true;
                }
                this.ruleSet.format(decimalQuantity_DualStorageBCD.getDigit(i), stringBuilder, n + this.pos, n2);
            }
        }
    }

    @Override
    char tokenChar() {
        return '>';
    }

    @Override
    public double transformNumber(double d) {
        return d - Math.floor(d);
    }

    @Override
    public long transformNumber(long l) {
        return 0L;
    }
}

