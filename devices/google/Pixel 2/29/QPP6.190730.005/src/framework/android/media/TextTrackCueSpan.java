/*
 * Decompiled with CFR 0.145.
 */
package android.media;

class TextTrackCueSpan {
    boolean mEnabled;
    String mText;
    long mTimestampMs;

    TextTrackCueSpan(String string2, long l) {
        this.mTimestampMs = l;
        this.mText = string2;
        boolean bl = this.mTimestampMs < 0L;
        this.mEnabled = bl;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof TextTrackCueSpan;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (TextTrackCueSpan)object;
            if (this.mTimestampMs != ((TextTrackCueSpan)object).mTimestampMs || !this.mText.equals(((TextTrackCueSpan)object).mText)) break block1;
            bl = true;
        }
        return bl;
    }
}

