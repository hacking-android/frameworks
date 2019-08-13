/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

public abstract class BluetoothGattCallback {
    public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
    }

    public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
    }

    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int n, int n2) {
    }

    public void onConnectionUpdated(BluetoothGatt bluetoothGatt, int n, int n2, int n3, int n4) {
    }

    public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int n) {
    }

    public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int n) {
    }

    public void onMtuChanged(BluetoothGatt bluetoothGatt, int n, int n2) {
    }

    public void onPhyRead(BluetoothGatt bluetoothGatt, int n, int n2, int n3) {
    }

    public void onPhyUpdate(BluetoothGatt bluetoothGatt, int n, int n2, int n3) {
    }

    public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int n, int n2) {
    }

    public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int n) {
    }

    public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int n) {
    }
}

