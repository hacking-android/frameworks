/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public class SdpMnsRecord
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public SdpMnsRecord createFromParcel(Parcel parcel) {
            return new SdpMnsRecord(parcel);
        }

        public SdpMnsRecord[] newArray(int n) {
            return new SdpMnsRecord[n];
        }
    };
    private final int mL2capPsm;
    private final int mProfileVersion;
    private final int mRfcommChannelNumber;
    private final String mServiceName;
    private final int mSupportedFeatures;

    public SdpMnsRecord(int n, int n2, int n3, int n4, String string2) {
        this.mL2capPsm = n;
        this.mRfcommChannelNumber = n2;
        this.mSupportedFeatures = n4;
        this.mServiceName = string2;
        this.mProfileVersion = n3;
    }

    public SdpMnsRecord(Parcel parcel) {
        this.mRfcommChannelNumber = parcel.readInt();
        this.mL2capPsm = parcel.readInt();
        this.mServiceName = parcel.readString();
        this.mSupportedFeatures = parcel.readInt();
        this.mProfileVersion = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getL2capPsm() {
        return this.mL2capPsm;
    }

    public int getProfileVersion() {
        return this.mProfileVersion;
    }

    public int getRfcommChannelNumber() {
        return this.mRfcommChannelNumber;
    }

    public String getServiceName() {
        return this.mServiceName;
    }

    public int getSupportedFeatures() {
        return this.mSupportedFeatures;
    }

    public String toString() {
        CharSequence charSequence = "Bluetooth MNS SDP Record:\n";
        if (this.mRfcommChannelNumber != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Bluetooth MNS SDP Record:\n");
            ((StringBuilder)charSequence).append("RFCOMM Chan Number: ");
            ((StringBuilder)charSequence).append(this.mRfcommChannelNumber);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        CharSequence charSequence2 = charSequence;
        if (this.mL2capPsm != -1) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("L2CAP PSM: ");
            ((StringBuilder)charSequence2).append(this.mL2capPsm);
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
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
        if (this.mSupportedFeatures != -1) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("Supported features: ");
            ((StringBuilder)charSequence2).append(this.mSupportedFeatures);
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (this.mProfileVersion != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("Profile_version: ");
            ((StringBuilder)charSequence).append(this.mProfileVersion);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRfcommChannelNumber);
        parcel.writeInt(this.mL2capPsm);
        parcel.writeString(this.mServiceName);
        parcel.writeInt(this.mSupportedFeatures);
        parcel.writeInt(this.mProfileVersion);
    }

}

