/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.GetChars;
import android.text.Spanned;
import android.text.TextUtils;

public class AlteredCharSequence
implements CharSequence,
GetChars {
    private char[] mChars;
    private int mEnd;
    private CharSequence mSource;
    private int mStart;

    private AlteredCharSequence(CharSequence charSequence, char[] arrc, int n, int n2) {
        this.mSource = charSequence;
        this.mChars = arrc;
        this.mStart = n;
        this.mEnd = n2;
    }

    public static AlteredCharSequence make(CharSequence charSequence, char[] arrc, int n, int n2) {
        if (charSequence instanceof Spanned) {
            return new AlteredSpanned(charSequence, arrc, n, n2);
        }
        return new AlteredCharSequence(charSequence, arrc, n, n2);
    }

    @Override
    public char charAt(int n) {
        int n2 = this.mStart;
        if (n >= n2 && n < this.mEnd) {
            return this.mChars[n - n2];
        }
        return this.mSource.charAt(n);
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        TextUtils.getChars(this.mSource, n, n2, arrc, n3);
        n = Math.max(this.mStart, n);
        n2 = Math.min(this.mEnd, n2);
        if (n > n2) {
            System.arraycopy(this.mChars, n - this.mStart, arrc, n3, n2 - n);
        }
    }

    @Override
    public int length() {
        return this.mSource.length();
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return AlteredCharSequence.make(this.mSource.subSequence(n, n2), this.mChars, this.mStart - n, this.mEnd - n);
    }

    @Override
    public String toString() {
        int n = this.length();
        char[] arrc = new char[n];
        this.getChars(0, n, arrc, 0);
        return String.valueOf(arrc);
    }

    void update(char[] arrc, int n, int n2) {
        this.mChars = arrc;
        this.mStart = n;
        this.mEnd = n2;
    }

    private static class AlteredSpanned
    extends AlteredCharSequence
    implements Spanned {
        private Spanned mSpanned;

        private AlteredSpanned(CharSequence charSequence, char[] arrc, int n, int n2) {
            super(charSequence, arrc, n, n2);
            this.mSpanned = (Spanned)charSequence;
        }

        @Override
        public int getSpanEnd(Object object) {
            return this.mSpanned.getSpanEnd(object);
        }

        @Override
        public int getSpanFlags(Object object) {
            return this.mSpanned.getSpanFlags(object);
        }

        @Override
        public int getSpanStart(Object object) {
            return this.mSpanned.getSpanStart(object);
        }

        @Override
        public <T> T[] getSpans(int n, int n2, Class<T> class_) {
            return this.mSpanned.getSpans(n, n2, class_);
        }

        @Override
        public int nextSpanTransition(int n, int n2, Class class_) {
            return this.mSpanned.nextSpanTransition(n, n2, class_);
        }
    }

}

