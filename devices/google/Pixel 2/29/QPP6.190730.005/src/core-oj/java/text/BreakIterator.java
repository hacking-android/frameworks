/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.BreakIterator
 */
package java.text;

import java.text.CharacterIterator;
import java.text.IcuIteratorWrapper;
import java.text.StringCharacterIterator;
import java.util.Locale;

public abstract class BreakIterator
implements Cloneable {
    public static final int DONE = -1;

    protected BreakIterator() {
    }

    public static Locale[] getAvailableLocales() {
        synchronized (BreakIterator.class) {
            Locale[] arrlocale = android.icu.text.BreakIterator.getAvailableLocales();
            return arrlocale;
        }
    }

    public static BreakIterator getCharacterInstance() {
        return BreakIterator.getCharacterInstance(Locale.getDefault());
    }

    public static BreakIterator getCharacterInstance(Locale locale) {
        return new IcuIteratorWrapper(android.icu.text.BreakIterator.getCharacterInstance((Locale)locale));
    }

    public static BreakIterator getLineInstance() {
        return BreakIterator.getLineInstance(Locale.getDefault());
    }

    public static BreakIterator getLineInstance(Locale locale) {
        return new IcuIteratorWrapper(android.icu.text.BreakIterator.getLineInstance((Locale)locale));
    }

    public static BreakIterator getSentenceInstance() {
        return BreakIterator.getSentenceInstance(Locale.getDefault());
    }

    public static BreakIterator getSentenceInstance(Locale locale) {
        return new IcuIteratorWrapper(android.icu.text.BreakIterator.getSentenceInstance((Locale)locale));
    }

    public static BreakIterator getWordInstance() {
        return BreakIterator.getWordInstance(Locale.getDefault());
    }

    public static BreakIterator getWordInstance(Locale locale) {
        return new IcuIteratorWrapper(android.icu.text.BreakIterator.getWordInstance((Locale)locale));
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public abstract int current();

    public abstract int first();

    public abstract int following(int var1);

    public abstract CharacterIterator getText();

    public boolean isBoundary(int n) {
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        int n2 = this.following(n - 1);
        if (n2 != -1) {
            if (n2 != n) {
                bl = false;
            }
            return bl;
        }
        throw new IllegalArgumentException();
    }

    public abstract int last();

    public abstract int next();

    public abstract int next(int var1);

    public int preceding(int n) {
        int n2 = this.following(n);
        while (n2 >= n && n2 != -1) {
            n2 = this.previous();
        }
        return n2;
    }

    public abstract int previous();

    public void setText(String string) {
        this.setText(new StringCharacterIterator(string));
    }

    public abstract void setText(CharacterIterator var1);
}

