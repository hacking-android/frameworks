/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.Parcelable;

public class NetworkMisc
implements Parcelable {
    public static final Parcelable.Creator<NetworkMisc> CREATOR = new Parcelable.Creator<NetworkMisc>(){

        @Override
        public NetworkMisc createFromParcel(Parcel parcel) {
            NetworkMisc networkMisc = new NetworkMisc();
            int n = parcel.readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            networkMisc.allowBypass = bl2;
            bl2 = parcel.readInt() != 0;
            networkMisc.explicitlySelected = bl2;
            bl2 = parcel.readInt() != 0;
            networkMisc.acceptUnvalidated = bl2;
            networkMisc.subscriberId = parcel.readString();
            bl2 = parcel.readInt() != 0;
            networkMisc.provisioningNotificationDisabled = bl2;
            bl2 = parcel.readInt() != 0 ? bl : false;
            networkMisc.skip464xlat = bl2;
            return networkMisc;
        }

        public NetworkMisc[] newArray(int n) {
            return new NetworkMisc[n];
        }
    };
    public boolean acceptPartialConnectivity;
    public boolean acceptUnvalidated;
    public boolean allowBypass;
    public boolean explicitlySelected;
    public boolean provisioningNotificationDisabled;
    public boolean skip464xlat;
    public String subscriberId;

    public NetworkMisc() {
    }

    public NetworkMisc(NetworkMisc networkMisc) {
        if (networkMisc != null) {
            this.allowBypass = networkMisc.allowBypass;
            this.explicitlySelected = networkMisc.explicitlySelected;
            this.acceptUnvalidated = networkMisc.acceptUnvalidated;
            this.subscriberId = networkMisc.subscriberId;
            this.provisioningNotificationDisabled = networkMisc.provisioningNotificationDisabled;
            this.skip464xlat = networkMisc.skip464xlat;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.allowBypass);
        parcel.writeInt((int)this.explicitlySelected);
        parcel.writeInt((int)this.acceptUnvalidated);
        parcel.writeString(this.subscriberId);
        parcel.writeInt((int)this.provisioningNotificationDisabled);
        parcel.writeInt((int)this.skip464xlat);
    }

}

