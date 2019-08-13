/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.media.Cea708CCWidget;
import android.media.Cea708CaptionTrack;
import android.media.MediaFormat;
import android.media.SubtitleController;
import android.media.SubtitleTrack;

public class Cea708CaptionRenderer
extends SubtitleController.Renderer {
    private Cea708CCWidget mCCWidget;
    private final Context mContext;

    public Cea708CaptionRenderer(Context context) {
        this.mContext = context;
    }

    @Override
    public SubtitleTrack createTrack(MediaFormat mediaFormat) {
        if ("text/cea-708".equals(mediaFormat.getString("mime"))) {
            if (this.mCCWidget == null) {
                this.mCCWidget = new Cea708CCWidget(this.mContext);
            }
            return new Cea708CaptionTrack(this.mCCWidget, mediaFormat);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No matching format: ");
        stringBuilder.append(mediaFormat.toString());
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public boolean supports(MediaFormat mediaFormat) {
        if (mediaFormat.containsKey("mime")) {
            return "text/cea-708".equals(mediaFormat.getString("mime"));
        }
        return false;
    }
}

