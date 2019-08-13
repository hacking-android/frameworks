/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.Cea708CCParser;
import android.media.Cea708CCWidget;
import android.media.MediaFormat;
import android.media.SubtitleTrack;
import java.util.Vector;

class Cea708CaptionTrack
extends SubtitleTrack {
    private final Cea708CCParser mCCParser;
    private final Cea708CCWidget mRenderingWidget;

    Cea708CaptionTrack(Cea708CCWidget cea708CCWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = cea708CCWidget;
        this.mCCParser = new Cea708CCParser(this.mRenderingWidget);
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

