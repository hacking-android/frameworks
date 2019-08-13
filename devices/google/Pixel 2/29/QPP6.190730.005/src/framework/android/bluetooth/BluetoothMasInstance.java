/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

public final class BluetoothMasInstance
implements Parcelable {
    public static final Parcelable.Creator<BluetoothMasInstance> CREATOR = new Parcelable.Creator<BluetoothMasInstance>(){

        @Override
        public BluetoothMasInstance createFromParcel(Parcel parcel) {
            return new BluetoothMasInstance(parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt());
        }

        public BluetoothMasInstance[] newArray(int n) {
            return new BluetoothMasInstance[n];
        }
    };
    private final int mChannel;
    private final int mId;
    private final int mMsgTypes;
    private final String mName;

    public BluetoothMasInstance(int n, String string2, int n2, int n3) {
        this.mId = n;
        this.mName = string2;
        this.mChannel = n2;
        this.mMsgTypes = n3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BluetoothMasInstance;
        boolean bl2 = false;
        if (bl) {
            if (this.mId == ((BluetoothMasInstance)object).mId) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int getChannel() {
        return this.mChannel;
    }

    public int getId() {
        return this.mId;
    }

    public int getMsgTypes() {
        return this.mMsgTypes;
    }

    public String getName() {
        return this.mName;
    }

    public int hashCode() {
        return this.mId + (this.mChannel << 8) + (this.mMsgTypes << 16);
    }

    public boolean msgSupported(int n) {
        boolean bl = (this.mMsgTypes & n) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.mId));
        stringBuilder.append(":");
        stringBuilder.append(this.mName);
        stringBuilder.append(":");
        stringBuilder.append(this.mChannel);
        stringBuilder.append(":");
        stringBuilder.append(Integer.toHexString(this.mMsgTypes));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mChannel);
        parcel.writeInt(this.mMsgTypes);
    }

    public static final class MessageType {
        public static final int EMAIL = 1;
        public static final int MMS = 8;
        public static final int SMS_CDMA = 4;
        public static final int SMS_GSM = 2;
    }

}

