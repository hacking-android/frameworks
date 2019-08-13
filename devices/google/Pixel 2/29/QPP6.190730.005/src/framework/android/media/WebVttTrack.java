/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaFormat;
import android.media.MediaTimeProvider;
import android.media.SubtitleTrack;
import android.media.TextTrackCue;
import android.media.TextTrackCueSpan;
import android.media.TextTrackRegion;
import android.media.Tokenizer;
import android.media.UnstyledTextExtractor;
import android.media.WebVttCueListener;
import android.media.WebVttParser;
import android.media.WebVttRenderingWidget;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class WebVttTrack
extends SubtitleTrack
implements WebVttCueListener {
    private static final String TAG = "WebVttTrack";
    private Long mCurrentRunID;
    private final UnstyledTextExtractor mExtractor = new UnstyledTextExtractor();
    private final WebVttParser mParser = new WebVttParser(this);
    private final Map<String, TextTrackRegion> mRegions = new HashMap<String, TextTrackRegion>();
    private final WebVttRenderingWidget mRenderingWidget;
    private final Vector<Long> mTimestamps = new Vector();
    private final Tokenizer mTokenizer = new Tokenizer(this.mExtractor);

    WebVttTrack(WebVttRenderingWidget webVttRenderingWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = webVttRenderingWidget;
    }

    @Override
    public WebVttRenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCueParsed(TextTrackCue textTrackCue) {
        WebVttParser webVttParser = this.mParser;
        synchronized (webVttParser) {
            int n;
            Object object;
            if (textTrackCue.mRegionId.length() != 0) {
                textTrackCue.mRegion = this.mRegions.get(textTrackCue.mRegionId);
            }
            if (this.DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("adding cue ");
                ((StringBuilder)object).append(textTrackCue);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            this.mTokenizer.reset();
            for (Object object2 : textTrackCue.mStrings) {
                this.mTokenizer.tokenize((String)object2);
            }
            textTrackCue.mLines = this.mExtractor.getText();
            if (this.DEBUG) {
                object = new StringBuilder();
                object = textTrackCue.appendStringsToBuilder((StringBuilder)object);
                ((StringBuilder)object).append(" simplified to: ");
                Log.v(TAG, textTrackCue.appendLinesToBuilder((StringBuilder)object).toString());
            }
            object = textTrackCue.mLines;
            int n2 = ((String[])object).length;
            for (n = 0; n < n2; ++n) {
                for (Object object2 : object[n]) {
                    if (((TextTrackCueSpan)object2).mTimestampMs <= textTrackCue.mStartTimeMs || ((TextTrackCueSpan)object2).mTimestampMs >= textTrackCue.mEndTimeMs || this.mTimestamps.contains(((TextTrackCueSpan)object2).mTimestampMs)) continue;
                    this.mTimestamps.add(((TextTrackCueSpan)object2).mTimestampMs);
                }
            }
            if (this.mTimestamps.size() > 0) {
                textTrackCue.mInnerTimesMs = new long[this.mTimestamps.size()];
                for (n = 0; n < this.mTimestamps.size(); ++n) {
                    textTrackCue.mInnerTimesMs[n] = this.mTimestamps.get(n);
                }
                this.mTimestamps.clear();
            } else {
                textTrackCue.mInnerTimesMs = null;
            }
            textTrackCue.mRunID = this.mCurrentRunID;
        }
        this.addCue(textTrackCue);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onData(byte[] object, boolean bl, long l) {
        CharSequence charSequence;
        block6 : {
            try {
                charSequence = new String((byte[])object, "UTF-8");
                object = this.mParser;
                // MONITORENTER : object
                if (this.mCurrentRunID == null || l == this.mCurrentRunID) break block6;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Run #");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("subtitle data is not UTF-8 encoded: ");
                stringBuilder.append(unsupportedEncodingException);
                Log.w(TAG, stringBuilder.toString());
            }
            ((StringBuilder)charSequence).append(this.mCurrentRunID);
            ((StringBuilder)charSequence).append(" in progress.  Cannot process run #");
            ((StringBuilder)charSequence).append(l);
            IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)charSequence).toString());
            throw illegalStateException;
        }
        this.mCurrentRunID = l;
        this.mParser.parse((String)charSequence);
        if (bl) {
            this.finishedRun(l);
            this.mParser.eos();
            this.mRegions.clear();
            this.mCurrentRunID = null;
        }
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onRegionParsed(TextTrackRegion textTrackRegion) {
        WebVttParser webVttParser = this.mParser;
        synchronized (webVttParser) {
            this.mRegions.put(textTrackRegion.mId, textTrackRegion);
            return;
        }
    }

    @Override
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
        Object object;
        if (!this.mVisible) {
            return;
        }
        if (this.DEBUG && this.mTimeProvider != null) {
            try {
                object = new StringBuilder();
                ((StringBuilder)object).append("at ");
                ((StringBuilder)object).append(this.mTimeProvider.getCurrentTimeUs(false, true) / 1000L);
                ((StringBuilder)object).append(" ms the active cues are:");
                Log.d(TAG, ((StringBuilder)object).toString());
            }
            catch (IllegalStateException illegalStateException) {
                Log.d(TAG, "at (illegal state) the active cues are:");
            }
        }
        if ((object = this.mRenderingWidget) != null) {
            ((WebVttRenderingWidget)object).setActiveCues(vector);
        }
    }
}

