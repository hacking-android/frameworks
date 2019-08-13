/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.Cea608CCParser;
import android.media.ClosedCaptionWidget;
import android.media.SubtitleTrack;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import android.widget.TextView;

class Cea608CCWidget
extends ClosedCaptionWidget
implements Cea608CCParser.DisplayListener {
    private static final String mDummyText = "1234567890123456789012345678901234";
    private static final Rect mTextBounds = new Rect();

    public Cea608CCWidget(Context context) {
        this(context, null);
    }

    public Cea608CCWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Cea608CCWidget(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Cea608CCWidget(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(Context context) {
        return new CCLayout(context);
    }

    @Override
    public CaptioningManager.CaptionStyle getCaptionStyle() {
        return this.mCaptionStyle;
    }

    @Override
    public void onDisplayChanged(SpannableStringBuilder[] arrspannableStringBuilder) {
        ((CCLayout)this.mClosedCaptionLayout).update(arrspannableStringBuilder);
        if (this.mListener != null) {
            this.mListener.onChanged(this);
        }
    }

    private static class CCLayout
    extends LinearLayout
    implements ClosedCaptionWidget.ClosedCaptionLayout {
        private static final int MAX_ROWS = 15;
        private static final float SAFE_AREA_RATIO = 0.9f;
        private final CCLineBox[] mLineBoxes = new CCLineBox[15];

        CCLayout(Context context) {
            super(context);
            this.setGravity(8388611);
            this.setOrientation(1);
            for (int i = 0; i < 15; ++i) {
                this.mLineBoxes[i] = new CCLineBox(this.getContext());
                this.addView((View)this.mLineBoxes[i], -2, -2);
            }
        }

        @Override
        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
            if ((n3 -= n) * 3 >= (n4 -= n2) * 4) {
                n2 = n4 * 4 / 3;
                n = n4;
            } else {
                n2 = n3;
                n = n3 * 3 / 4;
            }
            n2 = (int)((float)n2 * 0.9f);
            int n5 = (int)((float)n * 0.9f);
            n3 = (n3 - n2) / 2;
            n4 = (n4 - n5) / 2;
            for (n = 0; n < 15; ++n) {
                this.mLineBoxes[n].layout(n3, n5 * n / 15 + n4, n3 + n2, (n + 1) * n5 / 15 + n4);
            }
        }

        @Override
        protected void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            n2 = this.getMeasuredWidth();
            n = this.getMeasuredHeight();
            if (n2 * 3 >= n * 4) {
                n2 = n * 4 / 3;
            } else {
                n = n2 * 3 / 4;
            }
            int n3 = (int)((float)n2 * 0.9f);
            n2 = View.MeasureSpec.makeMeasureSpec((int)((float)n * 0.9f) / 15, 1073741824);
            n3 = View.MeasureSpec.makeMeasureSpec(n3, 1073741824);
            for (n = 0; n < 15; ++n) {
                this.mLineBoxes[n].measure(n3, n2);
            }
        }

        @Override
        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
            for (int i = 0; i < 15; ++i) {
                this.mLineBoxes[i].setCaptionStyle(captionStyle);
            }
        }

        @Override
        public void setFontScale(float f) {
        }

        void update(SpannableStringBuilder[] arrspannableStringBuilder) {
            for (int i = 0; i < 15; ++i) {
                if (arrspannableStringBuilder[i] != null) {
                    this.mLineBoxes[i].setText(arrspannableStringBuilder[i], TextView.BufferType.SPANNABLE);
                    this.mLineBoxes[i].setVisibility(0);
                    continue;
                }
                this.mLineBoxes[i].setVisibility(4);
            }
        }
    }

    private static class CCLineBox
    extends TextView {
        private static final float EDGE_OUTLINE_RATIO = 0.1f;
        private static final float EDGE_SHADOW_RATIO = 0.05f;
        private static final float FONT_PADDING_RATIO = 0.75f;
        private int mBgColor = -16777216;
        private int mEdgeColor = 0;
        private int mEdgeType = 0;
        private float mOutlineWidth;
        private float mShadowOffset;
        private float mShadowRadius;
        private int mTextColor = -1;

        CCLineBox(Context object) {
            super((Context)object);
            this.setGravity(17);
            this.setBackgroundColor(0);
            this.setTextColor(-1);
            this.setTypeface(Typeface.MONOSPACE);
            this.setVisibility(4);
            object = this.getContext().getResources();
            this.mOutlineWidth = ((Resources)object).getDimensionPixelSize(17105429);
            this.mShadowRadius = ((Resources)object).getDimensionPixelSize(17105431);
            this.mShadowOffset = ((Resources)object).getDimensionPixelSize(17105430);
        }

        private void drawEdgeOutline(Canvas canvas) {
            TextPaint textPaint = this.getPaint();
            Paint.Style style2 = textPaint.getStyle();
            Paint.Join join = textPaint.getStrokeJoin();
            float f = textPaint.getStrokeWidth();
            this.setTextColor(this.mEdgeColor);
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            textPaint.setStrokeJoin(Paint.Join.ROUND);
            textPaint.setStrokeWidth(this.mOutlineWidth);
            super.onDraw(canvas);
            this.setTextColor(this.mTextColor);
            textPaint.setStyle(style2);
            textPaint.setStrokeJoin(join);
            textPaint.setStrokeWidth(f);
            this.setBackgroundSpans(0);
            super.onDraw(canvas);
            this.setBackgroundSpans(this.mBgColor);
        }

        private void drawEdgeRaisedOrDepressed(Canvas canvas) {
            TextPaint textPaint = this.getPaint();
            Paint.Style style2 = textPaint.getStyle();
            textPaint.setStyle(Paint.Style.FILL);
            boolean bl = this.mEdgeType == 3;
            int n = -1;
            int n2 = bl ? -1 : this.mEdgeColor;
            if (bl) {
                n = this.mEdgeColor;
            }
            float f = this.mShadowRadius;
            float f2 = f / 2.0f;
            this.setShadowLayer(f, -f2, -f2, n2);
            super.onDraw(canvas);
            this.setBackgroundSpans(0);
            this.setShadowLayer(this.mShadowRadius, f2, f2, n);
            super.onDraw(canvas);
            textPaint.setStyle(style2);
            this.setBackgroundSpans(this.mBgColor);
        }

        private void setBackgroundSpans(int n) {
            Cea608CCParser.MutableBackgroundColorSpan[] arrmutableBackgroundColorSpan = this.getText();
            if (arrmutableBackgroundColorSpan instanceof Spannable) {
                arrmutableBackgroundColorSpan = (Spannable)arrmutableBackgroundColorSpan;
                arrmutableBackgroundColorSpan = arrmutableBackgroundColorSpan.getSpans(0, arrmutableBackgroundColorSpan.length(), Cea608CCParser.MutableBackgroundColorSpan.class);
                for (int i = 0; i < arrmutableBackgroundColorSpan.length; ++i) {
                    arrmutableBackgroundColorSpan[i].setBackgroundColor(n);
                }
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int n = this.mEdgeType;
            if (n != -1 && n != 0 && n != 2) {
                if (n == 1) {
                    this.drawEdgeOutline(canvas);
                } else {
                    this.drawEdgeRaisedOrDepressed(canvas);
                }
                return;
            }
            super.onDraw(canvas);
        }

        @Override
        protected void onMeasure(int n, int n2) {
            float f = (float)View.MeasureSpec.getSize(n2) * 0.75f;
            this.setTextSize(0, f);
            this.mOutlineWidth = 0.1f * f + 1.0f;
            this.mShadowOffset = this.mShadowRadius = 0.05f * f + 1.0f;
            this.setScaleX(1.0f);
            this.getPaint().getTextBounds(Cea608CCWidget.mDummyText, 0, Cea608CCWidget.mDummyText.length(), mTextBounds);
            f = mTextBounds.width();
            this.setScaleX((float)View.MeasureSpec.getSize(n) / f);
            super.onMeasure(n, n2);
        }

        void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
            this.mTextColor = captionStyle.foregroundColor;
            this.mBgColor = captionStyle.backgroundColor;
            this.mEdgeType = captionStyle.edgeType;
            this.mEdgeColor = captionStyle.edgeColor;
            this.setTextColor(this.mTextColor);
            if (this.mEdgeType == 2) {
                float f = this.mShadowRadius;
                float f2 = this.mShadowOffset;
                this.setShadowLayer(f, f2, f2, this.mEdgeColor);
            } else {
                this.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            }
            this.invalidate();
        }
    }

}

