/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.annotation.UnsupportedAppUsage;
import android.net.nsd.DnsSdTxtRecord;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class WifiP2pDnsSdServiceInfo
extends WifiP2pServiceInfo {
    public static final int DNS_TYPE_PTR = 12;
    public static final int DNS_TYPE_TXT = 16;
    public static final int VERSION_1 = 1;
    private static final Map<String, String> sVmPacket = new HashMap<String, String>();

    static {
        sVmPacket.put("_tcp.local.", "c00c");
        sVmPacket.put("local.", "c011");
        sVmPacket.put("_udp.local.", "c01c");
    }

    private WifiP2pDnsSdServiceInfo(List<String> list) {
        super(list);
    }

    private static String compressDnsName(String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        do {
            String string3;
            int n;
            block7 : {
                block6 : {
                    block5 : {
                        if ((string3 = sVmPacket.get(string2)) == null) break block5;
                        stringBuffer.append(string3);
                        break block6;
                    }
                    n = string2.indexOf(46);
                    if (n != -1) break block7;
                    if (string2.length() > 0) {
                        stringBuffer.append(String.format(Locale.US, "%02x", string2.length()));
                        stringBuffer.append(WifiP2pServiceInfo.bin2HexStr(string2.getBytes()));
                    }
                    stringBuffer.append("00");
                }
                return stringBuffer.toString();
            }
            string3 = string2.substring(0, n);
            string2 = string2.substring(n + 1);
            stringBuffer.append(String.format(Locale.US, "%02x", string3.length()));
            stringBuffer.append(WifiP2pServiceInfo.bin2HexStr(string3.getBytes()));
        } while (true);
    }

    private static String createPtrServiceQuery(String arrby, String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("bonjour ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".local.");
        stringBuffer.append(WifiP2pDnsSdServiceInfo.createRequest(stringBuilder.toString(), 12, 1));
        stringBuffer.append(" ");
        arrby = arrby.getBytes();
        stringBuffer.append(String.format(Locale.US, "%02x", arrby.length));
        stringBuffer.append(WifiP2pServiceInfo.bin2HexStr(arrby));
        stringBuffer.append("c027");
        return stringBuffer.toString();
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    static String createRequest(String string2, int n, int n2) {
        StringBuffer stringBuffer = new StringBuffer();
        String string3 = string2;
        if (n == 16) {
            string3 = string2.toLowerCase(Locale.ROOT);
        }
        stringBuffer.append(WifiP2pDnsSdServiceInfo.compressDnsName(string3));
        stringBuffer.append(String.format(Locale.US, "%04x", n));
        stringBuffer.append(String.format(Locale.US, "%02x", n2));
        return stringBuffer.toString();
    }

    private static String createTxtServiceQuery(String arrby, String string2, DnsSdTxtRecord dnsSdTxtRecord) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("bonjour ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)arrby);
        stringBuilder.append(".");
        stringBuilder.append(string2);
        stringBuilder.append(".local.");
        stringBuffer.append(WifiP2pDnsSdServiceInfo.createRequest(stringBuilder.toString(), 16, 1));
        stringBuffer.append(" ");
        arrby = dnsSdTxtRecord.getRawData();
        if (arrby.length == 0) {
            stringBuffer.append("00");
        } else {
            stringBuffer.append(WifiP2pDnsSdServiceInfo.bin2HexStr(arrby));
        }
        return stringBuffer.toString();
    }

    public static WifiP2pDnsSdServiceInfo newInstance(String string2, String string3, Map<String, String> object) {
        if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
            DnsSdTxtRecord dnsSdTxtRecord = new DnsSdTxtRecord();
            if (object != null) {
                for (String string4 : object.keySet()) {
                    dnsSdTxtRecord.set(string4, (String)object.get(string4));
                }
            }
            object = new ArrayList();
            ((ArrayList)object).add(WifiP2pDnsSdServiceInfo.createPtrServiceQuery(string2, string3));
            ((ArrayList)object).add(WifiP2pDnsSdServiceInfo.createTxtServiceQuery(string2, string3, dnsSdTxtRecord));
            return new WifiP2pDnsSdServiceInfo((List<String>)object);
        }
        throw new IllegalArgumentException("instance name or service type cannot be empty");
    }
}

