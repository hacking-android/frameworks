/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CSCharacterIterator;
import android.icu.impl.CacheValue;
import android.icu.impl.ICUDebug;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ULocale;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;
import java.util.MissingResourceException;

public abstract class BreakIterator
implements Cloneable {
    private static final boolean DEBUG = ICUDebug.enabled("breakiterator");
    public static final int DONE = -1;
    public static final int KIND_CHARACTER = 0;
    private static final int KIND_COUNT = 5;
    public static final int KIND_LINE = 2;
    public static final int KIND_SENTENCE = 3;
    @Deprecated
    public static final int KIND_TITLE = 4;
    public static final int KIND_WORD = 1;
    public static final int WORD_IDEO = 400;
    public static final int WORD_IDEO_LIMIT = 500;
    public static final int WORD_KANA = 300;
    public static final int WORD_KANA_LIMIT = 400;
    public static final int WORD_LETTER = 200;
    public static final int WORD_LETTER_LIMIT = 300;
    public static final int WORD_NONE = 0;
    public static final int WORD_NONE_LIMIT = 100;
    public static final int WORD_NUMBER = 100;
    public static final int WORD_NUMBER_LIMIT = 200;
    private static final CacheValue<?>[] iterCache = new CacheValue[5];
    private static BreakIteratorServiceShim shim;
    private ULocale actualLocale;
    private ULocale validLocale;

    protected BreakIterator() {
    }

    public static Locale[] getAvailableLocales() {
        synchronized (BreakIterator.class) {
            Locale[] arrlocale = BreakIterator.getShim().getAvailableLocales();
            return arrlocale;
        }
    }

    public static ULocale[] getAvailableULocales() {
        synchronized (BreakIterator.class) {
            ULocale[] arruLocale = BreakIterator.getShim().getAvailableULocales();
            return arruLocale;
        }
    }

    @Deprecated
    public static BreakIterator getBreakInstance(ULocale object, int n) {
        if (object != null) {
            Object object2 = iterCache;
            if (object2[n] != null && (object2 = (BreakIteratorCache)object2[n].get()) != null && ((BreakIteratorCache)object2).getLocale().equals(object)) {
                return ((BreakIteratorCache)object2).createBreakInstance();
            }
            object2 = BreakIterator.getShim().createBreakIterator((ULocale)object, n);
            object = new BreakIteratorCache((ULocale)object, (BreakIterator)object2);
            BreakIterator.iterCache[n] = CacheValue.getInstance(object);
            return object2;
        }
        throw new NullPointerException("Specified locale is null");
    }

    public static BreakIterator getCharacterInstance() {
        return BreakIterator.getCharacterInstance(ULocale.getDefault());
    }

    public static BreakIterator getCharacterInstance(ULocale uLocale) {
        return BreakIterator.getBreakInstance(uLocale, 0);
    }

    public static BreakIterator getCharacterInstance(Locale locale) {
        return BreakIterator.getBreakInstance(ULocale.forLocale(locale), 0);
    }

    public static BreakIterator getLineInstance() {
        return BreakIterator.getLineInstance(ULocale.getDefault());
    }

    public static BreakIterator getLineInstance(ULocale uLocale) {
        return BreakIterator.getBreakInstance(uLocale, 2);
    }

    public static BreakIterator getLineInstance(Locale locale) {
        return BreakIterator.getBreakInstance(ULocale.forLocale(locale), 2);
    }

    public static BreakIterator getSentenceInstance() {
        return BreakIterator.getSentenceInstance(ULocale.getDefault());
    }

    public static BreakIterator getSentenceInstance(ULocale uLocale) {
        return BreakIterator.getBreakInstance(uLocale, 3);
    }

    public static BreakIterator getSentenceInstance(Locale locale) {
        return BreakIterator.getBreakInstance(ULocale.forLocale(locale), 3);
    }

    private static BreakIteratorServiceShim getShim() {
        if (shim == null) {
            try {
                shim = (BreakIteratorServiceShim)Class.forName("android.icu.text.BreakIteratorFactory").newInstance();
            }
            catch (Exception exception) {
                if (DEBUG) {
                    exception.printStackTrace();
                }
                throw new RuntimeException(exception.getMessage());
            }
            catch (MissingResourceException missingResourceException) {
                throw missingResourceException;
            }
        }
        return shim;
    }

    @Deprecated
    public static BreakIterator getTitleInstance() {
        return BreakIterator.getTitleInstance(ULocale.getDefault());
    }

    @Deprecated
    public static BreakIterator getTitleInstance(ULocale uLocale) {
        return BreakIterator.getBreakInstance(uLocale, 4);
    }

    @Deprecated
    public static BreakIterator getTitleInstance(Locale locale) {
        return BreakIterator.getBreakInstance(ULocale.forLocale(locale), 4);
    }

    public static BreakIterator getWordInstance() {
        return BreakIterator.getWordInstance(ULocale.getDefault());
    }

    public static BreakIterator getWordInstance(ULocale uLocale) {
        return BreakIterator.getBreakInstance(uLocale, 1);
    }

    public static BreakIterator getWordInstance(Locale locale) {
        return BreakIterator.getBreakInstance(ULocale.forLocale(locale), 1);
    }

    public static Object registerInstance(BreakIterator breakIterator, ULocale uLocale, int n) {
        Object object = iterCache;
        if (object[n] != null && (object = (BreakIteratorCache)object[n].get()) != null && ((BreakIteratorCache)object).getLocale().equals(uLocale)) {
            BreakIterator.iterCache[n] = null;
        }
        return BreakIterator.getShim().registerInstance(breakIterator, uLocale, n);
    }

    public static Object registerInstance(BreakIterator breakIterator, Locale locale, int n) {
        return BreakIterator.registerInstance(breakIterator, ULocale.forLocale(locale), n);
    }

    public static boolean unregister(Object object) {
        if (object != null) {
            if (shim != null) {
                for (int i = 0; i < 5; ++i) {
                    BreakIterator.iterCache[i] = null;
                }
                return shim.unregister(object);
            }
            return false;
        }
        throw new IllegalArgumentException("registry key must not be null");
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public abstract int current();

    public abstract int first();

    public abstract int following(int var1);

    public final ULocale getLocale(ULocale.Type object) {
        object = object == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
        return object;
    }

    public int getRuleStatus() {
        return 0;
    }

    public int getRuleStatusVec(int[] arrn) {
        if (arrn != null && arrn.length > 0) {
            arrn[0] = 0;
        }
        return 1;
    }

    public abstract CharacterIterator getText();

    public boolean isBoundary(int n) {
        boolean bl = true;
        if (n == 0) {
            return true;
        }
        if (this.following(n - 1) != n) {
            bl = false;
        }
        return bl;
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

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        boolean bl = true;
        boolean bl2 = uLocale == null;
        if (uLocale2 != null) {
            bl = false;
        }
        if (bl2 == bl) {
            this.validLocale = uLocale;
            this.actualLocale = uLocale2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setText(CharSequence charSequence) {
        this.setText(new CSCharacterIterator(charSequence));
    }

    public void setText(String string) {
        this.setText(new StringCharacterIterator(string));
    }

    public abstract void setText(CharacterIterator var1);

    private static final class BreakIteratorCache {
        private BreakIterator iter;
        private ULocale where;

        BreakIteratorCache(ULocale uLocale, BreakIterator breakIterator) {
            this.where = uLocale;
            this.iter = (BreakIterator)breakIterator.clone();
        }

        BreakIterator createBreakInstance() {
            return (BreakIterator)this.iter.clone();
        }

        ULocale getLocale() {
            return this.where;
        }
    }

    static abstract class BreakIteratorServiceShim {
        BreakIteratorServiceShim() {
        }

        public abstract BreakIterator createBreakIterator(ULocale var1, int var2);

        public abstract Locale[] getAvailableLocales();

        public abstract ULocale[] getAvailableULocales();

        public abstract Object registerInstance(BreakIterator var1, ULocale var2, int var3);

        public abstract boolean unregister(Object var1);
    }

}

