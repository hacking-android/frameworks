/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;

public abstract class BluetoothGattServerCallback {
    public void onCharacteristicReadRequest(BluetoothDevice bluetoothDevice, int n, int n2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    public void onCharacteristicWriteRequest(BluetoothDevice bluetoothDevice, int n, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean bl, boolean bl2, int n2, byte[] arrby) {
    }

    public void onConnectionStateChange(BluetoothDevice bluetoothDevice, int n, int n2) {
    }

    public void onConnectionUpdated(BluetoothDevice bluetoothDevice, int n, int n2, int n3, int n4) {
    }

    public void onDescriptorReadRequest(BluetoothDevice bluetoothDevice, int n, int n2, BluetoothGattDescriptor bluetoothGattDescriptor) {
    }

    public void onDescriptorWriteRequest(BluetoothDevice bluetoothDevice, int n, BluetoothGattDescriptor bluetoothGattDescriptor, boolean bl, boolean bl2, int n2, byte[] arrby) {
    }

    public void onExecuteWrite(BluetoothDevice bluetoothDevice, int n, boolean bl) {
    }

    public void onMtuChanged(BluetoothDevice bluetoothDevice, int n) {
    }

    public void onNotificationSent(BluetoothDevice bluetoothDevice, int n) {
    }

    public void onPhyRead(BluetoothDevice bluetoothDevice, int n, int n2, int n3) {
    }

    public void onPhyUpdate(BluetoothDevice bluetoothDevice, int n, int n2, int n3) {
    }

    public void onServiceAdded(int n, BluetoothGattService bluetoothGattService) {
    }
}

