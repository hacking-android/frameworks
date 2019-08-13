/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import com.android.internal.util.BitUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ScanFilter
implements Parcelable {
    public static final Parcelable.Creator<ScanFilter> CREATOR;
    public static final ScanFilter EMPTY;
    private final String mDeviceAddress;
    private final String mDeviceName;
    private final byte[] mManufacturerData;
    private final byte[] mManufacturerDataMask;
    private final int mManufacturerId;
    private final byte[] mServiceData;
    private final byte[] mServiceDataMask;
    private final ParcelUuid mServiceDataUuid;
    private final ParcelUuid mServiceSolicitationUuid;
    private final ParcelUuid mServiceSolicitationUuidMask;
    private final ParcelUuid mServiceUuid;
    private final ParcelUuid mServiceUuidMask;

    static {
        EMPTY = new Builder().build();
        CREATOR = new Parcelable.Creator<ScanFilter>(){

            @Override
            public ScanFilter createFromParcel(Parcel parcel) {
                byte[] arrby;
                byte[] arrby2;
                Builder builder = new Builder();
                if (parcel.readInt() == 1) {
                    builder.setDeviceName(parcel.readString());
                }
                if (parcel.readInt() == 1) {
                    builder.setDeviceAddress(parcel.readString());
                }
                if (parcel.readInt() == 1) {
                    arrby2 = (byte[])parcel.readParcelable(ParcelUuid.class.getClassLoader());
                    builder.setServiceUuid((ParcelUuid)arrby2);
                    if (parcel.readInt() == 1) {
                        builder.setServiceUuid((ParcelUuid)arrby2, (ParcelUuid)parcel.readParcelable(ParcelUuid.class.getClassLoader()));
                    }
                }
                if (parcel.readInt() == 1) {
                    arrby2 = (ParcelUuid)parcel.readParcelable(ParcelUuid.class.getClassLoader());
                    builder.setServiceSolicitationUuid((ParcelUuid)arrby2);
                    if (parcel.readInt() == 1) {
                        builder.setServiceSolicitationUuid((ParcelUuid)arrby2, (ParcelUuid)parcel.readParcelable(ParcelUuid.class.getClassLoader()));
                    }
                }
                if (parcel.readInt() == 1) {
                    arrby2 = (ParcelUuid)parcel.readParcelable(ParcelUuid.class.getClassLoader());
                    if (parcel.readInt() == 1) {
                        byte[] arrby3 = new byte[parcel.readInt()];
                        parcel.readByteArray(arrby3);
                        if (parcel.readInt() == 0) {
                            builder.setServiceData((ParcelUuid)arrby2, arrby3);
                        } else {
                            arrby = new byte[parcel.readInt()];
                            parcel.readByteArray(arrby);
                            builder.setServiceData((ParcelUuid)arrby2, arrby3, arrby);
                        }
                    }
                }
                int n = parcel.readInt();
                if (parcel.readInt() == 1) {
                    arrby = new byte[parcel.readInt()];
                    parcel.readByteArray(arrby);
                    if (parcel.readInt() == 0) {
                        builder.setManufacturerData(n, arrby);
                    } else {
                        arrby2 = new byte[parcel.readInt()];
                        parcel.readByteArray(arrby2);
                        builder.setManufacturerData(n, arrby, arrby2);
                    }
                }
                return builder.build();
            }

            public ScanFilter[] newArray(int n) {
                return new ScanFilter[n];
            }
        };
    }

    private ScanFilter(String string2, String string3, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, ParcelUuid parcelUuid4, ParcelUuid parcelUuid5, byte[] arrby, byte[] arrby2, int n, byte[] arrby3, byte[] arrby4) {
        this.mDeviceName = string2;
        this.mServiceUuid = parcelUuid;
        this.mServiceUuidMask = parcelUuid2;
        this.mServiceSolicitationUuid = parcelUuid3;
        this.mServiceSolicitationUuidMask = parcelUuid4;
        this.mDeviceAddress = string3;
        this.mServiceDataUuid = parcelUuid5;
        this.mServiceData = arrby;
        this.mServiceDataMask = arrby2;
        this.mManufacturerId = n;
        this.mManufacturerData = arrby3;
        this.mManufacturerDataMask = arrby4;
    }

    private boolean matchesPartialData(byte[] arrby, byte[] arrby2, byte[] arrby3) {
        if (arrby3 != null && arrby3.length >= arrby.length) {
            if (arrby2 == null) {
                for (int i = 0; i < arrby.length; ++i) {
                    if (arrby3[i] == arrby[i]) continue;
                    return false;
                }
                return true;
            }
            for (int i = 0; i < arrby.length; ++i) {
                if ((arrby2[i] & arrby3[i]) == (arrby2[i] & arrby[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean matchesServiceSolicitationUuid(UUID uUID, UUID uUID2, UUID uUID3) {
        return BitUtils.maskedEquals(uUID3, uUID, uUID2);
    }

    private static boolean matchesServiceSolicitationUuids(ParcelUuid parcelUuid, ParcelUuid parcelUuid2, List<ParcelUuid> object) {
        if (parcelUuid == null) {
            return true;
        }
        if (object == null) {
            return false;
        }
        Iterator<ParcelUuid> iterator = object.iterator();
        while (iterator.hasNext()) {
            ParcelUuid parcelUuid3 = iterator.next();
            object = parcelUuid2 == null ? null : parcelUuid2.getUuid();
            if (!ScanFilter.matchesServiceUuid(parcelUuid.getUuid(), (UUID)object, parcelUuid3.getUuid())) continue;
            return true;
        }
        return false;
    }

    private static boolean matchesServiceUuid(UUID uUID, UUID uUID2, UUID uUID3) {
        return BitUtils.maskedEquals(uUID3, uUID, uUID2);
    }

    public static boolean matchesServiceUuids(ParcelUuid parcelUuid, ParcelUuid parcelUuid2, List<ParcelUuid> object) {
        if (parcelUuid == null) {
            return true;
        }
        if (object == null) {
            return false;
        }
        Iterator<ParcelUuid> iterator = object.iterator();
        while (iterator.hasNext()) {
            ParcelUuid parcelUuid3 = iterator.next();
            object = parcelUuid2 == null ? null : parcelUuid2.getUuid();
            if (!ScanFilter.matchesServiceUuid(parcelUuid.getUuid(), (UUID)object, parcelUuid3.getUuid())) continue;
            return true;
        }
        return false;
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
            object = (ScanFilter)object;
            if (!(Objects.equals(this.mDeviceName, ((ScanFilter)object).mDeviceName) && Objects.equals(this.mDeviceAddress, ((ScanFilter)object).mDeviceAddress) && this.mManufacturerId == ((ScanFilter)object).mManufacturerId && Objects.deepEquals(this.mManufacturerData, ((ScanFilter)object).mManufacturerData) && Objects.deepEquals(this.mManufacturerDataMask, ((ScanFilter)object).mManufacturerDataMask) && Objects.equals(this.mServiceDataUuid, ((ScanFilter)object).mServiceDataUuid) && Objects.deepEquals(this.mServiceData, ((ScanFilter)object).mServiceData) && Objects.deepEquals(this.mServiceDataMask, ((ScanFilter)object).mServiceDataMask) && Objects.equals(this.mServiceUuid, ((ScanFilter)object).mServiceUuid) && Objects.equals(this.mServiceUuidMask, ((ScanFilter)object).mServiceUuidMask) && Objects.equals(this.mServiceSolicitationUuid, ((ScanFilter)object).mServiceSolicitationUuid) && Objects.equals(this.mServiceSolicitationUuidMask, ((ScanFilter)object).mServiceSolicitationUuidMask))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public byte[] getManufacturerData() {
        return this.mManufacturerData;
    }

    public byte[] getManufacturerDataMask() {
        return this.mManufacturerDataMask;
    }

    public int getManufacturerId() {
        return this.mManufacturerId;
    }

    public byte[] getServiceData() {
        return this.mServiceData;
    }

    public byte[] getServiceDataMask() {
        return this.mServiceDataMask;
    }

    public ParcelUuid getServiceDataUuid() {
        return this.mServiceDataUuid;
    }

    public ParcelUuid getServiceSolicitationUuid() {
        return this.mServiceSolicitationUuid;
    }

    public ParcelUuid getServiceSolicitationUuidMask() {
        return this.mServiceSolicitationUuidMask;
    }

    public ParcelUuid getServiceUuid() {
        return this.mServiceUuid;
    }

    public ParcelUuid getServiceUuidMask() {
        return this.mServiceUuidMask;
    }

    public int hashCode() {
        return Objects.hash(this.mDeviceName, this.mDeviceAddress, this.mManufacturerId, Arrays.hashCode(this.mManufacturerData), Arrays.hashCode(this.mManufacturerDataMask), this.mServiceDataUuid, Arrays.hashCode(this.mServiceData), Arrays.hashCode(this.mServiceDataMask), this.mServiceUuid, this.mServiceUuidMask, this.mServiceSolicitationUuid, this.mServiceSolicitationUuidMask);
    }

    public boolean isAllFieldsEmpty() {
        return EMPTY.equals(this);
    }

    public boolean matches(ScanResult object) {
        if (object == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = ((ScanResult)object).getDevice();
        Object object2 = this.mDeviceAddress;
        if (!(object2 == null || bluetoothDevice != null && ((String)object2).equals(bluetoothDevice.getAddress()))) {
            return false;
        }
        if ((object = ((ScanResult)object).getScanRecord()) == null && (this.mDeviceName != null || this.mServiceUuid != null || this.mManufacturerData != null || this.mServiceData != null || this.mServiceSolicitationUuid != null)) {
            return false;
        }
        object2 = this.mDeviceName;
        if (object2 != null && !((String)object2).equals(((ScanRecord)object).getDeviceName())) {
            return false;
        }
        object2 = this.mServiceUuid;
        if (object2 != null && !ScanFilter.matchesServiceUuids((ParcelUuid)object2, this.mServiceUuidMask, ((ScanRecord)object).getServiceUuids())) {
            return false;
        }
        object2 = this.mServiceSolicitationUuid;
        if (object2 != null && !ScanFilter.matchesServiceSolicitationUuids((ParcelUuid)object2, this.mServiceSolicitationUuidMask, ((ScanRecord)object).getServiceSolicitationUuids())) {
            return false;
        }
        object2 = this.mServiceDataUuid;
        if (object2 != null && !this.matchesPartialData(this.mServiceData, this.mServiceDataMask, ((ScanRecord)object).getServiceData((ParcelUuid)object2))) {
            return false;
        }
        int n = this.mManufacturerId;
        return n < 0 || this.matchesPartialData(this.mManufacturerData, this.mManufacturerDataMask, ((ScanRecord)object).getManufacturerSpecificData(n));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BluetoothLeScanFilter [mDeviceName=");
        stringBuilder.append(this.mDeviceName);
        stringBuilder.append(", mDeviceAddress=");
        stringBuilder.append(this.mDeviceAddress);
        stringBuilder.append(", mUuid=");
        stringBuilder.append(this.mServiceUuid);
        stringBuilder.append(", mUuidMask=");
        stringBuilder.append(this.mServiceUuidMask);
        stringBuilder.append(", mServiceSolicitationUuid=");
        stringBuilder.append(this.mServiceSolicitationUuid);
        stringBuilder.append(", mServiceSolicitationUuidMask=");
        stringBuilder.append(this.mServiceSolicitationUuidMask);
        stringBuilder.append(", mServiceDataUuid=");
        stringBuilder.append(Objects.toString(this.mServiceDataUuid));
        stringBuilder.append(", mServiceData=");
        stringBuilder.append(Arrays.toString(this.mServiceData));
        stringBuilder.append(", mServiceDataMask=");
        stringBuilder.append(Arrays.toString(this.mServiceDataMask));
        stringBuilder.append(", mManufacturerId=");
        stringBuilder.append(this.mManufacturerId);
        stringBuilder.append(", mManufacturerData=");
        stringBuilder.append(Arrays.toString(this.mManufacturerData));
        stringBuilder.append(", mManufacturerDataMask=");
        stringBuilder.append(Arrays.toString(this.mManufacturerDataMask));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        byte[] arrby = this.mDeviceName;
        int n2 = 0;
        int n3 = arrby == null ? 0 : 1;
        parcel.writeInt(n3);
        arrby = this.mDeviceName;
        if (arrby != null) {
            parcel.writeString((String)arrby);
        }
        n3 = this.mDeviceAddress == null ? 0 : 1;
        parcel.writeInt(n3);
        arrby = this.mDeviceAddress;
        if (arrby != null) {
            parcel.writeString((String)arrby);
        }
        n3 = this.mServiceUuid == null ? 0 : 1;
        parcel.writeInt(n3);
        arrby = this.mServiceUuid;
        if (arrby != null) {
            parcel.writeParcelable((Parcelable)arrby, n);
            n3 = this.mServiceUuidMask == null ? 0 : 1;
            parcel.writeInt(n3);
            arrby = this.mServiceUuidMask;
            if (arrby != null) {
                parcel.writeParcelable((Parcelable)arrby, n);
            }
        }
        n3 = this.mServiceSolicitationUuid == null ? 0 : 1;
        parcel.writeInt(n3);
        arrby = this.mServiceSolicitationUuid;
        if (arrby != null) {
            parcel.writeParcelable((Parcelable)arrby, n);
            n3 = this.mServiceSolicitationUuidMask == null ? 0 : 1;
            parcel.writeInt(n3);
            arrby = this.mServiceSolicitationUuidMask;
            if (arrby != null) {
                parcel.writeParcelable((Parcelable)arrby, n);
            }
        }
        n3 = this.mServiceDataUuid == null ? 0 : 1;
        parcel.writeInt(n3);
        arrby = this.mServiceDataUuid;
        if (arrby != null) {
            parcel.writeParcelable((Parcelable)arrby, n);
            n = this.mServiceData == null ? 0 : 1;
            parcel.writeInt(n);
            arrby = this.mServiceData;
            if (arrby != null) {
                parcel.writeInt(arrby.length);
                parcel.writeByteArray(this.mServiceData);
                n = this.mServiceDataMask == null ? 0 : 1;
                parcel.writeInt(n);
                arrby = this.mServiceDataMask;
                if (arrby != null) {
                    parcel.writeInt(arrby.length);
                    parcel.writeByteArray(this.mServiceDataMask);
                }
            }
        }
        parcel.writeInt(this.mManufacturerId);
        n = this.mManufacturerData == null ? 0 : 1;
        parcel.writeInt(n);
        arrby = this.mManufacturerData;
        if (arrby != null) {
            parcel.writeInt(arrby.length);
            parcel.writeByteArray(this.mManufacturerData);
            n = this.mManufacturerDataMask == null ? n2 : 1;
            parcel.writeInt(n);
            arrby = this.mManufacturerDataMask;
            if (arrby != null) {
                parcel.writeInt(arrby.length);
                parcel.writeByteArray(this.mManufacturerDataMask);
            }
        }
    }

    public static final class Builder {
        private String mDeviceAddress;
        private String mDeviceName;
        private byte[] mManufacturerData;
        private byte[] mManufacturerDataMask;
        private int mManufacturerId = -1;
        private byte[] mServiceData;
        private byte[] mServiceDataMask;
        private ParcelUuid mServiceDataUuid;
        private ParcelUuid mServiceSolicitationUuid;
        private ParcelUuid mServiceSolicitationUuidMask;
        private ParcelUuid mServiceUuid;
        private ParcelUuid mUuidMask;

        public ScanFilter build() {
            return new ScanFilter(this.mDeviceName, this.mDeviceAddress, this.mServiceUuid, this.mUuidMask, this.mServiceSolicitationUuid, this.mServiceSolicitationUuidMask, this.mServiceDataUuid, this.mServiceData, this.mServiceDataMask, this.mManufacturerId, this.mManufacturerData, this.mManufacturerDataMask);
        }

        public Builder setDeviceAddress(String string2) {
            if (string2 != null && !BluetoothAdapter.checkBluetoothAddress(string2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid device address ");
                stringBuilder.append(string2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mDeviceAddress = string2;
            return this;
        }

        public Builder setDeviceName(String string2) {
            this.mDeviceName = string2;
            return this;
        }

        public Builder setManufacturerData(int n, byte[] arrby) {
            if (arrby != null && n < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            this.mManufacturerId = n;
            this.mManufacturerData = arrby;
            this.mManufacturerDataMask = null;
            return this;
        }

        public Builder setManufacturerData(int n, byte[] arrby, byte[] arrby2) {
            if (arrby != null && n < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            byte[] arrby3 = this.mManufacturerDataMask;
            if (arrby3 != null) {
                byte[] arrby4 = this.mManufacturerData;
                if (arrby4 != null) {
                    if (arrby4.length != arrby3.length) {
                        throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
                    }
                } else {
                    throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
                }
            }
            this.mManufacturerId = n;
            this.mManufacturerData = arrby;
            this.mManufacturerDataMask = arrby2;
            return this;
        }

        public Builder setServiceData(ParcelUuid parcelUuid, byte[] arrby) {
            if (parcelUuid != null) {
                this.mServiceDataUuid = parcelUuid;
                this.mServiceData = arrby;
                this.mServiceDataMask = null;
                return this;
            }
            throw new IllegalArgumentException("serviceDataUuid is null");
        }

        public Builder setServiceData(ParcelUuid parcelUuid, byte[] arrby, byte[] arrby2) {
            if (parcelUuid != null) {
                byte[] arrby3 = this.mServiceDataMask;
                if (arrby3 != null) {
                    byte[] arrby4 = this.mServiceData;
                    if (arrby4 != null) {
                        if (arrby4.length != arrby3.length) {
                            throw new IllegalArgumentException("size mismatch for service data and service data mask");
                        }
                    } else {
                        throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
                    }
                }
                this.mServiceDataUuid = parcelUuid;
                this.mServiceData = arrby;
                this.mServiceDataMask = arrby2;
                return this;
            }
            throw new IllegalArgumentException("serviceDataUuid is null");
        }

        public Builder setServiceSolicitationUuid(ParcelUuid parcelUuid) {
            this.mServiceSolicitationUuid = parcelUuid;
            if (parcelUuid == null) {
                this.mServiceSolicitationUuidMask = null;
            }
            return this;
        }

        public Builder setServiceSolicitationUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) {
            if (parcelUuid2 != null && parcelUuid == null) {
                throw new IllegalArgumentException("SolicitationUuid is null while SolicitationUuidMask is not null!");
            }
            this.mServiceSolicitationUuid = parcelUuid;
            this.mServiceSolicitationUuidMask = parcelUuid2;
            return this;
        }

        public Builder setServiceUuid(ParcelUuid parcelUuid) {
            this.mServiceUuid = parcelUuid;
            this.mUuidMask = null;
            return this;
        }

        public Builder setServiceUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) {
            if (this.mUuidMask != null && this.mServiceUuid == null) {
                throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
            }
            this.mServiceUuid = parcelUuid;
            this.mUuidMask = parcelUuid2;
            return this;
        }
    }

}

