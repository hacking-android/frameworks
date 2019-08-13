/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.os.Parcel;
import android.os.Parcelable;

public class UsbEndpoint
implements Parcelable {
    public static final Parcelable.Creator<UsbEndpoint> CREATOR = new Parcelable.Creator<UsbEndpoint>(){

        @Override
        public UsbEndpoint createFromParcel(Parcel parcel) {
            return new UsbEndpoint(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public UsbEndpoint[] newArray(int n) {
            return new UsbEndpoint[n];
        }
    };
    private final int mAddress;
    private final int mAttributes;
    private final int mInterval;
    private final int mMaxPacketSize;

    public UsbEndpoint(int n, int n2, int n3, int n4) {
        this.mAddress = n;
        this.mAttributes = n2;
        this.mMaxPacketSize = n3;
        this.mInterval = n4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAddress() {
        return this.mAddress;
    }

    public int getAttributes() {
        return this.mAttributes;
    }

    public int getDirection() {
        return this.mAddress & 128;
    }

    public int getEndpointNumber() {
        return this.mAddress & 15;
    }

    public int getInterval() {
        return this.mInterval;
    }

    public int getMaxPacketSize() {
        return this.mMaxPacketSize;
    }

    public int getType() {
        return this.mAttributes & 3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbEndpoint[mAddress=");
        stringBuilder.append(this.mAddress);
        stringBuilder.append(",mAttributes=");
        stringBuilder.append(this.mAttributes);
        stringBuilder.append(",mMaxPacketSize=");
        stringBuilder.append(this.mMaxPacketSize);
        stringBuilder.append(",mInterval=");
        stringBuilder.append(this.mInterval);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAddress);
        parcel.writeInt(this.mAttributes);
        parcel.writeInt(this.mMaxPacketSize);
        parcel.writeInt(this.mInterval);
    }

}

