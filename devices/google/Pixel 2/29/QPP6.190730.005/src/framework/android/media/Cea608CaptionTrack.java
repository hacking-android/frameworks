/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.Cea608CCParser;
import android.media.Cea608CCWidget;
import android.media.MediaFormat;
import android.media.SubtitleTrack;
import java.util.Vector;

class Cea608CaptionTrack
extends SubtitleTrack {
    private final Cea608CCParser mCCParser;
    private final Cea608CCWidget mRenderingWidget;

    Cea608CaptionTrack(Cea608CCWidget cea608CCWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = cea608CCWidget;
        this.mCCParser = new Cea608CCParser(this.mRenderingWidget);
    }

    @Override
    public SubtitleTrack.RenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }

    @Override
    public void onData(byte[] arrby, boolean bl, long l) {
        this.mCCParser.parse(arrby);
    }

    @Override
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
    }
}

