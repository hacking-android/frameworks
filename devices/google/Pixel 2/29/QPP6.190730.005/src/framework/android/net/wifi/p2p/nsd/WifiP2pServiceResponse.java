/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceResponse;
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceResponse;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WifiP2pServiceResponse
implements Parcelable {
    public static final Parcelable.Creator<WifiP2pServiceResponse> CREATOR;
    private static int MAX_BUF_SIZE;
    protected byte[] mData;
    protected WifiP2pDevice mDevice;
    protected int mServiceType;
    protected int mStatus;
    protected int mTransId;

    static {
        MAX_BUF_SIZE = 1024;
        CREATOR = new Parcelable.Creator<WifiP2pServiceResponse>(){

            @Override
            public WifiP2pServiceResponse createFromParcel(Parcel arrby) {
                int n = arrby.readInt();
                int n2 = arrby.readInt();
                int n3 = arrby.readInt();
                WifiP2pDevice wifiP2pDevice = (WifiP2pDevice)arrby.readParcelable(null);
                int n4 = arrby.readInt();
                if (n4 > 0) {
                    byte[] arrby2 = new byte[n4];
                    arrby.readByteArray(arrby2);
                    arrby = arrby2;
                } else {
                    arrby = null;
                }
                if (n == 1) {
                    return WifiP2pDnsSdServiceResponse.newInstance(n2, n3, wifiP2pDevice, arrby);
                }
                if (n == 2) {
                    return WifiP2pUpnpServiceResponse.newInstance(n2, n3, wifiP2pDevice, arrby);
                }
                return new WifiP2pServiceResponse(n, n2, n3, wifiP2pDevice, arrby);
            }

            public WifiP2pServiceResponse[] newArray(int n) {
                return new WifiP2pServiceResponse[n];
            }
        };
    }

    protected WifiP2pServiceResponse(int n, int n2, int n3, WifiP2pDevice wifiP2pDevice, byte[] arrby) {
        this.mServiceType = n;
        this.mStatus = n2;
        this.mTransId = n3;
        this.mDevice = wifiP2pDevice;
        this.mData = arrby;
    }

    private boolean equals(Object object, Object object2) {
        if (object == null && object2 == null) {
            return true;
        }
        if (object != null) {
            return object.equals(object2);
        }
        return false;
    }

    private static byte[] hexStr2Bin(String string2) {
        int n = string2.length() / 2;
        byte[] arrby = new byte[string2.length() / 2];
        for (int i = 0; i < n; ++i) {
            try {
                arrby[i] = (byte)Integer.parseInt(string2.substring(i * 2, i * 2 + 2), 16);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<WifiP2pServiceResponse> newInstance(String object, byte[] object2) {
        ArrayList<WifiP2pServiceResponse> arrayList = new ArrayList<WifiP2pServiceResponse>();
        WifiP2pDevice wifiP2pDevice = new WifiP2pDevice();
        wifiP2pDevice.deviceAddress = object;
        if (object2 == null) {
            return null;
        }
        object2 = new DataInputStream(new ByteArrayInputStream((byte[])object2));
        try {
            do {
                if (((FilterInputStream)object2).available() <= 0) {
                    return arrayList;
                }
                int n = ((DataInputStream)object2).readUnsignedByte() + (((DataInputStream)object2).readUnsignedByte() << 8) - 3;
                int n2 = ((DataInputStream)object2).readUnsignedByte();
                int n3 = ((DataInputStream)object2).readUnsignedByte();
                int n4 = ((DataInputStream)object2).readUnsignedByte();
                if (n < 0) {
                    return null;
                }
                if (n == 0) {
                    if (n4 != 0) continue;
                    object = new WifiP2pServiceResponse(n2, n4, n3, wifiP2pDevice, null);
                    arrayList.add((WifiP2pServiceResponse)object);
                    continue;
                }
                if (n > MAX_BUF_SIZE) {
                    ((FilterInputStream)object2).skip(n);
                    continue;
                }
                object = new byte[n];
                ((DataInputStream)object2).readFully((byte[])object);
                object = n2 == 1 ? WifiP2pDnsSdServiceResponse.newInstance(n4, n3, wifiP2pDevice, (byte[])object) : (n2 == 2 ? WifiP2pUpnpServiceResponse.newInstance(n4, n3, wifiP2pDevice, (byte[])object) : new WifiP2pServiceResponse(n2, n4, n3, wifiP2pDevice, (byte[])object));
                if (object == null || ((WifiP2pServiceResponse)object).getStatus() != 0) continue;
                arrayList.add((WifiP2pServiceResponse)object);
            } while (true);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            if (arrayList.size() > 0) {
                return arrayList;
            }
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof WifiP2pServiceResponse)) {
            return false;
        }
        object = (WifiP2pServiceResponse)object;
        if (((WifiP2pServiceResponse)object).mServiceType != this.mServiceType || ((WifiP2pServiceResponse)object).mStatus != this.mStatus || !this.equals(object.mDevice.deviceAddress, this.mDevice.deviceAddress) || !Arrays.equals(((WifiP2pServiceResponse)object).mData, this.mData)) {
            bl = false;
        }
        return bl;
    }

    public byte[] getRawData() {
        return this.mData;
    }

    public int getServiceType() {
        return this.mServiceType;
    }

    public WifiP2pDevice getSrcDevice() {
        return this.mDevice;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getTransactionId() {
        return this.mTransId;
    }

    public int hashCode() {
        int n = this.mServiceType;
        int n2 = this.mStatus;
        int n3 = this.mTransId;
        byte[] arrby = this.mDevice.deviceAddress;
        int n4 = 0;
        int n5 = arrby == null ? 0 : this.mDevice.deviceAddress.hashCode();
        arrby = this.mData;
        if (arrby != null) {
            n4 = Arrays.hashCode(arrby);
        }
        return ((((17 * 31 + n) * 31 + n2) * 31 + n3) * 31 + n5) * 31 + n4;
    }

    public void setSrcDevice(WifiP2pDevice wifiP2pDevice) {
        if (wifiP2pDevice == null) {
            return;
        }
        this.mDevice = wifiP2pDevice;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("serviceType:");
        stringBuffer.append(this.mServiceType);
        stringBuffer.append(" status:");
        stringBuffer.append(Status.toString(this.mStatus));
        stringBuffer.append(" srcAddr:");
        stringBuffer.append(this.mDevice.deviceAddress);
        stringBuffer.append(" data:");
        stringBuffer.append(Arrays.toString(this.mData));
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mServiceType);
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mTransId);
        parcel.writeParcelable(this.mDevice, n);
        byte[] arrby = this.mData;
        if (arrby != null && arrby.length != 0) {
            parcel.writeInt(arrby.length);
            parcel.writeByteArray(this.mData);
        } else {
            parcel.writeInt(0);
        }
    }

    public static class Status {
        public static final int BAD_REQUEST = 3;
        public static final int REQUESTED_INFORMATION_NOT_AVAILABLE = 2;
        public static final int SERVICE_PROTOCOL_NOT_AVAILABLE = 1;
        public static final int SUCCESS = 0;

        private Status() {
        }

        public static String toString(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return "UNKNOWN";
                        }
                        return "BAD_REQUEST";
                    }
                    return "REQUESTED_INFORMATION_NOT_AVAILABLE";
                }
                return "SERVICE_PROTOCOL_NOT_AVAILABLE";
            }
            return "SUCCESS";
        }
    }

}

