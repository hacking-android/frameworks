/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

public abstract class LoginFilter
implements InputFilter {
    private boolean mAppendInvalid;

    LoginFilter() {
        this.mAppendInvalid = false;
    }

    LoginFilter(boolean bl) {
        this.mAppendInvalid = bl;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
        int n5;
        char c;
        this.onStart();
        for (n5 = 0; n5 < n3; ++n5) {
            c = spanned.charAt(n5);
            if (this.isAllowed(c)) continue;
            this.onInvalidCharacter(c);
        }
        SpannableStringBuilder spannableStringBuilder = null;
        n3 = 0;
        for (n5 = n; n5 < n2; ++n5) {
            c = charSequence.charAt(n5);
            if (this.isAllowed(c)) {
                ++n3;
                continue;
            }
            if (this.mAppendInvalid) {
                ++n3;
            } else {
                SpannableStringBuilder spannableStringBuilder2 = spannableStringBuilder;
                if (spannableStringBuilder == null) {
                    spannableStringBuilder2 = new SpannableStringBuilder(charSequence, n, n2);
                    n3 = n5 - n;
                }
                spannableStringBuilder2.delete(n3, n3 + 1);
                spannableStringBuilder = spannableStringBuilder2;
            }
            this.onInvalidCharacter(c);
        }
        for (n = n4; n < spanned.length(); ++n) {
            c = spanned.charAt(n);
            if (this.isAllowed(c)) continue;
            this.onInvalidCharacter(c);
        }
        this.onStop();
        return spannableStringBuilder;
    }

    public abstract boolean isAllowed(char var1);

    public void onInvalidCharacter(char c) {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public static class PasswordFilterGMail
    extends LoginFilter {
        public PasswordFilterGMail() {
            super(false);
        }

        public PasswordFilterGMail(boolean bl) {
            super(bl);
        }

        @Override
        public boolean isAllowed(char c) {
            if (' ' <= c && c <= '') {
                return true;
            }
            return '\u00a0' <= c && c <= '\u00ff';
        }
    }

    public static class UsernameFilterGMail
    extends LoginFilter {
        public UsernameFilterGMail() {
            super(false);
        }

        public UsernameFilterGMail(boolean bl) {
            super(bl);
        }

        @Override
        public boolean isAllowed(char c) {
            if ('0' <= c && c <= '9') {
                return true;
            }
            if ('a' <= c && c <= 'z') {
                return true;
            }
            if ('A' <= c && c <= 'Z') {
                return true;
            }
            return '.' == c;
        }
    }

    public static class UsernameFilterGeneric
    extends LoginFilter {
        private static final String mAllowed = "@_-+.";

        public UsernameFilterGeneric() {
            super(false);
        }

        public UsernameFilterGeneric(boolean bl) {
            super(bl);
        }

        @Override
        public boolean isAllowed(char c) {
            if ('0' <= c && c <= '9') {
                return true;
            }
            if ('a' <= c && c <= 'z') {
                return true;
            }
            if ('A' <= c && c <= 'Z') {
                return true;
            }
            return mAllowed.indexOf(c) != -1;
        }
    }

}

