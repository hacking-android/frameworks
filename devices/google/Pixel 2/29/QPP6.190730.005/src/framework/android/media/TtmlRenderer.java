/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.media.MediaFormat;
import android.media.SubtitleController;
import android.media.SubtitleTrack;
import android.media.TtmlRenderingWidget;
import android.media.TtmlTrack;

public class TtmlRenderer
extends SubtitleController.Renderer {
    private static final String MEDIA_MIMETYPE_TEXT_TTML = "application/ttml+xml";
    private final Context mContext;
    private TtmlRenderingWidget mRenderingWidget;

    @UnsupportedAppUsage
    public TtmlRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (this.mRenderingWidget == null) {
            this.mRenderingWidget = new TtmlRenderingWidget(this.mContext);
        }
        return new TtmlTrack(this.mRenderingWidget, mediaFormat);
    }

    @Override
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return mediaFormat.getString("mime").equals(MEDIA_MIMETYPE_TEXT_TTML);
        }
        return false;
    }
}

