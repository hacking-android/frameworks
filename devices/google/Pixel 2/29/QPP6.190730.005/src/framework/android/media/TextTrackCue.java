/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.SubtitleTrack;
import android.media.TextTrackCueSpan;
import android.media.TextTrackRegion;
import android.media.WebVttParser;
import java.util.Arrays;

class TextTrackCue
extends SubtitleTrack.Cue {
    static final int ALIGNMENT_END = 202;
    static final int ALIGNMENT_LEFT = 203;
    static final int ALIGNMENT_MIDDLE = 200;
    static final int ALIGNMENT_RIGHT = 204;
    static final int ALIGNMENT_START = 201;
    private static final String TAG = "TTCue";
    static final int WRITING_DIRECTION_HORIZONTAL = 100;
    static final int WRITING_DIRECTION_VERTICAL_LR = 102;
    static final int WRITING_DIRECTION_VERTICAL_RL = 101;
    int mAlignment = 200;
    boolean mAutoLinePosition;
    String mId = "";
    Integer mLinePosition = null;
    TextTrackCueSpan[][] mLines = null;
    boolean mPauseOnExit = false;
    TextTrackRegion mRegion = null;
    String mRegionId = "";
    int mSize = 100;
    boolean mSnapToLines = true;
    String[] mStrings;
    int mTextPosition = 50;
    int mWritingDirection = 100;

    TextTrackCue() {
    }

    public StringBuilder appendLinesToBuilder(StringBuilder stringBuilder) {
        TextTrackCueSpan[][] arrtextTrackCueSpan = this.mLines;
        TextTrackCueSpan[][] arrtextTrackCueSpan2 = "null";
        if (arrtextTrackCueSpan == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append("[");
            arrtextTrackCueSpan = this.mLines;
            int n = arrtextTrackCueSpan.length;
            boolean bl = true;
            for (int i = 0; i < n; ++i) {
                Object object = arrtextTrackCueSpan[i];
                if (!bl) {
                    stringBuilder.append(", ");
                }
                if (object == null) {
                    stringBuilder.append((String)arrtextTrackCueSpan2);
                    object = arrtextTrackCueSpan2;
                    arrtextTrackCueSpan2 = arrtextTrackCueSpan;
                    arrtextTrackCueSpan = object;
                } else {
                    stringBuilder.append("\"");
                    int n2 = ((TextTrackCueSpan[])object).length;
                    long l = -1L;
                    bl = true;
                    for (int j = 0; j < n2; ++j) {
                        TextTrackCueSpan textTrackCueSpan = object[j];
                        if (!bl) {
                            stringBuilder.append(" ");
                        }
                        long l2 = l;
                        if (textTrackCueSpan.mTimestampMs != l) {
                            stringBuilder.append("<");
                            stringBuilder.append(WebVttParser.timeToString(textTrackCueSpan.mTimestampMs));
                            stringBuilder.append(">");
                            l2 = textTrackCueSpan.mTimestampMs;
                        }
                        stringBuilder.append(textTrackCueSpan.mText);
                        bl = false;
                        l = l2;
                    }
                    object = arrtextTrackCueSpan2;
                    arrtextTrackCueSpan2 = arrtextTrackCueSpan;
                    stringBuilder.append("\"");
                    arrtextTrackCueSpan = object;
                }
                bl = false;
                object = arrtextTrackCueSpan2;
                arrtextTrackCueSpan2 = arrtextTrackCueSpan;
                arrtextTrackCueSpan = object;
            }
            stringBuilder.append("]");
        }
        return stringBuilder;
    }

    public StringBuilder appendStringsToBuilder(StringBuilder stringBuilder) {
        if (this.mStrings == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append("[");
            boolean bl = true;
            for (String string2 : this.mStrings) {
                if (!bl) {
                    stringBuilder.append(", ");
                }
                if (string2 == null) {
                    stringBuilder.append("null");
                } else {
                    stringBuilder.append("\"");
                    stringBuilder.append(string2);
                    stringBuilder.append("\"");
                }
                bl = false;
            }
            stringBuilder.append("]");
        }
        return stringBuilder;
    }

    public boolean equals(Object object) {
        boolean bl;
        block6 : {
            int n;
            if (!(object instanceof TextTrackCue)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            try {
                object = (TextTrackCue)object;
                bl = this.mId.equals(((TextTrackCue)object).mId) && this.mPauseOnExit == ((TextTrackCue)object).mPauseOnExit && this.mWritingDirection == ((TextTrackCue)object).mWritingDirection && this.mRegionId.equals(((TextTrackCue)object).mRegionId) && this.mSnapToLines == ((TextTrackCue)object).mSnapToLines && this.mAutoLinePosition == ((TextTrackCue)object).mAutoLinePosition && (this.mAutoLinePosition || this.mLinePosition != null && this.mLinePosition.equals(((TextTrackCue)object).mLinePosition) || this.mLinePosition == null && ((TextTrackCue)object).mLinePosition == null) && this.mTextPosition == ((TextTrackCue)object).mTextPosition && this.mSize == ((TextTrackCue)object).mSize && this.mAlignment == ((TextTrackCue)object).mAlignment && this.mLines.length == ((TextTrackCue)object).mLines.length;
                if (!bl) break block6;
                n = 0;
            }
            catch (IncompatibleClassChangeError incompatibleClassChangeError) {
                return false;
            }
            do {
                block7 : {
                    if (n >= this.mLines.length) break;
                    boolean bl2 = Arrays.equals(this.mLines[n], ((TextTrackCue)object).mLines[n]);
                    if (bl2) break block7;
                    return false;
                }
                ++n;
            } while (true);
        }
        return bl;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public void onTime(long l) {
        for (TextTrackCueSpan[] arrtextTrackCueSpan : this.mLines) {
            for (TextTrackCueSpan textTrackCueSpan : arrtextTrackCueSpan) {
                boolean bl = l >= textTrackCueSpan.mTimestampMs;
                textTrackCueSpan.mEnabled = bl;
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WebVttParser.timeToString(this.mStartTimeMs));
        stringBuilder.append(" --> ");
        stringBuilder.append(WebVttParser.timeToString(this.mEndTimeMs));
        stringBuilder.append(" {id:\"");
        stringBuilder.append(this.mId);
        stringBuilder.append("\", pauseOnExit:");
        stringBuilder.append(this.mPauseOnExit);
        stringBuilder.append(", direction:");
        int n = this.mWritingDirection;
        String string2 = "INVALID";
        Object object = n == 100 ? "horizontal" : (n == 102 ? "vertical_lr" : (n == 101 ? "vertical_rl" : "INVALID"));
        stringBuilder.append((String)object);
        stringBuilder.append(", regionId:\"");
        stringBuilder.append(this.mRegionId);
        stringBuilder.append("\", snapToLines:");
        stringBuilder.append(this.mSnapToLines);
        stringBuilder.append(", linePosition:");
        object = this.mAutoLinePosition ? "auto" : this.mLinePosition;
        stringBuilder.append(object);
        stringBuilder.append(", textPosition:");
        stringBuilder.append(this.mTextPosition);
        stringBuilder.append(", size:");
        stringBuilder.append(this.mSize);
        stringBuilder.append(", alignment:");
        n = this.mAlignment;
        if (n == 202) {
            object = "end";
        } else if (n == 203) {
            object = "left";
        } else if (n == 200) {
            object = "middle";
        } else if (n == 204) {
            object = "right";
        } else {
            object = string2;
            if (n == 201) {
                object = "start";
            }
        }
        stringBuilder.append((String)object);
        stringBuilder.append(", text:");
        this.appendStringsToBuilder(stringBuilder).append("}");
        return stringBuilder.toString();
    }
}

