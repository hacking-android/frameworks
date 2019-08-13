/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.UnicodeSet;
import android.icu.util.OutputInt;

public class UnicodeSetSpanner {
    private final UnicodeSet unicodeSet;

    public UnicodeSetSpanner(UnicodeSet unicodeSet) {
        this.unicodeSet = unicodeSet;
    }

    public int countIn(CharSequence charSequence) {
        return this.countIn(charSequence, CountMethod.MIN_ELEMENTS, UnicodeSet.SpanCondition.SIMPLE);
    }

    public int countIn(CharSequence charSequence, CountMethod countMethod) {
        return this.countIn(charSequence, countMethod, UnicodeSet.SpanCondition.SIMPLE);
    }

    public int countIn(CharSequence charSequence, CountMethod countMethod, UnicodeSet.SpanCondition spanCondition) {
        int n = 0;
        int n2 = 0;
        UnicodeSet.SpanCondition spanCondition2 = spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED ? UnicodeSet.SpanCondition.SIMPLE : UnicodeSet.SpanCondition.NOT_CONTAINED;
        int n3 = charSequence.length();
        OutputInt outputInt = null;
        while (n2 != n3 && (n2 = this.unicodeSet.span(charSequence, n2, spanCondition2)) != n3) {
            if (countMethod == CountMethod.WHOLE_SPAN) {
                n2 = this.unicodeSet.span(charSequence, n2, spanCondition);
                ++n;
                continue;
            }
            OutputInt outputInt2 = outputInt;
            if (outputInt == null) {
                outputInt2 = new OutputInt();
            }
            n2 = this.unicodeSet.spanAndCount(charSequence, n2, spanCondition, outputInt2);
            n += outputInt2.value;
            outputInt = outputInt2;
        }
        return n;
    }

    public String deleteFrom(CharSequence charSequence) {
        return this.replaceFrom(charSequence, "", CountMethod.WHOLE_SPAN, UnicodeSet.SpanCondition.SIMPLE);
    }

    public String deleteFrom(CharSequence charSequence, UnicodeSet.SpanCondition spanCondition) {
        return this.replaceFrom(charSequence, "", CountMethod.WHOLE_SPAN, spanCondition);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof UnicodeSetSpanner && this.unicodeSet.equals(((UnicodeSetSpanner)object).unicodeSet);
        return bl;
    }

    public UnicodeSet getUnicodeSet() {
        return this.unicodeSet;
    }

    public int hashCode() {
        return this.unicodeSet.hashCode();
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        return this.replaceFrom(charSequence, charSequence2, CountMethod.MIN_ELEMENTS, UnicodeSet.SpanCondition.SIMPLE);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2, CountMethod countMethod) {
        return this.replaceFrom(charSequence, charSequence2, countMethod, UnicodeSet.SpanCondition.SIMPLE);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2, CountMethod countMethod, UnicodeSet.SpanCondition spanCondition) {
        UnicodeSet.SpanCondition spanCondition2 = spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED ? UnicodeSet.SpanCondition.SIMPLE : UnicodeSet.SpanCondition.NOT_CONTAINED;
        boolean bl = charSequence2.length() == 0;
        StringBuilder stringBuilder = new StringBuilder();
        int n = charSequence.length();
        OutputInt outputInt = null;
        int n2 = 0;
        while (n2 != n) {
            int n3;
            OutputInt outputInt2;
            if (countMethod == CountMethod.WHOLE_SPAN) {
                n2 = this.unicodeSet.span(charSequence, n2, spanCondition);
                outputInt2 = outputInt;
            } else {
                outputInt2 = outputInt;
                if (outputInt == null) {
                    outputInt2 = new OutputInt();
                }
                n2 = this.unicodeSet.spanAndCount(charSequence, n2, spanCondition, outputInt2);
            }
            if (!bl && n2 != 0) {
                if (countMethod == CountMethod.WHOLE_SPAN) {
                    stringBuilder.append(charSequence2);
                } else {
                    for (n3 = outputInt2.value; n3 > 0; --n3) {
                        stringBuilder.append(charSequence2);
                    }
                }
            }
            if (n2 == n) break;
            n3 = this.unicodeSet.span(charSequence, n2, spanCondition2);
            stringBuilder.append(charSequence.subSequence(n2, n3));
            outputInt = outputInt2;
            n2 = n3;
        }
        return stringBuilder.toString();
    }

    public CharSequence trim(CharSequence charSequence) {
        return this.trim(charSequence, TrimOption.BOTH, UnicodeSet.SpanCondition.SIMPLE);
    }

    public CharSequence trim(CharSequence charSequence, TrimOption trimOption) {
        return this.trim(charSequence, trimOption, UnicodeSet.SpanCondition.SIMPLE);
    }

    public CharSequence trim(CharSequence charSequence, TrimOption trimOption, UnicodeSet.SpanCondition spanCondition) {
        int n;
        int n2;
        int n3 = charSequence.length();
        if (trimOption != TrimOption.TRAILING) {
            n = n2 = this.unicodeSet.span(charSequence, spanCondition);
            if (n2 == n3) {
                return "";
            }
        } else {
            n = 0;
        }
        n2 = trimOption != TrimOption.LEADING ? this.unicodeSet.spanBack(charSequence, spanCondition) : n3;
        if (n != 0 || n2 != n3) {
            charSequence = charSequence.subSequence(n, n2);
        }
        return charSequence;
    }

    public static enum CountMethod {
        WHOLE_SPAN,
        MIN_ELEMENTS;
        
    }

    public static enum TrimOption {
        LEADING,
        BOTH,
        TRAILING;
        
    }

}

