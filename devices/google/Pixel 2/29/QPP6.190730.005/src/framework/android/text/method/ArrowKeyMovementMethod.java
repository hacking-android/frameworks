/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.graphics.Rect;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.BaseMovementMethod;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.text.method.WordIterator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.TextView;

public class ArrowKeyMovementMethod
extends BaseMovementMethod
implements MovementMethod {
    private static final Object LAST_TAP_DOWN = new Object();
    private static ArrowKeyMovementMethod sInstance;

    private static int getCurrentLineTop(Spannable spannable, Layout layout2) {
        return layout2.getLineTop(layout2.getLineForOffset(Selection.getSelectionEnd(spannable)));
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ArrowKeyMovementMethod();
        }
        return sInstance;
    }

    private static int getPageHeight(TextView textView) {
        Rect rect = new Rect();
        int n = textView.getGlobalVisibleRect(rect) ? rect.height() : 0;
        return n;
    }

    private static boolean isSelecting(Spannable spannable) {
        boolean bl;
        block0 : {
            bl = true;
            if (MetaKeyKeyListener.getMetaState((CharSequence)spannable, 1) == 1 || MetaKeyKeyListener.getMetaState((CharSequence)spannable, 2048) != 0) break block0;
            bl = false;
        }
        return bl;
    }

    @Override
    protected boolean bottom(TextView textView, Spannable spannable) {
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            Selection.extendSelection(spannable, spannable.length());
        } else {
            Selection.setSelection(spannable, spannable.length());
        }
        return true;
    }

    @Override
    public boolean canSelectArbitrarily() {
        return true;
    }

    @Override
    protected boolean down(TextView object, Spannable spannable) {
        object = ((TextView)object).getLayout();
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            return Selection.extendDown(spannable, (Layout)object);
        }
        return Selection.moveDown(spannable, (Layout)object);
    }

    @Override
    protected boolean end(TextView textView, Spannable spannable) {
        return this.lineEnd(textView, spannable);
    }

    @Override
    protected boolean handleMovementKey(TextView textView, Spannable spannable, int n, int n2, KeyEvent keyEvent) {
        if (n == 23 && KeyEvent.metaStateHasNoModifiers(n2) && keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0 && MetaKeyKeyListener.getMetaState(spannable, 2048, keyEvent) != 0) {
            return textView.showContextMenu();
        }
        return super.handleMovementKey(textView, spannable, n, n2, keyEvent);
    }

    @Override
    protected boolean home(TextView textView, Spannable spannable) {
        return this.lineStart(textView, spannable);
    }

    @Override
    public void initialize(TextView textView, Spannable spannable) {
        Selection.setSelection(spannable, 0);
    }

    @Override
    protected boolean left(TextView object, Spannable spannable) {
        object = ((TextView)object).getLayout();
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            return Selection.extendLeft(spannable, (Layout)object);
        }
        return Selection.moveLeft(spannable, (Layout)object);
    }

    @Override
    protected boolean leftWord(TextView object, Spannable spannable) {
        int n = ((TextView)object).getSelectionEnd();
        object = ((TextView)object).getWordIterator();
        ((WordIterator)object).setCharSequence(spannable, n, n);
        return Selection.moveToPreceding(spannable, (Selection.PositionIterator)object, ArrowKeyMovementMethod.isSelecting(spannable));
    }

    @Override
    protected boolean lineEnd(TextView object, Spannable spannable) {
        object = ((TextView)object).getLayout();
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            return Selection.extendToRightEdge(spannable, (Layout)object);
        }
        return Selection.moveToRightEdge(spannable, (Layout)object);
    }

    @Override
    protected boolean lineStart(TextView object, Spannable spannable) {
        object = ((TextView)object).getLayout();
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            return Selection.extendToLeftEdge(spannable, (Layout)object);
        }
        return Selection.moveToLeftEdge(spannable, (Layout)object);
    }

    @Override
    public void onTakeFocus(TextView textView, Spannable spannable, int n) {
        if ((n & 130) != 0) {
            if (textView.getLayout() == null) {
                Selection.setSelection(spannable, spannable.length());
            }
        } else {
            Selection.setSelection(spannable, spannable.length());
        }
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        int n = -1;
        int n2 = -1;
        int n3 = motionEvent.getAction();
        if (n3 == 1) {
            n = Touch.getInitialScrollX(textView, spannable);
            n2 = Touch.getInitialScrollY(textView, spannable);
        }
        boolean bl = ArrowKeyMovementMethod.isSelecting(spannable);
        boolean bl2 = Touch.onTouchEvent(textView, spannable, motionEvent);
        if (textView.didTouchFocusSelect()) {
            return bl2;
        }
        if (n3 == 0) {
            if (ArrowKeyMovementMethod.isSelecting(spannable)) {
                if (!textView.isFocused() && !textView.requestFocus()) {
                    return bl2;
                }
                n = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                spannable.setSpan(LAST_TAP_DOWN, n, n, 34);
                textView.getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (textView.isFocused()) {
            if (n3 == 2) {
                if (ArrowKeyMovementMethod.isSelecting(spannable) && bl2) {
                    n = spannable.getSpanStart(LAST_TAP_DOWN);
                    textView.cancelLongPress();
                    n2 = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    Selection.setSelection(spannable, Math.min(n, n2), Math.max(n, n2));
                    return true;
                }
            } else if (n3 == 1) {
                if (n2 >= 0 && n2 != textView.getScrollY() || n >= 0 && n != textView.getScrollX()) {
                    textView.moveCursorToVisibleOffset();
                    return true;
                }
                if (bl) {
                    n = spannable.getSpanStart(LAST_TAP_DOWN);
                    n2 = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    Selection.setSelection(spannable, Math.min(n, n2), Math.max(n, n2));
                    spannable.removeSpan(LAST_TAP_DOWN);
                }
                MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
                MetaKeyKeyListener.resetLockedMeta(spannable);
                return true;
            }
        }
        return bl2;
    }

    @Override
    protected boolean pageDown(TextView textView, Spannable spannable) {
        boolean bl;
        block3 : {
            boolean bl2;
            Layout layout2 = textView.getLayout();
            boolean bl3 = ArrowKeyMovementMethod.isSelecting(spannable);
            int n = ArrowKeyMovementMethod.getCurrentLineTop(spannable, layout2);
            int n2 = ArrowKeyMovementMethod.getPageHeight(textView);
            bl = false;
            do {
                int n3 = Selection.getSelectionEnd(spannable);
                if (bl3) {
                    Selection.extendDown(spannable, layout2);
                } else {
                    Selection.moveDown(spannable, layout2);
                }
                if (Selection.getSelectionEnd(spannable) == n3) break block3;
                bl2 = true;
                bl = true;
            } while (ArrowKeyMovementMethod.getCurrentLineTop(spannable, layout2) < n + n2);
            bl = bl2;
        }
        return bl;
    }

    @Override
    protected boolean pageUp(TextView textView, Spannable spannable) {
        boolean bl;
        block3 : {
            boolean bl2;
            Layout layout2 = textView.getLayout();
            boolean bl3 = ArrowKeyMovementMethod.isSelecting(spannable);
            int n = ArrowKeyMovementMethod.getCurrentLineTop(spannable, layout2);
            int n2 = ArrowKeyMovementMethod.getPageHeight(textView);
            bl = false;
            do {
                int n3 = Selection.getSelectionEnd(spannable);
                if (bl3) {
                    Selection.extendUp(spannable, layout2);
                } else {
                    Selection.moveUp(spannable, layout2);
                }
                if (Selection.getSelectionEnd(spannable) == n3) break block3;
                bl2 = true;
                bl = true;
            } while (ArrowKeyMovementMethod.getCurrentLineTop(spannable, layout2) > n - n2);
            bl = bl2;
        }
        return bl;
    }

    @Override
    protected boolean right(TextView object, Spannable spannable) {
        object = ((TextView)object).getLayout();
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            return Selection.extendRight(spannable, (Layout)object);
        }
        return Selection.moveRight(spannable, (Layout)object);
    }

    @Override
    protected boolean rightWord(TextView object, Spannable spannable) {
        int n = ((TextView)object).getSelectionEnd();
        object = ((TextView)object).getWordIterator();
        ((WordIterator)object).setCharSequence(spannable, n, n);
        return Selection.moveToFollowing(spannable, (Selection.PositionIterator)object, ArrowKeyMovementMethod.isSelecting(spannable));
    }

    @Override
    protected boolean top(TextView textView, Spannable spannable) {
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            Selection.extendSelection(spannable, 0);
        } else {
            Selection.setSelection(spannable, 0);
        }
        return true;
    }

    @Override
    protected boolean up(TextView object, Spannable spannable) {
        object = ((TextView)object).getLayout();
        if (ArrowKeyMovementMethod.isSelecting(spannable)) {
            return Selection.extendUp(spannable, (Layout)object);
        }
        return Selection.moveUp(spannable, (Layout)object);
    }
}

