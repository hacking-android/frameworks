/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.TextTrackCue;
import android.media.TextTrackRegion;

interface WebVttCueListener {
    public void onCueParsed(TextTrackCue var1);

    public void onRegionParsed(TextTrackRegion var1);
}

