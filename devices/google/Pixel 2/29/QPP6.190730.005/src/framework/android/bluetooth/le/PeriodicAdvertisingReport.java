/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.le.ScanRecord;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class PeriodicAdvertisingReport
implements Parcelable {
    public static final Parcelable.Creator<PeriodicAdvertisingReport> CREATOR = new Parcelable.Creator<PeriodicAdvertisingReport>(){

        @Override
        public PeriodicAdvertisingReport createFromParcel(Parcel parcel) {
            return new PeriodicAdvertisingReport(parcel);
        }

        public PeriodicAdvertisingReport[] newArray(int n) {
            return new PeriodicAdvertisingReport[n];
        }
    };
    public static final int DATA_COMPLETE = 0;
    public static final int DATA_INCOMPLETE_TRUNCATED = 2;
    private ScanRecord mData;
    private int mDataStatus;
    private int mRssi;
    private int mSyncHandle;
    private long mTimestampNanos;
    private int mTxPower;

    public PeriodicAdvertisingReport(int n, int n2, int n3, int n4, ScanRecord scanRecord) {
        this.mSyncHandle = n;
        this.mTxPower = n2;
        this.mRssi = n3;
        this.mDataStatus = n4;
        this.mData = scanRecord;
    }

    private PeriodicAdvertisingReport(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mSyncHandle = parcel.readInt();
        this.mTxPower = parcel.readInt();
        this.mRssi = parcel.readInt();
        this.mDataStatus = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mData = ScanRecord.parseFromBytes(parcel.createByteArray());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (PeriodicAdvertisingReport)object;
            if (this.mSyncHandle != ((PeriodicAdvertisingReport)object).mSyncHandle || this.mTxPower != ((PeriodicAdvertisingReport)object).mTxPower || this.mRssi != ((PeriodicAdvertisingReport)object).mRssi || this.mDataStatus != ((PeriodicAdvertisingReport)object).mDataStatus || !Objects.equals(this.mData, ((PeriodicAdvertisingReport)object).mData) || this.mTimestampNanos != ((PeriodicAdvertisingReport)object).mTimestampNanos) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public ScanRecord getData() {
        return this.mData;
    }

    public int getDataStatus() {
        return this.mDataStatus;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getSyncHandle() {
        return this.mSyncHandle;
    }

    public long getTimestampNanos() {
        return this.mTimestampNanos;
    }

    public int getTxPower() {
        return this.mTxPower;
    }

    public int hashCode() {
        return Objects.hash(this.mSyncHandle, this.mTxPower, this.mRssi, this.mDataStatus, this.mData, this.mTimestampNanos);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PeriodicAdvertisingReport{syncHandle=");
        stringBuilder.append(this.mSyncHandle);
        stringBuilder.append(", txPower=");
        stringBuilder.append(this.mTxPower);
        stringBuilder.append(", rssi=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(", dataStatus=");
        stringBuilder.append(this.mDataStatus);
        stringBuilder.append(", data=");
        stringBuilder.append(Objects.toString(this.mData));
        stringBuilder.append(", timestampNanos=");
        stringBuilder.append(this.mTimestampNanos);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSyncHandle);
        parcel.writeInt(this.mTxPower);
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mDataStatus);
        if (this.mData != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mData.getBytes());
        } else {
            parcel.writeInt(0);
        }
    }

}

