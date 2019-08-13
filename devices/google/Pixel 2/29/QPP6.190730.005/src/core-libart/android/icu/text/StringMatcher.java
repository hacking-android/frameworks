/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.text.Replaceable;
import android.icu.text.RuleBasedTransliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeReplacer;
import android.icu.text.UnicodeSet;

class StringMatcher
implements UnicodeMatcher,
UnicodeReplacer {
    private final RuleBasedTransliterator.Data data;
    private int matchLimit;
    private int matchStart;
    private String pattern;
    private int segmentNumber;

    public StringMatcher(String string, int n, int n2, int n3, RuleBasedTransliterator.Data data) {
        this(string.substring(n, n2), n3, data);
    }

    public StringMatcher(String string, int n, RuleBasedTransliterator.Data data) {
        this.data = data;
        this.pattern = string;
        this.matchLimit = -1;
        this.matchStart = -1;
        this.segmentNumber = n;
    }

    @Override
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        int n;
        for (int i = 0; i < this.pattern.length(); i += UTF16.getCharCount((int)n)) {
            n = UTF16.charAt(this.pattern, i);
            UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n);
            if (unicodeMatcher == null) {
                unicodeSet.add(n);
                continue;
            }
            unicodeMatcher.addMatchSetTo(unicodeSet);
        }
    }

    @Override
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
    }

    @Override
    public int matches(Replaceable replaceable, int[] arrn, int n, boolean bl) {
        int[] arrn2 = new int[]{arrn[0]};
        if (n < arrn2[0]) {
            for (int i = this.pattern.length() - 1; i >= 0; --i) {
                int n2 = this.pattern.charAt(i);
                UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n2);
                if (unicodeMatcher == null) {
                    if (arrn2[0] > n && n2 == replaceable.charAt(arrn2[0])) {
                        arrn2[0] = arrn2[0] - 1;
                        continue;
                    }
                    return 0;
                }
                n2 = unicodeMatcher.matches(replaceable, arrn2, n, bl);
                if (n2 == 2) continue;
                return n2;
            }
            if (this.matchStart < 0) {
                this.matchStart = arrn2[0] + 1;
                this.matchLimit = arrn[0] + 1;
            }
        } else {
            for (int i = 0; i < this.pattern.length(); ++i) {
                if (bl && arrn2[0] == n) {
                    return 1;
                }
                int n3 = this.pattern.charAt(i);
                UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n3);
                if (unicodeMatcher == null) {
                    if (arrn2[0] < n && n3 == replaceable.charAt(arrn2[0])) {
                        arrn2[0] = arrn2[0] + 1;
                        continue;
                    }
                    return 0;
                }
                n3 = unicodeMatcher.matches(replaceable, arrn2, n, bl);
                if (n3 == 2) continue;
                return n3;
            }
            this.matchStart = arrn[0];
            this.matchLimit = arrn2[0];
        }
        arrn[0] = arrn2[0];
        return 2;
    }

    @Override
    public boolean matchesIndexValue(int n) {
        int n2 = this.pattern.length();
        boolean bl = true;
        if (n2 == 0) {
            return true;
        }
        n2 = UTF16.charAt(this.pattern, 0);
        UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n2);
        if (unicodeMatcher == null) {
            if ((n2 & 255) != n) {
                bl = false;
            }
        } else {
            bl = unicodeMatcher.matchesIndexValue(n);
        }
        return bl;
    }

    @Override
    public int replace(Replaceable replaceable, int n, int n2, int[] arrn) {
        int n3 = 0;
        int n4 = this.matchStart;
        int n5 = n3;
        if (n4 >= 0) {
            int n6 = this.matchLimit;
            n5 = n3;
            if (n4 != n6) {
                replaceable.copy(n4, n6, n2);
                n5 = this.matchLimit - this.matchStart;
            }
        }
        replaceable.replace(n, n2, "");
        return n5;
    }

    public void resetMatch() {
        this.matchLimit = -1;
        this.matchStart = -1;
    }

    @Override
    public String toPattern(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (this.segmentNumber > 0) {
            stringBuffer.append('(');
        }
        for (int i = 0; i < this.pattern.length(); ++i) {
            char c = this.pattern.charAt(i);
            UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(c);
            if (unicodeMatcher == null) {
                Utility.appendToRule(stringBuffer, c, false, bl, stringBuffer2);
                continue;
            }
            Utility.appendToRule(stringBuffer, unicodeMatcher.toPattern(bl), true, bl, stringBuffer2);
        }
        if (this.segmentNumber > 0) {
            stringBuffer.append(')');
        }
        Utility.appendToRule(stringBuffer, -1, true, bl, stringBuffer2);
        return stringBuffer.toString();
    }

    @Override
    public String toReplacerPattern(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer("$");
        Utility.appendNumber(stringBuffer, this.segmentNumber, 10, 1);
        return stringBuffer.toString();
    }
}

