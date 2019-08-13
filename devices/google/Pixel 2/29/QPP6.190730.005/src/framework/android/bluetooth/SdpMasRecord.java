/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.SdpRecord;
import android.os.Parcel;
import android.os.Parcelable;

public class SdpMasRecord
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public SdpMasRecord createFromParcel(Parcel parcel) {
            return new SdpMasRecord(parcel);
        }

        public SdpRecord[] newArray(int n) {
            return new SdpRecord[n];
        }
    };
    private final int mL2capPsm;
    private final int mMasInstanceId;
    private final int mProfileVersion;
    private final int mRfcommChannelNumber;
    private final String mServiceName;
    private final int mSupportedFeatures;
    private final int mSupportedMessageTypes;

    public SdpMasRecord(int n, int n2, int n3, int n4, int n5, int n6, String string2) {
        this.mMasInstanceId = n;
        this.mL2capPsm = n2;
        this.mRfcommChannelNumber = n3;
        this.mProfileVersion = n4;
        this.mSupportedFeatures = n5;
        this.mSupportedMessageTypes = n6;
        this.mServiceName = string2;
    }

    public SdpMasRecord(Parcel parcel) {
        this.mMasInstanceId = parcel.readInt();
        this.mL2capPsm = parcel.readInt();
        this.mRfcommChannelNumber = parcel.readInt();
        this.mProfileVersion = parcel.readInt();
        this.mSupportedFeatures = parcel.readInt();
        this.mSupportedMessageTypes = parcel.readInt();
        this.mServiceName = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getL2capPsm() {
        return this.mL2capPsm;
    }

    public int getMasInstanceId() {
        return this.mMasInstanceId;
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

    public int getSupportedFeatures() {
        return this.mSupportedFeatures;
    }

    public int getSupportedMessageTypes() {
        return this.mSupportedMessageTypes;
    }

    public boolean msgSupported(int n) {
        boolean bl = (this.mSupportedMessageTypes & n) != 0;
        return bl;
    }

    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2 = "Bluetooth MAS SDP Record:\n";
        if (this.mMasInstanceId != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Bluetooth MAS SDP Record:\n");
            ((StringBuilder)charSequence).append("Mas Instance Id: ");
            ((StringBuilder)charSequence).append(this.mMasInstanceId);
            ((StringBuilder)charSequence).append("\n");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if (this.mRfcommChannelNumber != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("RFCOMM Chan Number: ");
            ((StringBuilder)charSequence).append(this.mRfcommChannelNumber);
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
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
        if (this.mProfileVersion != -1) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("Profile version: ");
            ((StringBuilder)charSequence2).append(this.mProfileVersion);
            ((StringBuilder)charSequence2).append("\n");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (this.mSupportedMessageTypes != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("Supported msg types: ");
            ((StringBuilder)charSequence).append(this.mSupportedMessageTypes);
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
        return charSequence2;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMasInstanceId);
        parcel.writeInt(this.mL2capPsm);
        parcel.writeInt(this.mRfcommChannelNumber);
        parcel.writeInt(this.mProfileVersion);
        parcel.writeInt(this.mSupportedFeatures);
        parcel.writeInt(this.mSupportedMessageTypes);
        parcel.writeString(this.mServiceName);
    }

    public static final class MessageType {
        public static final int EMAIL = 1;
        public static final int MMS = 8;
        public static final int SMS_CDMA = 4;
        public static final int SMS_GSM = 2;
    }

}

