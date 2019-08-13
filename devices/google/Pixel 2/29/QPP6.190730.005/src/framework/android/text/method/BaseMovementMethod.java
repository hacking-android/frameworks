/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.Layout;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

public class BaseMovementMethod
implements MovementMethod {
    private int getBottomLine(TextView textView) {
        return textView.getLayout().getLineForVertical(textView.getScrollY() + this.getInnerHeight(textView));
    }

    private int getCharacterWidth(TextView textView) {
        return (int)Math.ceil(textView.getPaint().getFontSpacing());
    }

    private int getInnerHeight(TextView textView) {
        return textView.getHeight() - textView.getTotalPaddingTop() - textView.getTotalPaddingBottom();
    }

    private int getInnerWidth(TextView textView) {
        return textView.getWidth() - textView.getTotalPaddingLeft() - textView.getTotalPaddingRight();
    }

    private int getScrollBoundsLeft(TextView textView) {
        int n;
        Layout layout2 = textView.getLayout();
        int n2 = this.getTopLine(textView);
        if (n2 > (n = this.getBottomLine(textView))) {
            return 0;
        }
        int n3 = Integer.MAX_VALUE;
        while (n2 <= n) {
            int n4 = (int)Math.floor(layout2.getLineLeft(n2));
            int n5 = n3;
            if (n4 < n3) {
                n5 = n4;
            }
            ++n2;
            n3 = n5;
        }
        return n3;
    }

    private int getScrollBoundsRight(TextView textView) {
        int n;
        Layout layout2 = textView.getLayout();
        int n2 = this.getTopLine(textView);
        if (n2 > (n = this.getBottomLine(textView))) {
            return 0;
        }
        int n3 = Integer.MIN_VALUE;
        while (n2 <= n) {
            int n4 = (int)Math.ceil(layout2.getLineRight(n2));
            int n5 = n3;
            if (n4 > n3) {
                n5 = n4;
            }
            ++n2;
            n3 = n5;
        }
        return n3;
    }

    private int getTopLine(TextView textView) {
        return textView.getLayout().getLineForVertical(textView.getScrollY());
    }

    protected boolean bottom(TextView textView, Spannable spannable) {
        return false;
    }

    @Override
    public boolean canSelectArbitrarily() {
        return false;
    }

    protected boolean down(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean end(TextView textView, Spannable spannable) {
        return false;
    }

    protected int getMovementMetaState(Spannable spannable, KeyEvent keyEvent) {
        return KeyEvent.normalizeMetaState(MetaKeyKeyListener.getMetaState((CharSequence)spannable, keyEvent) & -1537) & -194;
    }

    protected boolean handleMovementKey(TextView textView, Spannable spannable, int n, int n2, KeyEvent keyEvent) {
        block28 : {
            block24 : {
                block25 : {
                    block26 : {
                        block27 : {
                            if (n == 92) break block24;
                            if (n == 93) break block25;
                            if (n == 122) break block26;
                            if (n == 123) break block27;
                            switch (n) {
                                default: {
                                    break block28;
                                }
                                case 22: {
                                    if (KeyEvent.metaStateHasNoModifiers(n2)) {
                                        return this.right(textView, spannable);
                                    }
                                    if (KeyEvent.metaStateHasModifiers(n2, 4096)) {
                                        return this.rightWord(textView, spannable);
                                    }
                                    if (KeyEvent.metaStateHasModifiers(n2, 2)) {
                                        return this.lineEnd(textView, spannable);
                                    }
                                    break block28;
                                }
                                case 21: {
                                    if (KeyEvent.metaStateHasNoModifiers(n2)) {
                                        return this.left(textView, spannable);
                                    }
                                    if (KeyEvent.metaStateHasModifiers(n2, 4096)) {
                                        return this.leftWord(textView, spannable);
                                    }
                                    if (KeyEvent.metaStateHasModifiers(n2, 2)) {
                                        return this.lineStart(textView, spannable);
                                    }
                                    break block28;
                                }
                                case 20: {
                                    if (KeyEvent.metaStateHasNoModifiers(n2)) {
                                        return this.down(textView, spannable);
                                    }
                                    if (KeyEvent.metaStateHasModifiers(n2, 2)) {
                                        return this.bottom(textView, spannable);
                                    }
                                    break block28;
                                }
                                case 19: {
                                    if (KeyEvent.metaStateHasNoModifiers(n2)) {
                                        return this.up(textView, spannable);
                                    }
                                    if (KeyEvent.metaStateHasModifiers(n2, 2)) {
                                        return this.top(textView, spannable);
                                    }
                                    break block28;
                                }
                            }
                        }
                        if (KeyEvent.metaStateHasNoModifiers(n2)) {
                            return this.end(textView, spannable);
                        }
                        if (KeyEvent.metaStateHasModifiers(n2, 4096)) {
                            return this.bottom(textView, spannable);
                        }
                        break block28;
                    }
                    if (KeyEvent.metaStateHasNoModifiers(n2)) {
                        return this.home(textView, spannable);
                    }
                    if (KeyEvent.metaStateHasModifiers(n2, 4096)) {
                        return this.top(textView, spannable);
                    }
                    break block28;
                }
                if (KeyEvent.metaStateHasNoModifiers(n2)) {
                    return this.pageDown(textView, spannable);
                }
                if (KeyEvent.metaStateHasModifiers(n2, 2)) {
                    return this.bottom(textView, spannable);
                }
                break block28;
            }
            if (KeyEvent.metaStateHasNoModifiers(n2)) {
                return this.pageUp(textView, spannable);
            }
            if (KeyEvent.metaStateHasModifiers(n2, 2)) {
                return this.top(textView, spannable);
            }
        }
        return false;
    }

    protected boolean home(TextView textView, Spannable spannable) {
        return false;
    }

    @Override
    public void initialize(TextView textView, Spannable spannable) {
    }

    protected boolean left(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean leftWord(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean lineEnd(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean lineStart(TextView textView, Spannable spannable) {
        return false;
    }

    @Override
    public boolean onGenericMotionEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            float f;
            boolean bl;
            float f2;
            if ((motionEvent.getMetaState() & 1) != 0) {
                f2 = 0.0f;
                f = motionEvent.getAxisValue(9);
            } else {
                f2 = -motionEvent.getAxisValue(9);
                f = motionEvent.getAxisValue(10);
            }
            boolean bl2 = false;
            if (f < 0.0f) {
                bl2 = false | this.scrollLeft(textView, spannable, (int)Math.ceil(-f));
            } else if (f > 0.0f) {
                bl2 = false | this.scrollRight(textView, spannable, (int)Math.ceil(f));
            }
            if (f2 < 0.0f) {
                bl = bl2 | this.scrollUp(textView, spannable, (int)Math.ceil(-f2));
            } else {
                bl = bl2;
                if (f2 > 0.0f) {
                    bl = bl2 | this.scrollDown(textView, spannable, (int)Math.ceil(f2));
                }
            }
            return bl;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(TextView textView, Spannable spannable, int n, KeyEvent keyEvent) {
        boolean bl = this.handleMovementKey(textView, spannable, n, this.getMovementMetaState(spannable, keyEvent), keyEvent);
        if (bl) {
            MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
            MetaKeyKeyListener.resetLockedMeta(spannable);
        }
        return bl;
    }

    @Override
    public boolean onKeyOther(TextView textView, Spannable spannable, KeyEvent keyEvent) {
        int n = this.getMovementMetaState(spannable, keyEvent);
        int n2 = keyEvent.getKeyCode();
        if (n2 != 0 && keyEvent.getAction() == 2) {
            int n3 = keyEvent.getRepeatCount();
            boolean bl = false;
            for (int i = 0; i < n3 && this.handleMovementKey(textView, spannable, n2, n, keyEvent); ++i) {
                bl = true;
            }
            if (bl) {
                MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
                MetaKeyKeyListener.resetLockedMeta(spannable);
            }
            return bl;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(TextView textView, Spannable spannable, int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onTakeFocus(TextView textView, Spannable spannable, int n) {
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onTrackballEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        return false;
    }

    protected boolean pageDown(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean pageUp(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean right(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean rightWord(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean scrollBottom(TextView textView, Spannable object) {
        object = textView.getLayout();
        int n = ((Layout)object).getLineCount();
        if (this.getBottomLine(textView) <= n - 1) {
            Touch.scrollTo(textView, (Layout)object, textView.getScrollX(), ((Layout)object).getLineTop(n) - this.getInnerHeight(textView));
            return true;
        }
        return false;
    }

    protected boolean scrollDown(TextView textView, Spannable object, int n) {
        int n2;
        object = textView.getLayout();
        int n3 = this.getInnerHeight(textView);
        int n4 = textView.getScrollY() + n3;
        int n5 = n2 = ((Layout)object).getLineForVertical(n4);
        if (((Layout)object).getLineTop(n2 + 1) < n4 + 1) {
            n5 = n2 + 1;
        }
        if (n5 <= (n2 = ((Layout)object).getLineCount() - 1)) {
            n = Math.min(n5 + n - 1, n2);
            Touch.scrollTo(textView, (Layout)object, textView.getScrollX(), ((Layout)object).getLineTop(n + 1) - n3);
            return true;
        }
        return false;
    }

    protected boolean scrollLeft(TextView textView, Spannable spannable, int n) {
        int n2 = this.getScrollBoundsLeft(textView);
        int n3 = textView.getScrollX();
        if (n3 > n2) {
            textView.scrollTo(Math.max(n3 - this.getCharacterWidth(textView) * n, n2), textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollLineEnd(TextView textView, Spannable spannable) {
        int n = this.getScrollBoundsRight(textView) - this.getInnerWidth(textView);
        if (textView.getScrollX() < n) {
            textView.scrollTo(n, textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollLineStart(TextView textView, Spannable spannable) {
        int n = this.getScrollBoundsLeft(textView);
        if (textView.getScrollX() > n) {
            textView.scrollTo(n, textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollPageDown(TextView textView, Spannable object) {
        object = textView.getLayout();
        int n = this.getInnerHeight(textView);
        int n2 = ((Layout)object).getLineForVertical(textView.getScrollY() + n + n);
        if (n2 <= ((Layout)object).getLineCount() - 1) {
            Touch.scrollTo(textView, (Layout)object, textView.getScrollX(), ((Layout)object).getLineTop(n2 + 1) - n);
            return true;
        }
        return false;
    }

    protected boolean scrollPageUp(TextView textView, Spannable object) {
        object = textView.getLayout();
        int n = ((Layout)object).getLineForVertical(textView.getScrollY() - this.getInnerHeight(textView));
        if (n >= 0) {
            Touch.scrollTo(textView, (Layout)object, textView.getScrollX(), ((Layout)object).getLineTop(n));
            return true;
        }
        return false;
    }

    protected boolean scrollRight(TextView textView, Spannable spannable, int n) {
        int n2 = this.getScrollBoundsRight(textView) - this.getInnerWidth(textView);
        int n3 = textView.getScrollX();
        if (n3 < n2) {
            textView.scrollTo(Math.min(this.getCharacterWidth(textView) * n + n3, n2), textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollTop(TextView textView, Spannable object) {
        object = textView.getLayout();
        if (this.getTopLine(textView) >= 0) {
            Touch.scrollTo(textView, (Layout)object, textView.getScrollX(), ((Layout)object).getLineTop(0));
            return true;
        }
        return false;
    }

    protected boolean scrollUp(TextView textView, Spannable object, int n) {
        int n2;
        object = textView.getLayout();
        int n3 = textView.getScrollY();
        int n4 = n2 = ((Layout)object).getLineForVertical(n3);
        if (((Layout)object).getLineTop(n2) == n3) {
            n4 = n2 - 1;
        }
        if (n4 >= 0) {
            n = Math.max(n4 - n + 1, 0);
            Touch.scrollTo(textView, (Layout)object, textView.getScrollX(), ((Layout)object).getLineTop(n));
            return true;
        }
        return false;
    }

    protected boolean top(TextView textView, Spannable spannable) {
        return false;
    }

    protected boolean up(TextView textView, Spannable spannable) {
        return false;
    }
}

