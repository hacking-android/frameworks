/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.annotation.SystemApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public final class DataProfile
implements Parcelable {
    public static final Parcelable.Creator<DataProfile> CREATOR = new Parcelable.Creator<DataProfile>(){

        @Override
        public DataProfile createFromParcel(Parcel parcel) {
            return new DataProfile(parcel);
        }

        public DataProfile[] newArray(int n) {
            return new DataProfile[n];
        }
    };
    public static final int TYPE_3GPP = 1;
    public static final int TYPE_3GPP2 = 2;
    public static final int TYPE_COMMON = 0;
    private final String mApn;
    private final int mAuthType;
    private final int mBearerBitmask;
    private final boolean mEnabled;
    private final int mMaxConnections;
    private final int mMaxConnectionsTime;
    private final int mMtu;
    private final String mPassword;
    private final boolean mPersistent;
    private final boolean mPreferred;
    private final int mProfileId;
    private final int mProtocolType;
    private final int mRoamingProtocolType;
    private final int mSupportedApnTypesBitmask;
    private final int mType;
    private final String mUserName;
    private final int mWaitTime;

    private DataProfile(int n, String string2, int n2, int n3, String string3, String string4, int n4, int n5, int n6, int n7, boolean bl, int n8, int n9, int n10, int n11, boolean bl2, boolean bl3) {
        this.mProfileId = n;
        this.mApn = string2;
        this.mProtocolType = n2;
        n = n3 == -1 ? (TextUtils.isEmpty(string3) ? 0 : 3) : n3;
        this.mAuthType = n;
        this.mUserName = string3;
        this.mPassword = string4;
        this.mType = n4;
        this.mMaxConnectionsTime = n5;
        this.mMaxConnections = n6;
        this.mWaitTime = n7;
        this.mEnabled = bl;
        this.mSupportedApnTypesBitmask = n8;
        this.mRoamingProtocolType = n9;
        this.mBearerBitmask = n10;
        this.mMtu = n11;
        this.mPersistent = bl2;
        this.mPreferred = bl3;
    }

    private DataProfile(Parcel parcel) {
        this.mProfileId = parcel.readInt();
        this.mApn = parcel.readString();
        this.mProtocolType = parcel.readInt();
        this.mAuthType = parcel.readInt();
        this.mUserName = parcel.readString();
        this.mPassword = parcel.readString();
        this.mType = parcel.readInt();
        this.mMaxConnectionsTime = parcel.readInt();
        this.mMaxConnections = parcel.readInt();
        this.mWaitTime = parcel.readInt();
        this.mEnabled = parcel.readBoolean();
        this.mSupportedApnTypesBitmask = parcel.readInt();
        this.mRoamingProtocolType = parcel.readInt();
        this.mBearerBitmask = parcel.readInt();
        this.mMtu = parcel.readInt();
        this.mPersistent = parcel.readBoolean();
        this.mPreferred = parcel.readBoolean();
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
            object = (DataProfile)object;
            if (!(this.mProfileId == ((DataProfile)object).mProfileId && this.mProtocolType == ((DataProfile)object).mProtocolType && this.mAuthType == ((DataProfile)object).mAuthType && this.mType == ((DataProfile)object).mType && this.mMaxConnectionsTime == ((DataProfile)object).mMaxConnectionsTime && this.mMaxConnections == ((DataProfile)object).mMaxConnections && this.mWaitTime == ((DataProfile)object).mWaitTime && this.mEnabled == ((DataProfile)object).mEnabled && this.mSupportedApnTypesBitmask == ((DataProfile)object).mSupportedApnTypesBitmask && this.mRoamingProtocolType == ((DataProfile)object).mRoamingProtocolType && this.mBearerBitmask == ((DataProfile)object).mBearerBitmask && this.mMtu == ((DataProfile)object).mMtu && this.mPersistent == ((DataProfile)object).mPersistent && this.mPreferred == ((DataProfile)object).mPreferred && Objects.equals(this.mApn, ((DataProfile)object).mApn) && Objects.equals(this.mUserName, ((DataProfile)object).mUserName) && Objects.equals(this.mPassword, ((DataProfile)object).mPassword))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getApn() {
        return this.mApn;
    }

    public int getAuthType() {
        return this.mAuthType;
    }

    public int getBearerBitmask() {
        return this.mBearerBitmask;
    }

    public int getMaxConnections() {
        return this.mMaxConnections;
    }

    public int getMaxConnectionsTime() {
        return this.mMaxConnectionsTime;
    }

    public int getMtu() {
        return this.mMtu;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getProfileId() {
        return this.mProfileId;
    }

    public int getProtocolType() {
        return this.mProtocolType;
    }

    public int getRoamingProtocolType() {
        return this.mRoamingProtocolType;
    }

    public int getSupportedApnTypesBitmask() {
        return this.mSupportedApnTypesBitmask;
    }

    public int getType() {
        return this.mType;
    }

    public String getUserName() {
        return this.mUserName;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public int hashCode() {
        return Objects.hash(this.mProfileId, this.mApn, this.mProtocolType, this.mAuthType, this.mUserName, this.mPassword, this.mType, this.mMaxConnectionsTime, this.mMaxConnections, this.mWaitTime, this.mEnabled, this.mSupportedApnTypesBitmask, this.mRoamingProtocolType, this.mBearerBitmask, this.mMtu, this.mPersistent, this.mPreferred);
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public boolean isPreferred() {
        return this.mPreferred;
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataProfile=");
        stringBuilder.append(this.mProfileId);
        stringBuilder.append("/");
        stringBuilder.append(this.mProtocolType);
        stringBuilder.append("/");
        stringBuilder.append(this.mAuthType);
        stringBuilder.append("/");
        if (Build.IS_USER) {
            charSequence = "***/***/***";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mApn);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(this.mUserName);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(this.mPassword);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append("/");
        stringBuilder.append(this.mType);
        stringBuilder.append("/");
        stringBuilder.append(this.mMaxConnectionsTime);
        stringBuilder.append("/");
        stringBuilder.append(this.mMaxConnections);
        stringBuilder.append("/");
        stringBuilder.append(this.mWaitTime);
        stringBuilder.append("/");
        stringBuilder.append(this.mEnabled);
        stringBuilder.append("/");
        stringBuilder.append(this.mSupportedApnTypesBitmask);
        stringBuilder.append("/");
        stringBuilder.append(this.mRoamingProtocolType);
        stringBuilder.append("/");
        stringBuilder.append(this.mBearerBitmask);
        stringBuilder.append("/");
        stringBuilder.append(this.mMtu);
        stringBuilder.append("/");
        stringBuilder.append(this.mPersistent);
        stringBuilder.append("/");
        stringBuilder.append(this.mPreferred);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mProfileId);
        parcel.writeString(this.mApn);
        parcel.writeInt(this.mProtocolType);
        parcel.writeInt(this.mAuthType);
        parcel.writeString(this.mUserName);
        parcel.writeString(this.mPassword);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mMaxConnectionsTime);
        parcel.writeInt(this.mMaxConnections);
        parcel.writeInt(this.mWaitTime);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeInt(this.mSupportedApnTypesBitmask);
        parcel.writeInt(this.mRoamingProtocolType);
        parcel.writeInt(this.mBearerBitmask);
        parcel.writeInt(this.mMtu);
        parcel.writeBoolean(this.mPersistent);
        parcel.writeBoolean(this.mPreferred);
    }

    public static final class Builder {
        private String mApn;
        private int mAuthType;
        private int mBearerBitmask;
        private boolean mEnabled;
        private int mMaxConnections;
        private int mMaxConnectionsTime;
        private int mMtu;
        private String mPassword;
        private boolean mPersistent;
        private boolean mPreferred;
        private int mProfileId;
        private int mProtocolType;
        private int mRoamingProtocolType;
        private int mSupportedApnTypesBitmask;
        private int mType;
        private String mUserName;
        private int mWaitTime;

        public DataProfile build() {
            return new DataProfile(this.mProfileId, this.mApn, this.mProtocolType, this.mAuthType, this.mUserName, this.mPassword, this.mType, this.mMaxConnectionsTime, this.mMaxConnections, this.mWaitTime, this.mEnabled, this.mSupportedApnTypesBitmask, this.mRoamingProtocolType, this.mBearerBitmask, this.mMtu, this.mPersistent, this.mPreferred);
        }

        public Builder enable(boolean bl) {
            this.mEnabled = bl;
            return this;
        }

        public Builder setApn(String string2) {
            this.mApn = string2;
            return this;
        }

        public Builder setAuthType(int n) {
            this.mAuthType = n;
            return this;
        }

        public Builder setBearerBitmask(int n) {
            this.mBearerBitmask = n;
            return this;
        }

        public Builder setMaxConnections(int n) {
            this.mMaxConnections = n;
            return this;
        }

        public Builder setMaxConnectionsTime(int n) {
            this.mMaxConnectionsTime = n;
            return this;
        }

        public Builder setMtu(int n) {
            this.mMtu = n;
            return this;
        }

        public Builder setPassword(String string2) {
            this.mPassword = string2;
            return this;
        }

        public Builder setPersistent(boolean bl) {
            this.mPersistent = bl;
            return this;
        }

        public Builder setPreferred(boolean bl) {
            this.mPreferred = bl;
            return this;
        }

        public Builder setProfileId(int n) {
            this.mProfileId = n;
            return this;
        }

        public Builder setProtocolType(int n) {
            this.mProtocolType = n;
            return this;
        }

        public Builder setRoamingProtocolType(int n) {
            this.mRoamingProtocolType = n;
            return this;
        }

        public Builder setSupportedApnTypesBitmask(int n) {
            this.mSupportedApnTypesBitmask = n;
            return this;
        }

        public Builder setType(int n) {
            this.mType = n;
            return this;
        }

        public Builder setUserName(String string2) {
            this.mUserName = string2;
            return this;
        }

        public Builder setWaitTime(int n) {
            this.mWaitTime = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

}

