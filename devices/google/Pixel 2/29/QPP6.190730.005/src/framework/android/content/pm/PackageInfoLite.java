/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.PackageInfo;
import android.content.pm.VerifierInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class PackageInfoLite
implements Parcelable {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static final Parcelable.Creator<PackageInfoLite> CREATOR = new Parcelable.Creator<PackageInfoLite>(){

        @Override
        public PackageInfoLite createFromParcel(Parcel parcel) {
            return new PackageInfoLite(parcel);
        }

        public PackageInfoLite[] newArray(int n) {
            return new PackageInfoLite[n];
        }
    };
    public int baseRevisionCode;
    public int installLocation;
    public boolean multiArch;
    public String packageName;
    public int recommendedInstallLocation;
    public String[] splitNames;
    public int[] splitRevisionCodes;
    public VerifierInfo[] verifiers;
    @Deprecated
    public int versionCode;
    public int versionCodeMajor;

    public PackageInfoLite() {
    }

    private PackageInfoLite(Parcel parcel) {
        this.packageName = parcel.readString();
        this.splitNames = parcel.createStringArray();
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.baseRevisionCode = parcel.readInt();
        this.splitRevisionCodes = parcel.createIntArray();
        this.recommendedInstallLocation = parcel.readInt();
        this.installLocation = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.multiArch = bl;
        int n = parcel.readInt();
        if (n == 0) {
            this.verifiers = new VerifierInfo[0];
        } else {
            this.verifiers = new VerifierInfo[n];
            parcel.readTypedArray(this.verifiers, VerifierInfo.CREATOR);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PackageInfoLite{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.packageName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.packageName);
        parcel.writeStringArray(this.splitNames);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeInt(this.baseRevisionCode);
        parcel.writeIntArray(this.splitRevisionCodes);
        parcel.writeInt(this.recommendedInstallLocation);
        parcel.writeInt(this.installLocation);
        parcel.writeInt((int)this.multiArch);
        VerifierInfo[] arrverifierInfo = this.verifiers;
        if (arrverifierInfo != null && arrverifierInfo.length != 0) {
            parcel.writeInt(arrverifierInfo.length);
            parcel.writeTypedArray((Parcelable[])this.verifiers, n);
        } else {
            parcel.writeInt(0);
        }
    }

}

