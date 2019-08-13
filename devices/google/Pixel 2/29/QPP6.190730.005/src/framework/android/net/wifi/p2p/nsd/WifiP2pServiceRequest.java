/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p.nsd;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Locale;

public class WifiP2pServiceRequest
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<WifiP2pServiceRequest> CREATOR = new Parcelable.Creator<WifiP2pServiceRequest>(){

        @Override
        public WifiP2pServiceRequest createFromParcel(Parcel parcel) {
            return new WifiP2pServiceRequest(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString());
        }

        public WifiP2pServiceRequest[] newArray(int n) {
            return new WifiP2pServiceRequest[n];
        }
    };
    private int mLength;
    private int mProtocolType;
    private String mQuery;
    private int mTransId;

    private WifiP2pServiceRequest(int n, int n2, int n3, String string2) {
        this.mProtocolType = n;
        this.mLength = n2;
        this.mTransId = n3;
        this.mQuery = string2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    protected WifiP2pServiceRequest(int n, String string2) {
        this.validateQuery(string2);
        this.mProtocolType = n;
        this.mQuery = string2;
        this.mLength = string2 != null ? string2.length() / 2 + 2 : 2;
    }

    public static WifiP2pServiceRequest newInstance(int n) {
        return new WifiP2pServiceRequest(n, null);
    }

    public static WifiP2pServiceRequest newInstance(int n, String string2) {
        return new WifiP2pServiceRequest(n, string2);
    }

    private void validateQuery(String string2) {
        if (string2 == null) {
            return;
        }
        if (string2.length() % 2 != 1) {
            if (string2.length() / 2 <= 65535) {
                string2 = string2.toLowerCase(Locale.ROOT);
                Object object = string2.toCharArray();
                int n = ((char[])object).length;
                for (int i = 0; i < n; ++i) {
                    char c = object[i];
                    if (c >= '0' && c <= '9' || c >= 'a' && c <= 'f') {
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("query should be hex string. query=");
                    ((StringBuilder)object).append(string2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("query size is too large. len=");
            stringBuilder.append(string2.length());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("query size is invalid. query=");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof WifiP2pServiceRequest)) {
            return false;
        }
        object = (WifiP2pServiceRequest)object;
        if (((WifiP2pServiceRequest)object).mProtocolType == this.mProtocolType && ((WifiP2pServiceRequest)object).mLength == this.mLength) {
            if (((WifiP2pServiceRequest)object).mQuery == null && this.mQuery == null) {
                return true;
            }
            object = ((WifiP2pServiceRequest)object).mQuery;
            if (object != null) {
                return ((String)object).equals(this.mQuery);
            }
            return false;
        }
        return false;
    }

    public String getSupplicantQuery() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format(Locale.US, "%02x", this.mLength & 255));
        stringBuffer.append(String.format(Locale.US, "%02x", this.mLength >> 8 & 255));
        stringBuffer.append(String.format(Locale.US, "%02x", this.mProtocolType));
        stringBuffer.append(String.format(Locale.US, "%02x", this.mTransId));
        String string2 = this.mQuery;
        if (string2 != null) {
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }

    public int getTransactionId() {
        return this.mTransId;
    }

    public int hashCode() {
        int n = this.mProtocolType;
        int n2 = this.mLength;
        String string2 = this.mQuery;
        int n3 = string2 == null ? 0 : string2.hashCode();
        return ((17 * 31 + n) * 31 + n2) * 31 + n3;
    }

    public void setTransactionId(int n) {
        this.mTransId = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mProtocolType);
        parcel.writeInt(this.mLength);
        parcel.writeInt(this.mTransId);
        parcel.writeString(this.mQuery);
    }

}

