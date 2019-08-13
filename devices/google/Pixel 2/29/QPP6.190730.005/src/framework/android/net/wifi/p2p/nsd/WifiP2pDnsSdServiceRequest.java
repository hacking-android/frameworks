/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;

public class WifiP2pDnsSdServiceRequest
extends WifiP2pServiceRequest {
    private WifiP2pDnsSdServiceRequest() {
        super(1, null);
    }

    private WifiP2pDnsSdServiceRequest(String string2) {
        super(1, string2);
    }

    private WifiP2pDnsSdServiceRequest(String string2, int n, int n2) {
        super(1, WifiP2pDnsSdServiceInfo.createRequest(string2, n, n2));
    }

    public static WifiP2pDnsSdServiceRequest newInstance() {
        return new WifiP2pDnsSdServiceRequest();
    }

    public static WifiP2pDnsSdServiceRequest newInstance(String string2) {
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(".local.");
            return new WifiP2pDnsSdServiceRequest(stringBuilder.toString(), 12, 1);
        }
        throw new IllegalArgumentException("service type cannot be null");
    }

    public static WifiP2pDnsSdServiceRequest newInstance(String string2, String string3) {
        if (string2 != null && string3 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(".");
            stringBuilder.append(string3);
            stringBuilder.append(".local.");
            return new WifiP2pDnsSdServiceRequest(stringBuilder.toString(), 16, 1);
        }
        throw new IllegalArgumentException("instance name or service type cannot be null");
    }
}

