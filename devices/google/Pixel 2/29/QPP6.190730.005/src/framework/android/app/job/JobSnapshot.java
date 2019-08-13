/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.app.job.JobInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class JobSnapshot
implements Parcelable {
    public static final Parcelable.Creator<JobSnapshot> CREATOR = new Parcelable.Creator<JobSnapshot>(){

        @Override
        public JobSnapshot createFromParcel(Parcel parcel) {
            return new JobSnapshot(parcel);
        }

        public JobSnapshot[] newArray(int n) {
            return new JobSnapshot[n];
        }
    };
    private final boolean mIsRunnable;
    private final JobInfo mJob;
    private final int mSatisfiedConstraints;

    public JobSnapshot(JobInfo jobInfo, int n, boolean bl) {
        this.mJob = jobInfo;
        this.mSatisfiedConstraints = n;
        this.mIsRunnable = bl;
    }

    public JobSnapshot(Parcel parcel) {
        this.mJob = JobInfo.CREATOR.createFromParcel(parcel);
        this.mSatisfiedConstraints = parcel.readInt();
        this.mIsRunnable = parcel.readBoolean();
    }

    private boolean satisfied(int n) {
        boolean bl = (this.mSatisfiedConstraints & n) != 0;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public JobInfo getJobInfo() {
        return this.mJob;
    }

    public boolean isBatteryNotLowSatisfied() {
        boolean bl = !this.mJob.isRequireBatteryNotLow() || this.satisfied(2);
        return bl;
    }

    public boolean isChargingSatisfied() {
        boolean bl;
        block0 : {
            boolean bl2 = this.mJob.isRequireCharging();
            bl = true;
            if (!bl2 || this.satisfied(1)) break block0;
            bl = false;
        }
        return bl;
    }

    public boolean isRequireDeviceIdleSatisfied() {
        boolean bl = !this.mJob.isRequireDeviceIdle() || this.satisfied(4);
        return bl;
    }

    public boolean isRequireStorageNotLowSatisfied() {
        boolean bl = !this.mJob.isRequireStorageNotLow() || this.satisfied(8);
        return bl;
    }

    public boolean isRunnable() {
        return this.mIsRunnable;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mJob.writeToParcel(parcel, n);
        parcel.writeInt(this.mSatisfiedConstraints);
        parcel.writeBoolean(this.mIsRunnable);
    }

}

