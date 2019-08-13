/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.le.BluetoothLeUtils;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class AdvertiseData
implements Parcelable {
    public static final Parcelable.Creator<AdvertiseData> CREATOR = new Parcelable.Creator<AdvertiseData>(){

        @Override
        public AdvertiseData createFromParcel(Parcel parcel) {
            int n;
            Builder builder = new Builder();
            Iterator<ParcelUuid> iterator = parcel.createTypedArrayList(ParcelUuid.CREATOR).iterator();
            while (iterator.hasNext()) {
                builder.addServiceUuid(iterator.next());
            }
            int n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                builder.addManufacturerData(parcel.readInt(), parcel.createByteArray());
            }
            n2 = parcel.readInt();
            for (n = 0; n < n2; ++n) {
                builder.addServiceData(parcel.readTypedObject(ParcelUuid.CREATOR), parcel.createByteArray());
            }
            n = parcel.readByte();
            boolean bl = false;
            boolean bl2 = n == 1;
            builder.setIncludeTxPowerLevel(bl2);
            bl2 = bl;
            if (parcel.readByte() == 1) {
                bl2 = true;
            }
            builder.setIncludeDeviceName(bl2);
            return builder.build();
        }

        public AdvertiseData[] newArray(int n) {
            return new AdvertiseData[n];
        }
    };
    private final boolean mIncludeDeviceName;
    private final boolean mIncludeTxPowerLevel;
    private final SparseArray<byte[]> mManufacturerSpecificData;
    private final Map<ParcelUuid, byte[]> mServiceData;
    private final List<ParcelUuid> mServiceUuids;

    private AdvertiseData(List<ParcelUuid> list, SparseArray<byte[]> sparseArray, Map<ParcelUuid, byte[]> map, boolean bl, boolean bl2) {
        this.mServiceUuids = list;
        this.mManufacturerSpecificData = sparseArray;
        this.mServiceData = map;
        this.mIncludeTxPowerLevel = bl;
        this.mIncludeDeviceName = bl2;
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (AdvertiseData)object;
            if (!(Objects.equals(this.mServiceUuids, ((AdvertiseData)object).mServiceUuids) && BluetoothLeUtils.equals(this.mManufacturerSpecificData, ((AdvertiseData)object).mManufacturerSpecificData) && BluetoothLeUtils.equals(this.mServiceData, ((AdvertiseData)object).mServiceData) && this.mIncludeDeviceName == ((AdvertiseData)object).mIncludeDeviceName && this.mIncludeTxPowerLevel == ((AdvertiseData)object).mIncludeTxPowerLevel)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public boolean getIncludeDeviceName() {
        return this.mIncludeDeviceName;
    }

    public boolean getIncludeTxPowerLevel() {
        return this.mIncludeTxPowerLevel;
    }

    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.mManufacturerSpecificData;
    }

    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.mServiceData;
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public int hashCode() {
        return Objects.hash(this.mServiceUuids, this.mManufacturerSpecificData, this.mServiceData, this.mIncludeDeviceName, this.mIncludeTxPowerLevel);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AdvertiseData [mServiceUuids=");
        stringBuilder.append(this.mServiceUuids);
        stringBuilder.append(", mManufacturerSpecificData=");
        stringBuilder.append(BluetoothLeUtils.toString(this.mManufacturerSpecificData));
        stringBuilder.append(", mServiceData=");
        stringBuilder.append(BluetoothLeUtils.toString(this.mServiceData));
        stringBuilder.append(", mIncludeTxPowerLevel=");
        stringBuilder.append(this.mIncludeTxPowerLevel);
        stringBuilder.append(", mIncludeDeviceName=");
        stringBuilder.append(this.mIncludeDeviceName);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        List<ParcelUuid> list = this.mServiceUuids;
        parcel.writeTypedArray((Parcelable[])list.toArray(new ParcelUuid[list.size()]), n);
        parcel.writeInt(this.mManufacturerSpecificData.size());
        for (int i = 0; i < this.mManufacturerSpecificData.size(); ++i) {
            parcel.writeInt(this.mManufacturerSpecificData.keyAt(i));
            parcel.writeByteArray(this.mManufacturerSpecificData.valueAt(i));
        }
        parcel.writeInt(this.mServiceData.size());
        for (ParcelUuid parcelUuid : this.mServiceData.keySet()) {
            parcel.writeTypedObject(parcelUuid, n);
            parcel.writeByteArray(this.mServiceData.get(parcelUuid));
        }
        parcel.writeByte((byte)(this.getIncludeTxPowerLevel() ? 1 : 0));
        parcel.writeByte((byte)(this.getIncludeDeviceName() ? 1 : 0));
    }

    public static final class Builder {
        private boolean mIncludeDeviceName;
        private boolean mIncludeTxPowerLevel;
        private SparseArray<byte[]> mManufacturerSpecificData = new SparseArray();
        private Map<ParcelUuid, byte[]> mServiceData = new ArrayMap<ParcelUuid, byte[]>();
        private List<ParcelUuid> mServiceUuids = new ArrayList<ParcelUuid>();

        public Builder addManufacturerData(int n, byte[] object) {
            if (n >= 0) {
                if (object != null) {
                    this.mManufacturerSpecificData.put(n, (byte[])object);
                    return this;
                }
                throw new IllegalArgumentException("manufacturerSpecificData is null");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid manufacturerId - ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder addServiceData(ParcelUuid parcelUuid, byte[] arrby) {
            if (parcelUuid != null && arrby != null) {
                this.mServiceData.put(parcelUuid, arrby);
                return this;
            }
            throw new IllegalArgumentException("serviceDataUuid or serviceDataUuid is null");
        }

        public Builder addServiceUuid(ParcelUuid parcelUuid) {
            if (parcelUuid != null) {
                this.mServiceUuids.add(parcelUuid);
                return this;
            }
            throw new IllegalArgumentException("serivceUuids are null");
        }

        public AdvertiseData build() {
            return new AdvertiseData(this.mServiceUuids, this.mManufacturerSpecificData, this.mServiceData, this.mIncludeTxPowerLevel, this.mIncludeDeviceName);
        }

        public Builder setIncludeDeviceName(boolean bl) {
            this.mIncludeDeviceName = bl;
            return this;
        }

        public Builder setIncludeTxPowerLevel(boolean bl) {
            this.mIncludeTxPowerLevel = bl;
            return this;
        }
    }

}

