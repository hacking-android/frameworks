/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.os.LocaleList;
import android.text.Layout;
import android.text.PrecomputedText;
import android.text.SpanSet;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class TextLine {
    private static final boolean DEBUG = false;
    private static final char TAB_CHAR = '\t';
    private static final int TAB_INCREMENT = 20;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static final TextLine[] sCached = new TextLine[3];
    private final TextPaint mActivePaint = new TextPaint();
    private float mAddedWidthForJustify;
    @UnsupportedAppUsage
    private final SpanSet<CharacterStyle> mCharacterStyleSpanSet = new SpanSet<CharacterStyle>(CharacterStyle.class);
    private char[] mChars;
    private boolean mCharsValid;
    private PrecomputedText mComputed;
    private final DecorationInfo mDecorationInfo = new DecorationInfo();
    private final ArrayList<DecorationInfo> mDecorations = new ArrayList();
    private int mDir;
    private Layout.Directions mDirections;
    private int mEllipsisEnd;
    private int mEllipsisStart;
    private boolean mHasTabs;
    private boolean mIsJustifying;
    private int mLen;
    @UnsupportedAppUsage
    private final SpanSet<MetricAffectingSpan> mMetricAffectingSpanSpanSet = new SpanSet<MetricAffectingSpan>(MetricAffectingSpan.class);
    private TextPaint mPaint;
    @UnsupportedAppUsage
    private final SpanSet<ReplacementSpan> mReplacementSpanSpanSet = new SpanSet<ReplacementSpan>(ReplacementSpan.class);
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Spanned mSpanned;
    private int mStart;
    private Layout.TabStops mTabs;
    @UnsupportedAppUsage
    private CharSequence mText;
    private final TextPaint mWorkPaint = new TextPaint();

    private int adjustEndHyphenEdit(int n, int n2) {
        block0 : {
            if (n >= this.mLen) break block0;
            n2 = 0;
        }
        return n2;
    }

    private int adjustStartHyphenEdit(int n, int n2) {
        block0 : {
            if (n <= 0) break block0;
            n2 = 0;
        }
        return n2;
    }

    private char charAt(int n) {
        int n2 = this.mCharsValid ? (n = this.mChars[n]) : (n = (int)this.mText.charAt(this.mStart + n));
        return (char)n2;
    }

    private int countStretchableSpaces(int n, int n2) {
        int n3 = 0;
        while (n < n2) {
            char c = this.mCharsValid ? this.mChars[n] : this.mText.charAt(this.mStart + n);
            int n4 = n3;
            if (this.isStretchableWhitespace(c)) {
                n4 = n3 + 1;
            }
            ++n;
            n3 = n4;
        }
        return n3;
    }

    private float drawRun(Canvas canvas, int n, int n2, boolean bl, float f, int n3, int n4, int n5, boolean bl2) {
        int n6 = this.mDir;
        boolean bl3 = true;
        if (n6 != 1) {
            bl3 = false;
        }
        if (bl3 == bl) {
            float f2 = -this.measureRun(n, n2, n2, bl, null);
            this.handleRun(n, n2, n2, bl, canvas, f + f2, n3, n4, n5, null, false);
            return f2;
        }
        return this.handleRun(n, n2, n2, bl, canvas, f, n3, n4, n5, null, bl2);
    }

    private static void drawStroke(TextPaint textPaint, Canvas canvas, int n, float f, float f2, float f3, float f4, float f5) {
        f = f5 + (float)textPaint.baselineShift + f;
        int n2 = textPaint.getColor();
        Paint.Style style2 = textPaint.getStyle();
        boolean bl = textPaint.isAntiAlias();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(n);
        canvas.drawRect(f3, f, f4, f + f2, textPaint);
        textPaint.setStyle(style2);
        textPaint.setColor(n2);
        textPaint.setAntiAlias(bl);
    }

    private void drawTextRun(Canvas canvas, TextPaint textPaint, int n, int n2, int n3, int n4, boolean bl, float f, int n5) {
        if (this.mCharsValid) {
            canvas.drawTextRun(this.mChars, n, n2 - n, n3, n4 - n3, f, (float)n5, bl, (Paint)textPaint);
        } else {
            int n6 = this.mStart;
            canvas.drawTextRun(this.mText, n6 + n, n6 + n2, n6 + n3, n6 + n4, f, (float)n5, bl, (Paint)textPaint);
        }
    }

    private static boolean equalAttributes(TextPaint textPaint, TextPaint textPaint2) {
        boolean bl = textPaint.getColorFilter() == textPaint2.getColorFilter() && textPaint.getMaskFilter() == textPaint2.getMaskFilter() && textPaint.getShader() == textPaint2.getShader() && textPaint.getTypeface() == textPaint2.getTypeface() && textPaint.getXfermode() == textPaint2.getXfermode() && textPaint.getTextLocales().equals(textPaint2.getTextLocales()) && TextUtils.equals(textPaint.getFontFeatureSettings(), textPaint2.getFontFeatureSettings()) && TextUtils.equals(textPaint.getFontVariationSettings(), textPaint2.getFontVariationSettings()) && textPaint.getShadowLayerRadius() == textPaint2.getShadowLayerRadius() && textPaint.getShadowLayerDx() == textPaint2.getShadowLayerDx() && textPaint.getShadowLayerDy() == textPaint2.getShadowLayerDy() && textPaint.getShadowLayerColor() == textPaint2.getShadowLayerColor() && textPaint.getFlags() == textPaint2.getFlags() && textPaint.getHinting() == textPaint2.getHinting() && textPaint.getStyle() == textPaint2.getStyle() && textPaint.getColor() == textPaint2.getColor() && textPaint.getStrokeWidth() == textPaint2.getStrokeWidth() && textPaint.getStrokeMiter() == textPaint2.getStrokeMiter() && textPaint.getStrokeCap() == textPaint2.getStrokeCap() && textPaint.getStrokeJoin() == textPaint2.getStrokeJoin() && textPaint.getTextAlign() == textPaint2.getTextAlign() && textPaint.isElegantTextHeight() == textPaint2.isElegantTextHeight() && textPaint.getTextSize() == textPaint2.getTextSize() && textPaint.getTextScaleX() == textPaint2.getTextScaleX() && textPaint.getTextSkewX() == textPaint2.getTextSkewX() && textPaint.getLetterSpacing() == textPaint2.getLetterSpacing() && textPaint.getWordSpacing() == textPaint2.getWordSpacing() && textPaint.getStartHyphenEdit() == textPaint2.getStartHyphenEdit() && textPaint.getEndHyphenEdit() == textPaint2.getEndHyphenEdit() && textPaint.bgColor == textPaint2.bgColor && textPaint.baselineShift == textPaint2.baselineShift && textPaint.linkColor == textPaint2.linkColor && textPaint.drawableState == textPaint2.drawableState && textPaint.density == textPaint2.density && textPaint.underlineColor == textPaint2.underlineColor && textPaint.underlineThickness == textPaint2.underlineThickness;
        return bl;
    }

    private static void expandMetricsFromPaint(Paint.FontMetricsInt fontMetricsInt, TextPaint textPaint) {
        int n = fontMetricsInt.top;
        int n2 = fontMetricsInt.ascent;
        int n3 = fontMetricsInt.descent;
        int n4 = fontMetricsInt.bottom;
        int n5 = fontMetricsInt.leading;
        textPaint.getFontMetricsInt(fontMetricsInt);
        TextLine.updateMetrics(fontMetricsInt, n, n2, n3, n4, n5);
    }

    private void extractDecorationInfo(TextPaint textPaint, DecorationInfo decorationInfo) {
        decorationInfo.isStrikeThruText = textPaint.isStrikeThruText();
        if (decorationInfo.isStrikeThruText) {
            textPaint.setStrikeThruText(false);
        }
        decorationInfo.isUnderlineText = textPaint.isUnderlineText();
        if (decorationInfo.isUnderlineText) {
            textPaint.setUnderlineText(false);
        }
        decorationInfo.underlineColor = textPaint.underlineColor;
        decorationInfo.underlineThickness = textPaint.underlineThickness;
        textPaint.setUnderlineText(0, 0.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getOffsetBeforeAfter(int n, int n2, int n3, boolean bl, int n4, boolean bl2) {
        int n5;
        TextPaint textPaint;
        Object object;
        int n6;
        block8 : {
            MetricAffectingSpan[] arrmetricAffectingSpan;
            block10 : {
                block9 : {
                    if (n < 0) break block9;
                    n6 = 0;
                    n = bl2 ? this.mLen : 0;
                    if (n4 == n) break block9;
                    textPaint = this.mWorkPaint;
                    textPaint.set(this.mPaint);
                    if (this.mIsJustifying) {
                        textPaint.setWordSpacing(this.mAddedWidthForJustify);
                    }
                    n = n2;
                    if (this.mSpanned != null) break block10;
                    n2 = n3;
                    break block8;
                }
                if (!bl2) return TextUtils.getOffsetBefore(this.mText, this.mStart + n4) - this.mStart;
                return TextUtils.getOffsetAfter(this.mText, this.mStart + n4) - this.mStart;
            }
            n5 = bl2 ? n4 + 1 : n4;
            int n7 = this.mStart;
            do {
                n2 = this.mSpanned.nextSpanTransition(this.mStart + n, n7 + n3, MetricAffectingSpan.class);
                int n8 = this.mStart;
                if ((n2 -= n8) >= n5) {
                    arrmetricAffectingSpan = TextUtils.removeEmptySpans(this.mSpanned.getSpans(n8 + n, n8 + n2, MetricAffectingSpan.class), this.mSpanned, MetricAffectingSpan.class);
                    if (arrmetricAffectingSpan.length > 0) {
                        object = null;
                        break;
                    }
                    break block8;
                }
                n = n2;
            } while (true);
            for (n3 = 0; n3 < arrmetricAffectingSpan.length; ++n3) {
                MetricAffectingSpan metricAffectingSpan = arrmetricAffectingSpan[n3];
                if (metricAffectingSpan instanceof ReplacementSpan) {
                    object = (ReplacementSpan)metricAffectingSpan;
                    continue;
                }
                metricAffectingSpan.updateMeasureState(textPaint);
            }
            if (object != null) {
                if (!bl2) return n;
                return n2;
            }
        }
        n3 = bl2 ? n6 : 2;
        if (this.mCharsValid) {
            return textPaint.getTextRunCursor(this.mChars, n, n2 - n, bl, n4, n3);
        }
        object = this.mText;
        n5 = this.mStart;
        return textPaint.getTextRunCursor((CharSequence)object, n5 + n, n5 + n2, bl, n5 + n4, n3) - this.mStart;
    }

    private float getRunAdvance(TextPaint textPaint, int n, int n2, int n3, int n4, boolean bl, int n5) {
        if (this.mCharsValid) {
            return textPaint.getRunAdvance(this.mChars, n, n2, n3, n4, bl, n5);
        }
        int n6 = this.mStart;
        PrecomputedText precomputedText = this.mComputed;
        if (precomputedText == null) {
            return textPaint.getRunAdvance(this.mText, n6 + n, n6 + n2, n6 + n3, n6 + n4, bl, n6 + n5);
        }
        return precomputedText.getWidth(n + n6, n2 + n6);
    }

    private float handleReplacement(ReplacementSpan replacementSpan, TextPaint textPaint, int n, int n2, boolean bl, Canvas canvas, float f, int n3, int n4, int n5, Paint.FontMetricsInt fontMetricsInt, boolean bl2) {
        float f2;
        block10 : {
            int n6;
            int n7;
            block9 : {
                int n8;
                float f3;
                int n9;
                int n10;
                int n11;
                block8 : {
                    f3 = 0.0f;
                    n9 = this.mStart;
                    n6 = n9 + n;
                    n7 = n9 + n2;
                    if (bl2) break block8;
                    f2 = f3;
                    if (canvas == null) break block9;
                    f2 = f3;
                    if (!bl) break block9;
                }
                if ((n = fontMetricsInt != null ? 1 : 0) != 0) {
                    n10 = fontMetricsInt.top;
                    n9 = fontMetricsInt.ascent;
                    n8 = fontMetricsInt.descent;
                    n2 = fontMetricsInt.bottom;
                    int n12 = fontMetricsInt.leading;
                    n11 = n9;
                    n9 = n8;
                    n8 = n2;
                    n2 = n12;
                } else {
                    n10 = 0;
                    n11 = 0;
                    n9 = 0;
                    n8 = 0;
                    n2 = 0;
                }
                f2 = f3 = (float)replacementSpan.getSize(textPaint, this.mText, n6, n7, fontMetricsInt);
                if (n != 0) {
                    TextLine.updateMetrics(fontMetricsInt, n10, n11, n9, n8, n2);
                    f2 = f3;
                }
            }
            if (canvas != null) {
                if (bl) {
                    f -= f2;
                }
                replacementSpan.draw(canvas, this.mText, n6, n7, f, n3, n4, n5, textPaint);
            }
            if (!bl) break block10;
            f2 = -f2;
        }
        return f2;
    }

    private float handleRun(int n, int n2, int n3, boolean bl, Canvas object, float f, int n4, int n5, int n6, Paint.FontMetricsInt fontMetricsInt, boolean bl2) {
        if (n2 >= n && n2 <= n3) {
            int n7;
            Object object2;
            if (n == n2) {
                object = this.mWorkPaint;
                ((TextPaint)object).set(this.mPaint);
                if (fontMetricsInt != null) {
                    TextLine.expandMetricsFromPaint(fontMetricsInt, (TextPaint)object);
                }
                return 0.0f;
            }
            Object object3 = this.mSpanned;
            if (object3 == null) {
                n7 = 0;
            } else {
                object2 = this.mMetricAffectingSpanSpanSet;
                n7 = this.mStart;
                ((SpanSet)object2).init((Spanned)object3, n7 + n, n7 + n3);
                object3 = this.mCharacterStyleSpanSet;
                object2 = this.mSpanned;
                n7 = this.mStart;
                ((SpanSet)object3).init((Spanned)object2, n7 + n, n7 + n3);
                n7 = this.mMetricAffectingSpanSpanSet.numberOfSpans == 0 && this.mCharacterStyleSpanSet.numberOfSpans == 0 ? 0 : 1;
            }
            if (n7 == 0) {
                object3 = this.mWorkPaint;
                ((TextPaint)object3).set(this.mPaint);
                ((Paint)object3).setStartHyphenEdit(this.adjustStartHyphenEdit(n, ((Paint)object3).getStartHyphenEdit()));
                ((Paint)object3).setEndHyphenEdit(this.adjustEndHyphenEdit(n3, ((Paint)object3).getEndHyphenEdit()));
                return this.handleText((TextPaint)object3, n, n3, n, n3, bl, (Canvas)object, f, n4, n5, n6, fontMetricsInt, bl2, n2, null);
            }
            float f2 = f;
            while (n < n2) {
                int n8;
                boolean bl3;
                int n9;
                Object object4 = this.mWorkPaint;
                ((TextPaint)object4).set(this.mPaint);
                object3 = this.mMetricAffectingSpanSpanSet;
                n7 = this.mStart;
                int n10 = ((SpanSet)object3).getNextTransition(n7 + n, n7 + n3) - this.mStart;
                int n11 = Math.min(n10, n2);
                object3 = null;
                for (n7 = 0; n7 < this.mMetricAffectingSpanSpanSet.numberOfSpans; ++n7) {
                    object2 = object3;
                    if (this.mMetricAffectingSpanSpanSet.spanStarts[n7] < this.mStart + n11) {
                        n9 = this.mMetricAffectingSpanSpanSet.spanEnds[n7];
                        n8 = this.mStart;
                        if (n9 <= n8 + n) {
                            object2 = object3;
                        } else {
                            n9 = n8 + this.mEllipsisStart <= this.mMetricAffectingSpanSpanSet.spanStarts[n7] && this.mMetricAffectingSpanSpanSet.spanEnds[n7] <= this.mStart + this.mEllipsisEnd ? 1 : 0;
                            object2 = ((MetricAffectingSpan[])this.mMetricAffectingSpanSpanSet.spans)[n7];
                            if (object2 instanceof ReplacementSpan) {
                                object3 = n9 == 0 ? (ReplacementSpan)object2 : null;
                                object2 = object3;
                            } else {
                                ((CharacterStyle)object2).updateDrawState((TextPaint)object4);
                                object2 = object3;
                            }
                        }
                    }
                    object3 = object2;
                }
                if (object3 != null) {
                    bl3 = bl2 || n11 < n2;
                    f2 += this.handleReplacement((ReplacementSpan)object3, (TextPaint)object4, n, n11, bl, (Canvas)object, f2, n4, n5, n6, fontMetricsInt, bl3);
                } else {
                    n7 = n11;
                    int n12 = n10;
                    object3 = object4;
                    object4 = this;
                    TextPaint textPaint = ((TextLine)object4).mActivePaint;
                    textPaint.set(((TextLine)object4).mPaint);
                    object2 = ((TextLine)object4).mDecorationInfo;
                    ((TextLine)object4).mDecorations.clear();
                    int n13 = n;
                    n9 = n7;
                    int n14 = n;
                    n8 = n7;
                    n11 = n;
                    n = n14;
                    n7 = n13;
                    while (n < n8) {
                        Object object5 = ((TextLine)object4).mCharacterStyleSpanSet;
                        n13 = ((TextLine)object4).mStart;
                        n14 = ((SpanSet)object5).getNextTransition(n13 + n, n13 + n12) - ((TextLine)object4).mStart;
                        int n15 = Math.min(n14, n8);
                        ((TextPaint)object3).set(((TextLine)object4).mPaint);
                        for (n13 = 0; n13 < object4.mCharacterStyleSpanSet.numberOfSpans; ++n13) {
                            if (object4.mCharacterStyleSpanSet.spanStarts[n13] >= ((TextLine)object4).mStart + n15 || object4.mCharacterStyleSpanSet.spanEnds[n13] <= ((TextLine)object4).mStart + n) continue;
                            ((CharacterStyle[])object4.mCharacterStyleSpanSet.spans)[n13].updateDrawState((TextPaint)object3);
                        }
                        TextLine.super.extractDecorationInfo((TextPaint)object3, (DecorationInfo)object2);
                        if (n == n11) {
                            textPaint.set((TextPaint)object3);
                        } else if (!TextLine.equalAttributes((TextPaint)object3, textPaint)) {
                            textPaint.setStartHyphenEdit(TextLine.super.adjustStartHyphenEdit(n7, ((TextLine)object4).mPaint.getStartHyphenEdit()));
                            textPaint.setEndHyphenEdit(TextLine.super.adjustEndHyphenEdit(n9, ((TextLine)object4).mPaint.getEndHyphenEdit()));
                            bl3 = bl2 || n9 < n2;
                            f2 += this.handleText(textPaint, n7, n9, n11, n12, bl, (Canvas)object, f2, n4, n5, n6, fontMetricsInt, bl3, Math.min(n9, n8), ((TextLine)object4).mDecorations);
                            n7 = n;
                            textPaint.set((TextPaint)object3);
                            object4 = this;
                            ((TextLine)object4).mDecorations.clear();
                        }
                        n13 = n9 = n14;
                        if (((DecorationInfo)object2).hasDecoration()) {
                            object5 = ((DecorationInfo)object2).copyInfo();
                            ((DecorationInfo)object5).start = n;
                            ((DecorationInfo)object5).end = n9;
                            ((TextLine)object4).mDecorations.add((DecorationInfo)object5);
                        }
                        n = n9;
                        n9 = n13;
                    }
                    textPaint.setStartHyphenEdit(TextLine.super.adjustStartHyphenEdit(n7, ((TextLine)object4).mPaint.getStartHyphenEdit()));
                    textPaint.setEndHyphenEdit(TextLine.super.adjustEndHyphenEdit(n9, ((TextLine)object4).mPaint.getEndHyphenEdit()));
                    bl3 = bl2 || n9 < n2;
                    f2 += this.handleText(textPaint, n7, n9, n11, n12, bl, (Canvas)object, f2, n4, n5, n6, fontMetricsInt, bl3, Math.min(n9, n8), ((TextLine)object4).mDecorations);
                }
                n = n10;
            }
            return f2 - f;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("measureLimit (");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(") is out of start (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") and limit (");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(") bounds");
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    private float handleText(TextPaint textPaint, int n, int n2, int n3, int n4, boolean bl, Canvas canvas, float f, int n5, int n6, int n7, Paint.FontMetricsInt object, boolean bl2, int n8, ArrayList<DecorationInfo> arrayList) {
        block14 : {
            if (this.mIsJustifying) {
                textPaint.setWordSpacing(this.mAddedWidthForJustify);
            }
            if (object != null) {
                TextLine.expandMetricsFromPaint(object, textPaint);
            }
            if (n2 == n) {
                return 0.0f;
            }
            float f2 = 0.0f;
            int n9 = arrayList == null ? 0 : arrayList.size();
            if (bl2 || canvas != null && (textPaint.bgColor != 0 || n9 != 0 || bl)) {
                f2 = this.getRunAdvance(textPaint, n, n2, n3, n4, bl, n8);
            }
            if (canvas != null) {
                int n10;
                float f3;
                if (bl) {
                    f3 = f - f2;
                } else {
                    f3 = f;
                    f += f2;
                }
                if (textPaint.bgColor != 0) {
                    n10 = textPaint.getColor();
                    object = textPaint.getStyle();
                    textPaint.setColor(textPaint.bgColor);
                    textPaint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(f3, n5, f, n7, textPaint);
                    textPaint.setStyle((Paint.Style)((Object)object));
                    textPaint.setColor(n10);
                }
                this.drawTextRun(canvas, textPaint, n, n2, n3, n4, bl, f3, n6 + textPaint.baselineShift);
                if (n9 != 0) {
                    n5 = n9;
                    for (n7 = 0; n7 < n5; ++n7) {
                        float f4;
                        object = arrayList.get(n7);
                        n9 = Math.max(((DecorationInfo)object).start, n);
                        n10 = Math.min(((DecorationInfo)object).end, n8);
                        float f5 = this.getRunAdvance(textPaint, n, n2, n3, n4, bl, n9);
                        float f6 = this.getRunAdvance(textPaint, n, n2, n3, n4, bl, n10);
                        if (bl) {
                            f4 = f - f6;
                            f5 = f - f5;
                        } else {
                            f4 = f3 + f5;
                            f5 = f3 + f6;
                        }
                        if (((DecorationInfo)object).underlineColor != 0) {
                            TextLine.drawStroke(textPaint, canvas, ((DecorationInfo)object).underlineColor, textPaint.getUnderlinePosition(), ((DecorationInfo)object).underlineThickness, f4, f5, n6);
                        }
                        if (((DecorationInfo)object).isUnderlineText) {
                            f6 = Math.max(textPaint.getUnderlineThickness(), 1.0f);
                            TextLine.drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getUnderlinePosition(), f6, f4, f5, n6);
                        }
                        if (!((DecorationInfo)object).isStrikeThruText) continue;
                        f6 = Math.max(textPaint.getStrikeThruThickness(), 1.0f);
                        TextLine.drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getStrikeThruPosition(), f6, f4, f5, n6);
                    }
                }
            }
            f = f2;
            if (!bl) break block14;
            f = -f;
        }
        return f;
    }

    public static boolean isLineEndSpace(char c) {
        boolean bl = c == ' ' || c == '\t' || c == '\u1680' || '\u2000' <= c && c <= '\u200a' && c != '\u2007' || c == '\u205f' || c == '\u3000';
        return bl;
    }

    private boolean isStretchableWhitespace(int n) {
        boolean bl = n == 32;
        return bl;
    }

    private float measureRun(int n, int n2, int n3, boolean bl, Paint.FontMetricsInt fontMetricsInt) {
        return this.handleRun(n, n2, n3, bl, null, 0.0f, 0, 0, 0, fontMetricsInt, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static TextLine obtain() {
        TextLine[] arrtextLine = sCached;
        synchronized (arrtextLine) {
            int n;
            int n2 = sCached.length;
            do {
                if ((n = n2 - 1) < 0) {
                    return new TextLine();
                }
                n2 = n;
            } while (sCached[n] == null);
            TextLine textLine = sCached[n];
            TextLine.sCached[n] = null;
            return textLine;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static TextLine recycle(TextLine textLine) {
        textLine.mText = null;
        textLine.mPaint = null;
        textLine.mDirections = null;
        textLine.mSpanned = null;
        textLine.mTabs = null;
        textLine.mChars = null;
        textLine.mComputed = null;
        textLine.mMetricAffectingSpanSpanSet.recycle();
        textLine.mCharacterStyleSpanSet.recycle();
        textLine.mReplacementSpanSpanSet.recycle();
        TextLine[] arrtextLine = sCached;
        synchronized (arrtextLine) {
            for (int i = 0; i < sCached.length; ++i) {
                if (sCached[i] != null) continue;
                TextLine.sCached[i] = textLine;
                break;
            }
            return null;
        }
    }

    static void updateMetrics(Paint.FontMetricsInt fontMetricsInt, int n, int n2, int n3, int n4, int n5) {
        fontMetricsInt.top = Math.min(fontMetricsInt.top, n);
        fontMetricsInt.ascent = Math.min(fontMetricsInt.ascent, n2);
        fontMetricsInt.descent = Math.max(fontMetricsInt.descent, n3);
        fontMetricsInt.bottom = Math.max(fontMetricsInt.bottom, n4);
        fontMetricsInt.leading = Math.max(fontMetricsInt.leading, n5);
    }

    void draw(Canvas canvas, float f, int n, int n2, int n3) {
        float f2 = 0.0f;
        int n4 = this.mDirections.getRunCount();
        for (int i = 0; i < n4; ++i) {
            int n5 = this.mDirections.getRunStart(i);
            int n6 = Math.min(this.mDirections.getRunLength(i) + n5, this.mLen);
            boolean bl = this.mDirections.isRunRtl(i);
            for (int j = this.mHasTabs != false ? n5 : n6; j <= n6; ++j) {
                float f3;
                if (j != n6 && this.charAt(j) != '\t') continue;
                boolean bl2 = i != n4 - 1 || j != this.mLen;
                int n7 = j;
                f2 = f3 = f2 + this.drawRun(canvas, n5, j, bl, f + f2, n, n2, n3, bl2);
                if (n7 != n6) {
                    n5 = this.mDir;
                    f2 = (float)n5 * this.nextTab((float)n5 * f3);
                }
                n5 = n7 + 1;
            }
        }
    }

    int getOffsetToLeftRightOf(int n, boolean bl) {
        int n2;
        block24 : {
            int n3;
            int n4;
            int n5;
            block23 : {
                int n6;
                boolean bl2;
                int n7;
                int n8;
                n3 = this.mLen;
                boolean bl3 = this.mDir == -1;
                int[] arrn = this.mDirections.mDirections;
                n2 = 0;
                n5 = n3;
                boolean bl4 = false;
                if (n == 0) {
                    n4 = 0;
                    n8 = -2;
                    n = -1;
                } else if (n == n3) {
                    n8 = arrn.length;
                    n4 = 0;
                    n = -1;
                } else {
                    boolean bl5;
                    block22 : {
                        for (n4 = 0; n4 < arrn.length; n4 += 2) {
                            n2 = 0 + arrn[n4];
                            if (n < n2) continue;
                            n5 = n8 = (arrn[n4 + 1] & 67108863) + n2;
                            if (n8 > n3) {
                                n5 = n3;
                            }
                            if (n >= n5) continue;
                            n7 = arrn[n4 + 1] >>> 26 & 63;
                            if (n == n2) {
                                block21 : {
                                    int n9 = n - 1;
                                    n8 = n2;
                                    for (n6 = 0; n6 < arrn.length; n6 += 2) {
                                        int n10;
                                        int n11 = arrn[n6] + 0;
                                        if (n9 < n11) continue;
                                        n2 = n10 = n11 + (arrn[n6 + 1] & 67108863);
                                        if (n10 > n3) {
                                            n2 = n3;
                                        }
                                        if (n9 >= n2 || (n10 = arrn[n6 + 1] >>> 26 & 63) >= n7) continue;
                                        n4 = n6;
                                        n8 = n10;
                                        n6 = n11;
                                        n5 = n2;
                                        bl4 = true;
                                        n2 = n6;
                                        break block21;
                                    }
                                    n2 = n8;
                                    n8 = n7;
                                }
                                n6 = n8;
                                n8 = n4;
                                n4 = n6;
                                break block22;
                            }
                            bl4 = false;
                            n8 = n4;
                            n4 = n7;
                            break block22;
                        }
                        n6 = 0;
                        bl4 = false;
                        n8 = n4;
                        n4 = n6;
                    }
                    if (n8 != arrn.length && (n != (n6 = (bl5 = bl == (bl2 = (n4 & 1) != 0)) ? n5 : n2) || bl5 != bl4)) {
                        n = this.getOffsetBeforeAfter(n8, n2, n5, bl2, n, bl5);
                        if (bl5) {
                            n2 = n5;
                        }
                        if (n != n2) {
                            return n;
                        }
                    } else {
                        n = -1;
                    }
                }
                do {
                    int[] arrn2 = arrn;
                    n5 = bl == bl3 ? 1 : 0;
                    n2 = n5 != 0 ? 2 : -2;
                    n6 = n8 + n2;
                    if (n6 < 0 || n6 >= arrn2.length) break block23;
                    n8 = 0 + arrn2[n6];
                    n5 = n8 + (arrn2[n6 + 1] & 67108863);
                    if (n5 > n3) {
                        n5 = n3;
                    }
                    bl4 = ((n7 = arrn2[n6 + 1] >>> 26 & 63) & 1) != 0;
                    bl2 = bl == bl4;
                    if (n != -1) break;
                    n = bl2 ? n8 : n5;
                    n = this.getOffsetBeforeAfter(n6, n8, n5, bl4, n, bl2);
                    n4 = bl2 ? n5 : n8;
                    n2 = n;
                    if (n == n4) {
                        n8 = n6;
                        n4 = n7;
                        continue;
                    }
                    break block24;
                    break;
                } while (true);
                n2 = n;
                if (n7 < n4) {
                    n = bl2 ? n8 : n5;
                    n2 = n;
                }
                break block24;
            }
            n4 = -1;
            if (n == -1) {
                n = n4;
                if (n5 != 0) {
                    n = this.mLen + 1;
                }
                n2 = n;
            } else {
                n2 = n;
                if (n <= n3) {
                    n = n5 != 0 ? n3 : 0;
                    n2 = n;
                }
            }
        }
        return n2;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void justify(float f) {
        int n;
        for (n = this.mLen; n > 0 && TextLine.isLineEndSpace(this.mText.charAt(this.mStart + n - 1)); --n) {
        }
        int n2 = this.countStretchableSpaces(0, n);
        if (n2 == 0) {
            return;
        }
        this.mAddedWidthForJustify = (f - Math.abs(this.measure(n, false, null))) / (float)n2;
        this.mIsJustifying = true;
    }

    public float measure(int n, boolean bl, Paint.FontMetricsInt object) {
        block13 : {
            if (n > this.mLen) break block13;
            int n2 = bl ? n - 1 : n;
            if (n2 < 0) {
                return 0.0f;
            }
            float f = 0.0f;
            for (int i = 0; i < this.mDirections.getRunCount(); ++i) {
                int n3 = this.mDirections.getRunStart(i);
                int n4 = Math.min(this.mDirections.getRunLength(i) + n3, this.mLen);
                boolean bl2 = this.mDirections.isRunRtl(i);
                for (int j = this.mHasTabs != false ? n3 : n4; j <= n4; ++j) {
                    int n5;
                    float f2;
                    block15 : {
                        block14 : {
                            if (j == n4) break block14;
                            n5 = n3;
                            f2 = f;
                            if (this.charAt(j) != '\t') break block15;
                        }
                        boolean bl3 = false;
                        n5 = n2 >= n3 && n2 < j ? 1 : 0;
                        bl = this.mDir == -1;
                        if (bl == bl2) {
                            bl3 = true;
                        }
                        if (n5 != 0 && bl3) {
                            return this.measureRun(n3, n, j, bl2, (Paint.FontMetricsInt)object) + f;
                        }
                        f2 = this.measureRun(n3, j, j, bl2, (Paint.FontMetricsInt)object);
                        if (!bl3) {
                            f2 = -f2;
                        }
                        f2 = f + f2;
                        if (n5 != 0) {
                            return this.measureRun(n3, n, j, bl2, null) + f2;
                        }
                        f = f2;
                        if (j != n4) {
                            if (n == j) {
                                return f2;
                            }
                            n3 = this.mDir;
                            f = (float)n3 * this.nextTab((float)n3 * f2);
                            if (n2 == j) {
                                return f;
                            }
                        }
                        n5 = j + 1;
                        f2 = f;
                    }
                    n3 = n5;
                    f = f2;
                }
            }
            return f;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("offset(");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") should be less than line limit(");
        ((StringBuilder)object).append(this.mLen);
        ((StringBuilder)object).append(")");
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    @VisibleForTesting
    public float[] measureAllOffsets(boolean[] object, Paint.FontMetricsInt fontMetricsInt) {
        int n;
        int n2 = this.mLen;
        float[] arrf = new float[n2 + 1];
        int[] arrn = new int[n2 + 1];
        for (n2 = 0; n2 < arrn.length; ++n2) {
            n = object[n2] ? n2 - 1 : n2;
            arrn[n2] = n;
        }
        if (arrn[0] < 0) {
            arrf[0] = 0.0f;
        }
        float f = 0.0f;
        for (int i = 0; i < this.mDirections.getRunCount(); ++i) {
            n = this.mDirections.getRunStart(i);
            int n3 = Math.min(this.mDirections.getRunLength(i) + n, this.mLen);
            boolean bl = this.mDirections.isRunRtl(i);
            n2 = this.mHasTabs ? n : n3;
            int n4 = n2;
            n2 = n;
            while (n4 <= n3) {
                float f2;
                block14 : {
                    block13 : {
                        if (n4 == n3) break block13;
                        n = n2;
                        f2 = f;
                        if (this.charAt(n4) != '\t') break block14;
                    }
                    boolean bl2 = this.mDir == -1;
                    boolean bl3 = bl2 == bl;
                    n = n2;
                    f2 = this.measureRun(n2, n4, n4, bl, fontMetricsInt);
                    float f3 = bl3 ? f2 : -f2;
                    f3 = f + f3;
                    if (!bl3) {
                        f = f3;
                    }
                    object = bl3 ? fontMetricsInt : null;
                    for (n2 = n; n2 <= n4 && n2 <= this.mLen; ++n2) {
                        if (arrn[n2] < n || arrn[n2] >= n4) continue;
                        arrf[n2] = f + this.measureRun(n, n2, n4, bl, (Paint.FontMetricsInt)object);
                    }
                    f = f3;
                    if (n4 != n3) {
                        if (arrn[n4] == n4) {
                            arrf[n4] = f3;
                        }
                        n2 = this.mDir;
                        f = (float)n2 * this.nextTab((float)n2 * f3);
                        if (arrn[n4 + 1] == n4) {
                            arrf[n4 + 1] = f;
                        }
                    }
                    n = n4 + 1;
                    f2 = f;
                }
                ++n4;
                n2 = n;
                f = f2;
            }
        }
        n2 = this.mLen;
        if (arrn[n2] == n2) {
            arrf[n2] = f;
        }
        return arrf;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public float metrics(Paint.FontMetricsInt fontMetricsInt) {
        return this.measure(this.mLen, false, fontMetricsInt);
    }

    float nextTab(float f) {
        Layout.TabStops tabStops = this.mTabs;
        if (tabStops != null) {
            return tabStops.nextTab(f);
        }
        return Layout.TabStops.nextDefaultStop(f, 20.0f);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void set(TextPaint arrc, CharSequence charSequence, int n, int n2, int n3, Layout.Directions directions, boolean bl, Layout.TabStops tabStops, int n4, int n5) {
        this.mPaint = arrc;
        this.mText = charSequence;
        this.mStart = n;
        this.mLen = n2 - n;
        this.mDir = n3;
        this.mDirections = directions;
        if (this.mDirections != null) {
            this.mHasTabs = bl;
            this.mSpanned = null;
            bl = false;
            if (charSequence instanceof Spanned) {
                this.mSpanned = (Spanned)charSequence;
                this.mReplacementSpanSpanSet.init(this.mSpanned, n, n2);
                bl = this.mReplacementSpanSpanSet.numberOfSpans > 0;
            }
            this.mComputed = null;
            if (charSequence instanceof PrecomputedText) {
                this.mComputed = (PrecomputedText)charSequence;
                if (!this.mComputed.getParams().getTextPaint().equalsForTextMeasurement((Paint)arrc)) {
                    this.mComputed = null;
                }
            }
            this.mCharsValid = bl;
            if (this.mCharsValid) {
                arrc = this.mChars;
                if (arrc == null || arrc.length < this.mLen) {
                    this.mChars = ArrayUtils.newUnpaddedCharArray(this.mLen);
                }
                TextUtils.getChars(charSequence, n, n2, this.mChars, 0);
                if (bl) {
                    arrc = this.mChars;
                    n3 = n;
                    while (n3 < n2) {
                        int n6 = this.mReplacementSpanSpanSet.getNextTransition(n3, n2);
                        if (this.mReplacementSpanSpanSet.hasSpansIntersecting(n3, n6) && (n3 - n >= n5 || n6 - n <= n4)) {
                            arrc[n3 - n] = (char)65532;
                            for (n3 = n3 - n + 1; n3 < n6 - n; ++n3) {
                                arrc[n3] = (char)65279;
                            }
                        }
                        n3 = n6;
                    }
                }
            }
            this.mTabs = tabStops;
            this.mAddedWidthForJustify = 0.0f;
            n2 = 0;
            this.mIsJustifying = false;
            n = n4 != n5 ? n4 : 0;
            this.mEllipsisStart = n;
            n = n2;
            if (n4 != n5) {
                n = n5;
            }
            this.mEllipsisEnd = n;
            return;
        }
        throw new IllegalArgumentException("Directions cannot be null");
    }

    private static final class DecorationInfo {
        public int end = -1;
        public boolean isStrikeThruText;
        public boolean isUnderlineText;
        public int start = -1;
        public int underlineColor;
        public float underlineThickness;

        private DecorationInfo() {
        }

        public DecorationInfo copyInfo() {
            DecorationInfo decorationInfo = new DecorationInfo();
            decorationInfo.isStrikeThruText = this.isStrikeThruText;
            decorationInfo.isUnderlineText = this.isUnderlineText;
            decorationInfo.underlineColor = this.underlineColor;
            decorationInfo.underlineThickness = this.underlineThickness;
            return decorationInfo;
        }

        public boolean hasDecoration() {
            boolean bl = this.isStrikeThruText || this.isUnderlineText || this.underlineColor != 0;
            return bl;
        }
    }

}

