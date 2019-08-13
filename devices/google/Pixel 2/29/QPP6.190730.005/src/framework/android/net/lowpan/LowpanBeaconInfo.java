/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanIdentity;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.HexDump;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

public class LowpanBeaconInfo
implements Parcelable {
    public static final Parcelable.Creator<LowpanBeaconInfo> CREATOR = new Parcelable.Creator<LowpanBeaconInfo>(){

        @Override
        public LowpanBeaconInfo createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            builder.setLowpanIdentity(LowpanIdentity.CREATOR.createFromParcel(parcel));
            builder.setRssi(parcel.readInt());
            builder.setLqi(parcel.readInt());
            builder.setBeaconAddress(parcel.createByteArray());
            for (int i = parcel.readInt(); i > 0; --i) {
                builder.setFlag(parcel.readInt());
            }
            return builder.build();
        }

        public LowpanBeaconInfo[] newArray(int n) {
            return new LowpanBeaconInfo[n];
        }
    };
    public static final int FLAG_CAN_ASSIST = 1;
    public static final int UNKNOWN_LQI = 0;
    public static final int UNKNOWN_RSSI = Integer.MAX_VALUE;
    private byte[] mBeaconAddress = null;
    private final TreeSet<Integer> mFlags = new TreeSet();
    private LowpanIdentity mIdentity;
    private int mLqi = 0;
    private int mRssi = Integer.MAX_VALUE;

    private LowpanBeaconInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof LowpanBeaconInfo;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (LowpanBeaconInfo)object;
            if (!this.mIdentity.equals(((LowpanBeaconInfo)object).mIdentity) || !Arrays.equals(this.mBeaconAddress, ((LowpanBeaconInfo)object).mBeaconAddress) || this.mRssi != ((LowpanBeaconInfo)object).mRssi || this.mLqi != ((LowpanBeaconInfo)object).mLqi || !this.mFlags.equals(((LowpanBeaconInfo)object).mFlags)) break block1;
            bl = true;
        }
        return bl;
    }

    public byte[] getBeaconAddress() {
        return (byte[])this.mBeaconAddress.clone();
    }

    public Collection<Integer> getFlags() {
        return (Collection)this.mFlags.clone();
    }

    public LowpanIdentity getLowpanIdentity() {
        return this.mIdentity;
    }

    public int getLqi() {
        return this.mLqi;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int hashCode() {
        return Objects.hash(this.mIdentity, this.mRssi, this.mLqi, Arrays.hashCode(this.mBeaconAddress), this.mFlags);
    }

    public boolean isFlagSet(int n) {
        return this.mFlags.contains(n);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.mIdentity.toString());
        if (this.mRssi != Integer.MAX_VALUE) {
            stringBuffer.append(", RSSI:");
            stringBuffer.append(this.mRssi);
            stringBuffer.append("dBm");
        }
        if (this.mLqi != 0) {
            stringBuffer.append(", LQI:");
            stringBuffer.append(this.mLqi);
        }
        if (this.mBeaconAddress.length > 0) {
            stringBuffer.append(", BeaconAddress:");
            stringBuffer.append(HexDump.toHexString(this.mBeaconAddress));
        }
        for (Integer n : this.mFlags) {
            if (n != 1) {
                stringBuffer.append(", FLAG_");
                stringBuffer.append(Integer.toHexString(n));
                continue;
            }
            stringBuffer.append(", CAN_ASSIST");
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mIdentity.writeToParcel(parcel, n);
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mLqi);
        parcel.writeByteArray(this.mBeaconAddress);
        parcel.writeInt(this.mFlags.size());
        Iterator<Integer> iterator = this.mFlags.iterator();
        while (iterator.hasNext()) {
            parcel.writeInt(iterator.next());
        }
    }

    public static class Builder {
        final LowpanBeaconInfo mBeaconInfo = new LowpanBeaconInfo();
        final LowpanIdentity.Builder mIdentityBuilder = new LowpanIdentity.Builder();

        public LowpanBeaconInfo build() {
            this.mBeaconInfo.mIdentity = this.mIdentityBuilder.build();
            if (this.mBeaconInfo.mBeaconAddress == null) {
                this.mBeaconInfo.mBeaconAddress = new byte[0];
            }
            return this.mBeaconInfo;
        }

        public Builder setBeaconAddress(byte[] object) {
            LowpanBeaconInfo lowpanBeaconInfo = this.mBeaconInfo;
            object = object != null ? (byte[])object.clone() : null;
            lowpanBeaconInfo.mBeaconAddress = object;
            return this;
        }

        public Builder setChannel(int n) {
            this.mIdentityBuilder.setChannel(n);
            return this;
        }

        public Builder setFlag(int n) {
            this.mBeaconInfo.mFlags.add(n);
            return this;
        }

        public Builder setFlags(Collection<Integer> collection) {
            this.mBeaconInfo.mFlags.addAll(collection);
            return this;
        }

        public Builder setLowpanIdentity(LowpanIdentity lowpanIdentity) {
            this.mIdentityBuilder.setLowpanIdentity(lowpanIdentity);
            return this;
        }

        public Builder setLqi(int n) {
            this.mBeaconInfo.mLqi = n;
            return this;
        }

        public Builder setName(String string2) {
            this.mIdentityBuilder.setName(string2);
            return this;
        }

        public Builder setPanid(int n) {
            this.mIdentityBuilder.setPanid(n);
            return this;
        }

        public Builder setRssi(int n) {
            this.mBeaconInfo.mRssi = n;
            return this;
        }

        public Builder setType(String string2) {
            this.mIdentityBuilder.setType(string2);
            return this;
        }

        public Builder setXpanid(byte[] arrby) {
            this.mIdentityBuilder.setXpanid(arrby);
            return this;
        }
    }

}

