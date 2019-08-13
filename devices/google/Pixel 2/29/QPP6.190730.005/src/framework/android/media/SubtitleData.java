/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Parcel;

public final class SubtitleData {
    private static final String TAG = "SubtitleData";
    private byte[] mData;
    private long mDurationUs;
    private long mStartTimeUs;
    private int mTrackIndex;

    public SubtitleData(int n, long l, long l2, byte[] arrby) {
        if (arrby != null) {
            this.mTrackIndex = n;
            this.mStartTimeUs = l;
            this.mDurationUs = l2;
            this.mData = arrby;
            return;
        }
        throw new IllegalArgumentException("null data is not allowed");
    }

    public SubtitleData(Parcel parcel) {
        if (this.parseParcel(parcel)) {
            return;
        }
        throw new IllegalArgumentException("parseParcel() fails");
    }

    private boolean parseParcel(Parcel parcel) {
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        this.mTrackIndex = parcel.readInt();
        this.mStartTimeUs = parcel.readLong();
        this.mDurationUs = parcel.readLong();
        this.mData = new byte[parcel.readInt()];
        parcel.readByteArray(this.mData);
        return true;
    }

    public byte[] getData() {
        return this.mData;
    }

    public long getDurationUs() {
        return this.mDurationUs;
    }

    public long getStartTimeUs() {
        return this.mStartTimeUs;
    }

    public int getTrackIndex() {
        return this.mTrackIndex;
    }
}

