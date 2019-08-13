/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.pps;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class HomeSp
implements Parcelable {
    public static final Parcelable.Creator<HomeSp> CREATOR = new Parcelable.Creator<HomeSp>(){

        private Map<String, Long> readHomeNetworkIds(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            HashMap<String, Long> hashMap = new HashMap<String, Long>(n);
            for (int i = 0; i < n; ++i) {
                String string2 = parcel.readString();
                Long l = null;
                long l2 = parcel.readLong();
                if (l2 != -1L) {
                    l = l2;
                }
                hashMap.put(string2, l);
            }
            return hashMap;
        }

        @Override
        public HomeSp createFromParcel(Parcel parcel) {
            HomeSp homeSp = new HomeSp();
            homeSp.setFqdn(parcel.readString());
            homeSp.setFriendlyName(parcel.readString());
            homeSp.setIconUrl(parcel.readString());
            homeSp.setHomeNetworkIds(this.readHomeNetworkIds(parcel));
            homeSp.setMatchAllOis(parcel.createLongArray());
            homeSp.setMatchAnyOis(parcel.createLongArray());
            homeSp.setOtherHomePartners(parcel.createStringArray());
            homeSp.setRoamingConsortiumOis(parcel.createLongArray());
            return homeSp;
        }

        public HomeSp[] newArray(int n) {
            return new HomeSp[n];
        }
    };
    private static final int MAX_SSID_BYTES = 32;
    private static final int NULL_VALUE = -1;
    private static final String TAG = "HomeSp";
    private String mFqdn = null;
    private String mFriendlyName = null;
    private Map<String, Long> mHomeNetworkIds = null;
    private String mIconUrl = null;
    private long[] mMatchAllOis = null;
    private long[] mMatchAnyOis = null;
    private String[] mOtherHomePartners = null;
    private long[] mRoamingConsortiumOis = null;

    public HomeSp() {
    }

    public HomeSp(HomeSp arrl) {
        if (arrl == null) {
            return;
        }
        this.mFqdn = arrl.mFqdn;
        this.mFriendlyName = arrl.mFriendlyName;
        this.mIconUrl = arrl.mIconUrl;
        Object[] arrobject = arrl.mHomeNetworkIds;
        if (arrobject != null) {
            this.mHomeNetworkIds = Collections.unmodifiableMap(arrobject);
        }
        if ((arrobject = arrl.mMatchAllOis) != null) {
            this.mMatchAllOis = Arrays.copyOf(arrobject, arrobject.length);
        }
        if ((arrobject = arrl.mMatchAnyOis) != null) {
            this.mMatchAnyOis = Arrays.copyOf(arrobject, arrobject.length);
        }
        if ((arrobject = arrl.mOtherHomePartners) != null) {
            this.mOtherHomePartners = (String[])Arrays.copyOf(arrobject, arrobject.length);
        }
        if ((arrl = arrl.mRoamingConsortiumOis) != null) {
            this.mRoamingConsortiumOis = Arrays.copyOf(arrl, arrl.length);
        }
    }

    private static void writeHomeNetworkIds(Parcel parcel, Map<String, Long> object2) {
        if (object2 == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(object2.size());
        for (Map.Entry entry : object2.entrySet()) {
            parcel.writeString((String)entry.getKey());
            if (entry.getValue() == null) {
                parcel.writeLong(-1L);
                continue;
            }
            parcel.writeLong((Long)entry.getValue());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        Map<String, Long> map;
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof HomeSp)) {
            return false;
        }
        object = (HomeSp)object;
        if (!(TextUtils.equals(this.mFqdn, ((HomeSp)object).mFqdn) && TextUtils.equals(this.mFriendlyName, ((HomeSp)object).mFriendlyName) && TextUtils.equals(this.mIconUrl, ((HomeSp)object).mIconUrl) && ((map = this.mHomeNetworkIds) == null ? ((HomeSp)object).mHomeNetworkIds == null : map.equals(((HomeSp)object).mHomeNetworkIds)) && Arrays.equals(this.mMatchAllOis, ((HomeSp)object).mMatchAllOis) && Arrays.equals(this.mMatchAnyOis, ((HomeSp)object).mMatchAnyOis) && Arrays.equals(this.mOtherHomePartners, ((HomeSp)object).mOtherHomePartners) && Arrays.equals(this.mRoamingConsortiumOis, ((HomeSp)object).mRoamingConsortiumOis))) {
            bl = false;
        }
        return bl;
    }

    public String getFqdn() {
        return this.mFqdn;
    }

    public String getFriendlyName() {
        return this.mFriendlyName;
    }

    public Map<String, Long> getHomeNetworkIds() {
        return this.mHomeNetworkIds;
    }

    public String getIconUrl() {
        return this.mIconUrl;
    }

    public long[] getMatchAllOis() {
        return this.mMatchAllOis;
    }

    public long[] getMatchAnyOis() {
        return this.mMatchAnyOis;
    }

    public String[] getOtherHomePartners() {
        return this.mOtherHomePartners;
    }

    public long[] getRoamingConsortiumOis() {
        return this.mRoamingConsortiumOis;
    }

    public int hashCode() {
        return Objects.hash(this.mFqdn, this.mFriendlyName, this.mIconUrl, this.mHomeNetworkIds, this.mMatchAllOis, this.mMatchAnyOis, this.mOtherHomePartners, this.mRoamingConsortiumOis);
    }

    public void setFqdn(String string2) {
        this.mFqdn = string2;
    }

    public void setFriendlyName(String string2) {
        this.mFriendlyName = string2;
    }

    public void setHomeNetworkIds(Map<String, Long> map) {
        this.mHomeNetworkIds = map;
    }

    public void setIconUrl(String string2) {
        this.mIconUrl = string2;
    }

    public void setMatchAllOis(long[] arrl) {
        this.mMatchAllOis = arrl;
    }

    public void setMatchAnyOis(long[] arrl) {
        this.mMatchAnyOis = arrl;
    }

    public void setOtherHomePartners(String[] arrstring) {
        this.mOtherHomePartners = arrstring;
    }

    public void setRoamingConsortiumOis(long[] arrl) {
        this.mRoamingConsortiumOis = arrl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FQDN: ");
        stringBuilder.append(this.mFqdn);
        stringBuilder.append("\n");
        stringBuilder.append("FriendlyName: ");
        stringBuilder.append(this.mFriendlyName);
        stringBuilder.append("\n");
        stringBuilder.append("IconURL: ");
        stringBuilder.append(this.mIconUrl);
        stringBuilder.append("\n");
        stringBuilder.append("HomeNetworkIDs: ");
        stringBuilder.append(this.mHomeNetworkIds);
        stringBuilder.append("\n");
        stringBuilder.append("MatchAllOIs: ");
        stringBuilder.append(this.mMatchAllOis);
        stringBuilder.append("\n");
        stringBuilder.append("MatchAnyOIs: ");
        stringBuilder.append(this.mMatchAnyOis);
        stringBuilder.append("\n");
        stringBuilder.append("OtherHomePartners: ");
        stringBuilder.append(this.mOtherHomePartners);
        stringBuilder.append("\n");
        stringBuilder.append("RoamingConsortiumOIs: ");
        stringBuilder.append(this.mRoamingConsortiumOis);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public boolean validate() {
        if (TextUtils.isEmpty(this.mFqdn)) {
            Log.d(TAG, "Missing FQDN");
            return false;
        }
        if (TextUtils.isEmpty(this.mFriendlyName)) {
            Log.d(TAG, "Missing friendly name");
            return false;
        }
        Map<String, Long> object2 = this.mHomeNetworkIds;
        if (object2 != null) {
            for (Map.Entry<String, Long> entry : object2.entrySet()) {
                if (entry.getKey() != null && entry.getKey().getBytes(StandardCharsets.UTF_8).length <= 32) continue;
                Log.d(TAG, "Invalid SSID in HomeNetworkIDs");
                return false;
            }
        }
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mFqdn);
        parcel.writeString(this.mFriendlyName);
        parcel.writeString(this.mIconUrl);
        HomeSp.writeHomeNetworkIds(parcel, this.mHomeNetworkIds);
        parcel.writeLongArray(this.mMatchAllOis);
        parcel.writeLongArray(this.mMatchAnyOis);
        parcel.writeStringArray(this.mOtherHomePartners);
        parcel.writeLongArray(this.mRoamingConsortiumOis);
    }

}

