/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public class SdpOppOpsRecord
implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        public SdpOppOpsRecord createFromParcel(Parcel parcel) {
            return new SdpOppOpsRecord(parcel);
        }

        public SdpOppOpsRecord[] newArray(int n) {
            return new SdpOppOpsRecord[n];
        }
    };
    private final byte[] mFormatsList;
    private final int mL2capPsm;
    private final int mProfileVersion;
    private final int mRfcommChannel;
    private final String mServiceName;

    public SdpOppOpsRecord(Parcel parcel) {
        this.mRfcommChannel = parcel.readInt();
        this.mL2capPsm = parcel.readInt();
        this.mProfileVersion = parcel.readInt();
        this.mServiceName = parcel.readString();
        int n = parcel.readInt();
        if (n > 0) {
            byte[] arrby = new byte[n];
            parcel.readByteArray(arrby);
            this.mFormatsList = arrby;
        } else {
            this.mFormatsList = null;
        }
    }

    public SdpOppOpsRecord(String string2, int n, int n2, int n3, byte[] arrby) {
        this.mServiceName = string2;
        this.mRfcommChannel = n;
        this.mL2capPsm = n2;
        this.mProfileVersion = n3;
        this.mFormatsList = arrby;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getFormatsList() {
        return this.mFormatsList;
    }

    public int getL2capPsm() {
        return this.mL2capPsm;
    }

    public int getProfileVersion() {
        return this.mProfileVersion;
    }

    public int getRfcommChannel() {
        return this.mRfcommChannel;
    }

    public String getServiceName() {
        return this.mServiceName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Bluetooth OPP Server SDP Record:\n");
        stringBuilder.append("  RFCOMM Chan Number: ");
        stringBuilder.append(this.mRfcommChannel);
        stringBuilder.append("\n  L2CAP PSM: ");
        stringBuilder.append(this.mL2capPsm);
        stringBuilder.append("\n  Profile version: ");
        stringBuilder.append(this.mProfileVersion);
        stringBuilder.append("\n  Service Name: ");
        stringBuilder.append(this.mServiceName);
        stringBuilder.append("\n  Formats List: ");
        stringBuilder.append(Arrays.toString(this.mFormatsList));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRfcommChannel);
        parcel.writeInt(this.mL2capPsm);
        parcel.writeInt(this.mProfileVersion);
        parcel.writeString(this.mServiceName);
        byte[] arrby = this.mFormatsList;
        if (arrby != null && arrby.length > 0) {
            parcel.writeInt(arrby.length);
            parcel.writeByteArray(this.mFormatsList);
        } else {
            parcel.writeInt(0);
        }
    }

}

