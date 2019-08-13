/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.graphics.Paint;
import android.media.SubtitleTrack;
import android.media.TtmlCue;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Vector;

class TtmlRenderingWidget
extends LinearLayout
implements SubtitleTrack.RenderingWidget {
    private SubtitleTrack.RenderingWidget.OnChangedListener mListener;
    private final TextView mTextView;

    public TtmlRenderingWidget(Context context) {
        this(context, null);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TtmlRenderingWidget(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TtmlRenderingWidget(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        this.setLayerType(1, null);
        object = (CaptioningManager)context.getSystemService("captioning");
        this.mTextView = new TextView(context);
        this.mTextView.setTextColor(object.getUserStyle().foregroundColor);
        this.addView((View)this.mTextView, -1, -1);
        this.mTextView.setGravity(81);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setActiveCues(Vector<SubtitleTrack.Cue> object) {
        int n = ((Vector)object).size();
        String string2 = "";
        for (int i = 0; i < n; ++i) {
            TtmlCue ttmlCue = (TtmlCue)((Vector)object).get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(ttmlCue.mText);
            stringBuilder.append("\n");
            string2 = stringBuilder.toString();
        }
        this.mTextView.setText(string2);
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
    }
}

