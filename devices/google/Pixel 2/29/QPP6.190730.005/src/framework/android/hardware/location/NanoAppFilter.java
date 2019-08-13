/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.hardware.location.NanoAppInstanceInfo;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
@Deprecated
public class NanoAppFilter
implements Parcelable {
    public static final int APP_ANY = -1;
    public static final Parcelable.Creator<NanoAppFilter> CREATOR = new Parcelable.Creator<NanoAppFilter>(){

        @Override
        public NanoAppFilter createFromParcel(Parcel parcel) {
            return new NanoAppFilter(parcel);
        }

        public NanoAppFilter[] newArray(int n) {
            return new NanoAppFilter[n];
        }
    };
    public static final int FLAGS_VERSION_ANY = -1;
    public static final int FLAGS_VERSION_GREAT_THAN = 2;
    public static final int FLAGS_VERSION_LESS_THAN = 4;
    public static final int FLAGS_VERSION_STRICTLY_EQUAL = 8;
    public static final int HUB_ANY = -1;
    private static final String TAG = "NanoAppFilter";
    public static final int VENDOR_ANY = -1;
    private long mAppId;
    private long mAppIdVendorMask;
    private int mAppVersion;
    private int mContextHubId = -1;
    private int mVersionRestrictionMask;

    public NanoAppFilter(long l, int n, int n2, long l2) {
        this.mAppId = l;
        this.mAppVersion = n;
        this.mVersionRestrictionMask = n2;
        this.mAppIdVendorMask = l2;
    }

    private NanoAppFilter(Parcel parcel) {
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mVersionRestrictionMask = parcel.readInt();
        this.mAppIdVendorMask = parcel.readLong();
    }

    private boolean versionsMatch(int n, int n2, int n3) {
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean testMatch(NanoAppInstanceInfo nanoAppInstanceInfo) {
        boolean bl = !(this.mContextHubId != -1 && nanoAppInstanceInfo.getContexthubId() != this.mContextHubId || this.mAppId != -1L && nanoAppInstanceInfo.getAppId() != this.mAppId || !this.versionsMatch(this.mVersionRestrictionMask, this.mAppVersion, nanoAppInstanceInfo.getAppVersion()));
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("nanoAppId: 0x");
        stringBuilder.append(Long.toHexString(this.mAppId));
        stringBuilder.append(", nanoAppVersion: 0x");
        stringBuilder.append(Integer.toHexString(this.mAppVersion));
        stringBuilder.append(", versionMask: ");
        stringBuilder.append(this.mVersionRestrictionMask);
        stringBuilder.append(", vendorMask: ");
        stringBuilder.append(this.mAppIdVendorMask);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mVersionRestrictionMask);
        parcel.writeLong(this.mAppIdVendorMask);
    }

}

