/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.SubtitleTrack;
import android.media.TextTrackCue;
import android.media.TextTrackCueSpan;
import android.media.TextTrackRegion;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import com.android.internal.widget.SubtitleView;
import java.util.ArrayList;
import java.util.Vector;

class WebVttRenderingWidget
extends ViewGroup
implements SubtitleTrack.RenderingWidget {
    private static final boolean DEBUG = false;
    private static final int DEBUG_CUE_BACKGROUND = -2130771968;
    private static final int DEBUG_REGION_BACKGROUND = -2147483393;
    private static final CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = CaptioningManager.CaptionStyle.DEFAULT;
    private static final float LINE_HEIGHT_RATIO = 0.0533f;
    private CaptioningManager.CaptionStyle mCaptionStyle;
    private final CaptioningManager.CaptioningChangeListener mCaptioningListener = new CaptioningManager.CaptioningChangeListener(){

        @Override
        public void onFontScaleChanged(float f) {
            float f2 = WebVttRenderingWidget.this.getHeight();
            WebVttRenderingWidget webVttRenderingWidget = WebVttRenderingWidget.this;
            webVttRenderingWidget.setCaptionStyle(webVttRenderingWidget.mCaptionStyle, f2 * f * 0.0533f);
        }

        @Override
        public void onUserStyleChanged(CaptioningManager.CaptionStyle captionStyle) {
            WebVttRenderingWidget webVttRenderingWidget = WebVttRenderingWidget.this;
            webVttRenderingWidget.setCaptionStyle(captionStyle, webVttRenderingWidget.mFontSize);
        }
    };
    private final ArrayMap<TextTrackCue, CueLayout> mCueBoxes = new ArrayMap();
    private float mFontSize;
    private boolean mHasChangeListener;
    private SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final CaptioningManager mManager;
    private final ArrayMap<TextTrackRegion, RegionLayout> mRegionBoxes = new ArrayMap();

    public WebVttRenderingWidget(Context context) {
        this(context, null);
    }

    public WebVttRenderingWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WebVttRenderingWidget(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public WebVttRenderingWidget(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.setLayerType(1, null);
        this.mManager = (CaptioningManager)context.getSystemService("captioning");
        this.mCaptionStyle = this.mManager.getUserStyle();
        this.mFontSize = this.mManager.getFontScale() * (float)this.getHeight() * 0.0533f;
    }

    private int calculateLinePosition(CueLayout cueLayout) {
        TextTrackCue textTrackCue = cueLayout.getCue();
        Integer n = textTrackCue.mLinePosition;
        boolean bl = textTrackCue.mSnapToLines;
        boolean bl2 = n == null;
        if (!(bl || bl2 || n >= 0 && n <= 100)) {
            return 100;
        }
        if (!bl2) {
            return n;
        }
        if (!bl) {
            return 100;
        }
        return -(cueLayout.mOrder + 1);
    }

    private void layoutCue(int n, int n2, CueLayout cueLayout) {
        TextTrackCue textTrackCue = cueLayout.getCue();
        int n3 = this.getLayoutDirection();
        int n4 = WebVttRenderingWidget.resolveCueAlignment(n3, textTrackCue.mAlignment);
        boolean bl = textTrackCue.mSnapToLines;
        int n5 = cueLayout.getMeasuredWidth() * 100 / n;
        int n6 = n4 != 203 ? (n4 != 204 ? textTrackCue.mTextPosition - n5 / 2 : textTrackCue.mTextPosition - n5) : textTrackCue.mTextPosition;
        n4 = n6;
        if (n3 == 1) {
            n4 = 100 - n6;
        }
        n3 = n5;
        int n7 = n4;
        if (bl) {
            n3 = this.getPaddingLeft() * 100 / n;
            int n8 = this.getPaddingRight() * 100 / n;
            int n9 = n5;
            n6 = n4;
            if (n4 < n3) {
                n9 = n5;
                n6 = n4;
                if (n4 + n5 > n3) {
                    n6 = n4 + n3;
                    n9 = n5 - n3;
                }
            }
            float f = 100 - n8;
            n3 = n9;
            n7 = n6;
            if ((float)n6 < f) {
                n3 = n9;
                n7 = n6;
                if ((float)(n6 + n9) > f) {
                    n3 = n9 - n8;
                    n7 = n6;
                }
            }
        }
        n4 = n7 * n / 100;
        n6 = n3 * n / 100;
        n = this.calculateLinePosition(cueLayout);
        n3 = cueLayout.getMeasuredHeight();
        n = n < 0 ? n2 + n * n3 : (n2 - n3) * n / 100;
        cueLayout.layout(n4, n, n4 + n6, n + n3);
    }

    private void layoutRegion(int n, int n2, RegionLayout regionLayout) {
        TextTrackRegion textTrackRegion = regionLayout.getRegion();
        int n3 = regionLayout.getMeasuredHeight();
        int n4 = regionLayout.getMeasuredWidth();
        float f = textTrackRegion.mViewportAnchorPointX;
        float f2 = textTrackRegion.mViewportAnchorPointY;
        n = (int)((float)(n - n4) * f / 100.0f);
        n2 = (int)((float)(n2 - n3) * f2 / 100.0f);
        regionLayout.layout(n, n2, n + n4, n2 + n3);
    }

    private void manageChangeListener() {
        boolean bl = this.isAttachedToWindow() && this.getVisibility() == 0;
        if (this.mHasChangeListener != bl) {
            this.mHasChangeListener = bl;
            if (bl) {
                this.mManager.addCaptioningChangeListener(this.mCaptioningListener);
                this.setCaptionStyle(this.mManager.getUserStyle(), this.mManager.getFontScale() * (float)this.getHeight() * 0.0533f);
            } else {
                this.mManager.removeCaptioningChangeListener(this.mCaptioningListener);
            }
        }
    }

    private void prepForPrune() {
        int n;
        int n2 = this.mRegionBoxes.size();
        for (n = 0; n < n2; ++n) {
            this.mRegionBoxes.valueAt(n).prepForPrune();
        }
        n2 = this.mCueBoxes.size();
        for (n = 0; n < n2; ++n) {
            this.mCueBoxes.valueAt(n).prepForPrune();
        }
    }

    private void prune() {
        LinearLayout linearLayout;
        int n;
        int n2;
        int n3 = this.mRegionBoxes.size();
        int n4 = 0;
        while (n4 < n3) {
            linearLayout = this.mRegionBoxes.valueAt(n4);
            n2 = n3;
            n = n4;
            if (((RegionLayout)linearLayout).prune()) {
                this.removeView(linearLayout);
                this.mRegionBoxes.removeAt(n4);
                n2 = n3 - 1;
                n = n4 - 1;
            }
            n4 = n + 1;
            n3 = n2;
        }
        n2 = this.mCueBoxes.size();
        n4 = 0;
        while (n4 < n2) {
            linearLayout = this.mCueBoxes.valueAt(n4);
            n3 = n2;
            n = n4;
            if (!((CueLayout)linearLayout).isActive()) {
                this.removeView(linearLayout);
                this.mCueBoxes.removeAt(n4);
                n3 = n2 - 1;
                n = n4 - 1;
            }
            n4 = n + 1;
            n2 = n3;
        }
    }

    private static int resolveCueAlignment(int n, int n2) {
        int n3 = 203;
        if (n2 != 201) {
            if (n2 != 202) {
                return n2;
            }
            if (n == 0) {
                n3 = 204;
            }
            return n3;
        }
        if (n != 0) {
            n3 = 204;
        }
        return n3;
    }

    private void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
        int n;
        this.mCaptionStyle = captionStyle = DEFAULT_CAPTION_STYLE.applyStyle(captionStyle);
        this.mFontSize = f;
        int n2 = this.mCueBoxes.size();
        for (n = 0; n < n2; ++n) {
            this.mCueBoxes.valueAt(n).setCaptionStyle(captionStyle, f);
        }
        n2 = this.mRegionBoxes.size();
        for (n = 0; n < n2; ++n) {
            this.mRegionBoxes.valueAt(n).setCaptionStyle(captionStyle, f);
        }
    }

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
        n3 -= n;
        n2 = n4 - n2;
        this.setCaptionStyle(this.mCaptionStyle, this.mManager.getFontScale() * 0.0533f * (float)n2);
        n4 = this.mRegionBoxes.size();
        for (n = 0; n < n4; ++n) {
            this.layoutRegion(n3, n2, this.mRegionBoxes.valueAt(n));
        }
        n4 = this.mCueBoxes.size();
        for (n = 0; n < n4; ++n) {
            this.layoutCue(n3, n2, this.mCueBoxes.valueAt(n));
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        super.onMeasure(n, n2);
        int n4 = this.mRegionBoxes.size();
        for (n3 = 0; n3 < n4; ++n3) {
            this.mRegionBoxes.valueAt(n3).measureForParent(n, n2);
        }
        n4 = this.mCueBoxes.size();
        for (n3 = 0; n3 < n4; ++n3) {
            this.mCueBoxes.valueAt(n3).measureForParent(n, n2);
        }
    }

    public void setActiveCues(Vector<SubtitleTrack.Cue> object) {
        Context context = this.getContext();
        CaptioningManager.CaptionStyle captionStyle = this.mCaptionStyle;
        float f = this.mFontSize;
        this.prepForPrune();
        int n = ((Vector)object).size();
        for (int i = 0; i < n; ++i) {
            LinearLayout linearLayout;
            LinearLayout linearLayout2;
            TextTrackCue textTrackCue = (TextTrackCue)((Vector)object).get(i);
            TextTrackRegion textTrackRegion = textTrackCue.mRegion;
            if (textTrackRegion != null) {
                linearLayout2 = linearLayout = this.mRegionBoxes.get(textTrackRegion);
                if (linearLayout == null) {
                    linearLayout2 = new RegionLayout(context, textTrackRegion, captionStyle, f);
                    this.mRegionBoxes.put(textTrackRegion, (RegionLayout)linearLayout2);
                    this.addView((View)linearLayout2, -2, -2);
                }
                ((RegionLayout)linearLayout2).put(textTrackCue);
                continue;
            }
            linearLayout2 = linearLayout = this.mCueBoxes.get(textTrackCue);
            if (linearLayout == null) {
                linearLayout2 = new CueLayout(context, textTrackCue, captionStyle, f);
                this.mCueBoxes.put(textTrackCue, (CueLayout)linearLayout2);
                this.addView((View)linearLayout2, -2, -2);
            }
            ((CueLayout)linearLayout2).update();
            ((CueLayout)linearLayout2).setOrder(i);
        }
        this.prune();
        this.setSize(this.getWidth(), this.getHeight());
        object = this.mListener;
        if (object != null) {
            object.onChanged(this);
        }
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

    private static class CueLayout
    extends LinearLayout {
        private boolean mActive;
        private CaptioningManager.CaptionStyle mCaptionStyle;
        public final TextTrackCue mCue;
        private float mFontSize;
        private int mOrder;

        public CueLayout(Context context, TextTrackCue textTrackCue, CaptioningManager.CaptionStyle captionStyle, float f) {
            super(context);
            this.mCue = textTrackCue;
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int n = textTrackCue.mWritingDirection;
            int n2 = 0;
            int n3 = 1;
            n = n == 100 ? 1 : 0;
            if (n != 0) {
                n2 = 1;
            }
            this.setOrientation(n2);
            switch (textTrackCue.mAlignment) {
                default: {
                    break;
                }
                case 204: {
                    this.setGravity(5);
                    break;
                }
                case 203: {
                    this.setGravity(3);
                    break;
                }
                case 202: {
                    this.setGravity(8388613);
                    break;
                }
                case 201: {
                    this.setGravity(8388611);
                    break;
                }
                case 200: {
                    n = n != 0 ? n3 : 16;
                    this.setGravity(n);
                }
            }
            this.update();
        }

        public TextTrackCue getCue() {
            return this.mCue;
        }

        public boolean isActive() {
            return this.mActive;
        }

        public void measureForParent(int n, int n2) {
            TextTrackCue textTrackCue = this.mCue;
            int n3 = View.MeasureSpec.getSize(n);
            n2 = View.MeasureSpec.getSize(n2);
            n = WebVttRenderingWidget.resolveCueAlignment(this.getLayoutDirection(), textTrackCue.mAlignment);
            n = n != 200 ? (n != 203 ? (n != 204 ? 0 : textTrackCue.mTextPosition) : 100 - textTrackCue.mTextPosition) : (textTrackCue.mTextPosition <= 50 ? textTrackCue.mTextPosition * 2 : (100 - textTrackCue.mTextPosition) * 2);
            this.measure(View.MeasureSpec.makeMeasureSpec(Math.min(textTrackCue.mSize, n) * n3 / 100, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(n2, Integer.MIN_VALUE));
        }

        @Override
        protected void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
        }

        public void prepForPrune() {
            this.mActive = false;
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                if (!(view instanceof SpanLayout)) continue;
                ((SpanLayout)view).setCaptionStyle(captionStyle, f);
            }
        }

        public void setOrder(int n) {
            this.mOrder = n;
        }

        public void update() {
            this.mActive = true;
            this.removeAllViews();
            int n = WebVttRenderingWidget.resolveCueAlignment(this.getLayoutDirection(), this.mCue.mAlignment);
            Layout.Alignment alignment = n != 203 ? (n != 204 ? Layout.Alignment.ALIGN_CENTER : Layout.Alignment.ALIGN_RIGHT) : Layout.Alignment.ALIGN_LEFT;
            CaptioningManager.CaptionStyle captionStyle = this.mCaptionStyle;
            float f = this.mFontSize;
            TextTrackCueSpan[][] arrtextTrackCueSpan = this.mCue.mLines;
            int n2 = arrtextTrackCueSpan.length;
            for (n = 0; n < n2; ++n) {
                SpanLayout spanLayout = new SpanLayout(this.getContext(), arrtextTrackCueSpan[n]);
                spanLayout.setAlignment(alignment);
                spanLayout.setCaptionStyle(captionStyle, f);
                this.addView((View)spanLayout, -2, -2);
            }
        }
    }

    private static class RegionLayout
    extends LinearLayout {
        private CaptioningManager.CaptionStyle mCaptionStyle;
        private float mFontSize;
        private final TextTrackRegion mRegion;
        private final ArrayList<CueLayout> mRegionCueBoxes = new ArrayList();

        public RegionLayout(Context context, TextTrackRegion textTrackRegion, CaptioningManager.CaptionStyle captionStyle, float f) {
            super(context);
            this.mRegion = textTrackRegion;
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            this.setOrientation(1);
            this.setBackgroundColor(captionStyle.windowColor);
        }

        public TextTrackRegion getRegion() {
            return this.mRegion;
        }

        public void measureForParent(int n, int n2) {
            TextTrackRegion textTrackRegion = this.mRegion;
            n = View.MeasureSpec.getSize(n);
            n2 = View.MeasureSpec.getSize(n2);
            this.measure(View.MeasureSpec.makeMeasureSpec((int)textTrackRegion.mWidth * n / 100, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(n2, Integer.MIN_VALUE));
        }

        public void prepForPrune() {
            int n = this.mRegionCueBoxes.size();
            for (int i = 0; i < n; ++i) {
                this.mRegionCueBoxes.get(i).prepForPrune();
            }
        }

        public boolean prune() {
            int n = this.mRegionCueBoxes.size();
            int n2 = 0;
            while (n2 < n) {
                CueLayout cueLayout = this.mRegionCueBoxes.get(n2);
                int n3 = n;
                int n4 = n2;
                if (!cueLayout.isActive()) {
                    this.mRegionCueBoxes.remove(n2);
                    this.removeView(cueLayout);
                    n3 = n - 1;
                    n4 = n2 - 1;
                }
                n2 = n4 + 1;
                n = n3;
            }
            return this.mRegionCueBoxes.isEmpty();
        }

        public void put(TextTrackCue object) {
            int n = this.mRegionCueBoxes.size();
            for (int i = 0; i < n; ++i) {
                CueLayout cueLayout = this.mRegionCueBoxes.get(i);
                if (cueLayout.getCue() != object) continue;
                cueLayout.update();
                return;
            }
            object = new CueLayout(this.getContext(), (TextTrackCue)object, this.mCaptionStyle, this.mFontSize);
            this.mRegionCueBoxes.add((CueLayout)object);
            this.addView((View)object, -2, -2);
            if (this.getChildCount() > this.mRegion.mLines) {
                this.removeViewAt(0);
            }
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
            this.mCaptionStyle = captionStyle;
            this.mFontSize = f;
            int n = this.mRegionCueBoxes.size();
            for (int i = 0; i < n; ++i) {
                this.mRegionCueBoxes.get(i).setCaptionStyle(captionStyle, f);
            }
            this.setBackgroundColor(captionStyle.windowColor);
        }
    }

    private static class SpanLayout
    extends SubtitleView {
        private final SpannableStringBuilder mBuilder = new SpannableStringBuilder();
        private final TextTrackCueSpan[] mSpans;

        public SpanLayout(Context context, TextTrackCueSpan[] arrtextTrackCueSpan) {
            super(context);
            this.mSpans = arrtextTrackCueSpan;
            this.update();
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle, float f) {
            this.setBackgroundColor(captionStyle.backgroundColor);
            this.setForegroundColor(captionStyle.foregroundColor);
            this.setEdgeColor(captionStyle.edgeColor);
            this.setEdgeType(captionStyle.edgeType);
            this.setTypeface(captionStyle.getTypeface());
            this.setTextSize(f);
        }

        public void update() {
            SpannableStringBuilder spannableStringBuilder = this.mBuilder;
            TextTrackCueSpan[] arrtextTrackCueSpan = this.mSpans;
            spannableStringBuilder.clear();
            spannableStringBuilder.clearSpans();
            int n = arrtextTrackCueSpan.length;
            for (int i = 0; i < n; ++i) {
                if (!arrtextTrackCueSpan[i].mEnabled) continue;
                spannableStringBuilder.append(arrtextTrackCueSpan[i].mText);
            }
            this.setText(spannableStringBuilder);
        }
    }

}

