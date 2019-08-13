/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.hardware.usb.IUsbSerialReader;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbInterface;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;

public class UsbDevice
implements Parcelable {
    public static final Parcelable.Creator<UsbDevice> CREATOR = new Parcelable.Creator<UsbDevice>(){

        @Override
        public UsbDevice createFromParcel(Parcel parcel) {
            String string2 = parcel.readString();
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            int n5 = parcel.readInt();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            IUsbSerialReader iUsbSerialReader = IUsbSerialReader.Stub.asInterface(parcel.readStrongBinder());
            return new UsbDevice(string2, n, n2, n3, n4, n5, string3, string4, string5, (UsbConfiguration[])parcel.readParcelableArray(UsbConfiguration.class.getClassLoader(), UsbConfiguration.class), iUsbSerialReader);
        }

        public UsbDevice[] newArray(int n) {
            return new UsbDevice[n];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "UsbDevice";
    private final int mClass;
    private final UsbConfiguration[] mConfigurations;
    @UnsupportedAppUsage
    private UsbInterface[] mInterfaces;
    private final String mManufacturerName;
    private final String mName;
    private final int mProductId;
    private final String mProductName;
    private final int mProtocol;
    private final IUsbSerialReader mSerialNumberReader;
    private final int mSubclass;
    private final int mVendorId;
    private final String mVersion;

    private UsbDevice(String string2, int n, int n2, int n3, int n4, int n5, String string3, String string4, String string5, UsbConfiguration[] arrusbConfiguration, IUsbSerialReader iUsbSerialReader) {
        this.mName = Preconditions.checkNotNull(string2);
        this.mVendorId = n;
        this.mProductId = n2;
        this.mClass = n3;
        this.mSubclass = n4;
        this.mProtocol = n5;
        this.mManufacturerName = string3;
        this.mProductName = string4;
        this.mVersion = Preconditions.checkStringNotEmpty(string5);
        this.mConfigurations = Preconditions.checkArrayElementsNotNull(arrusbConfiguration, "configurations");
        this.mSerialNumberReader = Preconditions.checkNotNull(iUsbSerialReader);
        if (ActivityThread.isSystem()) {
            Preconditions.checkArgument(this.mSerialNumberReader instanceof IUsbSerialReader.Stub);
        }
    }

    public static int getDeviceId(String string2) {
        return UsbDevice.native_get_device_id(string2);
    }

    public static String getDeviceName(int n) {
        return UsbDevice.native_get_device_name(n);
    }

    private UsbInterface[] getInterfaceList() {
        if (this.mInterfaces == null) {
            int n;
            int n2 = this.mConfigurations.length;
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                n3 += this.mConfigurations[n].getInterfaceCount();
            }
            this.mInterfaces = new UsbInterface[n3];
            n3 = 0;
            for (n = 0; n < n2; ++n) {
                UsbConfiguration usbConfiguration = this.mConfigurations[n];
                int n4 = usbConfiguration.getInterfaceCount();
                int n5 = 0;
                while (n5 < n4) {
                    this.mInterfaces[n3] = usbConfiguration.getInterface(n5);
                    ++n5;
                    ++n3;
                }
            }
        }
        return this.mInterfaces;
    }

    private static native int native_get_device_id(String var0);

    private static native String native_get_device_name(int var0);

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof UsbDevice) {
            return ((UsbDevice)object).mName.equals(this.mName);
        }
        if (object instanceof String) {
            return ((String)object).equals(this.mName);
        }
        return false;
    }

    public UsbConfiguration getConfiguration(int n) {
        return this.mConfigurations[n];
    }

    public int getConfigurationCount() {
        return this.mConfigurations.length;
    }

    public int getDeviceClass() {
        return this.mClass;
    }

    public int getDeviceId() {
        return UsbDevice.getDeviceId(this.mName);
    }

    public String getDeviceName() {
        return this.mName;
    }

    public int getDeviceProtocol() {
        return this.mProtocol;
    }

    public int getDeviceSubclass() {
        return this.mSubclass;
    }

    public UsbInterface getInterface(int n) {
        return this.getInterfaceList()[n];
    }

    public int getInterfaceCount() {
        return this.getInterfaceList().length;
    }

    public String getManufacturerName() {
        return this.mManufacturerName;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public String getProductName() {
        return this.mProductName;
    }

    public String getSerialNumber() {
        try {
            String string2 = this.mSerialNumberReader.getSerial(ActivityThread.currentPackageName());
            return string2;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbDevice[mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(",mVendorId=");
        stringBuilder.append(this.mVendorId);
        stringBuilder.append(",mProductId=");
        stringBuilder.append(this.mProductId);
        stringBuilder.append(",mClass=");
        stringBuilder.append(this.mClass);
        stringBuilder.append(",mSubclass=");
        stringBuilder.append(this.mSubclass);
        stringBuilder.append(",mProtocol=");
        stringBuilder.append(this.mProtocol);
        stringBuilder.append(",mManufacturerName=");
        stringBuilder.append(this.mManufacturerName);
        stringBuilder.append(",mProductName=");
        stringBuilder.append(this.mProductName);
        stringBuilder.append(",mVersion=");
        stringBuilder.append(this.mVersion);
        stringBuilder.append(",mSerialNumberReader=");
        stringBuilder.append(this.mSerialNumberReader);
        stringBuilder.append(",mConfigurations=[");
        stringBuilder = new StringBuilder(stringBuilder.toString());
        for (int i = 0; i < this.mConfigurations.length; ++i) {
            stringBuilder.append("\n");
            stringBuilder.append(this.mConfigurations[i].toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeString(this.mManufacturerName);
        parcel.writeString(this.mProductName);
        parcel.writeString(this.mVersion);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
        parcel.writeParcelableArray((Parcelable[])this.mConfigurations, 0);
    }

    public static class Builder {
        private final int mClass;
        private final UsbConfiguration[] mConfigurations;
        private final String mManufacturerName;
        private final String mName;
        private final int mProductId;
        private final String mProductName;
        private final int mProtocol;
        private final int mSubclass;
        private final int mVendorId;
        private final String mVersion;
        public final String serialNumber;

        public Builder(String string2, int n, int n2, int n3, int n4, int n5, String string3, String string4, String string5, UsbConfiguration[] arrusbConfiguration, String string6) {
            this.mName = Preconditions.checkNotNull(string2);
            this.mVendorId = n;
            this.mProductId = n2;
            this.mClass = n3;
            this.mSubclass = n4;
            this.mProtocol = n5;
            this.mManufacturerName = string3;
            this.mProductName = string4;
            this.mVersion = Preconditions.checkStringNotEmpty(string5);
            this.mConfigurations = arrusbConfiguration;
            this.serialNumber = string6;
        }

        public UsbDevice build(IUsbSerialReader iUsbSerialReader) {
            return new UsbDevice(this.mName, this.mVendorId, this.mProductId, this.mClass, this.mSubclass, this.mProtocol, this.mManufacturerName, this.mProductName, this.mVersion, this.mConfigurations, iUsbSerialReader);
        }
    }

}

