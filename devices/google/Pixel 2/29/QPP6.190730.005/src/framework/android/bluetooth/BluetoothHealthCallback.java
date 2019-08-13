/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.os.ParcelFileDescriptor;
import android.util.Log;

@Deprecated
public abstract class BluetoothHealthCallback {
    private static final String TAG = "BluetoothHealthCallback";

    @Deprecated
    public void onHealthAppConfigurationStatusChange(BluetoothHealthAppConfiguration bluetoothHealthAppConfiguration, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onHealthAppConfigurationStatusChange: ");
        stringBuilder.append(bluetoothHealthAppConfiguration);
        stringBuilder.append("Status: ");
        stringBuilder.append(n);
        Log.d(TAG, stringBuilder.toString());
    }

    @Deprecated
    public void onHealthChannelStateChange(BluetoothHealthAppConfiguration bluetoothHealthAppConfiguration, BluetoothDevice bluetoothDevice, int n, int n2, ParcelFileDescriptor parcelFileDescriptor, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onHealthChannelStateChange: ");
        stringBuilder.append(bluetoothHealthAppConfiguration);
        stringBuilder.append("Device: ");
        stringBuilder.append(bluetoothDevice);
        stringBuilder.append("prevState:");
        stringBuilder.append(n);
        stringBuilder.append("newState:");
        stringBuilder.append(n2);
        stringBuilder.append("ParcelFd:");
        stringBuilder.append(parcelFileDescriptor);
        stringBuilder.append("ChannelId:");
        stringBuilder.append(n3);
        Log.d(TAG, stringBuilder.toString());
    }
}

