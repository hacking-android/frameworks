/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import java.util.Objects;

@Deprecated
public class PackageStats
implements Parcelable {
    public static final Parcelable.Creator<PackageStats> CREATOR = new Parcelable.Creator<PackageStats>(){

        @Override
        public PackageStats createFromParcel(Parcel parcel) {
            return new PackageStats(parcel);
        }

        public PackageStats[] newArray(int n) {
            return new PackageStats[n];
        }
    };
    public long cacheSize;
    public long codeSize;
    public long dataSize;
    public long externalCacheSize;
    public long externalCodeSize;
    public long externalDataSize;
    public long externalMediaSize;
    public long externalObbSize;
    public String packageName;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int userHandle;

    public PackageStats(PackageStats packageStats) {
        this.packageName = packageStats.packageName;
        this.userHandle = packageStats.userHandle;
        this.codeSize = packageStats.codeSize;
        this.dataSize = packageStats.dataSize;
        this.cacheSize = packageStats.cacheSize;
        this.externalCodeSize = packageStats.externalCodeSize;
        this.externalDataSize = packageStats.externalDataSize;
        this.externalCacheSize = packageStats.externalCacheSize;
        this.externalMediaSize = packageStats.externalMediaSize;
        this.externalObbSize = packageStats.externalObbSize;
    }

    public PackageStats(Parcel parcel) {
        this.packageName = parcel.readString();
        this.userHandle = parcel.readInt();
        this.codeSize = parcel.readLong();
        this.dataSize = parcel.readLong();
        this.cacheSize = parcel.readLong();
        this.externalCodeSize = parcel.readLong();
        this.externalDataSize = parcel.readLong();
        this.externalCacheSize = parcel.readLong();
        this.externalMediaSize = parcel.readLong();
        this.externalObbSize = parcel.readLong();
    }

    public PackageStats(String string2) {
        this.packageName = string2;
        this.userHandle = UserHandle.myUserId();
    }

    public PackageStats(String string2, int n) {
        this.packageName = string2;
        this.userHandle = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof PackageStats;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (PackageStats)object;
        bl = bl2;
        if (TextUtils.equals(this.packageName, ((PackageStats)object).packageName)) {
            bl = bl2;
            if (this.userHandle == ((PackageStats)object).userHandle) {
                bl = bl2;
                if (this.codeSize == ((PackageStats)object).codeSize) {
                    bl = bl2;
                    if (this.dataSize == ((PackageStats)object).dataSize) {
                        bl = bl2;
                        if (this.cacheSize == ((PackageStats)object).cacheSize) {
                            bl = bl2;
                            if (this.externalCodeSize == ((PackageStats)object).externalCodeSize) {
                                bl = bl2;
                                if (this.externalDataSize == ((PackageStats)object).externalDataSize) {
                                    bl = bl2;
                                    if (this.externalCacheSize == ((PackageStats)object).externalCacheSize) {
                                        bl = bl2;
                                        if (this.externalMediaSize == ((PackageStats)object).externalMediaSize) {
                                            bl = bl2;
                                            if (this.externalObbSize == ((PackageStats)object).externalObbSize) {
                                                bl = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.packageName, this.userHandle, this.codeSize, this.dataSize, this.cacheSize, this.externalCodeSize, this.externalDataSize, this.externalCacheSize, this.externalMediaSize, this.externalObbSize);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PackageStats{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.packageName);
        if (this.codeSize != 0L) {
            stringBuilder.append(" code=");
            stringBuilder.append(this.codeSize);
        }
        if (this.dataSize != 0L) {
            stringBuilder.append(" data=");
            stringBuilder.append(this.dataSize);
        }
        if (this.cacheSize != 0L) {
            stringBuilder.append(" cache=");
            stringBuilder.append(this.cacheSize);
        }
        if (this.externalCodeSize != 0L) {
            stringBuilder.append(" extCode=");
            stringBuilder.append(this.externalCodeSize);
        }
        if (this.externalDataSize != 0L) {
            stringBuilder.append(" extData=");
            stringBuilder.append(this.externalDataSize);
        }
        if (this.externalCacheSize != 0L) {
            stringBuilder.append(" extCache=");
            stringBuilder.append(this.externalCacheSize);
        }
        if (this.externalMediaSize != 0L) {
            stringBuilder.append(" media=");
            stringBuilder.append(this.externalMediaSize);
        }
        if (this.externalObbSize != 0L) {
            stringBuilder.append(" obb=");
            stringBuilder.append(this.externalObbSize);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.packageName);
        parcel.writeInt(this.userHandle);
        parcel.writeLong(this.codeSize);
        parcel.writeLong(this.dataSize);
        parcel.writeLong(this.cacheSize);
        parcel.writeLong(this.externalCodeSize);
        parcel.writeLong(this.externalDataSize);
        parcel.writeLong(this.externalCacheSize);
        parcel.writeLong(this.externalMediaSize);
        parcel.writeLong(this.externalObbSize);
    }

}

