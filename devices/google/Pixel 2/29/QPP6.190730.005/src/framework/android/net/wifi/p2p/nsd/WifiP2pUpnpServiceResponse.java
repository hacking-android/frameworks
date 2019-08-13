/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.nsd.WifiP2pServiceResponse;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WifiP2pUpnpServiceResponse
extends WifiP2pServiceResponse {
    private List<String> mUniqueServiceNames;
    private int mVersion;

    protected WifiP2pUpnpServiceResponse(int n, int n2, WifiP2pDevice wifiP2pDevice, byte[] arrby) {
        super(2, n, n2, wifiP2pDevice, arrby);
        if (this.parse()) {
            return;
        }
        throw new IllegalArgumentException("Malformed upnp service response");
    }

    static WifiP2pUpnpServiceResponse newInstance(int n, int n2, WifiP2pDevice parcelable, byte[] arrby) {
        if (n != 0) {
            return new WifiP2pUpnpServiceResponse(n, n2, (WifiP2pDevice)parcelable, null);
        }
        try {
            parcelable = new WifiP2pUpnpServiceResponse(n, n2, (WifiP2pDevice)parcelable, arrby);
            return parcelable;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            return null;
        }
    }

    private boolean parse() {
        if (this.mData == null) {
            return true;
        }
        int n = this.mData.length;
        if (n < 1) {
            return false;
        }
        this.mVersion = this.mData[0] & 255;
        String[] arrstring = new String(this.mData, 1, this.mData.length - 1).split(",");
        this.mUniqueServiceNames = new ArrayList<String>();
        for (String string2 : arrstring) {
            this.mUniqueServiceNames.add(string2);
        }
        return true;
    }

    public List<String> getUniqueServiceNames() {
        return this.mUniqueServiceNames;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("serviceType:UPnP(");
        stringBuffer.append(this.mServiceType);
        stringBuffer.append(")");
        stringBuffer.append(" status:");
        stringBuffer.append(WifiP2pServiceResponse.Status.toString(this.mStatus));
        stringBuffer.append(" srcAddr:");
        stringBuffer.append(this.mDevice.deviceAddress);
        stringBuffer.append(" version:");
        stringBuffer.append(String.format("%02x", this.mVersion));
        Object object = this.mUniqueServiceNames;
        if (object != null) {
            Iterator<String> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                stringBuffer.append(" usn:");
                stringBuffer.append((String)object);
            }
        }
        return stringBuffer.toString();
    }
}

