/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.annotation.SystemApi;
import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SystemApi
public final class DataCallResponse
implements Parcelable {
    public static final Parcelable.Creator<DataCallResponse> CREATOR = new Parcelable.Creator<DataCallResponse>(){

        @Override
        public DataCallResponse createFromParcel(Parcel parcel) {
            return new DataCallResponse(parcel);
        }

        public DataCallResponse[] newArray(int n) {
            return new DataCallResponse[n];
        }
    };
    public static final int LINK_STATUS_ACTIVE = 2;
    public static final int LINK_STATUS_DORMANT = 1;
    public static final int LINK_STATUS_INACTIVE = 0;
    public static final int LINK_STATUS_UNKNOWN = -1;
    private final List<LinkAddress> mAddresses;
    private final int mCause;
    private final List<InetAddress> mDnsAddresses;
    private final List<InetAddress> mGatewayAddresses;
    private final int mId;
    private final String mInterfaceName;
    private final int mLinkStatus;
    private final int mMtu;
    private final List<InetAddress> mPcscfAddresses;
    private final int mProtocolType;
    private final int mSuggestedRetryTime;

    public DataCallResponse(int n, int n2, int n3, int n4, int n5, String arrayList, List<LinkAddress> list, List<InetAddress> list2, List<InetAddress> list3, List<InetAddress> list4, int n6) {
        this.mCause = n;
        this.mSuggestedRetryTime = n2;
        this.mId = n3;
        this.mLinkStatus = n4;
        this.mProtocolType = n5;
        if (arrayList == null) {
            arrayList = "";
        }
        this.mInterfaceName = arrayList;
        arrayList = list == null ? new ArrayList() : new ArrayList<LinkAddress>(list);
        this.mAddresses = arrayList;
        arrayList = list2 == null ? new ArrayList() : new ArrayList<InetAddress>(list2);
        this.mDnsAddresses = arrayList;
        arrayList = list3 == null ? new ArrayList() : new ArrayList<InetAddress>(list3);
        this.mGatewayAddresses = arrayList;
        arrayList = list4 == null ? new ArrayList() : new ArrayList<InetAddress>(list4);
        this.mPcscfAddresses = arrayList;
        this.mMtu = n6;
    }

    @VisibleForTesting
    public DataCallResponse(Parcel parcel) {
        this.mCause = parcel.readInt();
        this.mSuggestedRetryTime = parcel.readInt();
        this.mId = parcel.readInt();
        this.mLinkStatus = parcel.readInt();
        this.mProtocolType = parcel.readInt();
        this.mInterfaceName = parcel.readString();
        this.mAddresses = new ArrayList<LinkAddress>();
        parcel.readList(this.mAddresses, LinkAddress.class.getClassLoader());
        this.mDnsAddresses = new ArrayList<InetAddress>();
        parcel.readList(this.mDnsAddresses, InetAddress.class.getClassLoader());
        this.mGatewayAddresses = new ArrayList<InetAddress>();
        parcel.readList(this.mGatewayAddresses, InetAddress.class.getClassLoader());
        this.mPcscfAddresses = new ArrayList<InetAddress>();
        parcel.readList(this.mPcscfAddresses, InetAddress.class.getClassLoader());
        this.mMtu = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof DataCallResponse)) {
            return false;
        }
        object = (DataCallResponse)object;
        if (!(this.mCause == ((DataCallResponse)object).mCause && this.mSuggestedRetryTime == ((DataCallResponse)object).mSuggestedRetryTime && this.mId == ((DataCallResponse)object).mId && this.mLinkStatus == ((DataCallResponse)object).mLinkStatus && this.mProtocolType == ((DataCallResponse)object).mProtocolType && this.mInterfaceName.equals(((DataCallResponse)object).mInterfaceName) && this.mAddresses.size() == ((DataCallResponse)object).mAddresses.size() && this.mAddresses.containsAll(((DataCallResponse)object).mAddresses) && this.mDnsAddresses.size() == ((DataCallResponse)object).mDnsAddresses.size() && this.mDnsAddresses.containsAll(((DataCallResponse)object).mDnsAddresses) && this.mGatewayAddresses.size() == ((DataCallResponse)object).mGatewayAddresses.size() && this.mGatewayAddresses.containsAll(((DataCallResponse)object).mGatewayAddresses) && this.mPcscfAddresses.size() == ((DataCallResponse)object).mPcscfAddresses.size() && this.mPcscfAddresses.containsAll(((DataCallResponse)object).mPcscfAddresses) && this.mMtu == ((DataCallResponse)object).mMtu)) {
            bl = false;
        }
        return bl;
    }

    public List<LinkAddress> getAddresses() {
        return this.mAddresses;
    }

    public int getCause() {
        return this.mCause;
    }

    public List<InetAddress> getDnsAddresses() {
        return this.mDnsAddresses;
    }

    public List<InetAddress> getGatewayAddresses() {
        return this.mGatewayAddresses;
    }

    public int getId() {
        return this.mId;
    }

    public String getInterfaceName() {
        return this.mInterfaceName;
    }

    public int getLinkStatus() {
        return this.mLinkStatus;
    }

    public int getMtu() {
        return this.mMtu;
    }

    public List<InetAddress> getPcscfAddresses() {
        return this.mPcscfAddresses;
    }

    public int getProtocolType() {
        return this.mProtocolType;
    }

    public int getSuggestedRetryTime() {
        return this.mSuggestedRetryTime;
    }

    public int hashCode() {
        return Objects.hash(this.mCause, this.mSuggestedRetryTime, this.mId, this.mLinkStatus, this.mProtocolType, this.mInterfaceName, this.mAddresses, this.mDnsAddresses, this.mGatewayAddresses, this.mPcscfAddresses, this.mMtu);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DataCallResponse: {");
        stringBuffer.append(" cause=");
        stringBuffer.append(this.mCause);
        stringBuffer.append(" retry=");
        stringBuffer.append(this.mSuggestedRetryTime);
        stringBuffer.append(" cid=");
        stringBuffer.append(this.mId);
        stringBuffer.append(" linkStatus=");
        stringBuffer.append(this.mLinkStatus);
        stringBuffer.append(" protocolType=");
        stringBuffer.append(this.mProtocolType);
        stringBuffer.append(" ifname=");
        stringBuffer.append(this.mInterfaceName);
        stringBuffer.append(" addresses=");
        stringBuffer.append(this.mAddresses);
        stringBuffer.append(" dnses=");
        stringBuffer.append(this.mDnsAddresses);
        stringBuffer.append(" gateways=");
        stringBuffer.append(this.mGatewayAddresses);
        stringBuffer.append(" pcscf=");
        stringBuffer.append(this.mPcscfAddresses);
        stringBuffer.append(" mtu=");
        stringBuffer.append(this.mMtu);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCause);
        parcel.writeInt(this.mSuggestedRetryTime);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mLinkStatus);
        parcel.writeInt(this.mProtocolType);
        parcel.writeString(this.mInterfaceName);
        parcel.writeList(this.mAddresses);
        parcel.writeList(this.mDnsAddresses);
        parcel.writeList(this.mGatewayAddresses);
        parcel.writeList(this.mPcscfAddresses);
        parcel.writeInt(this.mMtu);
    }

    public static final class Builder {
        private List<LinkAddress> mAddresses;
        private int mCause;
        private List<InetAddress> mDnsAddresses;
        private List<InetAddress> mGatewayAddresses;
        private int mId;
        private String mInterfaceName;
        private int mLinkStatus;
        private int mMtu;
        private List<InetAddress> mPcscfAddresses;
        private int mProtocolType;
        private int mSuggestedRetryTime;

        public DataCallResponse build() {
            return new DataCallResponse(this.mCause, this.mSuggestedRetryTime, this.mId, this.mLinkStatus, this.mProtocolType, this.mInterfaceName, this.mAddresses, this.mDnsAddresses, this.mGatewayAddresses, this.mPcscfAddresses, this.mMtu);
        }

        public Builder setAddresses(List<LinkAddress> list) {
            this.mAddresses = list;
            return this;
        }

        public Builder setCause(int n) {
            this.mCause = n;
            return this;
        }

        public Builder setDnsAddresses(List<InetAddress> list) {
            this.mDnsAddresses = list;
            return this;
        }

        public Builder setGatewayAddresses(List<InetAddress> list) {
            this.mGatewayAddresses = list;
            return this;
        }

        public Builder setId(int n) {
            this.mId = n;
            return this;
        }

        public Builder setInterfaceName(String string2) {
            this.mInterfaceName = string2;
            return this;
        }

        public Builder setLinkStatus(int n) {
            this.mLinkStatus = n;
            return this;
        }

        public Builder setMtu(int n) {
            this.mMtu = n;
            return this;
        }

        public Builder setPcscfAddresses(List<InetAddress> list) {
            this.mPcscfAddresses = list;
            return this;
        }

        public Builder setProtocolType(int n) {
            this.mProtocolType = n;
            return this;
        }

        public Builder setSuggestedRetryTime(int n) {
            this.mSuggestedRetryTime = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LinkStatus {
    }

}

