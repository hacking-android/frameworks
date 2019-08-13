/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2;

import android.net.wifi.hotspot2.pps.Credential;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.net.wifi.hotspot2.pps.Policy;
import android.net.wifi.hotspot2.pps.UpdateParameter;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public final class PasspointConfiguration
implements Parcelable {
    private static final int CERTIFICATE_SHA256_BYTES = 32;
    public static final Parcelable.Creator<PasspointConfiguration> CREATOR = new Parcelable.Creator<PasspointConfiguration>(){

        private Map<String, byte[]> readTrustRootCerts(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            HashMap<String, byte[]> hashMap = new HashMap<String, byte[]>(n);
            for (int i = 0; i < n; ++i) {
                hashMap.put(parcel.readString(), parcel.createByteArray());
            }
            return hashMap;
        }

        @Override
        public PasspointConfiguration createFromParcel(Parcel parcel) {
            PasspointConfiguration passpointConfiguration = new PasspointConfiguration();
            passpointConfiguration.setHomeSp((HomeSp)parcel.readParcelable(null));
            passpointConfiguration.setCredential((Credential)parcel.readParcelable(null));
            passpointConfiguration.setPolicy((Policy)parcel.readParcelable(null));
            passpointConfiguration.setSubscriptionUpdate((UpdateParameter)parcel.readParcelable(null));
            passpointConfiguration.setTrustRootCertList(this.readTrustRootCerts(parcel));
            passpointConfiguration.setUpdateIdentifier(parcel.readInt());
            passpointConfiguration.setCredentialPriority(parcel.readInt());
            passpointConfiguration.setSubscriptionCreationTimeInMillis(parcel.readLong());
            passpointConfiguration.setSubscriptionExpirationTimeInMillis(parcel.readLong());
            passpointConfiguration.setSubscriptionType(parcel.readString());
            passpointConfiguration.setUsageLimitUsageTimePeriodInMinutes(parcel.readLong());
            passpointConfiguration.setUsageLimitStartTimeInMillis(parcel.readLong());
            passpointConfiguration.setUsageLimitDataLimit(parcel.readLong());
            passpointConfiguration.setUsageLimitTimeLimitInMinutes(parcel.readLong());
            passpointConfiguration.setServiceFriendlyNames((HashMap)parcel.readBundle().getSerializable("serviceFriendlyNames"));
            return passpointConfiguration;
        }

        public PasspointConfiguration[] newArray(int n) {
            return new PasspointConfiguration[n];
        }
    };
    private static final int MAX_URL_BYTES = 1023;
    private static final int NULL_VALUE = -1;
    private static final String TAG = "PasspointConfiguration";
    private Credential mCredential = null;
    private int mCredentialPriority = Integer.MIN_VALUE;
    private HomeSp mHomeSp = null;
    private Policy mPolicy = null;
    private Map<String, String> mServiceFriendlyNames = null;
    private long mSubscriptionCreationTimeInMillis = Long.MIN_VALUE;
    private long mSubscriptionExpirationTimeInMillis = Long.MIN_VALUE;
    private String mSubscriptionType = null;
    private UpdateParameter mSubscriptionUpdate = null;
    private Map<String, byte[]> mTrustRootCertList = null;
    private int mUpdateIdentifier = Integer.MIN_VALUE;
    private long mUsageLimitDataLimit = Long.MIN_VALUE;
    private long mUsageLimitStartTimeInMillis = Long.MIN_VALUE;
    private long mUsageLimitTimeLimitInMinutes = Long.MIN_VALUE;
    private long mUsageLimitUsageTimePeriodInMinutes = Long.MIN_VALUE;

    public PasspointConfiguration() {
    }

    public PasspointConfiguration(PasspointConfiguration passpointConfiguration) {
        if (passpointConfiguration == null) {
            return;
        }
        Object object = passpointConfiguration.mHomeSp;
        if (object != null) {
            this.mHomeSp = new HomeSp((HomeSp)object);
        }
        if ((object = passpointConfiguration.mCredential) != null) {
            this.mCredential = new Credential((Credential)object);
        }
        if ((object = passpointConfiguration.mPolicy) != null) {
            this.mPolicy = new Policy((Policy)object);
        }
        if ((object = passpointConfiguration.mTrustRootCertList) != null) {
            this.mTrustRootCertList = Collections.unmodifiableMap(object);
        }
        if ((object = passpointConfiguration.mSubscriptionUpdate) != null) {
            this.mSubscriptionUpdate = new UpdateParameter((UpdateParameter)object);
        }
        this.mUpdateIdentifier = passpointConfiguration.mUpdateIdentifier;
        this.mCredentialPriority = passpointConfiguration.mCredentialPriority;
        this.mSubscriptionCreationTimeInMillis = passpointConfiguration.mSubscriptionCreationTimeInMillis;
        this.mSubscriptionExpirationTimeInMillis = passpointConfiguration.mSubscriptionExpirationTimeInMillis;
        this.mSubscriptionType = passpointConfiguration.mSubscriptionType;
        this.mUsageLimitDataLimit = passpointConfiguration.mUsageLimitDataLimit;
        this.mUsageLimitStartTimeInMillis = passpointConfiguration.mUsageLimitStartTimeInMillis;
        this.mUsageLimitTimeLimitInMinutes = passpointConfiguration.mUsageLimitTimeLimitInMinutes;
        this.mUsageLimitUsageTimePeriodInMinutes = passpointConfiguration.mUsageLimitUsageTimePeriodInMinutes;
        this.mServiceFriendlyNames = passpointConfiguration.mServiceFriendlyNames;
    }

    private static boolean isTrustRootCertListEquals(Map<String, byte[]> object, Map<String, byte[]> map) {
        boolean bl = true;
        if (object != null && map != null) {
            if (object.size() != map.size()) {
                return false;
            }
            for (Map.Entry entry : object.entrySet()) {
                if (Arrays.equals((byte[])entry.getValue(), map.get(entry.getKey()))) continue;
                return false;
            }
            return true;
        }
        if (object != map) {
            bl = false;
        }
        return bl;
    }

    private boolean validateForCommonR1andR2(boolean bl) {
        Map<String, byte[]> map = this.mHomeSp;
        if (map != null && ((HomeSp)((Object)map)).validate()) {
            map = this.mCredential;
            if (map != null && ((Credential)((Object)map)).validate(bl)) {
                map = this.mPolicy;
                if (map != null && !((Policy)((Object)map)).validate()) {
                    return false;
                }
                map = this.mTrustRootCertList;
                if (map != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        map = (String)entry.getKey();
                        byte[] object = (byte[])entry.getValue();
                        if (TextUtils.isEmpty((CharSequence)((Object)map))) {
                            Log.d(TAG, "Empty URL");
                            return false;
                        }
                        if (((String)((Object)map)).getBytes(StandardCharsets.UTF_8).length > 1023) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("URL bytes exceeded the max: ");
                            stringBuilder.append(((String)((Object)map)).getBytes(StandardCharsets.UTF_8).length);
                            Log.d(TAG, stringBuilder.toString());
                            return false;
                        }
                        if (object == null) {
                            Log.d(TAG, "Fingerprint not specified");
                            return false;
                        }
                        if (object.length == 32) continue;
                        map = new StringBuilder();
                        ((StringBuilder)((Object)map)).append("Incorrect size of trust root certificate SHA-256 fingerprint: ");
                        ((StringBuilder)((Object)map)).append(object.length);
                        Log.d(TAG, ((StringBuilder)((Object)map)).toString());
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private static void writeTrustRootCerts(Parcel parcel, Map<String, byte[]> object) {
        if (object == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(object.size());
        for (Map.Entry entry : object.entrySet()) {
            parcel.writeString((String)entry.getKey());
            parcel.writeByteArray((byte[])entry.getValue());
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
        if (!(object instanceof PasspointConfiguration)) {
            return false;
        }
        object = (PasspointConfiguration)object;
        Object object2 = this.mHomeSp;
        if (!((object2 == null ? ((PasspointConfiguration)object).mHomeSp == null : ((HomeSp)object2).equals(((PasspointConfiguration)object).mHomeSp)) && ((object2 = this.mCredential) == null ? ((PasspointConfiguration)object).mCredential == null : ((Credential)object2).equals(((PasspointConfiguration)object).mCredential)) && ((object2 = this.mPolicy) == null ? ((PasspointConfiguration)object).mPolicy == null : ((Policy)object2).equals(((PasspointConfiguration)object).mPolicy)) && ((object2 = this.mSubscriptionUpdate) == null ? ((PasspointConfiguration)object).mSubscriptionUpdate == null : ((UpdateParameter)object2).equals(((PasspointConfiguration)object).mSubscriptionUpdate)) && PasspointConfiguration.isTrustRootCertListEquals(this.mTrustRootCertList, ((PasspointConfiguration)object).mTrustRootCertList) && this.mUpdateIdentifier == ((PasspointConfiguration)object).mUpdateIdentifier && this.mCredentialPriority == ((PasspointConfiguration)object).mCredentialPriority && this.mSubscriptionCreationTimeInMillis == ((PasspointConfiguration)object).mSubscriptionCreationTimeInMillis && this.mSubscriptionExpirationTimeInMillis == ((PasspointConfiguration)object).mSubscriptionExpirationTimeInMillis && TextUtils.equals(this.mSubscriptionType, ((PasspointConfiguration)object).mSubscriptionType) && this.mUsageLimitUsageTimePeriodInMinutes == ((PasspointConfiguration)object).mUsageLimitUsageTimePeriodInMinutes && this.mUsageLimitStartTimeInMillis == ((PasspointConfiguration)object).mUsageLimitStartTimeInMillis && this.mUsageLimitDataLimit == ((PasspointConfiguration)object).mUsageLimitDataLimit && this.mUsageLimitTimeLimitInMinutes == ((PasspointConfiguration)object).mUsageLimitTimeLimitInMinutes && ((object2 = this.mServiceFriendlyNames) == null ? ((PasspointConfiguration)object).mServiceFriendlyNames == null : object2.equals(((PasspointConfiguration)object).mServiceFriendlyNames)))) {
            bl = false;
        }
        return bl;
    }

    public Credential getCredential() {
        return this.mCredential;
    }

    public int getCredentialPriority() {
        return this.mCredentialPriority;
    }

    public HomeSp getHomeSp() {
        return this.mHomeSp;
    }

    public Policy getPolicy() {
        return this.mPolicy;
    }

    public String getServiceFriendlyName() {
        Object object = this.mServiceFriendlyNames;
        if (object != null && !object.isEmpty()) {
            object = Locale.getDefault().getLanguage();
            if ((object = this.mServiceFriendlyNames.get(object)) != null) {
                return object;
            }
            object = this.mServiceFriendlyNames.get("en");
            if (object != null) {
                return object;
            }
            object = this.mServiceFriendlyNames;
            return object.get(object.keySet().stream().findFirst().get());
        }
        return null;
    }

    public Map<String, String> getServiceFriendlyNames() {
        return this.mServiceFriendlyNames;
    }

    public long getSubscriptionCreationTimeInMillis() {
        return this.mSubscriptionCreationTimeInMillis;
    }

    public long getSubscriptionExpirationTimeInMillis() {
        return this.mSubscriptionExpirationTimeInMillis;
    }

    public String getSubscriptionType() {
        return this.mSubscriptionType;
    }

    public UpdateParameter getSubscriptionUpdate() {
        return this.mSubscriptionUpdate;
    }

    public Map<String, byte[]> getTrustRootCertList() {
        return this.mTrustRootCertList;
    }

    public int getUpdateIdentifier() {
        return this.mUpdateIdentifier;
    }

    public long getUsageLimitDataLimit() {
        return this.mUsageLimitDataLimit;
    }

    public long getUsageLimitStartTimeInMillis() {
        return this.mUsageLimitStartTimeInMillis;
    }

    public long getUsageLimitTimeLimitInMinutes() {
        return this.mUsageLimitTimeLimitInMinutes;
    }

    public long getUsageLimitUsageTimePeriodInMinutes() {
        return this.mUsageLimitUsageTimePeriodInMinutes;
    }

    public int hashCode() {
        return Objects.hash(this.mHomeSp, this.mCredential, this.mPolicy, this.mSubscriptionUpdate, this.mTrustRootCertList, this.mUpdateIdentifier, this.mCredentialPriority, this.mSubscriptionCreationTimeInMillis, this.mSubscriptionExpirationTimeInMillis, this.mUsageLimitUsageTimePeriodInMinutes, this.mUsageLimitStartTimeInMillis, this.mUsageLimitDataLimit, this.mUsageLimitTimeLimitInMinutes, this.mServiceFriendlyNames);
    }

    public void setCredential(Credential credential) {
        this.mCredential = credential;
    }

    public void setCredentialPriority(int n) {
        this.mCredentialPriority = n;
    }

    public void setHomeSp(HomeSp homeSp) {
        this.mHomeSp = homeSp;
    }

    public void setPolicy(Policy policy) {
        this.mPolicy = policy;
    }

    public void setServiceFriendlyNames(Map<String, String> map) {
        this.mServiceFriendlyNames = map;
    }

    public void setSubscriptionCreationTimeInMillis(long l) {
        this.mSubscriptionCreationTimeInMillis = l;
    }

    public void setSubscriptionExpirationTimeInMillis(long l) {
        this.mSubscriptionExpirationTimeInMillis = l;
    }

    public void setSubscriptionType(String string2) {
        this.mSubscriptionType = string2;
    }

    public void setSubscriptionUpdate(UpdateParameter updateParameter) {
        this.mSubscriptionUpdate = updateParameter;
    }

    public void setTrustRootCertList(Map<String, byte[]> map) {
        this.mTrustRootCertList = map;
    }

    public void setUpdateIdentifier(int n) {
        this.mUpdateIdentifier = n;
    }

    public void setUsageLimitDataLimit(long l) {
        this.mUsageLimitDataLimit = l;
    }

    public void setUsageLimitStartTimeInMillis(long l) {
        this.mUsageLimitStartTimeInMillis = l;
    }

    public void setUsageLimitTimeLimitInMinutes(long l) {
        this.mUsageLimitTimeLimitInMinutes = l;
    }

    public void setUsageLimitUsageTimePeriodInMinutes(long l) {
        this.mUsageLimitUsageTimePeriodInMinutes = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UpdateIdentifier: ");
        stringBuilder.append(this.mUpdateIdentifier);
        stringBuilder.append("\n");
        stringBuilder.append("CredentialPriority: ");
        stringBuilder.append(this.mCredentialPriority);
        stringBuilder.append("\n");
        stringBuilder.append("SubscriptionCreationTime: ");
        long l = this.mSubscriptionCreationTimeInMillis;
        String string2 = "Not specified";
        Object object = l != Long.MIN_VALUE ? new Date(l) : "Not specified";
        stringBuilder.append(object);
        stringBuilder.append("\n");
        stringBuilder.append("SubscriptionExpirationTime: ");
        l = this.mSubscriptionExpirationTimeInMillis;
        object = l != Long.MIN_VALUE ? new Date(l) : "Not specified";
        stringBuilder.append(object);
        stringBuilder.append("\n");
        stringBuilder.append("UsageLimitStartTime: ");
        l = this.mUsageLimitStartTimeInMillis;
        object = l != Long.MIN_VALUE ? new Date(l) : string2;
        stringBuilder.append(object);
        stringBuilder.append("\n");
        stringBuilder.append("UsageTimePeriod: ");
        stringBuilder.append(this.mUsageLimitUsageTimePeriodInMinutes);
        stringBuilder.append("\n");
        stringBuilder.append("UsageLimitDataLimit: ");
        stringBuilder.append(this.mUsageLimitDataLimit);
        stringBuilder.append("\n");
        stringBuilder.append("UsageLimitTimeLimit: ");
        stringBuilder.append(this.mUsageLimitTimeLimitInMinutes);
        stringBuilder.append("\n");
        if (this.mHomeSp != null) {
            stringBuilder.append("HomeSP Begin ---\n");
            stringBuilder.append(this.mHomeSp);
            stringBuilder.append("HomeSP End ---\n");
        }
        if (this.mCredential != null) {
            stringBuilder.append("Credential Begin ---\n");
            stringBuilder.append(this.mCredential);
            stringBuilder.append("Credential End ---\n");
        }
        if (this.mPolicy != null) {
            stringBuilder.append("Policy Begin ---\n");
            stringBuilder.append(this.mPolicy);
            stringBuilder.append("Policy End ---\n");
        }
        if (this.mSubscriptionUpdate != null) {
            stringBuilder.append("SubscriptionUpdate Begin ---\n");
            stringBuilder.append(this.mSubscriptionUpdate);
            stringBuilder.append("SubscriptionUpdate End ---\n");
        }
        if (this.mTrustRootCertList != null) {
            stringBuilder.append("TrustRootCertServers: ");
            stringBuilder.append(this.mTrustRootCertList.keySet());
            stringBuilder.append("\n");
        }
        if (this.mServiceFriendlyNames != null) {
            stringBuilder.append("ServiceFriendlyNames: ");
            stringBuilder.append(this.mServiceFriendlyNames);
        }
        return stringBuilder.toString();
    }

    public boolean validate() {
        UpdateParameter updateParameter = this.mSubscriptionUpdate;
        if (updateParameter != null && !updateParameter.validate()) {
            return false;
        }
        return this.validateForCommonR1andR2(true);
    }

    public boolean validateForR2() {
        if (this.mUpdateIdentifier == Integer.MIN_VALUE) {
            return false;
        }
        UpdateParameter updateParameter = this.mSubscriptionUpdate;
        if (updateParameter != null && updateParameter.validate()) {
            return this.validateForCommonR1andR2(false);
        }
        return false;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mHomeSp, n);
        parcel.writeParcelable(this.mCredential, n);
        parcel.writeParcelable(this.mPolicy, n);
        parcel.writeParcelable(this.mSubscriptionUpdate, n);
        PasspointConfiguration.writeTrustRootCerts(parcel, this.mTrustRootCertList);
        parcel.writeInt(this.mUpdateIdentifier);
        parcel.writeInt(this.mCredentialPriority);
        parcel.writeLong(this.mSubscriptionCreationTimeInMillis);
        parcel.writeLong(this.mSubscriptionExpirationTimeInMillis);
        parcel.writeString(this.mSubscriptionType);
        parcel.writeLong(this.mUsageLimitUsageTimePeriodInMinutes);
        parcel.writeLong(this.mUsageLimitStartTimeInMillis);
        parcel.writeLong(this.mUsageLimitDataLimit);
        parcel.writeLong(this.mUsageLimitTimeLimitInMinutes);
        Bundle bundle = new Bundle();
        bundle.putSerializable("serviceFriendlyNames", (HashMap)this.mServiceFriendlyNames);
        parcel.writeBundle(bundle);
    }

}

