/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextLine;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ParagraphStyle;

public class BoringLayout
extends Layout
implements TextUtils.EllipsizeCallback {
    int mBottom;
    private int mBottomPadding;
    int mDesc;
    private String mDirect;
    private int mEllipsizedCount;
    private int mEllipsizedStart;
    private int mEllipsizedWidth;
    private float mMax;
    private Paint mPaint;
    private int mTopPadding;

    public BoringLayout(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean bl) {
        super(charSequence, textPaint, n, alignment, f, f2);
        this.mEllipsizedWidth = n;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.init(charSequence, textPaint, alignment, metrics, bl, true);
    }

    public BoringLayout(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean bl, TextUtils.TruncateAt truncateAt, int n2) {
        super(charSequence, textPaint, n, alignment, f, f2);
        boolean bl2;
        if (truncateAt != null && truncateAt != TextUtils.TruncateAt.MARQUEE) {
            this.replaceWith(TextUtils.ellipsize(charSequence, textPaint, n2, truncateAt, true, this), textPaint, n, alignment, f, f2);
            this.mEllipsizedWidth = n2;
            bl2 = false;
        } else {
            this.mEllipsizedWidth = n;
            this.mEllipsizedStart = 0;
            this.mEllipsizedCount = 0;
            bl2 = true;
        }
        this.init(this.getText(), textPaint, alignment, metrics, bl, bl2);
    }

    private static boolean hasAnyInterestingChars(CharSequence charSequence, int n) {
        char[] arrc = TextUtils.obtain(500);
        for (int i = 0; i < n; i += 500) {
            int n2;
            try {
                n2 = Math.min(i + 500, n);
                TextUtils.getChars(charSequence, i, n2, arrc, 0);
            }
            catch (Throwable throwable) {
                TextUtils.recycle(arrc);
                throw throwable;
            }
            for (int j = 0; j < n2 - i; ++j) {
                block6 : {
                    char c = arrc[j];
                    if (c != '\n' && c != '\t') {
                        boolean bl = TextUtils.couldAffectRtl(c);
                        if (bl) break block6;
                        continue;
                    }
                }
                TextUtils.recycle(arrc);
                return true;
            }
            continue;
        }
        TextUtils.recycle(arrc);
        return false;
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint) {
        return BoringLayout.isBoring(charSequence, textPaint, TextDirectionHeuristics.FIRSTSTRONG_LTR, null);
    }

    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint, Metrics metrics) {
        return BoringLayout.isBoring(charSequence, textPaint, TextDirectionHeuristics.FIRSTSTRONG_LTR, metrics);
    }

    @UnsupportedAppUsage
    public static Metrics isBoring(CharSequence charSequence, TextPaint textPaint, TextDirectionHeuristic object, Metrics object2) {
        int n = charSequence.length();
        if (BoringLayout.hasAnyInterestingChars(charSequence, n)) {
            return null;
        }
        if (object != null && object.isRtl(charSequence, 0, n)) {
            return null;
        }
        if (charSequence instanceof Spanned && ((Spanned)charSequence).getSpans(0, n, ParagraphStyle.class).length > 0) {
            return null;
        }
        if (object2 == null) {
            object = new Metrics();
        } else {
            ((Metrics)object2).reset();
            object = object2;
        }
        object2 = TextLine.obtain();
        ((TextLine)object2).set(textPaint, charSequence, 0, n, 1, Layout.DIRS_ALL_LEFT_TO_RIGHT, false, null, 0, 0);
        ((Metrics)object).width = (int)Math.ceil(((TextLine)object2).metrics((Paint.FontMetricsInt)object));
        TextLine.recycle((TextLine)object2);
        return object;
    }

    public static BoringLayout make(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean bl) {
        return new BoringLayout(charSequence, textPaint, n, alignment, f, f2, metrics, bl);
    }

    public static BoringLayout make(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean bl, TextUtils.TruncateAt truncateAt, int n2) {
        return new BoringLayout(charSequence, textPaint, n, alignment, f, f2, metrics, bl, truncateAt, n2);
    }

    @Override
    public void draw(Canvas canvas, Path path, Paint paint, int n) {
        String string2 = this.mDirect;
        if (string2 != null && path == null) {
            canvas.drawText(string2, 0.0f, this.mBottom - this.mDesc, this.mPaint);
        } else {
            super.draw(canvas, path, paint, n);
        }
    }

    @Override
    public void ellipsized(int n, int n2) {
        this.mEllipsizedStart = n;
        this.mEllipsizedCount = n2 - n;
    }

    @Override
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    @Override
    public int getEllipsisCount(int n) {
        return this.mEllipsizedCount;
    }

    @Override
    public int getEllipsisStart(int n) {
        return this.mEllipsizedStart;
    }

    @Override
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @Override
    public int getHeight() {
        return this.mBottom;
    }

    @Override
    public boolean getLineContainsTab(int n) {
        return false;
    }

    @Override
    public int getLineCount() {
        return 1;
    }

    @Override
    public int getLineDescent(int n) {
        return this.mDesc;
    }

    @Override
    public final Layout.Directions getLineDirections(int n) {
        return Layout.DIRS_ALL_LEFT_TO_RIGHT;
    }

    @Override
    public float getLineMax(int n) {
        return this.mMax;
    }

    @Override
    public int getLineStart(int n) {
        if (n == 0) {
            return 0;
        }
        return this.getText().length();
    }

    @Override
    public int getLineTop(int n) {
        if (n == 0) {
            return 0;
        }
        return this.mBottom;
    }

    @Override
    public float getLineWidth(int n) {
        float f = n == 0 ? this.mMax : 0.0f;
        return f;
    }

    @Override
    public int getParagraphDirection(int n) {
        return 1;
    }

    @Override
    public int getTopPadding() {
        return this.mTopPadding;
    }

    void init(CharSequence charSequence, TextPaint textPaint, Layout.Alignment object, Metrics metrics, boolean bl, boolean bl2) {
        int n;
        int n2;
        this.mDirect = charSequence instanceof String && object == Layout.Alignment.ALIGN_NORMAL ? charSequence.toString() : null;
        this.mPaint = textPaint;
        if (bl) {
            n2 = metrics.bottom;
            n = metrics.top;
            this.mDesc = metrics.bottom;
            n2 -= n;
        } else {
            n = metrics.descent;
            n2 = metrics.ascent;
            this.mDesc = metrics.descent;
            n2 = n - n2;
        }
        this.mBottom = n2;
        if (bl2) {
            this.mMax = metrics.width;
        } else {
            TextLine textLine = TextLine.obtain();
            n2 = charSequence.length();
            object = Layout.DIRS_ALL_LEFT_TO_RIGHT;
            n = this.mEllipsizedStart;
            textLine.set(textPaint, charSequence, 0, n2, 1, (Layout.Directions)object, false, null, n, n + this.mEllipsizedCount);
            this.mMax = (int)Math.ceil(textLine.metrics(null));
            TextLine.recycle(textLine);
        }
        if (bl) {
            this.mTopPadding = metrics.top - metrics.ascent;
            this.mBottomPadding = metrics.bottom - metrics.descent;
        }
    }

    public BoringLayout replaceOrMake(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean bl) {
        this.replaceWith(charSequence, textPaint, n, alignment, f, f2);
        this.mEllipsizedWidth = n;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.init(charSequence, textPaint, alignment, metrics, bl, true);
        return this;
    }

    public BoringLayout replaceOrMake(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, Metrics metrics, boolean bl, TextUtils.TruncateAt truncateAt, int n2) {
        boolean bl2;
        if (truncateAt != null && truncateAt != TextUtils.TruncateAt.MARQUEE) {
            this.replaceWith(TextUtils.ellipsize(charSequence, textPaint, n2, truncateAt, true, this), textPaint, n, alignment, f, f2);
            this.mEllipsizedWidth = n2;
            bl2 = false;
        } else {
            this.replaceWith(charSequence, textPaint, n, alignment, f, f2);
            this.mEllipsizedWidth = n;
            this.mEllipsizedStart = 0;
            this.mEllipsizedCount = 0;
            bl2 = true;
        }
        this.init(this.getText(), textPaint, alignment, metrics, bl, bl2);
        return this;
    }

    public static class Metrics
    extends Paint.FontMetricsInt {
        public int width;

        private void reset() {
            this.top = 0;
            this.bottom = 0;
            this.ascent = 0;
            this.descent = 0;
            this.width = 0;
            this.leading = 0;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append(" width=");
            stringBuilder.append(this.width);
            return stringBuilder.toString();
        }
    }

}

