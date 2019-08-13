/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.graphics.Paint;
import android.media.SubtitleTrack;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;

abstract class ClosedCaptionWidget
extends ViewGroup
implements SubtitleTrack.RenderingWidget {
    private static final CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = CaptioningManager.CaptionStyle.DEFAULT;
    protected CaptioningManager.CaptionStyle mCaptionStyle;
    private final CaptioningManager.CaptioningChangeListener mCaptioningListener = new CaptioningManager.CaptioningChangeListener(){

        @Override
        public void onFontScaleChanged(float f) {
            ClosedCaptionWidget.this.mClosedCaptionLayout.setFontScale(f);
        }

        @Override
        public void onUserStyleChanged(CaptioningManager.CaptionStyle captionStyle) {
            ClosedCaptionWidget.this.mCaptionStyle = DEFAULT_CAPTION_STYLE.applyStyle(captionStyle);
            ClosedCaptionWidget.this.mClosedCaptionLayout.setCaptionStyle(ClosedCaptionWidget.this.mCaptionStyle);
        }
    };
    protected ClosedCaptionLayout mClosedCaptionLayout;
    private boolean mHasChangeListener;
    protected SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final CaptioningManager mManager;

    public ClosedCaptionWidget(Context context) {
        this(context, null);
    }

    public ClosedCaptionWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClosedCaptionWidget(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ClosedCaptionWidget(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.setLayerType(1, null);
        this.mManager = (CaptioningManager)context.getSystemService("captioning");
        this.mCaptionStyle = DEFAULT_CAPTION_STYLE.applyStyle(this.mManager.getUserStyle());
        this.mClosedCaptionLayout = this.createCaptionLayout(context);
        this.mClosedCaptionLayout.setCaptionStyle(this.mCaptionStyle);
        this.mClosedCaptionLayout.setFontScale(this.mManager.getFontScale());
        this.addView((View)((ViewGroup)((Object)this.mClosedCaptionLayout)), -1, -1);
        this.requestLayout();
    }

    private void manageChangeListener() {
        boolean bl = this.isAttachedToWindow() && this.getVisibility() == 0;
        if (this.mHasChangeListener != bl) {
            this.mHasChangeListener = bl;
            if (bl) {
                this.mManager.addCaptioningChangeListener(this.mCaptioningListener);
            } else {
                this.mManager.removeCaptioningChangeListener(this.mCaptioningListener);
            }
        }
    }

    public abstract ClosedCaptionLayout createCaptionLayout(Context var1);

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.manageChangeListener();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.manageChangeListener();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        ((ViewGroup)((Object)this.mClosedCaptionLayout)).layout(n, n2, n3, n4);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        ((ViewGroup)((Object)this.mClosedCaptionLayout)).measure(n, n2);
    }

    @Override
    public void setOnChangedListener(SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener) {
        this.mListener = onChangedListener;
    }

    @Override
    public void setSize(int n, int n2) {
        this.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), View.MeasureSpec.makeMeasureSpec(n2, 1073741824));
        this.layout(0, 0, n, n2);
    }

    @Override
    public void setVisible(boolean bl) {
        if (bl) {
            this.setVisibility(0);
        } else {
            this.setVisibility(8);
        }
        this.manageChangeListener();
    }

    static interface ClosedCaptionLayout {
        public void setCaptionStyle(CaptioningManager.CaptionStyle var1);

        public void setFontScale(float var1);
    }

}

