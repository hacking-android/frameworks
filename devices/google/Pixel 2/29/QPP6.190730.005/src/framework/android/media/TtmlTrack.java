/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.media;

import android.media.MediaFormat;
import android.media.MediaTimeProvider;
import android.media.SubtitleTrack;
import android.media.TtmlCue;
import android.media.TtmlNode;
import android.media.TtmlNodeListener;
import android.media.TtmlParser;
import android.media.TtmlRenderingWidget;
import android.media.TtmlUtils;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParserException;

class TtmlTrack
extends SubtitleTrack
implements TtmlNodeListener {
    private static final String TAG = "TtmlTrack";
    private Long mCurrentRunID;
    private final TtmlParser mParser = new TtmlParser(this);
    private String mParsingData;
    private final TtmlRenderingWidget mRenderingWidget;
    private TtmlNode mRootNode;
    private final TreeSet<Long> mTimeEvents = new TreeSet();
    private final LinkedList<TtmlNode> mTtmlNodes = new LinkedList();

    TtmlTrack(TtmlRenderingWidget ttmlRenderingWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mRenderingWidget = ttmlRenderingWidget;
        this.mParsingData = "";
    }

    private void addTimeEvents(TtmlNode ttmlNode) {
        this.mTimeEvents.add(ttmlNode.mStartTimeMs);
        this.mTimeEvents.add(ttmlNode.mEndTimeMs);
        for (int i = 0; i < ttmlNode.mChildren.size(); ++i) {
            this.addTimeEvents(ttmlNode.mChildren.get(i));
        }
    }

    private List<TtmlNode> getActiveNodes(long l, long l2) {
        ArrayList<TtmlNode> arrayList = new ArrayList<TtmlNode>();
        for (int i = 0; i < this.mTtmlNodes.size(); ++i) {
            TtmlNode ttmlNode = this.mTtmlNodes.get(i);
            if (!ttmlNode.isActive(l, l2)) continue;
            arrayList.add(ttmlNode);
        }
        return arrayList;
    }

    public TtmlCue getNextResult() {
        while (this.mTimeEvents.size() >= 2) {
            long l;
            long l2 = this.mTimeEvents.pollFirst();
            if (this.getActiveNodes(l2, l = this.mTimeEvents.first().longValue()).isEmpty()) continue;
            return new TtmlCue(l2, l, TtmlUtils.applySpacePolicy(TtmlUtils.extractText(this.mRootNode, l2, l), false), TtmlUtils.extractTtmlFragment(this.mRootNode, l2, l));
        }
        return null;
    }

    @Override
    public TtmlRenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
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
        block9 : {
            try {
                charSequence = new String((byte[])object, "UTF-8");
                object = this.mParser;
                // MONITORENTER : object
                if (this.mCurrentRunID == null || l == this.mCurrentRunID) break block9;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mParsingData);
        stringBuilder.append((String)charSequence);
        this.mParsingData = stringBuilder.toString();
        if (bl) {
            try {
                this.mParser.parse(this.mParsingData, this.mCurrentRunID);
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            catch (XmlPullParserException xmlPullParserException) {
                xmlPullParserException.printStackTrace();
            }
            this.finishedRun(l);
            this.mParsingData = "";
            this.mCurrentRunID = null;
        }
        // MONITOREXIT : object
        return;
    }

    @Override
    public void onRootNodeParsed(TtmlNode object) {
        this.mRootNode = object;
        while ((object = this.getNextResult()) != null) {
            this.addCue((SubtitleTrack.Cue)object);
        }
        this.mRootNode = null;
        this.mTtmlNodes.clear();
        this.mTimeEvents.clear();
    }

    @Override
    public void onTtmlNodeParsed(TtmlNode ttmlNode) {
        this.mTtmlNodes.addLast(ttmlNode);
        this.addTimeEvents(ttmlNode);
    }

    @Override
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
        if (!this.mVisible) {
            return;
        }
        if (this.DEBUG && this.mTimeProvider != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("at ");
                stringBuilder.append(this.mTimeProvider.getCurrentTimeUs(false, true) / 1000L);
                stringBuilder.append(" ms the active cues are:");
                Log.d(TAG, stringBuilder.toString());
            }
            catch (IllegalStateException illegalStateException) {
                Log.d(TAG, "at (illegal state) the active cues are:");
            }
        }
        this.mRenderingWidget.setActiveCues(vector);
    }
}

