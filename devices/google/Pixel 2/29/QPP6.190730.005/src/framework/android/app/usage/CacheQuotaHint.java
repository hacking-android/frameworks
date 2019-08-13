/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.SystemApi;
import android.app.usage.UsageStats;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

@SystemApi
public final class CacheQuotaHint
implements Parcelable {
    public static final Parcelable.Creator<CacheQuotaHint> CREATOR = new Parcelable.Creator<CacheQuotaHint>(){

        @Override
        public CacheQuotaHint createFromParcel(Parcel parcel) {
            return new Builder().setVolumeUuid(parcel.readString()).setUid(parcel.readInt()).setQuota(parcel.readLong()).setUsageStats((UsageStats)parcel.readParcelable(UsageStats.class.getClassLoader())).build();
        }

        public CacheQuotaHint[] newArray(int n) {
            return new CacheQuotaHint[n];
        }
    };
    public static final long QUOTA_NOT_SET = -1L;
    private final long mQuota;
    private final int mUid;
    private final UsageStats mUsageStats;
    private final String mUuid;

    public CacheQuotaHint(Builder builder) {
        this.mUuid = builder.mUuid;
        this.mUid = builder.mUid;
        this.mUsageStats = builder.mUsageStats;
        this.mQuota = builder.mQuota;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof CacheQuotaHint;
        boolean bl2 = false;
        if (bl) {
            object = (CacheQuotaHint)object;
            if (Objects.equals(this.mUuid, ((CacheQuotaHint)object).mUuid) && Objects.equals(this.mUsageStats, ((CacheQuotaHint)object).mUsageStats) && this.mUid == ((CacheQuotaHint)object).mUid && this.mQuota == ((CacheQuotaHint)object).mQuota) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public long getQuota() {
        return this.mQuota;
    }

    public int getUid() {
        return this.mUid;
    }

    public UsageStats getUsageStats() {
        return this.mUsageStats;
    }

    public String getVolumeUuid() {
        return this.mUuid;
    }

    public int hashCode() {
        return Objects.hash(this.mUuid, this.mUid, this.mUsageStats, this.mQuota);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mUuid);
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mQuota);
        parcel.writeParcelable(this.mUsageStats, 0);
    }

    public static final class Builder {
        private long mQuota;
        private int mUid;
        private UsageStats mUsageStats;
        private String mUuid;

        public Builder() {
        }

        public Builder(CacheQuotaHint cacheQuotaHint) {
            this.setVolumeUuid(cacheQuotaHint.getVolumeUuid());
            this.setUid(cacheQuotaHint.getUid());
            this.setUsageStats(cacheQuotaHint.getUsageStats());
            this.setQuota(cacheQuotaHint.getQuota());
        }

        public CacheQuotaHint build() {
            return new CacheQuotaHint(this);
        }

        public Builder setQuota(long l) {
            boolean bl = l >= -1L;
            Preconditions.checkArgument(bl);
            this.mQuota = l;
            return this;
        }

        public Builder setUid(int n) {
            Preconditions.checkArgumentNonnegative(n, "Proposed uid was negative.");
            this.mUid = n;
            return this;
        }

        public Builder setUsageStats(UsageStats usageStats) {
            this.mUsageStats = usageStats;
            return this;
        }

        public Builder setVolumeUuid(String string2) {
            this.mUuid = string2;
            return this;
        }
    }

}

