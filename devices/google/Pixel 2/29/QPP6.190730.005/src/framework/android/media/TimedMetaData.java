/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Parcel;

public final class TimedMetaData {
    private static final String TAG = "TimedMetaData";
    private byte[] mMetaData;
    private long mTimestampUs;

    public TimedMetaData(long l, byte[] arrby) {
        if (arrby != null) {
            this.mTimestampUs = l;
            this.mMetaData = arrby;
            return;
        }
        throw new IllegalArgumentException("null metaData is not allowed");
    }

    private TimedMetaData(Parcel parcel) {
        if (this.parseParcel(parcel)) {
            return;
        }
        throw new IllegalArgumentException("parseParcel() fails");
    }

    static TimedMetaData createTimedMetaDataFromParcel(Parcel parcel) {
        return new TimedMetaData(parcel);
    }

    private boolean parseParcel(Parcel parcel) {
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        this.mTimestampUs = parcel.readLong();
        this.mMetaData = new byte[parcel.readInt()];
        parcel.readByteArray(this.mMetaData);
        return true;
    }

    public byte[] getMetaData() {
        return this.mMetaData;
    }

    public long getTimestamp() {
        return this.mTimestampUs;
    }
}

