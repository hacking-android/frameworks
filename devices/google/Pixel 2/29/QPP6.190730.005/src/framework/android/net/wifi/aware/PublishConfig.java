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

public final class PublishConfig
implements Parcelable {
    public static final Parcelable.Creator<PublishConfig> CREATOR = new Parcelable.Creator<PublishConfig>(){

        @Override
        public PublishConfig createFromParcel(Parcel parcel) {
            byte[] arrby = parcel.createByteArray();
            byte[] arrby2 = parcel.createByteArray();
            byte[] arrby3 = parcel.createByteArray();
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            boolean bl2 = parcel.readInt() != 0;
            return new PublishConfig(arrby, arrby2, arrby3, n, n2, bl, bl2);
        }

        public PublishConfig[] newArray(int n) {
            return new PublishConfig[n];
        }
    };
    public static final int PUBLISH_TYPE_SOLICITED = 1;
    public static final int PUBLISH_TYPE_UNSOLICITED = 0;
    public final boolean mEnableRanging;
    public final boolean mEnableTerminateNotification;
    public final byte[] mMatchFilter;
    public final int mPublishType;
    public final byte[] mServiceName;
    public final byte[] mServiceSpecificInfo;
    public final int mTtlSec;

    public PublishConfig(byte[] arrby, byte[] arrby2, byte[] arrby3, int n, int n2, boolean bl, boolean bl2) {
        this.mServiceName = arrby;
        this.mServiceSpecificInfo = arrby2;
        this.mMatchFilter = arrby3;
        this.mPublishType = n;
        this.mTtlSec = n2;
        this.mEnableTerminateNotification = bl;
        this.mEnableRanging = bl2;
    }

    public void assertValid(Characteristics arrby, boolean bl) throws IllegalArgumentException {
        WifiAwareUtils.validateServiceName(this.mServiceName);
        if (TlvBufferUtils.isValid(this.mMatchFilter, 0, 1)) {
            int n = this.mPublishType;
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
                    if (!bl && this.mEnableRanging) {
                        throw new IllegalArgumentException("Ranging is not supported");
                    }
                    return;
                }
                throw new IllegalArgumentException("Invalid ttlSec - must be non-negative");
            }
            arrby = new StringBuilder();
            arrby.append("Invalid publishType - ");
            arrby.append(this.mPublishType);
            throw new IllegalArgumentException(arrby.toString());
        }
        throw new IllegalArgumentException("Invalid txFilter configuration - LV fields do not match up to length");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof PublishConfig)) {
            return false;
        }
        object = (PublishConfig)object;
        if (!(Arrays.equals(this.mServiceName, ((PublishConfig)object).mServiceName) && Arrays.equals(this.mServiceSpecificInfo, ((PublishConfig)object).mServiceSpecificInfo) && Arrays.equals(this.mMatchFilter, ((PublishConfig)object).mMatchFilter) && this.mPublishType == ((PublishConfig)object).mPublishType && this.mTtlSec == ((PublishConfig)object).mTtlSec && this.mEnableTerminateNotification == ((PublishConfig)object).mEnableTerminateNotification && this.mEnableRanging == ((PublishConfig)object).mEnableRanging)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.mServiceName), Arrays.hashCode(this.mServiceSpecificInfo), Arrays.hashCode(this.mMatchFilter), this.mPublishType, this.mTtlSec, this.mEnableTerminateNotification, this.mEnableRanging);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PublishConfig [mServiceName='");
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
        stringBuilder.append(", mPublishType=");
        stringBuilder.append(this.mPublishType);
        stringBuilder.append(", mTtlSec=");
        stringBuilder.append(this.mTtlSec);
        stringBuilder.append(", mEnableTerminateNotification=");
        stringBuilder.append(this.mEnableTerminateNotification);
        stringBuilder.append(", mEnableRanging=");
        stringBuilder.append(this.mEnableRanging);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mServiceName);
        parcel.writeByteArray(this.mServiceSpecificInfo);
        parcel.writeByteArray(this.mMatchFilter);
        parcel.writeInt(this.mPublishType);
        parcel.writeInt(this.mTtlSec);
        parcel.writeInt((int)this.mEnableTerminateNotification);
        parcel.writeInt((int)this.mEnableRanging);
    }

    public static final class Builder {
        private boolean mEnableRanging = false;
        private boolean mEnableTerminateNotification = true;
        private byte[] mMatchFilter;
        private int mPublishType = 0;
        private byte[] mServiceName;
        private byte[] mServiceSpecificInfo;
        private int mTtlSec = 0;

        public PublishConfig build() {
            return new PublishConfig(this.mServiceName, this.mServiceSpecificInfo, this.mMatchFilter, this.mPublishType, this.mTtlSec, this.mEnableTerminateNotification, this.mEnableRanging);
        }

        public Builder setMatchFilter(List<byte[]> list) {
            this.mMatchFilter = new TlvBufferUtils.TlvConstructor(0, 1).allocateAndPut(list).getArray();
            return this;
        }

        public Builder setPublishType(int n) {
            if (n >= 0 && n <= 1) {
                this.mPublishType = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid publishType - ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setRangingEnabled(boolean bl) {
            this.mEnableRanging = bl;
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
    public static @interface PublishTypes {
    }

}

