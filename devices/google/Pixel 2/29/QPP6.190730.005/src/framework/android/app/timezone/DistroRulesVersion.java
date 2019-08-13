/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

import android.app.timezone.Utils;
import android.os.Parcel;
import android.os.Parcelable;

public final class DistroRulesVersion
implements Parcelable {
    public static final Parcelable.Creator<DistroRulesVersion> CREATOR = new Parcelable.Creator<DistroRulesVersion>(){

        @Override
        public DistroRulesVersion createFromParcel(Parcel parcel) {
            return new DistroRulesVersion(parcel.readString(), parcel.readInt());
        }

        public DistroRulesVersion[] newArray(int n) {
            return new DistroRulesVersion[n];
        }
    };
    private final int mRevision;
    private final String mRulesVersion;

    public DistroRulesVersion(String string2, int n) {
        this.mRulesVersion = Utils.validateRulesVersion("rulesVersion", string2);
        this.mRevision = Utils.validateVersion("revision", n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (DistroRulesVersion)object;
            if (this.mRevision != ((DistroRulesVersion)object).mRevision) {
                return false;
            }
            return this.mRulesVersion.equals(((DistroRulesVersion)object).mRulesVersion);
        }
        return false;
    }

    public int getRevision() {
        return this.mRevision;
    }

    public String getRulesVersion() {
        return this.mRulesVersion;
    }

    public int hashCode() {
        return this.mRulesVersion.hashCode() * 31 + this.mRevision;
    }

    public boolean isOlderThan(DistroRulesVersion distroRulesVersion) {
        int n = this.mRulesVersion.compareTo(distroRulesVersion.mRulesVersion);
        boolean bl = true;
        if (n < 0) {
            return true;
        }
        if (n > 0) {
            return false;
        }
        if (this.mRevision >= distroRulesVersion.mRevision) {
            bl = false;
        }
        return bl;
    }

    public String toDumpString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mRulesVersion);
        stringBuilder.append(",");
        stringBuilder.append(this.mRevision);
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DistroRulesVersion{mRulesVersion='");
        stringBuilder.append(this.mRulesVersion);
        stringBuilder.append('\'');
        stringBuilder.append(", mRevision='");
        stringBuilder.append(this.mRevision);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mRulesVersion);
        parcel.writeInt(this.mRevision);
    }

}

