/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.text.Replaceable;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeSet;

class Quantifier
implements UnicodeMatcher {
    public static final int MAX = Integer.MAX_VALUE;
    private UnicodeMatcher matcher;
    private int maxCount;
    private int minCount;

    public Quantifier(UnicodeMatcher unicodeMatcher, int n, int n2) {
        if (unicodeMatcher != null && n >= 0 && n2 >= 0 && n <= n2) {
            this.matcher = unicodeMatcher;
            this.minCount = n;
            this.maxCount = n2;
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        if (this.maxCount > 0) {
            this.matcher.addMatchSetTo(unicodeSet);
        }
    }

    @Override
    public int matches(Replaceable replaceable, int[] arrn, int n, boolean bl) {
        int n2;
        int n3;
        block5 : {
            int n4;
            int n5;
            block6 : {
                n3 = arrn[0];
                n5 = 0;
                do {
                    n2 = n5;
                    if (n5 >= this.maxCount) break block5;
                    n2 = arrn[0];
                    n4 = this.matcher.matches(replaceable, arrn, n, bl);
                    if (n4 != 2) break block6;
                    ++n5;
                } while (n2 != arrn[0]);
                n2 = n5;
                break block5;
            }
            n2 = n5;
            if (bl) {
                n2 = n5;
                if (n4 == 1) {
                    return 1;
                }
            }
        }
        if (bl && arrn[0] == n) {
            return 1;
        }
        if (n2 >= this.minCount) {
            return 2;
        }
        arrn[0] = n3;
        return 0;
    }

    @Override
    public boolean matchesIndexValue(int n) {
        boolean bl = this.minCount == 0 || this.matcher.matchesIndexValue(n);
        return bl;
    }

    @Override
    public String toPattern(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.matcher.toPattern(bl));
        int n = this.minCount;
        if (n == 0) {
            n = this.maxCount;
            if (n == 1) {
                stringBuilder.append('?');
                return stringBuilder.toString();
            }
            if (n == Integer.MAX_VALUE) {
                stringBuilder.append('*');
                return stringBuilder.toString();
            }
        } else if (n == 1 && this.maxCount == Integer.MAX_VALUE) {
            stringBuilder.append('+');
            return stringBuilder.toString();
        }
        stringBuilder.append('{');
        stringBuilder.append(Utility.hex(this.minCount, 1));
        stringBuilder.append(',');
        n = this.maxCount;
        if (n != Integer.MAX_VALUE) {
            stringBuilder.append(Utility.hex(n, 1));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

