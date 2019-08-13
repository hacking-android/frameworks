/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

@RemoteViews.RemoteView
public class ImageFloatingTextView
extends TextView {
    private int mImageEndMargin;
    private int mIndentLines;
    private int mLayoutMaxLines = -1;
    private int mMaxLinesForHeight = -1;
    private int mResolvedDirection = -1;

    public ImageFloatingTextView(Context context) {
        this(context, null);
    }

    public ImageFloatingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageFloatingTextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ImageFloatingTextView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected Layout makeSingleLayout(int n, BoringLayout.Metrics arrn, int n2, Layout.Alignment arrn2, boolean bl, TextUtils.TruncateAt truncateAt, boolean bl2) {
        TransformationMethod transformationMethod = this.getTransformationMethod();
        Object object = this.getText();
        arrn = object;
        if (transformationMethod != null) {
            arrn = transformationMethod.getTransformation((CharSequence)object, this);
        }
        if (arrn == null) {
            arrn = "";
        }
        object = StaticLayout.Builder.obtain((CharSequence)arrn, 0, arrn.length(), this.getPaint(), n).setAlignment((Layout.Alignment)arrn2).setTextDirection(this.getTextDirectionHeuristic()).setLineSpacing(this.getLineSpacingExtra(), this.getLineSpacingMultiplier()).setIncludePad(this.getIncludeFontPadding()).setUseLineSpacingFromFallbacks(true).setBreakStrategy(1).setHyphenationFrequency(2);
        n = this.mMaxLinesForHeight > 0 ? this.mMaxLinesForHeight : (this.getMaxLines() >= 0 ? this.getMaxLines() : Integer.MAX_VALUE);
        ((StaticLayout.Builder)object).setMaxLines(n);
        this.mLayoutMaxLines = n;
        if (bl) {
            ((StaticLayout.Builder)object).setEllipsize(truncateAt).setEllipsizedWidth(n2);
        }
        arrn = null;
        n = this.mIndentLines;
        if (n > 0) {
            arrn2 = new int[n + 1];
            n = 0;
            do {
                arrn = arrn2;
                if (n >= this.mIndentLines) break;
                arrn2[n] = this.mImageEndMargin;
                ++n;
            } while (true);
        }
        if (this.mResolvedDirection == 1) {
            ((StaticLayout.Builder)object).setIndents(arrn, null);
        } else {
            ((StaticLayout.Builder)object).setIndents(null, arrn);
        }
        return ((StaticLayout.Builder)object).build();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n2) - this.mPaddingTop - this.mPaddingBottom;
        if (this.getLayout() != null && this.getLayout().getHeight() != n3) {
            this.mMaxLinesForHeight = -1;
            this.nullLayouts();
        }
        super.onMeasure(n, n2);
        Layout layout2 = this.getLayout();
        if (layout2.getHeight() > n3) {
            int n4;
            for (n4 = layout2.getLineCount() - 1; n4 > 1 && layout2.getLineBottom(n4 - 1) > n3; --n4) {
            }
            n3 = n4;
            if (this.getMaxLines() > 0) {
                n3 = Math.min(this.getMaxLines(), n4);
            }
            if (n3 != this.mLayoutMaxLines) {
                this.mMaxLinesForHeight = n3;
                this.nullLayouts();
                super.onMeasure(n, n2);
            }
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        if (n != this.mResolvedDirection && this.isLayoutDirectionResolved()) {
            this.mResolvedDirection = n;
            if (this.mIndentLines > 0) {
                this.nullLayouts();
                this.requestLayout();
            }
        }
    }

    @RemotableViewMethod
    public void setHasImage(boolean bl) {
        int n = bl ? 2 : 0;
        this.setNumIndentLines(n);
    }

    @RemotableViewMethod
    public void setImageEndMargin(int n) {
        this.mImageEndMargin = n;
    }

    public boolean setNumIndentLines(int n) {
        if (this.mIndentLines != n) {
            this.mIndentLines = n;
            this.nullLayouts();
            this.requestLayout();
            return true;
        }
        return false;
    }
}

