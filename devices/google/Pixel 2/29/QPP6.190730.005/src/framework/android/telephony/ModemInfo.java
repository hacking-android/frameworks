/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class ModemInfo
implements Parcelable {
    public static final Parcelable.Creator<ModemInfo> CREATOR = new Parcelable.Creator(){

        public ModemInfo createFromParcel(Parcel parcel) {
            return new ModemInfo(parcel);
        }

        public ModemInfo[] newArray(int n) {
            return new ModemInfo[n];
        }
    };
    public final boolean isDataSupported;
    public final boolean isVoiceSupported;
    public final int modemId;
    public final int rat;

    public ModemInfo(int n) {
        this(n, 0, true, true);
    }

    public ModemInfo(int n, int n2, boolean bl, boolean bl2) {
        this.modemId = n;
        this.rat = n2;
        this.isVoiceSupported = bl;
        this.isDataSupported = bl2;
    }

    public ModemInfo(Parcel parcel) {
        this.modemId = parcel.readInt();
        this.rat = parcel.readInt();
        this.isVoiceSupported = parcel.readBoolean();
        this.isDataSupported = parcel.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof ModemInfo && this.hashCode() == object.hashCode()) {
            if (this == object) {
                return true;
            }
            object = (ModemInfo)object;
            boolean bl2 = bl;
            if (this.modemId == ((ModemInfo)object).modemId) {
                bl2 = bl;
                if (this.rat == ((ModemInfo)object).rat) {
                    bl2 = bl;
                    if (this.isVoiceSupported == ((ModemInfo)object).isVoiceSupported) {
                        bl2 = bl;
                        if (this.isDataSupported == ((ModemInfo)object).isDataSupported) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.modemId, this.rat, this.isVoiceSupported, this.isDataSupported);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("modemId=");
        stringBuilder.append(this.modemId);
        stringBuilder.append(" rat=");
        stringBuilder.append(this.rat);
        stringBuilder.append(" isVoiceSupported:");
        stringBuilder.append(this.isVoiceSupported);
        stringBuilder.append(" isDataSupported:");
        stringBuilder.append(this.isDataSupported);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.modemId);
        parcel.writeInt(this.rat);
        parcel.writeBoolean(this.isVoiceSupported);
        parcel.writeBoolean(this.isDataSupported);
    }

}

