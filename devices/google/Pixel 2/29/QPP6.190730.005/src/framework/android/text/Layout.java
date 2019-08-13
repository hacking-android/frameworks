/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.GetChars;
import android.text.MeasuredParagraph;
import android.text.SpanSet;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextLine;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text._$$Lambda$Layout$MzjK2UE2G8VG0asK8_KWY3gHAmY;
import android.text.method.TextKeyListener;
import android.text.style.AlignmentSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.ParagraphStyle;
import android.text.style.ReplacementSpan;
import android.text.style.TabStopSpan;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class Layout {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final float DEFAULT_LINESPACING_ADDITION = 0.0f;
    public static final float DEFAULT_LINESPACING_MULTIPLIER = 1.0f;
    @UnsupportedAppUsage
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static final Directions DIRS_ALL_LEFT_TO_RIGHT;
    @UnsupportedAppUsage
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static final Directions DIRS_ALL_RIGHT_TO_LEFT;
    public static final int DIR_LEFT_TO_RIGHT = 1;
    @UnsupportedAppUsage
    static final int DIR_REQUEST_DEFAULT_LTR = 2;
    static final int DIR_REQUEST_DEFAULT_RTL = -2;
    static final int DIR_REQUEST_LTR = 1;
    static final int DIR_REQUEST_RTL = -1;
    public static final int DIR_RIGHT_TO_LEFT = -1;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    private static final ParagraphStyle[] NO_PARA_SPANS;
    static final int RUN_LENGTH_MASK = 67108863;
    static final int RUN_LEVEL_MASK = 63;
    static final int RUN_LEVEL_SHIFT = 26;
    static final int RUN_RTL_FLAG = 67108864;
    private static final float TAB_INCREMENT = 20.0f;
    public static final int TEXT_SELECTION_LAYOUT_LEFT_TO_RIGHT = 1;
    public static final int TEXT_SELECTION_LAYOUT_RIGHT_TO_LEFT = 0;
    private static final Rect sTempRect;
    private Alignment mAlignment = Alignment.ALIGN_NORMAL;
    private int mJustificationMode;
    private SpanSet<LineBackgroundSpan> mLineBackgroundSpans;
    @UnsupportedAppUsage
    private TextPaint mPaint;
    private float mSpacingAdd;
    private float mSpacingMult;
    private boolean mSpannedText;
    private CharSequence mText;
    private TextDirectionHeuristic mTextDir;
    private int mWidth;
    private TextPaint mWorkPaint = new TextPaint();

    static {
        NO_PARA_SPANS = ArrayUtils.emptyArray(ParagraphStyle.class);
        sTempRect = new Rect();
        DIRS_ALL_LEFT_TO_RIGHT = new Directions(new int[]{0, 67108863});
        DIRS_ALL_RIGHT_TO_LEFT = new Directions(new int[]{0, 134217727});
    }

    protected Layout(CharSequence charSequence, TextPaint textPaint, int n, Alignment alignment, float f, float f2) {
        this(charSequence, textPaint, n, alignment, TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2);
    }

    protected Layout(CharSequence charSequence, TextPaint textPaint, int n, Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2) {
        if (n >= 0) {
            if (textPaint != null) {
                textPaint.bgColor = 0;
                textPaint.baselineShift = 0;
            }
            this.mText = charSequence;
            this.mPaint = textPaint;
            this.mWidth = n;
            this.mAlignment = alignment;
            this.mSpacingMult = f;
            this.mSpacingAdd = f2;
            this.mSpannedText = charSequence instanceof Spanned;
            this.mTextDir = textDirectionHeuristic;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Layout: ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" < 0");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    private void addSelection(int n, int n2, int n3, int n4, int n5, SelectionRectangleConsumer selectionRectangleConsumer) {
        int n6 = this.getLineStart(n);
        int n7 = this.getLineEnd(n);
        Directions directions = this.getLineDirections(n);
        int n8 = n7;
        if (n7 > n6) {
            n8 = n7;
            if (this.mText.charAt(n7 - 1) == '\n') {
                n8 = n7 - 1;
            }
        }
        n7 = 0;
        do {
            int n9;
            int n10 = n;
            Layout layout2 = this;
            if (n7 >= directions.mDirections.length) break;
            int n11 = directions.mDirections[n7] + n6;
            int n12 = n9 = (directions.mDirections[n7 + 1] & 67108863) + n11;
            if (n9 > n8) {
                n12 = n8;
            }
            if (n2 <= n12 && n3 >= n11 && (n9 = Math.max(n2, n11)) != (n12 = Math.min(n3, n12))) {
                float f = layout2.getHorizontal(n9, false, n10, false);
                float f2 = layout2.getHorizontal(n12, true, n10, false);
                float f3 = Math.min(f, f2);
                f = Math.max(f, f2);
                n12 = (directions.mDirections[n7 + 1] & 67108864) != 0 ? 0 : 1;
                selectionRectangleConsumer.accept(f3, n4, f, n5, n12);
            }
            n7 += 2;
        } while (true);
    }

    private void ellipsize(int n, int n2, int n3, char[] arrc, int n4, TextUtils.TruncateAt object) {
        int n5 = this.getEllipsisCount(n3);
        if (n5 == 0) {
            return;
        }
        int n6 = this.getEllipsisStart(n3);
        int n7 = this.getLineStart(n3);
        int n8 = ((String)(object = TextUtils.getEllipsisString((TextUtils.TruncateAt)((Object)object)))).length();
        n3 = n5 >= n8 ? 1 : 0;
        for (int i = 0; i < n5; ++i) {
            int n9 = n3 != 0 && i < n8 ? (int)((String)object).charAt(i) : 65279;
            int n10 = i + n6 + n7;
            if (n > n10 || n10 >= n2) continue;
            arrc[n4 + n10 - n] = (char)n9;
        }
    }

    public static float getDesiredWidth(CharSequence charSequence, int n, int n2, TextPaint textPaint) {
        return Layout.getDesiredWidth(charSequence, n, n2, textPaint, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    public static float getDesiredWidth(CharSequence charSequence, int n, int n2, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic) {
        return Layout.getDesiredWidthWithLimit(charSequence, n, n2, textPaint, textDirectionHeuristic, Float.MAX_VALUE);
    }

    public static float getDesiredWidth(CharSequence charSequence, TextPaint textPaint) {
        return Layout.getDesiredWidth(charSequence, 0, charSequence.length(), textPaint);
    }

    public static float getDesiredWidthWithLimit(CharSequence charSequence, int n, int n2, TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, float f) {
        float f2 = 0.0f;
        while (n <= n2) {
            float f3;
            int n3;
            int n4 = n3 = TextUtils.indexOf(charSequence, '\n', n, n2);
            if (n3 < 0) {
                n4 = n2;
            }
            if ((f3 = Layout.measurePara(textPaint, charSequence, n, n4, textDirectionHeuristic)) > f) {
                return f;
            }
            float f4 = f2;
            if (f3 > f2) {
                f4 = f3;
            }
            n = n4 + 1;
            f2 = f4;
        }
        return f2;
    }

    private float getHorizontal(int n, boolean bl) {
        float f = bl ? this.getPrimaryHorizontal(n) : this.getSecondaryHorizontal(n);
        return f;
    }

    private float getHorizontal(int n, boolean bl, int n2, boolean bl2) {
        Object object;
        int n3 = this.getLineStart(n2);
        int n4 = this.getLineEnd(n2);
        int n5 = this.getParagraphDirection(n2);
        boolean bl3 = this.getLineContainsTab(n2);
        Directions directions = this.getLineDirections(n2);
        object = bl3 && (object = this.mText) instanceof Spanned && ((TabStopSpan[])(object = Layout.getParagraphSpans((Spanned)object, n3, n4, TabStopSpan.class))).length > 0 ? new TabStops(20.0f, (Object[])object) : null;
        TextLine textLine = TextLine.obtain();
        textLine.set(this.mPaint, this.mText, n3, n4, n5, directions, bl3, (TabStops)object, this.getEllipsisStart(n2), this.getEllipsisStart(n2) + this.getEllipsisCount(n2));
        float f = textLine.measure(n - n3, bl, null);
        TextLine.recycle(textLine);
        float f2 = f;
        if (bl2) {
            n = this.mWidth;
            f2 = f;
            if (f > (float)n) {
                f2 = n;
            }
        }
        return (float)this.getLineStartPos(n2, this.getParagraphLeft(n2), this.getParagraphRight(n2)) + f2;
    }

    private float getHorizontal(int n, boolean bl, boolean bl2) {
        return this.getHorizontal(n, bl, this.getLineForOffset(n), bl2);
    }

    private float getJustifyWidth(int n) {
        Object object;
        ParagraphStyle[] arrparagraphStyle = this.mAlignment;
        int n2 = 0;
        int n3 = 0;
        int n4 = this.mWidth;
        int n5 = this.getParagraphDirection(n);
        ParagraphStyle[] arrparagraphStyle2 = NO_PARA_SPANS;
        Object object2 = arrparagraphStyle;
        int n6 = n4;
        if (this.mSpannedText) {
            Spanned spanned = (Spanned)this.mText;
            int n7 = this.getLineStart(n);
            boolean bl = n7 == 0 || this.mText.charAt(n7 - 1) == '\n';
            object = arrparagraphStyle;
            if (bl) {
                object2 = Layout.getParagraphSpans(spanned, n7, spanned.nextSpanTransition(n7, this.mText.length(), ParagraphStyle.class), ParagraphStyle.class);
                n7 = ((ParagraphStyle[])object2).length - 1;
                do {
                    object = arrparagraphStyle;
                    arrparagraphStyle2 = object2;
                    if (n7 < 0) break;
                    if (object2[n7] instanceof AlignmentSpan) {
                        object = ((AlignmentSpan)object2[n7]).getAlignment();
                        arrparagraphStyle2 = object2;
                        break;
                    }
                    --n7;
                } while (true);
            }
            int n8 = arrparagraphStyle2.length;
            boolean bl2 = bl;
            n7 = 0;
            do {
                bl = bl2;
                if (n7 >= n8) break;
                if (arrparagraphStyle2[n7] instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                    n6 = ((LeadingMarginSpan.LeadingMarginSpan2)arrparagraphStyle2[n7]).getLeadingMarginLineCount();
                    if (n < this.getLineForOffset(spanned.getSpanStart(arrparagraphStyle2[n7])) + n6) {
                        bl = true;
                        break;
                    }
                }
                ++n7;
            } while (true);
            int n9 = 0;
            n7 = n3;
            do {
                object2 = object;
                n2 = n7;
                n6 = n4;
                if (n9 >= n8) break;
                n2 = n7;
                n6 = n4;
                if (arrparagraphStyle2[n9] instanceof LeadingMarginSpan) {
                    object2 = (LeadingMarginSpan)arrparagraphStyle2[n9];
                    if (n5 == -1) {
                        n6 = n4 - object2.getLeadingMargin(bl);
                        n2 = n7;
                    } else {
                        n2 = n7 + object2.getLeadingMargin(bl);
                        n6 = n4;
                    }
                }
                ++n9;
                n7 = n2;
                n4 = n6;
            } while (true);
        }
        object = object2 == Alignment.ALIGN_LEFT ? (n5 == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE) : (object2 == Alignment.ALIGN_RIGHT ? (n5 == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL) : object2);
        n = object == Alignment.ALIGN_NORMAL ? (n5 == 1 ? this.getIndentAdjust(n, Alignment.ALIGN_LEFT) : -this.getIndentAdjust(n, Alignment.ALIGN_RIGHT)) : (object == Alignment.ALIGN_OPPOSITE ? (n5 == 1 ? -this.getIndentAdjust(n, Alignment.ALIGN_RIGHT) : this.getIndentAdjust(n, Alignment.ALIGN_LEFT)) : this.getIndentAdjust(n, Alignment.ALIGN_CENTER));
        return n6 - n2 - n;
    }

    private float getLineExtent(int n, TabStops tabStops, boolean bl) {
        int n2 = this.getLineStart(n);
        int n3 = bl ? this.getLineEnd(n) : this.getLineVisibleEnd(n);
        bl = this.getLineContainsTab(n);
        Directions directions = this.getLineDirections(n);
        int n4 = this.getParagraphDirection(n);
        TextLine textLine = TextLine.obtain();
        TextPaint textPaint = this.mWorkPaint;
        textPaint.set(this.mPaint);
        textPaint.setStartHyphenEdit(this.getStartHyphenEdit(n));
        textPaint.setEndHyphenEdit(this.getEndHyphenEdit(n));
        textLine.set(textPaint, this.mText, n2, n3, n4, directions, bl, tabStops, this.getEllipsisStart(n), this.getEllipsisStart(n) + this.getEllipsisCount(n));
        if (this.isJustificationRequired(n)) {
            textLine.justify(this.getJustifyWidth(n));
        }
        float f = textLine.metrics(null);
        TextLine.recycle(textLine);
        return f;
    }

    private float getLineExtent(int n, boolean bl) {
        Object object;
        int n2 = this.getLineStart(n);
        int n3 = bl ? this.getLineEnd(n) : this.getLineVisibleEnd(n);
        bl = this.getLineContainsTab(n);
        object = bl && (object = this.mText) instanceof Spanned && ((TabStopSpan[])(object = Layout.getParagraphSpans((Spanned)object, n2, n3, TabStopSpan.class))).length > 0 ? new TabStops(20.0f, (Object[])object) : null;
        Directions directions = this.getLineDirections(n);
        if (directions == null) {
            return 0.0f;
        }
        int n4 = this.getParagraphDirection(n);
        TextLine textLine = TextLine.obtain();
        TextPaint textPaint = this.mWorkPaint;
        textPaint.set(this.mPaint);
        textPaint.setStartHyphenEdit(this.getStartHyphenEdit(n));
        textPaint.setEndHyphenEdit(this.getEndHyphenEdit(n));
        textLine.set(textPaint, this.mText, n2, n3, n4, directions, bl, (TabStops)object, this.getEllipsisStart(n), this.getEllipsisStart(n) + this.getEllipsisCount(n));
        if (this.isJustificationRequired(n)) {
            textLine.justify(this.getJustifyWidth(n));
        }
        float f = textLine.metrics(null);
        TextLine.recycle(textLine);
        return f;
    }

    private float[] getLineHorizontals(int n, boolean bl, boolean bl2) {
        Object object;
        int n2 = this.getLineStart(n);
        int n3 = this.getLineEnd(n);
        int n4 = this.getParagraphDirection(n);
        boolean bl3 = this.getLineContainsTab(n);
        float[] arrf = this.getLineDirections(n);
        object = bl3 && (object = this.mText) instanceof Spanned && ((TabStopSpan[])(object = Layout.getParagraphSpans((Spanned)object, n2, n3, TabStopSpan.class))).length > 0 ? new TabStops(20.0f, (Object[])object) : null;
        TextLine textLine = TextLine.obtain();
        textLine.set(this.mPaint, this.mText, n2, n3, n4, (Directions)arrf, bl3, (TabStops)object, this.getEllipsisStart(n), this.getEllipsisStart(n) + this.getEllipsisCount(n));
        object = this.primaryIsTrailingPreviousAllLineOffsets(n);
        if (!bl2) {
            for (n4 = 0; n4 < ((Object[])object).length; ++n4) {
                object[n4] = object[n4] ^ true;
            }
        }
        object = textLine.measureAllOffsets((boolean[])object, null);
        TextLine.recycle(textLine);
        if (bl) {
            for (n4 = 0; n4 < ((Object[])object).length; ++n4) {
                Object object2 = object[n4];
                int n5 = this.mWidth;
                if (!(object2 > (float)n5)) continue;
                object[n4] = (float)n5;
            }
        }
        n4 = this.getLineStartPos(n, this.getParagraphLeft(n), this.getParagraphRight(n));
        arrf = new float[n3 - n2 + 1];
        for (n = 0; n < arrf.length; ++n) {
            arrf[n] = (float)n4 + object[n];
        }
        return arrf;
    }

    private int getLineStartPos(int n, int n2, int n3) {
        Alignment alignment;
        Object object = this.getParagraphAlignment(n);
        int n4 = this.getParagraphDirection(n);
        if (object == Alignment.ALIGN_LEFT) {
            alignment = n4 == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE;
        } else {
            alignment = object;
            if (object == Alignment.ALIGN_RIGHT) {
                alignment = n4 == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL;
            }
        }
        if (alignment == Alignment.ALIGN_NORMAL) {
            n = n4 == 1 ? this.getIndentAdjust(n, Alignment.ALIGN_LEFT) + n2 : this.getIndentAdjust(n, Alignment.ALIGN_RIGHT) + n3;
        } else {
            int n5;
            Object var7_7 = null;
            object = var7_7;
            if (this.mSpannedText) {
                object = var7_7;
                if (this.getLineContainsTab(n)) {
                    object = (Spanned)this.mText;
                    n5 = this.getLineStart(n);
                    Object[] arrobject = Layout.getParagraphSpans((Spanned)object, n5, object.nextSpanTransition(n5, object.length(), TabStopSpan.class), TabStopSpan.class);
                    object = var7_7;
                    if (arrobject.length > 0) {
                        object = new TabStops(20.0f, arrobject);
                    }
                }
            }
            n5 = (int)this.getLineExtent(n, (TabStops)object, false);
            n = alignment == Alignment.ALIGN_OPPOSITE ? (n4 == 1 ? n3 - n5 + this.getIndentAdjust(n, Alignment.ALIGN_RIGHT) : n2 - n5 + this.getIndentAdjust(n, Alignment.ALIGN_LEFT)) : n2 + n3 - (n5 & -2) >> this.getIndentAdjust(n, Alignment.ALIGN_CENTER) + 1;
        }
        return n;
    }

    private int getLineVisibleEnd(int n, int n2, int n3) {
        int n4;
        CharSequence charSequence = this.mText;
        if (n == this.getLineCount() - 1) {
            return n3;
        }
        for (n4 = n3; n4 > n2; --n4) {
            char c = charSequence.charAt(n4 - 1);
            if (c == '\n') {
                return n4 - 1;
            }
            if (!TextLine.isLineEndSpace(c)) break;
        }
        return n4;
    }

    private int getOffsetAtStartOf(int n) {
        if (n == 0) {
            return 0;
        }
        CharSequence charSequence = this.mText;
        int n2 = charSequence.charAt(n);
        int n3 = n;
        if (n2 >= 56320) {
            n3 = n;
            if (n2 <= 57343) {
                n2 = charSequence.charAt(n - 1);
                n3 = n;
                if (n2 >= 55296) {
                    n3 = n;
                    if (n2 <= 56319) {
                        n3 = n - 1;
                    }
                }
            }
        }
        n2 = n3;
        if (this.mSpannedText) {
            ReplacementSpan[] arrreplacementSpan = ((Spanned)charSequence).getSpans(n3, n3, ReplacementSpan.class);
            n = 0;
            do {
                n2 = n3;
                if (n >= arrreplacementSpan.length) break;
                int n4 = ((Spanned)charSequence).getSpanStart(arrreplacementSpan[n]);
                int n5 = ((Spanned)charSequence).getSpanEnd(arrreplacementSpan[n]);
                n2 = n3;
                if (n4 < n3) {
                    n2 = n3;
                    if (n5 > n3) {
                        n2 = n4;
                    }
                }
                ++n;
                n3 = n2;
            } while (true);
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getOffsetToLeftRightOf(int n, boolean bl) {
        int n2 = this.getLineForOffset(n);
        int n3 = this.getLineStart(n2);
        int n4 = this.getLineEnd(n2);
        int n5 = this.getParagraphDirection(n2);
        int n6 = 0;
        int n7 = 0;
        boolean bl2 = n5 == -1;
        if (bl == bl2) {
            n7 = 1;
        }
        if (n7 != 0) {
            n7 = n2;
            if (n == n4) {
                if (n2 >= this.getLineCount() - 1) return n;
                n6 = 1;
                n7 = n2 + 1;
            }
        } else {
            n7 = n2;
            if (n == n3) {
                if (n2 <= 0) return n;
                n6 = 1;
                n7 = n2 - 1;
            }
        }
        bl2 = bl;
        n2 = n5;
        if (n6 != 0) {
            int n8 = this.getLineStart(n7);
            n6 = this.getLineEnd(n7);
            int n9 = this.getParagraphDirection(n7);
            bl2 = bl;
            n3 = n8;
            n4 = n6;
            n2 = n5;
            if (n9 != n5) {
                bl2 = bl ^ true;
                n2 = n9;
                n4 = n6;
                n3 = n8;
            }
        }
        Directions directions = this.getLineDirections(n7);
        TextLine textLine = TextLine.obtain();
        textLine.set(this.mPaint, this.mText, n3, n4, n2, directions, false, null, this.getEllipsisStart(n7), this.getEllipsisStart(n7) + this.getEllipsisCount(n7));
        n = textLine.getOffsetToLeftRightOf(n - n3, bl2);
        TextLine.recycle(textLine);
        return n + n3;
    }

    private int getParagraphLeadingMargin(int n) {
        if (!this.mSpannedText) {
            return 0;
        }
        Spanned spanned = (Spanned)this.mText;
        int n2 = this.getLineStart(n);
        LeadingMarginSpan[] arrleadingMarginSpan = Layout.getParagraphSpans(spanned, n2, spanned.nextSpanTransition(n2, this.getLineEnd(n), LeadingMarginSpan.class), LeadingMarginSpan.class);
        if (arrleadingMarginSpan.length == 0) {
            return 0;
        }
        int n3 = 0;
        boolean bl = n2 == 0 || spanned.charAt(n2 - 1) == '\n';
        for (n2 = 0; n2 < arrleadingMarginSpan.length; ++n2) {
            boolean bl2 = bl;
            if (arrleadingMarginSpan[n2] instanceof LeadingMarginSpan.LeadingMarginSpan2) {
                boolean bl3 = n < this.getLineForOffset(spanned.getSpanStart(arrleadingMarginSpan[n2])) + ((LeadingMarginSpan.LeadingMarginSpan2)arrleadingMarginSpan[n2]).getLeadingMarginLineCount();
                bl2 = bl | bl3;
            }
            bl = bl2;
        }
        n2 = n3;
        for (n = 0; n < arrleadingMarginSpan.length; ++n) {
            n2 += arrleadingMarginSpan[n].getLeadingMargin(bl);
        }
        return n2;
    }

    static <T> T[] getParagraphSpans(Spanned spanned, int n, int n2, Class<T> class_) {
        if (n == n2 && n > 0) {
            return ArrayUtils.emptyArray(class_);
        }
        if (spanned instanceof SpannableStringBuilder) {
            return ((SpannableStringBuilder)spanned).getSpans(n, n2, class_, false);
        }
        return spanned.getSpans(n, n2, class_);
    }

    private boolean isJustificationRequired(int n) {
        int n2 = this.mJustificationMode;
        boolean bl = false;
        if (n2 == 0) {
            return false;
        }
        n = this.getLineEnd(n);
        boolean bl2 = bl;
        if (n < this.mText.length()) {
            bl2 = bl;
            if (this.mText.charAt(n - 1) != '\n') {
                bl2 = true;
            }
        }
        return bl2;
    }

    static /* synthetic */ void lambda$getSelectionPath$0(Path path, float f, float f2, float f3, float f4, int n) {
        path.addRect(f, f2, f3, f4, Path.Direction.CW);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static float measurePara(TextPaint var0, CharSequence var1_6, int var2_7, int var3_8, TextDirectionHeuristic var4_9) {
        block15 : {
            block16 : {
                block14 : {
                    var5_10 = TextLine.obtain();
                    var4_9 = MeasuredParagraph.buildForBidi(var1_6, var2_7, var3_8, (TextDirectionHeuristic)var4_9, null);
                    {
                        catch (Throwable var0_4) {
                            var4_9 = null;
                            break block15;
                        }
                    }
                    var6_11 = var4_9.getChars();
                    var7_12 = ((char[])var6_11).length;
                    var8_13 = var4_9.getDirections(0, var7_12);
                    var9_14 = var4_9.getParagraphDir();
                    var10_15 = false;
                    var11_16 = var1_6 instanceof Spanned;
                    if (!var11_16) ** GOTO lbl21
                    {
                        catch (Throwable var0_3) {}
                    }
                    try {
                        block17 : {
                            var12_17 = Layout.getParagraphSpans((Spanned)var1_6, var2_7, var3_8, LeadingMarginSpan.class);
                            var13_18 = var12_17.length;
                            var14_19 = 0;
                            break block17;
lbl21: // 1 sources:
                            var10_15 = false;
                            var14_19 = 0;
                            break block14;
                            break block15;
                        }
                        for (var15_20 = 0; var15_20 < var13_18; var14_19 += var12_17[var15_20].getLeadingMargin((boolean)true), ++var15_20) {
                        }
                    }
                    catch (Throwable var0_1) {
                        break block15;
                    }
                }
                for (var13_18 = 0; var13_18 < var7_12; ++var13_18) {
                    if (var6_11[var13_18] != '\t') continue;
                    {
                        if (var1_6 instanceof Spanned) {
                            var6_11 = (Spanned)var1_6;
                            var6_11 = Layout.getParagraphSpans((Spanned)var6_11, var2_7, var6_11.nextSpanTransition(var2_7, var3_8, TabStopSpan.class), TabStopSpan.class);
                            var10_15 = true;
                            var6_11 = ((Object)var6_11).length > 0 ? new TabStops(20.0f, (Object[])var6_11) : null;
                        } else {
                            var10_15 = true;
                            var6_11 = null;
                        }
                        break block16;
                    }
                }
                var6_11 = null;
            }
            try {
                var5_10.set(var0, var1_6, var2_7, var3_8, var9_14, var8_13, var10_15, (TabStops)var6_11, 0, 0);
            }
            catch (Throwable var0_2) {}
            var16_21 = var14_19;
            var17_22 = Math.abs(var5_10.metrics(null));
            TextLine.recycle(var5_10);
            var4_9.recycle();
            return var16_21 + var17_22;
        }
        TextLine.recycle(var5_10);
        if (var4_9 == null) throw var0_5;
        var4_9.recycle();
        throw var0_5;
    }

    static float nextTab(CharSequence charSequence, int n, int n2, float f, Object[] arrobject) {
        float f2 = Float.MAX_VALUE;
        boolean bl = false;
        if (charSequence instanceof Spanned) {
            Object[] arrobject2 = arrobject;
            if (arrobject == null) {
                arrobject2 = Layout.getParagraphSpans((Spanned)charSequence, n, n2, TabStopSpan.class);
                bl = true;
            }
            for (n = 0; n < arrobject2.length; ++n) {
                float f3;
                if (!bl && !(arrobject2[n] instanceof TabStopSpan)) {
                    f3 = f2;
                } else {
                    n2 = ((TabStopSpan)arrobject2[n]).getTabStop();
                    f3 = f2;
                    if ((float)n2 < f2) {
                        f3 = f2;
                        if ((float)n2 > f) {
                            f3 = n2;
                        }
                    }
                }
                f2 = f3;
            }
            if (f2 != Float.MAX_VALUE) {
                return f2;
            }
        }
        return (float)((int)((f + 20.0f) / 20.0f)) * 20.0f;
    }

    public void draw(Canvas canvas) {
        this.draw(canvas, null, null, 0);
    }

    public void draw(Canvas canvas, Path path, Paint paint, int n) {
        long l = this.getLineRangeForDraw(canvas);
        int n2 = TextUtils.unpackRangeStartFromLong(l);
        int n3 = TextUtils.unpackRangeEndFromLong(l);
        if (n3 < 0) {
            return;
        }
        this.drawBackground(canvas, path, paint, n, n2, n3);
        this.drawText(canvas, n2, n3);
    }

    @UnsupportedAppUsage
    public void drawBackground(Canvas canvas, Path path, Paint paint, int n, int n2, int n3) {
        if (this.mSpannedText) {
            if (this.mLineBackgroundSpans == null) {
                this.mLineBackgroundSpans = new SpanSet<LineBackgroundSpan>(LineBackgroundSpan.class);
            }
            Spanned spanned = (Spanned)this.mText;
            int n4 = spanned.length();
            this.mLineBackgroundSpans.init(spanned, 0, n4);
            if (this.mLineBackgroundSpans.numberOfSpans > 0) {
                int n5 = this.getLineTop(n2);
                int n6 = this.getLineStart(n2);
                ParagraphStyle[] arrparagraphStyle = NO_PARA_SPANS;
                int n7 = 0;
                TextPaint textPaint = this.mPaint;
                int n8 = 0;
                int n9 = this.mWidth;
                int n10 = n2;
                n2 = n7;
                while (n10 <= n3) {
                    int n11;
                    int n12;
                    n7 = this.getLineStart(n10 + 1);
                    int n13 = this.getLineTop(n10 + 1);
                    int n14 = this.getLineDescent(n10);
                    if (n7 >= n8) {
                        n2 = this.mLineBackgroundSpans.getNextTransition(n6, n4);
                        n8 = 0;
                        if (n6 == n7 && n6 != 0) {
                            n8 = 0;
                        } else {
                            n12 = n2;
                            n2 = n8;
                            for (n11 = 0; n11 < this.mLineBackgroundSpans.numberOfSpans; ++n11) {
                                ParagraphStyle[] arrparagraphStyle2 = arrparagraphStyle;
                                n8 = n2;
                                if (this.mLineBackgroundSpans.spanStarts[n11] < n7) {
                                    if (this.mLineBackgroundSpans.spanEnds[n11] <= n6) {
                                        arrparagraphStyle2 = arrparagraphStyle;
                                        n8 = n2;
                                    } else {
                                        arrparagraphStyle2 = GrowingArrayUtils.append(arrparagraphStyle, n2, ((LineBackgroundSpan[])this.mLineBackgroundSpans.spans)[n11]);
                                        n8 = n2 + 1;
                                    }
                                }
                                arrparagraphStyle = arrparagraphStyle2;
                                n2 = n8;
                            }
                            n8 = n2;
                            n2 = n12;
                        }
                    } else {
                        n12 = n8;
                        n8 = n2;
                        n2 = n12;
                    }
                    n12 = n10;
                    n10 = n7;
                    for (n11 = 0; n11 < n8; ++n11) {
                        ((LineBackgroundSpan)arrparagraphStyle[n11]).drawBackground(canvas, textPaint, 0, n9, n5, n13 - n14, n13, spanned, n6, n10, n12);
                    }
                    n6 = n7;
                    n5 = n13;
                    n10 = n2;
                    n2 = n8;
                    n8 = n10;
                    n10 = ++n12;
                }
            }
            this.mLineBackgroundSpans.recycle();
        }
        if (path != null) {
            if (n != 0) {
                canvas.translate(0.0f, n);
            }
            canvas.drawPath(path, paint);
            if (n != 0) {
                canvas.translate(0.0f, -n);
            }
        }
    }

    @UnsupportedAppUsage
    public void drawText(Canvas canvas, int n, int n2) {
        Object object = this;
        int n3 = n;
        int n4 = ((Layout)object).getLineTop(n3);
        int n5 = ((Layout)object).getLineStart(n3);
        Object object2 = NO_PARA_SPANS;
        int n6 = 0;
        Object[] arrobject = ((Layout)object).mWorkPaint;
        arrobject.set(((Layout)object).mPaint);
        CharSequence charSequence = ((Layout)object).mText;
        Object[] arrobject2 = ((Layout)object).mAlignment;
        int n7 = 0;
        Object object3 = TextLine.obtain();
        Object object4 = null;
        n3 = n;
        while (n3 <= n2) {
            Object object5;
            int n8;
            int n9;
            Object object6;
            int n10;
            int n11;
            int n12 = ((Layout)object).getLineStart(n3 + 1);
            boolean bl = ((Layout)object).isJustificationRequired(n3);
            int n13 = ((Layout)object).getLineVisibleEnd(n3, n5, n12);
            arrobject.setStartHyphenEdit(((Layout)object).getStartHyphenEdit(n3));
            arrobject.setEndHyphenEdit(((Layout)object).getEndHyphenEdit(n3));
            int n14 = ((Layout)object).getLineTop(n3 + 1);
            int n15 = ((Layout)object).getLineDescent(n3);
            int n16 = ((Layout)object).getParagraphDirection(n3);
            int n17 = n14 - n15;
            n15 = ((Layout)object).mWidth;
            boolean bl2 = ((Layout)object).mSpannedText;
            if (bl2) {
                object5 = (Object[])charSequence;
                n8 = charSequence.length();
                bl2 = n5 == 0 || charSequence.charAt(n5 - 1) == '\n';
                if (n5 >= n6 && (n3 == n || bl2)) {
                    n6 = object5.nextSpanTransition(n5, n8, ParagraphStyle.class);
                    object6 = Layout.getParagraphSpans((Spanned)object5, n5, n6, ParagraphStyle.class);
                    object2 = ((Layout)object).mAlignment;
                    for (n7 = ((ParagraphStyle[])object6).length - 1; n7 >= 0; --n7) {
                        if (!(object6[n7] instanceof AlignmentSpan)) continue;
                        object2 = ((AlignmentSpan)object6[n7]).getAlignment();
                        break;
                    }
                    n7 = 0;
                } else {
                    object6 = object2;
                    object2 = arrobject2;
                }
                n10 = ((ParagraphStyle[])object6).length;
                boolean bl3 = bl2;
                arrobject2 = object5;
                for (n11 = 0; n11 < n10; ++n11) {
                    if (!(object6[n11] instanceof LeadingMarginSpan.LeadingMarginSpan2)) continue;
                    n9 = ((LeadingMarginSpan.LeadingMarginSpan2)object6[n11]).getLeadingMarginLineCount();
                    if (n3 >= ((Layout)object).getLineForOffset(arrobject2.getSpanStart(object6[n11])) + n9) continue;
                    bl3 = true;
                    break;
                }
                int n18 = 0;
                n11 = 0;
                n9 = n4;
                n4 = n17;
                n17 = n8;
                arrobject2 = object4;
                object4 = object6;
                n8 = n10;
                for (n10 = n18; n10 < n8; ++n10) {
                    if (!(object4[n10] instanceof LeadingMarginSpan)) continue;
                    object6 = (LeadingMarginSpan)object4[n10];
                    if (n16 == -1) {
                        object6.drawLeadingMargin(canvas, (Paint)arrobject, n15, n16, n9, n4, n14, charSequence, n5, n13, bl2, this);
                        n15 -= object6.getLeadingMargin(bl3);
                        object = this;
                        continue;
                    }
                    object = this;
                    object6.drawLeadingMargin(canvas, (Paint)arrobject, n11, n16, n9, n4, n14, charSequence, n5, n13, bl2, this);
                    n11 += object6.getLeadingMargin(bl3);
                }
                object6 = arrobject2;
                n17 = n3;
                n8 = n4;
                object5 = object3;
                n4 = n9;
                arrobject2 = arrobject;
                arrobject = object4;
                object3 = object;
                n9 = n6;
                object4 = object2;
                n3 = n16;
                n16 = n11;
                n11 = n15;
                n15 = n8;
                object = object6;
                object2 = object5;
            } else {
                n9 = n16;
                n11 = n3;
                object6 = arrobject;
                arrobject = object;
                n3 = n17;
                n8 = 0;
                n16 = n15;
                object5 = object2;
                object2 = object3;
                n17 = n11;
                object = object4;
                n15 = n3;
                n11 = n16;
                n16 = n8;
                n3 = n9;
                object4 = arrobject2;
                n9 = n6;
                object3 = arrobject;
                arrobject = object5;
                arrobject2 = object6;
            }
            bl2 = ((Layout)object3).getLineContainsTab(n17);
            if (bl2 && n7 == 0) {
                if (object == null) {
                    object = new TabStops(20.0f, arrobject);
                } else {
                    ((TabStops)object).reset(20.0f, arrobject);
                }
                n6 = 1;
            } else {
                n6 = n7;
            }
            object6 = object4 == Alignment.ALIGN_LEFT ? (n3 == 1 ? Alignment.ALIGN_NORMAL : Alignment.ALIGN_OPPOSITE) : (object4 == Alignment.ALIGN_RIGHT ? (n3 == 1 ? Alignment.ALIGN_OPPOSITE : Alignment.ALIGN_NORMAL) : object4);
            n8 = n3;
            if (object6 == Alignment.ALIGN_NORMAL) {
                if (n8 == 1) {
                    n7 = n3 = ((Layout)object3).getIndentAdjust(n17, Alignment.ALIGN_LEFT);
                    n3 = n16 + n3;
                } else {
                    n7 = n3 = -((Layout)object3).getIndentAdjust(n17, Alignment.ALIGN_RIGHT);
                    n3 = n11 - n3;
                }
            } else {
                n10 = (int)Layout.super.getLineExtent(n17, (TabStops)object, false);
                if (object6 == Alignment.ALIGN_OPPOSITE) {
                    if (n8 == 1) {
                        n7 = n3 = -((Layout)object3).getIndentAdjust(n17, Alignment.ALIGN_RIGHT);
                        n3 = n11 - n10 - n3;
                    } else {
                        n7 = n3 = ((Layout)object3).getIndentAdjust(n17, Alignment.ALIGN_LEFT);
                        n3 = n16 - n10 + n3;
                    }
                } else {
                    n7 = ((Layout)object3).getIndentAdjust(n17, Alignment.ALIGN_CENTER);
                    n3 = (n11 + n16 - (n10 & -2) >> 1) + n7;
                }
            }
            object6 = ((Layout)object3).getLineDirections(n17);
            if (!(object6 != DIRS_ALL_LEFT_TO_RIGHT || ((Layout)object3).mSpannedText || bl2 || bl)) {
                canvas.drawText(charSequence, n5, n13, (float)n3, (float)n15, (Paint)arrobject2);
            } else {
                ((TextLine)object2).set((TextPaint)arrobject2, charSequence, n5, n13, n8, (Directions)object6, bl2, (TabStops)object, ((Layout)object3).getEllipsisStart(n17), ((Layout)object3).getEllipsisStart(n17) + ((Layout)object3).getEllipsisCount(n17));
                if (bl) {
                    ((TextLine)object2).justify(n11 - n16 - n7);
                }
                ((TextLine)object2).draw(canvas, n3, n4, n15, n14);
            }
            n3 = n17 + 1;
            object5 = arrobject2;
            object6 = object3;
            object3 = object2;
            n4 = n14;
            n5 = n12;
            n7 = n6;
            object2 = arrobject;
            n6 = n9;
            arrobject2 = object4;
            object4 = object;
            arrobject = object5;
            object = object6;
        }
        TextLine.recycle((TextLine)object3);
    }

    public final Alignment getAlignment() {
        return this.mAlignment;
    }

    public abstract int getBottomPadding();

    public void getCursorPath(int n, Path path, CharSequence charSequence) {
        int n2;
        int n3;
        int n4;
        float f;
        int n5;
        int n6;
        block13 : {
            int n7;
            int n8;
            block12 : {
                path.reset();
                n5 = this.getLineForOffset(n);
                n7 = this.getLineTop(n5);
                n8 = this.getLineBottomWithoutSpacing(n5);
                f = this.getPrimaryHorizontal(n, this.shouldClampCursor(n5)) - 0.5f;
                n3 = TextKeyListener.getMetaState(charSequence, 1) | TextKeyListener.getMetaState(charSequence, 2048);
                n2 = TextKeyListener.getMetaState(charSequence, 2);
                n5 = 0;
                if (n3 != 0) break block12;
                n6 = n7;
                n4 = n8;
                if (n2 == 0) break block13;
            }
            int n9 = n8 - n7 >> 2;
            n = n7;
            if (n2 != 0) {
                n = n7 + n9;
            }
            n6 = n;
            n4 = n8;
            n5 = n9;
            if (n3 != 0) {
                n4 = n8 - n9;
                n5 = n9;
                n6 = n;
            }
        }
        float f2 = f;
        if (f < 0.5f) {
            f2 = 0.5f;
        }
        path.moveTo(f2, n6);
        path.lineTo(f2, n4);
        if (n3 == 2) {
            path.moveTo(f2, n4);
            path.lineTo(f2 - (float)n5, n4 + n5);
            path.lineTo(f2, n4);
            path.lineTo((float)n5 + f2, n4 + n5);
        } else if (n3 == 1) {
            path.moveTo(f2, n4);
            path.lineTo(f2 - (float)n5, n4 + n5);
            path.moveTo(f2 - (float)n5, (float)(n4 + n5) - 0.5f);
            path.lineTo((float)n5 + f2, (float)(n4 + n5) - 0.5f);
            path.moveTo((float)n5 + f2, n4 + n5);
            path.lineTo(f2, n4);
        }
        if (n2 == 2) {
            path.moveTo(f2, n6);
            path.lineTo(f2 - (float)n5, n6 - n5);
            path.lineTo(f2, n6);
            path.lineTo((float)n5 + f2, n6 - n5);
        } else if (n2 == 1) {
            path.moveTo(f2, n6);
            path.lineTo(f2 - (float)n5, n6 - n5);
            path.moveTo(f2 - (float)n5, (float)(n6 - n5) + 0.5f);
            path.lineTo((float)n5 + f2, (float)(n6 - n5) + 0.5f);
            path.moveTo((float)n5 + f2, n6 - n5);
            path.lineTo(f2, n6);
        }
    }

    public abstract int getEllipsisCount(int var1);

    public abstract int getEllipsisStart(int var1);

    public int getEllipsizedWidth() {
        return this.mWidth;
    }

    public int getEndHyphenEdit(int n) {
        return 0;
    }

    public int getHeight() {
        return this.getLineTop(this.getLineCount());
    }

    public int getHeight(boolean bl) {
        return this.getHeight();
    }

    public int getIndentAdjust(int n, Alignment alignment) {
        return 0;
    }

    public final int getLineAscent(int n) {
        return this.getLineTop(n) - (this.getLineTop(n + 1) - this.getLineDescent(n));
    }

    public final int getLineBaseline(int n) {
        return this.getLineTop(n + 1) - this.getLineDescent(n);
    }

    public final int getLineBottom(int n) {
        return this.getLineTop(n + 1);
    }

    public final int getLineBottomWithoutSpacing(int n) {
        return this.getLineTop(n + 1) - this.getLineExtra(n);
    }

    public int getLineBounds(int n, Rect rect) {
        if (rect != null) {
            rect.left = 0;
            rect.top = this.getLineTop(n);
            rect.right = this.mWidth;
            rect.bottom = this.getLineTop(n + 1);
        }
        return this.getLineBaseline(n);
    }

    public abstract boolean getLineContainsTab(int var1);

    public abstract int getLineCount();

    public abstract int getLineDescent(int var1);

    public abstract Directions getLineDirections(int var1);

    public final int getLineEnd(int n) {
        return this.getLineStart(n + 1);
    }

    public int getLineExtra(int n) {
        return 0;
    }

    public int getLineForOffset(int n) {
        int n2 = this.getLineCount();
        int n3 = -1;
        while (n2 - n3 > 1) {
            int n4 = (n2 + n3) / 2;
            if (this.getLineStart(n4) > n) {
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

    public int getLineForVertical(int n) {
        int n2 = this.getLineCount();
        int n3 = -1;
        while (n2 - n3 > 1) {
            int n4 = (n2 + n3) / 2;
            if (this.getLineTop(n4) > n) {
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

    public float getLineLeft(int n) {
        Alignment alignment;
        int n2;
        int n3 = this.getParagraphDirection(n);
        Alignment alignment2 = alignment = this.getParagraphAlignment(n);
        if (alignment == null) {
            alignment2 = Alignment.ALIGN_CENTER;
        }
        alignment2 = (n2 = 1.$SwitchMap$android$text$Layout$Alignment[alignment2.ordinal()]) != 1 ? (n2 != 2 ? (n2 != 3 ? (n2 != 4 ? Alignment.ALIGN_LEFT : Alignment.ALIGN_RIGHT) : Alignment.ALIGN_CENTER) : (n3 == -1 ? Alignment.ALIGN_LEFT : Alignment.ALIGN_RIGHT)) : (n3 == -1 ? Alignment.ALIGN_RIGHT : Alignment.ALIGN_LEFT);
        n2 = 1.$SwitchMap$android$text$Layout$Alignment[alignment2.ordinal()];
        if (n2 != 3) {
            if (n2 != 4) {
                return 0.0f;
            }
            return (float)this.mWidth - this.getLineMax(n);
        }
        n2 = this.getParagraphLeft(n);
        float f = this.getLineMax(n);
        return (float)Math.floor((float)n2 + ((float)this.mWidth - f) / 2.0f);
    }

    public float getLineMax(int n) {
        float f = this.getParagraphLeadingMargin(n);
        float f2 = this.getLineExtent(n, false);
        if (!(f2 >= 0.0f)) {
            f2 = -f2;
        }
        return f2 + f;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public long getLineRangeForDraw(Canvas canvas) {
        int n;
        int n2;
        Rect rect = sTempRect;
        synchronized (rect) {
            if (!canvas.getClipBounds(sTempRect)) {
                return TextUtils.packRangeInLong(0, -1);
            }
            n = Layout.sTempRect.top;
            n2 = Layout.sTempRect.bottom;
        }
        n = Math.max(n, 0);
        n2 = Math.min(this.getLineTop(this.getLineCount()), n2);
        if (n < n2) return TextUtils.packRangeInLong(this.getLineForVertical(n), this.getLineForVertical(n2));
        return TextUtils.packRangeInLong(0, -1);
    }

    public float getLineRight(int n) {
        Alignment alignment;
        int n2;
        int n3 = this.getParagraphDirection(n);
        Alignment alignment2 = alignment = this.getParagraphAlignment(n);
        if (alignment == null) {
            alignment2 = Alignment.ALIGN_CENTER;
        }
        alignment2 = (n2 = 1.$SwitchMap$android$text$Layout$Alignment[alignment2.ordinal()]) != 1 ? (n2 != 2 ? (n2 != 3 ? (n2 != 4 ? Alignment.ALIGN_LEFT : Alignment.ALIGN_RIGHT) : Alignment.ALIGN_CENTER) : (n3 == -1 ? Alignment.ALIGN_LEFT : Alignment.ALIGN_RIGHT)) : (n3 == -1 ? Alignment.ALIGN_RIGHT : Alignment.ALIGN_LEFT);
        n3 = 1.$SwitchMap$android$text$Layout$Alignment[alignment2.ordinal()];
        if (n3 != 3) {
            if (n3 != 4) {
                return this.getLineMax(n);
            }
            return this.mWidth;
        }
        n3 = this.getParagraphRight(n);
        float f = this.getLineMax(n);
        return (float)Math.ceil((float)n3 - ((float)this.mWidth - f) / 2.0f);
    }

    public abstract int getLineStart(int var1);

    public abstract int getLineTop(int var1);

    public int getLineVisibleEnd(int n) {
        return this.getLineVisibleEnd(n, this.getLineStart(n), this.getLineStart(n + 1));
    }

    public float getLineWidth(int n) {
        float f = this.getParagraphLeadingMargin(n);
        float f2 = this.getLineExtent(n, true);
        if (!(f2 >= 0.0f)) {
            f2 = -f2;
        }
        return f2 + f;
    }

    public int getOffsetForHorizontal(int n, float f) {
        return this.getOffsetForHorizontal(n, f, true);
    }

    public int getOffsetForHorizontal(int n, float f, boolean bl) {
        Layout layout2 = this;
        int n2 = this.getLineEnd(n);
        int n3 = this.getLineStart(n);
        Object object = this.getLineDirections(n);
        TextLine textLine = TextLine.obtain();
        TextPaint textPaint = layout2.mPaint;
        CharSequence charSequence = layout2.mText;
        int n4 = this.getParagraphDirection(n);
        int n5 = this.getEllipsisStart(n);
        int n6 = this.getEllipsisStart(n);
        int n7 = this.getEllipsisCount(n);
        Directions directions = object;
        textLine.set(textPaint, charSequence, n3, n2, n4, (Directions)object, false, null, n5, n6 + n7);
        object = layout2.new HorizontalMeasurementProvider(n, bl);
        n = n == this.getLineCount() - 1 ? n2 : textLine.getOffsetToLeftRightOf(n2 - n3, layout2.isRtlCharAt(n2 - 1) ^ true) + n3;
        n2 = n3;
        float f2 = Math.abs(((HorizontalMeasurementProvider)object).get(n3) - f);
        for (n7 = 0; n7 < directions.mDirections.length; n7 += 2) {
            float f3;
            float f4;
            int n8 = directions.mDirections[n7] + n3;
            n6 = (directions.mDirections[n7 + 1] & 67108863) + n8;
            bl = (directions.mDirections[n7 + 1] & 67108864) != 0;
            n5 = bl ? -1 : 1;
            n4 = n6;
            if (n6 > n) {
                n4 = n;
            }
            int n9 = n4 - 1 + 1;
            int n10 = n8 + 1 - 1;
            n6 = n5;
            n5 = n10;
            while (n9 - n5 > 1) {
                n10 = (n9 + n5) / 2;
                if (((HorizontalMeasurementProvider)object).get(this.getOffsetAtStartOf(n10)) * (float)n6 >= (float)n6 * f) {
                    n9 = n10;
                    continue;
                }
                n5 = n10;
            }
            n6 = n5;
            if (n5 < n8 + 1) {
                n6 = n8 + 1;
            }
            if (n6 < n4) {
                n10 = textLine.getOffsetToLeftRightOf(n6 - n3, bl) + n3;
                bl = !bl;
                n9 = textLine.getOffsetToLeftRightOf(n10 - n3, bl) + n3;
                n5 = n2;
                f3 = f2;
                if (n9 >= n8) {
                    n5 = n2;
                    f3 = f2;
                    if (n9 < n4) {
                        float f5;
                        f4 = f5 = Math.abs(((HorizontalMeasurementProvider)object).get(n9) - f);
                        n6 = n9;
                        if (n10 < n4) {
                            f3 = Math.abs(((HorizontalMeasurementProvider)object).get(n10) - f);
                            f4 = f5;
                            n6 = n9;
                            if (f3 < f5) {
                                f4 = f3;
                                n6 = n10;
                            }
                        }
                        n5 = n2;
                        f3 = f2;
                        if (f4 < f2) {
                            n5 = n6;
                            f3 = f4;
                        }
                    }
                }
            } else {
                f3 = f2;
                n5 = n2;
            }
            f4 = Math.abs(((HorizontalMeasurementProvider)object).get(n8) - f);
            n2 = n5;
            f2 = f3;
            if (!(f4 < f3)) continue;
            f2 = f4;
            n2 = n8;
        }
        if (Math.abs(((HorizontalMeasurementProvider)object).get(n) - f) <= f2) {
            n2 = n;
        }
        TextLine.recycle(textLine);
        return n2;
    }

    public int getOffsetToLeftOf(int n) {
        return this.getOffsetToLeftRightOf(n, true);
    }

    public int getOffsetToRightOf(int n) {
        return this.getOffsetToLeftRightOf(n, false);
    }

    public final TextPaint getPaint() {
        return this.mPaint;
    }

    public final Alignment getParagraphAlignment(int n) {
        Alignment alignment;
        Alignment alignment2 = alignment = this.mAlignment;
        if (this.mSpannedText) {
            AlignmentSpan[] arralignmentSpan = Layout.getParagraphSpans((Spanned)this.mText, this.getLineStart(n), this.getLineEnd(n), AlignmentSpan.class);
            n = arralignmentSpan.length;
            alignment2 = alignment;
            if (n > 0) {
                alignment2 = arralignmentSpan[n - 1].getAlignment();
            }
        }
        return alignment2;
    }

    public abstract int getParagraphDirection(int var1);

    public final int getParagraphLeft(int n) {
        if (this.getParagraphDirection(n) != -1 && this.mSpannedText) {
            return this.getParagraphLeadingMargin(n);
        }
        return 0;
    }

    public final int getParagraphRight(int n) {
        int n2 = this.mWidth;
        if (this.getParagraphDirection(n) != 1 && this.mSpannedText) {
            return n2 - this.getParagraphLeadingMargin(n);
        }
        return n2;
    }

    public float getPrimaryHorizontal(int n) {
        return this.getPrimaryHorizontal(n, false);
    }

    @UnsupportedAppUsage
    public float getPrimaryHorizontal(int n, boolean bl) {
        return this.getHorizontal(n, this.primaryIsTrailingPrevious(n), bl);
    }

    public long getRunRange(int n) {
        int n2 = this.getLineForOffset(n);
        int[] arrn = this.getLineDirections(n2);
        if (arrn != DIRS_ALL_LEFT_TO_RIGHT && arrn != DIRS_ALL_RIGHT_TO_LEFT) {
            arrn = arrn.mDirections;
            int n3 = this.getLineStart(n2);
            for (int i = 0; i < arrn.length; i += 2) {
                int n4 = arrn[i] + n3;
                int n5 = (arrn[i + 1] & 67108863) + n4;
                if (n < n4 || n >= n5) continue;
                return TextUtils.packRangeInLong(n4, n5);
            }
            return TextUtils.packRangeInLong(0, this.getLineEnd(n2));
        }
        return TextUtils.packRangeInLong(0, this.getLineEnd(n2));
    }

    public float getSecondaryHorizontal(int n) {
        return this.getSecondaryHorizontal(n, false);
    }

    @UnsupportedAppUsage
    public float getSecondaryHorizontal(int n, boolean bl) {
        return this.getHorizontal(n, this.primaryIsTrailingPrevious(n) ^ true, bl);
    }

    public final void getSelection(int n, int n2, SelectionRectangleConsumer selectionRectangleConsumer) {
        int n3;
        if (n == n2) {
            return;
        }
        if (n2 >= n) {
            n3 = n;
            n = n2;
            n2 = n3;
        }
        int n4 = this.getLineForOffset(n2);
        n3 = this.getLineForOffset(n);
        int n5 = this.getLineTop(n4);
        int n6 = this.getLineBottomWithoutSpacing(n3);
        if (n4 == n3) {
            this.addSelection(n4, n2, n, n5, n6, selectionRectangleConsumer);
        } else {
            float f = this.mWidth;
            this.addSelection(n4, n2, this.getLineEnd(n4), n5, this.getLineBottom(n4), selectionRectangleConsumer);
            if (this.getParagraphDirection(n4) == -1) {
                selectionRectangleConsumer.accept(this.getLineLeft(n4), n5, 0.0f, this.getLineBottom(n4), 0);
            } else {
                selectionRectangleConsumer.accept(this.getLineRight(n4), n5, f, this.getLineBottom(n4), 1);
            }
            for (n2 = n4 + 1; n2 < n3; ++n2) {
                n6 = this.getLineTop(n2);
                n4 = this.getLineBottom(n2);
                if (this.getParagraphDirection(n2) == -1) {
                    selectionRectangleConsumer.accept(0.0f, n6, f, n4, 0);
                    continue;
                }
                selectionRectangleConsumer.accept(0.0f, n6, f, n4, 1);
            }
            n2 = this.getLineTop(n3);
            n4 = this.getLineBottomWithoutSpacing(n3);
            this.addSelection(n3, this.getLineStart(n3), n, n2, n4, selectionRectangleConsumer);
            if (this.getParagraphDirection(n3) == -1) {
                selectionRectangleConsumer.accept(f, n2, this.getLineRight(n3), n4, 0);
            } else {
                selectionRectangleConsumer.accept(0.0f, n2, this.getLineLeft(n3), n4, 1);
            }
        }
    }

    public void getSelectionPath(int n, int n2, Path path) {
        path.reset();
        this.getSelection(n, n2, new _$$Lambda$Layout$MzjK2UE2G8VG0asK8_KWY3gHAmY(path));
    }

    public final float getSpacingAdd() {
        return this.mSpacingAdd;
    }

    public final float getSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public int getStartHyphenEdit(int n) {
        return 0;
    }

    public final CharSequence getText() {
        return this.mText;
    }

    public final TextDirectionHeuristic getTextDirectionHeuristic() {
        return this.mTextDir;
    }

    public abstract int getTopPadding();

    public final int getWidth() {
        return this.mWidth;
    }

    public final void increaseWidthTo(int n) {
        if (n >= this.mWidth) {
            this.mWidth = n;
            return;
        }
        throw new RuntimeException("attempted to reduce Layout width");
    }

    @UnsupportedAppUsage
    public boolean isLevelBoundary(int n) {
        int n2 = this.getLineForOffset(n);
        int[] arrn = this.getLineDirections(n2);
        Directions directions = DIRS_ALL_LEFT_TO_RIGHT;
        boolean bl = false;
        if (arrn != directions && arrn != DIRS_ALL_RIGHT_TO_LEFT) {
            arrn = arrn.mDirections;
            int n3 = this.getLineStart(n2);
            int n4 = this.getLineEnd(n2);
            if (n != n3 && n != n4) {
                for (n2 = 0; n2 < arrn.length; n2 += 2) {
                    if (n - n3 != arrn[n2]) continue;
                    return true;
                }
                return false;
            }
            n2 = this.getParagraphDirection(n2) == 1 ? 0 : 1;
            n = n == n3 ? 0 : arrn.length - 2;
            if ((arrn[n + 1] >>> 26 & 63) != n2) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean isRtlCharAt(int n) {
        int n2 = this.getLineForOffset(n);
        Directions directions = this.getLineDirections(n2);
        int[] arrn = DIRS_ALL_LEFT_TO_RIGHT;
        boolean bl = false;
        if (directions == arrn) {
            return false;
        }
        if (directions == DIRS_ALL_RIGHT_TO_LEFT) {
            return true;
        }
        arrn = directions.mDirections;
        int n3 = this.getLineStart(n2);
        for (n2 = 0; n2 < arrn.length; n2 += 2) {
            int n4 = arrn[n2] + n3;
            int n5 = arrn[n2 + 1];
            if (n < n4 || n >= (n5 & 67108863) + n4) continue;
            if ((arrn[n2 + 1] >>> 26 & 63 & 1) != 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    protected final boolean isSpanned() {
        return this.mSpannedText;
    }

    @VisibleForTesting
    public boolean primaryIsTrailingPrevious(int n) {
        int n2;
        int n3;
        int n4;
        int n5 = this.getLineForOffset(n);
        int n6 = this.getLineStart(n5);
        int n7 = this.getLineEnd(n5);
        int[] arrn = this.getLineDirections((int)n5).mDirections;
        int n8 = -1;
        int n9 = 0;
        do {
            n3 = n8;
            if (n9 >= arrn.length) break;
            n2 = arrn[n9] + n6;
            n3 = n4 = (arrn[n9 + 1] & 67108863) + n2;
            if (n4 > n7) {
                n3 = n7;
            }
            if (n >= n2 && n < n3) {
                if (n > n2) {
                    return false;
                }
                n3 = arrn[n9 + 1] >>> 26 & 63;
                break;
            }
            n9 += 2;
        } while (true);
        boolean bl = true;
        n9 = n3;
        if (n3 == -1) {
            n3 = this.getParagraphDirection(n5) == 1 ? 0 : 1;
            n9 = n3;
        }
        n8 = -1;
        if (n == n6) {
            n = this.getParagraphDirection(n5) == 1 ? 0 : 1;
        } else {
            n5 = n - 1;
            n3 = 0;
            do {
                n = n8;
                if (n3 >= arrn.length) break;
                n2 = arrn[n3] + n6;
                n = n4 = (arrn[n3 + 1] & 67108863) + n2;
                if (n4 > n7) {
                    n = n7;
                }
                if (n5 >= n2 && n5 < n) {
                    n = arrn[n3 + 1] >>> 26 & 63;
                    break;
                }
                n3 += 2;
            } while (true);
        }
        if (n >= n9) {
            bl = false;
        }
        return bl;
    }

    @VisibleForTesting
    public boolean[] primaryIsTrailingPreviousAllLineOffsets(int n) {
        int n2;
        int n3;
        int n4;
        int n5 = this.getLineStart(n);
        int n6 = this.getLineEnd(n);
        int[] arrn = this.getLineDirections((int)n).mDirections;
        boolean[] arrbl = new boolean[n6 - n5 + 1];
        byte[] arrby = new byte[n6 - n5 + 1];
        for (n3 = 0; n3 < arrn.length; n3 += 2) {
            int n7 = arrn[n3] + n5;
            n4 = n2 = (arrn[n3 + 1] & 67108863) + n7;
            if (n2 > n6) {
                n4 = n6;
            }
            if (n4 == n7) continue;
            arrby[n4 - n5 - 1] = (byte)(arrn[n3 + 1] >>> 26 & 63);
        }
        for (n4 = 0; n4 < arrn.length; n4 += 2) {
            n6 = arrn[n4] + n5;
            n2 = (byte)(arrn[n4 + 1] >>> 26 & 63);
            boolean bl = false;
            n3 = n6 == n5 ? (this.getParagraphDirection(n) == 1 ? 0 : 1) : arrby[n6 - n5 - 1];
            if (n2 > n3) {
                bl = true;
            }
            arrbl[n6 - n5] = bl;
        }
        return arrbl;
    }

    void replaceWith(CharSequence charSequence, TextPaint textPaint, int n, Alignment alignment, float f, float f2) {
        if (n >= 0) {
            this.mText = charSequence;
            this.mPaint = textPaint;
            this.mWidth = n;
            this.mAlignment = alignment;
            this.mSpacingMult = f;
            this.mSpacingAdd = f2;
            this.mSpannedText = charSequence instanceof Spanned;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Layout: ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" < 0");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    protected void setJustificationMode(int n) {
        this.mJustificationMode = n;
    }

    @UnsupportedAppUsage
    public boolean shouldClampCursor(int n) {
        int n2 = 1.$SwitchMap$android$text$Layout$Alignment[this.getParagraphAlignment(n).ordinal()];
        boolean bl = false;
        if (n2 != 1) {
            return n2 == 5;
        }
        if (this.getParagraphDirection(n) > 0) {
            bl = true;
        }
        return bl;
    }

    public static enum Alignment {
        ALIGN_NORMAL,
        ALIGN_OPPOSITE,
        ALIGN_CENTER,
        ALIGN_LEFT,
        ALIGN_RIGHT;
        
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BreakStrategy {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Direction {
    }

    public static class Directions {
        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public int[] mDirections;

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public Directions(int[] arrn) {
            this.mDirections = arrn;
        }

        public int getRunCount() {
            return this.mDirections.length / 2;
        }

        public int getRunLength(int n) {
            return this.mDirections[n * 2 + 1] & 67108863;
        }

        public int getRunStart(int n) {
            return this.mDirections[n * 2];
        }

        public boolean isRunRtl(int n) {
            int[] arrn = this.mDirections;
            boolean bl = true;
            if ((arrn[n * 2 + 1] & 67108864) == 0) {
                bl = false;
            }
            return bl;
        }
    }

    static class Ellipsizer
    implements CharSequence,
    GetChars {
        Layout mLayout;
        TextUtils.TruncateAt mMethod;
        CharSequence mText;
        int mWidth;

        public Ellipsizer(CharSequence charSequence) {
            this.mText = charSequence;
        }

        @Override
        public char charAt(int n) {
            char[] arrc = TextUtils.obtain(1);
            this.getChars(n, n + 1, arrc, 0);
            char c = arrc[0];
            TextUtils.recycle(arrc);
            return c;
        }

        @Override
        public void getChars(int n, int n2, char[] arrc, int n3) {
            int n4 = this.mLayout.getLineForOffset(n2);
            TextUtils.getChars(this.mText, n, n2, arrc, n3);
            for (int i = this.mLayout.getLineForOffset((int)n); i <= n4; ++i) {
                this.mLayout.ellipsize(n, n2, i, arrc, n3, this.mMethod);
            }
        }

        @Override
        public int length() {
            return this.mText.length();
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            char[] arrc = new char[n2 - n];
            this.getChars(n, n2, arrc, 0);
            return new String(arrc);
        }

        @Override
        public String toString() {
            char[] arrc = new char[this.length()];
            this.getChars(0, this.length(), arrc, 0);
            return new String(arrc);
        }
    }

    private class HorizontalMeasurementProvider {
        private float[] mHorizontals;
        private final int mLine;
        private int mLineStartOffset;
        private final boolean mPrimary;

        HorizontalMeasurementProvider(int n, boolean bl) {
            this.mLine = n;
            this.mPrimary = bl;
            this.init();
        }

        private void init() {
            if (Layout.this.getLineDirections(this.mLine) == DIRS_ALL_LEFT_TO_RIGHT) {
                return;
            }
            this.mHorizontals = Layout.this.getLineHorizontals(this.mLine, false, this.mPrimary);
            this.mLineStartOffset = Layout.this.getLineStart(this.mLine);
        }

        float get(int n) {
            int n2 = n - this.mLineStartOffset;
            float[] arrf = this.mHorizontals;
            if (arrf != null && n2 >= 0 && n2 < arrf.length) {
                return arrf[n2];
            }
            return Layout.this.getHorizontal(n, this.mPrimary);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HyphenationFrequency {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface JustificationMode {
    }

    @FunctionalInterface
    public static interface SelectionRectangleConsumer {
        public void accept(float var1, float var2, float var3, float var4, int var5);
    }

    static class SpannedEllipsizer
    extends Ellipsizer
    implements Spanned {
        private Spanned mSpanned;

        public SpannedEllipsizer(CharSequence charSequence) {
            super(charSequence);
            this.mSpanned = (Spanned)charSequence;
        }

        @Override
        public int getSpanEnd(Object object) {
            return this.mSpanned.getSpanEnd(object);
        }

        @Override
        public int getSpanFlags(Object object) {
            return this.mSpanned.getSpanFlags(object);
        }

        @Override
        public int getSpanStart(Object object) {
            return this.mSpanned.getSpanStart(object);
        }

        @Override
        public <T> T[] getSpans(int n, int n2, Class<T> class_) {
            return this.mSpanned.getSpans(n, n2, class_);
        }

        @Override
        public int nextSpanTransition(int n, int n2, Class class_) {
            return this.mSpanned.nextSpanTransition(n, n2, class_);
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            Object object = new char[n2 - n];
            this.getChars(n, n2, (char[])object, 0);
            object = new SpannableString(new String((char[])object));
            TextUtils.copySpansFrom(this.mSpanned, n, n2, Object.class, (Spannable)object, 0);
            return object;
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static class TabStops {
        private float mIncrement;
        private int mNumStops;
        private float[] mStops;

        public TabStops(float f, Object[] arrobject) {
            this.reset(f, arrobject);
        }

        public static float nextDefaultStop(float f, float f2) {
            return (float)((int)((f + f2) / f2)) * f2;
        }

        float nextTab(float f) {
            int n = this.mNumStops;
            if (n > 0) {
                float[] arrf = this.mStops;
                for (int i = 0; i < n; ++i) {
                    float f2 = arrf[i];
                    if (!(f2 > f)) continue;
                    return f2;
                }
            }
            return TabStops.nextDefaultStop(f, this.mIncrement);
        }

        void reset(float f, Object[] arrobject) {
            this.mIncrement = f;
            int n = 0;
            if (arrobject != null) {
                float[] arrf = this.mStops;
                int n2 = arrobject.length;
                n = 0;
                for (int i = 0; i < n2; ++i) {
                    Object object = arrobject[i];
                    int n3 = n;
                    float[] arrf2 = arrf;
                    if (object instanceof TabStopSpan) {
                        if (arrf == null) {
                            arrf2 = new float[10];
                        } else {
                            arrf2 = arrf;
                            if (n == arrf.length) {
                                arrf2 = new float[n * 2];
                                for (n3 = 0; n3 < n; ++n3) {
                                    arrf2[n3] = arrf[n3];
                                }
                            }
                        }
                        arrf2[n] = ((TabStopSpan)object).getTabStop();
                        n3 = n + 1;
                    }
                    n = n3;
                    arrf = arrf2;
                }
                if (n > 1) {
                    Arrays.sort(arrf, 0, n);
                }
                if (arrf != this.mStops) {
                    this.mStops = arrf;
                }
            }
            this.mNumStops = n;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TextSelectionLayout {
    }

}

