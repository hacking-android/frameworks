/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.widget.ImageView;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class ImageButton
extends ImageView {
    public ImageButton(Context context) {
        this(context, null);
    }

    public ImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842866);
    }

    public ImageButton(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ImageButton(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.setFocusable(true);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ImageButton.class.getName();
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (this.getPointerIcon() == null && this.isClickable() && this.isEnabled()) {
            return PointerIcon.getSystemIcon(this.getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }

    @Override
    protected boolean onSetAlpha(int n) {
        return false;
    }
}

