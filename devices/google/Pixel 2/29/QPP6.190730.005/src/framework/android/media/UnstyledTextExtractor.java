/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.TextTrackCueSpan;
import android.media.Tokenizer;
import java.util.Vector;

class UnstyledTextExtractor
implements Tokenizer.OnTokenListener {
    Vector<TextTrackCueSpan> mCurrentLine = new Vector();
    long mLastTimestamp;
    StringBuilder mLine = new StringBuilder();
    Vector<TextTrackCueSpan[]> mLines = new Vector();

    UnstyledTextExtractor() {
        this.init();
    }

    private void init() {
        StringBuilder stringBuilder = this.mLine;
        stringBuilder.delete(0, stringBuilder.length());
        this.mLines.clear();
        this.mCurrentLine.clear();
        this.mLastTimestamp = -1L;
    }

    public TextTrackCueSpan[][] getText() {
        if (this.mLine.length() > 0 || this.mCurrentLine.size() > 0) {
            this.onLineEnd();
        }
        TextTrackCueSpan[][] arrtextTrackCueSpan = new TextTrackCueSpan[this.mLines.size()][];
        this.mLines.toArray((T[])arrtextTrackCueSpan);
        this.init();
        return arrtextTrackCueSpan;
    }

    @Override
    public void onData(String string2) {
        this.mLine.append(string2);
    }

    @Override
    public void onEnd(String string2) {
    }

    @Override
    public void onLineEnd() {
        Object object;
        if (this.mLine.length() > 0) {
            this.mCurrentLine.add(new TextTrackCueSpan(this.mLine.toString(), this.mLastTimestamp));
            object = this.mLine;
            ((StringBuilder)object).delete(0, ((StringBuilder)object).length());
        }
        object = new TextTrackCueSpan[this.mCurrentLine.size()];
        this.mCurrentLine.toArray((T[])object);
        this.mCurrentLine.clear();
        this.mLines.add((TextTrackCueSpan[])object);
    }

    @Override
    public void onStart(String string2, String[] arrstring, String string3) {
    }

    @Override
    public void onTimeStamp(long l) {
        if (this.mLine.length() > 0 && l != this.mLastTimestamp) {
            this.mCurrentLine.add(new TextTrackCueSpan(this.mLine.toString(), this.mLastTimestamp));
            StringBuilder stringBuilder = this.mLine;
            stringBuilder.delete(0, stringBuilder.length());
        }
        this.mLastTimestamp = l;
    }
}

