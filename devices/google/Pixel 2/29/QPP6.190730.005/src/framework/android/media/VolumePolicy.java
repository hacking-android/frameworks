/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class VolumePolicy
implements Parcelable {
    public static final int A11Y_MODE_INDEPENDENT_A11Y_VOLUME = 1;
    public static final int A11Y_MODE_MEDIA_A11Y_VOLUME = 0;
    public static final Parcelable.Creator<VolumePolicy> CREATOR;
    public static final VolumePolicy DEFAULT;
    public final boolean doNotDisturbWhenSilent;
    public final int vibrateToSilentDebounce;
    public final boolean volumeDownToEnterSilent;
    public final boolean volumeUpToExitSilent;

    static {
        DEFAULT = new VolumePolicy(false, false, false, 400);
        CREATOR = new Parcelable.Creator<VolumePolicy>(){

            @Override
            public VolumePolicy createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                boolean bl = true;
                boolean bl2 = n != 0;
                boolean bl3 = parcel.readInt() != 0;
                if (parcel.readInt() == 0) {
                    bl = false;
                }
                return new VolumePolicy(bl2, bl3, bl, parcel.readInt());
            }

            public VolumePolicy[] newArray(int n) {
                return new VolumePolicy[n];
            }
        };
    }

    public VolumePolicy(boolean bl, boolean bl2, boolean bl3, int n) {
        this.volumeDownToEnterSilent = bl;
        this.volumeUpToExitSilent = bl2;
        this.doNotDisturbWhenSilent = bl3;
        this.vibrateToSilentDebounce = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof VolumePolicy)) {
            return false;
        }
        boolean bl = true;
        if (object == this) {
            return true;
        }
        object = (VolumePolicy)object;
        if (((VolumePolicy)object).volumeDownToEnterSilent != this.volumeDownToEnterSilent || ((VolumePolicy)object).volumeUpToExitSilent != this.volumeUpToExitSilent || ((VolumePolicy)object).doNotDisturbWhenSilent != this.doNotDisturbWhenSilent || ((VolumePolicy)object).vibrateToSilentDebounce != this.vibrateToSilentDebounce) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.volumeDownToEnterSilent, this.volumeUpToExitSilent, this.doNotDisturbWhenSilent, this.vibrateToSilentDebounce);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VolumePolicy[volumeDownToEnterSilent=");
        stringBuilder.append(this.volumeDownToEnterSilent);
        stringBuilder.append(",volumeUpToExitSilent=");
        stringBuilder.append(this.volumeUpToExitSilent);
        stringBuilder.append(",doNotDisturbWhenSilent=");
        stringBuilder.append(this.doNotDisturbWhenSilent);
        stringBuilder.append(",vibrateToSilentDebounce=");
        stringBuilder.append(this.vibrateToSilentDebounce);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.volumeDownToEnterSilent);
        parcel.writeInt((int)this.volumeUpToExitSilent);
        parcel.writeInt((int)this.doNotDisturbWhenSilent);
        parcel.writeInt(this.vibrateToSilentDebounce);
    }

}

