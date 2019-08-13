/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.Normalizer;
import android.icu.text.Normalizer2;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUUncheckedIOException;
import java.io.IOException;

public class FilteredNormalizer2
extends Normalizer2 {
    private Normalizer2 norm2;
    private UnicodeSet set;

    public FilteredNormalizer2(Normalizer2 normalizer2, UnicodeSet unicodeSet) {
        this.norm2 = normalizer2;
        this.set = unicodeSet;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Appendable normalize(CharSequence charSequence, Appendable appendable, UnicodeSet.SpanCondition spanCondition) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        try {
            while (n < charSequence.length()) {
                int n2 = this.set.span(charSequence, n, spanCondition);
                int n3 = n2 - n;
                if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                    if (n3 != 0) {
                        appendable.append(charSequence, n, n2);
                    }
                    spanCondition = UnicodeSet.SpanCondition.SIMPLE;
                } else {
                    if (n3 != 0) {
                        appendable.append(this.norm2.normalize(charSequence.subSequence(n, n2), stringBuilder));
                    }
                    spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
                }
                n = n2;
            }
            return appendable;
        }
        catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    private StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence, boolean bl) {
        if (stringBuilder != charSequence) {
            if (stringBuilder.length() == 0) {
                if (bl) {
                    return this.normalize(charSequence, stringBuilder);
                }
                stringBuilder.append(charSequence);
                return stringBuilder;
            }
            int n = this.set.span(charSequence, 0, UnicodeSet.SpanCondition.SIMPLE);
            if (n != 0) {
                CharSequence charSequence2 = charSequence.subSequence(0, n);
                int n2 = this.set.spanBack(stringBuilder, Integer.MAX_VALUE, UnicodeSet.SpanCondition.SIMPLE);
                if (n2 == 0) {
                    if (bl) {
                        this.norm2.normalizeSecondAndAppend(stringBuilder, charSequence2);
                    } else {
                        this.norm2.append(stringBuilder, charSequence2);
                    }
                } else {
                    StringBuilder stringBuilder2 = new StringBuilder(stringBuilder.subSequence(n2, stringBuilder.length()));
                    if (bl) {
                        this.norm2.normalizeSecondAndAppend(stringBuilder2, charSequence2);
                    } else {
                        this.norm2.append(stringBuilder2, charSequence2);
                    }
                    stringBuilder.delete(n2, Integer.MAX_VALUE).append(stringBuilder2);
                }
            }
            if (n < charSequence.length()) {
                charSequence = charSequence.subSequence(n, charSequence.length());
                if (bl) {
                    this.normalize(charSequence, stringBuilder, UnicodeSet.SpanCondition.NOT_CONTAINED);
                } else {
                    stringBuilder.append(charSequence);
                }
            }
            return stringBuilder;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public StringBuilder append(StringBuilder stringBuilder, CharSequence charSequence) {
        return this.normalizeSecondAndAppend(stringBuilder, charSequence, false);
    }

    @Override
    public int composePair(int n, int n2) {
        n = this.set.contains(n) && this.set.contains(n2) ? this.norm2.composePair(n, n2) : -1;
        return n;
    }

    @Override
    public int getCombiningClass(int n) {
        n = this.set.contains(n) ? this.norm2.getCombiningClass(n) : 0;
        return n;
    }

    @Override
    public String getDecomposition(int n) {
        String string = this.set.contains(n) ? this.norm2.getDecomposition(n) : null;
        return string;
    }

    @Override
    public String getRawDecomposition(int n) {
        String string = this.set.contains(n) ? this.norm2.getRawDecomposition(n) : null;
        return string;
    }

    @Override
    public boolean hasBoundaryAfter(int n) {
        boolean bl = !this.set.contains(n) || this.norm2.hasBoundaryAfter(n);
        return bl;
    }

    @Override
    public boolean hasBoundaryBefore(int n) {
        boolean bl = !this.set.contains(n) || this.norm2.hasBoundaryBefore(n);
        return bl;
    }

    @Override
    public boolean isInert(int n) {
        boolean bl = !this.set.contains(n) || this.norm2.isInert(n);
        return bl;
    }

    @Override
    public boolean isNormalized(CharSequence charSequence) {
        UnicodeSet.SpanCondition spanCondition = UnicodeSet.SpanCondition.SIMPLE;
        int n = 0;
        while (n < charSequence.length()) {
            int n2 = this.set.span(charSequence, n, spanCondition);
            if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                spanCondition = UnicodeSet.SpanCondition.SIMPLE;
            } else {
                if (!this.norm2.isNormalized(charSequence.subSequence(n, n2))) {
                    return false;
                }
                spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
            n = n2;
        }
        return true;
    }

    @Override
    public Appendable normalize(CharSequence charSequence, Appendable appendable) {
        if (appendable != charSequence) {
            return this.normalize(charSequence, appendable, UnicodeSet.SpanCondition.SIMPLE);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public StringBuilder normalize(CharSequence charSequence, StringBuilder stringBuilder) {
        if (stringBuilder != charSequence) {
            stringBuilder.setLength(0);
            this.normalize(charSequence, stringBuilder, UnicodeSet.SpanCondition.SIMPLE);
            return stringBuilder;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public StringBuilder normalizeSecondAndAppend(StringBuilder stringBuilder, CharSequence charSequence) {
        return this.normalizeSecondAndAppend(stringBuilder, charSequence, true);
    }

    @Override
    public Normalizer.QuickCheckResult quickCheck(CharSequence charSequence) {
        Object object = Normalizer.YES;
        Object object2 = UnicodeSet.SpanCondition.SIMPLE;
        int n = 0;
        while (n < charSequence.length()) {
            int n2 = this.set.span(charSequence, n, (UnicodeSet.SpanCondition)((Object)object2));
            if (object2 == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                object2 = UnicodeSet.SpanCondition.SIMPLE;
            } else {
                object2 = this.norm2.quickCheck(charSequence.subSequence(n, n2));
                if (object2 == Normalizer.NO) {
                    return object2;
                }
                if (object2 == Normalizer.MAYBE) {
                    object = object2;
                }
                object2 = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
            n = n2;
        }
        return object;
    }

    @Override
    public int spanQuickCheckYes(CharSequence charSequence) {
        UnicodeSet.SpanCondition spanCondition = UnicodeSet.SpanCondition.SIMPLE;
        int n = 0;
        while (n < charSequence.length()) {
            int n2 = this.set.span(charSequence, n, spanCondition);
            if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
                spanCondition = UnicodeSet.SpanCondition.SIMPLE;
            } else {
                if ((n = this.norm2.spanQuickCheckYes(charSequence.subSequence(n, n2)) + n) < n2) {
                    return n;
                }
                spanCondition = UnicodeSet.SpanCondition.NOT_CONTAINED;
            }
            n = n2;
        }
        return charSequence.length();
    }
}

