/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;
import android.graphics.text.LineBreaker;
import android.graphics.text.MeasuredText;
import android.text.AutoGrowArray;
import android.text.Layout;
import android.text.MeasuredParagraph;
import android.text.PrecomputedText;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineHeightSpan;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.Arrays;

public class StaticLayout
extends Layout {
    private static final char CHAR_NEW_LINE = '\n';
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DEFAULT_MAX_LINE_HEIGHT = -1;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    @UnsupportedAppUsage
    private static final int ELLIPSIS_START = 5;
    private static final int END_HYPHEN_MASK = 7;
    private static final int EXTRA = 3;
    private static final double EXTRA_ROUNDING = 0.5;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    private static final int START = 0;
    private static final int START_HYPHEN_BITS_SHIFT = 3;
    private static final int START_HYPHEN_MASK = 24;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final float TAB_INCREMENT = 20.0f;
    private static final int TAB_MASK = 536870912;
    static final String TAG = "StaticLayout";
    private static final int TOP = 1;
    private int mBottomPadding;
    @UnsupportedAppUsage
    private int mColumns;
    private boolean mEllipsized;
    private int mEllipsizedWidth;
    private int[] mLeftIndents;
    @UnsupportedAppUsage
    private int mLineCount;
    @UnsupportedAppUsage
    private Layout.Directions[] mLineDirections;
    @UnsupportedAppUsage
    private int[] mLines;
    private int mMaxLineHeight;
    @UnsupportedAppUsage
    private int mMaximumVisibleLineCount;
    private int[] mRightIndents;
    private int mTopPadding;

    private StaticLayout(Builder builder) {
        CharSequence charSequence = builder.mEllipsize == null ? builder.mText : (builder.mText instanceof Spanned ? new Layout.SpannedEllipsizer(builder.mText) : new Layout.Ellipsizer(builder.mText));
        super(charSequence, builder.mPaint, builder.mWidth, builder.mAlignment, builder.mTextDir, builder.mSpacingMult, builder.mSpacingAdd);
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        if (builder.mEllipsize != null) {
            charSequence = (Layout.Ellipsizer)this.getText();
            ((Layout.Ellipsizer)charSequence).mLayout = this;
            ((Layout.Ellipsizer)charSequence).mWidth = builder.mEllipsizedWidth;
            ((Layout.Ellipsizer)charSequence).mMethod = builder.mEllipsize;
            this.mEllipsizedWidth = builder.mEllipsizedWidth;
            this.mColumns = 7;
        } else {
            this.mColumns = 5;
            this.mEllipsizedWidth = builder.mWidth;
        }
        this.mLineDirections = ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
        this.mMaximumVisibleLineCount = builder.mMaxLines;
        this.mLeftIndents = builder.mLeftIndents;
        this.mRightIndents = builder.mRightIndents;
        this.setJustificationMode(builder.mJustificationMode);
        this.generate(builder, builder.mIncludePad, builder.mIncludePad);
    }

    StaticLayout(CharSequence charSequence) {
        super(charSequence, null, 0, null, 0.0f, 0.0f);
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = 7;
        this.mLineDirections = ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    }

    @Deprecated
    public StaticLayout(CharSequence charSequence, int n, int n2, TextPaint textPaint, int n3, Layout.Alignment alignment, float f, float f2, boolean bl) {
        this(charSequence, n, n2, textPaint, n3, alignment, f, f2, bl, null, 0);
    }

    @Deprecated
    public StaticLayout(CharSequence charSequence, int n, int n2, TextPaint textPaint, int n3, Layout.Alignment alignment, float f, float f2, boolean bl, TextUtils.TruncateAt truncateAt, int n4) {
        this(charSequence, n, n2, textPaint, n3, alignment, TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, bl, truncateAt, n4, Integer.MAX_VALUE);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=117521430L)
    public StaticLayout(CharSequence charSequence, int n, int n2, TextPaint object, int n3, Layout.Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean bl, TextUtils.TruncateAt truncateAt, int n4, int n5) {
        CharSequence charSequence2 = truncateAt == null ? charSequence : (charSequence instanceof Spanned ? new Layout.SpannedEllipsizer(charSequence) : new Layout.Ellipsizer(charSequence));
        super(charSequence2, (TextPaint)object, n3, alignment, textDirectionHeuristic, f, f2);
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        object = Builder.obtain(charSequence, n, n2, (TextPaint)object, n3).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setIncludePad(bl).setEllipsizedWidth(n4).setEllipsize(truncateAt).setMaxLines(n5);
        if (truncateAt != null) {
            charSequence = (Layout.Ellipsizer)this.getText();
            ((Layout.Ellipsizer)charSequence).mLayout = this;
            ((Layout.Ellipsizer)charSequence).mWidth = n4;
            ((Layout.Ellipsizer)charSequence).mMethod = truncateAt;
            this.mEllipsizedWidth = n4;
            this.mColumns = 7;
        } else {
            this.mColumns = 5;
            this.mEllipsizedWidth = n3;
        }
        this.mLineDirections = ArrayUtils.newUnpaddedArray(Layout.Directions.class, 2);
        this.mLines = ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
        this.mMaximumVisibleLineCount = n5;
        this.generate((Builder)object, ((Builder)object).mIncludePad, ((Builder)object).mIncludePad);
        Builder.recycle((Builder)object);
    }

    @Deprecated
    public StaticLayout(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, boolean bl) {
        this(charSequence, 0, charSequence.length(), textPaint, n, alignment, f, f2, bl);
    }

    private void calculateEllipsis(int n, int n2, MeasuredParagraph arrn, int n3, float f, TextUtils.TruncateAt truncateAt, int n4, float f2, TextPaint textPaint, boolean bl) {
        float f3 = f - this.getTotalInsets(n4);
        if (f2 <= f3 && !bl) {
            arrn = this.mLines;
            n = this.mColumns;
            arrn[n * n4 + 5] = 0;
            arrn[n * n4 + 6] = 0;
            return;
        }
        float f4 = textPaint.measureText(TextUtils.getEllipsisString(truncateAt));
        int n5 = 0;
        int n6 = 0;
        int n7 = n2 - n;
        if (truncateAt == TextUtils.TruncateAt.START) {
            if (this.mMaximumVisibleLineCount == 1) {
                f = 0.0f;
                n2 = n7;
                block0 : do {
                    n6 = --n2;
                    if (n2 <= 0) break;
                    f2 = arrn.getCharWidthAt(n2 - 1 + n - n3);
                    if (f2 + f + f4 > f3) {
                        do {
                            n6 = n2;
                            if (n2 >= n7) break block0;
                            n6 = n2;
                            if (arrn.getCharWidthAt(n2 + n - n3) != 0.0f) break block0;
                            ++n2;
                        } while (true);
                    }
                    f += f2;
                } while (true);
                n = 0;
                n2 = n6;
            } else {
                n = n5;
                n2 = n6;
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Start Ellipsis only supported with one line");
                    n = n5;
                    n2 = n6;
                }
            }
        } else if (truncateAt != TextUtils.TruncateAt.END && truncateAt != TextUtils.TruncateAt.MARQUEE && truncateAt != TextUtils.TruncateAt.END_SMALL) {
            if (this.mMaximumVisibleLineCount == 1) {
                f2 = 0.0f;
                f = 0.0f;
                n2 = n7;
                float f5 = (f3 - f4) / 2.0f;
                block2 : do {
                    n6 = --n2;
                    if (n2 <= 0) break;
                    float f6 = arrn.getCharWidthAt(n2 - 1 + n - n3);
                    if (f6 + f > f5) {
                        do {
                            n6 = n2;
                            if (n2 >= n7) break block2;
                            n6 = n2;
                            if (arrn.getCharWidthAt(n2 + n - n3) != 0.0f) break block2;
                            ++n2;
                        } while (true);
                    }
                    f += f6;
                } while (true);
                for (n2 = 0; n2 < n6 && !((f5 = arrn.getCharWidthAt(n2 + n - n3)) + f2 > f3 - f4 - f); ++n2) {
                    f2 += f5;
                }
                n = n2;
                n2 = n6 - n2;
            } else {
                n = n5;
                n2 = n6;
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Middle Ellipsis only supported with one line");
                    n = n5;
                    n2 = n6;
                }
            }
        } else {
            f = 0.0f;
            for (n2 = 0; n2 < n7 && !((f2 = arrn.getCharWidthAt(n2 + n - n3)) + f + f4 > f3); ++n2) {
                f += f2;
            }
            n3 = n2;
            n6 = n7 - n2;
            n = n3;
            n2 = n6;
            if (bl) {
                n = n3;
                n2 = n6;
                if (n6 == 0) {
                    n = n3;
                    n2 = n6;
                    if (n7 > 0) {
                        n = n7 - 1;
                        n2 = 1;
                    }
                }
            }
        }
        this.mEllipsized = true;
        arrn = this.mLines;
        n3 = this.mColumns;
        arrn[n3 * n4 + 5] = n;
        arrn[n3 * n4 + 6] = n2;
    }

    private float getTotalInsets(int n) {
        int n2 = 0;
        int[] arrn = this.mLeftIndents;
        if (arrn != null) {
            n2 = arrn[Math.min(n, arrn.length - 1)];
        }
        arrn = this.mRightIndents;
        int n3 = n2;
        if (arrn != null) {
            n3 = n2 + arrn[Math.min(n, arrn.length - 1)];
        }
        return n3;
    }

    private int out(CharSequence charSequence, int n, int n2, int n3, int n4, int n5, int n6, int n7, float f, float f2, LineHeightSpan[] arrlineHeightSpan, int[] arrn, Paint.FontMetricsInt fontMetricsInt, boolean bl, int n8, boolean bl2, MeasuredParagraph measuredParagraph, int n9, boolean bl3, boolean bl4, boolean bl5, char[] arrc, int n10, TextUtils.TruncateAt truncateAt, float f3, float f4, TextPaint textPaint, boolean bl6) {
        int n11;
        int n12;
        double d;
        int n13 = this.mLineCount;
        int n14 = this.mColumns;
        int n15 = n13 * n14;
        int n16 = 1;
        n14 = n15 + n14 + 1;
        Object[] arrobject = this.mLines;
        int n17 = measuredParagraph.getParagraphDir();
        if (n14 >= arrobject.length) {
            arrc = ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(n14));
            System.arraycopy(arrobject, 0, arrc, 0, arrobject.length);
            this.mLines = arrc;
        } else {
            arrc = arrobject;
        }
        if (n13 >= this.mLineDirections.length) {
            arrobject = ArrayUtils.newUnpaddedArray(Layout.Directions.class, GrowingArrayUtils.growSize(n13));
            Layout.Directions[] arrdirections = this.mLineDirections;
            System.arraycopy(arrdirections, 0, arrobject, 0, arrdirections.length);
            this.mLineDirections = arrobject;
        }
        if (arrlineHeightSpan != null) {
            fontMetricsInt.ascent = n3;
            fontMetricsInt.descent = n4;
            fontMetricsInt.top = n5;
            fontMetricsInt.bottom = n6;
            n3 = n13;
            n4 = n16;
            n5 = n14;
            for (n6 = 0; n6 < arrlineHeightSpan.length; ++n6) {
                if (arrlineHeightSpan[n6] instanceof LineHeightSpan.WithDensity) {
                    ((LineHeightSpan.WithDensity)arrlineHeightSpan[n6]).chooseHeight(charSequence, n, n2, arrn[n6], n7, fontMetricsInt, textPaint);
                    continue;
                }
                arrlineHeightSpan[n6].chooseHeight(charSequence, n, n2, arrn[n6], n7, fontMetricsInt);
            }
            n5 = n4;
            n13 = n3;
            n6 = fontMetricsInt.ascent;
            n16 = fontMetricsInt.descent;
            n14 = fontMetricsInt.top;
            n4 = fontMetricsInt.bottom;
            n3 = n5;
            n5 = n16;
        } else {
            n12 = 1;
            n16 = n4;
            n4 = n6;
            n14 = n5;
            n5 = n16;
            n6 = n3;
            n3 = n12;
        }
        int n18 = 0;
        n12 = n13 == 0 ? n3 : 0;
        n16 = n13 + 1 == this.mMaximumVisibleLineCount ? n3 : 0;
        if (truncateAt != null) {
            n11 = bl6 && this.mLineCount + n3 == this.mMaximumVisibleLineCount ? n3 : 0;
            if (!((this.mMaximumVisibleLineCount == n3 && bl6 || n12 != 0 && !bl6) && truncateAt != TextUtils.TruncateAt.MARQUEE || n12 == 0 && (n16 != 0 || !bl6) && truncateAt == TextUtils.TruncateAt.END)) {
                n3 = 0;
            }
            if (n3 != 0) {
                this.calculateEllipsis(n, n2, measuredParagraph, n10, f3, truncateAt, n13, f4, textPaint, n11 != 0);
            }
        }
        if (this.mEllipsized) {
            n3 = 1;
        } else {
            n3 = n10 != n9 && n9 > 0 && charSequence.charAt(n9 - 1) == '\n' ? 1 : 0;
            n3 = n2 == n9 && n3 == 0 ? 1 : (n == n9 && n3 != 0 ? 1 : 0);
        }
        n11 = n;
        n9 = n6;
        if (n12 != 0) {
            if (bl4) {
                this.mTopPadding = n14 - n6;
            }
            n9 = n6;
            if (bl3) {
                n9 = n14;
            }
        }
        n = n5;
        if (n3 != 0) {
            if (bl4) {
                this.mBottomPadding = n4 - n5;
            }
            n = n5;
            if (bl3) {
                n = n4;
            }
        }
        n3 = bl2 && (bl5 || n3 == 0) ? ((d = (double)((float)(n - n9) * (f - 1.0f) + f2)) >= 0.0 ? (int)(0.5 + d) : -((int)(-d + 0.5))) : 0;
        arrc[n15 + 0] = n11;
        arrc[n15 + 1] = n7;
        arrc[n15 + 2] = n + n3;
        arrc[n15 + 3] = (char)n3;
        if (!this.mEllipsized && n16 != 0) {
            if (!bl3) {
                n4 = n;
            }
            this.mMaxLineHeight = n7 + (n4 - n9);
        }
        n3 = n7 + (n - n9 + n3);
        n = this.mColumns;
        arrc[n15 + n + 0] = n2;
        arrc[n15 + n + 1] = (char)n3;
        n4 = n15 + 0;
        n5 = arrc[n4];
        n = n18;
        if (bl) {
            n = 536870912;
        }
        arrc[n4] = n5 | n;
        arrc[n15 + 4] = n8;
        n = n15 + 0;
        arrc[n] = arrc[n] | n17 << 30;
        this.mLineDirections[n13] = measuredParagraph.getDirections(n11 - n10, n2 - n10);
        ++this.mLineCount;
        return n3;
    }

    static int packHyphenEdit(int n, int n2) {
        return n << 3 | n2;
    }

    static int unpackEndHyphenEdit(int n) {
        return n & 7;
    }

    static int unpackStartHyphenEdit(int n) {
        return (n & 24) >> 3;
    }

    void generate(Builder object, boolean bl, boolean bl2) {
        block51 : {
            boolean bl3;
            float f;
            Object object2;
            Object object3;
            Object object4;
            int n;
            float f2;
            Object object5;
            float f3;
            int n2;
            boolean bl4;
            TextUtils.TruncateAt truncateAt;
            Object object6;
            int n3;
            block45 : {
                Object object7;
                object3 = this;
                int[] arrn = ((Builder)object).mText;
                int n4 = ((Builder)object).mStart;
                int n5 = ((Builder)object).mEnd;
                Object object8 = ((Builder)object).mPaint;
                int n6 = ((Builder)object).mWidth;
                object4 = ((Builder)object).mTextDir;
                boolean bl5 = ((Builder)object).mFallbackLineSpacing;
                f = ((Builder)object).mSpacingMult;
                f3 = ((Builder)object).mSpacingAdd;
                f2 = ((Builder)object).mEllipsizedWidth;
                truncateAt = ((Builder)object).mEllipsize;
                bl3 = ((Builder)object).mAddLastLineLineSpacing;
                int n7 = 0;
                object6 = null;
                float[] arrf = null;
                float[] arrf2 = null;
                float[] arrf3 = null;
                boolean[] arrbl = null;
                int[] arrn2 = null;
                object3.mLineCount = 0;
                object3.mEllipsized = false;
                n = object3.mMaximumVisibleLineCount < 1 ? 0 : -1;
                object3.mMaxLineHeight = n;
                int n8 = 0;
                bl4 = f != 1.0f || f3 != 0.0f;
                Object object9 = ((Builder)object).mFontMetricsInt;
                if (object3.mLeftIndents == null && object3.mRightIndents == null) {
                    object7 = null;
                } else {
                    object7 = object3.mLeftIndents;
                    n = object7 == null ? 0 : ((int[])object7).length;
                    object7 = object3.mRightIndents;
                    n3 = object7 == null ? 0 : ((int[])object7).length;
                    object7 = new int[Math.max(n, n3)];
                    for (n2 = 0; n2 < n; ++n2) {
                        object7[n2] = object3.mLeftIndents[n2];
                    }
                    for (n2 = 0; n2 < n3; ++n2) {
                        object7[n2] = object7[n2] + object3.mRightIndents[n2];
                    }
                }
                Object object10 = new LineBreaker.Builder().setBreakStrategy(((Builder)object).mBreakStrategy).setHyphenationFrequency(((Builder)object).mHyphenationFrequency).setJustificationMode(((Builder)object).mJustificationMode).setIndents((int[])object7).build();
                Object object11 = new LineBreaker.ParagraphConstraints();
                object7 = null;
                Object object12 = arrn instanceof Spanned ? (Spanned)arrn : null;
                boolean bl6 = arrn instanceof PrecomputedText;
                if (bl6 && (n = ((PrecomputedText)(object5 = (int[])arrn)).checkResultUsable(n4, n5, (TextDirectionHeuristic)object4, (TextPaint)object8, ((Builder)object).mBreakStrategy, ((Builder)object).mHyphenationFrequency)) != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            object7 = ((PrecomputedText)object5).getParagraphInfo();
                        }
                    } else {
                        object7 = PrecomputedText.create((CharSequence)object5, new PrecomputedText.Params.Builder((TextPaint)object8).setBreakStrategy(((Builder)object).mBreakStrategy).setHyphenationFrequency(((Builder)object).mHyphenationFrequency).setTextDirection((TextDirectionHeuristic)object4).build()).getParagraphInfo();
                    }
                }
                object5 = object7 == null ? PrecomputedText.createMeasuredParagraphs((CharSequence)arrn, new PrecomputedText.Params((TextPaint)object8, (TextDirectionHeuristic)object4, ((Builder)object).mBreakStrategy, ((Builder)object).mHyphenationFrequency), n4, n5, false) : object7;
                int n9 = 0;
                object = null;
                object2 = object12;
                object7 = arrn;
                n = n4;
                n2 = n5;
                object12 = object8;
                while (n9 < ((int[])object5).length) {
                    int n10;
                    Object object13;
                    int n11;
                    int n12;
                    float[] arrf4;
                    int n13;
                    block48 : {
                        block46 : {
                            block50 : {
                                block49 : {
                                    block47 : {
                                        n3 = n9 == 0 ? n : ((PrecomputedText.ParagraphInfo)object5[n9 - 1]).paragraphEnd;
                                        n13 = ((PrecomputedText.ParagraphInfo)object5[n9]).paragraphEnd;
                                        n4 = n6;
                                        n5 = n6;
                                        if (object2 == null) break block46;
                                        object8 = StaticLayout.getParagraphSpans((Spanned)object2, n3, n13, LeadingMarginSpan.class);
                                        n12 = 1;
                                        n10 = 0;
                                        n11 = n;
                                        n = n12;
                                        do {
                                            arrn = object12;
                                            if (n10 >= ((LeadingMarginSpan[])object8).length) break;
                                            object12 = object8[n10];
                                            n4 -= object8[n10].getLeadingMargin(true);
                                            n12 = n5 - object8[n10].getLeadingMargin(false);
                                            n5 = n;
                                            if (object12 instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                                                object12 = (LeadingMarginSpan.LeadingMarginSpan2)object12;
                                                n5 = Math.max(n, object12.getLeadingMarginLineCount());
                                            }
                                            ++n10;
                                            object12 = arrn;
                                            n = n5;
                                            n5 = n12;
                                        } while (true);
                                        n10 = n11;
                                        object13 = object7;
                                        object8 = StaticLayout.getParagraphSpans((Spanned)object2, n3, n13, LineHeightSpan.class);
                                        if (((android.text.style.ParagraphStyle[])object8).length != 0) break block47;
                                        object8 = null;
                                        n11 = n4;
                                        n4 = n5;
                                        n5 = n10;
                                        object7 = object;
                                        break block48;
                                    }
                                    if (object == null) break block49;
                                    object7 = object;
                                    if (((TabStopSpan[])object).length >= ((android.text.style.ParagraphStyle[])object8).length) break block50;
                                }
                                object7 = ArrayUtils.newUnpaddedIntArray(((android.text.style.ParagraphStyle[])object8).length);
                            }
                            for (n11 = 0; n11 < ((android.text.style.ParagraphStyle[])object8).length; ++n11) {
                                n12 = object2.getSpanStart(object8[n11]);
                                object7[n11] = n12 < n3 ? object3.getLineTop(object3.getLineForOffset(n12)) : n8;
                            }
                            n11 = n4;
                            n4 = n5;
                            n5 = n10;
                            break block48;
                        }
                        object13 = object7;
                        n10 = 1;
                        n11 = n4;
                        n4 = n5;
                        object8 = null;
                        object7 = object;
                        n5 = n;
                        arrn = object12;
                        n = n10;
                    }
                    Object object14 = object4;
                    bl6 = false;
                    if (object2 != null && ((TabStopSpan[])(object = StaticLayout.getParagraphSpans((Spanned)object2, n3, n13, TabStopSpan.class))).length > 0) {
                        arrf4 = new float[((TabStopSpan[])object).length];
                        for (n10 = 0; n10 < ((TabStopSpan[])object).length; ++n10) {
                            arrf4[n10] = object[n10].getTabStop();
                        }
                        Arrays.sort(arrf4, 0, arrf4.length);
                    } else {
                        arrf4 = null;
                    }
                    MeasuredParagraph measuredParagraph = ((PrecomputedText.ParagraphInfo)object5[n9]).measured;
                    char[] arrc = measuredParagraph.getChars();
                    int[] arrn3 = measuredParagraph.getSpanEndCache().getRawArray();
                    int[] arrn4 = measuredParagraph.getFontMetrics().getRawArray();
                    float f4 = n4;
                    object = object11;
                    ((LineBreaker.ParagraphConstraints)object).setWidth(f4);
                    ((LineBreaker.ParagraphConstraints)object).setIndent(n11, n);
                    ((LineBreaker.ParagraphConstraints)object).setTabStops(arrf4, 20.0f);
                    object12 = measuredParagraph.getMeasuredText();
                    int n14 = n9;
                    n9 = object3.mLineCount;
                    int n15 = n;
                    Object object15 = object10;
                    object4 = object15.computeLineBreaks((MeasuredText)object12, (LineBreaker.ParagraphConstraints)object, n9);
                    n9 = ((LineBreaker.Result)object4).getLineCount();
                    if (n7 < n9) {
                        object12 = new int[n9];
                        arrf = new float[n9];
                        arrf2 = new float[n9];
                        arrf3 = new float[n9];
                        arrbl = new boolean[n9];
                        arrn2 = new int[n9];
                        n7 = n9;
                    } else {
                        object12 = object6;
                    }
                    for (n = 0; n < n9; ++n) {
                        object12[n] = ((LineBreaker.Result)object4).getLineBreakOffset(n);
                        arrf[n] = ((LineBreaker.Result)object4).getLineWidth(n);
                        arrf2[n] = ((LineBreaker.Result)object4).getLineAscent(n);
                        arrf3[n] = ((LineBreaker.Result)object4).getLineDescent(n);
                        arrbl[n] = ((LineBreaker.Result)object4).hasLineTab(n);
                        arrn2[n] = StaticLayout.packHyphenEdit(((LineBreaker.Result)object4).getStartLineHyphenEdit(n), ((LineBreaker.Result)object4).getEndLineHyphenEdit(n));
                    }
                    Object object16 = object;
                    n10 = object3.mMaximumVisibleLineCount - object3.mLineCount;
                    n = truncateAt != null && (truncateAt == TextUtils.TruncateAt.END || object3.mMaximumVisibleLineCount == 1 && truncateAt != TextUtils.TruncateAt.MARQUEE) ? 1 : 0;
                    if (n10 > 0 && n10 < n9 && n != 0) {
                        f4 = 0.0f;
                        bl6 = false;
                        object = object2;
                        object6 = object4;
                        for (n12 = n10 - 1; n12 < n9; ++n12) {
                            if (n12 == n9 - 1) {
                                f4 += arrf[n12];
                            } else {
                                float f5;
                                if (n12 == 0) {
                                    n = 0;
                                    f5 = f4;
                                } else {
                                    n = object12[n12 - 1];
                                    f5 = f4;
                                }
                                do {
                                    object4 = object;
                                    f4 = f5;
                                    object = object4;
                                    if (n >= object12[n12]) break;
                                    f5 += measuredParagraph.getCharWidthAt(n);
                                    ++n;
                                    object = object4;
                                } while (true);
                            }
                            bl6 |= arrbl[n12];
                        }
                        object12[n10 - 1] = object12[n9 - 1];
                        arrf[n10 - 1] = f4;
                        arrbl[n10 - 1] = bl6;
                        n9 = n10;
                        object11 = object6;
                        object10 = object;
                    } else {
                        object10 = object2;
                        object11 = object4;
                    }
                    int n16 = 0;
                    int n17 = 0;
                    n12 = n3;
                    int n18 = 0;
                    int n19 = n3;
                    int n20 = 0;
                    int n21 = n10;
                    int n22 = 0;
                    int n23 = 0;
                    int n24 = 0;
                    object2 = object9;
                    object9 = object5;
                    object6 = object13;
                    n = n5;
                    object5 = arrn;
                    object4 = object14;
                    object = object16;
                    n5 = n8;
                    n8 = n20;
                    n10 = n12;
                    object13 = object3;
                    object3 = object15;
                    int n25 = n11;
                    n20 = n4;
                    arrn = measuredParagraph;
                    n4 = n24;
                    n11 = n22;
                    n12 = n3;
                    n3 = n18;
                    while (n19 < n13) {
                        int n26 = arrn3[n17];
                        int n27 = 0;
                        int n28 = n13;
                        n13 = arrn4[n16 * 4 + 0];
                        object14 = object2;
                        ((Paint.FontMetricsInt)object14).top = n13;
                        int n29 = 1;
                        ((Paint.FontMetricsInt)object14).bottom = arrn4[n16 * 4 + 1];
                        int n30 = 2;
                        ((Paint.FontMetricsInt)object14).ascent = arrn4[n16 * 4 + 2];
                        ((Paint.FontMetricsInt)object14).descent = arrn4[n16 * 4 + 3];
                        n18 = n3;
                        if (((Paint.FontMetricsInt)object14).top < n3) {
                            n18 = ((Paint.FontMetricsInt)object14).top;
                        }
                        n13 = n11;
                        if (((Paint.FontMetricsInt)object14).ascent < n11) {
                            n13 = ((Paint.FontMetricsInt)object14).ascent;
                        }
                        n11 = n4;
                        if (((Paint.FontMetricsInt)object14).descent > n4) {
                            n11 = ((Paint.FontMetricsInt)object14).descent;
                        }
                        if (((Paint.FontMetricsInt)object14).bottom > n23) {
                            n23 = ((Paint.FontMetricsInt)object14).bottom;
                            n3 = n8;
                        } else {
                            n3 = n8;
                        }
                        while (n3 < n9 && n12 + object12[n3] < n19) {
                            ++n3;
                        }
                        int n31 = n13;
                        n8 = n3;
                        n22 = n18;
                        n3 = n11;
                        n18 = n23;
                        n24 = n10;
                        object2 = object11;
                        n11 = n28;
                        n28 = n5;
                        n5 = n;
                        object13 = object;
                        n = n27;
                        n23 = n29;
                        n10 = n26;
                        object = object14;
                        n4 = n2;
                        n13 = n20;
                        n20 = n8;
                        n8 = n12;
                        n2 = n31;
                        while (n20 < n9 && object12[n20] + n8 <= n10) {
                            n31 = n8 + object12[n20];
                            n12 = n31 < n4 ? n23 : n;
                            if (bl5) {
                                n2 = Math.min(n2, Math.round(arrf2[n20]));
                            }
                            if (bl5) {
                                n3 = Math.max(n3, Math.round(arrf3[n20]));
                            }
                            n28 = this.out((CharSequence)object6, n24, n31, n2, n3, n22, n18, n28, f, f3, (LineHeightSpan[])object8, (int[])object7, (Paint.FontMetricsInt)object, arrbl[n20], arrn2[n20], bl4, (MeasuredParagraph)arrn, n4, bl, bl2, bl3, arrc, n8, truncateAt, f2, arrf[n20], (TextPaint)object5, n12 != 0);
                            if (n31 < n10) {
                                object11 = object;
                                n22 = ((Paint.FontMetricsInt)object11).top;
                                n18 = ((Paint.FontMetricsInt)object11).bottom;
                                n2 = ((Paint.FontMetricsInt)object11).ascent;
                                n3 = ((Paint.FontMetricsInt)object11).descent;
                            } else {
                                n2 = n;
                                n3 = n;
                                n18 = n;
                                n22 = n;
                            }
                            n24 = n31;
                            ++n20;
                            if (this.mLineCount < this.mMaximumVisibleLineCount || !this.mEllipsized) continue;
                            return;
                        }
                        object14 = this;
                        n = n13;
                        ++n16;
                        n30 = n17 + 1;
                        n12 = n22;
                        n23 = n18;
                        object15 = object13;
                        n13 = n11;
                        n17 = n3;
                        n11 = n2;
                        object11 = object2;
                        n22 = n20;
                        n2 = n4;
                        n18 = n5;
                        object2 = object;
                        n3 = n12;
                        n12 = n8;
                        n19 = n10;
                        n4 = n17;
                        n20 = n;
                        object13 = object14;
                        n17 = n30;
                        n10 = n24;
                        n8 = n22;
                        n5 = n28;
                        object = object15;
                        n = n18;
                    }
                    if (n13 == n2) {
                        n3 = n5;
                        object = object12;
                        object3 = object13;
                        break block45;
                    }
                    object8 = object2;
                    arrn = object12;
                    object2 = object10;
                    object11 = object;
                    object12 = object5;
                    object10 = object3;
                    n9 = n14 + 1;
                    object = object7;
                    object5 = object9;
                    object7 = object6;
                    object3 = object13;
                    object6 = arrn;
                    n8 = n5;
                    object9 = object8;
                }
                object = object6;
                n3 = n8;
                object6 = object7;
                object5 = object12;
                object2 = object9;
            }
            if (n2 != n && object6.charAt(n2 - 1) != '\n') break block51;
            if (object3.mLineCount < object3.mMaximumVisibleLineCount) {
                object = MeasuredParagraph.buildForBidi((CharSequence)object6, n2, n2, (TextDirectionHeuristic)object4, null);
                ((Paint)object5).getFontMetricsInt((Paint.FontMetricsInt)object2);
                this.out((CharSequence)object6, n2, n2, ((Paint.FontMetricsInt)object2).ascent, ((Paint.FontMetricsInt)object2).descent, ((Paint.FontMetricsInt)object2).top, ((Paint.FontMetricsInt)object2).bottom, n3, f, f3, null, null, (Paint.FontMetricsInt)object2, false, 0, bl4, (MeasuredParagraph)object, n2, bl, bl2, bl3, null, n, truncateAt, f2, 0.0f, (TextPaint)object5, false);
            }
        }
    }

    @Override
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    @Override
    public int getEllipsisCount(int n) {
        int n2 = this.mColumns;
        if (n2 < 7) {
            return 0;
        }
        return this.mLines[n2 * n + 6];
    }

    @Override
    public int getEllipsisStart(int n) {
        int n2 = this.mColumns;
        if (n2 < 7) {
            return 0;
        }
        return this.mLines[n2 * n + 5];
    }

    @Override
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @Override
    public int getEndHyphenEdit(int n) {
        return StaticLayout.unpackEndHyphenEdit(this.mLines[this.mColumns * n + 4] & 255);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    @Override
    public int getHeight(boolean bl) {
        int n;
        if (bl && this.mLineCount > this.mMaximumVisibleLineCount && this.mMaxLineHeight == -1 && Log.isLoggable(TAG, 5)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("maxLineHeight should not be -1.  maxLines:");
            stringBuilder.append(this.mMaximumVisibleLineCount);
            stringBuilder.append(" lineCount:");
            stringBuilder.append(this.mLineCount);
            Log.w(TAG, stringBuilder.toString());
        }
        if (!bl || this.mLineCount <= this.mMaximumVisibleLineCount || (n = this.mMaxLineHeight) == -1) {
            n = super.getHeight();
        }
        return n;
    }

    @Override
    public int getIndentAdjust(int n, Layout.Alignment arrn) {
        if (arrn == Layout.Alignment.ALIGN_LEFT) {
            arrn = this.mLeftIndents;
            if (arrn == null) {
                return 0;
            }
            return arrn[Math.min(n, arrn.length - 1)];
        }
        if (arrn == Layout.Alignment.ALIGN_RIGHT) {
            arrn = this.mRightIndents;
            if (arrn == null) {
                return 0;
            }
            return -arrn[Math.min(n, arrn.length - 1)];
        }
        if (arrn == Layout.Alignment.ALIGN_CENTER) {
            int n2 = 0;
            arrn = this.mLeftIndents;
            if (arrn != null) {
                n2 = arrn[Math.min(n, arrn.length - 1)];
            }
            int n3 = 0;
            arrn = this.mRightIndents;
            if (arrn != null) {
                n3 = arrn[Math.min(n, arrn.length - 1)];
            }
            return n2 - n3 >> 1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unhandled alignment ");
        stringBuilder.append(arrn);
        throw new AssertionError((Object)stringBuilder.toString());
    }

    @Override
    public boolean getLineContainsTab(int n) {
        int[] arrn = this.mLines;
        int n2 = this.mColumns;
        boolean bl = false;
        if ((arrn[n2 * n + 0] & 536870912) != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public int getLineCount() {
        return this.mLineCount;
    }

    @Override
    public int getLineDescent(int n) {
        return this.mLines[this.mColumns * n + 2];
    }

    @Override
    public final Layout.Directions getLineDirections(int n) {
        if (n <= this.getLineCount()) {
            return this.mLineDirections[n];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public int getLineExtra(int n) {
        return this.mLines[this.mColumns * n + 3];
    }

    @Override
    public int getLineForVertical(int n) {
        int n2 = this.mLineCount;
        int n3 = -1;
        int[] arrn = this.mLines;
        while (n2 - n3 > 1) {
            int n4 = n2 + n3 >> 1;
            if (arrn[this.mColumns * n4 + 1] > n) {
                n2 = n4;
                continue;
            }
            n3 = n4;
        }
        if (n3 < 0) {
            return 0;
        }
        return n3;
    }

    @Override
    public int getLineStart(int n) {
        return this.mLines[this.mColumns * n + 0] & 536870911;
    }

    @Override
    public int getLineTop(int n) {
        return this.mLines[this.mColumns * n + 1];
    }

    @Override
    public int getParagraphDirection(int n) {
        return this.mLines[this.mColumns * n + 0] >> 30;
    }

    @Override
    public int getStartHyphenEdit(int n) {
        return StaticLayout.unpackStartHyphenEdit(this.mLines[this.mColumns * n + 4] & 255);
    }

    @Override
    public int getTopPadding() {
        return this.mTopPadding;
    }

    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool(3);
        private boolean mAddLastLineLineSpacing;
        private Layout.Alignment mAlignment;
        private int mBreakStrategy;
        private TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private int mEnd;
        private boolean mFallbackLineSpacing;
        private final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private int[] mLeftIndents;
        private int mMaxLines;
        private TextPaint mPaint;
        private int[] mRightIndents;
        private float mSpacingAdd;
        private float mSpacingMult;
        private int mStart;
        private CharSequence mText;
        private TextDirectionHeuristic mTextDir;
        private int mWidth;

        private Builder() {
        }

        public static Builder obtain(CharSequence charSequence, int n, int n2, TextPaint textPaint, int n3) {
            Builder builder;
            Builder builder2 = builder = sPool.acquire();
            if (builder == null) {
                builder2 = new Builder();
            }
            builder2.mText = charSequence;
            builder2.mStart = n;
            builder2.mEnd = n2;
            builder2.mPaint = textPaint;
            builder2.mWidth = n3;
            builder2.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            builder2.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            builder2.mSpacingMult = 1.0f;
            builder2.mSpacingAdd = 0.0f;
            builder2.mIncludePad = true;
            builder2.mFallbackLineSpacing = false;
            builder2.mEllipsizedWidth = n3;
            builder2.mEllipsize = null;
            builder2.mMaxLines = Integer.MAX_VALUE;
            builder2.mBreakStrategy = 0;
            builder2.mHyphenationFrequency = 0;
            builder2.mJustificationMode = 0;
            return builder2;
        }

        private static void recycle(Builder builder) {
            builder.mPaint = null;
            builder.mText = null;
            builder.mLeftIndents = null;
            builder.mRightIndents = null;
            sPool.release(builder);
        }

        public StaticLayout build() {
            StaticLayout staticLayout = new StaticLayout(this);
            Builder.recycle(this);
            return staticLayout;
        }

        void finish() {
            this.mText = null;
            this.mPaint = null;
            this.mLeftIndents = null;
            this.mRightIndents = null;
        }

        Builder setAddLastLineLineSpacing(boolean bl) {
            this.mAddLastLineLineSpacing = bl;
            return this;
        }

        public Builder setAlignment(Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public Builder setBreakStrategy(int n) {
            this.mBreakStrategy = n;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt truncateAt) {
            this.mEllipsize = truncateAt;
            return this;
        }

        public Builder setEllipsizedWidth(int n) {
            this.mEllipsizedWidth = n;
            return this;
        }

        public Builder setHyphenationFrequency(int n) {
            this.mHyphenationFrequency = n;
            return this;
        }

        public Builder setIncludePad(boolean bl) {
            this.mIncludePad = bl;
            return this;
        }

        public Builder setIndents(int[] arrn, int[] arrn2) {
            this.mLeftIndents = arrn;
            this.mRightIndents = arrn2;
            return this;
        }

        public Builder setJustificationMode(int n) {
            this.mJustificationMode = n;
            return this;
        }

        public Builder setLineSpacing(float f, float f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            return this;
        }

        public Builder setMaxLines(int n) {
            this.mMaxLines = n;
            return this;
        }

        public Builder setPaint(TextPaint textPaint) {
            this.mPaint = textPaint;
            return this;
        }

        public Builder setText(CharSequence charSequence) {
            return this.setText(charSequence, 0, charSequence.length());
        }

        public Builder setText(CharSequence charSequence, int n, int n2) {
            this.mText = charSequence;
            this.mStart = n;
            this.mEnd = n2;
            return this;
        }

        public Builder setTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public Builder setUseLineSpacingFromFallbacks(boolean bl) {
            this.mFallbackLineSpacing = bl;
            return this;
        }

        public Builder setWidth(int n) {
            this.mWidth = n;
            if (this.mEllipsize == null) {
                this.mEllipsizedWidth = n;
            }
            return this;
        }
    }

    static class LineBreaks {
        private static final int INITIAL_SIZE = 16;
        @UnsupportedAppUsage
        public float[] ascents = new float[16];
        @UnsupportedAppUsage
        public int[] breaks = new int[16];
        @UnsupportedAppUsage
        public float[] descents = new float[16];
        @UnsupportedAppUsage
        public int[] flags = new int[16];
        @UnsupportedAppUsage
        public float[] widths = new float[16];

        LineBreaks() {
        }
    }

}

