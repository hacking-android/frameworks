/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

@Deprecated
public class ZoomButton
extends ImageButton
implements View.OnLongClickListener {
    private boolean mIsInLongpress;
    private final Runnable mRunnable = new Runnable(){

        @Override
        public void run() {
            if (ZoomButton.this.hasOnClickListeners() && ZoomButton.this.mIsInLongpress && ZoomButton.this.isEnabled()) {
                ZoomButton.this.callOnClick();
                ZoomButton zoomButton = ZoomButton.this;
                zoomButton.postDelayed(this, zoomButton.mZoomSpeed);
            }
        }
    };
    private long mZoomSpeed = 1000L;

    public ZoomButton(Context context) {
        this(context, null);
    }

    public ZoomButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZoomButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ZoomButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.setOnLongClickListener(this);
    }

    @Override
    public boolean dispatchUnhandledMove(View view, int n) {
        this.clearFocus();
        return super.dispatchUnhandledMove(view, n);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ZoomButton.class.getName();
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        this.mIsInLongpress = false;
        return super.onKeyUp(n, keyEvent);
    }

    @Override
    public boolean onLongClick(View view) {
        this.mIsInLongpress = true;
        this.post(this.mRunnable);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.mIsInLongpress = false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void setEnabled(boolean bl) {
        if (!bl) {
            this.setPressed(false);
        }
        super.setEnabled(bl);
    }

    public void setZoomSpeed(long l) {
        this.mZoomSpeed = l;
    }

}

