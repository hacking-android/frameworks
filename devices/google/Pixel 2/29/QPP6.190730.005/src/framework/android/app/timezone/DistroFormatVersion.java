/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.app.timezone.Utils;
import android.os.Parcel;
import android.os.Parcelable;

public final class DistroFormatVersion
implements Parcelable {
    public static final Parcelable.Creator<DistroFormatVersion> CREATOR = new Parcelable.Creator<DistroFormatVersion>(){

        @Override
        public DistroFormatVersion createFromParcel(Parcel parcel) {
            return new DistroFormatVersion(parcel.readInt(), parcel.readInt());
        }

        public DistroFormatVersion[] newArray(int n) {
            return new DistroFormatVersion[n];
        }
    };
    private final int mMajorVersion;
    private final int mMinorVersion;

    public DistroFormatVersion(int n, int n2) {
        this.mMajorVersion = Utils.validateVersion("major", n);
        this.mMinorVersion = Utils.validateVersion("minor", n2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (DistroFormatVersion)object;
            if (this.mMajorVersion != ((DistroFormatVersion)object).mMajorVersion) {
                return false;
            }
            if (this.mMinorVersion != ((DistroFormatVersion)object).mMinorVersion) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getMajorVersion() {
        return this.mMajorVersion;
    }

    public int getMinorVersion() {
        return this.mMinorVersion;
    }

    public int hashCode() {
        return this.mMajorVersion * 31 + this.mMinorVersion;
    }

    public boolean supports(DistroFormatVersion distroFormatVersion) {
        boolean bl = this.mMajorVersion == distroFormatVersion.mMajorVersion && this.mMinorVersion <= distroFormatVersion.mMinorVersion;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DistroFormatVersion{mMajorVersion=");
        stringBuilder.append(this.mMajorVersion);
        stringBuilder.append(", mMinorVersion=");
        stringBuilder.append(this.mMinorVersion);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMajorVersion);
        parcel.writeInt(this.mMinorVersion);
    }

}

