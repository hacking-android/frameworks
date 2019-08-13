/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

class RoundScrollbarRenderer {
    private static final int DEFAULT_THUMB_COLOR = -1512723;
    private static final int DEFAULT_TRACK_COLOR = 1291845631;
    private static final int MAX_SCROLLBAR_ANGLE_SWIPE = 16;
    private static final int MIN_SCROLLBAR_ANGLE_SWIPE = 6;
    private static final int SCROLLBAR_ANGLE_RANGE = 90;
    private static final float WIDTH_PERCENTAGE = 0.02f;
    private final int mMaskThickness;
    private final View mParent;
    private final RectF mRect = new RectF();
    private final Paint mThumbPaint = new Paint();
    private final Paint mTrackPaint = new Paint();

    public RoundScrollbarRenderer(View view) {
        this.mThumbPaint.setAntiAlias(true);
        this.mThumbPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mThumbPaint.setStyle(Paint.Style.STROKE);
        this.mTrackPaint.setAntiAlias(true);
        this.mTrackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mTrackPaint.setStyle(Paint.Style.STROKE);
        this.mParent = view;
        this.mMaskThickness = view.getContext().getResources().getDimensionPixelSize(17105046);
    }

    private static int applyAlpha(int n, float f) {
        return Color.argb((int)((float)Color.alpha(n) * f), Color.red(n), Color.green(n), Color.blue(n));
    }

    private static float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    private void setThumbColor(int n) {
        if (this.mThumbPaint.getColor() != n) {
            this.mThumbPaint.setColor(n);
        }
    }

    private void setTrackColor(int n) {
        if (this.mTrackPaint.getColor() != n) {
            this.mTrackPaint.setColor(n);
        }
    }

    public void drawRoundScrollbars(Canvas canvas, float f, Rect rect) {
        if (f == 0.0f) {
            return;
        }
        float f2 = this.mParent.computeVerticalScrollRange();
        float f3 = this.mParent.computeVerticalScrollExtent();
        if (!(f3 <= 0.0f) && !(f2 <= f3)) {
            float f4 = Math.max(0, this.mParent.computeVerticalScrollOffset());
            float f5 = this.mParent.computeVerticalScrollExtent();
            f3 = (float)this.mParent.getWidth() * 0.02f;
            this.mThumbPaint.setStrokeWidth(f3);
            this.mTrackPaint.setStrokeWidth(f3);
            this.setThumbColor(RoundScrollbarRenderer.applyAlpha(-1512723, f));
            this.setTrackColor(RoundScrollbarRenderer.applyAlpha(1291845631, f));
            f = RoundScrollbarRenderer.clamp(f5 / f2 * 90.0f, 6.0f, 16.0f);
            f2 = RoundScrollbarRenderer.clamp((90.0f - f) * f4 / (f2 - f5) - 45.0f, -45.0f, 45.0f - f);
            f3 = f3 / 2.0f + (float)this.mMaskThickness;
            this.mRect.set((float)rect.left + f3, (float)rect.top + f3, (float)rect.right - f3, (float)rect.bottom - f3);
            canvas.drawArc(this.mRect, -45.0f, 90.0f, false, this.mTrackPaint);
            canvas.drawArc(this.mRect, f2, f, false, this.mThumbPaint);
            return;
        }
    }
}

