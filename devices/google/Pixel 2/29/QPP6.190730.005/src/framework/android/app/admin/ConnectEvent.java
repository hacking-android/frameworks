/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.NetworkEvent;
import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class ConnectEvent
extends NetworkEvent
implements Parcelable {
    public static final Parcelable.Creator<ConnectEvent> CREATOR = new Parcelable.Creator<ConnectEvent>(){

        @Override
        public ConnectEvent createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 2) {
                return null;
            }
            return new ConnectEvent(parcel);
        }

        public ConnectEvent[] newArray(int n) {
            return new ConnectEvent[n];
        }
    };
    private final String mIpAddress;
    private final int mPort;

    private ConnectEvent(Parcel parcel) {
        this.mIpAddress = parcel.readString();
        this.mPort = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTimestamp = parcel.readLong();
        this.mId = parcel.readLong();
    }

    public ConnectEvent(String string2, int n, String string3, long l) {
        super(string3, l);
        this.mIpAddress = string2;
        this.mPort = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public InetAddress getInetAddress() {
        try {
            InetAddress inetAddress = InetAddress.getByName(this.mIpAddress);
            return inetAddress;
        }
        catch (UnknownHostException unknownHostException) {
            return InetAddress.getLoopbackAddress();
        }
    }

    public int getPort() {
        return this.mPort;
    }

    public String toString() {
        return String.format("ConnectEvent(%d, %s, %d, %d, %s)", this.mId, this.mIpAddress, this.mPort, this.mTimestamp, this.mPackageName);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(2);
        parcel.writeString(this.mIpAddress);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimestamp);
        parcel.writeLong(this.mId);
    }

}

