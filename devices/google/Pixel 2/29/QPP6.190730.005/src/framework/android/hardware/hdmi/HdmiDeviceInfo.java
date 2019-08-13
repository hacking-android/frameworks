/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public class HdmiDeviceInfo
implements Parcelable {
    public static final int ADDR_INTERNAL = 0;
    public static final Parcelable.Creator<HdmiDeviceInfo> CREATOR;
    public static final int DEVICE_AUDIO_SYSTEM = 5;
    public static final int DEVICE_INACTIVE = -1;
    public static final int DEVICE_PLAYBACK = 4;
    public static final int DEVICE_PURE_CEC_SWITCH = 6;
    public static final int DEVICE_RECORDER = 1;
    public static final int DEVICE_RESERVED = 2;
    public static final int DEVICE_TUNER = 3;
    public static final int DEVICE_TV = 0;
    public static final int DEVICE_VIDEO_PROCESSOR = 7;
    private static final int HDMI_DEVICE_TYPE_CEC = 0;
    private static final int HDMI_DEVICE_TYPE_HARDWARE = 2;
    private static final int HDMI_DEVICE_TYPE_INACTIVE = 100;
    private static final int HDMI_DEVICE_TYPE_MHL = 1;
    public static final int ID_INVALID = 65535;
    private static final int ID_OFFSET_CEC = 0;
    private static final int ID_OFFSET_HARDWARE = 192;
    private static final int ID_OFFSET_MHL = 128;
    public static final HdmiDeviceInfo INACTIVE_DEVICE;
    public static final int PATH_INTERNAL = 0;
    public static final int PATH_INVALID = 65535;
    public static final int PORT_INVALID = -1;
    private final int mAdopterId;
    private final int mDeviceId;
    private final int mDevicePowerStatus;
    private final int mDeviceType;
    private final String mDisplayName;
    private final int mHdmiDeviceType;
    private final int mId;
    private final int mLogicalAddress;
    private final int mPhysicalAddress;
    private final int mPortId;
    private final int mVendorId;

    static {
        INACTIVE_DEVICE = new HdmiDeviceInfo();
        CREATOR = new Parcelable.Creator<HdmiDeviceInfo>(){

            @Override
            public HdmiDeviceInfo createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                int n2 = parcel.readInt();
                int n3 = parcel.readInt();
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 100) {
                                return null;
                            }
                            return INACTIVE_DEVICE;
                        }
                        return new HdmiDeviceInfo(n2, n3);
                    }
                    n = parcel.readInt();
                    return new HdmiDeviceInfo(n2, n3, parcel.readInt(), n);
                }
                n = parcel.readInt();
                int n4 = parcel.readInt();
                int n5 = parcel.readInt();
                int n6 = parcel.readInt();
                return new HdmiDeviceInfo(n, n2, n3, n4, n5, parcel.readString(), n6);
            }

            public HdmiDeviceInfo[] newArray(int n) {
                return new HdmiDeviceInfo[n];
            }
        };
    }

    public HdmiDeviceInfo() {
        this.mHdmiDeviceType = 100;
        this.mPhysicalAddress = 65535;
        this.mId = 65535;
        this.mLogicalAddress = -1;
        this.mDeviceType = -1;
        this.mPortId = -1;
        this.mDevicePowerStatus = -1;
        this.mDisplayName = "Inactive";
        this.mVendorId = 0;
        this.mDeviceId = -1;
        this.mAdopterId = -1;
    }

    public HdmiDeviceInfo(int n, int n2) {
        this.mHdmiDeviceType = 2;
        this.mPhysicalAddress = n;
        this.mPortId = n2;
        this.mId = HdmiDeviceInfo.idForHardware(n2);
        this.mLogicalAddress = -1;
        this.mDeviceType = 2;
        this.mVendorId = 0;
        this.mDevicePowerStatus = -1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HDMI");
        stringBuilder.append(n2);
        this.mDisplayName = stringBuilder.toString();
        this.mDeviceId = -1;
        this.mAdopterId = -1;
    }

    public HdmiDeviceInfo(int n, int n2, int n3, int n4) {
        this.mHdmiDeviceType = 1;
        this.mPhysicalAddress = n;
        this.mPortId = n2;
        this.mId = HdmiDeviceInfo.idForMhlDevice(n2);
        this.mLogicalAddress = -1;
        this.mDeviceType = 2;
        this.mVendorId = 0;
        this.mDevicePowerStatus = -1;
        this.mDisplayName = "Mobile";
        this.mDeviceId = n3;
        this.mAdopterId = n4;
    }

    public HdmiDeviceInfo(int n, int n2, int n3, int n4, int n5, String string2) {
        this(n, n2, n3, n4, n5, string2, -1);
    }

    public HdmiDeviceInfo(int n, int n2, int n3, int n4, int n5, String string2, int n6) {
        this.mHdmiDeviceType = 0;
        this.mPhysicalAddress = n2;
        this.mPortId = n3;
        this.mId = HdmiDeviceInfo.idForCecDevice(n);
        this.mLogicalAddress = n;
        this.mDeviceType = n4;
        this.mVendorId = n5;
        this.mDevicePowerStatus = n6;
        this.mDisplayName = string2;
        this.mDeviceId = -1;
        this.mAdopterId = -1;
    }

    public static int idForCecDevice(int n) {
        return n + 0;
    }

    public static int idForHardware(int n) {
        return n + 192;
    }

    public static int idForMhlDevice(int n) {
        return n + 128;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof HdmiDeviceInfo;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (HdmiDeviceInfo)object;
            if (this.mHdmiDeviceType != ((HdmiDeviceInfo)object).mHdmiDeviceType || this.mPhysicalAddress != ((HdmiDeviceInfo)object).mPhysicalAddress || this.mPortId != ((HdmiDeviceInfo)object).mPortId || this.mLogicalAddress != ((HdmiDeviceInfo)object).mLogicalAddress || this.mDeviceType != ((HdmiDeviceInfo)object).mDeviceType || this.mVendorId != ((HdmiDeviceInfo)object).mVendorId || this.mDevicePowerStatus != ((HdmiDeviceInfo)object).mDevicePowerStatus || !this.mDisplayName.equals(((HdmiDeviceInfo)object).mDisplayName) || this.mDeviceId != ((HdmiDeviceInfo)object).mDeviceId || this.mAdopterId != ((HdmiDeviceInfo)object).mAdopterId) break block1;
            bl = true;
        }
        return bl;
    }

    public int getAdopterId() {
        return this.mAdopterId;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public int getDevicePowerStatus() {
        return this.mDevicePowerStatus;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public int getId() {
        return this.mId;
    }

    public int getLogicalAddress() {
        return this.mLogicalAddress;
    }

    public int getPhysicalAddress() {
        return this.mPhysicalAddress;
    }

    public int getPortId() {
        return this.mPortId;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public boolean isCecDevice() {
        boolean bl = this.mHdmiDeviceType == 0;
        return bl;
    }

    public boolean isInactivated() {
        boolean bl = this.mHdmiDeviceType == 100;
        return bl;
    }

    public boolean isMhlDevice() {
        int n = this.mHdmiDeviceType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isSourceType() {
        boolean bl = this.isCecDevice();
        boolean bl2 = false;
        if (bl) {
            int n = this.mDeviceType;
            if (n == 4 || n == 1 || n == 3) {
                bl2 = true;
            }
            return bl2;
        }
        return this.isMhlDevice();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        int n = this.mHdmiDeviceType;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 100) {
                        return "";
                    }
                    stringBuffer.append("Inactivated: ");
                } else {
                    stringBuffer.append("Hardware: ");
                }
            } else {
                stringBuffer.append("MHL: ");
                stringBuffer.append("device_id: ");
                stringBuffer.append(String.format("0x%04X", this.mDeviceId));
                stringBuffer.append(" ");
                stringBuffer.append("adopter_id: ");
                stringBuffer.append(String.format("0x%04X", this.mAdopterId));
                stringBuffer.append(" ");
            }
        } else {
            stringBuffer.append("CEC: ");
            stringBuffer.append("logical_address: ");
            stringBuffer.append(String.format("0x%02X", this.mLogicalAddress));
            stringBuffer.append(" ");
            stringBuffer.append("device_type: ");
            stringBuffer.append(this.mDeviceType);
            stringBuffer.append(" ");
            stringBuffer.append("vendor_id: ");
            stringBuffer.append(this.mVendorId);
            stringBuffer.append(" ");
            stringBuffer.append("display_name: ");
            stringBuffer.append(this.mDisplayName);
            stringBuffer.append(" ");
            stringBuffer.append("power_status: ");
            stringBuffer.append(this.mDevicePowerStatus);
            stringBuffer.append(" ");
        }
        stringBuffer.append("physical_address: ");
        stringBuffer.append(String.format("0x%04X", this.mPhysicalAddress));
        stringBuffer.append(" ");
        stringBuffer.append("port_id: ");
        stringBuffer.append(this.mPortId);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mHdmiDeviceType);
        parcel.writeInt(this.mPhysicalAddress);
        parcel.writeInt(this.mPortId);
        n = this.mHdmiDeviceType;
        if (n != 0) {
            if (n == 1) {
                parcel.writeInt(this.mDeviceId);
                parcel.writeInt(this.mAdopterId);
            }
        } else {
            parcel.writeInt(this.mLogicalAddress);
            parcel.writeInt(this.mDeviceType);
            parcel.writeInt(this.mVendorId);
            parcel.writeInt(this.mDevicePowerStatus);
            parcel.writeString(this.mDisplayName);
        }
    }

}

