/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattIncludedService;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BluetoothGattService
implements Parcelable {
    public static final Parcelable.Creator<BluetoothGattService> CREATOR = new Parcelable.Creator<BluetoothGattService>(){

        @Override
        public BluetoothGattService createFromParcel(Parcel parcel) {
            return new BluetoothGattService(parcel);
        }

        public BluetoothGattService[] newArray(int n) {
            return new BluetoothGattService[n];
        }
    };
    public static final int SERVICE_TYPE_PRIMARY = 0;
    public static final int SERVICE_TYPE_SECONDARY = 1;
    private boolean mAdvertisePreferred;
    protected List<BluetoothGattCharacteristic> mCharacteristics;
    @UnsupportedAppUsage
    protected BluetoothDevice mDevice;
    protected int mHandles = 0;
    protected List<BluetoothGattService> mIncludedServices;
    protected int mInstanceId;
    protected int mServiceType;
    protected UUID mUuid;

    BluetoothGattService(BluetoothDevice bluetoothDevice, UUID uUID, int n, int n2) {
        this.mDevice = bluetoothDevice;
        this.mUuid = uUID;
        this.mInstanceId = n;
        this.mServiceType = n2;
        this.mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
        this.mIncludedServices = new ArrayList<BluetoothGattService>();
    }

    private BluetoothGattService(Parcel iterator) {
        this.mUuid = ((ParcelUuid)((Parcel)((Object)iterator)).readParcelable(null)).getUuid();
        this.mInstanceId = ((Parcel)((Object)iterator)).readInt();
        this.mServiceType = ((Parcel)((Object)iterator)).readInt();
        this.mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
        Object object = ((Parcel)((Object)iterator)).createTypedArrayList(BluetoothGattCharacteristic.CREATOR);
        if (object != null) {
            Iterator<BluetoothGattCharacteristic> iterator2 = ((ArrayList)object).iterator();
            while (iterator2.hasNext()) {
                BluetoothGattCharacteristic bluetoothGattCharacteristic = iterator2.next();
                bluetoothGattCharacteristic.setService(this);
                this.mCharacteristics.add(bluetoothGattCharacteristic);
            }
        }
        this.mIncludedServices = new ArrayList<BluetoothGattService>();
        iterator = ((Parcel)((Object)iterator)).createTypedArrayList(BluetoothGattIncludedService.CREATOR);
        if (object != null) {
            iterator = ((ArrayList)((Object)iterator)).iterator();
            while (iterator.hasNext()) {
                object = (BluetoothGattIncludedService)iterator.next();
                this.mIncludedServices.add(new BluetoothGattService(null, ((BluetoothGattIncludedService)object).getUuid(), ((BluetoothGattIncludedService)object).getInstanceId(), ((BluetoothGattIncludedService)object).getType()));
            }
        }
    }

    public BluetoothGattService(UUID uUID, int n) {
        this.mDevice = null;
        this.mUuid = uUID;
        this.mInstanceId = 0;
        this.mServiceType = n;
        this.mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
        this.mIncludedServices = new ArrayList<BluetoothGattService>();
    }

    public BluetoothGattService(UUID uUID, int n, int n2) {
        this.mDevice = null;
        this.mUuid = uUID;
        this.mInstanceId = n;
        this.mServiceType = n2;
        this.mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
        this.mIncludedServices = new ArrayList<BluetoothGattService>();
    }

    public boolean addCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.mCharacteristics.add(bluetoothGattCharacteristic);
        bluetoothGattCharacteristic.setService(this);
        return true;
    }

    public void addIncludedService(BluetoothGattService bluetoothGattService) {
        this.mIncludedServices.add(bluetoothGattService);
    }

    public boolean addService(BluetoothGattService bluetoothGattService) {
        this.mIncludedServices.add(bluetoothGattService);
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public BluetoothGattCharacteristic getCharacteristic(UUID uUID) {
        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : this.mCharacteristics) {
            if (!uUID.equals(bluetoothGattCharacteristic.getUuid())) continue;
            return bluetoothGattCharacteristic;
        }
        return null;
    }

    BluetoothGattCharacteristic getCharacteristic(UUID uUID, int n) {
        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : this.mCharacteristics) {
            if (!uUID.equals(bluetoothGattCharacteristic.getUuid()) || bluetoothGattCharacteristic.getInstanceId() != n) continue;
            return bluetoothGattCharacteristic;
        }
        return null;
    }

    public List<BluetoothGattCharacteristic> getCharacteristics() {
        return this.mCharacteristics;
    }

    BluetoothDevice getDevice() {
        return this.mDevice;
    }

    int getHandles() {
        return this.mHandles;
    }

    public List<BluetoothGattService> getIncludedServices() {
        return this.mIncludedServices;
    }

    public int getInstanceId() {
        return this.mInstanceId;
    }

    public int getType() {
        return this.mServiceType;
    }

    public UUID getUuid() {
        return this.mUuid;
    }

    public boolean isAdvertisePreferred() {
        return this.mAdvertisePreferred;
    }

    @UnsupportedAppUsage
    public void setAdvertisePreferred(boolean bl) {
        this.mAdvertisePreferred = bl;
    }

    void setDevice(BluetoothDevice bluetoothDevice) {
        this.mDevice = bluetoothDevice;
    }

    public void setHandles(int n) {
        this.mHandles = n;
    }

    @UnsupportedAppUsage
    public void setInstanceId(int n) {
        this.mInstanceId = n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(new ParcelUuid(this.mUuid), 0);
        parcel.writeInt(this.mInstanceId);
        parcel.writeInt(this.mServiceType);
        parcel.writeTypedList(this.mCharacteristics);
        ArrayList<BluetoothGattIncludedService> arrayList = new ArrayList<BluetoothGattIncludedService>(this.mIncludedServices.size());
        for (BluetoothGattService bluetoothGattService : this.mIncludedServices) {
            arrayList.add(new BluetoothGattIncludedService(bluetoothGattService.getUuid(), bluetoothGattService.getInstanceId(), bluetoothGattService.getType()));
        }
        parcel.writeTypedList(arrayList);
    }

}

