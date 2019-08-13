/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;

public class NetworkState
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<NetworkState> CREATOR;
    public static final NetworkState EMPTY;
    private static final boolean SANITY_CHECK_ROAMING = false;
    public final LinkProperties linkProperties;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public final Network network;
    public final NetworkCapabilities networkCapabilities;
    public final String networkId;
    public final NetworkInfo networkInfo;
    public final String subscriberId;

    static {
        EMPTY = new NetworkState(null, null, null, null, null, null);
        CREATOR = new Parcelable.Creator<NetworkState>(){

            @Override
            public NetworkState createFromParcel(Parcel parcel) {
                return new NetworkState(parcel);
            }

            public NetworkState[] newArray(int n) {
                return new NetworkState[n];
            }
        };
    }

    public NetworkState(NetworkInfo networkInfo, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, Network network, String string2, String string3) {
        this.networkInfo = networkInfo;
        this.linkProperties = linkProperties;
        this.networkCapabilities = networkCapabilities;
        this.network = network;
        this.subscriberId = string2;
        this.networkId = string3;
    }

    @UnsupportedAppUsage
    public NetworkState(Parcel parcel) {
        this.networkInfo = (NetworkInfo)parcel.readParcelable(null);
        this.linkProperties = (LinkProperties)parcel.readParcelable(null);
        this.networkCapabilities = (NetworkCapabilities)parcel.readParcelable(null);
        this.network = (Network)parcel.readParcelable(null);
        this.subscriberId = parcel.readString();
        this.networkId = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.networkInfo, n);
        parcel.writeParcelable(this.linkProperties, n);
        parcel.writeParcelable(this.networkCapabilities, n);
        parcel.writeParcelable(this.network, n);
        parcel.writeString(this.subscriberId);
        parcel.writeString(this.networkId);
    }

}

