/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.media.MediaFormat;
import android.media.SRTTrack;
import android.media.SubtitleController;
import android.media.SubtitleTrack;
import android.media.WebVttRenderingWidget;
import android.os.Handler;

public class SRTRenderer
extends SubtitleController.Renderer {
    private final Context mContext;
    private final Handler mEventHandler;
    private final boolean mRender;
    private WebVttRenderingWidget mRenderingWidget;

    public SRTRenderer(Context context) {
        this(context, null);
    }

    SRTRenderer(Context context, Handler handler) {
        this.mContext = context;
        boolean bl = handler == null;
        this.mRender = bl;
        this.mEventHandler = handler;
    }

    @Override
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if (this.mRender && this.mRenderingWidget == null) {
            this.mRenderingWidget = new WebVttRenderingWidget(this.mContext);
        }
        if (this.mRender) {
            return new SRTTrack(this.mRenderingWidget, mediaFormat);
        }
        return new SRTTrack(this.mEventHandler, mediaFormat);
    }

    @Override
    public boolean supports(MediaFormat mediaFormat) {
        boolean bl = mediaFormat.containsKey("mime");
        boolean bl2 = false;
        if (bl) {
            if (!mediaFormat.getString("mime").equals("application/x-subrip")) {
                return false;
            }
            boolean bl3 = this.mRender;
            bl = mediaFormat.getInteger("is-timed-text", 0) == 0;
            if (bl3 == bl) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }
}

