/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.DropDownListView;
import android.widget.ListView;
import com.android.internal.view.menu.ShowableListMenu;

public abstract class ForwardingListener
implements View.OnTouchListener,
View.OnAttachStateChangeListener {
    private int mActivePointerId;
    private Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    private final View mSrc;
    private final int mTapTimeout;
    private Runnable mTriggerLongPress;

    public ForwardingListener(View view) {
        this.mSrc = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.mScaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.mTapTimeout = ViewConfiguration.getTapTimeout();
        this.mLongPressTimeout = (this.mTapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    private void clearCallbacks() {
        Runnable runnable = this.mTriggerLongPress;
        if (runnable != null) {
            this.mSrc.removeCallbacks(runnable);
        }
        if ((runnable = this.mDisallowIntercept) != null) {
            this.mSrc.removeCallbacks(runnable);
        }
    }

    private void onLongPress() {
        this.clearCallbacks();
        View view = this.mSrc;
        if (view.isEnabled() && !view.isLongClickable()) {
            if (!this.onForwardingStarted()) {
                return;
            }
            view.getParent().requestDisallowInterceptTouchEvent(true);
            long l = SystemClock.uptimeMillis();
            MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0);
            view.onTouchEvent(motionEvent);
            motionEvent.recycle();
            this.mForwarding = true;
            return;
        }
    }

    private boolean onTouchForwarded(MotionEvent motionEvent) {
        View view = this.mSrc;
        Object object = this.getPopup();
        boolean bl = false;
        if (object != null && object.isShowing()) {
            DropDownListView dropDownListView = (DropDownListView)object.getListView();
            if (dropDownListView != null && dropDownListView.isShown()) {
                object = MotionEvent.obtainNoHistory(motionEvent);
                view.toGlobalMotionEvent((MotionEvent)object);
                dropDownListView.toLocalMotionEvent((MotionEvent)object);
                boolean bl2 = dropDownListView.onForwardedEvent((MotionEvent)object, this.mActivePointerId);
                ((MotionEvent)object).recycle();
                int n = motionEvent.getActionMasked();
                n = n != 1 && n != 3 ? 1 : 0;
                boolean bl3 = bl;
                if (bl2) {
                    bl3 = bl;
                    if (n != 0) {
                        bl3 = true;
                    }
                }
                return bl3;
            }
            return false;
        }
        return false;
    }

    private boolean onTouchObserved(MotionEvent motionEvent) {
        block7 : {
            View view;
            block4 : {
                block5 : {
                    int n;
                    block6 : {
                        view = this.mSrc;
                        if (!view.isEnabled()) {
                            return false;
                        }
                        n = motionEvent.getActionMasked();
                        if (n == 0) break block4;
                        if (n == 1) break block5;
                        if (n == 2) break block6;
                        if (n == 3) break block5;
                        break block7;
                    }
                    n = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (n >= 0 && !view.pointInView(motionEvent.getX(n), motionEvent.getY(n), this.mScaledTouchSlop)) {
                        this.clearCallbacks();
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                    break block7;
                }
                this.clearCallbacks();
                break block7;
            }
            this.mActivePointerId = motionEvent.getPointerId(0);
            if (this.mDisallowIntercept == null) {
                this.mDisallowIntercept = new DisallowIntercept();
            }
            view.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
            if (this.mTriggerLongPress == null) {
                this.mTriggerLongPress = new TriggerLongPress();
            }
            view.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
        }
        return false;
    }

    public abstract ShowableListMenu getPopup();

    protected boolean onForwardingStarted() {
        ShowableListMenu showableListMenu = this.getPopup();
        if (showableListMenu != null && !showableListMenu.isShowing()) {
            showableListMenu.show();
        }
        return true;
    }

    protected boolean onForwardingStopped() {
        ShowableListMenu showableListMenu = this.getPopup();
        if (showableListMenu != null && showableListMenu.isShowing()) {
            showableListMenu.dismiss();
        }
        return true;
    }

    @Override
    public boolean onTouch(View object, MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        boolean bl3 = this.mForwarding;
        boolean bl4 = true;
        if (bl3) {
            bl2 = this.onTouchForwarded(motionEvent) || !this.onForwardingStopped();
            bl = bl2;
        } else {
            bl2 = this.onTouchObserved(motionEvent) && this.onForwardingStarted();
            bl = bl2;
            if (bl2) {
                long l = SystemClock.uptimeMillis();
                object = MotionEvent.obtain(l, l, 3, 0.0f, 0.0f, 0);
                this.mSrc.onTouchEvent((MotionEvent)object);
                ((MotionEvent)object).recycle();
                bl = bl2;
            }
        }
        this.mForwarding = bl;
        bl2 = bl4;
        if (!bl) {
            bl2 = bl3 ? bl4 : false;
        }
        return bl2;
    }

    @Override
    public void onViewAttachedToWindow(View view) {
    }

    @Override
    public void onViewDetachedFromWindow(View object) {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        object = this.mDisallowIntercept;
        if (object != null) {
            this.mSrc.removeCallbacks((Runnable)object);
        }
    }

    private class DisallowIntercept
    implements Runnable {
        private DisallowIntercept() {
        }

        @Override
        public void run() {
            ViewParent viewParent = ForwardingListener.this.mSrc.getParent();
            if (viewParent != null) {
                viewParent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    private class TriggerLongPress
    implements Runnable {
        private TriggerLongPress() {
        }

        @Override
        public void run() {
            ForwardingListener.this.onLongPress();
        }
    }

}

