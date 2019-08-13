/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public final class JobWorkItem
implements Parcelable {
    public static final Parcelable.Creator<JobWorkItem> CREATOR = new Parcelable.Creator<JobWorkItem>(){

        @Override
        public JobWorkItem createFromParcel(Parcel parcel) {
            return new JobWorkItem(parcel);
        }

        public JobWorkItem[] newArray(int n) {
            return new JobWorkItem[n];
        }
    };
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mDeliveryCount;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    Object mGrants;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final Intent mIntent;
    final long mNetworkDownloadBytes;
    final long mNetworkUploadBytes;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mWorkId;

    public JobWorkItem(Intent intent) {
        this.mIntent = intent;
        this.mNetworkDownloadBytes = -1L;
        this.mNetworkUploadBytes = -1L;
    }

    @Deprecated
    public JobWorkItem(Intent intent, long l) {
        this(intent, l, -1L);
    }

    public JobWorkItem(Intent intent, long l, long l2) {
        this.mIntent = intent;
        this.mNetworkDownloadBytes = l;
        this.mNetworkUploadBytes = l2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    JobWorkItem(Parcel parcel) {
        this.mIntent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
        this.mNetworkDownloadBytes = parcel.readLong();
        this.mNetworkUploadBytes = parcel.readLong();
        this.mDeliveryCount = parcel.readInt();
        this.mWorkId = parcel.readInt();
    }

    public void bumpDeliveryCount() {
        ++this.mDeliveryCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getDeliveryCount() {
        return this.mDeliveryCount;
    }

    @Deprecated
    public long getEstimatedNetworkBytes() {
        if (this.mNetworkDownloadBytes == -1L && this.mNetworkUploadBytes == -1L) {
            return -1L;
        }
        long l = this.mNetworkDownloadBytes;
        if (l == -1L) {
            return this.mNetworkUploadBytes;
        }
        long l2 = this.mNetworkUploadBytes;
        if (l2 == -1L) {
            return l;
        }
        return l + l2;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.mNetworkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.mNetworkUploadBytes;
    }

    public Object getGrants() {
        return this.mGrants;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getWorkId() {
        return this.mWorkId;
    }

    public void setGrants(Object object) {
        this.mGrants = object;
    }

    public void setWorkId(int n) {
        this.mWorkId = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("JobWorkItem{id=");
        stringBuilder.append(this.mWorkId);
        stringBuilder.append(" intent=");
        stringBuilder.append(this.mIntent);
        if (this.mNetworkDownloadBytes != -1L) {
            stringBuilder.append(" downloadBytes=");
            stringBuilder.append(this.mNetworkDownloadBytes);
        }
        if (this.mNetworkUploadBytes != -1L) {
            stringBuilder.append(" uploadBytes=");
            stringBuilder.append(this.mNetworkUploadBytes);
        }
        if (this.mDeliveryCount != 0) {
            stringBuilder.append(" dcount=");
            stringBuilder.append(this.mDeliveryCount);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mIntent != null) {
            parcel.writeInt(1);
            this.mIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mNetworkDownloadBytes);
        parcel.writeLong(this.mNetworkUploadBytes);
        parcel.writeInt(this.mDeliveryCount);
        parcel.writeInt(this.mWorkId);
    }

}

