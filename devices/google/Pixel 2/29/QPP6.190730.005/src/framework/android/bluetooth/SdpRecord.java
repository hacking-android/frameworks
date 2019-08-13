/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public class SdpRecord
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public SdpRecord createFromParcel(Parcel parcel) {
            return new SdpRecord(parcel);
        }

        public SdpRecord[] newArray(int n) {
            return new SdpRecord[n];
        }
    };
    private final byte[] mRawData;
    private final int mRawSize;

    public SdpRecord(int n, byte[] arrby) {
        this.mRawData = arrby;
        this.mRawSize = n;
    }

    public SdpRecord(Parcel parcel) {
        this.mRawSize = parcel.readInt();
        this.mRawData = new byte[this.mRawSize];
        parcel.readByteArray(this.mRawData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getRawData() {
        return this.mRawData;
    }

    public int getRawSize() {
        return this.mRawSize;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BluetoothSdpRecord [rawData=");
        stringBuilder.append(Arrays.toString(this.mRawData));
        stringBuilder.append(", rawSize=");
        stringBuilder.append(this.mRawSize);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRawSize);
        parcel.writeByteArray(this.mRawData);
    }

}

