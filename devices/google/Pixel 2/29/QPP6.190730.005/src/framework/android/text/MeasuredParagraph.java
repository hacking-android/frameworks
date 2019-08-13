/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.text.MeasuredText;
import android.text.AndroidBidi;
import android.text.AutoGrowArray;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import android.util.Pools;
import java.util.Arrays;

public class MeasuredParagraph {
    private static final char OBJECT_REPLACEMENT_CHARACTER = '\ufffc';
    private static final Pools.SynchronizedPool<MeasuredParagraph> sPool = new Pools.SynchronizedPool(1);
    private Paint.FontMetricsInt mCachedFm;
    private TextPaint mCachedPaint = new TextPaint();
    private char[] mCopiedBuffer;
    private AutoGrowArray.IntArray mFontMetrics = new AutoGrowArray.IntArray(16);
    private AutoGrowArray.ByteArray mLevels = new AutoGrowArray.ByteArray();
    private boolean mLtrWithoutBidi;
    private MeasuredText mMeasuredText;
    private int mParaDir;
    private AutoGrowArray.IntArray mSpanEndCache = new AutoGrowArray.IntArray(4);
    private Spanned mSpanned;
    private int mTextLength;
    private int mTextStart;
    private float mWholeWidth;
    private AutoGrowArray.FloatArray mWidths = new AutoGrowArray.FloatArray();

    private MeasuredParagraph() {
    }

    private void applyMetricsAffectingSpan(TextPaint object, MetricAffectingSpan[] arrmetricAffectingSpan, int n, int n2, MeasuredText.Builder builder) {
        int n3;
        this.mCachedPaint.set((TextPaint)object);
        object = this.mCachedPaint;
        boolean bl = false;
        ((TextPaint)object).baselineShift = 0;
        if (builder != null) {
            bl = true;
        }
        if (bl && this.mCachedFm == null) {
            this.mCachedFm = new Paint.FontMetricsInt();
        }
        Object object2 = null;
        object = null;
        if (arrmetricAffectingSpan != null) {
            n3 = 0;
            do {
                object2 = object;
                if (n3 >= arrmetricAffectingSpan.length) break;
                object2 = arrmetricAffectingSpan[n3];
                if (object2 instanceof ReplacementSpan) {
                    object = (ReplacementSpan)object2;
                } else {
                    ((MetricAffectingSpan)object2).updateMeasureState(this.mCachedPaint);
                }
                ++n3;
            } while (true);
        }
        n3 = this.mTextStart;
        n -= n3;
        n2 -= n3;
        if (builder != null) {
            this.mCachedPaint.getFontMetricsInt(this.mCachedFm);
        }
        if (object2 != null) {
            this.applyReplacementRun((ReplacementSpan)object2, n, n2, builder);
        } else {
            this.applyStyleRun(n, n2, builder);
        }
        if (bl) {
            if (this.mCachedPaint.baselineShift < 0) {
                object = this.mCachedFm;
                ((Paint.FontMetricsInt)object).ascent += this.mCachedPaint.baselineShift;
                object = this.mCachedFm;
                ((Paint.FontMetricsInt)object).top += this.mCachedPaint.baselineShift;
            } else {
                object = this.mCachedFm;
                ((Paint.FontMetricsInt)object).descent += this.mCachedPaint.baselineShift;
                object = this.mCachedFm;
                ((Paint.FontMetricsInt)object).bottom += this.mCachedPaint.baselineShift;
            }
            this.mFontMetrics.append(this.mCachedFm.top);
            this.mFontMetrics.append(this.mCachedFm.bottom);
            this.mFontMetrics.append(this.mCachedFm.ascent);
            this.mFontMetrics.append(this.mCachedFm.descent);
        }
    }

    private void applyReplacementRun(ReplacementSpan replacementSpan, int n, int n2, MeasuredText.Builder builder) {
        TextPaint textPaint = this.mCachedPaint;
        Spanned spanned = this.mSpanned;
        int n3 = this.mTextStart;
        float f = replacementSpan.getSize(textPaint, spanned, n + n3, n2 + n3, this.mCachedFm);
        if (builder == null) {
            this.mWidths.set(n, f);
            if (n2 > n + 1) {
                Arrays.fill(this.mWidths.getRawArray(), n + 1, n2, 0.0f);
            }
            this.mWholeWidth += f;
        } else {
            builder.appendReplacementRun(this.mCachedPaint, n2 - n, f);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void applyStyleRun(int n, int n2, MeasuredText.Builder builder) {
        if (this.mLtrWithoutBidi) {
            if (builder == null) {
                this.mWholeWidth += this.mCachedPaint.getTextRunAdvances(this.mCopiedBuffer, n, n2 - n, n, n2 - n, false, this.mWidths.getRawArray(), n);
                return;
            } else {
                builder.appendStyleRun(this.mCachedPaint, n2 - n, false);
            }
            return;
        }
        byte by = this.mLevels.get(n);
        int n3 = n++;
        do {
            int n4;
            byte by2;
            block12 : {
                block11 : {
                    if (n == n2) break block11;
                    by2 = by;
                    n4 = n3;
                    if (this.mLevels.get(n) == by) break block12;
                }
                boolean bl = (by & 1) != 0;
                if (builder == null) {
                    n4 = n - n3;
                    this.mWholeWidth += this.mCachedPaint.getTextRunAdvances(this.mCopiedBuffer, n3, n4, n3, n4, bl, this.mWidths.getRawArray(), n3);
                } else {
                    builder.appendStyleRun(this.mCachedPaint, n - n3, bl);
                }
                if (n == n2) {
                    return;
                }
                n4 = n;
                by2 = this.mLevels.get(n);
            }
            ++n;
            by = by2;
            n3 = n4;
        } while (true);
    }

    public static MeasuredParagraph buildForBidi(CharSequence charSequence, int n, int n2, TextDirectionHeuristic textDirectionHeuristic, MeasuredParagraph measuredParagraph) {
        if (measuredParagraph == null) {
            measuredParagraph = MeasuredParagraph.obtain();
        }
        measuredParagraph.resetAndAnalyzeBidi(charSequence, n, n2, textDirectionHeuristic);
        return measuredParagraph;
    }

    public static MeasuredParagraph buildForMeasurement(TextPaint textPaint, CharSequence charSequence, int n, int n2, TextDirectionHeuristic textDirectionHeuristic, MeasuredParagraph measuredParagraph) {
        if (measuredParagraph == null) {
            measuredParagraph = MeasuredParagraph.obtain();
        }
        measuredParagraph.resetAndAnalyzeBidi(charSequence, n, n2, textDirectionHeuristic);
        measuredParagraph.mWidths.resize(measuredParagraph.mTextLength);
        if (measuredParagraph.mTextLength == 0) {
            return measuredParagraph;
        }
        if (measuredParagraph.mSpanned == null) {
            measuredParagraph.applyMetricsAffectingSpan(textPaint, null, n, n2, null);
        } else {
            while (n < n2) {
                int n3 = measuredParagraph.mSpanned.nextSpanTransition(n, n2, MetricAffectingSpan.class);
                measuredParagraph.applyMetricsAffectingSpan(textPaint, TextUtils.removeEmptySpans(measuredParagraph.mSpanned.getSpans(n, n3, MetricAffectingSpan.class), measuredParagraph.mSpanned, MetricAffectingSpan.class), n, n3, null);
                n = n3;
            }
        }
        return measuredParagraph;
    }

    public static MeasuredParagraph buildForStaticLayout(TextPaint textPaint, CharSequence object, int n, int n2, TextDirectionHeuristic textDirectionHeuristic, boolean bl, boolean bl2, MeasuredParagraph measuredParagraph, MeasuredParagraph measuredParagraph2) {
        if (measuredParagraph2 == null) {
            measuredParagraph2 = MeasuredParagraph.obtain();
        }
        measuredParagraph2.resetAndAnalyzeBidi((CharSequence)object, n, n2, textDirectionHeuristic);
        object = measuredParagraph == null ? new MeasuredText.Builder(measuredParagraph2.mCopiedBuffer).setComputeHyphenation(bl).setComputeLayout(bl2) : new MeasuredText.Builder(measuredParagraph.mMeasuredText);
        if (measuredParagraph2.mTextLength == 0) {
            measuredParagraph2.mMeasuredText = ((MeasuredText.Builder)object).build();
        } else {
            if (measuredParagraph2.mSpanned == null) {
                measuredParagraph2.applyMetricsAffectingSpan(textPaint, null, n, n2, (MeasuredText.Builder)object);
                measuredParagraph2.mSpanEndCache.append(n2);
            } else {
                while (n < n2) {
                    int n3 = measuredParagraph2.mSpanned.nextSpanTransition(n, n2, MetricAffectingSpan.class);
                    measuredParagraph2.applyMetricsAffectingSpan(textPaint, TextUtils.removeEmptySpans(measuredParagraph2.mSpanned.getSpans(n, n3, MetricAffectingSpan.class), measuredParagraph2.mSpanned, MetricAffectingSpan.class), n, n3, (MeasuredText.Builder)object);
                    measuredParagraph2.mSpanEndCache.append(n3);
                    n = n3;
                }
            }
            measuredParagraph2.mMeasuredText = ((MeasuredText.Builder)object).build();
        }
        return measuredParagraph2;
    }

    private static MeasuredParagraph obtain() {
        MeasuredParagraph measuredParagraph = sPool.acquire();
        if (measuredParagraph == null) {
            measuredParagraph = new MeasuredParagraph();
        }
        return measuredParagraph;
    }

    private void reset() {
        this.mSpanned = null;
        this.mCopiedBuffer = null;
        this.mWholeWidth = 0.0f;
        this.mLevels.clear();
        this.mWidths.clear();
        this.mFontMetrics.clear();
        this.mSpanEndCache.clear();
        this.mMeasuredText = null;
    }

    private void resetAndAnalyzeBidi(CharSequence arrreplacementSpan, int n, int n2, TextDirectionHeuristic textDirectionHeuristic) {
        this.reset();
        char[] arrc = arrreplacementSpan instanceof Spanned ? (char[])arrreplacementSpan : null;
        this.mSpanned = arrc;
        this.mTextStart = n;
        this.mTextLength = n2 - n;
        arrc = this.mCopiedBuffer;
        if (arrc == null || arrc.length != this.mTextLength) {
            this.mCopiedBuffer = new char[this.mTextLength];
        }
        TextUtils.getChars((CharSequence)arrreplacementSpan, n, n2, this.mCopiedBuffer, 0);
        arrreplacementSpan = this.mSpanned;
        if (arrreplacementSpan != null) {
            arrreplacementSpan = arrreplacementSpan.getSpans(n, n2, ReplacementSpan.class);
            for (n2 = 0; n2 < arrreplacementSpan.length; ++n2) {
                int n3 = this.mSpanned.getSpanStart(arrreplacementSpan[n2]) - n;
                int n4 = this.mSpanned.getSpanEnd(arrreplacementSpan[n2]) - n;
                int n5 = n3;
                if (n3 < 0) {
                    n5 = 0;
                }
                n3 = n4;
                if (n4 > this.mTextLength) {
                    n3 = this.mTextLength;
                }
                Arrays.fill(this.mCopiedBuffer, n5, n3, '\ufffc');
            }
        }
        arrreplacementSpan = TextDirectionHeuristics.LTR;
        n = 1;
        if ((textDirectionHeuristic == arrreplacementSpan || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR || textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) && TextUtils.doesNotNeedBidi(this.mCopiedBuffer, 0, this.mTextLength)) {
            this.mLevels.clear();
            this.mParaDir = 1;
            this.mLtrWithoutBidi = true;
        } else {
            if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
                n = 1;
            } else if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
                n = -1;
            } else if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                n = 2;
            } else if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
                n = -2;
            } else if (textDirectionHeuristic.isRtl(this.mCopiedBuffer, 0, this.mTextLength)) {
                n = -1;
            }
            this.mLevels.resize(this.mTextLength);
            this.mParaDir = AndroidBidi.bidi(n, this.mCopiedBuffer, this.mLevels.getRawArray());
            this.mLtrWithoutBidi = false;
        }
    }

    int breakText(int n, boolean bl, float f) {
        int n2;
        float[] arrf = this.mWidths.getRawArray();
        if (bl) {
            int n3;
            int n4 = 0;
            do {
                n3 = ++n4;
                if (n4 >= n) break;
                if (!((f -= arrf[n4]) < 0.0f)) continue;
                n3 = n4;
                break;
            } while (true);
            while (n3 > 0 && this.mCopiedBuffer[n3 - 1] == ' ') {
                --n3;
            }
            return n3;
        }
        int n5 = n - 1;
        do {
            n2 = --n5;
            if (n5 < 0) break;
            if (!((f -= arrf[n5]) < 0.0f)) continue;
            n2 = n5;
            break;
        } while (true);
        while (n2 < n - 1 && (this.mCopiedBuffer[n2 + 1] == ' ' || arrf[n2 + 1] == 0.0f)) {
            ++n2;
        }
        return n - n2 - 1;
    }

    public void getBounds(int n, int n2, Rect rect) {
        this.mMeasuredText.getBounds(n, n2, rect);
    }

    public float getCharWidthAt(int n) {
        return this.mMeasuredText.getCharWidthAt(n);
    }

    public char[] getChars() {
        return this.mCopiedBuffer;
    }

    public Layout.Directions getDirections(int n, int n2) {
        if (this.mLtrWithoutBidi) {
            return Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        return AndroidBidi.directions(this.mParaDir, this.mLevels.getRawArray(), n, this.mCopiedBuffer, n, n2 - n);
    }

    public AutoGrowArray.IntArray getFontMetrics() {
        return this.mFontMetrics;
    }

    public MeasuredText getMeasuredText() {
        return this.mMeasuredText;
    }

    public int getMemoryUsage() {
        return this.mMeasuredText.getMemoryUsage();
    }

    public int getParagraphDir() {
        return this.mParaDir;
    }

    public AutoGrowArray.IntArray getSpanEndCache() {
        return this.mSpanEndCache;
    }

    public int getTextLength() {
        return this.mTextLength;
    }

    public float getWholeWidth() {
        return this.mWholeWidth;
    }

    public float getWidth(int n, int n2) {
        float[] arrf = this.mMeasuredText;
        if (arrf == null) {
            arrf = this.mWidths.getRawArray();
            float f = 0.0f;
            while (n < n2) {
                f += arrf[n];
                ++n;
            }
            return f;
        }
        return arrf.getWidth(n, n2);
    }

    public AutoGrowArray.FloatArray getWidths() {
        return this.mWidths;
    }

    float measure(int n, int n2) {
        float f = 0.0f;
        float[] arrf = this.mWidths.getRawArray();
        while (n < n2) {
            f += arrf[n];
            ++n;
        }
        return f;
    }

    public void recycle() {
        this.release();
        sPool.release(this);
    }

    public void release() {
        this.reset();
        this.mLevels.clearWithReleasingLargeArray();
        this.mWidths.clearWithReleasingLargeArray();
        this.mFontMetrics.clearWithReleasingLargeArray();
        this.mSpanEndCache.clearWithReleasingLargeArray();
    }
}

