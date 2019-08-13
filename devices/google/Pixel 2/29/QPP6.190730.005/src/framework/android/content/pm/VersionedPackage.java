/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class VersionedPackage
implements Parcelable {
    public static final Parcelable.Creator<VersionedPackage> CREATOR = new Parcelable.Creator<VersionedPackage>(){

        @Override
        public VersionedPackage createFromParcel(Parcel parcel) {
            return new VersionedPackage(parcel);
        }

        public VersionedPackage[] newArray(int n) {
            return new VersionedPackage[n];
        }
    };
    private final String mPackageName;
    private final long mVersionCode;

    private VersionedPackage(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mVersionCode = parcel.readLong();
    }

    public VersionedPackage(String string2, int n) {
        this.mPackageName = string2;
        this.mVersionCode = n;
    }

    public VersionedPackage(String string2, long l) {
        this.mPackageName = string2;
        this.mVersionCode = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getLongVersionCode() {
        return this.mVersionCode;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    @Deprecated
    public int getVersionCode() {
        return (int)(this.mVersionCode & Integer.MAX_VALUE);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VersionedPackage[");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append("/");
        stringBuilder.append(this.mVersionCode);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mVersionCode);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VersionCode {
    }

}

