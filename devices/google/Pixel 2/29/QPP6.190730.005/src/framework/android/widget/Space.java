/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public final class Space
extends View {
    public Space(Context context) {
        this(context, null);
    }

    public Space(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Space(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Space(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        if (this.getVisibility() == 0) {
            this.setVisibility(4);
        }
    }

    private static int getDefaultSize2(int n, int n2) {
        int n3 = n;
        int n4 = View.MeasureSpec.getMode(n2);
        n2 = View.MeasureSpec.getSize(n2);
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 0) {
                n = n4 != 1073741824 ? n3 : n2;
            }
        } else {
            n = Math.min(n, n2);
        }
        return n;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(Space.getDefaultSize2(this.getSuggestedMinimumWidth(), n), Space.getDefaultSize2(this.getSuggestedMinimumHeight(), n2));
    }
}

