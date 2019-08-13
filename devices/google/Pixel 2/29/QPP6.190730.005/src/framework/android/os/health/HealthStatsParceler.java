/*
 * Decompiled with CFR 0.145.
 */
package android.os.health;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.health.HealthStats;
import android.os.health.HealthStatsWriter;

public class HealthStatsParceler
implements Parcelable {
    public static final Parcelable.Creator<HealthStatsParceler> CREATOR = new Parcelable.Creator<HealthStatsParceler>(){

        @Override
        public HealthStatsParceler createFromParcel(Parcel parcel) {
            return new HealthStatsParceler(parcel);
        }

        public HealthStatsParceler[] newArray(int n) {
            return new HealthStatsParceler[n];
        }
    };
    private HealthStats mHealthStats;
    private HealthStatsWriter mWriter;

    public HealthStatsParceler(Parcel parcel) {
        this.mHealthStats = new HealthStats(parcel);
    }

    public HealthStatsParceler(HealthStatsWriter healthStatsWriter) {
        this.mWriter = healthStatsWriter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public HealthStats getHealthStats() {
        if (this.mWriter != null) {
            Parcel parcel = Parcel.obtain();
            this.mWriter.flattenToParcel(parcel);
            parcel.setDataPosition(0);
            this.mHealthStats = new HealthStats(parcel);
            parcel.recycle();
        }
        return this.mHealthStats;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        HealthStatsWriter healthStatsWriter = this.mWriter;
        if (healthStatsWriter != null) {
            healthStatsWriter.flattenToParcel(parcel);
            return;
        }
        throw new RuntimeException("Can not re-parcel HealthStatsParceler that was constructed from a Parcel");
    }

}

