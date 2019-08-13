/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.SimpleFilteredSentenceBreakIterator;
import android.icu.text.BreakIterator;
import android.icu.util.ULocale;
import java.util.Locale;

public abstract class FilteredBreakIteratorBuilder {
    @Deprecated
    protected FilteredBreakIteratorBuilder() {
    }

    public static final FilteredBreakIteratorBuilder getEmptyInstance() {
        return new SimpleFilteredSentenceBreakIterator.Builder();
    }

    public static final FilteredBreakIteratorBuilder getInstance(ULocale uLocale) {
        return new SimpleFilteredSentenceBreakIterator.Builder(uLocale);
    }

    public static final FilteredBreakIteratorBuilder getInstance(Locale locale) {
        return new SimpleFilteredSentenceBreakIterator.Builder(locale);
    }

    public abstract boolean suppressBreakAfter(CharSequence var1);

    public abstract boolean unsuppressBreakAfter(CharSequence var1);

    public abstract BreakIterator wrapIteratorWithFilter(BreakIterator var1);
}

