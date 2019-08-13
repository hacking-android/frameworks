/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pWfdInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class WifiP2pDeviceList
implements Parcelable {
    public static final Parcelable.Creator<WifiP2pDeviceList> CREATOR = new Parcelable.Creator<WifiP2pDeviceList>(){

        @Override
        public WifiP2pDeviceList createFromParcel(Parcel parcel) {
            WifiP2pDeviceList wifiP2pDeviceList = new WifiP2pDeviceList();
            int n = parcel.readInt();
            for (int i = 0; i < n; ++i) {
                wifiP2pDeviceList.update((WifiP2pDevice)parcel.readParcelable(null));
            }
            return wifiP2pDeviceList;
        }

        public WifiP2pDeviceList[] newArray(int n) {
            return new WifiP2pDeviceList[n];
        }
    };
    private final HashMap<String, WifiP2pDevice> mDevices = new HashMap();

    public WifiP2pDeviceList() {
    }

    public WifiP2pDeviceList(WifiP2pDeviceList parcelable2) {
        if (parcelable2 != null) {
            for (WifiP2pDevice wifiP2pDevice : ((WifiP2pDeviceList)parcelable2).getDeviceList()) {
                this.mDevices.put(wifiP2pDevice.deviceAddress, new WifiP2pDevice(wifiP2pDevice));
            }
        }
    }

    public WifiP2pDeviceList(ArrayList<WifiP2pDevice> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            WifiP2pDevice wifiP2pDevice = (WifiP2pDevice)object.next();
            if (wifiP2pDevice.deviceAddress == null) continue;
            this.mDevices.put(wifiP2pDevice.deviceAddress, new WifiP2pDevice(wifiP2pDevice));
        }
    }

    private void validateDevice(WifiP2pDevice wifiP2pDevice) {
        if (wifiP2pDevice != null) {
            if (!TextUtils.isEmpty(wifiP2pDevice.deviceAddress)) {
                return;
            }
            throw new IllegalArgumentException("Empty deviceAddress");
        }
        throw new IllegalArgumentException("Null device");
    }

    private void validateDeviceAddress(String string2) {
        if (!TextUtils.isEmpty(string2)) {
            return;
        }
        throw new IllegalArgumentException("Empty deviceAddress");
    }

    public boolean clear() {
        if (this.mDevices.isEmpty()) {
            return false;
        }
        this.mDevices.clear();
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public WifiP2pDevice get(String string2) {
        this.validateDeviceAddress(string2);
        return this.mDevices.get(string2);
    }

    public Collection<WifiP2pDevice> getDeviceList() {
        return Collections.unmodifiableCollection(this.mDevices.values());
    }

    public boolean isGroupOwner(String string2) {
        this.validateDeviceAddress(string2);
        Object object = this.mDevices.get(string2);
        if (object != null) {
            return ((WifiP2pDevice)object).isGroupOwner();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Device not found ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public WifiP2pDevice remove(String string2) {
        this.validateDeviceAddress(string2);
        return this.mDevices.remove(string2);
    }

    public boolean remove(WifiP2pDevice wifiP2pDevice) {
        this.validateDevice(wifiP2pDevice);
        boolean bl = this.mDevices.remove(wifiP2pDevice.deviceAddress) != null;
        return bl;
    }

    public boolean remove(WifiP2pDeviceList object) {
        boolean bl = false;
        object = ((WifiP2pDeviceList)object).mDevices.values().iterator();
        while (object.hasNext()) {
            if (!this.remove((WifiP2pDevice)object.next())) continue;
            bl = true;
        }
        return bl;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (WifiP2pDevice wifiP2pDevice : this.mDevices.values()) {
            stringBuffer.append("\n");
            stringBuffer.append(wifiP2pDevice);
        }
        return stringBuffer.toString();
    }

    @UnsupportedAppUsage
    public void update(WifiP2pDevice wifiP2pDevice) {
        this.updateSupplicantDetails(wifiP2pDevice);
        this.mDevices.get((Object)wifiP2pDevice.deviceAddress).status = wifiP2pDevice.status;
    }

    public void updateGroupCapability(String object, int n) {
        this.validateDeviceAddress((String)object);
        object = this.mDevices.get(object);
        if (object != null) {
            ((WifiP2pDevice)object).groupCapability = n;
        }
    }

    public void updateStatus(String object, int n) {
        this.validateDeviceAddress((String)object);
        object = this.mDevices.get(object);
        if (object != null) {
            ((WifiP2pDevice)object).status = n;
        }
    }

    public void updateSupplicantDetails(WifiP2pDevice wifiP2pDevice) {
        this.validateDevice(wifiP2pDevice);
        WifiP2pDevice wifiP2pDevice2 = this.mDevices.get(wifiP2pDevice.deviceAddress);
        if (wifiP2pDevice2 != null) {
            wifiP2pDevice2.deviceName = wifiP2pDevice.deviceName;
            wifiP2pDevice2.primaryDeviceType = wifiP2pDevice.primaryDeviceType;
            wifiP2pDevice2.secondaryDeviceType = wifiP2pDevice.secondaryDeviceType;
            wifiP2pDevice2.wpsConfigMethodsSupported = wifiP2pDevice.wpsConfigMethodsSupported;
            wifiP2pDevice2.deviceCapability = wifiP2pDevice.deviceCapability;
            wifiP2pDevice2.groupCapability = wifiP2pDevice.groupCapability;
            wifiP2pDevice2.wfdInfo = wifiP2pDevice.wfdInfo;
            return;
        }
        this.mDevices.put(wifiP2pDevice.deviceAddress, wifiP2pDevice);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mDevices.size());
        Iterator<WifiP2pDevice> iterator = this.mDevices.values().iterator();
        while (iterator.hasNext()) {
            parcel.writeParcelable(iterator.next(), n);
        }
    }

}

