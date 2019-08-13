/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.SdpRecord;
import android.os.Parcel;
import android.os.Parcelable;

public class SdpSapsRecord
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public SdpSapsRecord createFromParcel(Parcel parcel) {
            return new SdpSapsRecord(parcel);
        }

        public SdpRecord[] newArray(int n) {
            return new SdpRecord[n];
        }
    };
    private final int mProfileVersion;
    private final int mRfcommChannelNumber;
    private final String mServiceName;

    public SdpSapsRecord(int n, int n2, String string2) {
        this.mRfcommChannelNumber = n;
        this.mProfileVersion = n2;
        this.mServiceName = string2;
    }

    public SdpSapsRecord(Parcel parcel) {
        this.mRfcommChannelNumber = parcel.readInt();
        this.mProfileVersion = parcel.readInt();
        this.mServiceName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getProfileVersion() {
        return this.mProfileVersion;
    }

    public int getRfcommCannelNumber() {
        return this.mRfcommChannelNumber;
    }

    public String getServiceName() {
        return this.mServiceName;
    }

    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2 = "Bluetooth MAS SDP Record:\n";
        if (this.mRfcommChannelNumber != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Bluetooth MAS SDP Record:\n");
            ((StringBuilder)charSequence).append("RFCOMM Chan Number: ");
            ((StringBuilder)charSequence).append(this.mRfcommChannelNumber);
            ((StringBuilder)charSequence).append("\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (this.mServiceName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("Service Name: ");
            ((StringBuilder)charSequence).append(this.mServiceName);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.mProfileVersion != -1) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("Profile version: ");
            ((StringBuilder)charSequence2).append(this.mProfileVersion);
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRfcommChannelNumber);
        parcel.writeInt(this.mProfileVersion);
        parcel.writeString(this.mServiceName);
    }

}

