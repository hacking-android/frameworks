/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.bluetooth.BluetoothHealthCallback;
import android.bluetooth.BluetoothProfile;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class BluetoothHealth
implements BluetoothProfile {
    @Deprecated
    public static final int APP_CONFIG_REGISTRATION_FAILURE = 1;
    @Deprecated
    public static final int APP_CONFIG_REGISTRATION_SUCCESS = 0;
    @Deprecated
    public static final int APP_CONFIG_UNREGISTRATION_FAILURE = 3;
    @Deprecated
    public static final int APP_CONFIG_UNREGISTRATION_SUCCESS = 2;
    @Deprecated
    public static final int CHANNEL_TYPE_RELIABLE = 10;
    @Deprecated
    public static final int CHANNEL_TYPE_STREAMING = 11;
    @Deprecated
    public static final int SINK_ROLE = 2;
    @Deprecated
    public static final int SOURCE_ROLE = 1;
    @Deprecated
    public static final int STATE_CHANNEL_CONNECTED = 2;
    @Deprecated
    public static final int STATE_CHANNEL_CONNECTING = 1;
    @Deprecated
    public static final int STATE_CHANNEL_DISCONNECTED = 0;
    @Deprecated
    public static final int STATE_CHANNEL_DISCONNECTING = 3;
    private static final String TAG = "BluetoothHealth";

    BluetoothHealth() {
    }

    @Deprecated
    public boolean connectChannelToSource(BluetoothDevice bluetoothDevice, BluetoothHealthAppConfiguration bluetoothHealthAppConfiguration) {
        Log.e(TAG, "connectChannelToSource(): BluetoothHealth is deprecated");
        return false;
    }

    @Deprecated
    public boolean disconnectChannel(BluetoothDevice bluetoothDevice, BluetoothHealthAppConfiguration bluetoothHealthAppConfiguration, int n) {
        Log.e(TAG, "disconnectChannel(): BluetoothHealth is deprecated");
        return false;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        Log.e(TAG, "getConnectedDevices(): BluetoothHealth is deprecated");
        return new ArrayList<BluetoothDevice>();
    }

    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        Log.e(TAG, "getConnectionState(): BluetoothHealth is deprecated");
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] arrn) {
        Log.e(TAG, "getDevicesMatchingConnectionStates(): BluetoothHealth is deprecated");
        return new ArrayList<BluetoothDevice>();
    }

    @Deprecated
    public ParcelFileDescriptor getMainChannelFd(BluetoothDevice bluetoothDevice, BluetoothHealthAppConfiguration bluetoothHealthAppConfiguration) {
        Log.e(TAG, "getMainChannelFd(): BluetoothHealth is deprecated");
        return null;
    }

    @Deprecated
    public boolean registerSinkAppConfiguration(String string2, int n, BluetoothHealthCallback bluetoothHealthCallback) {
        Log.e(TAG, "registerSinkAppConfiguration(): BluetoothHealth is deprecated");
        return false;
    }

    @Deprecated
    public boolean unregisterAppConfiguration(BluetoothHealthAppConfiguration bluetoothHealthAppConfiguration) {
        Log.e(TAG, "unregisterAppConfiguration(): BluetoothHealth is deprecated");
        return false;
    }
}

