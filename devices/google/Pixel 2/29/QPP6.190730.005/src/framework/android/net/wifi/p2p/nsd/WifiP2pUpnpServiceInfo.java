/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class WifiP2pUpnpServiceInfo
extends WifiP2pServiceInfo {
    public static final int VERSION_1_0 = 16;

    private WifiP2pUpnpServiceInfo(List<String> list) {
        super(list);
    }

    private static String createSupplicantQuery(String string2, String string3) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("upnp ");
        stringBuffer.append(String.format(Locale.US, "%02x ", 16));
        stringBuffer.append("uuid:");
        stringBuffer.append(string2);
        if (string3 != null) {
            stringBuffer.append("::");
            stringBuffer.append(string3);
        }
        return stringBuffer.toString();
    }

    public static WifiP2pUpnpServiceInfo newInstance(String string2, String object, List<String> list) {
        if (string2 != null && object != null) {
            UUID.fromString(string2);
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(WifiP2pUpnpServiceInfo.createSupplicantQuery(string2, null));
            arrayList.add(WifiP2pUpnpServiceInfo.createSupplicantQuery(string2, "upnp:rootdevice"));
            arrayList.add(WifiP2pUpnpServiceInfo.createSupplicantQuery(string2, (String)object));
            if (list != null) {
                object = list.iterator();
                while (object.hasNext()) {
                    arrayList.add(WifiP2pUpnpServiceInfo.createSupplicantQuery(string2, (String)object.next()));
                }
            }
            return new WifiP2pUpnpServiceInfo(arrayList);
        }
        throw new IllegalArgumentException("uuid or device cannnot be null");
    }
}

