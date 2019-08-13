/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.content.Context;
import android.text.Layout;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.method.MetaKeyKeyListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

public class Touch {
    private Touch() {
    }

    public static int getInitialScrollX(TextView arrdragState, Spannable spannable) {
        arrdragState = spannable.getSpans(0, spannable.length(), DragState.class);
        int n = arrdragState.length > 0 ? arrdragState[0].mScrollX : -1;
        return n;
    }

    public static int getInitialScrollY(TextView arrdragState, Spannable spannable) {
        arrdragState = spannable.getSpans(0, spannable.length(), DragState.class);
        int n = arrdragState.length > 0 ? arrdragState[0].mScrollY : -1;
        return n;
    }

    public static boolean onTouchEvent(TextView arrdragState, Spannable object, MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n != 0) {
            if (n != 1) {
                DragState[] arrdragState2;
                if (n == 2 && (arrdragState2 = object.getSpans(0, object.length(), DragState.class)).length > 0) {
                    if (!arrdragState2[0].mFarEnough) {
                        n = ViewConfiguration.get(arrdragState.getContext()).getScaledTouchSlop();
                        if (Math.abs(motionEvent.getX() - arrdragState2[0].mX) >= (float)n || Math.abs(motionEvent.getY() - arrdragState2[0].mY) >= (float)n) {
                            arrdragState2[0].mFarEnough = true;
                        }
                    }
                    if (arrdragState2[0].mFarEnough) {
                        float f;
                        float f2;
                        arrdragState2[0].mUsed = true;
                        n = (motionEvent.getMetaState() & 1) == 0 && MetaKeyKeyListener.getMetaState((CharSequence)object, 1) != 1 && MetaKeyKeyListener.getMetaState((CharSequence)object, 2048) == 0 ? 0 : 1;
                        if (n != 0) {
                            f2 = motionEvent.getX() - arrdragState2[0].mX;
                            f = motionEvent.getY() - arrdragState2[0].mY;
                        } else {
                            f2 = arrdragState2[0].mX - motionEvent.getX();
                            f = arrdragState2[0].mY - motionEvent.getY();
                        }
                        arrdragState2[0].mX = motionEvent.getX();
                        arrdragState2[0].mY = motionEvent.getY();
                        n = arrdragState.getScrollX();
                        int n2 = (int)f2;
                        int n3 = arrdragState.getScrollY();
                        int n4 = (int)f;
                        int n5 = arrdragState.getTotalPaddingTop();
                        int n6 = arrdragState.getTotalPaddingBottom();
                        object = arrdragState.getLayout();
                        n3 = Math.max(Math.min(n3 + n4, ((Layout)object).getHeight() - (arrdragState.getHeight() - (n5 + n6))), 0);
                        n4 = arrdragState.getScrollX();
                        n6 = arrdragState.getScrollY();
                        Touch.scrollTo((TextView)arrdragState, (Layout)object, n + n2, n3);
                        if (n4 != arrdragState.getScrollX() || n6 != arrdragState.getScrollY()) {
                            arrdragState.cancelLongPress();
                        }
                        return true;
                    }
                }
                return false;
            }
            arrdragState = object.getSpans(0, object.length(), DragState.class);
            for (n = 0; n < arrdragState.length; ++n) {
                object.removeSpan(arrdragState[n]);
            }
            return arrdragState.length > 0 && arrdragState[0].mUsed;
        }
        DragState[] arrdragState3 = object.getSpans(0, object.length(), DragState.class);
        for (n = 0; n < arrdragState3.length; ++n) {
            object.removeSpan(arrdragState3[n]);
        }
        object.setSpan(new DragState(motionEvent.getX(), motionEvent.getY(), arrdragState.getScrollX(), arrdragState.getScrollY()), 0, 0, 17);
        return true;
    }

    public static void scrollTo(TextView textView, Layout layout2, int n, int n2) {
        int n3;
        int n4 = textView.getTotalPaddingLeft();
        int n5 = textView.getTotalPaddingRight();
        int n6 = textView.getWidth() - (n4 + n5);
        Layout.Alignment alignment = layout2.getParagraphAlignment(n3);
        boolean bl = layout2.getParagraphDirection(n3) > 0;
        if (textView.getHorizontallyScrolling()) {
            n5 = textView.getTotalPaddingTop();
            n4 = textView.getTotalPaddingBottom();
            int n7 = layout2.getLineForVertical(textView.getHeight() + n2 - (n5 + n4));
            n4 = Integer.MAX_VALUE;
            n5 = 0;
            for (n3 = layout2.getLineForVertical((int)n2); n3 <= n7; ++n3) {
                n4 = (int)Math.min((float)n4, layout2.getLineLeft(n3));
                n5 = (int)Math.max((float)n5, layout2.getLineRight(n3));
            }
        } else {
            n4 = 0;
            n5 = n6;
        }
        n = (n3 = n5 - n4) < n6 ? (alignment == Layout.Alignment.ALIGN_CENTER ? n4 - (n6 - n3) / 2 : (bl && alignment == Layout.Alignment.ALIGN_OPPOSITE || !bl && alignment == Layout.Alignment.ALIGN_NORMAL || alignment == Layout.Alignment.ALIGN_RIGHT ? n4 - (n6 - n3) : n4)) : Math.max(Math.min(n, n5 - n6), n4);
        textView.scrollTo(n, n2);
    }

    private static class DragState
    implements NoCopySpan {
        public boolean mFarEnough;
        public int mScrollX;
        public int mScrollY;
        public boolean mUsed;
        public float mX;
        public float mY;

        public DragState(float f, float f2, int n, int n2) {
            this.mX = f;
            this.mY = f2;
            this.mScrollX = n;
            this.mScrollY = n2;
        }
    }

}

