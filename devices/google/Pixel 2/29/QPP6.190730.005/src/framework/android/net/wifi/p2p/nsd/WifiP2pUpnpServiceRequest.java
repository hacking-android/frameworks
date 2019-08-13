/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;
import java.util.Locale;

public class WifiP2pUpnpServiceRequest
extends WifiP2pServiceRequest {
    protected WifiP2pUpnpServiceRequest() {
        super(2, null);
    }

    protected WifiP2pUpnpServiceRequest(String string2) {
        super(2, string2);
    }

    public static WifiP2pUpnpServiceRequest newInstance() {
        return new WifiP2pUpnpServiceRequest();
    }

    public static WifiP2pUpnpServiceRequest newInstance(String string2) {
        if (string2 != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.format(Locale.US, "%02x", 16));
            stringBuffer.append(WifiP2pServiceInfo.bin2HexStr(string2.getBytes()));
            return new WifiP2pUpnpServiceRequest(stringBuffer.toString());
        }
        throw new IllegalArgumentException("search target cannot be null");
    }
}

