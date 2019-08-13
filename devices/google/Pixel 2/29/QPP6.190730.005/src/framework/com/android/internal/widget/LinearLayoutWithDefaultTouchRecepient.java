/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class LinearLayoutWithDefaultTouchRecepient
extends LinearLayout {
    private View mDefaultTouchRecepient;
    private final Rect mTempRect = new Rect();

    @UnsupportedAppUsage
    public LinearLayoutWithDefaultTouchRecepient(Context context) {
        super(context);
    }

    public LinearLayoutWithDefaultTouchRecepient(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mDefaultTouchRecepient == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (super.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        this.mTempRect.set(0, 0, 0, 0);
        this.offsetRectIntoDescendantCoords(this.mDefaultTouchRecepient, this.mTempRect);
        motionEvent.setLocation(motionEvent.getX() + (float)this.mTempRect.left, motionEvent.getY() + (float)this.mTempRect.top);
        return this.mDefaultTouchRecepient.dispatchTouchEvent(motionEvent);
    }

    @UnsupportedAppUsage
    public void setDefaultTouchRecepient(View view) {
        this.mDefaultTouchRecepient = view;
    }
}

