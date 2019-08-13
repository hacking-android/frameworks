/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.NetworkEvent;
import android.os.Parcel;
import android.os.Parcelable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DnsEvent
extends NetworkEvent
implements Parcelable {
    public static final Parcelable.Creator<DnsEvent> CREATOR = new Parcelable.Creator<DnsEvent>(){

        @Override
        public DnsEvent createFromParcel(Parcel parcel) {
            if (parcel.readInt() != 1) {
                return null;
            }
            return new DnsEvent(parcel);
        }

        public DnsEvent[] newArray(int n) {
            return new DnsEvent[n];
        }
    };
    private final String mHostname;
    private final String[] mIpAddresses;
    private final int mIpAddressesCount;

    private DnsEvent(Parcel parcel) {
        this.mHostname = parcel.readString();
        this.mIpAddresses = parcel.createStringArray();
        this.mIpAddressesCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTimestamp = parcel.readLong();
        this.mId = parcel.readLong();
    }

    public DnsEvent(String string2, String[] arrstring, int n, String string3, long l) {
        super(string3, l);
        this.mHostname = string2;
        this.mIpAddresses = arrstring;
        this.mIpAddressesCount = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getHostname() {
        return this.mHostname;
    }

    public List<InetAddress> getInetAddresses() {
        String[] arrstring = this.mIpAddresses;
        if (arrstring != null && arrstring.length != 0) {
            ArrayList<InetAddress> arrayList = new ArrayList<InetAddress>(arrstring.length);
            for (String string2 : this.mIpAddresses) {
                try {
                    arrayList.add(InetAddress.getByName(string2));
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    public int getTotalResolvedAddressCount() {
        return this.mIpAddressesCount;
    }

    public String toString() {
        long l = this.mId;
        String string2 = this.mHostname;
        Object object = this.mIpAddresses;
        object = object == null ? "NONE" : String.join((CharSequence)" ", object);
        return String.format("DnsEvent(%d, %s, %s, %d, %d, %s)", l, string2, object, this.mIpAddressesCount, this.mTimestamp, this.mPackageName);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(1);
        parcel.writeString(this.mHostname);
        parcel.writeStringArray(this.mIpAddresses);
        parcel.writeInt(this.mIpAddressesCount);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimestamp);
        parcel.writeLong(this.mId);
    }

}

