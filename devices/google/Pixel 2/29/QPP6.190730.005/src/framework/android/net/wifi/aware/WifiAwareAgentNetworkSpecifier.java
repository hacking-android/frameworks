/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.HexEncoding
 */
package android.net.wifi.aware;

import android.net.NetworkSpecifier;
import android.net.wifi.aware.WifiAwareNetworkSpecifier;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringJoiner;
import libcore.util.HexEncoding;

public class WifiAwareAgentNetworkSpecifier
extends NetworkSpecifier
implements Parcelable {
    public static final Parcelable.Creator<WifiAwareAgentNetworkSpecifier> CREATOR = new Parcelable.Creator<WifiAwareAgentNetworkSpecifier>(){

        @Override
        public WifiAwareAgentNetworkSpecifier createFromParcel(Parcel object2) {
            WifiAwareAgentNetworkSpecifier wifiAwareAgentNetworkSpecifier = new WifiAwareAgentNetworkSpecifier();
            for (Object object2 : ((Parcel)object2).readArray(null)) {
                wifiAwareAgentNetworkSpecifier.mNetworkSpecifiers.add((ByteArrayWrapper)object2);
            }
            return wifiAwareAgentNetworkSpecifier;
        }

        public WifiAwareAgentNetworkSpecifier[] newArray(int n) {
            return new WifiAwareAgentNetworkSpecifier[n];
        }
    };
    private static final String TAG = "WifiAwareAgentNs";
    private static final boolean VDBG = false;
    private MessageDigest mDigester;
    private Set<ByteArrayWrapper> mNetworkSpecifiers = new HashSet<ByteArrayWrapper>();

    public WifiAwareAgentNetworkSpecifier() {
        this.initialize();
    }

    public WifiAwareAgentNetworkSpecifier(WifiAwareNetworkSpecifier wifiAwareNetworkSpecifier) {
        this.initialize();
        this.mNetworkSpecifiers.add(this.convert(wifiAwareNetworkSpecifier));
    }

    public WifiAwareAgentNetworkSpecifier(WifiAwareNetworkSpecifier[] arrwifiAwareNetworkSpecifier) {
        this.initialize();
        for (WifiAwareNetworkSpecifier wifiAwareNetworkSpecifier : arrwifiAwareNetworkSpecifier) {
            this.mNetworkSpecifiers.add(this.convert(wifiAwareNetworkSpecifier));
        }
    }

    private ByteArrayWrapper convert(WifiAwareNetworkSpecifier arrby) {
        if (this.mDigester == null) {
            return null;
        }
        Parcel parcel = Parcel.obtain();
        arrby.writeToParcel(parcel, 0);
        arrby = parcel.marshall();
        this.mDigester.reset();
        this.mDigester.update(arrby);
        return new ByteArrayWrapper(this.mDigester.digest());
    }

    private void initialize() {
        try {
            this.mDigester = MessageDigest.getInstance("SHA-256");
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.e(TAG, "Can not instantiate a SHA-256 digester!? Will match nothing.");
            return;
        }
    }

    @Override
    public void assertValidFromUid(int n) {
        throw new SecurityException("WifiAwareAgentNetworkSpecifier should not be used in network requests");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof WifiAwareAgentNetworkSpecifier)) {
            return false;
        }
        return this.mNetworkSpecifiers.equals(((WifiAwareAgentNetworkSpecifier)object).mNetworkSpecifiers);
    }

    public int hashCode() {
        return this.mNetworkSpecifiers.hashCode();
    }

    public boolean isEmpty() {
        return this.mNetworkSpecifiers.isEmpty();
    }

    @Override
    public NetworkSpecifier redact() {
        return null;
    }

    @Override
    public boolean satisfiedBy(NetworkSpecifier object) {
        if (!(object instanceof WifiAwareAgentNetworkSpecifier)) {
            return false;
        }
        WifiAwareAgentNetworkSpecifier wifiAwareAgentNetworkSpecifier = (WifiAwareAgentNetworkSpecifier)object;
        for (ByteArrayWrapper byteArrayWrapper : this.mNetworkSpecifiers) {
            if (wifiAwareAgentNetworkSpecifier.mNetworkSpecifiers.contains(byteArrayWrapper)) continue;
            return false;
        }
        return true;
    }

    public boolean satisfiesAwareNetworkSpecifier(WifiAwareNetworkSpecifier parcelable) {
        parcelable = this.convert((WifiAwareNetworkSpecifier)parcelable);
        return this.mNetworkSpecifiers.contains(parcelable);
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<ByteArrayWrapper> iterator = this.mNetworkSpecifiers.iterator();
        while (iterator.hasNext()) {
            stringJoiner.add(iterator.next().toString());
        }
        return stringJoiner.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeArray(this.mNetworkSpecifiers.toArray());
    }

    private static class ByteArrayWrapper
    implements Parcelable {
        public static final Parcelable.Creator<ByteArrayWrapper> CREATOR = new Parcelable.Creator<ByteArrayWrapper>(){

            @Override
            public ByteArrayWrapper createFromParcel(Parcel parcel) {
                return new ByteArrayWrapper(parcel.readBlob());
            }

            public ByteArrayWrapper[] newArray(int n) {
                return new ByteArrayWrapper[n];
            }
        };
        private byte[] mData;

        ByteArrayWrapper(byte[] arrby) {
            this.mData = arrby;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof ByteArrayWrapper)) {
                return false;
            }
            return Arrays.equals(((ByteArrayWrapper)object).mData, this.mData);
        }

        public int hashCode() {
            return Arrays.hashCode(this.mData);
        }

        public String toString() {
            return new String(HexEncoding.encode((byte[])this.mData));
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeBlob(this.mData);
        }

    }

}

