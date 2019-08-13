/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.media.MediaFormat;
import android.media.SubtitleController;
import android.media.SubtitleTrack;
import android.media.WebVttRenderingWidget;
import android.media.WebVttTrack;

public class WebVttRenderer
extends SubtitleController.Renderer {
    private final Context mContext;
    private WebVttRenderingWidget mRenderingWidget;

    @UnsupportedAppUsage
    public WebVttRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (this.mRenderingWidget == null) {
            this.mRenderingWidget = new WebVttRenderingWidget(this.mContext);
        }
        return new WebVttTrack(this.mRenderingWidget, mediaFormat);
    }

    @Override
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return mediaFormat.getString("mime").equals("text/vtt");
        }
        return false;
    }
}

