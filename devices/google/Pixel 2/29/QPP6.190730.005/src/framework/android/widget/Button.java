/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.widget.RemoteViews;
import android.widget.TextView;

@RemoteViews.RemoteView
public class Button
extends TextView {
    public Button(Context context) {
        this(context, null);
    }

    public Button(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842824);
    }

    public Button(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Button(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (this.getPointerIcon() == null && this.isClickable() && this.isEnabled()) {
            return PointerIcon.getSystemIcon(this.getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }
}

