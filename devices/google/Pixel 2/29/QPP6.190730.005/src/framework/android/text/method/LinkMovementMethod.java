/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

public class LinkMovementMethod
extends ScrollingMovementMethod {
    private static final int CLICK = 1;
    private static final int DOWN = 3;
    private static Object FROM_BELOW = new NoCopySpan.Concrete();
    private static final int HIDE_FLOATING_TOOLBAR_DELAY_MS = 200;
    private static final int UP = 2;
    @UnsupportedAppUsage
    private static LinkMovementMethod sInstance;

    private boolean action(int n, TextView textView, Spannable object) {
        block19 : {
            int n2;
            int n3;
            block17 : {
                int n4;
                int n5;
                int n6;
                ClickableSpan[] arrclickableSpan;
                int n7;
                block18 : {
                    arrclickableSpan = textView.getLayout();
                    n3 = textView.getTotalPaddingTop();
                    n7 = textView.getTotalPaddingBottom();
                    n6 = textView.getScrollY();
                    n5 = textView.getHeight() + n6 - (n3 + n7);
                    n3 = arrclickableSpan.getLineForVertical(n6);
                    n7 = arrclickableSpan.getLineForVertical(n5);
                    n4 = arrclickableSpan.getLineStart(n3);
                    int n8 = arrclickableSpan.getLineEnd(n7);
                    arrclickableSpan = object.getSpans(n4, n8, ClickableSpan.class);
                    n7 = Selection.getSelectionStart((CharSequence)object);
                    n2 = Selection.getSelectionEnd((CharSequence)object);
                    n3 = Math.min(n7, n2);
                    n7 = Math.max(n7, n2);
                    if (n3 < 0 && object.getSpanStart(FROM_BELOW) >= 0) {
                        n7 = n3 = object.length();
                    }
                    n2 = n3;
                    if (n3 > n8) {
                        n7 = Integer.MAX_VALUE;
                        n2 = Integer.MAX_VALUE;
                    }
                    n3 = n7;
                    if (n7 < n4) {
                        n3 = -1;
                        n2 = -1;
                    }
                    if (n == 1) break block17;
                    if (n == 2) break block18;
                    if (n != 3) break block19;
                    n8 = Integer.MAX_VALUE;
                    n5 = Integer.MAX_VALUE;
                    n = n6;
                    n6 = n5;
                    for (n7 = 0; n7 < arrclickableSpan.length; ++n7) {
                        block21 : {
                            int n9;
                            block20 : {
                                n9 = object.getSpanStart(arrclickableSpan[n7]);
                                if (n9 > n2) break block20;
                                n4 = n8;
                                n5 = n6;
                                if (n2 != n3) break block21;
                            }
                            n4 = n8;
                            n5 = n6;
                            if (n9 < n6) {
                                n4 = object.getSpanEnd(arrclickableSpan[n7]);
                                n5 = n9;
                            }
                        }
                        n8 = n4;
                        n6 = n5;
                    }
                    if (n8 < Integer.MAX_VALUE) {
                        Selection.setSelection((Spannable)object, n6, n8);
                        return true;
                    }
                    break block19;
                }
                n4 = -1;
                n6 = -1;
                n = n5;
                n5 = n4;
                for (n7 = 0; n7 < arrclickableSpan.length; ++n7) {
                    n4 = object.getSpanEnd(arrclickableSpan[n7]);
                    if (n4 >= n3 && n2 != n3) continue;
                    if (n4 <= n6) continue;
                    n5 = object.getSpanStart(arrclickableSpan[n7]);
                    n6 = n4;
                }
                if (n5 >= 0) {
                    Selection.setSelection((Spannable)object, n6, n5);
                    return true;
                }
                break block19;
            }
            if (n2 == n3) {
                return false;
            }
            if (((ClickableSpan[])(object = object.getSpans(n2, n3, ClickableSpan.class))).length != 1) {
                return false;
            }
            if ((object = object[0]) instanceof TextLinks.TextLinkSpan) {
                ((TextLinks.TextLinkSpan)object).onClick(textView, 1);
            } else {
                ((ClickableSpan)object).onClick(textView);
            }
        }
        return false;
    }

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new LinkMovementMethod();
        }
        return sInstance;
    }

    @Override
    public boolean canSelectArbitrarily() {
        return true;
    }

    @Override
    protected boolean down(TextView textView, Spannable spannable) {
        if (this.action(3, textView, spannable)) {
            return true;
        }
        return super.down(textView, spannable);
    }

    @Override
    protected boolean handleMovementKey(TextView textView, Spannable spannable, int n, int n2, KeyEvent keyEvent) {
        if ((n == 23 || n == 66) && KeyEvent.metaStateHasNoModifiers(n2) && keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0 && this.action(1, textView, spannable)) {
            return true;
        }
        return super.handleMovementKey(textView, spannable, n, n2, keyEvent);
    }

    @Override
    public void initialize(TextView textView, Spannable spannable) {
        Selection.removeSelection(spannable);
        spannable.removeSpan(FROM_BELOW);
    }

    @Override
    protected boolean left(TextView textView, Spannable spannable) {
        if (this.action(2, textView, spannable)) {
            return true;
        }
        return super.left(textView, spannable);
    }

    @Override
    public void onTakeFocus(TextView textView, Spannable spannable, int n) {
        Selection.removeSelection(spannable);
        if ((n & 1) != 0) {
            spannable.setSpan(FROM_BELOW, 0, 0, 34);
        } else {
            spannable.removeSpan(FROM_BELOW);
        }
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent object) {
        int n = ((MotionEvent)object).getAction();
        if (n == 1 || n == 0) {
            int n2 = (int)((MotionEvent)object).getX();
            int n3 = (int)((MotionEvent)object).getY();
            int n4 = textView.getTotalPaddingLeft();
            int n5 = textView.getTotalPaddingTop();
            int n6 = textView.getScrollX();
            int n7 = textView.getScrollY();
            ClickableSpan[] arrclickableSpan = textView.getLayout();
            n6 = arrclickableSpan.getOffsetForHorizontal(arrclickableSpan.getLineForVertical(n3 - n5 + n7), n2 - n4 + n6);
            arrclickableSpan = spannable.getSpans(n6, n6, ClickableSpan.class);
            if (arrclickableSpan.length != 0) {
                object = arrclickableSpan[0];
                if (n == 1) {
                    if (object instanceof TextLinks.TextLinkSpan) {
                        ((TextLinks.TextLinkSpan)object).onClick(textView, 0);
                    } else {
                        ((ClickableSpan)object).onClick(textView);
                    }
                } else if (n == 0) {
                    if (textView.getContext().getApplicationInfo().targetSdkVersion >= 28) {
                        textView.hideFloatingToolbar(200);
                    }
                    Selection.setSelection(spannable, spannable.getSpanStart(object), spannable.getSpanEnd(object));
                }
                return true;
            }
            Selection.removeSelection(spannable);
        }
        return super.onTouchEvent(textView, spannable, (MotionEvent)object);
    }

    @Override
    protected boolean right(TextView textView, Spannable spannable) {
        if (this.action(3, textView, spannable)) {
            return true;
        }
        return super.right(textView, spannable);
    }

    @Override
    protected boolean up(TextView textView, Spannable spannable) {
        if (this.action(2, textView, spannable)) {
            return true;
        }
        return super.up(textView, spannable);
    }
}

