/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.HexEncoding
 */
package android.net.wifi.aware;

import android.net.wifi.aware.Characteristics;
import android.net.wifi.aware.TlvBufferUtils;
import android.net.wifi.aware.WifiAwareUtils;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import libcore.util.HexEncoding;

public final class SubscribeConfig
implements Parcelable {
    public static final Parcelable.Creator<SubscribeConfig> CREATOR = new Parcelable.Creator<SubscribeConfig>(){

        @Override
        public SubscribeConfig createFromParcel(Parcel parcel) {
            byte[] arrby = parcel.createByteArray();
            byte[] arrby2 = parcel.createByteArray();
            byte[] arrby3 = parcel.createByteArray();
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            int n3 = parcel.readInt();
            boolean bl2 = parcel.readInt() != 0;
            int n4 = parcel.readInt();
            boolean bl3 = parcel.readInt() != 0;
            return new SubscribeConfig(arrby, arrby2, arrby3, n, n2, bl, bl2, n3, bl3, n4);
        }

        public SubscribeConfig[] newArray(int n) {
            return new SubscribeConfig[n];
        }
    };
    public static final int SUBSCRIBE_TYPE_ACTIVE = 1;
    public static final int SUBSCRIBE_TYPE_PASSIVE = 0;
    public final boolean mEnableTerminateNotification;
    public final byte[] mMatchFilter;
    public final int mMaxDistanceMm;
    public final boolean mMaxDistanceMmSet;
    public final int mMinDistanceMm;
    public final boolean mMinDistanceMmSet;
    public final byte[] mServiceName;
    public final byte[] mServiceSpecificInfo;
    public final int mSubscribeType;
    public final int mTtlSec;

    public SubscribeConfig(byte[] arrby, byte[] arrby2, byte[] arrby3, int n, int n2, boolean bl, boolean bl2, int n3, boolean bl3, int n4) {
        this.mServiceName = arrby;
        this.mServiceSpecificInfo = arrby2;
        this.mMatchFilter = arrby3;
        this.mSubscribeType = n;
        this.mTtlSec = n2;
        this.mEnableTerminateNotification = bl;
        this.mMinDistanceMm = n3;
        this.mMinDistanceMmSet = bl2;
        this.mMaxDistanceMm = n4;
        this.mMaxDistanceMmSet = bl3;
    }

    public void assertValid(Characteristics arrby, boolean bl) throws IllegalArgumentException {
        WifiAwareUtils.validateServiceName(this.mServiceName);
        if (TlvBufferUtils.isValid(this.mMatchFilter, 0, 1)) {
            int n = this.mSubscribeType;
            if (n >= 0 && n <= 1) {
                if (this.mTtlSec >= 0) {
                    if (arrby != null) {
                        byte[] arrby2;
                        n = arrby.getMaxServiceNameLength();
                        if (n != 0 && this.mServiceName.length > n) {
                            throw new IllegalArgumentException("Service name longer than supported by device characteristics");
                        }
                        n = arrby.getMaxServiceSpecificInfoLength();
                        if (n != 0 && (arrby2 = this.mServiceSpecificInfo) != null && arrby2.length > n) {
                            throw new IllegalArgumentException("Service specific info longer than supported by device characteristics");
                        }
                        n = arrby.getMaxMatchFilterLength();
                        if (n != 0 && (arrby = this.mMatchFilter) != null && arrby.length > n) {
                            throw new IllegalArgumentException("Match filter longer than supported by device characteristics");
                        }
                    }
                    if (this.mMinDistanceMmSet && this.mMinDistanceMm < 0) {
                        throw new IllegalArgumentException("Minimum distance must be non-negative");
                    }
                    if (this.mMaxDistanceMmSet && this.mMaxDistanceMm < 0) {
                        throw new IllegalArgumentException("Maximum distance must be non-negative");
                    }
                    if (this.mMinDistanceMmSet && this.mMaxDistanceMmSet && this.mMaxDistanceMm <= this.mMinDistanceMm) {
                        throw new IllegalArgumentException("Maximum distance must be greater than minimum distance");
                    }
                    if (!bl && (this.mMinDistanceMmSet || this.mMaxDistanceMmSet)) {
                        throw new IllegalArgumentException("Ranging is not supported");
                    }
                    return;
                }
                throw new IllegalArgumentException("Invalid ttlSec - must be non-negative");
            }
            arrby = new StringBuilder();
            arrby.append("Invalid subscribeType - ");
            arrby.append(this.mSubscribeType);
            throw new IllegalArgumentException(arrby.toString());
        }
        throw new IllegalArgumentException("Invalid matchFilter configuration - LV fields do not match up to length");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        if (this == object) {
            return true;
        }
        if (!(object instanceof SubscribeConfig)) {
            return false;
        }
        object = (SubscribeConfig)object;
        if (Arrays.equals(this.mServiceName, ((SubscribeConfig)object).mServiceName) && Arrays.equals(this.mServiceSpecificInfo, ((SubscribeConfig)object).mServiceSpecificInfo) && Arrays.equals(this.mMatchFilter, ((SubscribeConfig)object).mMatchFilter) && this.mSubscribeType == ((SubscribeConfig)object).mSubscribeType && this.mTtlSec == ((SubscribeConfig)object).mTtlSec && this.mEnableTerminateNotification == ((SubscribeConfig)object).mEnableTerminateNotification && (bl = this.mMinDistanceMmSet) == ((SubscribeConfig)object).mMinDistanceMmSet && this.mMaxDistanceMmSet == ((SubscribeConfig)object).mMaxDistanceMmSet) {
            if (bl && this.mMinDistanceMm != ((SubscribeConfig)object).mMinDistanceMm) {
                return false;
            }
            return !this.mMaxDistanceMmSet || this.mMaxDistanceMm == ((SubscribeConfig)object).mMaxDistanceMm;
        }
        return false;
    }

    public int hashCode() {
        int n;
        int n2 = n = Objects.hash(Arrays.hashCode(this.mServiceName), Arrays.hashCode(this.mServiceSpecificInfo), Arrays.hashCode(this.mMatchFilter), this.mSubscribeType, this.mTtlSec, this.mEnableTerminateNotification, this.mMinDistanceMmSet, this.mMaxDistanceMmSet);
        if (this.mMinDistanceMmSet) {
            n2 = Objects.hash(n, this.mMinDistanceMm);
        }
        n = n2;
        if (this.mMaxDistanceMmSet) {
            n = Objects.hash(n2, this.mMaxDistanceMm);
        }
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SubscribeConfig [mServiceName='");
        Object object = this.mServiceName;
        String string2 = "<null>";
        object = object == null ? "<null>" : String.valueOf(HexEncoding.encode((byte[])object));
        stringBuilder.append((String)object);
        stringBuilder.append(", mServiceName.length=");
        object = this.mServiceName;
        int n = 0;
        int n2 = object == null ? 0 : ((byte[])object).length;
        stringBuilder.append(n2);
        stringBuilder.append(", mServiceSpecificInfo='");
        object = this.mServiceSpecificInfo;
        object = object == null ? string2 : String.valueOf(HexEncoding.encode((byte[])object));
        stringBuilder.append((String)object);
        stringBuilder.append(", mServiceSpecificInfo.length=");
        object = this.mServiceSpecificInfo;
        n2 = object == null ? 0 : ((byte[])object).length;
        stringBuilder.append(n2);
        stringBuilder.append(", mMatchFilter=");
        stringBuilder.append(new TlvBufferUtils.TlvIterable(0, 1, this.mMatchFilter).toString());
        stringBuilder.append(", mMatchFilter.length=");
        object = this.mMatchFilter;
        n2 = object == null ? n : ((byte[])object).length;
        stringBuilder.append(n2);
        stringBuilder.append(", mSubscribeType=");
        stringBuilder.append(this.mSubscribeType);
        stringBuilder.append(", mTtlSec=");
        stringBuilder.append(this.mTtlSec);
        stringBuilder.append(", mEnableTerminateNotification=");
        stringBuilder.append(this.mEnableTerminateNotification);
        stringBuilder.append(", mMinDistanceMm=");
        stringBuilder.append(this.mMinDistanceMm);
        stringBuilder.append(", mMinDistanceMmSet=");
        stringBuilder.append(this.mMinDistanceMmSet);
        stringBuilder.append(", mMaxDistanceMm=");
        stringBuilder.append(this.mMaxDistanceMm);
        stringBuilder.append(", mMaxDistanceMmSet=");
        stringBuilder.append(this.mMaxDistanceMmSet);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mServiceName);
        parcel.writeByteArray(this.mServiceSpecificInfo);
        parcel.writeByteArray(this.mMatchFilter);
        parcel.writeInt(this.mSubscribeType);
        parcel.writeInt(this.mTtlSec);
        parcel.writeInt((int)this.mEnableTerminateNotification);
        parcel.writeInt(this.mMinDistanceMm);
        parcel.writeInt((int)this.mMinDistanceMmSet);
        parcel.writeInt(this.mMaxDistanceMm);
        parcel.writeInt((int)this.mMaxDistanceMmSet);
    }

    public static final class Builder {
        private boolean mEnableTerminateNotification = true;
        private byte[] mMatchFilter;
        private int mMaxDistanceMm;
        private boolean mMaxDistanceMmSet = false;
        private int mMinDistanceMm;
        private boolean mMinDistanceMmSet = false;
        private byte[] mServiceName;
        private byte[] mServiceSpecificInfo;
        private int mSubscribeType = 0;
        private int mTtlSec = 0;

        public SubscribeConfig build() {
            return new SubscribeConfig(this.mServiceName, this.mServiceSpecificInfo, this.mMatchFilter, this.mSubscribeType, this.mTtlSec, this.mEnableTerminateNotification, this.mMinDistanceMmSet, this.mMinDistanceMm, this.mMaxDistanceMmSet, this.mMaxDistanceMm);
        }

        public Builder setMatchFilter(List<byte[]> list) {
            this.mMatchFilter = new TlvBufferUtils.TlvConstructor(0, 1).allocateAndPut(list).getArray();
            return this;
        }

        public Builder setMaxDistanceMm(int n) {
            this.mMaxDistanceMm = n;
            this.mMaxDistanceMmSet = true;
            return this;
        }

        public Builder setMinDistanceMm(int n) {
            this.mMinDistanceMm = n;
            this.mMinDistanceMmSet = true;
            return this;
        }

        public Builder setServiceName(String string2) {
            if (string2 != null) {
                this.mServiceName = string2.getBytes(StandardCharsets.UTF_8);
                return this;
            }
            throw new IllegalArgumentException("Invalid service name - must be non-null");
        }

        public Builder setServiceSpecificInfo(byte[] arrby) {
            this.mServiceSpecificInfo = arrby;
            return this;
        }

        public Builder setSubscribeType(int n) {
            if (n >= 0 && n <= 1) {
                this.mSubscribeType = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid subscribeType - ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setTerminateNotificationEnabled(boolean bl) {
            this.mEnableTerminateNotification = bl;
            return this;
        }

        public Builder setTtlSec(int n) {
            if (n >= 0) {
                this.mTtlSec = n;
                return this;
            }
            throw new IllegalArgumentException("Invalid ttlSec - must be non-negative");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SubscribeTypes {
    }

}

