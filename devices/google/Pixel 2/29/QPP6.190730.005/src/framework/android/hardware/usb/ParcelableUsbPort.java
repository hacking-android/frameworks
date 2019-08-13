/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.Immutable;

@Immutable
public final class ParcelableUsbPort
implements Parcelable {
    public static final Parcelable.Creator<ParcelableUsbPort> CREATOR = new Parcelable.Creator<ParcelableUsbPort>(){

        @Override
        public ParcelableUsbPort createFromParcel(Parcel parcel) {
            return new ParcelableUsbPort(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean());
        }

        public ParcelableUsbPort[] newArray(int n) {
            return new ParcelableUsbPort[n];
        }
    };
    private final String mId;
    private final int mSupportedContaminantProtectionModes;
    private final int mSupportedModes;
    private final boolean mSupportsEnableContaminantPresenceDetection;
    private final boolean mSupportsEnableContaminantPresenceProtection;

    private ParcelableUsbPort(String string2, int n, int n2, boolean bl, boolean bl2) {
        this.mId = string2;
        this.mSupportedModes = n;
        this.mSupportedContaminantProtectionModes = n2;
        this.mSupportsEnableContaminantPresenceProtection = bl;
        this.mSupportsEnableContaminantPresenceDetection = bl2;
    }

    public static ParcelableUsbPort of(UsbPort usbPort) {
        return new ParcelableUsbPort(usbPort.getId(), usbPort.getSupportedModes(), usbPort.getSupportedContaminantProtectionModes(), usbPort.supportsEnableContaminantPresenceProtection(), usbPort.supportsEnableContaminantPresenceDetection());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UsbPort getUsbPort(UsbManager usbManager) {
        return new UsbPort(usbManager, this.mId, this.mSupportedModes, this.mSupportedContaminantProtectionModes, this.mSupportsEnableContaminantPresenceProtection, this.mSupportsEnableContaminantPresenceDetection);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mSupportedModes);
        parcel.writeInt(this.mSupportedContaminantProtectionModes);
        parcel.writeBoolean(this.mSupportsEnableContaminantPresenceProtection);
        parcel.writeBoolean(this.mSupportsEnableContaminantPresenceDetection);
    }

}

