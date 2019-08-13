/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;

public class SubtitleView
extends View {
    private static final int COLOR_BEVEL_DARK = Integer.MIN_VALUE;
    private static final int COLOR_BEVEL_LIGHT = -2130706433;
    private static final float INNER_PADDING_RATIO = 0.125f;
    private Layout.Alignment mAlignment = Layout.Alignment.ALIGN_CENTER;
    private int mBackgroundColor;
    private final float mCornerRadius;
    private int mEdgeColor;
    private int mEdgeType;
    private int mForegroundColor;
    private boolean mHasMeasurements;
    private int mInnerPaddingX = 0;
    private int mLastMeasuredWidth;
    private StaticLayout mLayout;
    private final RectF mLineBounds = new RectF();
    private final float mOutlineWidth;
    private Paint mPaint;
    private final float mShadowOffsetX;
    private final float mShadowOffsetY;
    private final float mShadowRadius;
    private float mSpacingAdd = 0.0f;
    private float mSpacingMult = 1.0f;
    private final SpannableStringBuilder mText = new SpannableStringBuilder();
    private TextPaint mTextPaint;

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SubtitleView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SubtitleView(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2);
        object2 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.TextView, n, n2);
        object = "";
        n2 = 15;
        int n3 = ((TypedArray)object2).getIndexCount();
        for (n = 0; n < n3; ++n) {
            int n4 = ((TypedArray)object2).getIndex(n);
            if (n4 != 0) {
                if (n4 != 18) {
                    if (n4 != 53) {
                        if (n4 != 54) continue;
                        this.mSpacingMult = ((TypedArray)object2).getFloat(n4, this.mSpacingMult);
                        continue;
                    }
                    this.mSpacingAdd = ((TypedArray)object2).getDimensionPixelSize(n4, (int)this.mSpacingAdd);
                    continue;
                }
                object = ((TypedArray)object2).getText(n4);
                continue;
            }
            n2 = ((TypedArray)object2).getDimensionPixelSize(n4, n2);
        }
        object2 = this.getContext().getResources();
        this.mCornerRadius = ((Resources)object2).getDimensionPixelSize(17105428);
        this.mOutlineWidth = ((Resources)object2).getDimensionPixelSize(17105429);
        this.mShadowRadius = ((Resources)object2).getDimensionPixelSize(17105431);
        this.mShadowOffsetY = this.mShadowOffsetX = (float)((Resources)object2).getDimensionPixelSize(17105430);
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setSubpixelText(true);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.setText((CharSequence)object);
        this.setTextSize(n2);
    }

    private boolean computeMeasurements(int n) {
        if (this.mHasMeasurements && n == this.mLastMeasuredWidth) {
            return true;
        }
        if ((n -= this.mPaddingLeft + this.mPaddingRight + this.mInnerPaddingX * 2) <= 0) {
            return false;
        }
        this.mHasMeasurements = true;
        this.mLastMeasuredWidth = n;
        SpannableStringBuilder spannableStringBuilder = this.mText;
        this.mLayout = StaticLayout.Builder.obtain(spannableStringBuilder, 0, spannableStringBuilder.length(), this.mTextPaint, n).setAlignment(this.mAlignment).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setUseLineSpacingFromFallbacks(true).build();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int n;
        float f;
        StaticLayout staticLayout = this.mLayout;
        if (staticLayout == null) {
            return;
        }
        int n2 = canvas.save();
        int n3 = this.mInnerPaddingX;
        canvas.translate(this.mPaddingLeft + n3, this.mPaddingTop);
        int n4 = staticLayout.getLineCount();
        TextPaint textPaint = this.mTextPaint;
        Paint paint = this.mPaint;
        RectF rectF = this.mLineBounds;
        if (Color.alpha(this.mBackgroundColor) > 0) {
            float f2 = this.mCornerRadius;
            f = staticLayout.getLineTop(0);
            paint.setColor(this.mBackgroundColor);
            paint.setStyle(Paint.Style.FILL);
            for (n = 0; n < n4; ++n) {
                rectF.left = staticLayout.getLineLeft(n) - (float)n3;
                rectF.right = staticLayout.getLineRight(n) + (float)n3;
                rectF.top = f;
                f = rectF.bottom = (float)staticLayout.getLineBottom(n);
                canvas.drawRoundRect(rectF, f2, f2, paint);
            }
        }
        n = this.mEdgeType;
        n3 = 1;
        if (n == 1) {
            textPaint.setStrokeJoin(Paint.Join.ROUND);
            textPaint.setStrokeWidth(this.mOutlineWidth);
            textPaint.setColor(this.mEdgeColor);
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            for (n = 0; n < n4; ++n) {
                staticLayout.drawText(canvas, n, n);
            }
        } else if (n == 2) {
            textPaint.setShadowLayer(this.mShadowRadius, this.mShadowOffsetX, this.mShadowOffsetY, this.mEdgeColor);
        } else if (n == 3 || n == 4) {
            if (n != 3) {
                n3 = 0;
            }
            n = -1;
            int n5 = n3 != 0 ? -1 : this.mEdgeColor;
            if (n3 != 0) {
                n = this.mEdgeColor;
            }
            f = this.mShadowRadius / 2.0f;
            textPaint.setColor(this.mForegroundColor);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setShadowLayer(this.mShadowRadius, -f, -f, n5);
            for (n3 = 0; n3 < n4; ++n3) {
                staticLayout.drawText(canvas, n3, n3);
            }
            textPaint.setShadowLayer(this.mShadowRadius, f, f, n);
        }
        textPaint.setColor(this.mForegroundColor);
        textPaint.setStyle(Paint.Style.FILL);
        for (n = 0; n < n4; ++n) {
            staticLayout.drawText(canvas, n, n);
        }
        textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        canvas.restoreToCount(n2);
    }

    @Override
    public void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.computeMeasurements(n3 - n);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.computeMeasurements(View.MeasureSpec.getSize(n))) {
            StaticLayout staticLayout = this.mLayout;
            int n3 = this.mPaddingLeft;
            n2 = this.mPaddingRight;
            n = this.mInnerPaddingX;
            this.setMeasuredDimension(staticLayout.getWidth() + (n3 + n2 + n * 2), staticLayout.getHeight() + this.mPaddingTop + this.mPaddingBottom);
        } else {
            this.setMeasuredDimension(16777216, 16777216);
        }
    }

    public void setAlignment(Layout.Alignment alignment) {
        if (this.mAlignment != alignment) {
            this.mAlignment = alignment;
            this.mHasMeasurements = false;
            this.requestLayout();
            this.invalidate();
        }
    }

    @Override
    public void setBackgroundColor(int n) {
        this.mBackgroundColor = n;
        this.invalidate();
    }

    public void setEdgeColor(int n) {
        this.mEdgeColor = n;
        this.invalidate();
    }

    public void setEdgeType(int n) {
        this.mEdgeType = n;
        this.invalidate();
    }

    public void setForegroundColor(int n) {
        this.mForegroundColor = n;
        this.invalidate();
    }

    public void setStyle(int n) {
        Object object = this.mContext.getContentResolver();
        object = n == -1 ? CaptioningManager.CaptionStyle.getCustomStyle((ContentResolver)object) : CaptioningManager.CaptionStyle.PRESETS[n];
        CaptioningManager.CaptionStyle captionStyle = CaptioningManager.CaptionStyle.DEFAULT;
        n = ((CaptioningManager.CaptionStyle)object).hasForegroundColor() ? ((CaptioningManager.CaptionStyle)object).foregroundColor : captionStyle.foregroundColor;
        this.mForegroundColor = n;
        n = ((CaptioningManager.CaptionStyle)object).hasBackgroundColor() ? ((CaptioningManager.CaptionStyle)object).backgroundColor : captionStyle.backgroundColor;
        this.mBackgroundColor = n;
        n = ((CaptioningManager.CaptionStyle)object).hasEdgeType() ? ((CaptioningManager.CaptionStyle)object).edgeType : captionStyle.edgeType;
        this.mEdgeType = n;
        n = ((CaptioningManager.CaptionStyle)object).hasEdgeColor() ? ((CaptioningManager.CaptionStyle)object).edgeColor : captionStyle.edgeColor;
        this.mEdgeColor = n;
        this.mHasMeasurements = false;
        this.setTypeface(((CaptioningManager.CaptionStyle)object).getTypeface());
        this.requestLayout();
    }

    public void setText(int n) {
        this.setText(this.getContext().getText(n));
    }

    public void setText(CharSequence charSequence) {
        this.mText.clear();
        this.mText.append(charSequence);
        this.mHasMeasurements = false;
        this.requestLayout();
        this.invalidate();
    }

    public void setTextSize(float f) {
        if (this.mTextPaint.getTextSize() != f) {
            this.mTextPaint.setTextSize(f);
            this.mInnerPaddingX = (int)(0.125f * f + 0.5f);
            this.mHasMeasurements = false;
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            this.mHasMeasurements = false;
            this.requestLayout();
            this.invalidate();
        }
    }
}

