/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.graphics.Rect;
import android.text.Editable;
import android.text.GetChars;
import android.text.Spannable;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.view.View;

public abstract class ReplacementTransformationMethod
implements TransformationMethod {
    protected abstract char[] getOriginal();

    protected abstract char[] getReplacement();

    @Override
    public CharSequence getTransformation(CharSequence charSequence, View arrc) {
        arrc = this.getOriginal();
        char[] arrc2 = this.getReplacement();
        if (!(charSequence instanceof Editable)) {
            boolean bl;
            boolean bl2 = true;
            int n = arrc.length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                if (TextUtils.indexOf(charSequence, arrc[n2]) >= 0) {
                    bl = false;
                    break;
                }
                ++n2;
            } while (true);
            if (bl) {
                return charSequence;
            }
            if (!(charSequence instanceof Spannable)) {
                if (charSequence instanceof Spanned) {
                    return new SpannedString(new SpannedReplacementCharSequence((Spanned)charSequence, arrc, arrc2));
                }
                return new ReplacementCharSequence(charSequence, arrc, arrc2).toString();
            }
        }
        if (charSequence instanceof Spanned) {
            return new SpannedReplacementCharSequence((Spanned)charSequence, arrc, arrc2);
        }
        return new ReplacementCharSequence(charSequence, arrc, arrc2);
    }

    @Override
    public void onFocusChanged(View view, CharSequence charSequence, boolean bl, int n, Rect rect) {
    }

    private static class ReplacementCharSequence
    implements CharSequence,
    GetChars {
        private char[] mOriginal;
        private char[] mReplacement;
        private CharSequence mSource;

        public ReplacementCharSequence(CharSequence charSequence, char[] arrc, char[] arrc2) {
            this.mSource = charSequence;
            this.mOriginal = arrc;
            this.mReplacement = arrc2;
        }

        @Override
        public char charAt(int n) {
            char c = this.mSource.charAt(n);
            int n2 = this.mOriginal.length;
            char c2 = c;
            for (n = 0; n < n2; ++n) {
                c = c2;
                if (c2 == this.mOriginal[n]) {
                    c = this.mReplacement[n];
                }
                c2 = c;
            }
            return c2;
        }

        @Override
        public void getChars(int n, int n2, char[] arrc, int n3) {
            TextUtils.getChars(this.mSource, n, n2, arrc, n3);
            int n4 = this.mOriginal.length;
            for (int i = n3; i < n2 - n + n3; ++i) {
                char c = arrc[i];
                for (int j = 0; j < n4; ++j) {
                    if (c != this.mOriginal[j]) continue;
                    arrc[i] = this.mReplacement[j];
                }
            }
        }

        @Override
        public int length() {
            return this.mSource.length();
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            char[] arrc = new char[n2 - n];
            this.getChars(n, n2, arrc, 0);
            return new String(arrc);
        }

        @Override
        public String toString() {
            char[] arrc = new char[this.length()];
            this.getChars(0, this.length(), arrc, 0);
            return new String(arrc);
        }
    }

    private static class SpannedReplacementCharSequence
    extends ReplacementCharSequence
    implements Spanned {
        private Spanned mSpanned;

        public SpannedReplacementCharSequence(Spanned spanned, char[] arrc, char[] arrc2) {
            super(spanned, arrc, arrc2);
            this.mSpanned = spanned;
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

        @Override
        public CharSequence subSequence(int n, int n2) {
            return new SpannedString(this).subSequence(n, n2);
        }
    }

}

