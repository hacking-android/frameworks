/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public final class ConnectionInfo
implements Parcelable {
    public static final Parcelable.Creator<ConnectionInfo> CREATOR = new Parcelable.Creator<ConnectionInfo>(){

        @Override
        public ConnectionInfo createFromParcel(Parcel parcel) {
            int n;
            InetAddress inetAddress;
            int n2 = parcel.readInt();
            try {
                inetAddress = InetAddress.getByAddress(parcel.createByteArray());
                n = parcel.readInt();
            }
            catch (UnknownHostException unknownHostException) {
                throw new IllegalArgumentException("Invalid InetAddress");
            }
            try {
                InetAddress inetAddress2 = InetAddress.getByAddress(parcel.createByteArray());
                int n3 = parcel.readInt();
                return new ConnectionInfo(n2, new InetSocketAddress(inetAddress, n), new InetSocketAddress(inetAddress2, n3));
            }
            catch (UnknownHostException unknownHostException) {
                throw new IllegalArgumentException("Invalid InetAddress");
            }
        }

        public ConnectionInfo[] newArray(int n) {
            return new ConnectionInfo[n];
        }
    };
    public final InetSocketAddress local;
    public final int protocol;
    public final InetSocketAddress remote;

    public ConnectionInfo(int n, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        this.protocol = n;
        this.local = inetSocketAddress;
        this.remote = inetSocketAddress2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.protocol);
        parcel.writeByteArray(this.local.getAddress().getAddress());
        parcel.writeInt(this.local.getPort());
        parcel.writeByteArray(this.remote.getAddress().getAddress());
        parcel.writeInt(this.remote.getPort());
    }

}

