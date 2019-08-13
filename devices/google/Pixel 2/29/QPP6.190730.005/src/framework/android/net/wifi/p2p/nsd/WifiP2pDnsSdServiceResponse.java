/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.nsd.WifiP2pServiceResponse;
import android.os.Parcelable;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WifiP2pDnsSdServiceResponse
extends WifiP2pServiceResponse {
    private static final Map<Integer, String> sVmpack = new HashMap<Integer, String>();
    private String mDnsQueryName;
    private int mDnsType;
    private String mInstanceName;
    private final HashMap<String, String> mTxtRecord = new HashMap();
    private int mVersion;

    static {
        sVmpack.put(12, "_tcp.local.");
        sVmpack.put(17, "local.");
        sVmpack.put(28, "_udp.local.");
    }

    protected WifiP2pDnsSdServiceResponse(int n, int n2, WifiP2pDevice wifiP2pDevice, byte[] arrby) {
        super(1, n, n2, wifiP2pDevice, arrby);
        if (this.parse()) {
            return;
        }
        throw new IllegalArgumentException("Malformed bonjour service response");
    }

    static WifiP2pDnsSdServiceResponse newInstance(int n, int n2, WifiP2pDevice parcelable, byte[] arrby) {
        if (n != 0) {
            return new WifiP2pDnsSdServiceResponse(n, n2, (WifiP2pDevice)parcelable, null);
        }
        try {
            parcelable = new WifiP2pDnsSdServiceResponse(n, n2, (WifiP2pDevice)parcelable, arrby);
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
        Object object = new DataInputStream(new ByteArrayInputStream(this.mData));
        this.mDnsQueryName = this.readDnsName((DataInputStream)object);
        if (this.mDnsQueryName == null) {
            return false;
        }
        try {
            this.mDnsType = ((DataInputStream)object).readUnsignedShort();
            this.mVersion = ((DataInputStream)object).readUnsignedByte();
            int n = this.mDnsType;
            if (n == 12) {
                if ((object = this.readDnsName((DataInputStream)object)) == null) {
                    return false;
                }
                if (((String)object).length() <= this.mDnsQueryName.length()) {
                    return false;
                }
                this.mInstanceName = ((String)object).substring(0, ((String)object).length() - this.mDnsQueryName.length() - 1);
                return true;
            }
            if (n == 16) {
                return this.readTxtData((DataInputStream)object);
            }
            return false;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String readDnsName(DataInputStream object) {
        StringBuffer stringBuffer = new StringBuffer();
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>(sVmpack);
        if (this.mDnsQueryName != null) {
            hashMap.put(39, this.mDnsQueryName);
        }
        try {
            do {
                int n;
                if ((n = ((DataInputStream)object).readUnsignedByte()) == 0) {
                    return stringBuffer.toString();
                }
                if (n == 192) {
                    if ((object = hashMap.get(((DataInputStream)object).readUnsignedByte())) == null) {
                        return null;
                    }
                    stringBuffer.append((String)object);
                    return stringBuffer.toString();
                }
                byte[] arrby = new byte[n];
                ((DataInputStream)object).readFully(arrby);
                String string2 = new String(arrby);
                stringBuffer.append(string2);
                stringBuffer.append(".");
            } while (true);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean readTxtData(DataInputStream dataInputStream) {
        try {
            while (dataInputStream.available() > 0) {
                int n = dataInputStream.readUnsignedByte();
                if (n == 0) {
                    return true;
                }
                byte[] arrby = new byte[n];
                dataInputStream.readFully(arrby);
                String[] arrstring = new String(arrby);
                arrstring = arrstring.split("=");
                if (arrstring.length != 2) {
                    return false;
                }
                this.mTxtRecord.put(arrstring[0], arrstring[1]);
            }
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public String getDnsQueryName() {
        return this.mDnsQueryName;
    }

    public int getDnsType() {
        return this.mDnsType;
    }

    public String getInstanceName() {
        return this.mInstanceName;
    }

    public Map<String, String> getTxtRecord() {
        return this.mTxtRecord;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("serviceType:DnsSd(");
        stringBuffer.append(this.mServiceType);
        stringBuffer.append(")");
        stringBuffer.append(" status:");
        stringBuffer.append(WifiP2pServiceResponse.Status.toString(this.mStatus));
        stringBuffer.append(" srcAddr:");
        stringBuffer.append(this.mDevice.deviceAddress);
        stringBuffer.append(" version:");
        stringBuffer.append(String.format("%02x", this.mVersion));
        stringBuffer.append(" dnsName:");
        stringBuffer.append(this.mDnsQueryName);
        stringBuffer.append(" TxtRecord:");
        for (String string2 : this.mTxtRecord.keySet()) {
            stringBuffer.append(" key:");
            stringBuffer.append(string2);
            stringBuffer.append(" value:");
            stringBuffer.append(this.mTxtRecord.get(string2));
        }
        if (this.mInstanceName != null) {
            stringBuffer.append(" InsName:");
            stringBuffer.append(this.mInstanceName);
        }
        return stringBuffer.toString();
    }
}

