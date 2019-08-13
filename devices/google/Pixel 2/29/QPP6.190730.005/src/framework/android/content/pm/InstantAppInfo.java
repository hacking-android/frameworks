/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class InstantAppInfo
implements Parcelable {
    public static final Parcelable.Creator<InstantAppInfo> CREATOR = new Parcelable.Creator<InstantAppInfo>(){

        @Override
        public InstantAppInfo createFromParcel(Parcel parcel) {
            return new InstantAppInfo(parcel);
        }

        public InstantAppInfo[] newArray(int n) {
            return new InstantAppInfo[0];
        }
    };
    private final ApplicationInfo mApplicationInfo;
    private final String[] mGrantedPermissions;
    private final CharSequence mLabelText;
    private final String mPackageName;
    private final String[] mRequestedPermissions;

    public InstantAppInfo(ApplicationInfo applicationInfo, String[] arrstring, String[] arrstring2) {
        this.mApplicationInfo = applicationInfo;
        this.mPackageName = null;
        this.mLabelText = null;
        this.mRequestedPermissions = arrstring;
        this.mGrantedPermissions = arrstring2;
    }

    private InstantAppInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mLabelText = parcel.readCharSequence();
        this.mRequestedPermissions = parcel.readStringArray();
        this.mGrantedPermissions = parcel.createStringArray();
        this.mApplicationInfo = (ApplicationInfo)parcel.readParcelable(null);
    }

    public InstantAppInfo(String string2, CharSequence charSequence, String[] arrstring, String[] arrstring2) {
        this.mApplicationInfo = null;
        this.mPackageName = string2;
        this.mLabelText = charSequence;
        this.mRequestedPermissions = arrstring;
        this.mGrantedPermissions = arrstring2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public String[] getGrantedPermissions() {
        return this.mGrantedPermissions;
    }

    public String getPackageName() {
        ApplicationInfo applicationInfo = this.mApplicationInfo;
        if (applicationInfo != null) {
            return applicationInfo.packageName;
        }
        return this.mPackageName;
    }

    public String[] getRequestedPermissions() {
        return this.mRequestedPermissions;
    }

    public Drawable loadIcon(PackageManager packageManager) {
        ApplicationInfo applicationInfo = this.mApplicationInfo;
        if (applicationInfo != null) {
            return applicationInfo.loadIcon(packageManager);
        }
        return packageManager.getInstantAppIcon(this.mPackageName);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        ApplicationInfo applicationInfo = this.mApplicationInfo;
        if (applicationInfo != null) {
            return applicationInfo.loadLabel(packageManager);
        }
        return this.mLabelText;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeCharSequence(this.mLabelText);
        parcel.writeStringArray(this.mRequestedPermissions);
        parcel.writeStringArray(this.mGrantedPermissions);
        parcel.writeParcelable(this.mApplicationInfo, n);
    }

}

