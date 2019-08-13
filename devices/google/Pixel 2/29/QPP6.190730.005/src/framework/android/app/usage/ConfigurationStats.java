/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;

public final class ConfigurationStats
implements Parcelable {
    public static final Parcelable.Creator<ConfigurationStats> CREATOR = new Parcelable.Creator<ConfigurationStats>(){

        @Override
        public ConfigurationStats createFromParcel(Parcel parcel) {
            ConfigurationStats configurationStats = new ConfigurationStats();
            if (parcel.readInt() != 0) {
                configurationStats.mConfiguration = Configuration.CREATOR.createFromParcel(parcel);
            }
            configurationStats.mBeginTimeStamp = parcel.readLong();
            configurationStats.mEndTimeStamp = parcel.readLong();
            configurationStats.mLastTimeActive = parcel.readLong();
            configurationStats.mTotalTimeActive = parcel.readLong();
            configurationStats.mActivationCount = parcel.readInt();
            return configurationStats;
        }

        public ConfigurationStats[] newArray(int n) {
            return new ConfigurationStats[n];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int mActivationCount;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mBeginTimeStamp;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public Configuration mConfiguration;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mEndTimeStamp;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mLastTimeActive;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public long mTotalTimeActive;

    public ConfigurationStats() {
    }

    public ConfigurationStats(ConfigurationStats configurationStats) {
        this.mConfiguration = configurationStats.mConfiguration;
        this.mBeginTimeStamp = configurationStats.mBeginTimeStamp;
        this.mEndTimeStamp = configurationStats.mEndTimeStamp;
        this.mLastTimeActive = configurationStats.mLastTimeActive;
        this.mTotalTimeActive = configurationStats.mTotalTimeActive;
        this.mActivationCount = configurationStats.mActivationCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getActivationCount() {
        return this.mActivationCount;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeActive() {
        return this.mLastTimeActive;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getTotalTimeActive() {
        return this.mTotalTimeActive;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mConfiguration != null) {
            parcel.writeInt(1);
            this.mConfiguration.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastTimeActive);
        parcel.writeLong(this.mTotalTimeActive);
        parcel.writeInt(this.mActivationCount);
    }

}

