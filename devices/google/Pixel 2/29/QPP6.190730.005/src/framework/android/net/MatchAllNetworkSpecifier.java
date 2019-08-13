/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.NetworkSpecifier;
import android.os.Parcel;
import android.os.Parcelable;

public final class MatchAllNetworkSpecifier
extends NetworkSpecifier
implements Parcelable {
    public static final Parcelable.Creator<MatchAllNetworkSpecifier> CREATOR = new Parcelable.Creator<MatchAllNetworkSpecifier>(){

        @Override
        public MatchAllNetworkSpecifier createFromParcel(Parcel parcel) {
            return new MatchAllNetworkSpecifier();
        }

        public MatchAllNetworkSpecifier[] newArray(int n) {
            return new MatchAllNetworkSpecifier[n];
        }
    };

    public static void checkNotMatchAllNetworkSpecifier(NetworkSpecifier networkSpecifier) {
        if (!(networkSpecifier instanceof MatchAllNetworkSpecifier)) {
            return;
        }
        throw new IllegalArgumentException("A MatchAllNetworkSpecifier is not permitted");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        return object instanceof MatchAllNetworkSpecifier;
    }

    public int hashCode() {
        return 0;
    }

    @Override
    public boolean satisfiedBy(NetworkSpecifier networkSpecifier) {
        throw new IllegalStateException("MatchAllNetworkSpecifier must not be used in NetworkRequests");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
    }

}

