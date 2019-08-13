/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.hardware.usb.UsbInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public class UsbConfiguration
implements Parcelable {
    private static final int ATTR_REMOTE_WAKEUP = 32;
    private static final int ATTR_SELF_POWERED = 64;
    public static final Parcelable.Creator<UsbConfiguration> CREATOR = new Parcelable.Creator<UsbConfiguration>(){

        @Override
        public UsbConfiguration createFromParcel(Parcel arrparcelable) {
            int n = arrparcelable.readInt();
            Object object = arrparcelable.readString();
            int n2 = arrparcelable.readInt();
            int n3 = arrparcelable.readInt();
            arrparcelable = arrparcelable.readParcelableArray(UsbInterface.class.getClassLoader());
            object = new UsbConfiguration(n, (String)object, n2, n3);
            ((UsbConfiguration)object).setInterfaces(arrparcelable);
            return object;
        }

        public UsbConfiguration[] newArray(int n) {
            return new UsbConfiguration[n];
        }
    };
    private final int mAttributes;
    private final int mId;
    private Parcelable[] mInterfaces;
    private final int mMaxPower;
    private final String mName;

    public UsbConfiguration(int n, String string2, int n2, int n3) {
        this.mId = n;
        this.mName = string2;
        this.mAttributes = n2;
        this.mMaxPower = n3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAttributes() {
        return this.mAttributes;
    }

    public int getId() {
        return this.mId;
    }

    public UsbInterface getInterface(int n) {
        return (UsbInterface)this.mInterfaces[n];
    }

    public int getInterfaceCount() {
        return this.mInterfaces.length;
    }

    public int getMaxPower() {
        return this.mMaxPower * 2;
    }

    public String getName() {
        return this.mName;
    }

    public boolean isRemoteWakeup() {
        boolean bl = (this.mAttributes & 32) != 0;
        return bl;
    }

    public boolean isSelfPowered() {
        boolean bl = (this.mAttributes & 64) != 0;
        return bl;
    }

    public void setInterfaces(Parcelable[] arrparcelable) {
        this.mInterfaces = Preconditions.checkArrayElementsNotNull(arrparcelable, "interfaces");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbConfiguration[mId=");
        stringBuilder.append(this.mId);
        stringBuilder.append(",mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(",mAttributes=");
        stringBuilder.append(this.mAttributes);
        stringBuilder.append(",mMaxPower=");
        stringBuilder.append(this.mMaxPower);
        stringBuilder.append(",mInterfaces=[");
        stringBuilder = new StringBuilder(stringBuilder.toString());
        for (int i = 0; i < this.mInterfaces.length; ++i) {
            stringBuilder.append("\n");
            stringBuilder.append(this.mInterfaces[i].toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mAttributes);
        parcel.writeInt(this.mMaxPower);
        parcel.writeParcelableArray(this.mInterfaces, 0);
    }

}

