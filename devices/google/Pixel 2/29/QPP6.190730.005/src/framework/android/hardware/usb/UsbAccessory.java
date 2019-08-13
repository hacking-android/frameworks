/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.app.ActivityThread;
import android.hardware.usb.IUsbSerialReader;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;

public class UsbAccessory
implements Parcelable {
    public static final Parcelable.Creator<UsbAccessory> CREATOR = new Parcelable.Creator<UsbAccessory>(){

        @Override
        public UsbAccessory createFromParcel(Parcel parcel) {
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            String string4 = parcel.readString();
            String string5 = parcel.readString();
            String string6 = parcel.readString();
            return new UsbAccessory(string2, string3, string4, string5, string6, IUsbSerialReader.Stub.asInterface(parcel.readStrongBinder()));
        }

        public UsbAccessory[] newArray(int n) {
            return new UsbAccessory[n];
        }
    };
    public static final int DESCRIPTION_STRING = 2;
    public static final int MANUFACTURER_STRING = 0;
    public static final int MODEL_STRING = 1;
    public static final int SERIAL_STRING = 5;
    private static final String TAG = "UsbAccessory";
    public static final int URI_STRING = 4;
    public static final int VERSION_STRING = 3;
    private final String mDescription;
    private final String mManufacturer;
    private final String mModel;
    private final IUsbSerialReader mSerialNumberReader;
    private final String mUri;
    private final String mVersion;

    public UsbAccessory(String string2, String string3, String string4, String string5, String string6, IUsbSerialReader iUsbSerialReader) {
        this.mManufacturer = Preconditions.checkNotNull(string2);
        this.mModel = Preconditions.checkNotNull(string3);
        this.mDescription = string4;
        this.mVersion = string5;
        this.mUri = string6;
        this.mSerialNumberReader = iUsbSerialReader;
        if (ActivityThread.isSystem()) {
            Preconditions.checkArgument(this.mSerialNumberReader instanceof IUsbSerialReader.Stub);
        }
    }

    @Deprecated
    public UsbAccessory(String string2, String string3, String string4, String string5, String string6, String string7) {
        this(string2, string3, string4, string5, string6, new IUsbSerialReader.Stub(){

            @Override
            public String getSerial(String string2) {
                return String.this;
            }
        });
    }

    private static boolean compare(String string2, String string3) {
        if (string2 == null) {
            boolean bl = string3 == null;
            return bl;
        }
        return string2.equals(string3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof UsbAccessory;
        boolean bl2 = false;
        if (bl) {
            if (UsbAccessory.compare(this.mManufacturer, ((UsbAccessory)(object = (UsbAccessory)object)).getManufacturer()) && UsbAccessory.compare(this.mModel, ((UsbAccessory)object).getModel()) && UsbAccessory.compare(this.mDescription, ((UsbAccessory)object).getDescription()) && UsbAccessory.compare(this.mVersion, ((UsbAccessory)object).getVersion()) && UsbAccessory.compare(this.mUri, ((UsbAccessory)object).getUri()) && UsbAccessory.compare(this.getSerial(), ((UsbAccessory)object).getSerial())) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getManufacturer() {
        return this.mManufacturer;
    }

    public String getModel() {
        return this.mModel;
    }

    public String getSerial() {
        try {
            String string2 = this.mSerialNumberReader.getSerial(ActivityThread.currentPackageName());
            return string2;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
            return null;
        }
    }

    public String getUri() {
        return this.mUri;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public int hashCode() {
        int n = this.mManufacturer.hashCode();
        int n2 = this.mModel.hashCode();
        String string2 = this.mDescription;
        int n3 = 0;
        int n4 = string2 == null ? 0 : string2.hashCode();
        string2 = this.mVersion;
        int n5 = string2 == null ? 0 : string2.hashCode();
        string2 = this.mUri;
        if (string2 != null) {
            n3 = string2.hashCode();
        }
        return n ^ n2 ^ n4 ^ n5 ^ n3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UsbAccessory[mManufacturer=");
        stringBuilder.append(this.mManufacturer);
        stringBuilder.append(", mModel=");
        stringBuilder.append(this.mModel);
        stringBuilder.append(", mDescription=");
        stringBuilder.append(this.mDescription);
        stringBuilder.append(", mVersion=");
        stringBuilder.append(this.mVersion);
        stringBuilder.append(", mUri=");
        stringBuilder.append(this.mUri);
        stringBuilder.append(", mSerialNumberReader=");
        stringBuilder.append(this.mSerialNumberReader);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mManufacturer);
        parcel.writeString(this.mModel);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mVersion);
        parcel.writeString(this.mUri);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
    }

}

