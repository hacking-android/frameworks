/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ZoomButton;

@Deprecated
public class ZoomControls
extends LinearLayout {
    @UnsupportedAppUsage
    private final ZoomButton mZoomIn;
    @UnsupportedAppUsage
    private final ZoomButton mZoomOut;

    public ZoomControls(Context context) {
        this(context, null);
    }

    public ZoomControls(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setFocusable(false);
        ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(17367344, (ViewGroup)this, true);
        this.mZoomIn = (ZoomButton)this.findViewById(16909576);
        this.mZoomOut = (ZoomButton)this.findViewById(16909578);
    }

    private void fade(int n, float f, float f2) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setDuration(500L);
        this.startAnimation(alphaAnimation);
        this.setVisibility(n);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ZoomControls.class.getName();
    }

    @Override
    public boolean hasFocus() {
        boolean bl = this.mZoomIn.hasFocus() || this.mZoomOut.hasFocus();
        return bl;
    }

    public void hide() {
        this.fade(8, 1.0f, 0.0f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setIsZoomInEnabled(boolean bl) {
        this.mZoomIn.setEnabled(bl);
    }

    public void setIsZoomOutEnabled(boolean bl) {
        this.mZoomOut.setEnabled(bl);
    }

    public void setOnZoomInClickListener(View.OnClickListener onClickListener) {
        this.mZoomIn.setOnClickListener(onClickListener);
    }

    public void setOnZoomOutClickListener(View.OnClickListener onClickListener) {
        this.mZoomOut.setOnClickListener(onClickListener);
    }

    public void setZoomSpeed(long l) {
        this.mZoomIn.setZoomSpeed(l);
        this.mZoomOut.setZoomSpeed(l);
    }

    public void show() {
        this.fade(0, 0.0f, 1.0f);
    }
}

