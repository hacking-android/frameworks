/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.text.BreakIterator;
import android.icu.text.FilteredBreakIteratorBuilder;
import android.icu.text.UCharacterIterator;
import android.icu.util.BytesTrie;
import android.icu.util.CharsTrie;
import android.icu.util.CharsTrieBuilder;
import android.icu.util.StringTrieBuilder;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class SimpleFilteredSentenceBreakIterator
extends BreakIterator {
    private CharsTrie backwardsTrie;
    private BreakIterator delegate;
    private CharsTrie forwardsPartialTrie;
    private UCharacterIterator text;

    public SimpleFilteredSentenceBreakIterator(BreakIterator breakIterator, CharsTrie charsTrie, CharsTrie charsTrie2) {
        this.delegate = breakIterator;
        this.forwardsPartialTrie = charsTrie;
        this.backwardsTrie = charsTrie2;
    }

    private final boolean breakExceptionAt(int n) {
        BytesTrie.Result result;
        Object object;
        int n2 = -1;
        int n3 = -1;
        this.text.setIndex(n);
        this.backwardsTrie.reset();
        if (this.text.previousCodePoint() != 32) {
            this.text.nextCodePoint();
        }
        BytesTrie.Result result2 = BytesTrie.Result.INTERMEDIATE_VALUE;
        n = n3;
        while ((n3 = this.text.previousCodePoint()) != -1) {
            result2 = object = (result = this.backwardsTrie.nextForCodePoint(n3));
            if (!result.hasNext()) break;
            result2 = object;
            if (!object.hasValue()) continue;
            n2 = this.text.getIndex();
            n = this.backwardsTrie.getValue();
            result2 = object;
        }
        if (result2.matches()) {
            n = this.backwardsTrie.getValue();
            n2 = this.text.getIndex();
        }
        if (n2 >= 0) {
            if (n == 2) {
                return true;
            }
            if (n == 1 && (object = this.forwardsPartialTrie) != null) {
                ((CharsTrie)object).reset();
                object = BytesTrie.Result.INTERMEDIATE_VALUE;
                this.text.setIndex(n2);
                do {
                    n = this.text.nextCodePoint();
                    result2 = object;
                    if (n == -1) break;
                    result2 = object = (result = this.forwardsPartialTrie.nextForCodePoint(n));
                } while (result.hasNext());
                if (result2.matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final int internalNext(int n) {
        if (n != -1 && this.backwardsTrie != null) {
            this.resetState();
            int n2 = this.text.getLength();
            while (n != -1 && n != n2) {
                if (this.breakExceptionAt(n)) {
                    n = this.delegate.next();
                    continue;
                }
                return n;
            }
            return n;
        }
        return n;
    }

    private final int internalPrev(int n) {
        if (n != 0 && n != -1 && this.backwardsTrie != null) {
            this.resetState();
            while (n != -1 && n != 0) {
                if (this.breakExceptionAt(n)) {
                    n = this.delegate.previous();
                    continue;
                }
                return n;
            }
            return n;
        }
        return n;
    }

    private final void resetState() {
        this.text = UCharacterIterator.getInstance((CharacterIterator)this.delegate.getText().clone());
    }

    @Override
    public Object clone() {
        return (SimpleFilteredSentenceBreakIterator)super.clone();
    }

    @Override
    public int current() {
        return this.delegate.current();
    }

    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            object = (SimpleFilteredSentenceBreakIterator)object;
            if (!this.delegate.equals(((SimpleFilteredSentenceBreakIterator)object).delegate) || !this.text.equals(((SimpleFilteredSentenceBreakIterator)object).text) || !this.backwardsTrie.equals(((SimpleFilteredSentenceBreakIterator)object).backwardsTrie) || !this.forwardsPartialTrie.equals(((SimpleFilteredSentenceBreakIterator)object).forwardsPartialTrie)) break block3;
            bl = true;
        }
        return bl;
    }

    @Override
    public int first() {
        return this.delegate.first();
    }

    @Override
    public int following(int n) {
        return this.internalNext(this.delegate.following(n));
    }

    @Override
    public CharacterIterator getText() {
        return this.delegate.getText();
    }

    public int hashCode() {
        return this.forwardsPartialTrie.hashCode() * 39 + this.backwardsTrie.hashCode() * 11 + this.delegate.hashCode();
    }

    @Override
    public boolean isBoundary(int n) {
        if (!this.delegate.isBoundary(n)) {
            return false;
        }
        if (this.backwardsTrie == null) {
            return true;
        }
        this.resetState();
        return this.breakExceptionAt(n) ^ true;
    }

    @Override
    public int last() {
        return this.delegate.last();
    }

    @Override
    public int next() {
        return this.internalNext(this.delegate.next());
    }

    @Override
    public int next(int n) {
        return this.internalNext(this.delegate.next(n));
    }

    @Override
    public int preceding(int n) {
        return this.internalPrev(this.delegate.preceding(n));
    }

    @Override
    public int previous() {
        return this.internalPrev(this.delegate.previous());
    }

    @Override
    public void setText(CharacterIterator characterIterator) {
        this.delegate.setText(characterIterator);
    }

    public static class Builder
    extends FilteredBreakIteratorBuilder {
        static final int AddToForward = 2;
        static final int MATCH = 2;
        static final int PARTIAL = 1;
        static final int SuppressInReverse = 1;
        private HashSet<CharSequence> filterSet = new HashSet();

        public Builder() {
        }

        public Builder(ULocale object) {
            object = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/brkitr", (ULocale)object, ICUResourceBundle.OpenType.LOCALE_ROOT).findWithFallback("exceptions/SentenceBreak");
            if (object != null) {
                int n = ((UResourceBundle)object).getSize();
                for (int i = 0; i < n; ++i) {
                    String string = ((ICUResourceBundle)((UResourceBundle)object).get(i)).getString();
                    this.filterSet.add(string);
                }
            }
        }

        public Builder(Locale locale) {
            this(ULocale.forLocale(locale));
        }

        @Override
        public boolean suppressBreakAfter(CharSequence charSequence) {
            return this.filterSet.add(charSequence);
        }

        @Override
        public boolean unsuppressBreakAfter(CharSequence charSequence) {
            return this.filterSet.remove(charSequence);
        }

        @Override
        public BreakIterator wrapIteratorWithFilter(BreakIterator breakIterator) {
            int n;
            boolean bl;
            if (this.filterSet.isEmpty()) {
                return breakIterator;
            }
            CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
            CharsTrieBuilder charsTrieBuilder2 = new CharsTrieBuilder();
            int n2 = 0;
            int n3 = 0;
            int n4 = this.filterSet.size();
            CharSequence[] arrcharSequence = new CharSequence[n4];
            int[] arrn = new int[n4];
            CharsTrie charsTrie = null;
            CharsTrie charsTrie2 = null;
            int n5 = 0;
            Object object = this.filterSet.iterator();
            do {
                boolean bl2 = object.hasNext();
                bl = false;
                if (!bl2) break;
                arrcharSequence[n5] = object.next();
                arrn[n5] = 0;
                ++n5;
            } while (true);
            n5 = n3;
            for (n = 0; n < n4; ++n) {
                object = arrcharSequence[n].toString();
                int n6 = ((String)object).indexOf(46);
                if (n6 <= -1) continue;
                if (n6 + 1 != ((String)object).length()) {
                    int n7 = -1;
                    for (n3 = 0; n3 < n4; ++n3) {
                        int n8;
                        if (n3 == n) {
                            n8 = n7;
                        } else {
                            n8 = n7;
                            if (((String)object).regionMatches(0, arrcharSequence[n3].toString(), 0, n6 + 1)) {
                                if (arrn[n3] == 0) {
                                    arrn[n3] = 3;
                                    n8 = n7;
                                } else {
                                    n8 = n7;
                                    if ((arrn[n3] & 1) != 0) {
                                        n8 = n3;
                                    }
                                }
                            }
                        }
                        n7 = n8;
                    }
                    if (n7 == -1 && arrn[n] == 0) {
                        bl = false;
                        object = new StringBuilder(((String)object).substring(0, n6 + 1));
                        ((StringBuilder)object).reverse();
                        charsTrieBuilder.add((CharSequence)object, 1);
                        ++n2;
                        arrn[n] = 3;
                        continue;
                    }
                    bl = false;
                    continue;
                }
                bl = false;
            }
            n = 0;
            n3 = n2;
            for (n2 = n; n2 < n4; ++n2) {
                object = arrcharSequence[n2].toString();
                if (arrn[n2] == 0) {
                    charsTrieBuilder.add(new StringBuilder((String)object).reverse(), 2);
                    ++n3;
                    continue;
                }
                charsTrieBuilder2.add((CharSequence)object, 2);
                ++n5;
            }
            if (n3 > 0) {
                charsTrie = charsTrieBuilder.build(StringTrieBuilder.Option.FAST);
            }
            if (n5 > 0) {
                charsTrie2 = charsTrieBuilder2.build(StringTrieBuilder.Option.FAST);
            }
            return new SimpleFilteredSentenceBreakIterator(breakIterator, charsTrie2, charsTrie);
        }
    }

}

