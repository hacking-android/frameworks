/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.PeriodicAdvertisingParameters;
import android.os.RemoteException;
import android.util.Log;

public final class AdvertisingSet {
    private static final String TAG = "AdvertisingSet";
    private int mAdvertiserId;
    private final IBluetoothGatt mGatt;

    AdvertisingSet(int n, IBluetoothManager iBluetoothManager) {
        this.mAdvertiserId = n;
        try {
            this.mGatt = iBluetoothManager.getBluetoothGatt();
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to get Bluetooth gatt - ", remoteException);
            throw new IllegalStateException("Failed to get Bluetooth");
        }
    }

    public void enableAdvertising(boolean bl, int n, int n2) {
        try {
            this.mGatt.enableAdvertisingSet(this.mAdvertiserId, bl, n, n2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    public int getAdvertiserId() {
        return this.mAdvertiserId;
    }

    public void getOwnAddress() {
        try {
            this.mGatt.getOwnAddress(this.mAdvertiserId);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    void setAdvertiserId(int n) {
        this.mAdvertiserId = n;
    }

    public void setAdvertisingData(AdvertiseData advertiseData) {
        try {
            this.mGatt.setAdvertisingData(this.mAdvertiserId, advertiseData);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    public void setAdvertisingParameters(AdvertisingSetParameters advertisingSetParameters) {
        try {
            this.mGatt.setAdvertisingParameters(this.mAdvertiserId, advertisingSetParameters);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    public void setPeriodicAdvertisingData(AdvertiseData advertiseData) {
        try {
            this.mGatt.setPeriodicAdvertisingData(this.mAdvertiserId, advertiseData);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    public void setPeriodicAdvertisingEnabled(boolean bl) {
        try {
            this.mGatt.setPeriodicAdvertisingEnable(this.mAdvertiserId, bl);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    public void setPeriodicAdvertisingParameters(PeriodicAdvertisingParameters periodicAdvertisingParameters) {
        try {
            this.mGatt.setPeriodicAdvertisingParameters(this.mAdvertiserId, periodicAdvertisingParameters);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }

    public void setScanResponseData(AdvertiseData advertiseData) {
        try {
            this.mGatt.setScanResponseData(this.mAdvertiserId, advertiseData);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote exception - ", remoteException);
        }
    }
}

