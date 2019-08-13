/*
 * Decompiled with CFR 0.145.
 */
package android.content.rollback;

import android.annotation.SystemApi;
import android.content.pm.VersionedPackage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.IntArray;
import android.util.SparseLongArray;
import java.util.ArrayList;

@SystemApi
public final class PackageRollbackInfo
implements Parcelable {
    public static final Parcelable.Creator<PackageRollbackInfo> CREATOR = new Parcelable.Creator<PackageRollbackInfo>(){

        @Override
        public PackageRollbackInfo createFromParcel(Parcel parcel) {
            return new PackageRollbackInfo(parcel);
        }

        public PackageRollbackInfo[] newArray(int n) {
            return new PackageRollbackInfo[n];
        }
    };
    private final SparseLongArray mCeSnapshotInodes;
    private final IntArray mInstalledUsers;
    private final boolean mIsApex;
    private final IntArray mPendingBackups;
    private final ArrayList<RestoreInfo> mPendingRestores;
    private final VersionedPackage mVersionRolledBackFrom;
    private final VersionedPackage mVersionRolledBackTo;

    public PackageRollbackInfo(VersionedPackage versionedPackage, VersionedPackage versionedPackage2, IntArray intArray, ArrayList<RestoreInfo> arrayList, boolean bl, IntArray intArray2, SparseLongArray sparseLongArray) {
        this.mVersionRolledBackFrom = versionedPackage;
        this.mVersionRolledBackTo = versionedPackage2;
        this.mPendingBackups = intArray;
        this.mPendingRestores = arrayList;
        this.mIsApex = bl;
        this.mInstalledUsers = intArray2;
        this.mCeSnapshotInodes = sparseLongArray;
    }

    private PackageRollbackInfo(Parcel parcel) {
        this.mVersionRolledBackFrom = VersionedPackage.CREATOR.createFromParcel(parcel);
        this.mVersionRolledBackTo = VersionedPackage.CREATOR.createFromParcel(parcel);
        this.mIsApex = parcel.readBoolean();
        this.mPendingRestores = null;
        this.mPendingBackups = null;
        this.mInstalledUsers = null;
        this.mCeSnapshotInodes = null;
    }

    public void addPendingBackup(int n) {
        this.mPendingBackups.add(n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SparseLongArray getCeSnapshotInodes() {
        return this.mCeSnapshotInodes;
    }

    public IntArray getInstalledUsers() {
        return this.mInstalledUsers;
    }

    public String getPackageName() {
        return this.mVersionRolledBackFrom.getPackageName();
    }

    public IntArray getPendingBackups() {
        return this.mPendingBackups;
    }

    public ArrayList<RestoreInfo> getPendingRestores() {
        return this.mPendingRestores;
    }

    public RestoreInfo getRestoreInfo(int n) {
        for (RestoreInfo restoreInfo : this.mPendingRestores) {
            if (restoreInfo.userId != n) continue;
            return restoreInfo;
        }
        return null;
    }

    public VersionedPackage getVersionRolledBackFrom() {
        return this.mVersionRolledBackFrom;
    }

    public VersionedPackage getVersionRolledBackTo() {
        return this.mVersionRolledBackTo;
    }

    public boolean isApex() {
        return this.mIsApex;
    }

    public void putCeSnapshotInode(int n, long l) {
        this.mCeSnapshotInodes.put(n, l);
    }

    public void removePendingBackup(int n) {
        if ((n = this.mPendingBackups.indexOf(n)) != -1) {
            this.mPendingBackups.remove(n);
        }
    }

    public void removePendingRestoreInfo(int n) {
        this.removeRestoreInfo(this.getRestoreInfo(n));
    }

    public void removeRestoreInfo(RestoreInfo restoreInfo) {
        this.mPendingRestores.remove(restoreInfo);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mVersionRolledBackFrom.writeToParcel(parcel, n);
        this.mVersionRolledBackTo.writeToParcel(parcel, n);
        parcel.writeBoolean(this.mIsApex);
    }

    public static class RestoreInfo {
        public final int appId;
        public final String seInfo;
        public final int userId;

        public RestoreInfo(int n, int n2, String string2) {
            this.userId = n;
            this.appId = n2;
            this.seInfo = string2;
        }
    }

}

