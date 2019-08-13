/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class AuthenticatorDescription
implements Parcelable {
    public static final Parcelable.Creator<AuthenticatorDescription> CREATOR = new Parcelable.Creator<AuthenticatorDescription>(){

        @Override
        public AuthenticatorDescription createFromParcel(Parcel parcel) {
            return new AuthenticatorDescription(parcel);
        }

        public AuthenticatorDescription[] newArray(int n) {
            return new AuthenticatorDescription[n];
        }
    };
    public final int accountPreferencesId;
    public final boolean customTokens;
    public final int iconId;
    public final int labelId;
    public final String packageName;
    public final int smallIconId;
    public final String type;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private AuthenticatorDescription(Parcel parcel) {
        this.type = parcel.readString();
        this.packageName = parcel.readString();
        this.labelId = parcel.readInt();
        this.iconId = parcel.readInt();
        this.smallIconId = parcel.readInt();
        this.accountPreferencesId = parcel.readInt();
        byte by = parcel.readByte();
        boolean bl = true;
        if (by != 1) {
            bl = false;
        }
        this.customTokens = bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private AuthenticatorDescription(String string2) {
        this.type = string2;
        this.packageName = null;
        this.labelId = 0;
        this.iconId = 0;
        this.smallIconId = 0;
        this.accountPreferencesId = 0;
        this.customTokens = false;
    }

    public AuthenticatorDescription(String string2, String string3, int n, int n2, int n3, int n4) {
        this(string2, string3, n, n2, n3, n4, false);
    }

    public AuthenticatorDescription(String string2, String string3, int n, int n2, int n3, int n4, boolean bl) {
        if (string2 != null) {
            if (string3 != null) {
                this.type = string2;
                this.packageName = string3;
                this.labelId = n;
                this.iconId = n2;
                this.smallIconId = n3;
                this.accountPreferencesId = n4;
                this.customTokens = bl;
                return;
            }
            throw new IllegalArgumentException("packageName cannot be null");
        }
        throw new IllegalArgumentException("type cannot be null");
    }

    public static AuthenticatorDescription newKey(String string2) {
        if (string2 != null) {
            return new AuthenticatorDescription(string2);
        }
        throw new IllegalArgumentException("type cannot be null");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AuthenticatorDescription)) {
            return false;
        }
        object = (AuthenticatorDescription)object;
        return this.type.equals(((AuthenticatorDescription)object).type);
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AuthenticatorDescription {type=");
        stringBuilder.append(this.type);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.type);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.labelId);
        parcel.writeInt(this.iconId);
        parcel.writeInt(this.smallIconId);
        parcel.writeInt(this.accountPreferencesId);
        parcel.writeByte((byte)(this.customTokens ? 1 : 0));
    }

}

