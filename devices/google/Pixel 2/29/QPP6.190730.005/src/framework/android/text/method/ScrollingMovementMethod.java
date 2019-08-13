/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.BaseMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

public class ScrollingMovementMethod
extends BaseMovementMethod
implements MovementMethod {
    private static ScrollingMovementMethod sInstance;

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ScrollingMovementMethod();
        }
        return sInstance;
    }

    @Override
    protected boolean bottom(TextView textView, Spannable spannable) {
        return this.scrollBottom(textView, spannable);
    }

    @Override
    protected boolean down(TextView textView, Spannable spannable) {
        return this.scrollDown(textView, spannable, 1);
    }

    @Override
    protected boolean end(TextView textView, Spannable spannable) {
        return this.bottom(textView, spannable);
    }

    @Override
    protected boolean home(TextView textView, Spannable spannable) {
        return this.top(textView, spannable);
    }

    @Override
    protected boolean left(TextView textView, Spannable spannable) {
        return this.scrollLeft(textView, spannable, 1);
    }

    @Override
    protected boolean lineEnd(TextView textView, Spannable spannable) {
        return this.scrollLineEnd(textView, spannable);
    }

    @Override
    protected boolean lineStart(TextView textView, Spannable spannable) {
        return this.scrollLineStart(textView, spannable);
    }

    @Override
    public void onTakeFocus(TextView textView, Spannable object, int n) {
        object = textView.getLayout();
        if (object != null && (n & 2) != 0) {
            textView.scrollTo(textView.getScrollX(), ((Layout)object).getLineTop(0));
        }
        if (object != null && (n & 1) != 0) {
            int n2 = textView.getTotalPaddingTop();
            n = textView.getTotalPaddingBottom();
            int n3 = ((Layout)object).getLineCount();
            textView.scrollTo(textView.getScrollX(), ((Layout)object).getLineTop(n3 - 1 + 1) - (textView.getHeight() - (n2 + n)));
        }
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        return Touch.onTouchEvent(textView, spannable, motionEvent);
    }

    @Override
    protected boolean pageDown(TextView textView, Spannable spannable) {
        return this.scrollPageDown(textView, spannable);
    }

    @Override
    protected boolean pageUp(TextView textView, Spannable spannable) {
        return this.scrollPageUp(textView, spannable);
    }

    @Override
    protected boolean right(TextView textView, Spannable spannable) {
        return this.scrollRight(textView, spannable, 1);
    }

    @Override
    protected boolean top(TextView textView, Spannable spannable) {
        return this.scrollTop(textView, spannable);
    }

    @Override
    protected boolean up(TextView textView, Spannable spannable) {
        return this.scrollUp(textView, spannable, 1);
    }
}

