/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.pps;

import android.net.wifi.hotspot2.pps.UpdateParameter;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Policy
implements Parcelable {
    public static final Parcelable.Creator<Policy> CREATOR = new Parcelable.Creator<Policy>(){

        private Map<Integer, String> readProtoPortMap(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            HashMap<Integer, String> hashMap = new HashMap<Integer, String>(n);
            for (int i = 0; i < n; ++i) {
                hashMap.put(parcel.readInt(), parcel.readString());
            }
            return hashMap;
        }

        private List<RoamingPartner> readRoamingPartnerList(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            ArrayList<RoamingPartner> arrayList = new ArrayList<RoamingPartner>();
            for (int i = 0; i < n; ++i) {
                arrayList.add((RoamingPartner)parcel.readParcelable(null));
            }
            return arrayList;
        }

        @Override
        public Policy createFromParcel(Parcel parcel) {
            Policy policy = new Policy();
            policy.setMinHomeDownlinkBandwidth(parcel.readLong());
            policy.setMinHomeUplinkBandwidth(parcel.readLong());
            policy.setMinRoamingDownlinkBandwidth(parcel.readLong());
            policy.setMinRoamingUplinkBandwidth(parcel.readLong());
            policy.setExcludedSsidList(parcel.createStringArray());
            policy.setRequiredProtoPortMap(this.readProtoPortMap(parcel));
            policy.setMaximumBssLoadValue(parcel.readInt());
            policy.setPreferredRoamingPartnerList(this.readRoamingPartnerList(parcel));
            policy.setPolicyUpdate((UpdateParameter)parcel.readParcelable(null));
            return policy;
        }

        public Policy[] newArray(int n) {
            return new Policy[n];
        }
    };
    private static final int MAX_EXCLUSION_SSIDS = 128;
    private static final int MAX_PORT_STRING_BYTES = 64;
    private static final int MAX_SSID_BYTES = 32;
    private static final int NULL_VALUE = -1;
    private static final String TAG = "Policy";
    private String[] mExcludedSsidList = null;
    private int mMaximumBssLoadValue = Integer.MIN_VALUE;
    private long mMinHomeDownlinkBandwidth = Long.MIN_VALUE;
    private long mMinHomeUplinkBandwidth = Long.MIN_VALUE;
    private long mMinRoamingDownlinkBandwidth = Long.MIN_VALUE;
    private long mMinRoamingUplinkBandwidth = Long.MIN_VALUE;
    private UpdateParameter mPolicyUpdate = null;
    private List<RoamingPartner> mPreferredRoamingPartnerList = null;
    private Map<Integer, String> mRequiredProtoPortMap = null;

    public Policy() {
    }

    public Policy(Policy parcelable) {
        if (parcelable == null) {
            return;
        }
        this.mMinHomeDownlinkBandwidth = parcelable.mMinHomeDownlinkBandwidth;
        this.mMinHomeUplinkBandwidth = parcelable.mMinHomeUplinkBandwidth;
        this.mMinRoamingDownlinkBandwidth = parcelable.mMinRoamingDownlinkBandwidth;
        this.mMinRoamingUplinkBandwidth = parcelable.mMinRoamingUplinkBandwidth;
        this.mMaximumBssLoadValue = parcelable.mMaximumBssLoadValue;
        Object object = parcelable.mExcludedSsidList;
        if (object != null) {
            this.mExcludedSsidList = Arrays.copyOf(object, ((String[])object).length);
        }
        if ((object = parcelable.mRequiredProtoPortMap) != null) {
            this.mRequiredProtoPortMap = Collections.unmodifiableMap(object);
        }
        if ((object = parcelable.mPreferredRoamingPartnerList) != null) {
            this.mPreferredRoamingPartnerList = Collections.unmodifiableList(object);
        }
        if ((parcelable = parcelable.mPolicyUpdate) != null) {
            this.mPolicyUpdate = new UpdateParameter((UpdateParameter)parcelable);
        }
    }

    private static void writeProtoPortMap(Parcel parcel, Map<Integer, String> object2) {
        if (object2 == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(object2.size());
        for (Map.Entry entry : object2.entrySet()) {
            parcel.writeInt((Integer)entry.getKey());
            parcel.writeString((String)entry.getValue());
        }
    }

    private static void writeRoamingPartnerList(Parcel parcel, int n, List<RoamingPartner> object) {
        if (object == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            parcel.writeParcelable((RoamingPartner)object.next(), n);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        Object object2;
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof Policy)) {
            return false;
        }
        object = (Policy)object;
        if (!(this.mMinHomeDownlinkBandwidth == ((Policy)object).mMinHomeDownlinkBandwidth && this.mMinHomeUplinkBandwidth == ((Policy)object).mMinHomeUplinkBandwidth && this.mMinRoamingDownlinkBandwidth == ((Policy)object).mMinRoamingDownlinkBandwidth && this.mMinRoamingUplinkBandwidth == ((Policy)object).mMinRoamingUplinkBandwidth && Arrays.equals(this.mExcludedSsidList, ((Policy)object).mExcludedSsidList) && ((object2 = this.mRequiredProtoPortMap) == null ? ((Policy)object).mRequiredProtoPortMap == null : object2.equals(((Policy)object).mRequiredProtoPortMap)) && this.mMaximumBssLoadValue == ((Policy)object).mMaximumBssLoadValue && ((object2 = this.mPreferredRoamingPartnerList) == null ? ((Policy)object).mPreferredRoamingPartnerList == null : object2.equals(((Policy)object).mPreferredRoamingPartnerList)) && ((object2 = this.mPolicyUpdate) == null ? ((Policy)object).mPolicyUpdate == null : ((UpdateParameter)object2).equals(((Policy)object).mPolicyUpdate)))) {
            bl = false;
        }
        return bl;
    }

    public String[] getExcludedSsidList() {
        return this.mExcludedSsidList;
    }

    public int getMaximumBssLoadValue() {
        return this.mMaximumBssLoadValue;
    }

    public long getMinHomeDownlinkBandwidth() {
        return this.mMinHomeDownlinkBandwidth;
    }

    public long getMinHomeUplinkBandwidth() {
        return this.mMinHomeUplinkBandwidth;
    }

    public long getMinRoamingDownlinkBandwidth() {
        return this.mMinRoamingDownlinkBandwidth;
    }

    public long getMinRoamingUplinkBandwidth() {
        return this.mMinRoamingUplinkBandwidth;
    }

    public UpdateParameter getPolicyUpdate() {
        return this.mPolicyUpdate;
    }

    public List<RoamingPartner> getPreferredRoamingPartnerList() {
        return this.mPreferredRoamingPartnerList;
    }

    public Map<Integer, String> getRequiredProtoPortMap() {
        return this.mRequiredProtoPortMap;
    }

    public int hashCode() {
        return Objects.hash(this.mMinHomeDownlinkBandwidth, this.mMinHomeUplinkBandwidth, this.mMinRoamingDownlinkBandwidth, this.mMinRoamingUplinkBandwidth, this.mExcludedSsidList, this.mRequiredProtoPortMap, this.mMaximumBssLoadValue, this.mPreferredRoamingPartnerList, this.mPolicyUpdate);
    }

    public void setExcludedSsidList(String[] arrstring) {
        this.mExcludedSsidList = arrstring;
    }

    public void setMaximumBssLoadValue(int n) {
        this.mMaximumBssLoadValue = n;
    }

    public void setMinHomeDownlinkBandwidth(long l) {
        this.mMinHomeDownlinkBandwidth = l;
    }

    public void setMinHomeUplinkBandwidth(long l) {
        this.mMinHomeUplinkBandwidth = l;
    }

    public void setMinRoamingDownlinkBandwidth(long l) {
        this.mMinRoamingDownlinkBandwidth = l;
    }

    public void setMinRoamingUplinkBandwidth(long l) {
        this.mMinRoamingUplinkBandwidth = l;
    }

    public void setPolicyUpdate(UpdateParameter updateParameter) {
        this.mPolicyUpdate = updateParameter;
    }

    public void setPreferredRoamingPartnerList(List<RoamingPartner> list) {
        this.mPreferredRoamingPartnerList = list;
    }

    public void setRequiredProtoPortMap(Map<Integer, String> map) {
        this.mRequiredProtoPortMap = map;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MinHomeDownlinkBandwidth: ");
        stringBuilder.append(this.mMinHomeDownlinkBandwidth);
        stringBuilder.append("\n");
        stringBuilder.append("MinHomeUplinkBandwidth: ");
        stringBuilder.append(this.mMinHomeUplinkBandwidth);
        stringBuilder.append("\n");
        stringBuilder.append("MinRoamingDownlinkBandwidth: ");
        stringBuilder.append(this.mMinRoamingDownlinkBandwidth);
        stringBuilder.append("\n");
        stringBuilder.append("MinRoamingUplinkBandwidth: ");
        stringBuilder.append(this.mMinRoamingUplinkBandwidth);
        stringBuilder.append("\n");
        stringBuilder.append("ExcludedSSIDList: ");
        stringBuilder.append(this.mExcludedSsidList);
        stringBuilder.append("\n");
        stringBuilder.append("RequiredProtoPortMap: ");
        stringBuilder.append(this.mRequiredProtoPortMap);
        stringBuilder.append("\n");
        stringBuilder.append("MaximumBSSLoadValue: ");
        stringBuilder.append(this.mMaximumBssLoadValue);
        stringBuilder.append("\n");
        stringBuilder.append("PreferredRoamingPartnerList: ");
        stringBuilder.append(this.mPreferredRoamingPartnerList);
        stringBuilder.append("\n");
        if (this.mPolicyUpdate != null) {
            stringBuilder.append("PolicyUpdate Begin ---\n");
            stringBuilder.append(this.mPolicyUpdate);
            stringBuilder.append("PolicyUpdate End ---\n");
        }
        return stringBuilder.toString();
    }

    public boolean validate() {
        Object object = this.mPolicyUpdate;
        if (object == null) {
            Log.d(TAG, "PolicyUpdate not specified");
            return false;
        }
        if (!((UpdateParameter)object).validate()) {
            return false;
        }
        Object object2 = this.mExcludedSsidList;
        if (object2 != null) {
            if (((String[])object2).length > 128) {
                object = new StringBuilder();
                ((StringBuilder)object).append("SSID exclusion list size exceeded the max: ");
                ((StringBuilder)object).append(this.mExcludedSsidList.length);
                Log.d(TAG, ((StringBuilder)object).toString());
                return false;
            }
            int n = ((String[])object2).length;
            for (int i = 0; i < n; ++i) {
                object = object2[i];
                if (((String)object).getBytes(StandardCharsets.UTF_8).length <= 32) continue;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Invalid SSID: ");
                ((StringBuilder)object2).append((String)object);
                Log.d(TAG, ((StringBuilder)object2).toString());
                return false;
            }
        }
        if ((object = this.mRequiredProtoPortMap) != null) {
            object2 = object.entrySet().iterator();
            while (object2.hasNext()) {
                object = (String)((Map.Entry)object2.next()).getValue();
                if (((String)object).getBytes(StandardCharsets.UTF_8).length <= 64) continue;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("PortNumber string bytes exceeded the max: ");
                ((StringBuilder)object2).append((String)object);
                Log.d(TAG, ((StringBuilder)object2).toString());
                return false;
            }
        }
        if ((object = this.mPreferredRoamingPartnerList) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                if (((RoamingPartner)object.next()).validate()) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mMinHomeDownlinkBandwidth);
        parcel.writeLong(this.mMinHomeUplinkBandwidth);
        parcel.writeLong(this.mMinRoamingDownlinkBandwidth);
        parcel.writeLong(this.mMinRoamingUplinkBandwidth);
        parcel.writeStringArray(this.mExcludedSsidList);
        Policy.writeProtoPortMap(parcel, this.mRequiredProtoPortMap);
        parcel.writeInt(this.mMaximumBssLoadValue);
        Policy.writeRoamingPartnerList(parcel, n, this.mPreferredRoamingPartnerList);
        parcel.writeParcelable(this.mPolicyUpdate, n);
    }

    public static final class RoamingPartner
    implements Parcelable {
        public static final Parcelable.Creator<RoamingPartner> CREATOR = new Parcelable.Creator<RoamingPartner>(){

            @Override
            public RoamingPartner createFromParcel(Parcel parcel) {
                RoamingPartner roamingPartner = new RoamingPartner();
                roamingPartner.setFqdn(parcel.readString());
                boolean bl = parcel.readInt() != 0;
                roamingPartner.setFqdnExactMatch(bl);
                roamingPartner.setPriority(parcel.readInt());
                roamingPartner.setCountries(parcel.readString());
                return roamingPartner;
            }

            public RoamingPartner[] newArray(int n) {
                return new RoamingPartner[n];
            }
        };
        private String mCountries = null;
        private String mFqdn = null;
        private boolean mFqdnExactMatch = false;
        private int mPriority = Integer.MIN_VALUE;

        public RoamingPartner() {
        }

        public RoamingPartner(RoamingPartner roamingPartner) {
            if (roamingPartner != null) {
                this.mFqdn = roamingPartner.mFqdn;
                this.mFqdnExactMatch = roamingPartner.mFqdnExactMatch;
                this.mPriority = roamingPartner.mPriority;
                this.mCountries = roamingPartner.mCountries;
            }
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
            if (!(object instanceof RoamingPartner)) {
                return false;
            }
            object = (RoamingPartner)object;
            if (!TextUtils.equals(this.mFqdn, ((RoamingPartner)object).mFqdn) || this.mFqdnExactMatch != ((RoamingPartner)object).mFqdnExactMatch || this.mPriority != ((RoamingPartner)object).mPriority || !TextUtils.equals(this.mCountries, ((RoamingPartner)object).mCountries)) {
                bl = false;
            }
            return bl;
        }

        public String getCountries() {
            return this.mCountries;
        }

        public String getFqdn() {
            return this.mFqdn;
        }

        public boolean getFqdnExactMatch() {
            return this.mFqdnExactMatch;
        }

        public int getPriority() {
            return this.mPriority;
        }

        public int hashCode() {
            return Objects.hash(this.mFqdn, this.mFqdnExactMatch, this.mPriority, this.mCountries);
        }

        public void setCountries(String string2) {
            this.mCountries = string2;
        }

        public void setFqdn(String string2) {
            this.mFqdn = string2;
        }

        public void setFqdnExactMatch(boolean bl) {
            this.mFqdnExactMatch = bl;
        }

        public void setPriority(int n) {
            this.mPriority = n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FQDN: ");
            stringBuilder.append(this.mFqdn);
            stringBuilder.append("\n");
            stringBuilder.append("ExactMatch: ");
            stringBuilder.append("mFqdnExactMatch");
            stringBuilder.append("\n");
            stringBuilder.append("Priority: ");
            stringBuilder.append(this.mPriority);
            stringBuilder.append("\n");
            stringBuilder.append("Countries: ");
            stringBuilder.append(this.mCountries);
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }

        public boolean validate() {
            if (TextUtils.isEmpty(this.mFqdn)) {
                Log.d(Policy.TAG, "Missing FQDN");
                return false;
            }
            if (TextUtils.isEmpty(this.mCountries)) {
                Log.d(Policy.TAG, "Missing countries");
                return false;
            }
            return true;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mFqdn);
            parcel.writeInt((int)this.mFqdnExactMatch);
            parcel.writeInt(this.mPriority);
            parcel.writeString(this.mCountries);
        }

    }

}

