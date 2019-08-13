/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.Button;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class EmphasizedNotificationButton
extends Button {
    private final RippleDrawable mRipple = (RippleDrawable)((DrawableWrapper)this.getBackground().mutate()).getDrawable();
    private final int mStrokeColor = this.getContext().getColor(17170870);
    private final int mStrokeWidth = this.getResources().getDimensionPixelSize(17105150);

    public EmphasizedNotificationButton(Context context) {
        this(context, null);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mRipple.mutate();
    }

    @RemotableViewMethod
    public void setButtonBackground(ColorStateList colorStateList) {
        ((GradientDrawable)this.mRipple.getDrawable(0)).setColor(colorStateList);
        this.invalidate();
    }

    @RemotableViewMethod
    public void setHasStroke(boolean bl) {
        Drawable drawable2 = this.mRipple;
        int n = 0;
        drawable2 = (GradientDrawable)((LayerDrawable)drawable2).getDrawable(0);
        if (bl) {
            n = this.mStrokeWidth;
        }
        ((GradientDrawable)drawable2).setStroke(n, this.mStrokeColor);
        this.invalidate();
    }

    @RemotableViewMethod
    public void setRippleColor(ColorStateList colorStateList) {
        this.mRipple.setColor(colorStateList);
        this.invalidate();
    }
}

