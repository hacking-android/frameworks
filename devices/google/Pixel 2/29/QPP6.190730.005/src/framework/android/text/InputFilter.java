/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.util.Locale;

public interface InputFilter {
    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6);

    public static class AllCaps
    implements InputFilter {
        private final Locale mLocale;

        public AllCaps() {
            this.mLocale = null;
        }

        public AllCaps(Locale locale) {
            Preconditions.checkNotNull(locale);
            this.mLocale = locale;
        }

        @Override
        public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
            block3 : {
                spanned = new CharSequenceWrapper(charSequence, n, n2);
                int n5 = 0;
                n3 = 0;
                do {
                    n4 = n5;
                    if (n3 >= n2 - n) break block3;
                    n4 = Character.codePointAt(spanned, n3);
                    if (Character.isLowerCase(n4) || Character.isTitleCase(n4)) break;
                    n3 += Character.charCount(n4);
                } while (true);
                n4 = 1;
            }
            if (n4 == 0) {
                return null;
            }
            boolean bl = charSequence instanceof Spanned;
            charSequence = TextUtils.toUpperCase(this.mLocale, spanned, bl);
            if (charSequence == spanned) {
                return null;
            }
            charSequence = bl ? new SpannableString(charSequence) : charSequence.toString();
            return charSequence;
        }

        private static class CharSequenceWrapper
        implements CharSequence,
        Spanned {
            private final int mEnd;
            private final int mLength;
            private final CharSequence mSource;
            private final int mStart;

            CharSequenceWrapper(CharSequence charSequence, int n, int n2) {
                this.mSource = charSequence;
                this.mStart = n;
                this.mEnd = n2;
                this.mLength = n2 - n;
            }

            @Override
            public char charAt(int n) {
                if (n >= 0 && n < this.mLength) {
                    return this.mSource.charAt(this.mStart + n);
                }
                throw new IndexOutOfBoundsException();
            }

            @Override
            public int getSpanEnd(Object object) {
                return ((Spanned)this.mSource).getSpanEnd(object) - this.mStart;
            }

            @Override
            public int getSpanFlags(Object object) {
                return ((Spanned)this.mSource).getSpanFlags(object);
            }

            @Override
            public int getSpanStart(Object object) {
                return ((Spanned)this.mSource).getSpanStart(object) - this.mStart;
            }

            @Override
            public <T> T[] getSpans(int n, int n2, Class<T> class_) {
                Spanned spanned = (Spanned)this.mSource;
                int n3 = this.mStart;
                return spanned.getSpans(n3 + n, n3 + n2, class_);
            }

            @Override
            public int length() {
                return this.mLength;
            }

            @Override
            public int nextSpanTransition(int n, int n2, Class class_) {
                Spanned spanned = (Spanned)this.mSource;
                int n3 = this.mStart;
                return spanned.nextSpanTransition(n3 + n, n3 + n2, class_) - this.mStart;
            }

            @Override
            public CharSequence subSequence(int n, int n2) {
                if (n >= 0 && n2 >= 0 && n2 <= this.mLength && n <= n2) {
                    CharSequence charSequence = this.mSource;
                    int n3 = this.mStart;
                    return new CharSequenceWrapper(charSequence, n3 + n, n3 + n2);
                }
                throw new IndexOutOfBoundsException();
            }

            @Override
            public String toString() {
                return this.mSource.subSequence(this.mStart, this.mEnd).toString();
            }
        }

    }

    public static class LengthFilter
    implements InputFilter {
        @UnsupportedAppUsage
        private final int mMax;

        public LengthFilter(int n) {
            this.mMax = n;
        }

        @Override
        public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
            n3 = this.mMax - (spanned.length() - (n4 - n3));
            if (n3 <= 0) {
                return "";
            }
            if (n3 >= n2 - n) {
                return null;
            }
            n2 = n3 += n;
            if (Character.isHighSurrogate(charSequence.charAt(n3 - 1))) {
                n2 = --n3;
                if (n3 == n) {
                    return "";
                }
            }
            return charSequence.subSequence(n, n2);
        }

        public int getMax() {
            return this.mMax;
        }
    }

}

