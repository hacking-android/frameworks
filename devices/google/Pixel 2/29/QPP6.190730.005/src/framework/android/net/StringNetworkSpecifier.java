/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.NetworkSpecifier;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.util.Objects;

public final class StringNetworkSpecifier
extends NetworkSpecifier
implements Parcelable {
    public static final Parcelable.Creator<StringNetworkSpecifier> CREATOR = new Parcelable.Creator<StringNetworkSpecifier>(){

        @Override
        public StringNetworkSpecifier createFromParcel(Parcel parcel) {
            return new StringNetworkSpecifier(parcel.readString());
        }

        public StringNetworkSpecifier[] newArray(int n) {
            return new StringNetworkSpecifier[n];
        }
    };
    @UnsupportedAppUsage
    public final String specifier;

    public StringNetworkSpecifier(String string2) {
        Preconditions.checkStringNotEmpty(string2);
        this.specifier = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof StringNetworkSpecifier)) {
            return false;
        }
        return TextUtils.equals(this.specifier, ((StringNetworkSpecifier)object).specifier);
    }

    public int hashCode() {
        return Objects.hashCode(this.specifier);
    }

    @Override
    public boolean satisfiedBy(NetworkSpecifier networkSpecifier) {
        return this.equals(networkSpecifier);
    }

    public String toString() {
        return this.specifier;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.specifier);
    }

}

