/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public class SdpPseRecord
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public SdpPseRecord createFromParcel(Parcel parcel) {
            return new SdpPseRecord(parcel);
        }

        public SdpPseRecord[] newArray(int n) {
            return new SdpPseRecord[n];
        }
    };
    private final int mL2capPsm;
    private final int mProfileVersion;
    private final int mRfcommChannelNumber;
    private final String mServiceName;
    private final int mSupportedFeatures;
    private final int mSupportedRepositories;

    public SdpPseRecord(int n, int n2, int n3, int n4, int n5, String string2) {
        this.mL2capPsm = n;
        this.mRfcommChannelNumber = n2;
        this.mProfileVersion = n3;
        this.mSupportedFeatures = n4;
        this.mSupportedRepositories = n5;
        this.mServiceName = string2;
    }

    public SdpPseRecord(Parcel parcel) {
        this.mRfcommChannelNumber = parcel.readInt();
        this.mL2capPsm = parcel.readInt();
        this.mProfileVersion = parcel.readInt();
        this.mSupportedFeatures = parcel.readInt();
        this.mSupportedRepositories = parcel.readInt();
        this.mServiceName = parcel.readString();
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

    public int getSupportedRepositories() {
        return this.mSupportedRepositories;
    }

    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2 = "Bluetooth MNS SDP Record:\n";
        if (this.mRfcommChannelNumber != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Bluetooth MNS SDP Record:\n");
            ((StringBuilder)charSequence).append("RFCOMM Chan Number: ");
            ((StringBuilder)charSequence).append(this.mRfcommChannelNumber);
            ((StringBuilder)charSequence).append("\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (this.mL2capPsm != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("L2CAP PSM: ");
            ((StringBuilder)charSequence).append(this.mL2capPsm);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if (this.mProfileVersion != -1) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("profile version: ");
            ((StringBuilder)charSequence2).append(this.mProfileVersion);
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
        if (this.mSupportedRepositories != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("Supported repositories: ");
            ((StringBuilder)charSequence).append(this.mSupportedRepositories);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRfcommChannelNumber);
        parcel.writeInt(this.mL2capPsm);
        parcel.writeInt(this.mProfileVersion);
        parcel.writeInt(this.mSupportedFeatures);
        parcel.writeInt(this.mSupportedRepositories);
        parcel.writeString(this.mServiceName);
    }

}

