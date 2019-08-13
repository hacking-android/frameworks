/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.lang.UCharacter
 */
package android.text.method;

import android.graphics.Paint;
import android.icu.lang.UCharacter;
import android.text.Editable;
import android.text.Emoji;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.KeyListener;
import android.text.method.MetaKeyKeyListener;
import android.text.method.TextKeyListener;
import android.text.method.WordIterator;
import android.text.style.ReplacementSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.android.internal.annotations.GuardedBy;

public abstract class BaseKeyListener
extends MetaKeyKeyListener
implements KeyListener {
    private static final int CARRIAGE_RETURN = 13;
    private static final int LINE_FEED = 10;
    static final Object OLD_SEL_START = new NoCopySpan.Concrete();
    @GuardedBy(value={"mLock"})
    static Paint sCachedPaint = null;
    private final Object mLock = new Object();

    private static int adjustReplacementSpan(CharSequence charSequence, int n, boolean bl) {
        if (!(charSequence instanceof Spanned)) {
            return n;
        }
        ReplacementSpan[] arrreplacementSpan = ((Spanned)charSequence).getSpans(n, n, ReplacementSpan.class);
        int n2 = n;
        for (int i = 0; i < arrreplacementSpan.length; ++i) {
            int n3 = ((Spanned)charSequence).getSpanStart(arrreplacementSpan[i]);
            int n4 = ((Spanned)charSequence).getSpanEnd(arrreplacementSpan[i]);
            n = n2;
            if (n3 < n2) {
                n = n2;
                if (n4 > n2) {
                    n = bl ? n3 : n4;
                }
            }
            n2 = n;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean backspaceOrForwardDelete(View object, Editable editable, int n, KeyEvent object2, boolean bl) {
        if (!KeyEvent.metaStateHasNoModifiers(((KeyEvent)object2).getMetaState() & -28916)) {
            return false;
        }
        if (this.deleteSelection((View)object, editable)) {
            return true;
        }
        n = (((KeyEvent)object2).getMetaState() & 4096) != 0 ? 1 : 0;
        int n2 = BaseKeyListener.getMetaState(editable, 1, (KeyEvent)object2) == 1 ? 1 : 0;
        boolean bl2 = BaseKeyListener.getMetaState(editable, 2, (KeyEvent)object2) == 1;
        if (n != 0) {
            if (bl2) return false;
            if (n2 == 0) return this.deleteUntilWordBoundary((View)object, editable, bl);
            return false;
        }
        if (bl2 && this.deleteLine((View)object, editable)) {
            return true;
        }
        n2 = Selection.getSelectionEnd(editable);
        if (bl) {
            if (object instanceof TextView) {
                object = ((TextView)object).getPaint();
            } else {
                object2 = this.mLock;
                synchronized (object2) {
                    if (sCachedPaint == null) {
                        sCachedPaint = object = new Paint();
                    }
                    object = sCachedPaint;
                }
            }
            n = BaseKeyListener.getOffsetForForwardDeleteKey(editable, n2, (Paint)object);
        } else {
            n = BaseKeyListener.getOffsetForBackspaceKey(editable, n2);
        }
        if (n2 == n) return false;
        editable.delete(Math.min(n2, n), Math.max(n2, n));
        return true;
    }

    private boolean deleteLine(View object, Editable editable) {
        if (object instanceof TextView && (object = ((TextView)object).getLayout()) != null) {
            int n = ((Layout)object).getLineForOffset(Selection.getSelectionStart(editable));
            int n2 = ((Layout)object).getLineStart(n);
            if ((n = ((Layout)object).getLineEnd(n)) != n2) {
                editable.delete(n2, n);
                return true;
            }
        }
        return false;
    }

    private boolean deleteSelection(View view, Editable editable) {
        int n = Selection.getSelectionStart(editable);
        int n2 = Selection.getSelectionEnd(editable);
        int n3 = n;
        int n4 = n2;
        if (n2 < n) {
            n4 = n;
            n3 = n2;
        }
        if (n3 != n4) {
            editable.delete(n3, n4);
            return true;
        }
        return false;
    }

    private boolean deleteUntilWordBoundary(View object, Editable editable, boolean bl) {
        int n;
        int n2 = Selection.getSelectionStart(editable);
        if (n2 != Selection.getSelectionEnd(editable)) {
            return false;
        }
        if (!bl && n2 == 0 || bl && n2 == editable.length()) {
            return false;
        }
        WordIterator wordIterator = null;
        if (object instanceof TextView) {
            wordIterator = ((TextView)object).getWordIterator();
        }
        object = wordIterator;
        if (wordIterator == null) {
            object = new WordIterator();
        }
        if (bl) {
            int n3 = n2;
            ((WordIterator)object).setCharSequence(editable, n3, editable.length());
            int n4 = ((WordIterator)object).following(n2);
            n = n3;
            n2 = n4;
            if (n4 == -1) {
                n2 = editable.length();
                n = n3;
            }
        } else {
            int n5;
            int n6 = n2;
            ((WordIterator)object).setCharSequence(editable, 0, n6);
            n = n5 = ((WordIterator)object).preceding(n2);
            n2 = n6;
            if (n5 == -1) {
                n = 0;
                n2 = n6;
            }
        }
        editable.delete(n, n2);
        return true;
    }

    private static int getOffsetForBackspaceKey(CharSequence charSequence, int n) {
        if (n <= 1) {
            return 0;
        }
        boolean bl = true;
        int n2 = 2;
        int n3 = n;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        do {
            int n7;
            int n8 = Character.codePointBefore(charSequence, n3);
            n3 -= Character.charCount(n8);
            switch (n4) {
                default: {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("state ");
                    ((StringBuilder)charSequence).append(n4);
                    ((StringBuilder)charSequence).append(" is unknown");
                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                }
                case 12: {
                    if (Emoji.isTagSpecChar(n8)) {
                        n6 += 2;
                        n7 = n5;
                        break;
                    }
                    if (Emoji.isEmoji(n8)) {
                        n6 += Character.charCount(n8);
                        n4 = 13;
                        n7 = n5;
                        break;
                    }
                    n6 = 2;
                    n4 = 13;
                    n7 = n5;
                    break;
                }
                case 11: {
                    if (Emoji.isRegionalIndicatorSymbol(n8)) {
                        n6 -= 2;
                        n4 = 10;
                        n7 = n5;
                        break;
                    }
                    n4 = 13;
                    n7 = n5;
                    break;
                }
                case 10: {
                    if (Emoji.isRegionalIndicatorSymbol(n8)) {
                        n6 += 2;
                        n4 = 11;
                        n7 = n5;
                        break;
                    }
                    n4 = 13;
                    n7 = n5;
                    break;
                }
                case 9: {
                    if (Emoji.isEmoji(n8)) {
                        n6 += n5 + 1 + Character.charCount(n8);
                        n7 = 0;
                        n4 = 7;
                        break;
                    }
                    n4 = 13;
                    n7 = n5;
                    break;
                }
                case 8: {
                    if (Emoji.isEmoji(n8)) {
                        n6 += Character.charCount(n8) + 1;
                        n4 = Emoji.isEmojiModifier(n8) ? 4 : 7;
                        n7 = n5;
                        break;
                    }
                    if (BaseKeyListener.isVariationSelector(n8)) {
                        n7 = Character.charCount(n8);
                        n4 = 9;
                        break;
                    }
                    n4 = 13;
                    n7 = n5;
                    break;
                }
                case 7: {
                    if (n8 == Emoji.ZERO_WIDTH_JOINER) {
                        n4 = 8;
                        n7 = n5;
                        break;
                    }
                    n4 = 13;
                    n7 = n5;
                    break;
                }
                case 6: {
                    if (Emoji.isEmoji(n8)) {
                        n6 += Character.charCount(n8);
                        n4 = 7;
                        n7 = n5;
                        break;
                    }
                    n7 = n6;
                    if (!BaseKeyListener.isVariationSelector(n8)) {
                        n7 = n6;
                        if (UCharacter.getCombiningClass((int)n8) == 0) {
                            n7 = n6 + Character.charCount(n8);
                        }
                    }
                    n4 = 13;
                    n6 = n7;
                    n7 = n5;
                    break;
                }
                case 5: {
                    n7 = n6;
                    if (Emoji.isEmojiModifierBase(n8)) {
                        n7 = n6 + (n5 + Character.charCount(n8));
                    }
                    n4 = 13;
                    n6 = n7;
                    n7 = n5;
                    break;
                }
                case 4: {
                    if (BaseKeyListener.isVariationSelector(n8)) {
                        n7 = Character.charCount(n8);
                        n4 = 5;
                        break;
                    }
                    n7 = n6;
                    if (Emoji.isEmojiModifierBase(n8)) {
                        n7 = n6 + Character.charCount(n8);
                    }
                    n4 = 13;
                    n6 = n7;
                    n7 = n5;
                    break;
                }
                case 3: {
                    n7 = n6;
                    if (Emoji.isKeycapBase(n8)) {
                        n7 = n6 + (n5 + Character.charCount(n8));
                    }
                    n4 = 13;
                    n6 = n7;
                    n7 = n5;
                    break;
                }
                case 2: {
                    if (BaseKeyListener.isVariationSelector(n8)) {
                        n7 = Character.charCount(n8);
                        n4 = 3;
                        break;
                    }
                    n7 = n6;
                    if (Emoji.isKeycapBase(n8)) {
                        n7 = n6 + Character.charCount(n8);
                    }
                    n4 = 13;
                    n6 = n7;
                    n7 = n5;
                    break;
                }
                case 1: {
                    n7 = n6;
                    if (n8 == 13) {
                        n7 = n6 + 1;
                    }
                    n4 = 13;
                    n6 = n7;
                    n7 = n5;
                    break;
                }
                case 0: {
                    n6 = Character.charCount(n8);
                    if (n8 == 10) {
                        n4 = 1;
                        n7 = n5;
                        break;
                    }
                    if (BaseKeyListener.isVariationSelector(n8)) {
                        n4 = 6;
                        n7 = n5;
                        break;
                    }
                    if (Emoji.isRegionalIndicatorSymbol(n8)) {
                        n4 = 10;
                        n7 = n5;
                        break;
                    }
                    if (Emoji.isEmojiModifier(n8)) {
                        n4 = 4;
                        n7 = n5;
                        break;
                    }
                    if (n8 == Emoji.COMBINING_ENCLOSING_KEYCAP) {
                        n4 = 2;
                        n7 = n5;
                        break;
                    }
                    if (Emoji.isEmoji(n8)) {
                        n4 = 7;
                        n7 = n5;
                        break;
                    }
                    if (n8 == Emoji.CANCEL_TAG) {
                        n4 = 12;
                        n7 = n5;
                        break;
                    }
                    n4 = 13;
                    n7 = n5;
                }
            }
            if (n3 <= 0 || n4 == 13) break;
            n5 = n7;
        } while (true);
        return BaseKeyListener.adjustReplacementSpan(charSequence, n - n6, true);
    }

    private static int getOffsetForForwardDeleteKey(CharSequence charSequence, int n, Paint paint) {
        int n2 = charSequence.length();
        if (n >= n2 - 1) {
            return n2;
        }
        return BaseKeyListener.adjustReplacementSpan(charSequence, paint.getTextRunCursor(charSequence, n, n2, false, n, 0), false);
    }

    private static boolean isVariationSelector(int n) {
        return UCharacter.hasBinaryProperty((int)n, (int)36);
    }

    static int makeTextContentType(TextKeyListener.Capitalize capitalize, boolean bl) {
        int n = 1;
        int n2 = 1.$SwitchMap$android$text$method$TextKeyListener$Capitalize[capitalize.ordinal()];
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 == 3) {
                    n = 1 | 16384;
                }
            } else {
                n = 1 | 8192;
            }
        } else {
            n = 1 | 4096;
        }
        n2 = n;
        if (bl) {
            n2 = n | 32768;
        }
        return n2;
    }

    public boolean backspace(View view, Editable editable, int n, KeyEvent keyEvent) {
        return this.backspaceOrForwardDelete(view, editable, n, keyEvent, false);
    }

    public boolean forwardDelete(View view, Editable editable, int n, KeyEvent keyEvent) {
        return this.backspaceOrForwardDelete(view, editable, n, keyEvent, true);
    }

    @Override
    public boolean onKeyDown(View view, Editable editable, int n, KeyEvent keyEvent) {
        boolean bl = n != 67 ? (n != 112 ? false : this.forwardDelete(view, editable, n, keyEvent)) : this.backspace(view, editable, n, keyEvent);
        if (bl) {
            BaseKeyListener.adjustMetaAfterKeypress(editable);
            return true;
        }
        return super.onKeyDown(view, editable, n, keyEvent);
    }

    @Override
    public boolean onKeyOther(View object, Editable editable, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 2 && keyEvent.getKeyCode() == 0) {
            int n = Selection.getSelectionStart(editable);
            int n2 = Selection.getSelectionEnd(editable);
            int n3 = n;
            int n4 = n2;
            if (n2 < n) {
                n4 = n;
                n3 = n2;
            }
            if ((object = keyEvent.getCharacters()) == null) {
                return false;
            }
            editable.replace(n3, n4, (CharSequence)object);
            return true;
        }
        return false;
    }

}

