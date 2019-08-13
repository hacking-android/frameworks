/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.companion.BluetoothDeviceFilterUtils;
import android.companion.DeviceFilter;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.OneTimeUseBuilder;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public final class BluetoothDeviceFilter
implements DeviceFilter<BluetoothDevice> {
    public static final Parcelable.Creator<BluetoothDeviceFilter> CREATOR = new Parcelable.Creator<BluetoothDeviceFilter>(){

        @Override
        public BluetoothDeviceFilter createFromParcel(Parcel parcel) {
            return new BluetoothDeviceFilter(parcel);
        }

        public BluetoothDeviceFilter[] newArray(int n) {
            return new BluetoothDeviceFilter[n];
        }
    };
    private final String mAddress;
    private final Pattern mNamePattern;
    private final List<ParcelUuid> mServiceUuidMasks;
    private final List<ParcelUuid> mServiceUuids;

    private BluetoothDeviceFilter(Parcel parcel) {
        this(BluetoothDeviceFilterUtils.patternFromString(parcel.readString()), parcel.readString(), BluetoothDeviceFilter.readUuids(parcel), BluetoothDeviceFilter.readUuids(parcel));
    }

    private BluetoothDeviceFilter(Pattern pattern, String string2, List<ParcelUuid> list, List<ParcelUuid> list2) {
        this.mNamePattern = pattern;
        this.mAddress = string2;
        this.mServiceUuids = CollectionUtils.emptyIfNull(list);
        this.mServiceUuidMasks = CollectionUtils.emptyIfNull(list2);
    }

    private static List<ParcelUuid> readUuids(Parcel parcel) {
        return parcel.readParcelableList(new ArrayList(), ParcelUuid.class.getClassLoader());
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
            object = (BluetoothDeviceFilter)object;
            if (!(Objects.equals(this.mNamePattern, ((BluetoothDeviceFilter)object).mNamePattern) && Objects.equals(this.mAddress, ((BluetoothDeviceFilter)object).mAddress) && Objects.equals(this.mServiceUuids, ((BluetoothDeviceFilter)object).mServiceUuids) && Objects.equals(this.mServiceUuidMasks, ((BluetoothDeviceFilter)object).mServiceUuidMasks))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public String getAddress() {
        return this.mAddress;
    }

    @Override
    public String getDeviceDisplayName(BluetoothDevice bluetoothDevice) {
        return BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal(bluetoothDevice);
    }

    @Override
    public int getMediumType() {
        return 0;
    }

    public Pattern getNamePattern() {
        return this.mNamePattern;
    }

    public List<ParcelUuid> getServiceUuidMasks() {
        return this.mServiceUuidMasks;
    }

    public List<ParcelUuid> getServiceUuids() {
        return this.mServiceUuids;
    }

    public int hashCode() {
        return Objects.hash(this.mNamePattern, this.mAddress, this.mServiceUuids, this.mServiceUuidMasks);
    }

    @Override
    public boolean matches(BluetoothDevice bluetoothDevice) {
        boolean bl = BluetoothDeviceFilterUtils.matchesAddress(this.mAddress, bluetoothDevice) && BluetoothDeviceFilterUtils.matchesServiceUuids(this.mServiceUuids, this.mServiceUuidMasks, bluetoothDevice) && BluetoothDeviceFilterUtils.matchesName(this.getNamePattern(), bluetoothDevice);
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(BluetoothDeviceFilterUtils.patternToString(this.getNamePattern()));
        parcel.writeString(this.mAddress);
        parcel.writeParcelableList(this.mServiceUuids, n);
        parcel.writeParcelableList(this.mServiceUuidMasks, n);
    }

    public static final class Builder
    extends OneTimeUseBuilder<BluetoothDeviceFilter> {
        private String mAddress;
        private Pattern mNamePattern;
        private ArrayList<ParcelUuid> mServiceUuid;
        private ArrayList<ParcelUuid> mServiceUuidMask;

        public Builder addServiceUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2) {
            this.checkNotUsed();
            this.mServiceUuid = ArrayUtils.add(this.mServiceUuid, parcelUuid);
            this.mServiceUuidMask = ArrayUtils.add(this.mServiceUuidMask, parcelUuid2);
            return this;
        }

        @Override
        public BluetoothDeviceFilter build() {
            this.markUsed();
            return new BluetoothDeviceFilter(this.mNamePattern, this.mAddress, this.mServiceUuid, this.mServiceUuidMask);
        }

        public Builder setAddress(String string2) {
            this.checkNotUsed();
            this.mAddress = string2;
            return this;
        }

        public Builder setNamePattern(Pattern pattern) {
            this.checkNotUsed();
            this.mNamePattern = pattern;
            return this;
        }
    }

}

