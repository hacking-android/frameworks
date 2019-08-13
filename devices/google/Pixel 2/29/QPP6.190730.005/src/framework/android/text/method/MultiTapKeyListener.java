/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.method.BaseKeyListener;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;

public class MultiTapKeyListener
extends BaseKeyListener
implements SpanWatcher {
    private static MultiTapKeyListener[] sInstance = new MultiTapKeyListener[TextKeyListener.Capitalize.values().length * 2];
    private static final SparseArray<String> sRecs = new SparseArray();
    private boolean mAutoText;
    private TextKeyListener.Capitalize mCapitalize;

    static {
        sRecs.put(8, ".,1!@#$%^&*:/?'=()");
        sRecs.put(9, "abc2ABC");
        sRecs.put(10, "def3DEF");
        sRecs.put(11, "ghi4GHI");
        sRecs.put(12, "jkl5JKL");
        sRecs.put(13, "mno6MNO");
        sRecs.put(14, "pqrs7PQRS");
        sRecs.put(15, "tuv8TUV");
        sRecs.put(16, "wxyz9WXYZ");
        sRecs.put(7, "0+");
        sRecs.put(18, " ");
    }

    public MultiTapKeyListener(TextKeyListener.Capitalize capitalize, boolean bl) {
        this.mCapitalize = capitalize;
        this.mAutoText = bl;
    }

    public static MultiTapKeyListener getInstance(boolean bl, TextKeyListener.Capitalize capitalize) {
        MultiTapKeyListener[] arrmultiTapKeyListener = sInstance;
        int n = capitalize.ordinal() * 2 + bl;
        if (arrmultiTapKeyListener[n] == null) {
            arrmultiTapKeyListener[n] = new MultiTapKeyListener(capitalize, bl);
        }
        return sInstance[n];
    }

    private static void removeTimeouts(Spannable spannable) {
        Timeout[] arrtimeout = spannable.getSpans(0, spannable.length(), Timeout.class);
        for (int i = 0; i < arrtimeout.length; ++i) {
            Timeout timeout = arrtimeout[i];
            timeout.removeCallbacks(timeout);
            timeout.mBuffer = null;
            spannable.removeSpan(timeout);
        }
    }

    @Override
    public int getInputType() {
        return MultiTapKeyListener.makeTextContentType(this.mCapitalize, this.mAutoText);
    }

    @Override
    public boolean onKeyDown(View arrkeyListener, Editable editable, int n, KeyEvent keyEvent) {
        int n2 = arrkeyListener != null ? TextKeyListener.getInstance().getPrefs(arrkeyListener.getContext()) : 0;
        int n3 = Selection.getSelectionStart(editable);
        int n4 = Selection.getSelectionEnd(editable);
        int n5 = Math.min(n3, n4);
        int n6 = Math.max(n3, n4);
        n4 = editable.getSpanStart(TextKeyListener.ACTIVE);
        int n7 = editable.getSpanEnd(TextKeyListener.ACTIVE);
        n3 = (editable.getSpanFlags(TextKeyListener.ACTIVE) & -16777216) >>> 24;
        if (n4 == n5 && n7 == n6 && n6 - n5 == 1 && n3 >= 0 && n3 < sRecs.size()) {
            String string2;
            if (n == 17) {
                char c = editable.charAt(n5);
                if (Character.isLowerCase(c)) {
                    editable.replace(n5, n6, String.valueOf(c).toUpperCase());
                    MultiTapKeyListener.removeTimeouts(editable);
                    new Timeout(editable);
                    return true;
                }
                if (Character.isUpperCase(c)) {
                    editable.replace(n5, n6, String.valueOf(c).toLowerCase());
                    MultiTapKeyListener.removeTimeouts(editable);
                    new Timeout(editable);
                    return true;
                }
            }
            if (sRecs.indexOfKey(n) == n3 && (n4 = (string2 = sRecs.valueAt(n3)).indexOf(editable.charAt(n5))) >= 0) {
                n = (n4 + 1) % string2.length();
                editable.replace(n5, n6, string2, n, n + 1);
                MultiTapKeyListener.removeTimeouts(editable);
                new Timeout(editable);
                return true;
            }
            n3 = sRecs.indexOfKey(n);
            if (n3 >= 0) {
                Selection.setSelection(editable, n6, n6);
                n5 = n3;
                n4 = n6;
            } else {
                n4 = n5;
                n5 = n3;
            }
        } else {
            n3 = sRecs.indexOfKey(n);
            n4 = n5;
            n5 = n3;
        }
        if (n5 >= 0) {
            arrkeyListener = sRecs.valueAt(n5);
            if ((n2 & 1) != 0 && TextKeyListener.shouldCap(this.mCapitalize, editable, n4)) {
                for (n = 0; n < arrkeyListener.length(); ++n) {
                    if (!Character.isUpperCase(arrkeyListener.charAt(n))) {
                        continue;
                    }
                    break;
                }
            } else {
                n = 0;
            }
            if (n4 != n6) {
                Selection.setSelection(editable, n6);
            }
            editable.setSpan(OLD_SEL_START, n4, n4, 17);
            editable.replace(n4, n6, (CharSequence)arrkeyListener, n, n + 1);
            n = editable.getSpanStart(OLD_SEL_START);
            n4 = Selection.getSelectionEnd(editable);
            if (n4 != n) {
                Selection.setSelection(editable, n, n4);
                editable.setSpan(TextKeyListener.LAST_TYPED, n, n4, 33);
                editable.setSpan(TextKeyListener.ACTIVE, n, n4, 33 | n5 << 24);
            }
            MultiTapKeyListener.removeTimeouts(editable);
            new Timeout(editable);
            if (editable.getSpanStart(this) < 0) {
                arrkeyListener = editable.getSpans(0, editable.length(), KeyListener.class);
                n5 = arrkeyListener.length;
                for (n = 0; n < n5; ++n) {
                    editable.removeSpan(arrkeyListener[n]);
                }
                editable.setSpan(this, 0, editable.length(), 18);
            }
            return true;
        }
        return super.onKeyDown((View)arrkeyListener, editable, n, keyEvent);
    }

    @Override
    public void onSpanAdded(Spannable spannable, Object object, int n, int n2) {
    }

    @Override
    public void onSpanChanged(Spannable spannable, Object object, int n, int n2, int n3, int n4) {
        if (object == Selection.SELECTION_END) {
            spannable.removeSpan(TextKeyListener.ACTIVE);
            MultiTapKeyListener.removeTimeouts(spannable);
        }
    }

    @Override
    public void onSpanRemoved(Spannable spannable, Object object, int n, int n2) {
    }

    private class Timeout
    extends Handler
    implements Runnable {
        private Editable mBuffer;

        public Timeout(Editable editable) {
            this.mBuffer = editable;
            MultiTapKeyListener.this = this.mBuffer;
            MultiTapKeyListener.this.setSpan(this, 0, MultiTapKeyListener.this.length(), 18);
            this.postAtTime(this, SystemClock.uptimeMillis() + 2000L);
        }

        @Override
        public void run() {
            Editable editable = this.mBuffer;
            if (editable != null) {
                int n = Selection.getSelectionStart(editable);
                int n2 = Selection.getSelectionEnd(editable);
                int n3 = editable.getSpanStart(TextKeyListener.ACTIVE);
                int n4 = editable.getSpanEnd(TextKeyListener.ACTIVE);
                if (n == n3 && n2 == n4) {
                    Selection.setSelection(editable, Selection.getSelectionEnd(editable));
                }
                editable.removeSpan(this);
            }
        }
    }

}

