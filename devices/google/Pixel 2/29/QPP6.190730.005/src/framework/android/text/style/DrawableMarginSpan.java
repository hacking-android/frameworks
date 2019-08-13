/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;

public class DrawableMarginSpan
implements LeadingMarginSpan,
LineHeightSpan {
    private static final int STANDARD_PAD_WIDTH = 0;
    private final Drawable mDrawable;
    private final int mPad;

    public DrawableMarginSpan(Drawable drawable2) {
        this(drawable2, 0);
    }

    public DrawableMarginSpan(Drawable drawable2, int n) {
        this.mDrawable = drawable2;
        this.mPad = n;
    }

    @Override
    public void chooseHeight(CharSequence charSequence, int n, int n2, int n3, int n4, Paint.FontMetricsInt fontMetricsInt) {
        if (n2 == ((Spanned)charSequence).getSpanEnd(this)) {
            n = this.mDrawable.getIntrinsicHeight();
            n2 = n - (fontMetricsInt.descent + n4 - fontMetricsInt.ascent - n3);
            if (n2 > 0) {
                fontMetricsInt.descent += n2;
            }
            if ((n -= fontMetricsInt.bottom + n4 - fontMetricsInt.top - n3) > 0) {
                fontMetricsInt.bottom += n;
            }
        }
    }

    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint, int n, int n2, int n3, int n4, int n5, CharSequence charSequence, int n6, int n7, boolean bl, Layout layout2) {
        n3 = layout2.getLineTop(layout2.getLineForOffset(((Spanned)charSequence).getSpanStart(this)));
        n4 = this.mDrawable.getIntrinsicWidth();
        n2 = this.mDrawable.getIntrinsicHeight();
        this.mDrawable.setBounds(n, n3, n + n4, n3 + n2);
        this.mDrawable.draw(canvas);
    }

    @Override
    public int getLeadingMargin(boolean bl) {
        return this.mDrawable.getIntrinsicWidth() + this.mPad;
    }
}

