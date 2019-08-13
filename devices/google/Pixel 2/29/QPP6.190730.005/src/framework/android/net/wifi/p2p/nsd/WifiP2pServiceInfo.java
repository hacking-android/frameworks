/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class WifiP2pServiceInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiP2pServiceInfo> CREATOR = new Parcelable.Creator<WifiP2pServiceInfo>(){

        @Override
        public WifiP2pServiceInfo createFromParcel(Parcel parcel) {
            ArrayList<String> arrayList = new ArrayList<String>();
            parcel.readStringList(arrayList);
            return new WifiP2pServiceInfo(arrayList);
        }

        public WifiP2pServiceInfo[] newArray(int n) {
            return new WifiP2pServiceInfo[n];
        }
    };
    public static final int SERVICE_TYPE_ALL = 0;
    public static final int SERVICE_TYPE_BONJOUR = 1;
    public static final int SERVICE_TYPE_UPNP = 2;
    public static final int SERVICE_TYPE_VENDOR_SPECIFIC = 255;
    public static final int SERVICE_TYPE_WS_DISCOVERY = 3;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private List<String> mQueryList;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    protected WifiP2pServiceInfo(List<String> list) {
        if (list != null) {
            this.mQueryList = list;
            return;
        }
        throw new IllegalArgumentException("query list cannot be null");
    }

    static String bin2HexStr(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte by : arrby) {
            String string2;
            block3 : {
                try {
                    string2 = Integer.toHexString(by & 255);
                    if (string2.length() != 1) break block3;
                    stringBuffer.append('0');
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof WifiP2pServiceInfo)) {
            return false;
        }
        object = (WifiP2pServiceInfo)object;
        return this.mQueryList.equals(((WifiP2pServiceInfo)object).mQueryList);
    }

    public List<String> getSupplicantQueryList() {
        return this.mQueryList;
    }

    public int hashCode() {
        List<String> list = this.mQueryList;
        int n = list == null ? 0 : list.hashCode();
        return 17 * 31 + n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStringList(this.mQueryList);
    }

}

