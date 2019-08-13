/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class PackageInfo
implements Parcelable {
    public static final Parcelable.Creator<PackageInfo> CREATOR = new Parcelable.Creator<PackageInfo>(){

        @Override
        public PackageInfo createFromParcel(Parcel parcel) {
            return new PackageInfo(parcel);
        }

        public PackageInfo[] newArray(int n) {
            return new PackageInfo[n];
        }
    };
    public static final int INSTALL_LOCATION_AUTO = 0;
    public static final int INSTALL_LOCATION_INTERNAL_ONLY = 1;
    public static final int INSTALL_LOCATION_PREFER_EXTERNAL = 2;
    @UnsupportedAppUsage
    public static final int INSTALL_LOCATION_UNSPECIFIED = -1;
    public static final int REQUESTED_PERMISSION_GRANTED = 2;
    public static final int REQUESTED_PERMISSION_REQUIRED = 1;
    public ActivityInfo[] activities;
    public ApplicationInfo applicationInfo;
    public int baseRevisionCode;
    public int compileSdkVersion;
    public String compileSdkVersionCodename;
    public ConfigurationInfo[] configPreferences;
    @UnsupportedAppUsage
    public boolean coreApp;
    public FeatureGroupInfo[] featureGroups;
    public long firstInstallTime;
    public int[] gids;
    public int installLocation;
    public InstrumentationInfo[] instrumentation;
    public boolean isApex;
    public boolean isStub;
    public long lastUpdateTime;
    boolean mOverlayIsStatic;
    public String overlayCategory;
    public int overlayPriority;
    @UnsupportedAppUsage
    public String overlayTarget;
    public String packageName;
    public PermissionInfo[] permissions;
    public ProviderInfo[] providers;
    public ActivityInfo[] receivers;
    public FeatureInfo[] reqFeatures;
    public String[] requestedPermissions;
    public int[] requestedPermissionsFlags;
    public String requiredAccountType;
    public boolean requiredForAllUsers;
    public String restrictedAccountType;
    public ServiceInfo[] services;
    public String sharedUserId;
    public int sharedUserLabel;
    @Deprecated
    public Signature[] signatures;
    public SigningInfo signingInfo;
    public String[] splitNames;
    public int[] splitRevisionCodes;
    public String targetOverlayableName;
    @Deprecated
    public int versionCode;
    public int versionCodeMajor;
    public String versionName;

    public PackageInfo() {
        this.installLocation = 1;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private PackageInfo(Parcel object) {
        boolean bl = true;
        this.installLocation = 1;
        this.packageName = ((Parcel)object).readString();
        this.splitNames = ((Parcel)object).createStringArray();
        this.versionCode = ((Parcel)object).readInt();
        this.versionCodeMajor = ((Parcel)object).readInt();
        this.versionName = ((Parcel)object).readString();
        this.baseRevisionCode = ((Parcel)object).readInt();
        this.splitRevisionCodes = ((Parcel)object).createIntArray();
        this.sharedUserId = ((Parcel)object).readString();
        this.sharedUserLabel = ((Parcel)object).readInt();
        if (((Parcel)object).readInt() != 0) {
            this.applicationInfo = ApplicationInfo.CREATOR.createFromParcel((Parcel)object);
        }
        this.firstInstallTime = ((Parcel)object).readLong();
        this.lastUpdateTime = ((Parcel)object).readLong();
        this.gids = ((Parcel)object).createIntArray();
        this.activities = ((Parcel)object).createTypedArray(ActivityInfo.CREATOR);
        this.receivers = ((Parcel)object).createTypedArray(ActivityInfo.CREATOR);
        this.services = ((Parcel)object).createTypedArray(ServiceInfo.CREATOR);
        this.providers = ((Parcel)object).createTypedArray(ProviderInfo.CREATOR);
        this.instrumentation = ((Parcel)object).createTypedArray(InstrumentationInfo.CREATOR);
        this.permissions = ((Parcel)object).createTypedArray(PermissionInfo.CREATOR);
        this.requestedPermissions = ((Parcel)object).createStringArray();
        this.requestedPermissionsFlags = ((Parcel)object).createIntArray();
        this.signatures = ((Parcel)object).createTypedArray(Signature.CREATOR);
        this.configPreferences = ((Parcel)object).createTypedArray(ConfigurationInfo.CREATOR);
        this.reqFeatures = ((Parcel)object).createTypedArray(FeatureInfo.CREATOR);
        this.featureGroups = ((Parcel)object).createTypedArray(FeatureGroupInfo.CREATOR);
        this.installLocation = ((Parcel)object).readInt();
        boolean bl2 = ((Parcel)object).readInt() != 0;
        this.isStub = bl2;
        bl2 = ((Parcel)object).readInt() != 0;
        this.coreApp = bl2;
        bl2 = ((Parcel)object).readInt() != 0 ? bl : false;
        this.requiredForAllUsers = bl2;
        this.restrictedAccountType = ((Parcel)object).readString();
        this.requiredAccountType = ((Parcel)object).readString();
        this.overlayTarget = ((Parcel)object).readString();
        this.overlayCategory = ((Parcel)object).readString();
        this.overlayPriority = ((Parcel)object).readInt();
        this.mOverlayIsStatic = ((Parcel)object).readBoolean();
        this.compileSdkVersion = ((Parcel)object).readInt();
        this.compileSdkVersionCodename = ((Parcel)object).readString();
        if (((Parcel)object).readInt() != 0) {
            this.signingInfo = SigningInfo.CREATOR.createFromParcel((Parcel)object);
        }
        this.isApex = ((Parcel)object).readBoolean();
        object = this.applicationInfo;
        if (object != null) {
            this.propagateApplicationInfo((ApplicationInfo)object, this.activities);
            this.propagateApplicationInfo(this.applicationInfo, this.receivers);
            this.propagateApplicationInfo(this.applicationInfo, this.services);
            this.propagateApplicationInfo(this.applicationInfo, this.providers);
        }
    }

    public static long composeLongVersionCode(int n, int n2) {
        return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
    }

    private void propagateApplicationInfo(ApplicationInfo applicationInfo, ComponentInfo[] arrcomponentInfo) {
        if (arrcomponentInfo != null) {
            int n = arrcomponentInfo.length;
            for (int i = 0; i < n; ++i) {
                arrcomponentInfo[i].applicationInfo = applicationInfo;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLongVersionCode() {
        return PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    public boolean isOverlayPackage() {
        boolean bl = this.overlayTarget != null;
        return bl;
    }

    public boolean isStaticOverlayPackage() {
        boolean bl = this.overlayTarget != null && this.mOverlayIsStatic;
        return bl;
    }

    public void setLongVersionCode(long l) {
        this.versionCodeMajor = (int)(l >> 32);
        this.versionCode = (int)l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PackageInfo{");
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
        parcel.writeString(this.versionName);
        parcel.writeInt(this.baseRevisionCode);
        parcel.writeIntArray(this.splitRevisionCodes);
        parcel.writeString(this.sharedUserId);
        parcel.writeInt(this.sharedUserLabel);
        if (this.applicationInfo != null) {
            parcel.writeInt(1);
            this.applicationInfo.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.firstInstallTime);
        parcel.writeLong(this.lastUpdateTime);
        parcel.writeIntArray(this.gids);
        parcel.writeTypedArray((Parcelable[])this.activities, n | 2);
        parcel.writeTypedArray((Parcelable[])this.receivers, n | 2);
        parcel.writeTypedArray((Parcelable[])this.services, n | 2);
        parcel.writeTypedArray((Parcelable[])this.providers, n | 2);
        parcel.writeTypedArray((Parcelable[])this.instrumentation, n);
        parcel.writeTypedArray((Parcelable[])this.permissions, n);
        parcel.writeStringArray(this.requestedPermissions);
        parcel.writeIntArray(this.requestedPermissionsFlags);
        parcel.writeTypedArray((Parcelable[])this.signatures, n);
        parcel.writeTypedArray((Parcelable[])this.configPreferences, n);
        parcel.writeTypedArray((Parcelable[])this.reqFeatures, n);
        parcel.writeTypedArray((Parcelable[])this.featureGroups, n);
        parcel.writeInt(this.installLocation);
        parcel.writeInt((int)this.isStub);
        parcel.writeInt((int)this.coreApp);
        parcel.writeInt((int)this.requiredForAllUsers);
        parcel.writeString(this.restrictedAccountType);
        parcel.writeString(this.requiredAccountType);
        parcel.writeString(this.overlayTarget);
        parcel.writeString(this.overlayCategory);
        parcel.writeInt(this.overlayPriority);
        parcel.writeBoolean(this.mOverlayIsStatic);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString(this.compileSdkVersionCodename);
        if (this.signingInfo != null) {
            parcel.writeInt(1);
            this.signingInfo.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.isApex);
    }

}

