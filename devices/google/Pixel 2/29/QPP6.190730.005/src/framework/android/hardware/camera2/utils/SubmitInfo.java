/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class SubmitInfo
implements Parcelable {
    public static final Parcelable.Creator<SubmitInfo> CREATOR = new Parcelable.Creator<SubmitInfo>(){

        @Override
        public SubmitInfo createFromParcel(Parcel parcel) {
            return new SubmitInfo(parcel);
        }

        public SubmitInfo[] newArray(int n) {
            return new SubmitInfo[n];
        }
    };
    private long mLastFrameNumber;
    private int mRequestId;

    public SubmitInfo() {
        this.mRequestId = -1;
        this.mLastFrameNumber = -1L;
    }

    public SubmitInfo(int n, long l) {
        this.mRequestId = n;
        this.mLastFrameNumber = l;
    }

    private SubmitInfo(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLastFrameNumber() {
        return this.mLastFrameNumber;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public void readFromParcel(Parcel parcel) {
        this.mRequestId = parcel.readInt();
        this.mLastFrameNumber = parcel.readLong();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRequestId);
        parcel.writeLong(this.mLastFrameNumber);
    }

}

