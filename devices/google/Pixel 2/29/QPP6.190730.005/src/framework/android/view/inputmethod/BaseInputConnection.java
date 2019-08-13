/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.ComposingText;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;

public class BaseInputConnection
implements InputConnection {
    static final Object COMPOSING = new ComposingText();
    private static final boolean DEBUG = false;
    private static int INVALID_INDEX = 0;
    private static final String TAG = "BaseInputConnection";
    private Object[] mDefaultComposingSpans;
    final boolean mDummyMode;
    Editable mEditable;
    protected final InputMethodManager mIMM;
    KeyCharacterMap mKeyCharacterMap;
    final View mTargetView;

    static {
        INVALID_INDEX = -1;
    }

    public BaseInputConnection(View view, boolean bl) {
        this.mIMM = (InputMethodManager)view.getContext().getSystemService("input_method");
        this.mTargetView = view;
        this.mDummyMode = bl ^ true;
    }

    BaseInputConnection(InputMethodManager inputMethodManager, boolean bl) {
        this.mIMM = inputMethodManager;
        this.mTargetView = null;
        this.mDummyMode = bl ^ true;
    }

    private void ensureDefaultComposingSpans() {
        Object object;
        if (this.mDefaultComposingSpans == null && (object = (object = this.mTargetView) != null ? ((View)object).getContext() : (this.mIMM.mServedView != null ? this.mIMM.mServedView.getContext() : null)) != null) {
            TypedArray typedArray = ((Context)object).getTheme().obtainStyledAttributes(new int[]{16843312});
            object = typedArray.getText(0);
            typedArray.recycle();
            if (object != null && object instanceof Spanned) {
                this.mDefaultComposingSpans = ((Spanned)object).getSpans(0, object.length(), Object.class);
            }
        }
    }

    private static int findIndexBackward(CharSequence charSequence, int n, int n2) {
        int n3 = n;
        int n4 = 0;
        n = charSequence.length();
        if (n3 >= 0 && n >= n3) {
            if (n2 < 0) {
                return INVALID_INDEX;
            }
            n = n2;
            n2 = n4;
            do {
                if (n == 0) {
                    return n3;
                }
                if (--n3 < 0) {
                    if (n2 != 0) {
                        return INVALID_INDEX;
                    }
                    return 0;
                }
                char c = charSequence.charAt(n3);
                if (n2 != 0) {
                    if (!Character.isHighSurrogate(c)) {
                        return INVALID_INDEX;
                    }
                    n2 = 0;
                    --n;
                    continue;
                }
                if (!Character.isSurrogate(c)) {
                    --n;
                    continue;
                }
                if (Character.isHighSurrogate(c)) {
                    return INVALID_INDEX;
                }
                n2 = 1;
            } while (true);
        }
        return INVALID_INDEX;
    }

    private static int findIndexForward(CharSequence charSequence, int n, int n2) {
        boolean bl = false;
        int n3 = charSequence.length();
        if (n >= 0 && n3 >= n) {
            if (n2 < 0) {
                return INVALID_INDEX;
            }
            do {
                if (n2 == 0) {
                    return n;
                }
                if (n >= n3) {
                    if (bl) {
                        return INVALID_INDEX;
                    }
                    return n3;
                }
                char c = charSequence.charAt(n);
                if (bl) {
                    if (!Character.isLowSurrogate(c)) {
                        return INVALID_INDEX;
                    }
                    --n2;
                    bl = false;
                    ++n;
                    continue;
                }
                if (!Character.isSurrogate(c)) {
                    --n2;
                    ++n;
                    continue;
                }
                if (Character.isLowSurrogate(c)) {
                    return INVALID_INDEX;
                }
                bl = true;
                ++n;
            } while (true);
        }
        return INVALID_INDEX;
    }

    public static int getComposingSpanEnd(Spannable spannable) {
        return spannable.getSpanEnd(COMPOSING);
    }

    public static int getComposingSpanStart(Spannable spannable) {
        return spannable.getSpanStart(COMPOSING);
    }

    public static final void removeComposingSpans(Spannable spannable) {
        spannable.removeSpan(COMPOSING);
        Object[] arrobject = spannable.getSpans(0, spannable.length(), Object.class);
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                Object object = arrobject[i];
                if ((spannable.getSpanFlags(object) & 256) == 0) continue;
                spannable.removeSpan(object);
            }
        }
    }

    private void replaceText(CharSequence arrobject, int n, boolean bl) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return;
        }
        this.beginBatchEdit();
        int n2 = BaseInputConnection.getComposingSpanStart(editable);
        int n3 = BaseInputConnection.getComposingSpanEnd(editable);
        int n4 = n2;
        int n5 = n3;
        if (n3 < n2) {
            n4 = n3;
            n5 = n2;
        }
        if (n4 != -1 && n5 != -1) {
            BaseInputConnection.removeComposingSpans(editable);
        } else {
            n3 = Selection.getSelectionStart(editable);
            n5 = Selection.getSelectionEnd(editable);
            n2 = n3;
            if (n3 < 0) {
                n2 = 0;
            }
            n3 = n5;
            if (n5 < 0) {
                n3 = 0;
            }
            n4 = n2;
            n5 = n3;
            if (n3 < n2) {
                n5 = n2;
                n4 = n3;
            }
        }
        Object[] arrobject2 = arrobject;
        if (bl) {
            Object[] arrobject3;
            if (!(arrobject instanceof Spannable)) {
                Object[] arrobject4 = arrobject2 = new SpannableStringBuilder((CharSequence)arrobject);
                this.ensureDefaultComposingSpans();
                arrobject3 = arrobject2;
                arrobject = arrobject4;
                if (this.mDefaultComposingSpans != null) {
                    for (n2 = 0; n2 < (arrobject = this.mDefaultComposingSpans).length; ++n2) {
                        arrobject2.setSpan(arrobject[n2], 0, arrobject2.length(), 289);
                    }
                    arrobject3 = arrobject2;
                    arrobject = arrobject4;
                }
            } else {
                arrobject3 = (Spannable)arrobject;
            }
            BaseInputConnection.setComposingSpans((Spannable)arrobject3);
            arrobject2 = arrobject;
        }
        n2 = n > 0 ? n + (n5 - 1) : n + n4;
        n = n2;
        if (n2 < 0) {
            n = 0;
        }
        n2 = n;
        if (n > editable.length()) {
            n2 = editable.length();
        }
        Selection.setSelection(editable, n2);
        editable.replace(n4, n5, (CharSequence)arrobject2);
        this.endBatchEdit();
    }

    private void sendCurrentText() {
        if (!this.mDummyMode) {
            return;
        }
        Editable editable = this.getEditable();
        if (editable != null) {
            int n = editable.length();
            if (n == 0) {
                return;
            }
            if (n == 1) {
                if (this.mKeyCharacterMap == null) {
                    this.mKeyCharacterMap = KeyCharacterMap.load(-1);
                }
                Object[] arrobject = new char[1];
                editable.getChars(0, 1, (char[])arrobject, 0);
                arrobject = this.mKeyCharacterMap.getEvents((char[])arrobject);
                if (arrobject != null) {
                    for (n = 0; n < arrobject.length; ++n) {
                        this.sendKeyEvent((KeyEvent)arrobject[n]);
                    }
                    editable.clear();
                    return;
                }
            }
            this.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), editable.toString(), -1, 0));
            editable.clear();
        }
    }

    public static void setComposingSpans(Spannable spannable) {
        BaseInputConnection.setComposingSpans(spannable, 0, spannable.length());
    }

    public static void setComposingSpans(Spannable spannable, int n, int n2) {
        Object[] arrobject = spannable.getSpans(n, n2, Object.class);
        if (arrobject != null) {
            for (int i = arrobject.length - 1; i >= 0; --i) {
                Object object = arrobject[i];
                if (object == COMPOSING) {
                    spannable.removeSpan(object);
                    continue;
                }
                int n3 = spannable.getSpanFlags(object);
                if ((n3 & 307) == 289) continue;
                spannable.setSpan(object, spannable.getSpanStart(object), spannable.getSpanEnd(object), n3 & -52 | 256 | 33);
            }
        }
        spannable.setSpan(COMPOSING, n, n2, 289);
    }

    @Override
    public boolean beginBatchEdit() {
        return false;
    }

    @Override
    public boolean clearMetaKeyStates(int n) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return false;
        }
        MetaKeyKeyListener.clearMetaKeyState(editable, n);
        return true;
    }

    @Override
    public void closeConnection() {
        this.finishComposingText();
    }

    @Override
    public boolean commitCompletion(CompletionInfo completionInfo) {
        return false;
    }

    @Override
    public boolean commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle) {
        return false;
    }

    @Override
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return false;
    }

    @Override
    public boolean commitText(CharSequence charSequence, int n) {
        this.replaceText(charSequence, n, false);
        this.sendCurrentText();
        return true;
    }

    @Override
    public boolean deleteSurroundingText(int n, int n2) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return false;
        }
        this.beginBatchEdit();
        int n3 = Selection.getSelectionStart(editable);
        int n4 = Selection.getSelectionEnd(editable);
        int n5 = n3;
        int n6 = n4;
        if (n3 > n4) {
            n5 = n4;
            n6 = n3;
        }
        int n7 = BaseInputConnection.getComposingSpanStart(editable);
        int n8 = BaseInputConnection.getComposingSpanEnd(editable);
        n4 = n7;
        n3 = n8;
        if (n8 < n7) {
            n4 = n8;
            n3 = n7;
        }
        int n9 = n5;
        n8 = n6;
        if (n4 != -1) {
            n9 = n5;
            n8 = n6;
            if (n3 != -1) {
                n7 = n5;
                if (n4 < n5) {
                    n7 = n4;
                }
                n9 = n7;
                n8 = n6;
                if (n3 > n6) {
                    n8 = n3;
                    n9 = n7;
                }
            }
        }
        n5 = 0;
        if (n > 0) {
            n = n5 = n9 - n;
            if (n5 < 0) {
                n = 0;
            }
            editable.delete(n, n9);
            n5 = n9 - n;
        }
        if (n2 > 0) {
            n5 = n8 - n5;
            n = n2 = n5 + n2;
            if (n2 > editable.length()) {
                n = editable.length();
            }
            editable.delete(n5, n);
        }
        this.endBatchEdit();
        return true;
    }

    @Override
    public boolean deleteSurroundingTextInCodePoints(int n, int n2) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return false;
        }
        this.beginBatchEdit();
        int n3 = Selection.getSelectionStart(editable);
        int n4 = Selection.getSelectionEnd(editable);
        int n5 = n3;
        int n6 = n4;
        if (n3 > n4) {
            n5 = n4;
            n6 = n3;
        }
        int n7 = BaseInputConnection.getComposingSpanStart(editable);
        int n8 = BaseInputConnection.getComposingSpanEnd(editable);
        n4 = n7;
        n3 = n8;
        if (n8 < n7) {
            n4 = n8;
            n3 = n7;
        }
        int n9 = n5;
        n8 = n6;
        if (n4 != -1) {
            n9 = n5;
            n8 = n6;
            if (n3 != -1) {
                n7 = n5;
                if (n4 < n5) {
                    n7 = n4;
                }
                n9 = n7;
                n8 = n6;
                if (n3 > n6) {
                    n8 = n3;
                    n9 = n7;
                }
            }
        }
        if (n9 >= 0 && n8 >= 0 && (n = BaseInputConnection.findIndexBackward(editable, n9, Math.max(n, 0))) != INVALID_INDEX && (n5 = BaseInputConnection.findIndexForward(editable, n8, Math.max(n2, 0))) != INVALID_INDEX) {
            n2 = n9 - n;
            if (n2 > 0) {
                editable.delete(n, n9);
            }
            if (n5 - n8 > 0) {
                editable.delete(n8 - n2, n5 - n2);
            }
        }
        this.endBatchEdit();
        return true;
    }

    @Override
    public boolean endBatchEdit() {
        return false;
    }

    @Override
    public boolean finishComposingText() {
        Editable editable = this.getEditable();
        if (editable != null) {
            this.beginBatchEdit();
            BaseInputConnection.removeComposingSpans(editable);
            this.sendCurrentText();
            this.endBatchEdit();
        }
        return true;
    }

    @Override
    public int getCursorCapsMode(int n) {
        if (this.mDummyMode) {
            return 0;
        }
        Editable editable = this.getEditable();
        if (editable == null) {
            return 0;
        }
        int n2 = Selection.getSelectionStart(editable);
        int n3 = Selection.getSelectionEnd(editable);
        int n4 = n2;
        if (n2 > n3) {
            n4 = n3;
        }
        return TextUtils.getCapsMode(editable, n4, n);
    }

    public Editable getEditable() {
        if (this.mEditable == null) {
            this.mEditable = Editable.Factory.getInstance().newEditable("");
            Selection.setSelection(this.mEditable, 0);
        }
        return this.mEditable;
    }

    @Override
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int n) {
        return null;
    }

    @Override
    public Handler getHandler() {
        return null;
    }

    @Override
    public CharSequence getSelectedText(int n) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return null;
        }
        int n2 = Selection.getSelectionStart(editable);
        int n3 = Selection.getSelectionEnd(editable);
        int n4 = n2;
        int n5 = n3;
        if (n2 > n3) {
            n4 = n3;
            n5 = n2;
        }
        if (n4 != n5 && n4 >= 0) {
            if ((n & 1) != 0) {
                return editable.subSequence(n4, n5);
            }
            return TextUtils.substring(editable, n4, n5);
        }
        return null;
    }

    @Override
    public CharSequence getTextAfterCursor(int n, int n2) {
        int n3;
        Editable editable = this.getEditable();
        if (editable == null) {
            return null;
        }
        int n4 = Selection.getSelectionStart(editable);
        int n5 = n3 = Selection.getSelectionEnd(editable);
        if (n4 > n3) {
            n5 = n4;
        }
        n3 = n5;
        if (n5 < 0) {
            n3 = 0;
        }
        n5 = n;
        if (n3 + n > editable.length()) {
            n5 = editable.length() - n3;
        }
        if ((n2 & 1) != 0) {
            return editable.subSequence(n3, n3 + n5);
        }
        return TextUtils.substring(editable, n3, n3 + n5);
    }

    @Override
    public CharSequence getTextBeforeCursor(int n, int n2) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return null;
        }
        int n3 = Selection.getSelectionStart(editable);
        int n4 = Selection.getSelectionEnd(editable);
        int n5 = n3;
        if (n3 > n4) {
            n5 = n4;
        }
        if (n5 <= 0) {
            return "";
        }
        n4 = n;
        if (n > n5) {
            n4 = n5;
        }
        if ((n2 & 1) != 0) {
            return editable.subSequence(n5 - n4, n5);
        }
        return TextUtils.substring(editable, n5 - n4, n5);
    }

    @Override
    public boolean performContextMenuAction(int n) {
        return false;
    }

    @Override
    public boolean performEditorAction(int n) {
        long l = SystemClock.uptimeMillis();
        this.sendKeyEvent(new KeyEvent(l, l, 0, 66, 0, 0, -1, 0, 22));
        this.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), l, 1, 66, 0, 0, -1, 0, 22));
        return true;
    }

    @Override
    public boolean performPrivateCommand(String string2, Bundle bundle) {
        return false;
    }

    @Override
    public boolean reportFullscreenMode(boolean bl) {
        return true;
    }

    @Override
    public boolean requestCursorUpdates(int n) {
        return false;
    }

    @Override
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        this.mIMM.dispatchKeyEventFromInputMethod(this.mTargetView, keyEvent);
        return false;
    }

    @Override
    public boolean setComposingRegion(int n, int n2) {
        Editable editable = this.getEditable();
        if (editable != null) {
            int n3;
            this.beginBatchEdit();
            BaseInputConnection.removeComposingSpans(editable);
            int n4 = n3 = n;
            n = n2;
            if (n3 > n2) {
                n = n3;
                n4 = n2;
            }
            n3 = editable.length();
            n2 = n4;
            if (n4 < 0) {
                n2 = 0;
            }
            n4 = n;
            if (n < 0) {
                n4 = 0;
            }
            n = n2;
            if (n2 > n3) {
                n = n3;
            }
            n2 = n4;
            if (n4 > n3) {
                n2 = n3;
            }
            this.ensureDefaultComposingSpans();
            if (this.mDefaultComposingSpans != null) {
                Object[] arrobject;
                for (n4 = 0; n4 < (arrobject = this.mDefaultComposingSpans).length; ++n4) {
                    editable.setSpan(arrobject[n4], n, n2, 289);
                }
            }
            editable.setSpan(COMPOSING, n, n2, 289);
            this.sendCurrentText();
            this.endBatchEdit();
        }
        return true;
    }

    @Override
    public boolean setComposingText(CharSequence charSequence, int n) {
        this.replaceText(charSequence, n, true);
        return true;
    }

    @Override
    public boolean setSelection(int n, int n2) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return false;
        }
        int n3 = editable.length();
        if (n <= n3 && n2 <= n3 && n >= 0 && n2 >= 0) {
            if (n == n2 && MetaKeyKeyListener.getMetaState((CharSequence)editable, 2048) != 0) {
                Selection.extendSelection(editable, n);
            } else {
                Selection.setSelection(editable, n, n2);
            }
            return true;
        }
        return true;
    }
}

