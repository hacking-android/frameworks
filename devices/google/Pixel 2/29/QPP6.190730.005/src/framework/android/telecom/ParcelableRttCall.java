/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

public class ParcelableRttCall
implements Parcelable {
    public static final Parcelable.Creator<ParcelableRttCall> CREATOR = new Parcelable.Creator<ParcelableRttCall>(){

        @Override
        public ParcelableRttCall createFromParcel(Parcel parcel) {
            return new ParcelableRttCall(parcel);
        }

        public ParcelableRttCall[] newArray(int n) {
            return new ParcelableRttCall[n];
        }
    };
    private final ParcelFileDescriptor mReceiveStream;
    private final int mRttMode;
    private final ParcelFileDescriptor mTransmitStream;

    public ParcelableRttCall(int n, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        this.mRttMode = n;
        this.mTransmitStream = parcelFileDescriptor;
        this.mReceiveStream = parcelFileDescriptor2;
    }

    protected ParcelableRttCall(Parcel parcel) {
        this.mRttMode = parcel.readInt();
        this.mTransmitStream = (ParcelFileDescriptor)parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
        this.mReceiveStream = (ParcelFileDescriptor)parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ParcelFileDescriptor getReceiveStream() {
        return this.mReceiveStream;
    }

    public int getRttMode() {
        return this.mRttMode;
    }

    public ParcelFileDescriptor getTransmitStream() {
        return this.mTransmitStream;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRttMode);
        parcel.writeParcelable(this.mTransmitStream, n);
        parcel.writeParcelable(this.mReceiveStream, n);
    }

}

