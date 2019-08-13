/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.net.wifi.aware.PeerHandle;
import android.os.Parcel;
import android.os.Parcelable;

public final class ParcelablePeerHandle
extends PeerHandle
implements Parcelable {
    public static final Parcelable.Creator<ParcelablePeerHandle> CREATOR = new Parcelable.Creator<ParcelablePeerHandle>(){

        @Override
        public ParcelablePeerHandle createFromParcel(Parcel parcel) {
            return new ParcelablePeerHandle(new PeerHandle(parcel.readInt()));
        }

        public ParcelablePeerHandle[] newArray(int n) {
            return new ParcelablePeerHandle[n];
        }
    };

    public ParcelablePeerHandle(PeerHandle peerHandle) {
        super(peerHandle.peerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.peerId);
    }

}

