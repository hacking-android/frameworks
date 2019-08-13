/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import android.telephony.UiccAccessRule;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
public final class EuiccProfileInfo
implements Parcelable {
    public static final Parcelable.Creator<EuiccProfileInfo> CREATOR = new Parcelable.Creator<EuiccProfileInfo>(){

        @Override
        public EuiccProfileInfo createFromParcel(Parcel parcel) {
            return new EuiccProfileInfo(parcel);
        }

        public EuiccProfileInfo[] newArray(int n) {
            return new EuiccProfileInfo[n];
        }
    };
    public static final int POLICY_RULE_DELETE_AFTER_DISABLING = 4;
    public static final int POLICY_RULE_DO_NOT_DELETE = 2;
    public static final int POLICY_RULE_DO_NOT_DISABLE = 1;
    public static final int PROFILE_CLASS_OPERATIONAL = 2;
    public static final int PROFILE_CLASS_PROVISIONING = 1;
    public static final int PROFILE_CLASS_TESTING = 0;
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final int PROFILE_STATE_DISABLED = 0;
    public static final int PROFILE_STATE_ENABLED = 1;
    public static final int PROFILE_STATE_UNSET = -1;
    private final UiccAccessRule[] mAccessRules;
    private final CarrierIdentifier mCarrierIdentifier;
    private final String mIccid;
    private final String mNickname;
    private final int mPolicyRules;
    private final int mProfileClass;
    private final String mProfileName;
    private final String mServiceProviderName;
    private final int mState;

    private EuiccProfileInfo(Parcel parcel) {
        this.mIccid = parcel.readString();
        this.mNickname = parcel.readString();
        this.mServiceProviderName = parcel.readString();
        this.mProfileName = parcel.readString();
        this.mProfileClass = parcel.readInt();
        this.mState = parcel.readInt();
        this.mCarrierIdentifier = parcel.readByte() == 1 ? CarrierIdentifier.CREATOR.createFromParcel(parcel) : null;
        this.mPolicyRules = parcel.readInt();
        this.mAccessRules = parcel.createTypedArray(UiccAccessRule.CREATOR);
    }

    private EuiccProfileInfo(String string2, String string3, String string4, String string5, int n, int n2, CarrierIdentifier carrierIdentifier, int n3, List<UiccAccessRule> list) {
        this.mIccid = string2;
        this.mNickname = string3;
        this.mServiceProviderName = string4;
        this.mProfileName = string5;
        this.mProfileClass = n;
        this.mState = n2;
        this.mCarrierIdentifier = carrierIdentifier;
        this.mPolicyRules = n3;
        this.mAccessRules = list != null && list.size() > 0 ? list.toArray(new UiccAccessRule[list.size()]) : null;
    }

    @Deprecated
    @UnsupportedAppUsage
    public EuiccProfileInfo(String string2, UiccAccessRule[] object, String string3) {
        if (TextUtils.isDigitsOnly(string2)) {
            this.mIccid = string2;
            this.mAccessRules = object;
            this.mNickname = string3;
            this.mServiceProviderName = null;
            this.mProfileName = null;
            this.mProfileClass = -1;
            this.mState = -1;
            this.mCarrierIdentifier = null;
            this.mPolicyRules = 0;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("iccid contains invalid characters: ");
        ((StringBuilder)object).append(string2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (EuiccProfileInfo)object;
            if (!(Objects.equals(this.mIccid, ((EuiccProfileInfo)object).mIccid) && Objects.equals(this.mNickname, ((EuiccProfileInfo)object).mNickname) && Objects.equals(this.mServiceProviderName, ((EuiccProfileInfo)object).mServiceProviderName) && Objects.equals(this.mProfileName, ((EuiccProfileInfo)object).mProfileName) && this.mProfileClass == ((EuiccProfileInfo)object).mProfileClass && this.mState == ((EuiccProfileInfo)object).mState && Objects.equals(this.mCarrierIdentifier, ((EuiccProfileInfo)object).mCarrierIdentifier) && this.mPolicyRules == ((EuiccProfileInfo)object).mPolicyRules && Arrays.equals(this.mAccessRules, ((EuiccProfileInfo)object).mAccessRules))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public CarrierIdentifier getCarrierIdentifier() {
        return this.mCarrierIdentifier;
    }

    public String getIccid() {
        return this.mIccid;
    }

    public String getNickname() {
        return this.mNickname;
    }

    public int getPolicyRules() {
        return this.mPolicyRules;
    }

    public int getProfileClass() {
        return this.mProfileClass;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public String getServiceProviderName() {
        return this.mServiceProviderName;
    }

    public int getState() {
        return this.mState;
    }

    public List<UiccAccessRule> getUiccAccessRules() {
        UiccAccessRule[] arruiccAccessRule = this.mAccessRules;
        if (arruiccAccessRule == null) {
            return null;
        }
        return Arrays.asList(arruiccAccessRule);
    }

    public boolean hasPolicyRule(int n) {
        boolean bl = (this.mPolicyRules & n) != 0;
        return bl;
    }

    public boolean hasPolicyRules() {
        boolean bl = this.mPolicyRules != 0;
        return bl;
    }

    public int hashCode() {
        return ((((((((1 * 31 + Objects.hashCode(this.mIccid)) * 31 + Objects.hashCode(this.mNickname)) * 31 + Objects.hashCode(this.mServiceProviderName)) * 31 + Objects.hashCode(this.mProfileName)) * 31 + this.mProfileClass) * 31 + this.mState) * 31 + Objects.hashCode(this.mCarrierIdentifier)) * 31 + this.mPolicyRules) * 31 + Arrays.hashCode(this.mAccessRules);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EuiccProfileInfo (nickname=");
        stringBuilder.append(this.mNickname);
        stringBuilder.append(", serviceProviderName=");
        stringBuilder.append(this.mServiceProviderName);
        stringBuilder.append(", profileName=");
        stringBuilder.append(this.mProfileName);
        stringBuilder.append(", profileClass=");
        stringBuilder.append(this.mProfileClass);
        stringBuilder.append(", state=");
        stringBuilder.append(this.mState);
        stringBuilder.append(", CarrierIdentifier=");
        stringBuilder.append(this.mCarrierIdentifier);
        stringBuilder.append(", policyRules=");
        stringBuilder.append(this.mPolicyRules);
        stringBuilder.append(", accessRules=");
        stringBuilder.append(Arrays.toString(this.mAccessRules));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mIccid);
        parcel.writeString(this.mNickname);
        parcel.writeString(this.mServiceProviderName);
        parcel.writeString(this.mProfileName);
        parcel.writeInt(this.mProfileClass);
        parcel.writeInt(this.mState);
        if (this.mCarrierIdentifier != null) {
            parcel.writeByte((byte)1);
            this.mCarrierIdentifier.writeToParcel(parcel, n);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeInt(this.mPolicyRules);
        parcel.writeTypedArray((Parcelable[])this.mAccessRules, n);
    }

    public static final class Builder {
        private List<UiccAccessRule> mAccessRules;
        private CarrierIdentifier mCarrierIdentifier;
        private String mIccid;
        private String mNickname;
        private int mPolicyRules;
        private int mProfileClass;
        private String mProfileName;
        private String mServiceProviderName;
        private int mState;

        public Builder(EuiccProfileInfo euiccProfileInfo) {
            this.mIccid = euiccProfileInfo.mIccid;
            this.mNickname = euiccProfileInfo.mNickname;
            this.mServiceProviderName = euiccProfileInfo.mServiceProviderName;
            this.mProfileName = euiccProfileInfo.mProfileName;
            this.mProfileClass = euiccProfileInfo.mProfileClass;
            this.mState = euiccProfileInfo.mState;
            this.mCarrierIdentifier = euiccProfileInfo.mCarrierIdentifier;
            this.mPolicyRules = euiccProfileInfo.mPolicyRules;
            this.mAccessRules = Arrays.asList(euiccProfileInfo.mAccessRules);
        }

        public Builder(String string2) {
            if (TextUtils.isDigitsOnly(string2)) {
                this.mIccid = string2;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("iccid contains invalid characters: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public EuiccProfileInfo build() {
            String string2 = this.mIccid;
            if (string2 != null) {
                return new EuiccProfileInfo(string2, this.mNickname, this.mServiceProviderName, this.mProfileName, this.mProfileClass, this.mState, this.mCarrierIdentifier, this.mPolicyRules, this.mAccessRules);
            }
            throw new IllegalStateException("ICCID must be set for a profile.");
        }

        public Builder setCarrierIdentifier(CarrierIdentifier carrierIdentifier) {
            this.mCarrierIdentifier = carrierIdentifier;
            return this;
        }

        public Builder setIccid(String string2) {
            if (TextUtils.isDigitsOnly(string2)) {
                this.mIccid = string2;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("iccid contains invalid characters: ");
            stringBuilder.append(string2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setNickname(String string2) {
            this.mNickname = string2;
            return this;
        }

        public Builder setPolicyRules(int n) {
            this.mPolicyRules = n;
            return this;
        }

        public Builder setProfileClass(int n) {
            this.mProfileClass = n;
            return this;
        }

        public Builder setProfileName(String string2) {
            this.mProfileName = string2;
            return this;
        }

        public Builder setServiceProviderName(String string2) {
            this.mServiceProviderName = string2;
            return this;
        }

        public Builder setState(int n) {
            this.mState = n;
            return this;
        }

        public Builder setUiccAccessRule(List<UiccAccessRule> list) {
            this.mAccessRules = list;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PolicyRule {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProfileClass {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProfileState {
    }

}

