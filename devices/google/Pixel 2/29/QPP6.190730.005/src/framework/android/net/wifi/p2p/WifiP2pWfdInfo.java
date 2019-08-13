/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;

public class WifiP2pWfdInfo
implements Parcelable {
    private static final int COUPLED_SINK_SUPPORT_AT_SINK = 8;
    private static final int COUPLED_SINK_SUPPORT_AT_SOURCE = 4;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiP2pWfdInfo> CREATOR = new Parcelable.Creator<WifiP2pWfdInfo>(){

        @Override
        public WifiP2pWfdInfo createFromParcel(Parcel parcel) {
            WifiP2pWfdInfo wifiP2pWfdInfo = new WifiP2pWfdInfo();
            wifiP2pWfdInfo.readFromParcel(parcel);
            return wifiP2pWfdInfo;
        }

        public WifiP2pWfdInfo[] newArray(int n) {
            return new WifiP2pWfdInfo[n];
        }
    };
    private static final int DEVICE_TYPE = 3;
    public static final int PRIMARY_SINK = 1;
    public static final int SECONDARY_SINK = 2;
    private static final int SESSION_AVAILABLE = 48;
    private static final int SESSION_AVAILABLE_BIT1 = 16;
    private static final int SESSION_AVAILABLE_BIT2 = 32;
    public static final int SOURCE_OR_PRIMARY_SINK = 3;
    private static final String TAG = "WifiP2pWfdInfo";
    public static final int WFD_SOURCE = 0;
    private int mCtrlPort;
    private int mDeviceInfo;
    private int mMaxThroughput;
    private boolean mWfdEnabled;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public WifiP2pWfdInfo() {
    }

    @UnsupportedAppUsage
    public WifiP2pWfdInfo(int n, int n2, int n3) {
        this.mWfdEnabled = true;
        this.mDeviceInfo = n;
        this.mCtrlPort = n2;
        this.mMaxThroughput = n3;
    }

    @UnsupportedAppUsage
    public WifiP2pWfdInfo(WifiP2pWfdInfo wifiP2pWfdInfo) {
        if (wifiP2pWfdInfo != null) {
            this.mWfdEnabled = wifiP2pWfdInfo.mWfdEnabled;
            this.mDeviceInfo = wifiP2pWfdInfo.mDeviceInfo;
            this.mCtrlPort = wifiP2pWfdInfo.mCtrlPort;
            this.mMaxThroughput = wifiP2pWfdInfo.mMaxThroughput;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getControlPort() {
        return this.mCtrlPort;
    }

    public String getDeviceInfoHex() {
        return String.format(Locale.US, "%04x%04x%04x", this.mDeviceInfo, this.mCtrlPort, this.mMaxThroughput);
    }

    @UnsupportedAppUsage
    public int getDeviceType() {
        return this.mDeviceInfo & 3;
    }

    public int getMaxThroughput() {
        return this.mMaxThroughput;
    }

    public boolean isCoupledSinkSupportedAtSink() {
        boolean bl = (this.mDeviceInfo & 8) != 0;
        return bl;
    }

    public boolean isCoupledSinkSupportedAtSource() {
        boolean bl = (this.mDeviceInfo & 8) != 0;
        return bl;
    }

    public boolean isSessionAvailable() {
        boolean bl = (this.mDeviceInfo & 48) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isWfdEnabled() {
        return this.mWfdEnabled;
    }

    public void readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mWfdEnabled = bl;
        this.mDeviceInfo = parcel.readInt();
        this.mCtrlPort = parcel.readInt();
        this.mMaxThroughput = parcel.readInt();
    }

    @UnsupportedAppUsage
    public void setControlPort(int n) {
        this.mCtrlPort = n;
    }

    public void setCoupledSinkSupportAtSink(boolean bl) {
        this.mDeviceInfo = bl ? (this.mDeviceInfo |= 8) : (this.mDeviceInfo &= -9);
    }

    public void setCoupledSinkSupportAtSource(boolean bl) {
        this.mDeviceInfo = bl ? (this.mDeviceInfo |= 8) : (this.mDeviceInfo &= -9);
    }

    @UnsupportedAppUsage
    public boolean setDeviceType(int n) {
        if (n >= 0 && n <= 3) {
            this.mDeviceInfo &= -4;
            this.mDeviceInfo |= n;
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public void setMaxThroughput(int n) {
        this.mMaxThroughput = n;
    }

    @UnsupportedAppUsage
    public void setSessionAvailable(boolean bl) {
        if (bl) {
            this.mDeviceInfo |= 16;
            this.mDeviceInfo &= -33;
        } else {
            this.mDeviceInfo &= -49;
        }
    }

    @UnsupportedAppUsage
    public void setWfdEnabled(boolean bl) {
        this.mWfdEnabled = bl;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("WFD enabled: ");
        stringBuffer.append(this.mWfdEnabled);
        stringBuffer.append("WFD DeviceInfo: ");
        stringBuffer.append(this.mDeviceInfo);
        stringBuffer.append("\n WFD CtrlPort: ");
        stringBuffer.append(this.mCtrlPort);
        stringBuffer.append("\n WFD MaxThroughput: ");
        stringBuffer.append(this.mMaxThroughput);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.mWfdEnabled);
        parcel.writeInt(this.mDeviceInfo);
        parcel.writeInt(this.mCtrlPort);
        parcel.writeInt(this.mMaxThroughput);
    }

}

