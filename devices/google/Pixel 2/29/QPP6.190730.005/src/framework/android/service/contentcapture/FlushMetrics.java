/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentcapture;

import android.os.Parcel;
import android.os.Parcelable;

public final class FlushMetrics
implements Parcelable {
    public static final Parcelable.Creator<FlushMetrics> CREATOR = new Parcelable.Creator<FlushMetrics>(){

        @Override
        public FlushMetrics createFromParcel(Parcel parcel) {
            FlushMetrics flushMetrics = new FlushMetrics();
            flushMetrics.sessionStarted = parcel.readInt();
            flushMetrics.sessionFinished = parcel.readInt();
            flushMetrics.viewAppearedCount = parcel.readInt();
            flushMetrics.viewDisappearedCount = parcel.readInt();
            flushMetrics.viewTextChangedCount = parcel.readInt();
            return flushMetrics;
        }

        public FlushMetrics[] newArray(int n) {
            return new FlushMetrics[n];
        }
    };
    public int sessionFinished;
    public int sessionStarted;
    public int viewAppearedCount;
    public int viewDisappearedCount;
    public int viewTextChangedCount;

    @Override
    public int describeContents() {
        return 0;
    }

    public void reset() {
        this.viewAppearedCount = 0;
        this.viewDisappearedCount = 0;
        this.viewTextChangedCount = 0;
        this.sessionStarted = 0;
        this.sessionFinished = 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.sessionStarted);
        parcel.writeInt(this.sessionFinished);
        parcel.writeInt(this.viewAppearedCount);
        parcel.writeInt(this.viewDisappearedCount);
        parcel.writeInt(this.viewTextChangedCount);
    }

}

