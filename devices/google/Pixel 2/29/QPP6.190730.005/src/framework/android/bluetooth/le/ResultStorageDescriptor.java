/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class ResultStorageDescriptor
implements Parcelable {
    public static final Parcelable.Creator<ResultStorageDescriptor> CREATOR = new Parcelable.Creator<ResultStorageDescriptor>(){

        @Override
        public ResultStorageDescriptor createFromParcel(Parcel parcel) {
            return new ResultStorageDescriptor(parcel);
        }

        public ResultStorageDescriptor[] newArray(int n) {
            return new ResultStorageDescriptor[n];
        }
    };
    private int mLength;
    private int mOffset;
    private int mType;

    public ResultStorageDescriptor(int n, int n2, int n3) {
        this.mType = n;
        this.mOffset = n2;
        this.mLength = n3;
    }

    private ResultStorageDescriptor(Parcel parcel) {
        this.ReadFromParcel(parcel);
    }

    private void ReadFromParcel(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mOffset = parcel.readInt();
        this.mLength = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getLength() {
        return this.mLength;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public int getType() {
        return this.mType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mOffset);
        parcel.writeInt(this.mLength);
    }

}

