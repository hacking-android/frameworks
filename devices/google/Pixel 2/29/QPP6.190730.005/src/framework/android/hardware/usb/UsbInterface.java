/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.hardware.usb.UsbEndpoint;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public class UsbInterface
implements Parcelable {
    public static final Parcelable.Creator<UsbInterface> CREATOR = new Parcelable.Creator<UsbInterface>(){

        @Override
        public UsbInterface createFromParcel(Parcel arrparcelable) {
            int n = arrparcelable.readInt();
            int n2 = arrparcelable.readInt();
            Object object = arrparcelable.readString();
            int n3 = arrparcelable.readInt();
            int n4 = arrparcelable.readInt();
            int n5 = arrparcelable.readInt();
            arrparcelable = arrparcelable.readParcelableArray(UsbEndpoint.class.getClassLoader());
            object = new UsbInterface(n, n2, (String)object, n3, n4, n5);
            ((UsbInterface)object).setEndpoints(arrparcelable);
            return object;
        }

        public UsbInterface[] newArray(int n) {
            return new UsbInterface[n];
        }
    };
    private final int mAlternateSetting;
    private final int mClass;
    private Parcelable[] mEndpoints;
    private final int mId;
    private final String mName;
    private final int mProtocol;
    private final int mSubclass;

    public UsbInterface(int n, int n2, String string2, int n3, int n4, int n5) {
        this.mId = n;
        this.mAlternateSetting = n2;
        this.mName = string2;
        this.mClass = n3;
        this.mSubclass = n4;
        this.mProtocol = n5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAlternateSetting() {
        return this.mAlternateSetting;
    }

    public UsbEndpoint getEndpoint(int n) {
        return (UsbEndpoint)this.mEndpoints[n];
    }

    public int getEndpointCount() {
        return this.mEndpoints.length;
    }

    public int getId() {
        return this.mId;
    }

    public int getInterfaceClass() {
        return this.mClass;
    }

    public int getInterfaceProtocol() {
        return this.mProtocol;
    }

    public int getInterfaceSubclass() {
        return this.mSubclass;
    }

    public String getName() {
        return this.mName;
    }

    public void setEndpoints(Parcelable[] arrparcelable) {
        this.mEndpoints = Preconditions.checkArrayElementsNotNull(arrparcelable, "endpoints");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbInterface[mId=");
        stringBuilder.append(this.mId);
        stringBuilder.append(",mAlternateSetting=");
        stringBuilder.append(this.mAlternateSetting);
        stringBuilder.append(",mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(",mClass=");
        stringBuilder.append(this.mClass);
        stringBuilder.append(",mSubclass=");
        stringBuilder.append(this.mSubclass);
        stringBuilder.append(",mProtocol=");
        stringBuilder.append(this.mProtocol);
        stringBuilder.append(",mEndpoints=[");
        stringBuilder = new StringBuilder(stringBuilder.toString());
        for (int i = 0; i < this.mEndpoints.length; ++i) {
            stringBuilder.append("\n");
            stringBuilder.append(this.mEndpoints[i].toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAlternateSetting);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeParcelableArray(this.mEndpoints, 0);
    }

}

