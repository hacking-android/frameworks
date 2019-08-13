/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ULocale;
import java.text.CharacterIterator;

final class BreakTransliterator
extends Transliterator {
    static final int LETTER_OR_MARK_MASK = 510;
    private BreakIterator bi;
    private int[] boundaries = new int[50];
    private int boundaryCount = 0;
    private String insertion;

    public BreakTransliterator(String string, UnicodeFilter unicodeFilter) {
        this(string, unicodeFilter, null, " ");
    }

    public BreakTransliterator(String string, UnicodeFilter unicodeFilter, BreakIterator breakIterator, String string2) {
        super(string, unicodeFilter);
        this.bi = breakIterator;
        this.insertion = string2;
    }

    static void register() {
        Transliterator.registerInstance(new BreakTransliterator("Any-BreakInternal", null), false);
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        if (this.getFilterAsUnicodeSet(unicodeSet).size() != 0) {
            unicodeSet3.addAll(this.insertion);
        }
    }

    public BreakIterator getBreakIterator() {
        if (this.bi == null) {
            this.bi = BreakIterator.getWordInstance(new ULocale("th_TH"));
        }
        return this.bi;
    }

    public String getInsertion() {
        return this.insertion;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        synchronized (this) {
            int n;
            void var3_3;
            void var2_2;
            this.boundaryCount = 0;
            this.getBreakIterator();
            BreakIterator breakIterator = this.bi;
            int[] arrn = new ReplaceableCharacterIterator(replaceable, var2_2.start, var2_2.limit, var2_2.start);
            breakIterator.setText((CharacterIterator)arrn);
            int n2 = this.bi.first();
            while (n2 != -1 && n2 < var2_2.limit) {
                if (n2 != 0 && (1 << UCharacter.getType(UTF16.charAt(replaceable, n2 - 1)) & 510) != 0 && (1 << UCharacter.getType(UTF16.charAt(replaceable, n2)) & 510) != 0) {
                    if (this.boundaryCount >= this.boundaries.length) {
                        arrn = new int[this.boundaries.length * 2];
                        System.arraycopy(this.boundaries, 0, arrn, 0, this.boundaries.length);
                        this.boundaries = arrn;
                    }
                    arrn = this.boundaries;
                    n = this.boundaryCount;
                    this.boundaryCount = n + 1;
                    arrn[n] = n2;
                }
                n2 = this.bi.next();
            }
            n2 = 0;
            n = 0;
            if (this.boundaryCount != 0) {
                int n3 = this.boundaryCount * this.insertion.length();
                int n4 = this.boundaries[this.boundaryCount - 1];
                do {
                    n2 = n3;
                    n = n4;
                    if (this.boundaryCount <= 0) break;
                    arrn = this.boundaries;
                    this.boundaryCount = n2 = this.boundaryCount - 1;
                    n2 = arrn[n2];
                    replaceable.replace(n2, n2, this.insertion);
                } while (true);
            }
            var2_2.contextLimit += n2;
            var2_2.limit += n2;
            n2 = var3_3 != false ? n + n2 : var2_2.limit;
            var2_2.start = n2;
            return;
        }
    }

    public void setBreakIterator(BreakIterator breakIterator) {
        this.bi = breakIterator;
    }

    public void setInsertion(String string) {
        this.insertion = string;
    }

    static final class ReplaceableCharacterIterator
    implements CharacterIterator {
        private int begin;
        private int end;
        private int pos;
        private Replaceable text;

        public ReplaceableCharacterIterator(Replaceable replaceable, int n, int n2, int n3) {
            if (replaceable != null) {
                this.text = replaceable;
                if (n >= 0 && n <= n2 && n2 <= replaceable.length()) {
                    if (n3 >= n && n3 <= n2) {
                        this.begin = n;
                        this.end = n2;
                        this.pos = n3;
                        return;
                    }
                    throw new IllegalArgumentException("Invalid position");
                }
                throw new IllegalArgumentException("Invalid substring range");
            }
            throw new NullPointerException();
        }

        @Override
        public Object clone() {
            try {
                ReplaceableCharacterIterator replaceableCharacterIterator = (ReplaceableCharacterIterator)super.clone();
                return replaceableCharacterIterator;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException();
            }
        }

        @Override
        public char current() {
            int n = this.pos;
            if (n >= this.begin && n < this.end) {
                return this.text.charAt(n);
            }
            return '\uffff';
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ReplaceableCharacterIterator)) {
                return false;
            }
            object = (ReplaceableCharacterIterator)object;
            if (this.hashCode() != ((ReplaceableCharacterIterator)object).hashCode()) {
                return false;
            }
            if (!this.text.equals(((ReplaceableCharacterIterator)object).text)) {
                return false;
            }
            return this.pos == ((ReplaceableCharacterIterator)object).pos && this.begin == ((ReplaceableCharacterIterator)object).begin && this.end == ((ReplaceableCharacterIterator)object).end;
            {
            }
        }

        @Override
        public char first() {
            this.pos = this.begin;
            return this.current();
        }

        @Override
        public int getBeginIndex() {
            return this.begin;
        }

        @Override
        public int getEndIndex() {
            return this.end;
        }

        @Override
        public int getIndex() {
            return this.pos;
        }

        public int hashCode() {
            return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
        }

        @Override
        public char last() {
            int n = this.end;
            this.pos = n != this.begin ? n - 1 : n;
            return this.current();
        }

        @Override
        public char next() {
            int n = this.pos;
            int n2 = this.end;
            if (n < n2 - 1) {
                this.pos = n + 1;
                return this.text.charAt(this.pos);
            }
            this.pos = n2;
            return '\uffff';
        }

        @Override
        public char previous() {
            int n = this.pos;
            if (n > this.begin) {
                this.pos = n - 1;
                return this.text.charAt(this.pos);
            }
            return '\uffff';
        }

        @Override
        public char setIndex(int n) {
            if (n >= this.begin && n <= this.end) {
                this.pos = n;
                return this.current();
            }
            throw new IllegalArgumentException("Invalid index");
        }

        public void setText(Replaceable replaceable) {
            if (replaceable != null) {
                this.text = replaceable;
                this.begin = 0;
                this.end = replaceable.length();
                this.pos = 0;
                return;
            }
            throw new NullPointerException();
        }
    }

}

