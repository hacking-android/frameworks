/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.p2p.WifiP2pWfdInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiP2pDevice
implements Parcelable {
    public static final int AVAILABLE = 3;
    public static final int CONNECTED = 0;
    public static final Parcelable.Creator<WifiP2pDevice> CREATOR;
    private static final int DEVICE_CAPAB_CLIENT_DISCOVERABILITY = 2;
    private static final int DEVICE_CAPAB_CONCURRENT_OPER = 4;
    private static final int DEVICE_CAPAB_DEVICE_LIMIT = 16;
    private static final int DEVICE_CAPAB_INFRA_MANAGED = 8;
    private static final int DEVICE_CAPAB_INVITATION_PROCEDURE = 32;
    private static final int DEVICE_CAPAB_SERVICE_DISCOVERY = 1;
    public static final int FAILED = 2;
    private static final int GROUP_CAPAB_CROSS_CONN = 16;
    private static final int GROUP_CAPAB_GROUP_FORMATION = 64;
    private static final int GROUP_CAPAB_GROUP_LIMIT = 4;
    private static final int GROUP_CAPAB_GROUP_OWNER = 1;
    private static final int GROUP_CAPAB_INTRA_BSS_DIST = 8;
    private static final int GROUP_CAPAB_PERSISTENT_GROUP = 2;
    private static final int GROUP_CAPAB_PERSISTENT_RECONN = 32;
    public static final int INVITED = 1;
    private static final String TAG = "WifiP2pDevice";
    public static final int UNAVAILABLE = 4;
    private static final int WPS_CONFIG_DISPLAY = 8;
    private static final int WPS_CONFIG_KEYPAD = 256;
    private static final int WPS_CONFIG_PUSHBUTTON = 128;
    private static final Pattern detailedDevicePattern;
    private static final Pattern threeTokenPattern;
    private static final Pattern twoTokenPattern;
    public String deviceAddress = "";
    @UnsupportedAppUsage
    public int deviceCapability;
    public String deviceName = "";
    @UnsupportedAppUsage
    public int groupCapability;
    public String primaryDeviceType;
    public String secondaryDeviceType;
    public int status = 4;
    @UnsupportedAppUsage
    public WifiP2pWfdInfo wfdInfo;
    @UnsupportedAppUsage
    public int wpsConfigMethodsSupported;

    static {
        detailedDevicePattern = Pattern.compile("((?:[0-9a-f]{2}:){5}[0-9a-f]{2}) (\\d+ )?p2p_dev_addr=((?:[0-9a-f]{2}:){5}[0-9a-f]{2}) pri_dev_type=(\\d+-[0-9a-fA-F]+-\\d+) name='(.*)' config_methods=(0x[0-9a-fA-F]+) dev_capab=(0x[0-9a-fA-F]+) group_capab=(0x[0-9a-fA-F]+)( wfd_dev_info=0x([0-9a-fA-F]{12}))?");
        twoTokenPattern = Pattern.compile("(p2p_dev_addr=)?((?:[0-9a-f]{2}:){5}[0-9a-f]{2})");
        threeTokenPattern = Pattern.compile("(?:[0-9a-f]{2}:){5}[0-9a-f]{2} p2p_dev_addr=((?:[0-9a-f]{2}:){5}[0-9a-f]{2})");
        CREATOR = new Parcelable.Creator<WifiP2pDevice>(){

            @Override
            public WifiP2pDevice createFromParcel(Parcel parcel) {
                WifiP2pDevice wifiP2pDevice = new WifiP2pDevice();
                wifiP2pDevice.deviceName = parcel.readString();
                wifiP2pDevice.deviceAddress = parcel.readString();
                wifiP2pDevice.primaryDeviceType = parcel.readString();
                wifiP2pDevice.secondaryDeviceType = parcel.readString();
                wifiP2pDevice.wpsConfigMethodsSupported = parcel.readInt();
                wifiP2pDevice.deviceCapability = parcel.readInt();
                wifiP2pDevice.groupCapability = parcel.readInt();
                wifiP2pDevice.status = parcel.readInt();
                if (parcel.readInt() == 1) {
                    wifiP2pDevice.wfdInfo = WifiP2pWfdInfo.CREATOR.createFromParcel(parcel);
                }
                return wifiP2pDevice;
            }

            public WifiP2pDevice[] newArray(int n) {
                return new WifiP2pDevice[n];
            }
        };
    }

    public WifiP2pDevice() {
    }

    public WifiP2pDevice(WifiP2pDevice parcelable) {
        if (parcelable != null) {
            this.deviceName = parcelable.deviceName;
            this.deviceAddress = parcelable.deviceAddress;
            this.primaryDeviceType = parcelable.primaryDeviceType;
            this.secondaryDeviceType = parcelable.secondaryDeviceType;
            this.wpsConfigMethodsSupported = parcelable.wpsConfigMethodsSupported;
            this.deviceCapability = parcelable.deviceCapability;
            this.groupCapability = parcelable.groupCapability;
            this.status = parcelable.status;
            parcelable = parcelable.wfdInfo;
            if (parcelable != null) {
                this.wfdInfo = new WifiP2pWfdInfo((WifiP2pWfdInfo)parcelable);
            }
        }
    }

    @UnsupportedAppUsage
    public WifiP2pDevice(String object) throws IllegalArgumentException {
        String[] arrstring = ((String)object).split("[ \n]");
        if (arrstring.length >= 1) {
            int n = arrstring.length;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (((Matcher)(object = detailedDevicePattern.matcher((CharSequence)object))).find()) {
                            this.deviceAddress = ((Matcher)object).group(3);
                            this.primaryDeviceType = ((Matcher)object).group(4);
                            this.deviceName = ((Matcher)object).group(5);
                            this.wpsConfigMethodsSupported = this.parseHex(((Matcher)object).group(6));
                            this.deviceCapability = this.parseHex(((Matcher)object).group(7));
                            this.groupCapability = this.parseHex(((Matcher)object).group(8));
                            if (((Matcher)object).group(9) != null) {
                                object = ((Matcher)object).group(10);
                                this.wfdInfo = new WifiP2pWfdInfo(this.parseHex(((String)object).substring(0, 4)), this.parseHex(((String)object).substring(4, 8)), this.parseHex(((String)object).substring(8, 12)));
                            }
                            if (arrstring[0].startsWith("P2P-DEVICE-FOUND")) {
                                this.status = 3;
                            }
                            return;
                        }
                        throw new IllegalArgumentException("Malformed supplicant event");
                    }
                    if (((Matcher)(object = threeTokenPattern.matcher((CharSequence)object))).find()) {
                        this.deviceAddress = ((Matcher)object).group(1);
                        return;
                    }
                    throw new IllegalArgumentException("Malformed supplicant event");
                }
                if (((Matcher)(object = twoTokenPattern.matcher((CharSequence)object))).find()) {
                    this.deviceAddress = ((Matcher)object).group(2);
                    return;
                }
                throw new IllegalArgumentException("Malformed supplicant event");
            }
            this.deviceAddress = object;
            return;
        }
        throw new IllegalArgumentException("Malformed supplicant event");
    }

    private int parseHex(String string2) {
        int n;
        String string3;
        int n2;
        block5 : {
            block4 : {
                n = 0;
                if (string2.startsWith("0x")) break block4;
                string3 = string2;
                if (!string2.startsWith("0X")) break block5;
            }
            string3 = string2.substring(2);
        }
        try {
            n2 = Integer.parseInt(string3, 16);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to parse hex string ");
            stringBuilder.append(string3);
            Log.e(TAG, stringBuilder.toString());
            n2 = n;
        }
        return n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof WifiP2pDevice)) {
            return false;
        }
        if ((object = (WifiP2pDevice)object) != null && (object = ((WifiP2pDevice)object).deviceAddress) != null) {
            return ((String)object).equals(this.deviceAddress);
        }
        if (this.deviceAddress != null) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hashCode(this.deviceAddress);
    }

    public boolean isDeviceLimit() {
        boolean bl = (this.deviceCapability & 16) != 0;
        return bl;
    }

    public boolean isGroupLimit() {
        boolean bl = (this.groupCapability & 4) != 0;
        return bl;
    }

    public boolean isGroupOwner() {
        int n = this.groupCapability;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isInvitationCapable() {
        boolean bl = (this.deviceCapability & 32) != 0;
        return bl;
    }

    public boolean isServiceDiscoveryCapable() {
        int n = this.deviceCapability;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Device: ");
        stringBuffer.append(this.deviceName);
        stringBuffer.append("\n deviceAddress: ");
        stringBuffer.append(this.deviceAddress);
        stringBuffer.append("\n primary type: ");
        stringBuffer.append(this.primaryDeviceType);
        stringBuffer.append("\n secondary type: ");
        stringBuffer.append(this.secondaryDeviceType);
        stringBuffer.append("\n wps: ");
        stringBuffer.append(this.wpsConfigMethodsSupported);
        stringBuffer.append("\n grpcapab: ");
        stringBuffer.append(this.groupCapability);
        stringBuffer.append("\n devcapab: ");
        stringBuffer.append(this.deviceCapability);
        stringBuffer.append("\n status: ");
        stringBuffer.append(this.status);
        stringBuffer.append("\n wfdInfo: ");
        stringBuffer.append(this.wfdInfo);
        return stringBuffer.toString();
    }

    @UnsupportedAppUsage
    public void update(WifiP2pDevice wifiP2pDevice) {
        this.updateSupplicantDetails(wifiP2pDevice);
        this.status = wifiP2pDevice.status;
    }

    public void updateSupplicantDetails(WifiP2pDevice wifiP2pDevice) {
        if (wifiP2pDevice != null) {
            String string2 = wifiP2pDevice.deviceAddress;
            if (string2 != null) {
                if (this.deviceAddress.equals(string2)) {
                    this.deviceName = wifiP2pDevice.deviceName;
                    this.primaryDeviceType = wifiP2pDevice.primaryDeviceType;
                    this.secondaryDeviceType = wifiP2pDevice.secondaryDeviceType;
                    this.wpsConfigMethodsSupported = wifiP2pDevice.wpsConfigMethodsSupported;
                    this.deviceCapability = wifiP2pDevice.deviceCapability;
                    this.groupCapability = wifiP2pDevice.groupCapability;
                    this.wfdInfo = wifiP2pDevice.wfdInfo;
                    return;
                }
                throw new IllegalArgumentException("deviceAddress does not match");
            }
            throw new IllegalArgumentException("deviceAddress is null");
        }
        throw new IllegalArgumentException("device is null");
    }

    public boolean wpsDisplaySupported() {
        boolean bl = (this.wpsConfigMethodsSupported & 8) != 0;
        return bl;
    }

    public boolean wpsKeypadSupported() {
        boolean bl = (this.wpsConfigMethodsSupported & 256) != 0;
        return bl;
    }

    public boolean wpsPbcSupported() {
        boolean bl = (this.wpsConfigMethodsSupported & 128) != 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.deviceName);
        parcel.writeString(this.deviceAddress);
        parcel.writeString(this.primaryDeviceType);
        parcel.writeString(this.secondaryDeviceType);
        parcel.writeInt(this.wpsConfigMethodsSupported);
        parcel.writeInt(this.deviceCapability);
        parcel.writeInt(this.groupCapability);
        parcel.writeInt(this.status);
        if (this.wfdInfo != null) {
            parcel.writeInt(1);
            this.wfdInfo.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
    }

}

