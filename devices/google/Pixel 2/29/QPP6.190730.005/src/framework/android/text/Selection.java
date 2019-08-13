/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.text.Editable;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;

public class Selection {
    public static final Object SELECTION_END;
    private static final Object SELECTION_MEMORY;
    public static final Object SELECTION_START;

    static {
        SELECTION_MEMORY = new MEMORY();
        SELECTION_START = new START();
        SELECTION_END = new END();
    }

    private Selection() {
    }

    private static int chooseHorizontal(Layout layout2, int n, int n2, int n3) {
        if (layout2.getLineForOffset(n2) == layout2.getLineForOffset(n3)) {
            float f = layout2.getPrimaryHorizontal(n2);
            float f2 = layout2.getPrimaryHorizontal(n3);
            if (n < 0) {
                if (f < f2) {
                    return n2;
                }
                return n3;
            }
            if (f > f2) {
                return n2;
            }
            return n3;
        }
        if (layout2.getParagraphDirection(layout2.getLineForOffset(n2)) == n) {
            return Math.max(n2, n3);
        }
        return Math.min(n2, n3);
    }

    public static boolean extendDown(Spannable spannable, Layout layout2) {
        int n = Selection.getSelectionEnd(spannable);
        int n2 = layout2.getLineForOffset(n);
        if (n2 < layout2.getLineCount() - 1) {
            Selection.setSelectionAndMemory(spannable, layout2, n2, n, 1, true);
            return true;
        }
        if (n != spannable.length()) {
            Selection.extendSelection(spannable, spannable.length(), -1);
            return true;
        }
        return true;
    }

    public static boolean extendLeft(Spannable spannable, Layout layout2) {
        int n = Selection.getSelectionEnd(spannable);
        int n2 = layout2.getOffsetToLeftOf(n);
        if (n2 != n) {
            Selection.extendSelection(spannable, n2);
            return true;
        }
        return true;
    }

    public static boolean extendRight(Spannable spannable, Layout layout2) {
        int n = Selection.getSelectionEnd(spannable);
        int n2 = layout2.getOffsetToRightOf(n);
        if (n2 != n) {
            Selection.extendSelection(spannable, n2);
            return true;
        }
        return true;
    }

    public static final void extendSelection(Spannable spannable, int n) {
        Selection.extendSelection(spannable, n, -1);
    }

    private static void extendSelection(Spannable spannable, int n, int n2) {
        if (spannable.getSpanStart(SELECTION_END) != n) {
            spannable.setSpan(SELECTION_END, n, n, 34);
        }
        Selection.updateMemory(spannable, n2);
    }

    public static boolean extendToLeftEdge(Spannable spannable, Layout layout2) {
        Selection.extendSelection(spannable, Selection.findEdge(spannable, layout2, -1));
        return true;
    }

    public static boolean extendToRightEdge(Spannable spannable, Layout layout2) {
        Selection.extendSelection(spannable, Selection.findEdge(spannable, layout2, 1));
        return true;
    }

    public static boolean extendUp(Spannable spannable, Layout layout2) {
        int n = Selection.getSelectionEnd(spannable);
        int n2 = layout2.getLineForOffset(n);
        if (n2 > 0) {
            Selection.setSelectionAndMemory(spannable, layout2, n2, n, -1, true);
            return true;
        }
        if (n != 0) {
            Selection.extendSelection(spannable, 0);
            return true;
        }
        return true;
    }

    private static int findEdge(Spannable spannable, Layout layout2, int n) {
        int n2 = layout2.getLineForOffset(Selection.getSelectionEnd(spannable));
        if (n * layout2.getParagraphDirection(n2) < 0) {
            return layout2.getLineStart(n2);
        }
        n = layout2.getLineEnd(n2);
        if (n2 == layout2.getLineCount() - 1) {
            return n;
        }
        return n - 1;
    }

    public static final int getSelectionEnd(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned)charSequence).getSpanStart(SELECTION_END);
        }
        return -1;
    }

    private static int getSelectionMemory(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned)charSequence).getSpanStart(SELECTION_MEMORY);
        }
        return -1;
    }

    public static final int getSelectionStart(CharSequence charSequence) {
        if (charSequence instanceof Spanned) {
            return ((Spanned)charSequence).getSpanStart(SELECTION_START);
        }
        return -1;
    }

    public static boolean moveDown(Spannable spannable, Layout layout2) {
        int n;
        int n2 = Selection.getSelectionStart(spannable);
        if (n2 != (n = Selection.getSelectionEnd(spannable))) {
            int n3 = Math.min(n2, n);
            n = Math.max(n2, n);
            Selection.setSelection(spannable, n);
            return n3 != 0 || n != spannable.length();
        }
        int n4 = layout2.getLineForOffset(n);
        if (n4 < layout2.getLineCount() - 1) {
            Selection.setSelectionAndMemory(spannable, layout2, n4, n, 1, false);
            return true;
        }
        if (n != spannable.length()) {
            Selection.setSelection(spannable, spannable.length());
            return true;
        }
        return false;
    }

    public static boolean moveLeft(Spannable spannable, Layout layout2) {
        int n;
        int n2 = Selection.getSelectionStart(spannable);
        if (n2 != (n = Selection.getSelectionEnd(spannable))) {
            Selection.setSelection(spannable, Selection.chooseHorizontal(layout2, -1, n2, n));
            return true;
        }
        n2 = layout2.getOffsetToLeftOf(n);
        if (n2 != n) {
            Selection.setSelection(spannable, n2);
            return true;
        }
        return false;
    }

    public static boolean moveRight(Spannable spannable, Layout layout2) {
        int n;
        int n2 = Selection.getSelectionStart(spannable);
        if (n2 != (n = Selection.getSelectionEnd(spannable))) {
            Selection.setSelection(spannable, Selection.chooseHorizontal(layout2, 1, n2, n));
            return true;
        }
        n2 = layout2.getOffsetToRightOf(n);
        if (n2 != n) {
            Selection.setSelection(spannable, n2);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean moveToFollowing(Spannable spannable, PositionIterator positionIterator, boolean bl) {
        int n = positionIterator.following(Selection.getSelectionEnd(spannable));
        if (n != -1) {
            if (bl) {
                Selection.extendSelection(spannable, n);
            } else {
                Selection.setSelection(spannable, n);
            }
        }
        return true;
    }

    public static boolean moveToLeftEdge(Spannable spannable, Layout layout2) {
        Selection.setSelection(spannable, Selection.findEdge(spannable, layout2, -1));
        return true;
    }

    @UnsupportedAppUsage
    public static boolean moveToPreceding(Spannable spannable, PositionIterator positionIterator, boolean bl) {
        int n = positionIterator.preceding(Selection.getSelectionEnd(spannable));
        if (n != -1) {
            if (bl) {
                Selection.extendSelection(spannable, n);
            } else {
                Selection.setSelection(spannable, n);
            }
        }
        return true;
    }

    public static boolean moveToRightEdge(Spannable spannable, Layout layout2) {
        Selection.setSelection(spannable, Selection.findEdge(spannable, layout2, 1));
        return true;
    }

    public static boolean moveUp(Spannable spannable, Layout layout2) {
        int n;
        int n2 = Selection.getSelectionStart(spannable);
        if (n2 != (n = Selection.getSelectionEnd(spannable))) {
            int n3 = Math.min(n2, n);
            n = Math.max(n2, n);
            Selection.setSelection(spannable, n3);
            return n3 != 0 || n != spannable.length();
        }
        int n4 = layout2.getLineForOffset(n);
        if (n4 > 0) {
            Selection.setSelectionAndMemory(spannable, layout2, n4, n, -1, false);
            return true;
        }
        if (n != 0) {
            Selection.setSelection(spannable, 0);
            return true;
        }
        return false;
    }

    private static void removeMemory(Spannable spannable) {
        spannable.removeSpan(SELECTION_MEMORY);
        int n = spannable.length();
        MemoryTextWatcher[] arrmemoryTextWatcher = spannable.getSpans(0, n, MemoryTextWatcher.class);
        n = arrmemoryTextWatcher.length;
        for (int i = 0; i < n; ++i) {
            spannable.removeSpan(arrmemoryTextWatcher[i]);
        }
    }

    public static final void removeSelection(Spannable spannable) {
        spannable.removeSpan(SELECTION_START, 512);
        spannable.removeSpan(SELECTION_END);
        Selection.removeMemory(spannable);
    }

    public static final void selectAll(Spannable spannable) {
        Selection.setSelection(spannable, 0, spannable.length());
    }

    public static final void setSelection(Spannable spannable, int n) {
        Selection.setSelection(spannable, n, n);
    }

    public static void setSelection(Spannable spannable, int n, int n2) {
        Selection.setSelection(spannable, n, n2, -1);
    }

    private static void setSelection(Spannable spannable, int n, int n2, int n3) {
        int n4 = Selection.getSelectionStart(spannable);
        int n5 = Selection.getSelectionEnd(spannable);
        if (n4 != n || n5 != n2) {
            spannable.setSpan(SELECTION_START, n, n, 546);
            spannable.setSpan(SELECTION_END, n2, n2, 34);
            Selection.updateMemory(spannable, n3);
        }
    }

    private static void setSelectionAndMemory(Spannable spannable, Layout layout2, int n, int n2, int n3, boolean bl) {
        if (layout2.getParagraphDirection(n) == layout2.getParagraphDirection(n + n3)) {
            int n4 = Selection.getSelectionMemory(spannable);
            if (n4 > -1) {
                n = layout2.getOffsetForHorizontal(n + n3, layout2.getPrimaryHorizontal(n4));
                n2 = n4;
            } else {
                n = layout2.getOffsetForHorizontal(n + n3, layout2.getPrimaryHorizontal(n2));
            }
        } else {
            n = layout2.getLineStart(n + n3);
            n2 = -1;
        }
        if (bl) {
            Selection.extendSelection(spannable, n, n2);
        } else {
            Selection.setSelection(spannable, n, n, n2);
        }
    }

    private static void updateMemory(Spannable spannable, int n) {
        if (n > -1) {
            int n2 = Selection.getSelectionMemory(spannable);
            if (n != n2) {
                spannable.setSpan(SELECTION_MEMORY, n, n, 34);
                if (n2 == -1) {
                    spannable.setSpan(new MemoryTextWatcher(), 0, spannable.length(), 18);
                }
            }
        } else {
            Selection.removeMemory(spannable);
        }
    }

    private static final class END
    implements NoCopySpan {
        private END() {
        }
    }

    private static final class MEMORY
    implements NoCopySpan {
        private MEMORY() {
        }
    }

    public static final class MemoryTextWatcher
    implements TextWatcher {
        @Override
        public void afterTextChanged(Editable editable) {
            editable.removeSpan(SELECTION_MEMORY);
            editable.removeSpan(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }
    }

    public static interface PositionIterator {
        public static final int DONE = -1;

        public int following(int var1);

        public int preceding(int var1);
    }

    private static final class START
    implements NoCopySpan {
        private START() {
        }
    }

}

