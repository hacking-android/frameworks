/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.lang.UCharacter
 *  android.icu.text.BreakIterator
 */
package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.text.CharSequenceCharacterIterator;
import android.text.Selection;
import java.text.CharacterIterator;
import java.util.Locale;

public class WordIterator
implements Selection.PositionIterator {
    private static final int WINDOW_WIDTH = 50;
    private CharSequence mCharSeq;
    private int mEnd;
    private final BreakIterator mIterator;
    private int mStart;

    public WordIterator() {
        this(Locale.getDefault());
    }

    @UnsupportedAppUsage
    public WordIterator(Locale locale) {
        this.mIterator = BreakIterator.getWordInstance((Locale)locale);
    }

    private void checkOffsetIsValid(int n) {
        if (this.mStart <= n && n <= this.mEnd) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid offset: ");
        stringBuilder.append(n);
        stringBuilder.append(". Valid range is [");
        stringBuilder.append(this.mStart);
        stringBuilder.append(", ");
        stringBuilder.append(this.mEnd);
        stringBuilder.append("]");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private int getBeginning(int n, boolean bl) {
        this.checkOffsetIsValid(n);
        if (this.isOnLetterOrDigit(n)) {
            if (!(!this.mIterator.isBoundary(n) || this.isAfterLetterOrDigit(n) && bl)) {
                return n;
            }
            return this.mIterator.preceding(n);
        }
        if (this.isAfterLetterOrDigit(n)) {
            return this.mIterator.preceding(n);
        }
        return -1;
    }

    private int getEnd(int n, boolean bl) {
        this.checkOffsetIsValid(n);
        if (this.isAfterLetterOrDigit(n)) {
            if (!(!this.mIterator.isBoundary(n) || this.isOnLetterOrDigit(n) && bl)) {
                return n;
            }
            return this.mIterator.following(n);
        }
        if (this.isOnLetterOrDigit(n)) {
            return this.mIterator.following(n);
        }
        return -1;
    }

    private boolean isAfterLetterOrDigit(int n) {
        return this.mStart < n && n <= this.mEnd && Character.isLetterOrDigit(Character.codePointBefore(this.mCharSeq, n));
    }

    public static boolean isMidWordPunctuation(Locale locale, int n) {
        boolean bl = (n = UCharacter.getIntPropertyValue((int)n, (int)4116)) == 4 || n == 11 || n == 15;
        return bl;
    }

    private boolean isOnLetterOrDigit(int n) {
        return this.mStart <= n && n < this.mEnd && Character.isLetterOrDigit(Character.codePointAt(this.mCharSeq, n));
    }

    private static boolean isPunctuation(int n) {
        boolean bl = (n = Character.getType(n)) == 23 || n == 20 || n == 22 || n == 30 || n == 29 || n == 24 || n == 21;
        return bl;
    }

    private boolean isPunctuationEndBoundary(int n) {
        boolean bl = !this.isOnPunctuation(n) && this.isAfterPunctuation(n);
        return bl;
    }

    private boolean isPunctuationStartBoundary(int n) {
        boolean bl = this.isOnPunctuation(n) && !this.isAfterPunctuation(n);
        return bl;
    }

    @UnsupportedAppUsage
    @Override
    public int following(int n) {
        int n2;
        this.checkOffsetIsValid(n);
        while ((n2 = this.mIterator.following(n)) != -1) {
            n = n2;
            if (!this.isAfterLetterOrDigit(n2)) continue;
        }
        return n2;
    }

    @UnsupportedAppUsage
    public int getBeginning(int n) {
        return this.getBeginning(n, false);
    }

    @UnsupportedAppUsage
    public int getEnd(int n) {
        return this.getEnd(n, false);
    }

    @UnsupportedAppUsage
    public int getNextWordEndOnTwoWordBoundary(int n) {
        return this.getEnd(n, true);
    }

    @UnsupportedAppUsage
    public int getPrevWordBeginningOnTwoWordsBoundary(int n) {
        return this.getBeginning(n, true);
    }

    @UnsupportedAppUsage
    public int getPunctuationBeginning(int n) {
        this.checkOffsetIsValid(n);
        while (n != -1 && !this.isPunctuationStartBoundary(n)) {
            n = this.prevBoundary(n);
        }
        return n;
    }

    @UnsupportedAppUsage
    public int getPunctuationEnd(int n) {
        this.checkOffsetIsValid(n);
        while (n != -1 && !this.isPunctuationEndBoundary(n)) {
            n = this.nextBoundary(n);
        }
        return n;
    }

    @UnsupportedAppUsage
    public boolean isAfterPunctuation(int n) {
        if (this.mStart < n && n <= this.mEnd) {
            return WordIterator.isPunctuation(Character.codePointBefore(this.mCharSeq, n));
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean isBoundary(int n) {
        this.checkOffsetIsValid(n);
        return this.mIterator.isBoundary(n);
    }

    @UnsupportedAppUsage
    public boolean isOnPunctuation(int n) {
        if (this.mStart <= n && n < this.mEnd) {
            return WordIterator.isPunctuation(Character.codePointAt(this.mCharSeq, n));
        }
        return false;
    }

    @UnsupportedAppUsage
    public int nextBoundary(int n) {
        this.checkOffsetIsValid(n);
        return this.mIterator.following(n);
    }

    @UnsupportedAppUsage
    @Override
    public int preceding(int n) {
        int n2;
        this.checkOffsetIsValid(n);
        while ((n2 = this.mIterator.preceding(n)) != -1) {
            n = n2;
            if (!this.isOnLetterOrDigit(n2)) continue;
        }
        return n2;
    }

    @UnsupportedAppUsage
    public int prevBoundary(int n) {
        this.checkOffsetIsValid(n);
        return this.mIterator.preceding(n);
    }

    @UnsupportedAppUsage
    public void setCharSequence(CharSequence charSequence, int n, int n2) {
        if (n >= 0 && n2 <= charSequence.length()) {
            this.mCharSeq = charSequence;
            this.mStart = Math.max(0, n - 50);
            this.mEnd = Math.min(charSequence.length(), n2 + 50);
            this.mIterator.setText((CharacterIterator)new CharSequenceCharacterIterator(charSequence, this.mStart, this.mEnd));
            return;
        }
        throw new IndexOutOfBoundsException("input indexes are outside the CharSequence");
    }
}

