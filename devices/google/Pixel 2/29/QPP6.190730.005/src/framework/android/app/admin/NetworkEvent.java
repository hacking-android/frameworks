/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.ConnectEvent;
import android.app.admin.DnsEvent;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

public abstract class NetworkEvent
implements Parcelable {
    public static final Parcelable.Creator<NetworkEvent> CREATOR = new Parcelable.Creator<NetworkEvent>(){

        @Override
        public NetworkEvent createFromParcel(Parcel object) {
            int n = ((Parcel)object).dataPosition();
            int n2 = ((Parcel)object).readInt();
            ((Parcel)object).setDataPosition(n);
            if (n2 != 1) {
                if (n2 == 2) {
                    return ConnectEvent.CREATOR.createFromParcel((Parcel)object);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected NetworkEvent token in parcel: ");
                ((StringBuilder)object).append(n2);
                throw new ParcelFormatException(((StringBuilder)object).toString());
            }
            return DnsEvent.CREATOR.createFromParcel((Parcel)object);
        }

        public NetworkEvent[] newArray(int n) {
            return new NetworkEvent[n];
        }
    };
    static final int PARCEL_TOKEN_CONNECT_EVENT = 2;
    static final int PARCEL_TOKEN_DNS_EVENT = 1;
    long mId;
    String mPackageName;
    long mTimestamp;

    NetworkEvent() {
    }

    NetworkEvent(String string2, long l) {
        this.mPackageName = string2;
        this.mTimestamp = l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getId() {
        return this.mId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public void setId(long l) {
        this.mId = l;
    }

    @Override
    public abstract void writeToParcel(Parcel var1, int var2);

}

