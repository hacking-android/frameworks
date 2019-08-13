/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.DecimalFormat;
import android.icu.text.NFRuleSet;
import android.icu.text.NFSubstitution;
import java.text.ParsePosition;

class NumeratorSubstitution
extends NFSubstitution {
    private final double denominator;
    private final boolean withZeros;

    NumeratorSubstitution(int n, double d, NFRuleSet nFRuleSet, String string) {
        super(n, nFRuleSet, NumeratorSubstitution.fixdesc(string));
        this.denominator = d;
        this.withZeros = string.endsWith("<<");
    }

    static String fixdesc(String string) {
        block0 : {
            if (!string.endsWith("<<")) break block0;
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    @Override
    public double calcUpperBound(double d) {
        return this.denominator;
    }

    @Override
    public double composeRuleValue(double d, double d2) {
        return d / d2;
    }

    @Override
    public Number doParse(String object, ParsePosition object2, double d, double d2, boolean bl, int n) {
        int n2 = 0;
        if (this.withZeros) {
            String string = object;
            ParsePosition parsePosition = new ParsePosition(1);
            block0 : while (string.length() > 0 && parsePosition.getIndex() != 0) {
                parsePosition.setIndex(0);
                this.ruleSet.parse(string, parsePosition, 1.0, n).intValue();
                if (parsePosition.getIndex() == 0) break;
                int n3 = n2 + 1;
                ((ParsePosition)object2).setIndex(((ParsePosition)object2).getIndex() + parsePosition.getIndex());
                String string2 = string.substring(parsePosition.getIndex());
                do {
                    n2 = n3;
                    string = string2;
                    if (string2.length() <= 0) continue block0;
                    n2 = n3;
                    string = string2;
                    if (string2.charAt(0) != ' ') continue block0;
                    string2 = string2.substring(1);
                    ((ParsePosition)object2).setIndex(((ParsePosition)object2).getIndex() + 1);
                } while (true);
            }
            object = ((String)object).substring(((ParsePosition)object2).getIndex());
            ((ParsePosition)object2).setIndex(0);
        } else {
            n2 = 0;
        }
        if (this.withZeros) {
            d = 1.0;
        }
        object = object2 = super.doParse((String)object, (ParsePosition)object2, d, d2, false, n);
        if (this.withZeros) {
            long l;
            long l2 = ((Number)object2).longValue();
            long l3 = 1L;
            do {
                l = l3;
                if (l3 > l2) break;
                l3 *= 10L;
            } while (true);
            for (n = n2; n > 0; --n) {
                l *= 10L;
            }
            object = new Double((double)l2 / (double)l);
        }
        return object;
    }

    @Override
    public void doSubstitution(double d, StringBuilder stringBuilder, int n, int n2) {
        d = this.transformNumber(d);
        if (this.withZeros && this.ruleSet != null) {
            long l = (long)d;
            int n3 = stringBuilder.length();
            while ((double)(l = 10L * l) < this.denominator) {
                stringBuilder.insert(n + this.pos, ' ');
                this.ruleSet.format(0L, stringBuilder, n + this.pos, n2);
            }
            n += stringBuilder.length() - n3;
        }
        if (d == Math.floor(d) && this.ruleSet != null) {
            this.ruleSet.format((long)d, stringBuilder, n + this.pos, n2);
        } else if (this.ruleSet != null) {
            this.ruleSet.format(d, stringBuilder, n + this.pos, n2);
        } else {
            stringBuilder.insert(this.pos + n, this.numberFormat.format(d));
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object);
        boolean bl2 = false;
        if (bl) {
            object = (NumeratorSubstitution)object;
            bl = bl2;
            if (this.denominator == ((NumeratorSubstitution)object).denominator) {
                bl = bl2;
                if (this.withZeros == ((NumeratorSubstitution)object).withZeros) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    @Override
    char tokenChar() {
        return '<';
    }

    @Override
    public double transformNumber(double d) {
        return Math.round(this.denominator * d);
    }

    @Override
    public long transformNumber(long l) {
        return Math.round((double)l * this.denominator);
    }
}

