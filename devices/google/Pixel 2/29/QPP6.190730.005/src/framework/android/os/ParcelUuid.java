/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.UUID;

public final class ParcelUuid
implements Parcelable {
    public static final Parcelable.Creator<ParcelUuid> CREATOR = new Parcelable.Creator<ParcelUuid>(){

        @UnsupportedAppUsage
        @Override
        public ParcelUuid createFromParcel(Parcel parcel) {
            return new ParcelUuid(new UUID(parcel.readLong(), parcel.readLong()));
        }

        public ParcelUuid[] newArray(int n) {
            return new ParcelUuid[n];
        }
    };
    private final UUID mUuid;

    public ParcelUuid(UUID uUID) {
        this.mUuid = uUID;
    }

    public static ParcelUuid fromString(String string2) {
        return new ParcelUuid(UUID.fromString(string2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof ParcelUuid)) {
            return false;
        }
        object = (ParcelUuid)object;
        return this.mUuid.equals(((ParcelUuid)object).mUuid);
    }

    public UUID getUuid() {
        return this.mUuid;
    }

    public int hashCode() {
        return this.mUuid.hashCode();
    }

    public String toString() {
        return this.mUuid.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mUuid.getMostSignificantBits());
        parcel.writeLong(this.mUuid.getLeastSignificantBits());
    }

}

