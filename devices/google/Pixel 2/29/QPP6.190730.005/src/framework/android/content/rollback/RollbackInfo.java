/*
 * Decompiled with CFR 0.145.
 */
package android.content.rollback;

import android.annotation.SystemApi;
import android.content.pm.VersionedPackage;
import android.content.rollback.PackageRollbackInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class RollbackInfo
implements Parcelable {
    public static final Parcelable.Creator<RollbackInfo> CREATOR = new Parcelable.Creator<RollbackInfo>(){

        @Override
        public RollbackInfo createFromParcel(Parcel parcel) {
            return new RollbackInfo(parcel);
        }

        public RollbackInfo[] newArray(int n) {
            return new RollbackInfo[n];
        }
    };
    private final List<VersionedPackage> mCausePackages;
    private int mCommittedSessionId;
    private final boolean mIsStaged;
    private final List<PackageRollbackInfo> mPackages;
    private final int mRollbackId;

    public RollbackInfo(int n, List<PackageRollbackInfo> list, boolean bl, List<VersionedPackage> list2, int n2) {
        this.mRollbackId = n;
        this.mPackages = list;
        this.mIsStaged = bl;
        this.mCausePackages = list2;
        this.mCommittedSessionId = n2;
    }

    private RollbackInfo(Parcel parcel) {
        this.mRollbackId = parcel.readInt();
        this.mPackages = parcel.createTypedArrayList(PackageRollbackInfo.CREATOR);
        this.mIsStaged = parcel.readBoolean();
        this.mCausePackages = parcel.createTypedArrayList(VersionedPackage.CREATOR);
        this.mCommittedSessionId = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<VersionedPackage> getCausePackages() {
        return this.mCausePackages;
    }

    public int getCommittedSessionId() {
        return this.mCommittedSessionId;
    }

    public List<PackageRollbackInfo> getPackages() {
        return this.mPackages;
    }

    public int getRollbackId() {
        return this.mRollbackId;
    }

    public boolean isStaged() {
        return this.mIsStaged;
    }

    public void setCommittedSessionId(int n) {
        this.mCommittedSessionId = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRollbackId);
        parcel.writeTypedList(this.mPackages);
        parcel.writeBoolean(this.mIsStaged);
        parcel.writeTypedList(this.mCausePackages);
        parcel.writeInt(this.mCommittedSessionId);
    }

}

