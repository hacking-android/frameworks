/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.SubtitleTrack;

class TtmlCue
extends SubtitleTrack.Cue {
    public String mText;
    public String mTtmlFragment;

    public TtmlCue(long l, long l2, String string2, String string3) {
        this.mStartTimeMs = l;
        this.mEndTimeMs = l2;
        this.mText = string2;
        this.mTtmlFragment = string3;
    }
}

