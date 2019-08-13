/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;

public class IconMarginSpan
implements LeadingMarginSpan,
LineHeightSpan {
    private final Bitmap mBitmap;
    private final int mPad;

    public IconMarginSpan(Bitmap bitmap) {
        this(bitmap, 0);
    }

    public IconMarginSpan(Bitmap bitmap, int n) {
        this.mBitmap = bitmap;
        this.mPad = n;
    }

    @Override
    public void chooseHeight(CharSequence charSequence, int n, int n2, int n3, int n4, Paint.FontMetricsInt fontMetricsInt) {
        if (n2 == ((Spanned)charSequence).getSpanEnd(this)) {
            n = this.mBitmap.getHeight();
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
        if (n2 < 0) {
            n -= this.mBitmap.getWidth();
        }
        canvas.drawBitmap(this.mBitmap, n, n3, paint);
    }

    @Override
    public int getLeadingMargin(boolean bl) {
        return this.mBitmap.getWidth() + this.mPad;
    }
}

