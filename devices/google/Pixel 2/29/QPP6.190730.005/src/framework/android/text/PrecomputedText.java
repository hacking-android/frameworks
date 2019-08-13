/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.LocaleList;
import android.text.MeasuredParagraph;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Objects;

public class PrecomputedText
implements Spannable {
    private static final char LINE_FEED = '\n';
    private final int mEnd;
    private final ParagraphInfo[] mParagraphInfo;
    private final Params mParams;
    private final int mStart;
    private final SpannableString mText;

    private PrecomputedText(CharSequence charSequence, int n, int n2, Params params, ParagraphInfo[] arrparagraphInfo) {
        this.mText = new SpannableString(charSequence, true);
        this.mStart = n;
        this.mEnd = n2;
        this.mParams = params;
        this.mParagraphInfo = arrparagraphInfo;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PrecomputedText create(CharSequence charSequence, Params params) {
        ParagraphInfo[] arrparagraphInfo;
        ParagraphInfo[] arrparagraphInfo2 = arrparagraphInfo = null;
        if (charSequence instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText)charSequence;
            Params params2 = precomputedText.getParams();
            int n = params2.checkResultUsable(params.mPaint, params.mTextDir, params.mBreakStrategy, params.mHyphenationFrequency);
            if (n != 1) {
                if (n == 2) return precomputedText;
                arrparagraphInfo2 = arrparagraphInfo;
            } else {
                arrparagraphInfo2 = arrparagraphInfo;
                if (params.getBreakStrategy() == params2.getBreakStrategy()) {
                    arrparagraphInfo2 = arrparagraphInfo;
                    if (params.getHyphenationFrequency() == params2.getHyphenationFrequency()) {
                        arrparagraphInfo2 = PrecomputedText.createMeasuredParagraphsFromPrecomputedText(precomputedText, params, true);
                    }
                }
            }
        }
        arrparagraphInfo = arrparagraphInfo2;
        if (arrparagraphInfo2 != null) return new PrecomputedText(charSequence, 0, charSequence.length(), params, arrparagraphInfo);
        arrparagraphInfo = PrecomputedText.createMeasuredParagraphs(charSequence, params, 0, charSequence.length(), true);
        return new PrecomputedText(charSequence, 0, charSequence.length(), params, arrparagraphInfo);
    }

    public static ParagraphInfo[] createMeasuredParagraphs(CharSequence charSequence, Params params, int n, int n2, boolean bl) {
        ArrayList<ParagraphInfo> arrayList = new ArrayList<ParagraphInfo>();
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(params);
        boolean bl2 = params.getBreakStrategy() != 0 && params.getHyphenationFrequency() != 0;
        int n3 = n;
        while (n3 < n2) {
            n = TextUtils.indexOf(charSequence, '\n', n3, n2);
            n = n < 0 ? n2 : ++n;
            arrayList.add(new ParagraphInfo(n, MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), charSequence, n3, n, params.getTextDirection(), bl2, bl, null, null)));
            n3 = n;
        }
        return arrayList.toArray(new ParagraphInfo[arrayList.size()]);
    }

    private static ParagraphInfo[] createMeasuredParagraphsFromPrecomputedText(PrecomputedText precomputedText, Params params, boolean bl) {
        boolean bl2 = params.getBreakStrategy() != 0 && params.getHyphenationFrequency() != 0;
        ArrayList<ParagraphInfo> arrayList = new ArrayList<ParagraphInfo>();
        for (int i = 0; i < precomputedText.getParagraphCount(); ++i) {
            int n = precomputedText.getParagraphStart(i);
            int n2 = precomputedText.getParagraphEnd(i);
            arrayList.add(new ParagraphInfo(n2, MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), precomputedText, n, n2, params.getTextDirection(), bl2, bl, precomputedText.getMeasuredParagraph(i), null)));
        }
        return arrayList.toArray(new ParagraphInfo[arrayList.size()]);
    }

    @Override
    public char charAt(int n) {
        return this.mText.charAt(n);
    }

    public int checkResultUsable(int n, int n2, TextDirectionHeuristic textDirectionHeuristic, TextPaint textPaint, int n3, int n4) {
        if (this.mStart == n && this.mEnd == n2) {
            return this.mParams.checkResultUsable(textPaint, textDirectionHeuristic, n3, n4);
        }
        return 0;
    }

    public int findParaIndex(int n) {
        Object object;
        for (int i = 0; i < ((ParagraphInfo[])(object = this.mParagraphInfo)).length; ++i) {
            if (n >= object[i].paragraphEnd) continue;
            return i;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("pos must be less than ");
        ParagraphInfo[] arrparagraphInfo = this.mParagraphInfo;
        ((StringBuilder)object).append(arrparagraphInfo[arrparagraphInfo.length - 1].paragraphEnd);
        ((StringBuilder)object).append(", gave ");
        ((StringBuilder)object).append(n);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public void getBounds(int n, int n2, Rect object) {
        boolean bl = true;
        boolean bl2 = n >= 0 && n <= this.mText.length();
        Preconditions.checkArgument(bl2, "invalid start offset");
        bl2 = n2 >= 0 && n2 <= this.mText.length();
        Preconditions.checkArgument(bl2, "invalid end offset");
        bl2 = n <= n2 ? bl : false;
        Preconditions.checkArgument(bl2, "start offset can not be larger than end offset");
        Preconditions.checkNotNull(object);
        if (n == n2) {
            ((Rect)object).set(0, 0, 0, 0);
            return;
        }
        int n3 = this.findParaIndex(n);
        int n4 = this.getParagraphStart(n3);
        int n5 = this.getParagraphEnd(n3);
        if (n >= n4 && n5 >= n2) {
            this.getMeasuredParagraph(n3).getBounds(n - n4, n2 - n4, (Rect)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot measured across the paragraph:para: (");
        ((StringBuilder)object).append(n4);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n5);
        ((StringBuilder)object).append("), request: (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(")");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public float getCharWidthAt(int n) {
        boolean bl = n >= 0 && n < this.mText.length();
        Preconditions.checkArgument(bl, "invalid offset");
        int n2 = this.findParaIndex(n);
        int n3 = this.getParagraphStart(n2);
        this.getParagraphEnd(n2);
        return this.getMeasuredParagraph(n2).getCharWidthAt(n - n3);
    }

    public int getEnd() {
        return this.mEnd;
    }

    public MeasuredParagraph getMeasuredParagraph(int n) {
        return this.mParagraphInfo[n].measured;
    }

    public int getMemoryUsage() {
        int n = 0;
        for (int i = 0; i < this.getParagraphCount(); ++i) {
            n += this.getMeasuredParagraph(i).getMemoryUsage();
        }
        return n;
    }

    public int getParagraphCount() {
        return this.mParagraphInfo.length;
    }

    public int getParagraphEnd(int n) {
        Preconditions.checkArgumentInRange(n, 0, this.getParagraphCount(), "paraIndex");
        return this.mParagraphInfo[n].paragraphEnd;
    }

    public ParagraphInfo[] getParagraphInfo() {
        return this.mParagraphInfo;
    }

    public int getParagraphStart(int n) {
        Preconditions.checkArgumentInRange(n, 0, this.getParagraphCount(), "paraIndex");
        n = n == 0 ? this.mStart : this.getParagraphEnd(n - 1);
        return n;
    }

    public Params getParams() {
        return this.mParams;
    }

    @Override
    public int getSpanEnd(Object object) {
        return this.mText.getSpanEnd(object);
    }

    @Override
    public int getSpanFlags(Object object) {
        return this.mText.getSpanFlags(object);
    }

    @Override
    public int getSpanStart(Object object) {
        return this.mText.getSpanStart(object);
    }

    @Override
    public <T> T[] getSpans(int n, int n2, Class<T> class_) {
        return this.mText.getSpans(n, n2, class_);
    }

    public int getStart() {
        return this.mStart;
    }

    public CharSequence getText() {
        return this.mText;
    }

    public float getWidth(int n, int n2) {
        boolean bl = true;
        boolean bl2 = n >= 0 && n <= this.mText.length();
        Preconditions.checkArgument(bl2, "invalid start offset");
        bl2 = n2 >= 0 && n2 <= this.mText.length();
        Preconditions.checkArgument(bl2, "invalid end offset");
        bl2 = n <= n2 ? bl : false;
        Preconditions.checkArgument(bl2, "start offset can not be larger than end offset");
        if (n == n2) {
            return 0.0f;
        }
        int n3 = this.findParaIndex(n);
        int n4 = this.getParagraphStart(n3);
        int n5 = this.getParagraphEnd(n3);
        if (n >= n4 && n5 >= n2) {
            return this.getMeasuredParagraph(n3).getWidth(n - n4, n2 - n4);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot measured across the paragraph:para: (");
        stringBuilder.append(n4);
        stringBuilder.append(", ");
        stringBuilder.append(n5);
        stringBuilder.append("), request: (");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int length() {
        return this.mText.length();
    }

    @Override
    public int nextSpanTransition(int n, int n2, Class class_) {
        return this.mText.nextSpanTransition(n, n2, class_);
    }

    @Override
    public void removeSpan(Object object) {
        if (!(object instanceof MetricAffectingSpan)) {
            this.mText.removeSpan(object);
            return;
        }
        throw new IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
    }

    @Override
    public void setSpan(Object object, int n, int n2, int n3) {
        if (!(object instanceof MetricAffectingSpan)) {
            this.mText.setSpan(object, n, n2, n3);
            return;
        }
        throw new IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return PrecomputedText.create(this.mText.subSequence(n, n2), this.mParams);
    }

    @Override
    public String toString() {
        return this.mText.toString();
    }

    public static class ParagraphInfo {
        public final MeasuredParagraph measured;
        public final int paragraphEnd;

        public ParagraphInfo(int n, MeasuredParagraph measuredParagraph) {
            this.paragraphEnd = n;
            this.measured = measuredParagraph;
        }
    }

    public static final class Params {
        public static final int NEED_RECOMPUTE = 1;
        public static final int UNUSABLE = 0;
        public static final int USABLE = 2;
        private final int mBreakStrategy;
        private final int mHyphenationFrequency;
        private final TextPaint mPaint;
        private final TextDirectionHeuristic mTextDir;

        public Params(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int n, int n2) {
            this.mPaint = textPaint;
            this.mTextDir = textDirectionHeuristic;
            this.mBreakStrategy = n;
            this.mHyphenationFrequency = n2;
        }

        public int checkResultUsable(TextPaint textPaint, TextDirectionHeuristic textDirectionHeuristic, int n, int n2) {
            if (this.mBreakStrategy == n && this.mHyphenationFrequency == n2 && this.mPaint.equalsForTextMeasurement(textPaint)) {
                n = this.mTextDir == textDirectionHeuristic ? 2 : 1;
                return n;
            }
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (object != null && object instanceof Params) {
                object = (Params)object;
                if (this.checkResultUsable(((Params)object).mPaint, ((Params)object).mTextDir, ((Params)object).mBreakStrategy, ((Params)object).mHyphenationFrequency) != 2) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int getBreakStrategy() {
            return this.mBreakStrategy;
        }

        public int getHyphenationFrequency() {
            return this.mHyphenationFrequency;
        }

        public TextDirectionHeuristic getTextDirection() {
            return this.mTextDir;
        }

        public TextPaint getTextPaint() {
            return this.mPaint;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mPaint.getTextSize()), Float.valueOf(this.mPaint.getTextScaleX()), Float.valueOf(this.mPaint.getTextSkewX()), Float.valueOf(this.mPaint.getLetterSpacing()), Float.valueOf(this.mPaint.getWordSpacing()), this.mPaint.getFlags(), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), this.mPaint.getFontVariationSettings(), this.mPaint.isElegantTextHeight(), this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{textSize=");
            stringBuilder.append(this.mPaint.getTextSize());
            stringBuilder.append(", textScaleX=");
            stringBuilder.append(this.mPaint.getTextScaleX());
            stringBuilder.append(", textSkewX=");
            stringBuilder.append(this.mPaint.getTextSkewX());
            stringBuilder.append(", letterSpacing=");
            stringBuilder.append(this.mPaint.getLetterSpacing());
            stringBuilder.append(", textLocale=");
            stringBuilder.append(this.mPaint.getTextLocales());
            stringBuilder.append(", typeface=");
            stringBuilder.append(this.mPaint.getTypeface());
            stringBuilder.append(", variationSettings=");
            stringBuilder.append(this.mPaint.getFontVariationSettings());
            stringBuilder.append(", elegantTextHeight=");
            stringBuilder.append(this.mPaint.isElegantTextHeight());
            stringBuilder.append(", textDir=");
            stringBuilder.append(this.mTextDir);
            stringBuilder.append(", breakStrategy=");
            stringBuilder.append(this.mBreakStrategy);
            stringBuilder.append(", hyphenationFrequency=");
            stringBuilder.append(this.mHyphenationFrequency);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public static class Builder {
            private int mBreakStrategy = 1;
            private int mHyphenationFrequency = 1;
            private final TextPaint mPaint;
            private TextDirectionHeuristic mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;

            public Builder(Params params) {
                this.mPaint = params.mPaint;
                this.mTextDir = params.mTextDir;
                this.mBreakStrategy = params.mBreakStrategy;
                this.mHyphenationFrequency = params.mHyphenationFrequency;
            }

            public Builder(TextPaint textPaint) {
                this.mPaint = textPaint;
            }

            public Params build() {
                return new Params(this.mPaint, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
            }

            public Builder setBreakStrategy(int n) {
                this.mBreakStrategy = n;
                return this;
            }

            public Builder setHyphenationFrequency(int n) {
                this.mHyphenationFrequency = n;
                return this;
            }

            public Builder setTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
                this.mTextDir = textDirectionHeuristic;
                return this;
            }
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface CheckResultUsableResult {
        }

    }

}

