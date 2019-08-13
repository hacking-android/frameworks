/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.GetChars;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.text.method.TransformationMethod;
import android.text.style.UpdateLayout;
import android.view.View;
import java.lang.ref.WeakReference;

public class PasswordTransformationMethod
implements TransformationMethod,
TextWatcher {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static char DOT = (char)8226;
    @UnsupportedAppUsage
    private static PasswordTransformationMethod sInstance;

    public static PasswordTransformationMethod getInstance() {
        PasswordTransformationMethod passwordTransformationMethod = sInstance;
        if (passwordTransformationMethod != null) {
            return passwordTransformationMethod;
        }
        sInstance = new PasswordTransformationMethod();
        return sInstance;
    }

    private static void removeVisibleSpans(Spannable spannable) {
        Visible[] arrvisible = spannable.getSpans(0, spannable.length(), Visible.class);
        for (int i = 0; i < arrvisible.length; ++i) {
            spannable.removeSpan(arrvisible[i]);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    @Override
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (charSequence instanceof Spannable) {
            Spannable spannable = (Spannable)charSequence;
            ViewReference[] arrviewReference = spannable.getSpans(0, spannable.length(), ViewReference.class);
            for (int i = 0; i < arrviewReference.length; ++i) {
                spannable.removeSpan(arrviewReference[i]);
            }
            PasswordTransformationMethod.removeVisibleSpans(spannable);
            spannable.setSpan(new ViewReference(view), 0, 0, 34);
        }
        return new PasswordCharSequence(charSequence);
    }

    @Override
    public void onFocusChanged(View view, CharSequence charSequence, boolean bl, int n, Rect rect) {
        if (!bl && charSequence instanceof Spannable) {
            PasswordTransformationMethod.removeVisibleSpans((Spannable)charSequence);
        }
    }

    @Override
    public void onTextChanged(CharSequence object, int n, int n2, int n3) {
        if (object instanceof Spannable) {
            Spannable spannable = (Spannable)object;
            ViewReference[] arrviewReference = spannable.getSpans(0, object.length(), ViewReference.class);
            if (arrviewReference.length == 0) {
                return;
            }
            object = null;
            for (n2 = 0; object == null && n2 < arrviewReference.length; ++n2) {
                object = (View)arrviewReference[n2].get();
            }
            if (object == null) {
                return;
            }
            if ((TextKeyListener.getInstance().getPrefs(((View)object).getContext()) & 8) != 0 && n3 > 0) {
                PasswordTransformationMethod.removeVisibleSpans(spannable);
                if (n3 == 1) {
                    spannable.setSpan(new Visible(spannable, this), n, n + n3, 33);
                }
            }
        }
    }

    private static class PasswordCharSequence
    implements CharSequence,
    GetChars {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence charSequence) {
            this.mSource = charSequence;
        }

        @Override
        public char charAt(int n) {
            Visible[] arrvisible = this.mSource;
            if (arrvisible instanceof Spanned) {
                Spanned spanned = (Spanned)arrvisible;
                int n2 = spanned.getSpanStart(TextKeyListener.ACTIVE);
                int n3 = spanned.getSpanEnd(TextKeyListener.ACTIVE);
                if (n >= n2 && n < n3) {
                    return this.mSource.charAt(n);
                }
                arrvisible = spanned.getSpans(0, spanned.length(), Visible.class);
                for (n2 = 0; n2 < arrvisible.length; ++n2) {
                    if (spanned.getSpanStart(arrvisible[n2].mTransformer) < 0) continue;
                    int n4 = spanned.getSpanStart(arrvisible[n2]);
                    n3 = spanned.getSpanEnd(arrvisible[n2]);
                    if (n < n4 || n >= n3) continue;
                    return this.mSource.charAt(n);
                }
            }
            return DOT;
        }

        @Override
        public void getChars(int n, int n2, char[] arrc, int n3) {
            int n4;
            int n5;
            int n6;
            int n7;
            TextUtils.getChars(this.mSource, n, n2, arrc, n3);
            int n8 = -1;
            int n9 = -1;
            int n10 = 0;
            int[] arrn = null;
            int[] arrn2 = null;
            int[] arrn3 = this.mSource;
            if (arrn3 instanceof Spanned) {
                Spanned spanned = (Spanned)arrn3;
                n6 = spanned.getSpanStart(TextKeyListener.ACTIVE);
                n5 = spanned.getSpanEnd(TextKeyListener.ACTIVE);
                Visible[] arrvisible = spanned.getSpans(0, spanned.length(), Visible.class);
                n7 = arrvisible.length;
                arrn3 = new int[n7];
                int[] arrn4 = new int[n7];
                n4 = 0;
                do {
                    n8 = n6;
                    n9 = n5;
                    n10 = n7;
                    arrn = arrn3;
                    arrn2 = arrn4;
                    if (n4 >= n7) break;
                    if (spanned.getSpanStart(arrvisible[n4].mTransformer) >= 0) {
                        arrn3[n4] = spanned.getSpanStart(arrvisible[n4]);
                        arrn4[n4] = spanned.getSpanEnd(arrvisible[n4]);
                    }
                    ++n4;
                } while (true);
            }
            for (n4 = n; n4 < n2; ++n4) {
                if (n4 >= n8 && n4 < n9) continue;
                n7 = 0;
                n6 = 0;
                do {
                    n5 = n7;
                    if (n6 >= n10) break;
                    if (n4 >= arrn[n6] && n4 < arrn2[n6]) {
                        n5 = 1;
                        break;
                    }
                    ++n6;
                } while (true);
                if (n5 != 0) continue;
                arrc[n4 - n + n3] = DOT;
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
            return this.subSequence(0, this.length()).toString();
        }
    }

    private static class ViewReference
    extends WeakReference<View>
    implements NoCopySpan {
        public ViewReference(View view) {
            super(view);
        }
    }

    private static class Visible
    extends Handler
    implements UpdateLayout,
    Runnable {
        private Spannable mText;
        private PasswordTransformationMethod mTransformer;

        public Visible(Spannable spannable, PasswordTransformationMethod passwordTransformationMethod) {
            this.mText = spannable;
            this.mTransformer = passwordTransformationMethod;
            this.postAtTime(this, SystemClock.uptimeMillis() + 1500L);
        }

        @Override
        public void run() {
            this.mText.removeSpan(this);
        }
    }

}

